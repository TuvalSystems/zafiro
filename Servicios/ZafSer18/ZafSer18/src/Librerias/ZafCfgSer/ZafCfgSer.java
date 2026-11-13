/*
 * ZafCfgSer.java
 *
 * Created on 06 de Julio del 2016, 11:30
 */
package Librerias.ZafCfgSer;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;

/**
 *
 * @author sistemas 9
 */
public class ZafCfgSer
{
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strIpHost;
    private String strVer = "v0.1";
  
    public ZafCfgSer(ZafParSis obj)
    {
        //Inicializar objetos.
        objParSis=obj;
        System.out.println("ZafCfgSer "+strVer);
    }      
    
    /**
     * Carga datos configurados en la tabla tbm_serCliSerLoc
     * Permite saber en donde se ejecutan los servicios.
     * 
     * intCfgEmpGrp = Si trabaja por Grupo 0 si trabaja por Empresa 1
     * intCodSer = Codigo del Servicio que necesitamos los datos
     * @return true: Consulta obtuvo datos.
     * <BR> false: caso contrario.
     */
    public boolean cargaDatosIpHostServicios(int intCfgEmpGrp, int intCodSer) 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        boolean blnRes = false;
        int intCodEmp;
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                intCodEmp=(intCfgEmpGrp==0?objParSis.getCodigoEmpresaGrupo():objParSis.getCodigoEmpresa());
                stm = con.createStatement();
                strSQL =" SELECT tx_dirSer";
                strSQL+=" FROM tbm_serCliSerLoc";
                strSQL+=" WHERE co_emp=" + intCodEmp + "";
                strSQL+=" AND co_ser="+intCodSer+" AND st_reg='A';";
                System.out.println("cargaDatosIpHostServicios: "+strSQL);
                rst = stm.executeQuery(strSQL);
                
                while (rst.next()) 
                {
                    blnRes = true;
                    strIpHost=rst.getString("tx_dirSer");
                }
                
                rst.close();
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {   
            blnRes = false;    
        } 
        catch (Exception e) {    
            blnRes = false;    
        }
        return blnRes;
    }

    /**
     * Obtiene la dirección Ip del servidor configurada en la tabla tbm_serCliSerLoc
     * @return strIpHost: Dirección Ip
     */
    public String getIpHost(){
        return strIpHost;
    }
    
    /**
     * Establece ldirección Ip del servidor configurada en la tabla tbm_serCliSerLoc
     * @param IpHost 
     */
    public void setIpHost(String IpHost){
        strIpHost=IpHost;
    }
   
    
}
