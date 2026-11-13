/*
 * ZafMae87.java
 *
 * Created on 19 Agosto , 2013, 8:44 PM
 */
package Maestros.ZafMae87;
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
import Librerias.ZafUtilGP.ZafUtilGP;
import Librerias.ZafVenConGP.ZafVenConItmGP01;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVcoGP.ZafTblCelEdiTxtVcoGP;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 *INT_TBL_DAT_COD_ZAF
 * @author  José Marín 
 */
public class ZafMae87 extends javax.swing.JInternalFrame 
{
    //Constantes
    //Tabla 
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_ITM=1;
    private static final int INT_TBL_DAT_COD_3L=2;
    private static final int INT_TBL_DAT_COD_ZAF=3;
    private static final int INT_TBL_DAT_NOM_ITEM=4;
    private static final int INT_TBL_DAT_STOCK=5;
    private static final int INT_TBL_DAT_CHECK_ANU=6;    
   
    
    private static final int INT_TBL_DAT_LIN2=0;
    private static final int INT_TBL_DAT_CHK_SELECCION=1;
    private static final int INT_TBL_DAT_NOMBRE=2;
    
    private static int INT_ARL_EXC_ITEM=0; 
    private static int INT_ARL_EXC_INAC=1;     
    
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
    private ZafTblCelRenLbl objTblCelRenLblColCheck; 
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
    private boolean blnDetIncial;    
    private boolean blnAgrupa=false;
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
    
  
    
    
    
    private ZafVenConItmGP01 objVenConItm01GP;
    
    private ZafTblMod objTblModTipo;
    private ZafTblCelRenChk objTblCelRenChk;    
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas. 
    private ZafThGuarGUI objThrGuaGUI;

    private String version="v0.8"; 
    private ZafHisTblBasDat objHisTblBasDat;   
    private ZafHisTblBasDatGP objHisTblBasDatGP;   
    private ArrayList arlDatITEMDET,arlRegITEMDET; 
    private ArrayList arlDatITEMGRP,arlRegITEMGRP; 
    private ArrayList arlRegITEMPEDIDO; 
    private static final int INT_ARL_CON_ITEM=0;  
    private static final int INT_ARL_CON_DESC=1;
    private static final int INT_ARL_CON_STOCK=2;
    private static final int INT_ARL_CON_PED=3;
    
    private static final int INT_ARL_GRP_ITEM=0;  
    private static final int INT_ARL_GRP_EST=1;
    private static final int INT_ARL_GRP_GRP=2;    
    String mensajeConf ="",strFecUltMod="";
    private Connection conGP;
    
     
private boolean actualizarDetHis()
    {
        int intNumFil, i;
        int idItemPeso=0;
        boolean validaOk =false;
        boolean blnExisIgual =false;
        boolean blnRes=false,blnResStock=false,blnExisDet=false,blnResStockBin =false;
        String  strSQLZAF ="",datFecAuxZAFe="",strSQLZAFGLO="",strSQLGP="",strSQLGPGLO="";
        int contador =0;
        String codZafiro ="";
        double nd_PrecioVta =0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
////                stm=con.createStatement();
////                stmGP=conGP.createStatement();
                con.setAutoCommit(false);
                conGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
////                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
////                arlDatITEMDET= new ArrayList();

////                    objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
                    //if(InsertarHistorico(conGP,strFecUltMod,objParSis.getNombreUsuario()))
////                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))  
                    if(objHisTblBasDatGP.insertarHistoricoMasivo(conGP, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.tx_usrMod='" + objParSis.getNombreUsuario()+"'"))  
                    {
                         blnRes=true;
                         con.commit();
                        conGP.commit();   
                    }
                    else
                    {
                        blnRes=false;
                        
                    }
                    if (blnDetIncial)
                    {
                        int estadoPre ;
                        String strMsgItmUbi="";
                         strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items: <BR><BR>";
                         strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> DESCRIPCION </td><td> STOCK </td><td> PEDIDO </td>";
                         for (i=0; i<arlDatITEMDET.size();i++) 
                        { 

                                strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_ITEM) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_DESC) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_STOCK) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_PED) + "</td>";
                                strMsgItmUbi+="</tr>";


                        }
                        strMsgItmUbi+="</table><BR>";
                        strMsgItmUbi+="Poseen stock o se encuentran dentro de un PEDIDO <html>";
                        mostrarMsgInf(strMsgItmUbi);
                    }              
                
                
                
////                stm.close();
                con.close();
////                stm=null;
                con=null;
                
////                stmGP.close();
////                stmGP=null;
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

    
    private class ZafThGuarGUI extends Thread
    {
 
        public void run()
        {
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación de "+  mensajeConf +" ?"  )==0)
            {
                lblMsgSis.setText("Guardando");
                if (actualizarDet())
                {
                    if (actualizarDetHis())
                    {
                        lblMsgSis.setText("Listo");
                        pgrSis.setValue(0);
                        butCon.setText("Consultar");
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        butCon.doClick();
                    }

                }

                else
                {
                    
                    if (blnDetIncial)
                    {
                        int i=0;
                        String strMsgItmUbi="";
                         strMsgItmUbi="<HTML>No se pueden guardar los cambios de los siguientes items: <BR><BR>";
                         strMsgItmUbi+="<table BORDER=1><tr><td> ITEM </td> <td> DESCRIPCION </td><td> STOCK </td><td> PEDIDO </td>";
                         for (i=0; i<arlDatITEMDET.size();i++) 
                         { 

                                strMsgItmUbi+="<tr><td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_ITEM) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_DESC) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_STOCK) + "</td>";
                                strMsgItmUbi+="<td>" + objUti.getStringValueAt(arlDatITEMDET, i, INT_ARL_CON_PED) + "</td>";
                                strMsgItmUbi+="</tr>";


                        }
                        strMsgItmUbi+="</table><BR>";
                        strMsgItmUbi+="Poseen stock o se encuentran dentro de un PEDIDO. <html>";
                        mostrarMsgInf(strMsgItmUbi);
                    }
                    else
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

    
    /** Creates new form ZafMae87 */
    public ZafMae87(ZafParSis obj) 
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
            
            objHisTblBasDat=new ZafHisTblBasDat();
            objHisTblBasDatGP=new ZafHisTblBasDatGP();
            
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + version);
            lblTit.setText(strAux);
            
             //ConfigurarCombos
            
        

            
            
            //Configurar los JTables.
            configurarTblDat();

            
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
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
            vecCab.add(INT_TBL_DAT_STOCK,"Stock");
            vecCab.add(INT_TBL_DAT_CHECK_ANU,"A/I");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);  
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//////            objTblMod.setColumnDataType(INT_TBL_DAT_NOM_TAMA, ZafTblMod.INT_COL_DBL, new Integer(0), null);
//////            objTblMod.setColumnDataType(INT_TBL_DAT_NOM_PESO, ZafTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_DAT_STOCK).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CHECK_ANU).setPreferredWidth(50);            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MARC).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_LINEA).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_COM_MAT).setResizable(false);
////            tcmAux.getColumn(INT_TBL_DAT_BUT_CLASE1).setResizable(false);            
////            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHECK_ANU).setCellRenderer(objTblCelRenChk);
            
          vecAux = new Vector();

                vecAux.add("" + INT_TBL_DAT_CHECK_ANU);

            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_3L).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ZAF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITEM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STOCK).setCellRenderer(objTblCelRenLbl);
            //tcmAux.getColumn(INT_TBL_DAT_CHECK_ANU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    double stock  =-1;
                    stock= objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_STOCK));
                   
                     if (stock ==0)
////                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        objTblCelRenLbl.setBackground(new Color(255,255,255));
                    else
                         objTblCelRenLbl.setBackground(new Color(178, 186, 187));
////                     objTblCelRenLblFix.setBackground(java.awt.Color.GREEN);
                        
                }
            });
            
////                  tcmAux.getColumn(INT_TBL_DAT_CHECK_ANU).setCellRenderer(objTblCelRenChk);           
                objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCre;
               
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    int intFil = -1;
                    intFil = objTblCelRenChk.getRowRender();
                    BigDecimal bgdStock;
                    if ((objUti.parseString(objTblMod.getValueAt(intFil,INT_TBL_DAT_LIN)).equals("M")) &&(objTblMod.isChecked(intFil,INT_TBL_DAT_CHECK_ANU)==true))
                    {
                        bgdStock=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (INT_TBL_DAT_STOCK))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (INT_TBL_DAT_STOCK)))));                    
                        if (bgdStock.compareTo(BigDecimal.ZERO)!=0)
                        {
                             colFonColCre=new java.awt.Color(0,240,0);  
                             objTblCelRenChk.setBackground(java.awt.Color.RED);
                        }
                        else
                           objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));     
                    }
                    else
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));  

                    
////                    if (bgdStock==null || bgdStock.compareTo(BigDecimal.ZERO)==0)
////                    {
////                        objTblCelRenLblColCheck.setBackground(javax.swing.UIManager.getColor("Table.background"));
////                    }
////                    else{
////                        objTblCelRenLblColCheck.setBackground(new Color(255,151,151));    
////                    }
      
                }
            });                            

            //botones agregados
           
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
                case INT_TBL_DAT_STOCK: strMsg="Stock"; break;
                case INT_TBL_DAT_CHECK_ANU: strMsg="Inactivo"; break;

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
        chkInac = new javax.swing.JCheckBox();
        chkAct = new javax.swing.JCheckBox();
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
        butexpo = new javax.swing.JButton();
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
        lblTit.setText("Activacion/Inactivacion Masiva de items.....");
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

        chkInac.setSelected(true);
        chkInac.setText("Inactivar Items");
        chkInac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkInacActionPerformed(evt);
            }
        });
        panFil.add(chkInac);
        chkInac.setBounds(20, 120, 320, 16);

        chkAct.setActionCommand("Activar Items");
        chkAct.setLabel("Activar Items");
        chkAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActActionPerformed(evt);
            }
        });
        panFil.add(chkAct);
        chkAct.setBounds(20, 100, 320, 16);

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

        butexpo.setText("Cargar Archivo");
        butexpo.setToolTipText("Exporta informacion desde un archivo excel");
        butexpo.setPreferredSize(new java.awt.Dimension(110, 25));
        butexpo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butexpoActionPerformed(evt);
            }
        });
        panBot.add(butexpo);

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
                objThrGUI=new ZafMae87.ZafThreadGUI();
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

    private void butexpoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butexpoActionPerformed
        // TODO add your handling code here:
             try{
                if(cargarArchivoXLS()){

                }

            }
            catch(Exception e)
            {  
               objUti.mostrarMsgErr_F1(this, e);
            }                
    }//GEN-LAST:event_butexpoActionPerformed

    private void chkInacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkInacActionPerformed
        if (chkInac.isSelected())
        {
////         optFil.setSelected(true);
         objTblMod.removeAllRows();
         chkAct.setSelected(false);
        }
        else
        {
           objTblMod.removeAllRows();
           chkAct.setSelected(true); 
        }
            
        
    }//GEN-LAST:event_chkInacActionPerformed

    private void chkActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActActionPerformed
        // TODO add your handling code here:
        if (chkAct.isSelected())
        {
            objTblMod.removeAllRows();
            chkInac.setSelected(false);
        }
        else
        {
            objTblMod.removeAllRows();
            chkInac.setSelected(true);
        }    
            
        
        
    }//GEN-LAST:event_chkActActionPerformed
    
    private String strSql="";
    
    /**
     * 
     * @return 
     */
    private boolean validarDatosCargados(){
        boolean blnRes=true;
        String strCodItmDynBus="";
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int intCuentaItemsEncontrados=0;
        try{
            arlDatExcelVal=new ArrayList();
            for(int i=0;i<arlDatExcel.size();i++){
                strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM)+"'";
                if(i+1<arlDatExcel.size()) {
                    strCodItmDynBus+=",";
                }
                arlRegExcelVal=new ArrayList();
                arlRegExcelVal.add(INT_EXC_COD_ITM, objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM));
                arlRegExcelVal.add(INT_EXC_BLN, "N");
                arlDatExcelVal.add(arlRegExcelVal);
            }
            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT TRIM(a1.ITEMNMBR) as ITEMNMBR, TRIM(a1.ITEMDESC) as ITEMDESC, TRIM(a1.ITMSHNAM) as ITMSHNAM, TRIM(ITMGEDSC) as ITMGEDSC, a1.ITEMTYPE  "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 as a1 "
                        + " WHERE a1.ITMGEDSC IN ( "+strCodItmDynBus+" ) OR a1.ITEMNMBR IN ( "+strCodItmDynBus+" )"  ;
//                System.out.println("obtenerDatosGP: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    //Valida que encuentres el mismo numero de registros que los que te pusieron en el excel
                    intCuentaItemsEncontrados++;
                    /* Si agruegue un campo en el Array, debo actualizar los encontrados y despues mando un listado con los que faltaron */
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(rstLoc.getString("ITMGEDSC").equals(objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_ITEM))){
                            objUti.setStringValueAt(arlDatExcelVal, x, INT_EXC_BLN, "OK");
                        }
//                        else{
//                            objUti.setStringValueAt(arlDatExcelVal, x,INT_EXC_BLN, "NO");
//                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                
                if(intCuentaItemsEncontrados!=intNumeroItemsProcesar){
                    blnRes=false;
                    
                }
                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
    private boolean ingresarDatosCargados(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strUpdateItems="", strFecUltMod;
        BigDecimal  cantMaxEx ,cantMinEx,canProEx ;
        String cod_Ex="",cod_Tb="";
////        try{
////            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            if(conLoc!=null){
////                conLoc.setAutoCommit(false);
////                stmLoc = conLoc.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
              
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                for(int i=0;i<arlDatExcel.size();i++)
                {
                    cod_Ex =objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM).toString();
                    for(int j=0; j<objTblMod.getRowCountTrue();j++)
                    {
                          cod_Tb =objTblMod.getValueAt(j, INT_TBL_DAT_COD_3L).toString();      
                          if(cod_Tb.equals(cod_Ex))
                          { 
                              objTblMod.setValueAt(true, j, INT_TBL_DAT_CHECK_ANU);
                               
                              
                              
                              
                              
                              break; 
                          }
                    }
                    
                    
////                
                }
////                stmLoc.executeUpdate(strUpdateItems);
////                //Historico de inventario
////                if(!objHisTblBasDat.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())){
////                    blnRes=false;
////                }
                
                if(blnRes){
                    System.out.println("OK! ");
////                    conLoc.commit();
                }
                else{
////                    conLoc.rollback();
                }
                
                
////                stmLoc.close();
////                stmLoc=null;
////                conLoc.close();
////                conLoc=null;
////            }
////            else{
////                blnRes=false;
////            }
////        }
////        catch(java.sql.SQLException e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        catch(Exception e){
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
        return blnRes;
     }        
    boolean blnNoSeleccion=true;
    private boolean cargarArchivoXLS(){
        boolean blnRes=true;
        String strAux;
        try{          
            if (getArchivoExcel() == true){
                if(validarDatosCargados()){
                    if(ingresarDatosCargados()){
                        mostrarMsgInf("La carga del archivo fue exitosa.");
                    }
                    else{
                        blnRes=false;
                        mostrarMsgInf("No se puedo guardar los datos...");
                    }
                }
                else{
                    strAux =  "Existen Items en el documento que no son encontrados en el Sistema." + "\n\n";
                    strAux += "Notifique esta novedad al Administrador del sistema. \n";
                    for(int x=0;x<arlDatExcelVal.size();x++){
                        if(objUti.getStringValueAt(arlDatExcelVal, x, INT_EXC_BLN).equals("N")){
                            strAux+=" "+objUti.getStringValueAt(arlDatExcelVal, x, INT_ARL_EXC_ITEM) + " - "; 
                        }
                    }
                    blnRes=false;
                    mostrarMsgInf(strAux);
                }
            }
            else{  
                
                if(blnNoSeleccion){
                    blnRes=false;
                }
                else{
                    if (blnCargarArchivo == false){   
                        strAux =  "La carga del archivo no fue exitosa." + "\n\n";
                        strAux += "Notifique esta novedad al Administrador del sistema.";
                        mostrarMsgInf(strAux);
                    }
                }
            }
                 
        }
         
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
     }
    private boolean blnCargarArchivo;      //Var. booleana que indica si se presiono el Boton <Cancelar> en la ventana de Carga de Archivo de Retenciones.
    private ArrayList arlDatExcel, arlRegExcel;
    private ArrayList arlDatExcelVal, arlRegExcelVal;
    
    private int intNumeroItemsProcesar=0;
    private int INT_EXC_COD_ITM=0;
    private int INT_EXC_BLN=1;
    
    private boolean getArchivoExcel(){
        boolean blnRes = true;
        try{  
            blnCargarArchivo = false;
            intNumeroItemsProcesar=0;
            arlDatExcel = new ArrayList();
            JFileChooser objfileChooser = new JFileChooser();
            objfileChooser.setDialogTitle("Seleccionar archivo Excel Formato (XLS)");
            objfileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Excel", "xls"/*, "xlsx"*/));

            int userSelection = objfileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                blnNoSeleccion=false;
                File selectedFile = objfileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                // Aquí puedes hacer lo que necesites con el archivo Excel seleccionado
//                System.out.println("Archivo seleccionado: " + filePath);
                blnCargarArchivo=true;

                Workbook workbook = Workbook.getWorkbook(new File(filePath));

                // Leer la primera hoja del libro
                Sheet sheet = workbook.getSheet(0);

                // Obtener el número de filas y columnas en la hoja
                int numRows = sheet.getRows();
                int numCols = sheet.getColumns();

                arlDatExcel=new ArrayList();
                // Iterar sobre las filas de la hoja
                for (int i = 1; i < numRows; i++) {
                    // Iterar sobre las columnas de la fila actual
                    arlRegExcel=new ArrayList();

                    for (int j = 0; j < numCols; j++) {
                        Cell cell = sheet.getCell(j, i);
                        String cellValue = cell.getContents();
                        arlRegExcel.add(cellValue);
                        System.out.print(cellValue + "\t");
                    }
                    intNumeroItemsProcesar++;
                    arlDatExcel.add(arlRegExcel);
                    System.out.println();
                }
//                System.out.println("arlDatExcel<<<" + arlDatExcel.toString());
                workbook.close();


            }
            else{
                blnRes=false;
                mostrarMsgInf("No se seleccionó ningun archivo...");
            }
        } 
        catch (IOException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){  
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butexpo;
    private javax.swing.JCheckBox chkAct;
    private javax.swing.JCheckBox chkInac;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
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
                
                strSQL="";
                if (chkInac.isSelected())
                {
                    mensajeConf ="INACTIVAR";
                    strSQL="  SELECT trim(a1.ITEMNMBR) as tx_coItm,trim(a1.ITMGEDSC) as tx_tresL, trim(a1.ITMSHNAM) as tx_codZAF, trim(a1.ITEMDESC) as tx_nomItm, ";
                    strSQL+="         isnull((tra.QTYONHND),0) as disponible   --    isnull((tra.disponible + tra.transito),0) as disponible   \n";
                    strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as a1 (NOLOCK) \n";  
                    strSQL+="   LEFT JOIN "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as tra (NOLOCK) on (a1.ITEMNMBR = tra.ITEMNMBR)\n";
                    strSQL+=" WHERE a1.ITEMNMBR not like '%-L' and tra.RCRDTYPE=1  and a1.INACTIVE =0\n";               
                    strSQL+=  strAux;
                    strSQL+=" ORDER BY   a1.ITMSHNAM asc" ;                       
                }
                if (chkAct.isSelected())
                {
                    mensajeConf ="ACTIVAR";
                    strSQL="  SELECT trim(a1.ITEMNMBR) as tx_coItm,trim(a1.ITMGEDSC) as tx_tresL, trim(a1.ITMSHNAM) as tx_codZAF, trim(a1.ITEMDESC) as tx_nomItm, ";
                    strSQL+="         isnull((tra.QTYONHND),0) as disponible   --    isnull((tra.disponible + tra.transito),0) as disponible   \n";
                    strSQL+=" FROM "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as a1 (NOLOCK)  \n";  
                    strSQL+="   LEFT JOIN "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 as tra (NOLOCK) on (a1.ITEMNMBR = tra.ITEMNMBR)\n";
                    strSQL+=" WHERE a1.ITEMNMBR not like '%-L' and tra.RCRDTYPE=1  and a1.INACTIVE =1 \n";               
                    strSQL+=  strAux;
                    strSQL+=" ORDER BY   a1.ITMSHNAM asc" ; 
                }   
  
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
                        vecReg.add(INT_TBL_DAT_STOCK, rst.getDouble("disponible"));
                        vecReg.add(INT_TBL_DAT_CHECK_ANU, false);
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
    
    private boolean ValidaStock(String item)//chiqui
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="";
        boolean blnRes=false,blnIngreso =false;
        double  preResul=0;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT  ISNULL(QTYONHND,0) AS stock \n";
              strSqlZa+=" FROM   "+ objParSis.getNombreBaseDatosGP()  +"..IV00102 \n";
              strSqlZa+=" WHERE  ITEMNMBR="+ item +"  \n";
              strSqlZa+="       AND   RCRDTYPE=1   \n";              
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              if(rstLoc1.next())
              {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    preResul= rstLoc1.getDouble("stock");
                    if (preResul==0)
                        blnRes=true;
                    else
                        blnRes=false;
                    blnIngreso =true;
              }
              if (!blnIngreso)
                   blnRes=false;

              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
           blnRes=false;
         }
        return blnRes;
    }
    
    private boolean ValidaStockBin(String item)//chiqui
    {
        java.sql.Connection conLoc1;
        java.sql.Statement stmLoc1;
        java.sql.ResultSet rstLoc1;    

        String strSqlZa="";
        boolean blnRes=false,blnIngreso =false;
        double  preResul=0;
        try
        {
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
            if(conLoc1!=null)
            {
              stmLoc1=conLoc1.createStatement();
              strSqlZa="";
              strSqlZa+=" SELECT  ISNULL(QUANTITY,0) AS stock \n";
              strSqlZa+=" FROM   "+ objParSis.getNombreBaseDatosGP()  +"..IV00112 \n";
              strSqlZa+=" WHERE  ITEMNMBR="+ item +"  \n";
              rstLoc1 = stmLoc1.executeQuery(strSqlZa);
              if(rstLoc1.next())
              {
                /// PARA GUARDAR LOS ITEMS EN UN ARREGLO , DATOS DE ZAFIRO
                    preResul= rstLoc1.getDouble("stock");
                    if (preResul==0)
                        blnRes=true;
                    else
                        blnRes=false;
                    blnIngreso =true;
              }
              if (!blnIngreso)
                   blnRes=false;

              stmLoc1.close();
              stmLoc1=null;
              rstLoc1.close();
              rstLoc1=null;
            }
              conLoc1.close();
              conLoc1=null; 
        }
        catch(java.sql.SQLException e){ 
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
            blnRes=false;
         }
         catch (Exception e) {
            System.err.println("OCURRIO UN ERROR ValidaStock "+ e );
           blnRes=false;
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
        int idItemPeso=0;
        boolean validaOk =false;
        boolean blnExisIgual =false;
        boolean blnRes=false,blnResStock=false,blnResStockBin =false;
        String  strSQLZAF ="",datFecAuxZAFe="",strSQLZAFGLO="",strSQLGP="",strSQLGPGLO="",itemPedido="";
        int contador =0;
        String codZafiro ="";
        double nd_PrecioVta =0;
        String strValidaGrupo="";
        blnDetIncial=false;
        blnAgrupa=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
////            conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
////                stmGP=conGP.createStatement();
////                con.setAutoCommit(false);
////                conGP.setAutoCommit(false);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
                arlDatITEMDET= new ArrayList();
                
                for (i=0; i<intNumFil;i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        if (chkInac.isSelected())
                        {
                            arlRegITEMPEDIDO = objUti.getPedidosEnTransito(objParSis, con);
                            if ((!objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM).equals("")) && (!objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF).equals(""))  && (objTblMod.isChecked(i,INT_TBL_DAT_CHECK_ANU)==true))
                            {
                                 blnResStock=ValidaStock(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)));
                                 blnResStockBin =ValidaStockBin(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM))); 
                                 itemPedido=objUti.getItemInactivoPedido(objParSis, con, objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString(), arlRegITEMPEDIDO);
                                 strValidaGrupo= objUtiGP.validacionItemAgrupacion(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)));
   
                                 
                                if (itemPedido.equals(""))
                                {
                                    if( (blnResStock)&& (objTblMod.isChecked(i,INT_TBL_DAT_CHECK_ANU)==true))
                                    {
                                        
                                         
                                         strSQLGP +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set INACTIVE = 1 \n";
                                         strSQLGP+= "       ,DEX_ROW_TS ='"+strFecUltMod +"'\n";
                                         strSQLGP+= "WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";

                                         strSQLGP +="\n UPDATE Dynamics2021..tbm_inv set st_reg ='I' \n";
                                         strSQLGP+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' \n";
                                         strSQLGP+= "WHERE tx_itm ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      

                                         strSQLGP +="\n DELETE "+ objParSis.getNombreBaseDatosGP()  +"..IV00112  \n";
                                         strSQLGP+= "   WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      


                                         strSQLZAF += "\n UPDATE tbm_inv set st_reg ='I' \n";
                                         strSQLZAF += "    ,fe_ultmod = '"+strFecUltMod +"' , co_usrmod ="+ objParSis.getCodigoUsuario() +"";
                                         strSQLZAF += " WHERE tx_codalt2 ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_3L))+";";


                                         blnRes=true;   

                                         if (blnRes)
                                         {
                                             if (!strValidaGrupo.equals (""))
                                             {
                                                String [] columnasAct = strValidaGrupo.toString().split("-");
                                                if (columnasAct[1].equals("CABECERA"))
                                                    objUtiGP.anulacionAgrupacionCabecera(Integer.parseInt(columnasAct[0]) ,strFecUltMod);
                                                else
                                                {
                                                    if (columnasAct[1].equals("UNICO"))
                                                        objUtiGP.anulacionAgrupacionSoloItem(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) ,strFecUltMod);
                                                    else
                                                    {
                                                        if (columnasAct[1].equals("ULTIMO"))
                                                            objUtiGP.anulacionAgrupacionItemUltimo(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM))  ,strFecUltMod);
                                                            
                                                    }
                                                        
                                                }
                                             }                                             
                                         }
                                         contador ++;
                                         stm.executeUpdate(strSQLZAF);
                                         actualizarGP(strSQLGP);   
                                         
                                    }
                                     else
                                     {
                                        arlRegITEMDET = new ArrayList();
                                        arlRegITEMDET.add(INT_ARL_CON_ITEM,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_DESC,objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_STOCK,objTblMod.getValueAt(i,INT_TBL_DAT_STOCK).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_PED,"");
                                        arlDatITEMDET.add(arlRegITEMDET);
                                        blnDetIncial=true;                                 
                                     }
                                } 
                                else
                                {
                                        arlRegITEMDET = new ArrayList();
                                        arlRegITEMDET.add(INT_ARL_CON_ITEM,objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_DESC,objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_STOCK,objTblMod.getValueAt(i,INT_TBL_DAT_STOCK).toString());
                                        arlRegITEMDET.add(INT_ARL_CON_PED,itemPedido);
                                        arlDatITEMDET.add(arlRegITEMDET);
                                        blnDetIncial=true;
                                }    

                                
                                
                            }    
                         
                       } 
                       else
                       {
                          if (chkAct.isSelected())
                          { 
                              strSQLGP="";
                              strSQLZAF="";
                             if ((!objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITEM).equals("")) && (!objTblMod.getValueAt(i,INT_TBL_DAT_COD_ZAF).equals(""))  && (objTblMod.isChecked(i,INT_TBL_DAT_CHECK_ANU)==true))
                             {
////                                blnResStock=ValidaStock(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)));
                                 blnResStock=false;
                                if( (!blnResStock)&& (objTblMod.isChecked(i,INT_TBL_DAT_CHECK_ANU)==true))
                                {
                                     strSQLGP +=" UPDATE "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 set INACTIVE = 0 \n";
                                     strSQLGP+= "       ,DEX_ROW_TS ='"+strFecUltMod +"'\n";
                                     strSQLGP+= "WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";

                                     strSQLGP +="\n UPDATE Dynamics2021..tbm_inv set st_reg ='A' \n";
                                     strSQLGP+= "       ,tx_usrmod ='"+ objParSis.getNombreUsuario()+"', fe_ultmod ='"+ strFecUltMod +"' \n";
                                     strSQLGP+= "WHERE tx_itm ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      

////                                     strSQLGP +="\n DELETE "+ objParSis.getNombreBaseDatosGP()  +"..IV00112  \n";
////                                     strSQLGP+= "   WHERE ITEMNMBR ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM)) +" ;\n";      


                                     strSQLZAF += "\n UPDATE tbm_inv set st_reg ='A' \n";
                                     strSQLZAF += "    ,fe_ultmod = '"+strFecUltMod +"' , co_usrmod ="+ objParSis.getCodigoUsuario() +"";
                                     strSQLZAF += " WHERE tx_codalt2 ="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_3L))+";"; 
                                     contador ++;
                                     if (objUtiGP.creacionActualizarItem(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM).toString(), 1))
                                     {
                                        stm.executeUpdate(strSQLZAF);
                                        actualizarGP(strSQLGP);
                                        blnRes=true; 
                                        
                                                      
                                     }
                                     else
                                         blnRes=false; 
                                     
                                     
                                     
                                     
                                     
                                }                                
                                
                             }    
                          }    
                       }
                    }
                }
                
////                if ((blnRes) || (contador>0))
////                {
////                    con.commit();
////                }
////                else
////                {
////                    con.rollback();
//////                    conGP.rollback();                    
////                }
////         
                
                
                
                stm.close();
                con.close();
                stm=null;
                con=null;

                
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
    
   
    
private boolean actualizarGP (String sql)
    {
        java.sql.Statement stmGP1;
        java.sql.Connection conGP1;
        java.sql.ResultSet rstGP;  
        java.sql.CallableStatement cal;
        String strSQLIn ="";
        int intCodRes=-1;
        boolean blnRes=true;
 
        
        try
        {
         conGP1 = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());    
           if (conGP1!=null)
           {
//           
                stmGP1=conGP1.createStatement();
                
                strSQLIn ="";
                strSQLIn += sql;
                System.out.println("CONEXIONES " + strSQLIn);
                stmGP1.executeUpdate(strSQLIn);
                
                stmGP1.close();
                stmGP1=null;
                
                
                conGP1.close();
                conGP1=null;
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
    private boolean InsertarHistorico (java.sql.Connection conEXT,String fecha,String usuario)
    {
        
        
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        int intNumSeg=0;
        String ndItemGp ="",strSQLoc;
        try
        {
            if (conEXT!=null)
            {

                stmLoc=conEXT.createStatement();
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
    
}

