/*
 * ZafVen41.java
 *
 * Created on April 11, 2026, 12:35 PM
 */
package Ventas.ZafVen41;


import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblFiltro.ZafTblFiltro;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenConGP.ZafVenConGP;
import Ventas.ZafVen42.ZafVen42;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author  sistemas4
 */
public class ZafVen41 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strAux;
    private int intTabSel = 0;
    private boolean blnHayCam;
    private ZafDocLis objDocLis;
    private Connection con, conCab,conTuvalApi;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL;    
    private String strTxUsr;
    
    private boolean blnActChkSeg = false;
    private boolean blnActChkList = false;
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatSeg,vecCabSeg, vecRegSeg, vecAuxSeg;
    private Vector vecDatCan,vecCabCan, vecRegCan, vecAuxCan;
    private Vector vecDatCli,vecCabCli, vecRegCli, vecAuxCli;
    private Vector vecDatExc,vecCabExc, vecRegExc, vecAuxExc;
    private Vector vecDatFilSeg,vecCabFilSeg,vecRegFilSeg,vecAuxFilSeg;
    private Vector vecDatFilLis,vecCabFilLis,vecRegFilLis,vecAuxFilLis;
    
    private ZafNotCorEle objNotCorEle;
    
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_CHK = 1;
    final int INT_TBL_DAT_RUC = 2;
    final int INT_TBL_DAT_NOM_CLI = 3;
    final int INT_TBL_DAT_SEG = 4;    
    final int INT_TBL_DAT_LIS_ACT = 5;
    final int INT_TBL_DAT_LIS_NUE = 6;
    
    
    final int INT_TBL_SEG_LIN = 0;
    final int INT_TBL_SEG_CHK = 1;
    final int INT_TBL_SEG_RUC = 2;
    final int INT_TBL_SEG_NOM_CLI = 3;
    final int INT_TBL_SEG_VEN = 4; 
    final int INT_TBL_SEG_SUBSEG = 5;
    final int INT_TBL_SEG_LIS_ACT = 6;
    final int INT_TBL_SEG_LIS_NUE = 7;
    
    
    final int INT_TBL_CAN_LIN = 0;
    final int INT_TBL_CAN_CHK = 1;
    final int INT_TBL_CAN_RUC = 2;
    final int INT_TBL_CAN_NOM_CLI = 3;
    final int INT_TBL_CAN_VEN = 4;  
    final int INT_TBL_CAN_SUBSEG = 5;
    final int INT_TBL_CAN_LIS_ACT = 6;
    final int INT_TBL_CAN_LIS_NUE = 7;
    
    
    final int INT_TBL_CLI_LIN = 0;
    final int INT_TBL_CLI_CHK = 1;
    final int INT_TBL_CLI_RUC = 2;
    final int INT_TBL_CLI_NOM_CLI = 3;
    final int INT_TBL_CLI_VEN = 4;  
    final int INT_TBL_CLI_SUBSEG = 5;
    final int INT_TBL_CLI_LIS_ACT = 6;
    final int INT_TBL_CLI_LIS_NUE = 7;
    
    
    final int INT_TBL_EXC_LIN = 0;
    final int INT_TBL_EXC_CHK = 1;    
    final int INT_TBL_EXC_RUC = 2;
    final int INT_TBL_EXC_NOM_CLI = 3; 
    final int INT_TBL_EXC_LIS_ACT = 4;
    final int INT_TBL_EXC_LIS_NUE = 5;
    
    
    final int INT_TBL_FILSEG_LIN = 0;
    final int INT_TBL_FILSEG_CHK = 1;
    final int INT_TBL_FILSEG_COD = 2;
    final int INT_TBL_FILSEG_SUBSEG = 3;
    
    
    final int INT_TBL_FILIS_LIN = 0;
    final int INT_TBL_FILIS_CHK = 1;    
    final int INT_TBL_FILIS_LIS = 2;
    
    
    
    private boolean blnCargarArchivo; 
    private int intNumeroItemsProcesar=0;
    private ArrayList arlDatExcel, arlRegExcel;
    boolean blnNoSeleccion=true;
    StringBuilder cadenaColAct = new StringBuilder();
    

    private ZafTblMod objTblMod, objTblModSeg, objTblModCan, objTblModCli, objTblModExc;
    private ZafTblMod objTblModFilSeg, objTblModFilLis;
    private ZafTblFilCab objTblFilCab, objTblFilCabSeg, objTblFilCabCan,objTblFilCabCli,objTblFilCabExc;
    private ZafTblPopMnu objTblPopMnu,objTblPopMnuSeg,objTblPopMnuCan,objTblPopMnuCli,objTblPopMnuExc;
    
    
    private ZafMouMotAda objMouMotAda;


    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk,objTblCelEdiChkSeg,objTblCelEdiChkCan,objTblCelEdiChkCli, objTblCelEdiChkExc;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblSeg,objTblCelRenLblCan,objTblCelRenLblCli, objTblCelRenLblExc;

    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblFiltro objTblFilVen,objTblFilSeg,objTblFilCan,objTblFilCli,objTblFil,objTblFilExc;
    private ZafVenConGP vconVen, vconSeg, vconCan, vconCli;

    private Librerias.ZafAut.ZafAut objAutPrg;
    
    private boolean blnMarTodChkTblVen = true;
    private boolean blnMarTodChkTblSeg = true;
    private boolean blnMarTodChkTblCan = true;
    private boolean blnMarTodChkTblCli = true;
    private boolean blnMarTodChkTblExc = true;
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    

    private boolean blnCon, blnConSeg, blnConCan,blnConExc;
    private ZafThreadGUI objThrGUI;
    
    private ZafTblCelRenLbl objTblCelRenLblLista;
    
    private final Color colGeneral   = new Color(0, 128, 0);     // verde
    private final Color colDistri    = new Color(0, 102, 204);   // azul
    private final Color colRetail    = new Color(128, 0, 128);   // morado
    private final Color colCompetenc = new Color(255, 140, 0);   // naranja
    private final Color colTienda    = Color.BLACK;
    
    private String filtroTabla = "";
    private long ultimaTecla = 0;
    
    private String strCodCliAnt = "";
    
    private boolean blnResNO=false;
    
    private boolean blnEsGuardar = false;


    /** Creates new form ZafMae03 */
    public ZafVen41(ZafParSis obj) {
       
        try {
//            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            
                initComponents();
                configurarFrm();
                agregarDocLis();    
                
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
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
        lblTit = new javax.swing.JLabel();
        tabFrmLog = new javax.swing.JTabbedPane();
        panFrmVen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblLis = new javax.swing.JLabel();
        txtcodven = new javax.swing.JTextField();
        txtNom = new javax.swing.JTextField();
        butven = new javax.swing.JButton();
        cmbTie = new javax.swing.JComboBox<>();
        lblVen1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panFrmSeg = new javax.swing.JPanel();
        panFil1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblLis1 = new javax.swing.JLabel();
        txtcodseg = new javax.swing.JTextField();
        txtNomSeg = new javax.swing.JTextField();
        butseg = new javax.swing.JButton();
        cmbSeg = new javax.swing.JComboBox<>();
        lblVen2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatSeg = new javax.swing.JTable();
        panFrmCan = new javax.swing.JPanel();
        panFil2 = new javax.swing.JPanel();
        panSup = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblLis2 = new javax.swing.JLabel();
        txtcodcan = new javax.swing.JTextField();
        txtNomCan = new javax.swing.JTextField();
        butcan = new javax.swing.JButton();
        cmbCan = new javax.swing.JComboBox<>();
        lblVen3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtFilLisAct = new javax.swing.JTextField();
        panLoc = new javax.swing.JPanel();
        jCheckBoxSeg = new javax.swing.JCheckBox();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panLis = new javax.swing.JPanel();
        jCheckBoxLis = new javax.swing.JCheckBox();
        spnLis = new javax.swing.JScrollPane();
        tblLis = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDatCan = new javax.swing.JTable();
        panFrmCli = new javax.swing.JPanel();
        panFil3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        lblLis3 = new javax.swing.JLabel();
        txtcodcli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        cmbCli = new javax.swing.JComboBox<>();
        lblVen4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDatCli = new javax.swing.JTable();
        panFrmCarga = new javax.swing.JPanel();
        panFil4 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        butCarExc = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDatExc = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFrmVen.setLayout(new java.awt.BorderLayout());

        panFil.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel1.setLayout(null);

        lblLis.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLis.setText("APLICAR NUEVA LISTA:");
        lblLis.setToolTipText("Beneficiario");
        jPanel1.add(lblLis);
        lblLis.setBounds(750, 6, 150, 20);

        txtcodven.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodvenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodvenFocusLost(evt);
            }
        });
        txtcodven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodvenActionPerformed(evt);
            }
        });
        txtcodven.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodvenKeyReleased(evt);
            }
        });
        jPanel1.add(txtcodven);
        txtcodven.setBounds(79, 4, 40, 20);

        txtNom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomFocusLost(evt);
            }
        });
        txtNom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomActionPerformed(evt);
            }
        });
        txtNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomKeyReleased(evt);
            }
        });
        jPanel1.add(txtNom);
        txtNom.setBounds(117, 4, 310, 20);

        butven.setText("...");
        butven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butvenActionPerformed(evt);
            }
        });
        jPanel1.add(butven);
        butven.setBounds(427, 4, 30, 20);

        cmbTie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTieActionPerformed(evt);
            }
        });
        jPanel1.add(cmbTie);
        cmbTie.setBounds(900, 4, 120, 20);

        lblVen1.setText("Vendedor:");
        lblVen1.setToolTipText("Beneficiario");
        jPanel1.add(lblVen1);
        lblVen1.setBounds(6, 6, 60, 14);

        panFil.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

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
        ));
        jScrollPane1.setViewportView(tblDat);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panFil.add(jPanel2, java.awt.BorderLayout.CENTER);

        panFrmVen.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrmLog.addTab("ClientesAsignados", panFrmVen);

        panFrmSeg.setLayout(new java.awt.BorderLayout());

        panFil1.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.setLayout(null);

        lblLis1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLis1.setText("APLICAR NUEVA LISTA:");
        lblLis1.setToolTipText("Beneficiario");
        jPanel3.add(lblLis1);
        lblLis1.setBounds(950, 6, 150, 20);

        txtcodseg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodsegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodsegFocusLost(evt);
            }
        });
        txtcodseg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodsegActionPerformed(evt);
            }
        });
        txtcodseg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodsegKeyReleased(evt);
            }
        });
        jPanel3.add(txtcodseg);
        txtcodseg.setBounds(79, 4, 40, 20);

        txtNomSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomSegFocusLost(evt);
            }
        });
        txtNomSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomSegActionPerformed(evt);
            }
        });
        txtNomSeg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomSegKeyReleased(evt);
            }
        });
        jPanel3.add(txtNomSeg);
        txtNomSeg.setBounds(117, 4, 310, 20);

        butseg.setText("...");
        butseg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butsegActionPerformed(evt);
            }
        });
        jPanel3.add(butseg);
        butseg.setBounds(427, 4, 30, 20);

        jPanel3.add(cmbSeg);
        cmbSeg.setBounds(1100, 4, 120, 20);

        lblVen2.setText("Segmento:");
        lblVen2.setToolTipText("Beneficiario");
        jPanel3.add(lblVen2);
        lblVen2.setBounds(6, 6, 70, 14);

        panFil1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        tblDatSeg.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatSeg);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panFil1.add(jPanel4, java.awt.BorderLayout.CENTER);

        panFrmSeg.add(panFil1, java.awt.BorderLayout.CENTER);

        tabFrmLog.addTab("Segmentos", panFrmSeg);

        panFrmCan.setLayout(new java.awt.BorderLayout());

        panFil2.setMinimumSize(new java.awt.Dimension(100, 100));
        panFil2.setLayout(new java.awt.BorderLayout());

        jPanel5.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel5.setLayout(null);

        lblLis2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLis2.setText("APLICAR NUEVA LISTA:");
        lblLis2.setToolTipText("Beneficiario");
        jPanel5.add(lblLis2);
        lblLis2.setBounds(950, 6, 150, 20);

        txtcodcan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodcanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodcanFocusLost(evt);
            }
        });
        txtcodcan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodcanActionPerformed(evt);
            }
        });
        txtcodcan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodcanKeyReleased(evt);
            }
        });
        jPanel5.add(txtcodcan);
        txtcodcan.setBounds(79, 4, 40, 20);

        txtNomCan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCanFocusLost(evt);
            }
        });
        txtNomCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCanActionPerformed(evt);
            }
        });
        txtNomCan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomCanKeyReleased(evt);
            }
        });
        jPanel5.add(txtNomCan);
        txtNomCan.setBounds(117, 4, 310, 20);

        butcan.setText("...");
        butcan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcanActionPerformed(evt);
            }
        });
        jPanel5.add(butcan);
        butcan.setBounds(427, 4, 30, 20);

        cmbCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCanActionPerformed(evt);
            }
        });
        jPanel5.add(cmbCan);
        cmbCan.setBounds(1100, 4, 120, 20);

        lblVen3.setText("Canales:");
        lblVen3.setToolTipText("Beneficiario");
        jPanel5.add(lblVen3);
        lblVen3.setBounds(6, 6, 70, 14);

        jLabel1.setText("Lista Actual:");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(675, 6, 70, 14);
        jPanel5.add(txtFilLisAct);
        txtFilLisAct.setBounds(750, 4, 170, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panLoc.setAutoscrolls(true);
        panLoc.setName(""); // NOI18N
        panLoc.setPreferredSize(new java.awt.Dimension(300, 110));
        panLoc.setLayout(new java.awt.BorderLayout());

        jCheckBoxSeg.setText("Filtro Segmento");
        jCheckBoxSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSegActionPerformed(evt);
            }
        });
        panLoc.add(jCheckBoxSeg, java.awt.BorderLayout.PAGE_START);

        spnLoc.setMinimumSize(new java.awt.Dimension(50, 50));
        spnLoc.setPreferredSize(new java.awt.Dimension(100, 70));

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLoc.setMinimumSize(new java.awt.Dimension(50, 60));
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        panLis.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panLis.setAutoscrolls(true);
        panLis.setName(""); // NOI18N
        panLis.setPreferredSize(new java.awt.Dimension(300, 110));
        panLis.setLayout(new java.awt.BorderLayout());

        jCheckBoxLis.setText("Filtro Lista");
        jCheckBoxLis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxLisActionPerformed(evt);
            }
        });
        panLis.add(jCheckBoxLis, java.awt.BorderLayout.PAGE_START);
        jCheckBoxLis.getAccessibleContext().setAccessibleName("Filtro Lista");

        spnLis.setMinimumSize(new java.awt.Dimension(50, 50));
        spnLis.setPreferredSize(new java.awt.Dimension(100, 70));

        tblLis.setModel(new javax.swing.table.DefaultTableModel(
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
        tblLis.setMinimumSize(new java.awt.Dimension(50, 60));
        spnLis.setViewportView(tblLis);

        panLis.add(spnLis, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout panSupLayout = new javax.swing.GroupLayout(panSup);
        panSup.setLayout(panSupLayout);
        panSupLayout.setHorizontalGroup(
            panSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSupLayout.createSequentialGroup()
                .addGroup(panSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panSupLayout.createSequentialGroup()
                        .addGap(751, 751, 751)
                        .addComponent(panLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panSupLayout.createSequentialGroup()
                    .addGap(999, 999, 999)
                    .addComponent(panLis, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(112, Short.MAX_VALUE)))
        );
        panSupLayout.setVerticalGroup(
            panSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSupLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panLoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panSupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panSupLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(panLis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        panLoc.getAccessibleContext().setAccessibleName("Segmentos");

        panFil2.add(panSup, java.awt.BorderLayout.NORTH);

        jPanel7.setLayout(new java.awt.BorderLayout());

        tblDatCan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblDatCan);

        jPanel7.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        panFil2.add(jPanel7, java.awt.BorderLayout.CENTER);

        panFrmCan.add(panFil2, java.awt.BorderLayout.CENTER);

        tabFrmLog.addTab("Canales", panFrmCan);

        panFrmCli.setLayout(new java.awt.BorderLayout());

        panFil3.setLayout(new java.awt.BorderLayout());

        jPanel8.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel8.setLayout(null);

        lblLis3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLis3.setText("APLICAR NUEVA LISTA:");
        lblLis3.setToolTipText("Beneficiario");
        jPanel8.add(lblLis3);
        lblLis3.setBounds(950, 6, 150, 20);

        txtcodcli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodcliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodcliFocusLost(evt);
            }
        });
        txtcodcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodcliActionPerformed(evt);
            }
        });
        txtcodcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcodcliKeyReleased(evt);
            }
        });
        jPanel8.add(txtcodcli);
        txtcodcli.setBounds(79, 4, 100, 20);

        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomCliKeyReleased(evt);
            }
        });
        jPanel8.add(txtNomCli);
        txtNomCli.setBounds(180, 4, 310, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        jPanel8.add(butCli);
        butCli.setBounds(490, 4, 30, 20);

        cmbCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCliActionPerformed(evt);
            }
        });
        jPanel8.add(cmbCli);
        cmbCli.setBounds(1100, 4, 120, 20);

        lblVen4.setText("Clientes:");
        lblVen4.setToolTipText("Beneficiario");
        jPanel8.add(lblVen4);
        lblVen4.setBounds(6, 6, 70, 14);

        panFil3.add(jPanel8, java.awt.BorderLayout.NORTH);

        jPanel9.setLayout(new java.awt.BorderLayout());

        tblDatCli.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblDatCli);

        jPanel9.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        panFil3.add(jPanel9, java.awt.BorderLayout.CENTER);

        panFrmCli.add(panFil3, java.awt.BorderLayout.CENTER);

        tabFrmLog.addTab("Clientes", panFrmCli);

        panFrmCarga.setLayout(new java.awt.BorderLayout());

        panFil4.setLayout(new java.awt.BorderLayout());

        jPanel10.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel10.setLayout(null);

        butCarExc.setText("CARGAR EXCEL");
        butCarExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarExcActionPerformed(evt);
            }
        });
        jPanel10.add(butCarExc);
        butCarExc.setBounds(592, 4, 130, 23);

        panFil4.add(jPanel10, java.awt.BorderLayout.NORTH);

        jPanel11.setLayout(new java.awt.BorderLayout());

        tblDatExc.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDatExc);

        jPanel11.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        panFil4.add(jPanel11, java.awt.BorderLayout.CENTER);

        panFrmCarga.add(panFil4, java.awt.BorderLayout.CENTER);

        tabFrmLog.addTab("Carg.Exc", panFrmCarga);

        panFrm.add(tabFrmLog, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 50));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

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
        butGua.setToolTipText("Guarda los cambios");
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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1252, 619);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
// TODO add your handling code here:

}//GEN-LAST:event_formInternalFrameOpened

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
    String strTit, strMsg;
    try {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            //Cerrar la conexión si está abierta.
            if (rstCab != null) {
                rstCab.close();
                stmCab.close();
                conCab.close();
                rstCab = null;
                stmCab = null;
                conCab = null;
            }
            dispose();
        }
    } catch (java.sql.SQLException e) {
        dispose();
    }
}//GEN-LAST:event_exitForm

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
    //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
    if (butCon.getText().equals("Consultar")) {
        
        blnEsGuardar = false;
        blnCon = true;

        //GUARDAR pestaña actual
        intTabSel = tabFrmLog.getSelectedIndex();

        objThrGUI = null;
        objThrGUI = new ZafThreadGUI();
        objThrGUI.start();

    } else {
        blnCon = false;
    }
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
    exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        //guardar();
        
        blnEsGuardar = true;
        
        objThrGUI = null;
        objThrGUI = new ZafThreadGUI();
        objThrGUI.start();

    }//GEN-LAST:event_butGuaActionPerformed

    private void cmbCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCliActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomCliKeyReleased
        // TODO add your handling code here:
        if (txtNomCli.getText().trim().equals("")) {

            if (objTblModCli.getRowCount() > 0) {

                txtcodcli.setText("");

                objTblModCli.removeAllRows();
                tblDatCli.setModel(objTblModCli);
            }
        }
    }//GEN-LAST:event_txtNomCliKeyReleased

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (txtNomCli.getText().equals("")) {
            //txtCodItm.setText("");
            txtcodcli.setText("");
            txtNomCli.setText("");

            //objTblModCli.removeAllRows();
            //tblDatCli.setModel(objTblModCli);

        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtcodcliKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcliKeyReleased
        // TODO add your handling code here:
        if (txtcodcli.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblModCli.getRowCount() > 0) {

                txtNomCli.setText("");

                objTblModCli.removeAllRows();
                tblDatCli.setModel(objTblModCli);
            }
        }
    }//GEN-LAST:event_txtcodcliKeyReleased

    private void txtcodcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodcliActionPerformed
        // TODO add your handling code here:
        txtcodcli.transferFocus();
    }//GEN-LAST:event_txtcodcliActionPerformed

    private void txtcodcliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusLost
        // TODO add your handling code here:
        if (txtcodcli.getText().equals("")) {
            //txtCodItm.setText("");
            txtcodcli.setText("");
            txtNomCli.setText("");

            //objTblModCli.removeAllRows();
            //tblDatCli.setModel(objTblModCli);

        } else {
            mostrarVenConCli(1);
        }
    }//GEN-LAST:event_txtcodcliFocusLost

    private void txtcodcliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusGained
        // TODO add your handling code here:
        txtcodcli.selectAll();
    }//GEN-LAST:event_txtcodcliFocusGained

    private void butcanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcanActionPerformed
        // TODO add your handling code here:
        mostrarVenConCan(0);
    }//GEN-LAST:event_butcanActionPerformed

    private void txtNomCanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomCanKeyReleased
        // TODO add your handling code here:

        if (txtNomCan.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblModCan.getRowCount() > 0) {

                txtcodcan.setText("");

                objTblModCan.removeAllRows();
                tblDatCan.setModel(objTblModCan);
            }
        }
    }//GEN-LAST:event_txtNomCanKeyReleased

    private void txtNomCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCanActionPerformed

    private void txtNomCanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCanFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCanFocusLost

    private void txtNomCanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCanFocusGained
        // TODO add your handling code here:
        txtNomCan.selectAll();
    }//GEN-LAST:event_txtNomCanFocusGained

    private void txtcodcanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodcanKeyReleased
        // TODO add your handling code here:
        if (txtcodcan.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblModCan.getRowCount() > 0) {

                txtNomCan.setText("");

                objTblModCan.removeAllRows();
                tblDatCan.setModel(objTblModCan);
            }
        }
    }//GEN-LAST:event_txtcodcanKeyReleased

    private void txtcodcanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodcanActionPerformed
        // TODO add your handling code here:
        txtcodcan.transferFocus();
    }//GEN-LAST:event_txtcodcanActionPerformed

    private void txtcodcanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcanFocusLost
        // TODO add your handling code here:
        if (txtcodcan.getText().equals("")) {
            //txtCodItm.setText("");
            txtcodcan.setText("");
            txtNomCan.setText("");
        } else {
            mostrarVenConCan(1);
        }
    }//GEN-LAST:event_txtcodcanFocusLost

    private void txtcodcanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcanFocusGained
        // TODO add your handling code here:
        txtcodcan.selectAll();
    }//GEN-LAST:event_txtcodcanFocusGained

    private void butsegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butsegActionPerformed
        // TODO add your handling code here:
        mostrarVenConSeg(0);
    }//GEN-LAST:event_butsegActionPerformed

    private void txtNomSegKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomSegKeyReleased
        // TODO add your handling code here:

        if (txtNomSeg.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblModSeg.getRowCount() > 0) {

                txtcodseg.setText("");

                objTblModSeg.removeAllRows();
                tblDatSeg.setModel(objTblModSeg);
            }
        }
    }//GEN-LAST:event_txtNomSegKeyReleased

    private void txtNomSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomSegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomSegActionPerformed

    private void txtNomSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomSegFocusLost

    private void txtNomSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusGained
        // TODO add your handling code here:
        txtNomSeg.selectAll();
    }//GEN-LAST:event_txtNomSegFocusGained

    private void txtcodsegKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodsegKeyReleased
        // TODO add your handling code here:

        if (txtcodseg.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblModSeg.getRowCount() > 0) {

                txtNomSeg.setText("");

                objTblModSeg.removeAllRows();
                tblDatSeg.setModel(objTblModSeg);
            }
        }
    }//GEN-LAST:event_txtcodsegKeyReleased

    private void txtcodsegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodsegActionPerformed
        // TODO add your handling code here:
        txtcodseg.transferFocus();
    }//GEN-LAST:event_txtcodsegActionPerformed

    private void txtcodsegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodsegFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.

        if (txtcodseg.getText().equals("")) {
            //txtCodItm.setText("");
            txtcodseg.setText("");
            txtNomSeg.setText("");
        } else {
            mostrarVenConSeg(1);
        }
    }//GEN-LAST:event_txtcodsegFocusLost

    private void txtcodsegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodsegFocusGained
        // TODO add your handling code here:
        txtcodseg.selectAll();
    }//GEN-LAST:event_txtcodsegFocusGained

    private void cmbTieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTieActionPerformed

    private void butvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butvenActionPerformed
        mostrarVenConVen(0);
    }//GEN-LAST:event_butvenActionPerformed

    private void txtNomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomKeyReleased
        // TODO add your handling code here:

        if (txtNom.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblMod.getRowCount() > 0) {

                txtcodven.setText("");

                objTblMod.removeAllRows();
                tblDat.setModel(objTblMod);
            }
        }
    }//GEN-LAST:event_txtNomKeyReleased

    private void txtNomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomActionPerformed
        // TODO add your handling code here:
        txtNom.transferFocus();
    }//GEN-LAST:event_txtNomActionPerformed

    private void txtNomFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
    }//GEN-LAST:event_txtNomFocusLost

    private void txtNomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomFocusGained
        // TODO add your handling code here:

        txtNom.selectAll();
    }//GEN-LAST:event_txtNomFocusGained

    private void txtcodvenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodvenKeyReleased
        // TODO add your handling code here:

        if (txtcodven.getText().trim().equals("")) {

            //solo si hay datos en tabla
            if (objTblMod.getRowCount() > 0) {

                txtNom.setText("");

                objTblMod.removeAllRows();
                tblDat.setModel(objTblMod);
            }
        }
    }//GEN-LAST:event_txtcodvenKeyReleased

    private void txtcodvenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodvenActionPerformed
        // TODO add your handling code here:
        txtcodven.transferFocus();
    }//GEN-LAST:event_txtcodvenActionPerformed

    private void txtcodvenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodvenFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.

        if (txtcodven.getText().equals("")) {
            txtcodven.setText("");
            txtNom.setText("");
        } else {
            mostrarVenConVen(1);
        }
    }//GEN-LAST:event_txtcodvenFocusLost

    private void txtcodvenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodvenFocusGained
        // TODO add your handling code here:
        txtcodven.selectAll();
    }//GEN-LAST:event_txtcodvenFocusGained

    private void butCarExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarExcActionPerformed
        // TODO add your handling code here:
        cargarArchivoXLS();
        
        
    }//GEN-LAST:event_butCarExcActionPerformed

    private void jCheckBoxSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSegActionPerformed
        // TODO add your handling code here:
        spnLoc.setVisible(jCheckBoxSeg.isSelected());

        panLoc.revalidate();
        panLoc.repaint();
    }//GEN-LAST:event_jCheckBoxSegActionPerformed

    private void jCheckBoxLisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxLisActionPerformed
        // TODO add your handling code here:
        spnLis.setVisible(jCheckBoxLis.isSelected());

        panLis.revalidate();
        panLis.repaint();
    }//GEN-LAST:event_jCheckBoxLisActionPerformed

    private void cmbCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCanActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCarExc;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butcan;
    private javax.swing.JButton butseg;
    private javax.swing.JButton butven;
    private javax.swing.JComboBox<String> cmbCan;
    private javax.swing.JComboBox<String> cmbCli;
    private javax.swing.JComboBox<String> cmbSeg;
    private javax.swing.JComboBox<String> cmbTie;
    private javax.swing.JCheckBox jCheckBoxLis;
    private javax.swing.JCheckBox jCheckBoxSeg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblLis;
    private javax.swing.JLabel lblLis1;
    private javax.swing.JLabel lblLis2;
    private javax.swing.JLabel lblLis3;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JLabel lblVen2;
    private javax.swing.JLabel lblVen3;
    private javax.swing.JLabel lblVen4;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFil3;
    private javax.swing.JPanel panFil4;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panFrmCan;
    private javax.swing.JPanel panFrmCarga;
    private javax.swing.JPanel panFrmCli;
    private javax.swing.JPanel panFrmSeg;
    private javax.swing.JPanel panFrmVen;
    private javax.swing.JPanel panLis;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panSup;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnLis;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrmLog;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatCan;
    private javax.swing.JTable tblDatCli;
    private javax.swing.JTable tblDatExc;
    private javax.swing.JTable tblDatSeg;
    private javax.swing.JTable tblLis;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTextField txtFilLisAct;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtNomCan;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomSeg;
    private javax.swing.JTextField txtcodcan;
    private javax.swing.JTextField txtcodcli;
    private javax.swing.JTextField txtcodseg;
    private javax.swing.JTextField txtcodven;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            conTuvalApi = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(),objParSis.getUsuarioBaseDatos_TuvalApi(),objParSis.getClaveBaseDatos_TuvalApi());
            
            System.out.println(objParSis.getStringConexion_TuvalApi());
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objDocLis = new ZafDocLis();
           
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3");
            lblTit.setText(strAux);
            txtcodven.setBackground(objParSis.getColorCamposObligatorios());
            txtNom.setBackground(objParSis.getColorCamposObligatorios());
            
            txtcodseg.setBackground(objParSis.getColorCamposObligatorios());
            txtNomSeg.setBackground(objParSis.getColorCamposObligatorios());
            
            txtcodcan.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCan.setBackground(objParSis.getColorCamposObligatorios());
            
            txtcodcli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            
            txtFilLisAct.setVisible(false);
            jLabel1.setVisible(false);
            
            jCheckBoxSeg.setEnabled(false);
            jCheckBoxLis.setEnabled(false);
            spnLoc.setVisible(false);
            spnLis.setVisible(false);
            
            

            configurarFrmTbl();
            cargarComboTienda();
            
            configurarVenConVen();             
            configurarVenConSeg();            
            configurarVenConCan();
            configurarVenConCli();
            
            configurarTblLoc();
            cargarLoc();  
            
            configurarTblList();
            cargarList(); 
           
            configurarEventosFiltroSegmento();
            configurarEventosFiltroLista();
            
            
            
            tabFrmLog.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    limpiarPestana();
                }
            });
            
            cmbTie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaListaPrecio();
            }
            });
            
            cmbSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaListaPrecio();
            }
            });
            
            cmbCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaListaPrecio();
            }
            });
            
            cmbCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevaListaPrecio();
            }
            });
            
            
            
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /** Configurar el formulario. */
    private boolean configurarFrmTbl() {
        boolean blnRes = true;
        
        try {
            //Inicializar objetos.
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(3);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_RUC, "Ruc");
            vecCab.add(INT_TBL_DAT_NOM_CLI, "Cliente");
            vecCab.add(INT_TBL_DAT_SEG, "Segmento");                        
            vecCab.add(INT_TBL_DAT_LIS_ACT, "ListaPrecioActual");
            vecCab.add(INT_TBL_DAT_LIS_NUE, "ListaPrecioNueva");
            
            
            vecDatSeg = new Vector();    
            vecCabSeg = new Vector(3);  
            vecCabSeg.clear();
            vecCabSeg.add(INT_TBL_SEG_LIN, "");
            vecCabSeg.add(INT_TBL_SEG_CHK,"");
            vecCabSeg.add(INT_TBL_SEG_RUC, "Ruc");
            vecCabSeg.add(INT_TBL_SEG_NOM_CLI, "Cliente");
            vecCabSeg.add(INT_TBL_SEG_VEN, "Vendedor"); 
            vecCabSeg.add(INT_TBL_SEG_SUBSEG, "Segmento"); 
            vecCabSeg.add(INT_TBL_SEG_LIS_ACT, "ListaPrecioActual");
            vecCabSeg.add(INT_TBL_SEG_LIS_NUE, "ListaPrecioNueva");
            
            
            vecDatCan = new Vector();    
            vecCabCan = new Vector(3);  
            vecCabCan.clear();
            vecCabCan.add(INT_TBL_CAN_LIN, "");
            vecCabCan.add(INT_TBL_CAN_CHK,"");
            vecCabCan.add(INT_TBL_CAN_RUC, "Ruc");
            vecCabCan.add(INT_TBL_CAN_NOM_CLI, "Cliente");
            vecCabCan.add(INT_TBL_CAN_VEN, "Vendedor"); 
            vecCabCan.add(INT_TBL_CAN_SUBSEG, "Segmento"); 
            vecCabCan.add(INT_TBL_CAN_LIS_ACT, "ListaPrecioActual");
            vecCabCan.add(INT_TBL_CAN_LIS_NUE, "ListaPrecioNueva");
            
            
            vecDatCli = new Vector();    
            vecCabCli = new Vector(3);  
            vecCabCli.clear();
            vecCabCli.add(INT_TBL_CLI_LIN, "");
            vecCabCli.add(INT_TBL_CLI_CHK,"");
            vecCabCli.add(INT_TBL_CLI_RUC, "Ruc");
            vecCabCli.add(INT_TBL_CLI_NOM_CLI, "Cliente");
            vecCabCli.add(INT_TBL_CLI_VEN, "Vendedor"); 
            vecCabCli.add(INT_TBL_CLI_SUBSEG, "Segmento"); 
            vecCabCli.add(INT_TBL_CLI_LIS_ACT, "ListaPrecioActual");
            vecCabCli.add(INT_TBL_CLI_LIS_NUE, "ListaPrecioNueva");
            
            vecDatExc = new Vector();    
            vecCabExc = new Vector(3);  
            vecCabExc.clear();
            vecCabExc.add(INT_TBL_EXC_LIN, "");
            vecCabExc.add(INT_TBL_EXC_CHK,"");
            vecCabExc.add(INT_TBL_EXC_RUC, "Ruc");
            vecCabExc.add(INT_TBL_EXC_NOM_CLI, "Cliente");
            vecCabExc.add(INT_TBL_EXC_LIS_ACT, "ListaPrecioActal");
            vecCabExc.add(INT_TBL_EXC_LIS_NUE, "ListaPrecioNueva");
            
            

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            objTblModSeg = new ZafTblMod();
            objTblModSeg.setHeader(vecCabSeg);
            
            objTblModCan = new ZafTblMod();
            objTblModCan.setHeader(vecCabCan);
            
            objTblModCli = new ZafTblMod();
            objTblModCli.setHeader(vecCabCli);
            
            objTblModExc = new ZafTblMod();
            objTblModExc.setHeader(vecCabExc);
           
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            tblDatSeg.setModel(objTblModSeg);
            tblDatCan.setModel(objTblModCan);
            tblDatCli.setModel(objTblModCli);
            tblDatExc.setModel(objTblModExc);
            
            
            tblDat.setAutoCreateRowSorter(false);
            tblDatSeg.setAutoCreateRowSorter(false);
            tblDatCan.setAutoCreateRowSorter(false);
            tblDatCli.setAutoCreateRowSorter(false);
            tblDatExc.setAutoCreateRowSorter(false);
            
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDatSeg.setRowSelectionAllowed(true);
            tblDatCan.setRowSelectionAllowed(true);
            tblDatCli.setRowSelectionAllowed(true);
            tblDatExc.setRowSelectionAllowed(true);
            
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblDatSeg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblDatCan.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblDatCli.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            tblDatExc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objTblPopMnuSeg = new ZafTblPopMnu(tblDatSeg);
            objTblPopMnuCan = new ZafTblPopMnu(tblDatCan);
            objTblPopMnuCli = new ZafTblPopMnu(tblDatCli);
            objTblPopMnuExc = new ZafTblPopMnu(tblDatExc);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatSeg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatCan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatCli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblDatExc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            javax.swing.table.TableColumnModel tcmAuxSeg = tblDatSeg.getColumnModel();
            javax.swing.table.TableColumnModel tcmAuxCan = tblDatCan.getColumnModel();
            javax.swing.table.TableColumnModel tcmAuxCli = tblDatCli.getColumnModel();
            javax.swing.table.TableColumnModel tcmAuxExc = tblDatExc.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_RUC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_DAT_SEG).setPreferredWidth(300);            
            tcmAux.getColumn(INT_TBL_DAT_LIS_ACT).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_LIS_NUE).setPreferredWidth(110);
            
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIN).setPreferredWidth(30);
            tcmAuxSeg.getColumn(INT_TBL_SEG_CHK).setPreferredWidth(20);
            tcmAuxSeg.getColumn(INT_TBL_SEG_RUC).setPreferredWidth(100);
            tcmAuxSeg.getColumn(INT_TBL_SEG_NOM_CLI).setPreferredWidth(350);
            tcmAuxSeg.getColumn(INT_TBL_SEG_VEN).setPreferredWidth(250);            
            tcmAuxSeg.getColumn(INT_TBL_SEG_SUBSEG).setPreferredWidth(250);
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIS_ACT).setPreferredWidth(110);
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIS_NUE).setPreferredWidth(110);
            
            tcmAuxCan.getColumn(INT_TBL_CAN_LIN).setPreferredWidth(30);
            tcmAuxCan.getColumn(INT_TBL_CAN_CHK).setPreferredWidth(20);
            tcmAuxCan.getColumn(INT_TBL_CAN_RUC).setPreferredWidth(100);
            tcmAuxCan.getColumn(INT_TBL_CAN_NOM_CLI).setPreferredWidth(350);
            tcmAuxCan.getColumn(INT_TBL_CAN_VEN).setPreferredWidth(250);            
            tcmAuxCan.getColumn(INT_TBL_CAN_SUBSEG).setPreferredWidth(250);
            tcmAuxCan.getColumn(INT_TBL_CAN_LIS_ACT).setPreferredWidth(120);
            tcmAuxCan.getColumn(INT_TBL_CAN_LIS_NUE).setPreferredWidth(120);
            
            tcmAuxCli.getColumn(INT_TBL_CLI_LIN).setPreferredWidth(30);
            tcmAuxCli.getColumn(INT_TBL_CLI_CHK).setPreferredWidth(20);
            tcmAuxCli.getColumn(INT_TBL_CLI_RUC).setPreferredWidth(100);
            tcmAuxCli.getColumn(INT_TBL_CLI_NOM_CLI).setPreferredWidth(350);
            tcmAuxCli.getColumn(INT_TBL_CLI_VEN).setPreferredWidth(250);            
            tcmAuxCli.getColumn(INT_TBL_CLI_SUBSEG).setPreferredWidth(250);
            tcmAuxCli.getColumn(INT_TBL_CLI_LIS_ACT).setPreferredWidth(110);
            tcmAuxCli.getColumn(INT_TBL_CLI_LIS_NUE).setPreferredWidth(110);
            
            tcmAuxExc.getColumn(INT_TBL_EXC_LIN).setPreferredWidth(30);
            tcmAuxExc.getColumn(INT_TBL_EXC_CHK).setPreferredWidth(20);
            tcmAuxExc.getColumn(INT_TBL_EXC_RUC).setPreferredWidth(100);
            tcmAuxExc.getColumn(INT_TBL_EXC_NOM_CLI).setPreferredWidth(350); 
            tcmAuxExc.getColumn(INT_TBL_EXC_LIS_ACT).setPreferredWidth(110);
            tcmAuxExc.getColumn(INT_TBL_EXC_LIS_NUE).setPreferredWidth(110);
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            tcmAuxSeg.getColumn(INT_TBL_SEG_CHK).setResizable(false);
            tblDatSeg.getTableHeader().setReorderingAllowed(false);
            
            tcmAuxCan.getColumn(INT_TBL_CAN_CHK).setResizable(false);
            tblDatCan.getTableHeader().setReorderingAllowed(false);
            
            tcmAuxCli.getColumn(INT_TBL_CLI_CHK).setResizable(false);
            tblDatCli.getTableHeader().setReorderingAllowed(false);
            
            tcmAuxExc.getColumn(INT_TBL_EXC_CHK).setResizable(false);
            tblDatExc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            //objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafVen41.ZafMouMotAda());
            tblDatSeg.getTableHeader().addMouseMotionListener(new ZafVen41.ZafMouMotAda());
            tblDatCan.getTableHeader().addMouseMotionListener(new ZafVen41.ZafMouMotAda());
            tblDatCli.getTableHeader().addMouseMotionListener(new ZafVen41.ZafMouMotAda());
            tblDatExc.getTableHeader().addMouseMotionListener(new ZafVen41.ZafMouMotAda());
            
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            tblDatSeg.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            tblDatCan.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            tblDatCli.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            
            tblDatExc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            
            //filtro segmento en pestaña canales
            
            txtFilLisAct.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    ///filtrarSegmento();
                    filtrarListaActual();
                }
            });
            
            
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            vecAuxSeg=new Vector();
            vecAuxSeg.add("" + INT_TBL_SEG_CHK);
            objTblModSeg.setColumnasEditables(vecAuxSeg);
            vecAuxSeg=null;            
            
            vecAuxCan=new Vector();
            vecAuxCan.add("" + INT_TBL_CAN_CHK);
            objTblModCan.setColumnasEditables(vecAuxCan);
            vecAuxCan=null;
            
            vecAuxCli=new Vector();
            vecAuxCli.add("" + INT_TBL_CLI_CHK);
            objTblModCli.setColumnasEditables(vecAuxCli);
            vecAuxCli=null;
            
            
            vecAuxExc=new Vector();
            vecAuxExc.add("" + INT_TBL_EXC_CHK);
            objTblModExc.setColumnasEditables(vecAuxExc);
            vecAuxExc=null;
               
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDatSeg.getTableHeader().setReorderingAllowed(false);
            tblDatCan.getTableHeader().setReorderingAllowed(false);
            tblDatCli.getTableHeader().setReorderingAllowed(false);
            tblDatExc.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            objTblFilCabSeg = new ZafTblFilCab(tblDatSeg);
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIN).setCellRenderer(objTblFilCabSeg);
            
            objTblFilCabCan = new ZafTblFilCab(tblDatCan);
            tcmAuxCan.getColumn(INT_TBL_CAN_LIN).setCellRenderer(objTblFilCabCan);
            
            objTblFilCabCli = new ZafTblFilCab(tblDatCli);
            tcmAuxCli.getColumn(INT_TBL_CLI_LIN).setCellRenderer(objTblFilCabCli);
            
            objTblFilCabExc = new ZafTblFilCab(tblDatExc);
            tcmAuxExc.getColumn(INT_TBL_EXC_LIN).setCellRenderer(objTblFilCabExc);
            
            //Configurar JTable: Renderizar celdas.
            ZafTblCelRenChk renChkVen = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(renChkVen);

            ZafTblCelRenChk renChkSeg = new ZafTblCelRenChk();
            tcmAuxSeg.getColumn(INT_TBL_SEG_CHK).setCellRenderer(renChkSeg);
            
            ZafTblCelRenChk renChkCan = new ZafTblCelRenChk();
            tcmAuxCan.getColumn(INT_TBL_CAN_CHK).setCellRenderer(renChkCan);            
            
            ZafTblCelRenChk renChkCli = new ZafTblCelRenChk();
            tcmAuxCan.getColumn(INT_TBL_CLI_CHK).setCellRenderer(renChkCan); 
            
            ZafTblCelRenChk renChkExc = new ZafTblCelRenChk();
            tcmAuxExc.getColumn(INT_TBL_EXC_CHK).setCellRenderer(renChkExc); 
            
            
            objTblCelRenChk=null;
            
            //
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);            
            
            objTblCelEdiChkSeg = new ZafTblCelEdiChk(tblDatSeg);
            tcmAuxSeg.getColumn(INT_TBL_SEG_CHK).setCellEditor(objTblCelEdiChkSeg);
            
            objTblCelEdiChkCan = new ZafTblCelEdiChk(tblDatCan);
            tcmAuxCan.getColumn(INT_TBL_CAN_CHK).setCellEditor(objTblCelEdiChkCan);
            
            objTblCelEdiChkCli = new ZafTblCelEdiChk(tblDatCli);
            tcmAuxCan.getColumn(INT_TBL_CLI_CHK).setCellEditor(objTblCelEdiChkCli);
            
            objTblCelEdiChkExc = new ZafTblCelEdiChk(tblDatExc);
            tcmAuxExc.getColumn(INT_TBL_EXC_CHK).setCellEditor(objTblCelEdiChkExc);
            
            // FILTRO JP
            objTblFilVen = new ZafTblFiltro(tblDat, objTblMod, INT_TBL_DAT_RUC, INT_TBL_DAT_NOM_CLI,lblMsgSis);            
            objTblOrd=new ZafTblOrd(tblDat);
            
            objTblFilSeg = new ZafTblFiltro(tblDatSeg, objTblModSeg, INT_TBL_SEG_RUC, INT_TBL_SEG_NOM_CLI,lblMsgSis);
            objTblOrd=new ZafTblOrd(tblDatSeg);
            
            objTblFilCan = new ZafTblFiltro(tblDatCan, objTblModCan, INT_TBL_CAN_RUC, INT_TBL_CAN_NOM_CLI,lblMsgSis);            
            objTblOrd=new ZafTblOrd(tblDatCan);
            
            objTblFilCli = new ZafTblFiltro(tblDatCli, objTblModCli, INT_TBL_CLI_RUC, INT_TBL_CLI_NOM_CLI,lblMsgSis);            
            objTblOrd=new ZafTblOrd(tblDatCli);
            
            objTblFilExc = new ZafTblFiltro(tblDatExc, objTblModExc, INT_TBL_EXC_RUC, INT_TBL_EXC_NOM_CLI,lblMsgSis);            
            objTblOrd=new ZafTblOrd(tblDatExc);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDatSeg.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDatCan.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDatCli.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblDatExc.getTableHeader().addMouseMotionListener(objMouMotAda);

            //
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_RUC).setCellRenderer(objTblCelRenLbl);            
             
            objTblCelRenLblSeg = new ZafTblCelRenLbl();
            objTblCelRenLblSeg.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAuxSeg.getColumn(INT_TBL_SEG_RUC).setCellRenderer(objTblCelRenLblSeg); 
            
            objTblCelRenLblCan = new ZafTblCelRenLbl();
            objTblCelRenLblCan.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAuxCan.getColumn(INT_TBL_CAN_RUC).setCellRenderer(objTblCelRenLblCan);
            
            objTblCelRenLblCli = new ZafTblCelRenLbl();
            objTblCelRenLblCli.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAuxCli.getColumn(INT_TBL_CLI_RUC).setCellRenderer(objTblCelRenLblCli);
            
            objTblCelRenLblExc = new ZafTblCelRenLbl();
            objTblCelRenLblExc.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAuxExc.getColumn(INT_TBL_EXC_RUC).setCellRenderer(objTblCelRenLblExc);
            
            DefaultTableCellRenderer renderLista = new DefaultTableCellRenderer() {

                @Override
                public java.awt.Component getTableCellRendererComponent(
                        javax.swing.JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {

                    java.awt.Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);

                    if (value != null && !isSelected) {
                        String val = value.toString().trim().toUpperCase();
                        c.setBackground(obtenerColorLista(val));
                    } else if (isSelected) {
                        c.setBackground(table.getSelectionBackground());
                    }

                    return c;
                }
            };
            
            tcmAux.getColumn(INT_TBL_DAT_LIS_ACT).setCellRenderer(renderLista);
            tcmAux.getColumn(INT_TBL_DAT_LIS_NUE).setCellRenderer(renderLista);
            
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIS_ACT).setCellRenderer(renderLista);
            tcmAuxSeg.getColumn(INT_TBL_SEG_LIS_NUE).setCellRenderer(renderLista);
            
            tcmAuxCan.getColumn(INT_TBL_CAN_LIS_ACT).setCellRenderer(renderLista);
            tcmAuxCan.getColumn(INT_TBL_CAN_LIS_NUE).setCellRenderer(renderLista);
            
            tcmAuxCli.getColumn(INT_TBL_CLI_LIS_ACT).setCellRenderer(renderLista);
            tcmAuxCli.getColumn(INT_TBL_CLI_LIS_NUE).setCellRenderer(renderLista);
            
            tcmAuxExc.getColumn(INT_TBL_EXC_LIS_ACT).setCellRenderer(renderLista); 
            tcmAuxExc.getColumn(INT_TBL_EXC_LIS_NUE).setCellRenderer(renderLista);            
            
            objTblCelRenLbl = null;

            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            objTblModSeg.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            objTblModCan.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            objTblModCli.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            objTblModExc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            tcmAux=null;
            tcmAuxSeg=null;
            tcmAuxCan=null;
            tcmAuxCli=null;
            tcmAuxExc=null;


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFilSeg=new Vector();    //Almacena los datos
            vecCabFilSeg=new Vector(4);   //Almacena las cabeceras
            vecCabFilSeg.clear();
            vecCabFilSeg.add(INT_TBL_FILSEG_LIN,"");
            vecCabFilSeg.add(INT_TBL_FILSEG_CHK,"");
            vecCabFilSeg.add(INT_TBL_FILSEG_COD,"Cód");
            vecCabFilSeg.add(INT_TBL_FILSEG_SUBSEG,"Segmento");            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFilSeg=new ZafTblMod();
            objTblModFilSeg.setHeader(vecCabFilSeg);
            tblLoc.setModel(objTblModFilSeg);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_FILSEG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FILSEG_CHK).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_FILSEG_COD).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_FILSEG_SUBSEG).setPreferredWidth(215);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FILSEG_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
           objTblModFilSeg.addSystemHiddenColumn(INT_TBL_FILSEG_LIN, tblLoc);
           objTblModFilSeg.addSystemHiddenColumn(INT_TBL_FILSEG_COD, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAuxFilSeg=new Vector();
            vecAuxFilSeg.add("" + INT_TBL_FILSEG_CHK);
            objTblModFilSeg.setColumnasEditables(vecAuxFilSeg);
            vecAuxFilSeg=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_FILSEG_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FILSEG_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_FILSEG_COD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FILSEG_SUBSEG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_FILSEG_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Ordenar tabla
            objTblOrd=new ZafTblOrd(tblLoc);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModFilSeg.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblList()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFilLis=new Vector();    //Almacena los datos
            vecCabFilLis=new Vector(3);   //Almacena las cabeceras
            vecCabFilLis.clear();
            vecCabFilLis.add(INT_TBL_FILIS_LIN,"");
            vecCabFilLis.add(INT_TBL_FILIS_CHK,"");
            vecCabFilLis.add(INT_TBL_FILIS_LIS,"Lista");
                       
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFilLis=new ZafTblMod();
            objTblModFilLis.setHeader(vecCabFilLis);
            tblLis.setModel(objTblModFilLis);
            //Configurar JTable: Establecer tipo de selección.
            tblLis.setRowSelectionAllowed(true);
            tblLis.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLis);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLis.getColumnModel();
            tcmAux.getColumn(INT_TBL_FILIS_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FILIS_CHK).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_FILIS_LIS).setPreferredWidth(85);
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FILIS_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLis.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
           objTblModFilLis.addSystemHiddenColumn(INT_TBL_FILIS_LIN, tblLis);
          
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLis.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLis.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAuxFilLis=new Vector();
            vecAuxFilLis.add("" + INT_TBL_FILIS_CHK);
            objTblModFilLis.setColumnasEditables(vecAuxFilLis);
            vecAuxFilLis=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLis);
            tcmAux.getColumn(INT_TBL_FILIS_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FILIS_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            tcmAux.getColumn(INT_TBL_FILIS_LIS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLis);
            tcmAux.getColumn(INT_TBL_FILIS_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Ordenar tabla
            objTblOrd=new ZafTblOrd(tblLis);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModFilLis.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
     
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)
    {
        try {

            if (!(evt.getSource() instanceof javax.swing.table.JTableHeader)) {
                return;
            }

            javax.swing.JTable tbl = ((javax.swing.table.JTableHeader) evt.getSource()).getTable();

            int col = tbl.columnAtPoint(evt.getPoint());

            if (tbl == tblDat && col == INT_TBL_DAT_CHK) {

                int rows = objTblMod.getRowCountTrue();

                for (int i = 0; i < rows; i++) {
                    objTblMod.setChecked(blnMarTodChkTblVen, i, INT_TBL_DAT_CHK);
                }

                blnMarTodChkTblVen = !blnMarTodChkTblVen;
            }
            else if (tbl == tblDatSeg && col == INT_TBL_SEG_CHK) {

                int rows = objTblModSeg.getRowCountTrue();

                for (int i = 0; i < rows; i++) {
                    objTblModSeg.setChecked(blnMarTodChkTblSeg, i, INT_TBL_SEG_CHK);
                }

                blnMarTodChkTblSeg = !blnMarTodChkTblSeg;
            }
            else if (tbl == tblDatCan && col == INT_TBL_CAN_CHK) {

                int rows = objTblModCan.getRowCountTrue();

                for (int i = 0; i < rows; i++) {
                    objTblModCan.setChecked(blnMarTodChkTblCan, i, INT_TBL_CAN_CHK);
                }

                blnMarTodChkTblCan = !blnMarTodChkTblCan;
            }
            else if (tbl == tblDatCli && col == INT_TBL_CLI_CHK) {

                int rows = objTblModCli.getRowCountTrue();

                for (int i = 0; i < rows; i++) {
                    objTblModCli.setChecked(blnMarTodChkTblCli, i, INT_TBL_CLI_CHK);
                }

                blnMarTodChkTblCli = !blnMarTodChkTblCli;
            }
            else if (tbl == tblDatExc && col == INT_TBL_EXC_CHK) {

                int rows = objTblModExc.getRowCountTrue();

                for (int i = 0; i < rows; i++) {
                    objTblModExc.setChecked(blnMarTodChkTblExc, i, INT_TBL_EXC_CHK);
                }

                blnMarTodChkTblExc = !blnMarTodChkTblExc;
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModFilSeg.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_FILSEG_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModFilSeg.setChecked(true, i, INT_TBL_FILSEG_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModFilSeg.setChecked(false, i, INT_TBL_FILSEG_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

      

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtcodven.getDocument().addDocumentListener(objDocLis);
        txtNom.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }


    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() {
        boolean blnRes = true;
        try {
            txtcodven.setText("");
            txtNom.setText("");
            objTblMod.removeAllRows();
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

 
    private boolean cargarDetReg() {
        boolean blnRes = true;
        try {
            tblDat.setRowSorter(null);
            objTblMod.setData(new Vector());
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            // conexión SQL Server (Dynamics/TUVAL)
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null) {
                
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT DB_NAME()");

                String codVen = txtcodven.getText().trim();
                String nomVen = txtNom.getText().trim();
                
                if (rs.next()) {
                    System.out.println("JAVA ESTÁ CONECTADO A BD: " + rs.getString(1));
                }
                
                stm = con.createStatement();
                
                strSQL = "";
                strSQL += "SELECT DISTINCT ";
                strSQL += "r.CUSTNMBR, r.CUSTNAME,CONCAT(seg.subsegmento,' - ',seg.segmento) as segmento, r.PRCLEVEL ";
                strSQL += "FROM Dynamics2021.dbo.tbr_cliloc c WITH (NOLOCK)";
                strSQL += "INNER JOIN TUVAL.dbo.RM00101 r WITH (NOLOCK)";
                strSQL += "ON c.tx_cli = r.CUSTNMBR ";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbm_usr u WITH (NOLOCK)";
                strSQL += "ON c.co_ven = u.co_usr ";
                strSQL += "LEFT JOIN Dynamics2021.dbo.vw_clientesPorSegmento seg WITH (NOLOCK)";
                strSQL += "ON (seg.tx_cli = r.CUSTNMBR) ";
                strSQL += "WHERE c.st_tipVen = 1 ";
                //strSQL += "AND u.tx_usr = '" + txUsr + "' ";
                if (codVen != null && !codVen.equals("")) {

                    strSQL += "AND u.co_usr = " + codVen + " ";

                } else if (!nomVen.equals("")) {

                    strSQL += "AND UPPER(u.tx_nom) = UPPER('" + nomVen + "') ";
                }

                System.out.println("cargarDetReg: " + strSQL);

                rst = stm.executeQuery(strSQL);
               
                vecDat.clear();

                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);

                while (rst.next()) {
                    if (blnCon) {
                        vecReg = new Vector();

                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_CHK, true); // 
                        vecReg.add(INT_TBL_DAT_RUC, rst.getString("CUSTNMBR"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI, rst.getString("CUSTNAME"));
                        vecReg.add(INT_TBL_DAT_SEG, rst.getString("segmento"));                        
                        vecReg.add(INT_TBL_DAT_LIS_ACT, rst.getString("PRCLEVEL"));

                        String lisNue = cmbTie.getSelectedItem().toString();
                        vecReg.add(INT_TBL_DAT_LIS_NUE, lisNue);
                        
                        vecDat.add(vecReg);
                    } else {
                        break;
                    }
                }

                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;

                objTblMod.setData(vecDat);
                
                objTblMod.fireTableDataChanged();
                
                vecDat.clear();

                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                
                if (tblDat.getRowCount() > 0) {
                    tblDat.setRowSelectionInterval(0, 0);
                }
                
            }

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean cargarDetRegSeg() {
        boolean blnRes = true;
        try {
            tblDatSeg.setRowSorter(null);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos por segmento...");
            
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null) {
                stm = con.createStatement();

                String coSeg = txtcodseg.getText(); 
                String nomSeg = txtNomSeg.getText().trim();

                strSQL = "";
                strSQL += "SELECT DISTINCT ";
                strSQL += "r.CUSTNMBR, r.CUSTNAME, ";
                strSQL += "u.tx_nom AS vendedor, ";
                strSQL += "CONCAT(hijo.tx_nom,' - ',padre.tx_nom) as subsegmento, ";                
                strSQL += "r.PRCLEVEL ";
                strSQL += "FROM TUVAL.dbo.RM00101 r WITH (NOLOCK)";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbr_atrcli cli WITH (NOLOCK) ";
                strSQL += "ON cli.tx_cli = r.CUSTNMBR ";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli hijo WITH (NOLOCK) ";
                strSQL += "ON hijo.co_atrcli = cli.co_atrcli ";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli padre WITH (NOLOCK) ";
                strSQL += "ON padre.co_atrcli = hijo.co_pad ";
                strSQL += "LEFT JOIN Dynamics2021.dbo.tbr_cliloc c WITH (NOLOCK) ON c.tx_cli = r.CUSTNMBR ";
                strSQL += "LEFT JOIN Dynamics2021.dbo.tbm_usr u WITH (NOLOCK) ON c.co_ven = u.co_usr ";
                strSQL += "WHERE padre.st_reg = 'A' ";
                strSQL += "AND hijo.st_reg = 'A' ";
                strSQL += "AND c.st_tipVen = 1 ";
                //strSQL += "AND hijo.co_atrcli = " + coSeg + " ";
                if (coSeg != null && !coSeg.trim().equals("")) {

                    strSQL += "AND hijo.co_atrcli = " + coSeg + " ";

                } else if (!nomSeg.equals("")) {

                    strSQL += "AND UPPER(hijo.tx_nom) = UPPER('" + nomSeg + "') ";
                }
                

                System.out.println("cargarDetRegSeg: " + strSQL);

                rst = stm.executeQuery(strSQL);
                
                vecDatSeg.clear();

                lblMsgSis.setText("Cargando datos por segmento...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);

                while (rst.next()) {
                    if (blnCon) {
                        vecRegSeg = new Vector();

                        vecRegSeg.add(INT_TBL_SEG_LIN, "");
                        vecRegSeg.add(INT_TBL_SEG_CHK, true);
                        vecRegSeg.add(INT_TBL_SEG_RUC, rst.getString("CUSTNMBR"));
                        vecRegSeg.add(INT_TBL_SEG_NOM_CLI, rst.getString("CUSTNAME"));
                        vecRegSeg.add(INT_TBL_SEG_VEN, rst.getString("vendedor"));
                        vecRegSeg.add(INT_TBL_SEG_SUBSEG, rst.getString("subsegmento"));                        
                        vecRegSeg.add(INT_TBL_SEG_LIS_ACT, rst.getString("PRCLEVEL"));

                        String lisNue = cmbSeg.getSelectedItem().toString();
                        vecRegSeg.add(INT_TBL_SEG_LIS_NUE, lisNue);

                        vecDatSeg.add(vecRegSeg);
                    } else {
                        break;
                    }
                }

                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;

                objTblModSeg.setData(vecDatSeg);
                //tblDatSeg.setModel(objTblModSeg);
                vecDatSeg.clear();

                lblMsgSis.setText("Se encontraron " + tblDatSeg.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                
                if (tblDatSeg.getRowCount() > 0) {
                    tblDatSeg.setRowSelectionInterval(0, 0);
                }
                
            }

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetRegCan() {
        boolean blnRes = true;
        try {
            tblDatCan.setRowSorter(null);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos por canal...");
            
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null) {
                stm = con.createStatement();

                String co_can = txtcodcan.getText(); 
                String nomCan = txtNomCan.getText().trim();

                strSQL = "";
                strSQL += "SELECT DISTINCT ";
                strSQL += "r.CUSTNMBR, r.CUSTNAME, ";
                strSQL += "can.vendedor as vendedor, ";
                strSQL += "CONCAT(seg.subsegmento,' - ',seg.segmento) AS segmento, ";
                strSQL += "r.PRCLEVEL ";
                strSQL += "FROM Dynamics2021.dbo.vw_clientesPorCanal can WITH (NOLOCK)";
                strSQL += "INNER JOIN TUVAL.dbo.RM00101 r WITH (NOLOCK) ON can.tx_cli = r.CUSTNMBR ";
                strSQL += "LEFT JOIN Dynamics2021.dbo.vw_clientesPorSegmento seg  WITH (NOLOCK) ON seg.tx_cli = r.CUSTNMBR "; 
                strSQL += "WHERE 1 = 1 ";
                //strSQL += "AND can.co_canal = " + co_can + " ";
                if (co_can != null && !co_can.trim().equals("")) {

                    strSQL += "AND can.co_canal = " + co_can + " ";

                } else if (!nomCan.equals("")) {

                    strSQL += "AND can.canal = '" + nomCan + "' ";
                }
                

                System.out.println("cargarDetRegCan: " + strSQL);

                rst = stm.executeQuery(strSQL);
                
                vecDatCan.clear();

                lblMsgSis.setText("Cargando datos por canales...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);

                while (rst.next()) {
                    if (blnCon) {
                        vecRegCan = new Vector();

                        vecRegCan.add(INT_TBL_CAN_LIN, "");
                        vecRegCan.add(INT_TBL_CAN_CHK, true);
                        vecRegCan.add(INT_TBL_CAN_RUC, rst.getString("CUSTNMBR"));
                        vecRegCan.add(INT_TBL_CAN_NOM_CLI, rst.getString("CUSTNAME"));
                        vecRegCan.add(INT_TBL_CAN_VEN, rst.getString("vendedor"));
                        vecRegCan.add(INT_TBL_CAN_SUBSEG, rst.getString("segmento"));                        
                        vecRegCan.add(INT_TBL_CAN_LIS_ACT, rst.getString("PRCLEVEL"));

                        String lisNue = cmbCan.getSelectedItem().toString();
                        vecRegCan.add(INT_TBL_CAN_LIS_NUE, lisNue);

                        vecDatCan.add(vecRegCan);
                        
                        //txtFilLisAct.setText("");
                        //txtFilLisAct.setEnabled(true);
                        jCheckBoxSeg.setEnabled(true);
                        jCheckBoxLis.setEnabled(true);
                    } else {
                        //txtFilLisAct.setEnabled(false);
                        jCheckBoxSeg.setEnabled(false);
                        jCheckBoxLis.setEnabled(false);
                        break;
                    }
                }

                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;

                objTblModCan.setData(vecDatCan);
                //tblDatCan.setModel(objTblModCan);
                vecDatCan.clear();

                lblMsgSis.setText("Se encontraron " + tblDatCan.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                
                if (tblDatCan.getRowCount() > 0) {
                    tblDatCan.setRowSelectionInterval(0, 0);
                }
                
            }

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetRegCli() {
        boolean blnRes = true;
        try {
            
            tblDatCli.setRowSorter(null);
            objTblModCli.setData(new Vector());
            butCli.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            con = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
            );

            if (con != null) {

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT DB_NAME()");
                if (rs.next()) {
                    System.out.println("JAVA ESTÁ CONECTADO A BD: " + rs.getString(1));
                }

                stm = con.createStatement();

                String codCli = txtcodcli.getText().trim();
                String nomCli = txtNomCli.getText().trim();
                
                strSQL = "";
                strSQL += "SELECT DISTINCT ";
                strSQL += "r.CUSTNMBR, r.CUSTNAME, ";
                strSQL += "u.tx_nom as vendedor, ";
                strSQL += "CONCAT(seg.subsegmento,' - ',seg.segmento) as segmento, ";
                strSQL += "r.PRCLEVEL \n";
                strSQL += "FROM TUVAL.dbo.RM00101 r WITH (NOLOCK) \n";
                strSQL += "LEFT JOIN Dynamics2021.dbo.tbr_cliloc c WITH (NOLOCK) ";
                strSQL += "ON (c.tx_cli = r.CUSTNMBR) \n";
                strSQL += "LEFT JOIN Dynamics2021.dbo.tbm_usr u WITH (NOLOCK) ";
                strSQL += "ON (c.co_ven = u.co_usr) \n";
                strSQL += "LEFT JOIN Dynamics2021.dbo.vw_clientesPorSegmento seg WITH (NOLOCK) ";
                strSQL += "ON (seg.tx_cli = r.CUSTNMBR) \n";
                strSQL += "LEFT JOIN ( ";
                strSQL += "    SELECT DISTINCT c2.tx_cli ";
                strSQL += "    FROM Dynamics2021.dbo.tbr_cliloc c2 ";
                strSQL += "    INNER JOIN Dynamics2021.dbo.tbm_usr u2 ";
                strSQL += "        ON c2.co_ven = u2.co_usr ";
                strSQL += "    WHERE u2.tx_nom <> 'OFICINA' ";
                strSQL += ") cliConVendedor ";
                strSQL += "ON cliConVendedor.tx_cli = r.CUSTNMBR \n";
                strSQL += "WHERE 1=1 \n";
                strSQL += "AND NOT ( \n";
                strSQL += "    u.tx_nom = 'OFICINA' \n";
                strSQL += "    AND cliConVendedor.tx_cli IS NOT NULL \n";
                strSQL += ") \n";
                 
                if (!codCli.equals("")) {
                    strSQL += "AND r.CUSTNMBR = '" + codCli + "' ";
                }

                if (!nomCli.equals("")) {
                    strSQL += "AND r.CUSTNAME = '" + nomCli + "' ";
                }

                System.out.println("cargarClientes: " + strSQL);

                rst = stm.executeQuery(strSQL);

                vecDatCli.clear();

                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);

                while (rst.next()) {
                    if (blnCon) {
                        vecRegCli = new Vector();

                        vecRegCli.add(INT_TBL_CLI_LIN, "");
                        vecRegCli.add(INT_TBL_CLI_CHK, true);
                        vecRegCli.add(INT_TBL_CLI_RUC, rst.getString("CUSTNMBR"));
                        vecRegCli.add(INT_TBL_CLI_NOM_CLI, rst.getString("CUSTNAME"));
                        vecRegCli.add(INT_TBL_CLI_VEN, rst.getString("vendedor")); 
                        vecRegCli.add(INT_TBL_CLI_SUBSEG, rst.getString("segmento"));
                        vecRegCli.add(INT_TBL_CLI_LIS_ACT, rst.getString("PRCLEVEL"));

                        String lisNue = cmbCli.getSelectedItem().toString();
                        vecRegCli.add(INT_TBL_CLI_LIS_NUE, lisNue);

                        vecDatCli.add(vecRegCli);
                    } else {
                        break;
                    }
                }

                rst.close();
                stm.close();
                con.close();

                objTblModCli.setData(vecDatCli);
                objTblModCli.fireTableDataChanged();

                vecDatCli.clear();

                lblMsgSis.setText("Se encontraron " + tblDatCli.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCli.setText("Consultar");

                if (tblDatCli.getRowCount() > 0) {
                    tblDatCli.setRowSelectionInterval(0, 0);
                }
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean cargarComboTienda() {
        boolean blnRes = true;
        try {
            Connection con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            
            Statement stm = con.createStatement();

            String strSQL = "";
            strSQL += "SELECT PRCLEVEL, DSCRIPTN ";
            strSQL += "FROM TUVAL.dbo.IV40800 ";
            strSQL += "ORDER BY PRCLEVEL";

            ResultSet rs = stm.executeQuery(strSQL);

            cmbTie.removeAllItems();

            while (rs.next()) {
                String prclvl = rs.getString("PRCLEVEL");
                String desc = rs.getString("DSCRIPTN");
                
                cmbTie.addItem(prclvl);
                cmbSeg.addItem(prclvl);
                cmbCan.addItem(prclvl);
                cmbCli.addItem(prclvl);
            }

            rs.close();
            stm.close();
            con.close();

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }               

    
    private void NuevaListaPrecio() {

        String nuevaLista = "";

        if (tabFrmLog.getSelectedIndex() == 0) {
            // VENDEDOR
            nuevaLista = cmbTie.getSelectedItem().toString();

            for (int i = 0; i < tblDat.getRowCount(); i++) {
                int modelRow = tblDat.convertRowIndexToModel(i);
                objTblMod.setValueAt(nuevaLista, modelRow, INT_TBL_DAT_LIS_NUE);
                
                //boolean chk = (Boolean) objTblMod.getValueAt(modelRow, INT_TBL_DAT_CHK);
                
                /*if (chk) {
                    objTblMod.setValueAt(nuevaLista, modelRow, INT_TBL_DAT_LIS_NUE);
                }*/
            }
            tblDat.repaint();

        } else if (tabFrmLog.getSelectedIndex() == 1) {
            //SEGMENTO
            nuevaLista = cmbSeg.getSelectedItem().toString();

            for (int i = 0; i < tblDatSeg.getRowCount(); i++) {
                int modelRow = tblDatSeg.convertRowIndexToModel(i);
                
                objTblModSeg.setValueAt(nuevaLista, modelRow, INT_TBL_SEG_LIS_NUE);
                
                //boolean chk = (Boolean) objTblModSeg.getValueAt(modelRow, INT_TBL_SEG_CHK);
                
                /*if (chk) {
                    objTblModSeg.setValueAt(nuevaLista, modelRow, INT_TBL_SEG_LIS_NUE);
                }*/
            }

            tblDatSeg.repaint();
        }
        else if (tabFrmLog.getSelectedIndex() == 2) {
            //CANAL
            nuevaLista = cmbCan.getSelectedItem().toString();

            for (int i = 0; i < tblDatCan.getRowCount(); i++) {
                int modelRow = tblDatCan.convertRowIndexToModel(i);
                
                objTblModCan.setValueAt(nuevaLista, modelRow, INT_TBL_CAN_LIS_NUE);
                //boolean chk = (Boolean) objTblModCan.getValueAt(modelRow, INT_TBL_CAN_CHK);
                
                /*if (chk) {
                    objTblModCan.setValueAt(nuevaLista, modelRow, INT_TBL_CAN_LIS_NUE);
                }*/
            }

            tblDatCan.repaint();
        }
        else if (tabFrmLog.getSelectedIndex() == 3) {
            //CLIENTES
            nuevaLista = cmbCli.getSelectedItem().toString();

            for (int i = 0; i < tblDatCli.getRowCount(); i++) {
                int modelRow = tblDatCli.convertRowIndexToModel(i);
                
                objTblModCli.setValueAt(nuevaLista, modelRow, INT_TBL_CLI_LIS_NUE);
                //boolean chk = (Boolean) objTblModCan.getValueAt(modelRow, INT_TBL_CAN_CHK);
                
                /*if (chk) {
                    objTblModCan.setValueAt(nuevaLista, modelRow, INT_TBL_CAN_LIS_NUE);
                }*/
            }

            tblDatCli.repaint();
        }
        
    }
    
    //agregar aqui manualmente una nueva lista para asignarle color
    private Color obtenerColorLista(String val) {

        if (val == null) return Color.WHITE;

        val = val.trim().toUpperCase();

        if (val.equals("GENERAL")) {
            return new Color(204, 255, 204);
        } 
        else if (val.equals("DISTRI")) {
            return new Color(255, 255, 153);
        } 
        else if (val.equals("RETAIL_GEN")) {
            return new Color(0, 204, 255);
        } 
        else if (val.equals("COMPETENC")) {
            return new Color(255, 204, 0);
        } 
        else if (val.equals("TIENDA")) {
            return new Color(204, 153, 255);
        }

        return Color.WHITE;
    }
    
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() {
        if (txtNom.getText().equals("")) {
            //tabFrmCli.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Vendedor </FONT> es obligatorio.<BR>Escriba o seleccione un vendedor y vuelva a intentarlo.</HTML>");
            txtNom.requestFocus();
            return false;
        }

        return true;
    }
    
     private boolean isCamValSeg() {
        if (txtNomSeg.getText().equals("")) {
            //tabFrmCli.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Segmento </FONT> es obligatorio.<BR>Escriba o seleccione un segmento y vuelva a intentarlo.</HTML>");
            txtNomSeg.requestFocus();
            return false;
        }

        return true;
    }
     
     private boolean isCamValCan() {
        if (txtNomCan.getText().equals("")) {
            //tabFrmCli.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Canal </FONT> es obligatorio.<BR>Escriba o seleccione un canal y vuelva a intentarlo.</HTML>");
            txtNomCan.requestFocus();
            return false;
        }

        return true;
    }
     
    private boolean isCamValCli() {
        if (txtNomCli.getText().equals("")) {
            //tabFrmCli.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Cliente </FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
            txtNomCli.requestFocus();
            return false;
        }

        return true;
    } 
     
    
    private boolean guardar() {
        boolean blnRes = true;
        
        try {
            
            //lblMsgSis.setText("Guardando clientes...");
            pgrSis.setIndeterminate(true);
            
            con = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            conTuvalApi = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(),objParSis.getUsuarioBaseDatos_TuvalApi(),objParSis.getClaveBaseDatos_TuvalApi());
            con.setAutoCommit(false);
            conTuvalApi.setAutoCommit(false); 
            
           
                      
            int tab = tabFrmLog.getSelectedIndex();
            
            int[] COL_LIS_ACT = {INT_TBL_DAT_LIS_ACT,INT_TBL_SEG_LIS_ACT,INT_TBL_CAN_LIS_ACT,INT_TBL_CLI_LIS_ACT};            

            boolean haySeleccion = false;

            int rowCount = 0;
            String prcLevel = "";
            String tipo = "";
            ZafTblMod modelo = null;
            int colChk = 0;
            int colRuc = 0;
            int colNom = 0;

            //CONFIGURAR SEGÚN PESTAÑA
            if (tab == 0) {
                rowCount = tblDat.getRowCount();
                prcLevel = cmbTie.getSelectedItem().toString();
                tipo = "Por Vendedor";
                modelo = objTblMod;
                colChk = INT_TBL_DAT_CHK;
                colRuc = INT_TBL_DAT_RUC;
                colNom = INT_TBL_DAT_NOM_CLI;

            } else if (tab == 1) {
                rowCount = tblDatSeg.getRowCount();
                prcLevel = cmbSeg.getSelectedItem().toString();
                tipo = "Por Segmento";
                modelo = objTblModSeg;
                colChk = INT_TBL_SEG_CHK;
                colRuc = INT_TBL_SEG_RUC;
                colNom = INT_TBL_SEG_NOM_CLI;

            } else if (tab == 2) {
                rowCount = tblDatCan.getRowCount();
                prcLevel = cmbCan.getSelectedItem().toString();
                tipo = "Por Canal";
                modelo = objTblModCan;
                colChk = INT_TBL_CAN_CHK;
                colRuc = INT_TBL_CAN_RUC;
                colNom = INT_TBL_CAN_NOM_CLI;
            }
            
            else if (tab == 3) {
                rowCount = tblDatCli.getRowCount();
                prcLevel = cmbCli.getSelectedItem().toString();
                tipo = "Por cliente";
                modelo = objTblModCli;
                colChk = INT_TBL_CLI_CHK;
                colRuc = INT_TBL_CLI_RUC;
                colNom = INT_TBL_CLI_NOM_CLI;
            }
            else if (tab == 4) {
                rowCount = tblDatExc.getRowCount();
                prcLevel = ""; // no se usa
                tipo = "Por Excel";
                modelo = objTblModExc; 
                colChk = INT_TBL_EXC_CHK;
                colRuc = INT_TBL_EXC_RUC;
                colNom = INT_TBL_EXC_NOM_CLI;
            }

            // VALIDAR SELECCIÓN            
            JTable tabla = null;
            
            if (tab == 0) {
                tabla = tblDat;
            } else if (tab == 1) {
                tabla = tblDatSeg;
            } else if (tab == 2) {
                tabla = tblDatCan;
            } else if (tab == 3) {
                tabla = tblDatCli;
            }
            else if (tab == 4) {
                tabla = tblDatExc;
            }
            
            // VALIDAR SOLO FILAS VISIBLES
            int filasVisibles = tabla.getRowCount();

            for (int i = 0; i < filasVisibles; i++) {

                int modelRow = tabla.convertRowIndexToModel(i);

                if ((Boolean) modelo.getValueAt(modelRow, colChk)) {
                    haySeleccion = true;
                    break;
                }
            }

            if (!haySeleccion) {
                mostrarMsgInf("Debe seleccionar al menos un cliente.");
                pgrSis.setIndeterminate(false);
                pgrSis.setValue(0);
                return false;
            }
            
            int totalSeleccionados = 0;           
            
            // Contar SOLO filas visibles            

            for (int i = 0; i < filasVisibles; i++) {

                int modelRow = tabla.convertRowIndexToModel(i);

                boolean chk = (Boolean) modelo.getValueAt(modelRow, colChk);

                if (chk) {
                    totalSeleccionados++;
                }
            }
            
            String msgConfirm;

            if (tab == 4) {
                msgConfirm = "<HTML>¿Desea actualizar <b>" + totalSeleccionados + "</b> clientes desde Excel?</HTML>";
            } else {
                msgConfirm = "<HTML>¿Desea actualizar <FONT COLOR=\"blue\"> " + prcLevel +
                             " </FONT> a <b>" + totalSeleccionados + "</b> clientes <b>" + tipo + "</b>?</HTML>";
            }
            
            int res = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "<HTML>¿Desea actualizar <FONT COLOR=\"blue\"> " + prcLevel +
                    " </FONT> a <b>" + totalSeleccionados + "</b> clientes " + "<b>" + tipo + "</b>?</HTML>",
                    "Confirmación",
                    javax.swing.JOptionPane.YES_NO_OPTION
            );
            

            if (res != javax.swing.JOptionPane.YES_OPTION) {                
                
                pgrSis.setIndeterminate(false);
                pgrSis.setValue(0);
                return false;
            }

             //ACTIVAR BANDERA
            actualizarBandera(1);
            
            
            int cont = 0;           
            
            StringBuilder strDet = new StringBuilder();
            int totalCambios = 0;

            // CABECERA HTML
            strDet.append("<table style='border-collapse:collapse; font-family:Arial; font-size:12px;'>");
            strDet.append("<tr style='background-color:#2c3e50; color:white;'>");
            strDet.append("<th style='padding:6px;'>RUC</th>");
            strDet.append("<th style='padding:6px;'>NOMBRES</th>");
            strDet.append("<th style='padding:6px;'>LISTA ANTERIOR</th>");
            strDet.append("<th style='padding:6px;'>LISTA NUEVA</th>");
            strDet.append("</tr>");
            
            
            for (int i = 0; i < filasVisibles; i++) {
                int modelRow = tabla.convertRowIndexToModel(i);

                if ((Boolean) modelo.getValueAt(modelRow, colChk)) {

                    Object objNom = modelo.getValueAt(modelRow, colNom);
                    String nombre = (objNom == null) ? "" : objNom.toString().trim();
                    String ruc = modelo.getValueAt(modelRow, colRuc).toString().trim();
                
                    
                    //String listaNueva = prcLevel.trim();
                    
                    String listaNueva;

                    if (tab == 4) {
                        Object objLisNue = modelo.getValueAt(modelRow, INT_TBL_EXC_LIS_NUE);
                        listaNueva = (objLisNue == null) ? "" : objLisNue.toString().trim();
                    } else {
                        listaNueva = prcLevel.trim();
                    }
                  
                    //Object objLisAct = modelo.getValueAt(modelRow, COL_LIS_ACT[tab]);
                    
                    Object objLisAct;

                    if (tab == 4) {
                        objLisAct = modelo.getValueAt(modelRow, INT_TBL_EXC_LIS_ACT);
                    } else {
                        objLisAct = modelo.getValueAt(modelRow, COL_LIS_ACT[tab]);
                    }

                    String listaActual = (objLisAct == null) ? "" : objLisAct.toString().trim();
                    
                    // USAR SI QUIEREN QUE SI UN RUC TIENE LISTA ACTUAL Y NUEVA IGUAL NO SE HAGA EL UPDATE DE ESE RUC
                    /*if (tab == 4 && listaActual.equals(listaNueva)) {
                        continue;
                    }*/
                    
                    
                        strDet.append("<tr style='border-bottom:1px solid #ddd;'>");

                        strDet.append("<td style='padding:6px;'>")
                              .append(ruc)
                              .append("</td>");
                        
                        strDet.append("<td style='padding:6px;'>")
                               .append(nombre)
                               .append("</td>");

                        strDet.append("<td style='padding:6px;'>")
                              .append(listaActual)
                              .append("</td>");

                        strDet.append("<td style='padding:6px;'>")
                              .append(listaNueva)
                              .append("</td>");

                        strDet.append("</tr>");

                        totalCambios++;
                    
                    String sql = "UPDATE TUVAL.dbo.RM00101 "
                            + "SET PRCLEVEL = '" + listaNueva + "' "                            
                            + "WHERE CUSTNMBR = '" + ruc + "'; "
                    
                            + "INSERT INTO Dynamics2021..tbh_cliLisPre "
                            + "(co_his, tx_ruc, tx_lisPreAnt, tx_lisPreNue, co_usrmod) "
                            + "SELECT ISNULL(MAX(co_his),0)+1, "
                            + "'" + ruc + "', "
                            + "'" + listaActual + "', "
                            + "'" + listaNueva + "', "
                            + "'" + objParSis.getCodigoUsuario() + "' "
                            + "FROM Dynamics2021..tbh_cliLisPre "
                            + "WHERE tx_ruc = '" + ruc + "' ";                            
                    
                    Statement stm = con.createStatement();
                    stm.executeUpdate(sql);
                    stm.close();
                    
                    String sqlApi = "UPDATE gp_clientes "
                        + "SET CLI_NIVEL_PRECIO = '" + listaNueva + "' "
                        + "WHERE CLI_RUC = '" + ruc + "'";

                    Statement stmApi = conTuvalApi.createStatement();
                    stmApi.executeUpdate(sqlApi);
                    stmApi.close();                   
                    
                    cont++;
                    
                }
            }//fin for

            con.commit();
            conTuvalApi.commit();  
            
            //DESACTIVAR BANDERA
            actualizarBandera(0);
            
            // cerrar tabla
            strDet.append("</table>"); 
            
            // enviar correo
            System.out.println("totalCambios: " + totalCambios);
            if (totalCambios > 0) {
                
                String tx_usr = obtenerNombreUsuario(con, objParSis.getCodigoUsuario());

                String strMsg = "<HTML>"
                        + "<b>Usuario:</b> " + tx_usr + "<br>"
                        //+ "<b>Tipo:</b> " + tipo + "<br>"
                        + "<b>Total clientes modificados:</b> " + totalCambios + "<br><br>"
                        + strDet.toString()
                        + "<br><hr>"
                        + "<span style='font-size:11px; color:#777;'>"
                        + "Este correo ha sido generado automáticamente por el sistema."
                        + "</span>"
                        + "</HTML>";

                String strSbj = "Zafiro: Actualización de Lista de Precios";

                if (objNotCorEle == null) {
                    objNotCorEle = new ZafNotCorEle(objParSis);
                }
                
                //System.out.println("ENTRÓ A ENVÍO DE CORREO");
                
                //System.out.println("EMP: " + objParSis.getCodigoEmpresa());
                //System.out.println("LOC: " + objParSis.getCodigoLocal());
                
                objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(
                        objParSis.getCodigoEmpresa(),
                        objParSis.getCodigoLocal(),
                        40,
                        strMsg,
                        strSbj
                );
            }
            
            con.close();
            conTuvalApi.close();
            
            mostrarMsgInf("Se actualizaron " + cont + " clientes.");
            //System.out.println("Se actualizaron " + cont + " clientes.");

            //RECARGAR SEGÚN PESTAÑA
            if (tab == 0) {
                cargarDetReg();
            } else if (tab == 1) {
                cargarDetRegSeg();
            } else if (tab == 2) {
                cargarDetRegCan();
            }else if (tab == 3) {
                cargarDetRegCli();
            }
            else if (tab == 4) {
                objTblModExc.removeAllRows();
            }
            

        } catch (Exception e) {
            blnRes = false;          
            
            try {
                if (con != null) con.rollback();
                if (conTuvalApi != null) conTuvalApi.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            // APAGAR BANDERA en caso de error
            actualizarBandera(0);
            

            pgrSis.setIndeterminate(false);
            pgrSis.setValue(0);
            lblMsgSis.setText("Error en proceso");
            
            objUti.mostrarMsgErr_F1(this, e);
            
        }

        pgrSis.setIndeterminate(false);
        pgrSis.setValue(0);
        //lblMsgSis.setText("Listo");
        
        return blnRes;
    }

    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_RUC:
                    strMsg = "Ruc";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg = "Nombre del cliente";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FILSEG_LIN:
                    strMsg="";
                    break;
                case INT_TBL_FILSEG_COD:
                    strMsg="Código del Segmento";
                    break;
                case INT_TBL_FILSEG_SUBSEG:
                    strMsg="Nombre del Segmento";
                    break;                
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }

    

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: 
                    vconVen.setCampoBusqueda(1);
                    vconVen.show();
                    if (vconVen.getSelectedButton()==vconVen.INT_BUT_ACE){
                        
                        
                        String cod = vconVen.getValueAt(1);
                        String nom = vconVen.getValueAt(2);

                        //VALIDACIÓN CLAVE
                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un vendedor.");
                            return false;
                        }

                        txtcodven.setText(cod);
                        txtNom.setText(nom);
                        
                        txtcodven.setText(vconVen.getValueAt(1)); // co_usr
                        strTxUsr = vconVen.getValueAt(2);         // tx_usr
                        txtNom.setText(vconVen.getValueAt(3));    // tx_nom
                        
                        tblDat.clearSelection();

                        if (tblDat.isEditing() && tblDat.getCellEditor() != null) {
                            tblDat.getCellEditor().stopCellEditing();
                        }

                        //tblDat.setRowSorter(null);
                        objTblMod.setData(new Vector());
                        
                        //recargar
                        cargarDetReg();
                        
                    }
                    break;
                case 1: //
                    
                    String codIngresado = txtcodven.getText().trim();
                    if (vconVen.buscar("co_usr", txtcodven.getText())){
                        
                        String codEncontrado = vconVen.getValueAt(1);
                        
                        if (!codEncontrado.equals(codIngresado)) {
                            //mostrarMsgInf("No existe ese código exacto.");
                            txtcodven.setText("");
                            txtNom.setText("");
                            return false;
                        }
                         
                        txtcodven.setText(vconVen.getValueAt(1)); // co_usr
                        strTxUsr = vconVen.getValueAt(2);         // tx_usr
                        txtNom.setText(vconVen.getValueAt(3));    // tx_nom
                        
                        tblDat.clearSelection();
                        if (tblDat.isEditing() && tblDat.getCellEditor() != null) {
                            tblDat.getCellEditor().stopCellEditing();
                        }

                        tblDat.setRowSorter(null);
                        objTblMod.setData(new Vector());
                        cargarDetReg();
                    }
                    else{
                        vconVen.setCampoBusqueda(1);                        
                        vconVen.cargarDatos();
                        vconVen.show();
                        if (vconVen.getSelectedButton()==vconVen.INT_BUT_ACE)
                        {
                           
                            txtcodven.setText(vconVen.getValueAt(1)); // co_usr
                            strTxUsr = vconVen.getValueAt(2);         // tx_usr
                            txtNom.setText(vconVen.getValueAt(3));    // tx_nom
                            
                            tblDat.clearSelection();

                            if (tblDat.isEditing() && tblDat.getCellEditor() != null) {
                                tblDat.getCellEditor().stopCellEditing();
                            }

                            tblDat.setRowSorter(null);
                            objTblMod.setData(new Vector());
                            cargarDetReg();
                            
                            
                        }
                        
                    }
                    break;
                case 2: 
                    if (vconVen.buscar("tx_usr", txtNom.getText())){
                        
                        
                        txtcodven.setText(vconVen.getValueAt(1)); // co_usr
                        strTxUsr = vconVen.getValueAt(2);         // tx_usr
                        txtNom.setText(vconVen.getValueAt(3));    // tx_nom
                        
                        tblDat.clearSelection();

                        if (tblDat.isEditing() && tblDat.getCellEditor() != null) {
                             tblDat.getCellEditor().stopCellEditing();
                        }

                        tblDat.setRowSorter(null);
                        objTblMod.setData(new Vector());
                        cargarDetReg();   
                        
                    }
                    else{
                        vconVen.setCampoBusqueda(1);                        
                        vconVen.cargarDatos();
                        vconVen.show();
                        if (vconVen.getSelectedButton()==vconVen.INT_BUT_ACE){                            
                            
                            txtcodven.setText(vconVen.getValueAt(1)); // co_usr
                            strTxUsr = vconVen.getValueAt(2);         // tx_usr
                            txtNom.setText(vconVen.getValueAt(3));    // tx_nom
                            
                            tblDat.clearSelection();

                            if (tblDat.isEditing() && tblDat.getCellEditor() != null) {
                                 tblDat.getCellEditor().stopCellEditing();
                            }

                            tblDat.setRowSorter(null);
                            objTblMod.setData(new Vector());
                            cargarDetReg();
                            
                            
                        }
                        else
                        {
                            //txtNom.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConSeg(int intTipBus) {
        boolean blnRes = true;

        try {
            switch (intTipBus) {

                case 0: // ventana
                    vconSeg.setCampoBusqueda(0);
                    vconSeg.show();

                    if (vconSeg.getSelectedButton() == vconSeg.INT_BUT_ACE) {

                        
                        String cod = vconSeg.getValueAt(1);
                        String nom = vconSeg.getValueAt(2);

                        //VALIDACIÓN CLAVE
                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un segmento.");
                            return false;
                        }

                        txtcodseg.setText(cod);
                        txtNomSeg.setText(nom);
                        
                        txtcodseg.setText(vconSeg.getValueAt(1));
                        txtNomSeg.setText(vconSeg.getValueAt(2));
                        
                        tblDatSeg.clearSelection();
                        if (tblDatSeg.isEditing() && tblDatSeg.getCellEditor() != null) {
                           tblDatSeg.getCellEditor().stopCellEditing();
                        }

                        tblDatSeg.setRowSorter(null);
                        objTblModSeg.setData(new Vector());
                        
                        cargarDetRegSeg();  

                    }
                    break;

                case 1: // búsqueda por código
                    if (vconSeg.buscar("co_atrcli", txtcodseg.getText())) {

                        txtcodseg.setText(vconSeg.getValueAt(1));
                        txtNomSeg.setText(vconSeg.getValueAt(2));
                        
                        tblDatSeg.clearSelection();
                        if (tblDatSeg.isEditing() && tblDatSeg.getCellEditor() != null) {
                           tblDatSeg.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatSeg.setRowSorter(null);
                        objTblModSeg.setData(new Vector());
                        cargarDetRegSeg(); 

                    } else {

                        vconSeg.setCampoBusqueda(0);
                        vconSeg.cargarDatos();
                        vconSeg.show();

                        if (vconSeg.getSelectedButton() == vconSeg.INT_BUT_ACE) {
                            txtcodseg.setText(vconSeg.getValueAt(1));
                            txtNomSeg.setText(vconSeg.getValueAt(2));
                            
                        tblDatSeg.clearSelection();
                        if (tblDatSeg.isEditing() && tblDatSeg.getCellEditor() != null) {
                           tblDatSeg.getCellEditor().stopCellEditing();
                        }

                       
                        tblDatSeg.setRowSorter(null);
                        objTblModSeg.setData(new Vector());
                        cargarDetRegSeg(); 
                        }
                    }
                    break;

                case 2: // búsqueda por nombre
                    if (vconSeg.buscar("tx_nom", txtNomSeg.getText())) {

                        txtcodseg.setText(vconSeg.getValueAt(1));
                        txtNomSeg.setText(vconSeg.getValueAt(2));
                        
                        tblDatSeg.clearSelection();
                        if (tblDatSeg.isEditing() && tblDatSeg.getCellEditor() != null) {
                           tblDatSeg.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatSeg.setRowSorter(null);
                        objTblModSeg.setData(new Vector());
                        cargarDetRegSeg();
                        

                    } else {

                        vconSeg.setCampoBusqueda(1);
                        vconSeg.cargarDatos();
                        vconSeg.show();

                        if (vconSeg.getSelectedButton() == vconSeg.INT_BUT_ACE) {
                            txtcodseg.setText(vconSeg.getValueAt(1));
                            txtNomSeg.setText(vconSeg.getValueAt(2));
                            
                        tblDatSeg.clearSelection();
                        if (tblDatSeg.isEditing() && tblDatSeg.getCellEditor() != null) {
                           tblDatSeg.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatSeg.setRowSorter(null);
                        objTblModSeg.setData(new Vector());
                        cargarDetRegSeg();
                            
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean mostrarVenConCan(int intTipBus) {
        boolean blnRes = true;

        try {
            switch (intTipBus) {

                case 0: // ventana
                    vconCan.setCampoBusqueda(0);
                    vconCan.show();

                    if (vconCan.getSelectedButton() == vconCan.INT_BUT_ACE) {

                        String cod = vconCan.getValueAt(1);
                        String nom = vconCan.getValueAt(2);

                        //VALIDACIÓN CLAVE
                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un canal.");
                            return false;
                        }

                        txtcodcan.setText(cod);
                        txtNomCan.setText(nom);
                        
                        
                        txtcodcan.setText(vconCan.getValueAt(1));
                        txtNomCan.setText(vconCan.getValueAt(2));
                        
                        tblDatCan.clearSelection();
                        if (tblDatCan.isEditing() && tblDatCan.getCellEditor() != null) {
                           tblDatCan.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatCan.setRowSorter(null);
                        objTblModCan.setData(new Vector());
                        cargarDetRegCan();  

                    }
                    break;

                case 1: // búsqueda por código
                    if (vconCan.buscar("co_id", txtcodcan.getText())) {

                        String cod = vconCan.getValueAt(1);

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }
                        
                        txtcodcan.setText(vconCan.getValueAt(1));
                        txtNomCan.setText(vconCan.getValueAt(2));
                        
                        tblDatCan.clearSelection();
                        if (tblDatCan.isEditing() && tblDatCan.getCellEditor() != null) {
                           tblDatCan.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatCan.setRowSorter(null);
                        objTblModCan.setData(new Vector());
                        cargarDetRegCan();

                    } else {

                        vconCan.setCampoBusqueda(0);
                        vconCan.cargarDatos();
                        vconCan.show();

                        if (vconCan.getSelectedButton() == vconCan.INT_BUT_ACE) {
                            txtcodcan.setText(vconCan.getValueAt(1));
                            txtNomCan.setText(vconCan.getValueAt(2));
                            
                            tblDatCan.clearSelection();
                            if (tblDatCan.isEditing() && tblDatCan.getCellEditor() != null) {
                               tblDatCan.getCellEditor().stopCellEditing();
                            }

                            
                            tblDatCan.setRowSorter(null);
                            objTblModCan.setData(new Vector());
                            cargarDetRegCan();
                        }
                    }
                    break;

                case 2: // búsqueda por nombre
                    if (vconCan.buscar("tx_canasi", txtNomCan.getText())) {

                        
                        String cod = vconCan.getValueAt(1);

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }
                        
                        txtcodcan.setText(vconCan.getValueAt(1));
                        txtNomCan.setText(vconCan.getValueAt(2));
                        
                        tblDatCan.clearSelection();
                        if (tblDatCan.isEditing() && tblDatCan.getCellEditor() != null) {
                           tblDatCan.getCellEditor().stopCellEditing();
                        }

                        
                        tblDatCan.setRowSorter(null);
                        objTblModCan.setData(new Vector());
                        cargarDetRegCan();
                        

                    } else {

                        vconCan.setCampoBusqueda(1);
                        vconCan.cargarDatos();
                        vconCan.show();

                        if (vconCan.getSelectedButton() == vconCan.INT_BUT_ACE) {
                            txtcodcan.setText(vconCan.getValueAt(1));
                            txtNomCan.setText(vconCan.getValueAt(2));
                            
                            tblDatCan.clearSelection();
                            if (tblDatCan.isEditing() && tblDatCan.getCellEditor() != null) {
                               tblDatCan.getCellEditor().stopCellEditing();
                            }

                            
                            tblDatCan.setRowSorter(null);
                            objTblModCan.setData(new Vector());
                            cargarDetRegCan();                            
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean mostrarVenConCli(int intTipBus) {
        boolean blnRes = true;

        try {
            switch (intTipBus) {

                case 0: // ventana
                    vconCli.setCampoBusqueda(0);
                    vconCli.show();

                    if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {
                        
                        String cod = vconCli.getValueAt(1);
                        String nom = vconCli.getValueAt(2);

                        //VALIDACIÓN CLAVE
                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }
                        
                        txtcodcli.setText(vconCli.getValueAt(1));
                        txtNomCli.setText(vconCli.getValueAt(2));

                        tblDatCli.clearSelection();
                        if (tblDatCli.isEditing() && tblDatCli.getCellEditor() != null) {
                            tblDatCli.getCellEditor().stopCellEditing();
                        }

                        tblDatCli.setRowSorter(null);
                        objTblModCli.setData(new Vector());
                        cargarDetRegCli();  // 
                    }
                    break;

                case 1: // búsqueda por código
                    
                    String codIngresado = txtcodcli.getText().trim();
                    
                    if (vconCli.buscar("CUSTNMBR", txtcodcli.getText())) {
                        
                        String cod = vconCli.getValueAt(1);
                        String nom = vconCli.getValueAt(2);

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        } 
                        
                        if (!cod.equals(codIngresado)) {                           
                            
                            
                            txtcodcli.setText("");
                            txtNomCli.setText("");
                            
                            objTblModCli.removeAllRows();
                            tblDatCli.setModel(objTblModCli);                           
                            
                            return false;
                        }
                        
                        txtcodcli.setText(vconCli.getValueAt(1));
                        txtNomCli.setText(vconCli.getValueAt(2));

                        tblDatCli.clearSelection();
                        if (tblDatCli.isEditing() && tblDatCli.getCellEditor() != null) {
                            tblDatCli.getCellEditor().stopCellEditing();
                        }

                        tblDatCli.setRowSorter(null);
                        objTblModCli.setData(new Vector());
                        cargarDetRegCli();

                    } else {

                        vconCli.setCampoBusqueda(0);
                        vconCli.cargarDatos();
                        vconCli.show();

                        if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {

                            String cod = vconCli.getValueAt(1);
                            String nom = vconCli.getValueAt(2);

                            if (cod == null || cod.trim().equals("")) {
                                mostrarMsgInf("Debe seleccionar un cliente.");
                                return false;
                            }

                            txtcodcli.setText(cod);
                            txtNomCli.setText(nom);

                            txtcodcli.setText(vconCli.getValueAt(1));
                            txtNomCli.setText(vconCli.getValueAt(2));
                            
                            tblDatCli.clearSelection();
                            if (tblDatCli.isEditing() && tblDatCli.getCellEditor() != null) {
                                tblDatCli.getCellEditor().stopCellEditing();
                            }

                            tblDatCli.setRowSorter(null);
                            objTblModCli.setData(new Vector());
                            cargarDetRegCli();
                        }
                    }
                    break;

                case 2: // búsqueda por nombre
                    if (vconCli.buscar("CUSTNAME", txtNomCli.getText())) {

                        String cod = vconCli.getValueAt(1);
                        String nom = vconCli.getValueAt(2);

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }

                        txtcodcli.setText(cod);
                        txtNomCli.setText(nom);

                        txtcodcli.setText(vconCli.getValueAt(1));
                        txtNomCli.setText(vconCli.getValueAt(2));
                        
                        tblDatCli.clearSelection();
                        if (tblDatCli.isEditing() && tblDatCli.getCellEditor() != null) {
                            tblDatCli.getCellEditor().stopCellEditing();
                        }

                        tblDatCli.setRowSorter(null);
                        objTblModCli.setData(new Vector());
                        cargarDetRegCli();

                    } else {

                        vconCli.setCampoBusqueda(1);
                        vconCli.cargarDatos();
                        vconCli.show();

                        if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {

                            String cod = vconCli.getValueAt(1);
                            String nom = vconCli.getValueAt(2);

                            if (cod == null || cod.trim().equals("")) {
                                mostrarMsgInf("Debe seleccionar un cliente.");
                                return false;
                            }

                            txtcodcli.setText(cod);
                            txtNomCli.setText(nom);

                            txtcodcli.setText(vconCli.getValueAt(1));
                            txtNomCli.setText(vconCli.getValueAt(2));
                            
                            tblDatCli.clearSelection();
                            if (tblDatCli.isEditing() && tblDatCli.getCellEditor() != null) {
                                tblDatCli.getCellEditor().stopCellEditing();
                            }

                            tblDatCli.setRowSorter(null);
                            objTblModCli.setData(new Vector());
                            cargarDetRegCli();
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    


    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConVen() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("co_usr");
            arlCam.add("tx_usr");
            arlCam.add("tx_nom");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("120");
            arlAncCol.add("200");
            
            strSQL = "";
            strSQL += "SELECT u.co_usr, u.tx_usr, u.tx_nom ";
            strSQL += "FROM Dynamics2021.dbo.tbm_usr u ";
            strSQL += "inner join Dynamics2021.dbo.tbm_TipVen as tv on (u.co_tipven = tv.co_tipven) ";
            strSQL += "WHERE u.st_reg = 'A' ";
            
            //strSQL += "ORDER BY tx_usr";
            System.out.println("configurarVenConVen: "+strSQL);
            vconVen = new ZafVenConGP(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado de vendedores",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );
            

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }

    private boolean configurarVenConSeg() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("co_atrcli");
            arlCam.add("tx_nom");
            arlCam.add("segmento");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Subsegmento");
            arlAli.add("Segmento");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("250");
            arlAncCol.add("250");
            
            strSQL = "";
            strSQL += "SELECT h.co_atrcli, h.tx_nom, p.tx_nom AS segmento ";
            strSQL += "FROM Dynamics2021.dbo.tbm_atrcli h ";
            strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli p ";
            strSQL += "ON h.co_pad = p.co_atrcli ";
            strSQL += "WHERE h.co_pad IS NOT NULL ";
            strSQL += "AND h.st_reg = 'A' ";            

            System.out.println("configurarVenConSeg: "+strSQL);
            int intColOcu[] = new int[0];

            vconSeg = new ZafVenConGP(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado de Segmentos",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConCan() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("co_id");
            arlCam.add("tx_canasi");

            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Canal");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("300");

            strSQL = "";
            strSQL += "SELECT co_id, tx_canasi ";
            strSQL += "FROM Dynamics2021.dbo.tbm_canasi ";            

            System.out.println("configurarVenConCan:"+strSQL);
            int intColOcu[] = new int[0];

            vconCan = new ZafVenConGP(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado de Canales",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarVenConCli() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("CUSTNMBR");
            arlCam.add("CUSTNAME");

            ArrayList arlAli = new ArrayList();
            arlAli.add("RUC");
            arlAli.add("Cliente");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("120");
            arlAncCol.add("300");

            strSQL = "";
            strSQL += "SELECT CUSTNMBR, CUSTNAME ";
            strSQL += "FROM TUVAL.dbo.RM00101 ";
            strSQL += "WHERE 1=1 ";
            //strSQL += "ORDER BY CUSTNAME ";

            System.out.println("configurarVenConCli: " + strSQL);

            vconCli = new ZafVenConGP(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado de clientes",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");                
            }
             
            int intTab = intTabSel;
      
        if (intTab == 0) {
            // VENDEDOR
            if (tblDat.getRowCount() > 0) {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
        } 
        else if (intTab == 1) {
            // SEGMENTO
            if (tblDatSeg.getRowCount() > 0) {
                tblDatSeg.setRowSelectionInterval(0, 0);
                tblDatSeg.requestFocus();
            }
        }         
        else if (intTab == 2) {
            // CANAL
            if (tblDatCan.getRowCount() > 0) {
                tblDatCan.setRowSelectionInterval(0, 0);
                tblDatCan.requestFocus();
            }
        }  
        else if (intTab == 3) {
            // CANAL
            if (tblDatCli.getRowCount() > 0) {
                tblDatCli.setRowSelectionInterval(0, 0);
                tblDatCli.requestFocus();
            }
        } 
            objThrGUI=null;
        }
    }


    private boolean cargarReg() {
        boolean blnRes = true;

        if (blnEsGuardar) {
            blnEsGuardar = false;
            return guardar();
        }
        
        int intTab = intTabSel;
        
        try {

            if (intTab == 0) {

                if (!isCamVal()) {                    
                    return false;
                }

                lblMsgSis.setText("Consultando por vendedor...");
                if (!cargarDetReg()) {
                    blnRes = false;
                }

            } else if (intTab == 1) {

                if (!isCamValSeg()) {                    
                    return false;
                }

                lblMsgSis.setText("Consultando por segmento...");
                if (!cargarDetRegSeg()) {
                    blnRes = false;
                }
                
            }else if (intTab == 2) {
                if (!isCamValCan()) {                    
                    return false;
                }

                lblMsgSis.setText("Consultando por canal...");
                if (!cargarDetRegCan()) {
                    blnRes = false;
                }
            }else if (intTab == 3) {
                if (!isCamValCli()) {                    
                    return false;
                }

                lblMsgSis.setText("Consultando por canal...");
                if (!cargarDetRegCli()) {
                    blnRes = false;
                }
            }
            
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }

        return blnRes;
    }
    
    
    
    private void limpiarPestana() {

        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
        butCon.setText("Consultar");

        // QUITAR SORTERS PRIMERO
        tblDat.setRowSorter(null);
        tblDatSeg.setRowSorter(null);
        tblDatCan.setRowSorter(null);
        tblDatCli.setRowSorter(null);
        tblDatExc.setRowSorter(null);

        //LUEGO LIMPIAR
        objTblMod.removeAllRows();
        tblDat.setModel(objTblMod);

        objTblModSeg.removeAllRows();
        tblDatSeg.setModel(objTblModSeg);

        objTblModCan.removeAllRows();
        tblDatCan.setModel(objTblModCan);
        
        objTblModCli.removeAllRows();
        tblDatCli.setModel(objTblModCli);
        
        objTblModExc.removeAllRows();
        tblDatExc.setModel(objTblModExc);

        //LIMPIAR TEXTOS
        txtcodven.setText("");
        txtNom.setText("");

        txtcodseg.setText("");
        txtNomSeg.setText("");

        txtcodcan.setText("");
        txtNomCan.setText("");
        
        txtcodcli.setText("");
        txtNomCli.setText("");
        
        txtFilLisAct.setText("");
        txtFilLisAct.setEnabled(false);
    }
    
    
    private String obtenerNombreUsuario(Connection con, int coUsr) {

        String usuario = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT tx_usr FROM tbm_usr WHERE co_usr = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, coUsr);

            rs = ps.executeQuery();

            if (rs.next()) {
                usuario = rs.getString("tx_usr");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return usuario;
    }
    
    
    
    private boolean cargarArchivoXLS()
    {
        boolean blnRes=true;
        boolean blnCarGrp=false;
        String strAux;
       
        try
        {  
            if (getArchivoExcel() == true)
            {
                if(validarDatosCargados()){
                    if(ingresarDatosCargadosTbl())
                        mostrarMsgInf("La carga del archivo fue exitosa.");
                        else
                        {
                          blnRes=false;
                          mostrarMsgInf("No se puedo guardar los datos..."); 
                        }
                }
                else
                {
                    
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
    
    private boolean getArchivoExcel() {
        boolean blnRes = true;

        try {
            blnCargarArchivo = false;
            arlDatExcel = new ArrayList();

            JFileChooser objfileChooser = new JFileChooser();
            objfileChooser.setDialogTitle("Seleccionar archivo Excel (XLS)");
            objfileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xls"));

            int userSelection = objfileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {

                blnNoSeleccion = false;

                File selectedFile = objfileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                Workbook workbook = Workbook.getWorkbook(new File(filePath));
                Sheet sheet = workbook.getSheet(0);

                int numRows = sheet.getRows();

                //VALIDAR ENCABEZADOS
                String col0 = sheet.getCell(0, 0).getContents().trim().toUpperCase();
                String col1 = sheet.getCell(1, 0).getContents().trim().toUpperCase();

                if (!col0.equals("RUC") || !col1.equals("LISTA")) {
                    mostrarMsgInf("El archivo debe tener exactamente estas columnas:\nRUC | LISTA");
                    workbook.close();
                    return false;
                }

                // LEER DATOS
                for (int i = 1; i < numRows; i++) {

                    String ruc = sheet.getCell(0, i).getContents().trim();
                    String lista = sheet.getCell(1, i).getContents().trim();

                    // Validar vacíos
                    if (ruc.isEmpty() || lista.isEmpty()) {
                        mostrarMsgInf("Error en fila " + (i + 1) + ". Existen celdas vacías.");
                        workbook.close();
                        return false;
                    }

                    // Guardar en estructura
                    ArrayList fila = new ArrayList();
                    fila.add(ruc);
                    fila.add(lista);

                    arlDatExcel.add(fila);
                }

                workbook.close();
                blnCargarArchivo = true;

            } else {
                blnRes = false;
                mostrarMsgInf("No se seleccionó ningún archivo...");
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean validarDatosCargados() {
        boolean blnRes = true;

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            if (arlDatExcel == null || arlDatExcel.isEmpty()) {
                mostrarMsgInf("No hay datos cargados.");
                return false;
            }

            // 🔹 1. RUC del Excel (sin duplicados)
            java.util.HashSet<String> setRucExcel = new java.util.HashSet<>();

            for (int i = 0; i < arlDatExcel.size(); i++) {
                ArrayList fila = (ArrayList) arlDatExcel.get(i);
                String ruc = fila.get(0).toString().trim();

                if (ruc.isEmpty()) {
                    mostrarMsgInf("Existe un RUC vacío en el archivo.");
                    return false;
                }

                setRucExcel.add(ruc);
            }

            // 🔹 2. Armar IN para RUC
            StringBuilder strIn = new StringBuilder();
            int i = 0;
            for (String ruc : setRucExcel) {
                if (i > 0) strIn.append(",");
                strIn.append("'").append(ruc).append("'");
                i++;
            }

            // 🔹 3. Conexión
            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            stm = con.createStatement();

            // 🔹 4. Validar RUC en BD
            String sqlRuc = "SELECT CUSTNMBR "
                          + "FROM TUVAL.dbo.RM00101 "
                          + "WHERE CUSTNMBR IN (" + strIn.toString() + ")";

            rs = stm.executeQuery(sqlRuc);

            java.util.HashSet<String> setRucBD = new java.util.HashSet<>();

            while (rs.next()) {
                setRucBD.add(rs.getString("CUSTNMBR").trim());
            }

            rs.close();

            // 🔹 5. Validar RUC inexistentes
            StringBuilder erroresRuc = new StringBuilder();
            int numRuc = 1;

            for (String ruc : setRucExcel) {
                if (!setRucBD.contains(ruc)) {
                    erroresRuc.append(numRuc).append(". ").append(ruc).append("\n");
                    numRuc++;
                }
            }

            // 🔹 6. Obtener LISTAS válidas
            String sqlLis = "SELECT PRCLEVEL FROM TUVAL.dbo.IV40800";
            rs = stm.executeQuery(sqlLis);

            java.util.HashSet<String> setListasBD = new java.util.HashSet<>();

            while (rs.next()) {
                setListasBD.add(rs.getString("PRCLEVEL").trim().toUpperCase());
            }

            rs.close();

            // 🔹 7. Validar LISTAS del Excel
            StringBuilder erroresLis = new StringBuilder();
            int numLis = 1;

            for (int j = 0; j < arlDatExcel.size(); j++) {
                ArrayList fila = (ArrayList) arlDatExcel.get(j);

                String lista = fila.get(1).toString().trim().toUpperCase();
                System.out.println(lista);

                if (!setListasBD.contains(lista)) {
                    erroresLis.append(numLis).append(". ").append(lista).append("\n");
                    numLis++;
                }
            }

            // 🔹 8. Mostrar errores combinados
            if (erroresRuc.length() > 0 || erroresLis.length() > 0) {

                StringBuilder msg = new StringBuilder("Se encontraron errores en el archivo:\n\n");

                if (erroresRuc.length() > 0) {
                    msg.append("RUC no existentes:\n").append(erroresRuc).append("\n");
                    //objTblModExc.removeAllRows();
                }

                if (erroresLis.length() > 0) {
                    msg.append("Listas no válidas:\n").append(erroresLis);
                    //objTblModExc.removeAllRows();
                    
                }
                objTblModExc.removeAllRows();
                mostrarMsgInf(msg.toString());
                return false;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return blnRes;
    }
    
    
    
    
    private boolean ingresarDatosCargadosTbl() {
        boolean blnRes = true;

        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            // 🔹 1. Limpiar tabla
            objTblModExc.removeAllRows();

            if (arlDatExcel == null || arlDatExcel.isEmpty()) {
                mostrarMsgInf("No hay datos para cargar.");
                return false;
            }

            // 🔹 2. Armar lista de RUCs (sin duplicados)
            java.util.HashSet<String> setRucExcel = new java.util.HashSet<>();

            for (int i = 0; i < arlDatExcel.size(); i++) {
                ArrayList fila = (ArrayList) arlDatExcel.get(i);
                String ruc = fila.get(0).toString().trim();
                setRucExcel.add(ruc);
            }

            // 🔹 3. Armar IN (...)
            StringBuilder strIn = new StringBuilder();
            int i = 0;
            for (String ruc : setRucExcel) {
                if (i > 0) strIn.append(",");
                strIn.append("'").append(ruc).append("'");
                i++;
            }

            // 🔹 4. Conexión
            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021()
            );

            stm = con.createStatement();

            // 🔹 5. Traer nombres
            String sql = "SELECT CUSTNMBR, CUSTNAME, PRCLEVEL "
                       + "FROM TUVAL.dbo.RM00101 "
                       + "WHERE CUSTNMBR IN (" + strIn.toString() + ")";

            rs = stm.executeQuery(sql);

            // 🔹 6. Guardar en Map
            java.util.HashMap<String, String[]> mapClientes = new java.util.HashMap<>();

            while (rs.next()) {
                mapClientes.put(
                    rs.getString("CUSTNMBR").trim(),
                    new String[]{
                        rs.getString("CUSTNAME").trim(),
                        rs.getString("PRCLEVEL").trim()
                    }
                );
            }

            // 🔹 7. Llenar JTable
            int lin = 0;

            for (int j = 0; j < arlDatExcel.size(); j++) {

                ArrayList fila = (ArrayList) arlDatExcel.get(j);

                String ruc = fila.get(0).toString().trim();
                String listaNueva = fila.get(1).toString().trim().toUpperCase();

                String[] datos = mapClientes.get(ruc);

                String nombre = datos[0];
                String listaActual = datos[1];

                if (nombre == null) {
                    nombre = "NO EXISTE";
                }

                objTblModExc.insertRow(lin);

                objTblModExc.setValueAt(lin + 1, lin, INT_TBL_EXC_LIN);
                objTblModExc.setValueAt(true, lin, INT_TBL_EXC_CHK);
                objTblModExc.setValueAt(ruc, lin, INT_TBL_EXC_RUC);
                objTblModExc.setValueAt(nombre, lin, INT_TBL_EXC_NOM_CLI);
                objTblModExc.setValueAt(listaActual, lin, INT_TBL_EXC_LIS_ACT);
                objTblModExc.setValueAt(listaNueva, lin, INT_TBL_EXC_LIS_NUE);

                lin++;
            }

            System.out.println("Carga Excel -> Tabla OK");

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stm != null) stm.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return blnRes;
    }
    
       
    private void filtrarListaActual() {

        aplicarFiltros();

    }
    
    
    
    private void aplicarFiltros() {

        TableRowSorter sorter = (TableRowSorter) tblDatCan.getRowSorter();

        if (sorter == null) {
            sorter = new TableRowSorter(objTblModCan);
            tblDatCan.setRowSorter(sorter);
        }

        java.util.List<RowFilter<Object,Object>> filtros =
            new java.util.ArrayList<RowFilter<Object,Object>>();

//        // =====================================
//        // FILTRO LISTA ACTUAL CON TEXTFIELD
//        // =====================================
//        String textoLista = txtFilLisAct.getText().trim();
//
//        if (!textoLista.equals("")) {
//
//            filtros.add(
//                RowFilter.regexFilter(
//                    "(?i).*" + textoLista + ".*",
//                    INT_TBL_CAN_LIS_ACT
//                )
//            );
//
//        }

        // =====================================
        // FILTRO LISTA ACTUAL
        // =====================================
        String lista = "";

        for (int i = 0; i < tblLis.getRowCount(); i++) {

            boolean chk = Boolean.parseBoolean(
                tblLis.getValueAt(i, INT_TBL_FILIS_CHK).toString()
            );

            if (chk) {

                lista = tblLis.getValueAt(i, INT_TBL_FILIS_LIS).toString().trim();
                break;

            }

        }

        if (!lista.equals("")) {

            filtros.add(
                RowFilter.regexFilter(
                    "(?i).*" + lista + ".*",
                    INT_TBL_CAN_LIS_ACT
                )
            );

        }
        

        // =====================================
        // FILTRO SEGMENTO
        // =====================================
        String segmento = "";

        for (int i = 0; i < tblLoc.getRowCount(); i++) {

            boolean chk = Boolean.parseBoolean(
                tblLoc.getValueAt(i, INT_TBL_FILSEG_CHK).toString()
            );

            if (chk) {

                segmento = tblLoc.getValueAt(i, 3).toString().trim();
                break;

            }

        }

        if (!segmento.equals("")) {

            filtros.add(
                RowFilter.regexFilter(
                    "(?i).*" + segmento + ".*",
                    INT_TBL_CAN_SUBSEG
                )
            );

        }

        // =====================================
        // APLICAR TODOS LOS FILTROS
        // =====================================
        if (filtros.isEmpty()) {

            sorter.setRowFilter(null);

        } else {

            sorter.setRowFilter(
                RowFilter.andFilter(filtros)
            );

        }

    }
    
    
    private void actualizarBandera(int estado) {
        String sql = "UPDATE Dynamics2021.dbo.tbm_ctl_pro " +
                     "SET st_eje = " + estado + " WHERE co_pro = 'LIS_PRE'";

        try {
            Connection conFlag = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
            );

            Statement stm = conFlag.createStatement();
            stm.executeUpdate(sql);
            stm.close();
            conFlag.close();

            //System.out.println("Bandera actualizada a: " + estado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private boolean cargarLoc()
    {
        
        boolean blnRes=true;
        try
        {
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT h.co_atrcli, h.tx_nom, p.tx_nom AS segmento ";
                strSQL += "FROM Dynamics2021.dbo.tbm_atrcli h ";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli p ";
                strSQL += "ON h.co_pad = p.co_atrcli ";
                strSQL += "WHERE h.co_pad IS NOT NULL ";
                strSQL += "AND h.st_reg = 'A' ";   
                
                rst = stm.executeQuery(strSQL);
                
                System.out.println("FiltroSegmento: "+strSQL);
                //Limpiar vector de datos.
                vecDatFilSeg.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecRegFilSeg=new Vector();
                    vecRegFilSeg.add(INT_TBL_FILSEG_LIN,"");
                    vecRegFilSeg.add(INT_TBL_FILSEG_CHK,Boolean.FALSE);
                    vecRegFilSeg.add(INT_TBL_FILSEG_COD,rst.getString("co_atrcli"));
                    vecRegFilSeg.add(INT_TBL_FILSEG_SUBSEG,rst.getString("tx_nom"));                    
                    vecDatFilSeg.add(vecRegFilSeg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModFilSeg.setData(vecDatFilSeg);
                tblLoc.setModel(objTblModFilSeg);
                vecDatFilSeg.clear();
                blnMarTodChkTblEmp=false;
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
    
    
    private boolean cargarList()
    {
        
        boolean blnRes=true;
        try
        {
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                String strSQL = "";
                strSQL += "SELECT PRCLEVEL, DSCRIPTN ";
                strSQL += "FROM TUVAL.dbo.IV40800 ";
                strSQL += "ORDER BY PRCLEVEL"; 
                
                rst = stm.executeQuery(strSQL);
                
                System.out.println("Filtro Lista: "+strSQL);
                //Limpiar vector de datos.
                vecDatFilLis.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecRegFilLis=new Vector();
                    vecRegFilLis.add(INT_TBL_FILIS_LIN,"");
                    vecRegFilLis.add(INT_TBL_FILIS_CHK,Boolean.FALSE);
                    vecRegFilLis.add(INT_TBL_FILIS_LIS,rst.getString("PRCLEVEL"));                                        
                    vecDatFilLis.add(vecRegFilLis);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModFilLis.setData(vecDatFilLis);
                tblLis.setModel(objTblModFilLis);
                vecDatFilLis.clear();
                blnMarTodChkTblEmp=false;
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
    
    
    private void configurarEventosFiltroSegmento(){
        objTblModFilSeg.addTableModelListener(
                    new javax.swing.event.TableModelListener() {

                    public void tableChanged(javax.swing.event.TableModelEvent e) {

                        if(blnActChkSeg)
                            return;

                        int fila = e.getFirstRow();
                        int col = e.getColumn();

                        if(col == INT_TBL_FILSEG_CHK){

                            blnActChkSeg = true;

                            boolean blnMarcado = Boolean.parseBoolean(
                                tblLoc.getValueAt(fila, INT_TBL_FILSEG_CHK).toString()
                            );

                            // =====================================
                            // DESMARCAR TODOS MENOS EL ACTUAL
                            // =====================================
                            for(int i=0; i<tblLoc.getRowCount(); i++){

                                if(i != fila){

                                    tblLoc.setValueAt(false, i, INT_TBL_FILSEG_CHK);

                                }

                            }

                            blnActChkSeg = false;

                            // =====================================
                            // FILTRAR
                            // =====================================
                            aplicarFiltros();
                        }

                    }

                }
            );
    }
    
    
    private void configurarEventosFiltroLista(){

        objTblModFilLis.addTableModelListener(
            new javax.swing.event.TableModelListener() {

                public void tableChanged(javax.swing.event.TableModelEvent e) {

                    if(blnActChkList)
                        return;

                    int fila = e.getFirstRow();
                    int col = e.getColumn();

                    if(col == INT_TBL_FILIS_CHK){

                        blnActChkList = true;

                        boolean blnMarcado = Boolean.parseBoolean(
                            tblLis.getValueAt(fila, INT_TBL_FILIS_CHK).toString()
                        );

                        // =====================================
                        // DESMARCAR TODOS MENOS EL ACTUAL
                        // =====================================
                        for(int i=0; i<tblLis.getRowCount(); i++){

                            if(i != fila){

                                tblLis.setValueAt(false, i, INT_TBL_FILIS_CHK);

                            }

                        }

                        blnActChkList = false;

                        // =====================================
                        // FILTRAR
                        // =====================================
                        aplicarFiltros();
                    }

                }

            }
        );

    }
}