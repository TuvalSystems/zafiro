/*
 * ZafCom111.java
 *
 * Created on 05 de enero de 2006, 10:10 PM
 */
package Compras.ZafCom111;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
////import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
////import Librerias.ZafVenCon.ZafVenConItm01;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom111 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_2=4;                   //Código del item (Alterno 2).
    static final int INT_TBL_DAT_COD_ALT_3=5;                   //Código del item (Alterno 3).
    static final int INT_TBL_DAT_NOM_ITM=6;                     //Nombre del item.
    static final int INT_TBL_DAT_CHK_SER=7;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_UNI=8;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_DAT_DEC_UNI=9;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_BUT_UNI=10;                     //Botón de consulta.
    static final int INT_TBL_DAT_STK_CON=11;                    //Stock consolidado.
    static final int INT_TBL_DAT_PRE_VTA1=12;                   //Precio de venta 1.
    static final int INT_TBL_DAT_MAR_UTI=13;                    //Margen de utilidad.
    static final int INT_TBL_DAT_PES_KGR=14;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_CAN_MAX_VEN=15;                //Cantidad máxima para la venta.
    static final int INT_TBL_DAT_COS_UNI=16;                    //Costo unitario.
    static final int INT_TBL_DAT_FAC_COS_UNI=17;                //Factor para el costo unitario.
    static final int INT_TBL_DAT_COS_UNI_FAC=18;                //Costo unitario con factor.
    static final int INT_TBL_DAT_MAR_UTI_PRE_VTA=19;            //Margen de utilidad sobre el costo unitario.
    static final int INT_TBL_DAT_BUT_HIS=20;                    //Botón "Histórico".
    static final int INT_TBL_DAT_CHK_REV_TEC=21;                //Revisión técnica.
    static final int INT_TBL_DAT_UNI_VEN=22;                    //Rotación de inventario.
    static final int INT_TBL_DAT_INV_PRO=23;                    //Rotación de inventario.
    static final int INT_TBL_DAT_ROT_INV=24;                    //Rotación de inventario.
    static final int INT_TBL_DAT_PRE_SUG=25;                    //Precio Sugerido.//chiqui
    static final int INT_TBL_DAT_CHK_PREV_SUG=26;               //Nombre del item.//chiqui
    static final int INT_TBL_DAT_MOD=27;                        //Se modifico o no .//chiqui
    
    static final int INT_TBL_MOD_ITM=0;                          //Item //chiqui
    static final int INT_TBL_MOD_PRE_A=1;                        //Precio ACTUAL//chiqui
    static final int INT_TBL_MOD_PRE_S=2;                        //Precio SUGERIDO//chiqui
    static final int INT_TBL_MOD_PRE_M=3;                        //Precio MODIFICADO//chiqui
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer;                 //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPre;                 //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtMargen; 
    private ZafTblCelEdiTxt objTblCelEdiTxtMarUti;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPesKgr;              //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMaxVen;           //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtFacCosUni;           //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed;        //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
////    private ZafVenCon vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private ZafVenConGP vcoUniMed;                                //Ventana de consulta "Unidad de medida".
    private ZafVenConItmGP01 objVenConItm01;
    private Connection con;
    private Connection conGP;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private Vector<String> vecProInv;                          //Promedio de inventario.
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafCom111_01 objCom18_01;
    String strVer ="v0.5";
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ArrayList arlDatITEMSelecc,arlRegITEMSelecc;
   
    /** Crea una nueva instancia de la clase ZafCom111. */
    public ZafCom111(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
        chkSolStk = new javax.swing.JCheckBox();
        panFilRotInv = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cboProInv = new javax.swing.JComboBox<>();
        chkMosColUtiCalRotInv = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butPreVen = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        butGuardarPvSug = new javax.swing.JButton();
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

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 148, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 168, 400, 20);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(24, 208, 324, 20);

        panFilRotInv.setBorder(javax.swing.BorderFactory.createTitledBorder("Rotación de inventario"));
        panFilRotInv.setLayout(null);

        jLabel1.setText("Promedio de inventario:");
        jLabel1.setToolTipText("Promedio de inventario:");
        panFilRotInv.add(jLabel1);
        jLabel1.setBounds(12, 96, 120, 20);

        cboProInv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panFilRotInv.add(cboProInv);
        cboProInv.setBounds(132, 96, 200, 20);

        chkMosColUtiCalRotInv.setText("Mostrar las columnas que se utilizan para calcular \"Rot.Inv.\"");
        chkMosColUtiCalRotInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosColUtiCalRotInvActionPerformed(evt);
            }
        });
        panFilRotInv.add(chkMosColUtiCalRotInv);
        chkMosColUtiCalRotInv.setBounds(12, 116, 400, 23);

        panFil.add(panFilRotInv);
        panFilRotInv.setBounds(4, 4, 660, 144);

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

        butGua.setText("Guardar");
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butPreVen.setText("Pre.Vta.1");
        butPreVen.setToolTipText("Asigna el valor de \"Cos.Uni.Fac.\" a \"Pre.Vta.1.\" para las filas seleccionadas.");
        butPreVen.setPreferredSize(new java.awt.Dimension(92, 25));
        butPreVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPreVenActionPerformed(evt);
            }
        });
        panBot.add(butPreVen);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        butGuardarPvSug.setToolTipText("Cierra la ventana.");
        butGuardarPvSug.setLabel("Modificar Pre. Vta.1");
        butGuardarPvSug.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardarPvSug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarPvSugActionPerformed(evt);
            }
        });
        panBot.add(butGuardarPvSug);

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

        setBounds(0, 0, 720, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        if(!isVersionActualizada())
        {
            mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
            dispose();
        }  
    }//GEN-LAST:event_formInternalFrameOpened

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
    
    private boolean validaModifica()
    {
        int intNumFil, i,j;
        String strFecUltMod;
        String strMsgItm="";
        int idItemPeso=0;
        boolean blnRes=true;
        boolean validaOk =false,validaOKGP=false,validaExisP_S=false,validaOKSUG =false;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
////                stm=conGP.createStatement();
////////                con.setAutoCommit(false);
////                conGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
////                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
////                if (datFecAux==null)
////                    return false;
////                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
                for (j=0; j<intNumFil;j++)
                {
                    if(objTblMod.isChecked(j,INT_TBL_DAT_CHK_PREV_SUG))
                    {
                        if (consultaDatosIngresados(objUti.codificar(objTblMod.getValueAt(j,INT_TBL_DAT_COD_ALT_2),1),objUti.parseDouble(objTblMod.getValueAt(j,INT_TBL_DAT_PRE_VTA1)),objUti.parseDouble(objTblMod.getValueAt(j,INT_TBL_DAT_PRE_SUG)))==true)
                            validaOKSUG=true;
                        break;
////                            mostrarMsgInf("NO SE REALIZO LA EJECUCION DEL PROCESO");
                    }
                }
                
                if (validaOKSUG==true)
                {
                         strMsgItm="<HTML>Los siguientes items tienen modificado el precio de venta con respecto al que se encuentra en la pantalla<BR><BR>";
                         strMsgItm+="<table BORDER=1><tr><td> ITEM </td> <td> PRE_ACTUAL </td><td> PRE_SUG_ACTUAL </td><td> PRE_MODIFICADO</td>";
                         for (int k=0; k<arlDatITEMSelecc.size();k++) 
                        { 
                                strMsgItm+="<tr><td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_ITM) + "</td>";
                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_A) + "</td>";
                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_S) + "</td>";
                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_M) + "</td>";
                                strMsgItm+="</tr>";
                        }
                        strMsgItm+="</table><BR> <html>";
                        strMsgItm+="No se puede realizar esta operación. ";
                        if(mostrarMsgCon(strMsgItm)==0)
                            validaOKSUG=true;
                        else
                            validaOKSUG=false;
                }
////                stm.close();
////////                con.close();
////                stm=null;
////                con=null;
                
                conGP.close();
                conGP=null;
                
////                datFecAux=null;
////                //Inicializo el estado de las filas.
////                objTblMod.initRowsState();
////                objTblMod.setDataModelChanged(false);
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
    
    
       
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
////        if (objTblMod.isDataModelChanged())
////        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
////                if(validaModifica())
////                {
                    if (actualizarDet())
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    else
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
////                }
                
////                if (ModificacionPrecioVta())
////                {
////                    mostrarMsgInf("La operación MODIFICAR se realizó con éxito.");
////
////                    if (butCon.getText().equals("Consultar"))
////                    {
////                        blnCon=true;
////                        if (objThrGUI==null)
////                        {
////                            objThrGUI=new ZafThreadGUI();
////                            objThrGUI.start();
////                        }            
////                    }
////                    else
////                        blnCon=false;
////                }
////                else
////                    mostrarMsgErr("Ocurrió un error al realizar la operación MODIFICAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");

            }
////        }
////        else
////            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            objVenConItm01.limpiar();
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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butPreVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPreVenActionPerformed
        int intFilSel[], i;
        if (mostrarMsgCon("<HTML>¿Está seguro que desear asignar <FONT COLOR=\"blue\">Cos.Uni.Fac.</FONT> a <FONT COLOR=\"blue\">Pre.Vta.1.</FONT>?<BR>Sólo se asignarán los valores en las filas seleccionadas.")==0)
        {
            intFilSel=tblDat.getSelectedRows();
            for (i=0; i<intFilSel.length; i++)
            {
                if (objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI_FAC))!=0)
                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI_FAC), intFilSel[i], INT_TBL_DAT_PRE_VTA1);
            }
            //Seleccionar la fila donde se encontró el valor buscado.
            tblDat.setRowSelectionInterval(intFilSel[0], intFilSel[intFilSel.length-1]);
            //Ubicar el foco en la fila seleccionada.
            tblDat.changeSelection(intFilSel[0], INT_TBL_DAT_PRE_VTA1, true, true);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butPreVenActionPerformed

    private void chkMosColUtiCalRotInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosColUtiCalRotInvActionPerformed
        mostrarColUtiCalRotInv();
    }//GEN-LAST:event_chkMosColUtiCalRotInvActionPerformed

    private void butGuardarPvSugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarPvSugActionPerformed
////        if (objTblMod.isDataModelChanged())
////        {
////            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
////            {
////                if (ModificacionPrecioVta())
////                    {
////                        mostrarMsgInf("La operación MODIFICAR se realizó con éxito.");
////                        
////                        if (butCon.getText().equals("Consultar"))
////                        {
////                            blnCon=true;
////                            if (objThrGUI==null)
////                            {
////                                objThrGUI=new ZafThreadGUI();
////                                objThrGUI.start();
////                            }            
////                        }
////                        else
////                            blnCon=false;
////                    }
////                else
////                    mostrarMsgErr("Ocurrió un error al realizar la operación MODIFICAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
////            }
////        }
////        else
////            mostrarMsgInf("No ha realizado ningún cambio que se pueda modificar.");

        // TODO add your handling code here:
    }//GEN-LAST:event_butGuardarPvSugActionPerformed
    
    private String BusquedaItemGP(String codigo3L)//chiqui
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="",strNom1Zafiro="",strNom2Zafiro="",strProReGP="";
        String cod_item="-1";
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT TRM(ITEMNMBR) AS ITEMGP \n";
              strSqlZa+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101   \n";
              strSqlZa+=" WHERE ITMGEDSC ='"+codigo3L  +"' \n";
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              
              while(rstLoc1.next())
              {
                cod_item =rstLoc1.getString("ITEMGP");;
                break;
              }
              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
              
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
            
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR BusquedaItemZafiro "+ e );
         }
        return cod_item;
    }
    
    private boolean actualizarCostoPrecioVtaSugGP(java.sql.Connection conn, int i,String fecha)//chiqui
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc1;
        int intNumSeg=0;
        String ndItemGp ="";
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                
////                ndItemGp =BusquedaItemGP(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_2)));
////                if (ndItemGp.equals("-1"))
////                    blnRes=false;
////                else
////                {
                    strSQL="";
                    strSQL+="UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00108";
                    strSQL+=" SET UOMPRICE=" + objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_PRE_SUG).toString())+"";
                    strSQL+=" WHERE ITEMNMBR='" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3) + "' and PRCLEVEL ='GENERAL' ";
                    
                    strSQL+=";";
                    strSQL+=" UPDATE Dynamics2021..tbm_inv";
                    strSQL+=" set  nd_preVta1=" + objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_PRE_SUG))+",fe_ultmod ='" + fecha + "' ,tx_usrmod ='"+ objParSis.getNombreUsuario() +"'" ;
                    strSQL+=" WHERE tx_codalt2 ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_2),1) +" ";
                    strSQL+=";";
                
                    System.out.println("UPDATE " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
    
    
    
////    private boolean ModificacionPrecioVta()//chiqui
////    {
////            java.sql.Connection conLoc;
////        boolean blnRes=false,validaOKGP=false,validaOKZA=false;
////        try{
////            lblMsgSis.setText("Guardando datos...");
////            pgrSis.setIndeterminate(true);
////            int intNumFil=objTblMod.getRowCountTrue();
////            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            if(con!=null)
////            {
////                 con.setAutoCommit(false);
////                for (int i=0; i<intNumFil;i++)
////                {
////                    if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_PREV_SUG))
////                    {
////                        if(actualizarPrecioVtaGP())
////                            validaOKGP=true;
////                        else
////                        {
////                            validaOKGP=false;
////                            break;
////                        }
////                        
////                    }
////                }
////                    
////////                if(validaOKGP==true)
////////                {
////////                    for (int j=0; j<intNumFil;j++)
////////                    {
////////                        if(objTblMod.isChecked(j,INT_TBL_DAT_CHK_PREV_SUG))
////////                        {
////////                            if(actualizarPrecioZAF(j))
////////                               validaOKZA=true;
////////                            else
////////                            {
////////                                validaOKZA=false;
////////                                break;
////////                            }
////////                        }
////////                    }
////////                    if (validaOKZA==true)
////////                    {
////////                        con.commit();
////////                        blnRes=true;
////////                      
////////                    }
////////                    else
////////                        con.rollback();
////////                }
////////                else
////////                    con.rollback();
////                if (validaOKGP ==true)
////                {
////                    if(EjecucionFluctuaciones(con))
////                    {
////                        con.commit();
////                        blnRes=true;
////                        mostrarMsgInf("Se ha realizado con exito el proceso de FLUCTUACION sobre el precio nuevo.");
////                    }
////                    else
////                    {
////                        con.commit();
////                        blnRes=true;
////                        mostrarMsgInf("No se ha realizado con exito el proceso de FLUCTUACION sobre el precio nuevo.");
////                    }
////                        
////                }
////                else
////                {
////                    con.rollback();         
////                    blnRes=false;
////                }
////                    
////                con.close();
////                conLoc=null;
////                blnRes=false;
////            }
////            pgrSis.setIndeterminate(false);
////            ////con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
////            con.close();
////            con=null;
////            blnRes=true;
////       }
////       catch (java.sql.SQLException e) { 
////           objUti.mostrarMsgErr_F1(this, e);  
////       }
////       catch(Exception  Evt){ 
////           objUti.mostrarMsgErr_F1(this, Evt);
////       }  
////       return blnRes;
////    }
    
    private boolean ProcesoActualizarPrecioListaZAF(java.sql.Connection conn, int i)//chiqui
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;
        int intNumSeg=0;
        String ndItemGp ="";
        try
        {
            if (conn!=null)
            {
                stmLoc1=conn.createStatement();
                //Obtener la fecha del servidor.
                
////                ndItemGp =BusquedaItemGP(objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_2)));
////                if (ndItemGp.equals("-1"))
////                    blnRes=false;
////                else
////                {
                    strSQL="";
                    strSQL+="UPDATE tbm_inv";
                    strSQL+=" SET nd_preVta1=" + objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_PRE_SUG).toString())+"";

                    strSQL+=" WHERE tx_codAlt2='" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_2) + "'  ";
                    System.out.println("UPDATE " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc1.executeUpdate(strSQL);
                stmLoc1.close();
                stmLoc1=null;
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
    
    
    
    
     private boolean actualizarPrecioZAF(int indice)//chiqui
     {
        java.sql.Connection conLocZAF;  
        boolean blnRes=false;
        try{
            System.out.println("update actualizarPrecioZAF");
            conLocZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLocZAF!=null){
                conLocZAF.setAutoCommit(false);
                    if(ProcesoActualizarPrecioListaZAF(conLocZAF,indice)){
                                conLocZAF.commit();
                                blnRes=true;
                    }else{conLocZAF.rollback();}                    
                }else{conLocZAF.rollback();}     

                conLocZAF.close();
                conLocZAF=null;
        }
        catch (java.sql.SQLException e) {
            System.err.println("ERROR: " + e);
             blnRes=false;
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
             blnRes=false;
        }  
        return  blnRes;
    }
     
     
     
    
    private boolean EjecucionFluctuaciones(java.sql.Connection conExt, String items)//chiqui
    {
         boolean blnRes = true;
        java.sql.Connection con;
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
                strSql+="{call spVen24(?,?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = con.prepareCall(strSql);
                cal.setString("ITEMS",  items);//Envio de parametro
                
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butGuardarPvSug;
    private javax.swing.JButton butPreVen;
    private javax.swing.JComboBox<String> cboProInv;
    private javax.swing.JCheckBox chkMosColUtiCalRotInv;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilRotInv;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFilRotInv.add(objSelFec);
            objSelFec.setBounds(12, 20, 472, 72);
            //Configurar el combo "Estado de registro".
            vecProInv=new Vector();
            vecProInv.add("D");
            vecProInv.add("Q");
            vecProInv.add("F");
            cboProInv.removeAllItems();
            cboProInv.addItem("Semanal");
            cboProInv.addItem("Quincenal");
            cboProInv.addItem("Mensual");
            cboProInv.setSelectedIndex(2);
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItmGP01(objParSis, "a1.", optFil);
            panFil.add(objVenConItm01);
            objVenConItm01.setBounds(24, 188, 656, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            objHisTblBasDat=new ZafHisTblBasDat();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodItm.setVisible(false);
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(4652))
                butCon.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(4653))
                butGua.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(4654))
                butPreVen.setVisible(false);
            if (!objPerUsr.isOpcionEnabled(4655))
                butCer.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConUniMed();//aqui falta
            //Configurar los JTables.
            configurarTblDat();
            mostrarColUtiCalRotInv();
            butGuardarPvSug.setVisible(false);
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
            vecCab=new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_COD_ALT_3,"Cód.Alt.3");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_CHK_SER,"Servicio");
            vecCab.add(INT_TBL_DAT_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_BUT_UNI,"");
            vecCab.add(INT_TBL_DAT_STK_CON,"Stock");
            vecCab.add(INT_TBL_DAT_PRE_VTA1,"Pre.Vta.1");
            vecCab.add(INT_TBL_DAT_MAR_UTI,"Mar.Uti.");
            vecCab.add(INT_TBL_DAT_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_DAT_CAN_MAX_VEN,"Can.Máx.Ven.");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_FAC_COS_UNI,"Fac.Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_UNI_FAC,"P.V.Fac.");//se cambia el label Cos.Uni.Fac. Solicitado por LSC 22/Feb/2019
            vecCab.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,"Mar.Uti.Pre.Ven.");
            vecCab.add(INT_TBL_DAT_BUT_HIS,"");
            vecCab.add(INT_TBL_DAT_CHK_REV_TEC,"Rev.Téc.");
            vecCab.add(INT_TBL_DAT_UNI_VEN,"Uni.Ven.");
            vecCab.add(INT_TBL_DAT_INV_PRO,"Inv.Pro.");
            vecCab.add(INT_TBL_DAT_ROT_INV,"Rot.Inv.");
            vecCab.add(INT_TBL_DAT_PRE_SUG,"Pre.Sug.");
            vecCab.add(INT_TBL_DAT_CHK_PREV_SUG,"S");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PRE_VTA1, ZafTblMod.INT_COL_DBL, new Integer(0), null);
//            objTblMod.setColumnDataType(INT_TBL_DAT_MAR_UTI, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_MAX_VEN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_FAC_COS_UNI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
                        {
                            calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
                        {
                            calcularMarUtiCos();
                        }
                    }
                    else if (objTblPopMnu.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
                        {
                            calcularCosUniFac();
                        }
                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
                        {
                            calcularMarUtiCos();
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_3).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setPreferredWidth(48);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_FAC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REV_TEC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UNI_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_INV_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ROT_INV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_SUG).setPreferredWidth(60);//chiqui
            tcmAux.getColumn(INT_TBL_DAT_CHK_PREV_SUG).setPreferredWidth(47);//chiqui
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PREV_SUG).setResizable(false);//chiqui
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
            
            /*chiqui*/
            
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
            //Don Luigi solicitó el 02/Mar/2023 que se ocultara la columna "Rev.Téc." porque ya no se la está utilizando.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_REV_TEC, tblDat);
            if (objParSis.getCodigoUsuario()!=1)
            {
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (!objPerUsr.isOpcionEnabled(4654))
                {
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FAC_COS_UNI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI_FAC, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA, tblDat);
                   // butPreVen.setVisible(false);
                }
////                if (!objPerUsr.isOpcionEnabled(4654))
////                    butPreVen.setVisible(true);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_KGR, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_MAX_VEN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_REV_TEC, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI_FAC, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ROT_INV, tblDat);
            }
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            if (objParSis.getCodigoUsuario()==1)
            {
////                vecAux.add("" + INT_TBL_DAT_NOM_ITM);
////                vecAux.add("" + INT_TBL_DAT_DEC_UNI);
////                vecAux.add("" + INT_TBL_DAT_BUT_UNI);
////                vecAux.add("" + INT_TBL_DAT_PRE_VTA1);
                vecAux.add("" + INT_TBL_DAT_MAR_UTI);
////                vecAux.add("" + INT_TBL_DAT_PES_KGR);
////                vecAux.add("" + INT_TBL_DAT_CAN_MAX_VEN);
////                vecAux.add("" + INT_TBL_DAT_FAC_COS_UNI);
////                vecAux.add("" + INT_TBL_DAT_BUT_HIS);
                vecAux.add("" + INT_TBL_DAT_PRE_SUG);
            }
            else
            {
                //2960: Ficha "Reporte": Tabla->Modificar "Nombre del item".
////                if (objPerUsr.isOpcionEnabled(2960))
////                    vecAux.add("" + INT_TBL_DAT_NOM_ITM);
////                //2961: Ficha "Reporte": Tabla->Modificar "Unidad de medida".
////                if (objPerUsr.isOpcionEnabled(2961))
////                {
////                    vecAux.add("" + INT_TBL_DAT_DEC_UNI);
////                    vecAux.add("" + INT_TBL_DAT_BUT_UNI);
////                }
////                //2962: Ficha "Reporte": Tabla->Modificar "Precio de venta 1".
////                if (objPerUsr.isOpcionEnabled(2962))
////                    vecAux.add("" + INT_TBL_DAT_PRE_VTA1);
////                //2963: Ficha "Reporte": Tabla->Modificar "Margen de utilidad".
////                if (objPerUsr.isOpcionEnabled(2963))
////                    vecAux.add("" + INT_TBL_DAT_MAR_UTI);
////                //2964: Ficha "Reporte": Tabla->Modificar "Peso (Kg)".
////                if (objPerUsr.isOpcionEnabled(2964))
////                    vecAux.add("" + INT_TBL_DAT_PES_KGR);
////                //2989: Ficha "Reporte": Tabla->Modificar "Cantidad máxima para la venta".
////                if (objPerUsr.isOpcionEnabled(2989))
////                    vecAux.add("" + INT_TBL_DAT_CAN_MAX_VEN);
////                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
////                if (objPerUsr.isOpcionEnabled(2990))
////                    vecAux.add("" + INT_TBL_DAT_FAC_COS_UNI);
                if (objPerUsr.isOpcionEnabled(4654))
                {
                    vecAux.add("" + INT_TBL_DAT_PRE_SUG);
                    vecAux.add("" + INT_TBL_DAT_MAR_UTI);
                }
                                    
            }
            vecAux.add("" + INT_TBL_DAT_CHK_REV_TEC);
            vecAux.add("" + INT_TBL_DAT_CHK_PREV_SUG);//chiqui
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            
            
            
            /*chiqui*/
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_PREV_SUG).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PREV_SUG).setCellEditor(objTblCelEdiChk);
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
            /*chiqui*/
            
            
            
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_3).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLbl.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLblDer);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblDer);
            objTblCelRenLblDer.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLblDer.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_FAC).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI_PRE_VTA).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_UNI_VEN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_INV_PRO).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_ROT_INV).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PRE_SUG).setCellRenderer(objTblCelRenLblNum);
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenLblNum.setBackground(java.awt.Color.RED);//YA LO HIC
                            }
                            else
                            {
                                objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REV_TEC).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_MAR_UTI_PRE_VTA))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else
                    {
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0)
                        {
                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else
                        {
                            if (bgdMarUti.compareTo(bgdMarUtiCos)==1)
                            {
                                objTblCelRenChk.setBackground(java.awt.Color.RED);
                            }
                            else
                            {
                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_UNI;
            intColTbl[1]=INT_TBL_DAT_DEC_UNI;
            objTblCelEdiTxtVcoUniMed=new ZafTblCelEdiTxtVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMed);
            
            objTblCelEdiButVcoUniMed=new ZafTblCelEdiButVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMed);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxtPre=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellEditor(objTblCelEdiTxtPre);
            objTblCelEdiTxtPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!objParSis.getNombreEmpresa().equals("INDUSTRIAS COSENCO S.A."))
                    {
                        if (objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("L"))
                        {
                            objTblCelEdiTxtPre.setCancelarEdicion(true);
                        }
                        else
                        {
                            switch (objParSis.getCodigoUsuario())
                            {
                                case 1: //admin
                                case 24: //lsensi
                                case 122: //fruiz
                                    break;
                                case 38: //cbetancourt
                                    if (!(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("S") || objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT).toString().toUpperCase().endsWith("P")))
                                    {
                                        objTblCelEdiTxtPre.setCancelarEdicion(true);
                                    }
                                    break;
                            }

                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularMarUtiCos();
                }
            });
            
            //////////////
            objTblCelEdiTxtMargen=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellEditor(objTblCelEdiTxtMargen);
            objTblCelEdiTxtMargen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
                int intFil=-1;
                double bdeCanMargen,bdeFactor;
                double bdeDivisor,bdeDividendo,bdePrecioSugerido,bdeCosto;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    System.out.println("antes antes jm");
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
                    intFil=tblDat.getSelectedRow();
                    if(objTblMod.getValueAt(intFil, INT_TBL_DAT_MAR_UTI)==null){
                        bdeCanMargen = 0;
                    }
                    else{
                        bdeCanMargen=Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_MAR_UTI).toString());
                    }
                    
                    if(objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI)==null){
                        bdeCosto = 0;
                    }
                    else{
                        bdeCosto=Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI).toString());
                    }
                    //bdeCosto=Double.parseDouble(objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI)==null?0:objTblMod.getValueAt(intFil, INT_TBL_DAT_COS_UNI).toString());
                    System.out.println("margen: " + bdeCanMargen);
                    bdeDivisor = 1- (bdeCanMargen/100);
                    bdeDividendo = 1/ bdeDivisor;
                    bdeFactor = bdeDividendo/0.75;
                    bdePrecioSugerido = bdeCosto * bdeFactor;
                    System.out.println(bdeFactor);
                    objTblCelEdiTxtMargen.setCancelarEdicion(true);
                    objTblMod.setValueAt(bdeFactor, intFil, INT_TBL_DAT_FAC_COS_UNI);
                    objTblMod.setValueAt(bdePrecioSugerido, intFil, INT_TBL_DAT_PRE_SUG);
////                    generaDiarioAjuste();
                }
                
                
            });
            
            
            ///////////////////////////
//            
//            objTblCelEdiTxtMarUti=new ZafTblCelEdiTxt(tblDat);
//            tcmAux.getColumn(INT_TBL_DAT_MAR_UTI).setCellEditor(objTblCelEdiTxtMarUti);
//            objTblCelEdiTxtMarUti=null;
            
            objTblCelEdiTxtPesKgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgr);
            objTblCelEdiTxtPesKgr=null;
            
            objTblCelEdiTxtCanMaxVen=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_VEN).setCellEditor(objTblCelEdiTxtCanMaxVen);
            objTblCelEdiTxtCanMaxVen=null;
            
            objTblCelEdiTxtFacCosUni=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_FAC_COS_UNI).setCellEditor(objTblCelEdiTxtFacCosUni);
            objTblCelEdiTxtFacCosUni.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCosUniFac();
                }
            });
            
            objCom18_01=new ZafCom111_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objCom18_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {
                        objCom18_01.setParDlg(objParSis.getCodigoEmpresa(), Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_SIS).toString()));
                        objCom18_01.cargarReg();
                    }
                }
            });
            
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_REV_TEC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
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
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_PREV_SUG)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_PREV_SUG);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_PREV_SUG);
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
    
        private boolean consultaDatosIngresados(String codigo3l_actual, double prcioventaAc,double prcioventaSu)//chiqui
    {
        BigDecimal bgdPreVta, bgdCosUni, bgdFacCosUni,bgdPrecioSug=BigDecimal.ZERO,bgdMargen,bgdDivisor;
        String strFecDes, strFecHas;
        int intDiv; //Divisor
        boolean blnRes=true,blnExiste=false;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                strFecDes=objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                strFecHas=objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                intDiv=(objUti.getAnio(strFecHas, objParSis.getFormatoFechaBaseDatos())*12 + objUti.getMes(strFecHas, objParSis.getFormatoFechaBaseDatos()))
                     - (objUti.getAnio(strFecDes, objParSis.getFormatoFechaBaseDatos())*12 + objUti.getMes(strFecDes, objParSis.getFormatoFechaBaseDatos())) + 1;
                switch (vecProInv.get(cboProInv.getSelectedIndex()))
                {
                    case "D":
                        //D=Domingos. Se asume que cada mes tiene 4 semanas.
                        intDiv=intDiv*4;
                        break;
                    case "Q":
                        //Q=Quincena. Se asume que cada mes tiene 2 quincenas.
                        intDiv=intDiv*2;
                        break;
                    case "F":
                        //F=Fin de mes. 1 quincena por mes.
                        intDiv=intDiv*1;
                        break;
                    default:
                        //Error
                        intDiv=-1;
                        break;
                }
                //Obtener la condición.
                strConSQL="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
             
                if (chkSolStk.isSelected())
                    strConSQL+=" AND nd_stkAct>0";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT trim(a1.tx_itm) as co_itmMae, trim(a1.tx_itm) as co_itm, trim(a1.tx_codalt) as tx_codAlt, trim(a1.tx_codAlt2) as tx_codAlt2, trim(a1.tx_itm) as tx_codAltDyn, trim(a1.tx_nomItm) as tx_nomItm, \n"
                        + "     1 as co_reg, trim(a3.UOMSCHDL) as tx_desCor, 'N' as st_ser, a2.QTYONHND as nd_stkact, a4.UOMPRICE as nd_prevta1, a1.nd_marUti,a7.DATA as nd_pesItmKgr,0 as st_revTec \n";
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (objPerUsr.isOpcionEnabled(2990))
                    strSQL+=", a3.CURRCOST as nd_cosuni, a1.nd_canMaxVen, a1.nd_facCosUni \n";
                else
                    strSQL+=", NULL AS nd_cosUni, NULL AS nd_canMaxVen, NULL AS nd_facCosUni \n";
                //strSQL+=" , a5.nd_uniVen, a6.nd_invProUni, (CASE WHEN a6.nd_invProUni=0 THEN 0 ELSE ROUND(a5.nd_uniVen/a6.nd_invProUni, 2) END) AS nd_rotInv";
                strSQL+=" , a5.nd_uniVen,  a6.nd_invprouni, (CASE WHEN a6.nd_invProUni=0 THEN 0 ELSE (CASE WHEN a5.nd_uniVen IS NULL AND a6.nd_invProUni IS NOT NULL THEN 0 ELSE ROUND(a5.nd_uniVen/a6.nd_invProUni, 2) END) END) AS nd_rotInv \n";
                strSQL+=" FROM dynamics2021..tbm_inv AS a1 \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as a2 on (a1.tx_itm =a2.ITEMNMBR) \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as a3 on (a1.tx_itm =a3.ITEMNMBR) \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as a4 on (a1.tx_itm =a4.ITEMNMBR) \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..ALT_MIS_INFOADD as a7 on (a1.tx_itm =a7.MASTERID) \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                strSQL+="           SELECT  tx_itm, SUM(nd_uniVen) AS nd_uniVen \n";
                strSQL+="           FROM dynamics2021..tbm_venMenInvBod \n";
                strSQL+="           WHERE ne_ani = YEAR('" + strFecDes + "') AND ne_mes = MONTH('" + strFecHas + "' ) \n";
                strSQL+="           GROUP BY  tx_itm ) AS a5 on (a1.tx_itm = a5.tx_itm) \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                //strSQL+="           SELECT co_itm, ROUND(SUM(nd_salUni)/1, 2) AS nd_invProUni \n";
                strSQL+="           SELECT co_itm, ROUND(SUM(nd_salUni)/" + intDiv + ", 2) AS nd_invProUni \n";
                strSQL+="           FROM dynamics2021..tbm_salInvBodFecCor \n";
                strSQL+="           WHERE  (CASE WHEN 'F'='Q' AND tx_diaCor='F' THEN 'Q' ELSE tx_diaCor END)='F' AND fe_cor BETWEEN '" + strFecDes + "' AND '" + strFecHas + "' \n";
                strSQL+="           GROUP BY  co_itm) AS a6 on (a1.tx_itm = a6.co_itm) \n";
                strSQL+=" WHERE  a2.RCRDTYPE =1 and a4.PRCLEVEL ='GENERAL' "+ strConSQL +" and a1.tx_codAlt2 ="+ codigo3l_actual  +"" ;
                
                rst=stm.executeQuery(strSQL);
                lblMsgSis.setText("Consultado datos...");
                arlDatITEMSelecc= new ArrayList();
                while (rst.next())
                {
                    if(prcioventaAc == rst.getDouble("nd_prevta1"))
                    {
                        arlRegITEMSelecc = new ArrayList();
                        arlRegITEMSelecc.add(INT_TBL_MOD_ITM,rst.getString("co_itmMae"));
                        arlRegITEMSelecc.add(INT_TBL_MOD_PRE_A,prcioventaAc);
                        arlRegITEMSelecc.add(INT_TBL_MOD_PRE_S,prcioventaSu);
                        arlRegITEMSelecc.add(INT_TBL_MOD_PRE_M,rst.getDouble("nd_prevta1"));
                        arlDatITEMSelecc.add(arlRegITEMSelecc);
                        blnRes=true;
                    }
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        BigDecimal bgdPreVta, bgdCosUni, bgdFacCosUni,bgdPrecioSug,bgdMargen,bgdDivisor,bgdDescto;
        String strFecDes, strFecHas;
        boolean validaPreSu=false;
        int intDiv; //Divisor
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                strFecDes=objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                strFecHas=objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
                intDiv=(objUti.getAnio(strFecHas, objParSis.getFormatoFechaBaseDatos())*12 + objUti.getMes(strFecHas, objParSis.getFormatoFechaBaseDatos()))
                     - (objUti.getAnio(strFecDes, objParSis.getFormatoFechaBaseDatos())*12 + objUti.getMes(strFecDes, objParSis.getFormatoFechaBaseDatos())) + 1;
                switch (vecProInv.get(cboProInv.getSelectedIndex()))
                {
                    case "D":
                        //D=Domingos. Se asume que cada mes tiene 4 semanas.
                        intDiv=intDiv*4;
                        break;
                    case "Q":
                        //Q=Quincena. Se asume que cada mes tiene 2 quincenas.
                        intDiv=intDiv*2;
                        break;
                    case "F":
                        //F=Fin de mes. 1 quincena por mes.
                        intDiv=intDiv*1;
                        break;
                    default:
                        //Error
                        intDiv=-1;
                        break;
                }
                //Obtener la condición.
                strConSQL="";
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
             
////////             

            if (chkSolStk.isSelected())
                    strConSQL+=" AND a2.QTYONHND>0";
              

                strSQL="";
                strSQL+="  SELECT trim(a1.ITEMNMBR) as co_itmMae, trim(a1.ITEMNMBR) as co_itm, trim(a1.ITEMNMBR) as tx_codAlt, trim(a1.ITMGEDSC) as tx_codAlt2, trim(a1.ITEMNMBR) as tx_codAltDyn, trim(a1.ITEMDESC) as tx_nomItm, \n"
                        + "     1 as co_reg, trim(a1.UOMSCHDL) as tx_desCor, 'N' as st_ser, a2.QTYONHND as nd_stkact,a4.UOMPRICE as nd_prevta1, a3.nd_marUti,a7.DATA as nd_pesItmKgr,0 as st_revTec  \n";
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
////                if (objPerUsr.isOpcionEnabled(2990))
                    strSQL+=", a1.CURRCOST as nd_cosuni, a3.nd_canMaxVen, a3.nd_facCosUni  \n";
////                else
////                    strSQL+=", NULL AS nd_cosUni, NULL AS nd_canMaxVen, NULL AS nd_facCosUni \n";
                //strSQL+=" , a5.nd_uniVen, a6.nd_invProUni, (CASE WHEN a6.nd_invProUni=0 THEN 0 ELSE ROUND(a5.nd_uniVen/a6.nd_invProUni, 2) END) AS nd_rotInv";
                strSQL+=" , a5.nd_uniVen,  a6.nd_invprouni, (CASE WHEN a6.nd_invProUni=0 THEN 0 ELSE (CASE WHEN a5.nd_uniVen IS NULL AND a6.nd_invProUni IS NOT NULL THEN 0 ELSE ROUND(a5.nd_uniVen/a6.nd_invProUni, 2) END) END) AS nd_rotInv, a4.PRCLEVEL,a1.ITMGEDSC  \n";
                //strSQL+=" FROM dynamics2021..tbm_inv AS a1 \n";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1 \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as a2 on (a1.ITEMNMBR =a2.ITEMNMBR) \n";
                strSQL+="       left OUTER join dynamics2021..tbm_inv as a3 on (a1.ITEMNMBR =a3.tx_itm) \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as a4 on (a1.ITEMNMBR =a4.ITEMNMBR) \n";
                strSQL+="       left OUTER join "+ objParSis.getNombreBaseDatosGP()  +"..ALT_MIS_INFOADD as a7 on (a1.ITEMNMBR =a7.MASTERID) \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                strSQL+="           SELECT  tx_itm, SUM(nd_uniVen) AS nd_uniVen \n";
                strSQL+="           FROM dynamics2021..tbm_venMenInvBod \n";
                strSQL+="           WHERE ne_ani = YEAR('" + strFecDes + "') AND ne_mes = MONTH('" + strFecHas + "' ) \n";
                strSQL+="           GROUP BY  tx_itm ) AS a5 on (a1.ITEMNMBR = a5.tx_itm)  \n";
                strSQL+=" LEFT OUTER JOIN ( \n";
                //strSQL+="           SELECT co_itm, ROUND(SUM(nd_salUni)/1, 2) AS nd_invProUni \n";
                strSQL+="           SELECT co_itm, ROUND(SUM(nd_salUni)/" + intDiv + ", 2) AS nd_invProUni \n";
                strSQL+="           FROM dynamics2021..tbm_salInvBodFecCor \n";
                strSQL+="           WHERE  (CASE WHEN 'F'='Q' AND tx_diaCor='F' THEN 'Q' ELSE tx_diaCor END)='F' AND fe_cor BETWEEN '" + strFecDes + "' AND '" + strFecHas + "' \n";
                strSQL+="           GROUP BY  co_itm) AS a6 on (a1.ITEMNMBR = a6.co_itm) \n";
                strSQL+=" WHERE  a2.RCRDTYPE =1  and a4.PRCLEVEL ='GENERAL'  "+ strConSQL +"" ;
                strSQL+=" ORDER BY   a1.ITMGEDSC desc" ;
                
                
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    validaPreSu=false;
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_2,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_3,rst.getString("tx_codAltDyn"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_COD_UNI,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_BUT_UNI,null);
                        vecReg.add(INT_TBL_DAT_STK_CON,rst.getString("nd_stkAct"));
                        bgdPreVta=rst.getBigDecimal("nd_preVta1");
                        vecReg.add(INT_TBL_DAT_PRE_VTA1,bgdPreVta);
                        vecReg.add(INT_TBL_DAT_MAR_UTI,rst.getString("nd_marUti"));
                        vecReg.add(INT_TBL_DAT_PES_KGR,rst.getString("nd_pesItmKgr"));
                        vecReg.add(INT_TBL_DAT_CAN_MAX_VEN,rst.getString("nd_canMaxVen"));
                        bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        
                        
                        //////////////////////////
                        BigDecimal bdeDivisor,bdeDividendo;
                        System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
                        

////                    System.out.println("margen: " + bdeCanMargen);
////                    bdeDivisor = 1- (bdeCanMargen/100);
////                    bdeDividendo = 1/ bdeDivisor;
////                    bdeFactor = bdeDividendo/0.75;
////                    bdePrecioSugerido = bdeCosto * bdeFactor;
////                    System.out.println(bdeFactor);
////                    objTblCelEdiTxtMargen.setCancelarEdicion(true);
////                    objTblMod.setValueAt(bdeFactor, intFil, INT_TBL_DAT_FAC_COS_UNI);
////                    objTblMod.setValueAt(bdePrecioSugerido, intFil, INT_TBL_DAT_PRE_SUG);
                    
                        
                        /////////////////////////
                        
                        bgdFacCosUni=rst.getBigDecimal("nd_facCosUni");
                        bgdMargen =rst.getBigDecimal("nd_marUti");
////                        System.out.println("costo unitario " + bgdCosUni);
////                         System.out.println("margen " + bgdMargen);
////                        BigDecimal bgdValorDescto = new BigDecimal("0.75");
                        
                        bgdPrecioSug = BigDecimal.ZERO;
                        
                         if ((bgdCosUni==null || bgdMargen==null) || (bgdCosUni.compareTo(BigDecimal.ZERO)==0) || (bgdMargen.compareTo(BigDecimal.ZERO)==0))
                        {
                            bgdPrecioSug=null;
                            validaPreSu=false;
                            
                        }
                         else
                         {
                            bdeDivisor = (   (new BigDecimal("1")).subtract(   bgdMargen.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)));
                            System.out.println("margen_" + bgdMargen);
                            System.out.println("divisor_" + bdeDivisor);
                            if (bdeDivisor.compareTo(BigDecimal.ZERO) == 0)
                            {
                                bdeDividendo=BigDecimal.ZERO;
                                bgdFacCosUni=BigDecimal.ZERO;
                                bgdFacCosUni=BigDecimal.ZERO;
                                validaPreSu=false;
                            }
                            else
                            {
                                bdeDividendo = (   (new BigDecimal("1")).divide(bdeDivisor, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));  
                                bgdFacCosUni = bdeDividendo.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                bgdPrecioSug= bgdCosUni.multiply(bgdFacCosUni);
                                validaPreSu=true;
                            }
                            
                            
                            System.out.println("diviso__" + bdeDivisor);
                            System.out.println("dividendo_" + bdeDividendo);
                            
                            System.out.println("dividendo___" + bdeDividendo);
                            System.out.println("bgdFacCosUni" + bgdFacCosUni);                            
                            
////
////                            if (bgdPrecioSug.compareTo(BigDecimal.ZERO) == 0)
////                                  
////                            else
                                  

                         }
                                                
                        

                        
  
                         
                        
                        vecReg.add(INT_TBL_DAT_COS_UNI,bgdCosUni);
                        vecReg.add(INT_TBL_DAT_FAC_COS_UNI,bgdFacCosUni);
////                      
                        
                        
                        
                        
                        if (bgdCosUni==null || bgdFacCosUni==null)
                            vecReg.add(INT_TBL_DAT_COS_UNI_FAC,null);
                        else
                            vecReg.add(INT_TBL_DAT_COS_UNI_FAC,objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesMostrar()));
                            
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
                        {
                            vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,null);
                        }
                        else
                        {
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                            {
                                vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,null);
                            }
                            else
                            {
                                bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                vecReg.add(INT_TBL_DAT_MAR_UTI_PRE_VTA,objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar()));
                            }
                        }
                        
                        vecReg.add(INT_TBL_DAT_BUT_HIS,null);
                        vecReg.add(INT_TBL_DAT_CHK_REV_TEC,rst.getString("st_revTec")==null?null:Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_UNI_VEN, rst.getString("nd_uniVen"));
                        vecReg.add(INT_TBL_DAT_INV_PRO, rst.getString("nd_invProUni"));
                        vecReg.add(INT_TBL_DAT_ROT_INV, rst.getString("nd_rotInv"));
                        vecReg.add(INT_TBL_DAT_PRE_SUG, bgdPrecioSug);//CHIQUI
 //                       if (validaPreSu==true)
                            vecReg.add(INT_TBL_DAT_CHK_PREV_SUG,validaPreSu);//CHIQUI
////                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_PREV_SUG);//CHIQUI
                     //  else
//                             vecReg.setElementAt(new Boolean(false),INT_TBL_DAT_CHK_PREV_SUG);//CHIQUI
//////                            vecReg.add(INT_TBL_DAT_CHK_PREV_SUG,Boolean.FALSE);//CHIQUI


////                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
////                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
////                        if((!strAniSel.equals("")) && (!strMesSel.equals(""))){
////                            vecRegHis.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
////                            vecRegHis.setElementAt("M", INT_TBL_DAT_HIS_LIN);
////                        }
                        
                        
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
                if (blnCon)
                {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    blnMarTodChkTblEmp=false;
                }
                    
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
    
    
    private int getObtenerIdPesoGP(String item,java.sql.Connection conExt)//chiqui
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        int blnRes=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT Extender_Record_ID ";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 ";
                strSQL+=" WHERE Extender_Key_Values_1 ="+ item +"";
                strSQL+=" AND Extender_Window_ID = 'MSW_ADI_IV'";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    blnRes=rstLoc.getInt("Extender_Record_ID");
                    break;
                }
                rstLoc.close();
                stmLoc.close();
                //conGP.close();
                rstLoc=null;
                stmLoc=null;
                //conGP=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    private boolean insertarModificarPesoGP(java.sql.Connection conExt, String item,int idItem,double  peso)//chiqui
    {
        
        java.sql.Statement stmGP;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSql ="";
        int intCodRes=-1;
        
        boolean blnRes=true;
        try
        {
            //conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
             
            if (conExt!=null)
            {
                stmGP = conExt.createStatement();
                System.out.println("VAAAAAMMMOOOOSSSS ");
////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_INSER_MODI_PESO(?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("ITEM",  item);//Envio de parametro
                cal.setDouble("PESO",  peso);//Envio de parametro
                
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
              
                cal.close();
                cal=null;
                 
                stmGP.close();
                stmGP=null;
                
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
    
    private boolean modificarPrecioCosto(java.sql.Connection  conExt,String item,String descripcion,double precio,double costo)//chiqui
    {
        java.sql.Statement stmGP;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSql ="";
        int intCodRes=-1;
        boolean blnRes=true;
 
        
        try
        {
////         conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
           if (conExt!=null)
           {
//           
                stmGP=conExt.createStatement();
                strSQL ="";
                strSQL+="UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101";
                strSQL+=" SET ITEMDESC= " + descripcion +" , CURRCOST = "+ costo +"";
////                        strSQL+=", CURRCOST=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
                strSQL+=" WHERE ITEMNMBR='"+ item +"'";
                strSQL+=";";
                
                strSQL+="UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00108";
                strSQL+=" SET UOMPRICE= " + precio +"";
                strSQL+=" WHERE ITEMNMBR='"+ item +"'";  
                strSQL+=" AND  PRCLEVEL='GENERAL'";                        
                strSQL+=";";

                System.out.println("UPDATE " + strSQL);
                stmGP.executeUpdate(strSQL);
                stmGP.close();
                stmGP=null;

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
    private boolean actualizarDet()
    {
        int intNumFil, i,j;
        String strFecUltMod;
        String strMsgItm="";
        int idItemPeso=0;
        boolean blnRes=true;
        String items="",item="";
        boolean validaOk =false,validaOKGP=false,validaExisP_S=false,validaOKSUG =false;
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conGP!=null)
            {
                stm=conGP.createStatement();
////                con.setAutoCommit(false);
                conGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
////                for (j=0; j<intNumFil;j++)
////                {
////                    if(objTblMod.isChecked(j,INT_TBL_DAT_CHK_PREV_SUG))
////                    {
////                        if (consultaDatosIngresados(objUti.codificar(objTblMod.getValueAt(j,INT_TBL_DAT_COD_ALT_2),1),objUti.parseDouble(objTblMod.getValueAt(j,INT_TBL_DAT_PRE_VTA1)),objUti.parseDouble(objTblMod.getValueAt(j,INT_TBL_DAT_PRE_SUG)))==true)
////                            validaOKSUG=true;
////////                            mostrarMsgInf("NO SE REALIZO LA EJECUCION DEL PROCESO");
////                    }
////                }
////                
////                if (validaOKSUG==true)
////                {
////                         strMsgItm="<HTML>Los siguientes items tienen modificado el precio de venta con respecto al que se encuentra en la pantalla<BR><BR>";
////                         strMsgItm+="<table BORDER=1><tr><td> ITEM </td> <td> PRE_ACTUAL </td><td> PRE_SUG_ACTUAL </td><td> PRE_MODIFICADO</td>";
////                         for (int k=0; k<arlDatITEMSelecc.size();k++) 
////                        { 
////                                strMsgItm+="<tr><td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_ITM) + "</td>";
////                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_A) + "</td>";
////                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_S) + "</td>";
////                                strMsgItm+="<td>" + objUti.getStringValueAt(arlDatITEMSelecc, k, INT_TBL_MOD_PRE_M) + "</td>";
////                                strMsgItm+="</tr>";
////                        }
////                        strMsgItm+="</table><BR> <html>";
////                        strMsgItm+="No se puede realizar esta operación. ";
////                        if(mostrarMsgCon(strMsgItm)==0)
////                            validaOKSUG=true;
////                        else
////                            validaOKSUG=false;
////                }
                
                    double precioSug =0;
                    for (i=0; i<intNumFil;i++)
                    {
                        validaExisP_S=false;
                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_PREV_SUG))
                        {
                            if (objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_PRE_SUG)) !=0)
                            {
                                    ////                            if((actualizarCostoPrecioVtaSugGP(conGP,i)) && ((objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))))
                                if(actualizarCostoPrecioVtaSugGP(conGP,i,strFecUltMod))
                                {
                                    validaOKGP=true;
                                    item =objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_3).toString();
                                    items =item +','+ items;
                                }

                                else
                                {
                                    validaOKGP=false;
                                    blnRes=false;
                                    break;
                                }
                                validaExisP_S=true;
                            }
  

                        }
                    }
                    if  (validaOKGP==true)
                    {
                       if(EjecucionFluctuaciones(conGP,items))
                       {
                           //mostrarMsgInf("Se ha realizado con exito el proceso de FLUCTUTACION sobre el precio nuevo.");
    ////                       con.commit();
                           
                            if (InsertarHistorico(conGP,strFecUltMod,objParSis.getNombreUsuario()))
                           {
                               conGP.commit();
                               //mostrarMsgInf("Se ha realizado con exito el proceso de GUARDADO DEL HISTORICO."); 
                           }
                            else
                            {
                               mostrarMsgInf("No se ha realizado con exito el proceso de FLUCTUTACION sobre el precio nuevo."); 
                               blnRes=false;
                               conGP.rollback();
                            }
                           
////                         objHisTblBasDat.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'");
                           

                                
                       }
                       else
                       {
                           mostrarMsgInf("No se ha realizado con exito el proceso de FLUCTUTACION sobre el precio nuevo."); 
                            blnRes=false;
                           conGP.rollback();                       
                       }
                    }
                    else
                        conGP.rollback();
                        
               
                
                stm.close();
////                con.close();
                stm=null;
////                con=null;
                
                conGP.close();
                conGP=null;
                
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
    
    private boolean InsertarHistorico (java.sql.Connection conEXT,String fecha,String usuario)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="";
        try
        {
            if (conEXT!=null)
            {
                stmLoc=conEXT.createStatement();
                    strSQL="";
                    
                    strSQL+=" INSERT INTO tbh_inv\n" +
"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk) \n";
                    strSQL+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.*  \n";
                    strSQL+=" FROM tbm_inv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
                    strSQL+=" group by tx_itm \n";
                    strSQL+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQL+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                
                    System.out.println("UPDATE " + strSQL);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
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
     * mostrar la "Unidad de medida".
     */
    private boolean configurarVenConUniMed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Siglas");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.DEX_ROW_ID as co_reg, a1.BASEUOFM as tx_descor, a1.UOMSCHDL as tx_desLar";
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV40201 AS a1";
////            strSQL+=" WHERE a1.co_grp=5 AND a1.st_reg='A'";
////            strSQL+=" ORDER BY a1.co_reg";
            vcoUniMed=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de unidades de medida", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUniMed.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoUniMed.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función calcula el costo unitario con factor de la fila.
     */
    private void calcularCosUniFac()
    {
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdFacCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_FAC_COS_UNI)));
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesMostrar()), intFilSel[i], INT_TBL_DAT_COS_UNI_FAC);
        }
    }
    
    /**
     * Esta función calcula el margen de utilidad sobre el costo de la fila.
     */
    private void calcularMarUtiCos()
    {
        int intFilSel[], i;
        BigDecimal bgdPreVta, bgdCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdPreVta=(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PRE_VTA1)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PRE_VTA1))));
            bgdCosUni=(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COS_UNI))));
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
            {
                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
            }
            else
            {
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                {
                    objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
                }
                else
                {
                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar()), intFilSel[i], INT_TBL_DAT_MAR_UTI_PRE_VTA);
                }
            }
        }
    }
    
    /**
     * Esta función muestra/oculta las columnas que se utilizan para calcular la "Rotación de inventario".
     */
    private boolean mostrarColUtiCalRotInv()
    {
        boolean blnRes=true;
        try
        {
            if (chkMosColUtiCalRotInv.isSelected())
            {
                //Mostrar columnas.
                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_UNI_VEN, tblDat);
                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_INV_PRO, tblDat);
            }
            else
            {
                //Ocultar columnas.
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_UNI_VEN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_INV_PRO, tblDat);
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
                    strMsg="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'><tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr><tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que el \"Mar.Uti.Cos.\" es menor al \"Mar.Uti.\".</td></tr></table><br></html>";
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
                case INT_TBL_DAT_COD_ALT_2:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_COD_ALT_3:
                    strMsg="Código alterno 3 del item (Código GP Altiora)";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CHK_SER:
                    strMsg="Servicio";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_BUT_UNI:
                    strMsg="Muestra el \"Listado de unidades de medida\"";
                    break;
                case INT_TBL_DAT_STK_CON:
                    strMsg="Stock";
                    break;
                case INT_TBL_DAT_PRE_VTA1:
                    strMsg="Precio de venta 1";
                    break;
                case INT_TBL_DAT_MAR_UTI:
                    strMsg="Margen de utilidad";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_CAN_MAX_VEN:
                    strMsg="Cantidad máxima para la venta";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_FAC_COS_UNI:
                    strMsg="Factor para el costo unitario";
                    break;
                case INT_TBL_DAT_COS_UNI_FAC:
                    strMsg="Costo unitario con factor";
                    break;
                case INT_TBL_DAT_MAR_UTI_PRE_VTA:
                    strMsg="<HTML>Margen de utilidad sobre el precio de venta <BR><FONT COLOR=\"blue\">(Pre.Vta.1*0.75-Cos.Uni.)/(Pre.Vta.1*0.75).</FONT></HTML>";
                    break;
                case INT_TBL_DAT_BUT_HIS:
                    strMsg="Muestra el \"Histórico de items\"";
                    break;
                case INT_TBL_DAT_CHK_REV_TEC:
                    strMsg="Revisión técnica";
                    break;
                case INT_TBL_DAT_UNI_VEN:
                    strMsg="Unidades vendidas";
                    break;
                case INT_TBL_DAT_INV_PRO:
                    strMsg="Inventario promedio";
                    break;
                case INT_TBL_DAT_ROT_INV:
                    strMsg="Rotación de inventario";
                    break;
                case INT_TBL_DAT_PRE_SUG:
                    strMsg="Precio Sugerido";
                    break;    
                case INT_TBL_DAT_CHK_PREV_SUG:
                    strMsg="Seleccion para modificar";
                    break;                     
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}