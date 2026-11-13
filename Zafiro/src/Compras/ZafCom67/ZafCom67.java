/*
 * ZafCom67.java
 *
 * Created on 25 de junio de 2013, 10:10 PM
 */
package Compras.ZafCom67;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.event.EventListenerList;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import Librerias.ZafVenConGP.ZafVenConGP;

/**
 *
 * @author  Alex Morán
 */
public class ZafCom67 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM_MAE=1;                 //Código maestro del item.
    static final int INT_TBL_DAT_COD_ITM=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT_ITM=3;                 //Código del item (Alterno).
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_DES_COR_UNI_MED=5;             //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_PES_KGR=6;                     //Peso en kilogramos.
    static final int INT_TBL_DAT_UBI_ITM=7;                     //Ubicación.
    static final int INT_TBL_DAT_CHK_UBI=8;                     //Estado impresión orden.
    static final int INT_TBL_DAT_COD_LET_ITM=9;                 //Código del item (Alterno II).
    static final int INT_TBL_DAT_STK_CON=10;                    //Stock consolidado.
    static final int INT_TBL_DAT_UBI_ITM_ANT=11;                //Ubicación antigua.
    static final int INT_TBL_DAT_EXI_GP=12;                     //Existencia GP.
    static final int INT_TBL_DAT_RES_GP=13;                     //Reservado GP.
    static final int INT_TBL_DAT_DIS_GP=14;                     //Disponible GP.
    static final int INT_TBL_DAT_DIS_WE=15;                     //Disponible WEB.
    static final int INT_TBL_DAT_CHK_ELI=16;                     //Estado impresión orden.    
    
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
////    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
////    private ZafVenCon vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private ZafVenConGP vcoItm, vcoUniMed,vcoBodUsr;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strCodLetItm, strNomItm;                       //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private JFormattedTextField jFormattedTextField1;
    private boolean blnMarTodCanTbl = true;
    private String strMsgItmUbi;
    private ZafTblPopMnu ZafTblPopMnu;
    
    private String tx_formatoDOC="", tx_formatoLOT="",tx_fordoc="",tx_tipo="";
    private int nd_secDoc=0,nd_secLot=0;
    
    String strCodBod = "", strNomBod = "",formatoLote="";
    private ArrayList arlDatITEMUBI,arlRegITEMUBI;
    
    //ArrayList para guardar el detalle de las  ubicaciones
    private static final int INT_ARL_CON_BIN=0;  
    private static final int INT_ARL_CON_ITEM=1;  
    private static final int INT_ARL_CON_ESTADO=2;
    private static final int INT_ARL_CON_COESTADO=3;
    private static final int INT_ARL_CON_BIN_ANT=4;
    private static final int INT_ARL_CON_UOFM=5;
    private static final int INT_ARL_CON_CANT=6;
    
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    
    String strVer ="v1.10";
////    ZafVenCon vcoBodUsr;
    
   /** Creates new form ZafCom67 */
    public ZafCom67(Librerias.ZafParSis.ZafParSis obj) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis=(Librerias.ZafParSis.ZafParSis) obj.clone();
            initComponents();
            
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);  
            
//            MaskFormatter mask = new MaskFormatter("U-##-U-#U");
//            mask.setPlaceholderCharacter(' ');
//            jFormattedTextField1 = new JFormattedTextField();
//            
//            panUbi.add(jFormattedTextField1);
//            jFormattedTextField1.setBounds(80, 30, 80, 20);
//
//            jFormattedTextField1.setFormatterFactory(new DefaultFormatterFactory(mask));
//            jFormattedTextField1.setText("a-01-a-1a");
//            jFormattedTextField1.setFocusLostBehavior(JFormattedTextField.PERSIST);
//
//        } catch (ParseException ex) {
//            Logger.getLogger(ZafCom67.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (CloneNotSupportedException e) {
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
        bgrFilUbi = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodLetItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        chkSolStk = new javax.swing.JCheckBox();
        panDesHasItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panUbi = new javax.swing.JPanel();
        optItmTodUbi = new javax.swing.JRadioButton();
        optItmSinUbi = new javax.swing.JRadioButton();
        optItmConUbi = new javax.swing.JRadioButton();
        lblUbi = new javax.swing.JLabel();
        txtUbi = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butEli = new javax.swing.JButton();
        butCrear = new javax.swing.JButton();
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

        panFil.setLayout(null);

        lblBod.setText("Bodega:");
        panFil.add(lblBod);
        lblBod.setBounds(4, 4, 120, 20);

        txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
        txtCodBod.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panFil.add(txtCodBod);
        txtCodBod.setBounds(124, 4, 92, 20);

        txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panFil.add(txtNomBod);
        txtNomBod.setBounds(216, 4, 444, 20);

        butBod.setText("jButton2");
        butBod.setPreferredSize(new java.awt.Dimension(20, 20));
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panFil.add(butBod);
        butBod.setBounds(660, 4, 20, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 24, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 44, 390, 20);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(24, 64, 70, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 64, 56, 20);

        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(144, 64, 92, 20);

        txtCodLetItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusLost(evt);
            }
        });
        txtCodLetItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLetItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodLetItm);
        txtCodLetItm.setBounds(236, 64, 64, 20);

        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(300, 64, 340, 20);

        butItm.setText(".."); // NOI18N
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 64, 20, 20);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(360, 136, 308, 20);

        panDesHasItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panDesHasItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panDesHasItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panDesHasItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panDesHasItm);
        panDesHasItm.setBounds(20, 84, 328, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 184, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(360, 84, 308, 52);

        panUbi.setBorder(javax.swing.BorderFactory.createTitledBorder("Ubicación"));
        panUbi.setLayout(null);

        bgrFilUbi.add(optItmTodUbi);
        optItmTodUbi.setSelected(true);
        optItmTodUbi.setText("Todos");
        optItmTodUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmTodUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmTodUbi);
        optItmTodUbi.setBounds(10, 20, 120, 23);

        bgrFilUbi.add(optItmSinUbi);
        optItmSinUbi.setText("General");
        optItmSinUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmSinUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmSinUbi);
        optItmSinUbi.setBounds(10, 40, 120, 23);

        bgrFilUbi.add(optItmConUbi);
        optItmConUbi.setText("Con ubicación");
        optItmConUbi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optItmConUbiItemStateChanged(evt);
            }
        });
        panUbi.add(optItmConUbi);
        optItmConUbi.setBounds(10, 60, 120, 23);

        lblUbi.setText("Comienzan con:");
        panUbi.add(lblUbi);
        lblUbi.setBounds(170, 40, 100, 14);
        panUbi.add(txtUbi);
        txtUbi.setBounds(170, 60, 100, 20);

        panFil.add(panUbi);
        panUbi.setBounds(20, 136, 328, 90);

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Dynamics GP");
        panBot.add(jLabel1);

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guarda los cambios realizados.");
        butGuardar.setEnabled(false);
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        butEli.setText("Eliminar");
        butEli.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butEli.setPreferredSize(new java.awt.Dimension(92, 25));
        butEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliActionPerformed(evt);
            }
        });
        panBot.add(butEli);
        butEli.getAccessibleContext().setAccessibleDescription("Ejecuta la eliminacion de las ubicaciones de los items sin stock.");

        butCrear.setText("Crear");
        butCrear.setToolTipText("Crear una ubicación");
        butCrear.setPreferredSize(new java.awt.Dimension(92, 25));
        butCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCrearActionPerformed(evt);
            }
        });
        panBot.add(butCrear);

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

        setBounds(0, 0, 861, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //Validar que el programa sólo funcione en la empresa grupo
        if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) {
            mostrarMsgInf("Este programa sólo funciona en la empresa grupo.\nIngrese a la empresa grupo y vuelva a intentarlo.");
            dispose();
        }
        
        configurarFrm();
        
        if(!isVersionActualizada()){
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }  
        
    }//GEN-LAST:event_formInternalFrameOpened

    /**
     * JM 22/Oct/2018
     * 
     * Nuevo metodo para validar si la version de Facturacion esta actualizada 
     * 
     * 
     * NOTA: al modifical cualquiera de las clases que genera factura esta clase deberia actualizarse para
     * comprobar que siempre el area de venta este actualizada
     * @return 
     */
    
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
                    System.out.println("Version " + strVer);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(strVer.equals(rstLoc.getString("tx_ver"))){
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
    
    private boolean ConsultaUbicacion(String lote)//chiqui
    {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.Statement stmDele;            
            java.sql.ResultSet rstLoc;
            String ubicacionAn="",bodega ="",item="";
            try
            {
                if(con!=null)
                {
                    stmLoc= con.createStatement();          
                    stmDele= con.createStatement();                         
                    strSQL="";
                    strSQL+=" SELECT tx_lote,tx_doc,dt_fechaIng,RTRIM (tx_item)as item,tx_bodega,tx_uniMed,tx_binOrg,tx_binDest,st_estado,nd_cantidad,tx_usrIng,nd_secuencia  \n";
                    strSQL+=" FROM  tbm_trUbic   \n";
                    strSQL+=" WHERE tx_lote= '"+  lote  +"' \n";                    
                    rstLoc=stmLoc.executeQuery(strSQL); 
                    while(rstLoc.next())
                    {
                            ubicacionAn = rstLoc.getString("tx_binOrg");
                            bodega = rstLoc.getString("tx_bodega");
                            item = rstLoc.getString("item"); 
                            if (EliminacionUbicacion(bodega,ubicacionAn,item))
                                blnRes=true;
                            else
                            {
                                blnRes=false;
                                break;
                            }
                    }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                }                
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
            
    }
    private boolean EliminacionUbicacion(String bodega,String ubicacion, String item)//chiqui
     {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.Statement stmDele;            
            java.sql.ResultSet rstLoc;
            String strSQLDe="",itemSQL="";
            double stockactual=0,cantidadDis=0,cantidadRes=0;
            try
            {
                if(con!=null)
                {
                    stmLoc= con.createStatement();          
                    stmDele= con.createStatement();                         
                    strSQL="";
                    
                    strSQL+=" SELECT rtrim(IV12.ITEMNMBR) as item,IV12.QUANTITY,IV12.ATYALLOC  \n";
                    strSQL+=" FROM  "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 AS iv12   \n";
                    strSQL+=" INNER JOIN   "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV40701 AS IV40 ON (IV12.LOCNCODE =IV40.LOCNCODE AND IV12.BIN =IV40.BIN) \n";
////                    strSQL+=" WHERE iv12.LOCNCODE= '"+  bodega  +"' AND IV12.BIN = '"+  ubicacion  +"' and IV12.ITEMNMBR ='"+  item  +"'  \n";             
                    strSQL+=" WHERE iv12.LOCNCODE= '"+  bodega  +"' AND IV12.BIN = '"+  ubicacion  +"' \n";                            
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next())
                    {
                        itemSQL =rstLoc.getString("item");
                        if (itemSQL.equals(item))
                        {
                            cantidadDis = rstLoc.getDouble("QUANTITY");
                            cantidadRes = rstLoc.getDouble("ATYALLOC");
//                            stockactual = cantidadDis - cantidadRes; 
                            stockactual = cantidadDis; //20231031
                            if (stockactual <=0)
                            {
                                strSQLDe="";
                                strSQLDe+="DELETE  "+ objParSis.getNombreBaseDatosGP()  +"..IV00112";
                                strSQLDe+=" WHERE  LOCNCODE= '" + bodega +"' AND " ;
                                strSQLDe+=" BIN= '" + ubicacion +"' AND ";  
                                strSQLDe+=" ITEMNMBR ='"+  item  +"'";
                                strSQLDe+=";";
                                stmDele.executeUpdate(strSQLDe);
                                blnRes=true;
////                                    strSQLDe+="DELETE  "+ objParSis.getNombreBaseDatosGP()  +"..IV40701";
////                                    strSQLDe+=" WHERE  LOCNCODE= '" + bodega +"' AND " ;
////                                    strSQLDe+=" BIN= '" + ubicacion +"'";  
////                                    strSQLDe+=";";
                            }
                        }
                    }
                     stmLoc.close();
                     stmLoc=null;
                     rstLoc.close();
                     rstLoc=null;    
                     stmDele.close();
                     stmDele=null;                     
                }                
                
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
     }    
     
 private boolean GeneraSecuencia()//chiqui
         {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int stinactivo=0;
             
            
             try
            {
                if(con!=null)
                {
                    stmLoc= con.createStatement();

                        strSQL="";
                        strSQL+=" SELECT cmm.tx_formato as formatoDoc,nd_secuencia as secuenciaDoc,cl.tx_for as formatoLote ,cl.nd_sec as secuenciaLote,cmm.tx_mot as tipo  \n";
                        strSQL+=" from tbm_cfgMotMnu as cmm \n";
                        strSQL+="      inner join tbr_mnvLot as ml on (ml.co_mnu = cmm.co_mnu) \n";
                        strSQL+="      inner join tbm_cfglot as cl on (ml.co_lot =cl.co_lot ) \n";                        
                        strSQL+=" WHERE cmm.co_mnu = "+  objParSis.getCodigoMenu()  +" \n";
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            tx_formatoDOC = rstLoc.getString("formatoDoc");
                            tx_fordoc =rstLoc.getString("formatoLote");
                            tx_formatoLOT = rstLoc.getString("formatoLote");
                            nd_secDoc = rstLoc.getInt("secuenciaDoc");
                            nd_secLot = rstLoc.getInt("secuenciaLote");
                            tx_formatoLOT = tx_formatoLOT + String.format("%06d", nd_secLot+1);
                            tx_tipo =rstLoc.getString("tipo");
                            
                            blnRes=true;
                        }
                     stmLoc.close();
                     stmLoc=null;
                     rstLoc.close();
                     rstLoc=null;
                                    
                  
                }
            }
            catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
            } 
             return blnRes;
         }
    
         
    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        try
        {
            if (objTblMod.isDataModelChanged()) 
            {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0) 
                {
                    con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                    con.setAutoCommit(false);
                    if(con!=null)
                    {
                        if (arregloUbicaciones())
                        {
                             if (GeneraSecuencia())
                                {
                                    if (actualizarDet())
                                    {
////                                              con.commit();
////                                              objTblMod.initRowsState();
////                                              objTblMod.setDataModelChanged(false);
////                                              mostrarMsgInf("Transferencias realziadas con exito");

                                           if (ActualizarUbicacionGP(con,tx_formatoLOT,tx_fordoc,tx_tipo))
                                           {
                                             if(ConsultaUbicacion(tx_formatoLOT))  
                                             {
                                                con.commit();
                                                mostrarMsgInf("Transferencias realizadas con exito"); 
                                                  
//////                                                if(validarDat()) 
//////                                                {
//////                                                    if (!cargarDetReg()) 
//////                                                    {
//////                                                        //Inicializar objetos si no se pudo cargar los datos.
//////                                                        lblMsgSis.setText("Listo");
//////                                                        pgrSis.setValue(0);
//////                                                        butCon.setText("Consultar");
//////                                                    }
//////                                                    //Establecer el foco en el JTable sólo cuando haya datos.
//////////                                                    if (tblDat.getRowCount()>0) 
//////////                                                    {
//////////                                                        tabFrm.setSelectedIndex(1);
//////////                                                        tblDat.setRowSelectionInterval(0, 0);
//////////                                                        tblDat.requestFocus();
//////////                                                    }
//////                                                }
//////                                                 objThrGUI=null;
                                                  
                                                  
                                             }
                                             else
                                             {
                                                 mostrarMsgInf("No se ha realizado la consulta de la ubicación.");
                                                 con.rollback();
                                             }
                                                 
                                            }
                                           else
                                           {
                                               mostrarMsgInf("No ha realizado ningúna actualización .");
                                               con.rollback();
                                           }
                                               
                                    }
                                    else
                                    {
                                         mostrarMsgInf("No ha realizado ninguna inserción .");
                                         con.rollback();
                                    }
                                }
                        }
                    }
                    else
                    {
                       mostrarMsgInf("Se a perdido la conexión."); 
                    }
////                    else
////                    {
                        con.close();
                        con=null;
////                    }
////                        ;

////                    else
////                       con.rollback();


    ////                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
    ////                else
    ////                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifíquelo a su administrador del sistema.");
                }
                else
                     mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
            else
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }

    }//GEN-LAST:event_butGuardarActionPerformed

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

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
          optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodLetItm.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            strCodAlt="";
            strCodLetItm="";
            strNomItm="";
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(1);
            }
        } 
        else
          txtCodAlt.setText(strCodAlt);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
           txtNomItm.setText(strNomItm);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) 
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
              txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if ( txtCodAltHas.getText().length()>0)
          optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
           optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) 
        {
            if (txtCodBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }
            else
            BuscarBod("a.co_bod",txtCodBod.getText(),0);
        }
        else
          txtCodBod.setText(strCodBod);
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) 
        {
            if (txtNomBod.getText().equals("")) 
            {
                txtCodBod.setText("");
                txtNomBod.setText("");
            }
            else
                BuscarBod("a.tx_nom",txtNomBod.getText(),1);
        }
        else
           txtNomBod.setText(strNomBod);
    }//GEN-LAST:event_txtNomBodFocusLost

    public void BuscarBod(String campo,String strBusqueda,int tipo)
    {
        vcoBodUsr.setTitle("Listado de Bodegas");
        if (vcoBodUsr.buscar(campo, strBusqueda)) 
        {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod = vcoBodUsr.getValueAt(1);
            strNomBod = vcoBodUsr.getValueAt(2);
        }
        else
        {
            vcoBodUsr.setCampoBusqueda(tipo);
            vcoBodUsr.cargarDatos();
            vcoBodUsr.show();
            if (vcoBodUsr.getSelectedButton() == vcoBodUsr.INT_BUT_ACE) 
            {
                txtCodBod.setText(vcoBodUsr.getValueAt(1));
                txtNomBod.setText(vcoBodUsr.getValueAt(2));
                strCodBod = vcoBodUsr.getValueAt(1);
                strNomBod = vcoBodUsr.getValueAt(2);
            } 
            else 
            {
                txtCodBod.setText(strCodBod);
                txtNomBod.setText(strNomBod);
            }
        }
    }
 
    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        vcoBodUsr.setTitle("Listado de Bodegas");
        vcoBodUsr.setCampoBusqueda(1);
        vcoBodUsr.show();
        if (vcoBodUsr.getSelectedButton() == vcoBodUsr.INT_BUT_ACE) 
        {
            txtCodBod.setText(vcoBodUsr.getValueAt(1));
            txtNomBod.setText(vcoBodUsr.getValueAt(2));
            strCodBod = vcoBodUsr.getValueAt(1);
            strNomBod = vcoBodUsr.getValueAt(2);
        }
    }//GEN-LAST:event_butBodActionPerformed

    private void optItmTodUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmTodUbiItemStateChanged
        if (optItmTodUbi.isSelected()) 
        {
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);            
        }        
    }//GEN-LAST:event_optItmTodUbiItemStateChanged

    private void optItmSinUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmSinUbiItemStateChanged
        if (optItmSinUbi.isSelected()) 
        {
            lblUbi.setVisible(false);
            txtUbi.setVisible(false);            
        }              
    }//GEN-LAST:event_optItmSinUbiItemStateChanged

    private void optItmConUbiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optItmConUbiItemStateChanged
        if (optItmConUbi.isSelected()) 
        {
            lblUbi.setVisible(true);
            txtUbi.setVisible(true);            
        }                 
    }//GEN-LAST:event_optItmConUbiItemStateChanged

    private void txtCodLetItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLetItmActionPerformed
        txtCodLetItm.transferFocus();
    }//GEN-LAST:event_txtCodLetItmActionPerformed

    private void txtCodLetItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusGained
        strCodLetItm=txtCodLetItm.getText();
        txtCodLetItm.selectAll();
    }//GEN-LAST:event_txtCodLetItmFocusGained

    private void txtCodLetItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLetItm.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodLetItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            } 
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
           txtCodLetItm.setText(strCodLetItm);

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLetItmFocusLost

    
    
     
    private void butEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliActionPerformed
        int intNumFil=0,i=0;
        boolean blnRes=true;
        String item ="",ubicacion ="",bodega="";
        try
        {
            if (objTblMod.isDataModelChanged()) 
            {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0) 
                {
                    con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                    con.setAutoCommit(false);
                    if(con!=null)
                    {
                        intNumFil=objTblMod.getRowCountTrue();
                        for (i=0; i<intNumFil;i++) 
                        {
                           if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_ELI))
                            {
////                                if (objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM)== null)
                                if (objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM)!= null)                                
                                {
                                    item =objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                                    ubicacion =objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM).toString();
                                    bodega =txtCodBod.getText();
                                    if (EliminacionUbicacion(bodega,ubicacion,item)==false)
                                    {
                                        blnRes=false;
                                        break;
                                    }
                                }
                                else
                                {
                                    blnRes=false;
                                    break;                                    
                                }
                            }       
                        }
                    }
                    if (blnRes==true)
                    {
                        con.commit();
                        mostrarMsgInf("Se ha realizado la eliminacion de las ubicaciones seleccionadas.");
                        if(validarDat()) 
                        {
////                            objTblMod.clearAllRows(false);
                            if (!cargarDetReg()) 
                            {
//                                Inicializar objetos si no se pudo cargar los datos.
                                lblMsgSis.setText("Listo");
                                pgrSis.setValue(0);
                                butCon.setText("Consultar");
                                mostrarMsgInf("Se ha realizado la actualización de las ubicaciones.");
                            }
//                            Establecer el foco en el JTable sólo cuando haya datos.
                            if (tblDat.getRowCount()>0) 
                            {
                                tabFrm.setSelectedIndex(1);
                                tblDat.setRowSelectionInterval(0, 0);
                                tblDat.requestFocus();
                            }
                        }
                         objThrGUI=null;
                    }
                    else
                    {
                        con.rollback();
                        con.close();
                        con=null;
                        mostrarMsgInf("NO se ha realizado la eliminacion de las ubicaciones seleccionadas.");
                    }
                        

                }
                else
                     mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
            else
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butEliActionPerformed

    private void butCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCrearActionPerformed
                Compras.ZafCom67.ZafCom67_01 obj = new Compras.ZafCom67.ZafCom67_01(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis, txtCodBod.getText());
////        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
        // TODO add your handling code here:
        
        
                
                
        
    }//GEN-LAST:event_butCrearActionPerformed

    private boolean configurarVenConBodUsr() //chiqui
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("a.co_bod");
            arlCam.add("a.tx_nom");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nom.Bod");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("350");

            //Armar la sentencia SQL.   a7.nd_stkTot,
            String Str_Sql="";

             //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
////////            if (objParSis.getCodigoUsuario()==1) 
////////            {
////////                //Armar la sentencia SQL.
////////////                Str_Sql=" SELECT co_bod, tx_nom FROM ( SELECT a2.co_bod, a2.tx_nom "+
////////////                        " FROM tbm_emp AS a1 "+
////////////                        " INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) "+
////////////                        " WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" "+
////////////                        " ORDER BY a1.co_emp, a2.co_bod  ) as a ";
////////                
////////               Str_Sql="";
////////               Str_Sql+=" SELECT RTRIM(a.CodigoGP) as co_bod, RTRIM(a.NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas as a";             
////////            } 
////////            else 
////////            {
////////                //Armar la sentencia SQL.
////////                Str_Sql="";
////////////                Str_Sql+=" SELECT a1.co_bod AS co_bod, a1.tx_nom AS tx_nom ";
////////////                Str_Sql+=" FROM tbm_emp AS a3 INNER JOIN tbm_bod AS a1";
////////////                Str_Sql+=" ON (a3.co_emp=a1.co_emp)";
////////////                Str_Sql+=" INNER JOIN tbr_bodLocPrgUsr AS a2";
////////////                Str_Sql+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
////////////                Str_Sql+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////////////                Str_Sql+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
////////////                Str_Sql+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
////////////                Str_Sql+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
////////                
////////                Str_Sql+=" SELECT RTRIM(g.CodigoGP) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
////////                Str_Sql+=" FROM Dynamics2021.dbo.vwBodegas as g \n";
////////                Str_Sql+=" INNER JOIN Dynamics2021.dbo.tbm_bod as b on (g.CodigoGP = b.tx_descor) \n";
////////                Str_Sql+=" INNER JOIN Dynamics2021.dbo.tbr_bodlocprgusr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
////////                Str_Sql+=" WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
////////                Str_Sql+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
////////                Str_Sql+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
////////                //strSQL+="       AND t.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
////////                //strSQL+="       AND b.co_tipDoc=269 \n";
////////                Str_Sql+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
////////                Str_Sql+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
////////////                Str_Sql+=" ORDER BY a3.co_emp, a1.co_bod"; 
////////////               Str_Sql="";
////////////               Str_Sql+=" SELECT RTRIM(a.CodigoGP) as co_bod, RTRIM(a.NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas as a"; 
////////            }

            if(objParSis.getCodigoUsuario()==1){
                
                Str_Sql="";
////////                Str_Sql+=" SELECT RTRIM(b.tx_descor) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
////////                Str_Sql+=" FROM Dynamics2021.dbo.tbm_bod as b  \n";
////////                Str_Sql+="   INNER JOIN Dynamics2021.dbo.tbr_bodlocprgusr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
////////                Str_Sql+="   WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
////////                Str_Sql+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
////////                Str_Sql+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
////////                Str_Sql+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
////////                Str_Sql+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
////////                if(txtCodBod.getText().length()>0)
////////                    Str_Sql+=" AND  b.tx_descor !='"+txtCodBod.getText() +"'";

////               strSQL="";
               Str_Sql+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
            }
            else{
                    
                Str_Sql="";
                Str_Sql+=" SELECT RTRIM(b.tx_descor) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
                Str_Sql+=" FROM Dynamics2021.dbo.tbm_bod as b  \n";
                Str_Sql+="   INNER JOIN Dynamics2021.dbo.tbr_bodlocprgusr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
                Str_Sql+="   WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
                Str_Sql+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                Str_Sql+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
                Str_Sql+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                Str_Sql+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
                if(txtCodBod.getText().length()>0)
                    Str_Sql+=" AND  b.tx_descor !='"+txtCodBod.getText() +"'";

////               strSQL="";
////               strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
            }


////            vcoBodUsr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            vcoBodUsr=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

            vcoBodUsr.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrFilUbi;
    private javax.swing.JButton butBod;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCrear;
    private javax.swing.JButton butEli;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblUbi;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optItmConUbi;
    private javax.swing.JRadioButton optItmSinUbi;
    private javax.swing.JRadioButton optItmTodUbi;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panDesHasItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panUbi;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLetItm;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtUbi;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblDat();
            configurarVenConBodUsr();
        }
        catch(Exception e) {   blnRes=false;      objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();      //Almacena los datos
            vecCab = new Vector(8);     //Almacena las cabeceras
            
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_PES_KGR, "Peso(Kg)");
            vecCab.add(INT_TBL_DAT_UBI_ITM, "Ubicación");
            vecCab.add(INT_TBL_DAT_CHK_UBI, "Imp.Ord.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM, "Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_STK_CON, "Stock");
            vecCab.add(INT_TBL_DAT_UBI_ITM_ANT, "Ubicación.Ant");
            vecCab.add(INT_TBL_DAT_EXI_GP, "Existencia GP");
            vecCab.add(INT_TBL_DAT_RES_GP, "Reservado GP");
            vecCab.add(INT_TBL_DAT_DIS_GP, "Dispponible GP");
            vecCab.add(INT_TBL_DAT_DIS_WE, "Disponible WEB");
            vecCab.add(INT_TBL_DAT_CHK_ELI, "S");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_STK_CON, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
//            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setCellSelectionEnabled(true);
//            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(250);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            
            tcmAux.getColumn(INT_TBL_DAT_EXI_GP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_RES_GP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DIS_GP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_DIS_WE).setPreferredWidth(90);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_ELI).setPreferredWidth(60);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_UBI_ITM_ANT, tblDat);
            
//            if (objParSis.getCodigoUsuario() != 1) {
//                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
//                if (!objPerUsr.isOpcionEnabled(2990))
//                {
//                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
//                }
//            }
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
                        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            /*chiqui*/
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            new ZafTblOrd(tblDat);
            
            ZafTblPopMnu = new ZafTblPopMnu(tblDat);
            ZafTblPopMnu.setEliminarFilaVisible(false);
            ZafTblPopMnu.setInsertarFilasVisible(false);
            ZafTblPopMnu.setInsertarFilaVisible(false);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
////            if (objParSis.getCodigoUsuario()==1) {
////                vecAux.add("" + INT_TBL_DAT_UBI_ITM);
////                vecAux.add("" + INT_TBL_DAT_CHK_UBI);
////                butGuardar.setEnabled(true);
////            } else {
////                //2688: Tabla->Modificar "Ubicación del item".
////                if (objPerUsr.isOpcionEnabled(2688)){ 
////                    vecAux.add("" + INT_TBL_DAT_UBI_ITM);
////                    vecAux.add("" + INT_TBL_DAT_CHK_UBI);
////                    butGuardar.setEnabled(true); 
////                }
////            }
////      if (objParSis.getCodigoUsuario()==1) {
                vecAux.add("" + INT_TBL_DAT_UBI_ITM);
                vecAux.add("" + INT_TBL_DAT_CHK_UBI);
                vecAux.add("" + INT_TBL_DAT_CHK_ELI);
                butGuardar.setEnabled(true);
////            } else {
////                //2688: Tabla->Modificar "Ubicación del item".
////                if (objPerUsr.isOpcionEnabled(2688)){ 
////                    vecAux.add("" + INT_TBL_DAT_UBI_ITM);
////                    vecAux.add("" + INT_TBL_DAT_CHK_UBI);
////                    butGuardar.setEnabled(true); 
////                }
////            }
      
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_EXI_GP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_RES_GP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIS_GP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIS_WE).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLblDer);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            
            tcmAux.getColumn(INT_TBL_DAT_EXI_GP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_RES_GP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_DIS_GP).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_DIS_WE).setCellRenderer(objTblCelRenLblNum);
            
            ZafTblCelEdiTxtFor objTblCelEdiTxtFor = new ZafTblCelEdiTxtFor();
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellEditor(objTblCelEdiTxtFor);
            
//            objTblCelEdiTxtFor.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    verificarUbiItm();
//                }
//            });
                    
            objTblCelEdiTxtFor = null;            
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ELI).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_UBI).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Configurar JTable: Establecer los listener para el TableHeader.
             tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                 public void mouseClicked(java.awt.event.MouseEvent evt) {
                     tblDatMouseClicked(evt);
                 }
             });
    
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = tblDat.getRowCount();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.

            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_UBI) {
                if (blnMarTodCanTbl) {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblDat.setValueAt(Boolean.FALSE, i, INT_TBL_DAT_CHK_UBI);                            
                    }
                    blnMarTodCanTbl = false;
                }else {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++) {
                        tblDat.setValueAt(Boolean.TRUE, i, INT_TBL_DAT_CHK_UBI);                            
                    }
                    blnMarTodCanTbl = true;
                }
            }

        }         
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e); 
        }
    }

    private void verificarUbiItm() {
        int intNumFil, i;
        intNumFil=objTblMod.getRowCountTrue();
        for (i=0; i<intNumFil; i++) {
            if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")) {
                if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_UBI_ITM).toString().equals(objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM).toString())){
                    mostrarMsgInf("Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.");
                    break;                     
                }
                
            }
            
        }
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
private boolean cargarDetReg() //chiqui
{
    boolean blnRes = true;

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");

        con = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (con != null)
        {
            // Obtener los filtros de la pantalla.
            String strItemExacto = txtCodItm.getText().trim();
            String strCodAltDesde = txtCodAltDes.getText().trim();
            String strCodAltHasta = txtCodAltHas.getText().trim();
            String strTerminal = txtCodAltItmTer.getText().trim();
            String strBodega = txtCodBod.getText().trim();
            String strUbicacion = txtUbi.getText().trim();

            // 0 = Todas las ubicaciones
            // 1 = Sin ubicación (GENERAL)
            // 2 = Con ubicación
            int intFiltroUbicacion = 0;

            if (optItmSinUbi.isSelected())
            {
                intFiltroUbicacion = 1; 
            }
            else if (optItmConUbi.isSelected())
            {
                intFiltroUbicacion = 2;
            }

            // 1 = Con stock
            // 0 = Sin stock
            int intSoloConStock = chkSolStk.isSelected() ? 1 : 0;

            strSQL = "{call dbo.sp_Consulta_Disponible_Items_Bodegas_BIN_ZAFCOM67(?,?,?,?,?,?,?,?,?,?,?,?)}";

            java.sql.CallableStatement cst = con.prepareCall(strSQL);

            // Parámetros de búsqueda
            cst.setString(1, strBodega);                                // @Bodegas
            cst.setString(2, strItemExacto.isEmpty() ? null : strItemExacto); // @ItemExacto
            cst.setString(3, strCodAltDesde.isEmpty() ? null : strCodAltDesde); // @CodAltDesde
            cst.setString(4, strCodAltHasta.isEmpty() ? null : strCodAltHasta); // @CodAltHasta
            cst.setString(5, strTerminal.isEmpty() ? null : strTerminal); // @Terminal

            cst.setInt(6, intFiltroUbicacion);                          // @FiltroUbicacion
            cst.setString(7, strUbicacion.isEmpty() ? null : strUbicacion); // @Ubicacion
            cst.setInt(8, intSoloConStock);                             // @SoloConStock

            // Filtros adicionales
            cst.setInt(9, 0);     // @FiltroDisponible: 0 = Todos
            cst.setInt(10, 0);    // @FiltroReservaGP: 0 = Todos
            cst.setInt(11, 0);    // @FiltroReservaWEB: 0 = Todos

            // Parámetro OUTPUT (posición correcta: 12)
            cst.registerOutParameter(12, java.sql.Types.INTEGER);

            // Ejecutar procedimiento
            System.out.println("Ejecutando SP: " + strSQL);

            rst = cst.executeQuery();

            // Limpiar vector de datos.
            vecDat.clear();

            lblMsgSis.setText("Cargando datos...");

            while (rst.next())
            {
                if (blnCon)
                {
                    vecReg = new Vector();

                    vecReg.add(INT_TBL_DAT_LIN, "");

                    vecReg.add(
                        INT_TBL_DAT_COD_ITM_MAE,
                        rst.getString("co_itmMae")
                    );

                    vecReg.add(
                        INT_TBL_DAT_COD_ITM,
                        rst.getString("co_itm")
                    );

                    vecReg.add(
                        INT_TBL_DAT_COD_ALT_ITM,
                        rst.getString("tx_codAlt")
                    );

                    vecReg.add(
                        INT_TBL_DAT_NOM_ITM,
                        rst.getString("tx_nomItm")
                    );

                    vecReg.add(
                        INT_TBL_DAT_DES_COR_UNI_MED,
                        rst.getString("tx_desCor")
                    );

                    vecReg.add(
                        INT_TBL_DAT_PES_KGR,
                        rst.getString("nd_pesItmKgr")
                    );

                    vecReg.add(
                        INT_TBL_DAT_UBI_ITM,
                        rst.getString("tx_ubi")
                    );

                    vecReg.add(
                        INT_TBL_DAT_CHK_UBI,
                        "S".equals(rst.getString("st_impOrd"))
                            ? Boolean.TRUE
                            : Boolean.FALSE
                    );

                    vecReg.add(
                        INT_TBL_DAT_COD_LET_ITM,
                        rst.getString("tx_codAlt2")
                    );

                    vecReg.add(
                        INT_TBL_DAT_STK_CON,
                        rst.getString("nd_stkAct")
                    );

                    vecReg.add(
                        INT_TBL_DAT_UBI_ITM_ANT,
                        rst.getString("tx_ubi")
                    );

                    vecReg.add(
                        INT_TBL_DAT_EXI_GP,
                        rst.getString("existencia")
                    );

                    vecReg.add(
                        INT_TBL_DAT_RES_GP,
                        rst.getString("reservado")
                    );

                    vecReg.add(
                        INT_TBL_DAT_DIS_GP,
                        rst.getString("nd_stkAct")
                    );

                    vecReg.add(
                        INT_TBL_DAT_DIS_WE,
                        rst.getString("disponibleWEB")
                    );

                    vecReg.add(
                        INT_TBL_DAT_CHK_ELI,
                        Boolean.FALSE
                    );

                    vecDat.add(vecReg);
                }
                else
                {
                    break;
                }
            }

            rst.close();
            cst.close();
            con.close();

            rst = null;
            stm = null;
            con = null;

            // Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);

            vecDat.clear();

            if (blnCon)
            {
                lblMsgSis.setText(
                    "Se encontraron " +
                    tblDat.getRowCount() +
                    " registros."
                );
            }
            else
            {
                lblMsgSis.setText(
                    "Interrupción del usuario. Sólo se procesaron " +
                    tblDat.getRowCount() +
                    " registros."
                );
            }

            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
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
        if (!blnRes)
        {
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        }
    }

    return blnRes;
}
    
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_ELI)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_ELI);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_ELI);
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
    
    private boolean CrearUbicacionGP(java.sql.Connection conExt, String bodega,String ubicacion)//ESPERAR
    {
         boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strClaveOT ="",strSql ="",strTipoOT="";
        String registroGP ="";
        int intCodRes=-1;
        try{
            if(con!=null){
                stmLoc = con.createStatement();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_CREACION_OT_IMPORT(?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("BODEGA",  strTipoOT);//Envio de parametro
                cal.setString("UBICACION",  strClaveOT);//Envio de parametro
                cal.setString("USUARIO",  objParSis.getUsuarioBaseDatos());//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    private boolean ActualizarUbicacionGP(java.sql.Connection conExt, String lote, String formatoLote,String tipo)//ESPERAR
    {
         boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strClaveOT ="",strSql ="",strTipoOT="";
        String registroGP ="";
        int intCodRes=-1;
        try{
            con = conExt;
            if(con!=null){
                stmLoc = con.createStatement();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_CREACION_UBICACION(?,?,?,?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("LOTE",  lote);//Envio de parametro
                cal.setString("FORLOTE",  formatoLote);//Envio de parametro
                cal.setString("TIPO",  tipo);//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            System.out.println("entra como que se cae SQLException... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            System.out.println("entra como que se cae Exception... " + e.toString());
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
     private int ValidaBin(String ubicacion,String item)//chiqui
         {
            int estado=0;//si el valor es 0 siginifca que la ubicacion no existe 
            boolean blnRes=false;
             java.sql.Connection conLoc;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String tx_itemConsul;
            int nd_stockConsul=0;
             try
            {
              
                conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                conLoc.setAutoCommit(false);
                if(conLoc!=null)
                {
                    stmLoc= conLoc.createStatement();

                        strSQL="";
                        strSQL+=" SELECT RTRIM(a1.ITEMNMBR) AS co_itmMae, RTRIM(a1.ITEMNMBR) AS co_itm, RTRIM(a1.ITEMNMBR) AS tx_codAlt,RTRIM(a1.ITEMDESC) as tx_nomItm,";
                        strSQL+="        RTRIM(a1.ITMGEDSC ) as tx_codAlt2,'1' as co_reg,RTRIM (a1.PRCHSUOM) AS tx_desCor,'N' as st_ser,";
                        strSQL+="        ISNULL(SUM(a3.QUANTITY - a3.ATYALLOC) , 0) AS nd_stkAct,CAST(a4.DATA AS DECIMAL(18, 4)) AS nd_pesItmKgr,";
                        strSQL+="        a3.BIN as tx_ubi,CASE WHEN a3.BIN IS NULL THEN 'N' ELSE  'S' END as st_impord";
                        strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1";
                        strSQL+=" INNER JOIN    "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 AS a2 ON a1.ITEMNMBR = a2.ITEMNMBR ";
                        strSQL+=" LEFT JOIN     "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 AS a3 ON a2.ITEMNMBR = a3.ITEMNMBR AND a2.LOCNCODE = a3.LOCNCODE ";
                        strSQL+=" LEFT JOIN     "+ objParSis.getNombreBaseDatosGP()  +"..ALT_MIS_INFOADD AS a4 ON a1.ITEMNMBR = a4.MASTERID ";
                        strSQL+=" WHERE   (a2.RCRDTYPE = 2) AND (a1.INACTIVE = 0) ";
                        strSQL+="          AND a2.LOCNCODE = '" + txtCodBod.getText() + "' and a3.BIN = '"+ ubicacion +"'";    
                        //strSQL+=strConSQL;
                        strSQL+=" GROUP BY a1.ITEMNMBR, a1.ITEMDESC,a1.ITMGEDSC,a1.PRCHSUOM,a4.DATA,a3.bin";
                        strSQL+=" ORDER BY a1.ITEMNMBR asc";
                
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            tx_itemConsul = rstLoc.getString("co_itmMae");
                            nd_stockConsul =rstLoc.getInt("nd_stkAct");
                            if (tx_itemConsul == null ? item == null : tx_itemConsul.equals(item) )
                                estado=1;//es cuando la nueva ubicacion tiene el mismo item ingresado
                            else
                            {
                                if (nd_stockConsul >0 )
                                     estado=2;//es cuando la nueva ubicacion tiene otro item ingresado y con stock
                                else
                                    estado=3;// es cuando la nueva ubicacion tiene otro item ingresado sin stock
                            }
                            blnRes=true;
                        }
                        rstLoc.close();
                        stmLoc.close();
                        rstLoc=null;
                        stmLoc=null;
                }
////                if (blnRes == false)
////                    estado=-1;
                        conLoc.close();
                        conLoc=null;

            }
            catch (java.sql.SQLException e) { 
            estado=-1;  
            objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
            estado=-1;
            objUti.mostrarMsgErr_F1(this, Evt);
            }
             return estado;
         }
     
    private boolean actualizarDet()//chiqui
    {
        
        int i;
        boolean blnRes=false;
        int estadoFin,nd_indiceDoc=0;
        String ubicacionFin,itemFin,ubicacionAntFin,unidadFin,formatoDoc,strSQL;
        try
        {

           if (con!=null)
           {
//           
                stm=con.createStatement();
                nd_indiceDoc=nd_secDoc;
                formatoDoc =tx_formatoDOC;
                strSQL="";
                for (i=0; i<arlDatITEMUBI.size();i++) 
                {
                        ////formatoDoc = "DOC" + objParSis.getNombreUsuario().toUpperCase()+ ;
                        nd_indiceDoc++;
                        formatoDoc = tx_formatoDOC + String.format("%06d",nd_indiceDoc);
                        strSQL+="INSERT INTO tbm_trUbic(";
                        strSQL+="        tx_lote,tx_doc,dt_fechaIng,tx_item,tx_bodega,tx_uniMed,tx_binOrg,";
                        strSQL+="        tx_binDest,tx_usrIng,st_estado,nd_cantidad,nd_secuencia,tx_comIng,tx_comMod,st_reg)";
                        strSQL+=" VALUES (";
                        strSQL+=" '" + tx_formatoLOT + "'"; //formatoLOC
                        strSQL+=", '" + formatoDoc + "'"; //formatoDOC
                        strSQL+=",  GETDATE()"; //fecha
                        strSQL+=", '" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_ITEM)+"'"; //item
                        strSQL+=", '" + txtCodBod.getText()+"'"; //bodega
                        strSQL+=", '" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_UOFM)+"'"; //unidad de medida
                        strSQL+=", '" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_BIN_ANT)+"'"; //bin origen
                        strSQL+=", " + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_BIN)+""; //bin destino
                        strSQL+=", '" + objParSis.getNombreUsuario()+"'"; //usuario ing
                        strSQL+=", '" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_COESTADO)+"'"; //estado
                        strSQL+=", '" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_CANT)+"'"; //CANTIDAD
                        strSQL+=", " + nd_indiceDoc+ ""; //secuencia
                        strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                        strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                        strSQL+=", 'N'"; //ESTADO DE REGISTRO
                        

                        strSQL+=");"; 
                        
                }
               System.out.println("insertar_tbmCabMovInv: " + strSQL);
               stm.executeUpdate(strSQL);
               stm.close();
               stm=null;
               blnRes=true;


////                  if (isUbiDup()) 
////                  { 
////                    con.commit();
////                    //Inicializo el estado de las filas.
////                    objTblMod.initRowsState();
////                    objTblMod.setDataModelChanged(false);
////                } 
////                 else 
////                 {
////                    mostrarMsgInf(strMsgItmUbi);
////                    con.rollback(); 
////                    blnRes = false;
////                }

            }
           else
               blnRes=false;
                    
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
    */
    private boolean arregloUbicaciones()//chiqui
    {
        int intNumFil, i;
        int estado =0;
        boolean blnRes = true,blnUbiVac =false;
        double nd_dispGP=0,nd_dispWB=0;
////        java.sql.Connection conLoc;
////        java.sql.Statement stmLoc;
        try 
        {
            strInsertGP="";

////                stmLoc = con.createStatement();
                //Obtener la fecha del servidor.
                intNumFil=objTblMod.getRowCountTrue();
                arlDatITEMUBI= new ArrayList();
                for (i=0; i<intNumFil;i++) 
                {
                        if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                        {
                            ////estado =ValidaBin(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_UBI_ITM),1),objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE),1));
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM)!=null)
                            {   
                                estado =ValidaBin(objTblMod.getValueAt(i, INT_TBL_DAT_UBI_ITM).toString(),objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                                if (estado == -1)
                                {   
                                    break;
                                }
                                arlRegITEMUBI = new ArrayList();
                                arlRegITEMUBI.add(INT_ARL_CON_BIN,objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_UBI_ITM),1));
                                arlRegITEMUBI.add(INT_ARL_CON_ITEM,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                                nd_dispGP = objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_STK_CON));
                                nd_dispWB =objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_DIS_WE));
                                
////                                if ( nd_dispGP!=nd_dispWB )
////////////                                if (totalstock(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE).toString())==false)
////////////                                {
////////////                                    arlRegITEMUBI.add(INT_ARL_CON_ESTADO,"DISP. GP ES DIFERENTE A DISP. WEB");
////////////                                    blnRes =false;
////////////                                    estado =4;
////////////                                }
                                if (estado !=4 )
                                {
                                    if (estado ==0)
                                            arlRegITEMUBI.add(INT_ARL_CON_ESTADO,"CREAR UBICACION");
                                    if (estado ==1)
                                            arlRegITEMUBI.add(INT_ARL_CON_ESTADO,"ACTUALIZAR");
                                    if (estado ==2)
                                    {
                                        arlRegITEMUBI.add(INT_ARL_CON_ESTADO,"UBICACION OCUPADA CON STOCK");  
                                        blnRes =false;
                                    }
                                    if (estado ==3)
                                            arlRegITEMUBI.add(INT_ARL_CON_ESTADO,"UBICACION OCUPADA SIN STOCK"); 
                                }
                              

                                arlRegITEMUBI.add(INT_ARL_CON_COESTADO,estado);
                                arlRegITEMUBI.add(INT_ARL_CON_BIN_ANT,objTblMod.getValueAt(i,INT_TBL_DAT_UBI_ITM_ANT));
                                arlRegITEMUBI.add(INT_ARL_CON_UOFM,objTblMod.getValueAt(i,INT_TBL_DAT_DES_COR_UNI_MED));
                                arlRegITEMUBI.add(INT_ARL_CON_CANT,objTblMod.getValueAt(i,INT_TBL_DAT_STK_CON));
                                arlDatITEMUBI.add(arlRegITEMUBI);
                            } 
                            else 
                            {
                                blnRes= false;
                                blnUbiVac = true;
                                 break;
                            }
                        }
                        
                    }
////                    stmLoc.close();
////                    stmLoc=null;
                    if ((blnRes== false) && (blnUbiVac ==false))
                    {
                        int estadoPre ;
                         strMsgItmUbi="<HTML>No se pueden guardar los cambios porque existen novedades con las ubicaciones modificadas <BR><BR>";
                         strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> UBICACIÓN ACTUAL </td><td> CANTIDAD </td><td> UBICACIÓN NUEVA </td><td> ERROR </td>";
                         for (i=0; i<arlDatITEMUBI.size();i++) 
                        { 
                            estadoPre =objUti.getIntValueAt(arlDatITEMUBI, i, INT_ARL_CON_COESTADO);
                            if((estadoPre == 2) || (estadoPre == 4)) 
                            {
                                strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_ITEM) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_BIN_ANT) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_CANT) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_BIN) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMUBI, i, INT_ARL_CON_ESTADO) + "</td>";
                                strMsgItmUbi+="</tr>";
                            }

                        }
                        strMsgItmUbi+="</table><BR>";
                        strMsgItmUbi+="No se puede realizar esta operación. <html>";
                        mostrarMsgInf(strMsgItmUbi);
                    }
                    if ((blnRes== false) && (blnUbiVac ==true))
                    {
                        mostrarMsgInf("<HTML>Existen items que se encuentran sin ninguna ubicación ingresada.<BR> Verifique y vuelva a intentarlo.</HTML>");
                    }
                    
////                }
              
////                conLoc.close();
////                conLoc=null;
////                con.close();
////                con=null;                    

////            }
////        catch (java.sql.SQLException e) {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes=false;
            strMsgItmUbi+="<BR>ERROR:Revise las ubicaciones y vuelva a intentarlo.</HTML>";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private String strInsertGP="", strSql;
    
////    private boolean insertaGP(java.sql.Connection conZaf, java.sql.Connection conDyn, int CodEmp, int CodBod, String StrCodItem, String StrCodUbi){
    private boolean insertaGP( java.sql.Connection conDyn, int CodEmp, String CodBod, String StrCodItem, String StrCodUbi){
        boolean blnRes = true;
        
        String strNombreBodega="", strCodigoItemGP="";
        int intCodRegInsGP=-1;
        try{
            if(conDyn!=null){
                strNombreBodega=CodBod ;
                strCodigoItemGP=StrCodItem;
                if(strNombreBodega.equals("-1") || strCodigoItemGP.equals("-1")){
                    blnRes=false; return false;
                }
                intCodRegInsGP = getCodRegInsGP(conDyn, strNombreBodega, strCodigoItemGP, StrCodUbi);
                if(intCodRegInsGP==-1){
                    blnRes=false; 
                    return false;
                }
                strInsertGP="";
                strInsertGP+=" INSERT INTO Dynamics2021.dbo.tbm_camUbiItm (tx_codBod, tx_codItm, tx_codUbi, co_reg, tx_usrIng, fe_ing, st_reg, fe_pro)  ";
                strInsertGP+=" VALUES ('"+strNombreBodega+"', '"+strCodigoItemGP+"', ";
                strInsertGP+=""+objUti.codificar(StrCodUbi)+", "+intCodRegInsGP+", "+objUti.codificar(objParSis.getNombreUsuario())+", CURRENT_TIMESTAMP, 'N', null);";
                System.out.println("strInsertGP+++>: " + strInsertGP);
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    
    private int getCodRegInsGP(java.sql.Connection conExt,  String CodBodGP, String CodItm, String CodUbi){
        int intRes=-1;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT CASE WHEN MAX(co_reg) IS NULL THEN 1 ELSE  MAX(co_reg)+1 END AS registro ";
                strSql+=" FROM tbm_camUbiItm  ";
                strSql+=" WHERE tx_codBod = '"+CodBodGP+"' AND tx_codItm = '"+CodItm+"' ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("registro");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e) {
            intRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            intRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intRes;
    } 
    
    private String getCodigoBodegaGP(java.sql.Connection conExt, int codEmp, int codBod){
        String strRes="-1";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tbm_bod where co_emp="+codEmp+" AND co_bod="+codBod+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strRes = rstLoc.getString("tx_desCor");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e) {
            strRes="-1";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strRes;
    }
    
    private String getCodigoItemGP(java.sql.Connection conExt, int CodEmp, String CodItm3L){
        String strRes="-1";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT * FROM tbm_inv where co_emp="+CodEmp+" AND tx_codALt2 like "+objUti.codificar(CodItm3L, 0) +" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strRes = rstLoc.getString("tx_codAltDyn");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e) {
            strRes="-1";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strRes;
    }
    
    
    
    private boolean isUbiDup() 
    {
        boolean blnRes = true;
        int i, intNumTotFil;
        
        try 
        {
            if (con != null)
            {
                intNumTotFil = objTblMod.getRowCountTrue();
                
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="SELECT DISTINCT co_itmmae, tx_codalt, tx_nomitm, a2.tx_ubi";
                strSQL +=" FROM tbm_inv AS a0";
                strSQL +=" INNER JOIN tbm_equInv AS a1 ON (a1.co_emp = a0.co_emp AND a1.co_itm = a0.co_itm)";
                strSQL +=" INNER JOIN tbm_invBod AS a2 ON (a2.co_emp = a1.co_emp AND a2.co_itm = a1.co_itm)";
                strSQL +=" INNER JOIN tbr_bodempbodgrp AS a3 ON (a3.co_emp = a2.co_emp and a3.co_bod = a2.co_bod)";
                strSQL +=" INNER JOIN (";
                strSQL +=" SELECT tx_ubi, COUNT(DISTINCT co_itmMae)";
                strSQL +=" FROM tbm_equInv AS b1";
                strSQL +=" INNER JOIN tbm_invBod AS b2 ON (b2.co_emp = b1.co_emp AND b2.co_itm = b1.co_itm)";
                strSQL +=" INNER JOIN tbr_bodempbodgrp AS b3 ON (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
                strSQL +=" WHERE b3.co_empgrp = " + objParSis.getCodigoEmpresa();
                strSQL +=" AND b3.co_bodgrp = " + txtCodBod.getText();
                strSQL +=" AND tx_ubi IS NOT NULL AND tx_ubi != ''";
                strSQL +=" GROUP BY tx_ubi";
                strSQL +=" HAVING COUNT(DISTINCT b1.co_itmMae) > 1";
                strSQL +=" ) AS a4 ON (a4.tx_ubi = A2.tx_ubi)";
                strSQL +=" WHERE a3.co_empgrp = " + objParSis.getCodigoEmpresa();
                strSQL +=" AND a3.co_bodgrp = " + txtCodBod.getText();
                rst=stm.executeQuery(strSQL);
                
                strMsgItmUbi="<HTML>No se pueden guardar los cambios porque existen items con la misma ubicación:";
                while (rst.next()) {
                    strMsgItmUbi+="<BR><FONT COLOR=\"blue\">" + rst.getString("tx_codalt") + ": </FONT><FONT COLOR=\"red\">" + rst.getString("tx_ubi") + "</FONT>";
                    blnRes = false;
                }
                strMsgItmUbi+="<BR>Revise las ubicaciones y vuelva a intentarlo.</HTML>";
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;
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
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()//chiqui
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Let.Itm.");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            //Armar la sentencia SQL.

////            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
////            strSQL+=" FROM tbm_inv AS a1";
////            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
////            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
////            strSQL+=" AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.tx_codAlt";
            //CHIQUI
            strSQL="";
            strSQL+=" SELECT RTRIM(ITEMNMBR) as co_itm,RTRIM(ITEMNMBR) as tx_codAlt,RTRIM(ITMGEDSC) as tx_codAlt2,RTRIM(ITEMDESC) as tx_nomItm,RTRIM(PRCHSUOM) as tx_desCor";
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV00101";
            strSQL+=" WHERE INACTIVE=0 ";
////            strSQL+=" ORDER BY ITEMNMBR";
            
            
            
            
////            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
//CHIQUI
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
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
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) 
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodLetItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodLetItm.setText(strCodLetItm);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }
    
    private boolean validarDat() 
    {
        boolean blnRes=true;

        if(txtCodBod.getText().equals(""))
        {
           mostrarMsgInf("LA BODEGA ES OBLIGATORIA...");
           txtCodBod.requestFocus();
           return false;
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
            if(validarDat()) 
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
            }
            objThrGUI=null;
        }
    }
    
    
    public class ZafTblCelEdiTxtFor extends javax.swing.DefaultCellEditor 
    {
        JFormattedTextField ftf;
        protected EventListenerList objEveLisLis=new EventListenerList();
        
        public ZafTblCelEdiTxtFor() 
        {
            super(new JFormattedTextField());
            ftf = (JFormattedTextField) getComponent();
            MaskFormatter maskformatter = null;

            try
            {
                maskformatter = new MaskFormatter("U-##-U-##U");
                maskformatter.setPlaceholderCharacter(' ');
            } 
            catch (ParseException e) 
            {
                System.err.println("Formato no válido: " + e.getMessage());
                System.exit(-1);
            }

            ftf.setFormatterFactory(new DefaultFormatterFactory(maskformatter));
            ftf.setValue(null);
    //        ftf.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
            ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);
            ftf.setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0));
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            JFormattedTextField ftf = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            System.out.println("getTableCellEditorComponent: " + value);
            ftf.setValue(value);
            return ftf;
        }

        @Override
        public Object getCellEditorValue() {
            JFormattedTextField ftf = (JFormattedTextField) getComponent();
            try {
                ftf.commitEdit();
            } catch (ParseException e) {
                System.out.println("Exception:" + e.getMessage() + " Texto: " + ftf.getText() ); 
                ftf.setValue(null);
            }

            System.out.println("getCellEditorValue: " + ftf.getValue());
            System.out.println("getCellEditorTexto: " + ftf.getText());
            return ftf.getValue();
        }
        
//        /**
//         * Esta función adiciona el listener que controlará los eventos de edición.
//         * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
//         */
//        public void addTableEditorListener(ZafTableListener listener)
//        {
//            objEveLisLis.add(ZafTableListener.class, listener);
//        }        
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
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg="Ubicación";
                    break;
                case INT_TBL_DAT_CHK_UBI:
                    strMsg="Imprime en orden de despacho";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg="Código en letras del item";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock";
                case INT_TBL_DAT_UBI_ITM_ANT:
                    strMsg="Ubicación Ant";
                    break;
                case INT_TBL_DAT_EXI_GP:
                    strMsg="Existencia GP.";
                    break;
                case INT_TBL_DAT_RES_GP:
                    strMsg="Reservado GP.";
                    break;
                case INT_TBL_DAT_DIS_GP:
                    strMsg="Disponible GP.";
                    break;
                case INT_TBL_DAT_DIS_WE:
                    strMsg="Disponible WEB.";
                    break;
                case INT_TBL_DAT_CHK_ELI:
                    strMsg="Eliminacion de ubicaciones";
                    break;                    
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}