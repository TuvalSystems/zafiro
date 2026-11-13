/*
 * ZafCom119.java
 *
 * Created on 02 de agosto de 2007, 16:15 PM
 */
package Compras.ZafCom119;
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
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConMySQL.ZafVenConMySQL; 
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Vector;
import Librerias.ZafVenConMySQL.ZafVenConItmMySQL01;
import java.util.HashMap;

/**
 *
 * @author  Denis Toledo
 */
public class ZafCom119 extends javax.swing.JInternalFrame 
{
    //Constantes.
    //Columnas del JTable: TblEmp
    static final int INT_TBL_EMP_LIN=0;                         //Línea.
    static final int INT_TBL_EMP_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;                     //Código de la empresa.
                     //Nombre de la empresa.
    String strCodCli="";
   String strDesCli="";
   String strCodVen="";
   String strDesVen="";
    //Columnas del JTable: TblDat PASO 1
    static final int INT_TBL_DAT_LIN=0;   
    private String strCodLoc, strNomLoc;//Línea
//    static final int INT_TBL_DAT_PED=1;                 //Código maestro del item.
//    static final int INT_TBL_DAT_FAC=2;                 //Código de empresa del código del ítem.
//    static final int INT_TBL_DAT_EST_FAC=3;                     //Código del item.
//    static final int INT_TBL_DAT_FEC_FAC=4;                 //Código alterno del ítem.
//    static final int INT_TBL_DAT_ORD_DES=5;                     //Nombre del item.
//    static final int INT_TBL_DAT_EST_ORD_DES=6;             //Descripción corta de la unidad de medida.
//    static final int INT_TBL_DAT_RUC_CLI=7; 
//    static final int INT_TBL_DAT_NOM_CLI=8; 
//    static final int INT_TBL_DAT_BOD=9; 
//    static final int INT_TBL_DAT_PTO_VTA=10; 
//    static final int INT_TBL_DAT_USR_VTA=11; 
//    static final int INT_TBL_DAT_COND_PAG=12; 
//    static final int INT_TBL_DAT_BTN_DET=13; 
    
    
    static final int INT_TBL_DAT_OD=1;                 //Código maestro del item.
    static final int INT_TBL_DAT_EST_OD=2;                 //Código de empresa del código del ítem.
    static final int INT_TBL_DAT_FEC_HOR_LIB=3;                     //Código del item.
    static final int INT_TBL_DAT_PED=4;                 //Código alterno del ítem.
    static final int INT_TBL_DAT_FAC=5;                     //Nombre del item.
    static final int INT_TBL_DAT_FEC_FAC=6;             //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_EST_FAC=7; 
    static final int INT_TBL_DAT_CLI=8; 
    static final int INT_TBL_DAT_DIR=9; 
    static final int INT_TBL_DAT_CIU=10; 
    static final int INT_TBL_DAT_BOD=11; 
    static final int INT_TBL_DAT_TIP_DES=12; 
    static final int INT_TBL_DAT_VEN=13; 
    static final int INT_TBL_DAT_DIA_PEN=14; 
    static final int INT_TBL_DAT_ITEM=15; 
    static final int INT_TBL_DAT_ITEM_3L=16; 
    static final int INT_TBL_DAT_ITEM_DES=17; 
    static final int INT_TBL_DAT_CAN_FAC=18; 
    static final int INT_TBL_DAT_PES_FAC=19; 
    static final int INT_TBL_DAT_CAN_DES=20; 
    static final int INT_TBL_DAT_PES_DES=21; 
    static final int INT_TBL_DAT_CAN_PEN=22; 
    static final int INT_TBL_DAT_PES_PEN=23;
    
    static final int INT_TBL_DAT_UBI_ITM=24; 
    
    
    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModEmp;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;  
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;                                //Permisos de Usuario.
    private ZafVenConMySQL vcoItm;    
    private ZafVenConMySQL vcoLoc; //Ventana de Consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecEstReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm; 
    private ZafVenConMySQL objVenConCLi;
    private ZafVenConMySQL objVenConVen; 
    private ZafVenConItmMySQL01 objVenConItm01_01;
    
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de bodegas.
    
    private ZafTblCelRenBut objTblCelRenBut;
    // private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafSelFec objSelFec;
            
     private static ArrayList<String> arrItems = new ArrayList<>();
    private static ArrayList<String> arrBodegas = new ArrayList<>();    
    private HashMap<String, String> ubicacionMap = new HashMap<>();
   
    /** Crea una nueva instancia de la clase ZafCom13. *///constructor
    public ZafCom119(ZafParSis obj) {
        try {
            initComponents();
            objParSis=(ZafParSis)obj.clone();
//            // Inicializar objetos.
//            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
//            objUti = new ZafUtil();
//            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
//            objTblCelEdiButGenDG1 = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
//            objTblCelRenButDG1 = new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();

//            // Configurar ZafSelFec:
//            objSelFec = new ZafSelFec();
//            objSelFec.setCheckBoxVisible(true);
//            objSelFec.setCheckBoxChecked(true);
//            objSelFec.setTitulo("Fecha del documento");
//            objSelFec.setCheckBoxVisible(false);
//            panFil.add(objSelFec);
//            objSelFec.setBounds(4, 4, 472, 72);

//            // Configuración de ZafDatePicker:
//            Librerias.ZafDate.ZafDatePicker txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()), "d/m/y");
//            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
//            txtFecDoc.setHoy();
//            java.util.Calendar objFec = java.util.Calendar.getInstance();
//            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
//            int[] fecDoc = txtFecDoc.getFecha(objSelFec.getFechaDesde());
//            if (fecDoc != null) {
//                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
//                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
//                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
//            }
//            java.util.Calendar objFecPagActual = objFec.getInstance();
//            objFecPagActual.setTime(objFec.getTime());
//            objFecPagActual.add(java.util.Calendar.DATE, -30);
//			
//            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
//            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
//            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
//            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
//            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");
//			
//            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));

            // Configurar el formulario
            if (!configurarFrm()) {
                exitForm();
            }

            // Establecer el título de la ventana y el texto del label
            this.setTitle(objParSis.getNombreMenu() + " " );
            lblTit.setText(objParSis.getNombreMenu());

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
             objUti.mostrarMsgErr_F1(this, e);
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
        panEmp = new javax.swing.JPanel();
        spnEmp = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        panDetFil = new javax.swing.JPanel();
        panEstadoOD = new javax.swing.JPanel();
        lblEstReg = new javax.swing.JLabel();
        cboEstOrdDes = new javax.swing.JComboBox();
        panDoc = new javax.swing.JPanel();
        lblOd = new javax.swing.JLabel();
        txtOd2 = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtNomVen = new javax.swing.JTextField();
        txtCodVen = new javax.swing.JTextField();
        lblVen = new javax.swing.JLabel();
        lblCli = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butcli = new javax.swing.JButton();
        panFilItm = new javax.swing.JPanel();
        optTod1 = new javax.swing.JRadioButton();
        optFil1 = new javax.swing.JRadioButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.PAGE_START);

        panFil.setLayout(new java.awt.BorderLayout());

        panEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panEmp.setPreferredSize(new java.awt.Dimension(464, 200));
        panEmp.setLayout(new java.awt.GridLayout());

        spnEmp.setPreferredSize(new java.awt.Dimension(452, 482));

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnEmp.setViewportView(tblEmp);

        panEmp.add(spnEmp);

        panFil.add(panEmp, java.awt.BorderLayout.NORTH);

        panDetFil.setLayout(new java.awt.BorderLayout());

        panEstadoOD.setPreferredSize(new java.awt.Dimension(100, 30));
        panEstadoOD.setLayout(null);

        lblEstReg.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblEstReg.setText("Estado de Orden de Despacho:");
        lblEstReg.setToolTipText("Estado del registro:");
        panEstadoOD.add(lblEstReg);
        lblEstReg.setBounds(0, 0, 160, 21);

        cboEstOrdDes.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        cboEstOrdDes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cboEstOrdDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstOrdDesActionPerformed(evt);
            }
        });
        panEstadoOD.add(cboEstOrdDes);
        cboEstOrdDes.setBounds(178, 0, 120, 21);

        panDetFil.add(panEstadoOD, java.awt.BorderLayout.NORTH);

        panDoc.setPreferredSize(new java.awt.Dimension(100, 70));
        panDoc.setLayout(null);

        lblOd.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblOd.setText("OD:");
        lblOd.setToolTipText("Local");
        panDoc.add(lblOd);
        lblOd.setBounds(10, 70, 18, 15);

        txtOd2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOd2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOd2FocusLost(evt);
            }
        });
        txtOd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOd2ActionPerformed(evt);
            }
        });
        panDoc.add(txtOd2);
        txtOd2.setBounds(70, 70, 160, 20);

        butVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panDoc.add(butVen);
        butVen.setBounds(320, 110, 40, 20);

        txtNomVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panDoc.add(txtNomVen);
        txtNomVen.setBounds(140, 110, 180, 21);

        txtCodVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        panDoc.add(txtCodVen);
        txtCodVen.setBounds(70, 110, 70, 21);

        lblVen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblVen.setText("Vendedor :");
        panDoc.add(lblVen);
        lblVen.setBounds(10, 110, 54, 15);

        lblCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblCli.setText("Cliente:");
        panDoc.add(lblCli);
        lblCli.setBounds(10, 90, 35, 15);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panDoc.add(optTod);
        optTod.setBounds(10, 10, 131, 23);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panDoc.add(optFil);
        optFil.setBounds(10, 30, 297, 23);

        txtCodCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panDoc.add(txtCodCli);
        txtCodCli.setBounds(70, 90, 90, 21);

        txtNomCli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panDoc.add(txtNomCli);
        txtNomCli.setBounds(160, 90, 160, 21);

        butcli.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butcli.setText("...");
        butcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcliActionPerformed(evt);
            }
        });
        panDoc.add(butcli);
        butcli.setBounds(320, 90, 40, 20);

        panDetFil.add(panDoc, java.awt.BorderLayout.CENTER);

        panFilItm.setPreferredSize(new java.awt.Dimension(410, 130));
        panFilItm.setLayout(null);

        bgrFil.add(optTod1);
        optTod1.setText("Todos los items");
        optTod1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTod1ItemStateChanged(evt);
            }
        });
        panFilItm.add(optTod1);
        optTod1.setBounds(8, 2, 410, 14);

        bgrFil.add(optFil1);
        optFil1.setText("Sólo los items que cumplan el criterio seleccionado");
        panFilItm.add(optFil1);
        optFil1.setBounds(8, 18, 410, 14);

        panDetFil.add(panFilItm, java.awt.BorderLayout.SOUTH);

        panFil.add(panDetFil, java.awt.BorderLayout.CENTER);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 643);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            if (butCon.getText().equals("Consultar")) 
            {
                blnCon = true;
                if (objThrGUI == null) 
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else 
            {
                blnCon = false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void cboEstOrdDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstOrdDesActionPerformed
      
    }//GEN-LAST:event_cboEstOrdDesActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {

            strCodCli="";  strDesCli="";
            strCodVen="";  strDesVen="";

            txtCodCli.setText("");
            txtNomCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");

            

        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void txtOd2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOd2FocusGained
        strNomLoc=txtOd2.getText();
        txtOd2.selectAll();
    }//GEN-LAST:event_txtOd2FocusGained

    private void txtOd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOd2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtOd2.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtOd2.getText().equals(""))
            {
                txtOd2.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
        txtOd2.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
//        if (txtOd1.getText().length()>0)
//        {
//            optFil.setSelected(true);
//        }
    }//GEN-LAST:event_txtOd2FocusLost

    private void txtOd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOd2ActionPerformed
        txtOd2.transferFocus();
    }//GEN-LAST:event_txtOd2ActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)) {
            if(txtCodCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
            BuscarCliente("a1.CLI_RUC",txtCodCli.getText(),0);
        }else
        txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        if (!txtNomCli.getText().equalsIgnoreCase(strDesCli)) {
            if (txtNomCli.getText().equals("")) {
                txtCodCli.setText("");
                txtNomCli.setText("");
            }else
            BuscarCliente("a1.CLI_RAZON_SOCIAL",txtNomCli.getText(),1);
        }else
        txtNomCli.setText(strDesCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void butcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcliActionPerformed
        // TODO add your handling code here:
        objVenConCLi.setTitle("Listado de Clientes");
        objVenConCLi.setCampoBusqueda(1);
        objVenConCLi.show();
        if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
            txtCodCli.setText(objVenConCLi.getValueAt(1));
            txtNomCli.setText(objVenConCLi.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butcliActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        // TODO add your handling code here:
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        // TODO add your handling code here:
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)) {
            if(txtCodVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            BuscarVendedor("a1.USERID",txtCodVen.getText(),0);
        }else
        txtCodVen.setText(strCodVen);
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        // TODO add your handling code here:
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        // TODO add your handling code here:
        strDesVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        // TODO add your handling code here:
        if (!txtNomVen.getText().equalsIgnoreCase(strDesVen)) {
            if(txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }else
            BuscarVendedor("a1.USERNAME",txtNomVen.getText(),1);
        }else
        txtNomVen.setText(strDesVen);
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        // TODO add your handling code here:
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        // TODO add your handling code here:
        objVenConVen.setTitle("Listado de Vendedores");
        objVenConVen.setCampoBusqueda(1);
        objVenConVen.show();
        if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
            txtCodVen.setText(objVenConVen.getValueAt(1));
            txtNomVen.setText(objVenConVen.getValueAt(2));
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butVenActionPerformed

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void optTod1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTod1ItemStateChanged
        // TODO add your handling code here:
        if (optTod.isSelected())
        {
            objVenConItm01_01.limpiar();
        }
    }//GEN-LAST:event_optTod1ItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butcli;
    private javax.swing.JComboBox cboEstOrdDes;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblOd;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optFil1;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JRadioButton optTod1;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDetFil;
    private javax.swing.JPanel panDoc;
    private javax.swing.JPanel panEmp;
    private javax.swing.JPanel panEstadoOD;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnEmp;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtOd2;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.04");
            lblTit.setText(strAux);
           
            
            //Configurar Combos.
            configurarComboEstReg();
            //Configurar los JTables.
            configurarTblEmp();
            configurarTblDat();
            cargarEmp();
//            agregarColTblDat();
            //Configurar ZafVenConItm01:
            objVenConItm01_01=new ZafVenConItmMySQL01(objParSis, "art.", optFil1); // el a1. sale de la funcion que hace la consulta supuestamente
            panFilItm.add(objVenConItm01_01); // defino en que panel voy a agregar el menu de items
            objVenConItm01_01.setBounds(30, 50, 656, 20); // define coordenadas (donde se va a ubicar el menu de seleccion de items)
            
            
          
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarComboEstReg() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar el combo "Estado de registro".
            vecEstReg = new Vector();
            vecEstReg.add("");
            vecEstReg.add("L");
            vecEstReg.add("P");
            cboEstOrdDes.addItem("(Todos)");
            cboEstOrdDes.addItem("Liberada");
            cboEstOrdDes.addItem("Pendiente");
            cboEstOrdDes.setSelectedIndex(0);   //Por default muestre solo los items activos. 0 es la 1era 
        } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    
    /**
     * Esta función configura el JTable "tblEmp".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblEmp()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_LIN,"");
            vecCab.add(INT_TBL_EMP_CHK,"");
            vecCab.add(INT_TBL_EMP_COD_EMP,"Bodegas");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModEmp=new ZafTblMod();
            objTblModEmp.setHeader(vecCab);
            tblEmp.setModel(objTblModEmp);
            //Configurar JTable: Establecer tipo de selección.
            tblEmp.setRowSelectionAllowed(true); // Permitir la selección de filas
            tblEmp.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblEmp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(200);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblEmp.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMaxWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMinWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setPreferredWidth(0);
            //  tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setResizable(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblEmp.getTableHeader().addMouseMotionListener(new ZafMouMotAdaEmp());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblEmp.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblEmpMouseClicked(evt); // habilita el marcar todos los checks
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_EMP_CHK);
            objTblModEmp.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla.
            //  objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
            //  objTblBus=new ZafTblBus(tblEmp);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChk);
            
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                if (objTblCelEdiChk.isChecked()) {// si esta marcado
                    // Recorre todas las filas y desmarcar los cheks, excepto la  seleccionada inicialmente
                    for (int i = 0; i < objTblModEmp.getRowCountTrue(); i++) {// este objeto se debe utilizar para las validaciones
                        if (i != tblEmp.getSelectedRow()) {
                            objTblModEmp.setValueAt(false, i, INT_TBL_EMP_CHK); // Desmarcar los checks en otras filas
                        }
                    }
                    // oculta supuestamente
                    // objTblMod.removeSystemHiddenColumn(7 + tblEmp.getSelectedColumn() , tblDat);
                } 
//                else {
//                    // muestra supuestamente
//                   // objTblMod.addSystemHiddenColumn(7 + tblEmp.getSelectedColumn(), tblDat);
//                }
            }
        });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModEmp.setModoOperacion(objTblModEmp.INT_TBL_EDI);
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        java.util.ArrayList arlAux=new java.util.ArrayList();
        try
        {
            intNumFil=objTblModEmp.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblEmp.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModEmp.setChecked(true, i, INT_TBL_EMP_CHK);
                    }
//                    objTblMod.removeAllSystemHiddenColumns(tblDat);
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModEmp.setChecked(false, i, INT_TBL_EMP_CHK);
                        arlAux.add("" + (7 + i));
                    }
//                    objTblMod.addSystemHiddenColumns(arlAux, tblDat);
                    blnMarTodChkTblEmp=true;
                }
                arlAux=null;
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            vecCab=new Vector(7);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_OD,"Orden Desp.");
            vecCab.add(INT_TBL_DAT_EST_OD, "Estado OD");
            vecCab.add(INT_TBL_DAT_FEC_HOR_LIB,"liberación OD");
            vecCab.add(INT_TBL_DAT_PED,"Pedido");
            vecCab.add(INT_TBL_DAT_FAC,"Factura");
            vecCab.add(INT_TBL_DAT_FEC_FAC,"Fecha Fac.");
            vecCab.add(INT_TBL_DAT_EST_FAC,"Estado Fac.");
            vecCab.add(INT_TBL_DAT_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_DIR,"Dirección");
            vecCab.add(INT_TBL_DAT_CIU,"Ciudad");
            vecCab.add(INT_TBL_DAT_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_TIP_DES,"Tipo Desp.");
            vecCab.add(INT_TBL_DAT_VEN,"Vendedor");
            vecCab.add(INT_TBL_DAT_DIA_PEN,"Días Pend.");
            vecCab.add(INT_TBL_DAT_ITEM,"Item");
            vecCab.add(INT_TBL_DAT_ITEM_3L,"Item 3L");
            vecCab.add(INT_TBL_DAT_ITEM_DES,"Descripción");
            vecCab.add(INT_TBL_DAT_CAN_FAC,"Cant. Fac.");
            vecCab.add(INT_TBL_DAT_PES_FAC,"Peso Fact.");
            vecCab.add(INT_TBL_DAT_CAN_DES,"Cant. Desp.");
            vecCab.add(INT_TBL_DAT_PES_DES,"Peso Desp.");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Cant. Pend.");
            vecCab.add(INT_TBL_DAT_PES_PEN,"Peso Pend.");
            vecCab.add(INT_TBL_DAT_UBI_ITM,"Ubicación");

            

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
//            tcmAux.getColumn(INT_TBL_DAT_PED).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_FAC).setPreferredWidth(130);
//            tcmAux.getColumn(INT_TBL_DAT_EST_FAC).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_FEC_FAC).setPreferredWidth(70);
//            tcmAux.getColumn(INT_TBL_DAT_ORD_DES).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_EST_ORD_DES).setPreferredWidth(70);
//            tcmAux.getColumn(INT_TBL_DAT_RUC_CLI).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(257);
//            tcmAux.getColumn(INT_TBL_DAT_BOD).setPreferredWidth(70);
//            tcmAux.getColumn(INT_TBL_DAT_PTO_VTA).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_USR_VTA).setPreferredWidth(70);
//            tcmAux.getColumn(INT_TBL_DAT_COND_PAG).setPreferredWidth(100);
//            tcmAux.getColumn(INT_TBL_DAT_BTN_DET).setPreferredWidth(30);
            
            
            tcmAux.getColumn(INT_TBL_DAT_OD).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_EST_OD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_LIB).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_PED).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FAC).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_FEC_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CLI).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DIR).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CIU).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BOD).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TIP_DES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIA_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_ITEM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ITEM_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ITEM_DES).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(100);
            
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            // tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);

            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            //Columna Código de la  Empresa
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ITM).setCellRenderer(objTblCelRenLbl);
//            objTblCelRenLbl=null;
            //Otras Columnas
//            objTblCelRenLbl=new ZafTblCelRenLbl();
//            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//            tcmAux.getColumn(INT_TBL_DAT_PED).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_FAC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_EST_FAC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_FEC_FAC).setCellRenderer(objTblCelRenLbl);
            
//            // JM 8/Dic/202023 
//            vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_BTN_DET);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
//            
//            
//            //botones agregados
//            objTblCelRenBut=new ZafTblCelRenBut();
//            tcmAux.getColumn(INT_TBL_DAT_BTN_DET).setCellRenderer(objTblCelRenBut);
//            objTblCelRenBut=null;
//            
//            ButDetalle butDetalle = new ButDetalle(tblDat, INT_TBL_DAT_BTN_DET); //*****
            
            // JM 8/Dic/202023 
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento. sorting
            objTblOrd=new ZafTblOrd(tblDat);
            //Libero los objetos auxiliares.
            tcmAux=null;
            configurarVenConClientes();
            configurarVenConVendedor();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
//     */
//    private class ButDetalle extends Librerias.ZafTableColBut.ZafTableColBut_uni{
//        public ButDetalle(javax.swing.JTable tbl, int intIdx){
//            super(tbl,intIdx, "Detalle pendiente.");
//           
//        }
//        @Override
//        public void butCLick() {
//           int intCol = tblDat.getSelectedRow();
//           String strPedido = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_PED  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_PED  ).toString());
//          llamarVen(strPedido);
//        }
//    }
//    
//    
//    private void llamarVen(String strPedido){
//            ZafCom119_01 obj1 = new  Compras.ZafCom119.ZafCom119_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strPedido);
////            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
//            obj1.show();
//    }
       
    /**
     * Esta función permite consultar las empresas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las empresae.
     * <LI>Si se ingresa a cualquier otra empresa se muestra sólo la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarEmp()
    {
        int intCodEmp;
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
          Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            // conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if (conLoc!=null)
            {
            stmLoc = conLoc.createStatement();
                //Armar la sentencia SQL.
              if (objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+=" select a2.tx_descor as bod_bodega \n";
                strSQL+=" FROM tbm_bod as a2 \n";
                strSQL+=" where a2.co_emp=0 and a2.st_bodRep ='S'   ";
                strSQL+=" order by 1\n";
              }
              else{
                strSQL="";
                strSQL+=" select  a2.tx_descor as bod_bodega \n";
                strSQL+=" from tbr_bodUsr as a1 \n";
                strSQL+=" inner join tbm_bod as a2 on (a1.co_bod=a2.co_bod and a2.co_emp=0 and a2.st_bodRep ='S')\n";
                strSQL+=" inner join tbm_usr as a3 on (a1.co_usr=a3.co_usr)\n";
                strSQL+="  where a1.co_emp=0 and a1.st_reg in ('P','A') and  a1.co_usr="+ objParSis.getCodigoUsuario() + "";;
                strSQL+=" order by 1\n";
              }

                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(false));
                    vecReg.add(INT_TBL_EMP_COD_EMP,rstLoc.getString("bod_bodega"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                //Asignar vectores al modelo.
                objTblModEmp.setData(vecDat);
                tblEmp.setModel(objTblModEmp);
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

    private boolean configurarVenConVendedor() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.USERID");
            arlCam.add("a1.USERNAME");
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
                        
            String strSQL = "SELECT DISTINCT a1.USERID, a2.USERNAME \n" +
                        "FROM tuvalapi.gp_pedidos AS a1 \n" +
                        "INNER JOIN tuvalapi.gp_usuarios AS a2 ON (a1.USERID = RTRIM(a2.USERID)) \n" +
                        "WHERE a1.USERID NOT IN ('jmarin', 'dtoledo', 'cramirez', 'spinargote', 'adminWeb') " +
                        "ORDER BY a1.USERID\n";

            objVenConVen=new ZafVenConMySQL(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
     }

private boolean configurarVenConClientes() {
        boolean blnRes=true;
        try {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.CLI_RUC");
            arlCam.add("a1.CLI_RAZON_SOCIAL");
            ArrayList arlAli=new ArrayList();
            arlAli.add("RUC");
            arlAli.add("Razon Social.");
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
                        
            String strSQL = "select DISTINCT a1.CLI_RUC, a1.CLI_RAZON_SOCIAL \n" +
                            "FROM tuvalapi.gp_pedidos AS a1 \n" +
                            "ORDER BY a1.CLI_RAZON_SOCIAL ASC \n";

            objVenConCLi=new ZafVenConMySQL(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }catch (Exception e) {  blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
       return blnRes;
     }

     public void BuscarCliente(String campo,String strBusqueda,int tipo){
        objVenConCLi.setTitle("Listado de Clientes");
        if(objVenConCLi.buscar(campo, strBusqueda )) {
          txtCodCli.setText(objVenConCLi.getValueAt(1));
          txtNomCli.setText(objVenConCLi.getValueAt(2));
          optFil.setSelected(true);
        }else{
            objVenConCLi.setCampoBusqueda(tipo);
            objVenConCLi.cargarDatos();
            objVenConCLi.show();
            if (objVenConCLi.getSelectedButton()==objVenConCLi.INT_BUT_ACE) {
               txtCodCli.setText(objVenConCLi.getValueAt(1));
               txtNomCli.setText(objVenConCLi.getValueAt(2));
               optFil.setSelected(true);
            }else{
                txtCodCli.setText(strCodCli);
                txtNomCli.setText(strDesCli);
    }}}
     
    public void BuscarVendedor(String campo,String strBusqueda,int tipo){
      objVenConVen.setTitle("Listado de Vendedores");
      if(objVenConVen.buscar(campo, strBusqueda )) {
          txtCodVen.setText(objVenConVen.getValueAt(1));
          txtNomVen.setText(objVenConVen.getValueAt(2));
          optFil.setSelected(true);
      }else{
            objVenConVen.setCampoBusqueda(tipo);
            objVenConVen.cargarDatos();
            objVenConVen.show();
            if (objVenConVen.getSelectedButton()==objVenConVen.INT_BUT_ACE) {
                txtCodVen.setText(objVenConVen.getValueAt(1));
                txtNomVen.setText(objVenConVen.getValueAt(2));
                optFil.setSelected(true);
            }else{
                txtCodVen.setText(strCodVen);
                txtNomVen.setText(strDesVen);
      }}}
    
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter //
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
                case INT_TBL_DAT_OD:
                    strMsg="Orden de Despacho";
                    break;
                case INT_TBL_DAT_EST_OD:
                    strMsg="Estado Orden Despacho";
                    break;    
                case INT_TBL_DAT_FEC_HOR_LIB:
                    strMsg="Fecha Hora Liberación OD";
                    break;
                case INT_TBL_DAT_PED:
                    strMsg="Pedido";
                    break;
                case INT_TBL_DAT_FAC:
                    strMsg="Factura";
                    break;
                case INT_TBL_DAT_FEC_FAC:
                    strMsg="Fecha Factura";
                    break;
                case INT_TBL_DAT_EST_FAC:
                    strMsg="Estado Factura";
                    break;
                case INT_TBL_DAT_CLI:
                    strMsg="Nombre de Cliente";
                    break;
                case INT_TBL_DAT_DIR:
                    strMsg="Dirección";
                    break;
                case INT_TBL_DAT_CIU:
                    strMsg="Ciudad";
                    break;
                case INT_TBL_DAT_BOD:
                    strMsg="Bodega";
                    break;
                case INT_TBL_DAT_TIP_DES:
                    strMsg="Tipo Despacho";
                    break;
                case INT_TBL_DAT_VEN:
                    strMsg="Vendedor";
                    break;
                case INT_TBL_DAT_DIA_PEN:
                    strMsg="Días Pendientes";
                    break;
                case INT_TBL_DAT_ITEM:
                    strMsg="Item";
                    break;
                case INT_TBL_DAT_ITEM_3L:
                    strMsg="Codigo 3 Letras";
                    break;
                case INT_TBL_DAT_ITEM_DES:
                    strMsg="Descripción";
                    break;
                case INT_TBL_DAT_CAN_FAC:
                    strMsg="Cantidad Facturada";
                    break;
                case INT_TBL_DAT_PES_FAC:
                    strMsg="Peso Facturado";
                    break;
                case INT_TBL_DAT_CAN_DES:
                    strMsg="Cantidad Despachada";
                    break;
                case INT_TBL_DAT_PES_DES:
                    strMsg="Peso Despachado";
                    break;
                case INT_TBL_DAT_CAN_PEN:
                    strMsg="Cantidad Pendiente";
                    break;
                 case INT_TBL_DAT_PES_PEN:
                    strMsg="Peso Pendiente";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación";
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
    private class ZafMouMotAdaEmp extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblEmp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_LIN:
                    strMsg="";
                    break;
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Bodega";
                    break;
               
                default:
                    strMsg="";
                    break;
            }
            tblEmp.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBod, i, j;
        //Validar que esté seleccionada al menos un local si es la empresa grupo.
        intNumFilTblBod=objTblModEmp.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBod; j++)
        {
            if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una Bodega.<BR>Seleccione una Bodega y vuelva a intentarlo.</HTML>");
            tblEmp.requestFocus();
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
     private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        boolean blnEmp = false;
        String sqlFil ="",strEmp ="";
        java.sql.Connection conLoc;//conectarme
        java.sql.Statement stmLoc;//conectarme
        java.sql.ResultSet rstLoc;//obtener informacion
        
        int intNumFilTblEmp,  j, intEstReg=0;
        String strAux="";
        
        try 
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intNumFilTblEmp = objTblModEmp.getRowCountTrue();
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                
                //Filtrar por "Item".
                if (objVenConItm01_01.isCondicionAplicada())
                {
                    strAux+=objVenConItm01_01.getCondicionesSQL();
                }
                
                
                for (int k = 0; k < tblEmp.getRowCount(); k++) 
                {
                    if (tblEmp.getValueAt(k, INT_TBL_EMP_CHK).toString().equals("true"))
                    {
                        if (!blnEmp) //Primera vez que ingresa.
                        {
                            strEmp += " ( ";
                        } else {
                            strEmp += " OR ";
                        }
                        strEmp += " (od.bod_bodega=" + objUti.codificar(tblEmp.getValueAt(k, INT_TBL_EMP_COD_EMP), 0)   + ") ";
                        blnEmp = true;
                    }
                }
                if (blnEmp) 
                {
                    strEmp += " ) ";
                    sqlFil += strEmp;
                }
                 
                System.out.println(""+cboEstOrdDes.getSelectedItem()); 
                String strYrmp = cboEstOrdDes.getSelectedItem().toString(); 

                if(strYrmp.equals("(Todos)")){
                    strAux+=" and od.estado IN ('pendiente', 'liberada')  ";
                }else if(strYrmp.equals("Pendiente")){
                         strAux+=" and od.estado IN ('pendiente') ";
                }
                else{
                     strAux+=" and od.estado IN ('liberada') ";
                }
                    strSQL ="";
                    strSQL+="select od.orden, od.estado  , od.fecha_hora_liberacion , peds.pedido, peds.factura, peds.fecha_factura, peds.estado as estado_fact, peds.CLI_RAZON_SOCIAL as cliente, suc.cli_suc_direccion as direccion , \n";
                    strSQL+="suc.cli_suc_ciudad as ciudad,od.bod_bodega as bodega, peds.despacho, peds.USERID as vendedor, case when datediff(current_date(),od.fecha_hora_liberacion) is null then 0 else datediff(current_date(), od.fecha_hora_liberacion) end as dias_pendientes,\n";
                    strSQL+="dets.ART_CODIGO as item, dets.ART_CODIGO_TRES_LETRAS as item3l, dets.ART_DESCRIPCION as descripcion,  dets.ART_CANTIDAD  as cant_facturada,  (dets.ART_CANTIDAD*( art.art_peso )) as peso_fact ,\n";
                    strSQL+="CASE WHEN  g.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE g.GUI_REM_CANTIDAD END as cant_despachada,  ((CASE WHEN  g.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE g.GUI_REM_CANTIDAD END)*art.art_peso) as peso_despachado,\n";
                    strSQL+="(dets.ART_CANTIDAD  - CASE WHEN  g.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE g.GUI_REM_CANTIDAD END  ) as cant_pendiente, ((dets.ART_CANTIDAD  - CASE WHEN  g.GUI_REM_CANTIDAD IS NULL THEN 0 ELSE g.GUI_REM_CANTIDAD END  )* art.art_peso) as peso_pendiente\n";
                    strSQL+="from tuvalapi.gp_pedidos as peds \n";
                    strSQL+="inner join tuvalapi.gp_orden_despachos as od on (od.estado in ('pendiente','liberada') and peds.id=od.pedido_id  )\n";
                    strSQL+=" inner join tuvalapi.gp_pedido_detalles as dets on (peds.id=dets.pedido_id)\n";
                    strSQL+=" inner join tuvalapi.gp_articulos as art on (dets.ART_CODIGO=art.art_codigo)\n";
                    strSQL+=" INNER JOIN tuvalapi.gp_cliente_sucursals as suc on (peds.sucursal_id=suc.id)\n";
                    strSQL+=" LEFT JOIN( \n";
                    strSQL+="		SELECT a2.pedido, a2.GUI_REM_GUIA_REMISION, a2.estado,a1.detalle_id,a1.GUI_REM_CODI_INTERNO,  a1.GUI_REM_CANTIDAD, \n";
                    strSQL+="           case when art.art_peso is null then 0 else art.art_peso end as peso_u ,\n";
                    strSQL+="			((case when art.art_peso is null then 0 else art.art_peso end) *a1.GUI_REM_CANTIDAD) as peso_total\n";
                    strSQL+="	FROM tuvalapi.gp_guia_remision_detalles as a1     \n";
                    strSQL+="		INNER JOIN tuvalapi.gp_guia_remisions as a2 on (a1.guia_remision_id=a2.id)\n";
                    strSQL+="		inner join tuvalapi.gp_articulos as art on (a1.GUI_REM_CODI_INTERNO=art.art_codigo)\n";
                    strSQL+="        INNER JOIN tuvalapi.gp_pedidos as ped on (a2.pedido=ped.pedido ) \n";
                    strSQL+="        INNER JOIN tuvalapi.gp_orden_despachos as od on (ped.id=od.pedido_id and od.estado in ('pendiente','liberada')) \n";
                    strSQL+="	  WHERE  a2.tipo='despacho' \n";
                    strSQL+="	) as g on (dets.id=g.detalle_id and peds.pedido=g.pedido) \n";
                    strSQL+="where od.orden is not null -- od.estado in ('pendiente','liberada') \n";
                    if(sqlFil.length()>0){
                        strSQL+=" and "+sqlFil;
                    }
                    if(txtOd2.getText().length()>0){
                    strSQL+=" AND od.orden like "+ objUti.codificar(txtOd2.getText())+" \n";
                    }
                    if(txtCodCli.getText().length()>0){
                    strSQL+=" AND peds.CLI_RAZON_SOCIAL like "+ objUti.codificar(txtNomCli.getText())+" \n";
                    }
                    if(txtCodVen.getText().length()>0){
                    strSQL+=" AND peds.USERID like "+ objUti.codificar(txtCodVen.getText())+" \n";
                    }
                    strSQL+=""+strAux;
                    strSQL+=" ORDER BY od.orden asc ; \n";
                    
                    System.out.println("ZafCom119.cargarDetReg.EMPRESAS: " + strSQL);
                    rstLoc = stmLoc.executeQuery(strSQL);
                    //ArrayList<String> itemsListado = new ArrayList<>();
                    //ArrayList<String> bodegasListado = new ArrayList<>();
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next()) 
                {
                    String item = rstLoc.getString("item");
                    String bodega = rstLoc.getString("bodega");
                    
                    // Agregar a los arreglos
                    arrItems.add(rstLoc.getString("item"));
                    arrBodegas.add(rstLoc.getString("bodega"));
                    
                    if (blnCon) 
                    {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_OD, rstLoc.getString("orden"));
                        vecReg.add(INT_TBL_DAT_EST_OD, rstLoc.getString("estado"));
                        vecReg.add(INT_TBL_DAT_FEC_HOR_LIB, rstLoc.getString("fecha_hora_liberacion"));
                        vecReg.add(INT_TBL_DAT_PED, rstLoc.getString("pedido"));
                        vecReg.add(INT_TBL_DAT_FAC, rstLoc.getString("factura"));
                        vecReg.add(INT_TBL_DAT_FEC_FAC, rstLoc.getString("fecha_factura"));
                        vecReg.add(INT_TBL_DAT_EST_FAC, rstLoc.getString("estado_fact"));
                        vecReg.add(INT_TBL_DAT_CLI, rstLoc.getString("cliente"));
                        vecReg.add(INT_TBL_DAT_DIR, rstLoc.getString("direccion"));
                        vecReg.add(INT_TBL_DAT_CIU, rstLoc.getString("ciudad"));
                        vecReg.add(INT_TBL_DAT_BOD, rstLoc.getString("bodega"));
                        vecReg.add(INT_TBL_DAT_TIP_DES, rstLoc.getString("despacho")); 
                        vecReg.add(INT_TBL_DAT_VEN, rstLoc.getString("vendedor"));
                        vecReg.add(INT_TBL_DAT_DIA_PEN, rstLoc.getInt("dias_pendientes"));
                        vecReg.add(INT_TBL_DAT_ITEM, rstLoc.getString("item"));
                        vecReg.add(INT_TBL_DAT_ITEM_3L, rstLoc.getString("item3l"));
                        vecReg.add(INT_TBL_DAT_ITEM_DES, rstLoc.getString("descripcion"));
                        vecReg.add(INT_TBL_DAT_CAN_FAC, rstLoc.getDouble("cant_facturada"));
                        vecReg.add(INT_TBL_DAT_PES_FAC, rstLoc.getDouble("peso_fact"));
                        vecReg.add(INT_TBL_DAT_CAN_DES, rstLoc.getDouble("cant_despachada"));
                        vecReg.add(INT_TBL_DAT_PES_DES, rstLoc.getDouble("peso_despachado"));
                        vecReg.add(INT_TBL_DAT_CAN_PEN, rstLoc.getDouble("cant_pendiente"));
                        vecReg.add(INT_TBL_DAT_PES_PEN, rstLoc.getDouble("peso_pendiente"));
                        
                        //vecReg.add(INT_TBL_DAT_UBI_ITM, "");
                        //vecReg.add(INT_TBL_DAT_UBI_ITM, getUbicacionItem(rstLoc.getString("ubicacion")));
                        vecReg.add(INT_TBL_DAT_UBI_ITM,"" );
                        
                        //vecReg.add(INT_TBL_DAT_PES_PEN, "");
                        
                        
                        vecDat.add(vecReg);
                    }
                    else 
                    {
                        break;
                    }
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if(realizarNuevaConsulta()){
                    if(AsignarUbicaciones()){
                        System.out.println(" OK Denis xD ");
                    }
                }
                if (blnCon) 
                {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } 
                else 
                {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                
                
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        } 
        catch (java.sql.SQLException e) { blnRes = false;   objUti.mostrarMsgErr_F1(this, e); } 
        catch (Exception e) {  blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(1);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenConMySQL.INT_BUT_ACE)
                    {
              
                        txtOd2.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.USERID", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoLoc.getValueAt(1));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenConMySQL.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
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
     * DT
     */
    private boolean realizarNuevaConsulta() {
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        //ResultSet rstLoc = null;

        try {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc!=null)
                {
                    stmLoc=conLoc.createStatement();
                    StringBuilder strSQL = new StringBuilder();// se utiliza para concatenar 
                    strSQL.append("SELECT TRIM(ITEMNMBR) as item, TRIM(locncode) as bodega, TRIM(bin) as ubicacion FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.iv00112 WHERE ");
                    
                    for (int i = 0; i < arrItems.size(); i++) { //recorre el arreglo y arma la consulta 
                        if (i > 0) {
                            strSQL.append(" OR ");
                        }
                        strSQL.append("(ITEMNMBR = '").append(arrItems.get(i))
                              .append("' AND locncode = '").append(arrBodegas.get(i)).append("')");
                    }

                    // Ejecutar la consulta
                    rstLoc = stmLoc.executeQuery(strSQL.toString());
                    ubicacionMap = new HashMap();
                    while (rstLoc.next()) {
                        // Aquí puedes procesar los resultados de la nueva consulta
//                        System.out.println("Item: " + rstLoc.getString("item"));
//                        System.out.println("Bodega: " + rstLoc.getString("bodega"));
//                        System.out.println("Ubicación: " + rstLoc.getString("ubicacion"));
//                        ubicacionMap.get(rstLoc.getString("ubicacion")); //obtendria la ubicacion
                        
                        ubicacionMap.put(rstLoc.getString("item") /*Mando mi clave ID*/, rstLoc.getString("ubicacion") /*Mando mi dato para despues buscar*/);
                    }
                    rstLoc.close();
                    stmLoc.close();
                    rstLoc=null;
                    stmLoc=null;
                }
            conLoc.close();
            conLoc=null;

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
    
     
   /**
    * 
    * @return 
    */ 
    private boolean AsignarUbicaciones(){
        boolean blnRes=true;
        try{
            int intNumFil=objTblMod.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
//                System.out.println("dato a buscar " + objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString() ) ;
//                System.out.println("GET MAP " + ubicacionMap.get(objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString()));
                //objTblMod.setValueAt VOY a poner mi valor en ese objeto de mi tabla 
                // ubicacionMap.get ===>  Me obtiene mi dato de la coleccion "ubicacionMap"
                objTblMod.setValueAt( ubicacionMap.get(objTblMod.getValueAt(i, INT_TBL_DAT_ITEM).toString()) /* OBTENER el dato */  , i /*Colocar en esta fila*/, INT_TBL_DAT_UBI_ITM /*En esta columna */); 
            }
        }
        catch (Exception e)  
        {
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e); 
        }
        return blnRes;     
    }
    

}