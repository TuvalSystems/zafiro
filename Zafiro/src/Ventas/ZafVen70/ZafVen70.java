/*
 * ZafVen44.java
 *
 * Created on 16 de Septiembre de 2019
 */
package Ventas.ZafVen70;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author JoseMario
 */
public class ZafVen70 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_NOM_EMP=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_LOC=3;                     //Código alterno del ítem.
    static final int INT_TBL_DAT_NOM_LOC=4;                  //Código alterno 2 del ítem.
    static final int INT_TBL_DAT_BTN_USR=5;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_USR=6;                     //Total de unidades vendidas.
    static final int INT_TBL_DAT_NOM_USR=7;                     //Stock actual.
    
    static final int INT_TBL_DAT_BTN_COM_LOC=8;                    //Precio de venta 1.
    static final int INT_TBL_DAT_COD_COM_LOC=9;                     //Venta total.
    static final int INT_TBL_DAT_NOM_COM_LOC=10;                    //Costo total.
    static final int INT_TBL_DAT_COD_COM_LOC_ANT=11;                    //ANT
    
    static final int INT_TBL_DAT_BTN_COM_CLI=12;                    //Valor de ganancia.
    static final int INT_TBL_DAT_COD_COM_CLI=13;                    //Porcentaje de ganancia.
    static final int INT_TBL_DAT_NOM_COM_CLI=14;                //Peso total en Kilogramos.
    static final int INT_TBL_DAT_COD_COM_CLI_ANT=15;                    //ANT
    
    static final int INT_TBL_DAT_BTN_COM_CAN=16;                    //Bot. Canal   
    static final int INT_TBL_DAT_COD_COM_CAN=17;                    //Cod. Canal.
    static final int INT_TBL_DAT_NOM_COM_CAN=18;                //Desc. Canal   
    static final int INT_TBL_DAT_COD_COM_CAN_ANT=19;                    //ANT
    
    static final int INT_TBL_DAT_BTN_COM_LOC_EXT=20;                    //Bot. Loc. Ext       
    static final int INT_TBL_DAT_COD_COM_LOC_EXT=21;                    //Cod. Loc. Ext
    static final int INT_TBL_DAT_NOM_COM_LOC_EXT=22;                //Desc. Loc. Ext       
    static final int INT_TBL_DAT_COD_COM_LOC_EXT_ANT=23;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_VTA_TUB=24;                    //Bot. Vta.Tub.      
    static final int INT_TBL_DAT_COD_COM_VTA_TUB=25;                    //Cod. Vta.Tub.
    static final int INT_TBL_DAT_NOM_COM_VTA_TUB=26;                //Desc. Vta.Tub.   
    static final int INT_TBL_DAT_COD_COM_VTA_TUB_ANT=27;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_VTA_NOC=28;                    //Bot. Vta.NoComodity.      
    static final int INT_TBL_DAT_COD_COM_VTA_NOC=29;                    //Cod. Vta.NoComodity.
    static final int INT_TBL_DAT_NOM_COM_VTA_NOC=30;                //Desc. Vta.NoComodity.
    static final int INT_TBL_DAT_COD_COM_VTA_NOC_ANT=31;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_VTA_VAL=32;                    //Bot. Vta.Val.      
    static final int INT_TBL_DAT_COD_COM_VTA_VAL=33;                    //Cod. Vta.Val.
    static final int INT_TBL_DAT_NOM_COM_VTA_VAL=34;                //Desc. Vta.Val.    
    static final int INT_TBL_DAT_COD_COM_VTA_VAL_ANT=35;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_VTA_R=36;                    //Bot. Vta.R.      
    static final int INT_TBL_DAT_COD_COM_VTA_R=37;                    //Cod. Vta.R.
    static final int INT_TBL_DAT_NOM_COM_VTA_R=38;                //Desc. Vta.R.        
    static final int INT_TBL_DAT_COD_COM_VTA_R_ANT=39;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_PRE_PPE=40;                    //Bot. Pre.PPE      
    static final int INT_TBL_DAT_COD_COM_PRE_PPE=41;                    //Cod. Pre.PPE
    static final int INT_TBL_DAT_NOM_COM_PRE_PPE=42;                //Desc. Pre.PPE
    static final int INT_TBL_DAT_COD_COM_PRE_PPE_ANT=43;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_PRE=44;                    //Bot. Presupuesto      
    static final int INT_TBL_DAT_COD_COM_PRE=45;                    //Cod. Presupuesto
    static final int INT_TBL_DAT_NOM_COM_PRE=46;                //Desc. Presupuesto    
    static final int INT_TBL_DAT_COD_COM_PRE_ANT=47;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_PRE_CAL=48;                    //Bot. Pre. Calderos   
    static final int INT_TBL_DAT_COD_COM_PRE_CAL=49;                    //Cod. Pre. Calderos
    static final int INT_TBL_DAT_NOM_COM_PRE_CAL=50;                //Desc. Pre. Calderos
    static final int INT_TBL_DAT_COD_COM_PRE_CAL_ANT=51;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_PRE_TON=52;                    //Bot. Pre. Ton.
    static final int INT_TBL_DAT_COD_COM_PRE_TON=53;                    //Cod. Pre. Ton.
    static final int INT_TBL_DAT_NOM_COM_PRE_TON=54;                //Desc. Pre. Ton.    
    static final int INT_TBL_DAT_COD_COM_PRE_TON_ANT=55;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_COB=56;                    //Bot. Cob.
    static final int INT_TBL_DAT_COD_COM_COB=57;                    //Cod. Cob.
    static final int INT_TBL_DAT_NOM_COM_COB=58;                //Desc. Cob.        
    static final int INT_TBL_DAT_COD_COM_COB_ANT=59;                //ANT
    
    static final int INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN=60;                    //Bot. Cob.
    static final int INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN=61;                    //Cod. Cob.
    static final int INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN=62;                //Desc. Cob.        
    static final int INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT=63;                //ANT    
    
    static final int INT_TBL_DAT_BTN_COM_VTA_MAR=64;                    //Bot. Cob.
    static final int INT_TBL_DAT_COD_COM_VTA_MAR=65;                    //Cod. Cob.
    static final int INT_TBL_DAT_NOM_COM_VTA_MAR=66;                //Desc. Cob.        
    static final int INT_TBL_DAT_COD_COM_VTA_MAR_ANT=67;                //ANT     
    
    
     
     
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
     
   
    //private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenConGP vcoVen;                                   //Ventana de consulta.

    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux, strVersion="v 0.4";
    private Vector vecDat, vecCab, vecReg;
    
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
  
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
   
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoComLoc, objTblCelEdiTxtVcoComCli;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoComLoc, objTblCelEdiButVcoComCli;        //Editor: JButton en celda (Comision por Local).
    
    private ZafTblCelEdiButGen objTblCelEdiBut; 
    
    
   // private ZafVenCon vcoComLoc;            //
    //private ZafVenCon vcoComCliente;
    private ZafVenConGP vcoComLoc;            //
    private ZafVenConGP vcoComCliente;
    private ZafVenConGP vcoComCanal;
    private ZafVenConGP vcoComOtrLoc;    
    private ZafVenConGP vcoComVtaTub;        
    private ZafVenConGP vcoComVtaNoc;       
    private ZafVenConGP vcoComVtaVal;       
    private ZafVenConGP vcoComVtaR;       
    private ZafVenConGP vcoComPrePPE; 
    private ZafVenConGP vcoComPre; 
    private ZafVenConGP vcoComPreCal; 
    private ZafVenConGP vcoComPreTon; 
    private ZafVenConGP vcoComCob;     
    private ZafVenConGP vcoComTuvOtr;       
    private ZafVenConGP vcoComMar;     
    private ZafThrGua objThrGua;
    
    java.sql.Connection unConSqlServer = null;
    private int codBasGl=0;

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen70(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        chkMosLocIna = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 300));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los Vendedores");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 20);

        lblVen.setText("Vendedor:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(30, 60, 120, 20);

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
        txtCodVen.setBounds(150, 60, 56, 20);

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
        txtNomVen.setBounds(200, 60, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 60, 20, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        panFil.add(panLoc);
        panLoc.setBounds(20, 90, 640, 230);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

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

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 35));
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

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panBot.add(btnGuardar);

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

        setBounds(0, 0, 897, 506);
    }// </editor-fold>//GEN-END:initComponents

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed
  private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
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
  
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
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

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen))
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(1);
            }
        }
        else
            txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
            txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        { 
            txtCodVen.setText("");
            txtNomVen.setText("");
            
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(isCamVal()){
            if (objTblMod.isDataModelChanged()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                    if(objThrGua==null){
                        objThrGua=new ZafVen70.ZafThrGua();
                        objThrGua.start();
                    }
                }
            }
            else {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblDat();
            cargarLoc();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                   tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
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
            objTblOrdLoc=new ZafTblOrd(tblLoc);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_BTN_USR,"...");
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Usuario");
            vecCab.add(INT_TBL_DAT_BTN_COM_LOC,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_LOC,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_COD_COM_LOC_ANT,"Cód.ant.");            
            
            vecCab.add(INT_TBL_DAT_BTN_COM_CLI,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_CLI,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_CLI,"Clientes");
            vecCab.add(INT_TBL_DAT_COD_COM_CLI_ANT,"Cód.ant.");                        
            
            vecCab.add(INT_TBL_DAT_BTN_COM_CAN,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_CAN,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_CAN,"Canal");    
            vecCab.add(INT_TBL_DAT_COD_COM_CAN_ANT,"Cód.Ant.");
            
            vecCab.add(INT_TBL_DAT_BTN_COM_LOC_EXT,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_LOC_EXT,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_LOC_EXT,"Loc.Ext.");     
            vecCab.add(INT_TBL_DAT_COD_COM_LOC_EXT_ANT,"Cód.Ant.");  
            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_TUB,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_TUB,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_TUB,"Vta.Tub.");   
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_TUB_ANT,"Cód.Com.");            
            
            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_NOC,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_NOC,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_NOC,"Vta.Noc.");               
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_NOC_ANT,"Cód.Com.");            
            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_VAL,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_VAL,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_VAL,"Vta.Val."); 
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_VAL_ANT,"Cód.Com.");

            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_R,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_R,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_R,"Vta.R.");   
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_R_ANT,"Cód.Com.");
            
            vecCab.add(INT_TBL_DAT_BTN_COM_PRE_PPE,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_PPE,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_PRE_PPE,"Pre.PPE.");    
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_PPE_ANT,"Pre.PPE.");    
            
            vecCab.add(INT_TBL_DAT_BTN_COM_PRE,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_PRE,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_PRE,"Pre."); 
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_ANT,"Cód.Com.");            
            
            vecCab.add(INT_TBL_DAT_BTN_COM_PRE_CAL,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_CAL,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_PRE_CAL,"Pre.Cal.");   
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_CAL_ANT,"Cód.Com.");            
            
            vecCab.add(INT_TBL_DAT_BTN_COM_PRE_TON,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_TON,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_PRE_TON,"Pre.Ton.");  
            vecCab.add(INT_TBL_DAT_COD_COM_PRE_ANT,"Pre.Ton.");  
            
            vecCab.add(INT_TBL_DAT_BTN_COM_COB,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_COB,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_COB,"Cober..");            
            vecCab.add(INT_TBL_DAT_COD_COM_COB_ANT,"Cober..");   
            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN,"Cód.Tuv.Otr.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN,"Tuv.Otr..");            
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT,"Tuv.Otr..");   
            
            vecCab.add(INT_TBL_DAT_BTN_COM_VTA_MAR,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_MAR,"Cód.Mar.");
            vecCab.add(INT_TBL_DAT_NOM_COM_VTA_MAR,"Marg..");            
            vecCab.add(INT_TBL_DAT_COD_COM_VTA_MAR_ANT,"Marg..");               
 
    
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(210);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_LOC).setPreferredWidth(420);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_ANT).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_CLI).setPreferredWidth(420);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_CLI_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CAN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_CAN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_CAN).setPreferredWidth(420);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_CAN_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC_EXT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_EXT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_LOC_EXT).setPreferredWidth(420);    
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_EXT_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_TUB).setPreferredWidth(420); 
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_NOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_NOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_NOC).setPreferredWidth(420);  
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_NOC_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_VAL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_VAL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_VAL).setPreferredWidth(420);   
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_VAL_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_R).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_R).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_R).setPreferredWidth(420);   
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_R_ANT).setPreferredWidth(30);            
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_PPE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_PPE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_PPE).setPreferredWidth(420);        
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_PPE_ANT).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE).setPreferredWidth(420);      
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_ANT).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_CAL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_CAL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_CAL).setPreferredWidth(420); 
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_CAL_ANT).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_TON).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_TON).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_TON).setPreferredWidth(420);  
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_TON_ANT).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_COB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_COB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_COB).setPreferredWidth(420);              
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_COB_ANT).setPreferredWidth(30);   
            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN).setPreferredWidth(420);              
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT).setPreferredWidth(30);

            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_MAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_MAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_MAR).setPreferredWidth(420);              
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_MAR_ANT).setPreferredWidth(30);
 

            
            
                  
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BTN_USR, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_COM_LOC);
            vecAux.add("" + INT_TBL_DAT_COD_COM_LOC);
            vecAux.add("" + INT_TBL_DAT_BTN_COM_CLI);
            vecAux.add("" + INT_TBL_DAT_COD_COM_CLI);
            
            vecAux.add("" + INT_TBL_DAT_BTN_COM_CAN);
            vecAux.add("" + INT_TBL_DAT_COD_COM_CAN);
            vecAux.add("" + INT_TBL_DAT_BTN_COM_LOC_EXT);
            vecAux.add("" + INT_TBL_DAT_COD_COM_LOC_EXT); 
            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_TUB);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_TUB);             
            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_NOC);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_NOC);   
            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_VAL);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_VAL);      
            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_R);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_R);  
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE_PPE);      
            vecAux.add("" + INT_TBL_DAT_BTN_COM_PRE_PPE);
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE_PPE);   
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE);      
            vecAux.add("" + INT_TBL_DAT_BTN_COM_PRE);
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE);   
            vecAux.add("" + INT_TBL_DAT_BTN_COM_PRE_CAL);
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE_CAL);    
            vecAux.add("" + INT_TBL_DAT_BTN_COM_PRE_TON);
            vecAux.add("" + INT_TBL_DAT_COD_COM_PRE_TON); 
            vecAux.add("" + INT_TBL_DAT_BTN_COM_COB);
            vecAux.add("" + INT_TBL_DAT_COD_COM_COB); 
            
            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN); 

            vecAux.add("" + INT_TBL_DAT_BTN_COM_VTA_MAR);
            vecAux.add("" + INT_TBL_DAT_COD_COM_VTA_MAR);             
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CAN).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC_EXT).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB).setCellRenderer(objTblCelRenBut);            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_NOC).setCellRenderer(objTblCelRenBut);                  
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_VAL).setCellRenderer(objTblCelRenBut);               
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_R).setCellRenderer(objTblCelRenBut);             
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_PPE).setCellRenderer(objTblCelRenBut);        
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE).setCellRenderer(objTblCelRenBut);                    
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_CAL).setCellRenderer(objTblCelRenBut);  
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_TON).setCellRenderer(objTblCelRenBut);              
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_COB).setCellRenderer(objTblCelRenBut);                

            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN).setCellRenderer(objTblCelRenBut);    
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_MAR).setCellRenderer(objTblCelRenBut);                
            
            configurarTablasLocal();
            configurarTablasClientes();
            configurarTablasCobranzaCanal();
            configurarTablasCobranzaOtrosLocales();
            configurarTablasVtaTub();
            configurarTablasVtaNoc();
            configurarTablasVtaVal();            
            configurarTablasVtaR();                        
            configurarTablasPrePPE();             
            configurarTablasPre();                      
            configurarTablasPreCal();                              
            configurarTablasPreTon();    
            configurarTablasCob();
            configurarTablasTuvOtr();
            configurarTablasMarg();
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setCellEditor(objTblCelEdiBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setCellEditor(objTblCelEdiBut);          
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CAN).setCellEditor(objTblCelEdiBut);                  
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC_EXT).setCellEditor(objTblCelEdiBut);   
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB).setCellEditor(objTblCelEdiBut);              
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_NOC).setCellEditor(objTblCelEdiBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_VAL).setCellEditor(objTblCelEdiBut);            
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_R).setCellEditor(objTblCelEdiBut);              
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_PPE).setCellEditor(objTblCelEdiBut);   
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE).setCellEditor(objTblCelEdiBut);     
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_CAL).setCellEditor(objTblCelEdiBut);         
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_TON).setCellEditor(objTblCelEdiBut);                     
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_COB).setCellEditor(objTblCelEdiBut);                  
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN).setCellEditor(objTblCelEdiBut);  
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_MAR).setCellEditor(objTblCelEdiBut);              
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                int intFilSel, intColSel ;
                boolean blnUsar=true;
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if(intColSel==INT_TBL_DAT_BTN_COM_LOC)
                        mostrarVentanaTablas(2, intFilSel, intColSel);
                    else 
                    {
                        if(intColSel==INT_TBL_DAT_BTN_COM_CLI)
                            mostrarVentanaTablas(1, intFilSel, intColSel);
                        else
                        {
                            if(intColSel==INT_TBL_DAT_BTN_COM_CAN)
                                mostrarVentanaTablas(3, intFilSel, intColSel);
                            else
                            {
                                if(intColSel==INT_TBL_DAT_BTN_COM_LOC_EXT)
                                    mostrarVentanaTablas(4, intFilSel, intColSel);
                                else
                                {
                                    if(intColSel==INT_TBL_DAT_BTN_COM_VTA_TUB)                                    
                                        mostrarVentanaTablas(5, intFilSel, intColSel);
                                    else
                                    {
                                        if(intColSel==INT_TBL_DAT_BTN_COM_VTA_NOC)                                    
                                            mostrarVentanaTablas(6, intFilSel, intColSel);
                                        else
                                        {
                                            if(intColSel==INT_TBL_DAT_BTN_COM_VTA_VAL)                                    
                                                mostrarVentanaTablas(7, intFilSel, intColSel);     
                                            else
                                            {
                                                if(intColSel==INT_TBL_DAT_BTN_COM_VTA_R)                                    
                                                    mostrarVentanaTablas(8, intFilSel, intColSel);                                                                                                 
                                                else
                                                {
                                                    if(intColSel==INT_TBL_DAT_BTN_COM_PRE_PPE)                                    
                                                        mostrarVentanaTablas(9, intFilSel, intColSel);   
                                                    else
                                                    {
                                                        if(intColSel==INT_TBL_DAT_BTN_COM_PRE)                                    
                                                            mostrarVentanaTablas(10, intFilSel, intColSel);
                                                        else
                                                        {
                                                            if(intColSel==INT_TBL_DAT_BTN_COM_PRE_CAL)                                    
                                                                mostrarVentanaTablas(11, intFilSel, intColSel);     
                                                            else
                                                            {
                                                                if(intColSel==INT_TBL_DAT_BTN_COM_PRE_TON)                                    
                                                                    mostrarVentanaTablas(12, intFilSel, intColSel);  
                                                                else
                                                                {
                                                                    if(intColSel==INT_TBL_DAT_BTN_COM_COB)                                    
                                                                        mostrarVentanaTablas(13, intFilSel, intColSel);
                                                                    else
                                                                    {
                                                                        if(intColSel==INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN)                                    
                                                                            mostrarVentanaTablas(14, intFilSel, intColSel); 
                                                                        else
                                                                        {
                                                                            if(intColSel==INT_TBL_DAT_BTN_COM_VTA_MAR)                                    
                                                                                mostrarVentanaTablas(15, intFilSel, intColSel); 
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
            });
             
// 1. IMPORTANTE: Configurar el TableHeader para que soporte grupos (Evita el ClassCastException)
            tblDat.setTableHeader(new Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp(tblDat.getColumnModel()));
            
            // 2. Ahora sí podemos hacer el cast de forma segura
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();   
            objTblHeaGrp.setHeight(16 * 2); // Altura total de la cabecera (2 niveles)

            // 3. Crear y configurar el grupo "Datos"
            ZafTblHeaColGrp objTblHeaColGrpDatos = new ZafTblHeaColGrp("Datos");
            objTblHeaColGrpDatos.setHeight(16);
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EMP));         
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_NOM_LOC));         
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_BTN_USR));
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_COD_USR));         
            objTblHeaColGrpDatos.add(tcmAux.getColumn(INT_TBL_DAT_NOM_USR));
            
            // 4. Añadir el grupo a la cabecera principal
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDatos);
            
            
            ZafTblHeaColGrp objTblHeaColGrpLocal = new ZafTblHeaColGrp("Cobros Local");
            objTblHeaColGrpLocal.setHeight(16);
            objTblHeaColGrpLocal.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC));
            objTblHeaColGrpLocal.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC));         
            objTblHeaColGrpLocal.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_LOC));
            objTblHeaColGrpLocal.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_ANT));         
            
            // 4. Añadir el grupo a la cabecera principal
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpLocal);            
    
            
// Altura para las etiquetas de grupo
            int intAltHea = 16;
            ZafTblHeaColGrp objTblHeaColGrp;

            // --- GRUPO: Cobros Cliente ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Cobros Cliente");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_CLI));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_CLI));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_CLI_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Canal ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Canal");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CAN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_CAN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_CAN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_CAN_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Local Exterior ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Local Exterior");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC_EXT));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_EXT));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_LOC_EXT));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC_EXT_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Venta Tubos ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Venta Tubos");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_TUB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Venta No Commodity ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Venta No Commodity");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_NOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_NOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_NOC));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_NOC_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Venta Valor ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Venta Valor");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_VAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_VAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_VAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_VAL_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Venta R ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Venta R");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_R));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_R));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_R));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_R_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Presupuesto PPE ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Presupuesto PPE");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_PPE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_PPE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_PPE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_PPE_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Presupuesto ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Presupuesto");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Calderos ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Calderos");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_CAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_CAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_CAL));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_CAL_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Toneladas ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Toneladas");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_PRE_TON));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_TON));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_PRE_TON));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_PRE_TON_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

            // --- GRUPO: Cobranzas ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Cobranzas");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_COB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_COB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_COB));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_COB_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            
            // --- GRUPO: Tuveria Ortas Lineas ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Tuveria Ortas Lineas");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp); 
            
            // --- GRUPO: Margen ---
            objTblHeaColGrp = new ZafTblHeaColGrp("Margen");
            objTblHeaColGrp.setHeight(intAltHea);
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_BTN_COM_VTA_MAR));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_MAR));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_NOM_COM_VTA_MAR));
            objTblHeaColGrp.add(tcmAux.getColumn(INT_TBL_DAT_COD_COM_VTA_MAR));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrp);            
 

            
    

            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
             
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
             
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            
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
     * Se configuran las tablas de comision Local
     * @return 
     */
    
    private boolean configurarTablasLocal()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComLocal");
            arlCam.add("a1.tx_desLarComLocal");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComLocal, a4.tx_desLar as tx_desLarComLocal , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (2,3) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComLoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Comision Local", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    
    /**
     * Se configuran las tablas de comision Local
     * @return 
     */
    
    private boolean configurarTablasClientes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComClientes");
            arlCam.add("a1.tx_desLarComClientes");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComClientes, a4.tx_desLar as tx_desLarComClientes , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (1) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComCliente=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Comision Clientes", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComCliente.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    private boolean configurarTablasCobranzaCanal()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComCanal");
            arlCam.add("a1.tx_desLarComCanal");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComClientes, a4.tx_desLar as tx_desLarComClientes , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (5) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComCanal=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Comision Canal", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComCanal.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTablasCobranzaOtrosLocales()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComOtrLoc");
            arlCam.add("a1.tx_desLarComOtrLoc");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComOtrLoc, a4.tx_desLar as tx_desLarComOtrLoc , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (6) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComOtrLoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Otros Locales", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComOtrLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean configurarTablasVtaTub()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComVtaTub");
            arlCam.add("a1.tx_desLarComVtaTub");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComVtaTub, a4.tx_desLar as tx_desLarComVtaTub , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (7) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComVtaTub=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Vtas Tuberia", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComVtaTub.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    private boolean configurarTablasVtaNoc()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComVtaNoc");
            arlCam.add("a1.tx_desLarComVtaNoc");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComNoc, a4.tx_desLar as tx_desLarComNoc , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (7) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComVtaNoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Vtas. No Comidity", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComVtaNoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }            
    private boolean configurarTablasVtaVal()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComVtaVal");
            arlCam.add("a1.tx_desLarComVtaVal");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComVal, a4.tx_desLar as tx_desLarComVal , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (8) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComVtaVal=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Vtas. Valvula y Accesorio", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComVtaVal.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }               
    private boolean configurarTablasVtaR()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComVtaR");
            arlCam.add("a1.tx_desLarComVtaR");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComVtaR, a4.tx_desLar as tx_desLarComVtaR , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (10) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComVtaR=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Vtas. R", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComVtaR.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean configurarTablasPrePPE()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComPrePPE");
            arlCam.add("a1.tx_desLarComPrePPE");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComPrePPE, a4.tx_desLar as tx_desLarComPrePPE , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (11) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComPrePPE=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Pres. PPE", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComPrePPE.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }                      
    private boolean configurarTablasPre()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComPre");
            arlCam.add("a1.tx_desLarComPre");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComPre, a4.tx_desLar as tx_desLarComPre , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (12) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComPre=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Presupuesto", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComPre.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    private boolean configurarTablasPreCal()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComPreCal");
            arlCam.add("a1.tx_desLarComPreCal");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComPreCal, a4.tx_desLar as tx_desLarComPreCal , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (13) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComPreCal=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Presupuesto de Caldero", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComPreCal.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }           
    
    private boolean configurarTablasPreTon()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComPreTon");
            arlCam.add("a1.tx_desLarComPreTon");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComPreTon, a4.tx_desLar as tx_desLarComPreTon , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (14) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComPreTon=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Presupuesto en Tonelada", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComPreTon.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }           
    
    private boolean configurarTablasCob()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComCob");
            arlCam.add("a1.tx_desLarComCob");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComCob, a4.tx_desLar as tx_desLarComCob , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (15) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComCob=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Cobertura", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComCob.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }               

private boolean configurarTablasTuvOtr()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorTuvOtr");
            arlCam.add("a1.tx_desLarTuvOtr");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorVtaOtrLin, a4.tx_desLar as tx_desLarVtaOtrLin , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (16) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComTuvOtr=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Tuv Otras Lineas", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComTuvOtr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

private boolean configurarTablasMarg()//cramirez
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorMarg");
            arlCam.add("a1.tx_desLarMarg");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorMarg, a4.tx_desLar as tx_desLarMarg , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (17) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComMar=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Margen", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComMar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }     

    
    
    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            //con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            unConSqlServer= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            stm=unConSqlServer.createStatement();

            if (unConSqlServer!=null)
          
            //if (con!=null)
            {
                //stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc1 AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }else{
                        strSQL+=" WHERE a1.co_emp=1";
                    }                    
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc1 AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }else{
                        strSQL+=" WHERE a1.co_emp=1";
                    }  
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                //con.close();
                unConSqlServer.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
                vecDat.clear();
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
private boolean cargarDetReg()
{
    int intNumFilTblBod, i, j;
    boolean blnRes = true;
    Set<String> setUsrCargados = new HashSet<String>();
    String strSQL2 = "";

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");

        con = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (con != null)
        {
            stm = con.createStatement();

            strConSQL = "";

            intNumFilTblBod = objTblModLoc.getRowCountTrue();
            i = 0;
            strAux = "";

            for (j = 0; j < intNumFilTblBod; j++)
            {
                if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                {
                    if (i == 0)
                        strAux += " (a5.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP)
                               + " AND a5.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    else
                        strAux += " OR (a5.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP)
                               + " AND a5.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    i++;
                }
            }

            if (!strAux.equals(""))
            {
                strConSQL += " AND (" + strAux + ")";
            }

            strSQL = "";
            strSQL += " SELECT DISTINCT a2.co_emp, EMP.tx_nom as tx_nomEmp, a1.co_usr, a1.tx_nom,  \n";
            strSQL += "                 a3.co_tblComLocal, a3.co_reg, a3.tx_desCorComLocal, a3.tx_desLarComLocal ,   \n";
            strSQL += "                 a4.co_tblComCliente, a4.co_reg, a4.tx_desCorComCliente, a4.tx_desLarComCliente, \n";
            strSQL += "                 a6.co_tblComCanal, a6.co_reg, a6.tx_desCorComCanal, a6.tx_desLarComCanal, \n";
            strSQL += "                 a7.co_tblComOtrLoc, a7.co_reg, a7.tx_desCorComOtrLoc, a7.tx_desLarComOtrLoc, \n";
            strSQL += "                 a8.co_tblComVtaTub, a8.co_reg, a8.tx_desCorComVtaTub, a8.tx_desLarComVtaTub, \n";
            strSQL += "                 a9.co_tblComVtaNoc, a9.co_reg, a9.tx_desCorComVtaNoc, a9.tx_desLarComVtaNoc, \n";
            strSQL += "                 a10.co_tblComVtaVal, a10.co_reg, a10.tx_desCorComVtaVal, a10.tx_desLarComVtaVal, \n";
            strSQL += "                 a11.co_tblComVtaR, a11.co_reg, a11.tx_desCorComVtaR, a11.tx_desLarComVtaR, \n";
            strSQL += "                 a12.co_tblComPrePPE, a12.co_reg, a12.tx_desCorComPrePPE, a12.tx_desLarComPrePPE, \n";
            strSQL += "                 a13.co_tblComPre, a13.co_reg, a13.tx_desCorComPre, a13.tx_desLarComPre, \n";
            strSQL += "                 a14.co_tblComPreCal, a14.co_reg, a14.tx_desCorComPreCal, a14.tx_desLarComPreCal, \n";
            strSQL += "                 a15.co_tblComPreTon, a15.co_reg, a15.tx_desCorComPreTon, a15.tx_desLarComPreTon, \n";
            strSQL += "                 a16.co_tblComCob, a16.co_reg, a16.tx_desCorComCob, a16.tx_desLarComCob, \n";
            
            
            
            
            strSQL += "                 a17.co_tblComVtaOtrLin, a17.co_reg, a17.tx_desCorVtaOtrLin, a17.tx_desLarVtaOtrLin, \n";
            strSQL += "                 a18.co_tblComVtaMar, a18.co_reg, a18.tx_desCorVtaMar, a18.tx_desLarVtaMar \n";
            
            strSQL += " FROM tbm_usr as a1  \n";
            strSQL += " INNER JOIN tbr_usrEmp as a2 ON (a1.co_usr=a2.co_usr) \n";
            strSQL += " INNER JOIN tbr_locUsr as a5 ON (a2.co_emp=a5.co_emp AND a2.co_usr=a5.co_usr) \n";
            strSQL += " INNER JOIN tbm_emp as EMP ON (a2.co_emp=EMP.co_emp)  \n";

            strSQL += " LEFT OUTER JOIN (  \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComLocal, a3.co_reg, a4.tx_desCor as tx_desCorComLocal, a4.tx_desLar as tx_desLarComLocal  \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )  \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (2,3,4) AND a3.st_reg='I' \n";
            strSQL += " ) as a3 ON (EMP.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrVen) \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComCliente, a3.co_reg, a4.tx_desCor as tx_desCorComCliente, a4.tx_desLar as tx_desLarComCliente  \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (1) AND a3.st_reg='I' \n";
            strSQL += " ) as a4 ON (EMP.co_emp=a4.co_emp AND a2.co_usr=a4.co_usrVen) \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComCanal, a3.co_reg, a4.tx_desCor as tx_desCorComCanal, a4.tx_desLar as tx_desLarComCanal  \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (5) AND a3.st_reg='I' \n";
            strSQL += " ) as a6 ON (EMP.co_emp=a6.co_emp AND a2.co_usr=a6.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComOtrLoc, a3.co_reg, a4.tx_desCor as tx_desCorComOtrLoc, a4.tx_desLar as tx_desLarComOtrLoc   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (6) AND a3.st_reg='I' \n";
            strSQL += " ) as a7 ON (EMP.co_emp=a7.co_emp AND a2.co_usr=a7.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaTub, a3.co_reg, a4.tx_desCor as tx_desCorComVtaTub, a4.tx_desLar as tx_desLarComVtaTub   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (7) AND a3.st_reg='I' \n";
            strSQL += " ) as a8 ON (EMP.co_emp=a8.co_emp AND a2.co_usr=a8.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaNoc, a3.co_reg, a4.tx_desCor as tx_desCorComVtaNoc, a4.tx_desLar as tx_desLarComVtaNoc   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (8) AND a3.st_reg='I' \n";
            strSQL += " ) as a9 ON (EMP.co_emp=a9.co_emp AND a2.co_usr=a9.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaVal, a3.co_reg, a4.tx_desCor as tx_desCorComVtaVal, a4.tx_desLar as tx_desLarComVtaVal   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (9) AND a3.st_reg='I' \n";
            strSQL += " ) as a10 ON (EMP.co_emp=a10.co_emp AND a2.co_usr=a10.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaR, a3.co_reg, a4.tx_desCor as tx_desCorComVtaR, a4.tx_desLar as tx_desLarComVtaR   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (10) AND a3.st_reg='I' \n";
            strSQL += " ) as a11 ON (EMP.co_emp=a11.co_emp AND a2.co_usr=a11.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComPrePPE, a3.co_reg, a4.tx_desCor as tx_desCorComPrePPE, a4.tx_desLar as tx_desLarComPrePPE   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (11) AND a3.st_reg='I' \n";
            strSQL += " ) as a12 ON (EMP.co_emp=a12.co_emp AND a2.co_usr=a12.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComPre, a3.co_reg, a4.tx_desCor as tx_desCorComPre, a4.tx_desLar as tx_desLarComPre   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (12) AND a3.st_reg='I' \n";
            strSQL += " ) as a13 ON (EMP.co_emp=a13.co_emp AND a2.co_usr=a13.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComPreCal, a3.co_reg, a4.tx_desCor as tx_desCorComPreCal, a4.tx_desLar as tx_desLarComPreCal   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (13) AND a3.st_reg='I' \n";
            strSQL += " ) as a14 ON (EMP.co_emp=a14.co_emp AND a2.co_usr=a14.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComPreTon, a3.co_reg, a4.tx_desCor as tx_desCorComPreTon, a4.tx_desLar as tx_desLarComPreTon   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (14) AND a3.st_reg='I' \n";
            strSQL += " ) as a15 ON (EMP.co_emp=a15.co_emp AND a2.co_usr=a15.co_usrVen)  \n";

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComCob, a3.co_reg, a4.tx_desCor as tx_desCorComCob, a4.tx_desLar as tx_desLarComCob   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (15) AND a3.st_reg='I' \n";
            strSQL += " ) as a16 ON (EMP.co_emp=a16.co_emp AND a2.co_usr=a16.co_usrVen)  \n";
            





            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaOtrLin, a3.co_reg, a4.tx_desCor as tx_desCorVtaOtrLin, a4.tx_desLar as tx_desLarVtaOtrLin   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (16) AND a3.st_reg='I' \n";
            strSQL += " ) as a17 ON (EMP.co_emp=a17.co_emp AND a2.co_usr=a17.co_usrVen)  \n";            

            strSQL += " LEFT OUTER JOIN ( \n";
            strSQL += "       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComVtaMar, a3.co_reg, a4.tx_desCor as tx_desCorVtaMar, a4.tx_desLar as tx_desLarVtaMar   \n";
            strSQL += "       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
            strSQL += "       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
            strSQL += "       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
            strSQL += "       WHERE a5.co_basCal IN (17) AND a3.st_reg='I' \n";
            strSQL += " ) as a18 ON (EMP.co_emp=a18.co_emp AND a2.co_usr=a18.co_usrVen)  \n"; 
            
           

            strSQL += " WHERE a1.st_reg='A' AND a2.st_ven='S' AND a5.st_reg IN ('A', 'P') AND a1.co_usr NOT IN (9, 93, 156, 391, 217) ";
            

                    
            strSQL += strConSQL;

            if (txtCodVen.getText() != null)
            {
                if (txtCodVen.getText().trim().length() > 0)
                {
                    strSQL += " AND a1.co_usr=" + txtCodVen.getText().trim();
                }
            }

            strSQL += " ORDER BY a2.co_emp, a1.co_usr \n";

            rst = stm.executeQuery(strSQL);

            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");

            while (rst.next())
            {
                if (blnCon)
                {
                    String strCodUsr = getStringNull(rst, "co_usr");
                    setUsrCargados.add(strCodUsr);

                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP, getStringNull(rst, "co_emp"));
                    vecReg.add(INT_TBL_DAT_NOM_EMP, getStringNull(rst, "tx_nomEmp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC, "");
                    vecReg.add(INT_TBL_DAT_NOM_LOC, "");
                    vecReg.add(INT_TBL_DAT_BTN_USR, "");
                    vecReg.add(INT_TBL_DAT_COD_USR, strCodUsr);
                    vecReg.add(INT_TBL_DAT_NOM_USR, getStringNull(rst, "tx_nom"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_LOC, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_LOC, getStringNull(rst, "co_tblComLocal"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_LOC, getStringNull(rst, "tx_desLarComLocal"));
                    vecReg.add(INT_TBL_DAT_COD_COM_LOC_ANT, getStringNull(rst, "co_tblComLocal"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_CLI, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_CLI, getStringNull(rst, "co_tblComCliente"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_CLI, getStringNull(rst, "tx_desLarComCliente"));
                    vecReg.add(INT_TBL_DAT_COD_COM_CLI_ANT, getStringNull(rst, "co_tblComCliente"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_CAN, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_CAN, getStringNull(rst, "co_tblComCanal"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_CAN, getStringNull(rst, "tx_desLarComCanal"));
                    vecReg.add(INT_TBL_DAT_COD_COM_CAN_ANT, getStringNull(rst, "co_tblComCanal"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_LOC_EXT, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_LOC_EXT, getStringNull(rst, "co_tblComOtrLoc"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_LOC_EXT, getStringNull(rst, "tx_desLarComOtrLoc"));
                    vecReg.add(INT_TBL_DAT_COD_COM_LOC_EXT_ANT, getStringNull(rst, "co_tblComOtrLoc"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_TUB, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB, getStringNull(rst, "co_tblComVtaTub"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_TUB, getStringNull(rst, "tx_desLarComVtaTub"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_ANT, getStringNull(rst, "co_tblComVtaTub"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_NOC, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_NOC, getStringNull(rst, "co_tblComVtaNoc"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_NOC, getStringNull(rst, "tx_desLarComVtaNoc"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_NOC_ANT, getStringNull(rst, "co_tblComVtaNoc"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_VAL, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_VAL, getStringNull(rst, "co_tblComVtaVal"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_VAL, getStringNull(rst, "tx_desLarComVtaVal"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_VAL_ANT, getStringNull(rst, "co_tblComVtaVal"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_R, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_R, getStringNull(rst, "co_tblComVtaR"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_R, getStringNull(rst, "tx_desLarComVtaR"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_R_ANT, getStringNull(rst, "co_tblComVtaR"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_PRE_PPE, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_PPE, getStringNull(rst, "co_tblComPrePPE"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_PRE_PPE, getStringNull(rst, "tx_desLarComPrePPE"));
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_PPE_ANT, getStringNull(rst, "co_tblComPrePPE"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_PRE, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE, getStringNull(rst, "co_tblComPre"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_PRE, getStringNull(rst, "tx_desLarComPre"));
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_ANT, getStringNull(rst, "co_tblComPre"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_PRE_CAL, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_CAL, getStringNull(rst, "co_tblComPreCal"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_PRE_CAL, getStringNull(rst, "tx_desLarComPreCal"));
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_CAL_ANT, getStringNull(rst, "co_tblComPreCal"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_PRE_TON, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_TON, getStringNull(rst, "co_tblComPreTon"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_PRE_TON, getStringNull(rst, "tx_desLarComPreTon"));
                    vecReg.add(INT_TBL_DAT_COD_COM_PRE_TON_ANT, getStringNull(rst, "co_tblComPreTon"));

                    vecReg.add(INT_TBL_DAT_BTN_COM_COB, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_COB, getStringNull(rst, "co_tblComCob"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_COB, getStringNull(rst, "tx_desLarComCob"));
                    vecReg.add(INT_TBL_DAT_COD_COM_COB_ANT, getStringNull(rst, "co_tblComCob"));
                    

                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN, getStringNull(rst, "co_tblComVtaOtrLin"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN, getStringNull(rst, "tx_desLarVtaOtrLin"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT, getStringNull(rst, "co_tblComVtaOtrLin")); 
                    
                    vecReg.add(INT_TBL_DAT_BTN_COM_VTA_MAR, "");
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_MAR, getStringNull(rst, "co_tblComVtaMar"));
                    vecReg.add(INT_TBL_DAT_NOM_COM_VTA_MAR, getStringNull(rst, "tx_desLarVtaMar"));
                    vecReg.add(INT_TBL_DAT_COD_COM_VTA_MAR_ANT, getStringNull(rst, "co_tblComVtaMar"));                       

                    vecDat.add(vecReg);
                }
                else
                {
                    break;
                }
            }

            rst.close();
            rst = null;

            // =========================================================
            // 2DA CONSULTA: AGREGAR USUARIOS FALTANTES
            // =========================================================
            strSQL2 = "";
            strSQL2 += " SELECT a2.co_emp, EMP.tx_nom as tx_nomEmp, trim(a1.tx_nom) as nombre,a1.co_usr \n";
            strSQL2 += " FROM tbm_usr AS a1 \n";
            strSQL2 += "        INNER JOIN tbr_usrEmp as a2 ON (a1.co_usr=a2.co_usr) \n";            
            strSQL2 += "        INNER JOIN tbm_emp as EMP ON (a2.co_emp=EMP.co_emp)  \n";            
            strSQL2 += " WHERE a1.st_reg='A' and a1.co_tipven!=0 and a2.co_emp=1 \n";

            if (txtCodVen.getText() != null)
            {
                if (txtCodVen.getText().trim().length() > 0)
                {
                    strSQL2 += " AND a1.co_usr=" + txtCodVen.getText().trim() + " \n";
                }
            }

            strSQL2 += " ORDER BY a1.co_usr \n";

            rst = stm.executeQuery(strSQL2);

            while (rst.next())
            {
                if (blnCon)
                {
                    String strCodUsr = getStringNull(rst, "co_usr");
                    String strNomEmp = getStringNull(rst, "tx_nomEmp");
                    String strCodEmp = getStringNull(rst, "co_emp");

                    if (!setUsrCargados.contains(strCodUsr))
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP, strCodEmp);
                        vecReg.add(INT_TBL_DAT_NOM_EMP, strNomEmp);
                        vecReg.add(INT_TBL_DAT_COD_LOC, "");
                        vecReg.add(INT_TBL_DAT_NOM_LOC, "");
                        vecReg.add(INT_TBL_DAT_BTN_USR, "");
                        vecReg.add(INT_TBL_DAT_COD_USR, strCodUsr);
                        vecReg.add(INT_TBL_DAT_NOM_USR, getStringNull(rst, "nombre"));

                        vecReg.add(INT_TBL_DAT_BTN_COM_LOC, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_LOC, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_LOC, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_LOC_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_CLI, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_CLI, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_CLI, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_CLI_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_CAN, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_CAN, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_CAN, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_CAN_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_LOC_EXT, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_LOC_EXT, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_LOC_EXT, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_LOC_EXT_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_TUB, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_TUB, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_NOC, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_NOC, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_NOC, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_NOC_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_VAL, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_VAL, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_VAL, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_VAL_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_R, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_R, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_R, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_R_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_PRE_PPE, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_PPE, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_PRE_PPE, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_PPE_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_PRE, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_PRE, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_PRE_CAL, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_CAL, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_PRE_CAL, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_CAL_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_PRE_TON, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_TON, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_PRE_TON, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_PRE_TON_ANT, "");

                        vecReg.add(INT_TBL_DAT_BTN_COM_COB, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_COB, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_COB, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_COB_ANT, "");
                        
                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_TUB_OTR_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_TUB_OTR_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_TUB_OTR_LIN_ANT, ""); 
                        
                        vecReg.add(INT_TBL_DAT_BTN_COM_VTA_MAR, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_MAR, "");
                        vecReg.add(INT_TBL_DAT_NOM_COM_VTA_MAR, "");
                        vecReg.add(INT_TBL_DAT_COD_COM_VTA_MAR, "");                         

                        vecDat.add(vecReg);
                    }
                }
                else
                {
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
            tblDat.setModel(objTblMod);
            vecDat.clear();

            if (blnCon)
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            else
                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");

            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (rst != null)
                rst.close();
        }
        catch (Exception e) {}

        try
        {
            if (stm != null)
                stm.close();
        }
        catch (Exception e) {}

        try
        {
            if (con != null)
                con.close();
        }
        catch (Exception e) {}

        pgrSis.setIndeterminate(false);
        butCon.setText("Consultar");
    }

    return blnRes;
}
     
   private String getStringNull(java.sql.ResultSet rst, String strNomCam) throws java.sql.SQLException
{
    String strVal = rst.getString(strNomCam);
    return (strVal == null ? "" : strVal);
}
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        //Validar que esté seleccionada al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un local.<BR>Seleccione un local y vuelva a intentarlo.</HTML>");
            tblLoc.requestFocus();
            return false;
        }
       
        return true;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()throws Exception 

    {
        boolean blnRes=true;  
        java.sql.Statement stmSqlSer = null;
        String strcadSql="";   
 
        try
        {
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
            //System.out.println(objParSis.getStringConexion_Dynamics2021() + " " + objParSis.getUsuarioBaseDatos_Dynamics2021() + " " + objParSis.getClaveBaseDatos_Dynamics2021()  );
            unConSqlServer= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
            stmSqlSer=unConSqlServer.createStatement();

            if (unConSqlServer!=null)
            {
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){      
 
                //Armar la sentencia SQL.
                strcadSql="SELECT a1.co_usr,\n" +
                        "       a1.tx_usr,\n" +
                        "       a1.tx_nom\n" +
                        "FROM tbm_usr AS a1\n" +
                        "INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)\n" +
                        "WHERE a2.co_emp=1"+ //+ objParSis.getCodigoEmpresa()+
                        "  AND a1.st_reg='A'\n" +
                        "  AND a2.st_ven='S'\n" ;
                          
//                strSQL="";
//                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
//                strSQL+=" FROM tbm_usr AS a1";
//                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
//                strSQL+=" WHERE a1.st_reg='A' AND a2.st_ven='S'";
//                strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom";
//                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                 strcadSql="SELECT a1.co_usr,\n" +
                        "       a1.tx_usr,\n" +
                        "       a1.tx_nom\n" +
                        "FROM tbm_usr AS a1\n" +
                        "INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)\n" +
                        "WHERE a2.co_emp=" + objParSis.getCodigoEmpresa()+
                        "  AND a1.st_reg='A'\n" +
                        "  AND a2.st_ven='S'\n" ;
                
//                strSQL="";
//                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
//                strSQL+=" FROM tbm_usr AS a1";
//                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
//                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
//                strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S'";
//                strSQL+=" ORDER BY a1.tx_nom";
            }
                    
            //vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            vcoVen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strcadSql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            
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
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Empresa";
                    break;
                case INT_TBL_DAT_COD_USR:
                    strMsg="Código del usuario...";
                    break;
                case INT_TBL_DAT_NOM_USR:
                    strMsg="Nombre del usuario...";
                    break;
                case INT_TBL_DAT_BTN_COM_LOC:
                    strMsg="Muestra una ventana con las tablas de comisiones locales...";
                    break;
                case INT_TBL_DAT_BTN_COM_CLI:
                    strMsg="Muestra una ventana con las tablas de comisiones de los clientes configurados...";
                    break;
               
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     /*para guardar*/
    
// ===== CLASE AUXILIAR =====
private class CampoComision
{
    String nombre;
    int colAct;
    int colAnt;
    int tipoConf;

    CampoComision(String nombre, int colAct, int colAnt, int tipoConf)
    {
        this.nombre = nombre;
        this.colAct = colAct;
        this.colAnt = colAnt;
        this.tipoConf = tipoConf;
    }
}




// ===== ARREGLO CON TODAS LAS CONFIGURACIONES =====
private final CampoComision[] ARR_CAMPOS_COMISION = new CampoComision[]
{
    new CampoComision("Local",              INT_TBL_DAT_COD_COM_LOC,       INT_TBL_DAT_COD_COM_LOC_ANT,       2),
    new CampoComision("Clientes",           INT_TBL_DAT_COD_COM_CLI,       INT_TBL_DAT_COD_COM_CLI_ANT,       1),
    new CampoComision("Canal",              INT_TBL_DAT_COD_COM_CAN,       INT_TBL_DAT_COD_COM_CAN_ANT,       5),
    new CampoComision("Local Exterior",     INT_TBL_DAT_COD_COM_LOC_EXT,   INT_TBL_DAT_COD_COM_LOC_EXT_ANT,   6),
    new CampoComision("Venta Tubo",         INT_TBL_DAT_COD_COM_VTA_TUB,   INT_TBL_DAT_COD_COM_VTA_TUB_ANT,   5),
    new CampoComision("Venta No Commodity", INT_TBL_DAT_COD_COM_VTA_NOC,   INT_TBL_DAT_COD_COM_VTA_NOC_ANT,   6),
    new CampoComision("Venta Valor",        INT_TBL_DAT_COD_COM_VTA_VAL,   INT_TBL_DAT_COD_COM_VTA_VAL_ANT,   7),
    new CampoComision("Venta R",            INT_TBL_DAT_COD_COM_VTA_R,     INT_TBL_DAT_COD_COM_VTA_R_ANT,     8),
    new CampoComision("Precio PPE",         INT_TBL_DAT_COD_COM_PRE_PPE,   INT_TBL_DAT_COD_COM_PRE_PPE_ANT,   9),
    new CampoComision("Presupuesto",        INT_TBL_DAT_COD_COM_PRE,       INT_TBL_DAT_COD_COM_PRE_ANT,      10),
    new CampoComision("Pre. Calderos",      INT_TBL_DAT_COD_COM_PRE_CAL,   INT_TBL_DAT_COD_COM_PRE_CAL_ANT,  11),
    new CampoComision("Pre. Ton",           INT_TBL_DAT_COD_COM_PRE_TON,   INT_TBL_DAT_COD_COM_PRE_TON_ANT,  12),
    new CampoComision("Cobros",             INT_TBL_DAT_COD_COM_COB,       INT_TBL_DAT_COD_COM_COB_ANT,      13)
};

// ===== MÉTODOS AUXILIARES =====
private String getTextoCelda(int row, int col)
{
    String strRes = "";
    try
    {
        Object obj = objTblMod.getValueAt(row, col);
        if (obj != null)
            strRes = obj.toString().trim();
    }
    catch (Exception e)
    {
        strRes = "";
    }
    return strRes;
}

private int getValorEnteroCelda(int row, int col)
{
    int valor = 0;
    try
    {
        String str = getTextoCelda(row, col);
        if (!str.equals(""))
            valor = Integer.parseInt(str);
    }
    catch (Exception e)
    {
        valor = 0;
    }
    return valor;
}

private String getColumnasModificadas(int row)
{
    String strRes = "";

    for (int i = 0; i < ARR_CAMPOS_COMISION.length; i++)
    {
        int valorAct = getValorEnteroCelda(row, ARR_CAMPOS_COMISION[i].colAct);
        int valorAnt = getValorEnteroCelda(row, ARR_CAMPOS_COMISION[i].colAnt);

        if (valorAct != valorAnt)
            strRes += ARR_CAMPOS_COMISION[i].nombre + ", ";
    }

    if (strRes.endsWith(", "))
        strRes = strRes.substring(0, strRes.length() - 2);

    return strRes;
}


private void actualizarValoresAnterioresFila(int row)
{
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_LOC),      row, INT_TBL_DAT_COD_COM_LOC_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_CLI),      row, INT_TBL_DAT_COD_COM_CLI_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_CAN),      row, INT_TBL_DAT_COD_COM_CAN_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_LOC_EXT),  row, INT_TBL_DAT_COD_COM_LOC_EXT_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_VTA_TUB),  row, INT_TBL_DAT_COD_COM_VTA_TUB_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_VTA_NOC),  row, INT_TBL_DAT_COD_COM_VTA_NOC_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_VTA_VAL),  row, INT_TBL_DAT_COD_COM_VTA_VAL_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_VTA_R),    row, INT_TBL_DAT_COD_COM_VTA_R_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_PRE_PPE),  row, INT_TBL_DAT_COD_COM_PRE_PPE_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_PRE),      row, INT_TBL_DAT_COD_COM_PRE_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_PRE_CAL),  row, INT_TBL_DAT_COD_COM_PRE_CAL_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_PRE_TON),  row, INT_TBL_DAT_COD_COM_PRE_TON_ANT);
    objTblMod.setValueAt(objTblMod.getValueAt(row, INT_TBL_DAT_COD_COM_COB),      row, INT_TBL_DAT_COD_COM_COB_ANT);
}


// ===== GUARDA UNA CONFIGURACIÓN =====
private boolean guardarCambioConfiguracion(java.sql.Connection conExt, int row, CampoComision campo)
{
    boolean blnRes = true;
    java.sql.Statement stmLoc = null;
    String strIns = "";

    try
    {
        if (conExt != null)
        {
            stmLoc = conExt.createStatement();

            int valorAct = getValorEnteroCelda(row, campo.colAct);
            int valorAnt = getValorEnteroCelda(row, campo.colAnt);

            String strCodEmp = getTextoCelda(row, INT_TBL_DAT_COD_EMP);
            String strCodUsr = getTextoCelda(row, INT_TBL_DAT_COD_USR);

            // Si no cambió, no hago nada
            if (valorAct == valorAnt)
                return true;

            // Si ahora tiene un valor
            if (valorAct > 0)
            {
                if (!configuracionAntigua(conExt, valorAct, row))
                {
                    if (existeConfiguracion(conExt, campo.tipoConf, row))
                    {
                        strIns = "";

                        // Desactivar anterior si existía
                        if (valorAnt > 0)
                        {
                            strIns += " UPDATE tbm_preAsiTblComVenUsrVen SET st_reg='E', ";
                            strIns += " fe_ing=GETDATE(), ";
                            strIns += " co_usrIng=" + objParSis.getCodigoUsuario() + ", ";
                            strIns += " tx_comIng=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + " ";
                            strIns += " WHERE co_tblCom=" + valorAnt + " ";
                            strIns += " AND co_emp=" + strCodEmp + " ";
                            strIns += " AND co_usrVen=" + strCodUsr + " ";
                            strIns += " AND co_reg=" + CodigoRegistroExiste + "; ";
                        }

                        // Insertar nuevo
                        strIns += " INSERT INTO tbm_preAsiTblComVenUsrVen ";
                        strIns += " (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
                        strIns += " VALUES (";
                        strIns += strCodEmp + ",";
                        strIns += strCodUsr + ",";
                        strIns += valorAct + ",";
                        strIns += getCodigoRegistro(conExt, strCodEmp, strCodUsr) + ",";
                        strIns += " GETDATE(),";
                        strIns += objParSis.getCodigoUsuario() + ",";
                        strIns += objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";
                        strIns += " 'I'";
                        strIns += " );";

                        System.out.println("[" + campo.nombre + "] " + strIns);
                        stmLoc.executeUpdate(strIns);
                    }
                    else
                    {
                        strIns = "";
                        strIns += " INSERT INTO tbm_preAsiTblComVenUsrVen ";
                        strIns += " (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
                        strIns += " VALUES (";
                        strIns += strCodEmp + ",";
                        strIns += strCodUsr + ",";
                        strIns += valorAct + ",";
                        strIns += getCodigoRegistro(conExt, strCodEmp, strCodUsr) + ",";
                        strIns += " GETDATE(),";
                        strIns += objParSis.getCodigoUsuario() + ",";
                        strIns += objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ",";
                        strIns += " 'I'";
                        strIns += " );";

                        System.out.println("[" + campo.nombre + "] " + strIns);
                        stmLoc.executeUpdate(strIns);
                    }
                }
            }
            else
            {
                // Si antes tenía valor y ahora quedó vacío / 0
                if (valorAnt > 0)
                {
                    if (configuracionAntigua(conExt, valorAnt, row))
                    {
                        if (existeConfiguracion(conExt, campo.tipoConf, row))
                        {
                            strIns = "";
                            strIns += " UPDATE tbm_preAsiTblComVenUsrVen SET st_reg='E', ";
                            strIns += " fe_ing=GETDATE(), ";
                            strIns += " co_usrIng=" + objParSis.getCodigoUsuario() + ", ";
                            strIns += " tx_comIng=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + " ";
                            strIns += " WHERE co_emp=" + strCodEmp + " ";
                            strIns += " AND co_usrVen=" + strCodUsr + " ";
                            strIns += " AND co_tblCom=" + valorAnt + " ";                            
                            strIns += " AND co_reg=" + CodigoRegistroExiste + "; ";

                            System.out.println("[" + campo.nombre + "] " + strIns);
                            stmLoc.executeUpdate(strIns);
                        }
                    }
                }
            }
        }
    }
    catch (java.sql.SQLException e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (stmLoc != null)
                stmLoc.close();
        }
        catch (Exception e) { }
    }

    return blnRes;
}


// ===== GUARDA UNA FILA =====
private boolean guardarModificacion(java.sql.Connection conExt, int ROW)
{
    boolean blnRes = true;

    try
    {
        String strColsMod = getColumnasModificadas(ROW);
        System.out.println("Fila " + ROW + " modificada en: " + strColsMod);

        for (int i = 0; i < ARR_CAMPOS_COMISION.length; i++)
        {
            if (!guardarCambioConfiguracion(conExt, ROW, ARR_CAMPOS_COMISION[i]))
                return false;
        }

        actualizarValoresAnterioresFila(ROW);

        // Opcional: limpia el estado de modificación
        // objTblMod.setValueAt("", ROW, 0);
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}


// ===== GUARDA TODO =====
private boolean guardar()
{
    boolean blnRes = true;
    java.sql.Connection conLoc = null;

    try
    {
        int intNumFil = objTblMod.getRowCountTrue();

        conLoc = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021());

        if (conLoc != null)
        {
            conLoc.setAutoCommit(false);

            for (int i = 0; i < intNumFil; i++)
            {
                if (objUti.parseString(objTblMod.getValueAt(i, 0)).equals("M"))
                {
                    if (!guardarModificacion(conLoc, i))
                    {
                        conLoc.rollback();
                        mostrarMsgInf("Error al guardar...");
                        return false;
                    }
                }
            }

            conLoc.commit();
            mostrarMsgInf("Listo...");
        }
    }
    catch (Exception e)
    {
        blnRes = false;

        try
        {
            if (conLoc != null)
                conLoc.rollback();
        }
        catch (Exception ex) { }

        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if (conLoc != null)
                conLoc.close();
        }
        catch (Exception e) { }
    }

    return blnRes;
}
    
    
    
    /////////////////////fin guardar

    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (!guardar()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                }
                butCon.doClick();
                objThrGua=null;
                
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
////////    private boolean guardar()
////////    {
////////        boolean blnRes=true;
////////        java.sql.Connection conLoc;
////////        try{
////////            int intNumFil=objTblMod.getRowCountTrue();
////////            for(int i=0; i<intNumFil;i++){
////////                if(objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
////////                    //conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
////////                    conLoc= DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(),objParSis.getClaveBaseDatos_Dynamics2021());
////////
////////                    if(conLoc!=null){
////////                        conLoc.setAutoCommit(false);
////////                        if(guardarModificacion(conLoc, i)){
////////                            conLoc.commit();
////////                            mostrarMsgInf("Listo...");
////////                        }
////////                        else{
////////                             conLoc.rollback();
////////                            blnRes=false;
////////                            mostrarMsgInf("Error al guardar...");
////////                        }
////////                        conLoc.close();
////////                        conLoc=null;
////////                    }
////////                    
////////                }
////////            }
////////        }
////////        catch(Exception  Evt){ 
////////           objUti.mostrarMsgErr_F1(this, Evt);
////////           blnRes=false;
////////        }  
////////        return blnRes;
////////    }
    
    /**
     * 
     * @param conExt
     * @param ROW
     * @return 
    */
    
////////    private boolean guardarModificacion(java.sql.Connection conExt, int ROW){
////////        boolean blnRes=true;
////////        java.sql.Statement stmLoc;
////////        String strIns="";
////////        try{
////////            if(conExt!=null){
////////                stmLoc = conExt.createStatement(); 
////////                int valorAct = (objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC) != null
////////                            && !objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC).toString().trim().isEmpty())
////////                            ? Integer.parseInt(objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC).toString().trim())
////////                            : 0;
////////                int valorAnt = (objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC_ANT) != null
////////                            && !objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC_ANT).toString().trim().isEmpty())
////////                            ? Integer.parseInt(objTblMod.getValueAt(ROW, INT_TBL_DAT_COD_COM_LOC).toString().trim())
////////                            : 0;                
////////                if(valorAct!=0)
////////                {
////////                    if(valorAct>0 )
////////                    {
////////                        if(!configuracionAntigua(conExt, valorAct , ROW))
////////                        {
////////                            if(existeConfiguracion(conExt, 1, ROW))
////////                            {
////////////                                codAct = Integer.parseInt(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString());
////////////                                codAnt = Integer.parseInt(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC_ANT).toString());
////////                                if (valorAct != valorAnt)
////////                                {
////////                                    strIns="";
////////                                    strIns+=" UPDATE tbm_preAsiTblComVenUsrVen SET st_reg ='E' , ";
////////                                    strIns+=" fe_ing=GETDATE(), co_usrIng="+objParSis.getCodigoUsuario()+",tx_comIng="+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+"  \n";
////////                                    strIns+=" WHERE co_tblCom = "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC_ANT).toString()+" and co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND ";
////////                                    strIns+=" co_reg="+CodigoRegistroExiste+" ; \n";
////////                                    
////////                                    strIns+=" INSERT INTO tbm_preAsiTblComVenUsrVen (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
////////                                    strIns+=" VALUES ("+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+",";
////////                                    strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+",";
////////                                    strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString()+",";
////////                                    strIns+=" "+getCodigoRegistro(conExt, objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()) +",";
////////                                    strIns+=" GETDATE(),"+objParSis.getCodigoUsuario()+", "+ objUti.codificar(objParSis.getNombreComputadoraConDirIP()) +",'I'  ";
////////                                    strIns+=" );";                                    
////////                                    System.out.println("strIns " + strIns);
////////                                    stmLoc.executeUpdate(strIns);                                    
////////                                }
////////                            }
////////                            else
////////                            {
////////                                strIns="";
////////                                strIns+=" INSERT INTO tbm_preAsiTblComVenUsrVen (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
////////                                strIns+=" VALUES ("+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+",";
////////                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+",";
////////                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString()+",";
////////                                strIns+=" "+getCodigoRegistro(conExt, objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()) +",";
////////                                strIns+=" GETDATE(),"+objParSis.getCodigoUsuario()+", "+ objUti.codificar(objParSis.getNombreComputadoraConDirIP()) +",'I'  ";
////////                                strIns+=" );";
////////                               System.out.println("strIns " + strIns);
////////                                stmLoc.executeUpdate(strIns);
////////                            }
////////                        }
////////                        
////////                    }
////////                }
////////                else
////////                {
////////                        if(!configuracionAntigua(conExt, Integer.parseInt(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC_ANT).toString()) , ROW))
////////                        {
////////                            if(existeConfiguracion(conExt, 1, ROW))
////////                            {
////////                                strIns="";
////////                                strIns+=" UPDATE tbm_preAsiTblComVenUsrVen SET st_reg ='E', ";
////////                                strIns+=" fe_ing=GETDATE(), co_usrIng="+objParSis.getCodigoUsuario()+",tx_comIng="+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+"  ";
////////                                strIns+=" WHERE co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND ";
////////                                strIns+=" co_reg="+CodigoRegistroExiste+" ; ";
////////                                System.out.println("strIns " + strIns);
////////                                stmLoc.executeUpdate(strIns);
////////                            }
////////                        }                    
////////                    
////////                }
////////
////////                stmLoc.close();
////////                stmLoc=null;
////////            }
////////        }
////////        catch (java.sql.SQLException e){
////////            blnRes=false;
////////            objUti.mostrarMsgErr_F1(this, e);
////////        }
////////        catch(Exception  Evt){ 
////////           objUti.mostrarMsgErr_F1(this, Evt);
////////           blnRes=false;
////////        }  
////////        return blnRes;
////////    }
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodUsr
     * @return 
     */
    
    private int getCodigoRegistro(java.sql.Connection conExt, String CodEmp, String CodUsr){
        int intRet=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_reg) IS NULL THEN 1 ELSE MAX(a1.co_reg)+1 END AS maximoRegistro \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_usrVen="+CodUsr+"   ";
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
    
    private int CodigoRegistroExiste=-1;
    
    /**
     * 1;"COCLAS";"Cobros a clientes asignados"
       2;"COLO";"Cobros del local"
       3;"COLONOPA";"Cobros del local menos Patricia Solorzano"

     * @param conExt
     * @param tipoCalculo  1  LOCAL  ****  2  Clientes
     * @param ROW
     * @return 
     */
    
    private boolean existeConfiguracion(java.sql.Connection conExt, int tipoCalculo, int ROW){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strAux="";
        try{
                switch (tipoCalculo)
                {
                    case 1:  strAux = " AND a2.co_basCal IN (1) ";  break;
                    case 2:  strAux = " AND a2.co_basCal IN (2) ";  break;
                    case 3:  strAux = " AND a2.co_basCal IN (5) ";  break;
                    case 4:  strAux = " AND a2.co_basCal IN (6) ";  break;
                    case 5:  strAux = " AND a2.co_basCal IN (7) ";  break;
                    case 6:  strAux = " AND a2.co_basCal IN (8) ";  break;
                    case 7:  strAux = " AND a2.co_basCal IN (9) ";  break;
                    case 8:  strAux = " AND a2.co_basCal IN (10) "; break;
                    case 9:  strAux = " AND a2.co_basCal IN (11) "; break;
                    case 10: strAux = " AND a2.co_basCal IN (12) "; break;
                    case 11: strAux = " AND a2.co_basCal IN (13) "; break;
                    case 12: strAux = " AND a2.co_basCal IN (14) "; break;
                    case 13: strAux = " AND a2.co_basCal IN (15) "; break;
                }
            if(conExt!=null)
            {
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*  \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)   \n";
                strSQL+=" WHERE  a1.co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+"  AND a1.st_reg='I'  ";
                strSQL+=""+strAux; 
              //  System.out.println("existeConfiguracion " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    CodigoRegistroExiste = rstLoc.getInt("co_reg");
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }  
        return blnRes;
    }
    
    /**
     * Para validar que no exista la configuracion
     * @param conExt Conexion
     * @param CodTblCom Codigo de la tabla de comision
     * @param ROW Fila
     * @return 
     */
    private boolean configuracionAntigua(java.sql.Connection conExt, int CodTblCom, int ROW){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*  \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" WHERE a1.co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND a1.co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND  ";
                strSQL+="       a1.co_tblCom="+CodTblCom+"  ";
               // System.out.println("existeConfiguracion " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        } 
        return blnRes;
    }
    
    
    
    /**
     * 
     * @param CodFilSel
     * @param CodColSel
     * @return 
     */
    
    private boolean mostrarVentanaTablas(int intCodBasCal, int ROW, int COLUMN){
        boolean blnRes=false;
        try {
            int coUsr = Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_USR).toString()) ; 
            ZafVen70_01 obj = new ZafVen70_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, true,  intCodBasCal,coUsr);
//            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            if(obj.acepta()){
                objTblMod.setValueAt(obj.intCodTabSel, ROW, COLUMN+1); 
                objTblMod.setValueAt(obj.strDesCorTabSel, ROW, COLUMN+2); 
                obj=null;
                blnRes=true;
            }
            else{
                obj=null;
            }
        } 
        catch (Exception Evt) {    
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }
    
    
    
}