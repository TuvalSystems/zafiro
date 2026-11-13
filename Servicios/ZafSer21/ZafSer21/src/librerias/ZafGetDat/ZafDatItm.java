/**
 * Clase para obtener los datos del item 
 */
package Librerias.ZafGetDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 * @author Jota
 */
public class ZafDatItm
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSQL;
    private String strVersion="v0.03";

    public ZafDatItm(ZafParSis obj, java.awt.Component parent)
    {
        try
        {
            System.out.println("ZafDatItm "+strVersion);
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
     * Metodo para obtener el codigo del Item en Grupo
     * @param intCodEmp 
     * @param intCodItm
     * @return 
     */
    public int getCodigoItemGrupo(int intCodEmp, int intCodItm){
        int intCodItmGrp=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT co_itm \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_itmMae = ( \n";
                strSQL+="                        select co_itmMae  \n";
                strSQL+="                        from tbm_Equinv as a1 \n";
                strSQL+="                        where co_emp="+intCodEmp+" and co_itm="+intCodItm+")  \n";
                strSQL+=" and x1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmGrp=rstLoc.getInt("co_itm");
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
            intCodItmGrp=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodItmGrp=-1;
        }
        return intCodItmGrp;
    }

    /**
     * Metodo para obtener el codigo maestro del item
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */        
    public int getCodigoMaestroItem(int intCodEmp, int intCodItm){
        int intCodItmMae=0;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.co_itmMae \n";
                strSQL+=" FROM tbm_equInv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" and x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodItmMae=rstLoc.getInt("co_itmMae");
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
            intCodItmMae=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            intCodItmMae=-1;
        }
        return intCodItmMae;
    }

    /**
     * Metodo para obtener el Nombre del Item
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getNombreItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  tx_nomItm \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_nomItm");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el Codigo Alterno del Item '870-125I'
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getCodigoAlternoItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  tx_codAlt \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_codAlt");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener la unidad de medida del Item PZS
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
     public String getUnidadMedidaItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT  x1.tx_codAlt, a3.tx_desCor \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (x1.co_uni=a3.co_reg) ";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_desCor");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

     /**
      * Metodo para obtener el peso del Item en Kilogramos
      * @param intCodEmp
      * @param intCodItm
      * @return 
      */
     public String getPesoItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN nd_pesItmKgr IS NULL THEN 0 ELSE nd_pesItmKgr END as nd_pesItmKgr \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("nd_pesItmKgr");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el codigo de 3 letras del item XLD
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getCodigoLetraItem(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN tx_codAlt2 IS NULL THEN tx_codAlt ELSE tx_codAlt2 END AS tx_codAlt2 \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("tx_codAlt2");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el codigo de 3 letras del item XLD
     * @param intCodEmp
     * @param intCodItm
     * @return 
     */
    public String getIsItemServicio(int intCodEmp, int intCodItm){
        String strCodLetItm="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT x1.st_ser \n";
                strSQL+=" FROM tbm_inv as x1 \n";
                strSQL+=" WHERE x1.co_emp="+intCodEmp+" AND x1.co_itm="+intCodItm+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strCodLetItm=rstLoc.getString("st_ser");
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
            strCodLetItm="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strCodLetItm="ERROR";
        }
        return strCodLetItm;
    }

    /**
     * Metodo para obtener el DISPONIBLE DE UN ITEM POR GRUPO y BODEGA DE GRUPO
     * El disponible retornado es la sumatoria del disponible de todas las empresas existentes.
     * @param conExt Conexion 
     * @param CodItmMae Código Maestro del ítem.
     * @param CodBodGrp Código de Bodega en el Grupo, bodega de la que se desea obtener el disponible.
     * @return 
     */
     public double getDisponibleItemGrupo(java.sql.Connection conExt, int CodItmMae, int CodBodGrp){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod,  a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt, \n";
                strSQL+="              CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END as nd_canDis \n";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN (  "+CodItmMae+"  )  \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodBodGrp+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canDis");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            dblRes=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            dblRes=-1;
        }
        return dblRes;
     }

/**
     * Metodo para obtener el DISPONIBLE DE UN ITEM POR GRUPO y BODEGA DE GRUPO
     * El disponible retornado es la sumatoria del disponible de todas las empresas existentes.
     * @param conExt Conexion 
     * @param CodItmMae Código Maestro del ítem.
     * @param CodBodGrp Código de Bodega en el Grupo, bodega de la que se desea obtener el disponible.
     * @return 
     */
     public double getDisponibleItemEmpresa(java.sql.Connection conExt,int CodEmp, int CodItmMae, int CodBodGrp){
        double dblRes=0.00;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc=conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_bodGrp, a.co_itmMae,SUM(a.nd_canDis) as nd_canDis,a.tx_codAlt \n";
                strSQL+=" FROM( \n";
                strSQL+="       SELECT a3.co_itmMae, a1.co_emp, a1.co_itm, a1.co_bod, \n";
                strSQL+="              CASE WHEN a1.nd_canDis IS NULL THEN 0 ELSE a1.nd_canDis END as nd_canDis, ";
                strSQL+="              a4.co_empGrp, a4.co_bodGrp,a2.tx_codAlt  ";
                strSQL+="       FROM tbm_invBod AS a1   \n";
                strSQL+="       INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+="       INNER JOIN tbm_equInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
                strSQL+="       INNER JOIN tbr_bodEmpBodGrp as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bod=a4.co_bod) \n";
                strSQL+="       WHERE a3.co_itmMae IN ( "+CodItmMae+" ) AND a1.co_emp="+CodEmp+" \n";
                strSQL+=" ) as a \n";
                strSQL+=" WHERE a.co_bodGrp = ( "+CodBodGrp+" ) \n";
                strSQL+=" GROUP BY a.co_bodGrp, a.co_itmMae,a.tx_codAlt   \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    dblRes=rstLoc.getDouble("nd_canDis");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            dblRes=-1;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            dblRes=-1;
        }
        return dblRes;
     }
     
         
}
