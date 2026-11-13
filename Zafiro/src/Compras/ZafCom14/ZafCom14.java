/*
 * ZafCom14.java
 *
 * Created on 15 de junio de 2006, 10:10 PM
 */

package Compras.ZafCom14;
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
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;



import Librerias.ZafVenConGP.ZafVenConGP;
import java.math.BigDecimal;
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
public class ZafCom14 extends javax.swing.JInternalFrame 
{
    //Constantes: 
    //Jtable: tblBod
    private static final int INT_TBL_BOD_LIN=0;                         //Línea.
    private static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    private static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    private static final int INT_TBL_BOD_COD_BOD=3;                     //Código de la bodega.
    private static final int INT_TBL_BOD_NOM_BOD=4;                     //Nombre de la bodega.
    
    //Jtable: TblDat 
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    private static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    private static final int INT_TBL_DAT_COD_ALT=3;                     //Código del alterno del ítem.
    private static final int INT_TBL_DAT_COD_ALT_DOS=4;                 //Código del alterno 2 del ítem.
    private static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    private static final int INT_TBL_DAT_DEC_UNI=6;                     //Descripción corta de la unidad de medida.
    private static final int INT_TBL_DAT_CDI_STK=7;                     //Columna dinámica: Stock.

    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_TBL_DAT_NUM_TOT_CES=7;                 //Número total de columnas estáticas.
    private static final int INT_TBL_DAT_FAC_NUM_CDI=1;                 //Factor para calcular total de columnas dinámicas. 
    
    private static final int INT_NUM_COL_ADI_TBL_DATGRP=3;              //Cantidad de Columnas Adicionales por Grupo.   [Stock-Disponible-Sobrante] 
    private static final int INT_NUM_COL_ADI_TBL_DATEMP=2;              //Cantidad de Columnas Adicionales por Empresa. [Stock-Disponible]    

    //Arraylist: Datos Bodega
    private ArrayList arlRegBod, arlDatBod;
    static final int INT_ARL_COD_EMP=0;
    static final int INT_ARL_NOM_EMP=1;
    static final int INT_ARL_COD_BOD=2;
    static final int INT_ARL_NOM_BOD=3;
    static final int INT_ARL_EST_BOD=4;
    static final int INT_ARL_STK_SOB=5;
    
    //Arraylist: Datos Grupo
    private ArrayList arlRegGrp, arlDatGrp;
    final int INT_ARL_COL_ADD_COD_EMP_GRP=0;
    final int INT_ARL_COL_ADD_NOM_EMP_GRP=1;
    final int INT_ARL_COL_ADD_COD_BOD_GRP=2;
    final int INT_ARL_COL_ADD_NOM_BOD_GRP=3;
    final int INT_ARL_COL_ADD_NUM_COL_GRP=4;

    //Arraylist: Datos Empresa
    private ArrayList arlRegEmp, arlDatEmp;
    final int INT_ARL_COL_ADD_COD_EMP=0;
    final int INT_ARL_COL_ADD_NOM_EMP=1;
    final int INT_ARL_COL_ADD_COD_BOD=2;
    final int INT_ARL_COL_ADD_NOM_BOD=3;
    final int INT_ARL_COL_ADD_NUM_COL=4;
        
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafMouMotAdaGlo objMouMotAdaGlo;                  //ToolTipText en TableHeader.    
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafVenConGP vcoItm;                           //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private Connection conGP;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatBod, vecCabBod;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnPerVerColStkSob;
    private String strCodAlt, strCodAlt2, strNomItm;    //Contenido del campo al obtener el foco.
    private int intNumColBodCodEmpGrp;
    private int intColModIni;
    private int intColGrpAdi, intColGrpIni, intColGrpFin;
    private int intColEmpAdi, intColEmpIni, intColEmpFin;
    private int intColSumStkDisAdi, intColSumStkIni, intColSumStkFin;
    private int intColStkFalSobAdi, intColStkFalSobIni, intColStkFalSobFin;

    private java.awt.Color colFonColNar=new java.awt.Color(255,221,187);
    private java.awt.Color colFonColVer=new java.awt.Color(228,228,203);
    
    /** Crea una nueva instancia de la clase ZafCom14. */
    public ZafCom14(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatGrp=new ArrayList();
            arlDatEmp=new ArrayList();
            objPerUsr=new ZafPerUsr(objParSis);
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){          this.setTitle(this.getTitle() + " [ERROR]");      }
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
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panDesHasItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panBod = new javax.swing.JPanel();
        panOptBod = new javax.swing.JPanel();
        optBodTod = new javax.swing.JRadioButton();
        panLisBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkSolStk = new javax.swing.JCheckBox();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });

        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });

        panDesHasItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panDesHasItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panDesHasItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 16, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 15, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panDesHasItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 16, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 15, 100, 20);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmTer.setEnabled(false);
        panItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 16, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 15, 204, 20);

        panBod.setLayout(new java.awt.BorderLayout());

        panOptBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panOptBod.setPreferredSize(new java.awt.Dimension(100, 92));
        panOptBod.setLayout(null);

        optBodTod.setSelected(true);
        optBodTod.setText("Todas las bodegas");
        optBodTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBodTodActionPerformed(evt);
            }
        });
        panOptBod.add(optBodTod);
        optBodTod.setBounds(6, 14, 440, 14);

        panBod.add(panOptBod, java.awt.BorderLayout.NORTH);

        panLisBod.setLayout(new java.awt.BorderLayout());

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

        panLisBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panBod.add(panLisBod, java.awt.BorderLayout.CENTER);

        chkSolStk.setText("Mostrar sólo items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panFilLayout = new javax.swing.GroupLayout(panFil);
        panFil.setLayout(panFilLayout);
        panFilLayout.setHorizontalGroup(
            panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(panBod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panFilLayout.createSequentialGroup()
                .addGroup(panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panFilLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(optTod, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panFilLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(optFil, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panFilLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panFilLayout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(txtCodItm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblItm, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtCodAlt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtCodAlt2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtNomItm, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(butItm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panFilLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(panDesHasItm, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(panItmTer, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panFilLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(chkSolStk, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panFilLayout.setVerticalGroup(
            panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFilLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(optTod, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(optFil, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodItm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblItm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodAlt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNomItm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(butItm, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panFilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panDesHasItm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panItmTer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panBod, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkSolStk, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

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

        panBar.setPreferredSize(new java.awt.Dimension(320, 44));
        panBar.setLayout(new java.awt.BorderLayout());

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

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
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

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
       //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
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

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

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

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            chkSolStk.setSelected(false);
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void optBodTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBodTodActionPerformed
        if(optBodTod.isSelected())
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            intColGrpAdi=0;
        }
        else
            optBodTod.setSelected(true);
        cargarBod();
        seleccionaBodegas();
    }//GEN-LAST:event_optBodTodActionPerformed

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodAlt2);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optBodTod;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDesHasItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panLisBod;
    private javax.swing.JPanel panOptBod;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + "v2.1");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            chkSolStk.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();

            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
            {
                optBodTod.setVisible(false);
                optBodTod.setEnabled(false);

                
                
                panOptBod.setPreferredSize(new java.awt.Dimension(100, 19));
            }

            blnPerVerColStkSob=true;

            if(!objPerUsr.isOpcionEnabled(658)){
                butCon.setVisible(false);
            }
            if(!objPerUsr.isOpcionEnabled(659)){
                butCer.setVisible(false);
            }
            if(!objPerUsr.isOpcionEnabled(2599)){
                blnPerVerColStkSob=false;
            }

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
private String obtenerTooltipInformacionStock()
{
    return "<html>"
        + "<div style='width:550px; font-family:Arial; font-size:9px;'>"
        + "<p style='text-align:center; font-size:10px;'><b>INFORMACIÓN DE STOCK Y DISPONIBILIDAD</b></p>"
        + "<table border='1' cellpadding='3' cellspacing='0' width='100%' style='font-size:9px;'>"
        + "<tr bgcolor='#D9EAF7'>"
        + "<th>Columna</th>"
        + "<th>Información / Origen</th>"
        + "</tr>"

        + "<tr><td><b>Código</b></td>"
        + "<td>Código del artículo registrado en Dynamics GP.</td></tr>"

        + "<tr><td><b>Código alterno 2</b></td>"
        + "<td>Código Zafiro.</td></tr>"

        + "<tr><td><b>Nombre del artículo</b></td>"
        + "<td>Descripción del artículo.</td></tr>"

        + "<tr><td><b>Unidad</b></td>"
        + "<td>Unidad del artículo.</td></tr>"

        + "<tr><td><b>Existencia</b></td>"
        + "<td>Stock físico registrado en Dynamics GP.</td></tr>"

        + "<tr><td><b>Reserva WEB</b></td>"
        + "<td>Cantidad reservada obtenida de la WEB.</td></tr>"

        + "<tr><td><b>Reserva GP</b></td>"
        + "<td>Cantidad reservada obtenida de Dynamics GP.</td></tr>"

        + "<tr><td><b>Disponible</b></td>"
        + "<td>Existencia - Cantidad asignada GP - Reserva WEB.</td></tr>"

        + "<tr><td><b>Reposiciones</b></td>"
        + "<td>Reposición, transferencia y compra del artículo.</td></tr>"

        + "<tr><td><b>Total</b></td>"
        + "<td>Suma de los valores correspondientes a todas las bodegas consultadas.</td></tr>"

        + "</table>"
        + "</div>"
        + "</html>";
}

    private class ZafMouMotAdaGlo extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            int intColSel=-1;
            
//            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
//                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%1+INT_TBL_DAT_NUM_TOT_CES;
                        
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg=obtenerTooltipInformacionStock();
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            // Permitir redimensionar manualmente las columnas.
            tblDat.getTableHeader().setResizingAllowed(true);

            // Permitir redimensionar las columnas existentes.
            javax.swing.table.TableColumnModel tcmRedim = tblDat.getColumnModel();

            for (int i = 0; i < tcmRedim.getColumnCount(); i++)
            {
                tcmRedim.getColumn(i).setResizable(true);
            }            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaGlo = new ZafMouMotAdaGlo();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaGlo);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                    
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Libero los objetos auxiliares.
            tcmAux=null;

            intColModIni=objTblMod.getColumnCount();

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
private void habilitarRedimensionamientoColumnas()
{
    tblDat.setAutoResizeMode(
        javax.swing.JTable.AUTO_RESIZE_OFF
    );

    javax.swing.table.TableColumnModel tcm =
            tblDat.getColumnModel();

    for (int i = 0; i < tcm.getColumnCount(); i++)
    {
        tcm.getColumn(i).setResizable(true);
    }

    tblDat.getTableHeader().setResizingAllowed(true);
    tblDat.getTableHeader().setReorderingAllowed(false);

    tblDat.getTableHeader().revalidate();
    tblDat.getTableHeader().repaint();
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
            vecDatBod=new Vector();    //Almacena los datos
            vecCabBod=new Vector(6);   //Almacena las cabeceras
            vecCabBod.clear();
            vecCabBod.add(INT_TBL_BOD_LIN,"");
            vecCabBod.add(INT_TBL_BOD_CHK,"");
            vecCabBod.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCabBod.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCabBod.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCabBod);
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
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setResizable(false);
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
            //tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intCodEmpNotGrp;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        intCodEmpNotGrp=Integer.parseInt(objTblModBod.getValueAt(tblBod.getSelectedRow(), INT_TBL_BOD_COD_EMP).toString());
                        if(optBodTod.isSelected())
                            objTblCelEdiChk.setCancelarEdicion(false);
                        else{
                            if(intCodEmpNotGrp==objParSis.getCodigoEmpresaGrupo())
                                objTblCelEdiChk.setCancelarEdicion(false);
                            else
                                objTblCelEdiChk.setCancelarEdicion(true);
                        }

                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * Solo agrega columnas cuando el Código de la empresa de la bodega es Grupo.
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDatGrp()
    {
        javax.swing.table.TableColumn tbc;
        String strCodEmp="", strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        boolean blnRes=true;
        int intCodEmpBodGrp=-1;
        intColGrpAdi=0;
        int i;
        
        try
        {
            arlDatGrp.clear();
            intColGrpIni=objTblMod.getColumnCount();
            
            //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
            for(int j=0; j<objTblModBod.getRowCountTrue(); j++)
            {
                intCodEmpBodGrp=Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString());
                if(intCodEmpBodGrp==objParSis.getCodigoEmpresaGrupo())
                {
                    if(objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        intColGrpAdi++;
                        arlRegGrp=new ArrayList();
                        arlRegGrp.add(INT_ARL_COL_ADD_COD_EMP_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP));
                        arlRegGrp.add(INT_ARL_COL_ADD_COD_BOD_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD));
                        arlRegGrp.add(INT_ARL_COL_ADD_NOM_BOD_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_BOD));
                        arlRegGrp.add(INT_ARL_COL_ADD_NUM_COL_GRP, "");
                        arlDatGrp.add(arlRegGrp);
                    }
                }
            }
            System.out.println("Roseee.intColGrpAdi: "+intColGrpAdi);
            //</editor-fold>

            int p=0;//reutilizo la variable
            int q=0;
            for (i=0; i<(intColGrpAdi*INT_NUM_COL_ADI_TBL_DATGRP); i++)
            {
                boolean blnUltCol=false;
                intCodEmpBodGrp=objUti.getIntValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP);
                if(intCodEmpBodGrp==objParSis.getCodigoEmpresaGrupo())
                {
                    int intIndColGrp=(intColGrpIni+i*INT_TBL_DAT_FAC_NUM_CDI); //Obtengo Indice de la Columna.
                    tbc=new javax.swing.table.TableColumn(intIndColGrp);       //Creo la columna dinamica.
                    objUti.setStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NUM_COL_GRP, "" + (intIndColGrp)); //Actualiza en el Arraylist el último índice.

                    if((intIndColGrp-1)%INT_NUM_COL_ADI_TBL_DATGRP==0)  
                    {
                      tbc.setHeaderValue("Stock");
                    }
                    else if( (intIndColGrp+1) % INT_NUM_COL_ADI_TBL_DATGRP==0) // Verifico que el indice posterior haya sido multiplo de 3(stock).
                    {
                      tbc.setHeaderValue("Disponible");
                    } 
                    else if (intIndColGrp%INT_NUM_COL_ADI_TBL_DATGRP==0) //Si es multiplo de 3.
                    {
                      tbc.setHeaderValue("Sobrante");
                      blnUltCol=true;
                    }

                    //Configurar JTable: Establecer el ancho de la columna.
                    if(objParSis.getCodigoUsuario()==1)
                        tbc.setPreferredWidth(55);
                    else
                    {
                        if(blnPerVerColStkSob)
                            tbc.setPreferredWidth(55);
                        else
                        {
                            if( intIndColGrp  % INT_NUM_COL_ADI_TBL_DATGRP==0) //Sobrante
                            {
                                tbc.setWidth(0);
                                tbc.setMaxWidth(0);
                                tbc.setMinWidth(0);
                                tbc.setPreferredWidth(0);
                                tbc.setResizable(false);
                            }
                            else // Stock y Disponible
                            {
                                tbc.setPreferredWidth(55);
                            }
                        }
                    }

                    //Configurar JTable: Renderizar celdas.
                    objTblCelRenLbl=new ZafTblCelRenLbl();
                    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                    objTblCelRenLbl.setBackground(colFonColVer);
                    tbc.setCellRenderer(objTblCelRenLbl);
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                    //Agrupar las columnas.
                    //Columnas Grupo y Empresas.
                    if (!strCodEmp.equals(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP)))
                    {
                        objTblHeaColGrpEmp=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NOM_EMP_GRP));
                        objTblHeaColGrpEmp.setHeight(16);
                        strCodEmp=objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP);
                        objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                    }
                    //Columnas Bodegas
                    if (!strCodBod.equals(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_BOD_GRP)))
                    {                      
                        objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NOM_BOD_GRP));
                        objTblHeaColGrpBod.setHeight(16*2);
                        strCodBod=objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_BOD_GRP);
                        objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                    }                   
                    
                    //Columnas Stock, Disponible y Sobrante
                    objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                    if(blnUltCol)
                        p++;

                }
            }
            objTblCelRenLbl=null;
            intColGrpFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {      
        int i;
        javax.swing.table.TableColumn tbc;
        String strCodEmp="", strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        boolean blnRes=true;
        //int p=0;//borrar p y colocar i donde estan las p, cuando sea solo 1 columna dinamica por bodega.Ej.:Stock.
        int intCodEmpBodEmp=-1;
        intColEmpAdi=0;

        java.awt.Color colFonCol;
   
        colFonCol=colFonColNar; 
        int intColor=0;

        try
        {
            arlDatEmp.clear();
            intColEmpIni=objTblMod.getColumnCount();
            
            //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
            for(int j=0; j<objTblModBod.getRowCountTrue(); j++)
            {
                intCodEmpBodEmp=Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString());
                if(intCodEmpBodEmp!=objParSis.getCodigoEmpresaGrupo())
                {
                    if(objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        intColEmpAdi++;
                        arlRegEmp=new ArrayList();
                        arlRegEmp.add(INT_ARL_COL_ADD_COD_EMP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP));
                        arlRegEmp.add(INT_ARL_COL_ADD_COD_BOD, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD));
                        arlRegEmp.add(INT_ARL_COL_ADD_NOM_BOD, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_BOD));
                        arlRegEmp.add(INT_ARL_COL_ADD_NUM_COL, "");
                        arlDatEmp.add(arlRegEmp);
                    }
                }
            }
            //</editor-fold>
            
            int p=0;//reutilizo la variable
            for (i=0; i<intColEmpAdi*INT_NUM_COL_ADI_TBL_DATEMP; i++)
            {
                boolean blnUltCol=false;
                boolean blnEmpDif=false;
                int intIndColEmp=intColEmpIni+i*INT_TBL_DAT_FAC_NUM_CDI;
                tbc=new javax.swing.table.TableColumn(intIndColEmp);
                objUti.setStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NUM_COL, "" + (intIndColEmp)); //Actualiza en el Arraylist el último índice.
                
                if((i%2)==0)
                {
                     tbc.setHeaderValue("Stock");                    
                }
                else
                {
                     tbc.setHeaderValue("Disponible");
                     blnUltCol=true;
                }
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(55);
                
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                //Agrupar las columnas.    
                //Empresas
                if (!strCodEmp.equals(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_EMP)))
                {
                    blnEmpDif=true;
                    objTblHeaColGrpEmp=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_EMP));
                    objTblHeaColGrpEmp.setHeight(16);
                   
                    strCodEmp=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_EMP);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                   

                    if((intColor%2)==0)
                    {
                        colFonCol=colFonColNar;
                        intColor++;
                    }
                    else
                    {
                        colFonCol=colFonColVer;
                        intColor++;
                    }                                     
                }
                objTblCelRenLbl.setBackground(colFonCol);
                
                if (!strCodBod.equals(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD)))
                {
                    objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_BOD));
                    objTblHeaColGrpBod.setHeight(16*2);
                    strCodBod=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD);
                    objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                }
                else
                {
                    if(blnEmpDif)
                    {
                        objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_BOD));
                        objTblHeaColGrpBod.setHeight(16*2);
                        strCodBod=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD);
                        objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                    }
                }
              
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(intIndColEmp));

                if(blnUltCol)
                    p++;
                
            }
            objTblCelRenLbl=null;
            intColEmpFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDatStkDisFalSob()
    {
        int i;
        javax.swing.table.TableColumn tbc;
        boolean blnRes=true;
        intColStkFalSobAdi=4;
        String strTit="";
        try
        {
            intColStkFalSobIni=objTblMod.getColumnCount();

            for (i=0; i<intColStkFalSobAdi; i++)
            {
                tbc=new javax.swing.table.TableColumn(intColStkFalSobIni+i*INT_TBL_DAT_FAC_NUM_CDI);
                strTit=( i==0?"Stock": (i==1?"Disponible": (i==2?"Faltantes": (i==3?"Sobrantes":"") ))  );
                tbc.setHeaderValue(strTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                if(objParSis.getCodigoUsuario()==1)
                    tbc.setPreferredWidth(80);
                else
                {
                    if(blnPerVerColStkSob)
                        tbc.setPreferredWidth(80);
                    else
                    {
                        if( (i==0) || (i==1) || (i==2) )  //stock
                        { 
                            tbc.setPreferredWidth(88);
                        }
                        else  //sobrante
                        {
                            tbc.setWidth(0);
                            tbc.setMaxWidth(0);
                            tbc.setMinWidth(0);
                            tbc.setPreferredWidth(0);
                            tbc.setResizable(false);
                        }
                    }
                }


                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intColStkFalSobFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColSumStkDis()
    {
        int i;
        javax.swing.table.TableColumn tbc;
        boolean blnRes=true;
        intColSumStkDisAdi=2;
        String strTitTbl="";
        ZafTblHeaGrp objTblHeaSumTot = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaSumTot.setHeight(16 * 4);
        ZafTblHeaColGrp objTblHeaColSumTot = null;
        try
        {
            intColSumStkIni=objTblMod.getColumnCount();
            for (i=0; i<intColSumStkDisAdi; i++)
            {
                strTitTbl = (i==0 ? "Stock" : (i==1 ? "Disponible" : ""));
                tbc=new javax.swing.table.TableColumn(intColSumStkIni+i*INT_TBL_DAT_FAC_NUM_CDI);
                tbc.setHeaderValue("" + strTitTbl + "");
                //Configurar JTable: Establecer el ancho de la columna.
////////                if(!optStkFalSob.isSelected())
////////                {
////////                    tbc.setPreferredWidth(80);
////////                }
////////                else
////////                {
////////                    tbc.setWidth(0);
////////                    tbc.setMaxWidth(0);
////////                    tbc.setMinWidth(0);
////////                    tbc.setPreferredWidth(0);
////////                }
                
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);                
                tbc.setResizable(false);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0)
                {
                    objTblHeaColSumTot=new ZafTblHeaColGrp("Sumatoria Total");
                    objTblHeaColSumTot.setHeight(16*3);
                }
                objTblHeaSumTot.addColumnGroup(objTblHeaColSumTot);
                objTblHeaColSumTot.add(tblDat.getColumnModel().getColumn(intColSumStkIni+i));
            }
            objTblCelRenLbl=null;
            intColSumStkFin=objTblMod.getColumnCount();
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
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las bodegas.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo las bodegas de la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            conGP=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stm=conGP.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1)
                {
                    strSQL="";
                    strSQL+="SELECT 1 as co_emp,trim(locncode) as bodNomCor, trim(locndscr) as bodNomLar, 'A' as st_reg";                    
                    strSQL+=" FROM " + objParSis.getNombreBaseDatosGP() + "..IV40700 AS a1";
                    
                }
                
//////                if(objParSis.getCodigoUsuario()==1){
//////                    strSQL="";
//////                    strSQL+="SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
//////                    strSQL+=" FROM tbm_emp AS a1";
//////                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
//////                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
//////                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                    else{//la empresa es grupo(0)
//////                        if(optBodTod.isSelected())
//////                            strSQL+="";
//////                        else if(optBodEmp.isSelected())
//////                            strSQL+=" WHERE a1.co_emp<>" + intCodEmp;
//////                        else if(optBodGrp.isSelected())
//////                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                        else if(optBodGrpIncRel.isSelected())
//////                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                    }
//////                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
//////                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
//////                }
//////                else
//////                {
//////                    strSQL="";
//////                    strSQL+=" SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
//////                    strSQL+=" FROM tbm_emp AS a1";
//////                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
//////                    strSQL+=" INNER JOIN tbr_bodlocprgusr AS a3";
//////                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
//////
//////                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
//////                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                    else{//la empresa es grupo(0)
//////                        if(optBodTod.isSelected())
//////                            strSQL+="";
//////                        else if(optBodEmp.isSelected())
//////                            strSQL+=" WHERE a1.co_emp<>" + intCodEmp;
//////                        else if(optBodGrp.isSelected())
//////                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                        else if(optBodGrpIncRel.isSelected())
//////                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
//////                    }
//////                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
//////                    strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
//////                    strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
//////                    strSQL+=" AND a3.st_reg IN('A','P')";
//////                    strSQL+=" ORDER BY a1.co_emp, a3.ne_ord, a2.co_bod";
//////                }

                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatBod.clear();
                intNumColBodCodEmpGrp=0;
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    if (rst.getString("st_reg").equals("A"))
                        vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_BOD_CHK,null);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("bodNomCor"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("bodNomLar"));
                    vecDatBod.add(vecReg);

                    if(rst.getInt("co_emp")==objParSis.getCodigoEmpresaGrupo())
                        intNumColBodCodEmpGrp++;

                }
                rst.close();
                stm.close();
                conGP.close();
                rst=null;
                stm=null;
                conGP=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDatBod);
                tblBod.setModel(objTblModBod);
                vecDatBod.clear();
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



    private java.util.ArrayList<String> arrBodegasConsulta =
            new java.util.ArrayList<String>();

    private java.util.ArrayList<String> arrTiposTransito =
            new java.util.ArrayList<String>();

    private java.util.HashMap<String, Integer> mapColumnaDato =
            new java.util.HashMap<String, Integer>();
    
private java.util.LinkedHashMap<String, java.util.HashMap<String, BigDecimal>>
        mapDatosItemBodega =
        new java.util.LinkedHashMap<String, java.util.HashMap<String, BigDecimal>>();


private java.util.LinkedHashMap<String, java.util.HashMap<String, String>>
        mapDatosGeneralesItem =
        new java.util.LinkedHashMap<String, java.util.HashMap<String, String>>();     
    
private String obtenerBodegasSeleccionadas()
{
    String strBodegas = "";

    try
    {
        arrBodegasConsulta.clear();

        int intNumFil = objTblModBod.getRowCountTrue();

        for(int i=0; i<intNumFil; i++)
        {
            if(objTblModBod.isChecked(i, INT_TBL_BOD_CHK))
            {
                String strBodega = objTblModBod
                        .getValueAt(i, INT_TBL_BOD_COD_BOD)
                        .toString()
                        .trim();

                arrBodegasConsulta.add(strBodega);

                if(!strBodegas.equals(""))
                {
                    strBodegas += ",";
                }

                strBodegas += strBodega;
            }
        }
    }
    catch(Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return strBodegas;
}    

private boolean obtenerTiposTransito(java.sql.ResultSet rst)
{
    boolean blnRes = true;

    try
    {
        arrTiposTransito.clear();

        java.sql.ResultSetMetaData rstMeta =
                rst.getMetaData();

        int intNumCol =
                rstMeta.getColumnCount();

        /*
         * Columnas 1 a 10:
         *
         * co_item
         * tx_codalt
         * tx_codalt2
         * tx_nomitm
         * tx_descor
         * existencia
         * nd_candis
         * BODEGA
         * nd_resWEB
         * nd_resGP
         *
         * Desde la 11:
         * reposicion
         * transferencia
         * compra
         * etc.
         */
        for(int i=11; i<=intNumCol; i++)
        {
            String strTipo =
                    rstMeta.getColumnLabel(i);

            if(strTipo != null &&
               !strTipo.trim().equals(""))
            {
                arrTiposTransito.add(
                        strTipo.trim()
                );
            }
        }
    }
    catch(Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }

    return blnRes;
}

private boolean agregarColumnasStockTransito()
{
    boolean blnRes = true;

    try
    {
        mapColumnaDato.clear();

        intColStkFalSobIni =
                objTblMod.getColumnCount();


        /*==========================================================
          OBTENER HEADER AGRUPADO
        ==========================================================*/
        ZafTblHeaGrp objTblHeaGrp =
                (ZafTblHeaGrp)tblDat.getTableHeader();

        /*
         * NIVEL 1: BODEGA
         * NIVEL 2: EXISTENCIA / RESERVA / DISPONIBLE / TRANSITO
         * NIVEL 3: COLUMNAS FISICAS
         */
        objTblHeaGrp.setHeight(16 * 3);


        /*==========================================================
          RECORRER BODEGAS
        ==========================================================*/
        for(String strBodega : arrBodegasConsulta)
        {
            /*======================================================
              NIVEL 1: BODEGA
            ======================================================*/
            ZafTblHeaColGrp objGrpBodega =
                    new ZafTblHeaColGrp(
                            strBodega
                    );

            objGrpBodega.setHeight(16);


            /*======================================================
              EXISTENCIA
            ======================================================*/
            ZafTblHeaColGrp objGrpExistencia =
                    new ZafTblHeaColGrp(
                            "EXISTENCIA"
                    );

            objGrpExistencia.setHeight(16);


            int intColExistencia =
                    objTblMod.getColumnCount();

            javax.swing.table.TableColumn tbcExistencia =
                    new javax.swing.table.TableColumn(
                            intColExistencia
                    );

            tbcExistencia.setHeaderValue("");
            tbcExistencia.setPreferredWidth(75);
            tbcExistencia.setResizable(false);

            objTblCelRenLbl =
                    new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(
                    javax.swing.JLabel.RIGHT
            );

            objTblCelRenLbl.setTipoFormato(
                    objTblCelRenLbl.INT_FOR_NUM
            );

            objTblCelRenLbl.setFormatoNumerico(
                    objParSis.getFormatoNumero(),
                    false,
                    true
            );

            tbcExistencia.setCellRenderer(
                    objTblCelRenLbl
            );

            objTblMod.addColumn(
                    tblDat,
                    tbcExistencia
            );

            mapColumnaDato.put(
                    strBodega + "|existencia",
                    intColExistencia
            );

            objGrpExistencia.add(
                    tblDat.getColumnModel().getColumn(
                            intColExistencia
                    )
            );


            /*======================================================
              RESERVA
            ======================================================*/
            ZafTblHeaColGrp objGrpReserva =
                    new ZafTblHeaColGrp(
                            "RESERVA"
                    );

            objGrpReserva.setHeight(16);


            /*======================================================
              RESERVA WEB
            ======================================================*/
            int intColReservaWeb =
                    objTblMod.getColumnCount();

            javax.swing.table.TableColumn tbcReservaWeb =
                    new javax.swing.table.TableColumn(
                            intColReservaWeb
                    );

            tbcReservaWeb.setHeaderValue("WEB");
            tbcReservaWeb.setPreferredWidth(75);
            tbcReservaWeb.setResizable(false);

            objTblCelRenLbl =
                    new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(
                    javax.swing.JLabel.RIGHT
            );

            objTblCelRenLbl.setTipoFormato(
                    objTblCelRenLbl.INT_FOR_NUM
            );

            objTblCelRenLbl.setFormatoNumerico(
                    objParSis.getFormatoNumero(),
                    false,
                    true
            );

            tbcReservaWeb.setCellRenderer(
                    objTblCelRenLbl
            );

            objTblMod.addColumn(
                    tblDat,
                    tbcReservaWeb
            );

            mapColumnaDato.put(
                    strBodega + "|nd_resWEB",
                    intColReservaWeb
            );

            objGrpReserva.add(
                    tblDat.getColumnModel().getColumn(
                            intColReservaWeb
                    )
            );


            /*======================================================
              RESERVA GP
            ======================================================*/
            int intColReservaGP =
                    objTblMod.getColumnCount();

            javax.swing.table.TableColumn tbcReservaGP =
                    new javax.swing.table.TableColumn(
                            intColReservaGP
                    );

            tbcReservaGP.setHeaderValue("GP");
            tbcReservaGP.setPreferredWidth(75);
            tbcReservaGP.setResizable(false);

            objTblCelRenLbl =
                    new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(
                    javax.swing.JLabel.RIGHT
            );

            objTblCelRenLbl.setTipoFormato(
                    objTblCelRenLbl.INT_FOR_NUM
            );

            objTblCelRenLbl.setFormatoNumerico(
                    objParSis.getFormatoNumero(),
                    false,
                    true
            );

            tbcReservaGP.setCellRenderer(
                    objTblCelRenLbl
            );

            objTblMod.addColumn(
                    tblDat,
                    tbcReservaGP
            );

            mapColumnaDato.put(
                    strBodega + "|nd_resGP",
                    intColReservaGP
            );

            objGrpReserva.add(
                    tblDat.getColumnModel().getColumn(
                            intColReservaGP
                    )
            );


            /*======================================================
              DISPONIBLE
            ======================================================*/
            ZafTblHeaColGrp objGrpDisponible =
                    new ZafTblHeaColGrp(
                            "DISPONIBLE"
                    );

            objGrpDisponible.setHeight(16);


            int intColDisponible =
                    objTblMod.getColumnCount();

            javax.swing.table.TableColumn tbcDisponible =
                    new javax.swing.table.TableColumn(
                            intColDisponible
                    );

            /*
             * El título DISPONIBLE ya se muestra en el grupo,
             * por eso la columna física queda sin texto.
             */
            tbcDisponible.setHeaderValue("");
            tbcDisponible.setPreferredWidth(75);
            tbcDisponible.setResizable(false);

            objTblCelRenLbl =
                    new ZafTblCelRenLbl();

            objTblCelRenLbl.setHorizontalAlignment(
                    javax.swing.JLabel.RIGHT
            );

            objTblCelRenLbl.setTipoFormato(
                    objTblCelRenLbl.INT_FOR_NUM
            );

            objTblCelRenLbl.setFormatoNumerico(
                    objParSis.getFormatoNumero(),
                    false,
                    true
            );

            tbcDisponible.setCellRenderer(
                    objTblCelRenLbl
            );

            objTblMod.addColumn(
                    tblDat,
                    tbcDisponible
            );

            mapColumnaDato.put(
                    strBodega + "|nd_candis",
                    intColDisponible
            );

            objGrpDisponible.add(
                    tblDat.getColumnModel().getColumn(
                            intColDisponible
                    )
            );


            /*======================================================
              TRANSITO
            ======================================================*/
            ZafTblHeaColGrp objGrpTransito = null;

            if(!arrTiposTransito.isEmpty())
            {
                objGrpTransito =
                        new ZafTblHeaColGrp(
                                "TRÁNSITO"
                        );

                objGrpTransito.setHeight(16);


                for(String strTipo : arrTiposTransito)
                {
                    int intColTransito =
                            objTblMod.getColumnCount();

                    javax.swing.table.TableColumn tbcTransito =
                            new javax.swing.table.TableColumn(
                                    intColTransito
                            );

                    tbcTransito.setHeaderValue(
                            formatearNombreMovimiento(
                                    strTipo
                            )
                    );

                    tbcTransito.setPreferredWidth(75);
                    tbcTransito.setResizable(false);

                    objTblCelRenLbl =
                            new ZafTblCelRenLbl();

                    objTblCelRenLbl.setHorizontalAlignment(
                            javax.swing.JLabel.RIGHT
                    );

                    objTblCelRenLbl.setTipoFormato(
                            objTblCelRenLbl.INT_FOR_NUM
                    );

                    objTblCelRenLbl.setFormatoNumerico(
                            objParSis.getFormatoNumero(),
                            false,
                            true
                    );

                    tbcTransito.setCellRenderer(
                            objTblCelRenLbl
                    );

                    objTblMod.addColumn(
                            tblDat,
                            tbcTransito
                    );

                    mapColumnaDato.put(
                            strBodega + "|" + strTipo,
                            intColTransito
                    );

                    objGrpTransito.add(
                            tblDat.getColumnModel().getColumn(
                                    intColTransito
                            )
                    );
                }
            }


            /*======================================================
              ARMAR BODEGA

              BODEGA
                EXISTENCIA
                RESERVA
                DISPONIBLE
                TRANSITO
            ======================================================*/
            objGrpBodega.add(
                    objGrpExistencia
            );

            objGrpBodega.add(
                    objGrpReserva
            );

            objGrpBodega.add(
                    objGrpDisponible
            );

            if(objGrpTransito != null)
            {
                objGrpBodega.add(
                        objGrpTransito
                );
            }


            objTblHeaGrp.addColumnGroup(
                    objGrpBodega
            );
        }


        /*==========================================================
          SUMATORIA TOTAL
        ==========================================================*/
        ZafTblHeaColGrp objGrpSumatoria =
                new ZafTblHeaColGrp(
                        "SUMATORIA TOTAL"
                );

        objGrpSumatoria.setHeight(16);


        /*==========================================================
          TOTAL EXISTENCIA
        ==========================================================*/
        ZafTblHeaColGrp objGrpSumExistencia =
                new ZafTblHeaColGrp(
                        "EXISTENCIA"
                );

        objGrpSumExistencia.setHeight(16);


        int intColSumExistencia =
                objTblMod.getColumnCount();

        javax.swing.table.TableColumn tbcSumExistencia =
                new javax.swing.table.TableColumn(
                        intColSumExistencia
                );

        tbcSumExistencia.setHeaderValue("");
        tbcSumExistencia.setPreferredWidth(75);
        tbcSumExistencia.setResizable(false);

        objTblCelRenLbl =
                new ZafTblCelRenLbl();

        objTblCelRenLbl.setHorizontalAlignment(
                javax.swing.JLabel.RIGHT
        );

        objTblCelRenLbl.setTipoFormato(
                objTblCelRenLbl.INT_FOR_NUM
        );

        objTblCelRenLbl.setFormatoNumerico(
                objParSis.getFormatoNumero(),
                false,
                true
        );

        tbcSumExistencia.setCellRenderer(
                objTblCelRenLbl
        );

        objTblMod.addColumn(
                tblDat,
                tbcSumExistencia
        );

        mapColumnaDato.put(
                "TOTAL|existencia",
                intColSumExistencia
        );

        objGrpSumExistencia.add(
                tblDat.getColumnModel().getColumn(
                        intColSumExistencia
                )
        );


        /*==========================================================
          TOTAL RESERVA
        ==========================================================*/
        ZafTblHeaColGrp objGrpSumReserva =
                new ZafTblHeaColGrp(
                        "RESERVA"
                );

        objGrpSumReserva.setHeight(16);


        /*==========================================================
          TOTAL RESERVA WEB
        ==========================================================*/
        int intColSumReservaWeb =
                objTblMod.getColumnCount();

        javax.swing.table.TableColumn tbcSumReservaWeb =
                new javax.swing.table.TableColumn(
                        intColSumReservaWeb
                );

        tbcSumReservaWeb.setHeaderValue("WEB");
        tbcSumReservaWeb.setPreferredWidth(75);
        tbcSumReservaWeb.setResizable(false);

        objTblCelRenLbl =
                new ZafTblCelRenLbl();

        objTblCelRenLbl.setHorizontalAlignment(
                javax.swing.JLabel.RIGHT
        );

        objTblCelRenLbl.setTipoFormato(
                objTblCelRenLbl.INT_FOR_NUM
        );

        objTblCelRenLbl.setFormatoNumerico(
                objParSis.getFormatoNumero(),
                false,
                true
        );

        tbcSumReservaWeb.setCellRenderer(
                objTblCelRenLbl
        );

        objTblMod.addColumn(
                tblDat,
                tbcSumReservaWeb
        );

        mapColumnaDato.put(
                "TOTAL|nd_resWEB",
                intColSumReservaWeb
        );

        objGrpSumReserva.add(
                tblDat.getColumnModel().getColumn(
                        intColSumReservaWeb
                )
        );


        /*==========================================================
          TOTAL RESERVA GP
        ==========================================================*/
        int intColSumReservaGP =
                objTblMod.getColumnCount();

        javax.swing.table.TableColumn tbcSumReservaGP =
                new javax.swing.table.TableColumn(
                        intColSumReservaGP
                );

        tbcSumReservaGP.setHeaderValue("GP");
        tbcSumReservaGP.setPreferredWidth(75);
        tbcSumReservaGP.setResizable(false);

        objTblCelRenLbl =
                new ZafTblCelRenLbl();

        objTblCelRenLbl.setHorizontalAlignment(
                javax.swing.JLabel.RIGHT
        );

        objTblCelRenLbl.setTipoFormato(
                objTblCelRenLbl.INT_FOR_NUM
        );

        objTblCelRenLbl.setFormatoNumerico(
                objParSis.getFormatoNumero(),
                false,
                true
        );

        tbcSumReservaGP.setCellRenderer(
                objTblCelRenLbl
        );

        objTblMod.addColumn(
                tblDat,
                tbcSumReservaGP
        );

        mapColumnaDato.put(
                "TOTAL|nd_resGP",
                intColSumReservaGP
        );

        objGrpSumReserva.add(
                tblDat.getColumnModel().getColumn(
                        intColSumReservaGP
                )
        );


        /*==========================================================
          TOTAL DISPONIBLE
        ==========================================================*/
        ZafTblHeaColGrp objGrpSumDisponible =
                new ZafTblHeaColGrp(
                        "DISPONIBLE"
                );

        objGrpSumDisponible.setHeight(16);


        int intColSumDisponible =
                objTblMod.getColumnCount();

        javax.swing.table.TableColumn tbcSumDisponible =
                new javax.swing.table.TableColumn(
                        intColSumDisponible
                );

        tbcSumDisponible.setHeaderValue("");
        tbcSumDisponible.setPreferredWidth(75);
        tbcSumDisponible.setResizable(false);

        objTblCelRenLbl =
                new ZafTblCelRenLbl();

        objTblCelRenLbl.setHorizontalAlignment(
                javax.swing.JLabel.RIGHT
        );

        objTblCelRenLbl.setTipoFormato(
                objTblCelRenLbl.INT_FOR_NUM
        );

        objTblCelRenLbl.setFormatoNumerico(
                objParSis.getFormatoNumero(),
                false,
                true
        );

        tbcSumDisponible.setCellRenderer(
                objTblCelRenLbl
        );

        objTblMod.addColumn(
                tblDat,
                tbcSumDisponible
        );

        mapColumnaDato.put(
                "TOTAL|nd_candis",
                intColSumDisponible
        );

        objGrpSumDisponible.add(
                tblDat.getColumnModel().getColumn(
                        intColSumDisponible
                )
        );


        /*==========================================================
          TOTAL TRANSITO
        ==========================================================*/
        ZafTblHeaColGrp objGrpSumTransito = null;

        if(!arrTiposTransito.isEmpty())
        {
            objGrpSumTransito =
                    new ZafTblHeaColGrp(
                            "TRÁNSITO"
                    );

            objGrpSumTransito.setHeight(16);


            for(String strTipo : arrTiposTransito)
            {
                int intColSumTransito =
                        objTblMod.getColumnCount();

                javax.swing.table.TableColumn tbcSumTransito =
                        new javax.swing.table.TableColumn(
                                intColSumTransito
                        );

                tbcSumTransito.setHeaderValue(
                        formatearNombreMovimiento(
                                strTipo
                        )
                );

                tbcSumTransito.setPreferredWidth(75);
                tbcSumTransito.setResizable(false);

                objTblCelRenLbl =
                        new ZafTblCelRenLbl();

                objTblCelRenLbl.setHorizontalAlignment(
                        javax.swing.JLabel.RIGHT
                );

                objTblCelRenLbl.setTipoFormato(
                        objTblCelRenLbl.INT_FOR_NUM
                );

                objTblCelRenLbl.setFormatoNumerico(
                        objParSis.getFormatoNumero(),
                        false,
                        true
                );

                tbcSumTransito.setCellRenderer(
                        objTblCelRenLbl
                );

                objTblMod.addColumn(
                        tblDat,
                        tbcSumTransito
                );

                mapColumnaDato.put(
                        "TOTAL|" + strTipo,
                        intColSumTransito
                );

                objGrpSumTransito.add(
                        tblDat.getColumnModel().getColumn(
                                intColSumTransito
                        )
                );
            }
        }


        /*==========================================================
          ARMAR SUMATORIA TOTAL
        ==========================================================*/
        objGrpSumatoria.add(
                objGrpSumExistencia
        );

        objGrpSumatoria.add(
                objGrpSumReserva
        );

        objGrpSumatoria.add(
                objGrpSumDisponible
        );

        if(objGrpSumTransito != null)
        {
            objGrpSumatoria.add(
                    objGrpSumTransito
            );
        }


        objTblHeaGrp.addColumnGroup(
                objGrpSumatoria
        );


        objTblCelRenLbl = null;


        /*==========================================================
          INDICE FINAL
        ==========================================================*/
        intColStkFalSobFin =
                objTblMod.getColumnCount();


        /*==========================================================
          ACTUALIZAR HEADER
        ==========================================================*/
        objTblHeaGrp.setColumnMargin();

        tblDat.getTableHeader().revalidate();
        tblDat.getTableHeader().repaint();

        tblDat.revalidate();
        tblDat.repaint();
    }
    catch(Exception e)
    {
        blnRes = false;

        objUti.mostrarMsgErr_F1(
                this,
                e
        );
    }

    return blnRes;
}

private void configurarColumnaNumericaStock(int intCol)
{
    javax.swing.table.TableColumn tbc =
            tblDat.getColumnModel().getColumn(intCol);

    tbc.setPreferredWidth(85);

    ZafTblCelRenLbl objRen = new ZafTblCelRenLbl();

    objRen.setHorizontalAlignment(
            javax.swing.JLabel.RIGHT
    );

    objRen.setTipoFormato(
            objRen.INT_FOR_NUM
    );

    objRen.setFormatoNumerico(
            objParSis.getFormatoNumero(),
            false,
            true
    );

    tbc.setCellRenderer(objRen);
}

private String formatearNombreMovimiento(String strMovimiento)
{
    if(strMovimiento == null)
    {
        return "";
    }

    String strNom =
            strMovimiento
            .trim()
            .replace("_", " ");

    if(strNom.length()>0)
    {
        strNom =
                strNom.substring(0,1).toUpperCase()
                +
                strNom.substring(1).toLowerCase();
    }

    return strNom;
}

private void mostrarRangosBodegas()
{
    try
    {
        for(String strBodega : arrBodegasConsulta)
        {
            Integer intColIni =
                    mapColumnaDato.get(
                            strBodega + "|existencia"
                    );

            int intColFin;

            if(arrTiposTransito.size()>0)
            {
                String strUltTipo =
                        arrTiposTransito.get(
                                arrTiposTransito.size()-1
                        );

                intColFin =
                        mapColumnaDato.get(
                                strBodega + "|" + strUltTipo
                        );
            }
            else
            {
                intColFin =
                        mapColumnaDato.get(
                                strBodega + "|nd_resGP"
                        );
            }


            System.out.println(
                    "Bodega: " + strBodega
                    + " | Desde columna: " + intColIni
                    + " | Hasta columna: " + intColFin
            );
        }
    }
    catch(Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}



private boolean cargarDatosItemBodega(java.sql.ResultSet rstLoc)
{
    boolean blnRes = true;

    try
    {
        mapDatosItemBodega.clear();
        mapDatosGeneralesItem.clear();


        while(rstLoc.next())
        {
            if(!blnCon)
            {
                break;
            }


            /*======================================================
              ITEM
            ======================================================*/
            String strItem =
                    rstLoc.getString("tx_codalt");


            if(strItem == null)
            {
                continue;
            }


            strItem = strItem.trim();


            /*======================================================
              BODEGA
            ======================================================*/
            String strBodega =
                    rstLoc.getString("BODEGA");


            if(strBodega == null)
            {
                continue;
            }


            strBodega = strBodega.trim();


            /*======================================================
              CLAVE ITEM + BODEGA
            ======================================================*/
            String strClave =
                    strItem
                    + "|"
                    + strBodega;


            /*======================================================
              DATOS GENERALES DEL ITEM

              SOLO SE GUARDA UNA VEZ POR ITEM.
            ======================================================*/
            if(!mapDatosGeneralesItem.containsKey(strItem))
            {
                java.util.HashMap<String, String> mapItem =
                        new java.util.HashMap<String, String>();


                String strCoItem =
                        rstLoc.getString("co_item");

                String strCodAlt2 =
                        rstLoc.getString("tx_codalt2");

                String strNomItm =
                        rstLoc.getString("tx_nomitm");

                String strDesCor =
                        rstLoc.getString("tx_descor");


                mapItem.put(
                        "co_item",
                        strCoItem == null
                        ? ""
                        : strCoItem.trim()
                );


                mapItem.put(
                        "tx_codalt2",
                        strCodAlt2 == null
                        ? ""
                        : strCodAlt2.trim()
                );


                mapItem.put(
                        "tx_nomitm",
                        strNomItm == null
                        ? ""
                        : strNomItm.trim()
                );


                mapItem.put(
                        "tx_descor",
                        strDesCor == null
                        ? ""
                        : strDesCor.trim()
                );


                mapDatosGeneralesItem.put(
                        strItem,
                        mapItem
                );
            }


            /*======================================================
              DATOS ITEM + BODEGA
            ======================================================*/
            java.util.HashMap<String, BigDecimal> mapValores =
                    new java.util.HashMap<String, BigDecimal>();


            BigDecimal bdeExistencia =
                    rstLoc.getBigDecimal(
                            "existencia"
                    );


            BigDecimal bdeDisponible =
                    rstLoc.getBigDecimal(
                            "nd_candis"
                    );


            BigDecimal bdeReservaWeb =
                    rstLoc.getBigDecimal(
                            "nd_resWEB"
                    );


            BigDecimal bdeReservaGP =
                    rstLoc.getBigDecimal(
                            "nd_resGP"
                    );


            /*======================================================
              EXISTENCIA
            ======================================================*/
            mapValores.put(
                    "existencia",
                    bdeExistencia == null
                    ? BigDecimal.ZERO
                    : bdeExistencia
            );


            /*======================================================
              DISPONIBLE
            ======================================================*/
            mapValores.put(
                    "nd_candis",
                    bdeDisponible == null
                    ? BigDecimal.ZERO
                    : bdeDisponible
            );


            /*======================================================
              RESERVA WEB
            ======================================================*/
            mapValores.put(
                    "nd_resWEB",
                    bdeReservaWeb == null
                    ? BigDecimal.ZERO
                    : bdeReservaWeb
            );


            /*======================================================
              RESERVA GP
            ======================================================*/
            mapValores.put(
                    "nd_resGP",
                    bdeReservaGP == null
                    ? BigDecimal.ZERO
                    : bdeReservaGP
            );


            /*======================================================
              TRANSITO DINAMICO

              reposicion
              transferencia
              compra
              etc.
            ======================================================*/
            for(String strTipo : arrTiposTransito)
            {
                BigDecimal bdeValor =
                        rstLoc.getBigDecimal(
                                strTipo
                        );


                mapValores.put(
                        strTipo,
                        bdeValor == null
                        ? BigDecimal.ZERO
                        : bdeValor
                );
            }


            /*======================================================
              GUARDAR ITEM + BODEGA
            ======================================================*/
            mapDatosItemBodega.put(
                    strClave,
                    mapValores
            );


            /*======================================================
              DEBUG
            ======================================================*/
            System.out.println(
                    "ITEM: "
                    + strItem
                    + " | BODEGA: "
                    + strBodega
                    + " | STOCK: "
                    + mapValores.get("existencia")
                    + " | DISP: "
                    + mapValores.get("nd_candis")
            );
        }


        /*==========================================================
          DEBUG FINAL
        ==========================================================*/
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "TOTAL ITEMS: "
                + mapDatosGeneralesItem.size()
        );

        System.out.println(
                "TOTAL ITEM/BODEGA: "
                + mapDatosItemBodega.size()
        );

        System.out.println(
                "=========================================="
        );
    }
    catch(Exception e)
    {
        objUti.mostrarMsgErr_F1(
                this,
                e
        );

        blnRes = false;
    }


    return blnRes;
}

private boolean cargarFilasItems()
{
    boolean blnRes = true;

    try
    {
        vecDat.clear();


        for(String strItem : mapDatosGeneralesItem.keySet())
        {
            if(!blnCon)
            {
                break;
            }


            java.util.HashMap<String, String> mapItem =
                    mapDatosGeneralesItem.get(
                            strItem
                    );


            vecReg = new Vector();


            /*======================================================
              COLUMNAS FIJAS
            ======================================================*/
            vecReg.add(
                    INT_TBL_DAT_LIN,
                    ""
            );

            vecReg.add(
                    INT_TBL_DAT_COD_MAE,
                    ""
            );

            vecReg.add(
                    INT_TBL_DAT_COD_SIS,
                    mapItem.get("co_item")
            );

            vecReg.add(
                    INT_TBL_DAT_COD_ALT,
                    strItem
            );

            vecReg.add(
                    INT_TBL_DAT_COD_ALT_DOS,
                    mapItem.get("tx_codalt2")
            );

            vecReg.add(
                    INT_TBL_DAT_NOM_ITM,
                    mapItem.get("tx_nomitm")
            );

            vecReg.add(
                    INT_TBL_DAT_DEC_UNI,
                    mapItem.get("tx_descor")
            );


            /*======================================================
              INICIALIZAR COLUMNAS DINAMICAS
            ======================================================*/
            while(vecReg.size() <
                  objTblMod.getColumnCount())
            {
                vecReg.add(
                        BigDecimal.ZERO
                );
            }


            /*======================================================
              ACUMULADORES
            ======================================================*/
            BigDecimal bdeTotExistencia =
                    BigDecimal.ZERO;

            BigDecimal bdeTotReservaWeb =
                    BigDecimal.ZERO;

            BigDecimal bdeTotReservaGP =
                    BigDecimal.ZERO;

            BigDecimal bdeTotDisponible =
                    BigDecimal.ZERO;


            java.util.HashMap<String, BigDecimal> mapTotTransito =
                    new java.util.HashMap<String, BigDecimal>();


            for(String strTipo : arrTiposTransito)
            {
                mapTotTransito.put(
                        strTipo,
                        BigDecimal.ZERO
                );
            }


            /*======================================================
              RECORRER BODEGAS
            ======================================================*/
            for(String strBodega : arrBodegasConsulta)
            {
                String strClave =
                        strItem
                        + "|"
                        + strBodega;


                java.util.HashMap<String, BigDecimal> mapValores =
                        mapDatosItemBodega.get(
                                strClave
                        );


                if(mapValores == null)
                {
                    continue;
                }


                /*==================================================
                  EXISTENCIA
                ==================================================*/
                BigDecimal bdeExistencia =
                        mapValores.get(
                                "existencia"
                        );

                if(bdeExistencia == null)
                {
                    bdeExistencia =
                            BigDecimal.ZERO;
                }


                Integer intCol =
                        mapColumnaDato.get(
                                strBodega
                                + "|existencia"
                        );


                if(intCol != null)
                {
                    vecReg.set(
                            intCol,
                            bdeExistencia
                    );
                }


                bdeTotExistencia =
                        bdeTotExistencia.add(
                                bdeExistencia
                        );


                /*==================================================
                  RESERVA WEB
                ==================================================*/
                BigDecimal bdeReservaWeb =
                        mapValores.get(
                                "nd_resWEB"
                        );

                if(bdeReservaWeb == null)
                {
                    bdeReservaWeb =
                            BigDecimal.ZERO;
                }


                intCol =
                        mapColumnaDato.get(
                                strBodega
                                + "|nd_resWEB"
                        );


                if(intCol != null)
                {
                    vecReg.set(
                            intCol,
                            bdeReservaWeb
                    );
                }


                bdeTotReservaWeb =
                        bdeTotReservaWeb.add(
                                bdeReservaWeb
                        );


                /*==================================================
                  RESERVA GP
                ==================================================*/
                BigDecimal bdeReservaGP =
                        mapValores.get(
                                "nd_resGP"
                        );

                if(bdeReservaGP == null)
                {
                    bdeReservaGP =
                            BigDecimal.ZERO;
                }


                intCol =
                        mapColumnaDato.get(
                                strBodega
                                + "|nd_resGP"
                        );


                if(intCol != null)
                {
                    vecReg.set(
                            intCol,
                            bdeReservaGP
                    );
                }


                bdeTotReservaGP =
                        bdeTotReservaGP.add(
                                bdeReservaGP
                        );


                /*==================================================
                  DISPONIBLE
                ==================================================*/
                BigDecimal bdeDisponible =
                        mapValores.get(
                                "nd_candis"
                        );

                if(bdeDisponible == null)
                {
                    bdeDisponible =
                            BigDecimal.ZERO;
                }


                intCol =
                        mapColumnaDato.get(
                                strBodega
                                + "|nd_candis"
                        );


                if(intCol != null)
                {
                    vecReg.set(
                            intCol,
                            bdeDisponible
                    );
                }


                bdeTotDisponible =
                        bdeTotDisponible.add(
                                bdeDisponible
                        );


                /*==================================================
                  TRANSITO DINAMICO
                ==================================================*/
                for(String strTipo : arrTiposTransito)
                {
                    BigDecimal bdeValor =
                            mapValores.get(
                                    strTipo
                            );


                    if(bdeValor == null)
                    {
                        bdeValor =
                                BigDecimal.ZERO;
                    }


                    intCol =
                            mapColumnaDato.get(
                                    strBodega
                                    + "|"
                                    + strTipo
                            );


                    if(intCol != null)
                    {
                        vecReg.set(
                                intCol,
                                bdeValor
                        );
                    }


                    BigDecimal bdeTotalTipo =
                            mapTotTransito.get(
                                    strTipo
                            );


                    if(bdeTotalTipo == null)
                    {
                        bdeTotalTipo =
                                BigDecimal.ZERO;
                    }


                    mapTotTransito.put(
                            strTipo,
                            bdeTotalTipo.add(
                                    bdeValor
                            )
                    );
                }
            }


            /*======================================================
              TOTAL EXISTENCIA
            ======================================================*/
            Integer intColTotal =
                    mapColumnaDato.get(
                            "TOTAL|existencia"
                    );


            if(intColTotal != null)
            {
                vecReg.set(
                        intColTotal,
                        bdeTotExistencia
                );
            }


            /*======================================================
              TOTAL RESERVA WEB
            ======================================================*/
            intColTotal =
                    mapColumnaDato.get(
                            "TOTAL|nd_resWEB"
                    );


            if(intColTotal != null)
            {
                vecReg.set(
                        intColTotal,
                        bdeTotReservaWeb
                );
            }


            /*======================================================
              TOTAL RESERVA GP
            ======================================================*/
            intColTotal =
                    mapColumnaDato.get(
                            "TOTAL|nd_resGP"
                    );


            if(intColTotal != null)
            {
                vecReg.set(
                        intColTotal,
                        bdeTotReservaGP
                );
            }


            /*======================================================
              TOTAL DISPONIBLE
            ======================================================*/
            intColTotal =
                    mapColumnaDato.get(
                            "TOTAL|nd_candis"
                    );


            if(intColTotal != null)
            {
                vecReg.set(
                        intColTotal,
                        bdeTotDisponible
                );
            }


            /*======================================================
              TOTAL TRANSITO
            ======================================================*/
            for(String strTipo : arrTiposTransito)
            {
                intColTotal =
                        mapColumnaDato.get(
                                "TOTAL|"
                                + strTipo
                        );


                if(intColTotal != null)
                {
                    BigDecimal bdeTotalTipo =
                            mapTotTransito.get(
                                    strTipo
                            );


                    vecReg.set(
                            intColTotal,
                            bdeTotalTipo == null
                            ? BigDecimal.ZERO
                            : bdeTotalTipo
                    );
                }
            }


            /*======================================================
              AGREGAR FILA
            ======================================================*/
            vecDat.add(
                    vecReg
            );
        }
    }
    catch(Exception e)
    {
        objUti.mostrarMsgErr_F1(
                this,
                e
        );

        blnRes = false;
    }


    return blnRes;
}

private boolean cargarReg()
{
    boolean blnRes = true;

    try
    {
        /*
         * Evitar que Swing intente pintar la tabla mientras
         * eliminamos y volvemos a crear las columnas dinámicas.
         */

        conGP = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if(conGP != null)
        {
            /*
             * Filtro: Sólo el stock, faltantes y sobrantes totales.
             */
            if(optTod.isSelected())
            {
                if(eliminaTodasColumnasAdicionadas())
                {
                    if(cargarDetReg())
                    {
                        if(chkSolStk.isSelected())
                        {
                            if(quitaStockCero())
                            {
                            }
                        }
                    }
                }
            }
            else
            {
                if(eliminaTodasColumnasAdicionadas())
                {
                    if(cargarDetReg())
                    {
                        if(chkSolStk.isSelected())
                        {
                            if(quitaStockCero())
                            {
                            }
                        }
                    }                    
                    
                }                
            }

            /*
             * Filtro: Sólo las bodegas del grupo
             * (Incluir en el reporte las bodegas relacionadas).
             */
////////////            else if(optBodGrpIncRel.isSelected())
////////////            {
////////////                if(agregarFilasBodegasEmpresas())
////////////                {
////////////                    if(eliminaTodasColumnasAdicionadas())
////////////                    {
////////////                        if(agregarColTblDatGrp())
////////////                        {
////////////                            if(agregarColTblDat())
////////////                            {
////////////                                if(agregarColSumStkDis())
////////////                                {
////////////                                    if(cargarDetReg())
////////////                                    {
////////////                                        if(eliminarFilasBodegasEmpresas())
////////////                                        {
////////////                                            if(calcularTotalStkDis())
////////////                                            {
////////////                                                if(chkSolStk.isSelected())
////////////                                                {
////////////                                                    if(quitaStockCero())
////////////                                                    {
////////////                                                    }
////////////                                                }
////////////                                            }
////////////                                        }
////////////                                    }
////////////                                }
////////////                            }
////////////                        }
////////////                    }
////////////                }
////////////            }


            /*
             * Todos los otros filtros:
             * Trabaja con las bodegas seleccionadas directamente
             * en la tabla de bodegas.
             */
////////            else
////////            {
////////                if(eliminaTodasColumnasAdicionadas())
////////                {
////////                    if(agregarColTblDatGrp())
////////                    {
////////                        if(agregarColTblDat())
////////                        {
////////                            if(agregarColSumStkDis())
////////                            {
////////                                if(cargarDetReg())
////////                                {
////////                                    if(calcularTotalStkDis())
////////                                    {
////////                                        if(chkSolStk.isSelected())
////////                                        {
////////                                            if(quitaStockCero())
////////                                            {
////////                                            }
////////                                        }
////////                                    }
////////                                }
////////                            }
////////                        }
////////                    }
////////                }
////////            }


            conGP.close();
            conGP = null;
        }
    }
    catch(java.sql.SQLException e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch(Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    finally
    {
        try
        {
            if(conGP != null)
            {
                conGP.close();
                conGP = null;
            }
        }
        catch(Exception e)
        {
        }


        /*
         * Mostrar nuevamente la tabla cuando toda la estructura
         * de columnas ya esté terminada.
         */

        tblDat.revalidate();
        tblDat.repaint();

        if(tblDat.getTableHeader() != null)
        {
            tblDat.getTableHeader().revalidate();
            tblDat.getTableHeader().repaint();
        }
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
    boolean blnRes = true;

    java.sql.CallableStatement cal = null;
    java.sql.ResultSet rstLoc = null;

    String strSql = "";
    String strBodegasSP = "";

    String strItemExacto = null;
    String strDesde = null;
    String strHasta = null;
    String strTerminal = null;

    int intCodRes = -1;

    try
    {
        pgrSis.setIndeterminate(true);
        butCon.setText("Detener");
        lblMsgSis.setText("Obteniendo datos...");
        
        int intConDisponible = 0;

        if(chkSolStk.isSelected())
        {
            intConDisponible = 1;
        }        


        /*==========================================================
          1. FILTROS DE ITEMS

          optTod seleccionado:
              Todos los filtros quedan NULL.
              El SP consulta todos los items.

          optTod NO seleccionado:
              Se aplican los criterios ingresados.
        ==========================================================*/
        if(!optTod.isSelected())
        {
            /*======================================================
              ITEM EXACTO
              Ejemplo: 002-000046-S
            ======================================================*/
            if(txtCodAlt.getText().trim().length() > 0)
            {
                strItemExacto =
                        txtCodAlt.getText().trim();
            }


            /*======================================================
              DESDE
              Primeros 3 caracteres del ITEMNMBR.
            ======================================================*/
            if(txtCodAltDes.getText().trim().length() > 0)
            {
                strDesde =
                        txtCodAltDes.getText().trim();
            }


            /*======================================================
              HASTA
              Primeros 3 caracteres del ITEMNMBR.
            ======================================================*/
            if(txtCodAltHas.getText().trim().length() > 0)
            {
                strHasta =
                        txtCodAltHas.getText().trim();
            }


            /*======================================================
              TERMINAN CON
              Último carácter del ITEMNMBR.
            ======================================================*/
            if(txtCodAltItmTer.getText().trim().length() > 0)
            {
                strTerminal =
                        txtCodAltItmTer.getText().trim();
            }
        }


        /*==========================================================
          2. OBTENER BODEGAS SELECCIONADAS
        ==========================================================*/
        arrBodegasConsulta.clear();

        int intNumFilTblBod =
                objTblModBod.getRowCountTrue();

        for(int i = 0; i < intNumFilTblBod; i++)
        {
            if(objTblModBod.isChecked(i, INT_TBL_BOD_CHK))
            {
                Object objBodega =
                        objTblModBod.getValueAt(
                                i,
                                INT_TBL_BOD_COD_BOD
                        );

                if(objBodega != null)
                {
                    String strBodega =
                            objBodega.toString().trim();

                    if(!strBodega.equals(""))
                    {
                        arrBodegasConsulta.add(
                                strBodega
                        );

                        if(!strBodegasSP.equals(""))
                        {
                            strBodegasSP += ",";
                        }

                        strBodegasSP +=
                                strBodega;
                    }
                }
            }
        }


        /*==========================================================
          3. VALIDAR BODEGAS
        ==========================================================*/
        if(strBodegasSP.trim().equals(""))
        {
            lblMsgSis.setText(
                    "No se han seleccionado bodegas."
            );

            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);

            return true;
        }


        /*==========================================================
          DEBUG
        ==========================================================*/
        System.out.println(
                "=========================================="
        );

        System.out.println(
                "PARAMETROS SP"
        );

        System.out.println(
                "ItemExacto: " + strItemExacto
        );

        System.out.println(
                "Desde: " + strDesde
        );

        System.out.println(
                "Hasta: " + strHasta
        );

        System.out.println(
                "Terminal: " + strTerminal
        );

        System.out.println(
                "Bodegas: " + strBodegasSP
        );

        System.out.println(
                "=========================================="
        );


        /*==========================================================
          4. LIMPIAR DATOS
        ==========================================================*/
        vecDat.clear();

        arrTiposTransito.clear();

        mapColumnaDato.clear();

        mapDatosItemBodega.clear();

        mapDatosGeneralesItem.clear();

        lblMsgSis.setText(
                "Consultando disponibilidad..."
        );


        /*==========================================================
          5. EJECUTAR SP
        ==========================================================*/
        if(conGP != null)
        {
            strSql =
                    "{call sp_Consulta_Disponible_Items_Bodegas(?,?,?,?,?,?,?)}";

            cal =
                    conGP.prepareCall(
                            strSql
                    );


            cal.setString(
                    "ItemExacto",
                    strItemExacto
            );

            cal.setString(
                    "Desde",
                    strDesde
            );

            cal.setString(
                    "Hasta",
                    strHasta
            );

            cal.setString(
                    "Terminal",
                    strTerminal
            );

            cal.setString(
                    "Bodegas",
                    strBodegasSP
            );

            cal.registerOutParameter(
                    "intRes",
                    java.sql.Types.INTEGER
            );
            cal.setInt(
                    "ConDisponible",
                    intConDisponible
            );           


            boolean blnTieneResultado =
                    cal.execute();


            /*======================================================
              6. PRIMER RESULTSET
            ======================================================*/
            if(blnTieneResultado)
            {
                rstLoc =
                        cal.getResultSet();


                /*==================================================
                  DETECTAR TIPOS DE TRANSITO
                ==================================================*/
                if(!obtenerTiposTransito(rstLoc))
                {
                    return false;
                }


                System.out.println(
                        "===== TIPOS DE TRANSITO ====="
                );

                for(String strTipo : arrTiposTransito)
                {
                    System.out.println(
                            strTipo
                    );
                }


                /*==================================================
                  CREAR COLUMNAS Y HEADER NUEVO
                ==================================================*/
                if(!agregarColumnasStockTransito())
                {
                    return false;
                }
// Habilitar redimensionamiento de columnas dinámicas.
habilitarRedimensionamientoColumnas();                


                /*==================================================
                  CARGAR DATOS ITEM + BODEGA
                ==================================================*/
                if(!cargarDatosItemBodega(rstLoc))
                {
                    return false;
                }


                rstLoc.close();
                rstLoc = null;
            }


            /*======================================================
              7. CONSTRUIR FILAS
            ======================================================*/
            if(!cargarFilasItems())
            {
                return false;
            }


            /*======================================================
              8. TERMINAR RESULTSETS ANTES DEL OUTPUT PARAMETER
            ======================================================*/
            while(cal.getMoreResults() ||
                  cal.getUpdateCount() != -1)
            {
                java.sql.ResultSet rstAux =
                        cal.getResultSet();

                if(rstAux != null)
                {
                    rstAux.close();
                    rstAux = null;
                }
            }


            intCodRes =
                    cal.getInt("intRes");


            System.out.println(
                    "Resultado SP: " + intCodRes
            );


            if(intCodRes == 1)
            {
                blnRes = false;

                System.out.println(
                        "Falla sp_Consulta_Disponible_Items_Bodegas"
                );
            }


            cal.close();
            cal = null;
        }


        /*==========================================================
          9. CARGAR DATOS EN EL MODELO
        ==========================================================*/
        objTblMod.setData(
                vecDat
        );


        /*==========================================================
          10. ACTUALIZAR HEADER MULTINIVEL
        ==========================================================*/
        if(tblDat.getTableHeader() != null)
        {
            tblDat.getTableHeader().revalidate();
            tblDat.getTableHeader().repaint();
        }


        /*==========================================================
          FORZAR AL SCROLLPANE A RECALCULAR LA ALTURA DEL HEADER
        ==========================================================*/
        java.awt.Container contenedor =
                tblDat.getParent();

        if(contenedor instanceof javax.swing.JViewport)
        {
            javax.swing.JViewport viewport =
                    (javax.swing.JViewport)contenedor;

            java.awt.Container contenedorScroll =
                    viewport.getParent();

            if(contenedorScroll instanceof javax.swing.JScrollPane)
            {
                javax.swing.JScrollPane scrollPane =
                        (javax.swing.JScrollPane)contenedorScroll;

                scrollPane.revalidate();
                scrollPane.repaint();
            }
        }


        tblDat.revalidate();
        tblDat.repaint();


        vecDat.clear();


        /*==========================================================
          11. RESULTADO
        ==========================================================*/
        if(blnCon)
        {
            lblMsgSis.setText(
                    "Se encontraron "
                    + tblDat.getRowCount()
                    + " registros."
            );
        }
        else
        {
            lblMsgSis.setText(
                    "Interrupción del usuario. Sólo se procesaron "
                    + tblDat.getRowCount()
                    + " registros."
            );
        }


        butCon.setText(
                "Consultar"
        );

        pgrSis.setIndeterminate(
                false
        );
    }
    catch(java.sql.SQLFeatureNotSupportedException e)
    {
        System.out.println(
                "SQLFeatureNotSupportedException: "
                + e.toString()
        );

        blnRes = false;

        objUti.mostrarMsgErr_F1(
                this,
                e
        );
    }
    catch(java.sql.SQLException e)
    {
        System.out.println(
                "SQLException: "
                + e.toString()
        );

        blnRes = false;

        objUti.mostrarMsgErr_F1(
                this,
                e
        );
    }
    catch(Exception e)
    {
        System.out.println(
                "Exception: "
                + e.toString()
        );

        blnRes = false;

        objUti.mostrarMsgErr_F1(
                this,
                e
        );
    }
    finally
    {
        try
        {
            if(rstLoc != null)
            {
                rstLoc.close();
                rstLoc = null;
            }

            if(cal != null)
            {
                cal.close();
                cal = null;
            }
        }
        catch(Exception e)
        {
        }

        butCon.setText(
                "Consultar"
        );

        pgrSis.setIndeterminate(
                false
        );
    }

    return blnRes;
}
    /**
     * Esta función calcula el total de stock de las filas especificadas.
     * @return true: Si se pudo calcular el total de las filas especificadas sin
     * que ocurra ningún error. <BR>false: En el caso contrario.
     */
//////////    private boolean calcularTotalStkDis()
//////////    {
//////////        System.out.println("calcularTotalStkDis!!!");
//////////        boolean blnRes=true;
//////////        BigDecimal bdeVal=new BigDecimal("0");
//////////        BigDecimal bdeSumStk=new BigDecimal("0");
//////////        BigDecimal bdeSumDis=new BigDecimal("0");
//////////        int intTotColGrp, intTotColEmp;
//////////        int i, j;
//////////        try
//////////        {
//////////            System.gc();
//////////            pgrSis.setIndeterminate(true);
//////////            butCon.setText("Detener");
//////////            lblMsgSis.setText("Obteniendo datos...");
//////////            
//////////            if(!optStkFalSob.isSelected())
//////////            {
//////////                if ( (optBodTod.isSelected() && intColGrpAdi>0) /*|| optBodGrp.isSelected() || optBodGrpIncRel.isSelected()*/ )
//////////                {
//////////                    for(i=0; i<objTblMod.getRowCountTrue(); i++)
//////////                    {
//////////                        intTotColGrp=INT_TBL_DAT_CDI_STK+(intColGrpAdi*INT_NUM_COL_ADI_TBL_DATGRP);
//////////                        for (j=INT_TBL_DAT_CDI_STK; j<intTotColGrp; j++)
//////////                        {
//////////                            if((j-1)%INT_NUM_COL_ADI_TBL_DATGRP==0)  //Stock
//////////                            {
//////////                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//////////                                 bdeSumStk=bdeSumStk.add(bdeVal);
//////////                            }
//////////                            else if( (j+1) % INT_NUM_COL_ADI_TBL_DATGRP==0) //Disponible
//////////                            {
//////////                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//////////                                 bdeSumDis=bdeSumDis.add(bdeVal);
//////////                            } 
//////////                        }
//////////                        objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);
//////////                        objTblMod.setValueAt(bdeSumDis, i, intColSumStkIni+1);
//////////                        bdeSumStk=new BigDecimal("0");
//////////                        bdeSumDis=new BigDecimal("0");
//////////                    }
//////////                }
//////////                else if( (optBodTod.isSelected() && intColGrpAdi==0) /*|| optBodEmp.isSelected()*/ || (objParSis.getCodigoEmpresa()!= objParSis.getCodigoEmpresaGrupo()) )
//////////                {
//////////                    intTotColEmp=INT_TBL_DAT_CDI_STK+(intColEmpAdi*INT_NUM_COL_ADI_TBL_DATEMP);
//////////                    for(i=0; i<objTblMod.getRowCountTrue(); i++)
//////////                    {
//////////                        for (j=INT_TBL_DAT_CDI_STK; j<intTotColEmp; j++)
//////////                        {
//////////                            if(j%2==0)  //Disponible
//////////                            {
//////////                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//////////                                 bdeSumDis=bdeSumDis.add(bdeVal);
//////////                            }
//////////                            else //Stock
//////////                            {
//////////                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//////////                                 bdeSumStk=bdeSumStk.add(bdeVal);
//////////                            }
//////////                        }
//////////                        objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);
//////////                        objTblMod.setValueAt(bdeSumDis, i, intColSumStkIni+1);
//////////                        bdeSumStk=new BigDecimal("0");
//////////                        bdeSumDis=new BigDecimal("0");
//////////                    }
//////////                }
//////////            }
//////////      
//////////            if (blnCon)
//////////                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
//////////            else
//////////                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
//////////            butCon.setText("Consultar");
//////////            pgrSis.setIndeterminate(false);
//////////            
//////////        }
//////////        catch(Exception e)
//////////        {
//////////            objUti.mostrarMsgErr_F1(this, e);
//////////            blnRes=false;
//////////        }
//////////        return blnRes;
//////////    }
    
    
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
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
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT trim(itemnmbr) as co_itm, trim (itemnmbr) as tx_codalt, trim(itemdesc) as tx_nomItm,";
            strSQL+="           trim (prchsuom) as tx_descor, trim(ITMGEDSC) as tx_codalt2";            
            strSQL+=" FROM " + objParSis.getNombreBaseDatosGP() + "..IV00101 ";
            vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
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
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
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
        catch (Exception e)
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
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil, intCodEmpGrpChk;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {

                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);

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
            int intColSel=-1, intColArl=-1;
            int veremos=0;

            int intCol=tblDat.columnAtPoint(evt.getPoint());
            //System.out.println("--------ToolTips----------");
            String strMsg="";
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_FAC_NUM_CDI+INT_TBL_DAT_NUM_TOT_CES;
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
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CDI_STK:
                    intColSel=tblDat.columnAtPoint(evt.getPoint());
                        for(int j=0; j<intColGrpAdi; j++){
                            intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
                            if(intColArl==intColSel){
                                strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
                                veremos++;
                                break;
                            }
                        }

                        if(veremos==0){
                            for(int j=0; j<intColGrpAdi; j++){
                                intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
                                intColArl=(intColArl-1);
                                if(intColArl==intColSel){
                                    strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
                                    break;
                                }
                            }
                        }

                        for(int j=0; j<intColEmpAdi; j++){
                            intColArl=objUti.getIntValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NUM_COL);
                            if(intColArl==intColSel){
                                strMsg="" + objUti.getStringValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NOM_BOD);
                                break;
                            }
                        }

                    
                    


////////////                    if(optStkFalSob.isSelected())
////////////                    {
////////////                        if(INT_TBL_DAT_CDI_STK==intColSel){
////////////                            strMsg="Stock";
////////////                        }
////////////                        if((INT_TBL_DAT_CDI_STK+1)==intColSel){
////////////                            strMsg="Disponible";
////////////                        }
////////////                        if((INT_TBL_DAT_CDI_STK+2)==intColSel){
////////////                            strMsg="Faltantes";
////////////                        }
////////////                        if((INT_TBL_DAT_CDI_STK+3)==intColSel){
////////////                            strMsg="Sobrantes";
////////////                        }
////////////                    }
////////////                    else
////////////                    {
////////////                        for(int j=0; j<intColGrpAdi; j++){
////////////                            intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
////////////                            if(intColArl==intColSel){
////////////                                strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
////////////                                veremos++;
////////////                                break;
////////////                            }
////////////                        }
////////////
////////////                        if(veremos==0){
////////////                            for(int j=0; j<intColGrpAdi; j++){
////////////                                intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
////////////                                intColArl=(intColArl-1);
////////////                                if(intColArl==intColSel){
////////////                                    strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
////////////                                    break;
////////////                                }
////////////                            }
////////////                        }
////////////
////////////                        for(int j=0; j<intColEmpAdi; j++){
////////////                            intColArl=objUti.getIntValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NUM_COL);
////////////                            if(intColArl==intColSel){
////////////                                strMsg="" + objUti.getStringValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NOM_BOD);
////////////                                break;
////////////                            }
////////////                        }
////////////
////////////                    }
                    //strMsg=objTblMod.getColumnName(tblDat.columnAtPoint(evt.getPoint()));
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


    private boolean seleccionaBodegas()
    {
        boolean blnRes=true;
        int intCodEmpGrpChk=-1;
        try
        {
            for(int i=0; i<objTblModBod.getRowCountTrue(); i++)
            {
                intCodEmpGrpChk=Integer.parseInt(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_EMP).toString());
                if(optBodTod.isSelected())
                    objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private boolean agregarFilasBodegasEmpresas()
    {
        boolean blnRes=true;
        String strRstCodEmp="", strRstCodBod="", strRstNomEmp="", strRstNomBod="";
        int intCodEmpGrpChk=-1;
        String strCodBodEmp="";
        int intIni=0;
        int intFilIns=-1;
        try
        {
            for(int i=0; i<objTblModBod.getRowCountTrue(); i++)
            {
                if(objTblModBod.isChecked(i, INT_TBL_BOD_CHK)){
                    intCodEmpGrpChk=Integer.parseInt(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_EMP).toString());
                    if(intCodEmpGrpChk==objParSis.getCodigoEmpresaGrupo()){
                        if(intIni==0){
                            strCodBodEmp="" + objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                            intIni++;
                        }
                        else
                            strCodBodEmp+="," + objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                    }
                }
            }
            //System.out.println("agregarFilasBodegasEmpresas.BodegasAntes: " + objTblModBod.getRowCountTrue());
            if(conGP!=null)
            {
                stm=conGP.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a3.tx_nom AS tx_nomEmp, a1.co_bod, a1.tx_nom AS tx_nomBod";
                strSQL+=" FROM (tbm_bod AS a1 INNER JOIN tbm_emp AS a3 ON a1.co_emp=a3.co_emp)";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+=" WHERE a2.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a2.co_bodGrp IN(" + strCodBodEmp + ") AND a1.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_empGrp, a2.co_bod, a2.co_bodGrp";
                rst=stm.executeQuery(strSQL);
                objTblModBod.setModoOperacion(objTblModBod.INT_TBL_INS);
                while(rst.next())
                {
                    strRstCodEmp=rst.getString("co_emp");
                    strRstCodBod=rst.getString("co_bod");
                    strRstNomEmp=rst.getString("tx_nomEmp");
                    strRstNomBod=rst.getString("tx_nomBod");
                    
                    intFilIns=objTblModBod.getRowCountTrue();
                    objTblModBod.insertRow(intFilIns);
                    objTblModBod.setValueAt(new Boolean(true), intFilIns, INT_TBL_BOD_CHK);
                    objTblModBod.setValueAt(strRstCodEmp, intFilIns, INT_TBL_BOD_COD_EMP);
                    objTblModBod.setValueAt(strRstCodBod, intFilIns, INT_TBL_BOD_COD_BOD);
                    objTblModBod.setValueAt(strRstNomBod, intFilIns, INT_TBL_BOD_NOM_BOD);
                }
            }
        }
        catch(Exception e){        objUti.mostrarMsgErr_F1(this, e);      blnRes=false;       }
        return blnRes;
    }



    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaTodasColumnasAdicionadas()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intColModIni); i--)
            {
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);    blnRes=false;      }
        return blnRes;
    }




    private boolean eliminarFilasBodegasEmpresas()
    {
        boolean blnRes=true;
        String strLin="";
        try
        {
            for (int i=(objTblModBod.getRowCountTrue()-1); i>=0; i--)
            {
                strLin=objTblModBod.getValueAt(i, INT_TBL_BOD_LIN)==null?"":objTblModBod.getValueAt(i, INT_TBL_BOD_LIN).toString();
                if(strLin.equals("I"))
                {
                    objTblModBod.removeRow(i);
                }
            }
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
            objTblModBod.removeEmptyRows();
        }
        catch(Exception e){    objUti.mostrarMsgErr_F1(this, e);        blnRes=false;        }
        return blnRes;
    }

    private boolean quitaStockCero()
    {
        boolean blnRes=true;
        BigDecimal bdeValStk=new BigDecimal("0");
        BigDecimal bdeSumStk=new BigDecimal("0");
        try
        {
            System.out.println("quitaStockCero!!!!!!!!!   "+intColGrpAdi);
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo() && (intColGrpAdi==0) ) //Cuando no se ha seleccionado bodegas de grupo.
            {
                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    for(int j=INT_TBL_DAT_CDI_STK; j<intColSumStkIni; j++)
                    {
                        bdeValStk=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        bdeSumStk=bdeSumStk.add(bdeValStk);
                    }
                    objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);  //Comentado Rose
                    bdeSumStk=new BigDecimal("0");
                }
            }
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bdeValStk=new BigDecimal(objTblMod.getValueAt(i, intColSumStkIni)==null?"0":(objTblMod.getValueAt(i, intColSumStkIni).equals("")?"0":objTblMod.getValueAt(i, intColSumStkIni).toString()));
                if(bdeValStk.compareTo(new BigDecimal("0"))<=0){
                    objTblMod.removeRow(i);
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    


}