package RecursosHumanos.ZafRecHum44;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import RecursosHumanos.ZafRecHum44.ZafSueBas;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafToolBar.ZafToolBar;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
import java.time.Period;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Locale;



public class ZafRecHum44 extends javax.swing.JInternalFrame {

    private final String strVer = " v 1.04";
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    
    private String strAux = "";
    private boolean blnHayCam = false;
    
    private MiToolBar objTooBar;
        
    private ZafVenCon vcoTra;
    private String strCodTra = "";
    private String strNomTra = "";
    private String strIdeTra = "";
    
    private static final int INT_TBL_LIN = 0;
    private static final int INT_TBL_CON = 1;
    private static final int INT_TBL_BAS = 2;
    private static final int INT_TBL_DET = 3;
    private static final int INT_TBL_ING = 4;
    private static final int INT_TBL_EGR = 5;
    
    private static final int INT_COD_TIP_DOC_LIQ_HAB = 325;
    
    /*
    * Usuario autorizador inicial de liquidaciones.
    */
   private static final int INT_COD_USR_AUT_LIQ = 156;

    private int intCodDocGuardado = 0;
    private int intNumDocGuardado = 0;

    private DefaultTableModel objTblModLiq;
    
    private double dblTotIng = 0.00;
    private double dblTotEgr = 0.00;
    private double dblBaseRemLiq = 0.00;
    private boolean blnFuePagadoRolFinMes = false;
    
    private ArrayList<Integer> arrCodRubDetLiq =  new ArrayList<Integer>();
    
    private java.sql.Connection conCab = null;
    private java.sql.Statement stmCab = null;
    private java.sql.ResultSet rstCab = null;
    
    private boolean blnUsarVistaImprimible = true;
   //private boolean blnUsarVistaImprimible = false;
    private PanelVistaLiquidacion panVistaLiquidacion;
    private String strCargoTra = "";
    
    private double dblEscalaVistaReporte = 1.55;
    
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private java.util.Date datFecAux;
    

   
    public ZafRecHum44(ZafParSis obj) {
        try {
            this.objZafParSis = (ZafParSis) obj.clone();
            initComponents();

            objUti = new ZafUtil();
            configurarFormulario();
            configurarVenConTra();
            cargarCausasLiquidacion();

            setTitle(objZafParSis.getNombreMenu() + strVer);
            lblTit.setText(objZafParSis.getNombreMenu());
            
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(60, 20));
            txtFecDoc.setText("");
            txtFecDoc.setEnabled(false);
            panTra.add(txtFecDoc);
            txtFecDoc.setBounds(690, 25, 90, 20);
            txtFecDoc.setEnabled(false);
            txtFecDoc.setBackground(objZafParSis.getColorCamposObligatorios());
            txtFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
            txtFecDoc.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtFecDocActionPerformed(evt);
                }

                private void txtFecDocActionPerformed(ActionEvent evt) {
                    txtFecDoc.transferFocus();
                }
            });
            
             
           
        } catch (Exception ex) {
            ex.printStackTrace();
            if (objUti != null) {
                objUti.mostrarMsgErr_F1(this, ex);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Mensaje del sistema Zafiro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panDatGen = new javax.swing.JPanel();
        panTra = new javax.swing.JPanel();
        lblCodTra = new javax.swing.JLabel();
        lblNomTra = new javax.swing.JLabel();
        butConTra = new javax.swing.JButton();
        txtNomTra = new javax.swing.JTextField();
        txtCedTra = new javax.swing.JTextField();
        txtNumDoc = new javax.swing.JTextField();
        lblCodTra1 = new javax.swing.JLabel();
        lblNomTra1 = new javax.swing.JLabel();
        lblNomTra2 = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        panDatLiq = new javax.swing.JPanel();
        lblCedTra = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        lblFecIniCon = new javax.swing.JLabel();
        txtFecIniCon = new javax.swing.JTextField();
        lblFecFinCon = new javax.swing.JLabel();
        txtFecFinCon = new javax.swing.JTextField();
        lblSueMen = new javax.swing.JLabel();
        txtSueMen = new javax.swing.JTextField();
        lblCausal = new javax.swing.JLabel();
        butCalcular = new javax.swing.JButton();
        lblSueMen1 = new javax.swing.JLabel();
        txtFonRes = new javax.swing.JTextField();
        lblSueMen2 = new javax.swing.JLabel();
        txtDecTer = new javax.swing.JTextField();
        lblSueMen3 = new javax.swing.JLabel();
        txtDecCua = new javax.swing.JTextField();
        lblFecFinCon1 = new javax.swing.JLabel();
        txtDiasTra = new javax.swing.JTextField();
        cmbCau = new javax.swing.JComboBox<>();
        panObs = new javax.swing.JPanel();
        panObsLbl = new javax.swing.JPanel();
        lblObs = new javax.swing.JLabel();
        panObsDet = new javax.swing.JPanel();
        spnObs = new javax.swing.JScrollPane();
        txaObs = new javax.swing.JTextArea();
        panRep = new javax.swing.JPanel();
        panCabRep = new javax.swing.JPanel();
        lblTraRep = new javax.swing.JLabel();
        lblNomTraRep = new javax.swing.JLabel();
        lblFecRep = new javax.swing.JLabel();
        lblFecConRep = new javax.swing.JLabel();
        lblCauRep = new javax.swing.JLabel();
        lblCausalRep = new javax.swing.JLabel();
        spnDetLiq = new javax.swing.JScrollPane();
        tblDetLiq = new javax.swing.JTable();
        panPieRep = new javax.swing.JPanel();
        panTotRep = new javax.swing.JPanel();
        lblTotIng = new javax.swing.JLabel();
        txtTotIng = new javax.swing.JTextField();
        lblTotEgr = new javax.swing.JLabel();
        txtTotEgr = new javax.swing.JTextField();
        lblTotLiq = new javax.swing.JLabel();
        txtTotLiq = new javax.swing.JTextField();
        panBarGen = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(700, 500));
        setPreferredSize(new java.awt.Dimension(700, 500));
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

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Liquidación de Haberes");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panDatGen.setLayout(new java.awt.BorderLayout());

        panTra.setPreferredSize(new java.awt.Dimension(0, 90));
        panTra.setLayout(null);

        lblCodTra.setText("Cédula:");
        panTra.add(lblCodTra);
        lblCodTra.setBounds(10, 25, 125, 20);

        lblNomTra.setText("Nombre:");
        panTra.add(lblNomTra);
        lblNomTra.setBounds(10, 55, 100, 20);

        butConTra.setText("...");
        butConTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConTraActionPerformed(evt);
            }
        });
        panTra.add(butConTra);
        butConTra.setBounds(224, 25, 20, 20);

        txtNomTra.setEditable(false);
        txtNomTra.setBackground(objZafParSis.getColorCamposObligatorios());
        panTra.add(txtNomTra);
        txtNomTra.setBounds(125, 55, 350, 20);

        txtCedTra.setBackground(objZafParSis.getColorCamposSistema());
        txtCedTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCedTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedTraActionPerformed(evt);
            }
        });
        panTra.add(txtCedTra);
        txtCedTra.setBounds(125, 25, 100, 20);

        txtNumDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        panTra.add(txtNumDoc);
        txtNumDoc.setBounds(690, 46, 90, 20);

        lblCodTra1.setText("Fecha Documento:");
        panTra.add(lblCodTra1);
        lblCodTra1.setBounds(550, 25, 110, 20);

        lblNomTra1.setText("Número Documento:");
        panTra.add(lblNomTra1);
        lblNomTra1.setBounds(550, 46, 120, 20);

        lblNomTra2.setText("Códido Documento:");
        panTra.add(lblNomTra2);
        lblNomTra2.setBounds(550, 66, 120, 20);

        txtCodDoc.setBackground(objZafParSis.getColorCamposSistema());
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        panTra.add(txtCodDoc);
        txtCodDoc.setBounds(690, 65, 90, 20);

        panDatGen.add(panTra, java.awt.BorderLayout.NORTH);

        panDatLiq.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del contrato y liquidación"));
        panDatLiq.setLayout(null);

        lblCedTra.setText("Codigo:");
        panDatLiq.add(lblCedTra);
        lblCedTra.setBounds(20, 35, 100, 20);

        txtCodTra.setEditable(false);
        txtCodTra.setBackground(objZafParSis.getColorCamposSistema());
        panDatLiq.add(txtCodTra);
        txtCodTra.setBounds(125, 35, 140, 20);

        lblFecIniCon.setText("Fecha ingreso:");
        panDatLiq.add(lblFecIniCon);
        lblFecIniCon.setBounds(20, 65, 100, 20);

        txtFecIniCon.setEditable(false);
        txtFecIniCon.setBackground(objZafParSis.getColorCamposSistema());
        panDatLiq.add(txtFecIniCon);
        txtFecIniCon.setBounds(125, 65, 140, 20);

        lblFecFinCon.setText("Fecha salida:");
        panDatLiq.add(lblFecFinCon);
        lblFecFinCon.setBounds(20, 95, 100, 20);

        txtFecFinCon.setEditable(false);
        txtFecFinCon.setBackground(objZafParSis.getColorCamposSistema());
        panDatLiq.add(txtFecFinCon);
        txtFecFinCon.setBounds(125, 95, 140, 20);

        lblSueMen.setText("Sueldo:");
        panDatLiq.add(lblSueMen);
        lblSueMen.setBounds(20, 155, 110, 20);

        txtSueMen.setEditable(false);
        txtSueMen.setBackground(objZafParSis.getColorCamposSistema());
        txtSueMen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatLiq.add(txtSueMen);
        txtSueMen.setBounds(125, 155, 140, 20);

        lblCausal.setText("Causal de salida:");
        panDatLiq.add(lblCausal);
        lblCausal.setBounds(420, 60, 120, 20);

        butCalcular.setText("Calcular liquidación");
        butCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCalcularActionPerformed(evt);
            }
        });
        panDatLiq.add(butCalcular);
        butCalcular.setBounds(470, 130, 180, 40);

        lblSueMen1.setText("Fondos Reserva:");
        panDatLiq.add(lblSueMen1);
        lblSueMen1.setBounds(20, 185, 110, 20);

        txtFonRes.setEditable(false);
        txtFonRes.setBackground(objZafParSis.getColorCamposSistema());
        txtFonRes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatLiq.add(txtFonRes);
        txtFonRes.setBounds(125, 185, 140, 20);

        lblSueMen2.setText("Decimo Tercero:");
        panDatLiq.add(lblSueMen2);
        lblSueMen2.setBounds(20, 215, 110, 20);

        txtDecTer.setEditable(false);
        txtDecTer.setBackground(objZafParSis.getColorCamposSistema());
        txtDecTer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatLiq.add(txtDecTer);
        txtDecTer.setBounds(125, 215, 140, 20);

        lblSueMen3.setText("Decimo Cuarto:");
        panDatLiq.add(lblSueMen3);
        lblSueMen3.setBounds(20, 245, 110, 20);

        txtDecCua.setEditable(false);
        txtDecCua.setBackground(objZafParSis.getColorCamposSistema());
        txtDecCua.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatLiq.add(txtDecCua);
        txtDecCua.setBounds(125, 245, 140, 20);

        lblFecFinCon1.setText("Dias trabajados:");
        panDatLiq.add(lblFecFinCon1);
        lblFecFinCon1.setBounds(20, 125, 100, 20);

        txtDiasTra.setEditable(false);
        txtDiasTra.setBackground(objZafParSis.getColorCamposSistema());
        panDatLiq.add(txtDiasTra);
        txtDiasTra.setBounds(125, 125, 140, 20);

        panDatLiq.add(cmbCau);
        cmbCau.setBounds(420, 80, 270, 20);

        panDatGen.add(panDatLiq, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 80));
        panObs.setLayout(new java.awt.BorderLayout());

        panObsLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panObsLbl.setLayout(new java.awt.BorderLayout());

        lblObs.setText("Observación:");
        lblObs.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panObsLbl.add(lblObs, java.awt.BorderLayout.CENTER);

        panObs.add(panObsLbl, java.awt.BorderLayout.WEST);

        panObsDet.setLayout(new java.awt.BorderLayout());

        txaObs.setLineWrap(true);
        txaObs.setWrapStyleWord(true);
        spnObs.setViewportView(txaObs);

        panObsDet.add(spnObs, java.awt.BorderLayout.CENTER);

        panObs.add(panObsDet, java.awt.BorderLayout.CENTER);

        panDatGen.add(panObs, java.awt.BorderLayout.SOUTH);

        panGen.add(panDatGen, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panRep.setLayout(new java.awt.BorderLayout());

        panCabRep.setPreferredSize(new java.awt.Dimension(0, 75));
        panCabRep.setLayout(null);

        lblTraRep.setText("Trabajador:");
        panCabRep.add(lblTraRep);
        lblTraRep.setBounds(10, 10, 80, 20);

        lblNomTraRep.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblNomTraRep.setText("-");
        panCabRep.add(lblNomTraRep);
        lblNomTraRep.setBounds(95, 10, 540, 20);

        lblFecRep.setText("Contrato:");
        panCabRep.add(lblFecRep);
        lblFecRep.setBounds(10, 40, 80, 20);

        lblFecConRep.setText("-");
        panCabRep.add(lblFecConRep);
        lblFecConRep.setBounds(95, 40, 270, 20);

        lblCauRep.setText("Causal:");
        panCabRep.add(lblCauRep);
        lblCauRep.setBounds(390, 40, 60, 20);

        lblCausalRep.setText("-");
        panCabRep.add(lblCausalRep);
        lblCausalRep.setBounds(450, 40, 190, 20);

        panRep.add(panCabRep, java.awt.BorderLayout.PAGE_START);

        spnDetLiq.setViewportView(tblDetLiq);

        panRep.add(spnDetLiq, java.awt.BorderLayout.CENTER);

        panPieRep.setLayout(new java.awt.BorderLayout());

        panTotRep.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panTotRep.setPreferredSize(new java.awt.Dimension(0, 95));
        panTotRep.setLayout(null);

        lblTotIng.setText("Total ingresos:");
        panTotRep.add(lblTotIng);
        lblTotIng.setBounds(520, 10, 100, 20);

        txtTotIng.setEditable(false);
        txtTotIng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotIng.setText("0.00");
        panTotRep.add(txtTotIng);
        txtTotIng.setBounds(650, 10, 130, 20);

        lblTotEgr.setText("Total egresos:");
        panTotRep.add(lblTotEgr);
        lblTotEgr.setBounds(520, 35, 100, 20);

        txtTotEgr.setEditable(false);
        txtTotEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotEgr.setText("0.00");
        panTotRep.add(txtTotEgr);
        txtTotEgr.setBounds(650, 35, 130, 20);

        lblTotLiq.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTotLiq.setText("TOTAL A PAGAR:");
        panTotRep.add(lblTotLiq);
        lblTotLiq.setBounds(520, 65, 110, 20);

        txtTotLiq.setEditable(false);
        txtTotLiq.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        txtTotLiq.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotLiq.setText("0.00");
        panTotRep.add(txtTotLiq);
        txtTotLiq.setBounds(650, 65, 130, 22);

        panPieRep.add(panTotRep, java.awt.BorderLayout.CENTER);

        panRep.add(panPieRep, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRep);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarGen.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarGen, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 816, 605);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit = "Mensaje del sistema Zafiro";
        String strMsg = "¿Está seguro que desea cerrar este programa?";

        if (JOptionPane.showConfirmDialog(
                this,
                strMsg,
                strTit,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConTraActionPerformed
        // En el siguiente paso se conectará la ventana ZafVenCon de empleados.
        strCodTra = txtCodTra.getText();
        strNomTra = txtNomTra.getText();
        strIdeTra = txtCedTra.getText().trim();

        mostrarVenConTra(0);
    }//GEN-LAST:event_butConTraActionPerformed

    private void butCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCalcularActionPerformed
        if (!validarDatosCalculo()) {
            return;
        }
         
        intCodDocGuardado = 0;
        intNumDocGuardado = 0;

        if (calcularLiquidacion()) {
            mostrarPestanaReporte();
        }
    }//GEN-LAST:event_butCalcularActionPerformed

    private void txtCedTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedTraActionPerformed
        // TODO add your handling code here:
         strCodTra = txtCodTra.getText().trim();
        strIdeTra = txtCedTra.getText().trim();
        strNomTra = txtNomTra.getText().trim();

        if (!txtCedTra.getText().trim().equals(""))
        {        
            mostrarVenConTra(1);
        }
    }//GEN-LAST:event_txtCedTraActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodDocActionPerformed

    /**
     * Configuraciones iniciales del formulario.
     */
    private void configurarFormulario() {        

        cmbCau.setSelectedIndex(-1);
        
        txtCedTra.setEditable(true);
        txtCedTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCedTra.setBackground(objZafParSis.getColorCamposObligatorios());  
        
                
        txtNumDoc.setEditable(false);
        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        
        txtCodDoc.setEditable(false);
        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.setBackground(objZafParSis.getColorCamposObligatorios());
        
        txtNomTra.setEditable(false);
        txtCodTra.setEditable(false);
        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodTra.setBackground(objZafParSis.getColorCamposSistema());
        
        txtSueMen.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFonRes.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDecTer.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDecCua.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        
        txtFecIniCon.setEditable(false);
        txtFecFinCon.setEditable(false);
        txtSueMen.setEditable(false);

        txtTotIng.setEditable(false);
        txtTotEgr.setEditable(false);
        txtTotLiq.setEditable(false);   
        
        txtDiasTra.setEditable(false);
        txtFonRes.setEditable(false);
        txtDecTer.setEditable(false);
        txtDecCua.setEditable(false);
        
        objTblModLiq = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "#",
                    "Concepto",
                    "Base",
                    "Detalle",
                    "Ingreso",
                    "Descuento"
                }
        ) {
            private final Class[] types = new Class[]{
                java.lang.Integer.class,
                java.lang.String.class,
                java.lang.Double.class,
                java.lang.String.class,
                java.lang.Double.class,
                java.lang.Double.class
            };

            private final boolean[] canEdit = new boolean[]{
                false,
                false,
                false,
                false,
                false,
                false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(
                    int rowIndex,
                    int columnIndex) {

                return canEdit[columnIndex];
            }
        };

        tblDetLiq.setModel(objTblModLiq);

        tblDetLiq.setAutoResizeMode(
                javax.swing.JTable.AUTO_RESIZE_OFF
        );

        tblDetLiq.getTableHeader().setReorderingAllowed(false);

        javax.swing.table.TableColumnModel tcmAux = tblDetLiq.getColumnModel();

        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(35);
        tcmAux.getColumn(INT_TBL_CON).setPreferredWidth(280);
        tcmAux.getColumn(INT_TBL_BAS).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_DET).setPreferredWidth(210);
        tcmAux.getColumn(INT_TBL_ING).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_EGR).setPreferredWidth(90);

        /*
        * Base alineada a la izquierda.
        */
        javax.swing.table.DefaultTableCellRenderer objRenIzq =
               new javax.swing.table.DefaultTableCellRenderer();

        objRenIzq.setHorizontalAlignment(javax.swing.JLabel.LEFT );

        tcmAux.getColumn(INT_TBL_BAS).setCellRenderer(objRenIzq);

        /*
         * Ingreso y descuento alineados a la derecha.
         */
        javax.swing.table.DefaultTableCellRenderer objRenDer =
                new javax.swing.table.DefaultTableCellRenderer();

        objRenDer.setHorizontalAlignment(
                javax.swing.JLabel.RIGHT
        );

        tcmAux.getColumn(INT_TBL_ING).setCellRenderer(objRenDer);
        tcmAux.getColumn(INT_TBL_EGR).setCellRenderer(objRenDer);       
        

        tabFrm.setEnabledAt(1, false);
        
        configurarBarraHerramientas();
        configurarVistaReporte();
        
    }

    /**
     * Valida los datos mínimos antes de abrir el reporte.
     */
    private boolean validarDatosCalculo() {
        if (txtCodTra.getText().trim().equals("")) {
            mostrarMsgInf("Debe seleccionar un trabajador.");
            txtCodTra.requestFocus();
            return false;
        }

        if (cmbCau.getSelectedIndex() == -1) {
            mostrarMsgInf("Debe seleccionar la causal de salida.");
            cmbCau.requestFocus();
            return false;
        }

        if (txtFecFinCon.getText().trim().equals("")) {
            mostrarMsgInf("El trabajador seleccionado no tiene fecha de salida.");
            return false;
        }

        return true;
    }

    /**
     * Abre la pestaña Reporte y presenta la cabecera seleccionada.
     */
    public void mostrarPestanaReporte() {
        String strCausal = String.valueOf(cmbCau.getSelectedItem());

        lblNomTraRep.setText(
                txtCodTra.getText().trim() + " - " + txtNomTra.getText().trim()
        );
        lblFecConRep.setText(
                txtFecIniCon.getText().trim() + " al " + txtFecFinCon.getText().trim()
        );
        lblCausalRep.setText(strCausal);

        tabFrm.setEnabledAt(1, true);
        tabFrm.setSelectedIndex(1);
        
        actualizarVistaReporte();
    }

    /**
     * Regresa a la pestaña General.
     */
    public void mostrarPestanaGeneral() {
        tabFrm.setSelectedIndex(0);
    }

    /**
     * Limpia la tabla y bloquea el reporte.
     */
    public void bloquearPestanaReporte() {

        if (objTblModLiq != null) {
            objTblModLiq.setRowCount(0);
        }

        txtTotIng.setText("0.00");
        txtTotEgr.setText("0.00");
        txtTotLiq.setText("0.00");

        tabFrm.setSelectedIndex(0);
        tabFrm.setEnabledAt(1, false);
    }

    /**
     * Método auxiliar para cargar los datos del trabajador seleccionado.
     */
    public void cargarDatosTrabajador(
            String strCodTra,
            String strNomTra,
            String strCedTra,
            String strFecIni,
            String strFecFin,
            String strSueMen) {

        txtCodTra.setText(strCodTra == null ? "" : strCodTra);
        txtNomTra.setText(strNomTra == null ? "" : strNomTra);
        txtCodTra.setText(strCedTra == null ? "" : strCedTra);
        txtFecIniCon.setText(strFecIni == null ? "" : strFecIni);
        txtFecFinCon.setText(strFecFin == null ? "" : strFecFin);
        txtSueMen.setText(strSueMen == null ? "" : strSueMen);

        bloquearPestanaReporte();
    }

    public void agregarFilaReporte(
            String strConcepto,
            Double dblBase,
            String strDetalle,
            Double dblIngreso,
            Double dblDescuento)
    {
        /*
         * Los conceptos calculados como décimos, vacaciones,
         * desahucio y despido no tienen código de rubro.
         */
        agregarFilaReporte(
            strConcepto,
            dblBase,
            strDetalle,
            dblIngreso,
            dblDescuento,
            null
        );
    }
    
    public void agregarFilaReporte(
        String strConcepto,
        Double dblBase,
        String strDetalle,
        Double dblIngreso,
        Double dblDescuento,
        Integer intCodRub)
    {
        int intLinea =
                objTblModLiq.getRowCount() + 1;

        objTblModLiq.addRow(
            new Object[]{
                intLinea,
                strConcepto,
                dblBase,
                strDetalle,
                dblIngreso,
                dblDescuento
            }
        );

        /*
         * Guarda internamente el código del rubro
         * en la misma posición de la fila.
         */
        arrCodRubDetLiq.add(
            intCodRub
        );
    }

    /**
     * Establece los totales del reporte.
     */
    public void establecerTotales(double dblTotIng, double dblTotEgr) {

        double dblTotPag = dblTotIng - dblTotEgr;

        txtTotIng.setText(
            String.valueOf(
                objUti.redondear(dblTotIng, 2)
            )
        );

        txtTotEgr.setText(
            String.valueOf(
                objUti.redondear(dblTotEgr, 2)
            )
        );

        txtTotLiq.setText(
            String.valueOf(
                objUti.redondear(dblTotPag, 2)
            )
        );
    }

    /**
     * Devuelve R para renuncia y D para despido.
     */
    public String getCausalSeleccionada()
    {
        if (cmbCau.getSelectedIndex() == -1)
        {
            return "";
        }

        CausaLiquidacion objCau =
                (CausaLiquidacion) cmbCau.getSelectedItem();

        return objCau.getTipoCalculo();
    }

    /**
     * Limpia el formulario.
     */
    public void limpiarFrm() {
        txtCedTra.setText("");
        txtNomTra.setText("");
        txtCodTra.setText("");
        txtFecIniCon.setText("");
        txtFecFinCon.setText("");
        txtSueMen.setText("");
        
        txtDiasTra.setText("");
        txtFonRes.setText("");
        txtDecTer.setText("");
        txtDecCua.setText("");
        
        txtNumDoc.setText("");
        txtCodDoc.setText("");

        cmbCau.setSelectedIndex(-1);
        
        txaObs.setText("");
        
        bloquearPestanaReporte();
        txtCodTra.requestFocus();
    }
    
    private int obtenerCodigoEmpresa() {

        int intCodEmp = objZafParSis.getCodigoEmpresa();

        /*
         * En Recursos Humanos normalmente se trabaja con la empresa 1.
         * Si el programa fue abierto desde la empresa grupo, se consulta la 1.
         */
        if (intCodEmp == objZafParSis.getCodigoEmpresaGrupo()) {
            intCodEmp = 1;
        }

        return intCodEmp;
    }
    
    private boolean configurarVenConTra() {

        boolean blnRes = true;
        String strSQL = "";

        try {

            int intCodEmp = obtenerCodigoEmpresa();

            /*
             * Campos que devolverá la ventana.
             */
            ArrayList arlCam = new ArrayList();

            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.fe_inicon");
            arlCam.add("a1.fe_fincon");

            /*
             * Nombres visibles de las columnas.
             */
            ArrayList arlAli = new ArrayList();

            arlAli.add("Código");
            arlAli.add("Cédula");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            arlAli.add("Fecha ingreso");
            arlAli.add("Fecha salida");

            /*
             * Anchos de las columnas.
             */
            ArrayList arlAncCol = new ArrayList();

            arlAncCol.add("60");
            arlAncCol.add("100");
            arlAncCol.add("180");
            arlAncCol.add("180");
            arlAncCol.add("90");
            arlAncCol.add("90");

            /*
             * Se obtiene el último contrato inactivo de cada trabajador.
             * Solo aparecen exempleados que tengan fecha de salida.
             */
            strSQL = "";

            strSQL += "SELECT ";
            strSQL += "    a1.co_tra, ";
            strSQL += "    a1.tx_ide, ";
            strSQL += "    a1.tx_ape, ";
            strSQL += "    a1.tx_nom, ";
            strSQL += "    a1.fe_inicon, ";
            strSQL += "    a1.fe_fincon ";
            strSQL += "FROM ( ";

            strSQL += "    SELECT DISTINCT ON (a.co_tra) ";
            strSQL += "        a.co_tra, ";
            strSQL += "        a.tx_ide, ";
            strSQL += "        a.tx_ape, ";
            strSQL += "        a.tx_nom, ";
            strSQL += "        b.fe_inicon, ";
            strSQL += "        b.fe_fincon ";

            strSQL += "    FROM tbm_tra AS a ";
            strSQL += "    INNER JOIN tbm_traemp AS b ";
            strSQL += "        ON b.co_tra = a.co_tra ";

            strSQL += "    WHERE b.co_emp = " + intCodEmp;
            strSQL += "      AND b.st_reg = 'I' ";
            strSQL += "      AND b.fe_fincon IS NOT NULL ";

            strSQL += "    ORDER BY ";
            strSQL += "        a.co_tra, ";
            strSQL += "        b.fe_fincon DESC, ";
            strSQL += "        b.fe_inicon DESC ";

            strSQL += ") AS a1 ";

            strSQL += "ORDER BY ";
            strSQL += "    a1.tx_ape, ";
            strSQL += "    a1.tx_nom";

            System.out.println(
                "SQL ventana exempleados: " + strSQL
            );

            vcoTra = new ZafVenCon(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objZafParSis,
                "Listado de exempleados",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );

            vcoTra.setConfiguracionColumna(
                1,
                javax.swing.JLabel.RIGHT
            );

            arlCam = null;
            arlAli = null;
            arlAncCol = null;

        } catch (Exception e) {

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean mostrarVenConTra(
            int intTipBus)
    {
        boolean blnRes = true;

        try
        {
            switch (intTipBus)
            {
                case 0:
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.setVisible(true);

                    if (vcoTra.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtCedTra.setText(vcoTra.getValueAt(2));
                        cargarDatosTrabajadorSeleccionado();
                    }
                    break;
                case 1:

                    if (vcoTra.buscar("a1.tx_ide",txtCedTra.getText().trim()))
                    {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtCedTra.setText(vcoTra.getValueAt(2));
                        cargarDatosTrabajadorSeleccionado();
                    }
                    else
                    {                        
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.setVisible(true);

                        if (vcoTra.getSelectedButton()== ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtCedTra.setText(vcoTra.getValueAt(2));
                            cargarDatosTrabajadorSeleccionado();
                        }
                        else
                        {                            
                            txtCodTra.setText(strCodTra);
                            txtCedTra.setText(strIdeTra);
                            txtNomTra.setText(strNomTra);
                        }
                    }

                    break;
            }
        }
        catch (Exception e)
        {
            blnRes = false;

            objUti.mostrarMsgErr_F1(
                this,
                e
            );
        }

        return blnRes;
    }

    
    private boolean cargarDatosTrabajadorSeleccionado() {

        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try {

            if (txtCodTra.getText().trim().equals("")) {
                return false;
            }

            int intCodEmp = obtenerCodigoEmpresa();

            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null) {

                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    a.co_tra, ";
                strSQL += "    a.tx_ide, ";
                strSQL += "    a.tx_ape, ";
                strSQL += "    a.tx_nom, ";
                strSQL += "    b.fe_inicon, ";
                strSQL += "    b.fe_fincon, ";

                strSQL += "    COALESCE(b.st_recfonres,'') ";
                strSQL += "        AS st_recfonres, ";

                strSQL += "    COALESCE(b.tx_forrecfonres,'') ";
                strSQL += "        AS tx_forrecfonres, ";

                strSQL += "    COALESCE(b.tx_forpagdectersue,'') ";
                strSQL += "        AS tx_forpagdectersue, ";

                strSQL += "    COALESCE(b.tx_forpagdeccuasue,'') ";
                strSQL += "        AS tx_forpagdeccuasue, ";

                strSQL += "    COALESCE(c.nd_valrub, 0) AS sueldo ";

                strSQL += "FROM tbm_tra AS a ";

                strSQL += "INNER JOIN tbm_traemp AS b ";
                strSQL += "    ON b.co_tra = a.co_tra ";

                strSQL += "LEFT JOIN tbm_suetra AS c ";
                strSQL += "    ON c.co_emp = b.co_emp ";
                strSQL += "   AND c.co_tra = b.co_tra ";
                strSQL += "   AND c.co_rub = 1 ";

                strSQL += "WHERE a.co_tra = " + intCodTra;
                strSQL += "  AND b.co_emp = " + intCodEmp;
                strSQL += "  AND b.st_reg = 'I' ";
                strSQL += "  AND b.fe_fincon IS NOT NULL ";

                strSQL += "ORDER BY ";
                strSQL += "    b.fe_fincon DESC, ";
                strSQL += "    b.fe_inicon DESC ";

                strSQL += "LIMIT 1";

                System.out.println(
                    "SQL datos exempleado: " + strSQL
                );

                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next()) {

                    SimpleDateFormat objForFec =  new SimpleDateFormat("dd/MM/yyyy");

                    txtCodTra.setText(rstLoc.getString("co_tra"));
                    txtNomTra.setText(rstLoc.getString("tx_ape")+ " "+ rstLoc.getString("tx_nom"));
                    txtCedTra.setText(rstLoc.getString("tx_ide"));
                    
                    if (rstLoc.getDate("fe_inicon") != null) {
                        txtFecIniCon.setText(objForFec.format(rstLoc.getDate("fe_inicon")));
                    }else {
                        txtFecIniCon.setText("");
                    }
                    if (rstLoc.getDate("fe_fincon") != null) {

                        txtFecFinCon.setText(objForFec.format(rstLoc.getDate("fe_fincon")));
                    } else {
                        txtFecFinCon.setText("");
                    }
                    
                    /*
                    * Días trabajados 
                    */
                    java.sql.Date datFecIniSQL = rstLoc.getDate("fe_inicon");
                    java.sql.Date datFecFinSQL = rstLoc.getDate("fe_fincon");

                    if (datFecIniSQL != null && datFecFinSQL != null)
                    {
                        LocalDate datFecIniCon = datFecIniSQL.toLocalDate();
                        LocalDate datFecFinCon = datFecFinSQL.toLocalDate();                       
                        LocalDate datFecIniMes = datFecFinCon.withDayOfMonth(1);
                        LocalDate datFecDes;

                        /*
                         * Si ingresó durante el mismo mes en que salió,
                         * se empieza a contar desde la fecha real
                         * de ingreso.
                         *
                         * Caso contrario, desde el día 1 del mes.
                         */
                        if (datFecIniCon.isAfter(datFecIniMes))
                        {
                            datFecDes = datFecIniCon;
                        }
                        else
                        {
                            datFecDes =  datFecIniMes;
                        }

                        int intDiasTra = calcularDiasBase360(datFecDes,datFecFinCon);
                        txtDiasTra.setText(String.valueOf(intDiasTra));
                        System.out.println(
                            "Días trabajados último mes: desde="
                            + datFecDes
                            + ", hasta="
                            + datFecFinCon
                            + ", días="
                            + intDiasTra
                        );
                    }
                    else
                    {
                        txtDiasTra.setText("");
                    }

                    txtSueMen.setText(
                        ""
                        + objUti.redondear(
                            rstLoc.getDouble("sueldo"),
                            2
                        )
                    );
                    
                    if (datFecIniSQL != null && datFecFinSQL != null)
                    {
                        LocalDate datFecIni = datFecIniSQL.toLocalDate();
                        LocalDate datFecFin = datFecFinSQL.toLocalDate();
                        boolean blnCumplioAnio = !datFecFin.isBefore(datFecIni.plusYears(1));
                        String strRecFonRes = rstLoc.getString("st_recfonres");
                        String strForRecFonRes = rstLoc.getString("tx_forrecfonres");

                        if (strRecFonRes == null)
                        {
                            strRecFonRes = "";
                        }

                        if (strForRecFonRes == null)
                        {
                            strForRecFonRes = "";
                        }

                        if (!blnCumplioAnio)
                        {
                            txtFonRes.setText("N/A");
                        }
                        else if ("S".equalsIgnoreCase(strRecFonRes.trim()) && "R".equalsIgnoreCase(strForRecFonRes.trim()))
                        {
                            txtFonRes.setText("Mensualiza");
                        }
                        else
                        {
                            txtFonRes.setText("Acumula");
                        }
                    }
                    else
                    {
                        txtFonRes.setText("");
                    }

                    /*
                     * DÉCIMO TERCERO.
                     */
                    String strDecTer = rstLoc.getString("tx_forpagdectersue");

                    if (strDecTer == null)
                    {
                        strDecTer = "";
                    }

                    if ("M".equalsIgnoreCase(strDecTer.trim()))
                    {
                        txtDecTer.setText("Mensualiza");
                    }
                    else
                    {
                        txtDecTer.setText("Acumula");
                    }

                    /*
                     * DÉCIMO CUARTO.
                     */
                    String strDecCua = rstLoc.getString("tx_forpagdeccuasue");
                    
                    if (strDecCua == null)
                    {
                        strDecCua = "";
                    }

                    if ("M".equalsIgnoreCase(strDecCua.trim()))
                    {
                        txtDecCua.setText("Mensualiza");
                    }
                    else
                    {
                        txtDecCua.setText("Acumula");
                    }
                    
                    
                    txaObs.setText("");
                    bloquearPestanaReporte();
                    blnRes = true;
                } else {
                    mostrarMsgInf("No se encontraron datos del exempleado.");
                    limpiarDatosTrabajador();
                }
            }

        } catch (java.sql.SQLException e) {

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);

            limpiarDatosTrabajador();

        } catch (Exception e) {

            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);

            limpiarDatosTrabajador();

        } finally {

            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    private void limpiarDatosTrabajador() {

        intCodDocGuardado = 0;
        intNumDocGuardado = 0;
        txtCodTra.setText("");
        txtNomTra.setText("");
        txtCodTra.setText("");
        txtFecIniCon.setText("");
        txtFecFinCon.setText("");
        txtSueMen.setText("");
        
        txtDiasTra.setText("");
        txtFonRes.setText("");
        txtDecTer.setText("");
        txtDecCua.setText("");
        
        bloquearPestanaReporte();

        txtCodTra.requestFocus();
    }
    

    /**
     * Muestra un mensaje informativo.
     */
    private void mostrarMsgInf(String strMsg) {
        JOptionPane.showMessageDialog(
                this,
                strMsg,
                "Mensaje del sistema Zafiro",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void limpiarDetalleLiquidacion()
    {
        if (objTblModLiq != null) {
            objTblModLiq.setRowCount(0);
        }
        
        arrCodRubDetLiq.clear();

        dblTotIng = 0.00;
        dblTotEgr = 0.00;

        txtTotIng.setText("0.00");
        txtTotEgr.setText("0.00");
        txtTotLiq.setText("0.00");
    }
    
    
    private boolean cargarRubrosUltimoRol()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try
        {
            SimpleDateFormat objForFec = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date datFecSal = objForFec.parse(txtFecFinCon.getText().trim());
            java.sql.Date datFecSalSQL =  new java.sql.Date(datFecSal.getTime());
            java.util.Calendar calFecSal = java.util.Calendar.getInstance();
            calFecSal.setTime(datFecSal);

            int intAni = calFecSal.get(java.util.Calendar.YEAR);
            int intMes = calFecSal.get(java.util.Calendar.MONTH) + 1;
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null)
            {
                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    a.co_rub, ";
                strSQL += "    b.tx_nom, ";
                strSQL += "    b.tx_tiprub, ";
                strSQL += "    COALESCE(a.nd_valrub,0) AS nd_valrub ";

                strSQL += "FROM tbm_ingEgrMenTra AS a ";

                strSQL += "INNER JOIN tbm_rubrolpag AS b ";
                strSQL += "    ON b.co_rub = a.co_rub ";

                strSQL += "WHERE a.co_emp = " + intCodEmp;
                strSQL += "  AND a.co_tra = " + intCodTra;
                strSQL += "  AND a.ne_ani = " + intAni;
                strSQL += "  AND a.ne_mes = " + intMes;
                strSQL += "  AND a.ne_per = 2 ";
                strSQL += "  AND COALESCE(a.nd_valrub,0) <> 0 ";

                /*
                 * Primero se muestran ingresos y luego egresos.
                 */
                strSQL += "ORDER BY ";
                strSQL += "    CASE ";
                strSQL += "        WHEN b.tx_tiprub = 'I' THEN 1 ";
                strSQL += "        WHEN b.tx_tiprub = 'E' THEN 2 ";
                strSQL += "        ELSE 3 ";
                strSQL += "    END, ";
                strSQL += "    a.co_rub";

                System.out.println(
                    "SQL rubros último rol: " + strSQL
                );

                rstLoc = stmLoc.executeQuery(strSQL);

                while (rstLoc.next())
                {
                    int intCodRub = rstLoc.getInt("co_rub");
                    String strNomRub = rstLoc.getString("tx_nom");
                    String strTipRub = rstLoc.getString("tx_tiprub");
                    double dblValRub = rstLoc.getDouble("nd_valrub");
                    Double dblIngreso = null;
                    Double dblDescuento = null;
                    /*
                     * El catálogo del rubro define si se trata
                     * de un ingreso o de un egreso.
                     */
                    if ("I".equalsIgnoreCase(strTipRub))
                    {
                        dblIngreso = objUti.redondear(Math.abs(dblValRub),2);
                        dblTotIng += dblIngreso;
                    }
                    else if ("E".equalsIgnoreCase(strTipRub))
                    {
                        /*
                         * Valor normal proveniente del rol.
                         */
                        dblDescuento = objUti.redondear(Math.abs(dblValRub),2);

                        /*
                         * Verificar si este rubro corresponde a un egreso
                         * programado con cuotas.
                         */
                        Double dblValEgrPrg =  obtenerValorEgresoProgramadoLiquidacion(con,intCodEmp,intCodTra,intCodRub,datFecSalSQL);

                            /*
                             * Si tiene cuotas programadas, únicamente para la
                             * liquidación se reemplaza el valor del rol por el
                             * saldo de cuotas desde el mes de salida en adelante.
                             *
                             * tbm_ingegrmentra NO se modifica.
                             */
                        if (dblValEgrPrg != null)
                        {
                            dblDescuento = objUti.redondear(dblValEgrPrg,2);
                        }

                        dblTotEgr += dblDescuento;
                    }
                    String strDetalle = obtenerNombreMes(intMes) + " " + intAni;
                    agregarFilaReporte(strNomRub,null,strDetalle,dblIngreso,dblDescuento,intCodRub);
                }

                dblTotIng = objUti.redondear(dblTotIng,2);
                dblTotEgr = objUti.redondear(dblTotEgr,2);
                blnRes = true;
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
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    
    /**
    * Obtiene el valor pendiente de un egreso programado para liquidación.
    *
    * Busca egresos programados activos del trabajador y rubro que tengan
    * una cuota en el mismo mes de la fecha de salida.
    *
    * Si encuentra uno, suma las cuotas activas desde el mes de salida
    * hacia adelante.
    *
    * Retorna null si el rubro no corresponde a un egreso programado
    * con cuotas para ese mes.
    */
   private Double obtenerValorEgresoProgramadoLiquidacion(
           java.sql.Connection con,
           int intCodEmp,
           int intCodTra,
           int intCodRub,
           java.sql.Date datFecSal)
           throws Exception
   {
       Double dblVal = null;

       java.sql.PreparedStatement pstLoc = null;
       java.sql.ResultSet rstLoc = null;

       try
       {
           String strSQL = "";

           strSQL += "SELECT ";
           strSQL += "    SUM(a.nd_valcuo) AS nd_valliq ";

           strSQL += "FROM tbm_detingegrprgtra AS a ";

           strSQL += "INNER JOIN tbm_cabingegrprgtra AS b ";
           strSQL += "    ON b.co_emp = a.co_emp ";
           strSQL += "   AND b.co_egr = a.co_egr ";

           strSQL += "WHERE b.co_emp = ? ";
           strSQL += "  AND b.co_tra = ? ";
           strSQL += "  AND b.co_rub = ? ";
           strSQL += "  AND b.st_reg = 'A' ";
           strSQL += "  AND a.st_reg = 'A' ";

           /*
            * El egreso programado debe tener una cuota
            * correspondiente al mes de salida.
            */
           strSQL += "  AND EXISTS ( ";
           strSQL += "      SELECT 1 ";
           strSQL += "      FROM tbm_detingegrprgtra AS x ";
           strSQL += "      WHERE x.co_emp = b.co_emp ";
           strSQL += "        AND x.co_egr = b.co_egr ";
           strSQL += "        AND x.st_reg = 'A' ";
           strSQL += "        AND DATE_TRUNC('month',x.fe_cuo) ";
           strSQL += "            = DATE_TRUNC('month',CAST(? AS DATE)) ";
           strSQL += "  ) ";

           /*
            * Sumar la cuota del mes de salida
            * y todas las cuotas posteriores.
            */
           strSQL += "  AND a.fe_cuo >= ";
           strSQL += "      DATE_TRUNC('month',CAST(? AS DATE)) ";

           pstLoc = con.prepareStatement(strSQL);

           int intPar = 1;

           pstLoc.setInt(intPar++,intCodEmp);
           pstLoc.setInt(intPar++,intCodTra);
           pstLoc.setInt(intPar++,intCodRub);
           pstLoc.setDate(intPar++,datFecSal);
           pstLoc.setDate(intPar++,datFecSal);

           rstLoc = pstLoc.executeQuery();

           if (rstLoc.next())
           {
               if (rstLoc.getObject("nd_valliq") != null)
               {
                   dblVal = rstLoc.getDouble("nd_valliq");
               }
           }
       }
       finally
       {
           try
           {
               if (rstLoc != null)
                   rstLoc.close();
           }
           catch (Throwable ignore)
           {
           }

           try
           {
               if (pstLoc != null)
                   pstLoc.close();
           }
           catch (Throwable ignore)
           {
           }
       }

       return dblVal;
   }

    private String obtenerNombreMes(int intMes)
    {
        String[] arrMeses = {
            "",
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        };

        if (intMes >= 1 && intMes <= 12) {
            return arrMeses[intMes];
        }

        return "";
    }
    
    
    private boolean cargarDecimoTerceroAcumulado()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try
        {
            SimpleDateFormat objForFec = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date datFecSal = objForFec.parse(txtFecFinCon.getText().trim());
            java.util.Calendar calFecSal = java.util.Calendar.getInstance();
            calFecSal.setTime(datFecSal);
            int intAni = calFecSal.get(java.util.Calendar.YEAR);
            int intMes = calFecSal.get(java.util.Calendar.MONTH) + 1;            
            int intAniDes;

            if (intMes == 12)
            {
                intAniDes = intAni;
            }
            else
            {
                intAniDes = intAni - 1;
            }

            int intPerDes = intAniDes * 100 + 12;
            int intPerHas = intAni * 100 + intMes;
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null)
            {
                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += "WITH contrato AS ( ";

                strSQL += "    SELECT ";
                strSQL += "        COALESCE(tx_forpagdectersue,'') ";
                strSQL += "            AS forma_pago ";

                strSQL += "    FROM tbm_traemp ";

                strSQL += "    WHERE co_emp = " + intCodEmp;
                strSQL += "      AND co_tra = " + intCodTra;
                strSQL += "      AND st_reg = 'I' ";
                strSQL += "      AND fe_fincon IS NOT NULL ";

                strSQL += "    ORDER BY ";
                strSQL += "        fe_fincon DESC, ";
                strSQL += "        fe_inicon DESC ";

                strSQL += "    LIMIT 1 ";

                strSQL += ") ";

                strSQL += "SELECT ";

                strSQL += "    COALESCE( ";
                strSQL += "        (SELECT forma_pago FROM contrato), ";
                strSQL += "        '' ";
                strSQL += "    ) AS forma_pago, ";

                strSQL += "    COALESCE( ";
                strSQL += "        SUM(a.nd_valrub), ";
                strSQL += "        0 ";
                strSQL += "    ) AS base_decimo ";

                strSQL += "FROM tbm_ingEgrMenTra AS a ";

                strSQL += "WHERE a.co_emp = " + intCodEmp;
                strSQL += "  AND a.co_tra = " + intCodTra;
                strSQL += "  AND a.ne_per = 2 ";
                strSQL += "  AND a.co_rub IN (1,2,3,22,33) ";

                strSQL += "  AND (a.ne_ani * 100 + a.ne_mes) ";
                strSQL += "      BETWEEN " + intPerDes;
                strSQL += "          AND " + intPerHas;

                System.out.println("SQL décimo tercero: " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next())
                {
                    String strForPagDecTer = rstLoc.getString("forma_pago");

                    if (strForPagDecTer == null)
                    {
                        strForPagDecTer = "";
                    }

                    /*
                     * Cuando la forma es M, el décimo ya se paga
                     * mensualmente mediante el rubro 31.
                     *
                     * Por tanto, no debe agregarse otra vez.
                     */
                    boolean blnDecTerMensual ="M".equalsIgnoreCase(strForPagDecTer.trim());
                    if (!blnDecTerMensual)
                    {
                        double dblBaseDecTer = objUti.redondear(rstLoc.getDouble("base_decimo"),2);
                        double dblDecTer = objUti.redondear(dblBaseDecTer /12.0, 2);

                        if (dblDecTer > 0)
                        {
                            agregarFilaReporte(
                                "Décimo tercero acumulado",
                                dblBaseDecTer,
                                "01/12/"
                                    + intAniDes
                                    + " al "
                                    + txtFecFinCon.getText().trim(),
                                dblDecTer,
                                null
                            );

                            dblTotIng += dblDecTer;
                            dblTotIng = objUti.redondear(dblTotIng,2);
                        }
                    }
                }

                blnRes = true;
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
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    
    private boolean cargarDecimoCuartoAcumulado()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try
        {
            DateTimeFormatter objForFec =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecIniCon = LocalDate.parse(txtFecIniCon.getText().trim(),objForFec);
            LocalDate datFecFinCon = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);
            /*
             * Período Costa:
             * 1 de marzo hasta el último día de febrero.
             *
             * Si la salida es de marzo a diciembre,
             * el período comenzó en marzo del mismo año.
             *
             * Si la salida es enero o febrero,
             * el período comenzó en marzo del año anterior.
             */
            LocalDate datFecIniPer;

            if (datFecFinCon.getMonthValue() >= 3)
            {
                datFecIniPer = LocalDate.of(datFecFinCon.getYear(),3,1);
            }
            else
            {
                datFecIniPer = LocalDate.of(datFecFinCon.getYear() - 1,3,1);
            }

            /*
             * Si el trabajador ingresó después del inicio
             * del período, se toma la fecha de ingreso.
             */
            LocalDate datFecIniCal;

            if (datFecIniCon.isAfter(datFecIniPer))
            {
                datFecIniCal = datFecIniCon;
            }
            else
            {
                datFecIniCal = datFecIniPer;
            }

            if (datFecIniCal.isAfter(datFecFinCon))
            {
                return true;
            }

            LocalDate datFecFinCal = datFecFinCon;

            /*
             * Si el trabajador fue incluido en el rol de fin de mes,
             * se considera completo el mes de salida para el cálculo
             * del décimo cuarto.
             */
            if (blnFuePagadoRolFinMes)
            {
                datFecFinCal = datFecFinCon.withDayOfMonth(datFecFinCon.lengthOfMonth());
            }
            
            int intNumDias = calcularDiasBase360(datFecIniCal,datFecFinCal);

            int intCodEmp = obtenerCodigoEmpresa();

            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null)
            {
                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    COALESCE(tx_forpagdeccuasue,'') ";
                strSQL += "        AS forma_pago ";

                strSQL += "FROM tbm_traemp ";

                strSQL += "WHERE co_emp = " + intCodEmp;
                strSQL += "  AND co_tra = " + intCodTra;
                strSQL += "  AND st_reg = 'I' ";
                strSQL += "  AND fe_fincon IS NOT NULL ";

                strSQL += "ORDER BY ";
                strSQL += "    fe_fincon DESC, ";
                strSQL += "    fe_inicon DESC ";

                strSQL += "LIMIT 1";

                System.out.println("SQL forma pago décimo cuarto: "+ strSQL);

                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next())
                {
                    String strForPagDecCua = rstLoc.getString("forma_pago");

                    if (strForPagDecCua == null)
                    {
                        strForPagDecCua = "";
                    }

                    /*
                     * Cuando la forma es M, el décimo cuarto
                     * se paga mensualmente mediante el rubro 32.
                     *
                     * Por eso no se agrega nuevamente
                     * a la liquidación.
                     */
                    boolean blnDecCuaMensual = "M".equalsIgnoreCase(strForPagDecCua.trim());

                    if (!blnDecCuaMensual)
                    {
                        ZafSueBas objSueBas = new ZafSueBas();

                        double dblSbu = objSueBas.getDouNd_SueBas();

                        double dblDecCua = objUti.redondear((dblSbu / 360.0)* intNumDias,2);

                        if (dblDecCua > 0)
                        {
                            agregarFilaReporte("Décimo cuarto acumulado",objUti.redondear(dblSbu,2),datFecIniCal.format(objForFec)+ " al " + datFecFinCon.format(objForFec),dblDecCua,null);
                            dblTotIng += dblDecCua;
                            dblTotIng = objUti.redondear(dblTotIng,2);
                        }
                    }

                    blnRes = true;
                }
                else
                {
                    mostrarMsgInf("No se encontró la configuración "+ "del décimo cuarto del trabajador.");
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
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    
    private boolean cargarVacacionesPendientes()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try
        {
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());
            DateTimeFormatter objForFec = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecSal = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null)
            {
                stmLoc = con.createStatement();
                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    d.ne_perdes, ";
                strSQL += "    d.ne_perhas, ";

                /*
                 * Primera fecha registrada del período vacacional.
                 * Se utilizará para determinar si el período
                 * fue completado antes de la salida.
                 */
                strSQL += "    MIN(d.fe_ini) AS fe_ini_periodo, ";              
                

                /*
                 * Base real de vacaciones obtenida directamente
                 * de la suma de las provisiones mensuales.
                 */
                strSQL += "    ROUND( ";
                strSQL += "        SUM(COALESCE(d.nd_provac,0)), ";
                strSQL += "        2 ";
                strSQL += "    ) AS base_vacaciones, ";

                strSQL += "    c.ne_diaper, ";
                strSQL += "    c.ne_diagoz, ";
                strSQL += "    c.ne_diapag ";

                strSQL += "FROM tbm_detpervactra AS d ";

                strSQL += "LEFT JOIN tbm_cfgvactra AS c ";
                strSQL += "    ON c.co_emp = d.co_emp ";
                strSQL += "   AND c.co_tra = d.co_tra ";
                strSQL += "   AND c.ne_anio = d.ne_perhas ";
                strSQL += "   AND c.st_reg = 'A' ";

                strSQL += "WHERE d.co_emp = " + intCodEmp;
                strSQL += "  AND d.co_tra = " + intCodTra;
                strSQL += "  AND d.st_reg = 'A' ";

                strSQL += "GROUP BY ";
                strSQL += "    d.ne_perdes, ";
                strSQL += "    d.ne_perhas, ";
                strSQL += "    c.ne_diaper, ";
                strSQL += "    c.ne_diagoz, ";
                strSQL += "    c.ne_diapag ";

                strSQL += "HAVING ";
                strSQL += "    SUM(COALESCE(d.nd_provac,0)) > 0 ";

                strSQL += "ORDER BY ";
                strSQL += "    d.ne_perdes, ";
                strSQL += "    d.ne_perhas";

                System.out.println(
                    "SQL vacaciones pendientes: "
                    + strSQL
                );

                rstLoc = stmLoc.executeQuery(strSQL);

                while (rstLoc.next())
                {
                    int intPerDes = rstLoc.getInt("ne_perdes");
                    int intPerHas = rstLoc.getInt("ne_perhas");
                    double dblBaseVac = objUti.redondear(rstLoc.getDouble("base_vacaciones"),2);
                    java.sql.Date datFecIniPerSQL = rstLoc.getDate("fe_ini_periodo");
                    if (datFecIniPerSQL == null)
                    {
                        System.out.println("No se encontró fecha inicial para "+ "el período vacacional "+ intPerDes+ "-"+ intPerHas);
                        continue;
                    }
                    LocalDate datFecIniPer =  datFecIniPerSQL.toLocalDate();
                    
                    if (datFecIniPer.isAfter(datFecSal))
                    {
                        continue;
                    }

                    /*
                     * El período termina un día antes
                     * del siguiente aniversario.
                     *
                     * Ejemplo:
                     * Inicio: 25/08/2025
                     * Fin:    24/08/2026
                     */
                    LocalDate datFecFinPer = datFecIniPer.plusYears(1).minusDays(1);
                    boolean blnPeriodoCumplido =!datFecSal.isBefore(datFecFinPer);
                    boolean blnSaleAlCompletarPeriodo = datFecSal.isEqual(datFecFinPer);
                    Double dblValVac = null;
                    String strDetalle = "";
                    
                    /*
                    * Si el empleado sale exactamente el último día
                    * del período anual, se considera la remuneración
                    * completa del mes final para las vacaciones.
                    */
                   if (blnSaleAlCompletarPeriodo)
                   {
                       String strSQLAjuste = "";

                       strSQLAjuste += "SELECT ";
                       strSQLAjuste += "    COALESCE(SUM(x.nd_provac),0) AS ajuste ";
                       strSQLAjuste += "FROM tbm_detpervactra AS x ";
                       strSQLAjuste += "WHERE x.co_emp = " + intCodEmp;
                       strSQLAjuste += "  AND x.co_tra = " + intCodTra;
                       strSQLAjuste += "  AND x.st_reg = 'A' ";
                       strSQLAjuste += "  AND x.ne_perdes = " + intPerHas;
                       strSQLAjuste += "  AND x.ne_perhas = " + (intPerHas + 1);
                       strSQLAjuste += "  AND x.fe_ini > '" + datFecSal + "' ";
                       strSQLAjuste += "  AND x.fe_ini < '"
                               + datFecSal.withDayOfMonth(1).plusMonths(1) + "' ";

                       try (java.sql.Statement stmAjuste = con.createStatement();
                            java.sql.ResultSet rstAjuste = stmAjuste.executeQuery(strSQLAjuste))
                       {
                           if (rstAjuste.next())
                           {
                               double dblAjuste = rstAjuste.getDouble("ajuste");

                               dblBaseVac = objUti.redondear(
                                   dblBaseVac + dblAjuste,
                                   2
                               );

                               System.out.println(
                                   "Ajuste vacaciones empleado "
                                   + intCodTra
                                   + " período " + intPerDes + "-" + intPerHas
                                   + " ajuste=" + dblAjuste
                                   + " nueva base=" + dblBaseVac
                               );
                           }
                       }
                   }
                    

                    if (!blnPeriodoCumplido)
                    {
                        /*
                         * El trabajador salió antes de completar
                         * el período vacacional.
                         *
                         * No importa que exista ne_diaper.
                         * Se paga únicamente la provisión generada
                         * durante los meses trabajados.
                         */
                        dblValVac = objUti.redondear(dblBaseVac / 24.0,2);
                        strDetalle = "Período proporcional";
                        System.out.println(
                            "Vacaciones proporcionales "
                            + intPerDes
                            + "-"
                            + intPerHas
                            + ": inicio="
                            + datFecIniPer
                            + ", fin período="
                            + datFecFinPer
                            + ", salida="
                            + datFecSal
                            + ", base="
                            + dblBaseVac
                            + ", valor="
                            + dblValVac
                        );
                    }
                    else
                    {
                        /*
                         * El período sí fue completado.
                         * En este caso se utilizan los días
                         * configurados, gozados y pagados.
                         */
                        Object objDiasPeriodo = rstLoc.getObject("ne_diaper");
                        if (objDiasPeriodo == null)
                        {
                            System.out.println(
                                "No existe configuración de vacaciones "
                                + "para el período completado "
                                + intPerDes
                                + "-"
                                + intPerHas
                            );

                            continue;
                        }
                        int intDiasPeriodo = rstLoc.getInt("ne_diaper");
                        int intDiasGozados = rstLoc.getInt("ne_diagoz");
                        int intDiasPagados = rstLoc.getInt("ne_diapag");
                        int intDiasPendientes = intDiasPeriodo - intDiasGozados - intDiasPagados;
                        if (intDiasPendientes < 0)
                        {
                            intDiasPendientes = 0;
                        }

                        /*
                         * Si todos los días ya fueron gozados
                         * o pagados, no se agregan nuevamente.
                         */
                        if (intDiasPendientes == 0)
                        {
                            continue;
                        }
                        dblValVac =  objUti.redondear((dblBaseVac / 360.0) * intDiasPendientes,2);
                        strDetalle = intDiasPendientes + " de " + intDiasPeriodo + " días pendientes";

                        System.out.println(
                            "Vacaciones período completado "
                            + intPerDes
                            + "-"
                            + intPerHas
                            + ": días período="
                            + intDiasPeriodo
                            + ", gozados="
                            + intDiasGozados
                            + ", pagados="
                            + intDiasPagados
                            + ", pendientes="
                            + intDiasPendientes
                            + ", valor="
                            + dblValVac
                        );
                    }

                    if (dblValVac != null
                            && dblValVac > 0)
                    {
                        agregarFilaReporte("Vacaciones "+ intPerDes+ "-"+ intPerHas,dblBaseVac,strDetalle,dblValVac,null);
                        dblTotIng += dblValVac;
                        dblTotIng = objUti.redondear(dblTotIng,2);
                    }
                }

                blnRes = true;
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
            try {
                rstLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                con.close();
            }
            catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    private boolean cargarBonificacionDesahucio()
    {
        boolean blnRes = false;

        try
        {
            /*
             * Actualmente las dos causales configuradas
             * generan la bonificación por desahucio:
             *
             * R = Renuncia mediante desahucio.
             * D = Despido intempestivo.
             */
            String strCausal = getCausalSeleccionada();

            if (!"R".equals(strCausal)
                    && !"D".equals(strCausal))
            {
                return true;
            }

            DateTimeFormatter objForFec = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecIniCon = LocalDate.parse(txtFecIniCon.getText().trim(),objForFec);
            LocalDate datFecFinCon = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);
            if (datFecFinCon.isBefore(datFecIniCon))
            {
                mostrarMsgInf("La fecha de salida no puede ser "+ "menor que la fecha de ingreso.");
                return false;
            }

            /*
             * Period.getYears() devuelve únicamente
             * los años completos trabajados.
             *
             * Ejemplo:
             * 19/05/2025 al 21/07/2026 = 1 año completo.
             */
            //int intAniCom = Period.between(datFecIniCon,datFecFinCon).getYears();
            int intAniCom = Period.between(datFecIniCon,datFecFinCon.plusDays(1)).getYears();
            /*
             * Si todavía no tiene un año completo,
             * no se genera esta bonificación.
             */
            if (intAniCom <= 0)
            {
                return true;
            }
            
            double dblBaseDes =  obtenerBaseRemuneracionLiquidacion(true);
            double dblPorDes = 25.00;
            double dblValDes =(dblBaseDes * dblPorDes / 100.0)* intAniCom;
            dblValDes = objUti.redondear(dblValDes,objZafParSis.getDecimalesMostrar());
            String strDetAni = intAniCom == 1 ? "1 año completo" : intAniCom + " años completos";
            agregarFilaReporte(
                "Bonificación por desahucio",
                objUti.redondear(
                    dblBaseDes,
                    objZafParSis.getDecimalesMostrar()
                ),
                "25% $"
                    + objUti.redondear(
                        dblBaseDes,
                        objZafParSis.getDecimalesMostrar()
                    )
                    + " x "
                    + strDetAni,
                dblValDes,
                null
            );

            dblTotIng += dblValDes;
            dblTotIng = objUti.redondear(dblTotIng,objZafParSis.getDecimalesMostrar());
            System.out.println(
                "Empleado "
                + txtCodTra.getText()
                + " → base desahucio="
                + dblBaseDes
                + ", años completos="
                + intAniCom
                + ", valor desahucio="
                + dblValDes
            );

            blnRes = true;
        }
        catch (NumberFormatException e)
        {
            blnRes = false;
            mostrarMsgInf("El sueldo mensual del trabajador " + "no contiene un valor válido.");
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean cargarIndemnizacionDespido()
    {
        boolean blnRes = false;

        try
        {
            /*
             * La indemnización solamente corresponde
             * cuando la causal seleccionada es:
             *
             * D = Despido intempestivo.
             */
            if (!"D".equals(getCausalSeleccionada()))
            {
                return true;
            }

            DateTimeFormatter objForFec =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecIniCon = LocalDate.parse(txtFecIniCon.getText().trim(),objForFec);
            LocalDate datFecFinCon = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);
            
            if (datFecFinCon.isBefore(datFecIniCon))
            {
                mostrarMsgInf("La fecha de salida no puede ser "+ "menor que la fecha de ingreso.");
                return false;
            }

            /*
             * Obtener años, meses y días de servicio.
             */
            Period objPerServicio = Period.between(datFecIniCon,datFecFinCon);
            int intAniCompletos = objPerServicio.getYears();
            boolean blnTieneFraccion = objPerServicio.getMonths() > 0 || objPerServicio.getDays() > 0;
            /*
             * Para trabajadores con más de tres años,
             * cualquier fracción cuenta como año completo.
             */
            int intAniCalculo = intAniCompletos + (blnTieneFraccion ? 1 : 0);
            /*
             * Hasta tres años corresponden
             * tres remuneraciones.
             *
             * Desde más de tres años:
             * una remuneración por cada año,
             * máximo veinticinco.
             */
            int intNumRemuneraciones;

            if (intAniCalculo <= 3)
            {
                intNumRemuneraciones = 3;
            }
            else
            {
                intNumRemuneraciones = Math.min(intAniCalculo,25);
            }

            //double dblBaseInd = dblBaseRemLiq;
            double dblBaseInd = obtenerBaseRemuneracionLiquidacion(true);
            double dblValInd =  dblBaseInd * intNumRemuneraciones;
            dblValInd = objUti.redondear(dblValInd,objZafParSis.getDecimalesMostrar());
            String strDetalle;

            if (intNumRemuneraciones == 1)
            {
                strDetalle = "1 remuneración";
            }
            else
            {
                strDetalle = intNumRemuneraciones + " remuneraciones";
            }

            agregarFilaReporte(
                "Indemnización por despido intempestivo",
                objUti.redondear(
                    dblBaseInd,
                    objZafParSis.getDecimalesMostrar()
                ),
                strDetalle,
                dblValInd,
                null
            );

            dblTotIng += dblValInd;
            dblTotIng =  objUti.redondear(dblTotIng,objZafParSis.getDecimalesMostrar());

            System.out.println(
                "Empleado "
                + txtCodTra.getText()
                + " → años completos="
                + intAniCompletos
                + ", tiene fracción="
                + blnTieneFraccion
                + ", años cálculo="
                + intAniCalculo
                + ", remuneraciones="
                + intNumRemuneraciones
                + ", base indemnización="
                + dblBaseInd
                + ", indemnización="
                + dblValInd
            );

            blnRes = true;
        }
        catch (NumberFormatException e)
        {
            blnRes = false;

            mostrarMsgInf("El sueldo mensual del trabajador " + "no contiene un valor válido.");
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean consultarPagoRolFinMes()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strSQL = "";

        try
        {
            blnFuePagadoRolFinMes = false;
            DateTimeFormatter objForFec =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecSal = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);
            int intAni = datFecSal.getYear();
            int intMes = datFecSal.getMonthValue();
            int intCodEmp =  objZafParSis.getCodigoEmpresa();
            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con != null)
            {
                stmLoc = con.createStatement();

                strSQL = "";
                strSQL += "SELECT EXISTS (";
                strSQL += "    SELECT 1";
                strSQL += "    FROM tbm_feccorrolpag AS a";
                strSQL += "    INNER JOIN tbm_detrolpag AS b";
                strSQL += "        ON b.co_emp = a.co_emp";
                strSQL += "       AND b.co_loc = a.co_locrelrolpag";
                strSQL += "       AND b.co_tipdoc = a.co_tipdocrelrolpag";
                strSQL += "       AND b.co_doc = a.co_docrelrolpag";
                strSQL += "    WHERE a.co_emp = " + intCodEmp;
                strSQL += "      AND a.ne_ani = " + intAni;
                strSQL += "      AND a.ne_mes = " + intMes;
                strSQL += "      AND a.ne_per = 2";
                strSQL += "      AND a.co_locrelrolpag IS NOT NULL";
                strSQL += "      AND a.co_tipdocrelrolpag IS NOT NULL";
                strSQL += "      AND a.co_docrelrolpag IS NOT NULL";
                strSQL += "      AND b.co_tra = " + intCodTra;
                strSQL += ") AS bln_pagado";

                System.out.println("Consulta empleado pagado en rol: "+ strSQL);

                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next())
                {
                    blnFuePagadoRolFinMes = rstLoc.getBoolean("bln_pagado");
                }

                System.out.println(
                    "Empleado "
                    + intCodTra
                    + " fue pagado en el rol de fin de mes: "
                    + blnFuePagadoRolFinMes
                );

                blnRes = true;
            }
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try {
                rstLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                con.close();
            }
            catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
     /**
     * Calcula los días utilizando meses de 30 días
     * y años de 360 días.
     *
     * Las fechas inicial y final se consideran incluidas.
     */
    private int calcularDiasBase360(
            LocalDate datFecDes,
            LocalDate datFecHas)
    {
        if (datFecDes == null || datFecHas == null)
        {
            return 0;
        }

        if (datFecHas.isBefore(datFecDes))
        {
            return 0;
        }

        int intDiaDes = obtenerDiaBase30(datFecDes);
        int intDiaHas = obtenerDiaBase30(datFecHas);

        int intDias =
                ((datFecHas.getYear()
                - datFecDes.getYear()) * 360)
                +
                ((datFecHas.getMonthValue()
                - datFecDes.getMonthValue()) * 30)
                +
                (intDiaHas - intDiaDes)
                + 1;

        return intDias;
    }


    /**
     * Convierte el último día de cualquier mes
     * en el día 30 para el cálculo laboral.
     */
    private int obtenerDiaBase30(LocalDate datFec)
    {
        int intDia = datFec.getDayOfMonth();

        /*
         * El último día del mes se considera día 30.
         * Esto también aplica para febrero.
         */
        if (intDia == datFec.lengthOfMonth())
        {
            return 30;
        }

        return Math.min(intDia, 30);
    }
    
    private double obtenerBaseRemuneracionLiquidacion(boolean blnUltimoMesCompleto)throws Exception
    {
        double dblBaseRem = 0.00;
        double dblSueMen = 0.00;
        double dblIngVar = 0.00;

        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            int intCodEmp =  obtenerCodigoEmpresa();
            int intCodTra =  Integer.parseInt(txtCodTra.getText().trim());
            DateTimeFormatter objForFec = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate datFecSal = LocalDate.parse(txtFecFinCon.getText().trim(),objForFec);
            
            int intAni;
            int intMes;

            /*
             * Determinar el período que se utilizará.
             */
            if (blnUltimoMesCompleto)
            {
                /*
                 * Para el desahucio se utiliza el último
                 * mes calendario que el trabajador completó.
                 *
                 * Si sale antes de finalizar el mes:
                 * 29/07/2026 -> junio 2026.
                 *
                 * Si sale exactamente el último día del mes:
                 * se puede utilizar el mismo mes.
                 */
                LocalDate datMesBase;

                if (datFecSal.getDayOfMonth()  == datFecSal.lengthOfMonth())
                {
                    datMesBase = datFecSal;
                }
                else
                {
                    datMesBase = datFecSal.minusMonths(1);
                }

                intAni = datMesBase.getYear();
                intMes = datMesBase.getMonthValue();
            }
            else
            {
                /*
                 * Cálculo normal de la liquidación:
                 * utilizar el mes de salida.
                 */
                intAni = datFecSal.getYear();
                intMes = datFecSal.getMonthValue();

                /*
                 * Se toma el sueldo mensual actual completo,
                 * no el sueldo proporcional del último rol.
                 */
                dblSueMen = Double.parseDouble(txtSueMen.getText().trim().replace(",", "."));
            }

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con == null)
            {
                throw new Exception("No fue posible conectarse a la base de datos.");
            }

            stmLoc = con.createStatement();
            String strSQL = "";
            if (blnUltimoMesCompleto)
            {
                /*
                 * Último mes completo.
                 *
                 * Aquí sí se obtiene toda la remuneración
                 * desde el rol del mes completo:
                 *
                 * 1  = Sueldo
                 * 2  = Horas extras
                 * 3  = Horas extras 2
                 * 22 = Otros ingresos variables
                 * 33 = Comisiones
                 */
                strSQL += "SELECT ";
                strSQL += "    COALESCE(";
                strSQL += "        SUM(COALESCE(nd_valrub,0)),";
                strSQL += "        0";
                strSQL += "    ) AS base_remuneracion ";

                strSQL += "FROM tbm_ingEgrMenTra ";

                strSQL += "WHERE co_emp = " + intCodEmp;
                strSQL += "  AND co_tra = " + intCodTra;
                strSQL += "  AND ne_ani = " + intAni;
                strSQL += "  AND ne_mes = " + intMes;
                strSQL += "  AND ne_per = 2 ";
                strSQL += "  AND co_rub IN (1,2,3,22,33)";

                System.out.println("SQL base último mes completo: "+ strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if (rstLoc.next())
                {
                    dblBaseRem = rstLoc.getDouble("base_remuneracion");
                }

                System.out.println(
                    "Base último mes completo: año="
                    + intAni
                    + ", mes="
                    + intMes
                    + ", total="
                    + dblBaseRem
                );
            }
            else
            {
                /*
                 * Comportamiento que ya tenía el programa:
                 *
                 * Sueldo mensual actual
                 * +
                 * Variables del mes de salida.
                 */
                strSQL += "SELECT ";
                strSQL += "    COALESCE(";
                strSQL += "        SUM(COALESCE(nd_valrub,0)),";
                strSQL += "        0";
                strSQL += "    ) AS ingresos_variables ";

                strSQL += "FROM tbm_ingEgrMenTra ";

                strSQL += "WHERE co_emp = " + intCodEmp;
                strSQL += "  AND co_tra = " + intCodTra;
                strSQL += "  AND ne_ani = " + intAni;
                strSQL += "  AND ne_mes = " + intMes;
                strSQL += "  AND ne_per = 2 ";

                /*
                 * Rubros remunerativos variables:
                 *
                 * 2  = Horas extras
                 * 3  = Horas extras 2
                 * 22 = Otros ingresos variables
                 * 33 = Comisiones
                 */
                strSQL += "  AND co_rub IN (2,3,22,33)";
                System.out.println("SQL base remuneración liquidación: "+ strSQL);

                rstLoc = stmLoc.executeQuery(strSQL);

                if (rstLoc.next())
                {
                    dblIngVar = rstLoc.getDouble("ingresos_variables");
                }
                dblBaseRem = dblSueMen + dblIngVar;
                System.out.println(
                    "Base remuneración liquidación: sueldo="
                    + dblSueMen
                    + ", variables="
                    + dblIngVar
                    + ", total="
                    + dblBaseRem
                );
            }

            dblBaseRem = objUti.redondear(dblBaseRem,objZafParSis.getDecimalesMostrar());
        }
        finally
        {
            try
            {
                if (rstLoc != null)
                {
                    rstLoc.close();
                }
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (stmLoc != null)
                {
                    stmLoc.close();
                }
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }

        return dblBaseRem;
    }

    
    private boolean calcularLiquidacion()
    {
        boolean blnRes = false;

        try
        {
            limpiarDetalleLiquidacion();
            dblBaseRemLiq =  obtenerBaseRemuneracionLiquidacion(false);

            if (!consultarPagoRolFinMes())
            {
                return false;
            }

            /*
             * Si el exempleado no aparece en el documento del rol,
             * se cargan los rubros preparados por el Servicio 9.
             */
            if (!blnFuePagadoRolFinMes)
            {
                if (!cargarRubrosUltimoRol())
                {
                    return false;
                }
            }
            else
            {
                System.out.println(
                    "No se cargan los rubros del último rol porque "
                    + "el empleado ya fue pagado en el rol de fin de mes."
                );
            }

            /*
            * Cargar egresos programados independientemente
            * de que existan o no en tbm_ingegrmentra.
            */
           if (!cargarEgresosProgramadosLiquidacion())
           {
               return false;
           }
            
            
            if (!cargarDecimoTerceroAcumulado())
            {
                return false;
            }

            if (!cargarDecimoCuartoAcumulado())
            {
                return false;
            }

            if (!cargarVacacionesPendientes())
            {
                return false;
            }

            if (!cargarBonificacionDesahucio())
            {
                return false;
            }

            if (!cargarIndemnizacionDespido())
            {
                return false;
            }

            establecerTotales(dblTotIng,dblTotEgr);
            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private boolean guardarLiquidacion()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;

        try
        {
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodLoc = objZafParSis.getCodigoLocal();
            int intCodTipDoc = INT_COD_TIP_DOC_LIQ_HAB;
            int intCodTra = Integer.parseInt(txtCodTra.getText().trim());
            java.sql.Date datFecIniCon = convertirFechaSql(txtFecIniCon.getText());
            java.sql.Date datFecFinCon = convertirFechaSql(txtFecFinCon.getText());

            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con == null)
            {
                return false;
            }

            con.setAutoCommit(false);

            /*
             * Evita que dos usuarios calculen el mismo co_doc
             * simultáneamente mediante MAX()+1.
             *
             * El bloqueo dura solamente hasta COMMIT o ROLLBACK.
             */
            java.sql.Statement stmBloq = null;

            try
            {
                stmBloq = con.createStatement();
                stmBloq.executeUpdate("LOCK TABLE tbm_cabliqhab " + "IN EXCLUSIVE MODE");
            }
            finally
            {
                try {
                    stmBloq.close();
                }
                catch (Throwable ignore) {
                }
            }

            /*
             * No permite generar dos liquidaciones activas
             * para el mismo contrato del trabajador.
             */
            if (existeLiquidacionActiva(con,intCodEmp,intCodTipDoc,intCodTra,datFecIniCon,datFecFinCon))
            {
                con.rollback();
                mostrarMsgInf("Ya existe una liquidación activa para "+ "este trabajador y contrato.");
                return false;
            }

            int[] arrNumeros = obtenerNumerosDocumento(con,intCodEmp,intCodLoc,intCodTipDoc);
            intCodDocGuardado = arrNumeros[0];
            intNumDocGuardado = arrNumeros[1];

            if (!insertarCabeceraLiquidacion(con,intCodEmp,intCodLoc,intCodTipDoc,intCodDocGuardado,intNumDocGuardado,intCodTra,datFecIniCon,datFecFinCon))
            {
                con.rollback();
                intCodDocGuardado = 0;
                intNumDocGuardado = 0;
                return false;
            }

            if (!insertarDetalleLiquidacion(con,intCodEmp,intCodLoc,intCodTipDoc,intCodDocGuardado))
            {
                con.rollback();
                intCodDocGuardado = 0;
                intNumDocGuardado = 0;
                return false;
            }
            
            if (!insertarAutorizadoresLiquidacion(con,intCodEmp,intCodLoc,intCodTipDoc,intCodDocGuardado))
            {
                con.rollback();
                intCodDocGuardado = 0;
                intNumDocGuardado = 0;
                return false;
            }

            con.commit();
            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;

            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            intCodDocGuardado = 0;
            intNumDocGuardado = 0;

            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.setAutoCommit(true);
                    con.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }

        return blnRes;
    }
    
    private boolean existeLiquidacionActiva(
            java.sql.Connection con,
            int intCodEmp,
            int intCodTipDoc,
            int intCodTra,
            java.sql.Date datFecIniCon,
            java.sql.Date datFecFinCon)
            throws Exception
    {
        boolean blnExiste = false;

        String strSQL =
                "SELECT EXISTS ("
                + "    SELECT 1 "
                + "    FROM tbm_cabliqhab "
                + "    WHERE co_emp = ? "
                + "      AND co_tipdoc = ? "
                + "      AND co_tra = ? "
                + "      AND fe_inicon = ? "
                + "      AND fe_fincon = ? "
                + "      AND st_reg = 'A'"
                + ") AS existe";

        java.sql.PreparedStatement pstLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            
            pstLoc = con.prepareStatement(strSQL);

            pstLoc.setInt(1, intCodEmp);
            pstLoc.setInt(2, intCodTipDoc);
            pstLoc.setInt(3, intCodTra);
            pstLoc.setDate(4, datFecIniCon);
            pstLoc.setDate(5, datFecFinCon);

            rstLoc = pstLoc.executeQuery();

            if (rstLoc.next())
            {
                blnExiste = rstLoc.getBoolean("existe");
            }
        }
        finally
        {
            try {
                rstLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                pstLoc.close();
            }
            catch (Throwable ignore) {
            }
        }

        return blnExiste;
    }
    
    private int[] obtenerNumerosDocumento(
                    java.sql.Connection con,
                    int intCodEmp,
                    int intCodLoc,
                    int intCodTipDoc)
                    throws Exception
            {
                int intCodDoc = 0;
                int intNumDoc = 0;

                String strSQL =
                        "SELECT "
                        + "    COALESCE(MAX(co_doc),0)+1 AS co_doc, "
                        + "    COALESCE(MAX(ne_numdoc),0)+1 AS ne_numdoc "
                        + "FROM tbm_cabliqhab "
                        + "WHERE co_emp = ? "
                        + "  AND co_loc = ? "
                        + "  AND co_tipdoc = ?";

                java.sql.PreparedStatement pstLoc = null;
                java.sql.ResultSet rstLoc = null;

                try
                {
                    pstLoc = con.prepareStatement(strSQL);
                    pstLoc.setInt(1, intCodEmp);
                    pstLoc.setInt(2, intCodLoc);
                    pstLoc.setInt(3, intCodTipDoc);
                    rstLoc = pstLoc.executeQuery();

                    if (rstLoc.next())
                    {
                        intCodDoc = rstLoc.getInt("co_doc");
                        intNumDoc = rstLoc.getInt("ne_numdoc");
                    }
                }
                finally
                {
                    try {
                        rstLoc.close();
                    }
                    catch (Throwable ignore) {
                    }

                    try {
                        pstLoc.close();
                    }
                    catch (Throwable ignore) {
                    }
                }

                if (intCodDoc <= 0 || intNumDoc <= 0)
                {
                    throw new Exception(
                        "No fue posible generar la numeración "
                        + "de la liquidación."
                    );
                }

                return new int[]{
                    intCodDoc,
                    intNumDoc
                };
        }
    
    private boolean mostrarSiguienteNumeracion()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;

        try
        {
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodLoc = objZafParSis.getCodigoLocal();
            int intCodTipDoc = INT_COD_TIP_DOC_LIQ_HAB;
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (con == null)
            {
                return false;
            }

            int[] arrNumeros = obtenerNumerosDocumento(con,intCodEmp,intCodLoc,intCodTipDoc);

            /*
             * Posición 0 = co_doc
             * Posición 1 = ne_numdoc
             */
            txtCodDoc.setText(String.valueOf(arrNumeros[0]));
            txtNumDoc.setText(String.valueOf(arrNumeros[1]));
            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;

            txtCodDoc.setText("");
            txtNumDoc.setText("");
            objUti.mostrarMsgErr_F1(this,e);
        }
        finally
        {
            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }

        return blnRes;
    }
    

        private String obtenerTipoConcepto(String strConcepto,Integer intCodRub)
        {
            if (intCodRub != null)
            {
                return "ROL";
            }

            String strCon = strConcepto == null ? "" : strConcepto.trim().toLowerCase(Locale.ROOT);

            if (strCon.equals("décimo tercero acumulado"))
            {
                return "DEC13";
            }

            if (strCon.equals("décimo cuarto acumulado"))
            {
                return "DEC14";
            }

            if (strCon.startsWith("vacaciones "))
            {
                return "VAC";
            }

            if (strCon.equals("bonificación por desahucio"))
            {
                return "DESAH";
            }

            if (strCon.equals("indemnización por despido intempestivo"))
            {
                return "DESP";
            }

            throw new IllegalArgumentException(
                "No se pudo determinar el tipo del concepto: "
                + strConcepto
            );
        }

             
    private String obtenerTextoTabla(
            int intFil,
            int intCol)
    {
        Object objVal =
                objTblModLiq.getValueAt(
                    intFil,
                    intCol
                );

        return objVal == null
                ? ""
                : objVal.toString().trim();
    }        
      
    private BigDecimal obtenerBigDecimal(Object objVal)
    {
        BigDecimal bgVal = obtenerBigDecimalNullable(objVal);

        return bgVal == null ? BigDecimal.ZERO.setScale(2) : bgVal;
    }

    
    private BigDecimal obtenerBigDecimalNullable(Object objVal)
    {
        if (objVal == null)
        {
            return null;
        }

        if (objVal instanceof BigDecimal)
        {
            return ((BigDecimal) objVal).setScale(2,RoundingMode.HALF_UP);
        }

        if (objVal instanceof Number)
        {
            return new BigDecimal(objVal.toString()).setScale(2,RoundingMode.HALF_UP);
        }

        String strVal = objVal.toString().trim();
        if (strVal.equals(""))
        {
            return null;
        }

        /*
         * Admite:
         * 3020.80
         * 3020,80
         * 3.020,80
         * 3,020.80
         */
        if (strVal.contains(".") && strVal.contains(","))
        {
            if (strVal.lastIndexOf(",") > strVal.lastIndexOf("."))
            {
                strVal = strVal.replace(".", "").replace(",", ".");
            }
            else
            {
                strVal = strVal.replace(",", "");
            }
        }
        else
        {
            strVal = strVal.replace(",", ".");
        }

        return new BigDecimal(strVal).setScale(2,RoundingMode.HALF_UP);
    }
    
    private java.sql.Date convertirFechaSql(String strFecha)
    {
        DateTimeFormatter objForFec = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datFecha = LocalDate.parse(strFecha.trim(),objForFec);

        return java.sql.Date.valueOf(datFecha);
    }
        
    
    private boolean insertarCabeceraLiquidacion(
            java.sql.Connection con,
            int intCodEmp,
            int intCodLoc,
            int intCodTipDoc,
            int intCodDoc,
            int intNumDoc,
            int intCodTra,
            java.sql.Date datFecIniCon,
            java.sql.Date datFecFinCon)
            throws Exception
    {
        boolean blnRes = false;

        String strSQL =
                "INSERT INTO tbm_cabliqhab ("
                + "    co_emp, "
                + "    co_loc, "
                + "    co_tipdoc, "
                + "    co_doc, "
                + "    fe_doc, "
                + "    ne_numdoc, "
                + "    co_tra, "
                + "    fe_inicon, "
                + "    fe_fincon, "
                + "    tx_causal, "
                + "    co_cauliq, "
                + "    nd_suetra, "
               
                + "    ne_diatra, "
                + "    tx_fonres, "
                + "    tx_decter, "
                + "    tx_deccua, "

                + "    nd_basremliq, "
                + "    st_pagrolfinmes, "
                + "    nd_toting, "
                + "    nd_totegr, "
                + "    nd_totliq, "
                + "    st_aut, "
                + "    tx_obs1, "
                + "    st_reg, "
                + "    co_usring, "
                + "    fe_ing"
                + ") VALUES ("
                + "    ?, ?, ?, ?, "
                + "    CURRENT_DATE, "
                + "    ?, ?, ?, ?, ?, ?, "
                + "    ?, ?, ?, ?, ?, "
                + "    ?, ?, ?, ?, ?, "
                + "    'P', ?, 'A', ?, CURRENT_TIMESTAMP"
                + ")";

        java.sql.PreparedStatement pstLoc = null;

        try
        {
            /*
             * Verificar que los nuevos datos estén cargados
             * antes de guardar el documento.
             */
            if (txtDiasTra.getText().trim().equals("") || txtFonRes.getText().trim().equals("") ||
                txtDecTer.getText().trim().equals("")|| txtDecCua.getText().trim().equals(""))
            {
                throw new Exception(
                    "No se han cargado los días trabajados "
                    + "o la configuración de beneficios."
                );
            }

            BigDecimal bgSueTra = obtenerBigDecimal(txtSueMen.getText());
            int intDiasTra = Integer.parseInt(txtDiasTra.getText().trim());
            String strFonRes = txtFonRes.getText().trim();
            String strDecTer = txtDecTer.getText().trim();
            String strDecCua = txtDecCua.getText().trim();
            BigDecimal bgBasRemLiq = BigDecimal.valueOf(dblBaseRemLiq).setScale(2,RoundingMode.HALF_UP);
            BigDecimal bgTotIng = obtenerBigDecimal(txtTotIng.getText());
            BigDecimal bgTotEgr = obtenerBigDecimal(txtTotEgr.getText());
            BigDecimal bgTotLiq = bgTotIng.subtract(bgTotEgr).setScale(2,RoundingMode.HALF_UP);

            String strPagRolFinMes = blnFuePagadoRolFinMes ? "S" : "N"; 
            String strObs = txaObs.getText().trim();
            CausaLiquidacion objCau = (CausaLiquidacion) cmbCau.getSelectedItem();
            
            pstLoc = con.prepareStatement(strSQL);

            int intPar = 1;

            pstLoc.setInt(intPar++,intCodEmp);
            pstLoc.setInt(intPar++,intCodLoc);
            pstLoc.setInt(intPar++,intCodTipDoc);
            pstLoc.setInt(intPar++,intCodDoc);
            pstLoc.setInt(intPar++,intNumDoc);
            pstLoc.setInt(intPar++,intCodTra);
            pstLoc.setDate(intPar++,datFecIniCon);
            pstLoc.setDate(intPar++,datFecFinCon);
            pstLoc.setString(intPar++,objCau.getTipoCalculo()); // tx_causal
            pstLoc.setInt(intPar++,objCau.getCodigo());         // co_cauliq
            pstLoc.setBigDecimal(intPar++,bgSueTra);
            
            
            /*
             * Nuevos valores.
             */
            pstLoc.setInt(intPar++,intDiasTra);
            pstLoc.setString(intPar++,strFonRes);
            pstLoc.setString(intPar++,strDecTer);
            pstLoc.setString(intPar++,strDecCua);
            pstLoc.setBigDecimal(intPar++,bgBasRemLiq);
            pstLoc.setString(intPar++,strPagRolFinMes);
            pstLoc.setBigDecimal(intPar++,bgTotIng);
            pstLoc.setBigDecimal(intPar++,bgTotEgr);
            pstLoc.setBigDecimal(intPar++,bgTotLiq);

            if (strObs.equals(""))
            {
                pstLoc.setNull(intPar++,Types.VARCHAR);
            }
            else
            {
                pstLoc.setString(intPar++,strObs);
            }

            pstLoc.setInt(intPar++,objZafParSis.getCodigoUsuario());

            blnRes = pstLoc.executeUpdate() == 1;
        }
        finally
        {
            try
            {
                pstLoc.close();
            }
            catch (Throwable ignore)
            {
            }
        }

        return blnRes;
    }
    
    private boolean insertarDetalleLiquidacion(
            java.sql.Connection con,
            int intCodEmp,
            int intCodLoc,
            int intCodTipDoc,
            int intCodDoc)
            throws Exception
    {
        boolean blnRes = false;

        String strSQL =
                "INSERT INTO tbm_detliqhab ("
                + "    co_emp, "
                + "    co_loc, "
                + "    co_tipdoc, "
                + "    co_doc, "
                + "    co_reg, "
                + "    co_rub, "
                + "    tx_tipcon, "
                + "    tx_nomcon, "
                + "    nd_base, "
                + "    tx_det, "
                + "    nd_ing, "
                + "    nd_egr, "
                + "    st_reg, "
                + "    co_usring, "
                + "    fe_ing"
                + ") VALUES ("
                + "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "    'A', ?, CURRENT_TIMESTAMP"
                + ")";

        java.sql.PreparedStatement pstLoc = null;

        try
        {
            
            if (arrCodRubDetLiq.size() != objTblModLiq.getRowCount())
            {
                throw new Exception(
                    "La cantidad de códigos de rubro no coincide "
                    + "con las filas del detalle.\n"
                    + "Filas: "
                    + objTblModLiq.getRowCount()
                    + "\nCódigos: "
                    + arrCodRubDetLiq.size()
                );
            }
            
            pstLoc = con.prepareStatement(strSQL);

            for (int intFil = 0; intFil < objTblModLiq.getRowCount(); intFil++)
            {
                String strConcepto = obtenerTextoTabla(intFil,INT_TBL_CON);
                String strDetalle = obtenerTextoTabla(intFil,INT_TBL_DET);
                Integer intCodRub = null;
                if (intFil < arrCodRubDetLiq.size())
                {
                    intCodRub = arrCodRubDetLiq.get(intFil);
                }

                String strTipCon = obtenerTipoConcepto(strConcepto,intCodRub);

                /*
                 * Cuando el concepto proviene del rol,
                 * debe existir el código del rubro.
                 */
                if ("ROL".equals(strTipCon) && intCodRub == null)
                {
                    throw new Exception(
                        "No se pudo identificar el rubro "
                        + "de la línea "
                        + (intFil + 1)
                        + ": "
                        + strConcepto
                    );
                }

                BigDecimal bgBase = obtenerBigDecimalNullable(objTblModLiq.getValueAt(intFil,INT_TBL_BAS));
                BigDecimal bgIngreso = obtenerBigDecimal(objTblModLiq.getValueAt(intFil,INT_TBL_ING));
                BigDecimal bgEgreso =  obtenerBigDecimal(objTblModLiq.getValueAt(intFil,INT_TBL_EGR));

                if (bgIngreso.compareTo(BigDecimal.ZERO) > 0 && bgEgreso.compareTo(BigDecimal.ZERO) > 0)
                {
                    throw new Exception(
                        "La línea "
                        + (intFil + 1)
                        + " tiene ingreso y descuento "
                        + "al mismo tiempo."
                    );
                }

                int intPar = 1;

                pstLoc.setInt(intPar++, intCodEmp);
                pstLoc.setInt(intPar++, intCodLoc);
                pstLoc.setInt(intPar++, intCodTipDoc);
                pstLoc.setInt(intPar++, intCodDoc);
                pstLoc.setInt(intPar++, intFil + 1);

                if (intCodRub == null)
                {
                    pstLoc.setNull(intPar++,Types.SMALLINT);
                }
                else
                {
                    pstLoc.setInt(intPar++,intCodRub);
                }

                pstLoc.setString(intPar++,strTipCon);
                pstLoc.setString(intPar++,strConcepto);

                if (bgBase == null)
                {
                    pstLoc.setNull(intPar++,Types.NUMERIC);
                }
                else
                {
                    pstLoc.setBigDecimal(intPar++,bgBase);
                }

                if (strDetalle.equals(""))
                {
                    pstLoc.setNull(intPar++,Types.VARCHAR);
                }
                else
                {
                    pstLoc.setString(intPar++,strDetalle);
                }

                pstLoc.setBigDecimal(intPar++,bgIngreso);
                pstLoc.setBigDecimal(intPar++,bgEgreso);
                pstLoc.setInt(intPar++,objZafParSis.getCodigoUsuario());
                pstLoc.addBatch();
            }

            int[] arrRes = pstLoc.executeBatch();
            if (arrRes.length!= objTblModLiq.getRowCount())
            {
                throw new Exception(
                    "No se guardó todo el detalle "
                    + "de la liquidación."
                );
            }

            blnRes = true;
        }
        finally
        {
            try {
                pstLoc.close();
            }
            catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    private boolean insertarAutorizadoresLiquidacion(
            java.sql.Connection con,
            int intCodEmp,
            int intCodLoc,
            int intCodTipDoc,
            int intCodDoc)
            throws Exception
    {
        boolean blnRes = false;

        java.sql.PreparedStatement pstLoc = null;

        String strSQL =
                "INSERT INTO tbm_autliqhab ("
                + "    co_emp, "
                + "    co_loc, "
                + "    co_tipdoc, "
                + "    co_doc, "
                + "    co_reg, "
                + "    co_usraut, "
                + "    st_aut, "
                + "    st_reg, "
                + "    co_usring, "
                + "    fe_ing"
                + ") VALUES ("
                + "    ?, ?, ?, ?, "
                + "    1, ?, "
                + "    'P', "
                + "    'A', "
                + "    ?, "
                + "    CURRENT_TIMESTAMP"
                + ")";

        try
        {
            pstLoc = con.prepareStatement(strSQL);

            int intPar = 1;

            pstLoc.setInt(intPar++,intCodEmp);
            pstLoc.setInt(intPar++,intCodLoc);
            pstLoc.setInt(intPar++,intCodTipDoc);
            pstLoc.setInt(intPar++,intCodDoc);
            /*
             * Actualmente el único autorizador es el usuario 156.
             */
            pstLoc.setInt(intPar++,INT_COD_USR_AUT_LIQ);

            /*
             * Usuario que creó la liquidación y el registro
             * pendiente de autorización.
             */
            pstLoc.setInt(intPar++,objZafParSis.getCodigoUsuario());
            blnRes = pstLoc.executeUpdate() == 1;
        }
        finally
        {
            try
            {
                if (pstLoc != null)
                {
                    pstLoc.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }

        return blnRes;
    }
    
    
    private void configurarBarraHerramientas()
    {
        objTooBar = new MiToolBar(this);

        /*
         * Operaciones que utilizaremos.
         */
        objTooBar.setVisibleAceptar(true);
        objTooBar.setVisibleInsertar(true);

        /*
         * Por ahora no permitimos modificar ni eliminar.
         */
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleVistaPreliminar(false);
        objTooBar.setVisibleImprimir(true);

        panBarGen.add(objTooBar,java.awt.BorderLayout.CENTER);
    }
    
    private boolean consultarReg()
    {
        boolean blnRes = false;

        String strSQL = "";

        try
        {
            /*
             * Cerrar una consulta anterior antes de generar
             * un nuevo ResultSet de navegación.
             */
            cerrarConsultaCabecera();
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodLoc = objZafParSis.getCodigoLocal();
            conCab = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (conCab != null)
            {
                /*
                 * Este ResultSet debe permitir navegar:
                 * first, previous, next y last.
                 */
                stmCab = conCab.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);

                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    a.co_emp, ";
                strSQL += "    a.co_loc, ";
                strSQL += "    a.co_tipdoc, ";
                strSQL += "    a.co_doc ";
                strSQL += "FROM tbm_cabliqhab AS a ";
                strSQL += "WHERE a.co_emp = " + intCodEmp;
                strSQL += "  AND a.co_loc = " + intCodLoc;
                strSQL += "  AND a.co_tipdoc = " + INT_COD_TIP_DOC_LIQ_HAB;

                /*
                 * Si el usuario ingresó un trabajador,
                 * se consulta únicamente ese trabajador.
                 *
                 * Si está vacío, se consultan todas
                 * las liquidaciones.
                 */
                if (!txtCodTra.getText().trim().equals(""))
                {
                    strSQL += "  AND a.co_tra = "+ txtCodTra.getText().trim();
                }
                strSQL += " ORDER BY a.co_doc";

                System.out.println("SQL consultar liquidaciones: "+ strSQL);
                rstCab = stmCab.executeQuery(strSQL);

                if (rstCab.next())
                {
                    /*
                     * Se carga inicialmente el documento más reciente
                     * de los encontrados.
                     */
                    rstCab.last();

                    int intCanReg = rstCab.getRow();
                    objTooBar.setMenSis(
                        "Se encontraron "
                        + intCanReg
                        + " registros."
                    );

                    if (!cargarReg())
                    {
                        return false;
                    }

                    blnRes = true;
                }
                else
                {
                    mostrarMsgInf(
                        "No se encontraron liquidaciones "
                        + "que cumplan el criterio de búsqueda."
                    );

                    limpiarFrm();

                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");

                    cerrarConsultaCabecera();
                }
            }
        }
        catch (Exception e)
        {
            blnRes = false;

            cerrarConsultaCabecera();

            objUti.mostrarMsgErr_F1(
                this,
                e
            );
        }

        return blnRes;
    }
    
    private void cerrarConsultaCabecera()
    {
        try
        {
            if (rstCab != null)
            {
                rstCab.close();
                rstCab = null;
            }
        }
        catch (Exception ignore)
        {
        }

        try
        {
            if (stmCab != null)
            {
                stmCab.close();
                stmCab = null;
            }
        }
        catch (Exception ignore)
        {
        }

        try
        {
            if (conCab != null)
            {
                conCab.close();
                conCab = null;
            }
        }
        catch (Exception ignore)
        {
        }
    }
    
    
    private boolean cargarReg()
    {
        boolean blnRes = false;

        java.sql.Connection conLoc = null;
        java.sql.Statement stmCabLoc = null;
        java.sql.Statement stmDetLoc = null;
        java.sql.ResultSet rstCabLoc = null;
        java.sql.ResultSet rstDetLoc = null;

        String strSQL = "";

        try
        {
            if (rstCab == null)
            {
                return false;
            }

            int intCodEmp = rstCab.getInt("co_emp");
            int intCodLoc = rstCab.getInt("co_loc");
            int intCodTipDoc = rstCab.getInt("co_tipdoc");
            int intCodDoc = rstCab.getInt("co_doc");
            conLoc = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());

            if (conLoc != null)
            {
                stmCabLoc = conLoc.createStatement();
                stmDetLoc = conLoc.createStatement();

                /*
                 * Cargar cabecera y datos del trabajador.
                 */
                strSQL = "";

                strSQL += "SELECT ";
                strSQL += "    a.co_emp, ";
                strSQL += "    a.co_loc, ";
                strSQL += "    a.co_tipdoc, ";
                strSQL += "    a.co_doc, ";
                strSQL += "    a.fe_doc, ";
                strSQL += "    a.ne_numdoc, ";
                strSQL += "    a.co_tra, ";
                strSQL += "    a.fe_inicon, ";
                strSQL += "    a.fe_fincon, ";
                strSQL += "    a.tx_causal, ";
                strSQL += "    a.co_cauliq, ";
                strSQL += "    a.nd_suetra, ";
                strSQL += "    a.nd_basremliq, ";
                strSQL += "    a.st_pagrolfinmes, ";
                strSQL += "    a.nd_toting, ";
                strSQL += "    a.nd_totegr, ";
                strSQL += "    a.nd_totliq, ";
                strSQL += "    a.tx_obs1, ";
                strSQL += "    a.st_aut, ";
                strSQL += "    a.st_reg, ";
                strSQL += "    a.ne_diatra, ";
                strSQL += "    COALESCE(a.tx_fonres, '') AS tx_fonres, ";
                strSQL += "    COALESCE(a.tx_decter, '') AS tx_decter, ";
                strSQL += "    COALESCE(a.tx_deccua, '') AS tx_deccua, ";

                strSQL += "    b.tx_ide, ";

                strSQL += "    TRIM( ";
                strSQL += "        COALESCE(b.tx_ape,'') ";
                strSQL += "        || ' ' || ";
                strSQL += "        COALESCE(b.tx_nom,'') ";
                strSQL += "    ) AS tx_nomtra ";

                strSQL += "FROM tbm_cabliqhab AS a ";

                strSQL += "INNER JOIN tbm_tra AS b ";
                strSQL += "    ON b.co_tra = a.co_tra ";

                strSQL += "WHERE a.co_emp = " + intCodEmp;
                strSQL += "  AND a.co_loc = " + intCodLoc;
                strSQL += "  AND a.co_tipdoc = " + intCodTipDoc;
                strSQL += "  AND a.co_doc = " + intCodDoc;

                System.out.println("SQL cargar cabecera liquidación: " + strSQL);
                rstCabLoc = stmCabLoc.executeQuery(strSQL);

                if (!rstCabLoc.next())
                {
                    mostrarMsgInf(
                        "No se encontró la cabecera "
                        + "de la liquidación."
                    );

                    return false;
                }

                /*
                 * Limpiar el reporte anterior.
                 */
                limpiarDetalleLiquidacion();
                txtCodTra.setText(rstCabLoc.getString("co_tra"));
                txtNomTra.setText(rstCabLoc.getString("tx_nomtra"));
                txtCedTra.setText(rstCabLoc.getString("tx_ide"));
                java.text.SimpleDateFormat objForFec =  new java.text.SimpleDateFormat("dd/MM/yyyy");
                /*
                * Fecha real del documento consultado.
                */
               if (rstCabLoc.getDate("fe_doc") != null)
               {
                   txtFecDoc.setText(objForFec.format(rstCabLoc.getDate("fe_doc")));
               }
               else
               {
                   txtFecDoc.setText("");
               }

                txtFecIniCon.setText(objForFec.format(rstCabLoc.getDate("fe_inicon")));
                txtFecFinCon.setText(objForFec.format(rstCabLoc.getDate("fe_fincon")));
                txtSueMen.setText(rstCabLoc.getString("nd_suetra"));                
                txtDiasTra.setText(rstCabLoc.getString("ne_diatra"));
                txtFonRes.setText(rstCabLoc.getString("tx_fonres"));
                txtDecTer.setText(rstCabLoc.getString("tx_decter"));
                txtDecCua.setText(rstCabLoc.getString("tx_deccua"));
                
                /*
                 * Causal guardada.
                 */
                int intCodCau = rstCabLoc.getInt("co_cauliq");
                if (!rstCabLoc.wasNull())
                {
                    seleccionarCausaLiquidacion(intCodCau);
                }
                else
                {
                    cmbCau.setSelectedIndex(-1);
                }

                
                String strObs = rstCabLoc.getString("tx_obs1");

                txaObs.setText(strObs == null ? "" : strObs);

                blnFuePagadoRolFinMes = "S".equalsIgnoreCase(rstCabLoc.getString("st_pagrolfinmes"));
                dblBaseRemLiq = rstCabLoc.getDouble("nd_basremliq");
                dblTotIng = rstCabLoc.getDouble("nd_toting");
                dblTotEgr = rstCabLoc.getDouble("nd_totegr");
                double dblTotLiq = rstCabLoc.getDouble("nd_totliq");
                intCodDocGuardado = rstCabLoc.getInt("co_doc");
                intNumDocGuardado = rstCabLoc.getInt("ne_numdoc");
                txtCodDoc.setText(String.valueOf(intCodDocGuardado));
                txtNumDoc.setText(String.valueOf(intNumDocGuardado));
                
                /*
                 * Cargar el detalle guardado.
                 */
                strSQL = "";

                strSQL += "SELECT ";
                strSQL += "    co_reg, ";
                strSQL += "    co_rub, ";
                strSQL += "    tx_tipcon, ";
                strSQL += "    tx_nomcon, ";
                strSQL += "    nd_base, ";
                strSQL += "    tx_det, ";
                strSQL += "    nd_ing, ";
                strSQL += "    nd_egr ";

                strSQL += "FROM tbm_detliqhab ";

                strSQL += "WHERE co_emp = " + intCodEmp;
                strSQL += "  AND co_loc = " + intCodLoc;
                strSQL += "  AND co_tipdoc = " + intCodTipDoc;
                strSQL += "  AND co_doc = " + intCodDoc;
                strSQL += "  AND st_reg = 'A' ";

                strSQL += "ORDER BY co_reg";

                System.out.println(
                    "SQL cargar detalle liquidación: "
                    + strSQL
                );

                rstDetLoc = stmDetLoc.executeQuery(strSQL);

                while (rstDetLoc.next())
                {
                    Double dblBase = null;
                    Double dblIngreso = null;
                    Double dblDescuento = null;
                    Integer intCodRub = null;

                    if (rstDetLoc.getObject("nd_base") != null)
                    {
                        dblBase = rstDetLoc.getDouble("nd_base");
                    }

                    double dblAuxIng = rstDetLoc.getDouble("nd_ing");

                    if (dblAuxIng != 0)
                    {
                        dblIngreso = dblAuxIng;
                    }

                    double dblAuxEgr = rstDetLoc.getDouble("nd_egr");

                    if (dblAuxEgr != 0)
                    {
                        dblDescuento = dblAuxEgr;
                    }

                    if (rstDetLoc.getObject("co_rub") != null)
                    {
                        intCodRub = rstDetLoc.getInt("co_rub");
                    }

                    agregarFilaReporte(
                        rstDetLoc.getString("tx_nomcon"),
                        dblBase,
                        rstDetLoc.getString("tx_det"),
                        dblIngreso,
                        dblDescuento,
                        intCodRub
                    );
                }

                /*
                 * Mostrar exactamente los totales guardados.
                 */
                txtTotIng.setText(String.valueOf(objUti.redondear(dblTotIng,2)));
                txtTotEgr.setText(String.valueOf(objUti.redondear(dblTotEgr,2)));
                txtTotLiq.setText(String.valueOf(objUti.redondear(dblTotLiq,2)));

                /*
                 * Mostrar la pestaña Reporte.
                 */
                tabFrm.setEnabledAt(1, true);
                mostrarPestanaReporte();

                /*
                 * Una liquidación consultada no debe volver
                 * a insertarse.
                 */
                

                /*
                 * Estado visual del documento.
                 */
                String strEstReg = rstCabLoc.getString("st_reg");
                if ("I".equalsIgnoreCase(strEstReg))
                {
                    objTooBar.setEstadoRegistro("Anulado");
                }
                else
                {
                    objTooBar.setEstadoRegistro("Activo");
                }

                /*
                 * Posición de navegación.
                 */
                int intPosRel = rstCab.getRow();
                rstCab.last();

                int intTotReg = rstCab.getRow();
                rstCab.absolute(intPosRel);

                objTooBar.setPosicionRelativa(
                    intPosRel
                    + " / "
                    + intTotReg
                );

                blnRes = true;
            }
        }
        catch (Exception e)
        {
            blnRes = false;

            objUti.mostrarMsgErr_F1(
                this,
                e
            );
        }
        finally
        {
            try {
                rstDetLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                rstCabLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmDetLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                stmCabLoc.close();
            } catch (Throwable ignore) {
            }

            try {
                conLoc.close();
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }
    
    private boolean imprimirLiquidacion()
    {
        boolean blnRes = false;

        try
        {
            if (objTblModLiq == null || objTblModLiq.getRowCount() == 0)
            {
                mostrarMsgInf("No existe una liquidación para imprimir.");
                return false;
            }

            /*
             * Para imprimir el documento definitivo,
             * la liquidación debe estar guardada.
             */
            if (intCodDocGuardado <= 0)
            {
                mostrarMsgInf("Primero debe guardar la liquidación.");
                return false;
            }

            PrinterJob objTraImp = PrinterJob.getPrinterJob();
            objTraImp.setJobName("Liquidación de Haberes - " + txtCodTra.getText().trim());
            PageFormat objForPag = objTraImp.defaultPage();
            objForPag.setOrientation(PageFormat.PORTRAIT);
            objTraImp.setPrintable(new ReporteLiquidacionPrintable(),objForPag);
            /*
             * Mostrar selección de impresora.
             */
            if (objTraImp.printDialog())
            {
                objTraImp.print();
            }

            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;

            objUti.mostrarMsgErr_F1(
                this,
                e
            );
        }

        return blnRes;
    }
    
    private class ReporteLiquidacionPrintable
    implements Printable
    {
        @Override
        public int print(
                Graphics graphics,
                PageFormat pageFormat,
                int pageIndex)
                throws PrinterException
        {
            /*
             * La liquidación se imprime en una sola página.
             */
            if (pageIndex > 0)
            {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) graphics.create();

            try
            {
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                /*
                 * Escala exclusiva para impresión.
                 *
                 * La vista del programa no se modifica.
                 */
                double dblEscala = 0.80;

                /*
                 * Primero nos ubicamos dentro del área
                 * realmente imprimible de la impresora.
                 */
                g2d.translate(pageFormat.getImageableX(),pageFormat.getImageableY());

                /*
                 * Reducimos proporcionalmente todo el reporte.
                 */
                g2d.scale(dblEscala,dblEscala);

                /*
                 * Ya estamos ubicados dentro del área imprimible,
                 * por eso aquí trabajamos desde coordenadas locales.
                 */
                int x = 20;
                int y = 20;

                /*
                 * Como el Graphics fue escalado, aumentamos
                 * el ancho lógico disponible.
                 *
                 * Esto también ayuda a que no se corte
                 * la columna derecha:
                 * Causal, Cédula, Cargo, etc.
                 */
                int intAncPag = (int) (pageFormat.getImageableWidth()/ dblEscala ) - 40;
                dibujarLiquidacion(g2d,x,y,intAncPag);
                return Printable.PAGE_EXISTS;
            }
            finally
            {
                g2d.dispose();
            }
        }
    }

    private int dibujarDetalleLiquidacion(
            Graphics2D g2d,
            int x,
            int y,
            int intAncPag,
            boolean blnIngresos,
            Font objFueSub,
            Font objFueNor,
            Font objFueNeg)
    {
        String strTitulo =
                blnIngresos
                ? "INGRESOS"
                : "DESCUENTOS";

        int intColVal =
                blnIngresos
                ? INT_TBL_ING
                : INT_TBL_EGR;
        /*
        * Los valores individuales se muestran en una
        * columna intermedia.
        *
        * El subtotal permanece alineado al margen derecho.
        */
       int intXDerValor =
               x
               + (int) Math.round(
                   intAncPag * 0.90
               );

       int intXDerSubtotal =
               x + intAncPag;

        g2d.setFont(objFueSub);
        
        
        Font objFueConDet =
                new Font(
                    "SansSerif",
                    Font.BOLD,
                    9
                );

        Font objFueDetDet =
                new Font(
                    "SansSerif",
                    Font.PLAIN,
                    8
                );

        g2d.drawString(
            strTitulo,
            x,
            y
        );

        g2d.drawLine(
            x,
            y + 2,
            x + 80,
            y + 2
        );

        y += 18;

        for (
            int intFil = 0;
            intFil < objTblModLiq.getRowCount();
            intFil++
        )
        {
            double dblVal =
                    obtenerNumeroTabla(
                        intFil,
                        intColVal
                    );

            if (dblVal <= 0)
            {
                continue;
            }

            String strConcepto =
                    obtenerTextoTablaImpresion(
                        intFil,
                        INT_TBL_CON
                    );

            String strDetalle =
                    obtenerTextoTablaImpresion(
                        intFil,
                        INT_TBL_DET
                    );

            g2d.setFont(objFueConDet);

            g2d.drawString(
                strConcepto,
                x,
                y
            );

            dibujarTextoDerechaLiq(
                g2d,
                formatearValorImpresion(
                    dblVal
                ),
                intXDerValor,
                y
            );

            /*
             * Mostrar debajo el período o fórmula.
             */
            if (!strDetalle.equals(""))
            {
                y += 11;

                g2d.setFont(objFueDetDet);

                g2d.drawString(
                    strDetalle,
                    x + 10,
                    y
                );
            }

            y += 15;
        }

        y -= 7;
        
        g2d.drawLine(
            x,
            y,
            x + intAncPag,
            y
        );

        y += 12;

        g2d.setFont(objFueNeg);

        String strSubTot =
                blnIngresos
                ? "SUBTOTAL INGRESOS"
                : "SUBTOTAL DESCUENTOS";

        g2d.drawString(
            strSubTot,
            x,
            y
        );

        double dblTot =
                blnIngresos
                ? obtenerNumeroTexto(
                    txtTotIng.getText()
                )
                : obtenerNumeroTexto(
                    txtTotEgr.getText()
                );

        String strValTot =
                blnIngresos
                ? formatearValorImpresion(
                    dblTot
                )
                : "-"
                    + formatearValorImpresion(
                        dblTot
                    );

        dibujarTextoDerechaLiq(
            g2d,
            strValTot,
            intXDerSubtotal,
            y
        );

        return y;
    }
    
    private String obtenerTextoTablaImpresion(
        int intFil,
        int intCol)
    {
        Object objVal =
                objTblModLiq.getValueAt(
                    intFil,
                    intCol
                );

        return objVal == null
                ? ""
                : objVal.toString().trim();
    }
    
    private double obtenerNumeroTabla(
        int intFil,
        int intCol)
    {
        Object objVal =
                objTblModLiq.getValueAt(
                    intFil,
                    intCol
                );

        if (objVal == null)
        {
            return 0.00;
        }

        if (objVal instanceof Number)
        {
            return (
                (Number) objVal
            ).doubleValue();
        }

        return obtenerNumeroTexto(
            objVal.toString()
        );
    }
    
    private double obtenerNumeroTexto(
        String strVal)
    {
        try
        {
            if (strVal == null
                    || strVal.trim().equals(""))
            {
                return 0.00;
            }

            return Double.parseDouble(
                strVal.trim()
                    .replace(",", "")
            );
        }
        catch (Exception e)
        {
            return 0.00;
        }
    }

    private String formatearValorImpresion(
        double dblVal)
    {
        return String.format(
            new Locale(
                "es",
                "EC"
            ),
            "%,.2f",
            dblVal
        );
    }
    
    private String obtenerCausalImpresion()
    {
        if (cmbCau.getSelectedIndex() >= 0 && cmbCau.getSelectedItem() != null)
        {
            return cmbCau.getSelectedItem().toString();
        }

        return lblCausalRep.getText();
    }
    
    private void dibujarTextoCentradoLiq(
        Graphics2D g2d,
        String strTexto,
        int x,
        int y,
        int intAncho)
    {
        FontMetrics objMetFue =
                g2d.getFontMetrics();

        int intAncTex =
                objMetFue.stringWidth(
                    strTexto
                );

        int intXTex =
                x
                + (
                    intAncho
                    - intAncTex
                ) / 2;

        g2d.drawString(
            strTexto,
            intXTex,
            y
        );
    }
    
    private void dibujarTextoDerechaLiq(
        Graphics2D g2d,
        String strTexto,
        int intXDer,
        int y)
    {
        FontMetrics objMetFue =
                g2d.getFontMetrics();

        int intAncTex =
                objMetFue.stringWidth(
                    strTexto
                );

        g2d.drawString(
            strTexto,
            intXDer - intAncTex,
            y
        );
    }
    
    private void dibujarEtiquetaValorLiq(
        Graphics2D g2d,
        String strEtiqueta,
        String strValor,
        int x,
        int y,
        int intAncEti)
    {
        Font objFueAct =
                g2d.getFont();

        g2d.setFont(
            objFueAct.deriveFont(
                Font.BOLD
            )
        );

        g2d.drawString(
            strEtiqueta,
            x,
            y
        );

        g2d.setFont(
            objFueAct.deriveFont(
                Font.PLAIN
            )
        );

        g2d.drawString(
            strValor == null
            ? ""
            : strValor,
            x + intAncEti,
            y
        );

        g2d.setFont(objFueAct);
    }
    
    private void dibujarFirmasLiquidacion(
        Graphics2D g2d,
        int x,
        int y,
        int intAncPag)
    {
        Font objFueFir =
                new Font(
                    "SansSerif",
                    Font.BOLD,
                    8
                );

        g2d.setFont(objFueFir);

        int intAncLin = 140;
        int intMarFir = 25;

        int intXEla =
                x + intMarFir;

        int intXRec =
                x
                + intAncPag
                - intAncLin
                - intMarFir;

        g2d.drawLine(
            intXEla,
            y,
            intXEla + intAncLin,
            y
        );

        g2d.drawLine(
            intXRec,
            y,
            intXRec + intAncLin,
            y
        );

        y += 14;

        dibujarTextoCentradoLiq(
            g2d,
            "ELABORADO",
            intXEla,
            y,
            intAncLin
        );

        dibujarTextoCentradoLiq(
            g2d,
            "APROBADO",
            intXRec,
            y,
            intAncLin
        );
    }
    
    private String cortarTextoImpresion(
        String strTexto,
        Graphics2D g2d,
        int intAncMax)
    {
        if (strTexto == null)
        {
            return "";
        }

        FontMetrics objMetFue =
                g2d.getFontMetrics();

        String strRes =
                strTexto.trim();

        while (
            strRes.length() > 0
            && objMetFue.stringWidth(
                strRes
            ) > intAncMax
        )
        {
            strRes =
                    strRes.substring(
                        0,
                        strRes.length() - 1
                    );
        }

        if (!strRes.equals(strTexto.trim()))
        {
            strRes += "...";
        }

        return strRes;
    }
    
    private String obtenerCargoTrabajador()
    {
        java.sql.Connection conLoc = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strCargo = "";
        String strSQL = "";

        try
        {
            int intCodEmp =
                    obtenerCodigoEmpresa();

            int intCodTra =
                    Integer.parseInt(
                        txtCodTra.getText().trim()
                    );

            /*
             * Las fechas de la pantalla están en formato dd/MM/yyyy.
             */
            java.text.SimpleDateFormat objForFecPan =
                    new java.text.SimpleDateFormat(
                        "dd/MM/yyyy"
                    );

            java.text.SimpleDateFormat objForFecSQL =
                    new java.text.SimpleDateFormat(
                        "yyyy-MM-dd"
                    );

            String strFecIni =
                    objForFecSQL.format(
                        objForFecPan.parse(
                            txtFecIniCon.getText().trim()
                        )
                    );

            String strFecFin =
                    objForFecSQL.format(
                        objForFecPan.parse(
                            txtFecFinCon.getText().trim()
                        )
                    );

            conLoc = java.sql.DriverManager.getConnection(
                objZafParSis.getStringConexion(),
                objZafParSis.getUsuarioBaseDatos(),
                objZafParSis.getClaveBaseDatos()
            );

            if (conLoc != null)
            {
                stmLoc = conLoc.createStatement();

                strSQL = "";

                strSQL += "SELECT ";
                strSQL += "    c.tx_nomcar ";

                strSQL += "FROM tbm_traemp AS b ";

                strSQL += "INNER JOIN tbm_carlab AS c ";
                strSQL += "    ON c.co_car = b.co_car ";

                strSQL += "WHERE b.co_emp = " + intCodEmp;
                strSQL += "  AND b.co_tra = " + intCodTra;
                strSQL += "  AND b.fe_inicon = '" + strFecIni + "'";
                strSQL += "  AND b.fe_fincon = '" + strFecFin + "' ";

                strSQL += "LIMIT 1";

                System.out.println(
                    "SQL cargo trabajador: "
                    + strSQL
                );

                rstLoc = stmLoc.executeQuery(
                    strSQL
                );

                if (rstLoc.next())
                {
                    strCargo =
                            rstLoc.getString(
                                "tx_nomcar"
                            );

                    if (strCargo == null)
                    {
                        strCargo = "";
                    }
                }
            }
        }
        catch (Exception e)
        {
            strCargo = "";

            System.out.println(
                "No se pudo obtener el cargo del trabajador: "
                + e.getMessage()
            );
        }
        finally
        {
            try {
                rstLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                stmLoc.close();
            }
            catch (Throwable ignore) {
            }

            try {
                conLoc.close();
            }
            catch (Throwable ignore) {
            }
        }

        return strCargo.trim();
    }
    
    
    private void configurarVistaReporte()
    {
        /*
         * La tabla no se elimina. Solamente vuelve a colocarse
         * dentro del JScrollPane cuando esta opción sea false.
         */
        if (!blnUsarVistaImprimible)
        {
            spnDetLiq.setViewportView(tblDetLiq);

            panCabRep.setVisible(true);
            panPieRep.setVisible(true);

            return;
        }

        panVistaLiquidacion =
                new PanelVistaLiquidacion();

        panVistaLiquidacion.setOpaque(true);

        /*
         * Fondo exterior de la vista.
         */
        panVistaLiquidacion.setBackground(
            new java.awt.Color(220, 220, 220)
        );

        spnDetLiq.setViewportView(
            panVistaLiquidacion
        );

        spnDetLiq.getVerticalScrollBar()
                 .setUnitIncrement(16);

        /*
         * La nueva vista ya dibuja su propia cabecera
         * y sus propios totales.
         *
         * Estos paneles no se borran; quedan ocultos.
         */
        panCabRep.setVisible(false);
        panPieRep.setVisible(false);

        actualizarVistaReporte();
    }
    
    private void actualizarVistaReporte()
    {
        if (!blnUsarVistaImprimible
                || panVistaLiquidacion == null)
        {
            return;
        }

        /*
         * El alto aumenta según la cantidad de conceptos.
         */
        int intCanFil =
                objTblModLiq == null
                ? 0
                : objTblModLiq.getRowCount();

        int intAltVista =
                Math.max(
                    900,
                    650 + (intCanFil * 32)
                );

        int intAncVistaEsc =
                (int) Math.ceil(
                    760 * dblEscalaVistaReporte
                );

        int intAltVistaEsc =
                (int) Math.ceil(
                    intAltVista * dblEscalaVistaReporte
                );

        panVistaLiquidacion.setPreferredSize(
            new java.awt.Dimension(
                intAncVistaEsc,
                intAltVistaEsc
            )
        );

        /*
         * Consultar el cargo una sola vez.
         * paintComponent() puede ejecutarse muchas veces.
         */
        if (!txtCodTra.getText().trim().equals(""))
        {
            strCargoTra =
                    obtenerCargoTrabajador();
        }
        else
        {
            strCargoTra = "";
        }

        panVistaLiquidacion.revalidate();
        panVistaLiquidacion.repaint();

        javax.swing.SwingUtilities.invokeLater(
            new Runnable()
            {
                @Override
                public void run()
                {
                    spnDetLiq.getVerticalScrollBar()
                             .setValue(0);
                }
            }
        );
    }
    
    private class PanelVistaLiquidacion
            extends javax.swing.JPanel
    {
        @Override
        protected void paintComponent(
                java.awt.Graphics graphics)
        {
            super.paintComponent(graphics);

            java.awt.Graphics2D g2d =
                    (java.awt.Graphics2D)
                    graphics.create();

            try
            {
                g2d.setRenderingHint(
                    java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                );

                /*
                 * Medidas originales del documento.
                 */
                int intAncHojaBase = 690;
                int intMarHojaBase = 30;

                /*
                 * Recuperar el alto original antes de la escala.
                 */
                int intAltPanBase =
                        (int) Math.round(
                            getPreferredSize().height
                            / dblEscalaVistaReporte
                        );

                int intAltHojaBase =
                        Math.max(
                            820,
                            intAltPanBase - 40
                        );

                /*
                 * Ancho que ocupará visualmente la hoja ampliada.
                 */
                int intAncHojaEsc =
                        (int) Math.ceil(
                            intAncHojaBase
                            * dblEscalaVistaReporte
                        );

                int intXHoja =
                        Math.max(
                            20,
                            (
                                getWidth()
                                - intAncHojaEsc
                            ) / 2
                        );

                int intYHoja = 20;

                /*
                 * Crear un gráfico independiente para aplicar
                 * la escala solamente a la pantalla.
                 */
                java.awt.Graphics2D g2dHoja =
                        (java.awt.Graphics2D)
                        g2d.create();

                try
                {
                    g2dHoja.translate(
                        intXHoja,
                        intYHoja
                    );

                    /*
                     * Zoom únicamente para la vista del programa.
                     */
                    g2dHoja.scale(
                        dblEscalaVistaReporte,
                        dblEscalaVistaReporte
                    );

                    /*
                     * Fondo blanco de la hoja.
                     */
                    g2dHoja.setColor(
                        java.awt.Color.WHITE
                    );

                    g2dHoja.fillRect(
                        0,
                        0,
                        intAncHojaBase,
                        intAltHojaBase
                    );

                    /*
                     * Borde de la hoja.
                     */
                    g2dHoja.setColor(
                        new java.awt.Color(
                            150,
                            150,
                            150
                        )
                    );

                    g2dHoja.drawRect(
                        0,
                        0,
                        intAncHojaBase,
                        intAltHojaBase
                    );

                    g2dHoja.setColor(
                        java.awt.Color.BLACK
                    );

                    /*
                     * Se utiliza el mismo diseño, pero el Graphics2D
                     * ya está ampliado para la pantalla.
                     */
                    dibujarLiquidacion(
                        g2dHoja,
                        intMarHojaBase,
                        intMarHojaBase,
                        intAncHojaBase
                            - (intMarHojaBase * 2)
                    );
                }
                finally
                {
                    g2dHoja.dispose();
                }
            }
            finally
            {
                g2d.dispose();
            }
        }
    }

    private void dibujarLiquidacion(
            Graphics2D g2d,
            int x,
            int y,
            int intAncPag)
    {
        Font objFueTit =
                    new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                    );

            Font objFueSub =
                    new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                    );

            Font objFueNor =
                    new Font(
                        "SansSerif",
                        Font.PLAIN,
                        8
                    );

            Font objFueNeg =
                    new Font(
                        "SansSerif",
                        Font.BOLD,
                        8
                    );

            /*
             * Nombre del trabajador.
             */
            g2d.setFont(objFueTit);

            dibujarTextoCentradoLiq(
                g2d,
                txtNomTra.getText().trim(),
                x,
                y,
                intAncPag
            );

            y += 20;

            /*
             * Título.
             */
            String strTitulo =
                    "LIQUIDACIÓN DE HABERES";

            dibujarTextoCentradoLiq(
                g2d,
                strTitulo,
                x,
                y,
                intAncPag
            );

            int intAncTit =
                    g2d.getFontMetrics()
                    .stringWidth(strTitulo);

            int intXTit =
                    x
                    + (
                        intAncPag
                        - intAncTit
                    ) / 2;

            g2d.drawLine(
                intXTit,
                y + 2,
                intXTit + intAncTit,
                y + 2
            );

            y += 25;

            /*
            * Datos generales.
            */
           g2d.setFont(objFueNeg);

           int intXIzq = x;
           int intXDer = x + 320;

           int intAncEtiIzq = 95;
           int intAncEtiDer = 90;

           /*
            * Primera fila: Empresa sola
            */
           dibujarEtiquetaValorLiq(
               g2d,
               "Empresa:",
               "TUVAL S.A.",
               intXIzq,
               y,
               intAncEtiIzq
           );

           y += 12;

           /*
            * Segunda fila: Ingreso - Causal
            */
           dibujarEtiquetaValorLiq(
               g2d,
               "Ingreso:",
               txtFecIniCon.getText().trim(),
               intXIzq,
               y,
               intAncEtiIzq
           );

           dibujarEtiquetaValorLiq(
               g2d,
               "Causal:",
               obtenerCausalImpresion(),
               intXDer,
               y,
               intAncEtiDer
           );

           y += 12;

           /*
            * Tercera fila: Salida - Cédula
            */
           dibujarEtiquetaValorLiq(
               g2d,
               "Salida:",
               txtFecFinCon.getText().trim(),
               intXIzq,
               y,
               intAncEtiIzq
           );

           dibujarEtiquetaValorLiq(
               g2d,
               "Cédula:",
               txtCedTra.getText().trim(),
               intXDer,
               y,
               intAncEtiDer
           );

           y += 12;

           /*
            * Cuarta fila: Días trabajados - Cargo
            */
           dibujarEtiquetaValorLiq(
               g2d,
               "Días trabajados:",
               txtDiasTra.getText().trim(),
               intXIzq,
               y,
               intAncEtiIzq
           );

           dibujarEtiquetaValorLiq(
               g2d,
               "Cargo:",
               strCargoTra,
               intXDer,
               y,
               intAncEtiDer
           );

           y += 12;

           /*
            * Quinta fila: Fondos Reserva - Sueldo
            */           
           dibujarEtiquetaValorLiq(
               g2d,
               "Sueldo:",
               formatearValorImpresion(
                   obtenerNumeroTexto(
                       txtSueMen.getText()
                   )
               ),
               intXIzq,
               y,
               intAncEtiIzq
           );
           
           dibujarEtiquetaValorLiq(
               g2d,
               "Fondos Reserva:",
               txtFonRes.getText().trim(),
               intXDer,
               y,
               intAncEtiDer
           );

           

           y += 12;

           /*
            * Sexta fila: Décimo Tercero - Décimo Cuarto
            */
           dibujarEtiquetaValorLiq(
               g2d,
               "Décimo Tercero:",
               txtDecTer.getText().trim(),
               intXIzq,
               y,
               intAncEtiIzq
           );

           dibujarEtiquetaValorLiq(
               g2d,
               "Décimo Cuarto:",
               txtDecCua.getText().trim(),
               intXDer,
               y,
               intAncEtiDer
           );

           y += 13;
          
           /*
            * Desglose del último período utilizado
            * para vacaciones, décimos y remuneración.
            */
           y = dibujarDesgloseUltimoPeriodo(
               g2d,
               x,
               y,
               intAncPag,
               objFueNor,
               objFueNeg
           );

           y += 10;
           

            /*
             * Línea separadora.
             */
            /*g2d.drawLine(
                x,
                y,
                x + intAncPag,
                y
            );*/

            y += 5;

            /*
             * INGRESOS.
             */
            y = dibujarDetalleLiquidacion(
                g2d,
                x,
                y,
                intAncPag,
                true,
                objFueSub,
                objFueNor,
                objFueNeg
            );

            y += 25;

            /*
             * DESCUENTOS.
             */
            y = dibujarDetalleLiquidacion(
                g2d,
                x,
                y,
                intAncPag,
                false,
                objFueSub,
                objFueNor,
                objFueNeg
            );

            y += 20;

            /*
             * TOTAL A RECIBIR.
             */
            g2d.setFont(objFueNeg);

            g2d.drawString(
                "TOTAL A RECIBIR",
                x,
                y + 17
            );

            int intAncCaj = 130;
            int intAltCaj = 25;

            int intXCaj =
                    x
                    + intAncPag
                    - intAncCaj;

            g2d.drawRect(
                intXCaj,
                y,
                intAncCaj,
                intAltCaj
            );

            Font objFueTot =
                    new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                    );

            g2d.setFont(objFueTot);

            dibujarTextoDerechaLiq(
                g2d,
                formatearValorImpresion(
                    obtenerNumeroTexto(
                        txtTotLiq.getText()
                    )
                ),
                intXCaj + intAncCaj - 7,
                y + 18
            );

            y += 45;

            /*
             * Observación.
             */
            String strObs =
                    txaObs.getText() == null
                    ? ""
                    : txaObs.getText().trim();

            if (!strObs.equals(""))
            {
                g2d.setFont(objFueNeg);

                g2d.drawString(
                    "OBSERVACIÓN:",
                    x,
                    y
                );

                y += 14;

                g2d.setFont(objFueNor);

                g2d.drawString(
                    cortarTextoImpresion(
                        strObs,
                        g2d,
                        intAncPag
                    ),
                    x,
                    y
                );

                y += 25;
            }

            /*
             * Firmas.
             */
            y += 50;

            dibujarFirmasLiquidacion(
                g2d,
                x,
                y,
                intAncPag
            );
    }
    
    private int dibujarDesgloseUltimoPeriodo(
            Graphics2D g2d,
            int x,
            int y,
            int intAncPag,
            Font objFueNor,
            Font objFueNeg)
    {
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            int intCodEmp =
                    obtenerCodigoEmpresa();

            int intCodTra =
                    Integer.parseInt(
                        txtCodTra.getText().trim()
                    );

            /*
             * Obtener SBU.
             *
             * Utilizamos la misma clase que ya usa
             * el programa para el cálculo del XIV.
             */
            ZafSueBas objSueBas =
                    new ZafSueBas();

            double dblSbu =
                    objSueBas.getDouNd_SueBas();

            /*
             * Aquí guardaremos primero todos los registros.
             *
             * Es necesario leer todo el período antes de
             * dibujar porque debemos saber qué columnas
             * realmente tienen valores.
             */
            java.util.ArrayList<Object[]> arrDat =
                    new java.util.ArrayList<Object[]>();

            boolean blnMosHorExt = false;
            boolean blnMosOtrIngGra = false;
            boolean blnMosCom = false;

            /*
             * Los décimos se muestran únicamente cuando
             * el trabajador los acumula.
             */
            boolean blnMosDecTer =
                    "Acumula".equalsIgnoreCase(
                        txtDecTer.getText().trim()
                    );

            boolean blnMosDecCua =
                    "Acumula".equalsIgnoreCase(
                        txtDecCua.getText().trim()
                    );

            con = java.sql.DriverManager.getConnection(
                objZafParSis.getStringConexion(),
                objZafParSis.getUsuarioBaseDatos(),
                objZafParSis.getClaveBaseDatos()
            );

            if (con == null)
            {
                return y;
            }

            stmLoc = con.createStatement();

            String strSQL = "";

            strSQL += "WITH contrato AS ( ";

            strSQL += "    SELECT ";
            strSQL += "        co_emp, ";
            strSQL += "        co_tra, ";
            strSQL += "        fe_inicon, ";
            strSQL += "        fe_fincon, ";
            strSQL += "        COALESCE(tx_forpagdectersue,'') ";
            strSQL += "            AS tx_forpagdectersue, ";
            strSQL += "        COALESCE(tx_forpagdeccuasue,'') ";
            strSQL += "            AS tx_forpagdeccuasue ";

            strSQL += "    FROM tbm_traemp ";

            strSQL += "    WHERE co_emp = " + intCodEmp;
            strSQL += "      AND co_tra = " + intCodTra;
            strSQL += "      AND st_reg = 'I' ";
            strSQL += "      AND fe_fincon IS NOT NULL ";

            strSQL += "    ORDER BY ";
            strSQL += "        fe_fincon DESC, ";
            strSQL += "        fe_inicon DESC ";

            strSQL += "    LIMIT 1 ";

//            strSQL += "), ";
//            strSQL += "periodo AS ( ";
//
//            strSQL += "    SELECT ";
//            strSQL += "        MAX(ne_perdes) AS ne_perdes ";
//
//            strSQL += "    FROM tbm_detpervactra ";
//
//            strSQL += "    WHERE co_emp = " + intCodEmp;
//            strSQL += "      AND co_tra = " + intCodTra;
//            strSQL += "      AND st_reg = 'A' ";

            strSQL += ") ";

            strSQL += "SELECT ";

            strSQL += "    vac.ne_perdes, ";
            strSQL += "    vac.ne_perhas, ";
            strSQL += "    vac.fe_ini, ";
            strSQL += "    vac.fe_fin, ";

            /*
             * SUELDO.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE( ";
            strSQL += "            SUM( ";
            strSQL += "                CASE ";
            strSQL += "                    WHEN ing.co_rub = 1 ";
            strSQL += "                    THEN COALESCE(ing.nd_valrub,0) ";
            strSQL += "                    ELSE 0 ";
            strSQL += "                END ";
            strSQL += "            ), ";
            strSQL += "            0 ";
            strSQL += "        ), ";
            strSQL += "        2 ";
            strSQL += "    ) AS sueldo, ";

            /*
             * HORAS EXTRA:
             * rubros 2 y 3.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE( ";
            strSQL += "            SUM( ";
            strSQL += "                CASE ";
            strSQL += "                    WHEN ing.co_rub IN (2,3) ";
            strSQL += "                    THEN COALESCE(ing.nd_valrub,0) ";
            strSQL += "                    ELSE 0 ";
            strSQL += "                END ";
            strSQL += "            ), ";
            strSQL += "            0 ";
            strSQL += "        ), ";
            strSQL += "        2 ";
            strSQL += "    ) AS horas_extra, ";

            /*
             * OTROS INGRESOS GRAVABLES:
             * rubro 22.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE( ";
            strSQL += "            SUM( ";
            strSQL += "                CASE ";
            strSQL += "                    WHEN ing.co_rub = 22 ";
            strSQL += "                    THEN COALESCE(ing.nd_valrub,0) ";
            strSQL += "                    ELSE 0 ";
            strSQL += "                END ";
            strSQL += "            ), ";
            strSQL += "            0 ";
            strSQL += "        ), ";
            strSQL += "        2 ";
            strSQL += "    ) AS otros_ingresos_gravables, ";

            /*
             * COMISIONES:
             * rubro 33.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE( ";
            strSQL += "            SUM( ";
            strSQL += "                CASE ";
            strSQL += "                    WHEN ing.co_rub = 33 ";
            strSQL += "                    THEN COALESCE(ing.nd_valrub,0) ";
            strSQL += "                    ELSE 0 ";
            strSQL += "                END ";
            strSQL += "            ), ";
            strSQL += "            0 ";
            strSQL += "        ), ";
            strSQL += "        2 ";
            strSQL += "    ) AS comisiones, ";

            /*
             * TOTAL REMUNERACIÓN.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE(vac.nd_tot,0), ";
            strSQL += "        2 ";
            strSQL += "    ) AS total, ";

            /*
             * DÉCIMO TERCERO.
             *
             * Solo se calcula si acumula.
             */
            strSQL += "    CASE ";

            strSQL += "        WHEN con.tx_forpagdectersue <> 'M' ";

            strSQL += "         AND vac.fe_fin >= ";
            strSQL += "            CASE ";

            strSQL += "                WHEN ";
            strSQL += "                    EXTRACT(MONTH FROM con.fe_fincon) = 12 ";

            strSQL += "                THEN MAKE_DATE( ";
            strSQL += "                    EXTRACT(YEAR FROM con.fe_fincon)::INTEGER, ";
            strSQL += "                    12, ";
            strSQL += "                    1 ";
            strSQL += "                ) ";

            strSQL += "                ELSE MAKE_DATE( ";
            strSQL += "                    EXTRACT(YEAR FROM con.fe_fincon)::INTEGER - 1, ";
            strSQL += "                    12, ";
            strSQL += "                    1 ";
            strSQL += "                ) ";

            strSQL += "            END ";

            strSQL += "         AND vac.fe_ini <= con.fe_fincon ";

            strSQL += "        THEN ROUND( ";
            strSQL += "            COALESCE(vac.nd_tot,0) / 12.00, ";
            strSQL += "            2 ";
            strSQL += "        ) ";

            strSQL += "        ELSE 0.00 ";

            strSQL += "    END AS decimo_tercero, ";

            /*
             * DÉCIMO CUARTO.
             *
             * Solo se calcula si acumula.
             * Para Costa el período empieza en marzo.
             */
            strSQL += "    CASE ";

            strSQL += "        WHEN con.tx_forpagdeccuasue <> 'M' ";

            strSQL += "         AND vac.fe_fin >= ";

            strSQL += "            CASE ";

            strSQL += "                WHEN ";
            strSQL += "                    EXTRACT(MONTH FROM con.fe_fincon) >= 3 ";

            strSQL += "                THEN MAKE_DATE( ";
            strSQL += "                    EXTRACT(YEAR FROM con.fe_fincon)::INTEGER, ";
            strSQL += "                    3, ";
            strSQL += "                    1 ";
            strSQL += "                ) ";

            strSQL += "                ELSE MAKE_DATE( ";
            strSQL += "                    EXTRACT(YEAR FROM con.fe_fincon)::INTEGER - 1, ";
            strSQL += "                    3, ";
            strSQL += "                    1 ";
            strSQL += "                ) ";

            strSQL += "            END ";

            strSQL += "        THEN ";

            strSQL += "            ROUND( ";
            strSQL += "                (" + dblSbu + " / 360.00) ";
            strSQL += "                * ";

            strSQL += "                CASE ";

            /*
             * Ingreso y salida en el mismo mes.
             *
             * Ejemplo:
             * ingreso 13/08
             * salida  29/08
             *
             * días = 29 - 13 + 1
             */
            strSQL += "                    WHEN ";
            strSQL += "                        EXTRACT(YEAR FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(YEAR FROM con.fe_inicon) ";
            strSQL += "                     AND ";
            strSQL += "                        EXTRACT(MONTH FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(MONTH FROM con.fe_inicon) ";
            strSQL += "                     AND ";
            strSQL += "                        EXTRACT(YEAR FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(YEAR FROM con.fe_fincon) ";
            strSQL += "                     AND ";
            strSQL += "                        EXTRACT(MONTH FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(MONTH FROM con.fe_fincon) ";

            strSQL += "                    THEN ";
            strSQL += "                        LEAST( ";
            strSQL += "                            EXTRACT(DAY FROM con.fe_fincon)::INTEGER, ";
            strSQL += "                            30 ";
            strSQL += "                        ) ";
            strSQL += "                        - ";
            strSQL += "                        LEAST( ";
            strSQL += "                            EXTRACT(DAY FROM con.fe_inicon)::INTEGER, ";
            strSQL += "                            30 ";
            strSQL += "                        ) ";
            strSQL += "                        + 1 ";

            /*
             * Primer mes trabajado.
             *
             * Ejemplo:
             * ingreso 13/08
             *
             * días = 30 - 13 + 1 = 18
             */
            strSQL += "                    WHEN ";
            strSQL += "                        EXTRACT(YEAR FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(YEAR FROM con.fe_inicon) ";
            strSQL += "                     AND ";
            strSQL += "                        EXTRACT(MONTH FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(MONTH FROM con.fe_inicon) ";

            strSQL += "                    THEN ";
            strSQL += "                        30 ";
            strSQL += "                        - LEAST( ";
            strSQL += "                            EXTRACT(DAY FROM con.fe_inicon)::INTEGER, ";
            strSQL += "                            30 ";
            strSQL += "                        ) ";
            strSQL += "                        + 1 ";

            /*
             * Último mes trabajado.
             */
            strSQL += "                    WHEN ";
            strSQL += "                        EXTRACT(YEAR FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(YEAR FROM con.fe_fincon) ";
            strSQL += "                     AND ";
            strSQL += "                        EXTRACT(MONTH FROM vac.fe_fin) ";
            strSQL += "                            = EXTRACT(MONTH FROM con.fe_fincon) ";

            strSQL += "                    THEN LEAST( ";
            strSQL += "                        EXTRACT(DAY FROM con.fe_fincon)::INTEGER, ";
            strSQL += "                        30 ";
            strSQL += "                    ) ";

            /*
             * Mes intermedio completo.
             */
            strSQL += "                    ELSE 30 ";

            strSQL += "                END, ";

            
            strSQL += "                2 ";
            strSQL += "            ) ";

            strSQL += "        ELSE 0.00 ";

            strSQL += "    END AS decimo_cuarto, ";

            /*
             * VACACIONES.
             */
            strSQL += "    ROUND( ";
            strSQL += "        COALESCE(vac.nd_valvac,0), ";
            strSQL += "        2 ";
            strSQL += "    ) AS vacaciones ";

            strSQL += "FROM tbm_detpervactra AS vac ";

//            strSQL += "INNER JOIN periodo AS per ";
//            strSQL += "    ON per.ne_perdes = vac.ne_perdes ";

            strSQL += "CROSS JOIN contrato AS con ";

            strSQL += "LEFT JOIN tbm_ingEgrMenTra AS ing ";
            strSQL += "    ON ing.co_emp = vac.co_emp ";
            strSQL += "   AND ing.co_tra = vac.co_tra ";

            strSQL += "   AND ing.ne_ani = ";
            strSQL += "       EXTRACT(YEAR FROM vac.fe_fin)::INTEGER ";

            strSQL += "   AND ing.ne_mes = ";
            strSQL += "       EXTRACT(MONTH FROM vac.fe_fin)::INTEGER ";

            strSQL += "   AND ing.ne_per = 2 ";

            strSQL += "   AND ing.co_rub IN (1,2,3,22,33) ";

            strSQL += "WHERE vac.co_emp = " + intCodEmp;
            strSQL += "  AND vac.co_tra = " + intCodTra;
            strSQL += "  AND vac.st_reg = 'A' ";
            
            strSQL += "  AND vac.fe_fin >= GREATEST( ";
            strSQL += "      con.fe_inicon, ";
            strSQL += "      (con.fe_fincon - INTERVAL '1 year')::DATE ";
            strSQL += "  ) ";

            strSQL += "  AND vac.fe_ini <= con.fe_fincon ";
            
 
            strSQL += "GROUP BY ";

            strSQL += "    vac.co_reg, ";
            strSQL += "    vac.ne_perdes, ";
            strSQL += "    vac.ne_perhas, ";
            strSQL += "    vac.fe_ini, ";
            strSQL += "    vac.fe_fin, ";
            strSQL += "    vac.nd_tot, ";
            strSQL += "    vac.nd_valvac, ";
            strSQL += "    con.fe_inicon, ";
            strSQL += "    con.fe_fincon, ";
            strSQL += "    con.tx_forpagdectersue, ";
            strSQL += "    con.tx_forpagdeccuasue ";

            strSQL += "ORDER BY ";
            strSQL += "    vac.fe_ini, ";
            strSQL += "    vac.co_reg";

            System.out.println(
                "SQL desglose último período: "
                + strSQL
            );

            rstLoc =
                    stmLoc.executeQuery(
                        strSQL
                    );

            int intPerDes = 0;
            int intPerHas = 0;

            while (rstLoc.next())
            {
                
                System.out.println(
                    "DESGLOSE PERIODO"
                    + " | Vac="
                    + rstLoc.getInt("ne_perdes")
                    + "-"
                    + rstLoc.getInt("ne_perhas")
                    + " | Desde="
                    + rstLoc.getDate("fe_ini")
                    + " | Hasta="
                    + rstLoc.getDate("fe_fin")
                    + " | Sueldo="
                    + rstLoc.getDouble("sueldo")
                    + " | Total="
                    + rstLoc.getDouble("total")
                    + " | XIII="
                    + rstLoc.getDouble("decimo_tercero")
                    + " | XIV="
                    + rstLoc.getDouble("decimo_cuarto")
                    + " | Vac="
                    + rstLoc.getDouble("vacaciones")
                );

                
                
                double dblSue =
                        rstLoc.getDouble(
                            "sueldo"
                        );

                double dblHorExt =
                        rstLoc.getDouble(
                            "horas_extra"
                        );

                double dblOtrIng =
                        rstLoc.getDouble(
                            "otros_ingresos_gravables"
                        );

                double dblCom =
                        rstLoc.getDouble(
                            "comisiones"
                        );

                double dblTot =
                        rstLoc.getDouble(
                            "total"
                        );

                double dblDecTer =
                        rstLoc.getDouble(
                            "decimo_tercero"
                        );

                double dblDecCua =
                        rstLoc.getDouble(
                            "decimo_cuarto"
                        );

                double dblVac =
                        rstLoc.getDouble(
                            "vacaciones"
                        );

                /*
                 * Determinar columnas dinámicas.
                 */
                if (Math.abs(dblHorExt) > 0.001)
                {
                    blnMosHorExt = true;
                }

                if (Math.abs(dblOtrIng) > 0.001)
                {
                    blnMosOtrIngGra = true;
                }

                if (Math.abs(dblCom) > 0.001)
                {
                    blnMosCom = true;
                }

                if (intPerDes == 0)
                {
                    intPerDes =
                            rstLoc.getInt(
                                "ne_perdes"
                            );

                    intPerHas =
                            rstLoc.getInt(
                                "ne_perhas"
                            );
                }

                Object[] objFil =
                        new Object[9];

                objFil[0] =
                        rstLoc.getDate(
                            "fe_ini"
                        );

                objFil[1] =
                        Double.valueOf(
                            dblSue
                        );

                objFil[2] =
                        Double.valueOf(
                            dblHorExt
                        );

                objFil[3] =
                        Double.valueOf(
                            dblOtrIng
                        );

                objFil[4] =
                        Double.valueOf(
                            dblCom
                        );

                objFil[5] =
                        Double.valueOf(
                            dblTot
                        );

                objFil[6] =
                        Double.valueOf(
                            dblDecTer
                        );

                objFil[7] = Double.valueOf(dblDecCua);

                objFil[8] =  Double.valueOf(dblVac);

                arrDat.add(objFil);
            }

            /*
             * Si no existen registros, no dibujamos nada.
             */
            if (arrDat.isEmpty())
            {
                return y;
            }

            /*
             * Formatos.
             */
            java.text.SimpleDateFormat objForMes =
                    new java.text.SimpleDateFormat(
                        "MM-yy"
                    );

            java.text.DecimalFormatSymbols objSim =
                    new java.text.DecimalFormatSymbols(
                        java.util.Locale.US
                    );

            java.text.DecimalFormat objForNum =
                    new java.text.DecimalFormat(
                        "#,##0.00",
                        objSim
                    );

            /*
             * Título del período.
             */
            g2d.setFont(
                objFueNeg
            );

            String strTitPer =
                    "PERÍODO "
                    + intPerDes
                    + "-"
                    + intPerHas;

            dibujarTextoCentradoLiq(
                g2d,
                strTitPer,
                x,
                y,
                intAncPag
            );

            y += 11;

            /*
             * Calcular cuántas columnas numéricas
             * realmente serán visibles.
             *
             * Siempre:
             * Sueldo
             * Total
             * Vacaciones
             */
            int intNumColVal = 3;

            if (blnMosHorExt)
            {
                intNumColVal++;
            }

            if (blnMosOtrIngGra)
            {
                intNumColVal++;
            }

            if (blnMosCom)
            {
                intNumColVal++;
            }

            if (blnMosDecCua)
            {
                intNumColVal++;
            }

            if (blnMosDecTer)
            {
                intNumColVal++;
            }

            /*
             * Ancho de período.
             */
            int intAncPer = 55;

            int intAncDis =
                    intAncPag
                    - intAncPer;

            int intAncCol =
                    intAncDis
                    / intNumColVal;

            int intAltFil = 12;

            /*
             * Línea superior.
             */
            g2d.drawLine(
                x,
                y,
                x + intAncPag,
                y
            );

            y += 9;

            /*
             * CABECERA.
             */
            g2d.setFont(
                new Font(
                    "SansSerif",
                    Font.BOLD,
                    7
                )
            );

            int intXCol = x;

            dibujarTextoCentradoLiq(
                g2d,
                "PERÍODO",
                intXCol,
                y,
                intAncPer
            );

            intXCol +=
                    intAncPer;

            dibujarTextoCentradoLiq(
                g2d,
                "SUELDO",
                intXCol,
                y,
                intAncCol
            );

            intXCol +=
                    intAncCol;

            if (blnMosHorExt)
            {
                dibujarTextoCentradoLiq(
                    g2d,
                    "H. EXTRA",
                    intXCol,
                    y,
                    intAncCol
                );

                intXCol +=
                        intAncCol;
            }

            if (blnMosOtrIngGra)
            {
                dibujarTextoCentradoLiq(
                    g2d,
                    "OTR. ING.",
                    intXCol,
                    y,
                    intAncCol
                );

                intXCol +=
                        intAncCol;
            }

            if (blnMosCom)
            {
                dibujarTextoCentradoLiq(
                    g2d,
                    "COMISIONES",
                    intXCol,
                    y,
                    intAncCol
                );

                intXCol +=
                        intAncCol;
            }

            dibujarTextoCentradoLiq(
                g2d,
                "TOTAL",
                intXCol,
                y,
                intAncCol
            );

            intXCol +=
                    intAncCol;

            /*
             * Igual que el Excel:
             * XIV antes de XIII.
             */
            if (blnMosDecCua)
            {
                dibujarTextoCentradoLiq(
                    g2d,
                    "XIV",
                    intXCol,
                    y,
                    intAncCol
                );

                intXCol +=
                        intAncCol;
            }

            if (blnMosDecTer)
            {
                dibujarTextoCentradoLiq(
                    g2d,
                    "XIII",
                    intXCol,
                    y,
                    intAncCol
                );

                intXCol +=
                        intAncCol;
            }

            dibujarTextoCentradoLiq(
                g2d,
                "VACACIONES",
                intXCol,
                y,
                intAncCol
            );

            y += 5;

            /*
             * Línea debajo de los títulos.
             */
            g2d.drawLine(
                x,
                y,
                x + intAncPag,
                y
            );

            y += 9;

           /*
            * DETALLE.
            */
           g2d.setFont(
               new Font(
                   "SansSerif",
                   Font.PLAIN,
                   7
               )
           );

           /*
            * Acumuladores para la fila TOTAL.
            */
           double dblTotSue = 0.00;
           double dblTotHorExt = 0.00;
           double dblTotOtrIng = 0.00;
           double dblTotCom = 0.00;
           double dblTotRem = 0.00;
           double dblTotDecTer = 0.00;
           double dblTotDecCua = 0.00;
           double dblTotVac = 0.00;

           /*
            * XIII, XIV y vacaciones:
            * utilizar el valor definitivo calculado
            * en la liquidación y no la suma mensual redondeada.
            */
           String strConVac =
                   "Vacaciones "
                   + intPerDes
                   + "-"
                   + intPerHas;

           for (int intFil = 0;
                intFil < objTblModLiq.getRowCount();
                intFil++)
           {
               String strConcepto =
                       obtenerTextoTablaImpresion(
                           intFil,
                           INT_TBL_CON
                       );

               double dblIngreso =
                       obtenerNumeroTabla(
                           intFil,
                           INT_TBL_ING
                       );

               if ("Décimo tercero acumulado".equalsIgnoreCase(
                       strConcepto.trim()))
               {
                   dblTotDecTer = dblIngreso;
               }

               if ("Décimo cuarto acumulado".equalsIgnoreCase(
                       strConcepto.trim()))
               {
                   dblTotDecCua = dblIngreso;
               }

               if (strConVac.equalsIgnoreCase(
                       strConcepto.trim()))
               {
                   dblTotVac = dblIngreso;
               }
           }
           
           
           for (int i = 0;
                i < arrDat.size();
                i++)
           {
               Object[] objFil =
                       arrDat.get(i);

               java.sql.Date datFec =
                       (java.sql.Date)
                       objFil[0];

               double dblSue =
                       (
                           (Double)
                           objFil[1]
                       ).doubleValue();

               double dblHorExt =
                       (
                           (Double)
                           objFil[2]
                       ).doubleValue();

               double dblOtrIng =
                       (
                           (Double)
                           objFil[3]
                       ).doubleValue();

               double dblCom =
                       (
                           (Double)
                           objFil[4]
                       ).doubleValue();

               double dblTot =
                       (
                           (Double)
                           objFil[5]
                       ).doubleValue();

               double dblDecTer =
                       (
                           (Double)
                           objFil[6]
                       ).doubleValue();

               double dblDecCua =
                       (
                           (Double)
                           objFil[7]
                       ).doubleValue();

               double dblVac =
                       (
                           (Double)
                           objFil[8]
                       ).doubleValue();

               /*
                * Acumular totales.
                */
               dblTotSue += dblSue;
               dblTotHorExt += dblHorExt;
               dblTotOtrIng += dblOtrIng;
               dblTotCom += dblCom;
               dblTotRem += dblTot;
               /*dblTotDecTer += dblDecTer;
               dblTotDecCua += dblDecCua;
               dblTotVac += dblVac;*/

               intXCol = x;

               /*
                * PERÍODO.
                */
               String strMes =
                       objForMes.format(
                           datFec
                       );

               dibujarTextoCentradoLiq(
                   g2d,
                   strMes,
                   intXCol,
                   y,
                   intAncPer
               );

               intXCol += intAncPer;

               /*
                * SUELDO.
                */
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblSue
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;

               /*
                * HORAS EXTRA.
                */
               if (blnMosHorExt)
               {
                   dibujarTextoCentradoLiq(
                       g2d,
                       objForNum.format(
                           dblHorExt
                       ),
                       intXCol,
                       y,
                       intAncCol
                   );

                   intXCol += intAncCol;
               }

               /*
                * OTROS INGRESOS GRAVABLES.
                */
               if (blnMosOtrIngGra)
               {
                   dibujarTextoCentradoLiq(
                       g2d,
                       objForNum.format(
                           dblOtrIng
                       ),
                       intXCol,
                       y,
                       intAncCol
                   );

                   intXCol += intAncCol;
               }

               /*
                * COMISIONES.
                */
               if (blnMosCom)
               {
                   dibujarTextoCentradoLiq(
                       g2d,
                       objForNum.format(
                           dblCom
                       ),
                       intXCol,
                       y,
                       intAncCol
                   );

                   intXCol += intAncCol;
               }

               /*
                * TOTAL REMUNERACIÓN.
                */
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTot
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;

               /*
                * DÉCIMO CUARTO.
                */
               if (blnMosDecCua)
               {
                   dibujarTextoCentradoLiq(
                       g2d,
                       objForNum.format(
                           dblDecCua
                       ),
                       intXCol,
                       y,
                       intAncCol
                   );

                   intXCol += intAncCol;
               }

               /*
                * DÉCIMO TERCERO.
                */
               if (blnMosDecTer)
               {
                   dibujarTextoCentradoLiq(
                       g2d,
                       objForNum.format(
                           dblDecTer
                       ),
                       intXCol,
                       y,
                       intAncCol
                   );

                   intXCol += intAncCol;
               }

               /*
                * VACACIONES.
                */
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblVac
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               y += intAltFil;
           }
           
            /*
            * Línea antes de los totales.
            *
            * En este punto "y" ya está debajo
            * de la última fila del período.
            */
           int intYLinTotSup =
                   y - 6;

           g2d.drawLine(
               x,
               intYLinTotSup,
               x + intAncPag,
               intYLinTotSup
           );

           /*
            * Posición de la fila TOTAL.
            */
           y = intYLinTotSup + 10;

           /*
            * FILA TOTAL.
            */
           g2d.setFont(
               new Font(
                   "SansSerif",
                   Font.BOLD,
                   7
               )
           );

           intXCol = x;

           /*
            * TOTAL.
            */
           dibujarTextoCentradoLiq(
               g2d,
               "TOTAL",
               intXCol,
               y,
               intAncPer
           );

           intXCol += intAncPer;

           /*
            * TOTAL SUELDO.
            */
           dibujarTextoCentradoLiq(
               g2d,
               objForNum.format(
                   dblTotSue
               ),
               intXCol,
               y,
               intAncCol
           );

           intXCol += intAncCol;

           /*
            * TOTAL HORAS EXTRA.
            */
           if (blnMosHorExt)
           {
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTotHorExt
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;
           }

           /*
            * TOTAL OTROS INGRESOS GRAVABLES.
            */
           if (blnMosOtrIngGra)
           {
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTotOtrIng
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;
           }

           /*
            * TOTAL COMISIONES.
            */
           if (blnMosCom)
           {
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTotCom
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;
           }

           /*
            * TOTAL REMUNERACIÓN.
            */
           dibujarTextoCentradoLiq(
               g2d,
               objForNum.format(
                   dblTotRem
               ),
               intXCol,
               y,
               intAncCol
           );

           intXCol += intAncCol;

           /*
            * TOTAL DÉCIMO CUARTO.
            */
           if (blnMosDecCua)
           {
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTotDecCua
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;
           }

           /*
            * TOTAL DÉCIMO TERCERO.
            */
           if (blnMosDecTer)
           {
               dibujarTextoCentradoLiq(
                   g2d,
                   objForNum.format(
                       dblTotDecTer
                   ),
                   intXCol,
                   y,
                   intAncCol
               );

               intXCol += intAncCol;
           }

           /*
            * TOTAL VACACIONES.
            */
           dibujarTextoCentradoLiq(
               g2d,
               objForNum.format(
                   dblTotVac
               ),
               intXCol,
               y,
               intAncCol
           );

           /*
            * Línea debajo de los totales.
            */
           int intYLinTotInf =
                   y + 6;

           g2d.drawLine(
               x,
               intYLinTotInf,
               x + intAncPag,
               intYLinTotInf
           );

           /*
            * Continuar el reporte debajo
            * del bloque de totales.
            */
           y = intYLinTotInf + 5;
        }
        catch (Exception e)
        {
            System.out.println(
                "Error al dibujar desglose último período: "
                + e
            );

            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rstLoc != null)
                {
                    rstLoc.close();
                }
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (stmLoc != null)
                {
                    stmLoc.close();
                }
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (con != null)
                {
                    con.close();
                }
            }
            catch (Throwable ignore)
            {
            }
        }

        return y;
    }
    
    
    private boolean cargarEgresosProgramadosLiquidacion()
    {
        boolean blnRes = false;

        java.sql.Connection con = null;
        java.sql.PreparedStatement pstLoc = null;
        java.sql.ResultSet rstLoc = null;

        try
        {
            int intCodEmp = obtenerCodigoEmpresa();
            int intCodTra =
                    Integer.parseInt(
                        txtCodTra.getText().trim()
                    );

            java.sql.Date datFecSal =
                    convertirFechaSql(
                        txtFecFinCon.getText().trim()
                    );

            con = java.sql.DriverManager.getConnection(
                objZafParSis.getStringConexion(),
                objZafParSis.getUsuarioBaseDatos(),
                objZafParSis.getClaveBaseDatos()
            );

            if (con == null)
            {
                return false;
            }

            String strSQL = "";

            strSQL += "SELECT ";
            strSQL += "    b.co_rub, ";
            strSQL += "    r.tx_nom, ";
            strSQL += "    SUM(a.nd_valcuo) AS nd_valliq ";

            strSQL += "FROM tbm_cabingegrprgtra AS b ";

            strSQL += "INNER JOIN tbm_detingegrprgtra AS a ";
            strSQL += "    ON a.co_emp = b.co_emp ";
            strSQL += "   AND a.co_egr = b.co_egr ";

            strSQL += "INNER JOIN tbm_rubrolpag AS r ";
            strSQL += "    ON r.co_rub = b.co_rub ";

            strSQL += "WHERE b.co_emp = ? ";
            strSQL += "  AND b.co_tra = ? ";

            strSQL += "  AND b.st_reg = 'A' ";
            strSQL += "  AND a.st_reg = 'A' ";

            /*
             * El egreso debe tener una cuota correspondiente
             * al mes de salida.
             */
            strSQL += "  AND EXISTS ( ";
            strSQL += "      SELECT 1 ";
            strSQL += "      FROM tbm_detingegrprgtra AS x ";
            strSQL += "      WHERE x.co_emp = b.co_emp ";
            strSQL += "        AND x.co_egr = b.co_egr ";
            strSQL += "        AND x.st_reg = 'A' ";
            strSQL += "        AND DATE_TRUNC('month',x.fe_cuo) ";
            strSQL += "            = DATE_TRUNC('month',CAST(? AS DATE)) ";
            strSQL += "  ) ";

            /*
             * Sumar la cuota del mes de salida
             * y todas las posteriores.
             */
            strSQL += "  AND a.fe_cuo >= ";
            strSQL += "      DATE_TRUNC('month',CAST(? AS DATE)) ";

            strSQL += "GROUP BY ";
            strSQL += "    b.co_rub, ";
            strSQL += "    r.tx_nom ";

            strSQL += "ORDER BY b.co_rub";

            System.out.println(
                "SQL egresos programados liquidación: "
                + strSQL
            );

            pstLoc = con.prepareStatement(strSQL);

            int intPar = 1;

            pstLoc.setInt(intPar++,intCodEmp);
            pstLoc.setInt(intPar++,intCodTra);
            pstLoc.setDate(intPar++,datFecSal);
            pstLoc.setDate(intPar++,datFecSal);

            rstLoc = pstLoc.executeQuery();

            while (rstLoc.next())
            {
                int intCodRub =
                        rstLoc.getInt("co_rub");

                String strNomRub =
                        rstLoc.getString("tx_nom");

                double dblVal =
                        objUti.redondear(
                            rstLoc.getDouble("nd_valliq"),
                            2
                        );

                /*
                 * Si el rubro ya fue cargado desde tbm_ingegrmentra,
                 * no volver a agregarlo.
                 *
                 * En ese caso cargarRubrosUltimoRol() ya reemplazó
                 * el valor normal por el saldo de cuotas.
                 */
                if (arrCodRubDetLiq.contains(intCodRub))
                {
                    continue;
                }

                if (dblVal <= 0)
                {
                    continue;
                }

                agregarFilaReporte(
                    strNomRub,
                    null,
                    "Cuotas pendientes",
                    null,
                    dblVal,
                    intCodRub
                );

                dblTotEgr += dblVal;
            }

            dblTotEgr =
                    objUti.redondear(
                        dblTotEgr,
                        2
                    );

            blnRes = true;
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this,e);
        }
        finally
        {
            try
            {
                if (rstLoc != null)
                    rstLoc.close();
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (pstLoc != null)
                    pstLoc.close();
            }
            catch (Throwable ignore)
            {
            }

            try
            {
                if (con != null)
                    con.close();
            }
            catch (Throwable ignore)
            {
            }
        }

        return blnRes;
    }

    public boolean cargarLiquidacionDesdeAutorizacion(
            int intCodEmp,
            int intCodLoc,
            int intCodTipDoc,
            int intCodDoc,
            boolean blnAbrirReporte)
    {
        boolean blnRes = false;
        String strSQL = "";

        try
        {
            /*
             * Cerrar cualquier consulta que estuviera abierta.
             */
            cerrarConsultaCabecera();

            conCab = java.sql.DriverManager.getConnection(
                    objZafParSis.getStringConexion(),
                    objZafParSis.getUsuarioBaseDatos(),
                    objZafParSis.getClaveBaseDatos()
            );

            if (conCab != null)
            {
                stmCab = conCab.createStatement(
                        java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                        java.sql.ResultSet.CONCUR_READ_ONLY
                );

                strSQL = "";
                strSQL += "SELECT ";
                strSQL += "    a.co_emp, ";
                strSQL += "    a.co_loc, ";
                strSQL += "    a.co_tipdoc, ";
                strSQL += "    a.co_doc ";
                strSQL += "FROM tbm_cabliqhab AS a ";
                strSQL += "WHERE a.co_emp = " + intCodEmp + " ";
                strSQL += "  AND a.co_loc = " + intCodLoc + " ";
                strSQL += "  AND a.co_tipdoc = " + intCodTipDoc + " ";
                strSQL += "  AND a.co_doc = " + intCodDoc + " ";

                System.out.println(
                        "Liquidación recibida desde autorización: "
                        + strSQL
                );

                rstCab = stmCab.executeQuery(strSQL);

                if (rstCab.next())
                {
                    /*
                     * cargarReg() carga cabecera, detalle,
                     * totales y datos históricos.
                     */
                    if (!cargarReg())
                    {
                        return false;
                    }

                    tabFrm.setEnabledAt(1, true);

                    if (blnAbrirReporte)
                    {
                        /*
                         * Botón Reporte.
                         */
                        tabFrm.setSelectedIndex(1);
                    }
                    else
                    {
                        /*
                         * Botón Liquidación.
                         */
                        tabFrm.setSelectedIndex(0);
                    }

                    objTooBar.setMenSis(
                            "Liquidación consultada desde autorizaciones."
                    );

                    blnRes = true;
                }
                else
                {
                    mostrarMsgInf(
                            "No se encontró la liquidación seleccionada."
                    );

                    cerrarConsultaCabecera();
                }
            }
        }
        catch (Exception e)
        {
            blnRes = false;

            cerrarConsultaCabecera();

            objUti.mostrarMsgErr_F1(
                    this,
                    e
            );
        }

        return blnRes;
    }
    
    private void seleccionarCausaLiquidacion(int intCodCau)
    {
        cmbCau.setSelectedIndex(-1);

        for (int i = 0; i < cmbCau.getItemCount(); i++)
        {
            CausaLiquidacion objCau = cmbCau.getItemAt(i);

            if (objCau.getCodigo() == intCodCau)
            {
                cmbCau.setSelectedIndex(i);
                break;
            }
        }
    }

    private class CausaLiquidacion
    {
        private int intCodCau;
        private String strNomCau;
        private String strTipCal;

        public CausaLiquidacion(int intCodCau, String strNomCau, String strTipCal)
        {
            this.intCodCau = intCodCau;
            this.strNomCau = strNomCau;
            this.strTipCal = strTipCal;
        }

        public int getCodigo()
        {
            return intCodCau;
        }

        public String getNombre()
        {
            return strNomCau;
        }

        public String getTipoCalculo()
        {
            return strTipCal;
        }

        @Override
        public String toString()
        {
            return strNomCau;
        }
    }
    
    /**
    * Carga las causas de liquidación activas en el combo.
    */
   private boolean cargarCausasLiquidacion()
   {
       boolean blnRes = true;
       java.sql.Connection conLoc = null;
       java.sql.Statement stmLoc = null;
       java.sql.ResultSet rstLoc = null;

       try
       {
           conLoc = java.sql.DriverManager.getConnection(
                   objZafParSis.getStringConexion(),
                   objZafParSis.getUsuarioBaseDatos(),
                   objZafParSis.getClaveBaseDatos()
           );

           if (conLoc != null)
           {
               stmLoc = conLoc.createStatement();

               String strSQL = "";
               strSQL += " SELECT co_cauliq, tx_nomcau, tx_tipcal";
               strSQL += " FROM tbm_causalliq";
               strSQL += " WHERE st_reg='A'";
               strSQL += " ORDER BY co_cauliq";

               rstLoc = stmLoc.executeQuery(strSQL);

               cmbCau.removeAllItems();

               while (rstLoc.next())
               {
                   cmbCau.addItem(
                           new CausaLiquidacion(
                                   rstLoc.getInt("co_cauliq"),
                                   rstLoc.getString("tx_nomcau"),
                                   rstLoc.getString("tx_tipcal")
                           )
                   );
               }

               // Inicia sin ninguna causa seleccionada.
               cmbCau.setSelectedIndex(-1);
           }
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
               if (rstLoc != null)
                   rstLoc.close();

               if (stmLoc != null)
                   stmLoc.close();

               if (conLoc != null)
                   conLoc.close();
           }
           catch (Exception e)
           {
               blnRes = false;
           }
       }

       return blnRes;
   }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton butCalcular;
    public javax.swing.JButton butConTra;
    private javax.swing.JComboBox<CausaLiquidacion> cmbCau;
    private javax.swing.JLabel lblCauRep;
    private javax.swing.JLabel lblCausal;
    public javax.swing.JLabel lblCausalRep;
    private javax.swing.JLabel lblCedTra;
    private javax.swing.JLabel lblCodTra;
    private javax.swing.JLabel lblCodTra1;
    public javax.swing.JLabel lblFecConRep;
    private javax.swing.JLabel lblFecFinCon;
    private javax.swing.JLabel lblFecFinCon1;
    private javax.swing.JLabel lblFecIniCon;
    private javax.swing.JLabel lblFecRep;
    private javax.swing.JLabel lblNomTra;
    private javax.swing.JLabel lblNomTra1;
    private javax.swing.JLabel lblNomTra2;
    public javax.swing.JLabel lblNomTraRep;
    private javax.swing.JLabel lblObs;
    private javax.swing.JLabel lblSueMen;
    private javax.swing.JLabel lblSueMen1;
    private javax.swing.JLabel lblSueMen2;
    private javax.swing.JLabel lblSueMen3;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTotEgr;
    private javax.swing.JLabel lblTotIng;
    private javax.swing.JLabel lblTotLiq;
    private javax.swing.JLabel lblTraRep;
    private javax.swing.JPanel panBarGen;
    private javax.swing.JPanel panCabRep;
    private javax.swing.JPanel panDatGen;
    private javax.swing.JPanel panDatLiq;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panObsDet;
    private javax.swing.JPanel panObsLbl;
    private javax.swing.JPanel panPieRep;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panTotRep;
    private javax.swing.JPanel panTra;
    private javax.swing.JScrollPane spnDetLiq;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDetLiq;
    public javax.swing.JTextArea txaObs;
    public javax.swing.JTextField txtCedTra;
    public javax.swing.JTextField txtCodDoc;
    public javax.swing.JTextField txtCodTra;
    public javax.swing.JTextField txtDecCua;
    public javax.swing.JTextField txtDecTer;
    public javax.swing.JTextField txtDiasTra;
    public javax.swing.JTextField txtFecFinCon;
    public javax.swing.JTextField txtFecIniCon;
    public javax.swing.JTextField txtFonRes;
    public javax.swing.JTextField txtNomTra;
    public javax.swing.JTextField txtNumDoc;
    public javax.swing.JTextField txtSueMen;
    public javax.swing.JTextField txtTotEgr;
    public javax.swing.JTextField txtTotIng;
    public javax.swing.JTextField txtTotLiq;
    // End of variables declaration//GEN-END:variables

    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objZafParSis);
        }
        
        public boolean anular()
        {
            boolean blnRes = false;
            java.sql.Connection conn = null;

            try
            {
                conn = java.sql.DriverManager.getConnection(
                        objZafParSis.getStringConexion(),
                        objZafParSis.getUsuarioBaseDatos(),
                        objZafParSis.getClaveBaseDatos()
                );

                if (conn != null)
                {
                    conn.setAutoCommit(false);

                    strAux = objTooBar.getEstadoRegistro();

                    if (strAux.equals("Anulado"))
                    {
                        mostrarMsgInf(
                            "El documento ya está ANULADO.\n"
                            + "No es posible anular un documento anulado."
                        );

                        blnRes = true;
                    }

                    if (!blnRes)
                    {
                        if (anularReg(conn))
                        {
                            conn.commit();

                            blnRes = true;

                            objTooBar.setEstadoRegistro("Anulado");

                            blnHayCam = false;
                        }
                        else
                        {
                            conn.rollback();
                        }
                    }
                    else
                    {
                        blnRes = false;
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
                    if (conn != null)
                    {
                        conn.close();
                    }
                }
                catch (Exception e)
                {
                }
            }

            return blnRes;
        }
        
        private boolean anularReg(java.sql.Connection conn)
        {
            boolean blnRes = false;
            java.sql.Statement stmLoc = null;
            String strSQL = "";
            int intCodTipDoc = INT_COD_TIP_DOC_LIQ_HAB;

            try
            {
                if (conn != null)
                {
                    stmLoc = conn.createStatement();

                    strSQL = "";
                    strSQL += "UPDATE tbm_cabliqhab ";
                    strSQL += "SET st_reg='I', ";
                    strSQL += "    fe_ultmod=" + objZafParSis.getFuncionFechaHoraBaseDatos() + ", ";
                    strSQL += "    co_usrmod=" + objZafParSis.getCodigoUsuario() + " ";
                    strSQL += "WHERE co_emp=" + objZafParSis.getCodigoEmpresa() + " ";
                    strSQL += "  AND co_loc=" + objZafParSis.getCodigoLocal() + " ";
                    strSQL += "  AND co_tipdoc=" + intCodTipDoc;
                    strSQL += "  AND co_doc=" + txtCodDoc.getText() + " ";
                    strSQL += "  AND st_reg='A'";

                    System.out.println("anularReg: " + strSQL);

                    int intFilAfe = stmLoc.executeUpdate(strSQL);

                    if (intFilAfe == 1)
                    {
                        blnRes = true;
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
                    {
                        stmLoc.close();
                    }
                }
                catch (Exception e)
                {
                }
            }

            return blnRes;
        }

        

        public void clickInicio()
        {
            try
            {
                if (rstCab != null
                        && !rstCab.isFirst())
                {
                    rstCab.first();
                    cargarReg();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(
                    ZafRecHum44.this,
                    e
                );
            }
        }

        public void clickAnterior()
        {
            try
            {
                if (rstCab != null
                        && !rstCab.isFirst())
                {
                    rstCab.previous();
                    cargarReg();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(
                    ZafRecHum44.this,
                    e
                );
            }
        }

        public void clickSiguiente()
        {
            try
            {
                if (rstCab != null
                        && !rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(
                    ZafRecHum44.this,
                    e
                );
            }
        }

        public void clickFin()
        {
            try
            {
                if (rstCab != null
                        && !rstCab.isLast())
                {
                    rstCab.last();
                    cargarReg();
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(
                    ZafRecHum44.this,
                    e
                );
            }
        }

        public void clickInsertar()
        {
            /*
             * Preparar una liquidación nueva.
             */
            limpiarFrm();

            intCodDocGuardado = 0;
            intNumDocGuardado = 0;

            tabFrm.setSelectedIndex(0);
            tabFrm.setEnabledAt(1, false);

            txtCedTra.requestFocus();           
            
            txtNomTra.setEditable(false);
            txtCodTra.setEditable(false);
            txtNumDoc.setEditable(false); 
            txtCodDoc.setEditable(false);
            txtFecIniCon.setEditable(false);
            txtFecFinCon.setEditable(false);
            txtSueMen.setEditable(false);
            
            mostrarSiguienteNumeracion();
            
            datFecAux = objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            java.util.Date dateObj = datFecAux;
            java.util.Calendar calObj = java.util.Calendar.getInstance();
            txtFecDoc.setText(calObj.get(java.util.Calendar.DAY_OF_MONTH),
                        calObj.get(java.util.Calendar.MONTH) + 1,
                        calObj.get(java.util.Calendar.YEAR));
            
            objTooBar.setMenSis("Ingrese los datos de la nueva liquidación.");
        }

        public void clickConsultar()
        {
            limpiarFrm();

            tabFrm.setSelectedIndex(0);
            tabFrm.setEnabledAt(1, false);

            txtCodTra.requestFocus();

            objTooBar.setMenSis(
                "Ingrese el código del trabajador o consulte sin filtros."
            );
        }

        public void clickModificar()
        {
        }

        public void clickEliminar()
        {
        }

        public void clickAnular()
        {
        }

        public void clickImprimir()
        {
        }

        public void clickVisPreliminar()
        {
        }

        public void clickAceptar()
        {
        }

        public void clickCancelar()
        {
            limpiarFrm();
        }

        public boolean insertar()
        {
            boolean blnRes = false;

            try
            {
                /*
                 * Primero debe existir una liquidación calculada.
                 */
                if (objTblModLiq == null
                        || objTblModLiq.getRowCount() == 0)
                {
                    mostrarMsgInf(
                        "Primero debe calcular la liquidación."
                    );

                    return false;
                }

                /*
                 * Guarda cabecera y detalle en una sola transacción.
                 */
                blnRes = guardarLiquidacion();

                if (blnRes)
                {
                    mostrarMsgInf(
                        "La liquidación fue guardada correctamente.\n"
                        + "Número de documento: "
                        + intNumDocGuardado
                    );

                    tabFrm.setEnabledAt(1, true);
                    tabFrm.setSelectedIndex(1);
                }
            }
            catch (Exception e)
            {
                blnRes = false;
                objUti.mostrarMsgErr_F1(
                    ZafRecHum44.this,
                    e
                );
            }

            return blnRes;
        }

        public boolean consultar()
        {
            return consultarReg();
        }

        public boolean modificar()
        {
            return true;
        }

        public boolean eliminar()
        {
            return true;
        }        

        public boolean imprimir()
        {
            return imprimirLiquidacion();
        }

        public boolean vistaPreliminar()
        {
            return true;
        }

        public boolean aceptar()
        {
            return true;
        }

        public boolean cancelar()
        {
            return true;
        }

        public boolean beforeInsertar()
        {
            return true;
        }

        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            return true;
        }

        public boolean beforeEliminar()
        {
            return true;
        }

        public boolean beforeAnular()
        {
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
        {
            return true;
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }

        public boolean afterInsertar()
        {
            /*
             * Después de guardar queda en modo consulta.
             */
            this.setEstado('w');

            objTooBar.setEstado('w');

            objTooBar.setMenSis(
                "Liquidación guardada correctamente."
            );

            return true;
        }

        public boolean afterConsultar()
        {
            /*
             * Se ejecuta después de que la barra termina
             * de configurar el formulario en modo consulta.
             */
            javax.swing.SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        
                    }
                }
            );

            return true;
        }

        public boolean afterModificar()
        {
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }

        public boolean afterCancelar()
        {
            return true;
        }
    }


}
