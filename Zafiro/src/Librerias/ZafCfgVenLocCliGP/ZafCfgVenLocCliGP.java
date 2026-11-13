/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafCfgVenLocCliGP;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 *
 * @author Sistemas6
 */
public class ZafCfgVenLocCliGP {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSql;
    
    
    public ZafCfgVenLocCliGP(ZafParSis obj, java.awt.Component parent){
    
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
            System.out.println("ZafCfgVenLocCliGP v0.02");
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        
    }
    
    
    public boolean modificaConfiguracionActual(){
        boolean blnRes=false;
        try{
            if(inicia()){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }
    
    
    private boolean inicia(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            //conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            conLoc.setAutoCommit(false);
            if(conLoc!=null){
                if(procesoConfigurar(conLoc)){
                    if (procesoConfigurarActualizaEstado(conLoc)){
                   // if(clientesxVendedor(conLoc)){
                        blnRes=true;
                        conLoc.commit();
                   // }
                   //else{
                   //     conLoc.rollback();
                   // }
                }
                }
                else{
                    conLoc.rollback();
                }
                conLoc.close();
                conLoc=null;
            }
             
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            blnRes=false;            
        }
        return blnRes;
    }
    
    
    
    
    private boolean clientesxVendedor(java.sql.Connection conExt){
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        try {
            System.out.println("clientesxVendedor...");
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                String strCadena="";
                strCadena+=" /* TUVAL INMACONSA */ \n";
                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
                strCadena+=" FROM (	  \n";
                strCadena+="    SELECT CO_EMP, CO_LOC, tx_cli, co_ven   \n";
                strCadena+="    FROM tbr_cliLoc as a1  \n";
                strCadena+="    WHERE co_emp=1 and co_loc=4 and co_ven<>9 \n";
                strCadena+=" ) as a1  \n";
                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strCadena+=" tbr_cliLoc.co_loc=12 AND  \n";
                strCadena+=" tbr_cliLoc.tx_cli=a1.tx_cli ;  \n";
//                
//                
//                strCadena+=" /* CASTEK UIO - CASTEK UIO SUR */  \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="    SELECT CO_EMP, CO_LOC, tx_cli, co_ven   \n";
//                strCadena+="    FROM tbr_cliLoc as a1  \n";
//                strCadena+="    WHERE co_emp=2 and co_loc=1  and co_ven<>9	 \n";
//                strCadena+="    ) as a1   \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND     \n";
//                strCadena+="       tbr_cliLoc.co_loc=15 AND  \n";
//                strCadena+="       tbr_cliLoc.tx_cli=a1.tx_cli ;  \n";
//                
//                
//                
//                strCadena+=" /* CASTEK UIO INMACONSA */  \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="    SELECT CO_EMP, CO_LOC, tx_cli, co_ven   \n";
//                strCadena+="    FROM tbr_cliLoc as a1  \n";
//                strCadena+="    WHERE co_emp=2 and co_loc=1  and co_ven<>9	 \n";
//                strCadena+="    ) as a1   \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND     \n";
//                strCadena+="       tbr_cliLoc.co_loc=11 AND  \n";
//                strCadena+="       tbr_cliLoc.tx_cli=a1.tx_cli ;  \n";
//                
//                strCadena+=" /* CASTEK UIO SUR INMACONSA */  \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="    SELECT CO_EMP, CO_LOC, tx_cli, co_ven   \n";
//                strCadena+="    FROM tbr_cliLoc as a1  \n";
//                strCadena+="    WHERE co_emp=2 and co_loc=15  and co_ven<>9	 \n";
//                strCadena+="    ) as a1   \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND     \n";
//                strCadena+="       tbr_cliLoc.co_loc=16 AND  \n";
//                strCadena+="       tbr_cliLoc.tx_cli=a1.tx_cli ;  \n";
//                
//                strCadena+=" /* CASTEK MANTA INMACONSA */ \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="     SELECT CO_EMP, CO_LOC, tx_cli, co_ven  \n";
//                strCadena+="     FROM tbr_cliLoc as a1  \n";
//                strCadena+="     WHERE co_emp=2 and co_loc=4 and co_ven<>9	 \n";
//                strCadena+=" ) as a1  \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
//                strCadena+="        tbr_cliLoc.co_loc=12 AND   \n";
//                strCadena+="        tbr_cliLoc.tx_cli=a1.tx_cli ;  \n";
//                strCadena+=" /* CASTEK STO DOM INMACONSA */ \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="        SELECT CO_EMP, CO_LOC, tx_cli, co_ven   \n";
//                strCadena+="        FROM tbr_cliLoc as a1  \n";
//                strCadena+="        WHERE co_emp=2 and co_loc=6 and co_ven<>9	 \n";
//                strCadena+=" ) as a1  \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
//                strCadena+="        tbr_cliLoc.co_loc=13 AND  \n";
//                strCadena+="        tbr_cliLoc.tx_cli=a1.tx_cli ; \n";
//                strCadena+=" /* DIMULTI VIA DAULE INMACONSA */ \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven \n";
//                strCadena+=" FROM  ( \n";
//                strCadena+="        SELECT CO_EMP, CO_LOC, tx_cli, co_ven  \n";
//                strCadena+="        FROM tbr_cliLoc as a1  \n";
//                strCadena+="        WHERE co_emp=4 and co_loc=3 and co_ven<>9	 \n";
//                strCadena+=" ) as a1  \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
//                strCadena+="        tbr_cliLoc.co_loc=10 AND  \n";
//                strCadena+="        tbr_cliLoc.tx_cli=a1.tx_cli ; \n";
//                strCadena+=" /* DIMULTI DURAN INMACONSA */ \n";
//                strCadena+=" UPDATE tbr_cliLoc SET co_ven = a1.co_ven  \n";
//                strCadena+=" FROM ( \n";
//                strCadena+="        SELECT CO_EMP, CO_LOC, tx_cli, co_ven  \n";
//                strCadena+="        FROM tbr_cliLoc as a1  \n";
//                strCadena+="        WHERE co_emp=4 and co_loc=12 and co_ven<>9 \n";
//                strCadena+=" ) as a1  \n";
//                strCadena+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
//                strCadena+="        tbr_cliLoc.co_loc=13 AND  \n";
//                strCadena+="        tbr_cliLoc.tx_cli=a1.tx_cli ;   \n";
                stmLoc.executeUpdate(strCadena);
                stmLoc.close();
                stmLoc = null;
            }
            
            
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
            System.err.println("ERROR: " + e);
        } 
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
            System.err.println("ERROR: " + e);
        }
        return blnRes;
    }

      private boolean procesoConfigurarActualizaEstado(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if (conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+="UPDATE tbm_preCfgCliLoc SET st_reg='A' \n" +
                " FROM ( \n" +
                "       SELECT DISTINCT a2.co_loc, a1.CUSTNMBR tx_cli, a1.CUSTNAME tx_nom, a2.co_ven, a3.co_cfg, a3.co_usrVen, a3.co_usrIng,a3.st_reg ,a3.co_tipven \n" +
                "       FROM  tuval.dbo.RM00101 as a1  \n" +
                "       INNER JOIN tbr_cliLoc as a2 ON ( a1.CUSTNMBR=a2.tx_cli)  \n" +
                "       LEFT OUTER JOIN ( \n" +
                "           SELECT a1.co_emp, a1.co_loc,a1.tx_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng,a1.st_reg,a1.co_tipven \n" +
                "           FROM tbm_preCfgCliLoc as a1  \n" +
                "           INNER JOIN (   \n" +
                "               SELECT a3.co_emp, a3.co_loc, a3.tx_cli, MAX(a3.co_cfg) as co_cfg  , a3.co_tipven       \n" +
                "               FROM tbm_preCfgCliLoc as a3 where A3.st_reg='P'   \n" +
                "               GROUP BY  a3.co_emp, a3.co_loc, a3.tx_cli, a3.co_tipven      \n" +
                "           ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.tx_cli=a2.tx_cli AND a1.co_cfg=a2.co_cfg )  where a1.st_reg='P' \n" +
                "       ) as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.tx_cli=a3.tx_cli AND a3.st_reg='P')   \n" +
                "       WHERE a3.co_cfg IS NOT NULL AND A3.st_reg='P' AND \n" +
                "  (  CASE WHEN a2.co_ven IS NULL AND a3.co_usrVen IS NOT NULL THEN 1   WHEN a2.co_ven <> a3.co_usrVen THEN 1   ELSE 0 END = 1   )  ) as a1  \n" +
                " WHERE tbm_preCfgCliLoc.co_emp=1 AND  \n" +
                "       tbm_preCfgCliLoc.co_loc=a1.co_loc AND  \n" +
                "       tbm_preCfgCliLoc.tx_cli=a1.tx_cli  and  tbm_preCfgCliLoc.co_tipven=a1.co_tipven and tbm_preCfgCliLoc.st_reg='P'; \n" ;
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
    
    private boolean procesoConfigurar(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if (conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" UPDATE tbr_cliLoc SET co_ven = a1.co_usrVen,  fe_ultMod=CURRENT_TIMESTAMP, co_usrMod=a1.co_usrIng  \n";
                strSql+=" FROM ( \n";
                //strSql+="       SELECT a1.co_emp, a2.co_loc, a1.co_cli, a1.tx_nom, a2.co_ven, a3.co_cfg, a3.co_usrVen, a3.co_usrIng  \n";
                strSql+="       SELECT DISTINCT a2.co_loc, a1.CUSTNMBR tx_cli, a1.CUSTNAME tx_nom, a2.co_ven, a3.co_cfg, a3.co_usrVen, a3.co_usrIng,a3.st_reg ,a3.co_tipven \n";
                //strSql+="       FROM tbm_cli as a1  \n";
                strSql+="       FROM  tuval.dbo.RM00101 as a1  \n";
                strSql+="       INNER JOIN tbr_cliLoc as a2 ON ( a1.CUSTNMBR=a2.tx_cli)  \n";
                //strSql+="       INNER JOIN tbr_cliLoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSql+="       LEFT OUTER JOIN ( \n";
                //strSql+="           SELECT a1.co_emp, a1.co_loc,a1.co_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng   \n";
                strSql+="           SELECT a1.co_emp, a1.co_loc,a1.tx_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng,a1.st_reg,a1.co_tipven \n";
                strSql+="           FROM tbm_preCfgCliLoc as a1  \n";
                strSql+="           INNER JOIN (   \n";
                strSql+="               SELECT a3.co_emp, a3.co_loc, a3.tx_cli, MAX(a3.co_cfg) as co_cfg  , a3.co_tipven       \n";
                strSql+="               FROM tbm_preCfgCliLoc as a3 where A3.st_reg='P'   \n";
                strSql+="               GROUP BY  a3.co_emp, a3.co_loc, a3.tx_cli, a3.co_tipven      \n";
                strSql+="           ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.tx_cli=a2.tx_cli AND a1.co_cfg=a2.co_cfg )  where a1.st_reg='P' \n";
                strSql+="       ) as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.tx_cli=a3.tx_cli AND a3.st_reg='P')   \n";
                strSql+="       WHERE a3.co_cfg IS NOT NULL AND A3.st_reg='P' AND \n";
                strSql+="  (  CASE WHEN a2.co_ven IS NULL AND a3.co_usrVen IS NOT NULL THEN 1   WHEN a2.co_ven <> a3.co_usrVen THEN 1   ELSE 0 END = 1   ) ";
//                strSql+="             CASE WHEN a2.co_ven IS NULL AND a3.co_usrVen IS NOT NULL THEN TRUE ELSE  \n";
//                strSql+="                   CASE WHEN a2.co_ven <> a3.co_usrVen THEN TRUE ELSE FALSE   \n";
//                strSql+="                   END  \n";
//                strSql+="             END   \n";
                strSql+=" ) as a1  \n";
                strSql+=" WHERE tbr_cliLoc.co_emp=1 AND  \n";
                //strSql+=" WHERE tbr_cliLoc.co_emp=a1.co_emp AND  \n";
                strSql+="       tbr_cliLoc.co_loc=a1.co_loc AND  \n";
                strSql+="       tbr_cliLoc.tx_cli=a1.tx_cli  and  tbr_cliLoc.co_tipven=a1.co_tipven ; \n" ;
                strSql+=" \n";
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
