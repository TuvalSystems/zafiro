/*
 * ZafCom85_01.java
 *
 * Created on 04 de Mayo del 2016, 10:08
 */

package Ventas.ZafVen17;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author Jose Mario
 */
public class ZafVen17_01 extends javax.swing.JInternalFrame 
{
    //Constantes
    //JTable: Tabla de Datos
    private static final int INT_TBL_DAT_LIN = 0;                               //Linea.
    private static final int INT_TBL_DAT_COD_EMP = 1;            //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_COD_LOC = 2;            //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_COD_DOC = 3;         //Código de la cotización.
    private static final int INT_TBL_DAT_COD_REG = 4;      //Registro de la cotización.
    private static final int INT_TBL_DAT_COD_ITM = 5;      //Código del item.
    private static final int INT_TBL_DAT_COD_ALT = 6;            //Código alterno del item.
    private static final int INT_TBL_DAT_NOM_ITM = 7;            //Nombre del item.
    private static final int INT_TBL_DAT_CAN_ITM = 8;                           //Cantidad Cotización.
    private static final int INT_TBL_DAT_CAN_PEN = 9;                     //Cantidad Pendiente de llegar.
    private static final int INT_TBL_DAT_CAN_DIS_FAC = 10;                     //Cantidad Disponible para facturar (lo cotizado menos lo pendiente).
    private static final int INT_TBL_DAT_CAN_CAN = 11;                           //Cantidad Cancelada.
    private static final int INT_TBL_DAT_CAN_FAC = 12;                           //Cantidad Facturada.
    private static final int INT_TBL_DAT_CAN_SEL = 13;                           //Cantidad Seleccionada por el usuario.
    private static final int INT_TBL_DAT_NNNN = 14;                           //.
   
     
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
    private String strCodLoc="";
    private String strCodDoc="";
    
    private String strVersion="v 0.1";                                           //Versión del Programa.  
     private java.awt.Component cmpPad;
     private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    
    //Constructor
    public ZafVen17_01(java.awt.Component componente, ZafParSis objParSis,  String CodEmp, String CodLoc, String CodDoc) 
    {
        try
        {
            //Inicializar objetos.
            this.objParSis = (ZafParSis) objParSis.clone();
            cmpPad=componente;
            /*strCodSegSolTraInv=strCodSeg;*/
            strCodEmp=CodEmp;
            strCodLoc=CodLoc;
            strCodDoc=CodDoc;
            
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
            lblTit.setText("Detalle de ítems ");
            
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
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");      
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");  
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");        
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Ítem");
            vecCab.add(INT_TBL_DAT_CAN_ITM,"Cantidad");
            vecCab.add(INT_TBL_DAT_CAN_PEN,"Can.Pen.");
            vecCab.add(INT_TBL_DAT_CAN_DIS_FAC,"Can.Dis.Fac.");
            vecCab.add(INT_TBL_DAT_CAN_CAN,"Can.Can.");  
            vecCab.add(INT_TBL_DAT_CAN_FAC,"Can.Fac.");
            vecCab.add(INT_TBL_DAT_CAN_SEL,"Can.Sel.");
            vecCab.add(INT_TBL_DAT_NNNN,"XXXx");
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
            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS_FAC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CAN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_NNNN).setPreferredWidth(65);
             
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);            

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_NNNN, tblDat);
           
           
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
            vecAux.add("" + INT_TBL_DAT_CAN_SEL);
            objTblModDat.setColumnasEditables(vecAux);
            vecAux = null;
            
            
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_FAC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL).setCellRenderer(objTblCelRenLbl);
           
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL).setCellRenderer(objTblCelRenLbl); 
            objTblCelRenLbl=null;
            
            
            
            //Cantidad
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter(){
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    int intCelSel = tblDat.getSelectedRow();
                    if(objUti.parseDouble(objTblModDat.getValueAt(intCelSel, INT_TBL_DAT_CAN_SEL))>0){
                        if (objUti.parseDouble(objTblModDat.getValueAt(intCelSel, INT_TBL_DAT_CAN_SEL))>
                           (objUti.parseDouble(objTblModDat.getValueAt(intCelSel, INT_TBL_DAT_CAN_DIS_FAC)))){
                            mostrarMsgInf("La cantidad solicitada es mayor a lo disponible para facturar...");
                            objTblModDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_SEL);
                        }
                    }
                }
            });
            
            
            
           
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)   {  
            blnRes=false;   
            objUti.mostrarMsgErr_F1(cmpPad, e);     
        }
        return blnRes;
    }
 
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
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;        
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;                
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de registro del ítem";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del ítem";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código Alterno del ítem";
                    break;
               
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del ítem";
                    break;
                case INT_TBL_DAT_CAN_ITM:
                    strMsg="Cantidad en la cotizacón";
                    break;
                case INT_TBL_DAT_CAN_DIS_FAC:
                    strMsg="Cantidad disponible para facturar.";
                    break;
                case INT_TBL_DAT_CAN_CAN:
                    strMsg="Cantidad cancelada.";
                    break;
                case INT_TBL_DAT_CAN_FAC:
                    strMsg="Cantidad Facturada";
                    break;
                 case INT_TBL_DAT_CAN_SEL:
                    strMsg="Cantidad Selecionada por el usuario";
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
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
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

        tabFrm.addTab("Datos", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panBot.add(btnGuardar);

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
    
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea generar una factura parcial?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {
            if(objThrGua==null){
                objThrGua=new ZafVen17_01.ZafThrGua();
                objThrGua.start();
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
    
    //<editor-fold defaultstate="collapsed" desc=" // Variables declaration - do not modify  ">
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
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
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
  
    //</editor-fold>
    
    private ZafThrGua objThrGua; 
    
    private class ZafThrGua extends Thread{
        public void run(){
            try{
                
                //Inicializar objetos si no se pudo cargar los datos.
                if(iniciarProcesoFacturaParcial()){
                    blnAceptar=true;
                }
                else{
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                    mostrarMsgInf("No se pudo generar la factura parcial...");
                }
                objThrGua=null;
                
                 
            }
            catch (Exception e){
                System.err.println(e);
            }
        }
    }
    
    
    
    /**
     * Proceso para generar la factura parcial 
     *      1 generamos un contenedor donde tendremos el registro de la cotizacion y las cantidades seleccionadas a facturar 
     *      2 Enviamos estos datos a una clase aparte que se encargue de generar la factura 
     * JM
     * @return 
     */
    
    private ZafGenFacPar objGenFacPar;
    
    private boolean iniciarProcesoFacturaParcial(){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            pgrSis.setIndeterminate(true);           
            btnGuardar.setEnabled(false);
            if(isValidacionesDatosSeleccionados()){
                if(generarContenedorEnviar()){
                    conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (conLoc != null){
                        conLoc.setAutoCommit(false);
                        //Lamar a la clase
                        objGenFacPar = new Librerias.ZafGenFacAut.ZafGenFacPar(objParSis,cmpPad );
                        if(objGenFacPar.insertarFacturaParcial(conLoc, Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc), Integer.parseInt(strCodDoc), arlDatCon)){
                            blnRes=true;
                            conLoc.commit();
                        }
                        else{
                            conLoc.rollback();
                        }
                        objGenFacPar=null;
                        conLoc.close();
                        conLoc=null;
                    }
                }
            }
            pgrSis.setIndeterminate(false);
            consultar();
            btnGuardar.setEnabled(true);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    private ArrayList arlRegCon, arlDatCon;
    private static final int INT_TBL_DAT_CON_LIN = 0;                   //Linea.
    private static final int INT_TBL_DAT_CON_COD_EMP = 1;              //Código de Empresa de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CON_COD_LOC = 2;              //Código de Local de Ordenes de Despacho (Pedidos a Otras Bodegas).
    private static final int INT_TBL_DAT_CON_COD_DOC = 3;              //Código de la cotización.
    private static final int INT_TBL_DAT_CON_COD_REG = 4;              //Registro de la cotización.
    private static final int INT_TBL_DAT_CON_CAN_SOL_FAC_ITM = 5;      //Cantidad que se solicita facturar
    
    
    private boolean generarContenedorEnviar(){
        boolean blnRes=true;
        try{
            arlDatCon = new ArrayList();
            int intNumFil=objTblModDat.getRowCountTrue();
            for (int i=0; i<intNumFil;i++){
                if(objTblModDat.getValueAt(i,INT_TBL_DAT_CAN_SEL)!=null){
                    if(objTblModDat.getValueAt(i,INT_TBL_DAT_CAN_SEL).toString().length()>0){
                        arlRegCon = new ArrayList();
                        arlRegCon.add(INT_TBL_DAT_CON_LIN,"");
                        arlRegCon.add(INT_TBL_DAT_CON_COD_EMP, Integer.parseInt(strCodEmp));
                        arlRegCon.add(INT_TBL_DAT_CON_COD_LOC, Integer.parseInt(strCodLoc));
                        arlRegCon.add(INT_TBL_DAT_CON_COD_DOC, Integer.parseInt(strCodDoc));
                        arlRegCon.add(INT_TBL_DAT_CON_COD_REG, Integer.parseInt(objTblModDat.getValueAt(i,INT_TBL_DAT_COD_REG).toString()) );
                        arlRegCon.add(INT_TBL_DAT_CON_CAN_SOL_FAC_ITM, objTblModDat.getValueAt(i,INT_TBL_DAT_CAN_SEL).toString());
                        arlDatCon.add(arlRegCon);
                    }
                }
            }
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * 
     * @return 
     */
    
    private boolean isValidacionesDatosSeleccionados(){
        boolean blnRes=true, blnIsCanSel=false, blnCanDis=false;
        try{
            int intNumFil=objTblModDat.getRowCount();
            for (int i=0; i<intNumFil;i++){
                if(tblDat.getValueAt(i,INT_TBL_DAT_CAN_SEL)!=null){
                    if(tblDat.getValueAt(i,INT_TBL_DAT_CAN_SEL).toString().length()>0){
                        blnIsCanSel=true;
                    }
                }
                if(tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL)!=null){
                    if(tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString().length()>0){
                        if(Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString())
                                > Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_CAN_DIS_FAC).toString().equals("")?"0":tblDat.getValueAt(i, INT_TBL_DAT_CAN_DIS_FAC).toString())){
                            System.out.println("ssss entro ");
                            blnCanDis=true;
                        }
                    }
                }
            }
            if(!blnIsCanSel){
                mostrarMsgInf("No ha seleccionado ningun item...");
                blnRes=false;
            }
            
            if(blnCanDis){
                mostrarMsgInf("La cantidad solicitada es mayor a lo disponible para facturar...");
                blnRes=false;
            }
            
            if(!validaDisponibles()){
                blnRes=false;
            }
            
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
    }
    
    private String strMsg;
    private String strTit="Mensaje del sistema Zafiro";
    
    private boolean validaDisponibles(){
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        boolean blnRes=true;
        String strItem="";
        try{
            int intNumFil=objTblModDat.getRowCount();
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null){
                stmLoc = conLoc.createStatement();    
                strSQL ="";
                strSQL+=" SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.tx_codALt, a2.tx_nomItm, a2.nd_can, \n";        
                strSQL+="        CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END AS nd_canPenFac , \n";        
                strSQL+="        CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END as nd_canCan,CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END as nd_canFac,  \n";        
                strSQL+="        CASE WHEN a4.nd_can IS NULL THEN 0 ELSE a4.nd_can END as nd_canTra, a4.nd_canEgrBod, a4.nd_canDesEntCli, a4.nd_canIngBod,  \n";     
                strSQL+="        CASE WHEN a4.nd_canIngBod IS NULL THEN 0 ELSE a4.nd_canIngBod END AS nd_canPen , \n";
                strSQL+="        a2.nd_can -    \n";
                strSQL+="                 CASE WHEN a4.nd_canIngBod IS NULL THEN 0 ELSE a4.nd_canIngBod END -   \n";
                strSQL+="                 CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END -    \n";
                strSQL+="                 CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END AS nd_canDisFac  ";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";        
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND A1.co_cot=a2.co_cot) \n";        
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a3 ON (a1.co_emp=a3.co_empRelCabCotVen AND a1.co_loc=a3.co_locRelCabCotVen AND a1.co_cot=a3.co_cotRelCabCotVen) \n";        
                strSQL+=" LEFT OUTER JOIN ( \n";        
                strSQL+="       SELECT a1.co_seg, a4.co_itm, SUM(a4.nd_can) nd_can, SUM(a4.nd_canEgrBod) as nd_canEgrBod, SUM(a4.nd_canDesEntCli) as nd_canDesEntCli,\n";        
                strSQL+="              SUM( a4.nd_canIngBod) as nd_canIngBod, SUM(a4.nd_canPen) as nd_canPen    \n"; 
                strSQL+="       FROM tbm_cabSegMovInv as a1   \n"; 
                strSQL+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n"; 
                strSQL+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n"; 
                strSQL+="       INNER JOIN ( \n"; 
                strSQL+="           SELECT a3.co_seg \n"; 
                strSQL+="           FROM tbm_cabCotVen as a1   \n"; 
                strSQL+="           INNER JOIN tbm_cabSegMovInv as a3 ON (a1.co_emp=a3.co_empRelCabCotVen AND a1.co_loc=a3.co_locRelCabCotVen AND a1.co_cot=a3.co_cotRelCabCotVen)  \n"; 
                strSQL+="           WHERE a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_cot="+strCodDoc+"	  \n"; 
                strSQL+="       ) as a3 ON (a1.co_seg=a3.co_seg) \n"; 
                strSQL+="       INNER JOIN tbm_detMovInv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDOc=a4.co_tipDoc AND a2.co_doc=a4.co_doc)  \n"; 
                strSQL+="       WHERE a4.nd_can > 0  AND (a2.tx_tipMov='V' OR a2.tx_tipMov='I' OR a2.tx_tipMov='R')  \n";
                strSQL+="       GROUP BY  a1.co_seg, a4.co_itm \n";
                strSQL+=" ) as a4 ON (a3.co_seg=a4.co_seg AND a2.co_itm=a4.co_itm)  \n"; 
                strSQL+=" WHERE a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_cot="+strCodDoc+" \n"; 
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg  \n"; 
                System.out.println("cargarDetReg: "+strSQL);   
                rstLoc = stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    for (int i=0; i<intNumFil;i++){
                        if(rstLoc.getInt("co_reg")==Integer.parseInt(tblDat.getValueAt(i,INT_TBL_DAT_COD_REG).toString())){
                            if(tblDat.getValueAt(i,INT_TBL_DAT_CAN_SEL)!=null){
                                if(tblDat.getValueAt(i,INT_TBL_DAT_CAN_SEL).toString().length()>0){
                                    System.out.println(" REG "+tblDat.getValueAt(i,INT_TBL_DAT_COD_REG).toString());
                                    System.out.println("nd_canDisFac  "+rstLoc.getDouble("nd_canDisFac")  );
                                    System.out.println("INT_TBL_DAT_CAN_SEL  "+tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString()  );
                                    if(Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString())> rstLoc.getDouble("nd_canDisFac") ){
                                        blnRes=false;
                                        strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                                        strItem+=" <td>" + objUti.redondear(rstLoc.getDouble("nd_canDisFac"), objParSis.getDecimalesMostrar())  + " </td>";
                                        strItem+=" <td>" + tblDat.getValueAt(i,INT_TBL_DAT_CAN_SEL).toString() + " </td> ";
                                        strItem+=" </tr> ";
                                    }
                                }
                            }
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                if(!blnRes){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    JOptionPane.showMessageDialog(cmpPad, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        return blnRes;
        
    }
    
    
    
    
    
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
                strSQL+=" SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg, a2.co_itm, a2.tx_codALt, a2.tx_nomItm, a2.nd_can, \n";        
                strSQL+="        CASE WHEN a2.nd_canPenFac IS NULL THEN 0 ELSE a2.nd_canPenFac END AS nd_canPenFac , \n";        
                strSQL+="        CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END as nd_canCan,CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END as nd_canFac,  \n";        
                strSQL+="        CASE WHEN a4.nd_can IS NULL THEN 0 ELSE a4.nd_can END as nd_canTra, a4.nd_canEgrBod, a4.nd_canDesEntCli, a4.nd_canIngBod,  \n";     
                strSQL+="        CASE WHEN a4.nd_canIngBod IS NULL THEN 0 ELSE a4.nd_canIngBod END AS nd_canPen , \n";
                strSQL+="        a2.nd_can -    \n";
                strSQL+="                 CASE WHEN a4.nd_canIngBod IS NULL THEN 0 ELSE a4.nd_canIngBod END -   \n";
                strSQL+="                 CASE WHEN a2.nd_canCan IS NULL THEN 0 ELSE a2.nd_canCan END -    \n";
                strSQL+="                 CASE WHEN a2.nd_canFac IS NULL THEN 0 ELSE a2.nd_canFac END AS nd_canDisFac  ";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";        
                strSQL+=" INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND A1.co_cot=a2.co_cot) \n";        
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a3 ON (a1.co_emp=a3.co_empRelCabCotVen AND a1.co_loc=a3.co_locRelCabCotVen AND a1.co_cot=a3.co_cotRelCabCotVen) \n";        
                strSQL+=" LEFT OUTER JOIN ( \n";        
                strSQL+="       SELECT a1.co_seg, a4.co_itm, SUM(a4.nd_can) nd_can, SUM(a4.nd_canEgrBod) as nd_canEgrBod, SUM(a4.nd_canDesEntCli) as nd_canDesEntCli,\n";        
                strSQL+="              SUM( a4.nd_canIngBod) as nd_canIngBod, SUM(a4.nd_canPen) as nd_canPen    \n"; 
                strSQL+="       FROM tbm_cabSegMovInv as a1   \n"; 
                strSQL+="       INNER JOIN tbm_cabMovInv as a2 ON (a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND  \n"; 
                strSQL+="                                          a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc ) \n"; 
                strSQL+="       INNER JOIN ( \n"; 
                strSQL+="           SELECT a3.co_seg \n"; 
                strSQL+="           FROM tbm_cabCotVen as a1   \n"; 
                strSQL+="           INNER JOIN tbm_cabSegMovInv as a3 ON (a1.co_emp=a3.co_empRelCabCotVen AND a1.co_loc=a3.co_locRelCabCotVen AND a1.co_cot=a3.co_cotRelCabCotVen)  \n"; 
                strSQL+="           WHERE a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_cot="+strCodDoc+"	  \n"; 
                strSQL+="       ) as a3 ON (a1.co_seg=a3.co_seg) \n"; 
                strSQL+="       INNER JOIN tbm_detMovInv as a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDOc=a4.co_tipDoc AND a2.co_doc=a4.co_doc)  \n"; 
                strSQL+="       WHERE a4.nd_can > 0  AND (a2.tx_tipMov='V' OR a2.tx_tipMov='I' OR a2.tx_tipMov='R')  \n";
                strSQL+="       GROUP BY  a1.co_seg, a4.co_itm \n";
                strSQL+=" ) as a4 ON (a3.co_seg=a4.co_seg AND a2.co_itm=a4.co_itm)  \n"; 
                strSQL+=" WHERE a1.co_emp="+strCodEmp+" and a1.co_loc="+strCodLoc+" and a1.co_cot="+strCodDoc+" \n"; 
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot, a2.co_reg  \n"; 
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
                    vecReg.add(INT_TBL_DAT_COD_LOC, rstLoc.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rstLoc.getString("co_cot"));
                    vecReg.add(INT_TBL_DAT_COD_REG,rstLoc.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_ITM,rstLoc.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT,rstLoc.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rstLoc.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_CAN_ITM,objUti.redondear(rstLoc.getString("nd_can"), objParSis.getDecimalesMostrar()));
                    vecReg.add(INT_TBL_DAT_CAN_PEN,objUti.redondear(rstLoc.getString("nd_canPen"), objParSis.getDecimalesMostrar()));
                    if(Double.parseDouble(rstLoc.getString("nd_canDisFac"))>0){
                        vecReg.add(INT_TBL_DAT_CAN_DIS_FAC,objUti.redondear(rstLoc.getString("nd_canDisFac"), objParSis.getDecimalesMostrar()));
                    }
                    else{
                        vecReg.add(INT_TBL_DAT_CAN_DIS_FAC,"0");
                    }
                    vecReg.add(INT_TBL_DAT_CAN_CAN,objUti.redondear(rstLoc.getString("nd_canCan"), objParSis.getDecimalesMostrar()));
                    vecReg.add(INT_TBL_DAT_CAN_FAC,objUti.redondear(rstLoc.getString("nd_canFac"), objParSis.getDecimalesMostrar()));
                    vecReg.add(INT_TBL_DAT_CAN_SEL,"");
                    vecReg.add(INT_TBL_DAT_NNNN,"");
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
    
    
    
}
