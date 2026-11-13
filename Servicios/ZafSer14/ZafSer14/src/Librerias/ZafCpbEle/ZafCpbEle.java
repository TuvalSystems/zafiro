/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// ZafSer14
package Librerias.ZafCpbEle;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafCpbEle
{
   Librerias.ZafParSis.ZafParSis objParSis;
   ZafUtil objUti;
   private javax.swing.JInternalFrame ifruso;
   private javax.swing.JOptionPane oppMsg;
   private String strSQL;
   ArrayList arlRegDetResWS;
   private ZafNotCorEle objNotCorEle;
   private static final int INT_ARL_COD_CFG_RETENCIONES = 29;
   //Constantes del arreglo que devuelven los metodos de autorizacion de algun comprobante electronico
   static final int INT_ARL_DAT_RES_EST = 0;
   static final int INT_ARL_DAT_RES_MSJ = 1;
   static final int INT_ARL_DAT_RES_NUM_AUT = 2;
   static final int INT_ARL_DAT_RES_FEC_AUT = 3;
   static final int INT_ARL_DAT_RES_EST_CLV_ACC_REG = 4;
   static final int INT_ARL_DAT_RES_CLV_ACC = 5;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_cabguirem
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_EMP = 0;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_LOC = 1;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_CAB_GUI_REM_COD_DOC = 3;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_DOC = 4;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_DOC = 5;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_ING = 6;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_FAC = 7;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC = 8;
   static final int INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC = 9;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA = 10;
   static final int INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA = 11;
   static final int INT_ARL_DAT_CAB_GUI_REM_DIR_PAR = 12;
   static final int INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES = 13;
   static final int INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES = 14;
   static final int INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES = 15;
   static final int INT_ARL_DAT_CAB_GUI_REM_RUC_TRA = 16;
   static final int INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA = 17;
   static final int INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET = 18;
   static final int INT_ARL_DAT_CAB_GUI_REM_CHO_RET = 19;
   static final int INT_ARL_DAT_CAB_GUI_REM_OBS1 = 20;
   static final int INT_ARL_DAT_CAB_GUI_REM_OBS2 = 21;
   //Constantes del arreglo que guarda ciertos campos de la tabla tbm_detguirem
   static final int INT_ARL_DAT_DET_GUI_REM_COD_EMP = 0;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_LOC = 1;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_TIP_DOC = 2;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_DOC = 3;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_REG = 4;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_ITM = 5;
   static final int INT_ARL_DAT_DET_GUI_REM_COD_ALT = 6;
   static final int INT_ARL_DAT_DET_GUI_REM_NOM_ITM = 7;
   static final int INT_ARL_DAT_DET_GUI_REM_CAN = 8;
   //Variables para armar XML de comprobantes electronicos
   int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
   int intTipAmb, intTipEmi, intConEsp, intTimEsp;
   String strNumCpb, strCodNum, strPtoEmi, strCodEst, strCodCpb, strNomEmp, strRucEmp, strDirEmp, strDirLoc, strRazSocCom, strNomFilGuiRem;
   String strRutNomFilGuiRem, strNomFil_p12, strClvFil_p12;
   public boolean blnDirMacSrvPrd_Found;
   public String strEjeProTipAmbSriPru, strNomFilLog;
   
   public ZafCpbEle(Librerias.ZafParSis.ZafParSis obj, String strPrmNomFil_p12, String strPrmClvFil_p12)
   {
      String strAux, strDirMacSrvPro, strEnvCorTipAmbSriPru;
      StringTokenizer strTokCad;
      
      try
      {
         this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
         this.strNomFil_p12 = strPrmNomFil_p12;
         this.strClvFil_p12 = strPrmClvFil_p12;
         objUti = new ZafUtil();
         oppMsg = new JOptionPane();
         ifruso = new JInternalFrame();
         strEjeProTipAmbSriPru = objParSis.getEjecutarProcesoTipoAmbienteSRIPruebas();
         strEnvCorTipAmbSriPru = objParSis.getEnviarCorreoTipoAmbienteSRIPruebas();
         blnDirMacSrvPrd_Found = false;
         strDirMacSrvPro = objParSis.getDireccionMACServidorProduccion();
         strTokCad = new StringTokenizer(strDirMacSrvPro, "|");
         
         while (strTokCad.hasMoreElements())
         {  strAux = strTokCad.nextToken().toString(); //Ej. de strAux: 00-0C-29-12-12-0B
            
            if (strAux.lastIndexOf(objParSis.getMACAddress()) != -1)
            {  //Si la MAC Address corresponde al servidor de Produccion, solo en ese caso el Tipo de Ambiente del SRI
               //para Facturacion electronica sera el de Produccion
               intTipAmb = 2; //Produccion
               blnDirMacSrvPrd_Found = true;
               break;
            }
         }
         
         if (blnDirMacSrvPrd_Found == false)
         {  intTipAmb = 1; //Pruebas
            
            if (strEjeProTipAmbSriPru.equals("N") && strEnvCorTipAmbSriPru.equals("S"))
            {  //Significa que no se desea generar el XML en Ambiente de Pruebas del SRI. Por tanto, se procedera a notificar por correo.
               strAux =  "<FONT COLOR=\"blue\">Servicio 14:</FONT> Se detecto que la Direccion MAC donde se ejecuto el servicio no corresponde al Servidor de Produccion." + "<BR>";
               strAux += "Por tanto, los XML se van a generar y autorizar en Ambiente de Pruebas del SRI." + "<BR>";
               strAux += "Direccion MAC del equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getMACAddress() + "</FONT><BR>";
               strAux += "Equipo donde se ejecuto el servicio: <FONT COLOR=\"blue\">" + objParSis.getNombreComputadoraConDirIP() + "</FONT><BR><BR>";
               strAux += "Notifique esta novedad al Administrador del sistema";
               objNotCorEle = new ZafNotCorEle(objParSis);
               
               if (objNotCorEle.enviarNotificacionCorreoElectronico(0, 1, INT_ARL_COD_CFG_RETENCIONES, strAux))
               {  //System.out.println("OK INT_ARL_COD_CFG_RETENCIONES");
                  //Todo OK
               }
            }
         }
         
         //System.out.println("MAC = " + objParSis.getMACAddress());
         intTipEmi = 1; //Emision normal
         //intTipEmi = 9; //DBN_Prueba
      }
      
      catch (CloneNotSupportedException e)
      {  
         mostrarMsgErr(e.getMessage());
      }
   }
   
   /**
   * Esta función muestra un mensaje de error al usuario. Se podría utilizar
   * para mostrar al usuario un mensaje que indique que algo salió mal.
   */
   private void mostrarMsgErr(String strMsg)
   {
      String strTit;
      strTit = "Mensaje del sistema Zafiro";
      oppMsg.showMessageDialog(ifruso, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
   }
   
   private class Modulo11
   {
      private int getDigitoControl(String strCad) 
      {
         int i, intFactor, intLngCad, intSumTot, intAux, intResiduo, intRes = -1;
         String strCadInv = "", strAux;

         try
         {
           for (i = strCad.length() - 1; i >= 0; i--) 
              strCadInv = strCadInv + strCad.charAt(i);
         
           intLngCad = strCadInv.length();
           intFactor = 2;
           intSumTot = 0;

           for (i = 0; i < intLngCad; i++)
           {  if (intFactor == 8)
                 intFactor = 2;

              intAux = Integer.parseInt(strCadInv.substring(i, i + 1));
              intAux *= intFactor;
              intFactor++;
              intSumTot += intAux;
           }
          
           intResiduo = intSumTot % 11;
           intAux = 11 - intResiduo;
           
           if (intAux < 10)
              intRes = intAux;
           else if (intAux == 10)
              intRes = 1;
           else if (intAux == 11)
              intRes = 0;
         }
         
         catch(Exception Evt)
         {  intRes = -1;
            //strAux = "Error al ejecutar el algorimo denominado Metodo 11" + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema." + "\n\n";
            //strAux += Evt.getMessage();
            //mostrarMsgErr(strAux);
         }
         
         return intRes;
      }
   } //class Modulo11
   
   private boolean firmarArch_p12(int intCodEmp, String strNomFil, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strAux;
      
      try
      {  //Se va a firmar el archivo xml
         //-----------------------------
         ZafFirma_p12 obj = new ZafFirma_p12(intCodEmp, strNomFil, strNomFil_p12, strClvFil_p12);
         obj.execute();
         //obj.arlDatFirDig.get(0) solo se llenara si se encontro alguna excepcion
         if (!obj.arlDatFirDig.get(0).toString().equals(""))
         {  blnRes = false;
            //Se va a validar que, si viene alguna comilla simple ('), se la deseche para que no haya
            //ningun problema al momento de hacer Update a la tabla para guardar dicha descripcion.
            strAux = quitarCaracterEspecial(obj.arlDatFirDig.get(0).toString(), "'");
            arlRegDetResWS = new ArrayList();
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
            arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, strAux);
            arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
            arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
            arlResWS.add(arlRegDetResWS);
         }
         else
         {   
            //mostrarMsgErr("Archivo firmado exitosamente");
         }
      }
   
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al firmar. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         //strAux = "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo firmar el archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
         
      return blnRes;
   } //Funcion firmarArch_p12()
   
   private String quitarCaracterEspecial(String strCad, String strCarEsp)
   {
      String strRes = "", strAux;
      StringTokenizer strTokCad;
      ArrayList<String> arlLista = new ArrayList<String>();
      int i;
      
      try
      {
         //Se va a validar que, si el parametro 'strCad' viene algun caracter especial, se la deseche para que no haya ningun
         //problema al momento de hacer Update a la tabla para guardar dicha descripcion.
         strTokCad = new StringTokenizer(strCad, strCarEsp);
         
         while (strTokCad.hasMoreElements())
         {  strAux = strTokCad.nextToken().toString();
            arlLista.add(strAux);
            //System.out.println(strAux);
         }

         if (arlLista.size() > 0)
         {  strAux = "";
            
            for (i = 0; i < arlLista.size(); i++)
            {  strAux += arlLista.get(i);
               if (strCarEsp.equals("\n") && i < arlLista.size() - 1)
                  strAux += ". ";
            }
            
            strRes = strAux;
         }
         else
            strRes = strCad; //No se presento ningun caracter especial en el parametro recibido
      }
       
      catch(Exception e)
      {
         strRes = "Error en la funcion quitarCaracterEspecial. " + e.toString();
      }
    
      return strRes;
   } //Funcion quitarCaracterEspecial()
   
   /**
    * Esta funcion se encarga de reemplazar un caracter por otro en una cadena dada.
    * @param strCad Cadena donde se desea reemplazar el caracter.
    * @param chrCarBsq Caracter a buscar dentro de la cadena.
    * @param strCarRpl Caracter que reemplazara el caracter buscado.
    * @return La nueva cadena con el caracter reemplazado.
   */
   private String reemplazarCaracter(String strCad, char chrCarBsq, String strCarRpl)
   {
      String strRes = "", strAux;
      char[] arrCad;
      int i;
      
      try
      {
         strAux = "";
         arrCad = strCad.toCharArray();
         
         for (i = 0; i < arrCad.length; i++)
         {  if (arrCad[i] == chrCarBsq)
               strAux += strCarRpl;
            else
               strAux += arrCad[i];
         }
         
         strRes = strAux;
      }
       
      catch(Exception e)
      {
         strRes = strCad;
      }
    
      return strRes;
   } //Funcion reemplazarCaracter()
   
   private boolean obtenerDatosTagXML(Connection con, int intCodEmp, int intCodLoc, ArrayList arlResWS)
   {
       boolean blnRes = true;
       java.sql.Statement stm;
       java.sql.ResultSet rst;
       
       try
       {    intConEsp = 0;

            if (con != null)
            {   stm = con.createStatement();
                strNomEmp = "";
                strRucEmp = "";
                strSQL =  "SELECT     a1.tx_nom as tx_nomEmp, a1.tx_ruc as tx_rucEmp, a1.tx_dir as tx_dirEmp, a1.ne_numResConEsp, a2.tx_dir as tx_dirLoc, a2.tx_secdoc ";
                strSQL += "FROM       tbm_emp as a1 ";
                strSQL += "INNER JOIN tbm_loc as a2 ON a1.co_emp = a2.co_emp ";
                strSQL += "WHERE      a1.co_emp = " + intCodEmp;
                strSQL += "           and a2.co_loc = " + intCodLoc;
                rst = stm.executeQuery(strSQL);
                
                if (rst.next())
                {  strNomEmp = rst.getString("tx_nomEmp");
                   strRucEmp = rst.getString("tx_rucEmp");
                   strDirEmp = rst.getString("tx_dirEmp");
                   intConEsp = rst.getInt("ne_numResConEsp");
                   strDirLoc = rst.getString("tx_dirLoc");
                   //Ej. valor de campo tx_secdoc: 005-001
                   strCodEst = rst.getString("tx_secdoc").substring(0, 3); //005
                   strPtoEmi = rst.getString("tx_secdoc").substring(4, 7); //001
                }

                stm.close();
                stm = null;
                rst.close();
                rst = null;
            }
       } //try
        
       catch(Exception e)
       {  blnRes = false;
          arlRegDetResWS = new ArrayList();
          arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
          arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al obtener los datos para el archivo XML. " + e.getMessage());
          arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
          arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
          arlResWS.add(arlRegDetResWS);
          //strAux = "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema." + "\n\n";
          //strAux += e.getMessage();
          //mostrarMsgErr(strAux);
       }
    
       return blnRes;
   } //Funcion obtenerDatosTagXML()
   
   private boolean armarXMLGuiaRemision(Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String strRutFil, ArrayList arlResWS)
   {
        boolean blnRes = true, blnFound, blnEstImpGuiRem;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        Modulo11 objMod11 = new Modulo11();
        ArrayList arlRegCabGuiRem, arlDatCabGuiRem, arlRegDetGuiRem, arlDatDetGuiRem;
        String strXml, strNomFilXml, strClvAcc, strDD, strMM, strAAAA, strTipIdeTra, strFecEmiGuiRem, strFecIngGuiRem, strNumFac, strFecEmiFac;
        String strNumAutFac, strFecIniTra, strFecFinTra, strDirPar, strRucCliDes, strNomCliDes, strDirCliDes, strRucTra, strRazSocTra;
        String strAux, strPlaVehRet, strChoRet, strNumDocSus, strObs1, strObs2, strHor, strMin, strSeg, strMsj, strMotEjeUpd;
        int i, intDigCtr, intHor, intMin, intSeg, intAux, intCodUsrIng_CabIngEgrMerBod, intNumDocGuiRem;
        long lngAux, lngNumCpb, lngCodNum;
        BigDecimal bdeAux;
        PrintWriter xml = null;

        try
        {  if (con != null)
           {  if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              {  stm = con.createStatement();
                 strCodCpb = "00"; //00: Valor invalido para la etiqueta del archivo xml
                 strSQL = "select tx_codCatFacEle from tbm_catTipDocSis where co_cat = 24"; //24: Guia de remision
                 rst = stm.executeQuery(strSQL);

                 if (rst.next())
                    strCodCpb = rst.getString("tx_codCatFacEle");
                  
                 arlDatCabGuiRem = new ArrayList();
                 strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc as fe_doc_guirem, a1.fe_ing as fe_ing_guirem, a1.ne_numdoc as ne_numdoc_guirem, a3.co_usring, ";
	         strSQL += "       ( SELECT DISTINCT b5.ne_numdoc ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         LEFT  JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as ne_numfac, ";
                 strSQL += "       ( SELECT DISTINCT b5.fe_doc ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         LEFT  JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as fe_doc_fac, ";
                 strSQL += "       ( SELECT DISTINCT b5.tx_numAutFacEle ";
                 strSQL += "         FROM       tbm_cabguirem as b1 ";
                 strSQL += "         LEFT  JOIN tbm_cabingegrmerbod as b2 on b1.co_emp = b2.co_emp AND b1.co_loc = b2.co_locrelguirem AND b1.co_tipdoc = b2.co_tipdocrelguirem and b1.co_doc = b2.co_docrelguirem ";
                 strSQL += "         LEFT  JOIN tbr_cabguirem as b3 on b3.co_emp = b1.co_emp and b3.co_loc = b1.co_loc and b3.co_tipdoc = b1.co_tipdoc and b3.co_doc = b1.co_doc ";
                 strSQL += "         LEFT  JOIN tbm_detguirem as b4 on b4.co_emp = b3.co_emp and b4.co_loc = b3.co_locrel and b4.co_tipdoc = b3.co_tipdocrel and b4.co_doc = b3.co_docrel ";
                 strSQL += "         LEFT  JOIN tbm_cabmovinv as b5 on b5.co_emp = b4.co_emp AND b5.co_loc = b4.co_locRel AND b5.co_tipDoc = b4.co_tipDocRel AND b5.co_doc = b4.co_DocRel ";
                 strSQL += "         LEFT  JOIN tbm_cabtipdoc as b6 on b6.co_emp = b5.co_emp and b6.co_loc = b5.co_loc and b6.co_tipdoc = b5.co_tipdoc ";
                 strSQL += "         WHERE      b1.co_emp = " + intCodEmp;
                 strSQL += "                    and b1.co_loc = " + intCodLoc;
                 strSQL += "                    and b1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "                    and b1.co_doc = " + intCodDoc;
                 strSQL += "                    and b5.st_reg = 'A' and b6.co_cat = 3 and b6.st_utiFacEle = 'S' "; //3: Facturas de ventas
                 //Se debe poner "limit 1" para que el subquery devuelva un solo registro. Esto es porque puede presentarse el caso de una venta relacionada 
                 //y si es asi, puede haber el caso de que una misma guia de remision este asociada con 2 o mas facturas de ventas
                 strSQL += "                    limit 1 ";
                 strSQL += "       ) as tx_numAutFac, ";
                 strSQL += "       a1.fe_initra, a1.fe_tertra, a1.tx_ptopar, a1.tx_rucclides, a1.tx_nomclides, a1.tx_dirclides, a1.tx_ciuclides, a1.tx_datdocoriguirem, ";
                 //Si tbm_cabingegrmerbod.co_veh = null, significa que 'El cliente retira la mercaderia'
                 //Si tbm_cabingegrmerbod.co_veh <> null, significa que 'La mercadería se envia al cliente en uno de nuestros vehículos'
                 strSQL += "       (case when a3.co_veh is null then a3.tx_idetra else '" + strRucEmp + "' end) as tx_ructra, ";
                 strSQL += "       (case when a3.co_veh is null then a3.tx_nomtra else '" + strNomEmp + "' end) as tx_razsoctra, ";
                 strSQL += "       (case when a3.co_veh is null then a3.tx_plavehtra else a5.tx_pla end) as tx_plavehtra, ";
                 strSQL += "       (case when a3.co_veh is not null then a6.tx_ide end) as tx_idecho, ";
                 strSQL += "       (case when a3.co_veh is not null then a6.tx_nom || ' ' || a6.tx_ape end) as tx_nomcho_emp_env_mer, ";
                 strSQL += "       a1.tx_obs1, a1.tx_obs2 ";
                 strSQL += "FROM       tbm_cabguirem as a1 ";
                 strSQL += "INNER JOIN tbm_detguirem as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc ";
                 strSQL += "LEFT  JOIN tbm_cabingegrmerbod as a3 on a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_locrelguirem AND a1.co_tipdoc = a3.co_tipdocrelguirem and a1.co_doc = a3.co_docrelguirem ";
                 strSQL += "LEFT  JOIN tbm_veh AS a5 on a5.co_veh = a3.co_veh ";
                 strSQL += "LEFT  JOIN tbm_tra AS a6 on a6.co_tra = a3.co_cho ";
                 strSQL += "WHERE      a1.co_emp = " + intCodEmp;
                 strSQL += "           and a1.co_loc = " + intCodLoc;
                 strSQL += "           and a1.co_tipdoc = " + intCodTipDoc;
                 strSQL += "           and a1.co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);
                 intCodUsrIng_CabIngEgrMerBod = 0;
                 intNumDocGuiRem = 0;
                 strFecIngGuiRem = "";
                 
                 if (rst.next())
                 {  arlRegCabGuiRem = new ArrayList();
                    intCodUsrIng_CabIngEgrMerBod = rst.getInt("co_usring");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_COD_DOC, "" + rst.getInt("co_doc"));
                    intNumDocGuiRem = rst.getInt("ne_numdoc_GuiRem");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_DOC, "" + rst.getInt("ne_numdoc_GuiRem"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_doc_GuiRem"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC, "" + strAux);
                    
                    strFecIngGuiRem = rst.getString("fe_ing_GuiRem");
                    strFecIngGuiRem = strFecIngGuiRem.substring(0, 19); //Si la fecha tiene "2020-05-12 08:46:49.384964" va a extraer "2020-05-12 08:46:49"
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_ING, "" + strFecIngGuiRem);
                    
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_FAC, rst.getString("ne_numfac") == null? "" :rst.getString("ne_numfac"));
                    
                    strAux = rst.getString("fe_doc_fac") == null? "" :rst.getString("fe_doc_fac");
                    if (!strAux.equals(""))
                    {  strAux = objUti.formatearFecha(rst.getDate("fe_doc_fac"), "dd/MM/yyyy");
                       arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC, "" + strAux);
                    }
                    else
                       arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC, "");
                    
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC, rst.getString("tx_numautfac") == null? "" :rst.getString("tx_numautfac"));
                    strAux = objUti.formatearFecha(rst.getDate("fe_initra"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA, "" + strAux);
                    strAux = objUti.formatearFecha(rst.getDate("fe_tertra"), "dd/MM/yyyy");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA, "" + strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_DIR_PAR, rst.getString("tx_ptopar"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES, rst.getString("tx_rucclides"));
                    strAux = reemplazarCaracter(rst.getString("tx_nomclides"), '&', "&amp;");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES, strAux);
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES, "" + rst.getString("tx_dirclides"));
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RUC_TRA, "" + rst.getString("tx_ructra"));
                    strAux = reemplazarCaracter(rst.getString("tx_razsoctra"), '&', "&amp;");
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA, "" + strAux);
                    
                    strAux = rst.getString("tx_plavehtra") == null? "" :rst.getString("tx_plavehtra");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET, "" + strAux);
                    
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_CHO_RET, "" + rst.getString("tx_nomcho_emp_env_mer") == null? "" :rst.getString("tx_nomcho_emp_env_mer"));
                    
                    strAux = rst.getString("tx_obs1") == null? "" :rst.getString("tx_obs1");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_OBS1, strAux);
                    
                    strAux = rst.getString("tx_obs2") == null? "" :rst.getString("tx_obs2");
                    if (!strAux.equals(""))
                    {  strAux = reemplazarCaracter(strAux, '\n', " ");
                       strAux = reemplazarCaracter(strAux, '&', "&amp;");
                       strAux = reemplazarCaracter(strAux, '<', "&lt;");
                       strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    }
                    arlRegCabGuiRem.add(INT_ARL_DAT_CAB_GUI_REM_OBS2, strAux);
                    
                    arlDatCabGuiRem.add(arlRegCabGuiRem);
                 }

                 arlDatDetGuiRem = new ArrayList();
                 strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, tx_codalt, tx_nomitm, nd_can ";
                 strSQL += "FROM   tbm_detguirem ";
                 strSQL += "WHERE  co_emp = " + intCodEmp;
                 strSQL += "       and co_loc = " + intCodLoc;
                 strSQL += "       and co_tipdoc = " + intCodTipDoc;
                 strSQL += "       and co_doc = " + intCodDoc;
                 rst = stm.executeQuery(strSQL);

                 while (rst.next())
                 {  arlRegDetGuiRem = new ArrayList();
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_EMP, "" + rst.getInt("co_emp"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_LOC, "" + rst.getInt("co_loc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_TIP_DOC, "" + rst.getInt("co_tipdoc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_DOC, "" + rst.getInt("co_doc"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_REG, "" + rst.getInt("co_reg"));
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_ITM, "" + rst.getInt("co_itm"));
                    
                    strAux = rst.getString("tx_codalt");
                    strAux = reemplazarCaracter(strAux, '&', "&amp;");
                    strAux = reemplazarCaracter(strAux, '<', "&lt;");
                    strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_COD_ALT, strAux);
                    
                    strAux = rst.getString("tx_nomitm");
                    strAux = reemplazarCaracter(strAux, '&', "&amp;");
                    strAux = reemplazarCaracter(strAux, '<', "&lt;");
                    strAux = reemplazarCaracter(strAux, '>', "&gt;");
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_NOM_ITM, strAux);
                    
                    bdeAux = objUti.redondearBigDecimal(rst.getBigDecimal("nd_can"), objParSis.getDecimalesMostrar());
                    arlRegDetGuiRem.add(INT_ARL_DAT_DET_GUI_REM_CAN, "" + bdeAux);
                    arlDatDetGuiRem.add(arlRegDetGuiRem);    
                 }

                 strDD = "";
                 strMM = "";
                 strAAAA = "";
                 strHor = "";
                 strMin = "";
                 strSeg = "";
                 strFecIniTra = "";
                 strFecFinTra = "";
                 strFecEmiGuiRem = "";
                 strNumFac = "";
                 strFecEmiFac = "";
                 strNumAutFac = "";
                 strDirPar = "";
                 strRucCliDes = "";
                 strNomCliDes = "";
                 strDirCliDes = "";
                 strRucTra = ""; //RUC del transportista
                 strRazSocTra = ""; //Razon social del transportista
                 strPlaVehRet = ""; //Placa del vehiculo que retira la mercaderia
                 strChoRet = ""; //Nombre del chofer que retira la mercaderia
                 strTipIdeTra = ""; //Tipo de identificacion del transportista
                 strAux = "";
                 strCodNum = ""; //Codigo numerico (es secuencial)
                 strNumCpb = ""; //Numero de comprobante
                 strClvAcc = "";
                 blnEstImpGuiRem = false;
                 strMotEjeUpd = "";
                 
                 if (arlDatCabGuiRem.size() > 0)
                 {  strAux = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_COD_DOC);
                    lngCodNum = Long.parseLong(strAux);
                    strCodNum = String.format("%08d", lngCodNum); // => "00000001"

                    strAux = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_DOC);
                    lngNumCpb = Long.parseLong(strAux);
                    strNumCpb = String.format("%09d", lngNumCpb); // => "000000001"
                    //strNumCpb = "000000001"; //DBN_Prueba
                 
                    strNumFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_FAC);
                    if (!strNumFac.equals(""))
                    {  lngAux = Long.parseLong(strNumFac);
                       strNumFac = String.format("%09d", lngAux); // => "000000001"
                    }

                    strFecEmiFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_DOC_FAC);
                    //strFecEmiFac = "27/11/2014"; //DBN_Prueba

                    strNumAutFac = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NUM_AUT_FAC);
                    strFecIniTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_INI_TRA);
                    //strFecIniTra = "27/11/2014"; //DBN_Prueba
                    strFecFinTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_FIN_TRA);
                    //strFecFinTra = "27/11/2014"; //DBN_Prueba
                    strDirPar = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_DIR_PAR);
                    strRucCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RUC_CLI_DES);
                    strNomCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_NOM_CLI_DES);
                    strDirCliDes = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_DIR_CLI_DES);
                    strRucTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RUC_TRA);
                    strTipIdeTra = getTipIdeTransportista(strRucTra);
                    strRazSocTra = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_RAZ_SOC_TRA);
                    strPlaVehRet = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_PLA_VEH_RET);
                    strChoRet = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_CHO_RET);
                    strFecEmiGuiRem = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_FEC_DOC);
                    //strFecEmiGuiRem = "27/11/2014"; //DBN_Prueba
                    strDD = strFecEmiGuiRem.substring(0, 2);
                    strMM = strFecEmiGuiRem.substring(3, 5);
                    strAAAA = strFecEmiGuiRem.substring(6, 10);
                    Calendar objCal = new GregorianCalendar();
                    intHor = objCal.get(Calendar.HOUR_OF_DAY);
                    intMin = objCal.get(Calendar.MINUTE);
                    intSeg = objCal.get(Calendar.SECOND);
                    strHor = String.format("%02d", intHor);
                    strMin = String.format("%02d", intMin);
                    strSeg = String.format("%02d", intSeg);

                    strAux = strDD + strMM + strAAAA;
                    strAux += strCodCpb + strRucEmp + intTipAmb + strCodEst + strPtoEmi + strNumCpb + strCodNum + intTipEmi;
                    
                    strClvAcc = strAux;
                    intDigCtr = objMod11.getDigitoControl(strAux);
                    strClvAcc += intDigCtr;
                    
                    //--- Inicio bloque comentado el 22/May/2020 ---
                    //Este bloque fue comentado en la version del programa v0.3.7 (22/May/2020) para descongestionar trabajo al personal de Redes en la 
                    //reimpresion de guias. Esto fue aprobado por Eddye Lino y Alfredo Paulson.
                    /*
                    //Se va a calcular los dias transcurridos entre la fecha de emision de la O/D y la fecha actual
                    strSQL =  "SELECT a3.fe_doc as fe_doc_ordDes, (current_date - a3.fe_doc) as ne_num_dia_ordDes ";
                    strSQL += "FROM   tbm_cabguirem as a1 ";
                    strSQL += "INNER JOIN tbr_cabguirem as a2 on a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipdoc = a2.co_tipdoc and a1.co_doc = a2.co_doc ";
                    strSQL += "INNER JOIN tbm_cabguirem as a3 on a2.co_emp = a3.co_emp AND a2.co_locrel = a3.co_loc AND a2.co_tipdocrel = a3.co_tipdoc and a2.co_docrel = a3.co_doc ";
                    strSQL += "WHERE  a1.co_emp = " + intCodEmp;
                    strSQL += "       and a1.co_loc = " + intCodLoc;
                    strSQL += "       and a1.co_tipdoc = " + intCodTipDoc;
                    strSQL += "       and a1.co_doc = " + intCodDoc;
                    rst = stm.executeQuery(strSQL);
                    blnEstImpGuiRem = false;
                    
                    if (rst.next())
                    {  strAux = rst.getString("ne_num_dia_ordDes") == null? "" :rst.getString("ne_num_dia_ordDes");
                       if (!strAux.equals(""))
                       {  intAux = Integer.parseInt(strAux);
                          if (intAux > 30)
                          {  //Como entre la fecha actual y la fecha de emision de la O/D ha transcurrido mas de 30 dias, no se debe imprimir
                             //la guia de remision. Por ello, se pone el estado de impresion 'S' para que el Servicio 1 no imprima la guia.
                             blnEstImpGuiRem = true;
                             strMotEjeUpd =  "Fe_emision de OD es mayor a 30 dias. Fe_emi_OD = " + rst.getString("fe_doc_ordDes") + ". ";
                             strMotEjeUpd += "Num. dias antiguedad = " + intAux;
                          }
                       }
                    }
                    */
                    //--- Fin bloque comentado el 22/May/2020 ------
                    
                    if (blnEstImpGuiRem == false && intCodUsrIng_CabIngEgrMerBod == 246)
                    {  //co_usr 246 = wcampoverde
                       //Si el usuario es wcampoverde, significa que el confirmo la salida de mercaderia pero no quiere que se imprima en despacho.
                       //Por ello, se pone el estado de impresion 'S' para que el Servicio 1 no imprima la guia.
                       blnEstImpGuiRem = true;
                       strMotEjeUpd =  "tbm_cabIngEgrMerBod.co_usrIng = 246 = wcampoverde. El usuario confirmo la salida de mercaderia pero no quiere ";
                       strMotEjeUpd += "que se imprima en despacho";
                    }
                    
                    if (blnEstImpGuiRem == true)
                    {  //Se pone el estado de impresion 'S' para que el Servicio 1 no imprima la guia.
                       strSQL =  "UPDATE tbm_cabguirem ";
                       strSQL += "SET st_imp = 'S' ";
                       strSQL += "WHERE co_emp = " + intCodEmp;
                       strSQL += "      and co_loc = " + intCodLoc;
                       strSQL += "      and co_tipdoc = " + intCodTipDoc;
                       strSQL += "      and co_doc = " + intCodDoc;
                       stm.executeUpdate(strSQL);
                    }
                 } //if (arlDatCabGuiRem.size() > 0)
              
                 //Al inicio del nombre del archivo se pone la PK de la tabla para que, al momento de procesar en diferido, 
                 //se pueda relacionar el registro leido de la tabla con el archivo XML guardado en la carpeta.
                 strNomFilXml =  intCodEmp + "_" + intCodLoc + "_" + intCodTipDoc + "_" + intCodDoc + "_";
                 
                 strNomFilXml += "GuiaRemision_" + strCodEst + "_" + strPtoEmi + "_" + strNumCpb + "_";
                 strNomFilXml += strAAAA + strMM + strDD + "_" + strHor + strMin + strSeg;
                 strNomFilGuiRem = strNomFilXml;

                 xml = new PrintWriter(new FileOutputStream(strRutFil + File.separator + strNomFilXml + ".xml"));
                 strRutNomFilGuiRem = strRutFil + File.separator + strNomFilXml + ".xml";
                 
                 strXml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?> ";
                 strXml += "<guiaRemision id=\"comprobante\" version=\"1.1.0\"> ";
                 strXml += "<infoTributaria> ";
                 strXml += "<ambiente>" + intTipAmb + "</ambiente> ";
                 strXml += "<tipoEmision>" + intTipEmi + "</tipoEmision> ";
                 strXml += "<razonSocial>" + strNomEmp + "</razonSocial> ";
                 strXml += "<nombreComercial>" + strNomEmp + "</nombreComercial> ";
                 strXml += "<ruc>" + strRucEmp + "</ruc> ";
                 strXml += "<claveAcceso>" + strClvAcc + "</claveAcceso> ";
                 strXml += "<codDoc>" + strCodCpb + "</codDoc> ";
                 strXml += "<estab>" + strCodEst + "</estab> ";
                 strXml += "<ptoEmi>" + strPtoEmi + "</ptoEmi> ";
                 strXml += "<secuencial>" + strNumCpb + "</secuencial> ";
                 strXml += "<dirMatriz>" + strDirEmp + "</dirMatriz> ";
                 strXml += "</infoTributaria> ";
              
                 strXml += "<infoGuiaRemision> ";
                 strXml += "<dirEstablecimiento>" + strDirLoc + "</dirEstablecimiento> ";
                 strXml += "<dirPartida>" + strDirPar + "</dirPartida> ";
                 strXml += "<razonSocialTransportista>" + strRazSocTra + "</razonSocialTransportista> ";
                 strXml += "<tipoIdentificacionTransportista>" + strTipIdeTra + "</tipoIdentificacionTransportista> ";
                 strXml += "<rucTransportista>" + strRucTra + "</rucTransportista> ";
                 strXml += "<obligadoContabilidad>SI</obligadoContabilidad> "; //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                 strXml += "<contribuyenteEspecial>" + intConEsp + "</contribuyenteEspecial> ";
                 strXml += "<fechaIniTransporte>" + strFecIniTra + "</fechaIniTransporte> ";
                 strXml += "<fechaFinTransporte>" + strFecFinTra + "</fechaFinTransporte> ";
                 
                 if (strPlaVehRet.equals(""))
                    strPlaVehRet = "na";
                    
                 strXml += "<placa>" + strPlaVehRet + "</placa> ";
                 strXml += "</infoGuiaRemision> ";

                 strXml += "<destinatarios> ";
                 strXml +=   "<destinatario> ";
                 strXml +=     "<identificacionDestinatario>" + strRucCliDes + "</identificacionDestinatario> ";
                 strXml +=     "<razonSocialDestinatario>" + strNomCliDes + "</razonSocialDestinatario> ";
                 strXml +=     "<dirDestinatario>" + strDirCliDes + "</dirDestinatario> ";
                 
                 if (!strNumFac.equals(""))
                 {  //Si strNumFac <> "", significa que hay una factura de venta relacionada con la guia de remision
                    strXml +=     "<motivoTraslado>VENTA</motivoTraslado> ";
                 }   
                 else
                 {  //Si strNumFac = "", significa que no hay una factura de venta relacionada con la guia de remision
                    strXml +=     "<motivoTraslado>TRASLADO</motivoTraslado> ";
                 }
                 
                 if (!strNumFac.equals(""))
                 {  strXml +=     "<codDocSustento>01</codDocSustento> "; //01: Factura.
                    //En algun momento se sugiere reemplazar este codigo quemado segun lo indicado en el PDF del SRI
                    strNumDocSus = strCodEst + "-" + strPtoEmi + "-" + strNumFac;
                    strXml +=     "<numDocSustento>" + strNumDocSus + "</numDocSustento> ";
                 }
                 
                 if (!strNumAutFac.equals(""))
                    strXml +=     "<numAutDocSustento>" + strNumAutFac + "</numAutDocSustento> ";
                 
                 if (!strFecEmiFac.equals(""))
                    strXml +=     "<fechaEmisionDocSustento>" + strFecEmiFac + "</fechaEmisionDocSustento> ";

                 strXml += "<detalles> ";
              
                 for (i = 0; i < arlDatDetGuiRem.size(); i++)
                 {  strXml += "<detalle> ";
                    strXml += "<codigoInterno>" + objUti.getIntValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_COD_ITM) + "</codigoInterno> ";
                    strXml += "<codigoAdicional>" + objUti.getStringValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_COD_ALT) + "</codigoAdicional> ";
                    strXml += "<descripcion>" + objUti.getStringValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_NOM_ITM) + "</descripcion> ";
                    strXml += "<cantidad>" + objUti.getBigDecimalValueAt(arlDatDetGuiRem, i, INT_ARL_DAT_DET_GUI_REM_CAN) + "</cantidad> ";
                    strXml += "</detalle> ";
                 }

                 strXml += "</detalles> ";
                 strXml +=   "</destinatario> ";
                 strXml += "</destinatarios> ";

                 blnFound = false;
                 strObs1 = "";
                 strObs2 = "";

                 if (arlDatCabGuiRem.size() > 0)
                 {  strObs1 = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_OBS1);
                    strObs2 = objUti.getStringValueAt(arlDatCabGuiRem, 0, INT_ARL_DAT_CAB_GUI_REM_OBS2);
                    strObs1 = strObs1.trim();
                    strObs2 = strObs2.trim();
                    if (!strObs1.equals("") || !strObs2.equals(""))
                       blnFound = true; //Si se ingreso alguna observacion en algunos de los campos 'Observacion' de la Guia de remision
                 }

                 if (blnFound == true)
                 {  strXml += "<infoAdicional> ";
                    
                    if (!strObs1.equals(""))
                    {  if (strObs1.length() > 300)
                       {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                          //va a haber un error al momento de enviar el XML al SRI.
                          strObs1 = strObs1.substring(0, 300);
                       }
                       strXml += "<campoAdicional nombre=\"Observacion1\">" + strObs1 + "</campoAdicional> ";
                    }
                    
                    if (!strObs2.equals(""))
                    {  if (strObs2.length() > 300)
                       {  //Se debera truncar el contenido porque el Servicio Web del SRI solo permite como maximo 300 caracteres y si no se trunca, 
                          //va a haber un error al momento de enviar el XML al SRI.
                          strObs2 = strObs2.substring(0, 300);
                       }
                       strXml += "<campoAdicional nombre=\"Observacion2\">" + strObs2 + "</campoAdicional> ";
                    }
                    
                    strXml += "</infoAdicional> ";
                 }
              
                 strXml += "</guiaRemision>";
                 xml.println(strXml);
                 
                 arlRegDetResWS = new ArrayList();
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "C");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
                 arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, strClvAcc);
                 arlResWS.add(arlRegDetResWS);

                 rst.close();
                 rst = null;
                 stm.close();
                 stm = null;
                 xml.println();
                 xml.close();
                 
                 strMsj =  "co_emp = " + intCodEmp + "; co_loc = " + intCodLoc + "; co_tipDoc = " + intCodTipDoc + "; co_doc = " + intCodDoc;
                 strMsj += "; num_guia = " + intNumDocGuiRem + "; fe_ing_guia = " + strFecIngGuiRem;
                 
                 if (blnEstImpGuiRem == true)
                 {  //Si Se ejecuto "UPDATE tbm_cabguirem SET st_imp = 'S'
                    strMsj += "; Si se ejecuto UPDATE tbm_cabguirem SET st_imp = 'S'; Motivo = " + strMotEjeUpd;
                 }
                 else
                 {  //No Se ejecuto "UPDATE tbm_cabguirem SET st_imp = 'S'
                    strMsj += "; No se ejecuto UPDATE tbm_cabguirem SET st_imp = 'S'";
                 }
                 
                 escribirArchLog(strMsj);
              } //if (obtenerDatosTagXML(con, intCodEmp, intCodLoc, arlResWS) == true)
              else
                 blnRes = false;
           } //if (con != null)
           else
              blnRes = false;
        } //try
        
        catch(Exception e)
        {  
           e.printStackTrace();
           blnRes = false;
           arlRegDetResWS = new ArrayList();
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
           arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error en la generación del archivo XML. " + e.toString());
           arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
           arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
           arlResWS.add(arlRegDetResWS);
           //strAux =  "Error: " + e.toString() + "\n\n";
           //strAux += "Error en la generación del archivo XML." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
           //strAux += e;
           //mostrarMsgErr(strAux);
        }
        
        return blnRes;
   } // Funcion armarXMLGuiaRemision()
   
   /**
    * Este metodo se encarga de asignar la clave de contingencia a una Guia de remision.
    * @param con Conexion con la base de datos.
    * @param intCodEmp Codigo de empresa.
    * @param intCodLoc Codigo del local.
    * @param intCodTipDoc Codigo de tipo de documento.
    * @param intCodDoc Codigo del documento.
    * @param arlResWS Arreglo de tipo Array List que devuelve 4 valores, todos de tipo String:
    * <BR>
    * <BR><I>Indice 0:</I> Estado devuelto por el Servicio Web o por alguna excepcion.
    * <BR>Los estados devueltos son:
    * <BR>E: Errores varios: enlace, base de datos, etc. Basicamente almacena la excepción generada.
    * <BR>X: Error en estructura del archivo XML. Se envió el archivo XML pero no pasó el 1er. Servicio Web.
    * <BR>A: El documento fue autorizado.
    * <BR><I>Indice 1:</I> Mensaje devuelto por el Servicio Web o por alguna excepcion.
    * <BR><I>Indice 2:</I> Numero de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 3:</I> Fecha de autorizacion devuelto por el Servicio Web.
    * <BR><I>Indice 4:</I> Estado (S/N) que indica si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA".
    * <BR><I>Indice 5:</I> Clave de acceso del comprobante XML.
    * @return true: El metodo se ejecuto con exito.
   * <BR>false: En el caso contrario.
   */
   public boolean setClaveAccesoaGuiRem (Connection con, String strPrmNomEmp, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      String strRutFilGuiRem, strRutFilLog;
      
      try
      {
         arlResWS.clear();
         strRutFilGuiRem = "";
         
         if (System.getProperty("os.name").equals("Linux"))
         {  strRutFilGuiRem = "/Zafiro/Digitales/Compras/ZafCom23/" + strPrmNomEmp + "/Con_PenAut";
            strRutFilLog = "/Zafiro/logs/Servicios/ZafSer14/";
         }
         else
         {  strRutFilGuiRem = "C:\\Zafiro\\Digitales\\Compras\\ZafCom23\\" + strPrmNomEmp + "\\Con_PenAut";
            strRutFilLog = "C:\\Zafiro\\logs\\Servicios\\ZafSer14\\";
         }
         
         System.out.println("\n" + strPrmNomEmp + " => Inicio de proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         crearArchivoLog(strRutFilLog);
         
         if (armarXMLGuiaRemision(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, strRutFilGuiRem, arlResWS) == true)
         {  if (firmarArch_p12(intCodEmp, strRutNomFilGuiRem, arlResWS) == true)
            {  /* Todo OK */
               updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
            else
            {  blnRes = false;
               updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
            }
         }
         else
         {  blnRes = false;
            updateCamposFacEle(con, "tbm_cabguirem", intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, arlResWS);
         }
         
         System.out.println("\n" + strPrmNomEmp + " => Fin de proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
      } //try
      
      catch(Exception e)
      {  blnRes = false;
         arlRegDetResWS = new ArrayList();
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST, "E");
         arlRegDetResWS.add(INT_ARL_DAT_RES_MSJ, "Error al asignar la clave de contingencia. " + e.toString());
         arlRegDetResWS.add(INT_ARL_DAT_RES_NUM_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_FEC_AUT, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_EST_CLV_ACC_REG, "");
         arlRegDetResWS.add(INT_ARL_DAT_RES_CLV_ACC, "");
         arlResWS.add(arlRegDetResWS);
         System.out.println("Error en proceso de G/R: Emp = " + intCodEmp + "; Loc = " + intCodLoc + "; TipDoc = " + intCodTipDoc + "; Doc = " + intCodDoc);
         //strAux =  "Error: " + e.toString() + "\n\n";
         //strAux += "No se pudo obtener la autorizacion del SRI." + "\n" + "Revise los datos, haga las correcciones necesarias y vuelva a intentarlo." + "\n" + "Si el problema persiste comuníquelo a su administrador del sistema.";
         //mostrarMsgErr(strAux);
      }
 
   return blnRes;
   } //Metodo setClaveAccesoaGuiRem()
   
   private void crearArchivoLog(String strRutFilLog)
   {
      java.util.Date objFec = new java.util.Date();
      Calendar fecha = Calendar.getInstance();
      int intAni;
      String strAux, strNomFilHis, strMes, strDia;
      
      intAni = (objFec.getYear() + 1900);
      strMes = String.format("%02d", (objFec.getMonth() + 1));
      strDia = String.format("%02d", fecha.get(Calendar.DAY_OF_MONTH));
      strNomFilHis = strRutFilLog + intAni + "-" + strMes + "-" + strDia + ".txt";
      //System.out.println("NOMBRE ARCHIVO " + strNomFilHis);
      try
      {
          File fLogImp = new File(strNomFilHis);
          
          if (!fLogImp.exists()) {
              fLogImp.createNewFile();
          }
          
          strNomFilLog = strNomFilHis;            
      }
      
      catch(Exception e)
      {
         strAux = "Error en la funcion crearArchivoLog. " + e.toString();
         //System.out.println(strAux);
      }
   } //Funcion crearArchivoLog()
   
   private void escribirArchLog(String strMsj)
   {
      String strAux, strHora, strMin, strSeg;
      
      try
      {
         Calendar fecha = Calendar.getInstance();
         strHora = String.format("%02d", fecha.getTime().getHours());
         strMin = String.format("%02d", fecha.getTime().getMinutes());
         strSeg = String.format("%02d", fecha.getTime().getSeconds());
         strHora = "Hora_creacion_XML = " + strHora + ":" + strMin + ":" + strSeg;
         FileWriter writer = new FileWriter(strNomFilLog, true);
         writer.write(strHora + "; " + strMsj + "\n");
         writer.flush();
         writer.close();
      }
      
      catch(Exception e)
      {
         strAux = "Error en la funcion escribirArchLog. " + e.toString();
         //System.out.println(strAux);
      }
   } //Funcion escribirArchLog()
   
   private String getTipIdeTransportista (String strRucTra)
   {
      String strRes = "00", strUlt3Dig;
      
      try
      {
         if (strRucTra.length() == 13)
         {  //Si la longitud es 13, es RUC o Consumidor final
            //Ej: strRucTra = "1234567891001"
            strUlt3Dig = strRucTra.substring(10, 13); // Va a extraer "001"
            if (strUlt3Dig.equals("001"))
            {  //Es RUC
               strRes = "04"; //Segun PDF del SRI, el valor de 04 corresponde a RUC. En algun momento se sugiere reemplazar este codigo quemado
            }
            else if (strUlt3Dig.equals("999"))
            {  //Es Consumidor final
               strRes = "07"; //Segun PDF del SRI, el valor de 07 corresponde a Venta a consumidor final. En algun momento se sugiere reemplazar este codigo quemado
            }
            else
               strRes = "08"; //Segun PDF del SRI, el valor de 08 corresponde a Identificacion del exterior. En algun momento se sugiere reemplazar este codigo quemado
         }
         else if (strRucTra.length() == 10 && objUti.isNumero(strRucTra) == true)
            strRes = "05"; //Segun PDF del SRI, el valor de 05 corresponde a Cedula. En algun momento se sugiere reemplazar este codigo quemado
         else
            strRes = "08"; //Segun PDF del SRI, el valor de 08 corresponde a Identificacion del exterior. En algun momento se sugiere reemplazar este codigo quemado
         
         //else
         //   strRes = "09"; //Segun PDF del SRI, el valor de 09 corresponde a Placa. En algun momento se sugiere reemplazar este codigo quemado
      }
      
      catch(Exception e)
      {  
         strRes = "00";
      }
 
   return strRes;
   }
   
   public boolean updateCamposFacEle(Connection con, String strNomTbl, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, ArrayList arlResWS)
   {
      boolean blnRes = true;
      java.sql.Statement stm;
      String strEst, strMsj, strNumAut, strFecAut, strEstClvAccReg, strClvAcc;
      
      try
      {
         stm = con.createStatement();
         
         if (arlResWS.size() > 0)
         {  strEst = objUti.getStringValueAt(arlResWS, 0, 0); //Estado devuelto por el Servicio Web o por alguna excepcion
            strMsj = objUti.getStringValueAt(arlResWS, 0, 1); //Mensaje devuelto por el Servicio Web o por alguna excepcion
            strNumAut = objUti.getStringValueAt(arlResWS, 0, 2); //Num. autorizacion devuelto por el Servicio Web
            strFecAut = objUti.getStringValueAt(arlResWS, 0, 3); //Fecha de autorizacion devuelto por el Servicio Web
            strEstClvAccReg = objUti.getStringValueAt(arlResWS, 0, 4); //Estado usado para indicar si el Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA"
            strClvAcc = objUti.getStringValueAt(arlResWS, 0, 5); //Clave de acceso generada en el XML
            
            if (strMsj.length() > 1200)
            {  //Se debera truncar el mensaje porque el campo en la tabla es un varchar(1200) y si no se trunca, 
               //va a haber un error al momento de ejecutar el UPDATE
               strMsj = strMsj.substring(0, 1200);
            }

            strSQL =  "UPDATE " + strNomTbl + " ";
            strSQL += "SET    st_autFacEle = '" + strEst + "', ";
            strSQL += "       tx_desErrFacEle = '" + strMsj + "', ";
            strSQL += "       tx_claAccFacEle = '" + strClvAcc + "' ";

            if (strEst.equals("A") || strEstClvAccReg.equals("S"))
            {  //A: Si hubo autorizacion del comprobante
               //S: El Servicio Web devolvio el mensaje "CLAVE ACCESO REGISTRADA". De todas maneras, hay que guardar los datos de la autorizacion
               strSQL += ", ";
               strSQL += "tx_numAutFacEle = '" + strNumAut + "', ";
               strSQL += "fe_autFacEle = '" + strFecAut + "' ";
            }

            strSQL += "WHERE co_emp = " + intCodEmp;
            strSQL += "      and co_loc = " + intCodLoc;
            strSQL += "      and co_tipdoc = " + intCodTipDoc;
            strSQL += "      and co_doc = " + intCodDoc;
            stm.executeUpdate(strSQL);
         }
         
         stm.close();
         stm = null;
      } //try
      
      catch(Exception e)
      {
         blnRes = false;
      }
      
      return blnRes;
   } //Metodo updateCamposFacEle()
}