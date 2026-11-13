/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procesos;

import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JInternalFrame;
//import static procesos.EmiRetComRel.cnx;

/**
 *
 * @author Sistemas05
 */
public class ZafSer21 /*extends JFrame*/{

    ZafParSis objParSis;
    /**
     * @param args the command line arguments
     */
    RecepFacProv recFactura;
    EmiRetComRel emiRetRel;
    RetRecComRel retRecRel;
    CobMasComRel cobMasRel;
    PagMasComRel pagMasRel;
    
    Connection cnx=null;
    
    Timer programado;/*=new Timer();*/
    
    ZafSer21 cmpVenRel;

    
    public ZafSer21(){
        objParSis=new ZafParSis();
        System.out.println("Pasa par sis");
        objParSis.cargarAniosCreadosSistema(1);
        System.out.println("Pasa años creados sistema");
        objParSis.setCodigoUsuario(1);
        System.out.println("Pasa codigo usuario");
        //abrirCon2ParSis();  
        //abrirCon();
    }
    
    
   

    
    public static void main(String[] args) {
        try{
            ZafSer21 cmpVenRel=new ZafSer21();      
            //cmpVenRel.ejecutarPruebaRetRec();
            //cmpVenRel.enviarPulsoAutorizarRetenciones();
            //Thread.sleep(60000*2);//Espera 2 horas para que se autorizen            
            //cmpVenRel.verificarRetencionesNoAutorizadas();
            //cmpVenRel.ejecutar();
            cmpVenRel.crearTarea();
            //if("2_1_230_3089_Retencion_002_001_000012739_20190531_100300.pdf".matches("2_1_230_3089"+".*")){ 
              //  System.out.println("hello");
                
            //}
            //cmpVenRel.listar_Mover_Archivos_NoAutorizados("Ejemplo IVA");
            //cmpVenRel.listar_Mover_Archivos_NoAutorizados();
            //cmpVenRel.ejecutarMock();
            //System.exit(0);
            /*ZafSer21 cmpVenRel=new ZafSer21();
            cmpVenRel.crearTarea();*/
        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(-1);
        }
    }   

    

    /**
     * Metodo que crea la tarea que se ejecutar de manera programada
     * y luego de finalizar esta tares se vuelve automaticamente a programar 
     * para el siguiente mes.
     * Este metodo esta pensado para que el servicio comience a ejecutarse antes del inicio
     * del siguiente mes para que la suma de un mes (fechaCal.add(Calendar.MONTH,1))
     * sea siempre el siguiente mes
     */
    public void crearTarea(){
        Calendar fechaCal=Calendar.getInstance();
        //fechaCal.add(Calendar.MONTH,1);//ESTA DEBE IR . POR ESTA LINEA DE SER SUBIDO EL SERVICIO ANTES DE QUE TERMINE EL MES.
        //fechaCal.add(Calendar.MINUTE,2);
        //fechaCal.add(Calendar.DATE,1);
        //System.out.println("fecha con un mes mas "+fechaCal.getTime());
        //fechaCal.set(fechaCal.get(Calendar.YEAR),fechaCal.get(Calendar.MONTH),29,14,35,0);
        //fechaCal.set(fechaCal.get(Calendar.YEAR),fechaCal.get(Calendar.MONTH), 05,10,00,0);//ESTA DEBE IR. El dia 2 de cada mes a las 2 AM
        //fechaCal.set(fechaCal.get(Calendar.YEAR),fechaCal.get(Calendar.MONTH), 02,10,15,0);//ESTA DEBE IR. El dia 2 de cada mes a las 2 AM
        fechaCal.set(fechaCal.get(Calendar.YEAR),fechaCal.get(Calendar.MONTH), fechaCal.get(Calendar.DATE),20,30,0);//ESTA DEBE IR. El dia 2 de cada mes a las 2 AM
        //System.out.println("fecha primer o segundo  dia del sigte mes "+fechaCal.getTime());
        //fechaCal.add(Calendar.MINUTE,1);
        System.out.println("Fecha proxima ejecucion: "+fechaCal.getTime());
        //abrirCon2ParSis();
        TimerTask tarea=new TimerTask() {
            @Override
            public void run() {
                try{
                    //abrirCon();
                    abrirCon2ParSis();
                    ejecutar();
                    //ejecutar2();
                    System.exit(0);
                }catch(Exception ex){
                    ex.printStackTrace();
                }finally{
                    programado.cancel();
                    programado.purge();
                    //crearTarea();
                }
            }
        }; 
        programado=new Timer();
        programado.schedule(tarea, fechaCal.getTime());
    }    
 
    
   /**
    * Metodo que abre la conexion pero sin usar el archivo properties
    * fue utilizado en la etapa de desarrollo y pruebas.
    */
    public void abrirCon(){
        try{
            System.out.println("Entra a abrir conexion");
            cnx = DriverManager.getConnection("jdbc:postgresql://172.16.8.110:5432/Zafiro2006_31_Mar_2019","postgres", "*szlsc*");        
            //cnx = DriverManager.getConnection("jdbc:postgresql://172.16.1.110:5432/Zafiro2006_28_Feb_2019","postgres", "*szlsc*");
            //cnx = DriverManager.getConnection("jdbc:postgresql://172.16.8.7:5432/Zafiro2006_31_Ene_2019","postgres", "*szlsc*");
            System.out.println("conexion: "+cnx.getCatalog());
            cnx.setAutoCommit(false);
        }catch (Exception sqle) {
            System.out.println("Error: " + sqle);
        }
    }
    
    /**
     * Metodo que abre conexión con la  base configurada en ZafSer21.properties
     */
    public void abrirCon2ParSis(){
        try{
           cnx=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
           cnx.setAutoCommit(false);
            System.out.println("Database "+cnx.getCatalog());
        }catch(java.sql.SQLException  evt){            
            evt.printStackTrace();
        }
    }     
    

    /*Servicio 11 */
    /**
     * Metodo que envia pulso al servicio 11 que pone
     * la clave de acceso y el estado 'C' en la BD, ademas 
     * crea el archivo de xml en la carpeta pendientes de autorizacion
     * Luego se ejecuta el servicio 12 que es el envia a autorizar el archivo
     * xml antes mencionado. 
     * El servicio 12 a partir de cierta hora de la noche se despierta
     * y toma los archivos que esten en las carpetas pendientes de autorizar para
     * comenzar a realizar los envios al SRI de las retenciones.
     */
    public void enviarPulsoClaveAccesoRetenciones(){
        int puerto = 6010;
        String host = "172.16.8.2";    
        //String host = "172.16.8.7";    
        Socket so;
        DataOutputStream mensaje;
        DataInputStream entrada;
    
        try {
            so=new Socket(host, puerto);
            mensaje=new DataOutputStream(so.getOutputStream());
            mensaje.writeInt(1);
            so.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    
    }
    
    /**
     * Método que revisa las retenciones no autorizadas
     * luego las mueve de carpeta y cambia el estado en la BD(siempre que este en estado 'X', 'E') y las vuelve a enviar para su autorización.
     * Espera media hora y vuelve a revisar si aun existen retenciones no autorizadas.
     * Este método realiza solo 3 intentos de volver a enviar las retenciones no autorizadas
     */
    public boolean verificarRetencionesNoAutorizadas(){
        ResultSet rs=null;
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        String strCorreosUsers="",strMensajesErrorCorreo="", strNombreEmpresa="";
        byte contador=0;
        boolean booExisRetNoAut=false;
        String strActRete="",strArchivoParcial="";
        Statement stm=null, stmUpd=null;
        int intRow=0;
        
        String strQry=" select c.fe_doc,* \n" +
                " from tbm_cabpag as c\n" +
                " where c.co_tipdoc=230 \n" +
                " and c.fe_doc="+obtenerFecUltDiaMes()+"\n" +
                " and ((c.co_emp=1 and c.co_cli in (603,1039)) or (c.co_emp=2 and c.co_cli in (2854,789)) or (c.co_emp=4 and c.co_cli in (3117,498))) \n" +
                " and c.co_usring=1\n" +
                " and not exists(\n" +
                "	select * \n" +
                "	from tbm_cabpag as r\n" +
                "	where r.co_tipdoc=230 \n" +
                "	and r.fe_doc="+obtenerFecUltDiaMes()+"\n" +
                "	and ((r.co_emp=1 and r.co_cli in (603,1039)) or (r.co_emp=2 and r.co_cli in (2854,789)) or (r.co_emp=4 and r.co_cli in (3117,498))) \n" +
                "	and r.st_autfacele='A' and r.tx_numautfacele is not null \n" +
                "	and c.co_emp=r.co_emp and c.co_loc=r.co_loc and c.co_tipdoc=r.co_tipdoc and c.co_doc=r.co_doc\n" +
                ")";        
        try{
            stm=cnx.createStatement();
            System.out.println("Entra a verificar autorizacion de retenciones pendientes ");
            System.out.println("Database: "+cnx.getCatalog());
            while (contador<3){
                rs=stm.executeQuery(strQry);
                strMensajesErrorCorreo="";                
                while(rs.next()){                    
                    strMensajesErrorCorreo+=rs.getInt("co_emp")+"-"+rs.getInt("co_loc")+"-"+rs.getInt("co_tipdoc")+"-"+rs.getInt("co_doc")+"\n";                
                    //Archivo a mover
                    strArchivoParcial=rs.getString("co_emp")+"-"+rs.getString("co_loc")+"-"+rs.getString("co_tipdoc")+"-"+rs.getString("co_doc");
                    //Método que mueve archivos de No autorizados a pendientes de autorizar.
                    int intEmpresa=rs.getInt("co_emp");
                    if(intEmpresa==1){
                        strNombreEmpresa="Tuval";
                    }else if(intEmpresa==2){
                        strNombreEmpresa="Castek";
                    }else if(intEmpresa==4){
                        strNombreEmpresa="Dimulti";
                    }
                    //modificar los datos para volver a enviar el pulso
                    if(rs.getString("st_autfacele")!=null && (rs.getString("st_autfacele").equals("E") || rs.getString("st_autfacele").equals("X"))){                    
                        strActRete="update tbm_cabpag set st_autfacele='S' where co_emp="+rs.getInt("co_emp")+" and co_loc="+rs.getInt("co_loc")+" and co_tipdoc="+rs.getInt("co_tipdoc")+" and co_doc="+rs.getInt("co_doc");                            
                        stmUpd=cnx.createStatement();
                        intRow=stmUpd.executeUpdate(strActRete);
                        if(intRow!=1){
                            contador=4; //Para salir del bucle 
                            strMensajesErrorCorreo+="ERROR DE ACTUALIZACION DE ESTADO DE RETENCIONES";
                            cnx.rollback();// NO GUARDAR
                            booExisRetNoAut=false;// NO ESPERAR A NADA SINO SALIR 
                            break; 
                        }else if(intRow==1){
                            cnx.commit();
                            listar_Mover_Archivos_NoAutorizados(strArchivoParcial,strNombreEmpresa);                                                
                            booExisRetNoAut=true;                            
                        }
                    }
                    
                }
                if(booExisRetNoAut){                    
                    //enviarPulsoAutorizarRetenciones();
                    //Thread.sleep(60000*2);// 2 minutoa hora de espera
                    //Thread.sleep(900000);// 1/4 hora de espera
                    Thread.sleep(1800000);// 1/2 hora de espera
                    //Thread.sleep(900000);// 1/4 hora de espera (15 minutos)
                    booExisRetNoAut=false;
                }
                contador++;
            }
            //Se consulta por ultima vez para ver cuantos quedaron pendientes.
            rs=stm.executeQuery(strQry);
            while(rs.next()){
                strMensajesErrorCorreo+=rs.getInt("co_emp")+"-"+rs.getInt("co_loc")+"-"+rs.getInt("co_tipdoc")+"-"+rs.getInt("co_doc")+"\n";
                booExisRetNoAut=true;
            }
            //Se envia Correo.
            strCorreosUsers="sistemas5@tuvalsa.com";            
            objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Retenciones que no estan autorizadas - ", strMensajesErrorCorreo);
            System.out.println("Fin Proceso verifcacion retenciones");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                rs.close();
                stm.close();
                rs=null;
                stm=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return booExisRetNoAut;
    }    

    public void ejecutar2(){
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        String strCorreosUsers="",strMensajesErrorCorreo="";        
        strCorreosUsers="sistemas5@tuvalsa.com;chmateo@hotmail.com";     
        objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Retenciones Recibidas Terminado", "PROCESO RETENCIONES RECIBIDAS TERMINADO");
    
    }
    
    public void ejecutar(){
        boolean booRecFac=false,booEmiRel=false,booRetRec=false,booCobMas=false, booPagMas=false, booExtRetNoAut=false;
        ZafCorEle objCorEle = new ZafCorEle(objParSis);
        String strCorreosUsers="",strMensajesErrorCorreo="";         
        try{
            strCorreosUsers="sistemas5@tuvalsa.com;chmateo@hotmail.com";     
            recFactura=new RecepFacProv(objParSis,cnx);
            booRecFac=recFactura.procesar();
                   
            if(booRecFac){
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Recepcion de Facturas Terminado", "PROCESO RECEPCION FACTURAS TERMINADO");                
                emiRetRel=new EmiRetComRel(objParSis,cnx);
                booEmiRel=emiRetRel.procesar();
            }
            if(booEmiRel){
               objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Emision  de Retenciones Terminado", "PROCESO EMISION RETENCIONES TERMINADO");                                
               enviarPulsoClaveAccesoRetenciones(); //Envia Pulso al servicio 11
               //Thread.sleep(7200000);//Espera 2 horas para que se autorizen
               //Thread.sleep(1800000);//Espera 30 minutos para que se autorizen
               Thread.sleep(3600000);//Espera 1 hora para que se autorizen
               
            /* El servicio 12 a partir de cierta hora de la noche se despierta
            * y toma los archivos que esten en las carpetas pendientes de autorizar para
            * comenzar a realizar los envios al SRI de las retenciones.*/

               
               booExtRetNoAut=verificarRetencionesNoAutorizadas();
               if(!booExtRetNoAut){//No existen retenciones no autorizadas.*/
                retRecRel=new RetRecComRel(objParSis,cnx);        
                booRetRec=retRecRel.procesar();
               }
            }
            if(booRetRec){
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Retenciones Recibidas Terminado", "PROCESO RETENCIONES RECIBIDAS TERMINADO");                                            
                cobMasRel=new CobMasComRel(objParSis,cnx);        
                booCobMas=cobMasRel.procesar();    
            }
            if(booCobMas){
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Cobros Masivos Terminado", "PROCESO COBROS MASIVO TERMINADO");            
               pagMasRel=new PagMasComRel(objParSis,cnx);            
               booPagMas=pagMasRel.procesar();    
            }
            if(booPagMas){
                objCorEle.enviarCorreoMasivo(strCorreosUsers, "PROCESO COMPRA VENTAS - Pagos Masivos Terminado", "PROCESO PAGOS MASIVO TERMINADO");            
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try {
                cnx.close();
            } catch (SQLException ex) {
                Logger.getLogger(CobMasComRel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * Metodo que mueve los archivos de retenciones pendientes de autorizar 
     * de la carpeta no autorizados (Con_NoAut) a la carpeta pendientes autorizar (Con_PenAut)
     * @param strArchivoSinAutorizar
     * @param strEmpresa 
     */
    public void listar_Mover_Archivos_NoAutorizados(String strArchivoSinAutorizar, String strEmpresa){
        String strRutXmlPenAut = "/Zafiro/Digitales/Contabilidad/ZafCon05/"+strEmpresa+"/Con_PenAut";        
        String strRutXmlNoAut  = "/Zafiro/Digitales/Contabilidad/ZafCon05/"+strEmpresa+"/Con_NoAut";        
         File carpeta = new File(strRutXmlNoAut);

         for (File archivo:carpeta.listFiles())
         {  
             System.out.println("Archivo: "+archivo.getName());
            if(archivo.getName().matches(strArchivoSinAutorizar+".*")){                
                //Archivo -Ruta Original  
                File dataInputFile = new File(strRutXmlNoAut+"/"+archivo.getName()); 
                //Nueva Ruta
                File fileSendPath = new File(strRutXmlPenAut, dataInputFile.getName()); 
                 System.out.println("Moviendo archivo "+archivo.getName());
                //Mover archivo. 
                dataInputFile.renameTo(fileSendPath);             
            }
             
         }        
    
    }    
    
    
    private String obtenerFecUltDiaMes(){
        int intUltDia,intMes,intAnio, intMesAnterior;
        String strFechUltDia="";
        Calendar calFec=Calendar.getInstance();  
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intMes=calFec.get(Calendar.MONTH);       
        intAnio=calFec.get(Calendar.YEAR);
        calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        /*if(intMesAnterior==0){ // si es enero es el año anterior            
            intAnio--;
        }*/
        intMesAnterior++;    
        strFechUltDia="'"+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia+"'";
        return strFechUltDia;
    }    
    
    
    
//    private int getUltimoDiaMes(int anio, int mes)
//    {
//        java.util.Calendar cal=java.util.Calendar.getInstance();
//        cal.set(java.util.Calendar.YEAR, anio);
//        cal.set(java.util.Calendar.MONTH, mes);
//        cal.set(java.util.Calendar.DATE, 1);
//        return cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
//    }    
    
    
    /*public void ejecutarMock(){
        Calendar fechaCal=Calendar.getInstance();
        //verificarRetencionesNoAutorizadas();
        //verificar();
        verificar2();
        System.out.println("Siendo las : "+fechaCal.getTime()+" se terminó el proceso");
    }*/    
    
    /*public void verificar(){
        Calendar calFecAct=Calendar.getInstance();
        calFecAct.add(Calendar.MONTH, 1);
        calFecAct.set(calFecAct.get(Calendar.YEAR),calFecAct.get(Calendar.MONTH), 1,00,00,0);
        int intDia=calFecAct.get(Calendar.DATE);
        int intMes=0;
        int intAnio=0;
        int intUltDiaMes=0;
        System.out.println("Fecha dispuesta: "+ calFecAct.getTime());
        
        if (intDia>=1 && intDia<=31){            
            intMes=calFecAct.get(Calendar.MONTH);
            intMes=intMes+1; // porque Enero =0
            if (intMes==1){
                intMes=12;
                intAnio=calFecAct.get(Calendar.YEAR)-1;
            }else {
                intMes=intMes-1;
                intAnio=calFecAct.get(Calendar.YEAR);
            }
        }
        
        Formatter fmtMes = new Formatter();
        fmtMes = new Formatter();
        intUltDiaMes=getUltimoDiaMes(intAnio, intMes-1);

        String strMes=fmtMes.format("%02d",intMes).toString();    
        System.out.println("Fecha: "+intAnio+"-"+strMes+"-27");
        System.out.println("Fecha fin de mes: "+intAnio+"-"+strMes+"-"+intUltDiaMes);
    
    }*/
    
    
    /*public void verificar2(){
        int intMesAnterior,intUltDia, intAnio;
        Calendar calFec=Calendar.getInstance(); 
        /*Suponer estando en sigte mes*/
        //calFec.add(Calendar.MONTH, 1);
        //calFec.set(calFec.get(Calendar.YEAR),calFec.get(Calendar.MONTH), 1,00,00,0);
        /*Suponer estando en sigte mes*/
        /*SUPONER MES DIC 5 2018*/
       /* calFec.set(2019, 0, 5);
        System.out.println("Fecha: "+calFec.getTime());        
        /*SUPONER MES NOV 5 2018*/
      /*  calFec.add(Calendar.MONTH, -1);
        intMesAnterior=calFec.get(Calendar.MONTH);
        intUltDia=calFec.getActualMaximum(Calendar.DAY_OF_MONTH);
        intAnio=calFec.get(Calendar.YEAR);
        intMesAnterior++;//ya que calendar cuenta los meses desde 0 siendo enero 0; diciembre es 11 
        System.out.println("Fecha inicio mes: "+intAnio+"-"+String.format("%02d",intMesAnterior)+"-27"); 
        System.out.println("Fecha Fin de mes: "+intAnio+"-"+String.format("%02d",intMesAnterior)+"-"+intUltDia); 
    
    }*/
    
/*    public void ejecutarPruebaRetRec(){
        retRecRel=new RetRecComRel(cnx);        
        boolean booRetRec=retRecRel.procesar();
    }*/    



    
}
