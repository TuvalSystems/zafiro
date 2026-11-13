/*
 * ZafCon21.java
 *
 * Created on 09 de abril de 2006, 09:51 PM
 */
package Compras.ZafCom21;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafRptSis.ZafRptSis;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom21 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_SIS=1;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=2;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_BUT_ITM=3;                     //Botón del item.
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_DEC_UNI=5;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_CAN_EGR=6;                     //Cantidad egresada.
    static final int INT_TBL_DAT_CAN_ING=7;                     //Cantidad ingresada.
    static final int INT_TBL_DAT_COS_UNI=8;                     //Costo unitario.
    static final int INT_TBL_DAT_COS_TOT_EGR=9;                 //Costo total egresado.
    static final int INT_TBL_DAT_COS_TOT_ING=10;                //Costo total ingresado.
    static final int INT_TBL_DAT_AUX_COD_SIS=11;                //Auxiliar:Código del item (Sistema).
    static final int INT_TBL_DAT_AUX_CAN_EGR=12;                //Auxiliar:Cantidad egresada.
    static final int INT_TBL_DAT_AUX_CAN_ING=13;                //Auxiliar:Cantidad ingresada.
    static final int INT_TBL_DAT_AUX_COD_SIA=14;                //Auxiliar:Código actual del item(Sistema).
    static final int INT_TBL_DAT_EST_SER=15;                    //Estado de Item de Servicio.
    static final int INT_TBL_DAT_COD_ALT2=16;                    //Estado de Item de Servicio.--nuevo
    
    static final int INT_CFE_COD_SIS=0;                         //Columnas de las filas eliminadas: Código del item.
    static final int INT_CFE_CAN_EGR=1;                         //Columnas de las filas eliminadas: Cantidad egresada.
    static final int INT_CFE_CAN_ING=2;                         //Columnas de las filas eliminadas: Cantidad ingresada.
    //Variables generales.
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
////    private ZafVenCon vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
////    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
////    private ZafVenCon vcoBod;                                   //Ventana de consulta "Bodega".
    
    private ZafVenConGP vcoTipDoc;                                //Ventana de consulta "Tipo de documento".
    private ZafVenConGP vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenConGP vcoBod;                                   //Ventana de consulta "Bodega".
    private MiToolBar objTooBar;                                //Barra de botones.
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private Connection con, conCab,con1;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;                                  //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strCodBod, strNomBod;                        //Contenido del campo al obtener el foco.
    private int intSig=1;                                       //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private Date datFecAux;                                     //Auxiliar: Para almacenar fechas.
    //Variables de la clase.
    private int intCodEmp, intCodLoc;                           //Código de la empresa y local.
    private String strCodBodDoc;                                //Determina si el usuario ha cambiado la bodega con la que se guardó el documento originalmente.
    private String strEstRegRep;                                //Estado del registro a replicar.
    private String strMerIngEgrFisBodTipDoc;                    //Determina si la mercadería ingresa/egresa físicamente a Bodega según el Tipo de documento.
    private Date datFecDoc;                                     //Fecha del documento.
    private String tx_formato, tx_formatoDoc,tx_motivo,tx_forLot="",tx_tipo="";    
    private int nd_secuencia=0,nd_secLot=0;

    //ArrayList para consultar las ubicaciones con su stock correspondiente al item:bodega
    private ArrayList arlDatQTYUBI, arlRegQTYUBI,arlRegUbiQTY,arlDatUbiQTY;
    private static final int INT_ARL_CON_BIN=0;  
    private static final int INT_ARL_CON_DISP_P=1;   
    private static final int INT_ARL_CON_DISP_V=2;  
    private static final int INT_ARL_CON_CAN=3;
    private static final int INT_ARL_CON_ITM=4;
    private static final int INT_ARL_CON_BOD=5;
    
    //ArrayList para guardar los bin, con sus stock correspondienta al item:bodega
    private static final int INT_ARL_GUA_COD_EMP=0;  
    private static final int INT_ARL_GUA_COD_LOC=1;
    private static final int INT_ARL_GUA_TIP_DOC=2;
    private static final int INT_ARL_GUA_COD_DOC=3;
    private static final int INT_ARL_GUA_COD_ITEM=4;
    private static final int INT_ARL_GUA_COD_ITEM_ALT=5;
    private static final int INT_ARL_GUA_COD_ITEM_ALT2=6;
    private static final int INT_ARL_GUA_NOM_ITM=7;
    private static final int INT_ARL_GUA_DEC_UNI=8;
    private static final int INT_ARL_GUA_BOD=9;
    private static final int INT_ARL_GUA_COS_UNI=10;
    private static final int INT_ARL_GUA_TOT=11;
    private static final int INT_ARL_GUA_COS_TOT=12;
    private static final int INT_ARL_GUA_COS_UNI_GRU=13;
    private static final int INT_ARL_GUA_COS_TOT_GRU=14;
    private static final int INT_ARL_GUA_FIS_BOD=15;
    private static final int INT_ARL_GUA_REG_REP=16;
    private static final int INT_ARL_GUA_CO_ITM_ACT=17;
    private static final int INT_ARL_GUA_UBI_ITM=18;
    private static final int INT_ARL_GUA_CANT_UBI=19;
    private static final int INT_ARL_GUA_CANTIDAD=20;
    
    private int intIndiceSolTra=0;
    String strVer ="v0.32";
    
    /**
     * Crea una nueva instancia de la clase ZafCom21.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom21(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            configurarFrm();
            if(!isVersionActualizada()){
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }  
            
            agregarDocLis();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
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
     

    /**
     * Crea una nueva instancia de la clase ZafCom21 y carga el documento especificado.
     * @param obj El objeto ZafParSis.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public ZafCom21(ZafParSis obj, Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        this(obj);
        cargarDocumento(codigoEmpresa, codigoLocal, codigoTipoDocumento, codigoDocumento);
    }

    /**
     * Esta función carga el documento especificado.
     * @param codigoEmpresa El código de la empresa a cargar.
     * @param codigoLocal El código del local a cargar.
     * @param codigoTipoDocumento El código del tipo de documento a cargar.
     * @param codigoDocumento El código del documento a cargar.
     */
    public void cargarDocumento(Integer codigoEmpresa, Integer codigoLocal, Integer codigoTipoDocumento, Integer codigoDocumento)
    {
        intCodEmp=codigoEmpresa.intValue();
        intCodLoc=codigoLocal.intValue();
        txtCodTipDoc.setText(codigoTipoDocumento.toString());
        txtCodDoc.setText(codigoDocumento.toString());
        objTooBar.setVisibleModificar(false);
        objTooBar.setVisibleEliminar(false);
        objTooBar.setVisibleAnular(false);
        objTooBar.setEstado('c');
        objTooBar.consultar();
        objTooBar.setEstado('w');
        
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
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblBod = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panGenTotTot = new javax.swing.JPanel();
        lblTotEgr = new javax.swing.JLabel();
        txtTotEgr = new javax.swing.JTextField();
        lblTotIng = new javax.swing.JLabel();
        txtTotIng = new javax.swing.JTextField();
        lblDif = new javax.swing.JLabel();
        txtDif = new javax.swing.JTextField();
        panBar = new javax.swing.JPanel();

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
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 64));
        panGenCab.setLayout(null);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 4, 100, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 100, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 44, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(392, 4, 20, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(202, 4, 190, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 100, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número de documento");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(416, 24, 100, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(516, 24, 148, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lblBod.setText("Bodega:");
        lblBod.setToolTipText("Bodega");
        panGenCab.add(lblBod);
        lblBod.setBounds(0, 24, 100, 20);

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
        panGenCab.add(txtCodBod);
        txtCodBod.setBounds(100, 24, 100, 20);

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
        panGenCab.add(txtNomBod);
        txtNomBod.setBounds(202, 24, 190, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panGenCab.add(butBod);
        butBod.setBounds(392, 24, 20, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGenTotTot.setPreferredSize(new java.awt.Dimension(176, 10));
        panGenTotTot.setLayout(null);

        lblTotEgr.setText("Egresos:");
        lblTotEgr.setToolTipText("Total de egresos");
        panGenTotTot.add(lblTotEgr);
        lblTotEgr.setBounds(4, 0, 72, 20);

        txtTotEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenTotTot.add(txtTotEgr);
        txtTotEgr.setBounds(76, 0, 100, 20);

        lblTotIng.setText("Ingresos:");
        lblTotIng.setToolTipText("Total de ingresos");
        panGenTotTot.add(lblTotIng);
        lblTotIng.setBounds(4, 20, 72, 20);

        txtTotIng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenTotTot.add(txtTotIng);
        txtTotIng.setBounds(76, 20, 100, 20);

        lblDif.setText("Diferencia:");
        lblDif.setToolTipText("Diferencia");
        panGenTotTot.add(lblDif);
        lblDif.setBounds(4, 40, 72, 20);

        txtDif.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenTotTot.add(txtDif);
        txtDif.setBounds(76, 40, 100, 20);

        panGenTot.add(panGenTotTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        vcoBod.limpiar();
        //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
        if (objParSis.getCodigoUsuario()==1)
        {
            mostrarVenConBod(0);
        }
        else
        {
            if (txtCodTipDoc.getText().equals(""))
            {
                mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
            }
            else
            {
                vcoBod.setCondicionesSQL(" AND t.co_tipDoc=" + txtCodTipDoc.getText());
                mostrarVenConBod(0);
            }
        }
    }//GEN-LAST:event_butBodActionPerformed

    private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
        if (txtNomBod.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomBod.getText().equalsIgnoreCase(strNomBod))
            {
                if (txtNomBod.getText().equals(""))
                {
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        mostrarVenConBod(2);
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            txtNomBod.setText("");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND t.co_tipDoc=" + txtCodTipDoc.getText());
                            mostrarVenConBod(2);
                        }
                    }
                }
            }
            else
                txtNomBod.setText(strNomBod);
        }
    }//GEN-LAST:event_txtNomBodFocusLost

    private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
        strNomBod=txtNomBod.getText();
        txtNomBod.selectAll();
    }//GEN-LAST:event_txtNomBodFocusGained

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
        if (txtCodBod.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodBod.getText().equalsIgnoreCase(strCodBod))
            {
                if (txtCodBod.getText().equals(""))
                {
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        mostrarVenConBod(1);
                    }
                    else
                    {
                        if (txtCodTipDoc.getText().equals(""))
                        {
                            mostrarMsgInf("Para seleccionar la bodega primero debe indicar el tipo de documento.");
                            txtCodBod.setText("");
                        }
                        else
                        {
                            vcoBod.setCondicionesSQL(" AND t.co_tipDoc=" + txtCodTipDoc.getText());
                            mostrarVenConBod(1);
                        }
                    }
                }
            }
            else
                txtCodBod.setText(strCodBod);
        }
    }//GEN-LAST:event_txtCodBodFocusLost

    private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
        strCodBod=txtCodBod.getText();
        txtCodBod.selectAll();
    }//GEN-LAST:event_txtCodBodFocusGained

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                }
                else
                {
                    mostrarVenConTipDoc(1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
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
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTotEgr;
    private javax.swing.JLabel lblTotIng;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panGenTotTot;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDif;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc1;
    private javax.swing.JTextField txtTotEgr;
    private javax.swing.JTextField txtTotIng;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(516, 4, 148, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            this.setTitle(objParSis.getNombreMenu() + " v0.33");
            lblTit.setText(objParSis.getNombreMenu());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc1.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtTotEgr.setBackground(objParSis.getColorCamposSistema());
            txtTotIng.setBackground(objParSis.getColorCamposSistema());
            txtDif.setBackground(objParSis.getColorCamposSistema());
            //Configurar ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConItm();
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDat();
            //Ocultar objetos del sistema.
            txtCodTipDoc.setVisible(false);
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
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Código");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_CAN_EGR,"Egreso");
            vecCab.add(INT_TBL_DAT_CAN_ING,"Ingreso");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT_EGR,"Tot.Egr.");
            vecCab.add(INT_TBL_DAT_COS_TOT_ING,"Tot.Ing.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIS,"Aux.Cód.Sis.");
            vecCab.add(INT_TBL_DAT_AUX_CAN_EGR,"Aux.Can.Egr.");
            vecCab.add(INT_TBL_DAT_AUX_CAN_ING,"Aux.Can.Ing.");
            vecCab.add(INT_TBL_DAT_AUX_COD_SIA,"Aux.Cód.Sis.Act.");
            vecCab.add(INT_TBL_DAT_EST_SER,"Servicio");
            vecCab.add(INT_TBL_DAT_COD_ALT2,"Codigo Alt2");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_EGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_TOT_EGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_TOT_ING, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ALT);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            arlAux=new java.util.ArrayList();
            arlAux.add(INT_CFE_COD_SIS,"" + INT_TBL_DAT_AUX_COD_SIS);
            arlAux.add(INT_CFE_CAN_EGR,"" + INT_TBL_DAT_AUX_CAN_EGR);
            arlAux.add(INT_CFE_CAN_ING,"" + INT_TBL_DAT_AUX_CAN_ING);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            //Configurar JTable: Establecer el modelo de la tabla.
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
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(165);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_EGR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_ING).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_CAN_EGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_CAN_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUX_COD_SIA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_SER, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT2, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN_EGR);
            vecAux.add("" + INT_TBL_DAT_CAN_ING);
            vecAux.add("" + INT_TBL_DAT_COS_UNI);
            vecAux.add("" + INT_TBL_DAT_COS_TOT_EGR);
            vecAux.add("" + INT_TBL_DAT_COS_TOT_ING);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_EGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_ING).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[6];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=6;
            intColVen[5]=7;
            int intColTbl[]=new int[6];
            intColTbl[0]=INT_TBL_DAT_COD_SIS;
            intColTbl[1]=INT_TBL_DAT_COD_ALT;
            intColTbl[2]=INT_TBL_DAT_NOM_ITM;
            intColTbl[3]=INT_TBL_DAT_DEC_UNI;
            intColTbl[4]=INT_TBL_DAT_COS_UNI;
            intColTbl[5]=INT_TBL_DAT_EST_SER;
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (txtCodBod.getText().equals(""))
                    {
                        mostrarMsgInf("Para seleccionar el item primero debe indicar la bodega.");
                        objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                        txtCodBod.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setCriterio1(11);
                    vcoItm.setCondicionesSQL(" AND v.LOCNCODE='" + txtCodBod.getText()+ "'");
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada())
                    {
                        if (!objUti.parseString(objTblCelEdiTxtVcoItm.getCellEditorValue()).equals(""))
                        {
                            //Validar que no se repita el item.
                            if (existeCodAlt())
                            {
                                calcularTotFil();
                                calcularTotDoc();
                            }
                            else
                            {
                                calcularTotFil();
                                calcularTotDoc();
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_EGR);
                            }
                        }
                    }
                }
            });
            
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (txtCodBod.getText().equals(""))
                    {
                        mostrarMsgInf("Para seleccionar el item primero debe indicar la bodega.");
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        txtCodBod.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCondicionesSQL(" AND v.LOCNCODE='" + txtCodBod.getText()+ "'");
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada())
                    {
                        //Validar que no se repita el item.
                        if (existeCodAlt())
                        {
                            calcularTotFil();
                            calcularTotDoc();
                        }
                        else
                        {
                            calcularTotFil();
                            calcularTotDoc();
                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN_EGR);
                        }
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_EGR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT_ING).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                double dblCan, dblCosTot;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    //Validar que sólo se pueda escribir si se ingresó la cantidad (Egreso/Ingreso).
                    switch (tblDat.getSelectedColumn())
                    {
                        case INT_TBL_DAT_COS_TOT_EGR:
                            dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR));
                            if (dblCan==0)
                            {
                                mostrarMsgInf("Primero debe indicar la cantidad a egresar.");
                                objTblCelEdiTxt.setCancelarEdicion(true);
                                tblDat.changeSelection(intFilSel, INT_TBL_DAT_CAN_EGR, false, false);
                            }
                            break;
                        case INT_TBL_DAT_COS_TOT_ING:
                            dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING));
                            if (dblCan==0)
                            {
                                mostrarMsgInf("Primero debe indicar la cantidad a ingresar.");
                                objTblCelEdiTxt.setCancelarEdicion(true);
                                tblDat.changeSelection(intFilSel, INT_TBL_DAT_CAN_ING, false, false);
                            }
                            break;
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    switch (tblDat.getSelectedColumn())
                    {
                        case INT_TBL_DAT_CAN_EGR:
                            //Validar que sólo se pueda escribir una cantidad (Egreso/Ingreso).
                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_ING);
                            calcularTotFil();
                            break;
                        case INT_TBL_DAT_CAN_ING:
                            //Validar que sólo se pueda escribir una cantidad (Egreso/Ingreso).
                            objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_CAN_EGR);
                            calcularTotFil();
                            break;
                        case INT_TBL_DAT_COS_UNI:
                            calcularTotFil();
                            break;
                        case INT_TBL_DAT_COS_TOT_EGR:
                            dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR));
                            dblCosTot=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_TOT_EGR));
                            if (dblCan==0)
                            {
                                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_COS_UNI);
                            }
                            else
                            {
                                objTblMod.setValueAt("" + objUti.redondear(dblCosTot/dblCan, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_UNI);
                            }
                            break;
                        case INT_TBL_DAT_COS_TOT_ING:
                            dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING));
                            dblCosTot=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_TOT_ING));
                            if (dblCan==0)
                            {
                                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_COS_UNI);
                            }
                            else
                            {
                                objTblMod.setValueAt("" + objUti.redondear(dblCosTot/dblCan, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_UNI);
                            }
                            break;
                    }
                    calcularTotDoc();
                }
            });
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean obtenerCodGP()
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null){
                stmLoc=con.createStatement();
                strSQL="";
                    
                strSQL+=" select rtrim(cb.co_idCabGP) as co_idCabGP";
                strSQL+=" from tbm_cabMovInv cb";
                strSQL+=" inner join (";
                strSQL+="       SELECT c.co_tipDoc ";
                strSQL+="       FROM Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                strSQL+="           INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu) ";
                strSQL+="       WHERE g.co_mnu =" + objParSis.getCodigoMenu() + " ";  
                strSQL+="              AND g.co_emp=" + objParSis.getCodigoEmpresa() + ") as co on (cb.co_tipdoc = co.co_tipDoc) ";  
                strSQL+="       where cb.co_doc =" + txtCodDoc.getText() + " ";
                System.out.println("tbm_cabMovInv: " + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                if (rstLoc.next()){
                    strAux=rstLoc.getString("co_idCabGP");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    lblTit.setText(objParSis.getNombreMenu());  
                    strAux=rstLoc.getString("co_idCabGP");
                    lblTit.setText(objParSis.getNombreMenu() + ((strAux==null)?"":strAux ));  
                }
                else{
                    lblTit.setText(objParSis.getNombreMenu());  
                    blnRes=false;
                }
            rstLoc.close();
            stmLoc.close();
            con.close();
            rstLoc=null;
            stmLoc=null;
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
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public boolean anular()
        {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAceptar()
        {
            
        }

        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    rstCab.previous();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular()
        {
            
        }

        public void clickCancelar()
        {

        }

        public void clickConsultar() 
        {
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtDesCorTipDoc.requestFocus();
                    break;
                case 'j':
                    break;
            }
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }

        public void clickEliminar()
        {
            
        }

        public void clickFin() 
        {
            try
            {
                if (!rstCab.isLast())
                {
                    rstCab.last();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir()
        {
            
        }

        public void clickInicio()
        {
            try
            {
                if (!rstCab.isFirst())
                {
                    rstCab.first();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar()//chiqui
        {
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtTotEgr.setEditable(false);
                txtTotIng.setEditable(false);
                txtDif.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar()
        {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodBod.setEditable(false);
            txtNomBod.setEditable(false);
            butBod.setEnabled(false);
            txtCodDoc.setEditable(false);
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }

        public void clickSiguiente()
        {
            try
            {
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() 
        {
        }

        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
            try
            {
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

////        public boolean insertar()
////        {
////            if (!insertarReg())
////                return false;
////            return true;
////        }

        
            public boolean insertar(){
            if (!insertarReg())
            {
                return false;
            }
            else
            {
                obtenerCodGP();
                return true;
            }
                
            
        }
            
        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                objTblMod.clearRowHeaderRaise();
                limpiarFrm();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        }
        
        public boolean vistaPreliminar()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean aceptar()
        {
            return true;
        }
        
        public boolean imprimir()
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }
        
        public boolean beforeInsertar()
        {
            if (!isCamVal())
                return false;
            return true;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            if (!strAux.equals("Anulado"))
            {
                //Validar que exista el stock.
                if (!isStkBodSuf())
                {
                    mostrarMsgInf("<HTML>Algunos items no tienen stock suficiente para eliminar el documento.<BR>Revise su inventario y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            return true;
        }

        public boolean beforeAnular()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            //Validar que exista el stock.
            if (!isStkBodSuf())
            {
                mostrarMsgInf("<HTML>Algunos items no tienen stock suficiente para anular el documento.<BR>Revise su inventario y vuelva a intentarlo.</HTML>");
                return false;
            }
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
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarRegIns();
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            strCodBodDoc=txtCodBod.getText();
            strEstRegRep="I";
            actualizarValColAux();
            objTblMod.initRowsState();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            objTblMod.clearDataSavedBeforeRemoveRow();
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            strCodBodDoc=txtCodBod.getText();
            actualizarValColAux();
            objTblMod.initRowsState();
            blnHayCam=false;
            return true;
        }

        public boolean afterEliminar()
        {
            blnHayCam=false;
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

        public boolean beforeClickInicio()
        {
            return isRegPro();
        }

        public boolean beforeClickAnterior()
        {
            return isRegPro();
        }

        public boolean beforeClickSiguiente()
        {
            return isRegPro();
        }

        public boolean beforeClickFin()
        {
            return isRegPro();
        }

        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            return isRegPro();
        }

        public boolean beforeClickVistaPreliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()//chiqui
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar la "Bodega".
        if (txtCodBod.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtCodBod.requestFocus();
            return false;
        }
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        //Validar el "Número de documento".
        if (txtCodDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nùmero de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            txtCodDoc.requestFocus();
            return false;
        }
        //Validar que el "Código alterno" no se repita.
        if (!txtCodDoc.getText().equals(""))
        {
            strSQL="";
////            strSQL+="SELECT a1.ne_numdoc";
////            strSQL+=" FROM tbm_cabMovInv AS a1";
////            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////            strSQL+=" AND a1.co_loc=" + intCodLoc;
////            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
////            strSQL+=" AND a1.ne_numdoc='" + txtCodDoc.getText().replaceAll("'", "''") + "'";
            strSQL+="SELECT a1.ne_numdoc";
            strSQL+=" FROM Dynamics2021.dbo.tbm_cabMovInv AS a1";
            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
            strSQL+=" AND a1.co_loc=" + intCodLoc;
            strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a1.ne_numdoc='" + txtCodDoc.getText().replaceAll("'", "''") + "'";
            if (objTooBar.getEstado()=='m')
                strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
////            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL)) 
            if (!objUti.isCodigoUnico(this, objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), strSQL))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtCodDoc.getText() + "</FONT> ya existe.<BR>Escriba otro número de documento y vuelva a intentarlo.</HTML>");
                txtCodDoc.selectAll();
                txtCodDoc.requestFocus();
                return false;
            }
        }
        //Validar que el JTable de detalle esté completo.
        objTblMod.removeEmptyRows();
        if (!objTblMod.isAllRowsComplete())
        {
            mostrarMsgInf("<HTML>El detalle del documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.changeSelection(0, INT_TBL_DAT_COD_ALT, true, true);
            tblDat.requestFocus();
            return false;
        }
        //Validar que el "Documento esté cuadrado".
        if (objUti.parseDouble(txtDif.getText())!=0)
        {
            mostrarMsgInf("<HTML>El documento está <FONT COLOR=\"blue\">descuadrado</FONT>. Hay una diferencia de " + txtDif.getText() + ".<BR>Cuadre el documento y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que exista el stock.
        if (!isStkBodSuf())
        {
            mostrarMsgInf("<HTML>Algunos items no tienen stock suficiente para grabar el documento.<BR>Revise su inventario y vuelva a intentarlo.</HTML>");
            return false;
        }
        //Validar que se haya ingresado por lo menos un item con cantidad de egreso.
        if (objUti.parseDouble(txtTotEgr.getText())==0)
        {
            mostrarMsgInf("<HTML>El documento está <FONT COLOR=\"blue\">descuadrado</FONT>. No se a ingersado ningun item con cantidad de egreso.<BR>Cuadre el documento y vuelva a intentarlo.</HTML>");
            return false;
        }
          //Validar que se haya ingresado por lo menos un item con cantidad de ingreso.
        if (objUti.parseDouble(txtTotIng.getText())==0)
        {
            mostrarMsgInf("<HTML>El documento está <FONT COLOR=\"blue\">descuadrado</FONT>. No se a ingersado ningun item con cantidad de ingreso.<BR>Cuadre el documento y vuelva a intentarlo.</HTML>");
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
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    private boolean GeneraSecuencia(java.sql.Connection conExt)//chiqui
         {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            int stinactivo=0;
            tx_formato="";
            nd_secuencia=0;
            tx_formatoDoc="";
            
             try
            {
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();

////                        strSQL="";
////                        strSQL+=" SELECT m.tx_formato,m.tx_secuencia,m.tx_mot ,m.nd_secuencia  \n";
////                        strSQL+=" from tbm_cfgMotMnu as m \n";
////                        strSQL+="      inner join tbm_cabtipdoc as c on (m.co_tipDoc =c.co_tipdoc ) \n";
////                        strSQL+=" WHERE m.co_tipDoc = "+ txtCodTipDoc.getText() +" and c.tx_descor = '"+ txtDesCorTipDoc.getText()  +"' \n";
                                              strSQL="";
                        strSQL+=" SELECT m.tx_formato,m.tx_secuencia,m.tx_mot ,m.nd_secuencia,cl.tx_for as tx_forLot, cl.nd_sec as nd_secLote\n";
                        strSQL+=" from tbm_cfgMotMnu as m \n";
                        strSQL+="      inner join tbm_cabtipdoc as c on (m.co_tipDoc =c.co_tipdoc ) \n";
                        strSQL+="      inner join tbr_mnvLot as ml on (m.co_mnu = ml.co_mnu and m.co_tipDoc = ml.co_tipdoc) \n";       
                        strSQL+="      inner join tbm_cfglot as cl on (ml.co_lot =cl.co_lot) \n";                           
                        strSQL+=" WHERE m.co_tipDoc = "+ txtCodTipDoc.getText() +" and c.tx_descor = '"+ txtDesCorTipDoc.getText()  +"' \n";  
                        
                        
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            tx_formato = rstLoc.getString("tx_formato");
                            nd_secuencia = rstLoc.getInt("nd_secuencia");
                            tx_formatoDoc = tx_formato + String.format("%06d", nd_secuencia+1);
                            tx_motivo = rstLoc.getString("tx_mot");
                            tx_forLot = rstLoc.getString("tx_forLot");
                            tx_tipo = rstLoc.getString("tx_forLot");
                            nd_secLot = rstLoc.getInt("nd_secLote");
                            tx_forLot = tx_forLot + String.format("%06d", nd_secLot+1);
                            blnRes=true;
                        }
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

    
////    private boolean insertarGP(java.sql.Connection conExt,String formatoFinal,String motivo)
    private boolean insertarGP(java.sql.Connection conExt, String formatoFinal,String motivo,String formatoLote,String tipo)
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
                strTipoOT= formatoFinal;
                strClaveOT= txtCodDoc.getText();
                
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_CREACION_OT_IMPORT(?, ?, ?, ?, ?,?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("DOCUMENTO_ZAFIRO",  strTipoOT);//Envio de parametro
                cal.setString("ORDEN_TRABAJO",  strClaveOT);//Envio de parametro
                cal.setString("TIPO_DOCU",  txtCodTipDoc.getText());//Envio de parametro
                cal.setString("MOTIVO",  motivo);//Envio de parametro
                cal.setString("FORMA_LOTE",  formatoLote);//Envio de parametro
                cal.setString("TIPO_LOTE",  tipo);//Envio de parametro
                
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

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()//chiqui
    {
        boolean blnRes=false;
        try
        {
           
////          con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            //con1=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (insertarCab())
                {
                    if (insertarDet())
                    {
                        if(GeneraSecuencia(con))
                        {
////                             con.commit();
////                                blnRes=true;
                                
////                            if (insertarGP(con,tx_formatoDoc,tx_motivo))
                            if (insertarGP(con,tx_formatoDoc,tx_motivo,tx_forLot,tx_tipo))   
                            {
                                con.commit();
                                blnRes=true;
                            }
                            else
                                con.rollback(); 
                        }
                        else
                                con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite consultar el registro insertado.
     * @return true: Si se pudo consultar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarRegIns()//chiqui
    {
        boolean blnRes=true;
        try
        {
////            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            ////conCab=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            conCab=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText();
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontró " + rstCab.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    objTooBar.setEstadoRegistro("Activo");
                    rstCab.first();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()//chiqui
    {
        boolean blnRes=true;
        try
        {
////            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           //// conCab=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            conCab=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
////                    strSQL+="select co_emp,co_loc,co_tipdoc,co_doc ";
////                    strSQL+=" FROM Dynamics2021.dbo.tbm_cabMovInv";
////                    strSQL+=" where st_reg ='A' ";
////                    strSQL+=" AND co_emp=" + intCodEmp;
////                    strSQL+=" AND co_loc=" + intCodLoc;
////                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
////                    strSQL+=" FROM tbm_cabMovInv AS a1";
////                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////                    strSQL+=" AND a1.co_loc=" + intCodLoc;
////                    strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();

                    strSQL+="SELECT g.co_tipdoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_deslar) as tx_desLar,";
                    strSQL+="       isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                    strSQL+="       g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc,cab.*";
                    strSQL+=" FROM tbm_cabMovInv as cab";
                    strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = cab.co_emp and cab.co_tipdoc = g.co_tipdoc)";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+="  WHERE g.co_mnu =" + objParSis.getCodigoMenu();
                    


                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND co_tipdoc='" + strAux +'"';
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND co_doc=" + strAux;
                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND co_idCabGP='" + strAux +"'";//aqui
                strSQL+=" ORDER BY co_doc,ne_numDoc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
    
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()//chiqui
    {
        boolean blnRes=false;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            ////con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarCab())
                {
                    if (eliminarDet())
                    {
                        if (insertarDet())
                        {
////                            if (actualizarTbmInvBod(1))
////                            {
////                                if (actualizarTbmInv())
////                                {
////                                    recostearItm();
////                                    con.commit();
////                                    blnRes=true;
////                                }
////                                else
////                                    con.rollback();
////                            }
////                            else
////                                con.rollback();
                           con.commit();
                           blnRes=true;         
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg()//chiqui
    {
        boolean blnRes=false;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            ////con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            con.setAutoCommit(false);
            if (con!=null)
            {
//                if (eliminarDet())
//                {
                    if (eliminarCab())
                    {
////                        if (actualizarTbmInvBod(2))
////                        {
////                            if (actualizarTbmInv())
////                            {
////                                recostearItm();
////                                con.commit();
////                                blnRes=true;
////                            }
////                            else
////                                con.rollback();
////                        }
////                        else
////                            con.rollback();
                        
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
//                }
//                else
//                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()//chiqui
    {
        boolean blnRes=false;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            ////con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            con.setAutoCommit(false);
            if (con!=null)                if (anularCab())

            {
                if (anularCab())
                {
////                    if (actualizarTbmInvBod(3))
////                    {
////                        if (actualizarTbmInv())
////                        {
////                            recostearItm();
////                            con.commit();
////                            blnRes=true;
////                        }
////                        else
////                            con.rollback();
////                    }
////                    else
////                        con.rollback();
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()//chiqui
    {
        int intCodUsr, intUltSecGrp, intUltSecEmp, intUltReg;
        
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código para "ne_secGrp".
                strSQL="";
                strSQL+="SELECT a1.ne_secUltDocTbmCabMovInv";
                strSQL+=" FROM Dynamics2021.dbo.tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
////                intUltSecGrp=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
               //// intUltSecGrp=objUti.getNumeroRegistro(this, objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01(), strSQL);
                intUltSecGrp=objUti.getNumeroRegistro(this, objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), strSQL);
                
                if (intUltSecGrp==-1)
                    return false;
                intUltSecGrp++;
                //Obtener el código para "ne_secEmp".
                strSQL="";
                strSQL+="SELECT a1.ne_secUltDocTbmCabMovInv";
                strSQL+=" FROM Dynamics2021.dbo.tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////                intUltSecEmp=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                ////intUltSecEmp=objUti.getNumeroRegistro(this, objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01(), strSQL);
                intUltSecEmp=objUti.getNumeroRegistro(this, objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), strSQL);
                
                if (intUltSecEmp==-1)
                    return false;
                intUltSecEmp++;
                //Obtener el código para "co_doc".
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
////                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                ////intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01(), strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), strSQL);
                
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
////                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                ////datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
////                strSQL+="INSERT INTO tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numCot, ne_numDoc, tx_numPed";
////                strSQL+=", ne_numGui, co_dia, fe_doc, co_cli, tx_ruc, tx_nomCli, tx_dirCli, tx_telCli, tx_ciuCli, co_com, tx_nomVen, tx_ate, co_forPag";
////                strSQL+=", tx_desForPag, nd_sub, nd_porIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta, co_motDoc, co_mnu, tx_obs1 ,tx_obs2";
////                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod, fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet, st_regRep)";
////                strSQL+=" VALUES (";
                
                strSQL+="INSERT INTO Dynamics2021.dbo.tbm_cabMovInv (co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numDoc,fe_doc";
                strSQL+=",nd_sub ,nd_porIva ,nd_tot ,co_mnu ,tx_obs1 , tx_obs2,st_reg ,fe_ing ,fe_ultMod ,tx_usrIng ,tx_usrMod ,tx_comIng ,tx_comMod,st_regRep,co_dia ";
                strSQL+=",nd_valiva ,st_imp ,ne_numVecRecDoc) ";
                strSQL+=" VALUES (";
                
                strSQL+=intCodEmp; //co_emp
                strSQL+=", " + intCodLoc; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipDoc
                strSQL+=", " + intUltReg; //co_doc
                strSQL+=", " + intUltSecGrp; //ne_secGrp
                strSQL+=", " + intUltSecEmp; //ne_secEmp
                strSQL+=", " + objUti.codificar(txtCodDoc.getText(),3); //ne_numDoc
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_doc
                strSQL+=", " + objUti.codificar(txtTotEgr.getText(),3); //nd_sub
                strSQL+=", 0"; //nd_porIva
                strSQL+=", " + objUti.codificar(txtTotEgr.getText(),3); //nd_tot
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                strSQL+=", 'A'"; //st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'"; //fe_ing
                strSQL+=", '" + strAux + "'"; //fe_ultMod
                strSQL+=", '"+objParSis.getNombreUsuario()+"'"; //co_usrIng
                strSQL+=", '"+objParSis.getNombreUsuario()+"'"; //co_usrMod
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comIng
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'"; //tx_comMod
                strSQL+=", 'I'"; //st_regRep
                strSQL+=", " + objUti.codificar(txtCodDoc.getText(),3); //co_dia
                strSQL+=", 0"; //nd_valiva
                strSQL+=", 'N'"; //st_imp
                strSQL+=", 0"; //ne_numVecRecDoc
                strSQL+=")";
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()//chiqui
    {
        int i, j;
        String strCodTipDoc, strCodDoc, strCodItm, strAuxCodItm, strAuxCodItmAct;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if(validarStockDisponible(con,i,strCodTipDoc,strCodDoc))
                    {
                        if(validarCosto(con))
                        {
                            for (int k=0;k<arlDatUbiQTY.size();k++)
                            {
                                strSQL="";
                                strSQL+="INSERT INTO Dynamics2021.dbo.tbm_detMovInv (co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_numFil, co_itm,co_itmAct, tx_codAlt, tx_codAlt2";
                                strSQL+=", tx_nomItm, tx_uniMed, tx_bod,  nd_cosUni, nd_porDes,st_ivaCom,nd_tot, nd_cosTot";
                                strSQL+=", nd_cosPro, nd_cosUniGrp, nd_cosTotGrp, nd_canCon, st_regRep,tx_bin,nd_canBin, nd_can,nd_canDev,nd_exi,nd_valExi";
                                strSQL+=", nd_exiGrp, nd_valExiGrp, nd_cosProGrp,  st_merIngEgrFisBod, nd_canNunRec)";
                                strSQL+=" VALUES (";
                                strSQL+="" + intCodEmp; //co_emp
                                strSQL+=", " + intCodLoc; //co_loc
                                strSQL+=", " + strCodTipDoc; //co_tipDoc
                                strSQL+=", " + strCodDoc; //co_doc
                                strSQL+=", " + j; //co_reg
                                strSQL+=", " + (i+1); //ne_numfil
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM_ALT) +"'"; //co_itm
                                strSQL+=", 0"; //CO_ITMACT
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM_ALT)+"'"; //tx_codAlt
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COD_ITEM_ALT2)+"'"; //tx_codAlt2
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_NOM_ITM)+"'"; //tx_nomItm
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_DEC_UNI)+"'"; //tx_uniMed
                                strSQL+=", '" + txtCodBod.getText() + "'"; //co_bod
                                strSQL+=", " + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_UNI); //nd_cosUni
                                strSQL+=", 0"; //nd_porDes
                                strSQL+=", 'N'"; //st_ivaCom
                                strSQL+="," + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_TOT); //nd_tot
                                strSQL+=", " + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_TOT); //nd_cosTot
                                strSQL+=", 0"; //nd_cosPro
                                strSQL+=", " + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_UNI_GRU); //nd_cosUniGrp
                                strSQL+=", " + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_COS_TOT_GRU); //nd_cosTotGrp
                                strSQL+=", 0"; //nd_canCon
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_REG_REP)+"'"; //st_regRep
                                strSQL+=", '" + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_UBI_ITM)+"'"; //tx_bin
                                strSQL+=", " + objUti.getStringValueAt(arlDatUbiQTY, k, INT_ARL_GUA_CANT_UBI); //nd_canBin
                                strSQL+=", " + objUti.getDoubleValueAt(arlDatUbiQTY, k, INT_ARL_GUA_CANTIDAD); //nd_can
                                strSQL+=", 0"; //nd_canDev
                                strSQL+=", 0"; //nd_exi
                                strSQL+=", 0"; //nd_valExi
                                strSQL+=", 0"; //nd_exiGrp
                                strSQL+=", 0"; //nd_valExiGrp
                                strSQL+=", 0"; //nd_cosProGrp
                                strSQL+=", 'S'"; //st_merIngEgrFisBod
                                strSQL+=", 0"; //nd_canNunRec 
                                strSQL+=")";

                                stm.executeUpdate(strSQL);
                                j++;
                                blnRes=true;
                            }
                        }
                        else
                        {
                            con.rollback();
                            blnRes=false;
                            System.err.println("Error al validar el costo");
                            break;
                        }
////                      
                    }else
                    {
                            con.rollback();
                            blnRes=false;
                            System.err.println("Error al validar stock");
                            break;
                    }
                    
                   
                }
                stm.close();
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
    
     private void  anadirItemsArreglo(ArrayList arlRegUbiQTY,int i,String strCodTipDoc,String strCodDoc)//chiqui
     {
         String strCodItm2="",strAuxCodItm2="",strAuxCodItmAct="",strndCan ="";
       
         arlRegUbiQTY.add(INT_ARL_GUA_COD_EMP,intCodEmp);//cod_emp
         arlRegUbiQTY.add(INT_ARL_GUA_COD_LOC,intCodLoc);//co_loc
         arlRegUbiQTY.add(INT_ARL_GUA_TIP_DOC,strCodTipDoc);//co_tipdoc
         arlRegUbiQTY.add(INT_ARL_GUA_COD_DOC,strCodDoc);//co_doc
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS)));//co_item
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM_ALT,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT)));//tx_codalt
         arlRegUbiQTY.add(INT_ARL_GUA_COD_ITEM_ALT2,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT2)));//tx_codalt2
         arlRegUbiQTY.add(INT_ARL_GUA_NOM_ITM,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)));//tx_nomitm
         arlRegUbiQTY.add(INT_ARL_GUA_DEC_UNI,objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_DEC_UNI)));//tx_unimed
         arlRegUbiQTY.add(INT_ARL_GUA_BOD,txtCodBod.getText());//tx_bod
////         if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
////         {
////            strndCan = "-" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR), 3);//nd_can
////         }
////         else
////         {
////            strndCan = objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING), 3);//nd_can 
////         }
////         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
         arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI,objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI));//nd_cosUni
         if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
         {
            arlRegUbiQTY.add(INT_ARL_GUA_TOT,"-" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_EGR) );//nd_tot
            arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT,"-" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_EGR) );//nd_costot
            arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI_GRU,objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI) );//nd_cosunigrp
            arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT_GRU,"-" + objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_EGR) );//nd_costotgrp
         }
         else
         {
            arlRegUbiQTY.add(INT_ARL_GUA_TOT,  objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_ING) );//nd_tot
            arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT, objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_ING) );//nd_costot
            arlRegUbiQTY.add(INT_ARL_GUA_COS_UNI_GRU,objTblMod.getValueAt(i,INT_TBL_DAT_COS_UNI) );//nd_cosunigrp
            arlRegUbiQTY.add(INT_ARL_GUA_COS_TOT_GRU,objTblMod.getValueAt(i,INT_TBL_DAT_COS_TOT_ING) );//nd_costotgrp
         }
         arlRegUbiQTY.add(INT_ARL_GUA_FIS_BOD,strMerIngEgrFisBodTipDoc );//st_strMerIngEgrFisBodTipDoc
         if (strEstRegRep.equals("") || strEstRegRep.equals("I"))
            arlRegUbiQTY.add(INT_ARL_GUA_REG_REP,'I' );//st_regRep
         else if (strEstRegRep.equals("P") || strEstRegRep.equals("M"))
                arlRegUbiQTY.add(INT_ARL_GUA_REG_REP,'P' );   //st_regRep                         
        strCodItm2=(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
        strAuxCodItm2=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString());
        strAuxCodItmAct=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA).toString());
        //Determinar el código actual del item.
        if (strAuxCodItmAct.equals(""))
        {
            arlRegUbiQTY.add(INT_ARL_GUA_CO_ITM_ACT,strCodItm2 );//co_itmact
        }
        else
        {
            if (strCodItm2.equals(strAuxCodItm2))
                arlRegUbiQTY.add(INT_ARL_GUA_CO_ITM_ACT,strAuxCodItmAct ); //co_itmact
            else
                arlRegUbiQTY.add(INT_ARL_GUA_CO_ITM_ACT,strCodItm2 ); // co_itmact
        }
     }
         private boolean validarCosto(java.sql.Connection conExt)//chiqui
         {
            boolean blnRes=true, blnIsStock=true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            String strMsg="", strItem="";
            Double ndcostoApli, ndcostoInv=0.0;
            Boolean blvalistock =false;
             try
            {
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();
                    for (int l=0;l<arlDatUbiQTY.size();l++)
                    {
                        strSQL="";
                        strSQL+=" SELECT CURRCOST  \n";
                        strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as d \n";
                        strSQL+=" WHERE d.ITEMNMBR ='"+ objUti.getStringValueAt (arlDatUbiQTY, l, INT_ARL_GUA_COD_ITEM_ALT) +"'\n";
                        rstLoc=stmLoc.executeQuery(strSQL); 
                        while(rstLoc.next())
                        {
                            ndcostoInv = objUti.parseDouble(rstLoc.getDouble("CURRCOST"));
                        }
                        ndcostoApli= objUti.getDoubleValueAt(arlDatUbiQTY, l, INT_ARL_GUA_COS_UNI);
                        if (Objects.equals(ndcostoApli, ndcostoInv))
                            blnRes=true;
                        else
                        {
                            blnRes=false;
                            break;
                        }
                            
                    }
                    
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
    
    private boolean validarStockDisponible(java.sql.Connection conExt, int i,String strCodTipDoc,String strCodDoc)//chiqui
    {
         boolean blnRes=true, blnIsStock=true;
         java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strMsg="", strItem="";
        Boolean blvalistock =false;
        try
        {
            if(conExt!=null)
            {
               stmLoc= conExt.createStatement();

              strSQL="";
                    strSQL+=" SELECT RTRIM(BIN) as BIN,(u.QUANTITY - u.ATYALLOC) as disponibleU,d.DISPONIBLE as disponibleGP,RTRIM(d.ITEMNMBR) AS ITEM,u.QUANTITY,d.LOCNCODE as bodega , sum (d.DISPONIBLE) as total  \n";
                    strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..[vw_consulta_disponible_transito] as d \n";
                        strSQL+=" INNER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 as u on (d.ITEMNMBR = u.ITEMNMBR and d.LOCNCODE = u.LOCNCODE)  \n";
                    strSQL+=" WHERE d.ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT)) +" and \n";
                    strSQL+="       d.LOCNCODE ='"+ txtCodBod.getText() +"' AND BIN not like 'PED%' and u.QUANTITY >=0   \n";
                    strSQL+=" group by BIN,d.DISPONIBLE ,d.ITEMNMBR,u.QUANTITY,d.LOCNCODE,u.ATYALLOC \n";
                    strSQL+=" order by u.QUANTITY desc    \n";
                    
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL); 
                arlDatQTYUBI= new ArrayList();
                double dblCantidadGlobal =0;
                double dblCantidadTotalDis =0;
				//rrecore todas las ubicciones							  
                  while(rstLoc.next())
                    {
                        arlRegQTYUBI = new ArrayList();
                        arlRegQTYUBI.add(INT_ARL_CON_BIN,rstLoc.getString("BIN"));
                        arlRegQTYUBI.add(INT_ARL_CON_DISP_P,rstLoc.getDouble("disponibleU"));
                        arlRegQTYUBI.add(INT_ARL_CON_DISP_V,rstLoc.getDouble("disponibleGP"));
                        arlRegQTYUBI.add(INT_ARL_CON_CAN,rstLoc.getDouble("QUANTITY"));
                        arlRegQTYUBI.add(INT_ARL_CON_ITM,rstLoc.getString("ITEM"));
                        arlRegQTYUBI.add(INT_ARL_CON_BOD,rstLoc.getString("bodega"));
                        dblCantidadTotalDis = dblCantidadTotalDis + objUti.parseDouble(rstLoc.getString("total"));
                        arlDatQTYUBI.add(arlRegQTYUBI);
                    }
                  stmLoc.close();
                  stmLoc=null;
                  rstLoc.close();
                  rstLoc=null;
                  //revisa si hay stock
                 if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                {
                    dblCantidadGlobal =objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                    if ((dblCantidadGlobal >dblCantidadTotalDis) || (dblCantidadGlobal < 0))
                    {
                     blvalistock=false;
                    }else{
                         blvalistock=true;
                    }
                }
                else
                {
                    dblCantidadGlobal =objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                    blvalistock=true;
                    
                }
                if ((blvalistock==true) && ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0)))
                {
                  double dblCantidad =0, dblCanUbi =0,resul=0,dblCanDispGP =0,dblCostoUni=0;
                  String strUbica ="",strndCan="";
                  Boolean existe =false;
                  

                  dblCantidad=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                    if (dblCantidad ==0)
                    {
                        dblCantidad=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                        //dblCantidad = dblCantidad * (-1);
                    }
                   arlDatUbiQTY= new ArrayList();
                   for(int j=0; j<arlDatQTYUBI.size(); j++)
                   {
                      arlRegUbiQTY = new ArrayList();
                      dblCanUbi=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_P);
                      strUbica =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                      dblCanDispGP =objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V); 
					  
                      if (dblCantidad  == dblCanUbi )
                           {
                               anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                               arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                               arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                               if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                 {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                 }
                                 else
                                 {
                                    strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                 }
                                 arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can

                               arlDatUbiQTY.add(arlRegUbiQTY);
                               resul = dblCantidad - dblCanUbi;
                               dblCantidad =resul;
                               if (resul <=0)
                               {
                                   existe=true;
                                   break;
                               }
                           }
                           else
                           {
                               if (dblCantidad  < dblCanUbi )
                               {
                                   anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                   arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                   if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) 
                                if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                    (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                   {
                                    strndCan = "-" + dblCantidad ;//nd_can
                                   }
                                   else
                                   {
                                     strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                   }
                                   arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                   
                                   arlDatUbiQTY.add(arlRegUbiQTY);
                                   resul = dblCantidad - dblCanUbi;
                                   if (resul <=0)
                                    {
                                        existe=true;
                                        break;
                                    }
                               }
                               else
                               {
                                    if (dblCantidad  > dblCanUbi )
                                    {
                                        anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                                        arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbica);
                                        arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidad);
////                                        if (objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null)
                                        if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR)!=null) &&  
                                            (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR))>0))
                                        {
                                            strndCan = "-" + dblCantidad ;//nd_can
                                        }
                                        else
                                        {
                                            strndCan = objUti.parseString(dblCantidad)  ;//nd_can 
                                        }
                                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,strndCan);//nd_can
                                        arlDatUbiQTY.add(arlRegUbiQTY);
                                        resul = dblCantidad - dblCanUbi;
                                        dblCantidad =resul;
                                        if (resul <=0)
                                        {
                                            existe=true;
                                             break;
                                        }
                                    }
                               }
                           }
						   
                   }
                   if (existe == true)
                   {
                       if (dblCantidad <= dblCanDispGP )
                       {
                           blvalistock=true;
                       }
                       else
                       {
                            blvalistock=false;
                       }
                       String  strTit="";
                        if(blvalistock =false)
						{
                            strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";

                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";
                            blnRes=false;
						}
						if(existe == false)
                        {
                            strMsg="<html> La solicitud posee Items tes. <BR><BR>" ;// CAMBIA
                            strMsg+=" <table BORDER=1><tr><td> NO </td> <td> posee ubicaciones asignadas </td><td> Can.Sol. </td>";
                            strMsg+=""+ strItem + "    ";
                            strMsg+=" </table><BR>";
                            strMsg+="No se puede realizar esta operación. <html>";
                            //System.out.println(strMsg);
                            strTit="Mensaje del sistema Zafiro";   
                        }
                        
                   }
                   else
					
					   
                       blnRes=false;
		   

                   
                   }
				  
                else
                     blnRes=false;
                
			   
			   if ((objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING)!=null) && (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING))>0))
                {
                    arlDatUbiQTY= new ArrayList();
                     double dblCantidadI ,dblCantidadIU=0;
                     String strUbicaI ="",strndCanI="";
                     Boolean existe =false;
                     for(int j=0; j<arlDatQTYUBI.size(); j++)
                     {
                      arlRegUbiQTY = new ArrayList();
                      dblCantidadIU=objUti.getDoubleValueAt(arlDatQTYUBI, j, INT_ARL_CON_DISP_V);
                      strUbicaI =objUti.getStringValueAt(arlDatQTYUBI, j, INT_ARL_CON_BIN);
                      dblCantidadI=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                      if (dblCantidadIU >0)
                      {
                         anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                         arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                         arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                         arlDatUbiQTY.add(arlRegUbiQTY);
                         blnRes=true; 
                         break;
                      }
                      if("GENERAL".equals(strUbicaI))
                      {
                         anadirItemsArreglo(arlRegUbiQTY,i,strCodTipDoc,strCodDoc);
                         arlRegUbiQTY.add(INT_ARL_GUA_UBI_ITM,strUbicaI);
                         arlRegUbiQTY.add(INT_ARL_GUA_CANT_UBI,dblCantidadI);
                         arlRegUbiQTY.add(INT_ARL_GUA_CANTIDAD,dblCantidadI);//nd_can
                         arlDatUbiQTY.add(arlRegUbiQTY);
                         blnRes=true; 
                         break;
                      }
                      else
                      {
                          blnRes=false;
                      }
                     }  
                }
				
            }
            else{
                blnRes=false;
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

    
    /**
     * Esta función permite actualizar la tabla "tbm_invBod".
     * @param intTipOpe El tipo de operación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operación</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     * <BR>Nota.- Esta función actualiza el stock en la empresa seleccionada y en la empresa grupo.
     */
    private boolean actualizarTbmInvBod(int intTipOpe)
    {
        int intCodBod, intCodItm, i;
        double dblCan, dblCanEgr, dblAuxCanEgr, dblCanIng, dblAuxCanIng;
        boolean blnRes=true;
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodBod=Integer.parseInt(txtCodBod.getText());
                    switch (intTipOpe)
                    {
                        case 0: //Insertar
                            for (i=0; i<objTblMod.getRowCountTrue(); i++)
                            {
                                intCodItm=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                                dblCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                                dblCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                                dblCan=dblCanIng-dblCanEgr;
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE Dynamics2021.dbo.tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    if (dblCan>0)
                                        strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    else
                                        strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)+" + objUti.redondear(Math.abs(dblCan),objParSis.getDecimalesBaseDatos());
                                }
                                else
                                {
                                    strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + intCodBod;
                                strSQL+=" AND co_itm=" + intCodItm;
                                stm.executeUpdate(strSQL);
                            }
                            break;
                        case 1: //Modificar
                            for (i=0; i<objTblMod.getRowCountTrue(); i++)
                            {
                                intCodItm=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                                dblCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                                dblAuxCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_EGR));
                                dblCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                                dblAuxCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_ING));
                                if (txtCodBod.getText().equals(strCodBodDoc))
                                {
                                    strAux=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN));
                                    if (strAux.equals("M"))
                                    {
                                        if (intCodItm==Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString()))
                                        {
                                            if ( (dblCanEgr!=dblAuxCanEgr) || (dblCanIng!=dblAuxCanIng) )
                                            {
                                                //Caso 1: El usuario sólo modificó la cantidad.
                                                dblCan=(dblAuxCanEgr-dblCanEgr) - (dblAuxCanIng-dblCanIng);
                                                //Armar la sentencia SQL.
                                                strSQL="";
                                                strSQL+="UPDATE Dynamics2021.dbo.tbm_invBod";
                                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                                {
                                                    if (dblCan>0)
                                                        strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)-" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                                    else
                                                        strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                                }
                                                else
                                                {
                                                    strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                                }
                                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                                strSQL+=" AND co_bod=" + intCodBod;
                                                strSQL+=" AND co_itm=" + intCodItm;
                                                stm.executeUpdate(strSQL);
                                            }
                                        }
                                        else
                                        {
                                            //Caso 2 (Paso 1): El usuario cambió algo que debe ser considerado como una eliminación.
                                            java.util.ArrayList arlAux=new java.util.ArrayList();
                                            arlAux.add(INT_CFE_COD_SIS,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS));
                                            arlAux.add(INT_CFE_CAN_EGR,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_EGR));
                                            arlAux.add(INT_CFE_CAN_ING,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_ING));
                                            objTblMod.addRowDataSavedBeforeRemoveRow(arlAux);
                                            //Caso 2 (Paso 2): Considerar la modificación como una inserción.
                                            dblCan=dblCanIng-dblCanEgr;
                                            //Armar la sentencia SQL.
                                            strSQL="";
                                            strSQL+="UPDATE tbm_invBod";
                                            strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                            //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                            if (strMerIngEgrFisBodTipDoc.equals("S"))
                                            {
                                                if (dblCan>0)
                                                    strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                                else
                                                    strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)+" + objUti.redondear(Math.abs(dblCan),objParSis.getDecimalesBaseDatos());
                                            }
                                            else
                                            {
                                                strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                            }
                                            strSQL+=" WHERE co_emp=" + intCodEmp;
                                            strSQL+=" AND co_bod=" + intCodBod;
                                            strSQL+=" AND co_itm=" + intCodItm;
                                            stm.executeUpdate(strSQL);
                                        }
                                    }
                                    else if (strAux.equals("I"))
                                    {
                                        //Caso 3: El usuario insertó una fila.
                                        dblCan=dblCanIng-dblCanEgr;
                                        //Armar la sentencia SQL.
                                        strSQL="";
                                        strSQL+="UPDATE tbm_invBod";
                                        strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                        //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                        if (strMerIngEgrFisBodTipDoc.equals("S"))
                                        {
                                            if (dblCan>0)
                                                strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                            else
                                                strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)+" + objUti.redondear(Math.abs(dblCan),objParSis.getDecimalesBaseDatos());
                                        }
                                        else
                                        {
                                            strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                        }
                                        strSQL+=" WHERE co_emp=" + intCodEmp;
                                        strSQL+=" AND co_bod=" + intCodBod;
                                        strSQL+=" AND co_itm=" + intCodItm;
                                        stm.executeUpdate(strSQL);
                                    }
                                }
                                else
                                {
                                    //Caso especial (Paso 1): El usuario cambió la bodega y debe ser considerado como una eliminación.
                                    java.util.ArrayList arlAux=new java.util.ArrayList();
                                    arlAux.add(INT_CFE_COD_SIS,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS));
                                    arlAux.add(INT_CFE_CAN_EGR,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_EGR));
                                    arlAux.add(INT_CFE_CAN_ING,objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_ING));
                                    objTblMod.addRowDataSavedBeforeRemoveRow(arlAux);
                                    //Caso especial (Paso 2): Considerar la modificación como una inserción.
                                    dblCan=dblCanIng-dblCanEgr;
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE Dynamics2021.dbo.tbm_invBod";
                                    strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                    if (strMerIngEgrFisBodTipDoc.equals("S"))
                                    {
                                        if (dblCan>0)
                                            strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)-" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                        else
                                            strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    }
                                    else
                                    {
                                        strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    }
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_bod=" + intCodBod;
                                    strSQL+=" AND co_itm=" + intCodItm;
                                    stm.executeUpdate(strSQL);
                                }
                            }
                            //Caso 4: El usuario eliminó una o más filas.
                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                            for (i=0;i<arlAux.size();i++)
                            {
                                dblCan=objUti.getDoubleValueAt(arlAux,i,INT_CFE_CAN_EGR)-objUti.getDoubleValueAt(arlAux,i,INT_CFE_CAN_ING);
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE Dynamics2021.dbo.tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    if (dblCan>0)
                                        strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)-" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    else
                                        strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                }
                                else
                                {
                                    strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + strCodBodDoc;
                                strSQL+=" AND co_itm=" + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                                stm.executeUpdate(strSQL);
                            }
                            arlAux=null;
                            break;
                        case 2: //Eliminar
                        case 3: //Anular
                            for (i=0; i<objTblMod.getRowCountTrue(); i++)
                            {
                                intCodItm=Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                                dblCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                                dblCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                                dblCan=dblCanEgr-dblCanIng;
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="UPDATE Dynamics2021.dbo.tbm_invBod";
                                strSQL+=" SET nd_stkAct=nd_stkAct+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                //Se evalua si el tipo de documento indica que la mercaderia ingresa/egresa fisicamente a Bodega.
                                if (strMerIngEgrFisBodTipDoc.equals("S"))
                                {
                                    if (dblCan>0)
                                        strSQL+=", nd_canEgrBod=(CASE WHEN nd_canEgrBod IS NULL THEN 0 ELSE nd_canEgrBod END)-" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                    else
                                        strSQL+=", nd_canIngBod=(CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                }
                                else
                                {
                                    strSQL+=", nd_canDis=(CASE WHEN nd_canDis IS NULL THEN 0 ELSE nd_canDis END)+" + objUti.redondear(dblCan,objParSis.getDecimalesBaseDatos());
                                }
                                strSQL+=" WHERE co_emp=" + intCodEmp;
                                strSQL+=" AND co_bod=" + intCodBod;
                                strSQL+=" AND co_itm=" + intCodItm;
                                stm.executeUpdate(strSQL);
                            }
                            break;
                    }
                    stm.close();
                    stm=null;
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
    
    /**
     * Esta función permite actualizar la tabla "tbm_inv".
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     * <BR>Nota.- Esta función actualiza el stock en la empresa seleccionada y en la empresa grupo.
     */
    private boolean actualizarTbmInv()
    {
        int i;
        String strCodItmPro;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strCodItmPro=getCodItmPro();
                if (!strCodItmPro.equals(""))
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_inv";
                    strSQL+=" SET nd_stkAct=b1.nd_stkAct";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT a1.co_emp, a1.co_itm, SUM(a1.nd_stkAct) AS nd_stkAct";
                    strSQL+=" FROM tbm_invBod AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_itm IN (" + strCodItmPro + ")";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_itm";
                    strSQL+=" ) AS b1";
                    strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
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
    
    /**
     * Esta función obtiene el código de los items a procesar.
     * @return La cadena que contiene los códigos de los items a procesar.
     * Por ejemplo, '1', '4', '5' indica que se debe procesar los items 1, 4 y 5.
     */
    private String getCodItmPro()
    {
        int i, intNumFil;
        String strCta="";
        Object objAux;
        double dblCanEgr, dblAuxCanEgr, dblCanIng, dblAuxCanIng;
        switch (objTooBar.getEstado())
        {
            case 'n': //Insertar
            case 'e': //Eliminar
            case 'a': //Anular
                intNumFil=objTblMod.getRowCountTrue();
                //Procesar los items que se encuentran en el JTable.
                for (i=0; i<intNumFil;i++)
                {
                    //Códigos que no fueron cambiados.
                    objAux=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS);
                    if (objAux!=null)
                    {
                        if (!objAux.toString().equals(""))
                        {
                            if (strCta.length()==0)
                                strCta="'" + objAux.toString() + "'";
                            else
                            {
                                if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                    strCta+=",'" + objAux.toString() + "'";
                            }
                        }
                    }
                }
                break;
            case 'm': //Modificar
                intNumFil=objTblMod.getRowCountTrue();
                //Procesar los items que se encuentran en el JTable.
                for (i=0; i<intNumFil;i++)
                {
                    if (txtCodBod.getText().equals(strCodBodDoc))
                    {
                        strAux=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN));
                        if (strAux.equals("M"))
                        {
                            if (Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString())==Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString()))
                            {
                                dblCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                                dblAuxCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_EGR));
                                dblCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                                dblAuxCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_ING));
                                if ( (dblCanEgr!=dblAuxCanEgr) || (dblCanIng!=dblAuxCanIng) )
                                {
                                    //Códigos que no fueron cambiados.
                                    objAux=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS);
                                    if (objAux!=null)
                                    {
                                        if (!objAux.toString().equals(""))
                                        {
                                            if (strCta.length()==0)
                                                strCta="'" + objAux.toString() + "'";
                                            else
                                            {
                                                if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                                    strCta+=",'" + objAux.toString() + "'";
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                //Códigos que no fueron cambiados.
                                objAux=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS);
                                if (objAux!=null)
                                {
                                    if (!objAux.toString().equals(""))
                                    {
                                        if (strCta.length()==0)
                                            strCta="'" + objAux.toString() + "'";
                                        else
                                        {
                                            if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                                strCta+=",'" + objAux.toString() + "'";
                                        }
                                    }
                                }
                                //Códigos que fueron cambiados.
                                objAux=objTblMod.getValueAt(i, INT_TBL_DAT_AUX_COD_SIS);
                                if (objAux!=null)
                                {
                                    if (!objAux.toString().equals(""))
                                    {
                                        if (strCta.length()==0)
                                            strCta="'" + objAux.toString() + "'";
                                        else
                                        {
                                            if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                                strCta+=",'" + objAux.toString() + "'";
                                        }
                                    }
                                }
                            }
                        }
                        else if (strAux.equals("I"))
                        {
                            //Códigos que no fueron cambiados.
                            objAux=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS);
                            if (objAux!=null)
                            {
                                if (!objAux.toString().equals(""))
                                {
                                    if (strCta.length()==0)
                                        strCta="'" + objAux.toString() + "'";
                                    else
                                    {
                                        if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                            strCta+=",'" + objAux.toString() + "'";
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        //Códigos que no fueron cambiados.
                        objAux=objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS);
                        if (objAux!=null)
                        {
                            if (!objAux.toString().equals(""))
                            {
                                if (strCta.length()==0)
                                    strCta="'" + objAux.toString() + "'";
                                else
                                {
                                    if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                        strCta+=",'" + objAux.toString() + "'";
                                }
                            }
                        }
                        //Códigos que fueron cambiados.
                        objAux=objTblMod.getValueAt(i, INT_TBL_DAT_AUX_COD_SIS);
                        if (objAux!=null)
                        {
                            if (!objAux.toString().equals(""))
                            {
                                if (strCta.length()==0)
                                    strCta="'" + objAux.toString() + "'";
                                else
                                {
                                    if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                        strCta+=",'" + objAux.toString() + "'";
                                }
                            }
                        }
                    }
                }
                //Procesar los items que fueron eliminados del JTable.
                ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
                for (i=0;i<arlAux.size();i++)
                {
                    objAux=objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS);
                    if (objAux!=null)
                    {
                        if (!objAux.toString().equals(""))
                        {
                            if (strCta.length()==0)
                                strCta="'" + objAux.toString() + "'";
                            else
                            {
                                if (strCta.indexOf("'" + objAux.toString() + "'")==-1)
                                    strCta+=",'" + objAux.toString() + "'";
                            }
                        }
                    }
                }
                arlAux=null;
                break;
        }
        return strCta;
    }
    
    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                cargarDetReg();
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()//chiqui
    {
        int intPosRel;
        double dblTot;
        boolean blnRes=true;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
////                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a2.st_merIngEgrFisBod, a1.co_doc, a1.fe_doc";
////                strSQL+=", a1.ne_numDoc, a1.nd_tot, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_regRep";
////                strSQL+=" FROM tbm_cabMovInv AS a1";
////                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
////                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
////                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
////                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                
////                      strSQL+="SELECT 'CONVERSIONES' as  co_tipDoc,'CONVERSIONES' AS tx_desCor,'CONVERSIONES' AS tx_desLar,'I' AS tx_natDoc,co_doc,fe_doc, ne_numdoc,nd_tot,";
////                strSQL+=" tx_obs1,tx_obs2,st_reg, '' as st_regRep";
////                strSQL+=" FROM Dynamics2021.dbo.tbm_cabMovInv";
////                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
////                strSQL+=" AND co_tipDoc ='CONVERSIONES'";
////                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");

                    strSQL+="SELECT g.co_tipdoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_deslar) as tx_desLar,";
                    strSQL+="       isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                    strSQL+="       g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc,cab.*";
                    strSQL+=" FROM tbm_cabMovInv as cab";
                    strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = cab.co_emp and cab.co_tipdoc = g.co_tipdoc)";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+="  WHERE g.co_mnu =" + objParSis.getCodigoMenu();
                    strSQL+="   AND cab.co_doc=" +  rstCab.getString("co_doc");
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    
                    
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                    strAux=rst.getString("co_idCabGP");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    dblTot=rst.getDouble("nd_tot");
                    txtTotEgr.setText("" + objUti.redondear(dblTot, objParSis.getDecimalesBaseDatos()));
                    txtTotIng.setText(txtTotEgr.getText());
                    txtDif.setText("" + objUti.redondear(0, objParSis.getDecimalesBaseDatos()));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                    strAux=rst.getString("st_regRep");
                    strEstRegRep=(strAux==null)?"":strAux;
                    
                    strAux=rst.getString("co_idCabGP");
                    //this.setTitle(strAux + strVersion );
                    lblTit.setText(objParSis.getNombreMenu() + ((strAux==null)?"":strAux ));  
                    
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
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
            //Mostrar la posición relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()//chiqui
    {
        String intCodItm="";
        double dblCan, dblCosUni, dblCosTot;
        boolean blnRes=true;
        try
        {
//            objTooBar.setMenSis("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT d.co_itm, d.tx_codAlt, d.tx_nomItm, d.tx_uniMed, d.tx_bod, d.tx_bod as tx_nom, d.nd_can, d.nd_cosUni, d.nd_tot, d.co_itmAct, d.st_ivacom,i.ITMSHNAM as tx_desCor";
                strSQL+=" FROM tbm_detMovInv AS d";
                strSQL+=" INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 AS i ON (d.co_itm  = i.ITEMNMBR)";
                strSQL+=" WHERE d.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND d.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND d.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND d.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" ORDER BY d.co_emp, d.co_loc, d.co_tipDoc, d.co_doc";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
//                objTooBar.setMenSis("Cargando datos...");
                txtCodBod.setText("");
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    intCodItm=rst.getString("co_itm");
                    vecReg.add(INT_TBL_DAT_COD_SIS,"" + intCodItm);
                    vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_BUT_ITM,null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_uniMed"));
                    if (txtCodBod.getText().equals(""))
                    {
                        strCodBodDoc=rst.getString("tx_bod");
                        txtCodBod.setText(strCodBodDoc);
                        txtNomBod.setText(rst.getString("tx_nom"));
                    }
                    dblCan=rst.getDouble("nd_can");
                    dblCosUni=rst.getDouble("nd_cosUni");
                    dblCosTot=rst.getDouble("nd_tot");
                    if (dblCan<0)
                    {
                        //Egresos de inventario.
                        vecReg.add(INT_TBL_DAT_CAN_EGR,"" + Math.abs(dblCan));
                        vecReg.add(INT_TBL_DAT_CAN_ING,null);
                        vecReg.add(INT_TBL_DAT_COS_UNI,"" + dblCosUni);
                        vecReg.add(INT_TBL_DAT_COS_TOT_EGR,"" + Math.abs(dblCosTot));
                        vecReg.add(INT_TBL_DAT_COS_TOT_ING,null);
                        vecReg.add(INT_TBL_DAT_AUX_COD_SIS,"" + intCodItm);
                        vecReg.add(INT_TBL_DAT_AUX_CAN_EGR,"" + Math.abs(dblCan));
                        vecReg.add(INT_TBL_DAT_AUX_CAN_ING,null);
                    }
                    else
                    {
                        //Ingresos de inventario.
                        vecReg.add(INT_TBL_DAT_CAN_EGR,null);
                        vecReg.add(INT_TBL_DAT_CAN_ING,"" + dblCan);
                        vecReg.add(INT_TBL_DAT_COS_UNI,"" + dblCosUni);
                        vecReg.add(INT_TBL_DAT_COS_TOT_EGR,null);
                        vecReg.add(INT_TBL_DAT_COS_TOT_ING,"" + dblCosTot);
                        vecReg.add(INT_TBL_DAT_AUX_COD_SIS,"" + intCodItm);
                        vecReg.add(INT_TBL_DAT_AUX_CAN_EGR,null);
                        vecReg.add(INT_TBL_DAT_AUX_CAN_ING,"" + dblCan);
                    }
                    vecReg.add(INT_TBL_DAT_AUX_COD_SIA,rst.getString("co_itmAct"));
                    vecReg.add(INT_TBL_DAT_EST_SER,rst.getString("st_ivacom"));
                    vecReg.add(INT_TBL_DAT_COD_ALT2,rst.getString("tx_desCor"));
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
//                objTooBar.setMenSis("Listo");
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()//chiqui
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabMovInv";
                strSQL+=" SET ne_numDoc=" + objUti.codificar(txtCodDoc.getText(),2);
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_doc='" + strAux + "'";
                strSQL+=", nd_sub=" + objUti.codificar(txtTotEgr.getText(),3);
                strSQL+=", nd_tot=" + objUti.codificar(txtTotEgr.getText(),3);
                strSQL+=", co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarCab()//chiqui
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabMovInv";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()//chiqui
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM Dynamics2021.dbo.tbm_detMovInv";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularCab()//chiqui
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
////                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                ////datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod='" + objParSis.getNombreComputadoraConDirIP() + "'";
                if (!strEstRegRep.equals("I"))
                    strSQL+=", st_regRep='M'";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
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
            txtCodBod.setText("");
            txtNomBod.setText("");
            txtNumDoc1.setText("");
            txtCodDoc.setText("");
            objTblMod.removeAllRows();
            txaObs1.setText("");
            txaObs2.setText("");
            txtTotEgr.setText("");
            txtTotIng.setText("");
            txtDif.setText("");
            strEstRegRep="";
            strMerIngEgrFisBodTipDoc="";
            lblTit.setText(objParSis.getNombreMenu());
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_tipdoc");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("ne_ultDoc");
            arlCam.add("tx_natDoc");
            arlCam.add("st_merIngEgrFisBod");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Est.Mer.Ing.Egr.Fis.Bod.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("325");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                
//////                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod,'' as tx_natDoc,'' as st_merIngEgrFisBod";
//////                strSQL+=" FROM tbm_cabTipDoc AS a1";
//////                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
//////                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                strSQL+=" AND a1.co_loc=" + intCodLoc;
//////                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
//////                strSQL+=" ORDER BY a1.tx_desCor";
                
////                strSQL+="SELECT RTRIM(Reason_Code) as co_tipdoc ,RTRIM(Reason_Code_Description) as tx_desCor,RTRIM(Reason_Code_Description) as tx_desLar ,";
////                strSQL+="       isnull((select TOP 1 ne_numdoc from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_tipdoc ='CONVERSIONES' order by ne_numdoc desc),0) as ne_ultDoc ,'I' as tx_natDoc,'' as st_merIngEgrFisBod";
////                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV40300";
////                strSQL+=" WHERE Reason_Code = 'CONVERSIONES'";  
                strSQL="";
                strSQL+=" SELECT c.co_tipDoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_desLar) as tx_desLar, ";
                strSQL+="        isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                strSQL+="        g.tx_natDoc, CASE WHEN g.st_necautanudoc IS NULL THEN '' ELSE g.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM    Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";   
                     
                
////                strSQL+=" ORDER BY Reason_Code ASC";
            }
            else
            {
                //Armar la sentencia SQL.
////                strSQL="";
////                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
////                strSQL+=" FROM tbm_cabTipDoc AS a1";
////                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
////                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////                strSQL+=" AND a1.co_loc=" + intCodLoc;
////                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
////                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
////                strSQL+=" ORDER BY a1.tx_desCor";
////                strSQL="";
////                strSQL+=" SELECT c.co_tipDoc,RTRIM(g.Codigo) as tx_desCor, RTRIM(g.Descripcion) as tx_desLar, ";
////                strSQL+="        isnull((select TOP 1 ne_numdoc from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_tipdoc ==c.co_tipDoc  order by ne_numdoc desc),0) as ne_ultDoc ,";
////                strSQL+="        'I' as tx_natDoc,'' as st_merIngEgrFisBod";
////                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.vw_codigo_motivo_iv  as g ";
////                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.Codigo =c.tx_mot)";
////                strSQL+=" WHERE c.co_mnu =" + objParSis.getCodigoMenu() + " ";
                
////                strSQL+="SELECT Reason_Code as co_tipdoc ,Reason_Code_Description as tx_desCor,Reason_Code_Description as tx_desLar ,INACTIVE";
////                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV40300";
////                strSQL+=" WHERE INACTIVE=0 and  Reason_Code = 'CONVERSIONES'";
////                strSQL+=" ORDER BY co_tipdoc asc";
                
                    strSQL="";
                    strSQL+=" SELECT c.co_tipDoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_desLar) as tx_desLar, ";
                    strSQL+="        isnull((select TOP 1 co_grp from Dynamics2021.dbo.tbm_cabCfgConInv where st_reg ='A'  order by co_grp desc),0) as ne_ultDoc,";
                    strSQL+="         g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc";
                    strSQL+=" FROM    Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)";
                    strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";   
                    strSQL+=" AND m.co_usr=" + objParSis.getCodigoUsuario()+ "";  
                     
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
////            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            vcoTipDoc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a3.tx_unidad");
            arlCam.add("a2.nd_stkAct");
            arlCam.add("a1.nd_cosUni");
            arlCam.add("a1.st_ivacom");
            arlCam.add("a1.tx_desCor");
            
            
//            arlCam.add("a1.co_itm");
//            arlCam.add("a1.tx_codAlt");
//            arlCam.add("a1.tx_nomItm");
//            arlCam.add("a3.tx_desCor");
//            arlCam.add("a2.nd_stkAct");
//            arlCam.add("a1.nd_cosUni");
//            arlCam.add("a1.st_ser");
            //CHIQUI
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            arlAli.add("Stock");
            arlAli.add("Costo");
            arlAli.add("Servicio");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("225");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
////            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor, a2.nd_stkAct, a1.nd_cosUni, a1.st_ser";
////            strSQL+=" FROM tbm_inv AS a1";
////            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
////            strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";
////            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////            strSQL+=" AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.tx_codAlt";
            
            strSQL+=" select iv.DEX_ROW_ID as co_itm ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITEMDESC) as tx_nomItm,iv.PRCHSUOM as tx_unidad,  \n";
            strSQL+="        v.DISPONIBLE as nd_stkAct, iv.CURRCOST AS nd_cosUni,'' as st_ivacom, RTRIM(iv.ITMSHNAM) as tx_desCor     \n";
            strSQL+="        from "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito as v \n";
            strSQL+="             left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
            //strSQL+="             left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";
            strSQL+="        where v.DISPONIBLE > 0  \n";
////            strSQL+="        where v.DISPONIBLE > 0 and v.LOCNCODE ='"+ txtCodBod.getText() +"' \n";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
////            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Bodegas".
     */
    private boolean configurarVenConBod()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_bod");
            arlCam.add("tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("485");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
////                strSQL+="SELECT a1.co_bod, a1.tx_nom";
////                strSQL+=" FROM tbm_bod AS a1";
////                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////                strSQL+=" AND a1.st_reg='A'";
////                strSQL+=" ORDER BY a1.co_bod";
                
////               strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
               strSQL="";
               strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
////                  if(txtCodBodDes.getText().length()>0)
////                    strSQL+=" WHERE CodigoGP !='"+txtCodBodDes.getText()+"'";
            }
            else
            {
                //Armar la sentencia SQL.
////                strSQL="";
////                strSQL+="SELECT a1.co_bod, a1.tx_nom";
////                strSQL+=" FROM tbm_bod AS a1";
////                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
////                strSQL+=" WHERE a2.co_emp=" + intCodEmp;
////                strSQL+=" AND a2.co_loc=" + intCodLoc;
////                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
////                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
////                strSQL+=" AND a1.st_reg='A'";
////                strSQL+=" ORDER BY a1.co_bod";
                
                strSQL="";
                strSQL+=" SELECT RTRIM(g.CodigoGP) as co_bod,RTRIM(b.tx_nom) as tx_nom \n";
                strSQL+=" FROM Dynamics2021.dbo.vwBodegas as g \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_bod as b on (g.CodigoGP = b.tx_descor) \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_bodTipDocPrgUsr as t on (b.co_bod = t.co_bod and b.co_emp = t.co_emp) \n";
                strSQL+=" WHERE b.st_reg IN ('A','P') AND t.tx_natBod IN ('A','I') "+ " \n";
                strSQL+="       AND t.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                strSQL+="       AND t.co_loc=" + objParSis.getCodigoLocal() + " \n";
                //strSQL+="       AND t.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                //strSQL+="       AND b.co_tipDoc=269 \n";
                strSQL+="       AND t.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+="       AND t.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
                
////                    strSQL="";
////                    strSQL+=" SELECT RTRIM(CodigoGP) as co_bod, RTRIM(NombreLargo) as tx_nom  from Dynamics2021.dbo.vwBodegas";
////                if(txtCodBodDes.getText().length()>0)
////                    strSQL+=" AND g.LOCNCODE !='"+txtCodBodDes.getText()+"'";
            }
////            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de bodegas", strSQL, arlCam, arlAli, arlAncCol);
            vcoBod=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()//chiqui
    {
        boolean blnRes=true;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            ///con=DriverManager.getConnection(objParSis.getStringConexion_DGP01(), objParSis.getUsuarioBaseDatos_DGP01(), objParSis.getClaveBaseDatos_DGP01());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
////                    strSQL="";
////                    strSQL+="SELECT RTRIM(Reason_Code) as co_tipdoc ,RTRIM(Reason_Code_Description) as tx_desCor,RTRIM(Reason_Code_Description) as tx_desLar ,";
////                    strSQL+=" isnull((select TOP 1 ne_numdoc from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_tipdoc ='CONVERSIONES' order by ne_numdoc desc),0) as ne_ultDoc,'I' as tx_natDoc,'' as st_merIngEgrFisBod";
////                    strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.IV40300";
////                    strSQL+=" WHERE Reason_Code = 'CONVERSIONES'";
                    
                    
////                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.st_merIngEgrFisBod";
////                    strSQL+=" FROM tbm_cabTipDoc AS a1";
////                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
////                    strSQL+=" AND a1.co_loc=" + intCodLoc;
////                    strSQL+=" AND a1.co_tipDoc=";
////                    strSQL+=" (";
////                    strSQL+=" SELECT co_tipDoc";
////                    strSQL+=" FROM tbr_tipDocPrg";
////                    strSQL+=" WHERE co_emp=" + intCodEmp;
////                    strSQL+=" AND co_loc=" + intCodLoc;
////                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
////                    strSQL+=" AND st_reg='S'";
////                    strSQL+=" )";
                    
                strSQL="";
                strSQL+=" SELECT g.co_tipdoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_deslar) as tx_desLar, ";
                strSQL+="        isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc ,";
                strSQL+="        g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabtipdoc  as g  ";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + " ";
                strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + " ";
                rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
////                strSQL="";
////                strSQL+=" SELECT c.co_tipDoc,RTRIM(g.Codigo) as tx_desCor, RTRIM(g.Descripcion) as tx_desLar, ";
////                strSQL+="        isnull((select TOP 1 ne_numdoc from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_tipdoc =c.co_tipdoc order by ne_numdoc desc),0) as ne_ultDoc ,";
////                strSQL+="        'I' as tx_natDoc,'' as st_merIngEgrFisBod";
////                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +".dbo.vw_codigo_motivo_iv  as g ";
////                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.Codigo =c.tx_mot)";
////                strSQL+=" WHERE c.co_mnu =" + objParSis.getCodigoMenu() + " ";
                    
                    strSQL="";
                    strSQL+=" SELECT c.co_tipDoc,RTRIM(g.tx_descor) as tx_desCor, RTRIM(g.tx_desLar) as tx_desLar, ";
                    strSQL+="        isnull((select TOP 1 co_doc as n  from Dynamics2021.dbo.tbm_cabMovInv where st_reg ='A' and co_emp =g.co_emp and co_tipDoc = g.co_tipdoc order by co_doc desc),0) as ne_ultDoc,";
                    strSQL+="         g.tx_natDoc,g.st_merIngEgrFisBod,g.st_necautanudoc";
                    strSQL+=" FROM    Dynamics2021.dbo.tbm_cabtipdoc  as g ";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (g.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)";
                    strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)";
                    strSQL+=" WHERE g.co_mnu =" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND g.co_emp=" + objParSis.getCodigoEmpresa() + "";   
                    strSQL+=" AND m.co_usr=" + objParSis.getCodigoUsuario()+ "";   
                rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtCodDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strMerIngEgrFisBodTipDoc=rst.getString("st_merIngEgrFisBod");
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                        //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                        //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                txtCodBod.setText("");
                                txtNomBod.setText("");
                            }
                            txtCodDoc.selectAll();
                            txtCodDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                        //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                        txtCodDoc.selectAll();
                        txtCodDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtCodDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strMerIngEgrFisBodTipDoc=vcoTipDoc.getValueAt(6);
                            //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
                            {
                                txtCodBod.setText("");
                                txtNomBod.setText("");
                            }
                            txtCodDoc.selectAll();
                            txtCodDoc.requestFocus();
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBod(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBod.setCampoBusqueda(1);
                    vcoBod.setVisible(true);
                    if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoBod.buscar("a1.co_bod", txtCodBod.getText()))
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    else
                    {
                        vcoBod.setCampoBusqueda(0);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.setVisible(true);
                        if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        }
                        else
                        {
                            txtCodBod.setText(strCodBod);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText()))
                    {
                        txtCodBod.setText(vcoBod.getValueAt(1));
                        txtNomBod.setText(vcoBod.getValueAt(2));
                    }
                    else
                    {
                        vcoBod.setCampoBusqueda(1);
                        vcoBod.setCriterio1(11);
                        vcoBod.cargarDatos();
                        vcoBod.setVisible(true);
                        if (vcoBod.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        }
                        else
                        {
                            txtNomBod.setText(strNomBod);
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        if (blnHayCam || objTblMod.isDataModelChanged())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            blnRes=objTooBar.beforeInsertar();
                            if (blnRes)
                                blnRes=objTooBar.insertar();
                            break;
                        case 'm': //Modificar
                            blnRes=objTooBar.beforeModificar();
                            if (blnRes)
                                blnRes=objTooBar.modificar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    if (objTooBar.getBotonClick()==objTooBar.INT_BUT_CON || objTooBar.getBotonClick()==objTooBar.INT_BUT_ELI || objTooBar.getBotonClick()==objTooBar.INT_BUT_ANU || objTooBar.getBotonClick()==objTooBar.INT_BUT_IMP || objTooBar.getBotonClick()==objTooBar.INT_BUT_VIS)
                    {
                        blnRes=cargarReg();
                    }
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
        }
        return blnRes;
    }
    
    /**
     * Esta función calcula el total de la fila.
     */
    private void calcularTotFil()//chicquicalcular
    {
        int intFilSel;
        double dblCan=0, dblCosUni=0;
        intFilSel=tblDat.getSelectedRow();
        if (intFilSel!=-1)
        {
            if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR)!=null)
            {
                dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_EGR));
                dblCosUni=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI));
                objTblMod.setValueAt("" + objUti.redondear(dblCan*dblCosUni, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_TOT_EGR);
                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_COS_TOT_ING);
                
                 //Configurar JTable: Establecer columnas editables.
                    vecAux=new Vector();
                    vecAux.add("" + INT_TBL_DAT_COD_ALT);
                    vecAux.add("" + INT_TBL_DAT_BUT_ITM);
                    vecAux.add("" + INT_TBL_DAT_CAN_EGR);
                    vecAux.add("" + INT_TBL_DAT_CAN_ING);
                   // vecAux.add("" + INT_TBL_DAT_COS_UNI);
                    //vecAux.add("" + INT_TBL_DAT_COS_TOT_EGR);
                    //vecAux.add("" + INT_TBL_DAT_COS_TOT_ING);
                    objTblMod.setColumnasEditables(vecAux);
                    vecAux=null;
            }
            else
            {
                dblCan=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CAN_ING));
                dblCosUni=objUti.parseDouble(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COS_UNI));
                objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_COS_TOT_EGR);
                objTblMod.setValueAt("" + objUti.redondear(dblCan*dblCosUni, objParSis.getDecimalesBaseDatos()), intFilSel, INT_TBL_DAT_COS_TOT_ING);
                if (objTblMod.isValueAtEqualToBigDecimal(intFilSel, INT_TBL_DAT_COS_TOT_ING, BigDecimal.ZERO))
                {
                    objTblMod.setValueAt(null, intFilSel, INT_TBL_DAT_COS_TOT_ING);
                }
                //Configurar JTable: Establecer columnas editables.
                    vecAux=new Vector();
                    vecAux.add("" + INT_TBL_DAT_COD_ALT);
                    vecAux.add("" + INT_TBL_DAT_BUT_ITM);
                    vecAux.add("" + INT_TBL_DAT_CAN_EGR);
                    vecAux.add("" + INT_TBL_DAT_CAN_ING);
                    vecAux.add("" + INT_TBL_DAT_COS_UNI);
                   // vecAux.add("" + INT_TBL_DAT_COS_TOT_EGR);
                    //vecAux.add("" + INT_TBL_DAT_COS_TOT_ING);
                    objTblMod.setColumnasEditables(vecAux);
                    vecAux=null;
                
            }
        }
    }
    
    /**
     * Esta función calcula el total del documento.
     */
    private void calcularTotDoc()
    {
        int intFilPro, i;
        double dblTotEgr=0, dblTotIng=0;
        try
        {
            intFilPro=objTblMod.getRowCount();
            for (i=0; i<intFilPro; i++)
            {
                dblTotEgr+=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT_EGR));
                dblTotIng+=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT_ING));
            }
            txtTotEgr.setText("" + objUti.redondear(dblTotEgr,objParSis.getDecimalesBaseDatos()));
            txtTotIng.setText("" + objUti.redondear(dblTotIng,objParSis.getDecimalesBaseDatos()));
            txtDif.setText("" + objUti.redondear(dblTotEgr-dblTotIng,objParSis.getDecimalesBaseDatos()));
        }
        catch (NumberFormatException e)
        {
            txtTotEgr.setText("[ERROR]");
            txtTotIng.setText("[ERROR]");
        }
    }

    /**
     * Esta función obtiene el stock del item en la bodega especificada.
     * @param intCodEmp El código de la empresa.
     * @param intCodBod El código de la bodega.
     * @param intCodItm El código del item.
     * @return El stock del item en la bodega especificada.
     */
    private double getStkBod(int intCodEmp, String strCodBod, String intCodItm)//chiqui
    {
        double dblRes=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
////                strSQL="";
////                strSQL+="SELECT nd_stkAct";
////                strSQL+=" FROM tbm_invBod";
////                strSQL+=" WHERE co_emp=" + intCodEmp;
////                strSQL+=" AND co_bod='" + strCodBod +"'";
////                strSQL+=" AND co_itm=" + intCodItm;

        strSQL="";
                strSQL+="SELECT DISPONIBLE as nd_stkAct";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito ";
//                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" WHERE  LOCNCODE='" + strCodBod +"'";
                strSQL+=" AND ITEMNMBR='" + intCodItm +"'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblRes=rst.getDouble("nd_stkAct");
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
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return dblRes;
    }
    
    /**
     * Esta función permite actualizar los valores de las columnas auxiliares.
     * Se utilizan columnas auxiliares para almacenar el valor que tenía la celda
     * antes de ser modificada.
     * @return true: Si se pudo actualizar los valores de las columnas auxiliares.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarValColAux()
    {
        int i;
        String strCodItm, strAuxCodItm, strAuxCodItmAct;
        boolean blnRes=true;
        try
        {
            for (i=0;i<objTblMod.getRowCountTrue();i++)
            {
                strCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                strAuxCodItm=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString());
                strAuxCodItmAct=(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA)==null?"":objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIA).toString());
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS),i,INT_TBL_DAT_AUX_COD_SIS);
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR),i,INT_TBL_DAT_AUX_CAN_EGR);
                objTblMod.setValueAt(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING),i,INT_TBL_DAT_AUX_CAN_ING);
                if (strAuxCodItmAct.equals(""))
                {
                    objTblMod.setValueAt(strCodItm,i,INT_TBL_DAT_AUX_COD_SIA);
                }
                else
                {
                    if (strCodItm.equals(strAuxCodItm))
                        objTblMod.setValueAt(strAuxCodItmAct,i,INT_TBL_DAT_AUX_COD_SIA);
                    else
                        objTblMod.setValueAt(strCodItm,i,INT_TBL_DAT_AUX_COD_SIA);
                }
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
     * Esta función valida que exista el stock suficiente en la bodega para grabar el documento.
     * @return true: Si existe el stock suficiente.
     * <BR>false: En el caso contrario.
     */
    private boolean isStkBodSuf()//chiqui
    {
        //int intCodBod, intCodItm, i;
        String intCodItm="";
        int  i;
        double dblStkBod, dblCan, dblCanEgr, dblAuxCanEgr, dblCanIng, dblAuxCanIng;
//        String strCodItm, strAuxCodItm;
        String strCodBod;
        boolean blnRes=true;
        try
        {
            if (objTblMod.getRowCountTrue()>0)
            {
                objTblMod.clearRowHeaderRaise();
                strCodBod=txtCodBod.getText();
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        for (i=0; i<objTblMod.getRowCountTrue(); i++)
                        {
                            //Validar sólo si no es un item de servicio.
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_EST_SER).equals("N"))
                            {
                                if (objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR)!=null)
                                {
                                    dblStkBod=getStkBod(intCodEmp, strCodBod, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                                    dblCan=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR));
                                    if (dblStkBod<dblCan)
                                    {
                                        strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
                                        strAux+="<BR>Stock actual: " + dblStkBod;
                                        strAux+="<BR>Cantidad a egresar: " + dblCan;
                                        strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
                                        strAux+="</HTML>";
                                        objTblMod.addElementAtRowHeaderRaise(i, strAux);
                                    }
                                }
                            }
                        }
                        break;
                    case 'm':
                        for (i=0; i<objTblMod.getRowCountTrue(); i++)
                        {
                            //Validar sólo si no es un item de servicio.
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_EST_SER).equals("N"))
                            {
                                intCodItm=objUti.parseString (objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString());
                                dblCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_EGR));
                                dblAuxCanEgr=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_EGR));
                                dblCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_CAN_ING));
                                dblAuxCanIng=objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_CAN_ING));
                                dblStkBod=0;
                                if (txtCodBod.getText().equals(strCodBodDoc))
                                {
                                    strAux=objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN));
                                    if (strAux.equals("M"))
                                    {
                                        if (intCodItm==(objTblMod.getValueAt(i,INT_TBL_DAT_AUX_COD_SIS).toString()))
                                        {
                                            if ( (dblCanEgr!=dblAuxCanEgr) || (dblCanIng!=dblAuxCanIng) )
                                            {
                                                //Caso 1: El usuario sólo modificó la cantidad.
                                                dblCan=(dblAuxCanEgr-dblCanEgr) - (dblAuxCanIng-dblCanIng);
                                                //Si la cantidad es negativa validar que exista el stock suficiente.
                                                if (dblCan<0)
                                                {
                                                    dblCan=Math.abs(dblCan);
                                                    dblStkBod=getStkBod(intCodEmp, strCodBod, intCodItm);
                                                    if (dblStkBod<dblCan)
                                                    {
                                                        strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
                                                        strAux+="<BR>Stock actual: " + dblStkBod;
                                                        strAux+="<BR>Cantidad a egresar: " + dblCan;
                                                        strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
                                                        if (objTblMod.getValueAt(i, INT_TBL_DAT_AUX_CAN_EGR)!=null)
                                                        {
                                                            strAux+="<BR><I>Explicación.- </I>Al principio había egresado " + dblAuxCanEgr + "  y ahora desea egresar " + dblCanEgr + ".";
                                                            strAux+="<BR>Como en realidad necesita un stock de " + dblCan + " y sólo hay " + dblStkBod + " se concluye que le hacen falta " + (dblCan-dblStkBod);
                                                        }
                                                        else
                                                        {
                                                            strAux+="<BR><I>Explicación.- </I>Al principio había ingresado " + dblAuxCanIng + "  y ahora desea ingresar " + dblCanIng + ".";
                                                            strAux+="<BR>Como en realidad necesita un stock de " + dblCan + " y sólo hay " + dblStkBod + " se concluye que le hacen falta " + (dblCan-dblStkBod);
                                                        }
                                                        strAux+="</HTML>";
                                                        objTblMod.addElementAtRowHeaderRaise(i, strAux);
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            //Caso 2 (Paso 1): El usuario cambió algo que debe ser considerado como una eliminación.
        //                                    if (!strCodItm.equals(strAuxCodItm))
        //                                    {
        //                                        //El usuario cambió el item.
        //                                        dblStkBod=getStkBod(intCodEmp, Integer.parseInt(strAuxCodBodDes), Integer.parseInt(strAuxCodItm));
        //                                        strAux="";
        //                                        if (dblStkBod<dblAuxCan)
        //                                        {
        //                                            strAux="<HTML><FONT COLOR=\"blue\">Item: " + strAuxCodItm + " (Sistema)</FONT>";
        //                                            strAux+="<BR>Stock actual: " + dblStkBod;
        //                                            strAux+="<BR>Cantidad a devolver: " + dblAuxCan;
        //                                            strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblAuxCan-dblStkBod,objParSis.getDecimalesBaseDatos());
        //                                        }
        //                                        dblStkBod=getStkBod(intCodEmp, Integer.parseInt(strCodBodOrg), Integer.parseInt(strCodItm));
        //                                        if (dblStkBod<dblCan)
        //                                        {
        //                                            strAux=strAux.equals("")?"<HTML>":strAux + "<BR>";
        //                                            strAux+="<FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
        //                                            strAux+="<BR>Stock actual: " + dblStkBod;
        //                                            strAux+="<BR>Cantidad a transferir: " + dblCan;
        //                                            strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
        //                                        }
        //                                        if (!strAux.equals(""))
        //                                        {
        //                                            strAux+="<BR><I>Explicación.- </I>Usted cambió el item que tenía al principio por otro item.";
        //                                            strAux+="<BR>No se permitirá grabar la transferencia mientras no exista el stock suficiente para completar la devolución.";
        //                                            strAux+="</HTML>";
        //                                            objTblMod.addElementAtRowHeaderRaise(i, strAux);
        //                                        }
        //                                    }
        //                                    else if (!strCodBodOrg.equals(strAuxCodBodOrg))
        //                                    {
        //                                        //El usuario cambió la bodega origen.
        //                                        dblStkBod=getStkBod(intCodEmp, Integer.parseInt(strAuxCodBodDes), Integer.parseInt(strCodItm)) - dblAuxCan;
        //                                        strAux="";
        //                                        if (dblStkBod<dblAuxCan)
        //                                        {
        //                                            strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
        //                                            strAux+="<BR>Stock actual: " + dblStkBod + " (Bodega " + strAuxCodBodDes  + ")";
        //                                            strAux+="<BR>Cantidad a devolver: " + dblAuxCan;
        //                                            strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblAuxCan-dblStkBod,objParSis.getDecimalesBaseDatos());
        //                                        }
        //                                        dblStkBod=getStkBod(intCodEmp, Integer.parseInt(strCodBodOrg), Integer.parseInt(strCodItm));
        //                                        if (dblStkBod<dblCan)
        //                                        {
        //                                            strAux=strAux.equals("")?"<HTML>":strAux + "<BR>";
        //                                            strAux+="<FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
        //                                            strAux+="<BR>Stock actual: " + dblStkBod + " (Bodega " + strCodBodOrg  + ")";
        //                                            strAux+="<BR>Cantidad a transferir: " + dblCan;
        //                                            strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
        //                                        }
        //                                        if (!strAux.equals(""))
        //                                        {
        //                                            strAux+="<BR><I>Explicación.- </I>Usted cambió la bodega origen que tenía al principio por otra bodega.";
        //                                            strAux+="<BR>Al principio había transferido " + dblAuxCan + " items de la bodega " + strAuxCodBodDes + "  y ahora desea transferir " + dblCan + " de la bodega " + strCodBodOrg + ".";
        //                                            strAux+="<BR>No se permitirá grabar la transferencia mientras no exista el stock suficiente para completar la devolución.";
        //                                            strAux+="</HTML>";
        //                                            objTblMod.addElementAtRowHeaderRaise(i, strAux);
        //                                        }
        //                                    }
                                            //Caso 2 (Paso 2): Considerar la modificación como una inserción.
                                        }
                                    }
                                    else if (strAux.equals("I"))
                                    {
                                        //Caso 3: El usuario insertó una fila.
        //                                dblStkBod=getStkBod(intCodEmp, Integer.parseInt(strCodBodOrg), Integer.parseInt(strCodItm));
        //                                if (dblStkBod<dblCan)
        //                                {
        //                                    strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
        //                                    strAux+="<BR>Stock actual: " + dblStkBod;
        //                                    strAux+="<BR>Cantidad a transferir: " + dblCan;
        //                                    strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
        //                                    strAux+="</HTML>";
        //                                    objTblMod.addElementAtRowHeaderRaise(i, strAux);
        //                                }
                                    }
                                }
                                else
                                {
                                    //Caso especial (Paso 1): El usuario cambió la bodega y debe ser considerado como una eliminación.

                                    //Caso especial (Paso 2): Considerar la modificación como una inserción.
                                }
                            }
                        }
                        //Caso 4: El usuario eliminó una o más filas.
    //                    java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
    //                    strAux="";
    //                    for (i=0;i<arlAux.size();i++)
    //                    {
    //                        dblStkBod=getStkBod(intCodEmp, objUti.getIntValueAt(arlAux, i, INT_CFE_COD_BOD_DES), objUti.getIntValueAt(arlAux, i, INT_CFE_COD_SIS));
    //                        dblCan=objUti.getDoubleValueAt(arlAux,i,INT_CFE_CAN);
    //                        if (dblStkBod<dblCan)
    //                        {
    //                            strAux=strAux.equals("")?"<HTML>Los siguientes items fueron eliminados y no tienen stock suficiente: ":strAux;
    //                            strAux+="<BR><FONT COLOR=\"blue\">Item: " + objUti.getObjectValueAt(arlAux, i, INT_CFE_COD_SIS) + " (Sistema)</FONT>";
    //                            strAux+="<BR>Stock actual: " + dblStkBod;
    //                            strAux+="<BR>Cantidad a transferir: " + dblCan;
    //                            strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
    //                        }
    //                    }
    //                    if (!strAux.equals(""))
    //                    {
    //                        strAux+="<BR><I>Explicación.- </I>El sistema debe devolver los items que fueron eliminados de la transferencia.";
    //                        strAux+="<BR>No se permitirá grabar la transferencia mientras no exista el stock suficiente para devolver los items que eliminó.";
    //                        strAux+="</HTML>";
    //                        objTblMod.addElementAtRowHeaderRaise(objTblMod.getRowCountTrue(), strAux);
    //                    }
                        break;
                    case 'e':
                        for (i=0; i<objTblMod.getRowCountTrue(); i++)
                        {
                            //Validar sólo si no es un item de servicio.
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_EST_SER).equals("N"))
                            {
                                if (objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING)!=null)
                                {
                                    dblStkBod=getStkBod(intCodEmp, strCodBod, (objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString()));
                                    dblCan=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                                    if (dblStkBod<dblCan)
                                    {
                                        strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
                                        strAux+="<BR>Stock actual: " + dblStkBod;
                                        strAux+="<BR>Cantidad a egresar: " + dblCan;
                                        strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
                                        strAux+="</HTML>";
                                        objTblMod.addElementAtRowHeaderRaise(i, strAux);
                                    }
                                }
                            }
                        }
                        break;
                    case 'a':
                        for (i=0; i<objTblMod.getRowCountTrue(); i++)
                        {
                            //Validar sólo si no es un item de servicio.
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_EST_SER).equals("N"))
                            {
                                if (objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING)!=null)
                                {
                                    dblStkBod=getStkBod(intCodEmp, strCodBod, (objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString()));
                                    dblCan=objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING));
                                    if (dblStkBod<dblCan)
                                    {
                                        strAux="<HTML><FONT COLOR=\"blue\">Item: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT) + "</FONT>";
                                        strAux+="<BR>Stock actual: " + dblStkBod;
                                        strAux+="<BR>Cantidad a egresar: " + dblCan;
                                        strAux+="<BR>Cantidad que hace falta: " + objUti.redondear(dblCan-dblStkBod,objParSis.getDecimalesBaseDatos());
                                        strAux+="</HTML>";
                                        objTblMod.addElementAtRowHeaderRaise(i, strAux);
                                    }
                                }
                            }
                        }
                        break;
                }
                tblDat.repaint();
                if (objTblMod.getSizeRowHeaderRaise()>0)
                {
                    blnRes=false;
                }
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
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                //Obtener la fecha y hora del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                datFecAux=null;
                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 19:
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("co_emp", Short.valueOf(rstCab.getString("co_emp")));
                                mapPar.put("co_loc", Short.valueOf(rstCab.getString("co_loc")));
                                mapPar.put("co_tipDoc", Short.valueOf(rstCab.getString("co_tipDoc")));
                                mapPar.put("co_doc", Integer.valueOf(rstCab.getString("co_doc")));
                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
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
     * Esta función permite recostear los itmes de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean recostearItm()
    {
        int i;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        objUti.costearDocumento(this, objParSis, con, intCodEmp, objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    case 'm':
                        //Validar la fecha  del documento sólo si se encuentra en el modo "Modificar".
                        //Validar si se utiliza la fecha que tenia el documento inicialmente o la que tiene actualmente.
                        if (datFecDoc.compareTo(objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"))>0)
                        {
                            for (i=0;i<objTblMod.getRowCountTrue();i++)
                            {
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con,  intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            }
                        }
                        else
                        {
                            for (i=0;i<objTblMod.getRowCountTrue();i++)
                            {
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con,  intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                            }
                        }
                        break;
                    default:
                        for (i=0;i<objTblMod.getRowCountTrue();i++)
                        {
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con,  intCodEmp, objTblMod.getValueAt(i,INT_TBL_DAT_COD_SIS).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                        }
                        break;
                }
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
     * Esta función permite actualizar el campo "ne_secUltDocTbmCabMovInv" en la tabla "tbm_emp".
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarSecUltDocTbmCabMovInv()//chiqui
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_emp";
                strSQL+=" SET ne_secUltDocTbmCabMovInv=ne_secUltDocTbmCabMovInv+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" OR co_emp=" + intCodEmp;
                stm.executeUpdate(strSQL);
                stm.close();
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
     * Esta función valida que no se repita el item ingresado.
     * Si el item existe muestra un mensaje y lo borra para evitar problemas de stock.
     * @return true: Si el item existe.
     * <BR>false: En el caso contrario.
     */
    private boolean existeCodAlt()
    {
        int intFilSel;
        String strCodAlt;
        boolean blnRes=false;
        try
        {
            intFilSel=tblDat.getSelectedRow();
            strCodAlt=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_ALT));
            for (int i=0; i<objTblMod.getRowCountTrue(); i++)
            {
                if (i!=intFilSel)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT)).equals(strCodAlt))
                    {
                        blnRes=true;
                        mostrarMsgInf("<HTML>El item <FONT COLOR=\"blue\">" + strCodAlt + "</FONT> fue ingresado en la fila " + (i+1) + ".<BR>El item será eliminado para evitar problemas de stock.</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT);
                        break;
                    }
                }
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
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
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
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg="Cantidad (Egreso)";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg="Cantidad (Ingreso)";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT_EGR:
                    strMsg="Costo total (Egreso)";
                    break;
                case INT_TBL_DAT_COS_TOT_ING:
                    strMsg="Costo total (Ingreso)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularTotDoc();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

}