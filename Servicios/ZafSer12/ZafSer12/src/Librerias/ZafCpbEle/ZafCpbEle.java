/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer12
package Librerias.ZafCpbEle;

//import ec.gob.sri.comprobantes.table.model.FileTableModel;
import ec.gob.sri.comprobantes.util.ArchivoUtils;
import ec.gob.sri.comprobantes.util.AutorizacionComprobantesWs;
import ec.gob.sri.comprobantes.util.EnvioComprobantesWs;
import ec.gob.sri.comprobantes.util.FormGenerales;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.math.BigDecimal;
//Librerias para generacion de reporte en PDF
import java.util.HashMap;
import java.util.Map;
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
import net.sf.jasperreports.engine.JasperCompileManager;
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
   private ZafNotCorEle objNotCorEle;
   private javax.swing.JInternalFrame ifruso;
   private javax.swing.JOptionPane oppMsg;
   int intTipAmb, intTipEmi, intTimEsp;
   String strSQL, strConEsp, strRuc, strNomEmp, strRucEmp, strDirEmp, strDirLoc, strCodEst, strPtoEmi, strCorEleCliFacEle, strCorEleCliFacEle2;
   String strDesForPagSri, strEnvCorTipAmbSriPru;
   ArrayList arlRegDetResWS;
   private static final int INT_ARL_COD_CFG_RETENCIONES = 29;
   //Constantes del arreglo que devuelven los metodos de autorizacion de algun comprobante electronico
   static final int INT_ARL_DAT_RES_EST = 0;
   static final int INT_ARL_DAT_RES_MSJ = 1;
   static final int INT_ARL_DAT_RES_NUM_AUT = 2;
   static final int INT_ARL_DAT_RES_FEC_AUT = 3;
   static final int INT_ARL_DAT_RES_EST_CLV_ACC_REG = 4;
   static final int INT_ARL_DAT_RES_CLV_ACC = 5;
   public boolean blnDirMacSrvPrd_Found;
   public String strEjeProTipAmbSriPru;
      
   public ZafCpbEle(Librerias.ZafParSis.ZafParSis obj)
   {
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         objUti = new ZafUtil();
         oppMsg = new JOptionPane();
         ifruso = new JInternalFrame();
         verificarDireccionMAC();
      }
      
      catch (CloneNotSupportedException e)
      {  
         mostrarMsgErr(e.getMessage());
      }
   }

   public ZafCpbEle(Librerias.ZafParSis.ZafParSis obj, String ruc, int TimEsp)
   {
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         this.strRuc = ruc;
         this.intTimEsp = TimEsp;
         objUti = new ZafUtil();
         verificarDireccionMAC();
      }
      
      catch (CloneNotSupportedException e)
      {  
         mostrarMsgErr(e.getMessage());
      }
   }
   
   private void verificarDireccionMAC()
   {  
      try
      {
         String strAux, strDirMacSrvPro, strHorPC, strHorDesEnvCorTipAmbSriPru, strHorHasEnvCorTipAmbSriPru;
         StringTokenizer strTokCad;
         Date datHorPC, datHorDesEnvCorTipAmbSriPru, datHorHasEnvCorTipAmbSriPru;

         strEjeProTipAmbSriPru = objParSis.getEjecutarProcesoTipoAmbienteSRIPruebas();
         strEnvCorTipAmbSriPru = objParSis.getEnviarCorreoTipoAmbienteSRIPruebas();
         blnDirMacSrvPrd_Found = false;
         strDirMacSrvPro = objParSis.getDireccionMACServidorProduccion();
         strTokCad = new StringTokenizer(strDirMacSrvPro, "|");

         while (strTokCad.hasMoreElements())
         {  strAux = strTokCad.nextToken().toString(); //Ej. de strAux: 00-0C-29-12-12-0B

            if (strAux.lastIndexOf(objParSis.getMACAddress()) != -1)
            {  //Si la MAC Address corresponde al servidor de Produccion, solo en ese caso el Tipo de Ambiente del SRI
               //para Facturacion electronica sera el de Produccion
               intTipAmb = 2; //Produccion
               blnDirMacSrvPrd_Found = true;
               break;
            }
         }

         if (blnDirMacSrvPrd_Found == false)
         {  intTipAmb = 1; //Pruebas
            strHorPC = getHoraPC();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            datHorPC = dateFormat.parse(strHorPC);
            strHorDesEnvCorTipAmbSriPru = objParSis.getHoraDesdeEnviarCorreoTipoAmbienteSRIPruebas();
            strHorHasEnvCorTipAmbSriPru = objParSis.getHoraHastaEnviarCorreoTipoAmbienteSRIPruebas();
            datHorDesEnvCorTipAmbSriPru = dateFormat.parse(strHorDesEnvCorTipAmbSriPru);
            datHorHasEnvCorTipAmbSriPru = dateFormat.parse(strHorHasEnvCorTipAmbSriPru);

            if ( (datHorPC.compareTo(datHorDesEnvCorTipAmbSriPru) >= 0 && datHorPC.compareTo(datHorHasEnvCorTipAmbSriPru) <= 0) )
            {
               if (strEjeProTipAmbSriPru.equals("N") && strEnvCorTipAmbSriPru.equals("S"))
               {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI. Por tanto, se procedera a notificar por correo.
                  strAux =  "<FONT COLOR=\"blue\">Servicio 12:</FONT> Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion." + "<BR>";
                  strAux += "Por tanto, los XML se van a generar y autorizar en Ambiente de Pruebas del SRI." + "<BR>";
                  strAux += "Direccion MAC del equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getMACAddress() + "</FONT><BR>";
                  strAux += "Equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getNombreComputadoraConDirIP() + "</FONT><BR><BR>";
                  strAux += "Notifique esta novedad al Administrador del sistema";
                  objNotCorEle = new ZafNotCorEle(objParSis);

                  if (objNotCorEle.enviarNotificacionCorreoElectronico(0, 1, INT_ARL_COD_CFG_RETENCIONES, strAux))
                  {  //System.out.println("OK INT_ARL_COD_CFG_RETENCIONES");
                     //Todo OK
                  }
               }
            }
         }

         intTipEmi = 1; //Emision normal
         //intTipEmi = 9; //DBN_Prueba
      } //try
      
      catch(Exception e)
      {
         System.out.println(e.getMessage()); 
      }
   } //Funcion verificarDireccionMAC()
   
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
   
   public boolean invocarServiciosWeb(String strNomFil, ArrayList arlResWS)
   {
      boolean blnRes = true, blnClvAccReg;
      String strAux, resultado, strFecHor, strEstAut, strNumAut, strFecAut, strEstClvAccReg, strClvAcc, strGetEst;
      ArrayList arlDatAut;
      
      try
      {  //Se va a invocar los Servicios Web del SRI
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
            } //if (FormGenerales.existConnection(strGetTipoAmbiente, "RecepcionComprobantes") == true)
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
            {  strCorEleSrvInt = "edocs-comp@tuvalsa.com";
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
            {  strCorEleSrvInt = "edocs-comp@castek.ec";
               fromAddress = "edocs.castek.comp@gmail.com";
               clvRemite = "cas@comp.02";
            }
            else
            {  switch (intCodLoc)
               {  case 1: case 11: // Castek Quito
                     strCorEleSrvInt = "edocs-uio@castek.ec";
                     fromAddress = "edocs.castek.uio@gmail.com";
                     clvRemite = "cas@uio.02";
                     break;
                  case 4: case 12: // Castek Manta
                     strCorEleSrvInt = "edocs-mta@castek.ec";
                     fromAddress = "edocs.castek.mta@gmail.com";
                     clvRemite = "cas@mta.03";
                     break;
                  case 6: case 13: // Castek Santo Domingo
                     strCorEleSrvInt = "edocs-sto@castek.ec";
                     fromAddress = "edocs.castek.sto@gmail.com";
                     clvRemite = "cas@sto.04";
                     break;
                  case 10: case 14: // Castek Cuenca
                     strCorEleSrvInt = "edocs-cue@castek.ec";
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
            {  strCorEleSrvInt = "edocs-comp@dimulti.com";
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
      
      try
      {
         strSQL= "SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
         //--- Inicio bloque comentado el 26/Jun/2015 ---
         //Esta linea fue comentada en la version del programa 0.1.3 (26/Jun/2015) debido a que, al mostrar la leyenda 'IMPUESTO A LA RENTA' 
         //se consideraba el valor de 10 en la condicion del campo tbm_pagMovInv.nd_porret, correspondente a "Retencion en la fuente 10%". 
         //Se tuvo que quitar este valor de 10 en la condicion debido a que ahora ha aparecido el valor de 10 para "Retencion al IVA 10%" y esto 
         //se debe mostrar en la leyenda del reporte 'IVA'.
         //
         //strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
         //--- Fin bloque comentado el 26/Jun/2015 ------
         
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
   
   public boolean enviarCorreoDiferido(String strPrmNomEmp, String strTipCpb, String strNomTbl, Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, String strNomFil, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux, strAux2;
      int intCodCli;
      
      try
      {  System.out.println("\n" + "Inicio de proceso de Fact: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         intCodCli = getCodCliente(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strNomTbl);
         strCorEleCliFacEle = "";
         strCorEleCliFacEle2 = "";
         //La funcion buscarCorEleCliFacEle() se encarga de llenar las variables strCorEleCliFacEle y strCorEleCliFacEle2
         buscarCorEleCliFacEle(con, intCodEmp, intCodCli);
         obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS);
         
         strAux = generarPDF(strPrmNomEmp, strTipCpb, con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFil, strNomFil);
         if (strAux.equals("true"))
         {  //La generacion del PDF se realizo exitosamente
            if (strCorEleCliFacEle.equals("") && strCorEleCliFacEle2.equals(""))
            {  strAux = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
               strAux2 = "No se envió el correo debido a que no se encontró una dirección de correo en la base de datos";
               //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de "correo no enviado debido a..."
               strAux = strAux + ". " + strAux2;
               objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
               updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
            else
            {  strAux = enviarCorreoServidorInterno(strPrmNomEmp, strTipCpb, intCodEmp, intCodLoc, strRutFil, strNomFil);

               if (strAux.equals("true"))
               {  /*int Envio_de_correo_OK = 0;*/ }
               else
               {  //Hubo un error en el envio del correo
                  blnRes = false;
                  strAux2 = objUti.getStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ);
                  
                  if (!strAux2.equals(""))
                  {  //Se concatena el mensaje devuelto por el Servicio Web + el mensaje de excepcion por fallo de envio de correo
                     strAux = strAux2 + ". " + strAux;
                  }
                  
                  objUti.setStringValueAt(arlResWS, 0, INT_ARL_DAT_RES_MSJ, strAux);
                  updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
               
               strAux = enviarCorreoServidorExterno(strPrmNomEmp, strTipCpb, intCodEmp, intCodLoc, strRutFil, strNomFil);

               if (strAux.equals("true"))
               {  /*int Envio_de_correo_OK = 0;*/ }
               else
               {  //Hubo un error en el envio del correo.
                  blnRes = false;
                  strAux2 = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strNomTbl);

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
                  updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
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
            updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         } //if (strAux.equals("true"))
         
         System.out.println("\n" + "Fin de proceso de Fact: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en metodo enviarCorreoDiferido(). " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         System.out.println("Error en proceso de Fact: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
      return blnRes;
   } //Metodo enviarCorreoDiferido()
   
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
         strDesForPagSri = ""; //Variable global
         
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
                  strDesForPagSri = "Sin utilización del sistema financiero";
                  break;
               case 4: //Tarjeta de credito/Debito
                  strDesForPagSri = "Tarjeta de crédito";
                  break;
               default:
                  strDesForPagSri = "Otros con utilización del sistema financiero";
                  break;
            }
            
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
   
   private String getHoraPC()
   {
      String strRes = "", strHor, strMin;
      int intHor, intMin;
       
      try
      {
         Calendar objCal = new GregorianCalendar();
         intHor = objCal.get(Calendar.HOUR_OF_DAY);
         intMin = objCal.get(Calendar.MINUTE);
         strHor = String.format("%02d", intHor);
         strMin = String.format("%02d", intMin);
         strRes = strHor + ":" + strMin;
      }
       
      catch(Exception e)
      {
         strRes = "";
      }
    
      return strRes;
   } //Funcion getHoraPC()
   
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
            strSQL += "       tx_desErrFacEle = '" + strMsj + "' ";

            if (strEst.equals("A") || strEstClvAccReg.equals("S"))
            {  //A: Si hubo autorizacion del comprobante
               //S: El Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". De todas maneras, hay que guardar los datos de la autorizacion
               strSQL += ", ";
               strSQL += "tx_numAutFacEle = '" + strNumAut + "', ";
               strSQL += "fe_autFacEle = '" + strFecAut + "', ";
               strSQL += "tx_claAccFacEle = '" + strClvAcc + "' ";
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