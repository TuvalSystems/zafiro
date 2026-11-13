/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso;

import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 *
 * @author Sistemas6
 */
public class ZafSer17_03 {
    
     private ZafThreadarrancap objThrArr;
   int intPuerto;
   ZafParSis objParSis = new ZafParSis();
   ServerSocket puerto = null;
   String strMsjRec = "";
   private static boolean blnEjeProCosEcuDet = true;
   private ZafCfgSer objCfgSer;
   private int intCodSer=19;  // Sistema de reserva 
   
   private ZafCorEle objCorEle;
   private String strCorEle="sistemas6@tuvalsa.com", strTit="Sistema de Reservas - Pulso";
   
   
    public ZafSer17_03(){
        objParSis = new ZafParSis();
        objCfgSer = new ZafCfgSer(objParSis);
        intPuerto =  -1;
        objCorEle = new ZafCorEle(objParSis);
        iniciaServidor();
    }
    
    private void iniciaServidor(){
        try
        {
            objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(), intCodSer); 
            intPuerto = objCfgSer.getPueSer();
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
        public void run(){  
            while(true){  
                try{  
                    long tiempo = Calendar.getInstance().getTimeInMillis();
                    System.out.println("el tiempo es: " + tiempo);
                    Socket s1 = puerto.accept();
                    DataInputStream dis = new DataInputStream(s1.getInputStream());
                    strMsjRec = dis.readUTF();
                    if (strMsjRec.equals("")){
                        objCorEle.enviarCorreoMasivo(strCorEle, strTit, "Pulso ZafSer17..... ");
                        if(iniciarProceso()){
                            System.out.println("OK");
                        }
                    }
                    else{
                       System.out.println("POR EL ELSE....................................................");
                    }
                    dis.close();
                    s1.close();
                }
                catch(IOException e){
                   e.printStackTrace();
                   System.out.println(" " + e.toString() );
                }
                catch (Exception e){
                   System.out.println(e.getMessage());
                }
            }
        }
   }
   
   
   
    private boolean iniciarProceso(){
        boolean blnRes=false;
        try{
            System.out.println("iniciaGenerarFacturasAutomaticas.... ");
            ZafSer17 objSer17 = new ZafSer17();
            if( objSer17.iniciarProceso()){
                blnRes=true;
            }
            objSer17 = null;
            System.out.println("#Fin InciciarProceso... ");
        }
        catch (Exception e){
            blnRes=false;
            System.out.println(e.getMessage());
        }
        return blnRes;
    }
   
    
}
