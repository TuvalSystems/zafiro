/*
 * ZafCom10.java
 *
 * Created on 29 de diciembre de 2005, 10:10 PM
 */
package CxC.ZafCxC82;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Mario Marín
 */
public class ZafCxC82 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable: General
    final int INT_TBL_DAT_GEN_LIN=0;                        
    final int INT_TBL_DAT_GEN_FECHA=1;                     
    final int INT_TBL_DAT_GEN_CODEMP=2;                    
    final int INT_TBL_DAT_GEN_NOMEMP=3;                    
    final int INT_TBL_DAT_GEN_CODLOC=4;                     
    final int INT_TBL_DAT_GEN_NOMLOC=5;                     
    final int INT_TBL_DAT_GEN_CODCAJ=6;                     
    final int INT_TBL_DAT_GEN_CAJA=7;                    
    final int INT_TBL_DAT_GEN_SALDO=8;
    final int INT_TBL_DAT_GEN_BTNLISMOV=9;
    final int INT_TBL_DAT_GEN_SALDO_COBRANZA=10;
    final int INT_TBL_DAT_GEN_BTNIMP=11;
    
    //Constantes: Cierres de caja no recibidos 
    final int INT_TBL_DAT_CIECAJ_LIN=0;
    final int INT_TBL_DAT_CIECAJ_CODEMP=1;
    final int INT_TBL_DAT_CIECAJ_NOMEMP=2;
    final int INT_TBL_DAT_CIECAJ_CODLOC=3;
    final int INT_TBL_DAT_CIECAJ_NOMLOC=4;
    final int INT_TBL_DAT_CIECAJ_FECING=5;
    final int INT_TBL_DAT_CIECAJ_CODTIPDOC=6;
    final int INT_TBL_DAT_CIECAJ_DESCORTIPDOC=7;
    final int INT_TBL_DAT_CIECAJ_DESLARTIPDOC=8;
    final int INT_TBL_DAT_CIECAJ_NUMDOC=9;
    final int INT_TBL_DAT_CIECAJ_VALDOC=10;
    final int INT_TBL_DAT_CIECAJ_DIAS=11;
    
    // Constantes: Cheques vencidos
    final int INT_TBL_DAT_CHEVEN_LIN=0;
    final int INT_TBL_DAT_CHEVEN_CODEMP=1;
    final int INT_TBL_DAT_CHEVEN_NOMEMP=2;
    final int INT_TBL_DAT_CHEVEN_CODLOC=3;
    final int INT_TBL_DAT_CHEVEN_NOMLOC=4;
    final int INT_TBL_DAT_CHEVEN_CODCLI=5;
    final int INT_TBL_DAT_CHEVEN_NOMCLI=6;
    final int INT_TBL_DAT_CHEVEN_BANCO=7;
    final int INT_TBL_DAT_CHEVEN_NUMCTA=8;
    final int INT_TBL_DAT_CHEVEN_NUMCHQ=9;
    final int INT_TBL_DAT_CHEVEN_VALCHQ=10;
    final int INT_TBL_DAT_CHEVEN_POSTFECHADO=11;
    final int INT_TBL_DAT_CHEVEN_FECRECCHQ=12;
    final int INT_TBL_DAT_CHEVEN_FECVENCHQ=13;
    final int INT_TBL_DAT_CHEVEN_DIAS=14;

    // Ventas de contado
    final int INT_TBL_DAT_VENCON_LIN=0;
    final int INT_TBL_DAT_VENCON_CODEMP=1;
    final int INT_TBL_DAT_VENCON_NOMEMP=2;
    final int INT_TBL_DAT_VENCON_CODLOC=3;
    final int INT_TBL_DAT_VENCON_NOMLOC=4;
    final int INT_TBL_DAT_VENCON_CODTIPDOC=5;
    final int INT_TBL_DAT_VENCON_DESCORTIPDOC=6;
    final int INT_TBL_DAT_VENCON_DESLARTIPDOC=7;
    final int INT_TBL_DAT_VENCON_NUMDOC=8;
    final int INT_TBL_DAT_VENCON_FECDOC=9;
    final int INT_TBL_DAT_VENCON_CODCLI=10;
    final int INT_TBL_DAT_VENCON_NOMCLI=11;
    final int INT_TBL_DAT_VENCON_TOTAL=12;
    final int INT_TBL_DAT_VENCON_DIAS=13;
           
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModCieCaj;
    private ZafTblMod objTblModChqVen;
    private ZafTblMod objTblModVenCon;
    
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSql, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    
    private ZafDatePicker dtpFecDoc;                            //Fecha
    private Date datFecAux;
   
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC82(ZafParSis obj){
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
        if (configurarFrm()){
            if(configurarFrmCierreCaja()){
                if(configurarFrmChequesVencidos()){
                    if(configurarFrmVentasContadoVencidas()){
                        System.out.println("ok cargandoFormulario");
                    }else{exitForm();}
                }else{exitForm();}
            }else{exitForm();}
        }else{exitForm();}
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
        panGen = new javax.swing.JPanel();
        panGenFil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panGenDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panCieCaj = new javax.swing.JPanel();
        spnDat1 = new javax.swing.JScrollPane();
        tblDatCieCaj = new javax.swing.JTable();
        panCheVen = new javax.swing.JPanel();
        spnDat2 = new javax.swing.JScrollPane();
        tblDatChqVen = new javax.swing.JTable();
        panVenCon = new javax.swing.JPanel();
        spnDat3 = new javax.swing.JScrollPane();
        tblDatVenCon = new javax.swing.JTable();
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

        panGen.setLayout(new java.awt.BorderLayout());

        panGenFil.setPreferredSize(new java.awt.Dimension(100, 50));
        panGenFil.setLayout(null);

        jLabel1.setText("Fecha:");
        panGenFil.add(jLabel1);
        jLabel1.setBounds(10, 10, 60, 14);

        panGen.add(panGenFil, java.awt.BorderLayout.NORTH);

        panGenDat.setLayout(new java.awt.BorderLayout());

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

        panGenDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab(" General", panGen);

        panCieCaj.setLayout(new java.awt.BorderLayout());

        tblDatCieCaj.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat1.setViewportView(tblDatCieCaj);

        panCieCaj.add(spnDat1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cierres de caja no recibidos", panCieCaj);

        panCheVen.setLayout(new java.awt.BorderLayout());

        tblDatChqVen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat2.setViewportView(tblDatChqVen);

        panCheVen.add(spnDat2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cheques vencidos", panCheVen);

        panVenCon.setLayout(new java.awt.BorderLayout());

        tblDatVenCon.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat3.setViewportView(tblDatVenCon);

        panVenCon.add(spnDat3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Ventas de contado vencidas", panVenCon);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCheVen;
    private javax.swing.JPanel panCieCaj;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenDat;
    private javax.swing.JPanel panGenFil;
    private javax.swing.JPanel panVenCon;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnDat2;
    private javax.swing.JScrollPane spnDat3;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatChqVen;
    private javax.swing.JTable tblDatCieCaj;
    private javax.swing.JTable tblDatVenCon;
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
            this.setTitle(strAux + " v0.01");
            lblTit.setText(strAux);
            //Configurar objetos.
            
            
            //Configurar ZafDatePicker: Para la fecha 
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
            datFecAux=null;
            panGenFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(60, 10, 90, 20);
//            dtpFecDoc.setEnabled(false);  
            //fin config zafDatePicker
           
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_GEN_LIN,"");
            vecCab.add(INT_TBL_DAT_GEN_FECHA,"Fecha");
            vecCab.add(INT_TBL_DAT_GEN_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_GEN_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_GEN_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_GEN_NOMLOC,"Local");
            vecCab.add(INT_TBL_DAT_GEN_CODCAJ,"Cód.Caja");
            vecCab.add(INT_TBL_DAT_GEN_CAJA,"Caja");
            vecCab.add(INT_TBL_DAT_GEN_SALDO,"Saldo" );
            vecCab.add(INT_TBL_DAT_GEN_BTNLISMOV,"...");
            vecCab.add(INT_TBL_DAT_GEN_SALDO_COBRANZA,"CxC");
            vecCab.add(INT_TBL_DAT_GEN_BTNIMP,"...");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_GEN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_GEN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_GEN_FECHA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_GEN_NOMEMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_GEN_NOMLOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODCAJ).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CAJA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_GEN_SALDO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_GEN_BTNLISMOV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_GEN_SALDO_COBRANZA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_GEN_BTNIMP).setPreferredWidth(30);
            
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GEN_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GEN_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_GEN_CODCAJ, tblDat);
            
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_GEN_BTNLISMOV);
            vecAux.add("" + INT_TBL_DAT_GEN_BTNIMP);    
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GEN_CODCAJ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_GEN_SALDO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_GEN_SALDO_COBRANZA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_GEN_BTNLISMOV).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_GEN_BTNIMP).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            ButLisDoc butLisDoc = new ButLisDoc(tblDat, INT_TBL_DAT_GEN_BTNLISMOV);  
            ButGenDoc butGenDoc = new ButGenDoc(tblDat, INT_TBL_DAT_GEN_BTNIMP);  
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     */
    
     private class ButLisDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Listado de documentos.");
           
        }
        @Override
        public void butCLick() {
            int intRow = tblDat.getSelectedRow();
            int CodEmp = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODEMP  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODEMP  ).toString());
            int CodLoc = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODLOC  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODLOC  ).toString());
            int CodCot = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODCAJ  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODCAJ  ).toString());
            if(ventanaDocumentos(CodEmp,CodLoc,CodCot)){
                 
            }
            else{
                MensajeInf("Error al cargar los documentos.");
            }
        }
    }
     
     
    /**
     * 
     */ 
    private class ButGenDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButGenDoc(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Arqueo de caja.");
           
        }
        @Override
        public void butCLick() {
            int intRow = tblDat.getSelectedRow();
            int CodEmp = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODEMP  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODEMP  ).toString());
            int CodLoc = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODLOC  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODLOC  ).toString());
            int CodCot = Integer.parseInt( tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODCAJ  )==null?"":tblDat.getValueAt(intRow,  INT_TBL_DAT_GEN_CODCAJ  ).toString());
            if(cargarReporte(1, CodEmp,CodLoc,CodCot)){
                 
            }
            else{
                MensajeInf("Error al cargar los documentos.");
            }
        }
    }
     
    
    private ZafThreadGUIRpt objThrGUIRpt;
    private ZafRptSis objRptSis;
    private String strCodEmp, strCodLoc, strCodCaj, strFecDoc; 
    
    private boolean cargarReporte(int intTipo, int CodEmp, int CodLoc, int CodCaj){
        boolean blnRes=true;
        try{
            if(llenarstrWhere(CodEmp, CodLoc, CodCaj)){
                if (objThrGUIRpt==null){
                    objThrGUIRpt=new ZafThreadGUIRpt();
                    objThrGUIRpt.setIndFunEje(intTipo);
                    objThrGUIRpt.start();
                }
            }
            else{
                MensajeInf("Error: llenando consulta...");
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean llenarstrWhere(int CodEmp, int CodLoc, int CodCaj){
        boolean blnRes=true;
        try{
            strSql="";
            strSql+=" SELECT a2.tx_nom as tx_nomEmp, a3.tx_nom as tx_nomLoc, a1.* \n";
            strSql+=" FROM ( \n";
            strSql+="   SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.fe_doc, CAST( a1.ne_numDoc1 as Varchar) as ne_numDoc,  \n";
            strSql+="           a1.fe_ing  , a4.tx_desCor as tx_desCorTipDoc, a4.tx_desLar as tx_desLarTipDoc, ROUND(SUM(a2.nd_abo),2) as nd_aboModCxC  \n";
            strSql+="   FROM tbm_cabPag AS a1 \n";
            strSql+="   INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
            strSql+="   INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
            strSql+="   INNER JOIN tbm_tipDocCaj as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locTipDoc AND a4.co_tipDoc=a5.co_tipDoc)   \n";
            strSql+="   INNER JOIN tbm_caj as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_caj=a6.co_caj) \n";
            strSql+="   WHERE a1.st_reg='A' AND a1.fe_doc='"+ dtpFecDoc.getText()+"' AND a2.st_reg='A' AND a6.co_emp="+CodEmp+" AND a6.co_loc="+CodLoc+"  AND a6.co_caj="+CodCaj+"  \n";
            strSql+="   GROUP BY a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.fe_doc, a1.fe_ing, a1.ne_numDoc1, a4.tx_desCor, a4.tx_desLar  \n";
            strSql+="   UNION  ALL  \n";
            strSql+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia as co_doc, a1.fe_dia as fe_doc, a1.tx_numDia as ne_numDoc,  \n";
            strSql+="           a1.fe_ing  , a5.tx_desCor, a5.tx_desLar,   \n";
            strSql+="           ROUND(SUM(CASE WHEN a1.co_tipDoc IN (311, 312, 313) THEN a2.nd_monDeb ELSE a2.nd_monDeb*-1 END),2) as nd_aboModCxC       \n";
            strSql+="   FROM tbm_cabDia as a1 \n";
            strSql+="   INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia) \n";
            strSql+="   INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc) \n";
            strSql+="   INNER JOIN tbm_tipDocCaj as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locTipDoc AND a1.co_tipDoc=a3.co_tipDoc) \n";
            strSql+="   INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj) \n";
            strSql+="   WHERE  a1.st_reg IN('A','O','I') AND a1.fe_dia='"+ dtpFecDoc.getText()+"' AND a4.co_emp="+CodEmp+" AND a4.co_loc="+CodLoc+"  AND a4.co_caj="+CodCaj+" AND \n";
            strSql+="            a3.co_tipDoc NOT IN (24, 183, 287) \n";
            strSql+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia,a1.fe_ing,  a1.tx_numDia, a5.tx_desCor, a5.tx_desLar   \n";
            strSql+=" ) as a1 \n";
            strSql+=" INNER JOIN tbm_emp as a2 ON (a1.co_emp=a2.co_emp) \n";
            strSql+=" INNER JOIN tbm_loc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc) \n";
            strSql+=" ORDER BY a1.fe_ing ";
            
            strCodEmp = CodEmp+"";
            strCodLoc = CodLoc+"";
            strCodCaj = CodCaj+"";
            strFecDoc = dtpFecDoc.getText();
          
        }
        catch (Exception e){
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    generarRpt(1);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIRpt=null;
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
    private boolean generarRpt(int intTipRpt)  // 0 IMPRIME VISTA PRLIMIAR
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        try
        {
            System.out.println("generarRpt..... ");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null) {
                objRptSis = new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                objRptSis.cargarListadoReportes(con,objParSis.getCodigoEmpresa(),objParSis.getCodigoLocal(),objParSis.getCodigoMenu(),objParSis.getCodigoUsuario());
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();           
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            strRutRpt=objRptSis.getRutaReporte(i);
                            strNomRpt=objRptSis.getNombreReporte(i);
                            //Inicializar los parametros que se van a pasar al reporte.
                            java.util.Map mapPar=new java.util.HashMap();
                            mapPar.put("SUBREPORT_DIR", strRutRpt);
                            mapPar.put("CodEmp", strCodEmp);
                            mapPar.put("CodLoc", strCodLoc);
                            mapPar.put("CodCaj", strCodCaj);
                            mapPar.put("FecDoc", strFecDoc);
                            mapPar.put("CodUsr", objParSis.getCodigoUsuario()+"");
                            mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + "   " + strFecHorSer + "   " + this.getClass().getName() + "   " + strNomRpt);                                
                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                        }
                    }
                }
                con.close();
                con=null;
            }
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
     private boolean ventanaDocumentos(int CodEmp, int CodLoc, int CodCot){
        boolean blnRes=true;
         try{
            ZafCxC82_01 obj1 = new  ZafCxC82_01(javax.swing.JOptionPane.getFrameForComponent(this),objParSis, CodEmp , CodLoc , CodCot, dtpFecDoc.getText());
            //this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
            
    }
     
     private void MensajeInf(String strMensaje) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        JOptionPane.showMessageDialog(this, strMensaje, strTit, JOptionPane.INFORMATION_MESSAGE);
    }
    
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
      
    
    /**
     * Cierres de caja no recibidos 
     * @return 
     */
    private boolean configurarFrmCierreCaja()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_CIECAJ_LIN,"");
            vecCab.add(INT_TBL_DAT_CIECAJ_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CIECAJ_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_CIECAJ_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CIECAJ_NOMLOC,"Local");
            vecCab.add(INT_TBL_DAT_CIECAJ_FECING,"Fec.Ing.");
            vecCab.add(INT_TBL_DAT_CIECAJ_CODTIPDOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CIECAJ_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CIECAJ_DESLARTIPDOC,"Tipo de Documento");
            vecCab.add(INT_TBL_DAT_CIECAJ_NUMDOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_CIECAJ_VALDOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_CIECAJ_DIAS,"Dias");
            
            objTblModCieCaj=new ZafTblMod();
            objTblModCieCaj.setHeader(vecCab);
            tblDatCieCaj.setModel(objTblModCieCaj);
            //Configurar JTable: Establecer tipo de selección.
            tblDatCieCaj.setRowSelectionAllowed(true);
            tblDatCieCaj.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatCieCaj,INT_TBL_DAT_CIECAJ_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatCieCaj);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCieCaj.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatCieCaj.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_NOMEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_NOMLOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_FECING).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_DESLARTIPDOC).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_VALDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_DIAS).setPreferredWidth(60);
            
            objTblModCieCaj.addSystemHiddenColumn(INT_TBL_DAT_CIECAJ_CODEMP, tblDatCieCaj);
            objTblModCieCaj.addSystemHiddenColumn(INT_TBL_DAT_CIECAJ_CODLOC, tblDatCieCaj);
            objTblModCieCaj.addSystemHiddenColumn(INT_TBL_DAT_CIECAJ_CODTIPDOC, tblDatCieCaj);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatCieCaj.getTableHeader().setReorderingAllowed(false);
 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatCieCaj.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatCieCaj);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_CODTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_NUMDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_DIAS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CIECAJ_VALDOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatCieCaj);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    
    /**
     *  Cheques vencidos
     * @return 
     */
    private boolean configurarFrmChequesVencidos()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_CHEVEN_LIN,"");
            vecCab.add(INT_TBL_DAT_CHEVEN_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CHEVEN_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_CHEVEN_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CHEVEN_NOMLOC,"Local");
            vecCab.add(INT_TBL_DAT_CHEVEN_CODCLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_CHEVEN_NOMCLI,"Cliente");
            vecCab.add(INT_TBL_DAT_CHEVEN_BANCO,"Banco");
            vecCab.add(INT_TBL_DAT_CHEVEN_NUMCTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_CHEVEN_NUMCHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_DAT_CHEVEN_VALCHQ,"Val.Chq.");
            vecCab.add(INT_TBL_DAT_CHEVEN_POSTFECHADO,"Postfechado");
            vecCab.add(INT_TBL_DAT_CHEVEN_FECRECCHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_DAT_CHEVEN_FECVENCHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_DAT_CHEVEN_DIAS,"Dias");
            
             
            
            objTblModChqVen=new ZafTblMod();
            objTblModChqVen.setHeader(vecCab);
            tblDatChqVen.setModel(objTblModChqVen);
            //Configurar JTable: Establecer tipo de selección.
            tblDatChqVen.setRowSelectionAllowed(true);
            tblDatChqVen.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatChqVen,INT_TBL_DAT_CHEVEN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatChqVen);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatChqVen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatChqVen.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_NOMEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_NOMLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODCLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_NOMCLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_BANCO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_NUMCTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_NUMCHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_VALCHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_POSTFECHADO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_FECRECCHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_FECVENCHQ).setPreferredWidth(70);
            
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_DIAS).setPreferredWidth(60);
            objTblModChqVen.addSystemHiddenColumn(INT_TBL_DAT_CHEVEN_CODEMP, tblDatChqVen);
            objTblModChqVen.addSystemHiddenColumn(INT_TBL_DAT_CHEVEN_CODLOC, tblDatChqVen);
           
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatChqVen.getTableHeader().setReorderingAllowed(false);
 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatChqVen.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatChqVen);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_CODCLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CHEVEN_VALCHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
 
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatChqVen);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     *  Cheques vencidos
     * @return 
     */
    private boolean configurarFrmVentasContadoVencidas()
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_VENCON_LIN,"");
            vecCab.add(INT_TBL_DAT_VENCON_CODEMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_VENCON_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DAT_VENCON_CODLOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_VENCON_NOMLOC,"Local");
            vecCab.add(INT_TBL_DAT_VENCON_CODTIPDOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_VENCON_DESCORTIPDOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_VENCON_DESLARTIPDOC,"Tipo de Documento");
            vecCab.add(INT_TBL_DAT_VENCON_NUMDOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_VENCON_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_VENCON_CODCLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_VENCON_NOMCLI,"Cliente");
            vecCab.add(INT_TBL_DAT_VENCON_TOTAL,"Total");
            vecCab.add(INT_TBL_DAT_VENCON_DIAS,"Dias");
            objTblModVenCon=new ZafTblMod();
            objTblModVenCon.setHeader(vecCab);
            tblDatVenCon.setModel(objTblModVenCon);
            //Configurar JTable: Establecer tipo de selección.
            tblDatVenCon.setRowSelectionAllowed(true);
            tblDatVenCon.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatVenCon,INT_TBL_DAT_CHEVEN_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatVenCon);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatVenCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatVenCon.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_VENCON_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_NOMEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_NOMLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_DESCORTIPDOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_DESLARTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_FECDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_NOMCLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_TOTAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_DIAS).setPreferredWidth(60);
            objTblModVenCon.addSystemHiddenColumn(INT_TBL_DAT_VENCON_CODEMP, tblDatVenCon);
            objTblModVenCon.addSystemHiddenColumn(INT_TBL_DAT_VENCON_CODLOC, tblDatVenCon);
            objTblModVenCon.addSystemHiddenColumn(INT_TBL_DAT_VENCON_CODTIPDOC, tblDatVenCon);
           
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatVenCon.getTableHeader().setReorderingAllowed(false);
 
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDatChqVen.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatVenCon);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODEMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODLOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_DESLARTIPDOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODCLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODCLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_CODCLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VENCON_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatVenCon);
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
     * Metodo que muestra los cierres de caja que no han pasado a recepción de efectivo...
     * @return 
     */
    
    private boolean cargarCieCaj(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                strSql="";
                strSql+="  SELECT a1.co_emp, a4.tx_nom as tx_nomEmp, a1.co_loc, a5.tx_nom as tx_nomLoc, a1.co_tipDoc, a3.tx_desCor,  \n";
                strSql+="         a3.tx_desLar, a1.co_dia, a1.tx_numDia, CAST(a1.fe_ing as date) as fe_ing,     \n";
                strSql+="         ROUND( a2.nd_monDeb ,2) as nd_aboModCxCrstLoc, current_date - CAST(a1.fe_ing as date) as atraso   ";
                strSql+="  FROM tbm_cabDia as a1 \n";
                strSql+="  INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia) \n";
                strSql+="  INNER JOIN tbm_cabTipDoc as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) \n";
                strSql+="  INNER JOIN tbm_emp as a4 ON (a1.co_emp=a4.co_emp) \n";
                strSql+="  INNER JOIN tbm_loc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc) \n";
                strSql+="  WHERE a1.st_reg='O' and a2.nd_monDeb > 0 AND a1.co_tipDoc IN (92, 184 , 288)   \n";
                if(objParSis.getCodigoEmpresa()!=0){
                    strSql+=" AND a1.co_emp="+objParSis.getCodigoEmpresa()+" ";
                }
                strSql+="  ORDER BY a1.co_emp, a1.co_loc, atraso desc,  a1.co_tipDoc \n";
                strSql+="  \n";
                System.out.println("cargarCieCaj: " + strSql);
                rstLoc =stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_CIECAJ_LIN, "");
                    vecReg.add(INT_TBL_DAT_CIECAJ_CODEMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_CIECAJ_NOMEMP, rstLoc.getString("tx_nomEmp")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_CODLOC, rstLoc.getString("co_loc")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_NOMLOC, rstLoc.getString("tx_nomLoc")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_FECING, rstLoc.getString("fe_ing")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_CODTIPDOC, rstLoc.getString("co_tipDoc")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_DESCORTIPDOC, rstLoc.getString("tx_desCor")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_DESLARTIPDOC, rstLoc.getString("tx_desLar")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_NUMDOC, rstLoc.getString("tx_numDia") ); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_VALDOC, rstLoc.getString("nd_aboModCxCrstLoc")); 
                    vecReg.add(INT_TBL_DAT_CIECAJ_DIAS,  rstLoc.getString("atraso")); 
                    vecDat.add(vecReg);
                }
                //Asignar vectores al modelo.
                objTblModCieCaj.setData(vecDat);
                tblDatCieCaj.setModel(objTblModCieCaj);
                vecDat.clear();
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc = null;
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Carga los cheques vencidos
     * @return 
     */
    private boolean cargarChequesVencidos(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSql="";
                strSql+=" SELECT  *, ( nd_monchq - CASE WHEN totvalasi iS NULL THEN 0 ELSE totvalasi END ) as valpenasifac  \n";
                strSql+=" FROM (  \n";
                strSql+="       SELECT (  \n";
                strSql+="           SELECT sum(x1.nd_monchq) \n";
                strSql+="           FROM tbr_detrecdocpagmovinv as x  \n";
                strSql+="           INNER JOIN tbm_pagmovinv as x1 on (x1.co_emp=x.co_emprel and x1.co_loc=x.co_locrel and x1.co_tipdoc=x.co_tipdocrel and  \n";
                strSql+="                                              x1.co_doc=x.co_docrel and x1.co_reg=x.co_regrel)  \n";
                strSql+="           WHERE x.co_emp=a.co_emp and x.co_loc=a.co_loc and x.co_tipdoc=a.co_tipdoc and x.co_doc=a.co_doc and x.co_reg=a.co_reg and x.st_reg='A'  \n";
                strSql+="           ) as totvalasi  ,*   \n";
                strSql+="       FROM (      \n";
                strSql+="           SELECT a1.co_emp, a4.tx_nom as tx_nomEmp, a1.co_loc, a5.tx_nom as tx_nomLoc, a1.co_tipdoc,  \n";
                strSql+="                  a1.co_doc, a1.co_reg,  a1.co_cli, a3.tx_nom, a1.co_banchq,    \n";
                strSql+="                  a2.tx_descor, a2.tx_deslar,a1.tx_numctachq, a1.tx_numchq,a1.fe_recChq, a1.fe_venchq,    \n";
                strSql+="                  CASE WHEN a1.fe_recChq <> a1.fe_venChq THEN 'S' ELSE 'N' END as Posfechado  , \n";
                strSql+="                  a1.nd_monchq ,  CURRENT_DATE - a1.fe_recChq AS diasVencidos      \n";
                strSql+="           FROM tbm_cabrecdoc AS a   \n";
                strSql+="           INNER JOIN tbm_detrecdoc AS a1 ON (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc )   \n";
                strSql+="           INNER JOIN tbm_cli as a3 ON (a3.co_emp=a1.co_emp and a3.co_cli=a1.co_cli)    \n";
                strSql+="           INNER JOIN tbm_emp AS a4 ON (a1.co_emp=a4.co_emp)  \n";
                strSql+="           INNER JOIN tbm_loc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc) \n";
                strSql+="           LEFT  JOIN tbm_var as a2 ON (a2.co_reg=a1.co_banchq )    \n";
                strSql+="           WHERE /* a.co_emp=1 AND a.co_loc=4 AND*/ a.co_tipdoc= 94  and a.st_reg not in ('E','I') and a1.st_reg = 'A'  AND a1.nd_valapl = 0  AND   \n";
                strSql+="                 a1.co_tipdoccon IN (    \n";
                strSql+="                                      SELECT *   \n";
                strSql+="                                      FROM (   \n";
                strSql+="                                             SELECT a.co_tipdoc      \n";
                strSql+="                                             FROM tbr_tipdocdetprg as b   \n";
                strSql+="                                             LEFT OUTER JOIN tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc) \n";
                strSql+="                                             WHERE b.co_mnu = 1831  \n";
                strSql+="                                             GROUP BY a.co_tipdoc  \n";
                strSql+="                                      ) as a          \n";	
		strSql+="                   )    \n";				 
		strSql+="       ) as a    \n";
                strSql+=" ) as x   \n";
                strSql+=" ORDER BY x.diasVencidos DESC, x.nd_monchq DESC  \n";
                strSql+=" \n";
                System.out.println("v 0.02 (30/Oct/2019) cargarChequesVencidos: " + strSql);
                rst=stm.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
              
  		while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_CHEVEN_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHEVEN_CODEMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_NOMEMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_CODLOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_NOMLOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_CODCLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_NOMCLI,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_BANCO,rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_NUMCTA,rst.getString("tx_numctachq"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_NUMCHQ,rst.getString("tx_numchq"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_VALCHQ,rst.getString("nd_monchq"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_POSTFECHADO,rst.getString("Posfechado").equals("S")?true:false);
                        vecReg.add(INT_TBL_DAT_CHEVEN_FECRECCHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_FECVENCHQ,rst.getString("fe_venchq"));
                        vecReg.add(INT_TBL_DAT_CHEVEN_DIAS,rst.getString("diasVencidos"));
                        vecDat.add(vecReg);
                    }
                    else{
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
                objTblModChqVen.setData(vecDat);
                tblDatChqVen.setModel(objTblModChqVen);
                vecDat.clear();
                
            }
	}
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
    
    
    
    /**
     * Carga los cheques vencidos
     * @return 
     */
    private boolean cargarVentasContadoVencidas(){
        boolean blnRes=true;
        try{
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSql="";
                strSql+=" SELECT a1.co_emp, a3.tx_nom as tx_nomEmp, a1.co_loc, a4.tx_nom as tx_nomLoc, a1.co_tipDoc, a5.tx_desCor as tx_desCorTipDoc, \n";
                strSql+="        a5.tx_desLar as tx_desLarTipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, a1.nd_tot,   \n";
                strSql+="        CURRENT_DATE - a1.fe_doc as diasVencimiento  , a2.mo_pag + nd_abo as saldo  \n";
                strSql+=" FROM tbm_cabMovInv as a1 \n";
                strSql+=" INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSql+=" INNER JOIN tbm_emp as a3 ON (a1.co_emp=a3.co_emp) \n";
                strSql+=" INNER JOIN tbm_loc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc) \n";
                strSql+=" INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc) \n";
                strSql+=" INNER JOIN tbm_cabForPag as a6 ON (a1.co_emp=a6.co_emp AND a1.co_forPag=a6.co_forPag) \n";
                strSql+=" WHERE a1.co_tipDoc=228 and a6.ne_tipForPag IN (1,5) and a2.co_tipRet IS NULL AND a2.mo_pag + nd_abo < 0 AND a2.st_reg IN ('A','C')  AND  \n";
                strSql+="        a1.st_reg = 'A' and a1.fe_doc > '2018-01-01' \n";
                strSql+=" ORDER BY diasVencimiento DESC   \n";
                strSql+=" \n";
                System.out.println("cargarVentasContadoVencidas:  " + strSql);
                rst=stm.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
              
  		while (rst.next()){ 
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_VENCON_LIN,"");
                        vecReg.add(INT_TBL_DAT_VENCON_CODEMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_VENCON_NOMEMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_VENCON_CODLOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_VENCON_NOMLOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_VENCON_CODTIPDOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_VENCON_DESCORTIPDOC,rst.getString("tx_desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_VENCON_DESLARTIPDOC,rst.getString("tx_desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_VENCON_NUMDOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_VENCON_FECDOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VENCON_CODCLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_VENCON_NOMCLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_VENCON_TOTAL,rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_VENCON_DIAS,rst.getString("diasVencimiento"));
                        vecDat.add(vecReg);
                    }
                    else{
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
                objTblModVenCon.setData(vecDat);
                tblDatVenCon.setModel(objTblModVenCon);
                vecDat.clear();
            }
	}
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
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
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{ 
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strAux="";
                //Obtener la condición.
                if(dtpFecDoc.isFecha()) {
                    strAux+=" '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"'";
                }
                // <editor-fold defaultstate="collapsed" desc=" /* José Marín:   */ "> 
                //Armar la sentencia SQL.
//                strSql="";
//                strSql+=" SELECT  '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"' as fecha, a6.co_emp, a7.tx_nom as tx_nomEmp, a6.co_loc, a8.tx_nom as tx_nomLoc, a6.co_caj, a6.tx_desLar,   \n";
//                strSql+="         ROUND(SUM(a2.nd_abo),2) AS nd_aboModCxC      \n";
//                strSql+=" FROM tbm_cabPag AS a1	 \n";
//                strSql+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
//                strSql+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)   \n";
//                strSql+=" INNER JOIN tbm_tipDocCaj as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locTipDoc AND a4.co_tipDoc=a5.co_tipDoc)   \n";
//                strSql+=" INNER JOIN tbm_caj as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_caj=a6.co_caj) \n";
//                strSql+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp) \n";
//                strSql+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc) \n";
//                strSql+=" WHERE a1.st_reg='A'  AND a2.st_reg='A' \n";
//                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                    strSql+=" a1.co_emp="+objParSis.getCodigoEmpresa() + " AND a1.co_loc="+objParSis.getCodigoLocal()+" ";
//                }
//                strSql+=strAux;
//                strSql+=" GROUP BY a6.co_emp, a7.tx_nom , a6.co_loc, a8.tx_nom, a6.co_caj, a6.tx_desLar \n";  
                
                
                //</editor-fold>
                strSql="";
                strSql+=" SELECT a1.*, a7.tx_nom as tx_nomEmp, a8.tx_nom as tx_nomLoc \n";
                strSql+=" FROM ( \n";
                strSql+="   SELECT  '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"' as fecha, \n";
                strSql+="           a.co_emp, a.co_loc, a.co_caj, a.tx_desLar, \n";
                strSql+="           ROUND( CASE WHEN a1.nd_aboModCxC IS NULL THEN 0 ELSE a1.nd_aboModCxC END +  \n";
                strSql+="                  CASE WHEN a2.nd_aboModCxC IS NULL THEN 0 ELSE a2.nd_aboModCxC END, 2) as nd_aboModCxC , a3.nd_aboModCxC_2  \n";
                strSql+="   FROM tbm_caj as a \n";
                if(objParSis.getCodigoUsuario()!=1){
                    strSql+=" INNER JOIN tbm_usrCaj as b ON (a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_caj=b.co_caj AND b.co_usr="+objParSis.getCodigoUsuario()+"  ";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSql+=" ) \n";
                    }
                    else{
                        strSql+=" AND b.co_emp="+objParSis.getCodigoEmpresa()+" AND b.co_loc="+objParSis.getCodigoLocal()+"  ) \n"; 
                    }
                }
                strSql+="   LEFT OUTER JOIN( \n";
                strSql+="           SELECT a6.co_emp, a6.co_loc, a6.co_caj, a6.tx_desLar, ROUND(SUM(a2.nd_abo),2) AS nd_aboModCxC  \n";
                strSql+="           FROM tbm_cabPag AS a1	  \n";
                strSql+="           INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                strSql+="           INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                strSql+="           INNER JOIN tbm_tipDocCaj as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locTipDoc AND a4.co_tipDoc=a5.co_tipDoc) \n";
                strSql+="           INNER JOIN tbm_caj as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_caj=a6.co_caj AND a1.fe_doc >= a6.fe_iniCieCajObl) \n";
                strSql+="           WHERE a1.st_reg='A'  AND a2.st_reg='A' AND a1.fe_doc="+strAux+" \n";
                strSql+="           GROUP BY a6.co_emp, a6.co_loc, a6.co_caj, a6.tx_desLar  \n";
                strSql+="   ) as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_caj=a1.co_caj)  \n";
                strSql+="   LEFT OUTER JOIN ( \n";
                strSql+="       SELECT a4.co_emp, a4.co_loc,a4.co_caj, a4.tx_desLar, \n";
                strSql+="              ROUND(SUM(CASE WHEN a1.co_tipDoc IN (311, 312, 313) THEN a2.nd_monDeb ELSE a2.nd_monDeb*-1 END),2) as nd_aboModCxC       \n";
                strSql+="       FROM tbm_cabDia as a1 \n";
                strSql+="       INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)   \n";
                strSql+="       INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)  \n";
                strSql+="       INNER JOIN tbm_tipDocCaj as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locTipDoc AND a1.co_tipDoc=a3.co_tipDoc)  \n";
                strSql+="       INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj AND a1.fe_dia >= a4.fe_iniCieCajObl)  \n";
                strSql+="       WHERE  a1.st_reg IN('A', 'O', 'I') AND a1.fe_dia="+strAux+" AND a5.co_tipDoc NOT IN (24, 183, 287) \n";
                strSql+="       GROUP BY a4.co_emp, a4.co_loc,a4.co_caj  \n";
                strSql+="   ) as a2 ON (a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_caj=a2.co_caj)  \n";
                strSql+="   LEFT OUTER JOIN ( \n";
                strSql+="       SELECT a.co_emp, a.co_loc, a.co_caj, a.tx_desLar,  \n";
                strSql+="              ROUND( CASE WHEN a1.nd_aboModCxC IS NULL THEN 0 ELSE a1.nd_aboModCxC END +  \n";
                strSql+="              CASE WHEN a2.nd_aboModCxC IS NULL THEN 0 ELSE a2.nd_aboModCxC END, 2) as nd_aboModCxC_2    \n";
                strSql+="       FROM tbm_caj as a   \n";
                strSql+="       LEFT OUTER JOIN(   \n";
                strSql+="           SELECT a6.co_emp, a6.co_loc, a6.co_caj, a6.tx_desLar, ROUND(SUM(a2.nd_abo),2) AS nd_aboModCxC    \n";
                strSql+="           FROM tbm_cabPag AS a1  \n";
                strSql+="           INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)   \n";
                strSql+="           INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)  \n";
                strSql+="           INNER JOIN tbm_tipDocCaj as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locTipDoc AND a4.co_tipDoc=a5.co_tipDoc)  \n";
                strSql+="           INNER JOIN tbm_caj as a6 ON (a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_caj=a6.co_caj AND a1.fe_doc >= a6.fe_iniCieCajObl)  \n";
                strSql+="           WHERE a1.st_reg='A'  AND a2.st_reg='A' \n";
                strSql+="           GROUP BY a6.co_emp, a6.co_loc, a6.co_caj, a6.tx_desLar    \n";
                strSql+="       ) as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_caj=a1.co_caj) \n";
                strSql+="       LEFT OUTER JOIN (  \n";
                strSql+="           SELECT a4.co_emp, a4.co_loc,a4.co_caj, a4.tx_desLar,   \n";
                strSql+="                  ROUND(SUM(CASE WHEN a1.co_tipDoc IN (311, 312, 313) THEN a2.nd_monDeb ELSE a2.nd_monDeb*-1 END),2) as nd_aboModCxC   \n";
                strSql+="           FROM tbm_cabDia as a1  \n";
                strSql+="           INNER JOIN tbm_detDia as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)   \n";
                strSql+="           INNER JOIN tbm_cabTipDoc as a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)   \n";
                strSql+="           INNER JOIN tbm_tipDocCaj as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_locTipDoc AND a1.co_tipDoc=a3.co_tipDoc)    \n";
                strSql+="           INNER JOIN tbm_caj as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_caj=a4.co_caj AND a1.fe_dia >= a4.fe_iniCieCajObl)   \n";
                strSql+="           INNER JOIN tbm_detRecEfe as a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_locRec AND a1.co_tipDoc=a6.co_tipDocRec AND a1.co_dia=a6.co_docRec) \n";
                strSql+="           INNER JOIN tbm_cabRecEfe as a7 ON (a6.co_emp=a7.co_emp AND a6.co_loc=a7.co_loc AND a6.co_tipDoc=a7.co_tipDoc AND a6.co_doc=a7.co_doc) \n";
                strSql+="           WHERE  a1.st_reg IN('A')   AND a5.co_tipDoc NOT IN (24, 183, 287) AND a7.st_reg='A'  \n";
                strSql+="           GROUP BY a4.co_emp, a4.co_loc,a4.co_caj    \n";
                strSql+="       ) as a2 ON (a.co_emp=a2.co_emp AND a.co_loc=a2.co_loc AND a.co_caj=a2.co_caj)    \n";
                strSql+="   ) as a3 ON (a.co_emp=a3.co_emp AND a.co_loc=a3.co_loc AND a.co_caj=a3.co_caj)    \n";
	        strSql+=" )as a1 \n";
                strSql+=" INNER JOIN tbm_emp as a7 ON (a1.co_emp=a7.co_emp)   \n";
                strSql+=" INNER JOIN tbm_loc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc)   \n";
                strSql+=" \n";
                System.out.println("cargarDetReg: " + strSql);
                rst=stm.executeQuery(strSql);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_GEN_LIN,"");
                        vecReg.add(INT_TBL_DAT_GEN_FECHA,rst.getString("fecha"));
                        vecReg.add(INT_TBL_DAT_GEN_CODEMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_GEN_NOMEMP,rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_GEN_CODLOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_GEN_NOMLOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_GEN_CODCAJ,rst.getString("co_caj"));
                        vecReg.add(INT_TBL_DAT_GEN_CAJA,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_GEN_SALDO,rst.getString("nd_aboModCxC"));
                        vecReg.add(INT_TBL_DAT_GEN_BTNLISMOV,"");
                        vecReg.add(INT_TBL_DAT_GEN_SALDO_COBRANZA,rst.getString("nd_aboModCxC_2"));
                        vecReg.add(INT_TBL_DAT_GEN_BTNIMP,"");
                        vecDat.add(vecReg);
                    }
                    else{
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
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread{
        public void run(){
            if ( !cargarPantallas()){
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
        }
    }
    
    
    private boolean cargarPantallas(){
        boolean blnRes=false;
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if(cargarDetReg()){
                if(cargarChequesVencidos()){
                    if(cargarVentasContadoVencidas()){
                        if(cargarCieCaj()){
                            blnRes=true;
                        }
                    }
                }
            }
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        }
        catch (Exception e){
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
                case INT_TBL_DAT_GEN_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_GEN_FECHA:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_GEN_CODEMP:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_GEN_NOMEMP:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_GEN_CODLOC:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_GEN_NOMLOC:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_GEN_CODCAJ:
                    strMsg="Stock consolidado";
                    break;
                case INT_TBL_DAT_GEN_CAJA:
                    strMsg="Precio de venta 1";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}