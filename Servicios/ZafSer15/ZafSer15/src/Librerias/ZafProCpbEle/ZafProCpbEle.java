/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer15
package Librerias.ZafProCpbEle;

import Librerias.ZafUtil.ZafUtil;
import java.io.File;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.util.StringTokenizer;
import java.sql.*;
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

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafProCpbEle
{
   Librerias.ZafParSis.ZafParSis objParSis;
   ZafUtil objUti;
   private Connection con;
   private javax.swing.JInternalFrame ifruso;
   private javax.swing.JOptionPane oppMsg;
   //Variables para armar XML de comprobantes electronicos
   private String strNomEmp;
   private String strNomCarXmlSiAut, strDirCorEnvFil;
   
   public ZafProCpbEle(Librerias.ZafParSis.ZafParSis obj)
   {
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         objUti = new ZafUtil();
         oppMsg = new JOptionPane();
         ifruso = new JInternalFrame();
         strNomCarXmlSiAut = "Con_SiAut";
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
   
   public boolean ejecutarProcesoCompElec(String StrMsjRec)
   {
      boolean blnRes = true;
      Statement stmLoc;
      ResultSet rstLoc;
      int intCodEmp, intCodLoc;
      String strAux, strSQL, strNomFilXml, strRutXmlTipCpb, strRutXmlSiAut, strPK, strRutXmlFac, strRutXmlGuiRem, strRutXmlDevVta, strRutXmlRet;
      String strDirCorUsrSis, strUsr, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strTipCpb;
      String[] strSplt;
      StringTokenizer strTokCad;
      File carpeta;
      
      try
      {  
         con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
         strSQL = "";
         strNomEmp = "";
         strNomFilXml = "";
         strRutXmlTipCpb = "";
         strRutXmlSiAut = "";
         strDirCorEnvFil = "";
         
         strRutXmlFac = objParSis.getRutXmlFac();
         strRutXmlGuiRem = objParSis.getRutXmlGuiRem();
         strRutXmlDevVta = objParSis.getRutXmlDevVta();
         strRutXmlRet = objParSis.getRutXmlRet();
         strDirCorUsrSis = objParSis.getDirCorUsrSis();
         strSplt = StrMsjRec.split("-");
         strCodEmp = strSplt[0];
         strCodLoc = strSplt[1];
         strCodTipDoc = strSplt[2];
         strCodDoc = strSplt[3];
         strTipCpb = strSplt[4];
         strUsr = strSplt[5];
         System.out.println("\n" + "Inicio de proceso de " + strTipCpb + ": Emp = " + strCodEmp + "; Loc = " + strCodLoc + "; TipDoc = " + strCodTipDoc + "; Doc = " + strCodDoc);
         
         //--- Inicio bloque comentado el 9/Feb/2018 ---
         //Este bloque fue comentado en la version v0.1 (24/Ene/2018) para que la direccion de correo del usuario no sea leida del archivo de configuracion
         //"ZafParSis.properties". Ahora la direccion de correo sera leida de la tabla tbm_usr.
         /*
         strTokCad = new StringTokenizer(strDirCorUsrSis, ";");
         
         while (strTokCad.hasMoreElements())
         {  strAux = strTokCad.nextToken().toString(); //Ej. de strAux: usuario=psolorzano|correo=psolorzano@tuvalsa.com
            
            if (strAux.lastIndexOf(strUsr) != -1)
            {  strDirCorEnvFil = strAux.substring(strAux.lastIndexOf("|") + 8, strAux.length());
               break;
            }
         }*/
         //--- Fin bloque comentado el 9/Feb/2018 ------
         
         if (con != null)
         {  stmLoc = con.createStatement();
            strSQL += "SELECT tx_corele FROM tbm_usr WHERE tx_usr = '" + strUsr + "'";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {
               strDirCorEnvFil = rstLoc.getString("tx_corele") == null? "" :rstLoc.getString("tx_corele");
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         }
         
         con.close();
         con = null;
         intCodEmp = Integer.parseInt(strCodEmp);
         intCodLoc = Integer.parseInt(strCodLoc);
         
         switch (intCodEmp)
         {
            case 1:
               strNomEmp = "Tuval";
               break;
            case 2:
               strNomEmp = "Castek";
               break;
            case 4:
               strNomEmp = "Dimulti";
               break;
            case 5:
               strNomEmp = "Cosenco";
               break;
         }
         
         if (strTipCpb.equals("FAC"))
         {  
            strRutXmlTipCpb = strRutXmlFac;
         }
         else if (strTipCpb.equals("GUIREM"))
         {  
            strRutXmlTipCpb = strRutXmlGuiRem;
         }
         else if (strTipCpb.equals("DEVVTA"))
         {  
            strRutXmlTipCpb = strRutXmlDevVta;
         }
         else if (strTipCpb.equals("RET"))
         {  
            strRutXmlTipCpb = strRutXmlRet;
         }
         
         strRutXmlSiAut = strRutXmlTipCpb + strNomEmp + "/" + strNomCarXmlSiAut;
         strPK = strCodEmp + "_" + strCodLoc + "_" + strCodTipDoc + "_" + strCodDoc;
         carpeta = new File(strRutXmlSiAut);
         int i = 0;
         
         for (File archivo:carpeta.listFiles())
         {  i++;
            //System.out.println("Iteracion #" + i);
            
            strAux = archivo.getName();
            if (strAux.lastIndexOf(strPK) != -1)
            {  if (strAux.lastIndexOf("xml") != -1)
               {  strNomFilXml = strAux.substring(0, strAux.lastIndexOf(".xml"));
               }
               else if (strAux.lastIndexOf("pdf") != -1)
               {  strNomFilXml = strAux.substring(0, strAux.lastIndexOf(".pdf"));
               }
               break;
            }
         }
         
         if (!strNomFilXml.equals("") && !strDirCorEnvFil.equals(""))
         {  
            strAux = enviarCorreoServidorInterno(strTipCpb, intCodEmp, intCodLoc, strRutXmlSiAut, strNomFilXml);
         }
         
         System.out.println("\n" + "Fin de proceso de " + strTipCpb + ": Emp = " + strCodEmp + "; Loc = " + strCodLoc + "; TipDoc = " + strCodTipDoc + "; Doc = " + strCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         System.out.println("Error: " + e.toString() + "\n\n");
      }
 
      return blnRes;
   } //Metodo ejecutarProcesoCompElec()

   private String enviarCorreoServidorInterno(String strTipCpb, int intCodEmp, int intCodLoc, String strRutFilXml, String strNomFilXml)
   {
      String strRes = "", server, userName, fromAddress, clvRemite, toAddress1, toAddress2, body;
      
      try
      {  System.out.println("\n" + "Inicio de envio de correo desde servidor interno: " + strNomFilXml);
         fromAddress = "";
         clvRemite = "";
         server = "";
         userName = "";
         //userName = "zafiro"; //DBN_Prueba
         toAddress1 = strDirCorEnvFil;
         toAddress2 = "";
         //toAddress = "sistemas7@tuvalsa.com"; //DBN_Prueba
         
         switch (intCodEmp)
         {  case 1: //Tuval
               server = "mail.tuvalsa.com";
               
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
               break;
               
            case 2: //Castek
               server = "mail.castek.ec";
               
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
                  }
                  break;
               }
               break;
               
            case 4: //Dimulti
               server = "mail.dimulti.com";
               
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
               break;
                
            case 5: //Cosenco
               server = "mail.cosenco.ec";

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
               break;
         } //switch (intCodEmp)
         
         //fromAddress = "zafiro@tuvalsa.com"; //DBN_Prueba
         //clvRemite = "#tuv*sis28/10=";
         
         body = armarCuerpoCorreo(strTipCpb, strNomFilXml);
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
         
         //message.addRecipient(Message.RecipientType.BCC, new InternetAddress(fromAddress));

         // Set the subject
         message.setSubject("Comprobantes electronicos");

         MimeBodyPart cuerpoCorreo = new MimeBodyPart();
         //cuerpoCorreo.setText("Estado de Cuenta ...");
         cuerpoCorreo.setContent(body, "text/html");

         BodyPart adjunto1 = new MimeBodyPart();
         adjunto1.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".xml")));
         adjunto1.setFileName(strNomFilXml + ".xml");

         BodyPart adjunto2 = new MimeBodyPart();
         
         if (!strTipCpb.equals("GUIREM"))
         {  adjunto2.setDataHandler(new DataHandler(new FileDataSource(strRutFilXml + File.separator + strNomFilXml + ".pdf")));
            adjunto2.setFileName(strNomFilXml + ".pdf");
         }
         
         MimeMultipart multipart = new MimeMultipart();
         multipart.addBodyPart(cuerpoCorreo);
         multipart.addBodyPart(adjunto1);
         
         if (!strTipCpb.equals("GUIREM"))
         {  multipart.addBodyPart(adjunto2);
         }
         
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
      
      //PasswordAuthentication l = new PasswordAuthentication("zafiro", "#tuv*sis28/10=");
      protected PasswordAuthentication getPasswordAuthentication()
      {
         return l;
      }
   }
   
   private String armarCuerpoCorreo(String strTipCpb, String strNomFilXml)
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
         
         if (!strTipCpb.equals("GUIREM"))
         {  strBody += strNomFilXml + ".pdf";
         }
         
         strRes = strBody;
      }
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion armarCuerpoCorreo()
}