/*
 * ZafCom85_01.java
 *
 * Created on 04 de Mayo del 2016, 10:08
 */

package Ventas.ZafVen66;


import Librerias.ZafGenFacAut.ZafGenFacPar;
import java.sql.DriverManager;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Jose Mario
 */
public class ZafVen66_01 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    static final int INT_TBL_DAT_LIN=0;    
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_NOM_EMP=2;
    static final int INT_TBL_DAT_COD_LOC=3;
    static final int INT_TBL_DAT_NOM_LOC=4;
    static final int INT_TBL_DAT_COD_VEN=5;
    static final int INT_TBL_DAT_NOM_VEN=6;
    static final int INT_TBL_DAT_COM_CONF=7;                      
    static final int INT_TBL_DAT_VAL_FIJ_CONF=8;                    
    static final int INT_TBL_DAT_VAL_FIJ_INGRESO=9;                   
    static final int INT_TBL_DAT_VAL_FIJ_EGRESO=10;
    static final int INT_TBL_DAT_VAL_TOTAL=11;
    static final int INT_TBL_DAT_BTN_DET=12;
//    static final int INT_TBL_DAT_CHK_SOLICITAR=11;
//    static final int INT_TBL_DAT_CHK_PENDIENTE=12;
//    static final int INT_TBL_DAT_CHK_AUTORIZAR=13;
//    static final int INT_TBL_DAT_OBS_AUTORIZACION=14;
//    static final int INT_TBL_DAT_BTN_AUTORIZACION=15;                     
   
    
    //Variables
   
    private ZafParSis objParSis;
    private ZafUtil objUti;    
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblFilCab objTblFilCab;
    private ZafThreadGUI objThrGUI;
    private ZafTblMod objTblModDat;                                             //Modelo de Jtable.
    private ZafMouMotAda objMouMotAdaDat;                                       //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;
    
    private String strCodEmp="";
    private String strCodVen="";
    private String strCodPer="";
    
    private String strVersion="v0.1";                                           //Versión del Programa.  
     private java.awt.Component cmpPad;
     private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    
    //Constructor
    public ZafVen66_01(java.awt.Component componente, ZafParSis objParSis,  String CodEmp, String CodPer) 
    {
        try
        {
            //Inicializar objetos.
            this.objParSis = (ZafParSis) objParSis.clone();
            cmpPad=componente;
            /*strCodSegSolTraInv=strCodSeg;*/
            strCodEmp=CodEmp;
            strCodPer=CodPer;
            
            initComponents();
            if (!configurarFrm()) 
                exitForm();
            
            consultar();
            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=false;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText("Listado de documentos considerados para el cálculo de comisiones.");
            
            //Configurar Jtable
            if(configurarTblDat()){
                blnRes=true;
            }
        }
        catch(Exception e)   {    
            blnRes=false;   
            objUti.mostrarMsgErr_F1(cmpPad, e);   
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Empresa");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Nom.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Nom.Loc.");
            vecCab.add(INT_TBL_DAT_COD_VEN,"Cód.Ven.");
            vecCab.add(INT_TBL_DAT_NOM_VEN,"Vendedor");
            vecCab.add(INT_TBL_DAT_COM_CONF,"Com.Conf.");
            vecCab.add(INT_TBL_DAT_VAL_FIJ_CONF,"Val.Fijo.");
            vecCab.add(INT_TBL_DAT_VAL_FIJ_INGRESO,"Ingreso");
            vecCab.add(INT_TBL_DAT_VAL_FIJ_EGRESO,"Egreso");
            vecCab.add(INT_TBL_DAT_VAL_TOTAL,"Total");
            vecCab.add(INT_TBL_DAT_BTN_DET,"...");
            objTblModDat=new ZafTblMod();
            objTblModDat.setHeader(vecCab);
            tblDat.setModel(objTblModDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COM_CONF).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_CONF).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_INGRESO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_EGRESO).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOTAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BTN_DET).setPreferredWidth(30);
          
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_NOM_LOC, tblDat);
           
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDat=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAdaDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
   
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
             //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BTN_DET);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
            
            
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COM_CONF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_CONF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_INGRESO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FIJ_EGRESO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOTAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_DET).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
           
            ButDetComVen butDetComVen = new ButDetComVen(tblDat, INT_TBL_DAT_BTN_DET); //*****    
            
            int intCol[]={INT_TBL_DAT_COM_CONF, INT_TBL_DAT_VAL_FIJ_CONF, INT_TBL_DAT_VAL_FIJ_INGRESO,INT_TBL_DAT_VAL_FIJ_EGRESO,INT_TBL_DAT_VAL_TOTAL};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
             
            
        }
        catch(Exception e)   {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(cmpPad, e);     
        }
        return blnRes;
    }
    
    private ZafTblTot objTblTot;                                //JTable de totales.
     
 
    /**
     * ToolTips de Jtable: Datos.
     */
     private class ZafMouMotAda extends MouseMotionAdapter 
     {
        @Override
        public void mouseMoved(MouseEvent evt) 
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;                
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;        
                case INT_TBL_DAT_COD_VEN:
                    strMsg="Código del Vendedor";
                    break;  
                case INT_TBL_DAT_NOM_VEN:
                    strMsg="Nombre del Vendedor";
                    break;  
                case INT_TBL_DAT_COM_CONF:
                    strMsg="Calculo de la comisión segun lo configurado";
                    break;  
                case INT_TBL_DAT_VAL_FIJ_CONF:
                    strMsg="Valor fijo configurado";
                    break;  
                case INT_TBL_DAT_VAL_FIJ_INGRESO:
                    strMsg="Valor fijo de ingreso agregado";
                    break;  
                case INT_TBL_DAT_VAL_FIJ_EGRESO:
                    strMsg="Valor fijo de egreso agregado";
                    break;  
                case INT_TBL_DAT_VAL_TOTAL:
                    strMsg="Valor total";
                    break;  
                case INT_TBL_DAT_BTN_DET:
                    strMsg="Muestra el detalle del valor";
                    break;  
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()
        {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panEst = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setFrameIcon(null);
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

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(452, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Datos", panRpt);

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

        panEst.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panEst.setMinimumSize(new java.awt.Dimension(24, 26));
        panEst.setPreferredSize(new java.awt.Dimension(200, 15));
        panEst.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        panEst.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panEst, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(430, 180, 667, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        consultar();
    }//GEN-LAST:event_butConActionPerformed

    /*Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").*/
    private void consultar(){
        if (butCon.getText().equals("Consultar")){
            blnCon = true;
            if (objThrGUI == null){
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else 
        {
            blnCon = false;
        }
    }
    
    
    /* Cerrar la aplicación */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private boolean blnAceptar=false;
        
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEst;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
   
     
      
    
     
    
     
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que estï¿½ ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podrï¿½a presentar mensajes informativos en un JLabel e ir incrementando
     * un JProgressBar con lo cual el usuario estaría informado en todo momento
     * de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        @Override
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
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    
    private void exitForm() 
    {
        dispose();
    } 
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null){
                stmLoc = conLoc.createStatement();      
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a2.tx_nom as tx_nomEmp, a1.co_per, a1.co_usrVen, a3.tx_nom, ROUND(sum(a1.nd_valCom),2) as comision \n";
                strSQL+=" FROM tbm_comvenpag as a1 \n";
                strSQL+=" INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp) \n";
                strSQL+=" INNER JOIN tbm_usr as a3 ON (a1.co_usrVen=a3.co_usr) \n";
                strSQL+=" WHERE a1.co_emp="+strCodEmp+" AND a1.co_per="+strCodPer+" ";
                strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_per , a1.co_usrVen, a3.tx_nom \n";
                strSQL+=" ORDER BY a1.co_emp, a1.co_per , a1.co_usrVen \n";
                strSQL+=" \n";
                System.out.println("cargarDetReg: "+strSQL);   
                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rstLoc.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rstLoc.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_NOM_EMP, rstLoc.getString("tx_nomEmp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC, "");
                    vecReg.add(INT_TBL_DAT_NOM_LOC, "");
                    vecReg.add(INT_TBL_DAT_COD_VEN, rstLoc.getString("co_usrVen"));
                    vecReg.add(INT_TBL_DAT_NOM_VEN, rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_COM_CONF, rstLoc.getString("comision"));
                    vecReg.add(INT_TBL_DAT_VAL_FIJ_CONF, "");
                    vecReg.add(INT_TBL_DAT_VAL_FIJ_INGRESO, "");
                    vecReg.add(INT_TBL_DAT_VAL_FIJ_EGRESO, "");
                    vecReg.add(INT_TBL_DAT_VAL_TOTAL, rstLoc.getString("comision"));
                    vecReg.add(INT_TBL_DAT_BTN_DET, "");
                    vecDat.add(vecReg);
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc=null;
                stmLoc=null;
                conLoc=null;
               
                //Asignar vectores al modelo.
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                
                vecDat.clear();
                objTblTot.calcularTotales();   
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
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    private class ButDetComVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButDetComVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Detalle comisiones de venta.");
           
        }
        @Override
        public void butCLick() {
         // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarDetComVen();
        }
    }
       
      
    private void llamarDetComVen(){
        try{
            int intCol = tblDat.getSelectedRow();
            String strCodVen = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  ).toString());
           
            ZafVen66_02 obj1 = new  ZafVen66_02(javax.swing.JOptionPane.getFrameForComponent(this), objParSis , strCodEmp, strCodVen, strCodPer );
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
             
 
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        
    }
    
    
}
