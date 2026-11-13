/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package Librerias.ZafCpbEle;

import ec.gob.sri.comprobantes.administracion.modelo.Emisor;
//import ec.gob.sri.comprobantes.table.model.FileTableModel;
import ec.gob.sri.comprobantes.util.ArchivoUtils;
import ec.gob.sri.comprobantes.util.AutorizacionComprobantesWs;
import ec.gob.sri.comprobantes.util.EnvioComprobantesWs;
import ec.gob.sri.comprobantes.util.FormGenerales;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
//Librerias para generacion de reporte en PDF
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
//Librerias para envio de correo electronico
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import Librerias.ZafUtil.ZafUtil;
import java.util.Date;
import java.io.File;
import java.io.OutputStream;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
//Librerias para leer XML
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafCpbEle
{
   Librerias.ZafParSis.ZafParSis objParSis;
   ZafUtil objUti;
   private javax.swing.JInternalFrame ifruso;
   private javax.swing.JOptionPane oppMsg;
   private Emisor emisor;
   private String tokenSeleccionado, strSQL;
   ArrayList arlRegDetResWS;
   private ZafNotCorEle objNotCorEle;
   private static final int INT_ARL_COD_CFG_RETENCIONES = 29;
   //Constantes del arreglo que devuelven los metodos de autorizacion de algun comprobante electronico
   static final int INT_ARL_DAT_RES_EST = 0;
   static final int INT_ARL_DAT_RES_MSJ = 1;
   static final int INT_ARL_DAT_RES_NUM_AUT = 2;
   static final int INT_ARL_DAT_RES_FEC_AUT = 3;
   static final int INT_ARL_DAT_RES_EST_CLV_ACC_REG = 4;
   static final int INT_ARL_DAT_RES_CLV_ACC = 5;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_cabmovinv
   static final int INT_ARL_DAT_CAB_MOV_INV_COD_EMP = 0;
   static final int INT_ARL_DAT_CAB_MOV_INV_COD_LOC = 1;
   static final int INT_ARL_DAT_CAB_MOV_INV_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_CAB_MOV_INV_COD_DOC = 3;
   static final int INT_ARL_DAT_CAB_MOV_INV_NUM_DOC = 4;
   static final int INT_ARL_DAT_CAB_MOV_INV_FEC_DOC = 5;
   static final int INT_ARL_DAT_CAB_MOV_INV_NUM_FAC = 6;
   static final int INT_ARL_DAT_CAB_MOV_INV_FEC_DOC_FAC = 7;
   static final int INT_ARL_DAT_CAB_MOV_INV_NOM_CLI = 8;
   static final int INT_ARL_DAT_CAB_MOV_INV_RUC_CLI = 9;
   static final int INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI = 10;
   static final int INT_ARL_DAT_CAB_MOV_INV_POR_IVA = 11;
   static final int INT_ARL_DAT_CAB_MOV_INV_VAL_SUB = 12;
   static final int INT_ARL_DAT_CAB_MOV_INV_VAL_IVA = 13;
   static final int INT_ARL_DAT_CAB_MOV_INV_VAL_TOT = 14;
   static final int INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL = 15;
   static final int INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL = 16;
   static final int INT_ARL_DAT_CAB_MOV_INV_OBS1 = 17;
   static final int INT_ARL_DAT_CAB_MOV_INV_OBS2 = 18;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_detmovinv
   static final int INT_ARL_DAT_DET_MOV_INV_COD_EMP = 0;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_LOC = 1;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_DOC = 3;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_REG = 4;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_ITM = 5;
   static final int INT_ARL_DAT_DET_MOV_INV_COD_ALT = 6;
   static final int INT_ARL_DAT_DET_MOV_INV_NOM_ITM = 7;
   static final int INT_ARL_DAT_DET_MOV_INV_CAN = 8;
   static final int INT_ARL_DAT_DET_MOV_INV_PRE_UNI = 9;
   static final int INT_ARL_DAT_DET_MOV_INV_SUB_DET = 10;
   static final int INT_ARL_DAT_DET_MOV_INV_POR_DES = 11;
   static final int INT_ARL_DAT_DET_MOV_INV_VAL_DES = 12;
   static final int INT_ARL_DAT_DET_MOV_INV_TOT_DET = 13;
   static final int INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM = 14;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_cabguirem
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_EMP = 0;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_LOC = 1;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_DOC = 3;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_DOC = 4;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_DOC = 5;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_FAC = 6;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC = 7;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC = 8;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA = 9;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA = 10;
   static final int INT_ARL_DAT_CAB_GUI_REM_DIR_PAR = 11;
   static final int INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES = 12;
   static final int INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES = 13;
   static final int INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES = 14;
   static final int INT_ARL_DAT_CAB_GUI_REM_RUC_TRA = 15;
   static final int INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA = 16;
   static final int INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET = 17;
   static final int INT_ARL_DAT_CAB_GUI_REM_CHO_RET = 18;
   static final int INT_ARL_DAT_CAB_GUI_REM_OBS1 = 19;
   static final int INT_ARL_DAT_CAB_GUI_REM_OBS2 = 20;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_detguirem
   static final int INT_ARL_DAT_DET_GUI_REM_COD_EMP = 0;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_LOC = 1;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_DOC = 3;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_REG = 4;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_ITM = 5;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_ALT = 6;
   static final int INT_ARL_DAT_DET_GUI_REM_NOM_ITM = 7;
   static final int INT_ARL_DAT_DET_GUI_REM_CAN = 8;
   //Constantes del arreglo que guarda datos de la cabecera del comprobante de retencion
   static final int INT_ARL_DAT_CAB_CPB_RET_COD_EMP = 0;
   static final int INT_ARL_DAT_CAB_CPB_RET_COD_LOC = 1;
   static final int INT_ARL_DAT_CAB_CPB_RET_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_CAB_CPB_RET_COD_DOC = 3;
   static final int INT_ARL_DAT_CAB_CPB_RET_NUM_DOC = 4;
   static final int INT_ARL_DAT_CAB_CPB_RET_FEC_DOC = 5;
   static final int INT_ARL_DAT_CAB_CPB_RET_TIP_IDE_SUJ_RET = 6;
   static final int INT_ARL_DAT_CAB_CPB_RET_RUC_SUJ_RET = 7;
   static final int INT_ARL_DAT_CAB_CPB_RET_NOM_SUJ_RET = 8;
   static final int INT_ARL_DAT_CAB_CPB_RET_OBS1 = 9;
   static final int INT_ARL_DAT_CAB_CPB_RET_OBS2 = 10;
   //Constantes del arreglo que guarda datos del detalle del comprobante de retencion
   static final int INT_ARL_DAT_DET_CPB_RET_COD_EMP = 0;
   static final int INT_ARL_DAT_DET_CPB_RET_COD_LOC = 1;
   static final int INT_ARL_DAT_DET_CPB_RET_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_DET_CPB_RET_COD_DOC = 3;
   static final int INT_ARL_DAT_DET_CPB_RET_COD_REG = 4;
   static final int INT_ARL_DAT_DET_CPB_RET_BAS_IMP = 5;
   static final int INT_ARL_DAT_DET_CPB_RET_APL_RET = 6;
   static final int INT_ARL_DAT_DET_CPB_RET_POR_RET = 7;
   static final int INT_ARL_DAT_DET_CPB_RET_VAL_RET = 8;
   static final int INT_ARL_DAT_DET_CPB_RET_NUM_SER_FAC = 9;
   static final int INT_ARL_DAT_DET_CPB_RET_NUM_FAC = 10;
   static final int INT_ARL_DAT_DET_CPB_RET_FEC_DOC_FAC = 11;
   static final int INT_ARL_DAT_DET_CPB_RET_COD_SRI = 12;
	//CRIS
     static final int INT_ARL_DAT_DET_CPB_RET_FEC_REG_CON = 13;
     static final int INT_ARL_DAT_DET_CPB_RET_NUM_AUT_SRI = 14;
   //CRIS	 
   //Variables para armar XML de comprobantes electronicos
   int intTipAmb, intTipEmi, intTimEsp, intNumDiaPlaMax;
   private String strConEsp, strNumCpb, strCodNum, strPtoEmi, strCodEst, strCodCpb, strNomEmp, strRucEmp, strDirEmp, strDirLoc, strRazSocCom;
   private String strNomFilFac, strRutNomFilFac, strNomFilGuiRem, strRutNomFilGuiRem, strNomFilNotCre, strRutNomFilNotCre, strNomFilCpbRet, strRutNomFilCpbRet;
   private String strNomFilLiqCom, strRutNomFilLiqCom;
   private String strCorEleCliFacEle, strCorEleCliFacEle2, strRuc, strCodForPagSri, strDesForPagSri, strIpSrvPrd, strNomFil_p12, strClvFil_p12;
   private String strNomCarXmlPenAut, strNomCarXmlSiAut, strNomCarXmlNoAut;
   public boolean blnDirMacSrvPrd_Found;
   public String strEjeProTipAmbSriPru;
   
   public ZafCpbEle(Librerias.ZafParSis.ZafParSis obj, String strPrmNomFil_p12, String strPrmClvFil_p12)
   {
      String strAux, strDirMacSrvPro, strEnvCorTipAmbSriPru;
      StringTokenizer strTokCad;
      
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         this.strNomFil_p12 = strPrmNomFil_p12;
         this.strClvFil_p12 = strPrmClvFil_p12;
         objUti = new ZafUtil();
         oppMsg = new JOptionPane();
         ifruso = new JInternalFrame();
         strIpSrvPrd = "172.16.1.2"; //DBN_real
//         strIpSrvPrd = "172.16.8.7"; //DBN_prueba
         strNomCarXmlPenAut = "Con_PenAut";
         strNomCarXmlSiAut = "Con_SiAut";
         strNomCarXmlNoAut = "Con_NoAut";
         strEjeProTipAmbSriPru = objParSis.getEjecutarProcesoTipoAmbienteSRIPruebas();
         strEnvCorTipAmbSriPru = objParSis.getEnviarCorreoTipoAmbienteSRIPruebas();
         blnDirMacSrvPrd_Found = false;
         strDirMacSrvPro = objParSis.getDireccionMACServidorProduccion();
         strTokCad = new StringTokenizer(strDirMacSrvPro, "|");
         /* JM COMENTE TODO ESTE BLOQUE PARA QUE DE LEY SEA PRUEBAS POR SI ACASO */
////         while (strTokCad.hasMoreElements()){  
////            strAux = strTokCad.nextToken().toString(); //Ej. de strAux: 00-0C-29-12-12-0B
////            if (strAux.lastIndexOf(objParSis.getMACAddress()) != -1){  
////                //Si la MAC Address corresponde al servidor de Produccion, solo en ese caso el Tipo de Ambiente del SRI
////               //para Facturacion electronica sera el de Produccion
               intTipAmb = 2; //Produccion
               blnDirMacSrvPrd_Found = true;
////               break;
////            }
////         }// JM
         
        if (blnDirMacSrvPrd_Found == false){  // JM
            intTipAmb = 1; //Pruebas
            if (strEjeProTipAmbSriPru.equals("N") && strEnvCorTipAmbSriPru.equals("S")){  
            //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI. Por tanto, se procedera a notificar por correo.
               strAux =  "<FONT COLOR=\"blue\">Servicio 11:</FONT> Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion." + "<BR>";
               strAux += "Por tanto, los XML se van a generar y autorizar en Ambiente de Pruebas del SRI." + "<BR>";
               strAux += "Direccion MAC del equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getMACAddress() + "</FONT><BR>";
               strAux += "Equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getNombreComputadoraConDirIP() + "</FONT><BR><BR>";
               strAux += "Notifique esta novedad al Administrador del sistema";
               objNotCorEle = new ZafNotCorEle(objParSis);
               
               if (objNotCorEle.enviarNotificacionCorreoElectronico(0, 1, INT_ARL_COD_CFG_RETENCIONES, strAux)){  
                    //System.out.println("OK INT_ARL_COD_CFG_RETENCIONES");
                  //Todo OK
               }
            }
         }// JM
         
         //System.out.println("MAC Address: " + objParSis.getMACAddress());
         intTipEmi = 1; //Emision normal  // JM 
//         intTipEmi = 9; //DBN_Prueba
      }
      
      catch (CloneNotSupportedException e)
      {  
         mostrarMsgErr(e.getMessage());
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
   
   private class Modulo11
   {
      private int getDigitoControl(String strCad) 
      {
         int i, intFactor, intLngCad, intSumTot, intAux, intResiduo, intRes = -1;
         String strCadInv = "", strAux;

         try
         {
           for (i = strCad.length() - 1; i >= 0; i--) 
              strCadInv = strCadInv + strCad.charAt(i);
         
           intLngCad = strCadInv.length();
           intFactor = 2;
           intSumTot = 0;

           for (i = 0; i < intLngCad; i++)
           {  if (intFactor == 8)
                 intFactor = 2;

              intAux = Integer.parseInt(strCadInv.substring(i, i + 1));
              intAux *= intFactor;
              intFactor++;
              intSumTot += intAux;
           }
          
           intResiduo = intSumTot % 11;
           intAux = 11 - intResiduo;
           
           if (intAux < 10)
              intRes = intAux;
           else if (intAux == 10)
              intRes = 1;
           else if (intAux == 11)
              intRes = 0;
         }
         
         catch(Exception Evt)
         {  intRes = -1;
            //strAux = "Error al ejecutar el algorimo denominado Metodo 11" + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema." + "\n\n";
            //strAux += Evt.getMessage();
            //mostrarMsgErr(strAux);
         }
         
         return intRes;
      }
   } //class Modulo11
   
   private boolean firmarArchToken(String strRutFil, String strNomFil, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux, password, respuestaFirmado, resultado;
      
      try
      {  //Se va a firmar el archivo xml
         //-----------------------------
         password = null;
         //archivosSeleccionados.add(new File(strRutFilFac, strNomFilXml + ".xml"));
         this.tokenSeleccionado = emisor.getToken();

         if ((this.tokenSeleccionado.equals("ANF_TOKEN")) || (System.getProperty("os.name").toUpperCase().indexOf("LINUX") == 0) || (System.getProperty("os.name").toUpperCase().indexOf("MAC") == 0))
         {  //password = FormGenerales.ingresaPassword();
            //password = txtPassword.getText();
         }

         respuestaFirmado = null;
         File archivo = new File(strNomFil);
         //respuestaFirmado = ArchivoUtils.firmarArchivo(this.emisor, archivo.getPath(), strDirectorioFirmados, this.tokenSeleccionado, password);

         respuestaFirmado = ArchivoUtils.firmarArchivo(emisor, archivo.getPath(), strRutFil, this.tokenSeleccionado, password);
         //String tipo = FormGenerales.obtieneTipoDeComprobante(ArchivoUtils.obtenerValorXML(archivo, "/*/infoTributaria/claveAcceso"));
         resultado = (respuestaFirmado == null)? "Firmado" : "Error al firmar";

         if (respuestaFirmado != null)
         {  blnRes = false;
            respuestaFirmado = respuestaFirmado.equals("no match") ? "Contraseña incorrecta" : respuestaFirmado;
            arlRegDetResWS = new ArrayList();
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
            arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al firmar. " + respuestaFirmado);
            arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
            arlResWS.add(arlRegDetResWS);
            //strAux =  "Error al firmar. " + respuestaFirmado + "\n\n";
            //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
            //mostrarMsgErr(strAux);
         }
         else
         {
            //archivo.delete(); //DBN
         }
         //mostrarMsgErr("Mensaje de firma de archivo: " + resultado);
      } //try
   
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al firmar. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         //strAux = "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
         
      return blnRes;
   } //Funcion firmarArchToken()
   
   
   
   private boolean firmarArch_p12(int intCodEmp, String strNomFil, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux="", strClvAcc;
      
      try
      {  //Se va a firmar el archivo xml
         //-----------------------------
         ZafFirma_p12 obj = new ZafFirma_p12(intCodEmp, strNomFil, strNomFil_p12, strClvFil_p12);
         obj.execute();
         //obj.arlDatFirDig.get(0) solo se llenara si se encontro alguna excepcion
         if (!obj.arlDatFirDig.get(0).toString().equals("")){  
            blnRes = false;
            strClvAcc = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_CLV_ACC) == null? "": objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_CLV_ACC);
            arlResWS.clear();
            //Se va a validar que, si viene alguna comilla simple ('), se la deseche para que no haya
            //ningun problema al momento de hacer Update a la tabla para guardar dicha descripcion.
            strAux = quitarCaracterEspecial(obj.arlDatFirDig.get(0).toString(), "'");
            arlRegDetResWS = new ArrayList();
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
            arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, strAux);
            arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
            arlResWS.add(arlRegDetResWS);
         }
         else
         {   
            //mostrarMsgErr("Archivo firmado exitosamente");
         }
      }
   
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al firmar. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         //strAux = "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo firmar el archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
//          System.out.println("firmarArch_p12 ERROR " + e.toString());
//          System.out.println("same clase====>>>> " + arlResWS.toString());
//          System.out.println("same ====>>>> " + strAux);
      }
         
      return blnRes;
   } //Funcion firmarArch_p12()
   //invocar los servicios web
   
   
    private boolean invocarServiciosWeb(String strNomFil, ArrayList arlResWS){
        boolean blnRes = true, blnClvAccReg;
        String strAux, resultado, strFecHor, strEstAut, strNumAut, strFecAut, strEstClvAccReg, strClvAcc, strGetEst;
        ArrayList arlDatAut;
      
        try{  
         //Se va a invocar los Servicios Web del SRI
         //-----------------------------------------
            resultado = "";
            File archivo = new File(strNomFil);
            RespuestaSolicitud respuestaRecepcion = new RespuestaSolicitud();
            String respuestaAutoriz = null;

            String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(archivo, "/*/infoTributaria/claveAcceso");
            String tipoComprobante = ArchivoUtils.obtenerValorXML(archivo, "/*/infoTributaria/codDoc").substring(1);

         if ((tipoComprobante != null) && (claveAccesoComprobante != null))
         {  arlDatAut = new ArrayList();
            arlDatAut.add(0, "");
            arlDatAut.add(1, "");
            arlDatAut.add(2, "");
            String strGetTipoAmbiente = ArchivoUtils.obtenerValorXML(archivo, "/*/infoTributaria/ambiente").substring(0);
            String strDevuelveUrlWs = FormGenerales.devuelveUrlWs(strGetTipoAmbiente, "RecepcionComprobantesOffline");
            intTipAmb = Integer.parseInt(strGetTipoAmbiente);
            
            if (FormGenerales.existConnection(strGetTipoAmbiente, "RecepcionComprobantesOffline") == true)
            {  //Si hay conexion con los Servicios Web del SRI
               respuestaRecepcion = EnvioComprobantesWs.obtenerRespuestaEnvio(archivo, strRuc, tipoComprobante, claveAccesoComprobante, strDevuelveUrlWs);
               strGetEst = respuestaRecepcion.getEstado();
               blnClvAccReg = false;
               
               if (strGetEst.equals("DEVUELTA"))
               {  resultado = FormGenerales.insertarCaracteres(EnvioComprobantesWs.obtenerMensajeRespuesta(respuestaRecepcion), "\n", 160);
                  resultado = quitarCaracterEspecial(resultado, "'");
                  resultado = quitarCaracterEspecial(resultado, "\n");
                  
                  if (resultado.lastIndexOf("CLAVE ACCESO REGISTRADA") != -1)
                  {  strGetEst = "RECIBIDA";
                     blnClvAccReg = true;
                  }
               }
               
               if (strGetEst.equals("RECIBIDA"))
               {  Thread.currentThread();  Thread.sleep(intTimEsp * 1000);
                  //respuestaAutoriz = AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAccesoComprobante, archivo.getName(), strGetTipoAmbiente, arlDatAut);
                  respuestaAutoriz = AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAccesoComprobante, strNomFil, strGetTipoAmbiente, arlDatAut);
                  String estado = null;
                  if (respuestaAutoriz.lastIndexOf("|") != -1)
                  {  estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                     resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1, respuestaAutoriz.length());
                     //resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 2, respuestaAutoriz.length()); //No considera "\n" al inicio de la cadena extraida
                  }
                  else
                  {  estado = respuestaAutoriz;
                     
                     if (estado.lastIndexOf("AUTORIZADO") != -1)
                        resultado = "Procesado";
                     else
                        resultado = "El documento no fue procesado";
                  }
                  
                  //Se va a validar que, si viene alguna comilla simple ('), se la deseche para que no haya
                  //ningun problema al momento de hacer Update a la tabla para guardar dicha descripcion.
                  resultado = quitarCaracterEspecial(resultado, "'");
                  resultado = quitarCaracterEspecial(resultado, "\n");
                  
                  if (respuestaAutoriz.equals("AUTORIZADO"))
                  {  //JOptionPane.showMessageDialog(this, "El comprobante fue autorizado por el SRI", "Respuesta", 1);
                     if (blnClvAccReg == true)
                        resultado = "Procesado. Nota: El Servicio Web devolvio el mensaje <CLAVE ACCESO REGISTRADA>";
                     
                     arlRegDetResWS = new ArrayList();
                     arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "A");
                     arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, resultado);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, arlDatAut.get(0));
                     arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, arlDatAut.get(1));
                     arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, arlDatAut.get(2));
                     arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, claveAccesoComprobante);
                     arlResWS.add(arlRegDetResWS);
                     //archivo.delete();
                  }
                  else
                  {  //No fue autorizado
                     //JOptionPane.showMessageDialog(this, "El comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado\n" + estado + "\n" + resultado, "Respuesta", 1);
                     blnRes = false;
                     strEstAut = "E";
                     strNumAut = "";
                     strFecAut = "";
                     strEstClvAccReg = "N";
                     strClvAcc = "";
                     
                     //DBN_Prueba inicio
                     /*blnRes = true;
                     strEstAut = "A";
                     strFecAut = "2014-12-11";*/
                     //DBN_Prueba fin
                     
                     if (arlDatAut.get(2) == "S")
                     {  //Indice 2: Indica que el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". Por tanto, se va a proceder
                        //a obtener la fecha y numero de autorizacion, ademas de la clave de acceso del comprobante.
                        blnRes = true;
                        strEstAut = "A";
                        strNumAut = arlDatAut.get(0).toString();
                        strFecAut = arlDatAut.get(1).toString();
                        strEstClvAccReg = arlDatAut.get(2).toString();
                        strClvAcc = claveAccesoComprobante;
                        resultado = "Procesado. Nota: El Servicio Web devolvio el mensaje <CLAVE ACCESO REGISTRADA>";
                     }
                     arlRegDetResWS = new ArrayList();
                     arlRegDetResWS.add(INT_ARL_DAT_RES_EST, strEstAut);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, resultado);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, strNumAut);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, strFecAut);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, strEstClvAccReg);
                     arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                     arlResWS.add(arlRegDetResWS);
                     //strAux = "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
                     //mostrarMsgErr(strAux);
                  }
               }
               else if (strGetEst.equals("DEVUELTA"))
               {  blnRes = false;
                  resultado = FormGenerales.insertarCaracteres(EnvioComprobantesWs.obtenerMensajeRespuesta(respuestaRecepcion), "\n", 160);
                  //Se va a validar que, si viene alguna comilla simple ('), se la deseche para que no haya
                  //ningun problema al momento de hacer Update a la tabla para guardar dicha descripcion.
                  resultado = quitarCaracterEspecial(resultado, "'");
                  resultado = quitarCaracterEspecial(resultado, "\n");
                  blnRes = false;
                  strEstAut = "X";
                  strNumAut = "";
                  strFecAut = "";
                  strEstClvAccReg = "N";
                  strClvAcc = "";
                  
                  //DBN_Prueba inicio
                  /*blnRes = true;
                  strEstAut = "A";
                  strFecAut = "2015-01-02";*/
                  //DBN_Prueba fin
                  
                  if (arlDatAut.get(2) == "S")
                  {  //Indice 2: Indica que el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". Por tanto, se va a proceder
                     //a obtener la fecha y numero de autorizacion, ademas de la clave de acceso del comprobante.
                     blnRes = true;
                     strEstAut = "A";
                     strNumAut = arlDatAut.get(0).toString();
                     strFecAut = arlDatAut.get(1).toString();
                     strEstClvAccReg = arlDatAut.get(2).toString();
                     strClvAcc = claveAccesoComprobante;
                     resultado = "Procesado. Nota: El Servicio Web devolvio el mensaje <CLAVE ACCESO REGISTRADA>";
                  }
                  arlRegDetResWS = new ArrayList();
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST, strEstAut);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, resultado);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, strNumAut);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, strFecAut);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, strEstClvAccReg);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                  arlResWS.add(arlRegDetResWS);
                  //strAux =  "Error al tratar de enviar el comprobante hacia el SRI." + "\n";
                  //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
                  //mostrarMsgErr(strAux);
               } //if (strGetEst.equals("RECIBIDA"))
               else
               {  blnRes = false;
                  //Se va a validar que, si viene alguna comilla simple ('), se la deseche para que no haya
                  //ningun problema al momento de hacer Update a la tabla para guardar dicha descripcion.
                  resultado = quitarCaracterEspecial(respuestaRecepcion.getEstado(), "'");
                  resultado = quitarCaracterEspecial(resultado, "\n");
                  arlRegDetResWS = new ArrayList();
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "X");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, resultado);
                  arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
                  arlResWS.add(arlRegDetResWS);
                  //strAux =  "Error al tratar de enviar el comprobante hacia el SRI." + "\n";
                  //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
                  //mostrarMsgErr(strAux);
               }
            } //if (FormGenerales.existConnection(strGetTipoAmbiente, "RecepcionComprobantesOffline") == true)
            else
            {  //No hay conexion con los Servicios Web del SRI
               blnRes = false;
               strFecHor = getFechaHoraPC();
               strAux = "No hay conexion con los Servicios Web del SRI.";
               
               if (!strFecHor.equals(""))
                  strAux += " Esto ocurrió el " + strFecHor;
               
               arlRegDetResWS = new ArrayList();
               arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
               arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, strAux);
               arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
               arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
               arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
               arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
               arlResWS.add(arlRegDetResWS);
            } //if (FormGenerales.existConnection(strGetTipoAmbiente, "RecepcionComprobantesOffline") == true)
         }
         else
         {  blnRes = false;
            strAux =  "Error al tratar de enviar el comprobante hacia el SRI. ";
            strAux += "En: " + archivo.getName() + " la información <codDoc> y <claveAcceso> son obligatorias para el envio del archivo";
            arlRegDetResWS = new ArrayList();
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "X");
            arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, strAux);
            arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
            arlResWS.add(arlRegDetResWS);
            //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
            //mostrarMsgErr(strAux);
         } //if ((tipoComprobante != null) && (claveAccesoComprobante != null))
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al invocar el Servicio Web. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         //strAux = "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
         
      return blnRes;
   } //Funcion invocarServiciosWeb()
   
   private String getFechaHoraPC()
   {
      String strRes = "", strMes, strDia, strHor, strMin, strSeg;
      int intYear, intMes, intDia, intHor, intMin, intSeg;
       
      try
      {
         Calendar objCal = new GregorianCalendar();
         intYear = objCal.get(Calendar.YEAR);
         intMes = objCal.get(Calendar.MONTH) + 1;
         intDia = objCal.get(Calendar.DAY_OF_MONTH);
         intHor = objCal.get(Calendar.HOUR_OF_DAY);
         intMin = objCal.get(Calendar.MINUTE);
         intSeg = objCal.get(Calendar.SECOND);
         strMes = String.format("%02d", intMes);
         strDia = String.format("%02d", intDia);
         strHor = String.format("%02d", intHor);
         strMin = String.format("%02d", intMin);
         strSeg = String.format("%02d", intSeg);
         strRes = strDia + "/" + strMes + "/" + intYear + " " + strHor + ":" + strMin + ":" + strSeg;
      }
       
      catch(Exception e)
      {
         strRes = "";
      }
    
      return strRes;
   }
   
   private String quitarCaracterEspecial(String strCad, String strCarEsp)
   {
      String strRes = "", strAux;
      StringTokenizer strTokCad;
      ArrayList<String> arlLista = new ArrayList<String>();
      int i;
      
      try
      {
         //Se va a validar que, si el parametro 'strCad' viene algun caracter especial, se la deseche para que no haya ningun
         //problema al momento de hacer Update a la tabla para guardar dicha descripcion.
         strTokCad = new StringTokenizer(strCad, strCarEsp);
         
         while (strTokCad.hasMoreElements())
         {  strAux = strTokCad.nextToken().toString();
            arlLista.add(strAux);
            //System.out.println(strAux);
         }

         if (arlLista.size() > 0)
         {  strAux = "";
            
            for (i = 0; i < arlLista.size(); i++)
            {  strAux += arlLista.get(i);
               if (strCarEsp.equals("\n") && i < arlLista.size() - 1)
                  strAux += ". ";
            }
            
            strRes = strAux;
         }
         else
            strRes = strCad; //No se presento ningun caracter especial en el parametro recibido
      }
       
      catch(Exception e)
      {
         strRes = "Error en la funcion quitarCaracterEspecial. " + e.toString();
      }
    
      return strRes;
   } //Funcion quitarCaracterEspecial()
   
   /**
    * Esta funcion se encarga de reemplazar un caracter por otro en una cadena dada.
    * @param strCad Cadena donde se desea reemplazar el caracter.
    * @param chrCarBsq Caracter a buscar dentro de la cadena.
    * @param strCarRpl Caracter que reemplazara el caracter buscado.
    * @return La nueva cadena con el caracter reemplazado.
   */
   private String reemplazarCaracter(String strCad, char chrCarBsq, String strCarRpl)
   {
      String strRes = "", strAux;
      char[] arrCad;
      int i;
      
      try
      {
         strAux = "";
         arrCad = strCad.toCharArray();
         
         for (i = 0; i < arrCad.length; i++)
         {  if (arrCad[i] == chrCarBsq)
               strAux += strCarRpl;
            else
               strAux += arrCad[i];
         }
         
         strRes = strAux;
      }
       
      catch(Exception e)
      {
         strRes = strCad;
      }
    
      return strRes;
   } //Funcion reemplazarCaracter()
   
   private boolean obtenerDatosTagXML(Connection con, int intCodEmp, int intCodLoc, ArrayList arlResWS)
   {
       boolean blnRes = true;
       java.sql.Statement stm;
       java.sql.ResultSet rst;
       
       try
       {    strConEsp = "";

            if (con != null)
            {   stm = con.createStatement();
                strNomEmp = "";
                strRucEmp = "";
                strSQL =  "SELECT     a1.tx_nom as tx_nomEmp, a1.tx_ruc as tx_rucEmp, a1.tx_dir as tx_dirEmp, a1.ne_numResConEsp, a2.tx_dir as tx_dirLoc, a2.tx_secdoc ";
                strSQL += "FROM       tbm_emp as a1 ";
                strSQL += "INNER JOIN tbm_loc as a2 ON a1.co_emp = a2.co_emp ";
                strSQL += "WHERE      a1.co_emp = " + intCodEmp;
                strSQL += "           and a2.co_loc = " + intCodLoc;
                rst = stm.executeQuery(strSQL);
                
                if (rst.next())
                {  strNomEmp = rst.getString("tx_nomEmp");
                   strRucEmp = rst.getString("tx_rucEmp");
                   strDirEmp = rst.getString("tx_dirEmp");
                   strConEsp = rst.getString("ne_numResConEsp") == null? "" :rst.getString("ne_numResConEsp");
                   strDirLoc = rst.getString("tx_dirLoc");
                   //Ej. valor de campo tx_secdoc: 005-001
                   strCodEst = rst.getString("tx_secdoc").substring(0, 3); //005
                   strPtoEmi = rst.getString("tx_secdoc").substring(4, 7); //001
                }

                stm.close();
                stm = null;
                rst.close();
                rst = null;
            }
       } //try
        
       catch(Exception e)
       {  blnRes = false;
          arlRegDetResWS = new ArrayList();
          arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
          arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener los datos para el archivo XML. " + e.getMessage());
          arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
          arlResWS.add(arlRegDetResWS);
          //strAux = "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema." + "\n\n";
          //strAux += e.getMessage();
          //mostrarMsgErr(strAux);
       }
    
       return blnRes;
   } //Funcion obtenerDatosTagXML()
   
   private boolean armarXMLFact_OffLine(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
        boolean blnRes = true, blnFound, blnFacTieIva;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabFac, arlDatCabFac, arlRegDetFac, arlDatDetFac;
        String strAux, strNomFilXml, strXml, strClvAcc, strTipIdeCom, strFecEmi, strIdeCom;
        String strFecVen="";
        String strObs1, strDD, strMM, strAAAA, strHor, strMin, strSeg, strRucCli, strNumGuiRem, strNumPed;
        int i, intDigCtr, intCodPorIva, intHor, intMin, intSeg, intCodCli;
        long lngNumCpb, lngCodNum;
        Date datFecEmiFac, datFecIniFacIva14;
        BigDecimal bdeAux, bdeTotSinImp, bdeTotDes, bdeBasImpIva, bdePorIva, bdeSubDet, bdeValIvaDet, bdeSubFac, bdeValIvaFac, bdeTotFac;
        BigDecimal bdePorComSol, bdeValComSol;
        PrintWriter xml;

        try
        {  if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
                {   stm = con.createStatement();
                    intCodCli = 0;
                    strRucCli = "";
                    strNumGuiRem = "";
                    strNumPed = "";
                    datFecEmiFac = objUti.parseDate("1900/01/01", "yyyy/MM/dd");
                    strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                    strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 3"; //3: Facturas de ventas
                    rst = stm.executeQuery(strSQL);

                    if (rst.next())
                        strCodCpb = rst.getString("tx_codCatFacEle");
                  
                  
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_ven  \n";
                    strSQL+=" FROM tbm_pagMovInv as a1 \n";
                    strSQL+=" WHERE a1.nd_porRet = 0 and a1.co_emp="+intCodEmp+" and a1.co_loc="+intCodLoc+" and a1.co_tipDOc="+intCodTipDoc+" and a1.co_doc="+intCodDoc+"  \n";
                    strSQL+=" ORDER BY a1.fe_ven ASC LIMIT 1  \n";
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()){
                        strFecVen = objUti.formatearFecha(rst.getDate("fe_ven"), "dd/MM/yyyy");
                    }
                     
                    arlDatCabFac = new ArrayList();
                    strSQL =  "SELECT    a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.co_cli, a1.tx_nomcli, a1.tx_ruc as tx_rucCli, a2.tx_tipide as tx_tipidecli, a1.nd_poriva, abs(a1.nd_sub) as nd_sub, abs(a1.nd_valiva) as nd_valiva, abs(a1.nd_tot) as nd_tot, a1.nd_porComSol, abs(a1.nd_valComSol) as nd_valComSol, a1.tx_obs1, a1.tx_obs2, a1.tx_numguirem, a1.tx_numped ";
                    strSQL += "FROM      tbm_cabmovinv as a1 ";
                    strSQL += "LEFT JOIN tbm_cli as a2 on a2.co_emp = a1.co_emp and a2.co_cli = a1.co_cli ";
                    strSQL += "WHERE     a1.co_emp = " + intCodEmp;
                    strSQL += "          and a1.co_loc = " + intCodLoc;
                    strSQL += "          and a1.co_tipdoc = " + intCodTipDoc;
                    strSQL += "          and a1.co_doc = " + intCodDoc;
                    rst = stm.executeQuery(strSQL);

                  if (rst.next())
                  {  arlRegCabFac = new ArrayList();
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_NUM_DOC, "" + rst.getInt("ne_numdoc"));
                     strAux = objUti.formatearFecha(rst.getDate("fe_doc"), "yyyy/MM/dd"); //Se pone en el formato yyyy/MM/dd para la conversion a tipo Date
                     datFecEmiFac = objUti.parseDate(strAux, "yyyy/MM/dd");
                     strAux = objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy");
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC, "" + strAux);
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_NUM_FAC, "");
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC_FAC, "");
                     intCodCli = rst.getInt("co_cli");
                     strAux = reemplazarCaracter(rst.getString("tx_nomcli"), '&', "&amp;");
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_NOM_CLI, strAux);
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_RUC_CLI, rst.getString("tx_ruccli"));
                     strRucCli = rst.getString("tx_ruccli");
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI, rst.getString("tx_tipidecli"));
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva"), objParSis.getDecimalesMostrar());
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_POR_IVA, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_sub"), objParSis.getDecimalesMostrar());
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_VAL_SUB, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_valiva"), objParSis.getDecimalesMostrar());
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_VAL_IVA, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_tot"), objParSis.getDecimalesMostrar());
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_VAL_TOT, "" + bdeAux);
                     
                     strAux = rst.getString("nd_porComSol") == null? "" :rst.getString("nd_porComSol");
                     
                     if (strAux.equals(""))
                        bdeAux = new BigDecimal("0.00");
                     else
                     {  bdeAux = new BigDecimal(strAux);
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                     }
                     
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL, "" + bdeAux);
                     
                     strAux = rst.getString("nd_valComSol") == null? "" :rst.getString("nd_valComSol");
                     
                     if (strAux.equals(""))
                        bdeAux = new BigDecimal("0.00");
                     else
                     {  bdeAux = new BigDecimal(strAux);
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                     }
                     
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL, "" + bdeAux);
                     
                     strNumGuiRem = rst.getString("tx_numguirem") == null? "" :rst.getString("tx_numguirem");
                     strNumPed = rst.getString("tx_numped") == null? "" :rst.getString("tx_numped");
                     
                     strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                     if (!strAux.equals(""))
                     {  strAux = reemplazarCaracter(strAux, '\n', " ");
                        strAux = reemplazarCaracter(strAux, '&', "&amp;");
                        strAux = reemplazarCaracter(strAux, '<', "&lt;");
                        strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     }
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_OBS1, strAux);
                     
                     strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                     if (!strAux.equals(""))
                     {  strAux = reemplazarCaracter(strAux, '\n', " ");
                        strAux = reemplazarCaracter(strAux, '&', "&amp;");
                        strAux = reemplazarCaracter(strAux, '<', "&lt;");
                        strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     }
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_OBS2, strAux);
                     
                     arlDatCabFac.add(arlRegCabFac);
                  }

                  arlDatDetFac = new ArrayList();
                  strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt, tx_nomitm, abs(nd_can) as nd_can, nd_preuni, round((abs(nd_can) * nd_preuni), 2) as nd_subtotdet, ";
                  strSQL += "nd_pordes, round((abs(nd_can) * nd_preuni * nd_pordes / 100), 2) as nd_valdes, abs(nd_tot) as nd_totdet, st_ivacom ";
                  strSQL += "FROM   tbm_detmovinv ";
                  strSQL += "WHERE  co_emp = " + intCodEmp;
                  strSQL += "       and co_loc = " + intCodLoc;
                  strSQL += "       and co_tipdoc = " + intCodTipDoc;
                  strSQL += "       and co_doc = " + intCodDoc;
                  rst = stm.executeQuery(strSQL);

                  while (rst.next())
                  {  arlRegDetFac = new ArrayList();
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_REG, "" + rst.getInt("co_reg"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_ITM, "" + rst.getInt("co_itm"));
                     
                     strAux = rst.getString("tx_codalt");
                     strAux = reemplazarCaracter(strAux, '&', "&amp;");
                     strAux = reemplazarCaracter(strAux, '<', "&lt;");
                     strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_COD_ALT, strAux);
                     
                     strAux = rst.getString("tx_nomitm");
                     strAux = reemplazarCaracter(strAux, '&', "&amp;");
                     strAux = reemplazarCaracter(strAux, '<', "&lt;");
                     strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_NOM_ITM, strAux);
                     
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesMostrar());
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_CAN, "" + bdeAux);
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_PRE_UNI, "" + rst.getBigDecimal("nd_preuni"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_SUB_DET, "" + rst.getBigDecimal("nd_subtotdet"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_POR_DES, "" + rst.getBigDecimal("nd_pordes"));
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_VAL_DES, "" + rst.getBigDecimal("nd_valdes"));
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_totdet"), objParSis.getDecimalesMostrar());
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_TOT_DET, "" + bdeAux);
                     arlRegDetFac.add(INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM, rst.getString("st_ivacom"));
                     arlDatDetFac.add(arlRegDetFac);    
                  }

                  strFecEmi = "";
                  strDD = "";
                  strMM = "";
                  strAAAA = "";
                  strHor = "";
                  strMin = "";
                  strSeg = "";
                  strIdeCom = "";
                  strTipIdeCom = ""; //Tipo de identificacion del comprador
                  strRazSocCom = ""; //Razon social del comprador
                  strAux = "";
                  strCodNum = ""; //Codigo numerico (es secuencial)
                  strNumCpb = ""; //Numero de comprobante
                  strClvAcc = "";
                  strCorEleCliFacEle = "";
                  strCorEleCliFacEle2 = "";
                  bdePorIva = new BigDecimal("0");
                  bdeSubFac = new BigDecimal("0");
                  bdeValIvaFac = new BigDecimal("0");
                  bdeTotFac = new BigDecimal("0");
                  bdePorComSol = new BigDecimal("0");
                  bdeValComSol = new BigDecimal("0.00");

                  if (arlDatCabFac.size() > 0)
                  {  strAux = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_COD_DOC);
                     lngCodNum = Long.parseLong(strAux);
                     strCodNum = String.format("%08d", lngCodNum); // => "00000001"
                     strAux = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_NUM_DOC);
                     lngNumCpb = Long.parseLong(strAux);
                     strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                     //strNumCpb = "000000002"; // => "000000001" //DBN_Prueba
                     strFecEmi = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_FEC_DOC);
                     //strFecEmi = "12/12/2014"; //DBN_Prueba
                     strDD = strFecEmi.substring(0, 2);
                     strMM = strFecEmi.substring(3, 5);
                     strAAAA = strFecEmi.substring(6, 10);
                     Calendar objCal = new GregorianCalendar();
                     intHor = objCal.get(Calendar.HOUR_OF_DAY);
                     intMin = objCal.get(Calendar.MINUTE);
                     intSeg = objCal.get(Calendar.SECOND);
                     strHor = String.format("%02d", intHor);
                     strMin = String.format("%02d", intMin);
                     strSeg = String.format("%02d", intSeg);
                     strIdeCom = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_RUC_CLI);
                     //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                     buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                     strRazSocCom = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_NOM_CLI);
                     strTipIdeCom = "00"; //00: Valor invalido para la etiqueta del archivo xml
                     strAux = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI);
                     
                     if (strAux.equals("R"))
                        strTipIdeCom = "04"; //Venta con RUC
                     else if (strAux.equals("C"))
                        strTipIdeCom = "05"; //Venta con cedula
                     else if (strAux.equals("P"))
                        strTipIdeCom = "06"; //Venta con pasaporte
                     else if (strAux.equals("F"))
                        strTipIdeCom = "07"; //Venta a consumidor final
                     else if (strAux.equals("E"))
                        strTipIdeCom = "08"; //Identificacion del exterior
                     
                     //El valor de strTipIdeCom (Tipo de identificacion del comprador) esta quemado.
                     //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                     
                     bdePorIva = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_POR_IVA);
                     bdeSubFac = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_SUB);
                     bdeValIvaFac = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_IVA);
                     bdeValIvaFac = objUti.redondearBigDecimal(bdeValIvaFac, objParSis.getDecimalesMostrar());
                     bdeTotFac = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_TOT);
                     bdePorComSol = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL);
                     bdeValComSol = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL);

                     strAux =  strDD + strMM + strAAAA;
                     strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                     strClvAcc = strAux;
                     intDigCtr = objMod11.getDigitoControl(strAux);
                     strClvAcc += intDigCtr;
                  } //if (arlDatCabFac.size() > 0)
                  
                  //La funcion getCodForPag() se encarga de llenar las variables globales strCodForPagSri, strDesForPagSri y intNumDiaPlaMax
                  getCodForPag(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

                  //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                  //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                  strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                  strNomFilXml += "Factura_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                  strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                  strNomFilFac = strNomFilXml;
                  
                  xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                  strRutNomFilFac = strRutFil + File.separator + strNomFilXml + ".xml";

                  strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
                  strXml += "<factura id=\"comprobante\" version=\"1.1.0\"> ";
                  strXml += "<infoTributaria> ";
                  strXml += "<ambiente>" + intTipAmb + "</ambiente> ";
                  strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision> ";
                  strXml += "<razonSocial>" + strNomEmp + "</razonSocial> ";
                 strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";

                 /* JM: Se reversa el cambio 4/Nov/2020 */
//                  if( (intCodEmp==1) && (intCodLoc==13 || intCodLoc==14 || intCodLoc==15 || intCodLoc==16 || intCodLoc==17 || intCodLoc==18 || intCodLoc==23 || intCodLoc==24 || intCodLoc==25 || intCodLoc==26) ){
//                      strXml += "<nombreComercial>" + "CASTEK S.A." + "</nombreComercial> ";
//                  }
//                  else if ( (intCodEmp==1) && (intCodLoc==19 || intCodLoc==20 || intCodLoc==21 || intCodLoc==22  ) ){
//                      strXml += "<nombreComercial>" + "DIMULTI S.A." + "</nombreComercial> ";
//                  }
//                  else{
//                      strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                  }

                  strXml += "<ruc>" + strRucEmp + "</ruc> ";
                  strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso> ";
                  strXml += "<codDoc>" + strCodCpb + "</codDoc> ";
                  strXml += "<estab>" + strCodEst + "</estab> ";
                  strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi> ";
                  strXml += "<secuencial>" + strNumCpb + "</secuencial> ";
                  strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz> ";
                  strXml += "</infoTributaria> ";

                  strXml += "<infoFactura> ";
                  strXml += "<fechaEmision>" + strFecEmi + "</fechaEmision> ";
                  strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento> ";
                  
                  if (!strConEsp.equals(""))
                  {
                     strXml += "<contribuyenteEspecial>" + strConEsp + "</contribuyenteEspecial> ";
                  }
                  
                  strXml += "<obligadoContabilidad>SI</obligadoContabilidad> "; //En algun momento se sugiere reemplazar este codigo quemado
                  strXml += "<tipoIdentificacionComprador>" + strTipIdeCom + "</tipoIdentificacionComprador> ";
                  strXml += "<razonSocialComprador>" + strRazSocCom + "</razonSocialComprador> ";
                  strXml += "<identificacionComprador>" + strIdeCom + "</identificacionComprador> ";

                  bdeTotSinImp = new BigDecimal("0");
                  bdeTotDes = new BigDecimal("0");
                  bdeBasImpIva = new BigDecimal("0");
                  blnFacTieIva = false;

                  for (i = 0; i < arlDatDetFac.size(); i++)
                  {  bdeAux = objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                     bdeTotSinImp = bdeTotSinImp.add(bdeAux);
                     
                     if (objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  blnFacTieIva = true;
                        bdeBasImpIva = bdeBasImpIva.add(bdeAux);
                     }
                     
                     bdeAux = objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_VAL_DES);
                     bdeTotDes = bdeTotDes.add(bdeAux);
                  }

                  bdeTotSinImp = objUti.redondearBigDecimal(bdeTotSinImp, objParSis.getDecimalesMostrar());
                  bdeTotDes = objUti.redondearBigDecimal(bdeTotDes, objParSis.getDecimalesMostrar());
                  bdeBasImpIva = objUti.redondearBigDecimal(bdeBasImpIva, objParSis.getDecimalesMostrar());
                  strXml += "<totalSinImpuestos>" + bdeTotSinImp + "</totalSinImpuestos> ";
                  strXml += "<totalDescuento>" + bdeTotDes + "</totalDescuento>";

                  intCodPorIva = 6; //Se da este valor inicial cuyo significado es "No Objeto de Impuesto" segun el PDF del SRI

                  if (bdePorIva.equals(new BigDecimal("0.00")))
                     intCodPorIva = 0; //Valor especificado en el PDF del SRI
                  else if (bdePorIva.equals(new BigDecimal("12.00")))
                     intCodPorIva = 2; //Valor especificado en el PDF del SRI
                  else if (bdePorIva.equals(new BigDecimal("14.00")))
                     intCodPorIva = 3; //Valor especificado en el PDF del SRI
                  
                  //En intCodPorIva, en algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI

                  strXml += "<totalConImpuestos> ";
                  strXml +=   "<totalImpuesto> ";
                  strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado
                  
                  if (! bdeValIvaFac.equals(new BigDecimal("0.00")))
                     strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                  else
                     strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> ";
                  
                  if (blnFacTieIva == true)
                     strXml +=   "<baseImponible>" + bdeBasImpIva + "</baseImponible> ";
                  else
                     strXml +=   "<baseImponible>" + bdeSubFac + "</baseImponible> ";
                  
                  strXml +=   "<valor>" + bdeValIvaFac + "</valor> ";
                  strXml +=   "</totalImpuesto> ";
                  strXml += "</totalConImpuestos> ";
                  
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     strXml += "<compensaciones> ";
                     strXml +=   "<compensacion>";
                     strXml +=     "<codigo>1</codigo>"; //Segun PDF del SRI, el valor de 1 corresponde a "Descuento solidario". En algun momento se sugiere reemplazar este codigo quemado.
                     strXml +=     "<tarifa>" + bdePorComSol + "</tarifa>"; //Segun PDF del SRI, este valor corresponde al porcentaje de compensacion.
                     strXml +=     "<valor>" + bdeValComSol + "</valor>"; //Segun PDF del SRI, esta cifra corresponde al valor de compensacion.
                     strXml +=   "</compensacion>";
                     strXml += "</compensaciones>";
                  }

                  strXml += "<propina>0.00</propina> ";
                  
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     bdeAux = bdeTotFac.subtract(bdeValComSol);
                     strXml += "<importeTotal>" + bdeAux + "</importeTotal> ";
                  }
                  else
                  {  
                     strXml += "<importeTotal>" + bdeTotFac + "</importeTotal> ";
                  }
                  
                  strXml += "<moneda>DOLAR</moneda> "; //En algun momento se sugiere reemplazar este codigo quemado
                  
                  strXml += "<pagos>";
                  strXml +=   "<pago>";
                  strXml +=   "<formaPago>" + strCodForPagSri + "</formaPago>";
                  
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     bdeAux = bdeTotFac.subtract(bdeValComSol);
                     strXml += "<total>" + bdeAux + "</total>";
                  }
                  else
                  {  
                     strXml += "<total>" + bdeTotFac + "</total>";
                  }
                  
                  strXml +=   "<plazo>" + intNumDiaPlaMax + "</plazo>";
                  strXml +=   "<unidadTiempo>dias</unidadTiempo>";
                  strXml +=   "</pago>";
                  strXml += "</pagos>";
                  
                  strXml += "</infoFactura> ";

                  strXml += "<detalles> ";

                  for (i = 0; i < arlDatDetFac.size(); i++)
                  {  strXml += "<detalle> ";
                     strXml += "<codigoPrincipal>" + objUti.getIntValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_COD_ITM) + "</codigoPrincipal> ";
                     strXml += "<codigoAuxiliar>" + objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_COD_ALT) + "</codigoAuxiliar> ";
                     strXml += "<descripcion>" + objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_NOM_ITM) + "</descripcion> ";
                     strXml += "<cantidad>" + objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_CAN) + "</cantidad> ";
                     strXml += "<precioUnitario>" + objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_PRE_UNI) + "</precioUnitario> ";
                     strXml += "<descuento>" + objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_VAL_DES) + "</descuento> ";
                     strXml += "<precioTotalSinImpuesto>" + objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</precioTotalSinImpuesto> ";

                     bdeValIvaDet = new BigDecimal("0");

                     if (objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  bdeSubDet = objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                        bdeValIvaDet = objUti.redondearBigDecimal(bdeSubDet.multiply(bdePorIva), objParSis.getDecimalesMostrar());
                        bdeValIvaDet = bdeValIvaDet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                     }

                     strXml += "<impuestos> ";
                     strXml +=   "<impuesto> ";
                     strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado
                     
                     if (objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                        strXml +=   "<tarifa>" + bdePorIva + "</tarifa> ";
                     }
                     else
                     {  strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> "; //Segun PDF del SRI, el valor de 0 se refiete a que no tiene IVA. En algun momento se sugiere reemplazar este codigo quemado
                        strXml +=   "<tarifa>0</tarifa> "; //0 se refiere a 0% de IVA
                     }
                     
                     strXml +=   "<baseImponible>" + objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</baseImponible> ";
                     strXml +=   "<valor>" + bdeValIvaDet + "</valor> ";
                     strXml +=   "</impuesto> ";
                     strXml += "</impuestos> ";
                     strXml += "</detalle> ";
                  }

                  strXml += "</detalles> ";
                  blnFound = false;
                  strObs1 = "";
                  //strObs2 = "";

                  if (arlDatCabFac.size() > 0)
                  {  strObs1 = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_OBS1);
                     //strObs2 = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_OBS2);
                     strObs1 = strObs1.trim();
                     //strObs2 = strObs2.trim();
                  }
                  
                  datFecIniFacIva14 = objUti.parseDate("2016/06/01", "yyyy/MM/dd"); //Fecha de incio de Facturacion con IVA 14%

                  if (!strObs1.equals("") || !strNumPed.equals(""))
                  {  strXml += "<infoAdicional> ";

                     if (!strObs1.equals(""))
                     {  if (strObs1.length() > 300)
                        {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                           //va a haber un error al momento de enviar el XML al SRI.
                           strObs1 = strObs1.substring(0, 300);
                        }
                        strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional> ";
                     }
                     
                     //*** Inicio bloque comentado el 15/Oct/2018 ***
                     /*
                     if (!strObs2.equals(""))
                        strXml += "<campoAdicional nombre=\"Observacion2\">" + strObs2 + "</campoAdicional> ";
                     */
                     //*** Fin bloque comentado el 15/Oct/2018 ******
 
                     if (!strNumPed.equals(""))
                        strXml += "<campoAdicional nombre=\"PedidoCompra\">" + strNumPed + "</campoAdicional> ";

                     if (( (strRucCli.equals("0990293244001")) || /* HOLCIM */
                           (strRucCli.equals("1791924037001")) ) /*  AVICOLA SAN ISIDRO S.A. AVISID  */ && 
                             !strNumGuiRem.equals(""))
                     {  //Si el RUC es de Holcim Ecuador (0990293244001) y strNumGuiRem <> "", entonces se debe poner el num. de guia de remision
                        strXml += "<campoAdicional nombre=\"GuiaRemision\">" + strNumGuiRem + "</campoAdicional> ";
                     }
                     
                     /* JM: 24/Jun/2020 */
                     if (!strFecVen.equals(""))
                        strXml += "<campoAdicional nombre=\"FechaVencimiento\">" + strFecVen + "</campoAdicional> ";
                     /* JM: 24/Jun/2020 */

                     strXml += "</infoAdicional> ";
                  }

                  strXml += "</factura>";
                  xml.println(strXml);
                  
                  arlRegDetResWS = new ArrayList();
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                  arlResWS.add(arlRegDetResWS);

                  rst.close();
                  rst = null;
                  stm.close();
                  stm = null;
                  xml.println();
                  xml.close();
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;
   } //Funcion armarXMLFact_OffLine()
   
   public boolean getNumAutorizacionFac(Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux, strAux2, strRutFilFac, strEst, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut, strPK, strClvAccCpb;
      int intCodCli;
      File carpeta;
      
      try
      {  arlResWS.clear();
         emisor = new Emisor();
         strRutFilFac = "";
         strEst = "";
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFilFac = "/Zafiro/Digitales/Ventas/ZafVen02/" + strPrmNomEmp + "/" + strNomCarXmlPenAut;
         else
            strRutFilFac = "C:\\Zafiro\\Digitales\\Ventas\\ZafVen02\\" + strPrmNomEmp + "\\" + strNomCarXmlPenAut;
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de Fact: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         
         strEst = getEstAutFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
         
         if (strEst.equals("S")){  
            //Se va a solicitar la autorizacion en linea
            strRutXmlPenAut = "";
            strRutXmlSiAut = "";
            strRutXmlNoAut = "";
         
            //DBN_Prueba
            /*
            
            if (! System.getProperty("os.name").equals("Linux"))
            {  strRutXmlPenAut = "//" + strIpSrvPrd;
               strRutXmlSiAut = "//" + strIpSrvPrd;
               strRutXmlNoAut = "//" + strIpSrvPrd;
            }
            
            */

            strNomFilFac = "";
            strRutXmlTipCpb = "/Zafiro/Digitales/Ventas/ZafVen02/"; //DBN_real
//            strRutXmlTipCpb = "C:/Zafiro/Digitales/Ventas/ZafVen02/"; //DBN_prueba
            strRutXmlPenAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlPenAut;
            strRutXmlSiAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlSiAut;
            strRutXmlNoAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlNoAut;
            strPK = intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc;
            carpeta = new File(strRutXmlPenAut);
            
            for (File archivo:carpeta.listFiles())
            {  strAux = archivo.getName();
               if (strAux.lastIndexOf(strPK) != -1)
               {  strNomFilFac = strAux.substring(0, strAux.lastIndexOf(".xml"));
                  break;
               }
            }
            
            strRutNomFilFac = strRutXmlPenAut + File.separator + strNomFilFac + ".xml";
            File fOriXml = new File(carpeta + File.separator + strNomFilFac + ".xml");
            
            if (invocarServiciosWeb(strRutNomFilFac, arlResWS) == true)
            {  if (arlResWS.size() > 0)
               {  if (updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == true)
                  {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilFac + ".xml"));
                        strAux = generarPDF(strPrmNomEmp, "FAC", con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFilFac);
                        if (strAux.equals("true"))
                        {  //La generacion del PDF se realizo exitosamente
                           intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                           strCorEleCliFacEle = "";
                           strCorEleCliFacEle2 = "";
                           //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                           buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                           if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                           {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                              strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
                              if (!strAux.equals(""))
                              {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux + ". " + strAux2;
                              }
                              else
                              {  //Solo va el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux2;
                              }  
                              objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                              updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                           }
                           else
                           {  obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
                              strAux = enviarCorreoServidorInterno(strPrmNomEmp, "FAC", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilFac);

                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/  }
                              else
                              {  //Hubo un error en el envio del correo
                                 blnRes = false;
                                 strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                              
                              strAux = enviarCorreoServidorExterno(strPrmNomEmp, "FAC", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilFac);
                              
                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/ }
                              else
                              {  //Hubo un error en el envio del correo.
                                 blnRes = false;
                                 strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                                 if (strAux2.equals(""))
                                 {  //Significa que aun no se ha guardado ningun mensaje en el campo tx_deserrfacele. Por tanto, 
                                    //se va a obtener el mensaje devuelto por el Servicio Web
                                    strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 }
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje guardado en strAux2 + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                           } //if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                        }
                        else
                        {  //Hubo un error en la generacion del PDF
                           blnRes = false;
                           strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                           if (!strAux2.equals(""))
                           {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de generacion de PDF
                              strAux = strAux2 + ". " + strAux;
                           }
                           objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                           updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                        } //if (strAux.equals("true"))
                     } //if (strEst.equals("A"))
                     else
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilFac + ".xml"));

                     fOriXml.delete();
                  } //if (updateCamposFacEle(...)
               } //if (arlResWS.size() > 0)
            } //if (invocarServiciosWeb(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         else
         { //Solo se va a generar el XML. La autorizacion al SRI se lo hara en diferido.
            if (armarXMLFact_OffLine(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilFac, arlResWS) == true)
            {  emisor = new Emisor();
               emisor.setRuc(strRucEmp);
               emisor.setToken("BCE_IKEY2032");
               emisor.setTiempoEspera(3);
               strRuc = emisor.getRuc();
               intTimEsp = emisor.getTiempoEspera();
               
               if (firmarArch_p12(intCodEmp, strRutNomFilFac, arlResWS) == true)
               {  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  //DBN_Probar impresion
                  /*if (impresionFactura(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == false);
                  {  blnRes = false;
                     updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }*/
               }
               else
               {  blnRes = false;
                  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
            } //if armarXMLFact_OffLine(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         
         if (strEst.equals("A"))
            System.out.println("\n" + strPrmNomEmp + " => Fact si fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         else
            System.out.println("\n" + strPrmNomEmp + " => Fact no fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener el número de autorización. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println(strPrmNomEmp + " => Error en proceso de Fact: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
      return blnRes;
   } //Metodo getNumAutorizacionFac()
   
   private boolean armarXMLGuiaRemision_OffLine(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
        boolean blnRes = true, blnFound, blnEstImpGuiRem;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabGuiRem, arlDatCabGuiRem, arlRegDetGuiRem, arlDatDetGuiRem;
        String strXml, strNomFilXml, strClvAcc, strDD, strMM, strAAAA, strTipIdeTra, strFecEmiGuiRem, strNumFac, strFecEmiFac, strNumAutFac;
        String strFecIniTra, strFecFinTra, strDirPar, strRucCliDes, strNomCliDes, strDirCliDes, strRucTra, strRazSocTra;
        String strAux, strPlaVehRet, strChoRet, strNumDocSus, strObs1, strObs2, strHor, strMin, strSeg;
        int i, intDigCtr, intHor, intMin, intSeg, intAux, intCodUsrIng_CabIngEgrMerBod;
        long lngAux, lngNumCpb, lngCodNum;
        BigDecimal bdeAux;
        PrintWriter xml = null;

        try
        {  if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              {  stm = con.createStatement();
                 strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                 strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 24"; //24: Guia de remision
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                    strCodCpb = rst.getString("tx_codCatFacEle");
                  
                 arlDatCabGuiRem = new ArrayList();
                 strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc as fe_doc_guirem, a1.ne_numdoc as ne_numdoc_guirem, a3.co_usring, ";
	         strSQL += "       ( SELECT DISTINCT b5.ne_numdoc ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         INNER JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as ne_numfac, ";
                 strSQL += "       ( SELECT DISTINCT b5.fe_doc ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         INNER JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as fe_doc_fac, ";
                 strSQL += "       ( SELECT DISTINCT b5.tx_numAutFacEle ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         INNER JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as tx_numAutFac, ";
                 strSQL += "       a1.fe_initra, a1.fe_tertra, a1.tx_ptopar, a1.tx_rucclides, a1.tx_nomclides, a1.tx_dirclides, a1.tx_ciuclides, a1.tx_datdocoriguirem, ";
                 //Si tbm_cabingegrmerbod.co_veh = null, significa que 'El cliente retira la mercaderia'
                 //Si tbm_cabingegrmerbod.co_veh <> null, significa que 'La mercadería se envia al cliente en uno de nuestros vehículos'
                 strSQL += "       (case when a3.co_veh is null then a3.tx_idetra else '" + strRucEmp + "' end) as tx_ructra, ";
                 strSQL += "       (case when a3.co_veh is null then a3.tx_nomtra else '" + strNomEmp + "' end) as tx_razsoctra, ";
                 strSQL += "       (case when a3.co_veh is null then a3.tx_plavehtra else a5.tx_pla end) as tx_plavehtra, ";
                 strSQL += "       (case when a3.co_veh is not null then a6.tx_ide end) as tx_idecho, ";
                 strSQL += "       (case when a3.co_veh is not null then a6.tx_nom || ' ' || a6.tx_ape end) as tx_nomcho_emp_env_mer, ";
                 strSQL += "       a1.tx_obs1, a1.tx_obs2 ";
                 strSQL += "FROM       tbm_cabguirem as a1 ";
                 strSQL += "INNER JOIN tbm_detguirem as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc ";
                 strSQL += "INNER JOIN tbm_cabingegrmerbod as a3 on a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_locrelguirem AND a1.co_tipdoc = a3.co_tipdocrelguirem and a1.co_doc = a3.co_docrelguirem ";
                 strSQL += "LEFT  JOIN tbm_veh AS a5 on a5.co_veh = a3.co_veh ";
                 strSQL += "LEFT  JOIN tbm_tra AS a6 on a6.co_tra = a3.co_cho ";
                 strSQL += "WHERE      a1.co_emp = " + intCodEmp;
                 strSQL += "           and a1.co_loc = " + intCodLoc;
                 strSQL += "           and a1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "           and a1.co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);
                 intCodUsrIng_CabIngEgrMerBod = 0;
                 
                 if (rst.next())
                 {  arlRegCabGuiRem = new ArrayList();
                    intCodUsrIng_CabIngEgrMerBod = rst.getInt("co_usring");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_DOC, "" + rst.getInt("co_doc"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_DOC, "" + rst.getInt("ne_numdoc_GuiRem"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_doc_GuiRem"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC, "" + strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_FAC, rst.getString("ne_numfac") == null? "" :rst.getString("ne_numfac"));
                    
                    strAux = rst.getString("fe_doc_fac") == null? "" :rst.getString("fe_doc_fac");
                    if (!strAux.equals(""))
                    {  strAux = objUti.formatearFecha(rst.getDate("fe_doc_fac"), "dd/MM/yyyy");
                       arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC, "" + strAux);
                    }
                    else
                       arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC, "");
                    
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC, rst.getString("tx_numautfac") == null? "" :rst.getString("tx_numautfac"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_initra"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA, "" + strAux);
                    strAux = objUti.formatearFecha(rst.getDate("fe_tertra"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA, "" + strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_DIR_PAR, rst.getString("tx_ptopar"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES, rst.getString("tx_rucclides"));
                    strAux = reemplazarCaracter(rst.getString("tx_nomclides"), '&', "&amp;");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES, strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES, "" + rst.getString("tx_dirclides"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RUC_TRA, "" + rst.getString("tx_ructra"));
                    strAux = reemplazarCaracter(rst.getString("tx_razsoctra"), '&', "&amp;");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA, "" + strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET, "" + rst.getString("tx_plavehtra") == null? "" :rst.getString("tx_plavehtra"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_CHO_RET, "" + rst.getString("tx_nomcho_emp_env_mer") == null? "" :rst.getString("tx_nomcho_emp_env_mer"));
                    
                    strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_OBS1, strAux);
                    
                    strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_OBS2, strAux);
                    
                    arlDatCabGuiRem.add(arlRegCabGuiRem);
                 }

                 arlDatDetGuiRem = new ArrayList();
                 strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt, tx_nomitm, nd_can ";
                 strSQL += "FROM   tbm_detguirem ";
                 strSQL += "WHERE  co_emp = " + intCodEmp;
                 strSQL += "       and co_loc = " + intCodLoc;
                 strSQL += "       and co_tipdoc = " + intCodTipDoc;
                 strSQL += "       and co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);

                 while (rst.next())
                 {  arlRegDetGuiRem = new ArrayList();
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_DOC, "" + rst.getInt("co_doc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_REG, "" + rst.getInt("co_reg"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_ITM, "" + rst.getInt("co_itm"));
                    
                    strAux = rst.getString("tx_codalt");
                    strAux = reemplazarCaracter(strAux, '&', "&amp;");
                    strAux = reemplazarCaracter(strAux, '<', "&lt;");
                    strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_ALT, strAux);
                    
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_NOM_ITM, rst.getString("tx_nomitm"));
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesMostrar());
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_CAN, "" + bdeAux);
                    arlDatDetGuiRem.add(arlRegDetGuiRem);    
                 }

                 strDD = "";
                 strMM = "";
                 strAAAA = "";
                 strHor = "";
                 strMin = "";
                 strSeg = "";
                 strFecIniTra = "";
                 strFecFinTra = "";
                 strFecEmiGuiRem = "";
                 strNumFac = "";
                 strFecEmiFac = "";
                 strNumAutFac = "";
                 strDirPar = "";
                 strRucCliDes = "";
                 strNomCliDes = "";
                 strDirCliDes = "";
                 strRucTra = ""; //RUC del transportista
                 strRazSocTra = ""; //Razon social del transportista
                 strPlaVehRet = ""; //Placa del vehiculo que retira la mercaderia
                 strChoRet = ""; //Nombre del chofer que retira la mercaderia
                 strTipIdeTra = ""; //Tipo de identificacion del transportista
                 strAux = "";
                 strCodNum = ""; //Codigo numerico (es secuencial)
                 strNumCpb = ""; //Numero de comprobante
                 strClvAcc = "";

                 if (arlDatCabGuiRem.size() > 0)
                 {  strAux = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_COD_DOC);
                    lngCodNum = Long.parseLong(strAux);
                    strCodNum = String.format("%08d", lngCodNum); // => "00000001"

                    strAux = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_DOC);
                    lngNumCpb = Long.parseLong(strAux);
                    strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                    //strNumCpb = "000000001"; //DBN_Prueba
                 
                    strNumFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_FAC);
                    if (!strNumFac.equals(""))
                    {  lngAux = Long.parseLong(strNumFac);
                       strNumFac = String.format("%09d", lngAux); // => "000000001"
                    }

                    strFecEmiFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC);
                    //strFecEmiFac = "27/11/2014"; //DBN_Prueba

                    strNumAutFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC);
                    strFecIniTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA);
                    //strFecIniTra = "27/11/2014"; //DBN_Prueba
                    strFecFinTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA);
                    //strFecFinTra = "27/11/2014"; //DBN_Prueba
                    strDirPar = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_DIR_PAR);
                    strRucCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES);
                    strNomCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES);
                    strDirCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES);
                    strRucTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RUC_TRA);
                    strTipIdeTra = getTipIdeTransportista(strRucTra);
                    strRazSocTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA);
                    strPlaVehRet = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET);
                    strChoRet = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_CHO_RET);
                    strFecEmiGuiRem = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_DOC);
                    //strFecEmiGuiRem = "27/11/2014"; //DBN_Prueba
                    strDD = strFecEmiGuiRem.substring(0, 2);
                    strMM = strFecEmiGuiRem.substring(3, 5);
                    strAAAA = strFecEmiGuiRem.substring(6, 10);
                    Calendar objCal = new GregorianCalendar();
                    intHor = objCal.get(Calendar.HOUR_OF_DAY);
                    intMin = objCal.get(Calendar.MINUTE);
                    intSeg = objCal.get(Calendar.SECOND);
                    strHor = String.format("%02d", intHor);
                    strMin = String.format("%02d", intMin);
                    strSeg = String.format("%02d", intSeg);

                    strAux = strDD + strMM + strAAAA;
                    strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                    strClvAcc = strAux;
                    intDigCtr = objMod11.getDigitoControl(strAux);
                    strClvAcc += intDigCtr;
                    
                    //Se va a calcular los dias transcurridos entre la fecha de emision de la O/D y la fecha actual
                    strSQL =  "SELECT a3.fe_doc as fe_doc_orddes, (current_date - a3.fe_doc) as ne_num_dia_orddes ";
                    strSQL += "FROM   tbm_cabguirem as a1 ";
                    strSQL += "INNER JOIN tbr_cabguirem as a2 on a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipdoc = a2.co_tipdoc and a1.co_doc = a2.co_doc ";
                    strSQL += "INNER JOIN tbm_cabguirem as a3 on a2.co_emp = a3.co_emp AND a2.co_locrel = a3.co_loc AND a2.co_tipdocrel = a3.co_tipdoc and a2.co_docrel = a3.co_doc ";
                    strSQL += "WHERE  a1.co_emp = " + intCodEmp;
                    strSQL += "       and a1.co_loc = " + intCodLoc;
                    strSQL += "       and a1.co_tipdoc = " + intCodTipDoc;
                    strSQL += "       and a1.co_doc = " + intCodDoc;
                    rst = stm.executeQuery(strSQL);
                    blnEstImpGuiRem = false;
                    
                    if (rst.next())
                    {  strAux = rst.getString("ne_num_dia_orddes") == null? "" :rst.getString("ne_num_dia_orddes");
                       if (!strAux.equals(""))
                       {  intAux = Integer.parseInt(strAux);
                          if (intAux > 30)
                          {  //Como entre la fecha actual y la fecha de emision de la O/D ha transcurrido mas de 30 dias, no se debe imprimir
                             //la guia de remision. Por ello, se pone el estado de impresion 'S' para que el Servicio 1 no imprima la guia.
                             blnEstImpGuiRem = true;
                          }
                       }
                    }
                    
                    if (blnEstImpGuiRem == false && intCodUsrIng_CabIngEgrMerBod == 246)
                    {  //co_usr 246 = wcampoverde
                       //Si el usuario es wcampoverde, significa que el confirmo la salida de mercaderia pero no quiere que se imprima en despacho.
                       //Por ello, se pone el estado de impresion 'S' para que el Servicio 1 no imprima la guia.
                       blnEstImpGuiRem = true;
                    }
                    
                    if (blnEstImpGuiRem == true)
                    {  strSQL =  "UPDATE tbm_cabguirem ";
                       strSQL += "SET st_imp = 'S' ";
                       strSQL += "WHERE co_emp = " + intCodEmp;
                       strSQL += "      and co_loc = " + intCodLoc;
                       strSQL += "      and co_tipdoc = " + intCodTipDoc;
                       strSQL += "      and co_doc = " + intCodDoc;
                       stm.executeUpdate(strSQL);
                    }
                 } //if (arlDatCabGuiRem.size() > 0)
              
                 //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                 //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                 strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                 strNomFilXml += "GuiaRemision_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                 strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                 strNomFilGuiRem = strNomFilXml;

                 xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                 strRutNomFilGuiRem = strRutFil + File.separator + strNomFilXml + ".xml";
                 
                 strXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
                 strXml += "<guiaRemision id=\"comprobante\" version=\"1.1.0\"> ";
                 strXml += "<infoTributaria> ";
                 strXml += "<ambiente>" + intTipAmb + "</ambiente> ";
                 strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision> ";
                 strXml += "<razonSocial>" + strNomEmp + "</razonSocial> ";
                 strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
                /* JM: Se reversa el cambio 4/Nov/2020 */
//                if( (intCodEmp==1) && (intCodLoc==13 || intCodLoc==14 || intCodLoc==15 || intCodLoc==16 || intCodLoc==17 || intCodLoc==18 || intCodLoc==23 || intCodLoc==24 || intCodLoc==25 || intCodLoc==26) ){
//                      strXml += "<nombreComercial>" + "CASTEK S.A." + "</nombreComercial> ";
//                  }
//                  else if ( (intCodEmp==1) && (intCodLoc==19 || intCodLoc==20 || intCodLoc==21 || intCodLoc==22  ) ){
//                      strXml += "<nombreComercial>" + "DIMULTI S.A." + "</nombreComercial> ";
//                  }
//                  else{
//                      strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                  }

                 strXml += "<ruc>" + strRucEmp + "</ruc> ";
                 strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso> ";
                 strXml += "<codDoc>" + strCodCpb + "</codDoc> ";
                 strXml += "<estab>" + strCodEst + "</estab> ";
                 strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi> ";
                 strXml += "<secuencial>" + strNumCpb + "</secuencial> ";
                 strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz> ";
                 strXml += "</infoTributaria> ";
              
                 strXml += "<infoGuiaRemision> ";
                 strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento> ";
                 strXml += "<dirPartida>" + strDirPar + "</dirPartida> ";
                 strXml += "<razonSocialTransportista>" + strRazSocTra + "</razonSocialTransportista> ";
                 strXml += "<tipoIdentificacionTransportista>" + strTipIdeTra + "</tipoIdentificacionTransportista> ";
                 strXml += "<rucTransportista>" + strRucTra + "</rucTransportista> ";
                 strXml += "<obligadoContabilidad>SI</obligadoContabilidad> "; //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                 
                 if (!strConEsp.equals(""))
                 {
                    strXml += "<contribuyenteEspecial>" + strConEsp + "</contribuyenteEspecial> ";
                 }
                 
                 strXml += "<fechaIniTransporte>" + strFecIniTra + "</fechaIniTransporte> ";
                 strXml += "<fechaFinTransporte>" + strFecFinTra + "</fechaFinTransporte> ";
                 
                 if (strPlaVehRet.equals(""))
                    strPlaVehRet = "na";
                    
                 strXml += "<placa>" + strPlaVehRet + "</placa> ";
                 strXml += "</infoGuiaRemision> ";

                 strXml += "<destinatarios> ";
                 strXml +=   "<destinatario> ";
                 strXml +=     "<identificacionDestinatario>" + strRucCliDes + "</identificacionDestinatario> ";
                 strXml +=     "<razonSocialDestinatario>" + strNomCliDes + "</razonSocialDestinatario> ";
                 strXml +=     "<dirDestinatario>" + strDirCliDes + "</dirDestinatario> ";
                 
                 if (!strNumFac.equals(""))
                 {  //Si strNumFac <> "", significa que hay una factura de venta relacionada con la guia de remision
                    strXml +=     "<motivoTraslado>VENTA</motivoTraslado> ";
                 }   
                 else
                 {  //Si strNumFac = "", significa que no hay una factura de venta relacionada con la guia de remision
                    strXml +=     "<motivoTraslado>TRASLADO</motivoTraslado> ";
                 }
                 
                 if (!strNumFac.equals(""))
                 {  strXml +=     "<codDocSustento>01</codDocSustento> "; //01: Factura.
                    //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                    strNumDocSus = strCodEst + "-" + strPtoEmi + "-" + strNumFac;
                    strXml +=     "<numDocSustento>" + strNumDocSus + "</numDocSustento> ";
                 }
                 
                 if (!strNumAutFac.equals(""))
                    strXml +=     "<numAutDocSustento>" + strNumAutFac + "</numAutDocSustento> ";
                 
                 if (!strFecEmiFac.equals(""))
                    strXml +=     "<fechaEmisionDocSustento>" + strFecEmiFac + "</fechaEmisionDocSustento> ";

                 strXml += "<detalles> ";
              
                 for (i = 0; i < arlDatDetGuiRem.size(); i++)
                 {  strXml += "<detalle> ";
                    strXml += "<codigoInterno>" + objUti.getIntValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_COD_ITM) + "</codigoInterno> ";
                    strXml += "<codigoAdicional>" + objUti.getStringValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_COD_ALT) + "</codigoAdicional> ";
                    strAux = reemplazarCaracter(objUti.getStringValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_NOM_ITM), '&', "&amp;");
                    strXml += "<descripcion>" + strAux + "</descripcion> ";
                    strXml += "<cantidad>" + objUti.getBigDecimalValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_CAN) + "</cantidad> ";
                    strXml += "</detalle> ";
                 }

                 strXml += "</detalles> ";
                 strXml +=   "</destinatario> ";
                 strXml += "</destinatarios> ";

                 blnFound = false;
                 strObs1 = "";
                 strObs2 = "";

                 if (arlDatCabGuiRem.size() > 0)
                 {  strObs1 = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_OBS1);
                    strObs2 = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_OBS2);
                    strObs1 = strObs1.trim();
                    strObs2 = strObs2.trim();
                    if (!strObs1.equals("") || !strObs2.equals(""))
                       blnFound = true; //Si se ingreso alguna observacion en algunos de los campos 'Observacion' de la Guia de remision
                 }

                 if (blnFound == true)
                 {  strXml += "<infoAdicional> ";
                    if (!strObs1.equals(""))
                       strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional> ";
                 
                    if (!strObs2.equals(""))
                       strXml += "<campoAdicional nombre=\"Observacion2\">" + strObs2 + "</campoAdicional> ";
                    strXml += "</infoAdicional> ";
                 }
              
                 strXml += "</guiaRemision>";
                 xml.println(strXml);
                 
                 arlRegDetResWS = new ArrayList();
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                 arlResWS.add(arlRegDetResWS);

                 rst.close();
                 rst = null;
                 stm.close();
                 stm = null;
                 xml.println();
                 xml.close();
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  
           e.printStackTrace();
           blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //strAux += e;
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;
   } // Funcion armarXMLGuiaRemision_OffLine()
   
   /**
    * Este metodo se encarga de firmar, enviar y autorizar una Guia de remision XML usando los Servicios Web del SRI.
    * @param con Conexion con la base de datos.
    * @strPrmNomEmp Nombre de empresa.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @param arlResWS Arreglo de tipo Array List que devuelve 4 valores, todos de tipo String:
    * <BR>
    * <BR><I>Indice 0:</I> Estado devuelto por el Servicio Web o por alguna excepcion.
    * <BR>Los estados devueltos son:
    * <BR>E: Errores varios: enlace, base de datos, etc. Basicamente almacena la excepción generada.
    * <BR>X: Error en estructura del archivo XML. Se envió el archivo XML pero no pasó el 1er. Servicio Web.
    * <BR>A: El documento fue autorizado.
    * <BR><I>Indice 1:</I> Mensaje devuelto por el Servicio Web o por alguna excepcion.
    * <BR><I>Indice 2:</I> Numero de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 3:</I> Fecha de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 4:</I> Estado (S/N) que indica si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA".
    * <BR><I>Indice 5:</I> Clave de acceso del comprobante XML.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   public boolean getNumAutorizacionGuiRem (Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux, strAux2, strRutFilGuiRem, strEst, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut, strPK;
      int intCodCli;
      File carpeta;
      
      try
      {
         arlResWS.clear();
         emisor = new Emisor();
         strRutFilGuiRem = "";
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFilGuiRem = "/Zafiro/Digitales/Compras/ZafCom23/" + strPrmNomEmp + "/Con_PenAut";
         else
            strRutFilGuiRem = "C:\\Zafiro\\Digitales\\Compras\\ZafCom23\\" + strPrmNomEmp + "\\Con_PenAut";     
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         strEst = getEstAutFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabguirem");
         
         if (strEst.equals("S"))
         {  //Se va a solicitar la autorizacion en linea
            strRutXmlPenAut = "";
            strRutXmlSiAut = "";
            strRutXmlNoAut = "";
         
            //DBN_Prueba
            /*if (! System.getProperty("os.name").equals("Linux"))
            {  strRutXmlPenAut = "//" + strIpSrvPrd;
               strRutXmlSiAut = "//" + strIpSrvPrd;
               strRutXmlNoAut = "//" + strIpSrvPrd;
            }*/

            strNomFilGuiRem = "";
            strRutXmlTipCpb = "/Zafiro/Digitales/Compras/ZafCom23/"; //DBN_real
//            strRutXmlTipCpb = "C:/Zafiro/Digitales/Compras/ZafCom23/"; //DBN_prueba
            strRutXmlPenAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlPenAut;
            strRutXmlSiAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlSiAut;
            strRutXmlNoAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlNoAut;
            strPK = intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc;
            carpeta = new File(strRutXmlPenAut);
            
            for (File archivo:carpeta.listFiles())
            {  strAux = archivo.getName();
               if (strAux.lastIndexOf(strPK) != -1)
               {  strNomFilGuiRem = strAux.substring(0, strAux.lastIndexOf(".xml"));
                  break;
               }
            }
            
            strRutNomFilGuiRem = strRutXmlPenAut + File.separator + strNomFilGuiRem + ".xml";
            File fOriXml = new File(carpeta + File.separator + strNomFilGuiRem + ".xml");
            
            if (invocarServiciosWeb(strRutNomFilGuiRem, arlResWS) == true)
            {  if (arlResWS.size() > 0)
               {  if (updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == true)
                  {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilGuiRem + ".xml"));
                     }
                     else
                     {  //No hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilGuiRem + ".xml"));
                     }

                     fOriXml.delete();
                     
                     //Inicio bloque: Descomentar bloque cuando se vaya a enviar correo al cliente
                     /*strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilGuiRem + ".xml"));
                        strAux = generarPDF(strPrmNomEmp, "GUIREM", con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFilGuiRem);
                        if (strAux.equals("true"))
                        {  //La generacion del PDF se realizo exitosamente
                           intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabguirem");
                           strCorEleCliFacEle = "";
                           strCorEleCliFacEle2 = "";
                           //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                           buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                           if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                           {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                              strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
                              if (!strAux.equals(""))
                              {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux + ". " + strAux2;
                              }
                              else
                              {  //Solo va el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux2;
                              }
                              objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                              updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                           }
                           else
                           {  obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
                              strAux = enviarCorreoServidorInterno(strPrmNomEmp, "GUIREM", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilGuiRem);

                              if (strAux.equals("true"))
                              {   int Envio_de_correo_OK = 0;  }
                              else
                              {  //Hubo un error en el envio del correo
                                 blnRes = false;
                                 strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                              
                              strAux = enviarCorreoServidorExterno(strPrmNomEmp, "GUIREM", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilGuiRem);
                              
                              if (strAux.equals("true"))
                              {  int Envio_de_correo_OK = 0; }
                              else
                              {  //Hubo un error en el envio del correo.
                                 blnRes = false;
                                 strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabguirem");
                                 if (strAux2.equals(""))
                                 {  //Significa que aun no se ha guardado ningun mensaje en el campo tx_deserrfacele. Por tanto, 
                                    //se va a obtener el mensaje devuelto por el Servicio Web
                                    strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 }
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje guardado en strAux2 + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                           } //if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                        }
                        else
                        {  //Hubo un error en la generacion del PDF
                           blnRes = false;
                           strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                           if (!strAux2.equals(""))
                           {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de generacion de PDF
                              strAux = strAux2 + ". " + strAux;
                           }
                           objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                           updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                        } //if (strAux.equals("true"))
                     } //if (strEst.equals("A"))
                     else
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilGuiRem + ".xml"));

                     fOriXml.delete();*/
                     //Fin bloque: Descomentar bloque cuando se vaya a enviar correo al cliente
                  } //if (updateCamposFacEle(...)
               } //if (arlResWS.size() > 0)
            } //if (invocarServiciosWeb(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         else
         {  //--- Inicio bloque: Descomentar bloque cuando el Servicio 11 vaya a generar el XML de la guia ---
            //Este bloque fue comentado debido a que el Servicio 11 no va a generar el XML de la guia.
            /*
            //Solo se va a generar el XML. La autorizacion al SRI se lo hara en diferido.
            if (armarXMLGuiaRemision_OffLine(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilGuiRem, arlResWS) == true)
            {  emisor = new Emisor();
               emisor.setRuc(strRucEmp);
               emisor.setToken("BCE_IKEY2032");
               emisor.setTiempoEspera(3);
               strRuc = emisor.getRuc();
               intTimEsp = emisor.getTiempoEspera();

               if (firmarArch_p12(intCodEmp, strRutNomFilGuiRem, arlResWS) == true)
                  updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               else
               {  blnRes = false;
                  updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
            }
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }*/
            //--- Fin bloque: Descomentar bloque cuando el Servicio 11 vaya a generar el XML de la guia ------
         } //if (strEst.equals("S"))
         
         System.out.println("\n" + strPrmNomEmp + " => Fin de proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener el número de autorización. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println(strPrmNomEmp + " => Error en proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
   return blnRes;
   } //Metodo getNumAutorizacionGuiRem()
   
   private String getTipIdeTransportista (String strRucTra)
   {
      String strRes = "00", strUlt3Dig;
      
      try
      {
         if (strRucTra.length() == 13)
         {  //Si la longitud es 13, es RUC o Consumidor final
            //Ej: strRucTra = "1234567891001"
            strUlt3Dig = strRucTra.substring(10, 13); // Va a extraer "001"
            if (strUlt3Dig.equals("001"))
            {  //Es RUC
               strRes = "04"; //Segun PDF del SRI, el valor de 04 corresponde a RUC. En algun momento se sugiere reemplazar este codigo quemado
            }
            else if (strUlt3Dig.equals("999"))
            {  //Es Consumidor final
               strRes = "07"; //Segun PDF del SRI, el valor de 07 corresponde a Venta a consumidor final. En algun momento se sugiere reemplazar este codigo quemado
            }
         }
         else if (strRucTra.length() == 10 && objUti.isNumero(strRucTra) == true)
            strRes = "05"; //Segun PDF del SRI, el valor de 05 corresponde a Cedula. En algun momento se sugiere reemplazar este codigo quemado
         else
            strRes = "08"; //Segun PDF del SRI, el valor de 08 corresponde a Identificacion del exterior. En algun momento se sugiere reemplazar este codigo quemado
      }
      
      catch(Exception e)
      {  
         strRes = "00";
      }
 
   return strRes;
   } //Funcion getTipIdeTransportista()
   
   private boolean armarXMLNotaCredito(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
        boolean blnRes = true, blnFound, blnNotCreTieIva;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabNotCre, arlDatCabNotCre, arlRegDetNotCre, arlDatDetNotCre;
        String strAux, strXml, strNomFilXml, strClvAcc, strTipIdeCom, strFecEmiNotCre, strDD, strMM, strAAAA, strHor, strMin, strSeg;
        String strIdeCom, strObs1, strNumFac, strFecEmiFac, strNumDocMod, strTipDev;
        int i, intDigCtr, intCodPorIva, intHor, intMin, intSeg, intCodCli = 0;
        long lngAux, lngNumCpb, lngCodNum;
        BigDecimal bdeAux, bdeCan, bdePreUni, bdeValDes, bdeTotSinImp, bdeBasImpIva, bdePorIva, bdeSubDet, bdeValIvaDet, bdeSubNotCre;
        BigDecimal bdeValIvaNotCre, bdeTotNotCre, bdePorComSol, bdeValComSol;
        PrintWriter xml;

        try
        {  if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              {  stm = con.createStatement();
                 strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                 strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 4"; //4: Notas de credito (Devoluciones de ventas)
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                    strCodCpb = rst.getString("tx_codCatFacEle");
                 
                 strTipDev = "";
                 arlDatCabNotCre = new ArrayList();
                 strSQL =  "SELECT     nc.co_emp, nc.co_loc, nc.co_tipdoc, nc.co_doc, nc.st_tipdev, nc.fe_doc as fe_doc_notcre, nc.ne_numdoc as ne_numdoc_notcre, nc.ne_numcot as ne_numfac, ";
                 /*
                 //*** Inicio bloque comentado el 12/Jun/2017 ***
                 //Este subquery trae la fecha de la Factura Origen de la Nota de credito. Este query no trae dicha fecha si el local de donde se originó dicha 
                 //factura esta cerrado.
                 //co_cat = 3: Facturas de ventas
                 strSQL += "           ( select     fe_doc ";
                 strSQL += "             from       tbm_cabmovinv as b1 ";
                 strSQL += "             inner join tbm_cabtipdoc as b2 on b2.co_emp = b1.co_emp and b2.co_loc = b1.co_loc and b2.co_tipdoc = b1.co_tipdoc ";
                 strSQL += "             where      b1.co_emp = nc.co_emp and b1.co_loc = nc.co_loc and b1.ne_numdoc = nc.ne_numcot and b2.co_cat = 3 and b1.st_reg = 'A' ";
                 //La siguiente condicion (b2.st_utiFacEle = 'S') esta comentada debido a que, si la factura es pre-impresa, entonces no va a traer
                 //la Fecha_emision_Fac. Una vez que ya se este trabajando 100% con facturas electronicas, se le puede quitar el comentario
                 //strSQL += "and b2.st_utiFacEle = 'S' "; 
                 strSQL += "           ) as fe_doc_fac, ";
                 //*** Fin bloque comentado el 12/Jun/2017 ******
                 */
                 strSQL += "           fac.fe_doc as fe_doc_fac, ";
                 strSQL += "           nc.co_cli, nc.tx_nomcli, nc.tx_ruc as tx_rucCli, a2.tx_tipide as tx_tipidecli, nc.nd_poriva, abs(nc.nd_sub) as nd_sub, abs(nc.nd_valiva) as nd_valiva, abs(nc.nd_tot) as nd_tot, nc.nd_porComSol, abs(nc.nd_valComSol) as nd_valComSol, ";
                 strSQL += "           nc.tx_obs1, nc.tx_obs2 ";
                 strSQL += "FROM       tbm_cabmovinv as nc "; //nc = Nota de credito (Dev.Vta.)
                 strSQL += "INNER JOIN tbm_cabSolDevVen as s on s.co_emp = nc.co_emp and s.co_loc = nc.co_locrelsoldevven and s.co_tipdoc = nc.co_tipdocrelsoldevven and s.co_doc = nc.co_docrelsoldevven ";
                 strSQL += "INNER JOIN tbm_cabMovInv as fac on s.co_emp = fac.co_emp and s.co_locrel = fac.co_loc and s.co_tipdocrel = fac.co_tipdoc and s.co_docrel = fac.co_doc ";
                 strSQL += "LEFT JOIN  tbm_cli as a2 on a2.co_emp = nc.co_emp and a2.co_cli = nc.co_cli ";
                 strSQL += "WHERE      nc.co_emp = " + intCodEmp;
                 strSQL += "           and nc.co_loc = " + intCodLoc;
                 strSQL += "           and nc.co_tipdoc = " + intCodTipDoc;
                 strSQL += "           and nc.co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                 {  arlRegCabNotCre = new ArrayList();
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                    strTipDev = rst.getString("st_tipdev");
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_NUM_DOC, "" + rst.getInt("ne_numdoc_notcre"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_doc_notcre"), "dd/MM/yyyy");
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC, "" + strAux);
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_NUM_FAC, "" + rst.getInt("ne_numfac"));
                    
                    strAux = rst.getString("fe_doc_fac") == null? "" :rst.getString("fe_doc_fac");
                    if (!strAux.equals(""))
                       strAux = objUti.formatearFecha(rst.getDate("fe_doc_fac"), "dd/MM/yyyy");
                    
                    intCodCli = rst.getInt("co_cli");
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC_FAC, "" + strAux);
                    strAux = reemplazarCaracter(rst.getString("tx_nomcli"), '&', "&amp;");
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_NOM_CLI, strAux);
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_RUC_CLI, rst.getString("tx_ruccli"));
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI, rst.getString("tx_tipidecli"));
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva"), objParSis.getDecimalesMostrar());
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_POR_IVA, "" + bdeAux);
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_sub"), objParSis.getDecimalesMostrar());
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_VAL_SUB, "" + bdeAux);
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_valiva"), objParSis.getDecimalesMostrar());
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_VAL_IVA, "" + bdeAux);
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_tot"), objParSis.getDecimalesMostrar());
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_VAL_TOT, "" + bdeAux);
                    
                    strAux = rst.getString("nd_porComSol") == null? "" :rst.getString("nd_porComSol");
                     
                    if (strAux.equals(""))
                       bdeAux = new BigDecimal("0.00");
                    else
                    {  bdeAux = new BigDecimal(strAux);
                       bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                    }
                     
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL, "" + bdeAux);
                     
                    strAux = rst.getString("nd_valComSol") == null? "" :rst.getString("nd_valComSol");
                     
                    if (strAux.equals(""))
                       bdeAux = new BigDecimal("0.00");
                    else
                    {  bdeAux = new BigDecimal(strAux);
                       bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                    }
                     
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL, "" + bdeAux);
                    
                    strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_OBS1, strAux);
                    
                    strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabNotCre.add(INT_ARL_DAT_CAB_MOV_INV_OBS2, strAux);
                    
                    arlDatCabNotCre.add(arlRegCabNotCre);
                 }

                 arlDatDetNotCre = new ArrayList();
                 strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.st_tipdev, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, ";
                 //Case cuando st_tipdev = 'C' (Cantidades)
                 strSQL += "(case when a2.st_tipdev = 'C' then abs(a1.nd_can) end) as nd_can_tipdev_c, ";
                 strSQL += "(case when a2.st_tipdev = 'C' then a1.nd_preuni end) as nd_preuni_tipdev_c, ";
                 strSQL += "(case when a2.st_tipdev = 'C' then round((abs(a1.nd_can) * a1.nd_preuni), 2) end) as nd_subtotdet_tipdev_c, ";
                 strSQL += "(case when a2.st_tipdev = 'C' then round((abs(a1.nd_can) * a1.nd_preuni * a1.nd_pordes / 100), 2) end) as nd_valdes_tipdev_c, ";
                 //Case cuando st_tipdev = 'V' (Precio)
                 strSQL += "(case when a2.st_tipdev = 'V' then abs(a1.nd_canorg) end) as nd_can_tipdev_v, ";
                 strSQL += "(case when a2.st_tipdev = 'V' then abs(a1.nd_can) end) as nd_preuni_tipdev_v, ";
                 strSQL += "(case when a2.st_tipdev = 'V' then round((abs(a1.nd_canorg) * abs(a1.nd_can)), 2) end) as nd_subtotdet_tipdev_v, ";
                 strSQL += "(case when a2.st_tipdev = 'V' then round((abs(a1.nd_canorg) * abs(a1.nd_can) * a1.nd_pordes / 100), 2) end) as nd_valdes_tipdev_v, ";
                 //Case cuando st_tipdev = 'P' (Porcentaje de descuento) y 'E' (Descuentos especiales)
                 strSQL += "(case when a2.st_tipdev in ('P','E') then abs(a1.nd_canorg) end) as nd_can_tipdev_p_e, ";
                 strSQL += "(case when a2.st_tipdev in ('P','E') then a1.nd_preuni end) as nd_preuni_tipdev_p_e, ";
                 strSQL += "(case when a2.st_tipdev in ('P','E') then round(((abs(a1.nd_canorg) * a1.nd_preuni) - ((abs(a1.nd_canorg) * a1.nd_preuni) * a1.nd_pordes / 100)) * abs(a1.nd_can) / 100, 2) end) as nd_subtotdet_tipdev_p_e, ";
                 strSQL += "(case when a2.st_tipdev in ('P','E') then round((abs(a1.nd_canorg) * a1.nd_preuni), 2) ";
                 strSQL += "                                        - round(((abs(a1.nd_canorg) * a1.nd_preuni) - ((abs(a1.nd_canorg) * a1.nd_preuni) * a1.nd_pordes / 100)) * abs(a1.nd_can) / 100, 2) end) as nd_valdes_tipdev_p_e, ";
                 
                 strSQL += "a1.nd_pordes, abs(a1.nd_tot) as nd_totdet, a1.st_ivacom ";
                 strSQL += "FROM       tbm_detmovinv as a1 ";
                 strSQL += "INNER JOIN tbm_cabmovinv as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_loc and a1.co_tipdoc = a2.co_tipdoc and a1.co_doc = a2.co_doc ";
                 strSQL += "WHERE  a1.co_emp = " + intCodEmp;
                 strSQL += "       and a1.co_loc = " + intCodLoc;
                 strSQL += "       and a1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "       and a1.co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);

                 while (rst.next())
                 {  arlRegDetNotCre = new ArrayList();
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_REG, "" + rst.getInt("co_reg"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_ITM, "" + rst.getInt("co_itm"));
                    
                    strAux = rst.getString("tx_codalt");
                    strAux = reemplazarCaracter(strAux, '&', "&amp;");
                    strAux = reemplazarCaracter(strAux, '<', "&lt;");
                    strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_COD_ALT, strAux);
                    
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_NOM_ITM, rst.getString("tx_nomitm"));
                    
                    bdeCan = new BigDecimal("0");
                    bdePreUni = new BigDecimal("0");
                    bdeSubDet = new BigDecimal("0");
                    bdeValDes = new BigDecimal("0");
                    
                    if (strTipDev.equals("C"))
                    {  //C = Cantidades
                       bdeCan = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can_tipdev_c"), objParSis.getDecimalesMostrar());
                       bdePreUni = rst.getBigDecimal("nd_preuni_tipdev_c");
                       bdeSubDet = rst.getBigDecimal("nd_subtotdet_tipdev_c");
                       bdeValDes = rst.getBigDecimal("nd_valdes_tipdev_c");
                    }
                    else if (strTipDev.equals("V"))
                    {  //V = Precio
                       bdeCan = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can_tipdev_v"), objParSis.getDecimalesMostrar());
                       bdePreUni = rst.getBigDecimal("nd_preuni_tipdev_v");
                       bdeSubDet = rst.getBigDecimal("nd_subtotdet_tipdev_v");
                       bdeValDes = rst.getBigDecimal("nd_valdes_tipdev_v");
                    }
                    else if (strTipDev.equals("P") || strTipDev.equals("E"))
                    {  //P = Porcentaje de descuento
                       bdeCan = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can_tipdev_p_e"), objParSis.getDecimalesMostrar());
                       bdePreUni = rst.getBigDecimal("nd_preuni_tipdev_p_e");
                       bdeSubDet = rst.getBigDecimal("nd_subtotdet_tipdev_p_e");
                       bdeValDes = rst.getBigDecimal("nd_valdes_tipdev_p_e");
                    }
                    
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_CAN, "" + bdeCan);
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_PRE_UNI, "" + bdePreUni);
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_SUB_DET, "" + bdeSubDet);
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_POR_DES, "" + rst.getBigDecimal("nd_pordes"));
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_VAL_DES, "" + bdeValDes);
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_totdet"), objParSis.getDecimalesMostrar());
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_TOT_DET, "" + bdeAux);
                    arlRegDetNotCre.add(INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM, rst.getString("st_ivacom"));
                    arlDatDetNotCre.add(arlRegDetNotCre);    
                 }

                 strFecEmiNotCre = "";
                 strDD = "";
                 strMM = "";
                 strAAAA = "";
                 strHor = "";
                 strMin = "";
                 strSeg = "";
                 strIdeCom = "";
                 strRazSocCom = "";
                 strTipIdeCom = "";
                 strAux = "";
                 strCodNum = ""; //Codigo numerico (es secuencial)
                 strNumCpb = ""; //Numero de comprobante
                 strNumFac = "";
                 strFecEmiFac = "";
                 strClvAcc = "";
                 strCorEleCliFacEle = "";
                 strCorEleCliFacEle2 = "";
                 bdePorIva = new BigDecimal("0");
                 bdeSubNotCre = new BigDecimal("0");
                 bdeValIvaNotCre = new BigDecimal("0");
                 bdeTotNotCre = new BigDecimal("0");
                 bdePorComSol = new BigDecimal("0");
                 bdeValComSol = new BigDecimal("0.00");

                 if (arlDatCabNotCre.size() > 0)
                 {  strAux = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_COD_DOC);
                    lngCodNum = Long.parseLong(strAux);
                    strCodNum = String.format("%08d", lngCodNum); // => "00000001"

                    strAux = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_NUM_DOC);
                    lngNumCpb = Long.parseLong(strAux);
                    strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                    //strNumCpb = "000000004"; //DBN_Prueba

                    strAux = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_NUM_FAC);
                    lngAux = Long.parseLong(strAux);
                    strNumFac = String.format("%09d", lngAux); // => "000000001"

                    strFecEmiFac = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_FEC_DOC_FAC);
                    //strFecEmiFac = "27/10/2014"; //DBN_Prueba

                    strFecEmiNotCre = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_FEC_DOC);
                    //strFecEmiNotCre = "09/12/2014"; //DBN_Prueba
                    //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                    buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                    strDD = strFecEmiNotCre.substring(0, 2);
                    strMM = strFecEmiNotCre.substring(3, 5);
                    strAAAA = strFecEmiNotCre.substring(6, 10);
                    Calendar objCal = new GregorianCalendar();
                    intHor = objCal.get(Calendar.HOUR_OF_DAY);
                    intMin = objCal.get(Calendar.MINUTE);
                    intSeg = objCal.get(Calendar.SECOND);
                    strHor = String.format("%02d", intHor);
                    strMin = String.format("%02d", intMin);
                    strSeg = String.format("%02d", intSeg);
                    strIdeCom = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_RUC_CLI);
                    strRazSocCom = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_NOM_CLI);
                    strTipIdeCom = "00"; //00: Valor invalido para la etiqueta del archivo xml
                    strAux = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI);
                    
                    if (strAux.equals("R"))
                       strTipIdeCom = "04"; //Venta con RUC
                    else if (strAux.equals("C"))
                       strTipIdeCom = "05"; //Venta con cedula
                    else if (strAux.equals("P"))
                       strTipIdeCom = "06"; //Venta con pasaporte
                    else if (strAux.equals("F"))
                        strTipIdeCom = "07"; //Venta a consumidor final
                    else if (strAux.equals("E"))
                        strTipIdeCom = "08"; //Identificacion del exterior
                     
                    //El valor de strTipIdeCom (Tipo de identificacion del comprador) esta quemado.
                    //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                    
                    bdePorIva = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_POR_IVA);
                    bdeSubNotCre = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_SUB);
                    bdeValIvaNotCre = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_IVA);
                    bdeTotNotCre = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_TOT);
                    bdePorComSol = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL);
                    bdeValComSol = objUti.getBigDecimalValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL);
                    
                    strAux =  strDD + strMM + strAAAA;
                    strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                    strClvAcc = strAux;
                    intDigCtr = objMod11.getDigitoControl(strAux);
                    strClvAcc += intDigCtr;
                 }
              
                 //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                 //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                 strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                 strNomFilXml += "NotaCredito_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                 strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                 strNomFilNotCre = strNomFilXml;

                 xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                 strRutNomFilNotCre = strRutFil + File.separator + strNomFilXml + ".xml";

                 strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
                 strXml += "<notaCredito id=\"comprobante\" version=\"1.1.0\"> ";
                 strXml += "<infoTributaria> ";
                 strXml += "<ambiente>" + intTipAmb + "</ambiente> ";
                 strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision> ";
                 strXml += "<razonSocial>" + strNomEmp + "</razonSocial> ";
                 strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";

//                if( (intCodEmp==1) && (intCodLoc==13 || intCodLoc==14 || intCodLoc==15 || intCodLoc==16 || intCodLoc==17 || intCodLoc==18 || intCodLoc==23 || intCodLoc==24 || intCodLoc==25 || intCodLoc==26) ){
//                    strXml += "<nombreComercial>" + "CASTEK S.A." + "</nombreComercial> ";
//                }
//                else if ( (intCodEmp==1) && (intCodLoc==19 || intCodLoc==20 || intCodLoc==21 || intCodLoc==22  ) ){
//                    strXml += "<nombreComercial>" + "DIMULTI S.A." + "</nombreComercial> ";
//                }
//                else{
//                    strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                }
                
                 strXml += "<ruc>" + strRucEmp + "</ruc> ";
                 strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso> ";
                 strXml += "<codDoc>" + strCodCpb + "</codDoc> ";
                 strXml += "<estab>" + strCodEst + "</estab> ";
                 strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi> ";
                 strXml += "<secuencial>" + strNumCpb + "</secuencial> ";
                 strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz> ";
                 strXml += "</infoTributaria> ";

                 strXml += "<infoNotaCredito> ";
                 strXml += "<fechaEmision>" + strFecEmiNotCre + "</fechaEmision> ";
                 strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento> ";
                 strXml += "<tipoIdentificacionComprador>" + strTipIdeCom + "</tipoIdentificacionComprador> ";
                 strXml += "<razonSocialComprador>" + strRazSocCom + "</razonSocialComprador> ";
                 strXml += "<identificacionComprador>" + strIdeCom + "</identificacionComprador> ";
                 
                 if (!strConEsp.equals(""))
                 {
                    strXml += "<contribuyenteEspecial>" + strConEsp + "</contribuyenteEspecial> ";
                 }
                 
                 strXml += "<obligadoContabilidad>SI</obligadoContabilidad> "; //En algun momento se sugiere reemplazar este codigo quemado
                 strXml += "<codDocModificado>01</codDocModificado> "; //01: Factura. En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                 strNumDocMod = strCodEst + "-" + strPtoEmi + "-" + strNumFac;
                 strXml += "<numDocModificado>" + strNumDocMod + "</numDocModificado> ";
                 strXml += "<fechaEmisionDocSustento>" + strFecEmiFac + "</fechaEmisionDocSustento> ";

                 bdeTotSinImp = new BigDecimal("0");
                 bdeBasImpIva = new BigDecimal("0");
                 blnNotCreTieIva = false;

                 for (i = 0; i < arlDatDetNotCre.size(); i++)
                 {  bdeAux = objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                    bdeTotSinImp = bdeTotSinImp.add(bdeAux);
                    
                    if (objUti.getStringValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                    {  blnNotCreTieIva = true;
                       bdeBasImpIva = bdeBasImpIva.add(bdeAux);
                    }
                 }

                 bdeTotSinImp = objUti.redondearBigDecimal(bdeTotSinImp, objParSis.getDecimalesMostrar());
                 bdeBasImpIva = objUti.redondearBigDecimal(bdeBasImpIva, objParSis.getDecimalesMostrar());
                 strXml += "<totalSinImpuestos>" + bdeTotSinImp + "</totalSinImpuestos> ";
                 
                 if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                 {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                    strXml += "<compensaciones> ";
                    strXml +=   "<compensacion>";
                    strXml +=     "<codigo>1</codigo>"; //Segun PDF del SRI, el valor de 1 corresponde a "Descuento solidario". En algun momento se sugiere reemplazar este codigo quemado.
                    strXml +=     "<tarifa>" + bdePorComSol + "</tarifa>"; //Segun PDF del SRI, este valor corresponde al porcentaje de compensacion.
                    strXml +=     "<valor>" + bdeValComSol + "</valor>"; //Segun PDF del SRI, esta cifra corresponde al valor de compensacion.
                    strXml +=   "</compensacion>";
                    strXml += "</compensaciones>";
                 }
                 
                 if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                 {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                    bdeAux = bdeTotNotCre.subtract(bdeValComSol);
                    strXml += "<valorModificacion>" + bdeAux + "</valorModificacion> ";
                 }
                 else
                 {  
                    strXml += "<valorModificacion>" + bdeTotNotCre + "</valorModificacion> ";
                 }
                 
                 strXml += "<moneda>DOLAR</moneda> "; //En algun momento se sugiere reemplazar este codigo quemado.

                 intCodPorIva = 6; //Se da este valor inicial cuyo significado es "No Objeto de Impuesto" segun el PDF del SRI

                 if (bdePorIva.equals(new BigDecimal("0.00")))
                    intCodPorIva = 0; //Valor especificado en el PDF del SRI
                 else if (bdePorIva.equals(new BigDecimal("12.00")))
                    intCodPorIva = 2; //Valor especificado en el PDF del SRI
                 else if (bdePorIva.equals(new BigDecimal("14.00")))
                    intCodPorIva = 3; //Valor especificado en el PDF del SRI
                 
                 //En intCodPorIva, en algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI

                 strXml += "<totalConImpuestos> ";
                 strXml +=   "<totalImpuesto> ";
                 strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado.
                 
                 if (! bdeValIvaNotCre.equals(new BigDecimal("0.00")))
                    strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                 else
                    strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> ";
                 
                 if (blnNotCreTieIva == true)
                     strXml +=   "<baseImponible>" + bdeBasImpIva + "</baseImponible> ";
                  else
                     strXml +=   "<baseImponible>" + bdeSubNotCre + "</baseImponible> ";
                 
                 strXml +=   "<valor>" + bdeValIvaNotCre + "</valor> ";
                 strXml +=   "</totalImpuesto> ";
                 strXml += "</totalConImpuestos> ";

                 strXml += "<motivo>DEVOLUCION</motivo> "; //Para dejar este codigo quemado se consulto previamente a Eddye Lino
                 strXml += "</infoNotaCredito> ";

                 strXml += "<detalles> ";

                 for (i = 0; i < arlDatDetNotCre.size(); i++)
                 {  strXml += "<detalle> ";
                    strXml += "<codigoInterno>" + objUti.getIntValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_COD_ITM) + "</codigoInterno> ";
                    strXml += "<codigoAdicional>" + objUti.getStringValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_COD_ALT) + "</codigoAdicional> ";
                    strAux = reemplazarCaracter(objUti.getStringValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_NOM_ITM), '&', "&amp;");
                    strXml += "<descripcion>" + strAux + "</descripcion> ";
                    strXml += "<cantidad>" + objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_CAN) + "</cantidad> ";
                    strXml += "<precioUnitario>" + objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_PRE_UNI) + "</precioUnitario> ";
                    strXml += "<descuento>" + objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_VAL_DES) + "</descuento> ";
                    strXml += "<precioTotalSinImpuesto>" + objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</precioTotalSinImpuesto> ";

                    bdeValIvaDet = new BigDecimal("0");

                    if (objUti.getStringValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                    {  bdeSubDet = objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                       bdeValIvaDet = objUti.redondearBigDecimal(bdeSubDet.multiply(bdePorIva), objParSis.getDecimalesMostrar());
                       bdeValIvaDet = bdeValIvaDet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                    }

                    strXml += "<impuestos> ";
                    strXml +=   "<impuesto> ";
                    strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado.
                    
                    if (objUti.getStringValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                    {  strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                       strXml +=   "<tarifa>" + bdePorIva + "</tarifa> ";
                    }
                    else
                    {  strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> "; //Segun PDF del SRI, el valor de 0 se refiete a que no tiene IVA. En algun momento se sugiere reemplazar este codigo quemado
                       strXml +=   "<tarifa>0</tarifa> "; //0 se refiere a 0% de IVA
                    }
                    
                    strXml +=   "<baseImponible>" + objUti.getBigDecimalValueAt(arlDatDetNotCre, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</baseImponible> ";
                    strXml +=   "<valor>" + bdeValIvaDet + "</valor> ";
                    strXml +=   "</impuesto> ";
                    strXml += "</impuestos> ";
                    strXml += "</detalle> ";
                 }

                 strXml += "</detalles> ";
                 blnFound = false;
                 strObs1 = "";
                 //strObs2 = "";

                 if (arlDatCabNotCre.size() > 0)
                 {  strObs1 = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_OBS1);
                    //strObs2 = objUti.getStringValueAt(arlDatCabNotCre, 0, INT_ARL_DAT_CAB_MOV_INV_OBS2);
                    strObs1 = strObs1.trim();
                    //strObs2 = strObs2.trim();
                    if (!strObs1.equals(""))
                       blnFound = true; //Si se ingreso alguna observacion en el campos 'Observacion 1' de la Nota de credito
                 }

                 if (blnFound == true)
                 {  
                    strXml += "<infoAdicional> ";
                    
                    if (!strObs1.equals(""))
                    {  if (strObs1.length() > 300)
                       {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                          //va a haber un error al momento de enviar el XML al SRI.
                          strObs1 = strObs1.substring(0, 300);
                       } 
                       strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional> ";
                    }
                    
                    //*** Inicio bloque comentado el 15/Oct/2018 ***
                    /*
                    if (!strObs2.equals(""))
                       strXml += "<campoAdicional nombre=\"Observacion2\">" + strObs2 + "</campoAdicional> ";
                    */
                    //*** Fin bloque comentado el 15/Oct/2018 ******
                    
                    strXml += "</infoAdicional> ";
                 }

                 strXml += "</notaCredito>";
                 xml.println(strXml);
                 
                 arlRegDetResWS = new ArrayList();
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                 arlResWS.add(arlRegDetResWS);

                 rst.close();
                 rst = null;
                 stm.close();
                 stm = null;
                 xml.println();
                 xml.close();
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;
    } // Funcion armarXMLNotaCredito()
   
   /**
    * Este metodo se encarga de firmar, enviar y autorizar una Nota de credito XML usando los Servicios Web del SRI.
    * @param con Conexion con la base de datos.
    * @strPrmNomEmp Nombre de empresa.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @param arlResWS Arreglo de tipo Array List que devuelve 4 valores, todos de tipo String:
    * <BR>
    * <BR><I>Indice 0:</I> Estado devuelto por el Servicio Web o por alguna excepcion.
    * <BR>Los estados devueltos son:
    * <BR>E: Errores varios: enlace, base de datos, etc. Basicamente almacena la excepción generada.
    * <BR>X: Error en estructura del archivo XML. Se envió el archivo XML pero no pasó el 1er. Servicio Web.
    * <BR>A: El documento fue autorizado.
    * <BR><I>Indice 1:</I> Mensaje devuelto por el Servicio Web o por alguna excepcion.
    * <BR><I>Indice 2:</I> Numero de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 3:</I> Fecha de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 4:</I> Estado (S/N) que indica si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA".
    * <BR><I>Indice 5:</I> Clave de acceso del comprobante XML.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   public boolean getNumAutorizacionNotCre (Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strRutFilNotCre, strAux, strAux2, strEst, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut, strPK;
      int intCodCli;
      File carpeta;
      
      try
      {
         arlResWS.clear();
         emisor = new Emisor();
         strRutFilNotCre = "";
         strEst = "";
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFilNotCre = "/Zafiro/Digitales/Ventas/ZafVen28/" + strPrmNomEmp + "/" + strNomCarXmlPenAut;
         else
            strRutFilNotCre = "C:\\Zafiro\\Digitales\\Ventas\\ZafVen28\\" + strPrmNomEmp + "\\" + strNomCarXmlPenAut;
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de N/C: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         strEst = getEstAutFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
         
         if (strEst.equals("S"))
         {  //Se va a solicitar la autorizacion en linea
            strRutXmlPenAut = "";
            strRutXmlSiAut = "";
            strRutXmlNoAut = "";
         
            //DBN_Prueba
            /*if (! System.getProperty("os.name").equals("Linux"))
            {  strRutXmlPenAut = "//" + strIpSrvPrd;
               strRutXmlSiAut = "//" + strIpSrvPrd;
               strRutXmlNoAut = "//" + strIpSrvPrd;
            }*/

            strNomFilNotCre = "";
            strRutXmlTipCpb = "/Zafiro/Digitales/Ventas/ZafVen28/"; //DBN_real
//            strRutXmlTipCpb = "C:/Zafiro/Digitales/Ventas/ZafVen28/"; //DBN_prueba
            strRutXmlPenAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlPenAut;
            strRutXmlSiAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlSiAut;
            strRutXmlNoAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlNoAut;
            strPK = intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc;
            carpeta = new File(strRutXmlPenAut);
            
            for (File archivo:carpeta.listFiles())
            {  strAux = archivo.getName();
               if (strAux.lastIndexOf(strPK) != -1)
               {  strNomFilNotCre = strAux.substring(0, strAux.lastIndexOf(".xml"));
                  break;
               }
            }
            
            strRutNomFilNotCre = strRutXmlPenAut + File.separator + strNomFilNotCre + ".xml";
            File fOriXml = new File(carpeta + File.separator + strNomFilNotCre + ".xml");
            
            if (invocarServiciosWeb(strRutNomFilNotCre, arlResWS) == true)
            {  if (arlResWS.size() > 0)
               {  if (updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == true)
                  {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilNotCre + ".xml"));
                        strAux = generarPDF(strPrmNomEmp, "NOTCRE", con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFilNotCre);
                        if (strAux.equals("true"))
                        {  //La generacion del PDF se realizo exitosamente
                           intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                           strCorEleCliFacEle = "";
                           strCorEleCliFacEle2 = "";
                           //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                           buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                           if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                           {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                              strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
                              if (!strAux.equals(""))
                              {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux + ". " + strAux2;
                              }
                              else
                              {  //Solo va el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux2;
                              }
                              objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                              updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                           }
                           else
                           {  obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
                              strAux = enviarCorreoServidorInterno(strPrmNomEmp, "NOTCRE", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilNotCre);

                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/  }
                              else
                              {  //Hubo un error en el envio del correo
                                 blnRes = false;
                                 strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                              
                              strAux = enviarCorreoServidorExterno(strPrmNomEmp, "NOTCRE", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilNotCre);
                              
                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/ }
                              else
                              {  //Hubo un error en el envio del correo.
                                 blnRes = false;
                                 strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                                 if (strAux2.equals(""))
                                 {  //Significa que aun no se ha guardado ningun mensaje en el campo tx_deserrfacele. Por tanto, 
                                    //se va a obtener el mensaje devuelto por el Servicio Web
                                    strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 }
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje guardado en strAux2 + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                           } //if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                        }
                        else
                        {  //Hubo un error en la generacion del PDF
                           blnRes = false;
                           strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                           if (!strAux2.equals(""))
                           {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de generacion de PDF
                              strAux = strAux2 + ". " + strAux;
                           }
                           objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                           updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                        } //if (strAux.equals("true"))
                     } //if (strEst.equals("A"))
                     else
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilNotCre + ".xml"));

                     fOriXml.delete();
                  } //if (updateCamposFacEle(...)
               } //if (arlResWS.size() > 0)
            } //if (invocarServiciosWeb(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         else
         { //Solo se va a generar el XML. La autorizacion al SRI se lo hara en diferido.
            if (armarXMLNotaCredito(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilNotCre, arlResWS) == true)
            {  emisor = new Emisor();
               emisor.setRuc(strRucEmp);
               emisor.setToken("BCE_IKEY2032");
               emisor.setTiempoEspera(3);
               strRuc = emisor.getRuc();
               intTimEsp = emisor.getTiempoEspera();

               if (firmarArch_p12(intCodEmp, strRutNomFilNotCre, arlResWS) == true)
                  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               else
               {  blnRes = false;
                  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
            }
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         
         if (strEst.equals("A"))
            System.out.println("\n" + strPrmNomEmp + " => N/C si fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         else
            System.out.println("\n" + strPrmNomEmp + " => N/C no fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener el número de autorización. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println(strPrmNomEmp + " => Error en proceso de N/C: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
      return blnRes;
   } //Metodo getNumAutorizacionNotCre()
   
   private boolean armarXMLComprobanteRetencion(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
		boolean blnRes = true, blnFound;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabCpbRet, arlDatCabCpbRet, arlRegDetCpbRet, arlDatDetCpbRet;
        String strAux, strXml, strNomFilXml, strClvAcc, strDD, strMM, strAAAA, strHor, strMin, strSeg, strFecEmiCpbRet, strAplRet;
        String strNumDocSus, strTipIdeSujRet, strRucSujRet, strNomSujRet, strPerFis, strObs1, strObs2, strNumSerFac, strCodRet;
        int i, intDigCtr, intCodImp, intHor, intMin, intSeg, intCodCli = 0;
        long lngNumCpb, lngCodNum;
        BigDecimal bdeAux, bdePorRet;
        PrintWriter xml;

        try
        {  if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              {  stm = con.createStatement();
                 strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                 strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 17"; //17: Retenciones emitidas
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                    strCodCpb = rst.getString("tx_codCatFacEle");
                  
                 arlDatCabCpbRet = new ArrayList();
                 strSQL =  "SELECT    a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc as fe_doc_cpbret, a1.ne_numdoc1, a2.tx_tipide as tx_tipide_sujret, a1.tx_ruc as tx_ruc_sujret, a1.co_cli, a1.tx_nomcli as tx_nom_sujret, a1.tx_obs1, a1.tx_obs2 ";
                 strSQL += "FROM      tbm_cabpag as a1 ";
                 strSQL += "LEFT JOIN tbm_cli as a2 on a2.co_emp = a1.co_emp and a2.co_cli = a1.co_cli ";
                 strSQL += "WHERE     a1.co_emp = " + intCodEmp;
                 strSQL += "          and a1.co_loc = " + intCodLoc;
                 strSQL += "          and a1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "          and a1.co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                 {  arlRegCabCpbRet = new ArrayList();
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_COD_DOC, "" + rst.getInt("co_doc"));
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_NUM_DOC, "" + rst.getInt("ne_numdoc1"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_doc_cpbret"), "dd/MM/yyyy");
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_FEC_DOC, "" + strAux);
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_TIP_IDE_SUJ_RET, rst.getString("tx_tipide_sujret"));
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_RUC_SUJ_RET, rst.getString("tx_ruc_sujret"));
                    intCodCli = rst.getInt("co_cli");
                    strAux = reemplazarCaracter(rst.getString("tx_nom_sujret"), '&', "&amp;");
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_NOM_SUJ_RET, strAux);
                    
                    strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_OBS1, strAux);
                    
                    strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabCpbRet.add(INT_ARL_DAT_CAB_CPB_RET_OBS2, strAux);
                    
                    arlDatCabCpbRet.add(arlRegCabCpbRet);
                 }

                 arlDatDetCpbRet = new ArrayList();
//                 strSQL =  "SELECT sum(a2.nd_basImp) as nd_basImp, sum(abs(a1.nd_abo)) AS nd_valret, a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, a2.tx_numChq as tx_numFac,";
//                 //strSQL += "a2.fe_recChq as fe_emiFac, ";
//                 strSQL += " a7.fe_venChq as fe_emiFac, ";
//                 strSQL += " a2.tx_codSRI ";
//                 strSQL += "FROM tbm_detPag AS a1 ";
//                 strSQL += "LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_locPag = a2.co_loc AND a1.co_tipDocPag = a2.co_tipDoc AND a1.co_docPag = a2.co_doc AND a1.co_regPag = a2.co_reg) ";
//                 strSQL += "LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp = a3.co_emp AND a2.co_loc = a3.co_loc AND a2.co_tipDoc = a3.co_tipDoc AND a2.co_doc = a3.co_doc) ";
//                 strSQL += "LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp = a4.co_emp AND a2.co_loc = a4.co_loc AND a2.co_tipDoc = a4.co_tipDoc) ";
//                 strSQL += "LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp = a5.co_emp AND a2.co_tipRet = a5.co_tipRet) ";
//                 strSQL += "LEFT OUTER JOIN tbr_detRecDocCabMovInv AS a6 ON (a2.co_emp = a6.co_empRel AND a2.co_loc = a6.co_locRel AND a2.co_tipDoc = a6.co_tipDocRel AND a2.co_doc = a6.co_docRel) ";
//                 strSQL += "LEFT OUTER JOIN tbm_detRecDoc AS a7 ON (a6.co_emp = a7.co_emp AND a6.co_loc = a7.co_loc AND a6.co_tipDoc = a7.co_tipDoc AND a6.co_doc = a7.co_doc) ";
//                 strSQL += "WHERE a3.st_reg in ('A','R','C','F')";
//                 strSQL += " and a2.nd_porRet > 0";
//                 strSQL += " and a2.tx_numChq = a7.tx_numChq"; //Campo tx_numChq tiene el num.Fac.Prv. en las tablas tbm_pagMovInv y tbm_detRecDoc
//                 strSQL += " and a2.st_reg in ('A','C')";
//                 strSQL += " and a6.st_reg = 'A'";
//                 strSQL += " and a7.st_reg = 'A'";
//                 strSQL += " and a1.co_emp = " + intCodEmp;
//                 strSQL += " and a1.co_loc = " + intCodLoc;
//                 strSQL += " and a1.co_tipdoc = " + intCodTipDoc;
//                 strSQL += " and a1.co_doc = " + intCodDoc + " ";
//                 strSQL += "GROUP BY a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, tx_numFac, fe_emiFac, a2.tx_codSRI";
                /* JM: 8/Oct/2020, NO USAR SOLO PARA EL CASO DE LAS RETENCIONES RELACIONADAS QUE DIERON PROBLEMAS POR TUC */
               /* strSQL="";
                strSQL+=" SELECT sum(a1.nd_basImp) as nd_basImp, sum(abs(a1.nd_abo)) AS nd_valret, a1.tx_aplRet, a1.nd_porRet, a1.tx_numSer, a1.tx_numChq as tx_numFac, \n";
                strSQL+="        a1.fe_venChq as fe_emiFac, a1.tx_codSRI   \n";
                strSQL+=" FROM ( \n";
                strSQL+="       SELECT a2.nd_basImp  as nd_basImp,  a1.nd_abo, a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, a2.tx_numChq , a7.fe_venChq , a2.tx_codSRI   \n";
                strSQL+="       FROM tbm_detPag AS a1   \n";
                strSQL+="       LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_locPag = a2.co_loc AND a1.co_tipDocPag = a2.co_tipDoc AND \n";
                strSQL+="                                               a1.co_docPag = a2.co_doc AND a1.co_regPag = a2.co_reg)  \n";
                strSQL+="       LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp = a3.co_emp AND a2.co_loc = a3.co_loc AND a2.co_tipDoc = a3.co_tipDoc AND a2.co_doc = a3.co_doc)   \n";
                strSQL+="       LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp = a4.co_emp AND a2.co_loc = a4.co_loc AND a2.co_tipDoc = a4.co_tipDoc)   \n";
                strSQL+="       LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp = a5.co_emp AND a2.co_tipRet = a5.co_tipRet)  \n";
                strSQL+="       LEFT OUTER JOIN tbr_detRecDocCabMovInv AS a6 ON (a3.co_emp = a6.co_empRel AND a3.co_loc = a6.co_locRel AND a3.co_tipDoc = a6.co_tipDocRel AND \n";
                strSQL+="                                                        a3.co_doc = a6.co_docRel)   \n";
                strSQL+="       LEFT OUTER JOIN tbm_detRecDoc AS a7 ON (a6.co_emp = a7.co_emp AND a6.co_loc = a7.co_loc AND a6.co_tipDoc = a7.co_tipDoc AND a6.co_doc = a7.co_doc) \n";
                strSQL+="       WHERE a3.st_reg in ('A','R','C','F') and a2.nd_porRet > 0 and a2.tx_numChq = a7.tx_numChq  and a2.st_reg in ('A','C')  and a6.st_reg = 'A' and \n";
                strSQL+="              a7.st_reg = 'A' and a1.co_emp = "+intCodEmp+" and a1.co_loc = "+intCodLoc+" and a1.co_tipdoc = "+intCodTipDoc+" and a1.co_doc = "+intCodDoc+"  \n";
                strSQL+="       GROUP BY a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, a2.tx_numChq, a7.fe_venChq, a2.tx_codSRI , a2.nd_basImp , a1.nd_abo  \n";
                strSQL+="    ) as a1  \n";
                strSQL+="    GROUP BY a1.tx_aplRet, a1.nd_porRet, a1.tx_numSer, tx_numFac, fe_emiFac, a1.tx_codSRI      \n";
                strSQL+=" \n";*/
               
               
               //crisC2
            strSQL="";
            strSQL+=" SELECT sum(a1.nd_basImp) as nd_basImp, sum(abs(a1.nd_abo)) AS nd_valret, a1.tx_aplRet, a1.nd_porRet, a1.tx_numSer, a1.tx_numChq as tx_numFac,  \n";
            strSQL+="		a1.fe_venChq as fe_emiFac, a1.tx_codSRI, co_forpag,nd_tot,co_doc,co_tipdoc,fe_autfacele,a1.tx_claaccfacele  \n";
            strSQL+="	 FROM (  \n";
            strSQL+="	       SELECT a2.nd_basImp  as nd_basImp,  a1.nd_abo, a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, a2.tx_numChq , a7.fe_venChq , a2.tx_codSRI ,a3.co_forpag,a3.nd_tot,a1.co_doc,a1.co_tipdoc,  \n";
            strSQL+="	       cast(a8.fe_autfacele as DATE),a8.tx_claaccfacele  \n";

            strSQL+="	       FROM tbm_detPag AS a1     \n";
            strSQL+="	       LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp = a2.co_emp AND a1.co_locPag = a2.co_loc AND a1.co_tipDocPag = a2.co_tipDoc AND  \n";
            strSQL+="						       a1.co_docPag = a2.co_doc AND a1.co_regPag = a2.co_reg)    \n";
            strSQL+="	       LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp = a3.co_emp AND a2.co_loc = a3.co_loc AND a2.co_tipDoc = a3.co_tipDoc AND a2.co_doc = a3.co_doc)    \n";
            strSQL+="	       LEFT OUTER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp = a4.co_emp AND a2.co_loc = a4.co_loc AND a2.co_tipDoc = a4.co_tipDoc)    \n";
            strSQL+="	       LEFT OUTER JOIN tbm_cabTipRet AS a5 ON (a2.co_emp = a5.co_emp AND a2.co_tipRet = a5.co_tipRet)   \n";
            strSQL+="	       LEFT OUTER JOIN tbr_detRecDocCabMovInv AS a6 ON (a3.co_emp = a6.co_empRel AND a3.co_loc = a6.co_locRel AND a3.co_tipDoc = a6.co_tipDocRel AND  \n";
            strSQL+="								a3.co_doc = a6.co_docRel)    \n";
            strSQL+="	       LEFT OUTER JOIN tbm_detRecDoc AS a7 ON (a6.co_emp = a7.co_emp AND a6.co_loc = a7.co_loc AND a6.co_tipDoc = a7.co_tipDoc AND a6.co_doc = a7.co_doc)  \n";
            strSQL+="	       LEFT OUTER JOIN tbm_cabpag AS a8 ON (a1.co_emp = a8.co_emp AND a1.co_loc = a8.co_loc AND a1.co_tipDoc = a8.co_tipDoc AND a1.co_doc = a8.co_doc)  \n";
            strSQL+="	       WHERE a3.st_reg in ('A','R','C','F') and a2.nd_porRet > 0 and a2.tx_numChq = a7.tx_numChq  and a2.st_reg in ('A','C')  and a6.st_reg = 'A' and  \n";
            strSQL+="		      a7.st_reg = 'A' and a1.co_emp = "+intCodEmp+" and a1.co_loc = "+intCodLoc+" and a1.co_tipdoc = "+intCodTipDoc+" and a1.co_doc = "+intCodDoc+" \n";
            strSQL+="	       GROUP BY a2.tx_aplRet, a2.nd_porRet, a2.tx_numSer, a2.tx_numChq, a7.fe_venChq, a2.tx_codSRI , a2.nd_basImp , a1.nd_abo  ,a3.co_forpag,a3.nd_tot,a1.co_doc,a1.co_tipdoc, \n";
            strSQL+="	       a8.fe_autfacele,a8.tx_claaccfacele  \n";
            strSQL+="	    ) as a1   \n";
            strSQL+="	    GROUP BY a1.tx_aplRet, a1.nd_porRet, a1.tx_numSer, tx_numFac, fe_emiFac, a1.tx_codSRI   ,co_forpag,nd_tot,co_doc,co_tipdoc,fe_autfacele,a1.tx_claaccfacele  \n";
            strSQL+="	    order by co_doc desc  \n";
            strSQL+=" \n";
               //cris
			   
                rst = stm.executeQuery(strSQL);
                while (rst.next()){  
                    arlRegDetCpbRet = new ArrayList();
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_EMP, "");
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_LOC, "");
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_TIP_DOC, "");
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_DOC, "");
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_REG, "");
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_basimp"), objParSis.getDecimalesMostrar());
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_BAS_IMP, bdeAux);
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_APL_RET, rst.getString("tx_aplret"));
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_porret"), objParSis.getDecimalesMostrar());
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_POR_RET, "" + bdeAux);
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_valret"), objParSis.getDecimalesMostrar());
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_VAL_RET, bdeAux);
                    strAux = quitarCaracterEspecial(rst.getString("tx_numSer"), "-");
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_NUM_SER_FAC, strAux);
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_NUM_FAC, rst.getString("tx_numFac"));
                    
                    strAux = rst.getString("fe_emiFac") == null? "" :rst.getString("fe_emiFac");
                    
					
					

                    if (!strAux.equals(""))
                    {  strAux = objUti.formatearFecha(rst.getDate("fe_emiFac"), "dd/MM/yyyy");
                       arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_FEC_DOC_FAC, "" + strAux);
                    }
                    else
                       arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_FEC_DOC_FAC, "");
                    
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_COD_SRI, rst.getString("tx_codSRI"));
						  
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_FEC_REG_CON, rst.getString("fe_autfacele"));//OBTIENE LA FECHA DE REGISTRO CONTABLE
                    arlRegDetCpbRet.add(INT_ARL_DAT_DET_CPB_RET_NUM_AUT_SRI, rst.getString("tx_claaccfacele"));//OBTIENE EL NUMERO DE AUT DE LA FACTURAC2
                    //CRIS 
                    arlDatDetCpbRet.add(arlRegDetCpbRet);    
                 }
                 
                 if (verificar_DetXML_DetPag_Ret(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlDatDetCpbRet) == false)
                 {  //Los datos de tbm_detPag y del detalle del XML que se iba a generar no son iguales.
                    arlRegDetResWS = new ArrayList();
                    arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
                    arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Los datos de tbm_detPag y del detalle del XML que se iba a generar no son iguales");
                    arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                    arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                    arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                    arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
                    arlResWS.add(arlRegDetResWS);
                    return false;
                 }

                 strDD = "";
                 strMM = "";
                 strAAAA = "";
                 strHor = "";
                 strMin = "";
                 strSeg = "";
                 strFecEmiCpbRet = "";
                 strTipIdeSujRet = "";
                 strRucSujRet = "";
                 strNomSujRet = "";
                 strPerFis = ""; //Periodo fiscal
                 strAux = "";
                 strCodNum = ""; //Codigo numerico (es secuencial)
                 strNumCpb = ""; //Numero de comprobante
                 strClvAcc = "";
                 strCorEleCliFacEle = "";
                 strCorEleCliFacEle2 = "";

                 if (arlDatCabCpbRet.size() > 0)
                 {  strAux = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_COD_DOC);;;;;
                    lngCodNum = Long.parseLong(strAux);
                    strCodNum = String.format("%08d", lngCodNum); // => "00000001"

                    strAux = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_NUM_DOC);
                    lngNumCpb = Long.parseLong(strAux);
                    strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                    //strNumCpb = "000000004"; //DBN_Prueba
                    strTipIdeSujRet = "00"; //00: Valor invalido para la etiqueta del archivo xml
                    strAux = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_TIP_IDE_SUJ_RET);
                     
                    if (strAux.equals("R"))
                       strTipIdeSujRet = "04"; //Venta con RUC
                    else if (strAux.equals("C"))
                       strTipIdeSujRet = "05"; //Venta con cedula
                    else if (strAux.equals("P"))
                       strTipIdeSujRet = "06"; //Venta con pasaporte
                    else if (strAux.equals("F"))
                       strTipIdeSujRet = "07"; //Venta a consumidor final
                    else if (strAux.equals("E"))
                       strTipIdeSujRet = "08"; //Identificacion del exterior
                     
                    //El valor de strTipIdeSujRet (Tipo de identificacion del sujeto retenido) esta quemado.
                    //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                    
                    strRucSujRet = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_RUC_SUJ_RET);
                    strNomSujRet = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_NOM_SUJ_RET);
                    strFecEmiCpbRet = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_FEC_DOC);
                    //strFecEmiCpbRet = "11/12/2014"; //DBN_Prueba
                    //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                    buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                    strDD = strFecEmiCpbRet.substring(0, 2);
                    strMM = strFecEmiCpbRet.substring(3, 5);
                    strAAAA = strFecEmiCpbRet.substring(6, 10);
                    strPerFis = strMM + "/" + strAAAA;
                    Calendar objCal = new GregorianCalendar();
                    intHor = objCal.get(Calendar.HOUR_OF_DAY);
                    intMin = objCal.get(Calendar.MINUTE);
                    intSeg = objCal.get(Calendar.SECOND);
                    strHor = String.format("%02d", intHor);
                    strMin = String.format("%02d", intMin);
                    strSeg = String.format("%02d", intSeg);

                    strAux =  strDD + strMM + strAAAA;
                    strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                    strClvAcc = strAux;
                    intDigCtr = objMod11.getDigitoControl(strAux);
                    strClvAcc += intDigCtr;
                 } //if (arlDatCabCpbRet.size() > 0);;

                 //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                 //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                 strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                 strNomFilXml += "Retencion_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                 strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                 strNomFilCpbRet = strNomFilXml;

                 xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                 strRutNomFilCpbRet = strRutFil + File.separator + strNomFilXml + ".xml";

                 strXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                 strXml += "<comprobanteRetencion id=\"comprobante\" version=\"2.0.0\">";
                 strXml += "<infoTributaria>";
                 strXml += "<ambiente>" + intTipAmb + "</ambiente>";
                 strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision>";
                 strXml += "<razonSocial>" + strNomEmp + "</razonSocial>";
                 strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial>";
/* Se quita el cambio se regresa a que se ponga el mismo nombre de la empresa 4/Nov/2020 */
/*                if( (intCodEmp==1) && (intCodLoc==13 || intCodLoc==14 || intCodLoc==15 || intCodLoc==16 || intCodLoc==17 || intCodLoc==18 || intCodLoc==23 || intCodLoc==24 || intCodLoc==25 || intCodLoc==26) ){
//                    strXml += "<nombreComercial>" + "CASTEK S.A." + "</nombreComercial> ";
//                }
//                else if ( (intCodEmp==1) && (intCodLoc==19 || intCodLoc==20 || intCodLoc==21 || intCodLoc==22  ) ){
//                    strXml += "<nombreComercial>" + "DIMULTI S.A." + "</nombreComercial> ";
//                }
//                else{
//                    strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                }*/

                 strXml += "<ruc>" + strRucEmp + "</ruc>";
                 strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso>";
                 strXml += "<codDoc>" + strCodCpb + "</codDoc>";
                 strXml += "<estab>" + strCodEst + "</estab>";
                 strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi>";
                 strXml += "<secuencial>" + strNumCpb + "</secuencial>";
                 strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz>";
                 strXml += "</infoTributaria>";

                 strXml += "<infoCompRetencion>";
                 strXml += "<fechaEmision>" + strFecEmiCpbRet + "</fechaEmision>";
                 strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento>";
                 
                 if (!strConEsp.equals(""))
                 {
                    strXml += "<contribuyenteEspecial>" + strConEsp + "</contribuyenteEspecial> ";
                 }
                 
                 strXml += "<obligadoContabilidad>SI</obligadoContabilidad>"; //En algun momento se sugiere reemplazar este codigo quemado
                 strXml += "<tipoIdentificacionSujetoRetenido>" + strTipIdeSujRet + "</tipoIdentificacionSujetoRetenido>";
                 if (strTipIdeSujRet.equals("08"))
                 {
                     strXml += "<tipoSujetoRetenido>01</tipoSujetoRetenido>";//SUJETO A RETENCION
                 }    
                 
                 strXml += "<parteRel>NO</parteRel>";//PARTEREL
                 //CRIS		
				 
                 strXml += "<razonSocialSujetoRetenido>" + strNomSujRet + "</razonSocialSujetoRetenido>";
                 strXml += "<identificacionSujetoRetenido>" + strRucSujRet + "</identificacionSujetoRetenido>";
                 strXml += "<periodoFiscal>" + strPerFis + "</periodoFiscal> ";
                 strXml += "</infoCompRetencion> ";

                 strXml += "<docsSustento>";  
                 strXml += "<docSustento>";
                 strXml += "<codSustento>01</codSustento>"; 
                 strXml += "<codDocSustento>01</codDocSustento>"; //01: Factura. En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                 strNumSerFac = objUti.getStringValueAt(arlDatDetCpbRet, 0, INT_ARL_DAT_DET_CPB_RET_NUM_SER_FAC);
                 strNumDocSus = strNumSerFac + objUti.getStringValueAt(arlDatDetCpbRet, 0, INT_ARL_DAT_DET_CPB_RET_NUM_FAC);
                 strXml += "<numDocSustento>" + strNumDocSus + "</numDocSustento>";
                 strAux = objUti.getStringValueAt(arlDatDetCpbRet, 0, INT_ARL_DAT_DET_CPB_RET_FEC_DOC_FAC);
//                 strNumDocSus = objUti.getStringValueAt(arlDatDetCpbRet, 0, INT_ARL_DAT_DET_CPB_RET_NUM_AUT_SRI);
                    if (!strAux.equals(""))
                     { strXml += "<fechaEmisionDocSustento>" + strAux + "</fechaEmisionDocSustento>"; //En PDF del SRI dice "Obligatorio cuando corresponda"
                       //strFecEmiCpbRet = objUti.getStringValueAt(arlDatDetCpbRet, 0, INT_ARL_DAT_DET_CPB_RET_FEC_REG_CON);
                       strXml += "<fechaRegistroContable>" + strAux + "</fechaRegistroContable>"; //OJOC2
                     } 
                 
                 strXml += "<numAutDocSustento>" + strNumDocSus + "</numAutDocSustento>"; //OJO    
                 strXml += "<pagoLocExt>01</pagoLocExt>";
                 BigDecimal totalBaseImponible = new BigDecimal(0),baseImponible; 
                 BigDecimal totalValorRe = new BigDecimal(0),ValorRe;
                 BigDecimal totalImporte = new BigDecimal(0);
                 BigDecimal totalSimImpuesto = new BigDecimal(0),SimImpuesto;
                 
                 for (i = 0; i < arlDatDetCpbRet.size(); i++)
                 {  
                     strAplRet = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_APL_RET);
                      if (strAplRet.equals("S"))
                      {
                          totalSimImpuesto =totalSimImpuesto .add(objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_BAS_IMP), objParSis.getDecimalesMostrar()));  
                      }
                         baseImponible =  objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_BAS_IMP), objParSis.getDecimalesMostrar());
                         totalBaseImponible =totalBaseImponible .add(baseImponible);  
                         ValorRe =  objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_VAL_RET), objParSis.getDecimalesMostrar());
                         totalValorRe =totalValorRe.add(ValorRe);  
                    
                 }
                 totalImporte = totalBaseImponible.subtract(totalValorRe);  
                 strXml += "<totalSinImpuestos>" + totalSimImpuesto + "</totalSinImpuestos>"; 
                 strXml += "<importeTotal>" + totalImporte + "</importeTotal>"; 
                 strXml += "<impuestosDocSustento>";
                  for (i = 0; i < arlDatDetCpbRet.size(); i++)
                  {
                      strAplRet = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_APL_RET);
                         if (strAplRet.equals("S"))
                         {
                                strXml += "<impuestoDocSustento>";
                                intCodImp=2;
                              if (strAplRet.equals("I"))
                                if (strAplRet.equals("I"))
                                   intCodImp = 2; //2: IVA.
                                else if (strAplRet.equals("S") || strAplRet.equals("O"))
                                   intCodImp = 1; //1: RENTA.
                                else
                                   intCodImp = 0; //0: Valor invalido para la etiqueta del archivo xml


                            bdePorRet = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_POR_RET), objParSis.getDecimalesMostrar());
                            strCodRet = "0"; //0: Valor invalido para la etiqueta del archivo xml

                            if (strAplRet.equals("I"))
                            {  if (bdePorRet.compareTo(new BigDecimal("30.00")) == 0)
                                  strCodRet = "1"; //1: 30%
                               else if (bdePorRet.compareTo(new BigDecimal("70.00")) == 0)
                                  strCodRet = "2"; //2: 70%
                               else if (bdePorRet.compareTo(new BigDecimal("100.00")) == 0)
                                  strCodRet = "3"; //3: 100%
                               else if (bdePorRet.compareTo(new BigDecimal("10.00")) == 0)
                                  strCodRet = "9"; //9: 10%
                               else if (bdePorRet.compareTo(new BigDecimal("20.00")) == 0)
                                  strCodRet = "10"; //10: 20%
                            }
                            else if (strAplRet.equals("S") || strAplRet.equals("O"))
                               strCodRet = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_COD_SRI);

                                strXml += "<codImpuestoDocSustento>" + intCodImp + "</codImpuestoDocSustento>";
                                strXml += "<codigoPorcentaje>" + intCodImp + "</codigoPorcentaje>";
                                bdeAux = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_BAS_IMP), objParSis.getDecimalesMostrar());
                                strXml += "<baseImponible>" + bdeAux + "</baseImponible>";
                                bdePorRet = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_POR_RET), objParSis.getDecimalesMostrar());
                                strXml += "<tarifa>12.00</tarifa>";
                                //bdeAux =     objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_VAL_RET), objParSis.getDecimalesMostrar());
                                bdeAux = objUti.redondearBigDecimal(bdeAux.multiply(new BigDecimal((double)12/100)),2);
                               // bdeAux = bdeAux.multiply(new BigDecimal((double)12/100));
                                
                                strXml += "<valorImpuesto>" + bdeAux + "</valorImpuesto>";
                                strXml += "</impuestoDocSustento>";
                         }
                  }
                strXml += "</impuestosDocSustento>";
                  
                  
                  
                  ///retenciones
                  strXml += "<retenciones>";

                 for (i = 0; i < arlDatDetCpbRet.size(); i++)
                 {  strXml += "<retencion>";
                    strAplRet = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_APL_RET);
                    
                    if (strAplRet.equals("I"))
                       intCodImp = 2; //2: IVA.
                    else if (strAplRet.equals("S") || strAplRet.equals("O"))
                       intCodImp = 1; //1: RENTA.
                    else
                       intCodImp = 0; //0: Valor invalido para la etiqueta del archivo xml
                    
                    //Cod. impuesto 6 = ISD, segun PDF del SRI.
                    //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                    
                    strXml += "<codigo>" + intCodImp + "</codigo>";
                    bdePorRet = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_POR_RET), objParSis.getDecimalesMostrar());
                    strCodRet = "0"; //0: Valor invalido para la etiqueta del archivo xml
                    
                    if (strAplRet.equals("I"))
                    {  if (bdePorRet.compareTo(new BigDecimal("30.00")) == 0)
                          strCodRet = "1"; //1: 30%
                       else if (bdePorRet.compareTo(new BigDecimal("70.00")) == 0)
                          strCodRet = "2"; //2: 70%
                       else if (bdePorRet.compareTo(new BigDecimal("100.00")) == 0)
                          strCodRet = "3"; //3: 100%
                       else if (bdePorRet.compareTo(new BigDecimal("10.00")) == 0)
                          strCodRet = "9"; //9: 10%
                       else if (bdePorRet.compareTo(new BigDecimal("20.00")) == 0)
                          strCodRet = "10"; //10: 20%
                    }
                    else if (strAplRet.equals("S") || strAplRet.equals("O"))
                       strCodRet = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_COD_SRI);
                    
                    //En strCodRet, en algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                    
                    //DBN_Prueba
                    //if (strAplRet.equals("S"))
                    //   strCodRet = "341"; //341: Otras retenciones aplicables el 2% => 2% RF
                    
                    strXml += "<codigoRetencion>" + strCodRet + "</codigoRetencion>";
                    bdeAux = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_BAS_IMP), objParSis.getDecimalesMostrar());
                    strXml += "<baseImponible>" + bdeAux + "</baseImponible>";
                    strXml += "<porcentajeRetener>" + bdePorRet + "</porcentajeRetener>";
                    bdeAux = objUti.redondearBigDecimal(objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_VAL_RET), objParSis.getDecimalesMostrar());
                    strXml += "<valorRetenido>" + bdeAux + "</valorRetenido>";
//                    strXml += "<codDocSustento>01</codDocSustento> "; //01: Factura. En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
//                  strNumSerFac = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_NUM_SER_FAC);
//                  strNumDocSus = strNumSerFac + objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_NUM_FAC);
//                  strXml += "<numDocSustento>" + strNumDocSus + "</numDocSustento> ";
//                   
//                  strAux = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_FEC_DOC_FAC);
//                  if (!strAux.equals(""))
//                     strXml += "<fechaEmisionDocSustento>" + strAux + "</fechaEmisionDocSustento> "; //En PDF del SRI dice "Obligatorio cuando corresponda"
//                    
//                  strXml += "</impuesto> ";
                    strXml += "</retencion>";
                 } //for (i = 0; i < arlDatDetCpbRet.size(); i++)

                 strXml += "</retenciones>";
				 
				 

		getCodForPag(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);//SE OBTIENE EL CODIGO DE PAGO REFERENCIAL CON RESPECTO AL SRI
                 
																																				
				 
                   strXml += "<pagos>";
                   strXml += "<pago>";
                   strXml += "<formaPago>" + strCodForPagSri + "</formaPago>";
                   strXml += "<total>" + totalImporte + "</total>";
                   strXml += "</pago>";
                   strXml += "</pagos>";
                   strXml += "</docSustento>";
                   strXml += "</docsSustento>";  
                 blnFound = false;
                 strObs1 = "";
                 strObs2 = "";

                 if (arlDatCabCpbRet.size() > 0)
                 {  strObs1 = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_OBS1);
                    strObs2 = objUti.getStringValueAt(arlDatCabCpbRet, 0, INT_ARL_DAT_CAB_CPB_RET_OBS2);
                    strObs1 = strObs1.trim();
                    strObs2 = strObs2.trim();
                    if (!strObs1.equals("") || !strObs2.equals(""))
                       blnFound = true; //Si se ingreso alguna observacion en algunos de los campos 'Observacion' del Comprobante de retencion
                 }
                   
                 if (blnFound == true)
                 {  
                    strXml += "<infoAdicional>";
                    if (!strObs1.equals("")){   
                        if (strObs1.length() > 300)
                        {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                           //va a haber un error al momento de enviar el XML al SRI.
                           strObs1 = strObs1.substring(0, 300);
                        }
                        strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional>";
                    }
                    
                    if (!strObs2.equals(""))
                    {   if (strObs2.length() > 300)
                        {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                           //va a haber un error al momento de enviar el XML al SRI.
                           strObs2 = strObs2.substring(0, 300);
                        }
                        strXml += "<campoAdicional nombre=\"Observacion2\">" + strObs2 + "</campoAdicional>";
                    }
                    strXml += "</infoAdicional>";
                    
                 }
                 
                 strXml += "</comprobanteRetencion>";
                 xml.println(strXml);
                 
                System.out.println("JOTA!!! " + strXml.toString());
                  
                 arlRegDetResWS = new ArrayList();
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                 arlResWS.add(arlRegDetResWS);

                 rst.close();
                 rst = null;
                 stm.close();
                 stm = null;
                 xml.println();
                 xml.close();
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;								
   } // Funcion armarXMLComprobanteRetencion()
   
   /**
    * Este metodo se encarga de firmar, enviar y autorizar un Comprobante de retencion XML usando los Servicios Web del SRI.
    * @param con Conexion con la base de datos.
    * @strPrmNomEmp Nombre de empresa.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @param arlResWS Arreglo de tipo Array List que devuelve 4 valores, todos de tipo String:
    * <BR>
    * <BR><I>Indice 0:</I> Estado devuelto por el Servicio Web o por alguna excepcion.
    * <BR>Los estados devueltos son:
    * <BR>E: Errores varios: enlace, base de datos, etc. Basicamente almacena la excepción generada.
    * <BR>X: Error en estructura del archivo XML. Se envió el archivo XML pero no pasó el 1er. Servicio Web.
    * <BR>A: El documento fue autorizado.
    * <BR><I>Indice 1:</I> Mensaje devuelto por el Servicio Web o por alguna excepcion.
    * <BR><I>Indice 2:</I> Numero de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 3:</I> Fecha de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 4:</I> Estado (S/N) que indica si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA".
    * <BR><I>Indice 5:</I> Clave de acceso del comprobante XML.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   public boolean getNumAutorizacionCpbRet (Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strRutFilCpbRet, strAux, strAux2, strEst, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut, strPK;
      int intCodCli;
      File carpeta;
      
      try
      {
         arlResWS.clear();
         emisor = new Emisor();
         strRutFilCpbRet = "";
         strEst = "";
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFilCpbRet = "/Zafiro/Digitales/Contabilidad/ZafCon05/" + strPrmNomEmp + "/" + strNomCarXmlPenAut;
         else
            strRutFilCpbRet = "C:\\Zafiro\\Digitales\\Contabilidad\\ZafCon05\\" + strPrmNomEmp + "\\" + strNomCarXmlPenAut;
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de Ret: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         strEst = getEstAutFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabpag");
         
         if (strEst.equals("S"))
         {  //Se va a solicitar la autorizacion en linea
            strRutXmlPenAut = "";
            strRutXmlSiAut = "";
            strRutXmlNoAut = "";
         
            //DBN_Prueba
            /*if (! System.getProperty("os.name").equals("Linux"))
            {  strRutXmlPenAut = "//" + strIpSrvPrd;
               strRutXmlSiAut = "//" + strIpSrvPrd;
               strRutXmlNoAut = "//" + strIpSrvPrd;
            }*/

            strNomFilCpbRet = "";
            strRutXmlTipCpb = "//172.16.1.2/Zafiro/Digitales/Contabilidad/ZafCon05/"; //DBN_real
//            strRutXmlTipCpb = "C:/Zafiro/Digitales/Contabilidad/ZafCon05/"; //DBN_prueba
            strRutXmlPenAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlPenAut;
            strRutXmlSiAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlSiAut;
            strRutXmlNoAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlNoAut;
            strPK = intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc;
            
            
            carpeta = new File(strRutXmlPenAut);
            File[] archivo1 = carpeta.listFiles();
            
            for (File archivo:archivo1)
            {  strAux = archivo.getName();
               if (strAux.lastIndexOf(strPK) != -1)
               {  strNomFilCpbRet = strAux.substring(0, strAux.lastIndexOf(".xml"));
                  break;
               }
            }
            
            strRutNomFilCpbRet = strRutXmlPenAut + File.separator + strNomFilCpbRet + ".xml";
            File fOriXml = new File(carpeta + File.separator + strNomFilCpbRet + ".xml");
            
            if (invocarServiciosWeb(strRutNomFilCpbRet, arlResWS) == true)
            {  if (arlResWS.size() > 0)
               {  if (updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == true)
                  {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilCpbRet + ".xml"));
                        strAux = generarPDF(strPrmNomEmp, "RET", con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFilCpbRet);
                        if (strAux.equals("true"))
                        {  //La generacion del PDF se realizo exitosamente
                           intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabpag");
                           strCorEleCliFacEle = "";
                           strCorEleCliFacEle2 = "";
                           //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                           buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                           if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                           {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                              strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
                              if (!strAux.equals(""))
                              {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux + ". " + strAux2;
                              }
                              else
                              {  //Solo va el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux2;
                              }
                              objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                              updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                           }
                           else
                           {  obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
                              strAux = enviarCorreoServidorInterno(strPrmNomEmp, "RET", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilCpbRet);

                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/  }
                              else
                              {  //Hubo un error en el envio del correo
                                 blnRes = false;
                                 strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                              
                              strAux = enviarCorreoServidorExterno(strPrmNomEmp, "RET", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilCpbRet);
                              
                              if (strAux.equals("true"))
                              {  /*int Envio_de_correo_OK = 0;*/ }
                              else
                              {  //Hubo un error en el envio del correo.
                                 blnRes = false;
                                 strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabpag");
                                 if (strAux2.equals(""))
                                 {  //Significa que aun no se ha guardado ningun mensaje en el campo tx_deserrfacele. Por tanto, 
                                    //se va a obtener el mensaje devuelto por el Servicio Web
                                    strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 }
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje guardado en strAux2 + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                           } //if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                        }
                        else
                        {  //Hubo un error en la generacion del PDF
                           blnRes = false;
                           strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                           if (!strAux2.equals(""))
                           {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de generacion de PDF
                              strAux = strAux2 + ". " + strAux;
                           }
                           objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                           updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                        } //if (strAux.equals("true"))
                     } //if (strEst.equals("A"))
                     else
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilCpbRet + ".xml"));

                     fOriXml.delete();
                  } //if (updateCamposFacEle(...)
               } //if (arlResWS.size() > 0)
            } //if (invocarServiciosWeb(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         else
         { //Solo se va a generar el XML. La autorizacion al SRI se lo hara en diferido.
            if (armarXMLComprobanteRetencion(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilCpbRet, arlResWS) == true)
            {  emisor = new Emisor();
               emisor.setRuc(strRucEmp);
               emisor.setToken("BCE_IKEY2032");
               emisor.setTiempoEspera(3);
               strRuc = emisor.getRuc();
               intTimEsp = emisor.getTiempoEspera();

               if (firmarArch_p12(intCodEmp, strRutNomFilCpbRet, arlResWS) == true)
                  updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               else
               {  blnRes = false;
                  updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
            }
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         
         if (strEst.equals("A"))
            System.out.println("\n" + strPrmNomEmp + " => Ret. si fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         else
            System.out.println("\n" + strPrmNomEmp + " => Ret. no fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener el número de autorización. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, "tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println(strPrmNomEmp + " => Error en proceso de Ret: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
   return blnRes;
   } //Metodo getNumAutorizacionCpbRet()
   
   private boolean armarXMLLiqCom(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
        boolean blnRes = true, blnLiqComTieIva;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabLiqCom, arlDatCabLiqCom, arlRegDetLiqCom, arlDatDetLiqCom;
        String strAux, strNomFilXml, strXml, strClvAcc, strTipIdePrv, strFecEmi, strIdePrv, strRazSocPrv;
        String strDD, strMM, strAAAA, strHor, strMin, strSeg, strRucPrv;
        int i, intDigCtr, intCodPorIva, intHor, intMin, intSeg;
        long lngNumCpb, lngCodNum;
        BigDecimal bdeAux, bdeTotSinImp, bdeBasImpIva, bdePorIva, bdeSubDet, bdeValIvaDet, bdeSubLiqCom, bdeValIvaLiqCom, bdeTotLiqCom;
        PrintWriter xml;

        try
        {  
           if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              {   stm = con.createStatement();
                  //intCodCli = 0; //DBN_borrar
                  strRucPrv = "";
                  strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                  strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 15"; //15: Liquidaciones de compras
                  rst = stm.executeQuery(strSQL);

                  if (rst.next())
                     strCodCpb = rst.getString("tx_codCatFacEle");
                     
                  arlDatCabLiqCom = new ArrayList();
                  strSQL =  "SELECT    a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.st_autFacEle, a1.fe_ing, a1.ne_numdoc, a1.fe_doc, a1.tx_nomcli, a1.tx_ruc as tx_rucCli, a2.tx_tipide as tx_tipIdePrv, a1.nd_poriva, abs(a1.nd_sub) as nd_sub, abs(a1.nd_valiva) as nd_valiva, abs(a1.nd_tot) as nd_tot ";
                  strSQL += "FROM      tbm_cabmovinv as a1 ";
                  strSQL += "LEFT JOIN tbm_cli as a2 on a2.co_emp = a1.co_emp and a2.co_cli = a1.co_cli ";
                  strSQL += "WHERE     a1.co_emp = " + intCodEmp;
                  strSQL += "          and a1.co_loc = " + intCodLoc;
                  strSQL += "          and a1.co_tipdoc = " + intCodTipDoc;
                  strSQL += "          and a1.co_doc = " + intCodDoc;
                  rst = stm.executeQuery(strSQL);

                  if (rst.next())
                  {  arlRegCabLiqCom = new ArrayList();
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_NUM_DOC, "" + rst.getInt("ne_numdoc")); //DBN_real
                     //arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_NUM_DOC, rst.getInt("ne_numdoc") + 1); //DBN_prueba. Borrar al pasar a Produccion
                     strAux = objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy");
                     //strAux = "11/09/2019"; //DBN_prueba
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC, "" + strAux);
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_NUM_FAC, "");
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_FEC_DOC_FAC, "");
                     //intCodCli = rst.getInt("co_cli"); //DBN_borrar
                     strAux = reemplazarCaracter(rst.getString("tx_nomcli"), '&', "&amp;");
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_NOM_CLI, strAux);
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_RUC_CLI, rst.getString("tx_ruccli"));
                     strRucPrv = rst.getString("tx_ruccli");
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI, rst.getString("tx_tipIdePrv"));
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva"), objParSis.getDecimalesMostrar());
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_POR_IVA, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_sub"), objParSis.getDecimalesMostrar());
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_VAL_SUB, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_valiva"), objParSis.getDecimalesMostrar());
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_VAL_IVA, "" + bdeAux);
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_tot"), objParSis.getDecimalesMostrar());
                     arlRegCabLiqCom.add(INT_ARL_DAT_CAB_MOV_INV_VAL_TOT, "" + bdeAux);
                     arlDatCabLiqCom.add(arlRegCabLiqCom);
                     /*
                     strAux = rst.getString("nd_porComSol") == null? "" :rst.getString("nd_porComSol");
                     
                     if (strAux.equals(""))
                        bdeAux = new BigDecimal("0.00");
                     else
                     {  bdeAux = new BigDecimal(strAux);
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                     }
                     
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL, "" + bdeAux);
                     
                     strAux = rst.getString("nd_valComSol") == null? "" :rst.getString("nd_valComSol");
                     
                     if (strAux.equals(""))
                        bdeAux = new BigDecimal("0.00");
                     else
                     {  bdeAux = new BigDecimal(strAux);
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                     }
                     
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL, "" + bdeAux);
                     
                     strNumGuiRem = rst.getString("tx_numguirem") == null? "" :rst.getString("tx_numguirem");
                     strNumPed = rst.getString("tx_numped") == null? "" :rst.getString("tx_numped");
                     
                     strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                     if (!strAux.equals(""))
                     {  strAux = reemplazarCaracter(strAux, '\n', " ");
                        strAux = reemplazarCaracter(strAux, '&', "&amp;");
                        strAux = reemplazarCaracter(strAux, '<', "&lt;");
                        strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     }
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_OBS1, strAux);
                     
                     strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                     if (!strAux.equals(""))
                     {  strAux = reemplazarCaracter(strAux, '\n', " ");
                        strAux = reemplazarCaracter(strAux, '&', "&amp;");
                        strAux = reemplazarCaracter(strAux, '<', "&lt;");
                        strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     }
                     arlRegCabFac.add(INT_ARL_DAT_CAB_MOV_INV_OBS2, strAux);
                     
                     arlDatCabFac.add(arlRegCabFac);*/ //DBN_borrar
                  } //if (rst.next())

                  arlDatDetLiqCom = new ArrayList();
                  strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_des as tx_nomItm, abs(nd_can) as nd_can, nd_preuni, round((abs(nd_can) * nd_preuni), 2) as nd_subtotdet, abs(nd_tot) as nd_totdet, st_iva ";
                  strSQL += "FROM   tbm_detConIntMovInv ";
                  strSQL += "WHERE  co_emp = " + intCodEmp;
                  strSQL += "       and co_loc = " + intCodLoc;
                  strSQL += "       and co_tipdoc = " + intCodTipDoc;
                  strSQL += "       and co_doc = " + intCodDoc;
                  rst = stm.executeQuery(strSQL);

                  while (rst.next())
                  {  arlRegDetLiqCom = new ArrayList();
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_EMP, "" + rst.getInt("co_emp"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_LOC, "" + rst.getInt("co_loc"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_DOC, "" + rst.getInt("co_doc"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_REG, "" + rst.getInt("co_reg"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_ITM, "" + rst.getInt("co_reg"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_COD_ALT, "");
                     
                     strAux = rst.getString("tx_nomItm");
                     strAux = reemplazarCaracter(strAux, '&', "&amp;");
                     strAux = reemplazarCaracter(strAux, '<', "&lt;");
                     strAux = reemplazarCaracter(strAux, '>', "&gt;");
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_NOM_ITM, strAux);
                     
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesMostrar());
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_CAN, "" + bdeAux);
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_PRE_UNI, "" + rst.getBigDecimal("nd_preuni"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_SUB_DET, "" + rst.getBigDecimal("nd_subtotdet"));
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_POR_DES, "");
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_VAL_DES, "");
                     bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_totdet"), objParSis.getDecimalesMostrar());
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_TOT_DET, "" + bdeAux);
                     arlRegDetLiqCom.add(INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM, rst.getString("st_iva"));
                     arlDatDetLiqCom.add(arlRegDetLiqCom);    
                  }

                  strFecEmi = "";
                  strDD = "";
                  strMM = "";
                  strAAAA = "";
                  strHor = "";
                  strMin = "";
                  strSeg = "";
                  strIdePrv = "";
                  strTipIdePrv = ""; //Tipo de identificacion del proveedor
                  strRazSocPrv = ""; //Razon social del proveedor
                  strAux = "";
                  strCodNum = ""; //Codigo numerico (es secuencial)
                  strNumCpb = ""; //Numero de comprobante
                  strClvAcc = "";
                  /*strCorEleCliFacEle = "";
                  strCorEleCliFacEle2 = "";*/ //DBN_borrar
                  bdePorIva = new BigDecimal("0");
                  bdeSubLiqCom = new BigDecimal("0");
                  bdeValIvaLiqCom = new BigDecimal("0");
                  bdeTotLiqCom = new BigDecimal("0");

                  if (arlDatCabLiqCom.size() > 0)
                  {  strAux = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_COD_DOC);
                     lngCodNum = Long.parseLong(strAux);
                     strCodNum = String.format("%08d", lngCodNum); // => "00000001"
                     strAux = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_NUM_DOC);
                     lngNumCpb = Long.parseLong(strAux);
                     strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                     //strNumCpb = "000000002"; // => "000000001" //DBN_Prueba
                     strFecEmi = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_FEC_DOC);
                     //strFecEmi = "12/12/2014"; //DBN_Prueba
                     strDD = strFecEmi.substring(0, 2);
                     strMM = strFecEmi.substring(3, 5);
                     strAAAA = strFecEmi.substring(6, 10);
                     Calendar objCal = new GregorianCalendar();
                     intHor = objCal.get(Calendar.HOUR_OF_DAY);
                     intMin = objCal.get(Calendar.MINUTE);
                     intSeg = objCal.get(Calendar.SECOND);
                     strHor = String.format("%02d", intHor);
                     strMin = String.format("%02d", intMin);
                     strSeg = String.format("%02d", intSeg);
                     strIdePrv = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_RUC_CLI);
                     /*
                     //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                     buscarCorEleCliFacEle(con, intCodEmp, intCodCli); */ //DBN_borrar
                     strRazSocPrv = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_NOM_CLI);
                     strTipIdePrv = "00"; //00: Valor invalido para la etiqueta del archivo xml
                     strAux = objUti.getStringValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_TIP_IDE_CLI);
                     
                     if (strAux.equals("R"))
                        strTipIdePrv = "04"; //RUC
                     else if (strAux.equals("C"))
                        strTipIdePrv = "05"; //Cedula
                     else if (strAux.equals("P"))
                        strTipIdePrv = "06"; //Pasaporte
                     else if (strAux.equals("F"))
                        strTipIdePrv = "07"; //Consumidor final
                     else if (strAux.equals("E"))
                        strTipIdePrv = "08"; //Identificacion del exterior
                     
                     //El valor de strTipIdePrv (Tipo de identificacion del proveedor) esta quemado.
                     //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI.
                     
                     bdePorIva = objUti.getBigDecimalValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_POR_IVA);
                     bdeSubLiqCom = objUti.getBigDecimalValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_SUB);
                     bdeValIvaLiqCom = objUti.getBigDecimalValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_IVA);
                     bdeValIvaLiqCom = objUti.redondearBigDecimal(bdeValIvaLiqCom, objParSis.getDecimalesMostrar());
                     bdeTotLiqCom = objUti.getBigDecimalValueAt(arlDatCabLiqCom, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_TOT);
                     /*bdePorComSol = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_POR_COM_SOL);
                     bdeValComSol = objUti.getBigDecimalValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_VAL_COM_SOL);*/ //DBN_borrar

                     strAux =  strDD + strMM + strAAAA;
                     strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                     strClvAcc = strAux;
                     intDigCtr = objMod11.getDigitoControl(strAux);
                     strClvAcc += intDigCtr;
                  } //if (arlDatCabFac.size() > 0)
                  
                  //La funcion getCodForPag() se encarga de llenar las variables globales strCodForPagSri, strDesForPagSri y intNumDiaPlaMax
                  getCodForPag(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);

                  //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                  //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                  strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                  strNomFilXml += "LiqCom_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                  strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                  strNomFilLiqCom = strNomFilXml;
                  
                  xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                  strRutNomFilLiqCom = strRutFil + File.separator + strNomFilXml + ".xml";

                  strXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
                  strXml += "<liquidacionCompra id=\"comprobante\" version=\"1.1.0\"> ";
                  strXml += "<infoTributaria> ";
                  strXml += "<ambiente>" + intTipAmb + "</ambiente> ";
                  strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision> ";
                  strXml += "<razonSocial>" + strNomEmp + "</razonSocial> ";
                  strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                if( (intCodEmp==1) && (intCodLoc==13 || intCodLoc==14 || intCodLoc==15 || intCodLoc==16 || intCodLoc==17 || intCodLoc==18 || intCodLoc==23 || intCodLoc==24 || intCodLoc==25 || intCodLoc==26) ){
//                    strXml += "<nombreComercial>" + "CASTEK S.A." + "</nombreComercial> ";
//                }
//                else if ( (intCodEmp==1) && (intCodLoc==19 || intCodLoc==20 || intCodLoc==21 || intCodLoc==22  ) ){
//                    strXml += "<nombreComercial>" + "DIMULTI S.A." + "</nombreComercial> ";
//                }
//                else{
//                    strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
//                }

                  strXml += "<ruc>" + strRucEmp + "</ruc> ";
                  strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso> ";
                  strXml += "<codDoc>" + strCodCpb + "</codDoc> ";
                  strXml += "<estab>" + strCodEst + "</estab> ";
                  strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi> ";
                  strXml += "<secuencial>" + strNumCpb + "</secuencial> ";
                  strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz> ";
                  strXml += "</infoTributaria> ";

                  strXml += "<infoLiquidacionCompra> ";
                  strXml += "<fechaEmision>" + strFecEmi + "</fechaEmision> ";
                  strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento> ";
                  
                  if (!strConEsp.equals(""))
                  {
                     strXml += "<contribuyenteEspecial>" + strConEsp + "</contribuyenteEspecial> ";
                  }
                  
                  strXml += "<obligadoContabilidad>SI</obligadoContabilidad> "; //En algun momento se sugiere reemplazar este codigo quemado
                  strXml += "<tipoIdentificacionProveedor>" + strTipIdePrv + "</tipoIdentificacionProveedor> ";
                  strXml += "<razonSocialProveedor>" + strRazSocPrv + "</razonSocialProveedor> ";
                  strXml += "<identificacionProveedor>" + strIdePrv + "</identificacionProveedor> ";

                  bdeTotSinImp = new BigDecimal("0");
                  bdeBasImpIva = new BigDecimal("0");
                  blnLiqComTieIva = false;

                  for (i = 0; i < arlDatDetLiqCom.size(); i++)
                  {  //bdeAux = objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                     bdeAux = objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_SUB_DET); //DBN_ojo
                     bdeTotSinImp = bdeTotSinImp.add(bdeAux);
                     
                     if (objUti.getStringValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  blnLiqComTieIva = true;
                        bdeBasImpIva = bdeBasImpIva.add(bdeAux);
                     }
                     /*
                     bdeAux = objUti.getBigDecimalValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_VAL_DES);
                     bdeTotDes = bdeTotDes.add(bdeAux); */ //DBN_borrar
                  }

                  bdeTotSinImp = objUti.redondearBigDecimal(bdeTotSinImp, objParSis.getDecimalesMostrar());
                  //bdeTotDes = objUti.redondearBigDecimal(bdeTotDes, objParSis.getDecimalesMostrar()); //DBN_borrar
                  bdeBasImpIva = objUti.redondearBigDecimal(bdeBasImpIva, objParSis.getDecimalesMostrar());
                  strXml += "<totalSinImpuestos>" + bdeTotSinImp + "</totalSinImpuestos> ";
                  strXml += "<totalDescuento>0.00</totalDescuento>";

                  intCodPorIva = 6; //Se da este valor inicial cuyo significado es "No Objeto de Impuesto" segun el PDF del SRI

                  if (bdePorIva.equals(new BigDecimal("0.00")))
                     intCodPorIva = 0; //Valor especificado en el PDF del SRI
                  else if (bdePorIva.equals(new BigDecimal("12.00")))
                     intCodPorIva = 2; //Valor especificado en el PDF del SRI
                  else if (bdePorIva.equals(new BigDecimal("14.00")))
                     intCodPorIva = 3; //Valor especificado en el PDF del SRI
                  
                  //En intCodPorIva, en algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI

                  strXml += "<totalConImpuestos> ";
                  strXml +=   "<totalImpuesto> ";
                  strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado
                  
                  if (! bdeValIvaLiqCom.equals(new BigDecimal("0.00")))
                     strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                  else
                     strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> ";
                  
                  if (blnLiqComTieIva == true)
                     strXml +=   "<baseImponible>" + bdeBasImpIva + "</baseImponible> ";
                  else
                     strXml +=   "<baseImponible>" + bdeSubLiqCom + "</baseImponible> ";
                  
                  strXml +=   "<valor>" + bdeValIvaLiqCom + "</valor> ";
                  strXml +=   "</totalImpuesto> ";
                  strXml += "</totalConImpuestos> ";
                  /*
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     strXml += "<compensaciones> ";
                     strXml +=   "<compensacion>";
                     strXml +=     "<codigo>1</codigo>"; //Segun PDF del SRI, el valor de 1 corresponde a "Descuento solidario". En algun momento se sugiere reemplazar este codigo quemado.
                     strXml +=     "<tarifa>" + bdePorComSol + "</tarifa>"; //Segun PDF del SRI, este valor corresponde al porcentaje de compensacion.
                     strXml +=     "<valor>" + bdeValComSol + "</valor>"; //Segun PDF del SRI, esta cifra corresponde al valor de compensacion.
                     strXml +=   "</compensacion>";
                     strXml += "</compensaciones>";
                  }

                  strXml += "<propina>0.00</propina> ";
                  
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     bdeAux = bdeTotFac.subtract(bdeValComSol);
                     strXml += "<importeTotal>" + bdeAux + "</importeTotal> ";
                  }
                  else
                  {  
                     strXml += "<importeTotal>" + bdeTotFac + "</importeTotal> ";
                  }*/ //DBN_borrar
                  
                  strXml += "<importeTotal>" + bdeTotLiqCom + "</importeTotal> ";
                  strXml += "<moneda>DOLAR</moneda> "; //En algun momento se sugiere reemplazar este codigo quemado
                  
                  strXml += "<pagos>";
                  strXml +=   "<pago>";
                  strXml +=   "<formaPago>" + strCodForPagSri + "</formaPago>";
                  /*
                  //if (intCodEmp == 2 && intCodLoc == 4) //Este if se comentó debido al cambio de IVA 12% a partir del 1/Jun/2017.
                  if ( intCodEmp == 2 && intCodLoc == 4 && !bdeValComSol.equals(new BigDecimal("0.00")) )
                  {  //CodEmp 2 CodLoc 4 = CASTEK - Manta
                     bdeAux = bdeTotFac.subtract(bdeValComSol);
                     strXml += "<total>" + bdeAux + "</total>";
                  }
                  else
                  {  
                     strXml += "<total>" + bdeTotFac + "</total>";
                  }*/ //DBN_borrar
                  
                  strXml += "<total>" + bdeTotLiqCom + "</total>";
                  strXml +=   "<plazo>" + intNumDiaPlaMax + "</plazo>";
                  strXml +=   "<unidadTiempo>dias</unidadTiempo>";
                  strXml +=   "</pago>";
                  strXml += "</pagos>";
                  
                  strXml += "</infoLiquidacionCompra> ";

                  strXml += "<detalles> ";

                  for (i = 0; i < arlDatDetLiqCom.size(); i++)
                  {  strXml += "<detalle> ";
                     strXml += "<codigoPrincipal>" + objUti.getIntValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_COD_ITM) + "</codigoPrincipal> ";
                     //strXml += "<codigoAuxiliar>" + objUti.getStringValueAt(arlDatDetFac, i, INT_ARL_DAT_DET_MOV_INV_COD_ALT) + "</codigoAuxiliar> "; //DBN_borrar
                     strXml += "<descripcion>" + objUti.getStringValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_NOM_ITM) + "</descripcion> ";
                     strXml += "<cantidad>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_CAN) + "</cantidad> ";
                     strXml += "<precioUnitario>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_PRE_UNI) + "</precioUnitario> ";
                     strXml += "<descuento>0.00</descuento> "; //DBN
                     //strXml += "<precioTotalSinImpuesto>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</precioTotalSinImpuesto> ";
                     strXml += "<precioTotalSinImpuesto>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_SUB_DET) + "</precioTotalSinImpuesto> "; //DBN_ojo

                     bdeValIvaDet = new BigDecimal("0");

                     if (objUti.getStringValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  //bdeSubDet = objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET);
                        bdeSubDet = objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_SUB_DET); //DBN_ojo
                        bdeValIvaDet = objUti.redondearBigDecimal(bdeSubDet.multiply(bdePorIva), objParSis.getDecimalesMostrar());
                        bdeValIvaDet = bdeValIvaDet.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                     }

                     strXml += "<impuestos> ";
                     strXml +=   "<impuesto> ";
                     strXml +=   "<codigo>2</codigo> "; //Segun PDF del SRI, el valor de 2 es el cod.impuesto para IVA. En algun momento se sugiere reemplazar este codigo quemado
                     
                     if (objUti.getStringValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_STA_IVA_COM).equals("S"))
                     {  strXml +=   "<codigoPorcentaje>" + intCodPorIva + "</codigoPorcentaje> ";
                        strXml +=   "<tarifa>" + bdePorIva + "</tarifa> ";
                     }
                     else
                     {  strXml +=   "<codigoPorcentaje>0</codigoPorcentaje> "; //Segun PDF del SRI, el valor de 0 se refiere a que no tiene IVA. En algun momento se sugiere reemplazar este codigo quemado
                        strXml +=   "<tarifa>0</tarifa> "; //0 se refiere a 0% de IVA
                     }
                     
                     //strXml +=   "<baseImponible>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_TOT_DET) + "</baseImponible> ";
                     strXml +=   "<baseImponible>" + objUti.getBigDecimalValueAt(arlDatDetLiqCom, i, INT_ARL_DAT_DET_MOV_INV_SUB_DET) + "</baseImponible> "; //DBN_ojo
                     strXml +=   "<valor>" + bdeValIvaDet + "</valor> ";
                     strXml +=   "</impuesto> ";
                     strXml += "</impuestos> ";
                     strXml += "</detalle> ";
                  } //for (i = 0; i < arlDatDetLiqCom.size(); i++)

                  strXml += "</detalles> ";
                  /*
                  blnFound = false;
                  strObs1 = "";
                  //strObs2 = "";

                  if (arlDatCabFac.size() > 0)
                  {  strObs1 = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_OBS1);
                     //strObs2 = objUti.getStringValueAt(arlDatCabFac, 0, INT_ARL_DAT_CAB_MOV_INV_OBS2);
                     strObs1 = strObs1.trim();
                     //strObs2 = strObs2.trim();
                  }
                  
                  datFecIniFacIva14 = objUti.parseDate("2016/06/01", "yyyy/MM/dd"); //Fecha de incio de Facturacion con IVA 14%

                  if (!strObs1.equals("") || !strNumPed.equals(""))
                  {  strXml += "<infoAdicional> ";

                     if (!strObs1.equals(""))
                        strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional> ";
 
                     if (!strNumPed.equals(""))
                        strXml += "<campoAdicional nombre=\"PedidoCompra\">" + strNumPed + "</campoAdicional> ";

                     if (strRucCli.equals("0990293244001") && !strNumGuiRem.equals(""))
                     {  //Si el RUC es de Holcim Ecuador (0990293244001) y strNumGuiRem <> "", entonces se debe poner el num. de guia de remision
                        strXml += "<campoAdicional nombre=\"GuiaRemision\">" + strNumGuiRem + "</campoAdicional> ";
                     }

                     strXml += "</infoAdicional> ";
                  }*/ //DBN_borrar

                  strXml += "</liquidacionCompra>";
                  xml.println(strXml);
                  
                  arlRegDetResWS = new ArrayList();
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                  arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                  arlResWS.add(arlRegDetResWS);

                  rst.close();
                  rst = null;
                  stm.close();
                  stm = null;
                  xml.println();
                  xml.close();
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;
   } //Funcion armarXMLLiqCom()
   
   public boolean getNumAutorizacionLiqCom(Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux, strAux2, strRutFilLiqCom, strEst, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut, strPK, strClvAccCpb;
      int intCodCli;
      File carpeta;
      
      try
      {  arlResWS.clear();
         emisor = new Emisor();
         strRutFilLiqCom = "";
         strEst = "";
         
         if (System.getProperty("os.name").equals("Linux"))
            strRutFilLiqCom = "/Zafiro/Digitales/Contabilidad/ZafCon04/" + strPrmNomEmp + "/" + strNomCarXmlPenAut;
         else
            strRutFilLiqCom = "C:\\Zafiro\\Digitales\\Contabilidad\\ZafCon04\\" + strPrmNomEmp + "\\" + strNomCarXmlPenAut;
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de LiqCom: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         
         strEst = getEstAutFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
         
         if (strEst.equals("S"))
         {  //Se va a solicitar la autorizacion en linea
            strRutXmlPenAut = "";
            strRutXmlSiAut = "";
            strRutXmlNoAut = "";
         
            //DBN_Prueba
            /*if (! System.getProperty("os.name").equals("Linux"))
            {  strRutXmlPenAut = "//" + strIpSrvPrd;
               strRutXmlSiAut = "//" + strIpSrvPrd;
               strRutXmlNoAut = "//" + strIpSrvPrd;
            }*/

            strNomFilLiqCom = "";
            strRutXmlTipCpb = "/Zafiro/Digitales/Contabilidad/ZafCon04/"; //DBN_real
//            strRutXmlTipCpb = "C:/Zafiro/Digitales/Contabilidad/ZafCon04/"; //DBN_prueba
            strRutXmlPenAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlPenAut;
            strRutXmlSiAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlSiAut;
            strRutXmlNoAut += strRutXmlTipCpb + strPrmNomEmp + "/" + strNomCarXmlNoAut;
            strPK = intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc;
            carpeta = new File(strRutXmlPenAut);
            
            for (File archivo:carpeta.listFiles())
            {  strAux = archivo.getName();
               if (strAux.lastIndexOf(strPK) != -1)
               {  strNomFilLiqCom = strAux.substring(0, strAux.lastIndexOf(".xml"));
                  break;
               }
            }
            
            strRutNomFilLiqCom = strRutXmlPenAut + File.separator + strNomFilLiqCom + ".xml";
            File fOriXml = new File(carpeta + File.separator + strNomFilLiqCom + ".xml");
            
            if (invocarServiciosWeb(strRutNomFilLiqCom, arlResWS) == true)
            {  if (arlResWS.size() > 0)
               {  if (updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == true)
                  {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
                     
                     if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilLiqCom + ".xml"));
                     }
                     else
                     {  //No hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilLiqCom + ".xml"));
                     }

                     fOriXml.delete();
                  
                     //Inicio bloque: Descomentar bloque cuando se vaya a enviar correo al proveedor
                     /*if (strEst.equals("A"))
                     {  //A: Si hubo autorizacion del comprobante
                        copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFilLiqCom + ".xml"));
                        strAux = generarPDF(strPrmNomEmp, "FAC", con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFilLiqCom);
                        if (strAux.equals("true"))
                        {  //La generacion del PDF se realizo exitosamente
                           intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                           strCorEleCliFacEle = "";
                           strCorEleCliFacEle2 = "";
                           //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
                           buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
                           if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                           {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                              strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
                              if (!strAux.equals(""))
                              {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux + ". " + strAux2;
                              }
                              else
                              {  //Solo va el mensaje de "No se envió el correo debido a..."
                                 strAux = strAux2;
                              }  
                              objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                              updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                           }
                           else
                           {  obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
                              strAux = enviarCorreoServidorInterno(strPrmNomEmp, "FAC", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilLiqCom);

                              if (strAux.equals("true"))
                              {  int Envio_de_correo_OK = 0;  }
                              else
                              {  //Hubo un error en el envio del correo
                                 blnRes = false;
                                 strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                              
                              strAux = enviarCorreoServidorExterno(strPrmNomEmp, "FAC", intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilLiqCom);
                              
                              if (strAux.equals("true"))
                              {  int Envio_de_correo_OK = 0; }
                              else
                              {  //Hubo un error en el envio del correo.
                                 blnRes = false;
                                 strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabmovinv");
                                 if (strAux2.equals(""))
                                 {  //Significa que aun no se ha guardado ningun mensaje en el campo tx_deserrfacele. Por tanto, 
                                    //se va a obtener el mensaje devuelto por el Servicio Web
                                    strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                                 }
                                 if (!strAux2.equals(""))
                                 {  //Se concatena el mensaje guardado en strAux2 + el mensaje de excepcion por fallo de envio de correo
                                    strAux = strAux2 + ". " + strAux;
                                 }
                                 objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                                 updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                              }
                           } //if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
                        }
                        else
                        {  //Hubo un error en la generacion del PDF
                           blnRes = false;
                           strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                           if (!strAux2.equals(""))
                           {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de generacion de PDF
                              strAux = strAux2 + ". " + strAux;
                           }
                           objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                           updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                        } //if (strAux.equals("true"))
                     } //if (strEst.equals("A"))
                     else
                        copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFilLiqCom + ".xml"));

                     fOriXml.delete();*/
                     //Fin bloque: Descomentar bloque cuando se vaya a enviar correo al proveedor
                  } //if (updateCamposFacEle(...)
               } //if (arlResWS.size() > 0)
            } //if (invocarServiciosWeb(...)
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         else
         { //Solo se va a generar el XML. La autorizacion al SRI se lo hara en diferido.
            if (armarXMLLiqCom(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilLiqCom, arlResWS) == true)
            {  emisor = new Emisor();
               emisor.setRuc(strRucEmp);
               emisor.setToken("BCE_IKEY2032");
               emisor.setTiempoEspera(3);
               strRuc = emisor.getRuc();
               intTimEsp = emisor.getTiempoEspera();
               
               if (firmarArch_p12(intCodEmp, strRutNomFilLiqCom, arlResWS) == true)
               {  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  //DBN_Probar impresion
                  /*if (impresionFactura(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS) == false);
                  {  blnRes = false;
                     updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }*/
               }
               else
               {  blnRes = false;
                  updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
            }
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         } //if (strEst.equals("S"))
         
         if (strEst.equals("A"))
            System.out.println("\n" + strPrmNomEmp + " => LiqCom si fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         else
            System.out.println("\n" + strPrmNomEmp + " => LiqCom no fue autorizada: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener el número de autorización. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, "tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println(strPrmNomEmp + " => Error en proceso de LiqCom: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
      return blnRes;
   } //Funcion getNumAutorizacionLiqCom()
   
   private String generarPDF(String strPrmNomEmp, String strTipCpb, Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFilPDF, String strNomFilPDF)
   {
      String strRes = "", strSQLRep, strSQLSubRep, strNomEmpRep, strRutRpt, strNomRpt;
      
      try
      {  System.out.println("\n" + "Inicio de generacion de PDF: " + strNomFilPDF);
         strSQLRep = "";
         strSQLSubRep = "";
         
         //DBN
         //if (strTipCpb.equals("FAC"))
         //   strSQLRep = getSQLRepFac(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
         
         if (strTipCpb.equals("RET"))
         {  strSQLRep = getSQLRepCpbRet(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            //strSQLSubRep = getSQLSubRepCpbRet(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            strSQLSubRep = getSQLSubRepCpbRet_XML(intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilPDF, strNomFilPDF);
         }
         
         strNomEmpRep = "";
         strPrmNomEmp = strPrmNomEmp.toUpperCase();
         
         if (strPrmNomEmp.indexOf("TUVAL") != -1)
         {  
            strNomEmpRep = "Tuv";
         }
         else if (strPrmNomEmp.indexOf("CASTEK") != -1)
         {  
            strNomEmpRep = "Cas";
         }
         else if (strPrmNomEmp.indexOf("DIMULTI") != -1)
         {  
            strNomEmpRep = "Dim";
         }
         else if (strPrmNomEmp.indexOf("COSENCO") != -1)
         {  
            strNomEmpRep = "Cos";
         }
         else if (strPrmNomEmp.indexOf("ECUATOSA") != -1)
         {  
            strNomEmpRep = "Ecu";
         }
         else if (strPrmNomEmp.indexOf("DETOPACIO") != -1)
         {  
            strNomEmpRep = "Det";
         }
         
         strRutRpt = "";
         strNomRpt = "";
         
         if (strTipCpb.equals("FAC"))
         {  if (System.getProperty("os.name").equals("Linux"))
               strRutRpt = "/Zafiro/Reportes/Ventas/ZafVen02/";
            else
               strRutRpt = "C:\\Zafiro\\Reportes\\Ventas\\ZafVen02\\";

            strNomRpt = "ZafRptVen02.jrxml";
         }
         else if (strTipCpb.equals("NOTCRE"))
         {  if (System.getProperty("os.name").equals("Linux"))
               strRutRpt = "/Zafiro/Reportes/Ventas/ZafVen28/";
               
            else
               strRutRpt = "C:\\Zafiro\\Reportes\\Ventas\\ZafVen28\\";

            strNomRpt = "ZafRptVen28.jrxml";
         }
         else if (strTipCpb.equals("RET"))
         {  if (System.getProperty("os.name").equals("Linux"))
               strRutRpt = "/Zafiro/Reportes/Contabilidad/ZafCon05/";
            else
               strRutRpt = "C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon05\\";

            strNomRpt = "ZafRptCon05.jrxml";
         }
         
         //Inicializar los parametros que se van a pasar al reporte
         JasperDesign jasDesSinPrv = JRXmlLoader.load(strRutRpt + strNomRpt);
         JasperReport jasperReport = JasperCompileManager.compileReport(jasDesSinPrv);
         Map mapPar = new HashMap();
         
         if (strTipCpb.equals("FAC"))
         {  //La funcion getCodForPag() se encarga de llenar las variables globales strCodForPagSri, strDesForPagSri y intNumDiaPlaMax
            getCodForPag(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc);
            //mapPar.put("strSQLRep", strSQLRep); //DBN
            mapPar.put("codEmp", intCodEmp);
            mapPar.put("codLoc", intCodLoc);
            mapPar.put("codTipDoc", intCodTipDoc);
            mapPar.put("codDoc", intCodDoc);
            mapPar.put("ambiente", intTipAmb); //1 = Pruebas; 2 = Produccion
            mapPar.put("DesForPagSri", strDesForPagSri);
            mapPar.put("RUTA_LOGO", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");
         }
         else if (strTipCpb.equals("NOTCRE"))
         {  mapPar.put("codEmp", intCodEmp);
            mapPar.put("codLoc", intCodLoc);
            mapPar.put("codTipDoc", intCodTipDoc);
            mapPar.put("codDoc", intCodDoc);
            mapPar.put("SUBREPORT_DIR", strRutRpt);
            mapPar.put("LOGO_DIR", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");
         }
         else if (strTipCpb.equals("RET"))
         {  mapPar.put("codEmp", intCodEmp);
            mapPar.put("codLoc", intCodLoc);
            mapPar.put("codTipDoc", intCodTipDoc);
            mapPar.put("codDoc", intCodDoc);
            mapPar.put("strSQLRep", strSQLRep);
            mapPar.put("strSQLSubRep", strSQLSubRep);
            mapPar.put("SUBREPORT_DIR", strRutRpt);
            mapPar.put("RUTA_LOGO", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");
         }
         
         JasperPrint report = JasperFillManager.fillReport(jasperReport, mapPar, con);
         JasperExportManager.exportReportToPdfFile(report, strRutFilPDF + File.separator + strNomFilPDF + ".pdf");
         
         strRes = "true";
         System.out.println("\n" + "Fin de generacion de PDF: " + strNomFilPDF);
      } //try
      
      catch(Exception e)
      {  
         strRes = "Error al generar el PDF. " + e.toString();
         System.out.println("\n" + "Error al generar PDF: " + strNomFilPDF);
      }
      
      return strRes;
   } //Funcion generarPDF()
   
   private String enviarCorreoServidorInterno(String strPrmNomEmp, String strTipCpb, int intCodEmp, int intCodLoc, String strRutFilXml, String strNomFilXml)
   {
      String strRes = "", server, userName, fromAddress, clvRemite, toAddress1, toAddress2, body;
      
      try
      {  System.out.println("\n" + "Inicio de envio de correo desde servidor interno: " + strNomFilXml);
         fromAddress = "";
         clvRemite = "";
         server = "";
         userName = "";
         //userName = "zafiro"; //DBN_Prueba
         //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
         toAddress1 = strCorEleCliFacEle;
         toAddress2 = strCorEleCliFacEle2;
         //toAddress = "sistemas7@tuvalsa.com"; //DBN_Prueba
         strPrmNomEmp = strPrmNomEmp.toUpperCase();
         
         if (strPrmNomEmp.indexOf("TUVAL") != -1)
         {  server = "mail.tuvalsa.com";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@tuvalsa.com";
               fromAddress = "edocs-comp@tuvalsa.com";
               clvRemite = "D0cTuv@Pro";
            }
            else
            {  userName = "edocs@tuvalsa.com";
               fromAddress = "edocs@tuvalsa.com";
               clvRemite = "$lectr0Tuv@";
            }
         }
         else if (strPrmNomEmp.indexOf("CASTEK") != -1)
         {  server = "mail.castek.ec";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@castek.ec";
               fromAddress = "edocs-comp@castek.ec";
               clvRemite = "D0cC@$Pro";
            }
            else
            {  switch (intCodLoc)
               {  case 1: case 11: // Castek Quito
                     userName = "edocs-uio@castek.ec";
                     fromAddress = "edocs-uio@castek.ec";
                     clvRemite = "D0cC@$ProU";
                     break;
                  case 4: case 12: // Castek Manta
                     userName = "edocs-mta@castek.ec";
                     fromAddress = "edocs-mta@castek.ec";
                     clvRemite = "D0cC@$ProM";
                     break;
                  case 5: // Castek California (Ventas relacionadas)
                     userName = "edocs@castek.ec";
                     fromAddress = "edocs@castek.ec";
                     clvRemite = "D0cC@$ProGYE";
                     break;
                  case 6: case 13: // Castek Santo Domingo
                     userName = "edocs-sto@castek.ec";
                     fromAddress = "edocs-sto@castek.ec";
                     clvRemite = "D0cC@$ProS";
                     break;
                  case 10: case 14: // Castek Cuenca
                     userName = "edocs-cue@castek.ec";
                     fromAddress = "edocs-cue@castek.ec";
                     clvRemite = "D0cC@$ProC";
                     break;
                  default:
                     userName = "edocs@castek.ec";
                     fromAddress = "edocs@castek.ec";
                     clvRemite = "D0cC@$ProGYE";
                     break;
               }
            }
         } //else if (strPrmNomEmp.indexOf("CASTEK") != -1)
         else if (strPrmNomEmp.indexOf("DIMULTI") != -1)
         {  server = "mail.dimulti.com";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@dimulti.com";
               fromAddress = "edocs-comp@dimulti.com";
               clvRemite = "D0cD1mPro";
            }
            else
            {  userName = "edocs@dimulti.com";
               fromAddress = "edocs@dimulti.com";
               clvRemite = "$l$ctr0D1m";
            }
         }
         else if (strPrmNomEmp.indexOf("COSENCO") != -1)
         {  server = "mail.cosenco.ec";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@cosenco.ec";
               fromAddress = "edocs-comp@cosenco.ec";
               clvRemite = "ED0c15Co";
            }
            else
            {  userName = "edocs@cosenco.ec";
               fromAddress = "edocs@cosenco.ec";
               clvRemite = "ED0c18Co";
            }
         }
         else if (strPrmNomEmp.indexOf("ECUATOSA") != -1)
         {  server = "mail.ecuatosa.com";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@ecuatosa.com";
               fromAddress = "edocs-comp@ecuatosa.com";
               clvRemite = "ED0c15Ec";
            }
            else
            {  userName = "edocs@ecuatosa.com";
               fromAddress = "edocs@ecuatosa.com";
               clvRemite = "ED0c18Ec";
            }
         }
         else if (strPrmNomEmp.indexOf("DETOPACIO") != -1)
         {  server = "mail.detopacio.com";
               
            if (strTipCpb.equals("RET"))
            {  userName = "edocs-comp@detopacio.com";
               fromAddress = "edocs-comp@detopacio.com";
               clvRemite = "ED0c15De";
            }
            else
            {  userName = "edocs@detopacio.com";
               fromAddress = "edocs@detopacio.com";
               clvRemite = "ED0c18De";
            }
         }
         
         //fromAddress = "zafiro@tuvalsa.com"; //DBN_Prueba
         
         body = armarCuerpoCorreo(strNomFilXml);
         /*
         Properties props = System.getProperties();
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.ssl.trust", server);
         props.put("mail.smtp.host", server);
         props.put("mail.smtp.user", userName);
         props.put("mail.smtp.password", clvRemite);
         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.auth", "true");
         */
         
         Properties props = System.getProperties();
         props.put("mail.smtp.host", server);
         
         //Authenticator auth = new MyAuthenticator();
         Authenticator auth = new MyAuthenticator(userName, clvRemite);

         // Get session
         Session session = Session.getDefaultInstance(props, auth);
         session.setDebug(true);

         // Define message
         MimeMessage message = new MimeMessage(session);

         // Set the from address
         message.setFrom(new InternetAddress(fromAddress));

         if (!toAddress1.equals(""))
         {  // Set the to address 1
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress1));
         }
         
         if (!toAddress2.equals(""))
         {  // Set the to address 2
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(toAddress2));
         }
         
         message.addRecipient(Message.RecipientType.BCC, new InternetAddress(fromAddress));

         // Set the subject
         message.setSubject("Comprobantes electronicos");

         MimeBodyPart cuerpoCorreo = new MimeBodyPart();
         //cuerpoCorreo.setText("Estado de Cuenta ...");
         cuerpoCorreo.setContent(body, "text/html");

         BodyPart adjunto1 = new MimeBodyPart();
         adjunto1.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".xml")));
         adjunto1.setFileName(strNomFilXml + ".xml");

         BodyPart adjunto2 = new MimeBodyPart();
         adjunto2.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".pdf")));
         adjunto2.setFileName(strNomFilXml + ".pdf");

         MimeMultipart multipart = new MimeMultipart();
         multipart.addBodyPart(cuerpoCorreo);
         multipart.addBodyPart(adjunto1);
         multipart.addBodyPart(adjunto2);

         message.setContent(multipart);
         message.saveChanges();
         
         // Send message
         Transport.send(message);
         strRes = "true";
         System.out.println("\n" + "Fin de envio de correo desde servidor interno: " + strNomFilXml);
      } //try
      
      catch(Exception e)
      {  
         strRes = "Error al enviar el correo desde servidor interno. " + e.toString();
         System.out.println("\n" + "Error al enviar correo desde servidor interno: " + strNomFilXml);
      }
      
      return strRes;
   } //Funcion enviarCorreoServidorInterno()
   
   static class MyAuthenticator extends Authenticator
   {
      PasswordAuthentication l;
      
      private MyAuthenticator(String userName, String password)
      {
         l = new PasswordAuthentication(userName, password);
      }
      
      protected PasswordAuthentication getPasswordAuthentication()
      {
         return l;
      }
   }
   
   private String enviarCorreoServidorExterno(String strPrmNomEmp, String strTipCpb, int intCodEmp, int intCodLoc, String strRutFilXml, String strNomFilXml)
   {
      String strRes = "", strCorEleSrvInt, fromAddress, clvRemite, toAddress1, toAddress2, body;
      
      try
      {  System.out.println("\n" + "Inicio de envio de correo desde servidor externo: " + strNomFilXml);
         fromAddress = "";
         clvRemite = "";
         strCorEleSrvInt = "";
         //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
         toAddress1 = strCorEleCliFacEle;
         toAddress2 = strCorEleCliFacEle2;
         strPrmNomEmp = strPrmNomEmp.toUpperCase();
         
         if (strPrmNomEmp.indexOf("TUVAL") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs@tuvalsa.com";
               fromAddress = "edocs.tuval.comp@gmail.com";
               clvRemite = "tuv@comp.01";
            }
            else
            {  strCorEleSrvInt = "edocs@tuvalsa.com";
               fromAddress = "edocs.tuval@gmail.com";
               clvRemite = "tuv@cal.01";
            }
         }     
         else if (strPrmNomEmp.indexOf("CASTEK") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs@castek.ec";
               fromAddress = "edocs.castek.comp@gmail.com";
               clvRemite = "cas@comp.02";
            }
            else
            {  switch (intCodLoc)
               {  case 1: case 11: // Castek Quito
                     strCorEleSrvInt = "edocs@castek.ec";
                     fromAddress = "edocs.castek.uio@gmail.com";
                     clvRemite = "cas@uio.02";
                     break;
                  case 4: case 12: // Castek Manta
                     strCorEleSrvInt = "edocs@castek.ec";
                     fromAddress = "edocs.castek.mta@gmail.com";
                     clvRemite = "cas@mta.03";
                     break;
                  case 6: case 13: // Castek Santo Domingo
                     strCorEleSrvInt = "edocs@castek.ec";
                     fromAddress = "edocs.castek.sto@gmail.com";
                     clvRemite = "cas@sto.04";
                     break;
                  case 10: case 14: // Castek Cuenca
                     strCorEleSrvInt = "edocs@castek.ec";
                     fromAddress = "edocs.castek.cue@gmail.com";
                     clvRemite = "cas@cue.05";
                     break;
                  default:
                     strCorEleSrvInt = "";
                     fromAddress = "";
                     clvRemite = "";
                     break;
               }
            }
         } //else if (strPrmNomEmp.indexOf("CASTEK") != -1)
         else if (strPrmNomEmp.indexOf("DIMULTI") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs@dimulti.comm";
               fromAddress = "edocs.dimulti.comp@gmail.com";
               clvRemite = "dim@comp.03";
            }
            else
            {  strCorEleSrvInt = "edocs@dimulti.com";
               fromAddress = "edocs.dimulti@gmail.com";
               clvRemite = "dim@via.06";
            }
         }
         else if (strPrmNomEmp.indexOf("COSENCO") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs-comp@cosenco.ec";
               fromAddress = "edocs.cosenco.comp@gmail.com";
               clvRemite = "cos@comp.01";
            }
            else
            {  strCorEleSrvInt = "edocs@cosenco.ec";
               fromAddress = "edocs.cosenco@gmail.com";
               clvRemite = "cos@inm.01";
            }
         }
         else if (strPrmNomEmp.indexOf("ECUATOSA") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs-comp@ecuatosa.com";
               fromAddress = "edocs.ecuatosa.comp@gmail.com";
               clvRemite = "ecu@comp.02";
            }
            else
            {  strCorEleSrvInt = "edocs@ecuatosa.com";
               fromAddress = "edocs.ecuatosa@gmail.com";
               clvRemite = "ecu@inm.02";
            }
         }
         else if (strPrmNomEmp.indexOf("DETOPACIO") != -1)
         {  if (strTipCpb.equals("RET"))
            {  strCorEleSrvInt = "edocs-comp@detopacio.com";
               fromAddress = "edocs.detopacio.comp@gmail.com";
               clvRemite = "det@comp.03";
            }
            else
            {  strCorEleSrvInt = "edocs@detopacio.com";
               fromAddress = "edocs.detopacio@gmail.com";
               clvRemite = "det@inm.03";
            }
         }
         
         body = armarCuerpoCorreo(strNomFilXml);
         
         Properties props = new Properties();
         props.setProperty("mail.smtp.host",  "smtp.gmail.com");
         props.setProperty("mail.smtp.starttls.enable", "true");
         props.setProperty("mail.smtp.port", "587");
         props.setProperty("mail.smtp.auth", "true");

         //Session session = Session.getDefaultInstance(props);
         Session session = Session.getInstance(props);
         MimeMessage message = new MimeMessage(session);
         
         if (!toAddress1.equals(""))
         {  // Set the to address 1
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress1));
         }
         
         if (!toAddress2.equals(""))
         {  // Set the to address 2
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress2));
         }
         
         message.addRecipient(Message.RecipientType.CC, new InternetAddress(strCorEleSrvInt));
         
         // Set the subject
         message.setSubject("COPIA: Comprobantes electronicos");
         
         MimeBodyPart cuerpoCorreo = new MimeBodyPart();
         cuerpoCorreo.setContent(body, "text/html");
         
         BodyPart adjunto1 = new MimeBodyPart();
         adjunto1.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".xml")));
         adjunto1.setFileName(strNomFilXml + ".xml");

         BodyPart adjunto2 = new MimeBodyPart();
         adjunto2.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".pdf")));
         adjunto2.setFileName(strNomFilXml + ".pdf");
         
         MimeMultipart multiParte = new MimeMultipart();
         multiParte.addBodyPart(cuerpoCorreo);
         multiParte.addBodyPart(adjunto1);
         multiParte.addBodyPart(adjunto2);
         message.setContent(multiParte);
         
         Transport t = session.getTransport("smtp");
         t.connect(fromAddress, clvRemite);
         t.sendMessage(message, message.getAllRecipients());
         t.close();
         
         strRes = "true";
         System.out.println("\n" + "Fin de envio de correo desde servidor externo: " + strNomFilXml);
      } //try
      
      catch(Exception e)
      {  
         strRes = "Error al enviar el correo desde servidor externo. " + e.toString();
         System.out.println("\n" + "Error al enviar correo desde servidor externo: " + strNomFilXml);
      }
      
      return strRes;
   } //Funcion enviarCorreoServidorExterno()
   
   private String armarCuerpoCorreo(String strNomFilXml)
   {
      String strRes = "", strBody;
      
      try
      {
         strBody =  "Estimado cliente/proveedor:" + "<BR><BR>";
         strBody += "Este correo es informativo, favor no responder a esta direccion de correo ";
         strBody += "ya que no se encuentra habilitada para recibir mensajes." + "<BR>";
         strBody += "Si requiere mayor informacion sobre el contenido de este mensaje llame a ";
         strBody += "nuestras oficinas que con mucho gusto lo atenderemos." + "<BR><BR>";
         strBody += "Atentamente," + "<BR><BR>";
         strBody += strNomEmp + "<BR><BR>";
         strBody += "<FONT COLOR=\"blue\">Archivos adjuntos:" + "</FONT><BR>";
         strBody += strNomFilXml + ".xml" + "<BR>";
         strBody += strNomFilXml + ".pdf";
         strRes = strBody;
      }
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion armarCuerpoCorreo()
   
   private boolean buscarCorEleCliFacEle(Connection con, int intCodEmp, int intCodCli)
   {
      boolean blnRes = true;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         strCorEleCliFacEle = "";
         strCorEleCliFacEle2 = "";
         
         if (con != null)
         {  stm = con.createStatement();
            strSQL =  "select tx_corelefacele, tx_corelefacele2 from tbm_cli where st_reg <> 'I' ";
            strSQL += "and co_emp = " + intCodEmp + " ";
            strSQL += "and co_cli = " + intCodCli;
            rst = stm.executeQuery(strSQL);

            if (rst.next())
            {  strCorEleCliFacEle = rst.getString("tx_corelefacele") == null? "" :rst.getString("tx_corelefacele");
               strCorEleCliFacEle2 = rst.getString("tx_corelefacele2") == null? "" :rst.getString("tx_corelefacele2");
            }

            rst.close();
            rst = null;
            stm.close();
            stm = null;
         }
      }
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion buscarCorEleCliFacEle()
   
   private String getSQLRepFac(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
   {
      String strRes = "";
      
      try
      {
         strSQL =  "select (substring(loc.tx_secdoc,1,3) || '-' || substring(loc.tx_secdoc,5,3) || '-' || to_char(ne_numdoc, '000000000')) as tx_numdoc, ";
         strSQL += "DocCab.tx_numautfacele, ";
         strSQL += "DocCab.fe_autfacele, ";
         strSQL += "DocCab.tx_claaccfacele, ";
         strSQL += "usr.tx_nom as nomusring, ";
         strSQL += "DocCab.tx_obs1, ";
         strSQL += "loc.tx_dir as diremp, ";
         strSQL += "emp.tx_nom as nomemp, ";
         strSQL += "DocCab.tx_telcli, ";
         strSQL += "DocCab.tx_ruc, ";
         strSQL += "case when  DocCab.co_cli = 828 then 'Manta,' else '' end as feciu, ";
         strSQL += "DocCab.fe_doc, ";
         strSQL += "DocCab.tx_numped, ";
         strSQL += "DocCab.ne_numgui, ";
         strSQL += "DocCab.tx_desforpag, ";
         strSQL += "DocCab.tx_nomcli, ";
         strSQL += "DocCab.co_cli, ";
         strSQL += "DocCab.tx_nomven, ";
         strSQL += "DocCab.tx_dircli, ";
         strSQL += "DocCab.tx_vehret, ";
         strSQL += "DocCab.tx_choret, ";
         strSQL += "a3.tx_deslar, ";
         strSQL += "abs(DocCab.nd_sub) as subtotal, ";
         strSQL += "abs(DocCab.nd_sub*(DocCab.nd_poriva/100)) as iva, ";
         strSQL += "abs((DocCab.nd_sub*(DocCab.nd_poriva/100))+DocCab.nd_sub) as total, ";
         strSQL += "case when inv.st_ser = 'S' then '(*) '|| a2.tx_codalt2 else a2.tx_codalt2 end as tx_codalt2, ";
         strSQL += "CASE WHEN inv.st_ser = 'S' then 'SERVICIO: '|| a2.tx_nomItm ELSE ";
         strSQL += "     CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' ";
         strSQL += "     END ";
         strSQL += "END as tx_nomitm, ";
         strSQL += "abs(a2.nd_can) as can, ";
         strSQL += "a2.tx_unimed, ";
         strSQL += "a2.nd_preuni, ";
         strSQL += "a2.nd_pordes, ";
         strSQL += "abs(a2.nd_can*a2.nd_preuni) as precan, ";
         strSQL += "((a2.nd_can*abs(a2.nd_preuni))*(a2.nd_pordes/100)) as des, ";
         strSQL += "ROUND(  abs((a2.nd_can*abs(a2.nd_preuni))-((a2.nd_can*abs(a2.nd_preuni))*(a2.nd_pordes/100))) ,2) as tot, ";
         strSQL += "abs(DocCab.nd_sub) as sub, ";
         strSQL += "abs(DocCab.nd_valiva) as valiva, ";
         strSQL += "abs(DocCab.nd_tot) as  total_bas, ";
         strSQL += "DocCab.tx_ciucli as cuidad, ";
         strSQL += "emp.tx_desconesp as contri1, ";
         strSQL += "case when (ne_numdocree is not null) then ( 'Factura No ' || ne_numdoc  || ' Reemplaza a la Factura No ' ||  ne_numdocree ) end as Reem, ";
         strSQL += "(select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo ";
         strSQL += " from tbm_detmovinv as x ";
         strSQL += " inner join tbm_inv as inv on (x.co_emp=inv.co_emp and x.co_itm=inv.co_itm) ";
         strSQL += " WHERE x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc  and x.co_tipdoc=DocCab.co_tipdoc and x.co_doc=DocCab.co_doc ) as kgramo, ";
         strSQL += "(select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo ";
         strSQL += " from tbm_detmovinv as x ";
         strSQL += " inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm) ";
         strSQL += " WHERE x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc  and x.co_tipdoc=DocCab.co_tipdoc and x.co_doc=DocCab.co_doc ) as kilo, emp.tx_desconesp ";
         strSQL += " FROM tbm_cabMovInv as DocCab ";
         strSQL += " INNER JOIN tbm_detmovinv as a2  ON(DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc) ";
         strSQL += " LEFT OUTER JOIN tbm_var as a3 ON (DocCab.co_forret = a3.co_reg ) ";
         strSQL += " inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm) ";
         strSQL += " inner JOIN tbm_loc as loc ON ( doccab.co_emp = loc.co_emp and  doccab.co_loc=loc.co_loc) ";
         strSQL += " inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu) ";
         strSQL += " inner JOIN tbm_emp as emp ON ( emp.co_emp = DocCab.co_emp) ";
         strSQL += " inner JOIN tbm_usr as usr ON ( usr.co_usr = DocCab.co_usring) ";
         strSQL += " WHERE DocCab.co_emp = " + intCodEmp;
         strSQL += " and DocCab.co_loc = " + intCodLoc;
         strSQL += " and DocCab.co_tipdoc = " + intCodTipDoc;
         strSQL += " and DocCab.co_doc = " + intCodDoc;
         strRes = strSQL;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getSQLRepFac()
   
   private String getSQLRepCpbRet(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
   {
      String strRes = "";
      
      try
      {
         strSQL="";
         strSQL+=" SELECT  x.tx_numdoc, x.tx_dirLoc, x.tx_rucEmp, x.tx_nomEmp, x.tx_dirEmp, x.tx_telEmp,x.tx_faxEmp, x.tx_dirWebEmp, x.tx_corEleEmp,  \n";
         strSQL+="         x.tx_desConEspEmp, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.ne_numDoc1,  \n";
         strSQL+="         x.ne_numDocFor, x.fe_doc,x.FecReg, \n";
         strSQL+="         x.co_cli, x.tx_rucCli, x.tx_nomCli, x.tx_dirCli, x.tx_obs1, x.tx_obs2, \n";
         strSQL+="         x.ne_numOrdComCan, x.ne_numCmpVta, x.ne_aniEjeFis , x.tx_fecCadCli,  \n";
         strSQL+="  /*  CAMBIAR */      /*x.tx_numAutSRICli, DEJARA DE FUNCIONAR  */   \n";
         strSQL+="         x.tx_numAutSRICli, x.nd_tamAutSRIcli,  \n";
         strSQL+="         CASE WHEN x.nd_tamAutSRIcli = 10 THEN tx_numAutSRICli ELSE '' END as tx_numAutSRICli10 ,  /* ANTES DE FACTURACION ELECTRONICA */ \n";
         strSQL+=" 	      CASE WHEN x.nd_tamAutSRIcli = 37 THEN tx_numAutSRICli ELSE '' END as tx_numAutSRICli37 ,  /* FACTURACION ELECTRONICA */ \n";
         strSQL+=" /*  HASTA AKI JoséMario 29/08/2014  */ \n";
         strSQL+="         x.ne_valRet /* JoséMario 23Dic2014 */ ,x.fe_autFacEle,x.tx_claAccFacEle, x.tx_numAutFacEle  /* JoséMario 23Dic2014 */ \n";
         strSQL+=" FROM (\n";
         strSQL+="SELECT ";
         strSQL+="(substring(a5.tx_secdoc,1,3) || '-' || substring(a5.tx_secdoc,5,3) || '-' || to_char(a7.ne_numdoc1, '000000000')) as tx_numdoc, ";
         strSQL+="a5.tx_dir as tx_dirLoc, ";
         strSQL+="a1.tx_ruc AS tx_rucEmp, a1.tx_nom AS tx_nomEmp, a1.tx_dir AS tx_dirEmp, a1.tx_tel AS tx_telEmp,";
         strSQL+="a1.tx_fax AS tx_faxEmp, a1.tx_dirWeb AS tx_dirWebEmp, a1.tx_corEle AS tx_corEleEmp, a1.tx_desConEsp AS tx_desConEspEmp,";
         strSQL+="b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
         strSQL+=", b1.ne_numDoc1, lpad(''|| b1.ne_numDoc1,7,'0') AS ne_numDocFor";
         strSQL+=", b1.fe_doc, ";
         strSQL+=" extract(day from b1.fe_doc)  ||'-'||";
         strSQL+=" 	case extract(month from b1.fe_doc)";
         strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
         strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
         strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
         strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
         //--cliente
         strSQL+=", b1.co_cli, b1.tx_ruc AS tx_rucCli, b1.tx_nomCli, b1.tx_dirCli";
         strSQL+=", b1.tx_obs1, b1.tx_obs2";
         strSQL+=", cast( '" + getNumOrdenCompra(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc) + "' as character varying) AS ne_numOrdComCan";
         strSQL+=", (a6.tx_numSer  || '-' || a6.tx_numChq ) as ne_numCmpVta";
         strSQL+=", CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis";
         strSQL+=" , a6.tx_fecCad AS tx_fecCadCli, a6.tx_numAutSRI AS tx_numAutSRICli, length( a6.tx_numAutSRI) AS nd_tamAutSRIcli, ABS(b1.nd_monDoc) AS ne_valRet";
         strSQL+="  , a7.fe_autFacEle, a7.tx_claAccFacEle, a7.tx_numAutFacEle  /* JoséMario 23Dic2014 */";
         strSQL+=" FROM (tbm_cabPag AS b1 INNER JOIN tbm_emp AS a1 ON(b1.co_emp=a1.co_emp)";
         strSQL+=" 	INNER JOIN tbm_loc AS a5 ON (b1.co_emp=a5.co_emp AND b1.co_loc=a5.co_loc)";
         strSQL+="	INNER JOIN tbm_cabTipDoc AS a2 ON (b1.co_emp=a2.co_emp AND b1.co_loc=a2.co_loc AND b1.co_tipDoc=a2.co_tipDoc)";
         strSQL+=" )";
         strSQL+=" INNER JOIN tbm_detPag AS b2";
         strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
         strSQL+=" INNER JOIN tbm_pagMovInv AS a6";
         strSQL+=" ON b2.co_emp=a6.co_emp AND b2.co_locPag=a6.co_loc AND b2.co_tipDocPag=a6.co_tipDoc AND b2.co_docPag=a6.co_doc AND b2.co_regPag=a6.co_reg";
         strSQL+=" INNER JOIN tbm_cabMovInv AS a3";
         strSQL+=" ON a6.co_emp=a3.co_emp AND a6.co_loc=a3.co_loc AND a6.co_tipDoc=a3.co_tipDoc AND a6.co_doc=a3.co_doc";
         strSQL+=" INNER JOIN tbm_cabPag as a7 ON (b1.co_emp=a7.co_emp AND b1.co_loc=a7.co_loc AND b1.co_tipDoc=a7.co_tipDoc AND b1.co_doc=a7.co_doc)";
         strSQL+=" WHERE b1.co_emp=" + intCodEmp + "";
         strSQL+=" AND b1.co_loc=" + intCodLoc + "";
         strSQL+=" AND b1.co_tipDoc=" + intCodTipDoc + "";
         strSQL+=" AND b1.co_doc=" + intCodDoc + "";
         strSQL+=" GROUP BY a5.tx_secdoc, a7.ne_numdoc1, a5.tx_dir, a1.tx_ruc, a1.tx_nom, a1.tx_dir, a1.tx_tel,";
         strSQL+=" a1.tx_fax, a1.tx_dirWeb, a1.tx_corEle, a1.tx_desConEsp,";
         strSQL+=" b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1";
         strSQL+=" , b1.fe_doc, b1.co_cli, b1.tx_ruc,b1.tx_nomCli, b1.tx_dirCli";
         strSQL+=" , b1.tx_obs1, b1.tx_obs2, a6.tx_numSer, a6.tx_numChq";
         strSQL+=" , a6.tx_fecCad, a6.tx_numAutSRI, b1.nd_monDoc \n";
         strSQL+=" , a7.fe_autFacEle, a7.tx_claAccFacEle, a7.tx_numAutFacEle  ) as x";
         strRes = strSQL;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getSQLRepCpbRet()
   
   private String getSQLSubRepCpbRet(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
   {
      String strRes = "";
      BigDecimal bdePorRet_Xml, bdeBasImp_Xml, bdeValRet_Xml;
      
      try
      {
         strSQL= "SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
         //--- Inicio bloque comentado el 19/Jun/2015 ---
         //Esta linea fue comentada en la version del programa 0.2.36 (19/Jun/2015) debido a que, al mostrar la leyenda 'IMPUESTO A LA RENTA' 
         //se consideraba el valor de 10 en la condicion del campo tbm_pagMovInv.nd_porret, correspondente a "Retencion en la fuente 10%". 
         //Se tuvo que quitar este valor de 10 en la condicion debido a que ahora ha aparecido el valor de 10 para "Retencion al IVA 10%" y esto 
         //se debe mostrar en la leyenda del reporte 'IVA'.
         //
         //strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
         //--- Fin bloque comentado el 19/Jun/2015 ------
         
         strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8) then 'IMPUESTO A LA RENTA'";
         strSQL+=" 	when round(a2.nd_porret) in(30,70,100,10,20) then '    I.V.A.' end AS Impuesto";
         strSQL+=" , round(a2.nd_porret) AS nd_porret";
         strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, a2.tx_codSRI";
         strSQL+=" FROM (";
         strSQL+=" 	tbm_cabPag AS b1";
         strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
         strSQL+=" INNER JOIN tbm_detPag AS a1";
         strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
         strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
         strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
         strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
         strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
         strSQL+=" AND a1.co_loc=" + intCodLoc + "";
         strSQL+=" AND a1.co_tipDoc=" + intCodTipDoc + "";
         strSQL+=" AND a1.co_doc=" + intCodDoc + "";
         strSQL+=" GROUP BY b1.fe_doc";
         strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
         strSQL+=" ORDER BY a2.nd_porret";
         
         strRes = strSQL;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getSQLSubRepCpbRet()
   
   private String getSQLSubRepCpbRet_XML(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFilPDF, String strNomFilPDF)
   {
      String strRes = "", strData = "", strAux, strAux2, strFecFacPrv, strCodSri;
      int i, intAniFis;
      BigDecimal bdePorRet, bdeBasImp, bdeValRet;
      
      try
      {
         i = 0;
         strSQL = "";
         File archivo = new File(strRutFilPDF + File.separator + strNomFilPDF + ".xml");
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
         Document document = documentBuilder.parse(archivo);
         document.getDocumentElement().normalize();
         //System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
         NodeList listaComprobantes = document.getElementsByTagName("comprobante");

         for (int temp = 0; temp < listaComprobantes.getLength(); temp++)
         {  
            Node nodo = listaComprobantes.item(temp);
            //System.out.println("Elemento:" + nodo.getNodeName());
            if (nodo.getNodeType() == Node.ELEMENT_NODE)
            {  Element element = (Element) nodo;
               Node child = element.getFirstChild();

               if (child instanceof CharacterData)
               {  CharacterData cd = (CharacterData) child;
                  strData = cd.getData();
                  //Año fiscal
                  strAux = strData.substring(strData.indexOf("<periodoFiscal>") + 15, strData.indexOf("</periodoFiscal>"));
                  strAux2 = strAux.substring(strAux.indexOf("/") + 1, strAux.length());
                  intAniFis = Integer.parseInt(strAux2);

                  while (strData.indexOf("<impuesto>") != -1)
                  {  i++;
                     
                     if (i > 1) {
                        strSQL += "UNION " + "\n";
                     }
                     
                     strSQL += "SELECT " + intAniFis + " as ne_aniEjeFis, ";
                     strAux = strData.substring(strData.indexOf("<impuesto>") + 11, strData.indexOf("</impuesto>"));
                     //Porcentaje de retencion
                     strAux2 = strAux.substring(strAux.indexOf("<porcentajeRetener>") + 19, strAux.indexOf("</porcentajeRetener>"));
                     bdePorRet = new BigDecimal(strAux2);
                     //Base imponible
                     strAux2 = strAux.substring(strAux.indexOf("<baseImponible>") + 15, strAux.indexOf("</baseImponible>"));
                     bdeBasImp = new BigDecimal(strAux2);
                     //Valor de retencion
                     strAux2 = strAux.substring(strAux.indexOf("<valorRetenido>") + 15, strAux.indexOf("</valorRetenido>"));
                     bdeValRet = new BigDecimal(strAux2);
                     //Cod. SRI
                     strCodSri = strAux.substring(strAux.indexOf("<codigoRetencion>") + 17, strAux.indexOf("</codigoRetencion>"));
                     //Num. de factura del proveedor. Ej: 003004000002206
                     strAux2 = strAux.substring(strAux.indexOf("<numDocSustento>") + 16, strAux.indexOf("</numDocSustento>"));
                     strFecFacPrv = strAux2.substring(0,3) + "-" + strAux2.substring(3,6) + "-" + strAux2.substring(6,15);
                     
                     strSQL += bdeBasImp + " as nd_basImp, ";
                     strSQL += "case when " + bdePorRet + " in(0,1,2,5,8,1.75,2.75) then 'IMPUESTO A LA RENTA' when round(" + bdePorRet + ") in(30,70,100,10,20) then '    I.V.A.' end as Impuesto, ";
                     strSQL += "round(" + bdePorRet + ",2) as nd_porRet, ";
                     strSQL += bdeValRet + " as nd_abo, ";
                     strSQL += "'" + strFecFacPrv + "' as ne_numCmpVta, ";
                     strSQL += "'" + strCodSri + "' as tx_codSRI " + "\n";
                    
                    strData = strData.substring(strData.indexOf("</impuesto>") + 12, strData.length());
                    //System.out.println("strAux:" + strAux);
                  } //while (strData.indexOf("<impuesto>") != -1)
               } //if (child instanceof CharacterData)
            } //if (nodo.getNodeType() == Node.ELEMENT_NODE)
         } //for (int temp = 0; temp < listaComprobantes.getLength(); temp++)
         
         if (!strSQL.equals("")) {
            strSQL += "ORDER BY nd_porRet";
         }
         
         /*strSQL =  "select 2019 as ne_aniEjeFis, 100.1 as nd_basImp, 'IMPUESTO-RENTA' as impuesto, 1.01 as nd_porRet, 10.12 as nd_abo, '001-002-000000001' as ne_numCmpVta, '112' as tx_codSRI \n";
         strSQL += "union \n";
         strSQL += "select 2018 as ne_aniEjeFis, 200.2 as nd_basImp, 'IVVAA' as impuesto, 2.02 as nd_porRet, 20.23 as nd_abo, '001-002-000000002' as ne_numCmpVta, '22' as tx_codSRI";
         */ //DBN_prueba
         strRes = strSQL;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getSQLSubRepCpbRet_XML()
   
   private String getNumOrdenCompra(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
   {
      String strRes = "";
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      boolean blnFound;
      
      try
      {
         stm = con.createStatement();
         //Se va a obtener la(s) Orden(es) de compra asociadas a la retencion
         strSQL =  "SELECT DISTINCT a4.ne_numdoc ";
         strSQL += "FROM       tbm_cabpag as a1 ";
         strSQL += "INNER JOIN tbm_detpag as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_loc and a1.co_tipdoc = a2.co_tipdoc and a1.co_doc = a2.co_doc ";
         strSQL += "INNER JOIN tbm_pagmovinv as a3 on a2.co_emp = a3.co_emp and a2.co_locpag = a3.co_loc and a2.co_tipdocpag = a3.co_tipdoc and a2.co_docpag = a3.co_doc and a2.co_regpag = a3.co_reg ";
         strSQL += "INNER JOIN tbm_cabmovinv as a4 on a3.co_emp = a4.co_emp and a3.co_loc = a4.co_loc and a3.co_tipdoc = a4.co_tipdoc and a3.co_doc = a4.co_doc ";
         strSQL += "WHERE a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' ";
         strSQL += "and a1.co_emp = " + intCodEmp;
         strSQL += "and a1.co_loc = " + intCodLoc;
         strSQL += "and a1.co_tipdoc = " + intCodTipDoc;
         strSQL += "and a1.co_doc = " + intCodDoc;
         rst = stm.executeQuery(strSQL);
         blnFound = false;
         
         while (rst.next())
         {  blnFound = true;
            strRes += rst.getString("ne_numdoc") + ",";
         }
         
         if (blnFound == true)
         {  //Ej: strRes = "1234,"
            strRes = strRes.substring(0, strRes.length() - 1); // Va a extraer "1234"
         }
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getNumOrdenCompra()
   
   private String getEstAutFacEle(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      String strRes = "";
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         stm = con.createStatement();
         strSQL =  "SELECT st_autfacele ";
         strSQL += "FROM   " + strNomTbl + " ";
         strSQL += "WHERE co_emp = " + intCodEmp;
         strSQL += "      and co_loc = " + intCodLoc;
         strSQL += "      and co_tipdoc = " + intCodTipDoc;
         strSQL += "      and co_doc = " + intCodDoc;
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
            strRes = rst.getString("st_autfacele") == null? "" :rst.getString("st_autfacele");
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getEstAutFacEle()
   
   private int getCodCliente(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      int intRes = 0;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         stm = con.createStatement();
         strSQL =  "SELECT co_cli ";
         strSQL += "FROM   " + strNomTbl + " ";
         strSQL += "WHERE st_reg = 'A' ";
         strSQL += "      and co_emp = " + intCodEmp;
         strSQL += "      and co_loc = " + intCodLoc;
         strSQL += "      and co_tipdoc = " + intCodTipDoc;
         strSQL += "      and co_doc = " + intCodDoc;
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
            intRes = rst.getInt("co_cli");
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         intRes = 0;
      }
      
      return intRes;
   } //Funcion getCodCliente()
   
   private String getDesErrFacEle(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      String strRes = "";
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         stm = con.createStatement();
         strSQL =  "SELECT tx_deserrfacele ";
         strSQL += "FROM   " + strNomTbl + " ";
         strSQL += "WHERE co_emp = " + intCodEmp;
         strSQL += "      and co_loc = " + intCodLoc;
         strSQL += "      and co_tipdoc = " + intCodTipDoc;
         strSQL += "      and co_doc = " + intCodDoc;
         rst = stm.executeQuery(strSQL);
         
         if (rst.next())
            strRes = rst.getString("tx_deserrfacele") == null? "" :rst.getString("tx_deserrfacele");
         
         rst.close();
         rst = null;
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getDesErrFacEle()
   
   private boolean getCodForPag(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
   {
      boolean blnRes = true;
      int intCodForPag, intTipForPag;
      java.sql.Statement stm;
      java.sql.ResultSet rst;
      
      try
      {
         strCodForPagSri = ""; //Variable global
         strDesForPagSri = ""; //Variable global
         intNumDiaPlaMax = 0; //Variable global
         
         if (con != null)
         {  stm = con.createStatement();
            intCodForPag = 0;   
            strSQL =  "SELECT co_forpag ";
            strSQL += "FROM      tbm_cabmovinv ";
            strSQL += "WHERE     co_emp = " + intCodEmp;
            strSQL += "          and co_loc = " + intCodLoc;
            strSQL += "          and co_tipdoc = " + intCodTipDoc;
            strSQL += "          and co_doc = " + intCodDoc;
            rst = stm.executeQuery(strSQL);

            if (rst.next())
               intCodForPag = rst.getInt("co_forpag");
            
            intTipForPag = 0;
            strSQL = "select ne_tipforpag from tbm_cabforpag where co_emp = " + intCodEmp + " and co_forpag = " + intCodForPag;
            rst = stm.executeQuery(strSQL);
            
            if (rst.next())
               intTipForPag = rst.getInt("ne_tipforpag");
            
            switch (intTipForPag)
            {  case 1: //Contado
                  strCodForPagSri = "01"; //Sin utilizacion del sistema financiero
                  strDesForPagSri = "Sin utilización del sistema financiero";
                  break;
               case 4: //Tarjeta de credito/Debito
                  strCodForPagSri = "19"; //Tarjeta de credito
                  strDesForPagSri = "Tarjeta de crédito";
                  break;
               default:
                  strCodForPagSri = "20"; //Otros con utilizacion del sistema financiero
                  strDesForPagSri = "Otros con utilización del sistema financiero";
                  break;
            }
            
            intNumDiaPlaMax = 0;
            strSQL = "select max(ne_diacre) as ne_diacre from tbm_detforpag where co_emp = " + intCodEmp + " and co_forpag = " + intCodForPag;
            rst = stm.executeQuery(strSQL);
            
            if (rst.next())
               intNumDiaPlaMax = rst.getInt("ne_diacre");
            
            rst.close();
            rst = null;
            stm.close();
            stm = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion getCodForPag()
   
   private boolean verificar_DetXML_DetPag_Ret(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlDatDetCpbRet)
   {
      boolean blnRes = true, blnPorRetFound;
      int i, intNumRegRst, intCodDocAnt, intCodDocAct, intNumFaccom;
      BigDecimal bdePorRet_BD, bdeBasImp_BD, bdeValAbo_BD, bdePorRet_Xml, bdeBasImp_Xml, bdeValRet_Xml;
      String strAplRet_BD, strNumSer_BD, strNumFacPrv_BD, strCodSri_BD, strAplRet_Xml, strNumSer_Xml, strNumFacPrv_Xml, strCodSri_Xml;
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {
         if (con != null)
         {  stmLoc = con.createStatement(java.sql.ResultSet.TYPE_SCROLL_SENSITIVE, java.sql.ResultSet.CONCUR_READ_ONLY);
            intNumRegRst = 0;
            intNumFaccom = 0;
            intCodDocAnt = -1;
            strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc1, a1.tx_nomcli, a2.co_locPag, a2.co_tipdocPag,";
            strSQL += " a2.co_docPag,  a2.co_regPag, a3.nd_porRet, a3.tx_aplRet, a3.nd_basImp, abs(a2.nd_abo) as nd_abo, a3.tx_numSer, a3.tx_numChq, a3.tx_codSri ";
            strSQL += "FROM tbm_cabPag as a1 ";
            strSQL += "INNER JOIN tbm_detPag as a2 ON a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipDoc AND a1.co_doc = a2.co_doc ";
            strSQL += "INNER JOIN tbm_pagMovInv as a3 ON a2.co_emp = a3.co_emp AND a2.co_locPag = a3.co_loc AND a2.co_tipDocPag = a3.co_tipDoc AND a2.co_docPag = a3.co_doc AND a2.co_regPag = a3.co_reg ";
            strSQL += "WHERE a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C')";
            strSQL += " and a1.co_emp = " + intCodEmp;
            strSQL += " and a1.co_loc = " + intCodLoc; 
            strSQL += " and a1.co_tipdoc = " + intCodTipDoc;
            strSQL += " and a1.co_doc = " + intCodDoc;
            strSQL += "ORDER BY a2.co_tipdocPag, a2.co_docPag";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next()) 
            {  intNumRegRst++;
               
               if (rstLoc.getInt("co_tipdocPag") == 2)
               {  //co_tipdocPag 2 = FACCOM. Solo para este Tip.Doc, se va a contar el num. de FACCOM que estan relacionadas con la Retencion.
                  intCodDocAct = rstLoc.getInt("co_docPag");
               
                  if (intCodDocAnt != intCodDocAct)
                  {  //Se va a contar el num. de FACCOM que estan relacionadas con la Retencion.
                     intNumFaccom++;   
                  }
                  
                  intCodDocAnt = intCodDocAct;
                  intCodDocAct = -1;
               }
            }
            
            if (intNumFaccom > 1)
            {  //Si es > 1, significa que hay mas de una FACCOM que estan relacionadas con la Retencion. Por tanto, no se debe continuar con el proceso
               //de validacion. Sin embargo, se devuelve true para indicar que el proceso de validacion estuvo OK.
               return true;
            }
            
            //arlDatDetCpbRet: Arreglo que tiene el detalle con el cual se va a armar el XML.
            if (intNumRegRst != arlDatDetCpbRet.size())
            {  //Si es diferente, significa que el num_reg de tbm_detPag no concuerda con el num_reg del XML que se va a armar.
               blnRes = false;
            }
            
            rstLoc.beforeFirst();
            //Por cada reg. de tbm_detPag se lo va a buscar en el arreglo arlDatDetCpbRet para verificar si coinciden todos los datos. Vale recalcar que
            //el XML se va a armar con los datos del arreglo.
            while (rstLoc.next() && blnRes == true)
            {  bdePorRet_BD = rstLoc.getBigDecimal("nd_porRet");
               strAplRet_BD = rstLoc.getString("tx_aplRet");
               bdeBasImp_BD = rstLoc.getBigDecimal("nd_basImp");
               bdeValAbo_BD = rstLoc.getBigDecimal("nd_abo");
               strNumSer_BD = quitarCaracterEspecial(rstLoc.getString("tx_numSer"), "-");
               strNumFacPrv_BD = rstLoc.getString("tx_numChq");
               strCodSri_BD = rstLoc.getString("tx_codSri");
               blnPorRetFound = false;
                
               for (i = 0; i < arlDatDetCpbRet.size(); i++)
               {  bdePorRet_Xml = objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_POR_RET);
                  
                  if (bdePorRet_BD.compareTo(bdePorRet_Xml) == 0)
                  {  blnPorRetFound = true;
                     strAplRet_Xml = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_APL_RET);
                     bdeBasImp_Xml = objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_BAS_IMP);
                     bdeValRet_Xml = objUti.getBigDecimalValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_VAL_RET);
                     strNumSer_Xml = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_NUM_SER_FAC);
                     strNumFacPrv_Xml = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_NUM_FAC);
                     strCodSri_Xml = objUti.getStringValueAt(arlDatDetCpbRet, i, INT_ARL_DAT_DET_CPB_RET_COD_SRI);
                     
                     if ( strAplRet_BD.equals(strAplRet_Xml) && bdeBasImp_BD.compareTo(bdeBasImp_Xml) == 0 && bdeValAbo_BD.compareTo(bdeValRet_Xml) == 0
                         && strNumSer_BD.equals(strNumSer_Xml) && strNumFacPrv_BD.equals(strNumFacPrv_Xml) && strCodSri_BD.equals(strCodSri_Xml) )
                     {
                         //Los datos de tbm_detPag y del arreglo si son iguales.
                     }
                     else
                     {  //Los datos de tbm_detPag y del arreglo no son iguales.
                        blnRes = false;
                     }
                  }
               }
               
               if (blnPorRetFound == false)
               {  //Como el Por.Ret. de tbm_detPag no fue encontrado en el arreglo arlDatDetCpbRet, significa que los datos de tbm_detPag y del arreglo 
                  //no son iguales.
                  blnRes = false;
               }
            } //while (rstLoc.next() && blnRes == true)

            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
      
      catch(Exception e)
      {  
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion verificar_DetXML_DetPag_Ret()
   
   public void copiar(File src, File dst) throws IOException
   { 
      try
      {  InputStream in = new FileInputStream(src); 
         OutputStream out = new FileOutputStream(dst);

         byte[] buf = new byte[1024]; 
         int len; 

         while ((len = in.read(buf)) > 0)
         { 
            out.write(buf, 0, len); 
         } 
         in.close(); 
         out.close();
      }
      
      catch(Exception evt)
      {
         System.out.println(evt.getMessage()); 
      }
   }
   
   public boolean impresionFactura(Connection connImp, int intCodEmp, int intCodLoc, int codtipdoc, int codoc, ArrayList arlResWS)
   {
        //retNomEmp(intCodEmp);
        String fecha = "", DIRECCION_REPORTE_FACTURA, strImpDirectaFac = "facturacion", strRutRpt, strNomEmpRep;
        java.util.Date dtFecHor;
        boolean isLinux;

        try{
            DIRECCION_REPORTE_FACTURA = "/Zafiro/Reportes/Ventas/ZafVen02/ZafRptVen02.jasper";
            //conocer el tipo de S.O
            isLinux = System.getProperty("os.name").equals("Linux")? true :false;
        
            //if (isLinux){
            if (true){ //DBN_Prueba
                    if(connImp!=null){
                        dtFecHor=new java.util.Date();
                        fecha=objUti.formatearFecha(dtFecHor,"dd-MM-yyyy-hh-mm-ss");

                        strNomEmpRep = "";
         
                        switch (intCodEmp)
                        {
                           case 1:
                              strNomEmpRep = "Tuv";
                              break;
                           case 2:
                              strNomEmpRep = "Cas";
                              break;
                           case 4:
                              strNomEmpRep = "Dim";
                        }
                        
                        strRutRpt = "";
         
                        if (! System.getProperty("os.name").equals("Linux"))
                           strRutRpt = "//172.16.1.2";
                           
                        strRutRpt += "/Zafiro/Reportes/Ventas/ZafVen02/";
                        
                        Map parameters = new HashMap();
                        /*parameters.put("codEmp", new Integer(intCodEmp));
                        parameters.put("codLoc", new Integer(intCodLoc));
                        parameters.put("codTipDoc", new Integer(codtipdoc));
                        parameters.put("codDoc", new Integer(codoc));*/
                        
                        parameters.put("codEmp", intCodEmp);
                        parameters.put("codLoc", intCodLoc);
                        parameters.put("codTipDoc", codtipdoc);
                        parameters.put("codDoc", codoc);
                        parameters.put("ambiente", intTipAmb); //1 = Pruebas; 2 = Produccion
                        parameters.put("RUTA_LOGO", strRutRpt + "Logos" + File.separator + "log" + strNomEmpRep + ".png");

                        if(System.getProperty("os.name").equals("Linux")){
                            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                            JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_FACTURA, parameters, connImp);
                            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName(strImpDirectaFac, null);
                            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                            printServiceAttributeSet.add(printerName);
                            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                            objJRPSerExp.exportReport();

                        }else{
                            // JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
                            JasperPrint report = JasperFillManager.fillReport(DIRECCION_REPORTE_FACTURA, parameters, connImp);
                            JasperPrintManager.printReport(report, true); 
                        }
                    }
                }else {
                        //impresionFactura(intTipRptVta, intTipDoc, codoc);
                    }
        }
        
        catch (JRException e)
        {  
           //objUti.mostrarMsgErr_F1(jfrThis, e);
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al ejecutar impresionFactura(). " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           return false;
        }
        
        return true;
   } //Funcion impresionFactura()
   
   public boolean updateCamposFacEle(Connection con, String strNomTbl, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      java.sql.Statement stm;
      String strEst, strMsj, strNumAut, strFecAut, strEstClvAccReg, strClvAcc;
      
      try
      {
         stm = con.createStatement();
         
         if (arlResWS.size() > 0)
         {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
            strMsj = objUti.getStringValueAt(arlResWS, 0, 1); //Mensaje devuelto por el Servicio Web o por alguna excepcion
            strNumAut = objUti.getStringValueAt(arlResWS, 0, 2); //Num. autorizacion devuelto por el Servicio Web
            strFecAut = objUti.getStringValueAt(arlResWS, 0, 3); //Fecha de autorizacion devuelto por el Servicio Web
            strEstClvAccReg = objUti.getStringValueAt(arlResWS, 0, 4); //Estado usado para indicar si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
            strClvAcc = objUti.getStringValueAt(arlResWS, 0, 5); //Clave de acceso generada en el XML
            
            if (strMsj.length() > 1200)
            {  //Se debera truncar el mensaje porque el campo en la tabla es un varchar(1200) y si no se trunca, 
               //va a haber un error al momento de ejecutar el UPDATE
               strMsj = strMsj.substring(0, 1200);
            }

            strSQL =  "UPDATE " + strNomTbl + " ";
            strSQL += "SET    st_autFacEle = '" + strEst + "', ";
            strSQL += "       tx_desErrFacEle = '" + strMsj + "', ";
            strSQL += "       tx_claAccFacEle = '" + strClvAcc + "' ";

            if (strEst.equals("A") || strEstClvAccReg.equals("S"))
            {  //A: Si hubo autorizacion del comprobante
               //S: El Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". De todas maneras, hay que guardar los datos de la autorizacion
               strSQL += ", ";
               strSQL += "tx_numAutFacEle = '" + strNumAut + "', ";
               strSQL += "fe_autFacEle = '" + strFecAut + "' ";
            }

            strSQL += "WHERE co_emp = " + intCodEmp;
            strSQL += "      and co_loc = " + intCodLoc;
            strSQL += "      and co_tipdoc = " + intCodTipDoc;
            strSQL += "      and co_doc = " + intCodDoc;
            stm.executeUpdate(strSQL);
         }
         
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
   } //Metodo updateCamposFacEle()
}