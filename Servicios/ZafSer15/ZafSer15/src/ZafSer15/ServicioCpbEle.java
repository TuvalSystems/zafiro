/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafSer15;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafProCpbEle.ZafProCpbEle;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

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
               if (!strMsjRec.equals(""))
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
      ZafProCpbEle of = new ZafProCpbEle(objParSis);
      of.ejecutarProcesoCompElec(strMsjRec);
   }
}