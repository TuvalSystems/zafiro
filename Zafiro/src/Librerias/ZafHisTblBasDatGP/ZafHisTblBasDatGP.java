/*
 * ZafHisTblBasDatGP.java
 * 
 * Created on 25 de julio de 2017, 04:56 PM
 * v0.1
 */
package Librerias.ZafHisTblBasDatGP;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Esta clase sirve para insertar registros en las tablas históricas.
 * Hay métodos para insertar registros de manera individual o masivamente.
 * @author Eddye Lino
 */
public class ZafHisTblBasDatGP
{
    //Variables generales.
    private ZafUtil objUti; 
    private Statement stm;
    private ResultSet rst;
    private DatabaseMetaData dmd;
    private ResultSetMetaData rmd;
    private String strSQL;
    
    /**
     * Crea una nueva instancia de la clase ZafHisTblBasDatGP.
     * Esta clase se utiliza para insertar datos en las tablas históricas.
     */
    public ZafHisTblBasDatGP()
    {
        //Inicializar objetos.
        objUti=new ZafUtil();
    }
    
    /**
     * Esta función inserta 1 registro en la tabla histórica especificada.
     * <BR>Ejemplos: 
     * <UL>
     * <LI>Se inserta/modifica un item en "tbm_inv" y se hace 1 INSERT en "tbh_inv".
     * <LI>Se insertan/modifican 100 items en "tbm_inv" y se hace 100 INSERTs en "tbh_inv".
     * Hacer 100 inserciones no es eficiente. En esos casos es mejor utilizar la función "insertarHistoricoMasivo".
     * </UL>
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param strNomTbl El nombre de la tabla de donde se van a obtener los datos.
     * @param strNomTblHis El nombre de la tabla histórica.
     * @param strConSQL La condición SQL que sirve para obtener el registro a insertar. Incluye la palabra WHERE.
     * Ejemplo: Si se desea utilizar la tabla "tbm_inv" la condición sería "WHERE co_emp=1 AND co_itm=6603".
     * @return true: Si se pudo insertar el registro en la tabla histórica.
     * <BR>false: En el caso contrario.
     * @see #insertarHistoricoMasivo(java.sql.Connection, java.lang.String, java.lang.String, java.lang.String) 
     */
    public boolean insertarHistoricoIndividual(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL)
    {
        int intNumCam, j, intCodHis;
        String strLisCam="", strLisCamClaPri=""; //Listado de campos sin alias, Listado de campos clave primaria
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " " + strConSQL;
                rst=stm.executeQuery(strSQL);
                rmd=rst.getMetaData();
                intNumCam=rmd.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmd.getColumnName(j);
                }
                rst.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmd=con.getMetaData();
                rst=dmd.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rst.next())
                {
                    if (!strLisCamClaPri.equals(""))
                        strLisCamClaPri+=", ";
                    strLisCamClaPri+=rst.getString("COLUMN_NAME");
                }
                rst.close();
                //Obtener el código del histórico a utilizar.
                strSQL="SELECT MAX(co_his) AS co_his FROM " + strNomTblHis + " " + strConSQL + " GROUP BY " + strLisCamClaPri;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                    intCodHis=rst.getInt("co_his")+1;
                else
                    intCodHis=1;
                rst.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.
                strSQL="INSERT INTO " + strNomTblHis + "(co_his, " + strLisCam + ")";
                strSQL+=" SELECT " + intCodHis + " AS co_his, " + strLisCam + " FROM " + strNomTbl + " AS a1 " + strConSQL;
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * Esta función inserta 1 o más registros en la tabla histórica especificada.
     * Ejemplo: se insertan/actualizan 100 items en "tbm_inv", para no tener que hacer 100 INSERTs en "tbh_inv" se puede utilizar ésta función 1 sóla vez
     * indicando como condición la fecha y usuario de última modificación. De ésta forma se optimiza el proceso ya que en lugar de hacer 100 inserciones se hace 1 sola inserción.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param strNomTbl El nombre de la tabla de donde se van a obtener los datos.
     * @param strNomTblHis El nombre de la tabla histórica.
     * @param strConSQL La condición SQL que sirve para obtener los registros a insertar. Incluye la palabra WHERE y los campos con el alias "a1.".
     * Ejemplo: Si se desea utilizar la tabla "tbm_inv" la condición sería "WHERE a1.fe_ultMod='2017/08/15 14:27:36' AND a1.co_usrMod=1".
     * @return true: Si se pudieron insertar los registros en la tabla histórica.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarHistoricoMasivo(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL)
    {
        int intNumCam, j;
        String strLisCam="", strLisCamClaPri="", strRelTbl=""; //Listado de campos sin alias, Listado de campos clave primaria, Relación entre las tablas.
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " AS a1 " + strConSQL;
                rst=stm.executeQuery(strSQL);
                rmd=rst.getMetaData();
                intNumCam=rmd.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmd.getColumnName(j);
                }
                rst.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmd=con.getMetaData();
                rst=dmd.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rst.next())
                {
                    if (!strLisCamClaPri.equals(""))
                    {
                        strLisCamClaPri+=", ";
                        strRelTbl+=" AND ";
                    }
                    strLisCamClaPri+="a1." + rst.getString("COLUMN_NAME");
                    strRelTbl+="a1." + rst.getString("COLUMN_NAME") + "=a2." + rst.getString("COLUMN_NAME");
                }
                rst.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.
////                strSQL="INSERT INTO " + strNomTblHis + "(co_his, " + strLisCam + ")";
////                strSQL+=" SELECT (CASE WHEN MAX(a2.co_his) IS NULL THEN 1 ELSE MAX(a2.co_his)+1 END) AS co_his, a1.*";
////                strSQL+=" FROM " + strNomTbl + " AS a1";
////                strSQL+=" LEFT OUTER JOIN " + strNomTblHis + " AS a2 ON (" + strRelTbl + ")";
////                strSQL+=" " + strConSQL;
////                strSQL+=" GROUP BY " + strLisCamClaPri;
                    strSQL="";
                    strSQL+=" INSERT INTO " + strNomTblHis + "(co_his," + strLisCam + ") \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo, a1.* \n";
                    strSQL+=" FROM " + strNomTbl + " AS a1 \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from " + strNomTblHis + " as a2   \n";
                    strSQL+=" group by tx_itm \n";
                    strSQL+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQL+=" " + strConSQL;
                    
                    
                
                
////                    strSQLHist+=" INSERT INTO tbh_inv\n" +
////"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
////"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk) \n";
////                    strSQLHist+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm, a1.nd_marUti,nd_canMaxVen,a1.nd_facCosUni,a1.tx_usrmod, a1.fe_ultmod,a1.tx_codalt,"
////                            + "a1.tx_nomitm,a1.tx_codalt2,a1.nd_preVta1,a1.co_grpimp,a1.tx_nomitming,a1.st_revtec,a1.st_itmstk    \n";
////                    strSQLHist+=" FROM tbm_inv AS a1  \n";
////                    strSQLHist+=" left join (  \n";
////                    strSQLHist+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
////                    strSQLHist+=" group by tx_itm \n";
////                    strSQLHist+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
////                    strSQLHist+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                    
                    
                    
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    public boolean InsertarHistorico(java.sql.Connection conEXT,String fecha,String usuario)
    {
 
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="";
        try
        {
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                    strSQL="";
                    
                    strSQL+=" INSERT INTO tbh_inv\n" +
"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk,tx_obs1,tx_obs2,nd_proAnuVenMan,nd_stkBas,nd_tra,nd_fre,nd_punParMin,nd_punParMax,nd_moq,nd_exc,nd_proVenTeo,st_reg,nd_porIvaVen,co_grpinv,co_detGrpInv ) \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm, a1.nd_marUti,nd_canMaxVen,a1.nd_facCosUni,a1.tx_usrmod, a1.fe_ultmod,a1.tx_codalt,"
                              + "a1.tx_nomitm,a1.tx_codalt2,a1.nd_preVta1,a1.co_grpimp,a1.tx_nomitming,a1.st_revtec,a1.st_itmstk,    \n"
                              + "a1.tx_obs1,a1.tx_obs2,a1.nd_proAnuVenMan,a1.nd_stkBas,a1.nd_tra,a1.nd_fre,a1.nd_punParMin,a1.nd_punParMax,a1.nd_moq,a1.nd_exc,a1.nd_proVenTeo,"
                              + "a1.st_reg,a1.nd_porIvaVen,a1.co_grpinv,a1.co_detGrpInv   \n";
                    strSQL+=" FROM tbm_inv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
                    strSQL+=" group by tx_itm \n";
                    strSQL+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQL+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                
                    System.out.println("UPDATE " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
       catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    public boolean insertarHistoricoMasivoGeneral(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL,String item)
    {
        int intNumCam, j;
        String strLisCam="", strLisCamClaPri="", strRelTbl=""; //Listado de campos sin alias, Listado de campos clave primaria, Relación entre las tablas.
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " AS a1 " + strConSQL;
                rst=stm.executeQuery(strSQL);
                rmd=rst.getMetaData();
                intNumCam=rmd.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmd.getColumnName(j);
                }
                rst.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmd=con.getMetaData();
                rst=dmd.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rst.next())
                {
                    if (!strLisCamClaPri.equals(""))
                    {
                        strLisCamClaPri+=", ";
                        strRelTbl+=" AND ";
                    }
                    strLisCamClaPri+="a1." + rst.getString("COLUMN_NAME");
                    strRelTbl+="a1." + rst.getString("COLUMN_NAME") + "=a2." + rst.getString("COLUMN_NAME");
                }
                rst.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.
////                strSQL="INSERT INTO " + strNomTblHis + "(co_his, " + strLisCam + ")";
////                strSQL+=" SELECT (CASE WHEN MAX(a2.co_his) IS NULL THEN 1 ELSE MAX(a2.co_his)+1 END) AS co_his, a1.*";
////                strSQL+=" FROM " + strNomTbl + " AS a1";
////                strSQL+=" LEFT OUTER JOIN " + strNomTblHis + " AS a2 ON (" + strRelTbl + ")";
////                strSQL+=" " + strConSQL;
////                strSQL+=" GROUP BY " + strLisCamClaPri;
                    strSQL="";
                    strSQL+=" INSERT INTO " + strNomTblHis + "(co_his," + strLisCam + ") \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo, a1.* \n";
                    strSQL+=" FROM " + strNomTbl + " AS a1 \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,a2."+item +" from " + strNomTblHis + " as a2   \n";
                    strSQL+=" group by "+item+" \n";
                    strSQL+=" ) as a2 on (a1."+item +" =a2."+item +") \n";
                    strSQL+=" " + strConSQL;
                    
                    
                
                
////                    strSQLHist+=" INSERT INTO tbh_inv\n" +
////"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
////"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk) \n";
////                    strSQLHist+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm, a1.nd_marUti,nd_canMaxVen,a1.nd_facCosUni,a1.tx_usrmod, a1.fe_ultmod,a1.tx_codalt,"
////                            + "a1.tx_nomitm,a1.tx_codalt2,a1.nd_preVta1,a1.co_grpimp,a1.tx_nomitming,a1.st_revtec,a1.st_itmstk    \n";
////                    strSQLHist+=" FROM tbm_inv AS a1  \n";
////                    strSQLHist+=" left join (  \n";
////                    strSQLHist+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
////                    strSQLHist+=" group by tx_itm \n";
////                    strSQLHist+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
////                    strSQLHist+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                    
                    
                    
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
        
    
}
