/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 *
 * @author Sistemas6
 */
public class GeneraComisiones {
    
    private ZafParSis objParSis;
    private ZafNotCorEle objNotCorEle;
    private String strSql, strVersion="v 0.02", strSqlIns;
    
    private ArrayList arlDat, arlReg, arlConDatVen, arlConDatComVen, arlConDatVenLoc;
    
    private static final int INT_TBL_DAT_VEN=0;
    private static final int INT_TBL_DAT_VAL=1;
    private ZafUtil objUti;
    
    private static final int INT_TBL_DAT_INS_EMP=0;
    private static final int INT_TBL_DAT_INS_VEN=1;
    private static final int INT_TBL_DAT_INS_TBL_COM=2;
    private static final int INT_TBL_DAT_INS_POR_COM=3;
    private static final int INT_TBL_DAT_INS_VAL_FIJ=4;
    private static final int INT_TBL_DAT_INS_BAS_CAL=5;
    
    private static final int INT_CFG_BAS_CAL_COB_CLI=1;  // nd_stkAct
    
    
    
    private static final int INT_CFG_BAS_CAL_TOD_LOC=2;
    private static final int INT_CFG_BAS_CAL_LOC_NO_PS=3;
    
    
    
//    2;"COLO";"Cobros del local";"";"";"A"
//3;"COLONOPA";"Cobros del local menos Patricia Solorzano";"";"";"A"

    
    
    
    public GeneraComisiones(){
        System.out.println(strVersion);
        objParSis = new ZafParSis();
        objNotCorEle = new ZafNotCorEle(objParSis);
        objUti = new ZafUtil();
    }
    
    public boolean iniciaGeneracionComisiones(){
        boolean blnRes=false;
        try{
            if(ejecutaProceso()){
                blnRes=true;
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean ejecutaProceso(){
        boolean blnRes=false;
        try{
            if(obtenerFechasPeriodos()){
                if(eliminaPeriodo()){
                    if(procesoGenerarDatosComisiones()){
                        blnRes=true;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean eliminaPeriodo(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" DELETE FROM tbm_comVenPag WHERE co_per="+strPeriodo+" ; ";
                System.out.println(""+strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
     
    
    private String strFecDia1, strFecLastDay, strPeriodo;
    
    private boolean obtenerFechasPeriodos(){
        boolean blnRes=false;
        String strAux="", strMes;
        try{
            java.util.Calendar today, mesAnteriorDia1, mesAnteriorFinDia;
            today = java.util.Calendar.getInstance();  /* fecha de hoy  */ 
            mesAnteriorDia1 = java.util.Calendar.getInstance();  /* fecha de hoy  */ 
            mesAnteriorFinDia = java.util.Calendar.getInstance();  /* fecha de hoy  */  
            mesAnteriorDia1.add(Calendar.MONTH, -1);
            mesAnteriorDia1.set(Calendar.DAY_OF_MONTH, 1);
           
            mesAnteriorFinDia.add(Calendar.MONTH, -1);
            int days = mesAnteriorFinDia.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
            mesAnteriorFinDia.set(Calendar.DAY_OF_MONTH, days);

            strFecDia1 =""+(mesAnteriorDia1.getTime().getYear()+1900)+"-"+(mesAnteriorDia1.getTime().getMonth()+1)+"-01";
            strFecLastDay =""+(mesAnteriorFinDia.getTime().getYear()+1900)+"-"+(mesAnteriorFinDia.getTime().getMonth()+1)+"-"+days+"";

            System.out.println("FORMATEADAS1 " + strFecDia1 );
            System.out.println("FORMATEADAS FIN  " + strFecLastDay );
            
            strAux=(mesAnteriorDia1.getTime().getMonth()+1)+"";
            if(strAux.length()!=2){
                strMes=0+strAux;
            }
            else{
                strMes=strAux;
            }
            
            strPeriodo=""+(mesAnteriorFinDia.getTime().getYear()+1900)+""+strMes;
             
            blnRes=true;
                    
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean procesoGenerarDatosComisiones(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        int intCodEmp=-1 ;
        String strCodLoc="";
        double dblAcumLocal=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                stmLoc01 = conLoc.createStatement();
                for(int i=0;i<7;i++){
                    System.out.println(" EMPEZAMOS!!! " + i);
                    if(blnRes){
                        switch(i){
                            case 0: intCodEmp=1; strCodLoc="4,12"; break; /* Tuval */
                            case 1: intCodEmp=2; strCodLoc="1,11"; break; /* Castek Quito */
                            case 2: intCodEmp=2; strCodLoc="4,12"; break; /* Castek Manta */
                            case 3: intCodEmp=2; strCodLoc="6,13"; break; /* Castek StoDom */
                            case 4: intCodEmp=2; strCodLoc="10,14"; break; /* Castek Cuenca */
                            case 5: intCodEmp=4; strCodLoc="3,10"; break; /* Dimulti Via Daule */
                            case 6: intCodEmp=4; strCodLoc="12,13"; break; /* Dimulti Duran */
                        }
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ");
                        System.out.println(" Empresa:::: " + intCodEmp +" Locales: "+ strCodLoc);
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ");
                        /* Paso 1: obtener totales de venta por vendedor     */
                        arlConDatVen = new ArrayList();
                        strSql="";
                        strSql+=" SELECT a1.*, CASE WHEN a2.abono IS NULL THEN 0 ELSE a2.abono END as abono \n";
                        strSql+=" FROM ( \n";
                        strSql+="       SELECT a2.co_emp, a1.co_usr as co_ven, a1.tx_usr  \n";
                        strSql+="       FROM tbm_usr as a1   \n";
                        strSql+="       INNER JOIN tbr_usrEmp as a2 ON ( a1.co_usr=a2.co_usr)  \n";
                        strSql+="       INNER JOIN tbr_locUsr as a3 ON (a2.co_emp=a3.co_emp AND   a2.co_usr=a3.co_usr) \n";
                        strSql+="       INNER JOIN tbm_preAsiTblComVenUsrVen as a4 ON (a2.co_emp=a4.co_emp AND a2.co_usr=a4.co_usrVen)  \n";
                        strSql+="       WHERE a3.co_emp="+intCodEmp+" and a3.co_loc IN ("+strCodLoc+") AND a2.st_ven='S' and a1.st_reg='A' AND a2.st_reg='A'   \n";
                        strSql+="       GROUP BY a2.co_emp,  a1.co_usr, a1.tx_usr    \n";
                        strSql+="       UNION  \n";
                        strSql+="       SELECT "+intCodEmp+" as co_emp, co_usr, tx_usr  \n";
                        strSql+="       FROM tbm_usr WHERE co_usr=9   \n";
                        strSql+=" ) as a1   \n";
                        strSql+=" LEFT OUTER JOIN (  \n";
                        strSql+="   SELECT b1.co_emp , b1.co_ven, Round((SUM(b3.nd_aboModCxC))/1.12,2) as abono \n";
                        strSql+="   FROM( \n";
                        strSql+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag, a4.co_ven \n";
                        strSql+="           FROM tbm_cabMovInv AS a1 \n";
                        strSql+="           INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSql+="           INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                        strSql+="           INNER JOIN tbr_cliLoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cli=a4.co_cli) \n";
                        strSql+="           INNER JOIN tbm_cli AS b5 ON (a1.co_emp=b5.co_emp AND a1.co_cli=b5.co_cli) \n";
                        strSql+="           WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc IN ("+strCodLoc+") AND a1.co_tipDoc IN (1, 228) AND a1.st_reg IN ('A','R','C','F') AND a1.fe_doc>='2012/07/01' AND a3.st_ser<>'O' \n";
                        strSql+="                AND b5.co_empGrp IS NULL AND ((b5.co_emp=1 AND b5.co_cli<>3938) OR (b5.co_emp=2 AND b5.co_cli<>3080) OR (b5.co_emp=4 AND b5.co_cli<>3370)) /* EXCLUIR A COSENCO. */ \n";
                        strSql+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag, a4.co_ven \n";
                        strSql+="   ) AS b1 \n";
                        strSql+="   INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)	 \n";
                        strSql+="   INNER JOIN( \n";
                        strSql+="           SELECT a1.co_emp,a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag,  a2.nd_abo  AS nd_aboModCxC \n";
                        strSql+="           FROM tbm_cabPag AS a1 \n";
                        strSql+="           INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSql+="           INNER JOIN tbm_tipDocCalComVen as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                        strSql+="           WHERE a1.co_emp="+intCodEmp+" AND a1.st_reg='A' AND (a1.fe_doc BETWEEN '"+strFecDia1+"' AND '"+strFecLastDay+"') AND a2.st_reg='A' /* AND a3.st_reg IN ('A','R','C','F') */ \n";
                        strSql+="   ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag) \n";                       
                        strSql+="   WHERE b2.st_reg IN ('A','C')   \n";
                        strSql+="   GROUP BY b1.co_emp , b1.co_ven \n";
                        strSql+=" ) as a2 ON (a1.co_ven=a2.co_ven)  \n";
                        strSql+=" ORDER BY a1.co_ven "; 
                        System.out.println("Vendedores: " + strSql);
                        rstLoc = stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            arlReg = new ArrayList();
                            arlReg.add(INT_TBL_DAT_VEN, rstLoc.getString("co_ven"));
                            arlReg.add(INT_TBL_DAT_VAL, rstLoc.getString("abono"));
                            arlConDatVen.add(arlReg);
                        }
                        rstLoc.close();
                        rstLoc=null;
                        
                        dblAcumLocal=0;
                        System.out.println(">><><><><><><> "+arlConDatVen.toString());
                        
                        /* Paso 2: Obtener total del local, Tuval sin la Señora Patty, las demas empresas todo */
                         /* Solo en tuval las ventas de la señora Patty no son consideradas dentro de lo que vende el local */
                        for(int j=0; j<arlConDatVen.size(); j++){
                            if(objUti.getIntValueAt(arlConDatVen, j, INT_TBL_DAT_VEN)!=7){
                                dblAcumLocal+=objUti.getDoubleValueAt(arlConDatVen, j, INT_TBL_DAT_VAL);
                            }
                        }

                        System.out.println("TOTAL POR LOCAL: " + objUti.redondear(dblAcumLocal, objParSis.getDecimalesMostrar())); 
                        
                        arlConDatComVen = new ArrayList();  /* Para las ventas a clientes asignados */
                        arlConDatVenLoc = new ArrayList();  /* Para las comisiones por local */
                        /* Todos los vendedores que estan configurados obtenemos porcentajes a utilizar */
                        for(int k=0; k<arlConDatVen.size(); k++){
                            System.out.println("vendedor:::>>> "+objUti.getIntValueAt(arlConDatVen, k, INT_TBL_DAT_VEN));
                            strSql="";
                            strSql+=" SELECT a3.co_basCal, a3.tx_desLar \n";
                            strSql+=" FROM tbm_preAsiTblComVenUsrVen as a1  \n";
                            strSql+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)  \n";
                            strSql+=" INNER JOIN tbm_basCalComVen as a3 ON (a2.co_basCal = a3.co_basCal) \n";
                            strSql+="  WHERE a1.co_usrVen="+objUti.getIntValueAt(arlConDatVen, k, INT_TBL_DAT_VEN)+" and a1.st_reg='I' and a3.st_reg='A' \n";        
                            rstLoc = stmLoc.executeQuery(strSql);
                            while(rstLoc.next()){
                                System.out.println("co_basCal: " + rstLoc.getInt("co_basCal"));
                                if(rstLoc.getInt("co_basCal")==INT_CFG_BAS_CAL_COB_CLI){
                                    System.out.println("1");
                                    strSql="";
                                    strSql+=" SELECT a1.co_emp, a1.co_usrVen, a1.co_tblCom, a1.co_reg, a2.tx_desLar, a2.co_basCal, a2.st_reg,  \n";
                                    strSql+="        a3.nd_valMinCob, a3.nd_valMaxCob, a3.nd_porCom, a3.nd_valFij \n";
                                    strSql+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                                    strSql+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)  \n";
                                    strSql+=" INNER JOIN tbm_detTblComVen as a3 ON (a2.co_tblCom=a3.co_tblCom)  \n";
                                    strSql+=" WHERE a1.st_reg='I' AND a1.co_usrVen="+objUti.getIntValueAt(arlConDatVen, k, INT_TBL_DAT_VEN)+" AND  \n";
                                    strSql+="       a3.nd_valMinCob<="+objUti.getDoubleValueAt(arlConDatVen, k, INT_TBL_DAT_VAL)+" AND "
                                            + " CASE WHEN a3.nd_valMaxCob IS NULL THEN 999000.00 ELSE a3.nd_valMaxCob END >="+objUti.getDoubleValueAt(arlConDatVen, k, INT_TBL_DAT_VAL)+" AND "
                                            + " a2.co_basCal="+rstLoc.getInt("co_basCal")+" \n";
                                }
                                else if(rstLoc.getInt("co_basCal")==INT_CFG_BAS_CAL_TOD_LOC){
                                    System.out.println("2");
                                    strSql="";
                                    strSql+=" SELECT a1.co_emp, a1.co_usrVen, a1.co_tblCom, a1.co_reg, a2.tx_desLar, a2.co_basCal, a2.st_reg,  \n";
                                    strSql+="        a3.nd_valMinCob, a3.nd_valMaxCob, a3.nd_porCom, a3.nd_valFij \n";
                                    strSql+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                                    strSql+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)  \n";
                                    strSql+=" INNER JOIN tbm_detTblComVen as a3 ON (a2.co_tblCom=a3.co_tblCom)  \n";
                                    strSql+=" WHERE a1.st_reg='I' AND a1.co_usrVen="+objUti.getIntValueAt(arlConDatVen, k, INT_TBL_DAT_VEN)+" AND  \n";
                                    strSql+="       a3.nd_valMinCob<="+dblAcumLocal+" AND "
                                            + " CASE WHEN a3.nd_valMaxCob IS NULL THEN 999000.00 ELSE a3.nd_valMaxCob END >="+dblAcumLocal+" AND "
                                            + " a2.co_basCal="+rstLoc.getInt("co_basCal")+" \n";

                                }
                                else if(rstLoc.getInt("co_basCal")==INT_CFG_BAS_CAL_LOC_NO_PS){
                                    System.out.println("3");
                                    strSql="";
                                    strSql+=" SELECT a1.co_emp, a1.co_usrVen, a1.co_tblCom, a1.co_reg, a2.tx_desLar, a2.co_basCal, a2.st_reg,  \n";
                                    strSql+="        a3.nd_valMinCob, a3.nd_valMaxCob, a3.nd_porCom, a3.nd_valFij \n";
                                    strSql+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                                    strSql+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)  \n";
                                    strSql+=" INNER JOIN tbm_detTblComVen as a3 ON (a2.co_tblCom=a3.co_tblCom)  \n";
                                    strSql+=" WHERE a1.st_reg='I' AND a1.co_usrVen="+objUti.getIntValueAt(arlConDatVen, k, INT_TBL_DAT_VEN)+" AND  \n";
                                    strSql+="       a3.nd_valMinCob<="+dblAcumLocal+" AND "
                                            + "CASE WHEN a3.nd_valMaxCob IS NULL THEN 999000.00 ELSE a3.nd_valMaxCob END >="+dblAcumLocal+" AND "
                                            + " a2.co_basCal="+rstLoc.getInt("co_basCal")+" \n";
                                }
                                rstLoc01 = stmLoc01.executeQuery(strSql);
                                while(rstLoc01.next()){
                                    System.out.println("++>>> " +  rstLoc01.getString("co_usrVen")); System.out.println("++>>> " +  rstLoc01.getString("nd_porCom")); System.out.println("++>>> " +  rstLoc01.getString("co_tblCom"));
                                    if((rstLoc01.getInt("co_basCal")==INT_CFG_BAS_CAL_TOD_LOC) || ((rstLoc01.getInt("co_basCal")==INT_CFG_BAS_CAL_LOC_NO_PS))){
                                        System.out.println("1 Para el Local " + rstLoc01.getString("co_usrVen")+ " tblCom: " + rstLoc01.getString("co_tblCom"));
                                        arlReg = new ArrayList();
                                        arlReg.add(INT_TBL_DAT_INS_EMP, rstLoc01.getString("co_emp"));
                                        arlReg.add(INT_TBL_DAT_INS_VEN, rstLoc01.getString("co_usrVen"));
                                        arlReg.add(INT_TBL_DAT_INS_TBL_COM, rstLoc01.getString("co_tblCom"));
                                        arlReg.add(INT_TBL_DAT_INS_POR_COM, rstLoc01.getString("nd_porCom")); 
                                        arlReg.add(INT_TBL_DAT_INS_VAL_FIJ, rstLoc01.getString("nd_valFij"));   /* Valor fijo */
                                        arlReg.add(INT_TBL_DAT_INS_BAS_CAL, rstLoc01.getString("co_basCal"));   /* 1: Cobros a clientes asignados 2: Todo el local 3: todo el local sin PSolorzano */
                                        arlConDatVenLoc.add(arlReg);
                                    }
                                    else{
                                        System.out.println("2 Clientes asignados " + rstLoc01.getString("co_usrVen")+ " tblCom: " + rstLoc01.getString("co_tblCom"));
                                        arlReg = new ArrayList();
                                        arlReg.add(INT_TBL_DAT_INS_EMP, rstLoc01.getString("co_emp"));
                                        arlReg.add(INT_TBL_DAT_INS_VEN, rstLoc01.getString("co_usrVen"));
                                        arlReg.add(INT_TBL_DAT_INS_TBL_COM, rstLoc01.getString("co_tblCom"));
                                        arlReg.add(INT_TBL_DAT_INS_POR_COM, rstLoc01.getString("nd_porCom")); 
                                        arlReg.add(INT_TBL_DAT_INS_VAL_FIJ, rstLoc01.getString("nd_valFij"));   /* Valor fijo */
                                        arlReg.add(INT_TBL_DAT_INS_BAS_CAL, rstLoc01.getString("co_basCal"));  /* 1: Cobros a clientes asignados 2: Todo el local 3: todo el local sin PSolorzano */
                                        arlConDatComVen.add(arlReg);
                                    }
                                }
                                rstLoc01.close();
                                rstLoc01=null;
                            }
                            rstLoc.close();
                            rstLoc=null;
                        }  /* Fin For K */
                        
                        System.out.println(">arlConDatComVen >>>  "+arlConDatComVen.toString());
                        System.out.println(">arlConDatVenLoc >>>  "+arlConDatVenLoc.toString());

                        /* 
                            Paso 3: Obtener valores a cobrar y porcentajes a utilizar para el calculo de comisiones, de las tablas de configuracion de cada vendedor  
                                Dentro de mi RST, hay dos lazos, el primero es para sacar las ventas por cliente asignado, el segundo es para sacar las comisiones por 
                                local en caso que el vendedor lo posea
                        */
                        strSqlIns="";

                        strSql="";
                        strSql+=" SELECT b1.co_emp, b1.co_ven, /*b1.co_cli, b5.tx_nom,  b3.co_locPag, b3.co_tipDocPag, b3.co_docPag, \n";
                        strSql+="        b3.co_emp,*/ b3.co_loc, b3.co_tipDoc, b3.co_doc, b3.co_reg, /* b3.tx_desCor, b3.tx_desLar, */  \n";
                        strSql+="        Round((b3.nd_aboModCxC/1.12),2) as nd_aboModCxC   \n";
                        strSql+=" FROM( \n";
                        strSql+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag, a4.co_ven  \n";
                        strSql+="       FROM tbm_cabMovInv AS a1 \n";
                        strSql+="       INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSql+="       INNER JOIN tbm_inv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                        strSql+="       INNER JOIN tbr_cliLoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cli=a4.co_cli) \n";
                        strSql+="       INNER JOIN tbm_cli AS b5 ON (a1.co_emp=b5.co_emp AND a1.co_cli=b5.co_cli) \n";
                        strSql+="       WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc IN ("+strCodLoc+") AND a1.co_tipDoc IN (1, 228) AND a1.st_reg IN ('A','R','C','F') AND \n";
                        strSql+="              a1.fe_doc>='2012/07/01' AND a3.st_ser<>'O'   /* EXCLUIR LAS FACTURAS POR ITEMS DE SERVICIO \"O\" (Gestión de cobranzas, Intereses, etc.) */ \n";
                        strSql+="             AND b5.co_empGrp IS NULL AND ((b5.co_emp=1 AND b5.co_cli<>3938) OR (b5.co_emp=2 AND b5.co_cli<>3080) OR (b5.co_emp=4 AND b5.co_cli<>3370)) /* EXCLUIR A COSENCO. */ \n";
                        strSql+="       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.co_forPag, a4.co_ven  \n";
                        strSql+=" ) AS b1	 \n";
                        strSql+=" INNER JOIN tbm_pagMovInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc) \n";
                        strSql+=" INNER JOIN( \n";
                        strSql+=" /* QUERY PARA OBTENER EL VALOR PAGADO CON \"24=INCA; 183=INCA2; 18=DEPO; 39=DEPO2; 78=DEPOC; 79=DEPOD; 174=CVDCDB; 25=RETE; 26=RFIV30; 27=RFIV70; 73=RETE2%; 84=RFIV100; 195=COBTCM; 216=COBTCD; 247=RFIV10; 248=RFIV20\". */ \n";
                        strSql+="   SELECT a1.co_emp,a1.co_loc, a1.co_tipDoc, /* a4.tx_desCor, a4.tx_desLar,*/  a1.co_doc,  \n";
                        strSql+="          a2.co_reg, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag,  a2.nd_abo  AS nd_aboModCxC  \n";
                        strSql+="   FROM tbm_cabPag AS a1 \n";
                        strSql+="   INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)     \n";
                        strSql+="   INNER JOIN tbm_tipDocCalComVen as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)  \n";
                        strSql+="   WHERE a1.co_emp="+intCodEmp+"  AND /* a1.co_tipDoc IN (24, 183, 287, 18, 39, 78, 79, 174, 25, 26, 27, 73, 84, 247, 248, 195, 216) AND*/   \n";
                        strSql+="         a1.st_reg='A' AND (a1.fe_doc BETWEEN '"+strFecDia1+"' AND '"+strFecLastDay+"') AND a2.st_reg='A' /* AND a3.st_reg IN ('A','R','C','F') */ \n";
                        strSql+=" ) AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_loc=b3.co_locPag AND b2.co_tipDoc=b3.co_tipDocPag AND b2.co_doc=b3.co_docPag AND b2.co_reg=b3.co_regPag)	 \n";
                        strSql+=" WHERE b2.st_reg IN ('A','C')   \n";
                        strSql+=" ORDER BY b1.co_ven, b1.co_emp,b3.co_loc \n";
                        strSql+=" \n";
                        rstLoc = stmLoc.executeQuery(strSql);
                        while(rstLoc.next()){
                            for(int w=0;w<arlConDatComVen.size();w++){/* Aki voy a encontrar vendedores con varias tablas, puede cobrar al local o al cliente o los dos casos */
                                if(rstLoc.getInt("co_ven")==objUti.getIntValueAt(arlConDatComVen, w, INT_TBL_DAT_INS_VEN)){
                                    if(!armarDatos(rstLoc.getInt("co_emp"),rstLoc.getInt("co_ven"), 
                                            objUti.getIntValueAt(arlConDatComVen, w, INT_TBL_DAT_INS_TBL_COM), 
                                            rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipDoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_reg"),
                                            rstLoc.getDouble("nd_aboModCxC"), objUti.getStringValueAt(arlConDatComVen, w, INT_TBL_DAT_INS_POR_COM) ) ){
                                        blnRes=false;
                                    }
                                }
                            }
                            if(rstLoc.getInt("co_ven")!=7){
                                if(arlConDatVenLoc.size()>0){
                                    for(int e=0;e<arlConDatVenLoc.size();e++){
                                        if(!armarDatos(rstLoc.getInt("co_emp"),objUti.getIntValueAt(arlConDatVenLoc, e, INT_TBL_DAT_INS_VEN), 
                                                objUti.getIntValueAt(arlConDatVenLoc, e, INT_TBL_DAT_INS_TBL_COM), 
                                                rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipDoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_reg"),
                                                rstLoc.getDouble("nd_aboModCxC"), objUti.getStringValueAt(arlConDatVenLoc, e, INT_TBL_DAT_INS_POR_COM) ) ){
                                            blnRes=false;
                                        }
                                    }
                                }
                            }
                        }
                        rstLoc.close();
                        rstLoc=null;

                        /* Paso 4: insertar??? */
                        if(strSqlIns.length()>0){
                            if(!insertarCadena()){
                                blnRes=false;
                            }
                        }
                    }
                } /* SUPER FOR */
                stmLoc01.close();
                stmLoc01=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
        
    
    
    private boolean ejecutaUpdate(java.sql.Connection conExt){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                stmLoc.executeUpdate(strSqlIns);
                blnRes=true;
                strSqlIns="";
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean insertarCadena(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                if(ejecutaUpdate(conLoc)){
                    conLoc.commit();
                    blnRes=true;
                    System.out.println("OK");
                }
                else{
                    conLoc.rollback();
                    
                }
                strSqlIns="";
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            System.out.println("ERROR: " + e);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean armarDatos(int CodEmp,int CodVen,  int CodTblCom,  int CodLoc, int CodTipDoc, int CodDoc, int CodReg, double Abono, String PorcentajeCom ){
        boolean blnRes=true;
        BigDecimal bgdValAbo=new BigDecimal("0"); //Abonado
        BigDecimal bgdPorCom=new BigDecimal("0"); //porcentaje comision
        BigDecimal bgdValCom=new BigDecimal("0"); //Valor a comisionar 
        try{
             
            bgdValAbo=objUti.redondearBigDecimal(String.valueOf(Abono), objParSis.getDecimalesBaseDatos());
            bgdPorCom=objUti.redondearBigDecimal( PorcentajeCom, objParSis.getDecimalesBaseDatos());
            bgdValCom=objUti.redondearBigDecimal((bgdValAbo.multiply(bgdPorCom)),objParSis.getDecimalesBaseDatos());
            strSqlIns+=" INSERT INTO tbm_comvenpag(co_emp, co_per, co_usrven, co_tblcom, co_locreldetpag, co_tipdocreldetpag, ";
            strSqlIns+="                           co_docreldetpag, co_regreldetpag, nd_abo, nd_porcom, nd_valcom ) \n";
            strSqlIns+=" VALUES ("+CodEmp+", "+strPeriodo+" , "+CodVen+", "+CodTblCom+", "+CodLoc+", "+CodTipDoc+",  ";
            strSqlIns+=" "+CodDoc+", "+CodReg+", "+Abono+", "+PorcentajeCom+", "+bgdValCom.toString()+"   ); ";
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
         
    
    
}

 
   

    
    
 
