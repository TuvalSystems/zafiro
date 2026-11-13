/*
 * ZafKon.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v1.0
 */ 

package Librerias.ZafKon;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtil.ZafUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets; 
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
  

/**
 * El fin de la creación de esta clase es el de ubicar en ella todas aquellas operaciones
 * que se harían de uso común para el sistema. Esta clase contendrá tantos mï¿½todos como
 * operaciones vaya a realizar. Por ejemplo, como ejemplo de operaciones comunes tenemos
 * la opción de desactivar los componentes en el momento que se realiza una consulta para
 * que no se haga modificaciones a los datos que se presentan; otra operación es la de
 * añadir o eliminar filas de un JTable, que se usará cada vez que vayamos a crear una 
 * factura o una cotización.
 * 
 * @since 25/Ago/2004
 */
public class ZafKon
{
   
    private ZafUtil objUti;   
    private java.util.Date datFecAux;  
    String mensajeConf ="",strFecUltMod="";
    private ZafParSis objParSis;
    private String version ="v1.0"; 
    private String strbasKON="";
    
    

    
    
    /**
    * Crea una nueva instancia de la clase ZafKon
    */
    public ZafKon(ZafParSis obj)
    {
         try {
             
            this.objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            
            strbasKON=this.objParSis.getNombreBaseDatosKON();
            
        } catch (CloneNotSupportedException ex) {
          //  Logger.getLogger(ZafMae85_02.class.getName()).log(Level.SEVERE, null, ex);
        }      
           
    }
    
    public boolean obtenerParaKon()
    {
        java.sql.Statement stmGP1;
        java.sql.Connection conGP1;
        java.sql.ResultSet rstGP1; 
        String strSQLIn="";
        String st_estKon="";
        boolean blnRes=false;
        try
        {
            conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
            if (conGP1!=null)
            {
                stmGP1=conGP1.createStatement();
                strSQLIn ="";
                strSQLIn += "SELECT   st_konact \n";
                strSQLIn += "FROM tbm_emp \n";
                strSQLIn += "WHERE  co_emp =1\n";            
                System.out.println("obtenerParaKon " + strSQLIn);
                rstGP1 = stmGP1.executeQuery(strSQLIn);
                while(rstGP1.next())
                {
                    st_estKon= rstGP1.getString("co_grpo");
                    if (st_estKon.equals("S"))
                        blnRes=true;
                    else
                        blnRes=false;
                }   
                stmGP1.close();
                stmGP1=null;
                rstGP1.close();
                rstGP1=null;
                conGP1.close();
                conGP1=null;
            }
        }
        catch (java.sql.SQLException e){
        blnRes=false;
        }
        catch (Exception e){
        blnRes=false;
        }    
        return blnRes;  
    }
    
    public boolean ejecuSPCreaItmKon(int codEmp,String codAlt1,String codAlt2,String codAlt3,String nomItm,Integer codUniMed,String tipItm,BigDecimal preUni,BigDecimal marUti,BigDecimal pesItmKgr,String obs1,String obs2,int codUsrIng)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {

                strSql = "{ ? = call "+strbasKON+"..kps006_tbm_inv_insertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setInt(2, codEmp);
                cal.setString(3, codAlt1);
                cal.setString(4, codAlt2);
                cal.setString(5, codAlt3);
                cal.setString(6, nomItm);
                if (codUniMed != null)
                    cal.setInt(7, codUniMed);
                else
                    cal.setNull(7, java.sql.Types.SMALLINT);

                cal.setString(8, tipItm);
                cal.setBigDecimal(9, preUni);
                cal.setBigDecimal(10, marUti);
                cal.setBigDecimal(11, pesItmKgr);
                cal.setString(12, obs1);
                cal.setString(13, obs2);
                cal.setInt(14, codUsrIng);

                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Leer el valor devuelto (@varRes)
                 intCodRes = cal.getInt(1);
                System.out.println("Resultado del SP: " + intCodRes);

                blnRes = (intCodRes == 0); // 0 = Éxito, 1 = Error

                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR kps006_tbm_inv_insertar "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR kps006_tbm_inv_insertar "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }
    
    public boolean ejecuSPActuaItmKon(int codEmp,int codItm,String codAlt1,String codAlt2,String codAlt3,String nomItm,int codUniMed,String tipItm,BigDecimal preUni,BigDecimal marUti,BigDecimal pesItmKgr,String obs1,String obs2,String estReg,int codUsrMod)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {
                strSql = "{ ? = call "+strbasKON+"..kps007_tbm_inv_actualizar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setInt(2, codEmp);
                cal.setInt(3, codItm);
                cal.setString(4, codAlt1);
                cal.setString(5, codAlt2);
                cal.setString(6, codAlt3);
                cal.setString(7, nomItm);
                cal.setInt(8, codUniMed);
                cal.setString(9, tipItm);
                cal.setBigDecimal(10, preUni);
                cal.setBigDecimal(11, marUti);
                cal.setBigDecimal(12, pesItmKgr);
                cal.setString(13, obs1);
                cal.setString(14, obs2);
                cal.setString(15, estReg);
                cal.setInt(16, codUsrMod);

                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Obtener valor devuelto
                int resultado = cal.getInt(1);
                System.out.println("Resultado SP kps007_tbm_inv_actualizar → " + resultado);

                // 0 = Éxito, 1 = Error
                blnRes = (resultado == 0);
                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }    
    
    public boolean ejecuSPCreaCliKon(int codEmp,String estCli,String estPrvLoc,String estPrvExt,String estPrvLocImp,String estPrvLocAdm,int codTipIde,String ide,String nom,String dir,String refUbi,String tel1,String tel2,String tel3,String dirWeb,String corEle,
            Integer tipPer,Integer codCiu,Integer codForPag,String obs1,String obs2,int codUsrIng)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {
                strSql = "{ ? = call "+strbasKON+"..kps004_tbm_cli_insertar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setInt(2, codEmp);
                cal.setString(3, estCli);
                cal.setString(4, estPrvLoc);
                cal.setString(5, estPrvExt);
                cal.setString(6, estPrvLocImp);
                cal.setString(7, estPrvLocAdm);
                cal.setInt(8, codTipIde);
                cal.setString(9, ide);
                cal.setString(10, nom);
                cal.setString(11, dir);
                cal.setString(12, refUbi);
                cal.setString(13, tel1);
                cal.setString(14, tel2);
                cal.setString(15, tel3);
                cal.setString(16, dirWeb);
                cal.setString(17, corEle);
                // Los parámetros que pueden ser nulos
                if (tipPer != null) cal.setInt(18, tipPer);
                else cal.setNull(18, java.sql.Types.SMALLINT);

                if (codCiu != null) cal.setInt(19, codCiu);
                else cal.setNull(19, java.sql.Types.SMALLINT);

                if (codForPag != null) cal.setInt(20, codForPag);
                else cal.setNull(20, java.sql.Types.SMALLINT);

                cal.setString(21, obs1);
                cal.setString(22, obs2);
                cal.setInt(23, codUsrIng);                

                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Obtener valor devuelto
                int resultado = cal.getInt(1);
                System.out.println("Resultado SP kps004_tbm_cli_insertar  → " + resultado);

                // 0 = Éxito, 1 = Error
                blnRes = (resultado == 0);
                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }   
    
    public boolean ejecuSPActCliKon(int codEmp,int codCli,String estCli,String estPrvLoc,String estPrvExt,String estPrvLocImp,String estPrvLocAdm,int codTipIde,String ide,String nom,String dir,String refUbi,String tel1,String tel2,String tel3,String dirWeb,String corEle,
            Integer tipPer,Integer codCiu,Integer codForPag,String obs1,String obs2,String estReg,int codUsrMod)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {
                strSql = "{ ? = call "+strbasKON+"..kps005_tbm_cli_actualizar(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setInt(2, codEmp);
                cal.setInt(3, codCli);
                cal.setString(4, estCli);
                cal.setString(5, estPrvLoc);
                cal.setString(6, estPrvExt);
                cal.setString(7, estPrvLocImp);
                cal.setString(8, estPrvLocAdm);
                cal.setInt(9, codTipIde);
                cal.setString(10, ide);
                cal.setString(11, nom);
                cal.setString(12, dir);
                cal.setString(13, refUbi);
                cal.setString(14, tel1);
                cal.setString(15, tel2);
                cal.setString(16, tel3);
                cal.setString(17, dirWeb);
                cal.setString(18, corEle);

                if (tipPer != null)
                    cal.setInt(19, tipPer);
                else
                    cal.setNull(19, java.sql.Types.SMALLINT);

                if (codCiu != null)
                    cal.setInt(20, codCiu);
                else
                    cal.setNull(20, java.sql.Types.SMALLINT);

                if (codForPag != null)
                    cal.setInt(21, codForPag);
                else
                    cal.setNull(21, java.sql.Types.SMALLINT);

                cal.setString(22, obs1);
                cal.setString(23, obs2);
                cal.setString(24, estReg);
                cal.setInt(25, codUsrMod);            

                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Obtener valor devuelto
                int resultado = cal.getInt(1);
                System.out.println("Resultado SP ejecuSPActCliKon  → " + resultado);

                // 0 = Éxito, 1 = Error
                blnRes = (resultado == 0);
                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }   
    
    public boolean ejecuSPCreaItmKonJson(String json)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {

                strSql = "{ ? = call "+strbasKON+"..kps006_tbm_inv_insertar(?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setString(2, json);
                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Leer el valor devuelto (@varRes)
                 intCodRes = cal.getInt(1);
                System.out.println("Resultado del SP: " + intCodRes);

                blnRes = (intCodRes == 0); // 0 = Éxito, 1 = Error

                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR kps006_tbm_inv_insertar2 "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR kps006_tbm_inv_insertar2 "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }     
    
    public boolean ejecuSPActuaItmKonJson(String json)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        java.sql.CallableStatement cal; 
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=false;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {
                strSql = "{ ? = call "+strbasKON+"..kps007_tbm_inv_actualizar(?) }";
                cal = conLoc1.prepareCall(strSql);

                // Parámetro de retorno (@varRes)
                cal.registerOutParameter(1, java.sql.Types.INTEGER);

                // Parámetros de entrada
                cal.setString(2, json);
                // 🔹 Ejecutar el procedimiento
                cal.execute();

                // 🔹 Obtener valor devuelto
                int resultado = cal.getInt(1);
                System.out.println("Resultado SP kps007_tbm_inv_actualizar → " + resultado);

                // 0 = Éxito, 1 = Error
                blnRes = (resultado == 0);
                cal.close();
                conLoc1.close();
                conLoc1=null; 
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
            intCodRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR FluctuacionItems "+ e );
           intCodRes=-1;
         }
        return blnRes;
    }        

}
  