/*
 * ZafVen15.java
 *
 * Created on 18 de mayo de 2009, 16:15 PM
 */
package Ventas.ZafVen15;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenConItm01;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;


/**
 *
 * @author  Eddye Lino
 */
public class ZafVen15 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_NUM_TOT_CES=7;                 //Número total de columnas estáticas.
    static final int INT_TBL_DAT_NUM_TOT_CDI=3;                 //Número total de columnas dinámicas.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=5;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_PRE_UNI_VT1=6;                 //Precio unitario de venta 1.
    static final int INT_TBL_DAT_CDI_DIS=7;                     //Columna dinámica: Cantidad Disponible
    static final int INT_TBL_DAT_CDI_CAN_ING=8;                 //Columna dinámica: Cantidad por ingresar a Bodega.
    static final int INT_TBL_DAT_CDI_CAN_EGR=9;                 //Columna dinámica: Cantidad por egresar de Bodega.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenConItm01 objVenConItm01;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL,strFil, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblBod=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb; //Numero de columnas que se adicionan pedidos de importaciones.
    //Contiene la información de los nombres de los pedidos embarcados
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    static final int INT_ARL_PED_EMB_COD_EMP=0;
    static final int INT_ARL_PED_EMB_COD_LOC=1;
    static final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    static final int INT_ARL_PED_EMB_COD_DOC=3;
    static final int INT_ARL_PED_EMB_NUM_PED=4;
    static final int INT_ARL_PED_EMB_NUM_COL=5;
    static final int INT_ARL_PED_EMB_EST_COL=6;
    static final int INT_ARL_PED_EMB_FEC_EST_PED_EMB=7;
    //Contiene la información de los valores ingresados para cada item en los pedidos embarcados
    private ArrayList arlRegCanPedEmb, arlDatCanPedEmb;
    static final int INT_ARL_CAN_PED_EMB_COD_EMP=0;
    static final int INT_ARL_CAN_PED_EMB_COD_LOC=1;
    static final int INT_ARL_CAN_PED_EMB_COD_TIP_DOC=2;
    static final int INT_ARL_CAN_PED_EMB_COD_DOC=3;
    static final int INT_ARL_CAN_PED_EMB_COD_ITM=4;
    static final int INT_ARL_CAN_PED_EMB_CAN_PED_EMB=5;
    static final int INT_ARL_CAN_PED_EMB_COD_ALT_ITM=6;
    static final int INT_ARL_CAN_PED_EMB_EST=7;
    static final int INT_ARL_CAN_PED_EMB_NUM_COL=8;
    static final int INT_ARL_CAN_PED_EMB_COD_ITM_MAE=9;
   
    /**
     * Este constructor crea una instancia de la clase ZafVen15.
     * @param objParSis El objeto ZafParSis.
     */
    public ZafVen15(ZafParSis objParSis)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            this.objParSis=(ZafParSis)objParSis.clone();
            arlDatPedEmb=new ArrayList();
            arlDatCanPedEmb=new ArrayList();
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkMosItmPre = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(24, 64, 660, 92);

        chkMosItmPre.setText("Mostrar sólo los items que tienen precio");
        panFil.add(chkMosItmPre);
        chkMosItmPre.setBounds(24, 152, 470, 23);

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

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            objVenConItm01.limpiar();
            chkMosItmPre.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkMosItmPre;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItm01(objParSis, "a1.", optFil);
            panFil.add(objVenConItm01);
            objVenConItm01.setBounds(24, 44, 656, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.8.3");
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            //configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();
            if (objParSis.getCodigoMenu()==846)
                cargarCabeceraPedidosConfigurados();
            agregarColTblDat();
            if (objParSis.getCodigoMenu()==846)
                agregarColumnasPedidosEmbarcados();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBod".
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
            vecCab.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCab);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblBod);
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_NOM_EMP, tblBod);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    if (objTblCelEdiChk.isChecked())
                    {
                        //Mostrar columnas.
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    else
                    {
                        //Ocultar columnas.
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_PRE_UNI_VT1,"Pre.Uni.Vta.1");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            //Si es el usuario Administrador (Código=1) habilito el ZafTblPopMnu.
            if (objParSis.getCodigoUsuario()==1)
            {
                objTblPopMnu=new ZafTblPopMnu(tblDat);
            }
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_VT1).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 846: //Lista de precios (Stock físico por bodegas)...
                    break;
                case 910: //Listado de stock físico de inventario por bodegas...
                default:
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PRE_UNI_VT1, tblDat);
                    break;
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PRE_UNI_VT1).setCellRenderer(objTblCelRenLbl);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod";
                    strSQL+=" FROM tbr_bodLocPrgUsr AS b1";
                    strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)";
                    strSQL+=" WHERE b1.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND b1.st_reg IN ('A','P')";
                    strSQL+=" GROUP BY b2.co_empGrp, b2.co_bodGrp";
                    strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod=false;
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblEmp".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i, intNumFil, intNumColTblDat;
        javax.swing.table.TableColumn tbc;
        String strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();
            for (i=0; i<intNumFil; i++)
            {
                //Establecer el color de fondo de las columnas.
                if ((i%2)==0)
                    colFonCol=new java.awt.Color(228,228,203);
                else
                    colFonCol=new java.awt.Color(255,221,187);

                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Dis.");
                //Configurar JTable: Establecer el ancho de la columna.
                switch (objParSis.getCodigoMenu())
                {
                    case 846: //Lista de precios (Stock físico por bodegas)...
                        tbc.setPreferredWidth(80);
                        break;
                    case 910: //Listado de stock físico de inventario por bodegas...
                    default:
                        tbc.setPreferredWidth(60);
                        break;
                }
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Ing.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Can.Egr.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                //Agrupar las columnas.
                if (!strCodBod.equals(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString()))
                {
                    objTblHeaColGrpBod=new ZafTblHeaColGrp(objTblModBod.getValueAt(i, INT_TBL_BOD_NOM_BOD).toString());
                    objTblHeaColGrpBod.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpBod);
                    strCodBod=objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                }
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI));
                //Ocultar las columnas si la empresa no está seleccionada.
                if (!objTblModBod.isChecked(i, INT_TBL_BOD_CHK))
                {
                    //Ocultar columnas.
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                }
                else
                {
                    switch (objParSis.getCodigoMenu())
                    {
                        case 846: //Lista de precios (Stock físico por bodegas)...
                            //Ocultar columnas.
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            break;
                        case 910: //Listado de stock físico de inventario por bodegas...
                            break;
                        default:
                            break;
                    }
                }
            }
            objTblCelRenLbl=null;
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
    private boolean cargarDetReg()
    {
        
        int intCodEmp, intNumFilTblBod, j;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b1.nd_preVta1 \n";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        strSQL+=", b" + (j+2) + ".nd_canDis AS b" + (j+2) + "_nd_canDis";
                        strSQL+=", b" + (j+2) + ".nd_canIngBod AS b" + (j+2) + "_nd_canIngBod";
                        strSQL+=", b" + (j+2) + ".nd_canEgrBod AS b" + (j+2) + "_nd_canEgrBod";
                    }
                    else
                    {
                        strSQL+=", Null AS b" + (j+2) + "_nd_canDis";
                        strSQL+=", Null AS b" + (j+2) + "_nd_canIngBod";
                        strSQL+=", Null AS b" + (j+2) + "_nd_canEgrBod";
                    }
                }
                strSQL+="\n FROM ( \n";
                strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a1.nd_preVta1 \n";
                strSQL+=" FROM tbm_inv AS a1 \n";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.st_reg='A' \n";
                strSQL+=strConSQL+" \n";
                strSQL+=" ) AS b1 \n";
                
                /* Angelica Zambrano - 30Sep2020: Se comenta validación de usuario admin,
                   porque el filtro de listado por bodega ya trae las bodegas a las que tiene acceso el usuario */
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                //if (objParSis.getCodigoUsuario()==1)
                //{
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))  
                        {
                            strSQL+=" LEFT OUTER JOIN ( \n";
                            strSQL+=" SELECT a2.co_itmMae, SUM(a1.nd_canDis) AS nd_canDis, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod \n";
                            strSQL+=" FROM tbm_invBod AS a1 \n";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod) \n";
                            strSQL+=" WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+=" AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD)+" \n";
                            strSQL+=" GROUP BY a2.co_itmMae \n";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae) \n";
                        }
                    }
                //}
//                else
//                {
//                    for (j=0; j<intNumFilTblBod; j++)
//                    {
//                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
//                        {
//                            strSQL+=" LEFT OUTER JOIN ( \n";
//                            strSQL+=" SELECT a2.co_itmMae, SUM(a1.nd_canDis) AS nd_canDis, SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod \n";
//                            strSQL+=" FROM tbm_invBod AS a1 \n";
//                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
//                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod) \n";
//                            strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod) \n";
//                            strSQL+=" WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP)+" \n";
//                            strSQL+=" AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD)+" \n";
//                            strSQL+=" AND a4.co_mnu=" + objParSis.getCodigoMenu()+" \n";
//                            strSQL+=" AND a4.co_usr=" + objParSis.getCodigoUsuario()+ " \n";
//                            strSQL+=" AND a4.st_reg IN ('A','P')" + " \n";
//                            strSQL+=" GROUP BY a2.co_itmMae"+ " \n";
//                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae) \n";
//                        }
//                    }
//                }

                if (objParSis.getCodigoMenu()==846)
                {
                    if (chkMosItmPre.isSelected())
                        strSQL+=" WHERE (b1.nd_preVta1 IS NOT NULL AND b1.nd_preVta1>0)  \n";
                }
                strSQL+=" ORDER BY b1.tx_codAlt \n";
                System.out.println("cargarDetReg:::: " + strSQL);
                rst=stm.executeQuery(strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                vecReg.add(INT_TBL_DAT_PRE_UNI_VT1,rst.getString("nd_preVta1"));
                                for (j=0; j<intNumFilTblBod; j++)
                                {
                                    vecReg.add(INT_TBL_DAT_CDI_DIS+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canDis"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_ING+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_EGR+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                }
                                for (int d=0; d<arlDatPedEmb.size(); d++)
                                {
                                    vecReg.add((intNumColIniPedEmb+d), null);
                                }                                
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                vecReg.add(INT_TBL_DAT_PRE_UNI_VT1,null);
                                for (j=0; j<intNumFilTblBod; j++)
                                {
                                    vecReg.add(INT_TBL_DAT_CDI_DIS+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canDis"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_ING+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canIngBod"));
                                    vecReg.add(INT_TBL_DAT_CDI_CAN_EGR+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_canEgrBod"));
                                }
                                break;
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
                if (objParSis.getCodigoMenu()==846)
                {
                    //Se cargan los datos de valores del pedido embarcado.
                    int intCodItmMaeTbl=0;
                    int intCodItmMaeArl=0;
                    String strEstPedEmb="";
                    int intColPedEmb=0;
                    BigDecimal bdeValPedCur=new BigDecimal(BigInteger.ZERO);
                    for (int d=0; d<objTblMod.getRowCountTrue(); d++)
                    {
                        intCodItmMaeTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_MAE).toString()));
                        for (int e=0; e<(arlDatCanPedEmb.size()); e++)
                        {
                            intCodItmMaeArl=objUti.getIntValueAt(arlDatCanPedEmb,    e, INT_ARL_CAN_PED_EMB_COD_ITM_MAE);
                            strEstPedEmb=objUti.getStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST);
                            intColPedEmb=objUti.getIntValueAt(arlDatCanPedEmb,    e, INT_ARL_CAN_PED_EMB_NUM_COL);
                            if (strEstPedEmb.equals(""))
                            {
                                if (intCodItmMaeTbl==intCodItmMaeArl)
                                {
                                    objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "S");
                                    bdeValPedCur=objUti.getBigDecimalValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_CAN_PED_EMB);
                                    objTblMod.setValueAt(bdeValPedCur, d, intColPedEmb);
                                }
                            }
                        }
                    }
                }
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
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIS+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        switch (objParSis.getCodigoMenu())
                        {
                            case 846: //Lista de precios (Stock físico por bodegas)...
                                break;
                            case 910: //Listado de stock físico de inventario por bodegas...
                            default:
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_ING+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CAN_EGR+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                                break;
                        }
                    }
                    blnMarTodChkTblBod=true;
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
            if (!cargarReg())
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
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CDI+INT_TBL_DAT_NUM_TOT_CES;
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PRE_UNI_VT1:
                    strMsg="Precio unitario de venta 1";
                    break;
                case INT_TBL_DAT_CDI_DIS:
                    strMsg="Cantidad disponible";
                    break;
                case INT_TBL_DAT_CDI_CAN_ING:
                    strMsg="Cantidad por ingresar a Bodega";
                    break;
                case INT_TBL_DAT_CDI_CAN_EGR:
                    strMsg="Cantidad por egresar de Bodega";
                    break;
                default:
                    strMsg="";
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
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean agregarColumnasPedidosEmbarcados()
    {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=objTblMod.getColumnCount();//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        String strFecEstPedEmb="";//fecha estimada de pedidoEmbarcado
        try
        {
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (int i=0; i<intNumColAddPedEmb; i++)
            {
                strNomPed="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_PED);
                strFecEstPedEmb="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC_EST_PED_EMB);
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue(strNomPed);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                objTblHeaColGrpFecEstEmb=new ZafTblHeaColGrp(strFecEstPedEmb);
                objTblHeaColGrpFecEstEmb.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpFecEstEmb);
                objTblHeaColGrpFecEstEmb.add(tbc);
            }
            //objTblCelRenLbl=null;
            intNumColFinPedEmb=objTblMod.getColumnCount();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean cargarCabeceraPedidosConfigurados()
    {
        boolean blnRes=true;
        arlDatPedEmb.clear();
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT * FROM (";
                strSQL+="   SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc";
                strSQL+="   , CASE WHEN d1.fe_arr IS NULL THEN d1.fe_arrTitPed ELSE ''||d1.fe_arr END AS fe_arr";
                strSQL+="   , d1.fe_arrTitPed, d1.tx_numPed";//fe_arrTitPed
                strSQL+="   FROM (";
                strSQL+="	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.fe_arrTitPed, c1.tx_numPed";
                strSQL+="		, c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+="		, c1.fe_arr";
//                strSQL+="	        , CAST( (";
//                strSQL+="	                  (CASE WHEN c1.co_mesArr < c1.co_mesEmb";
//                strSQL+="	                   THEN ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb+1) ELSE EXTRACT('year' FROM c1.fe_doc) END)";
//                strSQL+="	                   ELSE ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb) ELSE EXTRACT('year' FROM c1.fe_doc) END)";
//                strSQL+="	                   END)";
//                strSQL+="	                   || '-' ||	";		
//                strSQL+="	                   (CASE WHEN c1.co_mesArr IN(1,2)   THEN '01' WHEN c1.co_mesArr IN(3,4)   THEN '02'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(5,6)   THEN '03' WHEN c1.co_mesArr IN(7,8)   THEN '04'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(9,10)  THEN '05' WHEN c1.co_mesArr IN(11,12) THEN '06'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(13,14) THEN '07' WHEN c1.co_mesArr IN(15,16) THEN '08'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(17,18) THEN '09' WHEN c1.co_mesArr IN(19,20) THEN '10'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(21,22) THEN '11' WHEN c1.co_mesArr IN(23,24) THEN '12'";
//                strSQL+="	                   END)";
//                strSQL+="	                   || '-' ||	";
//                strSQL+="	                   (CASE WHEN c1.co_mesArr IN (3,4) THEN '28' ELSE '30' END)";
//                strSQL+="	           ) AS DATE) AS fe_arr  ";    
                strSQL+="	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb, b1.fe_arrTitPed, b1.tx_numPed";
                strSQL+=" 		     , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+=" 		     , (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END  ) AS nd_canNotPedFal, b1.fe_arr";
                strSQL+="		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_mesArr, a1.co_mesEmb, a1.ne_aniEmb, a5.tx_deslar AS fe_arrTitPed";
                strSQL+=" 			    , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed";
                strSQL+=" 			    , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+=" 			    , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb, a1.fe_arr";
                strSQL+="			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesemb)"; //la fecha estiamada se presenta en base a ARRIBO
                strSQL+="			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+=" 			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="			LEFT OUTER JOIN ";
                strSQL+="			(        tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="				 AND a3.st_reg NOT IN('I','E')  AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL)";
                strSQL+="			)";
                strSQL+="			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+="			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="			WHERE a1.st_reg NOT IN('E','I')  AND a1.st_cie='N'  AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL)    ";
                strSQL+="			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+="			       , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, a1.fe_arr";
                strSQL+="			ORDER BY a1.co_doc";
                strSQL+="		) AS b1";
                strSQL+="		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb, b1.fe_arrTitPed, b1.tx_numPed, b1.nd_canNotPed, b1.fe_arr";
                strSQL+="	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+="   ) AS d1";
                strSQL+="   WHERE d1.nd_canNotPedFal > 0";
                strSQL+="   GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.fe_arr, d1.fe_arrTitPed, d1.tx_numPed";                
                strSQL+="   UNION";
                strSQL+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="    , ''||a1.fe_arr, ''||a1.fe_arr AS fe_arrTitPed"; //la fecha estimada se presenta en base a ARRIBO
                strSQL+="    , (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) AS tx_numPed";
                strSQL+="   FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="   ON a1.co_emp=a2.co_emprelpedembimp AND a1.co_loc=a2.co_locrelpedembimp";
                strSQL+="   AND a1.co_tipDoc=a2.co_tipdocrelpedembimp AND a1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="   WHERE a1.st_reg NOT IN('E','I') ";
                strSQL+="   AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL)";
                strSQL+="   AND a2.co_emp IS NULL";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.fe_arr, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.fe_doc, x.tx_numPed";
                System.out.println("cargarCabeceraPedidosConfigurados: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next())
                {
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP, "" + rst.getString("co_emp"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC, "" + rst.getString("co_loc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC, "" + rst.getString("co_tipDoc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC, "" + rst.getString("co_doc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED, "" + rst.getString("tx_numPed"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL, "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL, "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB, "" + rst.getString("fe_arr"));//fe_arrTitPed
                    arlDatPedEmb.add(arlRegPedEmb);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
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

    private boolean cargarDetallePedidosConfigurados()
    {
        boolean blnRes=true;
        Statement stmCanPedEmb;
        ResultSet rstCanPedEmb;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stmCanPedEmb=con.createStatement();
                //Crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+=" SELECT * FROM (";
                strSQL+="   SELECT g1.co_emp, g1.co_loc, g1.co_tipDoc, g1.co_doc, g1.co_itm, g1.nd_can, a1.tx_codAlt, k1.co_itmMae, g1.fe_arr";
                strSQL+="   FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS k1 ON a1.co_emp=k1.co_emp AND a1.co_itm=k1.co_itm )";
                strSQL+="   INNER JOIN(";
                strSQL+="       SELECT f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f1.fe_arr, f2.co_itm, SUM(f2.nd_can) AS nd_can";
                strSQL+="       FROM (tbm_cabPedEmbImp AS f1 ";
                strSQL+="  		LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="  		ON f1.co_emp=a2.co_emprelpedembimp AND f1.co_loc=a2.co_locrelpedembimp ";
                strSQL+="  		AND f1.co_tipDoc=a2.co_tipdocrelpedembimp AND f1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="  	)";
                strSQL+="       INNER JOIN tbm_detPedEmbImp AS f2";
                strSQL+="       ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc ";
                strSQL+="       WHERE f1.st_reg NOT IN('E','I') AND a2.co_emp IS NULL";
                strSQL+="       GROUP BY f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f1.fe_arr, f2.co_itm";
                strSQL+="   ) AS g1";
                strSQL+="   ON a1.co_emp=g1.co_emp AND a1.co_itm=g1.co_itm";
                strSQL+="   UNION ALL";
                strSQL+="   SELECT g2.co_emp, g2.co_loc, g2.co_tipDoc, g2.co_doc, g2.co_itm, g2.nd_canNotPedFal, a1.tx_codAlt, k1.co_itmMae, g2.fe_arr";
                strSQL+="   FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS k1 ON a1.co_emp=k1.co_emp AND a1.co_itm=k1.co_itm )";
                strSQL+="   INNER JOIN(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_itm, c1.nd_canNotPedFal";
                strSQL+="          , c1.fe_arr";
//                strSQL+="	     , CAST( (";
//                strSQL+="	                  (CASE WHEN c1.co_mesArr < c1.co_mesEmb";
//                strSQL+="	                   THEN ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb+1) ELSE EXTRACT('year' FROM c1.fe_doc) END)";
//                strSQL+="	                   ELSE ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb) ELSE EXTRACT('year' FROM c1.fe_doc) END)";
//                strSQL+="	                   END)";
//                strSQL+="	                   || '-' ||	";		
//                strSQL+="	                   (CASE WHEN c1.co_mesArr IN(1,2)   THEN '01' WHEN c1.co_mesArr IN(3,4)   THEN '02'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(5,6)   THEN '03' WHEN c1.co_mesArr IN(7,8)   THEN '04'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(9,10)  THEN '05' WHEN c1.co_mesArr IN(11,12) THEN '06'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(13,14) THEN '07' WHEN c1.co_mesArr IN(15,16) THEN '08'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(17,18) THEN '09' WHEN c1.co_mesArr IN(19,20) THEN '10'";
//                strSQL+="	                         WHEN c1.co_mesArr IN(21,22) THEN '11' WHEN c1.co_mesArr IN(23,24) THEN '12'";
//                strSQL+="	                   END)";
//                strSQL+="	                   || '-' ||	";
//                strSQL+="	                   (CASE WHEN c1.co_mesArr IN (3,4) THEN '28' ELSE '30' END)";
//                strSQL+="	           ) AS DATE) AS fe_arr  ";                  
                strSQL+=" 	FROM(";
                strSQL+="             SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_itm, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb";
                strSQL+="		   , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="		   , (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END ) AS nd_canNotPedFal, b1.fe_arr";
                strSQL+="	      FROM(	";
                strSQL+="		    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_mesArr, a1.co_mesEmb, a1.ne_aniEmb, a5.tx_deslar AS fe_arrTitPed";
                strSQL+=" 	                 , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed"; 
                strSQL+="			 , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="			 , CAST(a4.nd_can AS NUMERIC(18,6) ) AS nd_canNotPed, CAST(a2.nd_can AS NUMERIC(18,6) ) AS nd_canPedEmb";
                strSQL+=" 			 , a4.co_itm, a1.fe_arr";
                strSQL+=" 	            FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="		    INNER JOIN tbm_detNotPedImp AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="		    LEFT OUTER JOIN ";
                strSQL+="	            (      tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="		           ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="		           AND a3.st_reg NOT IN('I','E') AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL)";
                strSQL+="		    )";
                strSQL+=" 		    ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 		    AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+=" 		    WHERE a1.st_reg NOT IN('E','I')	  AND a1.st_cie='N'  AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL) ";
                strSQL+="		    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.co_mesArr, a1.co_mesEmb, a1.ne_aniEmb, a5.tx_deslar, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+=" 		           , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, a4.co_itm, a1.fe_arr";
                strSQL+=" 		    ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb, b1.fe_arrTitPed, b1.tx_numPed, b1.nd_canNotPed, b1.co_itm, b1.fe_arr";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+="  ) AS g2";
                strSQL+="  ON a1.co_emp=g2.co_emp AND a1.co_itm=g2.co_itm";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.fe_arr, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc";
                rstCanPedEmb=stmCanPedEmb.executeQuery(strSQL);
                arlDatCanPedEmb.clear();
                while(rstCanPedEmb.next())
                {
                    arlRegCanPedEmb=new ArrayList();
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_EMP, rstCanPedEmb.getString("co_emp"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_LOC, rstCanPedEmb.getString("co_loc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_TIP_DOC, rstCanPedEmb.getString("co_tipDoc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_DOC, rstCanPedEmb.getString("co_doc"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ITM, rstCanPedEmb.getString("co_itm"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_CAN_PED_EMB, rstCanPedEmb.getString("nd_can"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ALT_ITM, rstCanPedEmb.getString("tx_codAlt"));
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_EST, "");
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_NUM_COL, "0");
                    arlRegCanPedEmb.add(INT_ARL_CAN_PED_EMB_COD_ITM_MAE, rstCanPedEmb.getString("co_itmMae"));
                    arlDatCanPedEmb.add(arlRegCanPedEmb);
                }
                rstCanPedEmb.close();
                stmCanPedEmb.close();
                con.close();
                rstCanPedEmb=null;
                stmCanPedEmb=null;
                con=null;
                //Se cargan los datos de valores del pedido embarcado.
                int intCodEmpNomPedEmb=-1;
                int intCodLocNomPedEmb=-1;
                int intCodTipDocNomPedEmb=-1;
                int intCodDocNomPedEmb=-1;
                int intNumColNomPedEmb=-1;                
                int intCodEmpCanPedEmb=-1;
                int intCodLocCanPedEmb=-1;
                int intCodTipDocCanPedEmb=-1;
                int intCodDocCanPedEmb=-1;                
                String strEstPedEmb="";
                for (int d=0; d<(arlDatPedEmb.size()); d++)
                {
                    intCodEmpNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_EMP);
                    intCodLocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_LOC);
                    intCodTipDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_TIP_DOC);
                    intCodDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_DOC);
                    intNumColNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_NUM_COL);
                    for (int e=0; e<(arlDatCanPedEmb.size()); e++)
                    {
                        intCodEmpCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_EMP);
                        intCodLocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_LOC);
                        intCodTipDocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_TIP_DOC);
                        intCodDocCanPedEmb=objUti.getIntValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_COD_DOC);
                        strEstPedEmb=objUti.getStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST);
                        if (strEstPedEmb.equals(""))
                        {
                            if ( (intCodEmpNomPedEmb==intCodEmpCanPedEmb) && (intCodLocNomPedEmb==intCodLocCanPedEmb) && (intCodTipDocNomPedEmb==intCodTipDocCanPedEmb) && (intCodDocNomPedEmb==intCodDocCanPedEmb) )
                            {
                                objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "S");
                                objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_NUM_COL, ""+intNumColNomPedEmb);
                            }
                        }
                    }
                }
                //Se blanquean los estados de los registros para poder procesarlos al momento de cargar los datos.
                for (int e=0; e<(arlDatCanPedEmb.size()); e++)
                {
                    objUti.setStringValueAt(arlDatCanPedEmb, e, INT_ARL_CAN_PED_EMB_EST, "");
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
    
   private boolean cargarReg()
   {
        boolean blnRes=true;
        try
        {
            switch (objParSis.getCodigoMenu())
            {
                case 846: //Lista de precios (Stock físico por bodegas)...
                    if (cargarDetallePedidosConfigurados())
                    {
                        blnRes=cargarDetReg();
                         escondeColumnasPedidoSinValor();
                    }
                    else
                    {
                        blnRes=false;
                    }
                    break;
                case 910: //Listado de stock físico de inventario por bodegas...
                    blnRes=cargarDetReg();
                    break;
            }
            
            objTblMod.initRowsState();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }  

   /*private boolean cargarCabeceraPedidosConfiguradosConFiltroItem()
    {
        boolean blnRes=true;
        arlDatPedEmb.clear();
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
               
                strSQL+="SELECT *FROM \n";
                strSQL+="( \n";
                strSQL+="	SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.fe_arr, d1.fe_arrTitPed, d1.tx_numPed  \n";
                strSQL+="	FROM(  \n";
                strSQL+="		SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.fe_arrTitPed, c1.tx_numPed  \n";
                strSQL+="			, c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal  \n";
                strSQL+="			, CAST( (  \n";
                strSQL+="			  (CASE WHEN c1.co_mesArr < c1.co_mesEmb  \n";
                strSQL+="			   THEN ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb+1) ELSE EXTRACT('year' FROM c1.fe_doc) END)  \n";
                strSQL+="			   ELSE ( CASE WHEN c1.ne_aniEmb IS NOT NULL THEN (c1.ne_aniEmb) ELSE EXTRACT('year' FROM c1.fe_doc) END)  \n";
                strSQL+="			   END)  \n";
                strSQL+="			   || '-' ||	 \n";
                strSQL+="			   (CASE WHEN c1.co_mesArr IN(1,2)   THEN '01' WHEN c1.co_mesArr IN(3,4)   THEN '02'  \n";
                strSQL+="				 WHEN c1.co_mesArr IN(5,6)   THEN '03' WHEN c1.co_mesArr IN(7,8)   THEN '04'  \n";
                strSQL+="				 WHEN c1.co_mesArr IN(9,10)  THEN '05' WHEN c1.co_mesArr IN(11,12) THEN '06'  \n";
                strSQL+="				 WHEN c1.co_mesArr IN(13,14) THEN '07' WHEN c1.co_mesArr IN(15,16) THEN '08'  \n";
                strSQL+="				 WHEN c1.co_mesArr IN(17,18) THEN '09' WHEN c1.co_mesArr IN(19,20) THEN '10'  \n";
                strSQL+="				 WHEN c1.co_mesArr IN(21,22) THEN '11' WHEN c1.co_mesArr IN(23,24) THEN '12'  \n";
                strSQL+="			   END)  \n";
                strSQL+="			   || '-' ||	 \n";
                strSQL+="			   (CASE WHEN c1.co_mesArr IN (3,4) THEN '28' ELSE '30' END)  \n";
                strSQL+="			) AS DATE) AS fe_arr   \n";
                strSQL+="		FROM( \n";
                strSQL+="			SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb, b1.fe_arrTitPed, b1.tx_numPed  \n";
                strSQL+="				, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb \n";
                strSQL+="				, (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END  ) AS nd_canNotPedFal \n";
                strSQL+="			FROM( \n";
                strSQL+="				SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_doc, a.co_mesArr, a.co_mesEmb, a.ne_aniEmb, a5.tx_deslar AS fe_arrTitPed  \n";
                strSQL+="				    , (CASE WHEN a.tx_numDoc3 IS NOT NULL THEN a.tx_numDoc3 ELSE a.tx_numDoc2 END) AS tx_numPed, a1.tx_codalt as item, a1.co_itm \n";
                strSQL+="				    , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb  \n";
                strSQL+="				FROM (tbm_cabNotPedImp AS a INNER JOIN tbm_mesEmbImp AS a5 ON a.co_mesArr=a5.co_mesemb)  \n";
                strSQL+="				INNER JOIN tbm_detNotPedImp AS a4  \n";
                strSQL+="				ON a.co_emp=a4.co_emp AND a.co_loc=a4.co_loc AND a.co_tipDoc=a4.co_tipDoc AND a.co_doc=a4.co_doc  \n";
                strSQL+="				INNER JOIN tbm_inv as a1 ON (a4.co_emp = a1.co_emp AND a4.co_itm = a1.co_itm)  \n";
                strSQL+="				LEFT OUTER JOIN   \n";
                strSQL+="				(        tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3  \n";
                strSQL+="					 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc  \n";
                strSQL+="				)  \n";
                strSQL+="				ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel   \n";
                strSQL+="				AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel  \n";
                strSQL+="				WHERE a.st_reg NOT IN('E','I')  AND a.st_cie='N'  AND (a.st_doc IN ('A') OR a.st_doc IS NULL) \n";
                strSQL+=strFil+"\n"; 
                strSQL+="				GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_doc, a5.tx_deslar, a.tx_numDoc2, a.tx_numDoc3  \n";
                strSQL+="				       , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, item,a1.co_itm  \n";
                strSQL+="				ORDER BY a.co_doc  \n";
                strSQL+="			) AS b1 \n";
                strSQL+="			  GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.co_mesArr, b1.co_mesEmb, b1.ne_aniEmb, b1.fe_arrTitPed, b1.tx_numPed, b1.nd_canNotPed \n";
                strSQL+="		) AS c1 \n";
                strSQL+="		WHERE c1.nd_canNotPedFal > 0 \n";
                strSQL+="	) AS d1 \n";
                strSQL+="	WHERE d1.nd_canNotPedFal > 0 \n";
                strSQL+="	GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.fe_arr, d1.fe_arrTitPed, d1.tx_numPed \n";
                strSQL+="	UNION \n";
                strSQL+="	SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.fe_doc \n";
                strSQL+="	    , a3.fe_arr, ''||a3.fe_arr AS fe_arrTitPed \n";
                strSQL+="	    , (CASE WHEN a3.tx_numDoc4 IS NOT NULL THEN a3.tx_numDoc4 ELSE a3.tx_numDoc2 END) AS tx_numPed \n";
                strSQL+="	FROM tbm_cabPedEmbImp AS a3  \n";
                strSQL+="	   LEFT OUTER JOIN tbm_detPedEmbImp as a4 ON (a4.co_emp = a3.co_emp AND a4.co_loc = a3.co_loc AND a4.co_doc = a3.co_doc AND a4.co_tipdoc = a3.co_tipdoc) \n";
                strSQL+="          LEFT OUTER JOIN tbm_inv AS a1 ON (a1.co_emp = a4.co_emp AND a1.co_itm = a4.co_itm)\n";
                strSQL+="	   LEFT OUTER JOIN tbm_cabMovInv AS a2 \n";
                strSQL+="	   ON a3.co_emp=a2.co_emprelpedembimp AND a3.co_loc=a2.co_locrelpedembimp \n";
                strSQL+="	   AND a3.co_tipDoc=a2.co_tipdocrelpedembimp AND a3.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I') \n";
                strSQL+="	   WHERE a3.st_reg NOT IN('E','I')  \n";
                strSQL+="	   AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL) \n";
                strSQL+="	   AND a2.co_emp IS NULL \n";
                strSQL+=strFil+"\n";
                strSQL+=") AS x \n";
                strSQL+="ORDER BY x.fe_arr, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.fe_doc, x.tx_numPed \n";
                 
                System.out.println("CABECERA FILTRO: \n"+strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next())
                {
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP, "" + rst.getString("co_emp"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC, "" + rst.getString("co_loc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC, "" + rst.getString("co_tipDoc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC, "" + rst.getString("co_doc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED, "" + rst.getString("tx_numPed"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL, "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL, "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB, "" + rst.getString("fe_arrTitPed"));
                    arlDatPedEmb.add(arlRegPedEmb);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
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
    }*/
   
   
    private boolean escondeColumnasPedidoSinValor()
    {
        boolean blnRes=true;
        BigDecimal bdeValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try
        {
//            ArrayList arlColHid=new ArrayList();
            
            for (int d=0; d<(arlDatPedEmb.size()); d++){
                objUti.setStringValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_EST_COL, "");
            }
          
            for (int j=(intNumColFinPedEmb-1); j>=intNumColIniPedEmb; j--)
            {
                objTblMod.removeSystemHiddenColumn(j, tblDat);
            }
            for (int j=(intNumColFinPedEmb-1); j>=intNumColIniPedEmb; j--)
            {
                //Es menos 1 porque la ultima columna es en realidad de TOTAL.
                for (int i=0;i<objTblMod.getRowCountTrue(); i++)
                {
                    bdeValPed=bdeValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }               
                if ((bdeValPed.compareTo(new BigDecimal("0"))>0) ||  (bdeValPed.compareTo(new BigDecimal("0"))<0))
                {
                    
                }
                else
                {
                    //Se elimina la columna y se coloca como estado 'E' en el arraylist
                    for (int k=0;k<arlDatPedEmb.size(); k++)
                    {
                        intNumCol=objUti.getIntValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL);
                        if (! strEstArl.equals("E"))
                        {
                            if (j==intNumCol)
                            {
                                objUti.setStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat); 
//                                arlColHid.add(""+intNumCol);//columnas ocultas
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bdeValPed=new BigDecimal(BigInteger.ZERO);
            }
//            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);  // JM 
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

}