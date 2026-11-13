/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGenDocRev;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author JoseMario
 */
public class ZafGenDocRev {
    
    private java.awt.Component cmpPad;
    private String strVersion="ZafGenDocRev: v0.01", strSql;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafAsiDia objAsiDia;
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
    
    private Date dtpFecDoc;
    
    private int intUltCodDoc=-1, intUltNumDoc=-1;
    
    private final int INT_DAT_DOC_REV_GEN = 311 ; 
    private final int INT_DAT_DOC_REV_GEN_2 = 312 ;
    private final int INT_DAT_DOC_REV_GEN_3 = 313 ;
    
    private int intCodTipDocGen=-1;
    
    public ZafGenDocRev(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            objAsiDia = new ZafAsiDia(objParSis);
            vecDatDia=new Vector();
            System.out.println(strVersion);
        }
        catch (CloneNotSupportedException e){
            System.out.println("ZafGenDocRev: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }  
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    public boolean generaReversionDocumentoCierreDeCaja(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc ){
        boolean blnRes=false;
        try{
            if(conExt!=null){
                dtpFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if(iniDia()){
                    if(genDiaRev(CodEmp, CodLoc, CodTipDoc, CodDoc)){
                        if(getDatosDocumentoReversion(CodEmp, CodLoc, CodTipDoc, CodDoc)){
                            if(objAsiDia.insertarDiario(conExt, CodEmp, CodLoc, intCodTipDocGen, String.valueOf(intUltCodDoc), String.valueOf(intUltNumDoc), dtpFecDoc)){
                                if(insertarTBR_cabDia(conExt, CodEmp, CodLoc, CodTipDoc, CodDoc, intCodTipDocGen, intUltCodDoc)){
                                    if(modificarDocumentoReversado(conExt, CodEmp, CodLoc, CodTipDoc, CodDoc)){
                                        blnRes=true;
                                        System.out.println("generaReversionDocumentoCierreDeCaja OK!! xD");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    /**
     * Almacena la relación que existe entre un documento y otro(s) documento(es). Esta tabla se creó inicialmente para poder saber con qué documento se reversó un cierre de caja.
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @param CodTipDocRel
     * @param CodDocRel
     * @return 
     */
    
    private boolean insertarTBR_cabDia(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, int CodTipDocRel, int CodDocRel){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" INSERT INTO tbr_cabDia (co_emp, co_loc, co_tipDoc, co_dia, co_empRel, co_locRel, co_tipDocRel, co_diaRel)  \n";
                strSql+=" VALUES ("+CodEmp+", "+CodLoc+", "+CodTipDoc+", "+CodDoc+","+CodEmp+","+CodLoc+","+CodTipDocRel+", "+CodDocRel+" );  \n";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
       
         
         return blnRes;    
    }
    
    
    /**
     * TEMPORAL se deja quemado que todos estos documentos genreren R_CIECAJ 
     *  92;"CIECAJ";"Cierre de caja"        Cierres de caja, se revisan en tbm_cabDia   
     * 184;"CIECA2";"Cierre de caja 2"
     * 288;"CIECA3";"Cierre de caja 3"
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    private boolean getDatosDocumentoReversion(int CodEmp, int CodLoc, int CodTipDoc, int CodDia){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                
                switch(CodTipDoc){
                    case 92:  intCodTipDocGen = INT_DAT_DOC_REV_GEN;  break;
                    case 184: intCodTipDocGen = INT_DAT_DOC_REV_GEN_2; break;
                    case 288: intCodTipDocGen = INT_DAT_DOC_REV_GEN_3; break;
                    default: blnRes=false; break;
                }
                 
                strSql="";
                strSql+=" SELECT CASE WHEN (Max(co_dia)+1) IS NULL THEN 1 ELSE  (Max(co_dia)+1) END AS maximo \n";
                strSql+=" FROM tbm_cabDia as a1 \n";
                strSql+=" WHERE a1.co_emp ="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+intCodTipDocGen+ "  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intUltCodDoc = rstLoc.getInt("maximo");
                }
                rstLoc.close();
                rstLoc=null;
                
                strSql="";
                strSql+=" SELECT (a1.ne_ultDoc+1) as ne_ultDoc  \n";
                strSql+=" FROM tbm_cabTipDoc as a1  \n";
                strSql+=" WHERE a1.co_emp ="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+intCodTipDocGen+ "  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intUltNumDoc = rstLoc.getInt("ne_ultDoc");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception  e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite generar el diario de reversion del documento
     * @param codigoEmpresaDocumento Código de empresa del documento
     * @param codigoEmpresaCliente Código de empresa del cliente
     */
    private boolean genDiaRev(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){        
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg, a1.co_cta, a1.nd_monDeb, a1.nd_monHab, a2.tx_desLar, a2.tx_codCta \n";
                strSql+=" FROM tbm_detDia as a1  \n";
                strSql+=" INNER JOIN tbm_plaCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" AND a1.co_tipDoc="+CodTipDoc+" and a1.co_dia="+CodDoc+"  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(rstLoc.getDouble("nd_monHab")>0){
                        //Cuenta de debe
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, rstLoc.getString("co_cta"));//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, rstLoc.getString("tx_codCta"));
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, rstLoc.getString("tx_desLar"));
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + rstLoc.getString("nd_monHab"));
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else if(rstLoc.getDouble("nd_monDeb")>0){
                        //Cuenta de haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, rstLoc.getString("co_cta"));//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, rstLoc.getString("tx_codCta"));
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, rstLoc.getString("tx_desLar"));
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + rstLoc.getString("nd_monDeb"));
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                blnRes=true;
            }
            System.out.println("*detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setDetalleDiario(vecDatDia);
            System.out.println("***detalle diario:  " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    private boolean iniDia(){
        boolean blnRes=true;
        try{
            objAsiDia.inicializar();
            vecDatDia.clear();
        }
        catch(Exception e){
            System.out.println("Error - iniDia: " + e);
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    private boolean modificarDocumentoReversado(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabDia SET st_reg='I', tx_glo='Zafiro: Documento reversado...',  \n";
                strSql+="                       co_usrMod="+objParSis.getCodigoUsuario()+", fe_ultMod=CURRENT_TIMESTAMP ";
                strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_dia="+CodDoc+" ; ";
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
}
