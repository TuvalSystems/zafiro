/*
 * ZafCom88.java
 *
 * Created on 29 de Abril de 2023, 10:10 PM
 */
package Compras.ZafCom88;
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
import Librerias.ZafVenCon.ZafVenConItm01;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import javafx.scene.paint.Color;
/**
 *
 * @author  JoseMario 
 */
public class ZafCom88 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM_GP=1;                  
    static final int INT_TBL_DAT_COD_ITM_GEN=2;                 
    static final int INT_TBL_DAT_COD_3L=3;                    
    static final int INT_TBL_DAT_NOM_ITM_GP=4;                
    static final int INT_TBL_DAT_UNI_MED=5;                    
    static final int INT_TBL_DAT_STK_IMP=6;                     
    
    static final int INT_TBL_DAT_STK_INM=7;                    
    static final int INT_TBL_DAT_MIN_INM=8;                   
    static final int INT_TBL_DAT_MAX_INM=9;                    
    
    static final int INT_TBL_DAT_STK_CAL=10;                    
    static final int INT_TBL_DAT_MIN_CAL=11;                   
    static final int INT_TBL_DAT_MAX_CAL=12;                   
    
    static final int INT_TBL_DAT_STK_DAU=13;         
    static final int INT_TBL_DAT_MIN_DAU=14;         
    static final int INT_TBL_DAT_MAX_DAU=15;         
    
    static final int INT_TBL_DAT_STK_DUR=16;         
    static final int INT_TBL_DAT_MIN_DUR=17;         
    static final int INT_TBL_DAT_MAX_DUR=18;
    
    static final int INT_TBL_DAT_STK_UION=19;         
    static final int INT_TBL_DAT_MIN_UION=20;         
    static final int INT_TBL_DAT_MAX_UION=21; 
    
    static final int INT_TBL_DAT_STK_UIOS=22;         
    static final int INT_TBL_DAT_MIN_UIOS=23;         
    static final int INT_TBL_DAT_MAX_UIOS=24;
    
    static final int INT_TBL_DAT_STK_MAN=25;         
    static final int INT_TBL_DAT_MIN_MAN=26;         
    static final int INT_TBL_DAT_MAX_MAN=27;
    
    static final int INT_TBL_DAT_STK_DGO=28;         
    static final int INT_TBL_DAT_MIN_DGO=29;         
    static final int INT_TBL_DAT_MAX_DGO=30;
    
    static final int INT_TBL_DAT_STK_CUE=31;         
    static final int INT_TBL_DAT_MIN_CUE=32;         
    static final int INT_TBL_DAT_MAX_CUE=33;
     
    
    //Variables
     
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblStkCal, objTblCelRenLblStkViaDau, objTblCelRenLblStkDur, objTblCelRenLblStkUioN, 
                            objTblCelRenLblStkUioS, objTblCelRenLblStkMan, objTblCelRenLblStkStoDom, objTblCelRenLblStkCue;
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Clientes".
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenConItmGP01 objVenConItm01;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux, strVersion="v 0.03";
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        //Para el stock de cada bodega.
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;
    
    

    /** Crea una nueva instancia de la clase ZafVen04. */
    public ZafCom88(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
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
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        cmbMinimo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbMaximo = new javax.swing.JComboBox<>();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 156, 400, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 136, 400, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
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

        panFil.add(panLoc);
        panLoc.setBounds(0, 0, 640, 122);
        panLoc.getAccessibleContext().setAccessibleName("Listado de bodegas");

        cmbMinimo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "100", "90", "80", "70", "60", "50", "40", "30", "20" }));
        cmbMinimo.setSelectedItem("50");
        panFil.add(cmbMinimo);
        cmbMinimo.setBounds(200, 220, 170, 26);

        jLabel1.setText("% Resaltar mínimo:");
        panFil.add(jLabel1);
        jLabel1.setBounds(20, 220, 200, 20);

        jLabel2.setText("% Resaltar máximo:");
        panFil.add(jLabel2);
        jLabel2.setBounds(20, 250, 170, 20);

        cmbMaximo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "90", "80", "70", "60", "50", "40", "30", "20", "10", "Más del Máx" }));
        cmbMaximo.setSelectedItem("Más del Máx");
        panFil.add(cmbMaximo);
        cmbMaximo.setBounds(200, 250, 170, 26);

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

        jLabel3.setText("DynamicsGP");
        panBot.add(jLabel3);

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
        if (true/*isCamVal()*/)
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

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            objVenConItm01.limpiar();
        }
    }//GEN-LAST:event_optTodItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JComboBox<String> cmbMaximo;
    private javax.swing.JComboBox<String> cmbMinimo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafVenConItmGP01:
            objVenConItm01=new ZafVenConItmGP01(objParSis, "a1.", optFil);
            panFil.add(objVenConItm01);
            objVenConItm01.setBounds(24, 180, 636, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
             
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();           
            cargarBodegas();
            panLoc.setVisible(false);
           
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
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_EMP,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_EMP,"Bodega");
      
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
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(221);
             
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);           
//            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
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
    
    private double dblConfMin = 0.00;
    private double dblConfMax = 0.00;
    
    
    /**
     * Para saber lo que estan solicitando segun lo cargado
     * 
     * @return 
     */
    private boolean configurarArreglos(){
        boolean blnRes=false;
        try{
            switch(cmbMinimo.getSelectedItem().toString()){
                case "100": dblConfMin=100; break; 
                case "90": dblConfMin=90; break;
                case "80": dblConfMin=80; break; 
                case "70": dblConfMin=70; break; 
                case "60": dblConfMin=60; break; 
                case "50": dblConfMin=50; break; 
                case "40": dblConfMin=40; break; 
                case "30": dblConfMin=30; break; 
                case "20": dblConfMin=20; break; 
            }
            switch(cmbMaximo.getSelectedItem().toString()){
                case "Más del Máximo": dblConfMax=0; break;
                case "90": dblConfMax=90; break;
                case "80": dblConfMax=80; break; 
                case "70": dblConfMax=70; break; 
                case "60": dblConfMax=60; break; 
                case "50": dblConfMax=50; break; 
                case "40": dblConfMax=40; break; 
                case "30": dblConfMax=30; break; 
                case "20": dblConfMax=20; break; 
                case "10": dblConfMax=10; break; 
                
            }
        }
        catch (Exception e){
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
            vecCab.add(INT_TBL_DAT_COD_ITM_GP,"Item");
            vecCab.add(INT_TBL_DAT_COD_ITM_GEN,"Cód.Orden");
            vecCab.add(INT_TBL_DAT_COD_3L,"3L");
            vecCab.add(INT_TBL_DAT_NOM_ITM_GP,"Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med");
            vecCab.add(INT_TBL_DAT_STK_IMP,"Stock");
            vecCab.add(INT_TBL_DAT_STK_INM,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_INM,"Min");
            vecCab.add(INT_TBL_DAT_MAX_INM,"Max");
            vecCab.add(INT_TBL_DAT_STK_CAL,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_CAL,"Min");
            vecCab.add(INT_TBL_DAT_MAX_CAL,"Max");
            vecCab.add(INT_TBL_DAT_STK_DAU,"Stock");
            
            vecCab.add(INT_TBL_DAT_MIN_DAU,"Min");
            vecCab.add(INT_TBL_DAT_MAX_DAU,"Max");
            vecCab.add(INT_TBL_DAT_STK_DUR,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_DUR,"Min");
            vecCab.add(INT_TBL_DAT_MAX_DUR,"Max");
            vecCab.add(INT_TBL_DAT_STK_UION,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_UION,"Min");
            vecCab.add(INT_TBL_DAT_MAX_UION,"Max");
            vecCab.add(INT_TBL_DAT_STK_UIOS,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_UIOS,"Min");
            vecCab.add(INT_TBL_DAT_MAX_UIOS,"Max");
            vecCab.add(INT_TBL_DAT_STK_MAN,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_MAN,"Min");
            vecCab.add(INT_TBL_DAT_MAX_MAN,"Max");
            vecCab.add(INT_TBL_DAT_STK_DGO,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_DGO,"Min");
            vecCab.add(INT_TBL_DAT_MAX_DGO,"Max");
            vecCab.add(INT_TBL_DAT_STK_CUE,"Stock");
            vecCab.add(INT_TBL_DAT_MIN_CUE,"Min");
            vecCab.add(INT_TBL_DAT_MAX_CUE,"Max");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM_GP).setPreferredWidth(140);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_IMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_INM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_INM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_INM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_CAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_CAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_CAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_DAU).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_DAU).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DAU).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_DUR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_DUR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DUR).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_UION).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_UION).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_UION).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_UIOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_UIOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_UIOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_MAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_MAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_MAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_DGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_DGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DGO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_STK_CUE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MIN_CUE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MAX_CUE).setPreferredWidth(50);
 
  
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
//            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GP, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GEN, tblDat);
//            switch (objParSis.getCodigoMenu())
//            {
//                case 226: //Listado de ventas por item...
//                    break;
//                case 229: //Listado de ventas por item (Unidades)...
//                default:
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MIN_INM, tblDat);
//                    //Ficha "Reporte": Tabla->Mostrar "Venta total"
//                    if (objPerUsr.isOpcionEnabled(3921))
//                    {
//                        tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(285);
//                    }
//                    else
//                    {
//                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAX_INM, tblDat);
//                        tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(365);
//                    }
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_CAL, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MIN_CAL, tblDat);
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAX_CAL, tblDat);
//                    break;
//            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
  
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_INM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_INM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_INM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_CAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_CAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_DAU).setCellRenderer(objTblCelRenLbl);
             tcmAux.getColumn(INT_TBL_DAT_MIN_DAU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DAU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_DUR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_DUR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DUR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_UION).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_UION).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_UION).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_UIOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_UIOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_UIOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_MAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_MAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_MAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_DGO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_DGO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_DGO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_CUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MIN_CUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_MAX_CUE).setCellRenderer(objTblCelRenLbl);
            
            
            
            objTblCelRenLblStkCal=new ZafTblCelRenLbl();
            objTblCelRenLblStkCal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkCal.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkCal.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CAL).setCellRenderer(objTblCelRenLblStkCal);
            objTblCelRenLblStkCal.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCal.getRowRender(), INT_TBL_DAT_STK_CAL)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCal.getRowRender(), INT_TBL_DAT_MIN_CAL)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCal.getRowRender(), INT_TBL_DAT_MAX_CAL)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkCal.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkCal.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkCal.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkCal.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkViaDau=new ZafTblCelRenLbl();
            objTblCelRenLblStkViaDau.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkViaDau.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkViaDau.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_DAU).setCellRenderer(objTblCelRenLblStkViaDau);
            objTblCelRenLblStkViaDau.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkViaDau.getRowRender(), INT_TBL_DAT_STK_DAU)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkViaDau.getRowRender(), INT_TBL_DAT_MIN_DAU)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkViaDau.getRowRender(), INT_TBL_DAT_MAX_DAU)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkViaDau.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkViaDau.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkViaDau.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkViaDau.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            
            objTblCelRenLblStkDur=new ZafTblCelRenLbl();
            objTblCelRenLblStkDur.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkDur.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkDur.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_DUR).setCellRenderer(objTblCelRenLblStkDur);
            objTblCelRenLblStkDur.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkDur.getRowRender(), INT_TBL_DAT_STK_DUR)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkDur.getRowRender(), INT_TBL_DAT_MIN_DUR)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkDur.getRowRender(), INT_TBL_DAT_MAX_DUR)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkDur.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkDur.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkDur.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkDur.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkUioN=new ZafTblCelRenLbl();
            objTblCelRenLblStkUioN.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkUioN.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkUioN.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_UION).setCellRenderer(objTblCelRenLblStkUioN);
            objTblCelRenLblStkUioN.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioN.getRowRender(), INT_TBL_DAT_STK_UION)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioN.getRowRender(), INT_TBL_DAT_MIN_UION)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioN.getRowRender(), INT_TBL_DAT_MAX_UION)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkUioN.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkUioN.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkUioN.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkUioN.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkUioS=new ZafTblCelRenLbl();
            objTblCelRenLblStkUioS.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkUioS.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkUioS.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_UIOS).setCellRenderer(objTblCelRenLblStkUioS);
            objTblCelRenLblStkUioS.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioS.getRowRender(), INT_TBL_DAT_STK_UIOS)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioS.getRowRender(), INT_TBL_DAT_MIN_UIOS)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkUioS.getRowRender(), INT_TBL_DAT_MAX_UIOS)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkUioS.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkUioS.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkUioS.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkUioS.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkMan=new ZafTblCelRenLbl();
            objTblCelRenLblStkMan.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkMan.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkMan.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_MAN).setCellRenderer(objTblCelRenLblStkMan);
            objTblCelRenLblStkMan.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkMan.getRowRender(), INT_TBL_DAT_STK_MAN)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkMan.getRowRender(), INT_TBL_DAT_MIN_MAN)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkMan.getRowRender(), INT_TBL_DAT_MAX_MAN)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkMan.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkMan.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkMan.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkMan.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkStoDom=new ZafTblCelRenLbl();
            objTblCelRenLblStkStoDom.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkStoDom.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkStoDom.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_DGO).setCellRenderer(objTblCelRenLblStkStoDom);
            objTblCelRenLblStkStoDom.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkStoDom.getRowRender(), INT_TBL_DAT_STK_DGO)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkStoDom.getRowRender(), INT_TBL_DAT_MIN_DGO)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkStoDom.getRowRender(), INT_TBL_DAT_MAX_DGO)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkStoDom.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkStoDom.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkStoDom.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkStoDom.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblStkCue=new ZafTblCelRenLbl();
            objTblCelRenLblStkCue.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStkCue.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblStkCue.setFormatoNumerico("###,###",false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CUE).setCellRenderer(objTblCelRenLblStkCue);
            objTblCelRenLblStkCue.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    Double dblStock=0.00, dblMinimo=0.00, dblMaximo=0.00;
                    dblStock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCue.getRowRender(), INT_TBL_DAT_STK_CUE)) ;
                    dblMinimo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCue.getRowRender(), INT_TBL_DAT_MIN_CUE)) ;
                    dblMaximo= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblStkCue.getRowRender(), INT_TBL_DAT_MAX_CUE)) ;
                    if (dblStock <= 0){ 
                        objTblCelRenLblStkCue.setBackground(java.awt.Color.orange);
                    }
                    else if(dblStock > (dblMaximo + (  dblMaximo * (  dblConfMax /100  )   ) )){
                        objTblCelRenLblStkCue.setBackground(java.awt.Color.red);
                    }
                    else if(dblStock <  (  dblMinimo * (  dblConfMin /100  )   ) ){
                        objTblCelRenLblStkCue.setBackground(java.awt.Color.PINK);
                    }
                    else{
                        objTblCelRenLblStkCue.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            
            //IMPORT
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            
            ZafTblHeaColGrp objTblHeaColGrpImp=new ZafTblHeaColGrp("IMPORT");
            objTblHeaColGrpImp.setHeight(16);
            objTblHeaColGrpImp.add(tcmAux.getColumn(INT_TBL_DAT_STK_IMP)); 
            
            ZafTblHeaColGrp objTblHeaColGrpInm=new ZafTblHeaColGrp("INMACONSA");
            objTblHeaColGrpInm.setHeight(16);
            objTblHeaColGrpInm.add(tcmAux.getColumn(INT_TBL_DAT_STK_INM)); 
            objTblHeaColGrpInm.add(tcmAux.getColumn(INT_TBL_DAT_MIN_INM)); 
            objTblHeaColGrpInm.add(tcmAux.getColumn(INT_TBL_DAT_MAX_INM)); 
            
            ZafTblHeaColGrp objTblHeaColGrpCal=new ZafTblHeaColGrp("CALIFORNIA");
            objTblHeaColGrpCal.setHeight(16);
            objTblHeaColGrpCal.add(tcmAux.getColumn(INT_TBL_DAT_STK_CAL)); 
            objTblHeaColGrpCal.add(tcmAux.getColumn(INT_TBL_DAT_MIN_CAL)); 
            objTblHeaColGrpCal.add(tcmAux.getColumn(INT_TBL_DAT_MAX_CAL));
            
            ZafTblHeaColGrp objTblHeaColGrpDau=new ZafTblHeaColGrp("VIA_DAULE");
            objTblHeaColGrpDau.setHeight(16);
            objTblHeaColGrpDau.add(tcmAux.getColumn(INT_TBL_DAT_STK_DAU)); 
            objTblHeaColGrpDau.add(tcmAux.getColumn(INT_TBL_DAT_MIN_DAU)); 
            objTblHeaColGrpDau.add(tcmAux.getColumn(INT_TBL_DAT_MAX_DAU));
            
            ZafTblHeaColGrp objTblHeaColGrpDur=new ZafTblHeaColGrp("DURAN");
            objTblHeaColGrpDur.setHeight(16);
            objTblHeaColGrpDur.add(tcmAux.getColumn(INT_TBL_DAT_STK_DUR)); 
            objTblHeaColGrpDur.add(tcmAux.getColumn(INT_TBL_DAT_MIN_DUR)); 
            objTblHeaColGrpDur.add(tcmAux.getColumn(INT_TBL_DAT_MAX_DUR));
            
            ZafTblHeaColGrp objTblHeaColGrpUION=new ZafTblHeaColGrp("QUITO_N");
            objTblHeaColGrpUION.setHeight(16);
            objTblHeaColGrpUION.add(tcmAux.getColumn(INT_TBL_DAT_STK_UION)); 
            objTblHeaColGrpUION.add(tcmAux.getColumn(INT_TBL_DAT_MIN_UION)); 
            objTblHeaColGrpUION.add(tcmAux.getColumn(INT_TBL_DAT_MAX_UION));
            
            ZafTblHeaColGrp objTblHeaColGrpUIOS=new ZafTblHeaColGrp("QUITO_S");
            objTblHeaColGrpUIOS.setHeight(16);
            objTblHeaColGrpUIOS.add(tcmAux.getColumn(INT_TBL_DAT_STK_UIOS)); 
            objTblHeaColGrpUIOS.add(tcmAux.getColumn(INT_TBL_DAT_MIN_UIOS)); 
            objTblHeaColGrpUIOS.add(tcmAux.getColumn(INT_TBL_DAT_MAX_UIOS));
            
            ZafTblHeaColGrp objTblHeaColGrpMan=new ZafTblHeaColGrp("MANTA");
            objTblHeaColGrpMan.setHeight(16);
            objTblHeaColGrpMan.add(tcmAux.getColumn(INT_TBL_DAT_STK_MAN)); 
            objTblHeaColGrpMan.add(tcmAux.getColumn(INT_TBL_DAT_MIN_MAN)); 
            objTblHeaColGrpMan.add(tcmAux.getColumn(INT_TBL_DAT_MAX_MAN));
            
            ZafTblHeaColGrp objTblHeaColGrpSTD=new ZafTblHeaColGrp("STO_DGO");
            objTblHeaColGrpSTD.setHeight(16);
            objTblHeaColGrpSTD.add(tcmAux.getColumn(INT_TBL_DAT_STK_DGO)); 
            objTblHeaColGrpSTD.add(tcmAux.getColumn(INT_TBL_DAT_MIN_DGO)); 
            objTblHeaColGrpSTD.add(tcmAux.getColumn(INT_TBL_DAT_MAX_DGO));
            
            ZafTblHeaColGrp objTblHeaColGrpCue=new ZafTblHeaColGrp("CUENCA_2");
            objTblHeaColGrpCue.setHeight(16);
            objTblHeaColGrpCue.add(tcmAux.getColumn(INT_TBL_DAT_STK_CUE)); 
            objTblHeaColGrpCue.add(tcmAux.getColumn(INT_TBL_DAT_MIN_CUE)); 
            objTblHeaColGrpCue.add(tcmAux.getColumn(INT_TBL_DAT_MAX_CUE));
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpImp);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpInm);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCal);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDau);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDur);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpUION);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpUIOS);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMan);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSTD);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCue);
            
            
//            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
//            tcmAux.getColumn(INT_TBL_DAT_STK_INM).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_CAL).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_DAU).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_DUR).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_UION).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_UIOS).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_MAN).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_DGO).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_STK_CUE).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl = null;
            
            
            
            objTblCelRenLbl=null;
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
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBodegas()
    {
        int intCodEmp;
        boolean blnRes=true;
        java.sql.Connection conLoc;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLoc!=null)
            {
                stm=conLoc.createStatement();
                 
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT TRIM(locncode ) as CodigoGP, TRIM(LOCNDSCR) as NombreLargo \n";
                strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV40700 as a1   \n";
                strSQL+=" INNER JOIN tbm_bod as a2 ON (a2.co_emp="+objParSis.getCodigoEmpresa()+" AND  TRIM(a1.locncode)=a2.tx_desCor  ) \n";
                strSQL+=" WHERE a1.INACTIVE = 0  AND a1.locncode <> '' AND a2.st_bodRep = 'S' \n";
                System.out.println("... " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("CodigoGP"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("NombreLargo")); 
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                conLoc.close();
                rst=null;
                stm=null;
                conLoc=null;
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
        int intNumFilTblBod, intNumDec, i, j;
        BigDecimal bgdUniVen, bgdVen, bgdCos, bgdPesItmKgr;
        boolean blnRes=true;
        java.sql.Connection conLoc;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            intNumDec=objParSis.getDecimalesBaseDatos();
            lblMsgSis.setText("Obteniendo datos...");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLoc!=null)
            {
                stm=conLoc.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
                //Obtener los locales a consultar.
                intNumFilTblBod=objTblModLoc.getRowCountTrue();
                i=0;
//                strAux="";
//                for (j=0; j<intNumFilTblBod; j++)
//                {
//                    if (objTblModLoc.isChecked(j, INT_TBL_BOD_CHK))
//                    {
//                        if (i==0)
//                            strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_BOD_COD_EMP) + ")";
//                        else
//                            strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_BOD_COD_EMP) + ")";
//                        i++;
//                    }
//                }
//                if (!strAux.equals(""))
//                {
//                    strConSQL+=" AND (" + strAux + ")";
//                }

                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT TRIM(a1.ITEMNMBR) AS ITEMNMBR, TRIM(a1.ITMSHNAM) AS ITMSHNAM, TRIM(a1.ITMGEDSC) as ITMGEDSC, TRIM(a1.ITEMDESC) AS ITEMDESC, TRIM(a1.UOMSCHDL) as UOMSCHDL,   \n";
                strSQL+="        a2.existenciaTotal, CASE WHEN imp.QTYONHND IS NULL THEN 0 ELSE imp.QTYONHND END AS stockIMPORT,   \n";
                strSQL+="        CASE WHEN comp.QTYONHND IS NULL THEN 0 ELSE comp.QTYONHND END AS stockCOMP_LOCAL,   \n";
                strSQL+="        CASE WHEN inm.DISPONIBLE IS NULL THEN 0 ELSE inm.DISPONIBLE END AS stockINMACONSA, inm.ORDRPNTQTY AS minimoINMACONSA, inm.ORDRUPTOLVL AS maximoINMACONSA, \n";
                strSQL+="        CASE WHEN cal.QTYONHND IS NULL THEN 0 ELSE cal.QTYONHND END AS stockCALIFORNIA, cal.ORDRPNTQTY AS minimoCALIFORNIA, cal.ORDRUPTOLVL AS maximoCALIFORNIA,  \n";
                strSQL+="        CASE WHEN via.QTYONHND IS NULL THEN 0 ELSE via.QTYONHND END AS stockVIA_DAULE, via.ORDRPNTQTY AS minimoVIA_DAULE, via.ORDRUPTOLVL AS maximoVIA_DAULE , \n";
                strSQL+="        CASE WHEN dur.QTYONHND IS NULL THEN 0 ELSE dur.QTYONHND END AS stockDURAN, dur.ORDRPNTQTY AS minimoDURAN, dur.ORDRUPTOLVL AS maximoDURAN, \n";
                strSQL+="        CASE WHEN uioN.QTYONHND IS NULL THEN 0 ELSE uioN.QTYONHND END AS stockQUITO_N, uioN.ORDRPNTQTY AS minimoQUITO_N, uioN.ORDRUPTOLVL AS maximoQUITO_N,   \n";
                strSQL+="        CASE WHEN uioS.QTYONHND IS NULL THEN 0 ELSE uioS.QTYONHND END AS stockQUITO_S, uioS.ORDRPNTQTY AS minimoQUITO_S, uioS.ORDRUPTOLVL AS maximoQUITO_S, \n";
                strSQL+="        CASE WHEN man.QTYONHND IS NULL THEN 0 ELSE man.QTYONHND END AS stockMANTA, man.ORDRPNTQTY AS minimoMANTA, man.ORDRUPTOLVL AS maximoMANTA,  \n";
                strSQL+="        CASE WHEN sto.QTYONHND IS NULL THEN 0 ELSE sto.QTYONHND END AS stockSTO_DGO, sto.ORDRPNTQTY AS minimoSTO_DGO, sto.ORDRUPTOLVL AS maximoSTO_DGO, \n";
                strSQL+="        CASE WHEN cue.QTYONHND IS NULL THEN 0 ELSE cue.QTYONHND END AS stockCUENCA, cue.ORDRPNTQTY AS minimoCUENCA, cue.ORDRUPTOLVL AS maximoCUENCA, \n";
                strSQL+="        CASE WHEN inm2.QTYONHND IS NULL THEN 0 ELSE inm2.QTYONHND END AS stockINMACONSA2 \n";
                strSQL+=" FROM  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 (nolock) AS a1  \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                strSQL+="       SELECT  a1.ITEMNMBR, SUM (a1.QTYONHND) AS existenciaTotal  \n";
                strSQL+="       FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS a1 \n";
                strSQL+="       WHERE a1.RCRDTYPE=2  \n";
                strSQL+="       GROUP BY a1.ITEMNMBR \n";
                strSQL+=" ) AS a2 ON (a1.ITEMNMBR=a2.ITEMNMBR) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS imp ON (a1.ITEMNMBR=imp.ITEMNMBR AND imp.LOCNCODE='IMPORT' AND imp.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS comp ON (a1.ITEMNMBR=comp.ITEMNMBR AND comp.LOCNCODE='COMP_LOCAL' AND comp.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                strSQL+="       SELECT inm.LOCNCODE, inm.ITEMNMBR, CASE WHEN inmVW.DISPONIBLE IS NULL THEN 0 ELSE inmVW.DISPONIBLE END AS DISPONIBLE, inm.ORDRPNTQTY, inm.ORDRUPTOLVL  \n";
                strSQL+="       FROM  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS inm    \n";
                strSQL+="       LEFT OUTER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.vw_consulta_disponible_transito (nolock) as inmVW ON (inmVW.LOCNCODE='INMACONSA' AND    \n";
                strSQL+="                                                                                       inm.LOCNCODE=inmVW.LOCNCODE AND inm.ITEMNMBR=inmVw.ITEMNMBR)  \n";
                strSQL+="       WHERE inm.LOCNCODE='INMACONSA' AND inm.RCRDTYPE=2 AND (inm.ITEMNMBR LIKE ('%I') OR inm.ITEMNMBR LIKE ('%S') )   \n";
                strSQL+=" ) as inm ON (inm.LOCNCODE=a1.LOCNCODE AND inm.ITEMNMBR=a1.ITEMNMBR)   \n"; 
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS cal ON (a1.ITEMNMBR=cal.ITEMNMBR AND cal.LOCNCODE='CALIFORNIA' AND cal.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS via ON (a1.ITEMNMBR=via.ITEMNMBR AND via.LOCNCODE='VIA_DAULE' AND via.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS dur ON (a1.ITEMNMBR=dur.ITEMNMBR AND dur.LOCNCODE='DURAN' AND dur.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS uioN ON (a1.ITEMNMBR=uioN.ITEMNMBR AND uioN.LOCNCODE='QUITO_N' AND uioN.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS uioS ON (a1.ITEMNMBR=uioS.ITEMNMBR AND uioS.LOCNCODE='QUITO_S' AND uioS.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS man ON (a1.ITEMNMBR=man.ITEMNMBR AND man.LOCNCODE='MANTA' AND man.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS sto ON (a1.ITEMNMBR=sto.ITEMNMBR AND sto.LOCNCODE='STO_DGO' AND sto.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS cue ON (a1.ITEMNMBR=cue.ITEMNMBR AND cue.LOCNCODE='CUENCA_2' AND cue.RCRDTYPE=2) \n";
                strSQL+=" LEFT OUTER JOIN  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00102 (nolock) AS inm2 ON (a1.ITEMNMBR=inm2.ITEMNMBR AND inm2.LOCNCODE='INMACONSA2' AND inm2.RCRDTYPE=2) \n";
                strSQL+=" WHERE a1.INACTIVE = 0 AND a1.ITEMTYPE = 1 AND (a1.ITEMNMBR LIKE ('%I') OR a1.ITEMNMBR LIKE ('%S') )   \n";
                strSQL+=strConSQL;
                strSQL+=" ORDER BY a2.existenciaTotal DESC \n";
                System.out.println(">>> " + strSQL);
                rst=stm.executeQuery(strSQL);

                 
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM_GP,rst.getString("ITEMNMBR"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_GEN,rst.getString("ITMSHNAM"));
                        vecReg.add(INT_TBL_DAT_COD_3L,rst.getString("ITMGEDSC"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM_GP,rst.getString("ITEMDESC"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("UOMSCHDL"));
                        vecReg.add(INT_TBL_DAT_STK_IMP,rst.getDouble("stockIMPORT"));
                        
                        vecReg.add(INT_TBL_DAT_STK_INM,rst.getDouble("stockINMACONSA"));
                        vecReg.add(INT_TBL_DAT_MIN_INM,rst.getDouble("minimoINMACONSA"));
                        vecReg.add(INT_TBL_DAT_MAX_INM,rst.getDouble("maximoINMACONSA"));
                        
                        vecReg.add(INT_TBL_DAT_STK_CAL,rst.getDouble("stockCALIFORNIA"));
                        vecReg.add(INT_TBL_DAT_MIN_CAL,rst.getDouble("minimoCALIFORNIA"));
                        vecReg.add(INT_TBL_DAT_MAX_CAL,rst.getDouble("maximoCALIFORNIA"));
                        
                        vecReg.add(INT_TBL_DAT_STK_DAU,rst.getDouble("stockVIA_DAULE"));
                        vecReg.add(INT_TBL_DAT_MIN_DAU,rst.getDouble("minimoVIA_DAULE"));
                        vecReg.add(INT_TBL_DAT_MAX_DAU,rst.getDouble("maximoVIA_DAULE"));
                        
                        vecReg.add(INT_TBL_DAT_STK_DUR,rst.getDouble("stockDURAN"));
                        vecReg.add(INT_TBL_DAT_MIN_DUR,rst.getDouble("minimoDURAN"));
                        vecReg.add(INT_TBL_DAT_MAX_DUR,rst.getDouble("maximoDURAN"));
                        
                        vecReg.add(INT_TBL_DAT_STK_UION,rst.getDouble("stockQUITO_N"));
                        vecReg.add(INT_TBL_DAT_MIN_UION,rst.getDouble("minimoQUITO_N"));
                        vecReg.add(INT_TBL_DAT_MAX_UION,rst.getDouble("maximoQUITO_N"));
                        
                        vecReg.add(INT_TBL_DAT_STK_UIOS,rst.getDouble("stockQUITO_S"));
                        vecReg.add(INT_TBL_DAT_MIN_UIOS,rst.getDouble("minimoQUITO_S"));
                        vecReg.add(INT_TBL_DAT_MAX_UIOS,rst.getDouble("maximoQUITO_S"));
                        
                        vecReg.add(INT_TBL_DAT_STK_MAN,rst.getDouble("stockMANTA"));
                        vecReg.add(INT_TBL_DAT_MIN_MAN,rst.getDouble("minimoMANTA"));
                        vecReg.add(INT_TBL_DAT_MAX_MAN,rst.getDouble("maximoMANTA"));
                        
                        vecReg.add(INT_TBL_DAT_STK_DGO,rst.getDouble("stockSTO_DGO"));
                        vecReg.add(INT_TBL_DAT_MIN_DGO,rst.getDouble("minimoSTO_DGO"));
                        vecReg.add(INT_TBL_DAT_MAX_DGO,rst.getDouble("maximoSTO_DGO"));
                        
                        vecReg.add(INT_TBL_DAT_STK_CUE,rst.getDouble("stockCUENCA"));
                        vecReg.add(INT_TBL_DAT_MIN_CUE,rst.getDouble("minimoCUENCA"));
                        vecReg.add(INT_TBL_DAT_MAX_CUE,rst.getDouble("maximoCUENCA"));
                        
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                conLoc.close();
                rst=null;
                stm=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
              
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
            if (objTblModLoc.isChecked(j, INT_TBL_BOD_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un local.<BR>Seleccione una Bodega y vuelva a intentarlo.</HTML>");
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
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
        public void run()
        {
            configurarArreglos();
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
                    strMsg+="<html>";
                    strMsg+="<h3 style='text-align:center;'>Colores utilizados en la tabla</h3>";
                    strMsg+="<table border='1'><tr><td><b>Fondo</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #00FFFF'>&nbsp;</td><td>Indica que esta pendiente de ser configurado en el periodo.</td></tr>";
                    strMsg+="<tr><td style='background-color: #78F078'>&nbsp;</td><td>Indica que está siendo modificado por el usuario. </td></tr>";
                    strMsg+="</table><br></html>";
                    break; 
                case INT_TBL_DAT_COD_ITM_GEN:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_3L:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM_GP:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_STK_IMP:
                    strMsg="Total de unidades vendidas";
                    break;
                case INT_TBL_DAT_STK_INM:
                    strMsg="Stock actual";
                    break;
                case INT_TBL_DAT_MIN_INM:
                    strMsg="Precio de venta 1";
                    break;
                case INT_TBL_DAT_MAX_INM:
                    strMsg="Venta total";
                    break;
                case INT_TBL_DAT_STK_CAL:
                    strMsg="Costo total";
                    break;
                case INT_TBL_DAT_MIN_CAL:
                    strMsg="Valor de ganancia";
                    break;
                case INT_TBL_DAT_MAX_CAL:
                    strMsg="Porcentaje de ganancia";
                    break;
                case INT_TBL_DAT_STK_DAU:
                    strMsg="Peso total (Kg)";
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
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la Bodega";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la Bodega";
                    break;
                 
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
     

}