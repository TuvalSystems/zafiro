/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.  ne_numguides
 */
       
package impguia;   
  
import Librerias.ZafClassImpGuia.ZafClassEmvMesImp;
import Librerias.ZafClassImpGuia.ZafClasImpGuiImp;
import Librerias.ZafClassImpGuia.ZafClassImpGuia_01;
import Librerias.ZafParSis.ZafParSis;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
   

/**
 *  
 * @author ilino
 */
public class ServicioImpGuiaImportaciones {

 private ZafParSis objParSis;
 private ZafThreadarrancap objThrArr;

 ZafClassImpGuia_01 objEnvMail;
 ZafClasImpGuiImp objImpGuiaImp;
  ZafClassEmvMesImp objImpMes;
 
 String strNomArchHis="";
 String strNomImpBodImp_ordDesTuv="";
 String strNomImpBodImp_guiRemTuv="";
 String strNomImpBodImp_ordDesCasUio="";
 String strNomImpBodImp_guiRemCasUio="";
 String strNomImpBodImp_ordDesDim="";
 String strNomImpBodImp_guiRemDim="";
 //String strCorEleTo="sistemas9@tuvalsa.com"; //Rose
 //String strCorEleTo="gigi_2sweet4u@hotmail.com";  //sistemas1@tuvalsa.com";
 String strCorEleTo="sistemas3@tuvalsa.com";  
 String strImpRptBod2="N";
 String strNomImpFac="";
 String strRutaRpt1="";
 String strRutaRpt3="";//correo
 String strRutaRpt6="";//orden despacho tuval
 String strRutaRpt7="";//guia remision tuval
 String strRutaRpt8="";//orden despacho castek quito
 String strRutaRpt9="";//guia remision castek quito
 String strRutaRpt10="";//orden despacho dimulti
 String strRutaRpt11="";//guia remision dimulti
 String strImpRptBod1="";
 String StrMensRec="";
 String strImpRptFac="";
 String strNomImpBod2="";
  
 StringTokenizer stbTokens=null;

 int intPuerto=0;
 int intEstMens=0;
 int intCodBodGrp=0;
 int intCodEmpOrdDes_GuiRem=0;
 int intCodLocOrdDes_GuiRem=0;
 //int intCodEmpTrsImp= 0;
 //int intCodLocTrsImp= 0;
 int intCodTipDoc= 0;
 int intCodDoc=0;

 
 boolean blnEnvNumODBod=true;
 //String strIpMaqBod="172.16.1.26"; // ESTA ES LA IP DE LA MAQUINA DE BODEGA DE TUVAL EN EL CUAL ENVIA EL NUMERO DE O.D. ULTIMO GENERADO EN EL SISTEMAS
 String strIpMaqBod="172.16.1.8"; // ESTA ES LA IP DE LA MAQUINA DE BODEGA DE TUVAL EN EL CUAL ENVIA EL NUMERO DE O.D. ULTIMO GENERADO EN EL SISTEMAS

 String strTipMovFG="";

 //int intPuertoBod=6006;
 
 
 ServerSocket puerto=null;
    
    public ServicioImpGuiaImportaciones(){
    objParSis=new ZafParSis("importaciones");

    objEnvMail = new ZafClassImpGuia_01(strCorEleTo);

     cargarDrv_F1(objParSis.getDriverConexion());
 
     intPuerto    = objParSis.getPuertoCom();
     intCodBodGrp = objParSis.getCodigoBodegaGrp();

     intCodEmpOrdDes_GuiRem = objParSis.getCodigoEmpresaNumGuia();
     intCodLocOrdDes_GuiRem = objParSis.getCodigoLocalNumGuia();

     strNomImpBod2= objParSis.getNombreImpresoraBodega2();

     strNomImpBodImp_ordDesTuv=objParSis.getNombreImpresoraOrdDespachoBodImportacion_Tuval();
     strNomImpBodImp_guiRemTuv=objParSis.getNombreImpresoraGuiaRemisionBodImportacion_Tuval();
     strNomImpBodImp_ordDesCasUio=objParSis.getNombreImpresoraOrdDespachoBodImportacion_CastekUio();
     strNomImpBodImp_guiRemCasUio=objParSis.getNombreImpresoraGuiaRemisionBodImportacion_CastekUio();
     strNomImpBodImp_ordDesDim=objParSis.getNombreImpresoraOrdDespachoBodImportacion_Dimulti();
     strNomImpBodImp_guiRemDim=objParSis.getNombreImpresoraGuiaRemisionBodImportacion_Dimulti();


     strImpRptBod1 = "S";//Orden de Despacho
     strImpRptBod2 = objParSis.getImpBodega2();//Guias de Remisión
     strNomImpFac= objParSis.getNombreImpresoraFactura();
     strRutaRpt1= objParSis.getRutaReporte1();
     strRutaRpt3= objParSis.getRutaReporte3();
     strRutaRpt6= objParSis.getRutaReporteOrdDesTuv();
     strRutaRpt7= objParSis.getRutaReporteGuiRemTuv();
     strRutaRpt8= objParSis.getRutaReporteOrdDesCasUio();
     strRutaRpt9= objParSis.getRutaReporteGuiRemCasUio();
     strRutaRpt10= objParSis.getRutaReporteOrdDesDim();
     strRutaRpt11= objParSis.getRutaReporteGuiRemDim();
     //strRutaRpt6= objParSis.getRutaReporte6();
     strImpRptFac="S";

     _getCorEleToSistemas();
     iniciaServidorImpBod();

     //para bodegas California, Quito, Via Daule, Santo Domingo, Manta
     objImpGuiaImp = new ZafClasImpGuiImp(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), intCodBodGrp , strCorEleTo
             , strImpRptBod2,strRutaRpt6, strRutaRpt7, strRutaRpt8, strRutaRpt9, strRutaRpt10, strRutaRpt11
             , strNomArchHis
             , strNomImpBodImp_ordDesTuv, strNomImpBodImp_guiRemTuv,  strNomImpBodImp_ordDesCasUio, strNomImpBodImp_guiRemCasUio, strNomImpBodImp_ordDesDim, strNomImpBodImp_guiRemDim
             , strImpRptBod1, blnEnvNumODBod, strIpMaqBod, intPuerto
             , objParSis.getCodigoEmpresaTuval_Importaciones(), objParSis.getCodigoLocalTuval_Importaciones()
             , objParSis.getCodigoEmpresaCastekUio_Importaciones(), objParSis.getCodigoLocalCastekUio_Importaciones()
             , objParSis.getCodigoEmpresaDimulti_Importaciones(), objParSis.getCodigoLocalDimulti_Importaciones()
             );
    }


          
private void iniciaServidorImpBod(){
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
                    StrMensRec = dis.readUTF();
                    if(StrMensRec.equals("")){
                        iniciarProceso();
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
                    objEnvMail.enviarCorreo(" "+e.toString() );
                }
            }
        }
    }

    public String getDirectorioSistema(){
        String strRes="";
        try{
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null){
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e){
        strRes=null;
        objEnvMail.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }


    private String getObtenerNomMes(int intMes){
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

               
/*************************************************************************************/



/**
 * Función que permite obtener el correo electronico al que se va a enviar el email.
 * @return true: Si se tiene configurado alguna dirección para importaciones
 * <BR> false: Caso contrario.
 */
    private boolean _getCorEleToSistemas(){
        boolean blnRes=false;
        Connection  conn;
        Statement stm;
        ResultSet rst;
        String strSql="";
        try{
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos() );
            if(conn!=null ){
                stm=conn.createStatement();
                strSql="SELECT tx_coreleerr FROM tbm_sercliserloc WHERE co_emp="+objParSis.getCodigoEmpresaNumGuia()+"  AND co_loc="+objParSis.getCodigoLocalNumGuia()+" ";
                strSql+=" AND co_ser=3";
                rst=stm.executeQuery(strSql);
                if(rst.next()){
                    strCorEleTo=rst.getString("tx_coreleerr");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                conn.close();
                conn=null;
            }
        }
        catch(SQLException e){
            blnRes=false;
        }
        catch(Exception Evt){
            blnRes=false;
        }
        return blnRes;
    }


private boolean iniciarProceso(){
    boolean blnRes=false;
    String strMes="";
    try{
        java.util.Date objFec = new java.util.Date();
        Calendar fecha = Calendar.getInstance();
        String strDirSis= getDirectorioSistema();
        strMes = getObtenerNomMes(objFec.getMonth()+1);


        //para bodegas  : California, Via Daule, etc
        strNomArchHis=strDirSis+"/logs/Servicios/ZafSer01/importaciones/"+String.valueOf(fecha.get(Calendar.DAY_OF_MONTH))+"_"+strMes+"_"+String.valueOf((objFec.getYear()+1900))+".txt";
        objImpGuiaImp.setNomAch(strNomArchHis);
        almacenarArchHis("==================================================================");

        almacenarArchHis("Procesando Transferencia.....");
        objImpGuiaImp.generaTransGuiRem();

        almacenarArchHis("Creacion e impresion de Guías finales(Importaciones)....");
        objImpGuiaImp.generaGuiRemFin();


    }
    catch(Exception Evt){
        blnRes=false;
        System.out.println(""+Evt );
        objEnvMail.enviarCorreo(" "+Evt.toString() );
    }
    return blnRes;
}

/**
 *
 * @param conn
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return
 */

//PREGUNTAR SI SE GENERA COMPRA VENTA, DONDE SE IMPRIME LA FACTURA, del ANALISIS PARECE QUE ES EL MISMO ESQUEMA DE LOS OTROS PROCESOS, SE GENERAN INTERNAMENTE Y LAS IMPRIME PATRICIA LLONGO
    public boolean impresionFactura(Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        String DIRECCION_REPORTE_FACTURA="";
        String strDirSis="";
        try{
            if(conn!=null){
                strDirSis= getDirectorioSistema();
                strRutaRpt1= objParSis.getRutaReporte();
                DIRECCION_REPORTE_FACTURA=strDirSis+strRutaRpt1;
                Map parameters = new HashMap();
                parameters.put("codEmp", new Integer(intCodEmp) );
                parameters.put("codLoc", new Integer(intCodLoc) );
                parameters.put("codTipDoc", new Integer( intCodTipDoc ) );
                parameters.put("codDoc", new Integer( intCodDoc ) );
                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_FACTURA, parameters,  conn);
                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpFac , null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
            }
        }
        catch (JRException e){
            almacenarArchHis(" Error el imprimir factura.."+e.toString() );
        }
        return true;
    }

/***********************************************************************************/

/***********************************************************************************/

/**
 * Almacena un archivo de txt el proceso a ejecutarse.
 * @param strMenArchHis
 */
    private void almacenarArchHis(String strMenArchHis){
        try {
            Calendar fecha = Calendar.getInstance();
            int intHora = fecha.getTime().getHours();
            int intMin = fecha.getTime().getMinutes();
            int intSec = fecha.getTime().getSeconds();
            String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
            BufferedWriter bw = new BufferedWriter(new FileWriter(strNomArchHis, true));
            PrintWriter salida = new PrintWriter(bw);
            salida.println( strHora+" "+strMenArchHis );
            salida.close();
        }
        catch(IOException Ext ){
            System.out.println(""+Ext );
        }
    }

/**
 * Carga driver de conceccion a base.
 * @param driver
 * @return
 */
    private boolean cargarDrv_F1(String driver){
        boolean blnRes=true;
        try{
            Class.forName(driver);
        }
        catch (ClassNotFoundException e){
            blnRes=false;
            System.out.println(""+ e.toString());
        }
        return blnRes;
    }




    
}