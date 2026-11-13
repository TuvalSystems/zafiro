
package Librerias.ZafClassImpGuia;

import Librerias.ZafUtil.ZafUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author ilino
 */
public class ZafClasImpGuiImp 
{
    private ZafClassImpGuia_01 objEnvMail; 
    private ZafUtil objUti;    
    
    private String strStrConnDb="";
    private String strUsrConnDb="";
    private String strClaConnDb="";
    private String strCorEleTo="";
    private String strImpRptBod1="";//estados de impresion de OD
    private String strImpRptBod2="";//estados de impresion de GR
    private String strRutaRpt6="";//orden despacho tuval
    private String strRutaRpt7="";//guia remision tuval
    private String strRutaRpt8="";//orden despacho castek uio
    private String strRutaRpt9="";//guia remision castek uio
    private String strRutaRpt10="";//orden despacho dimulti
    private String strRutaRpt11="";//guia remision dimulti
    private String strNomArchHis="";
    private String strNomImpBod_ordDesTuv="";
    private String strNomImpBod_guiRemTuv="";
    private String strNomImpBod_ordDesCasUio="";
    private String strNomImpBod_guiRemCasUio="";
    private String strNomImpBod_ordDesDim="";
    private String strNomImpBod_guiRemDim="";

    private boolean blnEnvNumODBod=false;
    private String strIpMaqBod="";
    private int intPuertoBod=0;
    private int intCodBodGrp=0;

    final private int intCodTipDocGui=102;

    private String strBodUsaGenTrsImp="1,2,3,5,11,15,7";//se usan las bodegas donde se puede transferir de bodega importaciones donde   co_empGrp=0    y   co_bodGrp in (1,2,3,5,11,15)

    private int intCodBod_OriDes_LocInm;

    private String strSQL="";


    private int intCodEmp_Imp, intCodLoc_Imp;
    private int intCodEmpTuv_Imp, intCodLocTuv_Imp, intCodEmpCasUio_Imp, intCodLocCasUio_Imp, intCodEmpDim_Imp, intCodLocDim_Imp;

    private String strNomImpIngImp;



    public ZafClasImpGuiImp(String strStrConnDbRec, String strUsrConnDbRec, String strClaConnDbRec, int intCodBodGrpRec
        , String strCorEleToRec, String strImpRptBod2Rec, String strRutaRpt6Rec
        , String strRutaRpt7Rec, String strRutaRpt8Rec, String strRutaRpt9Rec, String strRutaRpt10Rec, String strRutaRpt11Rec
        , String strNomArchHisRec
        , String strNomImpBod1Rec, String strNomImpBod2Rec, String strNomImpBod3Rec, String strNomImpBod4Rec, String strNomImpBod5Rec, String strNomImpBod6Rec
        , String strImpRptBod1Rec
        , boolean blnEnvNumODBodR, String strIpMaqBodR, int intPuertoBodR
        , int codigoEmpresaTuval, int codigoLocalTuval, int codigoEmpresaCastekUio, int codigoLocalCastekUio, int codigoEmpresaDimulti, int codigoLocalDimulti) {
        try{
            strStrConnDb = strStrConnDbRec;
            strUsrConnDb = strUsrConnDbRec;
            strClaConnDb = strClaConnDbRec;
            strCorEleTo = strCorEleToRec;
            strImpRptBod1 = strImpRptBod1Rec;
            strImpRptBod2 = strImpRptBod2Rec;
            strRutaRpt6 = strRutaRpt6Rec;
            strRutaRpt7 = strRutaRpt7Rec;
            strRutaRpt8 = strRutaRpt8Rec;
            strRutaRpt9 = strRutaRpt9Rec;
            strRutaRpt10 = strRutaRpt10Rec;
            strRutaRpt11 = strRutaRpt11Rec;

            strNomArchHis = strNomArchHisRec;
            
            strNomImpBod_ordDesTuv = strNomImpBod1Rec;
            strNomImpBod_guiRemTuv = strNomImpBod2Rec;
            strNomImpBod_ordDesCasUio = strNomImpBod3Rec;
            strNomImpBod_guiRemCasUio = strNomImpBod4Rec;
            strNomImpBod_ordDesDim = strNomImpBod5Rec;
            strNomImpBod_guiRemDim = strNomImpBod6Rec;

            intCodEmpTuv_Imp=codigoEmpresaTuval;
            intCodLocTuv_Imp=codigoLocalTuval;
            intCodEmpCasUio_Imp=codigoEmpresaCastekUio;
            intCodLocCasUio_Imp=codigoLocalCastekUio;
            intCodEmpDim_Imp=codigoEmpresaDimulti;
            intCodLocDim_Imp=codigoLocalDimulti;
            
            intCodBodGrp = intCodBodGrpRec;

            blnEnvNumODBod=blnEnvNumODBodR;
            strIpMaqBod=strIpMaqBodR;
            intPuertoBod=intPuertoBodR;

            objEnvMail = new ZafClassImpGuia_01(strCorEleTo);
            objUti = new ZafUtil();
        }
        catch(Exception e){
            System.out.println("Error al cargar clase ZafClassImpGuiaImportaciones ");
        }
    }


    public void setNomAch(String strNomAch ){
        strNomArchHis = strNomAch;
    }




    //INICIO ORDENES DE DESPACHO

    /**
     * Función que llama a método de consulta de Transferencias generadas
     * @return true: Si se pudieron consultar los documentos de Transferencias
     * <BR> false: caso contrario
     */
    public boolean generaTransGuiRem(){
        boolean blnRes=false;
        Connection con1;
        try{
            con1=java.sql.DriverManager.getConnection(strStrConnDb, strUsrConnDb, strClaConnDb );
            if(con1!=null ){
                consultarTansAut(con1, "204, 234" );
                con1.close();
                con1=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            System.out.println(""+e );
            objEnvMail.enviarCorreo("Error SQL - Clase-ZafClasImpGuiImp, método-generaTransGuiRem:  " + e.toString() );
        }
        catch(Exception e){
            blnRes=false;
            System.out.println(""+e );
            objEnvMail.enviarCorreo("Error - Clase-ZafClasImpGuiImp, método-generaTransGuiRem:  "+e.toString() );
        }
        return blnRes;
    }

/**
 * Función que permite obtener los documentos a partir de los cuales se deben generar las Órdenes de Despacho
 * @return true: Si se pudo generar la Orden de Despacho
 * <BR> false: caso contrario
 */
    private boolean consultarTansAut(Connection con1, String strTipDoc ){
        boolean blnRes=true;
        ResultSet rst1, rst2;
        Statement stm1, stm2;
        int intCodEmpImp, intCodLocImp;
        try{
            if(con1!=null){
                stm1=con1.createStatement();
                stm2=con1.createStatement();
                strSQL="";
                strSQL+="SELECT a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, ( a3.tx_descor || '-' || a.ne_numdoc ) as docrel, a4.tx_dir";
                strSQL+=" FROM tbm_cabmovinv AS a";
                strSQL+=" INNER JOIN tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc)";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a2 ON (a2.co_emp=a.co_emp AND a2.co_bod=a1.co_bod)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc)";
                strSQL+=" INNER JOIN tbm_bod AS a4 on (a4.co_emp=a1.co_emp and a4.co_bod=a1.co_bod)";
                strSQL+=" WHERE a.co_tipdoc IN ( "+strTipDoc+" ) AND  a.st_creguirem='N'  AND a1.nd_can < 0 AND  a.st_reg not in('E','I')";
                strSQL+=" AND ( a2.co_empGrp=0 AND a2.co_bodGrp=" + intCodBodGrp + ")";
                strSQL+=" GROUP BY a.co_emp,  a.co_loc, a.co_tipdoc, a.co_doc, a.fe_doc, a1.co_bod, a3.tx_descor, a.ne_numdoc, a4.tx_dir";
                System.out.println("consultarTransAut  :  "+ strSQL );
                rst1=stm1.executeQuery(strSQL);
                while(rst1.next()){
                    strSQL="";
                    strSQL+="SELECT a.co_emp, x.co_bod, a.tx_nom, a.tx_dir, a.tx_tel, ";
                    strSQL+=" CASE WHEN a.co_emp=1 THEN";
                    strSQL+="	   CASE WHEN x.co_bod in (6, 7) THEN 1039";// DIMULTI
                    strSQL+="		WHEN x.co_bod in (8, 10, 12, 21, 22, 23) THEN 603";// CASTEK
                    strSQL+="		WHEN x.co_bod in (1, 15, 20, 3, 4) THEN 3516";//// TUVAL
                    strSQL+="		ELSE NULL";
                    strSQL+="	   END";
                    strSQL+=" WHEN a.co_emp=2 THEN";
                    strSQL+="	   CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23, 5, 6) THEN 446";// CASTEK
                    strSQL+="		WHEN x.co_bod IN (3, 15, 20) THEN 2854";// TUVAL
                    strSQL+="		WHEN x.co_bod in (8, 9) THEN 789";// DIMULTI
                    strSQL+="		ELSE NULL";
                    strSQL+="	   END";
                    strSQL+=" WHEN a.co_emp=4 THEN";
                    strSQL+="	   CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN 498";// CASTEK
                    strSQL+="		WHEN x.co_bod IN (2, 15, 20) THEN 3117";// TUVAL
                    strSQL+="		WHEN x.co_bod in (1, 6, 3, 4) THEN 886";// DIMULTI
                    strSQL+="		ELSE NULL";
                    strSQL+="	   END";
                    strSQL+=" END  AS coclides,";
                    strSQL+=" CASE WHEN a.co_emp=1 THEN";
                    strSQL+=" 	  CASE WHEN x.co_bod=7 THEN 'DIMULTI'";
                    strSQL+=" 	       WHEN x.co_bod=8 THEN 'CASTEK - QUITO'";
                    strSQL+="	       WHEN x.co_bod=10 THEN 'CASTEK - MANTA'";
                    strSQL+="	       WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO'";
                    strSQL+="	       WHEN x.co_bod in (1, 15, 3, 4) THEN 'TUVAL'";
                    strSQL+="	       WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
                    strSQL+="	  END";
                    strSQL+=" WHEN a.co_emp=2 THEN";
                    strSQL+="	  CASE WHEN x.co_bod in (1, 5, 6) THEN 'CASTEK - QUITO '";
                    strSQL+="	       WHEN x.co_bod in (8, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
                    strSQL+="	       WHEN x.co_bod in (3, 15) THEN 'TUVAL '";
                    strSQL+="	       WHEN x.co_bod=4 THEN 'CASTEK - MANTA '";
                    strSQL+="	       WHEN x.co_bod=12 THEN 'CASTEK - SANTO DOMINGO '";
                    strSQL+=" 	       WHEN x.co_bod=9 THEN 'DIMULTI'";
                    strSQL+="	  END";
                    strSQL+=" WHEN a.co_emp=4 THEN";
                    strSQL+="	  CASE WHEN x.co_bod=7 THEN 'CASTEK - QUITO '";
                    strSQL+="	       WHEN x.co_bod IN (2, 15) THEN 'TUVAL '";
                    strSQL+="	       WHEN x.co_bod=9 THEN 'CASTEK - MANTA '";
                    strSQL+="	       WHEN x.co_bod=11 THEN 'CASTEK - SANTO DOMINGO'";
                    strSQL+=" 	       WHEN x.co_bod in (1, 3, 4) THEN 'DIMULTI'";
                    strSQL+="	       WHEN x.co_bod in (6, 20, 21, 22, 23) THEN a.tx_nom";// EXPOSICION
                    strSQL+="	  END";
                    strSQL+=" END  AS EMPRESA,";
                    strSQL+=" CASE WHEN a.co_emp=1 THEN";
                    strSQL+="	  CASE WHEN x.co_bod in (6, 7) THEN '0992372427001'";//// DIMULTI
                    strSQL+="	       WHEN x.co_bod IN (8, 10, 12, 21, 22, 23) THEN '0992329432001'";// CASTEK
                    strSQL+="	       WHEN x.co_bod IN (1, 15, 20, 3, 4) THEN '0990281866001'";
                    strSQL+=" 	       ELSE NULL";// TUVAL
                    strSQL+="	  END";
                    strSQL+=" WHEN a.co_emp=2 THEN";
                    strSQL+="	  CASE WHEN x.co_bod IN (1, 4, 12, 21, 22, 23, 5, 6) THEN '0992329432001'";// CASTEK
                    strSQL+="	       WHEN x.co_bod in (3, 15, 20) THEN '0990281866001 '";// TUVAL
                    strSQL+="	       WHEN x.co_bod in (8, 9) THEN '0992372427001'";// DIMULTI
                    strSQL+="	  END";
                    strSQL+=" WHEN a.co_emp=4 THEN";
                    strSQL+="	 CASE WHEN x.co_bod IN (7, 9, 11, 21, 22, 23) THEN '0992329432001'";// CASTEK
                    strSQL+="	      WHEN x.co_bod IN (2, 15, 20) THEN '0990281866001'";// TUVAL
                    strSQL+="	      WHEN x.co_bod in (1, 6, 3, 4) THEN '0992372427001'";// DIMULTI
                    strSQL+="	 END";
                    strSQL+=" END  AS RUCEMP";
                    strSQL+=" FROM (";
                    strSQL+="       SELECT distinct(co_bod) , co_emp FROM tbm_detmovinv";
                    strSQL+="       WHERE co_emp="+rst1.getInt("co_emp") + "  AND co_loc="+rst1.getInt("co_loc") + "";
                    strSQL+="       AND co_tipdoc="+rst1.getInt("co_tipdoc") + " AND co_doc="+rst1.getInt("co_doc") + " and nd_can > 0";
                    strSQL+="      ) as x";
                    strSQL+=" INNER JOIN tbm_bod as a on (a.co_emp=x.co_emp and a.co_bod=x.co_bod) ;";
                    System.out.println("while - consultarTansAut:  "+ strSQL );
                    rst2=stm2.executeQuery(strSQL);
                    while(rst2.next()){
                        if(rst1.getInt("co_emp")==intCodEmpTuv_Imp){
                            intCodEmp_Imp=intCodEmpTuv_Imp;
                            intCodLoc_Imp=intCodLocTuv_Imp;
                            strNomImpIngImp="TUVAL S.A.";
                        }
                        else if(rst1.getInt("co_emp")==intCodEmpCasUio_Imp){
                            intCodEmp_Imp=intCodEmpCasUio_Imp;
                            intCodLoc_Imp=intCodLocCasUio_Imp;
                            strNomImpIngImp="CASTEK S.A.";
                        }
                        else if(rst1.getInt("co_emp")==intCodEmpDim_Imp){
                            intCodEmp_Imp=intCodEmpDim_Imp;
                            intCodLoc_Imp=intCodLocDim_Imp;
                            strNomImpIngImp="DIMULTI S.A.";
                        }
                        System.out.println("AAAAA");
                        insertarOrdDesTrs(con1, intCodEmp_Imp, intCodLoc_Imp, rst1.getInt("co_emp"), rst1.getInt("co_loc"), rst1.getInt("co_tipdoc"), rst1.getInt("co_doc")
                                              , rst2.getString("empresa"), rst2.getString("rucemp")
                                              , rst2.getString("tx_dir"), rst2.getString("tx_tel"), rst1.getInt("co_bod")
                                              , rst1.getString("tx_dir"), rst1.getString("docrel"), rst2.getInt("co_bod"), rst2.getString("coclides")
                                              , strNomImpIngImp
                                            );
                        System.out.println("BBBBB");
                    }
                    rst2.close();
                    rst2=null;

                }
                rst1.close();
                rst1=null;
                stm1.close();
                stm1=null;
                stm2.close();
                stm2=null;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println("Error SQL consultarTansAut: "+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println("Error consultarTansAut: "+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return  blnRes;
    }

    private boolean insertarOrdDesTrs(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,
                                          String strNomCli, String strRucCli, String strDirCli, String strTelCli,  int intCodBod
                                          , String strDirBod, String strDocRel, int intCodBodDes, String strcoclides, String strNombreImportador ){
        boolean blnRes=false;
        int intTipDocOrdDes=101;//para Orden de Despacho y el 102 es para GUIA
        int intCodDocOrdDes=0;
        int intNumOrdDes=0;
        ResultSet rst3;
        Statement stm3;
        try{
            if(con1!=null){
                con1.setAutoCommit(false);
                if(getVerficaOrdDesTrs(con1, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc  )){//se envian los datos de la transferencia
                    System.out.println("C");
                    stm3=con1.createStatement();
                    strSQL="";
                    strSQL+="SELECT CASE WHEN (Max(co_doc)+1) IS NULL THEN 1 ELSE  (Max(co_doc)+1) END AS co_doc";
                    strSQL+=" FROM tbm_cabguirem WHERE";
                    strSQL+=" co_emp=" + intCodEmp_Imp + " AND co_loc=" + intCodLoc_Imp + " AND co_tipDoc=" + intTipDocOrdDes + "";
                    System.out.println("co_doc: " + strSQL);
                    rst3=stm3.executeQuery(strSQL);
                    if(rst3.next())
                        intCodDocOrdDes=rst3.getInt("co_doc");

                    strSQL="";
                    strSQL+="SELECT CASE WHEN ne_ultnumorddes is null THEN 1 else ne_ultnumorddes+1 END as ne_numOrdDes";
                    strSQL+=" FROM tbm_loc WHERE co_emp=" + intCodEmp_Imp + "";
                    strSQL+=" AND co_loc=" + intCodLoc_Imp + "";
                    System.out.println("ne_numOrdDes: " + strSQL);
                    rst3=stm3.executeQuery(strSQL);
                    if(rst3.next())
                        intNumOrdDes=rst3.getInt("ne_numOrdDes");

                    strSQL="";
                    strSQL+="UPDATE tbm_loc SET ne_ultnumorddes=" + intNumOrdDes + "";
                    strSQL+=" WHERE co_emp=" + intCodEmp_Imp + " AND co_loc=" + intCodLoc_Imp + "";
                    System.out.println("UPDATE tbm_loc: " + strSQL);
                    stm3.executeUpdate(strSQL);

                    if(insertarCabOrdDesTrans(con1, intCodEmp_Imp, intCodLoc_Imp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intTipDocOrdDes, intCodDocOrdDes, intNumOrdDes, strNomCli,  strRucCli, strDirCli, strTelCli, intCodBod,  strDirBod , strDocRel, strcoclides, intCodBodDes, strNombreImportador ) ){
                        if( insertarDetOrdDesTrans(con1, intCodEmp_Imp, intCodLoc_Imp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intTipDocOrdDes, intCodDocOrdDes, intCodBod, intCodBodDes  ) ){
                            con1.commit();

                            almacenarArchHis("Ord.Des.Imp.--> "+ intNumOrdDes);
                            enviarNumODBod(intNumOrdDes);

                            if(strImpRptBod1.equals("S"))
                                impresionOrdDesImp(con1, intCodEmp_Imp, intCodLoc_Imp, intCodEmp, intCodLoc, intTipDocOrdDes, intCodDocOrdDes);
                        }
                        else{
                            con1.rollback();
                        }
                    }
                    else{
                        con1.rollback();
                    }
                    stm3.close();
                    stm3=null;
                }
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return  blnRes;
    }

    private boolean insertarCabOrdDesTrans(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodTipDocOrdDes, int intCodDocOrdDes, int intNumOrdDes, String strNomCli, String strRucCli, String strDirCli, String strTelCli,  int intCodBod, String strDirBod, String strDocRel, String strcoclides, int intCodBodDes, String strNomImp  ){
        boolean blnRes=true;

        Statement stm4;
        try{
            if(con1!=null){
                stm4=con1.createStatement();
                strSQL="";
                strSQL+="INSERT INTO tbm_cabguirem(";
                strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc ,fe_orddes";
                strSQL+=" , ne_numorddes, ne_numdoc,  tx_rucclides, tx_nomclides,  tx_dirclides, tx_telclides, tx_ciuclides, st_imp";
                strSQL+=" , st_reg, fe_ing, co_usring ,st_conInv, fe_initra, fe_tertra, tx_coming, st_regrep, co_ptopar, tx_ptopar";
                strSQL+=" , tx_vehret, tx_choret ,tx_datdocoriguirem , co_clides, co_ptodes, st_imporddes, tx_obs2  )";
                strSQL+=" VALUES( ";
                strSQL+="" + intCodEmp_Imp + ",";//co_emp
                strSQL+="" + intCodLoc_Imp + ",";//co_loc
                strSQL+="" + intCodTipDocOrdDes + ",";//co_tipdoc
                strSQL+="" + intCodDocOrdDes + ",";//co_doc
                strSQL+=" CURRENT_DATE,";//fe_doc
                strSQL+=" CURRENT_DATE,";//fe_orddes
                strSQL+="" + intNumOrdDes + ",";//ne_numorddes
                strSQL+=" 0,";//ne_numdoc
                strSQL+="" + objUti.codificar(strRucCli) + ",";//tx_rucclides
                strSQL+="" + objUti.codificar(strNomCli) + ",";//tx_nomclides
                strSQL+="" + objUti.codificar(strDirCli) + ",";//tx_dirclides
                strSQL+="" + objUti.codificar(strTelCli) + ",";//tx_telclides
                strSQL+=" Null,";//tx_ciuclides
                strSQL+=" 'N',";//st_imp
                strSQL+=" 'A',";//st_reg
                strSQL+=" CURRENT_TIMESTAMP,";//fe_ing
                strSQL+=" 1,";//co_usring
                strSQL+=" 'P',";//st_conInv
                strSQL+=" CURRENT_DATE,";//fe_initra
                strSQL+=" CURRENT_DATE,";//fe_tertra
                strSQL+=" '',";//tx_coming
                strSQL+=" 'I',";//st_regrep

                //de acuerdo a la empresa y local de Inmaconsa - Bodega Origen
                strSQL+="" + getBodegaOrigenDestino_Inmaconsa(con1, intCodEmp_Imp, intCodBod) + ",";//co_ptopar

                strSQL+="" + objUti.codificar(strDirBod) + ",";//tx_ptopar
                strSQL+=" '',";//tx_vehret
                strSQL+=" '',";//tx_choret
                strSQL+="" + objUti.codificar(strDocRel) + ",";//tx_datdocoriguirem
                strSQL+="" + strcoclides + ",";//co_clides


                //de acuerdo a la empresa y local de Inmaconsa - Bodega Destino
                strSQL+="" + getBodegaOrigenDestino_Inmaconsa(con1, intCodEmp_Imp, intCodBodDes)       + ",";//co_ptodes




                
                strSQL+="'S',";//st_imporddes
                strSQL+="'" + strNomImp + "'";//tx_obs2

                

                strSQL+=");";
                strSQL+=" UPDATE tbm_cabmovinv SET st_creguirem='S', ne_numorddes="+intNumOrdDes+"";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=" AND co_loc=" + intCodLoc + "";
                strSQL+=" AND co_tipdoc=" + intCodTipDoc + "";
                strSQL+=" AND co_doc="+intCodDoc + "";
                strSQL+=";";
                System.out.println("insertarCabOrdDesTrans: " + strSQL);
                stm4.executeUpdate(strSQL);
                stm4.close();
                stm4=null;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt.toString() );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return blnRes;
    }

    private boolean insertarDetOrdDesTrans(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodTipDocOrdDes, int intCodDocOrdDes, int intCodBodOri, int intCodBodDes  ){
        boolean blnRes=true;
        int intEstIns=0;
        int intReg=0;

        ResultSet rst5;
        Statement stm5, stm6;
        try{
            if(con1!=null){
                stm5=con1.createStatement();
                stm6=con1.createStatement();
                StringBuffer stbinsGuia=new StringBuffer(); //VARIABLE TIPO BUFFER
                strSQL="";
                strSQL+="SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.co_itm, x.co_reg, x.tx_codalt, x.tx_nomitm,";
                strSQL+=" x.tx_unimed, abs(x.nd_can) as nd_can, x.st_meringegrfisbod FROM (";
                strSQL+="       SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_meringegrfisbod , co_bod , ne_numfil";
                strSQL+="       FROM tbm_detmovinv";
                strSQL+="       WHERE co_emp=" + intCodEmp + " AND co_loc=" + intCodLoc + "";
                strSQL+="       AND co_tipdoc=" + intCodTipDoc + " AND co_doc=" + intCodDoc + "";
                strSQL+="       AND co_bod=" + intCodBodOri + " AND nd_can < 0";
                strSQL+=" ) AS x";
                strSQL+=" INNER JOIN(";
                strSQL+="       SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_meringegrfisbod , co_bod , ne_numfil";
                strSQL+="       FROM tbm_detmovinv";
                strSQL+="       WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+"";
                strSQL+="       AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+"";
                strSQL+="       AND co_bod="+intCodBodDes+" AND nd_can > 0";
                strSQL+=" ) AS x1";
                strSQL+=" ON ( x1.co_emp=x.co_emp AND x1.co_loc=x.co_loc AND x1.co_tipdoc=x.co_tipdoc";
                strSQL+="      AND x1.co_doc=x.co_doc AND x1.ne_numfil=x.ne_numfil )";
                System.out.println("insertarDetOrdDesTrans-1: " + strSQL);
                rst5=stm5.executeQuery(strSQL);
                while(rst5.next()){
                    intReg++;
                    if(intEstIns > 0)
                        stbinsGuia.append(" UNION ALL ");

                    strSQL="";
                    strSQL+="SELECT " + intCodEmp_Imp + "," + intCodLoc_Imp + "," + intCodTipDocOrdDes + ", " + intCodDocOrdDes + "";
                    strSQL+=" ," + intReg+", " + intCodEmp + "," + intCodLoc + "," + intCodTipDoc + "," + intCodDoc + "," + rst5.getInt("co_reg") + "";
                    strSQL+=" , " + rst5.getString("co_itm") + ", '" + rst5.getString("tx_codalt") + "'";
                    strSQL+=" ," + objUti.codificar(rst5.getString("tx_nomitm")) + ",'" + rst5.getString("tx_unimed") + "', abs(" + rst5.getString("nd_can") + ")";
                    strSQL+=" ,'I', '', '" + rst5.getString("st_meringegrfisbod") + "'";
                    stbinsGuia.append(strSQL);
                    intEstIns=1;
                }
                if(intEstIns==1){
                    strSQL="";
                    strSQL+="INSERT INTO  tbm_detguirem (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel,";
                    strSQL+=" co_tipdocrel, co_docrel, co_regrel, co_itm, tx_codalt,  tx_nomitm,";
                    strSQL+=" tx_unimed, nd_can, st_regrep, tx_obs1, st_meregrfisbod )";
                    strSQL+="" + stbinsGuia.toString() + "";
                    System.out.println("insertarDetOrdDesTrans 2: " + strSQL);
                    stm6.executeUpdate(strSQL);
                }
                stbinsGuia=null;
                stm5.close();
                stm5=null;
                stm6.close();
                stm6=null;
                rst5.close();
                rst5=null;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return blnRes;
    }

/**
 * Función que permite verificar las bodegas para poder generar la Orden de Despacho por Transferencias
 * @return true: Si el documento es de Ingreso de Inventario
 * <BR> false: caso contrario
 */
    private boolean getVerficaOrdDesTrs(Connection con1, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
        boolean blnRes=false;

        Statement stm7, stm8;
        ResultSet rst7;
        try{
            if(con1!=null){
                stm7=con1.createStatement();
                stm8=con1.createStatement();
                strSQL="";
                strSQL+="SELECT * FROM ( ";
                strSQL+="           SELECT distinct(co_bod), co_emp FROM tbm_detmovinv";
                strSQL+="           WHERE co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + "";
                strSQL+="           AND co_tipdoc="+intCodTipDoc+" AND co_doc="+intCodDoc+" AND nd_can > 0";
                strSQL+=") as x";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=x.co_emp AND a6.co_bod=x.co_bod) ";
                strSQL+=" AND ( a6.co_empGrp=0 AND a6.co_bodGrp in (" + strBodUsaGenTrsImp + ") )";
                System.out.println("getVerficaOrdDesTrs 1: " + strSQL);
                rst7=stm7.executeQuery(strSQL);
                if(rst7.next()){
                    blnRes=true;
                }
                else{
                    strSQL="";
                    strSQL+="UPDATE tbm_cabmovinv SET st_creguirem='S' WHERE co_emp="+intCodEmp+" ";
                    strSQL+=" AND co_loc=" + intCodLoc + " AND co_tipdoc=" + intCodTipDoc + " AND co_doc=" + intCodDoc + "";
                    System.out.println("else-getVerficaOrdDesTrs: " + strSQL);
                    stm8.executeUpdate(strSQL);
                }
                rst7.close();
                rst7=null;
                stm7.close();
                stm7=null;
                stm8.close();
                stm8=null;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return  blnRes;
    }

    private void enviarNumODBod(int intNumOD ){
        if( blnEnvNumODBod ){
            ZafThreadPro objThrPro=new ZafThreadPro(strIpMaqBod,intPuertoBod,intNumOD);
            objThrPro.start();
            objThrPro=null;
        }
    }

    private class ZafThreadPro extends Thread{
        String strIpS="";
        int intPuertoS=0;
        int intNumOD=0;

        public ZafThreadPro(String strIp, int intPuerto, int intNumODR ){
            strIpS=strIp;
            intPuertoS= intPuerto;
            intNumOD= intNumODR;
        }

        @Override
        public void run(){
            try{
                java.net.Socket s1 = new java.net.Socket(strIpS, intPuertoS);
                java.io.DataOutputStream dos = new java.io.DataOutputStream(s1.getOutputStream());
                //dos.writeInt(intNumOD);
                dos.writeUTF(""+intNumOD);
                dos.close();
                s1.close();
            }
            catch (java.net.ConnectException connExc){
                System.err.println("OCURRIO UN ERROR 1 "+connExc );
            }
            catch (java.io.IOException e){
                System.err.println("OCURRIO UN ERROR 2 "+ e );
            }
        }
    }

    private String getDirectorioSistema(){
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

/**
 * Almacena un archivo de txt el proceso a ejecutarse.
 * @param strMenArchHis
 */
    private void almacenarArchHis(String strMenArchHis){
        try{
            Calendar fecha = Calendar.getInstance();
            int intHora = fecha.getTime().getHours();
            int intMin = fecha.getTime().getMinutes();
            int intSec = fecha.getTime().getSeconds();
            String strHora = "Hora "+String.valueOf(intHora)+":"+(intMin<10?"0"+String.valueOf(intMin):String.valueOf(intMin))+":"+(intSec<10?"0"+String.valueOf(intSec):String.valueOf(intSec));
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(strNomArchHis, true));
            PrintWriter salida = new PrintWriter(bw);
            salida.println( strHora+" "+strMenArchHis );
            salida.close();
        }
        catch(IOException Ext ){
            System.out.println(""+Ext );
        }
        catch(Exception Ext ){
            System.out.println(""+Ext );
        }
    }

    //2.- IMPRESION
    private boolean impresionOrdDesImp(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
//        String strRutSubRpt="";
        String strRepUtiEmp="";
        String strNomPrn="";
        String strSQLRep="";
        String strSQLSubRep="";
        try{
            if(con1!=null){
                strDirSis= getDirectorioSistema();
                if(intCodEmp_Imp==1){
                    if(intCodLoc_Imp==6){
                        strRepUtiEmp=strRutaRpt6;
                        strNomPrn=strNomImpBod_ordDesTuv;
                    }

                }
                else if(intCodEmp_Imp==2){
                    if(intCodLoc_Imp==9){
                        strRepUtiEmp=strRutaRpt8;
                        strNomPrn=strNomImpBod_ordDesCasUio;
                    }
                }
                else if(intCodEmp_Imp==4){
                    if(intCodLoc_Imp==4){
                        strRepUtiEmp=strRutaRpt10;
                        strNomPrn=strNomImpBod_ordDesDim;
                    }
                }


                strSQLRep="";
                strSQLRep+="SELECT d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, d1.feimpguia, d1.fe_ing, d1.obs2,";
                strSQLRep+=" d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem, d1.tx_ptopar, ";
                strSQLRep+=" d1.empresa, d1.tx_telclides, d1.tx_rucclides, d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, ";
                strSQLRep+=" d1.co_clides, d1.tx_nomven, d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar,";
                strSQLRep+=" d1.cuidad, d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil,";
                strSQLRep+=" d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                strSQLRep+=" FROM ( SELECT";
                strSQLRep+="        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc,";
                strSQLRep+="        CURRENT_TIMESTAMP as feimpguia, b1.fe_ing, trim(b1.tx_obs2) as obs2,";
                strSQLRep+="        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.tx_datdocoriguirem,";
                strSQLRep+="        b1.tx_ptopar, b1.tx_telclides, b1.tx_rucclides,";
                strSQLRep+="        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                strSQLRep+="        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar,";
                strSQLRep+="        b1.tx_ciuclides as cuidad,";
                strSQLRep+="        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                strSQLRep+="        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                strSQLRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLRep+=" 	) as nd_kgr,";
                strSQLRep+="        (";
                strSQLRep+=" 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                strSQLRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLRep+=" 	) as nd_kil";
                strSQLRep+=" 	, c3.co_emp AS co_empIngImp, c3.co_loc AS co_locIngImp, c3.co_tipDoc AS co_tipDocIngImp, c3.co_doc AS co_docIngImp, c3.tx_numdoc2 AS tx_numIngImp";
                strSQLRep+="        FROM (tbm_cabguirem as b1 INNER JOIN tbm_detGuiRem AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQLRep+=" 			INNER JOIN tbm_detMovInv AS b3 ON b2.co_empRel=b3.co_emp AND b2.co_locRel=b3.co_loc AND b2.co_tipDocRel=b3.co_tipDoc AND b2.co_docRel=b3.co_doc AND b2.co_regRel=b3.co_reg";
                strSQLRep+=" 			INNER JOIN tbm_cabMovInv AS c1 ON b3.co_Emp=c1.co_emp AND b3.co_loc=c1.co_loc AND b3.co_tipDoc=c1.co_tipDoc AND b3.co_doc=c1.co_doc";
                strSQLRep+=" 			INNER JOIN tbr_cabMovInv AS c2 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                strSQLRep+=" 			INNER JOIN tbm_cabMovInv AS c3 ON c2.co_empRel=c3.co_emp AND c2.co_locRel=c3.co_loc AND c2.co_tipDocRel=c3.co_tipDoc AND c2.co_docRel=c3.co_doc AND c3.co_tipDoc IN(14, 245)";
                strSQLRep+=" 	    )";
                strSQLRep+="        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                strSQLRep+="        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                strSQLRep+="        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                strSQLRep+="        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                strSQLRep+="        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                strSQLRep+="        WHERE b1.co_emp=" + intCodEmp_Imp + "";
                strSQLRep+="        and b1.co_loc=" + intCodLoc_Imp + "";
                strSQLRep+="        and b1.co_tipdoc=" + intCodTipDoc + "";
                strSQLRep+="        and b1.co_doc=" + intCodDoc + "";
                strSQLRep+="     ) as d1";
                strSQLRep+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc,";
                strSQLRep+=" d1.feimpguia, d1.fe_ing, d1.obs2,";
                strSQLRep+=" d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem,";
                strSQLRep+=" d1.tx_ptopar, d1.empresa, d1.tx_telclides, d1.tx_rucclides,";
                strSQLRep+=" d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, d1.co_clides, d1.tx_nomven,";
                strSQLRep+=" d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar, d1.cuidad,";
                strSQLRep+=" d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil, d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                System.out.println("strSQLRep: " + strSQLRep);

                strSQLSubRep="";
                strSQLSubRep+="select *";
                strSQLSubRep+=" from ( select";
                strSQLSubRep+="        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b2.co_reg,";
                strSQLSubRep+="        current_timestamp as feimpguia, b1.fe_ing, b2.tx_obs1, trim(b1.tx_obs2) as obs2,";
                strSQLSubRep+="        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.fe_ing, b1.tx_datdocoriguirem,";
                strSQLSubRep+="        b1.tx_ptopar, b8.tx_nom  as empresa, b1.tx_telclides, b1.tx_rucclides,";
                strSQLSubRep+="        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                strSQLSubRep+="        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar, b2.tx_codalt, b5.tx_codalt2,";
                strSQLSubRep+="        b2.tx_nomitm, abs(b2.nd_can) AS nd_can, b2.tx_unimed, b1.tx_ciuclides as cuidad,";
                strSQLSubRep+="        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                strSQLSubRep+="        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                strSQLSubRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLSubRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLSubRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLSubRep+=" 	) as nd_kgr,";
                strSQLSubRep+="        (";
                strSQLSubRep+=" 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                strSQLSubRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLSubRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLSubRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLSubRep+=" 	) as nd_kil";
                strSQLSubRep+="        FROM tbm_cabguirem as b1 INNER JOIN tbm_detguirem as b2";
                strSQLSubRep+="        ON(b1.co_emp=b2.co_emp and b1.co_loc=b2.co_loc and b1.co_tipdoc=b2.co_tipdoc and b1.co_doc=b2.co_doc)";
                strSQLSubRep+="        inner join tbm_detmovinv as b3";
                strSQLSubRep+="        on (b3.co_emp=b2.co_emprel and b3.co_loc=b2.co_locrel and b3.co_tipdoc=b2.co_tipdocrel and b3.co_doc=b2.co_docrel and b3.co_reg=b2.co_regrel )";
                strSQLSubRep+="        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                strSQLSubRep+="        inner join tbm_inv as b5 on(b2.co_emp=b5.co_emp and b2.co_itm=b5.co_itm)";
                strSQLSubRep+="        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                strSQLSubRep+="        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                strSQLSubRep+="        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                strSQLSubRep+="        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                strSQLSubRep+="        WHERE b1.co_emp=" + intCodEmp_Imp + "";
                strSQLSubRep+="        and b1.co_loc=" + intCodLoc_Imp + "";
                strSQLSubRep+="        and b1.co_tipdoc=" + intCodTipDoc + "";
                strSQLSubRep+="        and b1.co_doc=" + intCodDoc + "";
                strSQLSubRep+="     ) as x";
                strSQLSubRep+=" order by co_reg";
                System.out.println("strSQLSubRep: " + strSQLSubRep);

                DIRECCION_REPORTE_GUIA=strDirSis+strRepUtiEmp;
                System.out.println("DIRECCION_REPORTE_GUIA ->  "+DIRECCION_REPORTE_GUIA );
                System.out.println("Ruta Reporte OD  -  impresionOrdDesImp   ->  "+DIRECCION_REPORTE_GUIA );
                Map parameters = new HashMap();
                parameters.put("co_emp", new Integer(intCodEmp_Imp) );
                parameters.put("co_loc", new Integer(intCodLoc_Imp) );
                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
                parameters.put("co_doc",  new Integer(intCodDoc) );
                parameters.put("strSQLRep", strSQLRep);
                parameters.put("strSQLSubRep", strSQLSubRep);
                //parameters.put("SUBREPORT_DIR", (strDirSis + "/"));
                parameters.put("SUBREPORT_DIR", (strDirSis + "/Reportes/Importaciones/ZafImp06/"));
                System.out.println("SUBREPORT_DIR ->  "+(strDirSis + "/Reportes/Importaciones/ZafImp06/") );

                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con1);
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

                System.out.println("Se imprimio la OD");
            }
        }
        catch (JRException e) {
            almacenarArchHis("Error en imprimir impresionOrdDesImp-JRException: "+e.toString() );
            objEnvMail.enviarCorreo(" Error en imprimir impresionOrdDesImp-JRException: "+e.toString() );
        }
        catch (Exception e) {
            almacenarArchHis("Error en imprimir impresionOrdDesImp: "+e.toString() );
            objEnvMail.enviarCorreo(" Error en imprimir impresionOrdDesImp: "+e.toString() );
        }
        return true;
    }



    private int getBodegaOrigenDestino_Inmaconsa(Connection con1, int intCodEmp_Imp, int intCodBodEmpTrs){
        boolean blnRes=true;
        Statement stm11;
        ResultSet rst11;
        String strAux;
        intCodBod_OriDes_LocInm=-1;
        try{
            if(con1!=null){
                stm11=con1.createStatement();
                strAux="";
                strAux+="SELECT b1.* FROM(";
                strAux+=" 	SELECT a2.co_empGrp, a2.co_bodGrp, a1.*";
                strAux+="	FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strAux+="	ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strAux+="	WHERE a1.co_emp=" + intCodEmp_Imp + "";
                strAux+=") AS b1";
                strAux+=" INNER JOIN(";
                strAux+=" 	SELECT a2.co_empGrp, a2.co_bodGrp";
                strAux+=" 	FROM tbm_bod AS a1 INNER JOIN tbr_bodEmpBodGrp AS a2";
                strAux+="	ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strAux+="	WHERE a1.co_emp=" + intCodEmp_Imp + " AND a1.co_bod=" + intCodBodEmpTrs + "";
                strAux+=" ) AS b2";
                strAux+=" ON b1.co_empGrp=b2.co_empGrp AND b1.co_bodGrp=b2.co_bodGrp";
                rst11=stm11.executeQuery(strAux);
                if(rst11.next()){
                    intCodBod_OriDes_LocInm=rst11.getInt("co_bod");
                }
                stm11.close();
                stm11=null;
                rst11.close();
                rst11=null;
            }

        }
        catch(java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR SQL getBodegaLocalInmaconsa: "+e );
            objEnvMail.enviarCorreo("ERROR SQL getBodegaLocalInmaconsa: "+e.toString() );
        }
        catch(Exception e){
            blnRes=false;
            System.out.println("ERROR getBodegaLocalInmaconsa: "+e );
            objEnvMail.enviarCorreo("ERROR getBodegaLocalInmaconsa: "+e.toString() );
        }
        return intCodBod_OriDes_LocInm;
    }




    //FIN ORDENES DE DESPACHO





    //INICIO GUIAS DE REMISION
    /**
     * Función que permite crear la Guia de Remisión a partir de la Confirmación de Egresos
     * @return true: Si se pudo efectuar la operación
     * <BR> false: Caso contrario
     */
    public boolean generaGuiRemFin(){
        boolean blnRes=true;
        java.sql.Connection con1;
        java.sql.Statement stm1;
        java.sql.ResultSet rst1;
        try{
            con1=java.sql.DriverManager.getConnection(strStrConnDb, strUsrConnDb, strClaConnDb );
            if(con1!=null){
                stm1=con1.createStatement();
                strSQL="";
                strSQL+=" SELECT";
                strSQL+=" a1.co_emp as coempgui, a1.co_loc as colocgui, a1.co_tipdoc as cotipdocgui, a1.co_doc as codocgui";
                strSQL+=" ,a1.ne_numorddes, a.tx_tipcon, a.co_emp,a.co_loc,a.co_tipdoc,a.co_doc , a7.co_bod, a7.tx_nom";
                strSQL+=" FROM tbr_bodEmpBodGrp as a6";
                strSQL+=" INNER JOIN tbm_bod AS a7 ON(a7.co_emp=a6.co_emp AND a7.co_bod=a6.co_bod)";
                strSQL+=" INNER JOIN tbm_cabguirem AS a1 ON(a1.co_emp=a7.co_emp AND a1.co_ptopar=a7.co_bod)";
                strSQL+=" INNER JOIN tbm_cabingegrmerbod AS a ON( a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelguirem";
                strSQL+=" AND a1.co_tipdoc=a.co_tipdocrelguirem AND a1.co_doc=a.co_docrelguirem)";
                strSQL+=" WHERE (a6.co_empGrp=0 AND a6.co_bodGrp=" + intCodBodGrp + ")";
                strSQL+=" AND a1.ne_numdoc=0 AND a1.st_reg = 'A' AND a1.st_imporddes='S' AND a.st_reg='A' AND a.tx_tipCon IN('P','T')";
                strSQL+=" AND a.co_mnu=2205";
                System.out.println("generaGuiRemFin: " + strSQL);
                rst1=stm1.executeQuery(strSQL);
                while(rst1.next()){
                    crearGuiaRemFin( rst1.getInt("coempgui"),rst1.getInt("colocgui"), rst1.getInt("cotipdocgui"), rst1.getInt("cotipdocgui"), rst1.getInt("codocgui"), rst1.getInt("co_emp"), rst1.getInt("co_loc"), rst1.getInt("co_tipdoc"), rst1.getInt("co_doc"), intCodTipDocGui , true );
                }
                rst1.close();
                rst1=null;
                stm1.close();
                stm1=null;
                con1.close();
                con1=null;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return  blnRes;
    }


    /**
     * Función que permite crear la Guia de Remisión
     * @param intCodEmpGui
     * @param intCodLocGui
     * @param intCodTipDocGui
     * @param intCodTipDocGuiNue
     * @param intCodDocGui
     * @param intCodEmpConf
     * @param intCodLocConf
     * @param intCodTipDocConf
     * @param intCodDocConf
     * @param intTipDocGuiParaObtNum
     * @param blnObtDatGu
     * @return
     */
    private boolean crearGuiaRemFin( int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue, int intCodDocGui, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf, int intTipDocGuiParaObtNum, boolean blnObtDatGu ){
        boolean blnRes=false;
        java.sql.Connection connCen;
        java.sql.Statement stmCen, stmLoc;
        java.sql.ResultSet rstCen;
        int intCodDocGuiNue=0;
        int intNumGuiFin=0;
        try{
            connCen=java.sql.DriverManager.getConnection( strStrConnDb, strUsrConnDb, strClaConnDb );
            if(connCen!=null){
                connCen.setAutoCommit(false);
                stmCen=connCen.createStatement();
                stmLoc=connCen.createStatement();

                strSQL="";
                strSQL+="SELECT CASE WHEN (MAX(co_doc)+1) IS NULL THEN 1 ELSE (MAX(co_doc)+1) END AS co_doc";
                strSQL+=" FROM tbm_cabguirem";
                strSQL+=" WHERE co_emp=" + intCodEmpGui + "";
                strSQL+=" AND co_loc=" + intCodLocGui + "";
                strSQL+=" AND co_tipDoc="+intCodTipDocGuiNue + "";
                rstCen=stmCen.executeQuery(strSQL);
                if(rstCen.next())
                    intCodDocGuiNue=rstCen.getInt("co_doc");

                strSQL="";
                strSQL+="SELECT CASE WHEN ne_ultdoc IS NULL THEN 1 ELSE ne_ultdoc+1 END AS ne_numGui";
                strSQL+=" FROM tbm_cabtipdoc WHERE co_emp=" + intCodEmpGui + "";
                strSQL+=" AND co_loc=" + intCodLocGui + " AND co_tipdoc=" + intTipDocGuiParaObtNum + "";
                rstCen=stmCen.executeQuery(strSQL);
                if(rstCen.next())
                    intNumGuiFin=rstCen.getInt("ne_numGui");

                strSQL="";
                strSQL+="UPDATE tbm_cabtipdoc";
                strSQL+=" SET ne_ultdoc=" + intNumGuiFin + "";
                strSQL+=" WHERE co_emp=" + intCodEmpGui + " AND co_loc=" + intCodLocGui + "";
                strSQL+=" and co_tipdoc="+intTipDocGuiParaObtNum+"";
                strSQL+=";";
                stmLoc.executeUpdate(strSQL);




                if( insertarCabGuiaRemFin( connCen, intCodEmpGui, intCodLocGui, intCodTipDocGui, intCodTipDocGuiNue, intCodDocGui, intCodDocGuiNue, intNumGuiFin, intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, blnObtDatGu ) ){
                    if( insertarDetGuiaRemFin( connCen, intCodEmpConf, intCodLocConf, intCodTipDocConf, intCodDocConf, intCodTipDocGuiNue, intCodDocGuiNue ) ){
                        connCen.commit();
                        almacenarArchHis(" Num Guia. " + intNumGuiFin  );
                        if(strImpRptBod2.equals("S"))
                            impresionGuiRemImp(connCen, intCodEmpGui, intCodLocGui, intCodTipDocGuiNue, intCodDocGuiNue );
                    }
                    else {
                        connCen.rollback(); }
                    }
                else{
                    connCen.rollback();
                }
                stmLoc.close();
                stmLoc=null;
                stmCen.close();
                stmCen=null;
                connCen.close();
                connCen=null;
                rstCen.close();
                rstCen=null;

                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return  blnRes;
    }

    private boolean insertarCabGuiaRemFin(java.sql.Connection conn, int intCodEmpGui, int intCodLocGui, int intCodTipDocGui, int intCodTipDocGuiNue, int intCodDocGui, int intCodDocGuiNue, int intNumGuiFin, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf, boolean blnObtDatGu ){
        boolean blnRes=false;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc;
        try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                stmLoc01=conn.createStatement();

                if(blnObtDatGu){
                    strSQL="";
                    strSQL+="SELECT * FROM tbm_cabguirem WHERE co_emp=" + intCodEmpGui + "";
                    strSQL+=" AND co_loc=" + intCodLocGui + " AND co_tipdoc=" + intCodTipDocGui + "";
                    strSQL+=" AND co_doc=" + intCodDocGui + "";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a6.tx_ptopar, a6.nd_pestotkgr, a6.tx_datdocoriguirem, a6.co_ptopar,";
                    strSQL+=" a6.ne_numorddes, a6.co_forRet, a6.tx_vehRet, a6.tx_choRet";
                    strSQL+=" ,a6.co_ven, a6.tx_nomven, a6.tx_numped, a6.co_ptodes";
                    strSQL+=" ,null as co_clides, a5.tx_nomcli as tx_nomclides, a5.tx_dircli as tx_dirclides,";
                    strSQL+=" a5.tx_telcli AS tx_telclides, a5.tx_ruc AS tx_rucclides, a5.tx_ciucli AS tx_ciuclides";
                    strSQL+=" FROM tbm_cabguirem AS a6";
                    strSQL+=" INNER JOIN tbm_detguirem AS a";
                    strSQL+=" ON(a.co_emp=a6.co_emp AND a.co_loc=a6.co_loc AND a.co_tipdoc=a6.co_tipdoc AND a.co_doc=a6.co_doc)";
                    strSQL+=" INNER JOIN tbm_detmovinv AS a1";
                    strSQL+=" ON(a1.co_emp=a.co_emprel AND a1.co_loc=a.co_locrel AND a1.co_tipdoc=a.co_tipdocrel AND a1.co_doc=a.co_docrel AND a1.co_reg=a.co_regrel)";
                    strSQL+=" INNER JOIN tbr_detmovinv AS a2";
                    strSQL+=" ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipdoc=a1.co_tipdoc AND a2.co_doc=a1.co_doc AND a2.co_reg=a1.co_reg)";
                    strSQL+=" INNER JOIN tbm_detmovinv AS a3";
                    strSQL+=" ON(a3.co_emp=a2.co_emprel AND a3.co_loc=a2.co_locrel AND a3.co_tipdoc=a2.co_tipdocrel AND a3.co_doc=a2.co_docrel AND a3.co_reg=a2.co_regrel)";
                    strSQL+=" INNER JOIN tbr_detmovinv AS a4";
                    strSQL+=" ON(a4.co_emprel=a3.co_emp AND a4.co_locrel=a3.co_loc AND a4.co_tipdocrel=a3.co_tipdoc";
                    strSQL+=" AND a4.co_docrel=a3.co_doc AND a4.co_regrel=a3.co_reg AND a4.co_emp=a3.co_emp)";
                    strSQL+=" INNER JOIN tbm_cabmovinv AS a5";
                    strSQL+=" ON(a5.co_emp=a4.co_emp AND a5.co_loc=a4.co_loc AND a5.co_tipdoc=a4.co_tipdoc AND a5.co_doc=a4.co_doc)";
                    strSQL+=" WHERE a6.co_emp=" + intCodEmpGui + " AND a6.co_loc=" + intCodLocGui + "";
                    strSQL+=" AND a6.co_tipdoc="+intCodTipDocGui+" AND a6.co_doc="+intCodDocGui+"";
                }
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){

                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabguirem(co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_initra, fe_tertra,";
                    strSQL+=" ne_numdoc, tx_ptopar, co_clides, tx_rucclides, tx_nomclides, tx_dirclides, tx_telclides,";
                    strSQL+=" tx_ciuclides, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, st_reg, fe_ing,";
                    strSQL+=" co_usring, tx_coming, tx_comultmod, co_ptopar, st_tipGuiRem, tx_datdocoriguirem";
                    strSQL+=" ,co_forRet,tx_vehRet,tx_choRet, co_ven, tx_nomven, tx_numped, co_ptodes, st_coninv, ne_numorddes)";
                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmpGui + ",";//co_emp
                    strSQL+="" + intCodLocGui + ",";//co_loc
                    strSQL+="" + intCodTipDocGuiNue + ",";//co_tipdoc
                    strSQL+="" + intCodDocGuiNue + ",";//co_doc
                    strSQL+=" CURRENT_DATE,";//fe_doc
                    strSQL+=" CURRENT_DATE,";//fe_initra
                    strSQL+=" (CURRENT_DATE + 3),";//fe_tertra
                    strSQL+="" + intNumGuiFin + ",";//ne_numdoc
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_ptopar")) + ",";//tx_ptopar
                    strSQL+="" + rstLoc.getString("co_clides") + ",";//co_clides
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_rucclides")) + ",";//tx_rucclides
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_nomclides")) + ",";//tx_nomclides
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_dirclides")) + ",";//tx_dirclides
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_telclides")) + ",";//tx_telclides
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_ciuclides")) + ",";//tx_ciuclides
                    strSQL+="" + rstLoc.getString("nd_pestotkgr") + ",";//nd_pestotkgr
                    strSQL+=" 'N',";//st_imp
                    strSQL+=" '',";//tx_obs1
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_datdocoriguirem")) + ",";//tx_obs2
                    strSQL+=" 'A',";//st_reg
                    strSQL+=" CURRENT_TIMESTAMP,";//fe_ing
                    strSQL+=" 1,";//co_usring
                    strSQL+=" '',";//tx_coming
                    strSQL+=" '',";//tx_comultmod
                    strSQL+="" + rstLoc.getString("co_ptopar") + ",";//co_ptopar
                    strSQL+=" 'S',";//st_tipGuiRem
                    strSQL+=" 'O.D-" + rstLoc.getString("ne_numorddes") + "',";//tx_datdocoriguirem
                    strSQL+="" + rstLoc.getString("co_forRet") + ",";//co_forRet
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_vehRet")) + ",";//tx_vehRet
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_choRet")) + ",";//tx_choRet
                    strSQL+="" + rstLoc.getString("co_ven") + ",";//co_ven
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_nomven")) + ",";//tx_nomven
                    strSQL+="" + objUti.codificar(rstLoc.getString("tx_numped")) + ",";//tx_numped
                    strSQL+="" + rstLoc.getString("co_ptodes") + ",";//co_ptodes
                    strSQL+="'C',";//st_coninv
                    strSQL+=" 0";//ne_numorddes
                    strSQL+=");";

                    strSQL+=" INSERT INTO tbr_cabguirem";
                    strSQL+="   (co_emp, co_loc, co_tipdoc, co_doc, co_locrel, co_tipdocrel, co_docrel, st_regrep)";
                    strSQL+="    VALUES(";
                    strSQL+="" + intCodEmpGui + ",";//co_emp
                    strSQL+="" + intCodLocGui + ",";//co_loc
                    strSQL+="" + intCodTipDocGuiNue + ",";//co_tipdoc
                    strSQL+="" + intCodDocGuiNue + ",";//co_doc
                    strSQL+="" + intCodLocGui + ",";//co_locrel
                    strSQL+="" + intCodTipDocGui + ",";//co_tipdocrel
                    strSQL+="" + intCodDocGui + ",";//co_docrel
                    strSQL+=" 'I'";//st_regrep
                    strSQL+=");";

                    strSQL+="UPDATE tbm_cabguirem";
                    strSQL+=" SET st_tieGuiSec='S'";
                    strSQL+=" WHERE co_emp=" + intCodEmpGui + " AND co_loc=" + intCodLocGui + "";
                    strSQL+=" AND co_tipdoc=" + intCodTipDocGui + "";
                    strSQL+=" AND co_doc=" + intCodDocGui + "";
                    strSQL+=";";

                    strSQL+="UPDATE tbm_cabingegrmerbod";
                    strSQL+=" SET co_locrelguirem=" + intCodLocGui + ",";
                    strSQL+=" co_tipdocrelguirem=" + intCodTipDocGuiNue + ",";
                    strSQL+=" co_docrelguirem=" + intCodDocGuiNue + "";
                    strSQL+=" WHERE co_emp=" + intCodEmpConf + "";
                    strSQL+=" AND co_loc=" + intCodLocConf + "";
                    strSQL+=" AND co_tipdoc=" + intCodTipDocConf + "";
                    strSQL+=" AND  co_doc=" + intCodDocConf + "";
                    strSQL+=";";
                    stmLoc01.executeUpdate(strSQL);
                }
                rstLoc.close();
                rstLoc=null;

                stmLoc.close();
                stmLoc=null;
                stmLoc01.close();
                stmLoc01=null;
                blnRes=true;
            }
        }
        catch(java.sql.SQLException Evt){
            blnRes=false;
            System.out.println(""+Evt.toString() );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        catch(Exception Evt){
            blnRes=false;
            System.out.println(""+Evt.toString() );
            objEnvMail.enviarCorreo(" "+Evt.toString() );
        }
        return blnRes;
    }

/**
 * REGISTRA DETALLE DE GUIA DE REMISION.
 */
        private boolean insertarDetGuiaRemFin(java.sql.Connection conn, int intCodEmpConf, int intCodLocConf, int intCodTipDocConf, int intCodDocConf, int intCodTipDocGuiNue, int intCodDocGuiNue ){
            boolean blnRes=false;
            java.sql.Statement stmLoc, stmLoc01;
            java.sql.ResultSet rstLoc;
            try{
                if(conn!=null){
                    stmLoc=conn.createStatement();
                    stmLoc01=conn.createStatement();

                    strSQL="";
                    strSQL+="SELECT * FROM(";
                    strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a.co_reg AS coregconf,";
                    strSQL+="       a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a1.tx_unimed, a1.nd_pestotkgr, abs(a.nd_can) AS canegr,";
                    strSQL+="       a1.st_meregrfisbod, abs(COALESCE(a.nd_cannunrec,0)) AS cannunrec, COALESCE((abs(a.nd_can)+abs(a.nd_cannunrec)),0) AS cantotconf";
                    strSQL+="       ,case when a1.st_meregrfisbod = 'S' then  abs(a.nd_can)  else   (abs(a1.nd_cantotguisec)-abs(a1.nd_cannunrec))  end AS canconf";
                    strSQL+="       ,a1.tx_obs1";
                    strSQL+="       FROM tbm_detingegrmerbod AS a";
                    strSQL+="       INNER JOIN tbm_detguirem AS a1";
                    strSQL+="       ON(a1.co_emp=a.co_emp AND a1.co_loc=a.co_locrelguirem AND a1.co_tipdoc=a.co_tipdocrelguirem AND a1.co_doc=a.co_docrelguirem AND a1.co_reg=a.co_regrelguirem)";
                    strSQL+="       WHERE a.co_emp=" + intCodEmpConf + " AND a.co_loc=" + intCodLocConf + "";
                    strSQL+="       AND a.co_tipdoc="+intCodTipDocConf+" AND a.co_doc= "+intCodDocConf+"";
                    strSQL+=") AS x   ";
                    strSQL+=" WHERE  canconf > 0";
                    strSQL+=" ORDER BY co_reg";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){

                        strSQL="";
                        strSQL+="INSERT INTO tbm_detguirem(co_emp, co_loc, co_tipdoc, co_doc, co_reg,";
                        strSQL+="co_itm, tx_codalt, tx_nomitm, tx_unimed, nd_can, st_regrep, nd_pestotkgr,";
                        strSQL+=" tx_obs1, st_meregrfisbod, nd_cannunrec, nd_cancon)";
                        strSQL+="VALUES (";
                        strSQL+="" + rstLoc.getInt("co_emp") + ",";//co_emp
                        strSQL+="" + rstLoc.getInt("co_loc") + ",";//co_loc
                        strSQL+="" + intCodTipDocGuiNue + ",";//co_tipdoc
                        strSQL+="" + intCodDocGuiNue + ",";//co_doc
                        strSQL+="" + rstLoc.getInt("coregconf") + ",";//co_reg
                        strSQL+="" + rstLoc.getString("co_itm") + ",";//co_itm
                        strSQL+="" + objUti.codificar(rstLoc.getString("tx_codalt")) + ",";//tx_codalt
                        strSQL+="" + objUti.codificar(rstLoc.getString("tx_nomitm")) + ",";//tx_nomitm
                        strSQL+="" + objUti.codificar(rstLoc.getString("tx_unimed")) + ",";//tx_unimed
                        strSQL+="" + rstLoc.getString("canconf") + ",";//nd_can
                        strSQL+=" 'I',";//st_regrep
                        strSQL+="" + rstLoc.getString("nd_pestotkgr") + ",";//nd_pestotkgr
                        strSQL+="" + objUti.codificar(rstLoc.getString("tx_obs1")) + ",";//tx_obs1
                        strSQL+="" + objUti.codificar(rstLoc.getString("st_meregrfisbod")) + ",";//st_meregrfisbod
                        strSQL+="" + rstLoc.getString("cannunrec") + ",";//nd_cannunrec
                        strSQL+="" + rstLoc.getString("canegr") + "";//nd_cancon
                        strSQL+=");";

                        strSQL+="INSERT INTO tbr_detguirem";
                        strSQL+=" (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locrel, co_tipdocrel, co_docrel, co_regrel, st_regrep)";
                        strSQL+=" VALUES(" + rstLoc.getInt("co_emp") + ",";//co_emp
                        strSQL+="" + rstLoc.getInt("co_loc") + ",";//co_loc
                        strSQL+="" + intCodTipDocGuiNue + ",";//co_tipdoc
                        strSQL+="" + intCodDocGuiNue + ",";//co_doc
                        strSQL+="" + rstLoc.getInt("coregconf") + ",";//co_reg
                        strSQL+="" + rstLoc.getInt("co_loc") + ",";//co_locrel
                        strSQL+="" + rstLoc.getInt("co_tipdoc") + ",";//co_tipdocrel
                        strSQL+="" + rstLoc.getInt("co_doc") + ",";//co_docrel
                        strSQL+="" + rstLoc.getInt("co_reg") + ",";//co_regrel
                        strSQL+=" 'I'";//st_regrep
                        strSQL+=");";

                        if(rstLoc.getString("st_meregrfisbod").equals("S")){
                            strSQL+=" UPDATE tbm_detguirem SET";
                            strSQL+="  nd_cantotguisec=nd_cantotguisec+" + rstLoc.getString("cantotconf") + "";
                            strSQL+=" WHERE co_emp=" + rstLoc.getInt("co_emp") + " AND co_loc=" + rstLoc.getInt("co_loc") + "";
                            strSQL+=" AND co_tipdoc=" + rstLoc.getInt("co_tipdoc") + "";
                            strSQL+=" AND co_doc=" + rstLoc.getInt("co_doc") + "  AND  co_reg=" + rstLoc.getInt("co_reg") + "";
                            strSQL+=";";
                            
                        }
                        strSQL+="UPDATE tbm_detingegrmerbod";
                        strSQL+=" SET co_locrelguirem=" + rstLoc.getInt("co_loc") + ",";
                        strSQL+=" co_tipdocrelguirem=" + intCodTipDocGuiNue + ", co_docrelguirem=" + intCodDocGuiNue + "";
                        strSQL+=" ,co_regrelguirem=" + rstLoc.getInt("coregconf") + "";
                        strSQL+=" WHERE co_emp=" + intCodEmpConf + " AND co_loc=" + intCodLocConf + "";
                        strSQL+=" AND co_tipdoc=" + intCodTipDocConf + " AND  co_doc=" + intCodDocConf + "";
                        strSQL+=" AND co_reg=" + rstLoc.getInt("coregconf") + "";
                        strSQL+=";";

                        System.out.println("insertarDetGuiaRemFin: " + strSQL);
                        stmLoc01.executeUpdate(strSQL);
                    }
                    rstLoc.close();
                    rstLoc=null;

                    stmLoc.close();
                    stmLoc=null;
                    stmLoc01.close();
                    stmLoc01=null;
                    blnRes=true;
                }
            }
            catch(SQLException Evt){
                blnRes=false;
                System.out.println(""+Evt.toString() );
                objEnvMail.enviarCorreo(strSQL + " "+Evt.toString() );
            }
            catch(Exception Evt){
                blnRes=false;
                System.out.println(""+Evt.toString() );
                objEnvMail.enviarCorreo(strSQL + " "+Evt.toString() );
            }
            return blnRes;
        }


    //1.- IMPRESION
    private boolean impresionGuiRemImp(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
        String strRepUtiEmp="";
        String strNomPrn="";
        String strSQLRep="";
        String strSQLSubRep="";
        try{
            if(conn!=null){
                strDirSis= getDirectorioSistema();

                if(intCodEmp==1){
                    if(intCodLoc==6){
                        strRepUtiEmp=strRutaRpt7;
                        strNomPrn=strNomImpBod_guiRemTuv;
                    }
                }
                else if(intCodEmp==2){
                    if(intCodLoc==9){
                        strRepUtiEmp=strRutaRpt9;
                        strNomPrn=strNomImpBod_guiRemCasUio;
                    }
                }
                else if(intCodEmp==4){
                    if(intCodLoc==4){
                        strRepUtiEmp=strRutaRpt11;
                        strNomPrn=strNomImpBod_guiRemDim;
                    }
                }

                strSQLRep="";
                strSQLRep+="SELECT b1.fe_initra, b1.fe_tertra, b1.NumSri, b1.obs2, b1.tx_numped, b1.tx_nom, b1.tx_descor,";
                strSQLRep+="         b1.fe_ing,b1.tx_datdocoriguirem,b1.co_ptopar,b1.tx_ptopar,	b1.puntopartida, b1.tx_telclides,";
                strSQLRep+=" 	b1.tx_rucclides,b1.ne_numdoc,b1.fe_doc,b1.tx_nomclides,b1.co_clides,b1.tx_nomven,b1.tx_dirclides,";
                strSQLRep+=" 	b1.tx_vehret,b1.tx_choret,b1.tx_deslar,	b1.cuidad, b1.contri1, b1.empresa, b1.kgramo, b1.kilo";
                strSQLRep+=" FROM(";
                strSQLRep+=" 	SELECT CASE WHEN inv.nd_pesitmkgr IS NULL THEN '(P0)' ELSE CASE  WHEN inv.nd_pesitmkgr <= 0 THEN '(P0)' else '' END END AS tiepes,";
                strSQLRep+=" 	DocCab.fe_initra, DocCab.fe_tertra, DocCab.fe_ing,";
                strSQLRep+=" 	(";
                strSQLRep+=" 		SELECT ( x5.tx_descor || '     ' || x4.tx_numautsri ) AS NumSri FROM tbm_cabguirem AS x";
                strSQLRep+=" 		INNER JOIN tbr_cabguirem as x1 ON (x1.co_emp=x.co_emp AND x1.co_loc=x.co_loc AND x1.co_tipdoc=x.co_tipdoc AND x1.co_doc=x.co_doc )";
                strSQLRep+=" 		INNER JOIN tbm_detguirem as x2 ON (x2.co_emp=x1.co_emp AND x2.co_loc=x1.co_locrel AND x2.co_tipdoc=x1.co_tipdocrel AND x2.co_doc=x1.co_docrel )";
                strSQLRep+=" 		INNER JOIN tbm_cabmovinv as x3 ON (x3.co_emp=x2.co_emprel AND x3.co_loc=x2.co_locrel AND x3.co_tipdoc=x2.co_tipdocrel AND x3.co_doc=x2.co_docrel )";
                strSQLRep+=" 		INNER JOIN tbm_datautsri as x4 ON (x4.co_emp=x3.co_emp AND x4.co_loc=x3.co_loc AND x4.co_tipdoc=x3.co_tipdoc )";
                strSQLRep+=" 		INNER JOIN tbm_cabtipdoc as x5 ON (x5.co_emp=x4.co_emp AND x5.co_loc=x4.co_loc AND x5.co_tipdoc=x4.co_tipdoc )";
                strSQLRep+=" 		where x.co_emp=DocCab.co_emp and x.co_loc=DocCab.co_loc AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc= DocCab.co_doc";
                strSQLRep+=" 		AND x3.ne_numdoc BETWEEN x4.ne_numdocdes AND x4.ne_numdochas";
                strSQLRep+=" 		GROUP BY x5.tx_descor, x4.tx_numautsri";
                strSQLRep+="	) as NumSri,";
                strSQLRep+=" 	TRIM(DocCab.tx_obs2) as obs2,";
                strSQLRep+=" 	CASE WHEN DocCab.tx_numped IS NULL THEN '' else DocCab.tx_numped END AS tx_numped,";
                strSQLRep+=" 	emp.tx_nom, tipdoc.tx_descor,DocCab.tx_datdocoriguirem,DocCab.co_ptopar,DocCab.tx_ptopar,";
                strSQLRep+="	DocCab.co_ptopar AS puntopartida,DocCab.tx_telclides,";
                strSQLRep+=" 	DocCab.tx_rucclides,DocCab.ne_numdoc,DocCab.fe_doc,DocCab.tx_nomclides,DocCab.co_clides,DocCab.tx_nomven,DocCab.tx_dirclides,";
                strSQLRep+=" 	DocCab.tx_vehret,DocCab.tx_choret,a3.tx_deslar,";
                strSQLRep+=" 	DocCab.tx_ciuclides as cuidad,";
                strSQLRep+=" 	emp.tx_desconesp as contri1,";
                strSQLRep+=" 	emp.tx_nom  AS empresa,";
                strSQLRep+=" 	(	SELECT sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) AS kgramo FROM tbm_detguirem AS x";
                strSQLRep+=" 		INNER JOIN tbm_inv AS inv ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=DocCab.co_emp AND x.co_loc=DocCab.co_loc  AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc=DocCab.co_doc";
                strSQLRep+=" 	) AS kgramo,";
                strSQLRep+=" 	(	SELECT sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) AS kilo FROM tbm_detguirem AS x";
                strSQLRep+=" 		INNER JOIN tbm_inv AS inv ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=DocCab.co_emp AND x.co_loc=DocCab.co_loc  AND x.co_tipdoc=DocCab.co_tipdoc AND x.co_doc=DocCab.co_doc";
                strSQLRep+=" 	) AS kilo";
                strSQLRep+=" 	FROM tbm_cabguirem as DocCab";
                strSQLRep+=" 	INNER JOIN tbm_detguirem AS a2 ON (DocCab.co_emp=a2.co_emp AND DocCab.co_loc=a2.co_loc AND DocCab.co_tipdoc=a2.co_tipdoc AND DocCab.co_doc=a2.co_doc)";
                strSQLRep+=" 	LEFT OUTER JOIN tbm_var AS a3 ON (DocCab.co_forret = a3.co_reg )";
                strSQLRep+=" 	INNER JOIN tbm_inv AS inv on(a2.co_emp=inv.co_emp AND a2.co_itm=inv.co_itm)";
                strSQLRep+=" 	INNER JOIN tbm_loc AS loc ON ( doccab.co_emp = loc.co_emp AND  doccab.co_loc=loc.co_loc)";
                strSQLRep+=" 	INNER JOIN tbm_ciu AS ciu ON ( loc.co_ciu = ciu.co_ciu)";
                strSQLRep+=" 	INNER JOIN tbm_emp AS emp ON ( emp.co_emp = DocCab.co_emp)";
                strSQLRep+=" 	INNER JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = DocCab.co_emp AND tipdoc.co_loc=DocCab.co_loc AND tipdoc.co_tipdoc=DocCab.co_tipdoc)";
                strSQLRep+=" 	WHERE DocCab.co_emp=" + intCodEmp + "";
                strSQLRep+=" 	AND DocCab.co_loc=" + intCodLoc + "";
                strSQLRep+=" 	AND DocCab.co_tipdoc=" + intCodTipDoc + "";
                strSQLRep+=" 	AND DocCab.co_doc=" + intCodDoc + "";
                strSQLRep+=" 	ORDER BY a2.co_reg";
                strSQLRep+=" ) AS b1";
                strSQLRep+=" GROUP BY b1.fe_initra, b1.fe_tertra, b1.NumSri, b1.obs2,b1.tx_numped,b1.tx_nom,";
                strSQLRep+=" b1.tx_descor,b1.fe_ing,b1.tx_datdocoriguirem,b1.co_ptopar,b1.tx_ptopar,b1.puntopartida,";
                strSQLRep+=" b1.tx_telclides,b1.tx_rucclides,b1.ne_numdoc,b1.fe_doc,b1.tx_nomclides,b1.co_clides,b1.tx_nomven,";
                strSQLRep+=" b1.tx_dirclides,b1.tx_vehret,b1.tx_choret,b1.tx_deslar,b1.cuidad,b1.contri1,b1.empresa,b1.kgramo,b1.kilo";
                System.out.println("strSQLRep: " + strSQLRep);

                strSQLSubRep="";
                strSQLSubRep+="select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                strSQLSubRep+=" a2.tx_obs1,a2.tx_codalt, abs(a2.nd_can) as nd_can,a2.tx_unimed,";
                strSQLSubRep+=" CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm";
                strSQLSubRep+=" FROM tbm_cabguirem as DocCab";
                strSQLSubRep+=" INNER JOIN tbm_detguirem as a2 ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                strSQLSubRep+=" LEFT OUTER JOIN tbm_var as a3 ON (DocCab.co_forret = a3.co_reg )";
                strSQLSubRep+=" inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                strSQLSubRep+=" inner JOIN tbm_loc as loc ON ( doccab.co_emp = loc.co_emp and  doccab.co_loc=loc.co_loc)";
                strSQLSubRep+=" inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                strSQLSubRep+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = DocCab.co_emp)";
                strSQLSubRep+=" inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = DocCab.co_emp and tipdoc.co_loc=DocCab.co_loc and tipdoc.co_tipdoc=DocCab.co_tipdoc)";
                strSQLSubRep+=" WHERE DocCab.co_emp=" + intCodEmp + "";
                strSQLSubRep+=" and DocCab.co_loc=" + intCodLoc + "";
                strSQLSubRep+=" and DocCab.co_tipdoc=" + intCodTipDoc + "";
                strSQLSubRep+=" and DocCab.co_doc=" + intCodDoc + "";
                strSQLSubRep+=" order by a2.co_reg";
                System.out.println("strSQLSubRep: " + strSQLSubRep);

                DIRECCION_REPORTE_GUIA=strDirSis+strRepUtiEmp;
                System.out.println("Ruta Reporte - impresionGuiRemImp ->  "+DIRECCION_REPORTE_GUIA);
                Map parameters = new HashMap();
                parameters.put("strSQLRep", strSQLRep);
                parameters.put("strSQLSubRep", strSQLSubRep);
                parameters.put("SUBREPORT_DIR", (strDirSis + "/Reportes/Importaciones/ZafImp06/"));
                System.out.println("SUBREPORT_DIR ->  "+(strDirSis + "/Reportes/Importaciones/ZafImp06/") );
                System.out.println("strNomPrn: " + strNomPrn);

//**********************
                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH));//sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                //objPriReqAttSet.add(new PrinterResolution(240, 144, PrinterResolution.DPI));//objPriReqAttSet.add(new PrinterResolution(116, 116, ResolutionSyntax.DPCM));
                //objPriReqAttSet.add(PrintQuality.HIGH);

                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomPrn , null);//Locale.getDefault()

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



//*********************************

//                JasperPrint print = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
//                PrinterJob job = PrinterJob.getPrinterJob();
//                /* Create an array of PrintServices */
//                PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//                int selectedService = 0;
//                /* Scan found services to see if anyone suits our needs */
//                for(int i = 0; i < services.length;i++){
//                    System.out.println("service: " + services[i].getName().toUpperCase());
//                    if(services[i].getName().contains(strNomPrn)){
//                        /*If the service is named as what we are querying we select it */
//                        selectedService = i;
//                    }
//                }
//                job.setPrintService(services[selectedService]);
//                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//                MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);//8.5f, 11f, MediaSize.INCH
//
//                printRequestAttributeSet.add(mediaSizeName);
//                printRequestAttributeSet.add(new Copies(1));
//                JRPrintServiceExporter exporter;
//                exporter = new JRPrintServiceExporter();
//                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//                /* We set the selected service and pass it as a paramenter */
//                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
//                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
//                exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
//                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//                exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//                exporter.exportReport();



//**************************************************













                System.out.println("Se imprimio la GR");

            }
        }
        catch (JRException e){
            almacenarArchHis("impresionGuiRemImp-JRException: "+e.toString() );
            objEnvMail.enviarCorreo(" Error en imprimir en impresionGuiRemImp-JRException:  "+e.toString() );
        }
        catch (Exception e) {
            almacenarArchHis("impresionGuiRemImp: "+e.toString() );
            objEnvMail.enviarCorreo(" Error en imprimir en impresionGuiRemImp: "+e.toString() );
        }
        return true;
    }

    //FIN DE GUIAS DE REMISION

}