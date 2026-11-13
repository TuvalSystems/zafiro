/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer11
package Librerias.ZafProCpbEle;

import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafCpbEle.ZafCpbEle;
import Librerias.ZafParSis.ZafParSis;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
//Librerias para envio de correo electronico
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafProCpbEle
{
   ZafParSis objParSis;
   ZafUtil objUti;
   private Connection con;
   String strSQL, strNomEmp, strPrmNomEmp_Glo, strNumDecTolDifCalIvaSub, strEjeSer, strNumDecTolDifCalValRet_PagMovInv, strFecIngGuiRem_CabGuiRem;
   String strNomFil_p12, strClvFil_p12;
   static final int INT_ARL_DAT_COD_EMP = 0;
   static final int INT_ARL_DAT_COD_LOC = 1;
   static final int INT_ARL_DAT_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_COD_DOC = 3;
   static final int INT_ARL_DAT_EST_AUT_FAC_ELE = 4;
   
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
         objParSis = new ZafParSis();
         strNumDecTolDifCalIvaSub = objParSis.getNumeroDecimalesToleranciaDiferenciaCalculoIvaSubtotal();
         strNumDecTolDifCalValRet_PagMovInv = objParSis.getNumeroDecimalesToleranciaDiferenciaCalculoValorRetencionPagMovInv();
	 strEjeSer = objParSis.getEjecutarServicio();
         strFecIngGuiRem_CabGuiRem = objParSis.getFechaIngresoGuiRem_CabGuiRem();
         System.out.println("Parametro de ejecucion de servicio: " + strEjeSer);
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
         
         ejecutarProcesoGuiRem();  
         ejecutarProcesoFac(); //DBN    
         ejecutarProcesoCpbRet();
         ejecutarProcesoNotCre();  
         //ejecutarProcesoLiqCom();  
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
    * Esta funcion se encarga de leer la tabla donde se guardan las facturas para firmar, enviar y autorizar cada factura XML 
    * usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   private boolean ejecutarProcesoFac()
   {
      boolean blnRes = true, blnDirMacSrvPrd_Found, blnRes1, blnRes2, blnRes3, blnRes4;
      int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      String strAux, strNumGuiRem, strNumPed, strTipProReaEmpRel, strEjeProTipAmbSriPru;
      BigDecimal bdePorComSol_CfgImpLoc;
      ArrayList arlResWS, arlRegFac, arlDatFac;
      Statement stmLoc;
      ResultSet rstLoc;
      
      try{  
        if (con != null){  
            stmLoc = con.createStatement();
            arlDatFac = new ArrayList();
            //Se va a leer el campo tbm_emp.tx_tipProReaEmpRel el cual indica si hay que tomar o no en cuenta a las facturas relacionadas.
            strTipProReaEmpRel = "";
            strSQL = "select tx_tipProReaEmpRel from tbm_emp where co_emp = 0";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next()){  
                strTipProReaEmpRel = rstLoc.getString("tx_tipProReaEmpRel") == null? "" :rstLoc.getString("tx_tipProReaEmpRel");
            }
            
            strSQL="";
            strSQL+=" SELECT a1.* \n";
            strSQL+=" FROM ( \n";
            strSQL+="    ( \n";
            strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.st_autFacEle, a1.fe_ing, a1.ne_numdoc, a1.fe_doc, a1.tx_nomcli,";
            strSQL+="               a1.tx_ruc as tx_rucCli, a3.tx_tipide as tx_tipidecli, a1.nd_poriva, abs(a1.nd_sub) as nd_sub, abs(a1.nd_valiva) as nd_valiva, abs(a1.nd_tot) as nd_tot,  \n";
            strSQL+= "             a1.tx_obs1, a1.tx_obs2, a1.tx_numguirem, a1.tx_numped, a4.nd_porImp as nd_porImpComSol \n";
            strSQL+= "      FROM tbm_cabmovinv as a1 \n";
            strSQL+= "      INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc ";
            strSQL+= "      LEFT  JOIN tbm_cli as a3 on a3.co_emp = a1.co_emp and a3.co_cli = a1.co_cli ";
            strSQL+= "      LEFT  JOIN tbm_cfgImpLoc as a4 on a1.co_emp = a4.co_emp and a1.co_loc = a4.co_loc and a4.co_imp = 3 "; //co_imp 3 = DESSOL = Descuento Solidario (-) 2% IVA
            strSQL+= "                           and (CASE WHEN a4.fe_vigHas IS NULL THEN a1.fe_doc >= a4.fe_vigDes ELSE a1.fe_doc >= a4.fe_vigDes AND a1.fe_doc <= a4.fe_vigHas END) ";
            strSQL+= "      WHERE      a1.st_reg = 'A' and (a1.st_autFacEle is null or a1.st_autFacEle = 'S') "; //DBN_real 
            strSQL+= "           and a2.st_utiFacEle = 'S' ";
            strSQL+= "           and a2.co_cat = 3 "; //3: Facturas de ventas
            strSQL+= "           and a1.ne_numdoc <> 0  and not ((a1.co_emp = 1 and a1.co_loc = 11) or (a1.co_emp = 2 and a1.co_loc = 5) or (a1.co_emp = 4 and a1.co_loc = 11)) ";
            strSQL+="       )    \n";
            strSQL+="   UNION ALL(  \n";
            strSQL+="       SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.st_autFacEle, a1.fe_ing, a1.ne_numdoc, a1.fe_doc,     \n";
            strSQL+="              a1.tx_nomcli, a1.tx_ruc as tx_rucCli, a3.tx_tipide as tx_tipidecli, a1.nd_poriva,   \n";
            strSQL+="              abs(a1.nd_sub) as nd_sub, abs(a1.nd_valiva) as nd_valiva, abs(a1.nd_tot) as nd_tot,   \n";
            strSQL+="              a1.tx_obs1, a1.tx_obs2, a1.tx_numguirem, a1.tx_numped, a4.nd_porImp as nd_porImpComSol   \n";
            strSQL+="       FROM tbm_cabmovinv as a1      \n";
            strSQL+="       INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc    \n";
            strSQL+="       LEFT JOIN tbm_cli as a3 on a3.co_emp = a1.co_emp and a3.co_cli = a1.co_cli   \n";
            strSQL+="       LEFT JOIN tbm_cfgImpLoc as a4 on a1.co_emp = a4.co_emp and a1.co_loc = a4.co_loc and a4.co_imp = 3 and  \n";
            strSQL+="                                        (CASE WHEN a4.fe_vigHas IS NULL THEN a1.fe_doc >= a4.fe_vigDes ELSE a1.fe_doc >= a4.fe_vigDes AND a1.fe_doc <= a4.fe_vigHas END) \n";
            strSQL+="       WHERE a1.st_reg = 'A' and /* (a1.st_autFacEle is null or a1.st_autFacEle = 'S') and */ \n";
            strSQL+="             a2.st_utiFacEle = 'S' and a2.co_cat = 3   and a1.ne_numdoc <> 0  and \n";
            strSQL+="             ((a1.co_emp = 1 and a1.co_loc = 11) or (a1.co_emp = 2 and a1.co_loc = 5) or (a1.co_emp = 4 and a1.co_loc = 11)) AND a1.st_autFacEle = 'L' \n";
            strSQL+="       ) \n";
            strSQL+=" ) as a1 \n";
            
            
//            if (!strTipProReaEmpRel.equals("P"))
//            {  //P = Prestamos. Si strTipProReaEmpRel <> 'P', significa que la fecha del sistema indica que debe ser el dia 26 o 27, y a partir de este dia
//               //todo lo que consta como prestamos entre las empresas relacionadas las vendedoras podrian facturar. Esto no puede facturarse sin que haya
//               //una revision previa del recurso encargado de Facturas relacionadas para corroborar que los datos de estos prestamos esten correctamente 
//               //guardados en las tablas respectivas. Una vez concluida dicha revision, se debera cambiar el estado del campo tbm_emp.tx_tipProReaEmpRel a 'P'.
//               //En conclusion, si strTipProReaEmpRel <> 'P' se debera ignorar las facturas relacionadas y no generar el XML.
//               strSQL += "        and not ((a1.co_emp = 1 and a1.co_loc = 11) or (a1.co_emp = 2 and a1.co_loc = 5) or (a1.co_emp = 4 and a1.co_loc = 11)) ";
//            }
            
            strSQL += "ORDER BY   a1.fe_ing";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next()){  
                
                strAux = rstLoc.getString("nd_porImpComSol") == null? "" :rstLoc.getString("nd_porImpComSol");
               
                if (strAux.equals(""))
                    bdePorComSol_CfgImpLoc = new BigDecimal("0.00");
                else{  
                    bdePorComSol_CfgImpLoc = new BigDecimal(strAux);
                    bdePorComSol_CfgImpLoc = objUti.redondearBigDecimal(bdePorComSol_CfgImpLoc, objParSis.getDecimalesMostrar());
                }
                
               strNumGuiRem = rstLoc.getString("tx_numguirem") == null? "" :rstLoc.getString("tx_numguirem");
               strNumPed = rstLoc.getString("tx_numped") == null? "" :rstLoc.getString("tx_numped");
               
               if ( ( (rstLoc.getString("tx_rucCli").equals("0990293244001")) || /* HOLCIM */
                       (rstLoc.getString("tx_rucCli").equals("1791924037001")) ||  /*  AVICOLA SAN ISIDRO S.A. AVISID  */ 
                       (rstLoc.getString("tx_rucCli").equals("0990576459001")) ||  /*  PRODUMAR  */
                       (rstLoc.getString("tx_rucCli").equals("0990608504001"))   /* OMARSA */ 
                       
                       ) 
                       
                       
                       && (strNumGuiRem.equals("") || strNumPed.equals("")) )
               {
                  //Si el RUC es de Holcim Ecuador (0990293244001), strNumGuiRem = "" y strNumPed = "", entonces no se lo debe agregar al arreglo
               }
               else
               {  blnRes1 = existeDiferencias_Sub_Iva_Fac(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabmovinv");
                  blnRes2 = existeDiferencias_Iva_ComSol_Fac(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getString("fe_doc"), bdePorComSol_CfgImpLoc, "tbm_cabmovinv");
                  blnRes3 = existeIncoherencia_IvaFac_y_Cliente(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabmovinv");
                  blnRes4 = existeIncoherencia_ItmDetFac_y_MaeItm(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabmovinv");
                  
                  if (blnRes1 == false && blnRes2 == false && blnRes3 == false && blnRes4 == false)
                  {  arlRegFac = new ArrayList();
                     arlRegFac.add(INT_ARL_DAT_COD_EMP, "" + rstLoc.getInt("co_emp"));
                     arlRegFac.add(INT_ARL_DAT_COD_LOC, "" + rstLoc.getInt("co_loc"));
                     arlRegFac.add(INT_ARL_DAT_COD_TIP_DOC, "" + rstLoc.getInt("co_tipdoc"));
                     arlRegFac.add(INT_ARL_DAT_COD_DOC, "" + rstLoc.getInt("co_doc"));
                     arlRegFac.add(INT_ARL_DAT_EST_AUT_FAC_ELE, "" + rstLoc.getString("st_autFacEle") == null? "" :rstLoc.getString("st_autFacEle"));
                     arlDatFac.add(arlRegFac);
                  }
               }
            }
            
            //DBN_Prueba inicio
            /*intCodEmp = 4;
            intCodLoc = 12;
            intCodTipDoc = 228;
            intCodDoc = 11117;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            String strEstCpbEle = "S";
            if (strEstCpbEle.equals("S"))
            {  blnRes = objCpbEle.getNumAutorizacionFac(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
               //strClvAccCpb = objUti.getStringValueAt(arlResWS, 0, 5); //Col 5: Clave de acceso del comprobante
               //objZafImpCon = new ZafImpCon(con);
               //objZafImpCon.actualizarEnCont(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, objCpbEle.getRetClaveCon(), objCpbEle.getSerieCont(), strClvAccCpb);
            }*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatFac.size(); i++)
            {  arlResWS = new ArrayList();
               intCodEmp = objUti.getIntValueAt(arlDatFac, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatFac, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatFac, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatFac, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
               
               strSQL = "select count(1) from tbm_emp where 1 = 0";
               rstLoc = stmLoc.executeQuery(strSQL);
               
               if (rstLoc.next())
               {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
                  //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
                  //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
                  if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
                  {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                     strAux =  "Servicio 11: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                     strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                     updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
                  else 
                   if (strEjeSer.equals("S")){  
                        //strEjeSer es un parametro leido del objeto ZafParSis.
                     //S = indica que si se debe ejecutar el servicio.
                        blnRes = objCpbEle.getNumAutorizacionFac(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                    }
                    else
                    {  //N = indica que no se debe ejecutar el servicio.
                        strAux = "No se esta ejecutando el proceso del Servicio 11. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                        updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                    }
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
   } //Funcion ejecutarProcesoFac()
   
   /**
    * Esta funcion se encarga de leer la tabla donde se guardan los comprobantes de retencion para firmar, enviar y autorizar cada retencion XML 
    * usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   private boolean ejecutarProcesoCpbRet()
   {
      boolean blnRes = true, blnDirMacSrvPrd_Found, blnRes1, blnRes2;
      int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      String strAux, strEjeProTipAmbSriPru;
      ArrayList arlResWS, arlRegCpbRet, arlDatCpbRet;
      Statement stmLoc;
      ResultSet rstLoc;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            arlDatCpbRet = new ArrayList();
            strSQL =  "SELECT     a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_ing, a1.fe_doc as fe_doc_cpbret, a1.ne_numdoc1, a3.tx_tipide as tx_tipide_sujret, a1.tx_ruc as tx_ruc_sujret, a1.tx_nomcli as tx_nom_sujret, a1.tx_obs1, a1.tx_obs2 ";
            strSQL += "FROM       tbm_cabpag as a1 ";
            strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc ";
            strSQL += "LEFT  JOIN tbm_cli as a3 on a3.co_emp = a1.co_emp and a3.co_cli = a1.co_cli ";
            strSQL += "WHERE      a1.st_reg = 'A' and (a1.st_autFacEle is null or a1.st_autFacEle = 'S') "; //DBN_real
            //strSQL += "WHERE      a1.st_reg = 'A' and a1.co_emp = 4 and a1.co_loc = 3 and a1.co_tipdoc = 230 and a1.co_doc = 7786 "; //DBN_prueba
            strSQL += "           and a2.st_utiFacEle = 'S' ";
            strSQL += "           and a2.co_cat = 17 "; //17: Retenciones emitidas
            strSQL += "ORDER BY   a1.fe_ing";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next())
            {  blnRes1 = existeDiferencias_CabPag_DetPag_Ret(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabpag");
               blnRes2 = existeDiferencias_PagMovInv_Ret(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabpag");

               if (blnRes1 == false && blnRes2 == false)
               {  arlRegCpbRet = new ArrayList();
                  arlRegCpbRet.add(INT_ARL_DAT_COD_EMP, "" + rstLoc.getInt("co_emp"));
                  arlRegCpbRet.add(INT_ARL_DAT_COD_LOC, "" + rstLoc.getInt("co_loc"));
                  arlRegCpbRet.add(INT_ARL_DAT_COD_TIP_DOC, "" + rstLoc.getInt("co_tipdoc"));
                  arlRegCpbRet.add(INT_ARL_DAT_COD_DOC, "" + rstLoc.getInt("co_doc"));
                  arlDatCpbRet.add(arlRegCpbRet);
               }
            }
            
            //DBN_Prueba inicio
            /*intCodEmp = 1;
            intCodLoc = 12;
            intCodTipDoc = 230;
            intCodDoc = 2585;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            blnRes = objCpbEle.getNumAutorizacionCpbRet(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatCpbRet.size(); i++)
            {  intCodEmp = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
//               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
                blnDirMacSrvPrd_Found = true;  // JM CAMBIAR 8/Oct/2020
               arlResWS = new ArrayList();
               
               strSQL = "select count(1) from tbm_emp where 1 = 0";
               rstLoc = stmLoc.executeQuery(strSQL);
               
               if (rstLoc.next())
               {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
                  //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
                  //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
                  if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
                  {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                     strAux =  "Servicio 11: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                     strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                     updateDesFacEle("tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
                  else if (strEjeSer.equals("S"))
                  {  //strEjeSer es un parametro leido del objeto ZafParSis.
                     //S = indica que si se debe ejecutar el servicio.
                     blnRes = objCpbEle.getNumAutorizacionCpbRet(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }
                  else
                  {  //N = indica que no se debe ejecutar el servicio.
                     strAux = "No se esta ejecutando el proceso del Servicio 11. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                     updateDesFacEle("tbm_cabpag", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
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
   } //Funcion ejecutarProcesoCpbRet()
   
   /**
    * Esta funcion se encarga de leer la tabla donde se guardan las notas de credito para firmar, enviar y autorizar cada nota de credito XML 
    * usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   private boolean ejecutarProcesoNotCre()
   {
      boolean blnRes = true, blnDirMacSrvPrd_Found, blnRes1, blnRes2;
      int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      String strAux, strEjeProTipAmbSriPru;
      ArrayList arlResWS, arlRegNotCre, arlDatNotCre;
      Statement stmLoc;
      ResultSet rstLoc;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            arlDatNotCre = new ArrayList();
            strSQL =  "SELECT     nc.co_emp, nc.co_loc, nc.co_tipdoc, nc.co_doc, nc.fe_ing, nc.fe_doc as fe_doc_notcre, nc.ne_numdoc as ne_numdoc_notcre, nc.ne_numcot as ne_numfac, ";
            /*
            //*** Inicio bloque comentado el 12/Jun/2017 ***
            //Este subquery trae la fecha de la Factura Origen de la Nota de credito. Este query no trae dicha fecha si el local de donde se originó dicha 
            //factura esta cerrado.
            //co_cat = 3: Facturas de ventas
            strSQL += "           ( select     fe_doc ";
            strSQL += "             from       tbm_cabmovinv as b1 ";
            strSQL += "             inner join tbm_cabtipdoc as b2 on b2.co_emp = b1.co_emp and b2.co_loc = b1.co_loc and b2.co_tipdoc = b1.co_tipdoc ";
            strSQL += "             where      b1.co_emp = nc.co_emp and b1.co_loc = nc.co_loc and b1.ne_numdoc = nc.ne_numcot and b2.co_cat = 3 and b1.st_reg = 'A' ";
            //La siguiente condicion (b2.st_utiFacEle = 'S') esta comentada debido a que, si la factura es pre-impresa, entonces no va a traer
            //la Fecha_emision_Fac. Una vez que ya se este trabajando 100% con facturas electronicas, se le puede quitar el comentario
            //strSQL += "and b2.st_utiFacEle = 'S' ";
            strSQL += "           ) as fe_doc_fac, ";
            //*** Fin bloque comentado el 12/Jun/2017 ******
            */
            strSQL += "           fac.fe_doc as fe_doc_fac, ";
            strSQL += "           nc.tx_nomcli, nc.tx_ruc as tx_rucCli, a3.tx_tipide as tx_tipidecli, nc.nd_poriva, abs(nc.nd_sub) as nd_sub, abs(nc.nd_valiva) as nd_valiva, abs(nc.nd_tot) as nd_tot, ";
            strSQL += "           nc.tx_obs1, nc.tx_obs2 ";
            strSQL += "FROM       tbm_cabmovinv as nc "; //nc = Nota de credito (Dev.Vta.)
            strSQL += "INNER JOIN tbm_cabSolDevVen as s on s.co_emp = nc.co_emp and s.co_loc = nc.co_locrelsoldevven and s.co_tipdoc = nc.co_tipdocrelsoldevven and s.co_doc = nc.co_docrelsoldevven ";
            strSQL += "INNER JOIN tbm_cabMovInv as fac on s.co_emp = fac.co_emp and s.co_locrel = fac.co_loc and s.co_tipdocrel = fac.co_tipdoc and s.co_docrel = fac.co_doc ";
            strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = nc.co_emp and a2.co_loc = nc.co_loc and a2.co_tipdoc = nc.co_tipdoc ";
            strSQL += "LEFT  JOIN tbm_cli as a3 on a3.co_emp = nc.co_emp and a3.co_cli = nc.co_cli ";
            strSQL += "WHERE      nc.st_reg = 'A' and (nc.st_autFacEle is null or nc.st_autFacEle = 'S') "; //DBN_real
            //strSQL += "WHERE      nc.st_reg = 'A' and nc.fe_doc between '2017-04-01' and CURRENT_DATE "; //DBN_prueba
            //strSQL += "WHERE      nc.co_emp = 4 and nc.co_loc = 10 and nc.co_tipdoc = 229 and nc.co_doc = 1178 "; //DBN_prueba
            strSQL += "           and a2.st_utiFacEle = 'S' ";
            strSQL += "           and a2.co_cat = 4 "; //4: Devoluciones de ventas (Notas de credito)
            strSQL += "ORDER BY   nc.fe_ing";
            
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next())
            {  blnRes1 = existeDiferencias_Sub_Iva_DevVta(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabmovinv");
               blnRes2 = existeDiferencias_Iva_ComSol_DevVta(rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), "tbm_cabmovinv");
               
               if (blnRes1 == false && blnRes2 == false)
               {  arlRegNotCre = new ArrayList();
                  arlRegNotCre.add(INT_ARL_DAT_COD_EMP, "" + rstLoc.getInt("co_emp"));
                  arlRegNotCre.add(INT_ARL_DAT_COD_LOC, "" + rstLoc.getInt("co_loc"));
                  arlRegNotCre.add(INT_ARL_DAT_COD_TIP_DOC, "" + rstLoc.getInt("co_tipdoc"));
                  arlRegNotCre.add(INT_ARL_DAT_COD_DOC, "" + rstLoc.getInt("co_doc"));
                  arlDatNotCre.add(arlRegNotCre);
               }
            }
            
            //DBN_Prueba inicio
            /*intCodEmp = 1;
            intCodLoc = 2;
            intCodTipDoc = 229;
            intCodDoc = 4;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            blnRes = objCpbEle.getNumAutorizacionNotCre(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatNotCre.size(); i++)
            {  intCodEmp = objUti.getIntValueAt(arlDatNotCre, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatNotCre, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatNotCre, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatNotCre, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
               arlResWS = new ArrayList();
               
               strSQL = "select count(1) from tbm_emp where 1 = 0";
               rstLoc = stmLoc.executeQuery(strSQL);
               
               if (rstLoc.next())
               {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
                  //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
                  //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
                  if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
                  {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                     strAux =  "Servicio 11: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                     strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                     updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
                  else if (strEjeSer.equals("S"))
                  {  //strEjeSer es un parametro leido del objeto ZafParSis.
                     //S = indica que si se debe ejecutar el servicio.
                     blnRes = objCpbEle.getNumAutorizacionNotCre(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }
                  else
                  {  //N = indica que no se debe ejecutar el servicio.
                     strAux = "No se esta ejecutando el proceso del Servicio 11. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                     updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
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
   } //Funcion ejecutarProcesoNotCre()
   
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
            //Este query no va a traer guias para generar el XML, ya que la generacion del XML lo realizara el Servicio 14. Por tanto este query solo traera 
            //las guias que ya tengan generado el XML para que sean autorizadas por el SRI (indicado por la condicion tbm_cabguirem.st_autFacEle = 'S').
            strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.fe_ing ";
            strSQL += "FROM   tbm_cabguirem as a1 ";
            strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc ";
            strSQL += "WHERE      a1.st_reg = 'A' and a1.st_autFacEle = 'S' ";
            strSQL += "           and a2.st_utiFacEle = 'S' ";
            strSQL += "           and a2.co_cat = 24 "; //24: Guia de remision
            strSQL += "           and a1.ne_numdoc <> 0 ";
            strSQL += "           and a1.fe_ing >= '" + strFecIngGuiRem_CabGuiRem + "'";
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
            /*intCodEmp = 1;
            intCodLoc = 4;
            intCodTipDoc = 231;
            intCodDoc = 28780;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            blnRes = objCpbEle.getNumAutorizacionGuiRem(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatGuiRem.size(); i++)
            {  intCodEmp = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatGuiRem, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
               arlResWS = new ArrayList();
               
               strSQL = "select count(1) from tbm_emp where 1 = 0";
               rstLoc = stmLoc.executeQuery(strSQL);
               
               if (rstLoc.next())
               {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
                  //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
                  //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
                  if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
                  {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                     strAux =  "Servicio 11: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                     strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                     updateDesFacEle("tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
                  else if (strEjeSer.equals("S"))
                  {  //strEjeSer es un parametro leido del objeto ZafParSis.
                     //S = indica que si se debe ejecutar el servicio.
                     blnRes = objCpbEle.getNumAutorizacionGuiRem(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }
                  else
                  {  //N = indica que no se debe ejecutar el servicio.
                     strAux = "No se esta ejecutando el proceso del Servicio 11. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                     updateDesFacEle("tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
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
   
   /**
    * Esta funcion se encarga de leer la tabla donde se guardan las liquidaciones de compras para firmar, enviar y autorizar cada retencion XML 
    * usando los Servicios Web del SRI.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   private boolean ejecutarProcesoLiqCom()
   {
      boolean blnRes = true, blnDirMacSrvPrd_Found;
      int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
      String strAux, strEjeProTipAmbSriPru;
      ArrayList arlResWS, arlRegCpbRet, arlDatCpbRet;
      Statement stmLoc;
      ResultSet rstLoc;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            arlDatCpbRet = new ArrayList();
            strSQL =  "SELECT     a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.st_autFacEle, a1.fe_ing, a1.ne_numdoc, a1.fe_doc, a1.tx_nomcli, a1.tx_ruc as tx_rucCli, a3.tx_tipide as tx_tipIdePrv, a1.nd_poriva, abs(a1.nd_sub) as nd_sub, abs(a1.nd_valiva) as nd_valiva, abs(a1.nd_tot) as nd_tot ";
            strSQL += "FROM       tbm_cabmovinv as a1 ";
            strSQL += "INNER JOIN tbm_cabtipdoc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc ";
            strSQL += "LEFT  JOIN tbm_cli as a3 on a3.co_emp = a1.co_emp and a3.co_cli = a1.co_cli ";
            strSQL += "WHERE      a1.st_reg = 'A' and (a1.st_autFacEle is null or a1.st_autFacEle = 'S') "; //DBN_real
            //strSQL += "WHERE      a1.st_reg = 'A' and a1.fe_doc between '2019-01-01' and CURRENT_DATE "; //DBN_prueba
            strSQL += "and a2.st_utiFacEle = 'S' ";
            strSQL += "and a2.co_cat = 15 "; //15: Liquidaciones de compras
            strSQL += "ORDER BY   a1.fe_ing";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next())
            {  arlRegCpbRet = new ArrayList();
               arlRegCpbRet.add(INT_ARL_DAT_COD_EMP, "" + rstLoc.getInt("co_emp"));
               arlRegCpbRet.add(INT_ARL_DAT_COD_LOC, "" + rstLoc.getInt("co_loc"));
               arlRegCpbRet.add(INT_ARL_DAT_COD_TIP_DOC, "" + rstLoc.getInt("co_tipdoc"));
               arlRegCpbRet.add(INT_ARL_DAT_COD_DOC, "" + rstLoc.getInt("co_doc"));
               arlDatCpbRet.add(arlRegCpbRet);
            }
            
            //DBN_Prueba inicio
            /*intCodEmp = 4;
            intCodLoc = 3;
            intCodTipDoc = 230;
            intCodDoc = 4785;
            getNombre_y_clave_archivo_p12(intCodEmp);
            ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12);
            arlResWS = new ArrayList();
            blnRes = objCpbEle.getNumAutorizacionCpbRet(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);*/
            //DBN_Prueba fin
            
            for (i = 0; i < arlDatCpbRet.size(); i++)
            {  intCodEmp = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_EMP);
               intCodLoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_LOC);
               intCodTipDoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_TIP_DOC);
               intCodDoc = objUti.getIntValueAt(arlDatCpbRet, i, INT_ARL_DAT_COD_DOC);
               getNombre_y_clave_archivo_p12(intCodEmp);
               ZafCpbEle objCpbEle = new ZafCpbEle(objParSis, strNomFil_p12, strClvFil_p12); //DBN_Prueba
               strEjeProTipAmbSriPru = objCpbEle.strEjeProTipAmbSriPru;
               blnDirMacSrvPrd_Found = objCpbEle.blnDirMacSrvPrd_Found;
               arlResWS = new ArrayList();
               
               strSQL = "select count(1) from tbm_emp where 1 = 0";
               rstLoc = stmLoc.executeQuery(strSQL);
               
               if (rstLoc.next())
               {  //Se ejecuta este query para evitar que se invoquen los Servicios Web del SRI si no hay conexion con la base de datos. Esto suele pasar
                  //al realizarse un restart de la base de datos, pues cuando sucede esto, se ejecutan los Servicios Web del SRI, se autorizan los
                  //documentos electronicos, pero los datos de la autorizacion no se actualizan en las tablas respectivas.
                  if (blnDirMacSrvPrd_Found == false && strEjeProTipAmbSriPru.equals("N"))
                  {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI.
                     strAux =  "Servicio 11: Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion. ";
                     strAux += "El servicio fue ejecutado en el equipo " + objParSis.getNombreComputadoraConDirIP();
                     updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
                  else if (strEjeSer.equals("S"))
                  {  //strEjeSer es un parametro leido del objeto ZafParSis.
                     //S = indica que si se debe ejecutar el servicio.
                     blnRes = objCpbEle.getNumAutorizacionLiqCom(con, strNomEmp, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
                  }
                  else
                  {  //N = indica que no se debe ejecutar el servicio.
                     strAux = "No se esta ejecutando el proceso del Servicio 11. Verifique el parametro del archivo de configuracion usado por el objeto ZafParSis.";
                     updateDesFacEle("tbm_cabmovinv", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strAux);
                  }
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
   } //Funcion ejecutarProcesoLiqCom()
   
   private boolean existeDiferencias_Sub_Iva_Fac(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux;
      BigDecimal bdeAux1, bdeAux2, bdeValCabMovInv, bdeValJota;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            strSQL =  "SELECT abs(b1.nd_sub - b1.totSubDetJota) as dif_sub, abs(b1.nd_valIva - b1.totIvaDetJota) as dif_iva, b1.* " + "\n";
            strSQL += "FROM ( " + "\n";
            strSQL += "       SELECT z1.co_emp, z1.co_loc, z1.co_tipDoc, z1.co_doc, a1.fe_doc, a1.nd_porIva, a1.nd_sub, a1.nd_valIva, a1.nd_tot, z1.nd_totDet, z1.totSubDetJota, z1.totIvaDetJota, " + "\n";
            strSQL += "          CASE WHEN a1.nd_sub <> z1.totSubDetJota THEN 'NO' ELSE 'OK' END AS mira_sub, " + "\n";
            strSQL += "          CASE WHEN a1.nd_valIva <> z1.totIvaDetJota THEN 'NO' ELSE 'OK' END AS mira_iva " + "\n";
            strSQL += "       FROM ( " + "\n";
            strSQL += "              SELECT x1.co_emp, x1.co_loc, x1.co_tipDoc, x1.co_doc, x1.nd_porIva, SUM(x1.nd_tot) as nd_totDet, SUM(x1.subDet) as totSubDetJota, round(sum(basImpIvaDet) * x1.nd_porIva / 100, 2) as totIvaDetJota " + "\n";
            strSQL += "              FROM ( " + "\n";
            strSQL += "                     SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_can, a2.nd_preUni, a2.nd_porDes, a2.st_ivaCom, a1.nd_porIva, a2.nd_tot, " + "\n";
            strSQL += "                        round( (a2.nd_can * a2.nd_preUni) - ((a2.nd_can * a2.nd_preUni) * (nd_porDes / 100)), 2) as subDet, " + "\n";
            strSQL += "                        ( case when a2.st_ivaCom = 'N' then 0 " + "\n";
            strSQL += "                               when a2.st_ivaCom = 'S' then round( (a2.nd_can * a2.nd_preUni) - ((a2.nd_can * a2.nd_preUni) * (nd_porDes / 100)), 2) " + "\n";
            strSQL += "                               else 0 " + "\n";
            strSQL += "                        end ) as basImpIvaDet " + "\n";
            strSQL += "                     FROM tbm_cabMovInv as a1 " + "\n";
            strSQL += "                     INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipDoc AND a1.co_doc = a2.co_doc) " + "\n";
            strSQL += "                     INNER JOIN tbm_inv as a3 on (a2.co_emp = a3.co_emp AND a2.co_itm = a3.co_itm) " + "\n";
            strSQL += "                     WHERE a1.co_emp = " + intCodEmp + "\n";
            strSQL += "                        AND a1.co_loc = " + intCodLoc + "\n";
            strSQL += "                        AND a1.co_tipDoc = " + intCodTipDoc + "\n";
            strSQL += "                        AND a1.co_doc = " + intCodDoc + "\n";
            strSQL += "                     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_tot, a2.nd_preUni ,a2.nd_porDes, a2.st_ivaCom, a2.nd_can, a3.st_ivaVen, a1.nd_porIva " + "\n";
            strSQL += "                   ) as x1 " + "\n";
            strSQL += "              GROUP BY x1.co_emp, x1.co_loc, x1.co_tipDoc, x1.co_doc, x1.nd_porIva " + "\n";
            strSQL += "            ) as z1 " + "\n";
            strSQL += "       INNER JOIN tbm_cabMovInv as a1 ON (z1.co_emp = a1.co_emp AND z1.co_loc = a1.co_loc AND z1.co_tipDoc = a1.co_tipDoc AND z1.co_doc = a1.co_doc) " + "\n";
            strSQL += "       ORDER BY mira_iva DESC " + "\n";
            strSQL += "     ) as b1 " + "\n";
            strSQL += "WHERE (b1.mira_sub = 'NO' or b1.mira_iva = 'NO')";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  strAux = "";
               bdeAux1 = new BigDecimal(rstLoc.getString("nd_sub"));
               bdeAux1 = bdeAux1.abs();
               bdeAux2 = new BigDecimal(rstLoc.getString("totSubDetJota"));
               bdeAux2 = bdeAux2.abs();
               bdeAux1 = bdeAux1.subtract(bdeAux2);
               bdeAux1 = bdeAux1.abs();
               
               if (rstLoc.getString("mira_sub").equals("NO") && bdeAux1.compareTo(new BigDecimal(strNumDecTolDifCalIvaSub)) > 0)
               {  //Hay una diferencia en el Subtotal del campo de la tabla cabecera con el calculo de los valores de la tabla de detalle.
                  bdeAux1 = new BigDecimal(rstLoc.getString("dif_sub"));
                  bdeAux1 = objUti.redondearBigDecimal(bdeAux1, objParSis.getDecimalesMostrar());
                  bdeValCabMovInv = new BigDecimal(rstLoc.getString("nd_sub"));
                  bdeValCabMovInv = objUti.redondearBigDecimal(bdeValCabMovInv.abs(), objParSis.getDecimalesMostrar());
                  bdeValJota = new BigDecimal(rstLoc.getString("totSubDetJota"));
                  bdeValJota = objUti.redondearBigDecimal(bdeValJota.abs(), objParSis.getDecimalesMostrar());
                  strAux = "Mensaje devuelto por el Servicio de Zafiro. ";
                  strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strAux += "Error de diferencia en Subtotal ocurrido en el metodo existeDiferencias_Sub_Iva_Fac. El campo tbm_cabMovInv.nd_sub es igual a ";
                  strAux += bdeValCabMovInv + ". El calculo de subtotal basado en tbm_detMovInv es igual a " + bdeValJota + ". La diferencia es " + bdeAux1 + ". ";
                  strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalIvaSub + ".";
               }
            
               bdeAux1 = new BigDecimal(rstLoc.getString("nd_valIva"));
               bdeAux1 = bdeAux1.abs();
               bdeAux2 = new BigDecimal(rstLoc.getString("totIvaDetJota"));
               bdeAux2 = bdeAux2.abs();
               bdeAux1 = bdeAux1.subtract(bdeAux2);
               bdeAux1 = bdeAux1.abs();
               
               if (rstLoc.getString("mira_iva").equals("NO") && bdeAux1.compareTo(new BigDecimal(strNumDecTolDifCalIvaSub)) > 0)
               {  //Hay una diferencia en el IVA del campo de la tabla cabecera con el calculo de los valores de la tabla de detalle.
                  bdeAux1 = new BigDecimal(rstLoc.getString("dif_iva"));
                  bdeAux1 = objUti.redondearBigDecimal(bdeAux1, objParSis.getDecimalesMostrar());
                  bdeValCabMovInv = new BigDecimal(rstLoc.getString("nd_valIva"));
                  bdeValCabMovInv = objUti.redondearBigDecimal(bdeValCabMovInv.abs(), objParSis.getDecimalesMostrar());
                  bdeValJota = new BigDecimal(rstLoc.getString("totIvaDetJota"));
                  bdeValJota = objUti.redondearBigDecimal(bdeValJota.abs(), objParSis.getDecimalesMostrar());
                  
                  if (strAux.equals(""))
                  {  strAux = "Mensaje devuelto por el Servicio de Zafiro.";
                  }
                  
                  strAux += " PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strAux += "Error de diferencia en IVA ocurrido en el metodo existeDiferencias_Sub_Iva_Fac. El campo tbm_cabMovInv.nd_valIva es igual a ";
                  strAux += bdeValCabMovInv + ". El calculo de IVA basado en tbm_detMovInv es igual a " + bdeValJota + ". La diferencia es " + bdeAux1 + ". ";
                  strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalIvaSub + ".";
               }
               
               if (!strAux.equals(""))
               {  strSQL =  "UPDATE " + strNomTbl + " ";
                  strSQL += "SET    st_autFacEle = 'X', ";
                  strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
                  strSQL += "WHERE  co_emp = " + intCodEmp;
                  strSQL += "          and co_loc = " + intCodLoc;
                  strSQL += "          and co_tipdoc = " + intCodTipDoc;
                  strSQL += "          and co_doc = " + intCodDoc;
                  stmLoc.executeUpdate(strSQL);
                  strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
                  blnRes = true;
               }
            } //if (rstLoc.next())
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_Sub_Iva_Fac()
   
   private boolean existeDiferencias_Iva_ComSol_Fac(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strFecFac, BigDecimal bdePorComSol_CfgImpLoc, String strNomTbl)
   {
      boolean blnRes = false, blnExiDif;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux, strFec;
      Date datFecFac;
      BigDecimal bdeAux, bdePorIvaVta_CnfImpLoc, bdeValIva_CabMovInv, bdeValComSol_CabMovInv, bdeValIva_Cal, bdeValComSol_Cal, bdeSubIvaGra;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            bdePorIvaVta_CnfImpLoc = new BigDecimal("0.00");
            bdeValIva_CabMovInv = new BigDecimal("0.00");
            bdeValComSol_CabMovInv = new BigDecimal("0.00");
            bdeValIva_Cal = new BigDecimal("0.00");
            bdeValComSol_Cal = new BigDecimal("0.00");
            datFecFac = objUti.parseDate(strFecFac, "yyyy-MM-dd");
            
            //bdePorIvaVta_CnfImpLoc = objParSis.getPorcentajeIvaVentas(intCodEmp, intCodLoc, datFecFac);
            strFec = objUti.formatearFecha(datFecFac, objParSis.getFormatoFechaBaseDatos());
            strSQL = "";
            strSQL += "SELECT a1.nd_porImp";
            strSQL += " FROM tbm_cfgImpLoc AS a1";
            strSQL += " WHERE a1.co_emp=" + intCodEmp + " AND a1.co_loc=" + intCodLoc + " AND a1.co_imp=2"; //co_imp 2 = IVA Ventas
            strSQL += " AND (CASE WHEN a1.fe_vigHas IS NULL THEN '" + strFec + "'>=fe_vigDes ELSE '" + strFec + "'>=fe_vigDes AND '" + strFec + "'<=fe_vigHas END)";
            strSQL += " ORDER BY a1.co_imp";
            rstLoc = stmLoc.executeQuery(strSQL);
            if (rstLoc.next())
            {
                bdePorIvaVta_CnfImpLoc = rstLoc.getBigDecimal("nd_porImp");
            }
            
            strSQL =  "SELECT nd_porIva, abs(nd_sub) as nd_sub, abs(nd_valIva) as nd_valIva, nd_tot, nd_subIvaCer, abs(nd_subIvaGra) as nd_subIvaGra, nd_porComSol, abs(nd_valComSol) as nd_valComSol " + "\n";
            strSQL += "FROM   tbm_cabMovInv " + "\n";
            strSQL += "WHERE  co_emp = " + intCodEmp + "\n";
            strSQL += "       and co_loc = " + intCodLoc + "\n";
            strSQL += "       and co_tipDoc = " + intCodTipDoc + "\n";
            strSQL += "       and co_doc = " + intCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  bdeValIva_CabMovInv = rstLoc.getBigDecimal("nd_valIva");
               bdeValIva_CabMovInv = objUti.redondearBigDecimal(bdeValIva_CabMovInv, objParSis.getDecimalesMostrar());
               
               strAux = rstLoc.getString("nd_valComSol") == null? "" :rstLoc.getString("nd_valComSol");
                     
               if (strAux.equals(""))
               {  bdeValComSol_CabMovInv = new BigDecimal("0.00");
               }
               else
               {  bdeValComSol_CabMovInv = new BigDecimal(strAux);
                  bdeValComSol_CabMovInv = objUti.redondearBigDecimal(bdeValComSol_CabMovInv, objParSis.getDecimalesMostrar());
               }
               
               strAux = rstLoc.getString("nd_subIvaGra") == null? "" :rstLoc.getString("nd_subIvaGra");
                     
               if (strAux.equals(""))
               {  bdeSubIvaGra = new BigDecimal("0.00");
               }
               else
               {  bdeSubIvaGra = new BigDecimal(strAux);
                  bdeSubIvaGra = objUti.redondearBigDecimal(bdeSubIvaGra, objParSis.getDecimalesMostrar());
               }
               //Basado con ciertos parametros de la tabla tbm_cfgImpLoc, se va a calcular el Val.Iva y Val.Compensacion_Solidaria.
               //Calculo de Val.Iva.
               bdeAux = bdePorIvaVta_CnfImpLoc.divide(new BigDecimal(100));
               bdeValIva_Cal = bdeSubIvaGra.multiply(bdeAux);
               bdeValIva_Cal = objUti.redondearBigDecimal(bdeValIva_Cal, objParSis.getDecimalesMostrar());
               //Calculo de Val.Compensacion_Solidaria.
               bdeAux = bdePorComSol_CfgImpLoc.divide(new BigDecimal(100));
               bdeValComSol_Cal = rstLoc.getBigDecimal("nd_sub").multiply(bdeAux);
               bdeValComSol_Cal = objUti.redondearBigDecimal(bdeValComSol_Cal, objParSis.getDecimalesMostrar());
            } //if (rstLoc.next())
            
            blnExiDif = false;
            strAux = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabMovInv");
            
            if (!bdeValIva_CabMovInv.equals(bdeValIva_Cal))
            {  //Hay una diferencia en el Val.Iva del campo de la tabla tbm_cabMovInv con el valor calculado basado en el parametro de la tabla tbm_cfgImpLoc.
               blnExiDif = true;
               bdeAux = bdeValIva_CabMovInv.subtract(bdeValIva_Cal);
               bdeAux = bdeAux.abs();
               
               if (!strAux.equals(""))
               {  //Si strAux <> "", significa que ya hay un valor guardado en el campo tx_desErrFacEle.  Por tanto, ese valor guardado no debe perderse
                  //en el momento de guardar la nueva descripcion que hay que agregar en dicho campo.
                  strAux += ". ";
               }
               
               strAux += "Mensaje devuelto por el Servicio de Zafiro. ";
               strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
               strAux += "Error de diferencia en IVA ocurrido en el metodo existeDiferencias_Iva_ComSol_Fac. El campo tbm_cabMovInv.nd_valIva es igual a ";
               strAux += bdeValIva_CabMovInv + ". El calculo de IVA basado en tbm_cfgImpLoc y nd_subIvaGra es igual a " + bdeValIva_Cal + ". La diferencia es " + bdeAux + ".";
            }
            
            if (!bdeValComSol_CabMovInv.equals(bdeValComSol_Cal))
            {  //Hay una diferencia en el Val.Com.Sol. del campo de la tabla tbm_cabMovInv con el valor calculado basado en el parametro de la tabla tbm_cfgImpLoc.
               blnExiDif = true;
               bdeAux = bdeValComSol_CabMovInv.subtract(bdeValComSol_Cal);
               bdeAux = bdeAux.abs();
               
               if (strAux.equals(""))
               {  strAux = "Mensaje devuelto por el Servicio de Zafiro.";
               }
               
               strAux += " PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
               strAux += "Error de diferencia en Valor de Compensacion Solidaria ocurrido en el metodo existeDiferencias_Iva_ComSol_Fac. El campo tbm_cabMovInv.nd_valComSol es igual a ";
               strAux += bdeValComSol_CabMovInv + ". El calculo de Valor de Compensacion Solidaria basado en tbm_cfgImpLoc y nd_sub es igual a " + bdeValComSol_Cal + ". La diferencia es " + bdeAux + ".";
            }
            
            if (blnExiDif == true)
            {  strSQL =  "UPDATE " + strNomTbl + " ";
               strSQL += "SET    st_autFacEle = 'X', ";
               strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
               strSQL += "WHERE  co_emp = " + intCodEmp;
               strSQL += "          and co_loc = " + intCodLoc;
               strSQL += "          and co_tipdoc = " + intCodTipDoc;
               strSQL += "          and co_doc = " + intCodDoc;
               stmLoc.executeUpdate(strSQL);
               strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
               blnRes = true;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_Iva_ComSol_Fac()
   
   private boolean existeIncoherencia_IvaFac_y_Cliente(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false, blnExiIncoh;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux, strMsj, strIvaVen_Cli;
      BigDecimal bdeValIva_CabMovInv;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            bdeValIva_CabMovInv = new BigDecimal("0.00");
            //Query para verificar si una factura debe tener IVA de acuerdo a lo indicado en el campo tbm_cli.st_ivaVen. Esto solo se va a verificar
            //si tbm_cli.st_ivaVen = 'N', es decir, para los clientes a los que no se les debe facturar con IVA.
            strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.nd_porIva, a1.nd_valIva, a1.nd_sub, a1.nd_subIvaGra, a1.co_cli, a1.tx_nomcli, a2.st_ivaVen, " + "\n";
            strSQL += " ( case when a2.st_ivaVen = 'S' and abs(nd_valIva) > 0 then 'OK'" + "\n";
            strSQL += "        when a2.st_ivaVen = 'N' and abs(nd_valIva) = 0 then 'OK'" + "\n";
            strSQL += "        else 'NO'" + "\n";
            strSQL += " end ) as tx_res" + "\n";
            strSQL += "FROM tbm_cabmovinv as a1" + "\n";
            strSQL += "INNER JOIN tbm_cli as a2 on a1.co_emp = a2.co_emp and a1.co_cli = a2.co_cli" + "\n";
            strSQL += "WHERE a2.st_ivaVen = 'N' and a1.co_emp = " + intCodEmp;
            strSQL += " and a1.co_loc = " + intCodLoc;
            strSQL += " and a1.co_tipDoc = " + intCodTipDoc;
            strSQL += " and a1.co_doc = " + intCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            
            blnExiIncoh = false;
            strMsj = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabMovInv");
            
            if (rstLoc.next())
            {  bdeValIva_CabMovInv = rstLoc.getBigDecimal("nd_valIva").abs();
               bdeValIva_CabMovInv = objUti.redondearBigDecimal(bdeValIva_CabMovInv, objParSis.getDecimalesMostrar());
               strIvaVen_Cli = rstLoc.getString("st_ivaVen");
               strAux = rstLoc.getString("tx_res");
               
               if (strAux.equals("NO"))
               {  //Hay una incoherencia con el IVA de la factura de acuerdo a lo indicado en el campo tbm_cli.st_ivaVen.
                  blnExiIncoh = true;

                  if (!strMsj.equals(""))
                  {  //Si strMsj <> "", significa que ya hay un valor guardado en el campo tx_desErrFacEle.  Por tanto, ese valor guardado no debe 
                     //perderse en el momento de guardar la nueva descripcion que hay que agregar en dicho campo.
                     strMsj += ". ";
                  }

                  strMsj += "Mensaje devuelto por el Servicio de Zafiro. ";
                  strMsj += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strMsj += "Se encontro una incoherencia con el IVA de la factura de acuerdo a lo indicado en el campo tbm_cli.st_ivaVen, ocurrido en ";
                  strMsj += "el metodo existeIncoherencia_IvaFac_y_Cliente. El campo tbm_cabMovInv.nd_valIva es igual a " + bdeValIva_CabMovInv;
                  strMsj += ". El campo tbm_cli.st_ivaVen es igual a " + strIvaVen_Cli + ".";
               }
            }
            
            if (blnExiIncoh == true)
            {  strSQL =  "UPDATE " + strNomTbl + " ";
               strSQL += "SET st_autFacEle = 'X',";
               strSQL += " tx_desErrFacEle = '" + strMsj + "' ";
               strSQL += "WHERE co_emp = " + intCodEmp;
               strSQL += " and co_loc = " + intCodLoc;
               strSQL += " and co_tipdoc = " + intCodTipDoc;
               strSQL += " and co_doc = " + intCodDoc;
               stmLoc.executeUpdate(strSQL);
               strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strMsj);
               blnRes = true;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeIncoherencia_IvaFac_y_Cliente()
   
   private boolean existeIncoherencia_ItmDetFac_y_MaeItm(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false, blnMosInfPK, blnExiIncoh;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux, strMsj, strCodAlt, strNomItm, strIvaCom_DetMovInv, strIvaVen_MaeItm;
      int i;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            i = 0;
            blnMosInfPK = true;
            //Query para verificar si los items que estan en tbm_detmovinv (campo tbm_detmovinv.st_ivaCom) graban o no IVA, de acuerdo a lo indicado por 
            //el campo tbm_inv.st_ivaVen.
            strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.st_ivaCom, a1.nd_porIva, a1.nd_basImpIvaGra, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.st_ivaVen," + "\n";
            strSQL += " ( case when a1.st_ivaCom = a2.st_ivaVen then 'OK'" + "\n";
            strSQL += "        else 'NO'" + "\n";
            strSQL += " end ) as tx_res" + "\n";
            strSQL += "FROM tbm_detmovinv as a1" + "\n";
            strSQL += "INNER JOIN tbm_inv as a2 on a1.co_emp = a2.co_emp and a1.co_itm = a2.co_itm" + "\n";
            strSQL += "WHERE a1.co_emp = " + intCodEmp;
            strSQL += " and a1.co_loc = " + intCodLoc;
            strSQL += " and a1.co_tipDoc = " + intCodTipDoc;
            strSQL += " and a1.co_doc = " + intCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            
            blnExiIncoh = false;
            strMsj = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabMovInv");
            
            while (rstLoc.next())
            {  strIvaVen_MaeItm = rstLoc.getString("st_ivaVen"); //tbm_inv.st_ivaVen
               strAux = rstLoc.getString("tx_res");
               
               if (strAux.equals("NO"))
               {  //Hay una incoherencia sobre si los items que estan en tbm_detmovinv (campo tbm_detmovinv.st_ivaCom) graban o no IVA, de acuerdo a lo 
                  //indicado por el campo tbm_inv.st_ivaVen.
                  i++;
                  blnExiIncoh = true;

                  if (!strMsj.equals(""))
                  {  //Si strMsj <> "", significa que ya hay un valor guardado en el campo tx_desErrFacEle.  Por tanto, ese valor guardado no debe 
                     //perderse en el momento de guardar la nueva descripcion que hay que agregar en dicho campo.
                     strMsj += ". ";
                  }
                  
                  if (blnMosInfPK == true)
                  {  strMsj += "Mensaje devuelto por el Servicio de Zafiro. ";
                     strMsj += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                     strMsj += "Se encontro una incoherencia sobre si los items que estan en tbm_detmovinv (campo tbm_detmovinv.st_ivaCom) graban o no ";
                     strMsj += "IVA, de acuerdo a lo indicado por el campo tbm_inv.st_ivaVen, ocurrido en el metodo ";
                     strMsj += "existeIncoherencia_ItmDetFac_y_MaeItm. El campo tbm_inv.st_ivaVen es igual a " + strIvaVen_MaeItm + ".";
                     blnMosInfPK = false; //Al poner false, esto hace que en el cuerpo del mensaje de error solo aparezca una sola vez la PK.
                  }
                  
                  strCodAlt = rstLoc.getString("tx_codAlt");
                  strNomItm = rstLoc.getString("tx_nomItm");
                  strIvaCom_DetMovInv = rstLoc.getString("st_ivaCom");
                  strMsj += " Caso #" + i + ". ";
                  strMsj += "Cod. alt. = " + strCodAlt + ". ";
                  strMsj += "Nom. item = " + strNomItm + ". ";
                  strMsj += "st_ivaCom = " + strIvaCom_DetMovInv + ".";
               }
            } //while (rstLoc.next())
            
            if (blnExiIncoh == true)
            {  strSQL =  "UPDATE " + strNomTbl + " ";
               strSQL += "SET st_autFacEle = 'X',";
               strSQL += " tx_desErrFacEle = '" + strMsj + "' ";
               strSQL += "WHERE co_emp = " + intCodEmp;
               strSQL += " and co_loc = " + intCodLoc;
               strSQL += " and co_tipdoc = " + intCodTipDoc;
               strSQL += " and co_doc = " + intCodDoc;
               stmLoc.executeUpdate(strSQL);
               strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strMsj);
               blnRes = true;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeIncoherencia_ItmDetFac_y_MaeItm()
   
   private boolean existeDiferencias_Iva_ComSol_DevVta(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false, blnExiDif;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux;
      BigDecimal bdeAux, bdeValIva_CabMovInv, bdeValComSol_CabMovInv, bdeValIva_Cal, bdeValComSol_Cal, bdeSubIvaGraDevVta, bdePorComSolFac;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            bdeValIva_CabMovInv = new BigDecimal("0.00");
            bdeValComSol_CabMovInv = new BigDecimal("0.00");
            bdeValIva_Cal = new BigDecimal("0.00");
            bdeValComSol_Cal = new BigDecimal("0.00");
            strSQL =  "SELECT fac.co_emp as co_empFac, fac.co_loc as co_locFac, fac.co_tipdoc as co_tipDocFac, fac.co_doc as co_docFac, " + "\n";
            strSQL += "   fac.ne_numdoc as ne_numDocFac, fac.fe_doc as fe_emiFac, fac.nd_porIva as nd_porIvaFac, fac.nd_porComSol as nd_porComSolFac, " + "\n";
            strSQL += "   nc.fe_doc as fe_emiDevVta, abs(nc.nd_sub) as nd_subDevVta, abs(nc.nd_valIva) as nd_valIvaDevVta, nc.nd_tot as nd_totDevVta, " + "\n";
            strSQL += "   nc.nd_subIvaCer as nd_subIvaCerDevVta, abs(nc.nd_subIvaGra) as nd_subIvaGraDevVta, nc.nd_porComSol as nd_porComSolDevVta, " + "\n";
            strSQL += "   abs(nc.nd_valComSol) as nd_valComSolDevVta, " + "\n";
            strSQL += "   round(nc.nd_subIvaGra * fac.nd_porIva / 100, 2) as valIvaCal, round(nc.nd_sub * fac.nd_porComSol / 100, 2) as valComSolCal " + "\n";
            strSQL += "FROM       tbm_cabMovInv as nc " + "\n"; //nc = Nota de credito (Dev.Vta.)
            strSQL += "inner join tbm_cabSolDevVen as s on s.co_emp = nc.co_emp and s.co_loc = nc.co_locrelsoldevven and s.co_tipdoc = nc.co_tipdocrelsoldevven and s.co_doc = nc.co_docrelsoldevven " + "\n";
            strSQL += "inner join tbm_cabMovInv as fac on s.co_emp = fac.co_emp and s.co_locrel = fac.co_loc and s.co_tipdocrel = fac.co_tipdoc and s.co_docrel = fac.co_doc " + "\n";
            strSQL += "WHERE nc.co_emp = " + intCodEmp + "\n";
            strSQL += "      and nc.co_loc = " + intCodLoc + "\n";
            strSQL += "      and nc.co_tipDoc = " + intCodTipDoc + "\n";
            strSQL += "      and nc.co_doc = " + intCodDoc;
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  bdeValIva_CabMovInv = rstLoc.getBigDecimal("nd_valIvaDevVta");
               bdeValIva_CabMovInv = objUti.redondearBigDecimal(bdeValIva_CabMovInv, objParSis.getDecimalesMostrar());
               
               strAux = rstLoc.getString("nd_valComSolDevVta") == null? "" :rstLoc.getString("nd_valComSolDevVta");
                     
               if (strAux.equals(""))
               {  bdeValComSol_CabMovInv = new BigDecimal("0.00");
               }
               else
               {  bdeValComSol_CabMovInv = new BigDecimal(strAux);
                  bdeValComSol_CabMovInv = objUti.redondearBigDecimal(bdeValComSol_CabMovInv, objParSis.getDecimalesMostrar());
               }
               
               //Basado en el campo nd_porIva de la factura (relacionada con la Dev.Vta), se va a calcular el Val.Iva y Val.Compensacion_Solidaria.
               //Calculo de Val.Iva.
               strAux = rstLoc.getString("nd_subIvaGraDevVta") == null? "" :rstLoc.getString("nd_subIvaGraDevVta");
                     
               if (strAux.equals(""))
               {  bdeSubIvaGraDevVta = new BigDecimal("0.00");
               }
               else
               {  bdeSubIvaGraDevVta = new BigDecimal(strAux);
                  bdeSubIvaGraDevVta = objUti.redondearBigDecimal(bdeSubIvaGraDevVta, objParSis.getDecimalesMostrar());
               }
               
               bdeAux = rstLoc.getBigDecimal("nd_porIvaFac").divide(new BigDecimal(100));
               bdeValIva_Cal = bdeSubIvaGraDevVta.multiply(bdeAux);
               bdeValIva_Cal = objUti.redondearBigDecimal(bdeValIva_Cal, objParSis.getDecimalesMostrar());
               
               //Calculo de Val.Compensacion_Solidaria.
               strAux = rstLoc.getString("nd_porComSolFac") == null? "" :rstLoc.getString("nd_porComSolFac");
                     
               if (strAux.equals(""))
               {  bdePorComSolFac = new BigDecimal("0.00");
               }
               else
               {  bdePorComSolFac = new BigDecimal(strAux);
                  bdePorComSolFac = objUti.redondearBigDecimal(bdePorComSolFac, objParSis.getDecimalesMostrar());
               }
               
               bdeAux = bdePorComSolFac.divide(new BigDecimal(100));
               bdeValComSol_Cal = rstLoc.getBigDecimal("nd_subDevVta").multiply(bdeAux);
               bdeValComSol_Cal = objUti.redondearBigDecimal(bdeValComSol_Cal, objParSis.getDecimalesMostrar());
            } //if (rstLoc.next())
            
            blnExiDif = false;
            strAux = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, "tbm_cabMovInv");
            
            if (!bdeValIva_CabMovInv.equals(bdeValIva_Cal))
            {  //Hay una diferencia en el Val.Iva del campo de la tabla tbm_cabMovInv con el valor calculado basado en el campo nd_porIva de la factura 
               //(relacionada con la Dev.Vta).
               blnExiDif = true;
               bdeAux = bdeValIva_CabMovInv.subtract(bdeValIva_Cal);
               bdeAux = bdeAux.abs();
               
               if (!strAux.equals(""))
               {  //Si strAux <> "", significa que ya hay un valor guardado en el campo tx_desErrFacEle.  Por tanto, ese valor guardado no debe perderse
                  //en el momento de guardar la nueva descripcion que hay que agregar en dicho campo.
                  strAux += ". ";
               }
               
               strAux += "Mensaje devuelto por el Servicio de Zafiro. ";
               strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
               strAux += "Error de diferencia en IVA ocurrido en el metodo existeDiferencias_Iva_ComSol_DevVta. El campo tbm_cabMovInv.nd_valIva es igual a ";
               strAux += bdeValIva_CabMovInv + ". El calculo de IVA basado en nd_porIva de la Factura y nd_subIvaGra es igual a " + bdeValIva_Cal + ". La diferencia es " + bdeAux + ".";
            }
            
            if (!bdeValComSol_CabMovInv.equals(bdeValComSol_Cal))
            {  //Hay una diferencia en el Val.Com.Sol. del campo de la tabla tbm_cabMovInv con el valor calculado basado en el campo nd_porIva de la factura 
               //(relacionada con la Dev.Vta).
               blnExiDif = true;
               bdeAux = bdeValComSol_CabMovInv.subtract(bdeValComSol_Cal);
               bdeAux = bdeAux.abs();
               
               if (strAux.equals(""))
               {  strAux = "Mensaje devuelto por el Servicio de Zafiro.";
               }
               
               strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
               strAux += "Error de diferencia en Valor de Compensacion Solidaria ocurrido en el metodo existeDiferencias_Iva_ComSol_DevVta. El campo tbm_cabMovInv.nd_valComSol es igual a ";
               strAux += bdeValComSol_CabMovInv + ". El calculo de Valor de Compensacion Solidaria basado en nd_porIva de la Factura y nd_sub es igual a " + bdeValComSol_Cal + ". La diferencia es " + bdeAux + ".";
            }
            
            if (blnExiDif == true)
            {  strSQL =  "UPDATE " + strNomTbl + " ";
               strSQL += "SET    st_autFacEle = 'X', ";
               strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
               strSQL += "WHERE  co_emp = " + intCodEmp;
               strSQL += "          and co_loc = " + intCodLoc;
               strSQL += "          and co_tipdoc = " + intCodTipDoc;
               strSQL += "          and co_doc = " + intCodDoc;
               stmLoc.executeUpdate(strSQL);
               strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
               blnRes = true;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_Iva_ComSol_DevVta()
   
   private boolean existeDiferencias_Sub_Iva_DevVta(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux;
      BigDecimal bdeAux1, bdeAux2, bdeValCabMovInv, bdeValCal;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            strSQL =  "SELECT d1.*, d2.nd_sub, d2.nd_valiva, abs(d2.nd_sub - d1.totDetCal) as dif_sub, abs(d2.nd_valIva - d1.ivaDetCal) as dif_iva, d2.ne_numdoc, " + "\n";
            strSQL += "   (case when d2.nd_sub <> d1.totDetCal then 'NO' else 'OK' end) AS mira_sub, " + "\n";
            strSQL += "   (case when d2.nd_valIva <> d1.IvaDetCal then 'NO' else 'OK' end) AS mira_iva " + "\n";
            strSQL += "FROM ( SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, sum(c1.totDetCal) as totDetCal, round(sum(c1.ivaDetCal), 2) as IvaDetCal " + "\n";
            strSQL += "       FROM ( SELECT b1.*, round(b1.nd_totDetCal, 2) as totDetCal, " + "\n";
            strSQL += "                 ( case when b1.st_ivaVen = 'S' then round(b1.nd_totDetCal, 2) * (b1.nd_poriva / 100) " + "\n";
            strSQL += "                        when b1.st_ivaVen = 'N' then 0 " + "\n";
            strSQL += "                        else 0 " + "\n";
            strSQL += "                 end ) as ivaDetCal " + "\n";
            strSQL += "              FROM ( SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.st_tipdev, a1.co_itm, a1.tx_codalt, a1.tx_nomitm, " + "\n";
            strSQL += "                        ( case when a2.st_tipdev = 'C' then abs(a1.nd_can) " + "\n"; //st_tipdev = 'C' (Cantidades)
            strSQL += "                               when a2.st_tipdev = 'V' then abs(a1.nd_canorg) " + "\n"; //st_tipdev = 'V' (Precio)
            strSQL += "                               when a2.st_tipdev in ('P','E') then abs(a1.nd_canorg) " + "\n"; //st_tipdev = 'P' (Porcentaje de descuento) y 'E' (Descuentos especiales)
            strSQL += "                        end ) as nd_can, " + "\n";
            strSQL += "                        ( case when a2.st_tipdev = 'C' then a1.nd_preuni " + "\n"; //st_tipdev = 'C' (Cantidades)
            strSQL += "                               when a2.st_tipdev = 'V' then abs(a1.nd_can) " + "\n"; //st_tipdev = 'V' (Precio)
            strSQL += "                               when a2.st_tipdev in ('P','E') then a1.nd_preuni " + "\n"; //st_tipdev = 'P' (Porcentaje de descuento) y 'E' (Descuentos especiales)
            strSQL += "                        end ) as nd_preuni, " + "\n";
            strSQL += "                        ( case when a2.st_tipdev = 'C' then (abs(a1.nd_can) * a1.nd_preuni) - (abs(a1.nd_can) * a1.nd_preuni * a1.nd_pordes / 100) " + "\n";
            strSQL += "                               when a2.st_tipdev = 'V' then (abs(a1.nd_canorg) * abs(a1.nd_can)) - (abs(a1.nd_canorg) * abs(a1.nd_can) * a1.nd_pordes / 100) " + "\n";
            strSQL += "                               when a2.st_tipdev in ('P','E') then ((abs(a1.nd_canorg) * a1.nd_preuni) - ((abs(a1.nd_canorg) * a1.nd_preuni) * a1.nd_pordes / 100)) * abs(a1.nd_can) / 100 " + "\n";
            strSQL += "                        end ) as nd_totDetCal, " + "\n";
            strSQL += "                        ( case when a2.st_tipdev = 'C' then (abs(a1.nd_can) * a1.nd_preuni * a1.nd_pordes / 100) " + "\n";
            strSQL += "                               when a2.st_tipdev = 'V' then (abs(a1.nd_canorg) * abs(a1.nd_can) * a1.nd_pordes / 100) " + "\n";
            strSQL += "                               when a2.st_tipdev in ('P','E') then (abs(a1.nd_canorg) * a1.nd_preuni) " + "\n";
            strSQL += "                                                                 - ((abs(a1.nd_canorg) * a1.nd_preuni) - ((abs(a1.nd_canorg) * a1.nd_preuni) * a1.nd_pordes / 100)) * abs(a1.nd_can) / 100 " + "\n";
            strSQL += "                        end ) as nd_valdes, " + "\n";
            strSQL += "                        a1.nd_pordes, abs(a1.nd_tot) as nd_totdet, a1.nd_poriva, a3.st_ivaVen " + "\n";
            strSQL += "                     FROM       tbm_detmovinv as a1 " + "\n";
            strSQL += "                     INNER JOIN tbm_cabmovinv as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_loc and a1.co_tipdoc = a2.co_tipdoc and a1.co_doc = a2.co_doc " + "\n";
            strSQL += "                     INNER JOIN tbm_inv as a3 on a1.co_emp = a3.co_emp AND a1.co_itm = a3.co_itm " + "\n";
            strSQL += "                     WHERE a1.co_emp = " + intCodEmp + "and a1.co_loc = " + intCodLoc + "and a1.co_tipdoc = " + intCodTipDoc + " and a1.co_doc = " + intCodDoc + "\n";
            strSQL += "                   ) as b1 " + "\n";
            strSQL += "            ) as c1 " + "\n";
            strSQL += "       GROUP BY c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_doc, c1.st_ivaVen, c1.nd_poriva " + "\n";
            strSQL += "     ) as d1 " + "\n";
            strSQL += "INNER JOIN tbm_cabmovinv as d2 on d1.co_emp = d2.co_emp and d1.co_loc = d2.co_loc and d1.co_tipdoc = d2.co_tipdoc and d1.co_doc = d2.co_doc";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  strAux = "";
               bdeAux1 = new BigDecimal(rstLoc.getString("nd_sub"));
               bdeAux1 = bdeAux1.abs();
               bdeAux2 = new BigDecimal(rstLoc.getString("totDetCal"));
               bdeAux2 = bdeAux2.abs();
               bdeAux1 = bdeAux1.subtract(bdeAux2);
               bdeAux1 = bdeAux1.abs();
               
               if (rstLoc.getString("mira_sub").equals("NO") && bdeAux1.compareTo(new BigDecimal(strNumDecTolDifCalIvaSub)) > 0)
               {  //Hay una diferencia en el Subtotal del campo de la tabla cabecera con el calculo de los valores de la tabla de detalle.
                  bdeAux1 = new BigDecimal(rstLoc.getString("dif_sub"));
                  bdeAux1 = objUti.redondearBigDecimal(bdeAux1, objParSis.getDecimalesMostrar());
                  bdeValCabMovInv = new BigDecimal(rstLoc.getString("nd_sub"));
                  bdeValCabMovInv = objUti.redondearBigDecimal(bdeValCabMovInv.abs(), objParSis.getDecimalesMostrar());
                  bdeValCal = new BigDecimal(rstLoc.getString("totDetCal"));
                  bdeValCal = objUti.redondearBigDecimal(bdeValCal.abs(), objParSis.getDecimalesMostrar());
                  strAux = "Mensaje devuelto por el Servicio de Zafiro. ";
                  strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strAux += "Error de diferencia en Subtotal ocurrido en el metodo existeDiferencias_Sub_Iva_DevVta. El campo tbm_cabMovInv.nd_sub es igual a ";
                  strAux += bdeValCabMovInv + ". El calculo de subtotal basado en tbm_detMovInv es igual a " + bdeValCal + ". La diferencia es " + bdeAux1 + ". ";
                  strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalIvaSub + ".";
               }
            
               bdeAux1 = new BigDecimal(rstLoc.getString("nd_valIva"));
               bdeAux1 = bdeAux1.abs();
               bdeAux2 = new BigDecimal(rstLoc.getString("ivaDetCal"));
               bdeAux2 = bdeAux2.abs();
               bdeAux1 = bdeAux1.subtract(bdeAux2);
               bdeAux1 = bdeAux1.abs();
               
               if (rstLoc.getString("mira_iva").equals("NO") && bdeAux1.compareTo(new BigDecimal(strNumDecTolDifCalIvaSub)) > 0)
               {  //Hay una diferencia en el IVA del campo de la tabla cabecera con el calculo de los valores de la tabla de detalle.
                  bdeAux1 = new BigDecimal(rstLoc.getString("dif_iva"));
                  bdeAux1 = objUti.redondearBigDecimal(bdeAux1, objParSis.getDecimalesMostrar());
                  bdeValCabMovInv = new BigDecimal(rstLoc.getString("nd_valIva"));
                  bdeValCabMovInv = objUti.redondearBigDecimal(bdeValCabMovInv.abs(), objParSis.getDecimalesMostrar());
                  bdeValCal = new BigDecimal(rstLoc.getString("ivaDetCal"));
                  bdeValCal = objUti.redondearBigDecimal(bdeValCal.abs(), objParSis.getDecimalesMostrar());
                  
                  if (strAux.equals(""))
                  {  strAux = "Mensaje devuelto por el Servicio de Zafiro.";
                  }
                  
                  strAux += " PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strAux += "Error de diferencia en IVA ocurrido en el metodo existeDiferencias_Sub_Iva_DevVta. El campo tbm_cabMovInv.nd_valIva es igual a ";
                  strAux += bdeValCabMovInv + ". El calculo de IVA basado en tbm_detMovInv es igual a " + bdeValCal + ". La diferencia es " + bdeAux1 + ". ";
                  strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalIvaSub + ".";
               }
               
               if (!strAux.equals(""))
               {  strSQL =  "UPDATE " + strNomTbl + " ";
                  strSQL += "SET    st_autFacEle = 'X', ";
                  strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
                  strSQL += "WHERE  co_emp = " + intCodEmp;
                  strSQL += "          and co_loc = " + intCodLoc;
                  strSQL += "          and co_tipdoc = " + intCodTipDoc;
                  strSQL += "          and co_doc = " + intCodDoc;
                  stmLoc.executeUpdate(strSQL);
                  strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
                  blnRes = true;
               }
            } //if (rstLoc.next())
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_Sub_Iva_DevVta()
   
   private boolean existeDiferencias_CabPag_DetPag_Ret(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux;
      BigDecimal bdeAux, bdeValCabPag, bdeValDetPag;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            strSQL =  "SELECT a1.nd_mondoc, a2.sumAbo, round(abs(a1.nd_mondoc - a2.sumAbo), 2) as difVal, (case when a1.nd_mondoc <> a2.sumAbo then 'NO' else 'OK' end) as tx_res " + "\n";
            strSQL += "FROM tbm_cabpag as a1 " + "\n";
            strSQL += "INNER JOIN ( select co_emp, co_loc, co_tipdoc, co_doc, sum(nd_abo) as sumAbo " + "\n";
            strSQL += "             from tbm_detpag " + "\n";
            strSQL += "             where st_reg = 'A' and co_emp = " + intCodEmp + " and co_loc = " + intCodLoc + " and co_tipdoc = " + intCodTipDoc + " and co_doc = " + intCodDoc + "\n";
            strSQL += "             group by co_emp, co_loc, co_tipdoc, co_doc " + "\n";
            strSQL += "           ) as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_loc and a1.co_tipDoc = a2.co_tipDoc and a1.co_doc = a2.co_doc";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            if (rstLoc.next())
            {  strAux = "";
               bdeAux = new BigDecimal(rstLoc.getString("difVal"));
               bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
               
               if (rstLoc.getString("tx_res").equals("NO") && bdeAux.compareTo(new BigDecimal(strNumDecTolDifCalIvaSub)) > 0)
               {  //Hay una diferencia en el valor del campo de la tabla cabecera con el calculo de los valores de la tabla de detalle.
                  bdeValCabPag = new BigDecimal(rstLoc.getString("nd_mondoc"));
                  bdeValCabPag = objUti.redondearBigDecimal(bdeValCabPag, objParSis.getDecimalesMostrar());
                  bdeValCabPag = bdeValCabPag.abs();
                  bdeValDetPag = new BigDecimal(rstLoc.getString("sumAbo"));
                  bdeValDetPag = objUti.redondearBigDecimal(bdeValDetPag, objParSis.getDecimalesMostrar());
                  bdeValDetPag = bdeValDetPag.abs();
                  
                  strAux = "Mensaje devuelto por el Servicio de Zafiro. ";
                  strAux += "PK co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                  strAux += "Error de diferencia en tbm_cabpag y tbm_detpag ocurrido en el metodo existeDiferencias_CabPag_DetPag_Ret. El campo tbm_cabpag.nd_mondoc es igual a ";
                  strAux += bdeValCabPag + ". El calculo de la Retencion basado en tbm_detpag es igual a " + bdeValDetPag + ". La diferencia es " + bdeAux + ". ";
                  strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalIvaSub + ".";
               }
            
               if (!strAux.equals(""))
               {  strSQL =  "UPDATE " + strNomTbl + " ";
                  strSQL += "SET    st_autFacEle = 'X', ";
                  strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
                  strSQL += "WHERE  co_emp = " + intCodEmp;
                  strSQL += "          and co_loc = " + intCodLoc;
                  strSQL += "          and co_tipdoc = " + intCodTipDoc;
                  strSQL += "          and co_doc = " + intCodDoc;
                  stmLoc.executeUpdate(strSQL);
                  strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
                  blnRes = true;
               }
            } //if (rstLoc.next())
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_CabPag_DetPag_Ret()
   
   private boolean existeDiferencias_PagMovInv_Ret(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      boolean blnRes = false, blnMosInfPK;
      Statement stmLoc;
      ResultSet rstLoc;
      String strAux;
      int i, intCodEmp_DocOri, intCodLoc_DocOri, intCodTipDoc_DocOri, intCodDoc_DocOri;
      BigDecimal bdeValAbo, bdeValRetCal, bdePorRet, bdeBasImp, bdeDifValRet;
      
      try
      {  if (con != null)
         {  stmLoc = con.createStatement();
            i = 0;
            blnMosInfPK = true;
            strAux = getDesErrFacEle(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strNomTbl);
            strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_porret, a1.tx_aplret, a1.nd_basimp, a1.nd_abo, round((a1.nd_basimp * a1.nd_porret / 100) * -1, 2) as valRetCal " + "\n";
            strSQL += "FROM tbm_pagmovinv as a1 " + "\n";
            strSQL += "INNER JOIN ( select co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locPag, co_tipdocPag, co_docPag, co_regPag, nd_abo " + "\n";
            strSQL += "             from tbm_detpag where st_reg = 'A' and co_emp = " + intCodEmp + " and co_loc = " + intCodLoc + " and co_tipdoc = " + intCodTipDoc + " and co_doc = " + intCodDoc + "\n";
            strSQL += "           ) as a2 on a1.co_emp = a2.co_emp and a1.co_loc = a2.co_locPag and a1.co_tipDoc = a2.co_tipDocPag and a1.co_doc = a2.co_docPag and a1.co_reg = a2.co_regPag";
            rstLoc = stmLoc.executeQuery(strSQL);
            
            while (rstLoc.next())
            {  bdeValAbo = new BigDecimal(rstLoc.getString("nd_abo"));
               bdeValAbo = objUti.redondearBigDecimal(bdeValAbo, objParSis.getDecimalesMostrar());
               bdeValAbo = bdeValAbo.abs();
               bdeValRetCal = new BigDecimal(rstLoc.getString("valRetCal"));
               bdeValRetCal = objUti.redondearBigDecimal(bdeValRetCal, objParSis.getDecimalesMostrar());
               bdeValRetCal = bdeValRetCal.abs();
               bdeDifValRet = bdeValAbo.subtract(bdeValRetCal);
               bdeDifValRet = bdeDifValRet.abs();
               
               if (!bdeValAbo.equals(bdeValRetCal) && bdeDifValRet.compareTo(new BigDecimal(strNumDecTolDifCalValRet_PagMovInv)) > 0)
               {  //Hay una diferencia en el valor del campo de la tabla tbm_pagmovinv.nd_abo con los valores calculados basado en la base imponible de 
                  //esta misma tabla.
                  i++;
                  intCodEmp_DocOri = rstLoc.getInt("co_emp");
                  intCodLoc_DocOri = rstLoc.getInt("co_loc");
                  intCodTipDoc_DocOri = rstLoc.getInt("co_tipdoc");
                  intCodDoc_DocOri = rstLoc.getInt("co_doc");
                  
                  if (!strAux.equals(""))
                  {  //Si strAux <> "", significa que ya hay un valor guardado en el campo tx_desErrFacEle.  Por tanto, ese valor guardado no debe perderse
                     //en el momento de guardar la nueva descripcion que hay que agregar en dicho campo.
                     strAux += ". ";
                  }
                  
                  if (blnMosInfPK == true)
                  {  strAux += "Mensaje devuelto por el Servicio de Zafiro. ";
                     strAux += "PK de Retencion: co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc + ". ";
                     strAux += "PK de Documento Origen: co_emp = " + intCodEmp_DocOri + "; co_loc = " + intCodLoc_DocOri + "; co_tipDoc = " + intCodTipDoc_DocOri + "; co_doc = " + intCodDoc_DocOri + ". ";
                     strAux += "Error de diferencia en tbm_pagmovinv ocurrido en el metodo existeDiferencias_PagMovInv_Ret. ";
                     strAux += "Valor de tolerancia de diferencia para calculo = " + strNumDecTolDifCalValRet_PagMovInv + ". ";
                     blnMosInfPK = false; //Al poner false, esto hace que en el cuerpo del mensaje de error solo aparezca una sola vez la PK.
                  }
                  
                  bdePorRet = new BigDecimal(rstLoc.getString("nd_porret"));
                  bdePorRet = objUti.redondearBigDecimal(bdePorRet, objParSis.getDecimalesMostrar());
                  bdeBasImp = new BigDecimal(rstLoc.getString("nd_basimp"));
                  bdeBasImp = objUti.redondearBigDecimal(bdeBasImp, objParSis.getDecimalesMostrar());
                  strAux += "Dif. #" + i + ". ";
                  strAux += "Porcentaje de Retencion = " + bdePorRet + ". ";
                  strAux += "Base imponible = " + bdeBasImp + ". ";
                  strAux += "El campo tbm_pagmovinv.nd_abo es igual a " + bdeValAbo + ". ";
                  strAux += "El calculo de la Retencion basado en tbm_pagmovinv.nd_basimp es igual a " + bdeValRetCal + ". ";
                  strAux += "La diferencia es " + bdeDifValRet + ".";;
               }
            } //while (rstLoc.next())
            
            if (!strAux.equals(""))
            {  strSQL =  "UPDATE " + strNomTbl + " ";
               strSQL += "SET    st_autFacEle = 'X', ";
               strSQL += "       tx_desErrFacEle = '" + strAux + "' ";
               strSQL += "WHERE  co_emp = " + intCodEmp;
               strSQL += "          and co_loc = " + intCodLoc;
               strSQL += "          and co_tipdoc = " + intCodTipDoc;
               strSQL += "          and co_doc = " + intCodDoc;
               stmLoc.executeUpdate(strSQL);
               strAux = enviarCorreoServidorInterno(intCodEmp, intCodLoc, intCodTipDoc, strAux);
               blnRes = true;
            }
            
            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
         } //if (con != null)
      } //try
         
      catch(Exception e)
      {  
         blnRes = true;
      }
         
      return blnRes;
   } //Funcion existeDiferencias_PagMovInv_Ret()
   
   private String getDesErrFacEle(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strNomTbl)
   {
      String strRes = "";
      java.sql.Statement stmLoc;
      java.sql.ResultSet rstLoc;
      
      try
      {
         stmLoc = con.createStatement();
         strSQL =  "SELECT tx_desErrFacEle ";
         strSQL += "FROM   " + strNomTbl + " ";
         strSQL += "WHERE co_emp = " + intCodEmp;
         strSQL += "      and co_loc = " + intCodLoc;
         strSQL += "      and co_tipdoc = " + intCodTipDoc;
         strSQL += "      and co_doc = " + intCodDoc;
         rstLoc = stmLoc.executeQuery(strSQL);
         
         if (rstLoc.next())
         {  strRes = rstLoc.getString("tx_desErrFacEle") == null? "" :rstLoc.getString("tx_desErrFacEle");
         }
         
         rstLoc.close();
         rstLoc = null;
         stmLoc.close();
         stmLoc = null;
      } //try
      
      catch(Exception e)
      {  
         strRes = "";
      }
      
      return strRes;
   } //Funcion getDesErrFacEle()
   
   private String enviarCorreoServidorInterno(int intCodEmp, int intCodLoc, int intCodTipDoc, String strMsjErr)
   {
      String strRes = "", toAddress1 = "", server, userName, fromAddress, clvRemite, body, strNomEmpLoc;
      
      try
      {  //System.out.println("\n" + "Inicio de envio de correo desde servidor interno: " + strNomFilXml);
         fromAddress = "";
         clvRemite = "";
         server = "";
         userName = "";
         
         if (intCodTipDoc == 229)
         {  //Se envia correo al recurso encargado de Devoluciones de Ventas.
            toAddress1 = "sistemas2@tuvalsa.com";
         }
         else if (intCodTipDoc == 230)
         {  //Se envia correo al recurso encargado de Retenciones.
            toAddress1 = "sistemas2@tuvalsa.com";
         }
         else if ( intCodTipDoc == 228 && ((intCodEmp == 1 && intCodLoc == 11) || (intCodEmp == 2 && intCodLoc == 5) || (intCodEmp == 4 && intCodLoc == 11)) )
         {  //Se envia correo al recurso encargado de Facturas relacionadas.
            toAddress1 = "sistemas2@tuvalsa.com";
         }
         else 
         {  //Se envia correo al recurso encargado de facturas de los diversos locales y que no sean Facturas relacionadas.
            toAddress1 = "sistemas2@tuvalsa.com";
         }
         
         //toAddress = "sistemas7@tuvalsa.com"; //DBN_Prueba
         server = "smtp.gmail.com";
         userName = "sistemasgrupolsc@gmail.com";
         fromAddress = "sistemasgrupolsc@gmail.com";
         clvRemite = "tuval1616";
         body = strMsjErr;
         
         Properties props = System.getProperties();
         props.put("mail.smtp.starttls.enable", "true");
         props.put("mail.smtp.ssl.trust", server);
         props.put("mail.smtp.host", server);
         props.put("mail.smtp.user", userName);
         props.put("mail.smtp.password", clvRemite);
         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.auth", "true");
         
         /*
         Properties props = System.getProperties();
         props.put("mail.smtp.host", server);
         */
         
         //Authenticator auth = new MyAuthenticator();
         Authenticator auth = new MyAuthenticator(userName, clvRemite);

         // Get session
         Session session = Session.getDefaultInstance(props, auth);
         session.setDebug(true);

         // Define message
         MimeMessage message = new MimeMessage(session);

         // Set the from address
         message.setFrom(new InternetAddress(fromAddress));

         if (!toAddress1.equals(""))
         {  // Set the to address 1
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress1));
         }
         
         strNomEmpLoc = "";
         
         if (strPrmNomEmp_Glo.indexOf("TUVAL") != -1)
         {  switch (intCodEmp)
            {  case 1:
                  strNomEmpLoc = "TUVAL";
                  break;
               case 2:
                  strNomEmpLoc = "CASTEK";
                  break;
               case 4:
                  strNomEmpLoc = "DIMULTI";
                  break;
               case 5:
                  strNomEmpLoc = "COSENCO";
                  break;
            }
         }
         else
         {
            strNomEmpLoc = strPrmNomEmp_Glo;
         }
         
         // Set the subject
         message.setSubject("Diferencias encontradas por Servicio de Facturacion Electronica en " + strNomEmpLoc);

         MimeBodyPart cuerpoCorreo = new MimeBodyPart();
         //cuerpoCorreo.setText("Estado de Cuenta ...");
         cuerpoCorreo.setContent(body, "text/html");

         MimeMultipart multipart = new MimeMultipart();
         multipart.addBodyPart(cuerpoCorreo);

         message.setContent(multipart);
         message.saveChanges();
         
         // Send message
         Transport.send(message);
         strRes = "true";
         //System.out.println("\n" + "Fin de envio de correo desde servidor interno: " + strNomFilXml);
      } //try
      
      catch(Exception e)
      {  
         strRes = "Error al enviar el correo desde servidor interno a la direccion " + toAddress1 + ". " + e.toString();
         System.out.println("\n" + "Error al enviar el correo desde servidor interno a la direccion " + toAddress1);
      }
      
      return strRes;
   } //Funcion enviarCorreoServidorInterno()
   
   static class MyAuthenticator extends Authenticator
   {
      PasswordAuthentication l;
      
      private MyAuthenticator(String userName, String password)
      {
         l = new PasswordAuthentication(userName, password);
      }
      
      protected PasswordAuthentication getPasswordAuthentication()
      {
         return l;
      }
   }
   
   private boolean getNombre_y_clave_archivo_p12(int intCodEmp)
   {
      boolean blnRes = true;
      
      try
      {  strNomEmp = "";
         strNomFil_p12 = "";
         strClvFil_p12 = "";
         
         if (strPrmNomEmp_Glo.indexOf("TUVAL") != -1 || strPrmNomEmp_Glo.indexOf("CASTEK") != -1 || strPrmNomEmp_Glo.indexOf("DIMULTI") != -1 || strPrmNomEmp_Glo.indexOf("COSENCO") != -1)
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
            else if (intCodEmp == 5)
            {  strNomEmp = "Cosenco";
               strNomFil_p12 = objParSis.getCosencoNombreArchivo_p12();
               strClvFil_p12 = objParSis.getCosencoClaveArchivo_p12();  
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