/*
 * ZafCom124.java
 *
 * Created on 19 Enero , 2016, 8:44 AM
 */
package Compras.ZafCom124; 
import Librerias.ZafCfgBod.ZafCfgBod; 
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafCorEleGP.ZafCorEleGP; 
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
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín 
 */
public class ZafCom124 extends javax.swing.JInternalFrame
{
    //Constantes: JTable
    private final int INT_TBL_DAT_LIN=0;
    
////    private final int INT_TBL_DAT_COD_TIP_DOC=1;//co_motTra
    private final int INT_TBL_DAT_COD_DOC=1;//co_solTraInv
    private final int INT_TBL_DAT_NUM_DOC=2;
    private final int INT_TBL_DAT_FEC_DOC=3;
////    private final int INT_TBL_DAT_BOD_OR=5;
////    private final int INT_TBL_DAT_BOD_DES=6;
    private final int INT_TBL_DAT_COD_MOT=4;
    private final int INT_TBL_DAT_DES_MOT=5;
//    private final int INT_TBL_DAT_FEC_DOC1=8;
    private final int INT_TBL_DAT_CON_SOL_TRA=6;
    private final int INT_TBL_DAT_CHK_AUT=7;
    private final int INT_TBL_DAT_CHK_DEN=8;
    private final int INT_TBL_DAT_OBS_AUT=9;
    private final int INT_TBL_DAT_CON_OBS=10;
    private final int INT_TBL_DAT_MOD=11;
    private final int INT_TBL_DAT_COS_TOT=12;
  
 
    
    
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
    
    private String strVersion="v1.0"; // 
    private String strMoTrans="";

    private int intNumSol=0;
    private StringBuilder htmlGlo = new StringBuilder(); 
    private ZafCorEleGP objRepCorEleGP;
    
    /**
     * Constructor Principal
     * @param obj 
     */
    public ZafCom124(ZafParSis obj) //chiqui
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
        
////        if(!isVersionActualizada()){
////        mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////        dispose();
////        }
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafCom124.ZafThreadGUI();
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
                    objThrGua=new ZafCom124.ZafThrGua();
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
            this.setTitle(objParSis.getNombreMenu() + strVersion );
            lblTit.setText(objParSis.getNombreMenu());
             btnAdmin.setVisible(false);
             btnAdmin.setEnabled(false);
            objRepCorEleGP = new ZafCorEleGP(objParSis);  /*Libreria para enviar correos electronicos a usuario.*/             
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnAdmin.setVisible(false);
                btnAdmin.setEnabled(false);
            }
            
//            3995;2078;"0";"Guardar"
            
////            if(!objPerUsr.isOpcionEnabled(4629)){
////                butGua.setVisible(false);
////            }
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
            vecCab.add(INT_TBL_DAT_COD_MOT,"Cód. Mot");
            vecCab.add(INT_TBL_DAT_DES_MOT,"Descripción");
            vecCab.add(INT_TBL_DAT_CON_SOL_TRA,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS_AUT,"Obs.Aut.");
            vecCab.add(INT_TBL_DAT_CON_OBS,"");
            vecCab.add(INT_TBL_DAT_MOD,"Nuevo");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Dif.Cos.");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_MOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_MOT).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_MOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(100);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC, tblDat);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_MOD).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setResizable(false);
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
////            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblCod);

            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
////            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
////            objTblCelRenLbl.setBackground(Color.RED);

            
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), true, true);
            
            tcmAux.getColumn(INT_TBL_DAT_DES_MOT).setCellRenderer(objTblCelRenLbl);          

            


            objTblCelRenLbl=null;
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MOD, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_TOT, tblDat);
            
            
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
                     if (objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_MOD)).equals("S"))
                     {
                         if (mostrarMsgCon("¿La conversión que esta intentando visualizar, presenta el Porcentaje de Costo Modificado Manualmente, se recomienda revisar?")==0)
                         {
                             if (intFilSel != -1)  
                             { //Si se permite editar marcar/desmarcar
                                if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AUT)) {                    
                                    objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                                }
                                else  {
                                    objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_AUT);
                                    objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                                }
                            }
                         }
                         else
                             objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                     }
                     else
                     {
////                        objTblCelRenLbl.setBackground(Color.RED);

                         if (intFilSel != -1)  
                         { //Si se permite editar marcar/desmarcar
                            if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AUT)) {                    
                                objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                            }
                            else  {
                                objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_AUT);
                                objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                            }
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
            
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
    
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_MOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_MOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenLbl);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenLbl);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MOD).setCellRenderer(objTblCelRenLbl);                                                
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);                                                
            

            objTblCelRenLbl.addTblCelRenListener(
                new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {

                    public void beforeRender(
                            Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {

                        int fila = objTblCelRenLbl.getRowRender();

                        BigDecimal costoTot = (objTblMod.getValueAt(fila, INT_TBL_DAT_COS_TOT) == null)
                                ? BigDecimal.ZERO
                                : new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_TOT).toString());

                        BigDecimal TOL = new BigDecimal("0.02");

                        // Color por defecto
                        objTblCelRenLbl.setBackground(
                                javax.swing.UIManager.getColor("Table.background")
                        );

                        if (costoTot.compareTo(TOL) > 0) {
                            objTblCelRenLbl.setBackground(new Color(255, 180, 180)); // rojo
                        }
                        else if (costoTot.compareTo(TOL) < 0) {
                            objTblCelRenLbl.setBackground(new Color(180, 200, 255)); // azul
                        }
                    }
                }
            );



            
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
                strSQL+=" SELECT c.co_emp,c.co_doc as co_doc ,c.fe_ing as fe_doc, c.tx_numdoc as tx_numDoc, \n";
                strSQL+="        c.tx_bodorg,c.tx_usrIng,c.tx_obsaut,c.st_aut,c.tx_obs1,C.co_mottra,CAST(det.costoTot AS DECIMAL(18,2)) AS costoTot \n";
                strSQL+=" FROM tbm_CABSolTraInv as c  \n";
                strSQL+="       left join(  \n";
                strSQL+="                   select  sum(nd_costoTot) as costoTot,co_motTra,co_doc  \n";
                strSQL+="                   from Dynamics2021..tbm_detSolTraInv   \n";
                strSQL+="                   group by co_doc,co_motTra  \n";
                strSQL+="                ) as det on (c.co_motTra= det.co_motTra and c.co_doc = det.co_doc)  \n";
                strSQL+=" WHERE st_reg= 'P'  \n";
                strSQL+=" ORDER BY c.co_doc desc";
                
            } 
            else{
                strSQL=" ";
                strSQL+=" SELECT c.co_emp,c.co_doc as co_doc ,c.fe_ing as fe_doc, c.tx_numdoc as tx_numDoc, \n";
                strSQL+="        c.tx_bodorg,c.tx_usrIng,c.tx_obsaut,c.st_aut,c.tx_obs1,C.co_mottra,CAST(det.costoTot AS DECIMAL(18,5)) AS costoTot\n";
                strSQL+=" FROM tbm_CABSolTraInv as c  \n";
                strSQL+="       left join(  \n";
                strSQL+="                   select  sum(nd_costoTot) as costoTot,co_motTra,co_doc  \n";
                strSQL+="                   from Dynamics2021..tbm_detSolTraInv   \n";
                strSQL+="                   group by co_doc,co_motTra  \n";
                strSQL+="                ) as det on (c.co_motTra= det.co_motTra and c.co_doc = det.co_doc)  \n";                
                strSQL+=" WHERE st_reg= 'P'  \n";
                strSQL+=" ORDER BY c.co_doc desc";
            }
            //System.out.println("ZafCom124.cargarDetReg: " + strSQL);
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
                    vecReg.add(INT_TBL_DAT_COD_MOT,rst.getString("co_mottra")==null?"":rst.getString("co_mottra"));
                    vecReg.add(INT_TBL_DAT_DES_MOT,rst.getString("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    vecReg.add(INT_TBL_DAT_CON_SOL_TRA,"...");
                    vecReg.add(INT_TBL_DAT_CHK_AUT,false);
                    vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                    vecReg.add(INT_TBL_DAT_OBS_AUT,rst.getString("tx_obsaut")==null?"":rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_DAT_CON_OBS,"...");
                    vecReg.add(INT_TBL_DAT_MOD,rst.getString("st_aut")==null?"":rst.getString("st_aut"));
                    vecReg.add(INT_TBL_DAT_COS_TOT,rst.getString("costoTot")==null?"":rst.getString("costoTot"));
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
                case INT_TBL_DAT_LIN:
                    strMsg=""; 
                    strMsg+="<html>";
                    strMsg+="<h3 style='text-align:center;'>Colores utilizados en la tabla</h3>";
                    strMsg+="<table border='1'><tr><td><b>Fondo</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: ##ff8000'>&nbsp;</td><td>Indica que se ha realizado alguna modificacion al prorateo del precio.</td></tr>";
                    strMsg+="</table><br></html>";
                    break; 
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
                case INT_TBL_DAT_COD_MOT: strMsg="Código del Motivo'"; break;
                case INT_TBL_DAT_DES_MOT: strMsg="Descripcion del Motivo'"; break;
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
        String strCodTipDoc = "CONV";
        String strCodDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString();

        Compras.ZafCom123.ZafCom123 obj = new Compras.ZafCom123.ZafCom123(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
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
                
////                    strSQL="";
////                    
////                    strSQL = "";
////                    strSQL+= " SELECT MAX(a1.trc_numtrx) as co_seg \n";
////                    strSQL+= " FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.tbmsw_transferencia_cab  as a1  \n";
////                    rstLoc1 = stmLoc.executeQuery(strSQL);
////                    if (rstLoc1.next()) {
////                        intNumSeg=rstLoc1.getInt("co_seg");
////                    }
////                    rstLoc1.close();
////                    rstLoc1=null;
                    
                    
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_CABSolTraInv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
               //  strSQL+=",tx_='"+ objParSis.getNombreComputadoraConDirIP() + "'";
////                 strSQL+=",tx_comMod='"+ objParSis.getNombreComputadoraConDirIP() + "'";
////                strSQL+=",tx_numDoc="+ intNumSeg + "";
                strSQL+=",st_reg='S'";
////                blnIsSolApr=true;
////                if(bodegaNecesitaConfirmacion(i)){
////                    strSQL+=" , st_conInv = 'P'";
////                }else{
////                    strSQL+=" , st_conInv = 'N'";
////                }
               strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" +  objParSis.getCodigoEmpresa()+" and co_mottra ='CONV'";
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
                
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabSolTraInv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
                //strSQL+=",tx_comMod='"+ objParSis.getNombreComputadoraConDirIP() + "'";
                strSQL+=",st_reg='D'";

               strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" +  objParSis.getCodigoEmpresa()+" and co_mottra ='CONV'";
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
    
    private boolean guardarAutorizacion()
    {
        java.sql.Connection conLoc;
        boolean blnRes=false;
        boolean blnAut=false;
        try
        {
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++)
            {
                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                {
                    //conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                    if(con!=null)
                    {
                        con.setAutoCommit(false);
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT))
                        {
                            if (Double.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT).toString())!=0)
                            {
                                if (mostrarMsgCon("Se va a autorizar una solicitud que presenta una diferencia\n" +
                                                  "entre el costo que egresa y el costo que ingresa. ¿Desea continuar?")==0)
                                    blnAut=true;
                            }
                            else
                                blnAut=true;
                            
                            if (blnAut)  
                            {
                                if(actualizarSolicitudAutorizar(con,i))
                                {
                                     con.commit();
                                     blnRes=true;
                                     mostrarMsgInf("<HTML>Registro Autorizado.</HTML>");
                                }
                                else
                                {
                                        con.rollback();
                                        blnRes=false;
                                        System.err.println("Error actualizarSolicitudAutorizar");
                                }                                   
                            }
                            else
                               mostrarMsgInf("<HTML>Registro Cancelado.</HTML>");
////                                   
                        }
                        else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN))
                        {
                            String codigo="";
                            if(actualizarSolicitudDenegar(con, i))
                            {
                                codigo= objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString();
                                String mensajesSujeto ="SE DENEGO LA CONVERSION : " + codigo  +"  POR EL USUARIO:  " + objParSis.getNombreUsuario()+ ""  ;                    
                                htmlGlo.setLength(0); // Esto deja el StringBuilder en blanco
                                htmlGlo = generarHtmlDenegacion(i);
                                    if (htmlGlo == null || htmlGlo.toString().trim().isEmpty())
                                    {
                                        mostrarMsgInf("No se pudo generar el correo de Denegacion de Conversion.. ");
                                        con.rollback();
                                        blnRes=false;  
                                    }
                                    else
                                    {
                                        if(objRepCorEleGP.enviarNotificacionCorreoElectronicoConAsunto(0, 1, 38, htmlGlo, mensajesSujeto  ))
                                            //con.commit();
                                            blnRes=true;                                
                                    } 
                                    
                                
                                blnRes=true;
                                mostrarMsgInf("<HTML>Registro Anulado.</HTML>");
                            }
                            else
                                blnRes=false;
                        }

                        if(blnRes==true)
                            con.commit();
                        else
                            con.rollback();
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
    
private StringBuilder generarHtmlDenegacion(int indice)
{
    if (indice < 0 || indice >= objTblMod.getRowCountTrue())
    {
        throw new IllegalArgumentException(
            "El índice de la fila no es válido: " + indice
        );
    }

    /* ============================================================
       OBTENER DATOS DE LA FILA
       ============================================================ */

    String codigoDocumento = obtenerTextoCelda(
        indice,
        INT_TBL_DAT_COD_DOC
    );



    String fechaDocumento = obtenerTextoCelda(
        indice,
        INT_TBL_DAT_FEC_DOC
    );

    String codigoMotivo = obtenerTextoCelda(
        indice,
        INT_TBL_DAT_COD_MOT
    );

    String descripcionMotivo = obtenerTextoCelda(
        indice,
        INT_TBL_DAT_DES_MOT
    );

    String observacion = obtenerTextoCelda(
        indice,
        INT_TBL_DAT_OBS_AUT
    );



    /* ============================================================
       GENERAR HTML
       ============================================================ */

    StringBuilder html = new StringBuilder();

    html.append("<html>");
    html.append("<head>");
    html.append("<style>");

    html.append("body {");
    html.append("    font-family: Arial, sans-serif;");
    html.append("    font-size: 12px;");
    html.append("    color: #000000;");
    html.append("}");

    html.append("table {");
    html.append("    border-collapse: collapse;");
    html.append("    width: 700px;");
    html.append("}");

    html.append("th, td {");
    html.append("    border: 1px solid #808080;");
    html.append("    padding: 6px;");
    html.append("}");

    html.append("th {");
    html.append("    width: 210px;");
    html.append("    background-color: #f2f2f2;");
    html.append("    text-align: left;");
    html.append("}");

    html.append(".titulo {");
    html.append("    font-size: 16px;");
    html.append("    font-weight: bold;");
    html.append("    margin-bottom: 15px;");
    html.append("}");

    html.append(".numero {");
    html.append("    text-align: right;");
    html.append("}");

    html.append(".denegada {");
    html.append("    background-color: #f2dede;");
    html.append("    color: #a94442;");
    html.append("    font-weight: bold;");
    html.append("}");

    html.append("</style>");
    html.append("</head>");

    html.append("<body>");

    html.append("<div class='titulo'>");
    html.append("Detalle de denegacion de conversion");
    html.append("</div>");

    html.append("<table>");

    agregarFilaHtml(
        html,
        "Codigo de solicitud",
        codigoDocumento,
        ""
    );



    agregarFilaHtml(
        html,
        "Fecha del documento",
        fechaDocumento,
        ""
    );

    agregarFilaHtml(
        html,
        "Codigo del motivo",
        codigoMotivo,
        ""
    );

    agregarFilaHtml(
        html,
        "Descripcion",
        descripcionMotivo,
        ""
    );

    agregarFilaHtml(
        html,
        "Estado",
        "DENEGADA",
        "denegada"
    );

    agregarFilaHtml(
        html,
        "Motivo de la denegacion",
        observacion.isEmpty()
            ? "Sin observación registrada"
            : observacion,
        ""
    );



    agregarFilaHtml(
        html,
        "Usuario",
        objParSis.getNombreUsuario(),
        ""
    );

    html.append("</table>");
    html.append("</body>");
    html.append("</html>");

    return html;
} 

private String obtenerTextoCelda(int fila, int columna)
{
    Object valor = objTblMod.getValueAt(fila, columna);

    if (valor == null)
    {
        return "";
    }

    String texto = valor.toString().trim();

    if (texto.equalsIgnoreCase("null"))
    {
        return "";
    }

    return texto;
}

private void agregarFilaHtml(
        StringBuilder html,
        String etiqueta,
        String valor,
        String clase
)
{
    html.append("<tr>");

    html.append("<th>");
    html.append(escaparHtml(etiqueta));
    html.append("</th>");

    if (clase != null && !clase.trim().isEmpty())
    {
        html.append("<td class='")
            .append(clase)
            .append("'>");
    }
    else
    {
        html.append("<td>");
    }

    html.append(
        escaparHtml(valor == null ? "" : valor)
    );

    html.append("</td>");
    html.append("</tr>");
}
    /**
     * 
     *   EGRESO --  INGRESO 
     * @param conn
     * @param intCodBodOrg
     * @param intCodBodDes
     * @return 
     */
   

private String escaparHtml(String texto)
{
    if (texto == null)
    {
        return "";
    }

    return texto
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;");
}

private String formatearDecimal(BigDecimal valor)
{
    if (valor == null)
    {
        return "0.00";
    }

    return valor
        .setScale(2, RoundingMode.HALF_UP)
        .toPlainString();
}  
  
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
                    strSQL+=" select iv.DEX_ROW_ID as co_item ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITMSHNAM) as tx_codAlt2,RTRIM(iv.ITEMDESC) as tx_nomItm, \n";
                    strSQL+="        RTRIM(iv.UOMSCHDL) as tx_desCor,inf.Peso_KG as nd_pesItmKgr, v.DISPONIBLE as nd_canDis,iv.CURRCOST,RTRIM(v.LOCNCODE) as bodega     \n";
                    strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito as v \n";
                    strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
                    //strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";
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
