package Maestros.ZafMae83; 







/*
 * ZafMae83_02.java
 *
 * Created on 27 de marzo de 2006, 12:15
 */



import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author  jayapata
 */
public class ZafMae83_03 extends javax.swing.JDialog {
    
    
    

    
    
      //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    
    
   // private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatPr, vecCabPr, vecRegPr;    
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    //Variables de la clase.
    private int intCodEmp;                              //Código de la empresa.
    private int intCodLoc;                              //Código del local.
    private int intCodTipDoc;                           //Código del tipo de documento.
    private int intCodMod;                              //Código del Movimiento.
    private String CodAlt;                               // Codigo Alternativo de Item
    private int CodCli; 
    private ZafVenConGP vcoItm, vcoTipDoc, vcoBod;
    private String strCodGru="-1";
    private String strVersion="v 0.03";
    private ZafPerUsr objPerUsr; 
    private java.util.Date datFecDoc;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.   
    private ZafTblPopMnu objTblPopMnuPro;                          //PopupMenu: Establecer PeopuMenú en JTable.       
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblBus objTblBusPro;                                //Editor de búsqueda.    
    private ZafTblFilCab objTblFilCab;
    private ZafTblFilCab objTblFilCabPro;    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblOrd objTblOrdPro, objTblOrdLocPro;                  //JTable de ordenamiento. 
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private String strCodAlt, strCodLetItm, strNomItm;                       //Contenido del campo al obtener el foco.
    private int estado=-1;
    
    private final int INT_TBL_DAT_LIN=0;    
    private final int INT_TBL_DAT_CODIGO=1;
    private final int INT_TBL_DAT_DESC_CORT=2;
    private final int INT_TBL_DAT_DESC_LAR=3;    
    private final int INT_TBL_DAT_FAC=4;    
    private final int INT_TBL_DAT_ESTADO=5;
    private final int INT_TBL_DAT_OB=6;
    
    private final int INT_TBL_DAT_LIN2=0;    
    private final int INT_TBL_DAT_CODIGO2=1;
    private final int INT_TBL_DAT_COD_ITM2=2;
    private final int INT_TBL_DAT_NOM_ITM2=3;
    private final int INT_TBL_DAT_TRESL2=4;  
    
    private ZafTblMod objTblMod;  
    private ZafTblMod objTblModPro;      
    private ZafMouMotAda objMouMotAda;   
    private ZafMouMotAda objMouMotAdaPro;       
    String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla
    JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes    
    final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje 
    private int procesoEjecucion =0;
    
    
    /** Creates new form ZafMae83_02 */
    public ZafMae83_03(java.awt.Frame parent, boolean modal, ZafParSis obj, String bodega) 
    {
        super(parent, modal);
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            configurarTblDatProceso();
            
            obtener_maximo();
            txtCodFactor.setText(String.valueOf(intCodCfg+1));
            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafMae83_02.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butGuardar = new javax.swing.JButton();
        butModificar = new javax.swing.JButton();
        butAnula = new javax.swing.JButton();
        butLimpiar = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panPer = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodFactor = new javax.swing.JTextField();
        txtNomLargo = new javax.swing.JTextField();
        txtNomCorto = new javax.swing.JTextField();
        lblDescCorta = new javax.swing.JLabel();
        lblDescLarga = new javax.swing.JLabel();
        lblFactor1 = new javax.swing.JLabel();
        txtFactor = new javax.swing.JTextField();
        lblFactor = new javax.swing.JLabel();
        txtFactorMod = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObser1 = new javax.swing.JTextArea();
        lblObserva1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatFact = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDatProceso = new javax.swing.JTable();
        butGuardar1 = new javax.swing.JButton();
        butProceso = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        PanFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        PanFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butGuardar.setText("Crear");
        butGuardar.setToolTipText("Cierra la ventana.");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        butModificar.setText("Modificar");
        butModificar.setToolTipText("Modifca los factores");
        butModificar.setPreferredSize(new java.awt.Dimension(92, 25));
        butModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModificarActionPerformed(evt);
            }
        });
        panBot.add(butModificar);

        butAnula.setText("Anular");
        butAnula.setToolTipText("Anula");
        butAnula.setPreferredSize(new java.awt.Dimension(92, 25));
        butAnula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAnulaActionPerformed(evt);
            }
        });
        panBot.add(butAnula);

        butLimpiar.setText("Limpiar");
        butLimpiar.setToolTipText("Limpia");
        butLimpiar.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpiarActionPerformed(evt);
            }
        });
        panBot.add(butLimpiar);

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        PanFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        panPer.setPreferredSize(new java.awt.Dimension(100, 50));
        panPer.setLayout(null);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panFil.setName(""); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(610, 180));
        panFil.setLayout(null);

        jLabel1.setText("Id:");
        panFil.add(jLabel1);
        jLabel1.setBounds(10, 10, 60, 14);

        txtCodFactor.setEnabled(false);
        txtCodFactor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodFactorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFactorFocusLost(evt);
            }
        });
        txtCodFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFactorActionPerformed(evt);
            }
        });
        panFil.add(txtCodFactor);
        txtCodFactor.setBounds(150, 10, 40, 20);

        txtNomLargo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLargoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLargoFocusLost(evt);
            }
        });
        txtNomLargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLargoActionPerformed(evt);
            }
        });
        panFil.add(txtNomLargo);
        txtNomLargo.setBounds(150, 30, 400, 20);

        txtNomCorto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCortoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCortoFocusLost(evt);
            }
        });
        txtNomCorto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCortoActionPerformed(evt);
            }
        });
        panFil.add(txtNomCorto);
        txtNomCorto.setBounds(150, 50, 400, 20);

        lblDescCorta.setText("Descripción Corta:");
        lblDescCorta.setToolTipText("");
        panFil.add(lblDescCorta);
        lblDescCorta.setBounds(10, 50, 120, 20);

        lblDescLarga.setText("Descripción Larga:");
        panFil.add(lblDescLarga);
        lblDescLarga.setBounds(10, 30, 120, 20);

        lblFactor1.setText("Factor:");
        panFil.add(lblFactor1);
        lblFactor1.setBounds(10, 70, 120, 20);

        txtFactor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFactorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFactorFocusLost(evt);
            }
        });
        txtFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFactorActionPerformed(evt);
            }
        });
        panFil.add(txtFactor);
        txtFactor.setBounds(150, 70, 90, 20);

        lblFactor.setText("Factor Modiifcado:");
        panFil.add(lblFactor);
        lblFactor.setBounds(300, 70, 120, 20);

        txtFactorMod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFactorModFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFactorModFocusLost(evt);
            }
        });
        txtFactorMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFactorModActionPerformed(evt);
            }
        });
        panFil.add(txtFactorMod);
        txtFactorMod.setBounds(420, 70, 90, 20);

        txtObser1.setColumns(20);
        txtObser1.setRows(5);
        jScrollPane1.setViewportView(txtObser1);

        panFil.add(jScrollPane1);
        jScrollPane1.setBounds(150, 90, 400, 40);

        lblObserva1.setText("Observacion:");
        panFil.add(lblObserva1);
        lblObserva1.setBounds(10, 90, 120, 20);

        tblDatFact.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDatFact);

        panFil.add(jScrollPane2);
        jScrollPane2.setBounds(10, 140, 540, 110);

        panDat.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Factor", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        tblDatProceso.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDatProceso);

        butGuardar1.setText("Crear");
        butGuardar1.setToolTipText("Cierra la ventana.");
        butGuardar1.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardar1ActionPerformed(evt);
            }
        });

        butProceso.setText("Ejecutar");
        butProceso.setToolTipText("Proceso");
        butProceso.setPreferredSize(new java.awt.Dimension(92, 25));
        butProceso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProcesoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panDetLayout = new javax.swing.GroupLayout(panDet);
        panDet.setLayout(panDetLayout);
        panDetLayout.setHorizontalGroup(
            panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(butProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panDetLayout.setVerticalGroup(
            panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDetLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panPer.add(tabFrm);
        tabFrm.setBounds(0, 0, 570, 290);

        PanFrm.add(panPer, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanFrm, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(589, 385));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private boolean configurarTblDatProceso()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatPr=new Vector();    //Almacena los datos
            vecCabPr=new Vector(23);  //Almacena las cabeceras
            vecCabPr.clear();
            vecCabPr.add(INT_TBL_DAT_LIN2,"");
            vecCabPr.add(INT_TBL_DAT_CODIGO2,"Id");
            vecCabPr.add(INT_TBL_DAT_COD_ITM2,"Item");
            vecCabPr.add(INT_TBL_DAT_NOM_ITM2,"Nombre del Item");
            vecCabPr.add(INT_TBL_DAT_TRESL2,"Tres L");
            
////            vecCab.add(INT_TBL_DAT_ESTADO,"Estado");
////            vecCab.add(INT_TBL_DAT_OB,"Observacion");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModPro=new ZafTblMod();
            objTblModPro.setHeader(vecCabPr);
            tblDatProceso.setModel(objTblModPro);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModPro.setColumnDataType(INT_TBL_DAT_LIN2, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            

            //Configurar JTable: Establecer tipo de selección.
            tblDatProceso.setCellSelectionEnabled(true);
            tblDatProceso.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPro=new ZafTblPopMnu(tblDatProceso);
            objTblPopMnuPro.setPegarEnabled(true);
            objTblPopMnuPro.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatProceso.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAuxPro=tblDatProceso.getColumnModel();
            tcmAuxPro.getColumn(INT_TBL_DAT_LIN2).setPreferredWidth(30);
            tcmAuxPro.getColumn(INT_TBL_DAT_CODIGO2).setPreferredWidth(30);
            tcmAuxPro.getColumn(INT_TBL_DAT_COD_ITM2).setPreferredWidth(100);            
            tcmAuxPro.getColumn(INT_TBL_DAT_NOM_ITM2).setPreferredWidth(300);  
            tcmAuxPro.getColumn(INT_TBL_DAT_TRESL2).setPreferredWidth(50);  
////            tcmAux.getColumn(INT_TBL_DAT_ESTADO).setPreferredWidth(50);
////            tcmAux.getColumn(INT_TBL_DAT_OB).setPreferredWidth(50);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaPro = new ZafMouMotAda();
            tblDatProceso.getTableHeader().addMouseMotionListener(objMouMotAdaPro);            
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            
////////            objTblCelRenLblDer=new ZafTblCelRenLbl();
////////            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////////            tcmAux.getColumn(INT_TBL_DAT_PESO).setCellRenderer(objTblCelRenLblDer);
////////            tcmAux.getColumn(INT_TBL_DAT_PRECIO).setCellRenderer(objTblCelRenLblDer);            
            
////////            //botones agregados
////////            objTblCelRenBut=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenBut);
////////            objTblCelRenBut=null;
////////            objTblCelRenButConOcp=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenButConOcp);
////////            objTblCelRenButConOcp = null;
////////            
////////                        objTblCelEdiBtn=new ZafTblCelEdiTxt(tblDatGru);
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellEditor(objTblCelEdiBtn);
////////////            
////////            new ButSolTraInv(tblDatGru, INT_TBL_DAT_MODIFICAR);            
            
            
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatProceso.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBusPro=new ZafTblBus(tblDatProceso);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabPro=new ZafTblFilCab(tblDatProceso);
            tcmAuxPro.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabPro);
            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_MODIFICAR);
////            objTblMod.setColumnasEditables(vecAux);
////            vecAux=null;
            objTblModPro.setModoOperacion(objTblModPro.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdPro=new ZafTblOrd(tblDatProceso);

//////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//////            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setCellRenderer(objTblCelRenLbl);
//////            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////
//////                }
//////            });


//Agregando el listener para cuando el usuario de click en un dia
//////        tblDatFact.addMouseListener(new java.awt.event.MouseAdapter() {
//////            @Override
//////            public void mouseClicked(java.awt.event.MouseEvent evt) {
//////                String nomL="",nomC="";
//////                if(evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2)
//////                    {
//////                        txtNomLargo.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_LAR ).toString());
//////                        txtNomCorto.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_CORT ).toString());
//////                        txtFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_FAC ).toString());
//////                        txtObser1.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_OB ).toString());
//////                        txtCodFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_CODIGO ).toString());
//////                    }
//////            }
//////        });
        
        
        
            

            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_CODIGO;

            intColVen=null;
            intColTbl=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdPro=new ZafTblOrd(tblDatProceso);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblModPro.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAuxPro=null;
            
////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
////            objTblMod.setColumnDataType(INT_TBL_DAT_PESO, objTblMod.INT_COL_DBL, new Integer(0), null);
////            objTblMod.setColumnDataType(INT_TBL_DAT_PRECIO, objTblMod.INT_COL_DBL, new Integer(0), null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  

    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_butCanActionPerformed
 
    private boolean obtener_maximo()
    {
        boolean blnRes=true;
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(co_facModPre) AS codigo";
                strSQL+=" FROM Dynamics2021..tbm_facModPre  ";
////                strSQL+=" WHERE st_reg='A'";
                System.out.println("tbm_facModPre: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    intCodCfg=rstLoc1.getInt("codigo");
                }
                else{
                    blnRes=false;
                }
            rstLoc1.close();
            stmLoc1.close();
            conExt.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt=null;
            
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
    
   
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private boolean consultar_Factor(java.sql.Connection conExt)
    {
        boolean blnRes=true;
        
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT co_facModPre AS codigo,rtrim(tx_desCor) AS descripcion FROM Dynamics2021..tbm_facModPre where st_reg='A' \n";
                    strSQL+=" AND ((co_facModPre=" + txtCodFactor.getText() + ") OR (tx_desCor = "+  objUti.codificar(txtNomCorto.getText()) +" ) OR (tx_desLar = "+  objUti.codificar(txtNomLargo.getText()) +" ))";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next())
                {
                    estado=1;
                    blnRes=false;
                }
                    
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    

private boolean insertar_Factor(java.sql.Connection conExt)//chiqui
    {
        boolean blnRes=false;
        try
        {
           if (conExt!=null)
           {
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO Dynamics2021..tbm_facModPre(";
                strSQL+="        co_facModPre,tx_desCor,tx_deslar,nd_fac,st_reg,tx_obs1,tx_obs2,fe_ing,co_usring,tx_comIng)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtCodFactor.getText() + ""; 
                strSQL+=", " +  objUti.codificar(txtNomCorto.getText())   + ""; 
                strSQL+=", " +  objUti.codificar(txtNomLargo.getText())+ ""; 
                strSQL+=", " + txtFactor.getText() + "";                
                strSQL+=", 'A'";              
                strSQL+=", " +  objUti.codificar(txtObser1.getText())+ "";
                strSQL+=", ''";                
                strSQL+=", GETDATE()";//fe_ing
                strSQL+=", '" + objParSis.getCodigoUsuario()+"'"; //usuario ing
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng                
                strSQL+=");";
                System.out.println("tbm_facModPre: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
               blnRes=true;
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

private boolean validarCampos(java.sql.Connection conExt){
  boolean blnRes=true;
  try{
      
      if(txtNomCorto.getText().equals("")) {
        strMsg = "El campo <<Nombre del Factor>> es obligatorio.\nEscriba el Nombre del Factor y vuelva a intentarlo.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        txtNomCorto.requestFocus();
        blnRes=false;
     }
     if(txtFactor.getText().equals("")) {
        strMsg = "El campo <<Factor> es obligatorio.\nNo se puede tener vacio el Factor.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        txtFactor.requestFocus();
        blnRes=false;
     }     
     if(objUti.isNumero(txtFactor.getText())==false) {
        strMsg = "El campo <<Factor> debe ser de tipo Numero";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        txtFactor.requestFocus();
        blnRes=false;
     }
     if (!consultar_Factor(conExt))
     {
        strMsg = "La informacion por ingresar , ya se encuentra ingresada dentro de los factores.";
        oppMsg.showMessageDialog(this,strMsg,strTit,JOptionPane.INFORMATION_MESSAGE);
        txtFactor.requestFocus();
        blnRes=false;         
     }
     



     
  }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

        
    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        boolean blnRes=false,blnInser=false;
        String error ="";
        estado=0;
        
        java.sql.Connection conLoc;
        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        try
        {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conLoc.setAutoCommit(false);
            mensajeError="";
            if (validarCampos(conLoc))
            {
                if(insertar_Factor(conLoc))
////                    mostrarMsgInf("Se ha realizado la insercion del factor de forma exitosa.");          
                    blnInser= true;                    
                else
                {
                    mostrarMsgInf("ha ocurrido un error al ingresar el factor.");    ;
                    blnInser= false;
                }
                if (blnInser== true)
                {
                    conLoc.commit();
                    mostrarMsgInf("La operación CREAR se realizó con éxito.");
                    limpiarFrm();
                    cargarFactores();
                    obtener_maximo();
                    txtCodFactor.setText(String.valueOf(intCodCfg+1));                    
////                    dispose();
                    //////ojo
                    ////        Maestros.ZafMae83.ZafMae83_01 obj = new Maestros.ZafMae83.ZafMae83_01(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis, "", "");
//////////////                    Maestros.ZafMae83.ZafMae83_02 obj = new Maestros.ZafMae83.ZafMae83_02(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis,txtCodFactor.getText(), txtObser1.getText(),0);
////            Maestros.ZafMae83.ZafMae83_03 obj = new Maestros.ZafMae83.ZafMae83_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
////        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
//////////////                    obj.show();
        // TODO add your handling code here:
        
                    
                }
                else
                    conLoc.rollback();
                
            }
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }
    }//GEN-LAST:event_butGuardarActionPerformed
   private boolean limpiarFrm()//chiqui_anterior
    {
        boolean blnRes=true;
        try{
            txtNomLargo.setText("");
            txtNomCorto.setText("");
            txtFactor.setText("");
            txtObser1.setText("");            
////            objTblMod.removeAllRows();
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    private void txtCodFactorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFactorFocusGained
    }//GEN-LAST:event_txtCodFactorFocusGained

    private void txtCodFactorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFactorFocusLost
              //Validar el contenido de la celda sólo si ha cambiado.
////        if (!txtCodGrupo.getText().equalsIgnoreCase(strCodLetItm))
////        {
////            if (txtCodGrupo.getText().equals(""))
////            {
////                txtCodItm.setText("");
////                txtCodAlt.setText("");
////                txtCodGrupo.setText("");
////                txtNomGrupo.setText("");
////            } 
////            else
////            {
////                mostrarVenConItm(2);
////            }
////        }
////        else
////           txtCodGrupo.setText(strCodLetItm);


    }//GEN-LAST:event_txtCodFactorFocusLost

    private void txtCodFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFactorActionPerformed
////        txtCodGrupo.transferFocus();
    }//GEN-LAST:event_txtCodFactorActionPerformed

    private void txtFactorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusGained

    private void txtFactorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusLost

    private void txtFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorActionPerformed

    private void txtNomCortoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCortoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoFocusGained

    private void txtNomCortoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCortoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoFocusLost

    private void txtNomCortoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCortoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoActionPerformed

    private void txtNomLargoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLargoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoFocusGained

    private void txtNomLargoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLargoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoFocusLost

    private void txtNomLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoActionPerformed

    private void butModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModificarActionPerformed
    if (!txtFactor.getText().equals(txtFactorMod.getText()))
    {
        
        double factor =0;
        procesoEjecucion =1;
        //butProceso.setText("Modificar");
        factor = (objUti.parseDouble(txtCodFactor.getText()));
        if (obtener_items(factor)==0 )
        {
            if (!ejecutarProceso())                
                mostrarMsgInf("Fallo en el proceso de Modificación");
            else
                configurarFrm();
        }
        else
            tabFrm.setSelectedIndex(1);


    }

            
    
    
    
////        Maestros.ZafMae83.ZafMae83_03_01 obj = new Maestros.ZafMae83.ZafMae83_03_01(javax.swing.JOptionPane.getFrameForComponent(this), true,objParSis,Integer.parseInt(txtFactor.getText()) ,1,Integer.parseInt(txtFactorMod.getText()));        
////        obj.show();
////        
////        configurarFrm();
////        limpiarFrm();
    }//GEN-LAST:event_butModificarActionPerformed
    private int obtener_items(double codFactor)
    {
        int blnRes=0;
        java.sql.Connection conExt;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        String strSQLItm ="";
        
        try{
            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt!=null){
                stmLoc1=conExt.createStatement();
                //Obtener el código del último registro.
                strSQLItm="";
                strSQLItm+=" SELECT a1.co_grpInv AS codGrupo, trim (a1.tx_codItm) as codItem, trim (a2.ITEMDESC) as nomItm, trim (a2.ITMGEDSC) as tresL \n";
                strSQLItm+=" FROM Dynamics2021..tbm_detGrpInv as a1  \n";
                strSQLItm+="   inner join "+ objParSis.getNombreBaseDatosGP()  +"..iv00101 as a2 on (a1.tx_codItm =a2.ITEMNMBR)  \n";
                strSQLItm+=" WHERE  a1.co_facModPre = "+ codFactor   +"   \n";
                strSQL+=" WHERE st_reg='A'";
                System.out.println("obtener_items: " + strSQLItm);
                rstLoc1=stmLoc1.executeQuery(strSQLItm);
                //Limpiar vector de datos.
                vecDatPr.clear();
                
                while (rstLoc1.next()){
                    vecRegPr=new Vector();
                    vecRegPr.add(INT_TBL_DAT_LIN2,"");
                    vecRegPr.add(INT_TBL_DAT_CODIGO2,rstLoc1.getInt("codGrupo"));                     
                    vecRegPr.add(INT_TBL_DAT_COD_ITM2,rstLoc1.getString("codItem"));
                    vecRegPr.add(INT_TBL_DAT_NOM_ITM2,rstLoc1.getString("nomItm"));
                    vecRegPr.add(INT_TBL_DAT_TRESL2,rstLoc1.getString("tresL"));
                    vecDatPr.add(vecRegPr);
                    blnRes =1;
                }
                
            rstLoc1.close();
            stmLoc1.close();
            conExt.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt=null;
            
            //Asignar vectores al modelo.
            objTblModPro.setData(vecDatPr);
            tblDatProceso.setModel(objTblModPro);
            vecDatPr.clear();             
            
            }

            
          
        }
        catch (java.sql.SQLException e)
        {
            blnRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=-1;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private void butAnulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAnulaActionPerformed
        
        double factor =0;
        procesoEjecucion =2;
        butProceso.setText("Anular");
        factor = (objUti.parseDouble(txtCodFactor.getText()));
        if (obtener_items(factor)==0 )
        {
            if (!ejecutarProceso())                
                mostrarMsgInf("Fallo en el proceso de Anulación");
            else
            {
                configurarFrm();
                
            }
                
        } 
        else
            tabFrm.setSelectedIndex(1);
    }//GEN-LAST:event_butAnulaActionPerformed
    private void limpiar()
    {
        obtener_maximo();
        txtCodFactor.setText( String.valueOf(intCodCfg+1));
        txtNomLargo.setText("");
        txtNomCorto.setText("");
        txtFactor.setText("");
        txtFactorMod.setText("");
        txtObser1.setText("");        
    }     
    private void txtFactorModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorModFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModFocusGained

    private void txtFactorModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorModFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModFocusLost

    private void txtFactorModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorModActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModActionPerformed

    private void butGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butGuardar1ActionPerformed
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
    private boolean ejecutarProceso()
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strSQLUp="";
        boolean blnRes=false;
        String proceso ="",mensaje ="",mensajeConfirmacion;
        
        datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
////            conLoc.setAutoCommit(false);
            if (conLoc!=null)
            {
                if (procesoEjecucion ==1)
                    proceso ="Modificaciòn";
                else
                    proceso ="Anulaciòn";
                mensaje ="¿Está seguro que desea realizar esta operación de "+ proceso;
                mensajeConfirmacion ="Se realizo el proceso de  "+ proceso;
                if (mostrarMsgCon(mensaje)==0)
                {
                    stmLoc=conLoc.createStatement();
                    int intNumFil=objTblModPro.getRowCountTrue();
                    if (procesoEjecucion==2)
                    {
                        for (int i=0; i<intNumFil;i++)
                        {
                            strSQLUp="";
        ////                    strSQLUp+="UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = "+cofFactGlobalNuevo  " + objParSis.getCodigoUsuario() + " ,fe_ultmod = '"+datFecDoc +"'" ;
                            if (procesoEjecucion==1)
                            {
    ////                            strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = "+ objUti.parseDouble(txtFactorMod.getText())  +"\n";
    ////                            strSQLUp+=" WHERE tx_coditm = '"+tblDatProceso.getValueAt(i, INT_TBL_DAT_COD_ITM2 ).toString() +"' and co_grpInv = "+ tblDatProceso.getValueAt(i, INT_TBL_DAT_CODIGO2 ).toString()  +"  ; \n";
                            }
                            else
                            {
                                strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = 0 \n";
                                strSQLUp+=" WHERE tx_coditm = '"+tblDatProceso.getValueAt(i, INT_TBL_DAT_COD_ITM2 ).toString() +"' and co_grpInv = "+ tblDatProceso.getValueAt(i, INT_TBL_DAT_CODIGO2 ).toString()  +"  ; \n";                            
                            }
    ////                            
    ////                        else
    ////                            strSQLUp+=" UPDATE  Dynamics2021..tbm_detGrpInv set  co_facModPre = 0 \n";


                            blnRes =true;
                        }
                        
                    }

                    if (procesoEjecucion==2)
                    {
                        strSQLUp+=" UPDATE  Dynamics2021..tbm_facModPre set   st_reg ='I' \n";
                        strSQLUp+=" WHERE co_facModPre = "+txtCodFactor.getText() +" ; \n"; 
                        blnRes =true;
                    }
                    else
                    {
                        strSQLUp+=" UPDATE  Dynamics2021..tbm_facModPre set  \n";
                        strSQLUp+="             tx_desLar = "+objUti.codificar(txtNomLargo.getText())  +"  \n"; 
                        if ((!txtFactorMod.getText().equals(txtFactor.getText()) ) && (!txtFactorMod.getText().equals(0)))
                            strSQLUp+="         ,   nd_fac = "+ objUti.parseDouble(txtFactorMod.getText())  +"  \n";
                        else
                            strSQLUp+="         ,   nd_fac = "+ objUti.parseDouble(txtFactor.getText())  +"  \n";
                        
                        strSQLUp+="         ,   tx_obs1 = "+ objUti.codificar(txtObser1.getText())  +"  \n";
                        strSQLUp+="         ,   tx_obs2 = "+ objUti.codificar(txtObser1.getText())  +"  \n";
                        strSQLUp+="         ,   tx_desCor = "+objUti.codificar(txtNomCorto.getText())  +"  \n";     
                        strSQLUp+=" WHERE co_facModPre = "+txtCodFactor.getText() +" ; \n";    
                        blnRes =true;
                    }
                    
                    

                    if (blnRes ==true)
                    {
                         System.out.println("tbm_detGrpInv_tbm_facModPre: " + strSQLUp); 
                         stmLoc.executeUpdate(strSQLUp);
                    }                    
                        mostrarMsgInf(mensajeConfirmacion);                    
                        stmLoc.close();
                        stmLoc=null;                    

                } 
            }            
           
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e); 
           blnRes=false;
       }
       catch(Exception  Evt){ 
                objUti.mostrarMsgErr_F1(this, Evt);
                blnRes=false;
        }

    return  blnRes;      
    }
    
    private void butProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProcesoActionPerformed
        // TODO add your handling code here:
        if(!ejecutarProceso())
            mostrarMsgInf("Fallo en el proceso de actualización");
        else
            configurarFrm();
        
        objTblModPro.setRowCount(0);
        tabFrm.setSelectedIndex(0);
            
        
        
    }//GEN-LAST:event_butProcesoActionPerformed

    private void butLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_butLimpiarActionPerformed
    
    private void exitForm() 
    {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanFrm;
    private javax.swing.JButton butAnula;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butGuardar1;
    private javax.swing.JButton butLimpiar;
    private javax.swing.JButton butModificar;
    private javax.swing.JButton butProceso;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblDescCorta;
    private javax.swing.JLabel lblDescLarga;
    private javax.swing.JLabel lblFactor;
    private javax.swing.JLabel lblFactor1;
    private javax.swing.JLabel lblObserva1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panPer;
    private javax.swing.JPanel panRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDatFact;
    private javax.swing.JTable tblDatProceso;
    private javax.swing.JTextField txtCodFactor;
    private javax.swing.JTextField txtFactor;
    private javax.swing.JTextField txtFactorMod;
    private javax.swing.JTextField txtNomCorto;
    private javax.swing.JTextField txtNomLargo;
    private javax.swing.JTextArea txtObser1;
    // End of variables declaration//GEN-END:variables
     
    
       
        
       /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            txtObser1.setBackground(objParSis.getColorCamposObligatorios());
            txtObser1.selectAll();
            txtObser1.requestFocus(); 
            configurarTblDat();  
            cargarFactores();
            
////            configurarVenConItm();

////            configuraListaPrecio();
////            configuraFlutuacion();

            datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());

////            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDatFact.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_CODIGO: strMsg="Código del Factor"; break;
                case INT_TBL_DAT_DESC_CORT: strMsg="Descripcion Corta del Factor"; break;
                case INT_TBL_DAT_DESC_LAR: strMsg="Descripcion Larga del Factor"; break;                
                case INT_TBL_DAT_FAC: strMsg="Valor del Factor"; break;
                case INT_TBL_DAT_ESTADO: strMsg="Estado del Factor"; break;   
                case INT_TBL_DAT_OB: strMsg="Onservaciones del Factor"; break; 
                default: strMsg=""; break;
                        
            }
            tblDatFact.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODIGO,"Id");
            vecCab.add(INT_TBL_DAT_DESC_CORT,"Descripcion Corta");
            vecCab.add(INT_TBL_DAT_DESC_LAR,"Descripcion Larga");
            vecCab.add(INT_TBL_DAT_FAC,"Factor");            
            vecCab.add(INT_TBL_DAT_ESTADO,"Estado");
            vecCab.add(INT_TBL_DAT_OB,"Observacion");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDatFact.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CODIGO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_FAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer tipo de selección.
            tblDatFact.setCellSelectionEnabled(true);
            tblDatFact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatFact);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFact.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFact.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DESC_CORT).setPreferredWidth(100);            
            tcmAux.getColumn(INT_TBL_DAT_DESC_LAR).setPreferredWidth(100);                        
            tcmAux.getColumn(INT_TBL_DAT_FAC).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_ESTADO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_OB).setPreferredWidth(50);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatFact.getTableHeader().addMouseMotionListener(objMouMotAda);            
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            
////////            objTblCelRenLblDer=new ZafTblCelRenLbl();
////////            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
////////            tcmAux.getColumn(INT_TBL_DAT_PESO).setCellRenderer(objTblCelRenLblDer);
////////            tcmAux.getColumn(INT_TBL_DAT_PRECIO).setCellRenderer(objTblCelRenLblDer);            
            
////////            //botones agregados
////////            objTblCelRenBut=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenBut);
////////            objTblCelRenBut=null;
////////            objTblCelRenButConOcp=new ZafTblCelRenBut();
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellRenderer(objTblCelRenButConOcp);
////////            objTblCelRenButConOcp = null;
////////            
////////                        objTblCelEdiBtn=new ZafTblCelEdiTxt(tblDatGru);
////////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setCellEditor(objTblCelEdiBtn);
////////////            
////////            new ButSolTraInv(tblDatGru, INT_TBL_DAT_MODIFICAR);            
            
            
////            tcmAux.getColumn(INT_TBL_DAT_MODIFICAR).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFact.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFact);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatFact);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_MODIFICAR);
////            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFact);

//////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
//////            tcmAux.getColumn(INT_TBL_DAT_CODIGO).setCellRenderer(objTblCelRenLbl);
//////            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//////
//////                }
//////            });


//Agregando el listener para cuando el usuario de click en un dia
        tblDatFact.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String nomL="",nomC="";
                if(evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2)
                    {
                        txtNomLargo.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_LAR ).toString());
                        txtNomCorto.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_DESC_CORT ).toString());
                        txtFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_FAC ).toString());
                        txtCodFactor.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_CODIGO ).toString());
                        txtObser1.setText(tblDatFact.getValueAt(tblDatFact.getSelectedRow(), INT_TBL_DAT_OB ).toString());
                        
                    }
            }
        });
        
        
        
            

            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_CODIGO;

            intColVen=null;
            intColTbl=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFact);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
////            objTblMod.setColumnDataType(INT_TBL_DAT_PESO, objTblMod.INT_COL_DBL, new Integer(0), null);
////            objTblMod.setColumnDataType(INT_TBL_DAT_PRECIO, objTblMod.INT_COL_DBL, new Integer(0), null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  

private boolean cargarFactores()//chiqui
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT co_facModPre as codigo,trim(tx_desCor) as desCor,trim(tx_desLar) as desLar,nd_fac as factor, st_reg as estado, \n";
                    strSQL+="      isnull (tx_obs1,'') as obser1, fe_ing as fecIng FROM Dynamics2021..tbm_facModPre WHERE co_facModPre !=0 \n";                    
                    strSQL+="order by co_facModPre asc";                    
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT co_facModPre as codigo,trim(tx_desCor) as desCor,trim(tx_desLar) as desLar,nd_fac as factor, st_reg as estado, \n";
                    strSQL+="       isnull (tx_obs1,'') as obser1, fe_ing as fecIng FROM Dynamics2021..tbm_facModPre WHERE  co_facModPre !=0 \n";                    
                    strSQL+="order by co_facModPre asc";
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                
             
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_CODIGO,rst.getInt("codigo"));                     
                    vecReg.add(INT_TBL_DAT_DESC_CORT,rst.getString("desCor"));
                    vecReg.add(INT_TBL_DAT_DESC_LAR,rst.getString("desLar"));
                    vecReg.add(INT_TBL_DAT_FAC,rst.getDouble("factor"));                    
                    if (rst.getString("estado").equals("A"))
                        vecReg.add(INT_TBL_DAT_ESTADO,Boolean.TRUE);
                    else
                        vecReg.add(INT_TBL_DAT_ESTADO,Boolean.FALSE);
                    vecReg.add(INT_TBL_DAT_OB,rst.getString("obser1"));
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
                tblDatFact.setModel(objTblMod);
                vecDat.clear();            }
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
    
    
 
    
    

       
    
       
  
           

    
    

    
    
}
