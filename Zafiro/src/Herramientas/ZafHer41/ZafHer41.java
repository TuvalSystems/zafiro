/*
 * ZafHer41.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Herramientas.ZafHer41; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
////import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVcoGP.ZafTblCelEdiButVcoGP;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
////import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import Librerias.ZafUtil.ZafExpoExc;
import Librerias.ZafUtilGP.ZafUtilGP;
import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import com.microsoft.sqlserver.jdbc.SQLServerConnection;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.NumberCell;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 *INT_TBL_DAT_COD_ZAF
 * @author  José Marín 
 */
public class ZafHer41 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    
    private static int INT_ARL_EXC_PRI_ITEM=0; 
    private static int INT_ARL_EXC_PRI_FRE=1;
    private static int INT_ARL_EXC_PRI_STO=2;
    private static int INT_ARL_EXC_PRI_LEA=3;
    private static int INT_ARL_EXC_PRI_MOQ=4;
    private static int INT_ARL_EXC_PRI_COB_MAX=5;
    private static int INT_ARL_EXC_PRI_PES=6; 
    private static int INT_ARL_EXC_PRI_PROVEN=7;
    private static int INT_ARL_EXC_PRI_PROTEO=8;
    private static int INT_ARL_EXC_PRI_DESC=9;
    private static int INT_ARL_EXC_PRI_CODZAF=10;
    private static int INT_ARL_EXC_PRI_CLA = 11;
    private static int INT_ARL_EXC_PRI_CAT = 12;
    private static int INT_ARL_EXC_PRI_LINE = 13;
    private static int INT_ARL_EXC_PRI_MAR = 14;
    private static int INT_ARL_EXC_PRI_CON= 15;
    private static int INT_ARL_EXC_PRI_COM = 16;
    private static int INT_ARL_EXC_PRI_MAT = 17; 
    private static int INT_ARL_EXC_PRI_CAT_COMI_VTA = 18;
    private static int INT_ARL_EXC_PRI_CLA_COMI_VTA = 19;
    private static int INT_ARL_EXC_PRI_LIN_COMI_VTA = 20;
    private static int INT_ARL_EXC_PRI_COM_SUB = 21;
        
    
    private static int INT_ARL_EXC_SEG_ITEM=0; 
    private static int INT_ARL_EXC_SEG_MIN=1;
    private static int INT_ARL_EXC_SEG_MAX=2;
    
    
    private static int INT_ARL_EXC_QUI_ITEM=0; 
    private static int INT_ARL_EXC_QUI_CATVEN=1;
    private static int INT_ARL_EXC_QUI_CATPES=2;
    private static int INT_ARL_EXC_QUI_CATFRE=3;
    private static int INT_ARL_EXC_QUI_CATTUV=4;
    private static int INT_ARL_EXC_QUI_CATCLA=5;
    
    
    
    private static int INT_ARL_TAB_TRE_LIN=0;
    private static int INT_ARL_TAB_TRE_TRES=1; 
    private static int INT_ARL_TAB_TRE_ITEM=2; 
    private static int INT_ARL_TAB_TRE_CHK_PED=3;
    private static int INT_ARL_TAB_TRE_CHK_AGR=4;
    private static int INT_ARL_TAB_TRE_CHK_STO=5;
    private static int INT_ARL_TAB_TRE_EST=6;
    
    private static int INT_ARL_TAB_CUA_LIN=0;
    private static int INT_ARL_TAB_CUA_TRES=1; 
    private static int INT_ARL_TAB_CUA_ITEM=2; 
    private static int INT_ARL_TAB_CUA_AGR=3;
    
    
    
    
    private static int INT_ARL_EXC_CUA_TRES=0;     
    private static int INT_ARL_EXC_CUA_ITEM=1;
 
    private static int INT_ARL_EXC_TRE_TRES=0;     
    private static int INT_ARL_EXC_TRE_ITEM=1;
    
    private static int INT_ARL_TAB_PRI_LIN=0;
    private static int INT_ARL_TAB_PRI_ITEM=1; 
    private static int INT_ARL_TAB_PRI_FRE=2;
    private static int INT_ARL_TAB_PRI_STO=3;
    private static int INT_ARL_TAB_PRI_LEA=4;
    private static int INT_ARL_TAB_PRI_MOQ=5;
    private static int INT_ARL_TAB_PRI_COB_MAX=6;
    private static int INT_ARL_TAB_PRI_PES=7;  
    private static int INT_ARL_TAB_PRI_PROVTA=8;
    private static int INT_ARL_TAB_PRI_PROTEO=9;
    private static int INT_ARL_TAB_PRI_DESC=10;
    private static int INT_ARL_TAB_PRI_CODZAF=11;
    private static int INT_ARL_TAB_PRI_CLA = 12;
    private static int INT_ARL_TAB_PRI_CAT = 13;
    private static int INT_ARL_TAB_PRI_LINE = 14;
    private static int INT_ARL_TAB_PRI_MAR = 15;
    private static int INT_ARL_TAB_PRI_CON= 16;
    private static int INT_ARL_TAB_PRI_COM = 17;
    private static int INT_ARL_TAB_PRI_MAT = 18; 
    private static int INT_ARL_TAB_PRI_CAT_COMI_VTA = 19;
    private static int INT_ARL_TAB_PRI_CLA_COMI_VTA = 20;
    private static int INT_ARL_TAB_PRI_LIN_COMI_VTA = 21;
    private static int INT_ARL_TAB_PRI_COM_SUB = 22;
    
    
    
    private static int INT_ARL_TAB_SEG_LIN=0;
    private static int INT_ARL_TAB_SEG_ITEM=1; 
    private static int INT_ARL_TAB_SEG_BODE=2; 
    private static int INT_ARL_TAB_SEG_MIN=3;
    private static int INT_ARL_TAB_SEG_MAX=4; 
    
    private static int INT_ARL_TAB_QUI_LIN=0;
    private static int INT_ARL_TAB_QUI_ITEM=1;
    private static int INT_ARL_TAB_QUI_CATVEN=2;
    private static int INT_ARL_TAB_QUI_CATPES=3;
    private static int INT_ARL_TAB_QUI_CATFRE=4;
    private static int INT_ARL_TAB_QUI_CATTUV=5;
    private static int INT_ARL_TAB_QUI_CLASE=6;
    
    
   
    
    private static final int INT_TBL_DAT_LIN2=0;
    private static final int INT_TBL_DAT_CHK_SELECCION=1;
    private static final int INT_TBL_DAT_NOMBRE=2;
    
    //Variables  
    private Connection conGP, conTuvalApi;
    private Statement stmGP;
    private ResultSet rstGP;
    private Connection conZAF;    
    private Statement stmZAF;
    private ResultSet rstZAF;   
      
    private ZafParSis objParSis;
    private ZafUtil objUti;                                     //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafUtilGP objUtiGP;    
    private ZafTblMod objTblMod1;
    private ZafTblMod objTblMod2;
    private ZafTblMod objTblMod3;    
    private ZafTblMod objTblMod4;        
    private ZafTblMod objTblMod5;
    private ZafColNumerada objColNum1;
    private ZafColNumerada objColNum2;
    private ZafColNumerada objColNum3;
    private ZafColNumerada objColNum4;    
    private ZafColNumerada objColNum5;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuPri;
//////    private ZafMouMotAda1 objMouMotAda1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
////    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenBut objTblCelRenBut;                     //Render: Presentar JButton en JTable.
    private ZafTblModLis objTblModLis1;                           //Detectar cambios de valores en las celdas.
    private ZafTblModLis objTblModLis2;                           //Detectar cambios de valores en las celdas.    
    private ZafTblModLis objTblModLis3;                           //Detectar cambios de valores en las celdas.    
    private ZafTblModLis objTblModLis4;                           //Detectar cambios de valores en las celdas.        
    private ZafTblModLis objTblModLis5;                           //Detectar cambios de valores en las celdas.       

    private ZafTblOrd objTblOrd1;
    private ZafTblOrd objTblOrd2;
    private ZafTblOrd objTblOrd3;    
    private ZafTblOrd objTblOrd4;        
    private ZafTblOrd objTblOrd5;
    private ZafTblBus objTblBus;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafVenCon vcoMar;
    private ZafVenCon vcoCom;
    private ZafVenCon vcoCho;
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.util.Date datFecAuxZAF;                           //Auxiliar: Para almacenar fechas.    
    private ZafThreadGUI objThrGUI;

    private boolean blnHayCam;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. 
    private Vector vecDat1, vecReg1, vecCab1, vecAux1;
    private Vector vecDat2, vecReg2, vecCab2, vecAux2;    
    private Vector vecDat3, vecReg3, vecCab3, vecAux3;    
    private Vector vecDat4, vecReg4, vecCab4, vecAux4;        
    private Vector vecDat5, vecReg5, vecCab5, vecAux5; //ABC 
    private Vector vecTipVeh;
    
    private String strSQLGP, strAux;
    private String strSQLZAF;    
    private String strCodMar, strNomMar;                       //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                       //Contenido del campo al obtener el foco.
    
    private String strPrg = "ZafHer41";
    
    private String[] arrNomColExcel;
    private List<String> lstNoProcesados = new ArrayList<>();
    private boolean huboErrores = false;
    
    private ZafVenConItmGP01 objVenConItm01GP;
    
    private ZafTblMod objTblModTipo;
    private ZafTblCelRenChk objTblCelRenChk;    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas. 
    private ZafThGuarGUI objThrGuaGUI;
    private String version="v0.5";
    private ZafHisTblBasDat objHisTblBasDat;  
    private ZafHisTblBasDatGP objHisTblBasDatGP;
     
    private String[][] ColumnasExcel;
    
    private String [][] columnasEsperadas = { {"FRECUENCIA"},{"STOCKBASE"},{"LEADTIME"},{"MOQ"},{"COBMAX"},{"PESO"},{"DESCRIPCION"},
        {"PROMVTA"},{"PROTEO"},{"CODZAF"},{"CATECOMISION"} };
    
    String strCodItmDynBus="";
    private ArrayList arlDatExcelVal, arlRegExcelVal;
    private int INT_EXC_PRI_COD_ITM=0;
    private int INT_EXC_PRI_BLN=1;    
    private int INT_EXC_SEG_COD_ITM=0;
    private int INT_EXC_SEG_BLN=1;   
    private int INT_EXC_TRE_COD_ITM=0;
    private int INT_EXC_TRE_BLN=1; 
    private int INT_EXC_CUA_COD_ITM=0;
    private int INT_EXC_CUA_BLN=1; 
    private int INT_EXC_QUI_COD_ITM=0;
    private int INT_EXC_QUI_BLN=1;
    
    
    private String strSql="";
    private boolean blnResNO=false;

    
    private int indTab =0;
    private int validaPes =0;
    
    private ArrayList arlDatITEMDET,arlRegITEMDET;    
    private static final int INT_ARL_CON_ITEM=0;  
    private static final int INT_ARL_CON_TRES=1;
    private static final int INT_ARL_CON_MOT=2;
    
    private boolean blnEje=false;    
    private ZafExpoExc objExcel; 
    
    
    private boolean blnHayErroresCarga = false;
    
    
    private int colFre;
    private int colSto;
    private int colLea;
    private int colMoq;
    private int colCob;
    private int colPes;
    private int colProVta;
    private int colProTeo;
    private int colDes;
    private int colCodZaf;
    private int colCla;
    private int colCat;
    private int colLin;
    private int colMar;
    private int colCon;
    private int colCom;
    private int colMat;
    
    private int colCatComi;
    private int colLinComi;
    private int colClaComi;
    private int colSubCom;
    
    private int colCatVen, colCatPes, colCatFre, colCatTuv, colClase;
    
    private java.util.HashMap<String, Integer> mapUsuarios = new java.util.HashMap<String, Integer>();
  
    
    
    private boolean ingresarDatosCargadosAtributosItem()
    {
        boolean blnRes = true;
             
        huboErrores = false;
        lstNoProcesados.clear();

        Connection conGP = null;
        Connection conPg = null;
        Connection conTuvalApi = null;
        
        Statement stmGP = null;
        Statement stmPg = null;
        Statement stmTuvalApi = null;
        String strUpdateItems = "", strFecUltMod;

        try {
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());             
            conPg = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
            conTuvalApi = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(),objParSis.getUsuarioBaseDatos_TuvalApi(),objParSis.getClaveBaseDatos_TuvalApi());
            
            
            if (conGP != null && conPg != null && conTuvalApi != null ) {

                conGP.setAutoCommit(false);
                stmGP = conGP.createStatement(); 
                
                conPg.setAutoCommit(false);
                stmPg = conPg.createStatement(); 
                
                conTuvalApi.setAutoCommit(false);
                stmTuvalApi = conTuvalApi.createStatement();
                              
                              
                Map<String, String> mapItemReal = cargarMapaItems(stmGP); 
                Map<String, Integer> mapMaxHis = obtenerMaxHistorico(stmGP);

                Map<String, Map<String, Object>> mapDatHist = obtenerHistorialItems(null);

                List<String> lstItemsExcel = obtenerItemsExcel(mapItemReal); 
                
                //VALIDACION DE AGRUPACION SOLO PARA MOQ Y FRE DICE CHIKI
                boolean blnValidaAgrupacion = false;
                
                for (int i = 0; i < objTblMod1.getRowCountTrue(); i++)
                {
                    Map<String, Object> mapValores = obtenerValoresFila(i);

                    Double fre = (Double) mapValores.get("fre");
                    Double moq = (Double) mapValores.get("moq");

                    if (fre != null || moq != null)
                    {
                        blnValidaAgrupacion = true;
                        break;
                    }
                }
                                
                Map<String, String> mapAgrupados =  new HashMap<String, String>();
                if (blnValidaAgrupacion)
                {
                    mapAgrupados = obtenerItemsConAgrupacion(lstItemsExcel);
                }       
                               
                int intTotReg = objTblMod1.getRowCountTrue();

                int contProcesados = 0;

                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intTotReg);
                pgrSis.setValue(0);

                for (int i = 0; i < objTblMod1.getRowCountTrue(); i++)
                {
                    String strCodRaw = "";
                    String strItemReal = "";

                    try
                    {
                        strCodRaw = objTblMod1.getValueAt(i, INT_ARL_TAB_PRI_ITEM)
                                .toString().trim().toUpperCase();

                        contProcesados++;

                        lblMsgSis.setText("Procesando item " + contProcesados
                                + " de " + intTotReg + "...");

                        pgrSis.setValue(contProcesados);

                        strItemReal = mapItemReal.get(strCodRaw);

                    if (strItemReal == null || strItemReal.trim().equals("")) {
                        continue;
                    }

                    String str_cod = objUti.codificar(strCodRaw);                   
                                                           
                    Map<String, Object> mapValores = obtenerValoresFila(i);
                    
                    //para max co_his
                    String txItm = strItemReal;
                    Integer intMaxHis = mapMaxHis.get(txItm);
                    if (intMaxHis == null)
                    {
                        intMaxHis = 1;
                    }
                    else
                    {
                        intMaxHis++;
                    }
                    Double fre = (Double) mapValores.get("fre");
                    Double sto = (Double) mapValores.get("sto");
                    Double lea = (Double) mapValores.get("lea");
                    Double moq = (Double) mapValores.get("moq");
                    Double cob = (Double) mapValores.get("cob");                    
                    Double pes = (Double) mapValores.get("pes");
                    BigDecimal provta = (BigDecimal) mapValores.get("provta");
                    BigDecimal proteo = (BigDecimal) mapValores.get("proteo");                    
                    String descripcion = (String) mapValores.get("descripcion");                    
                    String codzaf = (String) mapValores.get("codzaf");
                    String cla = (String) mapValores.get("cla");
                    String cat = (String) mapValores.get("cat");
                    String lin = (String) mapValores.get("lin");
                    String mar = (String) mapValores.get("mar");
                    String con = (String) mapValores.get("con");
                    String com = (String) mapValores.get("com");
                    String mat = (String) mapValores.get("mat");  
                    
                    String catcomi = (String) mapValores.get("catcomi");                    
                    String clacomi = (String) mapValores.get("clacomi");
                    String lincomi = (String) mapValores.get("lincomi");
                    //String subcom = (String) mapValores.get("subcom");
                    
                    // VALIDAR AGRUPACIÓN SOLO PARA MOQ Y FRE DICE CHIKI 
                    if (validarAgrupacion(mapAgrupados,strItemReal,strCodRaw,fre,moq)) {
                        continue;
                    } 
                    
                    String strSet = armarCamposUpdate(fre,sto,lea,moq,cob,pes,provta,proteo,descripcion,codzaf,
                            cla,cat,lin,mar,con,com,mat,catcomi,clacomi,lincomi);                          
//                    DESCOMENTAR CUANDO YA EXISTAN LOS NUEVOS EXTENDER EN TBM_INV Y TBH_INV
                    if (strSet.equals("")) {
                        continue;
                    }
                    
                    //provisional hasta que existan estos 4 en tbh_inv y tbm_inv
                    //boolean blnSoloExtender = catcomi != null || clacomi != null || lincomi != null || subcom != null;

//                    if (strSet.equals("") && !blnSoloExtender)
//                    {
//                        continue;
//                    }  
//                    ////provisional hasta que existan estos 4 en tbh_inv y tbm_inv
                    
                    String strUsuario = getNombreUsuarioModificacion();
                    
                    if (!strSet.equals(""))
                    {
                    
                    String sql = "UPDATE Dynamics2021..tbm_inv SET "
                            + strSet + " "
                            + ", tx_usrmod = " + objUti.codificar(strUsuario)
                            + ", fe_ultmod = CURRENT_TIMESTAMP"
                            + ", tx_proMod = " + objUti.codificar("ZafHer41")
                            + " WHERE tx_codalt2 = " + str_cod + "; "
                            
                            +"INSERT INTO Dynamics2021..tbh_inv ("
                            + "co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, "
                            + "tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, co_grpimp, tx_nomitming, st_revtec, "
                            + "st_itmstk, tx_obs1, tx_obs2, nd_proAnuVenMan, nd_stkBas, nd_tra, nd_fre, "
                            + "nd_punParMin, nd_punParMax, nd_moq, nd_exc, nd_proVenTeo, st_reg, nd_porIvaVen, "
                            + "co_grpinv, co_detGrpInv, co_grpcom, co_detgrpcom, co_tipcla, nd_proVenMenAnt, "
                            + "tx_uniMed, nd_pes, tx_atrLin, tx_atrcla, tx_atrMar, tx_atrMat, tx_atrCom, "
                            + "tx_atrSubCom, tx_atrCon, tx_atrSubCon, tx_atrCat, nd_eje, nd_FacAju,tx_atrCatcom, tx_atrlincom,tx_atrclacom,tx_proMod "
                            + ") "
                            + "SELECT "
                            + intMaxHis + ", "                            
                            + "tx_itm, "
                            + "nd_marUti, "
                            + "nd_canMaxVen, "
                            + "nd_facCosUni, "
                            + objUti.codificar(strUsuario)
                            + ", CURRENT_TIMESTAMP, "

                            + (codzaf != null ? objUti.codificar(codzaf) : "tx_codalt") + ", "
                            + (descripcion != null ? objUti.codificar(descripcion) : "tx_nomitm") + ", "
                            + "tx_codalt2, "
                            + "nd_preVta1, "
                            + "co_grpimp, "
                            + "tx_nomitming, "
                            + "st_revtec, "
                            + "st_itmstk, "
                            + "tx_obs1, "
                            + "tx_obs2, "

                            // VALORES FINALES
                            + (provta != null ? provta : "nd_proAnuVenMan") + ", "
                            + (sto != null ? sto : "nd_stkBas") + ", "
                            + (lea != null ? lea : "nd_tra") + ", "
                            + (fre != null ? fre : "nd_fre") + ", "

                            + "nd_punParMin, "

                            + (cob != null ? cob : "nd_punParMax") + ", "
                            + (moq != null ? moq : "nd_moq") + ", "

                            + "nd_exc, "

                            + (proteo != null ? proteo : "nd_proVenTeo") + ", "

                            + "st_reg, "
                            + "nd_porIvaVen, "
                            + "co_grpinv, "
                            + "co_detGrpInv, "
                            + "co_grpcom, "
                            + "co_detgrpcom, "
                            + "co_tipcla, "
                            + "nd_proVenMenAnt, "
                            + "tx_uniMed, "
                            + (pes != null ? pes : "nd_pes") + ", "

                            +(lin != null ? objUti.codificar(lin) : "tx_atrLin") + ", "
                            +(cla != null ? objUti.codificar(cla) : "tx_atrCla") + ", "
                            +(mar != null ? objUti.codificar(mar) : "tx_atrMar") + ", "
                            +(mat != null ? objUti.codificar(mat) : "tx_atrMat") + ", "
                            +(com != null ? objUti.codificar(com) : "tx_atrCom") + ", "
                            + "tx_atrSubCom, "
                            +(con != null ? objUti.codificar(con) : "tx_atrCon") + ", "
                            + "tx_atrSubCon, "
                            +(cat != null ? objUti.codificar(cat) : "tx_atrCat") + ", "
                            + "nd_eje, "
                            + "nd_FacAju, "
                            +(catcomi != null ? objUti.codificar(catcomi) : "tx_atrCatcom") + ", "
                            +(lincomi != null ? objUti.codificar(lincomi) : "tx_atrlincom") + ", "
                            +(clacomi != null ? objUti.codificar(clacomi) : "tx_atrclacom") + ", "
                            + objUti.codificar("ZafHer41") + " "
                            
                            + "FROM Dynamics2021..tbm_inv "
                            + "WHERE tx_codalt2 = " + str_cod;                            

                    System.out.println("tbh: "+sql);
                    stmGP.executeUpdate(sql);   
                }                         
                    // extender

                    if (pes != null)
                        actualizaExtenderPeso(conGP,strItemReal,179,pes,strUsuario,str_cod);

                    if (cat != null)
                        actualizaOtrosExtender(conGP,strItemReal,206,cat,strUsuario,"Categoria",str_cod);

                    if (cla != null)
                        actualizaOtrosExtender(conGP,strItemReal,208,cla,strUsuario,"Clase",str_cod);

                    if (lin != null)
                        actualizaOtrosExtender(conGP,strItemReal,207,lin,strUsuario,"Linea",str_cod);

                    if (mat != null)
                        actualizaOtrosExtender(conGP,strItemReal,120,mat,strUsuario,"Material",str_cod);

                    if (mar != null)
                        actualizaOtrosExtender(conGP,strItemReal,119,mar,strUsuario,"Marca",str_cod);

                    if (con != null)
                        actualizaOtrosExtender(conGP,strItemReal,203,con,strUsuario,"Control",str_cod);

                    if (com != null)
                        actualizaOtrosExtender(conGP,strItemReal,199,com,strUsuario,"Compras",str_cod);  
                    
                    if (catcomi != null){
                        
                        actualizaOtrosExtender(conGP,strItemReal,209,catcomi,strUsuario,"CateComision",str_cod);  
                    }
                    
                    if (clacomi != null)
                        actualizaOtrosExtender(conGP,strItemReal,211,clacomi,strUsuario,"ClaseComision",str_cod);
                    
                    if (lincomi != null)
                        actualizaOtrosExtender(conGP,strItemReal,210,lincomi,strUsuario,"LineaComision",str_cod);
                    
                    
                    // ACTUALIZA TABLA tbl_ItemsClasiAtrib
                    actualizaItemsClasiAtrib(stmGP,strItemReal,str_cod,pes,mat,mar,con,com,cat,lin,cla,catcomi,lincomi,clacomi);

                    // ACTUALIZA TABLA MYSQL gp_articulos
                    actualizaGpArticulosTuvalApi(stmTuvalApi,strCodRaw,descripcion,mar,mat,pes,cat,lin,cla);
                    
                    String strSetPg =  armarCamposUpdatePg(pes, descripcion,codzaf,provta);
                    if (!strSetPg.equals("")) {
                        String sqlPg =
                            "UPDATE tbm_inv "
                            + "SET " + strSetPg + " "
                            + "WHERE co_emp IN (0,1) " 
                            + "AND tx_codalt2 = " + str_cod;

                       //System.out.println(sqlPg);
                       stmPg.executeUpdate(sqlPg);                     
                    }
                    
                    String strSetIV = armarCamposUpdateIV00101(descripcion,codzaf);

                if (!strSetIV.equals(""))
                {
                    String sqlIV =
                        "UPDATE Tuval..iv00101 "
                        + "SET " + strSetIV + " "
                        + "WHERE ITEMNMBR = "
                        + objUti.codificar(strItemReal);

                    //System.out.println(sqlIV);

                    stmGP.executeUpdate(sqlIV);
                }

                        /*
                         * IMPORTANTE:
                         * Se confirma inmediatamente el item actual.
                         * Así SQL Server libera los bloqueos antes de
                         * comenzar el siguiente item.
                         */
                        conGP.commit();

                        /*
                         * mapMaxHis corresponde al historial de SQL Server.
                         * Se actualiza después de confirmar GP.
                         */
                        mapMaxHis.put(txItm, intMaxHis);

                        conPg.commit();
                        conTuvalApi.commit();
                    }
                    catch (Exception eItem)
                    {
                        try
                        {
                            if (conGP != null)
                                conGP.rollback();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        try
                        {
                            if (conPg != null)
                                conPg.rollback();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        try
                        {
                            if (conTuvalApi != null)
                                conTuvalApi.rollback();
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        huboErrores = true;

                        lstNoProcesados.add(
                                "Item: " + strCodRaw
                                + " - Error: " + eItem.getMessage()
                        );

                        eItem.printStackTrace();

                        // Continúa con el siguiente item.
                        continue;
                    }
                }//fin for
                
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);                
                
                mostrarResumenProceso();                
                
                objTblMod1.removeAllRows();
                objTblMod1.fireTableDataChanged();             
                
                resetearIndicesColumnas();
                ocultarColumnasNoUsadas();                
                
                /*if (!lstNoProcesados.isEmpty()) {
                    txtLog.setText(msg);
                    tabFrm.setSelectedComponent(panDatLog);
                }*/
            }

        } catch (Exception e) {           
            blnRes = false;
            try {
                if (conGP != null) conGP.rollback();
                if (conPg != null) conPg.rollback();
                if (conTuvalApi != null) conTuvalApi.rollback();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            objUti.mostrarMsgErr_F1(this, e);
        } finally {

            try { if (stmGP != null) stmGP.close(); } catch (Exception e) {}
            try { if (stmPg != null) stmPg.close(); } catch (Exception e) {}
            try { if (stmTuvalApi != null) stmTuvalApi.close(); } catch (Exception e) {}
            
            try { if (conGP != null) conGP.close(); } catch (Exception e) {}
            try { if (conPg != null) conPg.close(); } catch (Exception e) {}
            try { if (conTuvalApi != null) conTuvalApi.close(); } catch (Exception e) {}
        }
        return blnRes;
    }
    
    
//    private boolean ingresarDatosCargadosAtributosItem()
//    {
//        boolean blnRes = true;
//             
//        huboErrores = false;
//        lstNoProcesados.clear();
//
//        Connection conGP = null;
//        Connection conPg = null;
//        Connection conTuvalApi = null;
//        
//        Statement stmGP = null;
//        Statement stmPg = null;
//        Statement stmTuvalApi = null;
//        String strUpdateItems = "", strFecUltMod;
//
//        try {
//            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());             
//            conPg = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
//            conTuvalApi = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(),objParSis.getUsuarioBaseDatos_TuvalApi(),objParSis.getClaveBaseDatos_TuvalApi());
//            
//            
//            if (conGP != null && conPg != null && conTuvalApi != null ) {
//
//                conGP.setAutoCommit(false);
//                stmGP = conGP.createStatement(); 
//                
//                conPg.setAutoCommit(false);
//                stmPg = conPg.createStatement(); 
//                
//                conTuvalApi.setAutoCommit(false);
//                stmTuvalApi = conTuvalApi.createStatement();
//                              
//                              
//                Map<String, String> mapItemReal = cargarMapaItems(stmGP); 
//                Map<String, Integer> mapMaxHis = obtenerMaxHistorico(stmGP);
//
//                Map<String, Map<String, Object>> mapDatHist = obtenerHistorialItems(null);
//
//                List<String> lstItemsExcel = obtenerItemsExcel(mapItemReal); 
//                
//                //VALIDACION DE AGRUPACION SOLO PARA MOQ Y FRE DICE CHIKI
//                boolean blnValidaAgrupacion = false;
//                
//                for (int i = 0; i < objTblMod1.getRowCountTrue(); i++)
//                {
//                    Map<String, Object> mapValores = obtenerValoresFila(i);
//
//                    Double fre = (Double) mapValores.get("fre");
//                    Double moq = (Double) mapValores.get("moq");
//
//                    if (fre != null || moq != null)
//                    {
//                        blnValidaAgrupacion = true;
//                        break;
//                    }
//                }
//                                
//                Map<String, String> mapAgrupados =  new HashMap<String, String>();
//                if (blnValidaAgrupacion)
//                {
//                    mapAgrupados = obtenerItemsConAgrupacion(lstItemsExcel);
//                }       
//                               
//                int intTotReg = objTblMod1.getRowCountTrue();
//
//                int contProcesados = 0;
//
//                pgrSis.setMinimum(0);
//                pgrSis.setMaximum(intTotReg);
//                pgrSis.setValue(0);
//
//                //aqui
//                for (int i = 0; i < objTblMod1.getRowCountTrue(); i++)
//                {                                                           
//                    String strCodRaw = objTblMod1.getValueAt(i, INT_ARL_TAB_PRI_ITEM).toString().trim().toUpperCase();
//                    
//                    contProcesados++; 
//                    
//                    lblMsgSis.setText("Procesando item "+ contProcesados+ " de "+ intTotReg+ "...");
//                    
//                    pgrSis.setValue(contProcesados);                  
//                    
//                    String strItemReal = mapItemReal.get(strCodRaw);
//
//                    if (strItemReal == null || strItemReal.trim().equals("")) {
//                        continue;
//                    }
//
//                    String str_cod = objUti.codificar(strCodRaw);                   
//                                                           
//                    Map<String, Object> mapValores = obtenerValoresFila(i);
//                    
//                    //para max co_his
//                    String txItm = strItemReal;
//                    Integer intMaxHis = mapMaxHis.get(txItm);
//                    if (intMaxHis == null)
//                    {
//                        intMaxHis = 1;
//                    }
//                    else
//                    {
//                        intMaxHis++;
//                    }
//                    mapMaxHis.put(txItm, intMaxHis);                   
//                    
//
//                    Double fre = (Double) mapValores.get("fre");
//                    Double sto = (Double) mapValores.get("sto");
//                    Double lea = (Double) mapValores.get("lea");
//                    Double moq = (Double) mapValores.get("moq");
//                    Double cob = (Double) mapValores.get("cob");                    
//                    Double pes = (Double) mapValores.get("pes");
//                    BigDecimal provta = (BigDecimal) mapValores.get("provta");
//                    BigDecimal proteo = (BigDecimal) mapValores.get("proteo");                    
//                    String descripcion = (String) mapValores.get("descripcion");                    
//                    String codzaf = (String) mapValores.get("codzaf");
//                    String cla = (String) mapValores.get("cla");
//                    String cat = (String) mapValores.get("cat");
//                    String lin = (String) mapValores.get("lin");
//                    String mar = (String) mapValores.get("mar");
//                    String con = (String) mapValores.get("con");
//                    String com = (String) mapValores.get("com");
//                    String mat = (String) mapValores.get("mat");  
//                    
//                    String catcomi = (String) mapValores.get("catcomi");                    
//                    String clacomi = (String) mapValores.get("clacomi");
//                    String lincomi = (String) mapValores.get("lincomi");
//                    //String subcom = (String) mapValores.get("subcom");
//                    
//                    // VALIDAR AGRUPACIÓN SOLO PARA MOQ Y FRE DICE CHIKI 
//                    if (validarAgrupacion(mapAgrupados,strItemReal,strCodRaw,fre,moq)) {
//                        continue;
//                    } 
//                    
//                    String strSet = armarCamposUpdate(fre,sto,lea,moq,cob,pes,provta,proteo,descripcion,codzaf,
//                            cla,cat,lin,mar,con,com,mat,catcomi,clacomi,lincomi);                          
////                    DESCOMENTAR CUANDO YA EXISTAN LOS NUEVOS EXTENDER EN TBM_INV Y TBH_INV
//                    if (strSet.equals("")) {
//                        continue;
//                    }
//                    
//                    //provisional hasta que existan estos 4 en tbh_inv y tbm_inv
//                    //boolean blnSoloExtender = catcomi != null || clacomi != null || lincomi != null || subcom != null;
//
////                    if (strSet.equals("") && !blnSoloExtender)
////                    {
////                        continue;
////                    }  
////                    ////provisional hasta que existan estos 4 en tbh_inv y tbm_inv
//                    
//                    String strUsuario = objParSis.getNombreUsuario();
//                    
//                    if (!strSet.equals(""))
//                    {
//                    
//                    String sql = "UPDATE Dynamics2021..tbm_inv SET "
//                            + strSet + " "
//                            + ", tx_usrmod = " + objUti.codificar(strUsuario)
//                            + ", fe_ultmod = CURRENT_TIMESTAMP"
//                            + ", tx_proMod = " + objUti.codificar("ZafHer41")
//                            + " WHERE tx_codalt2 = " + str_cod + "; "
//                            
//                            +"INSERT INTO Dynamics2021..tbh_inv ("
//                            + "co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, "
//                            + "tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, co_grpimp, tx_nomitming, st_revtec, "
//                            + "st_itmstk, tx_obs1, tx_obs2, nd_proAnuVenMan, nd_stkBas, nd_tra, nd_fre, "
//                            + "nd_punParMin, nd_punParMax, nd_moq, nd_exc, nd_proVenTeo, st_reg, nd_porIvaVen, "
//                            + "co_grpinv, co_detGrpInv, co_grpcom, co_detgrpcom, co_tipcla, nd_proVenMenAnt, "
//                            + "tx_uniMed, nd_pes, tx_atrLin, tx_atrcla, tx_atrMar, tx_atrMat, tx_atrCom, "
//                            + "tx_atrSubCom, tx_atrCon, tx_atrSubCon, tx_atrCat, nd_eje, nd_FacAju,tx_atrCatcom, tx_atrlincom,tx_atrclacom,tx_proMod "
//                            + ") "
//                            + "SELECT "
//                            + intMaxHis + ", "                            
//                            + "tx_itm, "
//                            + "nd_marUti, "
//                            + "nd_canMaxVen, "
//                            + "nd_facCosUni, "
//
//                            + objUti.codificar(objParSis.getNombreUsuario())
//                            + ", CURRENT_TIMESTAMP, "
//
//                            + (codzaf != null ? objUti.codificar(codzaf) : "tx_codalt") + ", "
//                            + (descripcion != null ? objUti.codificar(descripcion) : "tx_nomitm") + ", "
//                            + "tx_codalt2, "
//                            + "nd_preVta1, "
//                            + "co_grpimp, "
//                            + "tx_nomitming, "
//                            + "st_revtec, "
//                            + "st_itmstk, "
//                            + "tx_obs1, "
//                            + "tx_obs2, "
//
//                            // VALORES FINALES
//                            + (provta != null ? provta : "nd_proAnuVenMan") + ", "
//                            + (sto != null ? sto : "nd_stkBas") + ", "
//                            + (lea != null ? lea : "nd_tra") + ", "
//                            + (fre != null ? fre : "nd_fre") + ", "
//
//                            + "nd_punParMin, "
//
//                            + (cob != null ? cob : "nd_punParMax") + ", "
//                            + (moq != null ? moq : "nd_moq") + ", "
//
//                            + "nd_exc, "
//
//                            + (proteo != null ? proteo : "nd_proVenTeo") + ", "
//
//                            + "st_reg, "
//                            + "nd_porIvaVen, "
//                            + "co_grpinv, "
//                            + "co_detGrpInv, "
//                            + "co_grpcom, "
//                            + "co_detgrpcom, "
//                            + "co_tipcla, "
//                            + "nd_proVenMenAnt, "
//                            + "tx_uniMed, "
//                            + (pes != null ? pes : "nd_pes") + ", "
//
//                            +(lin != null ? objUti.codificar(lin) : "tx_atrLin") + ", "
//                            +(cla != null ? objUti.codificar(cla) : "tx_atrCla") + ", "
//                            +(mar != null ? objUti.codificar(mar) : "tx_atrMar") + ", "
//                            +(mat != null ? objUti.codificar(mat) : "tx_atrMat") + ", "
//                            +(com != null ? objUti.codificar(com) : "tx_atrCom") + ", "
//                            + "tx_atrSubCom, "
//                            +(con != null ? objUti.codificar(con) : "tx_atrCon") + ", "
//                            + "tx_atrSubCon, "
//                            +(cat != null ? objUti.codificar(cat) : "tx_atrCat") + ", "
//                            + "nd_eje, "
//                            + "nd_FacAju, "
//                            +(catcomi != null ? objUti.codificar(catcomi) : "tx_atrCatcom") + ", "
//                            +(lincomi != null ? objUti.codificar(lincomi) : "tx_atrlincom") + ", "
//                            +(clacomi != null ? objUti.codificar(clacomi) : "tx_atrclacom") + ", "
//                            + objUti.codificar("ZafHer41") + " "
//                            
//                            + "FROM Dynamics2021..tbm_inv "
//                            + "WHERE tx_codalt2 = " + str_cod;                            
//
//                    System.out.println("tbh: "+sql);
//                    stmGP.executeUpdate(sql);   
//                }                         
//                    // extender
//
//                    if (pes != null)
//                        actualizaExtenderPeso(conGP,strItemReal,179,pes,strUsuario,str_cod);
//
//                    if (cat != null)
//                        actualizaOtrosExtender(conGP,strItemReal,206,cat,strUsuario,"Categoria",str_cod);
//
//                    if (cla != null)
//                        actualizaOtrosExtender(conGP,strItemReal,208,cla,strUsuario,"Clase",str_cod);
//
//                    if (lin != null)
//                        actualizaOtrosExtender(conGP,strItemReal,207,lin,strUsuario,"Linea",str_cod);
//
//                    if (mat != null)
//                        actualizaOtrosExtender(conGP,strItemReal,120,mat,strUsuario,"Material",str_cod);
//
//                    if (mar != null)
//                        actualizaOtrosExtender(conGP,strItemReal,119,mar,strUsuario,"Marca",str_cod);
//
//                    if (con != null)
//                        actualizaOtrosExtender(conGP,strItemReal,203,con,strUsuario,"Control",str_cod);
//
//                    if (com != null)
//                        actualizaOtrosExtender(conGP,strItemReal,199,com,strUsuario,"Compras",str_cod);  
//                    
//                    if (catcomi != null){
//                        
//                        actualizaOtrosExtender(conGP,strItemReal,209,catcomi,strUsuario,"CateComision",str_cod);  
//                    }
//                    
//                    if (clacomi != null)
//                        actualizaOtrosExtender(conGP,strItemReal,211,clacomi,strUsuario,"ClaseComision",str_cod);
//                    
//                    if (lincomi != null)
//                        actualizaOtrosExtender(conGP,strItemReal,210,lincomi,strUsuario,"LineaComision",str_cod);
//                    
//                    
//                    // ACTUALIZA TABLA tbl_ItemsClasiAtrib
//                    actualizaItemsClasiAtrib(stmGP,strItemReal,str_cod,pes,mat,mar,con,com,cat,lin,cla,catcomi,lincomi,clacomi);
//
//                    // ACTUALIZA TABLA MYSQL gp_articulos
//                    actualizaGpArticulosTuvalApi(stmTuvalApi,strCodRaw,descripcion,mar,mat,pes,cat,lin,cla);
//                    
//                    String strSetPg =  armarCamposUpdatePg(pes, descripcion,codzaf,provta);
//                    if (!strSetPg.equals("")) {
//                        String sqlPg =
//                            "UPDATE tbm_inv "
//                            + "SET " + strSetPg + " "
//                            + "WHERE co_emp = " + objParSis.getCodigoEmpresa() + " "
//                            + "AND tx_codalt2 = " + str_cod;
//
//                       //System.out.println(sqlPg);
//                       stmPg.executeUpdate(sqlPg);                     
//                    }
//                    
//                    String strSetIV = armarCamposUpdateIV00101(descripcion,codzaf);
//
//                if (!strSetIV.equals(""))
//                {
//                    String sqlIV =
//                        "UPDATE Tuval..iv00101 "
//                        + "SET " + strSetIV + " "
//                        + "WHERE ITEMNMBR = "
//                        + objUti.codificar(strItemReal);
//                    //System.out.println(sqlIV);
//
//                    stmGP.executeUpdate(sqlIV);
//                }
//                    
//                     
//                }//fin for                               
//                                
//                conGP.commit();
//                conPg.commit();
//                conTuvalApi.commit();
//                
//                lblMsgSis.setText("Listo");
//                pgrSis.setValue(0);                
//                
//                mostrarResumenProceso();                
//                
//                objTblMod1.removeAllRows();
//                objTblMod1.fireTableDataChanged();             
//                
//                resetearIndicesColumnas();
//                ocultarColumnasNoUsadas();                
//                
//                /*if (!lstNoProcesados.isEmpty()) {
//                    txtLog.setText(msg);
//                    tabFrm.setSelectedComponent(panDatLog);
//                }*/
//            }
//
//        } catch (Exception e) {           
//            blnRes = false;
//            try {
//                if (conGP != null) conGP.rollback();
//                if (conPg != null) conPg.rollback();
//                if (conTuvalApi != null) conTuvalApi.rollback();
//                
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//
//            objUti.mostrarMsgErr_F1(this, e);
//        } finally {
//
//            try { if (stmGP != null) stmGP.close(); } catch (Exception e) {}
//            try { if (stmPg != null) stmPg.close(); } catch (Exception e) {}
//            try { if (stmTuvalApi != null) stmTuvalApi.close(); } catch (Exception e) {}
//            
//            try { if (conGP != null) conGP.close(); } catch (Exception e) {}
//            try { if (conPg != null) conPg.close(); } catch (Exception e) {}
//            try { if (conTuvalApi != null) conTuvalApi.close(); } catch (Exception e) {}
//        }
//        return blnRes;
//    }
    
    
    
    private boolean ingresarDatosCargadosMinMax()
    {
        boolean blnRes=true;
        java.sql.Connection conGP;
        java.sql.Statement stmGP;
        String strUpdateItems="", strFecUltMod;
        int contador =0;
        String str_cod="",str_bod="";
        double dblmax =0,dblmin=0;
        boolean blnValid=false;
        try{
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conGP!=null){
                conGP.setAutoCommit(false);
                stmGP = conGP.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                //Map<String, Map<String, Object>> mapDatHist = obtenerHistorialItems(strCodItmDynBus);
                for(int i=0;i<objTblMod1.getRowCountTrue();i++)
                {
                     blnValid=false;
                     //str_cod2 =objUti.getStringValueAt(arlDatExcel, i, 0);
                     str_cod =objUti.codificar(objTblMod1.getValueAt(i, INT_ARL_TAB_SEG_ITEM));
                     str_bod =objUti.codificar(objTblMod1.getValueAt(i, INT_ARL_TAB_SEG_BODE));
                     dblmin = Double.parseDouble(objTblMod1.getValueAt(i, INT_ARL_TAB_SEG_MIN).toString()) ;
                     dblmax = Double.parseDouble(objTblMod1.getValueAt(i, INT_ARL_TAB_SEG_MAX).toString()) ;
                     strUpdateItems+="UPDATE Dynamics2021..tbm_invbod set  nd_repMin = "+ dblmin+", nd_repMax = "+dblmax +" where tx_codalt2 "+ str_cod+" and tx_bod ="+str_bod+"; \n";
                     strUpdateItems+="\n UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 SET ORDRPNTQTY="+dblmin+", ORDRUPTOLVL="+dblmax+ " ";
                     strUpdateItems+=" WHERE RCRDTYPE = 2 AND LOCNCODE ="+str_bod+" AND ITEMNMBR IN (  SELECT tx_codalt FROM tbm_inv WHERE tx_codalt2 = "+str_cod+" \n";
                     strUpdateItems+="\n INSERT INTO tbh_maxmininv (tx_codBod, tx_codAlt, co_reg, st_reg, nd_numMesRep, nd_valMin, nd_valMax, nd_stkExc, nd_stkFij, tx_usrIng, fe_ing) ";
                     strUpdateItems+=" VALUES("+str_bod+","+str_cod+", ";
                     strUpdateItems+=" (SELECT CASE WHEN MAX(co_reg) IS NULL THEN 1 ELSE MAX(co_reg)+1 END as co_reg FROM tbh_maxmininv WHERE tx_codBod="+str_bod+" AND ";
                     strUpdateItems+="  tx_codAlt="+str_cod+"), 'A',0,"+dblmin+","+dblmax+",0,0,"+objUti.codificar(objParSis.getNombreUsuario())+",CURRENT_TIMESTAMP) \n";                     
                }
                stmGP.executeUpdate(strUpdateItems);
                
                
                if(blnRes)
                {
                    System.out.println("OK! ");
                    lblMsgSis.setText("Listo...");
                    conGP.commit();
                    mostrarMsgInf("Se modifico la informacion de forma exitosa.");
                }
                else
                    conGP.rollback();
                
                stmGP.close();
                conGP.close();
                conGP=null;
            }
            else{
                blnRes=false;
            }
        }
        catch(java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
     }   
    
    private boolean obtenerNotPedTransito(String nomItem,String itemTerminal,String codAltDesde,String codAltHasta) 
    {
        Connection conLocGP = null;
        CallableStatement cal = null;
        ResultSet rs = null;
        boolean blnRes = false;        

        try {
            conLocGP = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (conLocGP != null) {

                String strSql = "{ call spImp1(?,?,?,?) }";
                cal = conLocGP.prepareCall(strSql);

                cal.setString(1, nomItem);
                cal.setString(2, itemTerminal);
                cal.setString(3, codAltDesde);
                cal.setString(4, codAltHasta);

                boolean hasResults = cal.execute();

                if (hasResults) {
                    rs = cal.getResultSet();

                    // 👉 VALIDACIÓN CLAVE
                    if (rs != null && rs.next()) {
                        blnRes = true;

                        // Si necesitas recorrer desde el inicio luego:
                        rs.beforeFirst();

                        // aquí puedes cargar la tabla si quieres
                        // cargarTabla(rs);
                    }
                }
            }

        } catch (SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (cal != null) cal.close();
                if (conLocGP != null) conLocGP.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return blnRes;
    }



    
private boolean ingresarDatosInactivos()
    {
        boolean blnRes=true;
        java.sql.Connection conGP;
        java.sql.Statement stmGP;
        java.sql.Connection conZaf;
        java.sql.Statement stmZaf;        
        String strUpdateItems="", strFecUltMod;
        int contador =0;
        String str_cod="",str_agr="";
        
        double dblmax =0,dblmin=0;
        boolean blnValid=true;
        blnEje=true;

        try{
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            conZaf=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conGP!=null)
            {
                //conGP.setAutoCommit(false);
                //conZaf.setAutoCommit(false);
                stmGP = conGP.createStatement();
                stmZaf = conZaf.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                //Map<String, Map<String, Object>> mapDatHist = obtenerHistorialItems(strCodItmDynBus);
                arlDatITEMDET= new ArrayList();
                for(int i=0;i<objTblMod3.getRowCountTrue();i++)
                {
                    strSQLGP="";
                    strSQLZAF="";
                    blnValid=true;
                     //str_cod2 =objUti.getStringValueAt(arlDatExcel, i, 0);
                     str_cod =objTblMod3.getValueAt(i, INT_ARL_TAB_TRE_ITEM).toString();
                     if (!obtenerNotPedTransito(objUti.codificar(str_cod),"","",""))
                         objTblMod3.setValueAt(false, i, INT_ARL_TAB_TRE_CHK_PED);
                     else
                     {
                         objTblMod3.setValueAt(true, i, INT_ARL_TAB_TRE_CHK_PED);
                         arlRegITEMDET = new ArrayList();
                         arlRegITEMDET.add(INT_ARL_CON_ITEM,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_ITEM).toString());
                         arlRegITEMDET.add(INT_ARL_CON_TRES,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_TRES).toString());
                         arlRegITEMDET.add(INT_ARL_CON_MOT,"TIENE PEDIDO");
                         arlDatITEMDET.add(arlRegITEMDET);
                         blnValid=false;
                     }
                     str_agr=objUtiGP.validacionItemAgrupacion(objUti.codificar(str_cod));
                     if (str_agr.equals(""))
                         objTblMod3.setValueAt(false, i, INT_ARL_TAB_TRE_CHK_AGR);
                     else
                     {
                         objTblMod3.setValueAt(true, i, INT_ARL_TAB_TRE_CHK_AGR);
                         arlRegITEMDET = new ArrayList();
                         arlRegITEMDET.add(INT_ARL_CON_ITEM,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_ITEM).toString());
                         arlRegITEMDET.add(INT_ARL_CON_TRES,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_TRES).toString());
                         arlRegITEMDET.add(INT_ARL_CON_MOT,"TIENE AGRUPACION");
                         arlDatITEMDET.add(arlRegITEMDET);                         
                         blnValid=false;
                     } 
                     if(ValidaStock(str_cod))
                         objTblMod3.setValueAt(false, i, INT_ARL_TAB_TRE_CHK_STO);
                     else
                     {
                         objTblMod3.setValueAt(true, i, INT_ARL_TAB_TRE_CHK_STO);
                         arlRegITEMDET = new ArrayList();
                         arlRegITEMDET.add(INT_ARL_CON_ITEM,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_ITEM).toString());
                         arlRegITEMDET.add(INT_ARL_CON_TRES,objTblMod3.getValueAt(i,INT_ARL_TAB_TRE_TRES).toString());
                         arlRegITEMDET.add(INT_ARL_CON_MOT,"TIENE STOCK");
                         arlDatITEMDET.add(arlRegITEMDET);                          
                         blnValid=false;
                     }  
                     
                    if(ValidaStockBin(str_cod))
                        blnValid=false;     
                    if (blnValid)
                        objTblMod3.setValueAt('I', i, INT_ARL_TAB_TRE_EST);
                    else
                        objTblMod3.setValueAt('P', i, INT_ARL_TAB_TRE_EST);
                     
                    if (blnValid)
                    {
                         strSQLGP +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set INACTIVE = 1 \n";
                         strSQLGP+= "       ,DEX_ROW_TS ='"+strFecUltMod +"'\n";
                         strSQLGP+= "WHERE ITEMNMBR ="+ objUti.codificar(str_cod) +" ;\n";

                         strSQLGP +="\n UPDATE Dynamics2021..tbm_inv set st_reg ='I' \n";
                         strSQLGP+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' \n";
                         strSQLGP+= "WHERE tx_itm ="+ objUti.codificar(str_cod) +" ;\n";      

                         strSQLGP +="\n DELETE "+ objParSis.getNombreBaseDatosGP()  +"..IV00112  \n";
                         strSQLGP+= "   WHERE ITEMNMBR ="+ objUti.codificar(str_cod) +" ;\n";      

                         strSQLZAF += "\n UPDATE tbm_inv set st_reg ='I' \n";
                         strSQLZAF += "    ,fe_ultmod = '"+strFecUltMod +"' , co_usrmod ="+ getCodigoUsuarioModificacion() +"";
                         strSQLZAF += " WHERE tx_codaltdyn ="+ objUti.codificar(str_cod)+";"; 
                         
                         stmGP.executeUpdate(strSQLGP);
                         stmZaf.executeUpdate(strSQLZAF);
                    }
                    else
                         blnEje =false;
                }
                //if (blnEje)
                    
                
                
                if(!blnEje)
                {
                    int estadoPre,ii=0 ;
                    String strMsgItmUbi="";
                    strMsgItmUbi="<HTML>Se Items que no se inactivaron: <BR><BR>";
                    strMsgItmUbi+="<table BORDER=1><tr><td><b>ITEM</b></td><td><b>MOTIVO</b></td></tr>";
                    for (ii=0; ii<arlDatITEMDET.size();ii++) 
                    { 
                        strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMDET, ii, INT_ARL_CON_ITEM) + "</td>";
                        strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, ii, INT_ARL_CON_MOT) + "</td>";
                        strMsgItmUbi+="</tr>";
                    }
                    strMsgItmUbi+="</table><BR>";
                    //strMsgItmUbi += "No se realizo la inactivación de los items.<BR>";
                    strMsgItmUbi += "Motivo: Stock - Pedidos - Agrupación.";
                    strMsgItmUbi += "</HTML>";
                    mostrarMsgInf(strMsgItmUbi);
                    eliminarRegistrosInactivos();
                    exportarExcel();
                }
                stmGP.close();
                conGP.close();
                conGP=null;
                stmZaf.close();
                conZaf.close();
                conZaf=null;                
            }
            else
                blnRes=false;
        }
        catch(java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
     }  

private boolean ingresarDatosDesagrupacion()//09012026
    {
        boolean blnRes=true;
        java.sql.Connection conGP;
        java.sql.Statement stmGP;
        java.sql.Connection conZaf;
        java.sql.Statement stmZaf;        
        String strUpdateItems="", strFecUltMod;
        int contador =0;
        String str_agr="";
        
        double dblmax =0,dblmin=0;
        boolean blnValid=true;
        blnEje=true;

        try{
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            conZaf=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conGP!=null)
            {
                //conGP.setAutoCommit(false);
                //conZaf.setAutoCommit(false);
                stmGP = conGP.createStatement();
                stmZaf = conZaf.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                //Map<String, Map<String, Object>> mapDatHist = obtenerHistorialItems(strCodItmDynBus);
                arlDatITEMDET= new ArrayList();
                for(int i=0;i<objTblMod4.getRowCountTrue();i++)
                {
                    strSQLGP="";
                    strSQLZAF="";
                    blnValid=true;
                     str_agr =objTblMod4.getValueAt(i, INT_ARL_TAB_CUA_AGR).toString();
                     if (!str_agr.equals("N/A"))
                     {
                        String [] columnasAct = str_agr.toString().split("-");
                        if (columnasAct[1].equals("CABECERA")==true)
                            if (!objUtiGP.anulacionAgrupacionCabecera(Integer.parseInt(columnasAct[0]) ,strFecUltMod))
                            {
                                blnRes=false;
                                break;
                            }
                        else
                        {
                            if (columnasAct[1].equals("UNICO"))
                                if (!objUtiGP.anulacionAgrupacionSoloItem(objUti.codificar(objTblMod4.getValueAt(i,INT_ARL_TAB_CUA_ITEM)) ,strFecUltMod))
                                {
                                    blnRes=false;
                                    break;
                                }
                            else
                            {
                                if (columnasAct[1].equals("ULTIMO"))
                                {
                                    if(!objUtiGP.anulacionAgrupacionItemUltimo(objUti.codificar(objTblMod4.getValueAt(i,INT_ARL_TAB_CUA_ITEM))  ,strFecUltMod))
                                    {
                                        blnRes=false;
                                        break;
                                    }                                    
                                }
                            }
                        }
                     }
                }
                if (blnRes)
                    mostrarMsgInf("Se modifico la informacion de forma exitosa.");
                else
                    mostrarMsgInf("Se tiene problemas al modificar la agrupacion. por favor informar al administrador del sistema...");
                
                stmGP.close();
                conGP.close();
                conGP=null;
                stmZaf.close();
                conZaf.close();
                conZaf=null;                
            }
            else
                blnRes=false;
        }
        catch(java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
     }  


    private boolean ingresarDatosCargadosABC()
    {
        boolean blnRes = true;

        Connection conGP = null;
        Statement stmGP = null;

        List<String> lstErrores = new ArrayList<String>();

        int intProcesadosOk = 0;

        try
        {
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (conGP != null)
            {
                /*
                 * Primero se carga el mapa con autoCommit=true.
                 * Todavía no se abre la transacción manual.
                 */
                stmGP = conGP.createStatement();

                Map<String, String> mapItemReal = cargarMapaItems(stmGP);

                /*
                 * Desde aquí comienzan las modificaciones.
                 */
                conGP.setAutoCommit(false);

                int intTotalRegistros =  objTblMod5.getRowCountTrue();

                pgrSis.setIndeterminate(false);
                pgrSis.setMaximum(intTotalRegistros);
                pgrSis.setValue(0);

                for (int i = 0; i < intTotalRegistros; i++)
                {
                    String strCodRaw = "";

                    try
                    {
                        lblMsgSis.setText("Procesando registro "+ (i + 1)+ " de "+ intTotalRegistros);

                        pgrSis.setValue(i + 1);

                        strCodRaw = objTblMod5.getValueAt(i,INT_ARL_TAB_QUI_ITEM).toString().trim().toUpperCase();

                        /*
                         * ITEM REAL GP.
                         */
                        String strItemReal = mapItemReal.get(strCodRaw);

                        if (strItemReal == null || strItemReal.trim().equals(""))
                        {
                            lstErrores.add(strCodRaw + " - No se encontró el item en tbm_inv.");
                            continue;
                        }

                        /*
                         * CATEGORÍAS.
                         */
                        String valCatVen =  objUti.getStringValueAt(objTblMod5.getData(), i,INT_ARL_TAB_QUI_CATVEN);

                        String valCatPes =  objUti.getStringValueAt(objTblMod5.getData(),i,INT_ARL_TAB_QUI_CATPES);

                        String valCatFre =  objUti.getStringValueAt(objTblMod5.getData(),i,INT_ARL_TAB_QUI_CATFRE);

                        String valCatTuv =  objUti.getStringValueAt(objTblMod5.getData(),i,INT_ARL_TAB_QUI_CATTUV);
                        
                        String valClase =  objUti.getStringValueAt(objTblMod5.getData(),i,INT_ARL_TAB_QUI_CLASE);

                        /*
                         * CÓDIGOS DE NIVEL.
                         */
                        Integer codCatVen =  obtenerCodigoNivel(conGP,valCatVen);

                        Integer codCatPes =  obtenerCodigoNivel(conGP,valCatPes);

                        Integer codCatFre = obtenerCodigoNivel(conGP,valCatFre);

                        Integer codCatTuv = obtenerCodigoNivel(conGP,valCatTuv);
                        
                        Integer codClase = obtenerCodigoNivel(conGP,valClase);

                        /*
                         * Los cuatro procesos pertenecen
                         * al mismo artículo.
                         */
                        procesarABC(stmGP,strItemReal,3,codCatVen);

                        procesarABC(stmGP,strItemReal,2,codCatPes);

                        procesarABC(stmGP,strItemReal,1,codCatFre);

                        procesarABC(stmGP,strItemReal,4,codCatTuv);
                        
                        procesarABC(stmGP,strItemReal,5,codClase);

                        /*
                         * Confirmar solamente este artículo.
                         * Aquí SQL Server libera sus bloqueos.
                         */
                        conGP.commit();

                        intProcesadosOk++;
                    }
                    catch (Exception eItem)
                    {
                        /*
                         * Deshacer solamente el artículo
                         * que presentó el error.
                         */
                        try
                        {
                            if (conGP != null)
                            {
                                conGP.rollback();
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }

                        blnRes = false;

                        lstErrores.add(
                            strCodRaw
                            + " - "
                            + eItem.getMessage()
                        );

                        eItem.printStackTrace();

                        /*
                         * Continuar con el siguiente artículo.
                         */
                        continue;
                    }
                }

                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);

                if (lstErrores.isEmpty())
                {
                    mostrarMsgInf("Proceso ABC ejecutado correctamente.\n"+ "Artículos procesados: "+ intProcesadosOk);

                    objTblMod5.removeAllRows();
                    objTblMod5.fireTableDataChanged();
                }
                else
                {
                    StringBuilder strDetalle =
                        new StringBuilder();

                    strDetalle.append(
                        "Proceso ABC finalizado con errores.\n\n"
                    );

                    strDetalle.append(
                        "Artículos procesados correctamente: "
                    );

                    strDetalle.append(intProcesadosOk);
                    strDetalle.append("\n");

                    strDetalle.append(
                        "Artículos con error: "
                    );

                    strDetalle.append(lstErrores.size());
                    strDetalle.append("\n\n");

                    strDetalle.append(
                        "Detalle:\n"
                    );

                    for (String strError : lstErrores)
                    {
                        strDetalle
                            .append(strError)
                            .append("\n");
                    }

                    mostrarMsgInf(
                        strDetalle.toString()
                    );
                }
            }
        }
        catch (Exception e)
        {
            blnRes = false;

            try
            {
                if (conGP != null)
                {
                    conGP.rollback();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            { 
                if (stmGP != null)
                {
                    stmGP.close();
                }
            }
            catch (Exception e)
            {
            }

            try
            {
                if (conGP != null)
                {
                    conGP.close();
                }
            }
            catch (Exception e)
            {
            }
        }

        return blnRes;
} 
  



private void eliminarRegistrosInactivos() {

    Vector vecNuevaData = new Vector();

    for (int i = 0; i < objTblMod3.getRowCountTrue(); i++) {

        String estado = objTblMod3.getValueAt(i, INT_ARL_TAB_TRE_EST).toString();

        if (!"I".equalsIgnoreCase(estado)) {
            vecNuevaData.add(objTblMod3.getData().get(i));
        }
    }

    objTblMod3.removeAllRows();
    objTblMod3.setData(vecNuevaData);

    tblCar3.repaint();
    tblCar3.updateUI();
}


  private void exportarExcel()
  {                                          
      try 
      {
         LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String directorio = "C:\\Zafiro\\Reportes\\Herramientas\\ZafHer41\\";
        String fechaFormateada = fechaHoraActual.format(formato);
        String nombreArchivo = "ZafHer41_" + fechaFormateada + ".xlsx";
        File directorioFile = new File(directorio);
        
        if (!directorioFile.exists()) {
            directorioFile.mkdirs();
        }
        String archivoCompleto = directorio + nombreArchivo;

        objExcel = new ZafExpoExc();
        objExcel.exportarJTableAExcel(tblCar3, archivoCompleto);
        mostrarMsgInf("Reporte exportado de forma exitosa a la ubicación: " + archivoCompleto);

        // Intentar abrir el archivo automáticamente
        File archivo = new File(archivoCompleto);
        if (archivo.exists()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(archivo);
            } else {
                mostrarMsgInf("El sistema no soporta la apertura automática de archivos.");
            }
        } else {
            mostrarMsgInf("El archivo no se encontró en la ubicación especificada.");
        }
    } catch (IOException ex) {
        mostrarMsgInf("Falló la exportación del archivo. Puede ser que el archivo esté abierto.");
    } catch (Exception ex) {
        mostrarMsgInf("Ocurrió un error inesperado: " + ex.getMessage());
    }
    }  

  
private boolean ValidaStockBin(String item)//chiqui
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
              strSqlZa+=" WHERE ITEMNMBR IN ( " +
                          "SELECT tx_codalt FROM tbm_inv WHERE tx_codalt2 = '"+ item +"' ) \n";
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
    private boolean ValidaStock(String item)//chiqui
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
              strSqlZa+=" WHERE ITEMNMBR IN ( " +
                          "SELECT tx_codalt FROM tbm_inv WHERE tx_codalt2 = '"+ item +"' ) \n";
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
    
    
    
    private class ZafThGuarGUI extends Thread
    { 
        public void run()
        {
            boolean validaIng =false;
            if (validaPes==1)
            {
                if (objTblMod1.getRowCountTrue() > 0)
                    validaIng=true;                
            }
            if (validaPes==5)
            {
                if (objTblMod2.getRowCountTrue() > 0)
                    validaIng=true;                
            }
            if (validaPes==3)
            {
                if (objTblMod3.getRowCountTrue() > 0)
                    validaIng=true;                
            }            
            if (validaPes==4)
            {
                if (objTblMod4.getRowCountTrue() > 0)
                    validaIng=true;                
            } 
            if (validaPes==2)
            {
                if (objTblMod5.getRowCountTrue() > 0)
                    validaIng=true;
            }
            if (validaIng==true)
            {                
                StringBuilder msg = new StringBuilder();

                msg.append("Se van a realizar los siguientes cambios:\n\n");

                if (validaPes == 1) {
                    msg.append("Pestaña: ATRIBUTOS ITEM\n");
                    msg.append("Registros a procesar: ")
                       .append(objTblMod1.getRowCountTrue())
                       .append("\n\n");
                }
                msg.append("¿Desea continuar?");
                
                if (mostrarMsgCon(msg.toString())==0)
                {
                    if (validaPes==1)
                    {
                        ingresarDatosCargadosAtributosItem();
                        //ingresarDatosCargadosHist();
                        if (!huboErrores)
                        {
                            tabFrm.setSelectedIndex(0);

                            if (tblCar1.getRowCount() > 0)
                            {
                                tblCar1.setRowSelectionInterval(0, 0);
                            }

                            tblCar1.requestFocus();
                        }                                              
                    }
//                    if (validaPes==5)
//                    {
//                        ingresarDatosCargadosMinMax();
//                        tabFrm.setSelectedIndex(1);
//                        tblCar2.setRowSelectionInterval(0, 0);
//                        tblCar2.requestFocus();                                                                        
//                    }
//                        
//                    if (validaPes==3)
//                    {
//                        ingresarDatosInactivos();
//                        tabFrm.setSelectedIndex(2);
//                        tblCar3.setRowSelectionInterval(0, 0);
//                        tblCar3.requestFocus();                                                                                                
//                        
//                    }
//                    if (validaPes==4)
//                    {
//                        if (ingresarDatosDesagrupacion())
//                        tabFrm.setSelectedIndex(3);
//                        tblCar4.setRowSelectionInterval(0, 0);
//                        tblCar4.requestFocus();                                                                                                
//                        
//                    }

                    if (validaPes==2)
                    {
                        ingresarDatosCargadosABC();

                        tabFrm.setSelectedIndex(1);

                        if (tblCar5.getRowCount() > 0)
                        {
                            tblCar5.setRowSelectionInterval(0, 0);
                        }
                        
                        tblCar5.requestFocus();
                    }                    
                    
                }                
            }
            else
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            objThrGuaGUI=null;
        }
    }
    
    
private boolean isVersionActualizada()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement(); 
                strSQLGP="";
                strSQLGP+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
                rstLoc = stmLoc.executeQuery(strSQLGP);
                if(rstLoc.next()){
                    System.out.println("Version " + version);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(version.equals(rstLoc.getString("tx_ver"))){
                        blnRes=true;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    /** Creates new form ZafHer41 */
    public ZafHer41(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();          

            initComponents();
            
            txtLog.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            
            txtLog.setEditable(false);
            txtLog.setLineWrap(true);
            txtLog.setWrapStyleWord(true);                
                
            if (!configurarFrm()) 
            exitForm();
           
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    } 
    
        /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try
        {
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);  
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);    
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + version);
            lblTit.setText(strAux);
            
            
           objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
             //ConfigurarCombos
            
        
            //Configurar los JTables.
            configurarTblDat1();
            configurarTblDat2();
            configurarTblDat3();
            configurarTblDat4();
            configurarTblDat5();
            
            cargarUsuarios();
            
            // ocultar pestañas no usadas
            tabFrm.remove(panDat2);
            tabFrm.remove(panDat3);
            tabFrm.remove(panDat4);
            
            butExp.setVisible(false);
            
            int index2 = tabFrm.indexOfTab("CARGA-2");
            if (index2 != -1) 
            {
                Component tab2 = tabFrm.getComponentAt(index2);
                tabFrm.removeTabAt(index2);

                // Si luego quieres volver a mostrarla:
                // jTabbedPane1.addTab("CARGA-2", tab);
            }

            int index3 = tabFrm.indexOfTab("CARGA-3");
            if (index3 != -1) 
            {
                Component tab3 = tabFrm.getComponentAt(index3);
                tabFrm.removeTabAt(index3);

                // Si luego quieres volver a mostrarla:
                // jTabbedPane1.addTab("CARGA-2", tab);
            }   
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    

 /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblTipoMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModTipo.getRowCountTrue();

        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    
   /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat1()//chiqui
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat1=new Vector();    //Almacena los datos
            vecCab1=new Vector();    //Almacena las cabeceras
            vecCab1.clear();
    
    
            vecCab1.add(INT_ARL_TAB_PRI_LIN,"");
            vecCab1.add(INT_ARL_TAB_PRI_ITEM,"Item");
            vecCab1.add(INT_ARL_TAB_PRI_FRE,"Fre.");
            vecCab1.add(INT_ARL_TAB_PRI_STO,"Sto.Bas");
            vecCab1.add(INT_ARL_TAB_PRI_LEA,"Lea.Tim");
            vecCab1.add(INT_ARL_TAB_PRI_MOQ,"MOQ");
            vecCab1.add(INT_ARL_TAB_PRI_COB_MAX,"Cob.Max.");
            vecCab1.add(INT_ARL_TAB_PRI_PES,"Pes");
            vecCab1.add(INT_ARL_TAB_PRI_PROVTA,"Pro.Vta.");
            vecCab1.add(INT_ARL_TAB_PRI_PROTEO,"Pro.Teo.");
            vecCab1.add(INT_ARL_TAB_PRI_DESC,"Descripcion.");
            vecCab1.add(INT_ARL_TAB_PRI_CODZAF,"Cod.Zaf");
            vecCab1.add(INT_ARL_TAB_PRI_CLA,"Clase");
            vecCab1.add(INT_ARL_TAB_PRI_CAT,"Categoria");
            vecCab1.add(INT_ARL_TAB_PRI_LINE,"Linea");
            vecCab1.add(INT_ARL_TAB_PRI_MAR,"Marca");
            vecCab1.add(INT_ARL_TAB_PRI_CON,"Control");
            vecCab1.add(INT_ARL_TAB_PRI_COM,"Compras");
            vecCab1.add(INT_ARL_TAB_PRI_MAT,"Material");
            vecCab1.add(INT_ARL_TAB_PRI_CAT_COMI_VTA,"Cate.Comision");
            vecCab1.add(INT_ARL_TAB_PRI_CLA_COMI_VTA,"Clase.Comision");
            vecCab1.add(INT_ARL_TAB_PRI_LIN_COMI_VTA,"Linea.Comision");
            vecCab1.add(INT_ARL_TAB_PRI_COM_SUB,"SubCompra");
            
                        
            objTblMod1=new ZafTblMod();
            objTblMod1.setHeader(vecCab1);
            tblCar1.setModel(objTblMod1);

            
            objTblMod1=new ZafTblMod();
            objTblMod1.setHeader(vecCab1);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblCar1.setModel(objTblMod1);  
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.            
            
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_FRE, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_STO, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_LEA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_MOQ, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_COB_MAX, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_PES, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_PROVTA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_PROTEO, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_DESC, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CODZAF, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CLA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CAT, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_LINE, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_MAR, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CON, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_COM, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_MAT, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CAT_COMI_VTA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_CLA_COMI_VTA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_LIN_COMI_VTA, ZafTblMod.INT_COL_DBL, null, null);
            objTblMod1.setColumnDataType(INT_ARL_TAB_PRI_COM_SUB, ZafTblMod.INT_COL_DBL, null, null);
            
 
            tblCar1.setCellSelectionEnabled(true);
            tblCar1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum1=new ZafColNumerada(tblCar1,INT_ARL_TAB_PRI_LIN);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCar1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                          
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCar1.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_PRI_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_PRI_ITEM).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_PRI_FRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_FRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_STO).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_LEA).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_MOQ).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_COB_MAX).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_PES).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_PRI_PROVTA).setPreferredWidth(90);
            tcmAux.getColumn(INT_ARL_TAB_PRI_PROTEO).setPreferredWidth(90);
            tcmAux.getColumn(INT_ARL_TAB_PRI_DESC).setPreferredWidth(600);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CODZAF).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CLA).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CAT).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_LINE).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_MAR).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CON).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_COM).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_MAT).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CAT_COMI_VTA).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_CLA_COMI_VTA).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_LIN_COMI_VTA).setPreferredWidth(150);
            tcmAux.getColumn(INT_ARL_TAB_PRI_COM_SUB).setPreferredWidth(150);
            
        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCar1.getTableHeader().setReorderingAllowed(false);
           
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis1=new ZafTblModLis();
            objTblMod1.addTableModelListener(objTblModLis1);   
            
            objTblMod1.setModoOperacion(objTblMod1.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd1=new ZafTblOrd(tblCar1);  
            
            //Configurar JTable: Ocultar columnas del sistema.           
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_FRE, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_STO, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_LEA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_MOQ, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_COB_MAX, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_PES, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_PROVTA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_PROTEO, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_DESC, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CODZAF, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CLA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CAT, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_LINE, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_MAR, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CON, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_COM, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_MAT, tblCar1);
            
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CAT_COMI_VTA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_CLA_COMI_VTA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_LIN_COMI_VTA, tblCar1);
            objTblMod1.addSystemHiddenColumn(INT_ARL_TAB_PRI_COM_SUB, tblCar1);
            
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblDat2()//chiqui
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat2=new Vector();    //Almacena los datos
            vecCab2=new Vector();    //Almacena las cabeceras
            vecCab2.clear();
    
            vecCab2.add(INT_ARL_TAB_SEG_LIN,"");
            vecCab2.add(INT_ARL_TAB_SEG_ITEM,"Item");
            vecCab2.add(INT_ARL_TAB_SEG_BODE,"Bodega");
            vecCab2.add(INT_ARL_TAB_SEG_MIN,"Minimo");
            vecCab2.add(INT_ARL_TAB_SEG_MAX,"Maximo");
            
            objTblMod2=new ZafTblMod();
            objTblMod2.setHeader(vecCab2);
            tblCar2.setModel(objTblMod2);

            
            objTblMod2=new ZafTblMod();
            objTblMod2.setHeader(vecCab2);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblCar2.setModel(objTblMod2);  
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod2.setColumnDataType(INT_ARL_TAB_SEG_MIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod2.setColumnDataType(INT_ARL_TAB_SEG_MAX, ZafTblMod.INT_COL_DBL, new Integer(0), null);
        
            tblCar2.setCellSelectionEnabled(true);
            tblCar2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum2=new ZafColNumerada(tblCar2,INT_ARL_TAB_SEG_LIN);
            
            tblCar2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCar2.getColumnModel();
            tcmAux.getColumn(INT_ARL_TAB_SEG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_ARL_TAB_SEG_ITEM).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_SEG_BODE).setPreferredWidth(100);
            tcmAux.getColumn(INT_ARL_TAB_SEG_MIN).setPreferredWidth(50);
            tcmAux.getColumn(INT_ARL_TAB_SEG_MAX).setPreferredWidth(50);
 

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCar2.getTableHeader().setReorderingAllowed(false);
                       
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis2=new ZafTblModLis();
            objTblMod2.addTableModelListener(objTblModLis2);   
            
            objTblMod2.setModoOperacion(objTblMod2.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd2=new ZafTblOrd(tblCar2);            
            
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
    private boolean configurarTblDat3()//chiqui
    {
            boolean blnRes=true;
            try{
                //Configurar JTable: Establecer el modelo.
                vecDat3=new Vector();    //Almacena los datos
                vecCab3=new Vector();    //Almacena las cabeceras
                vecCab3.clear();

                vecCab3.add(INT_ARL_TAB_TRE_LIN,"");
                vecCab3.add(INT_ARL_TAB_TRE_TRES,"3L");
                vecCab3.add(INT_ARL_TAB_TRE_ITEM,"Item");
                vecCab3.add(INT_ARL_TAB_TRE_CHK_PED,"Pedido");
                vecCab3.add(INT_ARL_TAB_TRE_CHK_AGR,"Agrupacion");
                vecCab3.add(INT_ARL_TAB_TRE_CHK_STO,"Stock");
                vecCab3.add(INT_ARL_TAB_TRE_EST,"Estado");
                objTblMod3=new ZafTblMod();
                objTblMod3.setHeader(vecCab3);
                tblCar3.setModel(objTblMod3);

                objTblMod3=new ZafTblMod();
                objTblMod3.setHeader(vecCab3);
                //Configuar JTable: Establecer el modelo de la tabla.
                tblCar3.setModel(objTblMod3);  
                //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                tblCar3.setCellSelectionEnabled(true);
                tblCar3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
                //Configurar JTable: Establecer la fila de cabecera.
                objColNum3=new ZafColNumerada(tblCar3,INT_ARL_TAB_TRE_LIN);
     
                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblCar3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    



                //Configurar JTable: Establecer el ancho de las columnas.
                javax.swing.table.TableColumnModel tcmAux=tblCar3.getColumnModel();
                tcmAux.getColumn(INT_ARL_TAB_TRE_LIN).setPreferredWidth(30);
                tcmAux.getColumn(INT_ARL_TAB_TRE_TRES).setPreferredWidth(70);
                tcmAux.getColumn(INT_ARL_TAB_TRE_ITEM).setPreferredWidth(70);
                tcmAux.getColumn(INT_ARL_TAB_TRE_EST).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_PED).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_AGR).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_STO).setPreferredWidth(50);
                
                objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_PED).setCellRenderer(objTblCelRenChk);
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_AGR).setCellRenderer(objTblCelRenChk);
                tcmAux.getColumn(INT_ARL_TAB_TRE_CHK_STO).setCellRenderer(objTblCelRenChk);
 
            

                //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
                tblCar3.getTableHeader().setReorderingAllowed(false);   

                //Configurar JTable: Detectar cambios de valores en las celdas.
                objTblModLis3=new ZafTblModLis();
                objTblMod3.addTableModelListener(objTblModLis3);   

                objTblMod3.setModoOperacion(objTblMod3.INT_TBL_EDI);
                //Configurar JTable: Establecer la clase que controla el ordenamiento.
                objTblOrd3=new ZafTblOrd(tblCar3);            

                System.out.println("Se configuara la tabla!!!!... ");
            }
            catch(Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }   
    
    private boolean configurarTblDat4()//chiqui
    {
            boolean blnRes=true;
            try{
                //Configurar JTable: Establecer el modelo.
                vecDat4=new Vector();    //Almacena los datos
                vecCab4=new Vector();    //Almacena las cabeceras
                vecCab4.clear();

                vecCab4.add(INT_ARL_TAB_CUA_LIN,"");
                vecCab4.add(INT_ARL_TAB_CUA_TRES,"3L");
                vecCab4.add(INT_ARL_TAB_CUA_ITEM,"Item");
                vecCab4.add(INT_ARL_TAB_CUA_AGR,"Grupo");
                objTblMod4=new ZafTblMod();
                objTblMod4.setHeader(vecCab4);
                tblCar4.setModel(objTblMod4);


                objTblMod4=new ZafTblMod();
                objTblMod4.setHeader(vecCab4);
                //Configuar JTable: Establecer el modelo de la tabla.
                tblCar4.setModel(objTblMod4);  
                //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                tblCar4.setCellSelectionEnabled(true);
                tblCar4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
                //Configurar JTable: Establecer la fila de cabecera.
                objColNum4=new ZafColNumerada(tblCar3,INT_ARL_TAB_CUA_LIN);
                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblCar4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    



                //Configurar JTable: Establecer el ancho de las columnas.
                javax.swing.table.TableColumnModel tcmAux=tblCar3.getColumnModel();
                tcmAux.getColumn(INT_ARL_TAB_CUA_LIN).setPreferredWidth(30);
                tcmAux.getColumn(INT_ARL_TAB_CUA_TRES).setPreferredWidth(70);
                tcmAux.getColumn(INT_ARL_TAB_CUA_ITEM).setPreferredWidth(70);
                tcmAux.getColumn(INT_ARL_TAB_CUA_AGR).setPreferredWidth(50);
                
                //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
                tblCar4.getTableHeader().setReorderingAllowed(false);
                //Configurar JTable: Detectar cambios de valores en las celdas.
                objTblModLis4=new ZafTblModLis();
                objTblMod4.addTableModelListener(objTblModLis4);   

                objTblMod4.setModoOperacion(objTblMod4.INT_TBL_EDI);
                //Configurar JTable: Establecer la clase que controla el ordenamiento.
                objTblOrd4=new ZafTblOrd(tblCar4);            

                System.out.println("Se configuara la tabla!!!!... ");
            }
            catch(Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
    } 
    
    private boolean configurarTblDat5()//chiqui
    {
            boolean blnRes=true;
            try{
                //Configurar JTable: Establecer el modelo.
                vecDat5=new Vector();    //Almacena los datos
                vecCab5=new Vector();    //Almacena las cabeceras
                vecCab5.clear();


                vecCab5.add(INT_ARL_TAB_QUI_LIN,"");
                vecCab5.add(INT_ARL_TAB_QUI_ITEM,"Item");
                vecCab5.add(INT_ARL_TAB_QUI_CATVEN,"Cat.Ventas");
                vecCab5.add(INT_ARL_TAB_QUI_CATPES,"Cat.Peso");
                vecCab5.add(INT_ARL_TAB_QUI_CATFRE,"Cat.Fre.");
                vecCab5.add(INT_ARL_TAB_QUI_CATTUV,"Cat.Tuv.");
                vecCab5.add(INT_ARL_TAB_QUI_CLASE,"Cat.Clase");
                


                objTblMod5=new ZafTblMod();
                objTblMod5.setHeader(vecCab5);
                tblCar5.setModel(objTblMod5);


                objTblMod5=new ZafTblMod();
                objTblMod5.setHeader(vecCab5);
                //Configurar JTable: Establecer el modelo de la tabla.
                tblCar5.setModel(objTblMod5);  
                //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.            

                objTblMod5.setColumnDataType(INT_ARL_TAB_QUI_CATVEN, ZafTblMod.INT_COL_DBL, null, null);
                objTblMod5.setColumnDataType(INT_ARL_TAB_QUI_CATPES, ZafTblMod.INT_COL_DBL, null, null);
                objTblMod5.setColumnDataType(INT_ARL_TAB_QUI_CATFRE, ZafTblMod.INT_COL_DBL, null, null);
                objTblMod5.setColumnDataType(INT_ARL_TAB_QUI_CATTUV, ZafTblMod.INT_COL_DBL, null, null);
                objTblMod5.setColumnDataType(INT_ARL_TAB_QUI_CLASE, ZafTblMod.INT_COL_DBL, null, null);
                


                tblCar5.setCellSelectionEnabled(true);
                tblCar5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
                //Configurar JTable: Establecer la fila de cabecera.
                objColNum5=new ZafColNumerada(tblCar5,INT_ARL_TAB_QUI_LIN);

                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblCar5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                          

                //Configurar JTable: Establecer el ancho de las columnas.
                javax.swing.table.TableColumnModel tcmAux=tblCar5.getColumnModel();
                tcmAux.getColumn(INT_ARL_TAB_QUI_LIN).setPreferredWidth(30);
                tcmAux.getColumn(INT_ARL_TAB_QUI_ITEM).setPreferredWidth(100);
                tcmAux.getColumn(INT_ARL_TAB_QUI_CATVEN).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_QUI_CATPES).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_QUI_CATFRE).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_QUI_CATTUV).setPreferredWidth(50);
                tcmAux.getColumn(INT_ARL_TAB_QUI_CLASE).setPreferredWidth(50);
                


                //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
                tblCar5.getTableHeader().setReorderingAllowed(false);


                //Configurar JTable: Detectar cambios de valores en las celdas.
                objTblModLis5=new ZafTblModLis();
                objTblMod5.addTableModelListener(objTblModLis5);   

                objTblMod5.setModoOperacion(objTblMod5.INT_TBL_EDI);
                //Configurar JTable: Establecer la clase que controla el ordenamiento.
                objTblOrd5=new ZafTblOrd(tblCar5);  

                System.out.println("Se configuara la tabla!!!!... ");
            }
            catch(Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
    } 
    
    
    private boolean ingresarDatosCargadosTbl(int tipo) {
    
    boolean blnRes = true;
    java.sql.Connection conGP = null;
    java.sql.Statement stmGP = null;
    java.sql.Connection conLoc = null;
    java.sql.Statement stmLoc = null;
    String strFecUltMod;
    String str_cod2 = "",str_cod="";
    String str_agr="";
    int contador;
    
    
    try 
    {
        pgrSis.setIndeterminate(true);        
        lblMsgSis.setText("Obteniendo datos...");
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        if (datFecAux == null)
            return false;
        // DECLARAR MAPAS FUERA
        Map<String, Map<String, Object>> mapDatHist = null;
        //Map<String, Map<String, Object>> mapDatInac = null;        

        String[] columnasAct = cadenaColAct.toString().split(",");
        strFecUltMod = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
        
        conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

        // Obtener los datos históricos desde Dynamics
        if (tipo==1)
             mapDatHist = obtenerHistorialItems(strCodItmDynBus);

        //Limpiar la tabla antes de cargar
        objTblMod1.removeAllRows();
        vecDat1.clear();
        objTblMod2.removeAllRows();
        vecDat2.clear();
        objTblMod3.removeAllRows();
        vecDat3.clear();        
        objTblMod4.removeAllRows();
        vecDat4.clear();        
        objTblMod5.removeAllRows();
        vecDat5.clear(); 

        cargarIndicesColumnas(tipo);
        
        
        boolean hayErrores = false;
        List<String> lstErrores = new ArrayList<>();
        List<Vector> listaTemporal = new ArrayList<>();       
        
        if (!existeDatosColumnas(tipo)) {
            mostrarMsgInf( "No existen datos a cargar.");
            return false;
        }
        
        // Recorrer Excel
        for (int i = 0; i < arlDatExcelVal.size(); i++) 
        {
            if (objUti.getStringValueAt(arlDatExcelVal, i, 1).toString().equals("OK"))
            {
                str_cod2 = objUti.getStringValueAt(arlDatExcel, i, 0);

                Vector vecReg = new Vector();
                if (tipo==1)
                {
                    Map<String, Object> datosHist = mapDatHist.get(str_cod2);

                    vecReg.add(INT_ARL_TAB_PRI_LIN, "");
                    vecReg.add(INT_ARL_TAB_PRI_ITEM, str_cod2);
                    
                    BigDecimal valFreHist = datosHist != null ? (BigDecimal) datosHist.get("nd_fre") : null;
                    BigDecimal valStoHist = datosHist != null ? (BigDecimal) datosHist.get("nd_stkBas") : null;
                    BigDecimal valLeaHist = datosHist != null ? (BigDecimal) datosHist.get("nd_tra") : null;
                    BigDecimal valMoqHist = datosHist != null ? (BigDecimal) datosHist.get("nd_moq") : null;
                    BigDecimal valCobHist = datosHist != null ? (BigDecimal) datosHist.get("nd_cobmax") : null;
                    BigDecimal valPesHist = datosHist != null ? (BigDecimal) datosHist.get("nd_pes") : null;
                    BigDecimal valProVtaHist = datosHist != null ? (BigDecimal) datosHist.get("nd_proAnuVenMan") : null;
                    BigDecimal valProTeoHist = datosHist != null ? (BigDecimal) datosHist.get("nd_proVenTeo") : null;   
                    String valDesHist = datosHist != null ? (String) datosHist.get("tx_nomitm") : null;
                    String valCodZafHist = datosHist != null ? (String) datosHist.get("tx_codalt") : null;
                    String valClafHist = datosHist != null ? (String) datosHist.get("tx_atrcla") : null;
                    String valCatHist = datosHist != null ? (String) datosHist.get("tx_atrcat") : null;
                    String valLinHist = datosHist != null ? (String) datosHist.get("tx_atrlin") : null;
                    String valMarHist = datosHist != null ? (String) datosHist.get("tx_atrmar") : null;
                    String valConHist = datosHist != null ? (String) datosHist.get("tx_atrcon") : null;
                    String valComHist = datosHist != null ? (String) datosHist.get("tx_atrcom") : null;
                    String valMatHist = datosHist != null ? (String) datosHist.get("tx_atrmat") : null;
                    
                    
                    
                    
                                       
                                       
                    Double valFreNuevo = getDoubleNullable(arlDatExcel, i, colFre);
                    Double valStoNuevo = getDoubleNullable(arlDatExcel, i, colSto);
                    Double valLeaNuevo = getDoubleNullable(arlDatExcel, i, colLea);
                    Double valMoqNuevo = getDoubleNullable(arlDatExcel, i, colMoq);
                    Double valCobNuevo = getDoubleNullable(arlDatExcel, i, colCob);
                    Double valPesNuevo = getDoubleNullable(arlDatExcel, i, colPes);  
                    
                    BigDecimal valProVtaNuevo = null;
                    if (colProVta != -1) {
                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colProVta);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valProVtaNuevo = new BigDecimal(obj.toString());
                        }
                    }
                    
                    BigDecimal valProTeoNuevo = null;
                    if (colProTeo != -1) {
                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colProTeo);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valProTeoNuevo = new BigDecimal(obj.toString());
                        }
                    }
                    
                    Double proVtaVal = (valProVtaNuevo != null) ? valProVtaNuevo.doubleValue() : null;
                    Double proTeoVal = (valProTeoNuevo != null) ? valProTeoNuevo.doubleValue() : null;
                    
                    String valDesNuevo = null;
                    if (colDes != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colDes);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valDesNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valCodZafNuevo = null;                    
                    if (colCodZaf != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCodZaf);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCodZafNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valClaNuevo = null;                    
                    if (colCla != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCla);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valClaNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valCatNuevo = null;                    
                    if (colCat != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCat);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valLinNuevo = null;                    
                    if (colLin != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colLin);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valLinNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valMarNuevo = null;                    
                    if (colMar != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colMar);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valMarNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valConNuevo = null;                    
                    if (colCon != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCon);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valConNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valComNuevo = null;                    
                    if (colCom != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCom);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valComNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valMatNuevo = null;                    
                    if (colMat != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colMat);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valMatNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valCatComiNuevo = null;                    
                    if (colCatComi != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colCatComi);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatComiNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valClaComiNuevo = null;                    
                    if (colClaComi != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colClaComi);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valClaComiNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valLinComiNuevo = null;                    
                    if (colLinComi != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colLinComi);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valLinComiNuevo = obj.toString().trim();
                        }
                    }
                    
                    String valSubComNuevo = null;                    
                    if (colSubCom != -1) {

                        ArrayList fila = (ArrayList) arlDatExcel.get(i);
                        Object obj = fila.get(colSubCom);

                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valSubComNuevo = obj.toString().trim();
                        }
                    }
                                        
                    
                    String error = validarAtributosItem(str_cod2,valFreNuevo,valStoNuevo,valLeaNuevo,valMoqNuevo,valCobNuevo,valPesNuevo,proVtaVal,proTeoVal,
                            valDesNuevo,valCodZafNuevo,valClaNuevo,valCatNuevo,valLinNuevo,valMarNuevo,valConNuevo,valComNuevo,valMatNuevo);

                    if (error != null) {
                        hayErrores = true;
                        lstErrores.add("ITEM: " + str_cod2 + " | " + error);
                        continue; // sigue revisando todos
                    }
                    lblMsgSis.setText("Cargando datos...");
                                                          
                    vecReg.add(INT_ARL_TAB_PRI_FRE, valFreNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_STO, valStoNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_LEA, valLeaNuevo);                
                    vecReg.add(INT_ARL_TAB_PRI_MOQ, valMoqNuevo);                    
                    vecReg.add(INT_ARL_TAB_PRI_COB_MAX, valCobNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_PES, valPesNuevo);                    
                    vecReg.add(INT_ARL_TAB_PRI_PROVTA,valProVtaNuevo != null ? valProVtaNuevo.setScale(6, RoundingMode.HALF_UP).toString() : null);
                    vecReg.add(INT_ARL_TAB_PRI_PROTEO,valProTeoNuevo != null ? valProTeoNuevo.setScale(6, RoundingMode.HALF_UP).toString() : null);                    
                    vecReg.add(INT_ARL_TAB_PRI_DESC, valDesNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_CODZAF, valCodZafNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_CLA, valClaNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_CAT, valCatNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_LINE, valLinNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_MAR, valMarNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_CON, valConNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_COM, valComNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_MAT, valMatNuevo);
                    
                    vecReg.add(INT_ARL_TAB_PRI_CAT_COMI_VTA, valCatComiNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_CLA_COMI_VTA, valClaComiNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_LIN_COMI_VTA, valLinComiNuevo);
                    vecReg.add(INT_ARL_TAB_PRI_COM_SUB, valSubComNuevo);
                    
                    
                    //vecDat1.add(vecReg);
                    listaTemporal.add(vecReg);
                    
                    lblMsgSis.setText("Listo");
                    pgrSis.setIndeterminate(false);
                    
                }
                
                if (tipo ==2)
                {
                    Vector vecReg5 = new Vector();

                    vecReg5.add(INT_ARL_TAB_CUA_LIN, "");
                    vecReg5.add(INT_ARL_TAB_QUI_ITEM, str_cod2);
                    
                    String valCatVen = null;
                    String valCatPes = null;
                    String valCatFre = null;
                    String valCatTuv = null;
                    String valClase = null;
                    ArrayList fila = (ArrayList) arlDatExcel.get(i);
                    
                    if (colCatVen != -1)
                    {
                        
                        Object obj =  fila.get(colCatVen);
                        
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatVen = obj.toString().trim();
                        }                        
                    }
                    if (colCatPes != -1)
                    {
                       Object obj =  fila.get(colCatPes);
                        
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatPes = obj.toString().trim();
                        }                        
                    }
                    if (colCatFre != -1)
                    {
                       Object obj =  fila.get(colCatFre);
                        
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatFre = obj.toString().trim();
                        }                        
                    }
                    if (colCatTuv != -1)
                    {
                       Object obj =  fila.get(colCatTuv);
                        
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valCatTuv = obj.toString().trim();
                        }                        
                    }
                    if (colClase != -1)
                    {
                       Object obj =  fila.get(colClase);
                        
                        if (obj != null && !obj.toString().trim().isEmpty()) {
                            valClase = obj.toString().trim();
                        }                        
                    }
                   
                    vecReg5.add(INT_ARL_TAB_QUI_CATVEN,valCatVen);
                    vecReg5.add(INT_ARL_TAB_QUI_CATPES,valCatPes);
                    vecReg5.add(INT_ARL_TAB_QUI_CATFRE,valCatFre);
                    vecReg5.add(INT_ARL_TAB_QUI_CATTUV,valCatTuv);
                    vecReg5.add(INT_ARL_TAB_QUI_CLASE,valClase);
                    
                   
                    listaTemporal.add(vecReg5);
                    
                    lblMsgSis.setText("Listo");
                    pgrSis.setIndeterminate(false);
                    
                }
                                                   
            }
        }
        
        if (hayErrores) { 
            blnHayErroresCarga = true;   
            mostrarErroresValidacion(lstErrores);
            return true; // 
        }    
        
        if (tipo ==1)
        {
            vecDat1.addAll(listaTemporal);
            objTblMod1.setData(vecDat1);
            tblCar1.setModel(objTblMod1);
            ocultarColumnasNoUsadas();
            vecDat1.clear();             
        }
        
        if (tipo ==2)
        {
            vecDat5.addAll(listaTemporal);
            objTblMod5.setData(vecDat5);
            tblCar5.setModel(objTblMod5);            
            vecDat5.clear();                        
        }
        
        //tblCar1.setModel(objTblMod1);
        System.out.println("Datos cargados en la tabla correctamente");
    
    }  catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (conGP != null)
                conGP.close();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    return blnRes;
}
    
 

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat1 = new javax.swing.JPanel();
        panDet1 = new javax.swing.JPanel();
        spnDet1 = new javax.swing.JScrollPane();
        tblCar1 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat2 = new javax.swing.JPanel();
        panDet2 = new javax.swing.JPanel();
        spnDet2 = new javax.swing.JScrollPane();
        tblCar2 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat3 = new javax.swing.JPanel();
        spnDet3 = new javax.swing.JScrollPane();
        tblCar3 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat4 = new javax.swing.JPanel();
        spnDet4 = new javax.swing.JScrollPane();
        tblCar4 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDat5 = new javax.swing.JPanel();
        spnDet6 = new javax.swing.JScrollPane();
        tblCar5 = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDatLog = new javax.swing.JPanel();
        spnDet5 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUsr = new javax.swing.JComboBox<>();
        butCarExc = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butExp = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                exitForm(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Actualizacion Masiva de items.....");
        lblTit.setToolTipText("");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));
        tabFrm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabFrmMouseClicked(evt);
            }
        });

        panDat1.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat1.setLayout(new java.awt.BorderLayout());

        panDet1.setName(""); // NOI18N
        panDet1.setPreferredSize(new java.awt.Dimension(610, 180));
        panDet1.setLayout(new java.awt.BorderLayout());

        tblCar1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDet1.setViewportView(tblCar1);

        panDet1.add(spnDet1, java.awt.BorderLayout.CENTER);

        panDat1.add(panDet1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("ATRIBUTOS_ITEM", panDat1);

        panDat2.setLayout(new java.awt.BorderLayout());

        panDet2.setLayout(new java.awt.BorderLayout());

        tblCar2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDet2.setViewportView(tblCar2);

        panDet2.add(spnDet2, java.awt.BorderLayout.CENTER);

        panDat2.add(panDet2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("MINIMO_MAXIMO", panDat2);

        panDat3.setLayout(new java.awt.BorderLayout());

        tblCar3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDet3.setViewportView(tblCar3);

        panDat3.add(spnDet3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("INACTIVACION", panDat3);

        panDat4.setLayout(new java.awt.BorderLayout());

        tblCar4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDet4.setViewportView(tblCar4);

        panDat4.add(spnDet4, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("DESAGRUPACION", panDat4);

        panDat5.setLayout(new java.awt.BorderLayout());

        tblCar5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnDet6.setViewportView(tblCar5);

        panDat5.add(spnDet6, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("ABC", panDat5);

        panDatLog.setLayout(new java.awt.BorderLayout());

        txtLog.setColumns(20);
        txtLog.setRows(5);
        spnDet5.setViewportView(txtLog);

        panDatLog.add(spnDet5, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("ERRORES", panDatLog);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel1.setText("Usuario:");
        panBot.add(jLabel1);

        btnUsr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panBot.add(btnUsr);

        butCarExc.setText("Cargar Excel");
        butCarExc.setToolTipText("Cargar Excel");
        butCarExc.setPreferredSize(new java.awt.Dimension(110, 25));
        butCarExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarExcActionPerformed(evt);
            }
        });
        panBot.add(butCarExc);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(110, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(110, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        butExp.setLabel("Exportar");
        butExp.setName(""); // NOI18N
        butExp.setPreferredSize(new java.awt.Dimension(110, 25));
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panBot.add(butExp);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
    try
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            if(conGP!=null){
                conGP.close();
                conGP=null;
            }
            dispose();
        }
    }
    catch (java.sql.SQLException e)
    {
        dispose();
    }        
}//GEN-LAST:event_exitForm

    private void butCarExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarExcActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCarExc.getText().equals("Cargar Excel"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafHer41.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butCarExcActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      
     blnCon=true;
    if (objThrGuaGUI==null)
    {
        objThrGuaGUI=new ZafThGuarGUI();
        objThrGuaGUI.start();
    }
       
     

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void tabFrmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabFrmMouseClicked
        indTab = tabFrm.getSelectedIndex(); 
        validaPes = indTab+1;
    }//GEN-LAST:event_tabFrmMouseClicked

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butExpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JComboBox<String> btnUsr;
    private javax.swing.JButton butCarExc;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butGua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat1;
    private javax.swing.JPanel panDat2;
    private javax.swing.JPanel panDat3;
    private javax.swing.JPanel panDat4;
    private javax.swing.JPanel panDat5;
    private javax.swing.JPanel panDatLog;
    private javax.swing.JPanel panDet1;
    private javax.swing.JPanel panDet2;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet1;
    private javax.swing.JScrollPane spnDet2;
    private javax.swing.JScrollPane spnDet3;
    private javax.swing.JScrollPane spnDet4;
    private javax.swing.JScrollPane spnDet5;
    private javax.swing.JScrollPane spnDet6;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCar1;
    private javax.swing.JTable tblCar2;
    private javax.swing.JTable tblCar3;
    private javax.swing.JTable tblCar4;
    private javax.swing.JTable tblCar5;
    private javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
private void mostrarMsgInfCopia(String strMsg)
{
    JTextArea textArea = new JTextArea(strMsg);
    textArea.setEditable(false);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setBackground(new Color(240, 240, 240)); // mismo color que el fondo del dialogo

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(300, 200));

    JOptionPane.showMessageDialog(
        this,
        scrollPane,
        "Mensaje del sistema Zafiro",
        JOptionPane.INFORMATION_MESSAGE
    );
}
    
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            indTab = tabFrm.getSelectedIndex(); 
            if (indTab==0)
                validaPes=1;
            if (indTab==1)
                validaPes=2;
            if (indTab==2)
                validaPes=3;  
            if (indTab==3)
                validaPes=4;              
            if (indTab==4)
                validaPes=5;
            
            cargarArchivoXLS();

            if (validaPes==1)
            {
                if (tblCar1.getRowCount()>0)
                 tabFrm.setSelectedIndex(0);
            }
            if (validaPes==2)
            {
                if (tblCar2.getRowCount()>0)
                 tabFrm.setSelectedIndex(1);
            }
            if (validaPes==3)
            {
                if (tblCar3.getRowCount()>0)
                 tabFrm.setSelectedIndex(2);
            }
            if (validaPes==4)
            {
                if (tblCar4.getRowCount()>0)
                 tabFrm.setSelectedIndex(3);
            }  
            if (validaPes==5)
            {
                if (tblCar5.getRowCount()>0)
                 tabFrm.setSelectedIndex(4);
            }  

            objThrGUI=null;
        }
    }
    
    private void ConfiguracionColumnas(int tipo)
    {
        if (tipo ==1)
        {
            ColumnasExcel =  new String[][]
            {
                {"FRECUENCIA", objUti.parseString(INT_ARL_EXC_PRI_FRE)},
                {"STOCKBASE", objUti.parseString(INT_ARL_EXC_PRI_STO)},
                {"LEADTIME", objUti.parseString(INT_ARL_EXC_PRI_LEA)},
                {"MOQ", objUti.parseString(INT_ARL_EXC_PRI_MOQ)},
                {"COBMAX", objUti.parseString(INT_ARL_EXC_PRI_COB_MAX)},
                {"PESO", objUti.parseString(INT_ARL_EXC_PRI_PES)},
                {"PROMVTA", objUti.parseString(INT_ARL_EXC_PRI_PROVEN)},
                {"PROTEO", objUti.parseString(INT_ARL_EXC_PRI_PROTEO)},
                {"DESCRIPCION", objUti.parseString(INT_ARL_EXC_PRI_DESC)},
                {"CODZAF", objUti.parseString(INT_ARL_EXC_PRI_CODZAF)},
                {"CLASE", objUti.parseString(INT_ARL_EXC_PRI_CLA)},
                {"CATEGORIA", objUti.parseString(INT_ARL_EXC_PRI_CAT)},
                {"LINEA", objUti.parseString(INT_ARL_EXC_PRI_LINE)},
                {"MARCA", objUti.parseString(INT_ARL_EXC_PRI_MAR)},
                {"CONTROL", objUti.parseString(INT_ARL_EXC_PRI_CON)},
                {"COMPRAS", objUti.parseString(INT_ARL_EXC_PRI_COM)},
                {"MATERIAL", objUti.parseString(INT_ARL_EXC_PRI_MAT)},
                {"CATECOMISION", objUti.parseString(INT_ARL_EXC_PRI_CAT_COMI_VTA)},
                {"CLASECOMISION", objUti.parseString(INT_ARL_EXC_PRI_CLA_COMI_VTA)},
                {"LINEACOMISION", objUti.parseString(INT_ARL_EXC_PRI_LIN_COMI_VTA)},
                {"SUBCOMPRAS", objUti.parseString(INT_ARL_EXC_PRI_COM_SUB)}
                
            };            
        }
        if (tipo ==5)
        {
            ColumnasExcel =  new String[][]
            {
                {"ITEM", objUti.parseString(INT_ARL_EXC_SEG_ITEM)},
                {"MIN", objUti.parseString(INT_ARL_EXC_SEG_MIN)},
                {"MAX", objUti.parseString(INT_ARL_EXC_SEG_MAX)},
            };                                    

        }        
        if (tipo ==3)
        {
            ColumnasExcel =  new String[][]
            {
                {"TRES", objUti.parseString(INT_ARL_EXC_TRE_TRES)},
                {"ITEM", objUti.parseString(INT_ARL_EXC_TRE_ITEM)},
            };                                    

        }   
        if (tipo ==4)
        {
            ColumnasExcel =  new String[][]
            {
                
                
            };                                    

        }
        
        if (tipo==2)
        {
            ColumnasExcel = new String[][]
            {
                {"ITEM", objUti.parseString(INT_ARL_EXC_QUI_ITEM)},
                {"Cat. Ventas", objUti.parseString(INT_ARL_EXC_QUI_CATVEN)},
                {"Cat. Peso", objUti.parseString(INT_ARL_EXC_QUI_CATPES)},
                {"Cat. Frec.", objUti.parseString(INT_ARL_EXC_QUI_CATFRE)},
                {"Cat. Tuv.", objUti.parseString(INT_ARL_EXC_QUI_CATTUV)},
                {"Cat. Clase.", objUti.parseString(INT_ARL_EXC_QUI_CATCLA)},
                
            };
        }
    }  
    
    public  String buscarColumnaSeleccionada(String[][] datos, String clave) {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0].equals(clave)) {
                return datos[i][1]; // Retorna el valor de la segunda columna
            }
        }
        return null; // Si no se encuentra la clave, devuelve null
    } 

    
    private boolean blnCargarArchivo; 
    private int intNumeroItemsProcesar=0;
    private ArrayList arlDatExcel, arlRegExcel;
    boolean blnNoSeleccion=true;
    StringBuilder cadenaColAct = new StringBuilder();
    
    
    private boolean getArchivoExcel(int tipo)
    {
        boolean blnRes = true;
        try{  
            blnCargarArchivo = false;
            intNumeroItemsProcesar=0;
            arlDatExcel = new ArrayList();
            JFileChooser objfileChooser = new JFileChooser();
            objfileChooser.setDialogTitle("Seleccionar archivo Excel Formato (XLSX)");
            objfileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xlsx"/*, "xlsx"*/));

            int userSelection = objfileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                blnNoSeleccion=false;
                File selectedFile = objfileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Aquí puedes hacer lo que necesites con el archivo Excel seleccionado
//                System.out.println("Archivo seleccionado: " + filePath);
                blnCargarArchivo=true;

                FileInputStream fis =   new FileInputStream(filePath);

                Workbook workbook;

                if (filePath.toLowerCase().endsWith(".xlsx"))
                {
                    workbook = new XSSFWorkbook(fis);
                }
                else
                {
                    workbook = new HSSFWorkbook(fis);
                }

                // Leer la primera hoja del libro
                Sheet sheet = workbook.getSheetAt(0);

                // Obtener el número de filas y columnas en la hoja
               
                //int numCols = sheet.getColumns();
                
                int numRows = sheet.getLastRowNum() + 1;
                int numCols = sheet.getRow(0).getLastCellNum();
                
                
                boolean blnExi=false;
                
                String[] columnasExcel = new String[numCols];
                for (int j = 0; j < numCols; j++) {
                    Row row0 = sheet.getRow(0);

                    Cell cell = row0.getCell(j);

                    String valor = "";

                    if (cell != null)
                    {
                        cell.setCellType(CellType.STRING);

                        valor =
                            cell.getStringCellValue().trim();
                    }

                    columnasExcel[j] = valor;
                }               
                
                /**/
                
                if (!validarColumnasExcel(columnasExcel, ColumnasExcel)) {
                    blnCargarArchivo = false;
                    //mostrarMsgInf("El archivo Excel NO cumple con la estructura requerida.");
                    return false;
                }                

                
                String columna ="";
                cadenaColAct = new StringBuilder(); 
                for (int i = 0; i <=0; i++) {
                    // Iterar sobre las columnas de la fila actual

                    for (int j = 0; j < numCols; j++) 
                    {
                        Row row = sheet.getRow(i);

                        Cell cell = row.getCell(j);

                        String cellValue = "";

                        if (cell != null)
                        {
                            cell.setCellType(CellType.STRING);

                            cellValue =
                                cell.getStringCellValue().trim();
                        }
                        if (j==0)
                        {
                            if (cellValue.equals(""))
                                blnExi=true;
                        }   
                           columna = buscarColumnaSeleccionada(ColumnasExcel,cellValue);
                           if (columna!=null)
                              cadenaColAct.append(cellValue).append(",");  
                    }
                }
                /**/
                
                if (cadenaColAct.length() > 0) {
                    cadenaColAct.setLength(cadenaColAct.length() - 1); 
                }
                String [] columnasAct = cadenaColAct.toString().split(",");
                arlDatExcel=new ArrayList();
                
                HashSet<String> setItemsValidos = cargarItemsValidos();
                
                Connection conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

                Statement stmGP = conGP.createStatement();
                Map<Integer, Set<String>> mapCatalogosExtender = cargarCatalogosExtender(stmGP);
                
                Map<String, Integer> mapFieldExt =  new HashMap<String, Integer>();
                mapFieldExt.put("CATEGORIA", 206);
                mapFieldExt.put("CLASE", 208);
                mapFieldExt.put("LINEA", 207);
                mapFieldExt.put("MATERIAL", 120);
                mapFieldExt.put("MARCA", 119);
                mapFieldExt.put("CONTROL", 203);
                mapFieldExt.put("COMPRAS", 199);
                
                mapFieldExt.put("CATECOMISION", 209);
                mapFieldExt.put("CLASECOMISION", 211);
                mapFieldExt.put("LINEACOMISION", 210);
                //mapFieldExt.put("SUBCOMPRA", );
                
                // temporal
//                HashSet<String> setPermitidos =  cargarItemsPermitidos(conGP);                
                
                // Iterar sobre las filas de la hoja
                for (int i = 1; i < numRows; i++) 
                {
                    arlRegExcel = new ArrayList();
                    blnExi = false;

                    // Leer la primera columna (ITEM)
                    Row row = sheet.getRow(i);

                    Cell celItem = row.getCell(0);

                    String strItem = ""; 
                    if (celItem != null)
                    {
                        celItem.setCellType(CellType.STRING);

                        strItem =  celItem.getStringCellValue().trim();                        
                        strItem =  strItem.replaceAll("\\s+", "");
                    }
                    
//                    //temporal para chiki
//                    if (
//                        !setPermitidos.contains(
//                            strItem.trim().toUpperCase()
//                        )
//                    )
//                    {
//                        continue;
//                    }
//                    //temporal para chiki
                    

                    // Si el ítem está vacío, saltar esta fila
                    if (strItem.isEmpty()) {
                        continue; // No se toma en cuenta esta fila
                    }

                    // Agregar todas las columnas normalmente
                    for (int j = 0; j < numCols; j++) {
                        Row rowDet = sheet.getRow(i);

                        Cell cell = rowDet.getCell(j);

                        String cellValue = "";

                        if (cell != null)
                        {
                            cell.setCellType(CellType.STRING);

                            cellValue =
                                cell.getStringCellValue().trim();
                        }
                        
                        String nombreCol = columnasExcel[j].trim().toUpperCase();
                        
                        
                        //VALIDAR VALOR EXTENDER
                        Integer fieldId =  mapFieldExt.get(nombreCol);

                        if (fieldId != null && !cellValue.isEmpty())
                        {
                            String errExt = validarValorExtender(mapCatalogosExtender,fieldId,cellValue,nombreCol);

                            if (errExt != null)
                            {
                                mostrarMsgInf("Error en el archivo Excel.\n\n"+ errExt+ "\nFila: " + (i + 1));

                                workbook.close();

                                return false;
                            }
                        }
                       
//                        VALIDAR ITEM EXISTE
//                        if (nombreCol.equals("ITEM")) {
//
//                            if (!setItemsValidos.contains(
//                                    cellValue.trim().toUpperCase()
//                                )) {
//
//                                mostrarMsgInf(
//                                    "Error en el archivo Excel.\n\n" +
//                                    "El ITEM '" + cellValue + "' no existe.\n" +
//                                    "Fila: " + (i + 1)
//                                );
//
//                                workbook.close();
//                                return false;
//                            }
//                        }
                       
                        if (tipo==1){
                           //SOLO PARA PROVTA Y PROTEO (precisión exacta)
                            if ((nombreCol.equals("PROMVTA") || nombreCol.equals("PROTEO"))
                                    && cell.getCellType() == CellType.NUMERIC) {

                                double valorNum = cell.getNumericCellValue();

                                cellValue = new BigDecimal(String.valueOf(valorNum))
                                    .setScale(6, RoundingMode.HALF_UP)
                                    .toPlainString();

                            } else {
                                cell.setCellType(CellType.STRING);

                                cellValue =
                                    cell.getStringCellValue().trim();
                            }

                            //VALIDAR NÚMERO MENOS ITEM
                            //if (!nombreCol.equals("ITEM") && !nombreCol.equals("TRESL") ) {
                            if (!nombreCol.equals("ITEM") && !nombreCol.equals("TRESL") && !nombreCol.equals("3L") && !nombreCol.equals("DESCRIPCION") && !nombreCol.equals("CODZAF") 
                                && !nombreCol.equals("CLASE") && !nombreCol.equals("CATEGORIA") && !nombreCol.equals("LINEA") && !nombreCol.equals("MARCA") 
                                && !nombreCol.equals("COMPRAS") && !nombreCol.equals("CONTROL") && !nombreCol.equals("MATERIAL")
                                && !nombreCol.equals("CATECOMISION") && !nombreCol.equals("CLASECOMISION") && !nombreCol.equals("LINEACOMISION") && !nombreCol.equals("SUBCOMPRA")
                                && !cellValue.isEmpty()) {
                                try {
                                    Double.parseDouble(cellValue);
                                } catch (Exception e) {

                                    mostrarMsgInf(
                                        "Error en el archivo Excel.\n\n" +
                                        "Valor inválido en la columna '" + nombreCol + "'.\n" +
                                        "Fila: " + (i + 1) + "\n\n"
                                        //"Debe ingresar solo números."
                                    );

                                    workbook.close();
                                    return false;
                                }
                            }// fin validar numero
                            
                        }
                        
                        
                        arlRegExcel.add(cellValue);
                        System.out.print(cellValue + "\t");
                    }

                    intNumeroItemsProcesar++;
                    arlDatExcel.add(arlRegExcel);
                    System.out.println();
                }

//                System.out.println("arlDatExcel<<<" + arlDatExcel.toString());
                workbook.close();

            }
            else{
                blnRes=false;
                mostrarMsgInf("No se seleccionó ningun archivo...");
            }
        } 
        catch (IOException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  

    
    
    private boolean validarDatosCargados(int tipo)
    {
        boolean blnRes=true;
        strCodItmDynBus="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCuentaItemsEncontrados=0;
        try{
            arlDatExcelVal=new ArrayList();
            if (tipo==1)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM)+"'";

                        if(i+1<arlDatExcel.size()) 
                            strCodItmDynBus+=",";
                        
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_PRI_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_ITEM));
                        arlRegExcelVal.add(INT_EXC_PRI_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    
                }                
            }
            if (tipo==5)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM)+"'";

                        if(i+1<arlDatExcel.size()) {
                            strCodItmDynBus+=",";
                        }
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_SEG_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_SEG_ITEM));
                        arlRegExcelVal.add(INT_EXC_SEG_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    
                }                
            }
            
            if (tipo==3)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_TRE_TRES).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_TRE_TRES)+"'";

                        if(i+1<arlDatExcel.size()) {
                            strCodItmDynBus+=",";
                        }
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_TRE_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_TRE_TRES));
                        arlRegExcelVal.add(INT_EXC_TRE_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    
                }                
            }    

            if (tipo==4)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_CUA_TRES).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_CUA_TRES)+"'";

                        if(i+1<arlDatExcel.size()) {
                            strCodItmDynBus+=",";
                        }
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_CUA_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_CUA_TRES));
                        arlRegExcelVal.add(INT_EXC_CUA_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    
                }                
            } 
            
            if (tipo==2)            
            {
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    if(!objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_QUI_ITEM).equals(""))
                    {
                        strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_QUI_ITEM)+"'";

                        if(i+1<arlDatExcel.size()) 
                            strCodItmDynBus+=",";
                        
                        arlRegExcelVal=new ArrayList();
                        arlRegExcelVal.add(INT_EXC_QUI_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_QUI_ITEM));
                        arlRegExcelVal.add(INT_EXC_QUI_BLN, "N");
                        arlDatExcelVal.add(arlRegExcelVal);
                    }    
                }                
            }
            
            

            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null)
            {
                
                if (strCodItmDynBus.endsWith(",")) 
                {
                    strCodItmDynBus = strCodItmDynBus.substring(0, strCodItmDynBus.length() - 1);
                }
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, TRIM(ITMGEDSC) as ITMGEDSC, a1.ITEMTYPE  "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1 WITH (NOLOCK) "
                       //18112024 + " WHERE a1.ITMGEDSC IN ( "+strCodItmDynBus+" )  and a1.inactive =0"  ;
                        + " WHERE a1.ITMGEDSC IN ( "+strCodItmDynBus+" )  "  ;
                System.out.println(strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                
                if (tipo==1)
                {
                    while(rstLoc.next())
                    { 
                        intCuentaItemsEncontrados++;
                        
                        for(int x=0;x<arlDatExcelVal.size();x++){

                            if(rstLoc.getString("ITMGEDSC").trim().equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM).toString().trim() ))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }
                
                if (tipo==5)
                {
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_SEG_ITEM)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_SEG_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }
                if (tipo==3)
                {
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_TRE_TRES)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_TRE_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }
                
                if (tipo==4)
                {
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_CUA_TRES)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_CUA_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }
                
                if (tipo==2)
                {
                    while(rstLoc.next())
                    { 
                        intCuentaItemsEncontrados++;
                        
                        for(int x=0;x<arlDatExcelVal.size();x++){

                            if(rstLoc.getString("ITMGEDSC").trim().equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_QUI_ITEM).toString().trim() ))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_QUI_BLN, "OK");
                                break;
                            }
                        }
                    }                    
                }
                

                rstLoc.close();
                rstLoc=null;
                
                /*if(intCuentaItemsEncontrados!=intNumeroItemsProcesar){
                    blnResNO=true;
                    
                }*/
                if(intCuentaItemsEncontrados != arlDatExcelVal.size()){
                    blnResNO = true;
                }
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    private HashMap<String, Integer> mapDatItems;     
    
    private boolean validarDatosCargadosGrupo() 
    {
        boolean blnRes = true;
        mapDatItems = new HashMap<>();

        try (SQLServerConnection conLoc = (SQLServerConnection) DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021())) {

            if (conLoc != null) {

                // Quita coma final si existe
                if (strCodItmDynBus.endsWith(",")) {
                    strCodItmDynBus = strCodItmDynBus.substring(0, strCodItmDynBus.length() - 1);
                }

                // Convertir lista "SAN,EZA,PGB" en un SQLServerDataTable
                SQLServerDataTable tvp = new SQLServerDataTable();
                tvp.addColumnMetadata("codigo", java.sql.Types.NVARCHAR);

                String[] codigos = strCodItmDynBus.split(",");
                for (String cod : codigos) {
                    tvp.addRow(cod.trim().replace("'", ""));
                }

                // Llamar al procedimiento
                try (SQLServerCallableStatement cstmt = 
                        (SQLServerCallableStatement) conLoc.prepareCall("{call dbo.spValidarDatosGrupo(?)}")) {
                    cstmt.setStructured(1, "dbo.ItemCodeList", tvp);

                    try (ResultSet rstLoc = cstmt.executeQuery()) {
                        while (rstLoc.next()) {
                            String item = rstLoc.getString("tresL");
                            int detalle = rstLoc.getInt("detalle");
                            mapDatItems.put(item, detalle);
                        }
                    }
                }
            } else {
                blnRes = false;
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
}  
    
    
    private  Map<String, Map<String, Object>> obtenerHistorialItems( String cadenaItems)  
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;    
        String strSql="";
        Map<String, Map<String, Object>> historial = new HashMap<>();
        try
        {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null)
            {
                
                stmLoc = conLoc.createStatement();
                strSql+=" select tx_codalt2,tx_itm, nd_fre, nd_moq, nd_pes, nd_punParMax as nd_cobmax, nd_tra, nd_stkBas "
                        + " FROM Dynamics2021..tbm_inv  WITH (NOLOCK) "
                        + " WHERE tx_codalt2 IN ( "+cadenaItems+" )  "  ;
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    String item = rstLoc.getString("tx_codalt2");

                    Map<String, Object> datos = new HashMap<>();
                    datos.put("tx_itm", rstLoc.getString("tx_itm"));
                    datos.put("nd_fre", rstLoc.getBigDecimal("nd_fre"));
                    datos.put("nd_moq", rstLoc.getBigDecimal("nd_moq"));
                    datos.put("nd_pes", rstLoc.getBigDecimal("nd_pes"));
                    datos.put("nd_cobmax", rstLoc.getBigDecimal("nd_cobmax"));
                    datos.put("nd_tra", rstLoc.getBigDecimal("nd_tra"));
                    datos.put("nd_stkBas", rstLoc.getBigDecimal("nd_stkBas"));
                    
                    historial.put(item, datos);
                }                       
            } 
        }
        catch(java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return historial;
    }
    
         
    private boolean validarColumnasExcel(String[] columnasExcel, String[][] columnasEsperadas) 
    {        
        arrNomColExcel = columnasExcel;        
        boolean blnValido = true;

        ArrayList<String> colCoinciden = new ArrayList<>();
        ArrayList<String> colFaltantes = new ArrayList<>();
        ArrayList<String> colSobrantes = new ArrayList<>();

        HashSet<String> setExcel = new HashSet<>();
        for (String col : columnasExcel) {
            if (col != null)
                setExcel.add(col.trim().toUpperCase());
        }
        
        //VALIDAR QUE EXISTA ITEM O TRESL
        if (!setExcel.contains("ITEM") && !setExcel.contains("TRESL") && !setExcel.contains("3L")) {
            mostrarMsgInf("El archivo Excel debe contener la columna ITEM o TRESL o 3L.");
            return false;
        }

        //VALIDAR SOLO SI EXISTE AL MENOS UNA COLUMNA VÁLIDA
        boolean tieneAlMenosUna = false;

        for (String[] colEsp : columnasEsperadas) {
            String colReq = colEsp[0].trim().toUpperCase();

            if (setExcel.contains(colReq)) {
                colCoinciden.add(colReq);
                tieneAlMenosUna = true;
            } else {
                colFaltantes.add(colReq);
            }
        }

        //NUEVA VALIDACIÓN
        if (!tieneAlMenosUna) {
            mostrarMsgInf("Debe existir al menos una columna válida a modificar.");
            return false;
        }

        // Detectar sobrantes (igual que antes)
        for (String colExcel : setExcel) {
            boolean existe = false;

            for (String[] colEsp : columnasEsperadas) {
                if (colExcel.equals(colEsp[0].trim().toUpperCase())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                colSobrantes.add(colExcel);
            }
        }
        
        if (!colSobrantes.isEmpty()) {
            System.out.println("Advertencia: columnas no reconocidas: " + colSobrantes);
        }

        // Debug
        System.out.println("==== VALIDACIÓN DE COLUMNAS ====");
        System.out.println("Columnas del Excel: " + Arrays.toString(columnasExcel));
        System.out.println("Coinciden: " + colCoinciden);
        System.out.println("Faltantes: " + colFaltantes);
        System.out.println("Sobrantes: " + colSobrantes);

        return true;
    }  
    

    
private boolean cargarArchivoXLS()
     {
        boolean blnRes=true;
        boolean blnCarGrp=false;
        String strAux;
        
        blnHayErroresCarga = false;
       
        try
        {  
            if (validaPes==1)
                ConfiguracionColumnas(1);// PARA LOS ATRIBUTOS
            if (validaPes==2)
                ConfiguracionColumnas(2);// PARA LOS MAXIMO Y MINIMO
            if (validaPes==3)
                ConfiguracionColumnas(3);// PARA LA INACTIVACION MASIVA
            if (validaPes==4)
                ConfiguracionColumnas(4);// PARA LA DESAGRUPACION   
            if (validaPes==5)
                ConfiguracionColumnas(5);// PARA ABC
                
            
            if (getArchivoExcel(validaPes) == true)
            {
                lblMsgSis.setText("Validando datos...");
                pgrSis.setIndeterminate(true);
                if(validarDatosCargados(validaPes))
                {
                    if (validaPes==1)
                    {
                        boolean blnValidaAgrupacion = false;
                        for (int i = 0; i < objTblMod1.getRowCountTrue(); i++)
                        {
                            Map<String, Object> mapValores = obtenerValoresFila(i);                          
                           
                            System.out.println("Fila: " + i);

                            System.out.println("catcomi MAP: " + mapValores.get("catcomi"));
                            
                            Double fre = (Double) mapValores.get("fre");
                            Double moq = (Double) mapValores.get("moq");

                            if (fre != null || moq != null)
                            {
                                blnValidaAgrupacion = true;
                                break;
                            }
                        }
                        if(!blnValidaAgrupacion || validarDatosCargadosGrupo())
                        {
                            Map<String, Map<String, Object>> resultado = obtenerHistorialItems(strCodItmDynBus);
                            if (!resultado.isEmpty())
                            {
                                pgrSis.setIndeterminate(false);
                                pgrSis.setValue(0);
                                if(ingresarDatosCargadosTbl(1))
                                    if (!blnHayErroresCarga) {
                                        mostrarMsgInf("La carga del archivo fue exitosa.");
                                    }
                                else
                                {
                                    blnRes=false;
                                    mostrarMsgInf("No se puedo guardar los datos..."); 
                                }                             
                            }                            
                        }
                    }
                    if ((validaPes==2) || (validaPes==3) || (validaPes==4) || (validaPes==5))
                    {
                        pgrSis.setIndeterminate(false);
                        pgrSis.setValue(0);
                        if(ingresarDatosCargadosTbl(validaPes))
                            mostrarMsgInf("La carga del archivo fue exitosa.");
                        else
                        {
                            blnRes=false;
                            mostrarMsgInf("No se puedo guardar los datos...");
                        }                          
                    }
                                     
                }
                else
                {
                    List<String> lstErrores =  new ArrayList<String>();

                    for(int x=0; x<arlDatExcelVal.size(); x++)
                    {
                        if(objUti.getStringValueAt(arlDatExcelVal,x,INT_EXC_PRI_BLN).equals("N"))
                        {
                            String strItem = objUti.getStringValueAt(arlDatExcelVal,x,INT_ARL_EXC_PRI_ITEM);
                            lstErrores.add(strItem);
                        }
                    }

                    blnRes = false;
                    
                    pgrSis.setIndeterminate(false);
                    pgrSis.setValue(0);
                    lblMsgSis.setText("");
                    
                    mostrarErroresItemsNoExisten(lstErrores);                    
                }
                
                if (blnResNO)//PARA ENVIAR UNA NOTIFICACION DE ITEMS QUE NO ESTAN INGRESADOS EN EL MAESTRO
                {
                    List<String> lstErrores = new ArrayList<String>();

                    for(int x=0; x<arlDatExcelVal.size(); x++)
                    {
                        if(objUti.getStringValueAt(arlDatExcelVal,x,INT_EXC_PRI_BLN).equals("N"))
                        {
                            String strItem = objUti.getStringValueAt(arlDatExcelVal,x,INT_ARL_EXC_PRI_ITEM);
                            lstErrores.add(strItem);
                        }
                    }

                    if (!lstErrores.isEmpty())
                    {
                        blnRes = false;

                        pgrSis.setIndeterminate(false);
                        pgrSis.setValue(0);
                        lblMsgSis.setText("");

                        mostrarErroresItemsNoExisten(lstErrores);
                    }                  
                }                
                
            }
            else{  
                
                if(blnNoSeleccion){
                    blnRes=false;
                }
                else{
                    if (blnCargarArchivo == false){   
                        strAux =  "La carga del archivo no fue exitosa." + "\n\n";
                        strAux += "Notifique esta novedad al Administrador del sistema.";
                        mostrarMsgInf(strAux);
                    }
                }
            }
                 
        }
         
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 


    private Map<String, String> obtenerItemsConAgrupacion(List<String> lstItems)
    {
        Map<String, String> mapAgrupados = new HashMap<>();        
        

        Connection conGP = null;
        Statement stmGP = null;
        ResultSet rstGP = null;

        try {
            conGP = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (conGP != null) {

                stmGP = conGP.createStatement();

                
                StringBuilder inClause = new StringBuilder();

                for (int i = 0; i < lstItems.size(); i++) {
                    if (i > 0) inClause.append(",");
                    inClause.append("'").append(lstItems.get(i)).append("'");
                }

                String sql =
                    "SELECT tx_itm, co_grpcom, co_detgrpcom " +
                    "FROM tbm_inv WITH (NOLOCK) " +
                    "WHERE tx_itm IN (" + inClause.toString() + ") " +
                    //"AND (co_grpcom > 0 OR co_detgrpcom > 0)";
                    "AND co_detgrpcom > 0";

                System.out.println("obtenerItemsConAgrupacion:\n" + sql);

                rstGP = stmGP.executeQuery(sql);

                while (rstGP.next()) {
                    String item = rstGP.getString("tx_itm").trim();
                    int grp = rstGP.getInt("co_grpcom");
                    int det = rstGP.getInt("co_detgrpcom");
                    
                    String linea = String.format(
                    "%-15s %-5s %-10d %-10d",
                    item, "", grp, det
                    );

                    mapAgrupados.put(item, linea);
                }

                rstGP.close();
                stmGP.close();
                conGP.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapAgrupados;
    }

    private int obtenerIndiceColumna(String nombreCol) {
        if (arrNomColExcel == null) return -1;

        for (int i = 0; i < arrNomColExcel.length; i++) {
            if (arrNomColExcel[i] != null &&
                arrNomColExcel[i].trim().equalsIgnoreCase(nombreCol)) {
                return i;
            }
        }
        return -1;
    }
    
    private void cargarIndicesColumnas(int tipo) {

        if (tipo == 1)
        {
            colFre = obtenerIndiceColumna("FRECUENCIA");
            colSto = obtenerIndiceColumna("STOCKBASE");
            colLea = obtenerIndiceColumna("LEADTIME");
            colMoq = obtenerIndiceColumna("MOQ");
            colCob = obtenerIndiceColumna("COBMAX");
            colPes = obtenerIndiceColumna("PESO");
            colProVta = obtenerIndiceColumna("PROMVTA");
            colProTeo = obtenerIndiceColumna("PROTEO");
            colDes = obtenerIndiceColumna("DESCRIPCION");
            colCodZaf = obtenerIndiceColumna("CODZAF");
            colCla = obtenerIndiceColumna("CLASE");
            colCat = obtenerIndiceColumna("CATEGORIA");
            colLin = obtenerIndiceColumna("LINEA");
            colMar = obtenerIndiceColumna("MARCA");
            colCon = obtenerIndiceColumna("CONTROL");
            colCom = obtenerIndiceColumna("COMPRAS");
            colMat = obtenerIndiceColumna("MATERIAL");
            
            colCatComi = obtenerIndiceColumna("CATECOMISION");
            colClaComi = obtenerIndiceColumna("CLASECOMISION");
            colLinComi = obtenerIndiceColumna("LINEACOMISION");
            colSubCom = obtenerIndiceColumna("SUBCOMPRA");
        }
        if (tipo == 2)
        {
           colCatVen = obtenerIndiceColumna("Cat. Ventas");
           colCatPes = obtenerIndiceColumna("Cat. Peso");
           colCatFre = obtenerIndiceColumna("Cat. Frec.");
           colCatTuv = obtenerIndiceColumna("Cat. Tuv.");
           colClase = obtenerIndiceColumna("Cat. Clase");
        }
        
        
    }

    
    private String validarAtributosItem(String item, Double fre, Double sto, Double lea, Double moq, Double cob, Double pes, Double provta,
                                    Double proteo, String des, String codzaf, String cla, String cat, String lin, String mar, String con, String com, String mat) 
    {

        // 1. Validaciones numéricas (se mantienen igual)
        if (moq != null && (moq < 0 || moq % 1 != 0)) return "MOQ INVÁLIDO";
        if (fre != null && fre < 0) return "FRECUENCIA NEGATIVA";
        if (lea != null && lea < 0) return "LEAD TIME NEGATIVO";
        if (sto != null && sto < 0) return "STOCK BASE NEGATIVO";
        if (cob != null && cob < 0) return "COBERTURA NEGATIVA";
        if (pes != null && pes < 0) return "PESO NEGATIVO";
        if (provta != null && provta < 0) return "PROMEDIO VENTA NEGATIVO";
        if (proteo != null && proteo < 0) return "PROMEDIO TEORICO NEGATIVO";

        // 2. Validación de textos usando el método optimizado con Regex
        String error;

        if ((error = validarTexto(des, "DESCRIPCION", 110)) != null) return error;
        if ((error = validarTexto(codzaf, "CODIGO ZAFIRO", 110)) != null) return error;

        // 3. Nuevos atributos en bloque
        String[][] atributos = {
            {cla, "CLASE"}, {cat, "CATEGORIA"}, {lin, "LINEA"}, 
            {mar, "MARCA"}, {con, "CONDICION"}, {com, "COMPONENTE"}, {mat, "MATERIAL"}
        };

        for (String[] atr : atributos) {
            if ((error = validarTexto(atr[0], atr[1], 100)) != null) return error;
        }

        return null; // Todo OK
    }

    /**
     * Valida largo y caracteres permitidos (Letras, números, espacios y guiones bajos)
     */
    private String validarTexto(String texto, String nombreCampo, int largoMax) {
        if (texto == null || texto.isEmpty()) return null;

        if (texto.length() > largoMax) {
            return nombreCampo + " DEMASIADO LARGO";
        }

        // Tu Regex: Acepta Alfanuméricos, espacios y guiones bajos
        /*if (!texto.matches("[A-Za-z0-9_]./+")) {
            return nombreCampo + " CON CARACTERES INVALIDOS";
        }*/

        return null;
    }
    
    
    private Double getDoubleNullable(ArrayList arlDatExcel, int row, int col) {

        if (col == -1) return null;

        Object obj = ((ArrayList) arlDatExcel.get(row)).get(col);

        if (obj == null) return null;

        String txt = obj.toString().trim();

        if (txt.isEmpty()) return null;

        return Double.parseDouble(txt);
    }

      private HashSet<String> cargarItemsValidos(){

        HashSet<String> setItems = new HashSet<String>();

        Connection conLoc = null;
        Statement stmLoc = null;
        ResultSet rstLoc = null;

        try{

            conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
            );

            String strSQL =
                "SELECT tx_codalt2 " +
                "FROM tbm_inv WITH (NOLOCK)";

            stmLoc = conLoc.createStatement();
            rstLoc = stmLoc.executeQuery(strSQL);

            while(rstLoc.next()){
                setItems.add(rstLoc.getString("tx_codalt2").trim().toUpperCase());
            }
        }
        catch(Exception e){

            objUti.mostrarMsgErr_F1(this, e);

        }
        finally{

            try{
                if(rstLoc != null) rstLoc.close();
                if(stmLoc != null) stmLoc.close();
                if(conLoc != null) conLoc.close();
            }
            catch(Exception e){}

        }

        return setItems;
    }
    
    private Map<String, String> cargarMapaItems(Statement stmGP)
            throws SQLException {

        Map<String, String> mapItemReal =
            new HashMap<String, String>();

        String sqlMap =
            "SELECT tx_codalt2, tx_itm FROM tbm_inv";

        ResultSet rsMap = stmGP.executeQuery(sqlMap);

        while (rsMap.next()) {

            mapItemReal.put(
                rsMap.getString("tx_codalt2")
                    .trim()
                    .toUpperCase(),

                rsMap.getString("tx_itm")
                    .trim()
            );
        }

        rsMap.close();

        return mapItemReal;
    }
    
    private List<String> obtenerItemsExcel(
            Map<String, String> mapItemReal){

        List<String> lstItemsExcel =
            new ArrayList<String>();

        for (int i = 0;
                i < objTblMod1.getRowCountTrue();
                i++) {

            String strCodRaw =
                objTblMod1.getValueAt(
                    i,
                    INT_ARL_TAB_PRI_ITEM
                ).toString().trim().toUpperCase();

            String strItemReal =
                mapItemReal.get(strCodRaw);

            if (strItemReal != null
                    && !strItemReal.trim().equals("")) {

                lstItemsExcel.add(strItemReal);

            }
        }

        return lstItemsExcel;
    }
    
    
    private boolean validarAgrupacion(Map<String, String> mapAgrupados, String strItemReal,
            String strCodRaw,Double fre, Double moq)
    {

        if (mapAgrupados.containsKey(strItemReal) && (fre != null || moq != null) ) {

            String lineaBD = mapAgrupados.get(strItemReal);

            String[] partes = lineaBD.trim().split("\\s+");

            int grp = Integer.parseInt(partes[1]);

            int det = Integer.parseInt(partes[2]);

            String lineaFinal = String.format(
                "%-15s %-6s %-10d %-10d %-20s",
                strItemReal,
                strCodRaw,
                grp,
                det,
                "TIENE AGRUPACION"
            );

            lstNoProcesados.add(lineaFinal);

            huboErrores = true;

            return true;
        }

        return false;
    }
    
   
    
    private String armarCamposUpdate(Double fre,Double sto,Double lea,Double moq,Double cob,Double pes,BigDecimal provta,BigDecimal proteo,String des,
                String codzaf,String cla,String cat,String lin,String mar,String con,String com,String mat, String catcomi, String clacomi, String lincomi){

        String strSet = "";

        if (fre != null) strSet += "nd_fre = " + fre + ",";

        if (sto != null) strSet += "nd_stkBas = " + sto + ",";

        if (lea != null) strSet += "nd_tra = " + lea + ",";

        if (moq != null) strSet += "nd_moq = " + moq + ",";

        if (cob != null) strSet += "nd_cobmax = " + cob + ",";

        if (pes != null) strSet += "nd_pes = " + pes + ",";

        if (provta != null) 
        {
            strSet += "nd_proVenMenAnt = nd_proAnuVenMan,"; strSet += "nd_proanuvenman = " + provta.toPlainString() + ",";
        }

        if (proteo != null) strSet += "nd_proventeo = " + proteo.toPlainString() + ",";
        
        if (des != null)   strSet += "tx_nomitm = " + objUti.codificar(des) + ",";
        
        if (codzaf != null) strSet += "tx_codalt = " + objUti.codificar(codzaf) + ",";
        
        if (cla != null) strSet += "tx_atrcla = " + objUti.codificar(cla) + ",";

        if (cat != null) strSet += "tx_atrCat = " + objUti.codificar(cat) + ",";

        if (lin != null) strSet += "tx_atrLin = "  + objUti.codificar(lin) + ",";

        if (mat != null) strSet += "tx_atrMat = " + objUti.codificar(mat) + ",";

        if (mar != null) strSet += "tx_atrMar = " + objUti.codificar(mar) + ",";

        if (con != null) strSet += "tx_atrCon = " + objUti.codificar(con) + ",";

        if (com != null) strSet += "tx_atrCom = " + objUti.codificar(com) + ",";
        
        if (clacomi != null) strSet += "tx_atrclacom = " + objUti.codificar(clacomi) + ",";

        if (catcomi != null) strSet += "tx_atrCatcom = " + objUti.codificar(catcomi) + ",";

        if (lincomi != null) strSet += "tx_atrLincom = "  + objUti.codificar(lincomi) + ",";
         
        if (!strSet.equals("")) {
            strSet = strSet.substring(0, strSet.length() - 1);
        }
        
        return strSet;
    }
    
    private String armarCamposUpdatePg(Double pes,String descripcion,String codzaf,BigDecimal provta) {

        String strSet = "";

        if (pes != null)
            strSet += "nd_pesitmkgr = " + pes + ",";

        if (descripcion != null)
            strSet += "tx_nomitm = " + objUti.codificar(descripcion) + ",";

        if (codzaf != null)
            strSet += "tx_codalt = " + objUti.codificar(codzaf) + ","; 
        
        if (provta != null)
        {
            strSet += "nd_promenvtaman = "  + provta.toPlainString() + ",";
        }
                
        if (!strSet.equals("")) {

            strSet += "co_usrmod = " + getCodigoUsuarioModificacion() + ",";

            strSet += "fe_ultmod = CURRENT_TIMESTAMP";
        }
        
        return strSet;
    }
    
    private String armarCamposUpdateIV00101(String descripcion,String codzaf)
    {
        String strSet = "";

        if (descripcion != null)
        {
            strSet +=
                "ITEMDESC = "
                + objUti.codificar(descripcion)
                + ",";
        }

        if (codzaf != null)
        {
            strSet +=
                "ITMSHNAM = "
                + objUti.codificar(codzaf)
                + ",";
        }

        if (!strSet.equals(""))
        {
            strSet +=
                "MODIFDT = CONVERT(DATE, GETDATE())";
        }

        return strSet;
    }
    
    
    private void actualizaExtenderPeso(Connection conExt,String item,int fieldId,Double valor,String usuario,String strCod) throws Exception
    {
        CallableStatement cal = null;
        try
        {
            if (conExt != null && valor != null)
            {
                String strSql =
                    "{call SP_INSER_MODI_EXTENDER_PESO(?, ?, ?, ?, ?)}";

                cal = conExt.prepareCall(strSql);

                cal.setString("ITEM", item);
                cal.setInt("FIELD_ID", fieldId);
                cal.setDouble("VALOR", valor);
                cal.setString("USUARIO", usuario);

                cal.registerOutParameter("intRes",java.sql.Types.INTEGER);

                cal.execute();

                int intCodRes = cal.getInt("intRes");

                if (intCodRes == 1)
                {
                    throw new Exception("Error al actualizar EXTENDER Peso item: "+ strCod);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if (cal != null)
                {
                    cal.close();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
    }
    
    private void actualizaOtrosExtender(Connection conExt,String item,int fieldId,String valor,String usuario,String nombreCampo,String strCod) throws Exception
    {
        CallableStatement cal = null;
        System.out.println("item: "+item);
        System.out.println("fieldId: "+fieldId);
        System.out.println("valor: "+valor);
        System.out.println("nombreCampo: "+nombreCampo);
        System.out.println("strCod: "+strCod);
        
        try
        {
            if (conExt != null && valor != null)
            {
                String strSql =
                    "{call SP_INSER_MODI_EXTENDER_OTROS(?, ?, ?, ?, ?)}";

                cal = conExt.prepareCall(strSql);

                cal.setString("ITEM", item);

                cal.setInt("FIELD_ID", fieldId);

                cal.setString("VALOR", valor);

                cal.setString("USUARIO", usuario);

                cal.registerOutParameter("intRes",java.sql.Types.INTEGER);

                cal.execute();

                int intCodRes = cal.getInt("intRes");

                if (intCodRes == 1)
                {
                    throw new Exception("Error al actualizar EXTENDER "+ nombreCampo+ " item: "+ strCod);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if (cal != null)
                {
                    cal.close();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
    }
    
    private void actualizaItemsClasiAtrib(Statement stmGP,String strItemReal,String strCod,Double pes,String mat,String mar,String con,String com,String cat,
        String lin,String cla,String catcomi,String lincomi,String clacomi ) throws Exception
    {
        String strSet = "";

        if (pes != null) {
            strSet += "Peso_KG = " + objUti.codificar(String.valueOf(pes)) + ",";
        }

        if (mat != null) {
            strSet += "Material = " + objUti.codificar(mat) + ",";
        }

        if (mar != null) {
            strSet += "Marca = " + objUti.codificar(mar) + ",";
        }

        if (con != null) {
            strSet += "Control = " + objUti.codificar(con) + ",";
        }

        if (com != null) {
            strSet += "compras = " + objUti.codificar(com) + ",";
        }

        if (cat != null) {
            strSet += "CategoriaCompra = " + objUti.codificar(cat) + ",";
        }

        if (lin != null) {
            strSet += "LineaCompra = " + objUti.codificar(lin) + ",";
        }

        if (cla != null) {
            strSet += "ClaseCompra = " + objUti.codificar(cla) + ",";
        }

        if (catcomi != null) {
            strSet += "CategoriaComision = " + objUti.codificar(catcomi) + ",";
        }

        if (lincomi != null) {
            strSet += "LineaComision = " + objUti.codificar(lincomi) + ",";
        }

        if (clacomi != null) {
            strSet += "ClaseComision = " + objUti.codificar(clacomi) + ",";
        }

        if (!strSet.equals("")) {
            strSet = strSet.substring(0, strSet.length() - 1);

            String sql =
                "UPDATE Dynamics2021..tbl_ItemsClasiAtrib "
                + "SET " + strSet + " "
                + "WHERE CodItm = " + objUti.codificar(strItemReal);

            System.out.println(sql);
            stmGP.executeUpdate(sql);
        }
    }
    
    private void actualizaGpArticulosTuvalApi(Statement stmTuvalApi,String strCodRaw,String descripcion,String mar,String mat,Double pes,String cat,String lin,String cla) throws Exception
    {
        String strSet = "";

        if (descripcion != null) {
            strSet += "ART_DESCRIPCION = " + objUti.codificar(descripcion) + ",";
        }

        if (mar != null) {
            strSet += "ART_MARCA = " + objUti.codificar(mar) + ",";
        }

        if (mat != null) {
            strSet += "ART_MATERIAL = " + objUti.codificar(mat) + ",";
        }

        if (pes != null) {
            strSet += "ART_PESO = " + pes + ",";
        }

        if (cat != null) {
            strSet += "cod_categoria = " + objUti.codificar(cat) + ",";
        }

        if (lin != null) {
            strSet += "cod_linea = " + objUti.codificar(lin) + ",";
        }

        if (cla != null) {
            strSet += "cod_clase = " + objUti.codificar(cla) + ",";
        }

        if (!strSet.equals("")) {
            strSet += "ART_FECHA_MODIFICACION = NOW()";

            String sql =
                "UPDATE gp_articulos "
                + "SET " + strSet + " "
                + "WHERE ART_CODIGO_TRES_LETRAS = " + objUti.codificar(strCodRaw);

            System.out.println("gp_articulos: "+sql);

            int intFilAct = stmTuvalApi.executeUpdate(sql);

            if (intFilAct == 0) {
                throw new Exception("No se encontró el item en gp_articulos. Código 3 letras: " + strCodRaw);
            }
        }
    }
    
    
    private Map<String, Object> obtenerValoresFila(int fila){

        Map<String, Object> map = new HashMap<String, Object>();

        Object objFre = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_FRE);
        Object objSto = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_STO);
        Object objLea = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_LEA);
        Object objMoq = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_MOQ);
        Object objCob = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_COB_MAX);
        Object objPes = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_PES);
        Object objProVta = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_PROVTA);
        Object objProTeo = objTblMod1.getValueAt(fila,INT_ARL_TAB_PRI_PROTEO);        
        Object objDes = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_DESC);        
        Object objCodZaf = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CODZAF);        
        Object objCla = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CLA);
        Object objCat = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CAT);
        Object objLin = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_LINE);
        Object objMar = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_MAR);
        Object objCon = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CON);
        Object objCom = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_COM);
        Object objMat = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_MAT);
        
        Object objCatComi = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CAT_COMI_VTA);
        Object objClaComi = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_CLA_COMI_VTA);
        Object objLinComi = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_LIN_COMI_VTA);
        Object objSubCom = objTblMod1.getValueAt(fila, INT_ARL_TAB_PRI_COM_SUB);
        
        

        Double fre = (objFre != null && !objFre.toString().trim().isEmpty())? Double.parseDouble(objFre.toString()): null;
        Double sto =  (objSto != null && !objSto.toString().trim().isEmpty())? Double.parseDouble(objSto.toString()): null;
        Double lea = (objLea != null && !objLea.toString().trim().isEmpty())? Double.parseDouble(objLea.toString()): null;
        Double moq = (objMoq != null&& !objMoq.toString().trim().isEmpty())? Double.parseDouble(objMoq.toString()): null;
        Double cob =(objCob != null && !objCob.toString().trim().isEmpty())? Double.parseDouble(objCob.toString()): null;
        Double pes = (objPes != null && !objPes.toString().trim().isEmpty()) ? Double.parseDouble(objPes.toString()): null;
        BigDecimal provta = (objProVta != null && !objProVta.toString().trim().isEmpty()) ? new BigDecimal(objProVta.toString()): null;
        BigDecimal proteo = (objProTeo != null && !objProTeo.toString().trim().isEmpty()) ? new BigDecimal(objProTeo.toString()): null;        
        String descripcion = (objDes != null&& !objDes.toString().trim().isEmpty())? objDes.toString().trim(): null;        
        String codzaf = (objCodZaf != null&& !objCodZaf.toString().trim().isEmpty())? objCodZaf.toString().trim(): null;
        String cla = (objCla != null&& !objCla.toString().trim().isEmpty())? objCla.toString().trim(): null;
        String cat = (objCat != null&& !objCat.toString().trim().isEmpty())? objCat.toString().trim(): null;
        String lin = (objLin != null&& !objLin.toString().trim().isEmpty())? objLin.toString().trim(): null;
        String mar = (objMar != null&& !objMar.toString().trim().isEmpty())? objMar.toString().trim(): null;
        String con = (objCon != null&& !objCon.toString().trim().isEmpty())? objCon.toString().trim(): null;
        String com = (objCom != null&& !objCom.toString().trim().isEmpty())? objCom.toString().trim(): null;
        String mat = (objMat != null&& !objMat.toString().trim().isEmpty())? objMat.toString().trim(): null;

        String catcomi = (objCatComi != null&& !objCatComi.toString().trim().isEmpty())? objCatComi.toString().trim(): null;
        String clacomi = (objClaComi != null&& !objClaComi.toString().trim().isEmpty())? objClaComi.toString().trim(): null;
        String lincomi = (objLinComi != null&& !objLinComi.toString().trim().isEmpty())? objLinComi.toString().trim(): null;
        String subcom = (objSubCom != null&& !objSubCom.toString().trim().isEmpty())? objSubCom.toString().trim(): null;        
        
        
        map.put("fre", fre);
        map.put("sto", sto);
        map.put("lea", lea);
        map.put("moq", moq);
        map.put("cob", cob);
        map.put("pes", pes);
        map.put("provta", provta);
        map.put("proteo", proteo);
        map.put("descripcion", descripcion);
        map.put("codzaf", codzaf);
        map.put("cla", cla);
        map.put("cat", cat);
        map.put("lin", lin);
        map.put("mar", mar);
        map.put("con", con);
        map.put("com", com);
        map.put("mat", mat);
        
        map.put("catcomi", catcomi);
        map.put("clacomi", clacomi);
        map.put("lincomi", lincomi);
        map.put("subcom", subcom);

        return map;
    }
    
    private void mostrarResumenProceso(){

        int total = objTblMod1.getRowCountTrue();
        int errores = lstNoProcesados.size();
        int ok = total - errores;

        String msgResumen;
        String msgDetalle = "";
        
        if (errores == 0) {

            msgResumen =
                "Se procesaron " + total + " registros.\n"
                + "Se modificaron correctamente todos los registros.";

        } else {

            msgResumen =
                "Se procesaron " + total + " registros.\n"
                + "Se modificaron correctamente " + ok + " registros.\n"
                + "No se pudieron actualizar " + errores + " registros.";

            // ===== DETALLE =====
            msgDetalle =
                msgResumen + "\n\nDetalle:\n\n";

            msgDetalle += String.format(
                "%-15s %-6s %-10s %-10s %-20s\n",
                "ITEM",
                "3L",
                "COGRP",
                "CODET",
                "MOTIVO"
            );

            msgDetalle +=
                "---------------------------------------------------------------\n";

            for (String it : lstNoProcesados) {

                msgDetalle += it + "\n";

            }

            txtLog.setText(msgDetalle);

            tabFrm.setSelectedComponent(panDatLog);
        }

        mostrarMsgInf(msgResumen);
    }
    
    private void mostrarErroresValidacion(
        List<String> lstErrores) {

        String msg =
            "Existen errores en el archivo.\n\n"
            + "Revise la pestaña LOG/ERRORES.";

        String detalle =
            "ERRORES EN VALIDACION\n\n";

        for (String err : lstErrores) {

            detalle += err + "\n";

        }

        txtLog.setText(detalle);

        tabFrm.setSelectedComponent(panDatLog);

        mostrarMsgInf(msg);
    }
    
    private void mostrarErroresItemsNoExisten(
        List<String> lstErrores)
    {
        String msg =
            "Existen items que no fueron encontrados.\n\n"
            + "Revise la pestaña LOG/ERRORES.";

        String detalle =
            "ITEMS NO ENCONTRADOS\n\n";

        for (String err : lstErrores)
        {
            detalle += err + "\n";
        }

        txtLog.setText(detalle);

        tabFrm.setSelectedComponent(panDatLog);

        mostrarMsgInf(msg);
    }
    
    
    private void ocultarColumnasNoUsadas() {        

        ArrayList arlColOcu = new ArrayList();

        //cargarIndicesColumnas();

        if (!tieneDatosExcel(colFre)) arlColOcu.add("" + INT_ARL_TAB_PRI_FRE);
        if (!tieneDatosExcel(colSto)) arlColOcu.add("" + INT_ARL_TAB_PRI_STO);
        if (!tieneDatosExcel(colLea)) arlColOcu.add("" + INT_ARL_TAB_PRI_LEA);
        if (!tieneDatosExcel(colMoq)) arlColOcu.add("" + INT_ARL_TAB_PRI_MOQ);
        if (!tieneDatosExcel(colCob)) arlColOcu.add("" + INT_ARL_TAB_PRI_COB_MAX);
        if (!tieneDatosExcel(colPes)) arlColOcu.add("" + INT_ARL_TAB_PRI_PES);
        if (!tieneDatosExcel(colProVta)) arlColOcu.add("" + INT_ARL_TAB_PRI_PROVTA);
        if (!tieneDatosExcel(colProTeo)) arlColOcu.add("" + INT_ARL_TAB_PRI_PROTEO);
        if (!tieneDatosExcel(colDes)) arlColOcu.add("" + INT_ARL_TAB_PRI_DESC);        
        if (!tieneDatosExcel(colCodZaf)) arlColOcu.add("" + INT_ARL_TAB_PRI_CODZAF);
        if (!tieneDatosExcel(colCla)) arlColOcu.add("" + INT_ARL_TAB_PRI_CLA);
        if (!tieneDatosExcel(colCat)) arlColOcu.add("" + INT_ARL_TAB_PRI_CAT);
        if (!tieneDatosExcel(colLin)) arlColOcu.add("" + INT_ARL_TAB_PRI_LINE);
        if (!tieneDatosExcel(colMar)) arlColOcu.add("" + INT_ARL_TAB_PRI_MAR);
        if (!tieneDatosExcel(colCon)) arlColOcu.add("" + INT_ARL_TAB_PRI_CON);
        if (!tieneDatosExcel(colCom)) arlColOcu.add("" + INT_ARL_TAB_PRI_COM);
        if (!tieneDatosExcel(colMat)) arlColOcu.add("" + INT_ARL_TAB_PRI_MAT);
        
        if (!tieneDatosExcel(colCatComi)) arlColOcu.add("" + INT_ARL_TAB_PRI_CAT_COMI_VTA);
        if (!tieneDatosExcel(colClaComi)) arlColOcu.add("" + INT_ARL_TAB_PRI_CLA_COMI_VTA);
        if (!tieneDatosExcel(colLinComi)) arlColOcu.add("" + INT_ARL_TAB_PRI_LIN_COMI_VTA);
        if (!tieneDatosExcel(colSubCom)) arlColOcu.add("" + INT_ARL_TAB_PRI_COM_SUB);

        objTblMod1.setSystemHiddenColumns(arlColOcu,tblCar1);        
    }
    
    private void resetearIndicesColumnas()
        {
            colFre = -1;
            colSto = -1;
            colLea = -1;
            colMoq = -1;
            colCob = -1;
            colPes = -1;
            colProVta = -1;
            colProTeo = -1;
            colDes = -1;
            colCodZaf = -1;

            colCla = -1;
            colCat = -1;
            colLin = -1;
            colMar = -1;
            colCon = -1;
            colCom = -1;
            colMat = -1;
            
            colCatComi = -1;
            colClaComi = -1;
            colLinComi = -1;
            colSubCom= -1;
            
        }
    
    
    private boolean tieneDatosExcel(int colExcel) {

        System.out.println(
            "COL EXCEL: " + colExcel
        );
        
        if (colExcel == -1)
            return false;

        for (int i = 0; i < arlDatExcel.size(); i++) {

            ArrayList fila =
                (ArrayList) arlDatExcel.get(i);

            Object obj = fila.get(colExcel);
            
            System.out.println("FILA: " + i + " VALOR=[" + obj + "]");


            if (obj != null
                && !obj.toString().trim().isEmpty()) {

                return true;
            }
        }

        return false;
    }
    
    private boolean existeDatosColumnas(int tipo) {

        cargarIndicesColumnas(tipo);

        if (tipo == 1)
        {
            return
                tieneDatosExcel(colFre) || tieneDatosExcel(colSto) || tieneDatosExcel(colLea) || tieneDatosExcel(colMoq) || tieneDatosExcel(colCob)|| 
                tieneDatosExcel(colPes) || tieneDatosExcel(colProVta) || tieneDatosExcel(colProTeo) || tieneDatosExcel(colDes) || tieneDatosExcel(colCodZaf) || 
                tieneDatosExcel(colCla) || tieneDatosExcel(colCat) || tieneDatosExcel(colLin) || tieneDatosExcel(colMar) || tieneDatosExcel(colCon) ||
                tieneDatosExcel(colCom) || tieneDatosExcel(colMat) || tieneDatosExcel(colCatComi) || tieneDatosExcel(colClaComi) || tieneDatosExcel(colLinComi) ;
        }
        
        if (tipo == 2)
        {
            return
                tieneDatosExcel(colCatVen)|| tieneDatosExcel(colCatPes)|| tieneDatosExcel(colCatFre)|| tieneDatosExcel(colCatTuv);    
        }
        
         return false;
        
    }
   
    
    private Map<Integer, Set<String>> cargarCatalogosExtender(Statement stmGP) throws SQLException
    {
        Map<Integer, Set<String>> mapCatalogos = new HashMap<Integer, Set<String>>();

        String sql =
            "SELECT "
            + "e20.Field_ID, "
            + "e12.UD_Form_Field_Desc "
            + "FROM TUVAL.dbo.EXT20010 e20 "
            + "INNER JOIN TUVAL.dbo.EXT01200 e12 "
            + "ON e12.Extender_Form_ID = e20.FIELDNAM "
            + "WHERE e20.Field_ID IN "
            + "(206,211,209,208,207,210,120,119,203,199,201)"
            + "ORDER BY "
            + "e20.Field_ID, "
            + "e12.UD_Form_Field_Desc";

        ResultSet rs = stmGP.executeQuery(sql);

        while (rs.next())
        {
            int fieldId = rs.getInt("Field_ID");

            String valor = rs.getString("UD_Form_Field_Desc");

            if (valor != null)
            {
                valor = valor.trim().toUpperCase();
            }

            Set<String> setValores =  mapCatalogos.get(fieldId);

            if (setValores == null)
            {
                setValores =  new HashSet<String>();

                mapCatalogos.put(fieldId,setValores);
            }

            setValores.add(valor);
            //System.out.println("FIELD_ID: " + fieldId + " -> "+ valor);
        }

        rs.close();

        return mapCatalogos;
    }
    
    
    private String validarValorExtender(
            Map<Integer, Set<String>> mapCatalogos,
            int fieldId,
            String valor,
            String nombreCampo
    )
    {
        if (valor == null)
        {
            return null;
        }

        Set<String> setValores =
            mapCatalogos.get(fieldId);

        if (setValores == null)
        {
            return nombreCampo
                + " SIN CATALOGO";
        }

        boolean blnExiste = false;

        String valExcel =  normalizarTexto(valor);

        for (String valBD : setValores)
        {
            if (
                normalizarTexto(valBD)
                    .equals(valExcel)
            )
            {
                blnExiste = true;
                break;
            }
        }

        if (!blnExiste)
        {
            return nombreCampo
                + " INVALIDO: "
                + valor;
        }

        return null;
    }
    
    private String normalizarTexto(String txt)
    {
        if (txt == null)
            return "";

        txt = txt.trim().toUpperCase();

        txt = java.text.Normalizer.normalize(
            txt,
            java.text.Normalizer.Form.NFD
        );

        txt = txt.replaceAll(
            "\\p{InCombiningDiacriticalMarks}+",
            ""
        );

        return txt;
    }
    
    
    
    ////ABC
     
    private Integer obtenerCodigoNivel(Connection con, String nivel)
    {         
        Integer coNivPar = null;

        PreparedStatement pst = null;
        ResultSet rst = null;

        try
        {
            String strSql =
                "SELECT co_nivpar " +
                "FROM tbm_nivpar WITH (NOLOCK) " +
                "WHERE tx_nivpar = ?";

            pst = con.prepareStatement(strSql);

            pst.setString(1, nivel);

            rst = pst.executeQuery();

            if (rst.next())
            {
                coNivPar = rst.getInt("co_nivpar");
            }

        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (rst != null)
                    rst.close();

                if (pst != null)
                    pst.close();
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        return coNivPar;
    }
  
    private void procesarABC(Statement stmGP,String strItemReal,int coCatPar,Integer coNivPar) throws Exception
    {
        if (coNivPar == null)
            return;

        String sql =
            "UPDATE tbr_itmpar "
            + "SET "
            + "co_nivpar = " + coNivPar + ", "
            + "co_usrmod = "+ getCodigoUsuarioModificacion()+ ", "
            + "fe_ultmod = GETDATE() "
            + "WHERE tx_itm = "
            + objUti.codificar(strItemReal)
            + " AND co_catpar = "
            + coCatPar + "; "

            + "INSERT INTO tbh_itmpar ("
            + "co_hist, "
            + "tx_itm, "
            + "co_catpar, "
            + "co_nivpar, "
            + "co_emp, "
            + "co_loc, "
            + "st_reg, "
            + "co_usring, "
            + "fe_ing"
            + ") "
            + "SELECT "
            + "ISNULL(MAX(co_hist),0)+1, "
            + objUti.codificar(strItemReal) + ", "
            + coCatPar + ", "
            + coNivPar + ", "
            + "1, "
            + "1, "
            + "'A', "
            + getCodigoUsuarioModificacion()+ ", "
            + "GETDATE() "
            + "FROM tbh_itmpar "
            + "WHERE tx_itm = "
            + objUti.codificar(strItemReal)
            + " AND co_catpar = "
            + coCatPar;

        System.out.println(sql);

        stmGP.executeUpdate(sql);
    }
    
    
    private Map<String, Integer> obtenerMaxHistorico(Statement stmGP)
    {
        Map<String, Integer> mapMaxHis =
                new HashMap<String, Integer>();

        ResultSet rst = null;

        try
        {
            String strSql = "";

            strSql += " SELECT ";
            strSql += "     tx_itm, ";
            strSql += "     MAX(co_his) AS max_his ";
            strSql += " FROM tbh_inv WITH (NOLOCK) ";
            strSql += " GROUP BY tx_itm ";

            rst = stmGP.executeQuery(strSql);

            while (rst.next())
            {
                String txItm = rst.getString("tx_itm");

                int maxHis =  rst.getInt("max_his");

                mapMaxHis.put(txItm, maxHis);
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (rst != null)
                {
                    rst.close();
                }
            }
            catch (Exception e) {}
        }

        return mapMaxHis;
    }    
       
    
    private void cargarUsuarios() {
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;

        try {
            con = java.sql.DriverManager.getConnection(
                    objParSis.getStringConexion(),
                    objParSis.getUsuarioBaseDatos(),
                    objParSis.getClaveBaseDatos()
            );

            if (con != null) {

                stm = con.createStatement();

                String strSQL = "";
                strSQL += " SELECT co_usr, tx_usr ";
                strSQL += " FROM tbm_usr ";
                strSQL += " WHERE st_reg = 'A' ";                
                strSQL += " ORDER BY tx_usr ";
                //strSQL += " LIMIT 10 ";

                rst = stm.executeQuery(strSQL);

                btnUsr.removeAllItems();
                mapUsuarios.clear();
                
                String strUsrPredeterminado = null;

                while (rst.next()) {

                    int intCodUsr = rst.getInt("co_usr");
                    String strUsr = rst.getString("tx_usr");

                    // En el combo solamente mostramos tx_usr
                    btnUsr.addItem(strUsr);

                    // Pero guardamos su co_usr asociado
                    mapUsuarios.put(strUsr, intCodUsr);
                    
                    // Usuario que queremos seleccionar por defecto
                    if (intCodUsr == 556) {
                        strUsrPredeterminado = strUsr;
                    }
                    
                    // Seleccionar automáticamente co_usr = 556
                    if (strUsrPredeterminado != null) {
                        btnUsr.setSelectedItem(strUsrPredeterminado);
                    }
                }
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        } finally {

            try {
                if (rst != null)
                    rst.close();
            } catch (Exception e) {
            }

            try {
                if (stm != null)
                    stm.close();
            } catch (Exception e) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
    }

    private int getCodigoUsuarioModificacion() {
        try {
            // Si ingresó como ADMIN
            if (objParSis.getCodigoUsuario() == 1) {

                String strUsrSel = (String) btnUsr.getSelectedItem();

                if (strUsrSel != null) {
                    Integer intCodUsr = mapUsuarios.get(strUsrSel);

                    if (intCodUsr != null) {
                        return intCodUsr;
                    }
                }
            }
            // Para cualquier otro usuario se mantiene el comportamiento actual
            return objParSis.getCodigoUsuario();

        } catch (Exception e) {
            return objParSis.getCodigoUsuario();
        }
    }

    private String getNombreUsuarioModificacion() {
        try {
            if (objParSis.getCodigoUsuario() == 1) {

                String strUsrSel = (String) btnUsr.getSelectedItem();

                if (strUsrSel != null && !strUsrSel.trim().equals("")) {
                    return strUsrSel;
                }
            }

            return objParSis.getNombreUsuario();

        } catch (Exception e) {
            return objParSis.getNombreUsuario();
        }
    }

//    private HashSet<String> cargarItemsPermitidos(
//        Connection conGP)
//        throws SQLException
//    {
//        HashSet<String> set =
//            new HashSet<String>();
//
//        String sql =
//            "SELECT tx_codalt2 "
//            + "FROM jp_carga";
//
//        Statement stm =
//            conGP.createStatement();
//
//        ResultSet rs =
//            stm.executeQuery(sql);
//
//        while (rs.next())
//        {
//            set.add(
//                rs.getString("tx_codalt2")
//                    .trim()
//                    .toUpperCase()
//            );
//        }
//
//        rs.close();
//        stm.close();
//
//        return set;
//    }
    
   
}

