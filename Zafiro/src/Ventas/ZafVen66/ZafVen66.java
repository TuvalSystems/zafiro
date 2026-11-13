/*
 * ZafVen39.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Ventas.ZafVen66;
import Compras.ZafCom18.ZafCom18;
import Compras.ZafCom18.ZafCom18_01;
import Compras.ZafCom91.ZafCom91;
import Compras.ZafCom99.ZafCom99;
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
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author  Jose Mario Marin
 */
public class ZafVen66 extends javax.swing.JInternalFrame{
    
    static final int INT_TBL_DAT_LIN=0;    
    static final int INT_TBL_DAT_PERIODO=1;
    static final int INT_TBL_DAT_SEMANA=2;
    static final int INT_TBL_DAT_LISTA_AFECTADA=3;
    static final int INT_TBL_DAT_LISTA_ORIGEN=4;
    static final int INT_TBL_DAT_FACTOR=5;                      
    static final int INT_TBL_DAT_EJECUTADA=6;                    
    static final int INT_TBL_DAT_FECH_EJECUCION=7;   
    static final int INT_TBL_DAT_DESDE=8;
    static final int INT_TBL_DAT_HASTA=9;
    static final int INT_TBL_DAT_FACTOR_RES=10;
    static final int INT_TBL_DAT_MODIFICAR=11;
    //Variables
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
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
    
     
    private ZafVenConGP vcoListPre,vcoListFlu;                                   //Ventana de consulta.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
     
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
 
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafMouMotAda objMouMotAda;
    private String strVersion="v0.3";
    
    private ZafTblCelEdiTxt objTblCelEdiTxtFactor;
    
    private ArrayList arlRegConDoc, arlDatConDoc;
    private static final int INT_ARL_FACTOR=0;
    
    private char estado='0';
    private java.util.Date datFecDoc;       
    private String strAfectar,strOrigen; 
    int semanaxAni =0, semanaActAnio=0, anioActual=0;
    Date date;
    Calendar calendario = Calendar.getInstance(); // Inicializamos Calendar con la fecha y hora actual
    private String str_estCon="";
    /**
     * Crea una nueva instancia de la clase  .
     * @param obj El objeto ZafParSis.
     */
    public ZafVen66(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panPer = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtValMax = new javax.swing.JTextField();
        butPeriodo = new javax.swing.JButton();
        txtPeriodo = new javax.swing.JTextField();
        txtValMin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtLstAfectar = new javax.swing.JTextField();
        butLstAfectar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtLstOrigen = new javax.swing.JTextField();
        butLstOrigen = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtValAfe = new javax.swing.JTextField();
        rbMan = new javax.swing.JRadioButton();
        rbRango = new javax.swing.JRadioButton();
        panTab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBot = new javax.swing.JPanel();
        butLimpiar = new javax.swing.JButton();
        butgua = new javax.swing.JButton();
        butCerrar = new javax.swing.JButton();
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

        panPer.setPreferredSize(new java.awt.Dimension(100, 50));
        panPer.setLayout(null);

        jLabel1.setText("Período:");
        panPer.add(jLabel1);
        jLabel1.setBounds(10, 8, 70, 14);

        jLabel2.setText("%");
        panPer.add(jLabel2);
        jLabel2.setBounds(630, 0, 20, 14);

        txtValMax.setEnabled(false);
        txtValMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValMaxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValMaxFocusLost(evt);
            }
        });
        txtValMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValMaxActionPerformed(evt);
            }
        });
        panPer.add(txtValMax);
        txtValMax.setBounds(590, 0, 40, 20);

        butPeriodo.setText("...");
        butPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPeriodoActionPerformed(evt);
            }
        });
        panPer.add(butPeriodo);
        butPeriodo.setBounds(184, 5, 20, 20);

        txtPeriodo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPeriodoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPeriodoFocusLost(evt);
            }
        });
        txtPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPeriodoActionPerformed(evt);
            }
        });
        panPer.add(txtPeriodo);
        txtPeriodo.setBounds(94, 6, 90, 20);

        txtValMin.setEnabled(false);
        txtValMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValMinFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValMinFocusLost(evt);
            }
        });
        txtValMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValMinActionPerformed(evt);
            }
        });
        panPer.add(txtValMin);
        txtValMin.setBounds(540, 0, 40, 20);

        jLabel5.setText("-");
        panPer.add(jLabel5);
        jLabel5.setBounds(580, 0, 10, 14);

        txtLstAfectar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLstAfectarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLstAfectarFocusLost(evt);
            }
        });
        txtLstAfectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLstAfectarActionPerformed(evt);
            }
        });
        panPer.add(txtLstAfectar);
        txtLstAfectar.setBounds(94, 30, 90, 20);

        butLstAfectar.setText("...");
        butLstAfectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLstAfectarActionPerformed(evt);
            }
        });
        panPer.add(butLstAfectar);
        butLstAfectar.setBounds(184, 30, 20, 20);

        jLabel7.setText("Lista Origen:");
        panPer.add(jLabel7);
        jLabel7.setBounds(210, 10, 80, 14);

        txtLstOrigen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLstOrigenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLstOrigenFocusLost(evt);
            }
        });
        txtLstOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLstOrigenActionPerformed(evt);
            }
        });
        panPer.add(txtLstOrigen);
        txtLstOrigen.setBounds(290, 10, 90, 20);

        butLstOrigen.setText("...");
        butLstOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLstOrigenActionPerformed(evt);
            }
        });
        panPer.add(butLstOrigen);
        butLstOrigen.setBounds(380, 10, 20, 20);

        jLabel8.setText("Lista Afectar:");
        panPer.add(jLabel8);
        jLabel8.setBounds(10, 30, 80, 14);

        txtValAfe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValAfeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValAfeFocusLost(evt);
            }
        });
        txtValAfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValAfeActionPerformed(evt);
            }
        });
        panPer.add(txtValAfe);
        txtValAfe.setBounds(540, 20, 90, 20);

        rbMan.setText("Uso Manual");
        rbMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbManActionPerformed(evt);
            }
        });
        panPer.add(rbMan);
        rbMan.setBounds(424, 20, 100, 23);

        rbRango.setText("Uso de Rango");
        rbRango.setToolTipText("");
        rbRango.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRangoActionPerformed(evt);
            }
        });
        panPer.add(rbRango);
        rbRango.setBounds(424, 0, 110, 23);

        panGen.add(panPer, java.awt.BorderLayout.NORTH);

        panTab.setPreferredSize(new java.awt.Dimension(0, 170));
        panTab.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        panTab.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butLimpiar.setText("LIMPIAR");
        butLimpiar.setToolTipText("Limpiar la informacion de la ventana.");
        butLimpiar.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpiarActionPerformed(evt);
            }
        });
        panBot.add(butLimpiar);

        butgua.setText("GUARDAR");
        butgua.setToolTipText("Cierra la ventana.");
        butgua.setPreferredSize(new java.awt.Dimension(92, 25));
        butgua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butguaActionPerformed(evt);
            }
        });
        panBot.add(butgua);

        butCerrar.setText("CERRAR");
        butCerrar.setToolTipText("Cierra la ventana.");
        butCerrar.setPreferredSize(new java.awt.Dimension(92, 25));
        butCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerrarActionPerformed(evt);
            }
        });
        panBot.add(butCerrar);

        panTab.add(panBot, java.awt.BorderLayout.PAGE_START);

        panGen.add(panTab, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("");
        tabFrm.getAccessibleContext().setAccessibleDescription("vvvv");

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

        setBounds(0, 0, 706, 502);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        if(!isVersionActualizada())
        {
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
    
    private void butLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpiarActionPerformed
    limpiarFrm();        
    }//GEN-LAST:event_butLimpiarActionPerformed

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

    
    private static boolean isNumeric(String cadena){
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}
    
    private void txtValMaxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMaxFocusGained
////        strDesLarCli=txtValMax.getText();
////        txtValMax.selectAll();
    }//GEN-LAST:event_txtValMaxFocusGained

    private boolean validacionValMax ()
    {
        boolean blnRes=true;
        if ( isNumeric(txtValMax.getText())==false)
        {
            mostrarMsgInf("<HTML>Se debe ingresar un numero</HTML>");
            txtValMax.requestFocus();
            blnRes=false;
        }

        if ( Double.parseDouble(txtValMin.getText()) >= Double.parseDouble(txtValMax.getText()))
        {
            mostrarMsgInf("<HTML>El numero ingresado en el campo DESDE debe ser menor al HASTA.</HTML>");
            txtValMax.requestFocus();
            blnRes=false;
        }
        return blnRes;
    }
 private boolean validacionValAfectar ()
    {
        boolean blnRes=true;
////        if ( isNumeric(txtValAfe.getText())==false)
////        {
////            mostrarMsgInf("<HTML>Se debe ingresar un numero</HTML>");
////            txtValAfe.requestFocus();
////            blnRes=false;
////        }

        if ( Double.parseDouble(txtValAfe.getText()) <= 0)
        {
            mostrarMsgInf("<HTML>El numero ingresado en el campo debe ser POSITIVO.</HTML>");
            txtValAfe.requestFocus();
            blnRes=false;
        }
        return blnRes;
    }
 
    private void txtValMaxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMaxFocusLost
        int numeroSem=0;
         if (validacionValMax()==true)
         {
            numeroSem=obtenerSemaAnio( Integer.parseInt(txtPeriodo.getText()));
            if (llenarTablaNuevo(numeroSem,1))
            {
                   estado='N';
            }             
         }
    }//GEN-LAST:event_txtValMaxFocusLost

    private void txtValMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValMaxActionPerformed
        txtValMax.transferFocus();
    }//GEN-LAST:event_txtValMaxActionPerformed

    private void butPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPeriodoActionPerformed

    mostrarFLuctuacion(0);//chiqui
////    if (validacionPeriodo())
////    {
////        mostrarMsgInf("<HTML>El periodo ingresado debe ser correcto.</HTML>"); 
////    }
    estado ='C';
////        mostrarVenConCli(0);
////        //Seleccionar el JRadioButton de filtro si es necesario.
////        if (txtCodCli.getText().length()>0)
////        {
////            optFil.setSelected(true);
////        }
    }//GEN-LAST:event_butPeriodoActionPerformed

    private void txtPeriodoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPeriodoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodoFocusGained

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
                strSQL+=" WHERE  flu.st_reg ='A' and flu.ne_perAnuFlu ='"+ txtPeriodo.getText() +"'";                
                
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
    
    private void validacionPeriodo()
    {
    ////    
        int anioMaximo =objUti.getAnio(datFecDoc) +50 ;
        int anioActual=objUti.getAnio(datFecDoc);
        if(( objUti.isNumero(txtPeriodo.getText()) && txtPeriodo.getText().equals("") ==false ) && (!txtLstOrigen.getText().equals(""))  && (!txtLstAfectar.getText().equals("")))
        {
            if  ((Integer.parseInt(txtPeriodo.getText())<anioActual) || (anioMaximo <= Integer.parseInt(txtPeriodo.getText())))
            {
                mostrarMsgInf("<HTML>Se debe ingresar un año considerable.</HTML>"); 
                txtPeriodo.requestFocus();
                txtPeriodo.setText("");
            }
            else
            {
                
                
                if (ValidaExistePeriodo())
                {
                    mostrarMsgInf("<HTML>Periodo ya ingresado</HTML>"); 
                    txtPeriodo.requestFocus();
                    txtPeriodo.setText("");
                }
                else
                {
                    txtLstAfectar.setEditable(true);
                    txtLstAfectar.setEnabled(true); 
                    butLstAfectar.setEnabled(true); 
                }

            }

        }
        else
        {
            mostrarMsgInf("<HTML>Se debe ingresar todos los datos requeridos : Año - Lista Origen - Lista a Aplicar .</HTML>"); 
            txtPeriodo.requestFocus();              
        }
////    else
////    {
////        mostrarMsgInf("<HTML>Se debe ingresar un año considerable.</HTML>"); 
////        txtPeriodo.requestFocus();        
////    }
////    if  ((Integer.parseInt(txtPeriodo.getText())<anioActual) || (anioMaximo <= Integer.parseInt(txtPeriodo.getText())))
////    {
////        mostrarMsgInf("<HTML>Se debe ingresar un año considerable.</HTML>"); 
////        txtPeriodo.requestFocus();
////    }
////    else
////    {
////        if( objUti.isNumero(txtPeriodo.getText()) && txtPeriodo.getText().equals("") ==false )
////        {
////            txtLstAfectar.setEditable(true);
////            txtLstAfectar.setEnabled(true);
////        }
////        else
////        {
////            mostrarMsgInf("<HTML>Se debe ingresar un año considerable.</HTML>"); 
////            txtPeriodo.requestFocus();
////        }
////    }

    
}
    private void txtPeriodoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPeriodoFocusLost
////////////////////////////        if (!txtPeriodo.getText().equalsIgnoreCase(txtPeriodo.getText())) {
////////////////////////////            if (txtPeriodo.getText().equals("")) {
////////////////////////////                txtPeriodo.setText("");
////////////////////////////            }
////////////////////////////        }
////////////////////////////        else
////////////////////////////           validacionPeriodo();
       // txtCodBod.setText(strCodBod);        
// TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodoFocusLost

    private void txtPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPeriodoActionPerformed

    private void txtValMinFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMinFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValMinFocusGained

    private void txtValMinFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValMinFocusLost
    if( objUti.isNumero(txtValMin.getText()) && txtValMin.getText().equals("") ==false )
    {
        txtValMax.setEditable(true);
        txtValMax.setEnabled(true);
    }
    else
    {
        txtValMin.requestFocus();
        mostrarMsgInf("<HTML>Se debe ingresar un numero.</HTML>"); 
    }
// TODO ad

// TODO add your handling code here:
    }//GEN-LAST:event_txtValMinFocusLost

    private void txtValMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValMinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValMinActionPerformed

    private void txtLstAfectarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLstAfectarFocusGained
        strAfectar = txtLstAfectar.getText();
        txtLstAfectar.selectAll();
    }//GEN-LAST:event_txtLstAfectarFocusGained

    private void txtLstAfectarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLstAfectarFocusLost
    if (!txtLstAfectar.getText().equalsIgnoreCase(strAfectar)) {
            if (txtLstAfectar.getText().equals("")) {
                txtLstAfectar.setText("");
            } else {
                mostrarListaPrecio(2);
                validacionLstAfecta();
            }
        } else
        txtLstAfectar.setText(strAfectar);
    }//GEN-LAST:event_txtLstAfectarFocusLost

    private void txtLstAfectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLstAfectarActionPerformed
        txtLstAfectar.transferFocus();
    }//GEN-LAST:event_txtLstAfectarActionPerformed
    private void validacionLstAfecta ()
    {
        if (txtLstAfectar.getText().equals("") ==false)
        {
            if (txtLstAfectar.getText().equals("GENERAL") ==true)
            {
                mostrarMsgInf("<HTML>La seleccion de la lista GENERAL no es compatible en este campo </HTML>");
                txtLstAfectar.setText("");
                txtLstAfectar.requestFocus();
                //limpiarFrm();
            }
            else
            {
                txtLstOrigen.setEditable(true);
                txtLstOrigen.setEnabled(true);
                butLstOrigen.setEnabled(true);
            }
        }
    }
    private void butLstAfectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLstAfectarActionPerformed
        // TODO add your handling code here:
        
        mostrarListaPrecio(0);//chiqui        // TODO add your handling code here:    
        validacionLstAfecta();
        
    }//GEN-LAST:event_butLstAfectarActionPerformed

    private boolean llenarTabla()//chiqui
    {
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
////            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean limpiarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            txtPeriodo.setText("");
            txtLstAfectar.setText("");
            txtLstOrigen.setText("");
            txtValMin.setText("");
            txtValMax.setText("");
            objTblMod.removeAllRows();
            rbRango.setEnabled(true);
            rbMan.setEnabled(true);
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarDetReg()//chiqui
    {
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        String str_tip="";
        try{
            objTblMod.removeAllRows();
            if (!txtPeriodo.getText().equals("")){
////                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
////                    strSQL="";
////                    strSQL+=" SELECT ne_perFlu,ne_sem,tx_codLis,tx_codLisApl,nd_porFlu,st_apl,fe_aplFlu ";
////                    strSQL+=" FROM tbm_cfgFluPer ";
////                    strSQL+=" WHERE ne_perFlu=" + txtPeriodo.getText() + "";
////                    strSQL+=" AND tx_codLis='" + txtLstAfectar.getText() + "'";
////                    strSQL+=" AND tx_codLisApl='" + txtLstOrigen.getText() + "'";
////                    strSQL+=" AND st_reg='A'";
////                    strSQL+=" ORDER BY ne_sem asc";

                    strSQL="";
                    strSQL+=" SELECT per.ne_perFlu AS periodo,per.ne_sem as semana,per.tx_codLis as listaA,per.tx_codLisApl as listaAP,per.nd_porFlu as porcen";
                    strSQL+=" ,per.st_apl as estado,per.fe_aplFlu as fechaApl ,flu.nd_des as desde,flu.nd_has  as hasta, flu.st_tipGen as tipo";
                    strSQL+=" FROM tbm_cfgFluPer  as per ";
                    strSQL+=" left join tbm_cfgFlu as flu on (per.ne_perFlu=flu.ne_perAnuFlu and per.tx_codLis = flu.tx_codLis and per.tx_codLisApl = flu.tx_codLisApl) ";
                    strSQL+=" WHERE  per.ne_perFlu=" + txtPeriodo.getText() + "";
                    strSQL+=" AND per.tx_codLis='" + txtLstAfectar.getText() + "'";
                    strSQL+=" AND per.tx_codLisApl='" + txtLstOrigen.getText() + "'";
                    strSQL+=" AND per.st_reg='A'";
                    strSQL+=" ORDER BY per.ne_sem asc";
                    
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();

                //segundo query fin
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_PERIODO,    rst.getString("periodo"));
                        vecReg.add(INT_TBL_DAT_SEMANA,    rst.getString("semana"));
                        vecReg.add(INT_TBL_DAT_LISTA_AFECTADA,    rst.getString("listaA"));
                        vecReg.add(INT_TBL_DAT_LISTA_ORIGEN,    rst.getString("listaAP"));
                        vecReg.add(INT_TBL_DAT_FACTOR,    rst.getString("porcen"));
                        vecReg.add(INT_TBL_DAT_EJECUTADA,  rst.getString("estado"));
                        vecReg.add(INT_TBL_DAT_FECH_EJECUCION,        rst.getString("fechaApl"));
                        str_tip = rst.getString("tipo");
                        if (str_tip.equals("A"))
                        {
                            vecReg.add(INT_TBL_DAT_DESDE,        rst.getString("desde"));
                            vecReg.add(INT_TBL_DAT_HASTA,        rst.getString("hasta"));                            
                        }
                        else
                        {
                            vecReg.add(INT_TBL_DAT_DESDE,        rst.getString("porcen"));
                            vecReg.add(INT_TBL_DAT_HASTA,        rst.getString("porcen"));
                        }
                        vecReg.add(INT_TBL_DAT_FACTOR_RES,   rst.getString("porcen"));
                        //LLENADO DE LA CONUSLTA
                        if ( (semanaActAnio >  Integer.parseInt(rst.getString("semana"))) && 
                              (anioActual >= Integer.parseInt(rst.getString("periodo"))))
                            vecReg.add(INT_TBL_DAT_MODIFICAR,  'N');
                        else
                            vecReg.add(INT_TBL_DAT_MODIFICAR,  'S');
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
    
    private boolean cargarCabReg()//chiqui
    {
        int intPosRel;
        String  strTip="";
        boolean blnRes=true;
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stm=con.createStatement();
              strSQL="";
                strSQL+=" SELECT rtrim(flu.ne_perAnuFlu) as periodo,rtrim(flu.tx_codLis) as listaAfectar,rtrim(flu.tx_codLisApl) as listaOrigen,\n";
                strSQL+="  flu.nd_des as desde , flu.nd_has as hasta,rtrim(flu.fe_ing) as fechaIng ,'2023-06-05' as fechaMod ,flu.st_tipGen as tipo\n";
                strSQL+=" FROM tbm_cfgFlu as flu \n";
                strSQL+=" WHERE  flu.st_reg ='A' and flu.ne_perAnuFlu ='"+ txtPeriodo.getText() +"' \n";                
                strSQL+="        AND flu.tx_codLisApl ='"+ txtLstOrigen.getText()+"' and  flu.tx_codLis = '"+ txtLstAfectar.getText() +"'\n";                
                
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("periodo");
                    txtPeriodo.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("listaAfectar");
                    txtLstAfectar.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("listaOrigen");
                    txtLstOrigen.setText((strAux==null)?"":strAux);
                    strTip = rst.getString("tipo");
                    if (strTip.equals("A"))
                    {
                        rbRango.setSelected(true);
                        rbMan.setSelected(false);
                        strAux=rst.getString("desde");
                        txtValMin.setText((strAux==null)?"":strAux);
                        strAux=rst.getString("hasta");//código de bodega de la empresa      
                        txtValMax.setText((strAux==null)?"":strAux); 
                        txtValAfe.setText("");
                    }
                    else
                    {
                        rbRango.setSelected(false);
                        rbMan.setSelected(true);
                        strAux=rst.getString("desde");//
                        txtValAfe.setText((strAux==null)?"":strAux); 
                        txtValMin.setText("");
                        txtValMax.setText("");
                                
                    }
                    

                    
                    lblTit.setText(objParSis.getNombreMenu() + strVersion /*+"  "+ ((strAux==null)?"":strAux )*/);                   
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
    
    private boolean mostrarFLuctuacion(int intTipBus)//chiqui 
    {
        boolean blnRes = true;
        String strtip="";
        try{
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoListFlu.setCampoBusqueda(2);
                    vcoListFlu.show();
                    if (vcoListFlu.getSelectedButton() == vcoListFlu.INT_BUT_ACE) {
                        
                        txtLstAfectar.setText(vcoListFlu.getValueAt(2));
                        txtLstOrigen.setText(vcoListFlu.getValueAt(3));
                        txtPeriodo.setText(vcoListFlu.getValueAt(1));
                        txtValMin.setText(vcoListFlu.getValueAt(7));
                        txtValMax.setText(vcoListFlu.getValueAt(8));
                        txtValAfe.setText(vcoListFlu.getValueAt(7));
                        strtip = vcoListFlu.getValueAt(9);
                        if (strtip.equals("A"))
                            rbRango.setSelected(true);
                        else
                            rbMan.setSelected(true);
                        llenarTabla();
                        str_estCon="C";
                        rbRango.setEnabled(false);
                        rbMan.setEnabled(false);
                        txtValMin.setEnabled(false);
                        txtValMax.setEnabled(false);
                        txtValAfe.setEnabled(false);
////                        txtNomBod.setText(vcoListPre.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".

                    if (vcoListPre.buscar("listaPrecio", txtLstAfectar.getText())) {
                        txtLstAfectar.setText(vcoListFlu.getValueAt(1));
                        txtLstAfectar.setText(vcoListFlu.getValueAt(2));
                    } else {
                        vcoListPre.setCampoBusqueda(0);
                        vcoListPre.setCriterio1(11);
                        vcoListPre.cargarDatos();
                        vcoListPre.show();
                        if (vcoListPre.getSelectedButton() == vcoListPre.INT_BUT_ACE) {
                            txtLstOrigen.setText(vcoListPre.getValueAt(1));
                            txtLstOrigen.setText(vcoListPre.getValueAt(2));
                        } else {
////                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoListPre.buscar("descripcion", txtLstAfectar.getText())) {
                        txtLstAfectar.setText(vcoListPre.getValueAt(1));
                        txtLstAfectar.setText(vcoListPre.getValueAt(2));
                    } else {
                        vcoListPre.setCampoBusqueda(1);
                        vcoListPre.setCriterio1(11);
                        vcoListPre.cargarDatos();
                        vcoListPre.show();
                        if (vcoListPre.getSelectedButton() == vcoListPre.INT_BUT_ACE) {
                            txtLstAfectar.setText(vcoListPre.getValueAt(1));
                            txtLstAfectar.setText(vcoListPre.getValueAt(2));
                        } else {
////                            txtLstOrigen.setText(strNomBod);
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
    
    
    private boolean mostrarListaPrecio(int intTipBus) //chiqui
    {
        boolean blnRes = true;
        try{
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoListPre.setCampoBusqueda(1);
                    vcoListPre.show();
                    if (vcoListPre.getSelectedButton() == vcoListPre.INT_BUT_ACE) {
                        txtLstAfectar.setText(vcoListPre.getValueAt(2));
////                        txtNomBod.setText(vcoListPre.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".

                    vcoListPre.setCampoBusqueda(1);
                    vcoListPre.show();
                    if (vcoListPre.getSelectedButton() == vcoListPre.INT_BUT_ACE) {
                        txtLstOrigen.setText(vcoListPre.getValueAt(2));
////                        txtNomBod.setText(vcoListPre.getValueAt(2));
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoListPre.buscar("descripcion", txtLstAfectar.getText()))
                    {
                        txtLstAfectar.setText(vcoListPre.getValueAt(2));
                        txtLstAfectar.selectAll();
                        txtLstAfectar.requestFocus();
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián larga".
                    if (vcoListPre.buscar("descripcion", txtLstOrigen.getText()))
                    {
                        txtLstOrigen.setText(vcoListPre.getValueAt(2));
                        txtLstOrigen.selectAll();
                        txtLstOrigen.requestFocus();
                    }
                    break;                    
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }   
        return blnRes;
    }

    
    private void txtLstOrigenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLstOrigenFocusGained
        strOrigen = txtLstOrigen.getText();
        txtLstOrigen.selectAll();
    }//GEN-LAST:event_txtLstOrigenFocusGained

    private void validacionLstOrigen ()
    {
        if (txtLstOrigen.getText().equals("") ==false)
        {
            if (txtLstOrigen.getText().equals(txtLstAfectar.getText()) ==true)
            {
                mostrarMsgInf("<HTML>La seleccion de la lista ORIGEN no puede ser la misma de la LISTA AFECTAR </HTML>");
                txtLstOrigen.setText("");
                txtLstOrigen.requestFocus();
                //limpiarFrm();
            }
            else
            {
                txtValMin.setEditable(true);
                txtValMin.setEnabled(true);
            }
        }
}
    private void txtLstOrigenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLstOrigenFocusLost
  if (!txtLstOrigen.getText().equalsIgnoreCase(strOrigen)) {
            if (txtLstOrigen.getText().equals("")) {
                txtLstOrigen.setText("");
            } else {
                mostrarListaPrecio(3);
                validacionLstOrigen();
            }
        } else
        txtLstOrigen.setText(strOrigen);
  // TODO add your handling code here:
    }//GEN-LAST:event_txtLstOrigenFocusLost

    private void txtLstOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLstOrigenActionPerformed
        txtLstOrigen.transferFocus();       // TODO add your handling code here:
    }//GEN-LAST:event_txtLstOrigenActionPerformed
    public static int obtenerSemaAnio(int anio) 
    {
            Calendar c = Calendar.getInstance();
            c.set(anio, 0, 1);
        return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }
    
    private String obtenerSemaActualAnio(String  fecha) 
    {
        String dia,mes,semanaanno;
         Calendar c = new GregorianCalendar();
         dia = Integer.toString(c.get(Calendar.DATE));
         mes = Integer.toString(c.get(Calendar.MONTH));
         semanaanno = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        return semanaanno;
    }
    

    
    
    private void butLstOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLstOrigenActionPerformed
        
        if (txtLstAfectar.getText().equals(""))
               mostrarMsgInf("<HTML>Se necesita ingresar la Lista de Precio referente a DESDE</HTML>");
        else
                  mostrarListaPrecio(1);        // TODO add your handling code here:
        
        if (txtLstOrigen.getText() == null ? txtLstAfectar.getText() == null : txtLstOrigen.getText().equals(txtLstAfectar.getText()))
        {
           mostrarMsgInf("<HTML>La lista de precio seleeccionada no puede ser la misma que la lista afectar </HTML>"); 
        }
        else
        {
           if(validacionExiste())
            {
                mostrarMsgInf("<HTML>Las combinaciones que ha ingresado indican que ya se encuentra ingresada </HTML>");
                limpiarFrm();
            }
           else
           {
                txtValMin.setEditable(true);
                txtValMin.setEnabled(true);               
           }
        }
////        if (txtLstOrigen.getText().equals("") ==false)
////        {
////            if(validacionExiste())
////            {
////                mostrarMsgInf("<HTML>Las combinaciones que ha ingresado indican que ya se encuentra ingresada </HTML>");
////                limpiarFrm();
////            }
////            else
////            {
////                if (txtLstOrigen.getText() == null ? txtLstAfectar.getText() == null : txtLstOrigen.getText().equals(txtLstAfectar.getText()))
////                {
////
////                    limpiarFrm();
////                }
////                else
////                {
////                    txtValMin.setEditable(true);
////                    txtValMin.setEnabled(true);
////                }
////            }
////        }
    }//GEN-LAST:event_butLstOrigenActionPerformed
    
    
    public double generarRandom(Double valorMinimo, Double valorMaximo, int indicador)
    {
        double generaFactor =0;
        Random rand = new Random();
        generaFactor =objUti.redondear(valorMinimo + ( valorMaximo - valorMinimo ) * rand.nextDouble(),2);
        generaFactor= organizaRandom(generaFactor,indicador);
        return generaFactor;
    }
    private double organizaRandom(double factor,int indicador)
    {
        boolean validaExiste =false;
        if (indicador ==0)
        {
            arlRegConDoc = new ArrayList();
            arlRegConDoc.add(INT_ARL_FACTOR,factor);
            arlDatConDoc.add(arlRegConDoc);
        }
        else
        {
            for (int k=0;k<arlDatConDoc.size();k++)
            {
                    if(factor == objUti.getDoubleValueAt(arlDatConDoc, k, INT_ARL_FACTOR))
                    {
                        validaExiste =true;
                    }
            }
            if (validaExiste ==false)
            {
                arlRegConDoc = new ArrayList();
                arlRegConDoc.add(INT_ARL_FACTOR,factor);
                arlDatConDoc.add(arlRegConDoc);
            }
            else
                generarRandom (Double.parseDouble(txtValMin.getText()),Double.parseDouble(txtValMax.getText()),indicador);
        }
        

        
        return factor;
    }

    private boolean llenarTablaNuevo(int semanas, int indicador)
    {
        boolean blnRes=true;
        double valorFactor =0;
        try{
            if (indicador ==1)
            {
                if (semanas> 0)
                {
                        vecDat.clear();
                        arlDatConDoc= new ArrayList();
                         for (int i=0; i<semanas;i++)
                         {
                            vecReg=new Vector();
                             vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_PERIODO,   txtPeriodo.getText());
                            vecReg.add(INT_TBL_DAT_SEMANA,    i+1);
                            vecReg.add(INT_TBL_DAT_LISTA_AFECTADA,    txtLstAfectar.getText());
                            vecReg.add(INT_TBL_DAT_LISTA_ORIGEN,    txtLstOrigen.getText());
                            valorFactor =generarRandom (Double.parseDouble(txtValMin.getText()),Double.parseDouble(txtValMax.getText()),i);
                            vecReg.add(INT_TBL_DAT_FACTOR,   valorFactor);
                            vecReg.add(INT_TBL_DAT_EJECUTADA,  "N");
                            vecReg.add(INT_TBL_DAT_FECH_EJECUCION,        "");
                            vecReg.add(INT_TBL_DAT_DESDE,    txtValMin.getText()    );
                            vecReg.add(INT_TBL_DAT_HASTA,        txtValMax.getText());
                            vecReg.add(INT_TBL_DAT_FACTOR_RES,   valorFactor);
                            vecReg.add(INT_TBL_DAT_MODIFICAR,   "N");
                            vecDat.add(vecReg);
                         }

                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        vecDat.clear();
                }
                else
                    blnRes=false;                
            }
            else
            {
                if (semanas> 0)
                {
                        vecDat.clear();
                        arlDatConDoc= new ArrayList();
                         for (int i=0; i<semanas;i++)
                         {
                            vecReg=new Vector();
                             vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_PERIODO,   txtPeriodo.getText());
                            vecReg.add(INT_TBL_DAT_SEMANA,    i+1);
                            vecReg.add(INT_TBL_DAT_LISTA_AFECTADA,    txtLstAfectar.getText());
                            vecReg.add(INT_TBL_DAT_LISTA_ORIGEN,    txtLstOrigen.getText());
                            valorFactor =Double.parseDouble(txtValAfe.getText());
                            vecReg.add(INT_TBL_DAT_FACTOR,   valorFactor);
                            vecReg.add(INT_TBL_DAT_EJECUTADA,  "N");
                            vecReg.add(INT_TBL_DAT_FECH_EJECUCION,        "");
                            vecReg.add(INT_TBL_DAT_DESDE,    txtValAfe.getText()    );
                            vecReg.add(INT_TBL_DAT_HASTA,        txtValAfe.getText());
                            vecReg.add(INT_TBL_DAT_FACTOR_RES,   valorFactor);
                            vecReg.add(INT_TBL_DAT_MODIFICAR,   "N");
                            vecDat.add(vecReg);
                         }

                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblDat.setModel(objTblMod);
                        vecDat.clear();
                }
                else
                    blnRes=false;                  
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean validacionExiste ()
   {
        boolean blnRes=false;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
////            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
             con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                //con=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                stmLoc = con.createStatement();
                strSQL="";
                strSQL+="SELECT rtrim(tx_codLis) AS listaPrecio,rtrim(tx_codLis) AS descripcion FROM tbm_cfgFlu ";
                strSQL+=" WHERE ne_perAnuFlu =" + txtPeriodo.getText() + "";
                strSQL+=" AND  tx_codLis ='" + txtLstAfectar.getText() + "'";
                strSQL+=" AND  tx_codLisApl ='" + txtLstOrigen.getText() + "'";
                strSQL+=" AND st_reg  ='A'";
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    blnRes=true;
                }
                stmLoc.close();
                stmLoc=null;
                rstLoc.close();
                rstLoc=null;
                con.close();
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
    private void butguaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butguaActionPerformed
        String indicador ="";
        
        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        if (estado=='0')
            mostrarMsgInf("<HTML>No se puede realizar el guardado, debe llenar todos los campos.</HTML>");
        else
        {
            if (rbRango.isSelected())
                indicador="A";
            else
                indicador ="M";
                
           if (guardarFlutuaciones (indicador))//chiqui
                mostrarMsgInf("<HTML>Proceso Actualizado Correctamente.</HTML>");
           else
                mostrarMsgInf("<HTML>Proceso No Actualizado.</HTML>"); 
        }
                
        // TODO add your handling code here:
    }//GEN-LAST:event_butguaActionPerformed
////    private boolean validacionModificacion()
////    {
////        boolean blnRes=false;     
////
////        int intFil=-1;
////        double bdeCanAnt=0,bdeCanSigt=0;
////        String strTipRelEgr="";
////        intFil=tblDat.getSelectedRow();
////        bdeCanAnt= objUti.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_FACTOR).toString()) ;
////        bdeCanSigt= objUti.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_FACTOR_RES).toString());
////        if (bdeCanAnt ==bdeCanSigt)
////            blnRes=true;
////        
////        dddd
////        return blnRes;
////    }
    private boolean guardarFlutuaciones(String tipo)//chiqui
    {
        java.sql.Connection conLoc;
        boolean blnRes=false;     
        int semanaxAni =0, semanaActAnio=0, anioActual=0;
        Date date;
        Calendar calendario = Calendar.getInstance(); // Inicializamos Calendar con la fecha y hora actual
    // y asignamos la variable date Date al Calendar de esta forma
        calendario.setTime(datFecDoc);
        semanaxAni = calendario.get(Calendar.WEEK_OF_YEAR);
        semanaActAnio = Integer.parseInt(obtenerSemaActualAnio(datFecDoc.toString())) ;
        semanaActAnio =semanaActAnio+1;
        anioActual =objUti.getAnio(datFecDoc.toString(),"yyyy-MM-dd");
        try
        {
            lblMsgSis.setText("Guardando datos...");
            int intNumFil=objTblMod.getRowCountTrue();
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

                if (estado =='C')
                {
                    for (int i=0; i<intNumFil;i++)
                    {
                           if ((objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")) && (objTblMod.getValueAt(i, INT_TBL_DAT_MODIFICAR).toString()=="M"))
                            {
                                if ((semanaActAnio <= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_SEMANA).toString())) && 
                                    (anioActual>= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PERIODO).toString())))
                                {
                                    if (semanaxAni >= Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR).toString()) )
                                    {
                                          if(conLoc!=null)
                                            {
                                                conLoc.setAutoCommit(false);
                                                if(ProcesoValidaModificacion(conLoc,i,tipo))
                                                {
                                                         blnRes=true;
                                                         mostrarMsgInf("<HTML>Registro Actualizado.</HTML>");
                                                }
                                                else
                                                 {
                                                        blnRes=false;
                                                        System.err.println("Error actualizarSolicitudAutorizar");
                                                        break;
                                                 }   
                                                if(blnRes==true){
                                                    conLoc.commit();
                                                }
                                                else{
                                                    conLoc.rollback();
                                                    break;
                                                }
                                                blnRes=false;
                                            }  
                                    }
                                }    
                                else
                                  mostrarMsgInf("<HTML>No se puede realizar la modificación , se quiere modificar una semana que no es inferior a la actual</HTML>");  
                            }
                        }
                }
                else
                {
                        if(conLoc!=null)
                        {
                            conLoc.setAutoCommit(false);
                            if(ProcesoValidaInsercion(conLoc,tipo))
                            {
                                blnRes=true;
                            }
                            else
                            {
                                blnRes=false;
                                System.err.println("Error actualizarSolicitudAutorizar");
                            }   
                            if(blnRes==true){
                                conLoc.commit();
                            }
                            else{
                                conLoc.rollback();
                            }

//                            blnRes=false;
                        }
                }
                conLoc.close();
                conLoc=null;

            
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }
        return blnRes;
    }
    
    
    
    private boolean insertar_tbmCabFlu(java.sql.Connection conExt, String tipo){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cfgFlu(";
                strSQL+="        ne_perAnuFlu,tx_codLis,tx_codLisApl,nd_des,nd_has,st_reg,fe_ing,co_usrIng,st_tipGen)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtPeriodo.getText() + ""; //co_emp
                strSQL+=", '" + txtLstAfectar.getText() + "'"; //co_loc
                strSQL+=", '" + txtLstOrigen.getText()+ "'"; //co_tipdoc    
                if (tipo.equals("A"))
                {
                    strSQL+=", " + txtValMin.getText(); //co_doc
                    strSQL+=", " + txtValMax.getText();//ne_secGrp                    
                }
                else
                {
                    strSQL+=", " + txtValAfe.getText(); //co_doc
                    strSQL+=", " + txtValAfe.getText();//ne_secGrp                        
                }

                strSQL+=", 'A'";//ne_secEmp
                strSQL+=", GETDATE()";//ne_numCot
                ////strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + "";//ne_numDoc
                strSQL+=", '" + objParSis.getNombreUsuario() + "'";
                strSQL+=", '" + tipo + "'";
                strSQL+=");";
                System.out.println("insertar_tbm_cfgFlu: " + strSQL);
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
    
    private boolean insertar_tbmDetFluPer(java.sql.Connection conExt){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        int k=0;
        try{
            if (conExt!=null)
            {
                stm=conExt.createStatement();
                //intCodBodEmp=getCodigoBodegaEmpresa();
////                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                 int intNumFil=objTblMod.getRowCountTrue();
                 strSQL="";
                 for ( k=0;k<intNumFil;k++)
                {
                    
                    strSQL+=" INSERT INTO tbm_cfgFluPer(";
                    strSQL+="        ne_perFlu,ne_sem,tx_codLis,tx_codLisApl,nd_porFlu,st_apl,fe_aplFlu,st_reg,";
                    strSQL+="        tx_obs1,tx_obs2)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + txtPeriodo.getText() + ""; //co_emp
                    strSQL+=", " + objTblMod.getValueAt(k,INT_TBL_DAT_SEMANA).toString() + ""; //SEMANA
                    strSQL+=", '" + txtLstAfectar.getText() + "'"; //AFECTADA
                    strSQL+=", '" + txtLstOrigen.getText() + "'"; //ORIGEN
                     strSQL+=", " +  objTblMod.getValueAt(k,INT_TBL_DAT_FACTOR).toString() + ""; //SEMANA
                    strSQL+=",'N'"; //co_reg
                    strSQL+=", ''"; //FECHA
                    strSQL+=", 'A'"; //ST_rEG
                    strSQL+=", ''"; //OBS1
                    strSQL+=", ''"; //OBS2
                    strSQL+="); "; 
                }
                 System.out.println("insertar_tbm_cfgFluPer: " + strSQL);
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
    
    
    
    
    
    private boolean ProcesoValidaInsercion(java.sql.Connection conExt,String tipo)
   {
        boolean blnRes=false;
        try{
            if(conExt!=null){
                if(insertar_tbmCabFlu(conExt,tipo)){
                    if(insertar_tbmDetFluPer(conExt))
                            blnRes=true;
                            else
                                 blnRes=false;
                         }
                        else
                            blnRes=false;
                    }
                    else
                        blnRes=false;
            }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
   }
    
    private boolean actualizarFlutuaciones(java.sql.Connection conn, int i){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cfgFluPer";
                strSQL+=" SET nd_porFlu=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR_RES).toString())+"";
                strSQL+=",fe_aplFlu=GETDATE()";
                strSQL+=",st_apl='S'";  
               strSQL+=" WHERE ne_perFlu=" + objTblMod.getValueAt(i,INT_TBL_DAT_PERIODO) + " AND tx_codLisApl=" +  objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_ORIGEN) +"";
               strSQL+="  AND  tx_codLis=" + objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_AFECTADA) + " AND ne_sem=" +  objTblMod.getValueAt(i,INT_TBL_DAT_SEMANA) +"";
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
    
    private void butCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerrarActionPerformed
        // TODO add your handling code here:
        exitForm(null);
    }//GEN-LAST:event_butCerrarActionPerformed

    private void txtValAfeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAfeFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValAfeFocusGained

    private void txtValAfeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValAfeFocusLost
        // TODO add your handling code here:
        int numeroSem=0;
         if (validacionValAfectar()==true)
         {
            numeroSem=obtenerSemaAnio( Integer.parseInt(txtPeriodo.getText()));
            if (llenarTablaNuevo(numeroSem, 2))
            {
                   estado='N';
            }             
         }        
    }//GEN-LAST:event_txtValAfeFocusLost

    private void txtValAfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValAfeActionPerformed
        // TODO add your handling code here:
         txtValAfe.transferFocus();
    }//GEN-LAST:event_txtValAfeActionPerformed

    private void rbRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRangoActionPerformed
        // TODO add your handling code here:
        if (rbRango.isSelected())
        {
            rbMan.setSelected(false);
            txtValAfe.setText("");
            txtValAfe.setEnabled(false);
            txtValMin.setEnabled(true);
            txtValMin.setEnabled(true);
        }
        else
        {
            rbMan.setSelected(true);
            txtValMin.setEnabled(false);
            txtValMin.setEnabled(false);
            txtValAfe.setEnabled(true);
            txtValMin.setText("");
            txtValMax.setText("");
        }
            
    }//GEN-LAST:event_rbRangoActionPerformed

    private void rbManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbManActionPerformed
        // TODO add your handling code here:
        if (rbMan.isSelected())
        {
            txtValMin.setEnabled(false);
            txtValMin.setEnabled(false);
            txtValAfe.setEnabled(true);            
            rbRango.setSelected(false);  
            txtValMin.setText("");
            txtValMax.setText("");            
        }
        else
        {
            txtValAfe.setText("");
            txtValAfe.setEnabled(false);
            txtValMin.setEnabled(true);
            txtValMax.setEnabled(true);            
            rbRango.setSelected(true);     
        }
            
            
    }//GEN-LAST:event_rbManActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCerrar;
    private javax.swing.JButton butLimpiar;
    private javax.swing.JButton butLstAfectar;
    private javax.swing.JButton butLstOrigen;
    private javax.swing.JButton butPeriodo;
    private javax.swing.JButton butgua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JRadioButton rbMan;
    private javax.swing.JRadioButton rbRango;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtLstAfectar;
    private javax.swing.JTextField txtLstOrigen;
    private javax.swing.JTextField txtPeriodo;
    private javax.swing.JTextField txtValAfe;
    private javax.swing.JTextField txtValMax;
    private javax.swing.JTextField txtValMin;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            configurarTblDat();
            configuraListaPrecio();
            configuraFlutuacion();
            
                // y asignamos la variable date Date al Calendar de esta forma
            datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
            calendario.setTime(datFecDoc);
            semanaxAni = calendario.get(Calendar.WEEK_OF_YEAR);//OBTIENE EL NUMERO DE SEMANAS ANUAL
            semanaActAnio = Integer.parseInt(obtenerSemaActualAnio(datFecDoc.toString())) ;//OBTIENE LA SEMANA ACTUAL ANUAL
            semanaActAnio =semanaActAnio+1;// OBTIENE LA SEMANA ANUAL SIGUIENTE DE LA FECHA ACTUAL
            anioActual =objUti.getAnio(datFecDoc.toString(),"yyyy-MM-dd"); //OBTIENE EL AÑO ACTUAL
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraFlutuacion()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("periodo");
            arlCam.add("listaAfectar");
            arlCam.add("listaOrigen");
            arlCam.add("rango");
            arlCam.add("fechaIng");
            arlCam.add("fechaMod");        
            arlCam.add("desde");
            arlCam.add("hasta");
            arlCam.add("tipo");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Periodo");
            arlAli.add("List. Afectar");
            arlAli.add("List. Origen");
            arlAli.add("Rango");
            arlAli.add("Fecha Ingreso");
            arlAli.add("Fecha Modificación");
            arlAli.add("Desde");
            arlAli.add("Hasta");
            arlAli.add("Tipo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("100");
            arlAncCol.add("100");
            arlAncCol.add("100");
            arlAncCol.add("100");     
            arlAncCol.add("50");
            arlAncCol.add("50"); 
            arlAncCol.add("10"); 
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT rtrim(flu.ne_perAnuFlu) as periodo,rtrim(flu.tx_codLis) as listaAfectar,rtrim(flu.tx_codLisApl) as listaOrigen,\n";
                strSQL+=" rtrim(CONCAT(flu.nd_des , '% - ' , flu.nd_has,'%')) as rango,rtrim(flu.fe_ing) as fechaIng ,'2023-06-05' as fechaMod ,flu.nd_des as desde,flu.nd_has as hasta, flu.st_tipGen as tipo \n";
                strSQL+=" FROM tbm_cfgFlu as flu  ";
                strSQL+=" WHERE flu.st_reg ='A'  ";
            }
            else{
                strSQL="";
                strSQL+="SELECT rtrim(flu.ne_perAnuFlu) as periodo,rtrim(flu.tx_codLis) as listaAfectar,rtrim(flu.tx_codLisApl) as listaOrigen,  \n";
                strSQL+=" rtrim(CONCAT(flu.nd_des , '% - ' , flu.nd_has,'%')) as rango,rtrim(flu.fe_ing) as fechaIng ,'2023-06-05' as fechaMod ,flu.nd_des as desde,flu.nd_has as hasta,flu.st_tipGen as tipo \n";
                strSQL+=" FROM tbm_cfgFlu as flu ";
                strSQL+=" WHERE flu.st_reg ='A'   ";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=7;
            intColOcu[1]=8;
            vcoListFlu=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Configuracion de Periodos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
////            intColOcu=null;
            //Configurar columnas.
            vcoListFlu.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(4, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(5, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(6, javax.swing.JLabel.LEFT); 
            vcoListFlu.setConfiguracionColumna(7, javax.swing.JLabel.LEFT);
            vcoListFlu.setConfiguracionColumna(8, javax.swing.JLabel.LEFT);  
            vcoListFlu.setConfiguracionColumna(9, javax.swing.JLabel.LEFT); 



        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     private boolean configuraListaPrecio()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("listaPrecio");
            arlCam.add("descripcion");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Lista Precio");
            arlAli.add("Descripcion");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT rtrim(PRCLEVEL) AS listaPrecio,rtrim(DSCRIPTN) AS descripcion FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV40800 \n";
            }
            else{
                strSQL="";
                strSQL+="SELECT rtrim(PRCLEVEL) AS listaPrecio,rtrim(DSCRIPTN) AS descripcion FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV40800 \n";
            }
            //Ocultar columnas.
////            int intColOcu[]=new int[2];
////            intColOcu[0]=5;
////            intColOcu[1]=6;
            vcoListPre=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Precios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
////            intColOcu=null;
            //Configurar columnas.
            vcoListPre.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoListPre.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);



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
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_PERIODO,"Periodo.");
            vecCab.add(INT_TBL_DAT_SEMANA,"Semana");
            vecCab.add(INT_TBL_DAT_LISTA_AFECTADA,"List.Afectada");
            vecCab.add(INT_TBL_DAT_LISTA_ORIGEN,"List.Origen");
            vecCab.add(INT_TBL_DAT_FACTOR,"Factor");
            vecCab.add(INT_TBL_DAT_EJECUTADA,"Eje");
            vecCab.add(INT_TBL_DAT_FECH_EJECUCION,"Fec. Ejecucion");
            vecCab.add(INT_TBL_DAT_DESDE,"Desde");
            vecCab.add(INT_TBL_DAT_HASTA,"Hasta");
            vecCab.add(INT_TBL_DAT_FACTOR_RES,"Factor_Resp");
            vecCab.add(INT_TBL_DAT_MODIFICAR,"MODIFICAR");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PERIODO, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
            objTblMod.setColumnDataType(INT_TBL_DAT_SEMANA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_FACTOR);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
//////                           calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
//////                            calcularMarUtiCos();
                        }
                    }
                    else if (objTblPopMnu.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_FACTOR);
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_FACTOR);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
//////                            calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FACTOR))
                        {
////                            calcularMarUtiCos();
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PERIODO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SEMANA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_LISTA_AFECTADA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_LISTA_ORIGEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FACTOR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FACTOR_RES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EJECUTADA).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_FECH_EJECUCION).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setPreferredWidth(2);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
//////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
//////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
            //Don Luigi solicitó el 02/Mar/2023 que se ocultara la columna "Rev.Téc." porque ya no se la está utilizando.
//////            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_REV_TEC, tblDat);
//////            if (objParSis.getCodigoUsuario()!=1)
//////            {
//////                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
//////                if (!objPerUsr.isOpcionEnabled(2990))
//////                {
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FAC_COS_UNI, tblDat);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI_FAC, tblDat);
//////                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA, tblDat);
//////                    butPreVen.setVisible(false);
//////                }
//////            }
//////            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//////            objMouMotAda=new ZafCom18.ZafMouMotAda();
//////            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_FACTOR_RES);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Libero los objetos auxiliares.

           
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_FACTOR_RES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {

                }
            });
            
//////            objTblCelRenLblDer=new ZafTblCelRenLbl();
//////            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//////            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLblDer);
//////            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblDer);
//////            objTblCelRenLblDer.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////                    BigDecimal bgdMarUti, bgdMarUtiCos;
//////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI))));
//////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
//////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//////                    {
//////                        objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                    }
//////                    else
//////                    {
//////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
//////                        {
//////                            objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                        }
//////                        else
//////                        {
//////                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
//////                            {
//////                                objTblCelRenLblDer.setBackground(java.awt.Color.RED);
//////                            }
//////                            else
//////                            {
//////                                objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                            }    
//////                        }
//////                    }
//////                }
//////            });
            
//////            objTblCelRenLblNum=new ZafTblCelRenLbl();
//////            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//////            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
//////            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//////            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_FAC).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_UNI_VEN).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_INV_PRO).setCellRenderer(objTblCelRenLblNum);
//////            tcmAux.getColumn(INT_TBL_DAT_ROT_INV).setCellRenderer(objTblCelRenLblNum);
//////            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////                    BigDecimal bgdMarUti, bgdMarUtiCos;
//////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI))));
//////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
//////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//////                    {
//////                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                    }
//////                    else
//////                    {
//////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
//////                        {
//////                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                        }
//////                        else
//////                        {
//////                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
//////                            {
//////                                objTblCelRenLblNum.setBackground(java.awt.Color.RED);
//////                            }
//////                            else
//////                            {
//////                                objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                            }    
//////                        }
//////                    }
//////                }
//////            });
            
//////            objTblCelRenBut=new ZafTblCelRenBut();
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellRenderer(objTblCelRenBut);
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellRenderer(objTblCelRenBut);
//////            objTblCelRenBut=null;
//////            
//////            objTblCelRenChk=new ZafTblCelRenChk();
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setCellRenderer(objTblCelRenChk);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_REV_TEC).setCellRenderer(objTblCelRenChk);
//////            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////                    BigDecimal bgdMarUti, bgdMarUtiCos;
//////                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI))));
//////                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
//////                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//////                    {
//////                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                    }
//////                    else
//////                    {
//////                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
//////                        {
//////                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                        }
//////                        else
//////                        {
//////                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
//////                            {
//////                                objTblCelRenChk.setBackground(java.awt.Color.RED);
//////                            }
//////                            else
//////                            {
//////                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////                            }    
//////                        }
//////                    }
//////                }
//////            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_FACTOR_RES;
//////            objTblCelEdiTxtFactor=new ZafTblCelEdiTxtVco(tblDat, vcoUniMed, intColVen, intColTbl);
//////            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellEditor(objTblCelEdiTxtFactor);
//////            
//////            objTblCelEdiButVcoUniMed=new ZafTblCelEdiButVco(tblDat, vcoUniMed, intColVen, intColTbl);
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMed);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxtFactor=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_FACTOR_RES).setCellEditor(objTblCelEdiTxtFactor);
            objTblCelEdiTxtFactor=null;
            
            objTblCelEdiTxtFactor=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_FACTOR_RES).setCellEditor(objTblCelEdiTxtFactor);
            objTblCelEdiTxtFactor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if ("S".equals(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_MODIFICAR).toString()))
                    {
////                        mostrarMsgInf("<HTML>No se puede realizar la modificación , las modificaciones son posterior a la semana actual</HTML>");  
////                        if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR_RES).toString().toUpperCase().endsWith("L"))
////                        {
////                            objTblCelEdiTxtFactor.setCancelarEdicion(false);
////                        }
////                        else
////                        {
////                            switch (objParSis.getCodigoUsuario())
////                            {
////                                case 1: //admin
////                                case 24: //lsensi
////                                case 122: //fruiz
////                                    break;
////                                case 38: //cbetancourt
////                                    if (!(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR_RES).toString().toUpperCase().endsWith("S") || objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR_RES).toString().toUpperCase().endsWith("P")))
////                                    {
////                                        objTblCelEdiTxtFactor.setCancelarEdicion(true);
////                                    }
////                                    break;
////                            }
////
////                        }
                    }
                    else
                    {
                        mostrarMsgInf("<HTML>No se puede realizar la modificación , las modificaciones son posterior a la semana actual</HTML>");  
                        objTblCelEdiTxtFactor.setCancelarEdicion(true);
                        butLimpiar.requestFocus();
                    }
                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                        if (validaRango( objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR_RES).toString() ))==false)
                        {
                                mostrarMsgInf("<HTML>No se puede realizar la modificación ,el valor ingresado ya existe dentro de las semanas</HTML>"); 
                                objTblMod.setValueAt ( objUti.parseDouble(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR).toString()) ,tblDat.getSelectedRow(), INT_TBL_DAT_FACTOR_RES);
                        }
                        else
                            objTblMod.setValueAt ( 'M' ,tblDat.getSelectedRow(), INT_TBL_DAT_MODIFICAR);
                }
            });
            
//////            objTblCelEdiTxtMarUti=new ZafTblCelEdiTxt(tblDat);
//////            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellEditor(objTblCelEdiTxtMarUti);
//////            objTblCelEdiTxtMarUti=null;
//////            
//////            objTblCelEdiTxtPesKgr=new ZafTblCelEdiTxt(tblDat);
//////            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgr);
//////            objTblCelEdiTxtPesKgr=null;
//////            
//////            objTblCelEdiTxtCanMaxVen=new ZafTblCelEdiTxt(tblDat);
//////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellEditor(objTblCelEdiTxtCanMaxVen);
//////            objTblCelEdiTxtCanMaxVen=null;
//////            
//////            objTblCelEdiTxtFacCosUni=new ZafTblCelEdiTxt(tblDat);
//////            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellEditor(objTblCelEdiTxtFacCosUni);
//////            objTblCelEdiTxtFacCosUni.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////                    calcularCosUniFac();
//////                }
//////            });
//////            
//////            objCom18_01=new ZafCom18_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
//////            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objCom18_01);
//////            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellEditor(objTblCelEdiButDlg);
//////            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//////                int intFilSel;
//////                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////                    intFilSel=tblDat.getSelectedRow();
//////                    if (intFilSel!=-1)
//////                    {
//////                        objCom18_01.setParDlg(objParSis.getCodigoEmpresa(), Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SIS).toString()));
//////                        objCom18_01.cargarReg();
//////                    }
//////                }
//////            });
            
//////            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
//////            tcmAux.getColumn(INT_TBL_DAT_CHK_REV_TEC).setCellEditor(objTblCelEdiChk);
//////            objTblCelEdiChk=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
       
   private boolean validaRango(double porcentaje)
   {
       boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLoc!=null)
            {
                stmLoc1=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT per.ne_perFlu AS periodo,per.ne_sem as semana,per.tx_codLis as listaA,per.tx_codLisApl as listaAP,per.nd_porFlu as porcen";
                strSQL+=" ,per.st_apl as estado,per.fe_aplFlu as fechaApl ,flu.nd_des as desde,flu.nd_has  as hasta";
                strSQL+=" FROM tbm_cfgFluPer  as per ";
                strSQL+=" left join tbm_cfgFlu as flu on (per.ne_perFlu=flu.ne_perAnuFlu) ";
                strSQL+=" WHERE  per.ne_perFlu=" + txtPeriodo.getText() + "";
                strSQL+=" AND per.tx_codLis='" + txtLstAfectar.getText() + "'";
                strSQL+=" AND per.tx_codLisApl='" + txtLstOrigen.getText() + "'";
                strSQL+=" AND per.nd_porFlu=" + porcentaje + "";
                strSQL+=" AND per.st_reg='A'";
                strSQL+=" ORDER BY per.ne_sem asc";
                    
                System.out.println("SELECT " + strSQL);
                rstLoc1 = stmLoc1.executeQuery(strSQL);
                if(rstLoc1.next())
                    blnRes=false;
                
                  stmLoc1.close();
                  stmLoc1=null;
                  rstLoc1.close();
                  rstLoc1=null;

                  
            }
            conLoc.close();
            conLoc=null;
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
   
    private boolean ProcesoValidaModificacion(java.sql.Connection conExt, int i, String tipo)
   {
       boolean blnRes=false;
       java.sql.Statement stmLoc;
       double porcentaje =0,desde=0,hasta=0;
       try
        {
           if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                if(  objUti.isNumero(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR_RES).toString()))
                {
                    if( Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR).toString()) != Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR_RES).toString()))
                    {
                        porcentaje =Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_FACTOR_RES).toString());
                        desde =Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_DESDE).toString());
                        hasta =Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_HASTA).toString());
                        
                       if( objTblMod.getValueAt(i, INT_TBL_DAT_EJECUTADA).toString().equals("N"))
                       {
                            if ((porcentaje >= desde ) &&  (porcentaje <= hasta ))
                            {
////                                objTblMod.setValueAt(null, i, INT_TBL_DAT_FACTOR_RES);
                                if (tipo.equals("A"))
                                {
                                    if (validaRango(porcentaje))
                                    {
                                        strSQL="";
                                        strSQL+="UPDATE Dynamics2021.dbo.tbm_cfgFluPer";
                                        strSQL+=" SET nd_porFlu=" + porcentaje +"";
                                        strSQL+=",fe_aplFlu=GETDATE()";
                                        strSQL+=",st_apl='S'";  
                                        strSQL+=" WHERE ne_perFlu=" + objTblMod.getValueAt(i,INT_TBL_DAT_PERIODO) + " AND tx_codLisApl='" +  objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_ORIGEN) +"'";
                                        strSQL+="  AND  tx_codLis='" + objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_AFECTADA) + "' AND ne_sem=" +  objTblMod.getValueAt(i,INT_TBL_DAT_SEMANA) +"";
                                        System.out.println("UPDATE " + strSQL);
                                        stmLoc.executeUpdate(strSQL);
                                        stmLoc.close();
                                        stmLoc=null;
                                        blnRes=true;
                                    }
                                    else
                                        blnRes=false;                                    
                                }
                                else
                                {
                                        strSQL="";
                                        strSQL+="UPDATE Dynamics2021.dbo.tbm_cfgFluPer";
                                        strSQL+=" SET nd_porFlu=" + porcentaje +"";
                                        strSQL+=",fe_aplFlu=GETDATE()";
                                        strSQL+=",st_apl='S'";  
                                        strSQL+=" WHERE ne_perFlu=" + objTblMod.getValueAt(i,INT_TBL_DAT_PERIODO) + " AND tx_codLisApl='" +  objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_ORIGEN) +"'";
                                        strSQL+="  AND  tx_codLis='" + objTblMod.getValueAt(i,INT_TBL_DAT_LISTA_AFECTADA) + "' AND ne_sem=" +  objTblMod.getValueAt(i,INT_TBL_DAT_SEMANA) +"";
                                        System.out.println("UPDATE " + strSQL);
                                        stmLoc.executeUpdate(strSQL);
                                        stmLoc.close();
                                        stmLoc=null;
                                        blnRes=true;
                                                                    
                                }

                            }
                            else
                                    blnRes=false;
                       }
                    }
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
    private void llamarDetComVen(){
////        try{
////            int intCol = tblDat.getSelectedRow();
////            String strCodEmp = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_EMP  ).toString());
////            String strCodPer = (cboAnio.getSelectedItem().toString() + "" + cboMes.getSelectedItem().toString());
////             
////            ZafVen66_01 obj1 = new  ZafVen66_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis , strCodEmp, strCodPer );
////            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
////            obj1.show();
////             
//// 
////        }
////        catch(Exception e){
////            objUti.mostrarMsgErr_F1(this, e);
////        }
        
    }
     
    
     
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
////    private boolean cargarDetReg(){
////        boolean blnRes=true;
////        try{
////            pgrSis.setIndeterminate(true);
////            butConsultar.setText("Detener");
////            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            if (con!=null){
////                stm=con.createStatement();
////                String strCodPer = (cboAnio.getSelectedItem().toString() + "" + cboMes.getSelectedItem().toString());
////                //Obtener la condición.
////                strSQL="";
////                strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp, a1.co_per, ROUND(sum(a1.nd_valCom),2) as comision \n";
////                strSQL+=" FROM tbm_comVenPag as a1 \n";
////                strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp) \n";
////                strSQL+=" WHERE a1.co_per="+strCodPer+" ";
////                strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_per  \n";
////                strSQL+=" ORDER BY a1.co_emp, a1.co_per  \n";
////                strSQL+=" \n";
////                System.out.println("---> "+strSQL);
////                rst=stm.executeQuery(strSQL);
////                //Limpiar vector de datos.
////                vecDat.clear();
////                //Obtener los registros.
////                lblMsgSis.setText("Cargando datos...");
////                while (rst.next()){
////                    if (blnCon){
////                        vecReg=new Vector();
////                        vecReg.add(INT_TBL_DAT_LIN,"");
////                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
////                        vecReg.add(INT_TBL_DAT_NOM_EMP,rst.getString("tx_nomEmp"));
////                        vecReg.add(INT_TBL_DAT_COD_LOC,"");
////                        vecReg.add(INT_TBL_DAT_NOM_LOC,"");
////                        vecReg.add(INT_TBL_DAT_COM_CONF,rst.getString("comision"));
////                        vecReg.add(INT_TBL_DAT_VAL_FIJ_CONF,"");
////                        vecReg.add(INT_TBL_DAT_VAL_FIJ_INGRESO,"");
////                        vecReg.add(INT_TBL_DAT_VAL_FIJ_EGRESO,"");
////                        vecReg.add(INT_TBL_DAT_VAL_TOTAL,rst.getString("comision"));
////                        vecReg.add(INT_TBL_DAT_BTN_DET,"");
////                        vecReg.add(INT_TBL_DAT_CHK_SOLICITAR,"");
////                        vecReg.add(INT_TBL_DAT_CHK_PENDIENTE,"");
////                        vecReg.add(INT_TBL_DAT_CHK_AUTORIZAR,"");
////                        vecReg.add(INT_TBL_DAT_OBS_AUTORIZACION,"");
////                        vecReg.add(INT_TBL_DAT_BTN_AUTORIZACION,"");
////                        vecDat.add(vecReg);
////                    }
////                    else{
////                        break;
////                    }
////                }
////                rst.close();
////                stm.close();
////                con.close();
////                rst=null;
////                stm=null;
////                con=null;
////                //Asignar vectores al modelo.
////                objTblMod.setData(vecDat);
////                tblDat.setModel(objTblMod);
////                vecDat.clear();
////                 
////                if (blnCon)
////                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
////                else
////                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
////                butConsultar.setText("Consultar");
////                pgrSis.setIndeterminate(false);
////            }
////        }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////      
    
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
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_PERIODO: strMsg="Código de la Empresa"; break;
                case INT_TBL_DAT_SEMANA: strMsg="Nombre de la Empresa"; break;
                case INT_TBL_DAT_LISTA_AFECTADA: strMsg="Código del Local"; break;
                case INT_TBL_DAT_LISTA_ORIGEN: strMsg="Nombre del Local"; break;
                case INT_TBL_DAT_FACTOR: strMsg="Calculo de la comisión segun lo configurado"; break;
                case INT_TBL_DAT_EJECUTADA: strMsg="Valor fijo configurado"; break;
                case INT_TBL_DAT_FECH_EJECUCION: strMsg="Valor fijo de ingreso agregado"; break;
                default: strMsg=""; break;
                        
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}
