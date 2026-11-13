/*
 * ZafGenPreEmp_Adm.java
 *
 * Created on Marzo 9, 2015, 17:30 PM
 */
package Maestros.ZafMae81;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafMovIngEgrInv.ZafMovIngEgrInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author  José Marín
 */
public class ZafMae81 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_EMP=1;
    private static final int INT_TBL_DAT_COD_CLI=2;
    private static final int INT_TBL_DAT_CED_CLI=3;
    private static final int INT_TBL_DAT_NOM_CLI=4;
    private static final int INT_TBL_DAT_DIR_CLI=5;
    private static final int INT_TBL_DAT_TEL_1=6;
    private static final int INT_TBL_DAT_TEL_2=7;
    private static final int INT_TBL_DAT_TEL_3=8;
    private static final int INT_TBL_DAT_COR_ELE_FAC_ELE=9;
    private static final int INT_TBL_DAT_BTN_CIU=10;
    private static final int INT_TBL_DAT_COD_CIU=11;
    private static final int INT_TBL_DAT_NOM_CIU=12;
    private static final int INT_TBL_DAT_BTN_PAR=13;
    private static final int INT_TBL_DAT_COD_PAR=14;
    private static final int INT_TBL_DAT_NOM_PAR=15;
    private static final int INT_TBL_DAT_SEX_CLI=16;
    private static final int INT_TBL_DAT_EST_CIV=17;
    private static final int INT_TBL_DAT_ORG_ING=18;
    private static final int INT_TBL_DAT_PER_CON=19;
    private static final int INT_TBL_DAT_TEL_CON=20;
    private static final int INT_TBL_DAT_COR_ELE_CON=21;
    
    private static final int INT_TBL_DAT_COD_FOR_PAG=22;
    private static final int INT_TBL_DAT_NOM_FOR_PAG=23;
    
    
    
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
     
     
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    
    private String strSql;
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm;     //Editor: TextField en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm2;    //Editor: TextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private boolean blnHayCam;
    private Vector vecDat, vecReg, vecCab, vecAux;
     
    private String strSQL;
    private String strAux;
    private String strVersion=" v0.03";   
    
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
     
    /** Creates new form ZafGenPreEmp_Adm */
    public ZafMae81(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            initComponents();
            configurarFrm();
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    } 
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panDat = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        panTabDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnConsultar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setMinimumSize(new java.awt.Dimension(148, 113));

        panFil.setPreferredSize(new java.awt.Dimension(0, 330));
        panFil.setLayout(new java.awt.BorderLayout());

        panCab.setMinimumSize(new java.awt.Dimension(0, 0));
        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(new java.awt.BorderLayout());

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
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

        panCab.add(panLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panCab, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("Filtro", panFil);

        panDat.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

        panTabDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panTabDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panDet.add(panTabDet, java.awt.BorderLayout.CENTER);

        panDat.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Clientes/Proveedores", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("General");

        panBar.setPreferredSize(new java.awt.Dimension(0, 55));
        panBar.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        jPanel1.add(btnConsultar);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar);

        btnExit.setText("Salir");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        jPanel1.add(btnExit);

        panBar.add(jPanel1, java.awt.BorderLayout.CENTER);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                if(objThrGua==null){
                    objThrGua=new ZafMae81.ZafThrGua();
                    objThrGua.start();
                }
            }
        }
      else{
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
      }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                if (!guardarDat()){
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         

                }
                objThrGua=null;
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafThrGua objThrGua;
    
    
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        try{
            if (btnConsultar.getText().equals("Consultar")){
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafMae81.ZafThreadGUI();
                    objThrGUI.start();    
                }            
            }
            else{
                blnCon=false;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        
    }//GEN-LAST:event_btnConsultarActionPerformed

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panTabDet;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objDocLis=new ZafDocLis();
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);  //Titulo y version
            lblTit.setText(strAux);
            configurarTblDat();
        }
        catch(Exception e){       
            blnRes=false;         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_CED_CLI,"Identificacíon");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_DIR_CLI,"Dirección");
            vecCab.add(INT_TBL_DAT_TEL_1,"Telefono 1");
            vecCab.add(INT_TBL_DAT_TEL_2,"Telefono 2");
            vecCab.add(INT_TBL_DAT_TEL_3,"Telefono 3");
            vecCab.add(INT_TBL_DAT_COR_ELE_FAC_ELE,"Correo electronico");
            vecCab.add(INT_TBL_DAT_BTN_CIU,"");
            vecCab.add(INT_TBL_DAT_COD_CIU,"Cód.Ciu.");
            vecCab.add(INT_TBL_DAT_NOM_CIU,"Ciudad");
            vecCab.add(INT_TBL_DAT_BTN_PAR,"");
            vecCab.add(INT_TBL_DAT_COD_PAR,"Cód.Par.");
            vecCab.add(INT_TBL_DAT_NOM_PAR,"Parroquia");
            vecCab.add(INT_TBL_DAT_SEX_CLI,"Sexo");
            vecCab.add(INT_TBL_DAT_EST_CIV,"Est.Civ.");
            vecCab.add(INT_TBL_DAT_ORG_ING,"Org.Ing.");
            vecCab.add(INT_TBL_DAT_PER_CON,"Per.Contacto");
            vecCab.add(INT_TBL_DAT_TEL_CON,"Tel.Contacto");
            vecCab.add(INT_TBL_DAT_COR_ELE_CON,"Cor.Ele.Contacto");
             
            vecCab.add(INT_TBL_DAT_COD_FOR_PAG,"Cód.For.Pag");
            vecCab.add(INT_TBL_DAT_NOM_FOR_PAG,"Forma de pago");
             
    
              
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CED_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DIR_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TEL_1).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TEL_2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TEL_3).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COR_ELE_FAC_ELE).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_BTN_CIU).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CIU).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CIU).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BTN_PAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_PAR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_SEX_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_EST_CIV).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ORG_ING).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PER_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TEL_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COR_ELE_CON).setPreferredWidth(150);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_FOR_PAG).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_FOR_PAG).setPreferredWidth(100);
            
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_DIR_CLI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMae81.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
             
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DIR_CLI);
            vecAux.add("" + INT_TBL_DAT_TEL_1);
            vecAux.add("" + INT_TBL_DAT_TEL_2);
            vecAux.add("" + INT_TBL_DAT_TEL_3);
            vecAux.add("" + INT_TBL_DAT_COR_ELE_FAC_ELE);
            vecAux.add("" + INT_TBL_DAT_BTN_CIU);
            vecAux.add("" + INT_TBL_DAT_BTN_PAR);
            vecAux.add("" + INT_TBL_DAT_SEX_CLI);
            vecAux.add("" + INT_TBL_DAT_EST_CIV);
            vecAux.add("" + INT_TBL_DAT_ORG_ING);
            vecAux.add("" + INT_TBL_DAT_PER_CON);
            vecAux.add("" + INT_TBL_DAT_TEL_CON);
            vecAux.add("" + INT_TBL_DAT_COR_ELE_CON);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_CIU);  
            arlColHid.add(""+INT_TBL_DAT_COD_PAR);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_CIU).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BTN_PAR).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            
            configurarCiudades();
            configurarParroquias();
            configurarTblLoc();
            cargarLoc();
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_EST_CIV).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strAux="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CIV)!= null){
                        strAux = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_CIV).toString();
                        if( !( strAux.equals("S") || strAux.equals("C") || strAux.equals("D") || strAux.equals("V") || strAux.equals("U") || strAux.equals("N"))){
                            mostrarMsgInf("Dato invalido... Favor colocar S=Soltero;C=Casado;D=Divorciado;V=Viudo;U=Unión libre;N=No Aplica");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_EST_CIV);
                        }
                    }
                    
                }
            });
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_SEX_CLI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strAux="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SEX_CLI)!= null){
                        strAux = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SEX_CLI).toString();
                        if( !(strAux.equals("M") || strAux.equals("F") || strAux.equals("N"))){
                            mostrarMsgInf("Dato invalido... Favor colocar M/F; N=No aplica");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_SEX_CLI);
                        }
                    }
                    
                }
            });
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_ORG_ING).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strAux="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ORG_ING)!= null){
                        strAux = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_ORG_ING).toString();
                        if( !(strAux.equals("B") || strAux.equals("V") || strAux.equals("I") || strAux.equals("A") || 
                                strAux.equals("R") || strAux.equals("H") || strAux.equals("M") || strAux.equals("N"))){
                            mostrarMsgInf("Dato Invalido... Favor colocar B=Empleado público ; V=Empleado Privado ; I=Independiente ; A=Ama de casa o Estudiante ; R=Rentista ; H=Jubilado M=Remesas del Exterior ; N=No Aplica  ");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_ORG_ING);
                        }
                    }
                    
                        
                }
            });
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COR_ELE_FAC_ELE).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strAux="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COR_ELE_FAC_ELE)!= null){
                        strAux = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COR_ELE_FAC_ELE).toString();
                        strAux = strAux.trim();
                        if(!isCorreoElectronico( strAux ) ){
                            mostrarMsgInf("Correo invalido...");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_COR_ELE_FAC_ELE);
                        }
                    }
                    
                        
                }
            });
            
            
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TEL_1).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_TEL_2).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_TEL_3).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_TEL_CON).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strAux="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn())!= null){
                        strAux = objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString();
                        strAux = strAux.trim();
                        if(!isTelefonoValido( strAux ) ){
                            mostrarMsgInf("Telefono invalido... Formato (02-4320981) ");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                }
            });
            
            
            
            
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CIU;
            intColTbl[1]=INT_TBL_DAT_NOM_CIU;
            
            objTblCelEdiButVcoCiu=new ZafTblCelEdiButVco(tblDat, vcoCiu, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_CIU).setCellEditor(objTblCelEdiButVcoCiu);
            objTblCelEdiButVcoCiu.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_PAR;
            intColTbl[1]=INT_TBL_DAT_NOM_PAR;  
            
            objTblCelEdiButVcoPar=new ZafTblCelEdiButVco(tblDat, vcoPar, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_PAR).setCellEditor(objTblCelEdiButVcoPar);
            objTblCelEdiButVcoPar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            
            objDocLis=new ZafDocLis();
            
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);  
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        catch(Exception e){  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this, e);     
        }
        return blnRes;
    }
    
    private boolean isCorreoElectronico(String strCorEle){
        boolean blnRes=true;
        try{
            if(!(strCorEle.contains("@") && strCorEle.contains("."))){
                blnRes=false;
            }
            if(strCorEle.length()<5){
                blnRes=false;
            }
        }
        catch(Exception e){  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this, e);     
        }
        return blnRes;
    }
    
    
    private boolean isTelefonoValido(String strFono){
        boolean blnRes=true;
        try{
            if(strFono.length()<9 || strFono.length()>10){
                blnRes=false;
            }
        }
        catch(Exception e){  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(this, e);     
        }
        return blnRes;
    }
    
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoCiu;//Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco  objTblCelEdiButVcoPar;
     
    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }
 
 
    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
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
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
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
            limpiarTabla();
        }
        catch (Exception e){         
            blnRes=false;      
        }
        return blnRes;
    }
    Date dtpFechaDocumento;
 
    
    

     /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_CED_CLI:
                    strMsg="Documento identificador";
                break;
                case INT_TBL_DAT_DIR_CLI:
                    strMsg="Dirección del cliente";
                    break;
                case INT_TBL_DAT_TEL_1:
                    strMsg="Telefono 1";
                    break;
                case INT_TBL_DAT_TEL_2:
                    strMsg="Telefono 2";
                    break;
                case INT_TBL_DAT_TEL_3:
                    strMsg="Telefono 3 - LSC: solo ese se va a pasar...";
                    break;
                case INT_TBL_DAT_COR_ELE_FAC_ELE:
                    strMsg="Correo de facturacion...";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
     

    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
     
     
     
    private ZafTblMod objTblModLoc;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    
    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
             
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
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
             
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
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
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
          
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
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
    
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    
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
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
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
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
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
    private boolean cargarLoc()
    {
       
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                stmLoc=conLoc.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom ";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.st_reg IN ('A', 'P')";
                strSQL+=" ORDER BY a1.co_emp";
                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rstLoc.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
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
    
    
    private class ZafThreadGUI extends Thread{
        
        public void run(){
            java.sql.Connection conLoc;
            try{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
                if (!cargarDetReg(conLoc)){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    btnConsultar.setText("Consultar");

                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0){
                     tabFrm.setSelectedIndex(1);
                }

                conLoc.close();
                conLoc=null;
                objThrGUI=null;
            }
            catch (java.sql.SQLException e){
                System.err.println(e);
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    private boolean cargarDetReg(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strConSQL="";
        try{
            pgrSis.setIndeterminate(true);
            btnConsultar.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (conExt!=null){
                stmLoc=conExt.createStatement();
                
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModLoc.getRowCountTrue();
                int i=0;
                strAux="";
                for (int j=0; j<intNumFilTblBod; j++){
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK)){
                        if (i==0)
                            strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + ")";
                        else
                            strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + ")";
                        i++;
                    }
                }
                if (!strAux.equals("")){
                    strConSQL+=" AND (" + strAux + ")";
                }
                
                 
                strSQL=" ";
                strSQL+=" SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_tel1, a1.tx_tel2, a1.tx_tel3, a1.tx_corEleFacEle,  \n";
                strSQL+="        a1.co_ciu, TRANSLATE(a2.tx_desLar ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜ','aeiouAEIOUaeiouAEIOU') as tx_ciudad,    \n";
                strSQL+="        a1.co_par, TRANSLATE(a4.tx_desLar ,'áéíóúÁÉÍÓÚäëïöüÄËÏÖÜ','aeiouAEIOUaeiouAEIOU') as tx_parroquia, \n";
                strSQL+="        a1.st_sex, a1.st_estciv, a1.st_oriIng, a1.tx_perCon, a1.tx_telCon, a1.tx_corEleCon , a5.co_forPag, a5.tx_des as tx_desForPag   \n";
                strSQL+=" FROM tbm_cli as a1   \n"; 
                strSQL+=" INNER JOIN tbm_cabForPag as a5 ON (a1.co_emp=a5.co_emp AND a1.co_forPag=a5.co_forPag) \n ";
                strSQL+=" INNER JOIN (  \n";
                strSQL+="       SELECT a1.co_emp, a1.co_cli  \n";
                strSQL+="       FROM(  \n";
                strSQL+="           SELECT a1.co_emp, a1.co_cli /* Debe tener saldo en cartera.  */  \n";
                strSQL+="           FROM (  \n";
                strSQL+="               SELECT  a3.co_emp, a3.co_cli, a3.tx_ide, a3.tx_nom, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal   \n";
                strSQL+="               FROM tbm_cabMovInv AS a1  \n";
                strSQL+="               INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                strSQL+="               INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)  \n";
                strSQL+="               WHERE a3.st_cli='S'  "+strConSQL+" AND a1.st_reg IN ('A','R','C','F') AND  \n";
                strSQL+="                     a2.st_reg IN ('A','C')   \n";
                strSQL+="               GROUP BY a3.co_emp, a3.co_cli, a3.tx_ide, a3.tx_nom   \n";
                strSQL+="               HAVING SUM(a2.mo_pag+a2.nd_abo)<>0   \n";
                strSQL+="               ORDER BY a3.tx_nom  \n";
                strSQL+="           ) as a1   \n";
                strSQL+="           UNION   \n";
                strSQL+="           SELECT a1.co_emp, a1.co_cli /* Debe tener alguna factura desde el 1 de enero de 2017. */  \n";
                strSQL+="           FROM tbm_cabMovInv as a1   \n";
                strSQL+="           INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                strSQL+="           WHERE a1.co_tipDOc = 228 "+strConSQL+"  AND a1.fe_doc >= '2017-01-01' and a2.st_reg IN ( 'A', 'N') \n";
                strSQL+="           GROUP BY a1.co_emp, a1.co_cli   \n";
                strSQL+="           UNION \n";
                strSQL+="           SELECT a1.co_emp, a1.co_cli /* Debe tener al menos alguna cotización desde 1 de enero de 2018 */ \n";
                strSQL+="           FROM tbm_cabCotVen as a1   \n";
                strSQL+="           INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                strSQL+="           WHERE a1.fe_cot >= '2018-01-01' "+strConSQL+" and a2.st_reg IN ( 'A', 'N')  \n";
                strSQL+="           GROUP BY a1.co_emp, a1.co_cli   \n";
                strSQL+="       ) as a1  \n";
                strSQL+="       GROUP BY a1.co_emp, a1.co_cli \n";
                strSQL+="       ORDER BY a1.co_emp, a1.co_cli \n";
                strSQL+=" ) as condiciones ON (a1.co_emp=condiciones.co_emp AND a1.co_cli=condiciones.co_cli) \n";
                strSQL+=" LEFT OUTER JOIN tbm_ciu as a2 ON (a1.co_ciu=a2.co_ciu) \n";
                strSQL+=" LEFT OUTER JOIN tbm_par as a4 ON (a1.co_par=a4.co_par) \n";
                strSQL+=" WHERE a1.st_cli = 'S' AND a1.tx_tipIde IN ('C', 'R') "+strConSQL+" AND   \n";
                strSQL+=" /*  (CASE WHEN LENGTH(a1.tx_ide) = 10 OR  LENGTH(a1.tx_ide) = 13 THEN TRUE ELSE FALSE END)    */ \n";
                strSQL+="       (a1.tx_corEleFacEle IS NULL OR a1.st_sex IS NULL or a1.st_estCiv IS NULL or a1.st_oriIng IS NULL OR a1.co_ciu IS NULL OR a1.co_par IS NULL  OR  \n";
//                strSQL+="       ( length(trim(from  replace(a1.tx_tel1, ' ', '') ) ) < 9 OR length(trim(from  replace(a1.tx_tel1, ' ', '') ) ) > 10  ) or  \n";
//                strSQL+="       ( length(trim(from  replace(a1.tx_tel2, ' ', '') )) < 9 OR length(trim(from  replace(a1.tx_tel2, ' ', '') ) ) > 10 ) or  \n";
                strSQL+="       ( length(trim(from  replace(a1.tx_tel3, ' ', '') )) < 9 OR length(trim(from  replace(a1.tx_tel3, ' ', '') ) ) > 10 )  	) \n";
                strSQL+=" ORDER BY a1.co_emp, a1.co_cli \n";
                strSQL+=" \n";
                System.out.println("cargarDetalle \n" + strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rstLoc.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rstLoc.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_CED_CLI,rstLoc.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rstLoc.getString("tx_nom"));
                        
                        vecReg.add(INT_TBL_DAT_DIR_CLI,rstLoc.getString("tx_dir"));
                        vecReg.add(INT_TBL_DAT_TEL_1,rstLoc.getString("tx_tel1"));
                        vecReg.add(INT_TBL_DAT_TEL_2,rstLoc.getString("tx_tel2"));
                        vecReg.add(INT_TBL_DAT_TEL_3,rstLoc.getString("tx_tel3"));
                        vecReg.add(INT_TBL_DAT_COR_ELE_FAC_ELE,rstLoc.getString("tx_corEleFacEle"));
                        vecReg.add(INT_TBL_DAT_BTN_CIU,"");
                        vecReg.add(INT_TBL_DAT_COD_CIU,rstLoc.getString("co_ciu"));
                        vecReg.add(INT_TBL_DAT_NOM_CIU,rstLoc.getString("tx_ciudad"));
                        vecReg.add(INT_TBL_DAT_BTN_PAR,"");
                        vecReg.add(INT_TBL_DAT_COD_PAR,rstLoc.getString("co_par"));
                        vecReg.add(INT_TBL_DAT_NOM_PAR,rstLoc.getString("tx_parroquia"));
                        vecReg.add(INT_TBL_DAT_SEX_CLI,rstLoc.getString("st_sex"));
                        vecReg.add(INT_TBL_DAT_EST_CIV,rstLoc.getString("st_estciv"));
                        vecReg.add(INT_TBL_DAT_ORG_ING,rstLoc.getString("st_oriIng"));
                        vecReg.add(INT_TBL_DAT_PER_CON,rstLoc.getString("tx_perCon"));
                        vecReg.add(INT_TBL_DAT_TEL_CON,rstLoc.getString("tx_telCon"));
                        vecReg.add(INT_TBL_DAT_COR_ELE_CON,rstLoc.getString("tx_corEleCon"));
                        vecReg.add(INT_TBL_DAT_COD_FOR_PAG,rstLoc.getString("co_forPag"));
                        vecReg.add(INT_TBL_DAT_NOM_FOR_PAG,rstLoc.getString("tx_desForPag"));
                        vecDat.add(vecReg);
                    }
                    else{
                        break;
                    }
                }
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                 
                if(blnCon){
                         lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                 }
                else{
                     lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                rstLoc.close();
                stmLoc.close();
                rstLoc=null;
                stmLoc=null;
                btnConsultar.setText("Consultar");
                pgrSis.setIndeterminate(false);     
                     
                }
                
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
    
    ZafVenCon vcoCiu;
    ZafVenCon vcoPar;
      
    /*
     * llenar la consulta de Ciudades  
     */
    
    private boolean configurarCiudades()
    {
        boolean blnRes=true;
        try
        {
            System.out.println("configurarCiudades::....");
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ciu");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_ciu, a1.tx_deslar";
            strSQL+=" FROM tbm_ciu a1 WHERE a1.st_reg = 'A' ";
            strSQL+=" ORDER BY a1.co_ciu";
            System.out.println("configurarCiudades:.." + strSQL);
            vcoCiu=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ciudades", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCiu.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     
    /*
     * llenar la consulta de Ciudades  
     */
    
    private boolean configurarParroquias()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_par");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_par, a1.tx_deslar";
            strSQL+=" FROM tbm_par a1 WHERE a1.st_reg = 'A' ";
            strSQL+=" ORDER BY a1.co_par";
            System.out.println("configurarParroquia:.." + strSQL);
            vcoPar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Parroquias", strSQL, arlCam, arlAli, arlAncCol);        
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
     /**
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
      
    
    
      
    /**
     * 
     * @return 
     */
    private boolean guardarDat(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conLoc.setAutoCommit(false);
            if(guardarDet(conLoc)){
                conLoc.commit();
                blnRes=true;
                mostrarMsgInf("Cambios guardados con exito");
                btnConsultar.doClick();
            }
            else{
                conLoc.rollback();
                mostrarMsgInf("Error al guardar");
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDet(java.sql.Connection conExt){
        int intNumFil;
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try{
            if (conExt!=null){
                stmLoc=conExt.createStatement();
                intNumFil=objTblMod.getRowCountTrue();
                strSql="";
                for (int intFil=0; intFil<intNumFil;intFil++){/*JM: Todas las filas */
                    if (objUti.parseString(objTblMod.getValueAt(intFil,0)).equals("M")){/*JM: Las filas modificadas */
                        strSql+=" UPDATE tbm_cli set tx_dir = "+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_DIR_CLI))+", ";
                        strSql+=" tx_tel1="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_1))+",  ";
                        strSql+=" tx_tel2="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_2))+",  ";
                        strSql+=" tx_tel3="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_3))+",  ";
                        strSql+=" tx_corEleFacEle="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COR_ELE_FAC_ELE))+",  ";
                        strSql+=" co_ciu="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CIU), 2)+",  ";
                        strSql+=" co_par="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_PAR), 2)+",  ";
                        strSql+=" st_sex="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_SEX_CLI))+",  ";
                        strSql+=" st_estCiv="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_CIV))+",  ";
                        strSql+=" st_oriIng="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_ORG_ING))+",  ";
                        strSql+=" tx_perCon="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_PER_CON))+",  ";
                        strSql+=" tx_telCon="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_CON))+",  ";
                        strSql+=" tx_CorEleCon="+objUti.codificar(objTblMod.getValueAt(intFil, INT_TBL_DAT_COR_ELE_CON))+", \n";
//                        strSql+=" tx_tel='"+objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_1)+objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_2)+objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_3)+"', ";
                        strSql+=" co_usrMod="+objParSis.getCodigoUsuario()+", ";
                        strSql+=" fe_ultMod=CURRENT_TIMESTAMP ";
                        
                        if(validacionAltaClientes(intFil)){
                            strSql+=" , st_reg='A' ";
                        }
                       
                        strSql+=" WHERE co_emp="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP)+" AND co_cli="+objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CLI)+" ; \n";
                        strSql+=" ";
                        strSql+=  " INSERT INTO tbh_cli(  co_emp, co_cli, co_his, st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir, "
                            + " tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu, "
                            + " co_par,co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag, "
                            + " nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing, "
                            + " fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti, "
                            + " st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3, "
                            + " tx_repleg,  tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro, "
                            + " fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon,  tx_obsven, "
                            + " tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat, fe_ultactdat, st_sex, st_estciv, st_oriing,  "
                            + " tx_obsinfburcre,  st_ivacom, st_ivaven, fe_his, co_usrhis, st_peringnomclicotven )  "
                            + " "
                            + " SELECT co_emp, co_cli, "
                            + "  ( select case when max(co_his)+1 is null then 1 else max(co_his)+1 end as cohis  from tbh_cli where co_emp=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP) + "),"
                            + " st_cli, st_prv, tx_tipide, tx_ide, tx_nom, tx_dir, "
                            + " tx_tel, tx_fax, tx_cas, tx_dirweb, tx_corele, tx_tipper, co_ciu, "
                            + " co_par,co_zon, tx_percon, tx_telcon, tx_corelecon, co_ven, co_grp, co_forpag,  "
                            + " nd_moncre, ne_diagra, nd_maxdes, tx_obs1, tx_obs2, st_reg, fe_ing,  "
                            + " fe_ultmod, co_usring, co_usrmod, co_tipper, tx_refubi, nd_maruti,  "
                            + " st_regrep, st_cieretpen, ne_diagrachqfec, tx_tel1, tx_tel2, tx_tel3,  "
                            + " tx_repleg, tx_idepro, tx_nompro, tx_dirpro, tx_telpro, tx_nacpro,  "
                            + " fe_conemp, tx_tipactemp, tx_obspro, ne_nummaxvencon, tx_obsven,  "
                            + " tx_obsinv, tx_obscxc, tx_obscxp, fe_proactdat, fe_ultactdat, st_sex, st_estciv, st_oriing, "
                            + " tx_obsinfburcre, st_ivacom, st_ivaven, CURRENT_TIMESTAMP , " + objParSis.getCodigoUsuario() + " , st_peringnomclicotven "
                            + " FROM tbm_cli WHERE  co_emp=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_EMP) + " AND co_cli=" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CLI)+" ; \n";  
                    }
                }
                System.out.println("guardarDet " + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
            }
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
     * Para que si el cliente no esta dado de alta, se lo cambie solo si cumple con las reglas de la migracion
     * @param intFil
     * @return 
     */
    
    private boolean validacionAltaClientes(int intFil){
        boolean blnRes=false;
        try{
            if( (objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_3)!=null) && (objTblMod.getValueAt(intFil, INT_TBL_DAT_COR_ELE_FAC_ELE)!=null) 
                && (objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CIU)!=null) && (objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_PAR)!=null) 
                && (objTblMod.getValueAt(intFil, INT_TBL_DAT_SEX_CLI)!=null) && (objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_CIV)!=null) 
                && (objTblMod.getValueAt(intFil, INT_TBL_DAT_ORG_ING)!=null) ){
                    if( (objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_3).toString().length()==9 || 
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_TEL_3).toString().length()==10) &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_COR_ELE_FAC_ELE).toString().length()>0 &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_CIU).toString().length()>0 &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_PAR).toString().length()>0 &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_SEX_CLI).toString().length()>0 &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_EST_CIV).toString().length()>0 &&  
                            objTblMod.getValueAt(intFil, INT_TBL_DAT_ORG_ING).toString().length()>0 ){
                        blnRes=true;
                    } 
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
}