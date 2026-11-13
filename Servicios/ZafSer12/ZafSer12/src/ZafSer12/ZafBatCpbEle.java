/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer12
package ZafSer12;

import Librerias.ZafCpbEle.ZafCpbEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import ZafSer12.ZafSer12;
import com.model.Claves;
import com.model.MetodosAlternos;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 *
 * @author sistemas4
 */
public class ZafBatCpbEle
{
   ZafParSis objParSis;
   ZafUtil objUtil;
   StringTokenizer pk;
   Claves oc;
    
   Timer t = new Timer();
   private Connection con;
   private Statement stm;
   int intFreEje;
   String strHorDesManEjeSer, strHorHasManEjeSer, strHorDesTarEjeSer, strHorHasTarEjeSer, strHorDesNocEjeSer, strHorHasNocEjeSer, strHorPC;
   String strCarXmlPenAut, strStrCon, strUsrBasDat, strClvBasDat, strEjeSer;
   Date datHorPC, datHorDesManEjeSer, datHorHasManEjeSer, datHorDesTarEjeSer, datHorHasTarEjeSer, datHorDesNocEjeSer, datHorHasNocEjeSer;
   
   static final int INT_TUV_CAS_DIM = 1;
   static final int INT_ECU = 2;
   static final int INT_DET = 3;
   static final int INT_COS = 0;
    
   public ZafBatCpbEle()
   {
      TimerTask tt = new TimerTask() 
      {
         @Override
         public void run()
         {
            try
            {
               strHorPC = getHoraPC();
               //strHorPC = "00:01"; //DBN_Prueba
               
               DateFormat dateFormat = new SimpleDateFormat("HH:mm");
               datHorPC = dateFormat.parse(strHorPC);
               System.out.println("Hora de activacion de servicio: " + strHorPC);
               System.out.println("Parametro de ejecucion de servicio: " + strEjeSer);
               datHorDesManEjeSer = dateFormat.parse(strHorDesManEjeSer);
               datHorHasManEjeSer = dateFormat.parse(strHorHasManEjeSer);
               datHorDesTarEjeSer = dateFormat.parse(strHorDesTarEjeSer);
               datHorHasTarEjeSer = dateFormat.parse(strHorHasTarEjeSer);
               datHorDesNocEjeSer = dateFormat.parse(strHorDesNocEjeSer);
               datHorHasNocEjeSer = dateFormat.parse(strHorHasNocEjeSer);
                
               if ( (datHorPC.compareTo(datHorDesManEjeSer) >= 0 && datHorPC.compareTo(datHorHasManEjeSer) <= 0) || 
                    (datHorPC.compareTo(datHorDesTarEjeSer) >= 0 && datHorPC.compareTo(datHorHasTarEjeSer) <= 0) ||    
                    (datHorPC.compareTo(datHorDesNocEjeSer) >= 0 && datHorPC.compareTo(datHorHasNocEjeSer) <= 0) )
               {  //Se debe ejecutar el servicio
                  //System.out.println("Hora_PC (" + strHorPC + ") es >= que Hora_desde_ejecucion_servicio (" + strHorDesEjeSer + ")");
                  System.out.println("Inicio de proceso de Servicio 12");
                  objParSis = new ZafParSis();
                  leerParametrosConfiguracion(objParSis);
                  
                  for (int i = 1; i <= 3; i++)
                  {  switch (i)
                     {  case INT_TUV_CAS_DIM:
                           strStrCon = objParSis.getStringConexion_Tuval_Castek_Dimulti();
                           break;
                        
                        case INT_COS:
                           //Como INT_COS = 0, el programa nunca ingresara por aqui. Este cambio se realizo debido a que Cosenco fue anexada a la base de 
                           //datos Zafiro2006.
                           strStrCon = objParSis.getStringConexion_Cosenco();
                           break;
                        
                        case INT_ECU:
                           strStrCon = objParSis.getStringConexion_Ecuatosa();
                           break;
                        
                        case INT_DET:
                           strStrCon = objParSis.getStringConexion_Detopacio();
                           break;
                     }
                     
                     con = DriverManager.getConnection(strStrCon, strUsrBasDat, strClvBasDat);
                     System.out.println("Nom_BD: " + con.getCatalog());
                     ejecutarProceso(i, "FAC", "tbm_cabmovinv"); //DBN
                     ejecutarProceso(i, "GUIREM", "tbm_cabguirem");
                     ejecutarProceso(i, "RET", "tbm_cabpag");
                     ejecutarProceso(i, "NOTCRE", "tbm_cabmovinv");
                  }
                  
                  con.close();
                  con = null;
                  //t.cancel();
               } //if ( (datHorPC.compareTo(datHorDesManEjeSer) >= 0 && datHorPC.compareTo(datHorHasManEjeSer) <= 0)...
            } //try

            catch(Exception e)
            {
               System.out.println(e.getMessage()); 
            }
         }
      };
      objParSis = new ZafParSis();
      leerParametrosConfiguracion(objParSis);
      //t.scheduleAtFixedRate(tt, 3, 3000);
      t.scheduleAtFixedRate(tt, 3, intFreEje);
   } //public ZafBatCpbEle()
   
   private boolean leerParametrosConfiguracion(ZafParSis objParSis)
   {
      boolean blnRes = true;
     
      try
      {  intFreEje = objParSis.getFrecuenciaEjecucion();
         strCarXmlPenAut = objParSis.getCarpetaXMLPendienteAutorizar();
         strHorDesManEjeSer = objParSis.getHoraDesdeMananaEjecucionServicio();
         strHorHasManEjeSer = objParSis.getHoraHastaMananaEjecucionServicio();
         strHorDesTarEjeSer = objParSis.getHoraDesdeTardeEjecucionServicio();
         strHorHasTarEjeSer = objParSis.getHoraHastaTardeEjecucionServicio();
         strHorDesNocEjeSer = objParSis.getHoraDesdeNocheEjecucionServicio();
         strHorHasNocEjeSer = objParSis.getHoraHastaNocheEjecucionServicio();
         strEjeSer = objParSis.getEjecutarServicio();
         strUsrBasDat = objParSis.getUsuarioBaseDatos();
         strClvBasDat = objParSis.getClaveBaseDatos();
      }
         
      catch(Exception e)
      {  
         blnRes = false;
      }
         
      return blnRes;
   } //Funcion leerParametrosConfiguracion()
   
   private boolean ejecutarProceso(int intIndPro, String strTipCpb, String strNomTbl)
   {
      boolean blnRes = true;
     
      try
      {  metodo(intIndPro, strTipCpb, strNomTbl, 1);
      
         //intIndPro = Indice del proceso.
         if (intIndPro == INT_TUV_CAS_DIM)
         {  //Si es verdadero, significa que la conexion es con la base de datos Zafiro2006, es decir, la base de datos de Tuval, Castek, Dimulti y Cosenco.
            metodo(intIndPro, strTipCpb, strNomTbl, 2);
            metodo(intIndPro, strTipCpb, strNomTbl, 4);
            metodo(intIndPro, strTipCpb, strNomTbl, 5);
         }
      } //try
         
      catch(Exception e)
      {  
         blnRes = false;
      }
         
      return blnRes;
   } //Funcion ejecutarProceso()
   
   private void metodo(int intIndPro, String strTipCpb, String strNomTbl, int intCodEmp)
   {
      String strSQL;
      ResultSet rstLoc;
      
      try
      {  String strAux, strNomEmp, strRutXmlTipCpb, strRutXmlPenAut, strRutXmlSiAut, strRutXmlNoAut;
         File carpeta;
         List<String> listaArchivos = new ArrayList<String>();
         int emp = 0;
         int intTim = 3;
         int intCodLoc, intCodTipDoc, intCodDoc;
         strNomEmp = "";

         //rutaCarpeta = "C:\\Zafiro\\Digitales\\Compras\\ZafCom23\\Tuval\\Con_NoAut"; //DBN_Prueba
         //strRutDes = "C:\\Zafiro\\Digitales\\Compras\\ZafCom23\\Tuval\\Con_SiAut";
         
         switch (intIndPro)
         {  case INT_TUV_CAS_DIM:
               switch (intCodEmp)
               {  case 1:
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
               break;
            
            case INT_COS:
               strNomEmp = "Cosenco";
               break;
            case INT_ECU:
               strNomEmp = "Ecuatosa";
               break;
            case INT_DET:
               strNomEmp = "Detopacio";
               break;
         }
         
         strRutXmlTipCpb = "";
         strRutXmlPenAut = "";
         strRutXmlSiAut = "";
         strRutXmlNoAut = "";

         //DBN_Prueba
         /*if (! System.getProperty("os.name").equals("Linux"))
         {  strRutXmlPenAut = "//172.16.1.2";
            strRutXmlSiAut = "//172.16.1.2";
            strRutXmlNoAut = "//172.16.1.2";
         }*/
         
         if (strTipCpb.equals("FAC"))
            strRutXmlTipCpb = "/Zafiro/Digitales/Ventas/ZafVen02/";
         else if (strTipCpb.equals("GUIREM"))
            strRutXmlTipCpb = "/Zafiro/Digitales/Compras/ZafCom23/";
         else if (strTipCpb.equals("RET"))
            strRutXmlTipCpb = "/Zafiro/Digitales/Contabilidad/ZafCon05/";
         else if (strTipCpb.equals("NOTCRE"))
            strRutXmlTipCpb = "/Zafiro/Digitales/Ventas/ZafVen28/";
         
         //DBN_inicio_Prueba. Comentar este bloque cuando se vaya a generar el jar de Produccion.
         /*if (strTipCpb.equals("FAC"))
            strRutXmlTipCpb = "C:/Zafiro/Digitales/Ventas/ZafVen02/";
         else if (strTipCpb.equals("GUIREM"))
            strRutXmlTipCpb = "C:/Zafiro/Digitales/Compras/ZafCom23/";
         else if (strTipCpb.equals("RET"))
            strRutXmlTipCpb = "C:/Zafiro/Digitales/Contabilidad/ZafCon05/";
         else if (strTipCpb.equals("NOTCRE"))
            strRutXmlTipCpb = "C:/Zafiro/Digitales/Ventas/ZafVen28/";*/
         //DBN_fin_Prueba. Comentar este bloque cuando se vaya a generar el jar de Produccion.
         
         strRutXmlPenAut += strRutXmlTipCpb + strNomEmp + "/" + strCarXmlPenAut;
         strRutXmlSiAut += strRutXmlTipCpb + strNomEmp + "/Con_SiAut";
         strRutXmlNoAut += strRutXmlTipCpb + strNomEmp + "/Con_NoAut";
         
         carpeta = new File(strRutXmlPenAut);

         for (File archivo:carpeta.listFiles())
         {  
            listaArchivos.add(archivo.getName());
         }
         
         stm = con.createStatement();
         rstLoc = null;
         
         for (int i = 0; i < listaArchivos.size(); i++)
         {
            oc = new Claves();
            oc.setEmp(listaArchivos.get(i).split("_")[0]);
            oc.setLoc(listaArchivos.get(i).split("_")[1]);
            oc.setCom(listaArchivos.get(i).split("_")[2]);
            oc.setCod(listaArchivos.get(i).split("_")[3]);
            emp = Integer.parseInt(oc.getEmp());
            
            strSQL = "select count(1) from tbm_emp where 1 = 0";
            rstLoc = stm.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
               //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
               //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
               if (strEjeSer.equals("S"))
               {  //strEjeSer es un parametro leido del objeto ZafParSis.
                  //S = indica que si se debe ejecutar el servicio.
                  String strName = listaArchivos.get(i);
                  invocaServ11(strNomEmp, strTipCpb, strNomTbl, oc, carpeta, strRutXmlSiAut, strRutXmlNoAut, strName, emp, intTim);
               }
               else
               {  //N = indica que no se debe ejecutar el servicio.
                  intCodEmp = Integer.parseInt(oc.getEmp());
                  intCodLoc = Integer.parseInt(oc.getLoc());
                  intCodTipDoc = Integer.parseInt(oc.getCom());
                  intCodDoc = Integer.parseInt(oc.getCod());
                  strAux = "No se esta ejecutando el proceso del Servicio 12. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                  updateDesFacEle(strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
               } //if (strEjeSer.equals("S"))
            }
            
            rstLoc.close();
            rstLoc = null;
         } //for (int i = 0; i < listaArchivos.size(); i++)
         
         stm.close();
         stm = null;
      } //try
      
      catch(Exception evt)
      {
         System.out.println("Error en metodo(). " + evt.getMessage()); 
      }
   } //Funcion metodo()
     
   private void invocaServ11(String strPrmNomEmp, String strTipCpb, String strNomTbl, Claves oc, File carpeta, String strRutXmlSiAut, String strRutXmlNoAut, String f, int emp, int intTim)
   {
      String strAux, ruc, e, rp, strNomFil, strSQL, strRucCli, strNumGuiRem, strNumPed, strEjeProTipAmbSriPru;
      int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      boolean blnDirMacSrvPrd_Found;
      ResultSet rstLoc;

      try
      {  intCodEmp = Integer.parseInt(oc.getEmp());
         intCodLoc = Integer.parseInt(oc.getLoc());
         intCodTipDoc = Integer.parseInt(oc.getCom());
         intCodDoc = Integer.parseInt(oc.getCod());
         strRucCli = "";
         strNumGuiRem = "";
         strNumPed = "";
         
         if (intCodTipDoc == 228)
         {  //CodTipDoc 228 = FACVENE
            strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, tx_nomcli, tx_ruc as tx_rucCli, tx_numguirem, tx_numped ";
            strSQL += "FROM   tbm_cabmovinv ";
            strSQL += "WHERE  co_emp = " + intCodEmp;
            strSQL += "       and co_loc = " + intCodLoc;
            strSQL += "       and co_tipdoc = " + intCodTipDoc;
            strSQL += "       and co_doc = " + intCodDoc;
            rstLoc = stm.executeQuery(strSQL);

            if (rstLoc.next())
            {  strRucCli = rstLoc.getString("tx_ruccli");
               strNumGuiRem = rstLoc.getString("tx_numguirem") == null? "" :rstLoc.getString("tx_numguirem");
               strNumPed = rstLoc.getString("tx_numped") == null? "" :rstLoc.getString("tx_numped");
            }
            
            rstLoc.close();
            rstLoc = null;
         }
         
         if (intCodTipDoc == 228 && strRucCli.equals("0990293244001") && (strNumGuiRem.equals("") || strNumPed.equals("")) )
         {  //CodTipDoc 228 = FACVENE
            //Si la factura es de Holcim Ecuador (RUC 0990293244001), strNumGuiRem = "" y strNumPed = "", entonces no se debe enviar al SRI
         }
         else
         {  Librerias.ZafUtil.ZafUtil objUtil = new ZafUtil();
            MetodosAlternos om = new MetodosAlternos();
            ruc = om.traeRuc(con, emp);
            //System.out.println("---->" + oc.getEmp());
            ArrayList arlResWS = new ArrayList();
            String ruta = carpeta.getAbsolutePath() + "/";
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, ruc, intTim);
            strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
            blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
            
            if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
            {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
               strAux =  "Servicio 12: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
               strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
               updateDesFacEle(strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
            }
            else
            {  
               objCpbEle.invocarServiciosWeb(ruta + f, arlResWS);
               e = objUtil.getStringValueAt(arlResWS, 0, 0);
               //System.out.println("\n" + "El estado del arreglo es: " + e + " y el XML es " + ruta + f);
               objCpbEle.updateCamposFacEle(con, strNomTbl, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               rp = ruta + f;
               strNomFil = f.substring(0, f.lastIndexOf(".xml"));
               File fOriXml = new File(carpeta + File.separator + strNomFil + ".xml");
               //e = "A"; //DBN_Prueba

               if (e.equals("A"))
               {  copiar(fOriXml, new File(strRutXmlSiAut + File.separator + strNomFil + ".xml"));

                  if (!strTipCpb.equals("GUIREM"))
                  {  //Se envia el correo de todos los Comprobantes Electronicos que no sean guias.
                     objCpbEle.enviarCorreoDiferido(strPrmNomEmp, strTipCpb, strNomTbl, con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutXmlSiAut, strNomFil, arlResWS);
                  }
               }
               else
               {   
                  copiar(fOriXml, new File(strRutXmlNoAut + File.separator + strNomFil + ".xml")); 
               }

               fOriXml.delete();
            }
         } //if (intCodTipDoc == 228 && strRucCli.equals("0990293244001") && (strNumGuiRem.equals("") || strNumPed.equals("")) )
      } //try
            
      catch(Exception evt)
      {
         System.out.println("Error en invocaServ11(). " + evt.getMessage()); 
      }
   } //Funcion invocaServ11()
        
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
         System.out.println("Error en copiar(). " + evt.getMessage()); 
      }
   } //Funcion copiar()
        
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
   
   private boolean updateDesFacEle(String strNomTbl, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strMsj)
   {
      boolean blnRes = true;
      String strSQL;
      
      try
      {  
         strSQL =  "UPDATE " + strNomTbl + " ";
         strSQL += "SET st_autFacEle = 'E',";
         strSQL += " tx_desErrFacEle = '" + strMsj + "' ";
         strSQL += "WHERE co_emp = " + intCodEmp;
         strSQL += " and co_loc = " + intCodLoc;
         strSQL += " and co_tipdoc = " + intCodTipDoc;
         strSQL += " and co_doc = " + intCodDoc;
         stm.executeUpdate(strSQL);
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion updateDesFacEle()
}