/*
 * ZafCon32.java
 *
 * Created on 21 Octubre, 2024, 10:16 PM
 */

package Contabilidad.ZafCon32;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
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
 * @author  José Mario Marín 
 */
public class ZafCon32 extends javax.swing.JInternalFrame {
    
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
    
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
   
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
    private ZafVenCon vcoMar;//j
    private String strChoCod;
    private String strChoNom;
    private ZafVenCon vcoCho;//j
    
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecVeh, vecCom;
    private String strTipPrg;
    private String strNomPrg;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private int intSelPrd;
    private int intSelRepSelPrd;

    //Tabla 
//    # id	 numero_recibo	 created_at	 USERID	 CLI_RUC	 CLI_RAZON_SOCIAL	 estado	 tipo	 banco	 comprobante	 estado	 mensaje_ws	 updated_at

   //Código del combustible/Descripción larga del combustible/Precio del combustible    
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_COB=1;
    private final int INT_TBL_DAT_NUM_REC=2;
    private final int INT_TBL_DAT_FEC_ING=3;
    private final int INT_TBL_DAT_USR_ING=4;
    private final int INT_TBL_DAT_COD_CLI=5;
    private final int INT_TBL_DAT_NOM_CLI=6;
    private final int INT_TBL_DAT_EST_SOL=7;
    private final int INT_TBL_DAT_TIP_DOC=8;
    private final int INT_TBL_DAT_COD_CHE=9;
    private final int INT_TBL_DAT_NUM_COM=10;
    private final int INT_TBL_DAT_MON_COB=11;
    private final int INT_TBL_DAT_EST_AUT=12;
    private final int INT_TBL_DAT_FEC_AUT=13;
    private final int INT_TBL_DAT_DIF=14;
    private final int INT_TBL_DAT_MSJ_DOC=15;
    private final int INT_TBL_DAT_USR_APR=16;
    
    /* LOCALES */
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafTblMod objTblModLoc;    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
   
     /* LOCALES */
    
    private Vector vecCodCom, vecDesLarCom, vecPreCom;
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    
    private ZafSelFec objSelFec;
    
    
    
    /** Creates new form ZafVeh06 */
    public ZafCon32(ZafParSis obj) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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
        panFil = new javax.swing.JPanel();
        panFecha = new javax.swing.JPanel();
        panFilDet = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumRec = new javax.swing.JTextField();
        chkPendientes = new javax.swing.JCheckBox();
        panLoc = new javax.swing.JPanel();
        chkMosLocIna = new javax.swing.JCheckBox();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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

        panFil.setLayout(new java.awt.BorderLayout());

        panFecha.setPreferredSize(new java.awt.Dimension(100, 100));
        panFecha.setLayout(new java.awt.BorderLayout());
        panFil.add(panFecha, java.awt.BorderLayout.NORTH);

        panFilDet.setPreferredSize(new java.awt.Dimension(100, 38));
        panFilDet.setLayout(null);

        jLabel1.setText("Núm.Recibo:");
        panFilDet.add(jLabel1);
        jLabel1.setBounds(30, 20, 140, 20);

        jLabel4.setText("Estado:");
        panFilDet.add(jLabel4);
        jLabel4.setBounds(30, 50, 120, 20);
        panFilDet.add(txtNumRec);
        txtNumRec.setBounds(190, 20, 380, 26);

        chkPendientes.setText("Mostrar solo las pendientes");
        panFilDet.add(chkPendientes);
        chkPendientes.setBounds(190, 50, 380, 29);

        panFil.add(panFilDet, java.awt.BorderLayout.CENTER);

        panLoc.setPreferredSize(new java.awt.Dimension(100, 120));
        panLoc.setLayout(new java.awt.BorderLayout());

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        spnLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        spnLoc.setPreferredSize(new java.awt.Dimension(462, 100));

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

        panFil.add(panLoc, java.awt.BorderLayout.SOUTH);
        panLoc.getAccessibleContext().setAccessibleName("Listado de locales");

        tabFrm.addTab("Filtro", panFil);

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

        tabFrm.addTab("General", panRpt);

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

        setBounds(0, 0, 700, 450);
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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
             cargarDetReg();
            if (objThrGUI==null)
            {
                objThrGUI=new ZafCon32.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JCheckBox chkPendientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFecha;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTextField txtNumRec;
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
            this.setTitle(objParSis.getNombreMenu() + " v0.4");
            lblTit.setText(objParSis.getNombreMenu());
            
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFecha.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_COB,"Cód.Cob.");
            vecCab.add(INT_TBL_DAT_NUM_REC,"RCB");
            vecCab.add(INT_TBL_DAT_FEC_ING,"Ingreso");
            vecCab.add(INT_TBL_DAT_USR_ING,"Solicitante");
            vecCab.add(INT_TBL_DAT_COD_CLI,"RUC");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_EST_SOL,"Solicitud");
            vecCab.add(INT_TBL_DAT_TIP_DOC,"TIPO");
            vecCab.add(INT_TBL_DAT_COD_CHE,"Banco");
            vecCab.add(INT_TBL_DAT_NUM_COM,"Comprobante");
            vecCab.add(INT_TBL_DAT_MON_COB,"Monto");
            vecCab.add(INT_TBL_DAT_EST_AUT,"Autorizado");
            vecCab.add(INT_TBL_DAT_FEC_AUT,"Autorización");
            vecCab.add(INT_TBL_DAT_DIF,"Diferencia");
            vecCab.add(INT_TBL_DAT_MSJ_DOC,"Mensaje");      
            vecCab.add(INT_TBL_DAT_USR_APR, "Aprobador");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ING).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_USR_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_EST_SOL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_CHE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NUM_COM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_MON_COB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_EST_AUT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_AUT).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_DIF).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MSJ_DOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_USR_APR).setPreferredWidth(60);
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_FEC_ING).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_FEC_AUT).setResizable(false);
            
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_MON_COB).setCellRenderer(objTblCelRenLblNum);
           
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_MON_COB, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
           
            // Columnas ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_COB, tblDat);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            //se valida que el precio no pueda ser menor que zero, en caso de serlo se mantiene el valor que 
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI); //tabla editable 
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();
             
            configurarTblLoc();
             
            cargarLoc();
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
        int i=0,temp=0,j=0;
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if (con!=null){
                stm=con.createStatement();
                
                String strConSQL="";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
                        strConSQL+=" WHERE CAST(a1.fecha as DATE) BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" WHERE CAST(a1.fecha as DATE) <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" WHERE CAST(a1.fecha as DATE) >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModLoc.getRowCountTrue();
              
                strAux="";
                for (j=0; j<intNumFilTblBod; j++){
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK)){
                        if (i==0)
                            strAux+=" (a7.PTO_VTA_ID=" + objUti.codificar(objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP))  + " )";
                        else
                            strAux+=" OR (a7.PTO_VTA_ID=" + objUti.codificar(objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP))  + " )";
                        i++;
                    }
                }
                if (!strAux.equals("")){
                    strConSQL+=" AND (" + strAux + ")";
                }
                
                
                strSQL=" ";
                strSQL+=" SELECT a7.PTO_VTA_ID, a1.id, a1.numero_recibo, a1.fecha, a2.created_at, a3.USERID, a4.CLI_RUC, a4.CLI_RAZON_SOCIAL,  \n";
                strSQL+="        a1.estado as estadoSol, a2.tipo, a2.banco, a2.comprobante, a2.monto, a2.estado, a2.mensaje_ws,  a2.updated_at, \n";
                strSQL+="        SEC_TO_TIME(TIMESTAMPDIFF(SECOND, a2.updated_at, a2.created_at)) as DiferenciaTiempo,   \n";
                strSQL+="        a2.user_aprobador_rechazo_id, a8.USERID as usrAprobador ";
                strSQL+=" FROM tuvalapi.gp_cobros as a1  \n";
                strSQL+=" INNER JOIN tuvalapi.gp_cobros_depositos as a2 ON (a1.id=a2.cobro_id) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_usuarios as a3 ON (a1.user_id = a3.id) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_clientes as a4 ON (a1.cliente_id=a4.id) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_cajas as a5 ON (a1.caja=a5.CAJA_CODIGO) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_relacion_punto_venta_cajas as a6 ON (a5.CAJA_CODIGO=a6.PTO_VTA_CAJA_CAJA) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_punto_ventas as a7 ON (a6.PTO_VTA_CAJA_PUNTO_VENTA=a7.PTO_VTA_ID) \n";
                strSQL+=" INNER JOIN tuvalapi.gp_usuarios as a8 ON (a2.user_aprobador_rechazo_id=a8.id ) ";
                strSQL+=strConSQL;
                if(txtNumRec.getText().length()>0){
                    strSQL+=" AND a1.numero_recibo like " + objUti.codificar(txtNumRec.getText());
                }
                if(chkPendientes.isSelected()){
                    strSQL+=" AND a2.estado <> 'enviado' ";
                }
                strSQL+=" ORDER BY a1.fecha ASC \n";
                System.out.println("zafCon32 "+strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                i=0;	   	
                vecCodCom = new Vector();
                vecDesLarCom = new Vector();
                vecPreCom = new Vector();
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_COB,rst.getInt("id"));
                        vecReg.add(INT_TBL_DAT_NUM_REC,rst.getString("numero_recibo"));
                        vecReg.add(INT_TBL_DAT_FEC_ING,rst.getString("created_at"));
                        vecReg.add(INT_TBL_DAT_USR_ING,rst.getString("USERID"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("CLI_RUC"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("CLI_RAZON_SOCIAL"));
                        vecReg.add(INT_TBL_DAT_EST_SOL,rst.getString("estadoSol"));
                        vecReg.add(INT_TBL_DAT_TIP_DOC,rst.getString("tipo"));
                        vecReg.add(INT_TBL_DAT_COD_CHE,rst.getString("banco"));
                        vecReg.add(INT_TBL_DAT_NUM_COM,rst.getString("comprobante"));
                        vecReg.add(INT_TBL_DAT_MON_COB,rst.getDouble("monto"));
                        vecReg.add(INT_TBL_DAT_EST_AUT,rst.getString("estado"));
                        vecReg.add(INT_TBL_DAT_FEC_AUT,rst.getString("updated_at"));
                        vecReg.add(INT_TBL_DAT_DIF,rst.getString("DiferenciaTiempo"));
                        vecReg.add(INT_TBL_DAT_MSJ_DOC,rst.getString("mensaje_ws"));
                        vecReg.add(INT_TBL_DAT_USR_APR,rst.getString("usrAprobador"));
                        vecDat.add(vecReg);
                    }
                    else{
                        break;
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
                case INT_TBL_DAT_COD_COB: strMsg="Código de Cobro"; break;
                case INT_TBL_DAT_NUM_REC: strMsg="Número de recibo"; break;
                case INT_TBL_DAT_FEC_ING: strMsg="Fecha/Hora de solicitud"; break;
                case INT_TBL_DAT_FEC_AUT: strMsg="Fecha/Hora de autorización"; break;
                case INT_TBL_DAT_DIF: strMsg="Diferencia de tiempo"; break;
                
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
    
    
//    /**
//     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
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
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT tx_codSer, tx_sucAlt \n";
                    strSQL+=" FROM tbm_loc ";
                    strSQL+=" WHERE st_reg = 'A' ORDER BY 1 \n";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a3.tx_codSer, a3.tx_sucAlt, a2.co_usr  ";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_loc1 as a1  ";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_locUsr as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) ";
                    strSQL+=" INNER JOIN tbm_loc as a3 ON (a1.tx_ptoVta=a3.tx_codSer) ";
                    strSQL+=" WHERE a2.st_reg IN ('A', 'P') AND a3.st_reg = 'A' AND a2.co_usr = "+objParSis.getCodigoUsuario()+"  ";
                    strSQL+=" ORDER BY a3.tx_sucAlt  ";
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("tx_codSer"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_sucAlt"));                   
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
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
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Locales");
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
                    strMsg="Código del Local";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre del Local";
                    break;
                 
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
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

    
}
