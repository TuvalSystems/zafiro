/*
 * ZafImp36.java
 *
 * Created on March 15, 2012
 */
package Importaciones.ZafImp36;
import Importaciones.ZafImp33.ZafImp33;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import javax.swing.JScrollBar;
/**
 *
 * @author  Gigi
 */
public class ZafImp36 extends javax.swing.JInternalFrame 
{    
    //Constantes: Columnas del JTable Pedidos Embarcados
    private static final int INT_TBL_DAT_PED_EMB_LIN=0;
    private static final int INT_TBL_DAT_PED_EMB_CHK=1;
    private static final int INT_TBL_DAT_PED_EMB_CHK_AUX=2;
    private static final int INT_TBL_DAT_PED_EMB_COD_EMP=3;
    private static final int INT_TBL_DAT_PED_EMB_COD_LOC=4;
    private static final int INT_TBL_DAT_PED_EMB_COD_TIP_DOC=5;
    private static final int INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC=6;
    private static final int INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC=7;
    private static final int INT_TBL_DAT_PED_EMB_COD_DOC=8;
    private static final int INT_TBL_DAT_PED_EMB_NUM_DOC=9;
    private static final int INT_TBL_DAT_PED_EMB_NUM_PED=10;
    private static final int INT_TBL_DAT_PED_EMB_FEC_DOC=11;
    private static final int INT_TBL_DAT_PED_EMB_COD_IMP=12;
    private static final int INT_TBL_DAT_PED_EMB_NOM_IMP=13;
    private static final int INT_TBL_DAT_PED_EMB_COD_EXP=14;
    private static final int INT_TBL_DAT_PED_EMB_NOM_EXP=15;
    private static final int INT_TBL_DAT_PED_EMB_TOT_FOB_CFR=16;
    private static final int INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO=17;//PRORATEO
    private static final int INT_TBL_DAT_PED_EMB_VAL_CFR_ARA=18;
    private static final int INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO=19;//PRORATEO
    private static final int INT_TBL_DAT_PED_EMB_NUM_PED_VER=20;
    
    //Constantes: Columnas del JTable Cargos a Pagar
    private static final int INT_TBL_DAT_CAR_PAG_LIN=0;
    private static final int INT_TBL_DAT_CAR_PAG_COD_REG=1;
    private static final int INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG=2;
    private static final int INT_TBL_DAT_CAR_PAG_COD_CAR_PAG=3;
    private static final int INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG=4;
    private static final int INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG=5;
    private static final int INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX=6;
    private static final int INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON=7;
    
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_LIN=0;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP=1;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC=2;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC=3;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC=4;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC=5;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED=6;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR=7;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO=8;//PRORATEO
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA=9;
    private static final int INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO=10;//PRORATEO
    //Las columnas de los cargos son dinámicas
        
    //Variables del sistema.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc, dtpFecEmb, dtpFecPue, dtpFecArr;
    private ZafDocLis objDocLis;
    private java.util.Date datFecAux;
    private ZafTblMod objTblModPedEmb, objTblModCarPag, objTblModPedEmbCarPagConImp;
    private ZafTblMod objTblModTotPedEmbCarPagConImp;//Totales
    private ZafTblPopMnu objTblPopMnuPedEmb, objTblPopMnuCarPag, objTblPopMnuPedEmbCarPagConImp;
    private ZafTblFilCab objTblFilCab;
    private ZafMouMotAdaPedEmb objMouMotAdaPedEmb;
    private ZafMouMotAdaCarPag objMouMotAdaCarPag;
    private ZafMouMotAdaPedEmbCarPagCon objMouMotAdaPedEmbCarPagConImp;
    private ZafTblBus objTblBusPedEmb, objTblBusCarPag, objTblBusPedEmbCarPagConImp;
    private ZafTblCelRenLbl objTblCelRenLblPedEmb, objTblCelRenLblCarPag, objTblCelRenLblPedEmbCarPagConImp, objTblCelRenLblColAddImp, objTblCelRenLblGrl;
    private ZafTblCelRenLbl objTblCelRenLblTotPedEmb, objTblCelRenLblTotPedEmbCarPagConImp;
    private ZafTblOrd objTblOrdPedEmb, objTblOrdPedEmbCarPagConImp;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut    objTblCelRenBut;
    private ZafTblCelEdiTxt objTblCelEdiTxt;    
    private ZafSegMovInv objSegMovInv;
    private ZafNotCorEle objNotCorEle;
    private ZafImp objZafImp; 
    private ZafVenCon vcoTipDoc;
    private String strDesCorTipDoc, strDesLarTipDoc;

    private Object objCodSegInsAnt;    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnHayCam;        
    private int intSig=1;
    private String strAux;
    private String strSQL;
    private String strNumDoc;
    private String strVer="v1.0";
    private int intCodHisPedCon;
    
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIP_DOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;
    
    private int intIndPedCon=0;
    private int intNumDocIngHoy;
    private ZafImp36_01 objImp36_01;
    private ArrayList arlDatCab_objImp36_01;//Cabecera: contendra la cabecera
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    //ArrayList objImp36_01: Por los pedidos previos
    private int intNumColEst;
    private int intNumColAddImp, intNumColIniImp, intNumColFinImp;
    
    private ArrayList arlRegCarPagImp, arlDatCarPagImp;
    private static final int INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG=0;
    private static final int INT_ARL_CAR_PAG_CON_IMP_NOM_CAR_PAG=1;
    private static final int INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG=2;
    private static final int INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG=3;
    
    private JScrollBar barDatPedEmbCarPagCon, barTotPedEmbCarPagCon;
    private ZafTblTot objTblTotPedEmb;
    
    private ArrayList arlRegInsPedCar, arlDatInsPedCar;
    public static final int INT_ARL_INS_PED_CAR_COD_EMP=0;
    public static final int INT_ARL_INS_PED_CAR_COD_LOC=1;
    public static final int INT_ARL_INS_PED_CAR_COD_TIP_DOC=2;
    public static final int INT_ARL_INS_PED_CAR_COD_DOC=3;
    public static final int INT_ARL_INS_PED_CAR_COD_CAR=4;
    public static final int INT_ARL_INS_PED_CAR_TIP_CAR=5;
    public static final int INT_ARL_INS_PED_CAR_VAL_CAR=6;
    private BigDecimal bgdFleItmCarPagCon;
    private ZafHisTblBasDat objHisTblBasDat;     //Histórico    
    
   private ZafCorEle objCorEle;

    /** Creates new form ZafImp30 */
    public ZafImp36(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatCon=new ArrayList();
            arlDatCarPagImp=new ArrayList();
            arlDatInsPedCar=new ArrayList();
            bgdFleItmCarPagCon=BigDecimal.ZERO;
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }

        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);            
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objDocLis=new ZafDocLis();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objNotCorEle = new ZafNotCorEle(objParSis);  /*Libreria para enviar correos electronicos a usuario.*/
            objSegMovInv=new ZafSegMovInv(objParSis, this);            
            objHisTblBasDat=new ZafHisTblBasDat();
            //ToolBar
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butCarPedEmb);
            
              //ToolBar
            panBar.add(objTooBar);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butInsertarGP);
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(582, 4, 102, 20);
                        
            dtpFecEmb=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecEmb.setText("");
            panCab.add(dtpFecEmb);
            dtpFecEmb.setBounds(582, 24, 102, 20);
            
            dtpFecPue=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecPue.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecPue.setText("");
            panCab.add(dtpFecPue);
            dtpFecPue.setBounds(582, 44, 102, 20);
            
            dtpFecArr=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setText("");
            panCab.add(dtpFecArr);
            dtpFecArr.setBounds(582, 64, 102, 20);

            //////////////////////////////////////////////////////////////////////
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumPedCon.setBackground(objParSis.getColorCamposObligatorios());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());

            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);

            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                }
            });
            
            dtpFecEmb.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                }
            });
            
            dtpFecPue.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                }
            });
            
            dtpFecArr.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                }
            });
            
            vecDat=new Vector();//Almacena los datos
            
            objTooBar.setVisibleAnular(false);// se inactiva por el momento, hasta implementar el núevo proceso de Reversiones (Asiento de diario que antes no existia aqui)
            objTooBar.setVisibleEliminar(false);
            
            configurarTblDatPedEmb();
            configurarTblCarPag();
            configurarTblDatPedEmbCarPagConImp();
            configurarTblTotPedEmbCarPagConImp();
            configurarVenConTipDoc();
            
            butGenCal.setVisible(false);
            if(objParSis.getCodigoUsuario()==1)
                butGenCal.setVisible(true);
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatPedEmb(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(21);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_PED_EMB_LIN,"");
            vecCab.add(INT_TBL_DAT_PED_EMB_CHK, null);
            vecCab.add(INT_TBL_DAT_PED_EMB_CHK_AUX, null);
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_PED_EMB_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_PED_EMB_NOM_IMP,"Importador");
            vecCab.add(INT_TBL_DAT_PED_EMB_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_PED_EMB_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR,"Tot.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO,"Rat.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA,"Val.Cfr.Ara.");
            vecCab.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO,"Rat.Cfr.Ara.");
            vecCab.add(INT_TBL_DAT_PED_EMB_NUM_PED_VER,"Núm.Ped.Ver.");
            

            objTblModPedEmb=new ZafTblMod();
            objTblModPedEmb.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatPedEmb.setModel(objTblModPedEmb);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDatPedEmb.setRowSelectionAllowed(true);
            tblDatPedEmb.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuPedEmb=new ZafTblPopMnu(tblDatPedEmb);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatPedEmb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBusPedEmb=new ZafTblBus(tblDatPedEmb);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatPedEmb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatPedEmb.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CHK_AUX).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NUM_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_IMP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NOM_IMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_EXP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NUM_PED_VER).setPreferredWidth(70);
            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_EMP, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_LOC, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_TIP_DOC, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_DOC, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_IMP, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_EXP, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO, tblDatPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_NUM_PED_VER, tblDatPedEmb);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatPedEmb.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaPedEmb=new ZafMouMotAdaPedEmb();
            tblDatPedEmb.getTableHeader().addMouseMotionListener(objMouMotAdaPedEmb);
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_LIN).setCellRenderer(objTblFilCab);            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CHK).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CHK_AUX).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDatPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CHK).setCellEditor(objTblCelEdiChk);
//            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                BigDecimal bdeValAplEvn=new BigDecimal(0);
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    if (objTblCelEdiChk.isChecked()){
//                    }
//                    else{
//                    }
//                }
//            });
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_PED_EMB_CHK);
            objTblModPedEmb.setColumnasEditables(vecAux);
            vecAux=null;

            objTblCelRenLblPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmb.setTipoFormato(objTblCelRenLblPedEmb.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NUM_DOC).setCellRenderer(objTblCelRenLblPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_FEC_DOC).setCellRenderer(objTblCelRenLblPedEmb);
            objTblCelRenLblPedEmb=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmb.setTipoFormato(objTblCelRenLblPedEmb.INT_FOR_NUM);
            objTblCelRenLblPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).setCellRenderer(objTblCelRenLblPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO).setCellRenderer(objTblCelRenLblPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).setCellRenderer(objTblCelRenLblPedEmb);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO).setCellRenderer(objTblCelRenLblPedEmb);
            objTblCelRenLblPedEmb=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdPedEmb=new ZafTblOrd(tblDatPedEmb);
            
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_PED_EMB_TOT_FOB_CFR, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO};
            objTblTotPedEmb=new ZafTblTot(spnDatPedEmb, spnTotPedEmb, tblDatPedEmb, tblTotPedEmb, intCol);
            
            objTblTotPedEmb.insertMoreRows(1);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    


    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatPedEmbCarPagConImp(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(11);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN,"");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR,"Tot.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO,"Rat.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA,"Val.Cfr.Ara.");
            vecCab.add(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO,"Rat.Cfr.Ara.");
                        
            objTblModPedEmbCarPagConImp=new ZafTblMod();
            objTblModPedEmbCarPagConImp.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatPedEmbCarPagConImp.setModel(objTblModPedEmbCarPagConImp);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDatPedEmbCarPagConImp.setRowSelectionAllowed(true);
            tblDatPedEmbCarPagConImp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuPedEmbCarPagConImp=new ZafTblPopMnu(tblDatPedEmbCarPagConImp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatPedEmbCarPagConImp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBusPedEmbCarPagConImp=new ZafTblBus(tblDatPedEmbCarPagConImp);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatPedEmbCarPagConImp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatPedEmbCarPagConImp.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).setPreferredWidth(100);

            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_EMP, tblDatPedEmb);
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_LOC, tblDatPedEmb);
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_TIP_DOC, tblDatPedEmb);
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_DOC, tblDatPedEmb);
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_IMP, tblDatPedEmb);
//            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_EXP, tblDatPedEmb);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatPedEmbCarPagConImp.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaPedEmbCarPagConImp=new ZafMouMotAdaPedEmbCarPagCon();
            tblDatPedEmbCarPagConImp.getTableHeader().addMouseMotionListener(objMouMotAdaPedEmbCarPagConImp);
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatPedEmbCarPagConImp);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLblPedEmbCarPagConImp=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmbCarPagConImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmbCarPagConImp.setTipoFormato(objTblCelRenLblPedEmbCarPagConImp.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED).setCellRenderer(objTblCelRenLblPedEmbCarPagConImp);
            objTblCelRenLblPedEmbCarPagConImp=null;
            
            objTblCelRenLblPedEmbCarPagConImp=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmbCarPagConImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmbCarPagConImp.setTipoFormato(objTblCelRenLblPedEmbCarPagConImp.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).setCellRenderer(objTblCelRenLblPedEmbCarPagConImp);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).setCellRenderer(objTblCelRenLblPedEmbCarPagConImp);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).setCellRenderer(objTblCelRenLblPedEmbCarPagConImp);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).setCellRenderer(objTblCelRenLblPedEmbCarPagConImp);
            //objTblCelRenLblPedEmbCarPagConImp=null;
                        
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdPedEmbCarPagConImp=new ZafTblOrd(tblDatPedEmbCarPagConImp);
            intNumColEst=objTblModPedEmbCarPagConImp.getColumnCount();
//            System.out.println("intNumColEst: " + intNumColEst);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      

    private boolean configurarTblTotPedEmbCarPagConImp()
    {
        boolean blnRes=true;
        try
        {
            objTblModTotPedEmbCarPagConImp=new ZafTblMod();
            objTblModTotPedEmbCarPagConImp.setHeader(vecCab);
            tblTotPedEmbCarPagConImp.setModel(objTblModTotPedEmbCarPagConImp);

            //Configurar JTable: Establecer tipo de selecciÃ³n.
            tblTotPedEmbCarPagConImp.setRowSelectionAllowed(true);
            tblTotPedEmbCarPagConImp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblTotPedEmbCarPagConImp);
            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotPedEmbCarPagConImp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotPedEmbCarPagConImp.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).setPreferredWidth(100);            
            
            
            
            
            
            //Configurar JTable: Ocultar columnas del sistema.
//            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
//            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
//            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
//            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
//            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModTotPedEmbCarPagConImp.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP, tblTotPedEmb);
//            objTblModTotPedEmbCarPagConImp.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC, tblTotPedEmb);
//            objTblModTotPedEmbCarPagConImp.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC, tblTotPedEmb);
//            objTblModTotPedEmbCarPagConImp.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC, tblTotPedEmb);
                                    
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDatPedEmbCarPagConImp.getColumnCount(); j++){
                tblTotPedEmbCarPagConImp.getColumnModel().getColumn(j).setWidth(tblDatPedEmbCarPagConImp.getColumnModel().getColumn(j).getWidth());
                tblTotPedEmbCarPagConImp.getColumnModel().getColumn(j).setMaxWidth(tblDatPedEmbCarPagConImp.getColumnModel().getColumn(j).getMaxWidth());
                tblTotPedEmbCarPagConImp.getColumnModel().getColumn(j).setMinWidth(tblDatPedEmbCarPagConImp.getColumnModel().getColumn(j).getMinWidth());
                tblTotPedEmbCarPagConImp.getColumnModel().getColumn(j).setPreferredWidth(tblDatPedEmbCarPagConImp.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotPedEmbCarPagConImp.getColumnModel().getColumn(j).setResizable(tblDatPedEmbCarPagConImp.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotPedEmbCarPagConImp.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotPedEmbCarPagConImp=new ZafTblCelRenLbl();
            objTblCelRenLblTotPedEmbCarPagConImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotPedEmbCarPagConImp.setTipoFormato(objTblCelRenLblTotPedEmbCarPagConImp.INT_FOR_NUM);
            objTblCelRenLblTotPedEmbCarPagConImp.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).setCellRenderer(objTblCelRenLblTotPedEmbCarPagConImp);
            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).setCellRenderer(objTblCelRenLblTotPedEmbCarPagConImp);
            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).setCellRenderer(objTblCelRenLblTotPedEmbCarPagConImp);
            tblTotPedEmbCarPagConImp.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).setCellRenderer(objTblCelRenLblTotPedEmbCarPagConImp);
            objTblCelRenLblTotPedEmbCarPagConImp=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDatPedEmbCarPagConImp.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //spnFix.setHorizontalScrollBarPolicy(spnDat.getHorizontalScrollBarPolicy());
            spnTotPedEmbCarPagConImp.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotPedEmbCarPagConImp.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotPedEmbCarPagConImp objTblColModLis=new ZafTblColModLisTotPedEmbCarPagConImp();
            tblDatPedEmbCarPagConImp.getColumnModel().addColumnModelListener(objTblColModLis);

            ZafTblColModLisTotPedEmbCarPagConImp objTblColModLisTot=new ZafTblColModLisTotPedEmbCarPagConImp();
            tblTotPedEmbCarPagConImp.getColumnModel().addColumnModelListener(objTblColModLisTot);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDatPedEmbCarPagCon=spnDatPedEmbCarPagConImp.getHorizontalScrollBar();
            barTotPedEmbCarPagCon=spnTotPedEmbCarPagConImp.getHorizontalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDatPedEmbCarPagCon.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barTotPedEmbCarPagCon.setValue(evt.getValue());
                }
            });
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private class ZafTblColModLisTotPedEmbCarPagConImp implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDatPedEmbCarPagConImp.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDatPedEmbCarPagConImp.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDatPedEmbCarPagConImp.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotPedEmbCarPagConImp.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }
    
    
    
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaPedEmb extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatPedEmb.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_PED_EMB_CHK:
                    strMsg="Pedido Facturado/Embarcado seleccionado";
                    break;
                case INT_TBL_DAT_PED_EMB_CHK_AUX:
                    strMsg="Pedido Facturado/Embarcado seleccionado auxiliar";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_LOC:
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_TIP_DOC:
                    strMsg="Código de Tipo de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC:
                    strMsg="Descripción corta de Tipo de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga de Tipo de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_NUM_PED:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_DAT_PED_EMB_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_IMP:
                    strMsg="Código de importador";
                    break;
                case INT_TBL_DAT_PED_EMB_NOM_IMP:
                    strMsg="Nombre de importador";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_EXP:
                    strMsg="Código de exportador";
                    break;
                case INT_TBL_DAT_PED_EMB_NOM_EXP:
                    strMsg="Exportador";
                    break;
                case INT_TBL_DAT_PED_EMB_TOT_FOB_CFR:
                    strMsg="Total FOB/CFR";
                    break;
                case INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO:
                    strMsg="Ratio FOB/CFR";
                    break;
                case INT_TBL_DAT_PED_EMB_VAL_CFR_ARA:
                    strMsg="Total CFR/ARA";
                    break;
                case INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO:
                    strMsg="Ratio CFR/ARA";
                    break;
                case INT_TBL_DAT_PED_EMB_NUM_PED_VER:
                    strMsg="Número de Pedido con versión";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatPedEmb.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaPedEmbCarPagCon extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatPedEmbCarPagConImp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC:
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC:
                    strMsg="Código de Tipo de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC:
                    strMsg="Descripción corta de Tipo de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR:
                    strMsg="Total FOB/CFR";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO:
                    strMsg="Ratio FOB/CFR";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA:
                    strMsg="Total CFR/ARA";
                    break;
                case INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO:
                    strMsg="Ratio CFR/ARA";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatPedEmbCarPagConImp.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    
    
    
    private boolean configurarTblCarPag(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_CAR_PAG_LIN,"");
            vecCab.add(INT_TBL_DAT_CAR_PAG_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG,"Tip.Car.Pag.");
            vecCab.add(INT_TBL_DAT_CAR_PAG_COD_CAR_PAG,"Cód.Car.Pag.");
            vecCab.add(INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG,"Cargo");
            vecCab.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG,"Valor");
            vecCab.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX,"Val.Aux.");
            vecCab.add(INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON,"Est.Ped.Con.");
            
            objTblModCarPag=new ZafTblMod();
            objTblModCarPag.setHeader(vecCab);
            tblDatCarPag.setModel(objTblModCarPag);

            tblDatCarPag.setRowSelectionAllowed(true);
            tblDatCarPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuCarPag=new ZafTblPopMnu(tblDatCarPag);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatCarPag);
            tblDatCarPag.getColumnModel().getColumn(INT_TBL_DAT_CAR_PAG_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCarPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatCarPag.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON).setPreferredWidth(70);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatCarPag.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCarPag=new ZafMouMotAdaCarPag();
            tblDatCarPag.getTableHeader().addMouseMotionListener(objMouMotAdaCarPag);
            //Configurar JTable: Editor de búsqueda.
            objTblBusCarPag=new ZafTblBus(tblDatCarPag);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG);
            objTblModCarPag.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModCarPag.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCarPag.setTipoFormato(objTblCelRenLblCarPag.INT_FOR_NUM);
            objTblCelRenLblCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).setCellRenderer(objTblCelRenLblCarPag);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX).setCellRenderer(objTblCelRenLblCarPag);
            objTblCelRenLblCarPag=null;

//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrdCarPag=new ZafTblOrd(tblDatCarPag);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCarPag.addSystemHiddenColumn(INT_TBL_DAT_CAR_PAG_COD_REG, tblDatCarPag);
            objTblModCarPag.addSystemHiddenColumn(INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG, tblDatCarPag);
            objTblModCarPag.addSystemHiddenColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX, tblDatCarPag);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG);
            objTblModCarPag.setColumnasEditables(vecAux);
            vecAux=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatCarPag);
            tcmAux.getColumn(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaCarPag extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDatCarPag.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_CAR_PAG_COD_REG:
                    strMsg="Código de registro de consolidado";
                    break;
                case INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG:
                    strMsg="Tipo de cargo a pagar";
                    break;
                case INT_TBL_DAT_CAR_PAG_COD_CAR_PAG:
                    strMsg="Código de cargo a pagar";
                    break;
                case INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG:
                    strMsg="Nombre de cargo a pagar";
                    break;
                case INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG:
                    strMsg="Valor de cargo a pagar";
                    break;
                case INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX:
                    strMsg="Valor de cargo a pagar auxiliar";
                    break;
                case INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON:
                    strMsg="Est.Ped.Con.";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDatCarPag.getTableHeader().setToolTipText(strMsg);
        }
    }
    

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);


        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc1 = new javax.swing.JLabel();
        lblFecDoc2 = new javax.swing.JLabel();
        lblFecDoc3 = new javax.swing.JLabel();
        txtNumPedCon = new javax.swing.JTextField();
        lblNumPed = new javax.swing.JLabel();
        lblFecDoc4 = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        butCarPedEmb = new javax.swing.JButton();
        butGenCal = new javax.swing.JButton();
        butInsertarGP = new javax.swing.JButton();
        panDet = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panDetPedImp = new javax.swing.JPanel();
        spnDatPedEmb = new javax.swing.JScrollPane();
        tblDatPedEmb = new javax.swing.JTable();
        spnTotPedEmb = new javax.swing.JScrollPane();
        tblTotPedEmb = new javax.swing.JTable();
        panDetCarLiq = new javax.swing.JPanel();
        panDatCarPag = new javax.swing.JScrollPane();
        tblDatCarPag = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panDetSur = new javax.swing.JPanel();
        chkMarPedFacPedEmb = new javax.swing.JCheckBox();
        chkCiePedEmb = new javax.swing.JCheckBox();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panCalCarPagCon = new javax.swing.JPanel();
        panCalCarPagConImp = new javax.swing.JPanel();
        spnDatPedEmbCarPagConImp = new javax.swing.JScrollPane();
        tblDatPedEmbCarPagConImp = new javax.swing.JTable();
        spnTotPedEmbCarPagConImp = new javax.swing.JScrollPane();
        tblTotPedEmbCarPagConImp = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setAutoscrolls(true);

        panGen.setAutoscrolls(true);
        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 106));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 120, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(96, 4, 32, 20);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(128, 4, 90, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(218, 4, 220, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 116, 20);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(4, 44, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(128, 44, 90, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(4, 24, 130, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(128, 24, 90, 20);

        lblFecDoc1.setText("Fecha de embarque:");
        lblFecDoc1.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc1);
        lblFecDoc1.setBounds(460, 24, 100, 20);

        lblFecDoc2.setText("Fecha de puerto:");
        lblFecDoc2.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc2);
        lblFecDoc2.setBounds(460, 44, 100, 20);

        lblFecDoc3.setText("Fecha de arribo:");
        lblFecDoc3.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc3);
        lblFecDoc3.setBounds(460, 64, 100, 20);

        txtNumPedCon.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumPedCon.setToolTipText("Número de egreso");
        txtNumPedCon.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedConFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedConFocusLost(evt);
            }
        });
        panCab.add(txtNumPedCon);
        txtNumPedCon.setBounds(128, 64, 90, 20);

        lblNumPed.setText("Número de pedido:");
        lblNumPed.setToolTipText("Número alterno 1");
        panCab.add(lblNumPed);
        lblNumPed.setBounds(4, 64, 120, 20);

        lblFecDoc4.setText("Valor del documento:");
        lblFecDoc4.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc4);
        lblFecDoc4.setBounds(460, 84, 110, 20);
        panCab.add(txtValDoc);
        txtValDoc.setBounds(582, 84, 102, 20);

        butCarPedEmb.setText("Cargar Datos");
        butCarPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarPedEmbActionPerformed(evt);
            }
        });
        panCab.add(butCarPedEmb);
        butCarPedEmb.setBounds(220, 30, 100, 23);

        butGenCal.setText("Genera Calculos");
        butGenCal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGenCalActionPerformed(evt);
            }
        });
        panCab.add(butGenCal);
        butGenCal.setBounds(240, 70, 130, 23);

        butInsertarGP.setText("Enviar Correo: Insertar GP");
        butInsertarGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butInsertarGPActionPerformed(evt);
            }
        });
        panCab.add(butInsertarGP);
        butInsertarGP.setBounds(550, 50, 170, 20);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setAutoscrolls(true);
        panDet.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);

        panDetPedImp.setPreferredSize(new java.awt.Dimension(0, 100));
        panDetPedImp.setLayout(new java.awt.BorderLayout());

        spnDatPedEmb.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Pedidos Facturados/Embarcados"));

        tblDatPedEmb.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatPedEmb.setViewportView(tblDatPedEmb);

        panDetPedImp.add(spnDatPedEmb, java.awt.BorderLayout.CENTER);

        spnTotPedEmb.setPreferredSize(new java.awt.Dimension(452, 20));

        tblTotPedEmb.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTotPedEmb.setViewportView(tblTotPedEmb);

        panDetPedImp.add(spnTotPedEmb, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setTopComponent(panDetPedImp);

        panDetCarLiq.setLayout(new java.awt.BorderLayout());

        panDatCarPag.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Cargos de Liquidación"));

        tblDatCarPag.setModel(new javax.swing.table.DefaultTableModel(
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
        panDatCarPag.setViewportView(tblDatCarPag);

        panDetCarLiq.add(panDatCarPag, java.awt.BorderLayout.CENTER);

        jSplitPane1.setBottomComponent(panDetCarLiq);

        panDet.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 96));
        panObs.setLayout(new java.awt.BorderLayout());

        panDetSur.setPreferredSize(new java.awt.Dimension(100, 40));
        panDetSur.setLayout(null);

        chkMarPedFacPedEmb.setText("Marcar Pedidos Facturados como Pedidos Embarcados");
        panDetSur.add(chkMarPedFacPedEmb);
        chkMarPedFacPedEmb.setBounds(1, 1, 390, 16);

        chkCiePedEmb.setText("Cerrar Pedidos Embarcados");
        chkCiePedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCiePedEmbActionPerformed(evt);
            }
        });
        panDetSur.add(chkCiePedEmb);
        chkCiePedEmb.setBounds(1, 18, 330, 16);

        panObs.add(panDetSur, java.awt.BorderLayout.NORTH);

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("  Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("  Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panCalCarPagCon.setLayout(new java.awt.BorderLayout());

        panCalCarPagConImp.setLayout(new java.awt.BorderLayout());

        tblDatPedEmbCarPagConImp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDatPedEmbCarPagConImp.setViewportView(tblDatPedEmbCarPagConImp);

        panCalCarPagConImp.add(spnDatPedEmbCarPagConImp, java.awt.BorderLayout.CENTER);

        spnTotPedEmbCarPagConImp.setPreferredSize(new java.awt.Dimension(452, 20));

        tblTotPedEmbCarPagConImp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTotPedEmbCarPagConImp.setViewportView(tblTotPedEmbCarPagConImp);

        panCalCarPagConImp.add(spnTotPedEmbCarPagConImp, java.awt.BorderLayout.SOUTH);

        panCalCarPagCon.add(panCalCarPagConImp, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panCalCarPagCon);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    //TODO add your handling code here:

}//GEN-LAST:event_formInternalFrameOpened

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCorTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(1);
        }
    } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
        if (txtDesLarTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(2);
        }
    } else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
    strNumDoc=txtNumDoc.getText();
    txtNumDoc.selectAll();
}//GEN-LAST:event_txtNumDocFocusGained

private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) {
        //actualizarGlo();
    }
}//GEN-LAST:event_txtNumDocFocusLost

    private void txtNumPedConFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedConFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumPedConFocusGained

    private void txtNumPedConFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedConFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumPedConFocusLost

    private void butCarPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarPedEmbActionPerformed
        // TODO add your handling code here:
        if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m')){
            objImp36_01.setCodTipDocRef(txtCodTipDoc.getText());
            objImp36_01.setCodDocRef(txtCodDoc.getText());
        }
        
        
        objImp36_01.cargarPedidosEmbarcados();
        
        if(objImp36_01.getBotonPresionado()==0){//no se ha presionado ningun boton, en este caso NO se debe cambiar nada del modelo
        }
        objImp36_01.setVisible(true);
        if(objImp36_01.getBotonPresionado()==1){ //se presiono el boton ACEPTAR
            //Se coloca la informacion en los arreglos
            arlDatCab_objImp36_01=objImp36_01.getCabeceraSeleccionada();
            //la informacion de los arreglos se la coloca en los campos y en las tablas respectivas
            objImp36_01.getCabeceraSeleccionada();
            //eliminaColumnasAdicionadas();
            cargarPedidosSeleccionados();
            objTblTotPedEmb.calcularTotales();
//            configurarColumnasAdicionar();
//            replicaCarPagConAra();
//            calculaProrateoCarPagConImp();
//            calcularCarPagImp();            
        }
        
    }//GEN-LAST:event_butCarPedEmbActionPerformed

    private void chkCiePedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCiePedEmbActionPerformed
        // TODO add your handling code here:
        if(chkCiePedEmb.isSelected())
            chkMarPedFacPedEmb.setSelected(true);
        
    }//GEN-LAST:event_chkCiePedEmbActionPerformed

    private void butGenCalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGenCalActionPerformed
        // TODO add your handling code here:
        generaCalculos();
    }//GEN-LAST:event_butGenCalActionPerformed

    private void butInsertarGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butInsertarGPActionPerformed
        boolean validaVersion=false;
        
        String mensaje ="SE REQUIERE QUE SE ACTUALICE A GP EL PEDIDO CONDOLIDADO  '"+txtNumPedCon.getText() +"' solicitado por : '"+ objParSis.getNombreUsuario() +"'";
////        objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com",mensaje , txtNumPedCon.getText());
        
     if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), 33, mensaje, txtNumPedCon.getText()  )){
            mostrarMsgInf("<HTML>Se envio el correo para el ingreso/modificación del pedido hacia GP</HTML>");
          //  blnRes=true;
        }        
////        mostrarMsgInf("Solicitud enviada........");
        
////        sss
////        try{
////            if(conExt!=null){
////                con=conExt;
////                //con.setAutoCommit(false);f
////            }
////            else{
////
////                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////                con.setAutoCommit(false);
////            }
////            System.out.println("estado" + objTooBar.getEstado());
////            if ((objTooBar.getEstado() =='j') || (objTooBar.getEstado() =='c'))
////            {
////                if(!isVersionActualizada())
////                {
////                    mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                    dispose();
////                    validaVersion=true;
////                }
////                if (objParSis.getCodigoUsuario()==1)
////                {
////                    if (validaVersion ==false)
////                    {
////                        if(actualizarDatosGP(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),  Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumPedOri.getText()))
////                        {
////                            con.commit();
////                            mostrarMsgInf("Pedido plantado en GP");
////                        }
////                        else
////                        {
////                            mostrarMsgInf("Pedido NO plantado en GP");
////                            objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com", "NO SE PLANTO EL PEDIDO EN PEDIDOS DE  IMPORTACION ", txtNumPedOri.getText());
////                        }
////                    }
////                }
////                else
////                {
////                    String mensaje ="SE REQUIERE QUE SE INSERTE A GP EL PEDIDO '"+txtNumDoc.getText() +"' solicitado por : '"+ objParSis.getNombreUsuario() +"'";
////                    objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com",mensaje , txtNumPedOri.getText());
////                }
////
////            }
////            con.close();
////            con=null;
////        }
////        catch (java.sql.SQLException e){
////            objUti.mostrarMsgErr_F1(this, e);
////            objCorEle.enviarCorreoMasivo("sistemas2@tuvalsa.com", "ERROR EN LA VENTANA DE PEDIDOS DE  IMPORTACION ", e.toString() + " : "+ txtNumPedOri.getText());
////        }
////        catch (Exception e){
////            objUti.mostrarMsgErr_F1(this, e);
////        }

    }//GEN-LAST:event_butInsertarGPActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCarPedEmb;
    private javax.swing.JButton butGenCal;
    private javax.swing.JButton butInsertarGP;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkCiePedEmb;
    private javax.swing.JCheckBox chkMarPedFacPedEmb;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecDoc1;
    private javax.swing.JLabel lblFecDoc2;
    private javax.swing.JLabel lblFecDoc3;
    private javax.swing.JLabel lblFecDoc4;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCalCarPagCon;
    private javax.swing.JPanel panCalCarPagConImp;
    private javax.swing.JScrollPane panDatCarPag;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDetCarLiq;
    private javax.swing.JPanel panDetPedImp;
    private javax.swing.JPanel panDetSur;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDatPedEmb;
    private javax.swing.JScrollPane spnDatPedEmbCarPagConImp;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnTotPedEmb;
    private javax.swing.JScrollPane spnTotPedEmbCarPagConImp;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatCarPag;
    private javax.swing.JTable tblDatPedEmb;
    private javax.swing.JTable tblDatPedEmbCarPagConImp;
    private javax.swing.JTable tblTotPedEmb;
    private javax.swing.JTable tblTotPedEmbCarPagConImp;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumPedCon;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables
    
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtNumPedCon.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }     

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgYesNot(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                            objTooBar.afterInsertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes=objTooBar.modificar();
                            objTooBar.afterModificar();
                        }
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtNumPedCon.setText("");
            txtValDoc.setText("");
            dtpFecDoc.setText("");
            dtpFecEmb.setText("");
            dtpFecArr.setText("");
            dtpFecPue.setText("");

            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);

            txaObs1.setText("");
            txaObs2.setText("");

            objTblModPedEmb.removeAllRows();
            objTblModCarPag.removeAllRows();
            objImp36_01=null;
            chkMarPedFacPedEmb.setSelected(false);
            chkCiePedEmb.setSelected(false);
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_ultDoc2 AS tx_numPedCon";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_ultDoc2 AS tx_numPedCon";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    txtNumPedCon.setText("" + (rst.getInt("tx_numPedCon")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                setNumPedCon();
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio(){
            try{
                if(arlDatCon.size()>0){
                    if(intIndPedCon>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndPedCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndPedCon=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                
        }

        public void clickAnterior() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndPedCon>0){
                        if (blnHayCam){
                            if (isRegPro()) {
                                intIndPedCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndPedCon--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }              
        }

        public void clickSiguiente() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndPedCon < arlDatCon.size()-1){
                        if (blnHayCam){
                            if (isRegPro()) {
                                intIndPedCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndPedCon++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }             
        }

        public void clickFin() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndPedCon<arlDatCon.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndPedCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndPedCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickInsertar() {
            try{
                if((blnHayCam)){
                    isRegPro();
                }
                
                limpiarFrm();
                objImp36_01=new ZafImp36_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objTooBar.getEstado());
                mostrarTipDocPre();
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Fecha de documento
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                //Fecha de embarque
                dtpFecEmb.setEnabled(true);
                dtpFecEmb.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                //Fecha de puerto
                dtpFecPue.setEnabled(true);
                dtpFecPue.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                //Fecha de arribo
                dtpFecArr.setEnabled(true);
                dtpFecArr.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                               
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                
                
                //cargarPedidos();
                
                butCarPedEmb.setEnabled(true);
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
                
                objTblModPedEmb.insertRow();
                
                objTblModPedEmb.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);
                objTblModCarPag.setModoOperacion(objTblModCarPag.INT_TBL_EDI);
                cargarCargosPagarConsolidados();
                
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {            
        }

        public void clickModificar() {
//            if(chkCiePedEmb.isSelected()){
//                txtCodTipDoc.setEditable(false);
//                txtDesCorTipDoc.setEditable(false);
//                txtDesLarTipDoc.setEditable(false);
//                butTipDoc.setEnabled(false);
//                txtCodDoc.setEditable(false);
//                txtNumDoc.setEditable(false);
//                txtNumPedCon.setEditable(false);
//                
//                dtpFecDoc.setEnabled(false);
//                dtpFecArr.setEnabled(false);
//                dtpFecEmb.setEnabled(false);
//                dtpFecPue.setEnabled(false);
//                
//                butCarPedEmb.setEnabled(false);
//                objImp36_01=new ZafImp36_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objTooBar.getEstado());
//                objTblModPedEmb.setModoOperacion(objTblModPedEmb.INT_TBL_NO_EDI);
//                objTblModCarPag.setModoOperacion(objTblModCarPag.INT_TBL_NO_EDI);
//                mostrarMsgInf("<HTML>Los Pedidos Embarcados se encuentran cerrados.<BR>Verifique y vuelva a intentarlo</HTML>");
//            }
//            else{
                txtCodTipDoc.setEditable(false);
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                butTipDoc.setEnabled(false);
                txtCodDoc.setEditable(false);
                txtNumDoc.setEditable(false);
                txtNumPedCon.setEditable(false);
                
                dtpFecDoc.setEnabled(false);
                dtpFecArr.setEnabled(true);
                dtpFecEmb.setEnabled(true);
                dtpFecPue.setEnabled(true);
                
                butCarPedEmb.setEnabled(true);
                cargarCargosPagarConsolidados();
                objImp36_01=new ZafImp36_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objTooBar.getEstado());
                objTblModPedEmb.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);
                objTblModCarPag.setModoOperacion(objTblModCarPag.INT_TBL_EDI);
                
                chkMarPedFacPedEmb.setEnabled(false);
                chkCiePedEmb.setEnabled(false);
                if(chkMarPedFacPedEmb.isSelected())
                    chkMarPedFacPedEmb.setEnabled(false);
                else
                    chkMarPedFacPedEmb.setEnabled(true);
                
                if(chkCiePedEmb.isSelected())
                    chkCiePedEmb.setEnabled(false);
                else
                    chkCiePedEmb.setEnabled(true);
//            }

        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            return true;
        }

        public boolean anular() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
//            if (objThrGUIVisPre==null){
//                objThrGUIVisPre=new ZafThreadGUIVisPre();
//                objThrGUIVisPre.setIndFunEje(1);
//                objThrGUIVisPre.start();
//            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if((blnHayCam) ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;
            
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            
            if (!isCamVal())
                return false;

            return blnRes;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            //consultarReg();
            blnHayCam=false;
            enviarCorElePedCon();
            enviarCorElePedEmbConDup();
            return true;
        }

        public boolean afterConsultar() {
            objTblTotPedEmb.calcularTotales();
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            enviarCorElePedCon();
            consultarReg();
            objTooBar.afterConsultar();
            //mostrarMsgInf("AQUI");
            enviarCorElePedEmbConDup();
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }
    }

    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        boolean blnOptCreCtaPas=false;//SI ES TRUE CREA CUENTA  DE ACTIVO Y PASIVO
        objCodSegInsAnt=null;
        java.util.Date datTmp;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(insertar_tbmCabPedCon()){
                    if(insertar_tbmDetPedConCabPedEmbImp()){
                        if(insertar_tbmDetPedConCarPagImp()){
                            if(actualizar_tbmCabTipDoc()){
                                if(actualizar_tbmCabPedEmbImp()){
                                    if(generaCalculos()){
                                        if(setDatGenCalCon()){
                                            if(actualizar_pedidoEmbarcado()){
                                                if(actualizar_tbmDetPedConCabPedEmbImp_pedEmbActivo()){
                                                    con.commit();
                                                    blnRes=true;
                                                }
                                                else{
                                                    con.rollback();
                                                }
                                            }
                                            else{
                                                con.rollback();
                                            }
                                        }
                                        else{
                                            con.rollback();
                                        }
                                    }
                                    else{
                                        con.rollback();
                                    }
                                }
                                else{
                                    con.rollback();
                                }
                            }
                            else{
                                con.rollback();
                            }
                        }
                        else{
                            con.rollback();
                        }
                    }
                    else{
                        con.rollback();
                    }
                }
                else{
                    con.rollback();
                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" , ne_ultDoc2=ne_ultDoc2+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                //System.out.println("actualizarCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    


    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabPedCon(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPedCon AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="   AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);                
                
                //Obtener el número de documentos consolidados del día
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocIngHoy";
                strSQL+=" FROM tbm_cabPedCon AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumDocIngHoy=rst.getInt("ne_numDocIngHoy");
                }
                intNumDocIngHoy++;
                

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabPedCon(";
                strSQL+="  co_emp, co_loc, co_tipdoc, co_doc, fe_doc, fe_emb, fe_pue, fe_arr";
                strSQL+="  , ne_numdoc, tx_numdoc2, nd_valdoc, tx_obs1, tx_obs2, st_reg, st_emb";
                strSQL+="  , st_ciepedemb, fe_ing, fe_ultmod, co_usring, co_usrmod";
                strSQL+="  )";
                strSQL+=" ( SELECT";
                strSQL+=" " + objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_emb
                strSQL+=", '" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_pue
                strSQL+=", '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_arr
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + ""; //ne_numdoc
                strSQL+=", " + objUti.codificar(txtNumPedCon.getText()) + ""; //tx_numdoc2
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + ""; //nd_valdoc
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + ""; //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + ""; //tx_obs2
                strSQL+=", 'A'";//st_reg
                if(chkMarPedFacPedEmb.isSelected())
                    strSQL+=", 'S'";//st_emb
                else
                    strSQL+=", 'N'";//st_emb
                
                if(chkCiePedEmb.isSelected())
                    strSQL+=", 'S'";//st_ciepedemb
                else
                    strSQL+=", 'N'";//st_ciepedemb
                
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=");";
                //System.out.println("insertar_tbmCabNotPedImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetPedConCabPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detPedConCabPedEmbImp(";
                    strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                    strSQL+="     , co_empRel, co_locRel, co_tipDocRel, co_docRel, st_reg";
                    strSQL+=" )";
                    strSQL+=" ( SELECT ";
                    strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + txtCodTipDoc.getText();//co_tipdoc
                    strSQL+=", " + txtCodDoc.getText();//co_doc
                    strSQL+=", " + j;//co_reg
                    strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP) + "";//co_empRel
                    strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC) + "";//co_locRel
                    strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC) + "";//co_tipDocRel
                    strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC) + "";//co_docRel
                    strSQL+=", 'A'";//st_reg
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                }
                //System.out.println("insertar_tbmDetPedConCabPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetPedConCarPagImp(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detPedConCarPagImp(";
                    strSQL+="     co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag";
                    strSQL+=" )";
                    strSQL+=" ( SELECT ";
                    strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                    strSQL+=", " + txtCodTipDoc.getText();//co_tipdoc
                    strSQL+=", " + txtCodDoc.getText();//co_doc
                    strSQL+=", " + j;//co_reg
                    strSQL+=", " + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG) + "";//co_carpag
                    strSQL+=", " + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG) + "";//nd_valCarPag
                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;
                }
                //System.out.println("insertar_tbmDetPedConCarPagImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                    if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                            strSQL="";
                            strSQL+="UPDATE tbm_cabPedEmbImp";
                            strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQL+=" SET fe_doc='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQL+=" , fe_emb='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQL+=" , fe_pue='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQL+=" , fe_arr='" + strAux + "'";
                            
                            if(chkMarPedFacPedEmb.isSelected())
                                strSQL+=", st_emb='S'";//st_emb
                            else
                                strSQL+=", st_emb='N'";//st_emb
                            
                            strSQL+=" WHERE co_emp=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP) + "";
                            strSQL+=" AND co_loc=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC) + "";
                            strSQL+=" AND co_tipDoc=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC) + "";
                            strSQL+=" AND co_doc=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC) + "";
                            strSQL+=";";
                            strSQLIns+=strSQL;
                    }
                }
                //System.out.println("actualizar_tbmCabPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_pedidoEmbarcado(){
        boolean blnRes=false;
        int intCodEmpCon, intCodLocCon, intCodTipDocCon, intCodDocCon;
        int intCodEmpArl, intCodLocArl, intCodTipDocArl, intCodDocArl;
        int intCodCarPagArl=-1;
        Object objValCarPagArl=null;
        String strTipCarArl="";
        int intNumVerUltDocGen=0;
        String strNumPedVer="", strNumPedEmb="";
        boolean blnCiePedEmb=false;
        boolean blnPedFac_Emb=false;
        try{
            if (con!=null){
                //Cierre de Pedido Embarcado
                if(chkCiePedEmb.isSelected())
                    blnCiePedEmb=true;
                else
                    blnCiePedEmb=false;
                
                //Facturado a Embarcado
                if(chkMarPedFacPedEmb.isSelected())
                    blnPedFac_Emb=true;
                else
                    blnPedFac_Emb=false;

                System.out.println("UPD-arlDatInsPedCar: " + arlDatInsPedCar.toString());
                if (arlDatInsPedCar.size()>0){
                    for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){//objTblModPedEmbCarPagConImp
                        if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                            intCodEmpCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString()));
                            intCodLocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString()));
                            intCodTipDocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString()));
                            intCodDocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString()));

                            ZafImp33 objImp33 = new ZafImp33(objParSis, con, intCodEmpCon, intCodLocCon, intCodTipDocCon, intCodDocCon, objParSis.getCodigoMenu(), new Boolean(true));
                            //objImp33.setVisible(true);
                            this.getParent().add(objImp33,javax.swing.JLayeredPane.DEFAULT_LAYER);

                            for(int j=0;j<arlDatInsPedCar.size();j++){
                                intCodEmpArl=objUti.getIntValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_COD_EMP);
                                intCodLocArl=objUti.getIntValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_COD_LOC);
                                intCodTipDocArl=objUti.getIntValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_COD_TIP_DOC);
                                intCodDocArl=objUti.getIntValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_COD_DOC);
                                intCodCarPagArl=objUti.getIntValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_COD_CAR);
                                objValCarPagArl=objUti.getObjectValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_VAL_CAR);
                                strTipCarArl=objUti.getStringValueAt(arlDatInsPedCar, j, INT_ARL_INS_PED_CAR_TIP_CAR);
                                
                                System.out.println("intCodCarPagArl: " + intCodCarPagArl);
                                System.out.println("objValCarPagArl: " + objValCarPagArl);
                                
                       
                                if(intCodEmpCon==intCodEmpArl){
                                    if(intCodLocCon==intCodLocArl){
                                        if(intCodTipDocCon==intCodTipDocArl){
                                            if(intCodDocCon==intCodDocArl){
                                                System.out.println("intCodDocCon: " + intCodDocCon);
                                                System.out.println("intCodDocArl: " + intCodDocArl);
                                                if(objImp33.setCargosPagarPedidoEmbarcado(intCodCarPagArl, objValCarPagArl)){
                                                    if(strTipCarArl.equals("F")){//if(intCodCarPagArl==2){//
                                                        bgdFleItmCarPagCon=new BigDecimal(objValCarPagArl==null?"0":(objValCarPagArl.toString().equals("")?"0":objValCarPagArl.toString()));
                                                        objImp33.setValorFleteTotal(bgdFleItmCarPagCon);
                                                    }
                                                    blnRes=true;
                                                }
                                                else{
                                                    blnRes=false;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if(blnRes==true){
                                //System.out.println("CARGOS A PAGAR TABLA: " + objImp33.detalleCargos());
                                if(objImp33.regenerarCarPagDxPImp()){
                                    objImp33.calculaTotalAranceles();
                                    objImp33.calculaTotalGastos();
                                    objImp33.setNumPedConsolidado(txtNumPedCon.getText());
                                    if(objImp33.actualizaPedidoEmbarcado(con, blnCiePedEmb, blnPedFac_Emb)){
                                        blnRes=true;
                                    }
                                    else{
                                        blnRes=false;
                                        break;
                                    }
                                }
                                else{
                                    blnRes=false;
                                    break;
                                }
                            }
                            else{
                                blnRes=false;
                                break;
                            }
                            objImp33=null;
                        }
                        else{
                            if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK_AUX)){
                                intCodEmpCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString()));
                                intCodLocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString()));
                                intCodTipDocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString()));
                                intCodDocCon=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString()));                    
                                strNumPedVer=(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED_VER)==null?"":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED_VER).toString());
                                strNumPedEmb=(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED)==null?"":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED).toString());

                                /*Se envia los datos del pedido anterior para ser ingresado como reversion*/
                                ZafImp33 objImp33 = new ZafImp33(objParSis, con, intCodEmpCon, intCodLocCon, intCodTipDocCon, intCodDocCon, objParSis.getCodigoMenu(), new Boolean(true), strNumPedEmb, intNumVerUltDocGen, strNumPedVer, "R");
    //                            objImp33.setVisible(true);
                                this.getParent().add(objImp33,javax.swing.JLayeredPane.DEFAULT_LAYER);

                                if(blnRes==true){
                                    if(objImp33.regenerarCarPagDxPImp()){
                                        objImp33.calculaTotalAranceles();
                                        objImp33.calculaTotalGastos();

                                        if(objImp33.actualizaPedidoEmbarcado(con, blnCiePedEmb, blnPedFac_Emb)){
                                            blnRes=true;
                                        }
                                        else{
                                            blnRes=false;
                                            break;
                                        }
                                    }
                                    else{
                                        blnRes=false;
                                        break;
                                    }
                                }
                                else{
                                    blnRes=false;
                                    break;
                                }
                                objImp33=null;


                                /*Se envia los datos del ultimo pedido que no habia sido consolidado para ser ingresado como nuevo*/
                                stm=con.createStatement();
                                strSQL="";
                                strSQL+=" SELECT e1.tx_numDoc4, e1.co_emp, e1.co_loc, e1.co_tipDoc, e2.co_doc, e1.tx_numDocCon, e1.fe_ing, e1.ne_verNumPed, e1.tx_numPed FROM(";
                                strSQL+=" 	SELECT d1.tx_numDoc4, d1.co_emp, d1.co_loc, d1.co_tipDoc, MAX(d1.fe_ing) AS fe_ing, d1.tx_numDocCon, d1.ne_verNumPed, d1.tx_numPed FROM(";
                                strSQL+=" 		SELECT c1.tx_numDoc4, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDocCon, c1.fe_ing, c1.ne_verNumPed, c1.tx_numPed FROM(";
                                strSQL+=" 			SELECT b1.tx_numDoc4, b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.tx_numDocCon, b2.fe_ing, b1.ne_verNumPed, b1.tx_numPed FROM(";
                                strSQL+=" 				SELECT a1.tx_numDoc4, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_verNumPed";                            
                                strSQL+=" 				, (CASE WHEN a1.tx_numdoc4 IS NOT NULL THEN a1.tx_numdoc4 ELSE a1.tx_numdoc2 END) AS tx_numPed";                            
                                strSQL+=" 				FROM tbm_cabPedEmbImp AS a1";
                                strSQL+=" 				WHERE a1.co_emp=" + intCodEmpCon + " AND a1.co_loc=" + intCodLocCon + "";
                                strSQL+=" 				AND a1.co_tipDoc=" + intCodTipDocCon + " AND a1.co_doc=" + intCodDocCon + "";
                                strSQL+=" 			) AS b1";
                                strSQL+=" 			INNER JOIN (";
                                strSQL+=" 				SELECT a1.tx_numDoc4, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDocCon, a1.fe_ing";
                                strSQL+=" 				FROM tbm_cabPedEmbImp AS a1";
                                strSQL+=" 				WHERE a1.st_reg='A'";
                                strSQL+=" 			) AS b2";
                                strSQL+=" 			ON b1.tx_numDoc4=b2.tx_numDoc4";
                                strSQL+=" 			ORDER BY b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc";
                                strSQL+=" 		) AS c1";
                                strSQL+=" 		WHERE c1.tx_numDocCon IS NULL";
                                strSQL+=" 	) AS d1";
                                strSQL+=" 	GROUP BY d1.tx_numDoc4, d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.tx_numDocCon, d1.ne_verNumPed, d1.tx_numPed";
                                strSQL+=" ) AS e1";
                                strSQL+=" INNER JOIN(";
                                strSQL+=" 	SELECT a1.tx_numDoc4, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDocCon, a1.fe_ing";
                                strSQL+=" 	FROM tbm_cabPedEmbImp AS a1";
                                strSQL+=" 	WHERE a1.tx_numDocCon IS NULL";
                                strSQL+=" ) AS e2";
                                strSQL+=" ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc AND e1.co_tipDoc=e2.co_tipDoc AND e1.fe_ing=e2.fe_ing ";
                                rst=stm.executeQuery(strSQL);
                                if(rst.next()){
                                    intCodEmpCon=rst.getInt("co_emp");
                                    intCodLocCon=rst.getInt("co_loc");
                                    intCodTipDocCon=rst.getInt("co_tipDoc");
                                    intCodDocCon=rst.getInt("co_doc");
                                    intNumVerUltDocGen=Integer.parseInt(rst.getObject("ne_verNumPed")==null?"0":(rst.getString("ne_verNumPed").equals("")?"0":rst.getString("ne_verNumPed")));
                                }

                                objImp33 = new ZafImp33(objParSis, con, intCodEmpCon, intCodLocCon, intCodTipDocCon, intCodDocCon, objParSis.getCodigoMenu(), new Boolean(true), strNumPedEmb, intNumVerUltDocGen, strNumPedVer, "N");
//                                objImp33.setVisible(true);
//                                this.getParent().add(objImp33,javax.swing.JLayeredPane.DEFAULT_LAYER);

                                if(blnRes==true){
                                    if(objImp33.regenerarCarPagDxPImp()){
                                        objImp33.calculaTotalAranceles();
                                        objImp33.calculaTotalGastos();

                                        if(objImp33.actualizaPedidoEmbarcado(con, blnCiePedEmb, blnPedFac_Emb)){
                                            blnRes=true;
                                        }
                                        else{
                                            blnRes=false;
                                            break;
                                        }
                                    }
                                    else{
                                        blnRes=false;
                                        break;
                                    }
                                }
                                else{
                                    blnRes=false;
                                    break;
                                }
                                objImp33=null;




                                stm.close();
                                stm=null;
                                rst.close();
                                rst=null;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función permite actualizar en el detalle de Pedidos Embarcados Consolidados, el registro activo y no el registro origen seleccionado
     * Recordar que se genera documento de reversión y nuevo documento activo, el detalle debe quedar relacionado con el nuevo registro de pedido activo y no con el origen porque éste se inactiva en st_doc
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmDetPedConCabPedEmbImp_pedEmbActivo(){
        boolean blnRes=true;
        int intCodEmpConIna, intCodLocConIna, intCodTipDocConIna, intCodDocConIna;
        String strSQLUpd="";
        try{
            if (con!=null){
                for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                    if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                        intCodEmpConIna=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString()));
                        intCodLocConIna=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString()));
                        intCodTipDocConIna=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString()));
                        intCodDocConIna=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString()));                    

                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT c1.tx_numDoc4, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDocCon, c1.fe_ing FROM(";
                        strSQL+=" 	SELECT b1.tx_numDoc4, b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.tx_numDocCon, b2.fe_ing FROM(";
                        strSQL+=" 		SELECT a1.tx_numDoc4, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+=" 		FROM tbm_cabPedEmbImp AS a1";
                        strSQL+=" 		WHERE a1.co_emp=" + intCodEmpConIna + " AND a1.co_loc=" + intCodLocConIna + "";
                        strSQL+=" 		AND a1.co_tipDoc=" + intCodTipDocConIna + " AND a1.co_doc=" + intCodDocConIna + "";
                        strSQL+=" 	) AS b1";
                        strSQL+=" 	INNER JOIN(";
                        strSQL+=" 		SELECT a1.tx_numDoc4, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDocCon, a1.fe_ing";
                        strSQL+=" 		FROM tbm_cabPedEmbImp AS a1";
                        strSQL+=" 		WHERE a1.st_reg='A' AND a1.st_doc='A'";
                        strSQL+=" 	) AS b2";
                        strSQL+=" 	ON b1.tx_numDoc4=b2.tx_numDoc4";
                        strSQL+=" 	ORDER BY b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc";
                        strSQL+=" ) AS c1";
                        strSQL+=" WHERE c1.tx_numDocCon IS NOT NULL";
                        //System.out.println("actualizar_tbmDetPedConCabPedEmbImp_pedEmbActivo: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            strSQLUpd+=";";
                            strSQLUpd+=" UPDATE tbm_detPedConCabPedEmbImp";
                            strSQLUpd+=" SET co_docRel=" + rst.getInt("co_doc") + "";
                            strSQLUpd+=" WHERE co_empRel=" + rst.getInt("co_emp") + "";
                            strSQLUpd+=" AND co_locRel=" + rst.getInt("co_loc") + "";
                            strSQLUpd+=" AND co_tipDocRel=" + rst.getInt("co_tipDoc") + "";
                            strSQLUpd+=" AND co_docRel=" + intCodDocConIna + "";
                            //System.out.println("strSQLUpd: " + strSQLUpd);
                            
                            //----------------
                            strSQLUpd+=";";
                            strSQLUpd+="UPDATE tbm_cabPedEmbImp";
                            
                            strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQLUpd+=" SET fe_doc='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQLUpd+=" , fe_emb='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQLUpd+=" , fe_pue='" + strAux + "'";
                            
                            strAux=objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                            strSQLUpd+=" , fe_arr='" + strAux + "'";
                            
                            if(chkMarPedFacPedEmb.isSelected())
                                strSQLUpd+=", st_emb='S'";//st_emb
                            else
                                strSQLUpd+=", st_emb='N'";//st_emb
                            
                            strSQLUpd+=" WHERE co_emp=" + rst.getInt("co_emp") + "";
                            strSQLUpd+=" AND co_loc=" + rst.getInt("co_loc") + "";
                            strSQLUpd+=" AND co_tipDoc=" + rst.getInt("co_tipDoc") + "";
                            strSQLUpd+=" AND co_doc=" + rst.getInt("co_doc") + "";
                            strSQLUpd+=";";
                            //------------------
                            
                        }
                    }
                }
                //System.out.println("**strSQLUpd: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    

    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizar_tbmCabPedCon()){
                    if(actualizar_tbmDetPedConCabPedEmbImp()){
                        if(actualizar_tbmDetPedConCarPagImp()){
//                            if(actualizar_tbmCabPedEmbImp()){
                                if(generaCalculos()){
                                    if(setDatGenCalCon()){
                                        if(actualizar_pedidoEmbarcado()){
                                            if(actualizar_tbmDetPedConCabPedEmbImp_pedEmbActivo()){
                                                if(elimina_tbm_detPedConCabPedEmbImp_inactivos()){
                                                    con.commit();
                                                    blnRes=true;
//                                                        con.rollback();
//                                                        blnRes=false;
                                                }
                                                else
                                                    con.rollback();                                                
                                            }
                                            else
                                                con.rollback();                                            
                                        }
                                        else
                                            con.rollback();                                        
                                    }
                                    else
                                        con.rollback();                                    
                                }
                                else
                                    con.rollback();                                
//                            }
//                            else
//                                con.rollback();
                        }
                        else
                            con.rollback();                        
                    }
                    else
                        con.rollback();                    
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabPedCon(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPedCon";
                strSQL+=" SET ne_numdoc=" + objUti.codificar(txtNumDoc.getText(),2) + "";
                strSQL+=" , tx_numdoc2=" + objUti.codificar(txtNumPedCon.getText()) + "";
                strSQL+=", nd_valdoc=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strSQL+=", fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_emb
                strSQL+=", fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_pue
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_arr
                if(chkMarPedFacPedEmb.isSelected())
                    strSQL+=", st_emb='S'";//st_emb
                else
                    strSQL+=", st_emb='N'";//st_emb
                if(chkCiePedEmb.isSelected())
                    strSQL+=", st_ciePedEmb='S'";//st_ciepedemb
                else
                    strSQL+=", st_ciePedEmb='N'";//st_ciepedemb
                
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", fe_ultmod='" + strAux + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=";";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;

                if(!(objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedCon", "tbh_cabPedCon", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "")))
                    blnRes=false;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar el del registro de pedidos facturados/embarcados
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmDetPedConCabPedEmbImp(){
        boolean blnRes=true;
        String strSQLIns="";
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                    if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                        if(!objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK_AUX)){
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detPedConCabPedEmbImp(";
                            strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                            strSQL+="     , co_empRel, co_locRel, co_tipDocRel, co_docRel, st_reg";
                            strSQL+=" )";
                            strSQL+=" ( SELECT ";
                            strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                            strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                            strSQL+=", " + txtCodTipDoc.getText();//co_tipdoc
                            strSQL+=", " + txtCodDoc.getText();//co_doc
                            
                            strSQL+=",     (SELECT (MAX(co_reg)+1) AS co_reg FROM tbm_detPedConCabPedEmbImp WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="     AND co_loc=" + objParSis.getCodigoLocal() + " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                            strSQL+="     AND co_doc=" + txtCodDoc.getText() + "";
                            strSQL+="     )";
                            
                            
                            
                            strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP) + "";//co_empRel
                            strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC) + "";//co_locRel
                            strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC) + "";//co_tipDocRel
                            strSQL+=", " + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC) + "";//co_docRel
                            strSQL+=", 'A'";//st_reg
                            strSQL+=");";
                            strSQLIns+=strSQL;
                        }
                    }
                    else if(!objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                        if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK_AUX)){
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="UPDATE tbm_detPedConCabPedEmbImp";
                            strSQL+=" SET st_reg='I'";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                            strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                            strSQL+=" AND co_emprel=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP) + "";
                            strSQL+=" AND co_locrel=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC) + "";
                            strSQL+=" AND co_tipdocrel=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC) + "";
                            strSQL+=" AND co_docrel=" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC) + "";
                            strSQL+=" AND st_reg='A'";
                            strSQL+=";";
                            strSQLIns+=strSQL;
                        }
                    }
                }
                //System.out.println("actualizar_tbmDetPedConCabPedEmbImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                
                if(!(objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_detPedConCabPedEmbImp", "tbh_detPedConCabPedEmbImp", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "")))
                    blnRes=false;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar el registro de cargos a pagar de consolidados
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmDetPedConCarPagImp(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=0;
        String strLin="";
        try{
            if (con!=null){
                stm=con.createStatement();
                
                strSQL="";
                strSQL+="SELECT MAX(co_reg) AS co_reg";
                strSQL+=" FROM tbm_detPedConCarPagImp";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=";";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    j=rst.getInt("co_reg");
                j++;
                
                for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                    strLin=objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_LIN)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_LIN).toString();
                    if(strLin.equals("I")){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detPedConCarPagImp(";
                        strSQL+="     co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag";
                        strSQL+=" )";
                        strSQL+=" ( SELECT ";
                        strSQL+="  " + objParSis.getCodigoEmpresa();//co_emp
                        strSQL+=", " + objParSis.getCodigoLocal();//co_loc
                        strSQL+=", " + txtCodTipDoc.getText();//co_tipdoc
                        strSQL+=", " + txtCodDoc.getText();//co_doc
                        strSQL+=", " + j;//co_reg
                        strSQL+=", " + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG) + "";//co_carpag
                        strSQL+=", " + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG) + "";//nd_valCarPag
                        strSQL+=");";
                        j++;
                        strSQLIns+=strSQL;
                    }
                    else{//modificación 'M'
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_detPedConCarPagImp";
                        strSQL+=" SET nd_valCarPag=" + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG) + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=" AND co_carPag=" + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG) + "";
                        strSQL+=";";
                        strSQLIns+=strSQL;
                    }
                }
                //System.out.println("actualizar_tbmDetPedConCarPagImp: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                
                if(!(objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_detPedConCarPagImp", "tbh_detPedConCarPagImp", "WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + " AND a1.co_doc=" + txtCodDoc.getText() + "")))
                    blnRes=false;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbhCabPedCon(){
        boolean blnRes=true;
        intCodHisPedCon=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                 strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                //Obtener el código del último histórico.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_his) AS co_his";
                strSQL+=" FROM tbh_cabPedCon AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="   AND a1.co_doc=" + txtCodDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodHisPedCon=rst.getInt("co_his");
                intCodHisPedCon++;

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbh_cabPedCon(";
                strSQL+="  co_emp, co_loc, co_tipdoc, co_doc, co_his, fe_doc, fe_emb, fe_pue, fe_arr";
                strSQL+="  , ne_numdoc, tx_numdoc2, nd_valdoc, tx_obs1, tx_obs2, st_reg, st_emb";
                strSQL+="  , st_ciepedemb, fe_ing, fe_ultmod, co_usring, co_usrmod, fe_his, co_usrHis";
                strSQL+="  )";
                strSQL+=" ( SELECT co_emp, co_loc, co_tipdoc, co_doc";
                strSQL+="  , " + intCodHisPedCon + " AS co_his";
                strSQL+="  , fe_doc, fe_emb, fe_pue, fe_arr";
                strSQL+="  , ne_numdoc, tx_numdoc2, nd_valdoc, tx_obs1, tx_obs2, st_reg, st_emb";
                strSQL+="  , st_ciepedemb, fe_ing, fe_ultmod";
                strSQL+="  , co_usring, co_usrmod";
                strSQL+="  , '" + strAux + "' AS fe_his";//fe_his
                strSQL+="  ," + objParSis.getCodigoUsuario() + " AS co_usrHis";//co_usrHis
                strSQL+=" FROM tbm_cabPedCon";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=");";
                //System.out.println("insertar_tbhCabPedCon: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite insertar el detalle del pedido embarcado/facturado.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbhDetPedConCabPedEmbImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbh_detPedConCabPedEmbImp(";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_his";
                strSQL+="     , co_empRel, co_locRel, co_tipDocRel, co_docRel, st_reg, fe_his, co_usrHis";
                strSQL+=" )";
                strSQL+=" ( SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg";
                strSQL+="  , " + intCodHisPedCon + " AS co_his";
                strSQL+="  , co_empRel, co_locRel, co_tipDocRel, co_docRel, st_reg";
                strSQL+="  , '" + strAux + "' AS fe_his";//fe_his
                strSQL+="  ," + objParSis.getCodigoUsuario() + " AS co_usrHis";//co_usrHis
                strSQL+=" FROM tbm_detPedConCabPedEmbImp";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=");";                
                //System.out.println("insertar_tbhDetPedConCabPedEmbImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    
    
    /**
     * Esta función permite insertar el detalle de los cargos a pagar de pedidos consolidados.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbhDetPedConCarPagImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbh_detPedConCarPagImp(";
                strSQL+="     co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_his, co_carPag, nd_valCarPag, fe_his, co_usrHis";
                strSQL+=" )";
                strSQL+=" ( SELECT co_emp, co_loc, co_tipDoc, co_doc, co_reg";
                strSQL+="  , " + intCodHisPedCon + " AS co_his";
                strSQL+="  , co_carPag, nd_valCarPag";
                strSQL+="  , '" + strAux + "' AS fe_his";//fe_his
                strSQL+="  ," + objParSis.getCodigoUsuario() + " AS co_usrHis";//co_usrHis
                strSQL+=" FROM tbm_detPedConCarPagImp";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                strSQL+=");";
                //System.out.println("insertar_tbhDetPedConCarPagImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
            
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        int intCodEmp;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPedCon AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";                
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";

                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecEmb.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecPue.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if (dtpFecArr.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                                 
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                strAux=txtNumPedCon.getText();
                if (!strAux.equals("")){
                    strSQL+=" AND (  UPPER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+="        OR LOWER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+=" )";
                }
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                //System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next()){
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIP_DOC,rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndPedCon=arlDatCon.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                } 
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

    
//    falta todo lo que es cargar cabecera, cargar pedido, cargar cargos a pagar
//            
//             Y
//            
//    métodos de cálculos
//    
    
    
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetPedConCabPedEmbImp()){
                    if (cargarDetPedConCarPagImp()){
                        
                        
           
                    }
                }
            }
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" , a1.fe_doc, a1.fe_emb, a1.fe_pue, a1.fe_arr, a1.ne_numdoc, a1.tx_numdoc2";
                strSQL+=" , a1.nd_valdoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_emb, a1.st_ciepedemb, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                strSQL+=" FROM tbm_cabPedCon AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_DOC);
                //System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    
                    dtpFecDoc.setText(""); 
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    dtpFecEmb.setText(""); 
                    dtpFecEmb.setText(objUti.formatearFecha(rst.getDate("fe_emb"),"dd/MM/yyyy"));
                    
                    dtpFecPue.setText(""); 
                    dtpFecPue.setText(objUti.formatearFecha(rst.getDate("fe_pue"),"dd/MM/yyyy"));
                    
                    dtpFecArr.setText(""); 
                    dtpFecArr.setText(objUti.formatearFecha(rst.getDate("fe_arr"),"dd/MM/yyyy"));
                
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_numdoc2");
                    txtNumPedCon.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);

                    strAux=rst.getObject("nd_valdoc")==null?"0":(rst.getString("nd_valdoc").equals("")?"0":rst.getString("nd_valdoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    txaObs1.setText(rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    txaObs2.setText(rst.getObject("tx_obs2")==null?"":rst.getString("tx_obs2"));
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    
                    strAux=rst.getObject("st_emb")==null?"":rst.getString("st_emb");
                    if(strAux.equals("S"))
                        chkMarPedFacPedEmb.setSelected(true);
                    else
                        chkMarPedFacPedEmb.setSelected(false);
                    
                    
                    strAux=rst.getObject("st_ciePedEmb")==null?"":rst.getString("st_ciePedEmb");
                    if(strAux.equals("S"))
                        chkCiePedEmb.setSelected(true);
                    else
                        chkCiePedEmb.setSelected(false);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndPedCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetPedConCabPedEmbImp(){
        boolean blnRes=true;
        try{
            objTblModPedEmb.removeAllRows();
            if(!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" , a2.co_emprel, a2.co_locrel, a2.co_tipdocrel, a2.co_docrel";
                    strSQL+=" , a4.tx_desCor, a4.tx_desLar, a3.ne_numDoc, a3.tx_numDoc2, a3.fe_doc";
                    strSQL+=" , (CASE WHEN a3.tx_numdoc4 IS NOT NULL THEN a3.tx_numdoc4 ELSE a3.tx_numdoc2 END) AS tx_numPed";
                    strSQL+=" , a3.co_imp, a5.tx_nom AS tx_nomImp, a3.co_exp, a6.tx_nom AS tx_nomExp";
                    strSQL+=" , a3.nd_totFobCfr, a3.nd_totCfrAra";
                    strSQL+=" FROM tbm_cabPedCon AS a1 INNER JOIN tbm_detPedConCabPedEmbImp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN ((tbm_cabPedEmbImp AS a3 INNER JOIN tbm_emp AS a5 ON a3.co_imp=a5.co_emp) INNER JOIN tbm_expImp AS a6 ON a3.co_exp=a6.co_exp)";
                    strSQL+=" ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4";
                    strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" AND a3.st_reg='A'";
                    strSQL+=" ORDER BY a2.co_reg";
                    //System.out.println("cargarDetPedConCabPedEmbImp: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_PED_EMB_LIN,"");
                        vecReg.add(INT_TBL_DAT_PED_EMB_CHK,            new Boolean(true));
                        vecReg.add(INT_TBL_DAT_PED_EMB_CHK_AUX,        new Boolean(true));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_EMP,        rst.getString("co_empRel"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_LOC,        rst.getString("co_locRel"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_TIP_DOC,    rst.getString("co_tipDocRel"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_DOC,        rst.getString("co_docRel"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_NUM_DOC,        rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_NUM_PED,        rst.getString("tx_numPed"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_FEC_DOC,        rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_IMP,        rst.getString("co_imp"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_NOM_IMP,        rst.getString("tx_nomImp"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_COD_EXP,        rst.getString("co_exp"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_NOM_EXP,        rst.getString("tx_nomExp"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR,    rst.getString("nd_totFobCfr"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO,"");
                        vecReg.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA,    rst.getString("nd_totCfrAra"));
                        vecReg.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO,"");
                        vecReg.add(INT_TBL_DAT_PED_EMB_NUM_PED_VER,    rst.getString("tx_numDoc2"));
                        vecDat.add(vecReg);
                    }
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    
                    //Asignar vectores al modelo.
                    objTblModPedEmb.setData(vecDat);
                    tblDatPedEmb.setModel(objTblModPedEmb);
                    vecDat.clear();
                }
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
    
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetPedConCarPagImp(){
        boolean blnRes=true;
        try{
            objTblModCarPag.removeAllRows();
            if(!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="  SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                    strSQL+=" , a5.st_carPagPedCon, a2.nd_valCarPag, a2.co_carPag, a5.tx_nom, a5.tx_tipCarPag";
                    strSQL+=" FROM tbm_cabPedCon AS a1 INNER JOIN tbm_detPedConCarPagImp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN tbm_carPagImp AS a5 ON a2.co_carPag=a5.co_carPag";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndPedCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" AND a1.st_reg IN('A', 'I')";
                    strSQL+=" ORDER BY a2.co_reg";
                    //System.out.println("cargarDetPedConCarPagImp: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_CAR_PAG_LIN,"");
                        vecReg.add(INT_TBL_DAT_CAR_PAG_COD_REG,         rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG,     rst.getString("tx_tipCarPag"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_COD_CAR_PAG,     rst.getString("co_carPag"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG,     rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG,     rst.getString("nd_valCarPag"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX, rst.getString("nd_valCarPag"));
                        vecReg.add(INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON, rst.getString("st_carPagPedCon"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    
                    //Asignar vectores al modelo.
                    objTblModCarPag.setData(vecDat);
                    tblDatCarPag.setModel(objTblModCarPag);
                    vecDat.clear();
                }
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    

    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        Object objValCarPagCer=null;
        boolean blnValNulCarPag=false;
        
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Seleccione un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if (txtNumPedCon.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> es obligatorio.<BR>Escriba un Número de pedido y vuelva a intentarlo.</HTML>");
            txtNumPedCon.requestFocus();
            return false;
        }
        if(objTooBar.getEstado()=='n'){
            if( isExisteNumeroPedido(txtNumPedCon.getText()) ) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> está repetido.<BR>Escriba un Número de pedido diferente y vuelva a intentarlo.</HTML>");
                txtNumPedCon.requestFocus();
                txtNumPedCon.selectAll();
                return false;
            } 
        }
        
        if(objTooBar.getEstado()=='n'){
            if(!objTblModPedEmb.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Cargos a Pagar no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
            
            if(!objTblModCarPag.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Pedidos facturados/embarcados no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
        }
        
        
        if(chkCiePedEmb.isSelected()){
            for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                objValCarPagCer=objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG);
                if(objValCarPagCer==null){
                    blnValNulCarPag=true;
                    break;
                }
            }
            if(blnValNulCarPag){
                mostrarMsgInf("<HTML>La tabla de Cargos a Pagar contiene valores nulos.<BR>Verifique los datos y vuelva a intentarlo.</HTML>");
                return false;
            }
            
//            if((objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m')){
//                mostrarMsgInf("<HTML>Los pedidos asociados al consolidado se encuentran cerrados.<BR>Verifique los datos y vuelva a intentarlo.</HTML>");
//                return false;
//            }
            
            
            
            
        }
        
        return true;
    }
    

    /**
     * Función que permite conocer si el numero de pedido ya existe
     * @return 
     */
    private boolean isExisteNumeroPedido(String strNumPed){
        boolean blnRes=false;
        Connection conExiNumPed;
        Statement stmExiNumPed;
        ResultSet rstExiNumPed;
        try{
            conExiNumPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiNumPed!=null){
                stmExiNumPed=conExiNumPed.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabPedCon AS a1";
                strSQL+=" WHERE a1.st_Reg IN ('A')";
                strSQL+=" AND a1.tx_numDoc2 LIKE "+objUti.codificar(strNumPed)+"";
                if(!txtCodDoc.getText().equals("")){
                    strSQL+=" AND a1.co_doc <> "+txtCodDoc.getText(); /* Para que valida entre los demas documentos. */
                }                
                rstExiNumPed=stmExiNumPed.executeQuery(strSQL);
                if(rstExiNumPed.next())
                    blnRes=true;
                conExiNumPed.close();
                conExiNumPed=null;
                stmExiNumPed.close();
                stmExiNumPed=null;
                rstExiNumPed.close();
                rstExiNumPed=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
   
   

    /**
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorElePedCon(){
        boolean blnRes=false;        
        String strSbjMsg="" + intNumDocIngHoy + ") " + txtNumPedCon.getText() + " (" + objParSis.getNombreMenu() + ")";
        int intCodCfgCorEle=26;
        BigDecimal bdeValCarPag=BigDecimal.ZERO;
        BigDecimal bdeValCarPagAux=BigDecimal.ZERO;
        try{
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Creación de cuentas pedido<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th colspan='7'> Pedido # " + txtNumPedCon.getText() + "</th>";
            strMsg+="</tr>";

            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th>Número Pedidos Facturados/Embarcados</th>";
            strMsg+="	<th>Importador</th>";
            strMsg+="	<th>Exportador</th>";
            strMsg+="</tr>";

            for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                    strMsg+="<tr bgcolor='#FF4000'>";
                    strMsg+="	<td align=right>" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED) + "</td>";
                    strMsg+="	<td align=left>" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NOM_IMP) + "</td>";
                    strMsg+="	<td align=left>" + objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NOM_EXP) + "</td>";
                    strMsg+="</tr>";
                }
            }
            
            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th>Cargos a Pagar</th>";
            strMsg+="	<th>Valor a pagar anterior</th>";
            strMsg+="	<th>Valor a pagar actual</th>";
            strMsg+="</tr>";
            
            for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                bdeValCarPag=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString()));
                bdeValCarPagAux=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX).toString()));
                if( (bdeValCarPag.compareTo(BigDecimal.ZERO)!=0)  || (bdeValCarPagAux.compareTo(BigDecimal.ZERO)!=0) ){
                    strMsg+="<tr bgcolor='#FF4000'>";
                    strMsg+="	<td align=right>" + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG) + "</td>";
                    strMsg+="	<td align=left>" + objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG) + "</td>";
                    strMsg+="</tr>";
                }
            }

            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                mostrarMsgInf("<HTML>Estimado usuario, <FONT COLOR=\"blue\">Creación de Pedidos Consolidados</FONT></HTML>");
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;     
    }
    
    
    /**
     * Función que permite validar si el Pedido Embarcado que se consolida, se queda con estado de documento 'A' en lugar de cambiarlo a 'I'. 
     * Documento original cambia de 'A' a 'I'.
     * @return true Si se pudo realizar la operación
     * <BR>false Caso contrario
     */
    private boolean enviarCorElePedEmbConDup(){
        boolean blnRes=false;        
        String strSbjMsg="Consolidado: " + txtNumPedCon.getText() + " (Pedidos Embarcados duplicados)";
        int intCodCfgCorEle=32;
        Connection conPedEmbDup;
        Statement stmPedEmbDup;
        ResultSet rstPedEmbDup;
        String strMsgCab="", strMsgCue="", strMsgPie="";
        try{
            conPedEmbDup=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmbDup!=null){
                
                strMsgCab+="<html>";
                strMsgCab+="<body>";
                strMsgCab+="<h3>Pedidos Embarcados que se consolidan y están duplicados en estado de documento (st_doc='A')<hr></h3>";
                strMsgCab+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
                strMsgCab+="<tr bgcolor='#F2E0F7'>";
                strMsgCab+="	<th colspan='7'> Pedido # " + txtNumPedCon.getText() + "</th>";
                strMsgCab+="</tr>";

                strMsgCab+="<tr bgcolor='#F2E0F7'>";
                strMsgCab+="	<th>Pedidos Embarcados/Facturados</th>";
                strMsgCab+="	<th>Cantidad de Pedidos duplicados</th>";
                strMsgCab+="</tr>";
                
                stmPedEmbDup=conPedEmbDup.createStatement();
                strSQL="";
                strSQL+=" SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_numDoc4, COUNT(c1.tx_numDoc4) AS ne_numVecRep";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc4";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDoc4, a1.st_reg, a1.st_doc";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a1";
                strSQL+=" 		WHERE a1.st_reg='A' AND a1.st_doc='A'";
                strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numDoc4, a1.st_reg, a1.st_doc";
                strSQL+=" 	) AS b1";
                strSQL+=" 	INNER JOIN(";
                strSQL+=" 		SELECT a3.tx_numDoc4";
                strSQL+=" 		FROM  tbm_cabPedCon AS a1 LEFT OUTER JOIN (tbm_detPedConCabPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+=" 			ON a2.co_empRel=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc)";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		WHERE a1.st_reg='A' AND a2.st_reg='A' AND a3.st_reg='A' AND a3.st_doc='A'";
                strSQL+="               AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" 		GROUP BY a3.tx_numDoc4";
                strSQL+=" 		ORDER BY a3.tx_numDoc4";
                strSQL+=" 	) AS b2";
                strSQL+=" 	ON b1.tx_numDoc4=b2.tx_numDoc4";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc4";
                strSQL+=" ) AS c1";
                strSQL+=" GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.tx_numDoc4";
                strSQL+=" HAVING COUNT(c1.tx_numDoc4)>1";
                System.out.println("enviarCorElePedEmbConDup: " + strSQL);
                rstPedEmbDup=stmPedEmbDup.executeQuery(strSQL);
                strMsgCue="";
                while(rstPedEmbDup.next()){
                    strMsgCue+="<tr bgcolor='#FFFF00'>";
                    strMsgCue+="	<td align=left>" + rstPedEmbDup.getString("tx_numDoc4") + "</td>";
                    strMsgCue+="	<td align=right>" + rstPedEmbDup.getString("ne_numVecRep") + "</td>";
                    strMsgCue+="</tr>";
                }
                
                strMsgPie+="</table>";
                strMsgPie+="</body>";
                strMsgPie+="</html> ";

                if(strMsgCue.length()>0){
                    if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, (strMsgCab+strMsgCue+strMsgPie), strSbjMsg )){
                        blnRes=true;
                    }
                }

                conPedEmbDup.close();
                conPedEmbDup=null;
                stmPedEmbDup.close();
                stmPedEmbDup=null;
                rstPedEmbDup.close();
                rstPedEmbDup=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;     
    }
    
    
    
    private boolean cargarPedidosSeleccionados(){
        boolean blnRes=true;
        try{
            vecDat.clear();
            if(arlDatCab_objImp36_01.size()>0){
                for(int i=0; i<arlDatCab_objImp36_01.size();i++){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_PED_EMB_LIN,"");
                    vecReg.add(INT_TBL_DAT_PED_EMB_CHK,            objUti.getObjectValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_VAL_CHK));
                    vecReg.add(INT_TBL_DAT_PED_EMB_CHK_AUX,        objUti.getObjectValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_VAL_CHK_AUX));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_EMP,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_EMP));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_LOC,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_LOC));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_TIP_DOC,    objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_TIP_DOC));
                    vecReg.add(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC, objUti.getObjectValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_DES_COR_TIP_DOC)    );
                    vecReg.add(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC,objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_DES_LAR_TIP_DOC));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_DOC,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_DOC));
                    vecReg.add(INT_TBL_DAT_PED_EMB_NUM_DOC,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_NUM_DOC_SIS));
                    vecReg.add(INT_TBL_DAT_PED_EMB_NUM_PED,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_NUM_DOC_PED));
                    vecReg.add(INT_TBL_DAT_PED_EMB_FEC_DOC,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_FEC_DOC));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_IMP,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_IMP));
                    vecReg.add(INT_TBL_DAT_PED_EMB_NOM_IMP,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_NOM_IMP));
                    vecReg.add(INT_TBL_DAT_PED_EMB_COD_EXP,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_COD_EXP));
                    vecReg.add(INT_TBL_DAT_PED_EMB_NOM_EXP,        objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_NOM_EXP));
                    vecReg.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR,    objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_TOT_FOB_CFR));
                    vecReg.add(INT_TBL_DAT_PED_EMB_TOT_FOB_CFR_PRO,"0");
                    vecReg.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA,    objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_VAL_CFR_ARA));
                    vecReg.add(INT_TBL_DAT_PED_EMB_VAL_CFR_ARA_PRO,"0");
                    vecReg.add(INT_TBL_DAT_PED_EMB_NUM_PED_VER,    objUti.getStringValueAt(arlDatCab_objImp36_01, i, objImp36_01.INT_ARL_NUM_DOC_PED_VER));
                    
                    vecDat.add(vecReg);
                }
            }
            
            //Asignar vectores al modelo.
            objTblModPedEmb.setData(vecDat);
            tblDatPedEmb.setModel(objTblModPedEmb);
            objTblModPedEmb.setDataModelChanged(true);
            vecDat.clear();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    
    


    private boolean cargarCargosPagarConsolidados(){
        boolean blnRes=true;
        Connection conPed;
        try{
            vecDat.clear();
            conPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPed!=null){
                stm=conPed.createStatement();
//                strSQL="";
//                strSQL+=" SELECT a1.co_carPag, a1.tx_nom, a1.tx_tipCarPag, a1.ne_ubi, a1.st_carPagPedCon";
//                strSQL+=" FROM tbm_carPagImp AS a1";
//                strSQL+=" WHERE a1.st_reg='A'";
//                strSQL+=" AND a1.st_carPagPedCon  NOT IN('N')";
//                strSQL+=" ORDER BY a1.ne_ubi";



                strSQL="";
                strSQL+=" SELECT a1.co_carPag, a1.tx_nom, a1.tx_tipCarPag, a1.ne_ubi, a1.st_carPagPedCon";
                if(objTooBar.getEstado()!='n')
                    strSQL+=" , a2.nd_valCarPag";
                strSQL+=" FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_detPedConCarPagImp AS a2";
                strSQL+=" ON a1.co_carPag=a2.co_carPag";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" AND a1.st_carPagPedCon  NOT IN('N')";
                if(objTooBar.getEstado()!='n'){
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " AND a2.co_doc=" + txtCodDoc.getText() + "";
                }
                strSQL+=" GROUP BY a1.co_carPag, a1.tx_nom, a1.tx_tipCarPag, a1.ne_ubi, a1.st_carPagPedCon";
                if(objTooBar.getEstado()!='n')
                    strSQL+=" , a2.nd_valCarPag";
                strSQL+=" ORDER BY a1.ne_ubi";
                //System.out.println("cargarCargosPagarConsolidados: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_CAR_PAG_LIN,"");
                    vecReg.add(INT_TBL_DAT_CAR_PAG_COD_REG,     null);
                    vecReg.add(INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG,     rst.getString("tx_tipCarPag"));
                    vecReg.add(INT_TBL_DAT_CAR_PAG_COD_CAR_PAG,     rst.getString("co_carPag"));                    
                    vecReg.add(INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG,     rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG,     0);
                    vecReg.add(INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX, 0);
                    vecReg.add(INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON, rst.getString("st_carPagPedCon"));
                    
                    if(objTooBar.getEstado()!='n'){
                        vecReg.setElementAt(rst.getString("nd_valCarPag"), INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG);
                        vecReg.setElementAt(rst.getString("nd_valCarPag"), INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG_AUX);
                    }
                    vecDat.add(vecReg);
                }
                conPed.close();
                conPed=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                
                //Asignar vectores al modelo.
                objTblModCarPag.setData(vecDat);
                tblDatCarPag.setModel(objTblModCarPag);
                objTblModCarPag.setDataModelChanged(true);
                vecDat.clear();
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
    
    
    /**
     * Función que permite obtener el número de Pedido Consolidado
     */
    private void setNumPedCon(){
        String strNumPedOri=txtNumPedCon.getText();
        int intLonNumPed=strNumPedOri.length();//obtengo la longitud de mi cadena
        
        String strNumPedDes="";
        
        
        int intNumMaxChr=5;//Número máximo de caracteres
        String strNumPedRll="";//Caracteres de relleno
        
        for(int i=0; i<(intNumMaxChr-intLonNumPed); i++){
            strNumPedRll=strNumPedRll+"0";
        }
        
        strNumPedDes="C"+strNumPedRll+strNumPedOri;
        txtNumPedCon.setText(strNumPedDes);
    }
    

    private boolean replicaCarPagConAra(){
        boolean blnRes=true;
        int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
        BigDecimal bdeTotFobCfr=BigDecimal.ZERO, bdeValCfrAra=BigDecimal.ZERO;
        String strDesCorTipDoc="", strNumPedCon="";
        try{
            for(int i=0; i<objTblModPedEmb.getRowCountTrue(); i++){
                if(objTblModPedEmb.isChecked(i, INT_TBL_DAT_PED_EMB_CHK)){
                    intCodEmp=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_EMP).toString()));
                    intCodLoc=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_LOC).toString()));
                    intCodTipDoc=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString()));
                    intCodDoc=Integer.parseInt(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_COD_DOC).toString()));
                    bdeTotFobCfr=new BigDecimal(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).toString()));
                    bdeValCfrAra=new BigDecimal(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA)==null?"0":(objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).toString().equals("")?"0":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).toString()));
                    strDesCorTipDoc=objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC)==null?"":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC).toString();
                    strNumPedCon=objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED)==null?"":objTblModPedEmb.getValueAt(i, INT_TBL_DAT_PED_EMB_NUM_PED).toString();

                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_LIN,"");
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP,         intCodEmp);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC,         intCodLoc);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC,     intCodTipDoc);                    
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_DES_COR_TIP_DOC, strDesCorTipDoc);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC,         intCodDoc);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_NUM_PED,         strNumPedCon);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR,     bdeTotFobCfr);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO, "0");
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA,     bdeValCfrAra);
                    vecReg.add(INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO, "0");
                    
                    for(int k=intNumColIniImp; k<intNumColFinImp;k++){
                        vecReg.add(k,     "");
                    }                    
                    vecDat.add(vecReg);
                }
            }
            //Asignar vectores al modelo.
            objTblModPedEmbCarPagConImp.setData(vecDat);
            tblDatPedEmbCarPagConImp.setModel(objTblModPedEmbCarPagConImp);
            vecDat.clear();
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    
    /**
     * Función que permite calcular el prorrateo del Total FOB/CFR y de Valor CFR/ARA
     * @return 
     */
    private boolean calculaProrateoCarPagConImp(){
        boolean blnRes=true;
        BigDecimal bdeTotFobCfr=BigDecimal.ZERO, bdeValCfrAra=BigDecimal.ZERO;
        BigDecimal bdeSumTotFobCfr=BigDecimal.ZERO, bdeSumValCfrAra=BigDecimal.ZERO;
        
        BigDecimal bdeValSumTotFobCfr=BigDecimal.ZERO, bdeValSumValCfrAra=BigDecimal.ZERO;

        try{          
            bdeSumTotFobCfr=new BigDecimal(objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR)==null?"0":(objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).toString().equals("")?"0":objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_TOT_FOB_CFR).toString()));
            bdeSumValCfrAra=new BigDecimal(objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA)==null?"0":(objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).toString().equals("")?"0":objTblTotPedEmb.getValueAt(0, INT_TBL_DAT_PED_EMB_VAL_CFR_ARA).toString()));
            //redondeando los valores
            bdeSumTotFobCfr=objUti.redondearBigDecimal(bdeSumTotFobCfr, objParSis.getDecimalesMostrar());
            bdeSumValCfrAra=objUti.redondearBigDecimal(bdeSumValCfrAra, objParSis.getDecimalesMostrar());
            
            objTblModTotPedEmbCarPagConImp.insertRow();
            
            objTblModTotPedEmbCarPagConImp.setValueAt(bdeSumTotFobCfr, 0, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR);
            objTblModTotPedEmbCarPagConImp.setValueAt(bdeSumValCfrAra, 0, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA);
            bdeTotFobCfr=BigDecimal.ZERO;
            bdeValCfrAra=BigDecimal.ZERO;

            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){

                bdeTotFobCfr=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString()));
                bdeValCfrAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA).toString()));
                //redondeando los valores
                bdeTotFobCfr=objUti.redondearBigDecimal(bdeTotFobCfr, objParSis.getDecimalesMostrar());
                bdeValCfrAra=objUti.redondearBigDecimal(bdeValCfrAra, objParSis.getDecimalesMostrar());
                
                if(bdeTotFobCfr.compareTo(BigDecimal.ZERO)>0){
                    objTblModPedEmbCarPagConImp.setValueAt((bdeTotFobCfr.divide(bdeSumTotFobCfr, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)), i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO);
                }
                
                if(bdeValCfrAra.compareTo(BigDecimal.ZERO)>0){
                    objTblModPedEmbCarPagConImp.setValueAt((bdeValCfrAra.divide(bdeSumValCfrAra, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)), i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO);                
                }
            }
//            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){
//                System.out.println("**INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO: " + objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO));
//            }
            
            //inicio - para valores faltantes
            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){
                bdeTotFobCfr=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).toString()));
                bdeValCfrAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).toString()));
                //redondeando los valores
                bdeTotFobCfr=objUti.redondearBigDecimal(bdeTotFobCfr, objParSis.getDecimalesMostrar());
                bdeValCfrAra=objUti.redondearBigDecimal(bdeValCfrAra, objParSis.getDecimalesMostrar());
                
                bdeValSumTotFobCfr=bdeValSumTotFobCfr.add(bdeTotFobCfr);
                bdeValSumValCfrAra=bdeValSumValCfrAra.add(bdeValCfrAra);
            }
            
            BigDecimal bdeValFalFobCfr=BigDecimal.ZERO;
            BigDecimal bdeValFalCfrAra=BigDecimal.ZERO;
            if(bdeValSumTotFobCfr.compareTo(BigDecimal.ONE)!=0){//NO esta completo
                bdeValFalFobCfr=(BigDecimal.ONE).subtract(bdeValSumTotFobCfr);//esto es lo faltante
            }
            if(bdeValSumValCfrAra.compareTo(BigDecimal.ONE)!=0){
                bdeValFalCfrAra=(BigDecimal.ONE).subtract(bdeValSumValCfrAra);//esto es lo faltante
            }
            
            BigDecimal bdeValRatFobCfr=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO).toString()));
            BigDecimal bdeValRatCfrAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO).toString()));
            
            objTblModPedEmbCarPagConImp.setValueAt((bdeValRatFobCfr.add(bdeValFalFobCfr)), (objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO);
            objTblModPedEmbCarPagConImp.setValueAt((bdeValRatCfrAra.add(bdeValFalCfrAra)), (objTblModPedEmbCarPagConImp.getRowCountTrue()-1), INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO);
            //fin - para valores faltantes
            
//            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){
//                System.out.println("--INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO: " + objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO));
//            }
            
            
            
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
            for (int i=(objTblModPedEmbCarPagConImp.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblModPedEmbCarPagConImp.removeColumn(tblDatPedEmbCarPagConImp, i);
            }
            for (int i=(objTblModTotPedEmbCarPagConImp.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblModTotPedEmbCarPagConImp.removeColumn(tblTotPedEmbCarPagConImp, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean configurarColumnasAdicionar(){
        boolean blnRes=true;
        try{
            if(eliminaColumnasAdicionadas()){
                //Impuestos
                if(getCargoPagarImpuesto()){
                    if(agregarColumnasCarPagImp()){
                        if(agregarColumnasTotPedEmbCarPagImp()){
                            
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
    
    
    
    private boolean getCargoPagarImpuesto(){
        boolean blnRes=true;
        String strEstPedCon="";
        int intCodCarPagTmp;
        try{
            arlDatCarPagImp.clear();
            for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                strEstPedCon=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON).toString());
                intCodCarPagTmp=Integer.parseInt(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString()));
                if( (strEstPedCon.equals("I")) || (strEstPedCon.equals("G")) ){
                    if(intCodCarPagTmp==9){
                        arlRegCarPagImp=new ArrayList();
                        arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG, "0");
                        arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_NOM_CAR_PAG, "% IVA");
                        arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG, "");
                        arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG, "1");
                        arlDatCarPagImp.add(arlRegCarPagImp);
                    }
                    arlRegCarPagImp=new ArrayList();
                    arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG, objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG));
                    arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_NOM_CAR_PAG, objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_NOM_CAR_PAG));
                    arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG, "");
                    arlRegCarPagImp.add(INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG, objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG));
                    arlDatCarPagImp.add(arlRegCarPagImp);
                }
            }
            //System.out.println("getCargoPagarImpuesto: " + arlDatCarPagImp.toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    
   private boolean agregarColumnasCarPagImp(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAddImp=arlDatCarPagImp.size();//
        intNumColIniImp=intNumColEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomIngImp="";
        try{
            objTblCelRenLblColAddImp=new ZafTblCelRenLbl();
            objTblCelRenLblColAddImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColAddImp.setTipoFormato(objTblCelRenLblColAddImp.INT_FOR_NUM);
            objTblCelRenLblColAddImp.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            for (int i=0; i<intNumColAddImp; i++){
                strNomIngImp=objUti.getStringValueAt(arlDatCarPagImp, i, INT_ARL_CAR_PAG_CON_IMP_NOM_CAR_PAG);
                objUti.setStringValueAt(arlDatCarPagImp, i, INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG, "" + (intNumColIniImp+i));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp+i);
                tbc.setHeaderValue(strNomIngImp);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColAddImp);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModPedEmbCarPagConImp.addColumn(tblDatPedEmbCarPagConImp, tbc);
            }
            intNumColFinImp=objTblModPedEmbCarPagConImp.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    
    private boolean agregarColumnasTotPedEmbCarPagImp(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            
            for (int i=0; i<(intNumColAddImp); i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniImp+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTotPedEmbCarPagConImp.addColumn(tblTotPedEmbCarPagConImp, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    /**
     * Función que permite calcular los cargos a pagar por impuestos en consolidado
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularCarPagImp(){
        boolean blnRes=true;
        int intCodCarPag=-1;
        String strEstCarPagPedCon="";
        BigDecimal bdeValCarPagUsr=BigDecimal.ZERO;
        String strTipCarPag="";
        try{
            arlDatInsPedCar.clear();
            for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                strEstCarPagPedCon=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON).toString());
                strTipCarPag=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG).toString());
                bdeValCarPagUsr=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString()));
                //redondear
                bdeValCarPagUsr=objUti.redondearBigDecimal(bdeValCarPagUsr, objParSis.getDecimalesMostrar());
                
                if(strEstCarPagPedCon.equals("I")){
                    intCodCarPag=Integer.parseInt(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG)==null?"-1":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString().equals("")?"-1":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString()));
                    bdeValCarPagUsr=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString()));
                    
                    //redondear
                    //ARANCEL (1)
                    if(intCodCarPag==1){
                        bdeValCarPagUsr=objUti.redondearBigDecimal(bdeValCarPagUsr, objParSis.getDecimalesBaseDatos());
                        System.out.println("a-bdeValCarPagUsr: " + bdeValCarPagUsr);
                    }//SOLO ARANCEL SE TRABAJA A 6 DECIMALES
                    else
                        bdeValCarPagUsr=objUti.redondearBigDecimal(bdeValCarPagUsr, objParSis.getDecimalesMostrar());
                    
                    //FODINFA  (4)
                    if(intCodCarPag==4){
                        calcularCarPagConImp_FodInf_Ara(intCodCarPag, strEstCarPagPedCon, bdeValCarPagUsr, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO, intNumColIniImp, strTipCarPag);
                        setAjuDecUltReg(intNumColIniImp);
                    }
                    
                    //ARANCEL (1)
                    if(intCodCarPag==1){
                        System.out.println("a-intCodCarPag: " + intCodCarPag);
                        calcularCarPagConImp_FodInf_Ara(intCodCarPag, strEstCarPagPedCon, bdeValCarPagUsr, INT_TBL_DAT_PED_EMB_CAR_PAG_VAL_CFR_ARA_PRO, (intNumColIniImp+1), strTipCarPag);
                        setAjuDecUltReg((intNumColIniImp+1));
                    }
                }

            }
            
            //FODINFA  (4)
            calcularTotales_FodInf_Ara(intNumColIniImp);
            //ARANCEL (1)
            calcularTotales_FodInf_Ara((intNumColIniImp+1));

            //Calcula Prorateo de IVA
            calcularCarPagConImp_Iva();
            //aqui

            for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                strEstCarPagPedCon=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON).toString());
                strTipCarPag=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG).toString());
                if(strEstCarPagPedCon.equals("I")){
                    intCodCarPag=Integer.parseInt(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG)==null?"-1":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString().equals("")?"-1":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString()));
                    bdeValCarPagUsr=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString()));
                    //redondear
                    bdeValCarPagUsr=objUti.redondearBigDecimal(bdeValCarPagUsr, objParSis.getDecimalesMostrar());
                    
                    //IVA  (9)
                    if(intCodCarPag==9){
                        calcularCarPagConImp_FodInf_Ara(intCodCarPag, strEstCarPagPedCon, bdeValCarPagUsr, (intNumColIniImp+2), (intNumColIniImp+3), strTipCarPag);
                        setAjuDecUltReg((intNumColIniImp+3));
                    }
                }
            }

            //IVA  (9)
            calcularTotales_FodInf_Ara((intNumColIniImp+3));
            
            /*GASTOS*/
            int intCodCarPagGto=-1;
            int intColCarPagGto=-1;
            for(int j=0; j<arlDatCarPagImp.size(); j++){
                intCodCarPagGto=objUti.getIntValueAt(arlDatCarPagImp, j, INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG);
                intColCarPagGto=objUti.getIntValueAt(arlDatCarPagImp, j, INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG);
                
                for(int i=0; i<objTblModCarPag.getRowCountTrue(); i++){
                    intCodCarPag=Integer.parseInt(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG)==null?"-1":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString().equals("")?"-1":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString()));
                    strEstCarPagPedCon=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_EST_CAR_PAG_CON).toString());                   
                    strTipCarPag=(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG)==null?"":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG).toString());
                    if(strEstCarPagPedCon.equals("G")){
                        if(intCodCarPagGto==intCodCarPag){
                            bdeValCarPagUsr=new BigDecimal(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG)==null?"0":(objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString().equals("")?"0":objTblModCarPag.getValueAt(i, INT_TBL_DAT_CAR_PAG_VAL_CAR_PAG).toString()));
                            //redondear
                            bdeValCarPagUsr=objUti.redondearBigDecimal(bdeValCarPagUsr, objParSis.getDecimalesMostrar());
                            calcularCarPagConImp_FodInf_Ara(intCodCarPag, strEstCarPagPedCon, bdeValCarPagUsr, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR_PRO, intColCarPagGto, strTipCarPag);
                            setAjuDecUltReg(intColCarPagGto);
                        }
                    }
                }
                calcularTotales_FodInf_Ara(intColCarPagGto);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean calcularCarPagConImp_FodInf_Ara(int intCodCarPagRef, String strEstCarPagPedCon, BigDecimal bdeValCarPagUsr, int columnaCarPagCalcular, int columnaSetearCarPagCal, String tipoCargo){
        boolean blnRes=true;        
        BigDecimal bdeValColCarPag=BigDecimal.ZERO;
        BigDecimal bdeSumValColCarPag=BigDecimal.ZERO;
        try{            
            if( (strEstCarPagPedCon.equals("I")) || (strEstCarPagPedCon.equals("G"))){
                for(int j=0; j<objTblModPedEmbCarPagConImp.getRowCountTrue(); j++){
                    
                    bdeValColCarPag=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(j, columnaCarPagCalcular)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(j, columnaCarPagCalcular).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(j, columnaCarPagCalcular).toString()));
                    if(intCodCarPagRef==1){
                        bdeSumValColCarPag=objUti.redondearBigDecimal((bdeValColCarPag.multiply(bdeValCarPagUsr)), objParSis.getDecimalesBaseDatos());
                        System.out.println("bdeValColCarPag-2: " + bdeValColCarPag);
                        System.out.println("bdeValCarPagUsr-2: " + bdeValCarPagUsr);
                        System.out.println("bdeSumValColCarPag-2: " + bdeSumValColCarPag);
                    }
                    else{
                        bdeSumValColCarPag=objUti.redondearBigDecimal((bdeValColCarPag.multiply(bdeValCarPagUsr)), objParSis.getDecimalesMostrar());
                        //System.out.println("bdeSumValColCarPag-6: " + bdeSumValColCarPag);
                    }
                    
                    objTblModPedEmbCarPagConImp.setValueAt(bdeSumValColCarPag, j, columnaSetearCarPagCal);//columna de fodinfa
                }
            }
            //System.out.println("AarlDatInsPedCar: " + arlDatInsPedCar.toString());
            bdeValColCarPag=BigDecimal.ZERO;
            bdeValCarPagUsr=BigDecimal.ZERO;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   

    private boolean calcularTotales_FodInf_Ara(int columnaSetearCarPagCal){
        boolean blnRes=true;
        //Calcula totales de FODINFA y ARANCEL
        BigDecimal bdeValCal=BigDecimal.ZERO;
        BigDecimal bdeSumCal=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){
                //FODINFA
                bdeValCal=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, columnaSetearCarPagCal)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, columnaSetearCarPagCal).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, columnaSetearCarPagCal).toString()));
                //redondear
                bdeValCal=objUti.redondearBigDecimal(bdeValCal, objParSis.getDecimalesMostrar());
                bdeSumCal=bdeSumCal.add(bdeValCal);
            }
            objTblModTotPedEmbCarPagConImp.setValueAt(bdeSumCal, 0, columnaSetearCarPagCal);
            
            bdeValCal=BigDecimal.ZERO;
            bdeSumCal=BigDecimal.ZERO;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    

    private boolean calcularCarPagConImp_Iva(){
        boolean blnRes=true;
        BigDecimal bdeSumTotFobCfr=BigDecimal.ZERO;//suma de la columna Tot.Fob.Cfr.
        BigDecimal bdeSumTotFodInf=BigDecimal.ZERO;//suma de Fodinfa
        BigDecimal bdeSumTotAra=BigDecimal.ZERO;//suma de Arancel
        
        BigDecimal bdeFobCfrPro=BigDecimal.ZERO;
        BigDecimal bdeValFodAra=BigDecimal.ZERO;
        BigDecimal bdeValFodInf=BigDecimal.ZERO;
        
        BigDecimal bdeProIva=BigDecimal.ZERO;//prorateo de iva
        try{
            // PRORATEO IVA
            bdeSumTotFobCfr=new BigDecimal(objTblModTotPedEmbCarPagConImp.getValueAt(0, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR)==null?"0":(objTblModTotPedEmbCarPagConImp.getValueAt(0, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString().equals("")?"0":objTblModTotPedEmbCarPagConImp.getValueAt(0, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString()));
            bdeSumTotFodInf=new BigDecimal(objTblModTotPedEmbCarPagConImp.getValueAt(0, intNumColIniImp)==null?"0":(objTblModTotPedEmbCarPagConImp.getValueAt(0, intNumColIniImp).toString().equals("")?"0":objTblModTotPedEmbCarPagConImp.getValueAt(0, intNumColIniImp).toString()));
            bdeSumTotAra=new BigDecimal(objTblModTotPedEmbCarPagConImp.getValueAt(0, (intNumColIniImp+1))==null?"0":(objTblModTotPedEmbCarPagConImp.getValueAt(0, (intNumColIniImp+1)).toString().equals("")?"0":objTblModTotPedEmbCarPagConImp.getValueAt(0, (intNumColIniImp+1)).toString()));
            
            for(int j=0; j<objTblModPedEmbCarPagConImp.getRowCountTrue(); j++){
                bdeFobCfrPro=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(j, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(j, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(j, INT_TBL_DAT_PED_EMB_CAR_PAG_TOT_FOB_CFR).toString()));
                //redondear
                bdeFobCfrPro=objUti.redondearBigDecimal(bdeFobCfrPro, objParSis.getDecimalesMostrar());
                
                bdeValFodAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(j, (intNumColIniImp+1))==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(j, (intNumColIniImp+1)).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(j, (intNumColIniImp+1)).toString()));
                //redondear
                bdeValFodAra=objUti.redondearBigDecimal(bdeValFodAra, objParSis.getDecimalesMostrar());
                
                bdeValFodInf=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(j, intNumColIniImp)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(j, intNumColIniImp).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(j, intNumColIniImp).toString()));
                //redondear
                bdeValFodInf=objUti.redondearBigDecimal(bdeValFodInf, objParSis.getDecimalesMostrar());
                
                bdeProIva=(bdeFobCfrPro.add(bdeValFodAra).add(bdeValFodInf)).divide((bdeSumTotFobCfr.add(bdeSumTotFodInf).add(bdeSumTotAra)), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                //redondear
                bdeProIva=objUti.redondearBigDecimal(bdeProIva, objParSis.getDecimalesMostrar());
                
                objTblModPedEmbCarPagConImp.setValueAt(bdeProIva, j, (intNumColIniImp+2));//columna de % iva
                
                setAjuDecUltReg((intNumColIniImp+2));
                
//                System.out.println("***********" + j);
//                System.out.println("bdeProIva: " + bdeProIva);
                
                
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    public ArrayList getArregloPedCar() {
        return arlDatInsPedCar;
    }
     
    private boolean generaCalculos(){
        boolean blnRes=false;
        try{
            if(configurarColumnasAdicionar()){
                if(replicaCarPagConAra()){
                    if(calculaProrateoCarPagConImp()){
                        //Problema: se calcula los datos y luego se hace lo de la diferencia del ultimo registro
                        if(calcularCarPagImp()){
                            System.out.println("--**//arlDatInsPedCar:  " + arlDatInsPedCar.toString());
                            blnRes=true;
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
    
    
    
    
    private boolean elimina_tbm_detPedConCabPedEmbImp_inactivos(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" DELETE FROM tbm_detPedConCabPedEmbImp";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                strSQL+=" AND st_reg='I';";
                //System.out.println("elimina_tbm_detPedConCabPedEmbImp_inactivos: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

            
        
            
    /**
     * Función que permite adicionar o restar decimales al último registro según el valor ingresado por usuario 
     * comparado a la suma del prorrateo a cada pedido según cada cargo a pagar
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean setAjuDecUltReg(int columna){
        boolean blnRes=true;
        int intCodCarPag=-1;
        int intColCarPag=-1;
        BigDecimal bdeValCarPagCon=BigDecimal.ZERO;
        BigDecimal bdeValCarPagPed=BigDecimal.ZERO;
        BigDecimal bdeSumCarPagPed=BigDecimal.ZERO;
        BigDecimal bdeValDifCarPag=BigDecimal.ZERO;
        BigDecimal bdeValUltReg=BigDecimal.ZERO;
        int j=columna;
        BigDecimal bdeValUltRegAra=BigDecimal.ZERO;
        try{
            //System.out.println("arlDatCarPagImp: " + arlDatCarPagImp.toString());
//            for(int j=intNumColIniImp; j<intNumColFinImp;j++){//columna
                for(int k=0; k<arlDatCarPagImp.size(); k++){
                    intCodCarPag=objUti.getIntValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG);
                    intColCarPag=objUti.getIntValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG);
                    bdeValCarPagCon=new BigDecimal(objUti.getObjectValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG)==null?"0":(objUti.getStringValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG).equals("")?"0":objUti.getStringValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_VAL_CAR_PAG)));
                    //redondear
                    bdeValCarPagCon=objUti.redondearBigDecimal(bdeValCarPagCon, objParSis.getDecimalesMostrar());
                    //System.out.println("*************bdeValCarPagCon: " + bdeValCarPagCon);
                    
                    if(intColCarPag==j){
                        for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){//fila
                            bdeValCarPagPed=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, j).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, j).toString()));
                            //redondeat
                            bdeValCarPagPed=objUti.redondearBigDecimal(bdeValCarPagPed, objParSis.getDecimalesMostrar());
                            bdeSumCarPagPed=bdeSumCarPagPed.add(bdeValCarPagPed);
                            //redondear
                            bdeSumCarPagPed=objUti.redondearBigDecimal(bdeSumCarPagPed, objParSis.getDecimalesMostrar());
                        }
                        //diferencia
                        bdeValDifCarPag=objUti.redondearBigDecimal((bdeValCarPagCon.subtract(bdeSumCarPagPed)), objParSis.getDecimalesMostrar()).abs();                       
                        
                        if(bdeValCarPagCon.compareTo(bdeSumCarPagPed)==0){//No hace nada
//                            System.out.println("BB");
                        }
                        else if(bdeValCarPagCon.compareTo(bdeSumCarPagPed)>0){//Se suma la diferencia al último registro
//                            if(intCodCarPag==1){//Derecho arancelario: No pone al último registro sino al último registro que tiene valor diferente a cero en cargo de Arancel
//                                for(int i=(objTblModPedEmbCarPagConImp.getRowCountTrue()-1); i>=0; i--){//fila
//                                    bdeValUltRegAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, j).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, j).toString()));
//                                    if(bdeValUltRegAra.compareTo(BigDecimal.ZERO)>0){
//                                        objTblModPedEmbCarPagConImp.setValueAt((objUti.redondearBigDecimal((bdeValUltRegAra.add(bdeValDifCarPag)), objParSis.getDecimalesMostrar())), i, j);
//                                        break;
//                                    }
//                                }                                
//                            }
//                            else{
                                bdeValUltReg=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j).toString()));                            
                                objTblModPedEmbCarPagConImp.setValueAt((objUti.redondearBigDecimal((bdeValUltReg.add(bdeValDifCarPag)), objParSis.getDecimalesMostrar())), (objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j);
                                objUti.setStringValueAt(arlDatInsPedCar, (arlDatInsPedCar.size()-1), INT_ARL_INS_PED_CAR_VAL_CAR, (objUti.redondearBigDecimal((bdeValUltReg.add(bdeValDifCarPag)), objParSis.getDecimalesMostrar())).toString());
//                            }
                        }
                        else if(bdeValCarPagCon.compareTo(bdeSumCarPagPed)<0){//Se resta la diferencia al último registro
//                            if(intCodCarPag==1){//Derecho arancelario: No pone al último registro sino al último registro que tiene valor diferente a cero en cargo de Arancel
//                                for(int i=(objTblModPedEmbCarPagConImp.getRowCountTrue()-1); i>=0; i--){//fila
//                                    bdeValUltRegAra=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, j).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, j).toString()));
//                                    if(bdeValUltRegAra.compareTo(BigDecimal.ZERO)>0){
//                                        objTblModPedEmbCarPagConImp.setValueAt((objUti.redondearBigDecimal((bdeValUltRegAra.add(bdeValDifCarPag)), objParSis.getDecimalesMostrar())), i, j);
//                                        break;
//                                    }
//                                }                                
//                            }
//                            else{
                                bdeValUltReg=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j).toString().equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt((objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j).toString()));                            
                                objTblModPedEmbCarPagConImp.setValueAt((objUti.redondearBigDecimal((bdeValUltReg.subtract(bdeValDifCarPag)), objParSis.getDecimalesMostrar())), (objTblModPedEmbCarPagConImp.getRowCountTrue()-1), j);
                                objUti.setStringValueAt(arlDatInsPedCar, (arlDatInsPedCar.size()-1), INT_ARL_INS_PED_CAR_VAL_CAR, (objUti.redondearBigDecimal((bdeValUltReg.subtract(bdeValDifCarPag)), objParSis.getDecimalesMostrar())).toString());
//                            }
                        }
                        bdeValCarPagCon=BigDecimal.ZERO;
                        bdeValCarPagPed=BigDecimal.ZERO;
                        bdeSumCarPagPed=BigDecimal.ZERO;
                        bdeValDifCarPag=BigDecimal.ZERO;
                        bdeValUltReg=BigDecimal.ZERO;
                        
                        
                    }
                }
//            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    /**
     * Función que permite cargar los datos de la tabla a un arraylist, mismos que deben ser guardados en la DB
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean setDatGenCalCon(){
        boolean blnRes=true;
        int intCodEmpPedEmb=-1;
        int intCodLocPedEmb=-1;
        int intCodTipDocPedEmb=-1;
        int intCodDocPedEmb=-1;
        int intCodCarPagPedEmb=-1;
        int intColArlCarPag=-1;
        BigDecimal bdeValCarPag=BigDecimal.ZERO;
        int intCodCarPagCon=-1;
        String strTipCar="";
        try{
            for(int i=0; i<objTblModPedEmbCarPagConImp.getRowCountTrue(); i++){//fila
                intCodEmpPedEmb=Integer.parseInt(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP).equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_EMP).toString()));
                intCodLocPedEmb=Integer.parseInt(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC).equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_LOC).toString()));
                intCodTipDocPedEmb=Integer.parseInt(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC).equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_TIP_DOC).toString()));
                intCodDocPedEmb=Integer.parseInt(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC).equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, INT_TBL_DAT_PED_EMB_CAR_PAG_COD_DOC).toString()));
                
                for (int j=intNumColIniImp; j<intNumColFinImp; j++){//columna
                    if(arlDatCarPagImp.size()>0){
                        for(int k=0; k<arlDatCarPagImp.size(); k++){
                            intColArlCarPag=objUti.getIntValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_COL_CAR_PAG);
                            if(j==intColArlCarPag){
                                intCodCarPagPedEmb=objUti.getIntValueAt(arlDatCarPagImp, k, INT_ARL_CAR_PAG_CON_IMP_COD_CAR_PAG);
                                for(int m=0; m<objTblModCarPag.getRowCountTrue(); m++){
                                    intCodCarPagCon=Integer.parseInt(objTblModCarPag.getValueAt(m, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG)==null?"-1":(objTblModCarPag.getValueAt(m, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).equals("")?"-1":objTblModCarPag.getValueAt(m, INT_TBL_DAT_CAR_PAG_COD_CAR_PAG).toString()));
                                    if(intCodCarPagCon==intCodCarPagPedEmb){
                                        strTipCar=(objTblModCarPag.getValueAt(m, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG)==null?"":objTblModCarPag.getValueAt(m, INT_TBL_DAT_CAR_PAG_TIP_CAR_PAG).toString());
                                        break;
                                    }
                                }
                                
                                System.out.println("intCodCarPagCon: " + intCodCarPagCon);
                                bdeValCarPag=new BigDecimal(objTblModPedEmbCarPagConImp.getValueAt(i, j)==null?"0":(objTblModPedEmbCarPagConImp.getValueAt(i, j).equals("")?"0":objTblModPedEmbCarPagConImp.getValueAt(i, j).toString()));
                                System.out.println("bdeValCarPag: " + bdeValCarPag);
                                
                                arlRegInsPedCar=new ArrayList();
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_COD_EMP, intCodEmpPedEmb);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_COD_LOC, intCodLocPedEmb);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_COD_TIP_DOC, intCodTipDocPedEmb);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_COD_DOC, intCodDocPedEmb);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_COD_CAR, intCodCarPagPedEmb);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_TIP_CAR, strTipCar);
                                arlRegInsPedCar.add(INT_ARL_INS_PED_CAR_VAL_CAR, bdeValCarPag);//Valor del cargo a pagar calculado....
                                arlDatInsPedCar.add(arlRegInsPedCar);
                                
                                
                                break;
                                
                            }
                        }
                    }
                }
            }
            System.out.println("arlDatInsPedCar: "  + arlDatInsPedCar.toString());
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
            
            
    

}