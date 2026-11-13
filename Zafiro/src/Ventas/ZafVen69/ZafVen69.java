/*
 * ZafVen05.java
 *
 * Created on 05 de enero de 2019, 10:10 PM
 */
package Ventas.ZafVen69;
import Librerias.ZafCorEle.ZafCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.table.TableColumn;

/**
 *
 * @author  José Marín 
 */
public class ZafVen69 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_COD_EMP=1;                      
    static final int INT_TBL_COD_LOC=2;                      
    static final int INT_TBL_COD_CLI=3;                    
    static final int INT_TBL_IDE_CLI=4;                   
    static final int INT_TBL_NOM_CLI=5;                  
    static final int INT_TBL_COD_CIU=6;                     
    static final int INT_TBL_NOM_CIU=7;           
    static final int INT_TBL_ST_NUE=8;
    static final int INT_TBL_COD_CFG=9;
    static final int INT_TBL_FEC_MOD=10;
    
    static final int INT_TBL_DAT_CDI_AUT=11;                      
    
    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_TBL_DAT_NUM_TOT_CES=9;         //Número total de columnas estáticas.
    private static final int INT_TBL_DAT_NUM_TOT_CDI=2;         //Número de columnas dinámicas por usuario. (codUsr,tx_usr,tx_nom,st_reg )
    private static final int INT_TBL_DAT_FAC_NUM_CDI=1;         //Factor para calcular total de columnas dinámicas. 
    Vector vecLocal; //Vector que contiene el codigo del local

    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPre;                 //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtMarUti;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPesKgr;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMaxVen;           //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtFacCosUni;           //Editor: JTextField en celda.
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed;        //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private Map<String, localSql> mapLocales; // Map para almacenar los locales por nombre
     
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSql, strConSql, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                       //Contenido del campo al obtener el foco.
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private int intNumColEst; 
    private ZafVenConGP vcoLoc;                           //Ventana de consulta <jota>
    private String strCodLoc, strNomLoc;
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private ZafVenConGP vcoVen;                                   //Ventana de consulta.
    private String strCodCli, strDesLarCli,strDesLargrupoEco;                     //Contenido del campo al obtener el foco.
    private ZafVenConGP vcoCli;                                   //Ventana de consulta "Clientes".
    private String strCodForPag, strForPag;             //Contenido del campo al obtener el foco.
    private ZafVenConGP vcoForPag;                        //Ventana de consulta.
    private String strCodCiu = "", strCiudad = "";
    private ZafVenConGP vcoCiu; 
    private int intNumColGrpUsrAdi=0;
    //Arreglos: Datos Usuario
    private ArrayList arlDatUsr, arlRegUsr;
    private static final int INT_ARL_DAT_USR_COD_USR=0;
    private static final int INT_ARL_DAT_USR_NOM_USR=1;
    private ZafCorEle objCorEle;
    private String strVer=" v0.7";
    private ZafThrGua objThrGua;
    private ZafThrGua2 objThrGua2;
    private int intContadorTiempo=0;
    private int intTiempoHiloDormido=600000; // 600000//10 min - 300000//5 min  -  60000//1 min  
    
    List<localSql> locales ; 
    private int localVendedor=0;


    public ZafVen69(ZafParSis obj){
        try{
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            objCorEle = new ZafCorEle(objParSis);
            configurarFrm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        chkCliSinVen = new javax.swing.JCheckBox();
        chkCliVenAct = new javax.swing.JCheckBox();
        chkCliVenIna = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        txtAnio = new javax.swing.JTextField();
        txtMes = new javax.swing.JTextField();
        lblVen1 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblForPag = new javax.swing.JLabel();
        txtForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        lblCiu = new javax.swing.JLabel();
        txtCodCiu = new javax.swing.JTextField();
        txtCiu = new javax.swing.JTextField();
        butCiu = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNameGrupoEconomico = new javax.swing.JTextField();
        tbnGrupoEconomico = new javax.swing.JButton();
        jcbLocalVend = new javax.swing.JComboBox<>();
        chkClComp = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        btnAsignar = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 50, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 70, 400, 20);

        chkCliSinVen.setSelected(true);
        chkCliSinVen.setText("Clientes que no tienen asignado vendedor");
        chkCliSinVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliSinVenActionPerformed(evt);
            }
        });
        panFil.add(chkCliSinVen);
        chkCliSinVen.setBounds(60, 200, 324, 20);

        chkCliVenAct.setSelected(true);
        chkCliVenAct.setText("Clientes que tienen asignado vendedor activo");
        chkCliVenAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliVenActActionPerformed(evt);
            }
        });
        panFil.add(chkCliVenAct);
        chkCliVenAct.setBounds(60, 220, 324, 20);

        chkCliVenIna.setSelected(true);
        chkCliVenIna.setText("Clientes que tienen asignado vendedor inactivo");
        chkCliVenIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliVenInaActionPerformed(evt);
            }
        });
        panFil.add(chkCliVenIna);
        chkCliVenIna.setBounds(60, 240, 324, 20);

        jLabel1.setText("Período:");
        panFil.add(jLabel1);
        jLabel1.setBounds(20, 10, 70, 14);

        txtAnio.setEnabled(false);
        panFil.add(txtAnio);
        txtAnio.setBounds(90, 10, 60, 20);

        txtMes.setEnabled(false);
        panFil.add(txtMes);
        txtMes.setBounds(150, 10, 80, 20);

        lblVen1.setText("Local Vendedor:");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen1);
        lblVen1.setBounds(20, 30, 100, 20);

        txtCodEmp.setEditable(false);
        txtCodEmp.setEnabled(false);
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(70, 30, 20, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(90, 30, 60, 20);

        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(150, 30, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(610, 30, 20, 20);

        lblVen.setText("Vendedor:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(40, 270, 80, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(130, 270, 50, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(180, 270, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(620, 270, 20, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(30, 90, 120, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(140, 90, 100, 20);

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
        panFil.add(txtNomCli);
        txtNomCli.setBounds(240, 90, 390, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(630, 90, 20, 20);

        lblForPag.setText("Forma de pago:");
        lblForPag.setToolTipText("Forma de pago");
        panFil.add(lblForPag);
        lblForPag.setBounds(30, 110, 120, 20);

        txtForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtForPagFocusLost(evt);
            }
        });
        txtForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPagActionPerformed(evt);
            }
        });
        panFil.add(txtForPag);
        txtForPag.setBounds(140, 110, 490, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panFil.add(butForPag);
        butForPag.setBounds(630, 110, 20, 20);

        lblCiu.setText("Ciudad:"); // NOI18N
        lblCiu.setToolTipText("Ciudad");
        panFil.add(lblCiu);
        lblCiu.setBounds(40, 150, 120, 20);

        txtCodCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCiuFocusLost(evt);
            }
        });
        txtCodCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCiuActionPerformed(evt);
            }
        });
        panFil.add(txtCodCiu);
        txtCodCiu.setBounds(140, 150, 50, 20);

        txtCiu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCiuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCiuFocusLost(evt);
            }
        });
        txtCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiuActionPerformed(evt);
            }
        });
        panFil.add(txtCiu);
        txtCiu.setBounds(190, 150, 440, 20);

        butCiu.setLabel("..."); // NOI18N
        butCiu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCiuActionPerformed(evt);
            }
        });
        panFil.add(butCiu);
        butCiu.setBounds(630, 150, 20, 20);

        jLabel2.setText("Grupo Economico:");
        panFil.add(jLabel2);
        jLabel2.setBounds(30, 130, 130, 20);

        txtNameGrupoEconomico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameGrupoEconomicoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameGrupoEconomicoFocusLost(evt);
            }
        });
        txtNameGrupoEconomico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameGrupoEconomicoActionPerformed(evt);
            }
        });
        panFil.add(txtNameGrupoEconomico);
        txtNameGrupoEconomico.setBounds(140, 130, 490, 20);

        tbnGrupoEconomico.setText("...");
        tbnGrupoEconomico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnGrupoEconomicoActionPerformed(evt);
            }
        });
        panFil.add(tbnGrupoEconomico);
        tbnGrupoEconomico.setBounds(630, 130, 20, 20);

        jcbLocalVend.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbLocalVend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbLocalVendActionPerformed(evt);
            }
        });
        panFil.add(jcbLocalVend);
        jcbLocalVend.setBounds(120, 30, 320, 20);

        chkClComp.setText("Clientes que hayan comprado alguna vez");
        panFil.add(chkClComp);
        chkClComp.setBounds(60, 180, 320, 23);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));
        panRpt.add(jPanel1, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnAsignar.setText("Asignar");
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });
        panBot.add(btnAsignar);

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
        butGua.setToolTipText("Guarda los cambios realizados.");
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

        getContentPane().add(panFrm);

        setBounds(0, 0, 712, 484);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkCliVenInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliVenInaActionPerformed
        if (!chkCliVenIna.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkCliVenInaActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        limpiarDetFrm();
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    /**
     * Esta función permite limpiar el detalle del formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    public void limpiarDetFrm(){
       objTblMod.removeAllRows();
       objTblMod.setDataModelChanged(false);
       eliminaColumnasAdicionadas();
    }
    
    
    
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
            cerrarHilo();
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkCliSinVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliSinVenActionPerformed
        if (!chkCliSinVen.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkCliSinVenActionPerformed

    private void chkCliVenActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliVenActActionPerformed
        if (!chkCliVenAct.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkCliVenActActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else{
                mostrarLocal(2);
            }
        }
        else
        txtNomLoc.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        //        if(txtCodEmp.getText().length()>0)
        //            configurarLocal();
        mostrarLocal(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)){
            if (txtCodVen.getText().equals("")){
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else{
                mostrarVenConVen(1);
            }
        }
        else
        txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)){
            if (txtNomVen.getText().equals("")){
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else{
                mostrarVenConVen(2);
            }
        }
        else
        txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // configurarVenConVen();
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();   
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)){
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else{
                mostrarLocal(1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                cerrarHilo();
                if(objThrGua==null){
                    objThrGua=new ZafVen69.ZafThrGua();
                    objThrGua.start();
                }
            }
        }
        else
        mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        // TODO add your handling code here:
        if(objParSis.getCodigoUsuario()==1){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                if(guardarAsignacion()){
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    butCon.doClick();
                    objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "Cambio de clientes por vendedor generado ", "OK ");
                }
                else{
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                    objCorEle.enviarCorreoMasivo("sistemas6@tuvalsa.com", "Cambio de clientes por vendedor generado ", "ERROR ");
                }
            }
        }
        else{
            mostrarMsgInf("Esta operación solo puede ser realizada por el Administrador del Sistema...");
        }
    }//GEN-LAST:event_btnAsignarActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void butCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCiuActionPerformed
        //Realiza búsqueda de ciudades.
        vcoCiu.setTitle("Listado de Ciudades");
        vcoCiu.setCampoBusqueda(1);
        vcoCiu.show();
        if (vcoCiu.getSelectedButton()==vcoCiu.INT_BUT_ACE)
        {
            txtCodCiu.setText(vcoCiu.getValueAt(1));
            txtCiu.setText(vcoCiu.getValueAt(2));
            strCodCiu = vcoCiu.getValueAt(1);
            strCiudad = vcoCiu.getValueAt(2);
        }
        if (txtCodCiu.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCiuActionPerformed

    private void txtCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiuActionPerformed
        txtCiu.transferFocus();
    }//GEN-LAST:event_txtCiuActionPerformed

    private void txtCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusLost
        if (!txtCiu.getText().equalsIgnoreCase(strCiudad)){
            if (txtCiu.getText().equals("")){
                txtCodCiu.setText("");
                txtCiu.setText("");
            }
            else
            {
                BuscarCiudad("a.tx_deslar", txtCiu.getText(), 1);
            }
        }
        else
        txtCiu.setText(strCiudad);

        if (txtCodCiu.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCiuFocusLost

    private void txtCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCiuFocusGained
        strCiudad = txtCiu.getText();
        txtCiu.selectAll();
    }//GEN-LAST:event_txtCiuFocusGained

    private void txtForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPagActionPerformed
        txtForPag.transferFocus();
    }//GEN-LAST:event_txtForPagActionPerformed

    private void txtForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtForPag.getText().equalsIgnoreCase(strForPag))
        {
            if (txtForPag.getText().equals(""))
            {
                //txtCodForPag.setText("");
                txtForPag.setText("");
            }
            else
            {
                mostrarVenConForPag(2);
            }
        }
        else
        txtForPag.setText(strForPag);
        //Seleccionar el JRadioButton de filtro si es necesario.
//        if (txtCodForPag.getText().length()>0)
//        {
//            optFil.setSelected(true);
//        }
    }//GEN-LAST:event_txtForPagFocusLost

    private void txtForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusGained
        strForPag=txtForPag.getText();
        txtForPag.selectAll();
    }//GEN-LAST:event_txtForPagFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
        txtNomCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }
            else{
                mostrarVenConCli(1);
            }
        }
        else
        txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCiuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCiuActionPerformed
        txtCodCiu.transferFocus();
    }//GEN-LAST:event_txtCodCiuActionPerformed

    private void txtCodCiuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusLost
        if (!txtCodCiu.getText().equalsIgnoreCase(strCodCiu))
        {
            if (txtCodCiu.getText().equals("")){
                txtCodCiu.setText("");
                txtCiu.setText("");
            }
            else
            {
                BuscarCiudad("a.co_ciu", txtCodCiu.getText(), 0);
            }
        }
        else
        {
            txtCodCiu.setText(strCodCiu);
        }
        if (txtCodCiu.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCiuFocusLost

    private void txtCodCiuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCiuFocusGained
        strCodCiu = txtCodCiu.getText();
        txtCodCiu.selectAll();
    }//GEN-LAST:event_txtCodCiuFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()){
            txtCodCli.setText("");
            txtNomCli.setText("");
            //txtCodForPag.setText("");
            txtForPag.setText("");
            txtCodCiu.setText("");
            txtCiu.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            chkClComp.setSelected(true);
            chkCliSinVen.setSelected(true);
            chkCliVenAct.setSelected(true);
            chkCliVenIna.setSelected(true);
            txtNameGrupoEconomico.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged
 
 
    
    private void txtNameGrupoEconomicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameGrupoEconomicoActionPerformed
        // TODO add your handling code here:
          txtNameGrupoEconomico.transferFocus();
    }//GEN-LAST:event_txtNameGrupoEconomicoActionPerformed

    private void txtNameGrupoEconomicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameGrupoEconomicoFocusLost
        // TODO add your handling code here:
         // TODO add your handling code here:
        if (!txtNameGrupoEconomico.getText().equalsIgnoreCase(strDesLargrupoEco)){
            if (txtNameGrupoEconomico.getText().equals("")){
                 txtNameGrupoEconomico.setText("");
            }
            else{
                mostrarGrupoEconomico(1);
            }
        }
        else
            txtNameGrupoEconomico.setText(strDesLargrupoEco);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNameGrupoEconomico.getText().length()>0){
            optFil.setSelected(true);
        }
     
    }//GEN-LAST:event_txtNameGrupoEconomicoFocusLost

    private void tbnGrupoEconomicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnGrupoEconomicoActionPerformed
        // TODO add your handling code here:
        mostrarGrupoEconomico(0);        
    }//GEN-LAST:event_tbnGrupoEconomicoActionPerformed

    private void txtNameGrupoEconomicoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameGrupoEconomicoFocusGained
        // TODO add your handling code here:
        strDesLargrupoEco=txtNameGrupoEconomico.getText();
        txtNameGrupoEconomico.selectAll();
         
    }//GEN-LAST:event_txtNameGrupoEconomicoFocusGained

    private void jcbLocalVendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLocalVendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbLocalVendActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCiu;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkClComp;
    private javax.swing.JCheckBox chkCliSinVen;
    private javax.swing.JCheckBox chkCliVenAct;
    private javax.swing.JCheckBox chkCliVenIna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JComboBox<String> jcbLocalVend;
    private javax.swing.JLabel lblCiu;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JButton tbnGrupoEconomico;
    private javax.swing.JTextField txtAnio;
    private javax.swing.JTextField txtCiu;
    private javax.swing.JTextField txtCodCiu;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtMes;
    private javax.swing.JTextField txtNameGrupoEconomico;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objHisTblBasDat=new ZafHisTblBasDat();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodEmp.setVisible(false);   
            txtCodCiu.setVisible(false);
            txtCiu.setVisible(false);
            butCiu.setVisible(false);
            lblCiu.setVisible(false);
            
            txtCodVen.setVisible(false);
            txtNomVen.setVisible(false);
            butVen.setVisible(false);
            lblVen.setVisible(false);
            
            butLoc.setVisible(false);
            txtNomLoc.setVisible(false);
            txtCodLoc.setVisible(false);

            if(objParSis.getCodigoUsuario()!=1){
                btnAsignar.setVisible(false);
            }
            //Configurar los JTables.
            configurarTblDat();
            configurarLocal();
            configurarGrupoEconomico();
            configurarVenConCli();
            configurarVenConCiudad();
            configurarVenConForPag();
            configurarVenConVen();
            configurarLocalVendedores();
            cargarDatosParaInsertar();
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
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(20);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_COD_CLI,"Cód.CLi.");
            vecCab.add(INT_TBL_IDE_CLI,"Identificación");
            vecCab.add(INT_TBL_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_COD_CIU,"Cod.Ciu.");
            vecCab.add(INT_TBL_NOM_CIU,"Ciudad");
            vecCab.add(INT_TBL_ST_NUE,"nuevo"); 
            vecCab.add(INT_TBL_COD_CFG,"CFG");
            vecCab.add(INT_TBL_FEC_MOD,"Fec.Mod." );
            
             
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
          
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
                      
             
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_COD_CLI).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_IDE_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_COD_CIU).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOM_CIU).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_ST_NUE).setPreferredWidth(100);
            
            tcmAux.getColumn(INT_TBL_COD_CFG).setPreferredWidth(30); 
            
            tcmAux.getColumn(INT_TBL_FEC_MOD).setPreferredWidth(70);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_LOC, tblDat);
           
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_CIU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_ST_NUE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_CFG, tblDat);
             
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            vecAux=null;
 
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_IDE_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_CIU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CIU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_ST_NUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_CFG).setCellRenderer(objTblCelRenLbl);
            
//            TableColumn columnCodcli = tblDat.getColumnModel().getColumn(INT_TBL_COD_CLI);
//            tblDat.removeColumn(columnCodcli); // Remueve la columna del modelo de columnas

            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLbl.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    if (objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_ST_NUE)).equals("P")){
                        objTblCelRenLbl.setBackground(Color.CYAN);
                    }
                    else{
                         if (objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_DAT_LIN)).equals("M")){
                            objTblCelRenLbl.setBackground(colFonColCre);
                        }
                        else {
                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                    }
                }
            });
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
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
    
     /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--){
                 objTblMod.removeColumn(tblDat, i);                
            }

        }
        catch(Exception e){  
             
            objUti.mostrarMsgErr_F1(this, e);  
            blnRes=false;      
        }
        return blnRes;
    
    }
   
    
    public localSql obtenerLocalSeleccionado(String nomLocSele) {
        for (localSql local : locales) {
        if (local.getTx_nom().equalsIgnoreCase(nomLocSele)) {
            return local; // Devuelve el objeto LocalSql si encuentra el nombre
        }
    }
    return null;
    }
    /**
     * Función que carga los usuarios que tienen asignados vistos buenos.
     * @return 
     */ 
    private boolean obtenerColumnasAdicionar(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{  
            // conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            Object selectedItem = jcbLocalVend.getSelectedItem();
            localVendedor=0;
            if (selectedItem.equals("Todos")){
            localVendedor=0;
            }else{
            String itemString = selectedItem.toString().trim(); // Trim para eliminar espacios al inicio y al final
            localSql LocalSeleccionado= obtenerLocalSeleccionado(itemString);
            localVendedor=LocalSeleccionado.getCo_loc();
            }
           // if (conLoc!=null){
            //if (LocalSeleccionado!=null){
            //if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                /* Agrega columnas automáticamente de acuerdo a la cantidad de usuarios. */
                strSql="";
                strSql+=" SELECT COUNT(a1.co_usr) as conteo  \n";
                strSql+=" FROM( \n";
                
                strSql+=" select 1 co_emp, a1.co_usr \n" +
                        " from tbm_usr a1\n" +
                        " where co_usr=9\n" +
                        "         union all "; //se add vendedor oficina            
 
                strSql+="       SELECT a2.co_emp, a1.co_usr \n";
                strSql+="       FROM tbm_usr AS a1 \n";
                strSql+="       INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                strSql+="       INNER JOIN tbr_usrLocPrgUsr  AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrRel)  \n";
                strSql+="       INNER JOIN tbm_usr as a4 ON (a3.co_usrRel=a4.co_usr)  ";
                strSql+="       WHERE a1.st_reg='A' AND a4.st_reg='A' AND  a3.co_usr="+objParSis.getCodigoUsuario() + " AND a3.co_mnu="+objParSis.getCodigoMenu();
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                    strSql+=" AND a3.co_emp="+objParSis.getCodigoEmpresa() ;//objParSis.getCodigoLocal(); 
                }
                if(txtCodEmp.getText().length()>0){
                    strSql+=" AND a3.co_emp="+txtCodEmp.getText();
                }
                if(txtCodLoc.getText().length()>0){
                    strSql+=" AND a3.co_loc="+txtCodLoc.getText();
                }
                if(localVendedor>0){
                    strSql+=" AND a3.co_loc="+localVendedor;
                }
                intNumColGrpUsrAdi=0;
                strSql+="       GROUP BY a2.co_emp, a1.co_usr, a2.st_reg  ";
                strSql+=" ) as a1  ";
                 
                System.out.println("obtenerColumnasAdicionar 1 " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                if(rstLoc.next()) 
                {
                    intNumColGrpUsrAdi=rstLoc.getInt("conteo");
                }
                rstLoc.close();                         
                rstLoc=null;

                /* Nombres de Usuarios */ 
                strSql ="";
                
                strSql=" select 1 co_emp, a1.co_usr,isnull(a1.tx_ced,'') tx_ced, a1.tx_usr, a1.tx_nom, a1.st_reg as st_regUsr, 'A' as st_regUsrEmp\n" +
                        " from tbm_usr a1\n" +
                        " where co_usr=9\n" +
                        "         union all "; //se add vendedor oficina            

                strSql+=" SELECT distinct  a2.co_emp, a1.co_usr,isnull(a1.tx_ced,'') tx_ced, a1.tx_usr, a1.tx_nom, a1.st_reg as st_regUsr, a2.st_reg  as st_regUsrEmp \n";
                strSql+=" FROM tbm_usr AS a1 \n";
                strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                strSql+=" INNER JOIN tbr_usrLocPrgUsr  AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrRel)  \n";
                strSql+=" INNER JOIN tbm_usr as a4 ON (a3.co_usrRel=a4.co_usr)  ";
                strSql+=" WHERE   a4.st_reg='A' AND  a3.co_usr="+objParSis.getCodigoUsuario() + " AND a3.co_mnu="+objParSis.getCodigoMenu();
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                    strSql+=" AND a3.co_emp="+objParSis.getCodigoEmpresa() ;//+objParSis.getCodigoLocal(); 
                }
                if(txtCodEmp.getText().length()>0){
                    strSql+=" AND a3.co_emp="+txtCodEmp.getText();
                }
                if(txtCodLoc.getText().length()>0){
                    strSql+=" AND a3.co_loc="+txtCodLoc.getText();
                }
                if(localVendedor>0){
                    strSql+=" AND a3.co_loc="+localVendedor;
                }
            //    strSql+=" GROUP BY a2.co_emp, a1.co_usr, a2.st_reg  \n";
                strSql+=" and a1.st_reg='A' ORDER BY a1.tx_usr  \n";
                 System.out.println("obtenerColumnasAdicionar 2 " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                arlDatUsr =new ArrayList();
                while(rstLoc.next()){
                    arlRegUsr= new ArrayList();
                    arlRegUsr.add(INT_ARL_DAT_USR_COD_USR, rstLoc.getString("co_usr"));
                    arlRegUsr.add(INT_ARL_DAT_USR_NOM_USR, rstLoc.getString("tx_nom"));
                    arlDatUsr.add(arlRegUsr);
                }
                rstLoc.close();                         
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            //}
            conLoc.close();
            conLoc=null;
        }
        catch (Exception e){         
            blnRes = false;     
            objUti.mostrarMsgErr_F1(this, e);      
        }
        return blnRes;
   }
     
    private ZafTblCelEdiChk objTblCelEdiChk;
    private int intColIni, intColFin; 
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
     
    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        javax.swing.table.TableColumn tbc1;
        try{
            intColIni=objTblMod.getColumnCount();
            int intIndColGrp =(intColIni*INT_TBL_DAT_FAC_NUM_CDI); //Obtengo Indice de la Columna.
           
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_COD_CLI);
            arlColHid.add(""+INT_TBL_COD_EMP);
            arlColHid.add(""+INT_TBL_COD_LOC);
            arlColHid.add(""+INT_TBL_COD_CIU);
            arlColHid.add(""+INT_TBL_ST_NUE);
            //arlColHid.add(""+INT_TBL_COD_CFG);
             
            Vector vecAux=new Vector();
            try{
                for(int i=0; i<intNumColGrpUsrAdi ; i++){ //Cantidad de usuarios*Número de columnas dinámicas por usuario.
               // for(int i=0; i<(intNumColGrpUsrAdi/* *INT_TBL_DAT_NUM_TOT_CDI*/); i++){ //Cantidad de usuarios*Número de columnas dinámicas por usuario.
                //Creo las columna dinamica.  
                tbc=new javax.swing.table.TableColumn(intIndColGrp);                  
                tbc.setHeaderValue(objUti.getStringValueAt(arlDatUsr, i, INT_ARL_DAT_USR_COD_USR));
                tbc.setPreferredWidth(30);
                tbc.setResizable(false); 
                
                objTblMod.addColumn(tblDat, tbc);

                for (int j = 0; j < objTblMod.getRowCountTrue(); j++) {
                    objTblMod.setValueAt( objUti.getStringValueAt(arlDatUsr, i, INT_ARL_DAT_USR_COD_USR), j, intIndColGrp );
                }
                
                   //columnas ocultas
                arlColHid.add(""+intIndColGrp);

                intIndColGrp++;
         
                tbc1=new javax.swing.table.TableColumn((intIndColGrp));              
                tbc1.setHeaderValue(objUti.getStringValueAt(arlDatUsr, i, INT_ARL_DAT_USR_NOM_USR));
                tbc1.setPreferredWidth(100);
                tbc1.setResizable(true);                
                
                //Configurar JTable: Establecer columnas editables.
                vecAux.add("" + (intIndColGrp));
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc1);
                 
                intIndColGrp++;

            }
          //  Actualizo el campo de modificado
              int rowCount= tblDat.getRowCount();
                 for (int row = 0; row < rowCount; row++) {
                    objTblMod.setValueAt(" ",row,INT_TBL_DAT_LIN);
              }
                 
            if (intNumColGrpUsrAdi > 0){
                //objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
                objTblMod.setSystemHiddenColumnsDinami(arlColHid, tblDat);
            }
            
            }catch(Exception e){
            blnRes=false;
            System.out.println("Error " + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
            }
            
            
            
         //    objTblMod.addSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            objTblMod.addColumnasEditables(vecAux);
            objTblCelRenChk=new ZafTblCelRenChk();
            objTblCelEdiChk = new ZafTblCelEdiChk();
            objTblCelRenLblCod=new ZafTblCelRenLbl();
             
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            for(int j=0;j<vecAux.size();j++){
                //Establecer: Check
                tcmAux.getColumn(Integer.parseInt(vecAux.get(j).toString())).setCellRenderer(objTblCelRenChk);
                //Metodos de Validaciones
                tcmAux.getColumn(Integer.parseInt(vecAux.get(j).toString())).setCellEditor(objTblCelEdiChk);
                //Agregue hacer que la columna donde pondre el codigo de los vendedores aparezca no salia sin eso :(  JM 
                tcmAux.getColumn((Integer.parseInt(vecAux.get(j).toString())) - 1 ).setCellRenderer(objTblCelRenLblCod);
            }
            vecAux=null;
//            objTblCelRenChk=null;
//            objTblCelRenLbl=null;

            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    int intFil = -1;
                    intFil = objTblCelRenLbl.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    if (objUti.parseString(objTblMod.getValueAt(intFil, INT_TBL_ST_NUE)).equals("P")){
                        objTblCelRenChk.setBackground(Color.CYAN);
                    }
                    else{
                         if (objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_DAT_LIN)).equals("M")){
                            objTblCelRenChk.setBackground(colFonColCre);
                        }
                        else {
                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                    }
                }
            });

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                }     
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intFil= tblDat.getSelectedRow()== -1 ? 0 :tblDat.getSelectedRow();
                    int intCol= tblDat.getSelectedColumn()== -1 ? 0 :tblDat.getSelectedColumn();
                    //Valida que exista aunque sea un visto bueno seleccionado en la fila anterior o en la fila visto bueno 1.
                    if(intFil>-1){
                        if(intCol>-1){
                            if(objTblMod.isChecked(intFil, intCol)){
                                for(int j=0;j<objTblMod.getColumnCount();j++){
                                    if(objTblMod.isCellEditable(intFil, j)){
                                        if(j!=intCol){
                                            objTblMod.setValueAt( new Boolean(false), intFil, j);
                                            if(intContadorTiempo==0){
                                                blnEjecutar=true;
                                                isDatosCambiados();   
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            });
            objTblCelEdiChk=null;           
            intColFin=objTblMod.getColumnCount();           
        }
        catch(Exception e){
            blnRes=false;
            System.out.println("Error " + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    /**
     * 
     * @return 
     */
    private boolean guardarDat(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            //conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc.setAutoCommit(false);
            if(guardarDet(conLoc)){
                conLoc.commit();
                blnRes=true;
            }
            else{
                conLoc.rollback();
            }
            conLoc.close();
            conLoc=null;
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDet(java.sql.Connection conExt){
        int intNumFil;
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                intNumFil=objTblMod.getRowCountTrue();
                for (int intFil=0; intFil<intNumFil;intFil++){/*JM: Todas las filas */
                  Object numIdenti =  (objTblMod.getValueAt(intFil,INT_TBL_IDE_CLI));
                  String numIdentiSinEspacios = ((String) numIdenti).trim();
                  
                    if (objUti.parseString(objTblMod.getValueAt(intFil,0)).equals("M")){/*JM: Las filas modificadas */
                        if(objTblMod.isCheckedAnyColumn(intFil)){/*JM: Las filas modificadas */
                             
                            //Armar la sentencia SQL.
                            for(int intCol=intColIni/*JM 0*/;intCol<objTblMod.getColumnCount();intCol++){ /*JM: Busco el vendedor en las dinamicas */
                                if(objTblMod.isCellEditable(intFil, intCol)){ /*JM: busco las columnas editables */
                                    if(objTblMod.isChecked(intFil, intCol)){ /*JM: si es el vendedor elegido */
                                      
                                    
                                        strSql="";
                                        //strSql+=" INSERT INTO tbm_preCfgCliLoc(co_emp, co_loc,co_cli, co_cfg, co_usrVen, tx_obs1, fe_ing, co_usrIng, tx_comIng)  \n";
                                        strSql+=" INSERT INTO tbm_preCfgCliLoc(co_emp, co_loc,tx_cli, st_reg,co_cfg, co_reg,co_usrVen, tx_obs1, fe_ing, co_usrIng, tx_comIng)  \n";
                                        strSql+=" VALUES ( "+objTblMod.getValueAt(intFil,INT_TBL_COD_EMP)+", ";
                                        strSql+=" "+objTblMod.getValueAt(intFil,INT_TBL_COD_LOC)+",'";
                                        //strSql+=" "+objTblMod.getValueAt(intFil,INT_TBL_COD_CLI)+", ";
                                        strSql+=   numIdentiSinEspacios +"','A', ";
                                        strSql+=" "+( objTblMod.getValueAt(intFil,INT_TBL_COD_CFG)==null?1:(Integer.parseInt(objTblMod.getValueAt(intFil,INT_TBL_COD_CFG).toString())+1) )+", ";  
                                        strSql+= getCodigoRegistro(conExt, objTblMod.getValueAt(intFil,INT_TBL_COD_EMP).toString(),objTblMod.getValueAt(intFil,(intCol-1)).toString(), objTblMod.getValueAt(intFil,INT_TBL_COD_LOC).toString() ,numIdentiSinEspacios  ) +",";
                                        /* JM:  Si no posee configuracion se graba 1, caso contrario toma la configuracion y le suma 1 */
                                        strSql+=" "+objTblMod.getValueAt(intFil,(intCol-1))+", "; /* JM:  Vendedor -1 es para el codigo */
                                        strSql+=" NULL,CURRENT_TIMESTAMP, "+objUti.codificar(objParSis.getCodigoUsuario(),2)+","+ objUti.codificar(objParSis.getNombreComputadoraConDirIP(), 0) ;
                                        strSql+=" ); \n";
                                        //System.out.println("==> "+strSql);
                                        stm.executeUpdate(strSql);
                                    }
                                }
                            }
                        }else{
                        //para omitir la pre-configuracion 
                        strSql="";
                        strSql="UPDATE tbm_preCfgCliLoc ";
                        strSql+="SET ST_REG='I'";
                        strSql+=" WHERE  co_emp=" + objTblMod.getValueAt(intFil,INT_TBL_COD_EMP) +" and co_loc="+ objTblMod.getValueAt(intFil,INT_TBL_COD_LOC) ;
                        strSql+=" and tx_cli='"+ numIdentiSinEspacios +"'" ;
                        stm.executeUpdate(strSql);

                        }
                    }
                }
                stm.close();
                stm=null;
                
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    /**
     * 
     * @return 
     */
    private boolean cargarDetReg(){
        boolean blnRes=false;
   
        try{ 
//            Object selectedItem = jcbLocalVend.getSelectedItem();         
//
//            if (selectedItem != null  ){
            
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if(eliminaColumnasAdicionadas()) {
                if(obtenerColumnasAdicionar()) {
                    if(agregarColTblDat()) {
                        lblMsgSis.setText("Cargando datos...");
                        if(setClientes()){
                            objTblMod.initRowsState();
                            blnRes=true;
                        }
                    }
                }
            }
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false); 
           // }
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
    private boolean cargarDatosParaInsertar(){
        boolean blnRes=true;
        try{
            //Obtiene Fecha de Base de Datos
            Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());

            //Date datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            String date = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos());
            LocalDate objDate = LocalDate.parse(date);
            objDate.plusMonths(1);
            txtAnio.setText(""+objDate.getYear());
            txtMes.setText(""+objDate.plusMonths(1).getMonth());             
        }
        catch(Exception e){        
            blnRes=false;         
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
    }
     
    
    /**
     * Función que generqa las filas de clientes segun el filtro
     */ 
    private boolean setClientes(){
        boolean blnRes=true; 
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

           // conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());; 
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSql="";
                 
                strSql+=" SELECT a.co_emp, a.co_loc, trim(a.tx_ide) tx_ide , trim(a.tx_nomCli) tx_nomCli,       (case a.st_regCli when 0 then 'A' else 'I' end )st_regCli, \n";
                strSql+="        a.tx_nomCiu, a.co_cfg, a.fe_ing, a.pintar, a.co_tipven,   \n";
                strSql+="        a.co_usr, a.tx_usr, a.tx_nomUsr, a.st_regUsr  ,  a.co_usrtbr_cliLoc\n";
                strSql+=" FROM ( \n";
                strSql+="       SELECT a2.co_emp, a2.co_loc,  a1.CUSTNMBR tx_ide, a1.CUSTNAME as tx_nomCli , a1.INACTIVE as st_regCli,  \n";
                strSql+="              a1.CITY as tx_nomCiu,  a7.co_tipven,  \n";
                strSql+="              a3.co_cfg, a7.co_usr as co_usrtbr_cliLoc  ,  \n";
                strSql+="              CAST( CASE WHEN a3.fe_ing  IS NULL THEN '2018-05-16' ELSE a3.fe_ing END as DATE) as fe_ing , ";
                strSql+="              CASE WHEN a4.co_usr IS NULL THEN a7.co_usr ELSE a4.co_usr END AS co_usr,   \n";
                strSql+="              CASE WHEN a4.tx_usr IS NULL THEN a7.tx_usr ELSE a4.tx_usr END AS tx_usr, \n";
                strSql+="              CASE WHEN a4.tx_nom IS NULL THEN a7.tx_nom ELSE a4.tx_nom END as tx_nomUsr,  \n";
                strSql+="              CASE WHEN a4.st_reg IS NULL THEN a7.st_reg ELSE a4.st_reg END as st_regUsr,   \n";
                strSql+="              CASE WHEN a3.co_cfg IS NOT NULL AND a3.co_usrVen <> a2.co_ven THEN 'P' ELSE 'N' END as pintar  ";
                strSql+="       FROM tuval.dbo.RM00101 as a1   \n";
                
                if (objParSis.getCodigoEmpresa() >0 ){
                    strSql+="       INNER JOIN tbr_cliLoc as a2 ON ( a2.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.CUSTNMBR=a2.tx_cli)  \n";
                }
                else {
                    strSql+="       INNER JOIN tbr_cliLoc as a2 ON ( a2.co_emp=1 AND a1.CUSTNMBR=a2.tx_cli)  \n";
                }
                strSql+="       LEFT OUTER JOIN tbm_usr as a7 ON (a2.co_ven=a7.co_usr) ";
                strSql+="       LEFT OUTER JOIN ( \n";
                strSql+="               SELECT a1.co_emp, a1.co_loc, a1.tx_cli, a1.co_cfg, a1.co_usrVen, a1.tx_obs1, a1.fe_ing, a1.co_usrIng, a1.tx_comIng \n";
                strSql+="               FROM tbm_preCfgCliLoc as a1 \n";
                strSql+="               INNER JOIN ( \n";
                strSql+="                   SELECT a3.co_emp, a3.co_loc, a3.tx_cli, MAX(a3.co_cfg) as co_cfg   \n";
                strSql+="                   FROM tbm_preCfgCliLoc as a3   \n";
                strSql+="                   WHERE 1=1  and a3.st_reg='A' ";
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                    strSql+=" AND a3.co_emp="+objParSis.getCodigoEmpresa()+ " AND a3.co_loc="+localVendedor+"  "; // objParSis.getCodigoLocal()+"  ";
                }else{
                    strSql+=" AND a3.co_emp=1 AND a3.co_loc="+localVendedor+"  "; // objParSis.getCodigoLocal()+"  ";
                }
                    
                if(txtCodEmp.getText().length()>0){
                    strSql+=" AND a3.co_emp="+txtCodEmp.getText()+" ";
                } 
//                if(txtCodLoc.getText().length()>0){
//                    strSql+=" AND a3.co_loc="+txtCodLoc.getText()+" ";
//                }

                if(txtCodCli.getText().length()>0){
                    strSql+=" AND a3.tx_cli='"+txtCodCli.getText()+"' ";
                }
                strSql+="\n";
                strSql+="                   GROUP BY  a3.co_emp, a3.co_loc, a3.tx_cli \n";
                strSql+="               ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.tx_cli=a2.tx_cli AND a1.co_cfg=a2.co_cfg) WHERE A1.st_reg='A' \n";
//                strSql+="               WHERE 1=1 ";
//                if(txtCodVen.getText().length()>0){
//                    strSql+=" AND a1.co_usrVen="+txtCodVen.getText()+" \n";
//                }
                strSql+="\n";
                strSql+="       ) as a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.tx_cli=a3.tx_cli)\n";
                strSql+="       LEFT OUTER JOIN tbm_usr as a4 ON (a3.co_usrVen=a4.co_usr)  \n";
               // strSql+="       LEFT OUTER JOIN tbm_ciu as a5 ON (a1.co_ciu=a5.co_ciu)  \n";
                strSql+="       LEFT OUTER JOIN tbr_locUsr as a6 ON (a3.co_emp=a6.co_emp AND a3.co_loc=a6.co_loc AND a3.co_usrVen=a6.co_usr) \n";
                strSql+="       WHERE a1.INACTIVE=0  ";
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                    strSql+=" AND a2.co_emp="+objParSis.getCodigoEmpresa() + " AND a2.co_loc="+localVendedor +" ";//objParSis.getCodigoLocal()+" ";
                }
                else{
                    strSql+=" AND a2.co_emp=1 AND a2.co_loc="+localVendedor +" ";
                }
                if(txtCodEmp.getText().length()>0){
                    strSql+=" AND a2.co_emp="+txtCodEmp.getText()+" ";
                } 
//                if(txtCodLoc.getText().length()>0){
//                    strSql+=" AND a2.co_loc="+txtCodLoc.getText()+" ";
//                }
                if(txtCodCli.getText().length()>0){
                    strSql+=" AND a1.custnmbr='"+txtCodCli.getText()+"' ";
                }
//                if(txtCodCiu.getText().length()>0){
//                    strSql+=" AND a1.co_ciu="+txtCodCiu.getText();
//                }
             
                strSql+="\n";
                strSql+=") as a   \n";
                
                  // -----------------------------------
                if(txtForPag.getText().length()>0){
                    strSql+="inner join  TUVAL.DBO.RM00101 cli on a.tx_ide=cli.CUSTNMBR and cli.PYMTRMID='"+txtForPag.getText()+ "'";
                }
                if(txtNameGrupoEconomico.getText().length()>0){
                    strSql+=" inner join tuval.[dbo].[MSW_vi_datos_adicionales_clientes_1] s on a.tx_ide=s.CUSTNMBR and s.GRUPO='"+txtNameGrupoEconomico.getText()+ "'";
                }
                // -----------------------------------

                strSql+="WHERE a.st_regCli=0 \n";
                
                
                if(txtCodVen.getText().length()>0){
                    strSql+=" AND a.co_usr="+txtCodVen.getText();
                }
           
                 
                if((chkCliSinVen.isSelected()) || (chkCliVenAct.isSelected()) || (chkCliVenIna.isSelected()) ){
                    strSql+=" AND ( ";
                    if(chkCliSinVen.isSelected()){
                        strSql+=" a.co_usr IS NULL  or  a.co_usr =9 ";
                        if((chkCliVenAct.isSelected()) || (chkCliVenIna.isSelected())){
                            strSql+=" OR ";
                        }
                    }
                    if(chkCliVenAct.isSelected()){
                        strSql+=" a.st_regUsr = 'A' ";
                        if((chkCliVenIna.isSelected())){
                            strSql+=" OR ";
                        }
                    }
                    if(chkCliVenIna.isSelected()){
                        strSql+=" a.st_regUsr = 'I' ";
                    }
                    strSql+=" ) \n";
                }
                if(chkClComp.isSelected()){
                 strSql+=" AND EXISTS ( SELECT 1 FROM TUVAL.dbo.SOP30200 AS sc WHERE sc.CUSTNMBR = a.tx_ide)";
                }
                     
                strSql+=" and a.co_tipven <>3  ORDER BY a.tx_nomCli    \n";
                  System.out.println("setClientes \n" + strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    objTblMod.insertRow();
                    objTblMod.setValueAt( "", (objTblMod.getRowCount()-1) , INT_TBL_DAT_LIN );
                    objTblMod.setValueAt( rstLoc.getString("co_emp"),(objTblMod.getRowCount()-1), INT_TBL_COD_EMP);
                    objTblMod.setValueAt( rstLoc.getString("co_loc"),(objTblMod.getRowCount()-1), INT_TBL_COD_LOC);
                    
                    //objTblMod.setValueAt( rstLoc.getString("co_cli"),(objTblMod.getRowCount()-1), INT_TBL_COD_CLI);
                    objTblMod.setValueAt( rstLoc.getString("tx_ide"),(objTblMod.getRowCount()-1), INT_TBL_IDE_CLI);
                    objTblMod.setValueAt( rstLoc.getString("tx_nomCli"),(objTblMod.getRowCount()-1), INT_TBL_NOM_CLI);
                   // objTblMod.setValueAt( rstLoc.getString("co_ciu"),(objTblMod.getRowCount()-1), INT_TBL_COD_CIU);
                    objTblMod.setValueAt( rstLoc.getString("tx_nomCiu"),(objTblMod.getRowCount()-1), INT_TBL_NOM_CIU);
                    objTblMod.setValueAt( rstLoc.getString("pintar"),(objTblMod.getRowCount()-1), INT_TBL_ST_NUE);
                    objTblMod.setValueAt( rstLoc.getString("co_cfg"),(objTblMod.getRowCount()-1),INT_TBL_COD_CFG  );
                    
                    objTblMod.setValueAt(rstLoc.getString("fe_ing"), (objTblMod.getRowCount()-1), INT_TBL_FEC_MOD);
                    
                    //Columnas Dinámicas (Autorización y Visto Bueno Previo)
                    for(int j=(INT_TBL_DAT_CDI_AUT); j< objTblMod.getColumnCount(); j=j+INT_TBL_DAT_NUM_TOT_CDI){
                        objTblMod.setValueAt(objTblMod.getColumnName(j) , (objTblMod.getRowCount()-1) , j );
                        if(rstLoc.getInt("co_usr")==Integer.parseInt(objTblMod.getColumnName(j))){
                            objTblMod.setValueAt(true , (objTblMod.getRowCount()-1) , (j+1) );
                        }
                        else{
                            objTblMod.setValueAt(false , (objTblMod.getRowCount()-1) , (j+1) );
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch (java.sql.SQLException e){
            blnRes=false;
             
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){        
            blnRes=false;         
             
            objUti.mostrarMsgErr_F1(this, e);        
        }
        return blnRes;
        
        
        
    }     
     
       private int getCodigoRegistro(java.sql.Connection conExt, String CodEmp, String CodUsr, String loc, String cliente){
        int intRet=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSQL;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_reg) IS NULL THEN 1 ELSE MAX(a1.co_reg)+1 END AS maximoRegistro \n";
                strSQL+=" FROM tbm_preCfgCliLoc as a1 \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+"  and tx_cli='" + cliente + "' and co_loc=" + loc ;//+" AND a1.co_usrVen="+CodUsr;
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRet = rstLoc.getInt("maximoRegistro");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            intRet=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           intRet=-1;
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return intRet;
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
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg=""; 
                    strMsg+="<html>";
                    strMsg+="<h3 style='text-align:center;'>Colores utilizados en la tabla</h3>";
                    strMsg+="<table border='1'><tr><td><b>Fondo</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #00FFFF'>&nbsp;</td><td>Indica que esta pendiente de ser configurado en el periodo.</td></tr>";
                    strMsg+="<tr><td style='background-color: #78F078'>&nbsp;</td><td>Indica que está siendo modificado por el usuario. </td></tr>";
                    strMsg+="</table><br></html>";
                    break; 
                case INT_TBL_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_IDE_CLI:
                    strMsg="Identificación del cliente";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_COD_CIU:
                    strMsg="Código de la Ciudad";
                    break;
                case INT_TBL_NOM_CIU:
                    strMsg="Nombre de la ciudad";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * 
     * @return 
     */
      public static List<localSql> obtenerLocalesDesdeSQLServer(String conexion, String usuario, String clave) throws Exception {
        List<localSql> locales = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establecer conexión a SQL Server
            conn = DriverManager.getConnection(conexion, usuario, clave);

            // Consulta SQL
            String sql = "SELECT a1.co_emp, a1.tx_nom, a1.co_loc, a1.tx_ptoVta locZa\n" +
                         "FROM tbm_Loc1 AS a1\n" +
                         "INNER JOIN tbm_Loc a2 ON a1.tx_ptoVta = a2.tx_codSer AND a2.st_reg = 'A'\n" +
                         "WHERE a1.co_emp > 0 AND a1.st_reg = 'A'";

            // Preparar consulta
            pstmt = conn.prepareStatement(sql);

            // Ejecutar consulta
            rs = pstmt.executeQuery();

            // Procesar resultados
            while (rs.next()) {
                int co_emp = rs.getInt("co_emp");
                String tx_nom = rs.getString("tx_nom");
                int co_loc = rs.getInt("co_loc");
                String tx_ptoVta = rs.getString("locZa");

                // Crear objeto LocalSql y agregarlo a la lista
                localSql local = new localSql(co_emp, tx_nom, co_loc, tx_ptoVta);
                locales.add(local);
            }
        } finally {
            // Cerrar recursos
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }

        return locales;
    }
   
    private boolean configurarLocalVendedores(){
        boolean blnRes=true;
        try
        {
        objUti = new ZafUtil();
          try {
            jcbLocalVend.removeAllItems();
             // Obtener la lista de locales desde SQL Server
            locales = obtenerLocalesDesdeSQLServer(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());

            // Llenar el JComboBox con los locales
            for (localSql local : locales) {
                jcbLocalVend.addItem(local.getTx_nom().trim());
            }

            // Seleccionar el primer elemento si hay elementos en el combo
            if (!locales.isEmpty()) 
            {
                  jcbLocalVend.setSelectedIndex(0);
            }
 
        } catch (Exception ex) {
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "No se ha podido cargar los datos del local.";
        }
            blnRes=true;
        }catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarGrupoEconomico(){
        boolean blnRes=true;
        String aux="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("grupoEco");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("NombreGrupo Economico");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("150");
            //Armar la sentencia SQL.
            
                strSql="";
                strSql+=" Select distinct trim(STRGA255) as grupoEco  \n" +
                        " from tuval.dbo.EXT01101 where Field_ID = (select top 1 Field_ID from tuval.dbo.EXT20010(nolock) where FIELDNAM='Grupo' and STRGA255 <> '')\n" ;
           
            vcoLoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Grupo Economico", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     private boolean mostrarGrupoEconomico(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: 
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE){
                        //System.out.println("selecccionado "+vcoLoc.getValueAt(1));
                        txtNameGrupoEconomico.setText(vcoLoc.getValueAt(1));
                    }
                    break;
                case 1: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.grupoEco", txtNameGrupoEconomico.getText()))
                    {
                        txtNameGrupoEconomico.setText(vcoLoc.getValueAt(1));
                    } 
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtNameGrupoEconomico.setText(vcoLoc.getValueAt(1));
                          
                        }
                        else
                        {
                            txtNameGrupoEconomico.setText(strDesLargrupoEco);
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
    
        
    private boolean configurarLocal(){
        boolean blnRes=true;
        String aux="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("5");
            arlAncCol.add("50");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){ 
               if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSql="";
                    strSql+=" SELECT a1.co_emp,a1.co_loc ,a1.tx_nom";
                    strSql+=" FROM tbm_Loc1 AS a1 inner join tbm_Loc a2 on a1.tx_ptoVta=a2.tx_codSer and a2.st_reg='A'";
                    strSql+=" WHERE a1.co_emp>0 and a1.st_reg='A'";
                 //   strSql+=" ORDER BY a1.co_emp,a1.co_loc";
               }
               else{
                   strSql="";
                   strSql+=" SELECT a1.co_emp, a1.co_loc ,a1.tx_nom";
                    strSql+=" FROM tbm_Loc1 AS a1 inner join tbm_Loc a2 on a1.tx_ptoVta=a2.tx_codSer and a2.st_reg='A'";
                    strSql+=" WHERE a1.co_emp>0 and a1.st_reg='A' and a1.co_emp=" + objParSis.getCodigoEmpresa();
                 //   strSql+=" ORDER BY a1.co_emp,a1.co_loc";
               }
            }
            else{
                strSql="";
                strSql+=" SELECT a3.co_emp, a3.co_loc as co_loc, a3.tx_nom as tx_nom ";
                strSql+=" FROM tbr_locPrgUsr as a2";
                strSql+=" INNER JOIN tbm_loc1 as a3 ON (a2.co_emprel=a3.co_emp AND a2.co_locrel=a3.co_loc)"
                        + " inner join tbm_Loc a4 on a3.tx_ptoVta=a4.tx_codSer and a4.st_reg='A'";
                strSql+=" WHERE a2.st_reg in ('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario()+ " AND ";
                strSql+="       a2.co_emp=" + objParSis.getCodigoEmpresa()+" AND ";
                strSql+="       a2.co_loc=" + objParSis.getCodigoLocal()+" AND ";
                strSql+="       a2.co_mnu=" + objParSis.getCodigoMenu();
            //    strSql+=" ORDER BY a3.co_emp,a3.co_loc";
            }
           
            vcoLoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param intTipBus
     * @return 
     */
    
    private boolean mostrarLocal(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: 
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE){
                        txtCodEmp.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodEmp.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                           txtCodEmp.setText(vcoLoc.getValueAt(1));
                            txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                            txtNomLoc.setText(vcoLoc.getValueAt(3));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodEmp.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    } 
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoLoc.getValueAt(1));
                            txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                            txtNomLoc.setText(vcoLoc.getValueAt(3));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSql="";
                    strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                    strSql+=" FROM tbm_usr AS a1";
                    strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
              //      strSql+=" ORDER BY a1.tx_nom";
                }
                else{
                    strSql="";
                    strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                    strSql+=" FROM tbm_usr AS a1";
                    strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                    strSql+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa()+"   ";
                   // strSql+=" ORDER BY a1.tx_nom";
                }
            }
            else{
                strSql="";
                strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSql+=" FROM tbm_usr AS a1";
                strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSql+=" INNER JOIN tbr_usrLocPrgUsr  AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrRel) ";
                strSql+=" WHERE a3.co_emp="+objParSis.getCodigoEmpresa()+" AND ";
                strSql+="       a3.co_loc="+objParSis.getCodigoLocal()+" AND a3.co_mnu="+objParSis.getCodigoMenu()+"   ";   
             //   strSql+=" ORDER BY a1.tx_nom";
            }
            vcoVen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
     /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Clientes".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            //arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            //arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            //arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("394");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSql="";
//                strSql+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
//                strSql+=" FROM tbm_cli AS a1";
//                strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                strSql+=" AND a1.st_reg='A' AND a1.st_cli='S'";
               // strSql+=" ORDER BY a1.tx_nom";
                strSql+=" select  LTRIM(RTRIM(CUSTNMBR)) as tx_ide ,LTRIM(RTRIM(CUSTNAME)) as tx_nom from tuval.dbo.RM00101 where INACTIVE=0";

            }
            else
            {
                //Armar la sentencia SQL.
                strSql=" select   LTRIM(RTRIM(CUSTNMBR)) as tx_ide ,LTRIM(RTRIM(CUSTNAME)) as tx_nom \n" +
                        " from tuval.dbo.RM00101 a \n" +
                        " inner join Dynamics2021.dbo.tbr_cliloc b on b.tx_cli=a.CUSTNMBR\n" +
                        " where a.INACTIVE=0\n" +
                        " and b.co_loc="+ objParSis.getCodigoLocal();;
                
//                strSql+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
//                strSql+=" FROM tbm_cli AS a1";
//                strSql+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
//                strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//                strSql+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
//                strSql+=" AND a1.st_reg='A' AND a1.st_cli='S'";
               // strSql+=" ORDER BY a1.tx_nom";
            }
            vcoCli=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            
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
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2)); //3
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.tx_ide", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2));//3
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(2));//3
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtNomCli.setText(vcoCli.getValueAt(2)); //3
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtNomCli.setText(vcoCli.getValueAt(2));//
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Formas de pago".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
          //  arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_des");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
         //   arlAli.add("Código");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
        //    arlAncCol.add("60");
            arlAncCol.add("250");
            //Armar la sentencia SQL.
            strSql="";
            strSql="select ltrim(rtrim(PYMTRMID)) tx_des from tuval.dbo.SY03300";
            //strSql+="SELECT a1.co_forPag, a1.tx_des";
            //strSql+=" FROM tbm_cabForPag AS a1";
           // strSql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
          //  strSql+=" ORDER BY a1.tx_des";
            vcoForPag=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de formas de pago", strSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
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
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setVisible(true);
                    if (vcoForPag.getSelectedButton()==ZafVenConGP.INT_BUT_ACE){
                         //System.out.println("selecccionado "+vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(1));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
//                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
//                    {
//                        txtCodForPag.setText(vcoForPag.getValueAt(1));
//                        txtForPag.setText(vcoForPag.getValueAt(2));
//                    }
                    //else
                    //{
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            //txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(1));
                        }
//                        else
//                        {
//                            txtCodForPag.setText(strCodForPag);
//                        }
                    //}
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForPag.buscar("a1.tx_des", txtForPag.getText()))
                    {
                        //txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(1));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            //txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(1));
                        }
                        else
                        {
                            txtForPag.setText(strForPag);
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
    private boolean configurarVenConCiudad() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_ciu");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción.");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSql = "";
            strSql = "SELECT a.co_ciu, a.tx_deslar FROM tbm_ciu as a where a.st_reg='A'  ";

            vcoCiu = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSql, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     * @param campo
     * @param strBusqueda
     * @param tipo 
     */
    
    public void BuscarCiudad(String campo, String strBusqueda, int tipo) 
    {
        vcoCiu.setTitle("Listado de Ciudades");
        if (vcoCiu.buscar(campo, strBusqueda)) 
        {
            txtCodCiu.setText(vcoCiu.getValueAt(1));
            txtCiu.setText(vcoCiu.getValueAt(2));
            strCodCiu =  vcoCiu.getValueAt(1);
            strCiudad =  vcoCiu.getValueAt(2);
        }
        else 
        {
            vcoCiu.setCampoBusqueda(tipo);
            vcoCiu.setCriterio1(11); 
            vcoCiu.cargarDatos();
            vcoCiu.show();
            if (vcoCiu.getSelectedButton() == vcoCiu.INT_BUT_ACE) 
            {
                txtCodCiu.setText(vcoCiu.getValueAt(1));
                txtCiu.setText(vcoCiu.getValueAt(2));
                strCodCiu =  vcoCiu.getValueAt(1);
                strCiudad =  vcoCiu.getValueAt(2);
            } 
            else 
            {
                txtCodCiu.setText(strCodCiu);
                txtCiu.setText(strCiudad);
            }
        } 
    }
    
    /**
     * 
     * @return 
     */
    
    private boolean guardarAsignacion(){
        boolean blnRes=false;
        try{
            Librerias.ZafCfgVenLocCliGP.ZafCfgVenLocCliGP objCfgVenLocCliGP = new Librerias.ZafCfgVenLocCliGP.ZafCfgVenLocCliGP(objParSis, this);
            if(objCfgVenLocCliGP.modificaConfiguracionActual()){
                blnRes=true;
            }
            objCfgVenLocCliGP = null;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * 
     */
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (guardarDat()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                }
                else{
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);  
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
                butCon.doClick();
                objThrGua=null;
           }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    /**
     * Nuevo hilo para guardar, despues de un tiempo de inactividad
     */
    private class ZafThrGua2 extends Thread{
        public void run(){
            try{
                while(intContadorTiempo>=0){
                    System.out.println("Intenta ejecutar " + intContadorTiempo);
                    if(intContadorTiempo==0){
                        if (!guardarDat()){
                            lblMsgSis.setText("Listo");
                            pgrSis.setValue(0);         
                        }
                        butCon.doClick();
                        objThrGua2.stop();
                        objThrGua2=null;
                        blnEjecutar=true;
                    }
                    else{
                        intContadorTiempo--;
                        sleep(intTiempoHiloDormido);
                    }
                }
               
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    private boolean blnEjecutar=true;
    
    /**
     * Revisa si hay datos cambiados en el modelo de la tabla y si encuentra llama a un hilo, para guardar
     * @return 
     */
    
    private boolean isDatosCambiados(){
        boolean blnRes=false;
        try{
            if(blnEjecutar){
                if (objTblMod.isDataModelChanged()){
                    blnEjecutar=false;
                    intContadorTiempo=3;
                    if(objThrGua2==null){
                        System.out.println("llama al hilo... ");
                        objThrGua2=new ZafVen69.ZafThrGua2();
                        objThrGua2.start();
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
     * Cierra el hilo de guardado
     * @return 
     */
    private boolean cerrarHilo(){
        boolean blnRes=false;
        try{
            if (objThrGua2!=null){
                objThrGua2.stop();
                objThrGua2=null;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
}