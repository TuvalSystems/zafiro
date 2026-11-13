/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso;

import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Sistema de Notificaciones de Reservas
 * @author Sistemas6
 */
public class ZafSer17_01 {
    
    
    private ZafParSis objParSis;
    
    private ZafNotCorEle objNotCorEle;
    
    private static final int INT_ARL_COD_EMP_TUV=1;
    private static final int INT_ARL_COD_EMP_CAS=2;
    private static final int INT_ARL_COD_EMP_DIM=4;
    
//1;4;1;"ZafSer17: Reservas de inventario con facturación automática (Pendientes y facturadas)"
//1;4;2;"ZafSer17: Reservas de inventario con facturación manual (Por vencer)"
//1;4;3;"ZafSer17: Reservas de inventario con facturación manual (Vencidas)"
//1;4;4;"ZafSer17: Reservas de inventario con facturación manual (Canceladas automáticamente por el Sistema)"
//1;4;5;"ZafSer17: Reservas de inventario en consignación (Vencidas y por vencer)"

    
    private static final int INT_ARL_COD_CFG_FAC_AUT=1;
    private static final int INT_ARL_COD_CFG_FAC_MAN_POR_VEN=2;
    private static final int INT_ARL_COD_CFG_FAC_MAN_VEN=3;
    private static final int INT_ARL_COD_CFG_FAC_MAN_CAN=4;
    private static final int INT_ARL_COD_CFG_FAC_CON=5;
    
    private static final int INT_ARL_COD_CFG_RETENCIONES=16;
    
    private String strFacAutTuv="",strFacAutCasUIO="", strFacAutCasSTO="",strFacAutCasCUE="",strFacAutCasMAN="",strFacAutDim="",strFacAutDimDur=""; //Facturas Automaticas 
    
    
    private String strSql,strVersion="Sistema Reserva: Notificaciones v 0.04";
    
     public ZafSer17_01(){
        System.out.println(strVersion);
        objParSis = new ZafParSis();
        objNotCorEle = new ZafNotCorEle(objParSis);
    }
     
 
     
     public boolean iniciarProcesoNotificaciones(){
         boolean blnRes=true;
         try{ System.out.println("OK iniciarProcesoNotificaciones");
            if(notificacionFacturaAutomatica()){
                if(armarCadenaFacturaAutomatica()){
                    if(enviarNotificacionFacturaAutomatica()){
                        System.out.println("OK Automaticas");
                    }else{blnRes=false;}
                }else{blnRes=false;}
            }else{blnRes=false;}
            
            if(notificacionFacturaManual()){
                if(armarCadenaFacturaManual()){
                     if(enviarNotificacionFacturaManual()){
                        System.out.println("OK Automaticas");
                     }else{blnRes=false;}
                 }else{blnRes=false;}
            } else{blnRes=false;}
            
            if(notificacionFacturaManualCancelada()){
                if(armarCadenaFacturaManualCancelada()){
                     if(enviarNotificacionFacturaManualCancelada()){
                        System.out.println("OK Manuales Canceladas");
                     }else{blnRes=false;}
                 }else{blnRes=false;}
            } else{blnRes=false;}
//                
//            if(notificacionFacturaConsignacion()){
//                if(armarCadenaFacturaConsignacion()){
//                    if(enviarNotificacionFacturaConsignacion()){
//                        System.out.println("OK Consignacion");
//                    }else{blnRes=false;}
//                }else{blnRes=false;} 
//            }else{blnRes=false;}
//              

            if(notificacionesContabilidad()){
                System.out.println("ok notificaciones contabilidad");
            }

             
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
     
    java.util.Date objFec;
    
    
    /**
     * ::: Date (0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday) :::
     * 
     * @return 
     */
     
    private boolean notificacionesContabilidad(){
        boolean blnRes=false;
        try{
            objFec = new java.util.Date();
            System.out.println("Date (0 = Sunday, 1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday)::: " + objFec.getDay());
            if(objFec.getDay()==1 || objFec.getDay()==4){
                if(consultarRetencionesPendientes()){
                    if(enviarCorreoRetencionesPendiente()){
                         
                    }
                }
            }
            objFec=null;
        }
        catch (Exception e) {
           System.err.println("CONTABILIDAD: " + e);
           blnRes=false;
       }
       return blnRes;
    }
     
    
    private String strRetencionesTuval="", strRetencionesCastek="", strRetencionesDimulti="";
    
    
    
    private boolean enviarCorreoRetencionesPendiente(){
        boolean blnRes=true, blnEnviar=false;
        String strAux="", strTit, strFin;
        strTit="<html> Notificaciones Sistema de Retenciones pendientes. <BR><BR>" ;// CAMBIA
        strTit+=" <table BORDER=1><tr><td> Empresa/Local </td> <td> Tip.Doc. </td><td> Tipo de Documento </td><td> Cód.Cli. </td> ";   
        strTit+="                     <td> Cliente </td><td> Fecha </td><td> Núm.Doc. </td><td> VAL.PEN.ABO. </td> <td> VAL.DOC. </td> <td> Dias pendiente </td> </tr>";  
        strFin=" </table><BR> <html>";
        try{
            if(strRetencionesTuval.length()>0 || strRetencionesCastek.length()>0 || strRetencionesDimulti.length()>0){
                strAux+=strTit;
                strAux+=strRetencionesTuval;
                strAux+=strRetencionesCastek;
                strAux+=strRetencionesDimulti;
                strAux+=strFin;
                blnEnviar=true;
            }
            
            if(blnEnviar){
                if(objNotCorEle.enviarNotificacionCorreoElectronico(0, 1, INT_ARL_COD_CFG_RETENCIONES, strAux)){
                     System.out.println("OK INT_ARL_COD_CFG_RETENCIONES");
                }
            }
            
        }
        catch (Exception e) {
           System.err.println("ERROR CONTABILIDAD: " + e);
           blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean consultarRetencionesPendientes(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp,c1.tx_nom as NomLoc, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_cli, a1.tx_nomCli, a1.fe_doc, \n";
                strSql+="        a1.ne_numDoc, ROUND(sum(a2.mo_pag + a2.nd_abo),2) AS nd_valPnd, ROUND(sum(a2.mo_pag),2) AS nd_valDoc,c1.tx_nom AS tx_nomLoc,  \n";
                strSql+="        a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc, (current_date - a1.fe_doc) as fechaPendientes  \n";
                strSql+=" FROM (tbm_cabMovInv as a1  \n";
                strSql+=" Inner join tbm_cabtipdoc as a3 on a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)  \n";
                strSql+=" inner join tbm_pagmovinv as a2 on a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc  \n";
                strSql+=" INNER JOIN tbm_loc AS c1 ON a2.co_emp=c1.co_emp AND a2.co_loc=c1.co_loc  \n";
                strSql+=" WHERE a1.fe_doc >='2018-01-01' AND a1.fe_doc<=CURRENT_DATE -15 AND a1.co_tipDoc=2 AND a1.st_reg NOT IN('E','I')  AND a2.st_reg IN('A','C') and  \n";
                strSql+="       case when a3.tx_natDoc='I' THEN (a2.mo_pag + a2.nd_abo)>0   \n";
                strSql+="       when a3.tx_natDoc='E' THEN (a2.mo_pag + a2.nd_abo)<0 END AND  a2.nd_porRet>0  \n";
                strSql+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli, a1.fe_doc, a1.ne_numdoc, c1.tx_nom, a3.tx_desCor, a3.tx_desLar  \n";
                strSql+=" ORDER BY a1.fe_doc, a1.co_emp, a1.co_loc \n";
                System.out.println("consultarRetencionesPendientes " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("co_emp")){
                        case 1:
                            strRetencionesTuval+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("NomLoc")+"</a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desCorTipDoc")+" </a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desLarTipDoc")+" </a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cli")+" </a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_doc")+" </a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("ne_numDoc")+"</a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valPnd")+"</a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valDoc")+"</a></td>";
                            strRetencionesTuval+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fechaPendientes")+"</a></td> </tr>";
                            break;
                        case 2: 
                            strRetencionesCastek+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("NomLoc")+"</a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desCorTipDoc")+" </a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desLarTipDoc")+" </a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cli")+" </a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_doc")+" </a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("ne_numDoc")+"</a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valPnd")+"</a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valDoc")+"</a></td>";
                            strRetencionesCastek+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fechaPendientes")+"</a></td> </tr>";
                            break;
                        case 4: 
                            strRetencionesDimulti+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("NomLoc")+"</a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desCorTipDoc")+" </a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_desLarTipDoc")+" </a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cli")+" </a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_doc")+" </a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("ne_numDoc")+"</a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valPnd")+"</a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_valDoc")+"</a></td>";
                            strRetencionesDimulti+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fechaPendientes")+"</a></td> </tr>";
                            break;
                    };
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch (Exception e) {
           System.err.println("ERROR CONTABILIDAD: " + e);
           blnRes=false;
        }
        return blnRes;
    }
     
    private String strMsgCorr="", strMsgFacAut, strMsgFacMan, strMsgFacCon ;// CAMBIA
    
    private String strFacManTuv="",strFacManCasUIO="",strFacManCasSTO="",strFacManCasMAN="",strFacManCasCUE="",strFacManDim=""; //Facturas Manuales
    private String strFacManTuvDir="",strFacManCasUIODir="",strFacManCasSTODir="",strFacManCasMANDir="",strFacManCasCUEDir="",strFacManDimDir=""; //Facturas Manuales // Directivos
    
    private String  strFacManDimDur="", strFacManDimDurDir="" ;
    
    
     private boolean notificacionFacturaManualCancelada(){
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc; 
         java.sql.ResultSet rstLoc;
         boolean blnRes=true;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot, \n";
                strSql+="        a1.tx_nomCli  , a2.ne_numDiaNotSolResInvPorVen, a2.ne_numDiaNotSolResInvVen, \n";
                strSql+="        a1.fe_autSolResInv,a1.fe_autSolResInv + a2.ne_numDiaNotSolResInvVen  as fechaDeCancelacion, \n";
                strSql+="        CASE WHEN  a1.fe_autSolResInv + a2.ne_numDiaNotSolResInvVen < CURRENT_DATE THEN 'CANCELAR' ELSE 'NOCANCELAR' END as texto \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) \n";
                strSql+=" INNER JOIN tbm_tipSolResInv AS a3 ON (a1.co_tipSolResInv=a3.co_tipSolResInv) \n";
                strSql+=" WHERE a1.st_reg IN ('F') AND a1.st_solResInv='C'  AND a3.tx_tipResInv like 'R' AND \n";
                strSql+="       CASE WHEN  a1.fe_autSolResInv + a2.ne_numDiaNotSolResInvVen < CURRENT_DATE THEN TRUE ELSE FALSE END AND \n";
                strSql+="       (CASE WHEN a1.nd_valSolResInvCan IS NOT NULL THEN  \n";
                strSql+="                           CASE WHEN a1.nd_valSolResInvCan > 0 THEN TRUE   \n";
                strSql+="                           ELSE FALSE END ELSE FALSE END )  AND  \n";
                strSql+="       (a1.fe_autSolResInv + a2.ne_numDiaNotSolResInvVen = current_date)  \n";
                strSql+=" ORDER BY a1.fe_cot, a1.co_emp,a1.co_loc,a1.co_cot ";
                System.out.println("notificacionFacturaManualCancelada: " + strSql);
                // Empresa	Local	Cotización	Fec.Cot.	Cliente	Total	Fec.Res.Aut.	Val.Pen.Fac.
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("co_emp")){
                        case 1: strFacManTuv+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                break; 
                        case 2: 
                            switch(rstLoc.getInt("co_loc")){
                                case 1: strFacManCasUIO+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break; 
                                case 11:strFacManCasUIO+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break;
                                case 4: strFacManCasMAN+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break;
                                case 12:strFacManCasMAN+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break;
                                case 6: strFacManCasSTO+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break; 
                                case 13:strFacManCasSTO+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break;
                                case 10:strFacManCasCUE+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break; 
                                case 14:strFacManCasCUE+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                        break;      
                            }
                        break;
                        case 4: 
                            switch(rstLoc.getInt("co_loc")){
                                case 3: strFacManDim+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                    break;
                                case 10: strFacManDim+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                    break;
                                case 12: strFacManDimDur+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                    break;
                                case 13: strFacManDimDur+=obtenerDatoFacturaManualCancelada(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot") ); 
                                    break;
                            }
                            
                             
                    }
                }
            }
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
    
     /**
      * 
      * @return 
      */
     private boolean notificacionFacturaManual(){
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         boolean blnRes=true;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot, \n";
                strSql+="        a1.tx_nomCli  , a2.ne_numDiaNotSolResInvPorVen, a2.ne_numDiaNotSolResInvVen \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) \n";
                strSql+=" INNER JOIN tbm_tipSolResInv AS a3 ON (a1.co_tipSolResInv=a3.co_tipSolResInv) \n";
                strSql+=" WHERE a1.st_reg NOT IN ('F','I') AND a1.st_solResInv='P' AND a1.st_autSolResInv='A' AND a3.tx_tipResInv like 'R'  ";
                strSql+=" ORDER BY a1.fe_cot, a1.co_emp,a1.co_loc,a1.co_cot ";
                System.out.println("notificacionFacturaManual " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("co_emp")){
                        case 1: strFacManTuv+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-" ); 
                                strFacManTuvDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+" ); 
                                break; 
                        case 2: 
                            switch(rstLoc.getInt("co_loc")){
                                case 1: strFacManCasUIO+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasUIODir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 11:strFacManCasUIO+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasUIODir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 4: strFacManCasMAN+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasMANDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 12:strFacManCasMAN+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasMANDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 6: strFacManCasSTO+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasSTODir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 13:strFacManCasSTO+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasSTODir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 10:strFacManCasCUE+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasCUEDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 14:strFacManCasCUE+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManCasCUEDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;      
                            }
                        break;
                        case 4: 
                            switch(rstLoc.getInt("co_loc")){
                                case 3: strFacManDim+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManDimDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                break; 
                                case 10: strFacManDim+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManDimDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                break; 
                                case 12: strFacManDimDur+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManDimDurDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                break;
                                case 13: strFacManDimDur+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacManDimDurDir+=obtenerDatoFacturaManual(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                break;
                            }
                        break;    
                    }
                }
            }
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
     
     private String strFacConTuv="",strFacConCasUIO="",strFacConCasMAN="",strFacConCasSTO="",strFacConCasCUE="",strFacConDim=""; //Facturas Por Mercaderia Consignada
     private String strFacConTuvDir="",strFacConCasUIODir="",strFacConCasMANDir="",strFacConCasSTODir="",strFacConCasCUEDir="",strFacConDimDir=""; //Facturas Por Mercaderia Consignada
     
     private String strFacConDimDur="", strFacConDimDurDir="";
     
    private boolean enviarNotificacionFacturaConsignacion(){
        boolean blnRes=true;
        try{
            if(strFacConTuv.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_CON, strFacConTuv)){
                    blnRes=false;
                }
            }
            strFacConTuv="";
            
            if(strFacConCasUIO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_CON, strFacConCasUIO)){
                    blnRes=false;
                }
            }
            strFacConCasUIO="";
            
            if(strFacConCasMAN.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_CON, strFacConCasMAN)){
                    blnRes=false;
                }
            }
            strFacConCasMAN="";
            
            if(strFacConCasSTO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_CON, strFacConCasSTO)){
                    blnRes=false;
                }
            }
            strFacConCasSTO="";
            
            if(strFacConCasCUE.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_CON, strFacConCasCUE)){
                    blnRes=false;
                }
            }
            strFacConCasCUE="";
            
            if(strFacConDim.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_CON, strFacConDim)){
                    blnRes=false;
                }
            }
            strFacConDim="";
            ////
            if(strFacConTuvDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_CON, strFacConTuvDir)){
                    blnRes=false;
                }
            }
            strFacConTuvDir="";
            
            if(strFacConCasUIODir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_CON, strFacConCasUIODir)){
                    blnRes=false;
                }
            }
            strFacConCasUIODir="";
            
            if(strFacConCasMANDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_CON, strFacConCasMANDir)){
                    blnRes=false;
                }
            }
            strFacConCasMANDir="";
            
            if(strFacConCasSTODir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_CON, strFacConCasSTODir)){
                    blnRes=false;
                }
            }
            strFacConCasSTODir="";
            
            if(strFacConCasCUEDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_CON, strFacConCasCUEDir)){
                    blnRes=false;
                }
            }
            strFacConCasCUEDir="";
            
            if(strFacConDimDir.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_CON, strFacConDimDir)){
                    blnRes=false;
                }
            }
            strFacConDimDir="";
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        
        return blnRes;
    }
     
     
     
     
     private boolean notificacionFacturaConsignacion(){
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         boolean blnRes=true;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot, \n";
                strSql+="        a1.tx_nomCli  , a2.ne_numDiaNotSolResInvPorVen, a2.ne_numDiaNotSolResInvVen \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) \n";
                strSql+=" WHERE a1.st_reg IN ('A','U','E','L') AND a1.co_tipSolResInv IN (6) AND a1.st_solResInv IS NOT NULL AND  \n";
                strSql+="       a1.st_autSolResInv='A' \n";
                strSql+=" ORDER BY a1.fe_cot, a1.co_emp,a1.co_loc,a1.co_cot \n";
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("co_emp")){
                        case 1: strFacConTuv+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-" ); 
                                strFacConTuvDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+" ); 
                                break; 
                        case 2: 
                            switch(rstLoc.getInt("co_loc")){
                                case 1: strFacConCasUIO+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasUIODir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 11:strFacConCasUIO+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasUIODir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 4: strFacConCasMAN+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasMANDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 12:strFacConCasMAN+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasMANDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 6: strFacConCasSTO+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasSTODir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 13:strFacConCasSTO+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasSTODir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;
                                case 10:strFacConCasCUE+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasCUEDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break; 
                                case 14:strFacConCasCUE+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                        strFacConCasCUEDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                        break;      
                            }
                        break;
                        case 4: 
                            switch(rstLoc.getInt("co_loc")){
                                case 3: 
                                    strFacConDim+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                    strFacConDimDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                    break; 
                                case 10: 
                                    strFacConDim+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                    strFacConDimDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                    break;
                                case 12: 
                                    strFacConDimDur+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                    strFacConDimDurDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                    break; 
                                case 13: 
                                    strFacConDimDur+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"-"); 
                                    strFacConDimDurDir+=obtenerDatoFacturaConsignacion(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"),rstLoc.getInt("ne_numDiaNotSolResInvPorVen"),rstLoc.getInt("ne_numDiaNotSolResInvVen"),"+"); 
                                    break;
                                    
                            }
                            
                            
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
     
     
     
    private boolean armarCadenaFacturaConsignacion(){
         boolean blnRes=true;
         String strAux="", strTit, strFin;
         // Empresa	Local	Cotización	Fec.Cot.	Cliente	Total	Fec.Res.Aut.	Val.Pen.Fac.
         strTit="<html> Notificaciones Sistema de Reserva. <BR><BR>" ;// CAMBIA
         strTit+=" <table BORDER=1><tr><td> EMPRESA </td> <td> LOCAL </td><td> COTIZACION </td><td> FECHA COTIZACION </td> ";   
         strTit+="                     <td> CLIENTE </td><td> TOTAL </td><td> FEC.RES.AUT. </td><td> VAL.PEN.FAC. </td> </tr>";  
         strFin=" </table><BR> <html>";
         try{
             if(strFacConTuv.length()>0){
                 strAux = strFacConTuv;
                 strFacConTuv=strTit;  
                 strFacConTuv+=strAux;
                 strFacConTuv+=strFin;
             }
             strAux="";
             if(strFacConCasUIO.length()>0){
                 strAux = strFacConCasUIO;
                 strFacConCasUIO=strTit;  
                 strFacConCasUIO+=strAux;
                 strFacConCasUIO+=strFin;
             }
             strAux="";
             if(strFacConCasMAN.length()>0){
                 strAux = strFacConCasMAN;
                 strFacConCasMAN=strTit;  
                 strFacConCasMAN+=strAux;
                 strFacConCasMAN+=strFin;
             }
             strAux="";
             if(strFacConCasSTO.length()>0){
                 strAux = strFacConCasSTO;
                 strFacConCasSTO=strTit;  
                 strFacConCasSTO+=strAux;
                 strFacConCasSTO+=strFin;
             }
             strAux="";
             if(strFacConCasCUE.length()>0){
                 strAux = strFacConCasCUE;
                 strFacConCasCUE=strTit;  
                 strFacConCasCUE+=strAux;
                 strFacConCasCUE+=strFin;
             }
             strAux="";
             if(strFacConDim.length()>0){
                 strAux = strFacConDim;
                 strFacConDim=strTit;  
                 strFacConDim+=strAux;
                 strFacConDim+=strFin;
             }
             strAux="";
             if(strFacConDimDur.length()>0){
                 strAux = strFacConDimDur;
                 strFacConDimDur=strTit;  
                 strFacConDimDur+=strAux;
                 strFacConDimDur+=strFin;
             }
             strAux="";
             /*  =================  */
             if(strFacConTuvDir.length()>0){
                 strAux = strFacConTuvDir;
                 strFacConTuvDir=strTit;  
                 strFacConTuvDir+=strAux;
                 strFacConTuvDir+=strFin;
             }
             strAux="";
             if(strFacConCasUIODir.length()>0){
                 strAux = strFacConCasUIODir;
                 strFacConCasUIODir=strTit;  
                 strFacConCasUIODir+=strAux;
                 strFacConCasUIODir+=strFin;
             }
             strAux="";
             if(strFacConCasMANDir.length()>0){
                 strAux = strFacConCasMANDir;
                 strFacConCasMANDir=strTit;  
                 strFacConCasMANDir+=strAux;
                 strFacConCasMANDir+=strFin;
             }
             strAux="";
             if(strFacConCasSTODir.length()>0){
                 strAux = strFacConCasSTODir;
                 strFacConCasSTODir=strTit;  
                 strFacConCasSTODir+=strAux;
                 strFacConCasSTODir+=strFin;
             }
             strAux="";
             if(strFacConCasCUEDir.length()>0){
                 strAux = strFacConCasCUEDir;
                 strFacConCasCUEDir=strTit;  
                 strFacConCasCUEDir+=strAux;
                 strFacConCasCUEDir+=strFin;
             }
             strAux="";
             if(strFacConDimDir.length()>0){
                 strAux = strFacConDimDir;
                 strFacConDimDir=strTit;  
                 strFacConDimDir+=strAux;
                 strFacConDimDir+=strFin;
             }
             strAux="";
             if(strFacConDimDurDir.length()>0){
                 strAux = strFacConDimDurDir;
                 strFacConDimDurDir=strTit;  
                 strFacConDimDurDir+=strAux;
                 strFacConDimDurDir+=strFin;
             }
             strAux="";
         }   
         catch (Exception e) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>> ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
     
     
     
     private String obtenerDatoFacturaConsignacion(int CodEmp, int CodLoc, int CodCot,int NumDiaNotSolResInvPorVen,int NumDiaNotSolResInvVen, String strOpe){
         String strRes="";
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a3.tx_nom as tx_nomEmp, a4.tx_nom as tx_nomLoc , a1.co_emp,a1.co_loc,a1.co_cot,  \n";
                strSql+="        a1.fe_cot,ROUND(a1.nd_tot,2) as nd_tot ,a1.fe_autSolResInv, a5.tx_nom as tx_nomCli, \n";
                strSql+="        ROUND(CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END,2) AS canFac ,\n";
                strSql+="        ROUND( a1.nd_tot - ( CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END +  \n";
                strSql+="              CASE WHEN a1.nd_valSolResInvCan IS NULL THEN 0 ELSE a1.nd_valSolResInvCan END ) ,2)  as nd_canPenFac , \n";
                strSql+="        a6.fe_venMenosDiasNotificacion, CURRENT_DATE AS FechaActual, \n";
                strSql+="        CASE WHEN CURRENT_DATE > a1.fe_autSolResInv  THEN 'VENCIDA' ELSE  \n";
                strSql+="        CASE WHEN CURRENT_DATE BETWEEN a6.fe_venMenosDiasNotificacion AND a1.fe_autSolResInv THEN 'XVNCER' ELSE '' END END AS COMENTARIO, \n";
                strSql+="        CASE WHEN CURRENT_DATE  > a6.fe_venMenosDiasNotificacion THEN 'DIRECTIVOS'  END AS COMENTARIO2   ";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)  \n";
                strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc)  \n";
                strSql+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc, a1.fe_dia as fe_venMenosDiasNotificacion   \n";
                strSql+="       FROM tbm_calCiu as a1  \n";
                strSql+="       INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu)  \n";
                strSql+="       INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc) \n";
                strSql+="       WHERE  (a3.fe_autSolResInv "+strOpe+"  ";
                if(strOpe.equals("-")){
                    strSql+=" "+NumDiaNotSolResInvPorVen+" ";
                }
                else{
                    strSql+=" "+NumDiaNotSolResInvVen+" ";
                }
                strSql+=" )  <= a1.fe_Dia  \n";
                strSql+=" ";
                strSql+="               AND a1.tx_tipDia='L' AND a3.co_emp="+CodEmp+" AND a3.co_loc="+CodLoc+"  AND a3.co_cot="+CodCot+"  \n";
                strSql+="       ORDER BY a1.fe_dia ASC  \n";
                strSql+="       LIMIT 1		 \n";
                strSql+=" ) as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
		strSql+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  \n"; 
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(strOpe.equals("-")){
                        if(rstLoc.getString("COMENTARIO").equals("XVNCER")){
                            strRes+="<tr><td> "+rstLoc.getString("tx_nomEmp")+" </td>";
                            strRes+=" <td> "+rstLoc.getString("tx_nomLoc")+"  </td>";
                            strRes+=" <td> "+rstLoc.getString("co_cot")+"  </td>";
                            strRes+=" <td> "+rstLoc.getString("fe_cot")+"  </td>";
                            strRes+=" <td> "+rstLoc.getString("tx_nomCli")+"  </td>";
                            strRes+=" <td> "+rstLoc.getString("nd_tot")+" </td>";
                            strRes+=" <td> "+rstLoc.getString("fe_autSolResInv")+" </td>";
                            strRes+=" <td> "+rstLoc.getString("nd_canPenFac")+" </td> </tr>";
                         }
                        else{
                            strRes+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                            strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                        }
                    }
                    else{
                        strRes+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                        strRes+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            strRes="ERROR " + e;
        }
        return strRes;
     }
      
     
     private boolean enviarNotificacionFacturaManualCancelada(){
        boolean blnRes=true;
        try{
            System.err.println("enviarNotificacionFacturaManualCancelada!!!! ");
            if(strFacManTuv.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManTuv)){
                    blnRes=false;
                }
            }
            strFacManTuv="";
            
            if(strFacManCasUIO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManCasUIO)){
                    blnRes=false;
                }
            }
            strFacManCasUIO="";
            
            if(strFacManCasMAN.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManCasMAN)){
                    blnRes=false;
                }
            }
            strFacManCasMAN="";
            
            if(strFacManCasSTO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManCasSTO)){
                    blnRes=false;
                }
            }
            strFacManCasSTO="";
            
            if(strFacManCasCUE.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManCasCUE)){
                    blnRes=false;
                }
            }
            strFacManCasCUE="";
            
            if(strFacManDim.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManDim)){
                    blnRes=false;
                }
            }
            strFacManDim="";
            
            if(strFacManDimDur.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 12, INT_ARL_COD_CFG_FAC_MAN_CAN, strFacManDimDur)){
                    blnRes=false;
                }
            }
            strFacManDimDur="";
            
            
            ///////////////////////////////////////////////////////////////////////////////////////////////
            
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        
        return blnRes;
    }
     
    
     
     
    private boolean enviarNotificacionFacturaManual(){
        boolean blnRes=true;
        try{
            if(strFacManTuv.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManTuv)){
                    blnRes=false;
                }
            }
            strFacManTuv="";
            
            if(strFacManCasUIO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManCasUIO)){
                    blnRes=false;
                }
            }
            strFacManCasUIO="";
            
            if(strFacManCasMAN.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManCasMAN)){
                    blnRes=false;
                }
            }
            strFacManCasMAN="";
            
            if(strFacManCasSTO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManCasSTO)){
                    blnRes=false;
                }
            }
            strFacManCasSTO="";
            
            if(strFacManCasCUE.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManCasCUE)){
                    blnRes=false;
                }
            }
            strFacManCasCUE="";
            
            if(strFacManDim.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManDim)){
                    blnRes=false;
                }
            }
            strFacManDim="";
            
            if(strFacManDimDur.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 12, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManDimDur)){
                    blnRes=false;
                }
            }
            strFacManDimDur="";
            
            ///////////////////////////////////////////////////////////////////////////////////////////////
            
            if(strFacManTuvDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManTuvDir)){
                    blnRes=false;
                }
            }
            strFacManTuvDir="";
            
            if(strFacManCasUIODir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManCasUIODir)){
                    blnRes=false;
                }
            }
            strFacManCasUIODir="";
            
            if(strFacManCasMANDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManCasMANDir)){
                    blnRes=false;
                }
            }
            strFacManCasMANDir="";
            
            if(strFacManCasSTODir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManCasSTODir)){
                    blnRes=false;
                }
            }
            strFacManCasSTODir="";
            
            if(strFacManCasCUEDir.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManCasCUEDir)){
                    blnRes=false;
                }
            }
            strFacManCasCUEDir="";
            
            if(strFacManDimDir.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_MAN_VEN, strFacManDimDir)){
                    blnRes=false;
                }
            }
            strFacManDimDir="";
            
            if(strFacManDimDurDir.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 12, INT_ARL_COD_CFG_FAC_MAN_POR_VEN, strFacManDimDurDir)){
                    blnRes=false;
                }
            }
            strFacManDimDurDir="";
            
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        
        return blnRes;
    }
     
    
    private boolean armarCadenaFacturaManualCancelada(){
         boolean blnRes=true;
         String strAux="", strTit, strFin;
         // Empresa	Local	Cotización	Fec.Cot.	Cliente	Total	Fec.Res.Aut.	Val.Pen.Fac.
         strTit="<html> Notificaciones Sistema de Reserva.<BR><BR>" ;// CAMBIA
         strTit+=" <table BORDER=1><tr><td> EMPRESA </td> <td> LOCAL </td><td> COTIZACION </td><td> FECHA COTIZACION </td> ";   
         strTit+="                     <td> CLIENTE </td><td> TOTAL </td><td> FEC.RES.AUT. </td><td> VAL.PEN.FAC. </td> </tr>";  
         strFin=" </table><BR> <html>";
         try{
             if(strFacManTuv.length()>0){
                 strAux = strFacManTuv;
                 strFacManTuv=strTit;  
                 strFacManTuv+=strAux;
                 strFacManTuv+=strFin;
             }
             strAux="";
             if(strFacManCasUIO.length()>0){
                 strAux = strFacManCasUIO;
                 strFacManCasUIO=strTit;  
                 strFacManCasUIO+=strAux;
                 strFacManCasUIO+=strFin;
             }
             strAux="";
             if(strFacManCasMAN.length()>0){
                 strAux = strFacManCasMAN;
                 strFacManCasMAN=strTit;  
                 strFacManCasMAN+=strAux;
                 strFacManCasMAN+=strFin;
             }
             strAux="";
             if(strFacManCasSTO.length()>0){
                 strAux = strFacManCasSTO;
                 strFacManCasSTO=strTit;  
                 strFacManCasSTO+=strAux;
                 strFacManCasSTO+=strFin;
             }
             strAux="";
             if(strFacManCasCUE.length()>0){
                 strAux = strFacManCasCUE;
                 strFacManCasCUE=strTit;  
                 strFacManCasCUE+=strAux;
                 strFacManCasCUE+=strFin;
             }
             strAux="";
             if(strFacManDim.length()>0){
                 strAux = strFacManDim;
                 strFacManDim=strTit;  
                 strFacManDim+=strAux;
                 strFacManDim+=strFin;
             }
             
         }   
         catch (Exception e) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>> ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
     
    private boolean armarCadenaFacturaManual(){
         boolean blnRes=true;
         String strAux="", strTit, strFin;
         strTit="<html> Notificaciones Sistema de Reserva. <BR><BR>" ;// CAMBIA
         strTit+=" <table BORDER=1><tr><td> EMPRESA </td> <td> LOCAL </td><td> COTIZACION </td><td> FECHA COTIZACION </td> ";   
         strTit+="                     <td> CLIENTE </td><td> TOTAL </td><td> FEC.RES.AUT. </td><td> VAL.PEN.FAC. </td> </tr>";  
         strFin=" </table><BR> <html>";
         try{
             if(strFacManTuv.length()>0){
                 strAux = strFacManTuv;
                 strFacManTuv=strTit;  
                 strFacManTuv+=strAux;
                 strFacManTuv+=strFin;
             }
             strAux="";
             if(strFacManCasUIO.length()>0){
                 strAux = strFacManCasUIO;
                 strFacManCasUIO=strTit;  
                 strFacManCasUIO+=strAux;
                 strFacManCasUIO+=strFin;
             }
             strAux="";
             if(strFacManCasMAN.length()>0){
                 strAux = strFacManCasMAN;
                 strFacManCasMAN=strTit;  
                 strFacManCasMAN+=strAux;
                 strFacManCasMAN+=strFin;
             }
             strAux="";
             if(strFacManCasSTO.length()>0){
                 strAux = strFacManCasSTO;
                 strFacManCasSTO=strTit;  
                 strFacManCasSTO+=strAux;
                 strFacManCasSTO+=strFin;
             }
             strAux="";
             if(strFacManCasCUE.length()>0){
                 strAux = strFacManCasCUE;
                 strFacManCasCUE=strTit;  
                 strFacManCasCUE+=strAux;
                 strFacManCasCUE+=strFin;
             }
             strAux="";
             if(strFacManDim.length()>0){
                 strAux = strFacManDim;
                 strFacManDim=strTit;  
                 strFacManDim+=strAux;
                 strFacManDim+=strFin;
             }
             strAux="";
             /*  =================  */
             if(strFacManTuvDir.length()>0){
                 strAux = strFacManTuvDir;
                 strFacManTuvDir=strTit;  
                 strFacManTuvDir+=strAux;
                 strFacManTuvDir+=strFin;
             }
             strAux="";
             if(strFacManCasUIODir.length()>0){
                 strAux = strFacManCasUIODir;
                 strFacManCasUIODir=strTit;  
                 strFacManCasUIODir+=strAux;
                 strFacManCasUIODir+=strFin;
             }
             strAux="";
             if(strFacManCasMANDir.length()>0){
                 strAux = strFacManCasMANDir;
                 strFacManCasMANDir=strTit;  
                 strFacManCasMANDir+=strAux;
                 strFacManCasMANDir+=strFin;
             }
             strAux="";
             if(strFacManCasSTODir.length()>0){
                 strAux = strFacManCasSTODir;
                 strFacManCasSTODir=strTit;  
                 strFacManCasSTODir+=strAux;
                 strFacManCasSTODir+=strFin;
             }
             strAux="";
             if(strFacManCasCUEDir.length()>0){
                 strAux = strFacManCasCUEDir;
                 strFacManCasCUEDir=strTit;  
                 strFacManCasCUEDir+=strAux;
                 strFacManCasCUEDir+=strFin;
             }
             strAux="";
             if(strFacManDimDir.length()>0){
                 strAux = strFacManDimDir;
                 strFacManDimDir=strTit;  
                 strFacManDimDir+=strAux;
                 strFacManDimDir+=strFin;
             }
             strAux="";
             
            if(strFacManDimDur.length()>0){
                strAux = strFacManDimDur;
                strFacManDimDur=strTit;  
                strFacManDimDur+=strAux;
                strFacManDimDur+=strFin;
             }
             strAux="";
              
         }   
         catch (Exception e) {
            System.err.println(">>>>>>>>>>>>>>>>>>>>> ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
    private String obtenerDatoFacturaManualCancelada(int CodEmp, int CodLoc, int CodCot){
         String strFacPen="";
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a3.tx_nom as tx_nomEmp, a4.tx_nom as tx_nomLoc , a1.co_emp,a1.co_loc,a1.co_cot,  \n";
                strSql+="        a1.fe_cot,ROUND(a1.nd_tot,2) as nd_tot ,a1.fe_autSolResInv, a5.tx_nom as tx_nomCli, \n";
                strSql+="        ROUND(CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END,2) AS canFac ,\n";
                strSql+="        ROUND( a1.nd_tot - ( CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END +  \n";
                strSql+="              CASE WHEN a1.nd_valSolResInvCan IS NULL THEN 0 ELSE a1.nd_valSolResInvCan END ) ,2)  as nd_canPenFac , \n";
                strSql+="        a6.fe_venMenosDiasNotificacion, CURRENT_DATE AS FechaActual, \n";
                strSql+="        CASE WHEN CURRENT_DATE > a1.fe_autSolResInv  THEN 'VENCIDA' ELSE  \n";
                strSql+="        CASE WHEN CURRENT_DATE BETWEEN a6.fe_venMenosDiasNotificacion AND a1.fe_autSolResInv THEN 'XVNCER' ELSE '' END END AS COMENTARIO, \n";
                strSql+="        CASE WHEN CURRENT_DATE BETWEEN  a1.fe_autSolResInv+1 AND a6.fe_venMenosDiasNotificacion  THEN 'DIRECTIVOS' ELSE ";
                strSql+="                CASE WHEN CURRENT_DATE  > a6.fe_venMenosDiasNotificacion THEN 'REVERSAR' ELSE '' END END AS COMENTARIO2   ";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)  \n";
                strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc)  \n";
                strSql+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc, a1.fe_dia as fe_venMenosDiasNotificacion   \n";
                strSql+="       FROM tbm_calCiu as a1  \n";
                strSql+="       INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu)  \n";
                strSql+="       INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc) \n";
                strSql+="       WHERE  a1.tx_tipDia='L' AND a3.co_emp="+CodEmp+" AND a3.co_loc="+CodLoc+"  AND a3.co_cot="+CodCot+"  \n";
                strSql+="       ORDER BY a1.fe_dia ASC  \n";
                strSql+="       LIMIT 1		 \n";
                strSql+=" ) as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc)\n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
		strSql+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  \n"; 
                System.out.println("obtenerDatoFacturaManualCancelada \n" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strFacPen+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                    strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
            
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            strFacPen="ERROR " + e;
        }
         System.out.println("=====>>>>" + strFacPen); 
        return strFacPen;
     } 
    
    /**
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @param NumDiaNotSolResInvPorVen
     * @param NumDiaNotSolResInvVen
     * @param strOpe
     * @return 
     */
     
     private String obtenerDatoFacturaManual(int CodEmp, int CodLoc, int CodCot,int NumDiaNotSolResInvPorVen,int NumDiaNotSolResInvVen, String strOpe){
         String strFacPen="";
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a3.tx_nom as tx_nomEmp, a4.tx_nom as tx_nomLoc , a1.co_emp,a1.co_loc,a1.co_cot,  \n";
                strSql+="        a1.fe_cot,ROUND(a1.nd_tot,2) as nd_tot ,a1.fe_autSolResInv, a5.tx_nom as tx_nomCli, \n";
                strSql+="        ROUND(CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END,2) AS canFac ,\n";
                strSql+="        ROUND( a1.nd_tot - ( CASE WHEN a1.nd_valSolResInvFac IS NULL THEN 0 ELSE a1.nd_valSolResInvFac END +  \n";
                strSql+="              CASE WHEN a1.nd_valSolResInvCan IS NULL THEN 0 ELSE a1.nd_valSolResInvCan END ) ,2)  as nd_canPenFac , \n";
                strSql+="        a6.fe_venMenosDiasNotificacion, CURRENT_DATE AS FechaActual, \n";
                strSql+="        CASE WHEN CURRENT_DATE > a1.fe_autSolResInv  THEN 'VENCIDA' ELSE  \n";
                strSql+="        CASE WHEN CURRENT_DATE BETWEEN a6.fe_venMenosDiasNotificacion AND a1.fe_autSolResInv THEN 'XVNCER' ELSE '' END END AS COMENTARIO, \n";
                strSql+="        CASE WHEN CURRENT_DATE BETWEEN  a1.fe_autSolResInv+1 AND a6.fe_venMenosDiasNotificacion  THEN 'DIRECTIVOS' ELSE ";
                strSql+="                CASE WHEN CURRENT_DATE  > a6.fe_venMenosDiasNotificacion THEN 'REVERSAR' ELSE '' END END AS COMENTARIO2   ";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)  \n";
                strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc)  \n";
                strSql+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc, a1.fe_dia as fe_venMenosDiasNotificacion   \n";
                strSql+="       FROM tbm_calCiu as a1  \n";
                strSql+="       INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu)  \n";
                strSql+="       INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc) \n";
                strSql+="       WHERE  (a3.fe_autSolResInv "+strOpe+"  ";
                if(strOpe.equals("-")){
                    strSql+=" "+NumDiaNotSolResInvPorVen+" ";
                }
                else{
                    strSql+=" "+NumDiaNotSolResInvVen+" ";
                }
                strSql+=" )  <= a1.fe_Dia  \n";
                strSql+=" ";
                strSql+="               AND a1.tx_tipDia='L' AND a3.co_emp="+CodEmp+" AND a3.co_loc="+CodLoc+"  AND a3.co_cot="+CodCot+"  \n";
                strSql+="       ORDER BY a1.fe_dia ASC  \n";
                strSql+="       LIMIT 1		 \n";
                strSql+=" ) as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc)\n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
		strSql+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  \n"; 
                System.out.println("obtenerDatoFacturaManual: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(strOpe.equals("-")){
                        if(rstLoc.getString("COMENTARIO").equals("XVNCER")){
                            strFacPen+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                         }
                    }
                    else{
                        if(rstLoc.getString("COMENTARIO2").equals("DIRECTIVOS")){  /*????*/
                            strFacPen+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                            strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                         }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
            
         }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            strFacPen="ERROR " + e;
        }
         System.out.println("=====>>>>" + strFacPen); 
        return strFacPen;
     }
 
     /**
      * 
      * @return 
      */
     
    private boolean enviarNotificacionFacturaAutomatica(){
        boolean blnRes=true;
        try{
            
            if(strFacAutTuv.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_TUV, 4, INT_ARL_COD_CFG_FAC_AUT, strFacAutTuv)){
                    blnRes=false;
                }
            }
            strFacAutTuv="";
            
            if(strFacAutCasUIO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 1, INT_ARL_COD_CFG_FAC_AUT, strFacAutCasUIO)){
                    blnRes=false;
                }
            }
            strFacAutCasUIO="";
            
            if(strFacAutCasMAN.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 4, INT_ARL_COD_CFG_FAC_AUT, strFacAutCasMAN)){
                    blnRes=false;
                }
            }
            strFacAutCasMAN="";
            
            if(strFacAutCasSTO.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 6, INT_ARL_COD_CFG_FAC_AUT, strFacAutCasSTO)){
                    blnRes=false;
                }
            }
            strFacAutCasSTO="";
            
            if(strFacAutCasCUE.length()>0){
                if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_CAS, 10, INT_ARL_COD_CFG_FAC_AUT, strFacAutCasCUE)){
                    blnRes=false;
                }
            }
            strFacAutCasCUE="";
            
            if(strFacAutDim.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 3, INT_ARL_COD_CFG_FAC_AUT, strFacAutDim)){
                    blnRes=false;
                }
            }
            strFacAutDim="";
            
            if(strFacAutDimDur.length()>0){
                 if(!objNotCorEle.enviarNotificacionCorreoElectronico(INT_ARL_COD_EMP_DIM, 12, INT_ARL_COD_CFG_FAC_AUT, strFacAutDimDur)){
                    blnRes=false;
                }
            }
            strFacAutDimDur="";
            
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        
        return blnRes;
    }
     
     /**
      * 
      * @return 
      */
     
     private boolean notificacionFacturaAutomatica(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT DISTINCT x.co_emp,x.co_loc,x.co_cot,x.fe_cot,x.nd_tot,x.tx_nomCli,x.st_reg \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot,a1.st_reg,a1.tx_nomCli,'PENDIENTE' as estado \n";
                strSql+="       FROM tbm_cabCotVen AS a1  \n";
                strSql+="       INNER JOIN tbm_tipSolResInv as a4 ON (a1.co_tipSolResInv=a4.co_tipSolResInv) \n";
                strSql+="       WHERE a1.st_reg IN ('A','U','E','L') AND a1.st_solResInv IS NOT NULL AND \n";
                strSql+="             a1.fe_autSolResInv<=CURRENT_DATE  AND a1.st_autSolResInv='A' AND a4.tx_tipResInv='F' AND \n";
                strSql+="             a4.co_tipSolResInv IN (1,2,3,4) AND a4.st_reg='A' AND \n ";
                strSql+="             (EXTRACT(MONTH FROM CURRENT_DATE) -1 = EXTRACT(MONTH FROM a1.fe_cot) AND   \n";
                strSql+="               EXTRACT(YEAR FROM CURRENT_DATE) = EXTRACT(YEAR FROM a1.fe_cot) ) \n";
                strSql+="       UNION \n";
                strSql+="       SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot,a1.st_reg, a1.tx_nomCli,'FACTURADO' as estado    \n";
                strSql+="       FROM tbm_cabCotVen AS a1 \n";
                strSql+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.ne_numCot) \n";
                strSql+="       INNER JOIN tbm_tipSolResInv as a4 ON (a1.co_tipSolResInv=a4.co_tipSolResInv)   \n";
                strSql+="       WHERE a1.st_reg IN ('F') AND a1.st_solResInv IS NOT NULL AND a1.st_autSolResInv='A' AND  \n";
                strSql+="             a2.fe_doc = CURRENT_DATE AND a4.tx_tipResInv = 'F' AND  \n";
                strSql+="             a4.tx_tipResInv='F' AND a4.st_reg='A' AND \n";
                strSql+="             (EXTRACT(MONTH FROM CURRENT_DATE) -1 = EXTRACT(MONTH FROM a1.fe_cot) AND   \n";
                strSql+="               EXTRACT(YEAR FROM CURRENT_DATE) = EXTRACT(YEAR FROM a1.fe_cot) ) \n";
                strSql+="  ) as x \n";
                strSql+=" ORDER BY x.fe_cot, x.co_emp,x.co_loc,x.co_cot  \n";
                System.out.println("notificacionFacturaAutomatica: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    switch(rstLoc.getInt("co_emp"))
                    {
                        case 1: strFacAutTuv+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                        case 2: 
                            switch(rstLoc.getInt("co_loc")){
                                case 1: strFacAutCasUIO+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 11: strFacAutCasUIO+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break;
                                case 4: strFacAutCasMAN+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break;
                                case 12:strFacAutCasMAN+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break;
                                case 6: strFacAutCasSTO+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 13:strFacAutCasSTO+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break;
                                case 10: strFacAutCasCUE+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 14: strFacAutCasCUE+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break;
                            }
                        break;
                        case 4: 
                            switch(rstLoc.getInt("co_loc")){
                                case 3 :strFacAutDim+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 10:strFacAutDim+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 12:strFacAutDimDur+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                                case 13:strFacAutDimDur+=obtenerDatoFacturaAutomatica(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot")); break; 
                            }
                        break;   
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
     } 
     
     
     /**
      * 
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     
     private String obtenerDatoFacturaAutomatica(int CodEmp, int CodLoc, int CodCot){
         String strRes="", strFacGenAut="",strFacPen="";
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc,rstLoc2;
         try{
             conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,ROUND(a1.nd_tot,2) as nd_tot, \n";
                strSql+="        a2.tx_nomCli, a2.ne_numDoc, a2.fe_doc ,a3.tx_nom as tx_nomEmp,a4.tx_nom as tx_nomLoc \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_cabMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.ne_numCot) \n";
                strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp) \n";
                strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" ";
                System.out.println("obtenerDatoFacturaAutomatica " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strFacGenAut+="<tr><td>"+rstLoc.getString("tx_nomEmp")+"</td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("tx_nomLoc")+" </td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("co_cot")+" </td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("fe_cot")+" </td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("tx_nomCli")+" </td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("nd_tot")+" </td>";
                    strFacGenAut+=" <td> FACTURADO </td>";
                    strFacGenAut+=" <td>"+rstLoc.getString("ne_numDoc")+" </td> </tr>";
                }else{
                    strSql="";
                    strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,ROUND(a1.nd_tot,2) as nd_tot, \n";
                    strSql+="         a3.tx_nom as tx_nomEmp,a4.tx_nom as tx_nomLoc, a5.tx_nom as tx_nomCLi \n";
                    strSql+=" FROM tbm_cabCotVen AS a1  \n";
                    strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp) \n";
                    strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc) \n";
                    strSql+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli) \n";
                    strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" ";
                    rstLoc2 = stmLoc.executeQuery(strSql);
                    if(rstLoc2.next()){ 
                        strFacPen+="<tr> <td><a style='color: #FF0000;'>"+rstLoc2.getString("tx_nomEmp")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc2.getString("tx_nomLoc")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc2.getString("co_cot")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc2.getString("fe_cot")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc2.getString("tx_nomCli")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc2.getString("nd_tot")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'> PENDIENTE </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'> SIN FACTURA </a></td> </tr>";
                    }
                    rstLoc2.close();
                    rstLoc2=null;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
            strRes+=strFacPen+" "+strFacGenAut;
         }
         catch (SQLException e) {
            strRes="ERROR: " + e.toString();
            System.err.println("ERROR: " + e);
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            strRes="ERROR: " + e.toString();
        }
         return strRes;
     }
    
     /**
      * 
      * @return 
      */
     
    private boolean armarCadenaFacturaAutomatica(){
         boolean blnRes=true;
         String strAux="", strTit, strFin;
         strTit="<html> Notificaciones Sistema de Reserva. <BR><BR>" ;// CAMBIA
         strTit+=" <table BORDER=1><tr><td> EMPRESA </td> <td> LOCAL </td><td> COTIZACION </td><td> FECHA COTIZACION </td> ";   
         strTit+="                     <td> CLIENTE </td><td> TOTAL </td><td> ESTADO </td><td> FACTURA </td> </tr>";  
         strFin=" </table><BR> <html>";
         try{
             if(strFacAutTuv.length()>0){
                 strAux = strFacAutTuv;
                 strFacAutTuv=strTit;  
                 strFacAutTuv+=strAux;
                 strFacAutTuv+=strFin;
             }
             strAux="";
             if(strFacAutCasUIO.length()>0){
                 strAux = strFacAutCasUIO;
                 strFacAutCasUIO=strTit;  
                 strFacAutCasUIO+=strAux;
                 strFacAutCasUIO+=strFin;
             }
             strAux="";
             if(strFacAutCasMAN.length()>0){
                 strAux = strFacAutCasMAN;
                 strFacAutCasMAN=strTit;  
                 strFacAutCasMAN+=strAux;
                 strFacAutCasMAN+=strFin;
             }
             strAux="";
             if(strFacAutCasSTO.length()>0){
                 strAux = strFacAutCasSTO;
                 strFacAutCasSTO=strTit;  
                 strFacAutCasSTO+=strAux;
                 strFacAutCasSTO+=strFin;
             }
             strAux="";
             if(strFacAutCasCUE.length()>0){
                 strAux = strFacAutCasCUE;
                 strFacAutCasCUE=strTit;  
                 strFacAutCasCUE+=strAux;
                 strFacAutCasCUE+=strFin;
             }
             strAux="";
             if(strFacAutDim.length()>0){
                 strAux = strFacAutDim;
                 strFacAutDim=strTit;  
                 strFacAutDim+=strAux;
                 strFacAutDim+=strFin;
             }
             strAux="";
             
             if(strFacAutDimDur.length()>0){
                 strAux = strFacAutDimDur;
                 strFacAutDimDur=strTit;  
                 strFacAutDimDur+=strAux;
                 strFacAutDimDur+=strFin;
             }
             strAux="";
             
             
             
         }   
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
         return blnRes;
     }
     
     
     
}
