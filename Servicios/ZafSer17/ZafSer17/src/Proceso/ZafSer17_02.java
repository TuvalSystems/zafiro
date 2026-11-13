/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proceso;

import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafGenDocIngEgrInvRes.ZafGenDocIngEgrInvRes;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 * ZafSer17_02: Cancelacion de Reservas 
 * 
 * @author Sistemas6
 */
public class ZafSer17_02 {
     private ZafParSis objParSis;
     private String strSql,strVersion="Sistema Reserva: Cancelaciones v 0.1";
     private String strCorEleErr="sistemas6@tuvalsa.com;sistemas9@tuvalsa.com";
    
     private ZafCorEle objCorEle;
     private Librerias.ZafUtil.ZafUtil objUti;
     
     public ZafSer17_02(){
         System.out.println(strVersion);
         objParSis = new ZafParSis();
         objUti = new ZafUtil();
         objCorEle = new ZafCorEle(objParSis);
     }
     
     /**
      * Metodo publico, debe ser llamado para mandar a cancelar todas las cotizaciones que se encuentren en estado de reserva
      * 
      * @return True, si logra procesar todos los docuemtos
      */
     
     public boolean iniciaCancelacionReservas(){
        boolean blnRes=true, blnIsCancelacion=false;
        java.sql.Connection conLoc, conCan;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc != null){
                stmLoc = conLoc.createStatement();
                stmLoc01 = conLoc.createStatement();
                strSql=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.nd_tot, \n";
                strSql+="        a1.tx_nomCli  , a2.ne_numDiaNotSolResInvPorVen, a2.ne_numDiaNotSolResInvVen \n";
                strSql+=" FROM tbm_cabCotVen AS a1  \n";
                strSql+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) \n";
                strSql+=" INNER JOIN tbm_tipSolResInv AS a3 ON (a1.co_tipSolResInv=a3.co_tipSolResInv) ";
                strSql+=" WHERE a1.st_reg IN ('A','U','E','P') AND a1.st_solResInv='P' AND a1.st_autSolResInv='A' AND a3.tx_tipResInv='R' ";
                strSql+=" ORDER BY a1.fe_cot, a1.co_emp,a1.co_loc,a1.co_cot ";
                System.out.println("Cancelacion 1:" + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
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
                    strSql+="                CASE WHEN CURRENT_DATE  > a6.fe_venMenosDiasNotificacion THEN 'REVERSAR' ELSE '' END END AS COMENTARIO2, a7.tx_momDes    ";
                    strSql+=" FROM tbm_cabCotVen AS a1  \n";
                    strSql+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)  \n";
                    strSql+=" INNER JOIN tbm_loc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc)  \n";
                    strSql+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)  \n";
                    strSql+=" INNER JOIN ( \n";
                    strSql+="       SELECT a2.co_emp, a2.co_loc, a1.fe_dia as fe_venMenosDiasNotificacion   \n";
                    strSql+="       FROM tbm_calCiu as a1  \n";
                    strSql+="       INNER JOIN tbm_loc as a2 ON (a1.co_ciu=a2.co_ciu)  \n";
                    strSql+="       INNER JOIN tbm_cabCotVen as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc) \n";
                    strSql+="       WHERE (a3.fe_autSolResInv + "+rstLoc.getString("ne_numDiaNotSolResInvVen") +") <= a1.fe_Dia AND \n";
                    strSql+="              a1.tx_tipDia='L' AND a3.co_emp="+rstLoc.getInt("co_emp")+" AND a3.co_loc="+rstLoc.getInt("co_loc")+"  AND a3.co_cot="+rstLoc.getInt("co_cot")+"  \n";
                    strSql+="       ORDER BY a1.fe_dia ASC  \n";
                    strSql+="       LIMIT 1		 \n";
                    strSql+=" ) as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc)\n";
                    strSql+=" INNER JOIN tbm_tipSolResInv as a7 ON (a1.co_tipSolResInv=a7.co_tipSolResInv)  \n";
                    strSql+=" WHERE a1.co_emp="+rstLoc.getInt("co_emp")+" AND a1.co_loc="+rstLoc.getInt("co_loc")+" AND a1.co_cot="+rstLoc.getInt("co_cot")+" \n";
                    strSql+=" ORDER BY a1.co_emp,a1.co_loc,a1.co_cot  \n";
                    System.out.println("Cancelacion 2:" + strSql);
                    rstLoc01=stmLoc01.executeQuery(strSql);
                    if(rstLoc01.next()){
                        if(rstLoc01.getString("COMENTARIO2").equals("REVERSAR")){
                            if(validaCotizacionIngresosCompletados(conLoc,rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"),rstLoc.getInt("co_cot"))){
                                conCan=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                if(conCan != null){
                                    conCan.setAutoCommit(false);
                                    if(cancelarDocumento(conCan, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                      if(colocarInventarioDisponible(conCan, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                            if(disponiblesNegativos(conCan, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                                conCan.commit();
                                                blnIsCancelacion=true;
                                                if(notificacionesCancelacionesCorreo( rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"))){
                                                    enviarCorreoCotizacionCanceladaSupervisores(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_cot"));
                                                    System.out.println("ok cancelacion v 0.3"); 
                                                }
//                                                enviarCorreoCotizacionCancelada(rstLoc.getInt("co_emp"),rstLoc.getInt("co_loc"),rstLoc.getInt("co_cot"));
                                            }else{blnRes=false; conCan.rollback(); }
                                        }else{blnRes=false; conCan.rollback(); }
                                    }else{blnRes=false; conCan.rollback();}
                                }
                                conCan.close();
                                conCan=null;
                            }
                            else{
                                System.err.println("COTIZACION PENDIENTE INGRESOS xD CAncelacio " + rstLoc.getInt("co_cot"));
                            }
                            
                        }
                    }
                }
                
                if(blnIsCancelacion){
                    objNotCorEle = new ZafNotCorEle(objParSis);
                    if(armarCadenaFacturaManualCancelada()){
                        if(enviarNotificacionFacturaManualCancelada()){
                           System.out.println("OK Manuales Canceladas");
                        }else{blnRes=false;}
                    }else{blnRes=false;}
                }
                
                
                if(cancelarSolicitudesPendientesAutorizar(conLoc)){
                    System.out.println("ok cancelacion solicitudes pendientes");
                }
                
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException Evt){ 
            System.out.println("ERROR iniciaCancelacionReservas:" + Evt.toString()); 
            blnRes=false; 
        }
        catch (Exception e) {
            System.err.println("ERROR iniciaCancelacionReservas: " + e);
            blnRes=false;
        }
        return blnRes;
     }
     
     
     private boolean cancelarSolicitudesPendientesAutorizar(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt != null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_cabCotVen SET st_solResInv='C', st_autSolResInv='D', st_reg='F',   \n";
                strSql+="                          tx_obsAutSolResInv='Zafiro: Denegada Automaticamente por el Sistema Zafiro'  \n";
                strSql+=" FROM( \n";
                strSql+="        SELECT a1.co_tipSolResInv,a1.fe_autSolResInv,CASE WHEN current_date > (a1.fe_autSolResInv - 2) THEN 'PILAS' ELSE '' END AS X, \n";
                strSql+="               a1.st_solResInv,a1.fe_solResInv,a3.tx_usr, a1.co_emp,a1.co_loc,a1.co_cot,a1.fe_cot,a1.co_cli,a2.tx_nom as tx_nomCli,  \n";
                strSql+="               a1.nd_sub,a1.nd_tot,a1.tx_obs2,a1.st_reg, \n";
                strSql+="               a1.fe_solResInv,a1.tx_obsSolResInv,a1.fe_ingSolResInv,   \n";
                strSql+="               a1.st_autSolResInv,  a1.fe_ingAutSolResInv, a1.tx_comIngAutSolResInv,  \n";
                strSql+="               a1.co_usrIngAutSolResInv,a4.tx_usr,a1.co_usrIng,a3.tx_corEle, \n";
                strSql+="               (a1.fe_autSolResInv - 3) as fechaMenosDias_iniciaNotificaciones   \n";
                strSql+="        FROM tbm_cabCotVen as a1  \n";
                strSql+="        INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                strSql+="        INNER JOIN tbm_usr as a3 ON (a1.co_usrIng=a3.co_usr) \n";
                strSql+="        LEFT OUTER JOIN tbm_usr as a4 ON (a1.co_usrIngAutSolResInv=a4.co_usr) \n";
                strSql+="        WHERE a1.st_solResInv ='P' AND a1.st_autSolResInv IN ('P')  AND a1.st_reg<>'I' AND a1.fe_autSolResInv IS NULL AND  \n";
                strSql+="              a1.fe_solResInv < current_date   \n";
                strSql+="        ORDER BY  a1.co_tipSolResInv DESC,   a1.fe_autSolResInv \n";
                strSql+=" ) as a \n";
                strSql+=" WHERE tbm_cabCotVen.co_emp=a.co_emp AND  \n";
                strSql+="       tbm_cabCotVen.co_loc=a.co_loc AND  \n";
                strSql+="       tbm_cabCotVen.co_cot=a.co_cot   ; \n";
                strSql+=" \n";
                System.out.println("cancelarSolicitudesPendientesAutorizar: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            System.out.println("ERROR cancelarSolicitudesPendientesAutorizar:" + Evt.toString()); 
            blnRes=false; 
        }
        catch (Exception e) {
            System.err.println("ERROR cancelarSolicitudesPendientesAutorizar: " + e);
            blnRes=false;
        }
        return blnRes;
     }
             
              
     
     
     
    private String strFacManTuv="",strFacManCasUIO="",strFacManCasSTO="",strFacManCasMAN="",strFacManCasCUE="",strFacManDim=""; //Facturas Manuales
    private String strFacManTuvDir="",strFacManCasUIODir="",strFacManCasSTODir="",strFacManCasMANDir="",strFacManCasCUEDir="",strFacManDimDir=""; //Facturas Manuales // Directivos
    private String  strFacManDimDur="", strFacManDimDurDir="" ;
     
    
    private boolean notificacionesCancelacionesCorreo(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        try{
            if(notificacionFacturaManualCancelada(CodEmp, CodLoc, CodCot)){
                
            } 
            else{blnRes=false;}
        }
        catch (Exception e) {
            System.err.println("ERROR iniciaCancelacionReservas: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
     private boolean notificacionFacturaManualCancelada(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
        try{
            switch(CodEmp){
                case 1: strFacManTuv+=obtenerDatoFacturaManualCancelada(CodEmp, CodLoc, CodCot ); 
                        break; 
                case 2: 
                    switch(CodLoc){
                        case 1: strFacManCasUIO+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break; 
                        case 11:strFacManCasUIO+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break;
                        case 4: strFacManCasMAN+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break;
                        case 12:strFacManCasMAN+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break;
                        case 6: strFacManCasSTO+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break; 
                        case 13:strFacManCasSTO+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break;
                        case 10:strFacManCasCUE+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break; 
                        case 14:strFacManCasCUE+=obtenerDatoFacturaManualCancelada(CodEmp,CodLoc, CodCot ); 
                                break;      
                    }
                case 4: 
                    switch(CodLoc){
                        case 3: strFacManDim+=obtenerDatoFacturaManualCancelada(CodEmp, CodLoc, CodCot ); 
                            break;
                        case 10: strFacManDim+=obtenerDatoFacturaManualCancelada(CodEmp, CodLoc, CodCot ); 
                            break;
                        case 12: strFacManDimDur+=obtenerDatoFacturaManualCancelada(CodEmp, CodLoc, CodCot ); 
                            break;
                        case 13: strFacManDimDur+=obtenerDatoFacturaManualCancelada(CodEmp, CodLoc, CodCot ); 
                            break;
                    }
            }
        }
        catch (Exception e) {
            System.err.println("ERROR iniciaCancelacionReservas: " + e);
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
                    if(validacionCancelacionGeneraCosto(conLoc,CodEmp,CodLoc,CodCot) ){
                        strFacPen+="<tr><td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomEmp")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomLoc")+" </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("co_cot")+" </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_cot")+" </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("tx_nomCli")+" </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_tot")+" </a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("fe_autSolResInv")+"</a></td>";
                        strFacPen+=" <td><a style='color: #FF0000;'>"+rstLoc.getString("nd_canPenFac")+"</a></td> </tr>";
                    }
                    else{
                        strFacPen+="<tr><td>"+rstLoc.getString("tx_nomEmp")+"</td>";
                        strFacPen+=" <td>"+rstLoc.getString("tx_nomLoc")+" </td>";
                        strFacPen+=" <td>"+rstLoc.getString("co_cot")+" </td>";
                        strFacPen+=" <td>"+rstLoc.getString("fe_cot")+" </td>";
                        strFacPen+=" <td>"+rstLoc.getString("tx_nomCli")+" </td>";
                        strFacPen+=" <td>"+rstLoc.getString("nd_tot")+" </td>";
                        strFacPen+=" <td>"+rstLoc.getString("fe_autSolResInv")+"</td>";
                        strFacPen+=" <td>"+rstLoc.getString("nd_canPenFac")+"</td> </tr>";
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
     
    
    private boolean validacionCancelacionGeneraCosto(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_bodOrg, a1.co_bodDes \n";
                strSql+=" FROM tbm_cabSolTraInv as a1  \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabSolTraInv AND a1.co_loc=a2.co_locRelCabSolTraInv AND \n";
                strSql+="                                       a1.co_tipDoc=a2.co_tipDocRelCabSolTraInv AND a1.co_doc=a2.co_docRelCabSolTraInv)  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a2.co_seg  \n";
                strSql+="       FROM tbm_cabCotVen as a1  \n";
                strSql+="       INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND \n";
                strSql+="                                             a1.co_cot=a2.co_cotRelCabCotVen) \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" \n";
                strSql+=" ) as a3 ON (a2.co_seg=a3.co_seg) \n";
                strSql+=" WHERE a1.co_bodOrg <> a1.co_bodDes \n";
                strSql+=" \n";
                System.out.println("validacionCancelacionGeneraCosto " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
	    }
        }
        catch(java.sql.SQLException Evt){ 
            System.out.println("ERROR enviarCorreoCotizacionCancelada:" + Evt.toString()); 
            blnRes=false;
        }
        catch (Exception e) {
            System.err.println("ERROR enviarCorreoCotizacionCancelada: " + e);
            blnRes=false;
        }
        return blnRes;
    }
     
     
     private void enviarCorreoCotizacionCancelada(int CodEmp, int CodLoc, int CodCot){
         String strAux="", strMensaje,strTit,strFin,strNomCli="",strNomLoc="",strFecCot="",strCodCot="",strCorEleDes="";
         java.sql.Connection conLoc;
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;        
         try{
             conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
             if(conLoc!=null){
                 stmLoc=conLoc.createStatement();
                 strSql="";
                 strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_cli,a1.fe_cot, a3.tx_nom as tx_nomCli, a2.co_reg, \n";
                 strSql+="        a2.co_itm,a2.tx_codAlt,ROUND(a2.nd_can,2) as nd_can, \n";
                 strSql+="        ROUND(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END,2) AS nd_canFac, \n";
                 strSql+="        ROUND(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END,2) AS nd_canCan, \n";
                 strSql+="          CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END AS nd_valFac, \n";
                 strSql+="          CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END AS nd_valCan, \n";
                 strSql+="          a4.tx_usr, a4.tx_corEle,a5.tx_nom as tx_nomLoc \n";
                 strSql+=" FROM tbm_cabCotVen as a1 \n";
                 strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                 strSql+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                 strSql+=" INNER JOIN tbm_usr as a4 ON (a1.co_usrIng=a4.co_usr) \n";
                 strSql+=" INNER JOIN tbm_loc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc) ";
                 strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
                 strSql+=" ORDER BY a2.co_reg "; 
                 rstLoc = stmLoc.executeQuery(strSql);
                 while(rstLoc.next()){
                    strNomCli=rstLoc.getString("tx_nomCli");
                    strNomLoc=rstLoc.getString("tx_nomLoc");
                    strFecCot=rstLoc.getString("fe_cot");
                    strCodCot=rstLoc.getString("co_cot");
                    strCorEleDes=rstLoc.getString("tx_corEle");
                    
                    strAux+="<tr><td> "+rstLoc.getString("tx_codAlt")+" </td>";
                    strAux+=" <td> "+rstLoc.getString("nd_can")+"  </td>";
                    strAux+=" <td> "+rstLoc.getString("nd_canFac")+"  </td>";
                    strAux+=" <td> "+rstLoc.getString("nd_canCan")+" </td> </tr>";
                 }
                 strTit="<html> Notificación de Cancelación de Mercadería Reservada. <BR><BR>" ;// CAMBIA
                 strTit+=" <b> Local: </b>"+ strNomLoc +" <BR>";
                 strTit+=" <b> Cotizacion: </b>"+ strCodCot +" <BR>";
                 strTit+=" <b> Cliente: </b>"+ strNomCli +" <BR>";
                 strTit+=" <b> Fecha Cotizacion: </b>"+ strFecCot +" <BR><BR>";
                 strTit+=" <table BORDER=1><tr><td> Item </td> <td> Cant.Cotizada </td><td> Cant.Facturada </td><td> Cant.Cancelada </tr>";  
                 strFin=" </table><BR> <html>";
                 strMensaje=strTit+strAux+strFin;
                 objCorEle.enviarCorreoMasivo(strCorEleDes, strTituloCancelacion, strMensaje);
            }
             
         }
         catch(java.sql.SQLException Evt){ 
            System.out.println("ERROR enviarCorreoCotizacionCancelada:" + Evt.toString()); 
         }
         catch (Exception e) {
            System.err.println("ERROR enviarCorreoCotizacionCancelada: " + e);
         }
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
     
    private ZafNotCorEle objNotCorEle;
    private static final int INT_ARL_COD_EMP_TUV=1;
    private static final int INT_ARL_COD_EMP_CAS=2;
    private static final int INT_ARL_COD_EMP_DIM=4;
     
    
    private static final int INT_ARL_COD_CFG_FAC_MAN_CAN=4;
   
    
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
        }
         catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        
        return blnRes;
    }
     
     private String strTituloCancelacion="ZafSer17: Reservas de inventario con facturación manual (Canceladas automáticamente por el Sistema)";
     
    /* Para solo pedir estos datos los demas la clase los obtiene */
    private static final int INT_ARL_DAT_COD_EMP=0;
    private static final int INT_ARL_DAT_COD_LOC=1;
    private static final int INT_ARL_DAT_COD_BOD_EMP=2;
    private static final int INT_ARL_DAT_COD_ITM_EMP=3;
    private static final int INT_ARL_DAT_CAN=4;
    private static final int INT_ARL_DAT_COS_UNI=5;
    private static final int INT_ARL_DAT_COD_SEG=6 ;
    
    private boolean insertarDocumentoIngreso(java.sql.Connection conExt,int CodEmp,int CodLoc,int CodCot){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        ArrayList arlDatCot, arlRegCot;
        java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
        try{
            if(conExt!=null){
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                stmLoc = conExt.createStatement();
                arlDatCot = new ArrayList();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.co_bod, a2.nd_can,  \n";
                strSql+="        CASE WHEN a3.nd_cosUni IS NULL THEN 0 ELSE a3.nd_cosUni END as nd_cosUni,a4.co_seg ";
                strSql+=" FROM tbm_cabCotVen as a1  \n";
                strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a4 ON (a1.co_emp=a4.co_empRelCabCotVen AND a1.co_loc=a4.co_locRelCabCotVen AND \n";
                strSql+="                                       a1.co_cot=a4.co_cotRelCabCotVen) \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" and a3.st_ser='N' \n";
                strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlRegCot = new ArrayList();
                    arlRegCot.add(INT_ARL_DAT_COD_EMP, rstLoc.getInt("co_emp"));
                    arlRegCot.add(INT_ARL_DAT_COD_LOC, rstLoc.getInt("co_loc"));
                    arlRegCot.add(INT_ARL_DAT_COD_BOD_EMP, rstLoc.getInt("co_bod"));
                    arlRegCot.add(INT_ARL_DAT_COD_ITM_EMP, rstLoc.getInt("co_itm"));
                    arlRegCot.add(INT_ARL_DAT_CAN, rstLoc.getDouble("nd_can"));
                    arlRegCot.add(INT_ARL_DAT_COS_UNI, rstLoc.getDouble("nd_cosUni"));
                    arlRegCot.add(INT_ARL_DAT_COD_SEG, rstLoc.getInt("co_seg"));
                    arlDatCot.add(arlRegCot);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                if(!arlDatCot.isEmpty()){
                    objParSis.setCodigoMenu(4146);
                    ZafGenDocIngEgrInvRes objGenDocIngEgrInvRes = new ZafGenDocIngEgrInvRes(objParSis,null);
                    if(objGenDocIngEgrInvRes.GenerarDocumentoIngresoEgresoReservas(conExt, datFecAux, arlDatCot, 0)){
                        blnRes=true;
                    }
                }
            }
        }
        catch(java.sql.SQLException Evt){ 
            System.err.println("ERROR:" + Evt.toString()); 
            blnRes=false; 
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
    
     /**
      * Cancelacion de Documentos
      * Parciales y totales
      * @param conExt
      * @param CodEmp
      * @param CodLoc
      * @param CodCot
      * @return 
      */
     
     private boolean cancelarDocumento(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
         java.sql.Statement stmLoc;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";//
                strSql+=" UPDATE tbm_detCotVen SET  nd_canCan=a1.nd_canPenFac,   nd_canPenFac=0, \n";
                strSql+="                           nd_valCan=a1.nd_valCanCal  \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT a1.*, (a1.totalCalculado - a1.nd_valFac) as  nd_valCanCal  \n";
                strSql+="       FROM (  \n";
                strSql+="           SELECT a2.co_emp, a2.co_loc,a2.co_cot,a2.co_reg, a2.nd_can,  \n";
                strSql+="                  CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END AS nd_canFac,  \n";
                strSql+="                  CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END  AS nd_canCan, \n";
                strSql+="                  CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END  AS nd_canPenFac, \n";
                strSql+="                  CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END  AS nd_valFac,  \n";
                strSql+="                  CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END  AS nd_valCan,  \n";
                strSql+="                  CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END  AS nd_canAutRes,  \n";
		strSql+="                  SUM((ROUND( a2.nd_preUni* a2.nd_can - (a2.nd_preUni* a2.nd_can)*a2.nd_porDes/100,2) )) as totalCalculado  \n";
                strSql+="           FROM tbm_cabCotVen as a1  \n";
                strSql+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="           WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND \n";
		strSql+="                 a1.st_solResInv='P' AND a1.st_autSolResInv = 'A' AND a1.co_tipSolResInv=5 AND a1.st_reg IN ('A','U','E') \n";
                strSql+="           GROUP BY a2.co_emp, a2.co_loc,a2.co_cot,a2.co_reg    \n";
                strSql+="       ) as a1 \n";
                strSql+=" ) as a1 \n";
		strSql+=" WHERE tbm_detCotVen.co_emp=a1.co_emp AND  \n";  
		strSql+="       tbm_detCotVen.co_loc=a1.co_loc AND \n";
		strSql+="       tbm_detCotVen.co_cot=a1.co_cot AND \n";
		strSql+="       tbm_detCotVen.co_reg=a1.co_reg; \n";
                System.out.println("CancelarDocumento 1: " + strSql);
                stmLoc.executeUpdate(strSql);
                
                strSql="";
                strSql+=" UPDATE tbm_cabCotVen SET st_solResInv = 'C' , nd_valSolResInvCan = a1.nd_valPenFac, st_reg='F', \n";
                strSql+="                          tx_obsAutSolResInv='Zafiro: Cancelada Automaticamente por el Sistema.'  ";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a1.*, (a1.nd_sub-a1.nd_valFac) as nd_valPenFac \n";
                strSql+="       FROM ( \n";
                strSql+="           SELECT a2.co_emp, a2.co_loc,a2.co_cot,a1.nd_sub, SUM(a2.nd_can) AS nd_can, \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END) AS nd_canFac,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END) AS nd_canCan,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END) AS nd_canPenFac,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END) AS nd_valFac , \n";
                strSql+="                  SUM(CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END) AS nd_valCan,  \n";
                strSql+="                  SUM(CASE WHEN a2.nd_canAutRes IS NULL THEN 0 ELSE a2.nd_canAutRes END) AS nd_canAutRes , \n";
                strSql+="                  SUM((ROUND( a2.nd_preUni* a2.nd_can - (a2.nd_preUni* a2.nd_can)*a2.nd_porDes/100,2) )) as totalCalculado   \n";
                strSql+="           FROM tbm_cabCotVen as a1  \n";
                strSql+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot)  \n";
                strSql+="           WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+" AND \n";
                strSql+="                 a1.st_solResInv <> 'C' AND  a1.st_autSolResInv = 'A' AND a1.co_tipSolResInv=5 AND a1.st_reg IN ('A','U','E')  \n";
		strSql+="           GROUP BY a2.co_emp, a2.co_loc,a2.co_cot, a1.nd_sub \n";
		strSql+="       ) as a1 \n";
                strSql+=" ) as a1  \n";
                strSql+=" WHERE tbm_cabCotVen.co_emp=a1.co_emp AND  \n";
                strSql+="       tbm_cabCotVen.co_loc=a1.co_loc AND  \n";
                strSql+="       tbm_cabCotVen.co_cot=a1.co_cot; \n";
                System.out.println("CancelarDocumento 2: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
         }
         catch(java.sql.SQLException Evt){ 
            System.err.println("ERROR cancelarDocumento:" + Evt.toString()); 
            blnRes=false; 
        }
         catch (Exception e) {
            System.err.println("ERROR cancelarDocumento: " + e);
            blnRes=false;
         }
         return blnRes;
     }
     
     
    
    private static final int INT_ARL_COD_CFG_FAC_MAN_CAN_SUPER=10;
        
    private void enviarCorreoCotizacionCanceladaSupervisores(int CodEmp, int CodLoc, int CodCot){
       String strAux="", strMensaje,strTit,strFin,strNomCli="",strNomLoc="",strFecCot="",strCodCot="",strCorEleDes="";
       java.sql.Connection conLoc;
       java.sql.Statement stmLoc;
       java.sql.ResultSet rstLoc;        
       try{
           conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conLoc!=null){
               stmLoc=conLoc.createStatement();
               strSql="";
               strSql+=" SELECT a1.co_emp,a1.co_loc,a1.co_cot,a1.co_cli,a1.fe_cot, a3.tx_nom as tx_nomCli, a2.co_reg, \n";
               strSql+="        a2.co_itm,a2.tx_codAlt,a6.tx_codAlt2,CASE WHEN a7.st_impOrd='S' THEN a7.tx_ubi ELSE '' end as tx_ubi, \n";
               strSql+="        ROUND(a2.nd_can,2) as nd_can, \n";
               strSql+="        ROUND(CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END,2) AS nd_canFac, \n";
               strSql+="        ROUND(CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END,2) AS nd_canCan, \n";
               strSql+="          CASE WHEN a2.nd_valFac IS NULL THEN 0 ELSE a2.nd_valFac END AS nd_valFac, \n";
               strSql+="          CASE WHEN a2.nd_valCan IS NULL THEN 0 ELSE a2.nd_valCan END AS nd_valCan, \n";
               strSql+="          a4.tx_usr, a4.tx_corEle,a5.tx_nom as tx_nomLoc, \n";
               strSql+="        ROUND(a2.nd_can - (CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END) ,2) as cancelar   \n";
               strSql+=" FROM tbm_cabCotVen as a1 \n";
               strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
               strSql+=" INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
               strSql+=" INNER JOIN tbm_usr as a4 ON (a1.co_usrIng=a4.co_usr) \n";
               strSql+=" INNER JOIN tbm_loc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc) \n";
               strSql+=" INNER JOIN tbm_inv as a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)  \n";
               strSql+=" INNER JOIN tbm_invBod as a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod AND a2.co_itm=a7.co_itm) \n";
               strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"  \n";
               strSql+=" ORDER BY a2.co_reg \n"; 
               System.out.println("enviarCorreoCotizacionCancelada: " + strSql);
               rstLoc = stmLoc.executeQuery(strSql);
               while(rstLoc.next()){
                   strNomCli=rstLoc.getString("tx_nomCli");
                   strNomLoc=rstLoc.getString("tx_nomLoc");
                   strFecCot=rstLoc.getString("fe_cot");
                   strCodCot=rstLoc.getString("co_cot");
                   strCorEleDes=rstLoc.getString("tx_corEle");

                   strAux+="<tr><td> "+rstLoc.getString("tx_codAlt")+" </td>";
                   strAux+=" <td> "+rstLoc.getString("tx_codAlt2")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("tx_ubi")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("nd_can")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("nd_canFac")+"  </td>";
                   strAux+=" <td> "+rstLoc.getString("cancelar")+" </td> </tr>";
               }
               strTit="<html> Notificación de Cancelación de Mercadería Reservada Automatica. <BR><BR>" ;// CAMBIA
               strTit+=" <b> Local: </b>"+ strNomLoc +" <BR>";
               strTit+=" <b> Cotizacion: </b>"+ strCodCot +" <BR>";
               strTit+=" <b> Cliente: </b>"+ strNomCli +" <BR>";
               strTit+=" <b> Fecha Cotizacion: </b>"+ strFecCot +" <BR><BR>";
               strTit+=" <table BORDER=1><tr><td> Item </td><td> Cod.Alt.2 </td><td> Ubicación </td><td> Cant.Cotizada </td><td> Cant.Facturada </td><td> Cant.Cancelada </tr>";  
               strFin=" </table><BR> <html>";
               strMensaje=strTit+strAux+strFin;
                objCorEle.enviarCorreoMasivo(strCorEleDes+";sistemas6@tuvalsa.com", strTituloCancelacion, strMensaje);
               objNotCorEle = new ZafNotCorEle(objParSis);
               objNotCorEle.enviarNotificacionCorreoElectronico(CodEmp, CodLoc, INT_ARL_COD_CFG_FAC_MAN_CAN_SUPER, strMensaje) ;
               objNotCorEle = null;
               rstLoc.close();
               rstLoc=null;
               stmLoc.close();
               stmLoc=null;
               conLoc.close();
               conLoc=null;
           }

        }
        catch(java.sql.SQLException e){ 
           System.err.println("ERROR enviarCorreoCotizacionCanceladaSupervisores:" + e.toString()); 
        }
        catch (Exception e) {
           System.err.println("ERROR enviarCorreoCotizacionCanceladaSupervisores:" + e.toString()); 
        }
    }
     
     
     private String strTituloCancelacion2="Reservas de inventario con facturación manual (Canceladas manualmente)";
     
     private boolean colocarInventarioDisponible(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
         boolean blnRes=false;
          java.sql.Statement stmLoc;
         try{
             if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbm_invBod SET nd_canDis=CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END + a.nd_canCan, \n";
                strSql+="                       nd_canRes=CASE WHEN nd_canRes IS NULL THEN 0 ELSE nd_canRes END - a.nd_canCan  \n";
                strSql+=" FROM ( \n";
                strSql+="       select a2.tx_codAlt, a1.co_emp, a1.co_loc, a1.co_cot,a2.co_bod, a2.co_itm, a2.nd_can,   \n";
                strSql+="              CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END  AS nd_canCan  ";
                strSql+="       from tbm_cabCotVen as a1  \n";
                strSql+="       inner join tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                strSql+="       inner join tbm_invBod as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod) \n";
                strSql+="       where  a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"   \n";
                strSql+="       order by a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg	 \n";
                strSql+=" )as a  \n";
                strSql+=" WHERE tbm_invBod.co_emp=a.co_emp AND tbm_invBod.co_itm=a.co_itm AND tbm_invBod.co_bod=a.co_bod; \n";
                strSql+=" \n";
                 System.out.println("colocarInventarioDisponible: " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                blnRes=true;
            }
         }
         catch(java.sql.SQLException Evt){ 
            System.err.println("ERROR colocarInventarioDisponible:" + Evt.toString()); 
            blnRes=false; 
        }
         catch (Exception e) {
            System.err.println("ERROR colocarInventarioDisponible: " + e);
            blnRes=false;
         }
         return blnRes;
     }
     
        private boolean disponiblesNegativos(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodCot){
           boolean blnRes=true;
           java.sql.Statement stmLoc;
           java.sql.ResultSet rstLoc;
           try{
               if(conExt!=null){
                  stmLoc = conExt.createStatement();
                  strSql="";
                  strSql+=" SELECT a2.tx_codAlt, a1.co_emp, a1.co_loc, a1.co_cot, a2.co_itm, a2.nd_can, a3.nd_canDis \n";
                  strSql+=" FROM tbm_cabCotVen as a1  \n";
                  strSql+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                  strSql+=" INNER JOIN tbm_invBod as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm AND a2.co_bod=a3.co_bod) \n";
                  strSql+=" WHERE  a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" AND a1.co_cot="+CodCot+"   \n";
                  strSql+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_itm \n";
                   System.out.println("disponiblesNegativos:" + strSql);
                  rstLoc=stmLoc.executeQuery(strSql);
                  while(rstLoc.next()){
                      if(rstLoc.getDouble("nd_canDis")<0){
                          blnRes=false;
                      }
                  }
                  if(blnRes==false){
                      System.out.println("Negativo Cancelacion");
                      objCorEle.enviarCorreoMasivo(strCorEleErr, "RESERVAS CANCELACIONES: Negativos ", strSql);
                  }
                  stmLoc.close();
                  stmLoc=null;
                  blnRes=true;
              }
           }
           catch(java.sql.SQLException Evt){ 
              System.err.println("ERROR disponiblesNegativos:" + Evt.toString()); 
              blnRes=false; 
          }
           catch (Exception e) {
              System.err.println("ERROR disponiblesNegativos: " + e);
              blnRes=false;
           }
           return blnRes;
       }
        
        
        
    private boolean validaCotizacionIngresosCompletados(java.sql.Connection conExt,int CodEmp,int CodLoc, int CodCot){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT a2.co_seg, a1.co_emp, a1.co_loc, a1.co_cot \n";
                strSql+=" FROM tbm_cabCotVen as a1 \n";
                strSql+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabCotVen AND a1.co_loc=a2.co_locRelCabCotVen AND  \n";
                strSql+="                                       a1.co_cot=a2.co_cotRelCabCotVen)  \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+"  AND a1.co_cot="+CodCot+" \n";
                strSql+=" \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(isDocIngPenCnf(conExt,rstLoc.getInt("co_seg"), "I")){
                       blnRes=false; 
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch (Exception e) {
            System.err.println("ERROR: " + e);
            blnRes=false;
        }
        return blnRes;
    }
        
        private String strSQL="";
        
    /**
     * Función que permite obtener los documentos de ingresos que están pendientes de confirmar
     * @return true Si obtiene algún registro es porque aún no se debe confirmar
     * <BR> false Caso contrario
     */
    public boolean isDocIngPenCnf(Connection conExt
                                    , int codigoSeguimiento
                                    , String tipoOperacion){
        boolean blnRes=true;
        int intCodSeg=codigoSeguimiento;
        BigDecimal bdeCanIngBod=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCanPndCnf=BigDecimal.ZERO;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                if(tipoOperacion.equals("I")){
                    stmLoc=conExt.createStatement();
                    if(intCodSeg!=-1){                        
                        strSQL="";
                        strSQL+=" SELECT SUM(b1.nd_canPenCnf) AS nd_canPenCnf, SUM(b1.nd_canIngBod) AS nd_canIngBod, SUM(b1.nd_can) AS nd_can";
                        strSQL+=" , SUM((CASE WHEN b1.nd_canDesEntCli IS NULL THEN 0 ELSE b1.nd_canDesEntCli END)) AS nd_canDesEntCli";
                        strSQL+=" , SUM((CASE WHEN b1.nd_canEgrBod IS NULL THEN 0 ELSE b1.nd_canEgrBod END)) AS nd_canEgrBod";
                        strSQL+=" FROM(";
                        strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="    , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+="    , a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+="    FROM(";
                        strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+="            FROM tbm_cabsegmovinv AS a3 INNER JOIN tbm_cabMovInv AS a1";
                        strSQL+="            ON a3.co_empRelCabMovInv=a1.co_emp AND a3.co_locRelCabMovInv=a1.co_loc";
                        strSQL+="            AND a3.co_tipDocRelCabMovInv=a1.co_tipDoc AND a3.co_docRelCabMovInv=a1.co_doc";
                        strSQL+="            INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="            WHERE a3.co_seg=" + intCodSeg + "";
                        strSQL+="    ) AS a1";
                        strSQL+="    INNER JOIN(";
                        strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon";
                        //strSQL+="            , (a2.nd_can - a2.nd_canCon) AS nd_canPenCnf, a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+="            , (a2.nd_can - (CASE WHEN a2.nd_canDesEntCli IS NULL THEN 0 ELSE a2.nd_canDesEntCli END)) AS nd_canPenCnf, a2.nd_canIngBod, a2.nd_canEgrBod, a2.nd_canDesEntCli";
                        strSQL+="            FROM tbm_cabMovInv AS a1";
                        //strSQL+="            INNER JOIN tbm_detMovInv AS a2";
                        strSQL+="            INNER JOIN (tbm_detMovInv AS a2";
                        strSQL+="                       LEFT OUTER JOIN tbr_detMovInv AS a3";
                        strSQL+="                       ON a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                        strSQL+="                       )";
                        strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+="            WHERE a2.st_meringegrfisbod='S'";
                        strSQL+="            AND (ABS(a2.nd_can) - ABS(a2.nd_canCon)) <> 0";
                        strSQL+="            AND (a2.nd_canPen<>0 OR a2.nd_canPen IS NOT NULL)";
                        strSQL+="            AND a2.nd_can>0";
                        strSQL+=" ) AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                        strSQL+=" , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.st_meringegrfisbod, a2.nd_can, a2.nd_canCon, a2.nd_canPenCnf";
                        strSQL+=" , a2.nd_canIngBod, a2.nd_canDesEntCli, a2.nd_canEgrBod";
                        strSQL+=" ) AS b1";
                        System.out.println("isDocIngPenCnf: " + strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);
                        if(rstLoc.next()){
                            bdeCanIngBod=new BigDecimal(rstLoc.getObject("nd_canIngBod")==null?"0":(rstLoc.getString("nd_canIngBod").equals("")?"0":rstLoc.getString("nd_canIngBod")));
                            bdeCan=new BigDecimal(rstLoc.getObject("nd_can")==null?"0":(rstLoc.getString("nd_can").equals("")?"0":rstLoc.getString("nd_can")));//rst.getBigDecimal("nd_can");
                            bdeCanPndCnf=new BigDecimal(rstLoc.getObject("nd_canPenCnf")==null?"0":(rstLoc.getString("nd_canPenCnf").equals("")?"0":rstLoc.getString("nd_canPenCnf")));//rst.getBigDecimal("nd_can");
                            //bdeCanDesEntCli=new BigDecimal(rst.getObject("nd_canDesEntCli")==null?"0":(rst.getString("nd_canDesEntCli").equals("")?"0":rst.getString("nd_canDesEntCli")));//rst.getBigDecimal("nd_canDesEntCli");
                            //bdeCanEgrBod=new BigDecimal(rst.getObject("nd_canEgrBod")==null?"0":(rst.getString("nd_canEgrBod").equals("")?"0":rst.getString("nd_canEgrBod")));//rst.getBigDecimal("nd_canDesEntCli");
                            
                            System.out.println("**++ bdeCanIngBod: " + bdeCanIngBod);
                            System.out.println("**++ bdeCanPndCnf: " + bdeCanPndCnf);
                            
                            //se comento
                            if(bdeCanIngBod.compareTo(BigDecimal.ZERO)==0 ){
                                if(bdeCanPndCnf.compareTo(BigDecimal.ZERO)==0 ){
                                  
                                        blnRes=false;
                                }
                            }
                            //fin 
                        }
                        rstLoc.close();
                        rstLoc=null;
                    }
                    stmLoc.close();
                    stmLoc=null;
                }                
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        catch(Exception e){
            System.err.println("ERROR: " + e);
            blnRes=false;            
        }
        return blnRes;
    }
    
     
}
