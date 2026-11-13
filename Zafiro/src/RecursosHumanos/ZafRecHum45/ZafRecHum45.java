package RecursosHumanos.ZafRecHum45;

import RecursosHumanos.ZafRecHum86.*;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Autorización de liquidaciones de haberes.
 *
 * Sólo el usuario 156 puede autorizar o denegar
 * una liquidación.
 *
 * @author Jean Moran
 */
public class ZafRecHum45 extends javax.swing.JInternalFrame
{
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_COEMP = 1;
    final int INT_TBL_DAT_EMP = 2;
    final int INT_TBL_DAT_CODLOC = 3;
    final int INT_TBL_DAT_CODTIPDOC = 4;
    final int INT_TBL_DAT_DESCORTIPDOC = 5;
    final int INT_TBL_DAT_DESLARTIPDOC = 6;
    final int INT_TBL_DAT_CODDOC = 7;

    final int INT_TBL_DAT_NUMDOC = 8;
    final int INT_TBL_DAT_FECDOC = 9;
    final int INT_TBL_DAT_EMPLE_SOLI = 10;

    /*
     * Abre ZafRecHum44 para consultar la liquidación.
     */
    final int INT_TBL_DAT_BUTLIQHAB = 11;

    /*
     * Estados de la liquidación.
     */
    final int INT_TBL_DAT_CHKAUTPEN = 12;
    final int INT_TBL_DAT_CHKAUTTOT = 13;

/*
 * Abre la ventana de autorizadores.
 */
final int INT_TBL_DAT_BUTANE2 = 14;

/*
 * Código del trabajador. Columna oculta.
 */
final int INT_TBL_DAT_CO_TRA = 15;

/*
 * Abre ZafRecHum44 directamente en la pestaña Reporte.
 */
final int INT_TBL_DAT_BUTANEREP = 16;
    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;
    private int intCoReg;   
    private String strCodTra = "";    

    private String strFecha;

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                    //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru, objTblCelRenLblDocRel, objTblCelRenLblNum, objTblCelRenLblCol;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;
    private ZafSelFec objSelFec;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafPerUsr objPerUsr;
        
    private ZafRecHum45_02 objAutLiq;
    public int intPosAct;

    private String strVersion = "v0.1";

    private final int intCodTipDocLiq = 325;
    private final int intCodUsrAut = 156;
    

    public ZafRecHum45(ZafParSis obj)
    {
        try 
        {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
 
            if (!configurarFrm()) {
                exitForm();
            }
            agregarColTblDat();
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panFecCor = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        chkMosDocTotAut = new javax.swing.JCheckBox();
        chkMosDocPenAut = new javax.swing.JCheckBox();
        jPanelReport = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
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

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panFecCor.setPreferredSize(new java.awt.Dimension(100, 100));
        panFecCor.setLayout(null);
        jPanel1.add(panFecCor, java.awt.BorderLayout.NORTH);

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(0, 200));
        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 4, 400, 14);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setSelected(true);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 20, 400, 14);

        chkMosDocTotAut.setText("Mostrar los documentos que están autorizados");
        chkMosDocTotAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocTotAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocTotAut);
        chkMosDocTotAut.setBounds(10, 80, 440, 20);

        chkMosDocPenAut.setSelected(true);
        chkMosDocPenAut.setText("Mostrar los documentos que están pendientes de autorizar");
        chkMosDocPenAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocPenAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocPenAut);
        chkMosDocPenAut.setBounds(10, 60, 430, 20);

        jPanel1.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", jPanel1);

        jPanelReport.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

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
        spnDat.setViewportView(tblDat);

        jPanelReport.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanelReport);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(385, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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


    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private boolean mensajeConfir(String strMsg2)
    {
        boolean blnRes=false;
        String strTit="Mensaje del sistema Zafiro";
        
        if((JOptionPane.showConfirmDialog(this,strMsg2,strTit,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == 0 ))
            blnRes=true;  // SI
        else
            blnRes=false;  // NO 
        return blnRes;
    }    
    

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */


    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
     }//GEN-LAST:event_formInternalFrameOpened

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);                      
            chkMosDocPenAut.setSelected(true);
            
            chkMosDocTotAut.setSelected(true);
        }
        else
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            optTod.setSelected(false);
            chkMosDocTotAut.setSelected(false);
        }
        else
        {
            optTod.setSelected(true);
        }
    }//GEN-LAST:event_optFilActionPerformed

        
    private void chkMosDocTotAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocTotAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocTotAutActionPerformed

    private void chkMosDocPenAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocPenAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocPenAutActionPerformed
  
    
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
     private void habilitarChkBox(boolean blnOptFil, boolean blnOptTod) {
        optFil.setSelected(blnOptFil);
        optTod.setSelected(blnOptTod);
    }
    

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkMosDocPenAut;
    private javax.swing.JCheckBox chkMosDocTotAut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelReport;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFecCor;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) 
    {
      //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    private class ZafThreadGUI extends Thread 
    {
        public void run() {
            if (!cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    
    
    
    
 
        private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();
            
            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 1: Liquidación de Haberes");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COEMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_EMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODLOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESCORTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESLARTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODDOC));
            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECDOC));

            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_EMPLE_SOLI)); 
            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTLIQHAB));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 2: Autorizacion");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN));            
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTTOT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANE2));     
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CO_TRA)); 
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANEREP));
            
            
 
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                     
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            
            strAux = objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(17);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_EMP, "Empresa");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC, "Cód.Doc.");            
          
            
            vecCab.add(INT_TBL_DAT_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc.");
            
            vecCab.add(INT_TBL_DAT_EMPLE_SOLI, "Empleado");
            
            vecCab.add(INT_TBL_DAT_BUTLIQHAB, "Liquidacion");

            vecCab.add(INT_TBL_DAT_CHKAUTPEN, "Pendiente");            
            vecCab.add(INT_TBL_DAT_CHKAUTTOT, "Aprobado");
            
            vecCab.add(INT_TBL_DAT_BUTANE2, "Autorizadores");     
            vecCab.add(INT_TBL_DAT_CO_TRA, "Cod.Trab");            
            vecCab.add(INT_TBL_DAT_BUTANEREP, "Reporte");
            
            

  
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Tama�o de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(90);
            
            tcmAux.getColumn(INT_TBL_DAT_EMPLE_SOLI).setPreferredWidth(250);
            
            
            tcmAux.getColumn(INT_TBL_DAT_BUTLIQHAB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTPEN).setPreferredWidth(70);           
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTTOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setPreferredWidth(70);
            
            
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panFecCor.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CO_TRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUTLIQHAB, tblDat);

            


            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDat);

            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDat);
 
            //*****************************************************************************
      
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCru = new ZafTblCelRenLbl();
            objTblCelRenLblCru.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCru.setTipoFormato(objTblCelRenLblCru.INT_FOR_NUM);
            objTblCelRenLblCru.setFormatoNumerico("####", false, true);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setCellRenderer(objTblCelRenLblCru);
            objTblCelRenLblCru = null;

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTLIQHAB);
            vecAux.add("" + INT_TBL_DAT_BUTANE2);
            vecAux.add("" + INT_TBL_DAT_BUTANEREP);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            if ((objPerUsr.isOpcionEnabled(3065))) {
                butCon.setEnabled(true);
            }
            if ((objPerUsr.isOpcionEnabled(3066))) {
                butCer.setEnabled(true);
            }

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTLIQHAB).setCellRenderer(zafTblDocCelRenBut);
            
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(zafTblDocCelRenBut);
            

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTLIQHAB).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTLIQHAB:
                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTLIQHAB:
                                abrirLiquidacion(getValTbl(intFilSel, INT_TBL_DAT_COEMP),getValTbl(intFilSel, INT_TBL_DAT_CODLOC),getValTbl(intFilSel, INT_TBL_DAT_CODTIPDOC),getValTbl(intFilSel, INT_TBL_DAT_CODDOC),false);
                                break;
                            case INT_TBL_DAT_BUTANEREP:                                
                                abrirLiquidacion(getValTbl(intFilSel, INT_TBL_DAT_COEMP),getValTbl(intFilSel, INT_TBL_DAT_CODLOC),getValTbl(intFilSel, INT_TBL_DAT_CODTIPDOC),getValTbl(intFilSel, INT_TBL_DAT_CODDOC),true);
                             
                                break;
                        }
                    }
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(objTblCelRenBut);
            

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenChk = new ZafTblCelRenChk();            
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);            ;
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUTANEREP).setCellRenderer(objTblCelRenBut);
            
            objTblCelRenBut = null;

            /*
            * Ventana de usuarios autorizadores.
            */
           objAutLiq = new ZafRecHum45_02(
                   javax.swing.JOptionPane.getFrameForComponent(this),
                   true,
                   objParSis
           );

           objTblCelEdiButDlg =
                   new ZafTblCelEdiButDlg(objAutLiq);

           tcmAux.getColumn(
                   INT_TBL_DAT_BUTANE2
           ).setCellEditor(objTblCelEdiButDlg);

           objTblCelEdiButDlg.addTableEditorListener(
               new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
               {
                   int intFilSel;

                   public void beforeEdit(
                           Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                   {
                       intFilSel = tblDat.getSelectedRow();

                       if (intFilSel != -1)
                       {
                           String strEstDoc = "P";

                           if (Boolean.TRUE.equals(
                                   tblDat.getValueAt(
                                       intFilSel,
                                       INT_TBL_DAT_CHKAUTTOT
                                   )
                           ))
                           {
                               strEstDoc = "T";
                           }

                           objAutLiq.setParDlg(
                                objParSis,
                                ZafRecHum45.this,

                                Integer.parseInt(
                                        getValTbl(
                                                intFilSel,
                                                INT_TBL_DAT_COEMP
                                        )
                                ),

                                Integer.parseInt(
                                        getValTbl(
                                                intFilSel,
                                                INT_TBL_DAT_CODLOC
                                        )
                                ),

                                Integer.parseInt(
                                        getValTbl(
                                                intFilSel,
                                                INT_TBL_DAT_CODTIPDOC
                                        )
                                ),

                                Integer.parseInt(
                                        getValTbl(
                                                intFilSel,
                                                INT_TBL_DAT_CODDOC
                                        )
                                ),

                                Integer.parseInt(
                                        getValTbl(
                                                intFilSel,
                                                INT_TBL_DAT_NUMDOC
                                        )
                                ),

                                getValTbl(
                                        intFilSel,
                                        INT_TBL_DAT_FECDOC
                                ),

                                strEstDoc
                        );

                        objAutLiq.cargarReg();
                           
                       }
                   }

                   public void afterEdit(
                           Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                   {
                       /*
                        * Si se autorizó o denegó, volver a consultar
                        * para actualizar el estado mostrado.
                        */
                       if (objAutLiq.getOpcSelDlg() == 1)
                       {
                           objTblMod.removeAllRows();

                           blnCon = true;

                           cargarReg();
                       }
                   }
               }
           );
            

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void abrirLiquidacion(
            String strCodEmp,
            String strCodLoc,
            String strCodTipDoc,
            String strCodDoc,
            boolean blnAbrirReporte)
    {
        try
        {
            RecursosHumanos.ZafRecHum44.ZafRecHum44 objLiq =
                    new RecursosHumanos.ZafRecHum44.ZafRecHum44(
                            objParSis
                    );

            this.getParent().add(
                    objLiq,
                    javax.swing.JLayeredPane.DEFAULT_LAYER
            );

            objLiq.show();

            boolean blnCar = objLiq.cargarLiquidacionDesdeAutorizacion(
                    Integer.parseInt(strCodEmp),
                    Integer.parseInt(strCodLoc),
                    Integer.parseInt(strCodTipDoc),
                    Integer.parseInt(strCodDoc),
                    blnAbrirReporte
            );

            if (!blnCar)
            {
                objLiq.dispose();
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(
                    this,
                    e
            );
        }
    }
    
     private String getValTbl(int fila, int columna)
    {
        Object objVal = objTblMod.getValueAt(fila, columna);
        return (objVal == null) ? "" : objVal.toString();
    }
     
     
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_COEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_EMP:
                    strMsg = "Empresa";
                    break;
                case INT_TBL_DAT_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg = "Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOC:
                    strMsg = "Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOC:
                    strMsg = "Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg = "Código del documento";
                    break;
                                        
                case INT_TBL_DAT_NUMDOC:
                    strMsg = "Número de Solicitud";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                    
                case INT_TBL_DAT_EMPLE_SOLI:
                    strMsg = "Fecha del documento";
                    break;
                                       
                case INT_TBL_DAT_BUTLIQHAB:
                    strMsg = "Muestra la solicitud";
                    break;
                case INT_TBL_DAT_CHKAUTPEN:
                    strMsg = "Autorización pendiente";
                    break;                     
                case INT_TBL_DAT_CHKAUTTOT:
                    strMsg = "Autorización total";
                    break;               
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
 
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                if (cargarDetReg()) {
                }
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean cargarDetReg()
    {
        boolean blnRes = true;
        int intNumReg = 0;

        strAux = "";
        String strEstConf = "";

        try
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null)
            {
                /*
                 * Filtro de fechas.
                 */
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0:
                        strAux += " AND a.fe_doc BETWEEN '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaDesde(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "' AND '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaHasta(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "' ";
                        break;

                    case 1:
                        strAux += " AND a.fe_doc <= '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaHasta(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "' ";
                        break;

                    case 2:
                        strAux += " AND a.fe_doc >= '"
                                + objUti.formatearFecha(
                                        objSelFec.getFechaDesde(),
                                        objSelFec.getFormatoFecha(),
                                        objParSis.getFormatoFechaBaseDatos()
                                )
                                + "' ";
                        break;

                    case 3:
                        break;
                }

                /*
                 * Filtro por estado.
                 *
                 * P = Pendiente
                 * T = Autorizada totalmente
                 */
                if (optFil.isSelected())
                {
                    if (chkMosDocPenAut.isSelected())
                    {
                        strEstConf = "'P'";
                    }

                    if (chkMosDocTotAut.isSelected())
                    {
                        if (strEstConf.equals(""))
                        {
                            strEstConf = "'T'";
                        }
                        else
                        {
                            strEstConf += ",'T'";
                        }
                    }

                    if (strEstConf.equals(""))
                    {
                        mostrarMsgInf(
                                "Seleccione al menos un estado de autorización."
                        );

                        return false;
                    }

                    strAux += " AND a.st_aut IN ("
                            + strEstConf
                            + ") ";
                }

                if (optTod.isSelected())
                {
                    strAux += " AND a.st_aut IN ('P','T') ";
                }

                stm = con.createStatement();

                strSQL = "";

                strSQL += "SELECT ";
                strSQL += "    a.co_emp, ";
                strSQL += "    a.co_loc, ";
                strSQL += "    a.co_tipdoc, ";
                strSQL += "    a.co_doc, ";
                strSQL += "    a.ne_numdoc, ";
                strSQL += "    a.fe_doc, ";
                strSQL += "    a.co_tra, ";
                strSQL += "    emp.tx_nom AS empresa, ";
                strSQL += "    tip.tx_descor, ";
                strSQL += "    tip.tx_deslar, ";

                strSQL += "    TRIM(";
                strSQL += "        COALESCE(tra.tx_ape, '') ";
                strSQL += "        || ' ' || ";
                strSQL += "        COALESCE(tra.tx_nom, '')";
                strSQL += "    ) AS empleado, ";

                strSQL += "    CASE a.st_aut ";
                strSQL += "        WHEN 'P' THEN 'PENDIENTE' ";
                strSQL += "        WHEN 'T' THEN 'AUTORIZADA' ";
                strSQL += "        WHEN 'D' THEN 'DENEGADA' ";
                strSQL += "        ELSE 'PENDIENTE' ";
                strSQL += "    END AS estado_autorizacion ";

                strSQL += "FROM tbm_cabliqhab AS a ";

                strSQL += "INNER JOIN tbm_tra AS tra ";
                strSQL += "    ON tra.co_tra = a.co_tra ";

                strSQL += "INNER JOIN tbm_emp AS emp ";
                strSQL += "    ON emp.co_emp = a.co_emp ";
                strSQL += "   AND emp.st_reg = 'A' ";

                strSQL += "INNER JOIN tbm_cabtipdoc AS tip ";
                strSQL += "    ON tip.co_emp = a.co_emp ";
                strSQL += "   AND tip.co_loc = a.co_loc ";
                strSQL += "   AND tip.co_tipdoc = a.co_tipdoc ";

                strSQL += "WHERE a.st_reg = 'A' ";
                strSQL += "  AND a.co_tipdoc = "
                        + intCodTipDocLiq
                        + " ";

                strSQL += strAux;

                /*
                 * Cuando no se está conectado a la empresa grupo,
                 * se limita al local actual.
                 */
                if (objParSis.getCodigoEmpresa()
                        != objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL += " AND a.co_emp = "
                            + objParSis.getCodigoEmpresa()
                            + " ";

                    strSQL += " AND a.co_loc = "
                            + objParSis.getCodigoLocal()
                            + " ";
                }

                strSQL += "ORDER BY ";
                strSQL += "    a.fe_doc, ";
                strSQL += "    a.co_doc ";

                System.out.println(
                        "SQL cargarDetReg liquidaciones: "
                        + strSQL
                );

                rst = stm.executeQuery(strSQL);

                vecDat.clear();

                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);

                while (rst.next() && blnCon)
                {
                    vecReg = new Vector();

                    vecReg.add(
                            INT_TBL_DAT_LIN,
                            ""
                    );

                    vecReg.add(
                            INT_TBL_DAT_COEMP,
                            rst.getInt("co_emp")
                    );

                    vecReg.add(
                            INT_TBL_DAT_EMP,
                            rst.getString("empresa")
                    );

                    vecReg.add(
                            INT_TBL_DAT_CODLOC,
                            rst.getInt("co_loc")
                    );

                    vecReg.add(
                            INT_TBL_DAT_CODTIPDOC,
                            rst.getInt("co_tipdoc")
                    );

                    vecReg.add(
                            INT_TBL_DAT_DESCORTIPDOC,
                            rst.getString("tx_descor")
                    );

                    vecReg.add(
                            INT_TBL_DAT_DESLARTIPDOC,
                            rst.getString("tx_deslar")
                    );

                    vecReg.add(
                            INT_TBL_DAT_CODDOC,
                            rst.getInt("co_doc")
                    );

                    vecReg.add(
                            INT_TBL_DAT_NUMDOC,
                            rst.getInt("ne_numdoc")
                    );

                    vecReg.add(
                            INT_TBL_DAT_FECDOC,
                            rst.getString("fe_doc")
                    );

                    vecReg.add(
                            INT_TBL_DAT_EMPLE_SOLI,
                            "<html><b>"
                            + rst.getString("empleado")
                            + "</b></html>"
                    );

                    /*
                     * Botón para abrir ZafRecHum44
                     * en la pestaña General.
                     */
                    vecReg.add(INT_TBL_DAT_BUTLIQHAB,
                            "..."
                    );

                    String strEstAut =
                            rst.getString(
                                    "estado_autorizacion"
                            );

                    if ("PENDIENTE".equals(strEstAut))
                    {
                        vecReg.add(
                                INT_TBL_DAT_CHKAUTPEN,
                                true
                        );

                        vecReg.add(
                                INT_TBL_DAT_CHKAUTTOT,
                                false
                        );
                    }
                    else if ("AUTORIZADA".equals(strEstAut))
                    {
                        vecReg.add(
                                INT_TBL_DAT_CHKAUTPEN,
                                false
                        );

                        vecReg.add(
                                INT_TBL_DAT_CHKAUTTOT,
                                true
                        );
                    }
                    else
                    {
                        vecReg.add(
                                INT_TBL_DAT_CHKAUTPEN,
                                false
                        );

                        vecReg.add(
                                INT_TBL_DAT_CHKAUTTOT,
                                false
                        );
                    }

                    /*
                     * Botón para abrir autorizadores.
                     */
                    vecReg.add(
                            INT_TBL_DAT_BUTANE2,
                            "..."
                    );

                    /*
                     * Código del trabajador oculto.
                     */
                    vecReg.add(
                            INT_TBL_DAT_CO_TRA,
                            rst.getInt("co_tra")
                    );

                    /*
                     * Botón para abrir ZafRecHum44
                     * directamente en Reporte.
                     */
                    vecReg.add(
                            INT_TBL_DAT_BUTANEREP,
                            "..."
                    );

                    vecDat.add(vecReg);

                    intNumReg++;
                    pgrSis.setValue(intNumReg);
                }

                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                objTblMod.initRowsState();

                lblMsgSis.setText(
                        "Se encontraron "
                        + intNumReg
                        + " registros."
                );
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
                {
                    rst.close();
                    rst = null;
                }

                if (stm != null)
                {
                    stm.close();
                    stm = null;
                }
            }
            catch (Exception e)
            {
            }

            pgrSis.setValue(0);
            butCon.setText("Consultar");
        }

        return blnRes;
    } 
    
}
