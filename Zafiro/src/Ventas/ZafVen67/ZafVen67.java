/*
 * ZafVen67.java
 *
 * Created on 30 Agosto, 2013, 10:16 PM
 */

package Ventas.ZafVen67;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 *
 * @author  José Marín 
 */
public class ZafVen67 extends javax.swing.JInternalFrame {
    
    private ZafParSis objParSis;
    private ZafVenCon vcoPrg;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenButDG1;
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafVenCon vcoUsr;
    private ZafVenCon vecLoc; 
   
    private Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    
    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafThreadReporte objThrReporte;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
     
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
  
    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecVeh, vecCom;
 
 

    //Tabla 
   //Código del combustible/Descripción larga del combustible/Precio del combustible    
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COT=1;
    private final int INT_TBL_DAT_COT_COM=2;
    private final int INT_TBL_DAT_NOM_CLI=3;
    private final int INT_TBL_DAT_USU_ING=4;
    private final int INT_TBL_DAT_EST=5;
    private final int INT_TBL_DAT_TOT=6;
    private final int INT_TBL_DAT_BTN=7;
    private final int INT_TBL_DAT_IMP=8;
    
    private Vector vecCodCom, vecDesLarCom, vecPreCom;
    
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
   private ZafRptSis objRptSis;
    
    private ZafThrAutoConsulta objThrAutoConsulta;
    
    private int intTiempoHiloDormido=60000; // 600000//10 min - 300000//5 min  - 120000 2 Min- 60000//1 min  <===
   
    /** Creates new form ZafVeh06 */
    public ZafVen67(ZafParSis obj) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /**
     * Constructor a ser llamado desde el inicio de sesion 
     * @param obj
     * @param intCodMnu 
     */
     public ZafVen67(ZafParSis obj, Integer intCodMnu) {
        try{
            initComponents();
            objUti = new ZafUtil();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objParSis.setCodigoMenu(intCodMnu); 
            objParSis.setNombreMenu("Listado de documentos imprimibles..."); 
      
            objRptSis = new ZafRptSis(JOptionPane.getFrameForComponent(this), true, objParSis);
            if (!configurarFrm())
                exitForm();
            btnConsultar.doClick();
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panRpt = new javax.swing.JPanel();
        panCotizaciones = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        btnConsultar = new javax.swing.JButton();
        txtNumCot = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
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
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panRpt.setLayout(new java.awt.BorderLayout());

        panCotizaciones.setLayout(new java.awt.BorderLayout());

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

        panCotizaciones.add(spnDet, java.awt.BorderLayout.CENTER);

        panRpt.add(panCotizaciones, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cotizaciones", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnConsultar.setText("Consultar");
        btnConsultar.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        btnConsultar.setPreferredSize(new java.awt.Dimension(92, 25));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        panBot.add(btnConsultar);

        txtNumCot.setToolTipText("Ingrese el numero de cotizacion para busqueda directa ");
        txtNumCot.setPreferredSize(new java.awt.Dimension(160, 25));
        txtNumCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumCotActionPerformed(evt);
            }
        });
        panBot.add(txtNumCot);

        btnBuscar.setText("Buscar");
        btnBuscar.setPreferredSize(new java.awt.Dimension(92, 25));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        panBot.add(btnBuscar);

        btnCerrar.setText("Cerrar");
        btnCerrar.setToolTipText("Cierra la ventana.");
        btnCerrar.setPreferredSize(new java.awt.Dimension(92, 25));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        panBot.add(btnCerrar);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 546, 568);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                 cerrarHilo();
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }        
}//GEN-LAST:event_exitForm

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (btnConsultar.getText().equals("Consultar"))
        {
            blnCon=true;
            if(objThrAutoConsulta==null){
                System.out.println("llama al hilo... ");
                objThrAutoConsulta=new ZafThrAutoConsulta();
                objThrAutoConsulta.start();
            }
            else{
                if (objThrGUI==null){
                    objThrGUI=new ZafVen67.ZafThreadGUI();
                    objThrGUI.start();    
                }            
            }
            
            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        exitForm(null);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtNumCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumCotActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumCotActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        btnConsultar.doClick();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCotizaciones;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtNumCot;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm(){
        cerrarHilo();
        dispose();
    }   
    
       
    
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            System.out.println("configurarFrm....");
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + " v0.01");
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COT,"Cotización");
            vecCab.add(INT_TBL_DAT_COT_COM,"Cotización");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_USU_ING,"Usuario");
            vecCab.add(INT_TBL_DAT_EST,"Estado");
            vecCab.add(INT_TBL_DAT_TOT,"Total");
            vecCab.add(INT_TBL_DAT_BTN,"...");
            vecCab.add(INT_TBL_DAT_IMP,"IMP");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
//            tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COT_COM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_USU_ING).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP).setPreferredWidth(30);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BTN).setResizable(false);
        
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);           
            tcmAux.getColumn(INT_TBL_DAT_TOT).setCellRenderer(objTblCelRenLblNum);
           
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_TOT, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
           
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add(""+INT_TBL_DAT_BTN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_IMP, tblDat);
             
             
             //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN).setCellRenderer(objTblCelRenBut);           
            objTblCelRenBut=null;
            
            
            ButCotVen butCotVen = new ButCotVen(tblDat, INT_TBL_DAT_BTN); //*****
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
             
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI); //tabla editable 
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);// Escrbir y buscar 
            
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            
            cargarLocalesPermitidos();
           
          
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    private String strCodCot="";
    
    private class ButCotVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButCotVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Cotizaciones de Venta.");
           
        }
        @Override
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
           strCodCot = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_COT  ).toString());
          llamarVen();
        }
    }
    
    /**
     * Validacion del estado de la cotizacion para poder ser impresa 
     */
    private void llamarVen(){
        try{
            int intCol = tblDat.getSelectedRow();
            String strEstadoCotizacion = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_EST  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_DAT_EST  ).toString());
            
            if(strEstadoCotizacion.equals("procesado") || strEstadoCotizacion.equals("aprobado")){
                cargarReporte(1);
            }
            else{
                mostrarMsgInf("La cotización seleccionada necesita ser autorizada para poder ser impresa, favor solicitar la autorización para poder continuar....");
            }
                
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }    
    }
    
    private void cargarReporte(int intTipo) {
            if (objThrReporte == null) {
                objThrReporte = new ZafThreadReporte();
                objThrReporte.setIndFunEje(intTipo);
                objThrReporte.start();
            }
        }
    
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podría presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaría informado en todo momento de
     * lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase ya
     * que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadReporte extends Thread {

        private int intIndFunRep;

        public ZafThreadReporte() {
            intIndFunRep = 0;
        }

        @Override
        public void run() {
            switch (intIndFunRep) {
                case 0: //Botón "Imprimir".
                   generarRpt(1);
                    break;
                case 1: //Botón "Vista Preliminar".
                   generarRpt(2);
                   break;
            }
            objThrReporte = null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la
         * clase Thread se pueden ejecutar diferentes funciones. Esta función
         * sirve para determinar la función que debe ejecutar el Thread.
         *
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice) {
            intIndFunRep = indice;
        }
    }
    
    
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio
     * seleccionado.
     *
     * @param intTipRpt El tipo de reporte a generar. <BR>Puede tomar uno de los
     * siguientes valores: <UL> <LI>0: Impresión directa. <LI>1: Impresión
     * directa (Cuadro de dialogo de impresión). <LI>2: Vista preliminar. </UL>
     * @return true: Si se pudo generar el reporte. <BR>false: En el caso
     * contrario.
     */
    private boolean generarRpt(int intTipRpt) {
        String strRutRpt, strNomRpt;
        int i=1, intNumTotRpt;
        boolean blnRes = true;
        
        String strRutImgLogo = "";
        try {
             
            objRptSis.cargarListadoReportes(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), 3965, objParSis.getCodigoUsuario()); 
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) {
                intNumTotRpt = objRptSis.getNumeroTotalReportes();
                for (i = 0; i < intNumTotRpt; i++) {
                    if (objRptSis.isReporteSeleccionado(i)) {
                        // System.out.println("este " + i);
                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                            case 19:
                            default:
                                strRutRpt = objRptSis.getRutaReporte(i);
                                strNomRpt = objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.

                                strRutImgLogo = strRutRpt + "logo.PNG";

                                Map mapPar = new HashMap();
                                mapPar.put("Cotizacion", strCodCot);
                                mapPar.put("RUTA_LOGO", strRutImgLogo);
                                
//                                System.out.println("strRutRpt " + strRutRpt);
//                                System.out.println("strNomRpt " + strNomRpt);
                                
                                objRptSis.generarReporteMySQL(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                 
                                break;
                        }
                    }
                }
            }
        } 
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    

 
    private String strFil="";

    private boolean cargarLocalesPermitidos(){
        boolean blnRes=true;
       
        String strLocales="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        boolean blnEmp = false;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT a3.tx_codSer, a3.tx_sucAlt  \n";
                    strSQL+=" FROM tbm_loc as a3  \n";
                    strSQL+=" WHERE st_reg = 'A' ";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a3.tx_codSer, a3.tx_sucAlt  \n";
                    strSQL+=" FROM tbr_locUsr as a1  \n";
                    strSQL+=" INNER JOIN tbm_loc1 as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc) \n";
                    strSQL+=" INNER JOIN tbm_loc as a3 ON (a2.tx_ptoVta=a3.tx_codSer) \n";
                    strSQL+=" WHERE a1.co_usr = "+objParSis.getCodigoUsuario()+"  AND a3.st_reg = 'A'  AND a1.st_reg IN ('A', 'P')  \n";
                    strSQL+=" ";
                }
                System.out.println("888>>>> " + strSQL);
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){ 
                    if (!blnEmp) //Primera vez que ingresa.
                    {
                        strLocales += " ( ";
                    } else {
                        strLocales += " OR ";
                    }
                    strLocales += " (PTO_VTA_ID=" + objUti.codificar(rstLoc.getString("tx_codSer"), 0)   + ") ";
                    blnEmp = true;
                }
                if (blnEmp) 
                {
                    strLocales += " ) ";
                    strFil += strLocales;
                }
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    
    private boolean cargarDetReg(){
        int i,temp=0,j=0;
        String strAux="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
           
            pgrSis.setIndeterminate(true);
            btnConsultar.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            Class.forName("com.mysql.cj.jdbc.Driver"); // Cargar el driver de MySQL 
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_TuvalApi(), objParSis.getUsuarioBaseDatos_TuvalApi(), objParSis.getClaveBaseDatos_TuvalApi()); 
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT PTO_VTA_ID, id, cotizacion, CAST(SUBSTRING(cotizacion, 4) AS UNSIGNED) AS cotizacionNumeros,   \n";
                strSQL+="        USERID, estado, CLI_RAZON_SOCIAL, total, impreso, created_at  \n";
                strSQL+=" FROM tuvalapi.gp_cotizacions  \n";
                strSQL+=" WHERE ";
                if(strFil.length()>0){
                    strSQL+=strFil+" \n";
                }
                if(txtNumCot.getText().length()>0){
                    strSQL+=" AND cotizacion like "+ objUti.codificar(txtNumCot.getText())+" \n";
                }
                else{
                    strSQL+=" AND CAST(created_at AS DATE) BETWEEN CURDATE() - INTERVAL 1 DAY AND CURDATE()  \n";
                }
                strSQL+=" ORDER BY cotizacion DESC ; \n";
                
                System.out.println("ZafVen67: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next()){
                    if (blnCon){
                       vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COT,        rstLoc.getString("cotizacion"));  
                        vecReg.add(INT_TBL_DAT_COT_COM,    rstLoc.getString("cotizacionNumeros"));  
                        vecReg.add(INT_TBL_DAT_NOM_CLI,    rstLoc.getString("CLI_RAZON_SOCIAL"));  
                        vecReg.add(INT_TBL_DAT_USU_ING,    rstLoc.getString("USERID"));  
                        vecReg.add(INT_TBL_DAT_EST,        rstLoc.getString("estado"));  
                        vecReg.add(INT_TBL_DAT_TOT,        rstLoc.getDouble("total"));  
                        vecReg.add(INT_TBL_DAT_BTN,        "");  
                        vecReg.add(INT_TBL_DAT_IMP,        rstLoc.getString("impreso"));  
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
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
                btnConsultar.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
            
//            else{
//                objThrAutoConsulta.stop();
//                objThrAutoConsulta=null;
//                objThrAutoConsulta=new ZafThrAutoConsulta();
//                objThrAutoConsulta.start();
//            }
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
     * Nuevo hilo para guardar, despues de un tiempo de inactividad
     */
    private class ZafThrAutoConsulta extends Thread{
        public void run(){
            try{
                while(true){
                    System.out.println("   >>HILO<<  ");
                    btnConsultar.doClick();
                    System.out.println("   Duerme => " + intTiempoHiloDormido);
                    sleep(intTiempoHiloDormido);
                }
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
     
    
    
    /**
     * Cierra el hilo de guardado
     * @return 
     */
    private boolean cerrarHilo(){
        boolean blnRes=false;
        try{
            if (objThrAutoConsulta!=null){
                objThrAutoConsulta.stop();
                objThrAutoConsulta=null;
            }
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
                case INT_TBL_DAT_COT: strMsg="Cotizacion..."; break;
                case INT_TBL_DAT_NOM_CLI: strMsg="Nombre del clientes..."; break;
                case INT_TBL_DAT_USU_ING: strMsg="Usuario de ingreeso..."; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      
    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
    
 
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);         
                btnConsultar.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                 tabFrm.setSelectedIndex(0);
            }
            objThrGUI=null;
        }
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
   
    
      

}
