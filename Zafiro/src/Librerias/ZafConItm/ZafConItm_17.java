/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafConItm;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafEvt.ZafAsiDiaEvent;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author José Mario 
 */
public class ZafConItm_17 extends javax.swing.JDialog 
{
   //Constantes.
     
    
    //Jtable: TblDat
    private static final int INT_TBL_DAT_LIN=0;                          //Línea
    private static final int INT_TBL_DAT_CODEMP = 1;                     //Código de Seguimiento.
    private static final int INT_TBL_DAT_CODLOC=2;                 //Código de Empresa.
    private static final int INT_TBL_DAT_CODTIPDOC=3;                 //Código de Local.
    private static final int INT_TBL_DAT_DESCORTIPDOC=4;
    private static final int INT_TBL_DAT_DESLARTIPDOC=5;
    private static final int INT_TBL_DAT_CODDOC=6;              //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDIA=7;                 //Código de Documento.
    private static final int INT_TBL_DAT_CODCAJ=8;           //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESCORCAJA=9;
    private static final int INT_TBL_DAT_CODUSR=10;           //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NOMUSR=11;                 //Número de Documento.
    private static final int INT_TBL_DAT_BUTDIARIO=12;                 //Botón de Solicitud de Transferencia.
     

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblMod objTblMod, objTblModDia;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChkBod;
    private ZafTblCelEdiChk objTblCelEdiChkBod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenButConSolTraInv;  
    private ZafTblCelRenLbl objTblCelRenLblColSolTraInv, objTblCelRenLblColCenSol, objTblCelRenLblColSolTraInvNum;
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
  
    private String strSql;
    private Vector vecDat, vecCab, vecReg, vecCta;
    private final Color colFonCol =new Color(255,0,0);
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodTblBod=true;
    private boolean blnBloqueo = false;  
    private int intSqlBod=1;
    
    private String strDesCorTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private ZafVenCon vcoTipDoc;    
    private Connection con;
    private Statement stm;
    private ResultSet rst; 
    private String strSQL, strAux;
    private int intIndiceAsiDia=0;        
    private double dblMonIniDeb, dblMonIniHab;      //Monto inicial del debe, monto del haber.
    private double dblMonDeb, dblMonHab;            //Monto del debe, monto del haber.
    private BigDecimal bdeMonDeb, bdeMonHab;        //Monto del debe, monto del haber en BigDecimal
    private Vector vecAux;
    private ZafTblEdi objTblEdi, objTblEdiDia;                            //Editor: Editor del JTable.
    private String strNumDia_Glo;
    private ZafTblCelRenBut objTblCelRenBut;                //Render: Presentar JButton en JTable.
    
    private int intCodEmpDia, intCodLocDia, intCodTipDocDia, intCodDocDia;
    
    
    /**
     * Creates new form ZafCom79_01: Ventana Emergente
     * Se utiliza para el bloqueo del sistema por confirmaciones de ingreso pendientes.
     */
    public ZafConItm_17(java.awt.Frame parent, boolean modal, ZafParSis obj) 
    {
        super(parent, modal);
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
             //Inicializar objetos.
            objUti=new ZafUtil();
            initComponents();
            configurarFrm();  
            consultar();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panReg = new javax.swing.JPanel();
        panCabDia = new javax.swing.JPanel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblNumDia = new javax.swing.JLabel();
        txtNumDia = new javax.swing.JTextField();
        lblFecDoc1 = new javax.swing.JLabel();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panDetDia = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDia = new javax.swing.JTable();
        panBarDia = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        panDIf = new javax.swing.JPanel();
        lblDif = new javax.swing.JLabel();
        txtDif = new javax.swing.JTextField();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 445));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Listado de ingresos físicos de mercadería pendientes de confirmar...");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);
        tabFrm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabFrmStateChanged(evt);
            }
        });

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
        ));
        tblDat.setAutoscrolls(false);
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panReg.setLayout(new java.awt.BorderLayout());

        panCabDia.setPreferredSize(new java.awt.Dimension(0, 100));
        panCabDia.setLayout(null);

        txtCodTipDoc.setEditable(false);
        txtCodTipDoc.setEnabled(false);
        panCabDia.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 20, 20);

        txtDesCorTipDoc.setEditable(false);
        txtDesCorTipDoc.setEnabled(false);
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
        panCabDia.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(110, 4, 80, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabDia.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabDia.add(lblCodDoc);
        lblCodDoc.setBounds(0, 24, 120, 20);

        txtCodDoc.setEditable(false);
        txtCodDoc.setEnabled(false);
        panCabDia.add(txtCodDoc);
        txtCodDoc.setBounds(110, 24, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.setEnabled(false);
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabDia.add(butTipDoc);
        butTipDoc.setBounds(430, 4, 20, 20);

        txtDesLarTipDoc.setEditable(false);
        txtDesLarTipDoc.setEnabled(false);
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
        panCabDia.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 4, 240, 20);

        lblNumDia.setText("Número de diario:");
        lblNumDia.setToolTipText("Número de diario");
        panCabDia.add(lblNumDia);
        lblNumDia.setBounds(450, 24, 100, 20);

        txtNumDia.setEditable(false);
        txtNumDia.setEnabled(false);
        panCabDia.add(txtNumDia);
        txtNumDia.setBounds(578, 24, 100, 20);

        lblFecDoc1.setText("Fecha del documento:");
        lblFecDoc1.setToolTipText("Fecha del documento");
        panCabDia.add(lblFecDoc1);
        lblFecDoc1.setBounds(450, 4, 130, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Número de diario");
        panCabDia.add(lblValDoc);
        lblValDoc.setBounds(450, 50, 130, 20);

        txtValDoc.setEditable(false);
        txtValDoc.setEnabled(false);
        txtValDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValDocFocusLost(evt);
            }
        });
        txtValDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValDocKeyPressed(evt);
            }
        });
        panCabDia.add(txtValDoc);
        txtValDoc.setBounds(579, 50, 100, 20);

        panReg.add(panCabDia, java.awt.BorderLayout.NORTH);

        panDetDia.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        tblDia.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDia);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panDetDia.add(jPanel1, java.awt.BorderLayout.CENTER);

        panBarDia.setPreferredSize(new java.awt.Dimension(679, 30));
        panBarDia.setLayout(null);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panBarDia.add(btnGuardar);
        btnGuardar.setBounds(563, 5, 110, 23);

        panDetDia.add(panBarDia, java.awt.BorderLayout.SOUTH);

        panReg.add(panDetDia, java.awt.BorderLayout.CENTER);

        panDIf.setPreferredSize(new java.awt.Dimension(679, 30));
        panDIf.setLayout(null);

        lblDif.setText("Diferencia:");
        panDIf.add(lblDif);
        lblDif.setBounds(500, 3, 52, 14);

        txtDif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDif.setText("0.00");
        txtDif.setEnabled(false);
        txtDif.setPreferredSize(new java.awt.Dimension(120, 21));
        panDIf.add(txtDif);
        txtDif.setBounds(560, 3, 120, 21);

        panReg.add(panDIf, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Registro", panReg);

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

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(panFrm, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(panFrm, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleParent(this);

        setSize(new java.awt.Dimension(700, 445));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
       consultar();
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        if(blnBloqueo)
        {
            butCer.setEnabled(false);
        }
        else
        {
            dispose();
        }
            
    }//GEN-LAST:event_butCerActionPerformed

    private void tabFrmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabFrmStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tabFrmStateChanged

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
        txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtValDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusLost
 
        
    }//GEN-LAST:event_txtValDocFocusLost

    private void txtValDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValDocKeyPressed
        
    }//GEN-LAST:event_txtValDocKeyPressed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(actualizarReg()){
            System.out.println("OK GUARDA");
            mostrarMsgInf("Modificación guardada con exito...");
            butCon.doClick();
            tabFrm.setSelectedIndex(0);
        }
        else{
            mostrarMsgInf("Error al guardar");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecDoc1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumDia;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarDia;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabDia;
    private javax.swing.JPanel panDIf;
    private javax.swing.JPanel panDetDia;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panReg;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDia;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDif;
    private javax.swing.JTextField txtNumDia;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objTblCelRenLblColSolTraInv = new ZafTblCelRenLbl();
            objTblCelRenLblColSolTraInvNum = new ZafTblCelRenLbl();
            
            this.setTitle("Listado de Cierres de caja...");
            lblTit.setText("Listado de Cierres de caja...");
            
            
            configurarVenConTipDoc();
            configurarVenConCta();
            
            //Configuraciones Varias.
            configurarTblDatCaja();
            configurarTblDatDia();
            
            
            
            //Se oculta panel de Confirmación de Registros.
            //tabFrm.remove(panReg);
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private boolean configurarTblDatCaja()
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras        
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC,"Tipo de Documento");
            vecCab.add(INT_TBL_DAT_CODDOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDIA,"Núm.Dia.");
            vecCab.add(INT_TBL_DAT_CODCAJ,"Cód.Caj.");
            vecCab.add(INT_TBL_DAT_DESCORCAJA,"Caja");
            vecCab.add(INT_TBL_DAT_CODUSR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_NOMUSR,"Usuario");
            vecCab.add(INT_TBL_DAT_BUTDIARIO,"...");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod); 
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAdaTblSolTraInv objMouMotAda2=new ZafMouMotAdaTblSolTraInv();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda2);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel(); 
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Establece tamaño.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUMDIA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CODCAJ).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DESCORCAJA).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_CODUSR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOMUSR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTDIARIO).setPreferredWidth(20);
             
            
            //Columnas Ocultas
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODCAJ, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODUSR, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTDIARIO);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Establecer Botón.
            objTblCelRenButConSolTraInv=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTDIARIO).setCellRenderer(objTblCelRenButConSolTraInv);
           objTblCelRenButConSolTraInv.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButConSolTraInv.getColumnRender())
                    {
                        case INT_TBL_DAT_BUTDIARIO:
                            if(objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODLOC).toString().equals(""))
                               objTblCelRenButConSolTraInv.setText("");
                            else
                               objTblCelRenButConSolTraInv.setText("...");
                        break;
                        
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTDIARIO).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTDIARIO:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTDIARIO:
                                intCodEmpDia = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMP).toString()); 
                                intCodLocDia = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOC).toString());
                                intCodTipDocDia = Integer.parseInt( tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString()) ;
                                intCodDocDia = Integer.parseInt(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOC).toString());
                                
                                cargarRegBloqueado(intCodEmpDia , intCodLocDia, intCodTipDocDia, intCodDocDia );
                                break;
                           

                        }
                    }
                }
            });           
            
            //Establecer Tabla Editable.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);                  
             
            
            tcmAux=null;
            blnRes = true;

        } 
        catch (Exception e) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaTblSolTraInv extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            
            switch (intCol) 
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODEMP:
                    strMsg="Código Empresa";
                    break;  
                case INT_TBL_DAT_CODLOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg="Código del tipo de documentos";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg="Código del documentos";
                    break;
                case INT_TBL_DAT_NUMDIA:
                    strMsg="Número de diario";
                    break;
                case INT_TBL_DAT_CODCAJ:
                    strMsg="Código de caja";
                    break;
                case INT_TBL_DAT_DESCORCAJA:
                    strMsg="Caja";
                    break;
                case INT_TBL_DAT_CODUSR:
                    strMsg="Código del usuario";
                    break;
                case INT_TBL_DAT_NOMUSR:
                    strMsg="NNombre del usuario";
                    break;
                case INT_TBL_DAT_BUTDIARIO:
                    strMsg="Botón Consulta Cierres de caja pendiente de contabilidad";
                    break;
             
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
        @Override
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
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
            
            if (blnBloqueo) 
            {
                butCer.setEnabled(false);
            } 
            else 
            {
                butCer.setEnabled(true);                
            }
            
        }
    }
    
     
   
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try
        {
            blnBloqueo=false;
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();      
                strSql="";
                strSql+=" SELECT a4.co_emp, a4.co_loc,a4.co_caj, a4.tx_desLar , a6.co_usr,  a7.tx_usr, a7.tx_nom, a5.tx_desCor as tx_desCorTipDoc, \n";
                strSql+="        a5.tx_desLar as tx_desLarTipDoc, a1.co_emp as co_empDia, a1.co_loc as co_locDia, ";
                strSql+="        a1.co_tipDoc as co_tipDocDia, a1.co_dia, a1.tx_numDia    \n";
                strSql+=" FROM tbm_cabDia as a1   \n";
                strSql+=" INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)   \n";
                strSql+=" INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)   \n";
                strSql+=" INNER JOIN tbm_tipDocCaj as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locTipDoc AND a1.co_tipDoc=a3.co_tipDoc)   \n";
                strSql+=" INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj AND a1.fe_dia >= a4.fe_iniCieCajObl)   \n";
                strSql+=" INNER JOIN tbm_usrCaj as a6 ON (a4.co_emp=a6.co_emp AND a4.co_loc=a6.co_loc AND a4.co_caj=a6.co_caj)   \n";
                strSql+=" INNER JOIN tbm_usr as a7 ON (a6.co_usr=a7.co_usr)   \n";
                strSql+=" WHERE  a1.st_reg IN('O') and a6.co_usr="+objParSis.getCodigoUsuario()+" AND a2.co_cta IS NULL    \n";
                strSql+=" GROUP BY a4.co_emp, a4.co_loc,a4.co_caj, a4.tx_desLar , a6.co_usr,  a7.tx_usr, a7.tx_nom, a5.tx_desCor,a5.tx_desLar, \n";
                strSql+="          a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia   \n";
                strSql+=" \n";
                System.out.println("ZafConItm_17.cargarDetReg: " + strSql);
                rstLoc=stmLoc.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next()) {
                    blnBloqueo=true;
                    if (blnCon) {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN," ");
                        vecReg.add(INT_TBL_DAT_CODEMP, rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rstLoc.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC, rstLoc.getString("co_tipDocDia")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC, rstLoc.getString("tx_desCorTipDoc")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC, rstLoc.getString("tx_desLarTipDoc")  );
                        vecReg.add(INT_TBL_DAT_CODDOC, rstLoc.getString("co_dia")  );
                        vecReg.add(INT_TBL_DAT_NUMDIA, rstLoc.getString("tx_numDia")  );
                        vecReg.add(INT_TBL_DAT_CODCAJ, rstLoc.getString("co_caj")  );
                        vecReg.add(INT_TBL_DAT_DESCORCAJA, rstLoc.getString("tx_desLar")  );
                        vecReg.add(INT_TBL_DAT_CODUSR, rstLoc.getString("co_usr")  );
                        vecReg.add(INT_TBL_DAT_NOMUSR, rstLoc.getString("tx_nom")  );
                        vecReg.add(INT_TBL_DAT_BUTDIARIO, "" );  
                        vecDat.add(vecReg);
                        
                        
                    }
                    else 
                    {
                        break;
                    }
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
     
    
    private void consultar(){
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            blnBloqueo = false;
            if (objThrGUI == null){
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else{
            blnCon = false;
        }
        
    }
     
    
    private ZafAsiDia objAsiDia;
    private boolean blnHayCam;                              //Determina si hay cambios en el formulario.
    private ZafDatePicker dtpFecDoc;
    private String strFecDocIni, strEstImpDoc;
    
    
    
    private boolean cargarRegBloqueado(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=false;
        try{
            
            if(cargarCabReg(CodEmp, CodLoc, CodTipDoc, CodDoc)){
                if(cargarDetRegBlo(CodEmp, CodLoc, CodTipDoc, CodDoc)){
                    blnRes=true;
                    tabFrm.setSelectedIndex(1);
                     
                }
            }
            
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_dia, a1.tx_numDia, a1.fe_dia, a1.st_reg, a1.st_imp, a1.fe_ven";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + CodEmp;
                strSQL+=" AND a1.co_loc=" + CodLoc;
                strSQL+=" AND a1.co_tipDoc=" + CodTipDoc;
                strSQL+=" AND a1.co_dia=" + CodDoc;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_dia");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_numDia");
                    txtNumDia.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_dia"),"dd/MM/yyyy"));

                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                           
                }
                else{
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            System.out.println("ERROR :  "  + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println("ERROR :  "  + e.toString());
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
     
    
    /**
     * Esta función carga el detalle del registro. 
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegBlo(int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia){
        BigDecimal bdeDeb, bdeHab, bdeTotDeb, bdeTotHab;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_cta, '' as tx_codCta, '' as tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a1.st_conBan ";
                strSQL+=" FROM tbm_detDia AS a1 ";
                strSQL+=" INNER JOIN tbm_cabDia AS a2 on a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQL+=" AND a1.co_dia=" + intCodDia;
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                System.out.println("cargarDetRegBlo " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                bdeTotDeb=new BigDecimal("0");
                bdeTotHab=new BigDecimal("0");
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_DIA_COD_CTA,rst.getString("co_cta")==null?"":rst.getString("co_cta"));
                    vecReg.add(INT_TBL_DAT_DIA_NUM_CTA,rst.getString("tx_codCta")==null?"":rst.getString("tx_codCta"));
                    vecReg.add(INT_TBL_DAT_DIA_BUT_CTA,"");
                    vecReg.add(INT_TBL_DAT_DIA_NOM_CTA,rst.getString("tx_desLar")==null?"":rst.getString("tx_desLar"));
                    bdeDeb=rst.getBigDecimal("nd_monDeb");
                    bdeHab=rst.getBigDecimal("nd_monHab");
                    bdeTotDeb=bdeTotDeb.add(bdeDeb);
                    bdeTotHab=bdeTotHab.add(bdeHab);
                    vecReg.add(INT_TBL_DAT_DIA_DEB,"" + bdeDeb);
                    vecReg.add(INT_TBL_DAT_DIA_HAB,"" + bdeHab);
                    vecReg.add(INT_TBL_DAT_DIA_REF,rst.getString("tx_ref"));
                    vecReg.add(INT_TBL_DAT_DIA_EST_CON,rst.getObject("st_conBan")==null?"":rst.getString("st_conBan"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModDia.setData(vecDat);
                tblDia.setModel(objTblModDia);
                //Almacenar el monto del debe y haber en variables globales.
                dblMonIniDeb=Double.parseDouble("" + bdeTotDeb);
                dblMonIniHab=Double.parseDouble("" + bdeTotHab);
                dblMonDeb=Double.parseDouble("" + bdeTotDeb);
                dblMonHab=Double.parseDouble("" + bdeTotHab);
                
                bdeMonDeb=bdeTotDeb;
                bdeMonHab=bdeTotHab;
                
//                txtDif.setText("" + objUti.formatearNumero((dblTotDeb-dblTotHab),objParSis.getFormatoNumero(),true));
                txtDif.setText("" + objUti.redondearBigDecimal((bdeTotDeb.subtract(bdeTotHab)), objParSis.getDecimalesBaseDatos()));
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
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mós espacio.
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
                    break;
                case INT_TBL_DAT_DIA_COD_CTA:
                    strMsg="Código de la cuenta";
                    break;
                case INT_TBL_DAT_DIA_NUM_CTA:
                    strMsg="Nómero de la cuenta";
                    break;
                case INT_TBL_DAT_DIA_BUT_CTA:
                    strMsg="";
                    break;
                case INT_TBL_DAT_DIA_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_DIA_DEB:
                    strMsg="Debe";
                    break;
                case INT_TBL_DAT_DIA_HAB:
                    strMsg="Haber";
                    break;
                case INT_TBL_DAT_DIA_REF:
                    strMsg="Referencia";
                    break;
                case INT_TBL_DAT_DIA_EST_CON:
                    strMsg="Estado de Conciliación Bancaria";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private ZafTblPopMnu objTblPopMnu;                      //PopupMenu: Establecer PeopuMenó en JTable.
    private ZafTblPopMnu objTblPopMnuDia;                      //PopupMenu: Establecer PeopuMenó en JTable.
    private ZafMouMotAda objMouMotAda;                      //ToolTipText en TableHeader.
     
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCtaDia;       //Editor: JTextField de consulta en celda.
    
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_DIA_LIN=0;             //Lónea
    private static final int INT_TBL_DAT_DIA_COD_CTA=1;         //Código de la cuenta (Sistema).
    private static final int INT_TBL_DAT_DIA_NUM_CTA=2;         //Nómero de la cuenta (Preimpreso).
    private static final int INT_TBL_DAT_DIA_BUT_CTA=3;         //Botón de consulta.
    private static final int INT_TBL_DAT_DIA_NOM_CTA=4;         //Nombre de la cuenta.
    private static final int INT_TBL_DAT_DIA_DEB=5;             //Debe.
    private static final int INT_TBL_DAT_DIA_HAB=6;             //Haber.
    private static final int INT_TBL_DAT_DIA_REF=7;             //Referencia.
    private static final int INT_TBL_DAT_DIA_EST_CON=8;         //Estado de conciliación bancaria de la cuenta
    private ZafVenCon vcoCta;                               //Ventana de consulta "Item".
    private boolean blnCanOpe;                      //Determina si se debe cancelar la operación.
     
     
    private ZafTblCelEdiButVco objTblCelEdiButVcoCtaDia;       //Editor: JButton en celda.
    private java.util.Date datFecAux;
    
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatDia(){
        boolean blnRes=true;
        try{
            //Configurar ZafDatePicker:
            dtpFecDoc=new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabDia.add(dtpFecDoc);
            dtpFecDoc.setBounds(578, 4, 100, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 

            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_DIA_LIN,"");
            vecCab.add(INT_TBL_DAT_DIA_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_DIA_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_DIA_BUT_CTA,"");
            vecCab.add(INT_TBL_DAT_DIA_NOM_CTA,"Nombre de la cuenta");
            vecCab.add(INT_TBL_DAT_DIA_DEB,"Debe");
            vecCab.add(INT_TBL_DAT_DIA_HAB,"Haber");
            vecCab.add(INT_TBL_DAT_DIA_REF,"Referencia");
            vecCab.add(INT_TBL_DAT_DIA_EST_CON,"Estado de Conciliación");


            vecCta=new Vector();    //Almacena las cuentas.
            objTblModDia=new ZafTblMod();
            objTblModDia.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModDia.setColumnDataType(INT_TBL_DAT_DIA_DEB, objTblModDia.INT_COL_DBL, new Integer(0), null);
            objTblModDia.setColumnDataType(INT_TBL_DAT_DIA_HAB, objTblModDia.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_DIA_COD_CTA);
            objTblModDia.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModDia.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDia.setModel(objTblModDia);
            //Configurar JTable: Establecer tipo de selección.
            tblDia.setRowSelectionAllowed(true);
            tblDia.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menó de contexto.
            objTblPopMnuDia=new ZafTblPopMnu(tblDia);
            
           
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDia.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDia.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DIA_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DIA_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_DIA_BUT_CTA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DIA_NOM_CTA).setPreferredWidth(190);
            tcmAux.getColumn(INT_TBL_DAT_DIA_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_HAB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_REF).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setPreferredWidth(80);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_DIA_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDia.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_COD_CTA).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DIA_EST_CON).setResizable(false);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DIA_NUM_CTA);
            vecAux.add("" + INT_TBL_DAT_DIA_BUT_CTA);
            vecAux.add("" + INT_TBL_DAT_DIA_DEB);
            vecAux.add("" + INT_TBL_DAT_DIA_HAB);
            vecAux.add("" + INT_TBL_DAT_DIA_REF);
            objTblModDia.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiDia=new ZafTblEdi(tblDia);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDia);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_DIA_NUM_CTA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_DIA_NOM_CTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_REF).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DIA_DEB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_HAB).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_DIA_BUT_CTA).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_DIA_COD_CTA;
            intColTbl[1]=INT_TBL_DAT_DIA_NUM_CTA;
            intColTbl[2]=INT_TBL_DAT_DIA_NOM_CTA;
            
            
            
            objTblCelEdiButVcoCtaDia=new ZafTblCelEdiButVco(tblDia, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_BUT_CTA).setCellEditor(objTblCelEdiButVcoCtaDia);
            objTblCelEdiButVcoCtaDia.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="", strMe="";//estado de conciliacion bancaria de la cuenta
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDia.getValueAt(tblDia.getSelectedRow(), INT_TBL_DAT_DIA_EST_CON)==null?"":tblDia.getValueAt(tblDia.getSelectedRow(), INT_TBL_DAT_DIA_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>No se puede presentar la ventana de consulta de una cuenta ya conciliada.</HTML>");
//                        fireAsiDiaListener(new ZafAsiDiaEvent(this), INT_BEF_EDI_CEL);
                        objTblCelEdiButVcoCtaDia.setCancelarEdicion(true);
                    }
                    else if(strEstCncDia.equals("B")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
                        objTblCelEdiButVcoCtaDia.setCancelarEdicion(true);
                        vcoCta.setVisible(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Generar evento "afterEditarCelda()".
                    if(strEstCncDia.equals("B")){
                        objTblCelEdiButVcoCtaDia.setCancelarEdicion(true);
                    }
                }
 
            });
            
            
 
 
            objTblCelEdiTxtVcoCtaDia=new ZafTblCelEdiTxtVco(tblDia, vcoCta, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DIA_NUM_CTA).setCellEditor(objTblCelEdiTxtVcoCtaDia);
            objTblCelEdiTxtVcoCtaDia.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strEstCncDia="", strMe="";//estado de conciliacion bancaria de la cuenta

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strEstCncDia=tblDia.getValueAt(tblDia.getSelectedRow(), INT_TBL_DAT_DIA_EST_CON)==null?"":tblDia.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_DIA_EST_CON).toString();
                    if(strEstCncDia.equals("S")){
                        mostrarMsgInf("<HTML>La cuenta ya fue conciliada.<BR>Desconcilie la cuenta en el documento a modificar y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoCtaDia.setCancelarEdicion(true);
                    }
                    else if(strEstCncDia.equals("B")){
                        mostrarMsgInf("<HTML>No se puede cambiar el valor de la cuenta<BR>Este valor proviene de la transferencia ingresada.</HTML>");
                        objTblCelEdiTxtVcoCtaDia.setCancelarEdicion(true);
                    }
                    else{
                        //Permitir de manera predeterminada la operación.
                        blnCanOpe=false;
                         
                        //Permitir/Cancelar la edición de acuerdo a "cancelarOperacion".
                        if (blnCanOpe)
                        {
                            objTblCelEdiTxtVcoCtaDia.setCancelarEdicion(true);
                        }
                    }
                }
                 
              });
            intColVen=null;
            intColTbl=null;
            
            
            
            //Establecer parómetros predeterminados para la clase.
            setEditable(true);
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
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+="SELECT DISTINCT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            else
            {
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar \n";
                strSQL+=" FROM tbm_placta as a1 \n";
                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta) \n";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)  \n";
                strSQL+=" INNER JOIN tbm_tipDocCaj as a3 ON (a5.co_emp=a3.co_emp AND a5.co_loc=a3.co_locTipDoc AND a5.co_tipDoc=a3.co_tipDoc)  \n";
                strSQL+=" INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj) \n";
                strSQL+=" INNER JOIN tbm_usrCaj as a6 ON (a4.co_emp=a6.co_emp AND a4.co_loc=a6.co_loc AND a4.co_caj=a6.co_caj) \n";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a6.co_usr=" + objParSis.getCodigoUsuario() +"";
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" AND a1.st_reg='A' \n";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private void setPuntosCta(){
        strAux=objTblCelEdiTxtVcoCtaDia.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
//            if(  (strCodCtaOri.length() % 2) != 0 ){
                chrCtaOri=strCodCtaOri.charAt(1);
                if(chrCtaOri!='.'){
                    for (int i=0; i < strCodCtaOri.length(); i++){
                        if(i==0){
                            strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            strCodCtaDes=strCodCtaDes+".";
                        }
                        else{
                            if(  (strCodCtaOri.length() % 2) == 0 ){
                                if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                            else{
                                if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }            
                                    
                        }
                    }
                    objTblCelEdiTxtVcoCtaDia.setText(strCodCtaDes);
                }
        }
    }
    
    
    /**
     * Esta función establece si el diario debe ser editable o no.
     * @param editable Puede tomar los siguientes valores:
     * <BR>true: Diario editable.
     * <BR>false: Diario no editable.
     */
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblModDia.setModoOperacion(objTblModDia.INT_TBL_INS);
        }
        else
        {
            objTblModDia.setModoOperacion(objTblModDia.INT_TBL_NO_EDI);
        }
    }
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL ="";
                strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 INNER JOIN tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e) {
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux=vcoTipDoc.getValueAt(4);
                        txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                         
                        strAux=vcoTipDoc.getValueAt(4);
                        txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
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
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
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
                        
                        strAux=vcoTipDoc.getValueAt(4);
                        txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                         
                        txtNumDia.selectAll();
                        txtNumDia.requestFocus();
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
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDia.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            txtNumDia.selectAll();
                            txtNumDia.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            dtpFecDoc.setText("");
            
            txtCodDoc.setText("");
            txtNumDia.setText("");
            objAsiDia.inicializar();           
            
            txtValDoc.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    
    
    private int getTipModFecUsr(){
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intTipModFec=0;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL ="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                        intTipModFec=rstLoc.getInt("ne_tipresmodfecdoc");
                    }
                    rstLoc.close();
                    rstLoc=null;                    
                    stmLoc.close();
                    stmLoc=null;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
    }
     
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        boolean blnExiNumDiaRep;
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if (objUti.isNumero(txtNumDia.getText()) == false)
        {  mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de diario</FONT> debe ser numérico.<BR>Verifique y vuelva a intentarlo.</HTML>");
           tabFrm.setSelectedIndex(0);
           txtNumDia.requestFocus();
           return false;
        }
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el asiento de diario y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=getTipModFecUsr();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                        String strMsj="";
                        strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                        strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                        strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                        strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                        strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                        strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                        strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                        switch (mostrarMsgCon(strMsj)){
                            case 0: //YES_OPTION
                                return true;
                            case 1: //NO_OPTION
                                return false;
                            case 2: //CANCEL_OPTION
                                return false;
                        }
                        return true;
                    }
                    break;
                case 1://no puede cambiarla para nada
                    
                    strFecDocTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }  
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                     
                    strFecDocTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }  
                    
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                     
                    strFecDocTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }  
                     
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        //Validar que el "Cádigo alterno" no se repita.
        if (!txtNumDia.getText().equals("")){ 
            strNumDia_Glo = ""; 
            blnExiNumDiaRep = validaExiNumAsiDia(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtNumDia.getText());

            if (blnExiNumDiaRep == true)
            {  strAux = "<HTML>El número de diario digitado por el usuario <FONT COLOR=\"blue\">(" + txtNumDia.getText() + ")</FONT> ya existe.<BR>";
               strAux += "Por tanto, se procedió a asignar otro número disponible <FONT COLOR=\"blue\">(" + strNumDia_Glo + ")</FONT>.</HTML>";
               mostrarMsgInf(strAux);
               txtNumDia.setText(strNumDia_Glo);
            }
             
        }
        
        //Validar que el "Diario está cuadrado".
        if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que coincida el valor inicial del asiento con el valor actual.
        if (    (!txtCodTipDoc.getText().equals("30")  &&  !txtCodTipDoc.getText().equals("86")  &&  !txtCodTipDoc.getText().equals("87")   &&  !txtCodTipDoc.getText().equals("88")  &&  !txtCodTipDoc.getText().equals("89")  
                &&  !txtCodTipDoc.getText().equals("113")  &&  !txtCodTipDoc.getText().equals("92")   &&  (!txtCodTipDoc.getText().equals("184"))   &&  (!txtCodTipDoc.getText().equals("288")) &&  (!txtCodTipDoc.getText().equals("194"))   )
                && objUti.redondear(objAsiDia.getMontoInicialDebe(), objParSis.getDecimalesMostrar()) != objUti.redondear(objAsiDia.getMontoDebe(), objParSis.getDecimalesMostrar())  )
        {
            mostrarMsgInf("<HTML>El valor inicial del asiento de diario era <FONT COLOR=\"blue\">" + objAsiDia.getMontoInicialDebe() + "</FONT> y actualmente es de <FONT COLOR=\"blue\">" + objAsiDia.getMontoDebe() + "</FONT>.<BR>El valor actual debe coincidir con el valor inicial del asiento de diario.<BR>Cuadre el valor actual con el valor inicial y vuelva a intentarlo.</HTML>");
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param strNumDia
     * @return 
     */
    
    private boolean validaExiNumAsiDia(int intCodEmp, int intCodLoc, int intCodTipDoc, String strNumDia)
    {
        boolean blnRes = false, blnConBuc;
        int intAux;
        String strNumDiaAux;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try
        {
            rstLoc = null;
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (conLoc != null)
            {   stmLoc = conLoc.createStatement();
                blnConBuc = true; //Variable booleana que indica si debe o no continuar en el bucle.
                strNumDiaAux = strNumDia;

                while (blnConBuc)
                {  strSQL =" SELECT co_emp, co_loc, co_tipdoc, co_dia, tx_numdia ";
                   strSQL+=" FROM tbm_cabdia ";
                   strSQL+=" WHERE co_emp = " + intCodEmp;
                   strSQL+=" and co_loc = " + intCodLoc;
                   strSQL+=" and co_tipdoc = " + intCodTipDoc;
                   strSQL+=" and tx_numdia = '" + strNumDiaAux + "'";
                    strSQL += " and co_dia <> " + txtCodDoc.getText();
                   
                   
                   rstLoc = stmLoc.executeQuery(strSQL);
                   
                   if (rstLoc.next())
                   {  //Significa que el numero de Diario si existe. Por tanto, se va a obtener un nuevo numero secuencial.
                      blnRes = true;
                      intAux = Integer.parseInt(strNumDiaAux) + 1;
                      strNumDiaAux = Integer.toString(intAux);
                   }
                   else
                   {  //El numero de Diario no existe.
                      blnConBuc = false; //Se debe salir del bucle
                      strNumDia_Glo = strNumDiaAux;
                   }
                }
                
                rstLoc.close();
                rstLoc = null;
                stmLoc.close();
                stmLoc = null;
                conLoc.close();
                conLoc = null;
            } //if (conLoc != null)
        } //try
        catch(Exception e)
        {
           blnRes = false;
        }
        return blnRes;
    }  //Funcion validaExiNumAsiDia()

    
    
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
                 
                if(objAsiDia.isDiarioCuadrado()){
                
                }
                else{
                    mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
                    blnRes=false;
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
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                
                if(actualizarRegistro_UPD(con, intCodEmpDia, intCodLocDia, intCodTipDocDia, intCodDocDia)){
                    con.commit();
                    blnRes=true;
                }
                else{
                    con.rollback();
                }
                
//                if(eliminarDet(con, intCodEmpDia, intCodLocDia, intCodTipDocDia, intCodDocDia)){
//                    if(actualizarRegistro(con, intCodEmpDia, intCodLocDia, intCodTipDocDia, intCodDocDia)){
//                        con.commit();
//                        blnRes=true;
//                    }
//                    else
//                        con.rollback();
//                }
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)  {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    String strEstCncCta="";
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    private boolean actualizarRegistro(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc ){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                for(int i=0;i<objTblModDia.getRowCountTrue();i++){
                    strEstCncCta=objTblMod.getValueAt(i,INT_TBL_DAT_DIA_EST_CON)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_DIA_EST_CON).toString();
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detDia (co_emp, co_loc, co_tipDoc, co_dia, co_reg";
                    strSQL+=", co_cta, nd_monDeb, nd_monHab, tx_ref, st_conBan)";
                    strSQL+=" VALUES (";
                    strSQL+="" + CodEmp;
                    strSQL+=", " + CodLoc;
                    strSQL+=", " + CodTipDoc;
                    strSQL+=", " + CodDoc;
                    strSQL+=", " + (i+1);
                    strSQL+=", " + objTblModDia.getValueAt(i,INT_TBL_DAT_DIA_COD_CTA);
                    strSQL+=", " + new java.math.BigDecimal(objUti.codificar(objTblModDia.getValueAt(i,INT_TBL_DAT_DIA_DEB), 3));
                    strSQL+=", " + new java.math.BigDecimal(objUti.codificar(objTblModDia.getValueAt(i,INT_TBL_DAT_DIA_HAB), 3));
                    strSQL+=", " + objUti.codificar(objTblModDia.getValueAt(i,INT_TBL_DAT_DIA_REF));
                    if(strEstCncCta.equals("S"))
                        strSQL+=", 'S'";
                    else
                        strSQL+=", Null";
                    strSQL+=")";
                    System.out.println("insertarDet**: " +strSQL);
                    stmLoc.executeUpdate(strSQL);
                }
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    private boolean actualizarRegistro_UPD(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc ){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                for(int i=0;i<objTblModDia.getRowCountTrue();i++){
                    strSQL="";
                    strSQL+=" UPDATE tbm_detDia SET co_cta="+objTblModDia.getValueAt(i,INT_TBL_DAT_DIA_COD_CTA);
                    strSQL+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" AND co_tipDoc="+CodTipDoc+" AND co_dia="+ CodDoc ;
                    strSQL+="       AND co_reg="+(i+1)+"; ";
                    System.out.println("actualizarRegistro_UPD**: " +strSQL);
                    stmLoc.executeUpdate(strSQL);
                }
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función elimina el detalle del registro. 
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario que se desea actualizar.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet(java.sql.Connection conExt, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detDia";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + intTipDoc;
                strSQL+=" AND co_dia=" + intCodDia;
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
     
}

