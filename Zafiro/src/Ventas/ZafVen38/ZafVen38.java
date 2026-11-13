/*
 * ZafCom18.java
 *
 * Created on 05 de enero de 2006, 10:10 PM
 */
package Ventas.ZafVen38;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
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
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author José Marín 
 */
public class ZafVen38 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                          
    static final int INT_TBL_COD_EMP=1;                      
    static final int INT_TBL_COD_LOC=2;                      
    static final int INT_TBL_COD_CLI=3;                    
    static final int INT_TBL_IDE_CLI=4;                   
    static final int INT_TBL_NOM_CLI=5;       
    
    static final int INT_TBL_COD_VEN=6;                    
    static final int INT_TBL_NOM_VEN=7;                    
    
    static final int INT_TBL_ST_ACT=8;                     
    static final int INT_TBL_CHK_ACT=9;           
    static final int INT_TBL_ST_NUE=10;
    static final int INT_TBL_CHK_NUE=11;
    
    static final int INT_TBL_ST_CAL=12;
    
     private String strVersion="v 0.04";
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti; 
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
    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed;        //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
     
     
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
    
    private ZafVenCon vcoLoc;                           //Ventana de consulta <jota>
    private String strCodLoc, strNomLoc;
   
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    
   
    /** Crea una nueva instancia de la clase ZafCom18. */
    public ZafVen38(ZafParSis obj){
        try{
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            initComponents();
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
        chkCliNue = new javax.swing.JCheckBox();
        chkCliSinCam = new javax.swing.JCheckBox();
        chkCliEli = new javax.swing.JCheckBox();
        lblVen1 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbAnio = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbMes = new javax.swing.JComboBox<>();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
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

        chkCliNue.setSelected(true);
        chkCliNue.setText("Clientes nuevos");
        chkCliNue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                chkCliNueFocusLost(evt);
            }
        });
        chkCliNue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliNueActionPerformed(evt);
            }
        });
        panFil.add(chkCliNue);
        chkCliNue.setBounds(160, 120, 130, 20);

        chkCliSinCam.setSelected(true);
        chkCliSinCam.setText("Clientes sin Cambios");
        chkCliSinCam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                chkCliSinCamFocusLost(evt);
            }
        });
        chkCliSinCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliSinCamActionPerformed(evt);
            }
        });
        panFil.add(chkCliSinCam);
        chkCliSinCam.setBounds(290, 120, 160, 20);

        chkCliEli.setSelected(true);
        chkCliEli.setText("Clientes eliminados");
        chkCliEli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                chkCliEliFocusLost(evt);
            }
        });
        chkCliEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCliEliActionPerformed(evt);
            }
        });
        panFil.add(chkCliEli);
        chkCliEli.setBounds(450, 120, 180, 20);

        lblVen1.setText("Local");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen1);
        lblVen1.setBounds(20, 30, 40, 20);

        txtCodEmp.setEditable(false);
        txtCodEmp.setEnabled(false);
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(70, 30, 20, 20);

        txtCodLoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
        lblVen.setBounds(20, 50, 80, 20);

        txtCodVen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
        txtCodVen.setBounds(90, 50, 60, 20);

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
        txtNomVen.setBounds(150, 50, 460, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(610, 50, 20, 20);

        jLabel1.setText("Cambios:");
        panFil.add(jLabel1);
        jLabel1.setBounds(50, 120, 80, 14);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(20, 70, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(20, 90, 400, 20);

        jLabel2.setText("Periodo:");
        panFil.add(jLabel2);
        jLabel2.setBounds(20, 10, 70, 14);

        jLabel3.setText("Año:");
        panFil.add(jLabel3);
        jLabel3.setBounds(90, 10, 50, 14);
        panFil.add(cmbAnio);
        cmbAnio.setBounds(120, 5, 60, 20);

        jLabel4.setText("Mes:");
        panFil.add(jLabel4);
        jLabel4.setBounds(200, 10, 50, 14);

        cmbMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panFil.add(cmbMes);
        cmbMes.setBounds(240, 5, 90, 20);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkCliEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliEliActionPerformed
   
    }//GEN-LAST:event_chkCliEliActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        boolean blnConsultar=false;
        limpiarDetFrm();
        if(consultaMesPresente()){
            blnConsultar=true;
        }
        else{
            if(txtCodVen.getText()!=null){
                if(txtCodVen.getText().length()>0){
                    blnConsultar=true;
                }
                else{
                    blnConsultar=false;
                    mostrarMsgInf("Para consultas de meses anteriores solo se puede consultar si elige un vendedor en particular (Temporalmente - Sistemas)");
                }
            }
            else{
                blnConsultar=false;
                 mostrarMsgInf("Para consultas de meses anteriores solo se puede consultar si elige un vendedor en particular (Temporalmente - Sistemas)");
            }
        }
        
        if(blnConsultar){
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
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkCliNueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliNueActionPerformed
        
}//GEN-LAST:event_chkCliNueActionPerformed

    private void chkCliSinCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCliSinCamActionPerformed
     
    }//GEN-LAST:event_chkCliSinCamActionPerformed

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
         
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // configurarVenConVen();
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
         
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
        
    }//GEN-LAST:event_txtCodLocFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()){
            txtCodVen.setText("");
            txtNomVen.setText("");
            chkCliNue.setSelected(true);
            chkCliSinCam.setSelected(true);
            chkCliEli.setSelected(true);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void chkCliNueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chkCliNueFocusLost
        // TODO add your handling code here:
        if(chkCliNue.isSelected()){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_chkCliNueFocusLost

    private void chkCliSinCamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chkCliSinCamFocusLost
        // TODO add your handling code here:
        if(chkCliSinCam.isSelected()){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_chkCliSinCamFocusLost

    private void chkCliEliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_chkCliEliFocusLost
        // TODO add your handling code here:
        if(chkCliEli.isSelected()){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_chkCliEliFocusLost

    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkCliEli;
    private javax.swing.JCheckBox chkCliNue;
    private javax.swing.JCheckBox chkCliSinCam;
    private javax.swing.JComboBox<String> cmbAnio;
    private javax.swing.JComboBox<String> cmbMes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomLoc;
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
            //Configurar objetos.
            txtCodEmp.setVisible(false);    
            //Configurar los JTables.
            configurarTblDat();
            configurarLocal();
            configurarComboAniosSistema();
            configurarComboMes();
            configurarVenConVen();
            if(objParSis.getCodigoUsuario()!=1){
                cargarVendedoresAsignadosPorUsuario();
            }
        }
        catch(Exception e){
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
        try{
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
            
            vecCab.add(INT_TBL_COD_VEN,"Cód.Ven.");
            vecCab.add(INT_TBL_NOM_VEN,"Vendedor");
            
                    
            vecCab.add(INT_TBL_ST_ACT,"st_actual");
            vecCab.add(INT_TBL_CHK_ACT,"Actual");
            vecCab.add(INT_TBL_ST_NUE,"st_nuevo");
            vecCab.add(INT_TBL_CHK_NUE,"Nuevo");
            vecCab.add(INT_TBL_ST_CAL,"CAL");
            
            
             
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
            tcmAux.getColumn(INT_TBL_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_IDE_CLI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(200);
            
            tcmAux.getColumn(INT_TBL_COD_VEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOM_VEN).setPreferredWidth(200);
            
            tcmAux.getColumn(INT_TBL_ST_ACT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CHK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_ST_NUE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CHK_NUE).setPreferredWidth(60); 
            
            tcmAux.getColumn(INT_TBL_ST_CAL).setPreferredWidth(30); 
            
            // 
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_LOC, tblDat);     
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_VEN, tblDat);     
            objTblMod.addSystemHiddenColumn(INT_TBL_IDE_CLI, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_ST_ACT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_ST_NUE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_ST_CAL, tblDat);
            
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
            tcmAux.getColumn(INT_TBL_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_COD_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_VEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;               
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLbl.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    objTblCelRenLbl.setBackground(Color.WHITE);
//                    if (objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_ST_CAL)).equals("NUEVA")){
//                       objTblCelRenLbl.setBackground(colFonColCre);
//                    }
//                    else {
//                       objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
                    if(objTblMod.getValueAt(intFil,INT_TBL_CHK_ACT).equals(true) && objTblMod.getValueAt(intFil,INT_TBL_CHK_NUE).equals(false)){
                        objTblCelRenLbl.setBackground(Color.RED);
                    }
                    else if(objTblMod.getValueAt(intFil,INT_TBL_CHK_ACT).equals(false) && objTblMod.getValueAt(intFil,INT_TBL_CHK_NUE).equals(true)){
                        objTblCelRenLbl.setBackground(colFonColCre);
                    }
                    else{
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }


                }
            });
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHK_ACT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_CHK_NUE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
               java.awt.Color colFonColCre;
               public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    int intFil = -1;
                    intFil = objTblCelRenChk.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
//                    if (objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_ST_CAL)).equals("NUEVA")){
//                        objTblCelRenChk.setBackground(colFonColCre);
//                    }
//                    else {
//                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
                    if(objTblMod.getValueAt(intFil,INT_TBL_CHK_ACT).equals(true) && objTblMod.getValueAt(intFil,INT_TBL_CHK_NUE).equals(false)){
                        objTblCelRenChk.setBackground(Color.RED);
                    }
                    else if(objTblMod.getValueAt(intFil,INT_TBL_CHK_ACT).equals(false) && objTblMod.getValueAt(intFil,INT_TBL_CHK_NUE).equals(true)){
                        objTblCelRenChk.setBackground(colFonColCre);
                    }
                    else{
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
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
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
       
    //Arreglos: Datos Usuario
    private ArrayList arlDat, arlReg;
 
     
    /**
     * La fecha de prueba ( 2018/Jun/22 )
     *  MES 5 - AÑO 2018
     *  AnioUti ==> 2018
     *  MesUti ==> 5
     * @return 
     */
    
    private boolean consultaMesPresente(){
        boolean blnRes=false;
        try{
            int intMes, intAnio;
            intMes = cmbMes.getSelectedIndex();
            intAnio = Integer.parseInt(cmbAnio.getSelectedItem().toString());
//            System.out.println("MES " + intMes+" - AÑO " + intAnio);
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//            System.out.println("AnioUti ==> " + objUti.parseString(objUti.getAnio(datFecAux)));     
//            System.out.println("MesUti ==> " + objUti.parseString(objUti.getMes(datFecAux))); 
            if( (intMes == Integer.parseInt(objUti.parseString(objUti.getMes(datFecAux))) ) && (intAnio == Integer.parseInt(objUti.parseString(objUti.getAnio(datFecAux))) )  ){
                blnRes=true;
            }
            datFecAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que carga los usuarios que tienen asignados vistos buenos.
     * @return 
     */ 
    private boolean cargarDatos(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(consultaMesPresente()){
                    strSql ="";
                    strSql+=" SELECT a1.co_emp,a2.co_loc , a1.co_cli, a1.tx_ide, a1.tx_nom as tx_nomCli, a1.st_reg,  \n";
                    strSql+="        a2.co_ven as co_venAntes, a2.tx_usr as tx_usrAntes, a2.tx_nom as tx_nomAntes,    \n";
                    strSql+="        a3.co_cfg, a3.co_usrVen as co_venNuevo, a3.tx_usr as tx_usrNuevo, a3.tx_nom as tx_nomNuevo, \n";
                    strSql+="        CASE WHEN a2.co_ven IS NULL AND a3.co_usrVen IS NOT NULL THEN 'NUEVA' ELSE  \n";
                    strSql+="           CASE WHEN a2.co_ven IS NOT NULL AND a3.co_usrVen IS NOT NULL THEN  \n";
                    strSql+="               CASE WHEN a2.co_ven=a3.co_usrVen THEN 'IGUAL' ELSE 'NUEVA' END  \n";
                    strSql+="                   ELSE CASE WHEN a2.co_ven IS NOT NULL AND a3.co_usrVen IS NULL THEN 'IGUAL' END END END AS analisis \n";
                    strSql+=" FROM tbm_cli as a1 \n";
                    strSql+=" LEFT OUTER JOIN ( \n";
                    strSql+="       SELECT a1.co_emp,a2.co_loc, a1.co_cli, a2.co_ven, a3.tx_usr, a3.tx_nom  \n";
                    strSql+="       FROM tbm_cli as a1  \n";
                    strSql+="       INNER JOIN tbr_cliLoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                    strSql+="       LEFT OUTER JOIN tbm_usr as a3 ON (a2.co_ven=a3.co_usr) \n";
                    strSql+="       WHERE a1.st_reg='A' ";
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        strSql+=" AND a2.co_emp="+objParSis.getCodigoEmpresa() +" AND a2.co_loc="+objParSis.getCodigoLocal(); 
                    }
                    if(txtCodEmp.getText().length()>0){
                        strSql+=" AND a2.co_emp="+txtCodEmp.getText();
                    }
                    if(txtCodLoc.getText().length()>0){
                        strSql+=" AND a2.co_loc="+txtCodLoc.getText();
                    }
                    strSql+=" \n";
                    strSql+=" ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                    strSql+=" LEFT OUTER JOIN ( \n";
                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_cli, a1.co_cfg, a1.co_usrVen, a3.tx_usr, a3.tx_nom \n";
                    strSql+="       FROM tbm_preCfgCliLoc as a1  \n";
                    strSql+="       INNER JOIN (   \n";
                    strSql+="           SELECT a3.co_emp, a3.co_loc, a3.co_cli, MAX(a3.co_cfg) as co_cfg  \n";
                    strSql+="           FROM tbm_preCfgCliLoc as a3   \n";
                    strSql+="           WHERE 1=1 ";
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        strSql+=" AND a3.co_emp="+objParSis.getCodigoEmpresa() +" AND a3.co_loc="+objParSis.getCodigoLocal(); 
                    }
                    if(txtCodEmp.getText().length()>0){
                        strSql+=" AND a3.co_emp="+txtCodEmp.getText();
                    }
                    if(txtCodLoc.getText().length()>0){
                        strSql+=" AND a3.co_loc="+txtCodLoc.getText();
                    }
                    strSql+=" \n";
                    strSql+="           GROUP BY  a3.co_emp, a3.co_loc, a3.co_cli   \n";
                    strSql+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND a1.co_cfg=a2.co_cfg) \n";
                    strSql+="       INNER JOIN tbm_usr as a3 ON (a1.co_usrVen=a3.co_usr) \n";
                    strSql+=" ) as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)  \n";
                    strSql+=" WHERE (a2.co_ven IS NOT NULL OR a3.co_cfg IS NOT NULL ) AND a1.st_reg='A'   \n";
                    if(txtCodVen.getText().length()>0){
                        strSql+="       AND (a2.co_ven = "+txtCodVen.getText()+" OR a3.co_usrVen = "+txtCodVen.getText()+" ) ";
                    }
                    else{
                        if(objParSis.getCodigoUsuario()!=1){
                            strSql+="       AND ( a2.co_ven IN ( \n";
                            strSql+="                       SELECT co_usrRel \n";
                            strSql+="                       FROM tbr_usrLocPrgUsr  \n";
                            strSql+="                       WHERE co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_usr="+objParSis.getCodigoUsuario()+" \n";
                            strSql+=" AND co_mnu="+objParSis.getCodigoMenu()+" ";
                            strSql+="                       ) \n";
                            strSql+="        OR a3.co_usrVen IN (  \n";
                            strSql+="                               SELECT co_usrRel \n";
                            strSql+="                               FROM tbr_usrLocPrgUsr  \n";
                            strSql+="                               WHERE co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_usr="+objParSis.getCodigoUsuario()+" \n";
                            strSql+=" AND co_mnu="+objParSis.getCodigoMenu()+" ";
                            strSql+="                           ) \n";
                            strSql+="           ) \n";
                        }
                    }
                    strSql+=" ORDER BY a1.tx_nom \n";
                    strSql+=" \n";
                }
                else{
                    /* JM PENDIENTE agregar que se vea que es lo nuevo del mes  */
                    
// <editor-fold defaultstate="collapsed" desc=" /* José Marín: No funco 2019-10-08   */ ">
//
//


//                    strSql ="";
//                    strSql+=" SELECT DISTINCT a1.co_emp, 1 as co_loc, a1.co_cli, a1.tx_ide, a1.tx_nom as tx_nomCli, a1.st_reg,   \n"; 
//                    strSql+="        a4.co_ven,  a3.co_cfg, a5.co_usr, a5.tx_usr, a5.tx_nom \n";
//                    strSql+=" FROM tbm_cli as a1  \n";
//                    strSql+=" INNER JOIN tbr_cliLoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli) \n";
//                    strSql+=" INNER JOIN tbm_usr as a5 ON (a4.co_ven=a5.co_usr) \n";
//                    strSql+=" LEFT OUTER JOIN (  \n";
//                    strSql+="       SELECT a1.co_emp, a1.co_loc,a1.co_cli, a1.co_cfg, a1.co_usrVen, a3.tx_usr, a3.tx_nom  , a1.fe_ing   \n";
//                    strSql+="       FROM tbm_preCfgCliLoc as a1   \n";
//                    strSql+="       INNER JOIN tbm_usr as a3 ON (a1.co_usrVen=a3.co_usr)   \n";
//                    strSql+="       WHERE a1.fe_ing   >=  to_date('"+cmbAnio.getSelectedItem()+"/"+(cmbMes.getSelectedIndex()+2)+"/01', 'YYYY/MM/DD')      \n";
//                    strSql+="  ) as a3 ON (a4.co_emp=a3.co_emp  AND a4.co_loc=a3.co_loc AND  a4.co_cli=a3.co_cli)    \n";
//                    strSql+=" WHERE a1.st_reg='A'  AND a3.co_cfg IS NULL   \n";
//                    if(txtCodVen.getText().length()>0){
//                        strSql+="       AND (a4.co_ven = "+txtCodVen.getText()+" ) ";
//                    }
//                    else{
//                        if(objParSis.getCodigoUsuario()!=1){
//                            strSql+="         a4.co_ven IN (  \n";
//                            strSql+="                               SELECT co_usrRel \n";
//                            strSql+="                               FROM tbr_usrLocPrgUsr  \n";
//                            strSql+="                               WHERE co_emp="+objParSis.getCodigoEmpresa()+" and co_loc="+objParSis.getCodigoLocal()+" and co_usr="+objParSis.getCodigoUsuario()+" \n";
//                            strSql+=" AND co_mnu="+objParSis.getCodigoMenu()+" ";
//                            strSql+="                           ) \n";
//                            strSql+="           ) \n";
//                        }
//                    }
//                     if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                        strSql+=" AND a4.co_emp="+objParSis.getCodigoEmpresa() +" AND a4.co_loc="+objParSis.getCodigoLocal(); 
//                    }
//                    if(txtCodEmp.getText().length()>0){
//                        strSql+=" AND a4.co_emp="+txtCodEmp.getText();
//                    }
//                    if(txtCodLoc.getText().length()>0){
//                        strSql+=" AND a4.co_loc="+txtCodLoc.getText();
//                    }
//                    strSql+=" ORDER BY a1.tx_nom \n";
                   
                    
                    //</editor-fold>
                    strSql="";
                    strSql+=" SELECT a1.*, a2.tx_ide, a2.tx_nom as tx_nomCli,a3.co_usr, a3.tx_nom as tx_nomVen \n";
                    strSql+=" FROM( \n";
                    strSql+="   /* todo lo que le configuraron antes de septiembre  */  \n";
                    strSql+="   ( (SELECT a1.co_emp, a1.co_loc,a1.co_cli   \n";
                    strSql+="   FROM tbm_preCfgCliLoc as a1 \n";
                    strSql+="   INNER JOIN ( \n";
                    strSql+="         SELECT a3.co_emp, a3.co_loc, a3.co_cli, MAX(a3.co_cfg) as co_cfg      \n";
                    strSql+="         FROM tbm_preCfgCliLoc as a3     \n";
                    strSql+="         WHERE a3.fe_ing <  to_date('"+cmbAnio.getSelectedItem()+"/"+(cmbMes.getSelectedIndex()+2)+"/01', 'YYYY/MM/DD')      \n";
                    strSql+="         GROUP BY  a3.co_emp, a3.co_loc, a3.co_cli  \n";
                    strSql+="   ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND a1.co_cfg=a2.co_cfg)   \n";
                    strSql+="   WHERE a1.co_usrVen = "+txtCodVen.getText()+"   \n";
                    strSql+="   UNION  /* lo que tiene en la actualidad */  \n";
                    strSql+="     SELECT a1.co_emp, a1.co_loc, a1.co_cli \n";
                    strSql+="     FROM tbr_cliLoc as a1 \n";
                    strSql+="     INNER JOIN tbr_locusr as a2 ON (a1.co_ven=a2.co_usr AND a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc)  \n";
                    strSql+="     where a1.co_ven="+txtCodVen.getText()+"  AND  a2.st_reg='P'  \n";
                    strSql+="   )EXCEPT /* todo lo que no es de ella despues de septiembre  */   \n";
                    strSql+="   SELECT a1.co_emp, a1.co_loc,a1.co_cli   \n";
                    strSql+="   FROM tbm_preCfgCliLoc as a1   \n";
                    strSql+="   INNER JOIN (  \n";
                    strSql+="     SELECT a3.co_emp, a3.co_loc, a3.co_cli, MAX(a3.co_cfg) as co_cfg   \n";
                    strSql+="     FROM tbm_preCfgCliLoc as a3    \n";
                    strSql+="     WHERE a3.fe_ing >= to_date('"+cmbAnio.getSelectedItem()+"/"+(cmbMes.getSelectedIndex()+2)+"/01', 'YYYY/MM/DD')      \n";
                    strSql+="     GROUP BY  a3.co_emp, a3.co_loc, a3.co_cli   \n";
                    strSql+="   ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cli=a2.co_cli AND a1.co_cfg=a2.co_cfg)   \n";
                    strSql+="   WHERE a1.co_usrVen = "+txtCodVen.getText()+"  ) \n";
                    strSql+=" ) as a1 \n";
                    strSql+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                    strSql+=" INNER JOIN tbm_usr as a3 ON (a3.co_usr="+txtCodVen.getText()+" )  \n";
                    strSql+="  \n";
                }
                System.out.println("cargarDatos 2 " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                vecDat=new Vector();
                while(rstLoc.next()){
                    if(consultaMesPresente()){
                        if(rstLoc.getString("analisis").equals("NUEVA")){
                            if(txtCodVen.getText().length()>0){
                                if(Integer.parseInt(txtCodVen.getText())==rstLoc.getInt("co_venAntes")){
                                    if(chkCliEli.isSelected()){
                                        vecReg= new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                                        vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                                        vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                                        vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                                        vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                                        vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_venAntes"));
                                        vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomAntes"));
                                        vecReg.add(INT_TBL_ST_ACT, "S");
                                        vecReg.add(INT_TBL_CHK_ACT, true);
                                        vecReg.add(INT_TBL_ST_NUE, "N");
                                        vecReg.add(INT_TBL_CHK_NUE, false);
                                        vecReg.add(INT_TBL_ST_CAL,rstLoc.getString("analisis"));
                                        vecDat.add(vecReg);
                                    }
                                }
                                else if(Integer.parseInt(txtCodVen.getText())==rstLoc.getInt("co_venNuevo")){
                                    if(chkCliNue.isSelected()){
                                        vecReg= new Vector();
                                        vecReg.add(INT_TBL_DAT_LIN,"");
                                        vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                                        vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                                        vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                                        vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                                        vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                                        vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_venNuevo"));
                                        vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomNuevo")); 
                                        vecReg.add(INT_TBL_ST_ACT, "N");
                                        vecReg.add(INT_TBL_CHK_ACT, false);
                                        vecReg.add(INT_TBL_ST_NUE, "S");
                                        vecReg.add(INT_TBL_CHK_NUE, true);
                                        vecReg.add(INT_TBL_ST_CAL,rstLoc.getString("analisis"));
                                        vecDat.add(vecReg);
                                    }
                                }
                            }
                            else{
                                for(int j=0;j<2;j++){
                                    if(j==0){
                                        if(isVendedorAsignado(rstLoc.getInt("co_venAntes"))){
                                            if(chkCliEli.isSelected()){
                                                vecReg= new Vector();
                                                vecReg.add(INT_TBL_DAT_LIN,"");
                                                vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                                                vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                                                vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                                                vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                                                vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                                                vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_venAntes"));
                                                vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomAntes"));
                                                vecReg.add(INT_TBL_ST_ACT, "S");
                                                vecReg.add(INT_TBL_CHK_ACT, true);
                                                vecReg.add(INT_TBL_ST_NUE, "N");
                                                vecReg.add(INT_TBL_CHK_NUE, false);
                                                vecReg.add(INT_TBL_ST_CAL,rstLoc.getString("analisis"));
                                                vecDat.add(vecReg);
                                            }
                                        }
                                    }
                                    else{
                                        if(isVendedorAsignado(rstLoc.getInt("co_venNuevo"))){
                                            if(chkCliNue.isSelected()){
                                                vecReg= new Vector();
                                                vecReg.add(INT_TBL_DAT_LIN,"");
                                                vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                                                vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                                                vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                                                vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                                                vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                                                vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_venNuevo"));
                                                vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomNuevo")); 
                                                vecReg.add(INT_TBL_ST_ACT, "N");
                                                vecReg.add(INT_TBL_CHK_ACT, false);
                                                vecReg.add(INT_TBL_ST_NUE, "S");
                                                vecReg.add(INT_TBL_CHK_NUE, true);
                                                vecReg.add(INT_TBL_ST_CAL,rstLoc.getString("analisis"));
                                                vecDat.add(vecReg);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            if(chkCliSinCam.isSelected()){
                                vecReg= new Vector();
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                                vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                                vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                                vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                                vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                                vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_venAntes"));
                                vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomAntes"));
                                vecReg.add(INT_TBL_ST_ACT, "S");
                                vecReg.add(INT_TBL_CHK_ACT, true);
                                vecReg.add(INT_TBL_ST_NUE, "S");
                                vecReg.add(INT_TBL_CHK_NUE, true);
                                vecReg.add(INT_TBL_ST_CAL,rstLoc.getString("analisis"));
                                vecDat.add(vecReg);
                            }
                        }
                    }
                    else{
                        vecReg= new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_COD_EMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_COD_LOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_COD_CLI, rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_IDE_CLI, rstLoc.getString("tx_ide"));
                        vecReg.add(INT_TBL_NOM_CLI, rstLoc.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_COD_VEN, rstLoc.getString("co_usr"));
                        vecReg.add(INT_TBL_NOM_VEN, rstLoc.getString("tx_nomVen"));
                        vecReg.add(INT_TBL_ST_ACT, "S");
                        vecReg.add(INT_TBL_CHK_ACT, true);
                        vecReg.add(INT_TBL_ST_NUE, "S");
                        vecReg.add(INT_TBL_CHK_NUE, true);
                        vecReg.add(INT_TBL_ST_CAL,"");
                        vecDat.add(vecReg);
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
                rstLoc.close();                         
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
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
     * 
     * @param CodVen
     * @return 
     */
    
    private boolean isVendedorAsignado(int CodVen){
        boolean blnRes=false;
        try{
            if(objParSis.getCodigoUsuario()==1){
                blnRes=true;
            } 
            else{
                for(int i=0;i<arlVenDat.size();i++){
                    if(CodVen==objUti.getIntValueAt(arlVenDat, i, 0) ){
                        blnRes=true;
                    }
                }
            }
        }
        catch (Exception e){         
            blnRes = false;     
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
    
    private boolean cargarDetReg(){
        boolean blnRes=false;
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if(cargarDatos()) {
                lblMsgSis.setText("Cargando datos...");
                objTblMod.initRowsState();
                blnRes=true;
            }
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false); 
        }
        catch(Exception e){        
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
                    strMsg+="<tr><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que el cliente ha sido eliminado.</td></tr>";
                    strMsg+="<tr><td style='background-color: #78F078'>&nbsp;</td><td>Indica que el cliente es nuevo. </td></tr>";
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
                case INT_TBL_COD_VEN:
                    strMsg="Nombre del vendedor";
                    break;
                    
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                    strSql+=" FROM tbm_loc AS a1";
                    strSql+=" WHERE a1.co_emp>0";
                    strSql+=" ORDER BY a1.co_emp,a1.co_loc";
               }
               else{
                   strSql="";
                   strSql+=" SELECT a1.co_emp, a1.co_loc ,a1.tx_nom";
                    strSql+=" FROM tbm_Loc AS a1";
                    strSql+=" WHERE a1.co_emp>0 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSql+=" ORDER BY a1.co_emp,a1.co_loc";
               }
            }
            else{
                strSql="";
                strSql+=" SELECT a3.co_emp, a3.co_loc as co_loc, a3.tx_nom as tx_nom ";
                strSql+=" FROM tbr_locPrgUsr as a2";
                strSql+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp AND a2.co_locrel=a3.co_loc)";
                strSql+=" WHERE a2.st_reg in ('A','P') and a2.co_usr=" + objParSis.getCodigoUsuario()+ " AND ";
                strSql+="       a2.co_emp=" + objParSis.getCodigoEmpresa()+" AND ";
                strSql+="       a2.co_loc=" + objParSis.getCodigoLocal()+" AND ";
                strSql+="       a2.co_mnu=" + objParSis.getCodigoMenu();
                strSql+=" ORDER BY a3.co_emp,a3.co_loc";
            }
           
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSql, arlCam, arlAli, arlAncCol);
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
    
    private boolean mostrarLocal(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: 
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
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
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                    strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                    strSql+=" FROM tbm_usr AS a1 \n";
                    strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                    strSql+=" WHERE a2.st_ven='S' \n";
                    strSql+=" ORDER BY a1.tx_nom \n";
                }
                else{
                    strSql="";
                    strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                    strSql+=" FROM tbm_usr AS a1 \n";
                    strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                    strSql+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usr) \n";
                    strSql+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND ";
                    strSql+=" a2.st_ven='S' \n";
                    strSql+=" ORDER BY a1.tx_nom \n";
                }
            }
            else{
                strSql="";
                strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                strSql+=" FROM tbm_usr AS a1 \n";
                strSql+=" INNER JOIN tbr_usrLocPrgUsr as a4 ON (a1.co_usr=a4.co_usrRel) \n";
                strSql+=" WHERE a4.co_emp="+objParSis.getCodigoEmpresa()+" AND a4.co_usr="+objParSis.getCodigoUsuario()+" AND \n";
                strSql+=" a4.co_loc="+objParSis.getCodigoLocal()+" and a4.co_mnu="+objParSis.getCodigoMenu()+"  \n";
                strSql+=" ORDER BY a1.tx_nom  \n";
            }
            System.out.println("configurarVenConVen: " + strSql);
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSql, arlCam, arlAli, arlAncCol);
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
    
    private ArrayList arlVenReg;
    private ArrayList arlVenDat;
    
    
    private boolean cargarVendedoresAsignadosPorUsuario(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                arlVenDat = new ArrayList();
                strSql="";
                strSql+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                strSql+=" FROM tbm_usr AS a1 \n";
                strSql+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                strSql+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usr) \n";
                strSql+=" INNER JOIN tbr_usrLocPrgUsr as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_usr=a4.co_usrRel) \n";
                strSql+=" WHERE a4.co_emp="+objParSis.getCodigoEmpresa()+" AND a4.co_usr="+objParSis.getCodigoUsuario()+" AND ";
                strSql+=" a4.co_loc="+objParSis.getCodigoLocal()+" AND a2.st_ven='S' \n";
                strSql+=" ORDER BY a1.tx_nom  \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    arlVenReg = new ArrayList();
                    arlVenReg.add(rstLoc.getInt("co_usr"));
                    arlVenDat.add(arlVenReg);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e) { 
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
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText())){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else{
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else{
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText())){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else{
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else{
                            txtNomVen.setText(strNomVen);
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
    
         
    private boolean configurarComboAniosSistema(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                cmbAnio.removeAllItems();
                
                /*JM obtengo todos los años */
                strSql="";
                strSql+=" select co_emp, ne_ani  \n";
                strSql+=" from tbm_aniCreSis  where co_emp="+objParSis.getCodigoEmpresa()+" order by  ne_ani \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    cmbAnio.addItem(rstLoc.getString("ne_ani"));
                }
                /*JM Selecciona el año actual */
                for(int i=0;i<cmbAnio.getItemCount();i++){
                    if(cmbAnio.getItemAt(i).equals(objUti.parseString(objUti.getAnio(datFecAux)))  ){
                        cmbAnio.setSelectedIndex(i);
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                datFecAux=null;
            }
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
           blnRes=false;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean configurarComboMes(){
        boolean blnRes=true;
        try{
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            System.out.println("NUMERO MES " + objUti.getMes(datFecAux));
            switch(objUti.getMes(datFecAux)){
                case 0: cmbMes.setSelectedIndex(0);break;
                case 1: cmbMes.setSelectedIndex(1);break;
                case 2: cmbMes.setSelectedIndex(2);break;
                case 3: cmbMes.setSelectedIndex(3);break;
                case 4: cmbMes.setSelectedIndex(4);break;
                case 5: cmbMes.setSelectedIndex(5);break;
                case 6: cmbMes.setSelectedIndex(6);break;
                case 7: cmbMes.setSelectedIndex(7);break;
                case 8: cmbMes.setSelectedIndex(8);break;
                case 9: cmbMes.setSelectedIndex(9);break;
                case 10: cmbMes.setSelectedIndex(10);break;
                case 11: cmbMes.setSelectedIndex(11);break;
            }
            datFecAux=null;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
}