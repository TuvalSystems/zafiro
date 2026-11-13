/*
 * ZafCon66.java
 *
 * Created on 02 de octubre de 2006, 11:38 AM
 */

package Contabilidad.ZafCon66;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

//para ireports
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrint; 
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


/**
 *
 * @author Sayda Villalta
 */
public class ZafCon66 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;                //Lánea
    final int INT_TBL_DAT_COD_SIS=1;            //Cádigo de la cuenta (Sistema).
    final int INT_TBL_DAT_NIV_CTA=2;            //Nivel de la cuenta.
    final int INT_TBL_DAT_PAD_CTA=3;            //Padre de la cuenta.
    final int INT_TBL_DAT_COD_CTA=4;            //Cádigo de la cuenta (Contable).
    final int INT_TBL_DAT_NOM_CTA=5;            //Nombre de la cuenta.
    final int INT_TBL_DAT_SAL_MEN=6;            //Saldo mensual.
    final int INT_TBL_DAT_SAL_ACU=7;            //Saldo acumulado.
    final int INT_TBL_DAT_TIP_CTA=8;            //Tipo de cuenta.
    final int INT_TBL_DAT_BTN_CTA_ANA=9;            //Boton para revisar las analiticas.
    final int INT_TBL_DAT_CTA_ANA=10;            //Boton para revisar las analiticas.
    
    
    //Constantes: Array List de la exportacion de datos
    final int INT_ARL_DAT_COD_ENT=0;            //Codigo del ente del mercado de valores.
    final int INT_ARL_DAT_COD_CTA=1;            //Codigo de la cuenta contable de ultimo nivel.
    final int INT_ARL_DAT_VAL_CTA=2;            //Valor de la cuenta contable.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PopupMená en JTable.
    private ZafTblBus objTblBus;                        //Editor de básqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafPerUsr objPerUsr;
    private ZafRptSis objRptSis;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafThreadGUIExportar objThrGUIExp;
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
 
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    private boolean blnButExp;
    javax.swing.JInternalFrame jfrThis;                 //Hace referencia a this
    private String strFecAux;
    private int intNIVELES=8;
    private double dblSalMenImp;
    private double dblSalAcuImp;
    
    private ZafTblCelRenBut objTblCelRenBut; //Render: Presentar JButton en JTable. // JM 21/Oct/2024

    //private String strNivRep;
    //private String strFecCor;
    
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon66(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            //objParSis=(ZafParSis)obj.clone();
            
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
//            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");

            objPerUsr=new ZafPerUsr(objParSis);
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            blnButExp = false;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFilFec = new javax.swing.JPanel();
        optDia = new javax.swing.JRadioButton();
        optMes = new javax.swing.JRadioButton();
        cboMesCor = new javax.swing.JComboBox();
        cboAnoCor = new javax.swing.JComboBox();
        panFil2 = new javax.swing.JPanel();
        lblNiv = new javax.swing.JLabel();
        cboNiv = new javax.swing.JComboBox();
        chkSalCer = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        panRptReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butImp = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panFilFec.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de corte"));
        panFilFec.setPreferredSize(new java.awt.Dimension(100, 150));
        panFilFec.setLayout(null);

        buttonGroup1.add(optDia);
        optDia.setSelected(true);
        optDia.setText("Día");
        optDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDiaActionPerformed(evt);
            }
        });
        panFilFec.add(optDia);
        optDia.setBounds(53, 20, 50, 20);

        buttonGroup1.add(optMes);
        optMes.setText("Mes");
        optMes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optMesStateChanged(evt);
            }
        });
        optMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optMesActionPerformed(evt);
            }
        });
        panFilFec.add(optMes);
        optMes.setBounds(53, 42, 50, 20);
        panFilFec.add(cboMesCor);
        cboMesCor.setBounds(110, 42, 180, 20);
        panFilFec.add(cboAnoCor);
        cboAnoCor.setBounds(292, 42, 64, 20);

        panFil.add(panFilFec, java.awt.BorderLayout.CENTER);

        panFil2.setPreferredSize(new java.awt.Dimension(100, 240));
        panFil2.setLayout(null);

        lblNiv.setText("Nivel:");
        panFil2.add(lblNiv);
        lblNiv.setBounds(10, 6, 100, 20);
        panFil2.add(cboNiv);
        cboNiv.setBounds(110, 6, 180, 20);

        chkSalCer.setSelected(true);
        chkSalCer.setText("No incluir cuentas con saldo cero");
        panFil2.add(chkSalCer);
        chkSalCer.setBounds(10, 70, 290, 29);

        panFil.add(panFil2, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        panRptReg.setLayout(new java.awt.BorderLayout(0, 1));

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

        panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panRptReg.add(spnTot, java.awt.BorderLayout.SOUTH);

        panRpt.add(panRptReg, java.awt.BorderLayout.CENTER);

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

        butImp.setText("Imprimir");
        butImp.setPreferredSize(new java.awt.Dimension(92, 25));
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panBot.add(butImp);

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

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private boolean isProRep() 
    {
//            if(!(imprimir())){
//                mostrarMsgInf("<HTML>El reporte no se pudo generar correctamente<BR>Intentelo nuevamente.</HTML>");
//                return false;
//            }   
//            return true;
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;            
        }    
    
    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        isProRep();
    }//GEN-LAST:event_butImpActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="áEstá seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void optMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optMesActionPerformed
        if(optMes.isSelected())
            optDia.setSelected(false);
        else
            optDia.setSelected(true);
    }//GEN-LAST:event_optMesActionPerformed

    private void optDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDiaActionPerformed
        if(optDia.isSelected())
            optMes.setSelected(false);
        else
            optMes.setSelected(true);
    }//GEN-LAST:event_optDiaActionPerformed

    private void optMesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optMesStateChanged
        
    }//GEN-LAST:event_optMesStateChanged

    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cboAnoCor;
    private javax.swing.JComboBox cboMesCor;
    private javax.swing.JComboBox cboNiv;
    private javax.swing.JCheckBox chkSalCer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNiv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optDia;
    private javax.swing.JRadioButton optMes;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar el combo "Mes de corte".
            cboMesCor.addItem("Enero");
            cboMesCor.addItem("Febrero");
            cboMesCor.addItem("Marzo");
            cboMesCor.addItem("Abril");
            cboMesCor.addItem("Mayo");
            cboMesCor.addItem("Junio");
            cboMesCor.addItem("Julio");
            cboMesCor.addItem("Agosto");
            cboMesCor.addItem("Septiembre");
            cboMesCor.addItem("Octubre");
            cboMesCor.addItem("Noviembre");
            cboMesCor.addItem("Diciembre");
            cboMesCor.setSelectedIndex(0);
//            //Configurar el combo "Aáo de corte".
//            cboAnoCor.addItem("2005");
//            cboAnoCor.addItem("2006");
//            cboAnoCor.addItem("2007");
//            cboAnoCor.addItem("2008");
//            cboAnoCor.addItem("2009");
//            cboAnoCor.addItem("2010");
//            cboAnoCor.setSelectedIndex(4);
            
            cargarAnioCreados();
            
            //Configurar el combo "Nivel".
            cboNiv.addItem("1");
            cboNiv.addItem("2");
            cboNiv.addItem("3");
            cboNiv.addItem("4");
            cboNiv.addItem("5");
            cboNiv.addItem("6");
            cboNiv.addItem("7");
            cboNiv.addItem("8");
            cboNiv.addItem("9");
            //cboNiv.setSelectedIndex(6);
            cboNiv.setSelectedIndex(8);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.02");
            lblTit.setText(strAux);
            //Configurar objetos.
//            txtCodItm.setVisible(false);
            //Configurar los JTables.
            configurarTblDat();

            /* JM */
            optMes.setVisible(false);
            cboMesCor.setVisible(false);
            cboAnoCor.setVisible(false);
            butImp.setVisible(false);
            /* JM */
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            
            panFilFec.add(dtpFecDoc);
            dtpFecDoc.setBounds(110, 20, 120, 20);


            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                        

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    

    /**
     * Esta funcián configura el JTable "tblDat".
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
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_NIV_CTA,"Nivel");
            vecCab.add(INT_TBL_DAT_PAD_CTA,"Cód.Cta.Pad.");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_SAL_MEN,"Sal.Men.");
            vecCab.add(INT_TBL_DAT_SAL_ACU,"Sal.Acu.");
            vecCab.add(INT_TBL_DAT_TIP_CTA,"Tip.Cta.");
            vecCab.add(INT_TBL_DAT_BTN_CTA_ANA,"...");  //JM 21/Oct/2024  
            vecCab.add(INT_TBL_DAT_CTA_ANA,"");  //JM 31/Oct/2024  
             

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(377);
            tcmAux.getColumn(INT_TBL_DAT_SAL_MEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_SAL_ACU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_BTN_CTA_ANA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_ANA).setPreferredWidth(30);
            
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setResizable(false);
//
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NIV_CTA).setResizable(false);
//
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_PAD_CTA).setResizable(false);


            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NIV_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PAD_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CTA_ANA, tblDat);
             
            
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_CTA_ANA).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            ButConAna butConAna = new ButConAna(tblDat, INT_TBL_DAT_BTN_CTA_ANA); //*****


            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_SAL_MEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_ACU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_SAL_MEN, INT_TBL_DAT_SAL_ACU};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);                      

            
            if (objParSis.getCodigoMenu()==342){
                strAux="Diferencias";
                objTblTot.setValueAt(""+strAux,0,INT_TBL_DAT_NOM_CTA);
            }
            else{
                strAux="Utilidad/Pérdida";
                objTblTot.setValueAt(""+strAux,0,INT_TBL_DAT_NOM_CTA);


                if(objParSis.getCodigoUsuario()==1){
                    objTblTot.insertMoreRows(2);
                    spnTot.setPreferredSize(new java.awt.Dimension(454, 50));
                }
                else{
                    if(objPerUsr.isOpcionEnabled(2764)){
                        objTblTot.insertMoreRows(2);
                        spnTot.setPreferredSize(new java.awt.Dimension(454, 50));
                    }
                }

            }
            
             //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_CTA_ANA);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
                         
            
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
    
    
    private Vector  vecAux;
    
    
    
    private class ButConAna extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButConAna(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Consulta de Analiticas.");
           
        }
        @Override
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
           String strCodCta = (tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_SIS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_SIS  ).toString());
           if( Integer.parseInt(tblDat.getValueAt(intCol,  INT_TBL_DAT_CTA_ANA  ).toString())> 0  )
                llamarVenAnaliticas(strCodCta);
           else{
               mostrarMsgErr("La cuenta seleccionada no posee analitica...");
           }
           
        }
    }
    
    
    private void llamarVenAnaliticas(String strCodCta){
        String strFechaDesde="";
        String strFechaHasta=""; 
        
        int intCodCta = Integer.parseInt(strCodCta);
        
        if(optMes.isSelected()){ //la forma que hasta el momento se ha manejado


        }
        else{
            strFechaDesde = objUti.codificar( objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) );
            strFechaHasta = objUti.codificar( objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) );
        }
         
        ZafCon66_01 obj1 = new  Contabilidad.ZafCon66.ZafCon66_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, intCodCta, strFechaDesde, strFechaHasta);
//        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }
            
    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     *  4764;7456;"R";"BALANCE GENERAL SV...";"";338;3;"";"Contabilidad.ZafCon66.ZafCon66"
        4767;7457;"R";"ESTADO DE RESULTADO SV...";"";338;3;"";"Contabilidad.ZafCon66.ZafCon66"

     */
    private boolean cargarDetReg()
    {
        int intNiv, j, intFecCor;
        double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
        String strNumCta, strTipCta, strNivCta1;
        boolean blnRes=true;
        Statement stmLoc1, stmLoc2;
        ResultSet rstLoc1, rstLoc2;
        
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                stmLoc1 = con.createStatement();
                stmLoc2 = con.createStatement();
                //Obtener la condicián.
                strAux="";
                
                /*JM Para meses se usa esto*/
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                /*JM Para meses se usa esto*/
                
                //INICIO: Mejorar esto:
                strFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), "yyyy-MM-dd");
                //  objUti.remayorizar(this, objParSis, obtienFechRemayor(strFecAux), strFecAux, "yyyy-MM-dd");
                //FIN: Mejorar esto.
                
                 
                //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                {
                    strSQL="";
                    strSQL+=" SELECT a1.ACTINDX, a1.USRDEFS1 as NIVEL, a1.USRDEFS2 as PADRE,  \n";
                    strSQL+="        CASE WHEN a1.TPCLBLNC = 0 THEN 'D' ELSE 'C' END SALDOTIPICO, \n";
                    strSQL+="          TRIM(a2.ACTNUMST) as ACTNUMST, TRIM(a1.ACTDESCR) as ACTDESCR, a3.ValorMen, a4.ValorAcum, COUNT(a6.aaTrxDimID) as dimenciones   \n";
                    strSQL+=" FROM TUVAL.dbo.GL00100 as a1  \n";
                    strSQL+=" INNER JOIN TUVAL.dbo.GL00105 as a2 ON (a1.ACTINDX=a2.ACTINDX) \n";
                    strSQL+=" INNER JOIN TUVAL.dbo.AAG00200 as a5 ON (a1.ACTINDX=a5.ACTINDX) \n";
                    strSQL+=" LEFT OUTER JOIN TUVAL.dbo.AAG00202 as a6 ON (a5.aaAcctClassID=a6.aaAcctClassID) \n";
                    strSQL+=" LEFT OUTER JOIN ( \n";
                    strSQL+="       SELECT a1.ACTINDX, SUM(a1.ValorMen) as ValorMen \n";
                    strSQL+="       FROM ( \n";
                    strSQL+="               SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorMen \n";
                    strSQL+="               FROM TUVAL.dbo.GL20000 (nolock) as a1  \n";
                    strSQL+="               INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX) \n";
                    strSQL+="               WHERE a1.SOURCDOC not in ('BBF') and a1.VOIDED = 0 ";
                    if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
//                    FORMAT(TuFecha, 'yyyyMM')
                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND FORMAT(CAST(a1.TRXDATE as DATE), 'yyyyMM') BETWEEN '" + strFecCorIni + "' AND '" + strFecCor + "'";
  
                    strSQL+="               UNION ALL     \n";
                    strSQL+="               SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorMen \n";
                    strSQL+="               FROM TUVAL.dbo.GL30000 (nolock) as a1  \n";
                    strSQL+="               INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="               WHERE a1.SOURCDOC not in ('BBF') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }   
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
                    
                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND FORMAT(CAST(a1.TRXDATE as DATE), 'yyyyMM') BETWEEN '" + strFecCorIni + "' AND '" + strFecCor + "'";
 
                      
                    strSQL+="       ) as a1   \n";
                    strSQL+="       GROUP BY  a1.ACTINDX  \n";
                    strSQL+=" ) as a3 ON (a1.ACTINDX=a3.ACTINDX) \n";
                    strSQL+=" LEFT OUTER JOIN ( \n";
                    strSQL+="       SELECT a1.ACTINDX, SUM(a1.ValorAcum) as ValorAcum \n";
                    strSQL+="       FROM (  \n";
                    strSQL+="           SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorAcum \n";
                    strSQL+="           FROM TUVAL.dbo.GL20000 (nolock) as a1  \n";
                    strSQL+="           INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="           WHERE a1.SOURCDOC not in ('BBF') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) AND ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) AND ";  // Estado de resultado 
                    }
                     
                     
//                    strSQL+="                 CAST(a1.TRXDATE as DATE) <=  '2024-09-30'    \n";
                    if (dtpFecDoc.isFecha())
                        strSQL+=" FORMAT(CAST(a1.TRXDATE as DATE), 'yyyyMM') <=  '" + strFecCor + "'";
                    
                    strSQL+="           UNION ALL  \n";
                    strSQL+="           SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorAcum  \n";
                    strSQL+="           FROM TUVAL.dbo.GL30000 (nolock) as a1  \n";
                    strSQL+="           INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="           WHERE a1.SOURCDOC not in ('BBF') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) AND ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) AND ";  // Estado de resultado 
                    }
                     
                    if (dtpFecDoc.isFecha())
                        strSQL+=" FORMAT(CAST(a1.TRXDATE as DATE), 'yyyyMM') <=  '" + strFecCor + "'";
 
                   // strSQL+="                 CAST(a1.TRXDATE as DATE) <=   '2024-09-30'  \n";
                    strSQL+="       ) as a1  \n";
                    strSQL+="       GROUP BY  a1.ACTINDX  \n";
                    strSQL+=" ) as a4 ON (a1.ACTINDX=a4.ACTINDX) \n";
                    
                    if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" WHERE a1.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }
                    else{
                        strSQL+=" WHERE a1.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
                    strSQL+=" GROUP BY a1.ACTINDX, a1.USRDEFS1, a1.USRDEFS2, a1.TPCLBLNC, a2.ACTNUMST, a1.ACTDESCR, a3.ValorMen, a4.ValorAcum  \n";     
                    strSQL+=" ORDER BY a2.ACTNUMST   \n";
                      
                }
                //</editor-fold>
                //<editor-fold defaultstate="collapsed" desc="/* Filtro: DIA */">
                else //a una fecha que no es mes completo Ejm. 2006-01-12
                {
                    
                    strSQL="";
                    strSQL+=" SELECT a1.ACTINDX, a1.USRDEFS1 as NIVEL, a1.USRDEFS2 as PADRE,  \n";
                    strSQL+="        CASE WHEN a1.TPCLBLNC = 0 THEN 'D' ELSE 'C' END SALDOTIPICO, \n";
                    strSQL+="          TRIM(a2.ACTNUMST) as ACTNUMST, TRIM(a1.ACTDESCR) as ACTDESCR, a3.ValorMen, a4.ValorAcum, COUNT(a6.aaTrxDimID) as dimenciones   \n";
                    strSQL+=" FROM TUVAL.dbo.GL00100 as a1  \n";
                    strSQL+=" INNER JOIN TUVAL.dbo.GL00105 as a2 ON (a1.ACTINDX=a2.ACTINDX) \n";
                    strSQL+=" INNER JOIN TUVAL.dbo.AAG00200 as a5 ON (a1.ACTINDX=a5.ACTINDX) \n";
                    strSQL+=" LEFT OUTER JOIN TUVAL.dbo.AAG00202 as a6 ON (a5.aaAcctClassID=a6.aaAcctClassID) \n";
                    strSQL+=" LEFT OUTER JOIN ( \n";
                    strSQL+="       SELECT a1.ACTINDX, SUM(a1.ValorMen) as ValorMen \n";
                    strSQL+="       FROM ( \n";
                    strSQL+="               SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorMen \n";
                    strSQL+="               FROM TUVAL.dbo.GL20000 (nolock) as a1  \n";
                    strSQL+="               INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX) \n";
                    strSQL+="               WHERE a1.SOURCDOC not in ('BBF','P/L') and a1.VOIDED = 0 ";
                    if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
                    
                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND CAST(a1.TRXDATE as DATE) BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
  
                    strSQL+="               UNION ALL     \n";
                    strSQL+="               SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorMen \n";
                    strSQL+="               FROM TUVAL.dbo.GL30000 (nolock) as a1  \n";
                    strSQL+="               INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="               WHERE a1.SOURCDOC not in ('BBF','P/L') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }   
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
                    
                    if (dtpFecDoc.isFecha())
                        strSQL+=" AND CAST(a1.TRXDATE as DATE) BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
 
                      
                    strSQL+="       ) as a1   \n";
                    strSQL+="       GROUP BY  a1.ACTINDX  \n";
                    strSQL+=" ) as a3 ON (a1.ACTINDX=a3.ACTINDX) \n";
                    strSQL+=" LEFT OUTER JOIN ( \n";
                    strSQL+="       SELECT a1.ACTINDX, SUM(a1.ValorAcum) as ValorAcum \n";
                    strSQL+="       FROM (  \n";
                    strSQL+="           SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorAcum \n";
                    strSQL+="           FROM TUVAL.dbo.GL20000 (nolock) as a1  \n";
                    strSQL+="           INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="           WHERE a1.SOURCDOC not in ('BBF','P/L') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) AND ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) AND ";  // Estado de resultado 
                    }
                     
                     
//                    strSQL+="                 CAST(a1.TRXDATE as DATE) <=  '2024-09-30'    \n";
                    if (dtpFecDoc.isFecha())
                        strSQL+=" CAST(a1.TRXDATE as DATE) <=  '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    
                    strSQL+="           UNION ALL  \n";
                    strSQL+="           SELECT a1.ACTINDX, a1.DEBITAMT - a1.CRDTAMNT AS ValorAcum  \n";
                    strSQL+="           FROM TUVAL.dbo.GL30000 (nolock) as a1  \n";
                    strSQL+="           INNER JOIN TUVAL.dbo.GL00100 as a2 ON (a1.ACTINDX=a2.ACTINDX)  \n";
                    strSQL+="           WHERE a1.SOURCDOC not in ('BBF','P/L') and a1.VOIDED = 0 ";
                     if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" AND a2.ACTNUMBR_1 IN (1,2,3) AND ";  // Balance General 
                    }
                    else{
                        strSQL+=" AND a2.ACTNUMBR_1 IN (4,5) AND ";  // Estado de resultado 
                    }
                     
                    if (dtpFecDoc.isFecha())
                        strSQL+=" CAST(a1.TRXDATE as DATE) <=  '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
 
                   // strSQL+="                 CAST(a1.TRXDATE as DATE) <=   '2024-09-30'  \n";
                    strSQL+="       ) as a1  \n";
                    strSQL+="       GROUP BY  a1.ACTINDX  \n";
                    strSQL+=" ) as a4 ON (a1.ACTINDX=a4.ACTINDX) \n";
                    
                    if (objParSis.getCodigoMenu() == 4764){
                        strSQL+=" WHERE a1.ACTNUMBR_1 IN (1,2,3) ";  // Balance General 
                    }
                    else{
                        strSQL+=" WHERE a1.ACTNUMBR_1 IN (4,5) ";  // Estado de resultado 
                    }
                    strSQL+=" GROUP BY a1.ACTINDX, a1.USRDEFS1, a1.USRDEFS2, a1.TPCLBLNC, a2.ACTNUMST, a1.ACTDESCR, a3.ValorMen, a4.ValorAcum  \n";     
                    strSQL+=" ORDER BY a2.ACTNUMST   \n";
                      
                }
                //</editor-fold>

                 
                //</editor-fold>
                
                System.out.println("SQL-BG/ER:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                intFecCor = Integer.parseInt(strFecCor);                                
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                dblTotMen=0;
                dblTotAcu=0;
                int intCtaPad=0;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("ACTINDX"));
                        intNiv=rst.getInt("NIVEL");
                        vecReg.add(INT_TBL_DAT_NIV_CTA,"" + intNiv);
                        vecReg.add(INT_TBL_DAT_PAD_CTA,(rst.getInt("PADRE")==0?(intCtaPad--):rst.getInt("PADRE")));
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("ACTNUMST"));
                        
                        //Generar la tabulación para el campo "tx_desLar".
                        strAux="";
                        for (j=1; j<intNiv; j++)
                            strAux+="        ";
                        vecReg.add(INT_TBL_DAT_NOM_CTA,strAux + rst.getString("ACTDESCR"));
                        dblSalMen=rst.getDouble("ValorMen");
                        dblSalAcu=rst.getDouble("ValorAcum");
                        
                        vecReg.add(INT_TBL_DAT_SAL_MEN,"" + dblSalMen);
                        vecReg.add(INT_TBL_DAT_SAL_ACU,"" + dblSalAcu);
 
                        vecReg.add(INT_TBL_DAT_TIP_CTA,"" + rst.getString("SALDOTIPICO"));
                        vecReg.add(INT_TBL_DAT_BTN_CTA_ANA,"");
                        vecReg.add(INT_TBL_DAT_CTA_ANA,rst.getString("dimenciones"));
                         
                        
                        if (intNiv==1)
                        {
                            dblTotMen+=dblSalMen;
                            dblTotAcu+=dblSalAcu;
                        }
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
                
                /*INICIO NUEVO*/
                
                
                if (objParSis.getCodigoMenu() == 4764 && intFecCor >= 202001){
                   //Cod.Menu 342 = Balance General.
                   /*Saldo Mensual*/
                    int intCtaCtbMod=-1;
                    int intNivCtaMod=-1;
                    int intCtaPadMod=-1;
                    int intCtaPadArl=-1;
                    BigDecimal bdeValMenCal=BigDecimal.ZERO;
                    BigDecimal bdeValAcuCal=BigDecimal.ZERO;
                    ArrayList arlRegCalCtaCab, arlDatCalCtaCab;
                    final int INT_ARL_MOD_CAL_CTA_CAB=0;
                    arlDatCalCtaCab=new ArrayList();
                    arlDatCalCtaCab.clear();
                    
                    int intCtaPadModCal=-1;
                    boolean blnCtaPadPro=false;
                   
                    for(int i=(objTblMod.getRowCountTrue()-1); i>0; i--){
                        bdeValAcuCal=BigDecimal.ZERO;
                        intCtaPadMod=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());
                        blnCtaPadPro=false;
                        for(int l=0; l<arlDatCalCtaCab.size(); l++){
                            intCtaPadArl=objUti.getIntValueAt(arlDatCalCtaCab, l, INT_ARL_MOD_CAL_CTA_CAB);
                            if(intCtaPadArl==intCtaPadMod){//Ya fue procesado ese codigo padre
                                blnCtaPadPro=true;
                            }
                            else{
                                arlRegCalCtaCab=new ArrayList();
                                arlRegCalCtaCab.add(INT_ARL_MOD_CAL_CTA_CAB, intCtaPadMod);
                                arlDatCalCtaCab.add(arlRegCalCtaCab);
                            }     
                        }     
                        if(!blnCtaPadPro){
                             for(int k=(objTblMod.getRowCountTrue()-1); k>=0; k--){
                                 intCtaPadModCal=Integer.parseInt(objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA).toString().equals("")?"0":objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA).toString());
                                 if(intCtaPadModCal==intCtaPadMod){                                     
                                     bdeValMenCal=new BigDecimal(objTblMod.getValueAt(k, INT_TBL_DAT_SAL_MEN)==null?"0":(objTblMod.getValueAt(k, INT_TBL_DAT_SAL_MEN).toString().equals("")?"0":objTblMod.getValueAt(k, INT_TBL_DAT_SAL_MEN).toString()));
                                     bdeValAcuCal=bdeValAcuCal.add(bdeValMenCal);
                                 }
                             }
                             //System.out.println("bdeValAcuCal: " + bdeValAcuCal);
                             for(int m=(objTblMod.getRowCountTrue()-1); m>=0; m--){
                                 intCtaCtbMod=Integer.parseInt(objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS).toString().equals("")?"0":objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS).toString());
                                 if(intCtaPadMod==intCtaCtbMod){
                                    objTblMod.setValueAt(bdeValAcuCal, m, INT_TBL_DAT_SAL_MEN);
                                    bdeValAcuCal=BigDecimal.ZERO;
                                    break;
                                 }
                             }
                        }
                    }
                    
                   /*Saldo Acumulado*/
                   arlDatCalCtaCab.clear();
                   
                    for(int i=(objTblMod.getRowCountTrue()-1); i>0; i--){
                        bdeValAcuCal=BigDecimal.ZERO;
                        intCtaPadMod=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());
                        blnCtaPadPro=false;
                        for(int l=0; l<arlDatCalCtaCab.size(); l++){
                            intCtaPadArl=objUti.getIntValueAt(arlDatCalCtaCab, l, INT_ARL_MOD_CAL_CTA_CAB);
                            if(intCtaPadArl==intCtaPadMod){//Ya fue procesado ese codigo padre
                                blnCtaPadPro=true;
                            }
                            else{
                                arlRegCalCtaCab=new ArrayList();
                                arlRegCalCtaCab.add(INT_ARL_MOD_CAL_CTA_CAB, intCtaPadMod);
                                arlDatCalCtaCab.add(arlRegCalCtaCab);
                            }     
                        }     
                        if(!blnCtaPadPro){
                             for(int k=(objTblMod.getRowCountTrue()-1); k>=0; k--){
                                 intCtaPadModCal=Integer.parseInt(objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA).toString().equals("")?"0":objTblMod.getValueAt(k, INT_TBL_DAT_PAD_CTA).toString());
                                 if(intCtaPadModCal==intCtaPadMod){                                     
                                     bdeValMenCal=new BigDecimal(objTblMod.getValueAt(k, INT_TBL_DAT_SAL_ACU)==null?"0":(objTblMod.getValueAt(k, INT_TBL_DAT_SAL_ACU).toString().equals("")?"0":objTblMod.getValueAt(k, INT_TBL_DAT_SAL_ACU).toString()));
                                     bdeValAcuCal=bdeValAcuCal.add(bdeValMenCal);
                                 }
                             }
                             //System.out.println("bdeValAcuCal: " + bdeValAcuCal);
                             for(int m=(objTblMod.getRowCountTrue()-1); m>=0; m--){
                                 intCtaCtbMod=Integer.parseInt(objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS).toString().equals("")?"0":objTblMod.getValueAt(m, INT_TBL_DAT_COD_SIS).toString());
                                 if(intCtaPadMod==intCtaCtbMod){
                                    objTblMod.setValueAt(bdeValAcuCal, m, INT_TBL_DAT_SAL_ACU);
                                    bdeValAcuCal=BigDecimal.ZERO;
                                    break;
                                 }
                             }
                        }
                    }
                }
                /*FIN NUEVO*/
                 
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);

                if( ! optMes.isSelected())  {
                    if(generaSaldosCuentasPadres()){
                        if(chkSalCer.isSelected()){
                            if(quitarCtasSaldoCero()){
                                if(calculaTotales()){

                                }
                            }
                        }
                    }
                }

                if(optMes.isSelected()){                                       
//                    //Calcular totales.
                    objTblTot.setValueAt("" + dblTotMen, 0, INT_TBL_DAT_SAL_MEN);
                    objTblTot.setValueAt("" + dblTotAcu, 0, INT_TBL_DAT_SAL_ACU);
                     
                    if(quitarNivCta()){
                    }
                    
                    if(quitarCtasSaldoCero()){
                        
                    }
                }
                
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
                
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

 
    
    
    private boolean calculaTotales(){
        boolean blnRes=true;
        BigDecimal bdeSalMen=new BigDecimal("0");
        BigDecimal bdeSalAcu=new BigDecimal("0");
        int intNivCta=-1;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intNivCta=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString());
                if(intNivCta==1){
                    bdeSalMen=bdeSalMen.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString())  );
                    bdeSalAcu=bdeSalAcu.add(new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString())  );
                }
            }
            
            objTblTot.setValueAt("" + bdeSalMen, 0, INT_TBL_DAT_SAL_MEN);
            objTblTot.setValueAt("" + bdeSalAcu, 0, INT_TBL_DAT_SAL_ACU);


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

                    



    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta funcián muestra un mensaje de error al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
 
    

  
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread{
        public void run(){
            if (!cargarDetReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
           
            //Establecer el foco en el JTable sÃ¡lo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIExportar extends Thread
    {
        public void run()
        {
             
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
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Cádigo del Sistema";
                    break;
                case INT_TBL_DAT_NIV_CTA:
                    strMsg="Nivel de la cuenta";
                    break;
                case INT_TBL_DAT_PAD_CTA:
                    strMsg="Cuenta padre";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Cádigo de la cuenta";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_DAT_SAL_MEN:
                    strMsg="Saldo mensual";
                    break;
                case INT_TBL_DAT_SAL_ACU:
                    strMsg="Saldo acumulado";
                    break;
                default:
                    strMsg=null;
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
 
    

    public boolean imprimir() {                
        int intNumTotReg, intNiv, i, j;
        double dblSalMen, dblSalAcu, dblTotMen, dblTotAcu;
        boolean blnRes=true;
        String strNivRep="";
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
        try
        {
            if(conIns!=null){
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                //INICIO: Mejorar esto:
                String strFecDes, strFecHas;
                //FIN: Mejorar esto.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    //Obtener los datos del "Grupo".
                    intNumTotReg=0;
                }
                else{                                                                              
                         //Balance General.
                        JasperDesign jasperDesign = JRXmlLoader.load("C://Zafiro//Reportes_impresos//RptZafCon13.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);                                                

                        // Second, create a map of parameters to pass to the report.
                        Map parameters = new HashMap();
                        parameters.put("codEmp", ""+objParSis.getCodigoEmpresa());
                        parameters.put("nomEmp", ""+objParSis.getNombreEmpresa());
                        parameters.put("fecCor", ""+strFecCor);
                        parameters.put("cboNiv", ""+(cboNiv.getSelectedIndex()+1));                        
                          if (objParSis.getCodigoMenu()==342){
                              parameters.put("nomRep", "BALANCE GENERAL");
                              parameters.put("tipRep", "1");//PARA BALANCE GENERAL EL DATO ES UNO(1)
                              parameters.put("titTot", "Diferencias");
                          }
                          else{
                              parameters.put("nomRep", "ESTADO DE RESULTADOS");
                              parameters.put("tipRep", "0");//PARA ESTADO DE RESULTADOS EL DATO ES CERO(0)
                              parameters.put("titTot", "Utilidad/Párdida");
                              parameters.put("aniIniCor", "" + strFecCorIni );
                          }
                        parameters.put("aniCor", "" + (cboAnoCor.getSelectedItem()));
                        parameters.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());
                        
                        
                        if(chkSalCer.isSelected()){
                            parameters.put("chkSel", "1");                            
                        }
                        else
                            parameters.put("chkSel", "0");
                         
                        
                        saldosTotales();
                        
                        parameters.put("salTotMen", new Double(dblSalMenImp));
                        parameters.put("salTotAcu", new Double(dblSalAcuImp));                                                
                        
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);

                        //para imprimir
                        //JasperManager.printReport(report, true);                                                                 
                        JasperViewer.viewReport(report, false);                          

                }                
            }
        }
        catch (JRException e)
        {
            System.out.println("Excepcián: " + e.toString());
        }
           
        }

        catch(SQLException ex)
        {
            System.out.println("Error al conectarse a la base");
        }                        
        return true;
     }       
    
    
    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {

            String strRutRpt, strNomRpt;
            int i, intNumTotRpt;
            boolean blnRes=true;
            String strSQLRep="", strSQLSubRep="";
            String strFecHorSer="";
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();

            Connection conIns;
            String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
            String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
            try
            {
                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try
                {
                    if(conIns!=null)
                    {
                        objRptSis.cargarListadoReportes();
                        objRptSis.show();
                        if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                        {
                            //Obtener la fecha y hora del servidor.
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            if (datFecAux==null)
                                return false;
                            strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                            datFecAux=null;

                            intNumTotRpt=objRptSis.getNumeroTotalReportes();
                            for (i=0;i<intNumTotRpt;i++)
                            {
                                if (objRptSis.isReporteSeleccionado(i))
                                {
                                    switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                    {
                                        case 155://BG
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                mapPar.put("codEmp", new Integer(""+objParSis.getCodigoEmpresa()));
                                                mapPar.put("nomEmp", ""+objParSis.getNombreEmpresa());
                                                mapPar.put("fecCor", new Integer(""+strFecCor));
                                                mapPar.put("cboNiv", new Integer(""+(cboNiv.getSelectedIndex()+1)));                        
                                                mapPar.put("nomRep", "BALANCE GENERAL");
                                                mapPar.put("tipRep", new Integer("1"));//PARA BALANCE GENERAL EL DATO ES UNO(1)
                                                mapPar.put("titTot", "Diferencias");
                                                mapPar.put("aniCor", new Integer("" + (cboAnoCor.getSelectedItem())));
                                                mapPar.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());
                                                if(chkSalCer.isSelected()){
                                                    mapPar.put("chkSel", new Integer("1"));                            
                                                }
                                                else
                                                    mapPar.put("chkSel", new Integer("0"));
                                                
                                                
                                                 
                                                saldosTotales();
                                                
                                                
                                                mapPar.put("salTotMen", new BigDecimal(dblSalMenImp));
                                                mapPar.put("salTotAcu", new BigDecimal(dblSalAcuImp));   
                                                mapPar.put("strCamAudRpt", "" + objParSis.getNombreUsuario() + "   -   " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                        case 156://ER
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                mapPar.put("codEmp", new Integer(""+objParSis.getCodigoEmpresa()));
                                                mapPar.put("nomEmp", ""+objParSis.getNombreEmpresa());
                                                mapPar.put("fecCor", new Integer(""+strFecCor));
                                                mapPar.put("cboNiv", new Integer(""+(cboNiv.getSelectedIndex()+1)));                        
                                                mapPar.put("nomRep", "ESTADO DE RESULTADOS");
                                                mapPar.put("tipRep", new Integer("0"));//PARA ESTADO DE RESULTADOS EL DATO ES CERO(0)
                                                mapPar.put("titTot", "Utilidad/Párdida");
                                                mapPar.put("aniIniCor", new Integer("" + strFecCorIni ));
                                                
                                                mapPar.put("aniCor", new Integer("" + (cboAnoCor.getSelectedItem())));
                                                mapPar.put("nomMes", "" + cboMesCor.getSelectedItem().toString().toUpperCase());

                                                if(chkSalCer.isSelected()){
                                                    mapPar.put("chkSel", new Integer("1"));                            
                                                }
                                                else
                                                    mapPar.put("chkSel", new Integer("0"));
                                                
                                                 
                                                saldosTotales();
                                                
                                                mapPar.put("salTotMen", new BigDecimal(dblSalMenImp));
                                                mapPar.put("salTotAcu", new BigDecimal(dblSalAcuImp));   
                                                mapPar.put("strCamAudRpt", "" + objParSis.getNombreUsuario() + "   -   " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    conIns.close();
                    conIns=null;
                }
                catch (Exception e)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
            }
            catch(SQLException ex)
            {
                System.out.println("Error al conectarse a la base");
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, ex);
            }
            return blnRes;
    }
    
    
    

    private void saldosTotales()
    {
        int intNumTotReg, intNiv, i, j;
        double dblSalMen, dblSalAcu, dblTotMen=0.00, dblTotAcu=0.00;
        //String strFecCor;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                String strFecCorIni="" + cboAnoCor.getSelectedItem() + "01";
                //INICIO: Mejorar esto:
                String strFecDes, strFecHas;
//                if (cboMesCor.getSelectedIndex()>=8)
//                {
//                    objUti.remayorizar(this, objParSis, "2006/09/01", "2006/10/31", "yyyy/MM/dd");
//                }
                //FIN: Mejorar esto.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    intNumTotReg=0;
                }
                else
                {                                        
                    if(chkSalCer.isSelected()){
                        strAux=" HAVING SUM(b1.nd_salCta)<>0";
                    }
                    else
                        strAux="";
                    
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    //Armar la sentencia SQL.
                    strSQL="";
                    //strSQL+="SELECT a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                    strSQL+="SELECT a1.co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar, a2.nd_salCta AS nd_salMen, a3.nd_salCta AS nd_salAcu";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta";
                    strSQL+=" FROM tbm_salCta AS b1";
                    strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (objParSis.getCodigoMenu()==342){
                        strSQL+=" AND b1.co_per<=" + strFecCor;
                    }
                    else{
                        strSQL+=" AND b1.co_per>=" + strFecCorIni + " AND b1.co_per<=" + strFecCor;
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_cta";
                    strSQL+=strAux;
                    strSQL+=" ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_per=" + strFecCor;
                    //strSQL+=" AND a1.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                    strSQL+=" AND a2.ne_niv<=" + (cboNiv.getSelectedIndex()+1);
                    
                    if (objParSis.getCodigoMenu()==342)
                    {
                        //Balance General.
                        strSQL+=" AND a1.tx_niv1 IN ('1', '2', '3')";
                    }
                    else
                    {
                        //Estado de Resultados.
                        strSQL+=" AND a1.tx_niv1 NOT IN ('1', '2', '3')";
                    }
                    //strSQL+=" ORDER BY a1.tx_codCta";
                    strSQL+=" ORDER BY a2.tx_codCta";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                dblTotMen=0;
                dblTotAcu=0;
                while (rst.next())
                {
                    intNiv=rst.getInt("ne_niv");
                    //Generar la tabulacián para el campo "tx_desLar".
                    strAux="";
                    for (j=1; j<intNiv; j++)
                        strAux+="        ";
                    dblSalMen=rst.getDouble("nd_salMen");
                    dblSalAcu=rst.getDouble("nd_salAcu");
                    if (intNiv==1)
                    {
                        dblTotMen+=dblSalMen;
                        dblTotAcu+=dblSalAcu;
                    }
    
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Calcular totales.
                dblSalMenImp=dblTotMen;
                dblSalAcuImp=dblTotAcu;
                
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
    }    
    
      
    
    
    
    private boolean cargarAnioCreados(){
        boolean blnRes=true;
        int intUltAni=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.YEAR1 ";
                strSQL+=" FROM ( ";
                strSQL+="       SELECT DISTINCT YEAR1 FROM TUVAL.dbo.GL10110  ";
                strSQL+="       UNION ";
                strSQL+="       SELECT DISTINCT YEAR1 FROM TUVAL.dbo.GL10111  ";
                strSQL+=" ) as a1  ";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    cboAnoCor.addItem("" + rst.getString("YEAR1"));
                    intUltAni++;
                }
                cboAnoCor.setSelectedIndex(intUltAni);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }


    private String getFechaInicioMesCorte(String fechaCorte){
        Connection conFecCor;
        Statement stmFecCor;
        ResultSet rstFecCor;
        String strFecIniMesCor="";

        try{
            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));

            cal.set(java.util.Calendar.DATE, cal.getActualMinimum(cal.DAY_OF_MONTH)    );
            strFecIniMesCor=objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy");


        }
//        catch (java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecIniMesCor;

    }



    private String getPeriodoAnterior(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
//            java.util.Calendar cal=java.util.Calendar.getInstance();
//            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
//            cal.set(java.util.Calendar.MONTH,cal.get((cal.MONTH))-1);
//            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));
//
//            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
//            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

//            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );

            

            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
            //cal.set(java.util.Calendar.MONTH, cal.get((cal.MONTH))-1);

            cal.add(java.util.Calendar.MONTH, -1);

            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));

            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<=9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );


        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }


    private String getPeriodoInicialEstadoResultados(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
            strPerAnt="" + (intFecPerAnt[2] + "01" );
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }


    private String getPeriodoFinalEstadoResultados(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<=9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }
 


    private boolean generaSaldosCuentasPadres(){
        boolean blnRes=true;
        String strLin="";
        //VARIABLES PARA EL DETALLE(O SEA QUIENES SE VAN A IR SUMANDO)
        int intCodCtaPadDetIni=-1;
        int intCodCtaPadDetFin=-1;
        BigDecimal bdeValMen=new BigDecimal("0");
        BigDecimal bdeValAcu=new BigDecimal("0");
        BigDecimal bdeSumMen=new BigDecimal("0");
        BigDecimal bdeSumAcu=new BigDecimal("0");

        //VARIABLES DE CABECERA(O SEA DE LA CUENTA DONDE SE VA A COLOCAR LO QUE SE SUMO EN LAS DE DETALLE)
        int intCodCtaCab=-1;
        int intPriNiv=0;//captura cual es el primer nivel(este nivel es el q se busca.
        int intNivCta=0;//contiene la cuenta padre que es la q se buscara para ir sumando.

        try{
            
            for(int p=intNIVELES;p>0;p--){
//                System.out.println("p: " + p);
                //for(int p=6;p>0;p--){
                for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                    intNivCta=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString());
//                    System.out.println("intNivCta: " + intNivCta);
                    if(intNivCta==p){
                        if(intPriNiv==0){
                            intCodCtaPadDetIni=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());
//                            System.out.println("intCodCtaPadDetIni: " + intCodCtaPadDetIni);
                            intPriNiv++;
//                            System.out.println("intPriNiv: " + intPriNiv);
                        }
                        intCodCtaPadDetFin=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PAD_CTA).toString());
//                        System.out.println("intCodCtaPadDetFin: " + intCodCtaPadDetFin);
                        
                        if(i>0){
                            intCodCtaCab=Integer.parseInt(objTblMod.getValueAt((i-1), INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt((i-1), INT_TBL_DAT_COD_SIS).toString());
//                            System.out.println("if-intCodCtaCab: " + intCodCtaCab);
                        }
                        else{
                            intCodCtaCab=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString());
//                            System.out.println("else-intCodCtaCab: " + intCodCtaCab);
                        }
                        
                        if(intCodCtaPadDetIni==intCodCtaCab){
                            bdeValMen=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString().toString());
//                            System.out.println("bdeValMen: " + bdeValMen);
                            bdeSumMen=bdeSumMen.add(bdeValMen);
//                            System.out.println("bdeSumMen: " + bdeSumMen);
                            bdeValAcu=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString().toString());
//                            System.out.println("bdeValAcu: " + bdeValAcu);
                            bdeSumAcu=bdeSumAcu.add(bdeValAcu);
//                            System.out.println("bdeSumAcu: " + bdeSumAcu);

                            objTblMod.setValueAt(bdeSumMen, (i-1), INT_TBL_DAT_SAL_MEN);
                            objTblMod.setValueAt(bdeSumAcu, (i-1), INT_TBL_DAT_SAL_ACU);
                            intPriNiv=0;
                            bdeSumMen=new BigDecimal("0");
                            bdeSumAcu=new BigDecimal("0");
                        }
                        else{
                            if(intCodCtaPadDetIni==intCodCtaPadDetFin){
                                bdeValMen=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString().toString());
//                                System.out.println("else-bdeValMen: " + bdeValMen);
                                bdeSumMen=bdeSumMen.add(bdeValMen);
//                                System.out.println("else-bdeSumMen: " + bdeSumMen);
                                bdeValAcu=new BigDecimal("" + objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString()==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString().toString());
//                                System.out.println("else-bdeValAcu: " + bdeValAcu);
                                bdeSumAcu=bdeSumAcu.add(bdeValAcu);
//                                System.out.println("else-bdeSumAcu: " + bdeSumAcu);
                            }
                        }
                    }


                }
            }

        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    private boolean quitarCtasSaldoCero(){
        boolean blnRes=true;
        BigDecimal bdeSumMen=new BigDecimal("0");
        BigDecimal bdeSumAcu=new BigDecimal("0");
        BigDecimal bdeValCer=new BigDecimal("0");
        try{
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int i=(objTblMod.getRowCountTrue()-1);i>=0;i--){
                
                if(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString())==298)
//                    System.out.println("codigoCta: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS));
                
                
                bdeSumMen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_MEN).toString());
                if(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString())==298)
//                    System.out.println("bdeSumMen: " + bdeSumMen);
                bdeSumAcu=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU)==null?"0":objTblMod.getValueAt(i, INT_TBL_DAT_SAL_ACU).toString());
                if(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString())==298)
//                    System.out.println("bdeSumAcu: " + bdeSumAcu);
                
                if( (bdeSumMen.compareTo(bdeValCer)==0) && (bdeSumAcu.compareTo(bdeValCer)==0) ){
                    objTblMod.removeRow(i);
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

        }catch (Exception e){
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
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
//                case 0: //Botón "Imprimir".
//                    generarRpt(0);
//                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }

    /**
     * Limpia el Jtable.
     * Esta función es utilizada cada vez que se elija formato mercado de valores.
     */
    private void limpiarJtableDat()
    {
        vecDat.clear();
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
    }
     
    
    /**
     * Función que permite quitar los registros por niveles según lo seleccionado en el comboBox
     * @return true Si se pudo realizar la operación 
     * <BR> false Caso contrario
     */
    private boolean quitarNivCta(){
        boolean blnRes=true;
        int intNivCtaSelUsr=-1;
        int intNivCtaMod=-1;
        try{
            System.out.println("quitarNivCta");
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            intNivCtaSelUsr=Integer.parseInt(cboNiv.getSelectedItem().toString());
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                intNivCtaMod=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NIV_CTA).toString()));
                if(intNivCtaMod>intNivCtaSelUsr){
                    objTblMod.removeRow(i);
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
}