/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafSer11;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafProCpbEle.ZafProCpbEle;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 *
 * @author: Dennis Betancourt
 */
public class ServicioCpbEle
{
   private ZafThreadarrancap objThrArr;
   int intPuerto;
   ZafParSis objParSis = new ZafParSis();
   ServerSocket puerto = null;
   String strMsjRec = "";
   private static boolean blnEjeProCosEcuDet = true;
   
   public ServicioCpbEle()
   {
      intPuerto = objParSis.getPuerto();
      iniciaServidorCpbEle();
   }
   
   private void iniciaServidorCpbEle()
   {
      try
      {
         puerto = new ServerSocket(intPuerto);
         objThrArr = new ZafThreadarrancap();
         objThrArr.start();
      }
      
      catch (Exception e)
      {
         System.out.println(e.getMessage());
      }
   }

   private class ZafThreadarrancap extends Thread
   {
      public ZafThreadarrancap()
      { 
      
      }

      @Override
      public void run()
      {  while(true)
         {  try
            {  long tiempo = Calendar.getInstance().getTimeInMillis();
               System.out.println("el tiempo es: " + tiempo);
               Socket s1 = puerto.accept();
               DataInputStream dis = new DataInputStream(s1.getInputStream());
               strMsjRec = dis.readUTF();
               if (strMsjRec.equals(""))
               {
                  iniciarProceso();
               }
               else
               {
                  System.out.println("POR EL ELSE....................................................");
               }
               dis.close();
               s1.close();
            }
             
            catch(IOException e)
            {
               e.printStackTrace();
               System.out.println(" " + e.toString() );
               //objEnvMail.enviarCorreo(" "+e.toString() );
            }
         }
      }
   }
   
   private void iniciarProceso()
   {
      String strHorDesManEjeProCosEcuDet, strHorHasManEjeProCosEcuDet, strHorDesTarEjeProCosEcuDet, strHorHasTarEjeProCosEcuDet, strHorPC;
      Date datHorPC, datHorDesManEjeProCosEcuDet, datHorHasManEjeProCosEcuDet, datHorDesTarEjeProCosEcuDet, datHorHasTarEjeProCosEcuDet;
      
      try
      {
         ZafProCpbEle of = new ZafProCpbEle(objParSis);
      
         strHorDesManEjeProCosEcuDet = objParSis.getHoraDesdeMananaEjecucionProcesoCosencoEcuatosaDetopacio();
         strHorHasManEjeProCosEcuDet = objParSis.getHoraHastaMananaEjecucionProcesoCosencoEcuatosaDetopacio();
         strHorDesTarEjeProCosEcuDet = objParSis.getHoraDesdeTardeEjecucionProcesoCosencoEcuatosaDetopacio();
         strHorHasTarEjeProCosEcuDet = objParSis.getHoraHastaTardeEjecucionProcesoCosencoEcuatosaDetopacio();

         strHorPC = getHoraPC();
         DateFormat dateFormat = new SimpleDateFormat("HH:mm");
         datHorPC = dateFormat.parse(strHorPC);
         datHorDesManEjeProCosEcuDet = dateFormat.parse(strHorDesManEjeProCosEcuDet);
         datHorHasManEjeProCosEcuDet = dateFormat.parse(strHorHasManEjeProCosEcuDet);
         datHorDesTarEjeProCosEcuDet = dateFormat.parse(strHorDesTarEjeProCosEcuDet);
         datHorHasTarEjeProCosEcuDet = dateFormat.parse(strHorHasTarEjeProCosEcuDet);
         
        if ( (datHorPC.compareTo(datHorDesManEjeProCosEcuDet) >= 0 && datHorPC.compareTo(datHorHasManEjeProCosEcuDet) <= 0) || 
              (datHorPC.compareTo(datHorDesTarEjeProCosEcuDet) >= 0 && datHorPC.compareTo(datHorHasTarEjeProCosEcuDet) <= 0)
            )
        {  if (blnEjeProCosEcuDet == true)
            {  //System.out.println("ejecutarProceso_CosEcuDet");
               //Se debe ejecutar el proceso en Cosenco, Ecuatosa y Detopacio
               //of.ejecutarProcesoCompElec("Cosenco");
               of.ejecutarProcesoCompElec("Ecuatosa");
               of.ejecutarProcesoCompElec("Detopacio"); //DBN. Quitar comentario al generar el jar de Produccion
            }
            
            //La variable booleana blnEjeProCosEcuDet debe tener false para que en un determinado lapso de tiempo, se ejecute una sola vez el proceso 
            //para Cosenco, Ecuatosa y Detopacio. Por ejemplo, supongamos que el horario de ejecucion de la mañana es entre 11:00 a 11:59. En ese lapso de 
            //tiempo, el proceso se debera ejecutar una sola vez para Cosenco, Ecuatosa y Detopacio, asi se hayan lanzado multiples pulsos al Servicio 11.
            blnEjeProCosEcuDet = false;
            //System.out.println("TRUE: blnEjeProCosEcuDet = " + blnEjeProCosEcuDet);
         }
         else
         {  //La variable booleana blnEjeProCosEcuDet debe tener true para que se pueda volver a ejecutar el proceso para Cosenco, Ecuatosa y Detopacio, 
            //si es que esta dentro del horario indicado en las variables leidas del archivo ZafSer11.properties.
            blnEjeProCosEcuDet = true;
            //System.out.println("FALSE: blnEjeProCosEcuDet = " + blnEjeProCosEcuDet);
         }
         
          //of.ejecutarProcesoCompElec("Cosenco"); //DBN. Comentar bloque al generar el jar de Produccion
         of.ejecutarProcesoCompElec("Ecuatosa");
         of.ejecutarProcesoCompElec("Detopacio"); 
         
         of.ejecutarProcesoCompElec("Tuval");
      } //try
      
      catch(Exception e)
      {
         System.out.println(e.getMessage()); 
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