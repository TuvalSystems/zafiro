/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafGetDat;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 *
 * @author Sistemas6
 */
public class ZafDatUsr {
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strVersion=" v 0.01", strSql;
    
    
    
    public ZafDatUsr(ZafParSis obj, java.awt.Component parent){
        try{
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
        }
        catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
  
    
    /**
     * Para saber si el usuario seleccionado es responsable de una caja...
     * @param CodUsr
     * @return 
     */
    
    public boolean isUsuarioResponsableCaja(int CodUsr){
        boolean blnRes=false;
        try{
            if(usuarioResponsableCaja(CodUsr)){
                blnRes=true;
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param CodUsr
     * @return 
     */
    
    private boolean usuarioResponsableCaja(int CodUsr){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tbm_usrCaj WHERE co_usr = "+CodUsr+" and st_usrResCaj = 'S' \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    blnRes=true;
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
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
}
