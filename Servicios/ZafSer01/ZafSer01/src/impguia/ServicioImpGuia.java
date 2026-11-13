/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impguia;

import Librerias.ZafClassImpGuia.ZafImpZafReg;
import Librerias.ZafParSis.ZafParSis;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.Calendar;

/**
 *
 * @author usuario
 */
public class ServicioImpGuia {
    
    ServerSocket puerto=null;
    private ZafThreadarrancap objThrArr;
    ZafImpZafReg objImpGui;
    private Connection con1;
    ZafParSis objParSis;
    String strNomArch="", strNomArchGuiNoImp="";
    private String version="0.3";
    
    
    
public ServicioImpGuia(){
    crearArchivoLog();//Crear archivo de Log impresiones.
    crearArchivoLogGuiasNoImpresas();    
    iniciaServidorImpBod(6000);
}
    
    private void iniciaServidorImpBod(int intPuerto){
        try{
       
            puerto= new ServerSocket(intPuerto);
            objThrArr= new ZafThreadarrancap();
            objThrArr.start();
        } catch (IOException e) { System.out.println("error  ");   e.printStackTrace();  }
    }
 
    private class ZafThreadarrancap extends Thread{
        public ZafThreadarrancap(){
        }

        @Override
        public void run(){
            while(true){
                try{
                    Socket s1= puerto.accept();
                    DataInputStream dis = new DataInputStream(s1.getInputStream());
                    String StrMensRec = dis.readUTF();
                    if(!StrMensRec.equals("")){
                        String[] strSplt=StrMensRec.split("-");//4-2-231-235-5
                        //iniciarProceso();
                                              
                        if(strSplt[0].equals("imprimeGuiaTerminalL")){// Imprime Guia Remision Terminales L
                            objParSis=new ZafParSis("tuval");
                            imprimirRepGuiaTerL(strSplt);//imprimeODLocal-1-10-102-766-15
                        }else if(strSplt[0].equals("imprimeODLocal")){ // Imprime OD locales
                            objParSis=new ZafParSis("tuval");
                            imprimirRepODLoc(strSplt);
                        }else if(strSplt[0].equals("imprimeGuiaRemisionConfirmacion")){ // Imprime Guia Remision por confirmacion
                            objParSis=new ZafParSis("tuval");
                            imprimirRepGuia(strSplt);
                        }else if( strSplt[0].equals("imprimeODRemotaComun")){
                            objParSis=new ZafParSis("tuval");
                            imprimirRepODRem(strSplt);
                        }else if(strSplt[0].equals("imprimeODInmaconsa")){
                            objParSis=new ZafParSis("inmaconsa");
                            imprimirRepODInm(strSplt);
                        }else if(strSplt[0].equals("imprimeODImportaciones")){
                            objParSis=new ZafParSis("inmaconsa");
                            imprimirRepODImp(strSplt);
                        }else if(strSplt[0].equals("imprimeDevVen")){
                            objParSis=new ZafParSis("tuval");
                            imprimirDevVen(strSplt);
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
                    System.out.println(" "+e.toString() );
                    //objEnvMail.enviarCorreo(" "+e.toString() );
                }
            }
        }
    }
    
    
    /**
     * Metodo que imprime las Guias Remision por confirmacion.
     * @param strGuia String[] donde se encuentran los parametros de la guia a imprimir.
     */  
    private void imprimirRepGuia(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            objImpGui.setStrNomArch(strNomArch);
            objImpGui.setStrNomArchGuiNoImpr(strNomArchGuiNoImp);
            objImpGui.imprimirGuiRemCliNotRet_autSRI(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]), Integer.parseInt(strGuia[5]));
            con1.close();
            con1=null;            
        }catch(Exception ex){
            ex.printStackTrace();        
        }
    }
    
    /**
     * Metodo que imprime las Guias Remision por terminales L
     * @param strGuia datos de la impresion
     */
    private void imprimirRepGuiaTerL(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            objImpGui.setStrNomArch(strNomArch);
            objImpGui.imprimirGuiRemTerL(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]), strGuia[5], strGuia[6], Integer.parseInt(strGuia[7]));
            con1.close();
            con1=null;            
        }catch(Exception ex){
            ex.printStackTrace();        
        }    
    }    
    
    /**
     * Metodo que imprime las OD LOCALES.
     * @param strGuia String[] donde se encuentran los parametros de la OD a imprimir.
     */
    private void imprimirRepODLoc(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            objImpGui.setStrNomArch(strNomArch);
            //objImpGui.impresionGuiaRemAutBod2ODLoc(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]), Integer.parseInt(strGuia[5]),Integer.parseInt(strGuia[6]));
            objImpGui.impresionGuiaRemAutBod2ODLoc(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]), Integer.parseInt(strGuia[5]));            
            con1.close();
            con1=null;
            
        }catch(Exception ex){
            ex.printStackTrace();        
        }    
    }
    
    
    /**
     * Metodo que imprime las OD remotas.
     * @param strGuia datos de las impresiones.
     */
    private void imprimirRepODRem(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            //objImpGui.impresionGuiaRemAutBod2ODLoc(con1, Integer.parseInt(strGuia[0]), Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]));
            objImpGui.setStrNomArch(strNomArch);
            //objImpGui.impresionGuiaRemAutBod2ODRem(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]),Integer.parseInt(strGuia[5]), strGuia[6],strGuia[7]);
            objImpGui.impresionGuiaRemAutBod2ODRem(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]), strGuia[5],strGuia[6]);            
            con1.close();
            con1=null;
            
        }catch(Exception ex){
            ex.printStackTrace();        
        }    
    }    
    
    
    /**
     * Imprime las OD remotas de inmaconsa.
     * @param strGuia datos de impresion
     */
    private void imprimirRepODInm(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            //objImpGui.imprimirGuiRemCliNotRet_autSRI(con1, Integer.parseInt(strGuia[0]), Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]));
            objImpGui.setStrNomArch(strNomArch);
            //objImpGui.impresionOrdDesTransInm(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]),Integer.parseInt(strGuia[5]),Integer.parseInt(strGuia[6]));
            objImpGui.impresionOrdDesTransInm(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]),Integer.parseInt(strGuia[5]));            
            con1.close();
            con1=null;
        }catch(Exception ex){
            ex.printStackTrace();        
        }
    }
    
    /**
     * Metodo que imprime las od remotas de importaciones
     * @param strGuia datos para la impresion.
     */
    private void imprimirRepODImp(String[] strGuia){
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            objImpGui.setStrNomArch(strNomArch);
            objImpGui.impresionOrdDesImp(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]));
            con1.close();
            con1=null;
        }catch(Exception ex){
            ex.printStackTrace();        
        }
    }    
    
    
    private void crearArchivoLog()
    {
        java.util.Date objFec = new java.util.Date();
        Calendar fecha = Calendar.getInstance();
        String strMes = getObtenerNomMes(objFec.getMonth()+1);
        String strDirSis= getDirectorioSistema();
        String strNomArchHis=strDirSis+"/logs/Servicios/ZafSer01/impresiones/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";
        System.out.println("NOMBRE ARCHIVO "+strNomArchHis);
        try{
            File fLogImp= new File(strNomArchHis);
            if(!fLogImp.exists()){
                fLogImp.createNewFile();
            }
            strNomArch=strNomArchHis;            
        }catch(IOException iox){
            iox.printStackTrace();
        }
    }
    
    private void crearArchivoLogGuiasNoImpresas(){
        
        java.util.Date objFec = new java.util.Date();
        Calendar fecha = Calendar.getInstance();
        String strMes = getObtenerNomMes(objFec.getMonth()+1);
        String strDirSis= getDirectorioSistema();
        String strNomArchGuiNoImpr=strDirSis+"/logs/Servicios/ZafSer01/impresiones/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+"_guiassinimprimir.txt";
        System.out.println("NOMBRE ARCHIVO "+strNomArchGuiNoImpr);
        try{
            File fLogImp= new File(strNomArchGuiNoImpr);
            if(!fLogImp.exists()){
                fLogImp.createNewFile();
            }
            strNomArchGuiNoImp=strNomArchGuiNoImpr;            
            
        }catch(IOException iox){
            iox.printStackTrace();
        }
        
    }


    public String getDirectorioSistema(){
        String strRes="";
        try{
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null){
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e){
        strRes=null;
        }
        return strRes;
    }
    
    private String getObtenerNomMes(int intMes)
    {
        String strMes="";
        if(intMes==1)      strMes="Ene";
        else if(intMes==2) strMes="Feb";
        else if(intMes==3) strMes="Mar";
        else if(intMes==4) strMes="Abr";
        else if(intMes==5) strMes="May";
        else if(intMes==6) strMes="Jun";
        else if(intMes==7) strMes="Jul";
        else if(intMes==8) strMes="Ago";
        else if(intMes==9) strMes="Sep";
        else if(intMes==10) strMes="Oct";
        else if(intMes==11) strMes="Nov";
        else if(intMes==12) strMes="Dic";

        return strMes;
    }  
    
    /**
     * Metodo que imprime las devoluciones de ventas en las impresoras od
     * @param strGuia datos de las impresiones.
     */
    private void imprimirDevVen(String[] strDevVen)
    {
        try{
            con1=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            objImpGui=new ZafImpZafReg();
            //objImpGui.impresionGuiaRemAutBod2ODLoc(con1, Integer.parseInt(strGuia[0]), Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]));
            objImpGui.setStrNomArch(strNomArch);
            //objImpGui.impresionGuiaRemAutBod2ODRem(con1, Integer.parseInt(strGuia[1]), Integer.parseInt(strGuia[2]), Integer.parseInt(strGuia[3]), Integer.parseInt(strGuia[4]),Integer.parseInt(strGuia[5]), strGuia[6],strGuia[7]);
            objImpGui.impresionDevVen(con1, Integer.parseInt(strDevVen[1]), Integer.parseInt(strDevVen[2]), Integer.parseInt(strDevVen[3]), Integer.parseInt(strDevVen[4]), Integer.parseInt(strDevVen[5]),strDevVen[6]);            
            con1.close();
            con1=null;
            
        }catch(Exception ex){
            ex.printStackTrace();        
        }    
    }

    
}
