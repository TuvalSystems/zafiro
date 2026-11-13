/*
 * ZafExcColMnu.java
 *
 * Created on 25 de agosto de 2004, 13:49
 * v0.59
 */ 

package Librerias.ZafExcColMnu;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtil.ZafUtil;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
  

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
public class ZafExcColMnu
{
   
    static java.awt.Color colComDes;
    //Constantes: Columnas utilizadas por el proceso de recosteo:
    private static final int INT_REC_COD_EMP=0;                    //Código de la empresa.
    private static final int INT_REC_COD_LOC=1;                    //Código del local.
    private static final int INT_REC_COD_TIP_DOC=2;                //Código del tipo de documento.
    private static final int INT_REC_COD_DOC=3;                    //Código del documento.
    private static final int INT_REC_COD_REG=4;                    //Código del registro.
    private static final int INT_REC_EST_CUC=5;                    //Estado de costo unitario calculado.
    private static final int INT_REC_NAT_DOC=6;                    //Naturaleza del documento.
    private static final int INT_REC_EST_DOC=7;                    //Estado del documento.
    private static final int INT_REC_CAN=8;                        //Cantidad.
    private static final int INT_REC_CAN_DEV=9;                    //Cantidad.
    private static final int INT_REC_COS_UNI=10;                   //Costo unitario.
    private static final int INT_REC_POR_DES=11;                   //Porcentaje de descuento.
    private static final int INT_REC_COS_TOT=12;                   //Costo total.
    private static final int INT_REC_SAL_UNI=13;                   //Saldo en unidades.
    private static final int INT_REC_SAL_VAL=14;                   //Saldo en valores.
    private static final int INT_REC_COS_PRO=15;                   //Costo promedio.
    private ZafUtil objUti;   
    private static final int INT_ARL_CON_TRESL=0;     
    
    private static final int INT_ARL_RES_LIN=0;
    private static final int INT_ARL_RES_TRESL=3;
    private static final int INT_ARL_RES_ITEM_GP=1;
    private static final int INT_ARL_RES_ITEM_ZAF=2;
    private static final int INT_ARL_RES_ITEM_NOM=4;
    private java.util.Date datFecAux;  
    String mensajeConf ="",strFecUltMod="";
    private ZafParSis objParSis;
    private String version ="v1.5"; 
    
    private static final int INT_ARL_CON_SQL_TRES=0;
    private static final int INT_ARL_CON_SQL_COD_GPGP=1;
    private static final int INT_ARL_CON_SQL_NOMBRE=2;
    private static final int INT_ARL_CON_SQL_COD_ZAF=3;
    private static final int INT_ARL_CON_SQL_COST=4;
    private static final int INT_ARL_CON_SQL_PREC=5;
    private static final int INT_ARL_CON_SQL_FAC_COS=6;    
    private static final int INT_ARL_CON_SQL_PRO_ANU_VEN=7;    
    private static final int INT_ARL_CON_SQL_STK_BAS=8;    
    private static final int INT_ARL_CON_SQL_TRA=9;    
    private static final int INT_ARL_CON_SQL_PUN_PAR_MIN=10;    
    private static final int INT_ARL_CON_SQL_PUN_PAR_MAX=11;    
    private static final int INT_ARL_CON_SQL_MOQ=12;    
    private static final int INT_ARL_CON_SQL_EXC=13;    
    private static final int INT_ARL_CON_SQL_PRO_VEN_TEO=14;
    private static final int INT_ARL_CON_SQL_PES=15;    
    
////    private static final int INT_ARL_CON_INV_MAR_UTI=5;
////    private static final int INT_ARL_CON_INV_MAX_VEN=6;
////    private static final int INT_ARL_CON_INV_COS_UNI=7;    
////    private static final int INT_ARL_CON_INV_PRE_VTA=8;
////    private static final int INT_ARL_CON_INV_GRP_IMP=9;
////    private static final int INT_ARL_CON_INV_NOM_ITM=10;
////    private static final int INT_ARL_CON_INV_REV_TEC=11;    
////    private static final int INT_ARL_CON_INV_ITM_STO=12;   
    
    private ZafExpoExc objExcel; 
    
    
    /**
    * Crea una nueva instancia de la clase ZafExcColMnu
    */
    public ZafExcColMnu(ZafParSis obj)
    {
         try {
             
            this.objParSis = (ZafParSis) obj.clone();
            
        } catch (CloneNotSupportedException ex) {
          //  Logger.getLogger(ZafMae85_02.class.getName()).log(Level.SEVERE, null, ex);
        }      
           
    }

          
    public int ValidarItemInactivoIndivual(String item,String tresl,int tipo)
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        String strSql ="",identificador ="";
        int IntRes=-1;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
                if (tipo ==1)
                        identificador =item;
                if (tipo == 2)
                        identificador =tresl;
                                
              stmLoc1=conLoc1.createStatement();
              strSql="";
              strSql+=" SELECT  trim(ITEMNMBR) as item \n";
              strSql+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101   \n";
              strSql+=" WHERE   ITMGEDSC like ("+ identificador +") and INACTIVE =1   \n"; 
              
              rstLoc1 = stmLoc1.executeQuery(strSql);
              
              while(rstLoc1.next())
              {
                  IntRes=1;
                  break;
              }
              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoIndivual "+ e );
            IntRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoIndivual "+ e );
           IntRes=-1;
         }
        return IntRes;
    }
    
    
    /**
     * 
     * @param arlRegItemsCons
     * @return 
     */
    
    public String ValidarItemInactivoMasivo(ArrayList arlRegItemsCons)
    {
         java.sql.Connection conLoc1;
         java.sql.Statement stmLoc1;
         java.sql.ResultSet rstLoc1;        
         int i =0,contador =0;
         String strSql ="",identificador ="";         
         String cadena="";
         ArrayList arlDatITEMS= new ArrayList();
         objUti=new ZafUtil();
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            stmLoc1=conLoc1.createStatement();
             for (i=0; i<arlRegItemsCons.size();i++)
             {
                  strSql="";
                  strSql+=" SELECT  trim(ITMGEDSC) as tresL,trim(ITEMNMBR) as codGP,trim(ITMSHNAM) as codZA ,trim(ITEMDESC) as nombre \n";
                  strSql+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101   \n";
                  strSql+=" WHERE   ITMGEDSC ='"+ objUti.getStringValueAt(arlRegItemsCons, i, INT_ARL_CON_TRESL) +"' and INACTIVE =1   \n"; 
                  rstLoc1 = stmLoc1.executeQuery(strSql);
                  while(rstLoc1.next())
                  {
                      ArrayList arlRegITEMS= new ArrayList();
                      arlRegITEMS.add(INT_ARL_RES_LIN,contador);
                      arlRegITEMS.add(INT_ARL_RES_TRESL,rstLoc1.getString("tresL"));
                      arlRegITEMS.add(INT_ARL_RES_ITEM_GP,rstLoc1.getString("codGP"));
                      arlRegITEMS.add(INT_ARL_RES_ITEM_ZAF,rstLoc1.getString("codZA"));
                      arlRegITEMS.add(INT_ARL_RES_ITEM_NOM,rstLoc1.getString("nombre"));
                      arlDatITEMS.add(arlRegITEMS);
                      contador ++;
                  }                  
             }
             if (contador>0)
             {
                cadena="<HTML>Se encuentran los siguientes items inactivos: <BR><BR>";
                 cadena+="<table BORDER=1><tr><td> TRESL </td> <td> ITEM GP </td><td> ITEM ZAF </td><td> NOMBRE </td>";
                 for (i=0; i<arlDatITEMS.size();i++) 
                { 
                    cadena+="<tr><td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_TRESL) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_GP) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_GP) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_NOM) + "</td>";
                    cadena+="</tr>";
                }
                cadena+="</table><BR>";
                cadena+="Poseen stock. <html>";                 
             }
             else
                cadena="";
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoMasivo "+ e );
            
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoMasivo "+ e );
           
         }
       return cadena;
    }
    
    public ArrayList ValidarItemInactivoMasivoArreglo(ArrayList arlRegItemsCons)
    {
         java.sql.Connection conLoc1;
         java.sql.Statement stmLoc1;
         java.sql.ResultSet rstLoc1;        
         int i =0,contador =0;
         String strSql ="",identificador ="";         
         String cadena="";
         ArrayList arlDatITEMS= new ArrayList();
         objUti=new ZafUtil();
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            stmLoc1=conLoc1.createStatement();
             for (i=0; i<arlRegItemsCons.size();i++)
             {
                  strSql="";
                  strSql+=" SELECT  trim(ITMGEDSC) as tresL,trim(ITEMNMBR) as codGP,trim(ITMSHNAM) as codZA ,trim(ITEMDESC) as nombre \n";
                  strSql+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101   \n";
                  strSql+=" WHERE   ITMGEDSC ='"+ objUti.getStringValueAt(arlRegItemsCons, i, INT_ARL_CON_TRESL) +"' and INACTIVE =1   \n"; //objUti.getStringValueAt(arlRegUbiItem, i, INT_ARL_CON_QUERY)
                  rstLoc1 = stmLoc1.executeQuery(strSql);
                  while(rstLoc1.next())
                  {
                      ArrayList arlRegITEMS= new ArrayList();
                      arlRegITEMS.add(INT_ARL_RES_LIN,contador);
                      arlRegITEMS.add(INT_ARL_RES_ITEM_GP,rstLoc1.getString("codGP"));
                      arlRegITEMS.add(INT_ARL_RES_ITEM_ZAF,rstLoc1.getString("codZA"));
                      arlRegITEMS.add(INT_ARL_RES_TRESL,rstLoc1.getString("tresL"));
                      arlRegITEMS.add(INT_ARL_RES_ITEM_NOM,rstLoc1.getString("nombre"));
                      arlDatITEMS.add(arlRegITEMS);
                      contador ++;
                  }                  
             }
             if (contador>0)
             {
                cadena="<HTML>Se encuentran los siguientes items inactivos: <BR><BR>";
                 cadena+="<table BORDER=1><tr><td> TRESL </td> <td> ITEM GP </td><td> ITEM ZAF </td><td> NOMBRE </td>";
                 for (i=0; i<arlDatITEMS.size();i++) 
                { 
                    cadena+="<tr><td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_TRESL) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_GP) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_GP) + "</td>";
                    cadena+="<td>" + objUti.getStringValueAt(arlDatITEMS, i, INT_ARL_RES_ITEM_NOM) + "</td>";
                    cadena+="</tr>";
                }
                cadena+="</table><BR>";
                cadena+="Poseen stock. <html>";                 
             }
             else
                cadena="";
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoMasivo "+ e );
            
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidarItemInactivoMasivo "+ e );
           
         }
       return arlDatITEMS;
    }
 
    public boolean ValidaStock(String item)//chiqui
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="";
        boolean blnRes=false,blnIngreso =false;
        double  preResul=0;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT  ISNULL(QTYONHND,0) AS stock \n";
              strSqlZa+=" FROM   "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 \n";
              strSqlZa+=" WHERE  ITEMNMBR= "+ item +"  \n";
              strSqlZa+="       AND   RCRDTYPE=1   \n";              
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              if(rstLoc1.next())
              {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    preResul= rstLoc1.getDouble("stock");
                    if (preResul==0)
                        blnRes=true;
                    else
                        blnRes=false;
                    blnIngreso =true;
              }
              if (!blnIngreso)
                   blnRes=false;

              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
           blnRes=false;
         }
        return blnRes;
    }
    
    public boolean ValidaStockBin(String item)//chiqui
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="";
        boolean blnRes=false,blnIngreso =false;
        double  preResul=0;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT  ISNULL(QUANTITY,0) AS stock \n";
              strSqlZa+=" FROM   "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 \n";
              strSqlZa+=" WHERE  ITEMNMBR="+ item +"  \n";
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              if(rstLoc1.next())
              {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    preResul= rstLoc1.getDouble("stock");
                    if (preResul==0)
                        blnRes=true;
                    else
                        blnRes=false;
                    blnIngreso =true;
              }
              if (!blnIngreso)
                   blnRes=false;

              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
           blnRes=false;
         }
        return blnRes;
    }     
    
    private boolean actualizarGP (String sql)
    {
        java.sql.Statement stmGP1;
        java.sql.Connection conGP1;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSQLIn ="";
        int intCodRes=-1;
        boolean blnRes=true;
 
        
        try
        {
         conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
           if (conGP1!=null)
           {
//           
                stmGP1=conGP1.createStatement();
                
                strSQLIn ="";
                strSQLIn += sql;
                System.out.println("CONEXIONES " + strSQLIn);
                stmGP1.executeUpdate(strSQLIn);
                
                stmGP1.close();
                stmGP1=null;
                
                
                conGP1.close();
                conGP1=null;
            }
           else
               blnRes=false;
                    
          }
        catch (java.sql.SQLException e){
            blnRes=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }  
    
    public boolean actualizarDet(ArrayList arlRegItems, int tipo)
    {
         java.sql.Connection conLocZAF;
         java.sql.Statement stmLocZAF;
         java.sql.Connection conLocGP;
         java.sql.Statement stmLocGP;
         ArrayList arlDatITEMDET,arlRegITEMDET;    
         int INT_ARL_CON_ITEM=0;  
         int INT_ARL_CON_DESC=1;
////         int INT_ARL_CON_STOCK=2; 
         objUti=new ZafUtil();
         
        int intNumFil, i;
        int idItemPeso=0;
        boolean validaOk =false;
        boolean blnExisIgual =false;
        boolean blnRes=false,blnResStock=false,blnExisDet=false,blnResStockBin =false;
        String  strSQLZAF ="",datFecAuxZAFe="",strSQLZAFGLO="",strSQLGP="",strSQLGPGLO="";
        int contador =0;
        String codZafiro ="";
        double nd_PrecioVta =0;
        try
        {
            conLocZAF = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           // conLocGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLocZAF!=null)
            {
                stmLocZAF=conLocZAF.createStatement();
////                stmGP=conGP.createStatement();
////                con.setAutoCommit(false);
////                conGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                arlDatITEMDET= new ArrayList();
                for (i=0; i<arlRegItems.size();i++)
                {
                    if (tipo==1)
                    {
                             blnResStock=ValidaStock(objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)));
                             blnResStockBin =ValidaStockBin(objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP))); 
                            if(blnResStock)
                            {
                                 strSQLGP +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set INACTIVE = 1 \n";
                                 strSQLGP+= "       ,DEX_ROW_TS ='"+strFecUltMod +"'\n";
                                 strSQLGP+= "WHERE ITEMNMBR ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)) +" ;\n";

                                 strSQLGP +="\n UPDATE Dynamics2021..tbm_inv set st_reg ='I' \n";
                                 strSQLGP+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' \n";
                                 strSQLGP+= "WHERE tx_itm ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)) +" ;\n";      

                                 strSQLGP +="\n DELETE "+ objParSis.getNombreBaseDatosGP()  +"..IV00112  \n";
                                 strSQLGP+= "   WHERE ITEMNMBR ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)) +" ;\n";      


                                 strSQLZAF += "\n UPDATE tbm_inv set st_reg ='I' \n";
                                 strSQLZAF += "    ,fe_ultmod = '"+strFecUltMod +"' , co_usrmod ="+ objParSis.getCodigoUsuario() +"";
                                 strSQLZAF += " WHERE tx_codalt2 ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_TRESL))+";";


                                 blnRes=true;   
                                 contador ++;
                                 stmLocZAF.executeUpdate(strSQLZAF);
                                 actualizarGP(strSQLGP);
                            }
                             else
                             {
                                arlRegITEMDET = new ArrayList();
                                arlRegITEMDET.add(INT_ARL_CON_ITEM,objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP));
                                arlRegITEMDET.add(INT_ARL_CON_DESC,objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_NOM));
////                                arlRegITEMDET.add(INT_ARL_CON_STOCK,objTblMod.getValueAt(i,INT_TBL_DAT_STOCK).toString());
                                arlDatITEMDET.add(arlRegITEMDET);
                                blnExisDet=true;                                 
                             }


                   } 
                   else
                   {
                      if (tipo==2)
                      { 
                          strSQLGP="";
                          strSQLZAF="";
                            blnResStock=ValidaStock(objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)));
                            if(!blnResStock)
                            {
                                 strSQLGP +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set INACTIVE = 0 \n";
                                 strSQLGP+= "       ,DEX_ROW_TS ='"+strFecUltMod +"'\n";
                                 strSQLGP+= "WHERE ITEMNMBR ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)) +" ;\n";

                                 strSQLGP +="\n UPDATE Dynamics2021..tbm_inv set st_reg ='A' \n";
                                 strSQLGP+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' \n";
                                 strSQLGP+= "WHERE tx_itm ="+ objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP)) +" ;\n";      

////                                     strSQLGP +="\n DELETE "+ objParSis.getNombreBaseDatosGP()  +"..IV00112  \n";
////                                     strSQLGP+= "   WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      


                                 strSQLZAF += "\n UPDATE tbm_inv set st_reg ='A' \n";
                                 strSQLZAF += "    ,fe_ultmod = '"+strFecUltMod +"' , co_usrmod ="+ objParSis.getCodigoUsuario() +"";
                                 strSQLZAF += " WHERE tx_codalt2 ="+  objUti.codificar(objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_TRESL))+";"; 

                                 blnRes=true;   
                                 contador ++;
                                 stmLocZAF.executeUpdate(strSQLZAF);
                                 actualizarGP(strSQLGP);                                     
                            }                                

                      }    
                   }
                    
                }
////                if ((blnRes) || (contador>0))
////                {
////                    con.commit();
////                }
////                else
////                {
////                    con.rollback();
//////                    conGP.rollback();                    
////                }
////         
                
                
                
                stmLocZAF.close();
                conLocZAF.close();
                stmLocGP=null;
                conLocZAF=null;

                

            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    public boolean actualizarDetHis(ArrayList arlRegItems)
    {
        java.sql.Connection conLocZAF;
        java.sql.Statement stmLocZAF;
        java.sql.Connection conLocGP;
        java.sql.Statement stmLocGP;  
        ArrayList arlDatITEMDET,arlRegITEMDET;         
        ZafHisTblBasDat objHisTblBasDat;   
        ZafHisTblBasDatGP objHisTblBasDatGP; 
        int idItemPeso=0;
        boolean validaOk =false;
        boolean blnExisIgual =false;
        boolean blnRes=false,blnResStock=false,blnExisDet=false,blnResStockBin =false;
        String  strSQLZAF ="",datFecAuxZAFe="",strSQLZAFGLO="",strSQLGP="",strSQLGPGLO="";
        int contador =0;
        String codZafiro ="";
        double nd_PrecioVta =0;
        try
        {
            conLocZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLocGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLocGP!=null)
            {
                
            objHisTblBasDat=new ZafHisTblBasDat();
            objHisTblBasDatGP=new ZafHisTblBasDatGP();                
////                stm=con.createStatement();
////                stmGP=conGP.createStatement();
                conLocZAF.setAutoCommit(false);
                conLocGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
////                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                arlDatITEMDET= new ArrayList();

                    objHisTblBasDat.insertarHistoricoMasivo(conLocZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
                    //if(InsertarHistorico(conGP,strFecUltMod,objParSis.getNombreUsuario()))
////                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))  
                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conLocGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))  
                    {
                         blnRes=true;
                         conLocZAF.commit();
                         conLocGP.commit();   
                    }
                    else
                    {
                        blnRes=false;
                        
                    }
                    if (blnExisDet)
                    {
                        int i=0 ;
                        String strMsgItmUbi="";
                         strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items: <BR><BR>";
                         strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> DESCRIPCION </td>";
                         for (i=0; i<arlDatITEMDET.size();i++) 
                        { 

                                strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_GP) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlRegItems, i, INT_ARL_RES_ITEM_NOM) + "</td>";
                                strMsgItmUbi+="</tr>";


                        }
                        strMsgItmUbi+="</table><BR>";
                        strMsgItmUbi+="Poseen stock. <html>";
////                        mostrarMsgInf(strMsgItmUbi);
                    }              
                
                
                
//                stm.close();
                conLocGP.close();
////                stm=null;
                conLocGP=null;
                
////                stmGP.close();
////                stmGP=null;
                conLocZAF.close();
                conLocZAF=null;
                
                datFecAux=null;

            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
       return blnRes; 
        
    }
        
    public boolean creacionActualizarItem(String item, int tipo)//CHIQUI
        {
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        java.sql.Connection conLocEXT;        
        boolean blnRes=false;
        String strSQLIn="";
        try
        { 
            conLocEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (tipo==1)
            {
                if(InsersionIV00102(conLocEXT,item,2))
                {
                    blnRes=true;
                    conLocEXT.commit();
                }
                else
                {
                    blnRes=false;
                    conLocEXT.rollback();
                }    
            }    
        } 
        catch(java.sql.SQLException e){
            blnRes=false;

        }
        catch(Exception e){
            blnRes=false;
        }        
        return blnRes;
    }
    
    private boolean InsersionIV00102(java.sql.Connection conEXT,String item,int tipo)//CHIQUI -PARA LA CREACION DEl REGISTRO CON TIPO 1 y TIPO 2 CON RESPECTO AL ITEM
        {
    ////        java.sql.Connection conLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Statement stmLoc;
            boolean blnRes=false;
            String strPatGP,strPatGPHora;        //PatrÃ³n            
            String strFecDes,strFecHora;
            Date datFecGPHora = new Date();
            String values="",cabecera="",strSQL="";
            Date datFecGP = new Date();
            String bodegasExclui ="'ADMIN','COMP_LOCAL','CUENCA','RECLAM_TER','VENTAS'";


            try
            {
                //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
                if(conEXT!=null)
                {
                    strPatGP = "yyyy-MM-dd";
                    strPatGPHora = "HH:mm:ss";
                    SimpleDateFormat sdfFecGP = new SimpleDateFormat (strPatGP); 
                    SimpleDateFormat sdfFecGPHora = new SimpleDateFormat (strPatGPHora);
                    strFecDes = sdfFecGP.format(datFecGP);
                    strFecHora = sdfFecGPHora.format(datFecGPHora);
                    
                    
                    stmLoc=conEXT.createStatement();
                    if (tipo==1)
                    {

                         values ="('"+ item +"', '', '', 1, '', 0, 0.00000, 0.00000, 0.00000,'"+ strFecDes +"', '', '"+ strFecDes +"', 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, 0.00000, "
                                 + "0.00000, 0.00000, 0.00000, 0.00000, '"+ strFecDes +"', '"+ strFecHora +"', '"+ strFecDes +"','"+ strFecHora +"', 0, '', '', '', 1, 0.00000, 0.00000, 1, 0.00000, 0.00000, 1.00000, 2, 0.00000, "
                                 + "0.00000, 0.00000, 0.00000, 0.00000, 0, 0, 1, 0, 0, 0, 3, 0.00000, 0.00000, 0.00000, '', '', '', '', '', '', '', '', 1, 1, '', 2, 1, 0.00000, 1, 1, 1, 0, 1, 1, 1, 0, 0)";//SE DEBE CAMBIAR PARA PRODUCCION EN VEZ DE 5 PONER 6
                        strSQL="";
                         cabecera ="(ITEMNMBR, LOCNCODE, BINNMBR, RCRDTYPE, PRIMVNDR, ITMFRFLG, BGNGQTY, LSORDQTY, LRCPTQTY, LSTORDDT, LSORDVND, LSRCPTDT, QTYRQSTN, QTYONORD, QTYBKORD, \n"
                                  + "QTY_Drop_Shipped, QTYINUSE, QTYINSVC, QTYRTRND, QTYDMGED, QTYONHND, ATYALLOC, QTYCOMTD, QTYSOLD, NXTCNTDT, NXTCNTTM, LSTCNTDT, LSTCNTTM, STCKCNTINTRVL, \n"
                                  + "Landed_Cost_Group_ID, BUYERID, PLANNERID, ORDERPOLICY, FXDORDRQTY, ORDRPNTQTY, NMBROFDYS, MNMMORDRQTY, MXMMORDRQTY, ORDERMULTIPLE, REPLENISHMENTMETHOD, \n"
                                  + "SHRINKAGEFACTOR, PRCHSNGLDTM, MNFCTRNGFXDLDTM, MNFCTRNGVRBLLDTM, STAGINGLDTME, PLNNNGTMFNCDYS, DMNDTMFNCPRDS, INCLDDINPLNNNG, CALCULATEATP, AUTOCHKATP, \n"
                                  + "PLNFNLPAB, FRCSTCNSMPTNPRD, ORDRUPTOLVL, SFTYSTCKQTY, REORDERVARIANCE, PORECEIPTBIN, PORETRNBIN, SOFULFILLMENTBIN, SORETURNBIN, BOMRCPTBIN, MATERIALISSUEBIN, \n"
                                  + "MORECEIPTBIN, REPAIRISSUESBIN, ReplenishmentLevel, POPOrderMethod, MasterLocationCode, POPVendorSelection, POPPricingSelection, PurchasePrice, IncludeAllocations, \n"
                                  + "IncludeBackorders, IncludeRequisitions, PICKTICKETITEMOPT, INCLDMRPMOVEIN, INCLDMRPMOVEOUT, INCLDMRPCANCEL, Move_Out_Fence, INACTIVE)";
                        strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..IV00102  "+ cabecera +" VALUES "+ values +"\n";
                        System.out.println("INSERT IV00102_TIPO1" + strSQL);                    
                    }
                    else
                    {
    ////                    blnRes =true;
                        if (BusquedaItemBodegaUbicacion(conEXT,item,bodegasExclui)==1)
                            blnRes=true;
                        else
                            blnRes=false;
                    }

                    //Armar la sentencia SQL.

                    stmLoc.executeUpdate(strSQL);
                    blnRes=true;
                    stmLoc.close();
                    stmLoc=null;                    
    ////                }
                }            
            }    
            catch(java.sql.SQLException e){
                blnRes=false;
            }
            catch(Exception e){
                blnRes=false;
            }
            return blnRes;        
    } 
    
    private int BusquedaItemBodegaUbicacion(java.sql.Connection conGlobal, String item,String bodegasExclui )//chiqui
        {
        ////java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
    ////        java.sql.Statement stm;
        java.sql.ResultSet rstLoc;    

        String strSqlZa="",strSQL="",strSQLGlobal12="",tx_ultUbicacion="",strSQLGlobal02="",strSQLGlobal="";
        int ultimoxUbicar=0;
        boolean validaCli=false;
        int blnRes=-1;
        try
        {
           //// conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            ultimoxUbicar =obtenerUltimoIdporUbicar();

            if(conGlobal!=null)
            {
              stmLoc=conGlobal.createStatement();
    ////              stm=conGlobal.createStatement();
              strSqlZa="";
    ////          strSqlZa+=" SELECT tx_descor as bodega  \n";
    ////          strSqlZa+=" FROM Dynamics2021..tbm_bod  \n";
    ////          strSqlZa+=" WHERE  co_emp=0 and st_bodRep ='S' and tx_descor <> 'IMPORT'  \n";
    ////          strSqlZa+=" EXCEPT  \n";
    ////          strSqlZa+=" SELECT distinct(LOCNCODE) as bodega \n";
    ////          strSqlZa+=" FROM "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00102]  \n";
    ////          strSqlZa+=" WHERE  ITEMNMBR='"+item +"' and  LOCNCODE <> 'IMPORT'  \n";
              strSqlZa+=" SELECT LOCNCODE as bodega  \n";
              strSqlZa+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..[IV40700]  \n";
              strSqlZa+=" WHERE LOCNCODE  not in ("+bodegasExclui +")  \n";
              strSqlZa+=" EXCEPT  \n";
              strSqlZa+=" SELECT distinct(LOCNCODE) as bodega \n";
              strSqlZa+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..[IV00102]  \n";
              strSqlZa+=" WHERE  ITEMNMBR='"+item +"'   \n";
              rstLoc = stmLoc.executeQuery(strSqlZa);

              while(rstLoc.next())
              {
                validaCli =true;
                ultimoxUbicar =ultimoxUbicar+1;
                tx_ultUbicacion = "POR_UBI_" + ultimoxUbicar;
                        strSQL="";

                        strSQL+="INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..[IV00102] ([ITEMNMBR]	,[LOCNCODE]	,[RCRDTYPE],[QTYONHND]	,[ATYALLOC],"
                                + " ORDERPOLICY,NMBROFDYS,ORDERMULTIPLE,REPLENISHMENTMETHOD,FRCSTCNSMPTNPRD,\n" 
                                + " ReplenishmentLevel,POPOrderMethod,POPVendorSelection,POPPricingSelection,IncludeAllocations,IncludeBackorders,IncludeRequisitions, \n"
                                + " INCLDMRPMOVEIN,INCLDMRPMOVEOUT,INCLDMRPCANCEL"
                                + ")";
                        strSQL+=" VALUES (";
                        strSQL+=" '" + item + "'"; //co_emp
                        strSQL+=",'"+ rstLoc.getString("bodega") +"'"; //
                        strSQL+=" ,2,0,0,1,1,1,2,3,1,1,2,1,1,1,1,1,1,1";
                        strSQL+=");  ";
                        strSQLGlobal02 = strSQL;

                        strSQL="";
    ////////                    strSQL+=" INSERT INTO "+ objZafParSis.getNombreBaseDatosGP()  +"..[IV00112] ([ITEMNMBR],[LOCNCODE],[BIN],[QTYTYPE],[QUANTITY],[ATYALLOC]) \n";
    ////////                    strSQL+=" VALUES ( \n";
    ////////                    strSQL+=" '" + item + "'"; //co_emp
    ////////                    strSQL+=",'"+ rstLoc.getString("bodega") +"'";
    ////////                    strSQL+=",'"+ tx_ultUbicacion +"'";
    ////////                    strSQL+=",1,0,0";
    ////////                    strSQL+=" );  ";
                        strSQLGlobal12 = strSQL;
                        strSQLGlobal += strSQLGlobal02 + strSQLGlobal12;

              }
              if(validaCli ==true)
              {
                  String bodega ="";
                     System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                     stmLoc.executeUpdate(strSQLGlobal); 
                     blnRes=1;
    ////                 if(modificarSecUltiUbicacion(conGlobal,ultimoxUbicar))
    ////                 {
    ////                      blnRes=1;
    ////                 }
    ////                 else
    ////                      blnRes=2;
              }
              else
                   blnRes=3;

              stmLoc.close();
              stmLoc=null;
              rstLoc.close();
              rstLoc=null;
    ////              stm.close();
    ////              stm=null;              

            }
    //////              conLoc.close();
    //////              conLoc=null; 
    ////         txtDat.setText(mensajePantalla);  
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
            blnRes=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
            blnRes=-1;
         }
        return blnRes;
    }
    
    private BigDecimal getPrecioGrupoDet (int codGrupo,int tipo)//CHIQUI
        {
        
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    
        
        String strSqlZa="";
        boolean blnRes=false,blnIngreso =false;
        BigDecimal precioVenta =BigDecimal.ZERO;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT  det.co_reg,TRIM(det.tx_codItm) AS item,det.co_facModPre, pre.UOMPRICE as precio,trim(iv01.ITMGEDSC) as tresL       \n";
              strSqlZa+=" FROM   Dynamics2021..tbm_detGrpInv as det  (NOLOCK)   \n";
              strSqlZa+="           inner join DYNAMICS2021..tbm_cabGrpInv as cab   (NOLOCK)on (det.co_grpInv = cab.co_grpInv)  \n";
              strSqlZa+="           inner join   "+ objParSis.getNombreBaseDatosGP()  +"..IV00108  as pre  (NOLOCK) on (det.tx_codItm = pre.ITEMNMBR)    \n";
              strSqlZa+="            inner join   "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 (NOLOCK) on   (det.tx_codItm = iv01.ITEMNMBR)    \n";              
              strSqlZa+=" WHERE  cab.co_tipAgr ="+tipo +" and det.co_reg=1  and pre.PRCLEVEL ='GENERAL' AND iv01.inactive=0    \n";
              strSqlZa+="       AND   cab.co_grpInv ="+ codGrupo  +"  \n"; 
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              if(rstLoc1.next())
              {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    precioVenta=BigDecimal.valueOf(rstLoc1.getDouble("precio")); 
                    
              }
              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
           blnRes=false;
         }
        return precioVenta;
       


    }
    
    public boolean ActualizarPrecioGrupo(java.sql.Connection conGPE,java.sql.Connection conZAFE,int codGrupo, String fecha,int tipo)//CHIQUI
        {
        java.sql.Connection conGP;
        java.sql.Connection conZAF;
        boolean blnRes=false;
        try
        {
            conGP =conGPE;
            conZAF=conZAFE;
            conGP.setAutoCommit(false);
            conZAF.setAutoCommit(false);
            double precioPadre =-1;
            BigDecimal precioVenta =BigDecimal.ZERO;
            precioVenta =getPrecioGrupoDet(codGrupo,1);
            if (precioVenta.compareTo(BigDecimal.ZERO)!=0)
            {
                if(ActualizaRegistro(conGP,conZAF,codGrupo,precioVenta,fecha,tipo))
                {
    ////            
////                        conZAF.commit();
////                        conGP.commit(); 
                        blnRes=true;                    
    ////          
                }
                else
                {
////                    conGP.rollback();
////                    conZAF.rollback();                
                    blnRes=false;
                }                
            } 
            else
                blnRes=false;

////            conZAF.close();
////            conZAF =null;
////            conGP.close();
////            conGP =null;
            
        } catch (java.sql.SQLException e) { 
            blnRes=false;
       }
       catch(Exception  Evt){ 
           blnRes=false;
       }     
        
        return blnRes;
}
    
    private boolean ActualizaRegistro(java.sql.Connection conExt,java.sql.Connection conExtZAF,int codGrupo,BigDecimal precio, String fecha,int tipo)//CHIQUI
        {
         boolean blnRes=false;
        java.sql.Statement stmLocGP;
        java.sql.ResultSet rstLocGP;  
        java.sql.Statement stmLocZAF;
        java.sql.ResultSet rstLocZAF;    
        String strSql1="",strSQLGPD="",strZAF="";
        try
        {
            if(conExt!=null)
            {
                stmLocGP = conExt.createStatement();
                stmLocZAF = conExtZAF.createStatement(); 
                strSql1="";
                strSql1+=" SELECT   det.co_reg,TRIM(det.tx_codItm) AS item,det.co_facModPre,fac.tx_desCor,fac.tx_desLar,fac.nd_fac, pre.UOMPRICE, "+ precio +" \n";
                strSql1+="  , ("+ precio +" *fac.nd_fac) as preNuevo,trim(iv01.ITMGEDSC) as tresL    \n";                  
                strSql1+=" FROM    Dynamics2021..tbm_detGrpInv as det  (NOLOCK)  \n";  
                strSql1+="         inner join DYNAMICS2021..tbm_cabGrpInv as cab   (NOLOCK)on (det.co_grpInv = cab.co_grpInv)   \n";  
                strSql1+="         inner join Dynamics2021..tbm_facModPre as fac  (NOLOCK)on (det.co_facModPre = fac.co_facModPre)   \n";  
                strSql1+="         inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as pre  (NOLOCK) on (det.tx_codItm = pre.ITEMNMBR)   \n";
                strSql1+="         inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 (NOLOCK) on   (det.tx_codItm = iv01.ITEMNMBR)   \n";                
                strSql1+=" WHERE cab.co_tipAgr ="+ tipo +" and fac.co_facModPre !=0 and cab.co_grpInv ="+ codGrupo +" and  det.co_reg>0  and pre.PRCLEVEL ='GENERAL' /*AND iv01.inactive=0 */  \n";
                rstLocGP=stmLocGP.executeQuery(strSql1);
                strSQLGPD=""; 
                strZAF="";

                while(rstLocGP.next())
                {

                    strSQLGPD+=" \n UPDATE tbm_inv";
                    strSQLGPD+=" \n  SET nd_preVta1= "+ rstLocGP.getDouble("preNuevo") +"";
                    strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                    strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                    strSQLGPD+=" \n  WHERE tx_itm='"+ rstLocGP.getString("item") +"'";
                    strSQLGPD+=" ;\n "; 

                    strSQLGPD+=" \n UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101";
                    strSQLGPD+=" \n  SET DEX_ROW_TS='" + fecha +"' \n";
                    strSQLGPD+=" \n  WHERE ITEMNMBR='"+  rstLocGP.getString("item") +"'";
                    strSQLGPD+=" ;\n ";

                    strSQLGPD+=" \n UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00108";
                    strSQLGPD+=" \n  SET UOMPRICE= " + rstLocGP.getDouble("preNuevo") +"";
                    strSQLGPD+=" \n  WHERE ITEMNMBR='"+  rstLocGP.getString("item")+"'";  
                    strSQLGPD+=" \n  AND  PRCLEVEL='GENERAL'";                        
                    strSQLGPD+=" ;\n"; 


                    strZAF+=" \n UPDATE tbm_inv";
                    strZAF+=" \n  SET nd_preVta1= "+ rstLocGP.getDouble("preNuevo") +"";
                    strZAF+=" \n  , fe_ultmod= '" + fecha +"'";
                    strZAF+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                    strZAF+=" \n  WHERE tx_codalt2='"+ rstLocGP.getString("tresL") +"'";
                    strZAF+=" ;\n ";                     
                }

                System.out.println("ActualizaRegistroGP: " + strSQLGPD);
                System.out.println("ActualizaRegistroZAF: " + strZAF);
                stmLocGP.executeUpdate(strSQLGPD);
                stmLocZAF.executeUpdate(strZAF);

                stmLocGP.close();
                stmLocGP=null;    
                rstLocGP.close();
                rstLocGP=null; 
                stmLocZAF.close();
                stmLocZAF=null;   
                blnRes=true;
            }            

        }  
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR Proceso_spInv21 "+ e );
         }
         catch (Exception e) {
            System.
                    err.println("OCURRIO UN ERROR Proceso_spInv21 "+ e );
         }        
         return blnRes;
    }
    
    public boolean ValidaGrupo(String item)//chiqui
        {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlGP="";
        boolean blnIngreso =false;
        double  preResul=0;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlGP="";
              strSqlGP+=" SELECT   cab.co_grpInv as grupo \n";
              strSqlGP+=" FROM   tbm_detGrpInv as det \n";
              strSqlGP+="   INNER JOIN   "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv  on (det.tx_codItm = iv.ITEMNMBR) \n";
              strSqlGP+="   INNER JOIN   tbm_cabgrpinv  as cab on (det.co_grpInv = cab.co_grpInv)  \n";
              strSqlGP+=" WHERE  iv.INACTIVE =0 AND det.tx_codItm= "+ item +"  \n";
              rstLoc1 = stmLoc1.executeQuery(strSqlGP);
              if(rstLoc1.next())
                 blnIngreso =true;

              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaGrupo "+ e );
            blnIngreso=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaGrupo "+ e );
           blnIngreso=false;
         }
        return blnIngreso;
    }    
    
    public boolean EjecucionSQL(ArrayList arlRegItems)
        {
        boolean blnRes = false;
        return  blnRes;
    } 
    
    public java.util.Date  ObtenerFechaServidorGP ()
    {
        java.sql.Statement stmGP1;
        java.sql.Connection conGP1;
        java.sql.ResultSet rstGP1;  
        String strSQLIn ="";
        int intCodRes=-1;
        boolean blnRes=true;
        java.util.Date fechahora= null;
 
        
        try
        {
         conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
           if (conGP1!=null)
           {
//           
                stmGP1=conGP1.createStatement();
                
                strSQLIn ="";
                strSQLIn += "SELECT GETDATE() AS fecha ";
                System.out.println("CONEXIONES " + strSQLIn);
                rstGP1 = stmGP1.executeQuery(strSQLIn);
                if(rstGP1.next())
                {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    fechahora= rstGP1.getDate("stock");
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
            fechahora=null;
        }
        catch (Exception e){
            fechahora=null;
        }
        return fechahora;

        
    }       
    
private double dblCosUni=0.00;
private int ndMaxVer =0;
public String consultaItemsNoAgrupados(int tipoAgr,int tipo)
    {
         String strSQLInt="";
            if (tipo==2)
            {
                        strSQLInt="SELECT * FROM (\n";            
                        strSQLInt+="SELECT rtrim(iv01.ITEMNMBR) as ITEMNMBR , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, \n";
                        strSQLInt+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado,iv01.INACTIVE,ica.codlinea,ica.codclase,ica.linea, ica.clase\n";
                        strSQLInt+="       ,  1 as tipo,'S' as estadoFac,iv01.ITMGEDSC \n";            
                        strSQLInt+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 \n";
                        strSQLInt+="       left join(\n";
                        strSQLInt+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm \n";
                        strSQLInt+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01\n"; 
                        strSQLInt+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)\n";
                        strSQLInt+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)\n";            
                        strSQLInt+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  /*and iv01.INACTIVE =0 AND cabG.st_reg ='A'*/  and cabg.co_tipAgr = "+ tipoAgr +" \n";
                        strSQLInt+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)\n";
                        strSQLInt+="       left join view_ItemsClasiAtrib as ica on (iv01.ITEMNMBR =ica.CodItm)\n";   
                        strSQLInt+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')    ) as a1\n";     
            }   
         
            return strSQLInt;
    }     


public String validacionAgrupacionAtributo(StringBuilder cadena, String atributo)//que tengan diferentes atributos
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=false;
    String mensaje="";
    String strSQLIn="";
    String strAtribSQL="",strAtribSQLComp="";
    int contadorRepet=0;
    int resultado =0;
    
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT  isnull(count (distinct(a1.datoPadre)),0) AS Repeticiones   \n";
            strSQLIn += "FROM (  \n";
            strSQLIn += "       SELECT TRIM(iv01.ITEMNMBR) as itemGP, trim (iv01.ITMGEDSC) as cod3L,atrib.datoPadre  \n";
            strSQLIn += "       FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01  \n";
            strSQLIn += "           LEFT JOIN (  \n";
            strSQLIn += "                       SELECT item.CodItm ,trim(det.tx_cfgPadDat) as datoPadre,trim(det.tx_cfgHijDat) as datoHijo, det.co_cfgPad as codigo,det.tx_tipo as tipoAtri   \n";
            strSQLIn += "                       FROM Dynamics2021.dbo.tbm_cfgAtrInv as det    \n";
            strSQLIn += "                           INNER JOIN  Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)   \n";
            strSQLIn += "                           INNER JOIN  Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)    \n";            
            strSQLIn += "                           INNER JOIN  Dynamics2021..tbl_ItemsClasiAtrib as item on (det.tx_cfgPadDat = item.compras)    \n";                        
            strSQLIn += "                       WHERE  tip.tx_nom ='"+ atributo +"' and det.co_cfghij is null   \n";
            strSQLIn += "                     ) AS atrib on (IV01.ITEMNMBR = atrib.CodItm)   \n";
            strSQLIn += "       WHERE iv01.ITEMNMBR like '%-I' or iv01.ITEMNMBR like '%-S' AND iv01.inactive =0   \n";
            strSQLIn += "     ) as a1  \n";
            strSQLIn += "WHERE a1.itemGP  in ( "+ cadena +") \n";    
            strSQLIn += "GROUP BY datoPadre  -- Agrupar para contar repeticiones \n";    
            strSQLIn += "ORDER BY Repeticiones DESC;  -- Ordenar por número de repeticiones \n";                
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            while(rstGP1.next())
            {
                 resultado=rstGP1.getInt("Repeticiones");
                 if (resultado !=0)
                    contadorRepet++;
            }    
                
            if ((contadorRepet >1) && (contadorRepet !=0))
                mensaje="Hay mas de un ATRIBUTO dentro de la generacion de la agrupacion";

            stmGP1.close();
            stmGP1=null;
            rstGP1.close();
            rstGP1=null;
            conGP1.close();
            conGP1=null;
        }

      }
    catch (java.sql.SQLException e){
        mensaje="ERROR";
    }
    catch (Exception e){
        mensaje="ERROR";
    }    
    return mensaje;
}    
            
public String validacionItemAtributo(StringBuilder cadena, String atributo)//un item no este relacionado a un atributo
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=true;
    String strMensaje="";
    String strSQLIn="";
    String strAtribSQL="",strAtribSQLComp="";
    int contadorRepet=0;
    String mensajeError ="";
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT  itemGP,cod3L,ISNULL (datoPadre,'NO') as datoAtribu     \n";
            strSQLIn += "FROM (  \n";
            strSQLIn += "       SELECT TRIM(iv01.ITEMNMBR) as itemGP, trim (iv01.ITMGEDSC) as cod3L,atrib.datoPadre  \n";
            strSQLIn += "       FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01  \n";
            strSQLIn += "           LEFT JOIN (  \n";
            strSQLIn += "                       SELECT item.CodItm ,trim(det.tx_cfgPadDat) as datoPadre,trim(det.tx_cfgHijDat) as datoHijo, det.co_cfgPad as codigo,det.tx_tipo as tipoAtri   \n";
            strSQLIn += "                       FROM Dynamics2021.dbo.tbm_cfgAtrInv as det    \n";
            strSQLIn += "                           INNER JOIN  Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)   \n";
            strSQLIn += "                           INNER JOIN  Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)    \n";            
            strSQLIn += "                           INNER JOIN  Dynamics2021..tbl_ItemsClasiAtrib as item on (det.tx_cfgPadDat = item.compras)    \n";                        
            strSQLIn += "                       WHERE  tip.tx_nom ='"+ atributo +"' and det.co_cfghij is null   \n";
            strSQLIn += "                     ) AS atrib on (IV01.ITEMNMBR = atrib.CodItm)   \n";
            strSQLIn += "       WHERE iv01.ITEMNMBR like '%-I' or iv01.ITEMNMBR like '%-S' AND iv01.inactive =0   \n";
            strSQLIn += "     ) as a1  \n";
            strSQLIn += "WHERE a1.itemGP  in ( "+ cadena +") \n";    
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            while(rstGP1.next())
            {
                strAtribSQL =rstGP1.getString("datoAtribu");
                if (strAtribSQL.equals("NO"))
                {
                    mensajeError="Item que NO tiene ATRIBUTO";
                    break;
                }
                    
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
        mensajeError="ERROR";
    }
    catch (Exception e){
        mensajeError="ERROR";
    }    
    return mensajeError;    
}

public String validacionItemDetaGrupo(StringBuilder cadenaItems)//que un item detalle  tenga valores de stockbase,etc
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=true;
    String strMensaje="";
    String strSQLIn="";
    double ndproVtaMen=0,ndFre=0,ndMoq=0,ndPPMAX=0,ndPPMIN=0,ndTran=0;
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       { 
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn +="SELECT * FROM ( \n";
            strSQLIn += "               SELECT  lis.promVntaMen,lis.moq,lis.stokbase,lis.tra,lis.fre,lis.nd_punParMax,lis.nd_punParMin  \n";
            strSQLIn += "               FROM Dynamics2021..view_listaPropiedadesItems as lis  \n";
            strSQLIn += "               WHERE codigoarticulo in ( "+ cadenaItems +")\n";    
            strSQLIn += "             ) as a1\n";                
            strSQLIn += "WHERE  (a1.promVntaMen !=0 or a1.moq !=0 or a1.stokbase !=0 or a1.tra !=0  or a1.fre !=0 or a1.nd_punParMax !=0  or a1.nd_punParMin !=0) \n";                
            
            System.out.println("validacionItemDetaGrupo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            if(rstGP1.next())
                strMensaje="Un item de detalle posee valores en STOCK BASE - MOQ - FRECUENCIA - PRO. VTA. MENSUAL - TRANSITO";
                    
            stmGP1.close();
            stmGP1=null;
            rstGP1.close();
            rstGP1=null;
            conGP1.close();
            conGP1=null;
        }

      }
    catch (java.sql.SQLException e){
        strMensaje="ERROR";
    }
    catch (Exception e){
        blnRes=false;
    }    
    return strMensaje;    
}

public boolean validacionItemCabecGrupo(StringBuilder cadenaItems)//que un item cabecera no tenga valores de stockbase,etc
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=true;
    String strMensaje="";
    String strSQLIn="";
    double ndproVtaMen=0,ndFre=0,ndMoq=0,ndPPMAX=0,ndPPMIN=0,ndTran=0;
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn +="SELECT * FROM ( \n";
            strSQLIn += "               SELECT  lis.promVntaMen,lis.moq,lis.stokbase,lis.tra,lis.fre,lis.nd_punParMax,lis.nd_punParMin  \n";
            strSQLIn += "               FROM Dynamics2021..view_listaPropiedadesItems as lis  \n";
            strSQLIn += "               WHERE codigoarticulo in ( "+ cadenaItems +")\n";    
            strSQLIn += "             ) as a1\n";                
            strSQLIn += "WHERE  (a1.promVntaMen =0 or a1.moq =0 or a1.stokbase =0 or a1.tra =0  or a1.fre =0 or a1.nd_punParMax =0  or a1.nd_punParMin =0) \n";                
            
            System.out.println("validacionItemCabecGrupo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            if(rstGP1.next())
                blnRes=false;
                    
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
        
public String validacionItemAgrupacion(String item)//es para obtener el estado del item dentro del grupo
{
java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=false;
    String mensaje="";
    String strSQLIn="";
    String strAtribSQL="",strAtribSQLComp="";
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT   det.co_grpInv as co_grpo,det.co_reg , det.st_mosItmLisPre as tipo, det.co_facModPre as fact, cab.tx_itmPad as itemcab,cab.co_tipAgr, \n";
            strSQLIn += "        (SELECT  COUNT (detin.co_reg) as maximo   \n";
            strSQLIn += "         FROM   Dynamics2021..tbm_detGrpInv as detin\n";
            strSQLIn += "         WHERE  detin.co_grpinv = det.co_grpinv\n";            
            strSQLIn += "         GROUP BY co_grpInv) as maximo \n";
            strSQLIn += "FROM Dynamics2021..tbm_detGrpInv as det    \n";
            strSQLIn += "      INNER JOIN  dynamics2021..tbm_cabGrpInv as cab on (det.co_grpinv = cab.co_grpinv)   \n";
            strSQLIn += "WHERE det.tx_codItm ="+ item +"    \n";
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
           if(rstGP1.next())
           {
               if (rstGP1.getInt("co_reg")==1)
                   mensaje= rstGP1.getString("co_grpo") +"-CABECERA-"+rstGP1.getString("co_tipAgr") ;
               else
               {
                   if (rstGP1.getInt("maximo") ==2)
                    mensaje= rstGP1.getString("co_grpo") +"-ULTIMO-"+rstGP1.getString("co_tipAgr") ;
                   else
                   {
                       if (rstGP1.getInt("maximo") >2)
                           mensaje= rstGP1.getString("co_grpo") +"-UNICO-"+rstGP1.getString("co_tipAgr") ;   
                   }
                    
               }   
                   
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
        mensaje="ERROR";
    }
    catch (Exception e){
        mensaje="ERROR";
    }    
    return mensaje;  
    
}

public boolean anulacionAgrupacionCabecera(int cabecera,String fecha)//es para anular todos los items del grupo
{
java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=false;
    String strSQLIn="";
    String strSQLGPD="";
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT  det.co_grpInv as co_grpo,det.co_reg , det.st_mosItmLisPre as tipo, det.co_facModPre as fact, cab.tx_itmPad as itemcab,cab.co_tipAgr,det.tx_codItm as itemDet,  \n";
            strSQLIn += "        grp.contador as maximo    \n";            
            strSQLIn += "FROM Dynamics2021..tbm_detGrpInv as det    \n";
            strSQLIn += "      INNER JOIN  dynamics2021..tbm_cabGrpInv as cab on (det.co_grpinv = cab.co_grpinv)   \n";
            strSQLIn += "      LEFT JOIN(   \n";            
            strSQLIn += "                SELECT  COUNT (co_reg) as contador , co_grpInv  \n";
            strSQLIn += "                FROM   Dynamics2021..tbm_detGrpInv\n";
            strSQLIn += "                WHERE co_grpInv = "+ cabecera+"\n";            
            strSQLIn += "                GROUP BY co_grpInv \n";
            strSQLIn += "               ) as grp on (det.co_grpInv = grp.co_grpInv)\n";
            strSQLIn += "WHERE cab.co_grpInv ="+ cabecera +"    \n";
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            while(rstGP1.next())
            {
                    if ( rstGP1.getInt("co_tipAgr") ==2)
                    {
                        strSQLGPD+=" \n UPDATE tbm_inv";
                        strSQLGPD+=" \n  SET co_grpcom=0,co_detgrpcom=0";
                        strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                        strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                        strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                        strSQLGPD+=" ;\n ";    
                        
                        strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+cabecera+" \n";                    
                        strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+cabecera +" \n";                    
                        strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+cabecera +" \n";                                         
                        strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+cabecera +" \n";                         
                    }
                    else
                    {
                        if ( rstGP1.getInt("itemDet") ==1)                        
                        {
                            strSQLGPD+=" \n UPDATE tbm_inv";
                            strSQLGPD+=" \n  SET co_grpinv=0,co_detGrpInv=0";
                            strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                            strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                            strSQLGPD+=" ;\n ";    

                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+cabecera+" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+cabecera +" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+cabecera +" \n";                                         
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+cabecera +" \n";                             
                        }
                                                
                    } 
                System.out.println("anulacionAgrupacionCabecera: " + strSQLGPD);
                stmGP1.executeUpdate(strSQLGPD);  
                blnRes=true;

                   
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

public boolean anulacionAgrupacionSoloItem(String item,String fecha)//es para cuando el item no es el ultimo de grupo
{
java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=false;
    String mensaje="";
    String strSQLIn="";
    String strSQLGPD="";
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT   det.co_grpInv as co_grpo,det.co_reg , det.st_mosItmLisPre as tipo, det.co_facModPre as fact, cab.tx_itmPad as itemcab,cab.co_tipAgr,det.tx_codItm as itemDet , \n";
            strSQLIn += "        (SELECT  COUNT (detin.co_reg) as maximo   \n";
            strSQLIn += "         FROM   Dynamics2021..tbm_detGrpInv as detin\n";
            strSQLIn += "         WHERE  detin.co_grpinv = det.co_grpinv\n";            
            strSQLIn += "         GROUP BY co_grpInv) as maximo \n";
            strSQLIn += "FROM Dynamics2021..tbm_detGrpInv as det    \n";
            strSQLIn += "      INNER JOIN  dynamics2021..tbm_cabGrpInv as cab on (det.co_grpinv = cab.co_grpinv)   \n";
            strSQLIn += "WHERE det.tx_codItm ="+ item +"    \n";   
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            while(rstGP1.next())
            {
                    if ( rstGP1.getInt("co_tipAgr") ==2)
                    {
                        strSQLGPD+=" \n UPDATE tbm_inv";
                        strSQLGPD+=" \n  SET co_grpcom=0,co_detgrpcom=0";
                        strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                        strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                        strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                        strSQLGPD+=" ;\n ";    
                        
////                        strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+item  +"' \n";                    
////                        strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+item  +"' \n";                    
                        
                        if ( rstGP1.getInt("maximo") <=2)
                        {
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                         
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                                     
                        }    
                        else
                        {
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n"; 
                        } 

                        
                    }
                    else
                    {
                        if ( rstGP1.getInt("co_tipAgr") ==1)                        
                        {
                            strSQLGPD+=" \n UPDATE tbm_inv";
                            strSQLGPD+=" \n  SET co_grpinv=0,co_detGrpInv=0";
                            strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                            strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                            strSQLGPD+=" ;\n ";    

////                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ="+item  +" \n";                    
////                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ="+item  +"\n";           
                           if ( rstGP1.getInt("maximo") <=2)
                            {
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                         
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                                     
                            }    
                            else
                            {
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n"; 
                            } 
                           
                        }
                                                
                    }                        

                blnRes=true;
           }  
                System.out.println("anulacionAgrupacionSoloItem: " + strSQLGPD);
                stmGP1.executeUpdate(strSQLGPD);            
               
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

public boolean anulacionAgrupacionItemUltimo(String item,String fecha)// es paa cuando el item es el ultimo del grupo
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1; 
    boolean blnRes=false;
    String strSQLIn="";
    String strSQLGPD="";
    int contadorRepet=0;
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
//           
            stmGP1=conGP1.createStatement();

            strSQLIn ="";
            strSQLIn += "SELECT   det.co_grpInv as co_grpo,det.co_reg , det.st_mosItmLisPre as tipo, det.co_facModPre as fact, cab.tx_itmPad as itemcab,cab.co_tipAgr,det.tx_codItm as itemDet , \n";
            strSQLIn += "        (SELECT  COUNT (detin.co_reg) as maximo   \n";
            strSQLIn += "         FROM   Dynamics2021..tbm_detGrpInv as detin\n";
            strSQLIn += "         WHERE  detin.co_grpinv = det.co_grpinv\n";            
            strSQLIn += "         GROUP BY co_grpInv) as maximo \n";
            strSQLIn += "FROM Dynamics2021..tbm_detGrpInv as det    \n";
            strSQLIn += "      INNER JOIN  dynamics2021..tbm_cabGrpInv as cab on (det.co_grpinv = cab.co_grpinv)   \n";
            strSQLIn += "WHERE det.tx_codItm ="+ item +"    \n";            
            
            
            
            System.out.println("validacionAgrupacionAtributo " + strSQLIn);
            rstGP1 = stmGP1.executeQuery(strSQLIn);
            while(rstGP1.next())
            {
                    if ( rstGP1.getInt("co_tipAgr") ==2)
                    {
                        strSQLGPD+=" \n UPDATE tbm_inv";
                        strSQLGPD+=" \n  SET co_grpcom=0,co_detgrpcom=0";
                        strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                        strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                        strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                        strSQLGPD+=" ;\n ";    
                        if ( rstGP1.getInt("maximo") <=2)
                        {
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                         
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                                     
                        }    
                        else
                        {
                            strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n";                    
                            strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n"; 
                        }    

                    }
                    else
                    {
                        if ( rstGP1.getInt("co_tipAgr") ==1)                        
                        {
                            strSQLGPD+=" \n UPDATE tbm_inv";
                            strSQLGPD+=" \n  SET co_grpinv=0,co_detGrpInv=0";
                            strSQLGPD+=" \n  , fe_ultmod= '" + fecha +"'";
                            strSQLGPD+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strSQLGPD+=" \n  WHERE tx_itm='"+ rstGP1.getString("itemDet") +"'";
                            strSQLGPD+=" ;\n ";    
                            
                           if ( rstGP1.getInt("maximo") <=2)
                            {
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                         
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_cabGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo") +" \n";                                                     
                            }    
                            else
                            {
                                strSQLGPD +="\n DELETE  Dynamics2021..tbh_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n";                    
                                strSQLGPD +="\n DELETE  Dynamics2021..tbm_detGrpInv where co_grpinv = "+rstGP1.getInt("co_grpo")+" and tx_codItm ='"+rstGP1.getString("itemDet")   +"' \n"; 
                            } 
                            
                        }
                                                
                    } 


                   
           }   
                System.out.println("anulacionAgrupacionCabecera: " + strSQLGPD);
                stmGP1.executeUpdate(strSQLGPD);  
                blnRes=true;            
               
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

////public boolean ejecucionActualizacionAtributo(String cadenaItems)//para poder ejecutar de forma automatica al tabla de atributos
////{
////    java.sql.Statement stmGP1;
////    java.sql.Connection conGP1;
////    java.sql.ResultSet rstGP1; 
////    java.sql.CallableStatement cal; 
////    boolean blnRes=false;
////    String strSQLIn="";
////    try
////    {
////     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
////       if (conGP1!=null)
////       {
////            strSQLIn="{call spInv45(?)}";
////            cal = conGP1.prepareCall(strSQLIn);
////            cadenaItems = "'" + cadenaItems + "'";
////            cal.setString(1, cadenaItems); // Asignar parámetro de entrada
////            boolean hasResults = cal.execute();     
////            int resultado = cal.getUpdateCount(); // Capturar código de retorno            
////             blnRes=true;
////        }            
////            conGP1.close();
////            conGP1=null;
////        }
////    catch (java.sql.SQLException e){
////       blnRes=false;
////    }
////    catch (Exception e){
////       blnRes=false;
////    }    
////    return blnRes;  
////    
////}
public boolean borradoTblListaAtributo(String cadenaItems)
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1;     
    boolean blnRes=false;
    String strSQLGPD="";
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
            stmGP1=conGP1.createStatement();
            strSQLGPD+=" \n DELETE  FROM Dynamics2021..tbl_ItemsClasiAtrib ";
                strSQLGPD+=" \n  WHERE CodItm in ("+ cadenaItems+")";            
            strSQLGPD+=" ;\n ";   
            stmGP1.executeUpdate(strSQLGPD); 
            blnRes=true;
            stmGP1.close();
            stmGP1=null;                        
        }            
            conGP1.close();
            conGP1=null;
        }
    catch (java.sql.SQLException e){
       blnRes=false;
    }
    catch (Exception e){
       blnRes=false;
    }    
    return blnRes;
}

public boolean borradoTblPedSug()
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1; 
    java.sql.ResultSet rstGP1;     
    boolean blnRes=false;
    String strSQLGPD="";
    try
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
            stmGP1=conGP1.createStatement();
            strSQLGPD+=" \n DELETE  FROM Dynamics2021..tbl_ItmDatPedSug ";
            strSQLGPD+=" ;\n ";   
            stmGP1.executeUpdate(strSQLGPD); 
            blnRes=true;
            stmGP1.close();
            stmGP1=null;                        
        }            
            conGP1.close();
            conGP1=null;
        }
    catch (java.sql.SQLException e){
       blnRes=false;
    }
    catch (Exception e){
       blnRes=false;
    }    
    return blnRes;
}

public boolean actualizarTblListaAtributo(String cadenaItems)
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1;     
    boolean blnRes=false;
    String strSQLGPD=""; 
    try 
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
            stmGP1=conGP1.createStatement();
            strSQLGPD+=" \n INSERT INTO tbl_ItemsClasiAtrib ( ";
            strSQLGPD+=" \n                                   CodItm, codAlt, Linea, clase, Marca, Material, Peso_KG, compras, SubCompras,  ";
            strSQLGPD+=" \n                                   Control, SubControl, codLinea, codClase, codMarca, codMaterial, codPeso,";
            strSQLGPD+=" \n                                   codCompras, CodSubCompras, codControl, codSubControl ";
            strSQLGPD+=" \n                                 )";               
            strSQLGPD+=" \n SELECT";   
            strSQLGPD+=" \n         CodItm, codAlt, Linea, clase, Marca, Material, Peso_KG, compras, SubCompras,";  
            strSQLGPD+=" \n         Control, SubControl, codLinea, codClase, codMarca, codMaterial, codPeso,";  
            strSQLGPD+=" \n         codCompras, CodSubCompras, codControl, codSubControl";  
            strSQLGPD+=" \n FROM Dynamics2021..view_ItemsClasiAtrib";  
            strSQLGPD+=" \n WHERE CodItm in ("+ cadenaItems+")";              
            strSQLGPD+=" ;\n ";   
            stmGP1.executeUpdate(strSQLGPD); 
            blnRes=true;
            
            stmGP1.close();
            stmGP1=null;            
        }            

            conGP1.close();
            conGP1=null;
        }
    catch (java.sql.SQLException e){
       blnRes=false;
    }
    catch (Exception e){
       blnRes=false;
    }    
    return blnRes;
}

public boolean actualizarTblListaPedSug()
{
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1;     
    boolean blnRes=false;
    String strSQLGPD=""; 
    try 
    {
     conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
       if (conGP1!=null)
       {
            stmGP1=conGP1.createStatement();
            strSQLGPD+=" \n INSERT INTO tbl_ItmDatPedSug ( ";
            strSQLGPD+=" \n                                   ITEMNMBR, ITMSHNAM, ITMGEDSC, UOMSCHDL, Compra, SubCompra,  ";
            strSQLGPD+=" \n                                   nd_proAnuVenManSolito, nd_InvTotalSolito, nd_StockBase, nd_TransitoInv, nd_MOQ, nd_fre,";
            strSQLGPD+=" \n                                   nd_frecuenciaCalSolito, nd_frecuenciaFinalSolito, nd_pesoSolito, nd_precioSolito, ";
            strSQLGPD+=" \n                                    ppmaxSolito, ppmninSolito, ne_ord, itemca,nd_frecuenciaCalSolitoPESO,nd_frecuenciaFinalSolitoPESO,nd_proAnuVenManSolitoPESO,nd_InvTotalSolitoPESO,nd_MOQPESO";            
            strSQLGPD+=" \n                                 )";               
            strSQLGPD+=" \n SELECT";   
            strSQLGPD+=" \n          ITEMNMBR, ITMSHNAM, ITMGEDSC, UOMSCHDL, Compra, ISNULL(SubCompra,'') AS SubCompra, ";  
            strSQLGPD+=" \n          nd_proAnuVenManSolito, nd_InvTotalSolito, nd_StockBase, nd_TransitoInv, nd_MOQ, nd_fre,";  
            strSQLGPD+=" \n          nd_frecuenciaCalSolito, ROUND(nd_frecuenciaFinalSolito, 0) AS nd_frecuenciaFinalSolito, ";  
            strSQLGPD+=" \n          nd_pesoSolito, nd_precioSolito, ";              
            strSQLGPD+=" \n          (ROUND(nd_frecuenciaFinalSolito, 0) + nd_StockBase + nd_TransitoInv) * nd_proAnuVenManSolito AS ppmaxSolito, ";  
            strSQLGPD+=" \n          (ROUND(nd_frecuenciaFinalSolito, 0) + nd_StockBase + nd_TransitoInv) * nd_proAnuVenManSolito - nd_MOQ AS ppmninSolito, ";  
            strSQLGPD+=" \n          (SELECT ne_ord FROM dynamics2021..tbm_cfgAtrInv WHERE tx_cfgPadDat =compra AND co_cfg=3 AND (co_cfgHij  IS NULL OR co_cfgHij='')) AS ne_ord, ";  
            strSQLGPD+=" \n           itemca,nd_frecuenciaCalSolitoPESO,nd_frecuenciaFinalSolitoPESO,nd_proAnuVenManSolitoPESO,nd_InvTotalSolitoPESO,nd_MOQPESO";  
            strSQLGPD+=" \n FROM (";  
            strSQLGPD+=" \n          SELECT ";  
            strSQLGPD+=" \n                 TRIM(a1.ITEMNMBR) AS ITEMNMBR, a1.ITMSHNAM, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL, ";  
            strSQLGPD+=" \n                 extItem.Extender_Record_ID, TRIM(extCompra.STRGA255) AS Compra, ";  
            strSQLGPD+=" \n                 TRIM(extSubcompra.STRGA255) AS SubCompra,  a2.promVntaMen AS nd_proAnuVenManSolito,";  
            strSQLGPD+=" \n                 ISNULL(disp.nd_stockTotal, 0) AS nd_InvTotalSolito, ISNULL(extPeso.TOTAL, 0) AS nd_pesoSolito,";  
            strSQLGPD+=" \n                 ROUND(a3.UOMPRICE * 0.75, 2) AS nd_precioSolito, ISNULL(a2.stokbase, 0) AS nd_StockBase,  ";              
            strSQLGPD+=" \n                 ISNULL(a2.tra, 0) AS nd_TransitoInv, ISNULL(a2.moq, 0) AS nd_MOQ, ISNULL(a2.fre, 0) AS nd_fre, ";              
            strSQLGPD+=" \n                 ISNULL(a2.exc, 0) AS nd_exc, ";              
            strSQLGPD+=" \n                 ROUND(ISNULL(ISNULL(a2.moq, 0) / NULLIF (ISNULL(a2.promVntaMen, 0), 0), 0), 2) AS nd_frecuenciaCalSolito,   ";              
            strSQLGPD+=" \n                 ROUND(ISNULL(  ";                          
            strSQLGPD+=" \n                      CASE   ";
            strSQLGPD+=" \n                             WHEN (ISNULL(a2.moq, 0) / NULLIF (ISNULL(a2.promVntaMen, 0), 0)) < ISNULL(a2.fre, 0)  ";
            strSQLGPD+=" \n                             THEN ISNULL(a2.fre, 0) ";
            strSQLGPD+=" \n                             ELSE (ISNULL(a2.moq, 0) / NULLIF (ISNULL(a2.promVntaMen, 0), 0))  ";            
            strSQLGPD+=" \n                     END, 0), 2) AS nd_frecuenciaFinalSolito, ";            
            strSQLGPD+=" \n                 a2.itemca, ";            
            strSQLGPD+=" \n                 CEILING(ISNULL(( ISNULL(a2.moq, 0) * ISNULL(extPeso.TOTAL, 0) ) / NULLIF ((ISNULL(a2.promVntaMen, 0) * ISNULL(extPeso.TOTAL, 0)), 0), 0)) AS nd_frecuenciaCalSolitoPESO,  ";            
            strSQLGPD+=" \n                 CEILING(ISNULL( ";            
            strSQLGPD+=" \n                                 CASE  ";            
            strSQLGPD+=" \n                                     WHEN ((ISNULL(a2.moq, 0) *ISNULL(extPeso.TOTAL, 0)) / NULLIF ((ISNULL(a2.promVntaMen, 0) * ISNULL(extPeso.TOTAL, 0)), 0)) < ISNULL(a2.fre, 0)   ";            
            strSQLGPD+=" \n                                     THEN ISNULL(a2.fre, 0)    ";                        
            strSQLGPD+=" \n                                     ELSE ((ISNULL(a2.moq, 0) *ISNULL(extPeso.TOTAL, 0)) / NULLIF (ISNULL(a2.promVntaMen, 0) * ISNULL(extPeso.TOTAL, 0), 0))";            
            strSQLGPD+=" \n                                     END, 0)) AS nd_frecuenciaFinalSolitoPESO,   ";            
            strSQLGPD+=" \n                 (a2.promVntaMen *ISNULL(extPeso.TOTAL, 0) ) AS nd_proAnuVenManSolitoPESO,";            
            strSQLGPD+=" \n                 (ISNULL(disp.nd_stockTotal, 0) *ISNULL(extPeso.TOTAL, 0)) AS nd_InvTotalSolitoPESO,  ";            
            strSQLGPD+=" \n                 (ISNULL(a2.moq, 0) *ISNULL(extPeso.TOTAL, 0) ) AS nd_MOQPESO ";            
            strSQLGPD+=" \n           FROM TUVAL.dbo.IV00101 AS a1 WITH (NOLOCK) ";            
            strSQLGPD+=" \n                 INNER JOIN TUVAL.dbo.EXT01100 AS extItem ON a1.ITEMNMBR = extItem.Extender_Key_Values_1   AND extItem.Extender_Window_ID = 'MSW_ADI_IV'  ";            
            strSQLGPD+=" \n                 INNER JOIN TUVAL.dbo.EXT01101 AS extCompra  ON extItem.Extender_Record_ID = extCompra.Extender_Record_ID  AND extCompra.Field_ID = 199  ";            
            strSQLGPD+=" \n                 LEFT JOIN TUVAL.dbo.EXT01101 AS extSubcompra ON extItem.Extender_Record_ID = extSubcompra.Extender_Record_ID   AND extSubcompra.Field_ID = 201";            
            strSQLGPD+=" \n                 INNER JOIN Dynamics2021..view_listaPropiedadesItems AS a2 WITH (NOLOCK)   ON a1.ITEMNMBR = a2.CodigoArticulo ";                        
            strSQLGPD+=" \n                 INNER JOIN TUVAL.dbo.IV00108 AS a3 WITH (NOLOCK)  ON a1.ITEMNMBR = a3.ITEMNMBR  AND a3.PRCLEVEL = 'GENERAL' ";        
            strSQLGPD+=" \n                 LEFT OUTER JOIN TUVAL.dbo.EXT01103 AS extPeso   ON extItem.Extender_Record_ID = extPeso.Extender_Record_ID  AND extPeso.Field_ID = 179 ";        
            strSQLGPD+=" \n                 LEFT OUTER JOIN dbo.view_DIS_IMP_TRANS_EXI_TOTAL AS disp    ON a1.ITEMNMBR = disp.co_itmMae ";        
            strSQLGPD+=" \n          WHERE";        
            strSQLGPD+=" \n                  (a1.ITEMTYPE = 1)  AND (a1.ITEMNMBR LIKE '%I' OR a1.ITEMNMBR LIKE '%S')  AND (a1.INACTIVE = 0)) AS a11;";                    
            
            
            strSQLGPD+=" ;\n ";   
            stmGP1.executeUpdate(strSQLGPD); 
            blnRes=true;
            
            stmGP1.close();
            stmGP1=null;            
        }            

            conGP1.close();
            conGP1=null;
        }
    catch (java.sql.SQLException e){
       blnRes=false;
    }
    catch (Exception e){
       blnRes=false;
    }    
    return blnRes;
}


public boolean ejecucionActualizacionAtributo(String cadenaItems)//para poder ejecutar de forma automatica al tabla de atributos
{
    boolean blnRes=false;
    if (borradoTblListaAtributo(cadenaItems))
    {
        if (actualizarTblListaAtributo(cadenaItems))
            blnRes=true;
    }
   
    return blnRes;  
    
}

public boolean ejecucionActualizacionTablaPedidosSugeridos() 
{
    boolean blnRes=false;
    if (borradoTblPedSug())
    {
        if (actualizarTblListaPedSug())
            blnRes=true;
    }
   
    return blnRes;  
    
} 

public boolean ejecucionActualizacionTablaPedidosSugeridosSP() 
{
    boolean blnRes = true;
    java.sql.Statement stmGP1;
    java.sql.Connection conGP1;
    java.sql.ResultSet rstGP1;
    java.sql.CallableStatement cal = null;
    try {
        conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), 
                
                                            objParSis.getUsuarioBaseDatos_Dynamics2021(), 
                                            objParSis.getClaveBaseDatos_Dynamics2021());                     
        if (conGP1 != null) {
            String strSQLGP = "{call sp_Insertar_ItmDatPedSug()}";
            cal = conGP1.prepareCall(strSQLGP);
            cal.execute();
            
        }
    } catch (java.sql.SQLException e) {
        blnRes = false;
    } catch (Exception e) {
        blnRes = false;
    } finally {
        try {
        } catch (Exception ex) {
        }
    }
    return blnRes; 
    
}


    public static int obtenerUltimoDiaDelMesActual() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }



}
  