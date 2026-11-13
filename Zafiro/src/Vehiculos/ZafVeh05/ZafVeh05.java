/*
 * ZafVeh05.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */


package Vehiculos.ZafVeh05;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
/**
 *
 * @author  José Marín 
 */
public class ZafVeh05 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafVenCon vcoPrg;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafVenCon vcoUsr;
    private ZafVenCon vecLoc; 
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg3;

    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strMarCod;
    private String strMarNom;
    
    private String strComCod;
    private String strComNom;
    
    private ZafVenCon vcoMar;//j
    private ZafVenCon vcoCom;//j
    

    private ZafVenCon vcoCho;//j
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm2;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm3;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm4;
    
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecVeh, vecCom;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private int intSelPrd;
    private int intSelRepSelPrd;
   
    //Tabla 
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_VEH=1;
    private final int INT_TBL_DAT_PLA_VEH=2;
    private final int INT_TBL_DAT_NOM_VEH=3;
    private final int INT_TBL_DAT_COD_MAR=4;
    private final int INT_TBL_DAT_CON_MAR=5;
    private final int INT_TBL_DAT_NOM_MAR=6;
    private final int INT_TBL_DAT_TIP_VEH=7;
    private final int INT_TBL_DAT_PES_SOP=8;
    private final int INT_TBL_DAT_COD_COM=9;//aki
    private final int INT_TBL_DAT_CON_COM=10;
    private final int INT_TBL_DAT_DES_LAR_COM=11;
    private final int INT_TBL_DAT_CAP_TAN=12;
    private final int INT_TBL_DAT_REN_VEH=13;
    private final int INT_TBL_DAT_COD_CHO=14;
    private final int INT_TBL_DAT_CON_CHO=15;
    private final int INT_TBL_DAT_NOM_CHO=16;
    private final int INT_TBL_DAT_KIL_VEH=17;
    
    private String strCodMar, strNomMar;                //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                //Contenido del campo al obtener el foco.
    
    private Vector vecCodVeh, vecPlaVeh, vecNomVeh, vecCodMar;
    private Vector vecNomMar, vecTipVeh, vecPesSop;
    private Vector vecCapTan, vecRenVeh, vecCodCho, vecNomCho; 
    private Vector vecCodCom, vecDesLarCom, vecUltKil;
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    /** Creates new form ZafVeh05 */
    public ZafVeh05(ZafParSis obj) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            objTblCelRenButDG1=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            if (!configurarFrm())
                exitForm();
            comboVehiculo();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
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
        panDat = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        butMar = new javax.swing.JButton();
        txtNomMar = new javax.swing.JTextField();
        txtCodMar = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipVeh = new javax.swing.JLabel();
        vehiculo = new javax.swing.JComboBox();
        lblTipCom = new javax.swing.JLabel();
        txtCodCom = new javax.swing.JTextField();
        txtNomCom = new javax.swing.JTextField();
        butCom = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(610, 110));
        panCab.setLayout(null);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCab.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        butMar.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butMar.setText("...");
        butMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMarActionPerformed(evt);
            }
        });
        panCab.add(butMar);
        butMar.setBounds(440, 40, 20, 20);

        txtNomMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMarActionPerformed(evt);
            }
        });
        txtNomMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMarFocusLost(evt);
            }
        });
        panCab.add(txtNomMar);
        txtNomMar.setBounds(260, 40, 180, 19);

        txtCodMar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarActionPerformed(evt);
            }
        });
        txtCodMar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarFocusLost(evt);
            }
        });
        panCab.add(txtCodMar);
        txtCodMar.setBounds(170, 40, 90, 19);

        lblMarca.setText("Marca:");
        panCab.add(lblMarca);
        lblMarca.setBounds(30, 40, 39, 15);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los vehículos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                optTodFocusGained(evt);
            }
        });
        panCab.add(optTod);
        optTod.setBounds(0, 0, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los vehículos que cumplan el criterio seleccionado");
        panCab.add(optFil);
        optFil.setBounds(0, 20, 400, 20);

        lblTipVeh.setText("Tipo de vehículo:"); // NOI18N
        panCab.add(lblTipVeh);
        lblTipVeh.setBounds(30, 82, 130, 15);

        vehiculo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        vehiculo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                vehiculoItemStateChanged(evt);
            }
        });
        vehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehiculoActionPerformed(evt);
            }
        });
        panCab.add(vehiculo);
        vehiculo.setBounds(170, 82, 210, 20);

        lblTipCom.setText("Tipo de combustible:"); // NOI18N
        panCab.add(lblTipCom);
        lblTipCom.setBounds(30, 60, 140, 15);

        txtCodCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodComActionPerformed(evt);
            }
        });
        txtCodCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodComFocusLost(evt);
            }
        });
        panCab.add(txtCodCom);
        txtCodCom.setBounds(170, 60, 90, 19);

        txtNomCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomComActionPerformed(evt);
            }
        });
        txtNomCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomComFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomComFocusLost(evt);
            }
        });
        panCab.add(txtNomCom);
        txtNomCom.setBounds(260, 60, 180, 19);

        butCom.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butCom.setText("...");
        butCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butComActionPerformed(evt);
            }
        });
        panCab.add(butCom);
        butCom.setBounds(440, 60, 20, 20);

        panDat.add(panCab, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("Filtro", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                if(con!=null){
                    con.close();
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void butMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMarActionPerformed
        // TODO add your handling code here:
        strMarCod=txtCodMar.getText();
        mostrarMarcas(0);
        optFil.setSelected(true);
    }//GEN-LAST:event_butMarActionPerformed

    private void txtNomMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMarActionPerformed
        // TODO add your handling code here:
        txtNomMar.transferFocus();
    }//GEN-LAST:event_txtNomMarActionPerformed

    private void txtNomMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusGained
        // TODO add your handling code here:
        strMarNom=txtNomMar.getText();
        txtNomMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomMarFocusGained

    private void txtNomMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMarFocusLost
        // TODO add your handling code here:
        if (!txtNomMar.getText().equalsIgnoreCase(strMarNom)){
            if (txtNomMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else{
                mostrarMarcas(2);
            }
        }
        else
            txtNomMar.setText(strMarNom);
    }//GEN-LAST:event_txtNomMarFocusLost

    private void txtCodMarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarActionPerformed
        // TODO add your handling code here:
        txtCodMar.transferFocus();
    }//GEN-LAST:event_txtCodMarActionPerformed

    private void txtCodMarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusGained
        // TODO add your handling code here:
        strMarCod=txtCodMar.getText();
        txtCodMar.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodMarFocusGained

    private void txtCodMarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarFocusLost
        // TODO add your handling code here:
        if (!txtCodMar.getText().equalsIgnoreCase(strMarCod)){
            if (txtCodMar.getText().equals("")){
                txtCodMar.setText("");
                txtNomMar.setText("");
            }
            else
            mostrarMarcas(1);
        }
        else
            txtCodMar.setText(strMarCod);
    }//GEN-LAST:event_txtCodMarFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");
            txtCodCom.setText("");
            txtNomCom.setText("");
            
//            vehiculo.setSelectedItem("");
//            combustible.setSelectedItem("");
            comboVehiculo();
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void vehiculoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_vehiculoItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_vehiculoItemStateChanged

    private void vehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehiculoActionPerformed
        // TODO add your handling code here:
        if(vehiculo.isFocusOwner())
        optFil.setSelected(true);
    }//GEN-LAST:event_vehiculoActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafVeh05.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else{
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                if (actualizarDet()) {
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                }
                else {
                    System.out.println("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
            }
        }
      else{
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
      }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_optTodFocusGained
        // TODO add your handling code here:
        if (optTod.isSelected())
        {
            txtCodMar.setText("");
            txtNomMar.setText("");
            txtCodCom.setText("");
            txtNomCom.setText("");
//            vehiculo.setSelectedItem("");
//            combustible.setSelectedItem("");
            comboVehiculo();
        }
    }//GEN-LAST:event_optTodFocusGained

    private void txtCodComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodComActionPerformed
        // TODO add your handling code here:
        txtCodCom.transferFocus();
    }//GEN-LAST:event_txtCodComActionPerformed

    private void txtCodComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusGained
        // TODO add your handling code here:
        strComCod=txtCodCom.getText();
        txtCodCom.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodComFocusGained

    private void txtCodComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodComFocusLost
        // TODO add your handling code here:
         if (!txtCodCom.getText().equalsIgnoreCase(strComCod)){
            if (txtCodCom.getText().equals("")){
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else
            mostrarCombustibles(1);
        }
        else
            txtCodCom.setText(strComCod);
    }//GEN-LAST:event_txtCodComFocusLost

    private void txtNomComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomComActionPerformed
        // TODO add your handling code here:
         txtNomCom.transferFocus();
    }//GEN-LAST:event_txtNomComActionPerformed

    private void txtNomComFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusGained
        // TODO add your handling code here:
         strComNom=txtNomCom.getText();
        txtNomCom.selectAll();
        optFil.setSelected(true);
    }//GEN-LAST:event_txtNomComFocusGained

    private void txtNomComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomComFocusLost
        // TODO add your handling code here:
          if (!txtNomCom.getText().equalsIgnoreCase(strComNom)){
            if (txtNomCom.getText().equals("")){
                txtCodCom.setText("");
                txtNomCom.setText("");
            }
            else{
                mostrarCombustibles(2);
            }
        }
        else
            txtNomCom.setText(strComNom);
    }//GEN-LAST:event_txtNomComFocusLost

    private void butComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butComActionPerformed
        strComCod=txtCodCom.getText();
        mostrarCombustibles(0);
        optFil.setSelected(true);
    }//GEN-LAST:event_butComActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCom;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butMar;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipCom;
    private javax.swing.JLabel lblTipVeh;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCom;
    private javax.swing.JTextField txtCodMar;
    private javax.swing.JTextField txtNomCom;
    private javax.swing.JTextField txtNomMar;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JComboBox vehiculo;
    // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
    
       
    
//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            System.out.println("configurarFrm....");
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            intSelPrd=0;
            this.setTitle(objParSis.getNombreMenu() + " v0.12");
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_VEH,"Cód.Veh.");
            vecCab.add(INT_TBL_DAT_PLA_VEH,"Pla.Veh.");
            vecCab.add(INT_TBL_DAT_NOM_VEH,"Vehículo");
            vecCab.add(INT_TBL_DAT_COD_MAR,"Cód.Mar.");
            vecCab.add(INT_TBL_DAT_CON_MAR,"");
            vecCab.add(INT_TBL_DAT_NOM_MAR,"Marca");
            vecCab.add(INT_TBL_DAT_TIP_VEH,"Tip.Veh.");
            vecCab.add(INT_TBL_DAT_PES_SOP,"Pes.Sop.");
            vecCab.add(INT_TBL_DAT_COD_COM,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_CON_COM,"");
            vecCab.add(INT_TBL_DAT_DES_LAR_COM,"Combustible");
            vecCab.add(INT_TBL_DAT_CAP_TAN,"Cap.Tan.");
            vecCab.add(INT_TBL_DAT_REN_VEH,"Ren.Veh.");
            vecCab.add(INT_TBL_DAT_COD_CHO,"Cód.Cho.");
            vecCab.add(INT_TBL_DAT_CON_CHO,"");
            vecCab.add(INT_TBL_DAT_NOM_CHO,"Chofer");
            vecCab.add(INT_TBL_DAT_KIL_VEH,"Últ.Kil.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_VEH).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PLA_VEH).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEH).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_MAR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MAR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_COM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_COM).setPreferredWidth(150);
            
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_CHO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CHO).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setPreferredWidth(100);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_MAR).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_COM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_CHO).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            if (!objPerUsr.isOpcionEnabled(3718)) //Si no tiene el acceso se oculta el boton guardar
            {
                butGua.setVisible(false);
            }
            
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setCellRenderer(objTblCelRenLblNum);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setCellRenderer(objTblCelRenLblCod);
            
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAP_TAN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_SOP, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_REN_VEH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_KIL_VEH, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_MAR);
            vecAux.add("" + INT_TBL_DAT_CON_MAR);
            vecAux.add("" + INT_TBL_DAT_TIP_VEH);
            
            if(objPerUsr.isOpcionEnabled(3720)) // Si no tiene el acceso no es editable
            {    
                vecAux.add("" + INT_TBL_DAT_COD_COM);
                vecAux.add("" + INT_TBL_DAT_CON_COM);
            }
            vecAux.add("" + INT_TBL_DAT_PES_SOP);
            
            if(objPerUsr.isOpcionEnabled(3721)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_CAP_TAN);
            
            if(objPerUsr.isOpcionEnabled(3722)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_REN_VEH);
            
            vecAux.add("" + INT_TBL_DAT_COD_CHO);
            vecAux.add("" + INT_TBL_DAT_CON_CHO);
            
            if(objPerUsr.isOpcionEnabled(3729)) // Si no tiene el acceso no es editable
                vecAux.add("" + INT_TBL_DAT_KIL_VEH);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_MAR).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_CHO).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_COM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenCmbBox=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellRenderer(objTblCelRenCmbBox);
            
            
            objTblCelEdiCmbBox=new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TIP_VEH).setCellEditor(objTblCelEdiCmbBox);
                    
            
            cargarComboTipoVeh();
            configurarChoferes();
            configurarMarcas();
            configurarCombustible();
            
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_MAR;
            intColTbl[1]=INT_TBL_DAT_NOM_MAR;
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoMar, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAR).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoMar.setCampoBusqueda(0);
                    vcoMar.setCriterio1(11);
                }
            });
            
            
            objTblCelEdiButVcoBodOrg=new ZafTblCelEdiButVco(tblDat, vcoMar, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_CON_MAR).setCellEditor(objTblCelEdiButVcoBodOrg);
            objTblCelEdiButVcoBodOrg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_COM;
            intColTbl[1]=INT_TBL_DAT_DES_LAR_COM;
            
            objTblCelEdiTxtVcoItm3=new ZafTblCelEdiTxtVco(tblDat, vcoCom, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM).setCellEditor(objTblCelEdiTxtVcoItm3);
            objTblCelEdiTxtVcoItm3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    vcoCom.setCampoBusqueda(0);
                    vcoCom.setCriterio1(11);
                }               
            });
            
            objTblCelEdiButVcoBodOrg3=new ZafTblCelEdiButVco(tblDat, vcoCom, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_CON_COM).setCellEditor(objTblCelEdiButVcoBodOrg3);
            objTblCelEdiButVcoBodOrg3.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CHO;
            intColTbl[1]=INT_TBL_DAT_NOM_CHO;
            
            objTblCelEdiTxtVcoItm2=new ZafTblCelEdiTxtVco(tblDat, vcoCho, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHO).setCellEditor(objTblCelEdiTxtVcoItm2);
            objTblCelEdiTxtVcoItm2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    vcoCho.setCampoBusqueda(0);
                    vcoCho.setCriterio1(11);
                }               
            });
            
            objTblCelEdiButVcoBodOrg2=new ZafTblCelEdiButVco(tblDat, vcoCho, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_CON_CHO).setCellEditor(objTblCelEdiButVcoBodOrg2);
            objTblCelEdiButVcoBodOrg2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            intColVen=null;
            intColTbl=null;

            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_SOP).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES_SOP))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_PES_SOP);
                        }
                }
            });
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAP_TAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAP_TAN))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAP_TAN);
                        }
                }
            });
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_REN_VEH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_REN_VEH))<0){
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_REN_VEH);
                        }
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_KIL_VEH).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    Double precio;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                {
                    precio=objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH));
                }
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH))<1){
                            objTblMod.setValueAt(precio, tblDat.getSelectedRow(), INT_TBL_DAT_KIL_VEH);
                        }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
            System.out.println("Se configuara la tabla!!!!... ");
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    private boolean cargarDetReg(){
        int i,temp=0,j=0;
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
            strSQL=" ";
            strSQL+=" SELECT  a1.co_veh,a1.tx_pla,a1.tx_deslar as NomVeh,a2.co_mar,";
            strSQL+=" a2.tx_deslar as NomMar,";
            strSQL+=" tx_tipVeh,round(nd_pessopkgr,2) as nd_pessopkgr,a4.co_com,";
            strSQL+=" a4.tx_deslar as tx_tipcom,";
            strSQL+=" round(nd_captan,2) as nd_captan,";
            strSQL+=" round(nd_renveh,2) as nd_renveh,a3.co_tra,a3.chofer,a1.ne_ultKil";
            strSQL+=" FROM tbm_veh as a1";
            strSQL+=" LEFT OUTER JOIN tbm_marVeh as a2 ON (a1.co_mar=a2.co_mar)";
            strSQL+=" LEFT OUTER JOIN(";
            strSQL+=" SELECT (tx_nom ||' ' || tx_ape) as chofer, co_tra";
            strSQL+=" FROM tbm_tra";
            strSQL+=" ) as a3 ON (a1.co_cho=a3.co_tra)";
            strSQL+=" LEFT OUTER JOIN tbm_comVeh as a4 ON(a1.co_com=a4.co_com)";
            strSQL+=" where 1=1";
            if(optFil.isSelected())
            {
                if(txtCodMar.getText().length()>0)
                    strSQL+=" and a2.co_mar=" + txtCodMar.getText();
                
                if(txtCodCom.getText().length()>0)
                    strSQL+=" and a1.co_com=" + txtCodCom.getText();
                
                if(vehiculo.getSelectedIndex()==1) 
                    strSQL+=" and tx_tipVeh='C'";
                else if(vehiculo.getSelectedIndex()==2) 
                    strSQL+=" and tx_tipVeh='M'";
                else if(vehiculo.getSelectedIndex()==3) 
                    strSQL+=" and tx_tipVeh='N'";
                else if(vehiculo.getSelectedIndex()==4) 
                    strSQL+=" and tx_tipVeh='O'";
            }
            strSQL+=" ";
            strSQL+=" ORDER BY a1.co_veh";
            System.out.println("zabVeh05 "+strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            i=0;	   	
            vecCodVeh = new Vector();
            vecPlaVeh = new Vector();
            vecNomVeh = new Vector();
            vecCodMar = new Vector();
            vecNomMar = new Vector();
            vecTipVeh = new Vector();
            vecPesSop = new Vector();
            vecCodCom = new Vector();
            vecDesLarCom = new Vector();
            vecCapTan = new Vector();
            vecRenVeh = new Vector();
            vecCodCho = new Vector();
            vecNomCho = new Vector();
            vecUltKil = new Vector();
            while(rst.next()){
                if (blnCon){
                   vecCodVeh.add(j,rst.getString("co_veh"));
                   if(rst.getString("tx_pla")!=null) {vecPlaVeh.add(j,rst.getString("tx_pla"));}
                   else {vecPlaVeh.add(j,"");}
                   if(rst.getString("nomveh")!=null) {vecNomVeh.add(j,rst.getString("nomveh"));}
                   else {vecNomVeh.add(j,"");}
                   if(rst.getString("co_mar")!=null) {vecCodMar.add(j,rst.getString("co_mar"));}
                   else {vecCodMar.add(j,"");}
                   if(rst.getString("nommar")!=null) {vecNomMar.add(j,rst.getString("nommar"));}
                   else {vecNomMar.add(j,"");}
                   if(rst.getString("tx_tipveh")!=null) {vecTipVeh.add(j,rst.getString("tx_tipveh"));}
                   else {vecTipVeh.add(j,"");}
                   if(rst.getString("nd_pessopkgr")!=null) {vecPesSop.add(j,rst.getString("nd_pessopkgr"));}
                   else {vecPesSop.add(j,"");}
                   
                   if(rst.getString("co_com")!=null) {vecCodCom.add(j,rst.getString("co_com"));}
                   else {vecCodCom.add(j,"");}  
                   if(rst.getString("tx_tipcom")!=null) {vecDesLarCom.add(j,rst.getString("tx_tipcom"));}
                   else {vecDesLarCom.add(j,"");}
                   
                   if(rst.getString("nd_captan")!=null) {vecCapTan.add(j,rst.getString("nd_captan"));}
                   else {vecCapTan.add(j,"");}
                   if(rst.getString("nd_renveh")!=null) {vecRenVeh.add(j,rst.getString("nd_renveh"));}
                   else {vecRenVeh.add(j,"");}
                   if(rst.getString("co_tra")!=null) {vecCodCho.add(j,rst.getString("co_tra"));}
                   else {vecCodCho.add(j,"");}
                   if(rst.getString("chofer")!=null) {vecNomCho.add(j,rst.getString("chofer"));}
                   else {vecNomCho.add(j,"");}
                   if(rst.getString("ne_ultKil")!=null) {vecUltKil.add(j,rst.getString("ne_ultKil"));}
                   else {vecUltKil.add(j,"");}
                   j++;
                }
                else{break;}
              }
            int jota=0;
            while(j>jota){
               vecReg= new Vector(); 
               vecReg.add(INT_TBL_DAT_LIN,"");
               if(vecCodVeh.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_VEH,vecCodVeh.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_VEH," ");}
               if(vecPlaVeh.get(jota)!=null) {vecReg.add(INT_TBL_DAT_PLA_VEH,vecPlaVeh.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_PLA_VEH," ");}
               if(vecNomVeh.get(jota)!=null) {vecReg.add(INT_TBL_DAT_NOM_VEH,vecNomVeh.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NOM_VEH," ");}
               if(vecCodMar.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_MAR,vecCodMar.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_MAR," ");}
               vecReg.add(INT_TBL_DAT_CON_MAR,"Boton");
               if(vecNomMar.get(jota)!=null){vecReg.add(INT_TBL_DAT_NOM_MAR,vecNomMar.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NOM_MAR," ");}
               
               if(vecTipVeh.get(jota)!=null){
                   //vecReg.add(INT_TBL_DAT_TIP_VEH,vecTipVeh.get(jota).toString());
                   switch(vecTipVeh.get(jota).toString().charAt(0))
                   {
                        case 'C':vecReg.add(INT_TBL_DAT_TIP_VEH,"Carro");
                            break;
                        case 'M':vecReg.add(INT_TBL_DAT_TIP_VEH,"Moto");
                            break;
                        case 'N':vecReg.add(INT_TBL_DAT_TIP_VEH,"Montacargas");
                            break;
                        case 'O':vecReg.add(INT_TBL_DAT_TIP_VEH,"Otros");
                            break;
                        default:
                            vecReg.add(INT_TBL_DAT_TIP_VEH,"");
                            break;
                   }
               }
               else{vecReg.add(INT_TBL_DAT_TIP_VEH," ");}
               
               if(vecPesSop.get(jota)!=null){vecReg.add(INT_TBL_DAT_PES_SOP,vecPesSop.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_PES_SOP," ");}
               
               if(vecCodCom.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_COM,vecCodCom.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_COM," ");}
               vecReg.add(INT_TBL_DAT_CON_COM,"Boton");
               if(vecDesLarCom.get(jota)!=null){vecReg.add(INT_TBL_DAT_DES_LAR_COM,vecDesLarCom.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_DES_LAR_COM," ");}
               
               if(vecCapTan.get(jota)!=null){vecReg.add(INT_TBL_DAT_CAP_TAN,vecCapTan.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_CAP_TAN," ");}
               if(vecRenVeh.get(jota)!=null){vecReg.add(INT_TBL_DAT_REN_VEH,vecRenVeh.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_REN_VEH," ");}
               if(vecCodCho.get(jota)!=null){vecReg.add(INT_TBL_DAT_COD_CHO,vecCodCho.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_COD_CHO," ");}
               vecReg.add(INT_TBL_DAT_CON_CHO,"Boton");
               if(vecNomCho.get(jota)!=null){vecReg.add(INT_TBL_DAT_NOM_CHO,vecNomCho.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_NOM_CHO," ");}
               if(vecUltKil.get(jota)!=null){vecReg.add(INT_TBL_DAT_KIL_VEH,vecUltKil.get(jota).toString());}
               else{vecReg.add(INT_TBL_DAT_KIL_VEH," ");}
               vecDat.add(vecReg);
               jota++;
            }
         }     
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        vecDat.clear();
        if(blnCon){
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
         }
        else{
             lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
        }
        rst.close();
        stm.close();
        con.close();
        rst=null;
        stm=null;
        con=null;
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
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
    
    
//    /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
      case INT_TBL_DAT_COD_VEH: strMsg="Código del Vehículo"; break;
      case INT_TBL_DAT_PLA_VEH: strMsg="Placa del Vehículo"; break;
      case INT_TBL_DAT_NOM_VEH: strMsg="Descripción larga del Vehículo"; break;
      case INT_TBL_DAT_COD_MAR: strMsg="Código de la Marca"; break;
      case INT_TBL_DAT_CON_MAR: strMsg="Muestra la ventana de consulta para elegir la 'Marca'"; break;
      case INT_TBL_DAT_NOM_MAR: strMsg="Descripción larga de la Marca"; break;
      case INT_TBL_DAT_TIP_VEH: strMsg="Tipo del Vehículo"; break;
      case INT_TBL_DAT_PES_SOP: strMsg="Peso soportado (Kilogramos)"; break;
      case INT_TBL_DAT_COD_COM: strMsg="Código de combustible"; break;
      case INT_TBL_DAT_CON_COM: strMsg="Muestra la ventana de consulta para elegir el 'Combustible'"; break;
      case INT_TBL_DAT_DES_LAR_COM: strMsg="Descripción larga de combustible"; break;
      case INT_TBL_DAT_CAP_TAN: strMsg="Capacidad del tanque"; break;
      case INT_TBL_DAT_REN_VEH: strMsg="Rendimiento del vehículo"; break;
      case INT_TBL_DAT_COD_CHO: strMsg="Código del chofer"; break;
      case INT_TBL_DAT_CON_CHO: strMsg="Muestra la ventana de consulta para elegir el 'Chofer'"; break;
      case INT_TBL_DAT_NOM_CHO: strMsg="Nombres y apellidos del chofer"; break;
      case INT_TBL_DAT_KIL_VEH: strMsg="Ültimo kilometraje del vehículo"; break;
      default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
//    /**
//     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

//    /**
//     * Esta clase implementa la interface DocumentListener que observa los cambios que
//     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
//     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
//     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
//     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
//     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
//     */
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
            }
            objThrGUI=null;
        }
    }
    
    
    /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    
    
    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarMarcas()
    {
        boolean blnRes=true;
        try
        {
            System.out.println("configurarMarcas::....");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mar");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_mar, a1.tx_deslar";
            strSQL+=" FROM tbm_marVeh a1 WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" ORDER BY a1.co_mar";
            System.out.println("configurarMarcas:.." + strSQL);
            vcoMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Marcas", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//    /**
//     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
//     * una básqueda directa (No se muestra la ventana de consulta a menos que no
//     * exista lo que se está buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opcián que desea utilizar.
//     * @param intTipBus El tipo de básqueda a realizar.
//     * @return true: Si no se presentá ningán problema.
//     * <BR>false: En el caso contrario.
//     */
    private boolean mostrarMarcas(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
               case 0: //Mostrar la ventana de consulta.
                    vcoMar.setCampoBusqueda(2);
                    vcoMar.setVisible(true);
                    if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoMar.buscar("a1.co_mar", txtCodMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(0);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(2));
                        }
                        else
                        {
                            txtCodMar.setText(strCodMar);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoMar.buscar("a1.tx_deslar", txtNomMar.getText()))
                    {
                        txtCodMar.setText(vcoMar.getValueAt(1));
                        txtNomMar.setText(vcoMar.getValueAt(2));
                    }
                    else
                    {
                        vcoMar.setCampoBusqueda(2);
                        vcoMar.setCriterio1(11);
                        vcoMar.cargarDatos();
                        vcoMar.setVisible(true);
                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodMar.setText(vcoMar.getValueAt(1));
                            txtNomMar.setText(vcoMar.getValueAt(3));
                        }
                        else
                        {
                            txtNomMar.setText(strNomMar);
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
    
  
    private boolean configurarChoferes(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("nombre");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" select distinct a2.co_tra, (a2.tx_ape || ' ' ||a2.tx_nom) as nombre ";
            strSQL+=" from tbm_traEmp as a1 INNER JOIN tbm_tra as a2 ON (a1.co_tra = a2.co_tra  and a2.st_reg='A')";
//            strSQL+=" WHERE a1.co_dep in (26,27,28,29,30)";
            strSQL+=" where a1.st_reg='A' ORDER BY nombre";
            System.out.println("configurarChoferes:.." + strSQL);
            vcoCho=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCho.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

 
//    /**
//     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
//     * una básqueda directa (No se muestra la ventana de consulta a menos que no
//     * exista lo que se está buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opcián que desea utilizar.
//     * @param intTipBus El tipo de básqueda a realizar.
//     * @return true: Si no se presentá ningán problema.
//     * <BR>false: En el caso contrario.
//     */
    private boolean mostrarChoferes(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            System.out.println("mostrarChoferes:.....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCho.setCampoBusqueda(1);
                    vcoCho.show();
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
    

    //Combo de formulario  del filtro   
    public void comboVehiculo()
    {
        vehiculo.removeAllItems();
        vehiculo.addItem(" ");
        vehiculo.addItem("Carro");
        vehiculo.addItem("Motos");
        vehiculo.addItem("Montacargas");
        vehiculo.addItem("Otros");
    }

   
    
    
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.   EN LA TABLA
//     */
    //combos de la tabla
    private boolean cargarComboTipoVeh(){
        objTblCelEdiCmbBox.removeAllItems();
        objTblCelEdiCmbBox.addItem(" ");
        objTblCelEdiCmbBox.addItem("Carro");
        objTblCelEdiCmbBox.addItem("Moto");
        objTblCelEdiCmbBox.addItem("Montacargas");
        objTblCelEdiCmbBox.addItem("Otros");
        return true;
}
    
     
//    /**
//     * Esta función permite actualizar los registros del detalle.
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    
    private boolean actualizarDet()
    {
        int intNumFil, i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++){
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_veh";
                        strSQL+=" SET tx_pla='" + objTblMod.getValueAt(i,INT_TBL_DAT_PLA_VEH)+"'";
                        strSQL+=",tx_deslar='" + objTblMod.getValueAt(i,INT_TBL_DAT_NOM_VEH) +"'";
                        System.out.println("prepara la consulta::::..."); 
                        strSQL+=",co_mar=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAR),2) +"";  
                        if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().length()>0)
                        {
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Carro"))
                                {strSQL+=",tx_tipVeh='C'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Moto"))
                                {strSQL+=",tx_tipVeh='M'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Montacargas"))
                                {strSQL+=",tx_tipVeh='N'";}
                            if(objTblMod.getValueAt(i,INT_TBL_DAT_TIP_VEH).toString().equals("Otros"))
                                {strSQL+=",tx_tipVeh='O'";}
                        }
                        strSQL+=",nd_pessopkgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_SOP),2) +"";
                        strSQL+=",co_com=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_COM),2) +"";
                        strSQL+=",nd_capTan=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_CAP_TAN),2) +"";
                        strSQL+=",nd_renveh=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_REN_VEH),2) +"";
                        strSQL+=",co_cho=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CHO),2) +"";
                        strSQL+=",ne_ultKil=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_KIL_VEH),2) +"";
                        strSQL+=",fe_ultMod='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())  + "'";
                        strSQL+=",co_usrmod="+ objParSis.getCodigoUsuario() + "";
                        strSQL+=",st_reg='A'";
                        
                        strSQL+=" WHERE co_veh=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_VEH) + "";
                        System.out.println(strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
//     /**
//     * Esta clase hereda de la interface TableModelListener que permite determinar
//     * cambios en las celdas del JTable.
//     */
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

    
    /*
     * llenar la consulta de marcas  
     */
    
    private boolean configurarCombustible()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_com");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_com, a1.tx_deslar";
            strSQL+=" FROM tbm_comVeh a1 WHERE a1.st_reg ='A'";
            strSQL+=" ORDER BY a1.co_com";
            System.out.println("configurarCombustible:.." + strSQL);
            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Combustibles", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//    /**
//     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
//     * una básqueda directa (No se muestra la ventana de consulta a menos que no
//     * exista lo que se está buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opcián que desea utilizar.
//     * @param intTipBus El tipo de básqueda a realizar.
//     * @return true: Si no se presentá ningán problema.
//     * <BR>false: En el caso contrario.
//     */
    private boolean mostrarCombustibles(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
               case 0: //Mostrar la ventana de consulta.
                    vcoCom.setCampoBusqueda(2);
                    vcoCom.setVisible(true);
                    if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCom.buscar("a1.co_com", txtCodCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(0);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.setVisible(true);
                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtNomCom.setText(vcoCom.getValueAt(2));
                        }
                        else
                        {
                            txtCodCom.setText(strCodCom);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoCom.buscar("a1.tx_deslar", txtNomCom.getText()))
                    {
                        txtCodCom.setText(vcoCom.getValueAt(1));
                        txtNomCom.setText(vcoCom.getValueAt(2));
                    }
                    else
                    {
                        vcoCom.setCampoBusqueda(2);
                        vcoCom.setCriterio1(11);
                        vcoCom.cargarDatos();
                        vcoCom.setVisible(true);
                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCom.setText(vcoCom.getValueAt(1));
                            txtNomCom.setText(vcoCom.getValueAt(3));
                        }
                        else
                        {
                            txtNomCom.setText(strNomCom);
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
    
    
}
