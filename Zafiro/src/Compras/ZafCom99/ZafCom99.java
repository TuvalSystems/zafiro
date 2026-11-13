/*
 * ZafCom99.java
 *
 * Created on January 17, 2018, 11:47 AM
 */
package Compras.ZafCom99;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.UltDocPrint;
import Librerias.ZafUtilGP.ZafUtilGP;
import java.math.BigDecimal;
import java.math.BigInteger;
import Librerias.ZafVenConGP.ZafVenConGP; 
import java.math.RoundingMode;
import java.util.HashMap;
/**
 *
 * @author  Gigi
 */
public class ZafCom99 extends javax.swing.JInternalFrame
{   
    //Modelo de la tabla de Com99
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;             //Línea
    private static final int INT_TBL_DAT_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ITM_GRP=2;         //Codigo de item de grupo
    private static final int INT_TBL_DAT_COD_ITM_EMP=3;         //Codigo de item de empresa
    private static final int INT_TBL_DAT_COD_ALT_ITM=4;     //Código alterno del item
    private static final int INT_TBL_DAT_COD_LET_ITM=5;     //Código alterno del item
    private static final int INT_TBL_DAT_BUT_ITM=6;         //buton para consulta del item
    private static final int INT_TBL_DAT_NOM_ITM=7;         //Nombre del item
    private static final int INT_TBL_DAT_UNI_MED=8;         //Unidad de medida    
    private static final int INT_TBL_DAT_COD_GRP=9;         //Unidad de medida
    private static final int INT_TBL_DAT_DES_COR_GRP=10;         //Unidad de medida
    private static final int INT_TBL_DAT_DES_LAR_GRP=11;         //Unidad de medida
    private static final int INT_TBL_DAT_CAN_EGR=12;             //Cantidad
    private static final int INT_TBL_DAT_CAN_ING=13;             //Cantidad
    private static final int INT_TBL_DAT_COS_UNI=14;             //Cantidad
    private static final int INT_TBL_DAT_COS_TOT=15;             //Cantidad
    private static final int INT_TBL_DAT_POR_CAL_COS_UNI=16;         //Unidad de medida
    private static final int INT_TBL_DAT_TIP_REL=17;         //Unidad de medida
    private static final int INT_TBL_DAT_CAN_EGR_AUX=18;             //Cantidad
    private static final int INT_TBL_DAT_CAN_ING_AUX=19;             //Cantidad
    private static final int INT_TBL_DAT_NUM_FIL_BLK=20;             //Cantidad
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafUtilGP objUtiGP;
    
    private ZafDocLis objDocLis;
    ////private ZafVenCon vcoItm, vcoTipDoc, vcoBod;
    private ZafVenConGP vcoItm, vcoTipDoc, vcoBod;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblEgr, objTblCelRenLblIng;
    private ZafTblCelEdiTxt objTblCelEdiTxtEgr, objTblCelEdiTxtIng;
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc;
    private UltDocPrint  objUltDocPrint;
    private ZafStkInv objStkInv;
    private ZafAsiDia objAsiDia;
    
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private java.util.Date datFecDoc;
    
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;    
    private boolean blnHayCam;     
    private String strVersion="v0.39"; 
    private String tx_formato="", tx_formatoDoc="",tx_motivo="",tx_forLot="",tx_tipo="";    
    private int nd_secuencia=0,nd_secLot=0;
    
    private String strCodBod,  strNomBod;                //Contenido del campo al obtener el foco.
    private Vector vecAux;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    private int tipoError  =-1;
    
    //ArrayList para consultar
    private ArrayList arlRegConDoc, arlDatConDoc;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;
    private static final int INT_ARL_CON_COD_TIP_DOC=2;
    private static final int INT_ARL_CON_COD_DOC=3;
    private static final int INT_ARL_CON_COD_USR_ING=4;  
    private static final int INT_ARL_CON_COD_USR_MOD=5;
    
    //Indice de la Consulta del programa 
    private int intIndReg;
    private int intSecGrp, intSecEmp;
    private int intSig=1;
    
    //Para actualizar inventario a través de clase ZafStkInv
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;//Solo egresos
    
    private ArrayList arlRegStkInvItmEgrIng, arlDatStkInvItmEgrIng;
    
    private int intNumFilEgr;
    private ArrayList arlDatQTYUBI,arlRegQTYUBI,arlDatUbiQTY,arlRegUbiQTY,arlDatUbi,arlRegUbi;
    
    //ArrayList para consultar las ubicaciones con su stock correspondiente al item:bodega
    private static final int INT_ARL_CON_BIN=0;  
    private static final int INT_ARL_CON_DISP_P=1;   
    private static final int INT_ARL_CON_DISP_V=2;  
    private static final int INT_ARL_CON_CAN=3;
    private static final int INT_ARL_CON_ITM=4;
    private static final int INT_ARL_CON_BOD=5;
    
    //ArrayList para guardar los bin, con sus stock correspondienta al item:bodega
    private static final int INT_ARL_GUA_COD_EMP=0;  
    private static final int INT_ARL_GUA_COD_LOC=1;
    private static final int INT_ARL_GUA_TIP_DOC=2;
    private static final int INT_ARL_GUA_COD_DOC=3;
    private static final int INT_ARL_GUA_COD_ITEM=4;
    private static final int INT_ARL_GUA_COD_ITEM_ALT=5;
    private static final int INT_ARL_GUA_COD_ITEM_ALT2=6;
    private static final int INT_ARL_GUA_NOM_ITM=7;
    private static final int INT_ARL_GUA_DEC_UNI=8;
    private static final int INT_ARL_GUA_BOD=9;
    private static final int INT_ARL_GUA_COS_UNI=10;
    private static final int INT_ARL_GUA_TOT=11;
    private static final int INT_ARL_GUA_COS_TOT=12;
    private static final int INT_ARL_GUA_COS_UNI_GRU=13;
    private static final int INT_ARL_GUA_COS_TOT_GRU=14;
    private static final int INT_ARL_GUA_FIS_BOD=15;
    private static final int INT_ARL_GUA_REG_REP=16;
    private static final int INT_ARL_GUA_CO_ITM_ACT=17;
    private static final int INT_ARL_GUA_UBI_ITM=18;
    private static final int INT_ARL_GUA_CANT_UBI=19;
    private static final int INT_ARL_GUA_CANTIDAD=20;
////////    
    private static final int INT_ARL_CON_ITEM=1;
    private static final int INT_ARL_CON_3L=2;
    private static final int INT_ARL_CON_BODE=3;
    private static final int INT_ARL_CON_UBI=4;
    private static final int INT_ARL_CON_CANTEGRE=5;
    private static final int INT_ARL_CON_CANTDISP=6;  
    
    private String strBodegaGlob ="";
    private String strCadeItemsGlo="";
    ArrayList<HashMap<String, Object>> listaReservas = new ArrayList<>();        
    
    
    
  
    
    
    /** Creates new form ZafCom77 */
    public ZafCom99(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatConDoc=new ArrayList();
            arlDatStkInvItm=new ArrayList();
            arlDatStkInvItmEgrIng=new ArrayList();
            objStkInv=new Librerias.ZafStkInv.ZafStkInv(objParSis);
            intNumFilEgr=1;
            initComponents();
            configurarFrm();
            agregarDocLis();
            btn_InsertarGP.setVisible(true);
            jButton1.setVisible(false);

            
////            if(objParSis.getCodigoUsuario()!=1){ // JM 2018/1/30 Oculto era una prueba de Ingrid
////                btn_InsertarGP.setVisible(false);
////                
////            }
            
        }
        catch (CloneNotSupportedException e){
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
        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBen = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        btn_InsertarGP = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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

        tabFrm.setAutoscrolls(true);

        panGrl.setAutoscrolls(true);
        panGrl.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 76));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 100, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 6, 10, 20);

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
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(118, 6, 90, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(205, 6, 200, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(406, 6, 20, 20);

        lblBen.setText("Bodega:");
        lblBen.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCab.add(lblBen);
        lblBen.setBounds(4, 26, 70, 20);

        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panCab.add(txtCodBod);
        txtCodBod.setBounds(118, 26, 90, 20);

        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panCab.add(txtNomBod);
        txtNomBod.setBounds(205, 26, 200, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCab.add(butBod);
        butBod.setBounds(406, 26, 20, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(4, 46, 110, 20);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(118, 46, 120, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 110, 20);

        lblNumDoc1.setText("Número documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(432, 27, 100, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
        });
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(550, 26, 120, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Código del documento");
        panCab.add(lblValDoc);
        lblValDoc.setBounds(432, 46, 110, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtValDoc);
        txtValDoc.setBounds(550, 46, 120, 20);

        btn_InsertarGP.setText("InsertarGP");
        btn_InsertarGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_InsertarGPActionPerformed(evt);
            }
        });
        panCab.add(btn_InsertarGP);
        btn_InsertarGP.setBounds(360, 50, 100, 23);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panCab.add(jButton1);
        jButton1.setBounds(280, 50, 73, 23);

        panGrl.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setBorder(javax.swing.BorderFactory.createTitledBorder("Items a ingresar:"));
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

        panGrl.add(panDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGrl.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGrl);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 50));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
// TODO add your handling code here:

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
                //Cerrar la conexión si está abierta.
                if (rst!=null)
                {
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
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
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
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

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        // TODO add your handling code here:
        strCodBod = txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
            if (txtCodBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                mostrarVenConBod(1);
            }
        } else
        txtCodBod.setText(strCodBod);
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        // TODO add your handling code here:
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        // TODO add your handling code here:
        strNomBod = txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
            if (txtNomBod.getText().equals("")) {
                txtCodBod.setText("");
                txtNomBod.setText("");
            } else {
                mostrarVenConBod(2);
            }
        } else
        txtNomBod.setText(strNomBod);
    }//GEN-LAST:event_txtNomBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        // TODO add your handling code here:
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        // TODO add your handling code here:
        mostrarVenConBod(0);
    }//GEN-LAST:event_butBodActionPerformed


    
private boolean validacionDisponibleCosto(java.sql.Connection conExt,int codDoc)
{
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLocIn;        
    boolean blnRes=true;
   double totalCantidad=0;
   String strCadenaItem="",strCadenaCant="";  
   strCadeItemsGlo="";      
   strBodegaGlob="";
   int contador =0;
        
   try
   {
        if (conExt!=null)
        {
            stmLoc=conExt.createStatement();
            strSQL ="";
            strSQL+=" SELECT trim(det.tx_codalt) as tx_itm ,isnull(det.nd_can,0) as nd_can,det.nd_cosuni  \n";
            strSQL+=" FROM  Dynamics2021..tbm_DETMovInv as det   \n";
            strSQL+="  INNER JOIN Dynamics2021..tbm_cabMovInv as cab on (det.co_doc = cab.co_doc and det.co_tipdoc = cab.co_tipdoc) \n";
            strSQL+="  WHERE det.co_tipdoc=300 and  det.co_doc= "+ codDoc  +"\n";
            rstLocIn=stmLoc.executeQuery(strSQL);
            System.out.println("SELECT  DETALLE DE SOLICITUD" + strSQL);  
            strBodegaGlob =txtCodBod.getText();
            strCadenaItem="";
            strCadenaCant="";
            BigDecimal bgdCos = BigDecimal.ZERO;
            BigDecimal bgdCant = BigDecimal.ZERO;
            
            while(rstLocIn.next())
            {
                  bgdCos= new BigDecimal(rstLocIn.getString("nd_cosuni"));
                  bgdCant= new BigDecimal(rstLocIn.getString("nd_can"));

                if (bgdCant.compareTo(new BigDecimal("-1")) <= 0) 
                {
                   BigDecimal bgdCantMod = new BigDecimal(rstLocIn.getString("nd_can"));
                   bgdCantMod = bgdCantMod.multiply(new BigDecimal("-1"));
                   strCadenaItem = objUti.codificar(rstLocIn.getString("tx_itm"))+","+ strCadenaItem;
                   strCadenaCant = bgdCantMod+","+ strCadenaCant;                    
                }
                         
            } 
            if (strCadenaItem.endsWith(",")) 
                strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
            if (strCadenaCant.endsWith(",")) 
                strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                
            
            if((strCadenaItem!="") && (strCadenaCant !=""))
            {
                strCadeItemsGlo=strCadenaItem;
////                mostrarMsgInf("antes consultarReservadosDesdeWeb..");
                
                if (consultarReservadosDesdeWeb(strCadenaItem,strCadenaCant,strBodegaGlob))
                {
////                     mostrarMsgInf("salio consultarReservadosDesdeWeb..");
                    if (validaCosto(conExt,strCadeItemsGlo,bgdCos))
                        blnRes =true;
                    
////                    mostrarMsgInf("salio validaCosto..");
                    
                }
                      
                else
                      blnRes =false;
            }
            else
                blnRes=false;           
        }            

   }  
   catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
   catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
   return blnRes;        
    }

private boolean validaCosto(java.sql.Connection conExt,String item,BigDecimal costo)
{
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLocIn;        
    boolean blnRes=false;
    BigDecimal bgdCos = BigDecimal.ZERO;
   try 
   {
        if (conExt!=null)
        {
            stmLoc=conExt.createStatement();
            strSQL ="";
            strSQL+=" SELECT trim(ITEMNMBR) as itm, isnull(CURRCOST,0) as costo  \n";
            strSQL+=" FROM  "+ objParSis.getNombreBaseDatosGP()  +"..iv00101   \n";
            strSQL+=" WHERE ITEMNMBR = "+ item  +" /*and CURRCOST = "+costo +"*/\n";
            rstLocIn=stmLoc.executeQuery(strSQL);
            
            if(rstLocIn.next())
            {
                bgdCos= new BigDecimal(rstLocIn.getString("costo"));
                if (bgdCos.compareTo(BigDecimal.ZERO) != 0)
                    blnRes = true;
            }
               
                
        }            
   }  
   catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
   catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
   return blnRes;        
    }

private boolean consultarReservadosDesdeWeb(String items,String cantidades,String bodega) {
    java.sql.Connection conn = null;
    java.sql.CallableStatement cstmt = null;
    java.sql.ResultSet rs = null;
    listaReservas.clear();
    

    boolean exito = true;
    
    HashMap<String, Object> hashFila = new HashMap<>();

    try {
        conn = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );
        
        System.out.println(""+ items );

        String procedimiento = "{call dbo.TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
        cstmt = conn.prepareCall(procedimiento);

////         items = "'025-000067-I','025-000127-I','025-000024-I','025-000190-I','028-000226-I','028-000167-I','028-000268-I','943-000300-I','561-000061-S','028-000100-I','024-000052-I','033-000014-S','024-000069-I','028-000021-I','966-000175-I','040-000055-I','953-000011-I','943-000253-I','877-000083-I','970-000077-I','873-000095-I','943-000307-I','953-000118-I','040-000048-I','038-000061-I','038-000042-I','029-000191-I','028-000017-I','028-000093-I','450-000057-I','028-000154-I','020-000147-I','700-000045-I','524-000010-I','028-000365-I','026-000089-I','037-000052-I','028-000334-I','037-000098-I','018-000035-I','900-000001-S','700-000192-I','024-000079-I','037-000048-I','099-000012-I','562-000034-S','953-000065-I','953-000064-I','023-000238-I','945-000001-I','560-000004-S','038-000106-I'";
////         cantidades = "1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0";
////         bodega = "VIA_DAULE";

        cstmt.setString(1, items);
        cstmt.setString(2, cantidades);
        cstmt.setString(3, bodega);
        cstmt.registerOutParameter(4, java.sql.Types.INTEGER); // Output param

        boolean hayResultados = cstmt.execute();

        if (hayResultados) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                HashMap<String, Object> fila = new HashMap<>();
                fila.put("CODIGO", rs.getString("CODIGO"));
                fila.put("BODEGA", rs.getString("BODEGA"));
                fila.put("CANTIDADPEDIDA", rs.getBigDecimal("CANTIDADPEDIDA"));
                fila.put("DISPONIBLEGP", rs.getBigDecimal("DISPONIBLEGP"));
                fila.put("RESERVADOGP", rs.getBigDecimal("RESERVADOGP"));
                fila.put("RESERVADOWEB", rs.getBigDecimal("RESERVADOWEB"));
                fila.put("RESERVADOFINAL", rs.getBigDecimal("RESERVADOFINAL"));
                fila.put("CANTIDADDISPONIBLE", rs.getBigDecimal("CANTIDADDISPONIBLE"));
                listaReservas.add(fila);
                exito=false;
            }
        }

        int resultadoSalida = cstmt.getInt(4);
        System.out.println("Código de resultado SP: " + resultadoSalida);

                        
                        
                String strMsgDisp="";
                if (!listaReservas.isEmpty()) 
                {
                    
                    strMsgDisp="<HTML>Se tiene los siguientes items sin disponibilidad: <BR><BR>";
                    strMsgDisp+="<table BORDER=1><tr><td> ITEM </td> <td> CANTIDAD PEDIDA </td><td> RESERVA WEB </td><td> DISPONIBLE GP </td><td> CANT.DISPONIBLE </td>";                    
                    for (HashMap<String, Object> fila : listaReservas)
                    {
                        strMsgDisp+="<tr><td>" +  fila.get("CODIGO") + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADPEDIDA")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("RESERVADOWEB") + "</td>";
                        strMsgDisp+="<td>" + fila.get("DISPONIBLEGP")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADDISPONIBLE")  + "</td>";

                    }
                        strMsgDisp+="</table><BR>";
                        strMsgDisp+="No poseen stock o se encuentran dentro de un PEDIDO <html>";
                        mostrarMsgInf(strMsgDisp);
                }
                
                
        

    } catch (Exception e) {
        System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        exito = false;
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return exito;
}


    private void btn_InsertarGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_InsertarGPActionPerformed
    try{
        con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
        if(con!=null){
            if (txtNumDoc.getText().equals(""))
            {
                if (validacionValores())
                {
////                    mostrarMsgInf("entro validacion valorea..");
                    if (validacionDisponibleCosto(con,Integer.parseInt(txtCodDoc.getText())))
                    {
////                         mostrarMsgInf("entro validacion disponiblre..");
                       if(GeneraSecuencia(con))
                       {
////                           mostrarMsgInf("entro secuencia..");
                         if (insertarGP(con,tx_formatoDoc,tx_motivo,tx_forLot,tx_tipo))
                         {
                             
                              mostrarMsgInf("Se realizo la insercion a GP  de forma correcta..");
                              obtenerCodGP();
                              consultarReg();
                         }

                        else
                            mostrarMsgInf("Se tiene problema con la insercion a GP, actualice el sistema e intente nuevamente..");
                       }                         
                        
                    }
   
                }
                else
                    mostrarMsgInf("Se tiene valores diferentes entre salida y entrada, actualice el sistema e intente nuevamente..");
            }
            else
                    mostrarMsgInf("Este documento ya se egenero una insercion a GP..");
         }        
    }
    catch (java.sql.SQLException e){
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e){
        objUti.mostrarMsgErr_F1(this, e);
    }    


       
        
        
    }//GEN-LAST:event_btn_InsertarGPActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         isDifCosIngEgrCero();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        // TODO add your handling code here:
        //consultarReg();
    }//GEN-LAST:event_txtNumDocFocusGained



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_InsertarGP;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);
            
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            objUltDocPrint=new Librerias.ZafUtil.UltDocPrint(objParSis);
                
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            configurarVenConTipDoc();
            configurarVenConBod();
            configurarVenConItm();
            configurarTblDat();
            tabFrm.setEnabledAt(1, false); 

            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setEditable(false);
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setEditable(false);
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(550, 4, 120, 20);
            dtpFecDoc.setEnabled(false);
            
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            
            txtCodTipDoc.setVisible(false);
            
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                objTooBar.setVisibleInsertar(true);
//            }
            
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            
            
            vecDat=new Vector();    //Almacena los datos

            panBar.add(objTooBar);
            
              //ToolBar
            panBar.add(objTooBar);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(btn_InsertarGP);            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
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
            vecCab = new Vector(21);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cod.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cod.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cod.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM, "Cod.Let.");            
            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            vecCab.add(INT_TBL_DAT_COD_GRP, "Cód.Grp.");
            vecCab.add(INT_TBL_DAT_DES_COR_GRP, "Des.Cor.Grp.");
            vecCab.add(INT_TBL_DAT_DES_LAR_GRP, "Des.Lar.Grp.");
            vecCab.add(INT_TBL_DAT_CAN_EGR, "Can.Egr.");
            vecCab.add(INT_TBL_DAT_CAN_ING, "Can.Ing.");
            vecCab.add(INT_TBL_DAT_COS_UNI, "Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT, "Cos.Tot.");
            vecCab.add(INT_TBL_DAT_POR_CAL_COS_UNI, "Por.Cal.Cos.Uni.");
            vecCab.add(INT_TBL_DAT_TIP_REL, "Tip.Rel.");
            vecCab.add(INT_TBL_DAT_CAN_EGR_AUX, "Can.Egr.Aux.");
            vecCab.add(INT_TBL_DAT_CAN_ING_AUX, "Can.Ing.Aux.");
            vecCab.add(INT_TBL_DAT_NUM_FIL_BLK, "Núm.Fil.Blk.");
            
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_CAN_EGR);
            arlAux.add("" + INT_TBL_DAT_CAN_ING);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;            
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            
             objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilSel=-1;
//                int intFilEli[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()){
                        intFilSel=tblDat.getSelectedRow();
                        objTblMod.removeEmptyRows();
                        if(eliminaGrpCnv(intFilSel)){
                            
                        }
                            //tblDat.getSelectionModel().addSelectionInterval (11, 12);
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    
                    if(objTblPopMnu.isClickEliminarFila()){
//                        if(isEliGrpCnv(intFilEli)){//se debe eliminar
//                        }

                    }
                }
            });
            
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_GRP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_GRP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_POR_CAL_COS_UNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_REL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FIL_BLK).setPreferredWidth(50);            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_CAL_COS_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblEgr=new ZafTblCelRenLbl();
            objTblCelRenLblEgr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblEgr.setTipoFormato(objTblCelRenLblEgr.INT_FOR_NUM);
            objTblCelRenLblEgr.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblEgr.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLblEgr);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_AUX).setCellRenderer(objTblCelRenLblEgr);
            objTblCelRenLblEgr=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblIng=new ZafTblCelRenLbl();
            objTblCelRenLblIng.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblIng.setTipoFormato(objTblCelRenLblIng.INT_FOR_NUM);
            objTblCelRenLblIng.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblIng.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLblIng);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_AUX).setCellRenderer(objTblCelRenLblIng);
            objTblCelRenLblIng=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            objTblCelEdiTxtEgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellEditor(objTblCelEdiTxtEgr);
            objTblCelEdiTxtEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeCanEgrAnt=BigDecimal.ZERO;
                BigDecimal bdeCanEgrSig=BigDecimal.ZERO;
                String strTipRelEgr="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("DDDDDDDDDDDDDDDDDDDDD");
                    intFil=tblDat.getSelectedRow();
                    bdeCanEgrAnt=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString()));
                    strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
                    
                    objTblCelEdiTxtEgr.setCancelarEdicion(true);
                    
                    if(objParSis.getCodigoMenu()==4202){//Egreso
                        if(strTipRelEgr.equals("E"))
                            objTblCelEdiTxtEgr.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                      if (objUtiGP.esNumeroPositivo(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR)))
                          bdeCanEgrSig=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString()));
                      else
                      {
                          bdeCanEgrSig=bdeCanEgrAnt;
                          objTblMod.setValueAt(bdeCanEgrAnt, intFil, INT_TBL_DAT_CAN_EGR);
                      }
                          
                          
                    
                    if(bdeCanEgrSig.compareTo(BigDecimal.ZERO)<=0)
                        objTblMod.setValueAt(bdeCanEgrAnt, intFil, INT_TBL_DAT_CAN_EGR);
                    calcularCostoTotalEgreso(intFil,bdeCanEgrSig);
                    calcularCostosIngresos(intFil, bdeCanEgrSig);
                    setValorDoc();
////                    generaDiarioAjuste();
                }
            });
            
            objTblCelEdiTxtIng=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellEditor(objTblCelEdiTxtIng);
            objTblCelEdiTxtIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
                int intFil=-1;
                BigDecimal bdeCanIngAnt=BigDecimal.ZERO;
                BigDecimal bdeCanIngSig=BigDecimal.ZERO;
                String strTipRelEgr="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
                    intFil=tblDat.getSelectedRow();
                    strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
                    System.out.println("strTipRelEgr: " + strTipRelEgr);
                    
                    objTblCelEdiTxtIng.setCancelarEdicion(true);
                    
                    if(objParSis.getCodigoMenu()!=4202){//Ingreso
                        
                        if(strTipRelEgr.equals("I"))
                            objTblCelEdiTxtIng.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bdeCanIngSig=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING).toString()));
                    
                    if(bdeCanIngSig.compareTo(BigDecimal.ZERO)<=0){
                        objTblMod.setValueAt(bdeCanIngAnt, intFil, INT_TBL_DAT_CAN_ING);
                    }
                    calcularCostoTotalIngreso(intFil);
                    calcularCostosEgresos(intFil, bdeCanIngSig);
                    setValorDoc();
////                    generaDiarioAjuste();
                }
                
                
            });
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_CAL_COS_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_EGR_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_FIL_BLK, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_LET_ITM);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN_EGR);
            vecAux.add("" + INT_TBL_DAT_CAN_ING);
                        
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[14];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            intColVen[8]=9;
            intColVen[9]=10;
            intColVen[10]=11;
            intColVen[11]=12;
            intColVen[12]=13;
            intColVen[13]=14;
            int intColTbl[]=new int[14];
            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
            intColTbl[1]=INT_TBL_DAT_COD_ITM_GRP;
            intColTbl[2]=INT_TBL_DAT_COD_ITM_EMP;
            intColTbl[3]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[4]=INT_TBL_DAT_COD_LET_ITM;
            intColTbl[5]=INT_TBL_DAT_NOM_ITM;
            intColTbl[6]=INT_TBL_DAT_UNI_MED;            
            intColTbl[7]=INT_TBL_DAT_COD_GRP;
            intColTbl[8]=INT_TBL_DAT_DES_COR_GRP;
            intColTbl[9]=INT_TBL_DAT_DES_LAR_GRP;
            intColTbl[10]=INT_TBL_DAT_CAN_EGR;
            intColTbl[11]=INT_TBL_DAT_TIP_REL;
            intColTbl[12]=INT_TBL_DAT_POR_CAL_COS_UNI;
            intColTbl[13]=INT_TBL_DAT_COS_TOT;
            
            
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                String strSQLAdi="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiTxtVcoItm.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.limpiar();
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
                        
                        if(objParSis.getCodigoMenu()==4202){//Egreso
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                        }
                        else{//Ingreso
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING_AUX);
                        }
                        
                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);                        
                        
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
                        //txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                  
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
                       //// generaDiarioAjuste();
                      //  calcular_totalCostos();
                    }
                }
            });
            
            objTblCelEdiTxtVcoLet=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellEditor(objTblCelEdiTxtVcoLet);
            objTblCelEdiTxtVcoLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                String strSQLAdi="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiTxtVcoLet.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(4);//cambiochiqui
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoLet.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);                        
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
//                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
//                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);

                        if(objParSis.getCodigoMenu()==4202){//Egreso
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                        }
                        else{//Ingreso
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING_AUX);
                        }

                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);
                        
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
//                        txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                        
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
////                        generaDiarioAjuste();
                        //calcular_totalCostos();
                    }
                }
            });
            

            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiButVcoItm.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(3);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
//                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
//                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);

                        if(objParSis.getCodigoMenu()==4202){//Egreso
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                            objTblMod.setValueAt(BigDecimal.ZERO, intFil, INT_TBL_DAT_CAN_ING);
                            objTblMod.setValueAt(BigDecimal.ZERO, intFil, INT_TBL_DAT_CAN_ING_AUX);
                        }
                        else{//Ingreso
                            objTblMod.setValueAt(BigDecimal.ZERO, intFil, INT_TBL_DAT_CAN_EGR);
                            objTblMod.setValueAt(BigDecimal.ZERO, intFil, INT_TBL_DAT_CAN_EGR_AUX);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING);
                            objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_ING_AUX);
                        }

                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);
                   
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
                        //txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                        
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
                        ////generaDiarioAjuste();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch(Exception e) {
            blnRes = false;
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código del item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código del item de empresa";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg = "Código en letras del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                    
                case INT_TBL_DAT_COD_GRP:
                    strMsg = "Código de grupo";
                    break;
                case INT_TBL_DAT_DES_COR_GRP:
                    strMsg = "Descripción corta de grupo";
                    break;
                case INT_TBL_DAT_DES_LAR_GRP:
                    strMsg = "Descripción larga de grupo";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg = "Cantidad de Egreso";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg = "Cantidad de Ingreso";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg = "Costo Unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg = "Costo Total";
                    break;
                case INT_TBL_DAT_POR_CAL_COS_UNI:
                    strMsg = "Porcentaje de cálculo de costo unitario";
                    break;
                case INT_TBL_DAT_TIP_REL:
                    strMsg = "Tipo de relación del inventario";
                    break;
                case INT_TBL_DAT_CAN_EGR_AUX:
                    strMsg = "Cantidad de Egreso Auxiliar";
                    break;
                case INT_TBL_DAT_CAN_ING_AUX:
                    strMsg = "Cantidad de Ingreso Auxiliar";
                    break;
                case INT_TBL_DAT_NUM_FIL_BLK:
                    strMsg = "Número de movimiento del bloque del item que genera el egreso";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        int intTipCnvIngEgr=0;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_itmMae");
            arlCam.add("co_itmEmp");
            arlCam.add("co_itmGrp");
            arlCam.add("tx_codAlt");
            arlCam.add("tx_codAlt2");
            arlCam.add("tx_nomItm");
            arlCam.add("tx_uniMed");
            arlCam.add("co_grp");
            arlCam.add("tx_desCorGrp");
            arlCam.add("tx_desLarGrp");
            arlCam.add("nd_can");
            arlCam.add("tx_tipRel");
            arlCam.add("nd_cosUniEmp");
            arlCam.add("nd_porCalCos");
            arlCam.add("nd_cosTotEgr");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.Emp.");
            arlAli.add("Cód.Itm.Grp.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Let."); 
            arlAli.add("Nom.Itm."); 
            arlAli.add("Uni.Med.");  
            arlAli.add("Cód.Grp.");  
            arlAli.add("Des.Cor.Grp.");  
            arlAli.add("Nom.Grp.");  
            arlAli.add("Can.Egr.");
            arlAli.add("Tip.Rel.");
            arlAli.add("Cos.Uni.Emp.");
            arlAli.add("Por.Cal.Cos.");
            arlAli.add("Cos.Tot.Egr.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("176");
            arlAncCol.add("50");
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("55");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            
            if(objParSis.getCodigoMenu()==4202)//Egreso
                intTipCnvIngEgr=-1;
            else//Ingreso
                intTipCnvIngEgr=1;
            
            //Armar la sentencia SQL.
////            strSQL="";
////            strSQL+=" SELECT b1.co_itmMae, b1.co_itmGrp, b3.co_itmEmp, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_desCor";
////            strSQL+=" , b2.co_grp, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel";
////            strSQL+=" , ((b3.nd_cosUniEmp)*(" + intTipCnvIngEgr + ")) AS nd_cosUniEmp";
////            strSQL+=" , b2.nd_porCalCos";
////            strSQL+=" , ((((b3.nd_cosUniEmp * b2.nd_porCalCos)/b2.nd_can)*b2.nd_can))*(" + intTipCnvIngEgr + ") AS nd_cosTotEgr";
////            strSQL+=" FROM(";
////            strSQL+="       SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmGrp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor, a1.nd_cosUni AS nd_cosUniGrp";
////            strSQL+="       FROM tbm_inv AS a1";
////            strSQL+="       INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
////            strSQL+="       LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
////            strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
////            strSQL+="       AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
////            strSQL+="       ORDER BY a1.tx_codAlt";
////            strSQL+=" ) AS b1";
////            strSQL+=" INNER JOIN(";
////            strSQL+="       SELECT a1.co_emp, a1.co_grp,  a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a1.st_reg";
////            strSQL+="       , a2.co_reg, a2.co_itm, a3.co_itmMae, a2.nd_can, a2.tx_tipRel, a2.st_reg, a2.nd_porCalCos";
////            strSQL+="       FROM tbm_cabCfgConInv AS a1 INNER JOIN tbm_detCfgConInv AS a2";
////            strSQL+="       ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
////            strSQL+="       INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
////            if(objParSis.getCodigoMenu()==4202)
////                strSQL+=" 	    WHERE a1.tx_tipCfg='E' AND a2.tx_tipRel='E' ";
////            else
////                strSQL+=" 	    WHERE a1.tx_tipCfg='I' AND a2.tx_tipRel='I' ";
////            
////            strSQL+=" 	    AND a1.st_reg='A' AND a2.st_reg='A'";
////            strSQL+=" ) AS b2";
////            strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
////            strSQL+=" INNER JOIN(";
////            strSQL+="       SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a1.nd_cosUni AS nd_cosUniEmp";
////            strSQL+="	    FROM tbm_inv AS a1";
////            strSQL+=" 	    INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
////            strSQL+=" 	    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////            strSQL+="       AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
////            strSQL+=" 	ORDER BY a1.tx_codAlt";
////            strSQL+=" ) AS b3";
////            strSQL+=" ON b1.co_itmMae=b3.co_itmMae";
////            strSQL+=" ORDER BY b2.co_grp, b1.tx_codAlt";

strSQL="";
            strSQL+=" SELECT b1.co_itmGrp as co_itmMae, b1.co_itmGrp as co_itmEmp,b1.co_itmGrp, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_uniMed , b2.co_grp, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel , ";
            strSQL+="        ((b1.nd_cosUniGrp)*(-1)) AS nd_cosUniEmp , b2.nd_porCalCos , ((((b1.nd_cosUniGrp * b2.nd_porCalCos)/b2.nd_can)*b2.nd_can))*(-1) AS nd_cosTotEgr ";
            strSQL+=" FROM(";
            strSQL+="       SELECT ITEMNMBR AS co_itmGrp,RTRIM(ITEMNMBR) as tx_codAlt,  RTRIM (ITMSHNAM) AS tx_codAlt2 , RTRIM(ITEMDESC) as tx_nomItm, RTRIM(ITMSHNAM) as tx_desCor , CURRCOST AS nd_cosUniGrp,PRCHSUOM as tx_uniMed ";
            strSQL+="       FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  ";
            strSQL+="       WHERE (ITEMNMBR like '%I' OR ITEMNMBR like '%S' ) ) AS b1";
            strSQL+="       INNER JOIN(    SELECT a1.co_emp, a1.co_grp,  RTRIM(a1.tx_desCor) AS tx_desCorGrp, RTRIM(a1.tx_desLar) AS tx_desLarGrp, a1.tx_obs1, a2.co_reg, a2.co_itm, a2.nd_can, a2.tx_tipRel,  a2.nd_porCalCos";
            strSQL+="       FROM tbm_cabCfgConInv AS a1 ";
            strSQL+="           INNER JOIN tbm_detCfgConInv AS a2       ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp        ";
             if(objParSis.getCodigoMenu()==4202)
                strSQL+="   WHERE a1.tx_tipCfg='E' AND a2.tx_tipRel='E' ";
            else
                strSQL+="   WHERE a1.tx_tipCfg='I' AND a2.tx_tipRel='I' ";
            strSQL+="       and a1.st_reg ='A' and a1.co_emp= "+ objParSis.getCodigoEmpresa() +" ) AS b2 ON  b1.co_itmGrp=b2.co_itm " ;
            ////strSQL+="       ORDER BY b2.co_grp, b1.tx_codAlt";
 
            System.out.println("vcoItm: "+strSQL);

            //Ocultar Columnas
            int intColOcu[]=new int[6];
            intColOcu[0]=1;  // co_itmMae
            intColOcu[1]=2;  // co_itmGrp
            intColOcu[2]=3;  // co_itmEmp
            intColOcu[3]=8;  // co_grp
            intColOcu[4]=10;  // tx_desLarGrp
            intColOcu[5]=12;  // nd_porCalCos
//            intColOcu[6]=14;  // nd_cosTotEgr

            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items a ingresar/egresar", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            vcoItm.setConfiguracionColumna(11, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(13, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
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
            txtCodBod.setText("");
            txtNomBod.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtValDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
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
    private boolean mostrarTipDocPre()//chiqui se cambio l numdoc x el txtCodDoc
    {
        boolean blnRes=true;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
////                    strSQL="";
////                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
////                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
////                    strSQL+=" FROM tbm_cabTipDoc AS a1";
////                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
////                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
////                    strSQL+=" AND a1.co_tipDoc=";
////                    strSQL+=" (";
////                    strSQL+=" SELECT co_tipDoc";
////                    strSQL+=" FROM tbr_tipDocPrg";
////                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
////                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
////                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
////                    strSQL+=" AND st_reg='S'";
////                    strSQL+=" )";
                        strSQL="";
                        strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_deslar) as tx_desLar, ";
                        strSQL+="        isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                        strSQL+="        g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                        strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                        strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
                     

                }
                else{
                        strSQL="";
                           strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_deslar) as tx_desLar, ";
                        strSQL+="        isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                        strSQL+="        g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                        strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                        strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                        strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtCodDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
    private boolean obtenerCodGP()
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                    
                strSQL+=" select rtrim(cb.co_idCabGP) as co_idCabGP";
                strSQL+=" from tbm_cabMovInv cb";
                strSQL+=" inner join (";
                strSQL+="       SELECT c.co_tipDoc ";
                strSQL+="       FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                strSQL+="           INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu) ";
                strSQL+="       WHERE g.co_mnu =" + objParSis.getCodigoMenu() + " ";  
                strSQL+="              AND g.co_emp=" + objParSis.getCodigoEmpresa() + ") as co on (cb.co_tipdoc = co.co_tipDoc) ";  
                strSQL+="       where cb.co_doc =" + txtCodDoc.getText() + " ";
                System.out.println("tbm_cabMovInv: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next()){
                    strAux=rstLoc.getString("co_idCabGP");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                }
                else{
                    blnRes=false;
                }
            rstLoc.close();
            stmLoc.close();
            con.close();
            rstLoc=null;
            stmLoc=null;
            con=null;
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
    private boolean insertarGP(java.sql.Connection conExt, String formatoFinal,String motivo,String formatoLote,String tipo)
    {
         boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strClaveOT ="",strSql ="",strTipoOT="";
        String registroGP ="";
        int intCodRes=-1;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                strTipoOT= formatoFinal;
                strClaveOT= txtCodDoc.getText();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_CREACION_OT_IMPORT(?, ?, ?, ?, ?,?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("DOCUMENTO_ZAFIRO",  strTipoOT);//Envio de parametro
                cal.setString("ORDEN_TRABAJO",  strClaveOT);//Envio de parametro
                cal.setString("TIPO_DOCU",  txtCodTipDoc.getText());//Envio de parametro
                cal.setString("MOTIVO",  motivo);//Envio de parametro
                cal.setString("FORMA_LOTE",  formatoLote);//Envio de parametro
                cal.setString("TIPO_LOTE",  tipo);//Envio de parametro
                
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
    
private boolean validacionValores() {
    boolean blnRes = false;
    BigDecimal cantidadEgre, costoUniEgre, costoEgre;
    BigDecimal cantidadIngre, costoUniIngre, costoIngre;
    BigDecimal TotalIng = BigDecimal.ZERO;
    BigDecimal TotalEgre = BigDecimal.ZERO;

    for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
        // Egreso
        Object valEgre = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR);
        Object valCosEgre = objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI);

        cantidadEgre = (valEgre == null || valEgre.toString().trim().isEmpty())
                ? BigDecimal.ZERO
                : new BigDecimal(valEgre.toString());
        costoUniEgre = (valCosEgre == null || valCosEgre.toString().trim().isEmpty())
                ? BigDecimal.ZERO
                : new BigDecimal(valCosEgre.toString());

        // Ingreso
        Object valIngre = objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING);
        Object valCosIngre = objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI);

        cantidadIngre = (valIngre == null || valIngre.toString().trim().isEmpty())
                ? BigDecimal.ZERO
                : new BigDecimal(valIngre.toString());
        costoUniIngre = (valCosIngre == null || valCosIngre.toString().trim().isEmpty())
                ? BigDecimal.ZERO
                : new BigDecimal(valCosIngre.toString());

        // Calcular totales
        costoEgre = cantidadEgre.multiply(costoUniEgre);
        costoIngre = cantidadIngre.multiply(costoUniIngre);

    TotalEgre = TotalEgre.add(costoEgre).setScale(4, RoundingMode.HALF_UP);
    TotalIng  = TotalIng.add(costoIngre).setScale(4, RoundingMode.HALF_UP);
    }

    // Calcular diferencia absoluta
    TotalEgre = TotalEgre.multiply(new BigDecimal("-1"));
    BigDecimal diferencia = TotalEgre.subtract(TotalIng);

    // ✅ TRUE si la diferencia es menor o igual a 2 centavos
    if (diferencia.compareTo(new BigDecimal("0.02")) <= 0) {
        blnRes = true;
    }

    return blnRes;
}

    
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            con.setAutoCommit(false);
            if(!isVersionActualizada()){
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }
            if(con!=null){
                if (validacionValores())
                {
                      if(insertar_tbmCabMovInv()){
                        if(insertar_tbmDetMovInv())
                        {
                            con.commit();
                            blnRes=true;    
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    mostrarMsgInf("<HTML>No se encuentran iguales los valores que egresan vs los valores que ingresan.</HTML>");
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabMovInv(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecDoc==null)
                    return false;
                getSecuencias();

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("insertar_tbmCabMovInv.tbm_cabMovInv: " + strSQL);
////                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), strSQL);
                
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,tx_numDoc2,";
                strSQL+="        tx_numPed,ne_numOrdDes,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="        tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,nd_porIva,";
                strSQL+="        nd_valIva,nd_tot,nd_pesTotKgr,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,co_motDoc,";
                strSQL+="        co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,tx_usrIng,tx_usrMod,tx_comIng,";
                strSQL+="        tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,ne_numVecRecDoc,fe_ultRecDoc,";
                strSQL+="        tx_obsSolAut,tx_obsAutSol,st_aut,ne_valAut,st_tipDev,st_conInv,st_docGenDevMerMalEst,";
                strSQL+="        st_regRep,ne_numDocRee,st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="        co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,co_usrAutExcDocConVenCon,";
                strSQL+="        tx_comAutExcDocConVenCon,co_ben,tx_benChq,st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,";
                strSQL+="        tx_usrAutAnu,tx_comAutAnu,co_ptoDes,st_emiChqAntRecFacPrv,st_docMarLis,st_itmSerPro,";
                strSQL+="        ne_tipNotPed,co_exp,co_empRelPedEmbImp,co_locRelPedEmbImp,co_tipDocRelPedEmbImp,co_docRelPedEmbImp, st_cieDis, st_ingImp)";
                strSQL+=" VALUES (";
                strSQL+=" " + objParSis.getCodigoEmpresa() + ""; //co_emp
                strSQL+=", " + objParSis.getCodigoLocal() + ""; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", " + intSecGrp;//ne_secGrp
                strSQL+=", " + intSecEmp;//ne_secEmp
                strSQL+=", Null";//ne_numCot
                ////strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + "";//ne_numDoc
                strSQL+=", " + txtCodDoc.getText();//ne_numDoc
                strSQL+=", Null";//tx_numDoc2
                strSQL+=", Null";//tx_numPed
                strSQL+=", Null";//ne_numOrdDes
                strSQL+=", Null";//ne_numGui
                strSQL+=", " + txtCodDoc.getText();//co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                strSQL+=", Null";//co_cli
                strSQL+=", Null";//tx_ruc
                strSQL+=", Null";//tx_nomCli
                strSQL+=", Null";//tx_dirCli
                strSQL+=", Null";//tx_telCli
                strSQL+=", Null";//tx_ciuCli
                strSQL+=", Null";//co_com
                strSQL+=", Null";//tx_nomVen
                strSQL+=", Null";//tx_ate
                strSQL+=", Null";//co_forPag
                strSQL+=", Null";//tx_desForPag
                strSQL+=", " + txtValDoc.getText();//nd_sub
                strSQL+=", 0";//nd_porIva
                strSQL+=", 0";//nd_valIva
                strSQL+=", " + txtValDoc.getText();//nd_tot
                strSQL+=", Null";//nd_pesTotKgr
                strSQL+=", Null";//tx_ptoPar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_motTra
                strSQL+=", Null";//tx_desMotTra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu();//co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecDoc, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultMod
                strSQL+=", '" + objParSis.getNombreUsuario()+"'";//co_usrIng
                strSQL+=", '" + objParSis.getNombreUsuario()+"'";//co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forRet
                strSQL+=", Null";//tx_vehRet
                strSQL+=", Null";//tx_choRet
                strSQL+=", 0";//ne_numVecRecDoc
                strSQL+=", Null";//fe_ultRecDoc
                strSQL+=", Null";//tx_obsSolAut
                strSQL+=", Null";//tx_obsAutSol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valAut
                strSQL+=", 'C'";//st_tipDev
                strSQL+=", 'C'";//st_conInv
                strSQL+=", Null";//st_docGenDevMerMalEst
                strSQL+=", 'I'";//st_regRep
                strSQL+=", Null";//ne_numDocRee
                strSQL+=", 'N'";//st_creGuiRem
                strSQL+=", 'N'";//st_conInvTraAut
                strSQL+=", Null";//co_locRelSolDevVen
                strSQL+=", Null";//co_tipDocRelSolDevVen
                strSQL+=", Null";//co_docRelSolDevVen
                strSQL+=", Null";//st_excDocConVenCon
                strSQL+=", Null";//fe_autExcDocConVenCon
                strSQL+=", Null";//co_usrAutExcDocConVenCon
                strSQL+=", Null";//tx_comAutExcDocConVenCon
                strSQL+=", Null";//co_ben
                strSQL+=", Null";//tx_benChq
                strSQL+=", 'N'";//st_docConMerSalDemDebFac
                strSQL+=", Null";//st_autAnu
                strSQL+=", Null";//fe_autAnu
                strSQL+=", Null";//co_usrAutAnu
                strSQL+=", Null";//tx_comAutAnu
                strSQL+=", Null";//co_ptoDes             objUti.codificar(txtCodBod.getText())
                strSQL+=", Null";//st_emiChqAntRecFacPrv
                strSQL+=", Null";//st_docMarLis
                strSQL+=", Null";//st_itmSerPro
                strSQL+=", Null"; //ne_tipNotPed
                strSQL+=", Null";//co_exp
                strSQL+=", Null";//co_empRelPedEmbImp
                strSQL+=", Null";//co_locRelPedEmbImp
                strSQL+=", Null";//co_tipDocRelPedEmbImp
                strSQL+=", Null";//co_docRelPedEmbImp
                strSQL+=", Null";//st_cieDis               
                strSQL+=", Null";//st_ingImp
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
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
    
     private void  anadirItemsArreglo(ArrayList arlRegUbiQTY,int i,String strCodTipDoc,String strCodDoc)//chiqui
     {
         int j=1;
        //int intCodBodEmp=-1;
        BigDecimal bdeCanEgr=BigDecimal.ZERO;
        BigDecimal bdeCanIng=BigDecimal.ZERO;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        BigDecimal bdeSigCanEgr=BigDecimal.ZERO;
        String strTipRelEgr="";
        int intNumFil=0;
         
        
         arlRegUbiQTY.add(INT_ARL_GUA_COD_EMP,objParSis.getCodigoEmpresa());//cod_emp
         arlRegUbiQTY.add(INT_ARL_GUA_COD_LOC,objParSis.getCodigoLocal());//co_loc
         arlRegUbiQTY.add(INT_ARL_GUA_TIP_DOC,txtCodTipDoc.getText());//co_tipdoc
         arlRegUbiQTY.add(INT_ARL_GUA_COD_DOC,txtCodDoc.getText());//co_doc
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP)));//co_item
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM_ALT,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)));//tx_codalt
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM_ALT2,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM)));//tx_codalt2
         arlRegUbiQTY.add(INT_ARL_GUA_NOM_ITM,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)));//tx_nomitm
         arlRegUbiQTY.add(INT_ARL_GUA_DEC_UNI,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_UNI_MED)));//tx_unimed
         arlRegUbiQTY.add(INT_ARL_GUA_BOD,txtCodBod.getText());//tx_bod
         ////arlRegUbiQTY.add(INT_ARL_GUA_BOD,txtNomBod.getText().getText());//tx_bod ojo
         arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI,objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()),objParSis.getDecimalesMostrar()).abs());//nd_cosUni
         if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
         {
              arlRegUbiQTY.add(INT_ARL_GUA_TOT,objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesMostrar()) );//nd_tot
              arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT, objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()),objParSis.getDecimalesMostrar()) );//nd_costot
              arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI_GRU,objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesMostrar()) );//nd_cosunigrp
              arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT_GRU, objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesMostrar()) );//nd_costotgrp
         }
         else
         {
              arlRegUbiQTY.add(INT_ARL_GUA_TOT, objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesMostrar()) );//nd_tot
              arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT, objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesMostrar()) );//nd_costot
              arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI_GRU,objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesMostrar()) );//nd_cosunigrp
              arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT_GRU, objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesMostrar()) );//nd_costotgrp  
         }
        arlRegUbiQTY.add(INT_ARL_GUA_FIS_BOD,'S' );//st_strMerIngEgrFisBodTipDoc
        arlRegUbiQTY.add(INT_ARL_GUA_REG_REP,'I' );   //st_regRep 
        arlRegUbiQTY.add(INT_ARL_GUA_CO_ITM_ACT,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP)) );//co_itmact
              
     }
     private boolean validarStockDisponible(java.sql.Connection conExt, int i,String strCodTipDoc,String strCodDoc)//chiqui
    { 
         boolean blnRes=true, blnIsStock=true;
         java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strMsg="", strItem="";
        Boolean blvalistock =false;
        tipoError=0;
        try
        {
            if(conExt!=null)
            {
               stmLoc= conExt.createStatement();

              strSQL="";
                    strSQL+=" SELECT RTRIM(BIN) as BIN,(u.QUANTITY - u.ATYALLOC) as disponibleU,d.DISPONIBLE as disponibleGP,RTRIM(d.ITEMNMBR) AS ITEM,u.QUANTITY,d.LOCNCODE as bodega , sum (d.DISPONIBLE) as total  \n";
                    strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..[vw_consulta_disponible_transito] as d \n";
                        strSQL+=" INNER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 as u on (d.ITEMNMBR = u.ITEMNMBR and d.LOCNCODE = u.LOCNCODE)  \n";
                    strSQL+=" WHERE d.ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)) +" and \n";
                    strSQL+="       d.LOCNCODE ='"+ txtCodBod.getText() +"' AND BIN not like 'PED%' and u.QUANTITY >=0   \n";
                    strSQL+=" group by BIN,d.DISPONIBLE ,d.ITEMNMBR,u.QUANTITY,d.LOCNCODE,u.ATYALLOC \n";
                    strSQL+=" order by u.QUANTITY desc    \n";
                    
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL); 
                arlDatQTYUBI= new ArrayList();
                double dblCantidadGlobal =0;
                double dblCantidadTotalDis =0;
                //rrecore todas las ubicciones
                  while(rstLoc.next())
                    {
                        arlRegQTYUBI = new ArrayList();
                        arlRegQTYUBI.add(INT_ARL_CON_BIN,rstLoc.getString("BIN"));
                        arlRegQTYUBI.add(INT_ARL_CON_DISP_P,rstLoc.getDouble("disponibleU"));
                        arlRegQTYUBI.add(INT_ARL_CON_DISP_V,rstLoc.getDouble("disponibleGP"));
                        arlRegQTYUBI.add(INT_ARL_CON_CAN,rstLoc.getDouble("QUANTITY"));
                        arlRegQTYUBI.add(INT_ARL_CON_ITM,rstLoc.getString("ITEM"));
                        arlRegQTYUBI.add(INT_ARL_CON_BOD,rstLoc.getString("bodega"));
                        dblCantidadTotalDis = dblCantidadTotalDis + objUti.parseDouble(rstLoc.getString("total"));
                        arlDatQTYUBI.add(arlRegQTYUBI);
                    }
                  stmLoc.close();
                  stmLoc=null;
                  rstLoc.close();
                  rstLoc=null;
                  //revisa si hay stock
                 if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                {
                    dblCantidadGlobal =objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                    if ((dblCantidadGlobal >dblCantidadTotalDis) || (dblCantidadGlobal < 0))
                    {
                     blvalistock=false;
                    }else{
                         blvalistock=true;
                    }
                }
                else
                {
                    dblCantidadGlobal =objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                    blvalistock=true;
                    
                }
                if ((blvalistock==true) && ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0)))
                {
                  double dblCantidad =0, dblCanUbi =0,resul=0,dblCanDispGP =0,dblCostoUni=0;
                  String strUbica ="",strndCan="";
                  Boolean existe =false;
                  
                  dblCantidad=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                    if (dblCantidad ==0)
                    {
                        dblCantidad=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                        //dblCantidad = dblCantidad * (-1);
                    }
                   arlDatUbiQTY= new ArrayList();
                   for(int j=0; j<arlDatQTYUBI.size(); j++)
                   {
                      arlRegUbiQTY = new ArrayList();
                      dblCanUbi=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_P);
                      strUbica =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                      dblCanDispGP =objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V); 
                      if (dblCantidad  == dblCanUbi )
                           {
                               anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                               arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                               arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                               if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                 {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                 }
                                 else
                                 {
                                    strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                 }
                                 arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can

                               arlDatUbiQTY.add(arlRegUbiQTY);
                               resul = dblCantidad - dblCanUbi;
                               dblCantidad =resul;
                               if (resul <=0)
                               {
                                   existe=true;
                                   break;
                               }
                           }
                           else
                           {
                               if (dblCantidad  < dblCanUbi )
                               {
                                   anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                   arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                   if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) 
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                   {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                   }
                                   else
                                   {
                                     strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                   }
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                   
                                   arlDatUbiQTY.add(arlRegUbiQTY);
                                   resul = dblCantidad - dblCanUbi;
                                   if (resul <=0)
                                    {
                                        existe=true;
                                        break;
                                    }
                               }
                               else
                               {
                                    if (dblCantidad  > dblCanUbi )
                                    {
                                        anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                        arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                        arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                        if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                            (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                        {
                                            strndCan = "-" + dblCantidad ;//nd_can
                                        }
                                        else
                                        {
                                            strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                        }
                                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                        arlDatUbiQTY.add(arlRegUbiQTY);
                                        resul = dblCantidad - dblCanUbi;
                                        dblCantidad =resul;
                                        if (resul <=0)
                                        {
                                            existe=true;
                                             break;
                                        }
                                    }
                               }
                           }
                   }
                   if (existe == true)
                   {
                       if (dblCantidad <= dblCanDispGP )
                       {
                           blvalistock=true;
                       }
                       else
                       {
                            blvalistock=false;
                       }
                       String  strTit="";
                         if(blvalistock =false)
                         {
                            strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";

                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";
                            blnRes=false;
                         }
                        if(existe == false)
                        {
                            strMsg="<html> La solicitud posee Items . <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> NO </td> <td> posee ubicaciones asignadas </td><td> Can.Sol. </td>";
                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";   
                        }
                   }
                   else
                   {
                       
                       blnRes=false;
                   }
                 }
                else
                     blnRes=false;
                
               
                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING))>0))
                {
                    boolean validaExisteUbic= false;
                     double dblCantidadI ,dblCantidadIU=0;
                     String strUbicaI ="",strndCanI="";
                     Boolean existe =false;
                     for(int j=0; j<arlDatQTYUBI.size(); j++)
                     {
                          validaExisteUbic=true;
                          arlRegUbiQTY = new ArrayList();
                          dblCantidadIU=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V);
                          strUbicaI =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                          dblCantidadI=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                          if (dblCantidadIU >0)
                          {
                             anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                             arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                             arlDatUbiQTY.add(arlRegUbiQTY);
                             blnRes=true; 
                             break;
                          }
                          if("GENERAL".equals(strUbicaI))
                          {
                             anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                             arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                             arlDatUbiQTY.add(arlRegUbiQTY);
                             blnRes=true; 
                             break;
                          }
                      else
                      {
                             anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                             arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                             arlDatUbiQTY.add(arlRegUbiQTY);
                             blnRes=true; 
                             break;                          
//////////////////                          blnRes=false;
                      }
                      
                     }
                    if (validaExisteUbic== false)
                        tipoError=1;
                     
////                     if (validaExiste== false)
////                     {
////                         strUbicaI="GENERAL";
////                         dblCantidadI=  objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
////                          arlRegUbiQTY = new ArrayList();
////                          anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
////                         arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
////                         arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
////                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
////                         arlDatUbiQTY.add(arlRegUbiQTY);
////                         blnRes=true; 
////                          
////                     }
                     
                     
                }
                
            }
            else{
                blnRes=false;
            }
        }
           catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
         return blnRes;
     }

private boolean validarStockDisponibleInsertar(java.sql.Connection conExt, int i,String strCodTipDoc,String strCodDoc)//chiqui
    { 
         boolean blnRes=true, blnIsStock=true;
         java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strMsg="", strItem="";
        Boolean blvalistock =false;
        
       
        double dblCantidadGlobal =0;
        double dblCantidadTotalDis =0;
        int contador =0;        
        tipoError=0;
        try
        {
            if(conExt!=null)
            {
               stmLoc= conExt.createStatement();
                          strSQL="";
                strSQL+=" SELECT trim (de.tx_codalt) as item, trim(de.tx_bin) as bin ,(iv12.QUANTITY-iv12.ATYALLOC) as disponibleGP, de.nd_can as cantEgre,  \n";
                strSQL+="   ((iv12.QUANTITY-iv12.ATYALLOC)+de.nd_can) as disponibleTot, de.tx_bod as bodega , trim(iv11.ITMGEDSC) as tresl  \n";                
                strSQL+=" from Dynamics2021..tbm_detMovInv as de \n";
                strSQL+=" INNER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 as iv12 on (de.tx_bin =iv12.bin and de.tx_codalt = iv12.ITEMNMBR and de.tx_bod = iv12.LOCNCODE)  \n";
                strSQL+=" WHERE de.ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)) +" and \n";
                strSQL+="       de.nd_can <0 and iv12.BIN not like 'PED%' AND de.co_cod ="+ txtCodDoc.getText() +"  \n";
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);  
                arlDatUbi= new ArrayList();
                while(rstLoc.next())
                {
                            dblCantidadTotalDis =  objUti.parseDouble(rstLoc.getString("disponibleTot")); 
                            if (dblCantidadTotalDis < 0)
                            {
                                arlRegUbi = new ArrayList();
                                arlRegUbi.add(INT_ARL_CON_ITEM,rstLoc.getString("item"));
                                arlRegUbi.add(INT_ARL_CON_3L,rstLoc.getString("tresl"));
                                arlRegUbi.add(INT_ARL_CON_BODE,rstLoc.getString("bodega"));
                                arlRegUbi.add(INT_ARL_CON_UBI,rstLoc.getString("bin"));
                                arlRegUbi.add(INT_ARL_CON_CANTEGRE,rstLoc.getDouble("cantEgre"));
                                arlRegUbi.add(INT_ARL_CON_CANTDISP,rstLoc.getDouble("disponibleGP"));
                                arlDatUbi.add(arlRegUbi);
                                
                                blnRes=false;
                                blvalistock=true;
                                //break;
                            }
                }                        
                
                
                
                
                  strSQL="";
                        strSQL+=" SELECT RTRIM(BIN) as BIN,(u.QUANTITY - u.ATYALLOC) as disponibleU,d.DISPONIBLE as disponibleGP,RTRIM(d.ITEMNMBR) AS ITEM,u.QUANTITY,d.LOCNCODE as bodega , sum (d.DISPONIBLE) as total  \n";
                        strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..[vw_consulta_disponible_transito] as d \n";
                            strSQL+=" INNER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 as u on (d.ITEMNMBR = u.ITEMNMBR and d.LOCNCODE = u.LOCNCODE)  \n";
                        strSQL+=" WHERE d.ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)) +" and \n";
                        strSQL+="       d.LOCNCODE ='"+ txtCodBod.getText() +"' AND BIN not like 'PED%' and u.QUANTITY >=0   \n";
                        strSQL+=" group by BIN,d.DISPONIBLE ,d.ITEMNMBR,u.QUANTITY,d.LOCNCODE,u.ATYALLOC \n";
                        strSQL+=" order by u.QUANTITY desc    \n";

                    System.out.println("validarStockDisponible: "+ strSQL);
                    rstLoc=stmLoc.executeQuery(strSQL);     
////                  arlDatUbi= new ArrayList();
                  while(rstLoc.next())
                    {
////                            arlRegQTYUBI = new ArrayList();
////                            arlRegQTYUBI.add(INT_ARL_CON_BIN,rstLoc.getString("BIN"));
////                            arlRegQTYUBI.add(INT_ARL_CON_DISP_P,rstLoc.getDouble("disponibleU"));
////                            arlRegQTYUBI.add(INT_ARL_CON_DISP_V,rstLoc.getDouble("disponibleGP"));
////                            arlRegQTYUBI.add(INT_ARL_CON_CAN,rstLoc.getDouble("QUANTITY"));
////                            arlRegQTYUBI.add(INT_ARL_CON_ITM,rstLoc.getString("ITEM"));
////                            arlRegQTYUBI.add(INT_ARL_CON_BOD,rstLoc.getString("bodega"));
                            dblCantidadTotalDis =  objUti.parseDouble(rstLoc.getString("disponibleU"));
////                            arlDatQTYUBI.add(arlRegQTYUBI);
                            contador ++;
                            blvalistock=true;
                    }                    
                            



                //rrecore todas las ubicciones

                  stmLoc.close();
                  stmLoc=null;
                  rstLoc.close();
                  rstLoc=null;
////                  if (contador >1)
////                    blnRes=false;
////                  else
////                  {
////                      blnRes =true;
////                  }
                    

                if ((blvalistock==true) && ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0)))
                {
                  double dblCantidad =0, dblCanUbi =0,resul=0,dblCanDispGP =0,dblCostoUni=0;
                  String strUbica ="",strndCan="";
                  Boolean existe =false;

                  dblCantidad=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                  if (dblCantidad >dblCantidadTotalDis )
                  {
                    arlRegUbi = new ArrayList(); 
                    arlRegUbi.add(INT_ARL_CON_ITEM,objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM));
                  }

                   for(int j=0; j<arlDatQTYUBI.size(); j++)
                   {
                      arlRegUbiQTY = new ArrayList();
                      dblCanUbi=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_P);
                      strUbica =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                      dblCanDispGP =objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V); 
                      if (dblCantidad  == dblCanUbi )
                           {
                               anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                               arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                               arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                               if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                 {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                 }
                                 else
                                 {
                                    strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                 }
                                 arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can

                               arlDatUbiQTY.add(arlRegUbiQTY);
                               resul = dblCantidad - dblCanUbi;
                               dblCantidad =resul;
                               if (resul <=0)
                               {
                                   existe=true;
                                   break;
                               }
                           }
                           else
                           {
                               if (dblCantidad  < dblCanUbi )
                               {
                                   anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                   arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                   if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) 
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                   {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                   }
                                   else
                                   {
                                     strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                   }
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                   
                                   arlDatUbiQTY.add(arlRegUbiQTY);
                                   resul = dblCantidad - dblCanUbi;
                                   if (resul <=0)
                                    {
                                        existe=true;
                                        break;
                                    }
                               }
                               else
                               {
                                    if (dblCantidad  > dblCanUbi )
                                    {
                                        anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                        arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                        arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                        if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                            (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                        {
                                            strndCan = "-" + dblCantidad ;//nd_can
                                        }
                                        else
                                        {
                                            strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                        }
                                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                        arlDatUbiQTY.add(arlRegUbiQTY);
                                        resul = dblCantidad - dblCanUbi;
                                        dblCantidad =resul;
                                        if (resul <=0)
                                        {
                                            existe=true;
                                             break;
                                        }
                                    }
                               }
                           }
                   }
                   if (existe == true)
                   {
                       if (dblCantidad <= dblCanDispGP )
                       {
                           blvalistock=true;
                       }
                       else
                       {
                            blvalistock=false;
                       }
                       String  strTit="";
                         if(blvalistock =false)
                         {
                            strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";

                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";
                            blnRes=false;
                         }
                        if(existe == false)
                        {
                            strMsg="<html> La solicitud posee Items . <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> NO </td> <td> posee ubicaciones asignadas </td><td> Can.Sol. </td>";
                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";   
                        }
                   }
                   else
                   {
                       
                       blnRes=false;
                   }
                 }
                else
                     blnRes=false;
                
               
                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING))>0))
                {
                    boolean validaExisteUbic= false;
                     double dblCantidadI ,dblCantidadIU=0;
                     String strUbicaI ="",strndCanI="";
                     Boolean existe =false;
                     for(int j=0; j<arlDatQTYUBI.size(); j++)
                     {
                          validaExisteUbic=true;
                          arlRegUbiQTY = new ArrayList();
                          dblCantidadIU=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V);
                          strUbicaI =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                          dblCantidadI=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                          if (dblCantidadIU >0)
                          {
                             anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                             arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                             arlDatUbiQTY.add(arlRegUbiQTY);
                             blnRes=true; 
                             break;
                          }
                          if("GENERAL".equals(strUbicaI))
                          {
                             anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                             arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                             arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                             arlDatUbiQTY.add(arlRegUbiQTY);
                             blnRes=true; 
                             break;
                      }
                      else
                      {
                          blnRes=false;
                      }
                      
                     }
                    if (validaExisteUbic== false)
                        tipoError=1;
                     
////                     if (validaExiste== false)
////                     {
////                         strUbicaI="GENERAL";
////                         dblCantidadI=  objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
////                          arlRegUbiQTY = new ArrayList();
////                          anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
////                         arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
////                         arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
////                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
////                         arlDatUbiQTY.add(arlRegUbiQTY);
////                         blnRes=true; 
////                          
////                     }
                     
                     
                }
                
            }
            else{
                blnRes=false;
            }
        }
           catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } 
         return blnRes;
     }

     
     private boolean validarItemInactivo(java.sql.Connection conExt)//chiqui
         {
            boolean blnRes=true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int stinactivo=0;
             try
            {
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();
                    for (int l=0;l<arlDatUbiQTY.size();l++)
                    {
                        strSQL="";
                        strSQL+=" SELECT inactive  \n";
                        strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as d \n";
                        strSQL+=" WHERE d.ITEMNMBR ='"+ objUti.getStringValueAt (arlDatUbiQTY, l, INT_ARL_GUA_COD_ITEM_ALT) +"' \n";
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            stinactivo = rstLoc.getInt("inactive");
                            
                        }
                       
                        if (stinactivo==1)
                        {
                            blnRes=false;
                            break;
                        }
                        else
                            blnRes=true;
                    }
                    
                }
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
         }
     
     
     private boolean GeneraSecuencia(java.sql.Connection conExt)//chiqui
         {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int stinactivo=0;
            tx_formato="";
            nd_secuencia=0;
            tx_formatoDoc="";
            
             try
            {
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();
////                        antes del lote configurable
////                        strSQL="";
////                        strSQL+=" SELECT m.tx_formato,m.tx_secuencia,m.tx_mot ,m.nd_secuencia  \n";
////                        strSQL+=" from tbm_cfgMotMnu as m \n";
////                        strSQL+="      inner join tbm_cabtipdoc as c on (m.co_tipDoc =c.co_tipdoc ) \n";
////                        strSQL+=" WHERE m.co_tipDoc = "+ txtCodTipDoc.getText() +" and c.tx_descor = '"+ txtDesCorTipDoc.getText()  +"' \n";
                        strSQL="";
                        strSQL+=" SELECT m.tx_formato,m.tx_secuencia,m.tx_mot ,m.nd_secuencia,cl.tx_for as tx_forLot, cl.nd_sec as nd_secLote\n";
                        strSQL+=" from tbm_cfgMotMnu as m \n";
                        strSQL+="      inner join tbm_cabtipdoc as c on (m.co_tipDoc =c.co_tipdoc ) \n";
                        strSQL+="      inner join tbr_mnvLot as ml on (m.co_mnu = ml.co_mnu and m.co_tipDoc = ml.co_tipdoc) \n";       
                        strSQL+="      inner join tbm_cfglot as cl on (ml.co_lot =cl.co_lot) \n";                           
                        strSQL+=" WHERE m.co_tipDoc = "+ txtCodTipDoc.getText() +" and c.tx_descor = '"+ txtDesCorTipDoc.getText()  +"' \n";
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            tx_formato = rstLoc.getString("tx_formato");
                            nd_secuencia = rstLoc.getInt("nd_secuencia");
                            tx_formatoDoc = tx_formato + String.format("%06d", nd_secuencia+1);
                            tx_motivo = rstLoc.getString("tx_mot");
                            tx_forLot = rstLoc.getString("tx_forLot");
                            tx_tipo = rstLoc.getString("tx_forLot");
                            nd_secLot = rstLoc.getInt("nd_secLote");
                            tx_forLot = tx_forLot + String.format("%06d", nd_secLot+1);
                            blnRes=true;
                        }
                }
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
         }
        
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        //int intCodBodEmp=-1;
        BigDecimal bdeCanEgr=BigDecimal.ZERO;
        BigDecimal bdeCanIng=BigDecimal.ZERO;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        BigDecimal bdeSigCanEgr=BigDecimal.ZERO;
        String strTipRelEgr="";
        int intNumFil=0;
        int k=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                //intCodBodEmp=getCodigoBodegaEmpresa();
                arlDatStkInvItm.clear();
                arlDatStkInvItmEgrIng.clear();
                
                
              
                
                
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    blnRes=validarStockDisponible(con,i,txtCodTipDoc.getText(),txtCodDoc.getText());
                      if(blnRes==true)
                    {
                        if(validarItemInactivo(con))
                        {
                               for ( k=0;k<arlDatUbiQTY.size();k++)
                            {
                                strSQL="";
                                strSQL+="INSERT INTO tbm_detMovInv(";
                                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,tx_codAlt,tx_codAlt2,";
                                strSQL+="        tx_nomItm,tx_uniMed,tx_bod,nd_can,nd_canDev,nd_cosUni,";
                                strSQL+="        nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                                strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                                strSQL+="        nd_canNunRec,st_regRep,co_itmAct,ne_numFil,tx_bin,nd_canBin)";
                                strSQL+=" VALUES (";
                                strSQL+=" " + objParSis.getCodigoEmpresa() + ""; //co_emp
                                strSQL+=", " + objParSis.getCodigoLocal() + ""; //co_loc
                                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                                strSQL+=", " + txtCodDoc.getText(); //co_doc
                                strSQL+=", " + j; //co_reg
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM)+"'"; //co_itm
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM_ALT)+"'"; //tx_codAlt
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM_ALT2)+"'"; //tx_codAlt2
                                strSQL+=", " + objUti.codificar( objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_NOM_ITM))+""; //tx_nomItm
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_DEC_UNI)+"'"; //tx_uniMed
                                strSQL+=", '" + txtCodBod.getText() + "'"; //tx_bod
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_CANTIDAD); //nd_can
                                strSQL+=", 0";//nd_canDev
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_UNI); //nd_cosUni
                                strSQL+=", 0";//nd_porDes
                                strSQL+=", 'N'";//st_ivaCom
                                strSQL+="," + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_TOT); //nd_tot
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_TOT); //nd_cosTot
                                strSQL+=", 0";//nd_exi
                                strSQL+=", 0";//nd_valExi
                                strSQL+=", 0";//nd_cosPro
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_UNI_GRU); //nd_cosUniGrp
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_TOT_GRU); //nd_cosTotGrp
                                strSQL+=", 0";//nd_exiGrp
                                strSQL+=", 0";//nd_valExiGrp
                                strSQL+=", 0";//nd_cosProGrp                    
                                strSQL+=", 'S'";//st_merIngEgrFisBod
                                strSQL+=", 0";//nd_canCon
                                strSQL+=", 0";//nd_canNunRec
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_REG_REP)+"'"; //st_regRep
                                strSQL+=", " + objUti.getIntValueAt(arlDatUbiQTY, k, INT_ARL_GUA_CO_ITM_ACT); //co_itmAct
                                strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK) + "";//ne_numFil
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_UBI_ITM)+"'"; //tx_bin
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_CANT_UBI); //nd_canBin
                                strSQL+=");"; 
                           } /////swui
                        }
                        else
                        {
                            mostrarMsgInf("<HTML>Existen items que se encuentran en estado INACTIVO.<BR> Verifique y vuelva a intentarlo.</HTML>");
                            blnRes=false;
                            break;  
                        }
                           
                         
                    }
                    else
                    {
                         blnRes=false;
                         break;
                    }
                      
                    //Armar la sentencia SQL.
                    
                    strSQLIns+=strSQL;
                    j++;
                    if(objTooBar.getEstado()=='n'){
                        strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                        if(strTipRelEgr.equals("E")){
                            arlRegStkInvItm = new ArrayList();
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_GRP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_LET_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_CAN_ITM,     bdeCanEgrIng);//el valor está en (+) y en (-)
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_BOD_EMP, txtCodBod.getText());
                            arlDatStkInvItm.add(arlRegStkInvItm);
                        }
                        //se almacena todo, sea ingreso o egreso
                        arlRegStkInvItmEgrIng=new ArrayList();
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_GRP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_LET_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_CAN_ITM,     bdeCanEgrIng);//el valor está en (+) y en (-)
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_BOD_EMP, txtCodBod.getText());
                        arlDatStkInvItmEgrIng.add(arlRegStkInvItmEgrIng);
                       }
                    
                        
                    
                }
                if(objTooBar.getEstado()=='n'){
////                    if(objStkInv.validaStk_Dis_InvBod(con, objParSis.getCodigoEmpresa(), arlDatStkInvItm, 1, "+", 0)){//valida stock de inventario (Egreso)
////                        if(objStkInv.validaStk_Dis_InvBod(con, objParSis.getCodigoEmpresa(), arlDatStkInvItm, 1, "+", 10)){//valida disponible de inventario (Egreso)
////                            if(objStkInv.actualizaInventario(con, objParSis.getCodigoEmpresa(), 0, "+", 1, arlDatStkInvItmEgrIng)){//tbm_inv  (egreso e ingreso)
////                                    if(objStkInv.actualizaDisponible(con, objParSis.getCodigoEmpresa(), 10, "+", 1, arlDatStkInvItmEgrIng)){ // disponible (ingreso/egreso)
////                                        arlDatStkInvItm.clear();
////                                    }
////                                    else{
////                                        mostrarMsgInf("Es posible que existan problemas de disponible");
////                                        blnRes=false;
////                                    }
////                            }
////                            else{
////                                mostrarMsgInf("<HTML>Existen problemas de stock de inventario.<BR> Verifique el item : <FONT COLOR=\"blue\">" + objStkInv.getStrCodAltItmSinStk() + "</FONT>.</HTML>");
////                                blnRes=false;
////                            }
////                        }
////                        else{
////                            mostrarMsgInf("Es posible que existan problemas de disponible de inventario");
////                            blnRes=false;
////                        }
////                    }
////                    else{
////                        mostrarMsgInf("<HTML>Existen problemas de inventario de bodega.<BR> Verifique y vuelva a intentarlo.</HTML>");
////                        blnRes=false;
////                    }
                } 
                if (blnRes==true) 
                {
                     System.out.println("insertar_tbmDetMovInv: " + strSQLIns);
                     stm.executeUpdate(strSQLIns);
                     stm.close();
                     stm=null;
                }
                else
                {

                    stm.close();
                     stm=null;
                     if (tipoError==-1)
                        mostrarMsgInf("<HTML>Existen problemas (costo-stock-ubicacion) con el item inventario de bodega.<BR> Verifique y vuelva a intentarlo.</HTML>");
                     else
                         mostrarMsgInf("<HTML>Existen items que no tienen una ubicación asignada.<BR> Asigne la ubicación al item y vuelva a intentarlo</HTML>");
                     
                     blnRes=false;
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
     * Esta función permite costear los items de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean costearItm(){
        int i;
        boolean blnRes=true;
        try{
            if (con!=null){
                switch (objTooBar.getEstado()){                
                    case 'n':
                        System.out.println("costearDocumento");
                        objUti.costearDocumento(this, objParSis, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    case 'm':
                        //Validar la fecha  del documento sólo si se encuentra en el modo "Modificar".
                        //Validar si se utiliza la fecha que tenia el documento inicialmente o la que tiene actualmente.
                        if (datFecDoc.compareTo(objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"))>0){
                            for (i=0;i<objTblMod.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            }
                        }
                        else{
                            for (i=0;i<objTblMod.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                            }
                        }
                        break;
                    default:
                        for (i=0;i<objTblMod.getRowCountTrue();i++) {
                            System.out.println("recostearItm");
                            //recostea la empresa
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            //recostea el grupo
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                        }
                        break;
                }                
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
     * Función que devuelve el código de bodega por empresa
     * @param conexion Conexión a la base de datos
     * @return el Código de la bodega por empresa
     */
    private int getCodigoBodegaEmpresa(){
        int intCodBodEmp=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.TX_bod, a1.co_empgrp, a1.co_bodgrp";
                strSQL+=" FROM tbr_bodempbodgrp AS a1";
                strSQL+=" WHERE a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.co_bodGrp=" + txtCodBod.getText() + "";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                intCodBodEmp=rst.getInt("co_bod");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodBodEmp;
    }
    
    
    /**
     * Función que permite obtener las secuencias de grupo y secuencias de empresa
     * @return true Si se pudo realizar la operación
     */
    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                intSecGrp=objUltDocPrint.getNumSecDoc(con, objParSis.getCodigoEmpresaGrupo());//la empresa es el grupo
                intSecEmp=objUltDocPrint.getNumSecDoc(con, objParSis.getCodigoEmpresa());//la empresa-importador
           }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
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
    private boolean isCamVal(){
        if(txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if(txtCodBod.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }
        
        if (txtCodDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if(objTooBar.getEstado()=='n'){
            if(!objTblMod.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblMod.getRowCountTrue()<=0){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.removeEmptyRows();
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        
        if(!isPerIngDocCon()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se ha excedido el número máximo de egresos de un item con fecha actual.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        if(isCosUniCero()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existen costos unitarios en cero.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false; 
        }
        if(isDifCosIngEgrCero()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El ingreso de documento genera descuadre.<BR>Verifique la Configuración de conversión de items vuelva a intentarlo.</HTML>");
            return false; 
        }
        
        
        
        objTblMod.removeEmptyRows();
        
        return true;
    }

    /**
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
                    if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_GRP, j, INT_TBL_DAT_COD_GRP)){
                        if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ITM_MAE, j, INT_TBL_DAT_COD_ITM_MAE)){
                            blnRes=true;
                            break;
                        }
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
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
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
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
////            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conCab=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (txtCodTipDoc.getText().equals("")){
////                    strSQL="";
////                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
////                    strSQL+=", a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
////                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
////                    strSQL+=" FROM (          (      (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
////                    strSQL+=" 			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
////                    strSQL+=" 			)";
////                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
////                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
////                    strSQL+="   )";
////                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                    if(objParSis.getCodigoUsuario()==1){
////                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////                    }
////                    else{
////                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.co_usr=" + objParSis.getCodigoUsuario() + "";
////                    }
////
////                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
////                    strAux=txtCodBod.getText();
////                    if (!strAux.equals(""))
////                        strSQL+=" AND a6.co_bod= " + strAux + "";
////                }
////                else{
////                    strSQL="";
////                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipD    oc, a1.co_doc, a1.fe_doc";
////                    strSQL+=", a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
////                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
////                    strSQL+=" FROM (          (      (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
////                    strSQL+=" 			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
////                    strSQL+=" 			)";
////                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
////                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
////                    strSQL+="   )";
////                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                    strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////
////                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
////                    strAux=txtCodBod.getText();
////                    if (!strAux.equals(""))
////                        strSQL+=" AND a6.co_bod= " + strAux + "";
////
////                }
////                strAux=txtCodTipDoc.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
////                
////                if (dtpFecDoc.isFecha())
////                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
////                strAux=txtNumDoc.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
////                
////                strAux=txtCodDoc.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";
////
////                strSQL+=" AND a1.st_reg<>'E'";
////                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
////                strSQL+=" , a1.co_usrIng, a15.tx_usr, a1.co_usrMod, a16.tx_usr, a1.co_mnu, a6.co_bod";
////                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.TX_usrIng ";
                    strSQL+=",  a1.TX_usrMod/*, a16.tx_usr AS tx_nomUsrMod*/, a1.co_mnu, a6.TX_bod";
                    strSQL+=" FROM tbm_cabMovInv as a1";
                    strSQL+=" 	inner join tbm_detMovInv as a6 on (a1.co_emp=a6.co_emp and a1.co_tipdoc = a6.co_tipdoc and a1.co_doc = a6.co_doc)";
                    strSQL+=" 	inner join tbm_cabtipdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_tipdoc = a2.co_tipdoc)";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    }
                    else{
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.tx_usr=" + objParSis.getCodigoUsuario() + "";
                    }

                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.tx_bod= " + strAux + "";
                    if (!txtCodDoc.getText().equals(""))
                        strSQL+=" AND a1.co_doc= " + txtCodDoc.getText() + "";
                }
                else{
                    
                    
                    
////                     strSQL="";
////                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.TX_usrIng ";
////                    strSQL+=",  a1.TX_usrMod/*, a16.tx_usr AS tx_nomUsrMod*/, a1.co_mnu, a6.TX_bod";
////                    strSQL+=" FROM tbm_cabMovInv as a1";
////                    strSQL+=" 	inner join tbm_detMovInv as a6 on (a1.co_emp=a6.co_emp and a1.co_tipdoc = a6.co_tipdoc and a1.co_doc = a6.co_doc)";
////                    strSQL+=" 	inner join tbm_cabtipdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_tipdoc = a2.co_tipdoc)";
////                    if(objParSis.getCodigoUsuario()==1){
////                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////                    }
////                    else{
////                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.tx_usr=" + objParSis.getCodigoUsuario() + "";
////                    }
////
////                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
////                    strAux=txtCodBod.getText();
////                    if (!strAux.equals(""))
////                        strSQL+=" AND a6.tx_bod= " + strAux + "";
                    
////                    
////                    strSQL="";
////                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipD    oc, a1.co_doc, a1.fe_doc";
////                    strSQL+=", a1.tx_usrIng, a15.tx_usr AS tx_nomUsrIng";
////                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
////                    strSQL+=" FROM (          (      (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
////                    strSQL+=" 			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
////                    strSQL+=" 			)";
////                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.tx_usrIng=a15.tx_usr)";
////                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.tx_usrMod=a16.tx_usr";
////                    strSQL+="   )";
////                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                    strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////
////                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
////                    strAux=txtCodBod.getText();
////                    if (!strAux.equals(""))
////                        strSQL+=" AND a6.tx_bod= " + strAux + "";
strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.TX_usrIng ";
                    strSQL+=",  a1.TX_usrMod/*, a16.tx_usr AS tx_nomUsrMod*/, a1.co_mnu, a6.TX_bod,a1.co_idCabGP";
                    strSQL+=" FROM tbm_cabMovInv as a1";
                    strSQL+=" 	inner join tbm_detMovInv as a6 on (a1.co_emp=a6.co_emp and a1.co_tipdoc = a6.co_tipdoc and a1.co_doc = a6.co_doc)";
                    strSQL+=" 	inner join tbm_cabtipdoc as a2 on (a1.co_emp = a2.co_emp and a1.co_tipdoc = a2.co_tipdoc)";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    }
                    else{
                            strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a1.co_emp=a2.co_emp AND a1.co_tipDoc=a2.co_tipDoc)";
                            strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
////                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
////                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.tx_usr=" + objParSis.getCodigoUsuario() + "";
                    }

                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.tx_bod= '" + strAux + "'";


                }
                
                
                
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_idCabGP = '" + strAux + "'";
                
                
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc , a1.TX_usrIng";
                strSQL+=" , a1.TX_usrMod/*, a16.tx_usr*/, a1.co_mnu, a6.TX_bod,a1.co_idCabGP";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                
                
                
                System.out.println("consultarReg:  " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                arlDatConDoc = new ArrayList();
                while(rstCab.next()){
                    arlRegConDoc = new ArrayList();
                    arlRegConDoc.add(INT_ARL_CON_COD_EMP,       rstCab.getInt("co_emp"));
                    arlRegConDoc.add(INT_ARL_CON_COD_LOC,       rstCab.getInt("co_loc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_TIP_DOC,   rstCab.getString("co_tipDoc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_DOC,       rstCab.getString("co_doc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_USR_ING,   rstCab.getString("tx_usrIng"));
                    arlRegConDoc.add(INT_ARL_CON_COD_USR_MOD,   rstCab.getString("tx_usrMod"));
                    arlDatConDoc.add(arlRegConDoc);
                }
                stmCab.close();
                stmCab=null;
                rstCab.close();
                rstCab=null;
                conCab.close();
                conCab=null;
                
                if(arlDatConDoc.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConDoc.size()) + " registros");
                    intIndReg=arlDatConDoc.size()-1;
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

    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if(cargarCabReg()){
                if(cargarDetReg()){
////                    if(objAsiDia.consultarDiario( objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP)
////                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC)
////                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC)
////                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC))){
////                    }
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
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stm=con.createStatement();
////                strSQL="";
////                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a2.tx_natDoc";
////                strSQL+=" , a1.nd_tot, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a6.co_bod, a7.tx_nom AS tx_nomBod";
////                strSQL+=" FROM (  tbm_cabMovInv AS a1";
////                strSQL+="           INNER JOIN (tbm_detMovInv AS a6";
////                strSQL+=" 	               INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
////                strSQL+="                       INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
////                strSQL+=" 	    )";
////                strSQL+="  	    ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
////                strSQL+="   )";
////                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                strSQL+="  WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP);
////                strSQL+="  AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC);
////                strSQL+="  AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC);
////                strSQL+="  AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC);
////                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a2.tx_natDoc";
////                strSQL+=" , a1.nd_tot, a1.tx_obs1";
////                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a6.co_bod, a7.tx_nom";
////                
                
              strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,RTRIM(a1.co_idCabGP) as co_idCabGP, a2.tx_natDoc , a1.nd_tot, a1.tx_obs1";
                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a6.tx_bod , a6.tx_bod as tx_nomBod";
                strSQL+=" FROM (tbm_cabMovInv AS a1";
                strSQL+="           INNER JOIN (tbm_detMovInv AS a6";
                strSQL+=" 	               INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  AS A7 ON (a6.tx_codalt = a7.ITEMNMBR) )";
                strSQL+="  	    ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+="   )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+="  WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP);
                strSQL+="  AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC);
                strSQL+="  AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="  AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC);
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.co_idCabGP, a2.tx_natDoc, a1.nd_tot, a1.tx_obs1 ";
                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a6.tx_bod";
                    
                    
                    
                
                
                System.out.println("cargarCabReg: " + strSQL);
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
                    strAux=rst.getString("tx_bod");//código de bodega de la empresa
                    txtCodBod.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBod");//nombre de bodega de la empresa
                    txtNomBod.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    datFecDoc=rst.getDate("fe_doc");
                    strAux=rst.getString("co_idCabGP");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    strAux=rst.getString("co_idCabGP");
                    //this.setTitle(strAux + strVersion );
                    lblTit.setText(objParSis.getNombreMenu() + strVersion +"  "+ ((strAux==null)?"":strAux ));                   
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
            intPosRel = intIndReg+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConDoc.size()) );
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
////                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.ne_numFil, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2";
                    strSQL+=" , a2.tx_nomItm, a2.tx_uniMed, a2.tx_bod, a2.tx_nomBodOrgDes, a2.nd_can, a2.nd_cosUni, a2.nd_tot, a2.nd_cosTot";
                    strSQL+=" FROM tbm_detMovInv AS a2";
                    strSQL+=" WHERE a2.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a2.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a2.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC) + "";
                    strSQL+=" AND a2.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" ORDER BY a2.ne_numFil, a2.co_reg";
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();

                //segundo query fin
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,    rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_EMP,    rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,        rst.getString("tx_uniMed"));                        
                        vecReg.add(INT_TBL_DAT_COD_GRP,        "");
                        vecReg.add(INT_TBL_DAT_DES_COR_GRP,    "");
                        vecReg.add(INT_TBL_DAT_DES_LAR_GRP,    "");                        
                        bdeCanEgrIng=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                        
                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR,   "0");
                            vecReg.add(INT_TBL_DAT_CAN_ING,   bdeCanEgrIng);
                        }
                        else{//Egreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR,   bdeCanEgrIng.abs());
                            vecReg.add(INT_TBL_DAT_CAN_ING,   "0");
                        }                        
                        vecReg.add(INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_COS_TOT,        rst.getString("nd_cosTot"));
                        vecReg.add(INT_TBL_DAT_POR_CAL_COS_UNI,"");
                        vecReg.add(INT_TBL_DAT_TIP_REL,        "");
                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   "0");
                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   bdeCanEgrIng);
                        }
                        else{//Egreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   bdeCanEgrIng);
                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   "0");
                        }
                        vecReg.add(INT_TBL_DAT_NUM_FIL_BLK,    rst.getString("ne_numFil"));
                        vecDat.add(vecReg);

                    }
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    vecDat.clear();
                }
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
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado){
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
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
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()//chiqui
    {
        boolean blnRes=true;
        try
        {
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
                
               
////                strSQL="";
////                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
////                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
////                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
////                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
////                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
////                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
////                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
////                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
////                strSQL+=" ORDER BY a1.tx_desCor";

                    strSQL="";
                    strSQL+=" SELECT c.co_tipDoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_desLar) as tx_desLar, ";
                    strSQL+="          isnull((select TOP 1 co_grp from Dynamics2021.dbo.tbm_cabCfgConInv where st_reg ='A'  order by co_grp desc),0) as ne_ultDoc,";
                    strSQL+="        g.tx_natDoc, CASE WHEN g.st_necautanudoc IS NULL THEN '' ELSE g.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
                     
            }
            else{
                    strSQL="";
                    strSQL+=" SELECT c.co_tipDoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_desLar) as tx_desLar, ";
                    strSQL+="          isnull((select TOP 1 co_grp from Dynamics2021.dbo.tbm_cabCfgConInv where st_reg ='A'  order by co_grp desc),0) as ne_ultDoc,";
                    strSQL+="        g.tx_natDoc, CASE WHEN g.st_necautanudoc IS NULL THEN '' ELSE g.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
    private boolean mostrarVenConTipDoc(int intTipBus)//se cambio el numdoc x el txtCodDoc
    {
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
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
                        
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
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
                                txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtCodDoc.selectAll();
                            txtCodDoc.requestFocus();
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
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
                                txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtCodDoc.selectAll();
                            txtCodDoc.requestFocus();
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Bodegas".
     */
    private boolean configurarVenConBod() //chiqui
    {
        boolean blnRes = true;
        try {
            
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                
                strSQL="";
                strSQL+=" SELECT RTRIM(b.tx_descor) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_bod as b  \n";
                strSQL+="   INNER JOIN Dynamics2021.dbo.tbr_bodlocprgusr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
                strSQL+="   WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
                strSQL+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                strSQL+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
                strSQL+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
                if(txtCodBod.getText().length()>0)
                    strSQL+=" AND  b.tx_descor !='"+txtCodBod.getText() +"'";

////               strSQL="";
////               strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
            }
            else{
                    
                strSQL="";
                strSQL+=" SELECT RTRIM(b.tx_descor) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_bod as b  \n";
                strSQL+="   INNER JOIN Dynamics2021.dbo.tbr_bodlocprgusr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
                strSQL+="   WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
                strSQL+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                strSQL+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
                strSQL+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
                if(txtCodBod.getText().length()>0)
                    strSQL+=" AND  b.tx_descor !='"+txtCodBod.getText() +"'";

////               strSQL="";
////               strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
            }


            vcoBod = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
        }
        catch (Exception e){
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
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try{
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoBod.setCampoBusqueda(2);
                    vcoBod.show();
                    if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".

                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    } else {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            txtNomBod.setText(strNomBod);
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
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm){
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConDoc.size()>0){
                    if(intIndReg>0){
                        if((blnHayCam)){
                            if(isRegPro()) {
                                intIndReg=0;
                                cargarReg();
                            }
                        }
                        else{
                            intIndReg=0;
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
                if(arlDatConDoc.size()>0){
                    if(intIndReg>0){
                        if ((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg--;
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
                 if(arlDatConDoc.size()>0){
                    if(intIndReg < arlDatConDoc.size()-1){
                        if ((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg++;
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
                 if(arlDatConDoc.size()>0){
                    if(intIndReg<arlDatConDoc.size()-1){
                        if((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg=arlDatConDoc.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg=arlDatConDoc.size()-1;
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
                if ((blnHayCam)){
                    isRegPro();
                }
                
            if(!isVersionActualizada()){
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }
                            

                //para insertar no se los puede cambiar
                txtCodTipDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtValDoc.setEditable(false);
                
                limpiarFrm();
                mostrarTipDocPre();
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                
//                objTblMod.setModoOperacion(objTooBar.getEstado());
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar(){
            txtDesCorTipDoc.setEditable(true);
            txtDesLarTipDoc.setEditable(true);
            butTipDoc.setEnabled(true);
            txtCodBod.setEditable(true);
            txtNomBod.setEditable(true);
            butBod.setEnabled(true);
            txtCodDoc.setEditable(true);
            dtpFecDoc.setEnabled(true);
            txtNumDoc.setEditable(true);
            txtValDoc.setEditable(false);
        }

        public void clickModificar(){
        }

        public void clickEliminar(){
        }

        public void clickAnular(){
        }

        public void clickImprimir(){
        }

        public void clickVisPreliminar(){
        }

        public void clickAceptar(){
        }

        public void clickCancelar(){
        }

        public boolean insertar(){
            if (!insertarReg())
            {
                return false;
            }
            else
            {
                obtenerCodGP();
                
                consultar();
                return true;
            }
                
            
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar(){
            return true;
        }

        public boolean eliminar(){
            return true;
        }

        public boolean anular(){
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            strAux=objParSis.getNombreMenu();
            lblTit.setText(strAux + strVersion);
            try{
                if((blnHayCam)){
                    if(objTooBar.getEstado()=='n'){
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
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
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
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            btn_InsertarGP.setEnabled(true);
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
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


    
    private boolean cargarItemIngreso(String codigoGrupo, String costoUnitarioEgreso){
        boolean blnRes=true;
        Connection conItmIng;
        Statement stmItmIng;
        ResultSet rstItmIng;
        int intFilAdd=-1;
        BigDecimal bdeCosUniEgr=new BigDecimal(costoUnitarioEgreso==null?"0":(costoUnitarioEgreso.equals("")?"0":costoUnitarioEgreso)).abs();
        try{
////            conItmIng=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conItmIng=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conItmIng!=null){
                stmItmIng=conItmIng.createStatement();
                intFilAdd=(objTblMod.getRowCountTrue());
////                strSQL="";
////                strSQL+=" SELECT c1.nd_cosUniEgr, c1.co_emp, c1.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_desCor \n";
////                strSQL+=" , c1.co_grp, c1.co_reg, c1.tx_desCorGrp, c1.tx_desLarGrp, c1.nd_can, c1.tx_tipRel, c1.nd_porCalCos, c1.co_itmEmp \n";
////                
////                if(objParSis.getCodigoMenu()==4202){//Egreso
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE ((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can) END AS nd_cosUniIng \n";
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can) END AS nd_cosTotIng \n";
////                }   
////                else{//Ingreso
//////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can))*(-1) END AS nd_cosUniIng";
//////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE ((((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can))*(-1) END AS nd_cosTotIng";
////
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEmp IS NULL OR c1.nd_cosUniEmp=0) THEN 0 ELSE (((c1.nd_cosUniEmp /* * c1.nd_porCalCos */ )/c1.nd_can))*(-1) END AS nd_cosUniIng \n";
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEmp IS NULL OR c1.nd_cosUniEmp=0) THEN 0 ELSE ((((c1.nd_cosUniEmp /* * c1.nd_porCalCos */ )/c1.nd_can)*c1.nd_can))*(-1) END AS nd_cosTotIng \n";
////
////
////                }
////                //strSQL+=" , c1.nd_cosUniEmp";
////                
////                strSQL+=" FROM( \n";
////                strSQL+="       SELECT " + bdeCosUniEgr + " AS nd_cosUniEgr, b1.co_emp, b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_desCor \n";
////                strSQL+="       , b2.co_grp, b2.co_reg, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel, b2.nd_porCalCos, b3.co_itmEmp \n";
////                strSQL+="       , b3.nd_cosUniEmp \n";
////                strSQL+="       FROM( \n";
////                strSQL+="           SELECT a1.co_emp, a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor \n";
////                strSQL+="           FROM tbm_inv AS a1 \n";
////                strSQL+="           INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) \n";
////                strSQL+="           LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg) \n";
////                strSQL+="           WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + " \n";
////                strSQL+="           AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' ) \n";
////                strSQL+="           ORDER BY a1.tx_codAlt \n";
////                strSQL+="       ) AS b1 \n";
////                strSQL+="       INNER JOIN( \n";
////                strSQL+="           SELECT a1.co_emp, a1.co_grp, a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a1.st_reg \n";
////                strSQL+="           , a2.co_reg, a2.co_itm, a3.co_itmMae, a2.nd_can, a2.tx_tipRel, a2.st_reg, a2.nd_porCalCos \n";
////                strSQL+="           FROM tbm_cabCfgConInv AS a1 INNER JOIN tbm_detCfgConInv AS a2 \n";
////                strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp \n";
////                strSQL+="           INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm \n";
////                
////                if(objParSis.getCodigoMenu()==4202)
////                    strSQL+="           WHERE a1.tx_tipCfg='E' AND a2.tx_tipRel='I'  \n";
////                else
////                    strSQL+="           WHERE a1.tx_tipCfg='I' AND a2.tx_tipRel='E'  \n";
////                
////                strSQL+="           AND a1.st_reg='A' AND a2.st_reg='A' \n";
////                strSQL+="       ) AS b2 \n";
////                strSQL+="       ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae \n";
////                strSQL+="       INNER JOIN( \n";
////                strSQL+="           SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a1.nd_cosUni AS nd_cosUniEmp \n";
////                strSQL+="           FROM tbm_inv AS a1 \n";
////                strSQL+="           INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm) \n";
////                strSQL+="           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " \n";
////                strSQL+="           AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' ) \n";
////                strSQL+="           ORDER BY a1.tx_codAlt \n";
////                strSQL+="       ) AS b3 \n";
////                strSQL+="       ON b1.co_itmMae=b3.co_itmMae \n";
////                strSQL+="       ORDER BY b2.co_reg \n";
////                strSQL+=" ) AS c1 \n";
////                strSQL+=" WHERE c1.co_grp=" + codigoGrupo + " \n";
////                strSQL+=" ORDER BY c1.co_reg \n";



strSQL="";
                strSQL+=" SELECT c1.nd_cosUniEgr, c1.co_emp,c1.tx_codAlt as co_itmMae,c1.tx_codAlt as co_itm,c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_unimed   \n";
                strSQL+=" ,  c1.co_grp, c1.co_reg, c1.tx_desCorGrp, c1.tx_desLarGrp, c1.nd_can, c1.tx_tipRel, c1.nd_porCalCos ,c1.tx_codAlt as co_itmEmp\n";
                
                if(objParSis.getCodigoMenu()==4202){//Egreso
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE ((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can) END AS nd_cosUniIng \n";
////                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can) END AS nd_cosTotIng \n";

                    strSQL+=" , CASE   WHEN ISNULL(c1.nd_cosUniEgr, 0) = 0   OR ISNULL(c1.nd_can, 0) = 0    THEN 0    ELSE ((c1.nd_cosUniEgr * c1.nd_porCalCos) / NULLIF(c1.nd_can, 0))   END AS nd_cosUniIng \n";
                    strSQL+=" , CASE   WHEN ISNULL(c1.nd_cosUniEgr, 0) = 0   OR ISNULL(c1.nd_can, 0) = 0   THEN 0  ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos) / NULLIF(c1.nd_can, 0)) * c1.nd_can)  END AS nd_cosTotIng \n";
                }   
                else{//Ingreso
//                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can))*(-1) END AS nd_cosUniIng";
//                    strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE ((((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can))*(-1) END AS nd_cosTotIng";

                    strSQL+=" , CASE WHEN (c1.nd_cosUniEmp IS NULL OR c1.nd_cosUniEmp=0) THEN 0 ELSE (((c1.nd_cosUniEmp /* * c1.nd_porCalCos */ )/c1.nd_can))*(-1) END AS nd_cosUniIng \n";
                    strSQL+=" , CASE WHEN (c1.nd_cosUniEmp IS NULL OR c1.nd_cosUniEmp=0) THEN 0 ELSE ((((c1.nd_cosUniEmp /* * c1.nd_porCalCos */ )/c1.nd_can)*c1.nd_can))*(-1) END AS nd_cosTotIng \n";


                }
                //strSQL+=" , c1.nd_cosUniEmp";
                
                strSQL+=" FROM( \n";
                strSQL+="       SELECT " + bdeCosUniEgr + " AS nd_cosUniEgr,  b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_unimed \n";
                strSQL+="       , b2.co_grp, b2.co_reg, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel, b2.nd_porCalCos,b2.co_emp,b1.nd_cosUniEmp \n";
                strSQL+="       FROM( \n";
                strSQL+="            SELECT ITEMNMBR AS co_itmGrp,RTRIM(ITEMNMBR) as tx_codAlt,  RTRIM (ITMSHNAM) AS tx_codAlt2 , RTRIM(ITEMDESC) as tx_nomItm, RTRIM(ITMSHNAM) as tx_desCor , CURRCOST AS nd_cosUniGrp,PRCHSUOM as tx_unimed,CURRCOST as nd_cosUniEmp \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  \n";
                strSQL+="           WHERE (ITEMNMBR like '%I' OR ITEMNMBR like '%S' ) \n";
                strSQL+="           ) AS b1 \n";
                strSQL+="       INNER JOIN( \n";
                strSQL+="           SELECT a1.co_emp, a1.co_grp,  a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a2.co_reg, a2.co_itm, a2.nd_can,    \n";
                strSQL+="           a2.tx_tipRel,  a2.nd_porCalCos \n";
                strSQL+="           FROM tbm_cabCfgConInv AS a1    \n";
                strSQL+="           INNER JOIN tbm_detCfgConInv AS a2       ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp   \n";
                
                if(objParSis.getCodigoMenu()==4202)
                    strSQL+="           WHERE a1.tx_tipCfg='E' AND a2.tx_tipRel='I'  \n";
                else
                    strSQL+="           WHERE a1.tx_tipCfg='I' AND a2.tx_tipRel='E'  \n";
                
                strSQL+="           AND a1.st_reg='A' AND a2.st_reg='A' \n";
                strSQL+="       ) AS b2 \n";
                strSQL+="       ON b1.co_itmGrp=b2.co_itm  \n";
////                strSQL+="       ORDER BY b2.co_reg \n";
                strSQL+=" ) AS c1 \n";
                strSQL+=" WHERE c1.co_grp=" + codigoGrupo + " \n";
                strSQL+=" ORDER BY c1.co_reg \n";
                
                
                System.out.println("INGRESO/EGRESO:  \n" + strSQL);
                rstItmIng=stmItmIng.executeQuery(strSQL);
                while(rstItmIng.next()){
                    //intFilAdd++;
                    objTblMod.insertRow(intFilAdd);
                    objTblMod.setValueAt(rstItmIng.getString("co_itmMae"), intFilAdd, INT_TBL_DAT_COD_ITM_MAE);
                    objTblMod.setValueAt(rstItmIng.getString("co_itm"), intFilAdd, INT_TBL_DAT_COD_ITM_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("co_itmEmp"), intFilAdd, INT_TBL_DAT_COD_ITM_EMP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_codAlt"), intFilAdd, INT_TBL_DAT_COD_ALT_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_codAlt2"), intFilAdd, INT_TBL_DAT_COD_LET_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_nomItm"), intFilAdd, INT_TBL_DAT_NOM_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_unimed"), intFilAdd, INT_TBL_DAT_UNI_MED);
                    objTblMod.setValueAt(rstItmIng.getString("co_grp"), intFilAdd, INT_TBL_DAT_COD_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_desCorGrp"), intFilAdd, INT_TBL_DAT_DES_COR_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_desLarGrp"), intFilAdd, INT_TBL_DAT_DES_LAR_GRP);
                    
                    if(objParSis.getCodigoMenu()==4202){//Egreso
                        objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_ING);
                        objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_ING_AUX);
                    }
                    else{//Ingreso
                        objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_EGR);
                        objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_EGR_AUX);
                    }
                    
                    objTblMod.setValueAt(rstItmIng.getString("tx_tipRel"), intFilAdd, INT_TBL_DAT_TIP_REL);
                    objTblMod.setValueAt(rstItmIng.getString("nd_porCalCos"), intFilAdd, INT_TBL_DAT_POR_CAL_COS_UNI);
                    objTblMod.setValueAt(objUti.redondearBigDecimal(rstItmIng.getString("nd_cosUniIng"), objParSis.getDecimalesBaseDatos()), intFilAdd, INT_TBL_DAT_COS_UNI);
                    objTblMod.setValueAt(objUti.redondearBigDecimal(rstItmIng.getString("nd_cosTotIng"), objParSis.getDecimalesBaseDatos()), intFilAdd, INT_TBL_DAT_COS_TOT);
                    objTblMod.setValueAt(intNumFilEgr, intFilAdd, INT_TBL_DAT_NUM_FIL_BLK);
                }
                rstItmIng.close();
                rstItmIng=null;
                stmItmIng.close();
                stmItmIng=null;
                conItmIng.close();
                conItmIng=null;
            }
            
            if(objParSis.getCodigoMenu()!=4202){//Ingreso
                calculaCosCnvIng();
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
    
    private boolean calculaCosCnvIng(){
        boolean blnRes=true;
        String strTipRelCnvIng="";
        BigDecimal bdeCosUniCnvIng=BigDecimal.ZERO;
        BigDecimal bdeSumCosUniCnvIng=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelCnvIng=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelCnvIng.equals("E")){
                    bdeCosUniCnvIng=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                    bdeSumCosUniCnvIng=bdeSumCosUniCnvIng.add(bdeCosUniCnvIng);
                }
            }
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelCnvIng=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelCnvIng.equals("I")){
                    objTblMod.setValueAt(bdeSumCosUniCnvIng.abs(), i, INT_TBL_DAT_COS_UNI);
                    break;
                }
            }            
            bdeCosUniCnvIng=BigDecimal.ZERO;
            bdeSumCosUniCnvIng=BigDecimal.ZERO;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    
  
    /**
     * Función que permite generar el asiento de diario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean generaDiarioAjuste(){
        boolean blnRes=true;
        
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;

        BigDecimal bdeValDoc=new BigDecimal(txtValDoc.getText().equals("")?"0":txtValDoc.getText());

        Connection conGenDiaAju;
        Statement stmGenDiaAju;
        ResultSet rstGenDiaAju;
        
        try{
            ///para generar el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
////            conGenDiaAju=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGenDiaAju=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conGenDiaAju!=null){
                stmGenDiaAju=conGenDiaAju.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_cfg, b1.tx_nom, b1.co_loc, b1.co_tipdoc, b1.tx_tipMov, b1.tx_obs1, b1.co_mnu";
                strSQL+=" , b1.co_ctaDeb, b1.tx_codCtaDeb, b1.tx_desLarDeb, b1.st_reg, b2.co_ctaHab, b2.tx_codCtaHab, b2.tx_desLarHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cfg, a1.tx_nom, a1.co_loc, a1.co_tipdoc, a1.tx_tipMov, a1.tx_obs1, a1.co_mnu";
                strSQL+=" 	, a1.co_ctaDeb, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_desLarDeb, a1.st_reg";
                strSQL+=" 	FROM tbm_cfgTipDocCtaPrg AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";                
                strSQL+="       ORDER BY a1.co_cfg";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cfg, a1.tx_nom, a1.co_loc, a1.co_tipdoc, a1.tx_tipMov, a1.tx_obs1, a1.co_mnu";
                strSQL+=" 	, a1.co_ctaHab, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_desLarHab, a1.st_reg";
                strSQL+=" 	FROM tbm_cfgTipDocCtaPrg AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+="       ORDER BY a1.co_cfg";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cfg=b2.co_cfg";
                System.out.println("generaDiarioAjuste: " + strSQL);
                rstGenDiaAju=stmGenDiaAju.executeQuery(strSQL);
                while(rstGenDiaAju.next()){
                    //Debe
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA,  rstGenDiaAju.getInt("co_ctaDeb"));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA,  rstGenDiaAju.getString("tx_codCtaDeb"));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA,  "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,  rstGenDiaAju.getString("tx_desLarDeb"));
                    vecRegDia.add(INT_VEC_DIA_DEB,      bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_HAB,      0);
                    vecRegDia.add(INT_VEC_DIA_REF,      "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON,  "");
                    vecDatDia.add(vecRegDia);
                    //Haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA,  rstGenDiaAju.getInt("co_ctaHab"));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA,  rstGenDiaAju.getString("tx_codCtaHab"));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA,  "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,  rstGenDiaAju.getString("tx_desLarHab"));
                    vecRegDia.add(INT_VEC_DIA_DEB,      0);
                    vecRegDia.add(INT_VEC_DIA_HAB,      bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_REF,      "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON,  "");
                    vecDatDia.add(vecRegDia);
                }
                
                conGenDiaAju.close();
                conGenDiaAju=null;
                stmGenDiaAju.close();
                stmGenDiaAju=null;
                rstGenDiaAju.close();
                rstGenDiaAju=null;
            }
            System.out.println("vecDatDia: " + vecDatDia.toString());
            objAsiDia.setDetalleDiario(vecDatDia);
//                System.out.println("getDetalleDiario: " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

   
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()//se cambio el numdoc x el txtCodDoc
    {
        strAux="";
        strAux+=" Ajuste: " + txtDesCorTipDoc.getText();
        strAux+=" # " + txtCodDoc.getText();
        objAsiDia.setGlosa(strAux);
    }
    
    
    private boolean calcularCostoTotalEgreso(int filaEgreso, BigDecimal bdeCanEgrSig){
        boolean blnRes=true;
        int intFilSelEgr=filaEgreso;
        BigDecimal bdeCosUniEgr=BigDecimal.ZERO;
        BigDecimal bdeCanEgr=BigDecimal.ZERO;
        BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
        
        try{
//            bdeCanEgr=new BigDecimal(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR).toString()));
            bdeCanEgr=bdeCanEgrSig;
            
            bdeCosUniEgr=new BigDecimal(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI).toString()));
            bdeCosTotEgr=objUti.redondearBigDecimal((bdeCanEgr.multiply(bdeCosUniEgr)), objParSis.getDecimalesBaseDatos());
            System.out.println("bdeCosTotEgr:" + bdeCosTotEgr);
            System.out.println("intFilSelEgr:" + intFilSelEgr);
            objTblMod.setValueAt(bdeCosTotEgr, intFilSelEgr, INT_TBL_DAT_COS_TOT);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean calcularCostoTotalIngreso(int filaIngreso){
        boolean blnRes=true;
        int intFilSelIng=filaIngreso;
        BigDecimal bdeCosUniIng=BigDecimal.ZERO;
        BigDecimal bdeCanIng=BigDecimal.ZERO;
        BigDecimal bdeCosTotIng=BigDecimal.ZERO;
        
        try{
            bdeCanIng=new BigDecimal(objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_CAN_ING)==null?"0":(objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_CAN_ING).toString()));
            bdeCosUniIng=new BigDecimal(objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intFilSelIng, INT_TBL_DAT_COS_UNI).toString()));
            bdeCosTotIng=objUti.redondearBigDecimal((bdeCanIng.multiply(bdeCosUniIng)), objParSis.getDecimalesBaseDatos());
            System.out.println("bdeCosTotIng:" + bdeCosTotIng);
            System.out.println("intFilSelIng:" + intFilSelIng);
            objTblMod.setValueAt(bdeCosTotIng, intFilSelIng, INT_TBL_DAT_COS_TOT);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    private boolean calcularCostosIngresos(int intFil, BigDecimal bdeCanEgrSig){
        boolean blnRes=true;
        String strTipRelIng="";
        int intNumFilItmEgr=-1;
        int intNumFilItmIng=-1;
        BigDecimal bdeCanIngAnt=BigDecimal.ZERO;
        BigDecimal bdeCanIngAct=BigDecimal.ZERO;
        BigDecimal bdeCanIngNue=BigDecimal.ZERO;
        BigDecimal bdeCosUniIng=BigDecimal.ZERO;
        BigDecimal bdeCosTotIng=BigDecimal.ZERO;
        String strTipRelEgr="";
        try{
            System.out.println("se llama: " + intNumFilItmEgr);
            intNumFilItmEgr=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString()));
            strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intNumFilItmIng=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString()));
                if(intNumFilItmEgr==intNumFilItmIng){
                    strTipRelIng=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                    if(strTipRelIng.equals("I")){
                        bdeCanIngAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX).toString()));
                        bdeCosUniIng=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                        bdeCanIngNue=bdeCanIngAct.multiply(bdeCanEgrSig);
                        objTblMod.setValueAt(bdeCanIngNue, i, INT_TBL_DAT_CAN_ING);

                        bdeCosTotIng=bdeCanIngNue.multiply(bdeCosUniIng);

                        objTblMod.setValueAt(bdeCosTotIng, i, INT_TBL_DAT_COS_TOT);
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
    
    
    private boolean calcularCostosEgresos(int intFil, BigDecimal bdeCanIngSig){
        boolean blnRes=true;
        String strTipRelIng="", strTipRelEgr="";
        int intNumFilItmEgr=-1, intNumFilItmIng=-1;
        BigDecimal bdeCanEgrAnt=BigDecimal.ZERO;
        BigDecimal bdeCanEgrAct=BigDecimal.ZERO;
        BigDecimal bdeCanEgrNue=BigDecimal.ZERO;
        BigDecimal bdeCosUniEgr=BigDecimal.ZERO;
        BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
        try{
            intNumFilItmIng=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString()));
            strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intNumFilItmEgr=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString()));
                if(intNumFilItmIng==intNumFilItmEgr){
                    strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                    if(strTipRelEgr.equals("E")){
                        bdeCanEgrAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR_AUX).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR_AUX).toString()));
                        bdeCosUniEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                        bdeCanEgrNue=bdeCanEgrAct.multiply(bdeCanIngSig);
                        objTblMod.setValueAt(bdeCanEgrNue, i, INT_TBL_DAT_CAN_EGR);

                        bdeCosTotEgr=bdeCanEgrNue.multiply(bdeCosUniEgr);

                        objTblMod.setValueAt(bdeCosTotEgr, i, INT_TBL_DAT_COS_TOT);
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
    
    
//    no esta calculando el acumulado de costos totales de egresos y colocarlo en valor de dcoumento.
    
private boolean setValorDoc(){
        boolean blnRes=true;
        String strTipRelEgr="";
        BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
        BigDecimal bdeCosTotEgrAcu=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelEgr.equals("E")){
                    bdeCosTotEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()));
                    bdeCosTotEgrAcu=bdeCosTotEgrAcu.add(bdeCosTotEgr);
                }
            }
            bdeCosTotEgrAcu=objUti.redondearBigDecimal(bdeCosTotEgrAcu, objParSis.getDecimalesMostrar());
            txtValDoc.setText(bdeCosTotEgrAcu.abs().toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    

    /**
     * Función que permite eliminar el grupo de conversión asociado al item seleccionado para eliminar
     * @param filaEli Fila seleccionada
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean eliminaGrpCnv(int filaEli){
        boolean blnRes=true;
        int intNumFilGrp=Integer.parseInt(objTblMod.getValueAt(filaEli, INT_TBL_DAT_NUM_FIL_BLK).toString());
        int intNumFil=-1;
        int j=0;
        try{
            System.out.println("intNumFilGrp: " + intNumFilGrp);
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                System.out.println("i: " + i);
                intNumFil=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString());
                if(intNumFilGrp==intNumFil){
                    tblDat.getSelectionModel().addSelectionInterval (i, i);
                    System.out.println("dentro - i: " + i);
                }
            }
            
            
            
            
//            System.out.println("intNumFilGrp: " + intNumFilGrp);
//            for(int i=(objTblMod.getRowCountTrue()-2); i>=0; i--){
//                System.out.println("i: " + i);
//                intNumFil=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString());
//                if(intNumFilGrp==intNumFil){
//                    objTblMod.removeRow(i);
//                }
//            }
//            objTblMod.removeEmptyRows();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    //validar que no exista la descripcion corta ya en la DB
    
    /**
     * Función que permite ingresar máximo 3 documentos de conversión asociados al item
     * @return true Si se pudo realizar la operación - Es menor o igual a 3
     * <BR> false Caso contrario
     */
    private boolean isPerIngDocCon(){
        boolean blnRes=false;
        String strTipRelEgr="";
        String strCodItmMae="";
        int j=0;
        Connection conPerIng;
        Statement stmPerIng;
        ResultSet rstPerIng;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelEgr.equals("E")){
                    if(j==0){
                        strCodItmMae=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString() + "";
                    }
                    else
                        strCodItmMae+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString() + "";
                    j++;
                }
            }
////            conPerIng=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conPerIng=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            
            if(conPerIng!=null){
                stmPerIng=conPerIng.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_itmMae, a1.ne_numItm";
                strSQL+=" FROM(";
                strSQL+="   SELECT a1.co_emp, a1.co_loc, a2.co_itm as co_itmMae, COUNT(a2.co_itm) AS ne_numItm   ";
                strSQL+="   FROM tbm_cabMovInv AS a1 ";
                strSQL+="   INNER JOIN tbm_detMovInv AS a2   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="   AND a1.fe_doc= CAST(GETDATE() AS DATE)";
                strSQL+="   AND a1.st_reg='A'";
                strSQL+="   AND a2.co_itm IN('" + strCodItmMae + "')";
                strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a2.co_itm";
                strSQL+=" ) AS a1";
                strSQL+=" WHERE a1.ne_numItm>3";
                System.out.println("strSQL: " + strSQL);
                rstPerIng=stmPerIng.executeQuery(strSQL);
                
                if(rstPerIng.next())
                    blnRes=false;
                else
                    blnRes=true;
                
                conPerIng.close();
                conPerIng=null;
                stmPerIng.close();
                stmPerIng=null;
                rstPerIng.close();
                rstPerIng=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite validar que no exista costos unitarios en cero.
     * @return 
     */
    private boolean isCosUniCero(){
        boolean blnRes=false;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                if(bdeCosUni.compareTo(BigDecimal.ZERO)==0){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite obtener diferencia entre items de Egresos e Ingresos
     * @return true Si existe diferencia
     * <BR> false Caso contrario
     */
    private boolean isDifCosIngEgrCero(){
        boolean blnRes=false;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        BigDecimal bdeSumCosUni=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()));
//                bdeCosUni=objUti.redondearBigDecimal(bdeCosUni, objParSis.getDecimalesMostrar()); // JM No funciona para redondeo de 6 decimales 
                bdeSumCosUni=bdeSumCosUni.add(bdeCosUni);
            }
            bdeSumCosUni=objUti.redondearBigDecimal(bdeSumCosUni, objParSis.getDecimalesMostrar());
            if((objUti.redondearBigDecimal(bdeSumCosUni, objParSis.getDecimalesMostrar())).compareTo(BigDecimal.ZERO)!=0){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    
    
}