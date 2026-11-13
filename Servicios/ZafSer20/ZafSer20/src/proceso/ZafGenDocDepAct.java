/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author Sistemas6
 */
public class ZafGenDocDepAct {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
    
    private String strSql;
    private int CodTipDoc=-1, CodDocIns=-1;
    private ZafNotCorEle objNotCorEle;
    
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    private Double dblValorDiario=-1.00;
    
     public ZafGenDocDepAct(){
         
        objParSis = new ZafParSis();
        objNotCorEle = new ZafNotCorEle(objParSis);
        objUti = new ZafUtil();
        objAsiDia = new ZafAsiDia(objParSis);
        
    }
    
    
     /**
      * 
      * @return 
      */
     
    public boolean iniciaGeneracionDepreciacion(){
        boolean blnRes=false;
        try{
            if(iniciarProceso()){
                blnRes=true;
            }
        }
        catch(Exception Evt){ 
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }
    
    private java.util.Date datFecAux; 
    
    /**
     * 
     * @return 
     */
    
    private boolean iniciarProceso(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                CodTipDoc = getTipoDpcumentoDepreciacion(conLoc);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if(CodTipDoc==-1){
                    System.out.println("ERROR: JM: FALTA CONFIGURAR TIPO DE DOCUMENTO DEPRECIACION COMO AUTOMATICO....");
                    return false;
                }
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_actEmp, a1.tx_desLar, a1.tx_carAct,a1.nd_valAct, a1.nd_valRes, a1.nd_valDepAct,  \n";
                strSql+="        a2.co_tipAct, a2.tx_desLar, a2.nd_porDep, a2.nd_durDep , a1.fe_actDebSerDep, a1.ne_numDep, a1.ne_numDepGen,  \n";
                strSql+="        a3.co_cta, a4.tx_desLar as tx_nomCta, a4.tx_codCta \n";
                strSql+=" FROM tbm_cabActEmp as a1   \n";
                strSql+=" INNER JOIN tbm_tipAct as a2 ON (a1.co_tipAct=a2.co_tipAct) \n";
                strSql+=" INNER JOIN tbr_tipActCta as a3 ON (a1.co_emp=a3.co_emp AND a1.co_tipAct=a3.co_tipAct)  \n";
                strSql+=" INNER JOIN tbm_plaCta as a4 ON (a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta) \n";
                strSql+=" WHERE a1.st_reg='A' and a1.st_actDebSerDep='S' AND a1.ne_numDep > CASE WHEN a1.ne_numDepGen IS NULL THEN 0 ELSE a1.ne_numDepGen END   \n";
                strSql+=" ORDER BY a1.co_emp, a1.co_actEmp   \n";
                System.out.println("iniciarProceso " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    dblValorDiario=-1.00;
                    vecDatDia=new Vector();
                    if(activoNoDepreciado(conLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_actEmp"))){
                        conLoc.setAutoCommit(false);
                        CodDocIns = getCodigoDocumentoInsertar(conLoc, rstLoc.getInt("co_emp"),CodTipDoc);
                        iniDia();
                        if(CodDocIns==-1){
                            System.out.println("ERROR: JM: ERROR AL INTENTAR OBTENER EL CODIGO DEL DOCUMENTO....");
                            return false;
                        }
                        if(insertarDocumentoDepreciacion(conLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_actEmp"), CodDocIns)){
                            if(insertarAsientoDiario(conLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_actEmp"), CodDocIns, rstLoc.getInt("co_cta"), rstLoc.getString("tx_nomCta"), rstLoc.getString("tx_codCta"),dblValorDiario)){
                                if(actualizarActivoFijo(conLoc, rstLoc.getInt("co_emp"), rstLoc.getInt("co_actEmp"))){
                                    conLoc.commit();
                                    System.out.println("OK!");
                                }
                                else{
                                    conLoc.rollback();
                                    System.out.println("ERROR: actualizarActivoFijo  ");
                                    return false;
                                }
                            }
                            else{
                                conLoc.rollback();
                                System.out.println("ERROR: insertarAsientoDiario  ");
                                return false;
                            }
                        }
                        else{
                            conLoc.rollback();
                            System.out.println("ERROR: insertarDocumentoDepreciacion ");
                            return false;
                        }
                    }

                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }

        }
        catch (SQLException Evt) {
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodTipDoc
     * @return 
     */

    
    private int getCodigoDocumentoInsertar(java.sql.Connection conExt, int CodEmp, int CodTipDoc){
        int intRes=-1, CodLoc=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                switch(CodEmp){
                    case 1: CodLoc=4; break;
                    case 2: CodLoc=1; break;
                    case 4: CodLoc=3; break;
                }
                strSql="";
                strSql+=" SELECT (CASE WHEN MAX(co_doc) IS NULL THEN 0 ELSE MAX(co_doc) END + 1) as co_doc   \n";
                strSql+=" FROM tbm_cabIngEgrActVal  \n";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("co_doc");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            intRes=-1;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            intRes=-1;
            System.out.println("Error 1 "+Evt ); 
        }
        return intRes;
    }
    
    /**
     * Inserta el asiento de diario a generar.
     * 
     * @param conExt
     * @param CodEmp
     * @param CodActEmp
     * @param CodDoc
     * @param Valor
     * @return 
     */
    
    private boolean insertarAsientoDiario(java.sql.Connection conExt, int CodEmp, int CodActEmp, int CodDoc, int CodCta, String strNomCta, String strCodCta, Double Valor){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        int CodLoc=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                switch(CodEmp){
                    case 1: CodLoc=4; break;
                    case 2: CodLoc=1; break;
                    case 4: CodLoc=3; break;
                }
                if(genDia(CodEmp, CodActEmp, CodCta, strNomCta, strCodCta, Valor )){
                    if(objAsiDia.insertarDiario(conExt, CodEmp, CodLoc, CodTipDoc, String.valueOf(CodDoc), String.valueOf(CodDoc), datFecAux)){
                        System.out.println("insertarAsientoDiario xD");
                        blnRes=true;
                    }
                }
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
                         
    }
    
    
            
            
            
    
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodActEmp
     * @return 
     */
    
    private boolean actualizarActivoFijo(java.sql.Connection conExt, int CodEmp, int CodActEmp){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabActEmp SET nd_valRes = a.nd_valResNEW , nd_valDepAct = a.depreMensual + a.nd_valDepAct,    \n";
                strSql+="                          ne_numDepGen = a.ne_numDepGen + 1  \n";
                strSql+=" FROM ( \n";
                strSql+="   SELECT a1.co_emp, a1.co_actEmp, ROUND((a1.nd_valAct * (a2.nd_porDep /100 ) ) / 12 ,2) as depreMensual,   \n";
                strSql+="          ROUND(a1.nd_valRes - ((a1.nd_valAct * (a2.nd_porDep /100 ) ) / 12),2) as nd_valResNEW, \n";
                strSql+="          CASE WHEN a1.nd_valDepAct IS NULL THEN 0 ELSE a1.nd_valDepAct END as nd_valDepAct,  \n";
                strSql+="          CASE WHEN a1.ne_numDepGen IS NULL THEN 0 ELSE a1.ne_numDepGen END as ne_numDepGen  \n";
                strSql+="   FROM tbm_cabActEmp as a1      \n";
                strSql+="   INNER JOIN tbm_tipAct as a2 ON (a1.co_tipAct=a2.co_tipAct)   \n";
                strSql+="   WHERE a1.co_emp="+CodEmp+" and a1.co_actEmp="+CodActEmp+"   \n";
                strSql+=" ) as a \n";
                strSql+=" WHERE tbm_cabActEmp.co_emp=a.co_emp AND tbm_cabActEmp.co_actEmp=a.co_actEmp ;  \n";
                strSql+=" \n";
                System.out.println("actualizarActivoFijo " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }
    
    
    /**
     * =(Valor Residual * 0.2)/12 
     * 
     * @param conExt
     * @param CodEmp
     * @param CodActEmp
     * @return 
     */
    
    private boolean insertarDocumentoDepreciacion(java.sql.Connection conExt, int CodEmp, int CodActEmp, int CodDoc){
        boolean blnRes=true;
        int CodLoc=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                
                switch(CodEmp){
                    case 1: CodLoc=4; break;
                    case 2: CodLoc=1; break;
                    case 4: CodLoc=3; break;
                }
                strSql="";
                strSql+="   SELECT a1.co_emp, a1.co_actEmp, ROUND((a1.nd_valRes * (a2.nd_porDep /100 ) ) / 12 ,2) as depreMensual,   \n";
                strSql+="          ROUND(a1.nd_valRes - ((a1.nd_valRes * (a2.nd_porDep /100 ) ) / 12),2) as nd_valResNEW, a2.nd_porDep \n";
                strSql+="   FROM tbm_cabActEmp as a1      \n";
                strSql+="   INNER JOIN tbm_tipAct as a2 ON (a1.co_tipAct=a2.co_tipAct)   \n";
                strSql+="   WHERE a1.co_emp="+CodEmp+" and a1.co_actEmp="+CodActEmp+"   \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    dblValorDiario = rstLoc.getDouble("depreMensual");
                }
                rstLoc.close();
                rstLoc=null;
                
                strSql="";
                strSql+=" INSERT INTO tbm_cabIngEgrActVal(co_emp, co_loc, co_tipDoc, co_doc, ne_numDoc, co_actEmp, fe_doc, nd_val, \n";
                strSql+="                                  ne_ani, ne_mes, st_reg, fe_ing, co_usrIng, nd_valExi, nd_porDep)   \n";
                strSql+=" SELECT a.co_emp, "+CodLoc+" as co_loc, "+CodTipDoc+" as co_tipDoc,   \n";
                strSql+=" "+CodDoc+" as co_doc, "+CodDoc+" as ne_numDoc, a.co_actEmp, current_date as fe_doc, (a.depreMensual*-1) as nd_val,   \n";
                strSql+=" EXTRACT(YEAR from current_date) as ne_ani, EXTRACT(MONTH from current_date) as ne_mes,  \n";
                strSql+="  'A' as st_reg, current_timestamp as fe_ing, 1 as co_usrIng,   a.nd_valResNEW as nd_valExi, a.nd_porDep   \n";
                strSql+=" FROM(  \n";
                strSql+="   SELECT a1.co_emp, a1.co_actEmp, ROUND((a1.nd_valAct * (a2.nd_porDep /100 ) ) / 12 ,2) as depreMensual,   \n";
                strSql+="          ROUND(a1.nd_valRes - ((a1.nd_valAct * (a2.nd_porDep /100 ) ) / 12),2) as nd_valResNEW, a2.nd_porDep \n";
                strSql+="   FROM tbm_cabActEmp as a1      \n";
                strSql+="   INNER JOIN tbm_tipAct as a2 ON (a1.co_tipAct=a2.co_tipAct)   \n";
                strSql+="   WHERE a1.co_emp="+CodEmp+" and a1.co_actEmp="+CodActEmp+"   \n";
                strSql+=" ) as a ;  \n";
                strSql+=" \n";
                System.out.println("insertarDocumentoDepreciacion " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }
    
    
    private int getTipoDpcumentoDepreciacion(java.sql.Connection conExt){
        int intRes=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" select * from tbm_cfgTipDocUtiProAut where co_cfg="+INT_CFG_TIP_DOC_DEP_ACT+" and co_emp=1 \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("co_tipDoc");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            intRes=-1;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            intRes=-1;
            System.out.println("Error 1 "+Evt ); 
        }
        return intRes;
    }
    
    
    
    final int INT_CFG_TIP_DOC_DEP_ACT=23;   
    
    /**
     * Metodo para revisar que no exista una depreciacion en el presente mes del Activo a revisar
     * @param conExt
     * @param CodEmp
     * @param CodActEmp
     * @return 
     */
    
    private boolean activoNoDepreciado(java.sql.Connection conExt, int CodEmp, int CodActEmp){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                
                strSql="";
                strSql+=" SELECT a2.tx_desLar, a1.*  \n";
                strSql+=" FROM tbm_cabIngEgrActVal as a1  \n";
                strSql+=" INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) \n";
                strSql+=" INNER JOIN tbm_cfgTipDocUtiProAut as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc) \n";
                strSql+=" WHERE  a1.co_emp="+CodEmp+" and a1.co_actEmp="+CodActEmp+" and a1.ne_mes=EXTRACT(MONTH from current_date) AND  \n";
                strSql+="        a1.ne_ani=EXTRACT(YEAR from current_date) and a3.co_cfg="+INT_CFG_TIP_DOC_DEP_ACT+"  \n";
                strSql+=" \n";
                System.out.println("activoNoDepreciado " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (SQLException Evt) {
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        catch(Exception Evt){ 
            blnRes=false;
            System.out.println("Error 1 "+Evt ); 
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite generar el diario cuando es un ingreso
     *  1;1824;"";"DEPRECIACIONES"8.01.03.01
        2;916;"";"DEPRECIACIONES"
        4;1756;"";"DEPRECIACIONES"
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDia(int codigoEmpresaDocumento, int CodActEmp, int CodCta, String strNomCta, String strCodCta, Double DblVal){
        boolean blnRes=true;
        try{
            //EMPRESA TUVAL - CLIENTE CASTEK
            System.out.println("DIARIO");
            System.out.println("codigoEmpresaDocumento:" + codigoEmpresaDocumento);
            System.out.println("Valor:" + DblVal);
            if(DblVal<0){
                System.out.println("ERROR EN VALOR DE DEPRECIACION..... ");
                return false;
            }
             
            if(codigoEmpresaDocumento==1){
            //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1824");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "8.01.03.01");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DEPRECIACIONES");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, CodCta+"");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strCodCta+"");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCta+"");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            else if(codigoEmpresaDocumento==2){
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "916");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "8.01.03.01");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DEPRECIACIONES");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, CodCta+"");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strCodCta+"");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCta+"");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            else if(codigoEmpresaDocumento==4){
                //Cuenta de debe
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, "1756");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, "8.01.03.01");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, "DEPRECIACIONES");
                vecRegDia.add(INT_VEC_DIA_DEB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_HAB, "0");
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
                //Cuenta de haber
                vecRegDia=new java.util.Vector();
                vecRegDia.add(INT_VEC_DIA_LIN, "");
                vecRegDia.add(INT_VEC_DIA_COD_CTA, CodCta+"");//intCodCtaTot
                vecRegDia.add(INT_VEC_DIA_NUM_CTA, strCodCta+"");
                vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                vecRegDia.add(INT_VEC_DIA_NOM_CTA, strNomCta+"");
                vecRegDia.add(INT_VEC_DIA_DEB, "0");
                vecRegDia.add(INT_VEC_DIA_HAB, "" + DblVal);
                vecRegDia.add(INT_VEC_DIA_REF, "");
                vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                vecDatDia.add(vecRegDia);
            }
            else{
                System.out.println("ERROR: LA EMPRESA ESTA MAL.... ni 1 2 4");
                return false;
            }
            
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
        }
        catch(Exception e){
            System.out.println("Error generaDiario "+e );  
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Inicia el Diario
     * @return 
     */
    
    
    private boolean iniDia(){
        boolean blnRes=true;
        try{
            objAsiDia.inicializar();
            vecDatDia.clear();
        }
        catch(Exception e){
            System.out.println("Error iniDia "+e );  
            blnRes=false;
        }
        return blnRes;
    }
}
