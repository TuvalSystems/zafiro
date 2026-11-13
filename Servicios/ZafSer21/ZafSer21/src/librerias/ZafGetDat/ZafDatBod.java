/*
 * To change this template, choose Tools | Templates
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
public class ZafDatBod {
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strVersion=" v 0.1", strSql;
    
    public ZafDatBod(ZafParSis obj, java.awt.Component parent)
    {
        try
        {
            System.out.println("ZafDatBod "+strVersion);
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
        }
        catch (CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Se envia la empresa y la bodega por empresa y obtenemos la bodega en el grupo 
     * @param CodEmp
     * @param CodBodEmp
     * @return 
     */
    
    public int getCodigoBodegaGrupo(int CodEmp, int CodBodEmp){
        int intCodRes=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_bodGrp \n";
                strSql+=" FROM tbr_bodEmpBodGrp as a1 \n";
                strSql+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_bod="+CodBodEmp+" AND a1.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intCodRes=rstLoc.getInt("co_bodGrp");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            intCodRes=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodRes=-1;
        }
        return intCodRes;
    }
    
    /**
     * Obtiene la bodega predeterminada por local
     * @param intCodEmp
     * @param intCodLoc
     * @return 
     */
    
    public int getbodegaPredeterminadaPorLocal(int intCodEmp, int intCodLoc){
        int intBodPre=-1;
        String strSql;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql=" SELECT a2.co_emp, a2.co_bod, a2.co_empGrp, a2.co_bodGrp ";
                strSql+=" FROM tbr_bodloc as a1 ";
                strSql+=" INNER JOIN tbr_bodEmpBodGrp as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) ";
                strSql+=" WHERE a1.co_loc="+intCodLoc+" and a1.st_reg='P' AND ";
                strSql+=" a1.co_emp="+intCodEmp+" ";
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intBodPre = rstLoc.getInt("co_bod");
                }
                strSql="";
                strSql="";
                conLoc.close();
                conLoc=null;
                rstLoc.close();
                stmLoc.close();
                stmLoc = null;
                rstLoc = null;
            }
        }
        catch(java.sql.SQLException Evt){ 
            objUti.mostrarMsgErr_F1(cmpPad, Evt); 
            intBodPre=-1;
        }
        catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(cmpPad, Evt);  
            intBodPre=-1;
        }
        return intBodPre;
    }
    
}
