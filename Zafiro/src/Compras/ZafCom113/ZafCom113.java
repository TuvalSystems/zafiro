/*
 * ZafCom113.java
 *
 * Created on 05 de enero de 2006, 10:10 PM
 */ 
package Compras.ZafCom113;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafKon.ZafKon;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;  
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;  
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.sql.Connection;
import java.sql.Statement; 
import java.sql.ResultSet;
import java.sql.DriverManager; 
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafUtilGP.HashMapPer.Com113;
import Librerias.ZafUtilGP.HashMapPer.Com113_hist;
import Librerias.ZafUtilGP.HashMapPer.Com113_inv;

import java.awt.Color;
import java.math.BigInteger;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import Librerias.ZafVenCon.ZafVenConItm01;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.EmptyBorder;
import Librerias.ZafVenConGP.ZafVenConItmGP03;
import java.math.RoundingMode;
import Librerias.ZafUtilGP.ZafUtilGP;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom113 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_DOS=4;                 //Código del item (Alterno2).
    static final int INT_TBL_DAT_COD_ALT_3=5;                   //Código del item (Alterno 3).
    static final int INT_TBL_DAT_NOM_ITM=6;                     //Nombre del item.
    static final int INT_TBL_DAT_CHK_SER=7;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_UNI=8;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_DAT_DEC_UNI=9;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_BUT_UNI=10;                     //Botón de consulta.
    static final int INT_TBL_DAT_PES_KGR=11;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_PES_KGR_AUX=12;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_STK_CON=13;                     //Stock consolidado.
    static final int INT_TBL_DAT_COD_GEN=14;                     //Codigo Genereico.
    static final int INT_TBL_DAT_IVA=15;                     //IVA
    static final int INT_TBL_DAT_GRP=16;                     //GRUPO
    static final int INT_TBL_DAT_DET=17;                     //DETALLE
    static final int INT_TBL_DAT_FAC=18;                     //FACTOR    
    
    
    //Configurar columnas del JTable Fijo
    static final int INT_TBL_FIX_LIN=0;                         //Línea
    static final int INT_TBL_FIX_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_FIX_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_FIX_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_FIX_COD_ALT_DOS=4;                 //Código del item (Alterno2).
    static final int INT_TBL_FIX_COD_ALT_3=5;                   //Código del item (Alterno 3).
    static final int INT_TBL_FIX_NOM_ITM=6;                     //Nombre del item.
    static final int INT_TBL_FIX_CHK_SER=7;                     //Nombre del item.
    static final int INT_TBL_FIX_COD_UNI=8;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_FIX_DEC_UNI=9;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_FIX_BUT_UNI=10;                     //Botón de consulta.
    static final int INT_TBL_FIX_PES_KGR=11;                    //Peso en Kilogramos.
    static final int INT_TBL_FIX_PES_KGR_AUX=12;                //Peso en Kilogramos.
    static final int INT_TBL_FIX_STK_CON=13;                    //Stock consolidado.
    static final int INT_TBL_FIX_COD_GEN=14;                    //Codigo Genereico.
    static final int INT_TBL_FIX_IVA=15;                        //IVA DE GP.
    static final int INT_TBL_FIX_GRP=16;                        //GRUPO DE GP. 
    static final int INT_TBL_FIX_DET=17;                        //DETALLE DEL GRUPO  DE GP. 
    static final int INT_TBL_FIX_FAC=18;                        //FACTOR    
   
    static final int INT_TBL_DAT_NUM_TOT_CES=15;                 //Número total de columnas estáticas.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafHisTblBasDatGP objHisTblBasDatGP;    
    private ZafTblFilCab objTblFilCab, objTblFilCabFix;
    private ZafTblMod objTblMod, objTblModFix;
    private ZafThreadGUI objThrGUI;
    private ZafThGuarGUI objThrGuaGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblFix;                                            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer, objTblCelRenLblDerFix;                                      //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum, objTblCelRenLblNumFix;                                      //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblColDinIngImp, objTblCelRenLblColDinIngImpFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinCosUni, objTblCelRenLblColDinCosUniFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPedEmb, objTblCelRenLblColDinPedEmbFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinNotPed, objTblCelRenLblColDinNotPedFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinMarNotPed, objTblCelRenLblColDinMarNotPedFix;              //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPreReaMar, objTblCelRenLblColDinPreReaMarFix;              //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinFac, objTblCelRenLblColDinFacFix;                          //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinMarFac, objTblCelRenLblColDinMarFacFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPre, objTblCelRenLblColDinPreFix;                          //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinCanMarPreLisNew, objTblCelRenLblColDinCanMarPreLisNewFix;  //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinFacCl, objTblCelRenLblColDinFacFixCl;                          //Render: Presentar JLabel en JTable (columnas dinámicas)
    
    private ZafTblCelRenLbl objTblCelRenLblColDinModCabDet;  //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinModOtr;  //Render: Presentar JLabel en JTable (columnas dinámicas)

    private ZafTblCelRenLbl objTblCelRenLblColDinCanMarIVA;  //Render: Presentar JLabel en JTable (columnas dinámicas)    
        private ZafTblCelRenLbl objTblCelRenLblColDinFactorPor;  //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinMarPrimero, objTblCelRenLblColDinMarPrimeroFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)chiqui
    private ZafTblCelRenLbl objTblCelRenLblColDinMarUltimo, objTblCelRenLblColDinMarUltimoFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)chiqui
    private ZafTblCelRenLbl objTblCelRenLblColDinMarPrimeraImporta;                    //Render: Presentar JLabel en JTable (columnas dinámicas)chiqui
    private ZafTblCelRenLbl objTblCelRenLblColDinMarUltimaImporta;                    //Render: Presentar JLabel en JTable (columnas dinámicas)chiqui    
    
    private ZafTblCelRenChk objTblCelRenLblColDinChekMa;
    private ZafTblCelRenChk objTblCelRenLblColDinChekMaPrimero;
    private ZafTblCelRenChk objTblCelRenLblColDinChekMaUltimo;
    private ZafTblCelRenChk objTblCelRenLblColDinChekMaPrimeraImporta;
                private ZafTblCelRenChk objTblCelRenLblColDinChekMaUltimaImporta;
    private ZafTblCelRenChk objTblCelRenLblColDinChekOtro;
            private ZafTblCelRenChk objTblCelRenLblColDinChekFactor;
    
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButFix;                        //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkFix;                        //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtFix, objTblCelEdiTxtFixItm, objTblCelEdiTxtFixUniMed;    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPreVta;                                      //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtFacCos;                                      //Editor: JTextField en celda.-
    private ZafTblCelEdiTxt objTblCelEdiTxtMarFacCos;                                   //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtMarFac;                                   //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtMarPrimero;                                   //Editor: JTextField en celda.//chiqui
    private ZafTblCelEdiTxt objTblCelEdiTxtMarUltimo;                                   //Editor: JTextField en celda.//chiqui
    private ZafTblCelEdiTxt objTblCelEdiTxtMarPrimeraImporta;                                   //Editor: JTextField en celda.//chiqui
        private ZafTblCelEdiTxt objTblCelEdiTxtMarUltimaImporta;                                   //Editor: JTextField en celda.//chiqui        
   private ZafTblCelEdiTxt objTblCelEdiTxtFacCl;                                      //Editor: JTextField en celda.-
    
    private ZafTblCelEdiChk objTblCelEdiTxtChekMa;                                  //Editor: JTextField en celda.
        private ZafTblCelEdiChk objTblCelEdiTxtChekFactor;                                  //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiTxtChekMaPrimero;                                  //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiTxtChekMaUltimo;                                  //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiTxtChekMaPrimeraImporta;                                  //Editor: JTextField en celda.
        private ZafTblCelEdiChk objTblCelEdiTxtChekMaUltimaImporta;                                  //Editor: JTextField en celda.        
    private ZafTblCelEdiChk objTblCelEdiTxtChekOtro;                                  //Editor: JTextField en celda.
    
    //private ZafTblCelEdiTxt objTblCelEdiTxtMarUti;                                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPesKgr, objTblCelEdiTxtPesKgrFix;                                      //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPreObjKil;                                   //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMarPreLisNew;                             //Editor: JTextField en celda.
    
    private ZafTblCelEdiTxt objTblCelEdiTxtModCabDet;                             //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtModOtr;                             //Editor: JTextField en celda.
    
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMarIVA;                             //Editor: JTextField en celda.    
        private ZafTblCelEdiTxt objTblCelEdiTxtFactorPor;                             //Editor: JTextField en celda.    
//    private ZafTblCelEdiTxt objTblCelEdiTxtFacCosUni;                                 //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed, objTblCelEdiButVcoUniMedFix;   //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed, objTblCelEdiTxtVcoUniMedFix;   //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda, objMouMotAdaFix;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;         //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus, objTblBusFix;                                //Editor de búsqueda.
    //private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
////    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenConGP vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoUniMed, vcoUniMedFix;                                //Ventana de consulta "Unidad de medida".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecDatFix, vecCab, vecReg, vecRegFix;
    private Vector vecAux, vecAuxFix;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                       //Contenido del campo al obtener el foco.
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafImp objImp;
        
    private int intNumColAddIngImp, intNumColIniIngImp, intNumColFinIngImp;
    private int intNumColAddCosUni, intNumColIniCosUni, intNumColFinCosUni;
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb;
    private int intNumColAddNotPed, intNumColIniNotPed, intNumColFinNotPed;
    private int intNumColAddMarNotPed, intNumColIniMarNotPed, intNumColFinMarNotPed;
    private int intNumColAddPreReaMar, intNumColIniPreReaMar, intNumColFinPreReaMar;    
////        private int intNumColAddPreReaMar, intNumColIniFactorPor, intNumColFinPreReaMar;   
    private int intNumColAddFac, intNumColIniFac, intNumColFinFac;
    private int intNumColAddFacCl, intNumColIniFacCl, intNumColFinFacCl;
    private int intNumColAddMarFac, intNumColIniMarFac, intNumColFinMarFac;
    private int intNumColAddCheck,intNumColCheck,intNumColIniChk,intNumColFinChk;//chiqui

    
    private int intNumColAddPre, intNumColIniPre, intNumColFinPre;
    private int intNumColAddCanMarPreLisNew, intNumColIniCanMarPreLisNew, intNumColFinCanMarPreLisNew;
    private int intNumColAddCanMarIVA, intNumColIniCanMarIVA, intNumColFinCanMarIVA;    

    private int intNumColAddMarPrimero, intNumColIniMarPrimero, intNumColFinMarPrimero;//chiqui
    private int intNumColAddMarUltimo, intNumColIniMarUltimo, intNumColFinMarUltimo;//chiqui
    private int intNumColAddMarPrimeraImporta, intNumColIniMarPrimeraImporta, intNumColFinMarPrimeraImporta;//chiqui
        private int intNumColAddMarUltimaImporta, intNumColIniMarUltimaImporta, intNumColFinMarUltimaImporta;//chiqui
////    private int intNumColAddMarOtro, intNumColIniMarOtro, intNumColFinMarOtro;//chiqui
    private int nd_validaA=-1,nd_validaB=-1;//CHIQUI
    private int intNumColAddCheckPrimero,intNumColCheckPrimero,intNumColIniChkPrimero,intNumColFinChkPrimero;//chiqui
    private int intNumColAddCheckUltimo,intNumColCheckUltimo,intNumColIniChkUltimo,intNumColFinChkUltimo;//chiqui
    private int intNumColAddCheckPrimeraImporta,intNumColIniChkPrimeraImporta,intNumColFinChkPrimeraImporta;//chiqui
        private int intNumColAddCheckUltimaImporta,intNumColIniChkUltimaImporta,intNumColFinChkUltimaImporta;//chiqui    
    private int intNumColAddCheckOtro,intNumColCheckOtro,intNumColIniChkOtro,intNumColFinChkOtro;//chiqui
    private int intNumColAddCheckFactor,intNumColIniChkFactor,intNumColFinChkFactor;//chiqui
    private int intNumColAddFactorPor, intNumColIniFactorPor, intNumColFinFactorPor;
    
    private int intNumColAddModCabDet,intNumColModCabDet,intNumColIniModCabDet,intNumColFinModCabDet;//chiqui    
    private int intNumColAddModOtr,intNumColModOtr,intNumColIniModOtr,intNumColFinModOtr;//chiqui 
    
    private int intNumColAddFacGrpDet,intNumColFacGrpDet,intNumColIniFacGrpDet,intNumColFinFacGrpDet;//chiqui       
            

private ZafTblCelRenLbl objTblCelRenLblStkCal, objTblCelRenLblStkViaDau, objTblCelRenLblStkDur, objTblCelRenLblStkUioN, 
                            objTblCelRenLblStkUioS, objTblCelRenLblStkMan, objTblCelRenLblStkStoDom, objTblCelRenLblStkCue;
    
    
    private int intNumColEst;
    
    //Ingresos por Importación: Pedidos llegados
    private ArrayList arlRegIngImp, arlDatIngImp;    
    private final int INT_ARL_ING_IMP_COD_EMP=0;
    private final int INT_ARL_ING_IMP_COD_LOC=1;
    private final int INT_ARL_ING_IMP_COD_TIP_DOC=2;
    private final int INT_ARL_ING_IMP_COD_DOC=3;
    private final int INT_ARL_ING_IMP_NUM=4;
    private final int INT_ARL_ING_IMP_COL=5;
    private final int INT_ARL_ING_IMP_EST_COL=6;
    private final int INT_ARL_ING_IMP_FEC=7;
    private final int INT_ARL_ING_IMP_COD_EMP_PED_EMB=8;
    private final int INT_ARL_ING_IMP_COD_LOC_PED_EMB=9;
    private final int INT_ARL_ING_IMP_COD_TIP_DOC_PED_EMB=10;
    private final int INT_ARL_ING_IMP_COD_DOC_PED_EMB=11;
    
    //Pedido embarcado: Pedidos en tránsito
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    
    private final int INT_ARL_PED_EMB_COD_EMP=0;
    private final int INT_ARL_PED_EMB_COD_LOC=1;
    private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_PED_EMB_COD_DOC=3;
    private final int INT_ARL_PED_EMB_NUM=4;
    private final int INT_ARL_PED_EMB_COL=5;
    private final int INT_ARL_PED_EMB_EST_COL=6;
    private final int INT_ARL_PED_EMB_FEC=7;
//    private final int INT_ARL_PED_EMB_COD_EMP_ING_IMP=8;
//    private final int INT_ARL_PED_EMB_COD_LOC_ING_IMP=9;
//    private final int INT_ARL_PED_EMB_COD_TIP_DOC_ING_IMP=10;
//    private final int INT_ARL_PED_EMB_COD_DOC_ING_IMP=11;
    
    //Nota de pedido: Pedidos en tránsito
    private ArrayList arlRegNotPed, arlDatNotPed;    
    private final int INT_ARL_NOT_PED_COD_EMP=0;
    private final int INT_ARL_NOT_PED_COD_LOC=1;
    private final int INT_ARL_NOT_PED_COD_TIP_DOC=2;
    private final int INT_ARL_NOT_PED_COD_DOC=3;
    private final int INT_ARL_NOT_PED_NUM=4;
    private final int INT_ARL_NOT_PED_COL=5;
    private final int INT_ARL_NOT_PED_EST_COL=6;
    private final int INT_ARL_NOT_PED_FEC=7;
    private final int INT_ARL_NOT_PED_COD_EMP_PED_EMB=8;
    private final int INT_ARL_NOT_PED_COD_LOC_PED_EMB=9;
    private final int INT_ARL_NOT_PED_COD_TIP_DOC_PED_EMB=10;
    private final int INT_ARL_NOT_PED_COD_DOC_PED_EMB=11;
    
    private ArrayList arlRegExpExc, arlDatExpExc;
    private final int INT_ARL_EXP_EXC_DAT_LIN=0;
    private final int INT_ARL_EXP_EXC_DAT_COD_MAE=1;
    private final int INT_ARL_EXP_EXC_DAT_COD_SIS=2;
    private final int INT_ARL_EXP_EXC_DAT_COD_ALT=3;
    private final int INT_ARL_EXP_EXC_DAT_COD_ALT_DOS=4;
    private final int INT_ARL_EXP_EXC_DAT_COD_ALT_3=5;
    private final int INT_ARL_EXP_EXC_DAT_NOM_ITM=6;
    private final int INT_ARL_EXP_EXC_DAT_CHK_SER=7;
    private final int INT_ARL_EXP_EXC_DAT_COD_UNI=8;
    private final int INT_ARL_EXP_EXC_DAT_DEC_UNI=9;
    private final int INT_ARL_EXP_EXC_DAT_BUT_UNI=10;
    private final int INT_ARL_EXP_EXC_DAT_PES_KGR=11;
    private final int INT_ARL_EXP_EXC_DAT_PES_KGR_AUX=12;
    private final int INT_ARL_EXP_EXC_DAT_STK_CON=13;
    private final int INT_ARL_EXP_EXC_DAT_COD_GEN=14;
    private final int INT_ARL_EXP_EXC_DAT_IVA=15;
    private final int INT_ARL_EXP_EXC_DAT_GRP=16;
     private final int INT_ARL_EXP_EXC_DAT_DET=17;
    private boolean blnMarTodChkMarActual=true;
    private boolean blnMarTodChkTblEmpPrimero=true;
    private boolean blnMarTodChkTblEmpUltimo=true;
    private boolean blnMarTodChkTblEmpOtro=true;
    private boolean blnMarTodChkTblEmpPrimeraImporta=true;
    private boolean blnMarTodChkTblEmpUltimaImporta=true;    
    private boolean blnMarTodChkFactorPor=true;    
    private ArrayList arlDatITEM_EM,arlRegITEM_EM;
    private ArrayList arlDatITEM_PE,arlRegITEM_PE;
    
    private final int INT_ARL_MAR_LIN=0;
    private final int INT_ARL_MAR_COSTO=0;
    private final int INT_ARL_MAR_TIPO=1;
      
    private int intCodLocGrp=1; 
    
    private BigDecimal bdePorIva=new BigDecimal("0");
    private BigDecimal bgdValor=new BigDecimal("0");
    
    private JScrollBar barDatVer, barFixVer, barDatHor, barFixHor;
    
    private String strVersion="v4.4";     
    private BigDecimal bgdFacCosUniGlo =BigDecimal.ZERO;
////    private ZafVenConItm01 objVenConItm01;
    private ZafVenConItmGP03 objVenConItm03;    
    private String Cadenaitems ="";      
    private boolean validaTodo=false;  
    
    private int contadorConsu =0;
    private ArrayList arlDatITEMDET,arlRegITEMDET;
    private static final int INT_ARL_CON_ITEM=0;  
    private static final int INT_ARL_CON_DESC=1;
    private static final int INT_ARL_CON_FAC=2;    
    private ArrayList arlRegITEMSEL,arlDatITEMSEL;
    private ArrayList arlRegITEMGRP,arlDatITEMGRP;
    private static final int INT_ARL_GRP_ITEM=0;
    private static final int INT_ARL_GRP_GRP=1;
    
    private static final int INT_ARL_SELE_ITEM=0;
    private static final int INT_ARL_SELE_3L=1;  
    private static final int INT_ARL_SELE_DESC=2;  
    private static final int INT_ARL_SELE_ITEM_ZAF=3;     
    
  
         
    private ZafUtilGP objUtiGP;
    HashMap<String, Double> dataMap = new HashMap<>();
    HashMap<String, Double> dataMapSTOCK  = new HashMap<>();
    HashMap<String, Com113> dataMapConsulta = new HashMap<>();
    HashMap<String, Com113_inv> dataMapInv = new HashMap<>();
    HashMap<String, Com113_hist> dataMapHist = new HashMap<>();
    private ArrayList arlDatPEDGP,arlRegPEDGP;  
    
    private ArrayList arlDatGrpDet,arlRegGrpDet;      
    
    private static final int INT_ARL_GRP_DET_COD=0;    
    private static final int INT_ARL_GRP_DET_ITEM=1;
    private static final int INT_ARL_GRP_DET_PRE=2;    
    private static final int INT_ARL_GRP_TIP=3;  
    
    
    private static final int INT_ARL_PED_EMP=0;
    private static final int INT_ARL_PED_LOC=1; 
    private static final int INT_ARL_PED_TIP_DOC=2; 
    private static final int INT_ARL_PED_DOC=3; 
    private static final int INT_ARL_PED_GP=4;    
    private static final int INT_ARL_PED_FEC_CREA=5; 
    private static final int INT_ARL_PED_FEC_ARRI=6; 
    private boolean blnCabecera =false;
    
    private ArrayList arlDatGrpDet2,arlRegGrpDet2;    
    private ZafTblCelRenLbl objTblCelRenLblColDinFacGrpDet;  //Render: Presentar JLabel en JTable (columnas dinámicas)    
    private ZafTblCelEdiTxt objTblCelEdiTxtFacGrpDet;                             //Editor: JTextField en celda.    
    
    private int columnaMod=-1;
    StringBuilder cadItms = new StringBuilder();
    int ne_menu=0;
    boolean blnMosPor=false;
    private boolean blnUsaKon=false;
    private ZafKon objKon;                    //OBJJETO DE SISTEMA KONFIABLE     
    
    private ZafThreadGUICarga objThrGUICarga; 
    private String[][] ColumasExcel;

    static final int INT_ARL_EXC_PRI_COD_GP=0;
    static final int INT_ARL_EXC_PRI_COD_3L=1;
    static final int INT_ARL_EXC_PRI_COD_MAR=2;
    private boolean blnCargarArchivo; 
    private int intNumeroItemsProcesar=0;
    private ArrayList arlDatExcel, arlRegExcel;
    boolean blnNoSeleccion=true;
    StringBuilder cadenaColAct = new StringBuilder();
    String strCodItmDynBus=""; 
    private ArrayList arlDatExcelVal, arlRegExcelVal;
    private static int INT_ARL_EXC_PRI_ITEM=0; 
    private int INT_EXC_PRI_COD_ITM=0;
    private int INT_EXC_PRI_BLN=1;     
    

   
    /** Crea una nueva instancia de la clase ZafCom113. */
    public ZafCom113(ZafParSis obj) 
    {
        try 
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatIngImp=new ArrayList();
            arlDatPedEmb=new ArrayList();
            arlDatNotPed=new ArrayList();
            arlDatExpExc=new ArrayList();
            chkMosValFac.setVisible(false);
            chkMosLSC.setVisible(false);
            chkMosAdi.setVisible(false);
            objKon = new ZafKon(objParSis);
            blnUsaKon=objKon.obtenerParaKon();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
////    public ZafCom113(ZafParSis obj,BigDecimal factorPorc,ZafTblMod obj1) 
////    public ZafCom113(ZafParSis obj,BigDecimal factorPorc)             
////    {
////        try
////        {
////////            initComponents();
////            int intNumFil=-1;
////            BigDecimal bgdPrecioVtaOb,bgdPrecioCalc;  
////            bgdPrecioVtaOb=BigDecimal.ZERO;
////            bgdPrecioCalc=BigDecimal.ZERO;            
////            intNumFil=objTblMod.getRowCountTrue();
////            for (int i=0; i<intNumFil;i++)
////            {
////                bgdPrecioVtaOb =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniFac)));//obtener el costo 
////                bgdPrecioCalc =(objUti.redondearBigDecimal((bgdPrecioVtaOb.multiply(factorPorc)), objParSis.getDecimalesBaseDatos()));                        
////                objTblMod.setValueAt(null, i, intNumColIniFactorPor);//intNumColIniMarNotPed
////            }
////            //Inicializar objetos.
////            objParSis=(ZafParSis)obj.clone();
////            arlDatIngImp=new ArrayList();
////            arlDatPedEmb=new ArrayList();
////            arlDatNotPed=new ArrayList();
////            arlDatExpExc=new ArrayList();
////            chkMosValFac.setVisible(false);
////            chkMosLSC.setVisible(false);
////        }
////        catch (CloneNotSupportedException e)
////        {
////            this.setTitle(this.getTitle() + " [ERROR]");
////        }
////    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        chkSolStk = new javax.swing.JCheckBox();
        chkMosValKil = new javax.swing.JCheckBox();
        chkMosValFac = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        chkMosPrimera = new javax.swing.JCheckBox();
        chkMosActual = new javax.swing.JCheckBox();
        chkMosUltima = new javax.swing.JCheckBox();
        chkMosUltimoIngre = new javax.swing.JCheckBox();
        chkMosPrimerIngre = new javax.swing.JCheckBox();
        chkMosLSC = new javax.swing.JCheckBox();
        chkMosPor = new javax.swing.JCheckBox();
        chkMosAgrup = new javax.swing.JCheckBox();
        chkMosAju = new javax.swing.JCheckBox();
        chkMosMar = new javax.swing.JCheckBox();
        chkMosAdi = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panFix = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panMov = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jLabelLey = new javax.swing.JLabel();
        butCon = new javax.swing.JButton();
        butRec = new javax.swing.JButton();
        butEnv = new javax.swing.JButton();
        butPorc = new javax.swing.JButton();
        butPreVen = new javax.swing.JButton();
        butSub = new javax.swing.JButton();
        butExpExc = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        lblTit1 = new javax.swing.JLabel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(473, 683));
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(null);

        jPanel2.setLayout(null);
        panFil.add(jPanel2);
        jPanel2.setBounds(320, 80, 0, 0);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(5, 80, 280, 16);

        chkMosValKil.setText("Mostrar valores por kilos");
        chkMosValKil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosValKilActionPerformed(evt);
            }
        });
        panFil.add(chkMosValKil);
        chkMosValKil.setBounds(5, 100, 270, 16);

        chkMosValFac.setText("Mostrar valores con factor");
        chkMosValFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosValFacActionPerformed(evt);
            }
        });
        panFil.add(chkMosValFac);
        chkMosValFac.setBounds(360, 220, 320, 16);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nota"));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Reporte no presenta stock de Bodegas:   Faltantes y Dañados");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(12, 20, 420, 18);

        panFil.add(jPanel1);
        jPanel1.setBounds(5, 270, 930, 40);

        chkMosPrimera.setSelected(true);
        chkMosPrimera.setText("Mostrar Precio Primera Importación");
        chkMosPrimera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPrimeraActionPerformed(evt);
            }
        });
        panFil.add(chkMosPrimera);
        chkMosPrimera.setBounds(5, 140, 320, 16);

        chkMosActual.setSelected(true);
        chkMosActual.setText("Mostrar Precio Actual");
        chkMosActual.setMaximumSize(new java.awt.Dimension(197, 23));
        chkMosActual.setMinimumSize(new java.awt.Dimension(197, 23));
        chkMosActual.setPreferredSize(new java.awt.Dimension(197, 23));
        chkMosActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosActualActionPerformed(evt);
            }
        });
        panFil.add(chkMosActual);
        chkMosActual.setBounds(5, 120, 260, 16);

        chkMosUltima.setSelected(true);
        chkMosUltima.setText("Mostrar Precio Ultima Importación");
        chkMosUltima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosUltimaActionPerformed(evt);
            }
        });
        panFil.add(chkMosUltima);
        chkMosUltima.setBounds(5, 160, 320, 16);

        chkMosUltimoIngre.setSelected(true);
        chkMosUltimoIngre.setInheritsPopupMenu(true);
        chkMosUltimoIngre.setLabel("Mostrar Ultimo Ingreso");
        chkMosUltimoIngre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosUltimoIngreActionPerformed(evt);
            }
        });
        panFil.add(chkMosUltimoIngre);
        chkMosUltimoIngre.setBounds(5, 200, 190, 23);

        chkMosPrimerIngre.setSelected(true);
        chkMosPrimerIngre.setLabel("Mostrar Penultimo Ingreso");
        chkMosPrimerIngre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPrimerIngreActionPerformed(evt);
            }
        });
        panFil.add(chkMosPrimerIngre);
        chkMosPrimerIngre.setBounds(5, 180, 320, 16);

        chkMosLSC.setText("Mostrar Precio Manual");
        chkMosLSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLSCActionPerformed(evt);
            }
        });
        panFil.add(chkMosLSC);
        chkMosLSC.setBounds(340, 230, 180, 23);

        chkMosPor.setText("Mostrar Aumento Porcentual");
        chkMosPor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPorActionPerformed(evt);
            }
        });
        panFil.add(chkMosPor);
        chkMosPor.setBounds(5, 250, 280, 16);

        chkMosAgrup.setText("Mostrar solo cabeceras");
        chkMosAgrup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosAgrupActionPerformed(evt);
            }
        });
        panFil.add(chkMosAgrup);
        chkMosAgrup.setBounds(5, 225, 320, 16);

        chkMosAju.setText("Mostrar Ajustes");
        chkMosAju.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosAjuActionPerformed(evt);
            }
        });
        panFil.add(chkMosAju);
        chkMosAju.setBounds(290, 100, 220, 16);

        chkMosMar.setText("Mostrar Margenes");
        chkMosMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMarActionPerformed(evt);
            }
        });
        panFil.add(chkMosMar);
        chkMosMar.setBounds(290, 80, 220, 16);

        chkMosAdi.setText("Mostrar Adicionales");
        chkMosAdi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosAdiActionPerformed(evt);
            }
        });
        panFil.add(chkMosAdi);
        chkMosAdi.setBounds(290, 120, 220, 16);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(518);
        jSplitPane1.setDividerSize(3);

        panFix.setLayout(new java.awt.BorderLayout());

        tblFix.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFix.setViewportView(tblFix);

        panFix.add(spnFix, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panFix);

        panMov.setLayout(new java.awt.BorderLayout());

        tblDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panMov.add(spnDat, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(panMov);

        panRpt.add(jSplitPane1);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabelLey.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelLey.setForeground(new java.awt.Color(255, 0, 0));
        jLabelLey.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelLey.setText("Item Detalle no se puede modificar el PRECIO");
        jLabelLey.setToolTipText("");
        jLabelLey.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panBot.add(jLabelLey);

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butRec.setText("Recalcular");
        butRec.setToolTipText("");
        butRec.setPreferredSize(new java.awt.Dimension(92, 25));
        butRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRecActionPerformed(evt);
            }
        });
        panBot.add(butRec);

        butEnv.setText("Agrupar");
        butEnv.setToolTipText("Eniva registros para la agraupacion de items");
        butEnv.setPreferredSize(new java.awt.Dimension(92, 25));
        butEnv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEnvActionPerformed(evt);
            }
        });
        panBot.add(butEnv);

        butPorc.setText("Porcentaje");
        butPorc.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butPorc.setPreferredSize(new java.awt.Dimension(92, 25));
        butPorc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPorcActionPerformed(evt);
            }
        });
        panBot.add(butPorc);
        butPorc.getAccessibleContext().setAccessibleDescription("Muestra ventana para la ejecucion del porcentaje del precio");

        butPreVen.setText("Pre.Vta.1");
        butPreVen.setToolTipText("Asigna el valor de \"Cos.Uni.Fac.\" a \"Pre.Vta.1.\" para las filas seleccionadas.");
        butPreVen.setPreferredSize(new java.awt.Dimension(92, 25));
        butPreVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPreVenActionPerformed(evt);
            }
        });
        panBot.add(butPreVen);

        butSub.setText("Cargar Margen");
        butSub.setToolTipText("");
        butSub.setPreferredSize(new java.awt.Dimension(92, 25));
        butSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSubActionPerformed(evt);
            }
        });
        panBot.add(butSub);

        butExpExc.setText("Exportar");
        butExpExc.setPreferredSize(new java.awt.Dimension(92, 25));
        butExpExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpExcActionPerformed(evt);
            }
        });
        panBot.add(butExpExc);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        lblTit1.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit1.setText("Título de la ventana");
        panFrm.add(lblTit1, java.awt.BorderLayout.NORTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 985, 450);
    }// </editor-fold>//GEN-END:initComponents

    
    
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
                strSQL="";
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    System.out.println("Version " + strVersion);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(strVersion.equals(rstLoc.getString("tx_ver"))){
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
////        if(!isVersionActualizada() )
////        {
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            dispose();
////        }
////        if (objVenConItm03.VersionErronea())
////        {
////            mostrarMsgInf("Su version del formulario de consulta  no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            dispose();            
////        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

////        if(!isVersionActualizada()){
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////        dispose();
////        }
////        else
////        {
            blnCon=true;
            if (objThrGuaGUI==null)
            {
                objThrGuaGUI=new ZafThGuarGUI();
                objThrGuaGUI.start();
            }
////        }

        
////        if (objTblMod.isDataModelChanged())
////        {
////            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
////            {
////                pgrSis.setIndeterminate(true);
////                if(isCamVal()){
////                    if (actualizarDet())
////                        {
////                            
////                                lblMsgSis.setText("Listo");
////                                pgrSis.setValue(0);
////////                            if(ProcesoUpdateGP(conGP,strFecUltMod))
////////                            {
////                                mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
////////                            }
////                        }
////
////                    else
////                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
////                }
////                else
////                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
////            }
////        }
////        else
////            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

        
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true); 
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
////            objVenConItm01.limpiar();
            objVenConItm03.limpiar();
            chkSolStk.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
////            if ((chkMosValFac.isSelected()) || (chkMosValKil.isSelected()))
////            {
                blnCon=true;
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
                //ocultaColumnasPorFiltro();
         
////            }
////            else
////            {
////                mostrarMsgInf("Debe de seleccionar al menos una opción (Por Factor o Por Kilos).");
////                blnCon=false;
////            }
                
            
           
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butPreVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPreVenActionPerformed
        int intFilSel[], i;
        if (mostrarMsgCon("<HTML>¿Está seguro que desear asignar <FONT COLOR=\"blue\">Cos.Uni.Fac.</FONT> a <FONT COLOR=\"blue\">Pre.Vta.1.</FONT>?<BR>Sólo se asignarán los valores en las filas seleccionadas.")==0)
        {
            intFilSel=tblDat.getSelectedRows();
            for (i=0; i<intFilSel.length; i++)
            {
                if (objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1)))!=0)
                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1)), intFilSel[i], intNumColIniPreReaMar);
            }
            //Seleccionar la fila donde se encontró el valor buscado.
            tblDat.setRowSelectionInterval(intFilSel[0], intFilSel[intFilSel.length-1]);
            //Ubicar el foco en la fila seleccionada.
            tblDat.changeSelection(intFilSel[0], intNumColIniPreReaMar, true, true);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butPreVenActionPerformed

    private void chkMosValKilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosValKilActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPrecios();
        ocultaColumnasMargenPrecio();
        calcularValoresKilos(1);
        calcularMarNotPed();
        calcularMarPedEmb();
    }//GEN-LAST:event_chkMosValKilActionPerformed

    private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
        // TODO add your handling code here:
        java.io.File filExc;
        Process pro;
        BigDecimal bdeCanRepValExpExc=new BigDecimal("0");
        try{

            mostrarMsgInf("<HTML>Recuerde guardar el archivo con otro nombre y en otro directorio.<BR>Si no realiza este paso, al volver a exportar el archivo se sobreescribirá y Ud. perderá todos los cambios efectuados.</HTML>");

            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            if(System.getProperty("os.name").equals("Linux")){
                filExc=new java.io.File("/tmp/ZafCom113.xls");
            }
            else{
                filExc=new java.io.File("C:\\Zafiro\\Reportes\\Compras\\ZafCom113\\ZafCom113.xls");
            }
            String strNomHoj="Hoja1";

            arlDatExpExc.clear();
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                //estaticas
                arlRegExpExc=new ArrayList();
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_LIN, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_MAE, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_MAE).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_SIS, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_ALT, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_ALT_DOS, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_ALT_3, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_3)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_3).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_NOM_ITM, (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_CHK_SER, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_UNI, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_DEC_UNI, (objTblMod.getValueAt(i, INT_TBL_DAT_DEC_UNI)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_DEC_UNI).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_BUT_UNI, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_PES_KGR, (objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_PES_KGR_AUX, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_STK_CON, (objTblMod.getValueAt(i, INT_TBL_DAT_STK_CON)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_STK_CON).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_GEN, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_GEN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_GEN).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_IVA, (objTblMod.getValueAt(i, INT_TBL_DAT_IVA)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_IVA).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_GRP, (objTblMod.getValueAt(i, INT_TBL_DAT_GRP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_GRP).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_DET, (objTblMod.getValueAt(i, INT_TBL_DAT_DET)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_DET).toString()));
                
                //dinamicas
                for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                    //System.out.println("Dato erroneo: " + (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
//////////////                for(int j=intNumColIniPre; j<intNumColFinPre;j++){
//////////////                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
//////////////                }
//////////////                for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
//////////////                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
//////////////                }
                arlDatExpExc.add(arlRegExpExc);                    
            }
//            System.out.println("arlDatExpExc: " + arlDatExpExc.toString());

            ZafCom113_01 objCom90_01= new ZafCom113_01(arlDatExpExc, filExc, strNomHoj
                    , intNumColIniIngImp, intNumColFinIngImp
                    , intNumColIniCosUni, intNumColFinCosUni
                    , intNumColIniPedEmb, intNumColFinPedEmb
                    , intNumColIniNotPed, intNumColFinNotPed
                    , intNumColIniMarNotPed, intNumColFinMarNotPed
                    , intNumColIniPreReaMar, intNumColFinPreReaMar
                    , intNumColIniFac, intNumColFinFac
                    , intNumColIniMarFac, intNumColFinMarFac
                    , intNumColIniPre, intNumColFinPre
                    , intNumColIniCanMarPreLisNew, intNumColFinCanMarPreLisNew
                    , arlDatIngImp, arlDatPedEmb, arlDatNotPed
                    , chkMosValKil.isSelected()?true:false
                    , chkMosValFac.isSelected()?true:false
            );
            
            
            
            
            
            
            

            if(objCom90_01.export()){
                mostrarMsgInf("El archivo se cargó correctamente.");

                if(System.getProperty("os.name").equals("Linux")){
                    pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCom113.xls");
//                    System.out.println("LINUX: " + pro.toString());
                }
                else{
                    pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Compras/ZafCom113/ZafCom113.xls");
//                    System.out.println("WINDOWS: " + pro.toString());
                }

            }
            else
                mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpExcActionPerformed

    private void chkMosPrimeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPrimeraActionPerformed
        // TODO add your handling code here:
         ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosPrimeraActionPerformed

    private void chkMosActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosActualActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosActualActionPerformed

    private void chkMosUltimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosUltimaActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosUltimaActionPerformed

    private void chkMosValFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosValFacActionPerformed
        // TODO add your handling code here:
        ////        ocultaColumnasFactor(); 09112023
    }//GEN-LAST:event_chkMosValFacActionPerformed

    private void chkMosUltimoIngreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosUltimoIngreActionPerformed
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosUltimoIngreActionPerformed

    private void chkMosPrimerIngreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPrimerIngreActionPerformed
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosPrimerIngreActionPerformed

    private void butPorcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPorcActionPerformed
                try{
            if(objTblMod.getRowCountTrue()>0){
                ZafCom113_02 objCom113_02 = new ZafCom113_02(javax.swing.JOptionPane.getFrameForComponent(this), objParSis);
                objCom113_02.show();
                if(objCom113_02.acepta())
                {
                    System.out.println("aceptar...");
                    int intNumFil=-1;
                    
                    BigDecimal bgdPrecioVtaOb,bgdPrecioCalc,factorPorc;  
                    bgdPrecioVtaOb=BigDecimal.ZERO;
                    bgdPrecioCalc=BigDecimal.ZERO;       
                    factorPorc=BigDecimal.ZERO; 
                    factorPorc = objCom113_02.getValor();
                    blnMosPor = objCom113_02.getOpcion();
                    if (blnMosPor)
                        chkMosPor.setSelected(true);
                    else
                        chkMosPor.setSelected(false);
                    
                    intNumFil=objTblMod.getRowCountTrue();
                    if(!factorPorc.equals(BigDecimal.ZERO))
                    {
                        for (int i=0; i<intNumFil;i++)
                        {
                            
                            
                             bgdPrecioVtaOb=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                           // bgdPrecioVtaOb =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniFac-4)));
                            bgdPrecioCalc =(objUti.redondearBigDecimal((bgdPrecioVtaOb.multiply(factorPorc).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesBaseDatos()));    
                            bgdPrecioCalc = bgdPrecioVtaOb.add(bgdPrecioCalc);

                            objTblMod.setValueAt(bgdPrecioCalc, i, intNumColIniFactorPor);//intNumColIniMarNotPed
                        }                        
                    }

                    ocultaColumnasPorFiltro();
                }
                objCom113_02 = null;
               
            }
            else{
                mostrarMsgInf("Debe tener datos cargados para ingresar datos para los calculos...");
            }
            
        }
        catch(Exception e)
        {  
           objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butPorcActionPerformed

    private void chkMosLSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLSCActionPerformed
        // TODO add your handling code here:
         ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosLSCActionPerformed

    private void chkMosPorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPorActionPerformed
        // TODO add your handling code here:
    ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosPorActionPerformed

    private void chkMosAgrupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosAgrupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosAgrupActionPerformed


    
            
            
    private void butEnvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEnvActionPerformed
        // TODO add your handling code here:
        int [] regSelec = tblFix.getSelectedRows();
////        arlDatITEMSEL.clear();
////        arlDatITEMGRP.clear();
        
        arlDatITEMSEL= new ArrayList();
        arlDatITEMGRP= new ArrayList();
        
        boolean validaGrupo = false,blnExisteGrp=false;
        int ndGrp=-1,ndDetGrp=-1,contador =0;
        
        int numRows = regSelec.length;

 
        for (int row : regSelec)
        {
            contador++;
            ndGrp=Integer.parseInt( objUti.parseString(objTblModFix.getValueAt(row, INT_TBL_FIX_GRP)));
            ndDetGrp=Integer.parseInt( objUti.parseString(objTblModFix.getValueAt(row, INT_TBL_FIX_DET)));
            if ((ndGrp !=0)|| (ndDetGrp!=0)) 
            {
                arlRegITEMGRP = new ArrayList();
                arlRegITEMGRP.add(INT_ARL_GRP_ITEM,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT_3));
                if (ndGrp !=0)
                    arlRegITEMGRP.add(INT_ARL_GRP_GRP,ndGrp);
                if (ndDetGrp !=0)
                    arlRegITEMGRP.add(INT_ARL_GRP_GRP,ndDetGrp);
                arlDatITEMGRP.add(arlRegITEMGRP);    
                blnExisteGrp=true;
            }
            else
            {
                arlRegITEMSEL = new ArrayList();
                arlRegITEMSEL.add(INT_ARL_SELE_ITEM,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT_3));
                arlRegITEMSEL.add(INT_ARL_SELE_3L,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT_DOS));
                arlRegITEMSEL.add(INT_ARL_SELE_DESC,objTblMod.getValueAt(row , INT_TBL_FIX_NOM_ITM));
                arlRegITEMSEL.add(INT_ARL_SELE_ITEM_ZAF,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT));
                arlDatITEMSEL.add(arlRegITEMSEL);                
            }
            
//////////            if (!blnExisteGrp)
//////////            {
//////////                arlRegITEMSEL = new ArrayList();
//////////                arlRegITEMSEL.add(INT_ARL_SELE_ITEM,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT_3));
//////////                arlRegITEMSEL.add(INT_ARL_SELE_3L,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT_DOS));
//////////                arlRegITEMSEL.add(INT_ARL_SELE_DESC,objTblMod.getValueAt(row , INT_TBL_FIX_NOM_ITM));
//////////                arlRegITEMSEL.add(INT_ARL_SELE_ITEM_ZAF,objTblMod.getValueAt(row , INT_TBL_FIX_COD_ALT));
//////////                arlDatITEMSEL.add(arlRegITEMSEL);
//////////            }
        }
        if ((blnExisteGrp) ) 
        {
////////////            String strMsgItmUbi="";
////////////             strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items: <BR><BR>";
////////////             strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> GRUPO </td>";
////////////             for (int i=0; i<arlDatITEMGRP.size();i++) 
////////////            { 
////////////                    strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMGRP, i, INT_ARL_GRP_ITEM) + "</td>";
////////////                    strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMGRP, i, INT_ARL_GRP_GRP) + "</td>";
////////////                    strMsgItmUbi+="</tr>";
////////////            }
////////////            strMsgItmUbi+="</table><BR>"; 
////////////            strMsgItmUbi+="Se encuentra en un grupo <html>";
////////////            mostrarMsgInf(strMsgItmUbi);    
            if (numRows ==1)
            {
                int grupo =0;
                 if (ndGrp >0)
                     grupo =ndGrp;
                 else
                     if (ndDetGrp >0)
                         grupo =ndDetGrp;             

                    Compras.ZafCom113.ZafCom113_03 obj13_04 = new Compras.ZafCom113.ZafCom113_03(objParSis,1,"",grupo);
                    this.getParent().add(obj13_04, javax.swing.JLayeredPane.DEFAULT_LAYER);
                    obj13_04.show();                
            }
            else 
              mostrarMsgInf("SE TIENE DENTRO DE LA SELECCION ITEMS CON GRUPO E ITEMS SIN GRUPO ");  
 
        } 
        else
        {
            if (numRows!=0)
            {
                Compras.ZafCom113.ZafCom113_03 obj13_03 = new Compras.ZafCom113.ZafCom113_03(objParSis,arlDatITEMSEL,'C');
                this.getParent().add(obj13_03, javax.swing.JLayeredPane.DEFAULT_LAYER);
                obj13_03.show();                            
            }    
            else
                mostrarMsgInf("NO SE HA SELECCIONADO NINGUN ITEM/AGRUPACION"); 


                
////        Compras.ZafCom91.ZafCom91 obj = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
////        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
////        obj.show();                
        }
        
        
    }//GEN-LAST:event_butEnvActionPerformed

    private void butRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRecActionPerformed
//////////        int intNumFil=0,i=0,numNoChe=0;
//////////        intNumFil=objTblMod.getRowCountTrue();
//////////        String strMsg="";
//////////        boolean blnExis=false;
//////////        BigDecimal bgdPrePrim, bgdMarPrim;
//////////        BigDecimal bgdPreUlt, bgdMarUlt;
//////////        BigDecimal bgdPrePedPri, bgdMarPedPri;
//////////        BigDecimal bgdPrePedUlt, bgdMarPedUlt;
//////////        BigDecimal bgdFacCos;
//////////        int detGrp =0;
//////////        String strIndMod="";
//////////        for (i=0; i<intNumFil;i++)
//////////        {
//////////            detGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_DET)));
//////////            strIndMod=  objUti.parseString(objTblMod.getValueAt(i,intNumColIniModCabDet));
//////////            if(!strIndMod.equals("") && !strIndMod.equals("D"))
//////////            {
//////////                bgdPrePrim=(objTblMod.getValueAt(i, (intNumColIniMarPrimeraImporta))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarPrimeraImporta)))));
//////////                bgdMarPrim=(objTblMod.getValueAt(i, (intNumColIniMarPrimeraImporta+1))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarPrimeraImporta+1)))));
//////////
//////////                bgdPreUlt=(objTblMod.getValueAt(i, (intNumColIniMarUltimaImporta))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarUltimaImporta)))));
//////////                bgdMarUlt=(objTblMod.getValueAt(i, (intNumColIniMarUltimaImporta+1))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarPrimeraImporta+1)))));            
//////////
//////////                bgdPrePedPri=(objTblMod.getValueAt(i, (intNumColIniMarPrimero))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarPrimero)))));
//////////                bgdMarPedPri=(objTblMod.getValueAt(i, (intNumColIniMarPrimero+1))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarPrimero+1)))));            
//////////
//////////                bgdPrePedUlt=(objTblMod.getValueAt(i, (intNumColIniMarUltimo))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarUltimo)))));
//////////                bgdMarPedUlt=(objTblMod.getValueAt(i, (intNumColIniMarUltimo+1))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniMarUltimo+1)))));            
//////////
//////////                bgdFacCos=(objTblMod.getValueAt(i, (intNumColIniFac))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniFac)))));            
//////////                bgdPrePrim=bgdPrePrim.multiply(bgdFacCos);
//////////                bgdPreUlt=bgdPreUlt.multiply(bgdFacCos);
//////////                bgdPrePedPri=bgdPrePedPri.multiply(bgdFacCos);
//////////                bgdPrePedUlt=bgdPrePedUlt.multiply(bgdFacCos);
//////////
//////////                objTblMod.setValueAt(bgdPrePrim , i, intNumColIniMarPrimeraImporta);
//////////                objTblMod.setValueAt(bgdPreUlt , i, intNumColIniMarUltimaImporta);
//////////                objTblMod.setValueAt(bgdPrePedPri , i, intNumColIniMarPrimero);
//////////                objTblMod.setValueAt(bgdPrePedUlt , i, intNumColIniMarUltimo);
//////////                blnExis=true;                        
//////////
//////////            }
//////////        }
//////////        if (!blnExis)
//////////        {
//////////            strMsg="<html> No existe para realizar el recalculo de los precios. <BR><BR>" ;// 
//////////            String strTit="Mensaje del sistema Zafiro";
//////////            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);            
//////////        }

                GenerarColumnasAdicionalesImportacionesFl(0,1,1);
                //calcularValoresKilosFl(0);
                 if(chkMosValKil.isSelected())
                    GenerarColumnasAdicionalesImportacionesFl(1,1,1); 
                 
                 GenerarColumnasAdicionalesFl(1,1);
            
    }//GEN-LAST:event_butRecActionPerformed

    private void chkMosAjuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosAjuActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosAjuActionPerformed

    private void chkMosMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMarActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosMarActionPerformed

    private void chkMosAdiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosAdiActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMosAdiActionPerformed

    private void butSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSubActionPerformed
        if (objThrGUICarga==null)
        {
            objThrGUICarga=new ZafCom113.ZafThreadGUICarga();
            objThrGUICarga.start();    
        }   
        objThrGUICarga =null;

    }//GEN-LAST:event_butSubActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private class ZafThreadGUICarga extends Thread
    {
        public void run()
        {
            cargarArchivoXLS();
            objThrGUICarga=null;
            tabFrm.setSelectedIndex(2);
        }
    } 
    private void ConfiguracionColumnas(int tipo)
    {
        if (tipo ==1)
        {
            ColumasExcel =  new String[][]
            {
                {"ITEM", objUti.parseString(INT_ARL_EXC_PRI_COD_GP)},
                {"TRESL", objUti.parseString(INT_ARL_EXC_PRI_COD_3L)},
                {"MARGEN", objUti.parseString(INT_ARL_EXC_PRI_COD_MAR)},
            };            
        }
    } 
    private boolean validarColumnasExcel(String[] columnasExcel, String[][] columnasEsperadas) 
    {
        boolean blnValido = true;

        ArrayList<String> colCoinciden = new ArrayList<>();
        ArrayList<String> colFaltantes = new ArrayList<>();
        ArrayList<String> colSobrantes = new ArrayList<>();

        // Convertir columnasExcel a mayúsculas para comparar sin error
        HashSet<String> setExcel = new HashSet<>();
        for (String col : columnasExcel) {
            if (col != null)
                setExcel.add(col.trim().toUpperCase());
        }

        // Validar columnas requeridas
        for (String[] colEsp : columnasEsperadas) {
            String colReq = colEsp[0].trim().toUpperCase();

            if (setExcel.contains(colReq)) {
                colCoinciden.add(colReq);
            } 
            else {
                colFaltantes.add(colReq);
                blnValido = false;
            }
        }

        // Detectar columnas sobrantes en el Excel
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

        // Mostrar resultado en consola (o usar tu propio mensaje)
        System.out.println("==== VALIDACIÓN DE COLUMNAS ====");
        System.out.println("Columnas del Excel: " + Arrays.toString(columnasExcel));
        System.out.println("Columnas requeridas: " + Arrays.deepToString(columnasEsperadas));
        System.out.println("Coinciden: " + colCoinciden);
        System.out.println("Faltantes: " + colFaltantes);
        System.out.println("Sobrantes: " + colSobrantes);

        if (!colFaltantes.isEmpty()) {
            mostrarMsgInf("Faltan columnas obligatorias en el Excel:\n" + colFaltantes);
        }

        return blnValido;
    }  
    public  String buscarColumnaSeleccionada(String[][] datos, String clave) 
    {
        for (int i = 0; i < datos.length; i++) {
            if (datos[i][0].equals(clave)) {
                return datos[i][1]; // Retorna el valor de la segunda columna
            }
        }
        return null; // Si no se encuentra la clave, devuelve null
    }    
    private boolean getArchivoExcel()
    {
        boolean blnRes = true;
        try{  
            blnCargarArchivo = false;
            intNumeroItemsProcesar=0;
            arlDatExcel = new ArrayList();
            JFileChooser objfileChooser = new JFileChooser();
            objfileChooser.setDialogTitle("Seleccionar archivo Excel Formato (XLS)");
            objfileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xls"/*, "xlsx"*/));

            int userSelection = objfileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                blnNoSeleccion=false;
                File selectedFile = objfileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Aquí puedes hacer lo que necesites con el archivo Excel seleccionado
//                System.out.println("Archivo seleccionado: " + filePath);
                blnCargarArchivo=true;

                Workbook workbook = Workbook.getWorkbook(new File(filePath));

                // Leer la primera hoja del libro
                Sheet sheet = workbook.getSheet(0);

                // Obtener el número de filas y columnas en la hoja
                int numRows = sheet.getRows();
                int numCols = sheet.getColumns();
                boolean blnExi=false;
                
                String[] columnasExcel = new String[numCols];
                for (int j = 0; j < numCols; j++) {
                    Cell cell = sheet.getCell(j, 0);
                    columnasExcel[j] = cell.getContents().trim();
                }                
                
                /**/
                
                if (!validarColumnasExcel(columnasExcel, ColumasExcel)) {
                    blnCargarArchivo = false;
                    mostrarMsgInf("El archivo Excel NO cumple con la estructura requerida.");
                    return false;
                }                

                
                String columna ="";
                cadenaColAct = new StringBuilder(); 
                for (int i = 0; i <=0; i++) {
                    // Iterar sobre las columnas de la fila actual

                    for (int j = 0; j < numCols; j++) 
                    {
                        Cell cell = sheet.getCell(j, i);
                        String cellValue = cell.getContents();
                        if (j==0)
                        {
                            if (cellValue.equals(""))
                                blnExi=true;
                        }   
                           columna = buscarColumnaSeleccionada(ColumasExcel,cellValue);
                           if (columna!=null)
                              cadenaColAct.append(cellValue).append(",");  
                    }
                }
                /**/
                
                if (cadenaColAct.length() > 0) {
                    cadenaColAct.setLength(cadenaColAct.length() - 1); // Elimina la última coma
                }
                String [] columnasAct = cadenaColAct.toString().split(",");
                arlDatExcel=new ArrayList();
                // Iterar sobre las filas de la hoja
                for (int i = 1; i < numRows; i++) 
                {
                    arlRegExcel = new ArrayList();
                    blnExi = false;

                    // Leer la primera columna (ITEM)
                    Cell celItem = sheet.getCell(0, i);
                    String strItem = celItem.getContents().trim();

                    // Si el ítem está vacío, saltar esta fila
                    if (strItem.isEmpty()) {
                        continue; // ❌ No se toma en cuenta esta fila
                    }

                    // Agregar todas las columnas normalmente
                    for (int j = 0; j < numCols; j++) {
                        Cell cell = sheet.getCell(j, i);
                        String cellValue = cell.getContents();
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
        String strSql="";
        
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
            

            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null)
            {
                
                if (strCodItmDynBus.endsWith(",")) 
                    strCodItmDynBus = strCodItmDynBus.substring(0, strCodItmDynBus.length() - 1);
                stmLoc = conLoc.createStatement();
                strSql="";
                //strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, TRIM(ITMGEDSC) as ITMGEDSC, a1.ITEMTYPE  "
                strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR,inv.co_grpinv,inv.co_detgrpinv"                        
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1 "
                        + "      LEFT JOIN ( "                        
                        + "                 SELECT tx_itm,co_grpinv,co_detgrpinv "                   
                        + "                 FROM Dynamics2021..tbm_inv "                   
                        + "                 WHERE co_grpinv !=0 or (co_grpinv=0 and co_detgrpinv =0)"                   
                        + "                ) as inv on (a1.ITEMNMBR = inv.tx_itm) "                   
                        + " WHERE a1.ITEMNMBR IN ( "+strCodItmDynBus+" )  "  ;
                rstLoc = stmLoc.executeQuery(strSql);
                    while(rstLoc.next())
                    { 
                        //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                        intCuentaItemsEncontrados++;
                        /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                        for(int x=0;x<arlDatExcelVal.size();x++){
                            if(rstLoc.getString("ITEMNMBR").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM)))
                            {
                                objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN, "OK");
                                break;
                            }
                        }
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
    private boolean ingresarDatosCargados()
    {
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strUpdateItems="", strFecUltMod;
        BigDecimal cantMargen;
        String cod_Ex="",cod_Tb="",valItm="";
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
              
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                for(int i=0;i<arlDatExcelVal.size();i++)
                {
                    cod_Ex =objUti.getStringValueAt(arlDatExcelVal, i, INT_EXC_PRI_COD_ITM).toString();
                    valItm =objUti.getStringValueAt(arlDatExcelVal, i, INT_EXC_PRI_BLN).toString();
                    if (valItm.equals("OK"))
                    {
                        for(int j=0; j<objTblMod.getRowCountTrue();j++)
                        {
                              cod_Tb =objTblMod.getValueAt(j, INT_TBL_DAT_COD_ALT_3).toString();      
                              if(cod_Tb.equals(cod_Ex))
                              { 
    ////                              cantMinEx =BigDecimal.valueOf(    objUti.parseDouble(objTblMod.getValueAt(i,(intNumColIniStkRep+3))   ));
                                  cantMargen =BigDecimal.valueOf(  objUti.parseDouble(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PRI_COD_MAR)));  
                                  objTblMod.setValueAt("" + objUti.redondearBigDecimal(cantMargen, objParSis.getDecimalesBaseDatos()), j, (intNumColIniPreReaMar+5));
                                  break; 
                              }
                        }                        
                    }

                }
                if(blnRes)
                    System.out.println("OK! ");
        return blnRes;
    }   
    
    private boolean cargarArchivoXLS()
    {
        boolean blnRes=true;
        boolean blnCarGrp=false;
        String strAux;
        try
        {  
            ConfiguracionColumnas(1);// PARA LOS ATRIBUTOS
            if (getArchivoExcel() == true)
            {
                //if(validarDatosCargados(validaPes))
                if(validarDatosCargados(1))
                {
                   if (ingresarDatosCargados())
                   {
                        calcularAjustePegar();

                       mostrarMsgInf("La carga del archivo fue exitosa.");
                       
                   }
                       
                   else
                       mostrarMsgInf("No se puedo guardar los datos...");
                }
                else
                {
                    strAux =  "Existen Items en el documento que no son encontrados en el Sistema." + "\n\n";
                    strAux += "Notifique esta novedad al Administrador del sistema. \n";
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_PRI_BLN).equals("N")){
                            strAux+=" "+objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_PRI_ITEM) + "-"; 
                        }
                    }
                    if (strAux.endsWith("-")) 
                        strAux = strAux.substring(0, strAux.length() - 1);
                        
                    blnRes=false;
                    mostrarMsgInf(strAux);
                }
            }
            else
            {  
                if(blnNoSeleccion)
                    blnRes=false;
                else
                {
                    if (blnCargarArchivo == false)
                    {   
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
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEnv;
    private javax.swing.JButton butExpExc;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butPorc;
    private javax.swing.JButton butPreVen;
    private javax.swing.JButton butRec;
    private javax.swing.JButton butSub;
    private javax.swing.JCheckBox chkMosActual;
    private javax.swing.JCheckBox chkMosAdi;
    private javax.swing.JCheckBox chkMosAgrup;
    private javax.swing.JCheckBox chkMosAju;
    private javax.swing.JCheckBox chkMosLSC;
    private javax.swing.JCheckBox chkMosMar;
    private javax.swing.JCheckBox chkMosPor;
    private javax.swing.JCheckBox chkMosPrimerIngre;
    private javax.swing.JCheckBox chkMosPrimera;
    private javax.swing.JCheckBox chkMosUltima;
    private javax.swing.JCheckBox chkMosUltimoIngre;
    private javax.swing.JCheckBox chkMosValFac;
    private javax.swing.JCheckBox chkMosValKil;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelLey;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit1;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFix;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panMov;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        Double dblValFacCal=0.00, dblValMinFacCal=0.01, dblValMaxFacCal=1.00, dblValIncFacCal=0.01;
        
        try
        {
            //Configurar ZafVenConItm01:
////            objVenConItm01=new ZafVenConItm01(objParSis, "a1.", optFil);
////            objVenConItm01.setBounds(24, 44, 656, 20);
            
            objVenConItm03=new ZafVenConItmGP03(objParSis, "a1.", optFil,objParSis.getCodigoMenu());
            panFil.add(objVenConItm03);
            objVenConItm03.setBounds(4, 55, 500, 20);
            objVenConItm03.setEnabled(true);            
                        
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objKon = new ZafKon(objParSis);
            objUtiGP=new ZafUtilGP(objParSis);
            objHisTblBasDat=new ZafHisTblBasDat();
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            
            jLabel2.setText(strAux);
            jLabelLey.setVisible(false);
            lblTit1.setText(strAux + strVersion);
            //lblTit1.setText("Actualización de Precios SUGERIDOS (Importaciones)");
             
            //PORCENTAJE IVA 

            bdePorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            
            ne_menu=objParSis.getCodigoMenu();
////            if  (ne_menu==4908)//para lo que es referncia a mas de 4 digitos
////                butRec.setVisible(true);
////            else
////                butRec.setVisible(false);
            
            
            //Configurar las ZafVenCon.
            butRec.setVisible(false);
            configurarVenConItm();
            configurarVenConUniMed();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblFix();
            
            butCon.setVisible(true);
            butGua.setVisible(true);
            butPreVen.setVisible(true);
            butCer.setVisible(true);
////            chkMosPorcen.setVisible(false);            
            if(objParSis.getCodigoUsuario()==1){
            }
            else
            {
////                if (ne_menu==4908)
////                {
////                    if(!objPerUsr.isOpcionEnabled(4909))
////                        butCon.setVisible(false);
////                    if(!objPerUsr.isOpcionEnabled(4910))
////                        butGua.setVisible(false);
////                    if(!objPerUsr.isOpcionEnabled(4911))
////                        butPreVen.setVisible(false);
////                    if(!objPerUsr.isOpcionEnabled(4912))
////                        butCer.setVisible(false);                    
////                    
////                }
////                else
////                {
                    if(!objPerUsr.isOpcionEnabled(4673))
                        butCon.setVisible(false);
                    if(!objPerUsr.isOpcionEnabled(4674))
                        butGua.setVisible(false);
                    if(!objPerUsr.isOpcionEnabled(4675))
                        butPreVen.setVisible(false);
                    if(!objPerUsr.isOpcionEnabled(4676))
                        butCer.setVisible(false);                    
                    
////                }

                
            }
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
/**
 * Deshabilita el menú contextual de pegar (Ctrl+V) para una columna específica.
 * @param intColumna Índice de la columna donde se desea deshabilitar el menú de pegado.
 */
    

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.Itm.Dos");
            vecCab.add(INT_TBL_DAT_COD_ALT_3,"Cód.Alt.3");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_CHK_SER,"Ser.");
            vecCab.add(INT_TBL_DAT_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"UN");
            vecCab.add(INT_TBL_DAT_BUT_UNI,"");
            vecCab.add(INT_TBL_DAT_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_DAT_PES_KGR_AUX,"Peso(Kg)Aux.");
            vecCab.add(INT_TBL_DAT_STK_CON,"Stk.");
            vecCab.add(INT_TBL_DAT_COD_GEN,"Cód.Gen.");//CHIQUI
            vecCab.add(INT_TBL_DAT_IVA,"IVA");//CHIQUI
            vecCab.add(INT_TBL_DAT_GRP,"GRP");//CHIQUI
            vecCab.add(INT_TBL_DAT_DET,"DET");//CHIQUI
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
////            ZafBorder borderRenderer = new ZafBorder(INT_TBL_DAT_NOM_ITM);
////            
////////            tblDat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 3, 4)));
////            
////                tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(new ZafBorder(INT_TBL_DAT_NOM_ITM));
////            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() 
            {
                
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {
                    if (objTblPopMnu.isClickPegar()) {
                        int intColSel = tblDat.getSelectedColumn();

                        // 🛑 Si está en la columna intNumColIniPreReaMar → cancelar el pegado
                        if (intColSel == intNumColIniPreReaMar) 
                        {
                            evt.cancel(); // 🔥 Esto bloquea el pegado ANTES de que ocurra
                            System.out.println("🚫 Pegado bloqueado en columna intNumColIniPreReaMar.");
                        }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {
                    if (objTblPopMnu.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected((intNumColIniFac+1)))
                        {
                            calcularCosUniFac();
                            calcularMarFac();
                        }
                        else if (tblDat.isColumnSelected(intNumColIniPreReaMar))
                        {
                            calcularMarUtiCos();
                            calcularPreVtaReaKil();
                        }
                    }
                    else if (objTblPopMnu.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
                            }
                        }

                        else if (tblDat.isColumnSelected(intNumColIniPreReaMar+5))
                        {
                            calcularAjustePegar();
                            
                            return;
        //                            calcularMarUtiCos();
                            //calcularPreVtaReaKil();
                            //procesoAsignacionEstadoPegado();
                        }

                        //nuevo
                        else if(tblDat.isColumnSelected(intNumColIniFac)){//se pego en columna "factor de costo"
                            calcularCosUniFacTbl();
                            calcularMarFacTbl();
                        }                        
                        else if(tblDat.isColumnSelected(intNumColIniMarFac)){//se pego en columna "margen con factor"
                            calcularCostoUnitarioFactor_MargenTbl();
                        }                       

                        else if(tblDat.isColumnSelected(intNumColIniPre)){//se pego en columna "precio objetivo kilo incluido IVA - Factor precio en kilos"
                            calcularPreLisCalConsulta();
                            calcularMarPreLisCal();  
                        }
                        else if(tblDat.isColumnSelected(intNumColIniCanMarPreLisNew)){//se pego en columna "Margen precio lista calculado"
                            calcularPrecioListaCalculado_MargenTbl();
                        }



                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(36);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_3).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR_AUX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_GEN).setPreferredWidth(100);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_IVA).setPreferredWidth(50);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_GRP).setPreferredWidth(50);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_DET).setPreferredWidth(50);//CHIQUI
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SER, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_KGR_AUX, tblDat);
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_DOS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_3, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEC_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_UNI, tblDat);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_KGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GEN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_IVA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DET, tblDat);
            
            
            
////            objTblMod.addSystemHiddenColumn(intNumColIniChkPrimero, tblDat);
////            objTblMod.addSystemHiddenColumn(intNumColIniChkUltimo, tblDat);
//////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GEN, tblDat);
            
            
            if (objParSis.getCodigoUsuario()!=1)
            {
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
////                if (ne_menu==4908)
////                {
////                    if (!objPerUsr.isOpcionEnabled(4919))
////                        butPreVen.setVisible(false);
////                }
////                else
////                {
                    if (!objPerUsr.isOpcionEnabled(2990))
                        butPreVen.setVisible(false);
////                }
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLbl.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    

                    
                }
            });
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLblDer);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblDer);
            objTblCelRenLblDer.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblDer.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR_AUX).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_COD_GEN).setCellRenderer(objTblCelRenLblNum);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_IVA).setCellRenderer(objTblCelRenLblNum);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_GRP).setCellRenderer(objTblCelRenLblNum);//CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_DET).setCellRenderer(objTblCelRenLblNum);//CHIQUI
            
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+3)))));

                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblNum.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }                    
                    
                    //nuevo color
                    if(chkMosValKil.isSelected()){
                        BigDecimal bgdPesKil=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_PES_KGR)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_PES_KGR))));

                        if(objTblCelRenLblNum.getColumnRender()==INT_TBL_DAT_PES_KGR){
                            if (bgdPesKil==null || bgdPesKil.compareTo(BigDecimal.ZERO)==0)
                                objTblCelRenLblNum.setBackground(java.awt.Color.GREEN);
                            else{
                                if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                                {
                                    objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                }
                                else{
                                    if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                    }
                                    else{
                                        if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                            objTblCelRenLblNum.setBackground(new Color(255,151,151));
                                        }
                                        else{
                                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                        }                                    
                                    }
                                }
                            }
                        }
                    }
                    
                }
            });
            
            
            //04032024
            
            objTblCelRenLblStkCal=new ZafTblCelRenLbl();
            objTblCelRenLblStkCal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkCal.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkCal.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(intNumColIniMarPrimero).setCellRenderer(objTblCelRenLblStkCal);
            objTblCelRenLblStkCal.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdPrecioAct;
                    
                    bgdPrecio =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdPrecioAct =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniMarPrimero))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniMarPrimero)))));

                    if (bgdPrecioAct.compareTo(bgdPrecio)> 0)//el precio sugerido es mayor al precio de venta
                        objTblCelRenLblColDinMarPrimero.setBackground(new Color(255,151,151));
                    else
                        objTblCelRenLblColDinMarPrimero.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    

////                    if (dblStock <= 0){ 
////                        objTblCelRenLblStkCal.setBackground(java.awt.Color.orange);
////                    }
////                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
////                        objTblCelRenLblStkCal.setBackground(java.awt.Color.red);
////                    }
////                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
////                        objTblCelRenLblStkCal.setBackground(java.awt.Color.PINK);
////                    }
////                    else{
////                        objTblCelRenLblStkCal.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
                }
            });
            
            
            
            
            
            
            
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenChk.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            
            
           
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[3];//CHIQUI
            intColTbl[0]=INT_TBL_DAT_COD_UNI;
            intColTbl[1]=INT_TBL_DAT_DEC_UNI;
            intColTbl[2]=INT_TBL_DAT_COD_GEN;
////            intColTbl[3]=INT_TBL_DAT_IVA;
            objTblCelEdiTxtVcoUniMed=new ZafTblCelEdiTxtVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMed);
            
            objTblCelEdiButVcoUniMed=new ZafTblCelEdiButVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMed);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxtPesKgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgr);
            //objTblCelEdiTxtPesKgr=null;
            objTblCelEdiTxtPesKgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularReversionValoresKilos();
                    
                    calcularPreVtaReaKil();
                    calcularPreLisCal();
                    calcularMarPreLisCal_row();
                }
            });
            
            //<editor-fold defaultstate="collapsed" desc="/*EVENTO MOUSE CLICK DINAMICO*/">
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            //</editor-fold>
            
            
            
//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblMod.getColumnCount();
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private void precioAnter()
    {
        int j, intNumFil;
        int ne_Grupo=-1,ne_DetGrupo=-1;        
        intNumFil=objTblMod.getRowCountTrue();
        BigDecimal bgdPrecio;
        String str_llave="",str_valor="";        
        if (columnaMod >0)
        {
            for (j=0; j<intNumFil; j++)
            {
                ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                int intNumCol = intNumColIniPreReaMar;
                str_llave = j +","+intNumCol;
                str_valor=obtenerTblHist(str_llave);   
                    // Convierte el valor anterior a BigDecimal (con validación)
                if (str_valor != null && !str_valor.equals("")) 
                    bgdPrecio = new BigDecimal(str_valor);
                else 
                    bgdPrecio = BigDecimal.ZERO;

                procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,columnaMod);                            
                objTblMod.setChecked(false, j, columnaMod);

            }             
        }
       
        
    }
    
        /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        int j, intNumFil;
        int ne_Grupo=-1,ne_DetGrupo=-1;
        BigDecimal bgdPrecio;
        String str_llave="",str_valor="";
        try
        {
            precioAnter();
            intNumFil=objTblMod.getRowCountTrue();
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChk)
            {
                bgdValor = BigDecimal.ZERO;                   
                bgdPrecio = BigDecimal.ZERO;  

                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA MARGEN ACTUAL*/">
                if (blnMarTodChkMarActual)
                {                            
                    columnaMod =intNumColIniChk;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);     
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                         

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChk-3)));
                        
                                
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString())); 
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChk-2)));                        
                        
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChk); //No marca columna Margen Actual
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            {
                                
                                objTblMod.setChecked(true,  j, intNumColIniChk);        //Chequea columna Margen Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero); //Desmarca columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkUltimo);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Otro         
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Otro                                 
                            }
                                procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,2,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChk);
                        }
                    }
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=true;           
                    blnMarTodChkFactorPor=true;                        
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChk);                            
                        objTblMod.setChecked(false, j, intNumColIniChk);

                    }
                    blnMarTodChkMarActual=true;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=false;      
                    blnMarTodChkFactorPor=false;     
                    
                   
                }
                //</editor-fold> --todook
                
                
                
            }
            
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkPrimero)
            {
                bgdValor = BigDecimal.ZERO;                   

                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA PRIMER PEDIDO*/">
                if (blnMarTodChkTblEmpPrimero)
                {                         
                    columnaMod =intNumColIniChkPrimero;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);      
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                             

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkPrimero-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j,  intNumColIniChkPrimero-2)));
                        
                        
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            {                            
                                objTblMod.setChecked(true,  j, intNumColIniChkPrimero);        //Chequea columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkUltimo);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Otro      
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Otro                                 
                            }
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,3,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkPrimero);
                             
                        }
                    }
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=true;
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=true;                     
                    blnMarTodChkFactorPor=true;                       
                    
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkPrimero); 
                        
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                    }
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpUltimo=false; 
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=false;  
                    blnMarTodChkFactorPor=false; 
                }
                //</editor-fold>--todook
                
                
                
            }
            
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkUltimo)
            {
                bgdValor = BigDecimal.ZERO;                   

                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA ULTIMO PEDIDO*/">
                if (blnMarTodChkTblEmpUltimo)
                {            
                    columnaMod =intNumColIniChkUltimo;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);      
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                           

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkUltimo-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j,intNumColIniChkUltimo-2)));
                        
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            { 
                                objTblMod.setChecked(true,  j, intNumColIniChkUltimo);        //Chequea columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);  //Desmarca columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Otro    
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Otro                                                               
                            }   
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,4,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkUltimo);

                            
                        }
                    }
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=true;
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=true;       
                    blnMarTodChkFactorPor=true;                       
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkUltimo);  
                        
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                    }
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpOtro=false;    
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=false;  
                    blnMarTodChkFactorPor=false;                     
                }
                //</editor-fold>--todok
                
                
                
            }
            
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkOtro)
            {
                bgdValor = BigDecimal.ZERO;                   

                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA OTRO*/">
                if (blnMarTodChkTblEmpOtro)
                {                     
                    columnaMod =intNumColIniChkOtro;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);       
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                          

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkOtro-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j,intNumColIniChkOtro-2)));  
                        
                        
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            { 
                                objTblMod.setChecked(false,  j, intNumColIniChkUltimo);        //Chequea columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);  //Desmarca columna Primer Pedido
                                objTblMod.setChecked(true, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Otro      
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Otro                                                              
                            }  
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,7,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkOtro);

                        }
                    }
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=true; 
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=true;              
                    blnMarTodChkFactorPor=true;                      
                    
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkOtro); 
                        
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                    }
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=false;   
                    blnMarTodChkFactorPor=false;                     
                }
                //</editor-fold>--todook
                
                
                
            }  
            
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkPrimeraImporta)
            {
                bgdValor = BigDecimal.ZERO;                   

                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA PRIMERA IMPORTACION*/">
                if (blnMarTodChkTblEmpPrimeraImporta)
                {                       
                    columnaMod =intNumColIniChkPrimeraImporta;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);   
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                          

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkPrimeraImporta-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString())); 
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j,intNumColIniChkPrimeraImporta-2))); 
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            { 
                                objTblMod.setChecked(false,  j, intNumColIniChkUltimo);        //Chequea columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);  //Desmarca columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(true, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Primera  
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Ultimo           
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Ultimo                                  
                            }                            
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,5,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkPrimeraImporta);                            
                                 
                        }
                    }
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=true; 
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=true;
                    blnMarTodChkFactorPor=true;     
                    
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkPrimeraImporta); 
                        
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                    }
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=false; 
                    blnMarTodChkFactorPor=false;                         
                }
                //</editor-fold>--todook
                
                
                
            }      
            
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkUltimaImporta)
            {
                bgdValor = BigDecimal.ZERO;     
                
                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA ULTIMA IMPORTACION*/">
                if (blnMarTodChkTblEmpUltimaImporta)
                {                 
                    columnaMod =intNumColIniChkUltimaImporta;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);    
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                        

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkUltimaImporta-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));     
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkUltimaImporta-2)));   
                        //if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            {                             
                                objTblMod.setChecked(false,  j, intNumColIniChkUltimo);        //Chequea columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);  //Desmarca columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Primera  
                                objTblMod.setChecked(true, j, intNumColIniChkUltimaImporta);  //Desmarca columna Ultimo       
                                objTblMod.setChecked(false, j, intNumColIniChkFactor);  //Desmarca columna Ultimo                                                              
                            }
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,6,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkUltimaImporta);                            

                        }
                    }
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=true; 
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=false;   
                    blnMarTodChkFactorPor=true;                    
                    
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkUltimaImporta);                         
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);
                    }
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkFactorPor=false;                    
                    blnMarTodChkTblEmpUltimaImporta=true;                    
                }
                //</editor-fold>--todook
            }    

            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkFactor)
            {
                bgdValor = BigDecimal.ZERO;     
                
                //<editor-fold defaultstate="collapsed" desc="/*EVENTO CLICK: COLUMNA DINAMICA FACTOR PORCENTUAL*/">
                if (blnMarTodChkFactorPor)
                {            
                    columnaMod =intNumColIniChkFactor;
                    //Marcar las columnas.
                    for (j=0; j<intNumFil; j++)
                    {
                        objTblMod.setChecked(false, j, intNumColIniChk);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                        objTblMod.setChecked(false, j, intNumColIniChkOtro);
                        objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);
                        objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);     
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);                          

                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkFactor-1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));  
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkFactor-1)));   
                        if ((ne_DetGrupo!=0) || (bgdPrecio.compareTo(BigDecimal.ZERO) == 0))
                        {
                            bgdValor = BigDecimal.ZERO;
                            objTblMod.setChecked(false, j, intNumColIniChkFactor);
                        }
                        else
                        {
                            if (((ne_Grupo !=0) || (ne_Grupo ==0 && ne_DetGrupo ==0)) && (bgdPrecio.compareTo(BigDecimal.ZERO) != 0))
                            {  
                                objTblMod.setChecked(false,  j, intNumColIniChkUltimo);        //Chequea columna Ultimo Pedido
                                objTblMod.setChecked(false, j, intNumColIniChk); //Desmarca columna Actual
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);  //Desmarca columna Primer Pedido
                                objTblMod.setChecked(false, j, intNumColIniChkOtro);  //Desmarca columna Otro
                                objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Primera  
                                objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Ultimo       
                                objTblMod.setChecked(true, j, intNumColIniChkFactor);  //Desmarca columna Ultimo                                 
                            }                            
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,9,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkFactor);                            
                            
                        }
                    }
                    blnMarTodChkTblEmpUltimo=true;
                    blnMarTodChkTblEmpPrimero=true;
                    blnMarTodChkMarActual=true; 
                    blnMarTodChkTblEmpOtro=true;
                    blnMarTodChkTblEmpPrimeraImporta=true;
                    blnMarTodChkTblEmpUltimaImporta=true;   
                    blnMarTodChkFactorPor=false;                     
                    
                }
                else
                {
                    for (j=0; j<intNumFil; j++)
                    {
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(j, INT_TBL_DAT_DET).toString()));                         
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = j +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,j,bgdPrecio,intNumColIniChkFactor); 
                        
                        objTblMod.setChecked(false, j, intNumColIniChkFactor);
                    }
                    blnMarTodChkTblEmpUltimo=false;
                    blnMarTodChkTblEmpPrimero=false;
                    blnMarTodChkMarActual=false;
                    blnMarTodChkTblEmpOtro=false;
                    blnMarTodChkTblEmpPrimeraImporta=false;
                    blnMarTodChkTblEmpUltimaImporta=false;    
                    blnMarTodChkFactorPor=true;                      
                }
                //</editor-fold>--todook
            }            

            
            
            
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFix()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FIX_LIN,"");
            vecCab.add(INT_TBL_FIX_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_FIX_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_FIX_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_FIX_COD_ALT_DOS,"Cód.Alt.Itm.Dos");
            vecCab.add(INT_TBL_FIX_COD_ALT_3,"Cód.Alt.3");
            vecCab.add(INT_TBL_FIX_NOM_ITM,"Item");
            vecCab.add(INT_TBL_FIX_CHK_SER,"Ser.");
            vecCab.add(INT_TBL_FIX_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_FIX_DEC_UNI,"Uni.Med.");
            vecCab.add(INT_TBL_FIX_BUT_UNI,"");
            vecCab.add(INT_TBL_FIX_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_FIX_PES_KGR_AUX,"Peso(Kg)Aux.");
            vecCab.add(INT_TBL_FIX_STK_CON,"Stk.");
            vecCab.add(INT_TBL_FIX_COD_GEN,"Cód. Gene.");
            vecCab.add(INT_TBL_FIX_IVA,"IVA.");
            vecCab.add(INT_TBL_FIX_GRP,"GRP");
            vecCab.add(INT_TBL_FIX_DET,"DET");
            vecCab.add(INT_TBL_FIX_FAC,"FACTOR");
            
////            ZafBorder borderRenderer = new ZafBorder(INT_TBL_FIX_COD_GEN);
////            
////////            tblDat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 3, 4)));
////            
////                tblDat.getColumnModel().getColumn(INT_TBL_FIX_COD_GEN).setCellRenderer(new ZafBorder(INT_TBL_FIX_COD_GEN));
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCab);
            tblFix.setModel(objTblModFix);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModFix.setColumnDataType(INT_TBL_FIX_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblFix.setRowSelectionAllowed(true);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            objTblPopMnuFix.setPegarEnabled(true);
            objTblPopMnuFix.setBorrarContenidoEnabled(true);
            objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() 
            {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuFix.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblFix.isColumnSelected(INT_TBL_FIX_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_COD_UNI);
                            }
                        }
                    }
                    else if (objTblPopMnuFix.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblFix.isColumnSelected(INT_TBL_FIX_DEC_UNI)){
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_COD_UNI);
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_DEC_UNI);
                            }
                        }
                    }
                }
               
                
            });
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_FIX_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setPreferredWidth(36);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_3).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR_AUX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_STK_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_GEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FIX_GRP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FIX_DET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FIX_FAC).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFix.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_MAE, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_SIS, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_CHK_SER, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_UNI, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_PES_KGR_AUX, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_GEN, tblFix);
            if (objParSis.getCodigoUsuario()!=1)
            {
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
////                if (ne_menu==4908)
////                {
////                    if (!objPerUsr.isOpcionEnabled(4919))
////                        butPreVen.setVisible(false);
////                }
////                else
////                {
                    if (!objPerUsr.isOpcionEnabled(2990))
                        butPreVen.setVisible(false);
////                }
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAdaFix=new ZafMouMotAda();
//            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblFilCabFix);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblFix=new ZafTblCelRenLbl();
            objTblCelRenLblFix.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_3).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFix);
//            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblFix);
//            tcmAux.getColumn(INT_TBL_DAT_COD_UNI).setCellRenderer(objTblCelRenLblFix);
//            tcmAux.getColumn(INT_TBL_FIX_GRP).setCellRenderer(objTblCelRenLblFix);
//            tcmAux.getColumn(INT_TBL_FIX_DET).setCellRenderer(objTblCelRenLblFix);
//            
////            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblFix);
////            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblFix);
////            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblFix);
            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    int padre =-1;
                    padre=Integer.parseInt( objUti.parseString(objTblModFix.getValueAt(objTblCelRenLblFix.getRowRender(), INT_TBL_FIX_GRP)));
                   
                     if (padre ==0)
////                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        objTblCelRenLblFix.setBackground(new Color(255,255,255));
                    else
                         objTblCelRenLblFix.setBackground(new Color(178, 186, 187));
////                     objTblCelRenLblFix.setBackground(java.awt.Color.GREEN);
                        
                }
            });

////////            objTblCelRenLblFix=new ZafTblCelRenLbl();
////////            objTblCelRenLblFix.setHorizontalAlignment(javax.swing.JLabel.LEFT);
////////            tcmAux.getColumn(INT_TBL_FIX_GRP).setCellRenderer(objTblCelRenLblFix);
////////            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
////////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
////////                    int padre =-1;
////////                    padre=Integer.parseInt( objUti.parseString(objTblModFix.getValueAt(objTblCelRenLblFix.getRowRender(), INT_TBL_FIX_GRP)));                    
////////                    BigDecimal bgdMarUti, bgdMarUtiCos;
////////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+2)))));
////////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+3)))));
////////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////////                    {
////////                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////                    }
////////                    else{
////////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////////                            objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////                        }
////////                        else{
////////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////////                                objTblCelRenLblFix.setBackground(new Color(255,151,151));
////////                            }
////////                            else{
////////                                objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////                            }    
////////                        }
////////                    }
////////                    
////////
////////                    
////////                }
////////            });
            
            
            objTblCelRenLblDerFix=new ZafTblCelRenLbl();
            objTblCelRenLblDerFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setCellRenderer(objTblCelRenLblDerFix);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellRenderer(objTblCelRenLblDerFix);
//            objTblCelRenLblDerFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+3)))));
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblDerFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }
//                }
//            });
            
            objTblCelRenLblNumFix=new ZafTblCelRenLbl();
            objTblCelRenLblNumFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumFix.setTipoFormato(objTblCelRenLblNumFix.INT_FOR_NUM);
            objTblCelRenLblNumFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FIX_STK_CON).setCellRenderer(objTblCelRenLblNumFix);
////            tcmAux.getColumn(INT_TBL_FIX_COD_GEN).setCellRenderer(objTblCelRenLblFix);
////            tcmAux.getColumn(INT_TBL_FIX_IVA).setCellRenderer(objTblCelRenLblFix);
////            tcmAux.getColumn(INT_TBL_FIX_GRP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellRenderer(objTblCelRenLblNumFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR_AUX).setCellRenderer(objTblCelRenLblNumFix);
//            objTblCelRenLblNumFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+3)))));
//
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblNumFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }                    
//                    
//                    //nuevo color
//                    if(chkMosValKil.isSelected()){
//                        BigDecimal bgdPesKil=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), INT_TBL_FIX_PES_KGR)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), INT_TBL_FIX_PES_KGR))));
//
//                        if(objTblCelRenLblNumFix.getColumnRender()==INT_TBL_FIX_PES_KGR){
//                            if (bgdPesKil==null || bgdPesKil.compareTo(BigDecimal.ZERO)==0)
//                                objTblCelRenLblNumFix.setBackground(java.awt.Color.GREEN);
//                            else{
//                                if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                                {
//                                    objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                }
//                                else{
//                                    if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                                        objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                    }
//                                    else{
//                                        if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                            objTblCelRenLblNumFix.setBackground(new Color(255,151,151));
//                                        }
//                                        else{
//                                            objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                        }                                    
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    
//                }
//            });
            
            objTblCelRenButFix=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellRenderer(objTblCelRenButFix);
            objTblCelRenButFix=null;
            
            objTblCelRenChkFix=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FIX_CHK_SER).setCellRenderer(objTblCelRenChkFix);
            objTblCelRenChkFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    if (objTblMod.getRowCount()>0)
                    {
                        bgdMarUti=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2)))));
                        bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3)))));
                        if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                                objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }
                            else{
                                if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                    objTblCelRenChkFix.setBackground(new Color(255,151,151));
                                }
                                else{
                                    objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                }    
                            }
                        }                        
                    }

                }
            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_FIX_COD_UNI;
            intColTbl[1]=INT_TBL_FIX_DEC_UNI;
            objTblCelEdiTxtVcoUniMedFix=new ZafTblCelEdiTxtVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMedFix);
            
            objTblCelEdiButVcoUniMedFix=new ZafTblCelEdiButVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMedFix);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxtFixItm=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellEditor(objTblCelEdiTxtFixItm);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellEditor(objTblCelEdiTxtFixItm);
            objTblCelEdiTxtFixItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                    intColSel=tblFix.getSelectedColumn();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_ALT), intFilSel, INT_TBL_DAT_COD_ALT);
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_NOM_ITM), intFilSel, INT_TBL_DAT_NOM_ITM);

                }
            });
            
            objTblCelEdiTxtFixUniMed=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
            objTblCelEdiTxtFixUniMed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                    intColSel=tblFix.getSelectedColumn();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_UNI), intFilSel, INT_TBL_DAT_COD_UNI);
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_DEC_UNI), intFilSel, INT_TBL_DAT_DEC_UNI);

                }
            });
            
            objTblCelEdiTxtPesKgrFix=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgrFix);
            //objTblCelEdiTxtPesKgrFix=null;
            objTblCelEdiTxtPesKgrFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_PES_KGR), intFilSel, INT_TBL_DAT_PES_KGR);

                    calcularReversionValoresKilos();
                    
                    calcularPreVtaReaKil();
                    calcularPreLisCal();
                    calcularMarPreLisCal_row();
                }
            });
                        
//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblFix);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblModFix.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            ZafTblHeaGrp objTblHeaGrpFix=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpFix.setHeight(16*3);
            
            
            //inicio
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
            tblDat.setSelectionModel(modDat);//se descomento

            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);
            
            //Inicio*****
            //Evitar que aparezca la barra de desplazamiento vertical en el JTable Fijo
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            
            //VERTICAL
                //Adicionar el listener que controla el desplazamiento del JTable de datos.
                barFixVer=spnFix.getVerticalScrollBar();
                barDatVer=spnDat.getVerticalScrollBar();
//                barFixHor=spnFix.getHorizontalScrollBar();
//                barDatHor=spnDat.getHorizontalScrollBar();
                
                //PARA DESPLAZAMIENTOS DE CELDAS
                barDatVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                        barFixVer.setValue(evt.getValue());
                    }
                });
                barFixVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                        barDatVer.setValue(evt.getValue());
                    }
                });
            
             
            //Fin*****

            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private void obtenerSTOCK(String cadena)
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQLGP="",strAuxGP="";
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());             
            if(conLoc!=null)
            {
                dataMapSTOCK.clear();
                stmLoc=conLoc.createStatement();
                strSQLGP="";  
                strSQLGP+="SELECT a1.tx_itm , vis.nd_stockTotal  \n";
                strSQLGP+="FROM tbm_inv as a1  \n";
                strSQLGP+="     INNER JOIN Dynamics2021..view_DIS_IMP_TRANS_EXI_TOTAL as vis on(a1.tx_itm = vis.co_itmMae)  \n";
                strSQLGP+="WHERE a1.st_reg='A' and a1.tx_codAlt2 in ( "+ cadena +")  \n";
                System.out.println("SELECT obtenerSTOCK" + strSQLGP);    
                rstLoc=stmLoc.executeQuery(strSQLGP);
                while (rstLoc.next())
                {
                    dataMapSTOCK.put(rstLoc.getString("tx_itm"), rstLoc.getDouble("nd_stockTotal"));   
                }  
                /*24062025_inicio*/
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc =null;
                
                /*24062025_fin*/
            }            
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }        
    }  
    
    private void obtenerDatosInv(String cadena)
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQLGP="",strAuxGP="";
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());             
            if(conLoc!=null)
            {
                dataMapInv.clear();
                stmLoc=conLoc.createStatement();
                strSQLGP="";  
                strSQLGP+="SELECT inv.tx_itm , vis.nd_stockTotal , isnull(a1.factor,0) as factor, trim(inv.tx_codalt2) as  cod3l,inv.co_grpinv,inv.co_detgrpinv,inv.nd_eje,inv.nd_FacAju  \n";
                strSQLGP+="FROM tbm_inv as inv    \n";
                strSQLGP+="     INNER JOIN Dynamics2021..view_DIS_IMP_TRANS_EXI_TOTAL as vis on(inv.tx_itm = vis.co_itmMae)    \n";
                strSQLGP+="     LEFT JOIN (    \n";
                strSQLGP+="                 select cab.co_grpInv as cogrpinv, det.tx_codItm as item,det.co_reg as reg ,isnull(fac.nd_fac,0) as factor    \n";                
                strSQLGP+="                 from Dynamics2021..tbm_cabGrpInv as cab (NOLOCK)    \n";                
                strSQLGP+="                     inner join Dynamics2021..tbm_detGrpInv as det (NOLOCK) on (cab.co_grpinv = det.co_grpInv and co_tipAgr=1)    \n";                
                strSQLGP+="                     inner join Dynamics2021..tbm_facModPre as fac  (NOLOCK) on (det.co_facModPre = fac.co_facModPre)    \n";                                
                strSQLGP+="               ) as a1 on (inv.tx_itm = a1.item)    \n";                                
                strSQLGP+="WHERE inv.st_reg='A'  and inv.tx_codAlt2 in ( "+ cadena +")  \n";
                System.out.println("SELECT obtenerDatosInv" + strSQLGP);    
                rstLoc=stmLoc.executeQuery(strSQLGP);
                while (rstLoc.next())
                {
                    dataMapInv.put(rstLoc.getString("cod3l"), new Com113_inv ( rstLoc.getString("tx_itm"),rstLoc.getDouble("nd_stockTotal"),rstLoc.getDouble("factor"),rstLoc.getInt("co_grpinv"),rstLoc.getInt("co_detgrpinv"),rstLoc.getDouble("nd_eje"),rstLoc.getDouble("nd_FacAju")));  //29092024                                       
                }  
                /*24062025_inicio*/
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc =null;
                
                /*24062025_fin*/
            }            
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }        
    }      
    
    
    
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String ItemsGP)
    {
        BigDecimal bgdPreVta, bgdPreVtaMarUtiPreVta, bgdCosUni, bgdFacCosUni, bgdPesUni, bgdPreObjKil=new BigDecimal("0"),bgdPreVtaMarUtiPreVtaKg,bgdFacCosUniAju,bgdPreVtaMarUtiPreVtaAju;
        BigDecimal bgdPrecioSug,bgdMargen,bdeDivisor,bdeDividendo,bdePeso,bdeIVAGP,bdeEjecu,bdeEjeFac,bdeFacAju;
        boolean blnRes=true;
        boolean validaPreSu =false;
        boolean blnRes11=true;
        
        int contador =0;
        
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc; 
        
        blnCabecera= chkMosAgrup.isSelected();
        
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());             
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                
                //Filtrar por "Item".
////                if (objVenConItm01.isCondicionAplicada())
////                {
////                    strAux+=objVenConItm01.getCondicionesSQL();
////                }
                if (chkSolStk.isSelected())
                    strAux+=" AND a3.nd_stkAct>0";

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_codAltDyn, a1.tx_nomItm, a1.st_ser, a1.co_reg, a1.tx_desCor, a1.nd_pesItmKgr, a1.nd_stkAct";
                //Ingresos por Importacion.
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+="	, a" + (i+2) + ".nd_can AS a" + (i+2) + "_nd_canIngImp, a" + (i+2) + ".nd_preUniImp AS a" + (i+2) + "_nd_preUniIngImp, a" + (i+2) + ".nd_cosUni AS a" + (i+2) + "_nd_cosUniIngImp";
                }
                
                //Costo unitario del item
                strSQL+=" 	, a1.nd_cosUni";
                
                //Pedidos Embarcados
                for(int i=0; i<arlDatPedEmb.size(); i++){
                    strSQL+=" 	, a" + (i+31) + ".nd_can AS a" + (i+31) + "_nd_canPedEmb, a" + (i+31) + ".nd_preUni AS a" + (i+31) + "_nd_preUniPedEmb, a" + (i+31) + ".nd_cosUni AS a" + (i+31) + "_nd_cosUniPedEmb";
                    i++;
                }
                
                //Notas de Pedido
                for(int i=0; i<arlDatNotPed.size(); i++){
                    strSQL+=" 	, a" + (i+40) + ".nd_can AS a" + (i+40) + "_nd_canNotPed, a" + (i+40) + ".nd_preUni AS a" + (i+40) + "_nd_preUniNotPed, a" + (i+40) + ".nd_cosUni AS a" + (i+40) + "_nd_cosUniNotPed";
                    i++;
                }                
                strSQL+=" 	, a1.nd_preVta1, a1.nd_marUti, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr,a1.tx_codgene ,COALESCE(a1.nd_porIvaVen,0) AS nd_porIvaVen,COALESCE( a1.co_grpInv,0) AS co_grpInv,COALESCE( a1.co_detGrpInv,0) AS co_detGrpInv";
                
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_codAltDyn, a1.tx_nomItm, a4.co_reg, a4.tx_desCor, a1.st_ser, a3.nd_stkAct";
                strSQL+=" 	     , a1.nd_preVta1, a1.nd_marUti, a1.nd_pesItmKgr, a1.nd_cosUni, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr,a1.tx_codgene ,COALESCE(a1.nd_porIvaVen,0) AS nd_porIvaVen,COALESCE( a1.co_grpInv,0) AS co_grpInv,COALESCE( a1.co_detGrpInv,0) AS co_detGrpInv";
                

                                
                                
                
                strSQL+=" 	FROM tbm_inv AS a1";
                
                
                
                
                strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" 	LEFT OUTER JOIN (";
                //strSQL+=" 		 SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                //strSQL+=" 		 FROM tbm_equInv AS b1";
                //strSQL+=" 		 INNER JOIN tbm_inv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                //strSQL+=" 		 WHERE b2.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                //strSQL+=" 		 GROUP BY b1.co_itmMae";
                strSQL+="              SELECT b1.co_itmMae, SUM(b3.nd_stkAct) AS nd_stkAct";
                strSQL+="              FROM ";
                
//////                if (!ItemsGP.equals(""))
//////                    strSQL+="              ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr from tbm_inv  where tx_codAlt2 in (" + ItemsGP +")  ) AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";                                    
//////                else
//////                    
////////                    strSQL+="              (tbm_inv AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";
//////                    strSQL+="              ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr from tbm_inv  where tx_codAlt2 =''  ) AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";                                                        

                if (!ItemsGP.equals(""))
                    strSQL+="              ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr from tbm_inv  where tx_codAlt2 in (" + ItemsGP +")  ) AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";                                    
                else
                {
                    if(validaTodo)
                        strSQL+="              ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr from tbm_inv   ) AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";                                                            
                    else
                        strSQL+="              ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr from tbm_inv  where tx_codAlt2 =''  ) AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";                                                        
                }
                
                
////                strSQL+="              (tbm_inv AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";
                strSQL+="              INNER JOIN tbm_invBod AS b3 ON b2.co_emp=b3.co_emp AND b2.co_itm=b3.co_itm";
                strSQL+="              WHERE b2.co_emp<>0";
////                strSQL+="              AND (   CASE WHEN b3.co_emp=1 THEN b3.co_bod NOT IN(4,13,14,16,17,18,19,24,25,27,30,33)";//tuval
                strSQL+="              AND (   CASE WHEN b3.co_emp=1 THEN b3.co_bod IN (1,3,4,7,8,9,10,12,15,19,24,26,28,32,36,38)";//tuval
                strSQL+="                           WHEN b3.co_emp=2 THEN b3.co_bod NOT IN(6,13,14,16,17,18,19,24,25,27,30)";//castek
                strSQL+="                           WHEN b3.co_emp=4 THEN b3.co_bod NOT IN(4,12,14,16,17,18,19,24,25,27,30)";//dimulti
                strSQL+="                      END";
                strSQL+="                  )";
                strSQL+="              AND (b2.tx_codAlt like '%S' OR b2.tx_codAlt like '%I')";
                strSQL+="              GROUP BY b1.co_itmMae";
                strSQL+=" 	 ) AS a3";
                strSQL+=" 	 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" 	 LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg)";
                strSQL+=" 	 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	 AND a1.st_reg='A'";
                
////                if (!ItemsGP.equals(""))
////                    strSQL+="               AND (a1.tx_codAlt2 in (" + ItemsGP +") )";                                    
////                else
////                    strSQL+="               AND (a1.tx_codAlt2 ='' )";       

                if (!ItemsGP.equals(""))
                    strSQL+="               AND (a1.tx_codAlt2 in (" + ItemsGP +") )";                                    
                else
                {
                    if(validaTodo)
                        strSQL+="               ";  
                    else
                        strSQL+="               AND (a1.tx_codAlt2 ='' )";  
                }
                    

              
                
                
////                strSQL+="        " + strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+=" 	 ORDER BY a1.tx_codAlt";
                strSQL+=" ) AS a1";
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+="    SELECT * FROM (";  
                    strSQL+="         SELECT a.co_itmMae, a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, SUM (a.nd_can) as nd_can, a.nd_cosUni, 0 as nd_preUniImp"; 
                    strSQL+="         FROM (";  
                    //INIMPO
                    strSQL+="            SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni";
                    strSQL+="                 , b1.co_reg, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                    strSQL+="            FROM tbm_cabMovInv AS c1 INNER JOIN tbm_detMovInv AS b1";
                    strSQL+="            ON c1.co_emp=b1.co_emp AND c1.co_loc=b1.co_loc AND c1.co_tipDoc=b1.co_tipDoc AND c1.co_doc=b1.co_doc";
                    strSQL+="            INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+="            ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="            INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+="            WHERE b1.co_emp=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_EMP) + "";
                    strSQL+="            AND b1.co_loc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_LOC) + "";
                    strSQL+="            AND b1.co_tipDoc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_TIP_DOC) + "";
                    strSQL+="            AND b1.co_doc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_DOC) + "";
                    strSQL+="            GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="                   , b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni, b1.co_reg, c1.tx_numDoc2, c1.fe_doc ";
                    strSQL+="           UNION ALL";
                    //AJUSTE
                    strSQL+="            SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni";
                    strSQL+="                 , b1.co_reg, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                    strSQL+="            FROM tbm_cabMovInv AS c1 INNER JOIN tbm_detMovInv AS b1";
                    strSQL+="            ON c1.co_emp=b1.co_emp AND c1.co_loc=b1.co_loc AND c1.co_tipDoc=b1.co_tipDoc AND c1.co_doc=b1.co_doc";
                    strSQL+="            INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+="            ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="            INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+="            INNER JOIN tbr_cabMovInv as a4 ";
                    strSQL+="            ON c1.co_emp=a4.co_emp AND c1.co_loc=a4.co_loc AND c1.co_tipDoc=a4.co_tipDoc AND c1.co_doc=a4.co_doc";
                    strSQL+="            AND c1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+"";
                    strSQL+="                                 and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV+")";
                    strSQL+="            WHERE a4.co_empRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_EMP) + "";
                    strSQL+="            AND a4.co_locRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_LOC) + "";
                    strSQL+="            AND a4.co_tipDocRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_TIP_DOC) + "";
                    strSQL+="            AND a4.co_docRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_DOC) + "";
                    strSQL+="            AND c1.st_reg IN ('A')"; //26Ene2018: Se agrega validación para que solo muestre costos de items de los ajustes activos, tbm_cabMovInv <>'O'.                   
                    strSQL+="            AND (CASE WHEN b1.st_Reg IS NULL THEN 'A' ELSE b1.st_Reg END ) NOT IN ('I')";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                    strSQL+="            GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="                   , b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni, b1.co_reg, c1.tx_numDoc2, c1.fe_doc ";
                    strSQL+="         ) as a"; 
                    strSQL+="         GROUP BY a.co_itmMae, a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a.nd_cosUni"; 
                    strSQL+="    ) as b WHERE b.nd_can>0";
                    strSQL+=") AS a" + (i+2);
                    strSQL+=" ON a1.co_itmMae=a" + (i+2) + ".co_itmMae";
                }
                for(int i=0; i<arlDatPedEmb.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+=" 	, e1.nd_can, e1.nd_preUni";
                    strSQL+=" 	, e1.nd_cosUni, e1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+=" 	, d1.co_emp AS co_empPedEmb, d1.co_loc AS co_locPedEmb, d1.co_tipdoc AS co_tipDocPedEmb, d1.co_doc AS co_docPedEmb";
                    strSQL+=" 	, d1.tx_numDoc2 AS tx_numPedEmb, d1.fe_doc AS fe_docPedEmb, d1.co_ctaAct, d1.co_ctaPas";
                    strSQL+=" 	FROM tbm_cabPedEmbImp AS d1";
                    strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS e1 ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                    strSQL+=" 	INNER JOIN (( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+=" 	INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
                    strSQL+=" 	ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 	WHERE d1.st_reg='A'";
                    strSQL+=" 	AND d1.co_emp=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_EMP) + "";
                    strSQL+=" 	AND d1.co_loc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_LOC) + "";
                    strSQL+=" 	AND d1.co_tipDoc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_TIP_DOC) + "";
                    strSQL+=" 	AND d1.co_doc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" 	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+=" 	, d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, e1.nd_can, e1.nd_preUni";
                    strSQL+="  , e1.nd_cosUni, e1.co_reg, a2.tx_desCor, d1.co_ctaAct, d1.co_ctaPas, d1.tx_numDoc2, d1.fe_doc";
                    strSQL+="  ORDER BY e1.co_reg";
                    strSQL+=" ) AS a" + (i+31);
                    strSQL+=" ON a1.co_itmMae=a" + (i+31) + ".co_itmMae";
                    i++;
                }
                for(int i=0; i<arlDatNotPed.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+=" 	, e1.nd_can, e1.nd_preUni";
                    strSQL+=" 	, e1.nd_cosUni, e1.co_reg AS co_regNotPed, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+=" 	, d1.co_emp AS co_empNotPed, d1.co_loc AS co_locNotPed, d1.co_tipdoc AS co_tipDocNotPed, d1.co_doc AS co_docNotPed";
                    strSQL+=" 	, d1.tx_numDoc2 AS tx_numNotPed, d1.fe_doc AS fe_docNotPed, d1.co_ctaAct, d1.co_ctaPas";
                    strSQL+=" 	, d1.st_notpedlis, d1.st_cie";
                    strSQL+=" 	FROM tbm_cabNotPedImp AS d1 INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+=" 						ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 						INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+=" 						)";
                    strSQL+=" 	ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                    strSQL+=" 	WHERE d1.st_reg='A'";
                    strSQL+=" 	AND d1.co_emp=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_EMP) + "";
                    strSQL+=" 	AND d1.co_loc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_LOC) + "";
                    strSQL+=" 	AND d1.co_tipDoc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_TIP_DOC) + "";
                    strSQL+=" 	AND d1.co_doc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_DOC) + "";
                    strSQL+=" ) AS a" + (i+40);
                    strSQL+=" ON a1.co_itmMae=a" + (i+40) + ".co_itmMae";
                    i++;
                }
                    strSQL+=" ORDER BY a1.tx_codAlt";
//                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                vecDatFix.clear();
                //obtenerSTOCK(Cadenaitems);
                obtenerDatosInv(Cadenaitems);
                
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        if (rst.getString("tx_codAlt2").equals("RKE"))
                            blnRes11=true;
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        //System.out.println("codigo maestros datos: " + rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_3,rst.getString("tx_codAltDyn"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_COD_UNI,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_BUT_UNI,null);
                        vecReg.add(INT_TBL_DAT_PES_KGR,rst.getString("nd_pesItmKgr"));
                        bdePeso=rst.getBigDecimal("nd_marUti");//chiqui
                        vecReg.add(INT_TBL_DAT_PES_KGR_AUX,rst.getString("nd_pesItmKgr"));
                        String stCod="";
                        stCod=rst.getString("tx_codAltDyn");
                        Double nd_stock = nd_stock = Optional
                                            .ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                                            .map(Com113_inv::getNe_stock)   // extrae el double
                                            .orElse(0.0); 
                        vecReg.add(INT_TBL_DAT_STK_CON, nd_stock);                          
                        vecReg.add(INT_TBL_DAT_COD_GEN,rst.getString("tx_codgene"));
                        vecReg.add(INT_TBL_DAT_IVA,rst.getBigDecimal("nd_porIvaVen"));
                        int ne_grupo = Optional
                            .ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                            .map(Com113_inv::getNe_grupo)
                            .orElse(0);
                        int ne_detgrupo =  Optional
                                            .ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                                            .map(Com113_inv::getNe_detgrupo)   
                                            .orElse(0);       
                      
                        vecReg.add(INT_TBL_DAT_GRP,ne_grupo);
                        vecReg.add(INT_TBL_DAT_DET,ne_detgrupo);
                        bdeIVAGP=objUti.redondearBigDecimal(((rst.getBigDecimal("nd_porIvaVen").add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui
                        //adicionar las columnas dinamicas
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                            vecReg.add(j,     null);
                        }
                       
                        for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniChk; j<intNumColFinChk;j++){
                            vecReg.add(j,     false);
                        }
//////                        for(int j=intNumColIniFacCl; j<intNumColFinFacCl;j++){
//////                            vecReg.add(j,     null);
//////                        }                         
                        for(int j=intNumColIniMarPrimero; j<intNumColFinMarPrimero;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniChkPrimero; j<intNumColFinChkPrimero;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniMarUltimo; j<intNumColFinMarUltimo;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniChkUltimo; j<intNumColFinChkUltimo;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniMarPrimeraImporta; j<intNumColFinMarPrimeraImporta;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniChkPrimeraImporta; j<intNumColFinChkPrimeraImporta;j++){
                            vecReg.add(j,     false);
                        } 
                        for(int j=intNumColIniMarUltimaImporta; j<intNumColFinMarUltimaImporta;j++){
                            vecReg.add(j,     false);
                        }
                        for(int j=intNumColIniChkUltimaImporta; j<intNumColFinChkUltimaImporta;j++){
                            vecReg.add(j,     false);
                        }                          
                        for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                            vecReg.add(j,     null);
                        }                        
                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecReg.add(j,     null);
                        }                        
                        for(int j=intNumColIniChkOtro; j<intNumColFinChkOtro;j++){
                            vecReg.add(j,     null);
                        }                          
                        for(int j=intNumColIniModCabDet; j<intNumColFinModCabDet;j++){
                                vecReg.add(j,     "");
                        }             
                        for(int j=intNumColIniFactorPor; j<intNumColFinFactorPor;j++){
                            vecReg.add(j,     null);
                        }                        

                        for(int j=intNumColIniChkFactor; j<intNumColFinChkFactor;j++){
                            vecReg.add(j,     false);
                        }
                        
                        for(int j=intNumColIniModOtr; j<intNumColFinModOtr;j++){
                                vecReg.add(j,     "");
                        }                         



    
                        for(int j=intNumColIniFacGrpDet; j<intNumColFinFacGrpDet;j++){
                                vecReg.add(j,     "");
                        }                         
                        int i;
                        i=0;
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            vecReg.setElementAt(rst.getString("a" + (i+2) + "_nd_cosUniIngImp"), j);
                            i++;
                        }
                        
                        
                        String tresL="";
                        tresL=rst.getString("tx_codAlt2");
                        Double value = dataMap.get(tresL);
                        Com113 objComm113 = dataMapConsulta.get(tresL);
                        Double costo = objComm113.getNd_costo();
                        
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            vecReg.setElementAt(costo, j);
                        }
                        
                        
////                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
////                            vecReg.setElementAt(value, j);
////                        }                        
                        
                          
                        
////////                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
////////                            vecReg.setElementAt(rst.getString("nd_cosUni"), j);
////////                        }
                        
                        i=0;
                        // para los pedidos embarcados
                        arlDatITEM_PE= new ArrayList();
////                            nd_validaA = intNumColIniPedEmb;
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            if((i%2)==0)//par
                                vecReg.setElementAt(rst.getString("a" + (i+31) + "_nd_cosUniPedEmb"), j);
                            i++;
                        }
                        
                        i=0;
                        
                        //esto es de los pedidos por embarcar
                        arlDatITEM_EM= new ArrayList();
////                        nd_validaB = intNumColIniNotPed;
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            if((i%2)==0)//par
                                vecReg.setElementAt(rst.getString("a" + (i+40) + "_nd_cosUniNotPed"), j);
                            i++;
                        }
                            bgdMargen = BigDecimal.ZERO;//chiqui
                            
                            
                            //precio de venta
                            Double precio  = objComm113.getNe_precio();
//////                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
//////                            if(j==intNumColIniPreReaMar)
//////                                if (objUti.parseString(rst.getString("nd_preVta1")).compareTo("") ==0)
//////                                    vecReg.setElementAt(0, j);
//////                                else
//////                                    {
//////                                        
//////                                        vecReg.setElementAt(objUti.parseDouble(rst.getString("nd_preVta1")), j);
//////////                                        vecReg.setElementAt(rst.getString("nd_preVta1"), j);
//////                                    }
//////                                    
//////                                
//////                            if(j==(intNumColIniPreReaMar+3))
//////                            {
//////                                vecReg.setElementAt(rst.getString("nd_marUti"), j);
//////                                bgdMargen =rst.getBigDecimal("nd_marUti");//chiqui
//////                            }
//////                                
//////                        }

                        bdeEjecu = BigDecimal.ZERO;//chiqui
                        bdeEjeFac = BigDecimal.ZERO;//chiqui
                        bdeFacAju = BigDecimal.ZERO;//chiqui
                        //bdeEjecu =rst.getBigDecimal("bd_eje");//chiqui
                        bdeEjecu = Optional.ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                                            .map(Com113_inv::getNe_eje)       // devuelve Double (puede ser null)
                                            .map(BigDecimal::valueOf)              // lo convierte a BigDecimal
                                            .orElse(BigDecimal.ZERO);              // valor por defecto  
                        
                        bdeFacAju = Optional.ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                                            .map(Com113_inv::getNe_FacAju)       // devuelve Double (puede ser null)
                                            .map(BigDecimal::valueOf)              // lo convierte a BigDecimal
                                            .orElse(BigDecimal.ZERO);              // valor por defecto                         
                        
   
////////                        vecReg.setElementAt(bdeEjecu, (intNumColIniFacCl));
////////                        vecReg.setElementAt(bdeEjeFac, (intNumColIniFacCl+1));
                        
                        

                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++)
                        {
                            if(j==intNumColIniPreReaMar)
                            {
                                if (objUti.parseString(precio).compareTo("") ==0)
                                    vecReg.setElementAt(0, j);
                                else
                                        vecReg.setElementAt(objUti.parseDouble(precio), j);
                            }
                            if(j==(intNumColIniPreReaMar+3))
                            {
                                vecReg.setElementAt(rst.getString("nd_marUti"), j);
                                bgdMargen =rst.getBigDecimal("nd_marUti");//chiqui
                            }
                            
                            
                            if(j==(intNumColIniPreReaMar+5))//ok
                                vecReg.setElementAt(bdeEjecu, j);
                            if(j==(intNumColIniPreReaMar+6))//ok
                                vecReg.setElementAt(bdeFacAju, j);
                            
                            
                                
                        }
                        
                        
//////                        bgdPreVta=rst.getBigDecimal("nd_preVta1");
                        bgdPreVta= BigDecimal.valueOf(precio);
                        
                        //bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        bgdCosUni=BigDecimal.valueOf(value);
                        //////////////////////////////////////////////////////////////
                        
                        
                        

                        /////////////////////////////////////////////////////////////chiqui
                        bgdPrecioSug = BigDecimal.ZERO;
                        bgdFacCosUni = BigDecimal.ZERO;
                        bgdFacCosUniAju= BigDecimal.ZERO;
////                        bgdFacCosUni=rst.getBigDecimal("nd_facCosUni");
                        bgdPreVtaMarUtiPreVta = BigDecimal.ZERO;
                        bgdPreVtaMarUtiPreVtaAju = BigDecimal.ZERO;
                        bgdPreVtaMarUtiPreVtaKg = BigDecimal.ZERO;

                            if ( bgdMargen==null || (bgdMargen.compareTo(BigDecimal.ZERO)==0))
                            {
                                validaPreSu=false;
                            }
                             else
                             {
                                bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargen.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP)));
                                if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                    bgdFacCosUni=BigDecimal.ZERO;
                                else
                                {
                                    bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, 4, BigDecimal.ROUND_HALF_UP));  
                                    bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_CEILING);
                                }
                             } 
                            
                            
                            


                        if (bdeEjecu.compareTo(BigDecimal.ZERO)==0)
                        {
                            if ( bgdMargen==null || (bgdMargen.compareTo(BigDecimal.ZERO)==0))
                            {
                                bgdPreVtaMarUtiPreVta=null;
                                validaPreSu=false;
                            }
                             else
                             {
                                bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargen.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP)));
    ////                            System.out.println("margen_" + bgdMargen);
    ////                            System.out.println("divisor_" + bdeDivisor);
                                if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                {
                                    bdeDividendo=BigDecimal.ZERO;
                                    bgdFacCosUni=BigDecimal.ZERO;
                                    validaPreSu=false;
                                }
                                else
                                {
                                    bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, 4, BigDecimal.ROUND_HALF_UP));  
    ////                                bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                    bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_CEILING);
                                    if ((bgdCosUni!=null )|| (bgdCosUni.compareTo(BigDecimal.ZERO)!=0))
                                    {
                                        bgdPreVtaMarUtiPreVta= bgdCosUni.multiply(bgdFacCosUni);
                                        bgdPreVtaMarUtiPreVtaKg =bgdPreVtaMarUtiPreVta;  
                                       validaPreSu=true;                                    
                                    }
                                    else
                                    {
                                        bgdPreVtaMarUtiPreVta= BigDecimal.ZERO;
                                        bgdPreVtaMarUtiPreVtaKg =bgdPreVtaMarUtiPreVta;
                                        validaPreSu=false;
                                    }


                                }
                             }                            
                        }
                        else
                        {
                            if ( bgdMargen==null || (bgdMargen.compareTo(BigDecimal.ZERO)==0))
                            {
                                bgdPreVtaMarUtiPreVtaAju=null;
                                validaPreSu=false;
                            }
                             else
                             {
                                bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargen.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP)));
    ////                            System.out.println("margen_" + bgdMargen);
    ////                            System.out.println("divisor_" + bdeDivisor);
                                if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                {
                                    bdeDividendo=BigDecimal.ZERO;
                                    bgdFacCosUniAju=BigDecimal.ZERO;
                                    validaPreSu=false;
                                }
                                else
                                {
                                    bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, 4, BigDecimal.ROUND_HALF_UP));  
    ////                                bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                    bgdFacCosUniAju = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_CEILING);
                                    bgdFacCosUniAju = bgdFacCosUniAju.add(bdeEjecu);
                                    
                                    if ((bgdCosUni!=null )|| (bgdCosUni.compareTo(BigDecimal.ZERO)!=0))
                                    {
                                        bgdPreVtaMarUtiPreVtaAju= bgdCosUni.multiply(bgdFacCosUniAju);
                                        bgdPreVtaMarUtiPreVtaKg =bgdPreVtaMarUtiPreVtaAju;  
                                       validaPreSu=true;                                    
                                    }
                                    else
                                    {
                                        bgdPreVtaMarUtiPreVtaAju= BigDecimal.ZERO;
                                        bgdPreVtaMarUtiPreVtaKg =bgdPreVtaMarUtiPreVtaAju;
                                        validaPreSu=false;
                                    }


                                }
                             }                            
                            
                        }

////                         bgdFacCosUniGlo =bgdFacCosUni;
                        ////////////////////////////////////////////////////////////
                        
                        ////bgdFacCosUni=rst.getBigDecimal("nd_facCosUni"); lo quito ciqui
                        
                        vecReg.setElementAt(bgdFacCosUni, (intNumColIniFac));//factor de costo
                        /////////////////lo quito chiqui
////                        bgdPreVtaMarUtiPreVta=new BigDecimal("0");
////                        if (bgdCosUni==null || bgdFacCosUni==null)
////                            vecReg.setElementAt(null, (intNumColIniFac+1));
////                        else{
////                            //Precio de venta con factor
////                            bgdPreVtaMarUtiPreVta=objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesMostrar());//cambiar de 6 decimales a 2. Solicitado por LSC el 22/Feb/2019
////                            vecReg.setElementAt(bgdPreVtaMarUtiPreVta, (intNumColIniFac+1));//redondear a dos decimales
////                        }
                        ///////////////
                        
                        bdeEjeFac = bgdFacCosUni.add(bdeEjecu);
                        
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++)
                        {
                            if(j==(intNumColIniPreReaMar+6))//ok
                                vecReg.setElementAt(bdeEjeFac, j);
                            if(j==(intNumColIniPreReaMar+4))//oo
                                vecReg.setElementAt(bgdFacCosUni, j);                            
                        }     
                        
                        if (bdeEjecu.compareTo(BigDecimal.ZERO)==0)
                        {
                            if (chkMosValKil.isSelected())// para cuando es solo mostrar valores con KILOS
                                vecReg.setElementAt(bgdPreVtaMarUtiPreVtaKg, (intNumColIniFac+1));//redondear a dos decimales// lo puso chiqui
                            else
                                vecReg.setElementAt(bgdPreVtaMarUtiPreVta, (intNumColIniFac+1));//redondear a dos decimales// lo puso chiqui
                        }
                        else
                        {
                            if (chkMosValKil.isSelected())// para cuando es solo mostrar valores con KILOS
                                vecReg.setElementAt(bgdPreVtaMarUtiPreVtaKg, (intNumColIniFac+1));//redondear a dos decimales// lo puso chiqui
                            else
                                vecReg.setElementAt(bgdPreVtaMarUtiPreVtaAju, (intNumColIniFac+1));//redondear a dos decimales// lo puso chiqui                            
                        }

                        
                        
////////                        bdeEjecu = BigDecimal.ZERO;//chiqui
////////                        bdeEjeFac = BigDecimal.ZERO;//chiqui
////////                        //bdeEjecu =rst.getBigDecimal("bd_eje");//chiqui
////////                        bdeEjecu = Optional.ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
////////                                            .map(Com113_inv::getNe_eje)       // devuelve Double (puede ser null)
////////                                            .map(BigDecimal::valueOf)              // lo convierte a BigDecimal
////////                                            .orElse(BigDecimal.ZERO);              // valor por defecto  
////////                        
////////                        bdeEjeFac = bgdFacCosUni.add(bdeEjecu);
////////                        vecReg.setElementAt(bdeEjecu, (intNumColIniFacCl));
////////                        vecReg.setElementAt(bdeEjeFac, (intNumColIniFacCl+1));
                        
                        
                        //margen de utilidad(minimo)
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, (intNumColIniPreReaMar+2));
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, (intNumColIniPreReaMar+2));
                            }
                            else{
                                vecReg.setElementAt(objUti.redondearBigDecimal((((bgdPreVta.multiply(BigDecimal.valueOf(0.75))).subtract(bgdCosUni)).divide((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), (intNumColIniPreReaMar+2));
                            }
                        }


                        if (bdeEjecu.compareTo(BigDecimal.ZERO)==0)
                        {
                            //factor
                            if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, intNumColIniMarFac);
                            }
                            else{
                                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                    vecReg.setElementAt(null, intNumColIniMarFac);
                                }
                                else{
                                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                                    vecReg.setElementAt(objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intNumColIniMarFac);
                                }
                            }                            
                            
                        }
                        else
                        {
                            //factor
                            if (bgdPreVtaMarUtiPreVtaAju==null || bgdPreVtaMarUtiPreVtaAju.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, intNumColIniMarFac);
                            }
                            else{
                                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                    vecReg.setElementAt(null, intNumColIniMarFac);
                                }
                                else{
                                    bgdPreVtaMarUtiPreVtaAju=bgdPreVtaMarUtiPreVtaAju.multiply(BigDecimal.valueOf(0.75));
                                    vecReg.setElementAt(objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVtaAju.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVtaAju, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intNumColIniMarFac);
                                }
                            }                              
                        }
                        

                        
                        //precio venta real kilo
                        bgdPesUni=rst.getBigDecimal("nd_pesItmKgr");
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, (intNumColIniPreReaMar+1));
                        }
                        else{
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, (intNumColIniPreReaMar+1));
                            }
                            else{
                                
                                //bgdPreObjKil=((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()));
                                //bgdPreObjKil=((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()));
                                
                                bgdPreObjKil=((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdeIVAGP)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()));
                                vecReg.setElementAt(bgdPreObjKil, (intNumColIniPreReaMar+1));
                            }
                        }
                                
                        //precio lista calculado
                        if (bgdPreObjKil==null || bgdPreObjKil.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, intNumColIniPre);
                        }
                        else{
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, intNumColIniPre);
                            }
                            else{
                                vecReg.setElementAt(rst.getString("nd_preVtaObjKgr"), intNumColIniPre);
                            }
                        }
                        
                        
                        vecDat.add(vecReg);
                        
                        //inicio fix
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_FIX_LIN,"");
                        //System.out.println("codigo maestros fijo: " + rst.getString("co_itmMae"));
                        vecRegFix.add(INT_TBL_FIX_COD_MAE,rst.getString("co_itmMae"));
                        vecRegFix.add(INT_TBL_FIX_COD_SIS,rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT,rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_3,rst.getString("tx_codAltDyn"));
                        vecRegFix.add(INT_TBL_FIX_NOM_ITM,rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_FIX_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecRegFix.add(INT_TBL_FIX_COD_UNI,rst.getString("co_reg"));
                        vecRegFix.add(INT_TBL_FIX_DEC_UNI,rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_FIX_BUT_UNI,null);
                        vecRegFix.add(INT_TBL_FIX_PES_KGR,rst.getString("nd_pesItmKgr"));
                        vecRegFix.add(INT_TBL_FIX_PES_KGR_AUX,rst.getString("nd_pesItmKgr"));
                        //vecRegFix.add(INT_TBL_FIX_STK_CON,rst.getString("nd_stkAct"));
                        vecRegFix.add(INT_TBL_FIX_STK_CON,nd_stock);
                        vecRegFix.add(INT_TBL_FIX_COD_GEN,rst.getString("tx_codgene"));
                        vecRegFix.add(INT_TBL_FIX_IVA,rst.getDouble("nd_porIvaVen"));
                        //vecRegFix.add(INT_TBL_FIX_GRP,rst.getInt("co_grpInv"));
//                        vecRegFix.add(INT_TBL_FIX_DET,rst.getInt("co_detGrpInv"));

                        vecRegFix.add(INT_TBL_FIX_GRP,ne_grupo);      
                        
                     
                        vecRegFix.add(INT_TBL_FIX_DET,ne_detgrupo);
                        
                        Double nd_factor = nd_factor = Optional
                                            .ofNullable(dataMapInv.get(rst.getString("tx_codAlt2")))
                                            .map(Com113_inv::getNe_factor)   
                                            .orElse(0.0); 
                        vecRegFix.add(INT_TBL_FIX_FAC,nd_factor);
                        
                        vecDatFix.add(vecRegFix);
                        //fin fix
                    }
                    else
                    {
                        break;
                    }
		
                    //Configurar JTable: Establecer columnas editables.
                    vecAux=new Vector();
                    vecAuxFix=new Vector();
                    
////////////////////                    if (objParSis.getCodigoUsuario()==1)
////////////////////                    {
                        vecAux.add("" + INT_TBL_DAT_COD_ALT);vecAuxFix.add("" + INT_TBL_FIX_COD_ALT);
                        vecAux.add("" + INT_TBL_DAT_NOM_ITM);vecAuxFix.add("" + INT_TBL_FIX_NOM_ITM);
                        vecAux.add("" + INT_TBL_DAT_DEC_UNI);vecAuxFix.add("" + INT_TBL_FIX_DEC_UNI);
                        vecAux.add("" + INT_TBL_DAT_BUT_UNI);vecAuxFix.add("" + INT_TBL_FIX_BUT_UNI);
                        vecAux.add("" + INT_TBL_DAT_PES_KGR);vecAuxFix.add("" + INT_TBL_FIX_PES_KGR);
                        vecAux.add("" + INT_TBL_DAT_COD_GEN);vecAuxFix.add("" + INT_TBL_FIX_COD_GEN);
                        vecAux.add("" + INT_TBL_DAT_IVA);vecAuxFix.add("" + INT_TBL_FIX_IVA);
                        vecAux.add("" + INT_TBL_DAT_GRP);vecAuxFix.add("" + INT_TBL_FIX_GRP);
                        vecAux.add("" + INT_TBL_DAT_DET);vecAuxFix.add("" + INT_TBL_FIX_DET);
                        vecAux.add("" + INT_TBL_DAT_FAC);vecAuxFix.add("" + INT_TBL_FIX_FAC);
                        
                        
                        //Modificar "Precio de venta 1"
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            if(j==intNumColIniPreReaMar){
                                vecAux.add(""+j);
                            }//precio venta
                        }
                        //Modificar "Margen de utilidad".
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            if(j==(intNumColIniPreReaMar+3)){
                                vecAux.add(""+j);
                            }//margen minimo - margen utilidad
                            
                            if(j==(intNumColIniPreReaMar+5))//ok
                            {
                                vecAux.add(""+j);
                            }//margen minimo - margen utilidad
                            
                        }
                        //Modificar "Cantidad maxima para venta".
                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecAux.add(""+j);//cantidad maxima de precio de venta
                        }
                        
//////////////////                    }
//////////////////                    else
//////////////////                    {
//////////////////                        if(objParSis.getCodigoUsuario()==90){
//////////////////                            vecAux.add("" + INT_TBL_DAT_COD_ALT);
//////////////////                            vecAuxFix.add("" + INT_TBL_DAT_COD_ALT);
//////////////////                        }
//////////////////                        
//////////////////                        
//////////////////                        if (ne_menu==4908)
//////////////////                        {
//////////////////                            //3869: Ficha "Reporte": Tabla->Modificar "Nombre del item".
//////////////////                            if (objPerUsr.isOpcionEnabled(4913)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_NOM_ITM);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_NOM_ITM);
//////////////////                            }
//////////////////                            //3870: Ficha "Reporte": Tabla->Modificar "Unidad de medida".
//////////////////                            if (objPerUsr.isOpcionEnabled(4914)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_BUT_UNI);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_BUT_UNI);
//////////////////                            }
//////////////////                            //3871: Ficha "Reporte": Tabla->Modificar "Precio de venta 1".
//////////////////                            if (objPerUsr.isOpcionEnabled(4915))
//////////////////                            {
//////////////////                                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
//////////////////                                    if(j==intNumColIniPreReaMar){
//////////////////                                        vecAux.add(""+j);
//////////////////                                    }//precio venta
//////////////////                                }
//////////////////                            }
//////////////////                            //3872: Ficha "Reporte": Tabla->Modificar "Margen de utilidad".
//////////////////                            if (objPerUsr.isOpcionEnabled(4916))
//////////////////                            {
//////////////////                                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
//////////////////                                    if(j==(intNumColIniPreReaMar+3)){
//////////////////                                        vecAux.add(""+j);
//////////////////                                    }//margen minimo - margen utilidad
//////////////////                                }
//////////////////                            }
//////////////////                            //3873: Ficha "Reporte": Tabla->Modificar "Peso(kg)"
//////////////////                            if (objPerUsr.isOpcionEnabled(4917)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_PES_KGR);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_PES_KGR);
//////////////////                            }
//////////////////
//////////////////                            
//////////////////                        }
//////////////////                        else
//////////////////                        {
//////////////////                            if (objPerUsr.isOpcionEnabled(4677)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_NOM_ITM);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_NOM_ITM);
//////////////////                            }
//////////////////                            //3870: Ficha "Reporte": Tabla->Modificar "Unidad de medida".
//////////////////                            if (objPerUsr.isOpcionEnabled(4678)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_BUT_UNI);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_BUT_UNI);
//////////////////                            }
//////////////////                            //3871: Ficha "Reporte": Tabla->Modificar "Precio de venta 1".
//////////////////                            if (objPerUsr.isOpcionEnabled(4679))
//////////////////                            {
//////////////////                                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
//////////////////                                    if(j==intNumColIniPreReaMar){
//////////////////                                        vecAux.add(""+j);
//////////////////                                    }//precio venta
//////////////////                                }
//////////////////                            }
//////////////////                            //3872: Ficha "Reporte": Tabla->Modificar "Margen de utilidad".
//////////////////                            if (objPerUsr.isOpcionEnabled(4680)){
//////////////////                                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
//////////////////                                    if(j==(intNumColIniPreReaMar+3)){
//////////////////                                        vecAux.add(""+j);
//////////////////                                    }//margen minimo - margen utilidad
//////////////////                                }
//////////////////                            }
//////////////////                            //3873: Ficha "Reporte": Tabla->Modificar "Peso(kg)"
//////////////////                            if (objPerUsr.isOpcionEnabled(4681)){
//////////////////                                vecAux.add("" + INT_TBL_DAT_PES_KGR);
//////////////////                                vecAuxFix.add("" + INT_TBL_DAT_PES_KGR);
//////////////////                            }                            
//////////////////                            
//////////////////                        }
//////////////////                        
//////////////////                        
//////////////////                        
//////////////////                        
//////////////////
//////////////////                        //3874: Ficha "Reporte": Tabla->Modificar "Cantidad maxima para venta".
////////////////////                        if (objPerUsr.isOpcionEnabled(3874)){
//////////////////
////////////////////                        }
//////////////////                    }
                    

                    
////////////////////                    for(int j=intNumColIniFac; j<intNumColFinFac;j++){
////////////////////                        if(j==intNumColIniFac){
////////////////////                            vecAux.add(""+j);
////////////////////                        }                            
////////////////////                        if(j==(intNumColFinFac-1)){
////////////////////                            vecAux.add(""+j);
////////////////////                        }
////////////////////                    }
                    
                    
                    
                        //Modificar "check precio sugerido actual".revisar
                        for(int j=intNumColIniChk; j<intNumColFinChk;j++){
                            vecAux.add(""+j);
                        }
//////                    for(int j=intNumColIniFacCl; j<intNumColFinFacCl;j++){
//////                        if(j==intNumColIniFacCl){
//////                            vecAux.add(""+j);
//////                        }                            
//////////                        if(j==(intNumColFinFacCl-1)){
//////////                            vecAux.add(""+j);
//////////                        }
//////                    }                        
                        
                                                //Modificar "check precio sugerido primero".revisar
                        for(int j=intNumColIniChkPrimero; j<intNumColFinChkPrimero;j++){
                            vecAux.add(""+j);
                        }
                        
                                                //Modificar "check precio sugerido ultimo".revisar
                        for(int j=intNumColIniChkUltimo; j<intNumColFinChkUltimo;j++){
                            vecAux.add(""+j);
                        }
                        
                                                //Modificar "check precio sugerido Primer import".revisar
                        for(int j=intNumColIniChkPrimeraImporta; j<intNumColFinChkPrimeraImporta;j++){
                            vecAux.add(""+j);
                        }        
                                                //Modificar "check precio sugerido Ultima import".revisar
                        for(int j=intNumColIniChkUltimaImporta; j<intNumColFinChkUltimaImporta;j++){
                            vecAux.add(""+j);
                        }          
                                                //Modificar "check factor".revisar
                        for(int j=intNumColIniChkFactor; j<intNumColFinChkFactor;j++){
                            vecAux.add(""+j);
                        }                           
                                                //Modificar "check precio sugerido otro".revisar
                        for(int j=intNumColIniChkOtro; j<intNumColFinChkOtro;j++){
                            vecAux.add(""+j);
                        }    
                        
      
////                                                //19122023
////                        for(int j=intNumColIniFactorPor; j<intNumColFinFactorPor;j++){
////                            vecAux.add(""+j);
////                        }                         
                        
                    
                    for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                        if(j==intNumColIniPre){
                            vecAux.add(""+j);
                        }
                    }
                    
                    for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecAux.add(""+j);
                    }
                    
                    for(int j=intNumColIniModCabDet; j<intNumColFinModCabDet;j++){
                            vecAux.add(""+j);
                    }
                    for(int j=intNumColIniFacGrpDet; j<intNumColFinFacGrpDet;j++){
                            vecAux.add(""+j);
                    } 
                    
                    for(int j=intNumColIniModOtr; j<intNumColFinModOtr;j++){
                            vecAux.add(""+j);
                    }                    
                    
                    
                    
                    objTblMod.setColumnasEditables(vecAux);
                    vecAux=null;
                    objTblModFix.setColumnasEditables(vecAuxFix);
                    vecAuxFix=null;
                                        
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //System.out.println("vecDat: " +  vecDat.toString());
                //System.out.println("vecFix: " +  vecDatFix.toString());
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Asignar vectores al modelo
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
                
                GenerarColumnasAdicionalesImportaciones(0);
                calcularValoresKilos(0);
                 if(chkMosValKil.isSelected())
                    GenerarColumnasAdicionalesImportaciones(1);                
////                ocultaColumnasPrecios();
                //ocultaColumnasMargenPrecio();
                ocultaColumnasFactor();
                calcularMarNotPed();
                calcularMarPedEmb();
                calcularPreLisCalConsulta();
                calcularMarPreLisCal();
               // ocultaColumnaCosto();
                if(objParSis.getCodigoUsuario()!=1)
                {
////                    if (ne_menu==4908)
////                    {
////                        if(!objPerUsr.isOpcionEnabled(4919))
////                        {
////                            ocultaColumnasCostoUnitario();
////                            encerarColumnasCostoUnitario();//esta comentado el codigo - no hace nada el metodo
////                        }                        
////                    }
////                    else
////                    {
                        if(!objPerUsr.isOpcionEnabled(4683))
                        {
                            ocultaColumnasCostoUnitario();
                            encerarColumnasCostoUnitario();//esta comentado el codigo - no hace nada el metodo
                        }                          
////                    }

                }
                GenerarColumnasAdicionales();

                
////                if (chkMosPrimera.isSelected()== false)
////                {
////                    ocultaColumnasPrimerPedido();
////                }
                
                ocultaColumnasPorFiltro();
                ocultaColumnasColoreo();
                guardarTblHist();
                //ColorearCampos();
////                objTblCelRenLblColDinMarUltimaImporta.setBackground(new Color(144, 238, 144));
                

                objTblMod.initRowsState();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                jLabelLey.setVisible(false);
                        
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
private void guardarTblHist()
{
    int intNumFil = objTblMod.getRowCountTrue();
    int intNumCol = objTblMod.getColumnCount();

    for (int i = 0; i < intNumFil; i++) {
        for (int j = 0; j < intNumCol; j++) {
            Object objVal = objTblMod.getValueAt(i, j);
            String strValor = (objVal == null) ? "" : objVal.toString().trim();

            String clave = i + "," + j;
            Com113_hist dato = new Com113_hist(strValor);

            dataMapHist.put(clave, dato);
        }
    }
    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
    
}
private String obtenerTblHist(String llave)
{
    String str_valor="";
    
    if (dataMapHist.containsKey(llave)) {
         str_valor = dataMapHist.get(llave).getStr_valor();
    }
    return str_valor;
}



    
    
private String obtenerConexionSQL()
{
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQLGP="",strAuxGP="";
        String itemsSt ="";
        StringBuilder Item = new StringBuilder(); 
                
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());             
            if(conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                strSQLGP="";  
                
                if (objVenConItm03.ValidaAtributos())
                {
                    strSQLGP+="SELECT cod3l,costo,precio \n";  
                    strSQLGP+=" FROM (SELECT trim(iv01.ITMGEDSC) as cod3l,isnull(iv01.CURRCOST,0) as costo ,ica.*,IV01.*,isnull(iv08.UOMPRICE,0) as precio \n";        
                    strSQLGP+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 \n";            
                    strSQLGP+="     inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as iv08 on (iv01.ITEMNMBR = iv08.ITEMNMBR) \n";                      

                    if (!chkSolStk.isSelected())
                    {
                        strSQLGP+="         LEFT join dynamics2021..view_ItemsClasiAtrib_conGP as ica on (iv01.ITEMNMBR = ica.CodItm) \n"; 
                        strSQLGP+="WHERE   IV08.PRCLEVEL ='GENERAL') as a1 \n"; 

                    }
                    else
                    {
                        strSQLGP+=" LEFT join dynamics2021..view_ItemsClasiAtrib_conGP as ica on (iv01.ITEMNMBR = ica.CodItm)\n";                     
                        strSQLGP+=" inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as dis on (iv01.ITEMNMBR = dis.ITEMNMBR) \n";         
                        strSQLGP+=" WHERE  dis.RCRDTYPE =1 and (QTYONHND-ATYALLOC)>0 and iv08.PRCLEVEL = 'GENERAL' ) as a1   \n";                        
                    }

                    System.out.println("SELECT obtenerConexionSQL" + strSQLGP);                
                        if (!optTod.isSelected())
                        {
                            if (objVenConItm03.isCondicionAplicada())
                            {
                                strAuxGP+=   " WHERE a1.INACTIVE =0  and  a1.cod3l !=''  " + objVenConItm03.getCondicionesSQL();
    ////                            if (chkSolStk.isSelected())                
    ////                                strSQLGP += " AND a3.nd_stkAct>0";
                            }
                        } 
    ////                stmLoc.executeUpdate(strSQL);
                    strSQLGP +=strAuxGP;                    
                }
                else
                {
                    strSQLGP+="SELECT trim(a1.ITMGEDSC) as cod3l,isnull(a1.CURRCOST,0) as costo,isnull(iv08.UOMPRICE ,0) as precio \n";  
                    strSQLGP+="FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as a1 \n";
                    strSQLGP+="     inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as iv08 on (a1.ITEMNMBR = iv08.ITEMNMBR) \n";                      
                    if (chkSolStk.isSelected())
                    {
                        strSQLGP+=" inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as dis on (a1.ITEMNMBR = dis.ITEMNMBR) \n";         
                        strSQLGP+=" WHERE  dis.RCRDTYPE =1 and (dis.QTYONHND-dis.ATYALLOC)>0 AND  iv08.PRCLEVEL = 'GENERAL'  \n";
                        if (objVenConItm03.isCondicionAplicada())
                            strAuxGP+=   " AND   a1.INACTIVE =0  and  a1.ITMGEDSC !=''  " + objVenConItm03.getCondicionesSQL();
                        else
                            strAuxGP+=   " AND  a1.INACTIVE =0  and  a1.ITMGEDSC !='' ";                                        

                    }
                    else
                    {
                        if (objVenConItm03.isCondicionAplicada())
                            strAuxGP+=   " WHERE   a1.INACTIVE =0  AND  iv08.PRCLEVEL = 'GENERAL' and  a1.ITMGEDSC !=''  " + objVenConItm03.getCondicionesSQL();
                        else
                            strAuxGP+=   " WHERE  a1.INACTIVE =0  AND  iv08.PRCLEVEL = 'GENERAL' and  a1.ITMGEDSC !='' ";                        
                    }

////                    if (optTod.isSelected())
////                    {

////                    } 
                        
    ////                stmLoc.executeUpdate(strSQL);
    
                    

                    System.out.println("SELECT obtenerConexionSQL" + strSQLGP);                

                    strSQLGP +=strAuxGP;
                    
                }
                

               
                rstLoc=stmLoc.executeQuery(strSQLGP);
                
                   //16092024       
                while (rstLoc.next())
                {
////                    Items = rstLoc.getString("item");
                    StringBuilder Items = new StringBuilder(rstLoc.getString("cod3l"));                    
                    Item.append("'").append(Items).append("'").append(",");    
                    
                    dataMapConsulta.put(rstLoc.getString("cod3l"), new Com113 ( rstLoc.getDouble("costo"),rstLoc.getDouble("precio")));  //18092024                                       
                    dataMap.put(rstLoc.getString("cod3l"), rstLoc.getDouble("costo"));                    
                    
                }
                if (Item.length() > 0) 
                    Item.deleteCharAt(Item.length() - 1);
                
                itemsSt=Item.toString();
                
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;                      
            }

            
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            itemsSt="";
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            itemsSt="";
        }        
        return  itemsSt;
}    

private String obtenerConexionSQLGRP(String cadenaItems)
{
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQLGP="",strAuxGP="";
        String itemsSt ="";
        StringBuilder Item1 = new StringBuilder();         
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());             
            if(conLoc!=null)
            {
                
                stmLoc=conLoc.createStatement();
                strSQLGP="";  
                

                    strSQLGP+="SELECT trim(a1.ITMGEDSC) as cod3l , isnull(a1.CURRCOST,0) as costo,isnull(iv08.UOMPRICE,0) as precio  \n";  
                    strSQLGP+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as a1  \n";     
                    strSQLGP+="       INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as iv08 on (a1.ITEMNMBR = iv08.ITEMNMBR)   \n";                         
                    strSQLGP+=" WHERE iv08.prclevel ='general' and a1.INACTIVE =0 and a1.ITEMNMBR not like '%-L%'  and a1.ITMGEDSC != ''  and a1.ITMGEDSC not in \n";    
                    strSQLGP+=" (    \n";
                    strSQLGP+="     select distinct (ITMGEDSC) as tresl \n"; 
                    strSQLGP+="     from Dynamics2021..tbm_detGrpInv as det \n"; 
                    strSQLGP+="         inner join DYNAMICS2021..tbm_cabGrpInv as cab on (det.co_grpInv = cab.co_grpInv) \n"; 
                    strSQLGP+="         inner join tuval..IV00101 as iv01 on (det.tx_codItm = iv01.ITEMNMBR) \n";
                    if (validaTodo)
                    {
                        strSQLGP+="     where  cab.co_tipAgr =1 and det.co_reg!=1 and cab.st_reg ='A'\n";
                        strSQLGP+=" ) \n";   
                    }
                    else
                    {
                        strSQLGP+="     where iv01.ITMGEDSC in (  "+cadenaItems +")\n";
                        strSQLGP+="           and cab.co_tipAgr =1 and det.co_reg!=1 and cab.st_reg ='A' \n";    
                        strSQLGP+=" ) \n";   
                        strSQLGP+="     AND a1.ITMGEDSC in (  "+cadenaItems +")\n";
                    }    
                    strSQLGP+=" ORDER BY A1.ITEMNMBR DESC \n";
                rstLoc=stmLoc.executeQuery(strSQLGP);

                while (rstLoc.next())
                {
////                    Items = rstLoc.getString("item");
                    StringBuilder Items1 = new StringBuilder(rstLoc.getString("cod3l"));                    
                    Item1.append("'").append(Items1).append("'").append(",");  
                    
                    dataMap.put(rstLoc.getString("cod3l"), rstLoc.getDouble("costo")); 
                    dataMapConsulta.put(rstLoc.getString("cod3l"), new Com113 ( rstLoc.getDouble("costo"),rstLoc.getDouble("precio")));  //18092024                   
                }
                if (Item1.length() > 0) 
                    Item1.deleteCharAt(Item1.length() - 1);
                
                itemsSt=Item1.toString();
                
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;                      
            }

            
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            itemsSt="";
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            itemsSt="";
        }        
        return  itemsSt;
}    



   public boolean ocultaColumnasPorFiltro()
   {
       boolean blnRes=false;
       
        if(chkMosPrimera.isSelected()) 
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniMarPrimero, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColFinMarPrimero-1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniChkPrimero, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniMarPrimero, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColFinMarPrimero-1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniChkPrimero, tblDat);
        }       
       
        if(chkMosUltima.isSelected())
        {
////            objTblMod.removeSystemHiddenColumn(intNumColFinChkPrimero, tblDat);
////            objTblMod.removeSystemHiddenColumn(intNumColIniChkUltimo, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarUltimo, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColFinMarUltimo-1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniChkUltimo, tblDat);            
        }
        else
        {
////            objTblMod.addSystemHiddenColumn(intNumColFinChkPrimero, tblDat);
////            objTblMod.addSystemHiddenColumn(intNumColIniChkUltimo, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarUltimo, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColFinMarUltimo-1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniChkUltimo, tblDat);            
        }
        
        if(chkMosPrimerIngre.isSelected())
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniMarPrimeraImporta, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarPrimeraImporta+1, tblDat);            
            objTblMod.removeSystemHiddenColumn(intNumColIniChkPrimeraImporta, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniMarPrimeraImporta, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarPrimeraImporta+1, tblDat);            
            objTblMod.addSystemHiddenColumn(intNumColIniChkPrimeraImporta, tblDat);
        }  
        
        if(chkMosUltimoIngre.isSelected())
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniMarUltimaImporta, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarUltimaImporta+1, tblDat);            
            objTblMod.removeSystemHiddenColumn(intNumColIniChkUltimaImporta, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniMarUltimaImporta, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarUltimaImporta+1, tblDat);            
            objTblMod.addSystemHiddenColumn(intNumColIniChkUltimaImporta, tblDat);
        }        
        
        if(chkMosActual.isSelected())
        {
            //objTblMod.removeSystemHiddenColumn(intNumColIniFac, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColFinFac-1, tblDat);            
            objTblMod.removeSystemHiddenColumn(intNumColIniChk, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniFac, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColFinFac-1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniChk, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniChk-1, tblDat);
        }
        
        if(chkMosPor.isSelected())
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniFactorPor, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColFinFactorPor-1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniChkFactor, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniFactorPor, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColFinFactorPor-1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniChkFactor, tblDat);
        }
        if(chkMosMar.isSelected()) 
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniMarPrimeraImporta+1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarUltimaImporta+1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarPrimero+1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarUltimo+1, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniMarFac, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniMarPrimeraImporta+1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarUltimaImporta+1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarPrimero+1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarUltimo+1, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniMarFac, tblDat);
        }        
        
        if(chkMosAju.isSelected()) 
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniPreReaMar+6, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniPreReaMar+5, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniPreReaMar+6, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniPreReaMar+5, tblDat);
        }          
        
        if(chkMosAdi.isSelected()) 
        {
            objTblMod.removeSystemHiddenColumn(intNumColIniModCabDet, tblDat);
            objTblMod.removeSystemHiddenColumn(intNumColIniModOtr, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(intNumColIniModCabDet, tblDat);
            objTblMod.addSystemHiddenColumn(intNumColIniModOtr, tblDat);
        }  
        
       
     
  
       
       return blnRes;
   }
   
private String ValidacionPedidos(int i)//chiqui27112023
   {
       int contadorA=-1,contadorB=-1,j=0,k=0;
       String indicador="";
       //si es el primero del A y el primero del B es AB
       //si es el primero del A y el ultimo del B es AZ
       //si es el primero del A es A
       //si es el primero del A y el ultimo del A es AA
       //si es el primero del B es B
       //si es el primero del B y el ultimo del B
       //pedido embarcado
       k=0;
       for (j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++)
       {
           BigDecimal bgdPeso = BigDecimal.ZERO;
           bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));               
           if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO)) 
           {
               if((k%2)==0)
                contadorA=contadorA+1;
           }
           k=k+1;
       }
       
       //pedido por embarcar
       k=0;
       for (j=intNumColIniNotPed; j<intNumColFinNotPed;j++)
       {
                      BigDecimal bgdPeso = BigDecimal.ZERO;
bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));                                     
           if ((objTblMod.getValueAt(i, j)!=null)&& (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
           {
               if((k%2)==0)
                contadorB=contadorB+1;
           }
           k=k+1;
       }
       
       if (contadorA>=0 && contadorB==0)
           indicador ="AB";
       if (contadorA>=0 && contadorB>0)
           indicador ="AZ";
       if (contadorA==0 && contadorB==-1)
           indicador ="A";
       if (contadorA>0 && contadorB==-1)
           indicador ="AA";
       if (contadorA==-1 && contadorB==0)
           indicador ="B";
       if (contadorA==-1 && contadorB>0)
           indicador ="BB";       
       
       
       return indicador;
   }

    
   private String ValidacionImportaciones(int i)
   {
       int j=0,k=0;
       String indicador="";
       //si es el primero del A y el primero del B es AB
       //si es el primero del A y el ultimo del B es AZ
       //si es el primero del A es A
       //si es el primero del A y el ultimo del A es AA
       //si es el primero del B es B
       //si es el primero del B y el ultimo del B
       //pedido embarcado
       k=0;
       for (j=intNumColIniIngImp; j<intNumColFinIngImp;j++)
       {
           if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO)) 
               k=k+1;
           
       }
       if (k==1)
           indicador ="A";
       if (k>1)
           indicador ="AB";
       if (k==0)
           indicador ="C";
       return indicador;
   }
   
////   private double obtenerPedido(int tipo, int indice,int columna)
////   {
////       //BigDecimal bgdCosUni, bgdMargCosUni,bgdPeso;
////       double cantidad=0;
////       int k=0,i=0,j=0;
////       i=indice;
////       k=0;
////       if (tipo==1)//para obtener el ultimo pedido disponible
////       {
////           
////           for (j=intNumColFinPedEmb-1; j>intNumColIniPedEmb;j--)
////           {
////               //bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
////               
////               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
////               {
////                   if((k%2)==0)
////                   {
////                       if (columna ==1)
////                       {
////////                           if (chkMosValKil.isSelected())
////////                           {
////////                               
////////                               cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j-1));//costo
//////// //                              bgdPreVtaMarUtiPreVtaKg = bdeDividendo.multiply(new BigDecimal("1.12"));
////////   //                            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j-1)));
////////     //                          cantidad = bgdCosUni.multiply(bgdPeso);
////////                           }
////////                           else
////                               cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j-1));//costo
////                           
////                       }
////                           
////                       else
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j));//margen
////                       break;
////                   }
////               }
////               k=k+1;
////           }
////       }
////       if (tipo==2)//para obtner el ultimo pedido por embarcar disponible
////       {
////           for (j=intNumColFinNotPed-1; j>intNumColIniNotPed;j--)
////           {
////               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
////               {
////                   if((k%2)==0)
////                   {
////                       if (columna ==1)
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j-1));//costo
////                       else
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j));//margen
////                       break;
////                   }
////               }
////               k=k+1;
////           }
////       }
////       if (tipo==3)//para obtner el primer pedido por embarcar disponible
////       {
////           for (j=intNumColIniNotPed; j<intNumColFinNotPed;j++)
////           {
////               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
////               {
////                   if((k%2)==0)
////                   {
////                       if (columna ==1)
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j));//costo
////                       else
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j+1));//margen
////                       break;
////                   }
////               }
////               k=k+1;
////           }
////       }        
////        if (tipo==4)//para obtener el primer pedido disponible
////       {
////           for (j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++)
////           {
////               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
////               {
////                   if((k%2)==0)
////                   {
////                       if (columna ==1)
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j));//costo
////                       else
////                           cantidad =objUti.parseDouble(objTblMod.getValueAt(i, j+1));//margen
////                       break;
////                   }
////               }
////               k=k+1;
////           }
////       }
////
////       
////       return cantidad;
////   }
   
private BigDecimal obtenerPedido(int tipo, int indice,int columna)
   {
       BigDecimal bgdCosUni, bgdMargCosUni,bgdPeso;
       BigDecimal cantidad=BigDecimal.ZERO;
       int k=0,i=0,j=0;
       i=indice;
       k=0;
       if (tipo==1)//para obtener el ultimo pedido disponible
       {
           
           for (j=intNumColFinPedEmb-1; j>intNumColIniPedEmb;j--)
           {
               bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
               
               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))//COJE EL MARGEN
               {
                   if((k%2)==0)
                   {
                       if (columna ==1)
                       {
                           if (chkMosValKil.isSelected())
                           {
                               bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j-1)));
                               cantidad = bgdCosUni.multiply(bgdPeso);//costo
                           }
                           else
                               cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j-1)));//costo O P3020-POR EJEMPLO
                           
                       }
                           
                       else
                           cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));//margen
                       break;
                   }
               }
               k=k+1;
           }
       }
       if (tipo==2)//para obtner el ultimo pedido por embarcar disponible
       {
           for (j=intNumColFinNotPed-1; j>intNumColIniNotPed;j--)
           {
               bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));               
               if ((objTblMod.getValueAt(i, j-1)!=null) && (objTblMod.getValueAt(i, j-1) != BigDecimal.ZERO))
               {
                   if((k%2)==0)
                   {
                       if (columna ==1)
                       {
                           if (chkMosValKil.isSelected())
                           {
                               bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j-1)));
                               cantidad = bgdCosUni.multiply(bgdPeso);//costo
                           }
                           else
                               cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j-1)));//costo                           
                       }
                       else
                           cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));//margen
                       break;
                   }
               }
               k=k+1;
           }
       }
       if (tipo==3)//para obtner el primer pedido por embarcar disponible
       {
           for (j=intNumColIniNotPed; j<intNumColFinNotPed;j++)
           {
               bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
               {
                   if((k%2)==0)
                   {
                       if (columna ==1)
                       {
                           if (chkMosValKil.isSelected())
                           {
                               bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j)));//costo
                               cantidad = bgdCosUni.multiply(bgdPeso);//costo
                           }
                           else
                               cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));//costo                            
                       }
                       else
                           cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j+1)));//margen
                       break;
                   }
               }
               k=k+1;
           }
       }        
        if (tipo==4)//para obtener el primer pedido disponible
       {
           for (j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++)
           {
               bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));               
               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
               {
                   if((k%2)==0)
                   {
                       if (columna ==1)
                       {
                           if (chkMosValKil.isSelected())
                           {
                               bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j)));//costo
                               cantidad = bgdCosUni.multiply(bgdPeso);//costo
                           }
                           else
                               cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));//costo                            
                       }
                       else
                           cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j+1)));//margen
                       break;
                   }
               }
               k=k+1;
           }
       }

       
       return cantidad;
   }

private BigDecimal obtenerPedidoPlus(int tipo, int indice,int columna)
{
       BigDecimal bgdCosUni, bgdMargCosUni,bgdPeso;
       BigDecimal cantidad=BigDecimal.ZERO;
       int k=0,i=0,j=0;
       i=indice;
       k=0; 
       if (tipo==1)//para obtener el ultimo pedido disponible
       {
           for (j=intNumColFinPedEmb-1; j>intNumColIniPedEmb;j--)
           {
            bgdPeso=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));               
               if ((objTblMod.getValueAt(i, j) ==null) || (objTblMod.getValueAt(i, j) == BigDecimal.ZERO))//COJE EL MARGEN
               {
                   if (chkMosValKil.isSelected())
                   {
                       bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j-1)));
                       cantidad = bgdCosUni.multiply(bgdPeso);//costo
                   }
                   else
                       cantidad =BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j-1)));//costo O P3020-POR EJEMPLO                   
               }            
           }           
           
       }  
       return cantidad;
}

private BigDecimal obtenerImportacion(int tipo, int indice)//chiqui27112023
   {
       BigDecimal bgdCosUni, bgdMargCosUni,bgdPeso;
       BigDecimal cantidad=BigDecimal.ZERO;
       int fin=intNumColFinIngImp-1;
       int k=0,i=0,j=0;
       i=indice;
       k=0;
////       if (tipo==1)//para obtner la primera importacion
////       {
////           for (j=intNumColIniIngImp; j<intNumColFinIngImp;j++)
////           {
////               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
////               {
////                   cantidad=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j)));//costo
////                   break;
////               }
////           }
////       }    
        if (tipo==1)//para obtner la penultima importacion
        {
            int l=0;
           for (j=fin; j>intNumColIniIngImp;j--)
           {
               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
               {
                   if (l==1)
                   {
                       cantidad=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j)));//costo
                       break;                       
                   }
                   l=l+1;

               }
           }
        } 

        j=0;
        if (tipo==2)//para obtner la ultima importacion
        {
           for (j=fin; j>intNumColIniIngImp;j--)
           {
               if ((objTblMod.getValueAt(i, j)!=null) && (objTblMod.getValueAt(i, j) != BigDecimal.ZERO))
               {
                   cantidad=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i,j)));//costo
                   break;
               }
           }
        } 

        
       return cantidad;
   }

private boolean GenerarColumnasAdicionalesImportaciones(int tipo)
{
    int i=0,intNumFil=0,validaImporta=-1;
    boolean blnRes =false;
    String indicador="";
       BigDecimal cantidad,bgdCosUniU,bgdCosUniP,bgdPrecioSug,bgdPreVtaMarUtiPreVtaPrim,bgdPreVtaMarUtiPreVtaUlti;
    intNumFil=objTblMod.getRowCountTrue();
    
    bgdCosUniU = BigDecimal.ZERO;//chiqui    
    bgdCosUniP = BigDecimal.ZERO;//chiqui   +
    
    for (i=0; i<intNumFil;i++) 
    {
        validaImporta=-1;
        indicador =ValidacionImportaciones(i);
        if (indicador =="A")
        {
            cantidad=obtenerImportacion(2,i);//el primero
            bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
            validaImporta=1;
        }
        if (indicador =="AB")
        {
            cantidad=obtenerImportacion(1,i);//el primero
            bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
            cantidad=obtenerImportacion(2,i);//el ultimo
            bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());            
            validaImporta=2;
        }   
        if (indicador =="C")
        {
            bgdCosUniU = BigDecimal.ZERO;//chiqui    
            bgdCosUniP = BigDecimal.ZERO;//chiqui 
            validaImporta=3;
        }
        if (validaImporta==1)        
        {
    /////////////////////////////////////////////////////////////chiqui////precio Primer
            bgdPrecioSug = BigDecimal.ZERO;
            bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;

            if ((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) )
            {
                bgdPreVtaMarUtiPreVtaPrim=null;
    ////                            validaPreSu=false;

            }
             else
             {
                bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniFac)==null?"0":(objTblMod.getValueAt(i, intNumColIniFac).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniFac).toString()) , objParSis.getDecimalesMostrar() );
                bgdPreVtaMarUtiPreVtaPrim= bgdCosUniP.multiply(bgdFacCosUniGlo);
             }
            bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;
            if (tipo==0)
            {
////                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//
////                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//
////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta);//  
////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta+1);//           

                objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta);//
                objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta+1);//
                
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarUltimaImporta);//  
                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarUltimaImporta+1);//                     
            }
            else
            {
                objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta+1);//
////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta);//  
                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarUltimaImporta+1);//                  
            }

    ///////////////////////////////////////////
                    
        }
        
        if (validaImporta==2)        
        {
    /////////////////////////////////////////////////////////////chiqui////precio Primer
            bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;
            bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;            

            if (((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) ) || ((bgdCosUniU==null) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0) ))
            {
                bgdPreVtaMarUtiPreVtaPrim=null;
                bgdPreVtaMarUtiPreVtaUlti=null;
    ////                            validaPreSu=false;

            }
             else
             {
                bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniFac)==null?"0":(objTblMod.getValueAt(i, intNumColIniFac).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniFac).toString()) , objParSis.getDecimalesMostrar() );
                bgdPreVtaMarUtiPreVtaPrim= bgdCosUniP.multiply(bgdFacCosUniGlo);
                bgdPreVtaMarUtiPreVtaUlti= bgdCosUniU.multiply(bgdFacCosUniGlo);                
             }
            
            if (tipo==0)
            {
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//intNumColIniMarNotPed
                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//            

                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta);//intNumColIniMarNotPed
                objTblMod.setValueAt(bgdCosUniU , i, intNumColIniMarUltimaImporta+1);//                
            }
            else
            {
                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//            
                objTblMod.setValueAt(bgdCosUniU , i, intNumColIniMarUltimaImporta+1);//                  
            }
            
    ///////////////////////////////////////////
                    
        }  
        if (validaImporta==3)        
        {
            bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;//chiqui    
            bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;//chiqui     
            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//intNumColIniMarNotPed     
            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta);//intNumColIniMarNotPed
            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta+1);//intNumColIniMarNotPed     
            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta+1);//intNumColIniMarNotPed            
        }        
    }
    
    
    return blnRes;
}

private boolean GenerarColumnasAdicionalesImportacionesFl(int tipo,int fila, double factorN)
{
    int i=0,intNumFil=0,validaImporta=-1;
    boolean blnRes =false;
    int detGrp =0;
    String strIndMod="";    
    String indicador="";
       BigDecimal cantidad,bgdCosUniU,bgdCosUniP,bgdPrecioSug,bgdPreVtaMarUtiPreVtaPrim,bgdPreVtaMarUtiPreVtaUlti;
    intNumFil=objTblMod.getRowCountTrue();
    
    bgdCosUniU = BigDecimal.ZERO;//chiqui    
    bgdCosUniP = BigDecimal.ZERO;//chiqui   +
    i=fila;
    
////    for (i=0; i<intNumFil;i++) 
////    {
////        detGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_DET)));
        strIndMod=  objUti.parseString(objTblMod.getValueAt(i,intNumColIniModCabDet));
        if(!strIndMod.equals("") && !strIndMod.equals("D"))
        {
            validaImporta=-1;
            indicador =ValidacionImportaciones(i);
            if (indicador =="A")
            {
                cantidad=obtenerImportacion(2,i);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                validaImporta=1;
            }
            if (indicador =="AB")
            {
                cantidad=obtenerImportacion(1,i);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerImportacion(2,i);//el ultimo
                bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());            
                validaImporta=2;
            }   
            if (indicador =="C")
            {
                bgdCosUniU = BigDecimal.ZERO;//chiqui    
                bgdCosUniP = BigDecimal.ZERO;//chiqui 
                validaImporta=3;
            }
            if (validaImporta==1)        
            {
        /////////////////////////////////////////////////////////////chiqui////precio Primer
                bgdPrecioSug = BigDecimal.ZERO;
                bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;

                if ((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) )
                {
                    bgdPreVtaMarUtiPreVtaPrim=null;
        ////                            validaPreSu=false;

                }
                 else
                 {
                     
                    //bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPreReaMar+5)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString()) , objParSis.getDecimalesMostrar() );
                     bgdFacCosUniGlo =objUti.redondearBigDecimal (objUti.parseString(factorN) , objParSis.getDecimalesMostrar() );
                    bgdPreVtaMarUtiPreVtaPrim= bgdCosUniP.multiply(bgdFacCosUniGlo);
                 }
                bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;
                if (tipo==0)
                {
    ////                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//
    ////                objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//
    ////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta);//  
    ////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta+1);//           

                    objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta);//
                    objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta+1);//

                    objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarUltimaImporta);//  
                    objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarUltimaImporta+1);//                     
                }
                else
                {
                    objTblMod.setValueAt(0 , i, intNumColIniMarPrimeraImporta+1);//
    ////                objTblMod.setValueAt(0 , i, intNumColIniMarUltimaImporta);//  
                    objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarUltimaImporta+1);//                  
                }

        ///////////////////////////////////////////

            }

            if (validaImporta==2)        
            {
        /////////////////////////////////////////////////////////////chiqui////precio Primer
                bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;
                bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;            

                if (((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) ) || ((bgdCosUniU==null) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0) ))
                {
                    bgdPreVtaMarUtiPreVtaPrim=null;
                    bgdPreVtaMarUtiPreVtaUlti=null;
        ////                            validaPreSu=false;

                }
                 else
                 {
                    //bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPreReaMar+5)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString()) , objParSis.getDecimalesMostrar() );
                     bgdFacCosUniGlo =objUti.redondearBigDecimal (objUti.parseString(factorN) , objParSis.getDecimalesMostrar() );
                    bgdPreVtaMarUtiPreVtaPrim= bgdCosUniP.multiply(bgdFacCosUniGlo);
                    bgdPreVtaMarUtiPreVtaUlti= bgdCosUniU.multiply(bgdFacCosUniGlo);                
                 }

                if (tipo==0)
                {
                    objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//intNumColIniMarNotPed
                    objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//            

                    objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta);//intNumColIniMarNotPed
                    objTblMod.setValueAt(bgdCosUniU , i, intNumColIniMarUltimaImporta+1);//                
                }
                else
                {
                    objTblMod.setValueAt(bgdCosUniP , i, intNumColIniMarPrimeraImporta+1);//            
                    objTblMod.setValueAt(bgdCosUniU , i, intNumColIniMarUltimaImporta+1);//                  
                }

        ///////////////////////////////////////////

            }  
            if (validaImporta==3)        
            {
                bgdPreVtaMarUtiPreVtaPrim = BigDecimal.ZERO;//chiqui    
                bgdPreVtaMarUtiPreVtaUlti = BigDecimal.ZERO;//chiqui     
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta);//intNumColIniMarNotPed     
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta);//intNumColIniMarNotPed
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaPrim , i, intNumColIniMarPrimeraImporta+1);//intNumColIniMarNotPed     
                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaUlti , i, intNumColIniMarUltimaImporta+1);//intNumColIniMarNotPed            
            }              
        }        
   /// }
    return blnRes;
}


    
    private boolean GenerarColumnasAdicionales()
    {
        int intNumFil =0,i=0,j=0,k=0,validaPedido=-1;
        BigDecimal bgdMargenP,bgdPrecioSug,bgdFacCosUni,bgdPreVtaMarUtiPreVtaP,bgdCosUniP,bdeDivisor,bdeDividendo;
        BigDecimal bgdMargenU,bgdCosUniU,bgdPreVtaMarUtiPreVtaU,cantidad;
        double costo=0,margen=0;
////        double cantidad=0;
        boolean blnRes =false;
        String descripcion ="",indicador="";
        intNumFil=objTblMod.getRowCountTrue();
        bgdMargenP = BigDecimal.ZERO;//chiqui
        bgdCosUniP = BigDecimal.ZERO;//chiqui
        bgdMargenU = BigDecimal.ZERO;//chiqui
        bgdCosUniU = BigDecimal.ZERO;//chiqui
        
        
        for (i=0; i<intNumFil;i++) 
        {
            k=0;
            validaPedido=-1;
            indicador =ValidacionPedidos(i);
            if (indicador =="AB")//pedido embarcado y pedidos por llegar
            {
////                bgdCosUniP=v////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );
////                
                cantidad=obtenerPedido(4,i,1);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(4,i,2);
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());   
                
                cantidad=obtenerPedido(3,i,1);//el ultimo
                bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(3,i,2);
                bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                  
                
////                bgdCosUniU=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed).toString()) , objParSis.getDecimalesMostrar() );
////                bgdMargenU=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed+1).toString()) , objParSis.getDecimalesMostrar() );
                
                validaPedido=2;
            }
            if (indicador =="A")//pedido embarcado
            { 
                cantidad=obtenerPedido(1,i,1);//el primero y a la vez el primero//OBTIENE EL P-2030-0 POREJEMPO
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(1,i,2);//OBTIENE EL MARGEN
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                if ((bgdCosUniP.compareTo(BigDecimal.ZERO)==0) && (bgdMargenP.compareTo(BigDecimal.ZERO)==0))
                {
                   cantidad =obtenerPedidoPlus(1,i,1); 
                   bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                   cantidad=new BigDecimal(objTblMod.getValueAt(i, intNumColIniPreReaMar+3)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+3).toString()));
                   bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                }   
                
                
                validaPedido=1;
            }
            
            if (indicador =="AZ")//pedido embarcado y pedido por llegar
            {
////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb).toString()) , objParSis.getDecimalesMostrar() );
////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );
                
                cantidad=obtenerPedido(4,i,1);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(4,i,2);
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                
                
                cantidad=obtenerPedido(2,i,1);//el ultimo
                bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(2,i,2);
                bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                
                validaPedido=2;
            }
            
            if (indicador =="AA")//pedido embarcado y pedido embarcado
            {
////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb).toString()) , objParSis.getDecimalesMostrar() );
////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );
                
                cantidad=obtenerPedido(4,i,1);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(4,i,2);
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                
                
                cantidad=obtenerPedido(1,i,1);//el ultimo
                bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(1,i,2);
                bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                
                validaPedido=2;
            }
            
            
            if (indicador =="B")//pedido por llegar
            {

                cantidad=obtenerPedido(2,i,1);//el ultimo y a la vez el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(2,i,2);
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                
                
                validaPedido=1;
            }
            if (indicador =="BB")//pedido por llegar y pedido por llegar
            {
////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed).toString()) , objParSis.getDecimalesMostrar() );
////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed+1).toString()) , objParSis.getDecimalesMostrar() );
               
                cantidad=obtenerPedido(3,i,1);//el primero
                bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(3,i,2);
                bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                
                
                cantidad=obtenerPedido(2,i,1);//el ultimo
                bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                cantidad=obtenerPedido(2,i,2);
                bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                validaPedido=2;
            }
            
            
            
                if(validaPedido>=1)
                {
                    /////////////////////////////////////////////////////////////chiqui////precio Primer
                            bgdPrecioSug = BigDecimal.ZERO;
                            bgdFacCosUni = BigDecimal.ZERO;
                            bgdPreVtaMarUtiPreVtaP = BigDecimal.ZERO;

////                            if ((bgdCosUniP==null || bgdMargenP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) || (bgdMargenP.compareTo(BigDecimal.ZERO)==0))
                            if ((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) )
                                
                            {
                                bgdPreVtaMarUtiPreVtaP=null;
    ////                            validaPreSu=false;

                            }
                             else
                             {
                                bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargenP.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
////                                System.out.println("margen_" + bgdMargenP);
////                                System.out.println("divisor_" + bdeDivisor);
                                if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                {
                                    bdeDividendo=BigDecimal.ZERO;
                                    bgdFacCosUni=BigDecimal.ZERO;
                                    bgdFacCosUni=BigDecimal.ZERO;
    ////                                validaPreSu=false;
                                }
                                else
                                {
                                    bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));  
                                    bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                    
                                    
////                                    bgdFacCosUniGlo = objTblMod.(i,intNumColIniFac).toString();
                                    
////                                      bgdFacCosUniGlo = objUti.redondearBigDecimal(objTblMod.getValueAt(i,intNumColIniFac), 2);
                                      bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniFac)==null?"0":(objTblMod.getValueAt(i, intNumColIniFac).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniFac).toString()) , objParSis.getDecimalesMostrar() );
                                            
                                    bgdPreVtaMarUtiPreVtaP= bgdCosUniP.multiply(bgdFacCosUniGlo);
////                                    bgdPreVtaMarUtiPreVtaP= bgdCosUniP.multiply(bgdFacCosUni);
                                    
                                   
    ////                                validaPreSu=true;
                                }

////
////                                System.out.println("diviso__" + bdeDivisor);
////                                System.out.println("dividendo_" + bdeDividendo);

////                                System.out.println("dividendo___" + bdeDividendo);
////                                System.out.println("bgdFacCosUni" + bgdFacCosUni);                            


                             }
                            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaP , i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                            objTblMod.setValueAt(bgdMargenP , i, intNumColIniMarPrimero+1);//
////                            if (bgdPreVtaMarUtiPreVtaP >0)
////                            {
////                                
////                            }
                    ///////////////////////////////////////////

                }
                if(validaPedido==2)
                {
    /////////////////////////////////////////////////////////////chiqui////precio Ultimo
                            bgdPrecioSug = BigDecimal.ZERO;
                            bgdFacCosUni = BigDecimal.ZERO;
                            bgdPreVtaMarUtiPreVtaU = BigDecimal.ZERO;

////                            if ((bgdCosUniU==null || bgdMargenU==null) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0) || (bgdMargenU.compareTo(BigDecimal.ZERO)==0))
                            if ((bgdCosUniU==null ) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0))                                
                            {
                                bgdPreVtaMarUtiPreVtaU=null;
    ////                            validaPreSu=false;

                            }
                             else
                             {
                                bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargenU.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
////                                System.out.println("margen_" + bgdMargenU);
////                                System.out.println("divisor_" + bdeDivisor);
                                if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                {
                                    bdeDividendo=BigDecimal.ZERO;
                                    bgdFacCosUni=BigDecimal.ZERO;
                                    bgdFacCosUni=BigDecimal.ZERO;
    ////                                validaPreSu=false;
                                }
                                else
                                {
                                    bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));  
                                    bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
////                                    bgdPreVtaMarUtiPreVtaU= bgdCosUniU.multiply(bgdFacCosUni);
                                     bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniFac)==null?"0":(objTblMod.getValueAt(i, intNumColIniFac).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniFac).toString()) , objParSis.getDecimalesMostrar() );
                                    bgdPreVtaMarUtiPreVtaU= bgdCosUniU.multiply(bgdFacCosUniGlo);                                    
    ////                                validaPreSu=true;
                                }


////                                System.out.println("diviso__" + bdeDivisor);
////                                System.out.println("dividendo_" + bdeDividendo);
////
////                                System.out.println("dividendo___" + bdeDividendo);
////                                System.out.println("bgdFacCosUni" + bgdFacCosUni);                            


                             }


                            ///////////////////////
                            objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaU , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
                            objTblMod.setValueAt(bgdMargenU , i, intNumColIniMarUltimo+1);//

                }
                else
                {
////                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                    objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
////                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero+1);//intNumColIniMarNotPed
                    objTblMod.setValueAt(0 , i, intNumColIniMarUltimo+1);//intNumColIniMarNotPed                     
                }
////                  objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed 
                
                if (validaPedido ==-1)
                {
                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                    objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero+1);//intNumColIniMarNotPed
                    objTblMod.setValueAt(0 , i, intNumColIniMarUltimo+1);//intNumColIniMarNotPed                    
                }                
////            }

            
            
                        
                        
                         
                        ////////////////////////////////////////////////////////////
                        
                        
                        
                        
                        
            
            
            
            
                ///////////////////////////////////////////                
                
                
                
////            //pedido embarcado
////            for (j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++)
////            {
////                
////                if ((j==intNumColIniPedEmb) || (j==intNumColFinPedEmb))
////                {
////                    if (objTblMod.getValueAt(i, j)!=null)
////                    {
////                        if((k%2)==0)
////                        {
////////                            costo = objUti.parseDouble(objTblMod.getValueAt(i, j));  
////                             bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()) , objParSis.getDecimalesMostrar() );
////                             descripcion = objTblMod.getColumnName(j);
////                        }
////                            
////                        else
////////                            margen = objUti.parseDouble(objTblMod.getValueAt(i, j));
////                            bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()) , objParSis.getDecimalesMostrar() );
////                        k=k+1;
////                    }
////                }
////                  
////                        
////                        
////            }
////            
////            
////            k=0;
////            //por embarcar
////            for (j=intNumColIniNotPed; j<intNumColFinNotPed;j++)
////            {
////                if ((j==intNumColIniNotPed) || (j==intNumColFinNotPed))
////                {
////                    if (objTblMod.getValueAt(i, j)!=null)
////                    {
////                        if((k%2)==0)
////                        {
////                            costo = objUti.parseDouble(objTblMod.getValueAt(i, j));    
////                            descripcion = objTblMod.getColumnName(j);
////                        }
////                            
////                        else
////                            margen = objUti.parseDouble(objTblMod.getValueAt(i, j));
////                        k=k+1;
////                    }
////                }
////            }
////            
            
            ///////////////////////////////////////////////////
            
        }
        
        
        return blnRes;
    }
    
    private boolean GenerarColumnasAdicionalesFl(int fila,double factorN)
    {
        int intNumFil =0,i=0,j=0,k=0,validaPedido=-1;
        BigDecimal bgdMargenP,bgdPrecioSug,bgdFacCosUni,bgdPreVtaMarUtiPreVtaP,bgdCosUniP,bdeDivisor,bdeDividendo;
        BigDecimal bgdMargenU,bgdCosUniU,bgdPreVtaMarUtiPreVtaU,cantidad;
        double costo=0,margen=0;
        int detGrp =0;
        String strIndMod="";         
////        double cantidad=0;
        boolean blnRes =false;
        String descripcion ="",indicador="";
        intNumFil=objTblMod.getRowCountTrue();
        bgdMargenP = BigDecimal.ZERO;//chiqui
        bgdCosUniP = BigDecimal.ZERO;//chiqui
        bgdMargenU = BigDecimal.ZERO;//chiqui
        bgdCosUniU = BigDecimal.ZERO;//chiqui
        
        i=fila;
////        for (i=0; i<intNumFil;i++) 
////        {
            
            detGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_DET)));
            strIndMod=  objUti.parseString(objTblMod.getValueAt(i,intNumColIniModCabDet));
            if(!strIndMod.equals("") && !strIndMod.equals("D"))
            {
                k=0;
                validaPedido=-1;
                indicador =ValidacionPedidos(i);
                if (indicador =="AB")//pedido embarcado y pedidos por llegar
                {
    ////                bgdCosUniP=v////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );
    ////                
                    cantidad=obtenerPedido(4,i,1);//el primero
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(4,i,2);
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());   

                    cantidad=obtenerPedido(3,i,1);//el ultimo
                    bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(3,i,2);
                    bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                  

    ////                bgdCosUniU=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed).toString()) , objParSis.getDecimalesMostrar() );
    ////                bgdMargenU=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed+1).toString()) , objParSis.getDecimalesMostrar() );

                    validaPedido=2;
                }
                if (indicador =="A")//pedido embarcado
                {
                    cantidad=obtenerPedido(1,i,1);//el primero y a la vez el primero//OBTIENE EL P-2030-0 POREJEMPO
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(1,i,2);//OBTIENE EL MARGEN
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    if ((bgdCosUniP.compareTo(BigDecimal.ZERO)==0) && (bgdMargenP.compareTo(BigDecimal.ZERO)==0))
                    {
                       cantidad =obtenerPedidoPlus(1,i,1); 
                       bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                       cantidad=new BigDecimal(objTblMod.getValueAt(i, intNumColIniPreReaMar+3)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+3).toString()));
                       bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    }   


                    validaPedido=1;
                }

                if (indicador =="AZ")//pedido embarcado y pedido por llegar
                {
    ////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb).toString()) , objParSis.getDecimalesMostrar() );
    ////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );

                    cantidad=obtenerPedido(4,i,1);//el primero
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(4,i,2);
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());


                    cantidad=obtenerPedido(2,i,1);//el ultimo
                    bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(2,i,2);
                    bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());

                    validaPedido=2;
                }

                if (indicador =="AA")//pedido embarcado y pedido embarcado
                {
    ////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb).toString()) , objParSis.getDecimalesMostrar() );
    ////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPedEmb+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPedEmb+1).toString()) , objParSis.getDecimalesMostrar() );

                    cantidad=obtenerPedido(4,i,1);//el primero
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(4,i,2);
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());


                    cantidad=obtenerPedido(1,i,1);//el ultimo
                    bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(1,i,2);
                    bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());

                    validaPedido=2;
                }


                if (indicador =="B")//pedido por llegar
                {

                    cantidad=obtenerPedido(2,i,1);//el ultimo y a la vez el primero
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(2,i,2);
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                

                    validaPedido=1;
                }
                if (indicador =="BB")//pedido por llegar y pedido por llegar
                {
    ////                bgdCosUniP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed).toString()) , objParSis.getDecimalesMostrar() );
    ////                bgdMargenP=objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniNotPed+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniNotPed+1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniNotPed+1).toString()) , objParSis.getDecimalesMostrar() );

                    cantidad=obtenerPedido(3,i,1);//el primero
                    bgdCosUniP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(3,i,2);
                    bgdMargenP=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());                

                    cantidad=obtenerPedido(2,i,1);//el ultimo
                    bgdCosUniU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    cantidad=obtenerPedido(2,i,2);
                    bgdMargenU=objUti.redondearBigDecimal(objUti.parseString(cantidad),objParSis.getDecimalesMostrar());
                    validaPedido=2;
                }



                    if(validaPedido>=1)
                    {
                        /////////////////////////////////////////////////////////////chiqui////precio Primer
                                bgdPrecioSug = BigDecimal.ZERO;
                                bgdFacCosUni = BigDecimal.ZERO;
                                bgdPreVtaMarUtiPreVtaP = BigDecimal.ZERO;

    ////                            if ((bgdCosUniP==null || bgdMargenP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) || (bgdMargenP.compareTo(BigDecimal.ZERO)==0))
                                if ((bgdCosUniP==null) || (bgdCosUniP.compareTo(BigDecimal.ZERO)==0) )

                                {
                                    bgdPreVtaMarUtiPreVtaP=null;
        ////                            validaPreSu=false;

                                }
                                 else
                                 {
                                    bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargenP.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
    ////                                System.out.println("margen_" + bgdMargenP);
    ////                                System.out.println("divisor_" + bdeDivisor);
                                    if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                    {
                                        bdeDividendo=BigDecimal.ZERO;
                                        bgdFacCosUni=BigDecimal.ZERO;
                                        bgdFacCosUni=BigDecimal.ZERO;
        ////                                validaPreSu=false;
                                    }
                                    else
                                    {
                                        bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));  
                                        bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);


    ////                                    bgdFacCosUniGlo = objTblMod.(i,intNumColIniFac).toString();

    ////                                      bgdFacCosUniGlo = objUti.redondearBigDecimal(objTblMod.getValueAt(i,intNumColIniFac), 2);
                                          //bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPreReaMar+5)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString()) , objParSis.getDecimalesMostrar() );
                                          bgdFacCosUniGlo =objUti.redondearBigDecimal (objUti.parseString(factorN) , objParSis.getDecimalesMostrar() );

                                        bgdPreVtaMarUtiPreVtaP= bgdCosUniP.multiply(bgdFacCosUniGlo);
    ////                                    bgdPreVtaMarUtiPreVtaP= bgdCosUniP.multiply(bgdFacCosUni);


        ////                                validaPreSu=true;
                                    }

    ////
    ////                                System.out.println("diviso__" + bdeDivisor);
    ////                                System.out.println("dividendo_" + bdeDividendo);

    ////                                System.out.println("dividendo___" + bdeDividendo);
    ////                                System.out.println("bgdFacCosUni" + bgdFacCosUni);                            


                                 }
                                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaP , i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                                objTblMod.setValueAt(bgdMargenP , i, intNumColIniMarPrimero+1);//
    ////                            if (bgdPreVtaMarUtiPreVtaP >0)
    ////                            {
    ////                                
    ////                            }
                        ///////////////////////////////////////////

                    }
                    if(validaPedido==2)
                    {
        /////////////////////////////////////////////////////////////chiqui////precio Ultimo
                                bgdPrecioSug = BigDecimal.ZERO;
                                bgdFacCosUni = BigDecimal.ZERO;
                                bgdPreVtaMarUtiPreVtaU = BigDecimal.ZERO;

    ////                            if ((bgdCosUniU==null || bgdMargenU==null) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0) || (bgdMargenU.compareTo(BigDecimal.ZERO)==0))
                                if ((bgdCosUniU==null ) || (bgdCosUniU.compareTo(BigDecimal.ZERO)==0))                                
                                {
                                    bgdPreVtaMarUtiPreVtaU=null;
        ////                            validaPreSu=false;

                                }
                                 else
                                 {
                                    bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargenU.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
    ////                                System.out.println("margen_" + bgdMargenU);
    ////                                System.out.println("divisor_" + bdeDivisor);
                                    if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                                    {
                                        bdeDividendo=BigDecimal.ZERO;
                                        bgdFacCosUni=BigDecimal.ZERO;
                                        bgdFacCosUni=BigDecimal.ZERO;
        ////                                validaPreSu=false;
                                    }
                                    else
                                    {
                                        bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));  
                                        bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
    ////                                    bgdPreVtaMarUtiPreVtaU= bgdCosUniU.multiply(bgdFacCosUni);
                                         //bgdFacCosUniGlo =objUti.redondearBigDecimal (objTblMod.getValueAt(i, intNumColIniPreReaMar+5)==null?"0":(objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString()) , objParSis.getDecimalesMostrar() );
                                         bgdFacCosUniGlo =objUti.redondearBigDecimal (objUti.parseString(factorN) , objParSis.getDecimalesMostrar() );
                                        bgdPreVtaMarUtiPreVtaU= bgdCosUniU.multiply(bgdFacCosUniGlo);                                    
        ////                                validaPreSu=true;
                                    }


    ////                                System.out.println("diviso__" + bdeDivisor);
    ////                                System.out.println("dividendo_" + bdeDividendo);
    ////
    ////                                System.out.println("dividendo___" + bdeDividendo);
    ////                                System.out.println("bgdFacCosUni" + bgdFacCosUni);                            


                                 }


                                ///////////////////////
                                objTblMod.setValueAt(bgdPreVtaMarUtiPreVtaU , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
                                objTblMod.setValueAt(bgdMargenU , i, intNumColIniMarUltimo+1);//

                    }
                    else
                    {
    ////                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                        objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
    ////                    objTblMod.setValueAt(0, i, intNumColIniMarPrimero+1);//intNumColIniMarNotPed
                        objTblMod.setValueAt(0 , i, intNumColIniMarUltimo+1);//intNumColIniMarNotPed                     
                    }
    ////                  objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed 

                    if (validaPedido ==-1)
                    {
                        objTblMod.setValueAt(0, i, intNumColIniMarPrimero);//intNumColIniMarNotPed
                        objTblMod.setValueAt(0 , i, intNumColIniMarUltimo);//intNumColIniMarNotPed
                        objTblMod.setValueAt(0, i, intNumColIniMarPrimero+1);//intNumColIniMarNotPed
                        objTblMod.setValueAt(0 , i, intNumColIniMarUltimo+1);//intNumColIniMarNotPed                    
                    }              
            }            
              
            
        //}
        
        
        return blnRes;
    }
    
    
//// private boolean actualizarDatosZafGP(java.sql.Connection conn,java.sql.Connection conGP,String fecha,int filas)//chiqui
 private boolean actualizarDatosZafGP(String fecha,int filas)//chiqui    
    {
        boolean blnRes=true;
        boolean blnExisDet=false;
        java.sql.Statement stmLoc;
        java.sql.Statement stmGP;
        java.sql.ResultSet rstLoc1;
        java.sql.Connection conn;
        java.sql.Connection conGP;
        boolean validaModifica =false;
        int intNumFil=filas,i=0,numNoChe=0;
        int codGrp=-1,detGrp=-1;
        String strIndMod="";
        
        String strSQLGP="";
        String strSQLGPGlo="";
        String strSQLGlo="";
        boolean blnResGrpDet=false;
        String strCadItms="";
        try
        {
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                stmGP=conGP.createStatement();
                arlDatITEMDET= new ArrayList();
                arlDatGrpDet2= new ArrayList(); 
                List<Map<String, Object>> listaTablaCabecera = new ArrayList<>();
                for (i=0; i<intNumFil;i++)  
                {
                    detGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_DET)));
                    strIndMod=  objUti.parseString(objTblMod.getValueAt(i,intNumColIniModCabDet));
                    if(!strIndMod.equals("") && !strIndMod.equals("D"))
                            numNoChe =numNoChe+1;                         
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    if(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3) !=null )
                    {
                        if ((((objTblMod.isChecked(i,intNumColIniChk)) || (objTblMod.isChecked(i,intNumColIniChkPrimero)) || 
                              (objTblMod.isChecked(i,intNumColIniChkUltimo))|| (objTblMod.isChecked(i,intNumColIniChkOtro))|| 
                                (!strIndMod.equals("") && !strIndMod.equals("D"))
                             ) && (!strIndMod.equals("D")) ) 
                             && ((!strIndMod.equals("D1")) || (!strIndMod.equals("D2")) || (!strIndMod.equals("D3"))|| (!strIndMod.equals("D4")) 
                                 || (!strIndMod.equals("D5"))|| (!strIndMod.equals("D6"))|| (!strIndMod.equals("D7"))|| (!strIndMod.equals("D9")))  
                           ) 
                        {
                            //Verifica que el item:codigo alterno no este ingresado en la db para poder guardarlo
                            if(objParSis.getCodigoUsuario()==90)
                            {
                                strSQL ="";
                                strSQL+="\n SELECT tx_codAlt";
                                strSQL+="\n FROM tbm_inv";
                                strSQL+="\n WHERE tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase());
                                strSQL+="\n GROUP BY tx_codAlt";
                                rstLoc1=stmLoc.executeQuery(strSQL);
                                if(rstLoc1.next())
                                {
                                    mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                    return false;
                                } 
                            }
                            double ndPrecioVenta=0,ndmarUti=0,ndfasCosUni=0,ndcanMaxVen=0,ndAju=0,ndFacAju=0;
                            
                            BigDecimal precioVenta =BigDecimal.ZERO;
                            BigDecimal bgndmarUti =BigDecimal.ZERO;
                            BigDecimal bgndfasCosUni =BigDecimal.ZERO;
                            BigDecimal bgndAju =BigDecimal.ZERO;
                            BigDecimal bgndFacAju =BigDecimal.ZERO;

                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="\nUPDATE tbm_inv";
                            strSQL+="\n SET tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase(),1);
                            strSQL+="\n, tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                            strSQL+="\n, co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
                            for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
                                if(j==(intNumColIniPreReaMar+3))
                                {
                                        ndmarUti = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                        bgndmarUti=BigDecimal.valueOf(ndmarUti); 
//                                        strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                        strSQL+=", nd_marUti=" + bgndmarUti ;
                                }
                            } 
                            if (!blnExisDet)  
                            {
                                // <editor-fold defaultstate="collapsed" desc=" /* Cristian:consulta si sta activo algun check  */ ">
                    if (objTblMod.isChecked(i,intNumColIniChk)) 
                    {
                        for(int j=intNumColIniFac; j<intNumColFinFac; j++){
                            if(j==intNumColIniFac+1)
                            {
////                                        strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////                                        ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                {
                                    strSQL+=", nd_preVta1=0";
                                    ndPrecioVenta = 0;
                                }

                                else
                                {
                                     strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                     ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                }                                        
                                validaModifica=true;
                            }
                        }  
                    }
                    else
                    {
                        if (objTblMod.isChecked(i,intNumColIniChkPrimero)) 
                        {
                            for(int j=intNumColIniMarPrimero; j<intNumColFinMarPrimero; j++){
                                if(j==intNumColIniMarPrimero)
                                {
////                                            strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////                                            ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                    if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                    {
                                        strSQL+=", nd_preVta1=0";
                                        ndPrecioVenta = 0;
                                    }

                                    else
                                    {
                                         strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                         ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                    }                                            
                                    validaModifica=true;
                                }
                            }

                        }
                        else
                        {
                            if (objTblMod.isChecked(i,intNumColIniChkUltimo))
                            {
                                for(int j=intNumColIniMarUltimo; j<intNumColFinMarUltimo; j++){
                                    if(j==intNumColIniMarUltimo)
                                    {
////                                                strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////                                                ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                            if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                            {
                                                strSQL+=", nd_preVta1=0";
                                                ndPrecioVenta = 0;
                                            }

                                            else
                                            {
                                                 strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                 ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                            }                                                
                                        validaModifica=true;
                                    }
                                }                                        
                            }
                            else
                            {
                                if(objTblMod.isChecked(i,intNumColIniChkOtro))
                                {
                                    for(int j=intNumColIniPre; j<intNumColFinPre; j++){
                                        if(j==intNumColFinPre-1)
                                        {
////                                                    strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////                                                    ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                            if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                            {
                                                strSQL+=", nd_preVta1=0";
                                                ndPrecioVenta = 0;
                                            }

                                            else
                                            {
                                                 strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                 ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                            }                                                    
                                            validaModifica=true;
                                        }
                                    } 
                                }
                                else
                                {
                                   if(objTblMod.isChecked(i,intNumColIniChkPrimeraImporta))
                                   {
                                        for(int j=intNumColIniMarPrimeraImporta; j<intNumColFinMarPrimeraImporta; j++){
                                            if(j==intNumColFinMarPrimeraImporta-2)
                                            {
////                                                    strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////                                                    ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                                {
                                                    strSQL+=", nd_preVta1=0";
                                                    ndPrecioVenta = 0;
                                                }

                                                else
                                                {
                                                     strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                     ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                }                                                    
                                                validaModifica=true;
                                            }
                                        }                                                
                                   }
                                   else
                                   {
                                       if(objTblMod.isChecked(i,intNumColIniChkUltimaImporta))
                                       {
                                            for(int j=intNumColIniMarUltimaImporta; j<intNumColFinMarUltimaImporta; j++){
                                                if(j==intNumColFinMarUltimaImporta-2)
                                                {
////                                                        strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                    if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                                    {
                                                        strSQL+=", nd_preVta1=0";
                                                        ndPrecioVenta = 0;
                                                    }

                                                    else
                                                    {
                                                         strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                         ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                    }

////                                                        ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                    validaModifica=true;
                                                }
                                            }                                                
                                       }
                                       else
                                       {
                                           if(objTblMod.isChecked(i,intNumColIniChkFactor))
                                           {
                                                for(int j=intNumColIniFactorPor; j<intNumColFinFactorPor; j++){
                                                    if(j==intNumColFinFactorPor-1)
                                                    {
////                                                        strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                        if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                                        {
                                                            strSQL+=", nd_preVta1=0";
                                                            ndPrecioVenta = 0;
                                                        }

                                                        else
                                                        {
                                                             strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                             ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                        }

////                                                        ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                        validaModifica=true;
                                                    }
                                                }  
                                           }
                                           else
                                           {
                                           ///este es el que no tiene check                                                       
                                                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
                                                    if(j==intNumColIniPreReaMar)
                                                    {
                                                        if (objUti.codificar(objTblMod.getValueAt(i,j),2).equals("") || (objUti.codificar(objTblMod.getValueAt(i,j),2).toString()== "Null" ))
                                                        {
                                                            strSQL+=", nd_preVta1=0";
                                                            ndPrecioVenta = 0;
                                                        }

                                                        else
                                                        {
                                                             strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                                             ndPrecioVenta = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                                        }
////                                                            strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);

                                                        validaModifica=true;
                                                    }
        ////                                            if(j==(intNumColIniPreReaMar+3))
        ////                                            {
        ////                                                ndmarUti = objUti.parseDouble(objTblMod.getValueAt(i,j));
        ////                                                strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
        ////                                            }
                                                }                                                       
                                           }



                                        ///////////////////
                                       }
                                   }



                                }

                            }
                        }
                    }   
                        //</editor-fold>                                     
                            }
                            for(int j=intNumColIniFac; j<intNumColFinFac; j++)
                            {
                                if(j==intNumColIniFac)
                                {
                                            ndfasCosUni= objUti.parseDouble(objTblMod.getValueAt(i,j));
                                            bgndfasCosUni=BigDecimal.valueOf(ndfasCosUni); 
                                            //strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                            strSQL+=", nd_facCosUni=" + bgndfasCosUni ;
                                            ndfasCosUni = objUti.parseDouble(objTblMod.getValueAt(i,j));                                        
                                }
                            }
                             if (!blnExisDet) 
                             {
                                for(int j=intNumColIniPre; j<intNumColFinPre; j++)
                                {
                                    if(j==intNumColIniPre)
                                            strSQL+=", nd_preVtaObjKgr=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                }                                 
                             }   
                            for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew; j++)
                            {
                                if(j==intNumColIniCanMarPreLisNew)
                                {
                                      strSQL+=", nd_canMaxVen=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                      ndcanMaxVen = objUti.parseDouble(objTblMod.getValueAt(i,j));  
                                }
                            }
                            strSQL+="\n , nd_pesItmKgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_KGR),2);
                            strSQL+="\n , fe_ultMod='" + fecha + "'";
                            strSQL+="\n , co_usrMod=" + objParSis.getCodigoUsuario();
                            strSQL+="\n FROM (  ";
                            strSQL+="\n       SELECT a1.co_emp, a2.co_itm ";
                            strSQL+="\n       FROM tbm_inv AS a1 ";
                            strSQL+="\n       INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) ";
                            strSQL+="\n       WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
                            strSQL+=" \n ) AS b1";
                            strSQL+=" \n WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm; ";
                            Date date = new Date();
                            System.out.println("actualizarDet.TbmInv_ZAFFIRO: " + dateFormat.format(date));
                            //////////////////////////////////////////////////////

                            strSQLGP="";
                            strSQLGP+=" \n UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101";
                            strSQLGP+=" \n SET ITEMDESC=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                            strSQLGP+=" \n , DEX_ROW_TS='" + fecha +"' \n";
                            strSQLGP+=" \n  WHERE ITEMNMBR="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3),1) +"";
                            strSQLGP+="; ";
                            
                            strCadItms = objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString();
                            if (cadItms.length() > 0) cadItms.append(',');
                            cadItms.append(strCadItms); 
                            
                            precioVenta=BigDecimal.valueOf(ndPrecioVenta); 
                            precioVenta =precioVenta.setScale(5, RoundingMode.CEILING);
                            if (!blnExisDet) 
                            {
                                strSQLGP+=" \n UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00108";
                                strSQLGP+=" \n  SET UOMPRICE= " + precioVenta +"";
                                strSQLGP+=" \n  WHERE ITEMNMBR="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString().toUpperCase(),1) +"";  
                                strSQLGP+=" \n  AND  PRCLEVEL='GENERAL'";                        
                                strSQLGP+=";";                                 
                            }
                            
                            ndAju = objUti.parseDouble(objTblMod.getValueAt(i,intNumColIniPreReaMar+5));
                            bgndAju=BigDecimal.valueOf(ndAju); 
                            
                            ndFacAju = objUti.parseDouble(objTblMod.getValueAt(i,intNumColIniPreReaMar+6));
                            bgndFacAju=BigDecimal.valueOf(ndFacAju);                             
                                        
                            strSQLGP+=" \n UPDATE tbm_inv";
                            strSQLGP+=" \n  SET tx_nomitm= " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1) +"";
                            strSQLGP+=" \n  , nd_marUti= " + bgndmarUti +"";
                            strSQLGP+=" \n  , nd_canMaxVen= " + ndcanMaxVen +"";
                            strSQLGP+=" \n  , nd_facCosUni= " + bgndfasCosUni +"";
                            strSQLGP+=" \n  , nd_eje= " + bgndAju +"";
                            strSQLGP+=" \n  , nd_FacAju= " + bgndFacAju +"";
                            
                            if (!blnExisDet) 
                                strSQLGP+=" \n  , nd_preVta1= " + precioVenta +"";                                

                            strSQLGP+=" \n  , fe_ultmod= '" + fecha +"'";
                            strSQLGP+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strSQLGP+=" \n  WHERE tx_itm="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString().toUpperCase(),1) +"";  
                            strSQLGP+=";";  
                            System.out.println("*****codigo maestro: "+ i +"");
                            strSQLGlo =  strSQLGlo + strSQL;
                            strSQLGPGlo =  strSQLGPGlo + strSQLGP;
                            
                            Map<String, Object> item = new HashMap<>();
                            item.put("tx_codAlt2",objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString());
                            item.put("nd_preUni", precioVenta);
                            item.put("tx_obs1", "Modificado desde Actualizacion Precios Sugeridos");
                            item.put("co_usrIng", objParSis.getCodigoUsuario());
                            listaTablaCabecera.add(item);
                            
                            codGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_GRP)));
                            if (codGrp!=0)
                            {
                                arlRegGrpDet2 = new ArrayList();
                                arlRegGrpDet2.add(INT_ARL_GRP_DET_COD,codGrp);                    
                                arlRegGrpDet2.add(INT_ARL_GRP_DET_ITEM,"item");                    
                                arlRegGrpDet2.add(INT_ARL_GRP_DET_PRE,precioVenta);
                                arlRegGrpDet2.add(INT_ARL_GRP_TIP,"C");                                        
                                arlDatGrpDet2.add(arlRegGrpDet2); 
                            }
                            Date date1 = new Date();
                            System.out.println("actualizarDet.TbmInv_GP: " + dateFormat.format(date1));
                        }
                   }
                }
                if (arlDatGrpDet2.size() >0)
                {
                    jsonTablaCabecera = crearJsonItems(listaTablaCabecera);
                    
                    if (ActualizarDetalleGrupoGlobal(fecha))
                        blnResGrpDet =true;
                    else
                       blnResGrpDet =false;
                }
                if ((numNoChe !=intNumFil)   && (arlDatGrpDet2.size() >0))
                {
                    if (blnResGrpDet)
                    {
                            stmGP.executeUpdate(strSQLGPGlo);
                            stmLoc.executeUpdate(strSQLGlo);   
////                            if(objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + fecha + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()))
////                            {
                                if(objHisTblBasDatGP.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + fecha + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))                                
                                     blnRes=true;
                                else
                                     blnRes=false;
////                            }
////                            else
////                                 blnRes=false;                                 
                    }
                    else
                         blnRes=false;                                 
                }
                else
                {
//////////                    if (numNoChe !=intNumFil)
//////////                    {
                            stmGP.executeUpdate(strSQLGPGlo);
                            stmLoc.executeUpdate(strSQLGlo);                            
////////////                    }
////////////                    else
////////////                    {
////////////                         blnRes=false; 
////////////                         mostrarMsgInf("<HTML>No se puede generar el proceso.<BR>No se a modicado ningun registro y vuelva a seleccionar.</HTML>");                            
////////////                    }
                }
                if(blnExisDet)
                {
                    int estadoPre ;
                    String strMsgItmUbi="";
                     strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items con respecto a los precios de venta: <BR><BR>";
                     strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> DESCRIPCION </td><td> FACTOR </td>";
                     for (i=0; i<arlDatITEMDET.size();i++) 
                    { 

                            strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_ITEM) + "</td>";
                            strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_DESC) + "</td>";
                            strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_FAC) + "</td>";
                            strMsgItmUbi+="</tr>";
                    }
                    strMsgItmUbi+="</table><BR>";
                    strMsgItmUbi+="Se encuentran agrupados, por lo tanto no se pueden modificar de forma individual. <html>";
                    mostrarMsgInf(strMsgItmUbi);
                };
                pgrSis.setIndeterminate(false);
                //Armar la sentencia SQL.

                stmLoc.close();
                stmLoc=null;
                stmGP.close();
                stmGP=null;
                conn.close();
                conGP.close();
                conn=null;
                conGP=null;                               
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
  private boolean actualizarDatosZafDetalle(String fecha,int filas)//chiqui    
    {
        boolean blnRes=true;
        boolean blnExisDet=false;
        java.sql.Statement stmLoc;
        java.sql.Statement stmGP;
        java.sql.ResultSet rstLoc1;
        java.sql.Connection conn;
        java.sql.Connection conGP;
        boolean validaModifica =false;
        int intNumFil=filas,i=0,numNoChe=0;
        int codGrp=-1,detGrp=-1;
        String strIndMod="";
        
        String strSQLGP="";
        String strSQLGPGlo="";
        String strSQLGlo="";
        boolean blnResGrpDet=false;
        String strCadItms="";
        String strLinMod="";
        try
        {
            conn=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                stmGP=conGP.createStatement();
                arlDatITEMDET= new ArrayList();
                arlDatGrpDet2= new ArrayList(); 
                
                List<Map<String, Object>> listaTablaMargen = new ArrayList<>();
                for (i=0; i<intNumFil;i++)
                {
                    detGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_DET)));
                    codGrp=  Integer.parseInt(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_GRP)));
                    strLinMod=objTblMod.getValueAt(i, intNumColIniModOtr)==null?"":objTblMod.getValueAt(i, intNumColIniModOtr).toString();

                    //if((objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3) !=null ) &&  (strLinMod.equals("M")) && ((detGrp==0 && codGrp==0)||(detGrp!=0)))
                    if((objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3) !=null ) &&  (strLinMod.equals("M")) )
                    {
                            //Verifica que el item:codigo alterno no este ingresado en la db para poder guardarlo
                            if(objParSis.getCodigoUsuario()==90)
                            {
                                strSQL ="";
                                strSQL+="\n SELECT tx_codAlt";
                                strSQL+="\n FROM tbm_inv";
                                strSQL+="\n WHERE tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase());
                                strSQL+="\n GROUP BY tx_codAlt";
                                rstLoc1=stmLoc.executeQuery(strSQL);
                                if(rstLoc1.next())
                                {
                                    mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                    return false;
                                } 
                            }
                            double ndmarUti=0;
                            BigDecimal bgndmarUti =BigDecimal.ZERO;

                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="\nUPDATE tbm_inv";
                            strSQL+="\n SET tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase(),1);
                            strSQL+="\n, tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                            //strSQL+="\n, co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
                            for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
                                if(j==(intNumColIniPreReaMar+3))
                                {
                                        ndmarUti = objUti.parseDouble(objTblMod.getValueAt(i,j));
                                        bgndmarUti=BigDecimal.valueOf(ndmarUti); 
//                                        strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                                        strSQL+=", nd_marUti=" + bgndmarUti ;
                                }
                            } 
                            
                            
                            Map<String, Object> item = new HashMap<>();
                            item.put("tx_codAlt2",objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_DOS).toString());
                            item.put("nd_marUti", bgndmarUti);
                            item.put("tx_obs1", "Modificado desde Actualizacion Precios Sugeridos");
                            item.put("co_usrIng", objParSis.getCodigoUsuario());
                            listaTablaMargen.add(item);
                    
                    
                            
   
////////////                            for(int j=intNumColIniFac; j<intNumColFinFac; j++)
////////////                            {
////////////                                if(j==intNumColIniFac)
////////////                                {
////////////                                            ndfasCosUni= objUti.parseDouble(objTblMod.getValueAt(i,j));
////////////                                            bgndfasCosUni=BigDecimal.valueOf(ndfasCosUni); 
////////////                                            //strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
////////////                                            strSQL+=", nd_facCosUni=" + bgndfasCosUni ;
////////////                                            ndfasCosUni = objUti.parseDouble(objTblMod.getValueAt(i,j));                                        
////////////                                }
////////////                            }
 
                            strSQL+="\n , fe_ultMod='" + fecha + "'";
                            strSQL+="\n , co_usrMod=" + objParSis.getCodigoUsuario();
                            strSQL+="\n FROM (  ";
                            strSQL+="\n       SELECT a1.co_emp, a2.co_itm ";
                            strSQL+="\n       FROM tbm_inv AS a1 ";
                            strSQL+="\n       INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) ";
                            strSQL+="\n       WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
                            strSQL+=" \n ) AS b1";
                            strSQL+=" \n WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm; ";
                            //////////////////////////////////////////////////////

                            strSQLGP="";
                            strSQLGP+=" \n UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101";
                            strSQLGP+=" \n SET ITEMDESC=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                            strSQLGP+=" \n , DEX_ROW_TS='" + fecha +"' \n";
                            strSQLGP+=" \n  WHERE ITEMNMBR="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3),1) +"";
                            strSQLGP+="; ";
                            
                            strSQLGP+=" \n UPDATE tbm_inv";
                            strSQLGP+=" \n  SET tx_nomitm= " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1) +"";
                            strSQLGP+=" \n  , nd_marUti= " + bgndmarUti +"";
                            strSQLGP+=" \n  , fe_ultmod= '" + fecha +"'";
                            strSQLGP+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strSQLGP+=" \n  WHERE tx_itm="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString().toUpperCase(),1) +"";  
                            strSQLGP+=";";  
                            System.out.println("*****codigo maestro: "+ i +"");
                            strSQLGlo =  strSQLGlo + strSQL;
                            strSQLGPGlo =  strSQLGPGlo + strSQLGP;
                            blnRes=true;
                          
                   }
                }
                if (blnRes)
                {
                    
                    stmGP.executeUpdate(strSQLGPGlo);
                    stmLoc.executeUpdate(strSQLGlo);
                    jsonTablaMargen = crearJsonItems(listaTablaMargen);
////                    if(objHisTblBasDat.insertarHistoricoMasivo(conn, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + fecha + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()))
////                    {
                        if(objHisTblBasDatGP.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + fecha + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))                                
                             blnRes=true;
                        else
                             blnRes=false;
////                    }
////                    else
////                         blnRes=false;                    
                }
                else
                    mostrarMsgInf("<HTML>No se puede generar el proceso.<BR>No se a modicado ningun registro y vuelva a seleccionar.</HTML>");                     



                pgrSis.setIndeterminate(false);
                //Armar la sentencia SQL.

                stmLoc.close();
                stmLoc=null;
                stmGP.close();
                stmGP=null;
                conn.close();
                conGP.close();
                conn=null;
                conGP=null;                               
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 private boolean ActualizarDetalleGrupo(int idGrupo,BigDecimal precio, String fecha)
 {
     boolean blnRes=false;
     java.sql.Connection conGP;
     java.sql.Connection conZAF;
        try
        {
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            conGP.setAutoCommit(false);
            conZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            conZAF.setAutoCommit(false);            
            if(ActualizaRegistro(conGP,conZAF,idGrupo,precio,fecha))
            {
////                objHisTblBasDat.insertarHistoricoMasivo(conZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + fecha + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
////                 if(InsertarHistorico(conGP,fecha,objParSis.getNombreUsuario()))
////                 {
                    conZAF.commit();
                    conGP.commit(); 
                    blnRes=true;                    
////                 }
////                 else
////                 {
////                    conGP.rollback();
////                    conZAF.rollback();  
////                    blnRes=false;                    
////                 }
            }
            else
            {
                conGP.rollback();
                conZAF.rollback();                
                blnRes=false;
            }
            conZAF.close();
            conZAF =null;
            conGP.close();
            conGP =null;
            
        } catch (java.sql.SQLException e) { 
            blnRes=false;
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           blnRes=false;
           objUti.mostrarMsgErr_F1(this, Evt);
       }     
     

     return blnRes;
 }
 
 
 private boolean ActualizarDetalleGrupoGlobal(String fecha)
 {
     boolean blnRes=false;
     java.sql.Connection conGP;
     java.sql.Connection conZAF;
     int idGrupo =0;
     double precio=0;
     String strFecUltMod="";
     BigDecimal precioVenta =BigDecimal.ZERO;
        try
        {
            //strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            conGP.setAutoCommit(false);
            conZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());            
            conZAF.setAutoCommit(false);  
             for (int j = 0; j < arlDatGrpDet2.size(); j++) 
             {
                idGrupo=objUti.getIntValueAt(arlDatGrpDet2, j, INT_ARL_GRP_DET_COD);
                precio=objUti.getDoubleValueAt(arlDatGrpDet2, j, INT_ARL_GRP_DET_PRE);
                precioVenta=BigDecimal.valueOf(precio); 
                
                if(ActualizaRegistroGlobal(conGP,conZAF,idGrupo,precioVenta,fecha))
                {
                    conZAF.commit();
                    conGP.commit(); 
                    blnRes=true;                    
                }
                else
                {
                    conGP.rollback();
                    conZAF.rollback();                
                    blnRes=false;
                }


             }
                 
                 
                 

            conZAF.close();
            conZAF =null;
            conGP.close();
            conGP =null;
            
        } catch (java.sql.SQLException e) { 
            blnRes=false;
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           blnRes=false;
           objUti.mostrarMsgErr_F1(this, Evt);
       }     
     

     return blnRes;
 }


                    
                    
 
 private boolean ActualizaRegistro(java.sql.Connection conExt,java.sql.Connection conExtZAF,int codGrupo,BigDecimal precio, String fecha)
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
                strSql1+=" WHERE cab.co_tipAgr =1 and cab.co_grpInv ="+ codGrupo +" and fac.co_facModPre !=0 and det.co_reg>0  and pre.PRCLEVEL ='GENERAL' AND iv01.inactive=0   \n";
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
 
 String jsonTablaDetalle ="";
 String jsonTablaCabecera ="";
 String jsonTablaMargen ="";
 private boolean ActualizaRegistroGlobal(java.sql.Connection conExt,java.sql.Connection conExtZAF,int codGrupo,BigDecimal precio, String fecha)
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
                strSql1+=" WHERE cab.co_tipAgr =1 and cab.co_grpInv ="+ codGrupo +" and fac.co_facModPre !=0 and det.co_reg>0  and pre.PRCLEVEL ='GENERAL' AND iv01.inactive=0   \n";
                rstLocGP=stmLocGP.executeQuery(strSql1);
                strSQLGPD="";
                strZAF=""; 
                List<Map<String, Object>> listaTablaDetalle = new ArrayList<>();
                while(rstLocGP.next())
                {
                    Map<String, Object> item = new HashMap<>();
                    item.put("tx_codAlt2",rstLocGP.getString("tresL"));
                    item.put("nd_preUni", rstLocGP.getDouble("preNuevo"));
                    item.put("tx_obs1", "Modificado desde Actualizacion Precios Sugeridos");
                    item.put("co_usrIng", objParSis.getCodigoUsuario());
                    listaTablaDetalle.add(item);
                    
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
                //Generar JSON
                 jsonTablaDetalle = crearJsonItems(listaTablaDetalle);
                
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
 
public static String crearJsonItems(List<Map<String, Object>> listaItems) {
    JSONArray jsonArray = new JSONArray();
    for (Map<String, Object> item : listaItems) {
        jsonArray.put(new JSONObject(item));
    }
    JSONObject jsonFinal = new JSONObject();
    jsonFinal.put("items", jsonArray);
    return jsonFinal.toString(2); 
} 
 
        
    /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        java.sql.Statement stmGP; 
        java.sql.Connection conGP;
        java.sql.ResultSet rstGP;  
        boolean blnRes=true,validaSeleccion=false,validaModifica=false;
        String strFecUltMod,strSQLGP="";
        int intNumFil, i;
        String jsonFinal="";
  
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                
                
                 pgrSis.setIndeterminate(true);
                 lblMsgSis.setText("Guardando...");
                
                stm=con.createStatement();
                stmGP=conGP.createStatement();
////                conGP.setAutoCommit(false);
////                con.setAutoCommit(false);
                
                
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
                if (validaSeleccion==false)
                {
                    if (actualizarDatosZafGP(strFecUltMod,intNumFil))                    
                    {
                         if (actualizarDatosZafDetalle(strFecUltMod,intNumFil)) 
                         {
                             jsonFinal=unificarJsons(jsonTablaDetalle,jsonTablaCabecera,jsonTablaMargen);
                            if (!jsonFinal.equals(""))
                            {
                                objKon.ejecuSPActuaItmKonJson(jsonFinal);
                                blnRes=true;  
                            }
                            else
                            {
                                blnRes=false;
                                mostrarMsgInf("<HTML>No se puede generar el proceso:KON.<BR>Verifique y vuelva a seleccionar.</HTML>");                                
                                
                            }
                         }
                         else
                         {
                            blnRes=false;
                            mostrarMsgInf("<HTML>No se puede generar el proceso:Detalle<BR>Verifique y vuelva a seleccionar.</HTML>");                              
                         }
                             
                    }
                    else
                    {
                        blnRes=false;
                        mostrarMsgInf("<HTML>No se puede generar el proceso.<BR>Verifique y vuelva a seleccionar.</HTML>");
                    }
                }
                else
                {
                    blnRes=false;
                    mostrarMsgInf("<HTML>No se puede seleccionar mas de1 Precio de Venta Sugerido en una mismo item.<BR>Verifique y vuelva a seleccionar.</HTML>");
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                
                stmGP.close();
                conGP.close();
                stmGP=null;
                conGP=null;    
                
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
            }
            pgrSis.setIndeterminate(false);
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private String unificarJsons(String json1, String json2, String json3) {
        JSONArray itemsUnificados = new JSONArray();

        // Lista de JSONs recibidos
        String[] jsons = {json1, json2, json3};

        for (String jsonStr : jsons) {
            if (jsonStr != null && !jsonStr.trim().isEmpty()) {
                JSONObject obj = new JSONObject(jsonStr);
                if (obj.has("items")) {
                    JSONArray items = obj.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        itemsUnificados.put(items.getJSONObject(i));
                    }
                }
            }
        }

        // Crear el JSON final
        JSONObject jsonFinal = new JSONObject();
        jsonFinal.put("items", itemsUnificados);

        return jsonFinal.toString(2); // devuelve el JSON con formato bonito
    }

    
    
    private boolean EjecucionFluctuaciones(java.sql.Connection conExt, String items)//chiqui
    {
         boolean blnRes = true;
        java.sql.Connection con;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strClaveOT ="",strSql ="",strTipoOT="";
        String registroGP ="";
        int intCodRes=-1;
        try{
            con = conExt;
            if(con!=null){
                stmLoc = con.createStatement();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call spVen24(?,?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("ITEMS",  items);//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    
    private boolean InsertarHistorico (java.sql.Connection conEXT,String fecha,String usuario)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="",strSQLHist="";
        try
        {
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                    strSQLHist="";
                    
                    strSQLHist+=" INSERT INTO tbh_inv\n" +
"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk,tx_obs1,tx_obs2,nd_proAnuVenMan,nd_stkBas,nd_tra,nd_fre,nd_punParMin,nd_punParMax,nd_moq,nd_exc,nd_proVenTeo,st_reg,nd_porIvaVen,co_grpinv,co_detGrpInv ) \n";
                    strSQLHist+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm, a1.nd_marUti,nd_canMaxVen,a1.nd_facCosUni,a1.tx_usrmod, a1.fe_ultmod,a1.tx_codalt,"
                              + "a1.tx_nomitm,a1.tx_codalt2,a1.nd_preVta1,a1.co_grpimp,a1.tx_nomitming,a1.st_revtec,a1.st_itmstk    \n"
                              + "a1.,tx_obs1,a1.tx_obs2,a1.nd_proAnuVenMan,a1.nd_stkBas,a1.nd_tra,a1.nd_fre,a1.nd_punParMin,a1.nd_punParMax,a1.nd_moq,a1.nd_exc,a1.nd_proVenTeo,"
                              + "a1.st_reg,a1.nd_porIvaVen,a1.co_grpinv,a1.co_detGrpInv   \n";
                    strSQLHist+=" FROM tbm_inv AS a1  \n";
                    strSQLHist+=" left join (  \n";
                    strSQLHist+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
                    strSQLHist+=" group by tx_itm \n";
                    strSQLHist+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQLHist+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                
                    System.out.println("UPDATE " + strSQLHist);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQLHist);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
////    private boolean ProcesoUpdateGP(java.sql.Connection  conExt,String fecha)//chiqui
////    {
////        java.sql.Statement stmGP;
////////        java.sql.Connection conGP;
////        java.sql.ResultSet rstGP;  
////        java.sql.CallableStatement cal;
////        String strSql ="";
////        int intCodRes=-1;
////        boolean blnRes=true;
//// 
////        
////        try
////        {
////           if (conExt!=null)
////           {
//////           
////                stmGP=conExt.createStatement();
////                strSQL ="";
////                strSQL+="UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101";
////                strSQL+=" SET ITEMDESC= " + descripcion +" , CURRCOST = "+ costo +"";
////////                        strSQL+=", CURRCOST=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
////                strSQL+=" WHERE ITEMNMBR='"+ item +"'";
////                strSQL+=";";
////                
////                strSQL+="UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00108";
////                strSQL+=" SET UOMPRICE= " + precio +"";
////                strSQL+=" WHERE ITEMNMBR='"+ item +"'";  
////                strSQL+=" AND  PRCLEVEL='GENERAL'";                        
////                strSQL+=";";
////
////                System.out.println("UPDATE " + strSQL);
////                stmGP.executeUpdate(strSQL);
////                stmGP.close();
////                stmGP=null;
////
////            }
////           else
////               blnRes=false;
////                    
////          }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
    
     //<editor-fold defaultstate="collapsed" desc="/* Inserción de histórico transaccionado */">
//    /**
//     * Esta función permite actualizar los registros del detalle.
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean actualizarDet()
//    {
//        boolean blnRes=true;
//        String strFecUltMod;
//        try
//        {
//            //Obtener la fecha del servidor.
//            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//            if (datFecAux==null)
//                return false;
//            strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null)
//            {
//                //Actualiza el inventario
//                if(actualizaTbmInv(con, strFecUltMod)){
//                    //Insertar el histórico.
//                    if(objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) {
//                        blnRes=true; 
//                        con.commit();
//                    }
//                    else{ 
//                        con.rollback(); 
//                        blnRes=false; System.err.println("Error en insertarHistoricoMasivo");  
//                    }
//                }
//                else{  
//                    con.rollback(); 
//                    blnRes=false; System.err.println("Error en actualizaTbmInv");  
//                }
//         
//                con.close();
//                con=null;
//                datFecAux=null;
//                //Inicializo el estado de las filas.
//                objTblMod.initRowsState();
//                objTblMod.setDataModelChanged(false);
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
//        
//    /**
//     * Esta función permite actualizar los registros en la tabla tbm_inv
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean actualizaTbmInv(Connection conn, String strFecUltMod)
//    {
//        boolean blnRes=true;
//        int intNumFil, i;
//        try
//        {
//            if (conn!=null)
//            {
//                stm=conn.createStatement();
//                intNumFil=objTblMod.getRowCountTrue();
//                for (i=0; i<intNumFil;i++)
//                {
//                    if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
//                    {
//                        //System.out.println("*****codigo maestro: " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE));
//                        //verifica que el item:codigo alterno no este ingresado en la db para poder guardarlo
//                        if(objParSis.getCodigoUsuario()==90){
//                            strSQL ="";
//                            strSQL+=" SELECT tx_codAlt";
//                            strSQL+=" FROM tbm_inv";
//                            strSQL+=" WHERE tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase());
//                            strSQL+=" GROUP BY tx_codAlt";
//                            System.out.println("strSQL: " + strSQL);
//                            rst=stm.executeQuery(strSQL);
//                            if(rst.next()){
//                                mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                                return false;
//                            } 
//                        }
//                        //Armar la sentencia SQL.
//                        strSQL="";
//                        strSQL+=" UPDATE tbm_inv";
//                        strSQL+=" SET tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase(),1);
//                        strSQL+=", tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
//                        strSQL+=", co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
//                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
//                            if(j==intNumColIniPreReaMar)
//                                strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                            if(j==(intNumColIniPreReaMar+3))
//                                strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniFac; j<intNumColFinFac; j++){
//                            if(j==intNumColIniFac)
//                                strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniPre; j<intNumColFinPre; j++){
//                            if(j==intNumColIniPre)
//                                strSQL+=", nd_preVtaObjKgr=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew; j++){
//                            if(j==intNumColIniCanMarPreLisNew)
//                                strSQL+=", nd_canMaxVen=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        strSQL+=", nd_pesItmKgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_KGR),2);
//                        strSQL+=", fe_ultMod='" + strFecUltMod + "'";
//                        strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
//                        strSQL+=" FROM (";
//                        strSQL+="       SELECT a1.co_emp, a2.co_itm";
//                        strSQL+="       FROM tbm_inv AS a1";
//                        strSQL+="       INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
//                        strSQL+="       WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
//                        strSQL+=" ) AS b1";
//                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
//                        System.out.println("actualizaTbmInv: " + strSQL);
//                        stm.executeUpdate(strSQL);
//                    }
//                }    
//                stm.close();           
//                stm=null;
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    //</editor-fold>
        
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */ 
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
////            strSQL="";
////            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
////            strSQL+=" FROM tbm_inv AS a1";
////            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
////            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
////            strSQL+=" AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.tx_codAlt";
            strSQL="";
            strSQL+="SELECT trim(a1.ITEMNMBR) as co_itm, trim(a1.ITEMNMBR) as tx_codAlt, trim(a1.ITEMDESC) as tx_nomItm, trim(a1.UOMSCHDL) as tx_desCor";
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1";
////            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE (a1.INACTIVE = 0) ";
////            strSQL+=" AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar la "Unidad de medida".
     */
    private boolean configurarVenConUniMed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Siglas");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_reg, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_var AS a1";
            strSQL+=" WHERE a1.co_grp=5 AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_reg";
            vcoUniMed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de unidades de medida", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUniMed.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoUniMed.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    

    
    
    /**
     * Esta función calcula el margen de utilidad sobre el costo de la fila.
     */
    private void calcularMarUtiCos()
    {
        int intFilSel[], i;
        BigDecimal bgdPreVta, bgdCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdPreVta=(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar))));
            bgdCosUni=(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni))));
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
            {
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+2));
            }
            else
            {
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                {
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+2));
                }
                else
                {
                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniPreReaMar+2));
                }
            }
        }
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        
        public void run()
        {
            //Agregado por Eddye el 15/Sep/2022:
            Cadenaitems="";             
            butCon.setText("Detener");
            lblMsgSis.setText("Configurando columnas dinámicas...");
            if  (!optTod.isSelected())
            {
                if (!chkMosAgrup.isSelected())
                {
                    Cadenaitems=obtenerConexionSQL();
                    validaTodo=false;                    
                }
                else
                {
                    Cadenaitems=obtenerConexionSQL();
                    Cadenaitems = obtenerConexionSQLGRP(Cadenaitems);
                    validaTodo=false;                     
                }    
                    

            }
            else
            {
                validaTodo=true;
                if (!chkMosAgrup.isSelected())
                {
                    Cadenaitems=obtenerConexionSQL();
                    validaTodo=false;                    
                }
                else
                {
                    Cadenaitems=obtenerConexionSQL();
                    Cadenaitems = obtenerConexionSQLGRP(Cadenaitems);
                    validaTodo=false;                     
                }                 
            }
                
            if (!Cadenaitems.equals(""))
            {
                
                if(configurarColumnasAdicionar())
                {
                        //ocultaColumnasPorFiltro();
                    ocultarColumVarias();

                        if (!cargarDetReg(Cadenaitems))
                            {
                                //Inicializar objetos si no se pudo cargar los datos.
                                lblMsgSis.setText("Listo");
                                pgrSis.setValue(0);
                                butCon.setText("Consultar");
                            }
                            //Establecer el foco en el JTable sólo cuando haya datos.
                            if (tblDat.getRowCount()>0)
                            {
                                tabFrm.setSelectedIndex(1);
                                tblDat.setRowSelectionInterval(0, 0);
                                tblDat.requestFocus();
                            }
                            objThrGUI=null;
                }                
                
            }
            else
            {
                butCon.setText("Consultar");
                lblMsgSis.setText("No hay items que consultar..."); 
                pgrSis.setValue(0);
                butCon.setText("Consultar"); 
                pgrSis.setIndeterminate(false);
                objThrGUI=null;
            }

            

            
        }
    }
    
    private void ocultarColumVarias()
    {
        boolean blnAct =    chkMosActual.isSelected();  // guardo el estado
        boolean blnPriImp = chkMosPrimera.isSelected();  // guardo el estado
        boolean blnUltImp = chkMosUltima.isSelected();  // guardo el estado
        boolean blnUltIng = chkMosUltimoIngre.isSelected();  // guardo el estado
        boolean blnPenIng = chkMosPrimerIngre.isSelected();  // guardo el estado
        boolean blnAju = chkMosAju.isSelected();  // guardo el estado
        boolean blnAdi = chkMosAdi.isSelected();  // guardo el estado
        boolean blnPor = chkMosPor.isSelected();  // guardo el estado        
        
        chkMosActual.doClick();                              // lanza el evento y cambia estado
        chkMosPrimera.doClick();                              // lanza el evento y cambia estado
        chkMosUltima.doClick();                              // lanza el evento y cambia estado    
        chkMosUltimoIngre.doClick();
        chkMosPrimerIngre.doClick();
        chkMosAju.doClick();                              // lanza el evento y cambia estado
        chkMosAdi.doClick();                              // lanza el evento y cambia estado    
        chkMosPor.doClick(); 
        
        chkMosActual.setSelected(blnAct);            // restauro el estado original    
        chkMosPrimera.setSelected(blnPriImp);            // restauro el estado original        
        chkMosUltima.setSelected(blnUltImp);            // restauro el estado original             
        chkMosUltimoIngre.setSelected(blnUltIng);            // restauro el estado original  
        chkMosPrimerIngre.setSelected(blnPenIng);            // restauro el estado original        
        chkMosAju.setSelected(blnAju);            // restauro el estado original        
        chkMosAdi.setSelected(blnAdi);            // restauro el estado original   
        chkMosPor.setSelected(blnPor);
            
    }

    
    
    private class ZafThGuarGUI extends Thread
    {
        public void run()
        {
                if(isCamVal()){
                    if (actualizarDet())
                        {
                            lblMsgSis.setText("Listo");
                            pgrSis.setValue(0);
                            mostrarMsgInf("La operación se realizó con éxito.");
                            
                            if(objUtiGP.FluctuacionItems(cadItms)!=0)
                                mostrarMsgInf("No se ha realizado con exito el proceso de FLUCTUTACION sobre el precio nuevo."); 
                            cadItms.setLength(0);                            
                        }
                    else
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                objThrGuaGUI=null;
            butCon.doClick();
        }
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            int intColSel=-1;
            
//            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
//                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%1+INT_TBL_DAT_NUM_TOT_CES;
                        
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'><tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr><tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que el \"Mar.Uti.Cos.\" es menor al \"Mar.Uti.\".</td></tr></table><br></html>";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno del item dos";
                    break;
                case INT_TBL_DAT_COD_ALT_3:
                    strMsg="Código alterno 3 del item (Código GP Altiora)";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CHK_SER:
                    strMsg="Servicio";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
////                case INT_TBL_DAT_STK_CON:
////                    intColSel=tblDat.columnAtPoint(evt.getPoint());
////                    break;
                case INT_TBL_DAT_COD_GEN://CHIQIUI
                     strMsg="Código Generado";     
                     break;
                case INT_TBL_DAT_IVA://CHIQIUI
                     strMsg="IVA ";     
                     break;       
                case INT_TBL_DAT_GRP://CHIQIUI
                     strMsg="GRP";     
                     break;      
                case INT_TBL_DAT_DET://CHIQIUI
                     strMsg="DET";     
                     break;                      
                case INT_TBL_DAT_STK_CON:
                     strMsg="Stock";
                     break;
                default:
                    strMsg="";
                    break;
            }
            
                        for(int i=intNumColIniIngImp; i<intNumColFinIngImp; i++){
                            if(intColSel==i){
                                strMsg="Ingresos por Importación";
                                break;
                            }
                            }
                        
                         for(int i=intNumColIniCosUni; i<intNumColFinCosUni; i++){
                            if(intColSel==i){
                                strMsg="Costo Promedio del item";
                                break;
                            }
                        }
                         
                         for(int i=intNumColIniPedEmb; i<intNumColFinPedEmb; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Pedidos Embarcados";
                                break;
                            }
                        }
                        for(int i=intNumColIniPedEmb; i<intNumColFinPedEmb; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Pedidos Embarcados";
                                break;
                            }
                        }
                        for(int i=intNumColIniNotPed; i<intNumColFinNotPed; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Notas de Pedido";
                                break;
                            }
                        }
                        for(int i=intNumColIniMarNotPed; i<intNumColFinMarNotPed; i++){
                            if(intColSel==i){
                                strMsg="Margen de utilidad: Notas de Pedido";
                                break;
                            }
                        }
                        for(int i=intNumColIniPreReaMar; i<intNumColFinPreReaMar; i++){
                            if(intColSel==i){
                                if(i==intNumColIniPreReaMar){
                                    strMsg="Precio de venta";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+1)){
                                    strMsg="Precio de venta real por kilo incluido IVA";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+2)){
                                    strMsg="Margen de utilidad sobre el precio de venta real";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+3)){
                                    strMsg="Margen de utilidad mínimo";
                                    break;
                                }
                                
                            }

                        }
                        for(int i=intNumColIniFac; i<intNumColFinFac; i++){
                            if(intColSel==i){
                                if(i==intNumColIniFac){
                                    strMsg="Factor de costo en unidades";
                                    break;
                                }
                                if(i==(intNumColIniFac+1)){
                                    strMsg="Costo unitario con factor - Precio de venta con factor";
                                    break;
                                }
                            }
                        }
                        
                        for(int i=intNumColIniMarFac; i<intNumColFinMarFac; i++){
                            strMsg="Margen con factor";
                            break;
                        }                        
                        
                        
                        for(int i=intNumColIniPre; i<intNumColFinPre; i++){
                            if(intColSel==i){
                                if(i==intNumColIniPre){
                                    strMsg="Factor de precio en kilos incluido IVA - Precio objetivo en kilos incluido IVA";
                                    break;
                                }
                                if(i==(intNumColIniPre+1)){
                                    strMsg="Precio de lista calculado";
                                    break;
                                }
                            }
                        }
                        for(int i=intNumColIniCanMarPreLisNew; i<intNumColFinCanMarPreLisNew; i++){
                            if(intColSel==i){
                                strMsg="Margen de precio de lista calculado";
                                break;
                            }
                        }
                        for(int i=intNumColIniMarPrimero; i<intNumColFinMarPrimero; i++){
                            if(intColSel==i)
                            {
                                if(i==intNumColIniMarPrimero){
                                    strMsg="Precio Sug. Primer Pedido";
                                    break;
                                }
                                if(i==(intNumColIniMarPrimero+1)){
                                    strMsg="Margen de Precio Primer Pedido";
                                    break;
                                }
                            }
                        }   
                        
                        for(int i=intNumColIniChkPrimero; i<intNumColFinChkPrimero; i++){
                            if(intColSel==i)
                            {
                                if(i==intNumColIniChkPrimero){
                                    strMsg="Selección";
                                    break;
                                }
                            }
                        }                                         
                        
                        
                        
                        
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    


    private boolean configurarColumnasAdicionar(){
        boolean blnRes=true;
        boolean blnValidaP =false,blnValidaU =false;
        int ultimo=0;
        try{
            if(eliminaColumnasAdicionadas()){
                if(cargarCabeceraIngresosImportacion()){
                    if(agregarColumnasIngresosImportacion()){
                        if(agregarColumnasCostoUnitario()){//totales
                            if(cargarCabeceraPedidosEmbarcados()){
                                if(agregarColumnasPedidoEmbarcado()){
                                    if(cargarCabeceraNotasPedidos()){
                                        if(agregarColumnasNotaPedido()){
                                            if(agregarColumnasMargenNotaPedidoReciente()){
                                                if(agregarColumnasPrecioRealMargen()){
                                                    if(agregarColumnasFactor())
                                                    {
                                                        if (agregarColumnasFactorMargen())
                                                        {
                                                         if(agregarColumnasCheckMargen()){
////                                                            if(agregarColumnasFactorClau())
////                                                            {
                                                                
                                                                if(agregarColumnaMargenPrimerPedido()){
                                                                    if(agregarColumnasCheckMargenPrimero()){
                                                                        if(agregarColumnaMargenUltimoPedido()){
                                                                            if(agregarColumnasCheckMargenUltimo()){
                                                                               if(agregarColumnaMargenPrimeraImportacion()){
                                                                                    if(agregarColumnasCheckMargenPrimeraImportacion()){
                                                                                        if(agregarColumnaMargenUltimaImportacion()){
                                                                                            if(agregarColumnasCheckMargenUltimaImportacion()){

                                                                                                        if(agregarColumnasPrecio()){
                                                                                                            if(agregarColumnasCantidadMargenPrecioListaNew()){
                                                                                                                if(agregarColumnasCheckOtrosFiltros()){
                                                                                                                    if(agregarColumnasModificadoPrecioCabDet())
                                                                                                                    {
////                                                                                                                        if(agregarColumnasFactorGrpDet())
////                                                                                                                        {
////
////                                                                                                                        }                                                                                                                        
                                                                                                                        
                                                                                                if (agregarColumnaFactorPorcentual()){
                                                                                                    if (agregarColumnaChekcFactorPorcentual())
                                                                                                    {               
                                                                                                        if(agregarColumnasModificadoOtros())
                                                                                                        {
                                                                                                            
                                                                                                        }
                                                                                                        
                                                                                                                    }

                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
 
                                                                                            }

                                                                                        }                                                                                        

                                                                                    }                                                                                   
                                                                                } 
                                                                            }

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
////            }
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
        }
        return blnRes;
    }

   private boolean eliminaColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();            
     
            for (int i=(objTblMod.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblMod.removeColumn(tblDat, i);
            }          
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   private boolean agregarColumnasIngresosImportacion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddIngImp=arlDatIngImp.size();//
        intNumColIniIngImp=intNumColEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomIngImp="";
        try{
            objTblCelRenLblColDinIngImp=new ZafTblCelRenLbl();
            objTblCelRenLblColDinIngImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinIngImp.setTipoFormato(objTblCelRenLblColDinIngImp.INT_FOR_NUM);
            objTblCelRenLblColDinIngImp.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinIngImp.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinIngImp.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            
            
            
            for (int i=0; i<intNumColAddIngImp; i++){
                //strNomIngImp="" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM);
                strNomIngImp="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM) + "<BR>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_FEC) + "</DIV></HTML>";
                
                objUti.setStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COL, "" + (intNumColIniIngImp+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniIngImp+i);
                tbc.setHeaderValue(strNomIngImp);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinIngImp);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Lle.");
                    objTblHeaColGrpTit.setHeight(16);
                    
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);                
            }
            intNumColFinIngImp=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
   private boolean agregarColumnasCostoUnitario(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        intNumColAddCosUni=1;//
        intNumColIniCosUni=intNumColFinIngImp;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinCosUni=new ZafTblCelRenLbl();
            objTblCelRenLblColDinCosUni.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinCosUni.setTipoFormato(objTblCelRenLblColDinCosUni.INT_FOR_NUM);
            objTblCelRenLblColDinCosUni.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinCosUni.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinCosUni.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            
            
            for (int i=0; i<intNumColAddCosUni; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniCosUni+i);
                tbc.setHeaderValue("Cos.Uni.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinCosUni);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);                               
            }
            intNumColFinCosUni=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean agregarColumnasPedidoEmbarcado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=intNumColFinCosUni;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        try{
            objTblCelRenLblColDinPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPedEmb.setTipoFormato(objTblCelRenLblColDinPedEmb.INT_FOR_NUM);
            objTblCelRenLblColDinPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinPedEmb.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPedEmb.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                
                if((i%2)==0)
                    strNomPed="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM) + "<BR>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC) + "</DIV></HTML>";
                else
                    strNomPed="<HTML><DIV ALIGN=center>" + "Margen" + "<BR>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM) + "</DIV></HTML>";
                    
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue(strNomPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinPedEmb);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Emb.");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
            }
            
            intNumColFinPedEmb=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

   private boolean agregarColumnasNotaPedido(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddNotPed=arlDatNotPed.size();//
        intNumColIniNotPed=intNumColFinPedEmb;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomNotPed="";
        try{
            objTblCelRenLblColDinNotPed=new ZafTblCelRenLbl();
            objTblCelRenLblColDinNotPed.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinNotPed.setTipoFormato(objTblCelRenLblColDinNotPed.INT_FOR_NUM);
            objTblCelRenLblColDinNotPed.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinNotPed.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinNotPed.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddNotPed; i++){

                
                if((i%2)==0)
                    strNomNotPed="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_NUM) + "<BR>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_FEC) + "</DIV></HTML>" ;
                else
                    strNomNotPed="<HTML><DIV ALIGN=center>" + "Margen" + "<BR>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_NUM) + "</DIV></HTML>" ;
     
                objUti.setStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COL, "" + (intNumColIniNotPed+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniNotPed+i);
                
                tbc.setHeaderValue(strNomNotPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinNotPed);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Por.Emb.");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinNotPed=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   
   
   private boolean agregarColumnasMargenNotaPedidoReciente(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;

        intNumColAddMarNotPed=0;//
        intNumColIniMarNotPed=intNumColFinNotPed;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomNotPed="";
        try{
            objTblCelRenLblColDinMarNotPed=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarNotPed.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarNotPed.setTipoFormato(objTblCelRenLblColDinMarNotPed.INT_FOR_NUM);
            objTblCelRenLblColDinMarNotPed.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinMarNotPed.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarNotPed.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarNotPed.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinMarNotPed.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddMarNotPed; i++){
                    
                tbc=new javax.swing.table.TableColumn(intNumColIniMarNotPed+i);
                tbc.setHeaderValue("Mar.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarNotPed);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
            }
            
            intNumColFinMarNotPed=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   
   
   
private boolean agregarColumnasPrecioRealMargen()//para modificar la columnaPrecio
{
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPreReaMar=7;//
        intNumColIniPreReaMar=intNumColFinMarNotPed;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try
        {
            objTblCelRenLblColDinPreReaMar=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPreReaMar.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPreReaMar.setTipoFormato(objTblCelRenLblColDinPreReaMar.INT_FOR_NUM);
            objTblCelRenLblColDinPreReaMar.setFormatoNumerico(objParSis.getFormatoNumero(),false,true); 
            // Renderer especial para "Aju.Fac" (3 decimales)
            ZafTblCelRenLbl objTblCelRenLblAjuFac = new ZafTblCelRenLbl();
            objTblCelRenLblAjuFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAjuFac.setTipoFormato(objTblCelRenLblAjuFac.INT_FOR_NUM);
            objTblCelRenLblAjuFac.setFormatoNumerico("###,###,##0.000", false, true);  // 3 decimales
            objTblCelEdiTxtPreVta=new ZafTblCelEdiTxt();
            objTblCelRenLblColDinPreReaMar.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() 
            {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //String prueba ="";
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdStock;
                    int intFil=-1;
                    
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+3)))));
                    bgdPrecio=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdStock=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (INT_TBL_DAT_STK_CON))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (INT_TBL_DAT_STK_CON)))));
                    intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), intNumColIniModCabDet).toString().trim());
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPreReaMar.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    if ((bgdPrecio==null || bgdPrecio.compareTo(BigDecimal.ZERO)==0 ) &&(bgdStock.compareTo(BigDecimal.ZERO)>0))
                    objTblCelRenLblColDinPreReaMar.setBackground(Color.yellow);
                        if (((("D1".equals(valorCelda)) ||("D2".equals(valorCelda))) || (("D3".equals(valorCelda)) ||("D3".equals(valorCelda)))) ||
                           ((("D4".equals(valorCelda)) ||("D5".equals(valorCelda))) || (("D6".equals(valorCelda)) ||("D7".equals(valorCelda))|| ("D9".equals(valorCelda)) || ("D10".equals(valorCelda))))   )
                        {
                            objTblCelRenLblColDinPreReaMar.setBackground(new java.awt.Color(255, 204, 102));
                        } 
                        else
                        {
                          if ("D8".equals(valorCelda))
                              objTblCelRenLblColDinPreReaMar.setBackground(Color.WHITE);
                        }
                }
            });
            
            objTblCelEdiTxtPreVta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFil=-1;
                int intCol=-1;
                double bdeCanMargen,bdeFactor,bdeAjuMan,bdeAjuFac;
                double bdeDivisor,bdeDividendo,bdePrecioSugerido,bdeCosto;  
                BigDecimal nd_preAct ;
                BigDecimal nd_MarAct ;
                BigDecimal nd_AjuMan ;
                BigDecimal nd_AjuFac ;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar)));
                    nd_MarAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar+3)));
                    nd_AjuMan =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar+5)));//ok
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    int ne_tipSel=1;
                    String str_llave="",str_valor="";
                    bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar)));
                    ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                    ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));                        
                    if (bgdPrecio.compareTo(BigDecimal.ZERO) < 0)
                    {
                        int intNumFil = tblDat.getSelectedRow();
                        int intNumCol = tblDat.getSelectedColumn();
                        str_llave = intNumFil +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;
                            // Reasigna el valor corregido a la celda
                        objTblMod.setValueAt(bgdPrecio, intFil, intNumColIniPreReaMar);
                        ne_tipSel=8;
                    }       
                    if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
                    {
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,ne_tipSel,intNumColIniPreReaMar,intFil,nd_preAct,-1);
                        System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");

                        if(objTblMod.getValueAt(intFil, intNumColIniPreReaMar+3)==null){
                            bdeCanMargen = 0;
                        }
                        else{
                            bdeCanMargen=Double.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar+3).toString());
                        }

                        if(objTblMod.getValueAt(intFil, intNumColIniCosUni)==null){
                            bdeCosto = 0;
                        }
                        else{
                            bdeCosto=Double.parseDouble(objTblMod.getValueAt(intFil, intNumColIniCosUni).toString());
                        }

                        if((objTblMod.getValueAt(intFil, intNumColIniPreReaMar+5)==null) || (objTblMod.getValueAt(intFil, intNumColIniPreReaMar+5).equals("")))
                        {
                            bdeAjuMan = 0;
                        }
                        else{
                            bdeAjuMan=Double.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar+5).toString());//ok
                        }

                        //bdeCosto=Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI)==null?0:objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI).toString());
                        System.out.println("margen: " + bdeCanMargen);
                        bdeDivisor = 1- (bdeCanMargen/100);
                        if (bdeDivisor !=0)
                        {
                            bdeDividendo = 1/ bdeDivisor;
                            bdeFactor = bdeDividendo/0.75;
                            bdePrecioSugerido = bdeCosto * bdeFactor;                        
                        }
                        else
                        {
                            bdeFactor=0;
                            bdePrecioSugerido=0;
                            bdeAjuFac=0;
                        }
                        System.out.println(bdeFactor);
                        // Redondeo a 4 decimales
                        bdeFactor = new BigDecimal(bdeFactor).setScale(4, RoundingMode.HALF_UP).doubleValue();
                        bdePrecioSugerido = new BigDecimal(bdePrecioSugerido).setScale(4, RoundingMode.HALF_UP).doubleValue();     
                        bdeAjuFac = bdeAjuMan +bdeFactor;
                        double nd_marPriImp=0, nd_preSugPriIm=0;
                        double nd_marUltImp=0, nd_preSugUltIm=0;                                

                        double nd_marPriPed=0, nd_preSugPriPed=0;
                        double nd_marUltPed=0, nd_preSugUltPed=0;    
                        double nd_factor =0;

                        bdeAjuFac = new BigDecimal(bdeAjuFac)
                            .setScale(3, RoundingMode.CEILING)   // redondea a 2 decimales
                            .doubleValue();
                        bdePrecioSugerido=bdeCosto *bdeAjuFac;
                        GenerarColumnasAdicionalesImportacionesFl(0,intFil,bdeAjuFac);
                        //calcularValoresKilosFl(0);
                         if(chkMosValKil.isSelected())
                            GenerarColumnasAdicionalesImportacionesFl(1,intFil,bdeAjuFac); 
                         GenerarColumnasAdicionalesFl(intFil,bdeAjuFac);                                    
                        objTblMod.setValueAt(bdeFactor, intFil, intNumColIniFac);
                        objTblMod.setValueAt(bdePrecioSugerido, intFil, intNumColIniFac+1);
                        if (nd_MarAct.compareTo(BigDecimal.valueOf(bdeCanMargen)) == 0) 
                            System.out.println("Son iguales");
                        else
                            objTblMod.setValueAt(bdeFactor, intFil, intNumColIniPreReaMar+4);//o0
                        objTblMod.setValueAt(bdeAjuFac, intFil, intNumColIniPreReaMar+6);//ok
                        if(intCol ==intNumColIniPreReaMar+3)
                            objTblMod.setValueAt("M", intFil, intNumColIniModOtr);
                        jLabelLey.setVisible(false);
                    }
                    else
                    {
                        if(intCol ==intNumColIniPreReaMar+3)
                        {
                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniPreReaMar);
                            objTblMod.setValueAt(nd_AjuMan, intFil, intNumColIniPreReaMar+5);//ok {
                            objTblMod.setValueAt("M", intFil, intNumColIniModOtr);
                        }
                        else
                        {
                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniPreReaMar);
                            objTblMod.setValueAt(nd_MarAct, intFil, intNumColIniPreReaMar+3);
                            objTblMod.setValueAt(nd_AjuMan, intFil, intNumColIniPreReaMar+5);//ok                                
                            
                        }
                        jLabelLey.setVisible(true);
                    }
                }
            });
            for(int i=0; i<intNumColAddPreReaMar; i++)
            {
                String strSubTit =
                        (i == 0) ? "Pre.Vta."
                      : (i == 1) ? "Pre.Vta.Rea.Kil.(IVA)"
                      : (i == 2) ? "Mar.Pre.Vta.Rea."
                      : (i == 3) ? "Mar.Pre.Vta.Min."
                      : (i == 4) ? "Fac.Cos."                          
                      : (i == 5) ? "Ajuste"
                      : (i == 6) ? "Aju.Fac"
                      : "";                
                tbc=new javax.swing.table.TableColumn(intNumColIniPreReaMar+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                
                    // Asignar renderer según la columna
                if ((i == 5) || (i == 6)) {
                    // Columna "Aju.Fac"
                    tbc.setCellRenderer(objTblCelRenLblAjuFac);
                } else {
                    // Otras columnas
                    tbc.setCellRenderer(objTblCelRenLblColDinPreReaMar);
                }
                tbc.setCellEditor(objTblCelEdiTxtPreVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Precio de venta");
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            intNumColFinPreReaMar=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private void calcularAjustePegar()
{
    int i=0,intFilSel=0;
    BigDecimal bgdAjuste = BigDecimal.ZERO;
    int ne_Grupo =0;
    int ne_DetGrupo =0;
    BigDecimal bgdPrecioAnt,bgdPrecio;
    String str_llave="",str_valor="";
    int intNumFil=0,intNumCol=0;
    
    intFilSel=objTblMod.getRowCountTrue();
    
        for (i=0; i<intFilSel; i++)
        {
            str_llave = i +","+intNumColIniPreReaMar+5;
            str_valor=obtenerTblHist(str_llave);
////            Object objVal = objTblMod.getValueAt(i, intNumColIniPreReaMar + 5);
////            if (objVal != null && !objVal.toString().trim().equals("")) {
////                bgdAjuste = BigDecimal.valueOf(objUti.parseDouble(objVal)); // tu método utilitario
////            }
            //bgdAjuste=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar+5)));
            ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_GRP).toString()));
            ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_DET).toString()));
////            if (bgdAjuste.compareTo(BigDecimal.ZERO)==0)
////                objTblMod.setValueAt(0, i, intNumColIniPreReaMar+5); 
////            else
////            {
                if ((ne_Grupo ==0) && (ne_DetGrupo !=0))
                {
                    if (str_valor != null && !str_valor.equals("")) 
                        bgdAjuste = new BigDecimal(str_valor);
                    else 
                        bgdAjuste = BigDecimal.ZERO;    
                    objTblMod.setValueAt(bgdAjuste, i, intNumColIniPreReaMar+5);        
                }                            
                
////            }

        }
        double bdeCanMargen=0,bdeFactor,bdeAjuMan,bdeAjuFac;
        double bdeDivisor,bdeDividendo,bdePrecioSugerido,bdeCosto=0; 
        BigDecimal nd_MarAct ;
        int ne_tipSel=0;
        
        for (i=0; i<intFilSel; i++)
        {
            ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_GRP).toString()));
            ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_DET).toString()));            
                if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
                {
                    if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
                        objTblMod.setValueAt("S", i, intNumColIniModCabDet);
                    else
                    {
                        if ((ne_Grupo !=0) && (ne_DetGrupo ==0))
                            objTblMod.setValueAt("C", i, intNumColIniModCabDet);
                    }
                     
                     bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                    str_llave = i +","+intNumColIniPreReaMar;
                    str_valor=obtenerTblHist(str_llave);
                    
                    if (str_valor != null && !str_valor.equals("")) 
                        bgdPrecioAnt = new BigDecimal(str_valor);
                    else 
                        bgdPrecioAnt = BigDecimal.ZERO;
                                             
                    ne_tipSel=10;                         
                    
////                    procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,ne_tipSel,intNumColIniPreReaMar,i,bgdPrecioAnt,-1);
                    System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
                        
                    str_llave = i +","+intNumColIniPreReaMar+3;
                    str_valor=obtenerTblHist(str_llave);
                    if (str_valor != null && !str_valor.equals("")) 
                        nd_MarAct = new BigDecimal(str_valor);
                    else 
                        nd_MarAct = BigDecimal.ZERO;                

                    //nd_MarAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar+3)));
                    if((objTblMod.getValueAt(i, intNumColIniPreReaMar+5)==null) || (objTblMod.getValueAt(i, intNumColIniPreReaMar+5).equals("")))
                        bdeAjuMan = 0;
                    else
                        bdeAjuMan=Double.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar+5).toString());//ok

                    if((objTblMod.getValueAt(i, intNumColIniPreReaMar+3)==null) || (objTblMod.getValueAt(i, intNumColIniPreReaMar+3).equals("")))
                        bdeCanMargen = 0;
                    else
                        bdeCanMargen=Double.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar+3).toString());//ok    
                    if(objTblMod.getValueAt(i, intNumColIniCosUni)==null)
                        bdeCosto = 0;
                    else
                        bdeCosto=Double.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni).toString());

                    bdeDivisor = 1- (bdeCanMargen/100);
                    if (bdeDivisor !=0)
                    {
                        bdeDividendo = 1/ bdeDivisor;
                        bdeFactor = bdeDividendo/0.75;
                        bdePrecioSugerido = bdeCosto * bdeFactor;                        
                    }
                    else
                    {
                        bdeFactor=0;
                        bdePrecioSugerido=0;
                        bdeAjuFac=0;
                    } 
                    System.out.println(bdeFactor);
                    // Redondeo a 4 decimales
                    bdeFactor = new BigDecimal(bdeFactor).setScale(4, RoundingMode.HALF_UP).doubleValue();
                    bdePrecioSugerido = new BigDecimal(bdePrecioSugerido).setScale(4, RoundingMode.HALF_UP).doubleValue();     
                    bdeAjuFac = bdeAjuMan +bdeFactor;   
                    double nd_marPriImp=0, nd_preSugPriIm=0;
                    double nd_marUltImp=0, nd_preSugUltIm=0;                                

                    double nd_marPriPed=0, nd_preSugPriPed=0;
                    double nd_marUltPed=0, nd_preSugUltPed=0;    
                    double nd_factor =0;   
                    bdeAjuFac = new BigDecimal(bdeAjuFac)
                    .setScale(3, RoundingMode.CEILING)   // redondea a 2 decimales
                    .doubleValue();
                    bdePrecioSugerido=bdeCosto *bdeAjuFac;
                    
                    GenerarColumnasAdicionalesImportacionesFl(0,i,bdeAjuFac);
                    //calcularValoresKilosFl(0);
                     if(chkMosValKil.isSelected())
                        GenerarColumnasAdicionalesImportacionesFl(1,i,bdeAjuFac); 
                     GenerarColumnasAdicionalesFl(i,bdeAjuFac);                                    
                    objTblMod.setValueAt(bdeFactor, i, intNumColIniFac);
                    objTblMod.setValueAt(bdePrecioSugerido, i, intNumColIniFac+1);
                    if (nd_MarAct.compareTo(BigDecimal.valueOf(bdeCanMargen)) == 0) 
                        System.out.println("Son iguales");
                    else
                        objTblMod.setValueAt(bdeFactor, i, intNumColIniPreReaMar+4);//o0
                    objTblMod.setValueAt(bdeAjuFac, i, intNumColIniPreReaMar+6);//ok
                    jLabelLey.setVisible(false);                      
                    
                }
          
            
            
        }        
           
         
}

private void procesoAsignacionEstado(int grupo,int detgrupo,BigDecimal precio, int tipo,int columna, int fila,BigDecimal precioAct,int columnaCheck)
{
    int intNumFil =-1;
    if (((grupo !=0) && (detgrupo ==0)) || ((grupo ==0) && (detgrupo ==0)))
    {
        intNumFil=objTblMod.getRowCountTrue();
        if((grupo !=0) && (detgrupo ==0))
        {
    ////                                if (!blnCabecera)

                //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
                ProcesoColoreoVisualizaDetalleGruposoloCabe(grupo,precio,tipo,columna);
                jLabelLey.setVisible(false);
        }
        else
        {
            if ((grupo ==0) && (detgrupo ==0))
            {
                objTblMod.setValueAt("S", fila, intNumColIniModCabDet);
                jLabelLey.setVisible(false);
                objTblMod.setValueAt(precio, fila, columna);
            }
                

        }        

    }
    else
    {
        precioAct=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(fila, intNumColIniPreReaMar)));
        objTblMod.setValueAt(precioAct, fila, intNumColIniPreReaMar);
        if (tipo !=1)
            objTblMod.setChecked(false, tblDat.getSelectedRow(), columnaCheck);
        jLabelLey.setVisible(true);        
    }
        
       
    

    
}

private void procesoAsignacionEstadoPegado()
{
    int intNumFil = tblDat.getSelectedRow();   
    int intNumCol = tblDat.getSelectedColumn();    
    BigDecimal bgdPrecio,precioAct;
    int ne_Grupo =0;
    int ne_DetGrupo =0;
    String str_llave="",str_valor="";
    int ne_tipSel=1;
    //int intCol = tblDat.getSelectedColumn();
  //  int contador =0;

        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intNumFil, intNumColIniPreReaMar)));
        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_GRP).toString()));
        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intNumFil, INT_TBL_DAT_DET).toString()));   
        
        if (bgdPrecio.compareTo(BigDecimal.ZERO) < 0)
        {

            str_llave = intNumFil +","+intNumCol;
            str_valor=obtenerTblHist(str_llave);   
                // Convierte el valor anterior a BigDecimal (con validación)
            if (str_valor != null && !str_valor.equals("")) 
                bgdPrecio = new BigDecimal(str_valor);
            else 
                bgdPrecio = BigDecimal.ZERO;
                // Reasigna el valor corregido a la celda
            objTblMod.setValueAt(bgdPrecio, intNumFil, intNumColIniPreReaMar);
            ne_tipSel=8;
        } 
                        
                        
                        
    if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
    {
        if((ne_Grupo !=0) && (ne_DetGrupo ==0))
        {
    ////                                if (!blnCabecera)

                //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
                ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,ne_tipSel,intNumColIniPreReaMar);
                jLabelLey.setVisible(false);
        }
        else
        {
            if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
            {
                objTblMod.setValueAt("S", intNumFil, intNumColIniModCabDet);
                jLabelLey.setVisible(false);
            }
                

        }        

    }
    else
    {
        precioAct=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intNumFil, intNumColIniPreReaMar)));
        objTblMod.setValueAt(precioAct, intNumFil, intNumColIniPreReaMar);
        jLabelLey.setVisible(true);        
    }
        
       
    

    
}

private void pintarSoloUnaCelda(int filaObjetivo, int columnaObjetivo) {
    TableCellRenderer originalRenderer = tblDat.getDefaultRenderer(Object.class);

    tblDat.getColumnModel().getColumn(columnaObjetivo).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Formato numérico a 2 decimales
            if (value instanceof Number) {
                double val = ((Number) value).doubleValue();
                setText(String.format("%.2f", val));
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setHorizontalAlignment(SwingConstants.LEFT);
            }

            // Colorear solo la celda indicada
            if (row == filaObjetivo && column == columnaObjetivo) {
                comp.setBackground(Color.GREEN);
                comp.setForeground(Color.BLACK);
            } else if (isSelected) {
                comp.setBackground(Color.BLUE);
                comp.setForeground(Color.WHITE);
            } else {
                comp.setBackground(Color.WHITE);
                comp.setForeground(Color.BLACK);
            }

            return comp;
        }
    });

    tblDat.repaint();
}






////private void ProcesoColoreoVisualizaDetalleGrupo(int codGrupo,BigDecimal precio)
////{
////     boolean blnRes=false;
////    java.sql.Connection conExt;
////    java.sql.Statement stmLocGP;
////    java.sql.ResultSet rstLocGP;  
////    int intNumFil =-1;
////    String strcodGP="",strcodGPSQL="";
////    String strSQLGPD="";
////    double nd_preNue=0;
////    String strTip="";
////    Set<Integer> celdasAPintar  = new HashSet<>();
////        try
////        {
////            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());              
////            if(conExt!=null)
////            {
////                stmLocGP = conExt.createStatement();
////                strSQLGPD="";
////                strSQLGPD+=" SELECT   det.co_reg,TRIM(det.tx_codItm) AS item,det.co_facModPre,fac.tx_desCor,fac.tx_desLar,fac.nd_fac, pre.UOMPRICE, "+ precio +" \n";
////                strSQLGPD+="  , ("+ precio +" *fac.nd_fac) as preNuevo,trim(iv01.ITMGEDSC) as tresL    \n";                  
////                strSQLGPD+=" FROM    Dynamics2021..tbm_detGrpInv as det  (NOLOCK)  \n";  
////                strSQLGPD+="         inner join DYNAMICS2021..tbm_cabGrpInv as cab   (NOLOCK)on (det.co_grpInv = cab.co_grpInv)   \n";  
////                strSQLGPD+="         inner join Dynamics2021..tbm_facModPre as fac  (NOLOCK)on (det.co_facModPre = fac.co_facModPre)   \n";  
////                strSQLGPD+="         inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as pre  (NOLOCK) on (det.tx_codItm = pre.ITEMNMBR)   \n";
////                strSQLGPD+="         inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 (NOLOCK) on   (det.tx_codItm = iv01.ITEMNMBR)   \n";                
////                strSQLGPD+=" WHERE cab.co_tipAgr =1 and cab.co_grpInv ="+ codGrupo +" /*and fac.co_facModPre !=0*/ and det.co_reg>0  and pre.PRCLEVEL ='GENERAL' AND iv01.inactive=0   \n";
////                rstLocGP=stmLocGP.executeQuery(strSQLGPD);
////                arlDatGrpDet= new ArrayList();
////                
////                while(rstLocGP.next())
////                {
////                    arlRegGrpDet = new ArrayList();
////                    arlRegGrpDet.add(INT_ARL_GRP_DET_COD,codGrupo);                    
////                    arlRegGrpDet.add(INT_ARL_GRP_DET_ITEM,rstLocGP.getString("item"));                    
////                    arlRegGrpDet.add(INT_ARL_GRP_DET_PRE,rstLocGP.getDouble("preNuevo"));
////                    if (rstLocGP.getInt("co_reg")==1)
////                        arlRegGrpDet.add(INT_ARL_GRP_TIP,"C");                                        
////                    else
////                        arlRegGrpDet.add(INT_ARL_GRP_TIP,"D");                                        
////                    
////                    arlDatGrpDet.add(arlRegGrpDet);                    
////                }
////                intNumFil=objTblMod.getRowCountTrue();
////               
////                for (int j = 0; j < arlDatGrpDet.size(); j++) 
////                {
////                    strcodGPSQL=objUti.getStringValueAt(arlDatGrpDet, j, INT_ARL_GRP_DET_ITEM);
////                    nd_preNue=objUti.getDoubleValueAt(arlDatGrpDet, j, INT_ARL_GRP_DET_PRE);
////                    strTip=objUti.getStringValueAt(arlDatGrpDet, j, INT_ARL_GRP_TIP);
////                    for (int i = 0; i < intNumFil; i++) 
////                    {                
////                        strcodGP =objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_3).toString();//obtener el codigo GP
////                        if (strcodGPSQL.equals(strcodGP))
////                        {
////                            if (strTip.equals("D"))
////                            {
////                                objTblMod.setValueAt(strTip, i, intNumColIniModCabDet);
////                                objTblMod.setValueAt(nd_preNue, i, intNumColIniPreReaMar);                                
////                            }
////                            else
////                                objTblMod.setValueAt(strTip, i, intNumColIniModCabDet);
////
////                            /////pintarSoloUnaCelda(i,intNumColIniPreReaMar);
//////////////                                 
////                        }
////                        
////                    }                    
////                }
////                
////
////                
////                
////                System.out.println("ProcesoColoreoVisualizaDetalleGrupo: " + strSQLGPD);
////                stmLocGP.close();
////                stmLocGP=null;    
////                rstLocGP.close();
////                rstLocGP=null; 
////                conExt.close();
////                conExt=null;
////                blnRes=true;
////            }            
////            
////        }  
////        catch(java.sql.SQLException e){ 
////            System.err.println("OCURRIO UN ERROR Proceso_spInv21 "+ e );
////         }
////         catch (Exception e) {
////            System.
////                    err.println("OCURRIO UN ERROR Proceso_spInv21 "+ e );
////         }        
////
////     
////     
////     
////}

private void ProcesoColoreoVisualizaDetalleGruposoloCabe(int codGrupo,BigDecimal precio,int tipo,int columna)
{
     boolean blnRes=false;
    java.sql.Connection conExt;
    java.sql.Statement stmLocGP;
    java.sql.ResultSet rstLocGP;  
    int intNumFil =-1;
    int codDetGrp=0,codCabGrp=0;
    String strSQLGPD="";
    BigDecimal nd_preNue, nd_factor;
    String strTip="";
    Set<Integer> celdasAPintar  = new HashSet<>();
        try
        {
                intNumFil=objTblMod.getRowCountTrue();
                for (int i = 0; i < intNumFil; i++) 
                {
                    codDetGrp= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_DET).toString()); 
                    codCabGrp= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_GRP).toString()); 
                    if (codDetGrp ==codGrupo)
                    {
                        nd_factor= BigDecimal.valueOf(Double.parseDouble(objTblModFix.getValueAt(i, INT_TBL_FIX_FAC).toString())) ;
                        nd_preNue= precio.multiply(nd_factor); 
     
                            if (tipo==1)
                                objTblMod.setValueAt("D1", i, intNumColIniModCabDet);
                            if (tipo==2)
                                    objTblMod.setValueAt("D2", i, intNumColIniModCabDet);
                            if (tipo==3)
                                    objTblMod.setValueAt("D3", i, intNumColIniModCabDet);
                            if (tipo==4)
                                    objTblMod.setValueAt("D4", i, intNumColIniModCabDet);
                            if (tipo==5)
                                    objTblMod.setValueAt("D5", i, intNumColIniModCabDet);
                            if (tipo==6)
                                    objTblMod.setValueAt("D6", i, intNumColIniModCabDet);
                            if (tipo==7)
                                    objTblMod.setValueAt("D7", i, intNumColIniModCabDet);
                            if (tipo==8)
                                    objTblMod.setValueAt("D8", i, intNumColIniModCabDet);  
                            if (tipo==9)
                                    objTblMod.setValueAt("D9", i, intNumColIniModCabDet);  
                            if (tipo==10)
                                    objTblMod.setValueAt("D10", i, intNumColIniModCabDet);  

                              objTblMod.setValueAt(nd_preNue, i, columna);                              
                       
                                              
                    }
                    if (codCabGrp ==codGrupo)
                    {
                            objTblMod.setValueAt('C', i, intNumColIniModCabDet);
                            objTblMod.setValueAt(precio, i, columna);                            

                    }
                }
                System.out.println("ProcesoColoreoVisualizaDetalleGruposoloCabe: " + strSQLGPD);
                blnRes=true;
                        
        }  

         catch (Exception e) {
            System.
                    err.println("OCURRIO UN ERROR ProcesoColoreoVisualizaDetalleGruposoloCabe "+ e );
         }        

     
     
     
}

private void ProcesoColoreoEliminarDetalleGruposoloCabe(int codGrupo,BigDecimal precio,int tipo,int columna)
{
     boolean blnRes=false;
    java.sql.Connection conExt;
    java.sql.Statement stmLocGP;
    java.sql.ResultSet rstLocGP;  
    int intNumFil =-1;
    int codDetGrp=0,codCabGrp=0;
    String strSQLGPD="";
    BigDecimal nd_preNue, nd_factor;
    String strTip="";
    Set<Integer> celdasAPintar  = new HashSet<>();
        try
        {
                intNumFil=objTblMod.getRowCountTrue();
                for (int i = 0; i < intNumFil; i++) 
                {
                    codDetGrp= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_DET).toString()); 
                    codCabGrp= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_GRP).toString()); 
                    
                    nd_factor= BigDecimal.valueOf(Double.parseDouble(objTblModFix.getValueAt(i, INT_TBL_FIX_FAC).toString())) ;
                    nd_preNue= precio.multiply(nd_factor);                     
                    if (codDetGrp ==codGrupo)
                    {
                        
                            objTblMod.setValueAt("D8", i, intNumColIniModCabDet);
                            objTblMod.setValueAt(nd_preNue, i, columna);  
                                              
                    }
                    if (codCabGrp ==codGrupo)
                    {
                        objTblMod.setValueAt("", i, intNumColIniModCabDet);
                        objTblMod.setValueAt(precio, i, columna);
                    }
                }
                System.out.println("ProcesoColoreoEliminarDetalleGruposoloCabe: " + strSQLGPD);
                blnRes=true;
                        
        }  

         catch (Exception e) {
            System.
                    err.println("OCURRIO UN ERROR ProcesoColoreoEliminarDetalleGruposoloCabe "+ e );
         }        

     
     
     
}


private boolean agregarColumnasFactor()
{
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddFac=2;//chiqui111
        intNumColIniFac=intNumColFinPreReaMar;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinFac=new ZafTblCelRenLbl();
            objTblCelRenLblColDinFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinFac.setTipoFormato(objTblCelRenLblColDinFac.INT_FOR_NUM);
            //
//////            if (ne_menu==4908)// a 4 decimales
//////                objTblCelRenLblColDinFac.setFormatoNumerico("###,###,##0.0000", false, true);
//////            else
                objTblCelRenLblColDinFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtFacCos=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinFac.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    int intFil=-1;
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinFac.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    
                    
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal) //actual
////                    {
////////                        if ("D2".equals(valorCelda))  
////////                            objTblCelRenLblColDinFac.setBackground(Color.GREEN);
////                    }
////                    else
////                    {
////                            objTblCelRenLblColDinFac.setBackground(Color.white);                        
////                    }

                }
            });
            
            
            
            
            
            for(int i=0; i<intNumColAddFac; i++){
                String strSubTit=(i==0?"Fac.Cos.":"P.V.Fac.S");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
                tbc=new javax.swing.table.TableColumn(intNumColIniFac+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth((i==0)?56:68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinFac);
                tbc.setCellEditor(objTblCelEdiTxtFacCos);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Precio Modi.");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            
            objTblCelEdiTxtFacCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFil=-1;
                double bdeCanMargen,bdeFactor;
                double bdeDivisor,bdeDividendo,bdePrecioSugerido,bdeCosto;  
                BigDecimal nd_facCos ;
                BigDecimal nd_facCosSug ;
                
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {                        
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_facCos =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac)));
                    nd_facCosSug =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1)));
                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFil=tblDat.getSelectedRow();    
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                    ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString())); 
                    if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
                        jLabelLey.setVisible(false);
                    else
                    {
                        objTblMod.setValueAt(nd_facCos, intFil, intNumColIniFac);
                        objTblMod.setValueAt(nd_facCosSug, intFil, intNumColIniFac+1);
                        jLabelLey.setVisible(true);                                                
                    }
                    calcularCosUniFac();
                    calcularMarFac();


                    
                }

            });
            
            
            
            
            intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



   
private boolean agregarColumnaMargenPrimerPedido()//chiqui09112023
      {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarPrimero=2;//
        intNumColIniMarPrimero=intNumColFinChk;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarPrimero=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarPrimero.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarPrimero.setTipoFormato(objTblCelRenLblColDinMarPrimero.INT_FOR_NUM);
            objTblCelRenLblColDinMarPrimero.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarPrimero=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinMarPrimero.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdPrecioAct;
                    bgdPrecio =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdPrecioAct =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniMarPrimero))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), (intNumColIniMarPrimero)))));

                    if (bgdPrecio==null || bgdPrecio.compareTo(BigDecimal.ZERO)==0)
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarPrimero.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarPrimero.setBackground(new Color(144, 238, 144));
                            
                            else
                                objTblCelRenLblColDinMarPrimero.setBackground(javax.swing.UIManager.getColor("Table.background"));                             
                        }
                    }
                    else
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarPrimero.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarPrimero.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarPrimero.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        }
                            
                    }
                                        int intFil=-1;
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinMarPrimero.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
//////                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal && chkOtrosDesmarcados) //actual
////                    {
                        if ("D3".equals(valorCelda))  
                        {
////                            objTblCelRenLblColDinMarPrimero.setBackground(Color.GREEN);
                        }
                        
                        
                    

        
        
////                   contadorConsu ++; 
                }                
            });
            
            for(int i=0; i<intNumColAddMarPrimero; i++){
////                String strSubTit="Pre.Sug";
                String strSubTit=(i==0?"Pre.Sug":"Marg.");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
////                String strSubTit=(i==0?"Fac.Cos.":"P.V.Fac.S");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
                tbc=new javax.swing.table.TableColumn(intNumColIniMarPrimero+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarPrimero);
                tbc.setCellEditor(objTblCelEdiTxtMarPrimero);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Primer Pedido");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
////                if (chkMosPrimera.isSelected()== false)
////                {
////                       objTblMod.addSystemHiddenColumn(intNumColIniMarPrimero, tblDat);
////                }

                
            
            objTblCelEdiTxtMarPrimero.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
             //////////////////////////////////////////////////////////////
            
            
            
            
            
            
            
            
            /////////////////////////////////////////////////////////////
            
            
            
            
            
            
            intNumColFinMarPrimero=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
private boolean agregarColumnaMargenUltimoPedido()//chiqui
      {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarUltimo=2;//
        intNumColIniMarUltimo=intNumColFinChkPrimero;//numero de columnas que tiene el modelo antes de adicionar las columnas
////        intNumColIniMarUltimo=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarUltimo=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarUltimo.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarUltimo.setTipoFormato(objTblCelRenLblColDinMarUltimo.INT_FOR_NUM);
            objTblCelRenLblColDinMarUltimo.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarUltimo=new ZafTblCelEdiTxt();
            
            
            
            
            
 
            objTblCelRenLblColDinMarUltimo.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdPrecioAct;
////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo+2)))));
////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo+3)))));
                    bgdPrecio =(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdPrecioAct =(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), (intNumColIniMarUltimo)))));
                    
////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else{
////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////                            objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
////                        else{
////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////                                objTblCelRenLblColDinMarUltimo.setBackground(new Color(255,151,151));
////                            }
////                            else{
////                                objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                            }    
////                        }
////                    }
////////                    if (bgdPrecioAct.compareTo(bgdPrecio)> 0)//el precio sugerido es mayor al precio de venta
////////                        objTblCelRenLblColDinMarUltimo.setBackground(new Color(255,151,151));
////////                    else
////////                        objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));              

                    if (bgdPrecio==null || bgdPrecio.compareTo(BigDecimal.ZERO)==0)
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarUltimo.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));                             
                        }
                    }
                    else
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarUltimo.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarUltimo.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        }
                            
                    }
                    

                    int intFil=-1;
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinMarUltimo.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
//////                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal && chkOtrosDesmarcados) //actual
////                    {
                        if ("D4".equals(valorCelda))  
                        {
////                            objTblCelRenLblColDinMarUltimo.setBackground(Color.GREEN);
                        }
                        
                        
                    
                }                
            });
            
            for(int i=0; i<intNumColAddMarUltimo; i++){
////                String strSubTit="Pre.Sug.";
                String strSubTit=(i==0?"Pre.Sug":"Marg.");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
                tbc=new javax.swing.table.TableColumn(intNumColIniMarUltimo+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarUltimo);
                tbc.setCellEditor(objTblCelEdiTxtMarUltimo);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ultimo Pedido");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
////            if (chkMosUltima.isSelected()== false)
////            {
////                   objTblMod.addSystemHiddenColumn(intNumColIniMarUltimo, tblDat);
////            }
            
            
            objTblCelEdiTxtMarUltimo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
            
            intNumColFinMarUltimo=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   

private boolean agregarColumnaMargenPrimeraImportacion()//chiqui 27112023
      {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarPrimeraImporta=2;//
        intNumColIniMarPrimeraImporta=intNumColFinChkUltimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
////        intNumColIniMarUltimo=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarPrimeraImporta=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarPrimeraImporta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarPrimeraImporta.setTipoFormato(objTblCelRenLblColDinMarPrimeraImporta.INT_FOR_NUM);
            objTblCelRenLblColDinMarPrimeraImporta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarPrimeraImporta=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinMarPrimeraImporta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdPrecioAct;
////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta+2)))));
////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta+3)))));
                    bgdPrecio =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdPrecioAct =(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), (intNumColIniMarPrimeraImporta)))));                    
////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else{
////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////                            objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
////                        else{
////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(new Color(255,151,151));
////                            }
////                            else{
////                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                            }    
////                        }
////                    }
////////                    if (bgdPrecioAct.compareTo(bgdPrecio)> 0)//el precio sugerido es mayor al precio de venta
////////                        objTblCelRenLblColDinMarPrimeraImporta.setBackground(new Color(255,151,151));
////////                    else
////////                        objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));     
                    if (bgdPrecio==null || bgdPrecio.compareTo(BigDecimal.ZERO)==0)
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));                             
                        }
                    }
                    else
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarPrimeraImporta.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        }
                            
                    }
                    
                    int intFil=-1;
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinMarPrimeraImporta.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
//////                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal && chkOtrosDesmarcados) //actual
////                    {
                        if ("D5".equals(valorCelda))  
                        {
////                            objTblCelRenLblColDinMarPrimeraImporta.setBackground(Color.GREEN);
                        }
                        
                }                
            });
            
            for(int i=0; i<intNumColAddMarPrimeraImporta; i++){
////                String strSubTit="Pre.Sug.";
                String strSubTit=(i==0?"Pre.Sug":"Costo");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
////                String strSubTit="Penultimo";
                tbc=new javax.swing.table.TableColumn(intNumColIniMarPrimeraImporta+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarPrimeraImporta);
                tbc.setCellEditor(objTblCelEdiTxtMarPrimeraImporta);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Penultimo");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
////            if (chkMosUltima.isSelected()== false)
////            {
////                   objTblMod.addSystemHiddenColumn(intNumColIniMarUltimo, tblDat);
////            }
            
            
            objTblCelEdiTxtMarPrimeraImporta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
            
            intNumColFinMarPrimeraImporta=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
      
private boolean agregarColumnaMargenUltimaImportacion()//chiqui 27112023
      {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarUltimaImporta=2;//
        intNumColIniMarUltimaImporta=intNumColFinChkPrimeraImporta;//numero de columnas que tiene el modelo antes de adicionar las columnas
////        intNumColIniMarUltimo=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarUltimaImporta=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarUltimaImporta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarUltimaImporta.setTipoFormato(objTblCelRenLblColDinMarUltimaImporta.INT_FOR_NUM);
            objTblCelRenLblColDinMarUltimaImporta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarUltimaImporta=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinMarUltimaImporta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos,bgdPrecio,bgdPrecioAct;
////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta+2)))));
////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta+3)))));
                    bgdPrecio =(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniPreReaMar))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniPreReaMar)))));
                    bgdPrecioAct =(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), (intNumColIniMarUltimaImporta)))));                                        
////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else{
////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////                            objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
////                        else{
////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////                                objTblCelRenLblColDinMarUltimaImporta.setBackground(new Color(255,151,151));
////                            }
////                            else{
////                                objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                            }    
////                        }
////                    }
                    if (bgdPrecio==null || bgdPrecio.compareTo(BigDecimal.ZERO)==0)
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarUltimaImporta.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));                             
                        }
                    }
                    else
                    {
                        if (bgdPrecioAct==null || bgdPrecioAct.compareTo(BigDecimal.ZERO)==0)
                            objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        else
                        {
                            if (bgdPrecioAct.compareTo(bgdPrecio)> 0)
                                objTblCelRenLblColDinMarUltimaImporta.setBackground(new Color(144, 238, 144));
                            else
                                objTblCelRenLblColDinMarUltimaImporta.setBackground(javax.swing.UIManager.getColor("Table.background")); 
                        }
                            
                    }
                    
int intFil=-1;
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinMarUltimaImporta.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
//////                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal && chkOtrosDesmarcados) //actual
////                    {
                        if ("D6".equals(valorCelda))  
                        {
////                            objTblCelRenLblColDinMarUltimaImporta.setBackground(Color.GREEN);
                        }                    
              
                }                
            });
            
            for(int i=0; i<intNumColAddMarUltimaImporta; i++){
////                String strSubTit="Pre.Sug.";
                String strSubTit=(i==0?"Pre.Sug":"Costo.");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC
////                String strSubTit=("Pre.Sug");//Se cambia de Pre.Vta.Fac. a P.V.Fac. Solicitado por LSC                
                tbc=new javax.swing.table.TableColumn(intNumColIniMarUltimaImporta+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarUltimaImporta);
                tbc.setCellEditor(objTblCelEdiTxtMarUltimaImporta);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ultimo");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
////            if (chkMosUltima.isSelected()== false)
////            {
////                   objTblMod.addSystemHiddenColumn(intNumColIniMarUltimo, tblDat);
////            }
            
            
            objTblCelEdiTxtMarUltimaImporta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
            
            intNumColFinMarUltimaImporta=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 

   
   
   
   private boolean agregarColumnasFactorMargen(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarFac=1;//
        intNumColIniMarFac=intNumColFinFac;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarFac=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarFac.setTipoFormato(objTblCelRenLblColDinMarFac.INT_FOR_NUM);
            objTblCelRenLblColDinMarFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarFac=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinMarFac.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarFac.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarFac.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinMarFac.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    


                    
                }                
            });
            
            for(int i=0; i<intNumColAddMarFac; i++)
            {
                String strSubTit="Mar.Fac.";
                tbc=new javax.swing.table.TableColumn(intNumColIniMarFac+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarFac);
                tbc.setCellEditor(objTblCelEdiTxtMarFac);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("factor         ");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            
            objTblCelEdiTxtMarFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
            
            intNumColFinMarFac=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
  
   
   

   
   
   
   
   
   
   private boolean agregarColumnasPrecio(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
//        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
//        objTblHeaGrp.setHeight(16*3);
//        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPre=2;//
////        intNumColIniPre=intNumColFinMarFac;//numero de columnas que tiene el modelo antes de adicionar las columnas  *AQUI  "intNumColFinFac" 
////        intNumColIniPre=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas  *AQUI  "intNumColFinFac"chiqui
////            intNumColIniPre=intNumColFinChkUltimaImporta;//se cambio 19122023
            intNumColIniPre=intNumColFinChkUltimaImporta;
////        intNumColIniPre=intNumColFinChkUltimo;//numero de columnas que tiene el modelo antes de adicionar las columnas  *AQUI  "intNumColFinFac"chiqui
        try{
            objTblCelRenLblColDinPre=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPre.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPre.setTipoFormato(objTblCelRenLblColDinPre.INT_FOR_NUM);
            objTblCelRenLblColDinPre.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtPreObjKil=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinPre.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPre.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for(int i=0; i<intNumColAddPre; i++){
                String strSubTit=(i==0?"Pre.Vta.Obj.Kil.(IVA)":"Pre.Vta.Lis.Cal.");
                tbc=new javax.swing.table.TableColumn(intNumColIniPre+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinPre);
                tbc.setCellEditor(objTblCelEdiTxtPreObjKil);
                
                objTblCelEdiTxtPreObjKil.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                            calcularPreLisCal();
                            //calcularMarPreObjKil();
                            calcularMarPreLisCal_row();                       
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
//                if(i==0){
//                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Datos de precios");
//                    objTblHeaColGrpTit.setHeight(16);
//                }
//                
//                objTblHeaColGrpTit.add(tbc);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinPre=objTblMod.getColumnCount();

//            objTblHeaGrp=null;
//            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean agregarColumnasCantidadMargenPrecioListaNew(){
        boolean blnRes=true;

        javax.swing.table.TableColumn tbc;

        intNumColAddCanMarPreLisNew=1;//
////        intNumColIniCanMarPreLisNew=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        intNumColIniCanMarPreLisNew=intNumColFinPre;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinCanMarPreLisNew=new ZafTblCelRenLbl();
            objTblCelRenLblColDinCanMarPreLisNew.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinCanMarPreLisNew.setTipoFormato(objTblCelRenLblColDinCanMarPreLisNew.INT_FOR_NUM);
            objTblCelRenLblColDinCanMarPreLisNew.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtCanMarPreLisNew=new ZafTblCelEdiTxt();
            
            objTblCelRenLblColDinCanMarPreLisNew.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinCanMarPreLisNew.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    
int intFil=-1;
                    
                                        intFil=tblDat.getSelectedRow(); 
                    String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), intNumColIniModCabDet) == null
                            ? null
                            : objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), intNumColIniModCabDet).toString().trim());                    
                    
//////                    boolean chkPrincipal = objTblMod.isChecked(intFil, intNumColIniChk);
//////                    boolean chkOtrosDesmarcados = 
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimero) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimo) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkOtro) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkPrimeraImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkUltimaImporta) &&
//////                           objTblMod.isChecked(intFil, intNumColIniChkFactor);
                    
////                    if  (chkPrincipal && chkOtrosDesmarcados) //actual
////                    {
                        if ("D7".equals(valorCelda))  
                        {
////                            objTblCelRenLblColDinCanMarPreLisNew.setBackground(Color.GREEN);
                        }   
                        
                }
            });
            
            for (int i=0; i<intNumColAddCanMarPreLisNew; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniCanMarPreLisNew+i);
                tbc.setHeaderValue("Mar.Pre.Lis.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinCanMarPreLisNew);
                tbc.setCellEditor(objTblCelEdiTxtCanMarPreLisNew);
                
                objTblCelEdiTxtCanMarPreLisNew.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        calcularPrecioListaCalculado_Margen();
                        
                        
                    }
                });
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);                               
            }
            intNumColFinCanMarPreLisNew=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
private boolean agregarColumnasModificadoPrecioCabDet() // 27062025
{
    boolean blnRes = true;

    javax.swing.table.TableColumn tbc;

    // Número de columnas a agregar
    intNumColAddModCabDet = 1;

    // Columna inicial donde se insertan las nuevas (después de las existentes)
    intNumColIniModCabDet = intNumColFinChkOtro;

    try {
        objTblCelRenLblColDinModCabDet = new ZafTblCelRenLbl();
        objTblCelRenLblColDinModCabDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLblColDinModCabDet.setTipoFormato(objTblCelRenLblColDinModCabDet.INT_FOR_NUM);
        objTblCelRenLblColDinModCabDet.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);

        objTblCelEdiTxtModCabDet = new ZafTblCelEdiTxt();

        objTblCelRenLblColDinModCabDet.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinModCabDet.getRowRender(), intNumColIniModCabDet) == null
                        ? null
                        : objTblMod.getValueAt(objTblCelRenLblColDinModCabDet.getRowRender(), intNumColIniModCabDet).toString().trim());

////                if ("hola".equals(valorCelda)) {
////                    objTblCelRenLblColDinModCabDet.setBackground(Color.GREEN);
////                } else {
////                    objTblCelRenLblColDinModCabDet.setBackground(Color.WHITE);
////                }
            }
        });

        for (int i = 0; i < intNumColAddModCabDet; i++) {
            int colIndex = intNumColIniModCabDet + i;
            tbc = new javax.swing.table.TableColumn(colIndex);
            tbc.setHeaderValue("Mod.Cab.Det. " + (i + 1));
            tbc.setPreferredWidth(68);
            tbc.setCellRenderer(objTblCelRenLblColDinModCabDet);
            tbc.setCellEditor(objTblCelEdiTxtModCabDet);

            objTblCelEdiTxtModCabDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    // Aquí puedes hacer lógica adicional, como cálculos automáticos
                }
            });

            objTblMod.addColumn(tblDat, tbc);
        }

        intNumColFinModCabDet = objTblMod.getColumnCount();
    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}
private boolean agregarColumnasModificadoOtros() // 27062025
{
    boolean blnRes = true;

    javax.swing.table.TableColumn tbc;

    // Número de columnas a agregar
    intNumColAddModOtr = 1;

    // Columna inicial donde se insertan las nuevas (después de las existentes)
    intNumColIniModOtr = intNumColFinChkFactor;

    try {
        objTblCelRenLblColDinModOtr = new ZafTblCelRenLbl();
        objTblCelRenLblColDinModOtr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLblColDinModOtr.setTipoFormato(objTblCelRenLblColDinModOtr.INT_FOR_NUM);
        objTblCelRenLblColDinModOtr.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);

        objTblCelEdiTxtModOtr = new ZafTblCelEdiTxt();

        objTblCelRenLblColDinModOtr.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinModOtr.getRowRender(), intNumColIniModOtr) == null
                        ? null
                        : objTblMod.getValueAt(objTblCelRenLblColDinModOtr.getRowRender(), intNumColIniModOtr).toString().trim());

////                if ("hola".equals(valorCelda)) {
////                    objTblCelRenLblColDinModCabDet.setBackground(Color.GREEN);
////                } else {
////                    objTblCelRenLblColDinModCabDet.setBackground(Color.WHITE);
////                }
            }
        });

        for (int i = 0; i < intNumColAddModOtr; i++) {
            int colIndex = intNumColIniModOtr + i;
            tbc = new javax.swing.table.TableColumn(colIndex);
            tbc.setHeaderValue("Mod.Otro");
            tbc.setPreferredWidth(68);
            tbc.setCellRenderer(objTblCelRenLblColDinModOtr);
            tbc.setCellEditor(objTblCelEdiTxtModOtr);
            objTblMod.addSystemHiddenColumn(intNumColIniModOtr, tblDat);

            objTblCelEdiTxtModOtr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    // Aquí puedes hacer lógica adicional, como cálculos automáticos
                }
            });

            objTblMod.addColumn(tblDat, tbc);
        }

        intNumColFinModOtr = objTblMod.getColumnCount();
    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}

private boolean agregarColumnasFactorGrpDet() 
{
    boolean blnRes = true;

    javax.swing.table.TableColumn tbc;

    // Número de columnas a agregar
    intNumColAddFacGrpDet = 1;

    // Columna inicial donde se insertan las nuevas (después de las existentes)
    intNumColIniFacGrpDet = intNumColFinModCabDet;

    try {
        objTblCelRenLblColDinFacGrpDet = new ZafTblCelRenLbl();
        objTblCelRenLblColDinFacGrpDet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLblColDinFacGrpDet.setTipoFormato(objTblCelRenLblColDinFacGrpDet.INT_FOR_NUM);
        objTblCelRenLblColDinFacGrpDet.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);

        objTblCelEdiTxtFacGrpDet = new ZafTblCelEdiTxt();

        objTblCelRenLblColDinFacGrpDet.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            @Override
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                String valorCelda = (objTblMod.getValueAt(objTblCelRenLblColDinFacGrpDet.getRowRender(), intNumColIniFacGrpDet) == null
                        ? null
                        : objTblMod.getValueAt(objTblCelRenLblColDinFacGrpDet.getRowRender(), intNumColIniFacGrpDet).toString().trim());

////                if ("hola".equals(valorCelda)) {
////                    objTblCelRenLblColDinFactGrpDet.setBackground(Color.GREEN);
////                } else {
////                    objTblCelRenLblColDinFactGrpDet.setBackground(Color.WHITE);
////                }
            }
        });

        for (int i = 0; i < intNumColAddFacGrpDet; i++) {
            int colIndex = intNumColIniFacGrpDet + i;
            tbc = new javax.swing.table.TableColumn(colIndex);
            tbc.setHeaderValue("Fact.Grp.Det " + (i + 1));
            tbc.setPreferredWidth(68);
            tbc.setCellRenderer(objTblCelRenLblColDinFacGrpDet);
            tbc.setCellEditor(objTblCelEdiTxtFacGrpDet);

            objTblCelEdiTxtFacGrpDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    // Aquí puedes hacer lógica adicional, como cálculos automáticos
                }
            });

            objTblMod.addColumn(tblDat, tbc);
        }

        intNumColFinFacGrpDet = objTblMod.getColumnCount();
    } catch (Exception e) {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}




////    private boolean agregarColumnaIva(){
////        boolean blnRes=true;
////
////        javax.swing.table.TableColumn tbc;
////
////        intNumColAddCanMarIVA=1;//
////////        intNumColIniCanMarPreLisNew=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
////        intNumColIniCanMarIVA=intNumColFinChkOtro;//numero de columnas que tiene el modelo antes de adicionar las columnas
////        try{
////            objTblCelRenLblColDinCanMarIVA=new ZafTblCelRenLbl();
////            objTblCelRenLblColDinCanMarIVA.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////            objTblCelRenLblColDinCanMarIVA.setTipoFormato(objTblCelRenLblColDinCanMarIVA.INT_FOR_NUM);
////            objTblCelRenLblColDinCanMarIVA.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
////            
////            objTblCelEdiTxtCanMarIVA=new ZafTblCelEdiTxt();
////            
////////////            objTblCelRenLblColDinCanMarIVA.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
////////////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
////////////                    BigDecimal bgdMarUti, bgdMarUtiCos;
////////////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarIVA.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarIVA.getRowRender(), (intNumColIniPreReaMar+2)))));
////////////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarIVA.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarIVA.getRowRender(), (intNumColIniPreReaMar+3)))));
////////////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////////////                    {
////////////                        objTblCelRenLblColDinCanMarIVA.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////////                    }
////////////                    else{
////////////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////////////                            objTblCelRenLblColDinCanMarIVA.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////////                        }
////////////                        else{
////////////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////////////                                objTblCelRenLblColDinCanMarIVA.setBackground(new Color(255,151,151));
////////////                            }
////////////                            else{
////////////                                objTblCelRenLblColDinCanMarIVA.setBackground(javax.swing.UIManager.getColor("Table.background"));
////////////                            }    
////////////                        }
////////////                    }
////////////                }
////////////            });
////            
////            for (int i=0; i<intNumColAddCanMarIVA; i++){
////                tbc=new javax.swing.table.TableColumn(intNumColIniCanMarIVA+i);
////                tbc.setHeaderValue("IVA");
////                
////                //Configurar JTable: Establecer el ancho de la columna.
////                tbc.setPreferredWidth(68);
////                //Configurar JTable: Renderizar celdas.
////                tbc.setCellRenderer(objTblCelRenLblColDinCanMarIVA);
////                tbc.setCellEditor(objTblCelEdiTxtCanMarIVA);
////                
////                objTblCelEdiTxtCanMarIVA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////
////                    }
////                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                        calcularPrecioListaCalculado_Margen();
////                        
////                        
////                    }
////                });
////               
////                //Configurar JTable: Agregar la columna al JTable.
////                objTblMod.addColumn(tblDat, tbc);                               
////            }
////            intNumColFinCanMarIVA=objTblMod.getColumnCount();
////
////        }
////        catch(Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }

   
   
   //19122023
   private boolean agregarColumnaFactorPorcentual(){
        boolean blnRes=true;

        javax.swing.table.TableColumn tbc;

        intNumColAddFactorPor=1;//
////        intNumColIniCanMarPreLisNew=ultimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        intNumColIniFactorPor=intNumColFinModCabDet;//numero de columnas que tiene el modelo antes de adicionar las columnas
        
        try{
            objTblCelRenLblColDinFactorPor=new ZafTblCelRenLbl();
            objTblCelRenLblColDinFactorPor.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinFactorPor.setTipoFormato(objTblCelRenLblColDinFactorPor.INT_FOR_NUM);
            objTblCelRenLblColDinFactorPor.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtFactorPor=new ZafTblCelEdiTxt();
            
            objTblCelRenLblColDinFactorPor.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinFactorPor.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFactorPor.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinFactorPor.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFactorPor.getRowRender(), (intNumColIniPreReaMar+3)))));
////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenLblColDinFactorPor.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else{
////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
////                            objTblCelRenLblColDinFactorPor.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
////                        else{
////                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
////                                objTblCelRenLblColDinFactorPor.setBaFckground(new Color(255,151,151));
////                            }
////                            else{
////                                objTblCelRenLblColDinFactorPor.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                            }    
////                        }
////                    }
                }
            });
            
            for (int i=0; i<intNumColAddFactorPor; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniFactorPor+i);
                tbc.setHeaderValue("Pre.Cal.Porce.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinFactorPor);
                tbc.setCellEditor(objTblCelEdiTxtFactorPor);
                
                objTblCelEdiTxtFactorPor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        calcularPrecioListaCalculado_Margen();
                        
                        
                    }
                });
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);                               
            }
            intNumColFinFactorPor=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   

   private boolean agregarColumnaChekcFactorPorcentual(){ //chiqui esta si
        boolean blnRes=true;
        strAux="";

        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        
        blnMarTodChkFactorPor=true;  // Inicializa

        intNumColAddCheckFactor=1;//
        intNumColIniChkFactor=intNumColFinFactorPor;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            //<editor-fold defaultstate="collapsed" desc="/*Seccion: Formato*/">
            objTblCelRenLblColDinChekFactor=new ZafTblCelRenChk();
            
            objTblCelEdiTxtChekFactor=new ZafTblCelEdiChk();
            objTblCelEdiTxtChekFactor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               int intFil=-1;
               BigDecimal nd_preAct ;                 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    //nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkFactor-2)));
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil,intNumColIniFactorPor)));
                } 
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChekFactor.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);                        
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                        
                                                        
                        intFil=tblDat.getSelectedRow();    
                        BigDecimal bgdPrecio;
                        int ne_Grupo =0;
                        int ne_DetGrupo =0;
                        //int intCol = tblDat.getSelectedColumn();
                      //  int contador =0;
                    
                  
                        //bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1)));
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFactorPor)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,9,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniFactorPor+1); 
                    }
                    else
                    {
                        intFil=tblDat.getSelectedRow();   
                        BigDecimal bgdPrecio;
                        int ne_Grupo =0;
                        int ne_DetGrupo =0;
                        String str_llave="",str_valor="";
                        //int intCol = tblDat.getSelectedColumn();
                      //  int contador =0;

                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        int intNumCol = intNumColIniPreReaMar;
                        str_llave = intFil +","+intNumCol;
                        str_valor=obtenerTblHist(str_llave);   
                            // Convierte el valor anterior a BigDecimal (con validación)
                        if (str_valor != null && !str_valor.equals("")) 
                            bgdPrecio = new BigDecimal(str_valor);
                        else 
                            bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkFactor); 
                    }                    
                }
            });
            //</editor-fold>
            
            // Crear columnas
            for(int i=0; i<intNumColAddCheckFactor; i++){
                String strSubTit="Selección";
                tbc=new javax.swing.table.TableColumn(intNumColIniChkFactor+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinChekFactor);
                tbc.setCellEditor(objTblCelEdiTxtChekFactor);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Porcentaje");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
                tblDat.getTableHeader().setReorderingAllowed(false);
                
            }          
             
            
            

            intNumColFinChkFactor=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
   private boolean cargarCabeceraIngresosImportacion(){
        boolean blnRes=true;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conIngImp!=null){
                stmIngImp=conIngImp.createStatement();
                strAux="";
                //Filtrar por "Item".
////                if (objVenConItm01.isCondicionAplicada())
////                {
////                    strAux+=objVenConItm01.getCondicionesSQL();
////                }
                
                strSQL="";
                strSQL+=" SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="      , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="        , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+="   FROM(";
                //INIMPO
                strSQL+="        SELECT a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+="	      , c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                strSQL+="        FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1 ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                strSQL+="              INNER JOIN tbm_cabPedEmbImp AS d1";
                strSQL+="              ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc)";
////                strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
////                strSQL+="        WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";
//////                if (Cadenaitems!="")
//////                {
//////                    strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
//////                    strSQL+=  " WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";
//////                }
//////                    
//////                else
//////                {
//////                    strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
//////                    strSQL+="        WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";                    
//////                }

                if (!Cadenaitems.equals(""))//modificado
                {
                    strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    strSQL+=  " WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";
                }
                    
                else
                {
                    if (validaTodo)
                        strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv ) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    else
                        strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  = '') AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    
                    //strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  = '') AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    strSQL+=  " WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";                  
                }


////                strSQL+=strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+="	 GROUP BY a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+=" 	        , c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc2, c1.fe_doc";
                strSQL+="        UNION ALL ";
                //AJUSTE
                strSQL+="        SELECT a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c2.co_emprelpedembimp, c2.co_locrelpedembimp, c2.co_tipdocrelpedembimp, c2.co_docrelpedembimp ";     
                strSQL+="             , c2.co_emp AS co_empIngImp, c2.co_loc AS co_locIngImp, c2.co_tipDoc AS co_tipDocIngImp, c2.co_doc AS co_docIngImp, c2.tx_numDoc2 AS tx_numIngImp, c2.fe_doc AS fe_docIngImp";
                strSQL+="        FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1 ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                strSQL+="        INNER JOIN tbr_CabMovInv AS d1 ON c1.co_emp=d1.co_emp AND c1.co_loc=d1.co_loc AND c1.co_tipdoc=d1.co_tipDoc AND c1.co_doc=d1.co_doc";
                strSQL+="        INNER JOIN tbm_CabMovInv as c2 ON c2.co_emp=d1.co_empRel AND c2.co_loc=d1.co_locRel AND c2.co_tipdoc=d1.co_tipDocRel AND c2.co_doc=d1.co_docRel)";
////                strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
////                strSQL+="        WHERE d1.st_reg='A'" ;
//////                if (!Cadenaitems.equals(""))
//////                {
//////                    strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
//////                    strSQL+=  " WHERE d1.st_reg='A'";
//////                }
//////                    
//////                else
//////                {
//////                    strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
//////                    strSQL+="        WHERE d1.st_reg='A'" ;                   
//////                } 
                
                if (!Cadenaitems.equals(""))//modificado
                {
                    strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    strSQL+=  " WHERE d1.st_reg='A'";
                }
                    
                else
                {
                     if (validaTodo)
                        strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv ) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                     else
                        strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  ='' ) AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    //strSQL+=  " INNER JOIN(select TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm   from tbm_inv where TX_CODALT2  ='') AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm \n";
                    strSQL+=  " WHERE d1.st_reg='A'";                   
                }                 
                strSQL+="        AND c1.co_tipDoc IN(select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+"";
                strSQL+="                            and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV+")";
                strSQL+="        AND c1.st_reg IN ('A')"; //26Ene2018: Se agrega validación para que solo muestre costos de items de los ajustes activos, tbm_cabMovInv <>'O'. 
                strSQL+="        AND (CASE WHEN b1.st_Reg IS NULL THEN 'A' ELSE b1.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
////                strSQL+=strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+="        GROUP BY a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c2.co_emprelpedembimp, c2.co_locrelpedembimp, c2.co_tipdocrelpedembimp, c2.co_docrelpedembimp"; 	
 	        strSQL+="               , c2.co_emp, c2.co_loc, c2.co_tipDoc, c2.co_doc, c2.tx_numDoc2, c2.fe_doc";
                strSQL+="   ) AS x";
                strSQL+="   GROUP BY x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="          , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+="   ORDER BY x.fe_docIngImp DESC LIMIT 15";
                
                strSQL+=") AS x";
                strSQL+=" ORDER BY x.fe_docIngImp ASC, x.tx_numIngImp";
//                System.out.println("cargarCabeceraIngresosImportacion: " + strSQL);
                rstIngImp=stmIngImp.executeQuery(strSQL);
                arlDatIngImp.clear();
                for(int i=0; rstIngImp.next(); i++){
                    arlRegIngImp=new ArrayList();
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP,               "" + rstIngImp.getString("co_empIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC,               "" + rstIngImp.getString("co_locIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC,           "" + rstIngImp.getString("co_tipDocIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC,               "" + rstIngImp.getString("co_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_NUM,                   "" + rstIngImp.getString("tx_numIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegIngImp.add(INT_ARL_ING_IMP_EST_COL,               "");
                    arlRegIngImp.add(INT_ARL_ING_IMP_FEC,                   "" + rstIngImp.getString("fe_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP_PED_EMB,       "" + rstIngImp.getString("co_emprelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC_PED_EMB,       "" + rstIngImp.getString("co_locrelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC_PED_EMB,   "" + rstIngImp.getString("co_tipdocrelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC_PED_EMB,       "" + rstIngImp.getString("co_docrelpedembimp"));
                    arlDatIngImp.add(arlRegIngImp);
                }
                conIngImp.close();
                conIngImp=null;
                rstIngImp.close();
                rstIngImp=null;
                stmIngImp.close();
                stmIngImp=null;
//                System.out.println("fin--");
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
 
   private boolean cargarCabeceraPedidosEmbarcados(){
        boolean blnRes=true;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strAux="";
                //Filtrar por "Item".
////                if (objVenConItm01.isCondicionAplicada())
////                {
////                    strAux+=objVenConItm01.getCondicionesSQL();
////                }

                strSQL="";
                strSQL+="SELECT x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+=" , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+="   FROM(";
                strSQL+="          SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                strSQL+="         , e1.nd_can, e1.nd_preUni";
                strSQL+="         , e1.nd_cosUni, e1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                strSQL+="         , d1.co_emp AS co_empPedEmb, d1.co_loc AS co_locPedEmb, d1.co_tipdoc AS co_tipDocPedEmb, d1.co_doc AS co_docPedEmb";
                strSQL+="      , (CASE WHEN d1.tx_numDoc4 IS NOT NULL THEN d1.tx_numDoc4 ELSE d1.tx_numDoc2 END) AS tx_numPedEmb ";
                
                
                
                strSQL+="         , d1.fe_doc AS fe_docPedEmb, d1.fe_arr AS fe_arrPedEmb, d1.co_ctaAct, d1.co_ctaPas";
                strSQL+=" 	, c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp";
                strSQL+="         FROM (tbm_cabPedEmbImp AS d1 LEFT OUTER JOIN tbm_cabMovInv AS c1";
                strSQL+=" 	      ON d1.co_emp=c1.co_emprelpedembimp AND d1.co_loc=c1.co_locrelpedembimp AND d1.co_tipDoc=c1.co_tipdocrelpedembimp AND d1.co_doc=c1.co_docrelpedembimp";
                strSQL+=" 	      AND c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND c1.co_mnu IN("+objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") )";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS e1 ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                
////                if (!Cadenaitems.equals(""))
////                {
////                    strSQL+=  " INNER JOIN ((( select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr  from tbm_inv  where TX_CODALT2 in  (" + Cadenaitems+")) AS a1";    
////                    strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
////                    strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////                    strSQL+="         WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";
////                }
////                    
////                else
////                {
////                    strSQL+=" 	INNER JOIN (( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
////                    strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////                    strSQL+="         WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";                    
////                }


                if (!Cadenaitems.equals(""))//MODIFICADO
                {
                    strSQL+=  " INNER JOIN ((( select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr  from tbm_inv  where TX_CODALT2 in  (" + Cadenaitems+")) AS a1";    
                    strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
                    strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+="         WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";
                }
                    
                else
                {
                    if (validaTodo)
                        strSQL+=  " INNER JOIN ((( select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr  from tbm_inv ) AS a1";
                    else
                        strSQL+=  " INNER JOIN ((( select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr  from tbm_inv  where TX_CODALT2 ='') AS a1";
                  //  strSQL+=  " INNER JOIN ((( select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr  from tbm_inv  where TX_CODALT2 ='') AS a1";    
                    strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
                    strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+="         WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";                   
                }
                
                
////                strSQL+=" 	INNER JOIN (( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
////                strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////                strSQL+="         WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";
                
                
                
////                strSQL+="" + strAux;                
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                
                strSQL+=" 	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+=" 	, d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc";
                strSQL+=" 	, e1.nd_can, e1.nd_preUni, e1.nd_cosUni, e1.co_reg, a2.tx_desCor";
                strSQL+=" 	, d1.co_ctaAct, d1.co_ctaPas, d1.fe_arr";
                strSQL+=" 	, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, d1.tx_numDoc2, d1.fe_doc";
                strSQL+=" 	ORDER BY d1.fe_doc DESC";
                strSQL+="   ) AS x";
                strSQL+="   WHERE x.co_empIngImp IS NULL";
                strSQL+="   GROUP BY x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+="   ORDER BY x.fe_docPedEmb DESC LIMIT 15";
                strSQL+=") AS x";
                strSQL+=" ORDER BY x.fe_docPedEmb ASC, x.tx_numPedEmb";
                
                
//                System.out.println("cargarCabeceraPedidosEmbarcados: " + strSQL);
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                
                
                arlDatPedEmb.clear();
                obtenerpedidosEmbarcadosGP();
////////                int  i=0;
////////                for (i=0; i<arlDatPEDGP.size();i++) 
////////                {
////////                    arlRegPedEmb=new ArrayList();
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_EMP)) ;
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_TIP_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_GP));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_FEC_ARRI));//quite el valor de la fecha del documento y puse Fecha de arribo
////////                    arlDatPedEmb.add(arlRegPedEmb);
////////                    //Margen
////////                    arlRegPedEmb=new ArrayList();
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_EMP));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_TIP_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_DOC));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_GP));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
////////                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + objUti.getStringValueAt(arlDatPEDGP, i, INT_ARL_PED_FEC_ARRI));//quite el valor de la fecha del documento y puse Fecha de arribo
////////                    arlDatPedEmb.add(arlRegPedEmb);
////////                    
////////                }
                
                        
                for(int i=0; rstPedEmb.next(); i++){
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + rstPedEmb.getString("co_empPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + rstPedEmb.getString("co_locPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + rstPedEmb.getString("co_tipDocPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + rstPedEmb.getString("co_docPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + rstPedEmb.getString("tx_numPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + rstPedEmb.getString("fe_arrPedEmb"));//quite el valor de la fecha del documento y puse Fecha de arribo
                    arlDatPedEmb.add(arlRegPedEmb);
                    //Margen
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + rstPedEmb.getString("co_empPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + rstPedEmb.getString("co_locPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + rstPedEmb.getString("co_tipDocPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + rstPedEmb.getString("co_docPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + rstPedEmb.getString("tx_numPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + rstPedEmb.getString("fe_arrPedEmb"));//quite el valor de la fecha del documento y puse Fecha de arribo
                    arlDatPedEmb.add(arlRegPedEmb);
                }                
                conPedEmb.close();
                conPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                
//                System.out.println("arlDatPedEmb: " + arlDatPedEmb.toString());
                
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
   
    /*24062025_inicio*/
   private void obtenerpedidosEmbarcadosGP ()
   {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQLGP="",strAuxGP="";
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());             
            if(conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                strSQLGP="";  
                strSQLGP+="SELECT DISTINCT (pednot.pedido) AS tx_numPedEmb, \n";
                strSQLGP+="       SUBSTRING( \n";
                strSQLGP+="                 cab.ccidocumento,  \n";
                strSQLGP+="                 CHARINDEX('-',  cab.ccidocumento, CHARINDEX('-',  cab.ccidocumento, CHARINDEX('-',  cab.ccidocumento) + 1) + 1) + 1, \n";                
                strSQLGP+="                 CHARINDEX('/',  cab.ccidocumento) - CHARINDEX('-',  cab.ccidocumento, CHARINDEX('-',  cab.ccidocumento, CHARINDEX('-',  cab.ccidocumento) + 1) + 1) - 1 \n";    
                strSQLGP+="                ) AS co_docPedEmb, \n";                    
                strSQLGP+="       0 AS co_empPedEmb,1 AS co_locPedEmb,190 AS co_tipDocPedEmb,CONVERT(date, cab.dfeCreacion) AS dfeCreacion ,cab.dfeArribo \n";                     
                strSQLGP+="FROM Dynamics2021..view_det_pedi_not AS pednot  \n";
                strSQLGP+="     INNER JOIN tbm_inv AS inv ON (pednot.item = inv.tx_itm) \n";
                strSQLGP+="     LEFT JOIN view_listaPropiedadesItems AS lis ON (pednot.item = lis.CodigoArticulo) \n";                
                strSQLGP+="     LEFT JOIN Dynamics2021..TB_ORDEN_COMPRA_IMPORT_CAB as cab on (pednot.orden = cab.cciOrdenCompra) \n";                
                strSQLGP+="     LEFT JOIN tuval..PM00200 as prov on (cab.cciProveedor =prov.VENDORID) \n";
                strSQLGP+="WHERE cab.dfeCreacion  >'2024-01-01 09:22:59.830' and pednot.pedido not in  ('P-1516-0-E','P-1993-0','P-1908-0','P-1929-0','P-1909-0')  \n";                
                strSQLGP+="      AND (lis.CodigoArticulo like '%I' OR lis.CodigoArticulo like '%S' )  AND pednot.co_tipdoc =190 \n";      
                
                 if  (!optTod.isSelected())
                            strSQLGP+="      AND  inv.tx_codalt2 in ("+Cadenaitems +") \n";      
                     
                rstLoc=stmLoc.executeQuery(strSQLGP);
                arlDatPEDGP= new ArrayList();
                while (rstLoc.next())
                {
                    arlRegPEDGP = new ArrayList();
                    arlRegPEDGP.add(INT_ARL_PED_EMP,rstLoc.getString("co_empPedEmb"));                    
                    arlRegPEDGP.add(INT_ARL_PED_LOC,rstLoc.getString("co_locPedEmb"));                    
                    arlRegPEDGP.add(INT_ARL_PED_TIP_DOC,rstLoc.getString("co_tipDocPedEmb"));                    
                    arlRegPEDGP.add(INT_ARL_PED_DOC,rstLoc.getString("co_docPedEmb"));                    
                    arlRegPEDGP.add(INT_ARL_PED_GP,rstLoc.getString("tx_numPedEmb"));
                    arlRegPEDGP.add(INT_ARL_PED_FEC_CREA,rstLoc.getString("dfeCreacion"));
                    arlRegPEDGP.add(INT_ARL_PED_FEC_ARRI,rstLoc.getString("dfeArribo"));
                    arlDatPEDGP.add(arlRegPEDGP);
                }  
               
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc =null;
                
                
            }            
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }      
   }
   
   /*24062025_fin*/
   
   

   

   private boolean cargarCabeceraNotasPedidos(){
        boolean blnRes=true;
        Connection conNotPed;
        Statement stmNotPed;
        ResultSet rstNotPed;
        try{
            conNotPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNotPed!=null){
                stmNotPed=conNotPed.createStatement();
                
                strSQL ="";
                strSQL+=" SELECT x.co_empNotPed, x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.fe_arrNotPed, x.co_ctaAct, x.co_ctaPas";
                strSQL+="      , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_empNotPed, x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.fe_arrNotPed, x.co_ctaAct, x.co_ctaPas";
                strSQL+="        , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_empNotPed, a1.co_locNotPed, a1.co_tipDocNotPed, a1.co_docNotPed, a1.tx_numNotPed, a1.fe_docNotPed, a1.fe_arrNotPed, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+=" 	     , b2.co_empPedEmb, b2.co_locPedEmb, b2.co_tipDocPedEmb, b2.co_docPedEmb, a1.st_notpedlis, a1.st_cie";
                strSQL+=" 	FROM(";
                strSQL+="              SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                strSQL+=" 		    , e1.nd_can, e1.nd_preUni, e1.nd_cosUni, e1.co_reg AS co_regNotPed, a2.tx_desCor AS tx_desCorUniMed";
                strSQL+=" 		    , d1.co_emp AS co_empNotPed, d1.co_loc AS co_locNotPed, d1.co_tipdoc AS co_tipDocNotPed, d1.co_doc AS co_docNotPed";
                strSQL+="                   , (CASE WHEN d1.tx_numDoc3 IS NOT NULL THEN d1.tx_numDoc3 ELSE d1.tx_numDoc2 END) AS tx_numNotPed ";
                strSQL+=" 		    , d1.fe_doc AS fe_docNotPed, d1.fe_arr AS fe_arrNotPed, d1.co_ctaAct, d1.co_ctaPas, d1.st_notpedlis, d1.st_cie,a1.tx_codalt2";
                strSQL+=" 	       FROM (tbm_cabNotPedImp AS d1 LEFT OUTER JOIN tbm_mesembimp AS g1 ON d1.co_mesarr=g1.co_mesemb)";
                
                
////////                if (Cadenaitems!="")
////////                {
////////                    strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
////////                    strSQL+=" 			    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////////                    strSQL+=" 			    INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
////////                }
////////                    
////////                else
////////                {
////////                    strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
////////                    strSQL+=" 			    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////////                    strSQL+=" 			    INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";                  
////////                }   


                if (!Cadenaitems.equals(""))//MODIFICADO
                {
                    strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr   from tbm_inv where TX_CODALT2  in (" + Cadenaitems+")) AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
                    strSQL+=" 			    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 			    INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                }
                    
                else
                {
                    if(validaTodo)
                      strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr   from tbm_inv ) AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
                    else
                      strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr   from tbm_inv where TX_CODALT2  ='') AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
                    //strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN ((select  TX_CODALT2,co_emp,co_itm,tx_codAlt,tx_nomitm,co_uni,nd_pesitmkgr   from tbm_inv where TX_CODALT2  ='') AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
                    strSQL+=" 			    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 			    INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";                  
                } 
                
////                strSQL+=" 	       INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
////                strSQL+=" 			    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
////                strSQL+=" 			    INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 	       )";
                strSQL+=" 	       ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                strSQL+=" 	       WHERE d1.st_reg='A' AND (d1.st_doc='A' OR d1.st_doc IS NULL)";
                strSQL+="              AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                  
                //Filtrar por "Item".
////                if (objVenConItm01.isCondicionAplicada())
////                {
////                    strSQL+=objVenConItm01.getCondicionesSQL();
////                }
                
                strSQL+=" 	) AS a1";
                strSQL+=" 	LEFT OUTER JOIN(";
                strSQL+="              SELECT c1.co_emp AS co_empPedEmb, c1.co_loc AS co_locPedEmb, c1.co_tipDoc AS co_tipDocPedEmb, c1.co_doc AS co_docPedEmb";
                strSQL+=" 		    , e2.co_locRel, e2.co_tipDocRel, e2.co_docRel, e2.co_regRel";
                strSQL+=" 	       FROM tbm_cabPedEmbImp AS c1 INNER JOIN tbm_detPedEmbImp AS e2";
                strSQL+=" 	       ON c1.co_emp=e2.co_emp AND c1.co_loc=e2.co_loc AND c1.co_tipDoc=e2.co_tipDoc AND c1.co_doc=e2.co_doc";
                strSQL+=" 	       WHERE  c1.st_reg IN ('A') AND (c1.st_doc='A' OR c1.st_doc IS NULL) ";
                strSQL+=" 	) AS b2";
                strSQL+="	ON a1.co_empNotPed=b2.co_empPedEmb AND a1.co_locNotPed=b2.co_locRel AND a1.co_tipDocNotPed=b2.co_tipDocRel AND a1.co_docNotPed=b2.co_docRel AND a1.co_regNotPed=b2.co_regRel";
                strSQL+="	WHERE a1.st_cie NOT IN('S')";
                
                //Filtrar por "Item".//1112023
////                if (objVenConItm01.isCondicionAplicada())
////                {
////                    strSQL+=objVenConItm01.getCondicionesSQL();
////                }


//////                if (Cadenaitems!="")
//////                    strSQL+=  "  AND a1.TX_CODALT2 in (" + Cadenaitems+")";
//////                else
//////                    strSQL+=  ""; 

                if (!Cadenaitems.equals(""))//MODIFICADO
                    strSQL+=  "  AND a1.TX_CODALT2 in (" + Cadenaitems+")";
                else
                {
                    if(validaTodo)
                        strSQL+=  " "; 
                    else
                        strSQL+=  "  AND a1.TX_CODALT2 =''";                     
                }
                    
////                     strSQL+=  "  AND a1.TX_CODALT2 =''"; 
                
                strSQL+=" 	GROUP BY a1.co_empNotPed, a1.co_locNotPed, a1.co_tipDocNotPed, a1.co_docNotPed, a1.tx_numNotPed, a1.fe_docNotPed, a1.fe_arrNotPed, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+=" 	       , b2.co_empPedEmb, b2.co_locPedEmb, b2.co_tipDocPedEmb, b2.co_docPedEmb, a1.st_notpedlis, a1.st_cie";
                strSQL+=" 	ORDER BY a1.fe_arrNotPed DESC";
                strSQL+="   ) AS x";
                strSQL+="   WHERE x.co_empPedEmb IS NULL";
                strSQL+="   GROUP BY x.co_empNotPed, x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.fe_arrNotPed, x.co_ctaAct, x.co_ctaPas";
                strSQL+="          , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                //strSQL+="   ORDER BY x.fe_arrNotPed DESC LIMIT 15";
                strSQL+="   ORDER BY x.fe_arrNotPed DESC";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.fe_arrNotPed ASC, x.tx_numNotPed";
//                System.out.println("cargarCabeceraNotasPedidos: " + strSQL);
                rstNotPed=stmNotPed.executeQuery(strSQL);
                arlDatNotPed.clear();
                for(int i=0; rstNotPed.next(); i++){
                    arlRegNotPed=new ArrayList();
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_EMP,               "" + rstNotPed.getString("co_empNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_LOC,               "" + rstNotPed.getString("co_locNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_TIP_DOC,           "" + rstNotPed.getString("co_tipDocNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_DOC,               "" + rstNotPed.getString("co_docNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_NUM,                   "" + rstNotPed.getString("tx_numNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegNotPed.add(INT_ARL_NOT_PED_EST_COL,               "");
                    arlRegNotPed.add(INT_ARL_NOT_PED_FEC,                   "" + rstNotPed.getString("fe_arrNotPed"));  //Muestra fecha arribo formato dd/MM/YYYY
                    arlDatNotPed.add(arlRegNotPed);
                    //Margen
                    arlRegNotPed=new ArrayList();
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_EMP,               "" + rstNotPed.getString("co_empNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_LOC,               "" + rstNotPed.getString("co_locNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_TIP_DOC,           "" + rstNotPed.getString("co_tipDocNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_DOC,               "" + rstNotPed.getString("co_docNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_NUM,                   "" + rstNotPed.getString("tx_numNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegNotPed.add(INT_ARL_NOT_PED_EST_COL,               "");
                    arlRegNotPed.add(INT_ARL_NOT_PED_FEC,                   "" + rstNotPed.getString("fe_arrNotPed")); //Muestra fecha arribo formato dd/MM/YYYY
                    arlDatNotPed.add(arlRegNotPed);
                }                
                conNotPed.close();
                conNotPed=null;
                rstNotPed.close();
                rstNotPed=null;
                stmNotPed.close();
                stmNotPed=null;
//                System.out.println("arlDatNotPed: " + arlDatNotPed.toString());
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }


   /**
    * precio venta real kilo
    * precio objetivo kilo
    * precio lista calculado
    * @return 
    */
    boolean ocultaColumnasPrecios(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinPre-1); j>=intNumColIniPre; j--){
                if(chkMosValKil.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    boolean ocultaColumnaCosto(){
        boolean blnRes=true;
        try{
            for(int j=intNumColIniFac; j<intNumColFinFac; j++)
            {
                if(j==intNumColFinFac)
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
////                else
////                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    
    
    
    boolean ocultaColumnasMargenPrecio(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinCanMarPreLisNew-1); j>=intNumColIniCanMarPreLisNew; j--){
                if(chkMosValKil.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   
   /**
    * factor costo
    * precio venta factor
    * margen con factor
    * @return 
    */
    boolean ocultaColumnasFactor(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinMarFac-1); j>=intNumColIniMarFac; j--){
                if(chkMosValFac.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
            for(int j=(intNumColFinFac-1); j>=intNumColIniFac; j--){
                if(chkMosValFac.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    boolean ocultaColumnasCostoUnitario(){
        boolean blnRes=true;
        try{            
            for(int j=(intNumColFinCosUni-1); j>=intNumColIniCosUni; j--){
                objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    boolean ocultaColumnasColoreo(){
        boolean blnRes=true;
        try{            
                objTblMod.addSystemHiddenColumn(intNumColIniModCabDet, tblDat);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    
    
    boolean encerarColumnasCostoUnitario(){
        boolean blnRes=true;

        return blnRes;
    }
    
    
    
    
    private boolean calcularValoresKilos(int intTipOpeCalKil){
        boolean blnRes=true;
        BigDecimal bgdPesUni=new BigDecimal("0");
        BigDecimal bgdCosUni=new BigDecimal("0");
        BigDecimal bgdIngImp=new BigDecimal("0");
        BigDecimal bgdPedEmb=new BigDecimal("0");
        BigDecimal bgdNotPed=new BigDecimal("0");
        BigDecimal bgdMarNotPed=new BigDecimal("0");
        
        BigDecimal bgdPreVtaFac=new BigDecimal("0");
        BigDecimal bgdPreVtaReaObjLisKil=new BigDecimal("0");
        
        try{
            if(chkMosValKil.isSelected()){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                    //ingresos por importacion
                    for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                        bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdIngImp.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //pedido embarcado
                    for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                        bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdPedEmb.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //nota de pedido
                    for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                        bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdNotPed.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    

                    
                    //costo unitario
                    for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                        bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdCosUni.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    

                }
            }
            else{
//                System.out.println("intTipOpeCalKil: " + intTipOpeCalKil);
                if(intTipOpeCalKil==1){//solo se valida si no esta seleccionado porque si no esta seleccionado no debe reversar nada porque los valores a mostrar deben ser los obtenidos en la DB
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                        //ingresos por importacion
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdIngImp.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //pedido embarcado
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPedEmb.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //nota de pedido
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdNotPed.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }
                        
                        
                        //costo unitario
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdCosUni.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }
                    }
                }

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
private boolean calcularValoresKilosFl(int intTipOpeCalKil){
        boolean blnRes=true;
        BigDecimal bgdPesUni=new BigDecimal("0");
        BigDecimal bgdCosUni=new BigDecimal("0");
        BigDecimal bgdIngImp=new BigDecimal("0");
        BigDecimal bgdPedEmb=new BigDecimal("0");
        BigDecimal bgdNotPed=new BigDecimal("0");
        BigDecimal bgdMarNotPed=new BigDecimal("0");
        
        BigDecimal bgdPreVtaFac=new BigDecimal("0");
        BigDecimal bgdPreVtaReaObjLisKil=new BigDecimal("0");
        
        try{
            if(chkMosValKil.isSelected()){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                    //ingresos por importacion
                    for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                        bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdIngImp.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //pedido embarcado
                    for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                        bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdPedEmb.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //nota de pedido
                    for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                        bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdNotPed.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    

                    
                    //costo unitario
                    for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                        bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdCosUni.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    

                }
            }
            else{
//                System.out.println("intTipOpeCalKil: " + intTipOpeCalKil);
                if(intTipOpeCalKil==1){//solo se valida si no esta seleccionado porque si no esta seleccionado no debe reversar nada porque los valores a mostrar deben ser los obtenidos en la DB
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                        //ingresos por importacion
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdIngImp.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //pedido embarcado
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPedEmb.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //nota de pedido
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdNotPed.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }
                        
                        
                        //costo unitario
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdCosUni.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }
                    }
                }

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    /**
     * Función que permite recalcular los valores si hay un cambio en la columna del peso
     * @return 
     */
    private boolean calcularReversionValoresKilos(){
        boolean blnRes=true;
        BigDecimal bgdPesUniOld=new BigDecimal("0");
        BigDecimal bgdPesUniNew=new BigDecimal("0");
        
        BigDecimal bgdCosUni=new BigDecimal("0");
        BigDecimal bgdIngImp=new BigDecimal("0");
        BigDecimal bgdPedEmb=new BigDecimal("0");
        BigDecimal bgdNotPed=new BigDecimal("0");
        
        BigDecimal bgdPreVta=new BigDecimal("0");
        BigDecimal bgdPreVtaFac=new BigDecimal("0");
        //BigDecimal bgdPreVtaReaKil=new BigDecimal("0"), bgdPreVtaObjKil=new BigDecimal("0"), bgdPreVtaLisCal=new BigDecimal("0");
        BigDecimal bgdPreVtaReaObjLisKil=new BigDecimal("0");
        try{
            if(chkMosValKil.isSelected()){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesUniOld=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX).toString()));
                    bgdPesUniNew=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
                    if(bgdPesUniOld.compareTo(bgdPesUniNew)!=0){//peso db es diferente a peso tbl
                     
                        //ingresos por importacion
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdIngImp.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);//SE REVERSA
                                bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdIngImp.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);//SE RECALCULA
                            }                            
                        }                    
                        //pedido embarcado
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPedEmb.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdPedEmb.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //nota de pedido
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdNotPed.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdNotPed.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //costo unitario
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdCosUni.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdCosUni.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //precio venta factura(factor)
                        for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                            if(j==(intNumColIniFac+1)){
                                bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                                else{
                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaFac.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                    bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                    objTblMod.setValueAt(bgdPreVtaFac.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                                }
                            }
                        }
                        
                        //precio venta real kilo, precio objetivo kilo, precio lista calculado
                        for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                            if(j==(intNumColIniPre+1)){
                                bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                                else{
                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaReaObjLisKil.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                    bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                    objTblMod.setValueAt(bgdPreVtaReaObjLisKil.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                                }
                            }
                        }
                        
                        //se actualiza el peso auxiliar con el nuevo peso ingresado
                    objTblMod.setValueAt(bgdPesUniNew, i, INT_TBL_DAT_PES_KGR_AUX);
                    
                    
                        
                    }                    
                }
            }

            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    
    /**
     * Esta función calcula el costo unitario con factor de la fila.
     */
    private void calcularCosUniFac()
    {
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdFacCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniFac)));
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniFac+1));
        }
    }
    
    
    
    
    
    /**
     * Esta función calcula el costo unitario con factor de toda la tabla
     */
    private void calcularCosUniFacTbl()
    {
        BigDecimal bgdCosUni, bgdFacCosUni;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniFac)));
//            System.out.println("bgdCosUni: " + bgdCosUni);
//            System.out.println("bgdFacCosUni: " + bgdFacCosUni);
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesBaseDatos()), i, (intNumColIniFac+1));
        }
    }
    
    
    
    
    /**
     * Función que permite calcular el margen con factor de la fila seleccionada.
     * Este valor debe recalcularse cuando se modifica el factor del costo
     */
    private void calcularMarFac(){
        
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdPreVtaMarUtiPreVta;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdPreVtaMarUtiPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1))));
            
            if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniMarFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniMarFac);
                }
                else{
                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniMarFac);
                }
            }
        }
    }
    
    
    /**
     * Función que permite calcular el margen con factor de toda la tabla
     * Este valor debe recalcularse cuando se modifica el factor del costo
     */
    private void calcularMarFacTbl(){
        BigDecimal bgdCosUni, bgdPreVtaMarUtiPreVta;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdPreVtaMarUtiPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniFac+1))));
            
            if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniMarFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniMarFac);
                }
                else{
                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, intNumColIniMarFac);
                }
            }
        }
    }
    
    
    /**
     * Función que permite calcular el margen de lista.
     * Este valor debe recalcularse cuando se modifica el precio objetivo kilo
     */
    private void calcularMarPreObjKil(){
        

    }
    
    
  
    private void calcularMarNotPed(){
        int k=0;
        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
        if(arlDatNotPed.size()>0){
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                k=0;
                for(int j=intNumColIniNotPed; j<intNumColFinNotPed; j++){
                    if(!((k%2)==0)){//devuelve impar
                        bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (j-1))));//obtener el costo
                        bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                        bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                            }
                            else{
                                if(chkMosValKil.isSelected())
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                                else
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, j);//intNumColIniMarNotPed
                            }
                        }
                    }
                    k++;
                }
            }
        }

    }
    
    
    
    
    /**
     * Función que permite calcular el margen del pedido embarcado mas reciente.
     * 
     */
    private void calcularMarPedEmb(){
        int k=0;
        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
        if(arlDatPedEmb.size()>0){
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                k=0;
                for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb; j++){
                    if(!((k%2)==0)){//devuelve impar
                        bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (j-1))));//obtener el costo
                        bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                        bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                            }
                            else{
                                if(chkMosValKil.isSelected()){
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                                }
                                else{
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                }
                                objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, j);//intNumColIniMarNotPed
                            }
                        }
                    }
                    k++;
                }
            }
        }

    }
    
    
    
    private void calcularPreVtaReaKil(){
        int intFilSel[], i;
        BigDecimal bgdPesUni, bgdPreVta,bgdIVAGP;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar)));
            
            bgdIVAGP=objUti.redondearBigDecimal(((BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_IVA))).add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui
            
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+1));
                }
                else{
                    //objTblMod.setValueAt("" + ((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos())), intFilSel[i], (intNumColIniPreReaMar+1));
                    objTblMod.setValueAt("" + ((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bgdIVAGP)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos())), intFilSel[i], (intNumColIniPreReaMar+1));
                }
            }
        }
    }
        
    
    private void calcularPreLisCal(){
        int intFilSel[], i;
        BigDecimal bgdPesUni, bgdPreVtaObj, bgdPreLisCal, bgdFacIva,bgdIVAGP;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdIVAGP=objUti.redondearBigDecimal(((BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_IVA))).add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui            
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            bgdPreVtaObj=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPre)));
            bgdPreLisCal=new BigDecimal("0");
            
            if (bgdPreVtaObj==null || bgdPreVtaObj.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
                }
                else{
////                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj.multiply(new BigDecimal("100")));
                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj);
                    bgdPreLisCal=bgdPreLisCal.multiply(bgdPesUni);
                    //bgdFacIva=objUti.redondearBigDecimal( (objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    
                    //bgdPreLisCal=bgdPreLisCal.divide( (bgdFacIva.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());
                    bgdPreLisCal=bgdPreLisCal.divide( (bgdIVAGP.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());//chiqui
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdPreLisCal, objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniPre+1));
                }
            }
        }
    }
    
    
    private void calcularPreLisCalConsulta(){
        BigDecimal bgdPesUni, bgdPreVtaObj, bgdPreLisCal, bgdFacIva,bgdIVAGP;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
            bgdPreVtaObj=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPre)));
            bgdPreLisCal=new BigDecimal("0");
            bgdIVAGP=objUti.redondearBigDecimal(((BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_IVA))).add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui                        
            
            if (bgdPreVtaObj==null || bgdPreVtaObj.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniPre+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniPre+1));
                }
                else{
                    //objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaObj.divide(bdePorIva, objParSis.getDecimalesBaseDatos())).divide(BigDecimal.valueOf(0.75), objParSis.getDecimalesBaseDatos())).multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, (intNumColIniPre+1));
////                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj.multiply(new BigDecimal("100")));
                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj);
                    
                    bgdPreLisCal=bgdPreLisCal.multiply(bgdPesUni);
                    //bgdFacIva=objUti.redondearBigDecimal( (objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    //bgdPreLisCal=bgdPreLisCal.divide( (bgdFacIva.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());*
                    bgdPreLisCal=bgdPreLisCal.divide( (bgdIVAGP.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdPreLisCal, objParSis.getDecimalesBaseDatos()), i, (intNumColIniPre+1));
                }
            }
        }
        
    }
    
    
    
    
    
    
    /**
     * Función que permite calcular el precio de lista basado en el margen que ingrese el usuario de la fila seleccionada
     */
    private void calcularPrecioListaCalculado_Margen(){
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdMarPreLis, bgdPreLisCal, bgdPesUni,bgdIVAGP;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdMarPreLis=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCanMarPreLisNew)));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            bgdIVAGP=objUti.redondearBigDecimal(((BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_IVA))).add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui
            
            //bgdPreLisCal=objUti.redondearBigDecimal((bgdCosUni.divide((BigDecimal.valueOf(0.75).multiply(new BigDecimal("1").subtract(bgdMarPreLis.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP )))), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesBaseDatos());
            
            bgdPreLisCal=BigDecimal.valueOf(0.0);
            bgdPreLisCal=bgdPreLisCal.add( bgdMarPreLis.multiply(new BigDecimal("0.01"))    );
            bgdPreLisCal=BigDecimal.valueOf(1).subtract(bgdPreLisCal);
            bgdPreLisCal=BigDecimal.valueOf(0.75).multiply(bgdPreLisCal);
            bgdPreLisCal=bgdCosUni.divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos());
            bgdPreLisCal=objUti.redondearBigDecimal((bgdPreLisCal.multiply(bgdPesUni)), objParSis.getDecimalesBaseDatos());
                   
            //precio lista calculado
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
            }
            else{
                if (bgdMarPreLis==null || bgdMarPreLis.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdPreLisCal, intFilSel[i], (intNumColIniPre+1));
                }
            }

            //precio objetivo en kilos
            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniPre);
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniPre);
                }
                else{
                    //objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniPre);
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bgdIVAGP)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniPre);
                }
            }                
        }
    }
    
    
    /**
     * Función que permite calcular el precio de lista basado en el margen que ingrese el usuario de toda la tabla
     */
    private void calcularPrecioListaCalculado_MargenTbl(){
        BigDecimal bgdCosUni, bgdMarPreLis, bgdPreLisCal, bgdPesUni,bgdIVAGP;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdMarPreLis=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCanMarPreLisNew)));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
            bgdIVAGP=objUti.redondearBigDecimal(((BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_IVA))).add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );//chiqui
            
            bgdPreLisCal=BigDecimal.valueOf(0.0);
            bgdPreLisCal=bgdPreLisCal.add( bgdMarPreLis.multiply(new BigDecimal("0.01"))    );
            bgdPreLisCal=BigDecimal.valueOf(1).subtract(bgdPreLisCal);
            bgdPreLisCal=BigDecimal.valueOf(0.75).multiply(bgdPreLisCal);
            bgdPreLisCal=bgdCosUni.divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos());
            bgdPreLisCal=objUti.redondearBigDecimal((bgdPreLisCal.multiply(bgdPesUni)), objParSis.getDecimalesBaseDatos());
                   
            //precio lista calculado
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniPre+1));
            }
            else{
                if (bgdMarPreLis==null || bgdMarPreLis.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniPre+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdPreLisCal, i, (intNumColIniPre+1));
                }
            }

            //precio objetivo en kilos
            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniPre);
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniPre);
                }
                else{
//                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), i, intNumColIniPre);
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bgdIVAGP)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), i, intNumColIniPre);
                }
            }                
        }
    }
    
    

    /**
     * Función que permite calcular el margen según el precio de lista calculado
     */
    private void calcularMarPreLisCal(){
        int i;
        BigDecimal bgdCosUni, bgdPreLisCal, bgdPesUni;
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdPreLisCal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniPre+1))));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniCanMarPreLisNew);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniCanMarPreLisNew);
                }
                else{
                    if(chkMosValKil.isSelected())
                        bgdPreLisCal=(bgdPreLisCal.multiply(BigDecimal.valueOf(0.75))).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                    else
                        bgdPreLisCal=bgdPreLisCal.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreLisCal.subtract(bgdCosUni)).multiply(BigDecimal.valueOf(100)).divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos())), objParSis.getDecimalesBaseDatos()), i, intNumColIniCanMarPreLisNew);
                    
                    
                    
                }
            }
        }
    }
    

    

    /**
     * Función que permite calcular el margen según el precio de lista calculado
     */
    private void calcularMarPreLisCal_row(){
        int intFilSel[], i;
        intFilSel=tblDat.getSelectedRows();
        BigDecimal bgdCosUni, bgdPreLisCal, bgdPesUni;
        for (i=0; i<intFilSel.length; i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdPreLisCal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniPre+1))));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));

            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniCanMarPreLisNew);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniCanMarPreLisNew);
                }
                else{
                    if(chkMosValKil.isSelected())
                        bgdPreLisCal=(bgdPreLisCal.multiply(BigDecimal.valueOf(0.75))).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                    else
                        bgdPreLisCal=bgdPreLisCal.multiply(BigDecimal.valueOf(0.75));
                    
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreLisCal.subtract(bgdCosUni)).multiply(BigDecimal.valueOf(100)).divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos())), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniCanMarPreLisNew);
                }
            }
        }
    }
    

    /**
     * Función que permite calcular el costo unitario con factor basado en el margen 
     * que ingrese el usuario de la fila seleccionada
     */
    private void calcularCostoUnitarioFactor_Margen(){
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdMarFac, bgdCosUniFac;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdMarFac=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniMarFac)));
            
            bgdCosUniFac=BigDecimal.valueOf(0.0);
            bgdCosUniFac=bgdCosUniFac.add( bgdMarFac.multiply(new BigDecimal("0.01"))    );
            bgdCosUniFac=BigDecimal.valueOf(1).subtract(bgdCosUniFac);
            bgdCosUniFac=BigDecimal.valueOf(0.75).multiply(bgdCosUniFac);
            bgdCosUniFac=bgdCosUni.divide(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
            bgdCosUniFac=objUti.redondearBigDecimal(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
                   
            //costo unitario con factor
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+1));
            }
            else{
                if (bgdMarFac==null || bgdMarFac.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdCosUniFac, intFilSel[i], (intNumColIniFac+1));
                }
            }

            //factor costo
            if (bgdCosUniFac==null || bgdCosUniFac.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniFac);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  bgdCosUniFac.divide(bgdCosUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniFac);
                }
            }                
        }
    }

    /**
     * Función que permite calcular el costo unitario con factor basado en el margen 
     * que ingrese el usuario de toda la tabla
     */
    private void calcularCostoUnitarioFactor_MargenTbl(){
        BigDecimal bgdCosUni, bgdMarFac, bgdCosUniFac;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdMarFac=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniMarFac)));
            
            bgdCosUniFac=BigDecimal.valueOf(0.0);
            bgdCosUniFac=bgdCosUniFac.add( bgdMarFac.multiply(new BigDecimal("0.01"))    );
            bgdCosUniFac=BigDecimal.valueOf(1).subtract(bgdCosUniFac);
            bgdCosUniFac=BigDecimal.valueOf(0.75).multiply(bgdCosUniFac);
            bgdCosUniFac=bgdCosUni.divide(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
            bgdCosUniFac=objUti.redondearBigDecimal(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
                   
            //costo unitario con factor
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniFac+1));
            }
            else{
                if (bgdMarFac==null || bgdMarFac.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniFac+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdCosUniFac, i, (intNumColIniFac+1));
                }
            }

            //factor costo
            if (bgdCosUniFac==null || bgdCosUniFac.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniFac);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  bgdCosUniFac.divide(bgdCosUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), i, intNumColIniFac);
                }
            }                
        }
    }
    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if(isItmModDat()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        return true;
    }
    
    
    /**
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veces
     * <BR> false: Caso contrario
     */
    private boolean isItmModDat(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                System.out.println("i: " + i);
//                System.out.println("1: " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT));
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
//                    System.out.println("j: " + j);
//                    System.out.println("2: " + objTblMod.getValueAt(j,INT_TBL_DAT_COD_ALT));
                    if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ALT, j, INT_TBL_DAT_COD_ALT)){
                        blnRes=true;
                        break;
                    }

                }
                if(blnRes)
                    break;
            }            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasCheckMargen_antes(){ //chiqui esta si
        boolean blnRes=true;
        strAux="";

        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        
        blnMarTodChkMarActual=true;  // Inicializa

        intNumColAddCheck=1;//
        intNumColIniChk=intNumColFinMarFac;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            //<editor-fold defaultstate="collapsed" desc="/*Seccion: Formato*/">
            objTblCelRenLblColDinChekMa=new ZafTblCelRenChk();
            
            objTblCelEdiTxtChekMa=new ZafTblCelEdiChk();
            objTblCelEdiTxtChekMa.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChekMa.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });
            //</editor-fold>
            
            // Crear columnas
            for(int i=0; i<intNumColAddCheck; i++){
                String strSubTit="Selección";
                tbc=new javax.swing.table.TableColumn(intNumColIniChk+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinChekMa);
                tbc.setCellEditor(objTblCelEdiTxtChekMa);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Actual");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
                tblDat.getTableHeader().setReorderingAllowed(false);
                
            }          
                        
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int j, intNumFil;
                    intNumFil=objTblMod.getRowCountTrue();
                    if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChk)
                    {
                        bgdValor = BigDecimal.ZERO;                         

                        if (blnMarTodChkMarActual)
                        {                            
                            //Marcar las columnas.
                            for (j=0; j<intNumFil; j++)
                            {
                                objTblMod.setChecked(false, j, intNumColIniChk);
                                objTblMod.setChecked(false, j, intNumColIniChkPrimero);
                                objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                                
                                bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChk-3)));
                                if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                                {
                                    bgdValor = BigDecimal.ZERO;
                                    objTblMod.setChecked(false, j, intNumColIniChk); //No marca columna Margen Actual
                                }
                                else
                                {
                                    objTblMod.setChecked(true,  j, intNumColIniChk); //Chequea columna Margen Actual
                                    objTblMod.setChecked(false, j, intNumColIniChkPrimero); //Desmarca columna Primer Pedido
                                    objTblMod.setChecked(false, j, intNumColIniChkUltimo);  //Desmarca columna Ultimo Pedido
                                    objTblMod.setChecked(false, j, intNumColIniChkPrimeraImporta);  //Desmarca columna Primera Importacion
                                    objTblMod.setChecked(false, j, intNumColIniChkUltimaImporta);  //Desmarca columna Ultima Importacion                                    
                                }
                            }
                            blnMarTodChkMarActual=false;
                        }
                        else
                        {
                            for (j=0; j<intNumFil; j++)
                            {
                                objTblMod.setChecked(false, j, intNumColIniChk);
                            }
                            blnMarTodChkMarActual=true;
                        }
                    }
                }
            });

            intNumColFinChk=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
    
    private boolean agregarColumnasCheckMargen(){ //PRIMER CHECK
        boolean blnRes=true;
        strAux="";

        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        
        blnMarTodChkMarActual=true;  // Inicializa

        intNumColAddCheck=1;//
        intNumColIniChk=intNumColFinMarFac;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            //<editor-fold defaultstate="collapsed" desc="/*Seccion: Formato*/">
            objTblCelRenLblColDinChekMa=new ZafTblCelRenChk();
            
            objTblCelEdiTxtChekMa=new ZafTblCelEdiChk();
            
            
            objTblCelEdiTxtChekMa.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1)));
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChekMa.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);                        
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
                        
                         
                        
                    intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1))); 
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
//////////////////                        if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
//////////////////                        {
//////////////////                            if((ne_Grupo !=0) && (ne_DetGrupo ==0))
//////////////////                            {
//////////////////////                                if (!blnCabecera)
//////////////////                                    
//////////////////                                    //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
//////////////////                                   //ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,2,intNumColIniFac+1);
//////////////////                                    ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,2,intNumColIniPreReaMar);
//////////////////                            }
//////////////////                            else
//////////////////                            {
//////////////////                                if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
//////////////////                                    objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
//////////////////                                    
//////////////////                            }
//////////////////                                                         
//////////////////                            
//////////////////                        } 
//////////////////                        else
//////////////////                        {
////////////////////////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
//////////////////                            
//////////////////                            //if(intNumColIniPreReaMar==ne_colPrec)
//////////////////                                //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
//////////////////                                jLabelLey.setVisible(true);
//////////////////                        }  
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,2,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChk);
                    }
                    else
                    {
                    intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio,nd_preAct;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        nd_preAct=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniPreReaMar)));
                        
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChk);
                        
                        
                    }
                    
                    
//////////                    else
//////////                    {
//////////                        
//////////                        intFil=tblDat.getSelectedRow();    
//////////                        BigDecimal bgdPrecio;
//////////                        int ne_Grupo =0;
//////////                        int ne_DetGrupo =0;
//////////                        //int intCol = tblDat.getSelectedColumn();
//////////                      //  int contador =0;
//////////
//////////                            bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniFac+1)));
//////////                            ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
//////////                            ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
//////////                            if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
//////////                            {
//////////                                if((ne_Grupo !=0) && (ne_DetGrupo ==0))
//////////                                {
//////////    ////                                if (!blnCabecera)
//////////
//////////                                        //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
//////////                                        ProcesoColoreoEliminarDetalleGruposoloCabe(ne_Grupo,bgdPrecio,2,intNumColIniFac+1);
//////////                                }
//////////                                else
//////////                                {
//////////                                    if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
//////////                                        objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
//////////
//////////                                }
//////////
//////////
//////////                            } 
//////////                            else
//////////                            {
//////////    //////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
//////////
//////////                                //if(intNumColIniPreReaMar==ne_colPrec)
//////////                                    //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
//////////                                    jLabelLey.setVisible(true);
//////////                            }                        
//////////                        
//////////                        
//////////                        
////////////                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
//////////                    }
                }
            });
            //</editor-fold>
            
            // Crear columnas
            for(int i=0; i<intNumColAddCheck; i++){
                String strSubTit="Selección";
                tbc=new javax.swing.table.TableColumn(intNumColIniChk+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinChekMa);
                tbc.setCellEditor(objTblCelEdiTxtChekMa);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Actual");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
                tblDat.getTableHeader().setReorderingAllowed(false);
                
            }          
             
            
            

            intNumColFinChk=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
    private boolean agregarColumnasCheckMargenPrimero(){
        boolean blnRes=true;
        strAux="";

        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddCheckPrimero=1;//
        intNumColIniChkPrimero=intNumColFinMarPrimero;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinChekMaPrimero=new ZafTblCelRenChk();
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtChekMaPrimero=new ZafTblCelEdiChk();
            objTblCelEdiTxtChekMaPrimero.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
               int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkPrimero-2)));
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChekMaPrimero.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);                        
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta); 
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
                        
                    intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkPrimero-2)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,3,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkPrimero);

                        
                    }
                    else
                    {
intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkPrimero);
                    }
                }
            });
            
            for(int i=0; i<intNumColAddCheckPrimero; i++){
                String strSubTit="Selección";
                tbc=new javax.swing.table.TableColumn(intNumColIniChkPrimero+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinChekMaPrimero);
                tbc.setCellEditor(objTblCelEdiTxtChekMaPrimero);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Primer");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
                tblDat.getTableHeader().setReorderingAllowed(false);
            }
            intNumColFinChkPrimero=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }    
    
    private boolean agregarColumnasCheckOtrosFiltros(){
            boolean blnRes=true;
            strAux="";

            javax.swing.table.TableColumn tbc;
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpTit=null;
////            sdsds
            intNumColAddCheckOtro=1;//intNumColIniChkFactor
            intNumColIniChkOtro=intNumColFinCanMarPreLisNew;//numero de columnas que tiene el modelo antes de adicionar las columnas
            try{
                objTblCelRenLblColDinChekOtro=new ZafTblCelRenChk();

                //Configurar JTable: Editor de celdas.
                objTblCelEdiTxtChekOtro=new ZafTblCelEdiChk();
                objTblCelEdiTxtChekOtro.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkOtro-3)));
                } 
                
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objTblCelEdiTxtChekOtro.isChecked())
                        {
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);                        
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                  
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkOtro-2)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
////                        if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
////                        {
////                            if((ne_Grupo !=0) && (ne_DetGrupo ==0))
////                            {
////////                                if (!blnCabecera)
////                                    
////                                    //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
////                                    //ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,7,intNumColIniChkOtro-3);
////                                    ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,7,intNumColIniPreReaMar);
////                            }
////                            else
////                            {
////                                if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
////                                    objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
////                                    
////                            }
////                                                         
////                            
////                        } 
////                        else
////                        {
//////////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
////                            
////                            //if(intNumColIniPreReaMar==ne_colPrec)
////                                //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
////                                jLabelLey.setVisible(true);
////                        }                            
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,7,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkOtro); 
                        }
                        else
                        {
intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkOtro); 
                        }
                    }
                });

                for(int i=0; i<intNumColAddCheckOtro; i++){
                    String strSubTit="Otro";
                    tbc=new javax.swing.table.TableColumn(intNumColIniChkOtro+i);
                    tbc.setHeaderValue(strSubTit);

                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblColDinChekOtro);
                    tbc.setCellEditor(objTblCelEdiTxtChekOtro);

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);

                    if(i==0){
                        objTblHeaColGrpTit=new ZafTblHeaColGrp("LSC");//
                        objTblHeaColGrpTit.setHeight(16);
                    }
                    objTblHeaColGrpTit.add(tbc);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);

                    tblDat.getTableHeader().setReorderingAllowed(false);
                }
                intNumColFinChkOtro=objTblMod.getColumnCount();

                objTblHeaGrp=null;
                objTblHeaColGrpTit=null;

            }
            catch(Exception e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;

        }     
    
    private boolean agregarColumnasCheckMargenUltimo(){
        boolean blnRes=true;
        strAux="";

        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddCheckUltimo=1;//
        intNumColIniChkUltimo=intNumColFinMarUltimo;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinChekMaUltimo=new ZafTblCelRenChk();
            
            objTblCelEdiTxtChekMaUltimo=new ZafTblCelEdiChk();
            objTblCelEdiTxtChekMaUltimo.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkUltimo-2)));
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtChekMaUltimo.isChecked())
                    {
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro);                        
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);                        
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta); 
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
                    
                    
                    
                    
                    intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil,intNumColIniChkUltimo-2)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
////////                        if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
////////                        {
////////                            if((ne_Grupo !=0) && (ne_DetGrupo ==0))
////////                            {
////////////                                if (!blnCabecera)
////////                                    
////////                                    //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
////////                                    //ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,4,intNumColIniChkUltimo-2);
////////                                    ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,4,intNumColIniPreReaMar);
////////                            }
////////                            else
////////                            {
////////                                if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
////////                                    objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
////////                                    
////////                            }
////////                                                         
////////                            
////////                        } 
////////                        else
////////                        {
//////////////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
////////                            
////////                            //if(intNumColIniPreReaMar==ne_colPrec)
////////                                //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
////////                                jLabelLey.setVisible(true);
////////                        }  
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,4,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkUltimo);  
                        }
                    else
                    {
intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkUltimo);                        
                    }
                }
            });  
            
            
            for(int i=0; i<intNumColAddCheckUltimo; i++){
                String strSubTit="Selección";
                tbc=new javax.swing.table.TableColumn(intNumColIniChkUltimo+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinChekMaUltimo);
                tbc.setCellEditor(objTblCelEdiTxtChekMaUltimo);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ultimo");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
                tblDat.getTableHeader().setReorderingAllowed(false);            
              
            }
            
            /*
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    
                        if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkUltimo)
                        {
                             
                            int j=0, intNumFil=0;
                            intNumFil=objTblMod.getRowCountTrue();
                            
                            if (blnMarTodChkTblEmpUltimo)
                            {
                                for (j=0; j<intNumFil; j++)
                                {
                                    bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkUltimo-1)));
                                    if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                                    {
                                      bgdValor = BigDecimal.ZERO;
                                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                                    }
                                    else
                                    {
                                        objTblMod.setChecked(true, j, intNumColIniChkUltimo);
                                        objTblMod.setChecked(false, j, intNumColIniChk);
                                        objTblMod.setChecked(false, j, intNumColIniChkPrimero);                                        
                                    }
                                        
                                }
                                blnMarTodChkTblEmpUltimo=false;
                            }
                            else
                            {
                                for (j=0; j<intNumFil; j++)
                                {
                                    objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                                }
                                blnMarTodChkTblEmpUltimo=true;
                            }
                        }
                    }
            });
            */
            
            intNumColFinChkUltimo=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }    

    private boolean agregarColumnasCheckMargenPrimeraImportacion()//chiqui27112023
    {
            boolean blnRes=true;
            strAux="";

            javax.swing.table.TableColumn tbc;
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpTit=null;

            intNumColAddCheckPrimeraImporta=1;//
            intNumColIniChkPrimeraImporta=intNumColFinMarPrimeraImporta;//numero de columnas que tiene el modelo antes de adicionar las columnas

            try{
                objTblCelRenLblColDinChekMaPrimeraImporta=new ZafTblCelRenChk();

                objTblCelEdiTxtChekMaPrimeraImporta=new ZafTblCelEdiChk();
                objTblCelEdiTxtChekMaPrimeraImporta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkPrimeraImporta-2)));
                } 
                
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objTblCelEdiTxtChekMaPrimeraImporta.isChecked())
                        {
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);   
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro); 
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimaImporta); 
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
                            
intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkPrimeraImporta-2)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
//////////                        if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
//////////                        {
//////////                            if((ne_Grupo !=0) && (ne_DetGrupo ==0))
//////////                            {
//////////////                                if (!blnCabecera)
//////////                                    
//////////                                    //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
//////////                                    //ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,5,intNumColIniChkPrimeraImporta-2);
//////////                                    ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,5,intNumColIniPreReaMar);
//////////                            }
//////////                            else
//////////                            {
//////////                                if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
//////////                                    objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
//////////                                    
//////////                            }
//////////                                                         
//////////                            
//////////                        } 
//////////                        else
//////////                        {
////////////////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
//////////                            
//////////                            //if(intNumColIniPreReaMar==ne_colPrec)
//////////                                //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
//////////                                jLabelLey.setVisible(true);
//////////                        }  
                        
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,5,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkPrimeraImporta); 
                        }
                        else
                        {
intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkPrimeraImporta);                            
                        }
                    }
                });  


                for(int i=0; i<intNumColAddCheckPrimeraImporta; i++){
                    String strSubTit="Selección";
                    tbc=new javax.swing.table.TableColumn(intNumColIniChkPrimeraImporta+i);
                    tbc.setHeaderValue(strSubTit);

                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblColDinChekMaPrimeraImporta);
                    tbc.setCellEditor(objTblCelEdiTxtChekMaPrimeraImporta);

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);

                    if(i==0){
                        objTblHeaColGrpTit=new ZafTblHeaColGrp("Penultimo");//
                        objTblHeaColGrpTit.setHeight(16);
                    }
                    objTblHeaColGrpTit.add(tbc);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);

                    tblDat.getTableHeader().setReorderingAllowed(false);            

                }

                /*
                tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {

                            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==intNumColIniChkUltimo)
                            {

                                int j=0, intNumFil=0;
                                intNumFil=objTblMod.getRowCountTrue();

                                if (blnMarTodChkTblEmpUltimo)
                                {
                                    for (j=0; j<intNumFil; j++)
                                    {
                                        bgdValor= BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(j, intNumColIniChkUltimo-1)));
                                        if ((bgdValor==null) || (bgdValor.compareTo(BigDecimal.ZERO)==0))
                                        {
                                          bgdValor = BigDecimal.ZERO;
                                            objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                                        }
                                        else
                                        {
                                            objTblMod.setChecked(true, j, intNumColIniChkUltimo);
                                            objTblMod.setChecked(false, j, intNumColIniChk);
                                            objTblMod.setChecked(false, j, intNumColIniChkPrimero);                                        
                                        }

                                    }
                                    blnMarTodChkTblEmpUltimo=false;
                                }
                                else
                                {
                                    for (j=0; j<intNumFil; j++)
                                    {
                                        objTblMod.setChecked(false, j, intNumColIniChkUltimo);
                                    }
                                    blnMarTodChkTblEmpUltimo=true;
                                }
                            }
                        }
                });
                */

                intNumColFinChkPrimeraImporta=objTblMod.getColumnCount();
                //intNumColFinFac=objTblMod.getColumnCount();

                objTblHeaGrp=null;
                objTblHeaColGrpTit=null;

            }
            catch(Exception e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;

        }     
    
    private boolean agregarColumnasCheckMargenUltimaImportacion()//chiqui27112023
    {
            boolean blnRes=true;
            strAux="";

            javax.swing.table.TableColumn tbc;
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpTit=null;

            intNumColAddCheckUltimaImporta=1;//
            intNumColIniChkUltimaImporta=intNumColFinMarUltimaImporta;//numero de columnas que tiene el modelo antes de adicionar las columnas

            try{
                objTblCelRenLblColDinChekMaUltimaImporta=new ZafTblCelRenChk();

                objTblCelEdiTxtChekMaUltimaImporta=new ZafTblCelEdiChk();
                objTblCelEdiTxtChekMaUltimaImporta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               int intFil=-1;
                BigDecimal nd_preAct ;                
                

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    System.out.println("");
                    nd_preAct =BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkUltimaImporta-2)));
                } 
                
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objTblCelEdiTxtChekMaUltimaImporta.isChecked())
                        {
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChk);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimero);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkUltimo);   
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkOtro); 
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkPrimeraImporta);
                            objTblMod.setChecked(false, tblDat.getSelectedRow(), intNumColIniChkFactor);
intFil=tblDat.getSelectedRow();    
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        bgdPrecio=BigDecimal.valueOf( objUti.parseDouble(objTblMod.getValueAt(intFil, intNumColIniChkUltimaImporta-2)));
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
////////                        if (((ne_Grupo !=0) && (ne_DetGrupo ==0)) || ((ne_Grupo ==0) && (ne_DetGrupo ==0)))
////////                        {
////////                            if((ne_Grupo !=0) && (ne_DetGrupo ==0))
////////                            {
////////////                                if (!blnCabecera)
////////                                    
////////                                    //ProcesoColoreoVisualizaDetalleGrupo(ne_Grupo,bgdPrecio);     
////////                                    //ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,6,intNumColIniChkUltimaImporta-2);
////////                                    ProcesoColoreoVisualizaDetalleGruposoloCabe(ne_Grupo,bgdPrecio,6,intNumColIniPreReaMar);
////////                            }
////////                            else
////////                            {
////////                                if ((ne_Grupo ==0) && (ne_DetGrupo ==0))
////////                                    objTblMod.setValueAt("S", intFil, intNumColIniModCabDet);
////////                                    
////////                            }
////////                                                         
////////                            
////////                        } 
////////                        else
////////                        {
//////////////////                            objTblMod.setValueAt(nd_preAct, intFil, intNumColIniFac+1);
////////                            
////////                            //if(intNumColIniPreReaMar==ne_colPrec)
////////                                //mostrarMsgInf("<HTML>No se puede modificar un item de detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
////////                                jLabelLey.setVisible(true);
////////                        }  
                            procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,6,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkUltimaImporta);                         
                        }
                        else
                        {
intFil=tblDat.getSelectedRow();   
                    BigDecimal bgdPrecio;
                    int ne_Grupo =0;
                    int ne_DetGrupo =0;
                    String str_llave="",str_valor="";
                    //int intCol = tblDat.getSelectedColumn();
                  //  int contador =0;
                    
                        ne_Grupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_GRP).toString()));
                        ne_DetGrupo=(Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_DET).toString()));
                        
                        
 
                            int intNumCol = intNumColIniPreReaMar;
                            str_llave = intFil +","+intNumCol;
                            str_valor=obtenerTblHist(str_llave);   
                                // Convierte el valor anterior a BigDecimal (con validación)
                            if (str_valor != null && !str_valor.equals("")) 
                                bgdPrecio = new BigDecimal(str_valor);
                            else 
                                bgdPrecio = BigDecimal.ZERO;                        
                        procesoAsignacionEstado (ne_Grupo,ne_DetGrupo,bgdPrecio,8,intNumColIniPreReaMar,intFil,nd_preAct,intNumColIniChkUltimaImporta);                            
                        }
                    }
                });  


                for(int i=0; i<intNumColAddCheckUltimaImporta; i++){
                    String strSubTit="Selección";
                    tbc=new javax.swing.table.TableColumn(intNumColIniChkUltimaImporta+i);
                    tbc.setHeaderValue(strSubTit);

                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblColDinChekMaUltimaImporta);
                    tbc.setCellEditor(objTblCelEdiTxtChekMaUltimaImporta);

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);

                    if(i==0){
                        objTblHeaColGrpTit=new ZafTblHeaColGrp("Ultimo");//
                        objTblHeaColGrpTit.setHeight(16);
                    }
                    objTblHeaColGrpTit.add(tbc);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);

                    tblDat.getTableHeader().setReorderingAllowed(false);            

                }

                

                intNumColFinChkUltimaImporta=objTblMod.getColumnCount();
                //intNumColFinFac=objTblMod.getColumnCount();

                objTblHeaGrp=null;
                objTblHeaColGrpTit=null;

            }
            catch(Exception e){
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;

        }    
    
    
    
}