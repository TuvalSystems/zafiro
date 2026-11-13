/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso; 

import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 
 * @author Sistemas6
 */
public class Main {
    
     
    private ZafThreadarrancap objThrArr;

    private String strNomArchHis="";
    private String strFormatDate ="d/m/y";
    private String strEstEjeProeFecHorDet="N";
    private String strFechHorEjePro="";
   
    private int intTiempoHiloDormido=1800000;  // 1800000 30 minutos  // 900000  15 min  // 600000//10 min  // 300000 5 min
     
    private int intHorEjejeProFacAut, intHorEjejeProCanAut, intHorEjejeProNot;  // Horas de ejecucion de los procesos
    private boolean blnEjeProHor=true;

    private String strVersion="ZafSer17 v0.14";
    private ZafCorEle objCorEle;
    private ZafParSis objParSis;
     
    /**
     * @param args the command line arguments
     */
 
    public Main(){                
        objParSis = new ZafParSis();
        objCorEle = new ZafCorEle(objParSis);
        intHorEjejeProFacAut=objParSis.getHoraEjeProcesoFacturacionAutamatica();
        intHorEjejeProCanAut=objParSis.getHoraEjecucionProcesoCancelacionAutomatica(); 
        intHorEjejeProNot=objParSis.getHoraEjecucionProcesoNotificaciones(); 
        System.out.println("" + strVersion);
        new ZafSer17_03();
        iniciaServidorEntreCompañias();
    }
 
 
    private void iniciaServidorEntreCompañias(){
        try{
              objThrArr= new ZafThreadarrancap();
              objThrArr.start();
        } 
        catch (Exception e) { 
               System.out.println("error  "+e.toString() );    
        }
    }
    
    boolean blnEjecutar=true;
    private String strCorEle="sistemas6@tuvalsa.com;jota.marin@live.com", strTit="Sistema de Reservas";
    private boolean blnNuevaForma=true;  // Nueva manera de trabajo hasta ponerlo en la 1.2
    
    private class ZafThreadarrancap extends Thread
    {
        public ZafThreadarrancap()
        {
        }

            @Override
        public void run()
        {
         
           while(true){
                try{
                    if(blnNuevaForma){
                        java.util.Date objFec = new java.util.Date();
                        System.out.println(" =======================================");
                        System.out.println(" ====>>> "+strVersion+" <<===== "); 
                        System.out.println(" =======================================");
                        System.out.println("BASE: " + objParSis.getStringConexion());
                        System.out.println("HORA: " + objFec.getHours());
                        System.out.println("EJECUCION: " + intHorEjejeProFacAut);
                        Calendar calendario = new GregorianCalendar();
                        int intDia = calendario.get(Calendar.DATE);
                        if(intHorEjejeProFacAut==objFec.getHours()){
                            if(blnEjecutar){
                                if (intDia == 1){   /* Si es el dia 1 se cambia la configuracion de los vendedores */
                                    if(modificarConfiguracionVendedores()){
                                        System.out.println("Modificar la configuracion para los vendedores... ");
                                    }
                                }

                                if(iniciarGenerarFacturasAutomaticas()){
                                    System.out.println(" ..Termina Facturas Automaticas.. ");
                                    if(iniciarCancelacionReservas()){
                                        System.out.println(" ..Termina cancelacion.. ");
                                        iniciarProcesoNotificaiones();
                                        System.out.println("...Termina notificaciones...");
                                        blnEjecutar=false;
                                        System.out.println("END GAME");
                                        sleep(intTiempoHiloDormido); 
                                    }
                                    else{
                                        blnEjecutar=false;
                                        objCorEle.enviarCorreoMasivo(strCorEle, strTit, "ERROR: Cancelacion de reservas ");
                                        sleep(intTiempoHiloDormido); 
                                    }
                                }
                                else{
                                    blnEjecutar=false;
                                    objCorEle.enviarCorreoMasivo(strCorEle, strTit, "ERROR: Generacion de las facturas de reservas ");
                                    sleep(intTiempoHiloDormido); 
                                }
                            }
                        }
                        else{
                            System.out.println("Not yet " + objFec.getHours());
                            sleep(intTiempoHiloDormido); 
                        }
                       
                    }
                    // <editor-fold defaultstate="collapsed" desc=" /* José Marín: Ya no se usara 20/Feb/2020  */ ">
//                    else{
//                        java.util.Date objFec = new java.util.Date();
//                        System.out.println(strVersion+""); 
//                        System.out.println("BASE: " + objParSis.getStringConexion());
//                        System.out.println("HORA: " + objFec.getHours());
//                        System.out.println("HORA DE EJECUCION Factura Automatica: " + intHorEjejeProFacAut);
//                        System.out.println("HORA DE EJECUCION Cancelacion Automatica: " + intHorEjejeProCanAut);
//                        System.out.println("HORA DE EJECUCION Notificaciones: " + intHorEjejeProNot);
//                        /*  EJECUCION DE GENERACION DE FACTURAS */
//                        if(objFec.getHours()==intHorEjejeProFacAut   ){
//                            if(blnEjecutar){
//                                if(iniciarGenerarFacturasAutomaticas()){
//                                    System.out.println(" ..Termina Facturas Automaticas.. ");
//                                    blnEjecutar=false;
//                                    sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.
//                                }
//                                else{
//                                    blnEjecutar=false;
//                                    objCorEle.enviarCorreoMasivo(strCorEle, strTit, "ERROR: Generacion de las facturas de reservas ");
//                                    sleep(intTiempoHiloDormido); 
//                                }
//                            }
//                        }
//                        else if(objFec.getHours()==intHorEjejeProCanAut   ){
//                            if(blnEjecutar){/*  EJECUCION DE CANCELACION DE RESERVAS */
//                                if(iniciarCancelacionReservas()){
//                                    System.out.println(" ..Termina cancelacion.. ");
//                                    blnEjecutar=false;
//                                    sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.
//                                }
//                                else{
//                                    blnEjecutar=false;
//                                    objCorEle.enviarCorreoMasivo(strCorEle, strTit, "ERROR: Cancelacion de reservas ");
//                                    sleep(intTiempoHiloDormido); 
//                                }
//                            }
//                        }
//                        else if(  objFec.getHours()==intHorEjejeProNot  ){    
//                           if(blnEjecutar){ /*  ENVO DE CORREOS POR NOTIFICACION */ 
//                               if(iniciarProcesoNotificaiones()){
//                                    System.out.println("...Termina notificaciones...");
//                                    blnEjecutar=false;
//                                    sleep(intTiempoHiloDormido);  //   tiempo que se ejecuta el proceso, depende del tiempo que le pongan en configuracion se recomienda que se ponga una hora como minimo.
//                                }
//                                else{
//                                    blnEjecutar=false;
//                                    objCorEle.enviarCorreoMasivo(strCorEle, strTit, "ERROR: NOTIFICACION de Reservas ");
//                                    sleep(intTiempoHiloDormido); 
//                                }
//                           }
//                        }
//                        else{
//                            System.out.println("...NO ENTRO EN NINGUNO...");
//                            blnEjecutar=true;
//                            sleep(intTiempoHiloDormido);
//                        }
//
//                        objFec = null;
//                    }
                    //</editor-fold>
                    
                }
                catch(Exception e) {   
                    System.out.println("ERROR " + e.toString());         
                }
            }
        }
    }

    /**
     * LLama a la generacion de las facturas auutomaticas para la mercaderia que esta reservada
     * @return 
     */

    private boolean iniciarGenerarFacturasAutomaticas(){
        
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
        catch(Exception Evt)  { 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
        }
         return blnRes;
    }

    
    /**
     * LLama a los metodos de notificacione del sistema de reserva
     * @return 
     */
    
    private boolean iniciarProcesoNotificaiones(){
        boolean blnRes=false;
        try{
            System.out.println("iniciarProcesoNotificaiones.... ");
            ZafSer17_01 objSer17_01 = new ZafSer17_01();
            if( objSer17_01.iniciarProcesoNotificaciones()){
                blnRes=true;
            }
            objSer17_01 = null;
            System.out.println("#Fin InciciarProceso... ");
        }
        catch(Exception Evt)  { 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
        }
         return blnRes;
    }

    /**
     * Llama a la cancelacion de la mercaderia que necesita ser cancelada
     * 
     * @return 
     */
    
    private boolean iniciarCancelacionReservas(){
        boolean blnRes=false;
        try{
            System.out.println("iniciarCancelacionReservas.... ");
            ZafSer17_02 objSer17_02 = new ZafSer17_02();
            if( objSer17_02.iniciaCancelacionReservas()){
                blnRes=true;
            }
            objSer17_02 = null;
            System.out.println("#Fin iniciarCancelacionReservas... ");
        }
        catch(Exception Evt)  { 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }

 
     private boolean modificarConfiguracionVendedores(){
        boolean blnRes=false;
        try{
           Librerias.ZafCfgVenLocCli.ZafCfgVenLocCli objCfgVenLocCli = new Librerias.ZafCfgVenLocCli.ZafCfgVenLocCli(objParSis, null);
            if(objCfgVenLocCli.modificaConfiguracionActual()){
                blnRes=true;
            }
            objCfgVenLocCli = null;
        }
        catch(Exception Evt){ 
            blnRes=false;   
            System.out.println("Error 1 "+Evt ); 
             
        }
        return blnRes;
    }
    
 
    public static void main(String[] args) {
        // TODO code application logic here
        
        Main main = new Main();
        
    }
}
