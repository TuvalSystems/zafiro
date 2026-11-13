/*
 * ZafMae88.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Maestros.ZafMae88;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Maestros.ZafMae85.ZafMae85;
////import Compras.ZafCom18.ZafCom18_01;
////import Compras.ZafCom91.ZafCom91;
////import Compras.ZafCom99.ZafCom99;

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
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafUtilGP.ZafUtilGP;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

     

/**
 *
 * @author  Cristhian Ramirez
 */
public class ZafMae88 extends javax.swing.JInternalFrame{
    
    private final int INT_TBL_DAT_LIN=0;    
    private final int INT_TBL_DAT_CODIGO=1;
    private final int INT_TBL_DAT_ITEM=2;    
    private final int INT_TBL_DAT_COD3L=3;    
    private final int INT_TBL_DAT_DESCRIPCION=4;
    private final int INT_TBL_DAT_PESO=5;
    private final int INT_TBL_DAT_PRECIO=6;                      
    private final int INT_TBL_DAT_MODIFICAR=7;
    private final int INT_TBL_DAT_TIPO=8;
    private final int INT_TBL_DAT_CHK_MOS = 9;
    
    //Variables
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafUtilGP objUtiGP;
    private ZafTblFilCab objTblFilCab;
   
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
 
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.

    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).    
    
     
    private ZafVenConGP vcoGrupo;                                   //Ventana de consulta.
    private Connection con; 
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
     
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
 
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafMouMotAda objMouMotAda;
    private String strVersion="v0.1"; 
    
    private ZafTblCelEdiTxt objTblCelEdiBtn;
    
    private ArrayList arlRegConDoc, arlDatConDoc;
    private static final int INT_ARL_FACTOR=0;
    
    private char estado='0';
    private ZafTblCelRenBut objTblCelRenButConGre, objTblCelRenButConOcp; 
    private int tipoAgrupa =-1;
    private ArrayList<String> dataList = new ArrayList<>(); 
    private ArrayList<String> dataListTipos = new ArrayList<>();     
    private ZafTblCelRenCbo objTblCelRenCmbBox;     
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;    
    private java.util.Date datFecDoc;    
    private String mensajeError ="";
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strFecUltMod="";
    
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafHisTblBasDatGP objHisTblBasDatGP;  
    private int codMenu;
    private boolean validaOk =false;
    private int nd_tipoConfig =-1;
    /**
     * Crea una nueva instancia de la clase  .
     * @param obj El objeto ZafParSis. 
     */
    public ZafMae88(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            configurarFrm();
            codMenu =objParSis.getCodigoMenu();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    public boolean validaOK()
    {
        return validaOk;
    }        
    
    public ZafMae88(ZafParSis obj1, String grupo,int menu){
////                this(obj1);
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj1.clone();
            
            configurarFrm();
            cmbTipoAgru.setSelectedItem("Precios");
            tipoAgrupa = obtenerIdTipoAgrup("Precios");            
            cmbTipoAgru.setEnabled(false);
            txtIdGrupo.setText(grupo);
            txtIdGrupo.setEnabled(false);
            ConsultaIndividual();
            txtItem.setEnabled(false);
            txtItem.setEnabled(false);
            txtDescGru.setEnabled(false);
            butGrupo.setEnabled(false);
            butLimpiar.setEnabled(false);
            butConsultar.setEnabled (false);
            
            codMenu =menu;
            
            lblTit.setText("Consulta-Modificación de Factores");
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
        bgrFuncionamiento = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panPer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDescGru = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdGrupo = new javax.swing.JTextField();
        butGrupo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbTipoAgru = new javax.swing.JComboBox<>();
        chk_mostrarDeta = new javax.swing.JCheckBox();
        txtItem = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbTipoCla = new javax.swing.JComboBox<>();
        panTab = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butLimpiar = new javax.swing.JButton();
        butCerrar2 = new javax.swing.JButton();
        butConsultar = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatGru = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
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

        panGen.setLayout(new java.awt.BorderLayout());

        panPer.setPreferredSize(new java.awt.Dimension(100, 80));
        panPer.setLayout(null);

        jLabel1.setText("Grupo:");
        panPer.add(jLabel1);
        jLabel1.setBounds(10, 50, 60, 14);

        txtDescGru.setEnabled(false);
        txtDescGru.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescGruFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescGruFocusLost(evt);
            }
        });
        txtDescGru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescGruActionPerformed(evt);
            }
        });
        panPer.add(txtDescGru);
        txtDescGru.setBounds(170, 50, 250, 20);

        jLabel5.setText("-");
        panPer.add(jLabel5);
        jLabel5.setBounds(660, 10, 10, 14);

        txtIdGrupo.setEnabled(false);
        txtIdGrupo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdGrupoFocusLost(evt);
            }
        });
        txtIdGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdGrupoActionPerformed(evt);
            }
        });
        panPer.add(txtIdGrupo);
        txtIdGrupo.setBounds(50, 50, 40, 20);

        butGrupo.setText("...");
        butGrupo.setEnabled(false);
        butGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrupoActionPerformed(evt);
            }
        });
        panPer.add(butGrupo);
        butGrupo.setBounds(420, 50, 20, 20);

        jLabel2.setText("Tipo:");
        panPer.add(jLabel2);
        jLabel2.setBounds(520, 50, 40, 20);

        cmbTipoAgru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguna" }));
        cmbTipoAgru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoAgruActionPerformed(evt);
            }
        });
        panPer.add(cmbTipoAgru);
        cmbTipoAgru.setBounds(250, 10, 170, 20);

        chk_mostrarDeta.setText("Detalle");
        chk_mostrarDeta.setActionCommand("Detalle");
        chk_mostrarDeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_mostrarDetaActionPerformed(evt);
            }
        });
        panPer.add(chk_mostrarDeta);
        chk_mostrarDeta.setBounds(440, 50, 70, 23);

        txtItem.setEnabled(false);
        txtItem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemFocusLost(evt);
            }
        });
        txtItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemActionPerformed(evt);
            }
        });
        panPer.add(txtItem);
        txtItem.setBounds(90, 50, 80, 20);

        jLabel3.setText("Tipo de Agrupación:");
        panPer.add(jLabel3);
        jLabel3.setBounds(10, 10, 160, 14);

        cmbTipoCla.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguna" }));
        cmbTipoCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoClaActionPerformed(evt);
            }
        });
        panPer.add(cmbTipoCla);
        cmbTipoCla.setBounds(550, 50, 110, 20);

        panGen.add(panPer, java.awt.BorderLayout.NORTH);

        panTab.setPreferredSize(new java.awt.Dimension(0, 170));
        panTab.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butLimpiar.setText("Limpiar");
        butLimpiar.setToolTipText("Limpiar la informacion de la ventana.");
        butLimpiar.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpiarActionPerformed(evt);
            }
        });
        panBot.add(butLimpiar);

        butCerrar2.setText("Cerrar");
        butCerrar2.setToolTipText("Cierra la ventana.");
        butCerrar2.setPreferredSize(new java.awt.Dimension(92, 25));
        butCerrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrar2ActionPerformed(evt);
            }
        });
        panBot.add(butCerrar2);

        butConsultar.setText("Consultar");
        butConsultar.setToolTipText("Crear Un Grupo.");
        butConsultar.setEnabled(false);
        butConsultar.setPreferredSize(new java.awt.Dimension(92, 25));
        butConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConsultarActionPerformed(evt);
            }
        });
        panBot.add(butConsultar);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guardar");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        panTab.add(panBot, java.awt.BorderLayout.PAGE_END);

        tblDatGru.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatGru.setRowHeight(20);
        jScrollPane2.setViewportView(tblDatGru);

        panTab.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        panGen.add(panTab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 706, 483);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
            if(!isVersionActualizada()){
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }        
    }//GEN-LAST:event_formInternalFrameOpened

    
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
                strSQL+=" SELECT co_mnuSis, tx_ver, tx_nomMnu FROM tbm_verMnuSis where co_mnuSis="+codMenu;
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

    private void butGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrupoActionPerformed
////
////                if ((txtIdGrupo.getText().equals("")) &&(txtDescGru.getText().equals("")))
////                    mostrarGrupos(0);//chiqui
////                else
////                     mostrarGrupos(1);//chiqui
        configurarGrupos();
        mostrarGrupos(0);//chiqui

    }//GEN-LAST:event_butGrupoActionPerformed

    private void txtIdGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdGrupoActionPerformed

    private void txtIdGrupoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdGrupoFocusLost
        ConsultaIndividual();
////        if (txtIdGrupo.getText().equals("")) 
////        {
////                configurarGrupos();
////                mostrarGrupos(4);
////        }
////        else
////        {
////            configurarGrupos();
////            mostrarGrupos(1);
////        }
                
        // txtCodBod.setText(strCodBod);

        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdGrupoFocusLost
    private void ConsultaIndividual ()
    {
        if (txtIdGrupo.getText().equals("")) 
        {
                configurarGrupos();
                mostrarGrupos(4);
        }
        else
        {
            configurarGrupos();
            mostrarGrupos(1);
        }
    }
    private void txtIdGrupoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdGrupoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdGrupoFocusGained

    private void txtDescGruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescGruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescGruActionPerformed

    private void txtDescGruFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescGruFocusLost
        ////        if (!txtDescGru.getText().equalsIgnoreCase(txtDescGru.getText())) {
            ////            if (txtDescGru.getText().equals("")) {
                ////                txtDescGru.setText("");
                ////            }
            ////        }
        ////        else
        ////           mostrarGrupos(2);
        // txtCodBod.setText(strCodBod);
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescGruFocusLost

    private void txtDescGruFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescGruFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescGruFocusGained

    private void butCerrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrar2ActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerrar2ActionPerformed

    private void butLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpiarActionPerformed
    limpiarFrm();
    }//GEN-LAST:event_butLimpiarActionPerformed

    
    public void ConsultarPorGrupo()
    {
       mostrarGrupos (3); 
    }
            
    private void butConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConsultarActionPerformed
        mostrarGrupos (3);
    }//GEN-LAST:event_butConsultarActionPerformed

    private void cmbTipoAgruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoAgruActionPerformed
        tipoAgrupa = obtenerIdTipoAgrup(cmbTipoAgru.getSelectedItem().toString());
        if (tipoAgrupa!=-1)
        {
            butGrupo.setEnabled(true);
            txtIdGrupo.setEnabled(true);
            txtDescGru.setEnabled(true);
            txtItem.setEnabled(true);
            butConsultar.setEnabled(true);
            objTblMod.removeAllRows();
            txtIdGrupo.setText("");
            txtDescGru.setText("");
            txtItem.setText("");
            
////            butCons.doClick();
            ////            if (!cargarDetReg())
            ////            {
                ////                //Inicializar objetos si no se pudo cargar los datos.
                ////                lblMsgSis1.setText("Listo");
                ////                pgrSis1.setValue(0);
                ////                butCons.setText("Consultar");
                ////            }
            ////            //Establecer el foco en el JTable sólo cuando haya datos.
            ////////            objThrGUI =null;
            ////            if (tblDatFor.getRowCount()>0)
            ////            {
                ////                tabFrm.setSelectedIndex(1);
                ////                tblDatFor.setRowSelectionInterval(0, 0);
                ////                tblDatFor.requestFocus();
                ////            }
        }
        else
        {
            butGrupo.setEnabled(false);
            txtIdGrupo.setEnabled(false);
            txtDescGru.setEnabled(false);
            butConsultar.setEnabled(false);
            txtItem.setEnabled(false);

        }

    }//GEN-LAST:event_cmbTipoAgruActionPerformed

    private void txtItemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemFocusGained

    private void txtItemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemFocusLost
//////        if (txtItem.getText().equals("")) 
//////        {
//////                configurarGrupos();
//////                mostrarGrupos(4);
//////        }
//////        else
//////            configurarGrupos();
//////            mostrarGrupos(2);
        // txtCodBod.setText(strCodBod);

        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemFocusLost

    private void txtItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemActionPerformed

private boolean borrar_tbmDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_detGrpInv where co_grpInv ="+ tipo +"";
                System.out.println("borrar_tbmDetGrupo: " + strSQL);
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
    
    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        // TODO add your handling code here:
        boolean blnRes=false;
        int indicador=-1;
        String error ="";
        int codMaxi=0;
        java.sql.Connection conLoc;
        java.sql.Connection connZAF;

        
        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        try
        {
          
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conLoc.setAutoCommit(false);
            connZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());      
            connZAF.setAutoCommit(false);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
             {
                 if ((!txtIdGrupo.getText().equals("")) && (cmbTipoCla.getSelectedItem().toString() !="Ninguno"))
                 {
                     int intNumFil=objTblMod.getRowCountTrue();
                     int codigoGrupoGlobal = Integer.parseInt(txtIdGrupo.getText()) ;
                     if (intNumFil>1)
                     {
                        if(modificar_tbmCabGrupo(conLoc,codigoGrupoGlobal))
                        {
                            if(modificar_tbmInv(conLoc,codigoGrupoGlobal,connZAF))
                                blnRes=true;
                            else
                            {
                                blnRes=false;
                                mensajeError ="<HTML>ERROR al momento de modificar la cabecera.</HTML>";
                            }
                        }
                        else
                        {
                            blnRes=false;
                            mensajeError ="<HTML>ERROR al momento de ingresar la cabecera.</HTML>";
                        }
                     }
                     else                     
                     {
                            blnRes=false;
                            mensajeError ="<HTML>ERROR no se tiene ningun elemento que modificar.</HTML>";                         
                     }
                 }
                 else
                 {
                     mensajeError ="<HTML>Debe seleccionar un Grupo.</HTML>";  
                     limpiarFrm();
                     blnRes=false;
                 }
                 if (blnRes)
                 {
////                    if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_cabGrpInv", "tbh_cabGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_itmpad")) 
////                    {
////                        if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_detGrpInv", "tbh_detGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_coditm"))                                
////                        {
////                            connZAF.commit();
////                            conLoc.commit();
////                             blnRes=true;
////                             mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
////                        }
////                        else
////                        {
////                            blnRes=false;
////                            connZAF.rollback();
////                            conLoc.rollback();
////                        }
////                    }
////                    else
////                    {
////                        blnRes=false; 
////                        conLoc.rollback();
////                        connZAF.rollback();
////                    }
                     
////                        if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_cabGrpInv", "tbh_cabGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_itmpad")) 
////                        {
////                            if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_detGrpInv", "tbh_detGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_coditm"))                                
////                            {
////                               if(objHisTblBasDat.insertarHistoricoMasivo(connZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) 
////                                {
                                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                                    {
////                                         connZAF.commit();
                                         conLoc.commit();
                                         blnRes=true;
                                         mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");                                        
                                    }
                                    else
                                    {
                                        blnRes=false;
                                        mensajeError ="<HTML>ERROR al momento de realizar el proceso de actualizacion.</HTML>";
                                    }
////                                }
////                               else
////                                    blnRes=false;
////                            }
////                            else
////                                blnRes=false;
                                
                            if (!blnRes)
                            {
                                connZAF.rollback();
                                conLoc.rollback();
                            }
////                        }
////                        else
////                        {
////                            connZAF.rollback();
////                            conLoc.rollback();
////                            mostrarMsgInf("No se puede realizar la insercion del historico:cabecera");
////                        }                    
                 }
                 else
                 {
                     mostrarMsgInf(mensajeError);
                     conLoc.rollback();
                     conLoc.rollback();
                 }
                 
                 conLoc.close();
                 conLoc=null;
                 connZAF.close();
                 connZAF=null;                 
             }            
            
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
                objUti.mostrarMsgErr_F1(this, Evt);
        }
        
////sds

    }//GEN-LAST:event_butGuardarActionPerformed
////    private boolean guardarTbmInv(java.sql.Connection conZAF)
////    {
////        boolean blnRes= false;
////        String strZAF ="";
////        try
////        {
////            if (conZAF!=null)
////            {
////                strZAF="";
////                strZAF+=" \n UPDATE tbm_inv ";
////                strZAF+=" \n SET ITEMDESC=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
////////                            strSQLGP+=", ITMSHNAM=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_GEN),1);03/2024
////                strZAF+=" \n , DEX_ROW_TS='" + fecha +"' \n";
////                strZAF+=" \n  WHERE ITEMNMBR="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3),1) +"";
////                strZAF+="; ";                
////            }
////        }
////        catch (java.sql.SQLException e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    private void chk_mostrarDetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_mostrarDetaActionPerformed
        // TODO add your handling code here:
        if (objTblMod.getRowCount()>0)
        {
            if (txtIdGrupo.getText().length()>0)
            {
                if (objTblMod.getRowCount()>0)
                    llenarTablaGrupo(1);           
            }
            else
                mostrarMsgInf("<HTML>Se debe seleccionar un grupo</HTML>");            
        }  
            else
                mostrarMsgInf("<HTML>Se debe seleccionar un grupo</HTML>");         


        
        
        
        
    }//GEN-LAST:event_chk_mostrarDetaActionPerformed

    private void cmbTipoClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoClaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTipoClaActionPerformed
   //aqui debe ser 
private boolean modificar_tbmInv(java.sql.Connection conExt,int codigoGlo,java.sql.Connection connZAF)
    {
        String strSQLGlobal="",strZAFGlobal="";
        int contador=0;
        int ordenamiento=0;
        boolean blnRes=true;
        String codigo="";
////        java.sql.Connection connZAF;
        java.sql.Statement stmZAF;
        String strZAF ="";
        try{
            
            
            if (conExt!=null)
            {
                stm=conExt.createStatement();

////                stmZAF=connZAF.createStatement();
////                objTblMod.removeEmptyRows();
////                ordenarDatPorOrden();
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
                for (int i=0; i<intNumFil;i++)
                {
                    codigo =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_ITEM));
                 
                    if  (codigo !="Null")
                    {
                        nd_orden =i+1;
////                        strZAF="";
                        strSQL="";

                        if (nd_orden ==1)
                        {
////                            strZAF+="\n UPDATE tbm_inv set co_tipcla = "+nd_tipoConfig +" ,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod ='"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_ITEM) +"';";                        
                            strSQL+="\n UPDATE tbm_inv set co_tipcla = "+nd_tipoConfig +" ,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_ITEM) +"'";                        
                            strSQLGlobal += strSQL;
                            break;
                        }
                        
////                        strZAFGlobal +=strZAF;
                    }

                     
                }
                if (blnRes ==true)
                {
                     System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                     stm.executeUpdate(strSQLGlobal);
////                     stmZAF.executeUpdate(strZAFGlobal);
                }

                stm.close();
                stm=null;
////                stmZAF.close();
////                stmZAF=null;                
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


    
    private boolean modificar_tbmCabGrupo(java.sql.Connection conExt,int codigoGrupo)//
    {
        boolean blnRes=true;
        String cadenaCabecera ="";
        String tipoconfi ="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
//////                int nd_orden =0;
//////                
//////                for (int i=0; i<intNumFil;i++)
//////                {
//////                    if (i==0)
//////                    {
//////                        cadenaCabecera =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)+ "-"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM));    
//////                        break;
//////                    }
//////                    
//////                }
            //modificar
                strSQL="";
                strSQL+="UPDATE  tbm_cabGrpInv set  co_usrmod = " + objParSis.getCodigoUsuario() + " ,fe_ultmod = '"+strFecUltMod +"'," ;
                tipoconfi =cmbTipoCla.getSelectedItem().toString() ;
                if (tipoconfi.equals("UNIDAD"))
                {
                    nd_tipoConfig=0;
                    strSQL+="        co_tipcla = "+nd_tipoConfig  +"";
                }
                     
                else
                {
                    nd_tipoConfig=1;
                    if (tipoconfi.equals("PESO"))
                         strSQL+="        co_tipcla = "+nd_tipoConfig  +""; 
                    else 
                        strSQL+="        co_tipcla = null"; 
                }
                
             
                strSQL+="  WHERE      co_grpInv = "+codigoGrupo +";";
                System.out.println("modificar_tbmCabGrupo: " + strSQL);
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
    
    private int obtenerIdTipoAgrup(String tipo)
    {
        java.sql.Statement stmLoc1 = null;
        java.sql.ResultSet rstLoc1 = null;
        java.sql.Connection conExt1;        
        int idTipo=-1;
        try{
    ////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

            if (conExt1!=null){
                stmLoc1=conExt1.createStatement();
              strSQL="";
                strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar ";
                strSQL+=" FROM Dynamics2021..tbm_tipAgr ";
                strSQL+=" WHERE  st_reg ='A' and tx_nom ="+ objUti.codificar(tipo)  +"";                

                System.out.println("obtenerIdTipoAtri: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next())
                    idTipo=rstLoc1.getInt("co_tipAgr");
            }
            rstLoc1.close();
            stmLoc1.close();
            conExt1.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt1=null;
        }
        catch (java.sql.SQLException e){
            idTipo=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            idTipo=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return idTipo;
    }
    public void mostrarGlobal ()
{
    butConsultar.doClick();
}
    
    

    private boolean ValidaExistePeriodo()//chiqui
    {
        int intPosRel;
        boolean blnRes=false;
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stm=con.createStatement();
              strSQL="";
                strSQL+=" SELECT RTRIM(ne_perAnuFlu) as periodo, RTRIM(tx_codLis) as listAfe, rtrim(tx_codLisApl) as listOrig,st_reg";
                strSQL+=" FROM tbm_cfgFlu as flu ";
                strSQL+=" WHERE  flu.st_reg ='A' and flu.ne_perAnuFlu ='"+ txtDescGru.getText() +"'";                
                
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                    blnRes=true;
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
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
    


    private boolean llenarTablaGrupo(int tipo)//chiqui_a
    {
        boolean blnRes=false;
        try{
            if (tipo ==0)
            {
                if(cargarDetReg(tipo))
                    blnRes=true;
                    
            }
            else
            {
                 if(cargarCabReg(tipo))
                 {
                     if(cargarDetReg(tipo))
                     {
                         blnRes=true;
                     }
                 }
            }
////            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        
        return blnRes;
    }
    
    private boolean limpiarFrm()//chiqui_anterior
    {
        boolean blnRes=true;
        try{
            txtDescGru.setText("");
            txtIdGrupo.setText("");
            objTblMod.removeAllRows();
            txtItem.setText("");
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarDetReg(int tipo)//chiqui
    {
        boolean blnRes=false, validaTodos=false;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
            
            if(tipo ==1)
            {
                if (!txtIdGrupo.getText().equals(""))
                    blnRes =true;
                else
                    blnRes =false;
            }
            else
                 blnRes =true;
            if ( blnRes ==true)
            {
////                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT det.co_grpInv as codigo ,RTRIM(iv.ITEMDESC) as descripcion,det.tx_codItm as item,CAST(p.DATA AS decimal(10, 2)) AS peso , \n";
                    strSQL+="        CAST(pre.UOMPRICE AS decimal(10, 2)) AS precio,TRIM(iv.ITMGEDSC) as tresL, cab.co_tipAgr as tipoCod, \n";
                    strSQL+="        det.st_mosItmLisPre as estado\n";
////                    strSQL+="        case  when det.co_facModPre IS NULL OR det.co_facModPre = 0 \n";
////                    strSQL+="        THEN '' \n";                    
////                    strSQL+="        ELSE CONCAT(fac.co_facModPre,'-',trim(fac.tx_desCor),'-',fac.nd_fac)  \n"; 
////                    strSQL+="        END as desfactor  \n";       
                    
                    strSQL+=" FROM Dynamics2021..tbm_detGrpInv as det "; 
                    strSQL+="  inner join Dynamics2021..tbm_cabGrpInv as cab on (cab.co_grpInv = det.co_grpInv) \n ";
                    strSQL+="  left join  "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv on (iv.ITEMNMBR =det.tx_codItm) \n ";
                    strSQL+="  left join  "+ objParSis.getNombreBaseDatosGP()  +"..[ALT_MIS_INFOADD] as p on (p.MASTERID =det.tx_codItm) \n ";
                    strSQL+="  left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as pre on (pre.ITEMNMBR = det.tx_codItm) \n "; 
                    //strSQL+="  left join Dynamics2021..tbm_facModPre  as fac on (det.co_facModPre = fac.co_facModPre) \n ";                     
                    strSQL+="  WHERE cab.st_reg ='A' and pre.PRCLEVEL ='GENERAL'  \n";
                    strSQL+="  AND   cab.co_tipAgr ="+ tipoAgrupa  +" and iv.inactive=0\n";
                    if (!chk_mostrarDeta.isSelected())
                    {
                        if (tipoAgrupa ==1)
                        {
                            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CHK_MOS, tblDatGru);
                            strSQL+="  AND det.st_mosItmLisPre ='S' \n";
                        }
                          
                        else
                        {
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_MOS, tblDatGru);
//                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_CONF, tblDatGru);
                        }

                    }    
                       
////                    else
////                        strSQL+="  AND det.st_mosItmLisPre  IS NULL \n";
                    
                    if (tipo ==1)
                    {
                        strSQL+=" AND det.co_grpInv = "+ txtIdGrupo.getText() +"  \n";
                    }   
                    
                    
                    strSQL+=" ORDER BY det.co_grpInv,det.co_reg asc";
                    
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    
                    int codigo1=0,codigo2=0,contador=0;
                    while (rst.next()){
                        
                       if (contador ==0)
                        {
                            codigo1 = Integer.parseInt(rst.getString("codigo")) ;
                            codigo2 =codigo1;
                        }
                        else
                        {
                            codigo2=Integer.parseInt(rst.getString("codigo"));
                            if (codigo1 != codigo2)
                            {
                                vecReg=new Vector();
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                vecReg.add(INT_TBL_DAT_CODIGO,   "");
                                vecReg.add(INT_TBL_DAT_ITEM,    "");                                
                                vecReg.add(INT_TBL_DAT_COD3L,   "");                                
                                vecReg.add(INT_TBL_DAT_DESCRIPCION,    "");
                                vecReg.add(INT_TBL_DAT_PESO,    "");
                                vecReg.add(INT_TBL_DAT_PRECIO,    "");
                                vecReg.add(INT_TBL_DAT_MODIFICAR, "..");
                                vecReg.add(INT_TBL_DAT_TIPO,    "");
                                vecReg.add(INT_TBL_DAT_CHK_MOS,    "");
                                //LLENADO DE LA CONUSLTA
                                vecDat.add(vecReg);
                                //objTblMod.insertRow();
                                codigo1=codigo2;
                            }
                                
                        }
                        vecReg=new Vector();

                //segundo query fin
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CODIGO,    rst.getString("codigo"));
                        vecReg.add(INT_TBL_DAT_ITEM,    rst.getString("item"));      
                        vecReg.add(INT_TBL_DAT_COD3L,    rst.getString("tresL"));      
                        vecReg.add(INT_TBL_DAT_DESCRIPCION,    rst.getString("descripcion"));
                        vecReg.add(INT_TBL_DAT_PESO,     rst.getString("peso"));
                        vecReg.add(INT_TBL_DAT_PRECIO,    rst.getString("precio"));
                        vecReg.add(INT_TBL_DAT_MODIFICAR, "..");
                        vecReg.add(INT_TBL_DAT_TIPO,    rst.getString("tipoCod"));
                        if (rst.getString("estado").equals("S"))
                            vecReg.add(INT_TBL_DAT_CHK_MOS,Boolean.TRUE);
                        else
                            vecReg.add(INT_TBL_DAT_CHK_MOS,Boolean.FALSE);
                        
                        //LLENADO DE LA CONUSLTA
                        vecDat.add(vecReg);
                        
                        contador =contador+1;
                        
                    }
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDatGru.setModel(objTblMod);
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
    
    private boolean cargarCabReg(int tipo)//chiqui
    {
        boolean blnRes=true;
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stm=con.createStatement();
              strSQL="";
                strSQL+=" SELECT RTRIM (co_grpInv) codigo, RTRIM(tx_nomGrpInv) as descripcion,TRIM(tx_itmPad) AS itm,isnull(co_tipcla,-1) as co_tipcla";
                strSQL+=" FROM tbm_cabGrpInv ";
                if (tipo==0)
                        strSQL+=" WHERE  st_reg ='A' ";           
                else    
                        strSQL+=" WHERE  st_reg ='A' and tx_nomGrpInv ="+ objUti.codificar(txtDescGru.getText())  +" and co_grpInv ="+ txtIdGrupo.getText() +" and co_tipAgr ="+ tipoAgrupa +"";           
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    if (tipo==0)
                    {
                        txtIdGrupo.setText("");
                        txtDescGru.setText("");
                    }
                    strAux=rst.getString("codigo");
                    txtIdGrupo.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("descripcion");
                    txtDescGru.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_tipcla");
                    if (strAux.equals("0"))
                        cmbTipoCla.setSelectedItem("UNIDAD");                    
                    else
                    {
                        if (strAux.equals("1"))
                            cmbTipoCla.setSelectedItem("PESO");                        
                        else
                            cmbTipoCla.setSelectedItem("NINGUNO");                    
                    }
                        
////                    lblTit.setText(objParSis.getNombreMenu() + strVersion +"  "+ ((strAux==null)?"":strAux ));                   
                }
                else{
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
    


    

    

    
        
    



    private boolean llenarTablaNuevo(int semanas)//chiqui_antiguo
    {
        boolean blnRes=true;
////        double valorFactor =0;
////        try{
////            if (semanas> 0){
////                    vecDat.clear();
////                    arlDatConDoc= new ArrayList();
////                     for (int i=0; i<semanas;i++)
////                     {
////                        vecReg=new Vector();
////                         vecReg.add(INT_TBL_DAT_LIN,"");
////                        vecReg.add(INT_TBL_DAT_PERIODO,   txtDescGru.getText());
////                        vecReg.add(INT_TBL_DAT_SEMANA,    i+1);
////                        vecReg.add(INT_TBL_DAT_LISTA_AFECTADA,    txtLstAfectar.getText());
////                        vecReg.add(INT_TBL_DAT_LISTA_ORIGEN,    txtLstOrigen.getText());
////                        valorFactor =generarRandom (Double.parseDouble(txtValMin.getText()),Double.parseDouble(txtValMax.getText()),i);
////                        vecReg.add(INT_TBL_DAT_FACTOR,   valorFactor);
////                        vecReg.add(INT_TBL_DAT_EJECUTADA,  "N");
////                        vecReg.add(INT_TBL_DAT_FECH_EJECUCION,        "");
////                        vecReg.add(INT_TBL_DAT_DESDE,    txtValMin.getText()    );
////                        vecReg.add(INT_TBL_DAT_HASTA,        txtValMax.getText());
////                        vecReg.add(INT_TBL_DAT_FACTOR_RES,   valorFactor);
////                        vecReg.add(INT_TBL_DAT_MODIFICAR,   "N");
////                        vecDat.add(vecReg);
////                     }
////                    
////                    //Asignar vectores al modelo.
////                    objTblMod.setData(vecDat);
////                    tblDat.setModel(objTblMod);
////                    vecDat.clear();
////            }
////            else
////                blnRes=false;
////        }
////        catch(Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
        return blnRes;
    }
    
   private boolean validacionExiste ()//chiqui_antiguo
   {
        boolean blnRes=false;
////        java.sql.ResultSet rstLoc;
////        java.sql.Statement stmLoc;
////        try{
////////            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////             con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            
////            if (con!=null){
////                //con=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
////                //Validar que sólo se muestre los documentos asignados al programa.
////                stmLoc = con.createStatement();
////                strSQL="";
////                strSQL+="SELECT rtrim(tx_codLis) AS listaPrecio,rtrim(tx_codLis) AS descripcion FROM tbm_cfgFlu ";
////                strSQL+=" WHERE ne_perAnuFlu =" + txtDescGru.getText() + "";
////                strSQL+=" AND  tx_codLis ='" + txtLstAfectar.getText() + "'";
////                strSQL+=" AND  tx_codLisApl ='" + txtLstOrigen.getText() + "'";
////                strSQL+=" AND st_reg  ='A'";
////                rstLoc = stmLoc.executeQuery(strSQL);
////                while(rstLoc.next()){
////                    blnRes=true;
////                }
////                stmLoc.close();
////                stmLoc=null;
////                rstLoc.close();
////                rstLoc=null;
////                con.close();
////                con=null;
////            }
////        }
////        catch (java.sql.SQLException e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
        return blnRes;
        
   }
   
    private boolean guardarFlutuaciones()//chiqui_antiguo
    {
////        java.sql.Connection conLoc;
        boolean blnRes=false;     
////        int semanaxAni =0, semanaActAnio=0, anioActual=0;
////        Date date;
////        Calendar calendario = Calendar.getInstance(); // Inicializamos Calendar con la fecha y hora actual
////    // y asignamos la variable date Date al Calendar de esta forma
////        calendario.setTime(datFecDoc);
////        semanaxAni = calendario.get(Calendar.WEEK_OF_YEAR);
////        semanaActAnio = Integer.parseInt(obtenerSemaActualAnio(datFecDoc.toString())) ;
////        semanaActAnio =semanaActAnio+1;
////        anioActual =objUti.getAnio(datFecDoc.toString(),"yyyy-MM-dd");
////        try
////        {
////            lblMsgSis.setText("Guardando datos...");
////            int intNumFil=objTblMod.getRowCountTrue();
////            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////
////                if (estado =='C')
////                {
////                    for (int i=0; i<intNumFil;i++)
////                    {
////                           if ((objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")) && (objTblMod.getValueAt(i, INT_TBL_DAT_MODIFICAR).toString()=="M"))
////                            {
////                                if ((semanaActAnio <= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_SEMANA).toString())) && 
////                                    (anioActual>= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PERIODO).toString())))
////                                {
////                                    if (semanaxAni >= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR).toString()) )
////                                    {
////                                          if(conLoc!=null)
////                                            {
////                                                conLoc.setAutoCommit(false);
////                                                if(ProcesoValidaModificacion(conLoc,i))
////                                                {
////                                                         blnRes=true;
////                                                         mostrarMsgInf("<HTML>Registro Actualizado.</HTML>");
////                                                }
////                                                else
////                                                 {
////                                                        blnRes=false;
////                                                        System.err.println("Error actualizarSolicitudAutorizar");
////                                                        break;
////                                                 }   
////                                                if(blnRes==true){
////                                                    conLoc.commit();
////                                                }
////                                                else{
////                                                    conLoc.rollback();
////                                                    break;
////                                                }
////                                                blnRes=false;
////                                            }  
////                                    }
////                                }    
////                                else
////                                  mostrarMsgInf("<HTML>No se puede realizar la modificación , se quiere modificar una semana que no es inferior a la actual</HTML>");  
////                            }
////                        }
////                }
////                else
////                {
////                        if(conLoc!=null)
////                        {
////                            conLoc.setAutoCommit(false);
////                            if(ProcesoValidaInsercion(conLoc))
////                            {
////                                blnRes=true;
////                            }
////                            else
////                            {
////                                blnRes=false;
////                                System.err.println("Error actualizarSolicitudAutorizar");
////                            }   
////                            if(blnRes==true){
////                                conLoc.commit();
////                            }
////                            else{
////                                conLoc.rollback();
////                            }
////
//////                            blnRes=false;
////                        }
////                }
////                conLoc.close();
////                conLoc=null;
////
////            
////        }
////        catch (java.sql.SQLException e) { 
////           objUti.mostrarMsgErr_F1(this, e);  
////       }
////       catch(Exception  Evt){ 
////           objUti.mostrarMsgErr_F1(this, Evt);
////       }
        return blnRes;
    }
    
    
    
   
    
    
////    public LLamadaOtroFormulario()
    
    
     public boolean mostrarGrupos(int intTipBus) //chiqui
    {
        boolean blnRes = true;
        boolean blnExis =false;
        try{
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoGrupo.setCampoBusqueda(1);
                    vcoGrupo.show();
                    if (vcoGrupo.getSelectedButton() == vcoGrupo.INT_BUT_ACE) 
                    {
                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
                        txtDescGru.setText(vcoGrupo.getValueAt(2));
                        txtItem.setText(vcoGrupo.getValueAt(3));
                        txtDescGru.selectAll();
////                        txtLstAfectar.requestFocus();
////                        llenarTablaGrupo(1);
                        txtDescGru.setEditable(false);
                        if (txtIdGrupo.getText().equals("")) 
                        {
                                mostrarGrupos(0);
                        }
                        else
                                mostrarGrupos(1);
                        blnExis=true;

                    }
                    break;
                    
                    
                    
////                    vcoGrupo.setCampoBusqueda(1);
////                    if (vcoGrupo.buscar("codigo", txtIdGrupo.getText()))
////                    {
////                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
////                        txtDescGru.setText(vcoGrupo.getValueAt(2));
////                        txtDescGru.selectAll();
////////                        txtLstAfectar.requestFocus();
////////                        llenarTablaGrupo(1);
////                        txtDescGru.setEditable(false);
////                    }
////                    break;
                    
////                    llenarTablaGrupo(0);
////                    txtDescGru.setText("");
////                    break;
                    
////                    vcoGrupo.setCampoBusqueda(2);
////                    vcoGrupo.show();
////                    if (vcoGrupo.getSelectedButton() == vcoGrupo.INT_BUT_ACE) {
////////                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
////////                        txtDescGru.setText(vcoGrupo.getValueAt(2));
////                        llenarTablaGrupo(0);
////////                        txtNomBod.setText(vcoListPre.getValueAt(2));
////                    }
////                    break;
                    
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoGrupo.buscar("codigo", txtIdGrupo.getText()))
                    {
                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
                        txtDescGru.setText(vcoGrupo.getValueAt(2));
                        txtItem.setText(vcoGrupo.getValueAt(3));
                        txtDescGru.selectAll();
////                        txtLstAfectar.requestFocus();
                        llenarTablaGrupo(1);
                        txtDescGru.setEditable(false);
                        blnExis=true;
                    }else
                        
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoGrupo.buscar("descripcion", txtDescGru.getText()))
                    {
                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
                        txtDescGru.setText(vcoGrupo.getValueAt(2));
                        txtItem.setText(vcoGrupo.getValueAt(3));
                        txtDescGru.selectAll();
////                        txtLstAfectar.requestFocus();
                        llenarTablaGrupo(1);
                        blnExis=true;
                    }
                    break;
                 case 3: //Básqueda directa por "ITEM".
                    if (vcoGrupo.buscar("item", txtItem.getText()))
                    {
                        txtIdGrupo.setText(vcoGrupo.getValueAt(1));
                        txtDescGru.setText(vcoGrupo.getValueAt(2));
                        txtItem.setText(vcoGrupo.getValueAt(3));
                        txtDescGru.selectAll();
////                        txtLstAfectar.requestFocus();
                        llenarTablaGrupo(1);
                        blnExis=true;
                    }
                    break;                    
                 case 4: //
                    llenarTablaGrupo(0);
                    txtDescGru.setText("");
                    txtItem.setText("");
                    //txtIdGrupo.setText("");
                    blnExis=true;
                    break;
            }
            if (!blnExis)
                limpiarFrm();

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }   
        return blnRes;
    }
     
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrFuncionamiento;
    private javax.swing.JButton butCerrar2;
    private javax.swing.JButton butConsultar;
    private javax.swing.JButton butGrupo;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butLimpiar;
    private javax.swing.JCheckBox chk_mostrarDeta;
    private javax.swing.JComboBox<String> cmbTipoAgru;
    private javax.swing.JComboBox<String> cmbTipoCla;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPer;
    private javax.swing.JPanel panTab;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatGru;
    private javax.swing.JTextField txtDescGru;
    private javax.swing.JTextField txtIdGrupo;
    private javax.swing.JTextField txtItem;
    // End of variables declaration//GEN-END:variables

    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            objHisTblBasDat=new ZafHisTblBasDat();
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
            
            configurarTblDat();
            LLenarComboTiposAgrupacion();
            LLenarComboTipoClasificacion();
////            LLenarComboFactores(); 
////            configurarGrupos();            

////            configuraListaPrecio();
////            configuraFlutuacion();
            
                // y asignamos la variable date Date al Calendar de esta forma
////            calendario.setTime(datFecDoc);
////            semanaxAni = calendario.get(Calendar.WEEK_OF_YEAR);//OBTIENE EL NUMERO DE SEMANAS ANUAL
////            semanaActAnio = Integer.parseInt(obtenerSemaActualAnio(datFecDoc.toString())) ;//OBTIENE LA SEMANA ACTUAL ANUAL
////            semanaActAnio =semanaActAnio+1;// OBTIENE LA SEMANA ANUAL SIGUIENTE DE LA FECHA ACTUAL
////            anioActual =objUti.getAnio(datFecDoc.toString(),"yyyy-MM-dd"); //OBTIENE EL AÑO ACTUAL
////            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
private boolean LLenarComboTiposAgrupacion()//chiqui
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
                strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A'  and co_tipAgr =2";
                strSQL+=" UNION";
                strSQL+="  SELECT 0 ,'',''";                
                rstLoc = stmLoc.executeQuery(strSQL);
                dataList.clear();
                while (rstLoc.next()){
                    String data = rstLoc.getString("tx_nom");
                    dataList.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList.toArray(new String[0]));
                    cmbTipoAgru.setModel(model);    
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

private boolean LLenarComboTipoClasificacion()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        dataListTipos.clear();
        try{
            //con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                    String data0 = "NINGUNO";
                    String data1 = "UNIDAD";
                    String data2 = "PESO";
                    dataListTipos.add(data0);
                    dataListTipos.add(data1);
                    dataListTipos.add(data2);
                    blnRes=true;
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataListTipos.toArray(new String[0]));
                    cmbTipoCla.setModel(model);    
                }
        }

        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
     private boolean configurarGrupos()//chiqui_a
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("codigo");
            arlCam.add("descripcion");
            arlCam.add("item");            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Id");
            arlAli.add("Descripcion");
            arlAli.add("Item");            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("300");
            arlAncCol.add("100");            
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT co_grpInv AS codigo,rtrim(tx_nomGrpInv) AS descripcion,trim(tx_itmPad) as item FROM tbm_cabGrpInv where st_reg='A' \n";
                strSQL+=" AND co_tipAgr="+ tipoAgrupa + "";

                    
            }
            else{
                strSQL="";
                strSQL+="SELECT co_grpInv AS codigo,rtrim(tx_nomGrpInv) AS descripcion,trim(tx_itmPad) as item FROM tbm_cabGrpInv where st_reg='A' \n";
                strSQL+=" AND co_tipAgr="+ tipoAgrupa + "";
            }
            //Ocultar columnas.
////            int intColOcu[]=new int[2];
////            intColOcu[0]=5;
////            intColOcu[1]=6;
            vcoGrupo=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Grupos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
////            intColOcu=null;
            //Configurar columnas.
            vcoGrupo.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoGrupo.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoGrupo.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);            



        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
       

  
  
  private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODIGO,"Id");
            vecCab.add(INT_TBL_DAT_ITEM,"Item");            
            vecCab.add(INT_TBL_DAT_COD3L,"3L");            
            vecCab.add(INT_TBL_DAT_DESCRIPCION,"Descripción");
            vecCab.add(INT_TBL_DAT_PESO,"Peso");
            vecCab.add(INT_TBL_DAT_PRECIO,"Precio");
            vecCab.add(INT_TBL_DAT_MODIFICAR,"..");
            vecCab.add(INT_TBL_DAT_TIPO,"Tipo");
            vecCab.add(INT_TBL_DAT_CHK_MOS,"M");
            

    
    
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatGru.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CODIGO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PESO, ZafTblMod.INT_COL_DBL, new Integer(0),null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PRECIO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            tblDatGru.setCellSelectionEnabled(true);
            tblDatGru.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatGru);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatGru.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatGru.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ITEM).setPreferredWidth(85);            
            tcmAux.getColumn(INT_TBL_DAT_COD3L).setPreferredWidth(35);            
            tcmAux.getColumn(INT_TBL_DAT_DESCRIPCION).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_PESO).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_PRECIO).setPreferredWidth(48);
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TIPO).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_MOS).setPreferredWidth(20);
            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatGru.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            
;
            
            

           
            
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_PESO).setCellRenderer(objTblCelRenLblDer);
            tcmAux.getColumn(INT_TBL_DAT_PRECIO).setCellRenderer(objTblCelRenLblDer);   
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_MOS).setCellRenderer(objTblCelRenLblDer);
  
            
                         
            
           
            
            
            

            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_MOS).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
////                    BigDecimal bgdMarUti, bgdMarUtiCos;
////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI))));
////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else
////                    {
////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
////                        {
////                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
////                        else
////                        {
////                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
////                            {
////                                objTblCelRenChk.setBackground(java.awt.Color.RED);
////                            }
////                            else
////                            {
////                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                            }    
////                        }
////                    }
                }
            });
            
            
            
            
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            objTblCelRenButConOcp=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenButConOcp);
            objTblCelRenButConOcp = null;
            
                        objTblCelEdiBtn=new ZafTblCelEdiTxt(tblDatGru);
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellEditor(objTblCelEdiBtn);
////            
            new ButSolTraInv(tblDatGru, INT_TBL_DAT_MODIFICAR); 
            //para ocultar la columna
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIPO, tblDatGru);
            
            
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatGru.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatGru);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatGru);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_MODIFICAR);
                vecAux.add("" + INT_TBL_DAT_CHK_MOS);
////                vecAux.add("" + INT_TBL_DAT_DES_FAC);
            objTblMod.setColumnasEditables(vecAux);                
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatGru);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {

                }
            });
            

            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_CODIGO;

            intColVen=null;
            intColTbl=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatGru);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PESO, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PRECIO, objTblMod.INT_COL_DBL, new Integer(0), null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
  
  
  
private double obtener_ValorFactor( String tipo,int idTipo, int estado)
    {
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        double factor =-1;
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                String[] partes = tipo.split("-");
                strSQL="";
                strSQL+=" SELECT nd_fac as factor \n";
                strSQL+=" FROM  Dynamics2021..tbm_facModPre \n   ";
                strSQL+=" WHERE st_reg ='A' and tx_desCor ="+ objUti.codificar(partes[0]) +"";
                System.out.println("obtener_ValorFactor: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                        factor=rstLoc1.getDouble("factor");
                }
                else{
                   factor =-1;
                }
            rstLoc1.close();
            stmLoc1.close();
            conExt.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt=null;
            
            }
        }
        catch (java.sql.SQLException e)
        {
             factor =-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
             factor =-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return factor;
    }      

     
    
      private class ButDetComVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButDetComVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Detalle comisiones de venta.");
           
        }
        @Override
        public void butCLick() {
         // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarDetComVen();
        }
    }
      
         
      private class ButSolTraInv extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButSolTraInv(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Creacion de Grupo de Inventario");
        }

        public void butCLick()
        {
            llamarDetComVen();
        }
    }
     
    private void llamarDetComVen(){
        try{

        int strCodDoc =   Integer.parseInt(tblDatGru.getValueAt(tblDatGru.getSelectedRow(), INT_TBL_DAT_CODIGO).toString()) ;
        String strDescDoc = tblDatGru.getValueAt(tblDatGru.getSelectedRow(), INT_TBL_DAT_DESCRIPCION).toString();
        int strTipo =   Integer.parseInt(tblDatGru.getValueAt(tblDatGru.getSelectedRow(), INT_TBL_DAT_TIPO).toString()) ;        
        

////////        Maestros.ZafMae88.ZafMae88_01 obj = new Maestros.ZafMae88.ZafMae88_01(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis, strCodDoc, strDescDoc);
////////        Maestros.ZafMae88.ZafMae88_02 obj = new Maestros.ZafMae88.ZafMae88_02(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis, strCodDoc, strDescDoc,1);        
////    Maestros.ZafMae85.ZafMae85 obj1 = new Maestros.ZafMae85.ZafMae85(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis,strTipo,strDescDoc,strCodDoc);             
////////            public ZafMae88_1(ZafParSis obj, String idGrupo, String descripcion)
////        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
////        obj1.show();
////        if (obj1.acepta())
////            mostrarGlobal();
              

////        Maestros.ZafMae88.ZafMae88_04 obj = new Maestros.ZafMae88.ZafMae88_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis,strTipo,strDescDoc,strCodDoc);        
////        obj.show();
////        if (obj.acepta())
////        {
////            validaOk=true;
////            mostrarGlobal();
////        }
////        else
////           validaOk=false;
//// 
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        
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
////        public void run()
////        {
////            
////            if (!cargarDetReg()){
////                //Inicializar objetos si no se pudo cargar los datos.
////                lblMsgSis.setText("Listo");
////                pgrSis.setValue(0);
////                butConsultar.setText("Consultar");
////            }
////            
////            
////            //Establecer el foco en el JTable sólo cuando haya datos.
//////            if (tblDat.getRowCount()>0)
//////            {
//////                tabFrm.setSelectedIndex(1);
//////                tblDat.setRowSelectionInterval(0, 0);
//////                tblDat.requestFocus();
//////            }
////            objThrGUI=null;
////        }
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
            int intCol=tblDatGru.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_CODIGO: strMsg="Código del Grupo"; break;
                case INT_TBL_DAT_DESCRIPCION: strMsg="Nombre del Grupo"; break;
                case INT_TBL_DAT_ITEM: strMsg="Código del Item"; break;
                case INT_TBL_DAT_COD3L: strMsg="Código 3L"; break;                
                case INT_TBL_DAT_PESO: strMsg="Peso de Item"; break;
                case INT_TBL_DAT_PRECIO: strMsg="Precio del Item"; break;
                case INT_TBL_DAT_TIPO: strMsg="Tipo de Item"; break;
                case INT_TBL_DAT_CHK_MOS: strMsg="Mostrar Lista"; break;
                default: strMsg=""; break;
                        
            }
            tblDatGru.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
