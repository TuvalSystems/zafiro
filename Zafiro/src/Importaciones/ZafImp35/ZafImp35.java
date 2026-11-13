/*
 * ZafImp35.java
 *
 * Created on 06 de Mayo de 2019, 13h09
 */
package Importaciones.ZafImp35;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;   
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;  
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafImp35 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable "tblCfgRub"
    static final int INT_TBL_CFG_RUB_LIN=0;
    static final int INT_TBL_CFG_RUB_COD_EMP=1;
    static final int INT_TBL_CFG_RUB_COD_LOC=2;
    static final int INT_TBL_CFG_RUB_COD_TIP_DOC=3;
    static final int INT_TBL_CFG_RUB_COD_DOC=4;
    static final int INT_TBL_CFG_RUB_COD_REG=5;
    static final int INT_TBL_CFG_RUB_NOM_RUB=6;
    static final int INT_TBL_CFG_RUB_VAL_RUB=7;
    static final int INT_TBL_CFG_RUB_CHK_CIE=8;
    static final int INT_TBL_CFG_RUB_TXT_OBS=9;
    static final int INT_TBL_CFG_RUB_BUT_MES_EMB=10;
    static final int INT_TBL_CFG_RUB_COD_MES_EMB=11;
    static final int INT_TBL_CFG_RUB_TXT_MES_EMB=12;
    static final int INT_TBL_CFG_RUB_TXT_ANI_EMB=13;
    static final int INT_TBL_CFG_RUB_BUT_MES_PUE=14;
    static final int INT_TBL_CFG_RUB_COD_MES_PUE=15;
    static final int INT_TBL_CFG_RUB_TXT_MES_PUE=16;
    static final int INT_TBL_CFG_RUB_TXT_ANI_PUE=17;
    static final int INT_TBL_CFG_RUB_NUM_REQ=18;//RFQ
    
    //Constantes: Columnas del JTable "tblCfgPed"
    static final int INT_TBL_CFG_PED_LIN=0;
    static final int INT_TBL_CFG_PED_COD_EMP=1;
    static final int INT_TBL_CFG_PED_COD_LOC=2;
    static final int INT_TBL_CFG_PED_COD_TIP_DOC=3;
    static final int INT_TBL_CFG_PED_COD_DOC=4;
    static final int INT_TBL_CFG_PED_NUM_PED=5;
    static final int INT_TBL_CFG_PED_VAL_PED=6;
    static final int INT_TBL_CFG_PED_CHK_CIE=7;
    static final int INT_TBL_CFG_PED_FEC_COL=8;
    static final int INT_TBL_CFG_PED_BUT_RUB=9;
    static final int INT_TBL_CFG_PED_TXT_OBS1=10;
    static final int INT_TBL_CFG_PED_TXT_OBS2=11;
    static final int INT_TBL_CFG_PED_DAT_APL=12;
    static final int INT_TBL_CFG_PED_NUM_REQ=13;    
    
    //Constantes: Columnas del JTable "tblRptPedConPre"/"tblRptPedSinPre"
    static final int INT_TBL_RPT_PED_PRE_LIN=0;
    static final int INT_TBL_RPT_PED_PRE_COD_EMP_RUB=1;
    static final int INT_TBL_RPT_PED_PRE_COD_LOC_RUB=2;
    static final int INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB=3;
    static final int INT_TBL_RPT_PED_PRE_COD_DOC_RUB=4;
    static final int INT_TBL_RPT_PED_PRE_COD_REG_RUB=5;
    static final int INT_TBL_RPT_PED_PRE_NOM_RUB=6;
    static final int INT_TBL_RPT_PED_PRE_VAL_RUB=7;
    static final int INT_TBL_RPT_PED_PRE_VAL_APL_RUB=8;
    static final int INT_TBL_RPT_PED_BUT_AUX=9;
    static final int INT_TBL_RPT_PED_PRE_COD_EMP_PED=10;
    static final int INT_TBL_RPT_PED_PRE_COD_LOC_PED=11;
    static final int INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED=12;
    static final int INT_TBL_RPT_PED_PRE_COD_DOC_PED=13;    
    static final int INT_TBL_RPT_PED_PRE_NUM_PED=14;
    static final int INT_TBL_RPT_PED_PRE_VAL_PED=15;  
    static final int INT_TBL_RPT_PED_PRE_FEC_COL=16;  
    
    //
    private final int INT_EST_COD_TIP_DOC_PED_PRE=207;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModCfgRub, objTblModCfgPed, objTblModRptPedConPre, objTblModRptPedSinPre, objTblModTotRpt;
    private ZafTblTot objTblTotCfgRub, objTblTotCfgPed, objTblTotRptPedConPre, objTblTotRptPedSinPre, objTblTotRpt;   //JTable de totales.
    private ZafThreadGUI objThrGUI;       
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblColNomRub, objTblCelRenLblColValRub;                      //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChkCieRub, objTblCelRenChkCiePed;       //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkCieRub, objTblCelEdiChkCiePed;       //Editor: JCheckBox en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtNomRub, objTblCelEdiTxtValRub;       //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtNumPed, objTblCelEdiTxtValPed;       //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtAniEmb, objTblCelEdiTxtAniPue, objTblCelEdiTxtNumReqPre, objTblCelEdiTxtNumReqNotPre;       //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtObs;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButGen objTblCelEdiBut;                 //Render: Editar JButton en JTable.
    private ZafMouMotAdaRpt objMouMotAdaRpt;                    //ToolTipText en TableHeader.
    private ZafMouMotAdaCfgRub objMouMotAdaCfgRub;              //ToolTipText en TableHeader.
    private ZafMouMotAdaCfgPed objMouMotAdaCfgPed;              //ToolTipText en TableHeader. 
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafDtePckEdi objDtePckEdiFecCol;
    private ZafTblCelRenLbl objTblCelRenLblDtePcki;
    private ZafDatePicker dtpFecColPed;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;
    private ZafVenCon vcoPed;                                   //Ventana de consulta "Pedidos".
    private ZafVenCon vcoMeses;                                 //Ventana de consulta "Meses".
    private ZafImp objZafImp;
    private ZafImp35_01 objImp35_01;
    private java.util.Date datFecAux;   
    private Object objCodSegNotPedPre;                          //Objeto de Codigo de Seguimiento.
    private ZafSegMovInv objSegMovInv;
    private ZafHisTblBasDat objHisTblBasDat;                    //Histórico    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoMesEmb, objTblCelEdiTxtVcoMesPue;
    private ZafTblCelEdiButVco objTblCelEdiButVcoMesEmb, objTblCelEdiButVcoMesPue;
    
    private BigDecimal bgdValTotPre;
    
    private int intCodDocPedPre_seg;
    private int intAniSel=-1, intMesSel=-1;
   
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;
    private String strSQL, strAux;
    private String strDesCorTipDoc, strDesLarTipDoc;   
    private String strFecUltMod="";
    private String strFormatoDatePicker = "y-m-d";    
    
    private String strVer=" v0.2.7";
    
    /** Crea una nueva clase ZafImp35. */
    public ZafImp35(ZafParSis obj)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Titulo Programa.                        
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objImp35_01= new ZafImp35_01(objParSis);  
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            objHisTblBasDat=new ZafHisTblBasDat();
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4497)) { //Consultar
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4498)) { //Guardar
                butGua.setVisible(false);
            }            
            if (!objPerUsr.isOpcionEnabled(4499)) {  //Cerrar
                butCer.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4500)) {  /*Ficha "Configuración": Casilla "Cierre presupuesto mensual"*/
                chkCiePreMen.setEnabled(false);
            }
            
            //Configurar ZafVenCon
            configurarVenConMes();
            
            //Configurar JCombo
            cargarPeriodo();  
             
            //Configurar Tipo de Documento Predeterminado
            mostrarTipDocPre();
            
            //Configurar los JTables.
            configurarTblCfgRub();
            configurarTblCfgPed();
            configurarTblRptPedConPre();
            configurarTblRptPedSinPre();
            configurarTblTot();
            
            //Campos 
            chkMosPedAso.setVisible(false);
            chkMosPedAso.setEnabled(false);
            
            
            if(objParSis.getCodigoUsuario()!=1) {
                txtCodDoc.setVisible(false);    
            }
            
            txtCodTipDoc.setVisible(false); 
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txtValTotPre.setEditable(false);
            
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema()); 
            txtValTotPre.setBackground(objParSis.getColorCamposSistema()); 
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblCfgRub".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCfgRub()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CFG_RUB_LIN,"");
            vecCab.add(INT_TBL_CFG_RUB_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_CFG_RUB_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CFG_RUB_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CFG_RUB_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_CFG_RUB_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_CFG_RUB_NOM_RUB,"Descripción");
            vecCab.add(INT_TBL_CFG_RUB_VAL_RUB,"Valor");
            vecCab.add(INT_TBL_CFG_RUB_CHK_CIE,"Cie.");
            vecCab.add(INT_TBL_CFG_RUB_TXT_OBS,"Obs.");
            vecCab.add(INT_TBL_CFG_RUB_BUT_MES_EMB,"");
            vecCab.add(INT_TBL_CFG_RUB_COD_MES_EMB,"Cód.Mes.Emb.");
            vecCab.add(INT_TBL_CFG_RUB_TXT_MES_EMB,"Mes.Emb.");
            vecCab.add(INT_TBL_CFG_RUB_TXT_ANI_EMB,"Año.Emb.");
            vecCab.add(INT_TBL_CFG_RUB_BUT_MES_PUE,"");
            vecCab.add(INT_TBL_CFG_RUB_COD_MES_PUE,"Cód.Mes.Pue.");
            vecCab.add(INT_TBL_CFG_RUB_TXT_MES_PUE,"Mes.Pue.");
            vecCab.add(INT_TBL_CFG_RUB_TXT_ANI_PUE,"Año.Pue.");
            vecCab.add(INT_TBL_CFG_RUB_NUM_REQ,"RFQ");

            objTblModCfgRub=new ZafTblMod();
            objTblModCfgRub.setHeader(vecCab);
            tblCfgRub.setModel(objTblModCfgRub);

            //Configurar JTable: Establecer tipo de selección.
            tblCfgRub.setRowSelectionAllowed(true);
            tblCfgRub.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCfgRub);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCfgRub.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCfgRub.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCfgRub.getColumnModel();
            tcmAux.getColumn(INT_TBL_CFG_RUB_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_REG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_RUB_NOM_RUB).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_CFG_RUB_VAL_RUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CFG_RUB_CHK_CIE).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_OBS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_EMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_MES_EMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_MES_EMB).setPreferredWidth(57);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_ANI_EMB).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_PUE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CFG_RUB_COD_MES_PUE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_MES_PUE).setPreferredWidth(57);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_ANI_PUE).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CFG_RUB_NUM_REQ).setPreferredWidth(60);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModCfgRub.setColumnDataType(INT_TBL_CFG_RUB_VAL_RUB, objTblModCfgRub.INT_COL_DBL, new Integer(0), null);            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_EMP, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_LOC, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_TIP_DOC, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_DOC, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_REG, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_MES_EMB, tblCfgRub);
            objTblModCfgRub.addSystemHiddenColumn(INT_TBL_CFG_RUB_COD_MES_PUE, tblCfgRub);

            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblCfgRub.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisMovPed());                          
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCfgRub=new ZafMouMotAdaCfgRub();
            tblCfgRub.getTableHeader().addMouseMotionListener(objMouMotAdaCfgRub);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblCfgRub);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_LIN).setCellRenderer(objTblFilCab);
 
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblCfgRub);   
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblCfgRub);
            
            //Configurar JTable: Establecer formato
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CFG_RUB_VAL_RUB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;     
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CFG_RUB_NOM_RUB);
            vecAux.add("" + INT_TBL_CFG_RUB_VAL_RUB);
            vecAux.add("" + INT_TBL_CFG_RUB_CHK_CIE);
            vecAux.add("" + INT_TBL_CFG_RUB_TXT_OBS);
            vecAux.add("" + INT_TBL_CFG_RUB_BUT_MES_EMB);   
            vecAux.add("" + INT_TBL_CFG_RUB_TXT_MES_EMB);  
            vecAux.add("" + INT_TBL_CFG_RUB_TXT_ANI_EMB);  
            vecAux.add("" + INT_TBL_CFG_RUB_BUT_MES_PUE);       
            vecAux.add("" + INT_TBL_CFG_RUB_TXT_MES_PUE); 
            vecAux.add("" + INT_TBL_CFG_RUB_TXT_ANI_PUE);
            vecAux.add("" + INT_TBL_CFG_RUB_NUM_REQ);
            objTblModCfgRub.addColumnasEditables(vecAux);
            vecAux=null;     
            
            //Establecer formato botón.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_EMB).setCellRenderer(objTblCelRenBut); 
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_PUE).setCellRenderer(objTblCelRenBut); 
            objTblCelRenBut=null;      
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_EMB).setResizable(false); 
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_PUE).setResizable(false); 
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtNomRub=new ZafTblCelEdiTxt(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_NOM_RUB).setCellEditor(objTblCelEdiTxtNomRub);   
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_OBS).setCellEditor(objTblCelEdiTxtNomRub);   
            objTblCelEdiTxtNomRub.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtNomRub.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiTxtNomRub.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtNomRub.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtNomRub.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                }
            });   
              
            //Edición de la celda Valor a Ingresar.
            objTblCelEdiTxtValRub = new ZafTblCelEdiTxt(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_VAL_RUB).setCellEditor(objTblCelEdiTxtValRub);
            objTblCelEdiTxtValRub.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtValRub.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  {  
                                    objTblCelEdiTxtValRub.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtValRub.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtValRub.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTotalPresupuesto();
                }
            });           
                        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkCieRub=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CFG_RUB_CHK_CIE).setCellRenderer(objTblCelRenChkCieRub);
            objTblCelRenChkCieRub=null;          
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCieRub=new ZafTblCelEdiChk(tblCfgRub); 
            tcmAux.getColumn(INT_TBL_CFG_RUB_CHK_CIE).setCellEditor(objTblCelEdiChkCieRub);   
            objTblCelEdiChkCieRub.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow(); 
                    if (intFilSel != -1) 
                    {
                        if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                        {
                            objTblCelEdiChkCieRub.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>No cuenta con el permiso para realizar cierre de rubros del presupuesto.<BR></HTML>"); 
                        }
                        else{
                            objTblCelEdiChkCieRub.setCancelarEdicion(false);
                        }                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Si se permite editar marcar/desmarcar
                        if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))   {                    
                            objTblModCfgRub.setValueAt(true, intFilSel, INT_TBL_CFG_RUB_CHK_CIE);
                        }
                        else {
                            objTblModCfgRub.setValueAt(false, intFilSel, INT_TBL_CFG_RUB_CHK_CIE);
                        }
                    }
                }
            });        
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Meses Embarque */">
            int intColVen[]=new int[2];            
            intColVen[0]=1; //co_mes
            intColVen[1]=2; //tx_mes
            int intColTbl[]=new int[2];            
            intColTbl[0]=INT_TBL_CFG_RUB_COD_MES_EMB;
            intColTbl[1]=INT_TBL_CFG_RUB_TXT_MES_EMB;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoMesEmb=new ZafTblCelEdiTxtVco(tblCfgRub, vcoMeses, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_MES_EMB).setCellEditor(objTblCelEdiTxtVcoMesEmb);
            objTblCelEdiTxtVcoMesEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtVcoMesEmb.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiTxtVcoMesEmb.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtVcoMesEmb.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtVcoMesEmb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                                
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMeses.limpiar();
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoMesEmb.isConsultaAceptada()){
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(1), intFilSel, INT_TBL_CFG_RUB_COD_MES_EMB);
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(2), intFilSel, INT_TBL_CFG_RUB_TXT_MES_EMB);
                    }
                }
            });
            
            //Búsqueda por botón.
            objTblCelEdiButVcoMesEmb=new ZafTblCelEdiButVco(tblCfgRub, vcoMeses, intColVen, intColTbl); 
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_EMB).setCellEditor(objTblCelEdiButVcoMesEmb);
            objTblCelEdiButVcoMesEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblCfgRub.getSelectedRow();   
                    if (intFilSel != -1)  
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiButVcoMesEmb.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiButVcoMesEmb.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiButVcoMesEmb.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiButVcoMesEmb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                      
                    
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMeses.limpiar();
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.setCriterio1(11);     
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoMesEmb.isConsultaAceptada()){ 
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(1), intFilSel, INT_TBL_CFG_RUB_COD_MES_EMB);
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(2), intFilSel, INT_TBL_CFG_RUB_TXT_MES_EMB);
                    }     
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>       
            
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Meses Puerto */">
            intColVen=new int[2];            
            intColVen[0]=1; //co_mes
            intColVen[1]=2; //tx_mes
            intColTbl=new int[2];            
            intColTbl[0]=INT_TBL_CFG_RUB_COD_MES_PUE;
            intColTbl[1]=INT_TBL_CFG_RUB_TXT_MES_PUE;

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoMesPue=new ZafTblCelEdiTxtVco(tblCfgRub, vcoMeses, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_MES_PUE).setCellEditor(objTblCelEdiTxtVcoMesPue);
            objTblCelEdiTxtVcoMesPue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblCfgRub.getSelectedRow();   
                    if (intFilSel != -1)  
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtVcoMesPue.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiTxtVcoMesPue.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtVcoMesPue.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtVcoMesPue.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                                   
                    
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMeses.limpiar();
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.setCriterio1(11);                      
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoMesPue.isConsultaAceptada()){
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(1), intFilSel, INT_TBL_CFG_RUB_COD_MES_PUE);
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(2), intFilSel, INT_TBL_CFG_RUB_TXT_MES_PUE);
                    }
                }
            });
            
            //Búsqueda por botón.
            objTblCelEdiButVcoMesPue=new ZafTblCelEdiButVco(tblCfgRub, vcoMeses, intColVen, intColTbl); 
            tcmAux.getColumn(INT_TBL_CFG_RUB_BUT_MES_PUE).setCellEditor(objTblCelEdiButVcoMesPue);
            objTblCelEdiButVcoMesPue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblCfgRub.getSelectedRow();   
                    if (intFilSel != -1)  
                    {
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiButVcoMesPue.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiButVcoMesPue.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiButVcoMesPue.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiButVcoMesPue.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                        
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMeses.limpiar();
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.setCriterio1(11);     
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoMesPue.isConsultaAceptada()){ 
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(1), intFilSel, INT_TBL_CFG_RUB_COD_MES_PUE);
                        objTblModCfgRub.setValueAt(vcoMeses.getValueAt(2), intFilSel, INT_TBL_CFG_RUB_TXT_MES_PUE);
                    }     
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>  
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtAniEmb=new ZafTblCelEdiTxt(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_ANI_EMB).setCellEditor(objTblCelEdiTxtAniEmb);   
            objTblCelEdiTxtAniEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                String strAniEmbBef="", strAniEmbAft="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        strAniEmbBef=objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_EMB)==null?"":objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_EMB).toString(); 
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtAniEmb.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiTxtAniEmb.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtAniEmb.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtAniEmb.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        strAniEmbAft=objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_EMB)==null?"":objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_EMB).toString();
                        if( (strAniEmbAft.length()>4)  ||  (!objUti.isNumero(strAniEmbAft))  )   {              
                            objTblModCfgRub.setValueAt(strAniEmbBef, intFilSel, INT_TBL_CFG_RUB_TXT_ANI_EMB);
                            mostrarMsgInf("<HTML>El año es incorrecto, verifique y vuelvo a intentarlo.</HTML>");
                        }
                    }                    
                }
            });  
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtAniPue=new ZafTblCelEdiTxt(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_TXT_ANI_PUE).setCellEditor(objTblCelEdiTxtAniPue);   
            objTblCelEdiTxtAniPue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                String strAniPueBef="", strAniPueAft="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        strAniPueBef=objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_PUE)==null?"":objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_PUE).toString(); 
                        if(!chkCiePreMen.isSelected()) {
                            if(!objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                            {
                                objTblCelEdiTxtAniPue.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar rubros del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgRub.isChecked(intFilSel, INT_TBL_CFG_RUB_CHK_CIE))  { 
                                    objTblCelEdiTxtAniPue.setCancelarEdicion(true); 
                                    mostrarMsgInf("<HTML>El rubro del presupuesto está cerrado.<BR>Aperture el rubro del presupuesto y vuelva intentarlo.</HTML>");
                                }
                                else {
                                    objTblCelEdiTxtAniPue.setCancelarEdicion(false);
                                }                            
                            }
                        }
                        else{
                            objTblCelEdiTxtAniPue.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>El presupuesto MENSUAL está cerrado, no puede realizar modificaciones.<BR>Solicite la apertura del presupuesto y vuelva intentarlo.</HTML>");
                        }
                    }                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgRub.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        strAniPueAft=objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_PUE)==null?"":objTblModCfgRub.getValueAt(intFilSel, INT_TBL_CFG_RUB_TXT_ANI_PUE).toString();
                        if( (strAniPueAft.trim().length()>4) ||  (!objUti.isNumero(strAniPueAft)) )   {              
                            objTblModCfgRub.setValueAt(strAniPueBef, intFilSel, INT_TBL_CFG_RUB_TXT_ANI_PUE);
                            mostrarMsgInf("<HTML>El año es incorrecto, verifique y vuelvo a intentarlo.</HTML>");
                        }
                    }                    
                }
            });
            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtNumReqPre=new ZafTblCelEdiTxt(tblCfgRub);
            tcmAux.getColumn(INT_TBL_CFG_RUB_NUM_REQ).setCellEditor(objTblCelEdiTxtNumReqPre);   
            objTblCelEdiTxtNumReqPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                String strAniPueBef="", strAniPueAft="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblCfgRub.getSelectedRow();                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblCfgRub.getSelectedRow();
                    intColSel = tblCfgPed.getSelectedColumn();
                    if (intFilSel != -1)   {
                        switch (intColSel)     {
                            case INT_TBL_CFG_PED_BUT_RUB:
                                mostrarVcoPreImp( tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_EMP).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_LOC).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_TIP_DOC).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_DOC).toString() );
                                break;  
                        }
                    }
                }
            });
            

            
            

            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_CFG_RUB_VAL_RUB};
            objTblTotCfgRub=new ZafTblTot(spnCfgRub, spnTotCfgRub, tblCfgRub, tblTotCfgRub, intCol);

            //Configurar JTable: Establecer el modo de operación.
            if(chkCiePreMen.isSelected())
                objTblModCfgRub.setModoOperacion(objTblModCfgRub.INT_TBL_NO_EDI); 
            else
                objTblModCfgRub.setModoOperacion(objTblModCfgRub.INT_TBL_INS); 
            
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaCfgRub extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCfgRub.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CFG_RUB_COD_EMP:
                    strMsg="Código de la empresa del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_COD_LOC:
                    strMsg="Código del local del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_COD_TIP_DOC:
                    strMsg="Código del tipo de documento del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_COD_DOC:
                    strMsg="Código del documento del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_COD_REG:
                    strMsg="Código del registro del presupuesto";
                    break;                    
                case INT_TBL_CFG_RUB_NOM_RUB:
                    strMsg="Descripción de rubros del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_VAL_RUB:
                    strMsg="Valor del rubro del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_CHK_CIE:
                    strMsg="Cierre de rubro del presupuesto";
                    break;
                case INT_TBL_CFG_RUB_TXT_OBS:
                    strMsg="Observación del rubro del presupuesto";
                    break; 
                case INT_TBL_CFG_RUB_BUT_MES_EMB:
                    strMsg="Mes de Embarque";
                    break;                          
                case INT_TBL_CFG_RUB_TXT_MES_EMB:
                    strMsg="Mes de Embarque";
                    break;                          
                case INT_TBL_CFG_RUB_TXT_ANI_EMB:
                    strMsg="Año de Embarque";
                    break;    
                case INT_TBL_CFG_RUB_BUT_MES_PUE:
                    strMsg="Mes de Puerto";
                    break;                        
                case INT_TBL_CFG_RUB_TXT_MES_PUE:
                    strMsg="Mes de Puerto";
                    break;                          
                case INT_TBL_CFG_RUB_TXT_ANI_PUE:
                    strMsg="Año de Puerto";
                    break;
                case INT_TBL_CFG_RUB_NUM_REQ:
                    strMsg="Número de requerimiento(máximo 8 caracteres)";
                    break;
                default:
                    break;
            }
            tblCfgRub.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura el JTable "tblMovPed".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCfgPed()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
                
            vecCab.add(INT_TBL_CFG_PED_LIN,"");
            vecCab.add(INT_TBL_CFG_PED_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_CFG_PED_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CFG_PED_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_CFG_PED_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_CFG_PED_NUM_PED,"Pedido");
            vecCab.add(INT_TBL_CFG_PED_VAL_PED,"Valor");
            vecCab.add(INT_TBL_CFG_PED_CHK_CIE,"Cie.");
            vecCab.add(INT_TBL_CFG_PED_FEC_COL,"Fec.Col.");
            vecCab.add(INT_TBL_CFG_PED_BUT_RUB,"");
            vecCab.add(INT_TBL_CFG_PED_TXT_OBS1,"Presupuesto");
            vecCab.add(INT_TBL_CFG_PED_TXT_OBS2,"Observación 2");
            vecCab.add(INT_TBL_CFG_PED_DAT_APL,"Dat.Apl.");
            vecCab.add(INT_TBL_CFG_PED_NUM_REQ,"Núm.Req.");

            objTblModCfgPed=new ZafTblMod();
            objTblModCfgPed.setHeader(vecCab);
            tblCfgPed.setModel(objTblModCfgPed);

            //Configurar JTable: Establecer tipo de selección.
            tblCfgPed.setRowSelectionAllowed(true);
            tblCfgPed.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCfgPed);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCfgPed.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCfgPed.getTableHeader().setReorderingAllowed(false);            
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCfgPed.getColumnModel();
            tcmAux.getColumn(INT_TBL_CFG_PED_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CFG_PED_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_PED_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_PED_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_PED_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CFG_PED_NUM_PED).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_CFG_PED_VAL_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CFG_PED_CHK_CIE).setPreferredWidth(35); 
            tcmAux.getColumn(INT_TBL_CFG_PED_FEC_COL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CFG_PED_BUT_RUB).setPreferredWidth(30); 
            tcmAux.getColumn(INT_TBL_CFG_PED_TXT_OBS1).setPreferredWidth(250); 
            tcmAux.getColumn(INT_TBL_CFG_PED_TXT_OBS2).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_CFG_PED_DAT_APL).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_CFG_PED_NUM_REQ).setPreferredWidth(70);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCfgPed.addSystemHiddenColumn(INT_TBL_CFG_PED_COD_EMP, tblCfgPed);
            objTblModCfgPed.addSystemHiddenColumn(INT_TBL_CFG_PED_COD_LOC, tblCfgPed);
            objTblModCfgPed.addSystemHiddenColumn(INT_TBL_CFG_PED_COD_TIP_DOC, tblCfgPed);
            objTblModCfgPed.addSystemHiddenColumn(INT_TBL_CFG_PED_COD_DOC, tblCfgPed);
            objTblModCfgPed.addSystemHiddenColumn(INT_TBL_CFG_PED_DAT_APL, tblCfgPed);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCfgPed=new ZafMouMotAdaCfgPed();
            tblCfgPed.getTableHeader().addMouseMotionListener(objMouMotAdaCfgPed);
            
            //Configurar JTable: Establecer formato
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CFG_PED_VAL_PED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;                
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModCfgPed.setColumnDataType(INT_TBL_CFG_PED_VAL_PED, objTblModCfgPed.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblCfgPed);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblCfgPed);
            tcmAux.getColumn(INT_TBL_CFG_PED_LIN).setCellRenderer(objTblFilCab);
 
           //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblCfgPed);   
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblCfgPed);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_CFG_PED_NUM_PED);
            vecAux.add("" + INT_TBL_CFG_PED_VAL_PED);
            //vecAux.add("" + INT_TBL_CFG_PED_CHK_CIE);   //No descomentar, la única forma que se podrá realizar cierres de pedidos será al ingresar NOTPED
            vecAux.add("" + INT_TBL_CFG_PED_FEC_COL);
            vecAux.add("" + INT_TBL_CFG_PED_BUT_RUB);
            vecAux.add("" + INT_TBL_CFG_PED_TXT_OBS2);
            vecAux.add("" + INT_TBL_CFG_PED_NUM_REQ);
            objTblModCfgPed.addColumnasEditables(vecAux);
            vecAux=null;        
            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtNumPed=new ZafTblCelEdiTxt(tblCfgPed);
            tcmAux.getColumn(INT_TBL_CFG_PED_NUM_PED).setCellEditor(objTblCelEdiTxtNumPed);   
            objTblCelEdiTxtNumPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            { 
                int intFilSel;
                String strNumPedAftEdi="", strNumPedBefEdi="";
                String strCodEmpPed="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgPed.getSelectedRow();
                    if (intFilSel != -1) 
                    {   
                        strCodEmpPed=objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_COD_EMP)==null?"":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_COD_EMP).toString();
                        strNumPedBefEdi=objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_NUM_PED)==null?"":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_NUM_PED).toString();
                        
                        /* Validaciones Varias */
                        if(chkMosPedAso.isSelected()) { //Valida que no este seleccionado el mostrar Pedidos
                            objTblCelEdiTxtNumPed.setCancelarEdicion(true);
                        }
                        else{
                            if(!objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                            {
                                objTblCelEdiTxtNumPed.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar pedidos del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgPed.isChecked(intFilSel, INT_TBL_CFG_PED_CHK_CIE))  {      
                                    objTblCelEdiTxtNumPed.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El pedido está cerrado, no es posible editar pedidos cerrados.</HTML>"); 
                                }
                                else {
                                    objTblCelEdiTxtNumPed.setCancelarEdicion(false);
                                }  
                                
                                
                                
//                                            if(strCodEmpPed.equals("")){ //Valida que sea un pedido nuevo.
//                                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
//                                                if(objTblModCfgPed.isChecked(intFilSel, INT_TBL_CFG_PED_CHK_CIE))  {      
//                                                    objTblCelEdiTxtNumPed.setCancelarEdicion(true);
//                                                    mostrarMsgInf("<HTML>El pedido está cerrado.<BR>No puede editar pedidos cerrados.</HTML>"); //OJO
//                                                }
//                                                else {
//                                                    objTblCelEdiTxtNumPed.setCancelarEdicion(false);
//                                                }                                     
//                                            }
//                                            else{ //Cuando un pedido ya existe, no se podrá modificar el número del pedido.
//                                                objTblCelEdiTxtNumPed.setCancelarEdicion(true);
//                                                mostrarMsgInf("<HTML>No es posible modificar el número de un pedido ya existente.</HTML>"); //OJO
//                                            }
                            }                        
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    strNumPedAftEdi=(objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_NUM_PED)==null?"":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_NUM_PED).toString()).trim();
                    if(validaExiNumPed(null, strNumPedAftEdi, intFilSel)) {
                        objTblModCfgPed.setValueAt(""+strNumPedBefEdi, intFilSel, INT_TBL_CFG_PED_NUM_PED);
                        mostrarMsgInf("<HTML>El número de Pedido "+strNumPedAftEdi+" ya existe.<BR>Indique otro número de pedido y vuelva a intentarlo.</HTML>");
                    }
                    else{
                        objTblModCfgPed.setValueAt(""+strNumPedAftEdi, intFilSel, INT_TBL_CFG_PED_NUM_PED); //Se setea el número de pedido sin espacios.
                    }
                }
            });   
            
            //Edición de la celda Valor a Ingresar.
            objTblCelEdiTxtValPed = new ZafTblCelEdiTxt(tblCfgPed);
            tcmAux.getColumn(INT_TBL_CFG_PED_VAL_PED).setCellEditor(objTblCelEdiTxtValPed);
            objTblCelEdiTxtValPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgPed.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Valida que no este seleccionado el mostrar Pedidos
                        if(chkMosPedAso.isSelected()) {
                            objTblCelEdiTxtNumPed.setCancelarEdicion(true);
                        }
                        else{                        
                            if(!objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/  
                            {
                                objTblCelEdiTxtValPed.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar pedidos del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgPed.isChecked(intFilSel, INT_TBL_CFG_PED_CHK_CIE))  {   
                                    objTblCelEdiTxtValPed.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El pedido está cerrado.<BR>No puede editar pedidos cerrados.</HTML>"); //OJO
                                }
                                else {
                                    objTblCelEdiTxtValPed.setCancelarEdicion(false);
                                }                            
                            }
                        }
                    }
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblTotCfgPed.calcularTotales();  //Calcular totales.
                }
            });            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtObs=new ZafTblCelEdiTxt(tblCfgPed);
            tcmAux.getColumn(INT_TBL_CFG_PED_TXT_OBS2).setCellEditor(objTblCelEdiTxtObs);
            objTblCelEdiTxtObs=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtNumReqNotPre=new ZafTblCelEdiTxt(tblCfgPed);
            tcmAux.getColumn(INT_TBL_CFG_PED_NUM_REQ).setCellEditor(objTblCelEdiTxtNumReqNotPre);   
            objTblCelEdiTxtNumReqNotPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                int intFilSel;
                String strObs2="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel=tblCfgPed.getSelectedRow();
                    strObs2=objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_TXT_OBS2)==null?"":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_TXT_OBS2).toString();
                    objTblCelEdiTxtNumReqNotPre.setCancelarEdicion(true);
                    if(strObs2.length()>0){
                        objTblCelEdiTxtNumReqNotPre.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                   
                }
            });
            
            
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkCiePed=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CFG_PED_CHK_CIE).setCellRenderer(objTblCelRenChkCiePed);
            objTblCelRenChkCiePed=null;          
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCiePed=new ZafTblCelEdiChk(tblCfgPed); 
            tcmAux.getColumn(INT_TBL_CFG_PED_CHK_CIE).setCellEditor(objTblCelEdiChkCiePed);   
            objTblCelEdiChkCiePed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgPed.getSelectedRow(); 
                    if (intFilSel != -1) 
                    {
                        if(!objPerUsr.isOpcionEnabled(4502))    /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                        {
                            objTblCelEdiChkCiePed.setCancelarEdicion(true);
                            mostrarMsgInf("<HTML>No cuenta con el permiso para realizar cierre de pedidos del presupuesto.<BR></HTML>"); 
                        }
                        else{
                            objTblCelEdiChkCiePed.setCancelarEdicion(false);
                        }                        
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblCfgPed.getSelectedRow();
                    if (intFilSel != -1) 
                    {
                        //Si se permite editar marcar/desmarcar
                        if(objTblModCfgPed.isChecked(intFilSel, INT_TBL_CFG_PED_CHK_CIE))   {                    
                            objTblModCfgPed.setValueAt(true, intFilSel, INT_TBL_CFG_PED_CHK_CIE);
                        }
                        else {
                            objTblModCfgPed.setValueAt(false, intFilSel, INT_TBL_CFG_PED_CHK_CIE);
                        }
                    }
                }
            });                    
       
            //Establecer JTable: Establecer Render Columnas DataPicker
            objTblCelRenLblDtePcki = new ZafTblCelRenLbl();
            objTblCelRenLblDtePcki.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_CFG_PED_FEC_COL).setCellRenderer(objTblCelRenLblDtePcki);            
            objTblCelRenLblDtePcki=null;               
            
            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecCol = new ZafDtePckEdi(strFormatoDatePicker);
            tcmAux.getColumn(INT_TBL_CFG_PED_FEC_COL).setCellEditor(objDtePckEdiFecCol);   
            objDtePckEdiFecCol.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblCfgPed.getSelectedRow(); 
                    if (intFilSel != -1) 
                    {
                        /* Validaciones Varias */
                        if(chkMosPedAso.isSelected()) { //Valida que no este seleccionado el mostrar Pedidos
                            objDtePckEdiFecCol.setCancelarEdicion(true);
                        }
                        else{
                            if(!objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                            {
                                objDtePckEdiFecCol.setCancelarEdicion(true);
                                mostrarMsgInf("<HTML>No cuenta con el permiso para editar pedidos del presupuesto.<BR></HTML>");
                            }
                            else{
                                //Valida Si pedido SI ha sido solicitado cierre, bloquear edición.
                                if(objTblModCfgPed.isChecked(intFilSel, INT_TBL_CFG_PED_CHK_CIE))  {      
                                    objDtePckEdiFecCol.setCancelarEdicion(true);
                                    mostrarMsgInf("<HTML>El pedido está cerrado, no puede editar pedidos cerrados.</HTML>"); 
                                }
                                else {
                                    objDtePckEdiFecCol.setCancelarEdicion(false);
                                }                                     
                            }                        
                        }     
                    }               
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });                        
            
            //Configurar JTable:  Botones.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_CFG_PED_BUT_RUB).setCellRenderer(objTblCelRenBut);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_CFG_PED_BUT_RUB).setResizable(false); 

            //Configurar JTable: Editor de celdas.
            objTblCelEdiBut= new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_CFG_PED_BUT_RUB).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                String strCodEmpPed;
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblCfgPed.getSelectedRow();
                    intColSel = tblCfgPed.getSelectedColumn();
                    strCodEmpPed = objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_COD_EMP)==null?"":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_COD_EMP).toString(); 
                    if (intFilSel != -1)  {
                        switch (intColSel)   {
                            case INT_TBL_CFG_PED_BUT_RUB:
                                if(!objPerUsr.isOpcionEnabled(4502)) {  /*Ficha "Configuración": Casilla "Modificar pedidos"*/
                                    objTblCelEdiBut.setCancelarEdicion(true);
                                }
                                else{
                                    if(strCodEmpPed.equals("")){
                                        objTblCelEdiBut.setCancelarEdicion(true);
                                    }
                                } 
                                break; 
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblCfgPed.getSelectedColumn();
                    if (intFilSel != -1)   {
                        switch (intColSel)     {
                            case INT_TBL_CFG_PED_BUT_RUB:
                                mostrarVcoPreImp( tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_EMP).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_LOC).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_TIP_DOC).toString()
                                                , tblCfgPed.getValueAt(tblCfgPed.getSelectedRow(), INT_TBL_CFG_PED_COD_DOC).toString() );
                                break;  
                        }
                    }
                }
            });             
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_CFG_PED_VAL_PED};
            objTblTotCfgPed=new ZafTblTot(spnCfgPed, spnTotCfgPed, tblCfgPed, tblTotCfgPed, intCol);
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModCfgPed.setModoOperacion(objTblModCfgPed.INT_TBL_INS);    
            
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaCfgPed extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCfgPed.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CFG_PED_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_CFG_PED_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_CFG_PED_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_CFG_PED_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_CFG_PED_NUM_PED:
                    strMsg="Número del pedido";
                    break;
                case INT_TBL_CFG_PED_VAL_PED:
                    strMsg="Valor del Pedido";
                    break;
                case INT_TBL_CFG_PED_CHK_CIE:
                    strMsg="Cierre del pedido";
                    break;                    
                case INT_TBL_CFG_PED_FEC_COL:
                    strMsg="Fecha de Colocación";
                    break;                    
                case INT_TBL_CFG_PED_BUT_RUB:
                    strMsg="Búsqueda de rubro del Presupuesto";
                    break; 
                case INT_TBL_CFG_PED_TXT_OBS1:
                    strMsg="Presupuesto (Observación 1)";
                    break;   
                case INT_TBL_CFG_PED_TXT_OBS2:
                    strMsg="Observación 2";
                    break;
                case INT_TBL_CFG_PED_DAT_APL:
                    strMsg="Datos Aplicados";
                    break;
                case INT_TBL_CFG_PED_NUM_REQ:
                    strMsg="Número de requerimiento que no se encuentran dentro del presupuesto";
                    break;
                default:
                    break;
            }
            tblCfgPed.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura el JTable "tblRptPedConPre".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblRptPedConPre()
    { 
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();     //Almacena los datos
            vecCab=new Vector();     //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_RPT_PED_PRE_LIN,"");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,"Cód.Emp.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,"Cód.Loc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,"Cód.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_REG_RUB,"Cód.Reg.");
            vecCab.add(INT_TBL_RPT_PED_PRE_NOM_RUB,"Presupuesto");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_RUB,"Valor Total");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_APL_RUB,"Valor Aplicado");
            vecCab.add(INT_TBL_RPT_PED_BUT_AUX,"");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_EMP_PED,"Cód.Emp.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_LOC_PED,"Cód.Loc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_DOC_PED,"Cód.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_NUM_PED,"Pedido");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_PED,"Valor");            
            vecCab.add(INT_TBL_RPT_PED_PRE_FEC_COL,"Fec.Col.");    
       
            objTblModRptPedConPre=new ZafTblMod();
            objTblModRptPedConPre.setHeader(vecCab);
            tblRptPedConPre.setModel(objTblModRptPedConPre);

            //Configurar JTable: Establecer tipo de selección.
            tblRptPedConPre.setRowSelectionAllowed(true);
            tblRptPedConPre.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblRptPedConPre);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRptPedConPre.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRptPedConPre.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRptPedConPre.getColumnModel();
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NOM_RUB).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NUM_PED).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED).setPreferredWidth(90);            
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_FEC_COL).setPreferredWidth(75);     
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRptPedConPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_RUB, objTblModRptPedConPre.INT_COL_DBL, new Integer(0), null); 
            objTblModRptPedConPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_APL_RUB, objTblModRptPedConPre.INT_COL_DBL, new Integer(0), null); 
            objTblModRptPedConPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_PED, objTblModRptPedConPre.INT_COL_DBL, new Integer(0), null);            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB, tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_REG_RUB,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_EMP_PED,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_LOC_PED,     tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED, tblRptPedConPre);
            objTblModRptPedConPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_DOC_PED,     tblRptPedConPre);    
            
            //Configurar JTable: Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrpPedConPre=(ZafTblHeaGrp)tblRptPedConPre.getTableHeader();
            objTblHeaGrpPedConPre.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColPedConPre=new ZafTblHeaColGrp("Presupuesto");
            objTblHeaColPedConPre.setHeight(16);
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NOM_RUB));
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB));
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB));
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX));
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NUM_PED));
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED));     
            objTblHeaColPedConPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_FEC_COL));
            objTblHeaGrpPedConPre.addColumnGroup(objTblHeaColPedConPre);            
 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaRpt=new ZafMouMotAdaRpt();
            tblRptPedConPre.getTableHeader().addMouseMotionListener(objMouMotAdaRpt);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblRptPedConPre);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblRptPedConPre);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            //objTblOrd=new ZafTblOrd(tblRptPedConPre);
            
            //Establecer formato botón.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX).setCellRenderer(objTblCelRenBut); 
            objTblCelRenBut=null;                  
            
            //Configurar JTable: Establecer Color
            objTblCelRenLblColNomRub = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NOM_RUB).setCellRenderer(objTblCelRenLblColNomRub); 
            objTblCelRenLblColNomRub.addTblCelRenListener(new ZafTblCelRenAdapter()  
            {
                java.awt.Color colFonColFilOcu;
                String strLinCol="";
                int intFil;
                public void beforeRender(ZafTblCelRenEvent evt) {
                    intFil=objTblCelRenLblColNomRub.getRowRender();
                    colFonColFilOcu=new java.awt.Color(228,228,203);
                    strLinCol=objTblModRptPedConPre.getValueAt(intFil, INT_TBL_RPT_PED_PRE_LIN)==null?"":objTblModRptPedConPre.getValueAt(intFil, INT_TBL_RPT_PED_PRE_LIN).toString();
                    if (strLinCol.equals("H")){
                        objTblCelRenLblColNomRub.setForeground(colFonColFilOcu);
                        objTblModRptPedConPre.setValueAt("", intFil, INT_TBL_RPT_PED_PRE_NOM_RUB);
                    }
                    else{
                        objTblCelRenLblColNomRub.setForeground(Color.BLACK);
                    }
                }
            });
            
            //Configurar JTable: Establecer Color y formato
            objTblCelRenLblColValRub=new ZafTblCelRenLbl();
            objTblCelRenLblColValRub.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColValRub.setTipoFormato(objTblCelRenLblColValRub.INT_FOR_NUM);
            objTblCelRenLblColValRub.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB).setCellRenderer(objTblCelRenLblColValRub);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB).setCellRenderer(objTblCelRenLblColValRub);
            objTblCelRenLblColValRub.addTblCelRenListener(new ZafTblCelRenAdapter()  
            {
                java.awt.Color colFonColFilOcu;
                String strLinCol=""; 
                int intFil;
                public void beforeRender(ZafTblCelRenEvent evt) {
                    intFil=objTblCelRenLblColValRub.getRowRender();
                    colFonColFilOcu=new java.awt.Color(228,228,203);
                    strLinCol=objTblModRptPedConPre.getValueAt(intFil, INT_TBL_RPT_PED_PRE_LIN)==null?"":objTblModRptPedConPre.getValueAt(intFil, INT_TBL_RPT_PED_PRE_LIN).toString();
                    if (strLinCol.equals("H")){
                        objTblCelRenLblColValRub.setForeground(colFonColFilOcu);
                        objTblModRptPedConPre.setValueAt("", intFil, INT_TBL_RPT_PED_PRE_VAL_RUB);
                        objTblModRptPedConPre.setValueAt("", intFil, INT_TBL_RPT_PED_PRE_VAL_APL_RUB);
                        calcularTotalesJTables();  //Despues de setear la columna del rubro en blanco, se vuelve a calcular los subtotales y totales del reporte.
                    }
                    else{
                        objTblCelRenLblColValRub.setForeground(Color.BLACK);
                    }
                }
            });                        
                        
            //Configurar JTable: Establecer formato
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;                 
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_RPT_PED_PRE_VAL_RUB, INT_TBL_RPT_PED_PRE_VAL_APL_RUB, INT_TBL_RPT_PED_PRE_VAL_PED};
            objTblTotRptPedConPre=new ZafTblTot(spnRptPedConPre, spnTotRptPedConPre, tblRptPedConPre, tblTotRptPedConPre, intCol);

            //Configurar JTable: Establecer el modo de operación.
            objTblModRptPedConPre.setModoOperacion(objTblModRptPedConPre.INT_TBL_NO_EDI);
            
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
     * Esta función configura el JTable "tblRptPedSinPre".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblRptPedSinPre()
    { 
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();     //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_RPT_PED_PRE_LIN,"");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,"Cód.Emp.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,"Cód.Loc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,"Cód.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_REG_RUB,"Cód.Reg.");
            vecCab.add(INT_TBL_RPT_PED_PRE_NOM_RUB,"Presupuesto");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_RUB,"Valor Total");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_APL_RUB,"Valor Aplicado");
            vecCab.add(INT_TBL_RPT_PED_BUT_AUX,"");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_EMP_PED,"Cód.Emp.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_LOC_PED,"Cód.Loc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_COD_DOC_PED,"Cód.Doc.");
            vecCab.add(INT_TBL_RPT_PED_PRE_NUM_PED,"Pedido");
            vecCab.add(INT_TBL_RPT_PED_PRE_VAL_PED,"Valor");            
            vecCab.add(INT_TBL_RPT_PED_PRE_FEC_COL,"Fec.Col.");    
       
            objTblModRptPedSinPre=new ZafTblMod();
            objTblModRptPedSinPre.setHeader(vecCab);
            tblRptPedSinPre.setModel(objTblModRptPedSinPre);

            //Configurar JTable: Establecer tipo de selección.
            tblRptPedSinPre.setRowSelectionAllowed(true);
            tblRptPedSinPre.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblRptPedSinPre);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblRptPedSinPre.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblRptPedSinPre.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblRptPedSinPre.getColumnModel();
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NOM_RUB).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NUM_PED).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED).setPreferredWidth(90);            
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_FEC_COL).setPreferredWidth(75);     
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRptPedSinPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_RUB, objTblModRptPedSinPre.INT_COL_DBL, new Integer(0), null);            
            objTblModRptPedSinPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_APL_RUB, objTblModRptPedSinPre.INT_COL_DBL, new Integer(0), null);  
            objTblModRptPedSinPre.setColumnDataType(INT_TBL_RPT_PED_PRE_VAL_PED, objTblModRptPedSinPre.INT_COL_DBL, new Integer(0), null);            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB, tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_REG_RUB,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_EMP_PED,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_LOC_PED,     tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED, tblRptPedSinPre);
            objTblModRptPedSinPre.addSystemHiddenColumn(INT_TBL_RPT_PED_PRE_COD_DOC_PED,     tblRptPedSinPre);  
            
            //Establecer formato botón.
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX).setCellRenderer(objTblCelRenBut); 
            objTblCelRenBut=null;            
            
            //Configurar JTable: Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrpPedSinPre=(ZafTblHeaGrp)tblRptPedSinPre.getTableHeader();
            objTblHeaGrpPedSinPre.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColPedSinPre=new ZafTblHeaColGrp("Pedidos SIN Presupuesto");
            objTblHeaColPedSinPre.setHeight(16);
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NOM_RUB));
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB));
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB));
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_BUT_AUX));
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_NUM_PED));
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED));            
            objTblHeaColPedSinPre.add(tcmAux.getColumn(INT_TBL_RPT_PED_PRE_FEC_COL));
            objTblHeaGrpPedSinPre.addColumnGroup(objTblHeaColPedSinPre);                  

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaRpt=new ZafMouMotAdaRpt();
            tblRptPedSinPre.getTableHeader().addMouseMotionListener(objMouMotAdaRpt);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblRptPedSinPre);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblRptPedSinPre);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_LIN).setCellRenderer(objTblFilCab);
 
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblRptPedSinPre);
            
            //Configurar JTable: Establecer formato
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_RUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_APL_RUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_RPT_PED_PRE_VAL_PED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;     
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_RPT_PED_PRE_VAL_RUB, INT_TBL_RPT_PED_PRE_VAL_APL_RUB, INT_TBL_RPT_PED_PRE_VAL_PED};
            objTblTotRptPedSinPre=new ZafTblTot(spnRptPedSinPre, spnTotRptPedSinPre, tblRptPedSinPre, tblTotRptPedSinPre, intCol); 
            
            //Configurar JTable: Establecer el modo de operación.
            objTblModRptPedSinPre.setModoOperacion(objTblModRptPedSinPre.INT_TBL_NO_EDI);
            
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaRpt extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCfgRub.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_RPT_PED_PRE_NOM_RUB:
                    strMsg="Descripción de rubros del presupuesto";
                    break;
                case INT_TBL_RPT_PED_PRE_VAL_RUB:
                    strMsg="Valor del rubro del presupuesto";
                    break;
                case INT_TBL_RPT_PED_PRE_VAL_APL_RUB:
                    strMsg="Valor total aplicado al rubro del presupuesto";
                    break;                    
                case INT_TBL_RPT_PED_PRE_NUM_PED:
                    strMsg="Número de pedido";
                    break;
                case INT_TBL_RPT_PED_PRE_VAL_PED:
                    strMsg="Valor del pedido";
                    break;
                case INT_TBL_RPT_PED_PRE_FEC_COL:
                    strMsg="Fecha de colocación del pedido";
                    break;                    
                default:
                    break;
            }
            tblRptPedConPre.getTableHeader().setToolTipText(strMsg);
            tblRptPedSinPre.getTableHeader().setToolTipText(strMsg);
        }
    }   
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisMovPed implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty())
            {
//                if (chkMosPedAso.isSelected())      
//                    cargarPedAso();  
//                else
//                    cargarCfgPed();    
            }
        }
    }    
    
    /**
     * Esta función configura el JTable "Totales".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblTot()
    { 
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
//            for (int j=0; j<tblRptPedSinPre.getColumnCount(); j++){
//                tblTotRpt.getColumnModel().getColumn(j).setWidth(tblRptPedSinPre.getColumnModel().getColumn(j).getWidth());
//                tblTotRpt.getColumnModel().getColumn(j).setMaxWidth(tblRptPedSinPre.getColumnModel().getColumn(j).getMaxWidth());
//                tblTotRpt.getColumnModel().getColumn(j).setMinWidth(tblRptPedSinPre.getColumnModel().getColumn(j).getMinWidth());
//                tblTotRpt.getColumnModel().getColumn(j).setPreferredWidth(tblRptPedSinPre.getColumnModel().getColumn(j).getPreferredWidth());
//                tblTotRpt.getColumnModel().getColumn(j).setResizable(tblRptPedSinPre.getColumnModel().getColumn(j).getResizable());
//            }
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intColTotRpt[]={INT_TBL_RPT_PED_PRE_VAL_RUB, INT_TBL_RPT_PED_PRE_VAL_APL_RUB, INT_TBL_RPT_PED_PRE_VAL_PED}; 
            objTblTotRpt=new ZafTblTot(spnRptPedSinPre, spnTotRpt, tblRptPedSinPre, tblTotRpt, intColTotRpt);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    private boolean cambiarColorRubros(){
        boolean blnRes=true;
        int intCodRegAct=-1;
        int intCodRegAnt=-1;
        int intCam=0;
        String strLin="";
        try{
            for(int i=(objTblModRptPedConPre.getRowCountTrue()-1);i>=0; i--){
                strLin=objTblModRptPedConPre.getValueAt(i, INT_TBL_RPT_PED_PRE_LIN)==null?"":objTblModRptPedConPre.getValueAt(i, INT_TBL_RPT_PED_PRE_LIN).toString();

                if(strLin.equals("")){
                    objTblModRptPedConPre.setValueAt("M", i, INT_TBL_RPT_PED_PRE_LIN);
                    if(intCam==0){
                        intCodRegAct=Integer.parseInt(objTblModRptPedConPre.getValueAt(i, INT_TBL_RPT_PED_PRE_COD_REG_RUB)==null?"-1":objTblModRptPedConPre.getValueAt(i, INT_TBL_RPT_PED_PRE_COD_REG_RUB).toString());
                        intCam++;
                    }

                    if((i-1)>=0){                        
                        intCodRegAnt=Integer.parseInt(objTblModRptPedConPre.getValueAt((i-1), INT_TBL_RPT_PED_PRE_COD_REG_RUB)==null?"-1":objTblModRptPedConPre.getValueAt((i-1), INT_TBL_RPT_PED_PRE_COD_REG_RUB).toString());

                        if( intCodRegAct==intCodRegAnt ){
                            objTblModRptPedConPre.setValueAt("H", i, INT_TBL_RPT_PED_PRE_LIN);
                        }
                        else{
                            intCam=0;
                            i++;
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
     * Esta función permite cargar el ultimo período.(Anio y Mes).
     * @return true: Si se pudo consultar el período.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPeriodo()
    {
        boolean blnRes=true;
        boolean blnUltFec = true;
        String strMes="" , strAnio;
        int intMes ;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();      
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a.ne_ani, a.ne_mes";
                strSQL+=" FROM (";
                strSQL+="    SELECT CAST ( (EXTRACT(YEAR FROM (CURRENT_DATE))) AS SMALLINT) AS ne_ani";
                strSQL+="         , CAST ( (EXTRACT(MONTH FROM (CURRENT_DATE))) AS SMALLINT) AS ne_mes";
                strSQL+="    UNION ";
                strSQL+="    SELECT ne_ani, ne_mes FROM tbm_cabPreImp"; 
                strSQL+=" ) AS a";
                strSQL+=" GROUP BY a.ne_ani, a.ne_mes";
                strSQL+=" ORDER BY a.ne_ani desc, a.ne_mes desc ";
                rst = stm.executeQuery(strSQL);
                DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox.
                //Obtener los registros.
                while (rst.next())
                {
                    if (blnUltFec) 
                    {
                        strMes = rst.getString("ne_mes");
                        blnUltFec = false;
                    }
                    strAnio = rst.getString("ne_ani");
                    
                    //Pregunto si existe el anio para que no se repita al momento de llenarlo en el combobox
                    if (model.getIndexOf(strAnio) == -1)  
                    {
                        model.addElement(strAnio);
                        cboPerAAAA.addItem(strAnio);
                    }
                }
                //Presenta el mes que aun no ha sido cerrado el rol.
                intMes = Integer.valueOf(strMes);
                cboPerMes.setSelectedIndex(intMes);
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)  {       blnRes = false;       objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {    blnRes = false;        objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }    
        
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" FROM tbm_cabPedEmbImp AS a1";
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
            strSQL+=" INNER JOIN ("; //Muestra solo los pedidos consolidados
            strSQL+="      SELECT COUNT(co_exp) as ne_numExp, co_Emp, co_loc, co_tiPDoc, co_Doc";
            strSQL+="      FROM ( SELECT DISTINCT co_Exp, co_emp, co_loc, co_tipDoc, co_doc FROM tbm_detPedEmbImp AS a3 ) as x ";    
            strSQL+="      GROUP BY co_emp, co_loc, co_tipDoc, co_doc ORDER BY co_doc, ne_numExp";
            strSQL+=" ) AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc AND a3.ne_numExp>1";               
            strSQL+=" WHERE a1.st_Reg='A' ";
            strSQL+=" ORDER BY a1.fe_doc, a1.tx_numDoc2";
            
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void mostrarVcoPreImp(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc)
    {
        ArrayList arlRubAso=new ArrayList();
        String strArlSQL="";
        String strRubObsArl="", strDatAplArl="";
        int intFilSel=tblCfgPed.getSelectedRow();
        String strNumReqPedSel="";
        try{
            objImp35_01.arlDatPedImp= new ArrayList();
            objImp35_01.arlRegPedImp= new ArrayList();
            objImp35_01.arlRegPedImp.add(objImp35_01.INT_ARL_DAT_PED_COD_EMP,     strCodEmp);
            objImp35_01.arlRegPedImp.add(objImp35_01.INT_ARL_DAT_PED_COD_LOC,     strCodLoc);
            objImp35_01.arlRegPedImp.add(objImp35_01.INT_ARL_DAT_PED_COD_TIP_DOC, strCodTipDoc);
            objImp35_01.arlRegPedImp.add(objImp35_01.INT_ARL_DAT_PED_COD_DOC,     strCodDoc);
            objImp35_01.arlDatPedImp.add(objImp35_01.arlRegPedImp);
            
            //Armar sentencia SQL
            String strDatAplTbl=(objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_DAT_APL)==null? "":objTblModCfgPed.getValueAt(intFilSel, INT_TBL_CFG_PED_DAT_APL).toString()); 
            
            strArlSQL ="";
            strArlSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.co_reg, z.tx_nomRub";
            strArlSQL+="      , z.nd_ValTotDoc, z.nd_valTotApl, z.nd_valTotPen, z.nd_valapl";
            strArlSQL+="      , z.st_ciePre, z.st_cieRub, z.ne_ani, z.ne_mes, z.st_aplRub, z.tx_numReq";
            strArlSQL+=" FROM (";
            strArlSQL+="      SELECT a1.ne_ani, a1.ne_mes, a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.co_Reg, a.tx_des AS tx_nomRub ";
            strArlSQL+="           , a.nd_valTot AS nd_ValTotDoc, a.nd_ValApl AS nd_ValTotApl, (a.nd_ValTot - a.nd_ValApl) AS nd_ValTotPen, CAST('' AS CHARACTER VARYING) AS nd_valapl";
            strArlSQL+="           , a1.st_cie AS st_ciePre, a.st_cie AS st_cieRub";
            if(strDatAplTbl.equals("")) {//Observación 2(No está en el presupuesto)
                strArlSQL+="       , CAST((CASE WHEN a2.st_aplRub IS NOT NULL THEN a2.st_aplRub ELSE 'N' END) AS CHARACTER VARYING) AS st_aplRub";
                strArlSQL+="       , CAST('' AS CHARACTER VARYING) AS tx_numReq";
            }
            else{//está en presupuesto
                strArlSQL+="       , CAST('N' AS CHARACTER VARYING) AS st_aplRub ";
                strArlSQL+="       , a.tx_numReq";
            }
            strArlSQL+="      FROM tbm_DetPreImp AS a";
            strArlSQL+="      INNER JOIN tbm_CabPreImp AS a1 ON a.co_Emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
            if(strDatAplTbl.equals("")) {//Observación 2(No está en el presupuesto)
                strArlSQL+="  LEFT OUTER JOIN ( ";
                strArlSQL+="       SELECT a.*, CAST('S' AS CHARACTER VARYING) AS st_aplRub ";
                strArlSQL+="       FROM tbr_detPreImp AS a INNER JOIN tbm_CabNotPedPreImp AS a1 ";
                strArlSQL+="       ON a.co_empRelCabNotPedPreImp=a1.co_Emp AND a.co_locRelCabNotPedPreImp=a1.co_loc AND a.co_tipDocRelCabNotPedPreImp=a1.co_tipDoc AND a.co_docRelCabNotPedPreImp=a1.co_doc";
                strArlSQL+="       WHERE a1.co_Emp="+strCodEmp+" AND a1.co_loc="+strCodLoc+" AND a1.co_tipDoc="+strCodTipDoc+" AND a1.co_doc="+strCodDoc+" ";
                strArlSQL+="  ) AS a2 ON a.co_Emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_tipDoc=a2.co_tipDoc AND a.co_doc=a2.co_doc AND a.co_reg=a2.co_reg";
            }
            else{//está en presupuesto
                strArlSQL+="  AND NOT EXISTS ( "; 	
                strArlSQL+=" 	SELECT * FROM (" + strDatAplTbl + ") AS x";
                strArlSQL+=" 	WHERE x.co_emp=a.co_emp AND x.co_loc=a.co_loc AND x.co_tipDoc=a.co_tipDoc AND x.co_doc=a.co_doc AND x.co_reg=a.co_reg";
                strArlSQL+=" )";
                strArlSQL+=" UNION ";
                strArlSQL+=" SELECT a1.ne_ani, a1.ne_mes, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a.tx_des AS tx_nomRub";
                strArlSQL+="      , a.nd_valTot AS nd_ValTotDoc, a.nd_ValApl AS nd_ValTotApl, (a.nd_ValTot - a.nd_ValApl) AS nd_ValTotPen, ''||a2.nd_valapl";
                strArlSQL+="      , a1.st_cie AS st_ciePre, a.st_cie AS st_cieRub, CAST('S' AS CHARACTER VARYING) AS st_aplRub, a.tx_numReq";
                strArlSQL+=" FROM ( " + strDatAplTbl + " ) AS a2";
                strArlSQL+=" INNER JOIN tbm_DetPreImp AS a ON a2.co_emp=a.co_emp AND a2.co_loc=a.co_loc AND a2.co_tipDoc=a.co_tipDoc AND a2.co_doc=a.co_doc AND a2.co_reg=a.co_reg";
                strArlSQL+=" INNER JOIN tbm_cabPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
            }
            strArlSQL+=" ) AS z";
            strArlSQL+=" WHERE z.ne_ani="+cboPerAAAA.getSelectedItem().toString()+" AND z.ne_mes="+cboPerMes.getSelectedIndex();
            strArlSQL+=" GROUP BY z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.co_reg, z.tx_nomRub";
            strArlSQL+="        , z.nd_ValTotDoc, z.nd_valTotApl, z.nd_valTotPen, z.nd_valapl";
            strArlSQL+="        , z.st_ciePre, z.st_cieRub, z.ne_ani, z.ne_mes, z.st_aplRub, z.tx_numReq";            
            strArlSQL+=" ORDER BY z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.co_Reg";          
            System.out.println("VentanaRubrosAplicados: "+strArlSQL);
            Importaciones.ZafImp35.ZafImp35_01 obj=new Importaciones.ZafImp35.ZafImp35_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strArlSQL, objImp35_01.arlDatPedImp);
            obj.setVisible(true);
            if(obj.isAceptar())
            {
                arlRubAso=obj.getArlPreSel();
                strRubObsArl=""; strDatAplArl="";
                strNumReqPedSel="";
                for(int i=0;i<arlRubAso.size();i++) 
                {
                    //Observación de rubros asociados.
                    strRubObsArl+=""+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_NOM_RUB_PRE)+";";
                    
                    //Obtiene Datos Aplicados
                    if(!strDatAplArl.equals("")) strDatAplArl+=" UNION ";
                    strDatAplArl+=" SELECT "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_COD_EMP_PRE)+" AS co_emp";
                    strDatAplArl+="      , "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_COD_LOC_PRE)+" AS co_loc";
                    strDatAplArl+="      , "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_COD_TIP_DOC_PRE)+" AS co_tipDoc";
                    strDatAplArl+="      , "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_COD_DOC_PRE)+" AS co_doc";
                    strDatAplArl+="      , "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_COD_REG_PRE)+" AS co_reg";
                    strDatAplArl+="      , "+objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_VAL_APL_PRE)+" AS nd_valapl";

                    
                    if(strNumReqPedSel.length()>0)
                        strNumReqPedSel+=(";" + objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_NUM_REQ));
                    else
                        strNumReqPedSel+=("" + objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_NUM_REQ));
                    
                    //strNumReqPedSel+=objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_NUM_REQ).length()<=0?"":(";" + objUti.getStringValueAt(arlRubAso, i, objImp35_01.INT_ARL_ASO_NUM_REQ));
                    
                }          
                
                if(!strDatAplArl.equals("")){
                    strDatAplArl=" " + strDatAplArl + " ";
                }
                objTblModCfgPed.setValueAt(""+strRubObsArl, intFilSel, INT_TBL_CFG_PED_TXT_OBS1); 
                objTblModCfgPed.setValueAt(""+strDatAplArl, intFilSel, INT_TBL_CFG_PED_DAT_APL);
                
                
                objTblModCfgPed.setValueAt(strNumReqPedSel, intFilSel, INT_TBL_CFG_PED_NUM_REQ);
                
                
                
            } 
        }
        catch (Exception e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        boolean blnRes=false;
        try 
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()); 
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                //Armar la sentencia SQL.
//                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
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
//                }
//                else{
//                    strSQL="";
//                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
//                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
//                    strSQL+=" FROM tbm_cabTipDoc AS a1";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
//                    strSQL+=" AND a1.co_tipDoc=";
//                    strSQL+=" (";
//                    strSQL+=" SELECT co_tipDoc";
//                    strSQL+=" FROM tbr_tipDocUsr";
//                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
//                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
//                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
//                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
//                    strSQL+=" AND st_reg='S'";
//                    strSQL+=" )";
//                }
                rstLoc=stmLoc.executeQuery(strSQL); 
                if (rstLoc.next())
                {
                    txtCodTipDoc.setText(rstLoc.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rstLoc.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rstLoc.getString("tx_desLar"));
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Meses".
     */
    private boolean configurarVenConMes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mes");
            arlCam.add("a1.tx_mes");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Mes.");
            arlAli.add("Mes");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_mesemb AS co_mes, a1.tx_desLar AS tx_mes";
            strSQL+=" FROM tbm_mesembimp AS a1";
            strSQL+=" WHERE a1.st_reg='A'"; 
            //strSQL+=" AND a1.co_mesEmb NOT IN (2,4,6,7,8,10,12,14,16,18,20, 22,24)";
            strSQL+=" ORDER BY a1.co_mesemb";            
            vcoMeses=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de meses", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMeses.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFilCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblPer = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        panCfgPre = new javax.swing.JPanel();
        panCfgCab = new javax.swing.JPanel();
        lblValTot = new javax.swing.JLabel();
        txtValTotPre = new javax.swing.JTextField();
        chkCiePreMen = new javax.swing.JCheckBox();
        sppCfgPre = new javax.swing.JSplitPane();
        panCfgRub = new javax.swing.JPanel();
        spnCfgRub = new javax.swing.JScrollPane();
        tblCfgRub = new javax.swing.JTable();
        spnTotCfgRub = new javax.swing.JScrollPane();
        tblTotCfgRub = new javax.swing.JTable();
        panCfgPed = new javax.swing.JPanel();
        spnCfgPed = new javax.swing.JScrollPane();
        tblCfgPed = new javax.swing.JTable();
        spnTotCfgPed = new javax.swing.JScrollPane();
        tblTotCfgPed = new javax.swing.JTable();
        chkMosPedAso = new javax.swing.JCheckBox();
        panObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptPedConPre = new javax.swing.JPanel();
        spnRptPedConPre = new javax.swing.JScrollPane();
        tblRptPedConPre = new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotRptPedConPre = new javax.swing.JScrollPane();
        tblTotRptPedConPre = new javax.swing.JTable();
        panRptPedSinPre = new javax.swing.JPanel();
        spnRptPedSinPre = new javax.swing.JScrollPane();
        tblRptPedSinPre = new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotRptPedSinPre = new javax.swing.JScrollPane();
        tblTotRptPedSinPre = new javax.swing.JTable();
        panTotRpt = new javax.swing.JPanel();
        spnTotRpt = new javax.swing.JScrollPane();
        tblTotRpt = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(new java.awt.BorderLayout());

        panFilCab.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFilCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFilCab.add(lblTipDoc);
        lblTipDoc.setBounds(20, 30, 120, 20);
        panFilCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 30, 10, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panFilCab.add(txtCodDoc);
        txtCodDoc.setBounds(450, 30, 60, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panFilCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 30, 80, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panFilCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(220, 30, 230, 20);

        lblPer.setText("Periodo:");
        panFilCab.add(lblPer);
        lblPer.setBounds(20, 60, 110, 20);

        cboPerAAAA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPerAAAAItemStateChanged(evt);
            }
        });
        panFilCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 60, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        cboPerMes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboPerMesItemStateChanged(evt);
            }
        });
        panFilCab.add(cboPerMes);
        cboPerMes.setBounds(220, 60, 120, 20);

        panFil.add(panFilCab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

        panCfgPre.setPreferredSize(new java.awt.Dimension(461, 800));
        panCfgPre.setLayout(new java.awt.BorderLayout());

        panCfgCab.setPreferredSize(new java.awt.Dimension(10, 40));
        panCfgCab.setLayout(null);

        lblValTot.setText("Valor Total Presupuesto:");
        panCfgCab.add(lblValTot);
        lblValTot.setBounds(5, 10, 190, 20);

        txtValTotPre.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCfgCab.add(txtValTotPre);
        txtValTotPre.setBounds(200, 10, 90, 20);

        chkCiePreMen.setText("Cierre de Presupuesto");
        chkCiePreMen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkCiePreMenItemStateChanged(evt);
            }
        });
        panCfgCab.add(chkCiePreMen);
        chkCiePreMen.setBounds(300, 10, 190, 20);

        panCfgPre.add(panCfgCab, java.awt.BorderLayout.PAGE_START);

        sppCfgPre.setDividerLocation(130);
        sppCfgPre.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppCfgPre.setResizeWeight(0.8);
        sppCfgPre.setOneTouchExpandable(true);

        panCfgRub.setPreferredSize(new java.awt.Dimension(452, 140));
        panCfgRub.setLayout(new java.awt.BorderLayout());

        spnCfgRub.setPreferredSize(new java.awt.Dimension(452, 200));

        tblCfgRub.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCfgRub.setViewportView(tblCfgRub);

        panCfgRub.add(spnCfgRub, java.awt.BorderLayout.CENTER);

        spnTotCfgRub.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotCfgRub.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotCfgRub.setViewportView(tblTotCfgRub);

        panCfgRub.add(spnTotCfgRub, java.awt.BorderLayout.SOUTH);

        sppCfgPre.setTopComponent(panCfgRub);

        panCfgPed.setPreferredSize(new java.awt.Dimension(452, 550));
        panCfgPed.setLayout(new java.awt.BorderLayout());

        spnCfgPed.setPreferredSize(new java.awt.Dimension(452, 560));

        tblCfgPed.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCfgPed.setViewportView(tblCfgPed);

        panCfgPed.add(spnCfgPed, java.awt.BorderLayout.CENTER);

        spnTotCfgPed.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotCfgPed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotCfgPed.setViewportView(tblTotCfgPed);

        panCfgPed.add(spnTotCfgPed, java.awt.BorderLayout.SOUTH);

        chkMosPedAso.setText("Mostrar los pedidos asociados al rubro seleccionado."); // NOI18N
        chkMosPedAso.setPreferredSize(new java.awt.Dimension(269, 20));
        panCfgPed.add(chkMosPedAso, java.awt.BorderLayout.NORTH);

        sppCfgPre.setBottomComponent(panCfgPed);

        panCfgPre.add(sppCfgPre, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 42));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        panObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.BorderLayout());

        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1, java.awt.BorderLayout.CENTER);
        panGenTxtObs.add(spnObs2, java.awt.BorderLayout.PAGE_START);

        panObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panCfgPre.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Configuración", panCfgPre);
        panCfgPre.getAccessibleContext().setAccessibleName("");
        panCfgPre.getAccessibleContext().setAccessibleParent(tabFrm);

        panRpt.setPreferredSize(new java.awt.Dimension(461, 800));
        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setDividerLocation(160);
        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.8);
        sppRpt.setOneTouchExpandable(true);
        sppRpt.setPreferredSize(new java.awt.Dimension(454, 707));

        panRptPedConPre.setPreferredSize(new java.awt.Dimension(452, 150));
        panRptPedConPre.setLayout(new java.awt.BorderLayout());

        spnRptPedConPre.setPreferredSize(new java.awt.Dimension(452, 80));

        tblRptPedConPre.setModel(new javax.swing.table.DefaultTableModel(
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
        spnRptPedConPre.setViewportView(tblRptPedConPre);

        panRptPedConPre.add(spnRptPedConPre, java.awt.BorderLayout.CENTER);

        spnTotRptPedConPre.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotRptPedConPre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotRptPedConPre.setViewportView(tblTotRptPedConPre);

        panRptPedConPre.add(spnTotRptPedConPre, java.awt.BorderLayout.SOUTH);

        sppRpt.setTopComponent(panRptPedConPre);

        panRptPedSinPre.setPreferredSize(new java.awt.Dimension(452, 500));
        panRptPedSinPre.setLayout(new java.awt.BorderLayout());

        spnRptPedSinPre.setPreferredSize(new java.awt.Dimension(452, 500));

        tblRptPedSinPre.setModel(new javax.swing.table.DefaultTableModel(
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
        spnRptPedSinPre.setViewportView(tblRptPedSinPre);

        panRptPedSinPre.add(spnRptPedSinPre, java.awt.BorderLayout.CENTER);

        spnTotRptPedSinPre.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotRptPedSinPre.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotRptPedSinPre.setViewportView(tblTotRptPedSinPre);

        panRptPedSinPre.add(spnTotRptPedSinPre, java.awt.BorderLayout.SOUTH);

        sppRpt.setBottomComponent(panRptPedSinPre);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

        panTotRpt.setPreferredSize(new java.awt.Dimension(454, 60));
        panTotRpt.setLayout(new java.awt.BorderLayout());

        spnTotRpt.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotRpt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTotRpt.setViewportView(tblTotRpt);

        panTotRpt.add(spnTotRpt, java.awt.BorderLayout.SOUTH);

        panRpt.add(panTotRpt, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        consultar();
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
        {
            if(isCamVal())
            {
                if (guardarDat())
                {
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    cargarDetReg();
                }
                else
                    mostrarMsgErr("<HTML>Ocurrió un error al realizar la operación GUARDAR.<BR>Intente realizar la operación nuevamente.<BR>Si el problema persiste notifiquelo a su administrador del sistema.</HTML>");
            }
            else
                mostrarMsgInf("Verifique los datos y vuelva a intentarlo.");
        }

    }//GEN-LAST:event_butGuaActionPerformed

    private void cboPerAAAAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPerAAAAItemStateChanged
        limpiarFrm(); 
    }//GEN-LAST:event_cboPerAAAAItemStateChanged

    private void cboPerMesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboPerMesItemStateChanged
        limpiarFrm(); 
    }//GEN-LAST:event_cboPerMesItemStateChanged

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
                //mostrarVenConTipDoc(1);
            }
        } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

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
                //mostrarVenConTipDoc(2);
            }
        } else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void chkCiePreMenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkCiePreMenItemStateChanged
//        if(!objPerUsr.isOpcionEnabled(4500)) { /*Ficha "Configuración": Casilla "Cierre presupuesto mensual"*/
//            mostrarMsgInf("<HTML>No cuenta con el permiso para realizar el cierre del presupuesto mensual.</HTML>");
//            chkCiePreMen.setSelected(false);
//        }
    }//GEN-LAST:event_chkCiePreMenItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JCheckBox chkCiePreMen;
    private javax.swing.JCheckBox chkMosPedAso;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblPer;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValTot;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCfgCab;
    private javax.swing.JPanel panCfgPed;
    private javax.swing.JPanel panCfgPre;
    private javax.swing.JPanel panCfgRub;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptPedConPre;
    private javax.swing.JPanel panRptPedSinPre;
    private javax.swing.JPanel panTotRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnCfgPed;
    private javax.swing.JScrollPane spnCfgRub;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnRptPedConPre;
    private javax.swing.JScrollPane spnRptPedSinPre;
    private javax.swing.JScrollPane spnTotCfgPed;
    private javax.swing.JScrollPane spnTotCfgRub;
    private javax.swing.JScrollPane spnTotRpt;
    private javax.swing.JScrollPane spnTotRptPedConPre;
    private javax.swing.JScrollPane spnTotRptPedSinPre;
    private javax.swing.JSplitPane sppCfgPre;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCfgPed;
    private javax.swing.JTable tblCfgRub;
    private javax.swing.JTable tblRptPedConPre;
    private javax.swing.JTable tblRptPedSinPre;
    private javax.swing.JTable tblTotCfgPed;
    private javax.swing.JTable tblTotCfgRub;
    private javax.swing.JTable tblTotRpt;
    private javax.swing.JTable tblTotRptPedConPre;
    private javax.swing.JTable tblTotRptPedSinPre;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtValTotPre;
    // End of variables declaration//GEN-END:variables

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
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodDoc.setText("");
            txtValTotPre.setText("");
            chkCiePreMen.setSelected(false);
            txaObs1.setText("");

            objTblModCfgRub.removeAllRows();
            objTblModCfgPed.removeAllRows();
            objTblModRptPedConPre.removeAllRows();
            objTblModRptPedSinPre.removeAllRows();
            
            calcularTotalesJTables();
            lblMsgSis.setText("Listo");
            pgrSis.setValue(0);            
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private void limpiarJtableDat()
    {
        vecDat.clear();
        
        objTblModCfgRub.setData(vecDat);
        tblCfgRub.setModel(objTblModCfgRub);
        
        objTblModCfgPed.setData(vecDat);
        tblCfgPed.setModel(objTblModCfgPed); 
        
        objTblModRptPedConPre.setData(vecDat);
        tblRptPedConPre.setModel(objTblModRptPedConPre);        
        
        objTblModRptPedSinPre.setData(vecDat);
        tblRptPedSinPre.setModel(objTblModRptPedSinPre);             
        
        calcularTotalesJTables();
        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
    }      
    
    private void consultar()
    {
        limpiarFrm();
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
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
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblCfgRub.getRowCount()>0 || tblCfgPed.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblCfgRub.requestFocus();
            }
            objThrGUI=null;
        }
    }
        
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=false;
        try{
            if (cargarCfgRub()){ 
                if (cargarCfgPed()){ 
                    if (cargarRptPedConPre()){ 
                        if (cargarRptPedSinPre()){ 
                            if(calcularTotalesJTables()){
                                blnRes=true;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCfgRub() 
    {
        boolean blnRes=true;
        BigDecimal bgdAux=BigDecimal.ZERO;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");      
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.ne_ani, a.ne_mes, a.nd_valDoc AS nd_valTotPreMen, a.st_cie AS st_ciePreMen";
                strSQL+="      , a.co_emp AS co_empPre, a.co_loc AS co_locPre, a.co_tipDoc AS co_tipDocPre, a.co_doc AS co_docPre";
                strSQL+="      , a1.* , a2.tx_DesLar AS tx_mesEmb, a3.tx_DesLar AS tx_mesPue";
                strSQL+=" FROM tbm_cabPreImp AS a";
                strSQL+=" LEFT OUTER JOIN tbm_detPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a2 ON a1.ne_mesEmb=a2.co_mesEmb";
                strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a3 ON a1.ne_mesPue=a3.co_mesEmb";
                strSQL+=" WHERE a.st_reg NOT IN('E','I') ";
                strSQL+=" AND a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND a.ne_mes="+cboPerMes.getSelectedIndex();
                //Validar que sólo se muestre los documentos asignados al programa.
                if(txtCodTipDoc.getText().equals("")) {
                    if(objParSis.getCodigoUsuario()==1)
                        strSQL+=" AND a.co_TipDoc IN ( select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                }
                else 
                    strSQL+=" AND a.co_tipDoc =" + txtCodTipDoc.getText() + "";   
                strSQL+=" ORDER BY a.co_tipDoc, a.co_doc, a1.co_reg";
                System.out.println("cargarCfgRub: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        if(rst.getObject("co_emp")!=null){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_CFG_RUB_LIN,"");
                            vecReg.add(INT_TBL_CFG_RUB_COD_EMP,       (rst.getObject("co_emp")==null?"":rst.getString("co_emp")));         
                            vecReg.add(INT_TBL_CFG_RUB_COD_LOC,       (rst.getObject("co_loc")==null?"":rst.getString("co_loc")));         
                            vecReg.add(INT_TBL_CFG_RUB_COD_TIP_DOC,   (rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc")));      
                            vecReg.add(INT_TBL_CFG_RUB_COD_DOC,       (rst.getObject("co_doc")==null?"":rst.getString("co_doc")));  
                            vecReg.add(INT_TBL_CFG_RUB_COD_REG,       (rst.getObject("co_reg")==null?"":rst.getString("co_reg")));   
                            vecReg.add(INT_TBL_CFG_RUB_NOM_RUB,       (rst.getObject("tx_des")==null?"":rst.getString("tx_des")));      
                            vecReg.add(INT_TBL_CFG_RUB_VAL_RUB,       (rst.getObject("nd_valTot")==null?"":rst.getString("nd_valTot")));      
                            vecReg.add(INT_TBL_CFG_RUB_CHK_CIE,       null);    
                            vecReg.add(INT_TBL_CFG_RUB_TXT_OBS,       (rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1")));  
                            vecReg.add(INT_TBL_CFG_RUB_BUT_MES_EMB,   "");
                            vecReg.add(INT_TBL_CFG_RUB_COD_MES_EMB,   (rst.getObject("ne_mesEmb")==null?"":rst.getString("ne_mesEmb"))); 
                            vecReg.add(INT_TBL_CFG_RUB_TXT_MES_EMB,   (rst.getObject("tx_mesEmb")==null?"":rst.getString("tx_mesEmb")));  
                            vecReg.add(INT_TBL_CFG_RUB_TXT_ANI_EMB,   (rst.getObject("ne_aniEmb")==null?"":rst.getString("ne_aniEmb")));
                            vecReg.add(INT_TBL_CFG_RUB_BUT_MES_PUE,   "");  
                            vecReg.add(INT_TBL_CFG_RUB_COD_MES_PUE,   (rst.getObject("ne_mesPue")==null?"":rst.getString("ne_mesPue"))); 
                            vecReg.add(INT_TBL_CFG_RUB_TXT_MES_PUE,   (rst.getObject("tx_mesPue")==null?"":rst.getString("tx_mesPue"))); 
                            vecReg.add(INT_TBL_CFG_RUB_TXT_ANI_PUE,   (rst.getObject("ne_aniPue")==null?"":rst.getString("ne_aniPue")));
                            vecReg.add(INT_TBL_CFG_RUB_NUM_REQ,           (rst.getObject("tx_numReq")==null?"":rst.getString("tx_numReq")));

                            strAux=rst.getObject("st_cie")==null?"":rst.getString("st_cie");
                            if ( strAux.equals("S") )  {   
                                vecReg.setElementAt(new Boolean(true), INT_TBL_CFG_RUB_CHK_CIE);
                            }
                            vecDat.add(vecReg);
                        }

                        
                        /*Presentar el valor y el nombre del presupuesto mensual */
                        strAux=rst.getObject("st_ciePreMen")==null?"":rst.getString("st_ciePreMen");
                        if ( strAux.equals("S") )  
                            chkCiePreMen.setSelected(true);
                        
                        bgdAux=objUti.redondearBigDecimal(new BigDecimal(rst.getObject("nd_valTotPreMen")==null?"0":(rst.getString("nd_valTotPreMen").equals("")?"0":rst.getString("nd_valTotPreMen"))), 2);                                
                        lblTit.setText("Presupuesto ("+cboPerMes.getSelectedItem().toString()+" "+cboPerAAAA.getSelectedItem().toString()+") "+" $ "+bgdAux);
                        txtValTotPre.setText(""+bgdAux);
                        txtCodDoc.setText(""+rst.getString("co_docPre")); 
                    }
                    else  {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.                
                objTblModCfgRub.setData(vecDat);
                tblCfgRub.setModel(objTblModCfgRub);
                //Calcular totales.
                objTblTotCfgRub.calcularTotales();  
                vecDat.clear(); 
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);  
                lblMsgSis.setText("Listo");
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
 
    /**
     * Esta función permite cargar los pedidos del periodo.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */    
    private boolean cargarCfgPed()
    {
        boolean blnRes=true;
        String strDatAplCon="";
        ResultSet rstLoc;
        Statement stmLoc;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_numDoc2 AS tx_numPed";
                strSQL+="      , a.nd_valDoc AS nd_valTotPed, a.fe_colPed, a.tx_obs1, a.tx_obs2, a.st_cie, a.tx_numReq";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a";
                strSQL+=" WHERE a.st_reg NOT IN('E','I') ";
                strSQL+=" AND a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND a.ne_mes="+cboPerMes.getSelectedIndex();
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_numDoc2, a.nd_valDoc, a.fe_colPed, a.tx_obs1, a.st_cie, a.tx_numReq";
                strSQL+=" ORDER BY a.co_tipDoc, a.co_doc";
                System.out.println("cargarCfgPed: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_CFG_PED_LIN,"");
                    vecReg.add(INT_TBL_CFG_PED_COD_EMP,     rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CFG_PED_COD_LOC,     rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CFG_PED_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_CFG_PED_COD_DOC,     rst.getString("co_doc"));
                    vecReg.add(INT_TBL_CFG_PED_NUM_PED,     rst.getString("tx_numPed"));
                    vecReg.add(INT_TBL_CFG_PED_VAL_PED,     rst.getString("nd_valTotPed"));
                    vecReg.add(INT_TBL_CFG_PED_CHK_CIE,     null);
                    vecReg.add(INT_TBL_CFG_PED_FEC_COL,     rst.getString("fe_colPed"));
                    vecReg.add(INT_TBL_CFG_PED_BUT_RUB,     null);
                    vecReg.add(INT_TBL_CFG_PED_TXT_OBS1,    rst.getString("tx_obs1"));  
                    vecReg.add(INT_TBL_CFG_PED_TXT_OBS2,    rst.getString("tx_obs2"));  
                    vecReg.add(INT_TBL_CFG_PED_DAT_APL,     null);
                    vecReg.add(INT_TBL_CFG_PED_NUM_REQ,     rst.getString("tx_numReq"));

                    if ( rst.getString("st_cie").equals("S") )  {
                        vecReg.setElementAt(new Boolean(true), INT_TBL_CFG_PED_CHK_CIE);  
                    }

                    //Obtiene Rubros de Presupuestos Asociados
                    stmLoc=con.createStatement();
                    strSQL="";
                    strSQL+=" SELECT * FROM ( ";
                    strSQL+="   SELECT *, (-1, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.co_reg, x.nd_ValApl, -1)  AS strDatApl";
                    strSQL+="   FROM tbr_detPreImp AS x";
                    strSQL+=" ) AS a1 ";
                    strSQL+=" WHERE a1.co_empRelCabNotPedPreImp="+rst.getString("co_emp");
                    strSQL+=" AND a1.co_locRelCabNotPedPreImp="+rst.getString("co_loc");
                    strSQL+=" AND a1.co_tipDocRelCabNotPedPreImp="+rst.getString("co_tipDoc");
                    strSQL+=" AND a1.co_docRelCabNotPedPreImp="+rst.getString("co_doc");
                    rstLoc=stmLoc.executeQuery(strSQL);
                    strDatAplCon="";
                    while(rstLoc.next()){
                        //Obtiene todos los datos de las facturas aplicadas.
                        String[] strAuxApl = rstLoc.getString("strDatApl").split(",");
                        if(!strDatAplCon.equals(""))
                            strDatAplCon+=" UNION ";
                        
                        strDatAplCon+=" SELECT " + strAuxApl[1] + " AS co_emp";  
                        strDatAplCon+="     ,  " + strAuxApl[2] + " AS co_loc"; 
                        strDatAplCon+="     ,  " + strAuxApl[3] + " AS co_tipDoc"; 
                        strDatAplCon+="     ,  " + strAuxApl[4] + " AS co_doc"; 
                        strDatAplCon+="     ,  " + strAuxApl[5] + " AS co_reg"; 
                        strDatAplCon+="     ,  " + strAuxApl[6] + " AS nd_valApl"; 
                    }
                    rstLoc.close();
                    rstLoc=null;
                    vecReg.setElementAt(""+strDatAplCon, INT_TBL_CFG_PED_DAT_APL);
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModCfgPed.setData(vecDat);
                tblCfgPed.setModel(objTblModCfgPed);
                //Calcular totales.
                objTblTotCfgPed.calcularTotales();                   
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite cargar los pedidos del periodo enlazados a un presupuesto.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */    
    private boolean cargarRptPedConPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp AS co_empRub, a.co_loc AS co_locRub, a.co_tipDoc AS co_tipDocRub, a.co_doc AS co_docRub, a1.co_Reg AS co_RegRub";
                strSQL+="      , a1.tx_des AS tx_desRub, a1.nd_valTot AS nd_valTotRub, a1.nd_valApl AS nd_ValTotApl";
                strSQL+="      , a3.co_emp AS co_empPed, a3.co_loc AS co_locPed, a3.co_tipDoc AS co_tipDocPed, a3.co_doc AS co_docPed";
                strSQL+="      , a3.tx_numDoc2 AS tx_numPed, a2.nd_ValApl AS nd_valAplPed, a3.fe_colPed, a3.nd_valDoc AS nd_valTotPed";
                strSQL+=" FROM tbm_cabPreImp AS a";
                strSQL+=" INNER JOIN tbm_detPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+="     tbr_detPreImp AS a2 ";
                strSQL+="     INNER JOIN tbm_cabNotPedPreImp AS a3 ON a2.co_empRelCabNotPedPreImp=a3.co_emp AND a2.co_locRelCabNotPedPreImp=a3.co_loc";
                strSQL+="     AND a2.co_tipDocRelCabNotPedPreImp=a3.co_tipDoc AND a2.co_docRelCabNotPedPreImp=a3.co_doc";
                strSQL+="     AND a3.st_reg NOT IN('E','I') ";
                strSQL+=" )ON a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_TipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_Reg=a2.co_Reg";
                strSQL+=" WHERE a.st_reg NOT IN('E','I') ";
                strSQL+=" AND a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND a.ne_mes="+cboPerMes.getSelectedIndex();
                
                //Validar que sólo se muestre los documentos asignados al programa.
                if(txtCodTipDoc.getText().equals("")) {
                    if(objParSis.getCodigoUsuario()==1)
                        strSQL+=" AND a.co_TipDoc IN ( select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                }
                else 
                    strSQL+=" AND a.co_tipDoc =" + txtCodTipDoc.getText() + "";   
                
                strSQL+=" ORDER BY a.co_tipDoc, a.co_doc, a1.co_reg";
                System.out.println("cargarRptPedConPre: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_RPT_PED_PRE_LIN,"");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,     rst.getString("co_empRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,     rst.getString("co_locRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB, rst.getString("co_tipDocRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,     rst.getString("co_docRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_REG_RUB,     rst.getString("co_regRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_NOM_RUB,         rst.getString("tx_desRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_RUB,         rst.getString("nd_valTotRub"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_APL_RUB,     rst.getString("nd_ValTotApl"));
                        vecReg.add(INT_TBL_RPT_PED_BUT_AUX,             null);
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_EMP_PED,     rst.getString("co_empPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_LOC_PED,     rst.getString("co_locPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED, rst.getString("co_tipDocPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_DOC_PED,     rst.getString("co_docPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_NUM_PED,         rst.getString("tx_numPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_PED,         rst.getString("nd_valAplPed"));    //20Jun2019: Se volvió a solicitar por AP que muestre el valor aplicado, tal como se estableció en reunión con Gerencia, que se muestre el valor del pedido aplicado al presupuesto.
                        //vecReg.add(INT_TBL_RPT_PED_PRE_VAL_PED,         rst.getString("nd_valTotPed"));   //13Jun2019: AP solicito que se muestre el valor total del pedido.
                        vecReg.add(INT_TBL_RPT_PED_PRE_FEC_COL,         rst.getString("fe_colPed"));
                        vecDat.add(vecReg);
                    }
                    else  {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModRptPedConPre.setData(vecDat);
                tblRptPedConPre.setModel(objTblModRptPedConPre);
                //Calcular totales.
                objTblTotRptPedConPre.calcularTotales();                   
                vecDat.clear();
                cambiarColorRubros(); 
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   

    /**
     * Esta función permite cargar los pedidos del periodo sin un presupuesto.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */    
    private boolean cargarRptPedSinPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a.co_emp AS co_empPed, a.co_loc AS co_locPed, a.co_tipDoc AS co_tipDocPed, a.co_doc AS co_docPed";
                strSQL+="      , a.tx_numDoc2 AS tx_numPed, a.nd_valDoc AS nd_valTotPed, a.fe_colPed, a.tx_obs1, a.tx_obs2";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a";
                strSQL+=" WHERE a.st_Reg IN ('A')";
                strSQL+=" AND a.ne_ani="+cboPerAAAA.getSelectedItem().toString();
                strSQL+=" AND a.ne_mes="+cboPerMes.getSelectedIndex();
                strSQL+=" AND NOT EXISTS (select * from tbr_detPreImp AS a1";
                strSQL+="                 where a.co_Emp=a1.co_empRelCabNotPedPreImp and a.co_loc=a1.co_locRelCabNotPedPreImp";
                strSQL+="                 and a.co_tipDoc=a1.co_tipDocRelCabNotPedPreImp and a.co_doc=a1.co_docRelCabNotPedPreImp";
                strSQL+=" )";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.tx_numDoc2, a.nd_valDoc, a.fe_colPed, a.tx_obs1, a.tx_obs2";
                //System.out.println("cargarRptPedSinPre: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_RPT_PED_PRE_LIN,"");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_EMP_RUB,     "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_LOC_RUB,     "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_RUB, "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_DOC_RUB,     "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_REG_RUB,     "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_NOM_RUB,         rst.getString("tx_obs2"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_RUB,         "");
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_APL_RUB,     "");
                        vecReg.add(INT_TBL_RPT_PED_BUT_AUX,             null);
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_EMP_PED,     rst.getString("co_empPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_LOC_PED,     rst.getString("co_locPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_TIP_DOC_PED, rst.getString("co_tipDocPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_COD_DOC_PED,     rst.getString("co_docPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_NUM_PED,         rst.getString("tx_numPed"));
                        vecReg.add(INT_TBL_RPT_PED_PRE_VAL_PED,         rst.getString("nd_valTotPed"));                        
                        vecReg.add(INT_TBL_RPT_PED_PRE_FEC_COL,         rst.getString("fe_colPed"));
                        vecDat.add(vecReg);
                    }
                    else  {
                        break;
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModRptPedSinPre.setData(vecDat);
                tblRptPedSinPre.setModel(objTblModRptPedSinPre);
                //Calcular totales.
                objTblTotRptPedSinPre.calcularTotales();                   
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite cargar el movimiento pedido seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPedAso()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.tx_numDoc2 AS tx_numPed";
                strSQL+="      , a3.nd_valDoc AS nd_valTotPed, a3.fe_colPed, a3.st_cie, a3.tx_obs1, a3.tx_obs2, a3.tx_numReq";
                strSQL+=" FROM tbm_cabPreImp AS a";
                strSQL+=" INNER JOIN tbm_detPreImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" INNER JOIN tbr_detPreImp AS a2 ON a1.co_emp=a2.co_Emp AND a1.co_loc=a2.co_loc AND a1.co_TipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a1.co_Reg=a2.co_Reg";
                strSQL+=" INNER JOIN tbm_cabNotPedPreImp AS a3 ON a2.co_empRelCabNotPedPreImp=a3.co_emp AND a2.co_locRelCabNotPedPreImp=a3.co_loc AND a2.co_tipDocRelCabNotPedPreImp=a3.co_tipDoc AND a2.co_docRelCabNotPedPreImp=a3.co_doc";
                strSQL+=" WHERE a.st_reg NOT IN('E','I') ";
                strSQL+=" AND a.co_emp=" + objTblModCfgRub.getValueAt(tblCfgRub.getSelectedRow(), INT_TBL_CFG_RUB_COD_EMP);
                strSQL+=" AND a.co_loc=" + objTblModCfgRub.getValueAt(tblCfgRub.getSelectedRow(), INT_TBL_CFG_RUB_COD_LOC);
                strSQL+=" AND a.co_tipDoc=" + objTblModCfgRub.getValueAt(tblCfgRub.getSelectedRow(), INT_TBL_CFG_RUB_COD_TIP_DOC);
                strSQL+=" AND a.co_doc=" + objTblModCfgRub.getValueAt(tblCfgRub.getSelectedRow(), INT_TBL_CFG_RUB_COD_DOC);
                strSQL+=" AND a1.co_reg=" + objTblModCfgRub.getValueAt(tblCfgRub.getSelectedRow(), INT_TBL_CFG_RUB_COD_REG);
                strSQL+=" ORDER BY a3.co_tipDoc, a3.co_doc";                
                System.out.println("cargarPedAso: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_CFG_PED_LIN,"");
                    vecReg.add(INT_TBL_CFG_PED_COD_EMP,     rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CFG_PED_COD_LOC,     rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CFG_PED_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_CFG_PED_COD_DOC,     rst.getString("co_doc"));
                    vecReg.add(INT_TBL_CFG_PED_NUM_PED,     rst.getString("tx_numPed"));
                    vecReg.add(INT_TBL_CFG_PED_VAL_PED,     rst.getString("nd_valTotPed"));
                    vecReg.add(INT_TBL_CFG_PED_CHK_CIE,     null);
                    vecReg.add(INT_TBL_CFG_PED_FEC_COL,     rst.getString("fe_colPed"));
                    vecReg.add(INT_TBL_CFG_PED_BUT_RUB,     null);
                    vecReg.add(INT_TBL_CFG_PED_TXT_OBS1,     rst.getString("tx_obs1"));   
                    vecReg.add(INT_TBL_CFG_PED_TXT_OBS2,     rst.getString("tx_obs2"));   
                    vecReg.add(INT_TBL_CFG_PED_DAT_APL,     null);  //Siempre NULL
                    vecReg.add(INT_TBL_CFG_PED_NUM_REQ,     rst.getString("tx_numReq"));  //Siempre NULL

                    if ( rst.getString("st_cie").equals("S") )  {
                        vecReg.setElementAt(new Boolean(true), INT_TBL_CFG_PED_CHK_CIE);
                    }
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModCfgPed.setData(vecDat);
                tblCfgPed.setModel(objTblModCfgPed);
                //Calcular totales.
                objTblTotCfgPed.calcularTotales();                   
                vecDat.clear();
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     
    
    /**
     * Esta función calcula el total de las columnas especificadas.
     * @return true: Si se pudo calcular el total de las columnas especificadas sin que ocurra ningún error.
     * <BR>false: En el caso contrario.
     */
    private boolean calcularTotalesJTables()
    {
        boolean blnRes=true;
        int intNumColRpt, j;
        int intNumDec=2;
        ArrayList<Integer> arlColTotRpt; /*Columnas Totales "Reporte" */
        try
        {
            /*Se vuelve a calcular todos los subtotales "Reporte" */
            objTblTotRptPedConPre.calcularTotales();   
            objTblTotRptPedSinPre.calcularTotales();      
            
            /* Totales "Reporte" */
            arlColTotRpt = new ArrayList();
            arlColTotRpt.add(INT_TBL_RPT_PED_PRE_VAL_RUB); 
            arlColTotRpt.add(INT_TBL_RPT_PED_PRE_VAL_APL_RUB);
            arlColTotRpt.add(INT_TBL_RPT_PED_PRE_VAL_PED); 
            intNumColRpt=arlColTotRpt.size();
            
            double dblTotRpt[]=new double[intNumColRpt];     
            for (j=0; j<intNumColRpt; j++){
                dblTotRpt[j]+=objUti.parseDouble(tblTotRptPedConPre.getValueAt(0, arlColTotRpt.get(j)));  
                dblTotRpt[j]+=objUti.parseDouble(tblTotRptPedSinPre.getValueAt(0, arlColTotRpt.get(j))); 
            }
            
            for (j=0; j<intNumColRpt; j++) {
                objTblTotRpt.setValueAt("Total Presupuesto", 0, INT_TBL_RPT_PED_PRE_NOM_RUB);   
                objTblTotRpt.setValueAt("Total Pedidos", 0, INT_TBL_RPT_PED_PRE_NUM_PED); 
                objTblTotRpt.setValueAt("" + objUti.redondear(dblTotRpt[j], intNumDec), 0, arlColTotRpt.get(j));  
            }
            //Limpiar la celda (0, 0). Al asignar el valor con "setValueAt" dicha celda queda marcada con estado "M".
            objTblModTotRpt.clearCell(0, 0, false);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    private void calcularTotalPresupuesto()
    {
        try
        {
            objTblTotCfgRub.calcularTotales();  //Calcular totales.
            txtValTotPre.setText(""+objUti.parseDouble(objTblTotCfgRub.getValueAt(0, INT_TBL_CFG_RUB_VAL_RUB)));    
            bgdValTotPre=objUti.redondearBigDecimal(new BigDecimal(txtValTotPre.getText()==null?"0":(txtValTotPre.getText().equals("")?"0":txtValTotPre.getText())) , objParSis.getDecimalesMostrar());
        }
        catch (Exception e)
        {
            System.err.println("Error al calcular Total del presupuesto.");
        }
    }
    

    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        boolean blnRes=true;
        Boolean blnNumPed=true, blnValPed=true, blnFecCol=true, blnValRub=true, blnMes=true;
        String strNumReq="";
        try{
            //Valida que este configurado el tipo de documento de Presupuesto, caso contrario vuelve a realizar la configuración.
            if (txtCodTipDoc.getText().equals("")){
                if(!mostrarTipDocPre()){
                    mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido configurado.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                    blnRes=false;
                }
            }

            /* Valida Permisos de Usuarios. */
            //Valida Tabla "Rubros del Presupuesto" 
            if(objTblModCfgRub.isDataModelChanged()){
                if(!objPerUsr.isOpcionEnabled(4501)){   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                    mostrarMsgInf("<HTML>No cuenta con el permiso para ingresar rubros del presupuesto.<BR></HTML>");
                    blnRes=false;
                }
                else{
                    //Valida "Valor del Rubro"
                    for (int i=0; i<objTblModCfgRub.getRowCount(); i++){
                        if (objUti.parseString(objTblModCfgRub.getValueAt(i,INT_TBL_CFG_RUB_LIN)).equals("M")){
                            BigDecimal bgdValTmp=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdValTmp.compareTo(BigDecimal.ZERO)<=0){
                                blnValRub=false;     
                            }
                        }
                    }                
                    if(!blnValRub){
                        mostrarMsgInf("<HTML>Verifique los valores del presupuesto.<BR>Debe asignarse valor a cada uno de los rubros del presupuesto.</HTML>");
                        blnRes=false;   
                    }
                    
                    //Valida "Fechas de Presupuesto"
                    for (int i=0; i<objTblModCfgRub.getRowCount(); i++){
                        if (objUti.parseString(objTblModCfgRub.getValueAt(i,INT_TBL_CFG_RUB_LIN)).equals("M")){
                            BigDecimal bgdFecMes=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_EMB)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_EMB).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_EMB).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdFecMes.compareTo(BigDecimal.ZERO)<=0){
                                blnMes=false;     
                            }
                            
                            bgdFecMes=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_EMB)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_EMB).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_EMB).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdFecMes.compareTo(BigDecimal.ZERO)<=0){ 
                                blnMes=false;     
                            }
                            
                            bgdFecMes=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_PUE)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_PUE).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_PUE).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdFecMes.compareTo(BigDecimal.ZERO)<=0){ 
                                blnMes=false;     
                            }
                            
                            bgdFecMes=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_PUE)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_PUE).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_PUE).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdFecMes.compareTo(BigDecimal.ZERO)<=0){ 
                                blnMes=false;     
                            }                            
                        }
                    }                
                    if(!blnMes){
                        mostrarMsgInf("<HTML>Debe ingresar las fechas de Embarque y Puerto en el presupuesto.</HTML>");
                        blnRes=false;   
                    }                    

                    for (int i=0; i<objTblModCfgRub.getRowCount(); i++){
                        strNumReq=objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NUM_REQ)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NUM_REQ).toString();
                        if(strNumReq.length()>8){                            
                            tabFrm.setSelectedIndex(1);
                            tblCfgRub.setRowSelectionInterval(i, i);
                            tblCfgRub.changeSelection(i, INT_TBL_CFG_RUB_NUM_REQ, true, true);
                            tblCfgRub.requestFocus();
                            mostrarMsgInf("<HTML>El campo número de requerimiento excede el número de caracteres permitidos.<BR>Ingrese un número de caracteres dentro del rango permitido y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                            break;
                            
                        }
                    }
                    
                }
            }

            //Valida Tabla "Pedidos" 
            if(objTblModCfgPed.isDataModelChanged()){
                if(!objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                {
                    mostrarMsgInf("<HTML>No cuenta con el permiso para ingresar pedidos.<BR></HTML>");
                    blnRes=false;
                }
                else{  /* Si cuenta con los permisos se realizan demas validaciones*/

//                    //Valida "Número del Pedido"
//                    for (int i=0; i<objTblModCfgPed.getRowCount(); i++)
//                    {
//                        if (objUti.parseString(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_LIN)).equals("M")) 
//                        {
//                            String strNumPed=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED)==null?"":(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED).toString().equals("")?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED).toString());
//                            if(!strNumPed.equals("")){
//                                blnNumPed=false;                            
//                            }
//                        }
//                    }                
//                    if(!blnNumPed){
//                        mostrarMsgInf("<HTML>Verifique los números de pedido y vuelva a intentarlo.</HTML>");
//                        return false;  
//                    }                    
                    
                    //Valida "Valor del Pedido"
                    for (int i=0; i<objTblModCfgPed.getRowCount(); i++){
                        if (objUti.parseString(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_LIN)).equals("M")){
                            BigDecimal bgdValTmp=objUti.redondearBigDecimal(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED)==null?"0":(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString().equals("")?"0":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString()), objParSis.getDecimalesBaseDatos());
                            if(bgdValTmp.compareTo(BigDecimal.ZERO)<=0){
                                blnValPed=false;                            
                            }
                        }
                    }                
                    if(!blnValPed){
                        mostrarMsgInf("<HTML>Verifique los valores de los pedidos ingresados.<BR>Todos los pedidos deben tener asignado un valor.</HTML>");
                        blnRes=false;  
                    }

                    //Valida "Fecha Colocación Pedido"
                    for (int i=0; i<objTblModCfgPed.getRowCountTrue(); i++){
                        if (objUti.parseString(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_LIN)).equals("M")){
                            /* Fecha Colocación Pedido */
                            String strAuxFec=(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_FEC_COL)==null)?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_FEC_COL).toString();
                            objDtePckEdiFecCol.setText(strAuxFec); //Se asigna la fecha de colocación al datePicker 
                            if(!objDtePckEdiFecCol.isFecha()) {
                                blnFecCol=false;                            
                            }
                        }
                    }
                    if(!blnFecCol){
                        mostrarMsgInf("<HTML>Verifique la fecha de colocacion en los pedidos ingresados.<BR>Todos los pedidos deben tener asignada una fecha de colocación del pedido.</HTML>");
                        blnRes=false;   
                    }
                    
                    
                    for (int i=0; i<objTblModCfgPed.getRowCount(); i++){
                        strNumReq=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_REQ)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_REQ).toString();
                        if(strNumReq.length()>8){
                            tabFrm.setSelectedIndex(1);
                            tblCfgPed.setRowSelectionInterval(i, i);
                            tblCfgPed.changeSelection(i, INT_TBL_CFG_PED_NUM_REQ, true, true);
                            tblCfgPed.requestFocus();
                            mostrarMsgInf("<HTML>El campo número de requerimiento excede el número de caracteres permitidos.<BR>Ingrese un número de caracteres dentro del rango permitido y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                            break;
                            
                        }
                    }
                    
                    
                    
                }
            }
            
        } 
        catch (Exception e)
        {
            System.err.println("Error validaciones");
            blnRes=false;   
        }
        return blnRes;
    }
    
    private boolean validaExiNumPed(Connection conExt, String numeroPedido, int fila) 
    {
        boolean blnRes=false;
        Connection conCie;
        Statement stmCie;
        ResultSet rstCie;
        String strSQLCie="";
        String codigoPedido="";
        try
        {
            if (conExt==null)
                conCie=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            else
                conCie=conExt;
            
            if (conCie!=null)
            {
                //Armar la sentencia SQL.
                stmCie=conCie.createStatement();
                strSQLCie =" ";
                strSQLCie+=" SELECT * FROM tbm_cabNotPedPreImp AS a1";
                strSQLCie+=" WHERE a1.st_reg NOT IN ('E', 'I') ";
                strSQLCie+=" AND a1.tx_numDoc2 ='"+numeroPedido+"'";
                
                codigoPedido=objTblModCfgPed.getValueAt(fila, INT_TBL_CFG_PED_COD_DOC)==null?"":objTblModCfgPed.getValueAt(fila, INT_TBL_CFG_PED_COD_DOC).toString();
                System.out.println("codigoPedido: "+codigoPedido);
                if(!codigoPedido.equals("")){
                    strSQLCie+=" AND a1.co_doc <> "+codigoPedido+"";
                }
                System.out.println("validaExiNumPed: "+strSQLCie);
                rstCie=stmCie.executeQuery(strSQLCie);
                if(rstCie.next())   {
                    blnRes=true;
                    ///mostrarMsgInf("<HTML>El número de pedido "+numeroPedido+" ya existe.</HTML>");
                } 
                rstCie.close();
                rstCie=null;
                stmCie.close();
                stmCie=null;      
                if (conExt==null){  //Si trabaja con conexión local, cierra conexión.
                    conCie.close();
                    conCie=null;
                }                
            }
        }
        catch (java.sql.SQLException e)   {
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    /**
     * Esta función permite actualizar los registros del detalle.
     * Indica que el pedido ha sido arribado.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDat()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if(getDatosGenerales()){
                    if(inserta_tbmCabPreImp()){   
                        if(actualiza_tbmCabPreImp()){
                            if(inserta_tbmDetPreImp()){
                                if(actualiza_tbmDetPreImp()){
                                    if(inserta_tbmCabNotPedPreImp()){
                                        if(actualiza_tbmCabNotPedPreImp()){
                                            if(inserta_tbrDetPreImp()){ 
                                                blnRes=true; 
                                                con.commit();
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
                        }
                        else
                            con.rollback();                     
                    }
                    else
                        con.rollback(); 
                }
                else
                    con.rollback();                     

                con.close();
                con=null; 
            }
            //Inicializo el estado de las filas.
            objTblModCfgRub.initRowsState();
            objTblModCfgRub.setDataModelChanged(false);
            objTblModCfgPed.initRowsState();
            objTblModCfgPed.setDataModelChanged(false);            
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean getDatosGenerales(){
        boolean blnRes=false;
        int intUltCodDoc=-1;
        ResultSet rstCodDoc;
        Statement stmCodDoc;
        try{
            if(con!=null){
                blnRes=true;
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intAniSel=Integer.parseInt(cboPerAAAA.getSelectedItem().toString());
                intMesSel=cboPerMes.getSelectedIndex();
                calcularTotalPresupuesto();

                //Obtener el código de documento
                if(txtCodDoc.getText().equals(""))
                {
                    /*Cuando se crea un presupuesto nuevo en el periodo seleccionado, se obtiene el último código de documento. */
                    stmCodDoc=con.createStatement(); 
                    strAux ="";
                    strAux+=" SELECT (CASE WHEN (MAX(a1.co_doc)+1) IS NULL THEN 1 ELSE (MAX(a1.co_doc)+1) END) AS co_doc";
                    strAux+=" FROM tbm_cabPreImp AS a1";
                    strAux+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa(); 
                    strAux+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strAux+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                    rstCodDoc=stmCodDoc.executeQuery(strAux); 
                    if(rstCodDoc.next()){
                        intUltCodDoc=rstCodDoc.getInt("co_doc");
                    }
                    rstCodDoc.close();
                    rstCodDoc=null;
                    stmCodDoc.close();
                    stmCodDoc=null;
                    if(intUltCodDoc==-1) 
                        return false;
                    txtCodDoc.setText("" + intUltCodDoc);   
                }
                //System.out.println("Codigo Documento: "+txtCodDoc.getText());
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean inserta_tbmCabPreImp(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(objPerUsr.isOpcionEnabled(4501))   /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                {
                    stm=con.createStatement();
                    
                    //Armar sentencia SQL: Cuando no existe el presupuesto, crea el presupuesto.
                    strSQL ="";
                    strSQL+=" INSERT INTO tbm_cabPreImp";
                    strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, ne_numdoc, tx_nom, ne_ani, ne_mes"; 
                    strSQL+=" , nd_valdoc, tx_obs1, st_cie, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod ";
                    strSQL+=" )";
                    strSQL+=" SELECT * FROM (";
                    strSQL+="     SELECT "+ objParSis.getCodigoEmpresa() +" AS co_emp";       //co_emp
                    strSQL+="          , "+ objParSis.getCodigoLocal() + " AS co_loc";        //co_loc
                    strSQL+="          , "+ txtCodTipDoc.getText()+ " AS co_tipDoc";          //co_tipdoc
                    strSQL+="          , "+ txtCodDoc.getText()+ " AS co_doc";                //co_doc
                    strSQL+="          , "+ intAniSel+""+intMesSel+"01 AS ne_numdoc";         //ne_numdoc
                    strSQL+="          , "+ objUti.codificar("Presupuesto "+intMesSel+" "+intAniSel)+" AS tx_nom";  //tx_nom
                    strSQL+="          , "+ intAniSel+" AS ne_ani";        //ne_ani
                    strSQL+="          , "+ intMesSel+" AS ne_mes";        //ne_mes
                    strSQL+="          , "+ bgdValTotPre+" AS nd_valdoc";  //nd_valdoc
                    strSQL+="          , "+ objUti.codificar(txaObs1.getText() + strVer)+" AS tx_obs1"; //tx_obs1
                    strSQL+="          ,'N' AS st_cie";   //st_cie
                    strSQL+="          ,'A' AS st_reg";   //st_reg
                    strSQL+="          ,CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ing";//fe_ing
                    strSQL+="          ,CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ultMod";//fe_ultMod      
                    strSQL+="          , "+ objParSis.getCodigoUsuario() + " AS co_usring"; //co_usring
                    strSQL+="          , "+ objParSis.getCodigoUsuario() + " AS co_usrmod"; //co_usrmod
                    strSQL+=" ) AS a";
                    strSQL+=" WHERE NOT EXISTS (select * from tbm_cabPreImp AS x WHERE a.ne_ani=x.ne_ani AND a.ne_mes=x.ne_mes)";
                    strSQL+=" ;";
                    System.out.println("inserta_tbmCabPreImp: "+strSQL);
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    
                    //Insertar Histórico
                    objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPreImp", "tbh_cabPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                    blnRes=true;
                }
                else{
                    blnRes=true;
                }
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
    
    private boolean actualiza_tbmCabPreImp(){
        boolean blnRes=false;        
        try{
            if(con!=null){
                stm=con.createStatement();
                
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" UPDATE tbm_cabPreImp ";
                strSQL+=" SET nd_valdoc="+bgdValTotPre;
                if(objPerUsr.isOpcionEnabled(4500)) { /*Ficha "Configuración": Casilla "Cierre presupuesto mensual"*/
                    strSQL+="   , st_cie='"+(chkCiePreMen.isSelected()?"S":"N")+"'"; 
                }
                strSQL+="   , fe_ultMod='"+strFecUltMod+"'";
                strSQL+="   , co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" WHERE ne_ani="+intAniSel;
                strSQL+=" AND ne_mes="+intMesSel;
                strSQL+=" ;";    
                System.out.println("actualiza_tbmCabPreImp: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                
                //Insertar Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPreImp", "tbh_cabPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                blnRes=true;  
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean inserta_tbmDetPreImp(){    
        boolean blnRes=false;
        String strUpdSQL="";
        BigDecimal bgdValTmp=new BigDecimal("0");
        int intCodReg=0;
        try{
            if (con!=null){
                if( (objPerUsr.isOpcionEnabled(4501)) || (objParSis.getCodigoUsuario()==1) ) /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                {
                    stm=con.createStatement();
                    
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.co_reg) AS co_reg FROM tbm_detPreImp AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=";";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intCodReg=rst.getInt("co_reg");
                    
                    intCodReg++;
                    
                    
                    System.out.println("objTblModCfgRub: " + objTblModCfgRub.getRowCountTrue());
                    
                    objTblModCfgRub.removeEmptyRows();
                    System.out.println("objTblModCfgRub: " + objTblModCfgRub.getRowCountTrue());
                    
                    for(int i=0; i<objTblModCfgRub.getRowCountTrue(); i++)
                    {
                        if (objUti.parseString(objTblModCfgRub.getValueAt(i,INT_TBL_CFG_RUB_LIN)).equals("I")) 
                        {
                            bgdValTmp=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString()), objParSis.getDecimalesBaseDatos());
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_detPreImp";
                            strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg, tx_des, nd_valtot, nd_valapl";
                            strSQL+=" , ne_aniEmb, ne_mesEmb, ne_aniPue, ne_mesPue";
                            strSQL+=" , tx_obs1, st_cie, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, tx_numReq";
                            strSQL+=" )";    
                            strSQL+=" SELECT * FROM (";
                            strSQL+="   SELECT "+ objParSis.getCodigoEmpresa() +" AS co_emp";       //co_emp
                            strSQL+="        , "+ objParSis.getCodigoLocal() + " AS co_loc";        //co_loc
                            strSQL+="        , "+ txtCodTipDoc.getText()+ " AS co_tipDoc";          //co_tipdoc
                            strSQL+="        , "+ txtCodDoc.getText()+ " AS co_doc";                //co_doc
                            strSQL+="        , "+ intCodReg + " AS co_Reg";                         //co_Reg
                            strSQL+="        , "+ objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NOM_RUB)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NOM_RUB).toString())+" AS tx_des"; //tx_des
                            strSQL+="        , "+ bgdValTmp+" AS nd_valtot";  //nd_valtot
                            strSQL+="        , 0 AS nd_valApl";               //nd_valApl
                            strSQL+="        , "+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_EMB),2)+" AS ne_aniEmb";    //ne_aniEmb
                            strSQL+="        , "+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_EMB),2)+" AS ne_mesEmb";    //ne_mesEmb
                            strSQL+="        , "+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_PUE),2)+" AS ne_aniPue";    //ne_aniPue
                            strSQL+="        , "+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_PUE),2)+" AS ne_mesPue";    //ne_mesPue
                            strSQL+="        , "+ objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_OBS)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_OBS).toString())+" AS tx_obs1"; //tx_obs1
                            strSQL+="        , 'N' AS st_cie";   //st_cie 
                            strSQL+="        , 'A' AS st_reg";   //st_reg
                            strSQL+="        , CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ing";//fe_ing
                            strSQL+="        , CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ultMod";//fe_ultMod      
                            strSQL+="        , "+ objParSis.getCodigoUsuario() + " AS co_usring"; //co_usring
                            strSQL+="        , "+ objParSis.getCodigoUsuario() + " AS co_usrmod"; //co_usrmod
                            strSQL+="        , "+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NUM_REQ),1)+" AS tx_numReq";    //tx_numReq
                            strSQL+=" ) AS a";
                            strSQL+=" WHERE NOT EXISTS (select * from tbm_detPreImp AS x WHERE a.co_Emp=x.co_Emp AND a.co_loc=x.co_loc AND a.co_tipDoc=x.co_tipDoc AND a.co_doc=x.co_doc AND a.co_reg=x.co_reg)";
                            strSQL+=" ;";
                            strUpdSQL+=strSQL;
                            intCodReg++;
                        }
                    }
                    System.out.println("inserta_tbmDetPreImp: " + strUpdSQL);
                    stm.executeUpdate(strUpdSQL);
                    stm.close();
                    stm=null;
                    
                    //Insertar Histórico
                    objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_detPreImp", "tbh_detPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                    blnRes=true;
                }
                else{
                    blnRes=true;
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

    private boolean actualiza_tbmDetPreImp(){ 
        boolean blnRes=false;
        BigDecimal bgdValRub=BigDecimal.ZERO;
        String strCodEmpRub="";
        String strSQLUpd="";
        try{
            if(con!=null){
                if(objPerUsr.isOpcionEnabled(4501)) /*Ficha "Configuración": Casilla "Modificar rubros presupuesto"*/ 
                { 
                    for(int i=0; i<objTblModCfgRub.getRowCountTrue(); i++){
                        if (objUti.parseString(objTblModCfgRub.getValueAt(i,INT_TBL_CFG_RUB_LIN)).equals("M")) 
                        {
                            strCodEmpRub=objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_EMP)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_EMP).toString();                        
                            /*Valida que sea un rubro existente.*/
                            if(!strCodEmpRub.equals("")) { 
                                /* Cuando es un pedido existente, se actualiza. */
                                bgdValRub=objUti.redondearBigDecimal(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB)==null?"0":(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString().equals("")?"0":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_VAL_RUB).toString()), objParSis.getDecimalesBaseDatos());                    

                                //Armar sentencia SQL
                                strSQL ="";
                                strSQL+=" UPDATE tbm_detPreImp ";
                                strSQL+=" SET tx_des="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NOM_RUB)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NOM_RUB).toString())+"";
                                strSQL+="   , nd_valTot="+bgdValRub;
                                strSQL+="   , fe_ultMod='"+strFecUltMod+"'";    
                                strSQL+="   , co_usrMod="+objParSis.getCodigoUsuario();      
                                strSQL+="   , st_cie='"+(objTblModCfgRub.isChecked(i, INT_TBL_CFG_RUB_CHK_CIE)?"S":"N")+"'"; 
                                strSQL+="   , tx_obs1="+ objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_OBS)==null?"":objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_OBS).toString());                                              
                                strSQL+="   , ne_aniEmb="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_EMB),2);   
                                strSQL+="   , ne_mesEmb="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_EMB),2);   
                                strSQL+="   , ne_aniPue="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_TXT_ANI_PUE),2);   
                                strSQL+="   , tx_numReq="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_NUM_REQ),1);
                                strSQL+="   , ne_mesPue="+objUti.codificar(objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_MES_PUE),2);
                                strSQL+=" WHERE co_emp="+objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_EMP).toString();
                                strSQL+=" AND co_loc="+objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_LOC).toString(); 
                                strSQL+=" AND co_tipDoc="+objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_TIP_DOC).toString();
                                strSQL+=" AND co_doc="+objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_DOC).toString();
                                strSQL+=" AND co_reg="+objTblModCfgRub.getValueAt(i, INT_TBL_CFG_RUB_COD_REG).toString();
                                strSQL+=" ;";     
                                strSQLUpd+=strSQL;
                            }
                        }
                    }
                    System.out.println("actualiza_tbmDetPreImp: "+strSQLUpd);
                    stm=con.createStatement();
                    stm.executeUpdate(strSQLUpd);
                    stm.close();
                    stm=null;    
                    
                    //Insertar Histórico
                    objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_detPreImp", "tbh_detPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                                    
                    blnRes=true;
                }
                else{
                    blnRes=true;
                }
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
    
    private boolean inserta_tbmCabNotPedPreImp(){
        boolean blnRes=true;
        int intCodDocPedPre_canRepLSC=-1;
        int intNumDocPedPre_canRepLSC=-1;
        BigDecimal bgdValPed=BigDecimal.ZERO;
        String strNumPed="";
        String strExiNotPed="";
        ResultSet rstLoc;
        Statement stmLoc;
        String strCodEmpPed="";
        try{ 
            if(con!=null){
                if(objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                {
                    for(int i=0; i<objTblModCfgPed.getRowCountTrue(); i++)
                    {      
                        strCodEmpPed=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP).toString();                        
                        if(strCodEmpPed.equals("")) /*Valida que sea un pedido nuevo.*/         
                        {                 
                            strNumPed=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED).toString();
                            if(!validaExiNumPed(con, strNumPed, i))  /* Valida que no exista el número de pedido. */
                            {
                                //Obtener el código de documento.
                                intCodDocPedPre_seg=-1;
                                stm=con.createStatement();
                                strSQL ="";
                                strSQL+=" SELECT (CASE WHEN (MAX(a1.co_doc)+1) IS NULL THEN 1 ELSE (MAX(a1.co_doc)+1) END) AS co_doc";                    
                                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + ""; 
                                //System.out.println("MAX codigoDocumento - NPP: " + strSQL);
                                rst=stm.executeQuery(strSQL); 
                                if(rst.next()){
                                    intCodDocPedPre_canRepLSC=rst.getInt("co_doc");
                                }
                                if(intCodDocPedPre_canRepLSC==-1) 
                                    return false;  
                                intCodDocPedPre_seg=intCodDocPedPre_canRepLSC;  
                                rst.close();
                                rst=null;
                                stm.close();
                                stm=null;

                                //Obtener el número de documento.
                                stm=con.createStatement();
                                strSQL ="";
                                strSQL+=" SELECT (CASE WHEN (MAX(a1.ne_numDoc)+1) IS NULL THEN 1 ELSE (MAX(a1.ne_numDoc)+1) END) AS ne_numDoc";                    
                                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                                //System.out.println("MAX numeroDocumento - NPP: " + strSQL);
                                rst=stm.executeQuery(strSQL);
                                if(rst.next())
                                    intNumDocPedPre_canRepLSC=rst.getInt("ne_numDoc");
                                if (intNumDocPedPre_canRepLSC==-1)
                                    return false;
                                rst.close();
                                rst=null;                        
                                stm.close();
                                stm=null;                        

                                //Armar sentencia SQL
                                stm=con.createStatement();
                                strSQL ="";
                                strSQL+=" INSERT INTO tbm_cabNotPedPreImp(";
                                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, tx_numdoc2";
                                strSQL+=" , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_cie, co_locRel, co_tipDocRel, co_docRel";
                                strSQL+=" , fe_colPed, nd_valDoc, ne_ani, ne_mes, tx_obs1, tx_obs2, tx_numReq";  
                                strSQL+=" )";
                                strExiNotPed+=" SELECT * FROM (";
                                strExiNotPed+="       SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp";  //co_emp
                                strExiNotPed+="            , " + objParSis.getCodigoLocal() + " AS co_loc";    //co_loc
                                strExiNotPed+="            , " + INT_EST_COD_TIP_DOC_PED_PRE + " AS co_tipdoc";//co_tipdoc
                                strExiNotPed+="            , " + intCodDocPedPre_canRepLSC + " AS co_doc";     //co_doc
                                strExiNotPed+="            , CAST('" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "' AS DATE) AS fe_doc";//fe_doc
                                strExiNotPed+="            , " + intNumDocPedPre_canRepLSC + " AS ne_numDoc";         //ne_numdoc
                                strExiNotPed+="            , CAST(" + objUti.codificar(strNumPed)+" AS CHARACTER VARYING) AS tx_numdoc2"; //tx_numdoc2                        
                                strExiNotPed+="            ,'A' AS st_Reg";//st_reg
                                strExiNotPed+="            ,CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ing";//fe_ing
                                strExiNotPed+="            ,CAST('"+ strFecUltMod + "' AS TIMESTAMP) AS fe_ultMod";//fe_ultMod      
                                strExiNotPed+="            , "+ objParSis.getCodigoUsuario() + " AS co_usring"; //co_usring
                                strExiNotPed+="            , "+ objParSis.getCodigoUsuario() + " AS co_usrmod"; //co_usrmod                        
                                strExiNotPed+="            ,'N' AS st_cie";         //st_cie
                                strExiNotPed+="            , CAST (Null AS INTEGER) AS co_locRel";     //co_locRel
                                strExiNotPed+="            , CAST (Null AS INTEGER) AS co_tipDocRel";  //co_tipDocRel
                                strExiNotPed+="            , CAST (Null AS INTEGER) AS co_docRel";     //co_docRel
                                strExiNotPed+="            , CAST ("+objUti.codificar( objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_FEC_COL)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_FEC_COL).toString())+" AS DATE) AS fe_colPed";      
                                bgdValPed=objUti.redondearBigDecimal(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED)==null?"0":(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString().equals("")?"0":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString()), objParSis.getDecimalesBaseDatos());
                                strExiNotPed+="            , "+ bgdValPed+" AS nd_valDoc"; //nd_valDoc    
                                strExiNotPed+="            , "+ intAniSel+" AS ne_ani";     //ne_ani
                                strExiNotPed+="            , "+ intMesSel+" AS ne_mes";     //ne_mes  
                                strExiNotPed+="            , "+ objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS1)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS1).toString())+" AS tx_obs1"; //tx_obs1               
                                strExiNotPed+="            , "+ objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS2)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS2).toString())+" AS tx_obs2"; //tx_obs2
                                strExiNotPed+="            , "+ objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_REQ),1) + " AS tx_numReq";//tx_numReq
                                strExiNotPed+=" ) AS a";
                                strSQL+=""+strExiNotPed; 
                                strSQL+=" WHERE NOT EXISTS (select * from tbm_cabNotPedPreImp AS x WHERE a.tx_numDoc2=x.tx_numDoc2)";
                                //strSQL+=" WHERE NOT EXISTS (select * from tbm_cabNotPedPreImp AS x WHERE a.co_emp=x.co_loc AND a.co_loc=x.co_loc AND a.co_tipDoc=x.co_tipDoc AND a.co_doc=x.co_doc)";
                                strSQL+=" ;";                                 
                                System.out.println("inserta_tbmCabNotPedPreImp: "+strSQL);
                                stm.executeUpdate(strSQL);
                                stm.close();
                                stm=null;       

                                //Insertar Histórico
                                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedPreImp", "tbh_cabNotPedPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                                                                                

                                //Valida si existe o no la Nota de Pedido Previa.
                                stmLoc=con.createStatement();
                                //System.out.println("VALIDA SI EXISTE NOTped: "+strExiNotPed);
                                rstLoc=stmLoc.executeQuery(strExiNotPed);
                                if(rstLoc.next()){ //Si fue creada la Nota de Pedido Previa, se inserta seguimiento
                                    if(insertar_tbmCabSegMovInv_NotPedPreImp()){ 
                                        System.out.println("SEGUIMIENTO INSERTADO.....");
                                    }
                                    else{        
                                        blnRes=false;
                                        System.out.println("ERROR AL INSERTAR seguimiento.....");
                                    }                             
                                }
                                rstLoc.close();
                                rstLoc=null;
                                stmLoc.close();
                                stmLoc=null;       
                            }
                        }
                    }
                }
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
     * Función que permite insertar la Nota de Pedido Previa en la tabla de seguimiento.
     * @return 
     */
    private boolean insertar_tbmCabSegMovInv_NotPedPreImp(){
        System.out.println("insertar_tbmCabSegMovInv_NotPedPreImp");
        boolean blnRes=false;
        objCodSegNotPedPre=null;
        try{  
            if(con!=null){
                if(getCodSegNotExiNotPedPre()){ 
                    /*Cuando no existe seguimiento, insertar seguimiento. */
                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegNotPedPre, 0, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), INT_EST_COD_TIP_DOC_PED_PRE, intCodDocPedPre_seg)){
                        blnRes=true;
                        System.out.println("Inserción en seguimiento de inventario");
                    }
                }
                else{
                    /* Cuando existe seguimiento, no insertar seguimiento. Solo se continúa con el proceso de guardado. */
                    blnRes=true;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }      
   
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa
     * @return true Si no existe seguimiento asociado para la Nota de Pedido Previa
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotExiNotPedPre(){
        boolean blnRes=false;
        objCodSegNotPedPre=null;
        ResultSet rstLoc;
        Statement stmLoc;
        try{
            if(con!=null)
            {
                stmLoc=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" ON a1.co_emp=a3.co_empRelCabNotPedPreImp AND a1.co_loc=a3.co_locRelCabNotPedPreImp";
                strSQL+=" AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedPreImp AND a1.co_doc=a3.co_docRelCabNotPedPreImp";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                strSQL+=" AND a1.co_doc=" + intCodDocPedPre_seg + "";                
                System.out.println("getCodSegNotExiNotPedPre: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    objCodSegNotPedPre=rstLoc.getObject("co_seg"); 
                }
                System.out.println("objCodSegNotPedPre: " + objCodSegNotPedPre);
                if(objCodSegNotPedPre==null){ //No existe seguimiento por tanto se debe ingresar
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            System.err.println("Error - getCodSegExiNotPedPre SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.err.println("Error - getCodSegExiNotPedPre: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }       
    
    private boolean actualiza_tbmCabNotPedPreImp(){
        boolean blnRes=false;
        BigDecimal bgdValPed=BigDecimal.ZERO;
        String strCodEmpPed="";
        try{
            if(con!=null){
                if(objPerUsr.isOpcionEnabled(4502))  /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                {
                    for(int i=0; i<objTblModCfgPed.getRowCountTrue(); i++)
                    {
                        if (objUti.parseString(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_LIN)).equals("M")) 
                        {                        
                            strCodEmpPed=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP).toString();                        
                            if(!strCodEmpPed.equals("")) {  /*Valida que sea un pedido existente.*/
                                /* Cuando es un pedido existente, se actualiza. */
                                bgdValPed=objUti.redondearBigDecimal(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED)==null?"0":(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString().equals("")?"0":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_VAL_PED).toString()), objParSis.getDecimalesBaseDatos());                    
                                String strNumPed=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_PED).toString();

                                //Armar sentencia SQL
                                strSQL ="";
                                strSQL+=" UPDATE tbm_cabNotPedPreImp ";
                                strSQL+=" SET nd_valdoc="+bgdValPed;
                                strSQL+="     , fe_ultMod='"+strFecUltMod+"'";    
                                strSQL+="     , co_usrMod="+objParSis.getCodigoUsuario(); 
                                strSQL+="     , tx_obs1="+ objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS1)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS1).toString())+"";                                         
                                strSQL+="     , tx_obs2="+ objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS2)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_TXT_OBS2).toString())+"";                                         
                                strSQL+="     , fe_colPed=" +objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_FEC_COL)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_FEC_COL).toString() )+"";   
                                
                                if(!validaExiNumPed(con, strNumPed, i)){ /* Se agrega actualización de número de pedido, solicitado por AP.*/
                                    strSQL+="     , tx_numDoc2="+objUti.codificar(strNumPed)+"";
                                }
                                
                                strSQL+="     , tx_numReq=" + objUti.codificar(objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_NUM_REQ),1) + "";
                                
                                strSQL+=" WHERE co_emp="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP).toString();
                                strSQL+=" AND co_loc="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_LOC).toString(); 
                                strSQL+=" AND co_tipDoc="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_TIP_DOC).toString();
                                strSQL+=" AND co_doc="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_DOC).toString();
                                strSQL+=" ;";     
                                System.out.println("actualiza_tbmCabNotPedPreImp: "+strSQL);
                                stm=con.createStatement();
                                stm.executeUpdate(strSQL);
                                stm.close();
                                stm=null;           

                                //Insertar Histórico
                                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedPreImp", "tbh_cabNotPedPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                                                    
                            }
                        }
                    }
                    blnRes=true;
                }
                else{
                    blnRes=true;
                }
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
    
    private boolean inserta_tbrDetPreImp(){    
        boolean blnRes=false;
        String strUpdSQL="";
        String strDatAplRel=""; 
        try{
            if (con!=null){
                if(objPerUsr.isOpcionEnabled(4502))   /*Ficha "Configuración": Casilla "Modificar pedidos"*/ 
                {
                    stm=con.createStatement();     
                    for(int i=0; i<objTblModCfgPed.getRowCountTrue(); i++)
                    {
                        if (objUti.parseString(objTblModCfgPed.getValueAt(i,INT_TBL_CFG_PED_LIN)).equals("M")) 
                        {
                            strDatAplRel=objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_DAT_APL)==null?"":objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_DAT_APL).toString();
                            
                            //Armar la sentencia SQL.
                            if(!strDatAplRel.equals("")){
                                //Se eliminan las relaciones anteriores.
                                strSQL ="";
                                strSQL+=" DELETE FROM tbr_detPreImp";
                                strSQL+=" WHERE co_empRelCabNotPedPreImp="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP).toString();
                                strSQL+=" AND co_locRelCabNotPedPreImp="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_LOC).toString();
                                strSQL+=" AND co_tipDocRelCabNotPedPreImp="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_TIP_DOC).toString();
                                strSQL+=" AND co_docRelCabNotPedPreImp="+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_DOC).toString();
                                strSQL+=" ;";
                                strUpdSQL+=strSQL;

                                //Se crean relaciones
                                strSQL ="";
                                strSQL+=" INSERT INTO tbr_detPreImp";
                                strSQL+=" ( co_emp, co_loc, co_tipdoc, co_doc, co_reg, nd_valapl";
                                strSQL+=" , co_empRelCabNotPedPreImp, co_locRelCabNotPedPreImp, co_tipdocRelCabNotPedPreImp, co_docRelCabNotPedPreImp";
                                strSQL+=" )";    
                                strSQL+=" SELECT y.*";
                                strSQL+="         , "+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_EMP).toString()+" AS co_empRelCabNotPedPreImp";
                                strSQL+="         , "+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_LOC).toString()+" AS co_locRelCabNotPedPreImp";
                                strSQL+="         , "+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_TIP_DOC).toString()+" AS co_tipDocRelCabNotPedPreImp";
                                strSQL+="         , "+objTblModCfgPed.getValueAt(i, INT_TBL_CFG_PED_COD_DOC).toString()+" AS co_docRelCabNotPedPreImp";
                                strSQL+=" FROM ( "+strDatAplRel+" ) AS y ";
                                strSQL+=" ;";
                                strUpdSQL+=strSQL;

                                //Se actualizar el valor Total Aplicado al rubro del presupuesto.
                                strSQL ="";
                                strSQL+=" UPDATE tbm_DetPreImp SET nd_ValApl=z.nd_ValAplRub";
                                strSQL+=" FROM (";
                                strSQL+="   SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.co_reg, SUM(x.nd_ValApl) AS nd_ValAplRub";
                                strSQL+="   FROM tbr_DetPreImp AS x";
                                strSQL+="   INNER JOIN ( "+strDatAplRel+" ) AS y ";
                                strSQL+="   ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_TipDoc=y.co_TipDoc AND x.co_doc=y.co_doc AND x.co_Reg=y.co_Reg ";
                                strSQL+="   GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.co_reg";
                                strSQL+=" ) AS z";
                                strSQL+=" WHERE tbm_DetPreImp.co_emp=z.co_emp AND tbm_DetPreImp.co_loc=z.co_loc";
                                strSQL+=" AND tbm_DetPreImp.co_tipDoc=z.co_tipDoc AND tbm_DetPreImp.co_doc=z.co_doc AND tbm_DetPreImp.co_reg=z.co_reg";
                                strSQL+=" ;";
                                strUpdSQL+=strSQL;                            
                            }
                        }
                    }
                    System.out.println("inserta_tbrDetPreImp: " + strUpdSQL);
                    stm.executeUpdate(strUpdSQL);
                    stm.close();
                    stm=null;
                    blnRes=true;
                }
                else{
                    blnRes=true;
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
    

    
    
    
    
} 
