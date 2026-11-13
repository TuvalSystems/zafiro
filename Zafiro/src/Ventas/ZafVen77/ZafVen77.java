/*
 * ZafVen77.java
 *
 * 
 */
package Ventas.ZafVen77;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafHisTblBasDatGP.ZafHisTblBasDatGP;
import Librerias.ZafNotCorEle.ZafNotCorEle;
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
import java.sql.PreparedStatement;
import java.util.HashMap;


public class ZafVen77 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHK=1;
    private static final int INT_TBL_DAT_RUC=2;
    private static final int INT_TBL_DAT_CLI=3;
    private static final int INT_TBL_DAT_COD_SEG_ACT = 4;
    private static final int INT_TBL_DAT_SEG_ACT=5;
    private static final int INT_TBL_DAT_COD_SEG_NUE = 6;
    private static final int INT_TBL_DAT_SEG_NUE=7;
   
    private static final int INT_TBL_DAT_SEG_LIN=0;
    private static final int INT_TBL_DAT_SEG_CHK=1;
    private static final int INT_TBL_DAT_SEG_NOM=2;
    private static final int INT_TBL_DAT_SEG_COD=3;
    
    
    private static final int INT_TBL_DAT_VEN_LIN=0;
    private static final int INT_TBL_DAT_VEN_CHK=1;
    private static final int INT_TBL_DAT_VEN_NOM=2;
    private static final int INT_TBL_DAT_VEN_COD=3;
    
    private static final int INT_TBL_DAT_CAN_LIN=0;
    private static final int INT_TBL_DAT_CAN_CHK=1;
    private static final int INT_TBL_DAT_CAN_NOM=2;
    private static final int INT_TBL_DAT_CAN_COD=3;
    
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
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuPri,objTblPopMnuVen,objTblPopMnuCan ;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab,objTblFilCabVen,objTblFilCabCan ;
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
    private Vector vecDatVen, vecRegVen, vecCabVen, vecAuxVen;
    private Vector vecDatCan, vecRegCan, vecCabCan, vecAuxCan;
    
    private ZafNotCorEle objNotCorEle;
    
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
    
    private ZafTblMod objTblModSeg, objTblModVen,objTblModCan;
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
    private String version="v0.2";
    private ZafHisTblBasDat objHisTblBasDat;  
    private ZafHisTblBasDatGP objHisTblBasDatGP;       
    StringBuilder cadenaItems = new StringBuilder();
    
    private ZafTblMod objTblModABC;    
    private Vector vecDatABC, vecRegABC, vecCabABC, vecAuxABC; 
    private ZafTblPopMnu objTblPopMnuABC, objTblPopMnuPriABC;  
    int valTip=0;
        
    private HashMap<String, String> mapSegmentos =   new HashMap<String, String>();
    
    private ZafVenConGP vconCli;
    
    ZafVenConGP objVenABCTuval; //***************** 
    ZafVenConGP objVenABCFre; //***************** 
    ZafVenConGP objVenABCValor; //*****************
    ZafVenConGP objVenABCClas; //*****************    
    ZafVenConGP objVenABCPeso; //*****************  
           
    
    private class ZafThGuarGUI extends Thread
    {
        public void run()
        {
            int intChk = 0;
            int intCliAct = 0;

            for (int i = 0; i < tblDat.getRowCount(); i++)
            {
                boolean chk = (Boolean)objTblMod.getValueAt(i,INT_TBL_DAT_CHK);
                String segActCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_ACT).toString();
                String segNueCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_NUE).toString();

                if (chk)
                {
                    intChk++;
                    if (!segActCod.equals(segNueCod))
                    {
                        intCliAct++;
                    }
                }
            }

            //No existe ningún check
            if (intChk == 0)
            {
                mostrarMsgInf("Debe seleccionar al menos un cliente a actualizar.");
                objThrGuaGUI = null;
                return;
            }

            //Segmento actual igual al nuevo
            if (intCliAct == 0)
            {
                mostrarMsgInf("El segmento a actualizar es el mismo que el actual.");
                objThrGuaGUI = null;
                return;
            }

            //Confirmación
            if (mostrarMsgCon("¿Desea actualizar Segmento a " + intCliAct + " clientes ?")==0)
            {
                lblMsgSis.setText("Guardando");

                if (actualizarDet())
                {
                    
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");

                    mostrarMsgInf("La operación GUARDAR se realizó con éxito." + "\nClientes actualizados: "+ intCliAct);
                }
                else
                {
                    mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.");

                    pgrSis.setValue(0);
                    lblMsgSis.setText("Cancelado");
                }

                if (tblDat.getRowCount()>0)
                {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }

                objThrGUI = null;
            }

            objThrGuaGUI = null;
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

    
    public ZafVen77(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
  
            initComponents();
            if (!configurarFrm()) 
                exitForm();

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
//            objVenConItm01GP=new ZafVenConItmGP01(objParSis, "a1.", optFil);
//            panFil.add(objVenConItm01GP);
//            objVenConItm01GP.setBounds(24, 50, 656, 20);
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objUtiGP=new ZafUtilGP(objParSis);  
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);  
            //panABC.setVisible(false);
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + version);
            lblTit.setText(strAux);
                        
           objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
           optTod.setSelected(true);
           
            //Configurar los JTables.
            
            configurarTblDat();
            cargarComboSegmento();
            
            configurarTblSegmento();
            cargarSegmentos();
            
            configurarTblVendedor();
            cargarVendedores();
            
            configurarTblCanales();
            cargarCanales();
            
            configurarVenConCli();
            
            
            tabFrm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    
                }
            });
            
            cmbSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoSegmento();
            }
            });
            

            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
private boolean configurarTblSegmento()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_SEG_LIN,"");
            vecCab.add(INT_TBL_DAT_SEG_CHK,"");
            vecCab.add(INT_TBL_DAT_SEG_NOM,"Segmento");
            vecCab.add(INT_TBL_DAT_SEG_COD,"Codigo");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModSeg=new ZafTblMod();
            objTblModSeg.setHeader(vecCab);
            tblLoc.setModel(objTblModSeg);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_SEG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_SEG_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_SEG_NOM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_SEG_COD).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_SEG_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);

            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModSeg.addSystemHiddenColumn(INT_TBL_DAT_SEG_COD, tblLoc);
            
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
            vecAux.add("" + INT_TBL_DAT_SEG_CHK);
            objTblModSeg.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_DAT_SEG_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_SEG_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_SEG_NOM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_DAT_SEG_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    optFil.setSelected(true);
                    txtcodcli.setText("");
                    txtNomCli.setText("");
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModSeg.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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

    private boolean cargarSegmentos()//chiqui
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
                
                strSQL = "";
                strSQL += "SELECT h.co_atrcli, h.tx_nom, p.tx_nom AS segmento ";
                strSQL += "FROM Dynamics2021.dbo.tbm_atrcli h ";
                strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli p ";
                strSQL += "ON h.co_pad = p.co_atrcli ";
                strSQL += "WHERE h.co_pad IS NOT NULL ";
                strSQL += "AND h.st_reg = 'A' "; 
                strSQL += "ORDER BY h.tx_nom";
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_SEG_LIN,"");
                    vecReg.add(INT_TBL_DAT_SEG_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_DAT_SEG_NOM,rst.getString("tx_nom") + " - " + rst.getString("segmento"));
                    vecReg.add(INT_TBL_DAT_SEG_COD,rst.getString("co_atrcli"));
                    
                    vecDat.add(vecReg);
                }
                
                vecReg = new Vector();
                vecReg.add(INT_TBL_DAT_SEG_LIN,"");
                vecReg.add(INT_TBL_DAT_SEG_CHK,Boolean.TRUE);
                vecReg.add(INT_TBL_DAT_SEG_NOM, "SIN SEGMENTO");
                vecReg.add(INT_TBL_DAT_SEG_COD, "-1");
                
                vecDat.add(vecReg);
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModSeg.setData(vecDat);
                tblLoc.setModel(objTblModSeg);
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

    
    private boolean configurarTblVendedor()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatVen=new Vector();    //Almacena los datos
            vecCabVen=new Vector(6);   //Almacena las cabeceras
            vecCabVen.clear();
            vecCabVen.add(INT_TBL_DAT_VEN_LIN,"");
            vecCabVen.add(INT_TBL_DAT_VEN_CHK,"");
            vecCabVen.add(INT_TBL_DAT_VEN_NOM,"Vendedor");
            vecCabVen.add(INT_TBL_DAT_VEN_COD,"Codigo");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModVen=new ZafTblMod();
            objTblModVen.setHeader(vecCabVen);
            tblVen.setModel(objTblModVen);
            //Configurar JTable: Establecer tipo de selección.
            tblVen.setRowSelectionAllowed(true);
            tblVen.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuVen=new ZafTblPopMnu(tblVen);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblVen.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblVen.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_VEN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_VEN_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_VEN_NOM).setPreferredWidth(200);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_VEN_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblVen.getTableHeader().setReorderingAllowed(false);

            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModVen.addSystemHiddenColumn(INT_TBL_DAT_VEN_COD, tblVen);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblVen.getTableHeader().addMouseMotionListener(new ZafMouMotAdaTipo());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblVen.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblVenMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAuxVen=new Vector();
            vecAuxVen.add("" + INT_TBL_DAT_VEN_CHK);
            objTblModVen.setColumnasEditables(vecAuxVen);
            vecAuxVen=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabVen=new ZafTblFilCab(tblVen);
            tcmAux.getColumn(INT_TBL_DAT_VEN_LIN).setCellRenderer(objTblFilCabVen);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_VEN_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_VEN_NOM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblVen);
            tcmAux.getColumn(INT_TBL_DAT_VEN_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    optFil.setSelected(true);
                    txtcodcli.setText("");
                    txtNomCli.setText("");
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModVen.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
    
    
    private boolean cargarVendedores()//chiqui
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
                
                strSQL = "";
                strSQL += "SELECT u.co_usr, u.tx_usr, u.tx_nom ";
                strSQL += "FROM Dynamics2021.dbo.tbm_usr u ";
                strSQL += "inner join Dynamics2021.dbo.tbm_TipVen as tv on (u.co_tipven = tv.co_tipven) ";
                strSQL += "WHERE u.st_reg = 'A' "; 
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatVen.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecRegVen=new Vector();
                    vecRegVen.add(INT_TBL_DAT_VEN_LIN,"");
                    vecRegVen.add(INT_TBL_DAT_VEN_CHK,Boolean.TRUE);
                    vecRegVen.add(INT_TBL_DAT_VEN_NOM,rst.getString("tx_nom") );
                    vecRegVen.add(INT_TBL_DAT_VEN_COD,rst.getString("co_usr") );
                    vecDatVen.add(vecRegVen);
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModVen.setData(vecDatVen);
                tblVen.setModel(objTblModVen);
                vecDatVen.clear();
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
    
    private boolean configurarTblCanales()//chiqui
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatCan=new Vector();    //Almacena los datos
            vecCabCan=new Vector(6);   //Almacena las cabeceras
            vecCabCan.clear();
            vecCabCan.add(INT_TBL_DAT_CAN_LIN,"");
            vecCabCan.add(INT_TBL_DAT_CAN_CHK,"");
            vecCabCan.add(INT_TBL_DAT_CAN_NOM,"Canal");
            vecCabCan.add(INT_TBL_DAT_CAN_COD,"Codigo");
            
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModCan=new ZafTblMod();
            objTblModCan.setHeader(vecCabCan);
            tblCan.setModel(objTblModCan);
            //Configurar JTable: Establecer tipo de selección.
            tblCan.setRowSelectionAllowed(true);
            tblCan.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuCan=new ZafTblPopMnu(tblCan);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCan.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CAN_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAN_NOM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COD).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CAN_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCan.getTableHeader().setReorderingAllowed(false);

            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCan.addSystemHiddenColumn(INT_TBL_DAT_CAN_COD, tblCan);
            
            tblCan.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblCanMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAuxCan=new Vector();
            vecAuxCan.add("" + INT_TBL_DAT_CAN_CHK);
            objTblModCan.setColumnasEditables(vecAuxCan);
            vecAuxCan=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabCan=new ZafTblFilCab(tblCan);
            tcmAux.getColumn(INT_TBL_DAT_CAN_LIN).setCellRenderer(objTblFilCabCan);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CAN_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_CAN_NOM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblVen);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    optFil.setSelected(true);
                    txtcodcli.setText("");
                    txtNomCli.setText("");
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModCan.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
    
    
    private boolean cargarCanales()//chiqui
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
                
                strSQL = "";
                strSQL += "SELECT co_id, tx_canasi ";
                strSQL += "FROM Dynamics2021.dbo.tbm_canasi ";  
                
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatCan.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecRegCan=new Vector();
                    vecRegCan.add(INT_TBL_DAT_CAN_LIN,"");
                    vecRegCan.add(INT_TBL_DAT_CAN_CHK,Boolean.TRUE);
                    vecRegCan.add(INT_TBL_DAT_CAN_NOM,rst.getString("tx_canasi") );
                     vecRegCan.add(INT_TBL_DAT_CAN_COD,rst.getString("co_id") );
                    
                    vecDatCan.add(vecRegCan);
                }
                
                vecRegCan = new Vector();

                vecRegCan.add(INT_TBL_DAT_CAN_LIN, "");
                vecRegCan.add(INT_TBL_DAT_CAN_CHK, Boolean.TRUE);
                vecRegCan.add(INT_TBL_DAT_CAN_NOM, "SIN CANAL");
                vecRegCan.add(INT_TBL_DAT_CAN_COD, "-1");

                vecDatCan.add(vecRegCan);
                
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModCan.setData(vecDatCan);
                tblCan.setModel(objTblModCan);
                vecDatCan.clear();
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
            intNumFil=objTblModSeg.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_DAT_SEG_CHK)
            {
                optFil.setSelected(true);
                txtcodcli.setText("");
                txtNomCli.setText("");

                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModSeg.setChecked(true, i, INT_TBL_DAT_SEG_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModSeg.setChecked(false, i, INT_TBL_DAT_SEG_CHK);
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
    
    private void tblVenMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModVen.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblVen.columnAtPoint(evt.getPoint())==INT_TBL_DAT_VEN_CHK)
            {
                optFil.setSelected(true);
                txtcodcli.setText("");
                txtNomCli.setText("");

                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModVen.setChecked(true, i, INT_TBL_DAT_VEN_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModVen.setChecked(false, i, INT_TBL_DAT_VEN_CHK);
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
    
    private void tblCanMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModCan.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblCan.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CAN_CHK)
            {
                optFil.setSelected(true);
                txtcodcli.setText("");
                txtNomCli.setText("");

                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblModCan.setChecked(true, i, INT_TBL_DAT_CAN_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModCan.setChecked(false, i, INT_TBL_DAT_CAN_CHK);
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
    
    private void tblDatMouseClicked(java.awt.event.MouseEvent evt)//chiqui
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK)
            {
                if (blnMarTodChkTblEmp){
                    for (i=0; i<intNumFil; i++){
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
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

private class ZafMouMotAdaTipo extends java.awt.event.MouseMotionAdapter//chiqui
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_SEG_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_SEG_CHK:
                    strMsg="Selección";
                    break;
                case INT_TBL_DAT_SEG_NOM:
                    strMsg="Nombre del Tipo de Atributo";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
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
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_RUC,"Ruc");
            vecCab.add(INT_TBL_DAT_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_COD_SEG_ACT,"Cod.Seg.Act.");
            vecCab.add(INT_TBL_DAT_SEG_ACT,"SegmentoActual");
            vecCab.add(INT_TBL_DAT_COD_SEG_NUE,"Cod.Seg.Nue.");
            vecCab.add(INT_TBL_DAT_SEG_NUE,"SegmentoNuevo");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);  
        
            tblDat.setCellSelectionEnabled(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
           
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);    
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            
            tcmAux.getColumn(INT_TBL_DAT_RUC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CLI).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG_ACT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_SEG_ACT).setPreferredWidth(280);
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG_NUE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_SEG_NUE).setPreferredWidth(280);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
                
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SEG_ACT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SEG_NUE, tblDat);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            //tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
           
            //tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAdaTipo());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
                 
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPri=new ZafTblPopMnu(tblDat);
            //objTblPopMnuPri.setPegarEnabled(true);
            //objTblPopMnuPri.setBorrarContenidoEnabled(true);
            objTblPopMnuPri.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {

                }
            });

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat); 
            
            //Render para SegmentoNuevo
            ZafTblCelRenLbl objTblCelRenSegNue = new ZafTblCelRenLbl();
            objTblCelRenSegNue.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenSegNue.setBackground(new java.awt.Color(185,255,115));
            objTblCelRenSegNue.setOpaque(true);

            tcmAux.getColumn(INT_TBL_DAT_SEG_NUE).setCellRenderer(objTblCelRenSegNue);
            
           
            System.out.println("Se configuara la tabla!!!!... ");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarVenConCli() {
        boolean blnRes = true;
        try {
            ArrayList arlCam = new ArrayList();
            arlCam.add("CUSTNMBR");
            arlCam.add("CUSTNAME");

            ArrayList arlAli = new ArrayList();
            arlAli.add("RUC");
            arlAli.add("Cliente");

            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("120");
            arlAncCol.add("300");

            strSQL = "";
            strSQL += "SELECT CUSTNMBR, CUSTNAME ";
            strSQL += "FROM TUVAL.dbo.RM00101 ";
            strSQL += "WHERE 1=1 ";
            //strSQL += "ORDER BY CUSTNAME ";

            //System.out.println("configurarVenConCli: " + strSQL);

            vconCli = new ZafVenConGP(
                javax.swing.JOptionPane.getFrameForComponent(this),
                objParSis,
                "Listado de clientes",
                strSQL,
                arlCam,
                arlAli,
                arlAncCol
            );

        } catch (Exception e) {
            blnRes = false;
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
                case INT_TBL_DAT_RUC: strMsg="Ruc de Cliente"; break;
                case INT_TBL_DAT_CLI: strMsg="Cliente"; break;
                case INT_TBL_DAT_SEG_ACT: strMsg="Segmento Actual"; break;
                case INT_TBL_DAT_SEG_NUE: strMsg="Segmento Nuevo"; break;            

                default: strMsg=""; break;
               
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
        panDat = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panLoc1 = new javax.swing.JPanel();
        spnLoc2 = new javax.swing.JScrollPane();
        tblVen = new javax.swing.JTable();
        panLoc2 = new javax.swing.JPanel();
        spnLoc3 = new javax.swing.JScrollPane();
        tblCan = new javax.swing.JTable();
        optCli = new javax.swing.JRadioButton();
        txtNomCli = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtcodcli = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        panFil1 = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jPanel3 = new javax.swing.JPanel();
        lblLis1 = new javax.swing.JLabel();
        cmbSeg = new javax.swing.JComboBox<>();
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
        lblTit.setText("Asignacion de clientes a segmentos.....");
        lblTit.setToolTipText("");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panFil.setName(""); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(610, 180));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(20, 20, 400, 20);

        bgrFil.add(optTod);
        optTod.setText("Todos los clientes");
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

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Segmentos"));
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
        panLoc.setBounds(20, 50, 280, 210);

        panLoc1.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Vendedores"));
        panLoc1.setAutoscrolls(true);
        panLoc1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panLoc1.setLayout(new java.awt.BorderLayout());

        tblVen.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc2.setViewportView(tblVen);

        panLoc1.add(spnLoc2, java.awt.BorderLayout.CENTER);

        panFil.add(panLoc1);
        panLoc1.setBounds(330, 50, 280, 210);

        panLoc2.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Canales"));
        panLoc2.setAutoscrolls(true);
        panLoc2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panLoc2.setLayout(new java.awt.BorderLayout());

        tblCan.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc3.setViewportView(tblCan);

        panLoc2.add(spnLoc3, java.awt.BorderLayout.CENTER);

        panFil.add(panLoc2);
        panLoc2.setBounds(640, 50, 280, 210);

        bgrFil.add(optCli);
        optCli.setText("Cliente:");
        panFil.add(optCli);
        optCli.setBounds(20, 280, 70, 23);

        txtNomCli.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomCli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomCliMouseClicked(evt);
            }
        });
        panFil.add(txtNomCli);
        txtNomCli.setBounds(200, 280, 250, 20);

        jButton1.setText("...");
        jButton1.setPreferredSize(new java.awt.Dimension(45, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panFil.add(jButton1);
        jButton1.setBounds(450, 280, 20, 20);

        txtcodcli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcodcliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcodcliFocusLost(evt);
            }
        });
        txtcodcli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtcodcliMouseClicked(evt);
            }
        });
        panFil.add(txtcodcli);
        txtcodcli.setBounds(90, 280, 110, 20);

        panDat.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panDat);

        panRpt.setLayout(new java.awt.BorderLayout());

        panFil1.setLayout(new java.awt.BorderLayout());

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

        panFil1.add(panDet, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 30));
        jPanel3.setLayout(null);

        lblLis1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLis1.setText("NUEVO SEGMENTO:");
        lblLis1.setToolTipText("Beneficiario");
        jPanel3.add(lblLis1);
        lblLis1.setBounds(590, 4, 150, 20);

        jPanel3.add(cmbSeg);
        cmbSeg.setBounds(730, 4, 280, 20);

        panFil1.add(jPanel3, java.awt.BorderLayout.NORTH);

        panRpt.add(panFil1, java.awt.BorderLayout.PAGE_START);

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

        setBounds(0, 0, 1046, 490);
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
                objThrGUI=new ZafVen77.ZafThreadGUI();
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
            //objVenConItm01GP.limpiar();
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
             optCli.setSelected(false);
             
             txtcodcli.setText("");
             txtNomCli.setText("");
             
         }
    // TODO add your handling code here:
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
         if (optFil.isSelected()){
////             objVenConItm01GP.activa_desac(true);             
             optTod.setSelected(false);
             optCli.setSelected(false);
             
             txtcodcli.setText("");
             txtNomCli.setText("");
             
         }        
    }//GEN-LAST:event_optFilActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        mostrarVenConCli(0);
        optCli.setSelected(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtcodcliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusLost
        // TODO add your handling code here:
        if (txtcodcli.getText().trim().length() > 0)
        {
            mostrarVenConCli(1);
        }
        else
        {
            txtNomCli.setText("");
        }
    }//GEN-LAST:event_txtcodcliFocusLost

    private void txtcodcliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcodcliFocusGained
        // TODO add your handling code here:
        txtcodcli.selectAll();
    }//GEN-LAST:event_txtcodcliFocusGained

    private void txtcodcliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtcodcliMouseClicked
        // TODO add your handling code here:
        
        optCli.setSelected(true);
        
    }//GEN-LAST:event_txtcodcliMouseClicked

    private void txtNomCliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomCliMouseClicked
        // TODO add your handling code here:
        optCli.setSelected(true);
        txtNomCli.setCaretPosition(0);
    }//GEN-LAST:event_txtNomCliMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JComboBox<String> cmbSeg;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblLis1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optCli;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panLoc1;
    private javax.swing.JPanel panLoc2;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnLoc2;
    private javax.swing.JScrollPane spnLoc3;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCan;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblVen;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtcodcli;
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
            }    
            objThrGUI=null;            
            
        }
    }
    


    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
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
                String strSeg = obtenerFiltroSegmentos();
                String strVen = obtenerFiltroVendedores();
                String strCan = obtenerFiltroCanales();
                
                if (!validarFiltrosSeleccionados())
                {
                    objTblMod.removeAllRows();
                    pgrSis.setIndeterminate(false);
                    lblMsgSis.setText("");
                    return false;
                }
                
                strSQL = "";

                strSQL += "SELECT DISTINCT ";
                strSQL += "r.CUSTNMBR, ";
                strSQL += "r.CUSTNAME, "; 
                strSQL += "hijo.co_atrcli AS segActCod, ";
                strSQL += "CASE WHEN hijo.co_atrcli IS NULL THEN 'SIN SEGMENTO' ELSE CONCAT(hijo.tx_nom, ' - ', padre.tx_nom) END AS segmentoActual ";
                strSQL += "FROM TUVAL.dbo.RM00101 r WITH (NOLOCK) ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.tbr_atrcli cli WITH (NOLOCK) ";
                strSQL += "ON cli.tx_cli = r.CUSTNMBR ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.tbm_atrcli hijo WITH (NOLOCK) ";
                strSQL += "ON hijo.co_atrcli = cli.co_atrcli AND hijo.st_reg = 'A'  ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.tbm_atrcli padre WITH (NOLOCK) ";
                strSQL += "ON padre.co_atrcli = hijo.co_pad AND padre.st_reg = 'A' ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.tbr_cliloc c WITH (NOLOCK) ";
                strSQL += "ON c.tx_cli = r.CUSTNMBR ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.tbm_usr u WITH (NOLOCK) ";
                strSQL += "ON c.co_ven = u.co_usr ";

                strSQL += "LEFT JOIN Dynamics2021.dbo.vw_clientesPorCanal can WITH (NOLOCK) ";
                strSQL += "ON can.tx_cli = r.CUSTNMBR ";
                
                strSQL += "WHERE c.st_tipVen = 1";                
                
                
                
                if (optFil.isSelected())
                {
                    if (!strSeg.equals(""))
                    {
                        if (strSeg.contains("-1"))
                        {
                            strSeg = strSeg.replace("-1,", "")
                                           .replace(",-1", "")
                                           .replace("-1", "");

                            strSQL += " AND ( ";

                            if (!strSeg.equals(""))
                            {
                                strSQL += " hijo.co_atrcli IN (" + strSeg + ") ";
                                strSQL += " OR ";
                            }

                            strSQL += " hijo.co_atrcli IS NULL ) ";
                        }
                        else
                        {
                            strSQL += " AND hijo.co_atrcli IN (" + strSeg + ") ";
                        }
                    }

                    if (!strVen.equals(""))
                    {
                        strSQL += " AND c.co_ven IN (" + strVen + ") ";
                    }  

                    if (!strCan.equals(""))
                    {
                        if (strCan.contains("-1"))
                        {
                            strCan = strCan.replace("-1,", "")
                                           .replace(",-1", "")
                                           .replace("-1", "");

                            strSQL += " AND ( ";

                            if (!strCan.equals(""))
                            {
                                strSQL += " can.co_canal IN (" + strCan + ") ";
                                strSQL += " OR ";
                            }

                            strSQL += " can.co_canal IS NULL ) ";
                        }
                        else
                        {
                            strSQL += " AND can.co_canal IN (" + strCan + ") ";
                        }
                    }
                }

                if (optCli.isSelected())
                {
                    strSQL += " AND r.CUSTNMBR = '"
                            + txtcodcli.getText().trim()
                            + "' ";
                }                
                
                strSQL +="ORDER BY segmentoActual,CUSTNAME";
               
                System.out.println(strSQL);
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        String segNue = cmbSeg.getSelectedItem().toString();
                        String segNueCod = mapSegmentos.get(segNue);
                        
                        vecReg=new Vector();                                       
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_RUC,rst.getString("CUSTNMBR")==null?"":rst.getString("CUSTNMBR"));
                        vecReg.add(INT_TBL_DAT_CLI,rst.getString("CUSTNAME")==null?"":rst.getString("CUSTNAME"));  
                        vecReg.add(INT_TBL_DAT_COD_SEG_ACT,rst.getString("segActCod")==null?"":rst.getString("segActCod"));
                        vecReg.add(INT_TBL_DAT_SEG_ACT,rst.getString("segmentoActual")==null?"":rst.getString("segmentoActual"));
                        vecReg.add(INT_TBL_DAT_COD_SEG_NUE,segNueCod);
                        vecReg.add(INT_TBL_DAT_SEG_NUE,segNue);
                        
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
        
    
     /**
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, i;
        boolean blnRes = true;

        try
        {
            pgrSis.setIndeterminate(false);
            butCon.setText("Detener");
            lblMsgSis.setText("Guardando datos...");

            con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021());

            if (con != null)
            {
                con.setAutoCommit(false);

                intNumFil = tblDat.getRowCount(); 
                
                int totalProcesar = obtenerTotalClientesActualizar();                
                int contProcesados = 0;                
                int totalCambios = 0;
                
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(obtenerTotalClientesActualizar());
                pgrSis.setValue(0);
                

                StringBuilder strDet = new StringBuilder();

                strDet.append("<table style='border-collapse:collapse; font-family:Arial; font-size:12px;'>");
                strDet.append("<tr style='background-color:#2c3e50; color:white;'>");
                strDet.append("<th style='padding:6px;'>RUC</th>");
                strDet.append("<th style='padding:6px;'>CLIENTE</th>");
                strDet.append("<th style='padding:6px;'>SEGMENTO ANTERIOR</th>");
                strDet.append("<th style='padding:6px;'>SEGMENTO NUEVO</th>");

                strDet.append("</tr>");

                for (i = 0; i < intNumFil; i++)
                {
//                    contProcesados++;
//                    lblMsgSis.setText("Procesando registro " + (i + 1) + " de " + intNumFil + "...");
//                    pgrSis.setValue(i + 1);
                    boolean chk = (Boolean)objTblMod.getValueAt(i,INT_TBL_DAT_CHK);
                    

                    if (chk)
                    {
                        String ruc = objTblMod.getValueAt(i,INT_TBL_DAT_RUC).toString();
                        String segActCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_ACT).toString();
                        String segNueCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_NUE).toString();

                        if (!segActCod.equals(segNueCod))
                        {
                            contProcesados++;
                            lblMsgSis.setText("Procesando cliente "+ contProcesados+ " de "+ totalProcesar+ "...");
                            pgrSis.setValue(contProcesados);
                            
                            String cliente = objTblMod.getValueAt(i,INT_TBL_DAT_CLI).toString();
                            String segAct = objTblMod.getValueAt(i,INT_TBL_DAT_SEG_ACT).toString();
                            String segNue = objTblMod.getValueAt(i,INT_TBL_DAT_SEG_NUE).toString();

                            strDet.append("<tr style='border-bottom:1px solid #ddd;'>");
                            strDet.append("<td style='padding:6px;'>")
                                  .append(ruc)
                                  .append("</td>");
                            strDet.append("<td style='padding:6px;'>")
                                  .append(cliente)
                                  .append("</td>");
                            strDet.append("<td style='padding:6px;'>")
                                  .append(segAct)
                                  .append("</td>");
                            strDet.append("<td style='padding:6px;'>")
                                  .append(segNue)
                                  .append("</td>");
                            strDet.append("</tr>");
                            totalCambios++;
                            
                            
                            String segActCodSQL = "";

                            if (segActCod == null || segActCod.trim().equals("") || segActCod.trim().equals("-1"))
                            {
                                segActCodSQL = "NULL";
                            }
                            else
                            {
                                segActCodSQL = segActCod.trim();
                            }

                            String segNueCodSQL = "";

                            if (segNueCod != null)
                            {
                                segNueCodSQL = segNueCod.trim();
                            }

                            // Validación: no permitir grabar SIN SEGMENTO como segmento nuevo.
                            if (segNueCodSQL.equals("") || segNueCodSQL.equals("-1"))
                            {
                                mostrarMsgInf("Debe seleccionar un segmento nuevo válido. No se puede grabar 'SIN SEGMENTO'.");
                                blnRes = false;
                                break;
                            }

                            // Por seguridad, escapar comillas simples.
                            String rucSQL = ruc.replace("'", "''");

                            strSQL = "";

                            // Si el cliente ya existe en tbr_atrcli, actualiza.
                            // Si no existe, inserta.
                            strSQL += "IF EXISTS ( ";
                            strSQL += "    SELECT 1 ";
                            strSQL += "    FROM Dynamics2021.dbo.tbr_atrcli ";
                            strSQL += "    WHERE tx_cli = '" + rucSQL + "' ";
                            strSQL += ") ";
                            strSQL += "BEGIN ";
                            strSQL += "    UPDATE Dynamics2021.dbo.tbr_atrcli ";
                            strSQL += "    SET co_atrcli = " + segNueCodSQL + " ";
                            strSQL += "    WHERE tx_cli = '" + rucSQL + "'; ";
                            strSQL += "END ";
                            strSQL += "ELSE ";
                            strSQL += "BEGIN ";
                            strSQL += "    INSERT INTO Dynamics2021.dbo.tbr_atrcli ";
                            strSQL += "    (tx_cli, co_atrcli) ";
                            strSQL += "    VALUES ";
                            strSQL += "    ('" + rucSQL + "', " + segNueCodSQL + "); ";
                            strSQL += "END; ";

                            // Historial de cambios
                            strSQL += "INSERT INTO Dynamics2021.dbo.tbh_cliseg ";
                            strSQL += "(co_his, tx_ruc, co_segant, co_segnue, co_usrmod) ";

                            strSQL += "SELECT ISNULL(MAX(co_his),0)+1, ";
                            strSQL += "'" + rucSQL + "', ";
                            strSQL += segActCodSQL + ", ";
                            strSQL += segNueCodSQL + ", ";
                            strSQL += "'" + objParSis.getCodigoUsuario() + "' ";

                            strSQL += "FROM Dynamics2021.dbo.tbh_cliseg ";
                            strSQL += "WHERE tx_ruc = '" + rucSQL + "'; ";

                            System.out.println(strSQL);

                            stm = con.createStatement();
stm.executeUpdate(strSQL);
                            
                            
                            objTblMod.setValueAt(segNueCod,i,INT_TBL_DAT_COD_SEG_ACT);
                            objTblMod.setValueAt(objTblMod.getValueAt(i, INT_TBL_DAT_SEG_NUE),i,INT_TBL_DAT_SEG_ACT);
                            objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                            
                        }
                    }
                }

                con.commit();
                
                strDet.append("</table>");
                if (totalCambios > 0)
                {
                    String tx_usr = obtenerNombreUsuario(con,objParSis.getCodigoUsuario());
                    
                    String strMsg = "<HTML>"
                            + "<b>Usuario:</b> " + tx_usr + "<br>"
                            + "<b>Total clientes modificados:</b> "
                            + totalCambios
                            + "<br><br>"
                            + strDet.toString()
                            + "<br><hr>"
                            + "<span style='font-size:11px; color:#777;'>"
                            + "Este correo ha sido generado automáticamente por el sistema."
                            + "</span>"
                            + "</HTML>";

                    String strSbj ="Zafiro: Actualización de Segmentos";

                    if (objNotCorEle == null)
                    {
                        objNotCorEle = new ZafNotCorEle(objParSis);
                    }

                    objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(
                            objParSis.getCodigoEmpresa(),
                            objParSis.getCodigoLocal(),
                            41,
                            strMsg,
                            strSbj
                    );
                }
                //mostrarMsgInf("Se actualizaron "+ totalCambios+ " clientes.");

                stm.close();
                con.close();

                stm = null;
                con = null;

                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);

                butCon.setText("Consultar");
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes = false;

            try
            {
                con.rollback();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private String obtenerFiltroSegmentos()
    {
        String strSeg = "";

        try
        {
            for (int i = 0; i < objTblModSeg.getRowCountTrue(); i++)
            {
                if (objTblModSeg.isChecked(i, INT_TBL_DAT_SEG_CHK))
                {
                    if (!strSeg.equals(""))
                        strSeg += ",";

                    strSeg += objTblModSeg.getValueAt(i, INT_TBL_DAT_SEG_COD).toString();
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

        return strSeg;
    }
    
    private String obtenerFiltroVendedores()
    {
        String strVen = "";

        try
        {
            for (int i = 0; i < objTblModVen.getRowCountTrue(); i++)
            {
                if (objTblModVen.isChecked(i, INT_TBL_DAT_VEN_CHK))
                {
                    if (!strVen.equals(""))
                        strVen += ",";

                    strVen += objTblModVen.getValueAt(i, INT_TBL_DAT_VEN_COD).toString();
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

        return strVen;
    }
    
    private String obtenerFiltroCanales()
    {
        String strCan = "";

        try
        {
            for (int i = 0; i < objTblModCan.getRowCountTrue(); i++)
            {
                if (objTblModCan.isChecked(i, INT_TBL_DAT_CAN_CHK))
                {
                    if (!strCan.equals(""))
                        strCan += ",";

                    strCan += objTblModCan.getValueAt(i, INT_TBL_DAT_CAN_COD).toString();
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

        return strCan;
    }
    
    
    private boolean cargarComboSegmento()
    {
        boolean blnRes = true;

        try
        {
            Connection con = DriverManager.getConnection(
                    objParSis.getStringConexion_Dynamics2021(),
                    objParSis.getUsuarioBaseDatos_Dynamics2021(),
                    objParSis.getClaveBaseDatos_Dynamics2021());

            Statement stm = con.createStatement();

            strSQL = "";
            strSQL += "SELECT h.co_atrcli, h.tx_nom, p.tx_nom AS segmento ";
            strSQL += "FROM Dynamics2021.dbo.tbm_atrcli h ";
            strSQL += "INNER JOIN Dynamics2021.dbo.tbm_atrcli p ";
            strSQL += "ON h.co_pad = p.co_atrcli ";
            strSQL += "WHERE h.co_pad IS NOT NULL ";
            strSQL += "AND h.st_reg = 'A' ";
            strSQL += "ORDER BY h.tx_nom ";

            System.out.println("cargarComboSegmento: " + strSQL);

            ResultSet rs = stm.executeQuery(strSQL);

            cmbSeg.removeAllItems();
            mapSegmentos.clear();

            while (rs.next()) {

                String cod = rs.getString("co_atrcli");

                String seg = rs.getString("tx_nom")
                        + " - "
                        + rs.getString("segmento");

                cmbSeg.addItem(seg);

                mapSegmentos.put(seg, cod);
            }

            rs.close();
            stm.close();
            con.close();

        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    } 
    
    private void NuevoSegmento()
    {
        if (tabFrm.getSelectedIndex() == 1)
        {
            String nuevoSeg =  cmbSeg.getSelectedItem().toString();

            String nuevoSegCod = mapSegmentos.get(nuevoSeg);

            for (int i = 0; i < tblDat.getRowCount(); i++)
            {
                int modelRow = tblDat.convertRowIndexToModel(i);

                objTblMod.setValueAt(nuevoSeg,modelRow,INT_TBL_DAT_SEG_NUE);

                objTblMod.setValueAt(nuevoSegCod,modelRow,INT_TBL_DAT_COD_SEG_NUE);
            }

            tblDat.repaint();
        }
    }
    
    private String obtenerNombreUsuario(Connection con, int coUsr) {

        String usuario = "";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT tx_usr FROM tbm_usr WHERE co_usr = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, coUsr);

            rs = ps.executeQuery();

            if (rs.next()) {
                usuario = rs.getString("tx_usr");
            }

        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return usuario;
    }    
    
    private int obtenerTotalClientesActualizar()
    {
        int total = 0;

        try
        {
            for (int i = 0; i < tblDat.getRowCount(); i++)
            {
                boolean chk = (Boolean)objTblMod.getValueAt(i,INT_TBL_DAT_CHK);

                String segActCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_ACT).toString();

                String segNueCod = objTblMod.getValueAt(i,INT_TBL_DAT_COD_SEG_NUE).toString();

                if (chk && !segActCod.equals(segNueCod))
                {
                    total++;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }

        return total;
    }
    
    private boolean mostrarVenConCli(int intTipBus) {
        boolean blnRes = true;

        try {
            switch (intTipBus) {

                case 0: // ventana
                    vconCli.setCampoBusqueda(0);
                    vconCli.show();

                    if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {
                        
                        String cod = vconCli.getValueAt(1).trim();
                        String nom = vconCli.getValueAt(2).trim();

                        
                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }
                        
                        txtcodcli.setText(cod);
                        txtNomCli.setText(nom);                
                    }
                    break;

                case 1: // búsqueda por código
                    
                    String codIngresado = txtcodcli.getText().trim();
                    
                    if (vconCli.buscar("CUSTNMBR", txtcodcli.getText())) {
                        
                        String cod = vconCli.getValueAt(1).trim();
                        String nom = vconCli.getValueAt(2).trim();

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        } 
                                               
                        txtcodcli.setText(cod);
                        txtNomCli.setText(nom);
                       
                    } else {

                        vconCli.setCampoBusqueda(0);
                        vconCli.cargarDatos();
                        vconCli.show();

                        if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {

                            String cod = vconCli.getValueAt(1).trim();
                            String nom = vconCli.getValueAt(2).trim();

                            if (cod == null || cod.trim().equals("")) {
                                mostrarMsgInf("Debe seleccionar un cliente.");
                                return false;
                            }

                            txtcodcli.setText(cod);
                            txtNomCli.setText(nom);                            
                        }
                    }
                    break;

                case 2: // búsqueda por nombre
                    if (vconCli.buscar("CUSTNAME", txtNomCli.getText())) {

                        String cod = vconCli.getValueAt(1).trim();
                        String nom = vconCli.getValueAt(2).trim();

                        if (cod == null || cod.trim().equals("")) {
                            mostrarMsgInf("Debe seleccionar un cliente.");
                            return false;
                        }

                        txtcodcli.setText(cod);
                        txtNomCli.setText(nom);                  

                    } else {

                        vconCli.setCampoBusqueda(1);
                        vconCli.cargarDatos();
                        vconCli.show();

                        if (vconCli.getSelectedButton() == vconCli.INT_BUT_ACE) {

                            String cod = vconCli.getValueAt(1).trim();
                            String nom = vconCli.getValueAt(2).trim();

                            if (cod == null || cod.trim().equals("")) {
                                mostrarMsgInf("Debe seleccionar un cliente.");
                                return false;
                            }

                            txtcodcli.setText(cod);
                            txtNomCli.setText(nom.trim());

                        }
                    }
                    break;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }  
    
    private boolean validarFiltrosSeleccionados()
    {
        int i;

        // VALIDAR SOLO SI ESTA FILTRO
        if (optFil.isSelected())
        {
            // SEGMENTOS
            for (i = 0; i < tblLoc.getRowCount(); i++)
            {
                if ((Boolean)objTblModSeg.getValueAt(i, INT_TBL_DAT_SEG_CHK))
                {
                    return true;
                }
            }

            // VENDEDORES
            for (i = 0; i < tblVen.getRowCount(); i++)
            {
                if ((Boolean)objTblModVen.getValueAt(i, INT_TBL_DAT_VEN_CHK))
                {
                    return true;
                }
            }

            // CANALES
            for (i = 0; i < tblCan.getRowCount(); i++)
            {
                if ((Boolean)objTblModCan.getValueAt(i, INT_TBL_DAT_CAN_CHK))
                {
                    return true;
                }
            }

            mostrarMsgInf("Debe seleccionar al menos un registro.");

            return false;
        }

        return true;
    }
      
    
    
}

