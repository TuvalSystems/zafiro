/*
 * ZafMae84.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Maestros.ZafMae84;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr; 
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
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
////import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVcoGP.ZafTblCelEdiButVcoGP;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
////import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import Librerias.ZafUtilGP.ZafUtilGP;

/**
 *INT_TBL_DAT_COD_ZAF
 * @author  José Marín 
 */
public class ZafMae84 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_ITM=1;
    private static final int INT_TBL_DAT_COD_3L=2;
    private static final int INT_TBL_DAT_COD_ZAF=3;
    private static final int INT_TBL_DAT_NOM_ITEM=4;
    private static final int INT_TBL_DAT_NOM_TAMA=5;
    private static final int INT_TBL_DAT_NOM_PESO=6;
    private static final int INT_TBL_DAT_COD_MARC=7;
    private static final int INT_TBL_DAT_NOM_MARC=8;
    private static final int INT_TBL_DAT_BUT_COM_MARC=9;     
    private static final int INT_TBL_DAT_COD_LINEA=10;
    private static final int INT_TBL_DAT_NOM_LINEA=11;    
    private static final int INT_TBL_DAT_BUT_COM_LINEA=12;
    private static final int INT_TBL_DAT_COD_CLASE1=13;
    private static final int INT_TBL_DAT_NOM_CLASE1=14;    
    private static final int INT_TBL_DAT_BUT_CLASE1=15;    
    private static final int INT_TBL_DAT_COD_MAT=16;
    private static final int INT_TBL_DAT_NOM_MAT=17;
    private static final int INT_TBL_DAT_BUT_COM_MAT=18;
    private static final int INT_TBL_DAT_COD_COMPR=19;
    private static final int INT_TBL_DAT_NOM_COMP=20;
    private static final int INT_TBL_DAT_BUT_COM_COMP=21;
    private static final int INT_TBL_DAT_COD_CATE=22;    
    private static final int INT_TBL_DAT_NOM_CATE=23;
    private static final int INT_TBL_DAT_BUT_COM_CATE=24;    
    
    private static final int INT_TBL_DAT_ABC_LIN=0;
    private static final int INT_TBL_DAT_ABC_COD_ITM=1;
    private static final int INT_TBL_DAT_ABC_COD_3L=2;
    private static final int INT_TBL_DAT_ABC_COD_ZAF=3;
    private static final int INT_TBL_DAT_ABC_NOM_ITEM=4;
    private static final int INT_TBL_DAT_ABC_COD_FRE=5;
    private static final int INT_TBL_DAT_ABC_DES_FRE=6;
    private static final int INT_TBL_DAT_ABC_BUT_FRE=7;
    private static final int INT_TBL_DAT_ABC_COD_PES=8;
    private static final int INT_TBL_DAT_ABC_DES_PES=9;
    private static final int INT_TBL_DAT_ABC_BUT_PES=10;
    private static final int INT_TBL_DAT_ABC_COD_VAL=11;
    private static final int INT_TBL_DAT_ABC_DES_VAL=12;
    private static final int INT_TBL_DAT_ABC_BUT_VAL=13;
    private static final int INT_TBL_DAT_ABC_COD_TUV=14;
    private static final int INT_TBL_DAT_ABC_DES_TUV=15;
    private static final int INT_TBL_DAT_ABC_BUT_TUV=16;    
    private static final int INT_TBL_DAT_ABC_COD_CLA=17;
    private static final int INT_TBL_DAT_ABC_DES_CLA=18;
    private static final int INT_TBL_DAT_ABC_BUT_CLA=19;

    
    
   
    
    private static final int INT_TBL_DAT_LIN2=0;
    private static final int INT_TBL_DAT_CHK_SELECCION=1;
    private static final int INT_TBL_DAT_NOMBRE=2;
    
    private static final int INT_TBL_DAT_ABC_LIN2=0;
    private static final int INT_TBL_DAT_ABC_CHK_SELECCION=1;
    private static final int INT_TBL_DAT_ABC_NOMBRE=2;    
    
    //Variables  
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Connection conZAF;    
    private Statement stmZAF;
    private ResultSet rstZAF;    
    
    private ZafParSis objParSis;
    private ZafUtil objUti;                                     //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafUtilGP objUtiGP;    
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuPri;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
////    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenCbo objTblCelRenCmbBox;
    private ZafTblCelEdiCbo objTblCelEdiCmbBox;
    private ZafTblCelRenBut objTblCelRenBut;                     //Render: Presentar JButton en JTable.
    private ZafTblModLis objTblModLis;                           //Detectar cambios de valores en las celdas.

    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafVenCon vcoMar;
    private ZafVenCon vcoCom;
    private ZafVenCon vcoCho;
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.util.Date datFecAuxZAF;                           //Auxiliar: Para almacenar fechas.    
    private ZafThreadGUI objThrGUI;

    private boolean blnHayCam;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo. 
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecTipVeh;
    
    private String strSQL, strAux;
    private String strCodMar, strNomMar;                       //Contenido del campo al obtener el foco.
    private String strCodCom, strNomCom;                       //Contenido del campo al obtener el foco.
    
    ZafVenConGP objVenConMarc; //*****************  
    ZafVenConGP objVenConMate; //*****************  
    ZafVenConGP objVenConLine; //***************** 
////    ZafVenConGP objVenConClas; //***************** 
    ZafVenConGP objVenConRefGen; //***************** 
    ZafVenConGP objVenConRefDet; //***************** 
    ZafVenConGP objVenConClase1; //*****************
    ZafVenConGP objVenConRefDet1; //*****************    
    ZafVenConGP objVenConCompr; //*****************  
    ZafVenConGP objVenConCate; //*****************      
    
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoMarca;            //Editor: JButton en celda 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoMate;            //Editor: JButton en celda 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoLine;    
////    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCla;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefGe;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefDet;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoClase1;
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoRefDet1;    
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCompra;        
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCate;        
    
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoMarca;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoMate;           //Editor: JTextField de consulta en celda
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoLine;           //Editor: JTextField de consulta en celda.
////    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoClas;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefGen;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefDet;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoClase1;           //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoRefDet1;           //Editor: JTextField de consulta en celda.    
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoCate;           //Editor: JTextField de consulta en celda. 
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoCompra;           //Editor: JTextField de consulta en celda. 
    
    
    
    private ZafVenConItmGP01 objVenConItm01GP;
    
    private ZafTblMod objTblModTipo;
    private ZafTblCelRenChk objTblCelRenChk;    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas. 
    private boolean blnMarTodChkTblEmpABC=true;                    //Marcar todas las casillas de verificación del JTable de empresas.     
    private ZafThGuarGUI objThrGuaGUI;
    private boolean blnMarca=false;
    private boolean blnMaterial=false;
    private boolean blnClase1=false;
    private boolean blnLinea=false;
    private boolean blnTamano=false;
    private boolean blnPeso=false; 
    private boolean blnCompra=false;     
    private boolean blnCategoria=false;      
    private String version="v1.3";
    private ZafHisTblBasDat objHisTblBasDat;  
    private ZafHisTblBasDatGP objHisTblBasDatGP;       
    StringBuilder cadenaItems = new StringBuilder();
    
    private ZafTblMod objTblModABC;    
    private Vector vecDatABC, vecRegABC, vecCabABC, vecAuxABC; 
    private ZafTblPopMnu objTblPopMnuABC, objTblPopMnuPriABC;  
    int valTip=0;
    
    ZafVenConGP objVenABCTuval; //***************** 
    ZafVenConGP objVenABCFre; //***************** 
    ZafVenConGP objVenABCValor; //*****************
    ZafVenConGP objVenABCClas; //*****************    
    ZafVenConGP objVenABCPeso; //*****************  
    
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoFre;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoFre;           //Editor: JTextField de consulta en celda.   
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoPes;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoPes;           //Editor: JTextField de consulta en celda.  
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoVal;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoVal;           //Editor: JTextField de consulta en celda.   
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoTuv;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoTuv;           //Editor: JTextField de consulta en celda. 
    private ZafTblCelEdiButVcoGP objTblCelEdiButVcoCla;            //Editor: JButton en celda     
    private ZafTblCelEdiTxtVcoGP objTblCelEdiTxtVcoCla;           //Editor: JTextField de consulta en celda.     
    
    
    
    private class ZafThGuarGUI extends Thread
    {
 
        public void run()
        {
        if (objTblMod.isDataModelChanged()) 
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación ?")==0)
            {
                lblMsgSis.setText("Guardando");
                if (valTip==0)
                {
                    if (actualizarDet())
                    {
                        /////objUtiGP.ejecucionActualizacionAtributo(cadenaItems.toString()); 
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    }

                    else
                    {
                        mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.");
                        pgrSis.setValue(0);
                        lblMsgSis.setText("Cancelado");
                    }                    
                }
                else
                {
                    if (actualizarDetABC())
                    {
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    }

                    else
                    {
                        mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.");
                        pgrSis.setValue(0);
                        lblMsgSis.setText("Cancelado");
                    }                     
                    
                }

                    
                if (tblDat.getRowCount()>0)
                {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
                objThrGUI=null;                
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        
        objThrGuaGUI=null;
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
                    System.out.println("Version " + version);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(version.equals(rstLoc.getString("tx_ver"))){
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

    
    /** Creates new form ZafMae84 */
    public ZafMae84(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
           
            

////            if(!isVersionActualizada()){
////                mostrarMsgInf("Su versión del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
////                dispose();
////            }
////            else
////            {
            initComponents();
            if (!configurarFrm()) 
                exitForm();
////            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    } 
    
        /** Configurar el formulario. */
    private boolean configurarFrm()//chiqui
    {
        boolean blnRes=true;
        try
        {
                        //Configurar ZafVenConItm01:
            objVenConItm01GP=new ZafVenConItmGP01(objParSis, "a1.", optFil);
            panFil.add(objVenConItm01GP);
            objVenConItm01GP.setBounds(24, 50, 656, 20);

            
            
////            objVenConItm01GP.activa_desac(false); 
            
            
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);  
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);  
            panABC.setVisible(false);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + version);
            lblTit.setText(strAux);
            
            
           objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
             //ConfigurarCombos
            
        

            
            
            //Configurar los JTables.
            
            configurarTblDat();
            configurarTblTipos();
            cargarTipos();
            

            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
private boolean configurarTblTipos()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN2,"");
            vecCab.add(INT_TBL_DAT_CHK_SELECCION,"");
            vecCab.add(INT_TBL_DAT_NOMBRE,"Nombre");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModTipo=new ZafTblMod();
            objTblModTipo.setHeader(vecCab);
            tblLoc.setModel(objTblModTipo);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN2).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SELECCION).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOMBRE).setPreferredWidth(200);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK_SELECCION).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);

            
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaTipo());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblTipoMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_SELECCION);
            objTblModTipo.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_DAT_LIN2).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SELECCION).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_NOMBRE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SELECCION).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
                        
                        ocultaColumnasPorFiltro();
                    }
                    else
                    {
                        ocultaColumnasPorFiltro();
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModTipo.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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

private boolean configurarTblABC()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatABC=new Vector();    //Almacena los datos
            vecCabABC=new Vector(6);   //Almacena las cabeceras
            vecCabABC.clear();
            vecCabABC.add(INT_TBL_DAT_ABC_LIN2,"");
            vecCabABC.add(INT_TBL_DAT_ABC_CHK_SELECCION,"");
            vecCabABC.add(INT_TBL_DAT_ABC_NOMBRE,"Nombre");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModABC=new ZafTblMod();
            objTblModABC.setHeader(vecCabABC);
            tblABC.setModel(objTblModABC);
            //Configurar JTable: Establecer tipo de selección.
            tblABC.setRowSelectionAllowed(true);
            tblABC.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuABC=new ZafTblPopMnu(tblABC);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblABC.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblABC.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_ABC_LIN2).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ABC_CHK_SELECCION).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ABC_NOMBRE).setPreferredWidth(200);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_ABC_CHK_SELECCION).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblABC.getTableHeader().setReorderingAllowed(false);

            
//////////////            //Configurar JTable: Ocultar columnas del sistema.
////////////////            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblABC);
//////////////            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//////////////            tblABC.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
//////////////            //Configurar JTable: Establecer los listener para el TableHeader.
//////////////            tblABC.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//////////////                public void mouseClicked(java.awt.event.MouseEvent evt) {
//////////////                    tblTipoMouseClicked(evt);
//////////////                }
//////////////            });
            //Configurar JTable: Establecer columnas editables.
            vecAuxABC=new Vector();
            vecAuxABC.add("" + INT_TBL_DAT_ABC_CHK_SELECCION);
            objTblModABC.setColumnasEditables(vecAuxABC);
            vecAuxABC=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblABC);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblABC);
            tcmAux.getColumn(INT_TBL_DAT_ABC_LIN2).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_ABC_CHK_SELECCION).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_ABC_NOMBRE).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblABC);
            tcmAux.getColumn(INT_TBL_DAT_ABC_CHK_SELECCION).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    if (objTblCelEdiChk.isChecked())
////                    {
////                        
////                        ocultaColumnasPorFiltro();
////                    }
////                    else
////                    {
////                        ocultaColumnasPorFiltro();
////                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblABC.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModABC.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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

private boolean cargarTipos()//chiqui
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
                    strSQL+="SELECT trim(tx_nom) as nombre FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' AND tx_nom NOT IN ('Control', 'SubCompras', 'SubControl') order by tx_nom asc";
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT trim(tx_nom) as nombre FROM Dynamics2021..tbm_tipExt WHERE st_reg ='A' AND tx_nom NOT IN ('Control', 'SubCompras', 'SubControl') order by tx_nom asc";
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN2,"");
                    vecReg.add(INT_TBL_DAT_CHK_SELECCION,Boolean.TRUE);
                    vecReg.add(INT_TBL_DAT_NOMBRE,rst.getString("nombre"));
                    vecDat.add(vecReg);
                }
                
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModTipo.setData(vecDat);
                tblLoc.setModel(objTblModTipo);
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

private boolean cargarABC()//chiqui
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
                    strSQL+="SELECT trim(tx_catpar) as nombre FROM Dynamics2021..tbm_catpar WHERE st_reg ='A'  order by co_catpar asc";
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT trim(tx_catpar) as nombre FROM Dynamics2021..tbm_catpar WHERE st_reg ='A'  order by co_catpar asc";
                }
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatABC.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecRegABC=new Vector();
                    vecRegABC.add(INT_TBL_DAT_ABC_LIN2,"");
                    vecRegABC.add(INT_TBL_DAT_ABC_CHK_SELECCION,Boolean.TRUE);
                    vecRegABC.add(INT_TBL_DAT_ABC_NOMBRE,rst.getString("nombre"));
                    vecDatABC.add(vecRegABC);
                }
                
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModABC.setData(vecDatABC);
                tblABC.setModel(objTblModABC);
                vecDatABC.clear();
                blnMarTodChkTblEmpABC=false;
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblTipoMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModTipo.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK_SELECCION)
            {
                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModTipo.setChecked(true, i, INT_TBL_DAT_CHK_SELECCION);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModTipo.setChecked(false, i, INT_TBL_DAT_CHK_SELECCION);
                    }
                    blnMarTodChkTblEmp=true;
                }
                ocultaColumnasPorFiltro();                
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    

private class ZafMouMotAdaTipo extends java.awt.event.MouseMotionAdapter//chiqui
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_LIN2:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CHK_SELECCION:
                    strMsg="Selección";
                    break;
                case INT_TBL_DAT_NOMBRE:
                    strMsg="Nombre del Tipo de Atributo";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }
    
private boolean ConfigurarMaterial()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_MATERIAL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConMate=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarLinea()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_LIN_COM' ";   
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConLine=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu =null;
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarCompras()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_COMPRAS' ";   
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=2;
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConCompr=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu =null;
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarCategoria()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CAT_COM' "; 
             
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=0;
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConCate=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu =null;
            
            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
private boolean ConfigurarMarca()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_MARCA' "; 
              //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=2;             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConMarc=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarABC()
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_nivpar");
            arlCam.add("tx_nivpar");
            arlCam.add("nd_porautubi");
            arlCam.add("st_reg");

            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripcion");
            arlAli.add("Por.Aut.");
            arlAli.add("Estado.");

            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");


            //Armar la sentencia SQL.
             String  strSQL="";
             strSQL+="SELECT co_nivpar ,tx_nivpar ,nd_porautubi,st_reg ";
             strSQL+="FROM  Dynamics2021..tbm_nivpar ";
             strSQL+="WHERE  st_reg='A' "; 

            objVenABCTuval=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCFre=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCValor=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCClas=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenABCPeso=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   


private boolean ConfigurarClase1()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'TUV_CLA_COM' ";            
             
            System.out.println("--- "+strSQL);
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConClase1=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarRefGeneral()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_GENERAL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConRefGen=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

private boolean ConfigurarRefDetallada()//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_Desc) as tx_desLar,'S' as st_reg ";
             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
             strSQL+="WHERE  Extender_Form_ID = 'MSW_REF_DETALL' ";             
             
           
////            objVenConMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            objVenConRefDet=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
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
    private boolean configurarTblDat()//chiqui
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cod.Itm.");
            vecCab.add(INT_TBL_DAT_COD_3L,"3L");
            vecCab.add(INT_TBL_DAT_COD_ZAF,"Cod.Zaf.");
            vecCab.add(INT_TBL_DAT_NOM_ITEM,"Nombre");
            vecCab.add(INT_TBL_DAT_NOM_TAMA,"Tamaño");
            vecCab.add(INT_TBL_DAT_NOM_PESO,"Peso");
            vecCab.add(INT_TBL_DAT_COD_MARC,"Cod.Marca");
            vecCab.add(INT_TBL_DAT_NOM_MARC,"Marca");            
            vecCab.add(INT_TBL_DAT_BUT_COM_MARC,"");
            vecCab.add(INT_TBL_DAT_COD_LINEA,"Cod.Linea");
            vecCab.add(INT_TBL_DAT_NOM_LINEA,"Linea");
            vecCab.add(INT_TBL_DAT_BUT_COM_LINEA,"");            
            vecCab.add(INT_TBL_DAT_COD_CLASE1,"Cod.Clase1");
            vecCab.add(INT_TBL_DAT_NOM_CLASE1,"Clase");    
            vecCab.add(INT_TBL_DAT_BUT_CLASE1,"");            
            vecCab.add(INT_TBL_DAT_COD_MAT,"Cod.Material");
            vecCab.add(INT_TBL_DAT_NOM_MAT,"Material"); 
            vecCab.add(INT_TBL_DAT_BUT_COM_MAT,"");            
            vecCab.add(INT_TBL_DAT_COD_COMPR,"Cod.Compras");
            vecCab.add(INT_TBL_DAT_NOM_COMP,"Compras");
            vecCab.add(INT_TBL_DAT_BUT_COM_COMP,"");            
            vecCab.add(INT_TBL_DAT_COD_CATE,"Cod.Categoria");
            vecCab.add(INT_TBL_DAT_NOM_CATE,"Categoria");
            vecCab.add(INT_TBL_DAT_BUT_COM_CATE,"");     
          
////            vecCab.add(INT_TBL_DAT_NOM_REF_GE,"Ref. General");            
////            vecCab.add(INT_TBL_DAT_COD_REF_DET,"Cod.Ref.Detallada");
////            vecCab.add(INT_TBL_DAT_BUT_COM_REF_DET,"");
////            vecCab.add(INT_TBL_DAT_NOM_REF_DET,"Ref. Detallada");            
////            vecCab.add(INT_TBL_DAT_COD_CLASE,"Cod.Clase");
////            vecCab.add(INT_TBL_DAT_BUT_COM_CLASE,"");
////            vecCab.add(INT_TBL_DAT_NOM_CLASE,"Clase");            
            
              
            //
            ConfigurarMarca();
            ConfigurarLinea();
////            ConfigurarClase();
            ConfigurarMaterial();
            ConfigurarRefGeneral();
            ConfigurarRefDetallada();
            ConfigurarCompras();
            ConfigurarCategoria();
            ConfigurarClase1();
////            ConfigurarRefDetallada1();      
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);  
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_NOM_TAMA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_NOM_PESO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
////            tblDat.setRowSelectionAllowed(true);
////            //tblDat.setRowSelectionAllowed(true);
////            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
////            objTblPopMnu=new ZafTblPopMnu(tblDat);
////            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            
            
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ZAF).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITEM).setPreferredWidth(500);
            tcmAux.getColumn(INT_TBL_DAT_NOM_TAMA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_PESO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MARC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_LINEA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LINEA).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MAT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLASE1).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLASE1).setPreferredWidth(200); 
            
            tcmAux.getColumn(INT_TBL_DAT_COD_COMPR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_COMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_CATE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CATE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CATE).setPreferredWidth(200);            
            
            
          
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MARC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setResizable(false); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_COMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CATE).setResizable(false);
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_COM_MAT);
            vecAux.add("" + INT_TBL_DAT_NOM_ITEM);            
            vecAux.add("" + INT_TBL_DAT_NOM_TAMA);
            vecAux.add("" + INT_TBL_DAT_NOM_PESO);
            vecAux.add("" + INT_TBL_DAT_BUT_COM_MARC);
            vecAux.add("" + INT_TBL_DAT_BUT_COM_LINEA);
            vecAux.add("" + INT_TBL_DAT_NOM_MARC);
            vecAux.add("" + INT_TBL_DAT_NOM_MAT);           
            vecAux.add("" + INT_TBL_DAT_NOM_LINEA);              
            vecAux.add("" + INT_TBL_DAT_NOM_CLASE1);   
            vecAux.add("" + INT_TBL_DAT_NOM_COMP);  
            vecAux.add("" + INT_TBL_DAT_BUT_COM_COMP);             
            vecAux.add("" + INT_TBL_DAT_NOM_CATE); 
            vecAux.add("" + INT_TBL_DAT_BUT_COM_CATE);             

            vecAux.add("" + INT_TBL_DAT_COD_ZAF);
////            vecAux.add("" + INT_TBL_DAT_BUT_COM_REF_GE);
////            vecAux.add("" + INT_TBL_DAT_BUT_COM_REF_DET);
            vecAux.add("" + INT_TBL_DAT_BUT_CLASE1);            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;            

            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MARC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CLASE).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_GE).setCellRenderer(objTblCelRenBut);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_REF_DET).setCellRenderer(objTblCelRenBut);     
            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setCellRenderer(objTblCelRenBut);  
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_COMP).setCellRenderer(objTblCelRenBut); 
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CATE).setCellRenderer(objTblCelRenBut); 
            objTblCelRenBut=null;
//marca                    
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_MARC;
            intColTbl[1]=INT_TBL_DAT_NOM_MARC;


            objTblCelEdiButVcoMarca=new ZafTblCelEdiButVcoGP(tblDat, objVenConMarc, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MARC).setCellEditor(objTblCelEdiButVcoMarca);
            objTblCelEdiButVcoMarca.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });

            objTblCelEdiTxtVcoMarca=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConMarc, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setCellEditor(objTblCelEdiTxtVcoMarca);
            objTblCelEdiTxtVcoMarca.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConMarc.setCampoBusqueda(1);
                    objVenConMarc.setCriterio1(11);
                }               
            });  
            
            
             
//Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPri=new ZafTblPopMnu(tblDat);
            objTblPopMnuPri.setPegarEnabled(true);
            objTblPopMnuPri.setBorrarContenidoEnabled(true);
            objTblPopMnuPri.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
////                    System.out.println("ZafTblPopMnu... ");
////                    if (objTblPopMnuPri.isClickPegar())
////                    {
////                        
////                        System.out.println("pegoooooo xD ");
////                         int intColVen[]=new int[2];
////                    }
                    if (objTblPopMnuPri.isClickBorrarContenido())
                    {
                        System.out.println("isClickBorrarContenido... ");
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                    }
                    else if (objTblPopMnuPri.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[];
                        int  i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        
                        //marca
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_MARC))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConMarc.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MARC)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MARC).toString()))
                                {
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MARC);
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MARC);                                
                                }
                                else
                                {
                                    objVenConMarc.setCampoBusqueda(1);
                                    objVenConMarc.setCriterio1(11);
                                    objVenConMarc.cargarDatos();
                                    objVenConMarc.setVisible(true);
                                    if (objVenConMarc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MARC);
                                    objTblMod.setValueAt(objVenConMarc.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MARC); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_MARC); 
                                    }
                                }                                  
                            }
                        }
                        //material
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_MAT))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConMate.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MAT)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_MAT).toString()))
                                {
                                    objTblMod.setValueAt(objVenConMate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MAT);
                                    objTblMod.setValueAt(objVenConMate.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MAT);                                
                                }
                                else
                                {
                                    objVenConMate.setCampoBusqueda(1);
                                    objVenConMate.setCriterio1(11);
                                    objVenConMate.cargarDatos();
                                    objVenConMate.setVisible(true);
                                    if (objVenConMate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConMate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_MAT);
                                    objTblMod.setValueAt(objVenConMate.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_MAT); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_MAT); 
                                    }
                                }                                  
                            }
                        }   
                         //linea   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_LINEA))
                        {
                         
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConLine.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_LINEA)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_LINEA).toString()))
                                {
                                    objTblMod.setValueAt(objVenConLine.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_LINEA);
                                    objTblMod.setValueAt(objVenConLine.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_LINEA);                                
                                }
                                else
                                {
                                    objVenConLine.setCampoBusqueda(1);
                                    objVenConLine.setCriterio1(11);
                                    objVenConLine.cargarDatos();
                                    objVenConLine.setVisible(true);
                                    if (objVenConLine.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConLine.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_LINEA);
                                    objTblMod.setValueAt(objVenConLine.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_LINEA); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_LINEA); 
                                    }
                                }                                  
                            }
                        }

                         //COMPRA   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_COMP))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConCompr.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_COMP)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_COMP).toString()))
                                {
                                    objTblMod.setValueAt(objVenConCompr.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_COMPR);
                                    objTblMod.setValueAt(objVenConCompr.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_COMP);                                
                                }
                                else
                                {
                                    objVenConCompr.setCampoBusqueda(1);
                                    objVenConCompr.setCriterio1(11);
                                    objVenConCompr.cargarDatos();
                                    objVenConCompr.setVisible(true);
                                    if (objVenConCompr.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConCompr.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_COMPR);
                                    objTblMod.setValueAt(objVenConCompr.getValueAt(2), intFilSel[i], INT_TBL_DAT_NOM_COMP); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_COMP); 
                                    }
                                }                                  
                            }
                        }                        
                        
                         //CATEGORIA   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_CATE))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConCate.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CATE)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CATE).toString()))
                                {
                                    objTblMod.setValueAt(objVenConCate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CATE);
                                    objTblMod.setValueAt(objVenConCate.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_CATE);                                
                                }
                                else
                                {
                                    objVenConCate.setCampoBusqueda(1);
                                    objVenConCate.setCriterio1(11);
                                    objVenConCate.cargarDatos();
                                    objVenConCate.setVisible(true);
                                    if (objVenConCate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenConCate.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CATE);
                                    objTblMod.setValueAt(objVenConCate.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_CATE); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_CATE); 
                                    }
                                }                                  
                            }
                        }                        

                        //clase
                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_CLASE1))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenConClase1.buscar("tx_desCor", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CLASE1)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_CLASE1).toString()))
                                {
                                    objTblMod.setValueAt(objVenConClase1.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CLASE1);
                                    objTblMod.setValueAt(objVenConClase1.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_CLASE1);                                
                                }
                                else
                                {
                                    objVenConClase1.setCampoBusqueda(1);
                                    objVenConClase1.setCriterio1(11);
                                    objVenConClase1.cargarDatos();
                                    objVenConClase1.setVisible(true);
                                    if (objVenConClase1.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                        objTblMod.setValueAt(objVenConClase1.getValueAt(0), intFilSel[i], INT_TBL_DAT_COD_CLASE1);
                                        objTblMod.setValueAt(objVenConClase1.getValueAt(3), intFilSel[i], INT_TBL_DAT_NOM_CLASE1); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_NOM_CLASE1); 
                                    }
                                }                                  
                            }
                        }
                        //peso
////                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_PESO))
////                        {
////                            int nuevo =0;
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////////                                nuevo =  Integer.parseInt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_PESO)) ;
////                                if (objUti.isNumero(""))
////                                {
////                                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_PESO), intFilSel[i], INT_TBL_DAT_NOM_PESO); 
////                                }
////                                else
////                                    objTblMod.setValueAt(0, intFilSel[i], INT_TBL_DAT_NOM_PESO); 
////                            }
////                        }
                          
////                        objVenConMarc.setCampoBusqueda(2);
////                        objVenConMarc.setVisible(true);   


                      
                        

////                            objTblCelEdiTxtVcoMarca=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConMarc, intColVen, intColTbl);
////                            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setCellEditor(objTblCelEdiTxtVcoMarca);
////                            objTblCelEdiTxtVcoMarca.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                                    System.out.println("aqui la busqueda::...");
////                                    objVenConMarc.setCampoBusqueda(1);
////                                    objVenConMarc.setCriterio1(11);
////                                }               
////                            });

            
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
////                            }
                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
////                        {
////                            calcularCosUniFac();
////                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
////                        {
////                            calcularMarUtiCos();
////                        }
                    
                }
            });

            
//material            
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_MAT;
            intColTbl[1]=INT_TBL_DAT_NOM_MAT;
            


            objTblCelEdiButVcoMate=new ZafTblCelEdiButVcoGP(tblDat, objVenConMate, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setCellEditor(objTblCelEdiButVcoMate);
            objTblCelEdiButVcoMate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            objTblCelEdiTxtVcoMate=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConMate, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_MAT).setCellEditor(objTblCelEdiTxtVcoMate);
            objTblCelEdiTxtVcoMate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConMate.setCampoBusqueda(1);
                    objVenConMate.setCriterio1(11);
                }               
            });             

//linea            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=3;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_LINEA;
            intColTbl[1]=INT_TBL_DAT_NOM_LINEA;
            


            objTblCelEdiButVcoLine=new ZafTblCelEdiButVcoGP(tblDat, objVenConLine, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setCellEditor(objTblCelEdiButVcoLine);
            objTblCelEdiButVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoLine=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConLine, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LINEA).setCellEditor(objTblCelEdiTxtVcoLine);
            objTblCelEdiTxtVcoLine.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConLine.setCampoBusqueda(1);
                    objVenConLine.setCriterio1(11);
                }               
            });   
            
//compras            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_COMPR;
            intColTbl[1]=INT_TBL_DAT_NOM_COMP;
            


            objTblCelEdiButVcoCompra=new ZafTblCelEdiButVcoGP(tblDat, objVenConCompr, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_COMP).setCellEditor(objTblCelEdiButVcoCompra);
            objTblCelEdiButVcoCompra.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoCompra=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConCompr, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_COMP).setCellEditor(objTblCelEdiTxtVcoCompra);
            objTblCelEdiTxtVcoCompra.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConCompr.setCampoBusqueda(1);
                    objVenConCompr.setCriterio1(11);
                }               
            });             

            
//CATEGORIA            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=3;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CATE;
            intColTbl[1]=INT_TBL_DAT_NOM_CATE;
            


            objTblCelEdiButVcoCate=new ZafTblCelEdiButVcoGP(tblDat, objVenConCate, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_CATE).setCellEditor(objTblCelEdiButVcoCate);
            objTblCelEdiButVcoCate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoCate=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConCate, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CATE).setCellEditor(objTblCelEdiTxtVcoCate);
            objTblCelEdiTxtVcoCate.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConCate.setCampoBusqueda(1);
                    objVenConCate.setCriterio1(11);
                }               
            });                
            //CLASE1  
            
 intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=3;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_CLASE1;
            intColTbl[1]=INT_TBL_DAT_NOM_CLASE1;
            


            objTblCelEdiButVcoClase1=new ZafTblCelEdiButVcoGP(tblDat, objVenConClase1, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setCellEditor(objTblCelEdiButVcoClase1);
            objTblCelEdiButVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoClase1=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConClase1, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLASE1).setCellEditor(objTblCelEdiTxtVcoClase1);
            objTblCelEdiTxtVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenConClase1.setCampoBusqueda(1);
                    objVenConClase1.setCriterio1(11);
                }               
            });   

            
////////////                intColVen=null;
////////////                intColTbl=null;
////////////                //newEnd
////////////
////////////                intColVen=new int[2];
////////////                intColVen[0]=1;
////////////                intColVen[1]=2;
////////////                intColTbl=new int[2];
////////////                intColTbl[0]=INT_TBL_DAT_COD_CLASE1;
////////////                intColTbl[1]=INT_TBL_DAT_NOM_CLASE1;
////////////
////////////
////////////            objTblCelEdiButVcoClase1=new ZafTblCelEdiButVcoGP(tblDat, objVenConClase1, intColVen, intColTbl);
////////////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setCellEditor(objTblCelEdiButVcoClase1);
////////////            objTblCelEdiButVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////////////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////////////                    
////////////                    int intFilSel=0;
////////////                    intFilSel=tblDat.getSelectedRow();
////////////                    String hijo = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////////////
////////////                    ConfigurarAtri2(2,"",hijo);//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////////////                    System.out.println("aqui la busqueda::...");
////////////                    objVenConClase1.setCampoBusqueda(1);
////////////                    objVenConClase1.setCriterio1(11);
////////////                    objVenConClase1.cargarDatos();
////////////                    objVenConClase1.setVisible(true);   
////////////                    if (objVenConClase1.getSelectedButton()==ZafVenConGP.INT_BUT_ACE)
////////////                    {
////////////                        objTblMod.setValueAt(objVenConClase1.getValueAt(1), intFilSel, INT_TBL_DAT_COD_CLASE1);   
////////////                        objTblMod.setValueAt(objVenConClase1.getValueAt(2), intFilSel, INT_TBL_DAT_NOM_CLASE1);    
////////////                    }
////////////                    else
////////////                    {
////////////                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_COD_CLASE1);                         
////////////                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_NOM_CLASE1);
////////////                    }
////////////
////////////                    
////////////               }
////////////            });
            
////            objTblCelEdiTxtVcoClase1=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConClase1, intColVen, intColTbl);
////            tcmAux.getColumn(INT_TBL_DAT_NOM_CLASE1).setCellEditor(objTblCelEdiTxtVcoClase1);
////            objTblCelEdiTxtVcoClase1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                    System.out.println("aqui la busqueda::...");
////                    objVenConClase1.setCampoBusqueda(1);
////                    objVenConClase1.setCriterio1(11);
////                }               
////            });               
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);   

            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);            
           
            
            
            
            
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarTblDatABC()//chiqui
    {
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatABC=new Vector();    //Almacena los datos
            vecCabABC=new Vector();    //Almacena las cabeceras
            vecCabABC.clear();
            
    
            vecCabABC.add(INT_TBL_DAT_ABC_LIN,"");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_ITM,"Cod.Itm.");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_3L,"3L");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_ZAF,"Cod.Zaf.");
            vecCabABC.add(INT_TBL_DAT_ABC_NOM_ITEM,"Nombre");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_FRE,"Cod.Fre.");
            vecCabABC.add(INT_TBL_DAT_ABC_DES_FRE,"Des.Fre.");
            vecCabABC.add(INT_TBL_DAT_ABC_BUT_FRE,"");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_PES,"Cod.Pes.");            
            vecCabABC.add(INT_TBL_DAT_ABC_DES_PES,"Des.Pes.");
            vecCabABC.add(INT_TBL_DAT_ABC_BUT_PES,"");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_VAL,"Cod.Val.");
            vecCabABC.add(INT_TBL_DAT_ABC_DES_VAL,"Des.Val.");            
            vecCabABC.add(INT_TBL_DAT_ABC_BUT_VAL,"");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_TUV,"Cod.Tuv.");    
            vecCabABC.add(INT_TBL_DAT_ABC_DES_TUV,"Des.Tuv.");            
            vecCabABC.add(INT_TBL_DAT_ABC_BUT_TUV,"");
            vecCabABC.add(INT_TBL_DAT_ABC_COD_CLA,"Cod.Cla."); 
            vecCabABC.add(INT_TBL_DAT_ABC_DES_CLA,"Des.Cla.");            
            vecCabABC.add(INT_TBL_DAT_ABC_BUT_CLA,"");
            //
            configurarABC();
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCabABC);
            tblDat.setModel(objTblMod);

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCabABC);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);  
//////////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//////////            objTblModABC.setColumnDataType(INT_TBL_DAT_NOM_TAMA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
//////////            objTblModABC.setColumnDataType(INT_TBL_DAT_NOM_PESO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
////            tblDat.setRowSelectionAllowed(true);
////            //tblDat.setRowSelectionAllowed(true);
////            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_ABC_LIN);
            //Configurar JTable: Establecer el menú de contexto.
////            objTblPopMnu=new ZafTblPopMnu(tblDat);
////            objTblPopMnu.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            
            
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_ABC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_3L).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_ZAF).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_ABC_NOM_ITEM).setPreferredWidth(500);
            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_FRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_FRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_FRE).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_PES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_PES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_PES).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_VAL).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_TUV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_TUV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_TUV).setPreferredWidth(20);

            tcmAux.getColumn(INT_TBL_DAT_ABC_COD_CLA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_CLA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_CLA).setPreferredWidth(20);            
            
          
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_FRE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_PES).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_VAL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_TUV).setResizable(false); 
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_CLA).setResizable(false);
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAuxABC=new Vector();
////            vecAuxABC.add("" + INT_TBL_DAT_ABC_COD_FRE);
            vecAuxABC.add("" + INT_TBL_DAT_ABC_DES_FRE);            
            vecAuxABC.add("" + INT_TBL_DAT_ABC_BUT_FRE);
////            vecAuxABC.add("" + INT_TBL_DAT_ABC_COD_PES);
            vecAuxABC.add("" + INT_TBL_DAT_ABC_DES_PES);
            vecAuxABC.add("" + INT_TBL_DAT_ABC_BUT_PES);
////            vecAuxABC.add("" + INT_TBL_DAT_ABC_COD_VAL);
            vecAuxABC.add("" + INT_TBL_DAT_ABC_DES_VAL);           
            vecAuxABC.add("" + INT_TBL_DAT_ABC_BUT_VAL);              
////            vecAuxABC.add("" + INT_TBL_DAT_ABC_COD_TUV);   
            vecAuxABC.add("" + INT_TBL_DAT_ABC_DES_TUV);  
            vecAuxABC.add("" + INT_TBL_DAT_ABC_BUT_TUV);             
////            vecAuxABC.add("" + INT_TBL_DAT_ABC_COD_CLA); 
            vecAuxABC.add("" + INT_TBL_DAT_ABC_DES_CLA);             
            vecAuxABC.add("" + INT_TBL_DAT_ABC_BUT_CLA);
            objTblMod.setColumnasEditables(vecAuxABC);
            vecAuxABC=null;            

            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_FRE).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_PES).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_VAL).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_TUV).setCellRenderer(objTblCelRenBut);  
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_CLA).setCellRenderer(objTblCelRenBut);  
            objTblCelRenBut=null;
//Frecuencia                    
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_ABC_COD_FRE;
            intColTbl[1]=INT_TBL_DAT_ABC_DES_FRE;


            objTblCelEdiButVcoFre=new ZafTblCelEdiButVcoGP(tblDat, objVenABCFre, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_FRE).setCellEditor(objTblCelEdiButVcoFre);
            objTblCelEdiButVcoFre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });

            objTblCelEdiTxtVcoFre=new ZafTblCelEdiTxtVcoGP(tblDat, objVenABCFre, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_FRE).setCellEditor(objTblCelEdiTxtVcoFre);
            objTblCelEdiTxtVcoFre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenABCFre.setCampoBusqueda(1);
                    objVenABCFre.setCriterio1(11);
                }               
            });  
            
            
             
////Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPri=new ZafTblPopMnu(tblDat);
            objTblPopMnuPri.setPegarEnabled(true);
            objTblPopMnuPri.setBorrarContenidoEnabled(true);
            objTblPopMnuPri.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
////                    System.out.println("ZafTblPopMnu... ");
////                    if (objTblPopMnuPri.isClickPegar())
////                    {
////                        
////                        System.out.println("pegoooooo xD ");
////                         int intColVen[]=new int[2];
////                    }
                    if (objTblPopMnuPri.isClickBorrarContenido())
                    {
                        System.out.println("isClickBorrarContenido... ");
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                    }
                    else if (objTblPopMnuPri.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[];
                        int  i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        
                        //frecuencia
                        if (tblDat.isColumnSelected(INT_TBL_DAT_ABC_DES_FRE))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenABCFre.buscar("tx_nivpar", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_FRE)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_FRE).toString()))
                                {
                                    objTblMod.setValueAt(objVenABCFre.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_FRE);
                                    objTblMod.setValueAt(objVenABCFre.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_FRE);                                
                                }
                                else
                                {
                                    objVenABCFre.setCampoBusqueda(1);
                                    objVenABCFre.setCriterio1(11);
                                    objVenABCFre.cargarDatos();
                                    objVenABCFre.setVisible(true);
                                    if (objVenABCFre.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenABCFre.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_FRE);
                                    objTblMod.setValueAt(objVenABCFre.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_FRE); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_ABC_DES_FRE); 
                                    }
                                }                                  
                            }
                        }
                        //material
                        if (tblDat.isColumnSelected(INT_TBL_DAT_ABC_DES_PES))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenABCPeso.buscar("tx_nivpar", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_PES)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_PES).toString()))
                                {
                                    objTblMod.setValueAt(objVenABCPeso.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_PES);
                                    objTblMod.setValueAt(objVenABCPeso.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_PES);                                
                                }
                                else
                                {
                                    objVenABCPeso.setCampoBusqueda(1);
                                    objVenABCPeso.setCriterio1(11);
                                    objVenABCPeso.cargarDatos();
                                    objVenABCPeso.setVisible(true);
                                    if (objVenABCPeso.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenABCPeso.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_PES);
                                    objTblMod.setValueAt(objVenABCPeso.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_PES); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_ABC_DES_PES); 
                                    }
                                }                                  
                            }
                        }   
                         //VALOR DE VENTA   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_ABC_DES_VAL))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenABCValor.buscar("tx_nivpar", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_VAL)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_VAL).toString()))
                                {
                                    objTblMod.setValueAt(objVenABCValor.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_VAL);
                                    objTblMod.setValueAt(objVenABCValor.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_VAL);                                
                                }
                                else
                                {
                                    objVenABCValor.setCampoBusqueda(1);
                                    objVenABCValor.setCriterio1(11);
                                    objVenABCValor.cargarDatos();
                                    objVenABCValor.setVisible(true);
                                    if (objVenABCValor.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenABCValor.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_VAL);
                                    objTblMod.setValueAt(objVenABCValor.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_VAL); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_ABC_DES_VAL); 
                                    }
                                }                                  
                            }
                        }

                         //TUVAL   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_ABC_DES_TUV))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenABCTuval.buscar("tx_nivpar", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_TUV)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_TUV).toString()))
                                {
                                    objTblMod.setValueAt(objVenABCTuval.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_TUV);
                                    objTblMod.setValueAt(objVenABCTuval.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_TUV);                                
                                }
                                else
                                {
                                    objVenABCTuval.setCampoBusqueda(1);
                                    objVenABCTuval.setCriterio1(11);
                                    objVenABCTuval.cargarDatos();
                                    objVenABCTuval.setVisible(true);
                                    if (objVenABCTuval.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenABCTuval.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_TUV);
                                    objTblMod.setValueAt(objVenABCTuval.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_TUV); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_ABC_DES_TUV); 
                                    }
                                }                                  
                            }
                        }                        
                        
                         //CLASE   
                        if (tblDat.isColumnSelected(INT_TBL_DAT_ABC_DES_CLA))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                if (objVenABCClas.buscar("tx_nivpar", objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_CLA)==null?"":objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_ABC_DES_CLA).toString()))
                                {
                                    objTblMod.setValueAt(objVenABCClas.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_CLA);
                                    objTblMod.setValueAt(objVenABCClas.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_CLA);                                
                                }
                                else
                                {
                                    objVenABCClas.setCampoBusqueda(1);
                                    objVenABCClas.setCriterio1(11);
                                    objVenABCClas.cargarDatos();
                                    objVenABCClas.setVisible(true);
                                    if (objVenABCClas.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                                    {
                                    objTblMod.setValueAt(objVenABCClas.getValueAt(1), intFilSel[i], INT_TBL_DAT_ABC_COD_CLA);
                                    objTblMod.setValueAt(objVenABCClas.getValueAt(2), intFilSel[i], INT_TBL_DAT_ABC_DES_CLA); 
                                    }
                                    else
                                    {
                                        objTblMod.setValueAt("NO TIENE", intFilSel[i], INT_TBL_DAT_ABC_DES_CLA); 
                                    }
                                }                                  
                            }
                        }                        

                        //peso
////                        if (tblDat.isColumnSelected(INT_TBL_DAT_NOM_PESO))
////                        {
////                            int nuevo =0;
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////////                                nuevo =  Integer.parseInt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_PESO)) ;
////                                if (objUti.isNumero(""))
////                                {
////                                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_NOM_PESO), intFilSel[i], INT_TBL_DAT_NOM_PESO); 
////                                }
////                                else
////                                    objTblMod.setValueAt(0, intFilSel[i], INT_TBL_DAT_NOM_PESO); 
////                            }
////                        }
                          
////                        objVenConMarc.setCampoBusqueda(2);
////                        objVenConMarc.setVisible(true);   


                      
                        

////                            objTblCelEdiTxtVcoMarca=new ZafTblCelEdiTxtVcoGP(tblDat, objVenConMarc, intColVen, intColTbl);
////                            tcmAux.getColumn(INT_TBL_DAT_NOM_MARC).setCellEditor(objTblCelEdiTxtVcoMarca);
////                            objTblCelEdiTxtVcoMarca.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
////                                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
////                                    System.out.println("aqui la busqueda::...");
////                                    objVenConMarc.setCampoBusqueda(1);
////                                    objVenConMarc.setCriterio1(11);
////                                }               
////                            });

            
////                            for (i=0; i<intFilSel.length; i++)
////                            {
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
////                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
////                            }
                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_FAC_COS_UNI))
////                        {
////                            calcularCosUniFac();
////                        }
////                        else if (tblDat.isColumnSelected(INT_TBL_DAT_PRE_VTA1))
////                        {
////                            calcularMarUtiCos();
////                        }
                    
                }
            });

            
//PESO            
            intColVen=null;
            intColTbl=null;
            
            //new
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_ABC_COD_PES;
            intColTbl[1]=INT_TBL_DAT_ABC_DES_PES;
            


            objTblCelEdiButVcoPes=new ZafTblCelEdiButVcoGP(tblDat, objVenABCPeso, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_PES).setCellEditor(objTblCelEdiButVcoPes);
            objTblCelEdiButVcoPes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               }
            });
            
            objTblCelEdiTxtVcoPes=new ZafTblCelEdiTxtVcoGP(tblDat, objVenABCPeso, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_PES).setCellEditor(objTblCelEdiTxtVcoPes);
            objTblCelEdiTxtVcoPes.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenABCPeso.setCampoBusqueda(1);
                    objVenABCPeso.setCriterio1(11);
                }               
            });             

//Valor de Venta            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_ABC_COD_VAL;
            intColTbl[1]=INT_TBL_DAT_ABC_DES_VAL;
////////////////////            


            objTblCelEdiButVcoVal=new ZafTblCelEdiButVcoGP(tblDat, objVenABCValor, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_VAL).setCellEditor(objTblCelEdiButVcoVal);
            objTblCelEdiButVcoVal.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoVal=new ZafTblCelEdiTxtVcoGP(tblDat, objVenABCValor, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_VAL).setCellEditor(objTblCelEdiTxtVcoVal);
            objTblCelEdiTxtVcoVal.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenABCValor.setCampoBusqueda(1);
                    objVenABCValor.setCriterio1(11);
                }               
            });   
            
//TUVAL            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_ABC_COD_TUV;
            intColTbl[1]=INT_TBL_DAT_ABC_DES_TUV;
//////////////////////            


            objTblCelEdiButVcoTuv=new ZafTblCelEdiButVcoGP(tblDat, objVenABCTuval, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_TUV).setCellEditor(objTblCelEdiButVcoTuv);
            objTblCelEdiButVcoTuv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    int intFilSel=0;
                    intFilSel=tblDat.getSelectedRow();
                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ABC_DES_TUV).toString();                    

                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
                    System.out.println("aqui la busqueda::...");
                    objVenABCTuval.setCampoBusqueda(1);
                    objVenABCTuval.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoTuv=new ZafTblCelEdiTxtVcoGP(tblDat, objVenABCTuval, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_TUV).setCellEditor(objTblCelEdiTxtVcoTuv);
            objTblCelEdiTxtVcoTuv.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenABCTuval.setCampoBusqueda(1);
                    objVenABCTuval.setCriterio1(11);
                }               
            });             

            
//CLASE            
            intColVen=null;
            intColTbl=null;
            //newEnd
            
            intColVen=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            intColTbl=new int[2];
            intColTbl[0]=INT_TBL_DAT_ABC_COD_CLA;
            intColTbl[1]=INT_TBL_DAT_ABC_DES_CLA;
            


            objTblCelEdiButVcoCla=new ZafTblCelEdiButVcoGP(tblDat, objVenABCClas, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_BUT_CLA).setCellEditor(objTblCelEdiButVcoCla);
            objTblCelEdiButVcoCla.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
////                    int intFilSel=0;
////                    intFilSel=tblDat.getSelectedRow();
////                    String padre = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_LINEA).toString();                    
////
////                    ConfigurarAtri2(1,padre,"");//debo enviar lo que es la opcion seleccionada del combo de tipos                     
////                    System.out.println("aqui la busqueda::...");
////                    objVenConLine.setCampoBusqueda(1);
////                    objVenConLine.setCriterio1(11);                    
               }
            });
            
            objTblCelEdiTxtVcoCla=new ZafTblCelEdiTxtVcoGP(tblDat, objVenABCClas, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_ABC_DES_CLA).setCellEditor(objTblCelEdiTxtVcoCla);
            objTblCelEdiTxtVcoCla.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("aqui la busqueda::...");
                    objVenABCClas.setCampoBusqueda(1);
                    objVenABCClas.setCriterio1(11);
                }               
            });                
////////////            


            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);   

            
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);            
           
            
            
            
            
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
private boolean ConfigurarAtri2(int tipo, String padre,String hijo)//chiqui
    {
        boolean blnRes=true;
        try
        {
         ArrayList arlCam=new ArrayList();
            arlCam.add("co_reg");
            arlCam.add("tx_desCor");
            arlCam.add("tx_desLar");
            arlCam.add("st_reg");
             
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Desc.Corta");
            arlAli.add("Desc.Larga");
            arlAli.add("Estado.");
            
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("103");
            arlAncCol.add("160");
            arlAncCol.add("170");
            arlAncCol.add("110");
            
            
              
            //Armar la sentencia SQL.
             String  strSQL="";
////             strSQL+="SELECT a.co_reg,a.tx_desCor,a.tx_desLar,a.st_reg FROM tbm_var AS a WHERE a.co_grp = 4"; 
////             strSQL+="SELECT 1 asco_reg, trim(a.material) as tx_desCor, 'A' as st_reg    from ("; 
////             strSQL+="          SELECT DISTINCT (STRGA255) as material"; 
////             strSQL+="          FROM EXT01101 where Field_ID  =  120"; 
////             strSQL+="              and STRGA255!=''  ) as a";
             
////             strSQL+="SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg ";
////             strSQL+="FROM  "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
////             strSQL+="WHERE  Extender_Form_ID = 'MSW_MATERIAL' ";   
             
////////                if  (tipo ==1)
////////                    strSQL+=" SELECT distinct(trim(det.tx_cfgPadDat)) as dato, det.co_cfgPad as codigo \n";
////////                else
////////                    
////////                    strSQL+=" SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo \n";
////////                strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
////////                strSQL+="   inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv) \n";
////////                strSQL+="   inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip) \n";
////////                if  (tipo ==1)
////////                    strSQL+=" WHERE  tip.tx_nom ='"+ padre +"'\n";                
////////                else
////////                {
////////                    strSQL+=" WHERE  det.tx_cfgPadDat ='"+ hijo +"'\n"; 
////////                    strSQL+="   AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)";  
////////                }

                if  (tipo ==1)
                {
                    strSQL+=" SELECT distinct(trim(det.tx_cfgPadDat)) as dato, det.co_cfgPad as codigo \n";
                    strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
                    strSQL+="   inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv) \n";
                    strSQL+="   inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip) \n";
                    strSQL+=" WHERE  tip.tx_nom ='"+ padre +"'\n";                
                }
                    
                else
                {
                    
                    if(hijo.equals("VALVULAS"))
                    {
                        strSQL+=" SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg \n"; 
                        strSQL+=" FROM tuval..EXT01200 as ex \n"; 
                        strSQL+=" inner join ( \n";                         
                        
                        strSQL+=" SELECT distinct(trim(ab.tx_cfgHijDat)) as dato , ab.co_cfghij as codigo \n";
                        strSQL+=" FROM Dynamics2021.dbo.tbm_cfgAtrInv as ab \n";
                        strSQL+=" inner join ( \n";                
                        strSQL+="       SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo  \n";
                        strSQL+="       FROM Dynamics2021.dbo.tbm_cfgAtrInv as det  \n";
                        strSQL+="           inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)  \n";
                        strSQL+="           inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)   \n";   
                        strSQL+=" WHERE  det.tx_cfgPadDat ='"+ hijo +"'\n"; 
                        strSQL+="   AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)) as pa on (ab.tx_cfgPadDat = pa.dato)"; 
                        strSQL+=" WHERE   ab.co_cfgHij != '' ) as ex01 on (ex01.dato = ex.UD_Form_Field_ID) \n";       
                        strSQL+="  WHERE  ex.Extender_Form_ID = 'TUV_CLASE_SUB'\n";                        
                    }
                    else
                    {
                        strSQL+=" SELECT Extender_Record_ID as co_reg,TRIM(UD_Form_Field_ID) as tx_desCor ,TRIM(UD_Form_Field_ID) as tx_desLar,'S' as st_reg \n"; 
                        strSQL+=" FROM tuval..EXT01200 as ex \n"; 
                        strSQL+=" inner join ( \n";  
                        
                        strSQL+=" SELECT distinct(trim(det.tx_cfgHijDat)) as dato , det.co_cfghij as codigo   \n";
                        strSQL+=" FROM  Dynamics2021.dbo.tbm_cfgAtrInv as det \n";
                        strSQL+="       inner join Dynamics2021.dbo.tbm_atrInv as cab on (det.co_cfgPad = co_atrInv)  \n";                
                        strSQL+="       inner join Dynamics2021.dbo.tbm_tipExt as tip on (cab.tx_desCor = tip.tx_tip)  \n";
                        strSQL+="       WHERE  det.tx_cfgPadDat = '"+ hijo +"' \n";
                        strSQL+="           AND   (det.tx_cfgPadDat !='' AND ISNULL(LEN(det.tx_cfgHijDat),0) >0)  )  as ex01 on (ex01.dato = ex.UD_Form_Field_ID)  \n";  
                        strSQL+="  WHERE  ex.Extender_Form_ID = 'TUV_CLASE_SUB'\n";                                  
                    }
                    
                                       
                }
                 if (tipo==1)
                     objVenConLine=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
                 else
                     objVenConClase1=new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol);
                 
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
        }
        catch (Exception e)
        {
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter//chiqui
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ITM: strMsg="Código del Item"; break;
                case INT_TBL_DAT_COD_3L: strMsg="Código 3L"; break;
                case INT_TBL_DAT_COD_ZAF: strMsg="Código Zafiro"; break;
                case INT_TBL_DAT_NOM_ITEM: strMsg="Descripción del Item"; break;
                case INT_TBL_DAT_NOM_TAMA: strMsg="Descripción del Tamaño"; break;
                case INT_TBL_DAT_NOM_PESO: strMsg="Descripción del Peso"; break;
                case INT_TBL_DAT_NOM_MARC: strMsg="Descripción de la Marca"; break;
                case INT_TBL_DAT_BUT_COM_MARC: strMsg="Busqueda de Marca"; break;
                case INT_TBL_DAT_NOM_LINEA: strMsg="Descripción de Linea"; break;
                case INT_TBL_DAT_BUT_COM_LINEA: strMsg="Busqueda de Linea"; break;
                case INT_TBL_DAT_NOM_MAT: strMsg="Descripción del Material"; break;
                case INT_TBL_DAT_BUT_COM_MAT: strMsg="Busqueda de Material"; break;
                case INT_TBL_DAT_NOM_CLASE1: strMsg="Descripción de Clase&SubClase"; break;
                case INT_TBL_DAT_NOM_CATE: strMsg="Descripcion de Categoria"; break;                
                case INT_TBL_DAT_BUT_COM_CATE: strMsg="Busqueda de Categoria"; break; 

                default: strMsg=""; break;
                

            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
 private class ZafMouMotAdaABC extends java.awt.event.MouseMotionAdapter//chiqui
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ITM: strMsg="Código del Item"; break;
                case INT_TBL_DAT_COD_3L: strMsg="Código 3L"; break;
                case INT_TBL_DAT_COD_ZAF: strMsg="Código Zafiro"; break;
                case INT_TBL_DAT_NOM_ITEM: strMsg="Descripción del Item"; break;
                case INT_TBL_DAT_NOM_TAMA: strMsg="Descripción del Tamaño"; break;
                case INT_TBL_DAT_NOM_PESO: strMsg="Descripción del Peso"; break;
                case INT_TBL_DAT_NOM_MARC: strMsg="Descripción de la Marca"; break;
                case INT_TBL_DAT_BUT_COM_MARC: strMsg="Busqueda de Marca"; break;
                case INT_TBL_DAT_NOM_LINEA: strMsg="Descripción de Linea"; break;
                case INT_TBL_DAT_BUT_COM_LINEA: strMsg="Busqueda de Linea"; break;
                case INT_TBL_DAT_NOM_MAT: strMsg="Descripción del Material"; break;
                case INT_TBL_DAT_BUT_COM_MAT: strMsg="Busqueda de Material"; break;
                case INT_TBL_DAT_NOM_CLASE1: strMsg="Descripción de Clase&SubClase"; break;
                case INT_TBL_DAT_NOM_CATE: strMsg="Descripcion de Categoria"; break;                
                case INT_TBL_DAT_BUT_COM_CATE: strMsg="Busqueda de Categoria"; break; 

                default: strMsg=""; break;
                

            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }    
    
//    
//private boolean mostrarMarca(int intTipBus)//chiqui
//    {
//        boolean blnRes=true;
//        try
//        {
//            switch (intTipBus)
//            {
//               case 0: //Mostrar la ventana de consulta.
//                    objVenConMarc.setCampoBusqueda(2);
//                    objVenConMarc.setVisible(true);
//                    if (objVenConMarc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                    {
//                        txtCodMar.setText(objVenConMarc.getValueAt(1));
//                        txtNomMar.setText(objVenConMarc.getValueAt(2));
//                    }
//                    break;
//                case 1: //Búsqueda directa por "Código".
//                    if (objVenConMarc.buscar("co_reg", txtCodMar.getText()))
//                    {
//                        txtCodMar.setText(objVenConMarc.getValueAt(1));
//                        txtNomMar.setText(objVenConMarc.getValueAt(2));
//                    }
//                    else
//                    {
//                        objVenConMarc.setCampoBusqueda(0);
//                        objVenConMarc.setCriterio1(11);
//                        objVenConMarc.cargarDatos();
//                        objVenConMarc.setVisible(true);
//                        if (objVenConMarc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodMar.setText(objVenConMarc.getValueAt(1));
//                            txtNomMar.setText(objVenConMarc.getValueAt(2));
//                        }
//                        else
//                        {
//                            txtCodMar.setText(strCodMar);
//                        }
//                    }
//                    break;
//                case 2: //Búsqueda directa por "Nombre".
//                    if (objVenConMarc.buscar("tx_deslar", txtNomMar.getText()))
//                    {
//                        txtCodMar.setText(objVenConMarc.getValueAt(1));
//                        txtNomMar.setText(objVenConMarc.getValueAt(2));
//                    }
//                    else
//                    {
//                        objVenConMarc.setCampoBusqueda(2);
//                        objVenConMarc.setCriterio1(11);
//                        objVenConMarc.cargarDatos();
//                        objVenConMarc.setVisible(true);
//                        if (objVenConMarc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
//                        {
//                            txtCodMar.setText(objVenConMarc.getValueAt(1));
//                            txtNomMar.setText(objVenConMarc.getValueAt(3));
//                        }
//                        else
//                        {
//                            txtNomMar.setText(strNomMar);
//                        }
//                    }
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }    
////
////
////private boolean mostrarMaterial(int intTipBus)//chiqui
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    objVenConMate.setCampoBusqueda(2);
////                    objVenConMate.setVisible(true);
////                    if (objVenConMate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodMar.setText(objVenConMate.getValueAt(1));
////                        txtNomMar.setText(objVenConMate.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (objVenConMate.buscar("co_reg", txtCodMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConMate.getValueAt(1));
////                        txtNomMar.setText(objVenConMate.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConMate.setCampoBusqueda(0);
////                        objVenConMate.setCriterio1(11);
////                        objVenConMate.cargarDatos();
////                        objVenConMate.setVisible(true);
////                        if (objVenConMate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConMate.getValueAt(1));
////                            txtNomMar.setText(objVenConMate.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodMar.setText(strCodMar);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (objVenConMate.buscar("tx_deslar", txtNomMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConMate.getValueAt(1));
////                        txtNomMar.setText(objVenConMate.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConMate.setCampoBusqueda(2);
////                        objVenConMate.setCriterio1(11);
////                        objVenConMate.cargarDatos();
////                        objVenConMate.setVisible(true);
////                        if (objVenConMate.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConMate.getValueAt(1));
////                            txtNomMar.setText(objVenConMate.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomMar.setText(strNomMar);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////
////private boolean mostrarLinea(int intTipBus)//chiqui
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    objVenConLine.setCampoBusqueda(2);
////                    objVenConLine.setVisible(true);
////                    if (objVenConLine.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodMar.setText(objVenConLine.getValueAt(1));
////                        txtNomMar.setText(objVenConLine.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (objVenConLine.buscar("co_reg", txtCodMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConLine.getValueAt(1));
////                        txtNomMar.setText(objVenConLine.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConLine.setCampoBusqueda(0);
////                        objVenConLine.setCriterio1(11);
////                        objVenConLine.cargarDatos();
////                        objVenConLine.setVisible(true);
////                        if (objVenConLine.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConLine.getValueAt(1));
////                            txtNomMar.setText(objVenConLine.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodMar.setText(strCodMar);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (objVenConLine.buscar("tx_deslar", txtNomMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConLine.getValueAt(1));
////                        txtNomMar.setText(objVenConLine.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConLine.setCampoBusqueda(2);
////                        objVenConLine.cargarDatos();
////                        objVenConLine.setVisible(true);
////                        if (objVenConLine.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConLine.getValueAt(1));
////                            txtNomMar.setText(objVenConLine.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomMar.setText(strNomMar);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////
////private boolean mostrarRefGen(int intTipBus)//chiqui
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    objVenConRefGen.setCampoBusqueda(2);
////                    objVenConRefGen.setVisible(true);
////                    if (objVenConRefGen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodMar.setText(objVenConRefGen.getValueAt(1));
////                        txtNomMar.setText(objVenConRefGen.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (objVenConRefGen.buscar("co_reg", txtCodMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConRefGen.getValueAt(1));
////                        txtNomMar.setText(objVenConRefGen.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConRefGen.setCampoBusqueda(0);
////                        objVenConRefGen.setCriterio1(11);
////                        objVenConRefGen.cargarDatos();
////                        objVenConRefGen.setVisible(true);
////                        if (objVenConRefGen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConRefGen.getValueAt(1));
////                            txtNomMar.setText(objVenConRefGen.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodMar.setText(strCodMar);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (objVenConRefGen.buscar("tx_deslar", txtNomMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConRefGen.getValueAt(1));
////                        txtNomMar.setText(objVenConRefGen.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConRefGen.setCampoBusqueda(2);
////                        objVenConRefGen.cargarDatos();
////                        objVenConRefGen.setVisible(true);
////                        if (objVenConRefGen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConRefGen.getValueAt(1));
////                            txtNomMar.setText(objVenConRefGen.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomMar.setText(strNomMar);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////
////private boolean mostrarRefDet(int intTipBus)//chiqui
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    objVenConRefDet.setCampoBusqueda(2);
////                    objVenConRefDet.setVisible(true);
////                    if (objVenConRefDet.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodMar.setText(objVenConRefDet.getValueAt(1));
////                        txtNomMar.setText(objVenConRefDet.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (objVenConRefDet.buscar("co_reg", txtCodMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConRefDet.getValueAt(1));
////                        txtNomMar.setText(objVenConRefDet.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConRefDet.setCampoBusqueda(0);
////                        objVenConRefDet.setCriterio1(11);
////                        objVenConRefDet.cargarDatos();
////                        objVenConRefDet.setVisible(true);
////                        if (objVenConRefDet.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConRefDet.getValueAt(1));
////                            txtNomMar.setText(objVenConRefDet.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodMar.setText(strCodMar);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (objVenConRefDet.buscar("tx_deslar", txtNomMar.getText()))
////                    {
////                        txtCodMar.setText(objVenConRefDet.getValueAt(1));
////                        txtNomMar.setText(objVenConRefDet.getValueAt(2));
////                    }
////                    else
////                    {
////                        objVenConRefDet.setCampoBusqueda(2);
////                        objVenConRefDet.cargarDatos();
////                        objVenConRefDet.setVisible(true);
////                        if (objVenConRefDet.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(objVenConRefDet.getValueAt(1));
////                            txtNomMar.setText(objVenConRefDet.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomMar.setText(strNomMar);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
    
////    //Combo de formulario  del filtro   
////    private boolean configurarComboFiltroTipVeh() {
////        boolean blnRes = true;
////        try {
////            //cboTipVeh.removeAllItems();
////            vecTipVeh = new Vector();
////            vecTipVeh.add("");
////            vecTipVeh.add("C");
////            vecTipVeh.add("M");
////            vecTipVeh.add("N");
////            vecTipVeh.add("O");
////            cboTipVeh.addItem("(Todos)");
////            cboTipVeh.addItem("C: Carro");
////            cboTipVeh.addItem("M: Moto");
////            cboTipVeh.addItem("N: Montacarga");
////            cboTipVeh.addItem("O: Otros");
////            cboTipVeh.setSelectedIndex(0);
////        } 
////        catch (Exception e) {
////            blnRes = false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.   EN LA TABLA
     */
////    private boolean cargarComboJtableTipoVeh(){
////        objTblCelEdiCmbBox.removeAllItems();
////        objTblCelEdiCmbBox.addItem(" ");
////        objTblCelEdiCmbBox.addItem("Carro");
////        objTblCelEdiCmbBox.addItem("Moto");
////        objTblCelEdiCmbBox.addItem("Montacarga");
////        objTblCelEdiCmbBox.addItem("Otros");
////        return true;
////    }
    
    /**
     * Configurar Marcas
     * @return 
     */
////    private boolean configurarMarcas()
////    {
////        boolean blnRes=true;
////        try
////        {
////            //Listado de campos.
////            ArrayList arlCam=new ArrayList();
////            arlCam.add("a1.co_mar");
////            arlCam.add("a1.tx_desLar");
////            //Alias de los campos.
////            ArrayList arlAli=new ArrayList();
////            arlAli.add("Código");
////            arlAli.add("Nombre");
////            //Ancho de las columnas.
////            ArrayList arlAncCol=new ArrayList();
////            arlAncCol.add("50");
////            arlAncCol.add("200");
////            //Armar la sentencia SQL.
////            strSQL="";
////            strSQL+=" SELECT a1.co_mar, a1.tx_deslar";
////            strSQL+=" FROM tbm_marVeh a1 WHERE a1.st_reg NOT IN('I','E')";
////            strSQL+=" ORDER BY a1.tx_deslar";
////            System.out.println("configurarMarcas: " + strSQL);
////            vcoMar=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Marcas", strSQL, arlCam, arlAli, arlAncCol);        
////            arlCam=null;
////            arlAli=null;
////            arlAncCol=null;
////            //Configurar columnas.
////            vcoMar.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
////    private boolean mostrarMarcas(int intTipBus)
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    vcoMar.setCampoBusqueda(2);
////                    vcoMar.setVisible(true);
////                    if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodMar.setText(vcoMar.getValueAt(1));
////                        txtNomMar.setText(vcoMar.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (vcoMar.buscar("a1.co_mar", txtCodMar.getText()))
////                    {
////                        txtCodMar.setText(vcoMar.getValueAt(1));
////                        txtNomMar.setText(vcoMar.getValueAt(2));
////                    }
////                    else
////                    {
////                        vcoMar.setCampoBusqueda(0);
////                        vcoMar.setCriterio1(11);
////                        vcoMar.cargarDatos();
////                        vcoMar.setVisible(true);
////                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(vcoMar.getValueAt(1));
////                            txtNomMar.setText(vcoMar.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodMar.setText(strCodMar);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (vcoMar.buscar("a1.tx_deslar", txtNomMar.getText()))
////                    {
////                        txtCodMar.setText(vcoMar.getValueAt(1));
////                        txtNomMar.setText(vcoMar.getValueAt(2));
////                    }
////                    else
////                    {
////                        vcoMar.setCampoBusqueda(2);
////                        vcoMar.setCriterio1(11);
////                        vcoMar.cargarDatos();
////                        vcoMar.setVisible(true);
////                        if (vcoMar.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodMar.setText(vcoMar.getValueAt(1));
////                            txtNomMar.setText(vcoMar.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomMar.setText(strNomMar);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////  
////    private boolean configurarChoferes(){
////        boolean blnRes=true;
////        try{
////            //Listado de campos.
////            ArrayList arlCam=new ArrayList();
////            arlCam.add("a1.co_tra");
////            arlCam.add("nombre");
////            //Alias de los campos.
////            ArrayList arlAli=new ArrayList();
////            arlAli.add("Código");
////            arlAli.add("Nombre");
////            //Ancho de las columnas.
////            ArrayList arlAncCol=new ArrayList();
////            arlAncCol.add("50");
////            arlAncCol.add("200");
////            //Armar la sentencia SQL.
////            strSQL="";
////            strSQL+=" select distinct a2.co_tra, (a2.tx_ape || ' ' ||a2.tx_nom) as nombre ";
////            strSQL+=" from tbm_traEmp as a1 INNER JOIN tbm_tra as a2 ON (a1.co_tra = a2.co_tra)";
////            strSQL+=" WHERE a1.co_dep in (26,27,28,29,30)";
////            strSQL+=" ORDER BY nombre";
////            System.out.println("configurarChoferes:.." + strSQL);
////            vcoCho=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Choferes", strSQL, arlCam, arlAli, arlAncCol);
////            arlCam=null;
////            arlAli=null;
////            arlAncCol=null;
////            //Configurar columnas.
////            vcoCho.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }

 
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
////    private boolean mostrarChoferes(int intTipBus)
////    {
////        boolean blnRes=true;
////        try
////        {
////            System.out.println("mostrarChoferes:.....");
////            switch (intTipBus)
////            {
////                case 0: //Mostrar la ventana de consulta.
////                    vcoCho.setCampoBusqueda(1);
////                    vcoCho.show();
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
    
    /**
     * Configurar Consulta de Combustible.
     * @return 
     */
////    private boolean configurarCombustible()
////    {
////        boolean blnRes=true;
////        try
////        {
////            //Listado de campos.
////            ArrayList arlCam=new ArrayList();
////            arlCam.add("a1.co_com");
////            arlCam.add("a1.tx_desLar");
////            //Alias de los campos.
////            ArrayList arlAli=new ArrayList();
////            arlAli.add("Código");
////            arlAli.add("Nombre");
////            //Ancho de las columnas.
////            ArrayList arlAncCol=new ArrayList();
////            arlAncCol.add("50");
////            arlAncCol.add("200");
////            //Armar la sentencia SQL.
////            strSQL="";
////            strSQL+=" SELECT a1.co_com, a1.tx_deslar";
////            strSQL+=" FROM tbm_comVeh a1 WHERE a1.st_reg ='A'";
////            strSQL+=" ORDER BY a1.tx_deslar";
////            System.out.println("configurarCombustible:.." + strSQL);
////            vcoCom=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Combustibles", strSQL, arlCam, arlAli, arlAncCol);        
////            arlCam=null;
////            arlAli=null;
////            arlAncCol=null;
////            //Configurar columnas.
////            vcoCom.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
////    private boolean mostrarCombustibles(int intTipBus)
////    {
////        boolean blnRes=true;
////        try
////        {
////            switch (intTipBus)
////            {
////               case 0: //Mostrar la ventana de consulta.
////                    vcoCom.setCampoBusqueda(2);
////                    vcoCom.setVisible(true);
////                    if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                    {
////                        txtCodCom.setText(vcoCom.getValueAt(1));
////                        txtNomCom.setText(vcoCom.getValueAt(2));
////                    }
////                    break;
////                case 1: //Búsqueda directa por "Código".
////                    if (vcoCom.buscar("a1.co_com", txtCodCom.getText()))
////                    {
////                        txtCodCom.setText(vcoCom.getValueAt(1));
////                        txtNomCom.setText(vcoCom.getValueAt(2));
////                    }
////                    else
////                    {
////                        vcoCom.setCampoBusqueda(0);
////                        vcoCom.setCriterio1(11);
////                        vcoCom.cargarDatos();
////                        vcoCom.setVisible(true);
////                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodCom.setText(vcoCom.getValueAt(1));
////                            txtNomCom.setText(vcoCom.getValueAt(2));
////                        }
////                        else
////                        {
////                            txtCodCom.setText(strCodCom);
////                        }
////                    }
////                    break;
////                case 2: //Búsqueda directa por "Nombre".
////                    if (vcoCom.buscar("a1.tx_deslar", txtNomCom.getText()))
////                    {
////                        txtCodCom.setText(vcoCom.getValueAt(1));
////                        txtNomCom.setText(vcoCom.getValueAt(2));
////                    }
////                    else
////                    {
////                        vcoCom.setCampoBusqueda(2);
////                        vcoCom.setCriterio1(11);
////                        vcoCom.cargarDatos();
////                        vcoCom.setVisible(true);
////                        if (vcoCom.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
////                        {
////                            txtCodCom.setText(vcoCom.getValueAt(1));
////                            txtNomCom.setText(vcoCom.getValueAt(3));
////                        }
////                        else
////                        {
////                            txtNomCom.setText(strNomCom);
////                        }
////                    }
////                    break;
////            }
////        }
////        catch (Exception e)
////        {
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }
////    

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
        panDat = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        cmbSeleccion = new javax.swing.JComboBox<>();
        lblTipDoc = new javax.swing.JLabel();
        panABC = new javax.swing.JPanel();
        spnLoc1 = new javax.swing.JScrollPane();
        tblABC = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        lblTit.setText("Actualizacion Masiva de items.....");
        lblTit.setToolTipText("");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panFil.setName(""); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(610, 180));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 20, 400, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(20, 0, 400, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Tipos"));
        panLoc.setAutoscrolls(true);
        panLoc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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

        panFil.add(panLoc);
        panLoc.setBounds(30, 90, 280, 190);

        cmbSeleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Atributos", "ABC" }));
        cmbSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSeleccionActionPerformed(evt);
            }
        });
        panFil.add(cmbSeleccion);
        cmbSeleccion.setBounds(560, 10, 110, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(450, 10, 100, 20);

        panABC.setBorder(javax.swing.BorderFactory.createTitledBorder("Items ABC"));
        panABC.setAutoscrolls(true);
        panABC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panABC.setLayout(new java.awt.BorderLayout());

        tblABC.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc1.setViewportView(tblABC);

        panABC.add(spnLoc1, java.awt.BorderLayout.CENTER);

        panFil.add(panABC);
        panABC.setBounds(30, 90, 280, 190);
        panABC.getAccessibleContext().setAccessibleName("Items ABC");

        panDat.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

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

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panRpt.add(panDet, java.awt.BorderLayout.CENTER);

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
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
    String strTit, strMsg;
    try
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            if(con!=null){
                con.close();
                con=null;
            }
            dispose();
        }
    }
    catch (java.sql.SQLException e)
    {
        dispose();
    }        
}//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        //objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
                objThrGUI=new ZafMae84.ZafThreadGUI();
                objThrGUI.start();    
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
      
    blnCon=true;
    if (objThrGuaGUI==null)
    {
        objThrGuaGUI=new ZafThGuarGUI();
        objThrGuaGUI.start();
    }
        
        
        


    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            objVenConItm01GP.limpiar();
////            objVenConItm01GP.activa_desac(false);

////            chkSolStk.setSelected(false);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optTodStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
         if (optTod.isSelected()){
             optFil.setSelected(false);
         }
    // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
         if (optFil.isSelected()){
////             objVenConItm01GP.activa_desac(true);             
             optTod.setSelected(false);
         }        
    }//GEN-LAST:event_optFilActionPerformed

    private void cmbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSeleccionActionPerformed
       if (cmbSeleccion.getSelectedIndex()==1)
       {
           panABC.setVisible(true);
           panLoc.setVisible(false);
           valTip=1;
           configurarTblDatABC();
           configurarTblABC();
           cargarABC();           
       }
       else
       {
           panABC.setVisible(false);
           panLoc.setVisible(true);    
           configurarTblDat();
           configurarTblTipos();
           cargarTipos();           
           valTip=0;
       }
           
       
    }//GEN-LAST:event_cmbSeleccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JComboBox<String> cmbSeleccion;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panABC;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnLoc1;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblABC;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
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
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (valTip==0)
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
                     objThrGUI=null;
                }                
            }
            else
            {
                if (!cargarDetRegABC())
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
                     objThrGUI=null;
                }                
                
            }

            
        }
    }
    
private boolean ocultaColumnasPorFiltro()//chiqui
   {
       boolean blnRes=false;
       
                //Obtener los locales a consultar.
                int intNumFilTblBod=objTblModTipo.getRowCountTrue();
                int i=0;
                String tx_nombre="";
                boolean blnExisteMarca=false;
                boolean blnExisteMaterial=false;
                boolean blnExisteLinea=false;
                boolean blnExisteClase1=false;
                boolean blnExisteTamano=false;
                boolean blnExistePeso=false;
                boolean blnExisteCompras=false;
                boolean blnExisteCate=false;
                strAux="";
                for (int j=0; j<intNumFilTblBod; j++){
////                    if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){
                        tx_nombre = objTblModTipo.getValueAt(j, INT_TBL_DAT_NOMBRE).toString();
                        if (tx_nombre.equals("Marca"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_MARC, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblDat); 
                                blnMarca =true;
                            }
                            else
                            {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MARC, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblDat);
                                blnMarca=false;
                            }
                           blnExisteMarca=true;
                        }
////                        else
////                        {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblDat);
////                                blnMarca=false;                            
////                        }
                        
                        if (tx_nombre.equals("Material"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_MAT, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblDat); 
                                blnMaterial =true;
                            }
                            else
                            {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAT, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblDat);   
                                blnMaterial =false;
                            }
                            blnExisteMaterial=true;
                        }
////                        else
////                        {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblDat);   
////                                blnMaterial =false;                            
////                        }
                       
////                        if (tx_nombre.equals("Referencia General"))
////                        {
////                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){                            
////////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_REF_GE, tblDat);
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_REF_GE, tblDat);                            
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_REF_GE, tblDat);  
////                             }
////                            else
////                            {
////////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REF_GE, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_REF_GE, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_REF_GE, tblDat);
////                            }
////                         }   
                          
////                        if (tx_nombre.equals("Referencia Detallada"))
////                        {
////                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){                                      
////////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_REF_DET, tblDat);
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_REF_DET, tblDat);                            
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_REF_DET, tblDat); 
////                            }
////                            else
////                            {       
////////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REF_DET, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_REF_DET, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_REF_DET, tblDat);
////                            }                                                  
////                        }                     
                                                   
                        if (tx_nombre.equals("Linea"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){ 
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblDat);   
                                blnLinea =true;
                            }
                            else
                            {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblDat);
                                blnLinea =false;
                            }    
                            blnExisteLinea=true;
                        }
////                        else
////                        {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblDat);
////                                blnLinea =false;                            
////                        }
                                              
                        if (tx_nombre.equals("Clase"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){ 
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_CLASE, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblDat); 
                                blnClase1 =true;
                            }
                            else
                            { 
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLASE, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblDat);
                                blnClase1 =false;
                            }    
                            blnExisteClase1=true;
                        }
                        
                        if (tx_nombre.equals("Compras"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){ 
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_CLASE, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_COMP, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_COMP, tblDat); 
                                blnCompra =true;
                            }
                            else
                            { 
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLASE, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_COMP, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_COMP, tblDat);
                                blnCompra =false;
                            }    
                            blnExisteCompras=true;
                        }                        
////                        else
////                        {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblDat);
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblDat);
////                                blnClase1 =false;                            
////                        }
                             
                        if (tx_nombre.equals("Tamaño"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION))
                            {
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_TAMA, tblDat);
                                blnTamano =true;
                            }
                                
                            else
                            {
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_TAMA, tblDat);
                                blnTamano =false;
                            }
                            blnExisteTamano=true;
                        }
                        
                        if (tx_nombre.equals("Peso"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION))
                            {
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_PESO, tblDat);
                                blnPeso =true;
                            }
                                
                            else
                            {
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_PESO, tblDat);
                                blnPeso =false;
                            }
                            blnExistePeso=true;
                        }
                        if (tx_nombre.equals("Categoria"))
                        {
                            if (objTblModTipo.isChecked(j, INT_TBL_DAT_CHK_SELECCION)){ 
////                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_COM_CATE, tblDat);                            
                                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_NOM_CATE, tblDat);   
                                blnCategoria =true;
                            }
                            else
                            {
////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_CATE, tblDat);
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CATE, tblDat);
                                blnCategoria =false;
                            }    
                            blnExisteCate=true;
                        }
                        
//////                        else
//////                        {
//////                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_TAMA, tblDat);
//////                                blnTamano =false;                            
//////                        }
                    }
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MARC, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAT, tblDat);
    ////                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REF_GE, tblDat);
    ////                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REF_DET, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLASE1, tblDat);
                    //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LINEA, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_COMPR, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CATE, tblDat);
                    if (!blnExisteMarca)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MARC, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MARC, tblDat);
                        blnMarca=false;                        
                    }
                    if (!blnExisteMaterial)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_MAT, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_MAT, tblDat);   
                        blnMaterial =false;                       
                    }    
                    if (!blnExisteLinea)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_LINEA, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_LINEA, tblDat);
                        blnLinea =false;                     
                    }   
                    if (!blnExisteClase1)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CLASE1, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CLASE1, tblDat);
                        blnClase1 =false;                    
                    }   
                    if (!blnExisteCompras)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_COMP, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_COMP, tblDat);
                        blnCategoria =false;                    
                    }   
                    if (!blnExisteCate)
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_COM_CATE, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_CATE, tblDat);
                        blnCategoria =false;                    
                    }                     
                    
                
   
       
       return blnRes;
   }

    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()//chiqui
    {
        boolean blnRes=true;
        
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                stm = con.createStatement();      
                
                strAux="";
                if ((objVenConItm01GP.isCondicionAplicada()) &&(!optTod.isSelected()))
                {
                    strAux=objVenConItm01GP.getCondicionesSQL();
                }
                else
                    strAux  ="";
                strSQL="  SELECT trim(a1.ITEMNMBR) as tx_coItm,trim(a1.ITMGEDSC) as tx_tresL, trim(a1.ITMSHNAM) as tx_codZAF, trim(a1.ITEMDESC) as tx_nomItm,trim(ISNULL(a17.MSW_ADI_IV_181_Tamano, '')) as tamano \n";
                strSQL+=",CASE WHEN a8.MSW_ADI_IV_119_Marca ='' THEN 'NO TIENE' ELSE trim(a8.MSW_ADI_IV_119_Marca) end as marca \n";
                strSQL+=",CASE WHEN a9.MSW_ADI_IV_120_Material ='' THEN 'NO TIENE' ELSE trim(a9.MSW_ADI_IV_120_Material) end as material \n";
                strSQL+=",CASE WHEN a10.MSW_ADI_IV_117_Referencia_General ='' THEN 'NO TIENE' ELSE trim(a10.MSW_ADI_IV_117_Referencia_General) end as Referencia_General \n";
                strSQL+=",CASE WHEN a11.MSW_ADI_IV_118_Referencia_Detallada ='' THEN 'NO TIENE' ELSE trim(a11.MSW_ADI_IV_118_Referencia_Detallada) end as Referencia_Detallada \n";
                strSQL+=",CASE WHEN a2.MSW_ADI_IV_196_Linea ='' THEN 'NO TIENE' ELSE trim(a2.MSW_ADI_IV_196_Linea) end as linea \n";
                strSQL+=",CASE WHEN a3.MSW_ADI_IV_197_Clase ='' THEN 'NO TIENE' ELSE trim(a3.MSW_ADI_IV_197_Clase) end as clase  \n";
                strSQL+=",CASE WHEN a30.TUV_199_Compras ='' THEN 'NO TIENE' ELSE trim(a30.TUV_199_Compras) end as compras  \n";        
                strSQL+=",CASE WHEN a32.TUV_204_Categoria ='' THEN 'NO TIENE' ELSE trim(a32.TUV_204_Categoria) end as categoria  \n";   
                    strSQL+=",CASE WHEN extCatCom.det ='' THEN 'NO_TIENE' ELSE trim(extCatCom.det) end as CategoriaComp \n";                     
                    strSQL+=",CASE WHEN extClaCom.det ='' THEN 'NO_TIENE' ELSE trim(extClaCom.det) end as ClaseComp \n";
                    strSQL+=",CASE WHEN extLinCom.det ='' THEN 'NO_TIENE' ELSE trim(extLinCom.det) end as LineaComp \n";                
                strSQL+=", ISNULL(a13.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MARCA' AND UD_Form_Field_ID ='NO TIENE')) as IDMarca\n";
                strSQL+=", ISNULL(a14.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_MATERIAL' AND UD_Form_Field_ID ='NO TIENE')) as IDMaterial      \n";
                strSQL+=", ISNULL(a15.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_GENERAL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefGe\n";
                strSQL+=", ISNULL(a16.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'MSW_REF_DETALL' AND UD_Form_Field_ID ='NO TIENE')) as IDRefDe\n";
                strSQL+=", ISNULL(a20.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_LINEAS' AND UD_Form_Field_ID ='NO TIENE')) as IDLinea\n";
                strSQL+=", ISNULL(a21.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CLASE_SUB' AND UD_Form_Field_ID ='NO TIENE')) as IDClase\n";
                strSQL+=", ISNULL(a30.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_COMPRAS' AND UD_Form_Field_ID ='NO TIENE')) as IDCompras\n";
                strSQL+=", ISNULL(A32.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CAT_COM' AND UD_Form_Field_ID ='NO TIENE')) as IDCategoria\n";
                
                strSQL+=", ISNULL(extcatcom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CAT_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDCategoriaCompra\n";
                strSQL+=", ISNULL(extclacom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_CLA_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDClaseCompra\n"; 
                strSQL+=", ISNULL(extlincom2.Extender_Record_ID,(SELECT Extender_Record_ID FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 WHERE Extender_Form_ID = 'TUV_LIN_COM' AND UD_Form_Field_ID ='NO_TIENE')) as IDLineaCompra\n";                 
                
                strSQL+=", ISNULL(a23.Peso_KG, 0) as peso   \n";  

                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 AS a1 \n";

                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e199.Extender_Record_ID, STRGA255 as TUV_199_Compras,e100.Extender_Key_Values_1  \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e199 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )  \n";
                strSQL+="            WHERE e199.Field_ID =199 ) as a30 on (a1.ITEMNMBR =a30.Extender_Key_Values_1)--Compras \n";          

                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e204.Extender_Record_ID, STRGA255 as TUV_204_Categoria,e100.Extender_Key_Values_1  \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e204 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e204.Extender_Record_ID = e100.Extender_Record_ID )  \n";
                strSQL+="            WHERE e204.Field_ID =204 ) as a32 on (a1.ITEMNMBR =a32.Extender_Key_Values_1)--Categoria \n";        


                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_196_Linea,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =196 ) as a2 on (a1.ITEMNMBR =a2.Extender_Key_Values_1)--Linea \n";         

                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_197_Clase,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =197 ) as a3 on (a1.ITEMNMBR =a3.Extender_Key_Values_1)--Clase \n";         

                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_119_Marca,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =119 ) as a8 on (a1.ITEMNMBR =a8.Extender_Key_Values_1)--marca \n";
                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_120_Material,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =120 ) as a9 on (a1.ITEMNMBR =a9.Extender_Key_Values_1)--material \n";
                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_117_Referencia_General,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =117 ) as a10 on (a1.ITEMNMBR =a10.Extender_Key_Values_1)--Referencia General \n";        
                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_118_Referencia_Detallada,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =118 ) as a11 on (a1.ITEMNMBR =a11.Extender_Key_Values_1)--Referencia Detallado \n";
                strSQL+="       left outer join ( \n";
                strSQL+="            SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_181_Tamano,e100.Extender_Key_Values_1 \n";
                strSQL+="            FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  AS e101 \n";
                strSQL+="                 left join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
                strSQL+="            WHERE e101.Field_ID =181 ) as a17 on (a1.ITEMNMBR =a17.Extender_Key_Values_1)--Tamano \n"; 
                
                    strSQL+="       left outer join ( \n";
                    strSQL+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
                    strSQL+="                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
                    strSQL+="                               LEFT JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
                    strSQL+="                               INNER JOIN ( \n";          
                    strSQL+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
                    strSQL+="                                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
                    strSQL+="                                               INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
                    strSQL+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
                    strSQL+="                          WHERE e199.Field_ID = 206 \n";         
                    strSQL+="                       ) AS extCatCom    ON (a1.ITEMNMBR = extCatCom.itm) --CATEGORIACOMPRA \n";     

                    strSQL+="       left outer join ( \n";
                    strSQL+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
                    strSQL+="                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
                    strSQL+="                               LEFT JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
                    strSQL+="                               INNER JOIN ( \n";          
                    strSQL+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
                    strSQL+="                                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
                    strSQL+="                                               INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
                    strSQL+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
                    strSQL+="                          WHERE e199.Field_ID = 208 \n";         
                    strSQL+="                       ) AS extClaCom    ON (a1.ITEMNMBR = extClaCom.itm) --CLASECOMPRA \n";  

                    strSQL+="       left outer join ( \n";
                    strSQL+="                           SELECT ext.cat,e100.Extender_Record_ID as idItem, STRGA255 as descCate,trim(e100.Extender_Key_Values_1  ) as itm, e199.dex_row_id as rowidCompra, e100.dex_row_id as rowidItem,ext.det \n";
                    strSQL+="                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e199 \n";
                    strSQL+="                               LEFT JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e199.Extender_Record_ID = e100.Extender_Record_ID )   \n";                
                    strSQL+="                               INNER JOIN ( \n";          
                    strSQL+="                                           SELECT   e12.Extender_Form_ID as cat,e12.UD_Form_Field_Desc as det,UD_Form_Field_ID as cab,e20.Field_ID as idcat  \n";        
                    strSQL+="                                           FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as e12  \n";        
                    strSQL+="                                               INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT20010 as e20 on (e12.Extender_Form_ID = e20.FIELDNAM) \n";        
                    strSQL+="                                          )as ext on (e199.STRGA255 =ext.cab and e199.Field_ID = ext.idcat ) \n";                           
                    strSQL+="                          WHERE e199.Field_ID = 207 \n";         
                    strSQL+="                       ) AS extLinCom    ON (a1.ITEMNMBR = extLinCom.itm) --LINEACOMPRA \n"; 
                    
                    
                
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a13 on (a8.MSW_ADI_IV_119_Marca =a13.UD_Form_Field_ID and a13.Extender_Form_ID = 'MSW_MARCA') \n";        
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as a23 on (a1.ITEMNMBR =a23.ITEMNMBR) \n";
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a14 on (a9.MSW_ADI_IV_120_Material =a14.UD_Form_Field_ID and a14.Extender_Form_ID = 'MSW_MATERIAL') \n";
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a15 on (a10.MSW_ADI_IV_117_Referencia_General =a15.UD_Form_Field_ID and a15.Extender_Form_ID = 'MSW_REF_GENERAL')  \n";
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a16 on (a11.MSW_ADI_IV_118_Referencia_Detallada =a16.UD_Form_Field_ID and a16.Extender_Form_ID = 'MSW_REF_DETALL')  \n";
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a20 on (a2.MSW_ADI_IV_196_Linea =a20.UD_Form_Field_ID and a20.Extender_Form_ID = 'TUV_LINEAS')  \n"; 
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a21 on (a3.MSW_ADI_IV_197_Clase =a21.UD_Form_Field_ID and a21.Extender_Form_ID = 'TUV_CLASE_SUB')  \n"; 
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a31 on (a30.TUV_199_Compras =a31.UD_Form_Field_ID and a31.Extender_Form_ID = 'TUV_COMPRAS')   \n";         
                strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as a33 on (a32.TUV_204_Categoria =a33.UD_Form_Field_ID and a33.Extender_Form_ID = 'TUV_CAT_COM')   \n";                 
                    strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as extcatcom2 on (extCatCom.det =extcatcom2.UD_Form_Field_Desc and extcatcom2.Extender_Form_ID = 'TUV_CAT_COM')  \n";                       
                    strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as extclacom2 on (extClaCom.det =extclacom2.UD_Form_Field_Desc and extclacom2.Extender_Form_ID = 'TUV_CLA_COM')  \n";                                           
                    strSQL+="       left outer join "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 as extlincom2 on (extLinCom.det =extlincom2.UD_Form_Field_Desc and extlincom2.Extender_Form_ID = 'TUV_LIN_COM')  \n";                                       
                if (strAux.equals(""))
                    strSQL+=" WHERE    a1.ITEMTYPE =1 and a1.INACTIVE =0 and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   ";
                else
                    strSQL+=" WHERE    a1.ITEMTYPE =1  and a1.INACTIVE =0 and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   "+ strAux  +"";
                strSQL+=" ORDER BY   a1.DEX_ROW_ID asc" ;  
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM, rst.getString("tx_coItm")==null?"":rst.getString("tx_coItm"));
                        vecReg.add(INT_TBL_DAT_COD_3L, rst.getString("tx_tresL")==null?"":rst.getString("tx_tresL"));
                        vecReg.add(INT_TBL_DAT_COD_ZAF, rst.getString("tx_codZAF")==null?"":rst.getString("tx_codZAF"));
                        vecReg.add(INT_TBL_DAT_NOM_ITEM, rst.getString("tx_nomItm")==null?"":rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_NOM_TAMA, rst.getString("tamano")==null?"":rst.getString("tamano"));
                        vecReg.add(INT_TBL_DAT_NOM_PESO, rst.getString("peso")==null?"":rst.getString("peso"));
                        vecReg.add(INT_TBL_DAT_COD_MARC, rst.getString("IDmarca")==null?"":rst.getString("IDmarca"));
                        vecReg.add(INT_TBL_DAT_NOM_MARC, rst.getString("marca")==null?"NO TIENE":rst.getString("marca")); 
                        vecReg.add(INT_TBL_DAT_BUT_COM_MARC, "");                        
                        vecReg.add(INT_TBL_DAT_COD_LINEA, rst.getString("IDLinea")==null?"":rst.getString("IDLineaCompra"));
                        vecReg.add(INT_TBL_DAT_NOM_LINEA, rst.getString("linea")==null?"NO TIENE":rst.getString("LineaComp"));  
                        vecReg.add(INT_TBL_DAT_BUT_COM_LINEA,  "");                        
                        vecReg.add(INT_TBL_DAT_COD_CLASE1, rst.getString("IDClase")==null?"":rst.getString("IDClaseCompra"));
                        vecReg.add(INT_TBL_DAT_NOM_CLASE1, rst.getString("clase")==null?"NO TIENE":rst.getString("ClaseComp"));
                        vecReg.add(INT_TBL_DAT_BUT_CLASE1, "");                        
                        vecReg.add(INT_TBL_DAT_COD_MAT, rst.getString("IDMaterial")==null?"":rst.getString("IDMaterial"));
                        vecReg.add(INT_TBL_DAT_NOM_MAT, rst.getString("material")==null?"NO TIENE":rst.getString("material"));                        
                        vecReg.add(INT_TBL_DAT_BUT_COM_MAT, "");       

                        vecReg.add(INT_TBL_DAT_COD_COMPR, rst.getString("IDCompras")==null?"":rst.getString("IDCompras"));
                        vecReg.add(INT_TBL_DAT_NOM_COMP, rst.getString("compras")==null?"NO TIENE":rst.getString("compras"));                        
                        vecReg.add(INT_TBL_DAT_BUT_COM_COMP, "");   
                        vecReg.add(INT_TBL_DAT_COD_CATE, rst.getString("IDCategoria")==null?"":rst.getString("IDCategoriaCompra"));
                        vecReg.add(INT_TBL_DAT_NOM_CATE, rst.getString("categoria")==null?"NO TIENE":rst.getString("CategoriaComp"));                           
                        vecReg.add(INT_TBL_DAT_BUT_COM_CATE, "");   
                        vecDat.add(vecReg);    
                    }
                    else
                        break;
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
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                ocultaColumnasPorFiltro();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean cargarDetRegABC()//chiqui
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
////            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con != null) 
            {
                
                
                stm = con.createStatement();      
                
                strAux="";
                if ((objVenConItm01GP.isCondicionAplicada()) &&(!optTod.isSelected()))
                {
                    strAux=objVenConItm01GP.getCondicionesSQL();
                }
                else
                    strAux  ="";
                strSQL="  SELECT trim(a1.ITEMNMBR) as item,trim(a1.ITMSHNAM) as codZaf,trim(a1.ITEMDESC) as descr,trim(a1.ITMGEDSC) as tresl, \n";
                strSQL+="        vis.Frecuencia  as desFre, nivf.co_nivpar as codFre,vis.Peso as desPes,nivP.co_nivpar as codPes,vis.[Valor venta] as desVal,nivV.co_nivpar as codVal,vis.Tuval as desTuv, \n";                
                strSQL+="        nivT.co_nivpar as codTuv,vis.Clase as desCla, nivC.co_nivpar as codCla \n";                
                strSQL+=" FROM Dynamics2021..vw_itemsABC_Columnas AS vis \n";
                strSQL+="       INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..IV00101  as a1 on (vis.tx_itm= a1.ITEMNMBR) \n";                
                strSQL+="       LEFT JOIN Dynamics2021..tbm_nivpar as nivF on (vis.Frecuencia = nivF.tx_nivpar) \n";
                strSQL+="       LEFT JOIN Dynamics2021..tbm_nivpar as nivP on (vis.Peso = nivP.tx_nivpar) \n";
                strSQL+="       LEFT JOIN Dynamics2021..tbm_nivpar as nivV on (vis.[Valor venta] = nivV.tx_nivpar) \n";
                strSQL+="       LEFT JOIN Dynamics2021..tbm_nivpar as nivT on (vis.Tuval = nivT.tx_nivpar) \n";
                strSQL+="       LEFT JOIN Dynamics2021..tbm_nivpar as nivC on (vis.Clase = nivC.tx_nivpar) \n";
                if (strAux.equals(""))
                    strSQL+=" WHERE    a1.ITEMTYPE =1 and a1.INACTIVE =0 and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   ";
                    //strSQL+=" WHERE    a1.ITEMTYPE =1  and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   ";
                else
                    strSQL+=" WHERE    a1.ITEMTYPE =1  and a1.INACTIVE =0 and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   "+ strAux  +"";
                    //strSQL+=" WHERE    a1.ITEMTYPE =1  and (a1.ITEMNMBR like '%-I' OR A1.ITEMNMBR LIKE '%-S%')   "+ strAux  +"";
                strSQL+=" ORDER BY   a1.DEX_ROW_ID asc" ;  
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDatABC.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecRegABC=new Vector();
                        vecRegABC.add(INT_TBL_DAT_ABC_LIN,"");
    
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_ITM, rst.getString("item")==null?"":rst.getString("item"));
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_3L, rst.getString("tresl")==null?"":rst.getString("tresl"));
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_ZAF, rst.getString("codZaf")==null?"":rst.getString("codZaf"));
                        vecRegABC.add(INT_TBL_DAT_ABC_NOM_ITEM, rst.getString("descr")==null?"":rst.getString("descr"));
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_FRE, rst.getString("codFre")==null?"":rst.getString("codFre"));
                        vecRegABC.add(INT_TBL_DAT_ABC_DES_FRE, rst.getString("desFre")==null?"NO TIENE":rst.getString("desFre")); 
                        vecRegABC.add(INT_TBL_DAT_ABC_BUT_FRE, "");                        
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_PES, rst.getString("codPes")==null?"":rst.getString("codPes"));
                        vecRegABC.add(INT_TBL_DAT_ABC_DES_PES, rst.getString("desPes")==null?"NO TIENE":rst.getString("desPes"));  
                        vecRegABC.add(INT_TBL_DAT_ABC_BUT_PES,  "");                        
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_VAL, rst.getString("codVal")==null?"":rst.getString("codVal"));
                        vecRegABC.add(INT_TBL_DAT_ABC_DES_VAL, rst.getString("desVal")==null?"NO TIENE":rst.getString("desVal"));
                        vecRegABC.add(INT_TBL_DAT_ABC_BUT_VAL, "");                        
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_TUV, rst.getString("codTuv")==null?"":rst.getString("codTuv"));
                        vecRegABC.add(INT_TBL_DAT_ABC_DES_TUV, rst.getString("desTuv")==null?"NO TIENE":rst.getString("desTuv"));                        
                        vecRegABC.add(INT_TBL_DAT_ABC_BUT_TUV, "");  
                        
                        vecRegABC.add(INT_TBL_DAT_ABC_COD_CLA, rst.getString("codCla")==null?"":rst.getString("codCla"));
                        vecRegABC.add(INT_TBL_DAT_ABC_DES_CLA, rst.getString("desCla")==null?"NO TIENE":rst.getString("desCla"));                        
                        vecRegABC.add(INT_TBL_DAT_ABC_BUT_CLA, "");                          

 
                        vecDatABC.add(vecRegABC);    
                    }
                    else
                        break;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                  
                //Asignar vectores al modelo.
                objTblMod.setData(vecDatABC);
                tblDat.setModel(objTblMod);
                vecDatABC.clear();
              
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                //ocultaColumnasPorFiltro();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
 private int getObtenerUltimoIdPesoGP(java.sql.Connection conExt)//CHIQUI -PARA OBTENER EL ULTIMPO ID DEL PESO EN EXT01100
 {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        int ultimoId=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT TOP 1  Extender_Record_ID  ";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 ";
                strSQL+=" ORDER BY Extender_Record_ID DESC";
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    ultimoId=rstLoc.getInt("Extender_Record_ID");
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
            ultimoId=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            ultimoId=0;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return ultimoId;
    }
    
    
    
////private boolean ConsultaInsersionEXT01100(java.sql.Connection conEXT,String fecha,String codigoGP)//CHIQUI -PARA LA INSERCION DE PESO DEL ITEM
////{
////////        java.sql.Connection conLoc;
////        java.sql.ResultSet rstLoc;
////        java.sql.Statement stmLoc;
////        boolean blnRes=false;
////        String values00="",cabecera00="",strSQL="",values03="",cabecera03="",strSQLGlo="";
////        int ultimoId=0;
////        
////        try
////        {
////            //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
////            if(conEXT!=null)
////            {
////                stmLoc=conEXT.createStatement();
////                ultimoId=getObtenerUltimoIdPesoGP(conEXT) +1 ;
////                if (ultimoId !=0)
////                {
////                    strSQL="";
////                    values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ codigoGP +"',0,'"+ fecha +"','"+ objParSis.getNombreUsuario()  +"','"+ fecha +"','"+ objParSis.getNombreUsuario()  +"')";
////                    cabecera00 ="(Extender_Record_ID, Extender_Window_ID, Extender_Key_Values_1, NOTEINDX,MODIFDT,MDFUSRID,CREATDDT,CRUSRID)";
////                    strSQL=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100  "+ cabecera00 +" VALUES "+ values00 +"\n";
////                    
////                    System.out.println("INSERT EXT01100" + strSQL);                     
////                    blnRes=true;
////                }
////                else
////                    blnRes=false;
////                if (blnRes ==true)
////                     stmLoc.executeUpdate(strSQL);
////               
////                stmLoc.close();
////                stmLoc=null;                    
////////                }
////            }            
////        }    
////        catch(java.sql.SQLException e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        catch(Exception e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        return blnRes;        
////}
    
 
 private int getObtenerIdPesoGP(String item,java.sql.Connection conExt)//chiqui
    {
        java.sql.Statement stmLoc;
////        java.sql.Connection conGP;
        java.sql.ResultSet rstLoc;  
        String strSQLOP="";
        int blnRes=0;
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQLOP="";
                strSQLOP+="SELECT Extender_Record_ID ";
                strSQLOP+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 ";
                strSQLOP+=" WHERE Extender_Key_Values_1 ="+ item +"";
                strSQLOP+=" AND Extender_Window_ID = 'MSW_ADI_IV'";
                rstLoc=stmLoc.executeQuery(strSQLOP);
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
        String strSQLOP="";
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
                strSQLOP="";
                strSQLOP+="{call SP_INSER_MODI_PESO_1(?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSQLOP);
                cal = conExt.prepareCall(strSQLOP);
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
    
    private String obtenerCodExtender(java.sql.Connection conExt,String desext,int indicador)
    {
        String tx_desext="";

        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;  
        String strSQL="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT trim(UD_Form_Field_ID) as codExt  ";
                strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01200 ";
                if(indicador==8)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_CAT_COM' and UD_Form_Field_Desc = '"+ desext +"'";
                if(indicador==9)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_CLA_COM' and UD_Form_Field_Desc = '"+ desext +"'";          
                if(indicador==10)
                    strSQL+=" WHERE Extender_Form_ID ='TUV_LIN_COM' and UD_Form_Field_Desc = '"+ desext +"'";                 
                rstLoc=stmLoc.executeQuery(strSQL);
                while (rstLoc.next())
                {
                    tx_desext=rstLoc.getString("codExt");
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
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return tx_desext;
    }    
     /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, i, intMaxHis=-1,idExt=0;
        boolean blnRes=true;
        int idItemPeso=0;
        java.sql.ResultSet rstLoc;
        String strFecUltMod="", strSQLZAF ="";
        cadenaItems = new StringBuilder();
        String codExtenderCatCom="",codExtenderClaCom="",codExtenderLinCom="";
        String items ="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            conZAF =DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                stmZAF=conZAF.createStatement();
                con.setAutoCommit(false);
                conZAF.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                datFecAuxZAF=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());                   
                intNumFil=objTblMod.getRowCountTrue();
////                strSQL="";
                for (i=0; i<intNumFil;i++){  
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {

                        strSQL="";
                         if ((!objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM).equals("")) && (!objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF).equals("")))
                         {
                             strSQL +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set ITEMDESC = "+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM))  +" ,MODIFDT = cast ( '"+strFecUltMod+"' as date) ,ITMSHNAM = "+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF)) +" \n";
                             strSQL+= "       ,DEX_ROW_TS ='"+strFecUltMod +"' \n";
                             strSQL+= "WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";
                             
                             strSQL +=" UPDATE Dynamics2021..tbm_inv set tx_nomitm = "+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM))  +" , tx_codalt = "+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF)) +" \n";
                             strSQL+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' , nd_pes = "+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_PESO)  +" \n";
                             
                             strSQL+= "       ,tx_atrmar ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MARC)+"', tx_atrmat ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MAT) +"' , tx_atrlincom = '"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_LINEA)  +"' \n";
                             //strSQL+= "       ,tx_com ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_COMP)+"', tx_con ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CON) +"' , tx_cat = "+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CATE)  +" \n";
                             strSQL+= "       ,tx_atrcom ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_COMP)+"', tx_atrCatCom = '"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CATE)  +"', tx_atrclacom = '"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CLASE1)  +"' \n";                             

                             
                             strSQL+= "WHERE tx_itm ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      
                             
////////////                             strSQLZAF += " UPDATE tmb_inv set tx_nomitm ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM))  +" , tx_codalt ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF))+" \n";
////////////                             strSQLZAF += "    ,fe_ultmod = '"+datFecAuxZAF +"' , co_usrmod ="+ objParSis.getCodigoUsuario() +"";
////////////                             strSQLZAF += " WHERE tx_codalt2 ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_3L))+"";
                         }
                         
                        
                        
                        if(!objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MARC).equals("") && !objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MAT).equals("") && !objTblMod.getValueAt(i,INT_TBL_DAT_NOM_LINEA).equals("") && 
                           !objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CLASE1).equals("") && !objTblMod.getValueAt(i,INT_TBL_DAT_NOM_COMP).equals("")  && !objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CATE).equals(""))
                        {

                            for (int j=0; j<=8;j++)
                            {
                                codExtenderCatCom =obtenerCodExtender(con,objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CATE).toString(),8);                
                                codExtenderClaCom =obtenerCodExtender(con,objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CLASE1).toString(),9); 
                                codExtenderLinCom =obtenerCodExtender(con,objTblMod.getValueAt(i,INT_TBL_DAT_NOM_LINEA).toString(),10);   
                                
                                idExt =consultaEXT01101_EXT01100(con,j,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString());
                                if(idExt!=-1)
                                {

                                    if ((j==2) && (blnMarca))//marca
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MARC) +"' WHERE DEX_ROW_ID ="+ idExt +" ;";
                                    if ((j==3) && (blnMaterial))//material
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_MAT) +"' WHERE DEX_ROW_ID ="+ idExt +" ;";                
                                    if ((j==4) && (blnTamano))//tamño
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_TAMA) +"' WHERE DEX_ROW_ID ="+ idExt +" ;";    
                                    if ((j==5) && (blnLinea))//linea
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderLinCom +"' WHERE DEX_ROW_ID ="+ idExt +" ;";                
                                    if ((j==6) && (blnClase1))//clase
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderClaCom +"' WHERE DEX_ROW_ID ="+ idExt +" ;";    
                                    if ((j==7) && (blnCompra))//compras
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_NOM_COMP) +"' WHERE DEX_ROW_ID ="+ idExt +" ;";                                    
                                    if ((j==8) && (blnCategoria))//categoria
                                        strSQL+= " \n UPDATE   "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 SET STRGA255 ='"+ codExtenderCatCom +"' WHERE DEX_ROW_ID ="+ idExt +" ;";                                                                        

                                    blnRes=true;
                                }
                                else
                                {
                                    blnRes=false;
////                                    break;
                                }                                
                            }
                            if(blnPeso)
                            {
                                idItemPeso =getObtenerIdPesoGP(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM),1),con);
                                if(insertarModificarPesoGP(con,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString(),idItemPeso,  objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_PESO)))==true)
                                    blnRes=true;
                                else
                                {
                                    blnRes=false;
                                    break;                                    
                                }   
                            
                            }
                                
                                
                            
                        }   
                        else
                        {
                            
                            blnRes=false;
                            break; 
                        }
////                       

    ////                    else
    ////                     mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                        if (blnRes)
                        {
                            stm.executeUpdate(strSQL);
                            items= "'"+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString() +"'";
                            cadenaItems.append(items).append(","); 
                          
                        }
                            
                        
    
                    }

                }
                
                if (blnRes)
                {
                    /////strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                 
////                            objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + datFecAux + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());


//////                               if(objHisTblBasDat.insertarHistoricoMasivo(connZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) 
//////                                { 
                    if(objHisTblBasDatGP.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                    { 
                        con.commit();
                        blnRes=true;
////                        mostrarMsgInf("<HTML>Se realizo la modificacion de forma exitosa.</HTML>");
                        if (cadenaItems.length() > 0 && cadenaItems.charAt(cadenaItems.length() - 1) == ',') {
                            cadenaItems.setLength(cadenaItems.length() - 1);
                        }  
                        
                    }
                    else
                    {
                        con.rollback();                                        
                        blnRes=false;
                        mostrarMsgInf("<HTML>No se realizo la modificacion de forma exitosa.</HTML>");
                    }    
                                         
                               
                               
                               
                               
                               

////                        if(InsertarHistorico(con,strFecUltMod,objParSis.getNombreUsuario()))
////                        {
////                            
//////////////                            stmZAF.executeUpdate(strSQLZAF);
//////////////                            objHisTblBasDat.insertarHistoricoMasivo(conZAF, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + datFecAuxZAF + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
////                            conZAF.commit();
////
////                            stm.executeUpdate(strSQL);
////                            con.commit();                            
////                            blnRes=true;
////                        }
////
////                        else
////                             blnRes=false;    

                        

                }                
                
                

                
                stm.close();
                con.close();
                stm=null;
                con=null;
                stmZAF.close();
                conZAF.close();
                stmZAF=null;
                conZAF=null;                
                datFecAux=null;
                datFecAuxZAF=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
private String getEjecucionABC(String item,java.sql.Connection conExt,String fecha,int codAbcFre,int codAbcPes, int codAbcVent,int codAbcTuv,int codAbcCla )//chiqui
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;  
        String strSQLOP="";
        String strValor="",strPeso="",strFre="",strTuv="",strCla="";
        String strSQLInt="";
        String cabecera="",values="";
        try
        {
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conExt!=null)
            {
                strSQLInt="";
                cabecera="";
                values="";
                stmLoc=conExt.createStatement();
                //Armar la sentencia SQL.
                strSQLOP="";
                strSQLOP+="SELECT isnull([Valor venta],'') as valor ,isnull(Peso,'') as peso,isnull(Frecuencia,'') as fre,isnull(Tuval,'') as tuv,isnull(Clase,'') as cla \n";
                strSQLOP+="FROM dynamics2021..vw_itemsABC_Columnas \n";
                strSQLOP+="WHERE  tx_itm='"+ item +"'";
                rstLoc=stmLoc.executeQuery(strSQLOP);
                while (rstLoc.next())
                {
                    strFre=rstLoc.getString("fre");
                    strPeso=rstLoc.getString("peso");
                    strValor=rstLoc.getString("valor");
                    strTuv=rstLoc.getString("tuv");
                    strCla=rstLoc.getString("cla");
                    if (!strFre.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ codAbcFre +" where  tx_itm = '"+item +"' and co_catpar =1;\n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+item +"',1,"+ codAbcFre +",'A',"+objParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }
                    if (!strPeso.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ codAbcPes +" where tx_itm = '"+item +"' and co_catpar =2; \n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ item +"',2,"+ codAbcPes +",'A',"+objParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }

                    if (!strValor.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ codAbcVent +" where tx_itm = '"+item +"' and co_catpar =3; \n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ item +"',3,"+ codAbcVent +",'A',"+objParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                    
                    
                    if (!strTuv.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ codAbcTuv +" where tx_itm = '"+item +"' and co_catpar =4; \n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ item +"',4,"+ codAbcTuv +",'A',"+objParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                    
                    if (!strCla.equals(""))
                        strSQLInt+="UPDATE Dynamics2021..tbr_itmpar set co_nivpar= "+ codAbcCla +" where tx_itm = '"+item +"' and co_catpar =5; \n";
                    else
                    {
                        cabecera ="(co_emp,co_loc,tx_itm,co_catpar,co_nivpar,st_reg,co_usring,fe_ing,co_usrmod,fe_ultmod)";
                        values ="(0,1,'"+ item+"',5,"+ codAbcCla +",'A',"+objParSis.getCodigoUsuario()+",'"+ fecha +"',0,'1900-01-01 00:00:00.000')";
                        strSQLInt+=" INSERT INTO Dynamics2021..tbr_itmpar  "+ cabecera +" VALUES "+ values +";\n";
                    }                                        
                   // blnRes=true;
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
            strSQLInt="";
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            strSQLInt="";
            objUti.mostrarMsgErr_F1(null, e);
        }
        return strSQLInt;
    }                        
                    


    
    private boolean actualizarDetABC()
    {
        int intNumFil, i, intMaxHis=-1,idExt=0;
        boolean blnRes=false;
        int idItemPeso=0;
        java.sql.ResultSet rstLoc;
        String strFecUltMod="", strSQLZAF ="";
        cadenaItems = new StringBuilder();
        String items ="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                con.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());                   
                intNumFil=objTblMod.getRowCountTrue();
////                strSQL="";
                for (i=0; i<intNumFil;i++)
                {  
                    strSQL="";
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        strSQL =getEjecucionABC(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_ITM).toString(),con,strFecUltMod,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_FRE).toString()),Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_PES).toString()), Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_VAL).toString()),Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_TUV).toString()),Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_ABC_COD_CLA).toString()) );
                        if (!strSQL.equals(""))
                            blnRes=true;
                        if (blnRes)
                            stm.executeUpdate(strSQL);
                    }

                }
                
                if (blnRes)
                {
                    if(objHisTblBasDatGP.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrmod='" + objParSis.getNombreUsuario()+"'")) 
                    { 
                        con.commit();
                        blnRes=true;
                    }
                    else
                    {
                        con.rollback();                                        
                        blnRes=false;
                        mostrarMsgInf("<HTML>No se realizo la modificacion de forma exitosa.</HTML>");
                    }    
                }                 
                
               
                
                

                
                stm.close();
                con.close();
                stm=null;
                con=null;
                datFecAux=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
    
////private boolean ModificarZAFIRO (String item)
////{
////////    boolean blnRes=false;
////////        try
////////        {
////////            conZAF=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////////            if (con!=null)
////////            {
////////                stmZAF=conZAF.createStatement();
////////                conZAF.setAutoCommit(false);     
////////                datFecAuxZAF=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());                
////////                if (datFecAuxZAF==null)
////////                    return false;                
////////            }
////////        }    
////////    return blnRes;
////}
    
private boolean InsertarHistorico (java.sql.Connection conEXT,String fecha,String usuario)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="",strSQLoc;
        try
        {
            if (con!=null)
            {

                stmLoc=con.createStatement();
                    strSQLoc="";
                    
                    strSQLoc+=" INSERT INTO tbh_inv\n" +
"				(co_his, tx_itm, nd_marUti, nd_canMaxVen, nd_facCosUni, tx_usrmod, fe_ultmod, tx_codalt, tx_nomitm, tx_codalt2, nd_preVta1, \n" +
"				 co_grpimp, tx_nomitming, st_revtec, st_itmstk) \n";
                    strSQLoc+=" SELECT isnull(a2.maximo,0)+1  maximo,a1.tx_itm, a1.nd_marUti,nd_canMaxVen,a1.nd_facCosUni,a1.tx_usrmod, a1.fe_ultmod,a1.tx_codalt,"
                            + "a1.tx_nomitm,a1.tx_codalt2,a1.nd_preVta1,a1.co_grpimp,a1.tx_nomitming,a1.st_revtec,a1.st_itmstk    \n";
                    strSQLoc+=" FROM tbm_inv AS a1  \n";
                    strSQLoc+=" left join (  \n";
                    strSQLoc+=" select isnull(max(co_his),0) as maximo,a2.tx_itm from tbh_inv as a2   \n";
                    strSQLoc+=" group by tx_itm \n";
                    strSQLoc+=" ) as a2 on (a1.tx_itm =a2.tx_itm) \n";
                    strSQLoc+=" WHERE a1.fe_ultMod = '"+  fecha  +"' and a1.tx_usrmod ='"+ usuario +"'\n";
                
                    System.out.println("UPDATE " + strSQLoc);                    
////                }
                
                //Armar la sentencia SQL.

                stmLoc.executeUpdate(strSQLoc);
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

////private boolean InsersionEXT01101(java.sql.Connection conEXT, int i)//CHIQUI -PARA LA INSERCION DE MARCA-MATERIAL-REF GENERAL Y DETALLADA--1101
////{
////////        java.sql.Connection conLoc;
////        java.sql.ResultSet rstLoc;
////        java.sql.Statement stmLoc;
////        boolean blnRes=false;
////        String values00="",cabecera00="",strSQL="",values01="",cabecera01="",strSQLGlo="";
////        int ultimoId=0,indicador =0,ultimoIdSgte=0;
////        
////        try
////        {
////            //conEXT = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
////            if(conEXT!=null)
////            {
////                stmLoc=conEXT.createStatement();
////                ultimoId=1;
////                if (ultimoId !=0)
////                {
////                    strSQL="";
//// 
////                        
////  
////                       // values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"',0,'"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";
////
////                        if (i==0)//para la marca
////                        {
////                         strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                         strSQL+=" VALUES ( \n";
////                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 119 ,'"+ txtMar.getText() +"'); \n"; 
////                        }
////                        else
////                        {
////                            if (i==1)//para el material
////                            {
////////                                values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";
////////                                values01 ="('"+ ultimoId +"',120 ,'"+ txtMat.getText() +"' );";
////                         strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                         strSQL+=" VALUES ( \n";
////                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 120 ,'"+ txtMat.getText() +"'); \n"; 
////                            }
////                                
////                            else
////                            {
////                                if (i==2)//PARA LA REF GENEERAL
////                                {
////////                                    ultimoIdSgte=ultimoId+1;
////////                                    values00 ="('"+ ultimoIdSgte+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
//////////                                    values01 ="('"+ ultimoIdSgte +"',117 ,'"+ txtRefG.getText() +"' );";
////                                         strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                                         strSQL+=" VALUES ( \n";
////                                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_REF_IV' ), 117 ,'"+ txtRefG.getText() +"'); \n"; 
////
////                                }
////                                    
////                                else
////                                {
////                                    if (i==3)//PARA LA REF GENEERAL//PARA LA REF DETALLADA
////                                    {
////////                                        ultimoIdSgte=ultimoId+1;
////////                                        values00 ="('"+ ultimoIdSgte+"', 'MSW_REF_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                    
////////                                        values01 ="('"+ ultimoIdSgte +"',118,'"+ txtRefD.getText() +"' );"; 
////                                          strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                                         strSQL+=" VALUES ( \n";
////                                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_REF_IV' ), 118 ,'"+ txtRefD.getText() +"'); \n"; 
////                                    }
////                                        
////                                    else
////                                    {
////                                        if(i==4)
////                                        {
////////                                            values00 ="('"+ ultimoId+"', 'MSW_ADI_IV' ,'"+ txtCodGP.getText() +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"','"+ strFecDes +"','"+ objZafParSis.getNombreUsuario()  +"')";                                        
////////////                                            values01 ="('"+ ultimoId +"',181,'"+ txtCodTama.getText() +"' );";  
////                                         strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                                         strSQL+=" VALUES ( \n";
////                                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 181 ,'"+ txtCodTama.getText() +"'); \n";                                             
////                                        }
////                                        else
////                                        {
////                                         strSQL+=" INSERT INTO "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101  (Extender_Record_ID, Field_ID, STRGA255) \n";
////                                         strSQL+=" VALUES ( \n";
////                                         strSQL+=" (SELECT TOP 1 Extender_Record_ID FROM TUVAL..EXT01100 where Extender_Key_Values_1 = '"+ txtCodGP.getText() +"' AND Extender_Window_ID = 'MSW_ADI_IV' ), 121 ,'"+ prefijo +"'); \n";                                             
////                                                                             
////                                        }    
////                                    }
////                                        
////                                }
////                                
////                            }
////                        }
////                        
////                
////
////
////                    
////                    System.out.println("INSERT EXT01100-EXT01101" + strSQL);                     
////                    blnRes=true;
////                }
////                else
////                    blnRes=false;
////                if (blnRes ==true)
////                     stmLoc.executeUpdate(strSQL);
////               
////                stmLoc.close();
////                stmLoc=null;                    
////////                }
////            }            
////        }    
////        catch(java.sql.SQLException e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        catch(Exception e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        return blnRes;        
////}    
    
    
public int consultaEXT01101_EXT01100(java.sql.Connection conn,int tipo,String codigoGP )//CHIQUI
{
////    java.sql.Connection conEXT;
    java.sql.ResultSet rstLoc;
    java.sql.Statement stmLoc;
    int ndRsul=-1;
    boolean blnsExiste=false;
    String cabecera="",values="",strSQL="";
    int indicador =0;
    try
    {
    
    if (tipo ==0)
        indicador =117;//ref general
    if (tipo ==1)
        indicador =118;//ref detallada
    if (tipo ==2)
        indicador =119;//marca
    if (tipo ==3)
        indicador =120;//material
    if (tipo ==4)
        indicador =181;//tamano       
    if (tipo ==5)
        indicador =207;//linea
    if (tipo ==6)
        indicador =208;//clase    
    if (tipo ==7)
        indicador =199;//compras      
    if (tipo ==8)
        indicador =206;//categoria       
////      conn = DriverManager.getConnection(objZafParSis.getStringConexion_Dynamics2021(), objZafParSis.getUsuarioBaseDatos_Dynamics2021(), objZafParSis.getClaveBaseDatos_Dynamics2021());
      if(conn!=null)
        {
            stmLoc=conn.createStatement();
            strSQL="";
            strSQL+=" SELECT e101.Extender_Record_ID, STRGA255 as MSW_ADI_IV_118_Referencia_Detallada,e100.Extender_Key_Values_1,e101.DEX_ROW_ID as id  \n";            
            strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..EXT01101 AS e101 \n";
            strSQL+="       INNER JOIN "+ objParSis.getNombreBaseDatosGP()  +"..EXT01100 as e100 on (e101.Extender_Record_ID = e100.Extender_Record_ID ) \n";
            strSQL+="       WHERE e101.Field_ID ="+ indicador +" and e100.Extender_Key_Values_1  ='"+ codigoGP +"'";
            
            rstLoc=stmLoc.executeQuery(strSQL);
            if (rstLoc.next())
            {
                blnsExiste=true;
                ndRsul=rstLoc.getInt("id");
            }
            else
                ndRsul=-1;
////            stmLoc.executeUpdate(strSQL);

            stmLoc.close();
            stmLoc=null;  
            rstLoc.close();
            rstLoc=null;            

////                }
        }    
    }    
    catch(java.sql.SQLException e){
        objUti.mostrarMsgErr_F1(this, e);
        ndRsul=-1;

    }
    catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
        ndRsul=-1;
    }
    return ndRsul;
  }
    
   


    
    
    
    
}

