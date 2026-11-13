/*
 * ZafCom113_03.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Compras.ZafCom113;
////import Compras.ZafCom18.ZafCom18;
////import Compras.ZafCom18.ZafCom18_01;
////import Compras.ZafCom91.ZafCom91;
////import Compras.ZafCom99.ZafCom99;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil; 
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP; 
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtilGP.ZafUtilGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
////import Librerias.ZafVenConGP.ZafVenConItmGP02;
import Librerias.ZafVenConGP.ZafVenConItmGP03;
import Librerias.ZafVenConGP.ZafVenConGP;
import java.awt.Color;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
     

/**
 *
 * @author  Jose Mario Marin
 */
public class ZafCom113_03 extends javax.swing.JInternalFrame{
    
    private static final int INT_TBL_DAT_LIN=0;             //Línea
    private static final int INT_TBL_DAT_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_DAT_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_DAT_ORDEN=4;         //ORDENAMIENTO
    private static final int INT_TBL_DAT_FACTOR=5;         //FACTOR    
    private static final int INT_TBL_DAT_SELE=6;         //SELECCION     
    private static final int INT_TBL_DAT_CMB_FACTOR=7;         //SELECCION         
    
    private static final int INT_TBL_CON_LIN=0;             //Línea
    private static final int INT_TBL_CON_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_CON_COD_ALT_ITM=2;     //Código alterno del item
    private static final int INT_TBL_CON_NOM_ITM=3;         //Nombre del item
    private static final int INT_TBL_CON_ORDEN=4;         //ORDENAMIENTO
    private static final int INT_TBL_CON_FACTOR=5;         //FACTOR
    private static final int INT_TBL_CON_SELE=6;         //SELECCION
    private static final int INT_TBL_CON_CMB_FACTOR=7;         //CMB FACTOR  
    
    private static final int INT_TBL_CON_ARR_COD_ITM=0;         //COD_GP    
    private static final int INT_TBL_ARR_COD_3L=1;         //3L     
    private static final int INT_TBL_ARR_DESC=2;         //DESCRIPCION
    
    //private final int INT_TBL_DAT_LIN=0;    
    private final int INT_TBL_DAT_CODIGO=1;
    private final int INT_TBL_DAT_DESC_CORT=2;
    private final int INT_TBL_DAT_DESC_LAR=3;    
    private final int INT_TBL_DAT_FAC=4;    
    private final int INT_TBL_DAT_ESTADO=5;
    private final int INT_TBL_DAT_OB=6;    
    
    
      //Variable
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModFact;    
    private ZafTblMod objTblModItems;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenCbo objTblCelRenCmbBox,objTblCelRenCmbBox2;   
    private ZafTblCelEdiCbo objTblCelEdiCmbBox,objTblCelEdiCmbBox2;      
    
    
     private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
     private ZafMouMotAda objMouMotAdaFact;                  //ToolTipText en TableHeader.     
     private ZafMouMotAdaItems objMouMotAdaItems;                  //ToolTipText en TableHeader.
   // private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatFact, vecCabFact, vecRegFact;    
    private Vector vecDatItems, vecCabItems, vecRegItems;
    private Vector vecAux;
    private Vector vecAuxFact;    
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
    private char estado='C'; 
    private int estadoFac=-1;
    private java.util.Date datFecDoc;    
    private String strVersion="v1.0";   
    private ZafPerUsr objPerUsr; 
////    private java.util.Date datFecDoc;
    private ZafTblPopMnu objTblPopMnu,objTblPopMnuTbl;                          //PopupMenu: Establecer PeopuMenú en JTable.    
    private ZafTblPopMnu objTblPopMnuFact,objTblPopMnuTblFact;                          //PopupMenu: Establecer PeopuMenú en JTable.        
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblBus objTblBusFact;                                //Editor de búsqueda.    
    private ZafTblFilCab objTblFilCab;
    private ZafTblFilCab objTblFilCabFact;    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblOrd objTblOrdFact, objTblOrdLocFact;                  //JTable de ordenamiento.    
    private ZafTblCelRenLbl objTblCelRenLbl,objTblCelRenLblItems;                    //Render: Presentar JLabel en JTable.
    private int intCodCfg=0;
    private String mensajeError;
    private ZafDocLis objDocLis;
    private boolean blnHayCam;    
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.  
    private ArrayList arlRegORDENAR,arlDatORDENAR;
    
////    private ZafVenConItmGP02 objVenConItm01,objVenConItm02;   
////        private ZafVenConItmGP03 objVenConItm03;   
    
    private ZafVenConItmGP01 objVenConItm01,objVenConItm02;       
    private ZafThreadGUI objThrGUI;
    
    private String strTipRegLin="";  
    private String estadoFor;
    private int tipoAgrupa =-1;
    private ArrayList<String> dataList = new ArrayList<>(); 
    private ArrayList<String> dataList1 = new ArrayList<>();     
    private String grupoGlobal ="";
    private int tipoGlobal =0,codigoGrupoGlobal =0;
    private boolean validaOk =false;
    private ZafHisTblBasDatGP objHisTblBasDatGP; 
    private ZafUtilGP objUtiGP;       
    private ZafHisTblBasDat objHisTblBasDat;    
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strFecUltMod="";     
    private ArrayList<String> dataListFa = new ArrayList<>();  
    private ArrayList<String> dataListFa2 = new ArrayList<>();  
    private ZafTblCelEdiTxt objTblCelEdiTxtEgr, objTblCelEdiTxtIng;   
    private String strMsg;        //Variable de tipo cadena para enviar los mensajes por pantalla   
    private JOptionPane oppMsg;   //Objeto de tipo OptionPane para presentar Mensajes     
    private int procesoEjecucion =0;    
    private Vector vecDatPr, vecCabPr, vecRegPr;   
    private final int INT_TBL_DAT_LIN2=0;    
    private final int INT_TBL_DAT_CODIGO2=1;
    private final int INT_TBL_DAT_COD_ITM2=2;
    private final int INT_TBL_DAT_NOM_ITM2=3;
    private final int INT_TBL_DAT_TRESL2=4; 
    private ZafTblMod objTblModPro;       
    private final String strTit = "Sistema Zafiro"; //Constante del titulo del mensaje     
    private ZafTblPopMnu objTblPopMnuPro;                          //PopupMenu: Establecer PeopuMenú en JTable.     
    private ZafTblBus objTblBusPro;                                //Editor de búsqueda.       
    private ZafTblOrd objTblOrdPro, objTblOrdLocPro;                  //JTable de ordenamiento.     
    private ZafTblFilCab objTblFilCabPro;        
    private ZafMouMotAda objMouMotAdaPro;    
    private boolean blnAnula=false;
    /**
     * Crea una nueva instancia de la clase  .
     * @param obj El objeto ZafParSis.
     */
    public ZafCom113_03(ZafParSis obj){
         try {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(false);     
            butGuardar.setEnabled(false); 
          
            estadoFor="N";
            estado ='C';
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafCom113_03.class.getName()).log(Level.SEVERE, null, ex);
        }
        
////        try{
////            initComponents();
////            //Inicializar objetos.
////            objParSis=(ZafParSis)obj.clone();
////        }
////        catch (CloneNotSupportedException e)
////        {
////            this.setTitle(this.getTitle() + " [ERROR]");
////        }
    }
    
    public ZafCom113_03(java.awt.Frame parent, boolean modal, ZafParSis obj, int tipo, String grupo, int codigoGrupo) {
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            grupoGlobal =grupo;
            tipoGlobal = tipo;
            codigoGrupoGlobal =codigoGrupo;
            initComponents();
            configurarFrm();
            estado ='M';
            butGuardar.setText("Modificar");
            
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(true);     
            butGuardar.setEnabled(true);             
            LLenarTabla();
            objTblMod.insertRow();  

                
            
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafCom113_03.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }  
    
    public ZafCom113_03( ZafParSis obj, int tipo, String grupo, int codigoGrupo) {
        
        try {
            this.objParSis = (ZafParSis) obj.clone();
            grupoGlobal =grupo;
            tipoGlobal = tipo;
            codigoGrupoGlobal =codigoGrupo;
            initComponents();
            configurarFrm();
            estado ='M';
            butGuardar.setText("Modificar");
            
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(true);     
            butGuardar.setEnabled(true);             
            LLenarTabla();
            objTblMod.insertRow();
////            configurarFrmFact();
////            configurarTblDatProceso();
            
            

                
            
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafCom113_03.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    
    public ZafCom113_03( ZafParSis obj, ArrayList arlItems, char estadoT) {
        
        try {
            this.objParSis = (ZafParSis) obj.clone();

            initComponents();
            configurarFrm();
            tipoGlobal=1;
            estado =estadoT;
            if (estado=='M')
                butGuardar.setText("Modificar");
            else
                butGuardar.setText("Crear");
            
////            lblMsgSis1.setVisible(false);
            butCons.setEnabled(true);     
            butGuardar.setEnabled(true);             
            LLenarTabla(arlItems);
            objTblMod.insertRow();
////            configurarFrmFact();
////            configurarTblDatProceso();
            
            

                
            
            lblTit.setText("Creacion-Modificacion de Grupo de Inventario");
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ZafCom113_03.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    
    
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
private boolean configurarFrmFact()//factor
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
////            strAux=objParSis.getNombreMenu();
////            this.setTitle(strAux + strVersion);
////            lblTit.setText(strAux);
            txtObser1.setBackground(objParSis.getColorCamposObligatorios());
            txtObser1.selectAll();
            txtObser1.requestFocus(); 
            configurarTblDatFact();  
            cargarFactores();
////            configurarTblDatProceso();
            
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
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            configurarTblDatItems();
            
            //Agregado por Eddye el 15/Sep/2022:
            butCons.setText("Detener");
            lblMsgSis1.setText("Obteniendo la información...");            
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis1.setText("Listo");
                pgrSis1.setValue(0);

                butCons.setText("Consultar");
                
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            objThrGUI =null;
            if (tblDatFor.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDatFor.setRowSelectionInterval(0, 0);
                tblDatFor.requestFocus();
            }
////                objThrGUI=null;            
            
        }
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
                strSQL+=" SELECT MAX(co_grpInv) AS co_grp";
                strSQL+=" FROM tbm_cabGrpInv  ";
                strSQL+=" WHERE st_reg='A'";
                System.out.println("tbm_cabMovInv: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next()){
                    intCodCfg=rstLoc1.getInt("co_grp")+1;
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
    
    private void LLenarTabla(ArrayList arlItems)//chiqui2
    {
        if (ConsultaComboTiposAgrupacion(tipoGlobal))
        {
            if(cargarDetRegGrupo(arlItems)){
                System.out.println("ok dos");
            }            
        }

    }
    
    private void LLenarTabla()//chiqui2
    {
        if (ConsultaComboTiposAgrupacion(tipoGlobal))
        {
            if(cargarDetRegGrupo()){
                System.out.println("ok tres");
            }            
        }

    }    

////        private boolean cargarCabReg(int tipo)//chiqui2
////    {
////        boolean blnRes=true;
////        try{ 
////            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            
////            if (con!=null){
////                stm=con.createStatement();
////              strSQL="";
////                strSQL+=" SELECT RTRIM (co_grpInv) codigo, RTRIM(tx_nomGrpInv) as descripcion";
////                strSQL+=" FROM tbm_cabGrpInv ";
////                if (tipo==0)
////                        strSQL+=" WHERE  st_reg ='A' ";           
////                else    
////                        strSQL+=" WHERE  st_reg ='A' and co_grpInv ='"+ txtIdGrupo.getText() +"'";           
////                System.out.println("cargarCabReg: " + strSQL);
////                rst=stm.executeQuery(strSQL);
////                if (rst.next()){
////                    if (tipo==0)
////                    {
////                        txtIdGrupo.setText("");
////                        txtDescGru.setText("");
////                    }
////                    strAux=rst.getString("codigo");
////                    txtIdGrupo.setText((strAux==null)?"":strAux);
////                    strAux=rst.getString("descripcion");
////                    txtDescGru.setText((strAux==null)?"":strAux);
////
////                    lblTit.setText(objParSis.getNombreMenu() + strVersion +"  "+ ((strAux==null)?"":strAux ));                   
////                }
////                else{
////                    limpiarFrm();
////                    blnRes=false;
////                }
////            }
////            rst.close();
////            stm.close();
////            con.close();
////            rst=null;
////            stm=null;
////            con=null;
////        }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
        
////    private boolean consultar_DetGrupo(java.sql.Connection conExt)
////    {
////        boolean blnRes=false;
////        
////        java.sql.Statement stmLoc;
////        java.sql.ResultSet rstLoc;
////        try{
////            if(conExt!=null){
////                stmLoc = conExt.createStatement();
////                    strSQL ="";
////                    strSQL+=" SELECT * from tbm_detGrpInv where co_grpInv = " + txtIdGrupo.getText() + " \n";
////                rstLoc = stmLoc.executeQuery(strSQL);
////                if(rstLoc.next())
////                    blnRes=true;
////                    
////                
////                rstLoc.close();
////                rstLoc=null;
////                stmLoc.close();
////                stmLoc=null;
////            }
////        }
////        catch (Exception e) {
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
private boolean borrar_tbmDetGrupoGlo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_detGrpInv where co_grpInv ="+ tipo +"";
                strSQL+="update tbm_inv set co_grpinv=0, co_detgrpinv =0, fe_ultMod='" + strFecUltMod + "' , tx_usrmod='" + objParSis.getNombreUsuario()+"'  where co_grpInv ="+ tipo +"  or co_detgrpinv = "+ tipo +" \n";
                System.out.println("borrar_tbmDetGrupoGlo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
private boolean borrar_tbmDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_detGrpInv where co_grpInv ="+ tipo +"; \n";
                strSQL+="update tbm_inv set co_grpinv=0, co_detgrpinv =0  where co_grpInv ="+ tipo +"  or co_detgrpinv = "+ tipo +" \n";
                System.out.println("borrar_tbmDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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


private boolean borrar_tbmCabGrupoZaf(java.sql.Connection conExtZaf, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExtZaf!=null){
                stm=conExtZaf.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="update tbm_inv set co_grpinv=0, co_detgrpinv =0  where co_grpInv ="+ tipo +"  or co_detgrpinv = "+ tipo +" \n";
                System.out.println("borrar_tbmCabGrupoZaf: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
private boolean borrar_tbmCabGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbm_cabGrpInv where co_grpInv ="+ tipo +" and co_tipAgr=1";
                System.out.println("borrar_tbmDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

private boolean borrar_tbhDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbh_detGrpInv where co_grpInv ="+ tipo +" ";
                System.out.println("borrar_tbhDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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


private boolean borrar_tbhCabGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE tbh_cabGrpInv where co_grpInv ="+ tipo +" ";
                System.out.println("borrar_tbhCabGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

private boolean borrarAgrupa (java.sql.Connection conExt,int tipo,java.sql.Connection connZAF)
{
    boolean blnRes=false;
    try
    {
////////            conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
////////            connZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());      
            
            conExt.setAutoCommit(false);  
            connZAF.setAutoCommit(false);  
            if(conExt!=null)
            {
                if (borrar_tbhDetGrupo(conExt,tipo))
                {
                    if (borrar_tbhCabGrupo(conExt,tipo))
                    {
                        if (borrar_tbmDetGrupoGlo(conExt,tipo))
                        {
                            if (borrar_tbmCabGrupo(conExt,tipo))
                            {
                                if (borrar_tbmCabGrupoZaf(connZAF,tipo))
                                    blnRes=true;    
                            }
                                
                        }
                        
                    }
                    
                }
            }            
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
        }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }    
   return blnRes;
   
   
               
            
}

private boolean anular_tbmCabDetGrupo(java.sql.Connection conExt, int tipo)//
    {
        boolean blnRes=true;
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabGrpInv set st_REg ='I' where co_grpInv ="+ tipo +"";
                System.out.println("anular_tbmDetGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

////private boolean modificar_tbmCabGrupo(java.sql.Connection conExt, int tipo)//
////    {
////        boolean blnRes=true;
////        String codigo="";
////        try{
////            
////            
////            if (conExt!=null)
////            {
////                stm=conExt.createStatement();
////                       
////                strSQL="";
////                if (tipo==0)
////                    strSQL+="update Dynamics2021..tbm_cabGrpInv set st_reg ='I' WHERE co_grpInv='" + grupoGlobal + "' ;";
////                else
////                    strSQL+="delete Dynamics2021..tbm_cabGrpInv WHERE co_grpInv='" + grupoGlobal + "' ;";
////                
////                System.out.println("borrar_tbmCabGrupo: " + strSQL);
////                 stm.executeUpdate(strSQL);
////
////                stm.close();
////                stm=null;
////            }
////            
////        }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    private ArrayList arlRegITEMSGRUPO,arlDatITEMSGRUPO,arlRegITEMZAF,arlDatITEMZAF;
    private static final int INT_ARL_CON_FAC=0;
    private static final int INT_ARL_CON_COD_ITM=1; 
    private static final int INT_ARL_CON_REG=2;     

////private boolean validaItemsZafiroGP(java.sql.Connection conZAF,int codIngresoGrp,ArrayList arlRegITEM)
////    {
////        java.sql.Statement stmLocZAF1;
////        java.sql.ResultSet rstLocZAF1;    
////
////        String strZAF="",strZAF1="";
////        boolean validaIngreso=false,blnRes=false;
////        double preVtaZa=0, preResul=0;
////        int i=0,indicador =0;
////        String codigoGP="";
////        try
////        {
////            if(conZAF!=null)
////            {
////                strZAF1="";
////                stmLocZAF1=conZAF.createStatement();
////                strZAF="";
////                strZAF+=" SELECT  distinct(trim(tx_codaltdyn)) as item \n";
////                strZAF+="  FROM    tbm_inv \n";
////                strZAF+=" where  co_grpinv ="+ codIngresoGrp +" or co_detgrpinv ="+ codIngresoGrp +"  \n";  
////
////                rstLocZAF1 = stmLocZAF1.executeQuery(strZAF);
////
////                  while(rstLocZAF1.next())
////                  {
////                    indicador =0;
////                    codigoGP = rstLocZAF1.getString("item");
////////                    strZAF1="";
////                    for (i=0; i<arlRegITEM.size();i++) 
////                    {
////                        if(codigoGP.equals(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM)))
////                            indicador++;
////                    }
////                    if (indicador==0)
////                    {
////                        strZAF1+=" \n UPDATE tbm_inv";
////                        strZAF1+=" \n  SET co_grpinv= 0";
////                        strZAF1+=" \n  , co_detgrpinv= 0";
////                        strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
////                        strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
////                        strZAF1+=" ;\n ";
////////                        stmLocZAF.executeUpdate(strZAF1);
////                    }
////                  }
////                  if (!strZAF1.equals(""))
////                   {
////                       stmLocZAF1.executeUpdate(strZAF1);
////                       blnRes=true;
////                   }   
////                      
////                    
////                  stmLocZAF1.close();
////                  stmLocZAF1=null;
////                  rstLocZAF1.close();
////                  rstLocZAF1=null;
////            }
////        }
////        catch(java.sql.SQLException e){ 
////            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
////            blnRes=false;
////         }
////         catch (Exception e) {
////            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
////           blnRes=false;
////         }
////        return blnRes;
////    }   
private boolean validaItemsZafiroGP(java.sql.Connection conZAF,int codIngresoGrp,ArrayList arlRegITEM, int tipo)
    {
        java.sql.Statement stmLocZAF1;
        java.sql.ResultSet rstLocZAF1;    

        String strZAF="",strZAF1="";
        boolean validaIngreso=false,blnRes=false;
        double preVtaZa=0, preResul=0;
        int i=0,indicador =0;
        String codigoGP="";
        try
        {
            if(conZAF!=null)
            {
                stmLocZAF1=conZAF.createStatement();
                if (tipo==1)
                {
                    strZAF1="";
                    
                    strZAF="";
                    strZAF+=" SELECT  distinct(trim(tx_codaltdyn)) as item \n";
                    strZAF+="  FROM    tbm_inv \n";
                    strZAF+=" where  co_grpinv ="+ codIngresoGrp +" or co_detgrpinv ="+ codIngresoGrp +"  \n";  

                    rstLocZAF1 = stmLocZAF1.executeQuery(strZAF);

                      while(rstLocZAF1.next())
                      {
                        indicador =0;
                        codigoGP = rstLocZAF1.getString("item");
////                        codigoGP = rstLocZAF1.getString("item");
    ////                    strZAF1="";
                        for (i=0; i<arlRegITEM.size();i++) 
                        {
                            if(codigoGP.equals(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM)))
                            {
                                indicador++;
                                if (Integer.parseInt(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_REG))==1)
                                {
                                    strZAF1+=" \n UPDATE tbm_inv";
                                    strZAF1+=" \n  SET co_grpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_detgrpinv= 0";
                                    strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                    strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                    strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                                    strZAF1+=" ;\n ";                                
                                }
                                else
                                {
                                    strZAF1+=" \n UPDATE tbm_inv";
                                    strZAF1+=" \n  SET co_grpinv= 0";
                                    strZAF1+=" \n  , co_detgrpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                    strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                    strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                                    strZAF1+=" ;\n ";                                                                
                                }   
                                break;
                            }
                            
                        }
                        if (indicador==0)
                        {
                            strZAF1+=" \n UPDATE tbm_inv";
                            strZAF1+=" \n  SET co_grpinv= 0";
                            strZAF1+=" \n  , co_detgrpinv= 0";
                            strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                            strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";     
                            strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                            strZAF1+=" ;\n ";
    ////                        stmLocZAF.executeUpdate(strZAF1);
                        }
                      }
                        indicador=0;
                        for (i=0; i<arlRegITEM.size();i++) 
                        {
                            indicador++;
                            if (Integer.parseInt(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_REG))==1)
                            {
                                strZAF1+=" \n UPDATE tbm_inv";
                                strZAF1+=" \n  SET co_grpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                strZAF1+=" \n  , co_detgrpinv= 0";
                                strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                strZAF1+=" \n  WHERE tx_codaltdyn ='"+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM) +"'";
                                strZAF1+=" ;\n ";                                
                            }
                            else
                            {
                                strZAF1+=" \n UPDATE tbm_inv";
                                strZAF1+=" \n  SET co_grpinv= 0";
                                strZAF1+=" \n  , co_detgrpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                strZAF1+=" \n  WHERE tx_codaltdyn ='"+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM) +"'";
                                strZAF1+=" ;\n ";                                                                
                            }   
                        }                      

                      if (!strZAF1.equals(""))
                       {
                           stmLocZAF1.executeUpdate(strZAF1);
                           blnRes=true;
                       }   


                      stmLocZAF1.close();
                      stmLocZAF1=null;
                      rstLocZAF1.close();
                      rstLocZAF1=null;                    
                }
                else
                {
                    if (tipo==0)
                    {
                        for (i=0; i<arlRegITEM.size();i++) 
                        {
                                
                                if (Integer.parseInt(objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_REG))==1)
                                {
                                    strZAF1+=" \n UPDATE tbm_inv";
                                    strZAF1+=" \n  SET co_grpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_detgrpinv= 0";
                                    strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                    strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                    strZAF1+=" \n  WHERE tx_codaltdyn ='"+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM) +"'";
                                    strZAF1+=" ;\n ";                                
                                }
                                else
                                {
                                    strZAF1+=" \n UPDATE tbm_inv";
                                    strZAF1+=" \n  SET co_grpinv= 0";
                                    strZAF1+=" \n  , co_detgrpinv= "+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_FAC) +"";
                                    strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                                    strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                                    strZAF1+=" \n  WHERE tx_codaltdyn ='"+ objUti.getStringValueAt(arlRegITEM, i, INT_ARL_CON_COD_ITM) +"'";
                                    strZAF1+=" ;\n ";                                                                
                                }                            
                        }
                        if (!strZAF1.equals(""))
                        {
                            stmLocZAF1.executeUpdate(strZAF1);
                            blnRes=true;
                        }
                        stmLocZAF1.close();
                        stmLocZAF1=null;

                    }
                        
                }

                  
            }
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR Validacion3LCostoWinv03 "+ e );
           blnRes=false;
         }
        return blnRes;
    }       

private boolean insertar_tbmDetGrupo(java.sql.Connection conExt,int tipo,java.sql.Connection connZAF)//chiqui_antiguo
    {
        String strSQLGlobal="",strZAFGlobal="";
        int contador=0;
        int ordenamiento=0;
        boolean blnRes=true;
        String codigo="";
        java.sql.Statement stmZAF;
        String strZAF ="";  
        String str_factor="",str_item="";
        try{
            
            
            if (conExt!=null)
            {
                stm=conExt.createStatement();
////                stmZAF=connZAF.createStatement();                  
////                objTblMod.removeEmptyRows();
////                ordenarDatPorOrden();
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                int codIngresoGrp=-1;                
                arlDatITEMSGRUPO= new ArrayList();
                for (int i=0; i<intNumFil;i++)
                {
                    codigo =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));




                    
                 
                    if  (codigo !="Null")
                    {
                        nd_orden =i+1;
                                            //Armar la sentencia SQL.
////                        contador = contador +1;
////                        ordenamiento =  Integer.valueOf(objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN).toString()) ;
////                        if (contador != ordenamiento)
////                        {
////                            blnRes=false;
////                            mensajeError ="El ordenamienro de la tabla se encuentra sin secuencia";
////                            break;
////                        }
                        
                        strSQL="";
                        strSQL+="\n INSERT INTO Dynamics2021..tbm_detGrpInv(";
                        strSQL+="        co_grpInv,co_reg,tx_codItm,st_mosItmLisPre,co_facModPre,fe_ing,fe_ultmod,co_usring,co_usrmod)";
                        strSQL+=" VALUES (";
                        if (tipo==0)
                            codIngresoGrp =intCodCfg;
////                            strSQL+=" " + intCodCfg + ""; //
                        else
                         codIngresoGrp =codigoGrupoGlobal;
                        strSQL+=" " + codIngresoGrp + ""; //
                        //zafiro
////                        if (nd_orden ==1)
////                            strZAF+="\n UPDATE tbm_inv set co_grpinv = "+codIngresoGrp +" ";
////                        else
////                            strZAF+="\n UPDATE tbm_inv set co_detgrpinv = "+codIngresoGrp +" ";
                        

                     
                     
                        //strZAF+="WHERE tx_codaltdyn = '"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"' ;";      
                        
                        
////                        strSQL+=", "+ objTblMod.getValueAt(i,INT_TBL_DAT_ORDEN) +""; //
                        strSQL+=", "+ nd_orden +""; //                        
                        strSQL+=", '" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) + "'"; //co_tipdoc  
                        if (nd_orden==1)
                            strSQL+=", 'S'";
                        else
                            strSQL+=", 'N'";
                        if (nd_orden>1)
                        {
                            String[] partes = objTblMod.getValueAt(i,INT_TBL_DAT_CMB_FACTOR).toString().split("-");
                            if (partes.length >1)
                                strSQL +=","+partes[0] +"";
                             else   
                                strSQL +=",0"; 
                        }
                        else
                             strSQL +=",0";
                        
                        //strSQL+=", 0"; //      
                        strSQL+=", '"+ strFecUltMod +"'";                            
                        strSQL+=", '"+ strFecUltMod +"'"; 
                        strSQL+=", "+ objParSis.getCodigoUsuario() +"" ;
                        strSQL+=", "+ objParSis.getCodigoUsuario() +"" ;                        
                        strSQL+=");";
                        
                        if (nd_orden ==1)
                        {
////                            strZAF+="\n UPDATE tbm_inv set co_grpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +" , co_detgrpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'";                        
                            strSQL+="\n UPDATE Dynamics2021..tbm_inv set co_grpinv = "+codIngresoGrp +" , co_detgrpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"';";                        
                        }
                        else
                        {
////                            strZAF+="\n UPDATE tbm_inv set co_detgrpinv = "+objTblMod.getValueAt(i,INT_TBL_DAT_FACTOR) +", co_grpinv =0,co_usrmod ="+ objParSis.getCodigoUsuario() +", fe_ultmod '"+ strFecUltMod + "' where tx_codaltdyn ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"' ";
                            strSQL+="\n UPDATE Dynamics2021..tbm_inv set co_detgrpinv = "+codIngresoGrp+", co_grpinv =0,tx_usrmod ='"+ objParSis.getNombreUsuario() +"', fe_ultmod ='"+ strFecUltMod + "' where tx_itm ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE) +"'; ";
                        }                        

                        strSQLGlobal += strSQL;
////                        strZAFGlobal += strZAF;
                        arlRegITEMSGRUPO = new ArrayList();
                        arlRegITEMSGRUPO.add(INT_ARL_CON_FAC,codIngresoGrp);
                        arlRegITEMSGRUPO.add(INT_ARL_CON_COD_ITM,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                        arlRegITEMSGRUPO.add(INT_ARL_CON_REG,nd_orden);
                        arlDatITEMSGRUPO.add(arlRegITEMSGRUPO);       
                        
                        if (i !=0)
                        {
                            str_factor=objTblMod.getValueAt(i,INT_TBL_DAT_CMB_FACTOR).toString();
                            str_item=objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE).toString();
                            if (str_factor.equals(""))
                            {
                                blnRes=false;
                                mensajeError="El item '"+str_item +"' no posee un factor asignado";
                                return blnRes;

                            }                        
                        }

                        
                    }

                    
                }
                if (blnRes ==true)
                {
                    if(validaItemsZafiroGP(connZAF,codIngresoGrp,arlDatITEMSGRUPO,tipo))
                    {
                        blnRes =true;
                        System.out.println("tbm_detGrpInv: " + strSQLGlobal);
                        stm.executeUpdate(strSQLGlobal);
////                        stmZAF.executeUpdate(strZAFGlobal);
                    }
                    else
                        blnRes=false;
                     
                     
                }

                stm.close();
                stm=null;
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

private boolean insertar_tbmCabGrupo(java.sql.Connection conExt, int tipo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="",itemPad="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
                for (int i=0; i<intNumFil;i++)
                {
                    if (i==0)
                    {
                        cadenaCabecera =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)+ "-"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM)); 
                        itemPad = objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE)); 
                        break;
                    }
                    
                }
            //modificar
                strSQL="";
                strSQL+="INSERT INTO tbm_cabGrpInv(";
                strSQL+="        co_grpInv,co_tipAgr,tx_nomGrpInv,tx_deslar,st_reg,fe_ing,co_usrIng,tx_itmPad,co_tipCla)";
                strSQL+=" VALUES (";
                if (tipo==0)
                    strSQL+=" " + intCodCfg + ""; //
                else
                    strSQL+=" " + codigoGrupoGlobal + "";
                strSQL+=", " + tipoAgrupa + ""; //                
                strSQL+=", " + cadenaCabecera + ""; //
                strSQL+=", " + cadenaCabecera+ ""; //     
                strSQL+=", 'A'";//ne_secEmp
                strSQL+=", '"+ strFecUltMod +"'";//
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//
                if (tipoAgrupa==1)
                 strSQL+=", "+ itemPad +",0";//   
                else
                {
                    if (tipoAgrupa==2)
                     strSQL+=", "+ itemPad +",1";   
                    else
                     strSQL+=", "+ itemPad +",2";   
                }                
////                strSQL+=", "+ itemPad +"";//
                strSQL+=");";
                System.out.println("tbm_cabGrpInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
private boolean modificar_tbmCabGrupo(java.sql.Connection conExt,int codigoGrupo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                String tx_itm ="";
                
                for (int i=0; i<intNumFil;i++)
                {
                    if (i==0)
                    {
                        cadenaCabecera =  objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT_ITM)+ "-"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM));   
                        tx_itm= objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE));
                        break;
                    }
                    
                }
            //modificar
                strSQL="";
                strSQL+="UPDATE  tbm_cabGrpInv set  tx_nomGrpInv =" + cadenaCabecera + "  ,co_tipCla=0 , tx_deslar = " + cadenaCabecera + " , co_usrmod = " + objParSis.getCodigoUsuario() + " ,fe_ultmod = '"+strFecUltMod +"'\n" ;
                strSQL+="        ,tx_itmPad = "+tx_itm +"";                
                strSQL+="  WHERE      co_grpInv = "+codigoGrupo +";";
                System.out.println("modificar_tbmCabGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

private boolean anular_tbmCabGrupo(java.sql.Connection conExt,int codigoGrupo)//chiqui_antiguo
    {
        boolean blnRes=true;
        String cadenaCabecera ="";
        try{
            if (conExt!=null){
                stm=conExt.createStatement();
                //Obtener la fecha del servidor.
////                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                //Armar la sentencia SQL.
                int intNumFil=objTblMod.getRowCountTrue();
                int nd_orden =0;
                
                strSQL="";
                strSQL+="UPDATE  tbm_cabGrpInv set st_Reg ='I' " ;
                strSQL+="  WHERE      co_grpInv = "+codigoGrupo +";";
                System.out.println("anular_tbmCabGrupo: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

////   private int valida_tbmCabGrupo()
////   {
////       int blnRes=0;
////        java.sql.Statement stmLoc1;
////        java.sql.ResultSet rstLoc1;
////        java.sql.Connection conExt;
////        try
////        {
////           conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////           
////            int intNumFil=objTblMod.getRowCountTrue();
////            if (conExt!=null)
////            {
////                stm=conExt.createStatement();
////                 if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
////                 {
////                     blnRes=-1;//hay error
////                 }
////                 else
////                 {
////                    stmLoc1=conExt.createStatement();
////                    strSQL="";
////                    strSQL+=" SELECT co_grpInv AS codigo,rtrim(tx_nomGrpInv) AS descripcion FROM tbm_cabGrpInv where st_reg='A' \n";
////                    strSQL+=" AND tx_nomGrpInv='" + txtDescGru.getText() + "'";
////
////                    System.out.println("SELECT " + strSQL);
////                    rstLoc1 = stmLoc1.executeQuery(strSQL);
////                    if(rstLoc1.next())
////                    {
////                        if (mostrarMsgCon("¿La descripción del grupo ya se encuentra , desea modificar el grupo de inventario?")==0)
////                        {
////                            if(borrar_tbmDetGrupo(conExt))
////                            {
////                                if(insertar_tbmDetGrupo(conExt))
////                                 blnRes=1;//modificado correcto
////                            }
////                            else
////                                blnRes=-1;//hay error
////                        }
////                        else
////                            blnRes=2;// no desea modificar
////                    }
////////                        blnRes=false;
////
////                      stmLoc1.close();
////                      stmLoc1=null;
////                      rstLoc1.close();
////                      rstLoc1=null;
////                 }                
////            }
////        }
////        catch (java.sql.SQLException e)
////        {
////            blnRes=-1;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            blnRes=-1;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////        }
   
   private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
   private boolean cargarDetRegGrupo(ArrayList arlItems)//chiqui
    {
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
                
            vecDat.clear();
            for (int i=0; i<arlItems.size();i++) 
            {
                vecReg=new Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    objUti.getStringValueAt(arlItems, i, INT_TBL_CON_ARR_COD_ITM));
                vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    objUti.getStringValueAt(arlItems, i, INT_TBL_ARR_COD_3L));
                vecReg.add(INT_TBL_DAT_NOM_ITM,     objUti.getStringValueAt(arlItems, i, INT_TBL_ARR_DESC));
                vecReg.add(INT_TBL_DAT_ORDEN,    "");
                vecReg.add(INT_TBL_DAT_FACTOR,   0);///12032024
                vecReg.add(INT_TBL_DAT_SELE,   true);///12032024
                vecReg.add(INT_TBL_DAT_CMB_FACTOR,  "");///12032024   
                vecDat.add(vecReg);
            }  
                objTblMod.setData(vecDat);
                tblDatGrupo.setModel(objTblMod);
                vecDat.clear();                
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
private boolean cargarDetRegGrupo()//chiqui
    {
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
//            if (!txtDescGru.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT trim(det.tx_codItm) as cod_itm,TRIM(iv.ITMGEDSC) as tx_codalt2,";
                    strSQL+="  RTRIM(iv.ITEMDESC) as item, det.co_reg,isnull(det.co_facModPre,0) as codFac, det.st_mosItmLisPre as estadoFac,";  
                    strSQL+="           case  when det.co_facModPre IS NULL OR det.co_facModPre = 0 ";                    
                    strSQL+="           THEN '' ";                    
                    strSQL+="           ELSE CONCAT(fac.co_facModPre,'-',trim(fac.tx_desCor),'-',fac.nd_fac) ";      
                    strSQL+="           END as desfactor ";                    
                    strSQL+=" FROM  Dynamics2021..tbm_detGrpInv as det  WITH (NOLOCK) ";
                    strSQL+="  inner join Dynamics2021..tbm_cabGrpInv as cab on (cab.co_grpInv = det.co_grpInv)  \n ";
                    strSQL+="  inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv WITH (NOLOCK)  on (iv.ITEMNMBR =det.tx_codItm)   \n ";
                    strSQL+="  inner join "+ objParSis.getNombreBaseDatosGP()  +"..[ALT_MIS_INFOADD] as p WITH (NOLOCK)  on (p.MASTERID =det.tx_codItm)   \n ";
                    strSQL+="  inner join "+ objParSis.getNombreBaseDatosGP()  +"..IV00108 as pre WITH (NOLOCK)  on (pre.ITEMNMBR = det.tx_codItm)    \n ";
                    strSQL+="  inner join Dynamics2021..tbm_facModPre  as fac on (det.co_facModPre = fac.co_facModPre)         \n ";
                    strSQL+="  WHERE cab.st_reg ='A' and pre.PRCLEVEL ='GENERAL'      AND   cab.co_tipAgr ="+ tipoGlobal +"   and iv.inactive=0  and cab.co_grpInv = "+ codigoGrupoGlobal +" \n";
                    strSQL+="  ORDER BY det.co_reg asc";
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    rst.getString("cod_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codalt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,    rst.getString("item"));
                        vecReg.add(INT_TBL_DAT_ORDEN,    "");
                        vecReg.add(INT_TBL_DAT_FACTOR,   rst.getInt("codFac"));///12032024
                        vecReg.add(INT_TBL_DAT_SELE,   rst.getString("estadoFac"));///12032024
                        vecReg.add(INT_TBL_DAT_CMB_FACTOR,   rst.getString("desfactor"));///12032024
                        
                         
                        vecDat.add(vecReg);
                    }
////////////                        vecReg=new Vector();
////////////                        vecReg.add(INT_TBL_DAT_LIN,"");
////////////                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,   "");
////////////                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,   "");
////////////                        vecReg.add(INT_TBL_DAT_BUT_ITM,"");
////////////                        vecReg.add(INT_TBL_DAT_NOM_ITM,  "");
////////////                        vecReg.add(INT_TBL_DAT_ORDEN,  "");
////////////                        vecDat.add(vecReg);
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDatGrupo.setModel(objTblMod);
                    vecDat.clear();
                    
                }
                else{
                    blnRes=false;
                    System.out.println("J1.. ");
                }
//            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    
private boolean cargarDetReg()//chiqui
{
    boolean blnRes=true;
    BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
    try{
        
            pgrSis1.setIndeterminate(true);
            butCons.setText("Detener");
            lblMsgSis1.setText("Obteniendo datos...");
            
            if (tipoAgrupa >=0)
            {
               objTblModItems.removeAllRows();
//            if (!txtDescGru.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
////                    strSQL="";

////                    strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
////                    strSQL+=" FROM TUVAL..IV00101 as iv01 ";
////////                    strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";     
////                    strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
////                    strSQL+=" FROM TUVAL..IV00101 as iv01 ";
////                    strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";   
////            strSQL="";
////            strSQL+="SELECT * FROM (SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, ";
////            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado";
////            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 ";
////            strSQL+="       left join(";
////            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm ";
////            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01"; 
////            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)";
////            strSQL+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)";            
////            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 AND cabG.st_reg ='A'  and cabg.co_tipAgr = "+ tipoAgrupa +" ";
////            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)";
////            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 "; 
            
            strSQL="";
            strSQL="SELECT * FROM (\n";            
            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as ITEMNMBR , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, \n";
            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado,iv01.INACTIVE,ica.codlinea,ica.codclase,ica.linea, ica.clase\n";
            strSQL+="       ,isnull(det1.co_facModPre,0) as codFac,det1.st_mosItmLisPre as estadoFac,iv01.ITMGEDSC \n";            
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 \n";
            strSQL+="       left join(\n";
            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm \n";
            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01\n"; 
            strSQL+="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)\n";
            strSQL+="                    LEFT JOIN Dynamics2021..tbm_cabGrpInv as cabG on (detG.co_grpInv = cabG.co_grpInv)\n";            
            strSQL+="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 AND cabG.st_reg ='A'  and cabg.co_tipAgr = "+ tipoAgrupa +" \n";
            strSQL+="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)\n";
            strSQL+="       left join view_ItemsClasiAtrib as ica on (iv01.ITEMNMBR =ica.CodItm)\n";   
            strSQL+="       left join Dynamics2021..tbm_detGrpInv as det1 on (iv01.ITEMNMBR = det1.tx_codItm)\n";               
            strSQL+="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')    ) as a1\n";             
   



////                    if (!optTod.isSelected())
////                    {
////                        if (objVenConItm03.isCondicionAplicada())
////                            strSQL+=   " WHERE " + objVenConItm03.getCondicionesSQL();
////                    }      

                    if (!optTod.isSelected())
                    {
                        if (objVenConItm01.isCondicionAplicada())
                            strSQL+=   " WHERE a1.INACTIVE =0  " + objVenConItm01.getCondicionesSQL();
                    } 

                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDatItems.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecRegItems=new Vector(); 
                        vecRegItems.add(INT_TBL_CON_LIN,"");
                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE,    rst.getString("ITEMNMBR"));
                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM,    rst.getString("tx_codalt2"));
                        vecRegItems.add(INT_TBL_CON_NOM_ITM,    rst.getString("item"));
                        vecRegItems.add(INT_TBL_CON_ORDEN, rst.getString("estado"));
                        vecRegItems.add(INT_TBL_CON_FACTOR, rst.getInt("codFac"));                        
                        vecRegItems.add(INT_TBL_CON_SELE, rst.getString("estadoFac"));
                        vecRegItems.add(INT_TBL_CON_CMB_FACTOR, "");                        
                        vecDatItems.add(vecRegItems);
                    }

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModItems.setData(vecDatItems);
                    tblDatFor.setModel(objTblModItems);
                    vecDatItems.clear();
                if (blnCon)
                    lblMsgSis1.setText("Se encontraron " + tblDatFor.getRowCount() + " registros.");
                else
                    lblMsgSis1.setText("Interrupción del usuario. Sólo se procesaron " + tblDatFor.getRowCount() + " registros.");
                butCons.setText("Consultar");
                pgrSis1.setIndeterminate(false);
                    
                }
                else{
                    blnRes=false;
                    System.out.println("J1.. ");
                }
            }
            else
               mostrarMsgInf("Se debe realizar la seleccion de un tipo de agrupación."); 

//            }
    }
    catch(java.sql.SQLException e){
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch(Exception e){
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}    
                  
private boolean configurarVenConItm()//chiqui2
{
    boolean blnRes=true;
    int intTipCnvIngEgr=0;
    try
    {
        //Listado de campos.
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_itmMae");
        arlCam.add("tx_codAlt2");
        arlCam.add("tx_nomItm");
        //Alias de los campos.
        ArrayList arlAli=new ArrayList();
        arlAli.add("Item");
        arlAli.add("3L");
        arlAli.add("Nombre"); 
        //Ancho de las columnas.
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("80");
        arlAncCol.add("80");
        arlAncCol.add("200");


////            strSQL="";
////            strSQL+="       SELECT rtrim(ITEMNMBR) AS co_itmMae,RTRIM(ITMGEDSC) as tx_codAlt2,  RTRIM (ITEMDESC) AS tx_nomItm  ";
////            strSQL+="       FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  ";
        strSQL="";
                strSQL+=" SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01 ";
                strSQL+=" WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";   
////            strSQL="";
////            strSQL+="SELECT rtrim(iv01.ITEMNMBR) as cod_itm , RTRIM(iv01.ITMGEDSC) as tx_codalt2 , RTRIM(iv01.ITEMDESC) as item,tot1.cod_itm as itemco, ";
////            strSQL+="       CASE WHEN tot1.cod_itm !='' THEN 'I' ELSE '0' end as estado";
////            strSQL+="FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as iv01 ";
////            strSQL+="       left join(";
////            strSQL+="                  SELECT distinct(rtrim(iv01.ITEMNMBR)) as cod_itm ";
////            strSQL+="                  FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv01"; 
////            strSQL="                    INNER JOIN Dynamics2021..tbm_detGrpInv  as detG on (iv01.ITEMNMBR = detG.tx_codItm)";
////            strSQL="                   WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0 ";
////            strSQL="                 ) as tot1 on (tot1.cod_itm =iv01.ITEMNMBR)";
////            strSQL="WHERE  (iv01.ITEMNMBR like '%-I' OR iv01.ITEMNMBR like '%-S')  and iv01.INACTIVE =0";            

        ////strSQL+="       ORDER BY b2.co_grp, b1.tx_codAlt";

        System.out.println("vcoItm: "+strSQL);

        //Ocultar Columnas
////            int intColOcu[]=new int[6];
////            intColOcu[0]=1;  // co_itmMae
////            intColOcu[1]=2;  // co_itmGrp
////            intColOcu[2]=3;  // co_itmEmp
//////            intColOcu[6]=14;  // nd_cosTotEgr

        vcoItm=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol);
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

        vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
        vcoItm.setCampoBusqueda(2);
    }
    catch (Exception e)
    {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

private boolean LLenarComboFactores()//chiqui
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
            strSQL+=" SELECT  CONCAT(co_facModPre,'-',trim(tx_desCor),'-',nd_fac) as desfactor  FROM Dynamics2021..tbm_facModPre WHERE st_reg ='A' AND co_facModPre !=0 order by co_facModPre asc";
////                strSQL+=" and co_tipAgr ="+tipo +"";                
            rstLoc = stmLoc.executeQuery(strSQL);
            objTblCelEdiCmbBox.removeAllItems();
           dataListFa.clear();
            while (rstLoc.next()){

                String data = rstLoc.getString("desfactor");
                dataListFa.add(data);
                blnRes=true;
            }
            if (blnRes)
            {
                DefaultComboBoxModel<String> modelFa = new DefaultComboBoxModel<>(dataListFa.toArray(new String[0]));
                objTblCelEdiCmbBox.removeAllItems();
                objTblCelEdiCmbBox.setModel(modelFa); 
//                    objTblCelEdiCmbBox.setEnabled(false);
                blnRes=true;
            }
            else
            {
                objTblCelEdiCmbBox.removeAllItems();
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

private boolean LLenarComboFactores2()//chiqui
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
            strSQL+=" SELECT  CONCAT(co_facModPre,'-',trim(tx_desCor),'-',nd_fac) as desfactor  FROM Dynamics2021..tbm_facModPre WHERE st_reg ='A' AND co_facModPre !=0 order by co_facModPre asc";
////                strSQL+=" and co_tipAgr ="+tipo +"";                
            rstLoc = stmLoc.executeQuery(strSQL);
            objTblCelEdiCmbBox2.removeAllItems();
           dataListFa2.clear();
            while (rstLoc.next()){

                String data = rstLoc.getString("desfactor");
                dataListFa2.add(data);
                blnRes=true;
            }
            if (blnRes)
            {
                DefaultComboBoxModel<String> modelFa = new DefaultComboBoxModel<>(dataListFa2.toArray(new String[0]));
                objTblCelEdiCmbBox2.removeAllItems();
                objTblCelEdiCmbBox2.setModel(modelFa); 
//                    objTblCelEdiCmbBox.setEnabled(false);
                blnRes=true;
            }
            else
            {
                objTblCelEdiCmbBox2.removeAllItems();
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
       
 private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);            
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            objVenConItm01=new ZafVenConItmGP01(objParSis, "a1.", optFil);
            panFiltros.add(objVenConItm01);
            objVenConItm01.setBounds(10, 98, 500, 20);
            objVenConItm01.setEnabled(true);
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            objHisTblBasDat=new ZafHisTblBasDat(); 
            
            configurarVenConItm();
            configurarTblDat();
            configurarTblDatItems();
            LLenarComboFactores();
            LLenarComboFactores2();
            
            LLenarComboTiposAgrupacion();
           // datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
           
           butCrearFactor.setVisible(false);
           butModificar.setVisible(false);
           butLimp.setVisible(false);
           butElimi.setVisible(false);           
           tabFrm1.setVisible(false);
        

////            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 private boolean configurarTblDat()//actual
    {
        boolean blnRes=true;
		
        try
        {
           //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Código");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "3L");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_ORDEN, "Orden");
            vecCab.add(INT_TBL_DAT_FACTOR, "Factor");
            vecCab.add(INT_TBL_DAT_SELE, "Seleccion");
            vecCab.add(INT_TBL_DAT_CMB_FACTOR, "Factor");            
			
            objTblMod=new ZafTblMod();
            
            objTblMod.setHeader(vecCab);
	    //Configurar JTable: Establecer el modelo de la tabla.
            tblDatGrupo.setModel(objTblMod);            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatGrupo);			
             //Configurar JTable: Establecer tipo de selección.
            tblDatGrupo.setRowSelectionAllowed(true);
            tblDatGrupo.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
            objTblPopMnuTbl=new ZafTblPopMnu(tblDatGrupo);
   
			
														 
											   
													  
														
													  
														
			
			
					  

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatGrupo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatGrupo.getColumnModel();
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(350); 
            tcmAux.getColumn(INT_TBL_DAT_ORDEN).setPreferredWidth(30);   
            tcmAux.getColumn(INT_TBL_DAT_FACTOR).setPreferredWidth(70); 
            tcmAux.getColumn(INT_TBL_DAT_SELE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CMB_FACTOR).setPreferredWidth(150);
            
																			  
			
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatGrupo.getTableHeader().setReorderingAllowed(false);          
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatGrupo);   
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatGrupo);   
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab); 
            //Habilitar Opción de Pegar.
//////            objTblPopMnu.setPegarEnabled(true);   
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN, tblDatGrupo); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FACTOR, tblDatGrupo); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SELE, tblDatGrupo); 
            lblMsgInf.setVisible(false);
////            lblMsgSis.setVisible(false);
            objMouMotAda=new ZafMouMotAda();
            tblDatGrupo.getTableHeader().addMouseMotionListener(objMouMotAda); 
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ORDEN, tblDatGrupo);                
            objTblMod.insertRow(); 
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_LIN);
            vecAux.add("" + INT_TBL_DAT_COD_ITM_MAE);
            //vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            //vecAux.add("" + INT_TBL_DAT_NOM_ITM);
            //vecAux.add("" + INT_TBL_DAT_ORDEN);
            //vecAux.add("" + INT_TBL_DAT_FACTOR);
            //vecAux.add("" + INT_TBL_DAT_SELE);
            vecAux.add("" + INT_TBL_DAT_CMB_FACTOR);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;                
            
            
            objTblCelRenCmbBox2=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_CMB_FACTOR).setCellRenderer(objTblCelRenCmbBox2); 
            
            objTblCelEdiCmbBox2 = new ZafTblCelEdiCbo(tblDatGrupo);//LA DE TIPOS
            tcmAux.getColumn(INT_TBL_DAT_CMB_FACTOR).setCellEditor(objTblCelEdiCmbBox2);
            objTblCelEdiCmbBox2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                 
                }
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    
                }
            });  
            
            
//////////            objTblMod.addTableModelListener(new TableModelListener() {
//////////            @Override
//////////            public void tableChanged(TableModelEvent e) {
//////////                // Verificar que el evento es de tipo UPDATE
//////////                if (e.getType() == TableModelEvent.UPDATE) {
//////////                    int row = e.getFirstRow();
//////////                    int column = e.getColumn();
//////////                    ArrayList arlDatItem;
//////////                    
//////////                    if (column ==INT_TBL_DAT_COD_ITM_MAE)
//////////                    {
//////////                        // <editor-fold desc="PARA LA BUSQUEDA DEL ITEM">
//////////                          arlDatItem =(obtenerDatosItems(objTblMod.getValueAt(row, column).toString()));
//////////                          if (arlDatItem.size() > 0)
//////////                          {
//////////                              for (int i=0; i<arlDatItem.size();i++) 
//////////                              {
//////////                                  if (objUti.getStringValueAt(arlDatItem, i, 0).equals(0))
//////////                                  {
//////////                                      objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 0), row, INT_TBL_DAT_NOM_ITM);
//////////                                      objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 1), row, INT_TBL_DAT_COD_ALT_ITM);
//////////                                      
//////////                                  }
//////////                                  else
//////////                                  {
//////////////                                      objTblMod.setValueAt("", row, INT_TBL_DAT_COD_ITM_MAE);
//////////                                      mostrarMsgInf("El item que quiere ingresar , ya se encuentra dentro de un grupo");
//////////                                      objTblMod.removeRows(row, row);
//////////                                      objTblMod.insertRow();
//////////                                  }
//////////                                      
//////////                              }
//////////                          }
//////////                          else
//////////                          {
//////////////                                      objTblMod.setValueAt("", row, INT_TBL_DAT_COD_ITM_MAE);
//////////                                      mostrarMsgInf("El item que quiere ingresar , no existe en el catalogo de inventario");
//////////                                      
//////////                                      objTblMod.insertRow();
//////////                          }
//////////                          // </editor-fold>
//////////                    }
//////////             //       Object newData = objTblMod.getValueAt(row, column);
//////////                 
//////////                    // Aquí puedes realizar la acción que desees con los datos actualizados
//////////                }
//////////            }
//////////        });

            //Configurar JTable: Establecer el menú de contexto.



            

////            objTblPopMnu=new ZafTblPopMnu(tblDatGrupo);
            objTblPopMnuTbl.setPegarEnabled(true);
            objTblPopMnuTbl.setBorrarContenidoEnabled(true);
            objTblPopMnuTbl.setInsertarFilaEnabled(true);
            objTblPopMnuTbl.setInsertarFilasEnabled(false);
            objTblPopMnuTbl.setEliminarFilaEnabled(true);
            objTblPopMnuTbl.setEliminarFilaVisible(true);            
            objTblPopMnuTbl.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuTbl.isClickPegar())                    
                    {
                        int intFilSel[];
                        int  i;
                        ArrayList arlDatItem;
                        intFilSel=tblDatGrupo.getSelectedRows();
                        if (tblDatGrupo.isColumnSelected(INT_TBL_DAT_COD_ITM_MAE))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                arlDatItem =(obtenerDatosItems(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COD_ITM_MAE).toString())); 
                                if (isExisteItemRepetido(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_COD_ITM_MAE).toString()))
                                {
                                    objTblMod.setValueAt("", intFilSel[i], INT_TBL_DAT_COD_ITM_MAE);
                                    mostrarMsgInf("El item que quiere ingresar ,ya se enecuentra ingresado para poder agrupar");
                                    break;
                                }    
                                  if (arlDatItem.size() > 0)
                                  {
                                      for (int ii=0; ii<arlDatItem.size();ii++) 
                                      {
                                          if (objUti.getStringValueAt(arlDatItem, ii, 2).equals("0"))
                                          {
                                              objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, ii, 0), intFilSel[i], INT_TBL_DAT_NOM_ITM);
                                              objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, ii, 1), intFilSel[i], INT_TBL_DAT_COD_ALT_ITM);

                                          }
                                          else
                                          {
                                              objTblMod.setValueAt("", intFilSel[i], INT_TBL_DAT_COD_ITM_MAE);
                                              mostrarMsgInf("El item que quiere ingresar , ya se encuentra dentro de un grupo");


                                          }

                                      }
                                  }
                                  else
                                  {
                                              objTblMod.setValueAt("", intFilSel[i], INT_TBL_DAT_COD_ITM_MAE);
                                              mostrarMsgInf("El item que quiere ingresar , no existe en el catalogo de inventario");
                                  }                                 
                            }    
                            
                            
                        }    
                            
                    }
                    if (objTblPopMnuTbl.isClickEliminarFila())
                    { 
                        int intFilSel;
                        int  i;
                        ArrayList arlDatItem;
                        intFilSel=tblDatGrupo.getSelectedRow();                        
                        objTblMod.removeRow(intFilSel);
                    }
        
                    
                }
                });


objTblCelEdiTxtEgr=new ZafTblCelEdiTxt(tblDatGrupo);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellEditor(objTblCelEdiTxtEgr);
            objTblCelEdiTxtEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                ArrayList arlDatItem;                
                String strTipRelEgr="";
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("DDDDDDDDDDDDDDDDDDDDD");
                    intFil=tblDatGrupo.getSelectedRow();
                    arlDatItem =(obtenerDatosItems(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString())); 
                      if (arlDatItem.size() > 0)
                      {
                          for (int i=0; i<arlDatItem.size();i++) 
                          {
                              if (objUti.getStringValueAt(arlDatItem, i, 2).equals("0"))
                              {
                                  objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 0), intFil, INT_TBL_DAT_NOM_ITM);
                                  objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 1), intFil, INT_TBL_DAT_COD_ALT_ITM);

                              }
                              else
                              {
                                  objTblMod.setValueAt("", intFil, INT_TBL_DAT_COD_ITM_MAE);
                                  mostrarMsgInf("El item que quiere ingresar , ya se encuentra dentro de un grupo");
////                                  objTblMod.removeRow(intFil);
////                                  objTblMod.insertRow();
                                  
                              }

                          }
                      }
                      else
                      {
                                  objTblMod.setValueAt("", intFil, INT_TBL_DAT_COD_ITM_MAE);
                                  mostrarMsgInf("El item que quiere ingresar , no existe en el catalogo de inventario");

////                                  objTblMod.insertRow();
                      }                    
                    
                    objTblCelEdiTxtEgr.setCancelarEdicion(true);
                    
                    if(objParSis.getCodigoMenu()==4202){//Egreso
                        if(strTipRelEgr.equals("E"))
                            objTblCelEdiTxtEgr.setCancelarEdicion(false);
                    }
                }
////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////////                    arlDatItem =(obtenerDatosItems(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString())); ;
////////                      if (arlDatItem.size() > 0)
////////                      {
////////                          for (int i=0; i<arlDatItem.size();i++) 
////////                          {
////////                              if (objUti.getStringValueAt(arlDatItem, i, 0).equals(0))
////////                              {
////////                                  objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 0), intFil, INT_TBL_DAT_NOM_ITM);
////////                                  objTblMod.setValueAt(objUti.getStringValueAt(arlDatItem, i, 1), intFil, INT_TBL_DAT_COD_ALT_ITM);
////////
////////                              }
////////                              else
////////                              {
////////                                  objTblMod.setValueAt("", intFil, INT_TBL_DAT_COD_ITM_MAE);
////////                                  mostrarMsgInf("El item que quiere ingresar , ya se encuentra dentro de un grupo");
////////                                  objTblMod.insertRow();
////////                              }
////////
////////                          }
////////                      }
////////                      else
////////                      {
////////                                  objTblMod.setValueAt("", intFil, INT_TBL_DAT_COD_ITM_MAE);
////////                                  mostrarMsgInf("El item que quiere ingresar , no existe en el catalogo de inventario");
////////
////////                                  objTblMod.insertRow();
////////                      }
////////////                    generaDiarioAjuste();
////////                }
            });
            
            
            
            
            
            

            
//<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
         
            tblDatGrupo.setDragEnabled(true);
            tblDatGrupo.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatFor.setDropTarget(new DropTarget()
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovFor[];
                    intFilMovFor = tblDatFor.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatGrupo.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowEmpOrigen = tblDatGrupo.getSelectedRow();
                    //System.out.println("intRowEmpOrigen: " + intRowEmpOrigen);
                    
                    int intRowForPtoDestino = tblDatFor.rowAtPoint(point);
                    //System.out.println("intRowForPtoDestino: " + intRowForPtoDestino);
                    
                    int intRowForDestino = tblDatFor.getSelectedRow();
                    //System.out.println("intRowForDestino: " + intRowForDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;
                    boolean blnValCtaExiFor=false;
                    
////                    if (intRowEmpOrigen > -1 && intRowForDestino == -1 && intRowForPtoDestino>0) //TRASLADAR CUENTAS A OTRO JTABLE.
                    if (intRowEmpOrigen > -1 && intRowForDestino == -1 ) //TRASLADAR CUENTAS A OTRO JTABLE.                        
                    {
                        
                    
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        if(blnAdd)
                        {
                            //AGREGAR ELEMENTOS A TBLDATFOR
                            for (int j = 0; j < intFilSel.length; j++) 
                            {
                                if (intFilSel[j] > -1 && intRowForDestino == -1) // TENGA VALORES 
                                {
                                    strTipRegLin = objTblMod.getValueAt(intFilSel[j], INT_TBL_DAT_LIN).toString();

                                    vecReg = new Vector();
                                    vecReg.add(INT_TBL_DAT_LIN, strTipRegLin.equals("E") ? "C" : "N");
                                    vecReg.add(INT_TBL_CON_COD_ITM_MAE, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ITM_MAE));
                                    vecReg.add(INT_TBL_CON_COD_ALT_ITM, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_COD_ALT_ITM));
                                    vecReg.add(INT_TBL_CON_NOM_ITM, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_NOM_ITM));
                                    vecReg.add(INT_TBL_CON_ORDEN, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_ORDEN));
                                    vecReg.add(INT_TBL_CON_FACTOR, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_FACTOR));
                                    vecReg.add(INT_TBL_CON_SELE, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_SELE));
                                    vecReg.add(INT_TBL_CON_CMB_FACTOR, tblDatGrupo.getValueAt(intFilSel[j], INT_TBL_DAT_CMB_FACTOR));
                                    boolean blnBoo = false;
                                    for (int i = 0; i < objTblModItems.getRowCount(); i++)
                                    {
                                        if (i == intRowForPtoDestino && !blnBoo) 
                                        {
                                            vecDatItems.add(vecReg);
                                            i--;
                                            blnBoo = true;
                                        }
                                        else 
                                        {
                                            vecDatItems.add(objTblModItems.getData().get(i));
                                        }
                                    }
                                    objTblModItems.setData(vecDatItems);
                                    tblDatFor.setModel(objTblModItems);
                                    vecDatItems.clear();
                                    intRowForPtoDestino++;
                                }
                            }
                    
                            //ELIMINAR REGISTROS TBLDATEMP
                            boolean blnExi = false;

                            for (int i = 0; i < objTblMod.getRowCount(); i++)
                            {
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (i == intFilSel[j]) 
                                    {
                                        blnExi = true;
                                    }
                                }
                                if (!blnExi) 
                                {
                                    vecDat.add(objTblMod.getData().get(i));
                                }
                                blnExi = false;

                            }
                            objTblMod.setData(vecDat);
                            tblDatGrupo.setModel(objTblMod);
                            vecDat.clear();
                        }
                    //</editor-fold>
                    
                    }
                    else if (intRowForDestino > -1 && intRowEmpOrigen > -1 && intRowForPtoDestino > -1) 
                    {
                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////                        limpiarJtableSelection();
                    }
                    else if ( intRowForPtoDestino <=0 ) 
                    {
                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
////                        limpiarJtableSelection();
                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovFor.length; i++) 
                        {
                            if (intFilMovFor[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-FOR!!");
                            //System.out.println("intFilMovFor: " + intFilMovFor + " intRowForPtoDestino: " + intRowForPtoDestino);
                            //objTblModFor.moveRow(intRowForDestino, intRowForPtoDestino);
                            objTblModItems.moveRows(intFilMovFor, intRowForPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se pueden mover cuentas ubicadas al inicio del plan de cuentas.");
////                            limpiarJtableSelection();
                        }
                                
                    }
                }
            });
            //</editor-fold>   
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        



            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    private ArrayList obtenerDatosItems(String item)
    {
        ArrayList arlRegItem,arlDatItem;
        java.sql.Statement stmLoc1 = null;
        java.sql.ResultSet rstLoc1 = null;
        java.sql.Connection conExt1;
        arlDatItem = new ArrayList();
        try
        {
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt1!=null){
                stmLoc1=conExt1.createStatement();
              strSQL="";
                strSQL+=" SELECT isnull (det.co_grpInv,0) as factor, trim(iv.ITEMDESC) as descripcion , trim (iv.ITMGEDSC) as tresL ";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as iv ";
                strSQL+="       LEFT JOIN   dynamics2021..tbm_detGrpInv as det  on (iv.ITEMNMBR =det.tx_codItm ) ";                
                strSQL+="       LEFT JOIN   dynamics2021..tbm_cabgrpinv as cab on (det.co_grpInv = cab.co_grpInv) ";                
                strSQL+=" WHERE iv.INACTIVE =0  AND ITEMNMBR ="+ objUti.codificar(item)  +"";                
                
                System.out.println("obtenerDatosItems: " + strSQL);
                
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next())
                {
                    arlRegItem = new ArrayList();
                    arlRegItem.add(0,rstLoc1.getString("descripcion"));
                    arlRegItem.add(1,rstLoc1.getString("tresL"));
                    arlRegItem.add(2,rstLoc1.getString("factor"));
                    arlDatItem.add(arlRegItem);
                }
                    
            }
            rstLoc1.close();
            stmLoc1.close();
            conExt1.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt1=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
         
         
        return arlDatItem;
    }
    
private boolean configurarTblDatItems()//actual
    {
        boolean blnRes=true;
		
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatItems=new Vector();    //Almacena los datos
            vecCabItems=new Vector();  //Almacena las cabeceras
            vecCabItems.clear();
            vecCabItems.add(INT_TBL_CON_LIN,"");
            vecCabItems.add(INT_TBL_CON_COD_ITM_MAE, "Código");
            vecCabItems.add(INT_TBL_CON_COD_ALT_ITM, "3L");
            vecCabItems.add(INT_TBL_CON_NOM_ITM, "Nombre");
            vecCabItems.add(INT_TBL_CON_ORDEN, "Orden");
            vecCabItems.add(INT_TBL_CON_FACTOR, "Factor");
            vecCabItems.add(INT_TBL_CON_SELE, "Seleccion");
            vecCabItems.add(INT_TBL_CON_CMB_FACTOR, "CMBfactor");
            
            objTblModItems=new ZafTblMod();
            objTblModItems.setHeader(vecCabItems);
	    //Configurar JTable: Establecer el modelo de la tabla.
            tblDatFor.setModel(objTblModItems);
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDatFor);            
	    //Configurar JTable: Establecer tipo de selección.
            tblDatFor.setRowSelectionAllowed(true);
            tblDatFor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
			//Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModItems.setColumnDataType(INT_TBL_CON_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModItems.setColumnDataType(INT_TBL_CON_COD_ITM_MAE, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));

            
			
////            //Configurar JTable: Establecer el menú de contexto. ES PARA LAS OPCIONES CON CLICK DERECHO DEL MOUSE
            objTblPopMnu=new ZafTblPopMnu(tblDatFor);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(true);
            objTblPopMnu.setInsertarFilasEnabled(false);
            objTblPopMnu.setEliminarFilaEnabled(true);
            objTblPopMnu.setEliminarFilaVisible(true);  
            
            
                      

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFor.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatFor.getColumnModel();
////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CON_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CON_COD_ALT_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CON_NOM_ITM).setPreferredWidth(350);
            tcmAux.getColumn(INT_TBL_CON_ORDEN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_CON_FACTOR).setPreferredWidth(70);            
            tcmAux.getColumn(INT_TBL_CON_SELE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CON_CMB_FACTOR).setPreferredWidth(150);
            
                        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatFor.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDatFor); 
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatFor);
            tcmAux.getColumn(INT_TBL_CON_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_CON_LIN);
            vecAux.add("" + INT_TBL_CON_COD_ITM_MAE);
            vecAux.add("" + INT_TBL_CON_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_CON_NOM_ITM);
            vecAux.add("" + INT_TBL_CON_ORDEN);
            vecAux.add("" + INT_TBL_CON_FACTOR);
            vecAux.add("" + INT_TBL_CON_SELE);
            vecAux.add("" + INT_TBL_CON_CMB_FACTOR);
            objTblModItems.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_ORDEN, tblDatFor); 
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_FACTOR, tblDatFor); 
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_SELE, tblDatFor); 
            objTblModItems.addSystemHiddenColumn(INT_TBL_CON_CMB_FACTOR, tblDatFor); 
            
            
            objMouMotAdaItems=new ZafMouMotAdaItems();
            tblDatFor.getTableHeader().addMouseMotionListener(objMouMotAdaItems);
            objTblCelRenCmbBox=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_CON_CMB_FACTOR).setCellRenderer(objTblCelRenCmbBox); 
            
            objTblCelEdiCmbBox = new ZafTblCelEdiCbo(tblDatFor);//LA DE TIPOS
            tcmAux.getColumn(INT_TBL_CON_CMB_FACTOR).setCellEditor(objTblCelEdiCmbBox);
            objTblCelEdiCmbBox.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                 
                }
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    
                }
            });
            
            
            
     
////////////////nueavamente
            //<editor-fold defaultstate="collapsed" desc="//Habilitar la opción de Arrastrar y Soltar (Drag and Drop).">
            tblDatFor.setDragEnabled(true);
            tblDatFor.setDropMode(DropMode.INSERT_ROWS);
            
            tblDatGrupo.setDropTarget(new DropTarget() 
            {
                @Override
                public synchronized void drop(DropTargetDropEvent dtde) 
                {
                    //Filas a Mover.
                    int intFilMovEmp[];
                    intFilMovEmp = tblDatGrupo.getSelectedRows();
                    
                    //Filas Seleccionadas.
                    int intFilSel[];
                    intFilSel = tblDatFor.getSelectedRows();
                    
                    Point point = dtde.getLocation();
                    
                    int intRowForOrigen = tblDatFor.getSelectedRow();                    
                    //System.out.println("intRowForOrigen: " + intRowForOrigen);
                    
                    int intRowEmpPtoDestino = tblDatGrupo.rowAtPoint(point);
                    //System.out.println("intRowEmpPtoDestino: " + intRowEmpPtoDestino);
                    
                    int intRowEmpDestino = tblDatGrupo.getSelectedRow();
                    //System.out.println("intRowEmpDestino: " + intRowEmpDestino);
                    
                    dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                    
                    boolean blnAdd=true;
                    boolean blnValCtaDet=false;

////                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 && intRowEmpPtoDestino>0)
                    if (intRowForOrigen > -1 && intRowEmpDestino == -1 )                        
                    {
                        //<editor-fold defaultstate="collapsed" desc="/* VALIDACIONES */">
////                        for(int j=0 ; j<intFilSel.length;j++)
////                        {
////                            if (intFilSel[j] > -1 && intRowEmpDestino == -1) 
////                            {
////                                strIsCtaCab = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_EST_CAB).toString();
////                                strTipRegLin = objTblModFor.getValueAt(intFilSel[j], INT_TBL_DAT_FOR_LIN).toString();
////
////                                if (strIsCtaCab.equals("false")) 
////                                {
////                                    System.out.println("DUMMY CODE!");
////                                }
////                                else 
////                                {
////                                    blnAdd=false;
////                                    blnValCtaDet=true;
////                                }
////                            }
////                        }
////                        if (blnValCtaDet) {
////                            mostrarMsgInf("Solo se pueden trasladar Cuentas de Detalle.");
////                            limpiarJtableSelection();
////                        }
                        
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* DRAP AND DROP */">
                        blnAdd=true;
                        if(blnAdd)
                            
                        {
                            String itemAcopiar ="";  
                            boolean blnRepe=false;                            
                                //VALIDACION SI EXISTE UN REPETIDO
                                for (int k = 0; k < intFilSel.length; k++) 
                                {
                                        itemAcopiar = tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_COD_ITM_MAE).toString();
                                        if ((tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_ORDEN).toString().equals("I")) || (tblDatFor.getValueAt(intFilSel[k], INT_TBL_CON_ORDEN).toString().equals("M")))
                                        {
                                             blnRepe=true;
                                             break;
                                        }
////                                        if (isExisteItemRepetido(1,itemAcopiar))
////                                        {
////                                            blnRepe=true;
////                                            break;
////                                        }
                                }                              
                            
                            
                            
                            if(!blnRepe)
                            { 
                            //AGREGAR ELEMENTOS A TBLDATEMP
                                for (int j = 0; j < intFilSel.length; j++) 
                                {
                                    if (intFilSel[j] > -1 && intRowEmpDestino == -1) // TENGA VALORES 
                                    {
                                        strTipRegLin = objTblModItems.getValueAt(intFilSel[j], INT_TBL_CON_LIN).toString();

                                        vecReg = new Vector();
                                        vecReg.add(INT_TBL_CON_LIN, strTipRegLin.equals("C") ? "E" : "N");
                                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ITM_MAE));
                                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_COD_ALT_ITM));
                                        vecReg.add(INT_TBL_DAT_NOM_ITM, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_NOM_ITM));
                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
                                        vecReg.add(INT_TBL_DAT_FACTOR, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_FACTOR));
                                        vecReg.add(INT_TBL_DAT_SELE, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_SELE));
                                        vecReg.add(INT_TBL_DAT_CMB_FACTOR, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_CMB_FACTOR));
                                        boolean blnBoo = false;
                                        for (int i = 0; i < objTblMod.getRowCount(); i++)
                                        {
                                            if (i == intRowEmpPtoDestino && !blnBoo) 
                                            {
                                                vecDat.add(vecReg);
                                                i--;
                                                blnBoo = true;
                                            }
                                            else 
                                            {
                                                vecDat.add(objTblMod.getData().get(i));
                                            }
                                        }
                                        objTblMod.setData(vecDat);
                                        tblDatGrupo.setModel(objTblMod);
                                        vecDat.clear();
                                        intRowEmpPtoDestino++;
                                    }
                                }

                                //ELIMINAR REGISTROS TBLDATEMP
                                boolean blnExi = false;

                                for (int i = 0; i < objTblModItems.getRowCount(); i++)
                                {
                                    for (int j = 0; j < intFilSel.length; j++) 
                                    {
                                        if (i == intFilSel[j]) 
                                        {
                                            blnExi = true;
                                        }
                                    }
                                    if (!blnExi) 
                                    {
                                        vecDatItems.add(objTblModItems.getData().get(i));
                                    }
                                    else
                                    {
                                        vecRegItems = new Vector();
                                        vecRegItems.add(INT_TBL_CON_LIN, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE));
                                        vecRegItems.add(INT_TBL_CON_COD_ITM_MAE, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ITM_MAE) );
                                        vecRegItems.add(INT_TBL_CON_COD_ALT_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_COD_ALT_ITM) );
                                        vecRegItems.add(INT_TBL_CON_NOM_ITM, objTblModItems.getValueAt(i, INT_TBL_CON_NOM_ITM) );
                                        vecRegItems.add(INT_TBL_CON_ORDEN, "M");
                                        vecRegItems.add(INT_TBL_CON_FACTOR, objTblModItems.getValueAt(i, INT_TBL_CON_FACTOR) );
                                        vecRegItems.add(INT_TBL_CON_SELE, objTblModItems.getValueAt(i, INT_TBL_CON_SELE) );
                                        vecRegItems.add(INT_TBL_CON_CMB_FACTOR, objTblModItems.getValueAt(i, INT_TBL_CON_CMB_FACTOR) );                                        
//                                        vecReg.add(INT_TBL_DAT_ORDEN, tblDatFor.getValueAt(intFilSel[j], INT_TBL_CON_ORDEN));
                                        
                                        
                                        
                                        vecDatItems.add(vecRegItems);                                                 
                                    }
                                    blnExi = false;

                                }
                                objTblModItems.setData(vecDatItems);
                                tblDatFor.setModel(objTblModItems);
                                vecDatItems.clear();                                
                            }
                            else
                                mostrarMsgInf("No se puede realizar esta acción ya que está un item a mover , ya se encuentra ingresado en la tabla.");                            

                        }
                    //</editor-fold>
                    }
////                    else if (intRowEmpDestino > -1 && intRowEmpPtoDestino > -1 && intRowForOrigen > -1) 
////                        
////                    {
////                        mostrarMsgInf("No se puede realizar esta acción ya que está seleccionando la otra tabla.");
////////                        limpiarJtableSelection();
////                    }
////                    else if ( intRowEmpPtoDestino <=0 ) 
////                    {
////                        mostrarMsgInf("No se pueden ubicar cuentas al inicio del plan de cuentas.");
////////                        limpiarJtableSelection();
////                    }
                    else  //MOVER CUENTAS DENTRO DEL MISMO JTABLE
                    {
                        boolean blnMov=true;
                        for (int i = 0; i < intFilMovEmp.length; i++) 
                        {
                            if (intFilMovEmp[i] <= 0)
                            {
                                blnMov=false;
                            }
                        }
                        if(blnMov)
                        {
                            //System.out.println("MOVER-EMP!!");
                            //System.out.println("intFilMovEmp: " + intFilMovEmp + " intRowEmp: " + intRowEmpPtoDestino);
                            //objTblModEmp.moveRow(intRowEmpDestino, intRowEmpPtoDestino);
                            objTblMod.moveRows(intFilMovEmp, intRowEmpPtoDestino);
                        }
                        else
                        {
                            mostrarMsgInf("No se puede mover el item de arriba hacia abajo con respecto a la primera ubicacion, intente de abajo hacia arriba.");
////                            limpiarJtableSelection();
                        }
                    }    
                }        
            });          
            
            
            //</editor-fold>


///////////////////////////
            
// //Configurar JTable: Renderizar celdas.
            objTblCelRenLblItems=new ZafTblCelRenLbl();
            objTblCelRenLblItems.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_COD_ALT_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_NOM_ITM).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_ORDEN).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_FACTOR).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_SELE).setCellRenderer(objTblCelRenLblItems);
            tcmAux.getColumn(INT_TBL_CON_CMB_FACTOR).setCellRenderer(objTblCelRenLblItems);
            objTblCelRenLblItems.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
                int intFil = -1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblItems.getRowRender();
                    colFonColCre=new java.awt.Color(0,240,0);
                    if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("M")){
                        objTblCelRenLblItems.setBackground(Color.ORANGE);
                    }
                    else{
                        if (objUti.parseString(objTblModItems.getValueAt(intFil, INT_TBL_CON_ORDEN)).equals("I")){
                            objTblCelRenLblItems.setBackground(Color.RED);
                        }
                        else
                            objTblCelRenLblItems.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        
////                         if (objUti.parseString(objTblModItems.getValueAt(intFil,INT_TBL_CON_ORDEN)).equals("M")){
////////                            objTblCelRenLbl.setBackground(colFonColCre);
////                        }
////                        else {
////////                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                        }
                    }
                }
            });
            
////                   
                        
          
            

            
            
            objTblModItems.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFor);

////            objTblCelRenLbl=new ZafTblCelRenLbl();
////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
																					   
            tcmAux.getColumn(INT_TBL_CON_COD_ITM_MAE).setCellRenderer(objTblCelRenLblItems);
            objTblCelRenLblItems.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
								
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
			 
                }
            });



            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDatFor);
            //Configurar JTable: Establecer que el JTable sea editable.
////            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);

            objTblModItems.setModoOperacion(objTblModItems.INT_TBL_INS);
            objDocLis=new ZafDocLis();
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblModItems.addTableModelListener(objTblModLis);  
            //Configurar JTable: Establecer tipo de selección.
            tblDatFor.setRowSelectionAllowed(true);
            tblDatFor.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
			
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
    
private int obtenerultimoregistro(String codigo){
        int ultimo = 0;
         int indice =0,contador=0;
         String codigoI="";
        
////        arlDatORDENAR= new ArrayList();
        for (int i=0; i<objTblMod.getRowCountTrue();i++)
        {
            codigoI =objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM).toString();
            
            if  (codigoI !="Null")
            {
                
                indice =indice+1;
            }
            

        }

        return indice;
    }
    
private boolean existeMismoItem(int intFilSel){
        boolean blnRes = false;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            if (tblDatGrupo.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE) != null){
                if (i != intFilSel){
                    if (tblDatGrupo.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString().equals(tblDatGrupo.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM_MAE).toString())){
                        blnRes = true;
                        mostrarMsgInf("<HTML>El item ya fue ingresado en la fila " + (i+1) + ".</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDatGrupo.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT_ITM);
                        break;
                    }
                }
            }
        }
        return blnRes;
    }
           
private boolean isExisteItemRepetido(String item)//chiqui
{
    boolean blnRes=false;
    try{
        String itemCompa="";
        int contador=0;
        for(int i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            itemCompa =objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE).toString();
            if (itemCompa.equals(item))
                contador++;
                
        }
        if (contador>1)
            blnRes=true;
    }
    catch (Exception e){
        blnRes=true;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}
        
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

    public void mouseMoved(java.awt.event.MouseEvent evt) {
        int intCol = tblDatGrupo.columnAtPoint(evt.getPoint());
        String strMsg = "";
        switch (intCol) {
            case INT_TBL_DAT_COD_ITM_MAE:
                strMsg = "Código del item maestro";
                break;
            case INT_TBL_DAT_COD_ALT_ITM:
                strMsg = "Código 3L";
                break;
            case INT_TBL_DAT_NOM_ITM:
                strMsg = "Descripción del item ";
                break;
            case INT_TBL_DAT_ORDEN:
                strMsg = "Ordenamiento";
                break;   
            case INT_TBL_DAT_FACTOR:
                strMsg = "Factor";
                break;    
            case INT_TBL_DAT_SELE:
                strMsg = "Seleccion";
                break;   
            case INT_TBL_DAT_CMB_FACTOR:
                strMsg = "Cmb Factor";
                break;                 
            default:
                strMsg = "";
                break;
        }
        tblDatGrupo.getTableHeader().setToolTipText(strMsg);
    }
   }
     
     private class ZafMouMotAdaItems extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDatGrupo.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_CON_COD_ITM_MAE:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_CON_COD_ALT_ITM:
                    strMsg = "Código 3L";
                    break;
                case INT_TBL_CON_NOM_ITM:
                    strMsg = "Descripción del item ";
                    break;
                case INT_TBL_CON_ORDEN:
                    strMsg = "Ordenamiento ";
                    break;   
                case INT_TBL_CON_FACTOR:
                    strMsg = "Factor ";
                    break;    
                case INT_TBL_CON_SELE:
                    strMsg = "Seleccion ";
                    break;           
                case INT_TBL_CON_CMB_FACTOR:
                    strMsg = "CMBFACTOR ";
                    break;                     
                default:
                    strMsg = "";
                    break;
            }
            tblDatGrupo.getTableHeader().setToolTipText(strMsg);
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
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butElimi = new javax.swing.JButton();
        butLimp = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butCrearFactor = new javax.swing.JButton();
        butModificar = new javax.swing.JButton();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panFiltros = new javax.swing.JPanel();
        butCons = new javax.swing.JButton();
        lblMsgInf = new javax.swing.JLabel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        cmbTipoAgru = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panTablas = new javax.swing.JPanel();
        spnDatFor = new javax.swing.JScrollPane();
        tblDatFor = new javax.swing.JTable();
        spnDatEmp = new javax.swing.JScrollPane();
        tblDatGrupo = new javax.swing.JTable();
        spnFactor = new javax.swing.JScrollPane();
        panFil1 = new javax.swing.JPanel();
        tabFrm1 = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panFil2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
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
        butProceso = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis1 = new javax.swing.JProgressBar();

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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butElimi.setText("Eliminar");
        butElimi.setToolTipText("Eliminar el grupo seleccionado.");
        butElimi.setEnabled(false);
        butElimi.setPreferredSize(new java.awt.Dimension(92, 25));
        butElimi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butElimiActionPerformed(evt);
            }
        });
        panBot.add(butElimi);

        butLimp.setText("Limpiar");
        butLimp.setToolTipText("Limpiar la ventana");
        butLimp.setEnabled(false);
        butLimp.setPreferredSize(new java.awt.Dimension(92, 25));
        butLimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimpActionPerformed(evt);
            }
        });
        panBot.add(butLimp);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Salir");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("Guardar los datos.");
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        panBot.add(butGuardar);

        butCrearFactor.setText("Crear factor");
        butCrearFactor.setToolTipText("Crear factor");
        butCrearFactor.setPreferredSize(new java.awt.Dimension(92, 25));
        butCrearFactor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCrearFactorActionPerformed(evt);
            }
        });
        panBot.add(butCrearFactor);

        butModificar.setText("Modificar");
        butModificar.setToolTipText("Modifca los factores");
        butModificar.setPreferredSize(new java.awt.Dimension(92, 25));
        butModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModificarActionPerformed(evt);
            }
        });
        panBot.add(butModificar);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        tabFrm.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

        spnFil.setName(""); // NOI18N
        spnFil.setPreferredSize(new java.awt.Dimension(950, 565));

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(1250, 565));
        panFil.setLayout(new java.awt.BorderLayout());

        panFiltros.setPreferredSize(new java.awt.Dimension(100, 120));
        panFiltros.setLayout(null);

        butCons.setLabel("Consultar");
        butCons.setPreferredSize(new java.awt.Dimension(92, 25));
        butCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConsActionPerformed(evt);
            }
        });
        panFiltros.add(butCons);
        butCons.setBounds(530, 98, 90, 20);

        lblMsgInf.setForeground(new java.awt.Color(0, 0, 255));
        lblMsgInf.setText("Trasladar las cuentas de detalle:   Plan de Cuentas: Empresa   <<<  A  >>>  Plan de Cuentas: *** Formato***");
        panFiltros.add(lblMsgInf);
        lblMsgInf.setBounds(10, 520, 740, 14);

        bgrFil.add(optTod);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFiltros.add(optTod);
        optTod.setBounds(10, 50, 270, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFiltros.add(optFil);
        optFil.setBounds(10, 70, 410, 20);

        cmbTipoAgru.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ninguna" }));
        cmbTipoAgru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoAgruActionPerformed(evt);
            }
        });
        panFiltros.add(cmbTipoAgru);
        cmbTipoAgru.setBounds(190, 10, 170, 20);

        jLabel1.setText("Tipo de Agrupación:");
        panFiltros.add(jLabel1);
        jLabel1.setBounds(10, 10, 160, 14);

        panFil.add(panFiltros, java.awt.BorderLayout.NORTH);

        panTablas.setLayout(new javax.swing.BoxLayout(panTablas, javax.swing.BoxLayout.LINE_AXIS));

        tblDatFor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatFor.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDatFor.setRowHeight(20);
        spnDatFor.setViewportView(tblDatFor);

        panTablas.add(spnDatFor);

        tblDatGrupo.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDatGrupo.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDatGrupo.setRowHeight(20);
        spnDatEmp.setViewportView(tblDatGrupo);

        panTablas.add(spnDatEmp);

        panFil.add(panTablas, java.awt.BorderLayout.CENTER);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Creación/Modificación", spnFil);

        spnFactor.setName(""); // NOI18N
        spnFactor.setPreferredSize(new java.awt.Dimension(950, 565));

        panFil1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil1.setPreferredSize(new java.awt.Dimension(1250, 565));
        panFil1.setLayout(new java.awt.BorderLayout());

        tabFrm1.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panFil2.setName(""); // NOI18N
        panFil2.setPreferredSize(new java.awt.Dimension(610, 180));
        panFil2.setLayout(null);

        jLabel2.setText("Id:");
        panFil2.add(jLabel2);
        jLabel2.setBounds(10, 10, 60, 14);

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
        panFil2.add(txtCodFactor);
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
        panFil2.add(txtNomLargo);
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
        panFil2.add(txtNomCorto);
        txtNomCorto.setBounds(150, 50, 400, 20);

        lblDescCorta.setText("Descripción Corta:");
        lblDescCorta.setToolTipText("");
        panFil2.add(lblDescCorta);
        lblDescCorta.setBounds(10, 50, 120, 20);

        lblDescLarga.setText("Descripción Larga:");
        panFil2.add(lblDescLarga);
        lblDescLarga.setBounds(10, 30, 120, 20);

        lblFactor1.setText("Factor:");
        panFil2.add(lblFactor1);
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
        panFil2.add(txtFactor);
        txtFactor.setBounds(150, 70, 90, 20);

        lblFactor.setText("Factor Modiifcado:");
        panFil2.add(lblFactor);
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
        panFil2.add(txtFactorMod);
        txtFactorMod.setBounds(420, 70, 90, 20);

        txtObser1.setColumns(20);
        txtObser1.setRows(5);
        jScrollPane1.setViewportView(txtObser1);

        panFil2.add(jScrollPane1);
        jScrollPane1.setBounds(150, 90, 400, 40);

        lblObserva1.setText("Observacion:");
        panFil2.add(lblObserva1);
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

        panFil2.add(jScrollPane2);
        jScrollPane2.setBounds(10, 140, 540, 450);

        panDat.add(panFil2, java.awt.BorderLayout.CENTER);

        tabFrm1.addTab("Factor", panDat);

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
            .addGroup(panDetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panDetLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1337, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(342, 342, 342))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDetLayout.createSequentialGroup()
                        .addComponent(butProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(358, 358, 358))))
        );
        panDetLayout.setVerticalGroup(
            panDetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDetLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm1.addTab("Reporte", panRpt);

        panFil1.add(tabFrm1, java.awt.BorderLayout.CENTER);

        spnFactor.setViewportView(panFil1);

        tabFrm.addTab("Factor", spnFactor);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis1.setText("Listo");
        lblMsgSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis1, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis1.setBorderPainted(false);
        pgrSis1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis1.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis1, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        getContentPane().add(panBarEst, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 1365, 754);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
////        configurarFrm();
////            if(!isVersionActualizada()){
////                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                dispose();
////            }        
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
                    System.out.println("Version " + strVersion);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(strVersion.equals(rstLoc.getString("tx_ver"))){
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
 

    private boolean LLenarComboTiposAgrupacion()//chiqui
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
                strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar FROM tbm_tipAgr where st_reg ='A'";
                strSQL+=" UNION";
                strSQL+="  SELECT 0 ,'',''";                
                rstLoc = stmLoc.executeQuery(strSQL);
////                cmbTipoAgru.removeAllItems();
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("tx_nom");
                    dataList.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList.toArray(new String[0]));
                    cmbTipoAgru.setModel(model);    
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
    
    private boolean ConsultaComboTiposAgrupacion(int tipo)//chiqui
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
                strSQL+=" SELECT co_tipAgr, trim(tx_nom) as nombreCor, trim(tx_desLar) as nombreLar FROM tbm_tipAgr WITH (NOLOCK)   where st_reg ='A'";
                strSQL+=" and co_tipAgr ="+tipo +"";                
                rstLoc = stmLoc.executeQuery(strSQL);
               
                while (rstLoc.next()){
                    String data = rstLoc.getString("nombreCor");
                    dataList1.add(data);
                    blnRes=true;
                }
                if (blnRes)
                {
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(dataList1.toArray(new String[0]));
                    cmbTipoAgru.setModel(model); 
                    cmbTipoAgru.setEnabled(false);
                    tipoAgrupa =tipoGlobal;
                    blnRes=true;
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

    private void butConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConsActionPerformed
        // TODO add your handling code here:
        if (butCons.getText().equals("Consultar"))
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

    }//GEN-LAST:event_butConsActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        ////        if (optTod.isSelected())
        ////        {
            ////            objVenConItm01.limpiar();
            ////            chkSolStk.setSelected(false);
            ////        }
    }//GEN-LAST:event_optTodItemStateChanged
    private boolean validaAgrupacion()
    {
        boolean blnRes=true;
        int intNumFil=objTblMod.getRowCountTrue();
        String str_factor ="", str_item="";
        for (int i=0; i<intNumFil;i++)
        {        
            if (i!=0)
            {
                str_factor=objTblMod.getValueAt(i,INT_TBL_DAT_CMB_FACTOR).toString();
                str_item=objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM_MAE).toString();
                if (str_factor.equals(""))
                {
                    blnRes=false;
                    mensajeError="El item '"+str_item +"' no posee un factor asignado";
                    return blnRes;
                    
                }
                //String[] partes = objTblMod.getValueAt(i,INT_TBL_DAT_CMB_FACTOR).toString().split("-");                
            }
        }
        return blnRes ;
    }
    private boolean guardarGrupo()
    {
        boolean blnRes=false;
        int indicador=-1;
        String error ="";
        int codMaxi=0;
        java.sql.Connection conLoc;
        java.sql.Connection connZAF;     
        
        
        //datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
        strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());          
        try
        {
            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conLoc.setAutoCommit(false);
            connZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());      
            connZAF.setAutoCommit(false);             
            mensajeError="";
            
             if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
             {
                // validaAgrupacion();

//                if (txtIdGrupo.getText().equals("") || txtDescGru.getText().equals("") )
//                      mensajeError="<HTML>Se debe ingresar los campos obligatorios</HTML>";
////                if (isExisteItemRepetido())
////                    mensajeError ="<HTML>Se encuentra mas de un item repetido</HTML>"; 
                if (mensajeError.equals(""))
                {
                    int intNumFil=objTblMod.getRowCountTrue();
                    
                    if (intNumFil>1)
                    {
                        if (estado =='C')
                        {
                            if(obtener_maximo())
                            {
                                if(insertar_tbmCabGrupo(conLoc,0))
                                {
                                    if(insertar_tbmDetGrupo(conLoc,0,connZAF))
                                    {
                                        indicador=1;
                                        blnRes=true;
                                    }

                                }                            
                            }
                            else
                               mostrarMsgInf("No se pudo obtener el grupo siguiente");                        
                        }
                        else
                        {
                            if (estado =='M')
                            {
                                if(modificar_tbmCabGrupo(conLoc,codigoGrupoGlobal))
                                {
                                    if (modificar_tbmDetGrupo_INV(conLoc,codigoGrupoGlobal,connZAF))
                                    {
                                        if(borrar_tbmDetGrupo(conLoc,codigoGrupoGlobal))
                                        {
                                            if(insertar_tbmDetGrupo(conLoc,1,connZAF))
                                            {
                                                indicador=2;
                                                blnRes=true;
                                            }
                                        }                                        
                                    }   


                                }
                            }
                        }                        
                    }
                    else
                    {
                        
                        borrarAgrupa(conLoc,codigoGrupoGlobal,connZAF);
                        //anular_tbmCabDetGrupo(conLoc,codigoGrupoGlobal);  
                        blnRes=true;
                        
                        blnAnula=true;
                    }

                        
                    

                        
                    if(blnRes==true)
                    {
                        if (estado =='M')
                            intCodCfg=codigoGrupoGlobal;
////                        if(InsertarHistoricoCabDet(conLoc,intCodCfg,tipoAgrupa))
////                        {
////////                            if(InsertarHistoricoDet(conLoc,intCodCfg))
////////                            {
////                               conLoc.commit();
////                               mostrarMsgInf("Se realizo la insercion del registro, de forma correcta");
////                               limpiarFrm();
////////                            }
////////                            else
////////                           {
////////                                conLoc.rollback();
////////                                mostrarMsgInf("No se puede realizar la insercion del historico:detalle");                
////////                            }                                  
////                        }
////                        if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_cabGrpInv", "tbh_cabGrpInv", "WHERE a1.fe_ing='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_itmpad")) 
////                        {
////                            if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_detGrpInv", "tbh_detGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_coditm"))                                
////                            {
////                                connZAF.commit();
////                                conLoc.commit();
////                                 blnRes=true;
////                                 mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
////                            }
////                            else
////                            {
////                                blnRes=false;
////                                connZAF.rollback();
////                                conLoc.rollback();
////                            }
////                        }                        
////                        else
////                        {
////                            conLoc.rollback();
////                            mostrarMsgInf("No se puede realizar la insercion del historico:cabecera");                
////                        }        
////                        if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_cabGrpInv", "tbh_cabGrpInv", "WHERE a1.fe_ing='" + strFecUltMod + "' AND a1.co_usring=" + objParSis.getCodigoUsuario()+"","tx_itmpad")) 
////                        { 
////                            if(objHisTblBasDatGP.insertarHistoricoMasivoGeneral(conLoc, "tbm_detGrpInv", "tbh_detGrpInv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario()+"","tx_coditm"))                                
////                            {
                               if(objHisTblBasDat.insertarHistoricoMasivo(connZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) 
                                { 
                                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                                    { 
////////                                        if (objUtiGP.ActualizarPrecioGrupo(conLoc,connZAF,intCodCfg,strFecUltMod))
////////                                        {                                
                                             connZAF.commit();
                                             conLoc.commit();
                                             blnRes=true;
                                             mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
////////                                        }    
////////
////////                                        else
////////                                        {
////////                                            blnRes=false;
////////                                            mostrarMsgInf("<HTML>El item de cabecera se encuentra con precio 0 </HTML>");
////////////                                            mensajeError ="<HTML>ERROR al momento de realizar ele proceso de actualizacion de precios.</HTML>";
////////                                        }
                                         
                                        // limpiarFrm();
                                    }
                                    else
                                         blnRes=false;
                                }
                               else
                                    blnRes=false;
////                            }
////                            else
////                                blnRes=false;
                                
                            if (!blnRes)
                            {
                                connZAF.rollback();
                                conLoc.rollback();
                                mostrarMsgInf("<HTML>No se pudo realizar la operacion de forma exitosa </HTML>");
                            }
////                        }
////                        else
////                        {
////                            connZAF.rollback();
////                            conLoc.rollback();
////                            mostrarMsgInf("No se puede realizar la insercion del historico:cabecera");
////                        }

                       if ((estado =='M') || (estado =='C'))
                       {
////                            Maestros.ZafMae83.ZafMae83 obj2 = new Maestros.ZafMae83.ZafMae83( objParSis , "sdsds");             
////////                            obj2.mostrarGrupos(3);
////                                obj2.mostrarGlobal();
                           validaOk =true;
                            this.dispose();                                
                       }

                    }
                    else
                    {
                        conLoc.rollback();
                        connZAF.rollback();
                        mostrarMsgInf(mensajeError);                
                    }          
                }
                else
                    mostrarMsgInf(mensajeError);
             }
             else
                  mostrarMsgInf("Se cancelo la operacion de GUARDAR de forma correcta");
            
                 conLoc.close();
                 conLoc=null;
                 connZAF.close();
                 connZAF=null;                 
            
////            
////            if (isExisteItemRepetido())
////            {
////               mostrarMsgInf(mensajeError);
////            }
////            else
////            {
////                
////                    
////            }
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }
        
////        if (estado=='0')
////            mostrarMsgInf("<HTML>No se puede realizar el guardado, debe llenar todos los campos.</HTML>");
////        else
////        {
////           if (insertar_tbmCabFlu ())//chiqui
////                mostrarMsgInf("<HTML>Proceso Actualizado Correctamente.</HTML>");
////           else
////                mostrarMsgInf("<HTML>Proceso No Actualizado.</HTML>"); 
////        }
                
        // TODO add your handling code here:
                
        
        
        return blnRes;
        
    }
    
private boolean modificar_tbmDetGrupo_INV(java.sql.Connection conExt,int codIngresoGrp,java.sql.Connection connZAF)//21012025
    {
         java.sql.Statement stmLocZAF1;
         java.sql.Statement stmLocGP1;
        java.sql.ResultSet rstLocGP1;    

        String strGP="",strZAF1="",strGP1="";
        boolean validaIngreso=false,blnRes=false;
        double preVtaZa=0, preResul=0;
        int i=0,indicador =0;
        String codigoGP="";
        try
        {

                stmLocZAF1=connZAF.createStatement();
                stmLocGP1=conExt.createStatement();

                    strZAF1="";
                    
                    strGP="";
                    strGP+=" SELECT  trim(tx_codItm) as  item \n";
                    strGP+="  FROM    tbm_detGrpInv \n";
                    strGP+=" where  co_grpinv ="+ codIngresoGrp +"  \n";  

                    rstLocGP1 = stmLocGP1.executeQuery(strGP);

                      while(rstLocGP1.next())
                      {
                        indicador =0;
                        codigoGP = rstLocGP1.getString("item");

                            strGP1+=" \n UPDATE Dynamics2021..tbm_inv";
                            strGP1+=" \n  SET co_grpinv= 0";
                            strGP1+=" \n  , co_detgrpinv= 0";
                            strGP1+=" \n  , tx_usrmod= '" + objParSis.getNombreUsuario() +"'";
                            strGP1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";
                            strGP1+=" \n  WHERE tx_itm ='"+ codigoGP +"'";
                            strGP1+=" ;\n ";                                
                      

                            strZAF1+=" \n UPDATE tbm_inv";
                            strZAF1+=" \n  SET co_grpinv= 0";
                            strZAF1+=" \n  , co_detgrpinv= 0";
                            strZAF1+=" \n  , co_usrmod= " + objParSis.getCodigoUsuario() +"";
                            strZAF1+=" \n  , fe_ultmod ='"+ strFecUltMod +"'";     
                            strZAF1+=" \n  WHERE tx_codaltdyn ='"+ codigoGP +"'";
                            strZAF1+=" ;\n ";
                            

                      }
                                          

                      if (!strGP1.equals(""))
                       {
                           stmLocGP1.executeUpdate(strGP1);
                           stmLocZAF1.executeUpdate(strZAF1);
                           blnRes=true;
                       }   


                      stmLocGP1.close();
                      stmLocGP1=null;
                      stmLocZAF1.close();
                      stmLocZAF1=null;                      
                      rstLocGP1.close();
                      rstLocGP1=null;                    
             

                  
            
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR modificar_tbmDetGrupo_INV "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR modificar_tbmDetGrupo_INV "+ e );
           blnRes=false;
         }
        return blnRes;
    }


    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed
        java.sql.Connection conLoc;
        java.sql.Connection connZAF;  
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
            conLoc.setAutoCommit(false);
            connZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());      
            connZAF.setAutoCommit(false);
            
           if (guardarGrupo())
           {
               if (!blnAnula)
               {
                   if (objUtiGP.ActualizarPrecioGrupo(conLoc,connZAF,intCodCfg,strFecUltMod,1))
                   {
                       if(objHisTblBasDat.insertarHistoricoMasivo(connZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) 
                        { 
                            if(objHisTblBasDatGP.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                            { 
                                 connZAF.commit();
                                 conLoc.commit();
                                 mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
                                 tabFrm.setSelectedIndex(0);
                            }
                            else
                            {
                                conLoc.rollback();
                                connZAF.rollback();
                                mostrarMsgInf("<HTML>No se pudo realizar la operacion de forma exitosa </HTML>");     
                                tabFrm.setSelectedIndex(0);                                        
                            }

                        }
                       else
                       {
                            conLoc.rollback();
                            connZAF.rollback();
                            mostrarMsgInf("<HTML>No se pudo realizar la operacion de forma exitosa </HTML>");     
                            tabFrm.setSelectedIndex(0);                                   
                       }
                   }
                   else
                   {
                        conLoc.rollback();
                        connZAF.rollback();
                        mostrarMsgInf("<HTML>No se pudo realizar la operacion de forma exitosa </HTML>");     
                        tabFrm.setSelectedIndex(0);
                   }                   
               }
               else
               {
                     connZAF.commit();
                     conLoc.commit();
                     mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
                     tabFrm.setSelectedIndex(0);                   
               }
     
           }
           else
               mostrarMsgInf("<HTML>No se pudo realizar la operacion de forma exitosa </HTML>"); 
        }  
        catch (java.sql.SQLException e) {  
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }        

           
           
    }//GEN-LAST:event_butGuardarActionPerformed
    public boolean acepta(){
      return validaOk;
    }
    private boolean InsertarHistoricoCabDet (java.sql.Connection conEXT,int codigoGrupo,int codigoClasifi)
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
                    
                    strSQL+=" INSERT INTO tbH_cabGrpInv\n" +
"				(co_grpInv, co_his, co_tipAgr, tx_nomGrpInv, tx_deslar, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, \n" +
"				 co_usrmod,tx_itmPad)  \n";
                    strSQL+=" SELECT a1.co_grpInv as codigoGrupo,isnull(a2.maximo,0)+1  maximo, a1.co_tipAgr as codigoClasi,trim(a1.tx_nomGrpInv) as nombreCo,"
                            + "a1.tx_deslar as nombreLa,a1.tx_obs1 as obs1 , a1.tx_obs2 as obs2 , a1.st_reg as estado, a1.fe_ing as fecingreso ,   \n"
                            + "a1.fe_ultmod as femodifica, a1.co_usring as usuarioCrea, a1.co_usrmod as usuarioModi,a1.tx_itmPad as item   \n";
                    strSQL+=" FROM tbm_cabGrpInv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+="   select isnull(max(CO_HIS),0) as maximo,co_grpInv from tbh_cabGrpInv as a2     \n";
                    strSQL+="   group by co_grpInv\n";
                    strSQL+="   ) as a2 on (a1.co_grpInv =a2.co_grpInv) \n";
                    strSQL+=" WHERE a1.co_grpInv = "+  codigoGrupo  +" and a1.co_tipAgr ="+ codigoClasifi +";\n";
                    
                    
                    strSQL+=" INSERT INTO tbh_detGrpInv\n" +
"				(co_grpInv, co_his, co_reg, tx_codItm, co_facModPre,st_mosItmLisPre) \n" ;
                    strSQL+=" SELECT a1.co_grpInv as codigoGrupo,isnull(a2.maximo,0)+1  maximo, a1.co_reg as registro,trim(a1.tx_codItm) as item,"
                            + " a1.co_facModPre as porcentaje,a1.st_mosItmLisPre   \n";
                    strSQL+=" FROM tbm_detGrpInv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+="   select isnull(max(CO_HIS),0) as maximo,co_grpInv from tbh_detGrpInv as a2     \n";
                    strSQL+="   group by co_grpInv\n";
                    strSQL+="   ) as a2 on (a1.co_grpInv =a2.co_grpInv) \n";
                    strSQL+=" WHERE a1.co_grpInv = "+  codigoGrupo  +"\n";                    
                
                    System.out.println("INSERT " + strSQL);                    
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
    private boolean InsertarHistoricoDet (java.sql.Connection conEXT,int codigoGrupo)
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
                    
                    strSQL+=" INSERT INTO tbh_detGrpInv\n" +
"				(co_grpInv, co_his, co_reg, tx_codItm, nd_porDif) \n" ;
                    strSQL+=" SELECT a1.co_grpInv as codigoGrupo,isnull(a2.maximo,0)+1  maximo, a1.co_reg as registro,trim(a1.tx_codItm) as item,"
                            + " a1.nd_porDif as porcentaje   \n";
                    strSQL+=" FROM tbm_detGrpInv AS a1  \n";
                    strSQL+=" left join (  \n";
                    strSQL+="   select isnull(max(CO_HIS),0) as maximo,co_grpInv from tbh_detGrpInv as a2     \n";
                    strSQL+="   group by co_grpInv\n";
                    strSQL+="   ) as a2 on (a1.co_grpInv =a2.co_grpInv) \n";
                    strSQL+=" WHERE a1.co_grpInv = "+  codigoGrupo  +"\n";
                
                    System.out.println("INSERT " + strSQL);                    
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

    
    private void butElimiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butElimiActionPerformed
////        java.sql.Statement stmLoc1;
////        java.sql.ResultSet rstLoc1;
////        java.sql.Connection conExt1;
////        try
////        {
////            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());  
////            conExt1.setAutoCommit(false);
////                     if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
////                     {
////                         
////                         if(borrar_tbmCabGrupo(conExt1,0))
////                         {
////                             conExt1.commit();
////                             mostrarMsgInf("La operación ELIMINAR se realizó con éxito.");
////                         }
////                         else
////                         {
////                             conExt1.rollback();
////                            mostrarMsgInf("La operación CREAR se NO realizó con éxito."); 
////                         }
////                          
////                     } 
////                     else
////                         mostrarMsgInf("La operación ELIMINAR se cancelo con éxito.");
////        }
////         catch (java.sql.SQLException e)
////        {
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            objUti.mostrarMsgErr_F1(this, e);
////        }
    }//GEN-LAST:event_butElimiActionPerformed

    private void butLimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimpActionPerformed

////      LLenarTabla();
          limpiarFrm();
    }//GEN-LAST:event_butLimpActionPerformed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:

////        java.sql.Statement stmLoc1;
////        java.sql.ResultSet rstLoc1;
////        java.sql.Connection conExt1;
////        try
////        {
////            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            conExt1.setAutoCommit(false);
////            if (!consultar_DetGrupo(conExt1))
////            {
////                if(borrar_tbmCabGrupo(conExt1,1))
////                {
////                    conExt1.commit();
////                    mostrarMsgInf("Se procedio a ELIMINAR el grupo creado con  éxito.");
////                }
////                else
////                {
////                    conExt1.rollback();
////                    mostrarMsgInf("La operación ELIMINAR se NO realizó con éxito.");
////                }
////            }
////        }
////        catch (java.sql.SQLException e)
////        {
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e)
////        {
////            objUti.mostrarMsgErr_F1(this, e);
////        }

        if (estado =='M')
           dispose();
        else
            dispose();

    }//GEN-LAST:event_butCanActionPerformed

    private void cmbTipoAgruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoAgruActionPerformed
        tipoAgrupa = obtenerIdTipoAgrup(cmbTipoAgru.getSelectedItem().toString());
        if (tipoAgrupa!=-1)
        {
            butCons.setEnabled(true);
            butGuardar.setEnabled(true);
            butCons.doClick();
////            if (!cargarDetReg())
////            {
////                //Inicializar objetos si no se pudo cargar los datos.
////                lblMsgSis1.setText("Listo");
////                pgrSis1.setValue(0);
////                butCons.setText("Consultar");
////            }
////            //Establecer el foco en el JTable sólo cuando haya datos.
////////            objThrGUI =null;
////            if (tblDatFor.getRowCount()>0)
////            {
////                tabFrm.setSelectedIndex(1);
////                tblDatFor.setRowSelectionInterval(0, 0);
////                tblDatFor.requestFocus();
////            }            
        }
        else
        {
            butCons.setEnabled(false);
            butGuardar.setEnabled(false);
            
            
        }
            
              
    }//GEN-LAST:event_cmbTipoAgruActionPerformed

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

    private void txtNomLargoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLargoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoFocusGained

    private void txtNomLargoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLargoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoFocusLost

    private void txtNomLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomLargoActionPerformed

    private void txtNomCortoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCortoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoFocusGained

    private void txtNomCortoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCortoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoFocusLost

    private void txtNomCortoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCortoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomCortoActionPerformed

    private void txtFactorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusGained

    private void txtFactorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorFocusLost

    private void txtFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorActionPerformed

    private void txtFactorModFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorModFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModFocusGained

    private void txtFactorModFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFactorModFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModFocusLost

    private void txtFactorModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFactorModActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFactorModActionPerformed

    private void butProcesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProcesoActionPerformed
        // TODO add your handling code here:
        if(!ejecutarProceso())
        mostrarMsgInf("Fallo en el proceso de actualización");
        else
        configurarFrm();

        objTblModPro.setRowCount(0);
        tabFrm.setSelectedIndex(0);
    }//GEN-LAST:event_butProcesoActionPerformed
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
    
    private boolean consultar_Factor(java.sql.Connection conExt)//factor
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
    
    private boolean insertar_Factor(java.sql.Connection conExt)//factor
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
    
    private boolean cargarFactores()//factor
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
                vecDatFact.clear();
                //Obtener los registros.
                
             
                while (rst.next()){
                    vecRegFact=new Vector();
                    vecRegFact.add(INT_TBL_DAT_LIN,"");
                    vecRegFact.add(INT_TBL_DAT_CODIGO,rst.getInt("codigo"));                     
                    vecRegFact.add(INT_TBL_DAT_DESC_CORT,rst.getString("desCor"));
                    vecRegFact.add(INT_TBL_DAT_DESC_LAR,rst.getString("desLar"));
                    vecRegFact.add(INT_TBL_DAT_FAC,rst.getDouble("factor"));                    
                    if (rst.getString("estado").equals("A"))
                        vecRegFact.add(INT_TBL_DAT_ESTADO,Boolean.TRUE);
                    else
                        vecRegFact.add(INT_TBL_DAT_ESTADO,Boolean.FALSE);
                    vecRegFact.add(INT_TBL_DAT_OB,rst.getString("obser1"));
                    vecDatFact.add(vecRegFact);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModFact.setData(vecDatFact);
                tblDatFact.setModel(objTblModFact);
                vecDatFact.clear();            }
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
    

    private boolean configurarTblDatFact()//factor
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFact=new Vector();    //Almacena los datos
            vecCabFact=new Vector(23);  //Almacena las cabeceras
            vecCabFact.clear();
            vecCabFact.add(INT_TBL_DAT_LIN,"");
            vecCabFact.add(INT_TBL_DAT_CODIGO,"Id");
            vecCabFact.add(INT_TBL_DAT_DESC_CORT,"Descripcion Corta");
            vecCabFact.add(INT_TBL_DAT_DESC_LAR,"Descripcion Larga");
            vecCabFact.add(INT_TBL_DAT_FAC,"Factor");            
            vecCabFact.add(INT_TBL_DAT_ESTADO,"Estado");
            vecCabFact.add(INT_TBL_DAT_OB,"Observacion");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFact=new ZafTblMod();
            objTblModFact.setHeader(vecCab);
            tblDatFact.setModel(objTblModFact);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModFact.setColumnDataType(INT_TBL_DAT_LIN, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModFact.setColumnDataType(INT_TBL_DAT_CODIGO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblModFact.setColumnDataType(INT_TBL_DAT_FAC, ZafTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer tipo de selección.
            tblDatFact.setCellSelectionEnabled(true);
            tblDatFact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFact=new ZafTblPopMnu(tblDatFact);
            objTblPopMnuFact.setPegarEnabled(true);
            objTblPopMnuFact.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatFact.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAuxFact=tblDatFact.getColumnModel();
            tcmAuxFact.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAuxFact.getColumn(INT_TBL_DAT_CODIGO).setPreferredWidth(30);
            tcmAuxFact.getColumn(INT_TBL_DAT_DESC_CORT).setPreferredWidth(100);            
            tcmAuxFact.getColumn(INT_TBL_DAT_DESC_LAR).setPreferredWidth(100);                        
            tcmAuxFact.getColumn(INT_TBL_DAT_FAC).setPreferredWidth(60);            
            tcmAuxFact.getColumn(INT_TBL_DAT_ESTADO).setPreferredWidth(50);
            tcmAuxFact.getColumn(INT_TBL_DAT_OB).setPreferredWidth(50);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFact = new ZafMouMotAda();
            tblDatFact.getTableHeader().addMouseMotionListener(objMouMotAdaFact);            
            
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
            objTblBusFact=new ZafTblBus(tblDatFact);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFact=new ZafTblFilCab(tblDatFact);
            tcmAuxFact.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabFact);
            //Configurar JTable: Establecer columnas editables.
////            vecAux=new Vector();
////            vecAux.add("" + INT_TBL_DAT_MODIFICAR);
////            objTblMod.setColumnasEditables(vecAux);
            vecAuxFact=null;
            objTblModFact.setModoOperacion(objTblModFact.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdFact=new ZafTblOrd(tblDatFact);

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
            objTblOrdFact=new ZafTblOrd(tblDatFact);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblModFact.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAuxFact=null;
            
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
    
    private void butCrearFactorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCrearFactorActionPerformed
        boolean blnRes=false,blnInser=false;
        String error ="";
        estadoFac=0;

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
    }//GEN-LAST:event_butCrearFactorActionPerformed

    private int obtener_items(double codFactor)//factor
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
    
    private int obtenerIdTipoAgrup(String tipo)
{
    java.sql.Statement stmLoc1 = null;
    java.sql.ResultSet rstLoc1 = null;
    java.sql.Connection conExt1;        
    int idTipo=-1;
    try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

        if (conExt1!=null){
            stmLoc1=conExt1.createStatement();
          strSQL="";
            strSQL+=" SELECT co_tipAgr, tx_nom, tx_desLar ";
            strSQL+=" FROM Dynamics2021..tbm_tipAgr ";
            strSQL+=" WHERE  st_reg ='A' and tx_nom ="+ objUti.codificar(tipo)  +"";                

            System.out.println("obtenerIdTipoAtri: " + strSQL);
            rstLoc1=stmLoc1.executeQuery(strSQL);
            if (rstLoc1.next())
                idTipo=rstLoc1.getInt("co_tipAgr");
        }
        rstLoc1.close();
        stmLoc1.close();
        conExt1.close();
        rstLoc1=null;
        stmLoc1=null;
        conExt1=null;
    }
    catch (java.sql.SQLException e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    catch (Exception e){
        idTipo=-1;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return idTipo;
}
    
    

    private boolean ValidaExisteItemTipo(String item)//chiqui
    {
        java.sql.Statement stmLoc1 = null;
        java.sql.ResultSet rstLoc1 = null;
        java.sql.Connection conExt1;        
        int intPosRel;
        boolean blnRes=false;
        try{
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conExt1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if (conExt1!=null){
                stmLoc1=conExt1.createStatement();
              strSQL="";
                strSQL+=" SELECT det.co_grpInv as co_grupo ";
                strSQL+=" FROM Dynamics2021..tbm_detGrpInv as det ";
                strSQL+="       inner join Dynamics2021..tbm_cabGrpInv as cab on (det.co_grpInv = cab.co_grpInv) ";                
                strSQL+=" WHERE  cab.st_reg='A' and det.tx_codItm ="+ objUti.codificar(item)  +"";                
                
                System.out.println("tbm_detGrpInv: " + strSQL);
                rstLoc1=stmLoc1.executeQuery(strSQL);
                if (rstLoc1.next())
                    blnRes=true;
            }
            rstLoc1.close();
            stmLoc1.close();
            conExt1.close();
            rstLoc1=null;
            stmLoc1=null;
            conExt1=null;
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
    
////    private boolean ValidaExistePeriodo()//chiqui
////    {
////        int intPosRel;
////        boolean blnRes=false;
////        try{
////////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////            
////            if (con!=null){
////                stm=con.createStatement();
////              strSQL="";
////                strSQL+=" SELECT RTRIM(ne_perAnuFlu) as periodo, RTRIM(tx_codLis) as listAfe, rtrim(tx_codLisApl) as listOrig,st_reg";
////                strSQL+=" FROM tbm_cfgFlu as flu ";
////                strSQL+=" WHERE  flu.st_reg ='A' and flu.ne_perAnuFlu ='"+ txtDescGru.getText() +"'";                
////                
////                System.out.println("cargarCabReg: " + strSQL);
////                rst=stm.executeQuery(strSQL);
////                if (rst.next())
////                    blnRes=true;
////            }
////            rst.close();
////            stm.close();
////            con.close();
////            rst=null;
////            stm=null;
////            con=null;
////        }
////        catch (java.sql.SQLException e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }    
    


  
    
    private boolean limpiarFrm()//chiqui_anterior
    {
        boolean blnRes=true;
        try{
////            txtDescGru.setText("");
////            txtIdGrupo.setText("");
            objTblMod.removeAllRows();
            objTblMod.insertRow();
            lblMsgSis1.setText("Limpiando");

            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
////                lblMsgSis.setText("Listo");
////                pgrSis.setValue(0);
                butCons.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
////            objThrGUI =null;
            if (tblDatFor.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDatFor.setRowSelectionInterval(0, 0);
                tblDatFor.requestFocus();
            }

        }
        catch (Exception e){
            blnRes=false;
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
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCons;
    private javax.swing.JButton butCrearFactor;
    private javax.swing.JButton butElimi;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butLimp;
    private javax.swing.JButton butModificar;
    private javax.swing.JButton butProceso;
    private javax.swing.JComboBox<String> cmbTipoAgru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblDescCorta;
    private javax.swing.JLabel lblDescLarga;
    private javax.swing.JLabel lblFactor;
    private javax.swing.JLabel lblFactor1;
    private javax.swing.JLabel lblMsgInf;
    private javax.swing.JLabel lblMsgSis1;
    private javax.swing.JLabel lblObserva1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFiltros;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTablas;
    private javax.swing.JProgressBar pgrSis1;
    private javax.swing.JScrollPane spnDatEmp;
    private javax.swing.JScrollPane spnDatFor;
    private javax.swing.JScrollPane spnFactor;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTabbedPane tabFrm1;
    private javax.swing.JTable tblDatFact;
    private javax.swing.JTable tblDatFor;
    private javax.swing.JTable tblDatGrupo;
    private javax.swing.JTable tblDatProceso;
    private javax.swing.JTextField txtCodFactor;
    private javax.swing.JTextField txtFactor;
    private javax.swing.JTextField txtFactorMod;
    private javax.swing.JTextField txtNomCorto;
    private javax.swing.JTextField txtNomLargo;
    private javax.swing.JTextArea txtObser1;
    // End of variables declaration//GEN-END:variables


    
 
  

     
    
  


    
     

 
    
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
       
 
    
     
 

    
}
