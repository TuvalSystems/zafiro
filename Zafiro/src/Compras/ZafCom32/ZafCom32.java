/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom32.java
 *
 * Created on Aug 24, 2009, 11:34:14 AM
 */
package Compras.ZafCom32;    

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.math.BigInteger;
import javax.swing.SpinnerNumberModel; 
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JFileChooser;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 *
 * @author ilino
 */
public class ZafCom32 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private ZafUtil objUti;


    private String strCodBodDes,  strNomBodDes;
    private ZafVenConGP vcoBodDes;
    private String strSQL, strAux;
    private final int INT_TBL_DAT_HIS_LIN=0;
    private final int INT_TBL_DAT_HIS_CHK=1;
    private final int INT_TBL_DAT_HIS_ANI=2;
    private final int INT_TBL_DAT_HIS_NUM_MES=3;
    private final int INT_TBL_DAT_HIS_MES=4;
    private Vector vecCabHis, vecRegHis, vecDatHis, vecAux;
    private Vector vecCab, vecReg, vecDat;
    private ZafTblMod objTblModHis, objTblMod;
    private ZafColNumerada objColNumHis, objColNum;
    private ZafTblFilCab objTblFilCabHis, objTblFilCab;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenChk objTblCelRenChkHis;
    private ZafTblCelEdiChk objTblCelEdiChkHis;
    private ZafDocLis objDocLis;
    private ZafVenConGP vcoItm;

    private boolean blnHayCam;
    private Connection con;
    private Statement stm;
    private ResultSet rst;

    private String strCodAlt, strNomItm;

    //PARA COLUMNAS ESTATICAS DE LA IZQUIERDA
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_ITM=1;
    private final int INT_TBL_DAT_COD_ALT_ITM=2;
    private final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;
    private final int INT_TBL_DAT_NOM_ITM=4;
    private final int INT_TBL_DAT_UNI_MED=5;
    private final int INT_TBL_DAT_STK_ACT_CEN_DIS=6;
    private final int INT_TBL_DAT_DIS_CEN_DIS=7;
    private final int INT_TBL_DAT_SALD=8;    
    
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;
    private ArrayList arlRegAniAdd, arlDatAniAdd;
    private final int INT_ARL_ANI=0;
    private final int INT_ARL_NUM_MES=1;
    private final int INT_ARL_MES=2;
    private final int INT_ARL_COL=3;
    private boolean blnCon;
    private ZafThreadGUI objThrGUI;
    //private int intNumColAdi=0;


    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenLbl objTblCelRenLblGP;
    private String strDesCorCla, strDesLarCla;
    private ZafVenCon vcoCla;

    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiBut;

    private ZafCom32_01 objCom32_01;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafPerUsr objPerUsr;

    private int intNumColFinModEst;//la ultima columna del modelo estatico
    private int intNumColAdiFec, intNumColIniFec, intNumColFinFec;//columnas a adicionar del filtro seleccionado por mes /anio
    private int intNumColAdiTot, intNumColIniTot, intNumColFinTot;//columnas a adicionar para totales(obtenidas por filtro de mes/anio)
    private int intNumColAdiStkAct, intNumColIniStkAct, intNumColFinStkAct;//columna del stock actual
    private int intNumColAdiProMen, intNumColIniProMen, intNumColFinProMen, intColPromMan;//columna de stock minimo
    private int intNumColAdiStkMinExc, intNumColIniStkMinExc, intNumColFinStkMinExc;//columna de stock minimo
    private int intNumColAdiObs, intNumColIniObs, intNumColFinStkObs;//columna de observacion
    private int intNumColAdiRep, intNumColIniStkRep, intNumColFinStkRep;//columna de reposicion
    private int intNumColAdiCanPenRep, intNumColIniCanPenRep, intNumColFinCanPenRep;//columna de reposicion
    
    private int intNumColAddMarFac, intNumColIniMarFac, intNumColFinMarFac;    
    private ZafTblMod objTblModTot, objTblModTotFix;
    private ZafTblFilCab objTblFilCabTot, objTblFilCabTotFix;    
    private ZafTblCelRenLbl objTblCelRenLblTot, objTblCelRenLblTotFix;    

    private ZafTblCelEdiTxt objTblCelEdiTxtPro,objTblCelEdiTxtMin;
    private JScrollBar barDat1, barFix1, barDatVer1, barDatTot1, barDat2, barFix2, barFixVer2, barDatTotFix2, barDat3, barFix3, barFixVer3, barDatTotFix3, barDatTot3;
    
    private final int INT_COD_BOD_CEN_DIS=15;
    private ZafVenConItmGP01 objVenConItm01;
    
    private ZafTblHeaColGrp objTblHeaColGrp=null;  
    private boolean validaMS=false,validaMV=false,validaMM=false;
    private String version="v0.52";
    
    private Vector vecCabFix, vecRegFix, vecDatFix;   
    private ZafTblMod objTblModFix;    
    private ZafTblPopMnu objTblPopMnuFix;    
    private ZafTblBus objTblBusFix;     
    private ZafTblCelRenLbl objTblCelRenLblColFix, objTblCelRenLblFixStr;  
    private ZafTblCelRenChk objTblCelRenChkEstEliItm, objTblCelRenChkEstItmStkRep, objTblCelRenChkFixEstItmStkRep;    
    private ZafTblCelEdiChk objTblCelEdiChkEstEliItm, objTblCelEdiChkEstItmStkRep, objTblCelEdiChkFixEstItmStkRep;    
    private ZafMouMotAda objMouMotAda, objMouMotAdaFix;                          //ToolTipText en TableHeader.
    //JTable TblFix: Tabla Fija
    private final int INT_TBL_FIX_LIN=0;
    private final int INT_TBL_FIX_COD_MAE=1;//ITEMS QUE NO SE PUEDEN QUEDAR SIN STOCK
    private final int INT_TBL_FIX_COD_SIS=2;
    private final int INT_TBL_FIX_COD_ALT=3;
    private final int INT_TBL_FIX_COD_ALT_DOS=4;
    private final int INT_TBL_FIX_COD_ALT_3=5;
    private final int INT_TBL_FIX_NOM_ITM=6;
    private final int INT_TBL_FIX_UNI_MED=7;    
    private final int INT_TBL_FIX_DIS_CEN_DIS=8;
    
    
    private ZafTblFilCab  objTblFilCabFix;    
    private JScrollBar barDatVer, barFixVer, barDatHor, barFixHor;    
    
    private static int INT_ARL_EXC_ITEM=0; 
    private static int INT_ARL_EXC_MAX=2;
    private static int INT_ARL_EXC_MIN=1;
    //private static int INT_ARL_EXC_PROM=3;    
    private java.util.Date datFecAux;    


////    private final int INT_TBL_DAT_FIX_UNI_MED=6;
////    private final int INT_TBL_DAT_FIX_PES_KG=7;
////    private final int INT_TBL_DAT_FIX_COS_UNI_GRP=8;
////    private final int INT_TBL_DAT_FIX_EST_ELI_ITM=9;    
    
    

    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom32(ZafParSis obj) {
        try {
            objParSis = (ZafParSis) obj.clone();
            vecDatHis=new Vector();
            arlDatAniAdd=new ArrayList();
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
             


        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
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
                strSQL="";
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+objParSis.getCodigoMenu();
                rstLoc = stmLoc.executeQuery(strSQL);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCon = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panConFil = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        panParRep = new javax.swing.JPanel();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        panCanItmRep = new javax.swing.JPanel();
        panOptHis = new javax.swing.JPanel();
        chkHisVta = new javax.swing.JCheckBox();
        optHisMen = new javax.swing.JRadioButton();
        optHisAnu = new javax.swing.JRadioButton();
        panFilHis = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHis = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panFacMin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtValMin = new javax.swing.JTextField();
        txtValMax = new javax.swing.JTextField();
        panItm = new javax.swing.JPanel();
        panFilItm = new javax.swing.JPanel();
        optTodReg = new javax.swing.JRadioButton();
        optFilReg = new javax.swing.JRadioButton();
        lblNumMesRep = new javax.swing.JLabel();
        jspNumMesRep = new javax.swing.JSpinner();
        panItmDesHas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        chkStkMinSug = new javax.swing.JCheckBox();
        chkHidColFec = new javax.swing.JCheckBox();
        lblCla = new javax.swing.JLabel();
        txtCodCla = new javax.swing.JTextField();
        txtDesCorCla = new javax.swing.JTextField();
        txtDesLarCla = new javax.swing.JTextField();
        butCla = new javax.swing.JButton();
        chkSalInv = new javax.swing.JCheckBox();
        chkVec = new javax.swing.JCheckBox();
        chkMes = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnRpt = new javax.swing.JSplitPane();
        panRptFix = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotFix = new javax.swing.JScrollPane();
        tblTotFix = new javax.swing.JTable();
        panRptDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panTotDat = new javax.swing.JPanel();
        spnTotDat = new javax.swing.JScrollPane();
        tblTotDat = new javax.swing.JTable();
        panAuxSpnTot = new javax.swing.JPanel();
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        panBotAux = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCalRep = new javax.swing.JButton();
        butCar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        PanPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panCon.setLayout(new java.awt.BorderLayout());

        panConFil.setLayout(new java.awt.BorderLayout());

        panRep.setPreferredSize(new java.awt.Dimension(710, 210));
        panRep.setLayout(new java.awt.BorderLayout());

        panParRep.setPreferredSize(new java.awt.Dimension(344, 24));
        panParRep.setLayout(null);

        lblBodDes.setText("Bodega:");
        lblBodDes.setToolTipText("Bodega en la que se debe hacer el conteo");
        panParRep.add(lblBodDes);
        lblBodDes.setBounds(8, 6, 70, 14);

        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        panParRep.add(txtCodBodDes);
        txtCodBodDes.setBounds(70, 2, 90, 20);

        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        panParRep.add(txtNomBodDes);
        txtNomBodDes.setBounds(160, 2, 270, 20);

        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panParRep.add(butBodDes);
        butBodDes.setBounds(430, 2, 20, 20);

        panRep.add(panParRep, java.awt.BorderLayout.NORTH);

        panCanItmRep.setPreferredSize(new java.awt.Dimension(350, 100));
        panCanItmRep.setLayout(new java.awt.BorderLayout());

        panOptHis.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis.setLayout(null);

        chkHisVta.setSelected(true);
        chkHisVta.setText("Histórico de ventas");
        chkHisVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaActionPerformed(evt);
            }
        });
        panOptHis.add(chkHisVta);
        chkHisVta.setBounds(3, 0, 180, 14);

        optHisMen.setText("Histórico mensual");
        optHisMen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMenActionPerformed(evt);
            }
        });
        panOptHis.add(optHisMen);
        optHisMen.setBounds(20, 30, 180, 14);

        optHisAnu.setSelected(true);
        optHisAnu.setText("Histórico anual");
        optHisAnu.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnuActionPerformed(evt);
            }
        });
        panOptHis.add(optHisAnu);
        optHisAnu.setBounds(20, 16, 180, 14);

        panCanItmRep.add(panOptHis, java.awt.BorderLayout.NORTH);

        panFilHis.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblHis);

        panFilHis.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panCanItmRep.add(panFilHis, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(null);

        panFacMin.setBorder(javax.swing.BorderFactory.createTitledBorder("Factores"));
        panFacMin.setLayout(null);

        jLabel1.setText("Mínimo:");
        panFacMin.add(jLabel1);
        jLabel1.setBounds(20, 22, 60, 14);

        jLabel2.setText("Máximo:");
        panFacMin.add(jLabel2);
        jLabel2.setBounds(20, 42, 60, 14);
        panFacMin.add(txtValMin);
        txtValMin.setBounds(70, 18, 80, 20);
        panFacMin.add(txtValMax);
        txtValMax.setBounds(70, 40, 80, 20);

        jPanel1.add(panFacMin);
        panFacMin.setBounds(100, 10, 190, 80);

        panRep.add(jPanel1, java.awt.BorderLayout.CENTER);

        panConFil.add(panRep, java.awt.BorderLayout.CENTER);

        panItm.setPreferredSize(new java.awt.Dimension(100, 160));
        panItm.setLayout(new java.awt.BorderLayout());

        panFilItm.setPreferredSize(new java.awt.Dimension(420, 100));
        panFilItm.setLayout(null);

        optTodReg.setSelected(true);
        optTodReg.setText("Todos los items");
        optTodReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodRegItemStateChanged(evt);
            }
        });
        panFilItm.add(optTodReg);
        optTodReg.setBounds(8, 26, 410, 14);

        optFilReg.setText("Sólo los items que cumplan el criterio seleccionado");
        panFilItm.add(optFilReg);
        optFilReg.setBounds(8, 42, 410, 14);

        lblNumMesRep.setText("Número de meses a reponer:");
        panFilItm.add(lblNumMesRep);
        lblNumMesRep.setBounds(10, 8, 180, 14);

        jspNumMesRep.setToolTipText("Para expresar decimales usar \",\" no \".\"");
        panFilItm.add(jspNumMesRep);
        jspNumMesRep.setBounds(200, 4, 80, 20);

        panItm.add(panFilItm, java.awt.BorderLayout.NORTH);

        panItmDesHas.setPreferredSize(new java.awt.Dimension(100, 44));
        panItmDesHas.setRequestFocusEnabled(false);
        panItmDesHas.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 60));
        jPanel3.setLayout(null);

        chkStkMinSug.setText("Mostrar items con stock mínimo sugerido");
        jPanel3.add(chkStkMinSug);
        chkStkMinSug.setBounds(3, 28, 270, 14);

        chkHidColFec.setText("Ocultar columnas del Histórico de ventas");
        jPanel3.add(chkHidColFec);
        chkHidColFec.setBounds(3, 42, 270, 14);

        lblCla.setText("Clasificación:");
        lblCla.setToolTipText("Bodega en la que se debe hacer el conteo");
        jPanel3.add(lblCla);
        lblCla.setBounds(10, 10, 80, 14);
        jPanel3.add(txtCodCla);
        txtCodCla.setBounds(50, 4, 40, 20);

        txtDesCorCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorClaFocusLost(evt);
            }
        });
        txtDesCorCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorClaActionPerformed(evt);
            }
        });
        jPanel3.add(txtDesCorCla);
        txtDesCorCla.setBounds(90, 4, 80, 20);

        txtDesLarCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarClaFocusLost(evt);
            }
        });
        txtDesLarCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarClaActionPerformed(evt);
            }
        });
        jPanel3.add(txtDesLarCla);
        txtDesLarCla.setBounds(170, 4, 260, 20);

        butCla.setText("...");
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        jPanel3.add(butCla);
        butCla.setBounds(430, 4, 20, 20);

        chkSalInv.setSelected(true);
        chkSalInv.setText("Mostrar saldos de inventario");
        chkSalInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSalInvActionPerformed(evt);
            }
        });
        jPanel3.add(chkSalInv);
        chkSalInv.setBounds(278, 42, 220, 14);

        chkVec.setText("Mostrar columnas de veces");
        chkVec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVecActionPerformed(evt);
            }
        });
        jPanel3.add(chkVec);
        chkVec.setBounds(278, 28, 190, 14);

        chkMes.setText("Mostrar columnas de meses");
        chkMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMesActionPerformed(evt);
            }
        });
        jPanel3.add(chkMes);
        chkMes.setBounds(470, 28, 190, 14);

        panItmDesHas.add(jPanel3, java.awt.BorderLayout.CENTER);

        panItm.add(panItmDesHas, java.awt.BorderLayout.CENTER);

        panConFil.add(panItm, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panConFil);

        panRpt.setLayout(new java.awt.GridLayout(1, 2));

        spnRpt.setDividerLocation(380);
        spnRpt.setDividerSize(2);

        panRptFix.setAutoscrolls(true);
        panRptFix.setPreferredSize(new java.awt.Dimension(600, 444));
        panRptFix.setLayout(new java.awt.BorderLayout());

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
        tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblFixFocusGained(evt);
            }
        });
        spnFix.setViewportView(tblFix);

        panRptFix.add(spnFix, java.awt.BorderLayout.CENTER);

        spnTotFix.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTotFix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotFix.setViewportView(tblTotFix);

        panRptFix.add(spnTotFix, java.awt.BorderLayout.SOUTH);

        spnRpt.setLeftComponent(panRptFix);

        panRptDat.setPreferredSize(new java.awt.Dimension(400, 444));
        panRptDat.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(900, 402));

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
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRptDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panTotDat.setLayout(new java.awt.BorderLayout());

        spnTotDat.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTotDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotDat.setViewportView(tblTotDat);

        panTotDat.add(spnTotDat, java.awt.BorderLayout.CENTER);

        panAuxSpnTot.setPreferredSize(new java.awt.Dimension(12, 10));
        panTotDat.add(panAuxSpnTot, java.awt.BorderLayout.EAST);

        panRptDat.add(panTotDat, java.awt.BorderLayout.SOUTH);

        spnRpt.setRightComponent(panRptDat);

        panRpt.add(spnRpt);

        tabFrm.addTab("Reporte", panRpt);

        panCon.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPie.setPreferredSize(new java.awt.Dimension(685, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setText("DynamicsGP");
        panBotAux.add(jLabel3);

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBotAux.add(butCon);

        butGua.setText("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBotAux.add(butGua);

        butCalRep.setText("Calcular reposición");
        butCalRep.setToolTipText("Cambia el número de meses a reponer");
        butCalRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCalRepActionPerformed(evt);
            }
        });
        panBotAux.add(butCalRep);

        butCar.setText("Cargar Archivo");
        butCar.setToolTipText("");
        butCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarActionPerformed(evt);
            }
        });
        panBotAux.add(butCar);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBotAux.add(butCer);

        panBot.add(panBotAux, java.awt.BorderLayout.EAST);

        panPie.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 22));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        PanPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        PanPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        PanPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        PanPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        PanPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(PanPrgSis, java.awt.BorderLayout.EAST);

        panPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panCon.add(panPie, java.awt.BorderLayout.SOUTH);

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panCon.add(lblTit, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panCon);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        // TODO add your handling code here:
        txtCodBodDes.transferFocus();
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
        // TODO add your handling code here:
        strCodBodDes = txtCodBodDes.getText();
        txtCodBodDes.selectAll();
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)) {
            if (txtCodBodDes.getText().equals("")) {
                txtCodBodDes.setText("");
                txtNomBodDes.setText("");
            } else {
                mostrarVenConBodDes(1);
            }
        } else
            txtCodBodDes.setText(strCodBodDes);
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
////        if(!isVersionActualizada())
////        {
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////        dispose();
////        }
////        else
////        {
            if(   (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==2332)  ){
                configurarFrm();
                agregarDocLis();
            }
            else if(   (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==1662)  ){
                mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Empresas.</HTML>");
                dispose();
            }
            else if(   (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==1662)  ){
                configurarFrm();
                agregarDocLis();
            }
            else if(   (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==2332)  ){
                mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Grupo.</HTML>");
                dispose();
            }
////        }        
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
        // TODO add your handling code here:
        txtNomBodDes.transferFocus();
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
        // TODO add your handling code here:
        strNomBodDes = txtNomBodDes.getText();
        txtNomBodDes.selectAll();
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
            if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)) {
                if (txtNomBodDes.getText().equals("")) {
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } else {
                    mostrarVenConBodDes(2);
                }
            }
            else
                txtNomBodDes.setText(strNomBodDes);
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        // TODO add your handling code here:
        mostrarVenConBodDes(0);
    }//GEN-LAST:event_butBodDesActionPerformed

    private void optHisAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnuActionPerformed
        // TODO add your handling code here:
        if(optHisAnu.isSelected()){
            chkHisVta.setSelected(true);
            optHisMen.setSelected(false);
            cargarAnios();
        }
        else{
            optHisMen.setSelected(true);
            chkHisVta.setSelected(true);
            cargarAniosMeses();
        }
    }//GEN-LAST:event_optHisAnuActionPerformed

    private void optHisMenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMenActionPerformed
        // TODO add your handling code here:
        if(optHisMen.isSelected()){
            chkHisVta.setSelected(true);
            optHisAnu.setSelected(false);
            cargarAniosMeses();
        }
        else{
            chkHisVta.setSelected(true);
            optHisAnu.setSelected(true);
            cargarAnios();
        }
    }//GEN-LAST:event_optHisMenActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
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
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:f
        
////        if(!isVersionActualizada())
////        {
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            dispose();
////        }
////        else
////        {
             objTblMod.removeAllRows();
            if (butCon.getText().equals("Consultar")){
                //objTblTotales.isActivo(false);
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else{
                blnCon=false;
            }
////        }
        

     

    }//GEN-LAST:event_butConActionPerformed

    private void txtDesLarClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarClaActionPerformed
        // TODO add your handling code here:
        txtDesLarCla.transferFocus();
}//GEN-LAST:event_txtDesLarClaActionPerformed

    private void txtDesLarClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarClaFocusGained
        // TODO add your handling code here:
        strDesLarCla=txtDesLarCla.getText();
        txtDesLarCla.selectAll();
}//GEN-LAST:event_txtDesLarClaFocusGained

    private void txtDesLarClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarClaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCla.getText().equalsIgnoreCase(strDesLarCla)) {
            if (txtDesLarCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCorCla.setText("");
                txtDesLarCla.setText("");
            } else {
                mostrarVenConCla(2);
            }
        } else
            txtDesLarCla.setText(strDesLarCla);
}//GEN-LAST:event_txtDesLarClaFocusLost

    private void txtDesCorClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorClaActionPerformed
        // TODO add your handling code here:
        txtDesCorCla.transferFocus();
}//GEN-LAST:event_txtDesCorClaActionPerformed

    private void txtDesCorClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorClaFocusGained
        // TODO add your handling code here:
        strDesCorCla=txtDesCorCla.getText();
        txtDesCorCla.selectAll();
}//GEN-LAST:event_txtDesCorClaFocusGained

    private void txtDesCorClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorClaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCla.getText().equalsIgnoreCase(strDesCorCla)) {
            if (txtDesCorCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCorCla.setText("");
                txtDesLarCla.setText("");
            } else {
                mostrarVenConCla(1);
            }
        } else
            txtDesCorCla.setText(strDesCorCla);
}//GEN-LAST:event_txtDesCorClaFocusLost

    private void butClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaActionPerformed
        // TODO add your handling code here:
        mostrarVenConCla(0);
}//GEN-LAST:event_butClaActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        
////        if(!isVersionActualizada())
////        {
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            dispose();
////        }
////        else
////        {
            if(guardarDatos()){
                mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
                objTblMod.removeAllRows();
                cargarReg();
                lblMsgSis.setText("Cargando datos...");
            }
            else{
                mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
            }            
////        }
            

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void chkHisVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaActionPerformed
        // TODO add your handling code here:
        if(chkHisVta.isSelected()){
            optHisAnu.setSelected(true);
            cargarAnios();
        }
        else{
            optHisAnu.setSelected(false);
            optHisMen.setSelected(false);
            objTblModHis.removeAllRows();
        }
    }//GEN-LAST:event_chkHisVtaActionPerformed

    private void butCalRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCalRepActionPerformed
        // TODO add your handling code here:
        
////        if(!isVersionActualizada())
////        {
////            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////            dispose();
////        }
////        else
////        {
                cambiarNumeroMesesReponer();
                calcularMinimoMaximo();
                calcularCantidadReponer();

                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount()>0)
                {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
                objTblMod.initRowsState(); // JM 27/Abr/2023             
////        }
    

    }//GEN-LAST:event_butCalRepActionPerformed

    private void optTodRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodRegItemStateChanged
        // TODO add your handling code here:
        if (optTodReg.isSelected())
        {
            objVenConItm01.limpiar();
        }
    }//GEN-LAST:event_optTodRegItemStateChanged

    private void chkSalInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSalInvActionPerformed
        // TODO add your handling code here:
        
        ocultaColumnasPorFiltro();
     if (chkSalInv.isSelected())
        validaMS =true;
     else
        validaMS =false; 
    }//GEN-LAST:event_chkSalInvActionPerformed

    private void chkVecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVecActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkVecActionPerformed

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained

    }//GEN-LAST:event_tblFixFocusGained

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void chkMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMesActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPorFiltro();
    }//GEN-LAST:event_chkMesActionPerformed

    private void butCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarActionPerformed
        // TODO add your handling code here:
        try{
            if(cargarArchivoXLS()){
        
            }
            
        }
        catch(Exception e)
        {  
           objUti.mostrarMsgErr_F1(this, e);
        }        
    }//GEN-LAST:event_butCarActionPerformed
    private boolean blnCargarArchivo;      //Var. booleana que indica si se presiono el Boton <Cancelar> en la ventana de Carga de Archivo de Retenciones.
    private ArrayList arlDatExcel, arlRegExcel;
    private ArrayList arlDatExcelVal, arlRegExcelVal;
    
    private int intNumeroItemsProcesar=0;
    private int INT_EXC_COD_ITM=0;
    private int INT_EXC_BLN=1;
    
    private boolean getArchivoExcel(){
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

                arlDatExcel=new ArrayList();
                // Iterar sobre las filas de la hoja
                for (int i = 1; i < numRows; i++) {
                    // Iterar sobre las columnas de la fila actual
                    arlRegExcel=new ArrayList();

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

private boolean ingresarDatosCargados(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strUpdateItems="", strFecUltMod;
        BigDecimal  cantMaxEx ,cantMinEx,canProEx ;
        String cod_Ex="",cod_Tb="";
////        try{
////            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            if(conLoc!=null){
////                conLoc.setAutoCommit(false);
////                stmLoc = conLoc.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
              
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    cod_Ex =objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM).toString();
                    for(int j=0; j<objTblMod.getRowCountTrue();j++)
                    {
                          cod_Tb =objTblMod.getValueAt(j, INT_TBL_DAT_COD_ALT_ITM_DOS).toString();      
                          if(cod_Tb.equals(cod_Ex))
                          { 
////                              cantMinEx =BigDecimal.valueOf(    objUti.parseDouble(objTblMod.getValueAt(i,(intNumColIniStkRep+3))   ));
                              cantMinEx =BigDecimal.valueOf(  objUti.parseDouble(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_MIN)));  
                              cantMaxEx =BigDecimal.valueOf(  objUti.parseDouble(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_MAX))); 
                             // canProEx = BigDecimal.valueOf(  objUti.parseDouble(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_PROM)));
                              objTblMod.setValueAt("" + objUti.redondearBigDecimal(cantMinEx, objParSis.getDecimalesBaseDatos()), j, (intNumColIniStkRep+3));
                              objTblMod.setValueAt("" + objUti.redondearBigDecimal(cantMaxEx, objParSis.getDecimalesBaseDatos()), j, (intNumColIniStkRep+4));
                             // objTblMod.setValueAt("" + objUti.redondearBigDecimal(canProEx, objParSis.getDecimalesBaseDatos()), j, (intNumColIniProMen+1));
                              break; 
                          }
                    }
                    
                    
////                    strUpdateItems+="UPDATE tbm_inv SET ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_PROMEDIO).toString().length()>0)
////                        strUpdateItems+="nd_proAnuVenMan="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PROMEDIO)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_STOCK).toString().length()>0)
////                        strUpdateItems+="nd_stkBas="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_STOCK)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_TRANSITO).toString().length()>0)
////                        strUpdateItems+="nd_tra="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_TRANSITO)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_FRECUENCIA).toString().length()>0)
////                        strUpdateItems+="nd_fre="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_FRECUENCIA)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_PPMAX).toString().length()>0)
////                        strUpdateItems+="nd_punParMax="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PPMAX)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_PPMIN).toString().length()>0)
////                        strUpdateItems+="nd_punParMin="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PPMIN)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_MOQ).toString().length()>0)
////                        strUpdateItems+="nd_moq="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_MOQ)+", ";
////                    if(objUti.getObjectValueAt(arlDatExcel, i, INT_ARL_EXC_EXC).toString().length()>0)
////                        strUpdateItems+="nd_exc="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_EXC)+"  " ; 
////                    strUpdateItems+="fe_ultmod="+objUti.codificar(strFecUltMod)+", ";
////                    strUpdateItems+="co_usrMod="+objParSis.getCodigoUsuario()+", "; 
////                    strUpdateItems+="WHERE tx_codAlt2="+objUti.codificar(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM)) +"; ";
                }
////                stmLoc.executeUpdate(strUpdateItems);
////                //Historico de inventario
////                if(!objHisTblBasDat.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())){
////                    blnRes=false;
////                }
                
                if(blnRes){
                    System.out.println("OK! ");
////                    conLoc.commit();
                }
                else{
////                    conLoc.rollback();
                }
                
                
////                stmLoc.close();
////                stmLoc=null;
////                conLoc.close();
////                conLoc=null;
////            }
////            else{
////                blnRes=false;
////            }
////        }
////        catch(java.sql.SQLException e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        catch(Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
        return blnRes;
     }    
    
    private String strSql="";
    
    /**
     * 
     * @return 
     */
    private boolean validarDatosCargados(){
        boolean blnRes=true;
        String strCodItmDynBus="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCuentaItemsEncontrados=0;
        try{
            arlDatExcelVal=new ArrayList();
            for(int i=0;i<arlDatExcel.size();i++){
                strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM)+"'";
                if(i+1<arlDatExcel.size()) {
                    strCodItmDynBus+=",";
                }
                arlRegExcelVal=new ArrayList();
                arlRegExcelVal.add(INT_EXC_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM));
                arlRegExcelVal.add(INT_EXC_BLN, "N");
                arlDatExcelVal.add(arlRegExcelVal);
            }
            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, TRIM(ITMGEDSC) as ITMGEDSC, a1.ITEMTYPE  "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1 "
                        + " WHERE  a1.inactive =0 and a1.ITMGEDSC IN ( "+strCodItmDynBus+" ) "  ;
//                System.out.println("obtenerDatosGP: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                    intCuentaItemsEncontrados++;
                    /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_ITEM))){
                            objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_BLN, "OK");
                        }
//                        else{
//                            objUti.setStringValueAt(arlDatExcelVal, x,INT_EXC_BLN, "NO");
//                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                
////                if(intCuentaItemsEncontrados!=intNumeroItemsProcesar){
////                    blnRes=false;
////                    
////                }
                
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
    
boolean blnNoSeleccion=true;
     
     
     private boolean cargarArchivoXLS(){
        boolean blnRes=true;
////        String strAux;
        try{          
            if (getArchivoExcel() == true){
                if(validarDatosCargados()){
                    if(ingresarDatosCargados()){
                        mostrarMsgInf("La carga del archivo fue exitosa.");
                    }
                    else{
                        blnRes=false;
                        mostrarMsgInf("No se puedo guardar los datos...");
                    }
                }
                else
                {
////////////                    strAux =  "Existen Items en el documento que no son encontrados en el Sistema." + "\n\n";
////////////                    strAux += "Notifique esta novedad al Administrador del sistema. \n";
////////////                    for(int x=0;x<arlDatExcelVal.size();x++){
////////////                        if(objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_BLN).equals("N")){
////////////                            strAux+=" "+objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_ITEM) + " - "; 
////////////                        }
////////////                    }
////////////                    blnRes=false;
////////////                    mostrarMsgInf(strAux);
                    
                        StringBuilder strAux = new StringBuilder();

                        strAux.append("<html>");
                        strAux.append("<body style='font-family: Arial, sans-serif; font-size: 14px;'>");

                        strAux.append("<p><strong>Existen Items en el documento que no fueron encontrados en el Sistema.</strong></p>");
                        strAux.append("<p>Por favor, notifique esta novedad al <strong>Administrador del sistema</strong>.</p>");

                        strAux.append("<div style='margin-top: 10px; padding: 8px; border: 1px solid #ccc;'>");
                        int contador = 0;
                        for (int x = 0; x < arlDatExcelVal.size(); x++) {
                            if (objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_BLN).equals("N")) {

                                String item = objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_ITEM);

                                // Agregar un guion adelante y formatear con guiones cada 3 letras
                                item = "-" + item.replaceAll("(.{3})(?=.)", "$1-");

                                strAux.append("<span style='display: inline-block; width: 120px;'>")
                                      .append(item)
                                      .append("</span>");

                                contador++;

                                // Salto de línea cada 6 items
                                if (contador % 15 == 0) {
                                    strAux.append("<br>");
                                }
                            }
                        }
                        strAux.append("</div>");

                        strAux.append("</body>");
                        strAux.append("</html>");

                        blnRes = false;
                        mostrarMsgInf(strAux.toString());


                    
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
    
    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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
////            ocultaColumnasPorFiltro();
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }


   private boolean cargarReg(){ 
        boolean blnRes=true;
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                if(isCamVal()){
                    if(chkHisVta.isSelected()){
                        if(configurarColumnasAniosMeses()){
                            
                            if(optHisMen.isSelected())
                            {
                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())//grupo
                                {
                                    if(consultarRegAniMesGrp()){ 
                                        calcularColumnaTotalesAniMes();
                                         calcularPromedioMensual();
                                         calcularCantidadReponer();
////                                         calcularStockActualAnterior();
                                         calcularStockActualSiguiente();
                                    }
                                }
                                else //por empresas
                                {
                                    if(consultarRegAniMesEmp()){
                                            calcularColumnaTotalesAniMes();
                                            //SOLO POR EMPRESAS PORQ POR GRUPO ESTA COLUMNA DE PROMEDIO CALCULADO NO EXISTE
                                            //para cuando no hay columnas de totales, sino esta informacion se calcula en "calcularColumnaTotalesAniMes"
                                            calcularColumnaPromedioCalculado();

                                    }
                                }
                            }
                            else if(optHisAnu.isSelected()) 
                            {
                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())//grupo
                                {
                                    if(consultarRegAniGrp()){
////                                        calcularVtasMensuales(); 
                                        calcularColumnaTotalesAniMes();

                                        calcularPromedioAnual();
                                        calcularCantidadReponer();

                                    }
                                }
                                else //empresas
                                {
                                    if(consultarRegAniEmp()){                                        
                                        calcularColumnaTotalesAniMes();
                                        //SOLO POR EMPRESAS PORQ POR GRUPO ESTA COLUMNA DE PROMEDIO CALCULADO NO EXISTE
                                        //para cuando no hay columnas de totales, sino esta informacion se calcula en "calcularColumnaTotalesAniMes"
                                        calcularColumnaPromedioCalculado();
                                    }
                                }

                            }
                        }
                    }
                    else{
                        if(configurarColumnasSinHistorico()){
                            if(consultarRegStkActGrp()){
                            }
                        }
                    }
                }
                objTblMod.initRowsState();
//                objTblMod.addUserHiddenColumn((intNumColIniStkRep+5), tblDat);
                
                if(chkHidColFec.isSelected()){
                    System.out.println("A");
                    //FECHA
                    for(int h=intNumColIniFec; h<intNumColFinFec; h++){
                        System.out.println("B");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.addUserHiddenColumn(h, tblDat);
                    }
                    //TOTALES
                    for(int h=intNumColIniTot; h<intNumColFinTot; h++){
                        System.out.println("C");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.addUserHiddenColumn(h, tblDat);
                    }
                }
                else{
                    System.out.println("D");
                    //FECHA
                    for(int h=intNumColIniFec; h<intNumColFinFec; h++){
                        System.out.println("E");
                        objTblMod.removeUserHiddenColumn(h, tblDat);
                    }
                    //TOTALES
                    for(int h=intNumColIniTot; h<intNumColFinTot; h++){
                        System.out.println("E");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.removeUserHiddenColumn(h, tblDat);
                    }
                }
                ocultaColumnasPorFiltro();
                con.close();
                con=null;
            }
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }  

   
private void calcularVtasMensuales(){
        int k=0;
        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                k=0;
                for(int j=intNumColIniMarFac; j<intNumColFinMarFac; j++){
                    
                    
                    
                    
////                        bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (j-1))));//obtener el costo
////                        bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
////                        bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
////
////                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
////                            objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
////                        }
////                        else{
////                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
////                                objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
////                            }
////                            else{
////                                if(chkMosValKil.isSelected())
////                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
////                                else
////                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                                               objTblMod.setValueAt(10, i, j);//intNumColIniMarNotPed
////                            }
                        }

                }
            }
        

    
       






    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanPrgSis;
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butCalRep;
    private javax.swing.JButton butCar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCla;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JCheckBox chkHidColFec;
    private javax.swing.JCheckBox chkHisVta;
    private javax.swing.JCheckBox chkMes;
    private javax.swing.JCheckBox chkSalInv;
    private javax.swing.JCheckBox chkStkMinSug;
    private javax.swing.JCheckBox chkVec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspNumMesRep;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblCla;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumMesRep;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFilReg;
    private javax.swing.JRadioButton optHisAnu;
    private javax.swing.JRadioButton optHisMen;
    private javax.swing.JRadioButton optTodReg;
    private javax.swing.JPanel panAuxSpnTot;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBotAux;
    private javax.swing.JPanel panCanItmRep;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panConFil;
    private javax.swing.JPanel panFacMin;
    private javax.swing.JPanel panFilHis;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panItm;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panOptHis;
    private javax.swing.JPanel panParRep;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptDat;
    private javax.swing.JPanel panRptFix;
    private javax.swing.JPanel panTotDat;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JSplitPane spnRpt;
    private javax.swing.JScrollPane spnTotDat;
    private javax.swing.JScrollPane spnTotFix;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTable tblHis;
    private javax.swing.JTable tblTotDat;
    private javax.swing.JTable tblTotFix;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtDesCorCla;
    private javax.swing.JTextField txtDesLarCla;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtValMax;
    private javax.swing.JTextField txtValMin;
    // End of variables declaration//GEN-END:variables


    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            
            //Configurar ZafVenConItmGP01:
            objVenConItm01=new ZafVenConItmGP01(objParSis, "a1.", optFilReg);
            panFilItm.add(objVenConItm01);
            objVenConItm01.setBounds(24, 64, 656, 20);
            objUti = new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
////            this.setTitle(strAux + " v0.45");
            this.setTitle(strAux + version);            
            lblTit.setText(strAux);
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());

            //Configurar las ZafVenCon.
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDatHis();
            configurarTblDat();
            configurarTblDatTot();
            cargarAnios();            
            configurarVenConCla();
            configurarVenConItm();
            configurarTblFix();
            configurarTblDatFixTot();     
            objTblModTotFix.insertRows(2,0);
            objTblModTotFix.setValueAt("Totales - peso: ", 0, INT_TBL_FIX_NOM_ITM);
            objTblModTotFix.setValueAt("Totales - precio venta neta: ", 1, INT_TBL_FIX_NOM_ITM);            

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                chkStkMinSug.setVisible(true);
                chkStkMinSug.setEnabled(true);
            }
            else{
                chkStkMinSug.setVisible(false);
                chkStkMinSug.setEnabled(false);
            }

            txtCodCla.setVisible(false);
            txtCodCla.setEditable(false);
            txtCodCla.setEnabled(false);
            
            /*JM Oculto*/
            lblCla.setVisible(false);
            txtDesCorCla.setVisible(false);
            txtDesLarCla.setVisible(false);
            butCla.setVisible(false);

            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);


            if(objParSis.getCodigoUsuario()==1){
                butCon.setVisible(true);
                butGua.setVisible(true);
                butCer.setVisible(true);
            }
            else{
                if(objParSis.getCodigoMenu()==2332){//bodegas
                    if(objPerUsr.isOpcionEnabled(2333)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2334)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2335)){//cerrar
                        butCer.setVisible(true);
                    }
                }

                if(objParSis.getCodigoMenu()==1662){//proveedores
                    if(objPerUsr.isOpcionEnabled(1663)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(1664)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(1665)){//cerrar
                        butCer.setVisible(true);
                    }
                }
            }

            //jspNumMesRep.setModel(new SpinnerNumberModel(1, 1, 100, 1));
            jspNumMesRep.setModel(new SpinnerNumberModel(1, 0.25, 100, 0.1));
            
            txtValMin.setEnabled(false);
            txtValMax.setEnabled(false);
            txtValMin.setText("0.50");
            txtValMax.setText("1.00");
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                lblNumMesRep.setVisible(false);
                jspNumMesRep.setVisible(false);
                jspNumMesRep.setEnabled(false);
                panFacMin.setVisible(false);
                butCalRep.setVisible(false);
                butCalRep.setEnabled(false);
            }



        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean configurarTblDatFixTot()
    {
        boolean blnRes=true;
        try
        {
            
            
            objTblModTotFix=new ZafTblMod();
            objTblModTotFix.setHeader(vecCabFix);
            tblTotFix.setModel(objTblModTotFix);

            //Configurar JTable: Establecer tipo de selección.
            tblTotFix.setRowSelectionAllowed(true);
            tblTotFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTotFix=new ZafTblFilCab(tblTotFix);
            tblTotFix.getColumnModel().getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblFilCabTotFix);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_FIX_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_3).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_FIX_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_FIX_DIS_CEN_DIS).setPreferredWidth(70);            
        
////            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(60);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(60);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(60);            
////            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(60);        
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_LIN, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_MAE, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_SIS, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_ALT, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_ALT_DOS, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_ALT_3, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_NOM_ITM, tblTotFix);            
////            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_UNI_MED, tblTotFix);
////            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_PES_KG, tblTotFix);
////            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COS_UNI_GRP, tblTotFix);            
////            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblTotFix);            
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblFix.getColumnCount(); j++){
                tblTotFix.getColumnModel().getColumn(j).setWidth(tblFix.getColumnModel().getColumn(j).getWidth());
                tblTotFix.getColumnModel().getColumn(j).setMaxWidth(tblFix.getColumnModel().getColumn(j).getMaxWidth());
                tblTotFix.getColumnModel().getColumn(j).setMinWidth(tblFix.getColumnModel().getColumn(j).getMinWidth());
                tblTotFix.getColumnModel().getColumn(j).setPreferredWidth(tblFix.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotFix.getColumnModel().getColumn(j).setResizable(tblFix.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotFix.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotFix=new ZafTblCelRenLbl();
            objTblCelRenLblTotFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotFix.setTipoFormato(objTblCelRenLblTotFix.INT_FOR_NUM);
            objTblCelRenLblTotFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
////            tblTotFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblTotFix);
////            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblTotFix);
            objTblCelRenLblTotFix=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnTotFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotFix.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);

            ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix();
            tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);                        
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFix2=spnFix.getHorizontalScrollBar();
            barDatTotFix2=spnTotFix.getHorizontalScrollBar();
            
            barFixVer2=spnFix.getVerticalScrollBar();
            barDat2=spnDat.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barFix2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTotFix2.setValue(evt.getValue());
                }
            });
            //VERTICAL
            barFixVer2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat2.setValue(evt.getValue());
                }
            });
            spnTotFix.setColumnHeader(null);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private class ZafTblColModLisTotFix implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblFix.getTableHeader().getResizingColumn()!=null){
                intColSel=tblFix.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblFix.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotFix.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
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
    
    private boolean configurarTblFix()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCabFix=new Vector(14);  //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_FIX_LIN,"");
            vecCabFix.add(INT_TBL_FIX_COD_MAE,"Cód.Mae.");
            vecCabFix.add(INT_TBL_FIX_COD_SIS,"Cód.Sis.");
            vecCabFix.add(INT_TBL_FIX_COD_ALT,"Cód.Alt.Itm.");
            vecCabFix.add(INT_TBL_FIX_COD_ALT_DOS,"3L");
            vecCabFix.add(INT_TBL_FIX_COD_ALT_3,"Cód.Alt.3");
            vecCabFix.add(INT_TBL_FIX_NOM_ITM,"Item");
            vecCabFix.add(INT_TBL_FIX_UNI_MED,"Unidad");
            vecCabFix.add(INT_TBL_FIX_DIS_CEN_DIS,"Dis.Cen.Dis.");            
            
////            ZafBorder borderRenderer = new ZafBorder(INT_TBL_FIX_COD_GEN);
////            
////////            tblDat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(2, 3, 4)));
////            
////                tblDat.getColumnModel().getColumn(INT_TBL_FIX_COD_GEN).setCellRenderer(new ZafBorder(INT_TBL_FIX_COD_GEN));
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);
            tblFix.setModel(objTblModFix);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblFix.setRowSelectionAllowed(true);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            objTblPopMnuFix.setPegarEnabled(true);
            objTblPopMnuFix.setBorrarContenidoEnabled(true);
            objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuFix.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.

                    }
                    else if (objTblPopMnuFix.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
         
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
            tcmAux.getColumn(INT_TBL_FIX_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_DIS_CEN_DIS).setPreferredWidth(90);            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFix.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_MAE, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_SIS, tblFix);


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//            objMouMotAdaFix=new ZafMouMotAda();
//            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblFilCabFix);

            //Configurar JTable: Renderizar celdas.
//////            objTblCelRenLblFix=new ZafTblCelRenLbl();
//////            objTblCelRenLblFix.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//////            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellRenderer(objTblCelRenLblFix);
//////            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setCellRenderer(objTblCelRenLblFix);
//////            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFix);
//            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3)))));
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }
//                }
//            });
            
////            objTblCelRenLblDerFix=new ZafTblCelRenLbl();
////            objTblCelRenLblDerFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setCellRenderer(objTblCelRenLblDerFix);
////            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellRenderer(objTblCelRenLblDerFix);
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
////            
////            objTblCelRenLblNumFix=new ZafTblCelRenLbl();
////            objTblCelRenLblNumFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////            objTblCelRenLblNumFix.setTipoFormato(objTblCelRenLblNumFix.INT_FOR_NUM);
////            objTblCelRenLblNumFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
////            tcmAux.getColumn(INT_TBL_FIX_STK_CON).setCellRenderer(objTblCelRenLblNumFix);
////            tcmAux.getColumn(INT_TBL_FIX_COD_GEN).setCellRenderer(objTblCelRenLblFix);
////            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellRenderer(objTblCelRenLblNumFix);
////            tcmAux.getColumn(INT_TBL_FIX_PES_KGR_AUX).setCellRenderer(objTblCelRenLblNumFix);
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
            
////            objTblCelRenButFix=new ZafTblCelRenBut();
////            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellRenderer(objTblCelRenButFix);
////            objTblCelRenButFix=null;
//            
//            objTblCelRenChkFix=new ZafTblCelRenChk();
//            tcmAux.getColumn(INT_TBL_FIX_CHK_SER).setCellRenderer(objTblCelRenChkFix);
//            objTblCelRenChkFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3)))));
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenChkFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }
//                }
//            });
////            //Configurar JTable: Editor de celdas.
////            int intColVen[]=new int[2];
////            intColVen[0]=1;
////            intColVen[1]=2;
////            int intColTbl[]=new int[2];
////            intColTbl[0]=INT_TBL_FIX_COD_UNI;
////            intColTbl[1]=INT_TBL_FIX_DEC_UNI;
////            objTblCelEdiTxtVcoUniMedFix=new ZafTblCelEdiTxtVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMedFix);
////            
////            objTblCelEdiButVcoUniMedFix=new ZafTblCelEdiButVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMedFix);
////            intColVen=null;
////            intColTbl=null;
////
////            objTblCelEdiTxtFixItm=new ZafTblCelEdiTxt(tblFix);
////            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellEditor(objTblCelEdiTxtFixItm);
////            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellEditor(objTblCelEdiTxtFixItm);
////            objTblCelEdiTxtFixItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                int intFilSel=-1;
////                int intColSel=-1;
////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    intFilSel=tblFix.getSelectedRow();
////                    intColSel=tblFix.getSelectedColumn();
////                }
////                    
////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_ALT), intFilSel, INT_TBL_DAT_COD_ALT);
////                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_NOM_ITM), intFilSel, INT_TBL_DAT_NOM_ITM);
////
////                }
////            });
////            
////            objTblCelEdiTxtFixUniMed=new ZafTblCelEdiTxt(tblFix);
////            tcmAux.getColumn(INT_TBL_FIX_COD_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
////            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
////            objTblCelEdiTxtFixUniMed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                int intFilSel=-1;
////                int intColSel=-1;
////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    intFilSel=tblFix.getSelectedRow();
////                    intColSel=tblFix.getSelectedColumn();
////                }
////                    
////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_UNI), intFilSel, INT_TBL_DAT_COD_UNI);
////                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_DEC_UNI), intFilSel, INT_TBL_DAT_DEC_UNI);
////
////                }
////            });
////            
////            objTblCelEdiTxtPesKgrFix=new ZafTblCelEdiTxt(tblFix);
////            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgrFix);
////            //objTblCelEdiTxtPesKgrFix=null;
////            objTblCelEdiTxtPesKgrFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                int intFilSel=-1;
////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    intFilSel=tblFix.getSelectedRow();
////                }
////                    
////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_PES_KGR), intFilSel, INT_TBL_DAT_PES_KGR);
////
////                    calcularReversionValoresKilos();
////                    
////                    calcularPreVtaReaKil();
////                    calcularPreLisCal();
////                    calcularMarPreLisCal_row();
////                }
////            });
                        
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
    
////private boolean configurarTblDatFix_antes() //chiqui02022024
////    {
////        boolean blnRes = true;
////        try {
////            
////            //Configurar JTable: Establecer el modelo.
////            vecDatFix = new Vector();    //Almacena los datos
////            vecCabFix = new Vector(10);   //Almacena las cabeceras
////            vecCabFix.clear();
////            vecCabFix.add(INT_TBL_DAT_FIX_LIN, "");
////            vecCabFix.add(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM, "Est.Mov.Itm.");
////            vecCabFix.add(INT_TBL_DAT_FIX_COD_ITM, "Cod.Itm");
////            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "Cod.Alt.");
////            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2, "Cod.Alt2.");
////            vecCabFix.add(INT_TBL_DAT_FIX_NOM_ITM, "Nombre");
////////            vecCabFix.add(INT_TBL_DAT_FIX_UNI_MED, "Uni");
////////            vecCabFix.add(INT_TBL_DAT_FIX_PES_KG, "Peso(kg)");
////////            vecCabFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "Pre.Vta.Net.");
////////            vecCabFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "Est.Eli.Itm.");
////            
////            objTblModFix = new ZafTblMod();
////            objTblModFix.setHeader(vecCabFix);
////
////            //Configurar JTable: Establecer el modelo de la tabla.
////            tblFix.setModel(objTblModFix);
////            //Configurar JTable: Establecer tipo de seleccián.
////            tblFix.setRowSelectionAllowed(true);
////            tblFix.getTableHeader().setReorderingAllowed(false);
////            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
////
////////////////            //Configurar JTable: Establecer el mená de contexto.
////////////////            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
////////////////            objTblPopMnuFix.setBorrarContenidoEnabled(true);
////////////////            objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
////////////////                int intFilEli[];
////////////////                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
////////////////                    intFilEli=tblFix.getSelectedRows();
////////////////                }
////////////////                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
////////////////                    if (objTblPopMnuFix.isClickEliminarFila()){
////////////////                        for(int p=(intFilEli.length-1);p>=0; p--){
////////////////                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
////////////////                            arlRegFilEli=new ArrayList();
////////////////                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
////////////////                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM));
////////////////                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
////////////////                            arlDatFilEli.add(arlRegFilEli);
////////////////                            //ahora si se remueve la fila seleccionada
////////////////                            objTblMod.removeRow(intFilEli[p]);
////////////////                        }
////////////////                    }
////////////////                }
////////////////            });
////
////            tblFix.setAutoResizeMode(tblDat.getAutoResizeMode());
////            //Configurar JTable: Establecer el ancho de las columnas.
////            javax.swing.table.TableColumnModel tcmAux = tblFix.getColumnModel();
////
////            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(20);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setPreferredWidth(30);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(50);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(74);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setPreferredWidth(12);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(125);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(34);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(58);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(58);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(58);
////            
////            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setResizable(false);
////
////            //Configurar JTable: Ocultar columnas del sistema.
////            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblFix);
////////////            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblFix);
////
//////////////            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//////////////            objMouMotAdaFix = new ZafMouMotAdaFix();
//////////////            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
////            //Configurar JTable: Establecer la fila de cabecera.
//////////////            objTblFilCabFix = new ZafTblFilCab(tblFix);
//////////////            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabFix);
////
////            //Configurar JTable: Editor de básqueda.
////            objTblBusFix = new ZafTblBus(tblFix);
////
////            //INICIO FIXED
////
////            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
////            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
////            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//////            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
////            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
////            tblDat.setSelectionModel(modDat);//se descomento
////
////            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
////            ListSelectionModel modFix = tblDat.getSelectionModel();
////            tblFix.setSelectionModel(modFix);
////                      
////            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
////            barFix3=spnFix.getHorizontalScrollBar();
////            barDatTotFix3=spnTotFix.getHorizontalScrollBar();
////            
////            barFixVer3=spnFix.getVerticalScrollBar();
////            barDat3=spnDat.getVerticalScrollBar();
////            
////            //PARA DESPLAZAMIENTOS DE CELDAS
////            //HORIZONTAL
////            barFix3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
////                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
////                    barDatTotFix3.setValue(evt.getValue());
////                }
////            });
////            //VERTICAL
////            barFixVer3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
////                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
////                    barDat3.setValue(evt.getValue());
////                }
////            });
////
////            vecAux=new Vector();
////////            vecAux.add("" + INT_TBL_DAT_FIX_PES_KG);
////            vecAux.add("" + INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
////            objTblModFix.setColumnasEditables(vecAux);
////            vecAux=null;
////            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);
////
////
//////////////            //Configurar JTable: Agrupamiento de columnas
//////////////            ZafTblHeaGrp objTblHeaGrpDatChq=(ZafTblHeaGrp)tblFix.getTableHeader();
//////////////            objTblHeaGrpDatChq.setHeight(16*4);
//////////////            ZafTblHeaColGrp objTblHeaColGrpDatChq=null;
//////////////            objTblHeaColGrpDatChq=new ZafTblHeaColGrp("Datos del item");
//////////////            objTblHeaColGrpDatChq.setHeight(16);
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_LIN));
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM));
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM));
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM));
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2));
//////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM));
//////////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED));
//////////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG));
//////////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP));
//////////////////            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM));
//////////////            objTblHeaGrpDatChq.addColumnGroup(objTblHeaColGrpDatChq);
////            //FIN FIXED
////
////            objTblCelRenLblColFix=new ZafTblCelRenLbl();
////            objTblCelRenLblColFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////            objTblCelRenLblColFix.setTipoFormato(objTblCelRenLblColFix.INT_FOR_NUM);
////            objTblCelRenLblColFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblColFix);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblColFix);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setCellRenderer(objTblCelRenLblColFix);
////////            objTblCelRenLblColFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
////////                java.awt.Color colFonColCru;
////////                String strEstItmEli="";
////////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
////////                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
////////                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
////////                    if (strEstItmEli.equals("S")){
////////                        objTblCelRenLblColFix.setBackground(colFonColCru);
////////                    }
////////                    else{
////////                        objTblCelRenLblColFix.setBackground(Color.WHITE);
////////                    }
////////                }
////////            });
////            
////            objTblCelRenLblFixStr=new ZafTblCelRenLbl();
////            objTblCelRenLblFixStr.setHorizontalAlignment(javax.swing.JLabel.LEFT);
////            objTblCelRenLblFixStr.setTipoFormato(objTblCelRenLblFixStr.INT_FOR_GEN);
////            //tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblCelRenLblFixStr);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellRenderer(objTblCelRenLblFixStr);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setCellRenderer(objTblCelRenLblFixStr);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setCellRenderer(objTblCelRenLblFixStr);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setCellRenderer(objTblCelRenLblFixStr);
////            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFixStr);
////////            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setCellRenderer(objTblCelRenLblFixStr);
////            
////////            objTblCelRenLblFixStr.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
////////                java.awt.Color colFonColCru;
////////                String strEstItmEli="";
////////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
////////                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
////////                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
////////                    if (strEstItmEli.equals("S")){
////////                        objTblCelRenLblFixStr.setBackground(colFonColCru);
////////                    }
////////                    else{
////////                        objTblCelRenLblFixStr.setBackground(Color.WHITE);
////////                    }
////////                }
////////            });
////            
//////////////            objTblCelEdiTxtPesItmFix=new ZafTblCelEdiTxt(tblFix);
//////////////            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setCellEditor(objTblCelEdiTxtPesItmFix);
//////////////            objTblCelEdiTxtPesItmFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////////////                BigDecimal bgdPesItmFix;
//////////////                int intFil=-1;
//////////////                int intCol=-1;
//////////////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////////                    bgdPesItmFix=new BigDecimal(BigInteger.ZERO);
//////////////                    intFil=tblFix.getSelectedRow();
//////////////                    intCol=tblFix.getSelectedColumn();
//////////////                }
//////////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////////                    bgdPesItmFix=new BigDecimal(objTblModFix.getValueAt(intFil, intCol)==null?"0":(objTblModFix.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModFix.getValueAt(intFil, intCol).toString()));
//////////////                    objTblMod.setValueAt(bgdPesItmFix, intFil, INT_TBL_DAT_PES_KG);
//////////////                    recalcularValoresTabla(tblFix.getSelectedRow());
//////////////                }
//////////////            });
////
////            //Configurar JTable: Renderizar celdas.
////            objTblCelRenChkFixEstItmStkRep=new ZafTblCelRenChk();
////            tblFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellRenderer(objTblCelRenChkFixEstItmStkRep);
////            objTblCelRenChkFixEstItmStkRep=null;
////            
//////////            //Configurar JTable: Editor de celdas.
//////////            objTblCelEdiChkFixEstItmStkRep=new ZafTblCelEdiChk(tblFix);
//////////            tblFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellEditor(objTblCelEdiChkFixEstItmStkRep);
//////////            objTblCelEdiChkFixEstItmStkRep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////////                int intFil=-1;
//////////                int intCol=-1;
//////////                String strNomCliFilSel="";
//////////                String strNomBenFilSel="";
//////////                boolean blnIsChkBef;
//////////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////                    intFil=tblFix.getSelectedRow();
//////////                    intCol=tblFix.getSelectedColumn();
//////////                    
////////////                    if(objTblModFix.isChecked(intFil, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM)){
////////////                        objTblCelEdiChkFixEstItmStkRep.setCancelarEdicion(false);
////////////                    }
//////////                }
//////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////                    if(objTblModFix.isChecked(intFil, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM))
//////////                        objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK_EST_MOV_ITM);
//////////                    else
//////////                        objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK_EST_MOV_ITM);
//////////                }
//////////            });
////            
////            
////            
////            tcmAux=null;
////
////        }
////        catch (Exception e) {
////            blnRes = false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////    
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
////                case INT_TBL_DAT_COD_MAE:
////                    strMsg="Código maestro del item";
////                    break;
////                case INT_TBL_DAT_COD_SIS:
////                    strMsg="Código del item (Sistema)";
////                    break;
////                case INT_TBL_DAT_COD_ALT:
////                    strMsg="Código alterno del item";
////                    break;
////                case INT_TBL_DAT_COD_ALT_DOS:
////                    strMsg="Código alterno del item dos";
////                    break;
////                case INT_TBL_DAT_COD_ALT_3:
////                    strMsg="Código alterno 3 del item (Código GP Altiora)";
////                    break;
////                case INT_TBL_DAT_NOM_ITM:
////                    strMsg="Nombre del item";
////                    break;
////                case INT_TBL_DAT_CHK_SER:
////                    strMsg="Servicio";
////                    break;
////                case INT_TBL_DAT_DEC_UNI:
////                    strMsg="Unidad de medida";
////                    break;
////                case INT_TBL_DAT_PES_KGR:
////                    strMsg="Peso(Kg)";
////                    break;
////////                case INT_TBL_DAT_STK_CON:
////////                    intColSel=tblDat.columnAtPoint(evt.getPoint());
////////                    break;
////                case INT_TBL_DAT_COD_GEN://CHIQIUI
////                     strMsg="Código Generado";     
////                     break;
////                case INT_TBL_DAT_STK_CON:
////                     strMsg="Stock";
////                     break;
                default:
                    strMsg="";
                    break;
            }
            
                                                           
                        
                        
                        
                        
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    
        /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis = new Vector();    //Almacena los datos
            vecCabHis = new Vector(5);  //Almacena las cabeceras
            vecCabHis.clear();
            vecCabHis.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis = new ZafTblMod();
            objTblModHis.setHeader(vecCabHis);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis.setModel(objTblModHis);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis.setRowSelectionAllowed(true);
            tblHis.getTableHeader().setReorderingAllowed(false);
            tblHis.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumHis = new ZafColNumerada(tblHis, INT_TBL_DAT_HIS_LIN);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis = new ZafTblFilCab(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis);

            vecAux=new Vector();
            objTblModHis.setModoOperacion(objTblModHis.INT_TBL_EDI);
            vecAux.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblHis);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis);
            objTblCelRenChkHis=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis=new ZafTblCelEdiChk(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis);



            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


        /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            if(objParSis.getCodigoMenu()==2332){//bodegas
                vecCab.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "Stk.Cen.Dis.");
                vecCab.add(INT_TBL_DAT_DIS_CEN_DIS, "Dis.Cen.Dis.");
                vecCab.add(INT_TBL_DAT_SALD, "Saldo");                  
            }    
          
                
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            if(objParSis.getCodigoMenu()==2332){//bodegas
                tcmAux.getColumn(INT_TBL_DAT_STK_ACT_CEN_DIS).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_DAT_DIS_CEN_DIS).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_DAT_SALD).setPreferredWidth(70);                
                
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tcmAux.getColumn(INT_TBL_DAT_STK_ACT_CEN_DIS).setCellRenderer(objTblCelRenLbl);
                tcmAux.getColumn(INT_TBL_DAT_DIS_CEN_DIS).setCellRenderer(objTblCelRenLbl);
                tcmAux.getColumn(INT_TBL_DAT_SALD).setCellRenderer(objTblCelRenLbl);                     
                objTblCelRenLbl=null;
            }    
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);     
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);            
            
            
            
////            //Configurar JTable: Establecer la fila de cabecera.
////            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
////            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
              objTblPopMnu.setPegarEnabled(true);
////            
////            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
////            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
           
////            
////            //Configurar JTable: Establecer la fila de cabecera.
////            objTblFilCab = new ZafTblFilCab(tblDat);
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
////
////            //Configurar JTable: Editor de básqueda.
////            objTblBus=new ZafTblBus(tblDat);
////            objTblOrd=new ZafTblOrd(tblDat);
////

            //Libero los objetos auxiliares.
            tcmAux = null;

            intNumColFinModEst=objTblMod.getColumnCount();

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT_CEN_DIS, tblDat);  // JM 3/Mar/2023
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);  // JM 3/Mar/2023
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_ITM_DOS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_UNI_MED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT_CEN_DIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DIS_CEN_DIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SALD, tblDat);                     


                        tblDat.setRowSelectionAllowed(true);
                        tblDat.setColumnSelectionAllowed(false);            
 


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblDatTot()
    {
        boolean blnRes=true;
        try
        {
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);
            tblTotDat.setModel(objTblModTot);

            //Configurar JTable: Establecer tipo de selección.
            tblTotDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);            
            tblTotDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTot=new ZafTblFilCab(tblTotDat);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabTot);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            
            
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(150);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setResizable(false);
            


            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setResizable(false);


                                    
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++){
                tblTotDat.getColumnModel().getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tblTotDat.getColumnModel().getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tblTotDat.getColumnModel().getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tblTotDat.getColumnModel().getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotDat.getColumnModel().getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblTot=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            //spnFix.setHorizontalScrollBarPolicy(spnDat.getHorizontalScrollBarPolicy());
            spnTotDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotDat.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTot objTblColModLis=new ZafTblColModLisTot();
            tblDat.getColumnModel().addColumnModelListener(objTblColModLis);

            ZafTblColModLisTot objTblColModLisTot=new ZafTblColModLisTot();
            tblTotDat.getColumnModel().addColumnModelListener(objTblColModLisTot);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDat1=spnDat.getHorizontalScrollBar();
            barDatTot1=spnTotDat.getHorizontalScrollBar();
            
            barDatVer1=spnDat.getVerticalScrollBar();
            barFix1=spnFix.getVerticalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDat1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTot1.setValue(evt.getValue());
                }
            });
            //VERTICAL            
            barDatVer1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFix1.setValue(evt.getValue());
                }
            });
            spnTotDat.setColumnHeader(null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private class ZafTblColModLisTot implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotDat.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
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
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
    }


    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
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

    
private boolean ocultaColumnasPorFiltro()
   {
       boolean blnRes=false;
       int intNumFil=objTblMod.getRowCountTrue();  
       int indicadorS =0,contadorS =4,indicadorV =0,contadorV =2,indicadorM =0,contadorM =3;  
    if (chkSalInv.isSelected())
         validaMS=true;
    else
         validaMS=false;
    if (chkVec.isSelected())
         validaMV=true;
    else
         validaMV=false;
    if (chkMes.isSelected())
         validaMM=true;
    else
         validaMM=false;    
    for(int j=intNumColIniFec; j<intNumColFinFec;j++){
////        vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
        for (int ii=1 ;ii<=intNumColAdiFec;ii++)
        {
            indicadorS = (intNumColIniFec+ contadorS)-1;
            indicadorV = (intNumColIniFec+ contadorV)-1;
            indicadorM = (intNumColIniFec+ contadorM)-1;
            //PARA LO QUE ES ESL STOCK    
            if (validaMS)
            {
                if (j==indicadorS)
                {
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                    contadorS +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);                    

            }
            else
            {
                if (j==indicadorS)
                {
                    objTblMod.addSystemHiddenColumn(j, tblDat);                                
                    contadorS +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);                    
            }
            //PARA LO QUE ES EL VECES
            if (validaMV)
            {
                if (j==indicadorV)
                {
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                    contadorV +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);                    

            }
            else
            {
                if (j==indicadorV)
                {
                    objTblMod.addSystemHiddenColumn(j, tblDat);                                
                    contadorV +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);
            } 
            //PARA LO QUE ES EL MESES
            if (validaMM)
            {
                if (j==indicadorM)
                {
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                    contadorM +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);                    

            }
            else
            {
                if (j==indicadorM)
                {
                    objTblMod.addSystemHiddenColumn(j, tblDat);                                
                    contadorM +=4;                                        
////                                        break;
                }
////                else
////                    objTblMod.removeSystemHiddenColumn(j, tblDat);
            }             

        }
    }                    
       
////        if(chkSalInv.isSelected())
////        {
////            objTblMod.removeSystemHiddenColumn(intNumColAdiFec*4, tblDat);
////        }
////        else
////        {
////            objTblMod.addSystemHiddenColumn(intNumColAdiFec*4, tblDat);
////        }       
////       
       
       return blnRes;
   }    


    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() {
        boolean blnRes = true;
        try {
                //Listado de campos.
                ArrayList arlCam = new ArrayList();
                arlCam.add("a1.CodigoGP");
                arlCam.add("a1.NombreLargo");
                //Alias de los campos.
                ArrayList arlAli = new ArrayList();
                arlAli.add("Código Bodega");
                arlAli.add("Nombre de Bodega");
                //Ancho de las columnas.
                ArrayList arlAncCol = new ArrayList();
                arlAncCol.add("100");
                arlAncCol.add("334");
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT TRIM(locncode ) as CodigoGP, TRIM(LOCNDSCR) as NombreLargo \n";
                    strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV40700 as a1   \n";
                    strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp="+objParSis.getCodigoEmpresa()+" AND  TRIM(a1.locncode)=a2.tx_desCor  ) \n";
                    strSQL+=" WHERE INACTIVE = 0  AND locncode <> ''\n";
                }
                else{
                    strSQL = "";
                    strSQL += " SELECT  TRIM(locncode ) as CodigoGP, TRIM(LOCNDSCR) as NombreLargo \n";
                    strSQL += " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV40700 as a1 \n";
                    strSQL += " INNER JOIN tbm_bod as a2 ON (TRIM(a1.locncode)=a2.tx_desCor  ) \n";
                    strSQL += " INNER JOIN tbr_bodLocPrgUsr as a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod) \n";
                    strSQL += " WHERE a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL += " AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL += " AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                vcoBodDes = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
//                vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
    private boolean mostrarVenConBodDes(int intTipBus) {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodDes.buscar("a1.CodigoGP", txtCodBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(0);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtCodBodDes.setText(strCodBodDes);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodDes.buscar("a1.NombreLargo", txtNomBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(1);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtNomBodDes.setText(strNomBodDes);
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

    
    private boolean cargarAnios(){
        boolean blnRes=true;
        try{
//            System.out.println(">strConexion>" + objParSis.getStringConexion_Dynamics2021());
//            System.out.println(">Usuario>" + objParSis.getUsuarioBaseDatos_Dynamics2021());
//            System.out.println(">Clave>" + objParSis.getClaveBaseDatos_Dynamics2021());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT ne_ani";
                strSQL+=" FROM tbm_venMenInvBod ";
                strSQL+=" GROUP BY ne_ani";
                strSQL+=" ORDER BY ne_ani";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecRegHis=new Vector();
                    vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                    vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                    vecDatHis.add(vecRegHis);
                } 
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModHis.setData(vecDatHis);
                tblHis.setModel(objTblModHis);
                vecDatHis.clear();
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
    

    private boolean cargarAniosMeses(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT ne_ani, ne_mes";
                strSQL+=" FROM tbm_venMenInvBod";
                strSQL+=" GROUP BY ne_ani, ne_mes";
                strSQL+=" ORDER BY ne_ani, ne_mes";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecRegHis=new Vector();
                    vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                    vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                    switch(rst.getInt("ne_mes")){
                        case 1:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Enero");
                            break;
                        case 2:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Febrero");
                            break;
                        case 3:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Marzo");
                            break;
                        case 4:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Abril");
                            break;
                        case 5:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Mayo");
                            break;
                        case 6:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Junio");
                            break;
                        case 7:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Julio");
                            break;
                        case 8:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Agosto");
                            break;
                        case 9:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Septiembre");
                            break;
                        case 10:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Octubre");
                            break;
                        case 11:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Noviembre");
                            break;
                        case 12:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Diciembre");
                            break;
                        default:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                            break;

                    }
                    vecDatHis.add(vecRegHis);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModHis.setData(vecDatHis);
                tblHis.setModel(objTblModHis);
                vecDatHis.clear();
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.ITEMNMBR");
            arlCam.add("a1.ITEMNMBR");
            arlCam.add("a1.ITEMDESC");
            arlCam.add("a2.ITMTSHID");
            arlCam.add("a2.UOMSCHDL");
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
            strSQL="";
            strSQL+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, a1.ITEMTYPE,  \n";
            strSQL+="        a1.CURRCOST, TRIM(a1.ITMTSHID) as ITMTSHID, TRIM(a2.BASEUOFM) as UOMSCHDL, TRIM(a1.PRCLEVEL) as PRCLEVEL      \n";
            strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1   \n";
            strSQL+=" INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV40201 as a2 ON (a1.UOMSCHDL=a2.UOMSCHDL)   \n";
            strSQL+=" WHERE a1.INACTIVE = 0 AND a1.ITEMTYPE = 1 AND (TRIM(a1.ITEMNMBR) like '%I' OR TRIM(a1.ITEMNMBR) like '%S') \n";
            strSQL+=" ORDER BY a1.ITEMNMBR  \n";
            System.out.println("configurarVenConItm: " + strSQL);
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


   private boolean configurarColumnasAniosMeses(){
       boolean blnRes=true;
       try{
           if(con!=null){
               if(eliminaColumnasAdicionadasAnios()){
                   intNumColAdiFec=numeroColumnasAdicionar();
                   if(optHisAnu.isSelected()){
                       if(agregarColumnasAdicionadasAnios(intNumColAdiFec)){
                       }
                   }
                   else if(optHisMen.isSelected()){
                       if(agregarColumnasAdicionadasAniosMeses(intNumColAdiFec)){
                       }
                   }
////                       if (agregarColumnasSaldos())
////                       {
////                       }
                       
                   if(agregarColumnasAdicionadasTotales()){
                   }
                   if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo_Exceso()){
                       }
                       if(agregarColumnaAdicionadaObservacion()){
                       }
                       if(agregarColumnaAdicionadaReposicion()){                           
                       }
                       if(agregarColAdiCanPenRep()){
                       }
                   }
                   else{//POR EMPRESA
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo()){
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


   private boolean configurarColumnasSinHistorico(){
       boolean blnRes=true;
       intNumColAdiFec=0;
       try{
           if(con!=null){
               if(eliminaColumnasAdicionadasAnios()){
                   if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnaAdicionadaObservacion()){
                       }
                   }
                   else{//POR EMPRESA
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo()){
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





   private boolean eliminaColumnasAdicionadasAnios(){
        boolean blnRes=true;
        int intNumColTot=tblDat.getColumnCount();//obtiene el numero total de columnas en ese momento
        try{
            for (int i=(intNumColTot-1); i>=intNumColFinModEst; i--){
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


   private int numeroColumnasAdicionar(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd.clear();
       try{
           for(int i=0; i<objTblModHis.getRowCountTrue(); i++){
               strLin=objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd=new ArrayList();
                       arlRegAniAdd.add(INT_ARL_ANI, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd.add(INT_ARL_NUM_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd.add(INT_ARL_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd.add(INT_ARL_COL, "" );
                       arlDatAniAdd.add(arlRegAniAdd);
                   }
               }
           }
           System.out.println("ARREGLO TIENE: " + arlDatAniAdd.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }



    private boolean agregarColumnasAdicionadasAnios(int numeroColumnasAdicionar)//chiqui
    {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);

////        intNumColAdiFec=numeroColumnasAdicionar+1;
        intNumColAdiFec=numeroColumnasAdicionar;        
        String strAni="", strMes="";
        int p=0, f=-1;
        intNumColIniFec=intNumColFinModEst;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<(intNumColAdiFec*4); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Unidades":(p==1?"Veces":(p==2?"Meses":(p==3?"Stock":""))));
////                String strSubTit=(p==0?"Unidades":(p==1?"Veces":(p==2?"Meses":"")));                
                tbc=new javax.swing.table.TableColumn(intNumColIniFec+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);

                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                if(p==3){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLbl=null;
            intNumColFinFec=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }






    private boolean agregarColumnasAdicionadasAniosMeses(int numeroColumnasAdicionar)//chiqui
    {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        intNumColAdiFec=numeroColumnasAdicionar;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFec=intNumColFinModEst;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<(intNumColAdiFec*4); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Unidades":(p==1?"Veces":(p==2?"Meses":(p==3?"Stock":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFec+i);
                tbc.setHeaderValue("" + strSubTit);
////                if (p==1)
////                {
////                    if (chkVec.isSelected())
////                         tbc.setPreferredWidth(60);   
////                    else
////                        tbc.setPreferredWidth(0);   
////                }
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==3){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLbl=null;
            intNumColFinFec=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }


    
private boolean agregarColumnasSaldos()//chiqui1
{
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
////        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarFac=1;//
        intNumColIniMarFac=intNumColFinFec;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for(int i=0; i<intNumColAddMarFac; i++){
                String strSubTit="SaldosVtas";
                tbc=new javax.swing.table.TableColumn(intNumColIniMarFac+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
////                tbc.setCellEditor(objTblCelRenLbl);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
//////               if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
//////                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
//////                    objTblHeaColGrp.setHeight(16);
//////                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
//////                }
//////                objTblHeaColGrp.add(tbc);
//////
//////                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);


                
                
                if(i==0){
////                    objTblHeaColGrpTit=new ZafTblHeaColGrp("factor");//
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            
////            
////            objTblCelEdiTxtMarFacCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
////                }
////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    calcularCostoUnitarioFactor_Margen();
////                }
////
////            });
////            
            
            
            intNumColFinMarFac=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrp=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





   private boolean agregarColumnasAdicionadasTotales()
   {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAdiTot=3;
        intNumColIniTot=intNumColFinFec;
        

        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<intNumColAdiTot; i++){
                String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);

                

                if(intNumColAdiFec>1){
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                }
                else{
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);
                    tbc.setResizable(false);
                }                



                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinTot=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;

   }


   
   private boolean agregarColumnaStockActual(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkAct=2;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkAct=intNumColFinTot;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxt=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkAct; i++){
                String strSubTit=((i==0?"Stk.Act.":(i==1?"Dis.Act.":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniStkAct+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkAct=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }


   private boolean agregarColumnaStockMinimo_Exceso(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkMinExc=4;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkMinExc=intNumColFinProMen;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMin=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkMinExc; i++){
                String strSubTit=(i==0?"Fijo":(i==1?"Exceso":(i==2)?"Stk.Min.Sug.":"Obs.Stk.Min.Sug."));
                tbc=new javax.swing.table.TableColumn(intNumColIniStkMinExc+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtMin);
                objTblCelEdiTxtMin.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    String strValCel="";
                    int intFil=-1;
                    int intCol=-1;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFil=tblDat.getSelectedRow();
                        intCol=tblDat.getSelectedColumn();
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if((intNumColIniStkMinExc+3)==tblDat.getSelectedColumn()){//es la columna de observacion
                        }
                        else{
                            strValCel=objTblMod.getValueAt(intFil, intCol)==null?"":objTblMod.getValueAt(intFil, intCol).toString();
                            if(!strValCel.equals("")){
                                //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                                bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                                objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                                calcularMinimoMaximo();
                                calcularCantidadReponer();

                            }
                            else{
                                 objTblMod.setValueAt("", intFil, intCol);
                                 calcularMinimoMaximo();
                                 calcularCantidadReponer();
                            }
                        }

                    }

                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkMinExc=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }




   private boolean agregarColumnaStockMinimo(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkMinExc=1;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkMinExc=intNumColFinProMen;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMin=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkMinExc; i++){
                String strSubTit=(i==0?"Stk.Min.":"");
                tbc=new javax.swing.table.TableColumn(intNumColIniStkMinExc+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtMin);

                objTblCelEdiTxtMin.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

                    BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){

 
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                        else{
                            objTblMod.setValueAt(null, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }

                    }

                });



                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkMinExc=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }





   private boolean agregarColumnaAdicionadaObservacion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiObs=1;
        intNumColIniObs=intNumColFinStkMinExc;
        try{
            String strSubTit="Obs.Stk.Min.Sug";
            tbc=new javax.swing.table.TableColumn(intNumColIniObs);
            tbc.setHeaderValue("" + strSubTit);
            //Configurar JTable: Establecer el ancho de la columna.
            tbc.setPreferredWidth(30);
            objTblCelRenBut=new ZafTblCelRenBut();
            tbc.setCellRenderer(objTblCelRenBut);
            objCom32_01=new ZafCom32_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiBut= new ZafTblCelEdiButDlg(objCom32_01);
            tbc.setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCom32_01.setContenido(""+objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()-1)).toString());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objCom32_01.getContenido(), tblDat.getSelectedRow(), (tblDat.getSelectedColumn()-1));
                }
            });
            objTblMod.addColumn(tblDat, tbc);
            objTblCelRenBut=null;
            intNumColFinStkObs=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
   }






   private boolean agregarColumnasAdicionadasPromedioMensualVtas()
   {
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        strAux="";
        intNumColAdiProMen=2;
        intNumColIniProMen=intNumColFinStkAct;
        intColPromMan=intNumColIniProMen+1;
        
        try
        {
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPro=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiProMen; i++)
            {
                String strSubTit=(i==0?"Pro.Cal.":(i==1?"Pro.Men.Pres.":""));
                tbc=new javax.swing.table.TableColumn(intNumColIniProMen+i); 
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(100);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtPro);

                //<editor-fold defaultstate="collapsed" desc="/* Opción Copiar y Pegar Pro.Man. */">
                objTblMod.setColumnDataType(intNumColIniStkMinExc, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 1, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 2, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 5, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                
                objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
                {
                    public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                    {
                        if (objTblPopMnu.isClickTipoSeleccionSeleccionCelda() ) 
                        //if (objTblPopMnu.isClickTipoSeleccionSeleccionCelda() && tblDat.isColumnSelected(intColPromMan)) 
                        {
                            System.out.println(" HABILITAR TIPO CELDA:" + intColPromMan);
                            objTblPopMnu.setPegarEnabled(true);
                            System.out.println(" HABILITAR PEGAR:" + intColPromMan);
                        }                
                        if (objTblPopMnu.isClickPegar()) 
                        {
                            System.out.println(" CLICK PEGAR:" + intColPromMan); //Pro.Man.
                            int intFilSel[], i;
                            String strFij, strStkExc, strStkMin, strObsStk, strNumMes;
                            intFilSel = tblDat.getSelectedRows();

                            for (i = 0; i < intFilSel.length; i++) 
                            {
                                strFij = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc).toString();
                                strStkExc = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 1).toString();
                                strStkMin = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 2).toString();
                                strObsStk = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 3).toString();
                                strNumMes = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 5).toString();

                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc); //Fijo
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 1); //Exceso
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 2); //Stk.Min.
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 3); //Obs.Stk.
                                //objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 5); //Num.Mes.

                                System.out.println("PEGAR=> Fijo.:" + strFij + " Stk.Exc.:" + strStkExc + " Stk.Min.:" + strStkMin + " Obs.Stk.:" + strObsStk + " Num.Mes.:" + strNumMes);
                            }
                        }
             
                    }
                }
                );
                //</editor-fold>
                
                objTblCelEdiTxtPro.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    String strValCel="";
                    int intFil=-1;
                    int intCol=-1;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFil=tblDat.getSelectedRow();
                        intCol=tblDat.getSelectedColumn();
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) { 
                        strValCel=objTblMod.getValueAt(intFil, intCol)==null?"":objTblMod.getValueAt(intFil, intCol).toString();
                        if(!strValCel.equals("")){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                calcularMinimoMaximo();
                                calcularCantidadReponer();
                            }
                        }
                        else{
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            calcularMinimoMaximo();
                            calcularCantidadReponer();
                        }



                    }
                });


                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pro.Men.Vta.");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinProMen=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }



   private boolean agregarColumnaAdicionadaReposicion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        String strSubTit="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAdiRep=7;  // JM 15/Mar/2023
        intNumColIniStkRep=intNumColFinStkObs;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblGP=new ZafTblCelRenLbl();
            objTblCelRenLblGP.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGP.setTipoFormato(objTblCelRenLblGP.INT_FOR_NUM);
            objTblCelRenLblGP.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblGP.setBackground(Color.red);
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiRep; i++){
//                String strSubTit=(i==0?"Núm,Mes.Rep.":(i==1?"Mínimo":(i==2?"Máximo":(i==3?"Can.Rep.":"Val.Uti.Cal.Min.Max")))); // JM 15/Mar/2023 
                switch(i){
                    case 0:
                        strSubTit = "Núm,Mes.Rep.";
                        break;
                    case 1:
                        strSubTit = "Mínimo"; 
                        break;
                    case 2:
                        strSubTit = "Máximo";
                        break;
                    case 3:
                        strSubTit = "GP: Mínimo"; 
                        break;
                    case 4:
                        strSubTit = "GP: Máximo"; 
                        break;    
                    case 5:
                        strSubTit = "Can.Rep."; 
                        break;    
                    case 6:
                        strSubTit = "Val.Uti.Cal.Min.Max"; 
                        break;     
                } // JM 15/Mar/2023
                

                tbc=new javax.swing.table.TableColumn(intNumColIniStkRep+i);
                tbc.setHeaderValue("" + strSubTit);
                if(i==5){
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);
                    tbc.setResizable(false);
                    tbc.setCellRenderer(objTblCelRenLbl);
                    tbc.setCellEditor(objTblCelEdiTxt);
                }
                else if(i==3 || i==4){
                    tbc.setCellRenderer(objTblCelRenLblGP);
                    tbc.setCellEditor(objTblCelEdiTxt);
                }
                else{
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLbl);
                    tbc.setCellEditor(objTblCelEdiTxt);
                }
                

                objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(intNumColIniStkRep==tblDat.getSelectedColumn()){//es la columna de observacion
                            calcularMinimoMaximo();
                            calcularCantidadReponer();
                        }
                    }
                });



                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Reposición");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinStkRep=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   
   
   private boolean agregarColAdiCanPenRep(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiCanPenRep=2;
        intNumColIniCanPenRep=intNumColFinStkRep;
        
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (int i=0; i<intNumColAdiCanPenRep; i++){
                String strSubTit=(i==0?"Can.Pen.Rep.":"Can.Dis.Rep.");
                tbc=new javax.swing.table.TableColumn(intNumColIniCanPenRep+i);
                tbc.setHeaderValue("" + strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
//                if(i==0){
//                    tbc.setWidth(0);
//                    tbc.setMaxWidth(0);
//                    tbc.setMinWidth(0);
//                    tbc.setPreferredWidth(0);
//                    tbc.setResizable(false);
//                }
//                else{
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(70);
//                }                
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinCanPenRep=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
   
   
   
   
   
   

    private boolean consultarRegAniMesGrp()//modificando
    {
        boolean blnRes=true;
        int intAni=0;
        int intMes=0;
        int intAniAnt=0;
        int intMesAnt=0;        
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        
        BigDecimal bdeStkActCal=new BigDecimal("0");
        BigDecimal bdeRepMaxCal=new BigDecimal("0");
        BigDecimal bdeRepMinCal=new BigDecimal("0");
        BigDecimal bdeStkExcCal=new BigDecimal("0");
        
        BigDecimal bdeStkDspBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMaxBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMinBodDstCal=new BigDecimal("0");
        BigDecimal bdeStkExcBodDstCal=new BigDecimal("0");
        BigDecimal bdeExiCanDspRepCal=new BigDecimal("0");
        
        java.sql.Connection conLoc;
        
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc!=null){
                stm=conLoc.createStatement();
                strAux="";
                strFilItm="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;

                    
//////                    strAux+="	LEFT OUTER JOIN( \n";
//////                    strAux+="         SELECT a4.tx_itm, "+objUti.codificar(txtCodBodDes.getText())+" AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen, \n";
//////                    strAux+="                SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes \n";
//////                    strAux+="         FROM( \n";
//////                    strAux+="              SELECT a4.tx_itm, a4.tx_bod, SUM(a4.nd_uniVen) AS nd_uniVen, \n";
//////                    strAux+="                     SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes \n";
//////                    strAux+="              FROM tbm_venMenInvBod AS a4 \n";
//////                    strAux+="              INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS a1 ON (a4.tx_itm=a1.ITEMNMBR) \n";
//////                    strAux+="              WHERE a4.tx_bod=" +objUti.codificar(txtCodBodDes.getText())+" \n";
//////                    strAux+="                     AND a4.ne_ani=" + intAni + " \n";
//////                    strAux+="                     AND a4.ne_mes=" + intMes + " \n";
//////                    strAux+="                     " + strFilItm + " \n";
//////                    strAux+="              GROUP BY a4.tx_itm, a4.tx_bod, a4.ne_ani, a4.ne_mes \n";
//////                    strAux+="          ) AS a4 \n";
//////                    strAux+="          GROUP BY a4.tx_itm, a4.ne_ani, a4.ne_mes \n";
//////                    strAux+=" 	) AS b" + (j+2) + "";
//////                    strAux+=" ON b1.LOCNCODE=b" + (j+2) + ".co_bod AND b1.ITEMNMBR=b" + (j+2) + ".tx_itm \n";
//////                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen \n" ;
//////                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec \n" ;
//////                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes \n" ;
                    /////////////////////////////////////////////////
                    strAux+="	LEFT OUTER JOIN( \n";
                    strAux+="         SELECT a1.ITEMNMBR , "+objUti.codificar(txtCodBodDes.getText())+" AS tx_bod,a4.nd_uniVen, a4.ne_numVec,a4.ne_mes, isnull(secun.totalSaldo,0) as totalSaldo,secun.mes, secun.anio  ,a4.ne_numMes\n";
                    strAux+="          FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS a1 (NOLOCK)\n";
                    strAux+="          LEFT JOIN  ( \n";         
                    strAux+="                       SELECT ne_ani,ne_mes, a4.tx_itm,"+objUti.codificar(txtCodBodDes.getText())+" AS tx_bod,SUM(a4.nd_uniVen) AS nd_uniVen,SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes \n";                    
                    strAux+="                       FROM tbm_venMenInvBod as a4 (NOLOCK) \n";      
                    strAux+="                       WHERE  a4.tx_bod =" +objUti.codificar(txtCodBodDes.getText())+"  \n";  
                    strAux+="                              AND a4.ne_ani=" + intAni + " \n";
                    strAux+="                              AND a4.ne_mes=" + intMes + " \n";                    
                    strAux+="                       GROUP BY  a4.tx_itm, a4.tx_bod, a4.ne_ani,	a4.ne_mes \n";     
                    strAux+="                      ) AS a4  ON a4.tx_itm = a1.ITEMNMBR \n";     
                    strAux+="          LEFT JOIN  ( \n"; 
                    strAux+="                       SELECT SUM(nd_saluni) AS totalSaldo,co_itm, "+objUti.codificar(txtCodBodDes.getText())+" AS co_bod,LEFT(co_per, 4) AS anio,CONVERT(INT, RIGHT(co_per, 2)) AS mes \n";                    
                    strAux+="                       FROM tbm_salmeninvbod (NOLOCK)\n";      
                    strAux+="                       WHERE co_bod=" +objUti.codificar(txtCodBodDes.getText())+"  \n";                     
                    strAux+="                             AND LEFT(co_per, 4)=" + intAniAnt + " \n";
                    strAux+="                             AND RIGHT(co_per, 2)=" + intMesAnt + " \n";                    
                    strAux+="                       GROUP BY co_itm, co_bod, co_per \n";   
                    strAux+="                      ) AS secun  ON (a1.ITEMNMBR = secun.co_itm ) ) AS  b" + (j+2) + "\n";   
                    strAux+="                           ON b1.LOCNCODE=b" + (j+2) + ".tx_bod AND b1.ITEMNMBR=b" + (j+2) + ".ITEMNMBR \n";                    
                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen \n" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec \n" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes \n" ;
                    strCamSel+=" , b" + (j+2) + ".totalSaldo AS " + "b" + (j+2) + "_totalSaldo \n" ; 

                    
                    
                    
                }
                
                
                strAux+=" WHERE b1.INACTIVE = 0 AND b1.ITEMTYPE = 1 AND (b1.ITEMNMBR LIKE '%I' OR b1.ITEMNMBR LIKE '%S') \n";
                strAux+=" ORDER BY b1.ITEMNMBR";
                
 
                
                strSQL="";
                strSQL+=" SELECT isnull(b1.totalSaldo,0) as totalSaldo,TRIM(b1.ITEMNMBR) as ITEMNMBR, TRIM(b1.ITMGEDSC) as ITMGEDSC, TRIM(b1.ITEMDESC) as ITEMDESC, \n";
                strSQL+="        '' as  tx_nomItm, TRIM(b1.UOMSCHDL) as UOMSCHDL, b1.StockInmaconsa, b1.StockInmaconsa as nd_canDisCenDis ";
                strSQL+="       "+strCamSel+", 0, 0, 0, b1.Stock,  b1.Stock as nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, \n";
                strSQL+="        b1.nd_stkExc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_nummesrep, b1.nd_repmin, b1.nd_repmax, \n";
                strSQL+="        b1.nd_stkFij, b1.nd_stkExcBodCenDis,b1.nd_repMinBodCenDis, b1.nd_repMaxBodCenDis, 0 as co_emp ,b1.totalSaldo,b1.codZaf \n";
                strSQL+=" FROM( \n"; 
                strSQL+=" 	SELECT a6.totalSaldo as totalSaldo,a1.ITEMNMBR, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL, a1.INACTIVE, a1.ITEMTYPE, a2.LOCNCODE, a2.ORDRPNTQTY as nd_stkMin, \n";
                strSQL+="              vw2.DISPONIBLE as StockInmaconsa, vw1.DISPONIBLE as Stock,  \n";
                strSQL+=" 	       a2.ORDRPNTQTY as nd_repmin, a2.ORDRUPTOLVL as nd_repmax, a5.nd_stkexc AS nd_stkExcBodCenDis,     \n";
                strSQL+="              a3.ORDRPNTQTY AS nd_repMinBodCenDis, a3.ORDRUPTOLVL AS nd_repMaxBodCenDis,a4.nd_stkFij, a4.nd_stkExc, a4.nd_stkminsug, a4.tx_obsstkminsug, a4.nd_nummesrep, a4.nd_promenvtaman,trim(a1.ITMSHNAM) as codZaf       \n";
                strSQL+=" 	FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1  (NOLOCK) \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 AS a2 (NOLOCK) ON (a1.ITEMNMBR=a2.ITEMNMBR) \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.vw_consulta_disponible_transito as vw1 (NOLOCK) ON (a2.LOCNCODE=vw1.LOCNCODE AND a2.ITEMNMBR=vw1.ITEMNMBR)  \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 AS a3 (NOLOCK) ON (a2.ITEMNMBR=a3.ITEMNMBR AND a3.LOCNCODE=" +objUti.codificar(txtCodBodDes.getText())+") \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.vw_consulta_disponible_transito as vw2 (NOLOCK) ON (a3.LOCNCODE=vw2.LOCNCODE AND a3.ITEMNMBR=vw2.ITEMNMBR) \n";
                strSQL+="       INNER JOIN Dynamics2021.dbo.tbm_invBod as a4 (NOLOCK) ON (a2.LOCNCODE=a4.tx_bod AND a2.ITEMNMBR=a4.tx_itm) \n";
                strSQL+="       INNER JOIN Dynamics2021.dbo.tbm_invBod as a5 (NOLOCK) ON (a3.LOCNCODE=a5.tx_bod AND a3.ITEMNMBR=a5.tx_itm) \n";                
                

                strSQL+="       left join ( \n";
                strSQL+="       select sum (nd_saluni) as totalSaldo,co_itm \n";
                strSQL+="       from tbm_salmeninvbod  (NOLOCK)\n";
                strSQL+="       where LEFT(co_per, 4) like '"+ intAniAnt +"% "+intMesAnt+"' \n";
                strSQL+="       group by co_itm) as a6 on (a1.ITEMNMBR = a6.co_itm) \n";
                strSQL+="       INNER JOIN Dynamics2021.dbo.tbm_invBod as a7 (NOLOCK) ON (a3.LOCNCODE=a7.tx_bod AND a3.ITEMNMBR=a7.tx_itm) \n";
                
                
                
                
                strSQL+="       WHERE a2.RCRDTYPE = 2 AND a2.LOCNCODE=" +objUti.codificar(txtCodBodDes.getText())+"  \n";
                strSQL+="            "+strFilItm+" \n";
                strSQL+=" ) AS b1 \n";
                strSQL+="" + strAux;
                
                
                System.out.println("consultarRegAniMesGrp: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                
               
                
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("ITEMNMBR"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("ITEMNMBR"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("ITMGEDSC")==null?"":rst.getString("ITMGEDSC"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("ITEMDESC"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("UOMSCHDL")==null?"":rst.getString("UOMSCHDL"));
                        vecReg.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "" + rst.getString("StockInmaconsa"));
                        vecReg.add(INT_TBL_DAT_DIS_CEN_DIS,     "" + rst.getString("StockInmaconsa")); 
                        vecReg.add(INT_TBL_DAT_SALD,     "" + rst.getString("totalSaldo"));   
                        
                       
                         
////                         int indicadorS =0,contadorS =4,indicadorV =0,contadorV =2;
                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
////////                            for (int ii=1 ;ii<=intNumColAdiFec;ii++)
////////                            {
////////                                indicadorS = (intNumColIniFec+ contadorS)-1;
////////                                indicadorV = (intNumColIniFec+ contadorV)-1;
////////                                //PARA LO QUE ES ESL STOCK    
////////                                if (validaMS)
////////                                {
////////                                    if (j==indicadorS)
////////                                    {
////////                                        objTblMod.removeSystemHiddenColumn(j, tblDat);
////////                                        contadorS +=4;                                        
////////////                                        break;
////////                                    }
////////                                        
////////                                }
////////                                else
////////                                {
////////                                    if (j==indicadorS)
////////                                    {
////////                                        objTblMod.addSystemHiddenColumn(j, tblDat);                                
////////                                        contadorS +=4;                                        
////////////                                        break;
////////                                    }
////////                                }
////////                                //PARA LO QUE ES EL VECES
////////                                if (validaMV)
////////                                {
////////                                    if (j==indicadorV)
////////                                    {
////////                                        objTblMod.removeSystemHiddenColumn(j, tblDat);
////////                                        contadorV +=4;                                        
////////////                                        break;
////////                                    }
////////                                        
////////                                }
////////                                else
////////                                {
////////                                    if (j==indicadorV)
////////                                    {
////////                                        objTblMod.addSystemHiddenColumn(j, tblDat);                                
////////                                        contadorV +=4;                                        
////////////                                        break;
////////                                    }
////////                                } 
////////                                
////////                            }
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     rst.getObject("nd_proMenVtaMan")==null?"":rst.getString("nd_proMenVtaMan")); 
                            else
                                vecReg.add(j,     "");
                        }
 
                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            if(j==intNumColIniStkMinExc){ // JM 2/Marzo/2023
                                vecReg.add(j,   rst.getObject("nd_stkFij")==null?"":rst.getString("nd_stkFij"));   // JM 2/Marzo/2023
                            } // JM 2/Marzo/2023
                            else{ // JM 2/Marzo/2023 
                                vecReg.add(j,     rst.getObject(j)==null?"": rst.getString(j));
                            } // JM 2/Marzo/2023
                            
                        }

                        //observacion - boton
                        vecReg.add(intNumColIniObs,     "");

                        //reposicion
                        for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                            if(j==intNumColIniStkRep)
                                vecReg.add(j,     rst.getObject("nd_nummesrep")==null?"":rst.getObject("nd_nummesrep"));
                            else if(j==(intNumColIniStkRep+1))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+2))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else if(j==(intNumColIniStkRep+3))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+4))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else
                                vecReg.add(j,     "");
                        }
                        //Bodega destino                        
                        bdeStkActCal=new BigDecimal(rst.getObject("Stock")==null?"0":(rst.getString("Stock").equals("")?"0":rst.getString("Stock")));
                        bdeRepMaxCal=new BigDecimal(rst.getObject("nd_repMax")==null?"0":(rst.getString("nd_repMax").equals("")?"0":rst.getString("nd_repMax")));
                        bdeRepMinCal=new BigDecimal(rst.getObject("nd_repMin")==null?"0":(rst.getString("nd_repMin").equals("")?"0":rst.getString("nd_repMin")));
                        bdeStkExcCal=new BigDecimal(rst.getObject("nd_stkExc")==null?"0":(rst.getString("nd_stkExc").equals("")?"0":rst.getString("nd_stkExc")));
                        
                        //bodega centro distribucion
                        bdeStkDspBodDstCal=new BigDecimal(rst.getObject("StockInmaconsa")==null?"0":(rst.getString("StockInmaconsa").equals("")?"0":rst.getString("StockInmaconsa")));
                        bdeRepMaxBodDstCal=new BigDecimal(rst.getObject("nd_repMaxBodCenDis")==null?"0":(rst.getString("nd_repMaxBodCenDis").equals("")?"0":rst.getString("nd_repMaxBodCenDis")));
                        bdeStkExcBodDstCal=new BigDecimal(rst.getObject("nd_stkExcBodCenDis")==null?"0":(rst.getString("nd_stkExcBodCenDis").equals("")?"0":rst.getString("nd_stkExcBodCenDis")));
                        bdeRepMinBodDstCal=new BigDecimal(rst.getObject("nd_repMinBodCenDis")==null?"0":(rst.getString("nd_repMinBodCenDis").equals("")?"0":rst.getString("nd_repMinBodCenDis")));
                        
                        //bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMaxBodDstCal.add(bdeStkExcBodDstCal)));
                        bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMinBodDstCal.add(bdeStkExcBodDstCal)));
                        
                        //cantidad pendiente de reponer
                        for(int j=intNumColIniCanPenRep; j<intNumColFinCanPenRep;j++){
                            if((bdeRepMinCal.subtract(bdeStkActCal)).compareTo(new BigDecimal("0"))>0){ //el resultado debe ser > 0
                                if(j==intNumColIniCanPenRep)
                                    vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                else{
                                    if(bdeExiCanDspRepCal.compareTo(new BigDecimal("0"))>0)//EL RESULTADO ES >0
                                    {
                                        if(bdeExiCanDspRepCal.compareTo((bdeRepMaxCal.subtract(bdeStkActCal)))>0) //el resultado debe ser > 0
                                            vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                        else
                                            vecReg.add(j,     bdeExiCanDspRepCal);
                                    }
                                    else
                                        vecReg.add(j,     "0");
                                }//can.dis.rep
                            }
                            else
                                vecReg.add(j,     "0");
                        }
                        System.out.println("++++ " + vecReg.toString());
                        vecDat.add(vecReg);
                        
                        //para cargar los datos en la tabla fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_FIX_COD_MAE,     "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_SIS,     "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT, "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_DOS,"" + rst.getString("ITMGEDSC"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_3,"" + rst.getString("codZaf"));                        
                        vecRegFix.add(INT_TBL_FIX_NOM_ITM,     "" + rst.getString("ITEMDESC"));
                        vecRegFix.add(INT_TBL_FIX_UNI_MED,     "" + rst.getString("UOMSCHDL"));
                        vecRegFix.add(INT_TBL_FIX_DIS_CEN_DIS,     "" + rst.getDouble("StockInmaconsa"));                        
////                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("UOMSCHDL"));
//////////                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" +0);                        
////                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + 0);                        
////                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + "");                        
////                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));
                        
////                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
////                        if(strAux.equals("S"))
////                            vecRegFix.setElementAt(new Boolean(false),INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                        
                        vecDatFix.add(vecRegFix);                        
                      
                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                conLoc.close();
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                ocultaColumnasPorFiltro();


                //para hacer editables las columnas
                if(objParSis.getCodigoUsuario()==1){
                    //minimo, ecexso, sugerido, observacion
                    for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                        vecAux.add("" + j);
                    }
                    //boton de observacion
                    vecAux.add(""+intNumColIniObs);
                    //promedio manual
                    for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                        if(j==(intNumColIniProMen+1))
                            vecAux.add("" + j);
                    }
                    for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                        if(j>intNumColIniStkRep+2)
                            vecAux.add("" + j);
                    }
                     
                }
                else{
                    if(objParSis.getCodigoMenu()==2332){//bodegas
                        if(objPerUsr.isOpcionEnabled(2334)){//guardar
                            //minimo, ecexso, sugerido, observacion
                            for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                                vecAux.add("" + j);
                            }
                            //boton de observacion
                            vecAux.add(""+intNumColIniObs);
                            //promedio manual
                            for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                                if(j==(intNumColIniProMen+1))
                                    vecAux.add("" + j);
                            }
                            for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                                if(j>intNumColIniStkRep+2)
                                    vecAux.add("" + j);
                            }
                              
                        }
                    }
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                //Asignar vectores al modelo.
                //tabla fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
                
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Ya no se usa JM 15/Marzo/2023
     * @return
     * @deprecated
     */
    @Deprecated
    private boolean consultarRegAniMesEmp(){
        boolean blnRes=true;
        int intAni=0;
        int intMes=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intNumColAdiFin=0;        
        try{
            if(con!=null){
                if(intNumColAdiFec>1)
                    intNumColAdiFin=6;
                else
                    intNumColAdiFin=3;
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    strAux+="		LEFT OUTER JOIN(";
                    
                    
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + "  AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     AND a4.ne_mes=" + intMes + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes";
                    strAux+="                     ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani, a4.ne_mes";
                    strAux+="               ORDER BY a4.co_itm, a4.ne_mes";
                    
                    
                    
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes" ;

                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T','O') AND (b1.tx_codAlt LIKE '%S' OR b1.tx_codAlt LIKE '%L')";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor";
                strSQL+="            " + strCamSel + ", 0, 0, 0 , b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2,  a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_promenvtaman, a3.nd_stkAct, a3.nd_canDis";
                strSQL+=" 		FROM     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";

                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }

                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";


                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }

                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println(" consultarRegAniMesEmp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                  
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        
                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     "" + (rst.getObject("nd_proMenVtaMan")==null?"0":(rst.getString("nd_proMenVtaMan").equals("")?"0":rst.getString("nd_proMenVtaMan")))); 
                            else
                                vecReg.add(j,     "0");
                        }


                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }



                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
//                        lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                if(objParSis.getCodigoUsuario()==1){
                    //promedio manual
                    vecAux.add("" + (intNumColIniProMen+1));
                    //minimo
                    vecAux.add("" + intNumColIniStkMinExc);
                }
                else{
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }

                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);


                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
     

    private int getRegHistoricoSeleccionados(){
        int intNumChkSel=0;
        String strLin="";
        for(int p=0; p<objTblModHis.getRowCountTrue();p++){
            strLin=objTblModHis.getValueAt(p, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(p, INT_TBL_DAT_HIS_LIN).toString();
            if(strLin.equals("M")){
                if(objTblModHis.isChecked(p, INT_TBL_DAT_HIS_CHK)){
                    intNumChkSel++;
                }
            }
        }
        return intNumChkSel;
    }

    

    private boolean consultarRegAniGrp() //chiqui
    {
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        boolean validaM=false;
        BigDecimal bdeStkActCal=new BigDecimal("0");
        BigDecimal bdeRepMaxCal=new BigDecimal("0");
        BigDecimal bdeRepMinCal=new BigDecimal("0");
        BigDecimal bdeStkExcCal=new BigDecimal("0");
        
        BigDecimal bdeStkDspBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMaxBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMinBodDstCal=new BigDecimal("0");
        BigDecimal bdeStkExcBodDstCal=new BigDecimal("0");
        BigDecimal bdeExiCanDspRepCal=new BigDecimal("0");
        
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc!=null){
                
                stm=conLoc.createStatement();
                strAux="";
                strFilItm="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                for(int j=0; j<arlDatAniAdd.size(); j++){       
////                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
//// 
////                    strAux+="	LEFT OUTER JOIN( \n";
////                    strAux+="         SELECT a4.tx_itm, "+objUti.codificar(txtCodBodDes.getText())+" AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen, \n";
////                    strAux+="                SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani  \n";
////                    strAux+="         FROM( \n";
////                    strAux+="              SELECT a4.tx_itm, a4.tx_bod, SUM(a4.nd_uniVen) AS nd_uniVen, \n";
////                    strAux+="                     SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani \n";
////                    strAux+="              FROM tbm_venMenInvBod AS a4 \n";
////                    strAux+="              INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS a1 ON (a4.tx_itm=a1.ITEMNMBR) \n";
////                    strAux+="              WHERE a4.tx_bod=" +objUti.codificar(txtCodBodDes.getText())+" \n";
////                    strAux+="                     AND a4.ne_ani=" + intAni + " \n";
////                    strAux+="                     " + strFilItm + " \n";
////                    strAux+="              GROUP BY a4.tx_itm, a4.tx_bod, a4.ne_ani  \n";
////                    strAux+="          ) AS a4 \n";
////                    strAux+="          GROUP BY a4.tx_itm, a4.ne_ani  \n";
////                    strAux+=" 	) AS b" + (j+2) + "";
////                    strAux+=" ON b1.LOCNCODE=b" + (j+2) + ".co_bod AND b1.ITEMNMBR=b" + (j+2) + ".tx_itm \n";
////                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen \n" ;
////                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec \n" ;
////                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes \n" ;

                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
 
                    strAux+="	LEFT OUTER JOIN( \n";
                    strAux+="         SELECT a1.ITEMNMBR , "+objUti.codificar(txtCodBodDes.getText())+" AS tx_bod,a4.nd_uniVen, a4.ne_numVec,a4.ne_numMes, secun.totalSaldo \n";
                    strAux+="          FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS a1 (NOLOCK)\n";
                    strAux+="          INNER JOIN  ( \n";         
                    strAux+="                       SELECT a4.tx_itm, "+objUti.codificar(txtCodBodDes.getText())+" AS tx_bod, SUM(a4.nd_uniVen) AS nd_uniVen,SUM(a4.ne_numVec) AS ne_numVec, count (a4.ne_mes) as ne_numMes \n";                    
                    strAux+="                       FROM tbm_venMenInvBod as a4 (NOLOCK) \n";      
                    strAux+="                       WHERE  a4.tx_bod =" +objUti.codificar(txtCodBodDes.getText())+"  \n";  
                    strAux+="                              AND a4.ne_ani=" + intAni + " \n";
                    strAux+="                       GROUP BY  a4.tx_itm, a4.tx_bod \n";     
                    strAux+="                      ) AS a4  ON a4.tx_itm = a1.ITEMNMBR \n";     
                    strAux+="          LEFT JOIN  ( \n"; 
                    strAux+="                       SELECT SUM(nd_saluni) AS totalSaldo,co_itm, "+objUti.codificar(txtCodBodDes.getText())+" AS co_bod,LEFT(co_per, 4) AS anio \n";                    
                    strAux+="                       FROM tbm_salmeninvbod (NOLOCK)\n";      
                    strAux+="                       WHERE co_bod=" +objUti.codificar(txtCodBodDes.getText())+"  \n";                     
                    strAux+="                             AND LEFT(co_per, 4)=" + intAni + " \n"; 
                    strAux+="                       GROUP BY  co_itm, co_bod, LEFT(co_per, 4) \n";   
                    strAux+="                      ) AS secun  ON (a4.tx_itm = secun.co_itm AND a4.tx_bod = secun.co_bod ) ) AS  b" + (j+2) + "\n";   
                    strAux+="                           ON b1.LOCNCODE=b" + (j+2) + ".tx_bod AND b1.ITEMNMBR=b" + (j+2) + ".ITEMNMBR \n";                    
                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen \n" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec \n" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes \n" ;
                    strCamSel+=" , b" + (j+2) + ".totalSaldo AS " + "b" + (j+2) + "_totalSaldo \n" ;                    
                    
                    
                    
                    
////                    
////                    strAux+="                SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani  \n";
////                    strAux+="         FROM( \n";
////                    strAux+="              SELECT a4.tx_itm, a4.tx_bod, SUM(a4.nd_uniVen) AS nd_uniVen, \n";
////                    strAux+="                     SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani \n";
////                    strAux+="              FROM tbm_venMenInvBod AS a4 \n";
////                    strAux+="              INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS a1 ON (a4.tx_itm=a1.ITEMNMBR) \n";
////                    strAux+="              WHERE a4.tx_bod=" +objUti.codificar(txtCodBodDes.getText())+" \n";
////                    strAux+="                     AND a4.ne_ani=" + intAni + " \n";
////                    strAux+="                     " + strFilItm + " \n";
////                    strAux+="              GROUP BY a4.tx_itm, a4.tx_bod, a4.ne_ani  \n";
////                    strAux+="          ) AS a4 \n";
////                    strAux+="          GROUP BY a4.tx_itm, a4.ne_ani  \n";
////                    strAux+=" 	) AS b" + (j+2) + "";
////                    strAux+=" ON b1.LOCNCODE=b" + (j+2) + ".co_bod AND b1.ITEMNMBR=b" + (j+2) + ".tx_itm \n";
////                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen \n" ;
////                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec \n" ;
////                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes \n" ;
                    
                    
                }
                
 
                strAux+=" WHERE b1.INACTIVE = 0 AND b1.ITEMTYPE = 1 AND (b1.ITEMNMBR LIKE '%I' OR b1.ITEMNMBR LIKE '%S') \n";
                strAux+=" ORDER BY b1.ITEMNMBR";

                strSQL="";
                strSQL+=" SELECT isnull(b1.totalSaldo,0) as totalSaldo,TRIM(b1.ITEMNMBR) as ITEMNMBR, TRIM(b1.ITMGEDSC) as ITMGEDSC, TRIM(b1.ITEMDESC) as ITEMDESC, \n";
                strSQL+="        '' as  tx_nomItm, TRIM(b1.UOMSCHDL) as UOMSCHDL, b1.StockInmaconsa, b1.StockInmaconsa as nd_canDisCenDis ";
                strSQL+="       "+strCamSel+", 0, 0, 0, b1.Stock,  b1.Stock as nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, \n";
                strSQL+="        b1.nd_stkExc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_nummesrep, b1.nd_repmin, b1.nd_repmax, \n";
                strSQL+="        b1.nd_stkFij,b1.nd_stkExcBodCenDis,b1.nd_repMinBodCenDis, b1.nd_repMaxBodCenDis, 0 as co_emp,b1.totalSaldo,b1.codZaf  \n";
                strSQL+=" FROM( \n"; 
                strSQL+=" 	SELECT a6.totalSaldo as totalSaldo,a1.ITEMNMBR, a1.ITMGEDSC, a1.ITEMDESC, a1.UOMSCHDL, a1.INACTIVE, a1.ITEMTYPE, a2.LOCNCODE, a2.ORDRPNTQTY as nd_stkMin, \n";
                strSQL+="              vw2.DISPONIBLE as StockInmaconsa, vw1.DISPONIBLE as Stock,  \n";
                strSQL+=" 	       a2.ORDRPNTQTY as nd_repmin, a2.ORDRUPTOLVL as nd_repmax, a5.nd_stkexc AS nd_stkExcBodCenDis,     \n";
                strSQL+="              a3.ORDRPNTQTY AS nd_repMinBodCenDis, a3.ORDRUPTOLVL AS nd_repMaxBodCenDis,a4.nd_stkFij, a4.nd_stkExc, a4.nd_stkminsug, a4.tx_obsstkminsug, a4.nd_nummesrep, a4.nd_promenvtaman,trim(a1.ITMSHNAM) as codZaf         \n";
                strSQL+=" 	FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1  (NOLOCK) \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 AS a2 (NOLOCK) ON (a1.ITEMNMBR=a2.ITEMNMBR) \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.vw_consulta_disponible_transito as vw1 (NOLOCK) ON (a2.LOCNCODE=vw1.LOCNCODE AND a2.ITEMNMBR=vw1.ITEMNMBR)  \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 AS a3 (NOLOCK) ON (a2.ITEMNMBR=a3.ITEMNMBR AND a3.LOCNCODE='INMACONSA') \n";
                strSQL+="       INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.vw_consulta_disponible_transito as vw2 (NOLOCK) ON (a3.LOCNCODE=vw2.LOCNCODE AND a3.ITEMNMBR=vw2.ITEMNMBR) \n";
                strSQL+="       INNER JOIN Dynamics2021.dbo.tbm_invBod as a4 (NOLOCK) ON (a2.LOCNCODE=a4.tx_bod AND a2.ITEMNMBR=a4.tx_itm) \n";
                strSQL+="       INNER JOIN Dynamics2021.dbo.tbm_invBod as a5 (NOLOCK) ON (a3.LOCNCODE=a5.tx_bod AND a3.ITEMNMBR=a5.tx_itm) \n";
                
                
                
                strSQL+="       left join ( \n";
                strSQL+="       select sum (nd_saluni) as totalSaldo,co_itm \n";
                strSQL+="       from tbm_salmeninvbod  (NOLOCK)\n";
                strSQL+="       where co_per like '2023%' \n";
                strSQL+="       group by co_itm) as a6 on (a1.ITEMNMBR = a6.co_itm) \n";
                
                
                
                
                strSQL+="       WHERE a2.RCRDTYPE = 2 AND a2.LOCNCODE=" +objUti.codificar(txtCodBodDes.getText())+"  \n";
                strSQL+="            "+strFilItm+" \n";
                strSQL+=" ) AS b1 \n";
                strSQL+="" + strAux;
                
                System.out.println("consultarRegAniGrp: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                

                
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("ITEMNMBR"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("ITEMNMBR"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("ITMGEDSC")==null?"":rst.getString("ITMGEDSC"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("ITEMDESC"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("UOMSCHDL")==null?"":rst.getString("UOMSCHDL"));
                        vecReg.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "" + rst.getString("StockInmaconsa"));
                        vecReg.add(INT_TBL_DAT_DIS_CEN_DIS,     "" + rst.getString("StockInmaconsa"));
                        vecReg.add(INT_TBL_DAT_SALD,     "" + rst.getString("totalSaldo"));                        
                        

                         if (chkSalInv.isSelected())
                             validaM=true;
                         else
                             validaM=false;
                         int indicador =0,contador =4;
                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                            for (int ii=1 ;ii<=intNumColAdiFec;ii++)
                            {
                                indicador = (intNumColIniFec+ contador)-1;

                                if (validaM)
                                {
                                    if (j==indicador)
                                    {
                                        objTblMod.removeSystemHiddenColumn(j, tblDat);
                                        contador +=4;                                        
////                                        break;
                                    }
                                        
                                }
                                else
                                {
                                    if (j==indicador)
                                    {
                                        objTblMod.addSystemHiddenColumn(j, tblDat);                                
                                        contador +=4;                                        
////                                        break;
                                    }
                                }
                            }
                        }                       
                         
////                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
////                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
////                            if (validaM)
////                            {
////                                if (j==(intNumColFinFec-1)*intNumColAdiFec)
////                                    objTblMod.removeSystemHiddenColumn(j, tblDat);
////                            }
////                            else
////                                if (j==(intNumColFinFec-1)*intNumColAdiFec)
////                                    objTblMod.addSystemHiddenColumn(j, tblDat);
////                            
////                        }

                
////                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
////////                            if (j ==intNumColFinFec-1)
////////                                 vecReg.add(j,     "" + 8);
////////                            else
////////                                 vecReg.add(j,     "" + 18+j);                                
////                                vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
////                        }
////                        for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
////                             vecReg.add(j,     "" + "");
////                        }

                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                            
                        }
                        
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1)){
                                    vecReg.add(j,     rst.getObject("nd_proMenVtaMan")==null?"":rst.getString("nd_proMenVtaMan")); 
                            }
                            else{
                                vecReg.add(j,     "");
                            }
                        }

//                        //minimo y exceso
//                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
//                            vecReg.add(j,     rst.getObject(j)==null?"":rst.getString(j));
//                        }
                        
                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            if(j==intNumColIniStkMinExc){ // JM 2/Marzo/2023
                                vecReg.add(j,   rst.getObject("nd_stkFij")==null?"":rst.getString("nd_stkFij"));   // JM 2/Marzo/2023
                            } // JM 2/Marzo/2023
                            else{ // JM 2/Marzo/2023 
                                vecReg.add(j,     rst.getObject(j)==null?"": rst.getString(j));
                            } // JM 2/Marzo/2023
                            
                        }

                        //observacion - boton
                        vecReg.add(intNumColIniObs,     "");

                        //reposicion
                        for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                            if(j==intNumColIniStkRep)
                                vecReg.add(j,     rst.getObject("nd_nummesrep")==null?"":rst.getObject("nd_nummesrep"));
                            else if(j==(intNumColIniStkRep+1))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+2))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else if(j==(intNumColIniStkRep+3))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+4))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else
                                vecReg.add(j,     "");
                        }
                        
                        bdeStkActCal=new BigDecimal(rst.getObject("Stock")==null?"0":(rst.getString("Stock").equals("")?"0":rst.getString("Stock")));
                        bdeRepMaxCal=new BigDecimal(rst.getObject("nd_repMax")==null?"0":(rst.getString("nd_repMax").equals("")?"0":rst.getString("nd_repMax")));
                        bdeRepMinCal=new BigDecimal(rst.getObject("nd_repMin")==null?"0":(rst.getString("nd_repMin").equals("")?"0":rst.getString("nd_repMin")));
                        bdeStkExcCal=new BigDecimal(rst.getObject("nd_stkExc")==null?"0":(rst.getString("nd_stkExc").equals("")?"0":rst.getString("nd_stkExc")));
                        
                        //bodega centro distribucion
                        bdeStkDspBodDstCal=new BigDecimal(rst.getObject("StockInmaconsa")==null?"0":(rst.getString("StockInmaconsa").equals("")?"0":rst.getString("StockInmaconsa")));
                        bdeRepMaxBodDstCal=new BigDecimal(rst.getObject("nd_repMaxBodCenDis")==null?"0":(rst.getString("nd_repMaxBodCenDis").equals("")?"0":rst.getString("nd_repMaxBodCenDis")));
                        bdeStkExcBodDstCal=new BigDecimal(rst.getObject("nd_stkExcBodCenDis")==null?"0":(rst.getString("nd_stkExcBodCenDis").equals("")?"0":rst.getString("nd_stkExcBodCenDis")));
                        bdeRepMinBodDstCal=new BigDecimal(rst.getObject("nd_repMinBodCenDis")==null?"0":(rst.getString("nd_repMinBodCenDis").equals("")?"0":rst.getString("nd_repMinBodCenDis")));
                        
                        //bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMaxBodDstCal.add(bdeStkExcBodDstCal)));
                        bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMinBodDstCal.add(bdeStkExcBodDstCal)));
                        
                        //cantidad pendiente de reponer
                        for(int j=intNumColIniCanPenRep; j<intNumColFinCanPenRep;j++){
                            if((bdeRepMinCal.subtract(bdeStkActCal)).compareTo(new BigDecimal("0"))>0){
                                if(j==intNumColIniCanPenRep)
                                    vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                else{
                                    if(bdeExiCanDspRepCal.compareTo(new BigDecimal("0"))>0){
                                        if(bdeExiCanDspRepCal.compareTo((bdeRepMaxCal.subtract(bdeStkActCal)))>0)
                                            vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                        else
                                            vecReg.add(j,     bdeExiCanDspRepCal);
                                    }
                                    else
                                        vecReg.add(j,     "0");
                                }//can.dis.rep
                            }
                            else
                                vecReg.add(j,     "0");
                        }
//                        System.out.println("+++" + vecReg.toString());
                        vecDat.add(vecReg);
                        
                        
                        

                        //para cargar los datos en la tabla fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_FIX_COD_MAE,     "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_SIS,     "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT, "" + rst.getString("ITEMNMBR"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_DOS,"" + rst.getString("ITMGEDSC"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_3,"" + rst.getString("codZaf"));                        
                        vecRegFix.add(INT_TBL_FIX_NOM_ITM,     "" + rst.getString("ITEMDESC"));
                        vecRegFix.add(INT_TBL_FIX_UNI_MED,     "" + rst.getString("UOMSCHDL"));
                        vecRegFix.add(INT_TBL_FIX_DIS_CEN_DIS,     "" + rst.getDouble("StockInmaconsa"));                            
////                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("UOMSCHDL"));
//////////                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" +0);                        
////                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + 0);                        
////                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + "");                        
////                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
////                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));
                        
////                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
////                        if(strAux.equals("S"))
////                            vecRegFix.setElementAt(new Boolean(false),INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                        
                        vecDatFix.add(vecRegFix);   
                        

                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                conLoc.close();
                rst=null;
                stm=null;
                conLoc=null;



                //para hacer editables las columnas
                if(objParSis.getCodigoUsuario()==1){
                    //minimo, ecexso, sugerido, observacion
                    for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                        vecAux.add("" + j);
                    }
                    //boton de observacion
                    vecAux.add(""+intNumColIniObs);
                    //promedio manual
                    for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                        if(j==(intNumColIniProMen+1))
                            vecAux.add("" + j);
                    }
                    for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                        if(j==intNumColIniStkRep)
                            vecAux.add("" + j);
                    }
                }
                else{
                    if(objParSis.getCodigoMenu()==2332){//bodegas
                        if(objPerUsr.isOpcionEnabled(2334)){//guardar
                            //minimo, ecexso, sugerido, observacion
                            for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                                vecAux.add("" + j);
                            }
                            //boton de observacion
                            vecAux.add(""+intNumColIniObs);
                            //promedio manual
                            for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                                if(j==(intNumColIniProMen+1))
                                    vecAux.add("" + j);
                            }
                            for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                                if(j==intNumColIniStkRep)
                                    vecAux.add("" + j);
                            }
                        }
                    }
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }


                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                
                //tabla fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();                

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Ya no se usa JM
     * @return 
     */
    @Deprecated
    private boolean consultarRegAniEmp(){
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intNumColAdiFin=0;
        try{
            if(con!=null){
                if(intNumColAdiFec>1)
                    intNumColAdiFin=6;
                else
                    intNumColAdiFin=3;
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + " AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani";
                    strAux+="               ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;
                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T') AND (b1.tx_codAlt LIKE '%S' OR b1.tx_codAlt LIKE '%L')";
                strAux+=" GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_stkAct, b1.nd_canDis, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_promenvtaman";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor";
                strSQL+="            " + strCamSel + ", 0, 0, 0 , b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan";
                strSQL+="            , b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_promenvtaman, a3.nd_stkAct, a3.nd_canDis";
                strSQL+=" 		FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }

                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }

                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println("consultarRegAniEmp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while(rst.next()){
                    if (blnCon){
                        vecAux=new Vector();
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));

                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     "" + (rst.getObject("nd_proMenVtaMan")==null?"0":(rst.getString("nd_proMenVtaMan").equals("")?"0":rst.getString("nd_proMenVtaMan"))));
                            else
                                vecReg.add(j,     "0");
                        }


                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }


                        vecDat.add(vecReg);





                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                if(objParSis.getCodigoUsuario()==1){
                    //promedio manual
                    vecAux.add("" + (intNumColIniProMen+1));
                    //minimo
                    vecAux.add("" + intNumColIniStkMinExc);
                }
                else{
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }



                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }



    private boolean calcularColumnaTotalesAniMes()//chiqui
    {
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeSumUni=new BigDecimal(0);
        BigDecimal bdeSumVec=new BigDecimal(0);
        BigDecimal bdeSumMes=new BigDecimal(0);
        
        double dblProCal=0.00;
        double dblIntAniDiv=0.00;

        try{
            if(optHisAnu.isSelected()) // José Marín M - 19-Nov-2014
                dblIntAniDiv=12*getRegHistoricoSeleccionados();
            else
                dblIntAniDiv=getRegHistoricoSeleccionados();
            // José Marín M - 19-Nov-2014
            
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=intNumColFinModEst; j<(intNumColFinModEst+(intNumColAdiFec*4)); j++){
                    if(p==0){
                        bdeSumUni=bdeSumUni.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p++;
                    }
                    else if(p==1){
                        bdeSumVec=bdeSumVec.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p++;
                    }
                    else if(p==2){
                        bdeSumMes=bdeSumMes.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p++;
                    }
                    else if(p==3)
                         p=0;
                        
                }
                objTblMod.setValueAt("" + bdeSumUni, i, (intNumColFinModEst+(intNumColAdiFec*4)));
                objTblMod.setValueAt("" + bdeSumVec, i, ((intNumColFinModEst+(intNumColAdiFec*4))+1));
                objTblMod.setValueAt("" + bdeSumMes, i, ((intNumColFinModEst+(intNumColAdiFec*4))+2));

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                }
                else{//empresa
                    if(bdeSumUni.compareTo(new BigDecimal(0))>0){
                        //José Marín M. - 19-Nov-2014 - Promedio Calculado 
                        dblProCal=objUti.redondear((Double.parseDouble(""+bdeSumUni)/dblIntAniDiv), objParSis.getDecimalesBaseDatos());
                        objTblMod.setValueAt("" + dblProCal, i, ((intNumColFinModEst+(intNumColAdiFec*4))+4));
                          //José Marín M. - 19-Nov-2014 - Promedio Calculado  
                    }
                    dblProCal=0.00;
                }
                bdeSumUni=new BigDecimal(0);
                bdeSumVec=new BigDecimal(0);
                bdeSumMes=new BigDecimal(0);

            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    private boolean calcularColumnaPromedioCalculado(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeSumUni=new BigDecimal(0);
        BigDecimal bdeSumVec=new BigDecimal(0);
        BigDecimal bdeSumMes=new BigDecimal(0);

        double dblProCal=0.00;
        double dblIntAniDiv=0.00;

        try{
            dblIntAniDiv=12*getRegHistoricoSeleccionados();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){

                bdeSumUni=bdeSumUni.add(objTblMod.getValueAt(i, intNumColFinModEst)==null?new BigDecimal(0):(objTblMod.getValueAt(i, intNumColFinModEst).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, intNumColFinModEst).toString())));
                bdeSumVec=bdeSumVec.add(objTblMod.getValueAt(i, (intNumColFinModEst+1))==null?new BigDecimal(0):(objTblMod.getValueAt(i, (intNumColFinModEst+1)).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, (intNumColFinModEst+1)).toString())));
                bdeSumMes=bdeSumMes.add(objTblMod.getValueAt(i, (intNumColFinModEst+2))==null?new BigDecimal(0):(objTblMod.getValueAt(i, (intNumColFinModEst+2)).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, (intNumColFinModEst+2)).toString())));

                if(bdeSumUni.compareTo(new BigDecimal(0))>0){
                    dblProCal=objUti.redondear((Double.parseDouble(""+bdeSumUni)/dblIntAniDiv), objParSis.getDecimalesBaseDatos());
                    objTblMod.setValueAt("" + dblProCal, i, (intNumColFinModEst+4));
                }
                dblProCal=0.00;

                bdeSumUni=new BigDecimal(0);
                bdeSumVec=new BigDecimal(0);
                bdeSumMes=new BigDecimal(0);
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConCla()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_desCorGrp");
            arlCam.add("a1.tx_desLarGrp");
            arlCam.add("a1.co_cla");
            arlCam.add("a1.tx_desCorCla");
            arlCam.add("a1.tx_desLarCla");

            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Grp.");
            arlAli.add("Ali.Grp.");
            arlAli.add("Nom.Grp.");
            arlAli.add("Cod.Cla.");
            arlAli.add("Ali.Cla.");
            arlAli.add("Nom.Cla.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("28");
            arlAncCol.add("60");
            arlAncCol.add("190");
            arlAncCol.add("30");
            arlAncCol.add("60");
            arlAncCol.add("190");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_grp, a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp";
            strSQL+=" , a2.co_cla, a2.tx_desCor AS tx_desCorCla, a2.tx_desLar AS tx_desLarCla";
            strSQL+=" FROM tbm_claInv AS a2 LEFT OUTER JOIN tbm_grpClaInv AS a1";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" ORDER BY a1.co_grp, a2.co_cla";
            //Ocultar columnas.
            vcoCla=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clasificación", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCla.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
    private boolean mostrarVenConCla(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCla.setCampoBusqueda(2);
                    vcoCla.show();
                    if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Descripción corta".
                    if (vcoCla.buscar("a1.tx_desCorCla", txtDesCorCla.getText()))
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCla.setCampoBusqueda(0);
                        vcoCla.setCriterio1(11);
                        vcoCla.cargarDatos();
                        vcoCla.show();
                        if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                        {
                            txtCodCla.setText(vcoCla.getValueAt(4));
                            txtDesCorCla.setText(vcoCla.getValueAt(5));
                            txtDesLarCla.setText(vcoCla.getValueAt(6));
                            objTblMod.removeAllRows();
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCla.buscar("a1.tx_desLarCla", txtDesLarCla.getText()))
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCla.setCampoBusqueda(2);
                        vcoCla.setCriterio1(11);
                        vcoCla.cargarDatos();
                        vcoCla.show();
                        if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                        {
                            txtCodCla.setText(vcoCla.getValueAt(4));
                            txtDesCorCla.setText(vcoCla.getValueAt(5));
                            txtDesLarCla.setText(vcoCla.getValueAt(6));
                            objTblMod.removeAllRows();
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


    /**
     * 
     * @return 
     */
    private boolean guardarDatos(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null){
                con.setAutoCommit(false);
                if(actualiza_tbmInvBod()){
                    con.commit();
                }
                else{
                    con.rollback();
                    blnRes=false;
                }
                con.close();
                con=null;
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
     * 
     * @return 
     */
    private boolean actualiza_tbmInvBod(){
        boolean blnRes=true;
        String strQueUpd;
        String strLin="";
        try{
            if(con!=null){
                strQueUpd="";
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue();i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        strSQL="";
                        strSQL+="UPDATE tbm_invbod SET nd_stkmin=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc)), 0) + "";//fijo
                        strSQL+=", nd_stkexc=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+1)), 0) + "";
                        strSQL+=", nd_stkMinSug=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+2)), 0) + "";
                        strSQL+=", tx_obsStkMinSug=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+3)), 0) + "";
                        strSQL+=", nd_proMenVtaMan=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniProMen+1)), 0)  + ""; 
                        strSQL+=", nd_numMesRep=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep)), 0) + "";
                        strSQL+=", nd_repMin=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+3)), 0) + "";
                        strSQL+=", nd_repMax=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+4)), 0) + "";
                        strSQL+=", nd_stkFij=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc)), 0) + "";//fijo 
                        strSQL+=" WHERE tx_bod="+objUti.codificar(txtCodBodDes.getText())+ "";
                        strSQL+=" AND tx_itm=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM)) + "";
                        strSQL+=";";
                        strSQL+=" UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 SET ORDRPNTQTY="+objUti.codificar(objTblMod.getValueAt(i,(intNumColIniStkRep+3)),2)+", ";
                        strSQL+=" ORDRUPTOLVL="+objUti.codificar(objTblMod.getValueAt(i,(intNumColIniStkRep+4)),2)+ "   ";
                        strSQL+=" WHERE RCRDTYPE = 2 AND LOCNCODE ="+objUti.codificar(txtCodBodDes.getText())+" AND ITEMNMBR="+objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM))+";";
                        strSQL+=" ";
                        
                        strSQL+=" INSERT INTO tbh_maxmininv (tx_codBod, tx_codAlt, co_reg, st_reg, nd_numMesRep, nd_valMin, nd_valMax, nd_stkExc, nd_stkFij, tx_usrIng, fe_ing) ";
                        strSQL+=" VALUES("+objUti.codificar(txtCodBodDes.getText())+","+objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM))+", ";
                        strSQL+=" (SELECT CASE WHEN MAX(co_reg) IS NULL THEN 1 ELSE MAX(co_reg)+1 END as co_reg FROM tbh_maxmininv WHERE tx_codBod="+objUti.codificar(txtCodBodDes.getText())+" AND ";
                        strSQL+="  tx_codAlt="+objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM))+"), 'A',";
                        
                        strSQL+=objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep)), 0) + ", "; // nd_numMesRep 
                        strSQL+=objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+3)), 0) + ", "; //rep min
                        strSQL+=objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+4)), 0) + ", "; //rep max
                        strSQL+=objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+1)), 0) + ", ";  // nd_stkExc 
                        strSQL+=objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc)), 0) + ","; // nd_stkFij 
                        strSQL+=objUti.codificar(objParSis.getNombreUsuario())+",";
                        strSQL+="CURRENT_TIMESTAMP);";
                        strSQL+="";
                         
                           
                        System.out.println("actualiza_tbmInvBod: " + strSQL);
                        strQueUpd+=strSQL;
                    }
                }
                stm.executeUpdate(strQueUpd);
                stm.close();
                stm=null;
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
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        //Validar el "Tipo de documento".
        if (txtCodBodDes.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBodDes.requestFocus();
            return false;
        }


        if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
            if( ! chkHisVta.isSelected()){
                mostrarMsgInf("<HTML>El selector <FONT COLOR=\"blue\">Histórico de Ventas </FONT> es obligatorio.<BR>Seleccionelo y escoga también una de las dos opciones y vuelva a intentarlo.</HTML>");
                return false;
            }
        }

        if(  (chkHisVta.isSelected()) &&  (getRegHistoricoSeleccionados()==0)  ){
            mostrarMsgInf("<HTML>Seleccione por lo menos un registro de la tabla de información histórica.</HTML>");
            return false;
        }



        return true;
    }


    private boolean consultarRegStkActGrp(){
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        try{
            if(con!=null){
                stm=con.createStatement();
                strAux="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strAux+=objVenConItm01.getCondicionesSQL();
                }

                strSQL="";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug";
                strSQL+=" 		FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }


                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strAux + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }


                strSQL+="   AND a1.st_reg NOT IN('U','T') AND a1.st_ser NOT IN('S','T') ";


                strSQL+=" AND (a1.tx_codAlt LIKE '%I' OR a1.tx_codAlt LIKE '%S')";




                strSQL+=" GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug";
                strSQL+=" ORDER BY a1.tx_codAlt";
                System.out.println("consultarRegStkActGrp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while(rst.next()){
                    if (blnCon){
                        vecAux=new Vector();
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getString(""));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));

                        vecReg.add((INT_TBL_DAT_UNI_MED+2), "" + rst.getObject("nd_stkMin")==null?null:rst.getString("nd_stkMin"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+3), "" + rst.getObject("nd_stkexc")==null?null:rst.getString("nd_stkexc"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+4), "" + rst.getObject("nd_stkminsug")==null?null:rst.getString("nd_stkminsug"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+5), "" + rst.getObject("tx_obsstkminsug")==null?null:rst.getString("tx_obsstkminsug"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+6), "");




                        if(objParSis.getCodigoUsuario()==1){
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                        }
                        else{
                            if(objParSis.getCodigoMenu()==2332){//bodegas
                                if(objPerUsr.isOpcionEnabled(2334)){//guardar
                                    //para hacer editables las ultimas 5 columnas
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                                }
                            }

                            if(objParSis.getCodigoMenu()==1662){//proveedores
                                if(objPerUsr.isOpcionEnabled(1664)){//guardar
                                    //para hacer editables las ultimas 5 columnas
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                                }
                            }
                        }


                        vecDat.add(vecReg);

                        i++;
                        pgrSis.setValue(i);
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                    }
                    else{
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

   

    private void exitForm(){
        String strTit, strMsg;
        try{
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
    }
    
    


    private boolean calcularPromedioAnual() 
    {
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAdiFec);

            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProMen);
                bdeProAnuCal=new BigDecimal(0);
                bdeTotUni=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularPromedioMensual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);
        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAdiFec);
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProMen);
                bdeProAnuCal=new BigDecimal(0);
                 bdeTotUni=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
private boolean calcularStockActualAnterior()//chiqui
{
        boolean blnRes=true;
        String strSQL="",coitm="";
        BigDecimal bdeInicial=new BigDecimal(0);
        BigDecimal bdeStock=new BigDecimal(0);
////        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);
        int intAni=0;
        int intMes=0;
        int intAniAnt=0;
        int intMesAnt=0; 
        double saldoStock=0;
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            stm=conLoc.createStatement();
                     for(int j=0; j<arlDatAniAdd.size(); j++)
                     {
                         intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                        intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                        intAniAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                        intMesAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);

                        intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                        intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt; 
                        
                         for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                         {
                             coitm=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString();
                             saldoStock =ObtenerStock(coitm,i,intMesAnt,intAniAnt,objUti.codificar(txtCodBodDes.getText()));
                             objTblMod.setValueAt("" + objUti.redondearBigDecimal(objUti.parseString(saldoStock) , objParSis.getDecimalesBaseDatos()), i, intNumColFinStkAct+1); 
                                 
                            
                         }
                     }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 

private double ObtenerStock(String codigo,int indice,int mesAnt,int anioAnt,String bodega)//chiqui
    {
        java.sql.Connection conLoc1GP;
        java.sql.Statement stmLoc1GP;
        java.sql.ResultSet rstLoc1GP;    

        String strSqlGP="";
        boolean validaIngreso=false,blnRes=false;
        double preVtaZa=0, preResul=0,stockGP=0;
        try
        {
            conLoc1GP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1GP!=null)
            {
                  stmLoc1GP=conLoc1GP.createStatement();
                strSQL="";  
                strSQL+="       SELECT SUM(nd_saluni) AS totalSaldo,co_itm, "+bodega+" AS co_bod,LEFT(co_per, 4) AS anio,CONVERT(INT, RIGHT(co_per, 2)) AS mes \n";
                strSQL+="       FROM tbm_salmeninvbod (NOLOCK)\n";
                strSQL+="       WHERE co_bod=" +bodega+"\n";
////                            strSQL+="           AND LEFT(co_per, 4) like '"+ intAniAnt +""+intMesAnt+"' \n";
                strSQL+="           AND LEFT(co_per, 4)=" + anioAnt + " \n";
                strSQL+="           AND RIGHT(co_per, 2)=" + mesAnt + " \n";  
                strSQL+="           AND co_itm = '"+ codigo +"' \n"; 
                strSQL+="       group by co_itm, co_bod, LEFT(co_per, 4), RIGHT(co_per, 2) \n";
                rstLoc1GP = stmLoc1GP.executeQuery(strSQL);

                  while(rstLoc1GP.next())
                  {
                     stockGP=rstLoc1GP.getDouble("totalSaldo");
                  }
                  stmLoc1GP.close();
                  stmLoc1GP=null;
                  rstLoc1GP.close();
                  rstLoc1GP=null;
            }
              conLoc1GP.close();
              conLoc1GP=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ObtenerStock "+ e );
            stockGP=-1;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ObtenerStock "+ e );
           stockGP=-1;
         }
        return stockGP;
    }


    
    
private boolean calcularStockActualSiguiente()//chiqui
{
        boolean blnRes=true;
        int contador=0,i=0;
        BigDecimal bdeInicial=new BigDecimal(0);
        BigDecimal bdeStock=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);
        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAdiFec);
                     for(int j=0; j<objTblMod.getRowCountTrue(); j++)
                     {
                         contador=0;
                         for( i=intNumColIniFec+3; i<intNumColFinFec;i=i+4)
                        {
                            //contiene el valor de la columna de UNIDADES en TOTALES
                            if (contador==0)
                                bdeInicial=new BigDecimal(objTblMod.getValueAt(j, i)==null?"0":(objTblMod.getValueAt(j, i).toString().equals("")?"0": objTblMod.getValueAt(j, i).toString()));

                            bdeStock=new BigDecimal(objTblMod.getValueAt(j, i)==null?"0":(objTblMod.getValueAt(j, i).toString().equals("")?"0": objTblMod.getValueAt(j, i).toString()));
                            if (bdeStock.compareTo(BigDecimal.ZERO)==0)
                                bdeStock= new BigDecimal(bdeInicial.toString());
                            else
                            {
                                bdeStock =new BigDecimal(objTblMod.getValueAt(j, i)==null?"0":(objTblMod.getValueAt(j, i).toString().equals("")?"0": objTblMod.getValueAt(j, i).toString()));
                                bdeInicial=new BigDecimal(bdeStock.toString());
                            }
                                

                            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeStock, Integer.parseInt("0")), j, i);
                            contador++;

            ////                bdeProAnuCal=(bdeInicial.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));//promedio anual calculado de ventas calculado por item
            ////                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProMen);
            ////                bdeProAnuCal=new BigDecimal(0);
            ////                bdeInicial=new BigDecimal(0);
                        }
                     }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    



    private boolean calcularCantidadReponer(){
        boolean blnRes=true;
        BigDecimal bdeStkAct=new BigDecimal(0);
        Object objCanRepCal="";
        Object objMin="";
        Object objMax="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStkAct)==null?"":(objTblMod.getValueAt(i, intNumColIniStkAct).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStkAct).toString()));
                objMin=objTblMod.getValueAt(i, (intNumColIniStkRep+1))==null?"":objTblMod.getValueAt(i, (intNumColIniStkRep+1)).toString();
                objMax=objTblMod.getValueAt(i, (intNumColIniStkRep+2))==null?"":objTblMod.getValueAt(i, (intNumColIniStkRep+2)).toString();

                
                if(  (!objMin.equals("")) && (!objMax.equals(""))  ){
                    if(bdeStkAct.compareTo(new BigDecimal(objMin.toString()))<0){
                        objCanRepCal=""+new BigDecimal(objMax.toString()).subtract(bdeStkAct);
                    }
                    else{
                        objCanRepCal=""+new BigDecimal(BigInteger.ZERO);
                    }
                }
                objTblMod.setValueAt(objCanRepCal, i, (intNumColIniStkRep+5));
//                objTblMod.addUserHiddenColumn((intNumColIniStkRep+5), tblDat);
            }
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cambiarNumeroMesesReponer(){
        boolean blnRes=true;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objTblMod.setValueAt(jspNumMesRep.getValue(), i, intNumColIniStkRep);
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * 
     * @return 
     */
    private boolean calcularMinimoMaximo(){
        boolean blnRes=true;
        Object objValUtiCalMinMax="";
        Object objPorMin="";
        Object objPorMax="";

        Object objFij="";
        Object objProAnuCal="";
        Object objProAnuMan="";
        BigDecimal bdeNumMesRep=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objProAnuCal=objTblMod.getValueAt(i, intNumColIniProMen)==null?"":objTblMod.getValueAt(i, intNumColIniProMen).toString();
                objProAnuMan=objTblMod.getValueAt(i, (intNumColIniProMen+1))==null?"":objTblMod.getValueAt(i, (intNumColIniProMen+1));
                objFij=objTblMod.getValueAt(i, intNumColIniStkMinExc)==null?"":objTblMod.getValueAt(i, intNumColIniStkMinExc);
                bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStkRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniStkRep).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniStkRep).toString()));

                /* 1.-     promedio manual * numero de meses a reponer - stock actual*/
                if(!objFij.equals("")){
                    //objValUtiCalMinMax="" + (new BigDecimal(objFij.toString()).multiply(bdeNumMesRep)  );
                    objValUtiCalMinMax="" + (new BigDecimal(objFij.toString()) );
                }
                /* 2.-     promedio manual * numero de meses a reponer - stock actual*/
                else if(   (!objProAnuMan.equals(""))  && (objFij.equals(""))  ){
                    objValUtiCalMinMax="" + (new BigDecimal(objProAnuMan.toString()).multiply(bdeNumMesRep)  );
                }
                /* 3.-      promedio calculado * numero de meses a reponer - stock actual*/
                else if(   (objProAnuMan.equals(""))  && (objFij.equals(""))  ){
                    objValUtiCalMinMax="" + (new BigDecimal(""+objProAnuCal).multiply(bdeNumMesRep)  );
                }
                objTblMod.setValueAt("" + objValUtiCalMinMax, i, (intNumColIniStkRep+6));
                if(!objValUtiCalMinMax.equals("")){
                    if(!objFij.equals("")){
                        objPorMin=objUti.redondearBigDecimal((new BigDecimal(""+objValUtiCalMinMax)), Integer.parseInt("0"));
                        objPorMax=new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMax.getText()));

                        //bdeMin
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMin.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+1));
                        //bdeMax
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMax.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+2));
                    }
                    else{
                        objPorMin=objUti.redondearBigDecimal((new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMin.getText()))), Integer.parseInt("0"));
                        objPorMax=new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMax.getText()));

                        //bdeMin
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMin.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+1));
                        //bdeMax
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMax.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+2));
                    }
                }
                else{
                    objPorMin="";
                    objPorMax="";

                    //bdeMin
                    objTblMod.setValueAt("", i, (intNumColIniStkRep+1));
                    //bdeMax
                    objTblMod.setValueAt("", i, (intNumColIniStkRep+2));

                }              
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 


}
