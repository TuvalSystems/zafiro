/*
 * ZafCom92.java
 *
 * Created on 19 Enero , 2016, 8:44 AM
 */
package Compras.ZafCom92;
import Librerias.ZafCfgBod.ZafCfgBod;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafGenFacAut.ZafGenFacAutAdm;
import Librerias.ZafImp.ZafImp; 
import Librerias.ZafMovIngEgrInv.ZafReaMovInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafWebSer.ZafWebSer;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author  José Marín 
 */
public class ZafCom92 extends javax.swing.JInternalFrame
{
       //Constantes: JTable
    private final int INT_TBL_DAT_LIN=0;
    
    private final int INT_TBL_DAT_COD_TIP_DOC=1;//co_motTra
    private final int INT_TBL_DAT_COD_DOC=2;//co_solTraInv
    private final int INT_TBL_DAT_NUM_DOC=3;
    private final int INT_TBL_DAT_FEC_DOC=4;
    private final int INT_TBL_DAT_BOD_OR=5;
    private final int INT_TBL_DAT_BOD_DES=6;
    private final int INT_TBL_DAT_NUM_MAX=7;
//    private final int INT_TBL_DAT_FEC_DOC1=8;
    private final int INT_TBL_DAT_CON_SOL_TRA=8;
    private final int INT_TBL_DAT_CHK_AUT=9;
    private final int INT_TBL_DAT_CHK_DEN=10;
    private final int INT_TBL_DAT_OBS_AUT=11;
    private final int INT_TBL_DAT_CON_OBS=12;
    
    private final int INT_TBL_DAT_TRA_LIN=0;    
    private final int INT_TBL_DAT_TRA_COD_DOC=1;
    private final int INT_TBL_DAT_TRA_NUM_DOC=2;
    private final int INT_TBL_DAT_TRA_BOD_ORI=3;    
    private final int INT_TBL_DAT_TRA_BOD_DES=4;    
    private final int INT_TBL_DAT_TRA_USR_ING=5;    
    private final int INT_TBL_DAT_TRA_FEC_ING=6;    
    private final int INT_TBL_DAT_TRA_USR_AUT=7;    
    private final int INT_TBL_DAT_TRA_FEC_AUT=8;    
    private final int INT_TBL_DAT_TRA_MAX_ITM=9;        
    private final int INT_TBL_DAT_TRA_CHK_AUT=10;    
    private final int INT_TBL_DAT_TRA_COD_TIP_DOC=11;   
    
    private final int INT_TBL_DAT_ELI_LIN=0;
    private final int INT_TBL_DAT_ELI_NUM_DOC=1;
    private final int INT_TBL_DAT_ELI_BOD_ORI=2;
    private final int INT_TBL_DAT_ELI_BOD_DES=3;
    private final int INT_TBL_DAT_ELI_FEC_CRE=4;
    private final int INT_TBL_DAT_ELI_OBS=5;
    private final int INT_TBL_DAT_ELI_BTN=6;
    private final int INT_TBL_DAT_ELI_MAX_ITM=7;
    private final int INT_TBL_DAT_ELI_CHK=8;
    

    
    
  
 
    
    
    /* CONSTANTES PARA CONTENEDOR A ENVIAR JoséMario 11/Marzo/2016  */
    final int INT_ARL_COD_EMP=0;
    final int INT_ARL_COD_LOC=1;
    final int INT_ARL_COD_TIP_DOC=2;
    final int INT_ARL_COD_BOD_GRP=3;  // TRABAJA POR GRUPO!!!! 
    final int INT_ARL_COD_ITM=4;
    final int INT_ARL_COD_ITM_MAE=5;
    final int INT_ARL_COD_BOD=6;
    final int INT_ARL_NOM_BOD=7;
    final int INT_ARL_CAN_COM=8;
    final int INT_ARL_CHK_CLI_RET=9;
    final int INT_ARL_EST_BOD=10;
    final int INT_ARL_ING_EGR_FIS_BOD=11;
    final int INT_ARL_COD_BOD_GRP_ING=12;  /// TRABAJA POR GRUPO !!!
    
    //Constantes: Para el contenedor a enviar solicitud de Cotizaciones de Venta JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=3;
    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    
    //Códigos de configuración.

    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;                                    //Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModEli;
    private ZafTblMod objTblModTra;
    private ZafColNumerada objColNum;
    private ZafColNumerada objColNumTra;    
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblPopMnu objTblPopMnuEli;
    private ZafTblPopMnu objTblPopMnuTra;    
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAda objMouMotAdaEli;
    private ZafMouMotAda objMouMotAdaTra;
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButDG1;//Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButEli, objTblCelRenButDG1Eli;//Render: Presentar JButton en JTable.    
    private ZafTblOrd objTblOrd;
    private ZafTblOrd objTblOrdEli;
    private ZafTblOrd objTblOrdTra;    
    private ZafTblBus objTblBus;
    private ZafTblBus objTblBusEli;
    private ZafTblBus objTblBusTra;
    private ZafThreadGUI objThrGUI;
    private ZafThrGua objThrGua; 
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg;        //Editor: JButton en celda (Bodega origen).
    private ZafTblCelEdiButVco objTblCelEdiButVcoBodOrg2;       //Editor: JButton en celda (Bodega origen).    
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblEli;                    //Render: Presentar JLabel en JTable.    
    private ZafTblCelRenLbl objTblCelRenLblTra;                    //Render: Presentar JLabel en JTable.    
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblNumEli;                 //Render: Presentar JLabel en JTable (Números).    
    private ZafTblCelRenLbl objTblCelRenLblNumTra;                 //Render: Presentar JLabel en JTable (Números).    
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCodEli;                 //Render: Presentar JLabel en JTable (Números).    
    private ZafTblCelRenLbl objTblCelRenLblCodTra;                 //Render: Presentar JLabel en JTable (Números).    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelRenChk objTblCelRenChkEli;
    private ZafTblCelRenChk objTblCelRenChkTra;    
    private ZafTblCelEdiChk objTblCelEdiChk; 
    private ZafTblCelEdiChk objTblCelEdiChkTra;     
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblModLis objTblModLisEli;                          //Detectar cambios de valores en las celdas.    
    private ZafTblModLis objTblModLisTra;                          //Detectar cambios de valores en las celdas.    
    private ZafTblCelEdiButGen objTblCelEdiButGenDG1;
    private ZafTblFilCab objTblFilCab;
    private ZafTblFilCab objTblFilCabEli;
    private ZafTblFilCab objTblFilCabTra;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblEdi objTblEdiEli;                                //Editor: Editor del JTable.    
    private ZafTblEdi objTblEdiTra;                                //Editor: Editor del JTable.    
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private ZafDocLis objDocLisEli;
    private ZafDocLis objDocLisTra;
    private ZafTblCelRenBut objTblCelRenButConGre, objTblCelRenButConOcp;  


    
    private java.util.Date datFecAux;                           //Auxiliar: Para almacenar fechas.
    private java.awt.Component componente;
    private java.awt.Frame Frame;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private Vector vecDatTra, vecRegTra, vecCabTra, vecAuxTra;    
    private Vector vecDatEli, vecRegEli, vecCabEli, vecAuxEli;      
    
    ArrayList arlDat = new ArrayList();
    ArrayList arlReg = new ArrayList();
    
 
    
  
       
    private boolean blnHayCam;

    private boolean blnCon;                                     //true: Continua la ejecución del hilo. // Continuidad del hilo
    

  
     
    private String strSQL, strAux, strNomBodSolTra;
    
    private String strVersion="v1.2";  
    private String strMoTrans="";

    private int intNumSol=0;
    private String strNumModGlo="";
    private int ne_tieespe=-1;
    private String fe_ultmod;   
    ArrayList<HashMap<String, Object>> listaReservas = new ArrayList<>();
    ArrayList<HashMap<String, Object>> listaReservasRepr = new ArrayList<>();
    ArrayList<HashMap<String, Object>> listaUbicacion = new ArrayList<>();    
    ArrayList<HashMap<String, Object>> listaUbicacionRepr = new ArrayList<>();
    private String strBodegaGlob ="";
    private String strBodegaGlobRepr ="";    
    private String strCadeItemsGlo="";    
    private String strCadeItemsGloRepr="";    
    private ZafWebSer objWeb;  
    
private void configurarFrmPen()
{
        try{
            if(!objPerUsr.isOpcionEnabled(3995)){
                butGua.setVisible(false);
            }
            //chiquitabla
            //Configurar JTable: Establecer el modelo.
            vecDatTra=new Vector();    //Almacena los datos
            vecCabTra=new Vector();   //Almacena las cabeceras
            vecCabTra.clear();
             //chiqui
             
            vecCabTra.add(INT_TBL_DAT_TRA_LIN,"");
            vecCabTra.add(INT_TBL_DAT_TRA_COD_DOC,"Cód.Doc.");
            vecCabTra.add(INT_TBL_DAT_TRA_NUM_DOC,"Num.Doc.");
            vecCabTra.add(INT_TBL_DAT_TRA_BOD_ORI,"Bod.Ori.");
            vecCabTra.add(INT_TBL_DAT_TRA_BOD_DES,"Bod.Des.");
            vecCabTra.add(INT_TBL_DAT_TRA_USR_ING,"Usr.Ing.");
            vecCabTra.add(INT_TBL_DAT_TRA_FEC_ING,"Fec.Ing.");
            vecCabTra.add(INT_TBL_DAT_TRA_USR_AUT,"Usr.Aut.");            
            vecCabTra.add(INT_TBL_DAT_TRA_FEC_AUT,"Fec.Aut.");
            vecCabTra.add(INT_TBL_DAT_TRA_MAX_ITM,"Max.Itm.");
            vecCabTra.add(INT_TBL_DAT_TRA_CHK_AUT,"✔"); 
            vecCabTra.add(INT_TBL_DAT_TRA_COD_TIP_DOC,"Tip.Doc.");
            
           
            objTblModTra=new ZafTblMod();
            objTblModTra.setHeader(vecCabTra);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatTra.setModel(objTblModTra);            
            //Configurar JTable: Establecer tipo de selección.
            tblDatTra.setCellSelectionEnabled(true);
            //tblDatTra.setRowSelectionAllowed(true);
            tblDatTra.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTra=new ZafColNumerada(tblDatTra,INT_TBL_DAT_TRA_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuTra=new ZafTblPopMnu(tblDatTra);
            objTblPopMnuTra.setPegarEnabled(true);
            objTblPopMnuTra.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatTra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAuxTra=tblDatTra.getColumnModel();
            //CHIQUI
            
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_LIN).setPreferredWidth(30);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_COD_DOC).setPreferredWidth(60);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_NUM_DOC).setPreferredWidth(60);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_BOD_ORI).setPreferredWidth(80);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_BOD_DES).setPreferredWidth(80);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_USR_ING).setPreferredWidth(0);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_FEC_ING).setPreferredWidth(100);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_USR_AUT).setPreferredWidth(80);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_FEC_AUT).setPreferredWidth(100);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_MAX_ITM).setPreferredWidth(60);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_CHK_AUT).setPreferredWidth(60);            
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_COD_TIP_DOC).setPreferredWidth(60); 

            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_CHK_AUT).setResizable(false);
            
            objTblCelRenLblNumTra=new ZafTblCelRenLbl();
            objTblCelRenLblNumTra.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumTra.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNumTra.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            objTblCelRenLblCodTra=new ZafTblCelRenLbl();
            objTblCelRenLblCodTra.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //CHIQUI
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_COD_DOC).setCellRenderer(objTblCelRenLblCodTra);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_NUM_DOC).setCellRenderer(objTblCelRenLblCodTra);
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_BOD_ORI).setCellRenderer(objTblCelRenLblCodTra);
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_BOD_DES).setCellRenderer(objTblCelRenLblCodTra);            
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_USR_ING).setCellRenderer(objTblCelRenLblCodTra);
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_FEC_ING).setCellRenderer(objTblCelRenLblCodTra);
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_USR_AUT).setCellRenderer(objTblCelRenLblCodTra);            
            //tcmAuxTra.getColumn(INT_TBL_DAT_TRA_FEC_AUT).setCellRenderer(objTblCelRenLblCodTra);                        
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_MAX_ITM).setCellRenderer(objTblCelRenLblCodTra);
            

            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatTra.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaTra=new ZafMouMotAda();
            tblDatTra.getTableHeader().addMouseMotionListener(objMouMotAdaTra);
            //Configurar JTable: Establecer columnas editables.
            vecAuxTra=new Vector();
            vecAuxTra.add("" + INT_TBL_DAT_TRA_CHK_AUT);
            objTblModTra.setColumnasEditables(vecAuxTra);
            vecAuxTra=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiTra=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTra=new ZafTblCelRenLbl();
           //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            //tcmAuxTra.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblTra);
            //cmAuxTra.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblTra);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            

            objTblCelRenLblTra=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTra=new ZafTblFilCab(tblDatTra);
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_LIN).setCellRenderer(objTblFilCabTra);
            objTblFilCabTra=null;
            objTblCelRenChkTra=new ZafTblCelRenChk();
            tcmAuxTra.getColumn(INT_TBL_DAT_TRA_CHK_AUT).setCellRenderer(objTblCelRenChkTra);
            
            objTblModTra.setModoOperacion(objTblModTra.INT_TBL_EDI);
            objTblOrdTra=new ZafTblOrd(tblDatTra);
            objTblBusTra=new ZafTblBus(tblDatTra);
            objDocLisTra=new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLisTra=new ZafTblModLis();
            objTblModTra.addTableModelListener(objTblModLisTra);           
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    
}

private void configurarFrmEliItm()//19052026
{
        try{
            
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnAdmin.setVisible(false);
                btnAdmin.setEnabled(false);
            }
            
//            3995;2078;"0";"Guardar"
            
            if(!objPerUsr.isOpcionEnabled(3995)){
                butGua.setVisible(false);
            }
            //chiquitabla
            //Configurar JTable: Establecer el modelo.
            vecDatEli=new Vector();    //Almacena los datos
            vecCabEli=new Vector();   //Almacena las cabeceras
            vecCabEli.clear();
             //chiqui
            vecCabEli.add(INT_TBL_DAT_ELI_LIN,"");
//            vecCabEli.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
//            vecCabEli.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCabEli.add(INT_TBL_DAT_ELI_NUM_DOC,"Num.Doc.");
//            vecCabEli.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
//            vecCabEli.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCabEli.add(INT_TBL_DAT_ELI_BOD_ORI,"Bod.Ori.");
            vecCabEli.add(INT_TBL_DAT_ELI_BOD_DES,"Bod.Des.");
            vecCabEli.add(INT_TBL_DAT_ELI_FEC_CRE,"Fec.Doc.");
            vecCabEli.add(INT_TBL_DAT_ELI_OBS,"Obs.");
            vecCabEli.add(INT_TBL_DAT_ELI_BTN,"..");
            vecCabEli.add(INT_TBL_DAT_ELI_MAX_ITM,"Max");
            vecCabEli.add(INT_TBL_DAT_ELI_CHK,"✔");
            objTblModEli=new ZafTblMod();
            objTblModEli.setHeader(vecCabEli);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatEli.setModel(objTblModEli);            
            //Configurar JTable: Establecer tipo de selección.
            tblDatEli.setCellSelectionEnabled(true);
            //tblDatEli.setRowSelectionAllowed(true);
            tblDatEli.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDatEli,INT_TBL_DAT_ELI_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuEli=new ZafTblPopMnu(tblDatEli);
            objTblPopMnuEli.setPegarEnabled(true);
            objTblPopMnuEli.setBorrarContenidoEnabled(true);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatEli.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatEli.getColumnModel();
            //CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_ELI_LIN).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ELI_NUM_DOC).setPreferredWidth(110);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ELI_BOD_ORI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_ELI_BOD_DES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_ELI_FEC_CRE).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_ELI_OBS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_ELI_BTN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ELI_MAX_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ELI_CHK).setPreferredWidth(60);

            

            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_ELI_BTN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_ELI_CHK).setResizable(false);
            
            objTblCelRenLblNumEli=new ZafTblCelRenLbl();
            objTblCelRenLblNumEli.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumEli.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNumEli.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            objTblCelRenLblCodEli=new ZafTblCelRenLbl();
            objTblCelRenLblCodEli.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_ELI_NUM_DOC).setCellRenderer(objTblCelRenLblCodEli);
            tcmAux.getColumn(INT_TBL_DAT_ELI_MAX_ITM).setCellRenderer(objTblCelRenLblCodEli);            
            

//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            

            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatEli.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaEli=new ZafMouMotAda();
            tblDatEli.getTableHeader().addMouseMotionListener(objMouMotAdaEli);
            //Configurar JTable: Establecer columnas editables.
            vecAuxEli=new Vector();
            vecAuxEli.add("" + INT_TBL_DAT_ELI_BTN);
            vecAuxEli.add("" + INT_TBL_DAT_ELI_CHK);
            vecAuxEli.add("" + INT_TBL_DAT_ELI_OBS);
            objTblModEli.setColumnasEditables(vecAuxEli);
            vecAuxEli=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiEli=new ZafTblEdi(tblDatEli);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblEli=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_ELI_NUM_DOC).setCellRenderer(objTblCelRenLblEli);
            

            objTblCelRenLblEli=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabEli=new ZafTblFilCab(tblDatEli);
            tcmAux.getColumn(INT_TBL_DAT_ELI_LIN).setCellRenderer(objTblFilCabEli);
            objTblFilCabEli=null;
            
            //botones agregados
            objTblCelRenButEli=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_ELI_BTN).setCellRenderer(objTblCelRenButEli);
            objTblCelRenButEli=null;

            //new ButSolTraInv(tblDatEli, INT_TBL_DAT_ELI_BTN);
            
            //ButObs butCot = new ButObs(tblDatEli, INT_TBL_DAT_CON_OBS); //*****
            
            
            objTblCelRenChkEli=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_ELI_CHK).setCellRenderer(objTblCelRenChkEli);
            
            
            objTblModEli.setModoOperacion(objTblModEli.INT_TBL_EDI);
            objTblOrdEli=new ZafTblOrd(tblDatEli);
            objTblBusEli=new ZafTblBus(tblDatEli);
            objDocLisEli=new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLisEli=new ZafTblModLis();
            objTblModEli.addTableModelListener(objTblModLisEli);  
            configurarEventoBotonDetalleEli();
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    
}

private void configurarEventoBotonDetalleEli()
{
    tblDatEli.addMouseListener(new java.awt.event.MouseAdapter()
    {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt)
        {
            try
            {
                int intFil = tblDatEli.rowAtPoint(evt.getPoint());
                int intCol = tblDatEli.columnAtPoint(evt.getPoint());

                if (intFil == -1 || intCol == -1)
                    return;

                if (intCol == INT_TBL_DAT_ELI_BTN)
                {
                    abrirFormularioDetalleReposicion(intFil);
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(null, e);
            }
        }
    });
}

private void abrirFormularioDetalleReposicion(int intFil)
{
    try
    {
        String numdoc = "";

        if (objTblModEli.getValueAt(intFil, INT_TBL_DAT_ELI_NUM_DOC) != null)
        {
            numdoc = objTblModEli.getValueAt(intFil, INT_TBL_DAT_ELI_NUM_DOC).toString().trim();
        }

        if (numdoc.equals(""))
        {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No se pudo obtener el número de reposición.",
                    "Mensaje del sistema",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        ZafCom92_02 objCom126_02 = new ZafCom92_02(
                javax.swing.JOptionPane.getFrameForComponent(this),
                true,
                objParSis,
                numdoc
        );

        objCom126_02.setVisible(true);

        /*
         * Como el formulario es modal, esta línea se ejecuta recién
         * cuando ZafCom92_02 se cierre.
         */
        if (objCom126_02.ejeOK())
        {
            System.out.println("El proceso del formulario hijo terminó correctamente.");
            obtenerRepoPendMYSQL();

            /*
             * Aquí puedes volver a consultar/refrescar la tabla padre.
             * Por ejemplo:
             */
            //obtenerTraPend();
        }
        else
        {
            System.out.println("El formulario hijo se cerró sin ejecutar cambios.");
        }

        objCom126_02.dispose();
        objCom126_02 = null;
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}

private void controlarBotonesPorTab()
{
    try
    {
        String strTab = tabFrm.getTitleAt(tabFrm.getSelectedIndex());
        boolean esAdmin = objParSis.getCodigoUsuario() == 1;

        butCon.setVisible(false);
        butGua.setVisible(false);
        butRep.setVisible(false);
        btnAdmin.setVisible(false);
        butEli.setVisible(false);
        butCer.setVisible(true);

        if (strTab.equalsIgnoreCase("General"))
        {
            butCon.setVisible(true);
            butGua.setVisible(true);

            // Solo admin ve este botón
            btnAdmin.setVisible(esAdmin);
        }
        else if (strTab.equalsIgnoreCase("reprocesar"))
        {
            butCon.setVisible(true);

            // Solo admin
            butRep.setVisible(esAdmin);
            butEli.setVisible(false);
        }
        else if (strTab.equalsIgnoreCase("Eliminar Item Repo"))
        {
            butCon.setVisible(true);
            butGua.setVisible(false);

            // Solo admin
            butEli.setVisible(esAdmin);
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}

private void configurarEventosTabs()
{
    tabFrm.addChangeListener(new javax.swing.event.ChangeListener()
    {
        public void stateChanged(javax.swing.event.ChangeEvent evt)
        {
            controlarBotonesPorTab();
        }
    });

    // Para aplicar la configuración desde que abre el formulario.
    controlarBotonesPorTab();
}
    /**
     * Constructor Principal
     * @param obj 
     */
    public ZafCom92(ZafParSis obj) //chiqui
    {
        try
        {
            objUti = new ZafUtil(); 
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            //datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021(), objParSis.getQueryFechaHoraBaseDatos());
            objTblCelEdiButGenDG1=new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            componente=this;
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {  //AKI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
                 initComponents();
                 configurarTabsPorUsuario();
                 configurarEventosTabs();
                 configurarFrm();
                 configurarFrmPen();
                 obtenerTraPend();
                 configurarFrmEliItm();
                 obtenerRepoPendMYSQL();
                 
              
                 strMoTrans ="TRANSFER";
             }
             else{
                 mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                 dispose();
             } 
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }

    }
    
private void configurarTabsPorUsuario()
{
    try
    {
        boolean esAdmin = objParSis.getCodigoUsuario() == 1;

        if (!esAdmin)
        {
            removerTabPorTitulo("reprocesar");
            removerTabPorTitulo("Eliminar Item Repo");
        }

        controlarBotonesPorTab();
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }
}

private void removerTabPorTitulo(String titulo)
{
    for (int i = 0; i < tabFrm.getTabCount(); i++)
    {
        if (tabFrm.getTitleAt(i).equalsIgnoreCase(titulo))
        {
            tabFrm.removeTabAt(i);
            break;
        }
    }
}    
private boolean obtenerTraPend()
    {
        String strAux="";
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;        
        
        boolean blnRes=true;
        strAux="";
        try
        {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            pgrSis.setIndeterminate(true);
            //butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");            
            if(conLoc!=null)
            {
                stmLoc = conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT    a1.co_doc,a1.tx_numDoc,a1.bodOri,a1.bodDes,a1.tx_usring,a1.fe_ing,a1.fe_aut,a1.tx_usraut,a1.trc_numitems,a1.st_doc,a1.co_tipDoc ";
                strSQL+=" FROM (\n";
                strSQL+="       SELECT cabSQL.co_doc,cabSQL.tx_numDoc,cabSQL.tx_bodorg AS bodOri,cabSQL.tx_boddes as bodDes, CABSQL.fe_ing, cabTUV.tx_usring,cabSQL.fe_aut, \n";
                strSQL+="              CABSQL.tx_usraut,cabTUV.trc_numdoc,  cabTUV.st_doc,cabtuv.trc_numitems , 'SOTRIN' as  co_tipDoc\n";
                strSQL+="       FROM Dynamics2021..tbm_cabSolTraInv as cabSQL  \n";
                strSQL+="               INNER join "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab as cabTUV on  (cabSQL.tx_numDoc = cabTUV.trc_numtrx  and cabTUV.st_doc='E')  \n";
                strSQL+="       WHERE cabSQL.st_aut ='A' and cabSQL.st_reg='A' and cabSQL.co_motTra='SOTRIN' \n";
                strSQL+="       UNION \n";                 
                strSQL+="       SELECT CAST(trc_numdoc AS INT) as CO_DOC,CAST(trc_numdoc AS INT)  as tx_numdoc,trc_bodorigen  as bodOri, trc_boddestino as bodDes,trc_fecing,tx_usring,trc_fecmod,tx_usring,trc_numdoc,\n";
                strSQL+="              st_doc ,trc_numitems,'REPOSICIONES' as co_tipDoc \n";
                strSQL+="       FROM "+objParSis.getNombreBaseDatosGP()+"..tbmsw_transferencia_cab  \n";
                strSQL+="       WHERE trc_codigomotivo='reposicion' and st_doc ='E'\n";                
                strSQL+="      ) as a1";                   
                strSQL+=" ORDER BY a1.co_tipDoc desc";   
                rstLoc=stmLoc.executeQuery(strSQL);
                vecDatTra.clear();
                lblMsgSis.setText("Cargando datos...");
                while(rstLoc.next())
                {
                    vecRegTra=new Vector();
                    vecRegTra.add(INT_TBL_DAT_LIN,"");
                    vecRegTra.add(INT_TBL_DAT_TRA_COD_DOC,rstLoc.getString("co_doc")==null?"":rstLoc.getString("co_doc"));
                    vecRegTra.add(INT_TBL_DAT_TRA_NUM_DOC,rstLoc.getString("tx_numDoc")==null?"":rstLoc.getString("tx_numDoc"));
                    vecRegTra.add(INT_TBL_DAT_TRA_BOD_ORI,rstLoc.getString("bodOri")==null?"":rstLoc.getString("bodOri"));
                    vecRegTra.add(INT_TBL_DAT_TRA_BOD_DES,rstLoc.getString("bodDes")==null?"":rstLoc.getString("bodDes"));
                    vecRegTra.add(INT_TBL_DAT_TRA_USR_ING,rstLoc.getString("tx_usring")==null?"":rstLoc.getString("tx_usring"));
                    vecRegTra.add(INT_TBL_DAT_TRA_FEC_ING,rstLoc.getString("fe_ing")==null?"":rstLoc.getString("fe_ing"));
                    vecRegTra.add(INT_TBL_DAT_TRA_USR_AUT,rstLoc.getString("tx_usraut")==null?"":rstLoc.getString("tx_usraut"));
                    vecRegTra.add(INT_TBL_DAT_TRA_FEC_AUT,rstLoc.getString("fe_aut")==null?"":rstLoc.getString("fe_aut"));
                    vecRegTra.add(INT_TBL_DAT_TRA_MAX_ITM,rstLoc.getString("trc_numitems")==null?"":rstLoc.getString("trc_numitems"));
                    vecRegTra.add(INT_TBL_DAT_TRA_CHK_AUT,false);
                    vecRegTra.add(INT_TBL_DAT_TRA_COD_TIP_DOC,rstLoc.getString("co_tipDoc")==null?"":rstLoc.getString("co_tipDoc"));
                    vecDatTra.add(vecRegTra);                        
                }
                
                rstLoc.close();
                stmLoc.close();
                rstLoc=null;
                stmLoc=null;                
            }
            objTblModTra.setData(vecDatTra);
            tblDatTra.setModel(objTblModTra);
            vecDatTra.clear();
            lblMsgSis.setText("Se encontraron " + tblDatTra.getRowCount() + " registros de Reprocesar.");

            pgrSis.setIndeterminate(false);
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

    
private boolean obtenerRepoPendMYSQL()
{
    boolean blnRes = true;

    String strSql = "";

    try
    {
        pgrSis.setIndeterminate(true);
        lblMsgSis.setText("Obteniendo datos desde MySQL...");

        try (java.sql.Connection conLocMy = DriverManager.getConnection(
                objParSis.getStringConexion_TuvalApi(),
                objParSis.getUsuarioBaseDatos_TuvalApi(),
                objParSis.getClaveBaseDatos_TuvalApi()
        ))
        {
            if (conLocMy != null)
            {
                strSql = "";
                strSql += " SELECT rep.Documento,COUNT(*) AS maxItm,SUM(CASE WHEN IFNULL(guidet.detalle_id, 0) = 0 THEN 1 ELSE 0 END) AS items_sin_guia,\n";
                strSql += "        SUM(CASE WHEN IFNULL(guidet.detalle_id, 0) <> 0 THEN 1 ELSE 0 END ) AS items_con_guia, \n";
                strSql += "        CAB.bodega_origen AS bodOri, \n";
                strSql += "        CAB.Bodega_Destino AS bodDes, \n";
                strSql += "        CAB.Fecha_Documento AS fe_doc \n";
                strSql += " FROM tuvalapi.gp_traslados_reposicions AS rep \n";
                strSql += "     LEFT JOIN ( \n";
                strSql += "                 SELECT det.* \n";
                strSql += "                 FROM tuvalapi.gp_guia_remision_detalles AS det \n";
                strSql += "                     INNER JOIN tuvalapi.gp_guia_remisions AS gui ON gui.id = det.guia_remision_id AND gui.tipo = 'reposicion' \n";
                strSql += "               ) AS guidet ON guidet.detalle_id = rep.id \n";
                strSql += "     LEFT JOIN tuvalapi.gp_traslados_reposicion_cabeceras AS CAB ON rep.Documento = CAB.Documento \n";
                strSql += " GROUP BY rep.Documento,CAB.bodega_origen,CAB.Bodega_Destino,CAB.Fecha_Documento\n";
                strSql += " HAVING items_sin_guia > 0\n";
                strSql += " ORDER BY rep.Documento\n";

                System.out.println("Consulta MYSQL obtenerTraPend:");
                System.out.println(strSql);

                vecDatEli.clear();
                lblMsgSis.setText("Cargando datos...");

                try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql);
                     java.sql.ResultSet rstLocMy = pstLocMy.executeQuery())
                {
                    while (rstLocMy.next())
                    {
                        vecRegEli = new Vector();

                        vecRegEli.add(INT_TBL_DAT_ELI_LIN, "");
                        vecRegEli.add(INT_TBL_DAT_ELI_NUM_DOC,rstLocMy.getString("Documento") == null ? "" : rstLocMy.getString("Documento"));
                        vecRegEli.add(INT_TBL_DAT_ELI_BOD_ORI,rstLocMy.getString("bodOri") == null ? "" : rstLocMy.getString("bodOri"));
                        vecRegEli.add(INT_TBL_DAT_ELI_BOD_DES,rstLocMy.getString("bodDes") == null ? "" : rstLocMy.getString("bodDes"));
                        vecRegEli.add(INT_TBL_DAT_ELI_FEC_CRE,rstLocMy.getString("fe_doc") == null ? "" : rstLocMy.getString("fe_doc"));
                        vecRegEli.add(INT_TBL_DAT_ELI_OBS,"");  
                        vecRegEli.add(INT_TBL_DAT_ELI_BTN,"..");   
                        vecRegEli.add(INT_TBL_DAT_ELI_MAX_ITM,rstLocMy.getString("maxItm") == null ? "" : rstLocMy.getString("maxItm"));
                        vecRegEli.add(INT_TBL_DAT_ELI_CHK,false);                          
                        vecDatEli.add(vecRegEli);
                    }
                }
            }
        }

        objTblModEli.setData(vecDatEli);
        tblDatEli.setModel(objTblModEli);
        vecDatEli.clear();
        lblMsgSis.setText("Se encontraron " + tblDatEli.getRowCount() + " registros de Eliminar Items.");
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
    finally
    {
        pgrSis.setIndeterminate(false);
    }

    return blnRes;
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
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panRep = new javax.swing.JPanel();
        panDet1 = new javax.swing.JPanel();
        spnDet1 = new javax.swing.JScrollPane();
        tblDatTra = new javax.swing.JTable();
        panEliItm = new javax.swing.JPanel();
        panDet2 = new javax.swing.JPanel();
        spnDet2 = new javax.swing.JScrollPane();
        tblDatEli = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butRep = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        butEli = new javax.swing.JButton();
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

        tabFrm.addTab("General", panRpt);

        panRep.setLayout(new java.awt.BorderLayout());

        panDet1.setLayout(new java.awt.BorderLayout());

        tblDatTra.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet1.setViewportView(tblDatTra);

        panDet1.add(spnDet1, java.awt.BorderLayout.CENTER);

        panRep.add(panDet1, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reprocesar", panRep);

        panEliItm.setLayout(new java.awt.BorderLayout());

        panDet2.setLayout(new java.awt.BorderLayout());

        tblDatEli.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDet2.setViewportView(tblDatEli);

        panDet2.add(spnDet2, java.awt.BorderLayout.CENTER);

        panEliItm.add(panDet2, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Eliminar Item Repo", panEliItm);

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

        butRep.setText("Reproceso");
        butRep.setPreferredSize(new java.awt.Dimension(92, 25));
        butRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepActionPerformed(evt);
            }
        });
        panBot.add(butRep);

        btnAdmin.setText("Admin");
        btnAdmin.setPreferredSize(new java.awt.Dimension(95, 25));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        panBot.add(btnAdmin);

        butEli.setText("Eliminar");
        butEli.setPreferredSize(new java.awt.Dimension(92, 25));
        butEli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliActionPerformed(evt);
            }
        });
        panBot.add(butEli);

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
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
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
        objTblMod.removeAllRows();
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafCom92.ZafThreadGUI();
                objThrGUI.start();   
                obtenerTraPend();
                obtenerRepoPendMYSQL();
            }            
        }
        else
        {
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()){
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(objThrGua==null){
                    objThrGua=new ZafCom92.ZafThrGua();
                    objThrGua.start();
                }
            }
        }
        else {
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }

    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        llamarVenAdmin();
        
    }//GEN-LAST:event_btnAdminActionPerformed

    private void butRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepActionPerformed
        java.sql.Connection conLoc;
        int intNumFil=objTblModTra.getRowCountTrue();
        boolean blnResWeb =false;
        String strTit="Mensaje del sistema Zafiro";
        String strMsgTotItm="";        
        String tx_tip="";
        try
        {
            lblMsgSis.setText("Reprocesando datos...");
            pgrSis.setIndeterminate(true);            
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            conLoc.setAutoCommit(false);
            if (conLoc !=null) 
            {
                for (int i=0; i<intNumFil;i++)
                {
                    if(objTblModTra.isChecked(i,INT_TBL_DAT_TRA_CHK_AUT))
                    {
                        
                        tx_tip=objTblModTra.getValueAt(i,INT_TBL_DAT_TRA_COD_TIP_DOC).toString();
                        if (validacionDisponibleReproceso(conLoc,i,tx_tip))    
                        {
                            if (validacionUbicacionesReproceso(conLoc,strCadeItemsGloRepr))
                            {
                                conLoc.commit();
                                intNumSol =Integer.parseInt(objTblModTra.getValueAt(i,INT_TBL_DAT_TRA_NUM_DOC).toString()) ;
                                blnResWeb=ejecucionWebService();
                                if (blnResWeb)
                                {
                                    String strNumMod = String.format("%015d", intNumSol);
                                    if (validaTraMySQL(strNumMod))
                                    {
                                        updateCabeceraReproceso(0);             
                                        strMsgTotItm="<HTML>Se realizo de forma EXITOSA el reproceso....<BR><BR><html>";                    
                                        mostrarMsgInf(strMsgTotItm);                                                                            
                                    }
                                    else
                                    {
                                        updateCabeceraReproceso(1);
                                        strMsgTotItm="<HTML>NO Se realizo El reproceso....Consultar por el Administrador del Sistema<BR><BR><html>";                    
                                        mostrarMsgInf(strMsgTotItm);                                         
                                    }
                                }
                                else
                                {
                                    updateCabeceraReproceso(1);
                                    strMsgTotItm="<HTML>NO Se realizo El reproceso....Consultar por el Administrador del Sistema<BR><BR><html>";                    
                                    mostrarMsgInf(strMsgTotItm);                                
                                }
                            }
                        }
                        else
                        {
                            conLoc.rollback();
                            strMsgTotItm="<HTML>NO Se realizo El reproceso....Consultar por el Administrador del Sistema<BR><BR><html>";                    
                            mostrarMsgInf(strMsgTotItm);                                
                        }
                    }
                }                
            }
            pgrSis.setIndeterminate(false);    
        }
        catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
        
    }//GEN-LAST:event_butRepActionPerformed

    private void butEliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliActionPerformed
        try
        {
            eliminarReposicionesSeleccionadas();
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
    }//GEN-LAST:event_butEliActionPerformed

private boolean tieneItemsEnGuiaMYSQL(java.sql.Connection conLocMy, String numdoc) throws Exception
{
    boolean blnTiene = false;
    String strSql = "";

    strSql = "";
    strSql += " SELECT COUNT(1) AS can_reg \n";
    strSql += " FROM tuvalapi.gp_traslados_reposicions AS rep \n";
    strSql += " INNER JOIN tuvalapi.gp_guia_remision_detalles AS det \n";
    strSql += "     ON det.detalle_id = rep.id \n";
    strSql += " INNER JOIN tuvalapi.gp_guia_remisions AS gui \n";
    strSql += "     ON gui.id = det.guia_remision_id \n";
    strSql += "    AND gui.tipo = 'reposicion' \n";
    strSql += " WHERE rep.Documento = ? \n";

    System.out.println("VALIDA GUIA MYSQL:");
    System.out.println(strSql);
    System.out.println("Documento: " + numdoc);

    try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql))
    {
        pstLocMy.setString(1, numdoc);

        try (java.sql.ResultSet rstLocMy = pstLocMy.executeQuery())
        {
            if (rstLocMy.next())
            {
                blnTiene = rstLocMy.getInt("can_reg") > 0;
            }
        }
    }

    return blnTiene;
}
    
private boolean eliminarReposicionSQL(java.sql.Connection conExt, String txrepo) throws Exception
{
    String strSQLInt = "";
    int intDetEli = 0;
    int intCabEli = 0;

    if (conExt == null)
        throw new Exception("La conexión SQL Server no está disponible.");

    /*
     * 1) Eliminar detalle.
     */
    strSQLInt = "";
    strSQLInt += " DELETE FROM " + objParSis.getNombreBaseDatosGP() + ".dbo.tbmsw_transferencia_det \n";
    strSQLInt += " WHERE trd_numdoc = ? \n";

    System.out.println("DELETE DETALLE SQL SERVER:");
    System.out.println(strSQLInt);
    System.out.println("Documento: " + txrepo);

    try (java.sql.PreparedStatement pstLoc = conExt.prepareStatement(strSQLInt))
    {
        pstLoc.setString(1, txrepo);
        intDetEli = pstLoc.executeUpdate();
    }

    /*
     * 2) Eliminar cabecera.
     */
    strSQLInt = "";
    strSQLInt += " DELETE FROM " + objParSis.getNombreBaseDatosGP() + ".dbo.tbmsw_transferencia_cab \n";
    strSQLInt += " WHERE trc_numdoc = ? \n";

    System.out.println("DELETE CABECERA SQL SERVER:");
    System.out.println(strSQLInt);
    System.out.println("Documento: " + txrepo);

    try (java.sql.PreparedStatement pstLoc = conExt.prepareStatement(strSQLInt))
    {
        pstLoc.setString(1, txrepo);
        intCabEli = pstLoc.executeUpdate();
    }

    System.out.println("Detalle SQL Server eliminado: " + intDetEli);
    System.out.println("Cabecera SQL Server eliminada: " + intCabEli);

    if (intCabEli <= 0)
    {
        throw new Exception(
                "No se eliminó la cabecera en SQL Server. Documento: " + txrepo
        );
    }

    return true;
}

private boolean eliminarReposicionMYSQL(java.sql.Connection conLocMy, String numdoc) throws Exception
{
    String strSql = "";
    int intDetEli = 0;
    int intCabEli = 0;

    if (conLocMy == null)
        throw new Exception("La conexión MySQL no está disponible.");

    /*
     * 1) Eliminar detalles.
     */
    strSql = "";
    strSql += " DELETE FROM tuvalapi.gp_traslados_reposicions \n";
    strSql += " WHERE Documento = ? \n";

    System.out.println("DELETE DETALLE MYSQL:");
    System.out.println(strSql);
    System.out.println("Documento: " + numdoc);

    try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql))
    {
        pstLocMy.setString(1, numdoc);
        intDetEli = pstLocMy.executeUpdate();
    }

    /*
     * 2) Eliminar cabecera.
     */
    strSql = "";
    strSql += " DELETE FROM tuvalapi.gp_traslados_reposicion_cabeceras \n";
    strSql += " WHERE Documento = ? \n";

    System.out.println("DELETE CABECERA MYSQL:");
    System.out.println(strSql);
    System.out.println("Documento: " + numdoc);

    try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSql))
    {
        pstLocMy.setString(1, numdoc);
        intCabEli = pstLocMy.executeUpdate();
    }

    System.out.println("Detalle MySQL eliminado: " + intDetEli);
    System.out.println("Cabecera MySQL eliminada: " + intCabEli);

    if (intCabEli <= 0)
    {
        throw new Exception(
                "No se eliminó la cabecera en MySQL. Documento: " + numdoc
        );
    }

    return true;
}

private String obtenerValorTablaEli(int intFil, int intCol)
{
    String strVal = "";

    try
    {
        Object objVal = objTblModEli.getValueAt(intFil, intCol);

        if (objVal != null)
        {
            strVal = objVal.toString().trim();
        }
    }
    catch (Exception e)
    {
        strVal = "";
    }

    return strVal;
}

private boolean eliminarReposicionesSeleccionadas()
{
    boolean blnRes = true;

    java.sql.Connection conSQL = null;
    java.sql.Connection conMySQL = null;

    java.util.ArrayList<String> arrDocSel = new java.util.ArrayList<String>();
    java.util.ArrayList<String> arrDocBloq = new java.util.ArrayList<String>();

    try
    {
        /*
         * 1) Recorrer tabla y obtener documentos chequeados.
         */
        for (int i = 0; i < objTblModEli.getRowCountTrue(); i++)
        {
            Object objChk = objTblModEli.getValueAt(i, INT_TBL_DAT_ELI_CHK);

            boolean blnChk = false;

            if (objChk instanceof Boolean)
            {
                blnChk = ((Boolean) objChk).booleanValue();
            }
            else if (objChk != null)
            {
                blnChk = objChk.toString().trim().equalsIgnoreCase("true")
                      || objChk.toString().trim().equalsIgnoreCase("S")
                      || objChk.toString().trim().equals("1");
            }

            if (blnChk)
            {
                String strNumDoc = obtenerValorTablaEli(i, INT_TBL_DAT_ELI_NUM_DOC);

                if (!strNumDoc.equals("") && !arrDocSel.contains(strNumDoc))
                {
                    arrDocSel.add(strNumDoc);
                }
            }
        }

        if (arrDocSel.size() == 0)
        {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar al menos una reposición para eliminar.",
                    "Mensaje del sistema",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        /*
         * 2) Abrir conexiones.
         */
        conSQL = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        conMySQL = DriverManager.getConnection(
                objParSis.getStringConexion_TuvalApi(),
                objParSis.getUsuarioBaseDatos_TuvalApi(),
                objParSis.getClaveBaseDatos_TuvalApi()
        );

        if (conSQL == null || conMySQL == null)
        {
            throw new Exception("No se pudo establecer conexión con SQL Server o MySQL.");
        }

        conSQL.setAutoCommit(false);
        conMySQL.setAutoCommit(false);

        /*
         * 3) Validar si alguna reposición tiene ítems en guía.
         */
        for (int i = 0; i < arrDocSel.size(); i++)
        {
            String strNumDoc = arrDocSel.get(i);

            if (tieneItemsEnGuiaMYSQL(conMySQL, strNumDoc))
            {
                arrDocBloq.add(strNumDoc);
            }
        }

        if (arrDocBloq.size() > 0)
        {
            StringBuilder sbMsg = new StringBuilder();
            sbMsg.append("No se puede eliminar la reposición porque tiene uno o más ítems dentro de una guía.\n\n");
            sbMsg.append("Reposiciones bloqueadas:\n");

            for (int i = 0; i < arrDocBloq.size(); i++)
            {
                sbMsg.append("- ").append(arrDocBloq.get(i)).append("\n");
            }

            throw new Exception(sbMsg.toString());
        }

        /*
         * 4) Confirmación.
         */
        int intOpc = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Se eliminarán completamente " + arrDocSel.size() + " reposición(es).\n"
                + "Esta acción eliminará cabecera y detalle en SQL Server y MySQL.\n\n"
                + "¿Está seguro de continuar?",
                "Confirmación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (intOpc != javax.swing.JOptionPane.YES_OPTION)
        {
            conMySQL.rollback();
            conSQL.rollback();
            return false;
        }

        /*
         * 5) Eliminar reposiciones.
         */
        for (int i = 0; i < arrDocSel.size(); i++)
        {
            String strNumDoc = arrDocSel.get(i);

            System.out.println("Eliminando reposición: " + strNumDoc);

            /*
             * Primero se ejecuta SQL Server, pero todavía NO se hace commit.
             */
            eliminarReposicionSQL(conSQL, strNumDoc);

            /*
             * Luego MySQL.
             * Si MySQL falla, se hace rollback también de SQL Server.
             */
            eliminarReposicionMYSQL(conMySQL, strNumDoc);
        }

        /*
         * 6) Commit.
         * SQL Server confirma solo después de que MySQL terminó correctamente.
         */
        conMySQL.commit();
        conSQL.commit();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Proceso ejecutado correctamente.\nReposiciones eliminadas: " + arrDocSel.size(),
                "Mensaje del sistema",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        /*
         * Opcional: refrescar tabla principal.
         */
        obtenerTraPend();
    }
    catch (Exception e)
    {
        blnRes = false;

        try
        {
            if (conMySQL != null)
                conMySQL.rollback();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        try
        {
            if (conSQL != null)
                conSQL.rollback();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        javax.swing.JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Mensaje del sistema",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
    }
    finally
    {
        try
        {
            if (conMySQL != null)
            {
                conMySQL.setAutoCommit(true);
                conMySQL.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            if (conSQL != null)
            {
                conSQL.setAutoCommit(true);
                conSQL.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    return blnRes;
}    
    
    private void llamarVenAdmin()
    {
        ZafGenFacAutAdm objGeneraFactura = new  ZafGenFacAutAdm(objParSis);
        this.getParent().add(objGeneraFactura,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objGeneraFactura.show();
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEli;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butRep;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDet1;
    private javax.swing.JPanel panDet2;
    private javax.swing.JPanel panEliItm;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnDet1;
    private javax.swing.JScrollPane spnDet2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatEli;
    private javax.swing.JTable tblDatTra;
    // End of variables declaration//GEN-END:variables

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }   
       
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {//chiqui
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + strVersion );
            lblTit.setText(objParSis.getNombreMenu());
             btnAdmin.setVisible(false);
             btnAdmin.setEnabled(false);
            objWeb = new ZafWebSer(objParSis);             
            //Botón Genera Factura Automática
            if(objParSis.getCodigoUsuario()!= 1){
                System.out.println("NO ES EL ADMIN... ");
                btnAdmin.setVisible(false);
                btnAdmin.setEnabled(false);
            }
            
//            3995;2078;"0";"Guardar"
            
            if(!objPerUsr.isOpcionEnabled(3995)){
                butGua.setVisible(false);
            }
            //chiquitabla
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
             //chiqui
            vecCab.add(INT_TBL_DAT_LIN,"");
//            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
//            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
//            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
//            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_BOD_OR,"Bodega Origen");
            vecCab.add(INT_TBL_DAT_BOD_DES,"Bodega Destino");
            vecCab.add(INT_TBL_DAT_NUM_MAX,"Numero Maximo");
            vecCab.add(INT_TBL_DAT_CON_SOL_TRA,"");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"Autorizar");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"Denegar");
            vecCab.add(INT_TBL_DAT_OBS_AUT,"Obs.Aut.");
           
           

            
            
            vecCab.add(INT_TBL_DAT_CON_OBS,"");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
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
            //CHIQUI
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
//            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_OBS_AUT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BOD_OR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BOD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_MAX).setPreferredWidth(60);

            
            
            
            //CHIQUI
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_DOC, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_COR_TIP_DOC, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_TIP_DOC, tblDat);
            

            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setResizable(false);
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            
            objTblCelRenLblCod=new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblCod);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            

            
            
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CON_SOL_TRA);
            vecAux.add("" + INT_TBL_DAT_CHK_AUT);
            vecAux.add("" + INT_TBL_DAT_CHK_DEN);
            vecAux.add("" + INT_TBL_DAT_OBS_AUT);
            vecAux.add("" + INT_TBL_DAT_CON_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
           //CHIQUI
//            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            

            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab=null;
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_CON_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objTblCelRenButConOcp=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_CON_SOL_TRA).setCellRenderer(objTblCelRenButConOcp);
            objTblCelRenButConOcp = null;
            
            new ButSolTraInv(tblDat, INT_TBL_DAT_CON_SOL_TRA);
            
            ButObs butCot = new ButObs(tblDat, INT_TBL_DAT_CON_OBS); //*****
            
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChk);
            
            //Check: Autorizar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_AUT)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_AUT);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                    }
                }
            });
                   
            //Check: Denegar
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()  {
                int intFilSel;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)  {
                    intFilSel = tblDat.getSelectedRow();
                    if (intFilSel != -1)  { //Si se permite editar marcar/desmarcar
                        if(!objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_DEN)) {                    
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_DEN);
                        }
                        else  {
                            objTblMod.setValueAt(true, intFilSel, INT_TBL_DAT_CHK_DEN);
                            objTblMod.setValueAt(false, intFilSel, INT_TBL_DAT_CHK_AUT);
                        }
                    }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDat);
            objTblBus=new ZafTblBus(tblDat);
            objDocLis=new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);           
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
    
    
    private boolean cargarDetReg(java.sql.Connection conn)
    {
        String strAux="";
        
        objUti=new ZafUtil();
        boolean blnRes=true;
        strAux="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (conn!=null){
                stm=conn.createStatement();
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL=" ";
                strSQL+=" SELECT a1.tx_numDoc,g.tx_descor as co_motTra,g.tx_deslar as Descripcion,a1.co_doc, a1.fe_doc,a1.tx_boddes,(SELECT COUNT(ne_Det) FROM Dynamics2021.dbo.tbm_detSolTraInv WHERE co_doc =a1.co_doc AND co_motTra =g.tx_descor) as contmax, \n";
                strSQL+="        a1.st_imp,a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.tx_usrIng,a1.tx_obUltM, a1.st_aut,a1.st_conInv,a1.tx_obsaut, a1.tx_bodorg\n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as a1  \n";
                strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = a1.co_emp and a1.co_tipdoc = g.co_tipdoc)  \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (a1.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)  \n";
                strSQL+=" WHERE a1.st_conInv ='P' AND a1.st_reg ='A' \n";
                //strSQL+=" AND c.co_mnu =" + objParSis.getCodigoMenu() + " \n";
                strSQL+=" ORDER BY a1.co_doc,a1.fe_doc desc";
                
            } 
            else{
                strSQL=" ";
                strSQL+=" SELECT a1.tx_numDoc,g.tx_descor as co_motTra,g.tx_deslar as Descripcion,a1.co_doc, a1.fe_doc,a1.tx_boddes,(SELECT COUNT(ne_Det) FROM Dynamics2021.dbo.tbm_detSolTraInv WHERE co_doc =a1.co_doc AND co_motTra =g.tx_descor) as contmax, \n";
                strSQL+="        a1.st_imp,a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.tx_usrIng,a1.tx_obUltM, a1.st_aut,a1.st_conInv,a1.tx_obsaut, a1.tx_bodorg\n";
                strSQL+=" FROM Dynamics2021.dbo.tbm_cabSolTraInv as a1  \n";
                strSQL+=" INNER join Dynamics2021.dbo.tbm_cabtipdoc  as g on (g.co_emp = a1.co_emp and a1.co_tipdoc = g.co_tipdoc)  \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbm_cfgMotMnu as c on (a1.co_tipDoc =c.co_tipDoc and g.co_mnu =c.co_mnu)  \n";
                strSQL+=" INNER JOIN Dynamics2021.dbo.tbr_tipdocusr as m on (g.co_tipdoc =m.co_tipdoc and g.co_mnu =m.co_mnu)  \n";
                strSQL+=" WHERE a1.st_conInv ='P' AND a1.st_reg ='A' \n";
                //strSQL+=" AND c.co_mnu =" + objParSis.getCodigoMenu() + " \n";
                strSQL+=" AND m.co_usr =" + objParSis.getCodigoUsuario()  + " \n";
                strSQL+=" ORDER BY a1.co_doc,fe_doc desc";
            }
            //System.out.println("ZafCom92.cargarDetReg: " + strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            while(rst.next()){
                if (blnCon){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
//                    vecReg.add(INT_TBL_DAT_COD_EMP,1);//chiquinova
//                    vecReg.add(INT_TBL_DAT_COD_LOC,1);//chiquinova
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_motTra")==null?"":rst.getString("co_motTra"));
//                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,1);//chiquinova
//                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,1);//chiquinova
                    vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc")==null?"":rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("tx_numDoc")==null?"":rst.getString("tx_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc")==null?"":rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_DAT_BOD_OR,rst.getString("tx_bodorg")==null?"":rst.getString("tx_bodorg"));
                    vecReg.add(INT_TBL_DAT_BOD_DES,rst.getString("tx_boddes")==null?"":rst.getString("tx_boddes"));
                    vecReg.add(INT_TBL_DAT_NUM_MAX,rst.getString("contmax")==null?"":rst.getString("contmax"));
                    vecReg.add(INT_TBL_DAT_CON_SOL_TRA,"Boton");
                    vecReg.add(INT_TBL_DAT_CHK_AUT,false);
                    vecReg.add(INT_TBL_DAT_CHK_DEN,false);
                    vecReg.add(INT_TBL_DAT_OBS_AUT,rst.getString("tx_obsaut")==null?"":rst.getString("tx_obsaut"));
                    vecReg.add(INT_TBL_DAT_CON_OBS,"Boton");
//               
//                    strBodOrigen =rst.getString("tx_bodorg");
//                    strBodDestino =rst.getString("tx_boddes");
//                    numeroItems =rst.getInt("contmax");
                    vecDat.add(vecReg);
                }
                else{
                    break;
                }
            }
         }     
        objTblMod.setData(vecDat);
        tblDat.setModel(objTblMod);
        vecDat.clear();
        if(blnCon){
                 lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros de General.");
         }
        else{
             lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
        }
        rst.close();
        stm.close();
        rst=null;
        stm=null;
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
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
    
    
 
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
//                case INT_TBL_DAT_COD_EMP: strMsg="Código de la Empresa"; break;
//                case INT_TBL_DAT_COD_LOC: strMsg="Código del Local"; break;
                case INT_TBL_DAT_COD_TIP_DOC: strMsg="Código del tipo de Documento"; break;
//                case INT_TBL_DAT_DES_COR_TIP_DOC: strMsg="Descripción corta del tipo de Documento"; break;
//                case INT_TBL_DAT_DES_LAR_TIP_DOC: strMsg="Descripción larga del tipo de Documento"; break;
                case INT_TBL_DAT_COD_DOC: strMsg="Código del documento"; break;
                case INT_TBL_DAT_NUM_DOC: strMsg="Número del documento"; break;
                case INT_TBL_DAT_FEC_DOC: strMsg="Fecha del documento"; break;
                case INT_TBL_DAT_CON_SOL_TRA: strMsg="Muestra la Solicitud de Transferecia'"; break;
                case INT_TBL_DAT_CHK_AUT: strMsg="Autorizar"; break;
                case INT_TBL_DAT_CHK_DEN: strMsg="Denegar"; break;
                case INT_TBL_DAT_OBS_AUT: strMsg="Observación de la autorización"; break;
                case INT_TBL_DAT_CON_OBS: strMsg="Muestra la Observación de la Autorización'"; break;
                case INT_TBL_DAT_BOD_OR: strMsg="Bodega de Origen'"; break;
                case INT_TBL_DAT_BOD_DES: strMsg="Bodega de Destino'"; break;
                case INT_TBL_DAT_NUM_MAX: strMsg="Numero Maximo'"; break;
                default: strMsg=""; break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
      

    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    

    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt){
            blnHayCam=true;
        }
    }
    
    private class ZafThreadGUI extends Thread
    {
        public void run(){
            try{
                //con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos()); 
                con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            
                if (!cargarDetReg(con))
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");

                }

                con.close();
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
    
    private boolean ejecucionWebService ()
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLocEx;        
        boolean blnRes=false;
        
    // Obtener la fecha actual con formato dd/MM/yyyy
        String fechaactual = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));        
        
        blnRes = objWeb.ejecucionWebServReposFechaRepo(intNumSol ,fechaactual);
////        if (blnRes) 
////            blnRes=true;
////        else
////             blnRes=false;
        return blnRes;
    }     
    
    private class ZafThrGua extends Thread
    {
        public void run()
        {
            try
            {
                boolean blnResWeb =false;
                boolean blnRes =false;                
                if (!guardarAutorizacion())
                {
                    lblMsgSis.setText("Listo"); 
                    pgrSis.setValue(0);         
                    butCon.setText("Consultar");
                }
                else
                {
                //Inicializar objetos si no se pudo cargar los datos.
                    blnResWeb=ejecucionWebService();
                    if (blnResWeb)
                        blnRes =true;
                    else
                        blnRes=false;                    
                    objThrGua=null;                    
                    //blnRes=false;
                }
                
                
                if (validaTraMySQL(strNumModGlo))
                {
                    String strTit="Mensaje del sistema Zafiro";
                    updateCabecera(0);             
                    String strMsgTotItm="";
                    strMsgTotItm="<HTML>Se realizo de forma EXITOSA....<BR><BR><html>";                    
                    mostrarMsgInf(strMsgTotItm);
                    
                }
                else
                {
                    String strTit="Mensaje del sistema Zafiro";
                    updateCabecera(1);  
                    String strMsgTotItm="";
                    strMsgTotItm="<HTML>No se realizo la TRANSFERENCIA DEL ITEM, notificar al Administrador del Sistema.....<BR><BR><html>";
                    mostrarMsgInf(strMsgTotItm);                    
                    
                }
//////////                if (!blnRes)
//////////                {
//////////                    String strTit="Mensaje del sistema Zafiro";
//////////                    updateCabecera(1);  
//////////                    String strMsgTotItm="";
//////////                    strMsgTotItm="<HTML>No se realizo la TRANSFERENCIA DEL ITEM, notificar al Administrador del Sistema.....<BR><BR><html>";
//////////                    mostrarMsgInf(strMsgTotItm);                    
//////////
//////////                }
//////////                else
//////////                {
//////////                    String strTit="Mensaje del sistema Zafiro";
//////////                    updateCabecera(0);                                        
//////////                }   

            }
            catch (Exception e)
            {
                System.err.println(e);
            }
        }
    }    
    
private boolean validaTraMySQL(String docTra)
{
    //strNumModGlo ="hola";
    boolean blnRes = false;

    String strSqlCab = "";
    String strSqlSec = "";

    try (java.sql.Connection conLocMy = DriverManager.getConnection(
            objParSis.getStringConexion_TuvalApi(),
            objParSis.getUsuarioBaseDatos_TuvalApi(),
            objParSis.getClaveBaseDatos_TuvalApi()
    ))
    {
        if (conLocMy != null)
        {
            /*
             * VALIDAR DOCUMENTO EN MYSQL
             * 
             * IMPORTANTE:
             * Cambia a1.trc_numtrx por la columna correcta si tx_doc
             * corresponde a otro campo, por ejemplo:
             * a1.tx_doc
             * a1.trc_doc
             * a1.trc_numdoc
             */
            strSqlCab = "";
            strSqlCab += " SELECT COUNT(1) AS existe \n";
            strSqlCab += " FROM tuvalapi.gp_traslados_reposicion_cabeceras AS a1 \n";
            strSqlCab += " WHERE a1.Documento = ? \n";

            System.out.println("Consulta MYSQL validaTraMySQL:");
            System.out.println(strSqlCab);

            try (java.sql.PreparedStatement pstLocMy = conLocMy.prepareStatement(strSqlCab))
            {
                pstLocMy.setString(1, docTra);

                try (java.sql.ResultSet rstLocMy = pstLocMy.executeQuery())
                {
                    if (rstLocMy.next())
                    {
                        if (rstLocMy.getInt("existe") > 0)
                            blnRes=true;
                    }
                }
            }
        }
    }
    catch (Exception e)
    {
        blnRes = false;
        e.printStackTrace();
        // objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}

    private boolean updateCabecera(int tipo)
    {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLocEx;
            String strSqlCab="";
            try{
                conLocEx=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if(conLocEx!=null){
                    stmLoc = conLocEx.createStatement();
                    strSqlCab="";
                    if (tipo==1)//CUANDO HAY UN ERROR
                        strSqlCab+="UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET trc_numdoc ='' ,st_doc ='E',fe_ing = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                    else//CUANDO ESTA TODO BIEN
                        strSqlCab+=" UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET st_doc ='A',fe_ing = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                    strSqlCab+=" WHERE  trc_numtrx = "+intNumSol +";\n";

                    if (tipo==1)//CUANDO HAY UN ERROR
                    {
                        strSqlCab+=" UPDATE Dynamics2021.dbo.tbm_cabSolTraInv SET st_conInv ='P'  \n";                    
                        strSqlCab+=" WHERE  tx_numDoc = "+intNumSol +";\n";
                    }
                    System.out.println("insertarCabecera GP" + strSqlCab);
                    stmLoc.executeUpdate(strSqlCab);
                    stmLoc.close();
                    stmLoc=null;
                    conLocEx=null;
                    conLocEx.close();
                    blnRes=true;
                }            
    //            insert tbmsw_transferencia_cab (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado)
    //            values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
            }
            catch (Exception e)
            {
                blnRes=true;
                //objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        } 
    
private boolean updateCabeceraReproceso(int tipo)
    {
            boolean blnRes=false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            java.sql.Connection conLocEx;
            String strSqlCab="";
            String strNumMod="";
            try{
                conLocEx=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if(conLocEx!=null){
                    stmLoc = conLocEx.createStatement();
                    strNumMod = String.format("%015d", intNumSol);
                    strSqlCab="";
                    if (tipo==1)//CUANDO HAY UN ERROR
                        strSqlCab+="UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET trc_numdoc ='' ,st_doc ='E',fe_ing = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                    else//CUANDO ESTA TODO BIEN
                        strSqlCab+=" UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET trc_numdoc = '"+strNumMod +"' ,st_doc ='A',trc_fecmod = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                    strSqlCab+=" WHERE  trc_numtrx = "+intNumSol +";\n";

                    if (tipo==1)//CUANDO HAY UN ERROR
                    {
                        strSqlCab+=" UPDATE Dynamics2021.dbo.tbm_cabSolTraInv SET st_conInv ='P'  \n";                    
                        strSqlCab+=" WHERE  tx_numDoc = "+intNumSol +";\n";
                    }
                    else
                    {
                        strSqlCab+=" UPDATE Dynamics2021.dbo.tbm_cabSolTraInv SET st_conInv ='A'  \n";                    
                        strSqlCab+=" WHERE  tx_numDoc = "+intNumSol +";\n";                        
                    }
                    System.out.println("insertarCabecera GP" + strSqlCab);
                    stmLoc.executeUpdate(strSqlCab);
                    stmLoc.close();
                    stmLoc=null;
                    conLocEx=null;
                    conLocEx.close();
                    blnRes=true;
                }            
    //            insert tbmsw_transferencia_cab (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado)
    //            values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
            }
            catch (Exception e)
            {
                blnRes=true;
                //objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
        } 

    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
   
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e){
            switch (e.getType()){
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="/* Botón Solicitud de Transferencia de Inventario */">
    private class ButSolTraInv extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButSolTraInv(javax.swing.JTable tbl, int intIdx)
        {
            super(tbl, intIdx, "Solicitud de Transferencia de Inventario.");
        }

        public void butCLick()
        {
            cargarVentanaSolTraInv();
        }
    }
    
    private void cargarVentanaSolTraInv()
    {
//        String strCodEmp = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString();
//        String strCodLoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString();
        String strCodEmp = "0";
        String strCodLoc = "1";
        String strCodTipDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString();
        String strCodDoc = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString();

        Compras.ZafCom91.ZafCom91 obj = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold> 
 
    private class ButObs extends Librerias.ZafTableColBut.ZafTableColBut_uni
    {
        public ButObs(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Observación.");
           
        }
        @Override
        public void butCLick() {
           llamarVenObs(tblDat.getSelectedRow());
        }
    }
        
    private void llamarVenObs(int intFil)
    {
        String strObs = ( tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  )==null?"":tblDat.getValueAt(intFil,  INT_TBL_DAT_OBS_AUT  ).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, INT_TBL_DAT_OBS_AUT);
        }
        obj = null;
    }
    
    private boolean insertarCabSolicitudAutorizarGP(java.sql.Connection connExt, int i)
    {
         boolean blnRes=true;
         java.sql.Statement stmLoc;
        //boolean blncorrecto=true;
         java.sql.ResultSet rstLoc;
         String strNumMod="";
    
     int intNumSeg=0;
        try
        {
     
                if (connExt!=null)
                {
                    stmLoc=connExt.createStatement();
                    
                    strSQL="";

//                    
                    strSQL = "";

                    strSQL+= " SELECT MAX(a1.trc_numtrx)+1 as co_seg \n";
                    strSQL+= " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab  as a1  \n";
                    rstLoc = stmLoc.executeQuery(strSQL);
                    if (rstLoc.next()) {
                        intNumSol=rstLoc.getInt("co_seg");
                    }
                    rstLoc.close();
                    rstLoc=null;
                    if (intNumSol ==0)
                    {
                        intNumSol= intNumSol+1;
                    }
                    strSQL="";
                    strNumMod = String.format("%015d", intNumSol);
                    strNumModGlo=strNumMod;
                    strSQL+=" INSERT  "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab  \n";
                    strSQL+="       (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado,trc_numdoc,st_doc) \n";
                    //values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
                    strSQL+=" VALUES ("+intNumSol +",CAST(CURRENT_TIMESTAMP AS DATE) , '"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)  +"',\n";
                    strSQL+="         '"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR) +"','"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_DES) +"',"+objTblMod.getValueAt(i,INT_TBL_DAT_NUM_MAX) +",'P','"+strNumMod+"','I') \n";
                    strSQL+=" \n ";
                    System.out.println("INSERT CABECERA" + strSQL);
                    stmLoc.executeUpdate(strSQL);
                    stmLoc.close();
                    stmLoc=null;
                }            
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
   }
    
    private boolean ValidaSolicitudAutorizarGPDisponible(java.sql.Connection connExt, int i)
    {
         boolean blnRes=true;
        java.sql.Statement stmLoc;
         java.sql.ResultSet rstLocIn;
         String strSQLGlob ="";
         String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="";
        try
        {
                if (connExt!=null)
                {
                    stmLoc=connExt.createStatement();
                strSQL ="";
                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.UOMSCHDL,a1.nd_can,  \n";
                strSQL+="        a2.STNDCOST,(a1.nd_can * a2.STNDCOST) as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                strSQL+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1   \n";
                strSQL+="  INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
                strSQL+="  WHERE co_motTra='" +objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC) + "' AND a1.co_doc= "+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)  +"\n";
                strSQL+="  ORDER BY a1.co_reg";
                rstLocIn=stmLoc.executeQuery(strSQL);
                System.out.println("SELECT  DETALLE DE SOLICITUD" + strSQL);
                 
                 strBodega =objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString();
                 while(rstLocIn.next())
                 {
                    //strCadenaItem = strCadenaItem + ","+rstLocIn.getString("ITEMNMBR");
                    strCadenaItem = rstLocIn.getString("ITEMNMBR")+","+ strCadenaItem;
                    strCadenaCant = rstLocIn.getString("nd_can")+","+ strCadenaCant;                    
////                    strCadenaCant = strCadenaCant + ","+ rstLocIn.getString("nd_can");
                 }
                 
                if (strCadenaItem.endsWith(",")) {
                    strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                    }
                if (strCadenaCant.endsWith(",")) {
                    strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                
                    }                 
                 
                if((strCadenaItem!="") && (strCadenaCant !=""))
                {
                    if (consultaDisponible(connExt,strCadenaItem,strCadenaCant,strBodega))
                          blnRes =true;
                    else
                          blnRes =false;
                }
                else
                    blnRes=false;                 
        
                 System.out.println("INSERT DETALLE" + strSQLGlob);
                    stmLoc.executeUpdate(strSQLGlob);
                    stmLoc.close();
                    stmLoc=null;
                }
                  
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
   }

    private String obtenerUbicacion(java.sql.Connection conExt,String item,String bodega)
    {
        String strUbic="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int contador =0;
        String strSql="";
        try
        {
            if(conExt!=null)
            {
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+="SELECT ITEMNMBR, LOCNCODE, COUNT(DISTINCT BIN) AS CantidadDeBins, trim(BIN) as ubicacion  \n";
                strSql+=" FROM  "+objParSis.getNombreBaseDatosGP()+".dbo.IV00112  \n";
                strSql+=" WHERE ITEMNMBR = "+item+" and LOCNCODE ="+bodega + " \n";
                strSql+=" GROUP BY ITEMNMBR, LOCNCODE,BIN; \n";
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next())
                {
                    contador++;
                    strUbic = rstLoc.getString("ubicacion");
                }
                if (contador >=2)
                    strUbic="ERROR";
                    
                rstLoc.close();
                rstLoc=null;
            }            
        }
        catch (Exception e)
        {
            strUbic="ERROR";
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strUbic;
    }
    
    private boolean insertarDetSolicitudAutorizarGP(java.sql.Connection connExt, int i)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLocIn;
        String strSQLGlob ="";
        String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="";
        String strUbi ="";
        String strNumMod  ="";         
        try
        {
                if (connExt!=null)
                {
                    stmLoc=connExt.createStatement();
                strSQL ="";
                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.UOMSCHDL,a1.nd_can,  \n";
                strSQL+="        a2.STNDCOST,(a1.nd_can * a2.STNDCOST) as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                strSQL+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1   \n";
                strSQL+="  INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
                strSQL+="  WHERE co_motTra='" +objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC) + "' AND a1.co_doc= "+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)  +"\n";
                strSQL+="  ORDER BY a1.co_reg";
                rstLocIn=stmLoc.executeQuery(strSQL);
                System.out.println("SELECT  DETALLE DE SOLICITUD" + strSQL);
                 
                 while(rstLocIn.next())
                 {
//////////                     if (!validarStockDisponible(con,rstLocIn.getString("ITEMNMBR"),objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString(),Double.parseDouble(rstLocIn.getString("nd_can")) ))
//////////                     {
//////////                             blnRes=false;
//////////                             break;
//////////                     }
//////////                     else
//////////                     {
                            strUbi = obtenerUbicacion(connExt,objUti.codificar(rstLocIn.getString("ITEMNMBR")),objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR)));
                            strNumMod =String.format("%015d", intNumSol);                         
                            strSQL ="";
                            strSQL+="\n INSERT  "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_det  \n";
                            strSQL+="  (trd_numtrx,trd_fechatrx,trd_codigomotivo,trd_bodorigen,trd_boddestino,trd_item,trd_cantidad_trf,trd_estado,trd_ubic,trd_numdoc) \n";
                            //values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
                            strSQL+=" VALUES ("+intNumSol +", CAST(CURRENT_TIMESTAMP AS DATE),'"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)  +"',\n";
                            strSQL+="         '"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR)  +"','"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_DES)  +"','"+rstLocIn.getString("ITEMNMBR") +"',"+rstLocIn.getString("nd_can") +",'P','"+strUbi +"','"+ strNumMod +"'); \n";
                            strSQL+=" \n "; 
                            strSQLGlob = strSQLGlob+ strSQL;
                            
////                            blnRes=true;                         
////////                     }
                 }
//////////                 strBodega =objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString();
//////////                 while(rstLocIn.next())
//////////                 {
//////////                    strCadenaItem = strCadenaItem + ","+rstLocIn.getString("ITEMNMBR");
//////////                    strCadenaCant = strCadenaCant + ","+ rstLocIn.getString("nd_can");
//////////                 }
//////////                 
//////////                if((strCadenaItem!="") && (strCadenaCant !=""))
//////////                {
//////////                    if (consultaDisponible(connExt,strCadenaItem,strCadenaCant,strBodega))
//////////                          blnRes =true;
//////////                    else
//////////                          blnRes =false;
//////////                }
//////////                else
//////////                    blnRes=false;                 
//////////                 
//////////                if (!blnRes)
//////////                 {
//////////                         blnRes=false;
//////////                 }
//////////                 else
//////////                 {
//////////                        strSQL ="";
//////////                        strSQL+="\n INSERT  "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_det  \n";
//////////                        strSQL+="  (trd_numtrx,trd_fechatrx,trd_codigomotivo,trd_bodorigen,trd_boddestino,trd_item,trd_cantidad_trf,trd_estado) \n";
//////////                        //values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
//////////                        strSQL+=" VALUES ("+intNumSol +", CAST(CURRENT_TIMESTAMP AS DATE),'"+objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)  +"',\n";
//////////                        strSQL+="         '"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR)  +"','"+objTblMod.getValueAt(i,INT_TBL_DAT_BOD_DES)  +"','"+rstLocIn.getString("ITEMNMBR") +"',"+rstLocIn.getString("nd_can") +",'I'); \n";
//////////                        strSQL+=" \n "; 
//////////                        strSQLGlob = strSQLGlob+ strSQL;
//////////
//////////////                            blnRes=true;                         
//////////                 }
                                      
                                      
                                      

                     
                 
                 
                 

//                    System.out.println("INSERT DETALLE" + strSQL);
//                    rstIns.executeUpdate(strSQL);
                  
                    


        
                 System.out.println("INSERT DETALLE" + strSQLGlob);
                    stmLoc.executeUpdate(strSQLGlob);
                    stmLoc.close();
                    stmLoc=null;
                }
                  
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
   }   

    private boolean actualizarSolicitudAutorizar(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc1;
        int intNumSeg=0;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                
                    strSQL="";
                    
                    strSQL = "";
                    strSQL+= " SELECT MAX(a1.trc_numtrx) as co_seg \n";
                    strSQL+= " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab  as a1  \n";
                    rstLoc1 = stmLoc.executeQuery(strSQL);
                    if (rstLoc1.next()) {
                        intNumSeg=rstLoc1.getInt("co_seg");
                    }
                    rstLoc1.close();
                    rstLoc1=null;
                    
                    
                
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabSolTraInv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
                strSQL+=",tx_numDoc="+ intNumSeg + "";
                strSQL+=",st_aut='A'";
                blnIsSolApr=true;
                if(bodegaNecesitaConfirmacion(i)){
                    strSQL+=" , st_conInv = 'P'";
                }else{
                    strSQL+=" , st_conInv = 'N'";
                }
                strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_motTra='" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+"'";
                System.out.println("UPDATE " + strSQL);
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
    
    private boolean bodegaNecesitaConfirmacion(int i)
    {
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc,rstLoc01;
        String strCadena="";
        boolean blnRes=false,blnConIng=false,blnConEgr=false;
//        try{
//            conLoc = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
//            if(conLoc!=null){
//                stmLoc = conLoc.createStatement();
//                strCadena = "";
//                strCadena+=" SELECT * \n";
//                strCadena+=" FROM tbm_cabSolTraInv as a1 \n";
//                strCadena+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_emp=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP)+" \n";
//                strCadena+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC) + " AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
//                System.out.println("bodegaNecesitaConfirmacion 1 :" + strCadena);
//                rstLoc = stmLoc.executeQuery(strCadena);
//                if(rstLoc.next()){
//                    strCadena="";
//                    strCadena+=" SELECT a1.co_emp, a1.co_cfg, a1.co_bodOrg, a1.co_bodDes, a1.st_conEgr, a1.st_conIng";
//                    strCadena+=" , a1.tx_egrGen, a1.tx_ingGen, a1.tx_obs1, a1.st_reg";
//                    strCadena+=" FROM tbm_cfgbod AS a1";
//                    strCadena+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
//                    strCadena+=" AND a1.co_bodOrg=" + rstLoc.getInt("co_bodOrg") + "";
//                    strCadena+=" AND a1.co_bodDes=" + rstLoc.getInt("co_bodDes") + "";
//                    strCadena+=" AND a1.st_reg='A'";
//                    System.out.println("bodegaNecesitaConfirmacion 2 :" + strCadena);
//                    rstLoc01=stmLoc.executeQuery(strCadena);
//                    if(rstLoc01.next()){
//                        blnConIng=new Boolean((rstLoc01.getObject("st_conIng")==null?false:true));
//                        blnConEgr=new Boolean((rstLoc01.getObject("st_conEgr")==null?false:true));
//                    }
//                    rstLoc01.close();
//                    rstLoc01 = null;
//                }
//                rstLoc.close();
//                rstLoc=null;
//                stmLoc.close();
//                stmLoc=null;
//                if(blnConIng || blnConEgr){
//                    blnRes=true;
//                }
//            }
//            conLoc.close();
//            conLoc=null;
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
        return blnRes;
    }

    private boolean actualizarSolicitudDenegar(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                //Obtener la fecha del servidor.
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                
                strSQL+="UPDATE Dynamics2021.dbo.tbm_cabSolTraInv";
                strSQL+=" SET tx_obsaut=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_OBS_AUT).toString())+"";
                strSQL+=",fe_aut='" + objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos()) +"'";
                strSQL+=",tx_usraut='" + objParSis.getNombreUsuario() +"'";  
                strSQL+=",tx_usrmod='"+ objParSis.getNombreUsuario() + "'";
                strSQL+=",st_aut='D'";
                strSQL+=",st_conInv='N'";
                blnIsSolApr=true;
                strSQL+=" WHERE co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC) + " AND co_motTra='" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC)+"'";
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
    
    private boolean llamarWebTuval()//chiqui
    {
        boolean blnRes=true;
        String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
        String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion/"+strFecSis; // Agregar fecha en este formato ======> 2023-08-02  
        try{
            String salida ="";
            System.out.println("LLAMADA>>>> "+strUrlWebTuval);
            URL url = new URL(strUrlWebTuval);
            HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
            conn1.setRequestMethod("GET");
            conn1.setRequestProperty("Accept", "application/json");
            InputStreamReader entrada = new InputStreamReader (conn1.getInputStream());
            BufferedReader lectura  = new BufferedReader (entrada);
            salida = lectura.readLine();
            conn1.disconnect();
            
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private void llamarWebReposicion()
    {       
           String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
        String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion/"+strFecSis; // Agregar fecha en este formato ======> 2023-08-02  
        try{
            String salida ="";
            System.out.println("LLAMADA>>>> "+strUrlWebTuval);
            URL url = new URL(strUrlWebTuval);
            HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
            conn1.setRequestMethod("GET");
            conn1.setRequestProperty("Accept", "application/json");
            InputStreamReader entrada = new InputStreamReader (conn1.getInputStream());
            BufferedReader lectura  = new BufferedReader (entrada);
            salida = lectura.readLine();
            conn1.disconnect();
            
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    private double TotalItems(java.sql.Connection conExt, int codigo)
    {
        double TotalItems=-1;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01 = null;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc01= conExt.createStatement();

              strSQL="";
                    strSQL+=" select sum (det1.nd_can) as totalCantidad,cab1.co_doc  \n";
                    strSQL+=" from Dynamics2021..tbm_cabSolTraInv as cab1  \n";
                    strSQL+=" 	inner join	Dynamics2021..tbm_detSolTraInv as det1 on (cab1.co_doc = det1.co_doc)    \n";  
                    strSQL+=" WHERE cab1.co_doc = "+ codigo +"  AND cab1.co_motTra ='SOTRIN' \n";   
                    strSQL+="  group by cab1.co_doc  \n";                    
                    
                System.out.println("TotalItems: "+ strSQL);
                rstLoc01=stmLoc01.executeQuery(strSQL);
                while(rstLoc01.next()){
                    TotalItems= rstLoc01.getDouble("totalCantidad");
                }
                rstLoc01.close();
                rstLoc01=null;
                stmLoc01.close();
                stmLoc01=null;
            }
            else{
                TotalItems=-1;
            }
            
        }
        catch (java.sql.SQLException e) { 
                TotalItems=-1;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
                TotalItems=-1;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return TotalItems;
    }
    
    private boolean validacionesIngreso(java.sql.Connection conExt, int i)
    {
        boolean blnRes=true, blnItem=true;
        double totalCantidad=0;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc = null, rstLoc01;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc= conExt.createStatement();
                totalCantidad = TotalItems(conExt,Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()) );
                if(totalCantidad>-1)
                {
                  strSQL="";
                        strSQL+=" select cab.trc_fechatrx,cab.trc_bodorigen,cab.trc_boddestino,cab.trc_numitems ,trc_estado, tot1.totalCantidad\n";
                        strSQL+=" from "+objParSis.getNombreBaseDatosGP()+"..tbmsw_transferencia_cab as cab \n";
                        strSQL+=" 	left join (  \n";     
                        strSQL+="               select sum (trd_cantidad_trf) as totalCantidad,cab1.trc_numtrx\n";     
                        strSQL+=" 		    from "+objParSis.getNombreBaseDatosGP()+"..tbmsw_transferencia_cab as cab1\n";     
                        strSQL+="                   inner join	"+objParSis.getNombreBaseDatosGP()+"..tbmsw_transferencia_det as det1 on (cab1.trc_numtrx = det1.trd_numtrx)  \n";
                        strSQL+="               group by cab1.trc_numtrx  \n";  
                        strSQL+="             )as tot1 on cab.trc_numtrx = tot1.trc_numtrx \n";  
                        strSQL+=" where cab.trc_estado ='I' and cab.trc_bodorigen = '"+ objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR) +"'  \n";           
                        strSQL+="       and  cab.trc_boddestino = '"+ objTblMod.getValueAt(i,INT_TBL_DAT_BOD_DES) +"'  \n";      
                        strSQL+="       and  cab.trc_numitems = "+ objTblMod.getValueAt(i,INT_TBL_DAT_NUM_MAX) +"  \n";  
                        strSQL+="       and trc_codigomotivo='SOTRIN'  \n";    
                        strSQL+="       AND tot1.totalCantidad = "+ totalCantidad  +"\n";   
                        strSQL+="       AND DATEDIFF(DAY, cab.trc_fechatrx, GETDATE()) <= 3  \n";                           

                    System.out.println("validacionesIngreso: "+ strSQL);
                    rstLoc=stmLoc.executeQuery(strSQL);
                    while(rstLoc.next()){
                                blnRes=false;
                    }                   
                }
                else
                    blnItem=false;

            if(blnItem){
                if(!blnRes){
                    strMsg="<html> La solicitud ya se encuentra ingresada en espera de ser procesada. <BR><BR>" ;// CAMBIA
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                    strMsg="<html> La solicitud no tiene items ingresados. <BR><BR>" ;// CAMBIA
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
            }

                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
            
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    } 
    
    private boolean consultaDisponible(java.sql.Connection conExt, String items,String cantidades,String bodega)//chiqui
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
                strSql+="{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
                System.out.println("iniciaProcesoOT:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("ITEMS",  items);//Envio de parametro
                cal.setString("CANTIDADES",  cantidades);//Envio de parametro
                cal.setString("BODEGA",  bodega   );//Envio de parametro
                
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

    private boolean validacionTiempo(java.sql.Connection conExt)
    {
            java.sql.Statement stmLoc,stmLoc01;
            java.sql.ResultSet rstLoc = null, rstLoc01;
            boolean  blnRes=false;
            try{
                if(conExt!=null)
                {
                    stmLoc= conExt.createStatement();
                      strSQL="";
                      strSQL+=" select CONVERT(VARCHAR(16), fe_ultRepTran, 120) as fe_ultRepTran,ne_tiereptraesp\n";
                      strSQL+=" from Dynamics2021..tbm_emp  \n";
                      strSQL+=" where co_emp =1 \n";           

                        System.out.println("validacionesIngreso: "+ strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);
                        if (rstLoc.next())
                        {
                            fe_ultmod =rstLoc.getString("fe_ultRepTran");
                            ne_tieespe=rstLoc.getInt("ne_tiereptraesp");
                            blnRes =true;
                        }   
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
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
            return blnRes;
        }

    private boolean validacionCheck()
    {
        int intNumFil=objTblMod.getRowCountTrue();
        int contador =0;
        boolean blnres =true;
        for (int i=0; i<intNumFil;i++)
        {
            if((objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT))|| (objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)))
            contador++;
        }
        if (contador >1)
                blnres =false;

        return blnres;
    }
    
    private boolean guardarAutorizacion()
    {
        java.sql.Connection conLoc;
        boolean blnRes=false,validaLLamada=false;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");      
        try{
            lblMsgSis.setText("Guardando datos...");
            pgrSis.setIndeterminate(true);
            int intNumFil=objTblMod.getRowCountTrue();
            
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());            
            fe_ultmod ="";
            ne_tieespe=-1;
            if (validacionTiempo(con))
            {
                if (validacionCheck())
                {
                    
                       Date fecha1 = formato.parse(fe_ultmod);
                       Date fechaActual = new Date();
                       Calendar calendario = Calendar.getInstance();
                       calendario.setTime(fecha1);
                       calendario.add(Calendar.MINUTE, ne_tieespe);
                       Date nuevaFecha1 = calendario.getTime();
                       long diferenciaEnMilisegundos = fechaActual.getTime() - fecha1.getTime();
                       long diferenciaEnMinutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaEnMilisegundos);

                        if (nuevaFecha1.before(fechaActual))                     
                        {
                            for (int i=0; i<intNumFil;i++){
                                if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M")){
                                    //conLoc=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());

                                    if(con!=null){
                                        con.setAutoCommit(false);
                                        if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_AUT))
                                        {

                                            if (validacionesIngreso(con,i))
                                            {
                                                
                                                 if (validacionDisponible(con,i))   
                                                 {
                                                     if (validacionUbicaciones(con,strCadeItemsGlo))
                                                     {
                                                       if (insertarCabSolicitudAutorizarGP(con,i))
                                                       {
                        //                                        con.commit();
                        //                                        blnRes=true;
                                                            if(ValidaSolicitudAutorizarGPDisponible(con,i))
                                                            {
                                                                if (insertarDetSolicitudAutorizarGP(con , i))
                                                                {
        ////                                                            con.commit();
        ////                                                            blnRes=true;
                                                                    if(actualizarSolicitudAutorizar(con,i))
                                                                    {
                                                                         con.commit();
                                                                         blnRes=true;

                                                                    }
                                                                    else
                                                                    {
                                                                        con.rollback();
                                                                        blnRes=false;
                                                                        System.err.println("Error actualizarSolicitudAutorizar");
                                                                    }                                                
                                                                }
                                                                else
                                                                {
                                                                    con.rollback();
                                                                    blnRes=false;
                                                                    System.err.println("Error insertarDetSolicitudAutorizarGP");
                                                                 }


                                                            }
                                                            else
                                                            {
                                                                con.rollback();
                                                                blnRes=false;
                                                                System.err.println("Error ValidaSolicitudAutorizarGPDisponible");
                                                            }
                                                        }
                                                        else
                                                        {
                                                            con.rollback();
                                                            blnRes=false;
                                                            System.err.println("Error insertarcabGP");
                                                        } 
                                                     }
                                                 }  
                                            }
                                            else
                                            {
                                                    blnRes=false;
                                                    System.err.println("Error validarStockDisponible");                                
                                            }  

                                        }
                                        else if(objTblMod.isChecked(i,INT_TBL_DAT_CHK_DEN)){
                                            if(actualizarSolicitudDenegar(con, i)){
                                                blnRes=true;
                                            }else{blnRes=false;}
                                        }

                                        if(blnRes==true){
                                            con.commit();
                                        }
                                        else{
                                            con.rollback();
                                        }
                                        con.close();
                                        conLoc=null;
                                        blnRes=false;
                                    }
                                }
                            }   
                            if(blnRes==true)
                            {
                                 if(llamarWebTuval())
                                 {
                                     llamarWebReposicion();
                                     lblMsgSis.setText("LLamada Web OK...");
                                     lblMsgSis.setText("Transferencias WEB Actualizadas...");
                                     pgrSis.setIndeterminate(true);
                                     blnRes=true;                     
                                 }
                                 else
                                 {
                                     lblMsgSis.setText("Transferencias WEB NO Actualizadas...");
                                     pgrSis.setIndeterminate(true);  
                                     lblMsgSis.setText("LLamada Web ERROR..."); 
                                     blnRes=false;
                                 }

                            }                        
                        }
                        else
                            mostrarMsgInf("Por cada transferencia se debe esperar "+ne_tieespe +" minutos ,faltan "+ diferenciaEnMinutos +" minutos para poder realizar otra transferencia.");  

                }
                else
                    mostrarMsgInf(" Solo se puede realizar una aprobacion / denegacion al mismo tiempo...");  
                  
            }
            else 
                mostrarMsgInf("No se tiene una configuraciòn de tiempo de espera.");
                
            pgrSis.setIndeterminate(false);
            ////con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            cargarDetReg(con);
            con.close();
            con=null;
            blnRes=true;
       }
       catch (java.sql.SQLException e) { 
           objUti.mostrarMsgErr_F1(this, e);  
       }
       catch(Exception  Evt){ 
           objUti.mostrarMsgErr_F1(this, Evt);
       }  
       return blnRes;
    }
    
    private boolean llamaObjetoCfgBod(java.sql.Connection conn, int intCodBodOrg, int intCodBodDes)
    {
        boolean blnRes=true;
        return blnRes;
    }
    
    private boolean llamaObjetoCfgBod(java.sql.Connection conn, int intCodEmp,int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
       int intCodBodOrg=0, intCodBodDes=0;
       boolean blnRes=true;
       return blnRes;
    }
    
    private boolean llamadoGeneracionPrestamos(java.sql.Connection conn,int i)
    {
        int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc;
        boolean blnRes=true;
        return blnRes;
    }
    
    private boolean procesoImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=false;
        return blnRes;
        
    }

    private boolean validaIsProcesoImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=false;
       return blnRes;        
    }
    
    private boolean insertaRelacionImportaciones(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
       return blnRes;
        
    }
    
    private boolean cierreAjusteInventarioImportaciones(java.sql.Connection conn, int i)//chiaquiNOVA
    {
        boolean blnRes=true;
       return blnRes;        
    }  
    
    private void llenarArraySolTra(java.sql.Connection conn, int i)
    {
    }
    
    private boolean blnIsImportacion=false;    
    private boolean validarStockDisponible(java.sql.Connection conExt, String  item, String bodega, double cantidad)
    {
        boolean blnRes=true, blnIsStock=true, blnIsBodIngImp=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc= conExt.createStatement();

              strSQL="";
                    strSQL+=" select iv.DEX_ROW_ID as co_item ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITMGEDSC) as tx_codAlt2,RTRIM(iv.ITEMDESC) as tx_nomItm, \n";
                    strSQL+="        RTRIM(iv.UOMSCHDL) as tx_desCor,inf.Peso_KG as nd_pesItmKgr, v.DISPONIBLE as nd_canDis,iv.CURRCOST,RTRIM(v.LOCNCODE) as bodega     \n";
                    strSQL+=" from "+objParSis.getNombreBaseDatosGP()+"..vw_consulta_disponible_transito as v \n";
                    strSQL+="      left join "+objParSis.getNombreBaseDatosGP()+"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
                    strSQL+="      left join "+objParSis.getNombreBaseDatosGP()+"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";
                    strSQL+=" where   v.LOCNCODE ='"+ bodega +"'  and  v.ITEMNMBR ='"+ item  +"' \n";
                    //strSQL+=" ORDER BY  v.ITEMNMBR desc  \n";
                    
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    if(blnIsImportacion){
                        //Valida que la bodega origen de la solicitud corresponda a la bodega del ingreso por importacion.
                        if(!(rstLoc.getString("co_bod")).equals(rstLoc.getString("co_bodIngImp"))){
                            blnRes=false;
                            blnIsBodIngImp=false;
                        }
                    }
                    
                    if(blnIsBodIngImp)
                    {
                        //Valida disponible
                        if(Double.parseDouble(rstLoc.getString("nd_canDis"))<cantidad){
                           blnRes=false;
                           blnIsStock=false;
                           strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canDis"), objParSis.getDecimalesMostrar()) + " </td> ";
                           strItem+=" <td>" + objUti.redondear(cantidad, objParSis.getDecimalesMostrar())  + " </td>";

                           if(blnIsImportacion)
                           {
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canIngImp"), objParSis.getDecimalesMostrar()) + " </td> ";
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canTran"), objParSis.getDecimalesMostrar()) + " </td> ";
                           }
                           strItem+=" </tr> ";
                        }
                    }
                }
                if(!blnIsBodIngImp) {
                    mostrarMsgInf("La solicitud que desea autorizar tiene la bodega origen distinta a la del ingreso por importación");
                }
                
                if(!blnIsStock){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    if(blnIsImportacion){
                        strMsg+="<td> Ingreso Importación </td>";
                        strMsg+="<td> Transferencias </td>";
                    }
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    //System.out.println(strMsg);
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
            
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    } 
    
    private boolean insertaSeguimiento(java.sql.Connection conn, int i)
    {
        boolean blnRes=true;
        return blnRes;
    }

    private boolean obtenerEmpresaItemBodega(int intCodBodDes,int intCodItm,int intCodBodOrg)
    {
       boolean blnRes=true;
       return blnRes;
    }
    
    private boolean obtenerItemBodega(int intCodEmp, int intCodBodDes,int intCodItm,int intCodBodOrg)
    {
        boolean blnRes=true;
        return blnRes;
    }
    
    private boolean obtenerCantidadNecesaria(java.sql.Connection conn, Double dblCanNec, int intCodItm, int intCodBodGrp)
    {
        boolean blnRes=false;
        return blnRes;
    }
    
    private boolean ordenarLasTransferencias()
    {
        boolean blnRes=false;
        return blnRes;
    }
    
    public boolean generaTransferenciaPreAutorizada(java.sql.Connection conn, int intCodEmp, int intCodLoc,int intCodTipDoc, int intCodDoc)
    {
       boolean blnRes=false;
       return blnRes;
    }
    
    private boolean insertaSeguimiento(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)
    {
        boolean blnRes=true;
        return blnRes;
    }
    
    private boolean generaPrestamos(java.sql.Connection conLoc, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc)//CHIQUINOVA
    {
        boolean blnRes=true,isEnviaSolicitud=true;
        return blnRes;
    }    

    private boolean validacionDisponible(java.sql.Connection conExt,int i)
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLocIn;        
        boolean blnRes=true;
        double totalCantidad=0;
        String strCadenaItem="",strCadenaCant="";  
        strCadeItemsGlo="";      
        strBodegaGlob="";
        int contador =0;

        try
        {
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                strSQL ="";
                strSQL+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.UOMSCHDL,isnUll(a1.nd_can,0) as nd_can,  \n";
                strSQL+="        a2.STNDCOST,(a1.nd_can * a2.STNDCOST) as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                strSQL+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1   \n";
                strSQL+="  INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
                strSQL+="  WHERE co_motTra='" +objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC) + "' AND a1.co_doc= "+ objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC)  +"\n";
                strSQL+="  ORDER BY a1.co_reg";
                rstLocIn=stmLoc.executeQuery(strSQL);
                System.out.println("SELECT  DETALLE DE SOLICITUD" + strSQL);  
                strBodegaGlob =objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString();
                strCadenaItem="";
                strCadenaCant="";
                while(rstLocIn.next())
                {
                    strCadenaItem = objUti.codificar(rstLocIn.getString("ITEMNMBR"))+","+ strCadenaItem;
                    strCadenaCant = rstLocIn.getString("nd_can")+","+ strCadenaCant;                         
                } 
                if (strCadenaItem.endsWith(",")) 
                    strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                if (strCadenaCant.endsWith(",")) 
                    strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                

                if((strCadenaItem!="") && (strCadenaCant !=""))
                {
                    strCadeItemsGlo=strCadenaItem;
                    if (consultarReservadosDesdeWeb(strCadenaItem,strCadenaCant,strBodegaGlob))
                    blnRes =true;
                    else
                    blnRes =false;
                }
                else
                    blnRes=false;           
            }            
        }  
           catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
           catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
           return blnRes;        
    }   

    
    private boolean validacionDisponibleReproceso(java.sql.Connection conExt,int i,String tipo)
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLocIn;        
        boolean blnRes=true;
        double totalCantidad=0;
        String strCadenaItem="",strCadenaCant="";  
        strCadeItemsGloRepr="";      
        strBodegaGlobRepr ="";
        int contador =0;
        String strSQLInt="";

        try
        {
            if (conExt!=null)
            {
                stmLoc=conExt.createStatement();
                strSQLInt ="";
                if (tipo.equals("SOTRIN"))//para SOTRIN
                {
                    strSQLInt+=" SELECT RTRIM(a1.co_motTra) as co_motTra,a1.co_reg,a2.DEX_ROW_ID,RTRIM(a2.ITEMNMBR) as ITEMNMBR,RTRIM(a2.ITEMDESC) as ITEMDESC,a2.UOMSCHDL,isnUll(a1.nd_can,0) as nd_can,  \n";
                    strSQLInt+="        a2.STNDCOST,(a1.nd_can * a2.STNDCOST) as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                    strSQLInt+=" FROM  Dynamics2021.dbo.tbm_detSolTraInv as a1   \n";
                    strSQLInt+="  INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 a2 on (a1.co_itm =a2.ITEMNMBR) \n";
                    strSQLInt+="  WHERE co_motTra='SOTRIN' AND a1.co_doc= "+ objTblModTra.getValueAt(i,INT_TBL_DAT_TRA_COD_DOC)  +"\n";
                    strSQLInt+="  ORDER BY a1.co_reg";                    
                }
                else
                {
                    if (tipo.equals("REPOSICIONES"))//para REPOSICIONES
                    {
                        strSQLInt+=" SELECT RTRIM(a2.ITEMNMBR) as ITEMNMBR,isnUll(det.trd_cantidad_trf,0) as nd_can  \n";
                        strSQLInt+="        a2.STNDCOST,(a1.nd_can * a2.STNDCOST) as nd_cosTot,a1.nd_pesounikgr ,a1.nd_pesototkgr,a2.ITMGEDSC \n";
                        strSQLInt+=" FROM  "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_det as det   \n";
                        strSQLInt+="    INNER JOIN "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 a2 on (det.trd_item =a2.ITEMNMBR) \n";
                        strSQLInt+=" WHERE det.trd_codigomotivo='REPOSICION' AND det.trd_numtrx= "+ objTblModTra.getValueAt(i,INT_TBL_DAT_TRA_COD_DOC)  +"\n";
                        strSQLInt+="  ORDER BY det.trd_row_id";                        
                        
                    }
                                        
                }

                rstLocIn=stmLoc.executeQuery(strSQLInt);
                System.out.println("validacionDisponibleReproceso: "+ strSQLInt);
                
                strBodegaGlobRepr =objTblModTra.getValueAt(i,INT_TBL_DAT_TRA_BOD_ORI).toString();
                strCadenaItem="";
                strCadenaCant="";
                while(rstLocIn.next())
                {
                    strCadenaItem = objUti.codificar(rstLocIn.getString("ITEMNMBR"))+","+ strCadenaItem;
                    strCadenaCant = rstLocIn.getString("nd_can")+","+ strCadenaCant;                         
                } 
                if (strCadenaItem.endsWith(",")) 
                    strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                if (strCadenaCant.endsWith(",")) 
                    strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                

                if((strCadenaItem!="") && (strCadenaCant !=""))
                {
                    strCadeItemsGloRepr=strCadenaItem;
                    if (consultarReservadosDesdeWebReproceso(strCadenaItem,strCadenaCant,strBodegaGlobRepr))
                        blnRes =true;
                    else
                        blnRes =false;
                }
                else
                    blnRes=false;           
            }            
        }  
           catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
           catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
           return blnRes;        
    } 
    
    private boolean validacionUbicaciones(java.sql.Connection conExt,String cadenaItems)
    {
            boolean blnRes=true, blnItem=true;
            java.sql.Statement stmLoc,stmLoc01;
            java.sql.ResultSet rstLoc = null, rstLoc01;
    ////        cadenaItems="'958-000059-I','950-000024-S','025-000056-I','869-000039-I','950-000015-S','882-000003-S','525-000035-I','869-000036-I','950-000026-S','025-000170-I','025-000167-I','025-000209-I','025-000135-I','025-000150-I','026-000108-I','889-000035-S','023-000212-I','023-000199-I','025-000048-I','026-000008-I','025-000133-I','025-000129-I','025-000104-I','025-000091-I','025-000214-I','024-000032-I','027-000043-I','025-000050-I','873-000033-I','876-000119-I','952-000032-I','028-000318-I','040-000026-I','961-000117-I','023-000193-I','023-000203-I','020-000205-I','949-000014-I','945-000068-I','876-000218-I','897-000014-S','024-000054-I','029-000199-I','521-000053-I','949-000019-I','945-000090-I','521-000110-I','961-000118-I','038-000070-I','953-000143-I','953-000027-I','951-000001-I','880-000056-I','882-000014-S','945-000031-I','040-000073-I','020-000211-I','020-000183-I','020-000084-I','028-000140-I','945-000046-I','880-000053-I','020-000209-I','021-000073-I'";
            String  cadenaItemsLimpia ="";
            ArrayList<String> lstItemsDuplicados = new ArrayList<>();
            try{
                if(conExt!=null){
                    stmLoc= conExt.createStatement();
                    listaUbicacion.clear();
                    cadenaItemsLimpia= cadenaItems.replace("''", "'");

                      strSQL="";
                            strSQL+=" SELECT iv102.ITEMNMBR, iv102.BIN ,trim (iv01.ITMGEDSC) as tresl, trim(iv01.ITMSHNAM) as itemzaf\n";
                            strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112 AS iv102 \n";
                            strSQL+="           inner join "+objParSis.getNombreBaseDatosGP()+"..IV00101 as iv01 on (iv102.ITEMNMBR = iv01.ITEMNMBR)  \n";                        
                            strSQL+=" WHERE iv102.LOCNCODE = '"+ strBodegaGlob +"'  \n";           
                            strSQL+="       AND iv102.ITEMNMBR IN ( \n";      
                            strSQL+="                               SELECT ITEMNMBR \n";  
                            strSQL+="                               FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112  \n"; 
                            strSQL+="                               WHERE LOCNCODE = '"+ strBodegaGlob +"'  \n";  
                            strSQL += "                                    AND iv102.ITEMNMBR IN (" + cadenaItemsLimpia + ") \n";
                            strSQL+="                               GROUP BY ITEMNMBR \n";   
                            strSQL+="                               HAVING COUNT(*) > 1 ) \n";                           
                            strSQL+=" ORDER BY iv102.ITEMNMBR DESC, iv102.BIN;  \n";                           

                        System.out.println("validacionUbicaciones: "+ strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);

                        while(rstLoc.next())
                        {
                            blnRes=false;
                            HashMap<String, Object> filaUbi = new HashMap<>();
                            filaUbi.put("ITEM", rstLoc.getString("ITEMNMBR"));
                            filaUbi.put("BIN", rstLoc.getString("BIN"));
                            filaUbi.put("tresl", rstLoc.getString("tresl"));
                            filaUbi.put("itemzaf", rstLoc.getString("itemzaf"));

                            listaUbicacion.add(filaUbi);                                
                        }                   



                    String strMsgDisp="";
                    if (!listaUbicacion.isEmpty()) 
                    {

                        strMsgDisp="<HTML>Se encontraron ubicaciones duplicadas para los siguientes items <BR><BR>";
                        strMsgDisp+="<table BORDER=1><tr><td> ITEM </td> <td> 3L </td><td> ITEM ZAF </td><td> UBICACION </td>";                    
                        for (HashMap<String, Object> filaUbi : listaUbicacion)
                        {
                            strMsgDisp+="<tr><td>" +  filaUbi.get("ITEM") + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("tresl")  + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("itemzaf") + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("BIN")  + "</td>";

                        }
                            strMsgDisp+="</table><BR> <html>";
                            //strMsgDisp+="No poseen stock o se encuentran dentro de un PEDIDO <html>";
                            mostrarMsgInf(strMsgDisp);
                    }




    ////                if(!blnRes)
    ////                {
    ////                    StringBuilder mensajeItems = new StringBuilder();
    ////                   // mensajeItems.append("<html>Se encontraron ubicaciones duplicadas para los siguientes ítems:<br><br>");
    ////                    for (String itemBin : lstItemsDuplicados) {
    ////                        mensajeItems.append(itemBin).append("<br>");
    ////                    }
    ////                    mensajeItems.append("</html>");
    ////                    JOptionPane.showMessageDialog(this, mensajeItems.toString(), "Mensaje del sistema Zafiro", JOptionPane.ERROR_MESSAGE);
    ////                }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                else{
                    blnRes=false;
                }

            }
            catch (java.sql.SQLException e) { 
                blnRes=false;  
                objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }  
            return blnRes;
        }   
    
    private boolean validacionUbicacionesReproceso(java.sql.Connection conExt,String cadenaItems)
    {
            boolean blnRes=true, blnItem=true;
            java.sql.Statement stmLoc,stmLoc01;
            java.sql.ResultSet rstLoc = null, rstLoc01;
    ////        cadenaItems="'958-000059-I','950-000024-S','025-000056-I','869-000039-I','950-000015-S','882-000003-S','525-000035-I','869-000036-I','950-000026-S','025-000170-I','025-000167-I','025-000209-I','025-000135-I','025-000150-I','026-000108-I','889-000035-S','023-000212-I','023-000199-I','025-000048-I','026-000008-I','025-000133-I','025-000129-I','025-000104-I','025-000091-I','025-000214-I','024-000032-I','027-000043-I','025-000050-I','873-000033-I','876-000119-I','952-000032-I','028-000318-I','040-000026-I','961-000117-I','023-000193-I','023-000203-I','020-000205-I','949-000014-I','945-000068-I','876-000218-I','897-000014-S','024-000054-I','029-000199-I','521-000053-I','949-000019-I','945-000090-I','521-000110-I','961-000118-I','038-000070-I','953-000143-I','953-000027-I','951-000001-I','880-000056-I','882-000014-S','945-000031-I','040-000073-I','020-000211-I','020-000183-I','020-000084-I','028-000140-I','945-000046-I','880-000053-I','020-000209-I','021-000073-I'";
            String  cadenaItemsLimpia ="";
            ArrayList<String> lstItemsDuplicados = new ArrayList<>();
            try{
                if(conExt!=null){
                    stmLoc= conExt.createStatement();
                    listaUbicacionRepr.clear();
                    cadenaItemsLimpia= cadenaItems.replace("''", "'");

                      strSQL="";
                            strSQL+=" SELECT iv102.ITEMNMBR, iv102.BIN ,trim (iv01.ITMGEDSC) as tresl, trim(iv01.ITMSHNAM) as itemzaf\n";
                            strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112 AS iv102 \n";
                            strSQL+="           inner join "+objParSis.getNombreBaseDatosGP()+"..IV00101 as iv01 on (iv102.ITEMNMBR = iv01.ITEMNMBR)  \n";                        
                            strSQL+=" WHERE iv102.LOCNCODE = '"+ strBodegaGlobRepr +"'  \n";           
                            strSQL+="       AND iv102.ITEMNMBR IN ( \n";      
                            strSQL+="                               SELECT ITEMNMBR \n";  
                            strSQL+="                               FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112  \n"; 
                            strSQL+="                               WHERE LOCNCODE = '"+ strBodegaGlobRepr +"'  \n";  
                            strSQL += "                                    AND iv102.ITEMNMBR IN (" + cadenaItemsLimpia + ") \n";
                            strSQL+="                               GROUP BY ITEMNMBR \n";   
                            strSQL+="                               HAVING COUNT(*) > 1 ) \n";                           
                            strSQL+=" ORDER BY iv102.ITEMNMBR DESC, iv102.BIN;  \n";                           

                        System.out.println("validacionUbicacionesReproceso: "+ strSQL);
                        rstLoc=stmLoc.executeQuery(strSQL);

                        while(rstLoc.next())
                        {
                            blnRes=false;
                            HashMap<String, Object> filaUbi = new HashMap<>();
                            filaUbi.put("ITEM", rstLoc.getString("ITEMNMBR"));
                            filaUbi.put("BIN", rstLoc.getString("BIN"));
                            filaUbi.put("tresl", rstLoc.getString("tresl"));
                            filaUbi.put("itemzaf", rstLoc.getString("itemzaf"));

                            listaUbicacionRepr.add(filaUbi);                                
                        }                   



                    String strMsgDisp="";
                    if (!listaUbicacionRepr.isEmpty()) 
                    {

                        strMsgDisp="<HTML>Se encontraron ubicaciones duplicadas para los siguientes items <BR><BR>";
                        strMsgDisp+="<table BORDER=1><tr><td> ITEM </td> <td> 3L </td><td> ITEM ZAF </td><td> UBICACION </td>";                    
                        for (HashMap<String, Object> filaUbi : listaUbicacionRepr)
                        {
                            strMsgDisp+="<tr><td>" +  filaUbi.get("ITEM") + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("tresl")  + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("itemzaf") + "</td>";
                            strMsgDisp+="<td>" + filaUbi.get("BIN")  + "</td>";

                        }
                            strMsgDisp+="</table><BR> <html>";
                            //strMsgDisp+="No poseen stock o se encuentran dentro de un PEDIDO <html>";
                            mostrarMsgInf(strMsgDisp);
                    }




    ////                if(!blnRes)
    ////                {
    ////                    StringBuilder mensajeItems = new StringBuilder();
    ////                   // mensajeItems.append("<html>Se encontraron ubicaciones duplicadas para los siguientes ítems:<br><br>");
    ////                    for (String itemBin : lstItemsDuplicados) {
    ////                        mensajeItems.append(itemBin).append("<br>");
    ////                    }
    ////                    mensajeItems.append("</html>");
    ////                    JOptionPane.showMessageDialog(this, mensajeItems.toString(), "Mensaje del sistema Zafiro", JOptionPane.ERROR_MESSAGE);
    ////                }
                    rstLoc.close();
                    rstLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                }
                else{
                    blnRes=false;
                }

            }
            catch (java.sql.SQLException e) { 
                blnRes=false;  
                objUti.mostrarMsgErr_F1(this, e);  
            }
            catch(Exception  Evt){ 
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, Evt);
            }  
            return blnRes;
        }  
    

    private boolean consultarReservadosDesdeWeb(String items,String cantidades,String bodega) 
    {
    java.sql.Connection conn = null;
    java.sql.CallableStatement cstmt = null;
    java.sql.ResultSet rs = null;
    listaReservas.clear();
    

    boolean exito = true;
    
    HashMap<String, Object> hashFila = new HashMap<>();

    try {
        conn = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );
        
        System.out.println(""+ items );

        String procedimiento = "{call dbo.TUVAL_ACTUALIZA_RESERVADO_WEB_pr(?, ?, ?, ?)}";
        cstmt = conn.prepareCall(procedimiento);

////         items = "'025-000067-I','025-000127-I','025-000024-I','025-000190-I','028-000226-I','028-000167-I','028-000268-I','943-000300-I','561-000061-S','028-000100-I','024-000052-I','033-000014-S','024-000069-I','028-000021-I','966-000175-I','040-000055-I','953-000011-I','943-000253-I','877-000083-I','970-000077-I','873-000095-I','943-000307-I','953-000118-I','040-000048-I','038-000061-I','038-000042-I','029-000191-I','028-000017-I','028-000093-I','450-000057-I','028-000154-I','020-000147-I','700-000045-I','524-000010-I','028-000365-I','026-000089-I','037-000052-I','028-000334-I','037-000098-I','018-000035-I','900-000001-S','700-000192-I','024-000079-I','037-000048-I','099-000012-I','562-000034-S','953-000065-I','953-000064-I','023-000238-I','945-000001-I','560-000004-S','038-000106-I'";
////         cantidades = "1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0";
////         bodega = "VIA_DAULE";

        cstmt.setString(1, items);
        cstmt.setString(2, cantidades);
        cstmt.setString(3, bodega);
        cstmt.registerOutParameter(4, java.sql.Types.INTEGER); // Output param

        boolean hayResultados = cstmt.execute();

        if (hayResultados) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                HashMap<String, Object> fila = new HashMap<>();
                fila.put("CODIGO", rs.getString("CODIGO"));
                fila.put("BODEGA", rs.getString("BODEGA"));
                fila.put("CANTIDADPEDIDA", rs.getBigDecimal("CANTIDADPEDIDA"));
                fila.put("DISPONIBLEGP", rs.getBigDecimal("DISPONIBLEGP"));
                fila.put("RESERVADOGP", rs.getBigDecimal("RESERVADOGP"));
                fila.put("RESERVADOWEB", rs.getBigDecimal("RESERVADOWEB"));
                fila.put("RESERVADOFINAL", rs.getBigDecimal("RESERVADOFINAL"));
                fila.put("CANTIDADDISPONIBLE", rs.getBigDecimal("CANTIDADDISPONIBLE"));
                listaReservas.add(fila);
                exito=false;
            }
        }

        int resultadoSalida = cstmt.getInt(4);
        System.out.println("Código de resultado SP: " + resultadoSalida);

                        
                        
                String strMsgDisp="";
                if (!listaReservas.isEmpty()) 
                {
                    
                    strMsgDisp="<HTML>Se tiene los siguientes items sin disponibilidad: <BR><BR>";
                    strMsgDisp+="<table BORDER=1><tr><td> ITEM </td> <td> CANTIDAD PEDIDA </td><td> RESERVA WEB </td><td> DISPONIBLE GP </td><td> CANT.DISPONIBLE </td>";                    
                    for (HashMap<String, Object> fila : listaReservas)
                    {
                        strMsgDisp+="<tr><td>" +  fila.get("CODIGO") + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADPEDIDA")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("RESERVADOWEB") + "</td>";
                        strMsgDisp+="<td>" + fila.get("DISPONIBLEGP")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADDISPONIBLE")  + "</td>";

                    }
                        strMsgDisp+="</table><BR>";
                        strMsgDisp+="No poseen stock o se encuentran dentro de un PEDIDO <html>";
                        mostrarMsgInf(strMsgDisp);
                }
                
                
        

    } catch (Exception e) {
        System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        exito = false;
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return exito;
}
    
    private boolean consultarReservadosDesdeWebReproceso(String items,String cantidades,String bodega) 
    {
    java.sql.Connection conn = null;
    java.sql.CallableStatement cstmt = null;
    java.sql.ResultSet rs = null;
    listaReservasRepr.clear();
    

    boolean exito = true;
    
    HashMap<String, Object> hashFila = new HashMap<>();

    try {
        conn = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );
        
        System.out.println(""+ items );

        String procedimiento = "{call dbo.TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
        cstmt = conn.prepareCall(procedimiento);

////         items = "'025-000067-I','025-000127-I','025-000024-I','025-000190-I','028-000226-I','028-000167-I','028-000268-I','943-000300-I','561-000061-S','028-000100-I','024-000052-I','033-000014-S','024-000069-I','028-000021-I','966-000175-I','040-000055-I','953-000011-I','943-000253-I','877-000083-I','970-000077-I','873-000095-I','943-000307-I','953-000118-I','040-000048-I','038-000061-I','038-000042-I','029-000191-I','028-000017-I','028-000093-I','450-000057-I','028-000154-I','020-000147-I','700-000045-I','524-000010-I','028-000365-I','026-000089-I','037-000052-I','028-000334-I','037-000098-I','018-000035-I','900-000001-S','700-000192-I','024-000079-I','037-000048-I','099-000012-I','562-000034-S','953-000065-I','953-000064-I','023-000238-I','945-000001-I','560-000004-S','038-000106-I'";
////         cantidades = "1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0";
////         bodega = "VIA_DAULE";

        cstmt.setString(1, items);
        cstmt.setString(2, cantidades);
        cstmt.setString(3, bodega);
        cstmt.registerOutParameter(4, java.sql.Types.INTEGER); // Output param

        boolean hayResultados = cstmt.execute();

        if (hayResultados) {
            rs = cstmt.getResultSet();
            while (rs.next()) {
                HashMap<String, Object> fila = new HashMap<>();
                fila.put("CODIGO", rs.getString("CODIGO"));
                fila.put("BODEGA", rs.getString("BODEGA"));
                fila.put("CANTIDADPEDIDA", rs.getBigDecimal("CANTIDADPEDIDA"));
                fila.put("DISPONIBLEGP", rs.getBigDecimal("DISPONIBLEGP"));
                fila.put("RESERVADOGP", rs.getBigDecimal("RESERVADOGP"));
                fila.put("RESERVADOWEB", rs.getBigDecimal("RESERVADOWEB"));
                fila.put("RESERVADOFINAL", rs.getBigDecimal("RESERVADOFINAL"));
                fila.put("CANTIDADDISPONIBLE", rs.getBigDecimal("CANTIDADDISPONIBLE"));
                listaReservasRepr.add(fila);
                exito=false;
            }
        }

        int resultadoSalida = cstmt.getInt(4);
        System.out.println("Código de resultado SP: " + resultadoSalida);

                        
                        
                String strMsgDisp="";
                if (!listaReservasRepr.isEmpty()) 
                {
                    
                    strMsgDisp="<HTML>Se tiene los siguientes items sin disponibilidad: <BR><BR>";
                    strMsgDisp+="<table BORDER=1><tr><td> ITEM </td> <td> CANTIDAD PEDIDA </td><td> RESERVA WEB </td><td> DISPONIBLE GP </td><td> CANT.DISPONIBLE </td>";                    
                    for (HashMap<String, Object> fila : listaReservasRepr)
                    {
                        strMsgDisp+="<tr><td>" +  fila.get("CODIGO") + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADPEDIDA")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("RESERVADOWEB") + "</td>";
                        strMsgDisp+="<td>" + fila.get("DISPONIBLEGP")  + "</td>";
                        strMsgDisp+="<td>" + fila.get("CANTIDADDISPONIBLE")  + "</td>";

                    }
                        strMsgDisp+="</table><BR>";
                        strMsgDisp+="No poseen stock o se encuentran dentro de un PEDIDO <html>";
                        mostrarMsgInf(strMsgDisp);
                }
                
                
        

    } catch (Exception e) {
        System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
        exito = false;
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
        try { if (conn != null) conn.close(); } catch (Exception e) {}
    }

    return exito;
}    


    
    
    ///////////////////////////

    boolean blnIsSolApr=false;
    
    
    

   
    
  
    
    /**
     * Valida Stock Disponible 
     * 
     * Funcion para validar la cantidad disponible contra la solicitud de transferencia
     * @return 
     */
    private boolean validarStockDisponible(java.sql.Connection conExt, int i){
        boolean blnRes=true, blnIsStock=true, blnIsBodIngImp=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc, rstLoc01;
        String strMsg="", strItem="";
        try{
            blnIsImportacion=false;
            if(conExt!=null){
                stmLoc= conExt.createStatement();

              strSQL="";
                    strSQL+=" select iv.DEX_ROW_ID as co_item ,RTRIM(v.ITEMNMBR) as tx_codAlt,RTRIM(iv.ITMSHNAM) as tx_codAlt2,RTRIM(iv.ITEMDESC) as tx_nomItm, \n";
                    strSQL+="        RTRIM(iv.UOMSCHDL) as tx_desCor,inf.Peso_KG as nd_pesItmKgr, v.DISPONIBLE as nd_canDis,iv.CURRCOST,RTRIM(v.LOCNCODE) as bodega     \n";
                    strSQL+=" from "+ objParSis.getNombreBaseDatosGP()  +"..vw_consulta_disponible_transito as v \n";
                    strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..IV00101 as Iv on v.ITEMNMBR = iv.ITEMNMBR  \n";
                    //strSQL+="      left join "+ objParSis.getNombreBaseDatosGP()  +"..MSW_vi_datos_adicionales_articulos_1 as inf on  v.ITEMNMBR = inf.ITEMNMBR \n";
//// chiqui2                   strSQL+=" where v.DISPONIBLE > 0 and v.LOCNCODE ='"+ objTblMod.getValueAt(i,INT_TBL_DAT_BOD_OR).toString() +"' \n";
                    strSQL+=" ORDER BY  v.ITEMNMBR desc  \n";
                    
                System.out.println("validarStockDisponible: "+ strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                while(rstLoc.next()){
                    if(blnIsImportacion){
                        //Valida que la bodega origen de la solicitud corresponda a la bodega del ingreso por importacion.
                        if(!(rstLoc.getString("co_bod")).equals(rstLoc.getString("co_bodIngImp"))){
                            blnRes=false;
                            blnIsBodIngImp=false;
                        }
                    }
                    
                    if(blnIsBodIngImp)
                    {
                        //Valida disponible
                        if(Double.parseDouble(rstLoc.getString("nd_canDis"))<0.00){
                           blnRes=false;
                           blnIsStock=false;
                           strItem+="<tr><td>" +  rstLoc.getString("tx_codAlt") + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("disponible"), objParSis.getDecimalesMostrar())  + " </td>";
                           strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_can"), objParSis.getDecimalesMostrar()) + " </td> ";

                           if(blnIsImportacion){
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canIngImp"), objParSis.getDecimalesMostrar()) + " </td> ";
                                strItem+=" <td>" + objUti.redondear(rstLoc.getString("nd_canTran"), objParSis.getDecimalesMostrar()) + " </td> ";
                           }
                           strItem+=" </tr> ";
                        }
                    }
                }
                if(!blnIsBodIngImp) {
                    mostrarMsgInf("La solicitud que desea autorizar tiene la bodega origen distinta a la del ingreso por importación");
                }
                
                if(!blnIsStock){
                    strMsg="<html> La solicitud posee Items con cantidades insuficientes. <BR><BR>" ;// CAMBIA
                    strMsg+=" <table BORDER=1><tr><td> ITEM </td> <td> Disponible </td><td> Can.Sol. </td>";
                    if(blnIsImportacion){
                        strMsg+="<td> Ingreso Importación </td>";
                        strMsg+="<td> Transferencias </td>";
                    }
                    strMsg+=""+ strItem + "    ";
                    strMsg+=" </table><BR>";
                    strMsg+="No se puede realizar esta operación. <html>";
                    //System.out.println(strMsg);
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            else{
                blnRes=false;
            }
            
        }
        catch (java.sql.SQLException e) { 
            blnRes=false;  
            objUti.mostrarMsgErr_F1(this, e);  
        }
        catch(Exception  Evt){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }  
        return blnRes;
    } 
    
   
}
