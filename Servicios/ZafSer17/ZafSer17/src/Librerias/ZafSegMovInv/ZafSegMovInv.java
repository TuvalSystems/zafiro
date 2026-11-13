/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafSegMovInv;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import javax.swing.JInternalFrame;

/**
 *
 * @author sistemas3
 */
public class ZafSegMovInv {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component jifCnt;
    private String strSQL="";
    private Statement stm;
    private ResultSet rst;
    
    public ZafSegMovInv(ZafParSis obj, java.awt.Component padre){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            jifCnt=padre;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(jifCnt, e);
        }
    }
    
   

    /**
     * Función que permite insertar en la tabla de seguimientos
     * @param con Conexión a la base de datos
     * @param codigoEmpresaSolicitud Código de empresa de la solicitud
     * @param codigoLocalSolicitud Código de local de la solicitud
     * @param codigoTipoDocumentoSolicitud Código de tipo de documento de la solicitud
     * @param codigoDocumentoSolicitud Código de documento de la solicitud
     * @param indiceTabla 
     *          <HTML>
     *              <BR>  0: Inserta en tabla "tbm_cabNotPedPreImp"
     *              <BR>  1: Inserta en campo "tbm_cabNotPedImp"
     *              <BR>  2: Inserta en campo "tbm_cabPedEmbImp"
     *              <BR>  3: Inserta en campo "tbm_cabCotVen"
     *              <BR>  4: Inserta en campo "tbm_cabSolTraInv"
     *              <BR>  5: Inserta en campo "tbm_cabMovInv"
     *              <BR>  6: Inserta en campo "tbm_cabGuiRem"
     *              <BR>  7: Inserta en campo "tbm_cabIngEgrMerBod"
     *          </HTML>
     * @param codigoEmpresa Código de la empresa de la tabla a insertar
     * @param codigoLocal Código de la local de la tabla a insertar
     * @param codigoTipoDocumento Código de tipo de documento de la tabla a insertar
     *      <BR> Cuando es la tabla de Cotizaciones este campo debe enviarse como Null
     * @param codigoDocumento Código de documento de la tabla a insertar
     * @return 
     */
    public boolean setSegMovInvCompra(Connection con
            , int codigoEmpresaSolicitud, int codigoLocalSolicitud, int codigoTipoDocumentoSolicitud, int codigoDocumentoSolicitud
            , int indiceTabla, Object codigoEmpresa, Object codigoLocal, Object codigoTipoDocumento, Object codigoDocumento
            ){
        boolean blnRes=true;
        int intCodSeg=-1;
        int intUltCodReg=0;
        int indTbl=indiceTabla;
        Object objCodEmp=codigoEmpresa;
        Object objCodLoc=codigoLocal;
        Object objCodTipDoc=codigoTipoDocumento;
        Object objCodDoc=codigoDocumento;
        try{
            if(con!=null){
                stm=con.createStatement();                
                strSQL="";
                strSQL+=" SELECT a1.co_seg FROM tbm_cabSegMovInv AS a1 ";
                strSQL+=" WHERE a1.co_emprelcabsoltrainv=" + codigoEmpresaSolicitud + "";
                strSQL+=" AND a1.co_locrelcabsoltrainv=" + codigoLocalSolicitud + "";
                strSQL+=" AND a1.co_tipdocrelcabsoltrainv=" + codigoTipoDocumentoSolicitud + "";
                strSQL+=" AND a1.co_docrelcabsoltrainv=" + codigoDocumentoSolicitud + "";
                strSQL+=" GROUP BY a1.co_seg";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodSeg=rst.getInt("co_seg");
                    
                    strSQL="";
                    strSQL+=" SELECT MAX(co_reg) AS co_reg ";
                    strSQL+=" FROM tbm_cabSegMovInv";
                    strSQL+=" WHERE co_seg=" + intCodSeg + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intUltCodReg=rst.getInt("co_reg");
                    intUltCodReg++;

                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabSegMovInv(";
                    strSQL+="   co_seg, co_reg, ";
                    strSQL+="   co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp,";//tbm_cabNotPedPreImp - 4
                    strSQL+="   co_emprelcabnotpedimp, co_locrelcabnotpedimp, co_tipdocrelcabnotpedimp, co_docrelcabnotpedimp,";//tbm_cabNotPedImp - 4
                    strSQL+="   co_emprelcabpedembimp, co_locrelcabpedembimp, co_tipdocrelcabpedembimp, co_docrelcabpedembimp,";//tbm_cabPedEmbImp -4 
                    strSQL+="   co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven,";//tbm_cabCotVen - 3
                    strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv,";//tbm_cabSolTraInv -4 
                    strSQL+="   co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv,";//tbm_cabMovInv - 4
                    strSQL+="   co_emprelcabguirem, co_locrelcabguirem, co_tipdocrelcabguirem, co_docrelcabguirem,";//tbm_cabGuiRem - 4
                    strSQL+="   co_emprelcabingegrmerbod, co_locrelcabingegrmerbod, co_tipdocrelcabingegrmerbod, co_docrelcabingegrmerbod";//tbm_cabIngEgrMerBod - 4
                    strSQL+=")";
                    strSQL+="(  SELECT " + intCodSeg + ", " + intUltCodReg + ", ";
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedPreImp - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedImp - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabPedEmbImp -4 
                    strSQL+="   Null, Null, Null,";//tbm_cabCotVen - 3
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabSolTraInv -4 
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabMovInv - 4
                    strSQL+="   Null, Null, Null, Null,";//tbm_cabGuiRem - 4
                    strSQL+="   Null, Null, Null, Null";//tbm_cabIngEgrMerBod - 4
                    strSQL+=")";
                    strSQL+=";";
                    strSQL+=" UPDATE tbm_cabSegMovInv";
                    strSQL+=" SET";
                    switch(indTbl){
                        case 0://tbm_cabNotPedPreImp
                            strSQL+=" co_emprelcabnotpedpreimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabnotpedpreimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabnotpedpreimp=" + objCodDoc + "";
                            break;
                        case 1://tbm_cabNotPedImp
                            strSQL+=" co_emprelcabnotpedimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabnotpedimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabnotpedimp=" + objCodDoc + "";
                            break;
                        case 2://tbm_cabPedEmbImp
                            strSQL+=" co_emprelcabpedembimp=" + objCodEmp + "";
                            strSQL+=", co_locrelcabpedembimp=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabpedembimp=" + objCodDoc + "";
                            break;
                        case 3://tbm_cabCotVen
                            strSQL+=" co_emprelcabcotven=" + objCodEmp + "";
                            strSQL+=", co_locrelcabcotven=" + objCodLoc + "";
                            strSQL+=", co_cotrelcabcotven=" + objCodDoc + "";
                            break;
                        case 4://tbm_cabSolTraInv
                            strSQL+=" co_emprelcabsoltrainv=" + objCodEmp + "";
                            strSQL+=", co_locrelcabsoltrainv=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabsoltrainv=" + objCodDoc + "";
                            break;
                        case 5://tbm_cabMovInv
                            strSQL+=" co_emprelcabmovinv=" + objCodEmp + "";
                            strSQL+=", co_locrelcabmovinv=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabmovinv=" + objCodDoc + "";
                            break;
                        case 6://tbm_cabGuiRem
                            strSQL+=" co_emprelcabguirem=" + objCodEmp + "";
                            strSQL+=", co_locrelcabguirem=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabguirem=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabguirem=" + objCodDoc + "";
                            break;
                        case 7://tbm_cabIngEgrMerBod
                            strSQL+=" co_emprelcabingegrmerbod=" + objCodEmp + "";
                            strSQL+=", co_locrelcabingegrmerbod=" + objCodLoc + "";
                            strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDoc + "";
                            strSQL+=", co_docrelcabingegrmerbod=" + objCodDoc + "";
                            break;
                        default://
                            break;
                    }
                    strSQL+=" WHERE co_seg=" + intCodSeg + " AND co_reg=" + intUltCodReg + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    rst.close();
                    rst=null;
                }
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - setSegMovInvCompra SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - setSegMovInvCompra: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite insertar en la tabla de seguimientos
     * @param con Conexión a la base de datos
     * @param codigoSeguimiento Código de seguimiento
     * @param indiceTabla 
     *          <HTML>
     *              <BR>  0: Inserta en tabla "tbm_cabNotPedPreImp"
     *              <BR>  1: Inserta en campo "tbm_cabNotPedImp"
     *              <BR>  2: Inserta en campo "tbm_cabPedEmbImp"
     *              <BR>  3: Inserta en campo "tbm_cabCotVen"
     *              <BR>  4: Inserta en campo "tbm_cabSolTraInv"
     *              <BR>  5: Inserta en campo "tbm_cabMovInv"
     *              <BR>  6: Inserta en campo "tbm_cabGuiRem"
     *              <BR>  7: Inserta en campo "tbm_cabIngEgrMerBod"
     *          </HTML>
     * @param codigoEmpresa Código de la empresa de la tabla a insertar
     * @param codigoLocal Código de la local de la tabla a insertar
     * @param codigoTipoDocumento Código de tipo de documento de la tabla a insertar
     *      <BR> Cuando es la tabla de Cotizaciones este campo debe enviarse como Null
     * @param codigoDocumento Código de documento de la tabla a insertar
     * @return 
     */
    public boolean setSegMovInvCompra(Connection con
            , Object codigoSeguimiento, int indiceTabla, Object codigoEmpresa, Object codigoLocal, Object codigoTipoDocumento, Object codigoDocumento
            ){
        boolean blnRes=true;
        Object objCodSeg=codigoSeguimiento;
        int intUltCodReg=0;
        int indTbl=indiceTabla;
        Object objCodEmp=codigoEmpresa;
        Object objCodLoc=codigoLocal;
        Object objCodTipDoc=codigoTipoDocumento;
        Object objCodDoc=codigoDocumento;
        try{
            if(con!=null){
                stm=con.createStatement();
                if(objCodSeg==null){
                    strSQL="";
                    strSQL+=" SELECT (MAX((CASE WHEN co_seg IS NULL THEN 0 ELSE co_seg END))+1) AS co_seg ";
                    strSQL+=" FROM tbm_cabSegMovInv";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        objCodSeg=rst.getObject("co_seg");
                }
                    
                strSQL="";
                strSQL+=" SELECT MAX(co_reg) AS co_reg ";
                strSQL+=" FROM tbm_cabSegMovInv";
                strSQL+=" WHERE co_seg=" + objCodSeg + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltCodReg=rst.getInt("co_reg");
                intUltCodReg++;

                strSQL="";
                strSQL+="INSERT INTO tbm_cabSegMovInv(";
                strSQL+="   co_seg, co_reg, ";
                strSQL+="   co_emprelcabnotpedpreimp, co_locrelcabnotpedpreimp, co_tipdocrelcabnotpedpreimp, co_docrelcabnotpedpreimp,";//tbm_cabNotPedPreImp - 4
                strSQL+="   co_emprelcabnotpedimp, co_locrelcabnotpedimp, co_tipdocrelcabnotpedimp, co_docrelcabnotpedimp,";//tbm_cabNotPedImp - 4
                strSQL+="   co_emprelcabpedembimp, co_locrelcabpedembimp, co_tipdocrelcabpedembimp, co_docrelcabpedembimp,";//tbm_cabPedEmbImp -4 
                strSQL+="   co_emprelcabcotven, co_locrelcabcotven, co_cotrelcabcotven,";//tbm_cabCotVen - 3
                strSQL+="   co_emprelcabsoltrainv, co_locrelcabsoltrainv, co_tipdocrelcabsoltrainv, co_docrelcabsoltrainv,";//tbm_cabSolTraInv -4 
                strSQL+="   co_emprelcabmovinv, co_locrelcabmovinv, co_tipdocrelcabmovinv, co_docrelcabmovinv,";//tbm_cabMovInv - 4
                strSQL+="   co_emprelcabguirem, co_locrelcabguirem, co_tipdocrelcabguirem, co_docrelcabguirem,";//tbm_cabGuiRem - 4
                strSQL+="   co_emprelcabingegrmerbod, co_locrelcabingegrmerbod, co_tipdocrelcabingegrmerbod, co_docrelcabingegrmerbod";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+="(  SELECT " + objCodSeg + ", " + intUltCodReg + ", ";
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedPreImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabNotPedImp - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabPedEmbImp -4 
                strSQL+="   Null, Null, Null,";//tbm_cabCotVen - 3
                strSQL+="   Null, Null, Null, Null,";//tbm_cabSolTraInv -4 
                strSQL+="   Null, Null, Null, Null,";//tbm_cabMovInv - 4
                strSQL+="   Null, Null, Null, Null,";//tbm_cabGuiRem - 4
                strSQL+="   Null, Null, Null, Null";//tbm_cabIngEgrMerBod - 4
                strSQL+=")";
                strSQL+=";";
                strSQL+=" UPDATE tbm_cabSegMovInv";
                strSQL+=" SET";
                switch(indTbl){
                    case 0://tbm_cabNotPedPreImp
                        strSQL+=" co_emprelcabnotpedpreimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedpreimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedpreimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedpreimp=" + objCodDoc + "";
                        break;
                    case 1://tbm_cabNotPedImp
                        strSQL+=" co_emprelcabnotpedimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabnotpedimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabnotpedimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabnotpedimp=" + objCodDoc + "";
                        break;
                    case 2://tbm_cabPedEmbImp
                        strSQL+=" co_emprelcabpedembimp=" + objCodEmp + "";
                        strSQL+=", co_locrelcabpedembimp=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabpedembimp=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabpedembimp=" + objCodDoc + "";
                        break;
                    case 3://tbm_cabCotVen
                        strSQL+=" co_emprelcabcotven=" + objCodEmp + "";
                        strSQL+=", co_locrelcabcotven=" + objCodLoc + "";
                        strSQL+=", co_cotrelcabcotven=" + objCodDoc + "";
                        break;
                    case 4://tbm_cabSolTraInv
                        strSQL+=" co_emprelcabsoltrainv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabsoltrainv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabsoltrainv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabsoltrainv=" + objCodDoc + "";
                        break;
                    case 5://tbm_cabMovInv
                        strSQL+=" co_emprelcabmovinv=" + objCodEmp + "";
                        strSQL+=", co_locrelcabmovinv=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabmovinv=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabmovinv=" + objCodDoc + "";
                        break;
                    case 6://tbm_cabGuiRem
                        strSQL+=" co_emprelcabguirem=" + objCodEmp + "";
                        strSQL+=", co_locrelcabguirem=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabguirem=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabguirem=" + objCodDoc + "";
                        break;
                    case 7://tbm_cabIngEgrMerBod
                        strSQL+=" co_emprelcabingegrmerbod=" + objCodEmp + "";
                        strSQL+=", co_locrelcabingegrmerbod=" + objCodLoc + "";
                        strSQL+=", co_tipdocrelcabingegrmerbod=" + objCodTipDoc + "";
                        strSQL+=", co_docrelcabingegrmerbod=" + objCodDoc + "";
                        break;
                    default://
                        break;
                }
                strSQL+=" WHERE co_seg=" + objCodSeg + " AND co_reg=" + intUltCodReg + "";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - setSegMovInvCompra SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - setSegMovInvCompra: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    
    
    
    
}
