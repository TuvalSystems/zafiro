/*
 * ZafVen35.java
 *
 * Created on 16 de Septiembre de 2019
 */
package Ventas.ZafVen35;

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
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author JoseMario
 */
public class ZafVen35 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_NOM_EMP=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_LOC=3;                     //Código alterno del ítem.
    static final int INT_TBL_DAT_NOM_LOC=4;                  //Código alterno 2 del ítem.
    static final int INT_TBL_DAT_BTN_USR=5;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_USR=6;                     //Total de unidades vendidas.
    static final int INT_TBL_DAT_NOM_USR=7;                     //Stock actual.
    static final int INT_TBL_DAT_BTN_COM_LOC=8;                    //Precio de venta 1.
    static final int INT_TBL_DAT_COD_COM_LOC=9;                     //Venta total.
    static final int INT_TBL_DAT_NOM_COM_LOC=10;                    //Costo total.
    static final int INT_TBL_DAT_BTN_COM_CLI=11;                    //Valor de ganancia.
    static final int INT_TBL_DAT_COD_COM_CLI=12;                    //Porcentaje de ganancia.
    static final int INT_TBL_DAT_NOM_COM_CLI=13;                //Peso total en Kilogramos.
     
     
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
     
   
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
   
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux, strVersion="v 0.04";
    private Vector vecDat, vecCab, vecReg;
    
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
  
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
   
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoComLoc, objTblCelEdiTxtVcoComCli;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoComLoc, objTblCelEdiButVcoComCli;        //Editor: JButton en celda (Comision por Local).
    
    private ZafTblCelEdiButGen objTblCelEdiBut; 
    
    
    private ZafVenCon vcoComLoc;            //
    private ZafVenCon vcoComCliente;
    private ZafThrGua objThrGua;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen35(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        chkMosLocIna = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 300));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los Vendedores");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 20);

        lblVen.setText("Vendedor:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(30, 60, 120, 20);

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
        txtCodVen.setBounds(150, 60, 56, 20);

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
        txtNomVen.setBounds(200, 60, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 60, 20, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

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

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        panFil.add(panLoc);
        panLoc.setBounds(20, 90, 640, 122);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

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

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 35));
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

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panBot.add(btnGuardar);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
            if (butCon.getText().equals("Consultar"))
            {
                blnCon=true;
                if (objThrGUI==null)
                {
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

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen))
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(1);
            }
        }
        else
            txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
            txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        { 
            txtCodVen.setText("");
            txtNomVen.setText("");
            
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(isCamVal()){
            if (objTblMod.isDataModelChanged()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                    if(objThrGua==null){
                        objThrGua=new ZafVen35.ZafThrGua();
                        objThrGua.start();
                    }
                }
            }
            else {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTextField txtCodVen;
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
            //Configurar las ZafVenCon.
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblDat();
            cargarLoc();
        }
        catch(Exception e)
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
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
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
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
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
//                    tblLocMouseClicked(evt);
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
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
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

            //Ordenar tabla
            objTblOrdLoc=new ZafTblOrd(tblLoc);
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
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_BTN_USR,"...");
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Usuario");
            vecCab.add(INT_TBL_DAT_BTN_COM_LOC,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_LOC,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_BTN_COM_CLI,"...");
            vecCab.add(INT_TBL_DAT_COD_COM_CLI,"Cód.Com.");
            vecCab.add(INT_TBL_DAT_NOM_COM_CLI,"Clientes");
             
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_LOC).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_COM_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COM_CLI).setPreferredWidth(200);
                  
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BTN_USR, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_COM_LOC);
            vecAux.add("" + INT_TBL_DAT_COD_COM_LOC);
            vecAux.add("" + INT_TBL_DAT_BTN_COM_CLI);
            vecAux.add("" + INT_TBL_DAT_COD_COM_CLI);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setCellRenderer(objTblCelRenBut);
            
            configurarTablasLocal();
            configurarTablasClientes();
            
            objTblCelEdiBut = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_LOC).setCellEditor(objTblCelEdiBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_COM_CLI).setCellEditor(objTblCelEdiBut);          
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                int intFilSel, intColSel ;
                boolean blnUsar=true;
                 public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if(intColSel==INT_TBL_DAT_BTN_COM_LOC){
                        mostrarVentanaTablas(2, intFilSel, intColSel);
                    }
                    else if(intColSel==INT_TBL_DAT_BTN_COM_CLI){
                        mostrarVentanaTablas(1, intFilSel, intColSel);
                    }
                }
            });
             
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
             
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
             
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            
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
     * Se configuran las tablas de comision Local
     * @return 
     */
    
    private boolean configurarTablasLocal()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComLocal");
            arlCam.add("a1.tx_desLarComLocal");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComLocal, a4.tx_desLar as tx_desLarComLocal , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (2,3) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Comision Local", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    
    /**
     * Se configuran las tablas de comision Local
     * @return 
     */
    
    private boolean configurarTablasClientes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tblCom");
            arlCam.add("a1.tx_desCorComClientes");
            arlCam.add("a1.tx_desLarComClientes");
            arlCam.add("a1.tx_obs2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            arlAli.add("Observación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("200");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT  a4.co_tblCom , a4.tx_desCor as tx_desCorComClientes, a4.tx_desLar as tx_desLarComClientes , a4.tx_obs2 ";
            strSQL+=" FROM tbm_cabTblComVen as a4 INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )   ";
            strSQL+=" WHERE a5.co_basCal IN (1) AND a4.st_reg='A' ";
            strSQL+=" ORDER BY a4.co_tblCom  ";
            vcoComCliente=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tablas de Comision Clientes", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoComCliente.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFilTblBod,   i, j;
       
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                  
                //Obtener los locales a consultar.
                intNumFilTblBod=objTblModLoc.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                    {
                        if (i==0)
                            strAux+=" (a5.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a5.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        else
                            strAux+=" OR (a5.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a5.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        i++;
                    }
                }
                if (!strAux.equals(""))
                {
                    strConSQL+=" AND (" + strAux + ")";
                }
                
                strSQL="";
                strSQL+=" SELECT DISTINCT a2.co_emp, EMP.tx_nom as tx_nomEmp, a1.co_usr, a1.tx_nom,  \n";
                strSQL+="                 a3.co_tblComLocal, a3.co_reg, a3.tx_desCorComLocal, a3.tx_desLarComLocal ,   \n";
                strSQL+="                 a4.co_tblComCliente, a4.co_reg, a4.tx_desCorComCliente, a4.tx_desLarComCliente \n";
                strSQL+=" FROM tbm_usr as a1  \n";
                strSQL+=" INNER JOIN tbr_usrEmp as a2 ON (a1.co_usr=a2.co_usr) \n";
                strSQL+=" INNER JOIN tbr_locUsr as a5 ON (a2.co_emp=a5.co_emp AND a2.co_usr=a5.co_usr) \n";
                strSQL+=" INNER JOIN tbm_emp as EMP ON (a2.co_emp=EMP.co_emp)  \n";
                strSQL+=" LEFT OUTER JOIN (  \n";
                strSQL+="       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComLocal, a3.co_reg, a4.tx_desCor as tx_desCorComLocal, a4.tx_desLar as tx_desLarComLocal  \n";
                strSQL+="       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
                strSQL+="       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )  \n";
                strSQL+="       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
                strSQL+="       WHERE   a5.co_basCal IN (2,3)  AND a3.st_reg='I' \n";
                strSQL+=" ) as a3 ON (EMP.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrVen) \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                strSQL+="       SELECT a3.co_emp, a3.co_usrVen, a3.co_tblCom as co_tblComCliente, a3.co_reg, a4.tx_desCor as tx_desCorComCliente, a4.tx_desLar as tx_desLarComCliente  \n";
                strSQL+="       FROM tbm_preAsiTblComVenUsrVen as a3  \n";
                strSQL+="       INNER JOIN tbm_cabTblComVen as a4 ON (a3.co_tblCom=a4.co_tblCom )   \n";
                strSQL+="       INNER JOIN tbm_basCalComVen as a5 ON (a4.co_basCal=a5.co_basCal )  \n";
                strSQL+="       WHERE   a5.co_basCal IN (1) AND a3.st_reg='I' \n";
                strSQL+=" ) as a4 ON (EMP.co_emp=a4.co_emp AND a2.co_usr=a4.co_usrVen) \n";
                strSQL+=" WHERE a1.st_reg='A'  AND a2.st_ven='S' AND a1.co_usr NOT IN (9, 93, 156, 391, 217) ";
                strSQL+=""+strConSQL;
                if(txtCodVen.getText()!=null){
                    if(txtCodVen.getText().length()>0){
                        strSQL+=" AND a1.co_usr="+txtCodVen.getText();
                    }
                }
                strSQL+=" ORDER BY a2.co_emp, a1.co_usr    \n";
                strSQL+=" \n";
                System.out.println("cargarDetReg:: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,"");
                        vecReg.add(INT_TBL_DAT_NOM_LOC,"");
                        vecReg.add(INT_TBL_DAT_BTN_USR,"");
                        vecReg.add(INT_TBL_DAT_COD_USR,rst.getString("co_usr"));
                        vecReg.add(INT_TBL_DAT_NOM_USR,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_BTN_COM_LOC,"");
                        vecReg.add(INT_TBL_DAT_COD_COM_LOC,rst.getString("co_tblComLocal"));
                        vecReg.add(INT_TBL_DAT_NOM_COM_LOC,rst.getString("tx_desLarComLocal"));
                        vecReg.add(INT_TBL_DAT_BTN_COM_CLI,"");
                        vecReg.add(INT_TBL_DAT_COD_COM_CLI,rst.getString("co_tblComCliente"));
                        vecReg.add(INT_TBL_DAT_NOM_COM_CLI,rst.getString("tx_desLarComCliente"));
                      
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
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
                //Calcular totales.
                
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        //Validar que esté seleccionada al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un local.<BR>Seleccione un local y vuelva a intentarlo.</HTML>");
            tblLoc.requestFocus();
            return false;
        }
       
        return true;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
        try
        {
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSQL+=" WHERE a1.st_reg='A' AND a2.st_ven='S'";
                strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
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
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Empresa";
                    break;
                case INT_TBL_DAT_COD_USR:
                    strMsg="Código del usuario...";
                    break;
                case INT_TBL_DAT_NOM_USR:
                    strMsg="Nombre del usuario...";
                    break;
                case INT_TBL_DAT_BTN_COM_LOC:
                    strMsg="Muestra una ventana con las tablas de comisiones locales...";
                    break;
                case INT_TBL_DAT_BTN_COM_CLI:
                    strMsg="Muestra una ventana con las tablas de comisiones de los clientes configurados...";
                    break;
               
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     

    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (!guardar()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                }
                butCon.doClick();
                objThrGua=null;
                
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    private boolean guardar(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            for(int i=0; i<intNumFil;i++){
                if(objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                    conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                    if(conLoc!=null){
                        conLoc.setAutoCommit(false);
                        if(guardarModificacion(conLoc, i)){
                            conLoc.commit();
                            mostrarMsgInf("Listo...");
                        }
                        else{
                             conLoc.rollback();
                            blnRes=false;
                            mostrarMsgInf("Error al guardar...");
                        }
                        conLoc.close();
                        conLoc=null;
                    }
                    
                }
            }
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }  
        return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @param ROW
     * @return 
     */
    private boolean guardarModificacion(java.sql.Connection conExt, int ROW){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        String strIns="";
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();                
                if(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC)!=null){
                    if(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString().length()>0 ){
                        if(!configuracionAntigua(conExt, Integer.parseInt(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString()) , ROW)){
                            if(existeConfiguracion(conExt, 1, ROW)){
                                strIns="";
                                strIns+=" UPDATE tbm_preAsiTblComVenUsrVen SET co_tblCom="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString()+", ";
                                strIns+=" fe_ing=CURRENT_TIMESTAMP, co_usrIng="+objParSis.getCodigoUsuario()+",tx_comIng="+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+"  ";
                                strIns+=" WHERE co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND ";
                                strIns+=" co_reg="+CodigoRegistroExiste+" ; ";
                                System.out.println("strIns " + strIns);
                                stmLoc.executeUpdate(strIns);
                            }
                            else{
                                strIns="";
                                strIns+=" INSERT INTO tbm_preAsiTblComVenUsrVen (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
                                strIns+=" VALUES ("+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+",";
                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+",";
                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_LOC).toString()+",";
                                strIns+=" "+getCodigoRegistro(conExt, objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()) +",";
                                strIns+=" CURRENT_TIMESTAMP,"+objParSis.getCodigoUsuario()+", "+ objUti.codificar(objParSis.getNombreComputadoraConDirIP()) +",'I'  ";
                                strIns+=" );";
                                System.out.println("strIns " + strIns);
                                stmLoc.executeUpdate(strIns);
                            }
                        }
                        
                    }
                }
                if(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_CLI)!=null){
                    if(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_CLI).toString().length()>0 ){
                        if(!configuracionAntigua(conExt, Integer.parseInt(objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_CLI).toString()) , ROW)){
                            if(existeConfiguracion(conExt, 2, ROW)){
                                strIns="";
                                strIns+=" UPDATE tbm_preAsiTblComVenUsrVen SET co_tblCom="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_CLI).toString()+", ";
                                strIns+=" fe_ing=CURRENT_TIMESTAMP, co_usrIng="+objParSis.getCodigoUsuario()+",tx_comIng="+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+"  ";
                                strIns+=" WHERE co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND ";
                                strIns+=" co_reg="+CodigoRegistroExiste+" ; ";
                                System.out.println("strIns " + strIns);
                                stmLoc.executeUpdate(strIns);
                            }
                            else{
                                strIns="";
                                strIns+=" INSERT INTO tbm_preAsiTblComVenUsrVen (co_emp, co_usrVen, co_tblCom, co_reg, fe_ing, co_usrIng, tx_comIng, st_reg) ";
                                strIns+=" VALUES ("+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+",";
                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+",";
                                strIns+=" "+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_COM_CLI).toString()+",";
                                strIns+=" "+getCodigoRegistro(conExt, objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString(),objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()) +",";
                                strIns+=" CURRENT_TIMESTAMP,"+objParSis.getCodigoUsuario()+", "+objUti.codificar(objParSis.getNombreComputadoraConDirIP())+",'I'  ";
                                strIns+=" );";
                                System.out.println("strIns " + strIns);
                                stmLoc.executeUpdate(strIns);
                            }
                        }
                        
                    }
                }
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }  
        return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @param CodEmp
     * @param CodUsr
     * @return 
     */
    
    private int getCodigoRegistro(java.sql.Connection conExt, String CodEmp, String CodUsr){
        int intRet=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX(a1.co_reg) IS NULL THEN 1 ELSE MAX(a1.co_reg)+1 END AS maximoRegistro \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" AND a1.co_usrVen="+CodUsr+"   ";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRet = rstLoc.getInt("maximoRegistro");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            intRet=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           intRet=-1;
           objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return intRet;
    }
    
    private int CodigoRegistroExiste=-1;
    
    /**
     * 1;"COCLAS";"Cobros a clientes asignados"
       2;"COLO";"Cobros del local"
       3;"COLONOPA";"Cobros del local menos Patricia Solorzano"

     * @param conExt
     * @param tipoCalculo  1  LOCAL  ****  2  Clientes
     * @param ROW
     * @return 
     */
    
    private boolean existeConfiguracion(java.sql.Connection conExt, int tipoCalculo, int ROW){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strAux="";
        try{
            if(tipoCalculo==1){
                strAux=" AND a2.co_basCal IN (2,3) ";
            }else{
                strAux=" AND a2.co_basCal IN (1) ";
            }
            
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*  \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" INNER JOIN tbm_cabTblComVen as a2 ON (a1.co_tblCom=a2.co_tblCom)   \n";
                strSQL+=" WHERE  a1.co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+"  AND a1.st_reg='I'  ";
                strSQL+=""+strAux; 
                System.out.println("existeConfiguracion " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    CodigoRegistroExiste = rstLoc.getInt("co_reg");
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        }  
        return blnRes;
    }
    
    /**
     * Para validar que no exista la configuracion
     * @param conExt Conexion
     * @param CodTblCom Codigo de la tabla de comision
     * @param ROW Fila
     * @return 
     */
    private boolean configuracionAntigua(java.sql.Connection conExt, int CodTblCom, int ROW){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.*  \n";
                strSQL+=" FROM tbm_preAsiTblComVenUsrVen as a1 \n";
                strSQL+=" WHERE a1.co_usrVen="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_USR).toString()+" AND a1.co_emp="+objTblMod.getValueAt(ROW,INT_TBL_DAT_COD_EMP).toString()+" AND  ";
                strSQL+="       a1.co_tblCom="+CodTblCom+"  ";
                System.out.println("existeConfiguracion " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    blnRes=true;
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
           blnRes=false;
        } 
        return blnRes;
    }
    
    
    
    /**
     * 
     * @param CodFilSel
     * @param CodColSel
     * @return 
     */
    
    private boolean mostrarVentanaTablas(int intCodBasCal, int ROW, int COLUMN){
        boolean blnRes=false;
        try {
            ZafVen35_01 obj = new ZafVen35_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, true,  intCodBasCal);
//            this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj.show();
            if(obj.acepta()){
                objTblMod.setValueAt(obj.intCodTabSel, ROW, COLUMN+1); 
                objTblMod.setValueAt(obj.strDesCorTabSel, ROW, COLUMN+2); 
                obj=null;
                blnRes=true;
            }
            else{
                obj=null;
            }
        } 
        catch (Exception Evt) {    
            blnRes=false;    
            objUti.mostrarMsgErr_F1(this, Evt);     
        }
        return blnRes;
    }
    
    
    
}