/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer16
package ZafSer16;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafRemayo.ZafRemayo;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafProRemayo
{
   ZafParSis objParSis;
   ZafUtil objUtil;
    
   Timer t = new Timer();
   private Connection con;
   private int intFreEje;
   private String strHorDesManEjeSer, strHorHasManEjeSer, strHorDesTarEjeSer, strHorHasTarEjeSer, strHorDesNocEjeSer, strHorHasNocEjeSer, strHorPC, strStrCon;
   private String strUsrBasDat, strClvBasDat, strEjeSer, strFecSrv;
   private int intAnoSrv, intMesSrv;
   private Date datHorPC, datHorDesManEjeSer, datHorHasManEjeSer, datHorDesTarEjeSer, datHorHasTarEjeSer, datHorDesNocEjeSer, datHorHasNocEjeSer;
   
   static final int INT_TUV_CAS_DIM = 1;
   static final int INT_ECU = 2;
   static final int INT_DET = 3;
   static final int INT_COS = 0;
    
   public ZafProRemayo()
   {
      TimerTask tt = new TimerTask() 
      {
         @Override
         public void run()
         {
            try
            {
               Date datFecAuxLoc;
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
                
               if ( ( (datHorPC.compareTo(datHorDesManEjeSer) >= 0 && datHorPC.compareTo(datHorHasManEjeSer) <= 0) || 
                      (datHorPC.compareTo(datHorDesTarEjeSer) >= 0 && datHorPC.compareTo(datHorHasTarEjeSer) <= 0) ||    
                      (datHorPC.compareTo(datHorDesNocEjeSer) >= 0 && datHorPC.compareTo(datHorHasNocEjeSer) <= 0) 
                    ) && strEjeSer.equals("S")
                  )
               {  //Se debe ejecutar el servicio. strEjeSer es un parametro leido del objeto ZafParSis.
                  //System.out.println("Hora_PC (" + strHorPC + ") es >= que Hora_desde_ejecucion_servicio (" + strHorDesEjeSer + ")");
                  System.out.println("Inicio de proceso de Servicio 16");
                  objParSis = new ZafParSis();
                  leerParametrosConfiguracion(objParSis);
                  objUtil = new ZafUtil();
                  datFecAuxLoc = objUtil.getFechaServidor(objParSis.getStringConexion_Tuval_Castek_Dimulti(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                  strFecSrv = objUtil.formatearFecha(datFecAuxLoc, objParSis.getFormatoFechaHoraBaseDatos());
                  intAnoSrv = Integer.parseInt(strFecSrv.substring(0, 4)); //Si strFecSrv es 2016-12-01, va a extraer "2016"
                  intMesSrv = Integer.parseInt(strFecSrv.substring(5, 7)); //Si strFecSrv es 2016-12-01, va a extraer "12"
                  
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
                     System.out.println("Nom_BD: " + con.getCatalog() + ". Inicio de remayorizacion");
                     ejecutarProceso(i); //DBN
                     System.out.println("Nom_BD: " + con.getCatalog() + ". Fin de remayorizacion");
                  }
                  
                  con.close();
                  con = null;
                  //t.cancel();
               }
            }

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
   } //public ZafProRemayo()
   
   private boolean leerParametrosConfiguracion(ZafParSis objParSis)
   {
      boolean blnRes = true;
     
      try
      {  intFreEje = objParSis.getFrecuenciaEjecucion();
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
   
   private boolean ejecutarProceso(int intIndPro)
   {
      boolean blnRes = true;
     
      try
      {  
         metodo(1);
      
         //intIndPro = Indice del proceso.
         if (intIndPro == INT_TUV_CAS_DIM)
         {  //Si es verdadero, significa que la conexion es con la base de datos Zafiro2006, es decir, la base de datos de Tuval, Castek, Dimulti y Cosenco.
            metodo(2);
            metodo(4);
            metodo(5);
         }
      } //try
         
      catch(Exception e)
      {  
         blnRes = false;
      }
         
      return blnRes;
   } //Funcion ejecutarProceso()
   
   private void metodo(int intCodEmp)
   {
      try
      {  
         ZafRemayo objRemayo = new ZafRemayo();
         objRemayo.remayorizarPeriodo(con, intCodEmp, intAnoSrv, intMesSrv);
      }
      
      catch(Exception evt)
      {
         System.out.println("Error en metodo(). " + evt.getMessage()); 
      }
   }
   
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
}