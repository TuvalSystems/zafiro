/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer14
package Librerias.ZafProCpbEle;

import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafCpbEle.ZafCpbEle;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafProCpbEle
{
   Librerias.ZafParSis.ZafParSis objParSis;
   ZafUtil objUti;
   private Connection con;
   String strSQL, strNomEmp, strPrmNomEmp_Glo, strEjeSer, strFecIngGuiRem_CabGuiRem, strNomFil_p12, strClvFil_p12;
   static final int INT_ARL_DAT_COD_EMP = 0;
   static final int INT_ARL_DAT_COD_LOC = 1;
   static final int INT_ARL_DAT_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_COD_DOC = 3;
   
   public ZafProCpbEle(Librerias.ZafParSis.ZafParSis obj)
   {
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         objUti = new ZafUtil();
      }
      
      catch (CloneNotSupportedException e)
      {  //mostrarMsgErr(e.getMessage());
      }
   }
   
   /**
    * Este metodo se encarga de leer la tablas donde se guardan las facturas, comprobantes de retencion, notas de credito y guias de remision 
    * para firmar, enviar y autorizar cada documento XML usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   public boolean ejecutarProcesoCompElec(String strPrmNomEmp)
   {
      boolean blnRes = true;
      
      try
      {  
         strEjeSer = objParSis.getEjecutarServicio();
         strFecIngGuiRem_CabGuiRem = objParSis.getFechaIngresoGuiRem_CabGuiRem();
         strPrmNomEmp_Glo = strPrmNomEmp.toUpperCase();
         
         if (strPrmNomEmp_Glo.indexOf("TUVAL") != -1 || strPrmNomEmp_Glo.indexOf("CASTEK") != -1 || strPrmNomEmp_Glo.indexOf("DIMULTI") != -1)
         {
            con = DriverManager.getConnection(objParSis.getStringConexion_Tuval_Castek_Dimulti(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
         }
         else if (strPrmNomEmp_Glo.indexOf("COSENCO") != -1)
         {
            con = DriverManager.getConnection(objParSis.getStringConexion_Cosenco(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
         }
         else if (strPrmNomEmp_Glo.indexOf("ECUATOSA") != -1)
         {
            con = DriverManager.getConnection(objParSis.getStringConexion_Ecuatosa(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
         }
         else if (strPrmNomEmp_Glo.indexOf("DETOPACIO") != -1)
         {
            con = DriverManager.getConnection(objParSis.getStringConexion_Detopacio(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
         }
         
         System.out.println("Nom_BD: " + con.getCatalog());
         ejecutarProcesoGuiRem(); //DBN
         con.close();
         con = null;
      }
      
      catch(Exception Evt)
      {  
         blnRes = false;
      }
         
      return blnRes;
   }
   
   /**
    * Esta funcion se encarga de leer la tabla donde se guardan las guias de remision para firmar, enviar y autorizar cada guia de remision XML 
    * usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   private boolean ejecutarProcesoGuiRem()
   {
      boolean blnRes = true, blnDirMacSrvPrd_Found;
      int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      String strAux, strEjeProTipAmbSriPru;
      ArrayList arlResWS, arlRegGuiRem, arlDatGuiRem;
      Statement stmLoc;
      ResultSet rstLoc;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            arlDatGuiRem = new ArrayList();
            strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.fe_ing ";
            strSQL += "FROM   tbm_cabguirem as a1 ";
            strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc ";
            strSQL += "WHERE      a1.st_reg = 'A' and a1.st_autFacEle is null ";
            strSQL += "           and a2.st_utiFacEle = 'S' ";
            strSQL += "           and a2.co_cat = 24 "; //24: Guia de remision
            strSQL += "           and a1.ne_numdoc <> 0 ";
            strSQL += "           and a1.fe_ing >= '" + strFecIngGuiRem_CabGuiRem + "' ";
            strSQL += "ORDER BY   a1.fe_ing";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next())
            {  arlRegGuiRem = new ArrayList();
               arlRegGuiRem.add(INT_ARL_DAT_COD_EMP, "" + rstLoc.getInt("co_emp"));
               arlRegGuiRem.add(INT_ARL_DAT_COD_LOC, "" + rstLoc.getInt("co_loc"));
               arlRegGuiRem.add(INT_ARL_DAT_COD_TIP_DOC, "" + rstLoc.getInt("co_tipdoc"));
               arlRegGuiRem.add(INT_ARL_DAT_COD_DOC, "" + rstLoc.getInt("co_doc"));
               arlDatGuiRem.add(arlRegGuiRem);
            }
            
            //DBN_Prueba inicio
            /*intCodEmp = 2;
            intCodLoc = 4;
            intCodTipDoc = 231;
            intCodDoc = 23706;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            blnRes = objCpbEle.setClaveAccesoaGuiRem(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatGuiRem.size(); i++)
            {  arlResWS = new ArrayList();
               intCodEmp = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
               
               if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
               {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                  strAux = "Servicio 14: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                  strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                  updateDesFacEle("tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
               }
               else if (strEjeSer.equals("S"))
               {  //strEjeSer es un parametro leido del objeto ZafParSis.
                  //S = indica que si se debe ejecutar el servicio.
                  blnRes = objCpbEle.setClaveAccesoaGuiRem(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               }
               else
               {  //N = indica que no se debe ejecutar el servicio.
                  strAux = "No se esta ejecutando el proceso del Servicio 14. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                  updateDesFacEle("tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
               }
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = false;
      }
         
      return blnRes;
   } //Funcion ejecutarProcesoGuiRem()
   
   private boolean getNombre_y_clave_archivo_p12(int intCodEmp)
   {
      boolean blnRes = true;
      
      try
      {  strNomEmp = "";
         strNomFil_p12 = "";
         strClvFil_p12 = "";
         
         if (strPrmNomEmp_Glo.indexOf("TUVAL") != -1 || strPrmNomEmp_Glo.indexOf("CASTEK") != -1 || strPrmNomEmp_Glo.indexOf("DIMULTI") != -1)
         {  if (intCodEmp == 1)
            {  strNomEmp = "Tuval";
               strNomFil_p12 = objParSis.getTuvalNombreArchivo_p12();
               strClvFil_p12 = objParSis.getTuvalClaveArchivo_p12();
            }
            else if (intCodEmp == 2)
            {  strNomEmp = "Castek";
               strNomFil_p12 = objParSis.getCastekNombreArchivo_p12();
               strClvFil_p12 = objParSis.getCastekClaveArchivo_p12();
            } 
            else if (intCodEmp == 4)
            {  strNomEmp = "Dimulti";
               strNomFil_p12 = objParSis.getDimultiNombreArchivo_p12();
               strClvFil_p12 = objParSis.getDimultiClaveArchivo_p12();  
            }
         }
         else if (strPrmNomEmp_Glo.indexOf("COSENCO") != -1)
         {  
            strNomEmp = "Cosenco";
            strNomFil_p12 = objParSis.getCosencoNombreArchivo_p12();
            strClvFil_p12 = objParSis.getCosencoClaveArchivo_p12();
         }
         else if (strPrmNomEmp_Glo.indexOf("ECUATOSA") != -1)
         {  
            strNomEmp = "Ecuatosa";
            strNomFil_p12 = objParSis.getEcuatosaNombreArchivo_p12();
            strClvFil_p12 = objParSis.getEcuatosaClaveArchivo_p12();
         }
         else if (strPrmNomEmp_Glo.indexOf("DETOPACIO") != -1)
         {  
            strNomEmp = "Detopacio";
            strNomFil_p12 = objParSis.getDetopacioNombreArchivo_p12();
            strClvFil_p12 = objParSis.getDetopacioClaveArchivo_p12();
         }
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion getNombre_y_clave_archivo_p12()
   
   private boolean updateDesFacEle(String strNomTbl, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strMsj)
   {
      boolean blnRes = true;
      String strSQL;
      Statement stmLoc;
      
      try
      {  stmLoc = con.createStatement();
         
         strSQL =  "UPDATE " + strNomTbl + " ";
         strSQL += "SET st_autFacEle = 'E',";
         strSQL += " tx_desErrFacEle = '" + strMsj + "' ";
         strSQL += "WHERE co_emp = " + intCodEmp;
         strSQL += " and co_loc = " + intCodLoc;
         strSQL += " and co_tipdoc = " + intCodTipDoc;
         strSQL += " and co_doc = " + intCodDoc;
         stmLoc.executeUpdate(strSQL);
         
         stmLoc.close();
         stmLoc = null;
      }
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
   } //Funcion updateDesFacEle()
}