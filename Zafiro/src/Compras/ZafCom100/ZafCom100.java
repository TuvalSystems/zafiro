/*
 * ZafCom100.java
 *
 * Created on 19 Enero , 2016, 8:44 AM
 */
package Compras.ZafCom100;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafGenFacAut.ZafGenFacAutAdm;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafMovIngEgrInv.ZafReaMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín 
 */
public class ZafCom100 extends javax.swing.JInternalFrame
{
    //Constantes: JTable
    private final int INT_TBL_DAT_LIN=0;
    
////    private final int INT_TBL_DAT_COD_TIP_DOC=1;//co_motTra
    private final int INT_TBL_DAT_COD_DOC=1;//co_solTraInv
    private final int INT_TBL_DAT_NUM_DOC=2;
    private final int INT_TBL_DAT_FEC_DOC=3;
////    private final int INT_TBL_DAT_BOD_OR=5;
////    private final int INT_TBL_DAT_BOD_DES=6;
    private final int INT_TBL_DAT_COD_REC=4;
    private final int INT_TBL_DAT_DES_REC=5;
//    private final int INT_TBL_DAT_FEC_DOC1=8;
    private final int INT_TBL_DAT_CON_SOL_TRA=6;
    private final int INT_TBL_DAT_CHK_AUT=7;
    private final int INT_TBL_DAT_CHK_DEN=8;
    private final int INT_TBL_DAT_OBS_AUT=9;
    private final int INT_TBL_DAT_CON_OBS=10;
  
 
    
    
    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 11/Marzo/2016  */
    final int INT_ARL_COD_EMP=0;
    final int INT_ARL_COD_LOC=1;
////    final int INT_ARL_COD_TIP_DOC=2;
////    final int INT_ARL_COD_BOD_GRP=3;  // TRABAJA POR GRUPO!!!! 
    final int INT_ARL_COD_ITM=3;
    final int INT_ARL_COD_ITM_MAE=4;
////    final int INT_ARL_COD_BOD=6;
////    final int INT_ARL_NOM_BOD=7;
    final int INT_ARL_CAN_COM=5;
    final int INT_ARL_CHK_CLI_RET=6;
////    final int INT_ARL_EST_BOD=10;
////    final int INT_ARL_ING_EGR_FIS_BOD=11;
////    final int INT_ARL_COD_BOD_GRP_ING=12;  /// TRABAJA POR GRUPO !!!
    
    //Constantes: Para el contenedor a enviar solicitud de Cotizaciones de Venta JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
////    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=2;
////    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    
    //Códigos de configuración.

    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;                                    //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButDG1;//Render: Presentar JButton en JTable.
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafThreadGUI objThrGUI;
    private ZafThrGua objThrGua; 
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;        //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;       //Editor: JButton en celda (Bodega origen).    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk; 
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private ZafTblCelRenBut objTblCelRenButConGre, objTblCelRenButConOcp;  


    
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.awt.Component componente;
    private java.awt.Frame Frame;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    ArrayList arlDat = new ArrayList();
    ArrayList arlReg = new ArrayList();
    
 
    
  
       
    private boolean blnHayCam;

    private boolean blnCon;                                     //true: Continua la ejecución del hilo. // Continuidad del hilo
    

  
    
    private String strSQL, strAux, strNomBodSolTra;
    
    private String strVersion=" v0.1"; //Cristhian Ramirez
    private String strMoTrans="";

    private int intNumSol=0;
    
    /**
     * Constructor Principal
     * @param obj 
     */
    public ZafCom100(ZafParSis obj) //chiqui
    {
        try{
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            componente=this;
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {  //AKI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
                 initComponents();
                 configurarFrm();
              
                 strMoTrans ="TRANSFER";
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
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
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

        btnAdmin.setText("Admin");
        btnAdmin.setPreferredSize(new java.awt.Dimension(95, 25));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        panBot.add(btnAdmin);

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
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
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
        objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafCom100.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(objThrGua==null){
                    objThrGua=new ZafCom100.ZafThrGua();
                    objThrGua.start();
                }
            }
        }
        else {
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        llamarVenAdmin();
        
    }//GEN-LAST:event_btnAdminActionPerformed

    private void llamarVenAdmin(){
        ZafGenFacAutAdm objGeneraFactura = new  ZafGenFacAutAdm(objParSis);
        this.getParent().add(objGeneraFactura,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraFactura.show();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
       
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){//chiqui
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + strVersion + "chiqui");
            lblTit.setText(objParSis.getNombreMenu());
             btnAdmin.setVisible(false);
             btnAdmin.setEnabled(false);
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnAdmin.setVisible(false);
                btnAdmin.setEnabled(false);
            }
            
//            3995;2078;"0";"Guardar"
            
            if(!objPerUsr.isOpcionEnabled(3995)){
                butGua.setVisible(false);
            }
            //chiquitabla
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
             //chiqui
            vecCab.add(INT_TBL_DAT_LIN,"");
//            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
//            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
////            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
//            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
//            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
////            vecCab.add(INT_TBL_DAT_BOD_OR,"Bodega Origen");
////            vecCab.add(INT_TBL_DAT_BOD_DES,"Bodega Destino");
            vecCab.add(INT_TBL_DAT_COD_REC,"Cód. Receta");
            vecCab.add(INT_TBL_DAT_DES_REC,"Descripción");
            vecCab.add(INT_TBL_DAT_CON_SOL_TRA,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS_AUT,"Obs.Aut.");
           
           

            
            
            vecCab.add(INT_TBL_DAT_CON_OBS,"");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
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
            //CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
////            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setPreferredWidth(20);
////            tcmAux.getColumn(INT_TBL_DAT_BOD_OR).setPreferredWidth(60);
////            tcmAux.getColumn(INT_TBL_DAT_BOD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_REC).setPreferredWidth(200);

            
            
            
            //CHIQUI
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_TIP_DOC, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_TIP_DOC, tblDat);
            

            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
////            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblCod);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            

            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CON_SOL_TRA);
            vecAux.add("" + INT_TBL_DAT_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_OBS_AUT);
            vecAux.add("" + INT_TBL_DAT_CON_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
           //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
////            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            

            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objTblCelRenButConOcp=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenButConOcp);
            objTblCelRenButConOcp = null;
            
            new ButSolTraInv(tblDat, INT_TBL_DAT_CON_SOL_TRA);
            
            ButObs butCot = new ButObs(tblDat, INT_TBL_DAT_CON_OBS); //*****
            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChk);
            
            //Check: Autorizar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AUT)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_AUT);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                    }
                }
            });
                   
            //Check: Denegar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_DEN)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_DEN);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                    }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();

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
 
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(java.sql.Connection conn)
    {
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (conn!=null){
                stm=conn.createStatement();
            if(objParSis.getCodigoUsuario()==1){
                strSQL=" ";
                strSQL+=" SELECT c.co_emp,co_grp as co_doc ,c.fe_ing as fe_doc, c.co_grp as tx_numDoc, \n";
                strSQL+="        tx_descor,tx_deslar,c.tx_usrIng,c.tx_obsaut \n";
                strSQL+=" FROM tbm_cabcfgconinv as c  \n";
                strSQL+=" WHERE st_reg= 'P'  \n";
                strSQL+=" ORDER BY c.co_grp desc";
                
            } 
            else{
                strSQL=" ";
                strSQL+="   SELECT a1.tx_numDoc,a1.co_motTra,a4.Descripcion,a1.co_doc, a1.fe_doc,a1.tx_boddes, \n";
                strSQL+="       (SELECT COUNT(ne_Det) FROM Dynamics2021.dbo.tbm_detSolTraInv WHERE co_doc =a1.co_doc AND co_motTra ='" + strMoTrans + "') as contmax, \n";
                strSQL+="        a1.st_imp,a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.tx_usrIng,a1.tx_obUltM, a1.st_aut,a1.st_conInv,a1.tx_obsaut, a1.tx_bodorg \n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as a1    \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu a4 on (a1.co_motTra =a4.tx_mot AND a1.co_tipdoc = a4.co_tipdoc)  \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr a5 on (a5.co_mnu =a4.co_mnu and a5.co_tipdoc =a4.co_tipdoc )  \n";
                strSQL+=" WHERE co_motTra='" + strMoTrans + "' AND a1.st_conInv ='P' AND a1.st_reg ='A' and a4.co_mnu =" + objParSis.getCodigoMenu() + " and a5.co_usr =" + objParSis.getCodigoUsuario()  + " \n";
                strSQL+=" ORDER BY a1.co_doc,fe_doc desc";
            }
            //System.out.println("ZafCom100.cargarDetReg: " + strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            while(rst.next()){
                if (blnCon){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
//                    vecReg.add(INT_TBL_DAT_COD_EMP,1);//chiquinova
//                    vecReg.add(INT_TBL_DAT_COD_LOC,1);//chiquinova
////                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_motTra")==null?"":rst.getString("co_motTra"));
//                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,1);//chiquinova
//                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,1);//chiquinova
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc")==null?"":rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("tx_numDoc")==null?"":rst.getString("tx_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc")==null?"":rst.getString("fe_doc"));
////                    vecReg.add(INT_TBL_DAT_BOD_OR,rst.getString("tx_bodorg")==null?"":rst.getString("tx_bodorg"));
////                    vecReg.add(INT_TBL_DAT_BOD_DES,rst.getString("tx_boddes")==null?"":rst.getString("tx_boddes"));
                    vecReg.add(INT_TBL_DAT_COD_REC,rst.getString("tx_descor")==null?"":rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_DAT_DES_REC,rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_CON_SOL_TRA,"Boton");
                    vecReg.add(INT_TBL_DAT_CHK_AUT,false);
                    vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                    vecReg.add(INT_TBL_DAT_OBS_AUT,rst.getString("tx_obsaut")==null?"":rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_DAT_CON_OBS,"Boton");
//               
//                    strBodOrigen =rst.getString("tx_bodorg");
//                    strBodDestino =rst.getString("tx_boddes");
//                    numeroItems =rst.getInt("contmax");
                    vecDat.add(vecReg);
                }
                else{
                    break;
                }
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
        rst=null;
        stm=null;
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
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
//                case INT_TBL_DAT_COD_EMP: strMsg="Código de la Empresa"; break;
//                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
////                case INT_TBL_DAT_COD_TIP_DOC: strMsg="Código del tipo de Documento"; break;
//                case INT_TBL_DAT_DES_COR_TIP_DOC: strMsg="Descripción corta del tipo de Documento"; break;
//                case INT_TBL_DAT_DES_LAR_TIP_DOC: strMsg="Descripción larga del tipo de Documento"; break;
                case INT_TBL_DAT_COD_DOC: strMsg="Código del documento"; break;
                case INT_TBL_DAT_NUM_DOC: strMsg="Número del documento"; break;
                case INT_TBL_DAT_FEC_DOC: strMsg="Fecha del documento"; break;
                case INT_TBL_DAT_CON_SOL_TRA: strMsg="Muestra la Solicitud de Conversion'"; break;
                case INT_TBL_DAT_CHK_AUT: strMsg="Autorizar"; break;
                case INT_TBL_DAT_CHK_DEN: strMsg="Denegar"; break;
                case INT_TBL_DAT_OBS_AUT: strMsg="Observación de la autorización"; break;
                case INT_TBL_DAT_CON_OBS: strMsg="Muestra la Observación de la Autorización'"; break;
////                case INT_TBL_DAT_BOD_OR: strMsg="Bodega de Origen'"; break;
////                case INT_TBL_DAT_BOD_DES: strMsg="Bodega de Destino'"; break;
                case INT_TBL_DAT_COD_REC: strMsg="Código de la Receta'"; break;
                case INT_TBL_DAT_DES_REC: strMsg="Descripcion de la Receta'"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }
    }
    
    private class ZafThreadGUI extends Thread{
        public void run(){
            try{
                //con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
            
                if (!cargarDetReg(con))
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");

                }

                con.close();
                objThrGUI=null;
            }
            catch (java.sql.SQLException e){
                System.err.println(e);
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (!guardarAutorizacion()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                //Inicializar objetos si no se pudo cargar los datos.
                objThrGua=null;
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener{
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Transferencia de Inventario */">
    private class ButSolTraInv extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButSolTraInv(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Solicitud de Conversion de Inventario.");
        }

        public void butCLick()
        {
            cargarVentanaSolTraInv();
        }
    }
    
    private void cargarVentanaSolTraInv()
    {
//        String strCodEmp = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
//        String strCodLoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString();
        String strCodEmp = "0";
        String strCodLoc = "1";
        String strCodTipDoc = "1";
        String strCodDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString();

        Compras.ZafCom98.ZafCom98 obj = new Compras.ZafCom98.ZafCom98(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold> 
 
 
    private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButObs(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación.");
           
        }
        @Override
        public void butCLick() {
           llamarVenObs(tblDat.getSelectedRow());
        }
    }
        
    private void llamarVenObs(int intFil)
    {
        String strObs = ( tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  )==null?"":tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  ).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, INT_TBL_DAT_OBS_AUT);
        }
        obj = null;
    }
    

    boolean blnIsSolApr=false;
    
    
    
    private boolean actualizarSolicitudAutorizar(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc1;
        int intNumSeg=0;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                
                    strSQL="";
                    
                    strSQL = "";
                    strSQL+= " SELECT MAX(a1.trc_numtrx) as co_seg \n";
                    strSQL+= " FROM TUVAL.dbo.tbmsw_transferencia_cab  as a1  \n";
                    rstLoc1 = stmLoc.executeQuery(strSQL);
                    if (rstLoc1.next()) {
                        intNumSeg=rstLoc1.getInt("co_seg");
                    }
                    rstLoc1.close();
                    rstLoc1=null;
                    
                    
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabcfgconinv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
                 strSQL+=",tx_comMod='"+ objParSis.getNombreComputadoraConDirIP() + "'";
////                strSQL+=",tx_numDoc="+ intNumSeg + "";
                strSQL+=",st_reg='A'";
////                blnIsSolApr=true;
////                if(bodegaNecesitaConfirmacion(i)){
////                    strSQL+=" , st_conInv = 'P'";
////                }else{
////                    strSQL+=" , st_conInv = 'N'";
////                }
               strSQL+=" WHERE co_grp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" +  objParSis.getCodigoEmpresa()+"";
                System.out.println("UPDATE " + strSQL);
                stmLoc.executeUpdate(strSQL);
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
    
    
   
    
    
     private boolean actualizarSolicitudDenegar(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabcfgconinv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
                strSQL+=",tx_comMod='"+ objParSis.getNombreComputadoraConDirIP() + "'";
                strSQL+=",st_reg='D'";

               strSQL+=" WHERE co_grp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" +  objParSis.getCodigoEmpresa()+"";
                System.out.println("UPDATE " + strSQL);
                blnIsSolApr=true;
                stmLoc.executeUpdate(strSQL);
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
    
    private boolean guardarAutorizacion(){
        java.sql.Connection conLoc;
        boolean blnRes=false;
        try{
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    //conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                    if(con!=null){
                        con.setAutoCommit(false);
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT)){
                            if(actualizarSolicitudAutorizar(con,i)){
                                 con.commit();
                                 blnRes=true;
                                 mostrarMsgInf("<HTML>Registro Autorizado.</HTML>");
                            }else{
                                    con.rollback();
                                    blnRes=false;
                                    System.err.println("Error actualizarSolicitudAutorizar");
                            }
////                                   
                        }
                        else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)){
                            if(actualizarSolicitudDenegar(con, i)){
                                blnRes=true;
                                  mostrarMsgInf("<HTML>Registro Anulado.</HTML>");
                            }else{blnRes=false;}
                        }

                        if(blnRes==true){
                            con.commit();
                        }
                        else{
                            con.rollback();
                        }
                        con.close();
                        conLoc=null;
                        blnRes=false;
                    }
                }
            }
            pgrSis.setIndeterminate(false);
            ////con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
          
            cargarDetReg(con);
            con.close();
            con=null;
            blnRes=true;
            
            
               
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    /**
     * 
     *   EGRESO --  INGRESO 
     * @param conn
     * @param intCodBodOrg
     * @param intCodBodDes
     * @return 
     */
   
    
  
    private boolean blnIsImportacion=false;
    
    /**
     * Valida Stock Disponible 
     * 
     * Funcion para validar la cantidad disponible contra la solicitud de transferencia
     * @return 
     */
    private boolean validarStockDisponible(java.sql.Connection conExt, int i){
        boolean blnRes=true, blnIsStock=true, blnIsBodIngImp=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc= conExt.createStatement();

              strSQL="";
                    strSQL+=" select iv.DEX_ROW_ID as co_item ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITMGEDSC) as tx_codAlt2,RTRIM(iv.ITEMDESC) as tx_nomItm, \n";
                    strSQL+="        RTRIM(iv.UOMSCHDL) as tx_desCor,inf.Peso_KG as nd_pesItmKgr, v.DISPONIBLE as nd_canDis,iv.CURRCOST,RTRIM(v.LOCNCODE) as bodega     \n";
                    strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito as v \n";
                    strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
                    strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";
//// chiqui2                   strSQL+=" where v.DISPONIBLE > 0 and v.LOCNCODE ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString() +"' \n";
                    strSQL+=" ORDER BY  v.ITEMNMBR desc  \n";
                    
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    if(blnIsImportacion){
                        //Valida que la bodega origen de la solicitud corresponda a la bodega del ingreso por importacion.
                        if(!(rstLoc.getString("co_bod")).equals(rstLoc.getString("co_bodIngImp"))){
                            blnRes=false;
                            blnIsBodIngImp=false;
                        }
                    }
                    
                    if(blnIsBodIngImp)
                    {
                        //Valida disponible
                        if(Double.parseDouble(rstLoc.getString("nd_canDis"))<0.00){
                           blnRes=false;
                           blnIsStock=false;
                           strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("disponible"), objParSis.getDecimalesMostrar())  + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_can"), objParSis.getDecimalesMostrar()) + " </td> ";

                           if(blnIsImportacion){
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canIngImp"), objParSis.getDecimalesMostrar()) + " </td> ";
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canTran"), objParSis.getDecimalesMostrar()) + " </td> ";
                           }
                           strItem+=" </tr> ";
                        }
                    }
                }
                if(!blnIsBodIngImp) {
                    mostrarMsgInf("La solicitud que desea autorizar tiene la bodega origen distinta a la del ingreso por importación");
                }
                
                if(!blnIsStock){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    if(blnIsImportacion){
                        strMsg+="<td> Ingreso Importación </td>";
                        strMsg+="<td> Transferencias </td>";
                    }
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    //System.out.println(strMsg);
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
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
    
   
}
