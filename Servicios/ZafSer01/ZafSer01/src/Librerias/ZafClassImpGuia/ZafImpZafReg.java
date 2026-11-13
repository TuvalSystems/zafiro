/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafClassImpGuia;

import Librerias.ZafJCE.ZafJCEAlgAES;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author usuario
 */
public class ZafImpZafReg {

    private final static int CODEMPINMTUV=1;    
    private final static int CODLOCINMTUV=10;
    private final static int CODEMPINMCASUIO=2;
    private final static int CODLOCINMCASUIO=5;
    private final static int CODEMPINMDIM=4;
    private final static int CODLOCINMDIM=10;     
    /*INMACONSA*/
    private static final String RUTAREPORTEODINMTUVAL="/Reportes/Compras/ZafCom23/Tuval/Inmaconsa/ZafRptCom23_01.jasper";
    private static final String RUTAREPORTEODINMCASUIO="/Reportes/Compras/ZafCom23/Castek/Inmaconsa/ZafRptCom23_01.jasper";
    private static final String RUTAREPORTEODINMDIM="/Reportes/Compras/ZafCom23/Dimulti/Inmaconsa/ZafRptCom23_01.jasper";
    
    private static final String NOMBREIMPRESORAODINMTUVAL="od_inmaconsa";
    private static final String NOMBREIMPRESORAODINMCASUIO="od_inmaconsa";
    private static final String NOMBREIMPRESORAODINMDIM="od_inmaconsa";
    
    private static final String NOMBREIMPRESORAODTUVALTRANS="printOrdDesBodImp";
    private static final String NOMBREIMPRESORAODCASUIOTRANS="printOrdDesBodImp";
    private static final String NOMBREIMPRESORAODDIMTRANS="printOrdDesBodImp";
    
    
    private String version="0.7";
    private String strNomArch="";
    private String strNomArchGuiNoImpr="";    
    

    
    /**
     * Metodo que obtiene la bodega de grupo por empresa - bodega.
     * @param con objeto conexion
     * @param intEmp
     * @param intBod
     * @return 
     */
    public int obtenerBodGru(Connection con,int intEmp, int intBod){
        int intBodGrp=0;
        Statement stmQry=null;
        ResultSet rsDat=null;        
        String strSql=" select co_bodgrp "+
                      " from tbr_bodempbodgrp where co_emp="+intEmp+ " and co_bod="+intBod;
        try{
            stmQry=con.createStatement();
            rsDat=stmQry.executeQuery(strSql);
            if(rsDat.next()){
                intBodGrp=rsDat.getInt("co_bodgrp");
            }            
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                rsDat.close();
                rsDat=null;
                stmQry.close();
                stmQry=null;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return intBodGrp;
    }  
    
    
    private String getDirectorioSistemaGuiRem()
        {
            String strRes="";
            try
            {
                URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
                if (urlArc!=null)
                {
                    //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                    //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                    strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                    strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                    //System.out.println("jklj");
                    if (strRes.indexOf("file:")!=-1){
                        strRes=strRes.substring(5);
                    }

                }
            }
            catch (Exception e)
            {
                strRes=null;
                //objEnvMail.enviarCorreo(" "+e.toString() );
            }
            return strRes;
        }

    private String getDirectorioSistemaImpOrd()
    {
        String strRes="";
        try
        {
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Servicios"));
                if (strRes.indexOf("file:")!=-1){
                    strRes=strRes.substring(5);
                }
                
            }
        }
        catch (Exception e)
        {
            strRes=null;
            //objEnvMail.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }



    public String[] obtenerRptImpGuiRem(int intCodBodGrp){
        String strRtaRptOD="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_califormia";  
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_dimulti";
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_quito";    
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasperXXX";
            strNomImp="PrintBodega2XXX";   
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_stodgo";      
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_inmaconsa";   
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_cuenca";   
            strEmpNumGui="2";
            strLocNumGui="10";
            
        
        }else if(intCodBodGrp==32){//DURAN
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_duran";   
            strEmpNumGui="4";
            strLocNumGui="12";
            
        
        }else if(intCodBodGrp==36){//DURAN
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_quitosur";   
            strEmpNumGui="2";
            strLocNumGui="15";
        }
        strArrRet[0]=strRtaRptOD;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }
    
   /**
    * Metodo que imprime la guia de remision desde la confirmacion.
    * @param con objeto conexion.
    * @param intCodEmpGui codigo de empresa de la guia.
    * @param intCodLocGui codigo de local de la guia.
    * @param intCodTipGui codigo del tipo documento de la guia.
    * @param intCodDocGui codigo del documento de la guia.
    * @param intCodBod codigo de la bodega de la guia.
    * @return boolean
    */
    public boolean imprimirGuiRemCliNotRet_autSRI(Connection con, int intCodEmpGui, int intCodLocGui, int intCodTipGui, int intCodDocGui, int intCodBod){
        boolean blnRes=true;
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
        String strRepUtiEmp="";
        String strNomPrn=""; 
        String strSQL="";
        String strSQLRep="", strSQLSubRep="";
            
        Statement stmGuiRemCliNotRetAutSri;
        ResultSet rstGuiRemCliNotRetAutSri;
        
        
        Statement stmGuiRemCliNotRetAutSri_estImp;
        
        String[] strRutaRpt2;
        String strNomImpBod2;
        
        
        //ZafImpGuiRem imp=new ZafImpGuiRem();
        
          String strRutSubRpt="";
          int cobodgru;
        
        
        System.out.println("Iniciando servicio 1");
        try{
            ZafJCEAlgAES objJCEAlgAES=new ZafJCEAlgAES();
            
            if( (con!=null) ){
                
                //ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
                strDirSis=getDirectorioSistemaGuiRem();
                //cobodgru=objGuiRem.obtenerBodGru(con, intCodEmpGui, intCodBod);
                //ZafReglas.ZafGenGuiRem objGenGuiRem=new ZafReglas.ZafGenGuiRem();
                //strRutaRpt2=objGenGuiRem.obtenerRptImpOD(cobodgru);
                strRutaRpt2=obtenerRptImpGuiRem(intCodBod);
                
                DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
                strNomImpBod2=strRutaRpt2[1];
      
                strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_08.jasper"));
                
                
                //strDirSis=getDirectorioSistemaGuiRem();        
                //DIRECCION_REPORTE_GUIA=strDirSis+strRtaRpt;
                //System.out.println("imprimirGuiRemCliNotRet-Ruta Reporte GR ->  "+DIRECCION_REPORTE_GUIA);
                
                //stmGuiRemCliNotRetAutSri=con.createStatement();
                stmGuiRemCliNotRetAutSri=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                //Query que retorna todas las GR pendientes de imprimir y que han sido autorizadas por el SRI                
                //co_mnu=2205 es de confirmacion de egreso normal, sin cliente retira
                strSQL="";
                strSQL+="SELECT a.co_mnu, CASE WHEN a.co_mnu=2205 THEN 'N' WHEN a.co_mnu=2915 THEN 'S' END AS st_cliRet,";
                strSQL+=" a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc";
                strSQL+=" FROM tbr_bodEmpBodGrp as a6";
                strSQL+=" inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  )";
                strSQL+=" inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod )";
                strSQL+=" inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem )";
                strSQL+=" WHERE /*( a6.co_empGrp=0 AND a6.co_bodGrp = " + intCodBod + "  )  AND*/";
                strSQL+=" a1.ne_numdoc!=0 and a1.st_reg = 'A' AND a1.st_imp='N'";
                //strSQL+=" and a.st_reg='A' and a.tx_tipCon in ('P','T') ";
                strSQL+=" and a.st_reg='A' ";
                strSQL+=" AND a1.st_autfacele in ('C','A') ";
                //strSQL+=" and  a.co_mnu = 2205 ";
                strSQL+=" and a1.co_emp="+ intCodEmpGui;
                strSQL+=" and a1.co_loc="+ intCodLocGui;
                strSQL+=" and a1.co_tipdoc="+intCodTipGui;
                strSQL+=" and a1.co_doc="+intCodDocGui;
                
                //System.out.println("imprimirGuiRemCliNotRet_autSRI: " + strSQL);
                rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                
                int cnt=0;
                while(!(rstGuiRemCliNotRetAutSri.next())&& cnt<=180){
                    rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                    Thread.sleep(1000);
                    cnt++;
                    System.out.println("cnt "+cnt);
                }
                //System.out.println("contador de tiempo "+cnt);
                
                if(cnt>=180){
                    System.out.println("entra en guias no impresas");
                    almacenarArchGuiNoImp("Guia: "+intCodEmpGui+"-"+intCodLocGui+"-"+intCodTipGui+"-"+intCodDocGui+" Impresora: "+strNomImpBod2);
                }else{
                    rstGuiRemCliNotRetAutSri.beforeFirst();
                
                
                while(rstGuiRemCliNotRetAutSri.next()){
                    
                    //Query del reporte
                    strSQL="";
                    strSQL+="select distinct a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=", a1.tx_numautfacele, a1.fe_autfacele, a1.tx_claaccfacele";
                    strSQL+=" , a1.fe_initra, a1.fe_tertra, a1.fe_ing, trim(a1.tx_obs2) as obs2,";
                    strSQL+=" case when a1.tx_numped is null then '' else a1.tx_numped end as tx_numped,";
                    strSQL+=" emp.tx_nom, tipdoc.tx_descor, a1.fe_ing,a1.st_imp as st_impGuia, a1.tx_datdocoriguirem,a1.co_ptopar, a1.tx_ptopar,";
                    strSQL+=" case when a1.co_ptopar =15 then 'INMACONSA' else '' end as puntopartida,";
                    strSQL+=" emp.tx_nom  as empresa, a1.tx_telclides, a1.tx_rucclides,";
                    strSQL+=" (substring(a1.tx_numserdocori,1,3) || '-' || substring(a1.tx_numserdocori,5,3) || '-' || to_char(a1.ne_numdoc, '000000000')) as tx_numdoc,";
                    strSQL+=" a1.ne_numdoc, a1.fe_doc, a1.tx_nomclides, a1.co_clides,";
                    //strSQL+=" a1.tx_nomven, a1.tx_dirclides, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                    strSQL+= " a1.tx_nomven, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";                    
                    strSQL+=" a1.tx_ciuclides as cuidad, emp.tx_desconesp as contri1, emp.tx_nom  as empresa,";
                    strSQL+=" (select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kgramo,";
                    strSQL+=" (select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kilo,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_idetra ELSE tra.tx_ide END AS tx_idetra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_nomtra ELSE tra.tx_nom || ' ' || tra.tx_ape END AS tx_nomtra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_plavehtra ELSE veh.tx_pla END AS tx_plaveh";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    strSQL+=" , usr.tx_usr,";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    
                     /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                    strSQL+=" CASE WHEN detord.co_tipdocrel IN (1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN ";
                    strSQL+="      'VENTA' ";
                    strSQL+=" ELSE ";
                    strSQL+="      CASE WHEN detord.co_tipDocRel IN (4,46,172,204,234) THEN ";
                    strSQL+="           'REPOSICIÓN' ";
                    strSQL+="       END ";
                    strSQL+=" END as documento ,a1.tx_obs1,";
				
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/                   
					/*CAMBIO POR DIRECCION EN DURAN*/
				
                    strSQL+=" case when boddes.tx_dir is null then ";
                    strSQL+="   a1.tx_dirclides ";
                    strSQL+=" else ";				  
                    strSQL+="   case when bodgrp.co_bodgrp=5 then 'Manta: '||boddes.tx_dir ";
                    strSQL+="      else case when bodgrp.co_bodgrp=3 then 'Quito: '||boddes.tx_dir ";
                    strSQL+="                else case when bodgrp.co_bodgrp=28 then 'Cuenca: '||boddes.tx_dir ";
                    strSQL+="                    else case when bodgrp.co_bodgrp=11 then 'Santo Domingo: '||boddes.tx_dir ";
                    strSQL+="                           else boddes.tx_dir";
                    strSQL+=" end end end end ";
                    strSQL+=" end as tx_dirclides, ";
					
                    /*CAMBIO POR DIRECCION EN DURAN*/
					
					
					
					
					/*CAMBIO POR POFASA AGREGAR NUMDOC FACTURA Y NUMAUT DEL SRI FACTURA*/
					strSQL+=" locser.tx_secdoc|| '-' ||to_char(fac.ne_numdoc, 'fm000000000') as numdocfac, fac.ne_numdoc, fac.tx_claaccfacele as claaccfac 	";				
                                        
                    strSQL+=" FROM tbm_cabguirem as a1";
                    strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (a1.co_forret = a3.co_reg )";
                    strSQL+=" inner JOIN tbm_loc as loc ON ( a1.co_emp = loc.co_emp and  a1.co_loc=loc.co_loc)";
                    strSQL+=" inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a1.co_emp)";
                    strSQL+=" inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = a1.co_emp and tipdoc.co_loc=a1.co_loc and tipdoc.co_tipdoc=a1.co_tipdoc)";
                    strSQL+=" LEFT JOIN tbm_veh as veh on (veh.co_veh = a1.co_veh)";
                    strSQL+=" LEFT JOIN tbm_tra as tra on (tra.co_tra = a1.co_cho)";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    strSQL+=" LEFT JOIN tbm_usr as usr on (usr.co_usr = a1.co_usring)";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                    strSQL+=" INNER JOIN tbr_cabguirem as rel ";
                    strSQL+=" on (rel.co_emp= a1.co_emp and rel.co_loc=a1.co_loc and rel.co_tipdoc=a1.co_tipdoc and rel.co_doc=a1.co_doc) ";
                    strSQL+=" INNER JOIN tbm_cabguirem as orden ";
                    strSQL+=" on (orden.co_emp=rel.co_emp and orden.co_loc=rel.co_locrel and orden.co_tipdoc=rel.co_tipdocrel and orden.co_doc=rel.co_docrel) ";
					
					/*CAMBIO DIRECCCION DE DURAN*/
					strSQL+=" left outer join tbm_bod as boddes ";
					strSQL+=" on (boddes.co_emp=orden.co_emp and boddes.co_bod=orden.co_ptodes) ";
					  
					strSQL+=" left outer join tbr_bodempbodgrp as bodgrp ";
					strSQL+=" on (bodgrp.co_emp=boddes.co_emp and bodgrp.co_bod=boddes.co_bod and bodgrp.co_empgrp=0) ";
					/*CAMBIO DIRECCCION DE DURAN*/

					strSQL+=" INNER JOIN tbm_detguirem as detord ";
					strSQL+=" on (detord.co_emp=orden.co_emp and detord.co_loc=orden.co_loc and detord.co_tipdoc=orden.co_tipdoc and detord.co_doc=orden.co_doc) ";

					/*CAMBIO PARA POFASA NUMDOC DE FACTURA Y NUMERO AUTORIZACION FAC*/
                    strSQL+=" LEFT OUTER JOIN tbm_cabmovinv as fac";
                    strSQL+=" on (fac.co_emp=detord.co_emprel and fac.co_loc=detord.co_locrel and fac.co_tipdoc=detord.co_tipdocrel and fac.co_doc=detord.co_docrel and fac.co_tipdoc=228)";
                    strSQL+=" LEFT OUTER JOIN tbm_loc as locser";
                    strSQL+=" on locser.co_emp=fac.co_emp and locser.co_loc=fac.co_loc";
					/*CAMBIO PARA POFASA NUMDOC DE FACTURA Y NUMERO AUTORIZACION FAC*/
					
					
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
					
                    strSQL+=" WHERE a1.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and a1.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and a1.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and a1.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" AND a1.st_autfacele in ('C','A') AND a1.st_imp='N'";
                    strSQLRep=strSQL;
                    //System.out.println("strSQLRep: " + strSQLRep);

                    //Query del subReporte
                    strSQL="";
                    strSQL+="select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                    strSQL+=" a2.tx_obs1,emp.tx_nom,a2.tx_codalt, inv.tx_codalt2, ";
                    strSQL+=" CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm,";
                    strSQL+=" abs(a2.nd_can) as can,a2.tx_unimed";
                    strSQL+=" FROM tbm_cabguirem as DocCab";
                    strSQL+=" INNER JOIN tbm_detguirem as a2";
                    strSQL+=" ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                    strSQL+=" inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a2.co_emp)";
                    strSQL+=" WHERE DocCab.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and DocCab.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and DocCab.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and DocCab.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" order by a2.co_reg";
                    strSQLSubRep=strSQL;
                    //System.out.println("strSQLSubRep: " + strSQLSubRep);
                    //System.out.println("jok");
                    String strNumImp="";
                    try{
                        strNumImp=rstGuiRemCliNotRetAutSri.getInt("co_tipDoc")+""+rstGuiRemCliNotRetAutSri.getInt("co_doc");
                        //System.out.println("Ruta Reporte GUIREM ->  "+DIRECCION_REPORTE_GUIA);
                        Map mapPar = new HashMap();                
                        mapPar.put("strSQLRep", strSQLRep);
                        mapPar.put("strSQLSubRep", strSQLSubRep);
                        mapPar.put("SUBREPORT_DIR", strDirSis);
                        mapPar.put("imgCodEmp", rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        mapPar.put("imgRut", strDirSis);
                        
                        /*System.out.println("SUBREPORT_DIR: " + strDirSis);
                        System.out.println("imgCodEmp: " + rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        System.out.println("imgRut: " + strDirSis);                        */

                        /*JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
                        JasperViewer.viewReport(reportGuiaRem);

                        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
                        aset.add(MediaSizeName.ISO_A4);
                        
                        if(intCodBod==1)
                           aset.add(new Copies(3));                        
                        aset.add(Sides.ONE_SIDED);*/
                        //*
//////////////                        String fileName   = DIRECCION_REPORTE_GUIA;
//////////////                        String fileNumber = strNumImp;
//////////////                        String printerName = "";//Ej:sistemas_inmaconsa";
//////////////                        String jobName = "PDF Print Job";
//////////////                        //String DireccionExpo = strDireccionNueva;
//////////////                        String DireccionExpo = DIRECCION_REPORTE_GUIA;
//////////////                        printPDF(fileName,fileNumber, printerName, jobName,mapPar,con,DireccionExpo);
                        
                        ////////*****
                        PrintRequestAttributeSet objPriReqAttSet = new HashPrintRequestAttributeSet();
                        //Medidas para que en la hoja la información no salga muy abajo
                        MediaPrintableArea mpa = new MediaPrintableArea(0.1f, 0.1f, 2.4f, 2.4f, Size2DSyntax.INCH);
                        objPriReqAttSet.add(MediaSizeName.ISO_A5);
                        //sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                        objPriReqAttSet.add(mpa);
                        //Orientación Vertical
                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                        objPriReqAttSet.add(new Copies(3));
                        JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar, con);
                        //Número de páginas que tiene el reporte.
                        int intCantPaginas = 0;
                        if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                            intCantPaginas = reportGuiaRem.getPages().size();
                        }
                        ///////////////////////////////////////////tony
                        //Crea físicamente el archivo pdf
                        //Se le colocó de nombre el codigo del tipo de documento y el codigo de documento para que sea multiusuario.
                        JasperExportManager.exportReportToPdfFile(reportGuiaRem, DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        //Instancio el archivo pdf para poder eliminarlo despues de utilizarlo
                        File pdfA4 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                        Rectangle recA5 = new Rectangle(PageSize.A5);
                        float floRecA5 = recA5.getHeight() - 70;
                        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                        docu.setPageSize(recA52);
                        PdfReader reader = new PdfReader(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        //Creo el segundo pdf ya modificado para que sea de tamano A5
                        PdfWriter writer = PdfWriter.getInstance(docu, new FileOutputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf")));
                        File pdfA5 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
                        docu.open();
                        PdfContentByte cb = writer.getDirectContent();
                        //PdfImportedPage page = writer.getImportedPage(reader, 1);
                        //Se reduce la medida escalandolo de a4 a a5
                       PdfImportedPage page = null;
                        //Se reduce la medida escalandolo de a4 a a5
                        float Scale = 0.67f;
                        for (int i = 0; i < intCantPaginas; i++) {
                            if (i>0) {
                                      docu.newPage();
                            }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
                        docu.close();
                        javax.print.PrintService[] service = PrinterJob.lookupPrintServices();
                        DocPrintJob docPrintJob = null;
                        int count = service.length;

                        for (int i = 0; i < count; i++) {
                            if (service[i].getName().equalsIgnoreCase(strNomImpBod2)) {
                                docPrintJob = service[i].createPrintJob();
                                i = count;
                            }
                        }
                        DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
                        FileInputStream psStream = null;
                        psStream = new FileInputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
                        Doc document = new SimpleDoc(psStream, flavor, null);
                        PrinterJob pjob = PrinterJob.getPrinterJob();
                        pjob.defaultPage();
                        pjob.setPrintService(docPrintJob.getPrintService());
                        pjob.setCopies(3);
                        docPrintJob.print(document, objPriReqAttSet);
                        pdfA4.delete();
                        pdfA5.delete();
                        /////////////////////////////tony
                        
                        ///////******
                        //*
                        //System.out.println("Nombre Impresora "+strNomImpBod2);
////////////////////
////////////////////                        PrintRequestAttributeSet objPriReqAttSet = new HashPrintRequestAttributeSet();
////////////////////                        //Medidas para que en la hoja la información no salga muy abajo
////////////////////                        MediaPrintableArea mpa = new MediaPrintableArea(0.1f, 0.1f, 2.4f, 2.4f, Size2DSyntax.INCH);
////////////////////                        objPriReqAttSet.add(MediaSizeName.ISO_A5);
////////////////////                        //sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
////////////////////                        objPriReqAttSet.add(mpa);
////////////////////                        //Orientación Vertical
////////////////////                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
////////////////////                        //Numero de copias tony
////////////////////                        objPriReqAttSet.add(new Copies(1));
////////////////////                        JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar, con);
////////////////////                        //Número de páginas que tiene el reporte.
////////////////////                        int intCantPaginas = 0;
////////////////////                        if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
////////////////////                            intCantPaginas = reportGuiaRem.getPages().size();
////////////////////                        }
////////////////////                       
////////////////////                        ///////////////////////////////////////////tony
////////////////////                        //Crea físicamente el archivo pdf
////////////////////                        //Se le colocó de nombre el codigo del tipo de documento y el codigo de documento para que sea multiusuario.
////////////////////                        JasperExportManager.exportReportToPdfFile(reportGuiaRem, DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
////////////////////                        //Instancio el archivo pdf para poder eliminarlo despues de utilizarlo
////////////////////                        File pdfA4 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
////////////////////                        Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
////////////////////                        Rectangle recA5 = new Rectangle(PageSize.A5);
////////////////////                        float floRecA5 = recA5.getHeight() - 70;
////////////////////                        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
////////////////////                        docu.setPageSize(recA52);
////////////////////                        PdfReader reader = new PdfReader(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
////////////////////                        //Creo el segundo pdf ya modificado para que sea de tamano A5
////////////////////                        PdfWriter writer = PdfWriter.getInstance(docu, new FileOutputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf")));
////////////////////                        File pdfA5 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
////////////////////                        docu.open();
////////////////////                        PdfContentByte cb = writer.getDirectContent();
////////////////////                        PdfImportedPage page = writer.getImportedPage(reader, 1);
////////////////////                        //Se reduce la medida escalandolo de a4 a a5
////////////////////                        float Scale = 0.67f;
////////////////////                        cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
////////////////////                        docu.close();
////////////////////                        javax.print.PrintService[] service = PrinterJob.lookupPrintServices();
////////////////////                        DocPrintJob docPrintJob = null;
////////////////////                        int count = service.length;
////////////////////
////////////////////                        for (int i = 0; i < count; i++) {
////////////////////                            if (service[i].getName().equalsIgnoreCase(strNomImpBod2)) {
////////////////////                                docPrintJob = service[i].createPrintJob();
////////////////////                                i = count;
////////////////////                            }
////////////////////                        }
////////////////////                        DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
////////////////////                        FileInputStream psStream = null;
////////////////////                        psStream = new FileInputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
////////////////////                        Doc document = new SimpleDoc(psStream, flavor, null);
////////////////////                        PrinterJob pjob = PrinterJob.getPrinterJob();
////////////////////                        pjob.defaultPage();
////////////////////                        pjob.setPrintService(docPrintJob.getPrintService());
////////////////////                        //Numero de copias tony
////////////////////                        pjob.setCopies(1);
////////////////////                        docPrintJob.print(document, objPriReqAttSet);
////////////////////                        pdfA4.delete();
////////////////////                        pdfA5.delete();
                        /////////////////////////////tony
//                        PrinterName printerName=new PrinterName( strNomImpBod2 , null); 
//                        PrintServiceAttributeSet printServiceAttributeSet=new HashPrintServiceAttributeSet(); 
//                        printServiceAttributeSet.add(printerName);          
//                        JRPrintServiceExporter objJRPSerExp=new JRPrintServiceExporter(); 
//                        //objJRPSerExp.setParameter(JRExporterParameter.JASPER_PRINT, reportGuiaRem); 
//                        objJRPSerExp.setParameter(_JRPrintServiceExporterParameter.INPUT_FILE_NAME, DIRECCION_REPORTE_GUIA.replace(".jasper", "2.pdf")); 
//                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet); 
//                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet); 
//                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE); 
//                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE); 
//                        objJRPSerExp.exportReport(); 
//                        objPriReqAttSet=null;
                        almacenarArchHis("Guia: "+rstGuiRemCliNotRetAutSri.getInt("co_emp")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_loc")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_tipDoc")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_doc")+" #GUIA: "+rstGuiRemCliNotRetAutSri.getString("ne_numDoc")+" Impresora: "+strNomImpBod2+" Cant. Pg.#: ");//+intCantPaginas);
                       
                        /*PrintService[] pservices=PrinterJob.lookupPrintServices();
                        if (pservices.length>0) {
                            int indice=imp.traeIndice(pservices, strNomImpBod2);
                            DocPrintJob pj = pservices[indice].createPrintJob();
                            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                            //pj.print(doc, aset);
                        } */                        
                        try{
                            //con.setAutoCommit(false);

                            stmGuiRemCliNotRetAutSri_estImp=con.createStatement();
                            strSQL="";
                            strSQL+="UPDATE tbm_cabGuiRem";
                            strSQL+=" SET st_imp='S'";
                            strSQL+=" WHERE co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                            strSQL+=" AND co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                            strSQL+=" AND co_tipDoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                            strSQL+=" AND co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                            strSQL+=";";
                            stmGuiRemCliNotRetAutSri_estImp.executeUpdate(strSQL);
                            //con.commit();

                                stmGuiRemCliNotRetAutSri_estImp.close();
                                stmGuiRemCliNotRetAutSri_estImp=null;
                            }catch(java.sql.SQLException e){
                                blnRes=false;
                                con.rollback();
                            }


                        }
                        catch (Exception e){
                            blnRes=false;
                            //conGuiRemCliNotRetAutSri_estImp.rollback();
                            e.printStackTrace();
                        }       

                    }
                }
                //conGuiRemCliNotRetAutSri.close();
                
                stmGuiRemCliNotRetAutSri.close();
                stmGuiRemCliNotRetAutSri=null;
                rstGuiRemCliNotRetAutSri.close();
                rstGuiRemCliNotRetAutSri=null;
                
                //conGuiRemCliNotRetAutSri_estImp.close();
                
            
            }
        }
        catch (Exception e){ 
            blnRes=false;
            e.printStackTrace();
        }
        return blnRes;
    } 
   
    
    /**
     * Metodo que imprime la guia remision por terminales L.
     * @param con objeto conexion.
     * @param intCodEmpGui codigo de empresa de guia.
     * @param intCodLocGui codigo de local de guia.
     * @param intCodTipGui codigo de tipo de documento.
     * @param intCodDocGui codigo de documento de guia.
     * @param strRtaRpt ruta reporte de guia.
     * @param strNomImp nombre impresora de guia.
     * @param intCodBodGrp codigo de bodega de grupo de guia.
     * @return  boolean.
     */
    public boolean imprimirGuiRemTerL(Connection con, int intCodEmpGui, int intCodLocGui, int intCodTipGui, int intCodDocGui,String strRtaRpt, String strNomImp, int intCodBodGrp){
        boolean blnRes=true;
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
        String strRepUtiEmp="";
        String strNomPrn=""; 
        String strSQL="";
        String strSQLRep="", strSQLSubRep="";
        Connection conGuiRemCliNotRetAutSri;        
        Statement stmGuiRemCliNotRetAutSri;
        ResultSet rstGuiRemCliNotRetAutSri;
        
        Connection conGuiRemCliNotRetAutSri_estImp;
        Statement stmGuiRemCliNotRetAutSri_estImp;
        
        
        try{
            ZafJCEAlgAES objJCEAlgAES=new ZafJCEAlgAES();
            
            if( (con!=null) ){
                //System.out.println("------------imprimirGuiRemCliNotRet_autSRI------------------------");
                strDirSis=getDirectorioSistemaGuiRem();        
                DIRECCION_REPORTE_GUIA=strDirSis+strRtaRpt;
                //System.out.println("imprimirGuiRemCliNotRet-Ruta Reporte GR ->  "+DIRECCION_REPORTE_GUIA);
                
                stmGuiRemCliNotRetAutSri=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                //Query que retorna todas las GR pendientes de imprimir y que han sido autorizadas por el SRI                
                //co_mnu=2205 es de confirmacion de egreso normal, sin cliente retira
                                strSQL="";
                strSQL+="SELECT a.co_mnu, CASE WHEN a.co_mnu=2205 THEN 'N' WHEN a.co_mnu=2915 THEN 'S' END AS st_cliRet,";
                strSQL+=" a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc";
                strSQL+=" FROM tbr_bodEmpBodGrp as a6";
                strSQL+=" inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  )";
                strSQL+=" inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod )";
                //strSQL+=" inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem )";
                strSQL+=" left outer join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem )";
                strSQL+=" WHERE /*( a6.co_empGrp=0 AND a6.co_bodGrp = " + intCodBodGrp + "  )  AND*/";
                strSQL+=" a1.ne_numdoc!=0 and a1.st_reg = 'A' AND a1.st_imp='N'";
                //strSQL+=" and a.st_reg='A' and a.tx_tipCon in ('P','T') ";
                strSQL+=" AND a1.st_autfacele in ('C','A')";
                //strSQL+=" and  a.co_mnu = 2205 ";
                strSQL+=" and a1.co_emp="+ intCodEmpGui;
                strSQL+=" and a1.co_loc="+ intCodLocGui;
                strSQL+=" and a1.co_tipdoc="+intCodTipGui;
                strSQL+=" and a1.co_doc="+intCodDocGui;
                
                //System.out.println("imprimirGuiRemCliNotRet_autSRI: " + strSQL);
                
                rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                int cnt=0;
                while(!(rstGuiRemCliNotRetAutSri.next())&& cnt<=180){
                    rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                    Thread.sleep(1000);
                    cnt++;
                    System.out.println("cnt "+cnt);
                }
                //System.out.println("contador de tiempo "+cnt);
                rstGuiRemCliNotRetAutSri.beforeFirst();
                while(rstGuiRemCliNotRetAutSri.next()){
                    
                    //System.out.println("PAsa primer query");
                    //Query del reporte
                    strSQL="";
                    strSQL+="select distinct a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=", a1.tx_numautfacele, a1.fe_autfacele, a1.tx_claaccfacele";
                    strSQL+=" , a1.fe_initra, a1.fe_tertra, a1.fe_ing, trim(a1.tx_obs2) as obs2,";
                    strSQL+=" case when a1.tx_numped is null then '' else a1.tx_numped end as tx_numped,";
                    strSQL+=" emp.tx_nom, tipdoc.tx_descor, a1.fe_ing,a1.st_imp as st_impGuia, a1.tx_datdocoriguirem,a1.co_ptopar, a1.tx_ptopar,";
                    strSQL+=" case when a1.co_ptopar =15 then 'INMACONSA' else '' end as puntopartida,";
                    strSQL+=" emp.tx_nom  as empresa, a1.tx_telclides, a1.tx_rucclides,";
                    strSQL+=" (substring(a1.tx_numserdocori,1,3) || '-' || substring(a1.tx_numserdocori,5,3) || '-' || to_char(a1.ne_numdoc, '000000000')) as tx_numdoc,";
                    strSQL+=" a1.ne_numdoc, a1.fe_doc, a1.tx_nomclides, a1.co_clides,";
                    //strSQL+=" a1.tx_nomven, a1.tx_dirclides, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                    strSQL+=" a1.tx_nomven, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                    strSQL+=" a1.tx_ciuclides as cuidad, emp.tx_desconesp as contri1, emp.tx_nom  as empresa,";
                    strSQL+=" (select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kgramo,";
                    strSQL+=" (select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kilo,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_idetra ELSE tra.tx_ide END AS tx_idetra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_nomtra ELSE tra.tx_nom || ' ' || tra.tx_ape END AS tx_nomtra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_plavehtra ELSE veh.tx_pla END AS tx_plaveh";
					/*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
					strSQL+=" ,usr.tx_usr,";
					/*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                     /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                    strSQL+=" CASE WHEN detord.co_tipdocrel IN (1,228,124,238,127,242,125,126,166,225,206,153,58,96,97,98) THEN ";
                    strSQL+="      'VENTA' ";
                    strSQL+=" ELSE ";
                    strSQL+="      CASE WHEN detord.co_tipDocRel IN (4,46,172,204,234) THEN ";
                    strSQL+="           'REPOSICIÓN' ";
                    strSQL+="       END ";
                    strSQL+=" END as documento, a1.tx_obs1 , ";
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/ 
					
                    /*CAMBIO POR DIRECCION EN DURAN*/

                    strSQL+=" case when boddes.tx_dir is null then ";
                    strSQL+="   a1.tx_dirclides ";
                    strSQL+=" else ";				  
                    strSQL+="   case when bodgrp.co_bodgrp=5 then 'Manta: '||boddes.tx_dir ";
                    strSQL+="      else case when bodgrp.co_bodgrp=3 then 'Quito: '||boddes.tx_dir ";
                    strSQL+="                else case when bodgrp.co_bodgrp=28 then 'Cuenca: '||boddes.tx_dir ";
                    strSQL+="                    else case when bodgrp.co_bodgrp=11 then 'Santo Domingo: '||boddes.tx_dir ";
                    strSQL+="                           else boddes.tx_dir";
                    strSQL+=" end end end end ";
                    strSQL+=" end as tx_dirclides, ";

                     /*CAMBIO POR DIRECCION EN DURAN*/
					
                    /*CAMBIO POR POFASA AGREGAR NUMDOC FACTURA Y NUMAUT DEL SRI FACTURA*/
                    strSQL+=" locser.tx_secdoc|| '-' ||to_char(fac.ne_numdoc, 'fm000000000') as numdocfac, fac.ne_numdoc, fac.tx_claaccfacele as claaccfac 	";					
                    /*CAMBIO POR POFASA AGREGAR NUMDOC FACTURA Y NUMAUT DEL SRI FACTURA*/                    

                                        
                    strSQL+=" FROM tbm_cabguirem as a1";
                    strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (a1.co_forret = a3.co_reg )";
                    strSQL+=" inner JOIN tbm_loc as loc ON ( a1.co_emp = loc.co_emp and  a1.co_loc=loc.co_loc)";
                    strSQL+=" inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a1.co_emp)";
                    strSQL+=" inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = a1.co_emp and tipdoc.co_loc=a1.co_loc and tipdoc.co_tipdoc=a1.co_tipdoc)";
                    strSQL+=" LEFT JOIN tbm_veh as veh on (veh.co_veh = a1.co_veh)";
                    strSQL+=" LEFT JOIN tbm_tra as tra on (tra.co_tra = a1.co_cho)";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    strSQL+=" LEFT JOIN tbm_usr as usr on (usr.co_usr = a1.co_usring)";
                    /*SE AGREGA PARA MOSTRAR EL USUARIO QUE CONFIRMA*/
                    
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                    strSQL+=" INNER JOIN tbr_cabguirem as rel ";
                    strSQL+=" on (rel.co_emp= a1.co_emp and rel.co_loc=a1.co_loc and rel.co_tipdoc=a1.co_tipdoc and rel.co_doc=a1.co_doc) ";
                    strSQL+=" INNER JOIN tbm_cabguirem as orden ";
                    strSQL+=" on (orden.co_emp=rel.co_emp and orden.co_loc=rel.co_locrel and orden.co_tipdoc=rel.co_tipdocrel and orden.co_doc=rel.co_docrel) ";
					
					
					/*CAMBIO DIRECCCION DE DURAN*/
					strSQL+=" left outer join tbm_bod as boddes ";
					strSQL+=" on (boddes.co_emp=orden.co_emp and boddes.co_bod=orden.co_ptodes) ";
					  
					strSQL+=" left outer join tbr_bodempbodgrp as bodgrp ";
					strSQL+=" on (bodgrp.co_emp=boddes.co_emp and bodgrp.co_bod=boddes.co_bod and bodgrp.co_empgrp=0) ";
					/*CAMBIO DIRECCCION DE DURAN*/

					strSQL+=" INNER JOIN tbm_detguirem as detord ";
					strSQL+=" on (detord.co_emp=orden.co_emp and detord.co_loc=orden.co_loc and detord.co_tipdoc=orden.co_tipdoc and detord.co_doc=orden.co_doc) ";
					
					/*CAMBIO PARA POFASA NUMDOC DE FACTURA Y NUMERO AUTORIZACION FAC*/
                    strSQL+=" LEFT OUTER JOIN tbm_cabmovinv as fac";
                    strSQL+=" on (fac.co_emp=detord.co_emprel and fac.co_loc=detord.co_locrel and fac.co_tipdoc=detord.co_tipdocrel and fac.co_doc=detord.co_docrel and fac.co_tipdoc=228)";
                    strSQL+=" LEFT OUTER JOIN tbm_loc as locser";
                    strSQL+=" on locser.co_emp=fac.co_emp and locser.co_loc=fac.co_loc";
					/*CAMBIO PARA POFASA NUMDOC DE FACTURA Y NUMERO AUTORIZACION FAC*/
					
					
					
/*                    strSQL+=" INNER JOIN tbm_detguirem as detord ";
                    strSQL+=" on (detord.co_emp=orden.co_emp and detord.co_loc=orden.co_loc and detord.co_tipdoc=orden.co_tipdoc and detord.co_doc=orden.co_doc) ";*/
                    /*SE AGREGA PARA EL PROYECTO DE TRANSFERENCIAS*/
                    
					
                    strSQL+=" WHERE a1.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and a1.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and a1.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and a1.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" AND a1.st_autfacele in ('C','A') AND a1.st_imp='N'";
                    strSQLRep=strSQL;
                    //System.out.println("strSQLRep: " + strSQLRep);

                    //Query del subReporte
                    strSQL="";
                    strSQL+="select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                    strSQL+=" a2.tx_obs1,emp.tx_nom,a2.tx_codalt, inv.tx_codalt2,";
                    strSQL+=" CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm,";
                    strSQL+=" abs(a2.nd_can) as can,a2.tx_unimed";
                    strSQL+=" FROM tbm_cabguirem as DocCab";
                    strSQL+=" INNER JOIN tbm_detguirem as a2";
                    strSQL+=" ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                    strSQL+=" inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a2.co_emp)";
                    strSQL+=" WHERE DocCab.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and DocCab.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and DocCab.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and DocCab.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" order by a2.co_reg";
                    strSQLSubRep=strSQL;
                    //System.out.println("strSQLSubRep: " + strSQLSubRep);
                     String strNumImp="";
                    try{
                        strNumImp=rstGuiRemCliNotRetAutSri.getInt("co_tipDoc")+""+rstGuiRemCliNotRetAutSri.getInt("co_doc");                        //System.out.println("Ruta Reporte GUIREM ->  "+DIRECCION_REPORTE_GUIA);
                        Map mapPar = new HashMap();                
                        mapPar.put("strSQLRep", strSQLRep);
                        mapPar.put("strSQLSubRep", strSQLSubRep);
                        mapPar.put("SUBREPORT_DIR", strDirSis);
                        mapPar.put("imgCodEmp", rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        mapPar.put("imgRut", strDirSis);
                        
                        /*System.out.println("SUBREPORT_DIR: " + strDirSis);
                        System.out.println("imgCodEmp: " + rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        System.out.println("imgRut: " + strDirSis);    */                    
                        //System.out.println("Nombre de la impresora :"+ strNomImp);
                        
                    //    PrintRequestAttributeSet objPriReqAttSet=new HashPrintRequestAttributeSet();
                      //  objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

                    ///////////////////////////////////////////tony
                         PrintRequestAttributeSet objPriReqAttSet = new HashPrintRequestAttributeSet();

                        MediaPrintableArea mpa = new MediaPrintableArea(0.1f, 0.1f, 2.4f, 2.4f, Size2DSyntax.INCH);
                        objPriReqAttSet.add(MediaSizeName.ISO_A5);
                        //sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                        objPriReqAttSet.add(mpa);
                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                        //objPriReqAttSet.add(MediaSizeName.ISO_A5);
                        //objPriReqAttSet.add(OrientationRequested.REVERSE_PORTRAIT); //it's the same here too
                        //Numero de copias tony
                        objPriReqAttSet.add(new Copies(3));
                        JasperPrint reportGuiaRem = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar, con);

                        int intCantPaginas = 0;
                        if (reportGuiaRem.getPages() != null && reportGuiaRem.getPages().size() > 0) {
                            intCantPaginas = reportGuiaRem.getPages().size();
                        }
                        JasperExportManager.exportReportToPdfFile(reportGuiaRem, DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        File pdfA4 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        Document docu = new Document(PageSize.A4, 0, 0, 0, 0);
                        Rectangle recA5 = new Rectangle(PageSize.A5);
                        float floRecA5 = recA5.getHeight() - 70;
                        Rectangle recA52 = new Rectangle(recA5.getWidth(), floRecA5);
                        docu.setPageSize(recA52);
                        PdfReader reader = new PdfReader(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + ".pdf"));
                        PdfWriter writer = PdfWriter.getInstance(docu, new FileOutputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf")));
                        File pdfA5 = new File(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
                        docu.open();
                        PdfContentByte cb = writer.getDirectContent();
                        //PdfImportedPage page = writer.getImportedPage(reader, 1);
                         PdfImportedPage page = null;
                        //Se reduce la medida escalandolo de a4 a a5
                        float Scale = 0.67f;
                        for (int i = 0; i < intCantPaginas; i++) {
                            if (i>0) {
                                      docu.newPage();
                            }
                            page = writer.getImportedPage(reader, i+1);
                             cb.addTemplate(page, Scale, 0, 0, Scale, 0, 0);
                        }
                        docu.close();
                        javax.print.PrintService[] service = PrinterJob.lookupPrintServices();
                        DocPrintJob docPrintJob = null;
                        int count = service.length;

                        for (int i = 0; i < count; i++) {
                            if (service[i].getName().equalsIgnoreCase(strNomImp)) {
                                docPrintJob = service[i].createPrintJob();
                                i = count;
                            }
                        }
                        DocFlavor flavor = DocFlavor.INPUT_STREAM.PDF;
                        FileInputStream psStream = null;
                        psStream = new FileInputStream(DIRECCION_REPORTE_GUIA.replace(".jasper", strNumImp + "2.pdf"));
                        Doc document = new SimpleDoc(psStream, flavor, null);
                        PrinterJob pjob = PrinterJob.getPrinterJob();
                        pjob.defaultPage();
                        pjob.setPrintService(docPrintJob.getPrintService());
                        //Numero de copias tony
                        pjob.setCopies(3);
                        docPrintJob.print(document, objPriReqAttSet);
                        pdfA4.delete();
                        pdfA5.delete();
                        /////////////////////////////tony                      
/**
 * 
 
//                        objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH) ); 
//                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                        //if( (intCodBodGrp==1) || (intCodBodGrp==5) || (intCodBodGrp==11) || (intCodBodGrp==2) || (intCodBodGrp==3)  )//california, manta, sto domingo, via a daule, quito
                            objPriReqAttSet.add(new Copies(3));
                        //System.out.println("Antes de imprimir");
                        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        int intCantPaginas=0;
                        if(reportGuiaRem.getPages()!=null && reportGuiaRem.getPages().size() >0){
                            intCantPaginas=reportGuiaRem.getPages().size();
                        }
                        PrinterName printerName=new PrinterName( strNomImp , null); 
                        PrintServiceAttributeSet printServiceAttributeSet=new HashPrintServiceAttributeSet(); 
                        printServiceAttributeSet.add(printerName);        
                        JRPrintServiceExporter objJRPSerExp=new JRPrintServiceExporter(); 
                        objJRPSerExp.setParameter(JRExporterParameter.JASPER_PRINT, reportGuiaRem); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE); 
                        objJRPSerExp.exportReport(); 
                        objPriReqAttSet=null;                        
 */                       
                        almacenarArchHis("Guia: "+rstGuiRemCliNotRetAutSri.getInt("co_emp")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_loc")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_tipDoc")+"-"+rstGuiRemCliNotRetAutSri.getInt("co_doc")+"  #GUIA: "+rstGuiRemCliNotRetAutSri.getString("ne_numDoc")+" Impresora: "+strNomImp+" Cant. Pg.: "+intCantPaginas);                        
                        
                       // System.out.println("Despues de imprimir");
                        
                        /*
                        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
                        JasperViewer.viewReport(reportGuiaRem);

                        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
                        aset.add(MediaSizeName.ISO_A4);
                        
                        if(intCodBodGrp==1)
                           aset.add(new Copies(3));                        
                        aset.add(Sides.ONE_SIDED);
                       

                        PrintService[] pservices=PrinterJob.lookupPrintServices();
                        if (pservices.length>0) {
                            int indice=traeIndice(pservices, strNomImp);
                            DocPrintJob pj = pservices[indice].createPrintJob();
                            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                            //pj.print(doc, aset);
                        }  
                        */
                        
                        
                        try{
                            //con.setAutoCommit(false);

                            stmGuiRemCliNotRetAutSri_estImp=con.createStatement();
                            strSQL="";
                            strSQL+="UPDATE tbm_cabGuiRem";
                            strSQL+=" SET st_imp='S'";
                            strSQL+=" WHERE co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                            strSQL+=" AND co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                            strSQL+=" AND co_tipDoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                            strSQL+=" AND co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                            strSQL+=";";
                            stmGuiRemCliNotRetAutSri_estImp.executeUpdate(strSQL);
                            //con.commit();

                            stmGuiRemCliNotRetAutSri_estImp.close();
                            stmGuiRemCliNotRetAutSri_estImp=null;


                        }catch(java.sql.SQLException e){
                            blnRes=false;
                            con.rollback();
                        }
                
                        
                    }
                    catch (Exception e){
                        blnRes=false;
                        //conGuiRemCliNotRetAutSri_estImp.rollback();
                        e.printStackTrace();
                    }       
                    
                }
                //conGuiRemCliNotRetAutSri.close();
                conGuiRemCliNotRetAutSri=null;
                //System.out.println("jjj");
                stmGuiRemCliNotRetAutSri.close();
                stmGuiRemCliNotRetAutSri=null;
                rstGuiRemCliNotRetAutSri.close();
                rstGuiRemCliNotRetAutSri=null;
                
                //conGuiRemCliNotRetAutSri_estImp.close();
                conGuiRemCliNotRetAutSri_estImp=null;
            
            }
        }
        catch (Exception e){ 
            blnRes=false;
            e.printStackTrace();
        }
        return blnRes;
    }    

    
    
   
public String[] obtenerRptImpOD(int intCodBodGrp){
        String strRtaRptOD="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper";
            strNomImp="od_califormia";  
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_01.jasper";
            strNomImp="od_dimulti";
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23_01.jasper";
            strNomImp="od_quito";    
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23_01.jasper";
            strNomImp="od_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasperXXX";
            strNomImp="PrintBodega2XXX";   
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23_01.jasper";
            strNomImp="od_stodgo";      
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper_xxx";
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/Inmaconsa/ZafRptCom23_01.jasper";
            //C:\Users\postgres\Desktop\zafiro_desarrollo\proyecto\Reportes\Compras\ZafCom23
            //strNomImp="od_inmaconsa_xxx";   
            strNomImp="od_inmaconsa";
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_01.jasper";
            strNomImp="od_cuenca";   
            strEmpNumGui="2";
            strLocNumGui="10";
        }else if(intCodBodGrp==32){ //DURAN
            strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_01.jasper";
            strNomImp="od_duran";   
            strEmpNumGui="4";
            strLocNumGui="12";
        }else if(intCodBodGrp==36){
            strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/QuitoSur/ZafRptCom23_01.jasper";
            strNomImp="od_quitosur";   
            strEmpNumGui="2";
            strLocNumGui="15";        
        }
        strArrRet[0]=strRtaRptOD;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }   
   
/**
 * Metodo utilizado para imprimir solo OD locales
 * @param conn objeto conexion.
 * @param intCodEmp codigo de empresa.
 * @param intCodLoc codigo de local.
 * @param intCodTipDoc codigo de tipo documento.
 * @param intCodDoc codigo de documento.
 * @param codbod codigo de bodega.
 * @return 
 */
public boolean impresionGuiaRemAutBod2ODLoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,int codbod){
  String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  String strRutSubRpt="";
  int cobodgru;
  
  //ZafImpGuiRem imp=new ZafImpGuiRem();
  try{
    if(conn!=null){
     
        //ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
        
        Statement stmQryOD=conn.createStatement();
        ResultSet rsCabGuiRem=null;
        String strSQL2="";
        strSQL2+="SELECT st_imporddes from tbm_cabGuiRem";
        strSQL2+=" WHERE co_emp=" + intCodEmp + "";
        strSQL2+=" AND co_loc=" + intCodLoc + "";
        strSQL2+=" AND co_tipDoc=" + intCodTipDoc + "";
        strSQL2+=" AND co_doc=" + intCodDoc + "";
        strSQL2+=";";
        rsCabGuiRem=stmQryOD.executeQuery(strSQL2);
        
        if(rsCabGuiRem!=null && rsCabGuiRem.next() && !(rsCabGuiRem.getString("st_imporddes").equalsIgnoreCase("S"))){
        
            strDirSis=getDirectorioSistemaImpOrd();
            cobodgru=obtenerBodGru(conn, intCodEmp, codbod);
            //ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
            String[] strRutaRpt2=obtenerRptImpOD(cobodgru);

            DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
            String strNomImpBod2=strRutaRpt2[1];

            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));

            //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
            //System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );

            Map parameters = new HashMap();
            parameters.put("co_emp", new Integer(intCodEmp) );
            parameters.put("co_loc", new Integer(intCodLoc) );
            parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
            parameters.put("co_doc",  new Integer(intCodDoc) );

            parameters.put("SUBREPORT_DIR", strRutSubRpt );

            //System.out.println("hjh");
            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
            int intCantPaginas=0;
            if(reportGuiaRem.getPages()!=null && reportGuiaRem.getPages().size() >0){
                intCantPaginas=reportGuiaRem.getPages().size();
            }

            //System.out.println("Nombre de la impresora: "+strNomImpBod2);

            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
            objPriReqAttSet=null;

            System.out.println("IMprimiendo en archivo OD");
            //almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" #OD:"+intNumOrdDes+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);
            almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);        

            try{
                    Statement stmActOD=conn.createStatement();
                    String strSQL="";
                    strSQL+="UPDATE tbm_cabGuiRem";
                    strSQL+=" SET st_imporddes='S'";
                    strSQL+=" WHERE co_emp=" + intCodEmp + "";
                    strSQL+=" AND co_loc=" + intCodLoc + "";
                    strSQL+=" AND co_tipDoc=" + intCodTipDoc + "";
                    strSQL+=" AND co_doc=" + intCodDoc + "";
                    strSQL+=";";
                    stmActOD.executeUpdate(strSQL);

                    stmActOD.close();
                    stmActOD=null;


            }catch(java.sql.SQLException e){
                return false;
            }finally{
                    rsCabGuiRem.close();
            }           
        }
        /*
        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
        JasperViewer.viewReport(reportGuiaRem);

        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);

        aset.add(Sides.ONE_SIDED);
        strNomImpBod2="\\\\http://172.16.8.4:631\\imp_sistemas";
        //aset.add(new PrinterName(strNomImpBod2, Locale.US));
        PrintService[] pservices=PrinterJob.lookupPrintServices();
        if (pservices.length>0) {
            int indice=imp.traeIndice(pservices, strNomImpBod2);
            DocPrintJob pj = pservices[indice].createPrintJob();
            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                //pj.print(doc, aset);
        }*/
        
    }
  }catch (JRException e) {  
      e.printStackTrace();
      //objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }catch (Exception e) {  
      e.printStackTrace();
      //objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }
 return true;
}  



/**
     * Metodo que realiza la impresion de OD en transferencia para INMACONSA.
     * @param con Conexion de acceso a Datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodEmpFacCli_FacRel codigo de empresa CLIENTE RETIRA.
     * @return Boolean 
     */
    public boolean impresionOrdDesTransInm( Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpFacCli_FacRel){
        Boolean booRetImp=true;
        String strRutaReporte="";
        String strNombreImpresora="";
        String strDirSis="";
        String DIRECCION_REPORTE_GUIA;
        String strRutSubRpt;
        try{
        strDirSis= getDirectorioSistemaGuiRem();
        if(intCodEmp==intCodEmpFacCli_FacRel){
            if(intCodEmp==CODEMPINMTUV){
                strRutaReporte=RUTAREPORTEODINMTUVAL;
                strNombreImpresora=NOMBREIMPRESORAODINMTUVAL;
            }else if(intCodEmp==CODEMPINMCASUIO){
                strRutaReporte=RUTAREPORTEODINMCASUIO;
                strNombreImpresora=NOMBREIMPRESORAODINMCASUIO;
            }else if(intCodEmp==CODEMPINMDIM){
                strRutaReporte=RUTAREPORTEODINMDIM;
                strNombreImpresora=NOMBREIMPRESORAODINMDIM;
            }
            
            DIRECCION_REPORTE_GUIA=strDirSis+strRutaReporte;
            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));
            
           /*AGREGADO PARA EVITAR QUE SE REPITAN LAS IMPRESIONES DE LAS OD IMPRESAS EN FECHAS ANTERIORES*/ 
            
           Statement stmQryOD=con.createStatement();
           ResultSet rsCabGuiRem=null;
           String strSQL2="";
           strSQL2+="SELECT st_imporddes from tbm_cabGuiRem";
           strSQL2+=" WHERE co_emp=" + intCodEmp + "";
           strSQL2+=" AND co_loc=" + intCodLoc + "";
           strSQL2+=" AND co_tipDoc=" + intCodTipDoc + "";
           strSQL2+=" AND co_doc=" + intCodDoc + "";
           strSQL2+=";";
           rsCabGuiRem=stmQryOD.executeQuery(strSQL2);
        
           if(rsCabGuiRem!=null && rsCabGuiRem.next() && !(rsCabGuiRem.getString("st_imporddes").equalsIgnoreCase("S"))){
           
           /*AGREGADO PARA EVITAR QUE SE REPITAN LAS IMPRESIONES DE LAS OD IMPRESAS EN FECHAS ANTERIORES*/ 
            
            
                Map parameters = new HashMap();
                parameters.put("co_emp", new Integer(intCodEmp) );
                parameters.put("co_loc", new Integer(intCodLoc) );
                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
                parameters.put("co_doc",  new Integer(intCodDoc) );                
                parameters.put("SUBREPORT_DIR", strRutSubRpt );                  


                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                //objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH));//sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                objPriReqAttSet.add(OrientationRequested.PORTRAIT);                

                //System.out.println("Nombre de la impresora: "+strNombreImpresora);

                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con);
                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNombreImpresora , null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
                objPriReqAttSet=null;       

                //almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+"-"+" #OD: "+intNumOrdDes+" Impresora: "+strNombreImpresora);
                almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+strNombreImpresora);

                try{
                        Statement stmActOD=con.createStatement();
                        String strSQL="";
                        strSQL+="UPDATE tbm_cabGuiRem";
                        strSQL+=" SET st_imporddes='S'";
                        strSQL+=" WHERE co_emp=" + intCodEmp + "";
                        strSQL+=" AND co_loc=" + intCodLoc + "";
                        strSQL+=" AND co_tipDoc=" + intCodTipDoc + "";
                        strSQL+=" AND co_doc=" + intCodDoc + "";
                        strSQL+=";";
                        stmActOD.executeUpdate(strSQL);

                        stmActOD.close();
                        stmActOD=null;


                }catch(java.sql.SQLException e){
                    return false;
                }finally{
                    rsCabGuiRem.close();
                }            
            
            }
            
            /*JasperPrint rptODInm =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con); 
            byte[] pdfFile=JasperExportManager.exportReportToPdf(rptODInm);
            JasperViewer.viewReport(rptODInm);

            PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
            aset.add(MediaSizeName.ISO_A4);

            aset.add(Sides.ONE_SIDED);*/

            //  PrintService[] pservices=PrinterJob.lookupPrintServices();
            //  if (pservices.length>0) {
            //  int indice=traeIndice(pservices, strNombreImpresora);
            //  DocPrintJob pj = pservices[indice].createPrintJob();
            //  System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
            //  Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
            //  pj.print(doc, aset);
            // }            
            
        }
        }catch(JRException ex){
            ex.printStackTrace();
            booRetImp=false;
        }catch(Exception ex){
            ex.printStackTrace();
            booRetImp=false;
        }
        
        return booRetImp;
    }
    
    /**
     * Metodo que imprime las OD de importaciones
     * @param con1 objeto conexion
     * @param intCodEmp_Imp codigo empresa importaciones
     * @param intCodLoc_Imp codigo local importaciones
     * @param intCodTipDoc
     * @param intCodDoc
     * @return 
     */
    public boolean impresionOrdDesImp(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, /*int intCodEmp, int intCodLoc,*/ int intCodTipDoc, int intCodDoc){
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
//        String strRutSubRpt="";
        String strRepUtiEmp="";
        String strNomPrn="";
        String strSQLRep="";
        String strSQLSubRep="";
        try{
            if(con1!=null){
                strDirSis= getDirectorioSistemaGuiRem();
                if(intCodEmp_Imp==1){
                    if(intCodLoc_Imp==6){
                        //strRepUtiEmp=RUTAREPORTEODIMPTTUVAL;
                        strRepUtiEmp=RUTAREPORTEODINMTUVAL;
                        strNomPrn=NOMBREIMPRESORAODTUVALTRANS;
                    }

                }
                else if(intCodEmp_Imp==2){
                    if(intCodLoc_Imp==9){
                        //strRepUtiEmp=RUTAREPORTEODIMPTCASUIO;
                        strRepUtiEmp=RUTAREPORTEODINMCASUIO;
                        strNomPrn=NOMBREIMPRESORAODCASUIOTRANS;
                    }
                }
                else if(intCodEmp_Imp==4){
                    if(intCodLoc_Imp==4){
                        //strRepUtiEmp=RUTAREPORTEODIMPTDIM;
                        strRepUtiEmp=RUTAREPORTEODINMDIM;
                        strNomPrn=NOMBREIMPRESORAODDIMTRANS;
                    }
                }



                DIRECCION_REPORTE_GUIA=strDirSis+strRepUtiEmp;
                String strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));
                Map parameters = new HashMap();
                parameters.put("co_emp", new Integer(intCodEmp_Imp) );
                parameters.put("co_loc", new Integer(intCodLoc_Imp) );
                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
                parameters.put("co_doc",  new Integer(intCodDoc) );
                parameters.put("SUBREPORT_DIR", strRutSubRpt);                
                
                //System.out.println("Nombre de la impresora: "+strNomPrn);
                
                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con1);
                int intCantPaginas=0;
                if(reportGuiaRem.getPages()!=null && reportGuiaRem.getPages().size() >0){
                    intCantPaginas=reportGuiaRem.getPages().size();
                }                
				
                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomPrn , null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
                objPriReqAttSet=null;
                
                almacenarArchHis("OD "+intCodEmp_Imp+"-"+intCodLoc_Imp+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+strNomPrn+" Cant Pg.: "+intCantPaginas);
                
                try{
                        Statement stmActOD=con1.createStatement();
                        String strSQL="";
                        strSQL+="UPDATE tbm_cabGuiRem";
                        strSQL+=" SET st_imporddes='S'";
                        strSQL+=" WHERE co_emp=" + intCodEmp_Imp + "";
                        strSQL+=" AND co_loc=" + intCodLoc_Imp + "";
                        strSQL+=" AND co_tipDoc=" + intCodTipDoc + "";
                        strSQL+=" AND co_doc=" + intCodDoc + "";
                        strSQL+=";";
                        stmActOD.executeUpdate(strSQL);

                        stmActOD.close();
                        stmActOD=null;


                }catch(java.sql.SQLException e){
                    return false;
                }                

            }
        }
        catch (JRException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }    
    
    
    /**
     * Metodo para imprimir OD REMOTAS de transferencias o prestamos que no sean de inmaconsa ni de importaciones.
     * @param conn Objeto conexion.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param rtaRpte ruta de repote.
     * @param nomImp nombre de la impresora
     * @return boolean.
     */
    public boolean impresionGuiaRemAutBod2ODRem(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String rtaRpte,String nomImp ){
      String DIRECCION_REPORTE_GUIA="";
      String strDirSis="";
      String strRutSubRpt="";
      try{
        if(conn!=null){
           strDirSis= getDirectorioSistemaGuiRem();
           DIRECCION_REPORTE_GUIA=strDirSis+rtaRpte;
            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));

            //System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );
            
           /*AGREGADO PARA EVITAR QUE SE REPITAN LAS IMPRESIONES DE LAS OD IMPRESAS EN FECHAS ANTERIORES*/ 
            
           Statement stmQryOD=conn.createStatement();
           ResultSet rsCabGuiRem=null;
           String strSQL2="";
           strSQL2+="SELECT st_imporddes from tbm_cabGuiRem";
           strSQL2+=" WHERE co_emp=" + intCodEmp + "";
           strSQL2+=" AND co_loc=" + intCodLoc + "";
           strSQL2+=" AND co_tipDoc=" + intCodTipDoc + "";
           strSQL2+=" AND co_doc=" + intCodDoc + "";
           strSQL2+=";";
           rsCabGuiRem=stmQryOD.executeQuery(strSQL2);
        
           if(rsCabGuiRem!=null && rsCabGuiRem.next() && !(rsCabGuiRem.getString("st_imporddes").equalsIgnoreCase("S"))){
           
           /*AGREGADO PARA EVITAR QUE SE REPITAN LAS IMPRESIONES DE LAS OD IMPRESAS EN FECHAS ANTERIORES*/ 
            
                System.out.println("Entro porque remota od ");
                Map parameters = new HashMap();
                parameters.put("co_emp", new Integer(intCodEmp) );
                parameters.put("co_loc", new Integer(intCodLoc) );
                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
                parameters.put("co_doc",  new Integer(intCodDoc) );

            parameters.put("SUBREPORT_DIR", strRutSubRpt );
            
            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
            
            //System.out.println("Nombre de la impresora: "+nomImp);

            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
            //JasperViewer.viewReport(reportGuiaRem);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( nomImp , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
            objPriReqAttSet=null;     
            
            //almacenarArchHis("OD "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" #OD: "+intNumOrdDes+" Impresora: "+nomImp);
            almacenarArchHis("OD "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+nomImp);
            
            try{
                Statement stmActOD=conn.createStatement();
                String strSQL="";
                strSQL+="UPDATE tbm_cabGuiRem";
                strSQL+=" SET st_imporddes='S'";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=" AND co_loc=" + intCodLoc + "";
                strSQL+=" AND co_tipDoc=" + intCodTipDoc + "";
                strSQL+=" AND co_doc=" + intCodDoc + "";
                strSQL+=";";
                stmActOD.executeUpdate(strSQL);
                //con.commit();

                stmActOD.close();
                stmActOD=null;


                }catch(java.sql.SQLException e){
                    return false;
                }finally{
                    rsCabGuiRem.close();
                }  
           }
        }
      }catch (JRException e) {
          e.printStackTrace();
      }catch (Exception e) {
          e.printStackTrace();
      }
     return true;
    }    

    public String getStrNomArch() {
        return strNomArch;
    }

    public void setStrNomArch(String strNomArch) {
        this.strNomArch = strNomArch;
    }

    public String getStrNomArchGuiNoImpr() {
        return strNomArchGuiNoImpr;
    }

    public void setStrNomArchGuiNoImpr(String strNomArchGuiNoImpr) {
        this.strNomArchGuiNoImpr = strNomArchGuiNoImpr;
    }
    
    
    
    private void almacenarArchHis(String strMenArchHis){
        try{
            Calendar fecha = Calendar.getInstance();
            int intHora = fecha.getTime().getHours();
            int intMin = fecha.getTime().getMinutes();
            int intSec = fecha.getTime().getSeconds();
            String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
            FileWriter writer=new FileWriter(getStrNomArch(),true);
            writer.write(strHora+" "+strMenArchHis+"\n");
            writer.flush();
            writer.close();
        }
        catch(IOException Ext ){
            System.out.println(""+Ext );
        }
        catch(Exception Ext ){
            System.out.println(""+Ext );
        }
    }
    
    private void almacenarArchGuiNoImp(String strMenArchHis){
        try{
            Calendar fecha = Calendar.getInstance();
            int intHora = fecha.getTime().getHours();
            int intMin = fecha.getTime().getMinutes();
            int intSec = fecha.getTime().getSeconds();
            String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
            FileWriter writer=new FileWriter(getStrNomArchGuiNoImpr(),true);
            writer.write(strHora+" "+strMenArchHis+"\n");
            writer.flush();
            writer.close();
        }
        catch(IOException Ext ){
            System.out.println(""+Ext );
        }
        catch(Exception Ext ){
            System.out.println(""+Ext );
        }
    }

    
    
    /**
     * Metodo para imprimir las devoluciones de ventas
     * @param conn Objeto conexion.
     * @param intCodEmp codigo de empresa.
     * @param intCodLoc codigo de local.
     * @param intCodTipDoc codigo tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param rtaRpte ruta de repote.
     * @param nomImp nombre de la impresora
     * @return boolean.
     */
    public boolean impresionDevVen(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodMnu, String strNomUsr ){
      String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  String strRutSubRpt="";
  
  try{
    if(conn!=null){
        Statement stmQry=conn.createStatement();
        ResultSet rsCab=null;
        String strSQL2="";
        strSQL2+= "  select a3.co_bod,a3.co_emp, a3.co_loc\n";
        strSQL2+= " from tbm_cabSolDevVen as a1\n";
        strSQL2+= " inner join tbm_detmovinv as a3 on (a1.co_emp=a3.co_emp and a1.co_locrel=a3.co_loc and a1.co_tipdocrel=a3.co_tipdoc and a1.co_docrel=a3.co_doc)\n";
        strSQL2+= " where a1.co_emp="+ intCodEmp + "";
        strSQL2+= " and a1.co_loc="+ intCodLoc + "";
        strSQL2+= " and a1.co_tipdoc="+ intCodTipDoc + "";
        strSQL2+= " and a1.co_doc="+ intCodDoc + "";
        rsCab=stmQry.executeQuery(strSQL2);
        if(rsCab.next()){
           //  System.out.println(rsCab.getString("st_genorddes"));
          //  if (rsCab.getString("st_genorddes").equalsIgnoreCase("S")) {
                //strDirSis=getDirectorioSistemaImpOrd(); Esto es solo para servicios
            //cobodgru=obtenerBodGru(conn, intCodEmp, codbod);
            //ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
            strDirSis=getDirectorioSistemaImpOrd();
            int cobodgru;
            cobodgru=obtenerBodGru(conn, intCodEmp, rsCab.getInt("co_bod"));
            //ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
            String[] strRutaRpt2=obtenerRptImpDev2(cobodgru);
            
            //String[] strRutaRpt2=obtenerRptImpDev(intCodEmp,intCodLoc,conn,intCodMnu);

            DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
            String strNomImpBod2=strRutaRpt2[1];

            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptVen11.jasper"));

            //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
            //System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );

            Map parameters = new HashMap();
            parameters.put("co_emp", new Integer(intCodEmp) );
            parameters.put("co_loc", new Integer(intCodLoc) );
            parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
            parameters.put("co_doc",  new Integer(intCodDoc) );
            parameters.put("nomUsrImp",  strNomUsr );

            parameters.put("SUBREPORT_DIR", strRutSubRpt );

            //System.out.println("hjh");
            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
            int intCantPaginas=0;
            if(reportGuiaRem.getPages()!=null && reportGuiaRem.getPages().size() >0){
                intCantPaginas=reportGuiaRem.getPages().size();
            }

            //System.out.println("Nombre de la impresora: "+strNomImpBod2);

            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
            objPriReqAttSet=null;

            //System.out.println("IMprimiendo en archivo OD");
            //almacenarArchHis("OD: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" #OD:"+intNumOrdDes+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);
            almacenarArchHis("DV: "+intCodEmp+"-"+intCodLoc+"-"+intCodTipDoc+"-"+intCodDoc+" Impresora: "+strNomImpBod2+" Cant. Pg.: "+intCantPaginas);        
//}
        } 
    }      
  }catch (JRException e) {  
      e.printStackTrace();
  }catch (Exception e) {  
      e.printStackTrace();
  }
 return true;
    }    
//     public String[] obtenerRptImpDev(int intCodEmp, int intCodLoc,Connection con,int intCodMnu){
//        String strRtaRptDev="",strNomImp="",strEmp="",strLoc="";
//        String[] strArrRet=new String[4];
//        strRtaRptDev=obtenerRtaRpt(con,intCodMnu);
//        if(intCodEmp==1 && intCodLoc==4){//TUVAL
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strRtaRptDev="D:/ZafiroNuevo 2016/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_califormia";  
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="1";
//            strLoc="4";
//            
//        }else if(intCodEmp==4 && intCodLoc==3){//DIMULTI
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_dimulti";
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="4";
//            strLoc="3";            
//            
//        }else if(intCodEmp==2 && intCodLoc==1){//QUITO
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_quito";    
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="2";
//            strLoc="1";                        
//            
//        }else if(intCodEmp==2 && intCodLoc==4){//MANTA
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_manta";   
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="2";
//            strLoc="4";                                    
//            
//        }else if(intCodEmp==1 && intCodLoc==6){//IMPORTACIONES
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="PrintBodega2XXX";   
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="1";
//            strLoc="6";
//            
//        }else if(intCodEmp==2 && intCodLoc==6){//STO DOMINGO
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_stodgo";      
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="2";
//            strLoc="6";            
//            
//        }else if(intCodEmp==1 && intCodLoc==10){//INMACONSA
//            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper_xxx";
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //C:\Users\postgres\Desktop\zafiro_desarrollo\proyecto\Reportes\Compras\ZafCom23
//            //strNomImp="od_inmaconsa_xxx";   
//            //strNomImp="od_inmaconsa";
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="1";
//            strLoc="10";
//            
//        }else if(intCodEmp==2 && intCodLoc==10){//CUENCA
//            strRtaRptDev+="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
//            //strNomImp="od_cuenca";   
//            strNomImp="sistemas_inmaconsa";  
//            strEmp="2";
//            strLoc="10";
//        }
//        strArrRet[0]=strRtaRptDev;
//        strArrRet[1]=strNomImp;
//        strArrRet[2]=strEmp;
//        strArrRet[3]=strLoc;        
//        
//        return strArrRet;
//    }   
//     private String obtenerRtaRpt(Connection con,int intCodMnu){
//        String strRuta="";
//            Statement stmQry=null;
//            ResultSet rsDat=null;
//            if (con!=null) {
//                String strSql="SELECT a1.co_rpt, a1.tx_desCor, a1.tx_desLar, a1.tx_rutAbsRpt, a1.tx_rutRelRpt, a1.tx_nomRpt, 'A' AS st_reg\n" +
//                        "                    FROM tbm_rptSis AS a1\n" +
//                        "                     INNER JOIN tbr_rptSisPrg AS a2 ON (a1.co_rpt=a2.co_rpt)\n" +
//                        "                     WHERE a2.co_mnu="+ intCodMnu +
//                        "                     AND a1.st_reg='A'\n" +
//                        "                     ORDER BY a1.co_rpt";
//                try{
//                    stmQry=con.createStatement();
//                    rsDat=stmQry.executeQuery(strSql);
//                    if(rsDat.next()){
//                        strRuta=rsDat.getString("tx_rutAbsRpt");
//                    }
//                }catch(Exception ex){
//                    ex.printStackTrace();
//                }finally{
//                    try{
//                        rsDat.close();
//                        rsDat=null;
//                        stmQry.close();
//                        stmQry=null;
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        return strRuta;
//    }
     public String[] obtenerRptImpDev2(int intCodBodGrp){
        String strRtaRptDev="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper";
            strNomImp="od_califormia";  
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_dimulti";
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_quito";    
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="PrintBodega2XXX";   
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_stodgo";      
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasper_xxx";
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //C:\Users\postgres\Desktop\zafiro_desarrollo\proyecto\Reportes\Compras\ZafCom23
            //strNomImp="od_inmaconsa_xxx";   
            strNomImp="od_inmaconsa";
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_cuenca";   
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="2";
            strLocNumGui="10";
            
        
        }
        else if(intCodBodGrp==32){//DIMULTI DURAN
            strRtaRptDev="/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            //strRtaRptDev+="//172.16.1.2/Zafiro/Reportes/Ventas/ZafVen11/ZafRptVen11.jasper";
            strNomImp="od_duran";   
            //strNomImp="sistemas_inmaconsa"; 
            strEmpNumGui="4";
            strLocNumGui="12";
            
        
        }
        strArrRet[0]=strRtaRptDev;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }   
}
