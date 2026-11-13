/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer14
package Librerias.ZafCpbEle;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.EnumFormatoFirma;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreMaintainer;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.AllXMLToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import es.mityc.javasign.xml.resolvers.XAdESResourceResolverSpi;
import es.mityc.javasign.pkstore.IPassStoreKS;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import java.util.ArrayList;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafFirma_p12
{
    private javax.swing.JInternalFrame ifruso;
    private javax.swing.JOptionPane oppMsg;
    private String PKCS12_RESOURCE;
    private String PKCS12_PASSWORD;
    private String RESOURCE_TO_SIGN;
    IPKStoreManager storeManager;
    DataToSign dataToSign;
    ArrayList arlDatFirDig;
    //Constante del arreglo que devuelve la descripcion del error al realizar la firma digital
    final int INT_ARL_DAT_FIR_MSJ = 0;
    
    public ZafFirma_p12(int intCodEmp, String strNomFil, String strNomFil_p12, String strClvFil_p12)
    {
      String strAux, strRutFil_p12;
      
      try
      {
         PKCS12_PASSWORD = strClvFil_p12; 
         oppMsg = new JOptionPane();
         ifruso = new JInternalFrame();
         RESOURCE_TO_SIGN = strNomFil;
         arlDatFirDig = new ArrayList();
         arlDatFirDig.add(INT_ARL_DAT_FIR_MSJ, "");
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFil_p12 = "/Zafiro/FacturacionElectronica/FirmasDigitales/";
         else
            strRutFil_p12 = "C:\\Zafiro\\FacturacionElectronica\\FirmasDigitales\\";
         
         PKCS12_RESOURCE = strRutFil_p12 + strNomFil_p12;
      }
      
      catch(Exception e)
      {  strAux = "No se pudo firmar el archivo XML. " + e.toString();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
         //mostrarMsgErr(strAux);
      }
    }
    
    /**
   * Esta función muestra un mensaje de error al usuario. Se podría utilizar
   * para mostrar al usuario un mensaje que indique que algo salió mal.
   */
   private void mostrarMsgErr(String strMsg)
   {
      String strTit;
      strTit = "Mensaje del sistema Zafiro";
      oppMsg.showMessageDialog(ifruso, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
   }
    
   class PassStoreKS implements IPassStoreKS {
    
    private final transient String password;

    public PassStoreKS(final String pass) {
  		this.password = pass;
  	}

    @Override
    public char[] getPassword(X509Certificate xc, String string) {
        return password.toCharArray();
    }
}

   protected void execute() throws ParserConfigurationException {
      
      String strAux;
      //arlDatFirDig solo se llenara si se encontro alguna excepcion
      
      // 1) Obtener un gestor de claves (instancia de la clase IPKStoreManager de la librería MITyCLibAPI)
      storeManager = getPKStoreManager();

      if (arlDatFirDig.get(0).toString().equals(""))
      {  if (storeManager == null) {
            strAux = "Error al intentar realizar la firma digital. El gestor de claves no se ha obtenido correctamente";
            arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
            System.err.println(strAux);
         }

         // 2) Obtener el certificado con el que se desea firmar, utilizando para ello el gestor de claves obtenido anteriormente.
         X509Certificate certificate = getFirstCertificate(storeManager);

         if (certificate == null) {
             strAux = "Error al intentar realizar la firma digital. No existe ningún certificado para firmar";
             arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
             System.err.println(strAux);
         }

         java.security.PrivateKey privateKey = null;

         if (arlDatFirDig.get(0).toString().equals(""))
         {  
            try {
                // 3) Obtención de la clave privada asociada al certificado anterior.
                privateKey = storeManager.getPrivateKey(certificate);

            } catch (CertStoreException e) {
                strAux = "Error al intentar realizar la firma digital. Error al acceder al almacén. " + e.getMessage();
                arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
                System.err.println(strAux);
            }

            // 4) Obtención del provider encargado de realizar las funciones criptográficas.
            Provider provider = storeManager.getProvider(certificate);
            DataToSign dataToSign = createDataToSign();

            FirmaXML firma = new FirmaXML();
            org.w3c.dom.Document docSigned = null;

            if (arlDatFirDig.get(0).toString().equals(""))
            {
               try {
                   Object[] r = firma.signFile(certificate, dataToSign, privateKey, provider);
                   docSigned = (org.w3c.dom.Document) r[0];
               } catch (Exception ex) {
                   strAux = "Error al intentar realizar la firma digital. " + ex.getMessage();
                   arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
                   System.err.println(strAux);
                   //System.err.println("Error realizando la firma");
                   ex.printStackTrace();
               }

               if (arlDatFirDig.get(0).toString().equals(""))
               {  saveDocumentToFile((org.w3c.dom.Document)docSigned, getSignatureFileName());
                  //System.out.println("Firma salvada en: " + filePath);
               }
            }
         }
      }
   } //fin de metodo

   private IPKStoreManager getPKStoreManager()  {
    
        String strAux;
        try {
             KeyStore ks = KeyStore.getInstance("PKCS12");
             ks.load(new FileInputStream(PKCS12_RESOURCE), PKCS12_PASSWORD.toCharArray());

             storeManager = new KSStore(ks, new PassStoreKS(PKCS12_PASSWORD));
        }
        catch (IOException ex) {
         Logger.getLogger(ZafFirma_p12.class.getName()).log(Level.SEVERE, null, ex);
         strAux = "Error al intentar realizar la firma digital. No se puede generar KeyStore PKCS12. " + ex.getMessage();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
      } catch (NoSuchAlgorithmException ex) {
         Logger.getLogger(ZafFirma_p12.class.getName()).log(Level.SEVERE, null, ex);
         strAux = "Error al intentar realizar la firma digital. No se puede generar KeyStore PKCS12. " + ex.getMessage();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
      } catch (CertificateException ex) {
         Logger.getLogger(ZafFirma_p12.class.getName()).log(Level.SEVERE, null, ex);
         strAux = "Error al intentar realizar la firma digital. No se puede generar KeyStore PKCS12. " + ex.getMessage();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
      } catch (KeyStoreException ex) {
         strAux = "Error al intentar realizar la firma digital. No se puede generar KeyStore PKCS12. " + ex.getMessage();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
         //System.exit(-1);
      }
        
        return storeManager;
   }

   private X509Certificate getFirstCertificate(IPKStoreManager storeManager) {
      List<X509Certificate> certs = null;
      String strAux;
      
         try {
             certs = storeManager.getSignCertificates();

         } catch (CertStoreException ex) {
             strAux = "Error al intentar realizar la firma digital. Fallo obteniendo listado de certificados. " + ex.getMessage();
             arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
             System.err.println(strAux);
             //System.exit(-1);
         }
         if ((certs == null) || (certs.isEmpty())) {
             strAux = "Error al intentar realizar la firma digital. Lista de certificados vacía";
             arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
             System.err.println(strAux);
             //System.exit(-1);
         }
 
         //X509Certificate certificate = certs.get(0); //Firma usando "cifrado de clave"
         X509Certificate certificate = certs.get(1); //Firma usando "firma digital"
         
         return certificate;
   }
   
   private DataToSign createDataToSign() {
      String strAux;
      try {
          DataToSign dataToSign = new DataToSign();
          dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
          dataToSign.setEsquema(XAdESSchemas.XAdES_132);
          dataToSign.setXMLEncoding("UTF-8");
          dataToSign.setEnveloped(true);
          dataToSign.setParentSignNode("comprobante");
          dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
          org.w3c.dom.Document docToSign = getDocument(RESOURCE_TO_SIGN);
          dataToSign.setDocument(docToSign);
          return dataToSign;
      } 
      catch (ParserConfigurationException ex) {
         Logger.getLogger(ZafFirma_p12.class.getName()).log(Level.SEVERE, null, ex);
         strAux = "Error al intentar realizar la firma digital. Ocurrio en funcion createDataToSign(). " + ex.getMessage();
         arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
         System.err.println(strAux);
      }
      return dataToSign;
   }
   
     protected org.w3c.dom.Document getDocument(String resource) throws ParserConfigurationException {

         org.w3c.dom.Document doct = null;
         String strAux;
         
         try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder dbuilder = dbf.newDocumentBuilder();
           try {
              doct = dbuilder.parse(resource);
           } catch (IOException ex) {
              Logger.getLogger(ZafFirma_p12.class.getName()).log(Level.SEVERE, null, ex);
              strAux = "Error al intentar realizar la firma digital. Ocurrio en funcion getDocument(). " + ex.getMessage();
              arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
              System.err.println(strAux);
           }
           System.out.println(doct);
             
         } catch (SAXException  ex) {
             strAux = "Error al intentar realizar la firma digital. Error al parsear el documento. " + ex.getMessage();
             arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
             System.err.println(strAux);
             //System.exit(-1);
         }

         return doct;
   } //fin de metodo

   private String getSignatureFileName() {
     
      return RESOURCE_TO_SIGN;
   }

   private void saveDocumentToFile(Document document, String pathfile) {
      String strAux;
      try {
            FileOutputStream fos = new FileOutputStream(pathfile);
            UtilidadTratarNodo.saveDocumentToOutputStream((org.w3c.dom.Document) document, fos, true);
            
        } catch (FileNotFoundException e) {
            strAux = "Error al intentar realizar la firma digital. Error al salvar el documento. " + e.getMessage();
            arlDatFirDig.set(INT_ARL_DAT_FIR_MSJ, strAux);
            System.err.println(strAux);
            e.printStackTrace();
        }
   }
}