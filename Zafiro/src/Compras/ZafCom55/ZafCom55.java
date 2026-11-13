/*
 * ZafCom55.java
 *
 * Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom55; 
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil; 
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement; 
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
//import Librerias.ZafInventario.ZafInvItm;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblOrd.ZafHeaRenLbl;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafToolBar.ZafToolBar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import Librerias.ZafReaVenComAut.ZafReaVenComAut;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafVenConGP.ZafVenConGP;
import Librerias.ZafWebSer.ZafWebSer;
import java.awt.Color;
import java.awt.Component;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.json.JSONArray;
import Librerias.ZafCorEleGP.ZafCorEleGP; 
import Librerias.ZafUtilGP.ZafUtilGP;
import java.math.RoundingMode;
import java.sql.SQLException;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafCom55 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_EMP=1;
    private final int INT_TBL_DAT_CHK=2;
    private final int INT_TBL_DAT_COD_ITM=3;
    private final int INT_TBL_DAT_COD_ITM_MAE=4;
    private final int INT_TBL_DAT_COD_ALT_ITM=5;
    private final int INT_TBL_DAT_COD_ALT_DOS=6;
    private final int INT_TBL_DAT_NOM_ITM=7;
    private final int INT_TBL_DAT_DES_COR_UNI_MED=8;  
    
    private final int INT_TBL_DAT_ITM_TIP=9;
    
    
    private final int INT_TBL_DAT_STK_ACT_BOD_CEN_DIS=10;
    private final int INT_TBL_DAT_DIS_IN4=11;
    private final int INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP=12;
    private final int INT_TBL_DAT_CAN_DIS=13;
    
    private final int INT_TBL_DAT_CAN_MIN_ORI=14;
    private final int INT_TBL_DAT_CAN_MAX_ORI=15;     
    
    private final int INT_TBL_DAT_STK_ACT=16;   
    
    private final int INT_TBL_DAT_CAN_MIN_DES=17;
    private final int INT_TBL_DAT_CAN_MAX_DES=18;
    
    private final int INT_TBL_DAT_CAN_REP_CAL_MIN=19;
    private final int INT_TBL_DAT_CAN_REP_CAL_PES_MIN=20;    
    private final int INT_TBL_DAT_CAN_REP_CAL_MED=21;
    private final int INT_TBL_DAT_CAN_REP_CAL_PES_MED=22;
    private final int INT_TBL_DAT_CAN_REP_CAL_MAX=23;
    private final int INT_TBL_DAT_CAN_REP_CAL_PES_MAX=24;

    
    
    private final int INT_TBL_DAT_CAN_REP_CAL=25; 
    private final int INT_TBL_DAT_CAN_REP_SEL=26;    
    private final int INT_TBL_DAT_CAN_REP_DIS=27;
    
    private final int INT_TBL_DAT_CAN_COB_PIS=28;
    private final int INT_TBL_DAT_CAN_COB_PRO=29;
    private final int INT_TBL_DAT_CAN_COB_SUC=30;
    
    private final int INT_TBL_DAT_PES=31;    
    private final int INT_TBL_DAT_PES_TOT_KGR=32;    
    private final int INT_TBL_DAT_STK_MIN=33;
    
    private final int INT_TBL_DAT_MIN=34;
    private final int INT_TBL_DAT_MAX=35;    
    private final int INT_TBL_DAT_UNI_MED=36;
    private final int INT_TBL_DAT_TRA=37;
    private final int INT_TBL_DAT_FEC_ULT_REP=38;        
    //private final int INT_TBL_DAT_ITM_TIP=36;
    private final int INT_TBL_DAT_UBI=39; 
    
    
    
    
    
    //PARA EL TAB DE TRANSFERENCIA
    private final int INT_TBL_DAT_TRS_LIN=0;
    private final int INT_TBL_DAT_TRS_CHK=1;
    private final int INT_TBL_DAT_TRS_COD_EMP=2;
    private final int INT_TBL_DAT_TRS_COD_LOC=3;
    private final int INT_TBL_DAT_TRS_COD_TIP_DOC=4;
    private final int INT_TBL_DAT_TRS_DES_COR_TIP_DOC=5;
    private final int INT_TBL_DAT_TRS_COD_DOC=6;
    private final int INT_TBL_DAT_TRS_NUM_DOC=7;
    private final int INT_TBL_DAT_TRS_FEC_DOC=8;
    private final int INT_TBL_DAT_TRS_BUT=9;
    private final int INT_TBL_DAT_TRS_COD_BOD_ORI=10;
    private final int INT_TBL_DAT_TRS_COD_ITM=11;
    private final int INT_TBL_DAT_TRS_COD_ITM_MAE=12;
    private final int INT_TBL_DAT_TRS_COD_ALT_ITM=13;
    private final int INT_TBL_DAT_TRS_NOM_ITM=14;
    private final int INT_TBL_DAT_TRS_UNI_MED=15;
    private final int INT_TBL_DAT_TRS_CAN_REP=16;
    private final int INT_TBL_DAT_TRS_STK_ACT=17;
    private final int INT_TBL_DAT_TRS_STK_MIN=18;
    private final int INT_TBL_DAT_TRS_STK_DIS=19;
    private final int INT_TBL_DAT_TRS_COS_UNI=20;
    private final int INT_TBL_DAT_TRS_PRE_UNI=21;
    private final int INT_TBL_DAT_TRS_COS_TOT=22;
    private final int INT_TBL_DAT_TRS_PES_GRP=23;
    private final int INT_TBL_DAT_TRS_COD_BOD_DES=24;
	//private final int INT_TBL_DAT_TRS_IN4=25;
       
    //ArrayList para consultar.
    private ArrayList arlDatConCnf, arlRegConCnf; 
    private static final int INT_ARL_CON_COD_DOC=0;  
    private int intIndiceConCnf=0;
    
    //ArrayList: Contenedor de solicitud de transferencias.
    private ArrayList arlDatConSolTraInv, arlRegConSolTraInv;
    final int INT_ARL_GEN_SOL_TRA_ITM_GRP=0;
    final int INT_ARL_GEN_SOL_TRA_CAN=1;
    final int INT_ARL_GEN_SOL_PES_UNI=2;
    final int INT_ARL_GEN_SOL_PES_TOT_ITM=3;
    
    //ArrayList: Para obtener solicitud de transferencia
    private ArrayList arlDatSol;
    
    // CODIGO DE LA CONFIGURACION PARA SOLICITUDES DE REPOSICIONES DE INVENTARIO
    final int INT_COD_CFG_TIP_AUT_SOL_REP_INV=2001; 
    
    // CODIGO DE LA CONFIGURACION PARA TRANSFERENCIAS DE REPOSICIONES DE INVENTARIO
    final int INT_COD_CFG_TIP_AUT_TRA_REP_INV=16; 
    
    //Código de Bodega de Centro de Distribución.
    private final int INT_COD_BOD_CEN_DIS=15;
    
    //CÓDIGO DE TIPO DOCUMENTO REPOSICION 
    final String STR_COD_TIP_DOC_REINBO="161"; 
    final String STR_COD_TIP_DOC_REINBD="223"; 
    
    //Variables generales.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum, objColNumTrs;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafTblFilCab objTblFilCab, objTblFilCabTrs;
    private ZafTblMod objTblModRep, objTblModRepTrs;
    private ZafTblEdi objTblEdi;                                       //Editor: Editor del JTable.
    private java.util.Date datFecAux;                                  //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCol;                           //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                                 //ToolTipText en TableHeader.
    private ZafMouMotAdaTrs objMouMotAdaTrs;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuTrs;                //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkTrs;       //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkTrs;       //Editor: JCheckBox en celda.
    private ZafVenCon vcoTipDoc;                 //Ventana de consulta "Bodega".
    private ZafThreadGUI objThrGUI;
    private ZafPerUsr objPerUsr;
    private ZafHeaRenLbl objHeaRenLbl;
    private ZafDocLis objDocLis;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtCanRep,objTblCelEdiTxt2;
    private ZafTblOrd objTblOrd;                                      //JTable de ordenamiento.
    private ZafTblCelRenBut    objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private Librerias.ZafUtil.UltDocPrint  objUltDocPrint2;
    private ZafReaVenComAut objReaVenComAut;
    private MiToolBar objTooBar;
    private ZafSegMovInv objSegMovInv;
    private ZafCfgSer objCfgSer;                                      //Obtiene Configuracion de Servicios.
    private GenOD.ZafGenOdPryTra objGenOD;    /*Jose Mario Marin - 22/Junio/2016*/
    
    private Vector vecDat,  vecCab,  vecReg;
    private Vector vecDatCapCar,  vecCabCapCar,  vecRegCapCar;
    private Vector vecDatTipEmi,  vecCabTipEmi,  vecRegTipEmi;
    private Vector vecDatTrs,  vecCabTrs,  vecRegTrs;
    private Vector vecAux;
    Vector vecDocSinPnd=new Vector();
    
    private boolean blnCon;                                           //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                                        //Determina si hay cambios en el formulario.
    private boolean blnChnEstImp;

    private BigDecimal bdePesTotTrs;
    private BigDecimal bdePorVarGrl; 

    private int intCodEmpGrp, intCodLocGrp;
    private int intOpcRedondeoStock, intOpcRedondeoMinimo;
    private int intNumRegSel;
    
    private String strSQL,  strAux;
    private String strDesCorTipDoc,  strDesLarTipDoc;                 //Contenido del campo al obtener el foco.
    private String strDesCorRes,  strDesLarRes;                       //Contenido del campo al obtener el foco.
    private String strCodBod,  strNomBod;                             //Contenido del campo al obtener el foco.
    private String strCodBodDes, strNomBodDes, strCodBodOri, strNomBodOri;
    private String strEstImpDoc;
    private String strIpImp;                                         //Ip donde se ejecuta el servidor de Impresion de OD y Guias.
    private String strPesTotKgrSolTra;                                //Obtiene el peso total de la solicitud de transferencia.
    
    private String strVersion="v0.45";    
    
    private int ne_tieespe=-1;
    private String fe_ultmod;
    private ZafWebSer objWeb;  
    private String strCadeItems ="";
    private int intNumDoc=-1;
    ArrayList<HashMap<String, Object>> listaReservas = new ArrayList<>();
    ArrayList<HashMap<String, Object>> listaUbicacion = new ArrayList<>();
    private boolean blnTotItm =false;
    String strMsgTotItm="";
    
    private String lbl_titulo="";
    List<Map<String, Object>> listaDatosOri = new ArrayList<>();
    List<Map<String, Object>> listaDatosFin = new ArrayList<>();
    List<Map<String, Object>> listaDiferencias = new ArrayList<>();
    private ZafCorEleGP objRepCorEleGP;
    private StringBuilder htmlGlo = new StringBuilder();    
    private double nd_pes =0;
    private int ne_canTotSel=0;
    
    private final int INT_TBL_DAT_CAP_CAR_LIN=0;
    private final int INT_TBL_DAT_CAP_CAR_CAT_NIV=1;
    private final int INT_TBL_DAT_CAP_CAR_NIV=2; 
    private ZafTblMod objTblModRepCapCar, objTblModRepTrsCapCar;
    private ZafTblMod objTblModRepTipEmi, objTblModRepTrsTipEmi;
    private boolean blnCarCmbAbcCat = false;
    private boolean blnCarCmbAbcCatTipEmi = false;
    
    private final int INT_TBL_DAT_TIP_EMI_LIN=0;
    private final int INT_TBL_DAT_TIP_EMI_CAT_NIV=1;
    private final int INT_TBL_DAT_TIP_EMI_NIV=2;     
    private ZafUtilGP objUtiGP; 
    
    

    /** Crea una nueva instancia de la clase ZafCom55. */
    public ZafCom55(ZafParSis obj){
        try{
            objParSis = (ZafParSis) obj.clone();
            intCodEmpGrp = objParSis.getCodigoEmpresa();
            intCodLocGrp = objParSis.getCodigoLocal();
            objUtiGP=new ZafUtilGP(objParSis);
            intOpcRedondeoStock = 0;
            intOpcRedondeoMinimo = 0;       
            System.out.println("entro ZafCom55  ");
             
            if(intCodEmpGrp==objParSis.getCodigoEmpresaGrupo()) 
            {
                int ii =0;
                ii = objParSis.getCodigoMenu();
                 System.out.println("entro initComponents  ");
                initComponents();
                System.out.println("entro configurarFrm 0  "); 
                configurarFrm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            System.out.println("eque paso ");
            
            
            blnChnEstImp=false;
        } 
        catch (CloneNotSupportedException e) 
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** Crea una nueva instancia de la clase ZafCom55. llamada desde el ZafCom57(Programa de seguimiento)*/
    public ZafCom55(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            intCodEmpGrp=codigoEmpresa; 
            intCodLocGrp=codigoLocal;
            System.out.println("entro ZafCom55  1");
            objUtiGP=new ZafUtilGP(objParSis);
            if(intCodEmpGrp==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();
                txtCodTipDoc.setText("" + codigoTipoDocumento);
                txtCodDoc.setText("" + codigoDocumento);
                configurarFrm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }

            consultarReg();
            objTooBar.afterConsultar();
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false);

            blnChnEstImp=false;

        } 
        catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
        
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes = true;
        try 
        {
            System.out.println("entro configurarFrm  2");
            objPerUsr=new ZafPerUsr(objParSis);
            objUti = new ZafUtil();
            objWeb = new ZafWebSer(objParSis);
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            objRepCorEleGP = new ZafCorEleGP(objParSis);  /*Libreria para enviar correos electronicos a usuario.*/	
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(560, 6, 120, 20);
            
            tabFrm.remove(panTraInv);

            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            // José Marín 28/Jul/2014 ....  9.04
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodOri.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtPesTot.setBackground(objParSis.getColorCamposSistema());
            txtPesDis.setBackground(objParSis.getColorCamposObligatorios());

            txtCodTipDoc.setVisible(false);
            
            
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            System.out.println("entro configurarVenConTipDoc  ");
            //configurarVenConBodDes();
            //configurarVenConBodOri();

            objTooBar=new MiToolBar(this);
            panBarBot.add(objTooBar);
            objTooBar.agregarSeparador();
            //objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleAnular(false);
           
            objTooBar.agregarBoton(butCarItm);
            
            objSegMovInv=new ZafSegMovInv(objParSis, this);

            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            txtCodTipDoc.setVisible(true);
            
            butLlamada.setVisible(false);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if(objParSis.getCodigoUsuario()==1)
            {
                //optPorVar.setVisible(false);
                optPorVar.setEnabled(false);
                
                //optItmApl.setVisible(false);
                optItmApl.setEnabled(false);
                
                optItmSelUsr.setVisible(true);
                optItmSelUsr.setEnabled(true);
                txtCodTipDoc.setVisible(false);
                butLlamada.setVisible(true);

            }
            else
            {
                if(objParSis.getCodigoMenu()==2336) //programa de reposicion de inventario desde otras bodegas
                {
                    //Ficha "General": Opción "Por porcentaje de variación"
                    if (!objPerUsr.isOpcionEnabled(3961)) 
                    {
                        //optPorVar.setVisible(false);
                        optPorVar.setEnabled(false);
                    }
                    //Ficha "General": Opción "Los primeros 13 items generados por el Sistema"
                    if (!objPerUsr.isOpcionEnabled(3962)) 
                    {
                        //optItmApl.setVisible(false);
                        optItmApl.setEnabled(false);
                    }
                    //Ficha "General": Opción "Items seleccionados por usuario (13)"
                    if(!objPerUsr.isOpcionEnabled(3958)) 
                    {
                        //optItmSelUsr.setVisible(false);
                        optItmSelUsr.setEnabled(false);
                    }
                }       
            } 
            
            optItmSelUsr.setVisible(false);
            optItmApl.setVisible(false);
            optPorVar.setVisible(false);
            
            
            
            //Configurar los JTables.
            System.out.println("entro configurarTblDat  ");
            configurarTblDat();
            System.out.println("entro configurarTblDatTrs  ");
            configurarTblDatTrs();
            System.out.println("entro cargarComboABC  ");
            cargarComboABC();
            System.out.println("entro configurarTblDatABCTipEmi  ");
            configurarTblDatABCTipEmi();
            cargarComboCatTipEmiABC();  
            
            if (chk_conCapCar.isSelected())
            {
                configurarTblDatABCCapCarga();
                cargarComboCatABC();
            }
            else
            {
                cmb_tipEmi.setSelectedItem("Minimo");
                cmb_abcCatTipEmi.setSelectedItem("Tuval");
            }
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private String obtenerUbicacion(java.sql.Connection conExt,String item,String bodega)
    {
        String strUbic="";
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        int contador =0;
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
    
private void configurarOrdenamientoPersonalizado() {
    try {
        javax.swing.table.TableRowSorter<javax.swing.table.TableModel> sorter;
        sorter = new javax.swing.table.TableRowSorter<javax.swing.table.TableModel>(objTblModRep);

        sorter.setComparator(INT_TBL_DAT_CHK, new java.util.Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                Boolean b1 = convertirBoolean(o1);
                Boolean b2 = convertirBoolean(o2);

                // Checked primero.
                return b2.compareTo(b1);
            }
        });

        sorter.setComparator(INT_TBL_DAT_ITM_TIP, new java.util.Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return Integer.compare(obtenerOrdenABC(o1), obtenerOrdenABC(o2));
            }
        });

        tblDat.setRowSorter(sorter);

        tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int intColVis = tblDat.columnAtPoint(evt.getPoint());
                int intColMod = tblDat.convertColumnIndexToModel(intColVis);

                if (intColMod == INT_TBL_DAT_CHK) {
                    java.util.List<javax.swing.RowSorter.SortKey> lisOrd;
                    lisOrd = new java.util.ArrayList<javax.swing.RowSorter.SortKey>();

                    // 1. Primero los seleccionados.
                    lisOrd.add(new javax.swing.RowSorter.SortKey(INT_TBL_DAT_CHK, javax.swing.SortOrder.DESCENDING));

                    // 2. Luego orden personalizado A, B, C, N, D, R.
                    lisOrd.add(new javax.swing.RowSorter.SortKey(INT_TBL_DAT_ITM_TIP, javax.swing.SortOrder.ASCENDING));

                    // 3. Finalmente por código de ítem.
                    lisOrd.add(new javax.swing.RowSorter.SortKey(INT_TBL_DAT_COD_ITM, javax.swing.SortOrder.ASCENDING));

                    sorter.setSortKeys(lisOrd);
                    sorter.sort();
                }
            }
        });

    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}    

private Boolean convertirBoolean(Object obj) {
    if (obj == null) {
        return false;
    }

    if (obj instanceof Boolean) {
        return (Boolean) obj;
    }

    String strVal = obj.toString().trim();

    return strVal.equalsIgnoreCase("true")
        || strVal.equalsIgnoreCase("S")
        || strVal.equalsIgnoreCase("1")
        || strVal.equalsIgnoreCase("SI");
}
    
private int obtenerOrdenABC(Object obj) {
    if (obj == null) {
        return 999;
    }

    String strVal = obj.toString().trim().toUpperCase();

    if (strVal.equals("A")) {
        return 1;
    }
    if (strVal.equals("B")) {
        return 2;
    }
    if (strVal.equals("C")) {
        return 3;
    }
    if (strVal.equals("N")) {
        return 4;
    }
    if (strVal.equals("D")) {
        return 5;
    }
    if (strVal.equals("R")) {
        return 6;
    }

    return 999;
}

private int getNumeroRegistrosChequeados() {
    int intCanRegChk = 0;

    try {
        for (int i = 0; i < objTblModRep.getRowCountTrue(); i++) {
            if (objTblModRep.isChecked(i, INT_TBL_DAT_CHK)) {
                intCanRegChk++;
            }
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return intCanRegChk;
}


private boolean configurarTblDat()  
    {
        boolean blnRes = true;
        try
        {
            //System.out.println("configurarTblDat ");
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(23);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp.");
            vecCab.add(INT_TBL_DAT_CHK, " ");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cod.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS, "Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Unidad");
            
            vecCab.add(INT_TBL_DAT_ITM_TIP, "ABC");            
            vecCab.add(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, "Stk.Cen.Dis.");
            vecCab.add(INT_TBL_DAT_DIS_IN4, "Disp.Inma.4");
            vecCab.add(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, "Per.Cen.Dis.");
            vecCab.add(INT_TBL_DAT_CAN_DIS,"Can.Dis.Bod.Ori.");
            
            vecCab.add(INT_TBL_DAT_CAN_MIN_ORI, "Min.Ori.");
            vecCab.add(INT_TBL_DAT_CAN_MAX_ORI, "Max.Ori.");            
            
            vecCab.add(INT_TBL_DAT_STK_ACT, "Disponible");
            
                        vecCab.add(INT_TBL_DAT_CAN_MIN_DES, "Min.Des.");
            vecCab.add(INT_TBL_DAT_CAN_MAX_DES, "Max.Des.");
            
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MIN, "Rep.Min");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MIN, "Rep.Min.Pes");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MED, "Rep.Med");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MED, "Rep.Med.Pes");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MAX, "Rep.Max");
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MAX, "Rep.Max.Pes");
          
            
            vecCab.add(INT_TBL_DAT_CAN_REP_CAL, "Can.Rep.Cal.");//cantidad a reponer calculada por el sistema segun parametros            
            vecCab.add(INT_TBL_DAT_CAN_REP_SEL, "Can.Rep.Sel.");//cantidad a reponer seleccionada
            vecCab.add(INT_TBL_DAT_CAN_REP_DIS, "Can.Rep.Dis.");//cantidad disponible a reponer(segun stock disponible en las empresas)

            vecCab.add(INT_TBL_DAT_CAN_COB_PIS, "Cob.Pis.");//Cobertura Piso          
            vecCab.add(INT_TBL_DAT_CAN_COB_PRO, "Cob.Reposicion");//Cobertura Promedio
            vecCab.add(INT_TBL_DAT_CAN_COB_SUC, "Cob.Suc.");//Cobertura Sucursal
          
            vecCab.add(INT_TBL_DAT_PES, "Peso(kg).");
            vecCab.add(INT_TBL_DAT_PES_TOT_KGR,"Pes.Tot(kg).");
            vecCab.add(INT_TBL_DAT_STK_MIN, "Stock Minimo");

            vecCab.add(INT_TBL_DAT_MIN, "Min.Dest.");
            vecCab.add(INT_TBL_DAT_MAX, "Max.Dest.");
            vecCab.add(INT_TBL_DAT_UNI_MED, "UNI.");
            vecCab.add(INT_TBL_DAT_TRA, "Tránsito");
            vecCab.add(INT_TBL_DAT_FEC_ULT_REP, "Fec.Ult.Rep.");
            
            
            
            // 🔹 Nuevos campos añadidos





            vecCab.add(INT_TBL_DAT_UBI, "Ubicación"); 
            
    
          
            objTblModRep = new ZafTblMod();

            objTblModRep.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
            
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_PIS, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_PRO, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_SUC, objTblModRep.INT_COL_INT, new Integer(0), null);
            
            objTblModRep.setColumnDataType(INT_TBL_DAT_PES, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_PES_TOT_KGR, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_MAX, objTblModRep.INT_COL_INT, new Integer(0), null);
            
            // Nuevos campos de origen
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MIN_ORI, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MAX_ORI, objTblModRep.INT_COL_DBL, new Integer(0), null);
            

            // Nuevos campos de reposición calculada
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MIN, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MIN, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MED, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MED, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MAX, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MAX, objTblModRep.INT_COL_DBL, new Integer(0), null);

            // Nuevos campos de destino
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MIN_DES, objTblModRep.INT_COL_DBL, new Integer(0), null);
            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MAX_DES, objTblModRep.INT_COL_DBL, new Integer(0), null);

            // Nuevo campo de tipo de ítem
            objTblModRep.setColumnDataType(INT_TBL_DAT_ITM_TIP, objTblModRep.INT_COL_DBL, new Integer(0), null);


            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblModRep);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(300);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            // Nuevo campo de tipo de ítem
            tcmAux.getColumn(INT_TBL_DAT_ITM_TIP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_SUC).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_PES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_MAX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_REP).setPreferredWidth(100);
            
            
            
            // Nuevos campos de origen
            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_ORI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_ORI).setPreferredWidth(70);

            // Nuevos campos de reposición calculada
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MIN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MIN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MED).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MED).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MAX).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MAX).setPreferredWidth(90);

            // Nuevos campos de destino
            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_DES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_DES).setPreferredWidth(70);


            tcmAux.getColumn(INT_TBL_DAT_UBI).setPreferredWidth(80); 
            
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setResizable(false);
//////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setResizable(false);
            //tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_DIS, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_MIN, tblDat);
//////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT, tblDat);  // JM Comentado el 29/Jun/2023
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_UBI, tblDat);  // JM Agregado el 29/Jun/2023
////////////////////
////////////////////        
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_DIS, tblDat);
////////////////////            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MIN, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MAX, tblDat);
////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_UNI_MED, tblDat);
////////////////////            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_DIS, tblDat);  
////////////////////            
////////////////////            if(! objTooBar.isOpcionEnabled(2337)){
////////////////////                objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_CAL, tblDat);
////////////////////            }

  
                                



            ocultarColumnasReptblDat(INT_TBL_DAT_CAN_REP_CAL_PES_MIN,INT_TBL_DAT_CAN_REP_CAL_PES_MED,INT_TBL_DAT_CAN_REP_CAL_PES_MAX,
                                     INT_TBL_DAT_MIN,INT_TBL_DAT_MAX,INT_TBL_DAT_UBI,
                                     INT_TBL_DAT_UNI_MED,INT_TBL_DAT_MIN,INT_TBL_DAT_MAX,
                                     INT_TBL_DAT_STK_MIN,
                                     INT_TBL_DAT_CAN_MIN_ORI,INT_TBL_DAT_CAN_MAX_ORI,INT_TBL_DAT_CAN_REP_DIS,INT_TBL_DAT_CAN_DIS,INT_TBL_DAT_TRA,INT_TBL_DAT_COD_ITM,INT_TBL_DAT_COD_ITM_MAE);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);
////            objTblOrd = new ZafTblOrd(tblDat);
            configurarOrdenamientoPersonalizado();

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
          
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                BigDecimal bdeCanReaRep=new BigDecimal(0);
                BigDecimal bdeCanStkDis=new BigDecimal(0);
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
                        intNumRegSel++;
                        bdeCanReaRep=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString());
                        bdeCanStkDis=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                        if( (intNumRegSel<=13))
                        {
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            //Validación por tipo de documento de retorno que no verifique stock de la bodega de distribución.
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)) //REINBO - Reposición de inventario desde otras bodegas
                            {
                                if(bdeCanStkDis.compareTo(bdeCanReaRep)>=0)
                                {
                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                                else
                                {
                                    objTblModRep.setValueAt(bdeCanStkDis, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)) //REINBD - Retorno de inventario a bodega de Distribución
                            {
                                if(bdeCanReaRep.compareTo(new BigDecimal("0"))>0)
                                {
                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                }
                            }
                            ////////////////////////////////////////////////////////////////////////////////////////////////////
                            
                            if(bdeCanReaRep.compareTo(new BigDecimal(BigInteger.ONE))<=0)
                            {
                                String strMsg="";
                                strMsg+="<HTML>";
                                strMsg+="El registro seleccionado tiene un valor menor a la unidad. <BR>";
                                strMsg+="¿Está seguro que desea seleccionar la cantidad a reponer?";
                                strMsg+="</HTML>";
                                switch (mostrarMsgCon(strMsg))
                                {
                                    case 0: //YES_OPTION

                                        break;
                                    case 1: //NO_OPTION
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                        intNumRegSel--;
                                        break;
                                    case 2: //CANCEL_OPTION
                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                        intNumRegSel--;
                                }
                            }
                            if (!getSumaTotalPeso(tblDat.getSelectedRow())) 
                            {
                                mostrarMsgInf("<HTML>El registro seleccionado tiene un peso mayor al disponible.</HTML>");
                                objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                                objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                                intNumRegSel--;
                            }
                        }
                        
                        else{
                            mostrarMsgInf("<HTML>Se han seleccionado mas de 13 registros.</HTML>");
                            objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                            objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
                            intNumRegSel--;
                        }

                    }
                    else{
                        if(getRestaTotalPeso(tblDat.getSelectedRow())){
                        }
                        intNumRegSel--;
                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
                    }

                    double dblCan; // José Marín M.
                    if(objTblCelEdiChk.isChecked())
                    {
                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
                    }
                    else{
                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
                    }
                    objTooBar.setMenSis("El número de registros seleccionados es: " + intNumRegSel);
                }
            });

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat); 
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellEditor(objTblCelEdiTxt);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_ORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_ORI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_DES).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PRO).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_SUC).setCellRenderer(objTblCelRenLbl);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MIN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MAX).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setCellRenderer(objTblCelRenLbl); 
    
            objTblCelRenLbl=null;

            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
objTblCelEdiTxtCanRep = new ZafTblCelEdiTxt(tblDat);

tcmAux.getColumn(
    INT_TBL_DAT_CAN_REP_SEL
).setCellEditor(objTblCelEdiTxtCanRep);


objTblCelEdiTxtCanRep.addTableEditorListener(
    new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
    {
        BigDecimal bdeValCanRepBefEdi = new BigDecimal("0");
        BigDecimal bdeValCanRepAftEdi = new BigDecimal("0");
        BigDecimal bdeValCanRepMaxDes = new BigDecimal("0");
        BigDecimal bdeValResCobRep = new BigDecimal("0");
        BigDecimal bdeValResCobSuc = new BigDecimal("0");

        BigDecimal bdeStkDis = new BigDecimal("0");

        BigDecimal bdeValCanRepCal = new BigDecimal("0");
        BigDecimal bdeValKg = new BigDecimal("0");
        BigDecimal bdeValKgTot = new BigDecimal("0");

        Double disponible;
        Double dblDisponible_tbm_invBod;

        Double disponibleMax;
        Double disponibleMin;

        String strUniMed = "";

        int fila = -1;
        int columna = -1;


        /*
         * 21092026
         *
         * Esta bandera evita que al modificar la celda
         * programáticamente con setValueAt(), se vuelva a ejecutar
         * la validación del editor.
         */
        boolean blnAjustandoCantidad = false;


        public void beforeEdit(
            Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt
        )
        {
            /*
             * Si estamos cambiando el valor desde código,
             * no volvemos a procesar el evento.
             */
            if (blnAjustandoCantidad)
            {
                return;
            }


            fila = getFilaModeloSeleccionada();

            int columnaVista = tblDat.getSelectedColumn();

            columna = columnaVista == -1
                    ? -1
                    : tblDat.convertColumnIndexToModel(columnaVista);


            if (fila == -1)
            {
                return;
            }


            /*==========================================================
              GUARDAR CANTIDAD ANTES DE EDITAR
            ==========================================================*/
            bdeValCanRepBefEdi = new BigDecimal(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ) == null
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ).toString().trim().equals("")
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ).toString()
            );


            /*==========================================================
              STOCK DISPONIBLE
            ==========================================================*/
            bdeStkDis = new BigDecimal(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
                ) == null
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
                ).toString().trim().equals("")
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
                ).toString()
            );


            /*==========================================================
              UNIDAD DE MEDIDA
            ==========================================================*/
            strUniMed = objTblModRep.getValueAt(
                fila,
                INT_TBL_DAT_DES_COR_UNI_MED
            ) == null
            ? ""
            : objTblModRep.getValueAt(
                fila,
                INT_TBL_DAT_DES_COR_UNI_MED
            ).toString();


            /*==========================================================
              DISPONIBLES CALCULADOS
            ==========================================================*/
            disponible = objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_CAL
                )
            );


            disponibleMax = objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_CAL_MAX
                )
            );


            disponibleMin = objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_CAL_MIN
                )
            );


            /*==========================================================
              DISPONIBLE EN tbm_invBod
            ==========================================================*/
            dblDisponible_tbm_invBod = objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_DIS
                )
            ) < 0
            ? 0
            : objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_DIS
                )
            );


            /*==========================================================
              CHECK
            ==========================================================*/
            if (
                objUti.parseDouble(
                    objTblModRep.getValueAt(
                        fila,
                        INT_TBL_DAT_CAN_REP_SEL
                    )
                ) <= 0
            )
            {
                objTblModRep.setValueAt(
                    false,
                    fila,
                    INT_TBL_DAT_CHK
                );
            }


            quitarItmCanRepCer();
        }


        public void afterEdit(
            Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt
        )
        {
            /*
             * Evitar que un setValueAt() realizado desde este mismo
             * método vuelva a ejecutar toda la validación.
             */
            if (blnAjustandoCantidad)
            {
                return;
            }


            if (fila == -1)
            {
                return;
            }


            /*==========================================================
              OBTENER NUEVA CANTIDAD INGRESADA
            ==========================================================*/
            bdeValCanRepAftEdi = new BigDecimal(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ) == null
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ).toString().trim().equals("")
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                ).toString()
            );


            /*==========================================================
              CANTIDAD MÁXIMA DE DESPACHO
            ==========================================================*/
            bdeValCanRepMaxDes = new BigDecimal(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_MAX_DES
                ) == null
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_MAX_DES
                ).toString().trim().equals("")
                ? "0"
                : objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_MAX_DES
                ).toString()
            );


            /*==========================================================
              OBTENER ITEM
            ==========================================================*/
            Object objItem = objTblModRep.getValueAt(
                fila,
                INT_TBL_DAT_COD_ALT_ITM
            );


            String strItem = "";

            if (objItem != null)
            {
                strItem = objItem.toString().trim();
            }


            /*==========================================================
              VALIDAR DECIMALES DEL ITEM
            ==========================================================*/
            double dblCan = objUti.parseDouble(
                objTblModRep.getValueAt(
                    fila,
                    INT_TBL_DAT_CAN_REP_SEL
                )
            );


            int intDecSQL = 0;
            String strTipoNumero = "";


            if (!strItem.equals(""))
            {
                intDecSQL = objUtiGP.obtenerDecimales(
                    strItem
                );
            }


            strTipoNumero = objUtiGP.obtenerTipoNumero(
                dblCan
            );


            /*
             * Si GP indica que el item NO permite cantidades
             * decimales y el usuario ingresó una cantidad decimal:
             *
             * 1. Regresamos al valor anterior.
             * 2. Mostramos el mensaje una sola vez.
             * 3. Recalculamos los datos usando el valor original.
             * 4. Salimos del método.
             */
            if (
                intDecSQL == 1
                &&
                strTipoNumero.equals("DECIMAL")
            )
            {
                try
                {
                    blnAjustandoCantidad = true;


                    /*==============================================
                      REGRESAR AL VALOR ANTERIOR
                    ==============================================*/
                    objTblModRep.setValueAt(
                        bdeValCanRepBefEdi,
                        fila,
                        INT_TBL_DAT_CAN_REP_SEL
                    );


                    /*==============================================
                      RESTAURAR CHECK SEGÚN CANTIDAD ORIGINAL
                    ==============================================*/
                    if (
                        bdeValCanRepBefEdi.compareTo(
                            BigDecimal.ZERO
                        ) > 0
                    )
                    {
                        objTblModRep.setValueAt(
                            true,
                            fila,
                            INT_TBL_DAT_CHK
                        );
                    }
                    else
                    {
                        objTblModRep.setValueAt(
                            false,
                            fila,
                            INT_TBL_DAT_CHK
                        );
                    }
                }
                finally
                {
                    blnAjustandoCantidad = false;
                }


                /*==============================================
                  MOSTRAR MENSAJE UNA SOLA VEZ
                ==============================================*/
                mostrarMsgInf(
                    "El item "
                    + strItem
                    + " no permite cantidades decimales."
                );

 
                /*==============================================
                  RECALCULAR CON EL VALOR ORIGINAL
                ==============================================*/
                calcularCobertura(fila);

                ne_canTotSel = getNumeroRegistrosChequeados();

                txtCanTot.setText(
                    String.valueOf(ne_canTotSel)
                );

                calcularPesTotRep();


                /*
                 * No continuar porque la cantidad digitada
                 * fue rechazada.
                 */
                return;
            }


            /*==========================================================
              VALIDAR CANTIDAD
            ==========================================================*/
            validaCantidad(
                fila,
                bdeStkDis,
                bdeValCanRepBefEdi,
                bdeValCanRepAftEdi
            );


            /*==========================================================
              CALCULAR COBERTURA
            ==========================================================*/
            calcularCobertura(fila);


            /*==========================================================
              MARCAR / DESMARCAR CHECK
            ==========================================================*/
            if (
                objUti.parseDouble(
                    objTblModRep.getValueAt(
                        fila,
                        INT_TBL_DAT_CAN_REP_SEL
                    )
                ) > 0
            )
            {
                objTblModRep.setValueAt(
                    true,
                    fila,
                    INT_TBL_DAT_CHK
                );
            }
            else
            {
                objTblModRep.setValueAt(
                    false,
                    fila,
                    INT_TBL_DAT_CHK
                );
            }


            /*==========================================================
              TOTAL DE REGISTROS SELECCIONADOS
            ==========================================================*/
            ne_canTotSel = getNumeroRegistrosChequeados();

            txtCanTot.setText(
                String.valueOf(ne_canTotSel)
            );


            /*==========================================================
              CALCULAR PESO TOTAL
            ==========================================================*/
            calcularPesTotRep();
        }
    }
);
            
            objTblCelRenLblCol = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_MIN).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_MAX).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_TRA).setCellRenderer(objTblCelRenLblCol);
            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCol.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblCol.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt){
                    if(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS)!=null){
                        if(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS).toString().length()>0){
                            if (Double.parseDouble(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS).toString() ) < 
                                   Double.parseDouble(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_REP_CAL).toString() ) ){
                                objTblCelRenLblCol.setBackground(colFonCol);
                            }
                            else{
                                objTblCelRenLblCol.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }
                        }
                    }
                }
            });
//            objTblCelRenLblCol = null;

// ============================================================
// COLUMNA DISPONIBLE
// Fondo gris azulado #E7EAF0.
// Texto rojo cuando Disponible <= Min.Des.
// ============================================================
ZafTblCelRenLbl objTblCelRenLblDisponible = new ZafTblCelRenLbl() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component comp = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        // ====================================================
        // MOSTRAR DISPONIBLE CON UN SOLO DECIMAL
        // ====================================================
        if (value != null && !value.toString().trim().equals(""))
        {
            try
            {
                double dblValor = Double.parseDouble(value.toString());
                setText(String.format(java.util.Locale.US, "%.1f", dblValor));
            }
            catch (Exception e)
            {
                setText(value.toString());
            }
        }        

        // Mantener alineación numérica a la derecha.
        if (comp instanceof javax.swing.JLabel) {
            ((javax.swing.JLabel) comp).setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        }

        // Fondo solicitado para toda la columna Disponible: #E7EAF0.
        if (!isSelected) {
            comp.setBackground(new java.awt.Color(231, 234, 240));
            comp.setForeground(table.getForeground());
        } else {
            comp.setBackground(table.getSelectionBackground());
            comp.setForeground(table.getSelectionForeground());
        }

        try {
            // La JTable puede estar ordenada, por eso convertimos la fila
            // visual a la fila real del modelo.
            int filaModelo = table.convertRowIndexToModel(row);

            Object objDisponible = objTblModRep.getValueAt(
                    filaModelo, INT_TBL_DAT_STK_ACT);

            Object objMinDes = objTblModRep.getValueAt(
                    filaModelo, INT_TBL_DAT_CAN_MIN_DES);

            if (objDisponible != null
                    && objMinDes != null
                    && !objDisponible.toString().trim().equals("")
                    && !objMinDes.toString().trim().equals("")) {

                double dblDisponible = objUti.parseDouble(objDisponible);
                double dblMinDes = objUti.parseDouble(objMinDes);

                // Regla solicitada: Disponible menor o igual a Min.Des.
                if (dblDisponible <= dblMinDes) {
                    comp.setForeground(java.awt.Color.RED);
                }
            }
        } catch (Exception e) {
            // Si algún valor no puede convertirse, se conserva el formato normal.
        }

        return comp;
    }
};

// IMPORTANTE: esta asignación va después de objTblCelRenLblCol para que
// este renderer sea el definitivo de la columna Disponible.
tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLblDisponible);


// =====================================================
// COLUMNA AMARILLA
// =====================================================
ZafTblCelRenLbl objTblCelRenLblAmarillo = new ZafTblCelRenLbl() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component comp = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            comp.setBackground(new java.awt.Color(255, 255, 153)); // Amarillo claro
        } else {
            comp.setBackground(table.getSelectionBackground());
        }

        return comp;
    }
};

tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setCellRenderer(objTblCelRenLblAmarillo);


// =====================================================
// COLUMNA AZUL CLARO
// =====================================================
ZafTblCelRenLbl objTblCelRenLblAzul = new ZafTblCelRenLbl() {
    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component comp = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        if (!isSelected) {
            comp.setBackground(new java.awt.Color(204, 229, 255)); // Azul claro
        } else {
            comp.setBackground(table.getSelectionBackground());
        }

        return comp;
    }
};

tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLblAzul);


            
            
            
            objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI); //tabla editable 


            tcmAux=null;
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
//////////    private boolean configurarTblDat()  
//////////    {
//////////        boolean blnRes = true;
//////////        try
//////////        {
//////////            //System.out.println("configurarTblDat ");
//////////            //Configurar JTable: Establecer el modelo.
//////////            vecDat = new Vector();    //Almacena los datos
//////////            vecCab = new Vector(23);  //Almacena las cabeceras
//////////            vecCab.clear();
//////////            vecCab.add(INT_TBL_DAT_LIN, "");
//////////            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp.");
//////////            vecCab.add(INT_TBL_DAT_CHK, " ");
//////////            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm.");
//////////            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cod.Itm.Mae.");
//////////            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.");
//////////            vecCab.add(INT_TBL_DAT_COD_ALT_DOS, "Cod.Alt.2");
//////////            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
//////////            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Unidad");
//////////            
//////////            vecCab.add(INT_TBL_DAT_ITM_TIP, "ABC");            
//////////            vecCab.add(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, "Stk.Cen.Dis.");
//////////            vecCab.add(INT_TBL_DAT_DIS_IN4, "Disp.Inma.4");
//////////            vecCab.add(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, "Per.Cen.Dis.");
//////////            vecCab.add(INT_TBL_DAT_CAN_DIS,"Can.Dis.Bod.Ori.");
//////////            
//////////            vecCab.add(INT_TBL_DAT_CAN_MIN_ORI, "Min.Ori.");
//////////            vecCab.add(INT_TBL_DAT_CAN_MAX_ORI, "Max.Ori.");            
//////////            
//////////            vecCab.add(INT_TBL_DAT_STK_ACT, "Disponible");
//////////            
//////////                        vecCab.add(INT_TBL_DAT_CAN_MIN_DES, "Min.Des.");
//////////            vecCab.add(INT_TBL_DAT_CAN_MAX_DES, "Max.Des.");
//////////            
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MIN, "Rep.Min");
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MIN, "Rep.Min.Pes");
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MED, "Rep.Med");
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MED, "Rep.Med.Pes");
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_MAX, "Rep.Max");
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL_PES_MAX, "Rep.Max.Pes");
//////////          
//////////            
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_CAL, "Can.Rep.Cal.");//cantidad a reponer calculada por el sistema segun parametros            
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_SEL, "Can.Rep.Sel.");//cantidad a reponer seleccionada
//////////            vecCab.add(INT_TBL_DAT_CAN_REP_DIS, "Can.Rep.Dis.");//cantidad disponible a reponer(segun stock disponible en las empresas)
//////////
//////////            vecCab.add(INT_TBL_DAT_CAN_COB_PIS, "Cob.Pis.");//Cobertura Piso          
//////////            vecCab.add(INT_TBL_DAT_CAN_COB_PRO, "Cob.Reposicion");//Cobertura Promedio
//////////            vecCab.add(INT_TBL_DAT_CAN_COB_SUC, "Cob.Suc.");//Cobertura Sucursal
//////////          
//////////            vecCab.add(INT_TBL_DAT_PES, "Peso(kg).");
//////////            vecCab.add(INT_TBL_DAT_PES_TOT_KGR,"Pes.Tot(kg).");
//////////            vecCab.add(INT_TBL_DAT_STK_MIN, "Stock Minimo");
//////////
//////////            vecCab.add(INT_TBL_DAT_MIN, "Min.Dest.");
//////////            vecCab.add(INT_TBL_DAT_MAX, "Max.Dest.");
//////////            vecCab.add(INT_TBL_DAT_UNI_MED, "UNI.");
//////////            vecCab.add(INT_TBL_DAT_TRA, "Tránsito");
//////////            vecCab.add(INT_TBL_DAT_FEC_ULT_REP, "Fec.Ult.Rep.");
//////////            
//////////            
//////////            
//////////            // 🔹 Nuevos campos añadidos
//////////
//////////
//////////
//////////
//////////
//////////            vecCab.add(INT_TBL_DAT_UBI, "Ubicación"); 
//////////            
//////////    
//////////          
//////////            objTblModRep = new ZafTblMod();
//////////
//////////            objTblModRep.setHeader(vecCab);
//////////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_PIS, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_PRO, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_COB_SUC, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_PES, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_PES_TOT_KGR, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_MIN, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_MAX, objTblModRep.INT_COL_INT, new Integer(0), null);
//////////            
//////////            // Nuevos campos de origen
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MIN_ORI, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MAX_ORI, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            
//////////
//////////            // Nuevos campos de reposición calculada
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MIN, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MIN, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MED, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MED, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_MAX, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_CAL_PES_MAX, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////
//////////            // Nuevos campos de destino
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MIN_DES, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_MAX_DES, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////
//////////            // Nuevo campo de tipo de ítem
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_ITM_TIP, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////
//////////
//////////            
//////////            //Configurar JTable: Establecer el modelo de la tabla.
//////////            tblDat.setModel(objTblModRep);
//////////            //Configurar JTable: Establecer tipo de seleccián.
//////////            tblDat.setRowSelectionAllowed(true);
//////////            tblDat.getTableHeader().setReorderingAllowed(false);
//////////            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//////////            //Configurar JTable: Establecer la fila de cabecera.
//////////            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
//////////
//////////            //Configurar JTable: Establecer el menú de contexto.
//////////            objTblPopMnu = new ZafTblPopMnu(tblDat);
//////////            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//////////            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//////////            //Configurar JTable: Establecer el ancho de las columnas.
//////////            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
//////////
//////////            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(20);
//////////            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(100);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(100);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(100);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(60); 
//////////            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(300);
//////////            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
//////////            // Nuevo campo de tipo de ítem
//////////            tcmAux.getColumn(INT_TBL_DAT_ITM_TIP).setPreferredWidth(70);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setPreferredWidth(70);
//////////            tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setPreferredWidth(70);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PIS).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PRO).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_SUC).setPreferredWidth(60);            
//////////            tcmAux.getColumn(INT_TBL_DAT_PES).setPreferredWidth(80);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);            
//////////            tcmAux.getColumn(INT_TBL_DAT_MIN).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_MAX).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_TRA).setPreferredWidth(60);
//////////            tcmAux.getColumn(INT_TBL_DAT_FEC_ULT_REP).setPreferredWidth(100);
//////////            
//////////            
//////////            
//////////            // Nuevos campos de origen
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_ORI).setPreferredWidth(70);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_ORI).setPreferredWidth(70);
//////////
//////////            // Nuevos campos de reposición calculada
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MIN).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MIN).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MED).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MED).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MAX).setPreferredWidth(90);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_PES_MAX).setPreferredWidth(90);
//////////
//////////            // Nuevos campos de destino
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_DES).setPreferredWidth(70);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_DES).setPreferredWidth(70);
//////////
//////////
//////////            tcmAux.getColumn(INT_TBL_DAT_UBI).setPreferredWidth(80); 
//////////            
//////////            
//////////            
//////////            
//////////            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
////////////////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);
////////////////////            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setResizable(false);
////////////////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setResizable(false);
////////////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setResizable(false);
//////////            //tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setResizable(false);
//////////            
//////////            //Configurar JTable: Ocultar columnas del sistema.
//////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_DIS, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_MIN, tblDat);
////////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_ACT, tblDat);  // JM Comentado el 29/Jun/2023
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_UBI, tblDat);  // JM Agregado el 29/Jun/2023
//////////////////////////////
//////////////////////////////        
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_DIS, tblDat);
//////////////////////////////            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MIN, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_MAX, tblDat);
//////////////////////////////            objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_UNI_MED, tblDat);
//////////////////////////////            //objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_DIS, tblDat);  
//////////////////////////////            
//////////////////////////////            if(! objTooBar.isOpcionEnabled(2337)){
//////////////////////////////                objTblModRep.addSystemHiddenColumn(INT_TBL_DAT_CAN_REP_CAL, tblDat);
//////////////////////////////            }
//////////
//////////  
//////////                                
//////////
//////////
//////////
//////////            ocultarColumnasReptblDat(INT_TBL_DAT_CAN_REP_CAL_PES_MIN,INT_TBL_DAT_CAN_REP_CAL_PES_MED,INT_TBL_DAT_CAN_REP_CAL_PES_MAX,
//////////                                     INT_TBL_DAT_MIN,INT_TBL_DAT_MAX,INT_TBL_DAT_UBI,
//////////                                     INT_TBL_DAT_UNI_MED,INT_TBL_DAT_MIN,INT_TBL_DAT_MAX,
//////////                                     INT_TBL_DAT_STK_MIN,
//////////                                     INT_TBL_DAT_CAN_MIN_ORI,INT_TBL_DAT_CAN_MAX_ORI,INT_TBL_DAT_CAN_REP_DIS,INT_TBL_DAT_CAN_DIS,INT_TBL_DAT_TRA,INT_TBL_DAT_COD_ITM,INT_TBL_DAT_COD_ITM_MAE);
//////////
//////////            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//////////            objMouMotAda = new ZafMouMotAda();
//////////            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
//////////            //Configurar JTable: Establecer la fila de cabecera.
//////////            objTblFilCab = new ZafTblFilCab(tblDat);
//////////            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
//////////
//////////            //Configurar JTable: Editor de básqueda.
//////////            objTblBus = new ZafTblBus(tblDat);
//////////////            objTblOrd = new ZafTblOrd(tblDat);
//////////            configurarOrdenamientoPersonalizado();
//////////
//////////            //Configurar JTable: Renderizar celdas.
//////////            objTblCelRenChk=new ZafTblCelRenChk();
//////////            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
//////////            objTblCelRenChk=null;
//////////          
//////////            //Configurar JTable: Editor de celdas.
//////////            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
//////////            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
//////////            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
//////////            {
//////////                BigDecimal bdeCanReaRep=new BigDecimal(0);
//////////                BigDecimal bdeCanStkDis=new BigDecimal(0);
//////////                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//////////                    if (objTblCelEdiChk.isChecked())
//////////                    {
//////////                        intNumRegSel++;
//////////                        bdeCanReaRep=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString());
//////////                        bdeCanStkDis=objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?new BigDecimal(0):new BigDecimal(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
//////////                        if( (intNumRegSel<=13))
//////////                        {
//////////                            ////////////////////////////////////////////////////////////////////////////////////////////////////
//////////                            //Validación por tipo de documento de retorno que no verifique stock de la bodega de distribución.
//////////                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)) //REINBO - Reposición de inventario desde otras bodegas
//////////                            {
//////////                                if(bdeCanStkDis.compareTo(bdeCanReaRep)>=0)
//////////                                {
//////////                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                }
//////////                                else
//////////                                {
//////////                                    objTblModRep.setValueAt(bdeCanStkDis, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                }
//////////                            }
//////////                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)) //REINBD - Retorno de inventario a bodega de Distribución
//////////                            {
//////////                                if(bdeCanReaRep.compareTo(new BigDecimal("0"))>0)
//////////                                {
//////////                                    objTblModRep.setValueAt(bdeCanReaRep, tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                }
//////////                            }
//////////                            ////////////////////////////////////////////////////////////////////////////////////////////////////
//////////                            
//////////                            if(bdeCanReaRep.compareTo(new BigDecimal(BigInteger.ONE))<=0)
//////////                            {
//////////                                String strMsg="";
//////////                                strMsg+="<HTML>";
//////////                                strMsg+="El registro seleccionado tiene un valor menor a la unidad. <BR>";
//////////                                strMsg+="¿Está seguro que desea seleccionar la cantidad a reponer?";
//////////                                strMsg+="</HTML>";
//////////                                switch (mostrarMsgCon(strMsg))
//////////                                {
//////////                                    case 0: //YES_OPTION
//////////
//////////                                        break;
//////////                                    case 1: //NO_OPTION
//////////                                        objTblCelEdiChk.setCancelarEdicion(true);
//////////                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//////////                                        intNumRegSel--;
//////////                                        break;
//////////                                    case 2: //CANCEL_OPTION
//////////                                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                        objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//////////                                        intNumRegSel--;
//////////                                }
//////////                            }
//////////                            if (!getSumaTotalPeso(tblDat.getSelectedRow())) 
//////////                            {
//////////                                mostrarMsgInf("<HTML>El registro seleccionado tiene un peso mayor al disponible.</HTML>");
//////////                                objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                                objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//////////                                intNumRegSel--;
//////////                            }
//////////                        }
//////////                        
//////////                        else{
//////////                            mostrarMsgInf("<HTML>Se han seleccionado mas de 13 registros.</HTML>");
//////////                            objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                            objTblModRep.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK);
//////////                            intNumRegSel--;
//////////                        }
//////////
//////////                    }
//////////                    else{
//////////                        if(getRestaTotalPeso(tblDat.getSelectedRow())){
//////////                        }
//////////                        intNumRegSel--;
//////////                        objTblModRep.setValueAt("0", tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL);
//////////                    }
//////////
//////////                    double dblCan; // José Marín M.
//////////                    if(objTblCelEdiChk.isChecked())
//////////                    {
//////////                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_SEL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
//////////                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
//////////                    }
//////////                    else{
//////////                        dblCan=Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL).toString()) * (Double.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_PES).toString()));
//////////                        objTblModRep.setValueAt(dblCan, tblDat.getSelectedRow(), INT_TBL_DAT_PES_TOT_KGR);
//////////                    }
//////////                    objTooBar.setMenSis("El número de registros seleccionados es: " + intNumRegSel);
//////////                }
//////////            });
//////////
//////////            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat); 
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellEditor(objTblCelEdiTxt);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellEditor(objTblCelEdiTxt);
//////////
//////////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//////////            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
//////////            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_ORI).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_ORI).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MIN_DES).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_MAX_DES).setCellRenderer(objTblCelRenLbl);
//////////            
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PIS).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_PRO).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_COB_SUC).setCellRenderer(objTblCelRenLbl);
//////////            
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MIN).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MED).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL_MAX).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setCellRenderer(objTblCelRenLbl); 
//////////    
//////////            objTblCelRenLbl=null;
//////////
//////////            objTblModRep.setColumnDataType(INT_TBL_DAT_CAN_REP_SEL, objTblModRep.INT_COL_DBL, new Integer(0), null);
//////////            
//////////            objTblCelRenLbl=new ZafTblCelRenLbl();
//////////            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//////////            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
//////////            tcmAux.getColumn(INT_TBL_DAT_UBI).setCellRenderer(objTblCelRenLbl);
//////////            objTblCelRenLbl=null;
//////////            
//////////objTblCelEdiTxtCanRep = new ZafTblCelEdiTxt(tblDat);
//////////
//////////tcmAux.getColumn(
//////////    INT_TBL_DAT_CAN_REP_SEL
//////////).setCellEditor(objTblCelEdiTxtCanRep);
//////////
//////////
//////////objTblCelEdiTxtCanRep.addTableEditorListener(
//////////    new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
//////////    {
//////////        BigDecimal bdeValCanRepBefEdi = new BigDecimal("0");
//////////        BigDecimal bdeValCanRepAftEdi = new BigDecimal("0");
//////////        BigDecimal bdeValCanRepMaxDes = new BigDecimal("0");
//////////        BigDecimal bdeValResCobRep = new BigDecimal("0");
//////////        BigDecimal bdeValResCobSuc = new BigDecimal("0");
//////////
//////////        BigDecimal bdeStkDis = new BigDecimal("0");
//////////
//////////        BigDecimal bdeValCanRepCal = new BigDecimal("0");
//////////        BigDecimal bdeValKg = new BigDecimal("0");
//////////        BigDecimal bdeValKgTot = new BigDecimal("0");
//////////
//////////        Double disponible;
//////////        Double dblDisponible_tbm_invBod;
//////////
//////////        Double disponibleMax;
//////////        Double disponibleMin;
//////////
//////////        String strUniMed = "";
//////////
//////////        int fila = -1;
//////////        int columna = -1;
//////////
//////////
//////////        /*
//////////         * 21092026
//////////         *
//////////         * Esta bandera evita que al modificar la celda
//////////         * programáticamente con setValueAt(), se vuelva a ejecutar
//////////         * la validación del editor.
//////////         */
//////////        boolean blnAjustandoCantidad = false;
//////////
//////////
//////////        public void beforeEdit(
//////////            Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt
//////////        )
//////////        {
//////////            /*
//////////             * Si estamos cambiando el valor desde código,
//////////             * no volvemos a procesar el evento.
//////////             */
//////////            if (blnAjustandoCantidad)
//////////            {
//////////                return;
//////////            }
//////////
//////////
//////////            fila = getFilaModeloSeleccionada();
//////////
//////////            int columnaVista = tblDat.getSelectedColumn();
//////////
//////////            columna = columnaVista == -1
//////////                    ? -1
//////////                    : tblDat.convertColumnIndexToModel(columnaVista);
//////////
//////////
//////////            if (fila == -1)
//////////            {
//////////                return;
//////////            }
//////////
//////////
//////////            /*==========================================================
//////////              GUARDAR CANTIDAD ANTES DE EDITAR
//////////            ==========================================================*/
//////////            bdeValCanRepBefEdi = new BigDecimal(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ) == null
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ).toString().trim().equals("")
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ).toString()
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              STOCK DISPONIBLE
//////////            ==========================================================*/
//////////            bdeStkDis = new BigDecimal(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
//////////                ) == null
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
//////////                ).toString().trim().equals("")
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP
//////////                ).toString()
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              UNIDAD DE MEDIDA
//////////            ==========================================================*/
//////////            strUniMed = objTblModRep.getValueAt(
//////////                fila,
//////////                INT_TBL_DAT_DES_COR_UNI_MED
//////////            ) == null
//////////            ? ""
//////////            : objTblModRep.getValueAt(
//////////                fila,
//////////                INT_TBL_DAT_DES_COR_UNI_MED
//////////            ).toString();
//////////
//////////
//////////            /*==========================================================
//////////              DISPONIBLES CALCULADOS
//////////            ==========================================================*/
//////////            disponible = objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_CAL
//////////                )
//////////            );
//////////
//////////
//////////            disponibleMax = objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_CAL_MAX
//////////                )
//////////            );
//////////
//////////
//////////            disponibleMin = objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_CAL_MIN
//////////                )
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              DISPONIBLE EN tbm_invBod
//////////            ==========================================================*/
//////////            dblDisponible_tbm_invBod = objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_DIS
//////////                )
//////////            ) < 0
//////////            ? 0
//////////            : objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_DIS
//////////                )
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              CHECK
//////////            ==========================================================*/
//////////            if (
//////////                objUti.parseDouble(
//////////                    objTblModRep.getValueAt(
//////////                        fila,
//////////                        INT_TBL_DAT_CAN_REP_SEL
//////////                    )
//////////                ) <= 0
//////////            )
//////////            {
//////////                objTblModRep.setValueAt(
//////////                    false,
//////////                    fila,
//////////                    INT_TBL_DAT_CHK
//////////                );
//////////            }
//////////
//////////
//////////            quitarItmCanRepCer();
//////////        }
//////////
//////////
//////////        public void afterEdit(
//////////            Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt
//////////        )
//////////        {
//////////            /*
//////////             * Evitar que un setValueAt() realizado desde este mismo
//////////             * método vuelva a ejecutar toda la validación.
//////////             */
//////////            if (blnAjustandoCantidad)
//////////            {
//////////                return;
//////////            }
//////////
//////////
//////////            if (fila == -1)
//////////            {
//////////                return;
//////////            }
//////////
//////////
//////////            /*==========================================================
//////////              OBTENER NUEVA CANTIDAD INGRESADA
//////////            ==========================================================*/
//////////            bdeValCanRepAftEdi = new BigDecimal(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ) == null
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ).toString().trim().equals("")
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                ).toString()
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              CANTIDAD MÁXIMA DE DESPACHO
//////////            ==========================================================*/
//////////            bdeValCanRepMaxDes = new BigDecimal(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_MAX_DES
//////////                ) == null
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_MAX_DES
//////////                ).toString().trim().equals("")
//////////                ? "0"
//////////                : objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_MAX_DES
//////////                ).toString()
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              OBTENER ITEM
//////////            ==========================================================*/
//////////            Object objItem = objTblModRep.getValueAt(
//////////                fila,
//////////                INT_TBL_DAT_COD_ALT_ITM
//////////            );
//////////
//////////
//////////            String strItem = "";
//////////
//////////            if (objItem != null)
//////////            {
//////////                strItem = objItem.toString().trim();
//////////            }
//////////
//////////
//////////            /*==========================================================
//////////              VALIDAR DECIMALES DEL ITEM
//////////            ==========================================================*/
//////////            double dblCan = objUti.parseDouble(
//////////                objTblModRep.getValueAt(
//////////                    fila,
//////////                    INT_TBL_DAT_CAN_REP_SEL
//////////                )
//////////            );
//////////
//////////
//////////            int intDecSQL = 0;
//////////            String strTipoNumero = "";
//////////
//////////
//////////            if (!strItem.equals(""))
//////////            {
//////////                intDecSQL = objUtiGP.obtenerDecimales(
//////////                    strItem
//////////                );
//////////            }
//////////
//////////
//////////            strTipoNumero = objUtiGP.obtenerTipoNumero(
//////////                dblCan
//////////            );
//////////
//////////
//////////            /*
//////////             * Si GP indica que el item NO permite cantidades
//////////             * decimales y el usuario ingresó una cantidad decimal:
//////////             *
//////////             * 1. Regresamos al valor anterior.
//////////             * 2. Mostramos el mensaje una sola vez.
//////////             * 3. Recalculamos los datos usando el valor original.
//////////             * 4. Salimos del método.
//////////             */
//////////            if (
//////////                intDecSQL == 1
//////////                &&
//////////                strTipoNumero.equals("DECIMAL")
//////////            )
//////////            {
//////////                try
//////////                {
//////////                    blnAjustandoCantidad = true;
//////////
//////////
//////////                    /*==============================================
//////////                      REGRESAR AL VALOR ANTERIOR
//////////                    ==============================================*/
//////////                    objTblModRep.setValueAt(
//////////                        bdeValCanRepBefEdi,
//////////                        fila,
//////////                        INT_TBL_DAT_CAN_REP_SEL
//////////                    );
//////////
//////////
//////////                    /*==============================================
//////////                      RESTAURAR CHECK SEGÚN CANTIDAD ORIGINAL
//////////                    ==============================================*/
//////////                    if (
//////////                        bdeValCanRepBefEdi.compareTo(
//////////                            BigDecimal.ZERO
//////////                        ) > 0
//////////                    )
//////////                    {
//////////                        objTblModRep.setValueAt(
//////////                            true,
//////////                            fila,
//////////                            INT_TBL_DAT_CHK
//////////                        );
//////////                    }
//////////                    else
//////////                    {
//////////                        objTblModRep.setValueAt(
//////////                            false,
//////////                            fila,
//////////                            INT_TBL_DAT_CHK
//////////                        );
//////////                    }
//////////                }
//////////                finally
//////////                {
//////////                    blnAjustandoCantidad = false;
//////////                }
//////////
//////////
//////////                /*==============================================
//////////                  MOSTRAR MENSAJE UNA SOLA VEZ
//////////                ==============================================*/
//////////                mostrarMsgInf(
//////////                    "El item "
//////////                    + strItem
//////////                    + " no permite cantidades decimales."
//////////                );
//////////
////////// 
//////////                /*==============================================
//////////                  RECALCULAR CON EL VALOR ORIGINAL
//////////                ==============================================*/
//////////                calcularCobertura(fila);
//////////
//////////                ne_canTotSel = getNumeroRegistrosChequeados();
//////////
//////////                txtCanTot.setText(
//////////                    String.valueOf(ne_canTotSel)
//////////                );
//////////
//////////                calcularPesTotRep();
//////////
//////////
//////////                /*
//////////                 * No continuar porque la cantidad digitada
//////////                 * fue rechazada.
//////////                 */
//////////                return;
//////////            }
//////////
//////////
//////////            /*==========================================================
//////////              VALIDAR CANTIDAD
//////////            ==========================================================*/
//////////            validaCantidad(
//////////                fila,
//////////                bdeStkDis,
//////////                bdeValCanRepBefEdi,
//////////                bdeValCanRepAftEdi
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              CALCULAR COBERTURA
//////////            ==========================================================*/
//////////            calcularCobertura(fila);
//////////
//////////
//////////            /*==========================================================
//////////              MARCAR / DESMARCAR CHECK
//////////            ==========================================================*/
//////////            if (
//////////                objUti.parseDouble(
//////////                    objTblModRep.getValueAt(
//////////                        fila,
//////////                        INT_TBL_DAT_CAN_REP_SEL
//////////                    )
//////////                ) > 0
//////////            )
//////////            {
//////////                objTblModRep.setValueAt(
//////////                    true,
//////////                    fila,
//////////                    INT_TBL_DAT_CHK
//////////                );
//////////            }
//////////            else
//////////            {
//////////                objTblModRep.setValueAt(
//////////                    false,
//////////                    fila,
//////////                    INT_TBL_DAT_CHK
//////////                );
//////////            }
//////////
//////////
//////////            /*==========================================================
//////////              TOTAL DE REGISTROS SELECCIONADOS
//////////            ==========================================================*/
//////////            ne_canTotSel = getNumeroRegistrosChequeados();
//////////
//////////            txtCanTot.setText(
//////////                String.valueOf(ne_canTotSel)
//////////            );
//////////
//////////
//////////            /*==========================================================
//////////              CALCULAR PESO TOTAL
//////////            ==========================================================*/
//////////            calcularPesTotRep();
//////////        }
//////////    }
//////////);
//////////            
//////////            objTblCelRenLblCol = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_DIS).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_CAL).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_CAN_REP_DIS).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_MIN).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_MIN).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_MAX).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setCellRenderer(objTblCelRenLblCol);
//////////            tcmAux.getColumn(INT_TBL_DAT_TRA).setCellRenderer(objTblCelRenLblCol);
//////////            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
//////////            objTblCelRenLblCol.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
//////////            objTblCelRenLblCol.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
//////////            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//////////                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt){
//////////                    if(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS)!=null){
//////////                        if(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS).toString().length()>0){
//////////                            if (Double.parseDouble(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_DIS).toString() ) < 
//////////                                   Double.parseDouble(objTblModRep.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_CAN_REP_CAL).toString() ) ){
//////////                                objTblCelRenLblCol.setBackground(colFonCol);
//////////                            }
//////////                            else{
//////////                                objTblCelRenLblCol.setBackground(javax.swing.UIManager.getColor("Table.background"));
//////////                            }
//////////                        }
//////////                    }
//////////                }
//////////            });
////////////            objTblCelRenLblCol = null;
//////////
//////////// =====================================================
//////////// COLUMNA AMARILLA
//////////// =====================================================
//////////ZafTblCelRenLbl objTblCelRenLblAmarillo = new ZafTblCelRenLbl() {
//////////    @Override
//////////    public java.awt.Component getTableCellRendererComponent(
//////////            javax.swing.JTable table,
//////////            Object value,
//////////            boolean isSelected,
//////////            boolean hasFocus,
//////////            int row,
//////////            int column) {
//////////
//////////        Component comp = super.getTableCellRendererComponent(
//////////                table, value, isSelected, hasFocus, row, column);
//////////
//////////        if (!isSelected) {
//////////            comp.setBackground(new java.awt.Color(255, 255, 153)); // Amarillo claro
//////////        } else {
//////////            comp.setBackground(table.getSelectionBackground());
//////////        }
//////////
//////////        return comp;
//////////    }
//////////};
//////////
//////////tcmAux.getColumn(INT_TBL_DAT_DIS_IN4).setCellRenderer(objTblCelRenLblAmarillo);
//////////
//////////
//////////// =====================================================
//////////// COLUMNA AZUL CLARO
//////////// =====================================================
//////////ZafTblCelRenLbl objTblCelRenLblAzul = new ZafTblCelRenLbl() {
//////////    @Override
//////////    public java.awt.Component getTableCellRendererComponent(
//////////            javax.swing.JTable table,
//////////            Object value,
//////////            boolean isSelected,
//////////            boolean hasFocus,
//////////            int row,
//////////            int column) {
//////////
//////////        Component comp = super.getTableCellRendererComponent(
//////////                table, value, isSelected, hasFocus, row, column);
//////////
//////////        if (!isSelected) {
//////////            comp.setBackground(new java.awt.Color(204, 229, 255)); // Azul claro
//////////        } else {
//////////            comp.setBackground(table.getSelectionBackground());
//////////        }
//////////
//////////        return comp;
//////////    }
//////////};
//////////
//////////tcmAux.getColumn(INT_TBL_DAT_CAN_REP_SEL).setCellRenderer(objTblCelRenLblAzul);
//////////
//////////
//////////            
//////////            
//////////            
//////////            objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI); //tabla editable 
//////////
//////////
//////////            tcmAux=null;
//////////        } 
//////////        catch (Exception e)
//////////        {
//////////            blnRes = false;
//////////            objUti.mostrarMsgErr_F1(this, e);
//////////        }
//////////        return blnRes;
//////////    }
    
    private boolean configurarTblDatABCCapCarga()  
    {
        boolean blnRes = true;
        try
        {
            //System.out.println("configurarTblDat ");
            //Configurar JTable: Establecer el modelo.
            vecDatCapCar = new Vector();    //Almacena los datos
            vecCabCapCar = new Vector(23);  //Almacena las cabeceras
            vecCabCapCar.clear();
            vecCabCapCar.add(INT_TBL_DAT_CAP_CAR_LIN, "");
            vecCabCapCar.add(INT_TBL_DAT_CAP_CAR_CAT_NIV, "Cat.Nivel");
            vecCabCapCar.add(INT_TBL_DAT_CAP_CAR_NIV, "Nivel");
            
            objTblModRepCapCar = new ZafTblMod();
            objTblModRepCapCar.setHeader(vecCabCapCar);
            

////////            objTblModRepCapCar.setHeader(vecCab);
////////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.


            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatCapCar.setModel(objTblModRepCapCar);
            
            // No permitir edición manual de ninguna columna
            Vector vecColEdiCapCar = new Vector();
            objTblModRepCapCar.setColumnasEditables(vecColEdiCapCar);
            vecColEdiCapCar = null;

            // Pero permitir operaciones sobre el modelo
            objTblModRepCapCar.setModoOperacion(ZafTblMod.INT_TBL_EDI);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDatCapCar.setRowSelectionAllowed(true);
            tblDatCapCar.getTableHeader().setReorderingAllowed(false);
            tblDatCapCar.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDatCapCar, INT_TBL_DAT_CAP_CAR_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatCapCar);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatCapCar.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatCapCar.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_LIN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_CAT_NIV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_NIV).setPreferredWidth(50);
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_CAT_NIV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_NIV).setResizable(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatCapCar.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatCapCar);
            tcmAux.getColumn(INT_TBL_DAT_CAP_CAR_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDatCapCar);
////            objTblOrd = new ZafTblOrd(tblDat);
//////////            configurarOrdenamientoPersonalizado();



             
            
            
            //objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI); //tabla editable 


            tcmAux=null;
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTblDatABCTipEmi()  
    {
        boolean blnRes = true;
        try
        {
            //System.out.println("configurarTblDat ");
            //Configurar JTable: Establecer el modelo.
            vecDatTipEmi = new Vector();    //Almacena los datos
            vecCabTipEmi = new Vector(23);  //Almacena las cabeceras
            vecCabTipEmi.clear();
            vecCabTipEmi.add(INT_TBL_DAT_TIP_EMI_LIN, "");
            vecCabTipEmi.add(INT_TBL_DAT_TIP_EMI_CAT_NIV, "Cat.Nivel");
            vecCabTipEmi.add(INT_TBL_DAT_TIP_EMI_NIV, "Nivel");
            
            objTblModRepTipEmi = new ZafTblMod();
            objTblModRepTipEmi.setHeader(vecCabTipEmi);
            

////////            objTblModRepTipEnv.setHeader(vecCab);
////////            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.


            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatTipEmi.setModel(objTblModRepTipEmi);
            
            // No permitir edición manual de ninguna columna
            Vector vecColEdiTipEmi = new Vector();
            objTblModRepTipEmi.setColumnasEditables(vecColEdiTipEmi);
            vecColEdiTipEmi = null;

            // Pero permitir operaciones sobre el modelo
            objTblModRepTipEmi.setModoOperacion(ZafTblMod.INT_TBL_EDI);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDatTipEmi.setRowSelectionAllowed(true);
            tblDatTipEmi.getTableHeader().setReorderingAllowed(false);
            tblDatTipEmi.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDatTipEmi, INT_TBL_DAT_TIP_EMI_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDatTipEmi);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatTipEmi.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDatTipEmi.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_LIN).setPreferredWidth(5);
            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_CAT_NIV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_NIV).setPreferredWidth(50);
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_CAT_NIV).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_NIV).setResizable(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDatTipEmi.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDatTipEmi);
            tcmAux.getColumn(INT_TBL_DAT_TIP_EMI_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDatTipEmi);
////            objTblOrd = new ZafTblOrd(tblDat);
//////////            configurarOrdenamientoPersonalizado();



             
            
            
            //objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI); //tabla editable 


            tcmAux=null;
        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private int getFilaModeloSeleccionada() {
    int filaVista = tblDat.getSelectedRow();

    if (filaVista == -1) {
        filaVista = tblDat.getEditingRow();
    }

    if (filaVista == -1) {
        return -1;
    }

    return tblDat.convertRowIndexToModel(filaVista);
}

    private final Color colFonCol =new Color(255,172,165); // JM: el color me lo paso el Ing. Eddye correo al 27/Feb/2020
    
    
    /**
     * Esta función configura el JTable "tblDatTrs".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatTrs() 
    {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatTrs = new Vector();    //Almacena los datos
            vecCabTrs = new Vector(25);  //Almacena las cabeceras
            vecCabTrs.clear();
            vecCabTrs.add(INT_TBL_DAT_TRS_LIN, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_CHK, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_EMP, "Cod.Emp.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_LOC, "Cod.Loc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_TIP_DOC, "Cod.Tip.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_DES_COR_TIP_DOC, "Tip.doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_DOC, "Cod.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_NUM_DOC, "Num.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_FEC_DOC, "Fec.Doc.");
            vecCabTrs.add(INT_TBL_DAT_TRS_BUT, "");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_BOD_ORI, "Cod.Bod.Ori.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ITM, "Cod.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ITM_MAE, "Cod.Itm.Mae");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_ALT_ITM, "Cod.Alt.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_NOM_ITM, "Nom.Itm.");
            vecCabTrs.add(INT_TBL_DAT_TRS_UNI_MED, "Uni.Med.");
            vecCabTrs.add(INT_TBL_DAT_TRS_CAN_REP, "Can.Rep.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_ACT, "Stk.Act.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_MIN, "Stk.Min.");
            vecCabTrs.add(INT_TBL_DAT_TRS_STK_DIS, "Stk.Dis.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COS_UNI, "Cos.Uni.");
            vecCabTrs.add(INT_TBL_DAT_TRS_PRE_UNI, "Pre.Uni.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COS_TOT, "Cos.Tot.");
            vecCabTrs.add(INT_TBL_DAT_TRS_PES_GRP, "Pes.Grp.");
            vecCabTrs.add(INT_TBL_DAT_TRS_COD_BOD_DES, "Cod.Bod.Des.");

            objTblModRepTrs = new ZafTblMod();

            objTblModRepTrs.setHeader(vecCabTrs);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_CAN_REP, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_ACT, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_MIN, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_STK_DIS, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_COS_UNI, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_PRE_UNI, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_COS_TOT, objTblModRepTrs.INT_COL_INT, new Integer(0), null);
            objTblModRepTrs.setColumnDataType(INT_TBL_DAT_TRS_PES_GRP, objTblModRepTrs.INT_COL_INT, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblTrs.setModel(objTblModRepTrs);
            //Configurar JTable: Establecer tipo de seleccián.
            tblTrs.setRowSelectionAllowed(true);
            tblTrs.getTableHeader().setReorderingAllowed(false);
            tblTrs.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTrs = new ZafColNumerada(tblTrs, INT_TBL_DAT_TRS_LIN);


            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuTrs = new ZafTblPopMnu(tblTrs);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTrs.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTrs.getColumnModel();


            tcmAux.getColumn(INT_TBL_DAT_TRS_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TRS_DES_COR_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TRS_BUT).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_ORI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ALT_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NOM_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CAN_REP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_MIN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_DIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PRE_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PES_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_DES).setPreferredWidth(60);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_ORI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_ALT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_NOM_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_UNI_MED).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CAN_REP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_ACT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_MIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_STK_DIS).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_UNI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PRE_UNI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COS_TOT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_PES_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_TRS_COD_BOD_DES).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_CHK, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_TIP_DOC, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_DOC, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_BOD_ORI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ITM_MAE, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_ALT_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_NOM_ITM, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_UNI_MED, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_CAN_REP, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_ACT, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_MIN, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_STK_DIS, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COS_UNI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_PRE_UNI, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COS_TOT, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_PES_GRP, tblTrs);
            objTblModRepTrs.addSystemHiddenColumn(INT_TBL_DAT_TRS_COD_BOD_DES, tblTrs);
            


            
            
            

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaTrs = new ZafMouMotAdaTrs();
            tblTrs.getTableHeader().addMouseMotionListener(objMouMotAdaTrs);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTrs = new ZafTblFilCab(tblTrs);
            tcmAux.getColumn(INT_TBL_DAT_TRS_LIN).setCellRenderer(objTblFilCabTrs);


            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkTrs=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setCellRenderer(objTblCelRenChkTrs);
            objTblCelRenChkTrs=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkTrs=new ZafTblCelEdiChk(tblTrs);
            tcmAux.getColumn(INT_TBL_DAT_TRS_CHK).setCellEditor(objTblCelEdiChkTrs);


            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);

            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;

            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE MAESTRO DE CLIENTES
            objTblCelRenBut=new ZafTblCelRenBut();
            tblTrs.getColumnModel().getColumn(INT_TBL_DAT_TRS_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblTrs.getColumnModel().getColumn(INT_TBL_DAT_TRS_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblTrs.getSelectedRow();
                    //SE DEBE VALIDAR QUE EXISTAN DATOS EN ESA FILA
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abreTransferencia(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            tcmAux=null;


        } 
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private void ocultarColumnasReptblDat(int... columnas) 
    {
        try 
        {
            for (int columna : columnas) {
                objTblModRep.addSystemHiddenColumn(columna, tblDat);
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) 
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del ítem";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg = "Código alterno 2 del ítem";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
            // 🔹 NUEVO CAMPO DE TIPO DE ÍTEM
                case INT_TBL_DAT_ITM_TIP:
                    strMsg = "Tipo de ítem";
                    break;                    
                case INT_TBL_DAT_STK_ACT_BOD_CEN_DIS:
                    strMsg = "Stock Actual de la Bodega Centro Distribución";
                    break;
                case INT_TBL_DAT_DIS_IN4:
                    strMsg = "Stock Actual de la Bodega Centro Distribución 4";
                    break;                    
                case INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP:
                    strMsg = "Stock permitido en la bodega Centro de Distribución(Según mínimos y máximos).";
                    break;                         
                case INT_TBL_DAT_CAN_DIS:
                    strMsg = "Cantidad disponible en la bodega Centro de Distribución";
                    break;
                    
// 🔹 NUEVOS CAMPOS ORIGEN
                case INT_TBL_DAT_CAN_MIN_ORI:
                    strMsg = "Cantidad mínima permitida en la bodega de origen";
                    break;
                case INT_TBL_DAT_CAN_MAX_ORI:
                    strMsg = "Cantidad máxima permitida en la bodega de origen";
                    break;      
                    
            // 🔹 NUEVOS CAMPOS REPOSICIÓN CALCULADA
            case INT_TBL_DAT_CAN_REP_CAL_MIN:
                strMsg = "Cantidad mínima a reponer calculada";
                break;
            case INT_TBL_DAT_CAN_REP_CAL_PES_MIN:
                strMsg = "Peso total de la cantidad mínima a reponer";
                break;
            case INT_TBL_DAT_CAN_REP_CAL_MED:
                strMsg = "Cantidad media a reponer calculada";
                break;
            case INT_TBL_DAT_CAN_REP_CAL_PES_MED:
                strMsg = "Peso total de la cantidad media a reponer";
                break;
            case INT_TBL_DAT_CAN_REP_CAL_MAX:
                strMsg = "Cantidad máxima a reponer calculada";
                break;
            case INT_TBL_DAT_CAN_REP_CAL_PES_MAX:
                strMsg = "Peso total de la cantidad máxima a reponer";
                break;
                
               
                case INT_TBL_DAT_CAN_REP_SEL:
                    strMsg = "Cantidad que el programa repone";
                    break;
                case INT_TBL_DAT_CAN_REP_CAL:
                    strMsg = "Cantidad que se debe reponer según stock";
                    break;
                    
            case INT_TBL_DAT_CAN_COB_PIS:
                strMsg = "Cob. Piso";
                break;
            case INT_TBL_DAT_CAN_COB_PRO:
                strMsg = "Cob. Promedio";
                break;
            case INT_TBL_DAT_CAN_COB_SUC:
                strMsg = "Cob. Sucursal";
                break;
                
                    

            
            
                case INT_TBL_DAT_CAN_REP_DIS:
                    strMsg = "Cantidad disponible que se puede usar para el proceso de reposición";
                    break;
                case INT_TBL_DAT_PES:
                    strMsg = "Peso (kg)";
                    break;
                case INT_TBL_DAT_PES_TOT_KGR:
                    strMsg = "Peso Total en (kg)";
                    break;    
                case INT_TBL_DAT_STK_MIN:
                    strMsg = "Stock Minimo";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg = "Stock Actual";
                    break;
                    




            // 🔹 NUEVOS CAMPOS DESTINO
            case INT_TBL_DAT_CAN_MIN_DES:
                strMsg = "Cantidad mínima permitida en la bodega de destino";
                break;
            case INT_TBL_DAT_CAN_MAX_DES:
                strMsg = "Cantidad máxima permitida en la bodega de destino";
                break;
                case INT_TBL_DAT_UBI:
                    strMsg = "Ubicación del ítem";
                    break;


                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaTrs extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblTrs.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_TRS_COD_EMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_TRS_COD_LOC:
                    strMsg = "Código de local";
                    break;
                case INT_TBL_DAT_TRS_COD_TIP_DOC:
                    strMsg = "Código de tipo de documento";
                    break;
                case INT_TBL_DAT_TRS_DES_COR_TIP_DOC:
                    strMsg = "Tipo de documento";
                    break;
                case INT_TBL_DAT_TRS_COD_DOC:
                    strMsg = "Código de documento";
                    break;
                case INT_TBL_DAT_TRS_NUM_DOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_DAT_TRS_FEC_DOC:
                    strMsg = "Fecha de documento";
                    break;
                case INT_TBL_DAT_TRS_COD_BOD_ORI:
                    strMsg = "Código de bodega origen";
                    break;
                case INT_TBL_DAT_TRS_COD_ITM:
                    strMsg = "Código de item";
                    break;
                case INT_TBL_DAT_TRS_COD_ITM_MAE:
                    strMsg = "Código de item maestro";
                    break;
                case INT_TBL_DAT_TRS_COD_ALT_ITM:
                    strMsg = "Código alterno de item";
                    break;
                case INT_TBL_DAT_TRS_NOM_ITM:
                    strMsg = "Nombre de item";
                    break;
                case INT_TBL_DAT_TRS_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_TRS_CAN_REP:
                    strMsg = "Cantidad a reponer";
                    break;
                case INT_TBL_DAT_TRS_STK_ACT:
                    strMsg = "Stock actual";
                    break;
                case INT_TBL_DAT_TRS_STK_MIN:
                    strMsg = "Stock mínimo";
                    break;
                case INT_TBL_DAT_TRS_STK_DIS:
                    strMsg = "Stock disponible";
                    break;
                case INT_TBL_DAT_TRS_COS_UNI:
                    strMsg = "Costo unitario";
                    break;
                case INT_TBL_DAT_TRS_PRE_UNI:
                    strMsg = "Precio unitario";
                    break;
                case INT_TBL_DAT_TRS_COS_TOT:
                    strMsg = "Costo total";
                    break;
                case INT_TBL_DAT_TRS_PES_GRP:
                    strMsg = "Peso que tiene el item en el grupo, no en empresa";
                    break;
                case INT_TBL_DAT_TRS_COD_BOD_DES:
                    strMsg = "Código de bodega destino";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblTrs.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + intCodEmpGrp;
                strSQL += " AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc \n";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1 \n";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc) \n";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + intCodEmpGrp + "";
                strSQL += " AND a2.co_loc=" + intCodLocGrp + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //System.out.println("configurarVenConTipDoc \n" + strSQL);

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBodOri() 
    {
        boolean blnRes = true;
        try {
                //Listado de campos.
                ArrayList arlCam = new ArrayList();
                arlCam.add("a1.CodigoGP");
                arlCam.add("a1.NombreLargo");
                //Alias de los campos.
                ArrayList arlAli = new ArrayList();
                arlAli.add("Código Bodega");
                arlAli.add("Nombre de Bodega");
                //Ancho de las columnas.
                ArrayList arlAncCol = new ArrayList();
                arlAncCol.add("100");
                arlAncCol.add("334");
                
                strSQL = "";
                strSQL += " SELECT * FROM vwBodegas ";
                //System.out.println("configurarVenConBodOri \n" + strSQL);

                vcoBodOri = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Origen", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
//                vcoBodOri.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private ZafVenConGP vcoBodDes, vcoBodOri;
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBodDes() 
    {
        boolean blnRes = true;
        try {
                //Listado de campos.
                ArrayList arlCam = new ArrayList();
                arlCam.add("a1.CodigoGP");
                arlCam.add("a1.NombreLargo");
                //Alias de los campos.
                ArrayList arlAli = new ArrayList();
                arlAli.add("Código Bodega");
                arlAli.add("Nombre de Bodega");
                //Ancho de las columnas.
                ArrayList arlAncCol = new ArrayList();
                arlAncCol.add("100");
                arlAncCol.add("334");

                
                strSQL = "";
                strSQL += " SELECT * FROM vwBodegas   ";
                   
                   
                //System.out.println("configurarVenConBodDes \n" + strSQL);

                vcoBodDes = new ZafVenConGP(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Destino", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
//                vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        } 
                        else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
            //////////////////
            configurarVenConBodOri();
            configurarVenConBodDes();
       }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBodDes(int intTipBus) 
    {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodDes.buscar("a1.CodigoGP", txtCodBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(0);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtCodBodDes.setText(strCodBodDes);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodDes.buscar("a1.NombreLargo", txtNomBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(1);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtNomBodDes.setText(strNomBodDes);
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

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBodOri(int intTipBus) 
    {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.
                        vcoBodOri.setCampoBusqueda(2);
                        vcoBodOri.show();
                        if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodOri.buscar("a1.CodigoGP", txtCodBodOri.getText())) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        } else {
                            vcoBodOri.setCampoBusqueda(0);
                            vcoBodOri.setCriterio1(11);
                            vcoBodOri.cargarDatos();
                            vcoBodOri.show();
                            if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                                txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                                txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                                vcoBodOri.limpiar();
                            } else {
                                txtCodBodOri.setText(strCodBodOri);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodOri.buscar("a1.NombreLargo", txtNomBodOri.getText())) {
                            txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                            txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                            vcoBodOri.limpiar();
                        } else {
                            vcoBodOri.setCampoBusqueda(1);
                            vcoBodOri.setCriterio1(11);
                            vcoBodOri.cargarDatos();
                            vcoBodOri.show();
                            if (vcoBodOri.getSelectedButton() == vcoBodOri.INT_BUT_ACE) {
                                txtCodBodOri.setText(vcoBodOri.getValueAt(1));
                                txtNomBodOri.setText(vcoBodOri.getValueAt(2));
                                vcoBodOri.limpiar();
                            } else {
                                txtNomBodOri.setText(strNomBodOri);
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
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()//28072026
    {
        boolean blnRes=true;
        Connection conTipDocPre;
        try
        {
            conTipDocPre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conTipDocPre!=null){
                stm=conTipDocPre.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc \n";
                strSQL+=" FROM tbm_cabTipDoc AS a1 \n";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
                strSQL+=" AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" ( \n";
                strSQL+="       SELECT co_tipDoc \n";
                strSQL+="       FROM tbr_tipDocPrg \n";
                strSQL+="       WHERE co_emp=" + intCodEmpGrp;
                strSQL+="       AND co_loc=" + intCodLocGrp;
                strSQL+="       AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+="       AND st_reg='S' \n";
                strSQL+=" ) \n";
                //System.out.println("mostrarTipDocPre: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    
//////                    if(objTooBar.getEstado()=='n')
//////                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                }
                rst.close();
                stm.close();
                conTipDocPre.close();
                rst=null;
                stm=null;
                conTipDocPre=null;
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrupo = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblBodOri = new javax.swing.JLabel();
        txtCodBodOri = new javax.swing.JTextField();
        txtNomBodOri = new javax.swing.JTextField();
        butBodOri = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        lblPesDis = new javax.swing.JLabel();
        txtPesDis = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblPesTot = new javax.swing.JLabel();
        txtPesTot = new javax.swing.JTextField();
        butCarItm = new javax.swing.JButton();
        lblPorVar = new javax.swing.JLabel();
        optPorVar = new javax.swing.JRadioButton();
        optItmApl = new javax.swing.JRadioButton();
        optItmSelUsr = new javax.swing.JRadioButton();
        butLlamada = new javax.swing.JButton();
        lblBodDes1 = new javax.swing.JLabel();
        rbMax = new javax.swing.JRadioButton();
        rbMed = new javax.swing.JRadioButton();
        rbMin = new javax.swing.JRadioButton();
        lblCantTot = new javax.swing.JLabel();
        txtCanTot = new javax.swing.JTextField();
        chk_capCar = new javax.swing.JCheckBox();
        panRep = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panConf = new javax.swing.JPanel();
        panCapCar = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cmb_abcCat = new javax.swing.JComboBox<>();
        chk_conCapCar = new javax.swing.JCheckBox();
        btn_anadirAbcCapCar = new javax.swing.JButton();
        btn_quitarAbcCapCar = new javax.swing.JButton();
        cmb_abcCapCar = new javax.swing.JComboBox<>();
        spnDat1 = new javax.swing.JScrollPane();
        tblDatCapCar = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panTipEmi = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cmb_abcTipEmi = new javax.swing.JComboBox<>();
        btn_agrAbcTipEm = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        spnDat2 = new javax.swing.JScrollPane();
        tblDatTipEmi = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        cmb_tipEmi = new javax.swing.JComboBox<>();
        btn_quitarAbcCapCar1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmb_abcCatTipEmi = new javax.swing.JComboBox<>();
        panTraInv = new javax.swing.JPanel();
        spnTrs = new javax.swing.JScrollPane();
        tblTrs = new javax.swing.JTable();
        panBarBot = new javax.swing.JPanel();

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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.PAGE_START);

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(3, 2, 122, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(95, 2, 32, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(128, 2, 80, 20);

        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(205, 2, 200, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(406, 2, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 130, 20);

        lblBodOri.setText("Bodega origen:");
        lblBodOri.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodOri);
        lblBodOri.setBounds(3, 23, 110, 20);

        txtCodBodOri.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodOriFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodOriFocusLost(evt);
            }
        });
        txtCodBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodOriActionPerformed(evt);
            }
        });
        panCabFil.add(txtCodBodOri);
        txtCodBodOri.setBounds(128, 23, 80, 20);

        txtNomBodOri.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodOriFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodOriFocusLost(evt);
            }
        });
        txtNomBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodOriActionPerformed(evt);
            }
        });
        panCabFil.add(txtNomBodOri);
        txtNomBodOri.setBounds(205, 23, 200, 20);

        butBodOri.setText("...");
        butBodOri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodOriActionPerformed(evt);
            }
        });
        panCabFil.add(butBodOri);
        butBodOri.setBounds(406, 23, 20, 20);

        lblNumDoc.setText("Número documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc);
        lblNumDoc.setBounds(432, 27, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(560, 27, 120, 20);

        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        panCabFil.add(txtCodBodDes);
        txtCodBodDes.setBounds(128, 44, 80, 20);

        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        panCabFil.add(txtNomBodDes);
        txtNomBodDes.setBounds(205, 44, 200, 20);

        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panCabFil.add(butBodDes);
        butBodDes.setBounds(406, 44, 20, 20);

        lblPesDis.setText("Peso disponible (kg):");
        lblPesDis.setToolTipText("Número alterno 1");
        panCabFil.add(lblPesDis);
        lblPesDis.setBounds(432, 48, 130, 20);

        txtPesDis.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesDis.setToolTipText("Número de egreso");
        panCabFil.add(txtPesDis);
        txtPesDis.setBounds(560, 48, 120, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(3, 64, 130, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(128, 65, 80, 20);

        lblPesTot.setText("Peso total (kg):");
        lblPesTot.setToolTipText("Número alterno 1");
        panCabFil.add(lblPesTot);
        lblPesTot.setBounds(432, 69, 130, 20);

        txtPesTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesTot.setToolTipText("Número de egreso");
        txtPesTot.setEnabled(false);
        txtPesTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesTotFocusGained(evt);
            }
        });
        panCabFil.add(txtPesTot);
        txtPesTot.setBounds(560, 69, 120, 20);

        butCarItm.setText("Cargar items");
        butCarItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCarItmActionPerformed(evt);
            }
        });
        panCabFil.add(butCarItm);
        butCarItm.setBounds(0, 100, 100, 23);
        panCabFil.add(lblPorVar);
        lblPorVar.setBounds(255, 90, 60, 14);

        optPorVar.setText("Por porcentaje de variación");
        optPorVar.setEnabled(false);
        optPorVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPorVarActionPerformed(evt);
            }
        });
        panCabFil.add(optPorVar);
        optPorVar.setBounds(440, 110, 190, 14);

        optItmApl.setText("Los primeros 13 items generados por el sistema");
        optItmApl.setEnabled(false);
        optItmApl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmAplActionPerformed(evt);
            }
        });
        panCabFil.add(optItmApl);
        optItmApl.setBounds(440, 110, 160, 14);

        optItmSelUsr.setSelected(true);
        optItmSelUsr.setText("Items seleccionados por usuario(13)");
        optItmSelUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optItmSelUsrActionPerformed(evt);
            }
        });
        panCabFil.add(optItmSelUsr);
        optItmSelUsr.setBounds(430, 110, 150, 14);

        butLlamada.setText("Manual");
        butLlamada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLlamadaActionPerformed(evt);
            }
        });
        panCabFil.add(butLlamada);
        butLlamada.setBounds(0, 80, 100, 23);

        lblBodDes1.setText("Bodega destino:");
        lblBodDes1.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBodDes1);
        lblBodDes1.setBounds(3, 44, 110, 20);

        btnGrupo.add(rbMax);
        rbMax.setText("Maximo");
        rbMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMaxActionPerformed(evt);
            }
        });
        panCabFil.add(rbMax);
        rbMax.setBounds(210, 65, 80, 23);

        btnGrupo.add(rbMed);
        rbMed.setText("Media");
        rbMed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMedActionPerformed(evt);
            }
        });
        panCabFil.add(rbMed);
        rbMed.setBounds(210, 85, 80, 23);

        btnGrupo.add(rbMin);
        rbMin.setText("Minimo");
        rbMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMinActionPerformed(evt);
            }
        });
        panCabFil.add(rbMin);
        rbMin.setBounds(210, 105, 80, 23);

        lblCantTot.setText("Cantidad total :");
        lblCantTot.setToolTipText("Número alterno 1");
        panCabFil.add(lblCantTot);
        lblCantTot.setBounds(432, 90, 130, 20);

        txtCanTot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanTot.setToolTipText("Número de egreso");
        txtCanTot.setEnabled(false);
        txtCanTot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCanTotFocusGained(evt);
            }
        });
        panCabFil.add(txtCanTot);
        txtCanTot.setBounds(560, 90, 120, 20);

        chk_capCar.setText("Capacidad Carga");
        chk_capCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_capCarActionPerformed(evt);
            }
        });
        panCabFil.add(chk_capCar);
        chk_capCar.setBounds(297, 70, 130, 30);

        panGenCab.add(panCabFil, java.awt.BorderLayout.CENTER);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

        panRep.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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
        spnDat.setViewportView(tblDat);

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panRep.add(panGenDet, java.awt.BorderLayout.CENTER);

        panCon.add(panRep, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panCon.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panCon);

        panConf.setLayout(new java.awt.BorderLayout());

        panCapCar.setLayout(null);

        jPanel5.setLayout(null);

        jPanel6.setLayout(null);

        cmb_abcCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_abcCatActionPerformed(evt);
            }
        });
        jPanel6.add(cmb_abcCat);
        cmb_abcCat.setBounds(130, 30, 120, 20);

        chk_conCapCar.setText("Capacidad Carga");
        chk_conCapCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_conCapCarActionPerformed(evt);
            }
        });
        jPanel6.add(chk_conCapCar);
        chk_conCapCar.setBounds(10, 0, 130, 30);

        btn_anadirAbcCapCar.setText("Añadir");
        btn_anadirAbcCapCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_anadirAbcCapCarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_anadirAbcCapCar);
        btn_anadirAbcCapCar.setBounds(10, 80, 80, 30);

        btn_quitarAbcCapCar.setText("Quitar");
        btn_quitarAbcCapCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarAbcCapCarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_quitarAbcCapCar);
        btn_quitarAbcCapCar.setBounds(170, 80, 80, 30);

        cmb_abcCapCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_abcCapCarActionPerformed(evt);
            }
        });
        jPanel6.add(cmb_abcCapCar);
        cmb_abcCapCar.setBounds(130, 50, 120, 20);

        tblDatCapCar.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat1.setViewportView(tblDatCapCar);

        jPanel6.add(spnDat1);
        spnDat1.setBounds(10, 110, 240, 170);

        jLabel8.setText("ABC");
        jPanel6.add(jLabel8);
        jLabel8.setBounds(10, 50, 120, 14);

        jLabel9.setText("Categoria ABC");
        jPanel6.add(jLabel9);
        jLabel9.setBounds(10, 30, 120, 14);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(0, 0, 250, 410);

        panCapCar.add(jPanel5);
        jPanel5.setBounds(0, 0, 250, 450);

        panConf.add(panCapCar, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Capacidad Carga", panConf);

        panTipEmi.setLayout(null);

        jPanel2.setLayout(null);

        jPanel4.setLayout(null);

        cmb_abcTipEmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_abcTipEmiActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_abcTipEmi);
        cmb_abcTipEmi.setBounds(130, 50, 120, 20);

        btn_agrAbcTipEm.setText("Añadir");
        btn_agrAbcTipEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agrAbcTipEmActionPerformed(evt);
            }
        });
        jPanel4.add(btn_agrAbcTipEm);
        btn_agrAbcTipEm.setBounds(10, 70, 80, 30);

        jLabel7.setText("Tipo de Emision");
        jPanel4.add(jLabel7);
        jLabel7.setBounds(10, 0, 90, 20);

        tblDatTipEmi.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDat2.setViewportView(tblDatTipEmi);

        jPanel4.add(spnDat2);
        spnDat2.setBounds(10, 100, 240, 190);

        cmb_tipEmi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Maximo", "Promedio", "Minimo" }));
        cmb_tipEmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_tipEmiActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_tipEmi);
        cmb_tipEmi.setBounds(130, 0, 120, 20);

        btn_quitarAbcCapCar1.setText("Quitar");
        btn_quitarAbcCapCar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarAbcCapCar1ActionPerformed(evt);
            }
        });
        jPanel4.add(btn_quitarAbcCapCar1);
        btn_quitarAbcCapCar1.setBounds(170, 70, 80, 30);

        jLabel10.setText("ABC");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(10, 50, 120, 14);

        jLabel11.setText("Categoria ABC");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(10, 20, 120, 14);

        cmb_abcCatTipEmi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_abcCatTipEmiActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_abcCatTipEmi);
        cmb_abcCatTipEmi.setBounds(130, 24, 120, 20);

        jPanel2.add(jPanel4);
        jPanel4.setBounds(0, 0, 520, 390);

        panTipEmi.add(jPanel2);
        jPanel2.setBounds(0, 0, 530, 400);

        tabFrm.addTab("Tipo Emision", panTipEmi);

        panTraInv.setLayout(new java.awt.BorderLayout());

        tblTrs.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTrs.setViewportView(tblTrs);

        panTraInv.add(spnTrs, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Transferencia de inventario", panTraInv);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 524);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) 
        {
            if (txtDesLarTipDoc.getText().equals("")) 
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else 
            {
                mostrarVenConTipDoc(2);
            }
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);

    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) 
        {
            if (txtDesCorTipDoc.getText().equals("")) 
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else 
            txtDesCorTipDoc.setText(strDesCorTipDoc);

    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        txtCodBodOri.setText("");
        txtNomBodOri.setText("");
        txtCodBodDes.setText("");
        txtNomBodDes.setText("");
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                dispose();
            }
        } 
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodOriActionPerformed
        txtCodBodOri.transferFocus();
    }//GEN-LAST:event_txtCodBodOriActionPerformed

    private void txtCodBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusGained
        strCodBodOri = txtCodBodOri.getText();
        txtCodBodOri.selectAll();
    }//GEN-LAST:event_txtCodBodOriFocusGained

    private void txtCodBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOriFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else
        {
            if (!txtCodBodOri.getText().equalsIgnoreCase(strCodBodOri)) 
            {
                if (txtCodBodOri.getText().equals("")) 
                {
                    txtCodBodOri.setText("");
                    txtNomBodOri.setText("");
                }
                else 
                {
                    mostrarVenConBodOri(1);
                }
            } 
            else
                txtCodBodOri.setText(strCodBodOri);
        }      
    }//GEN-LAST:event_txtCodBodOriFocusLost

    private void txtNomBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodOriActionPerformed
        txtNomBodOri.transferFocus();
    }//GEN-LAST:event_txtNomBodOriActionPerformed

    private void txtNomBodOriFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusGained
        strNomBodOri = txtNomBodOri.getText();
        txtNomBodOri.selectAll();
    }//GEN-LAST:event_txtNomBodOriFocusGained

    private void txtNomBodOriFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOriFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else
        {
            if (!txtNomBodOri.getText().equalsIgnoreCase(strNomBodOri)) 
            {
                if (txtNomBodOri.getText().equals("")) 
                {
                    txtCodBodOri.setText("");
                    txtNomBodOri.setText("");
                } 
                else 
                {
                    mostrarVenConBodOri(2);
                }
            }
            else
                txtNomBodOri.setText(strNomBodOri);
        } 
           
    }//GEN-LAST:event_txtNomBodOriFocusLost

    private void butBodOriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodOriActionPerformed
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
            mostrarVenConBodOri(0);
        }    
    }//GEN-LAST:event_butBodOriActionPerformed

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        txtCodBodDes.transferFocus();
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
        strCodBodDes = txtCodBodDes.getText();
        txtCodBodDes.selectAll();
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
           if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)) {
            if (txtCodBodDes.getText().equals("")) {
                txtCodBodDes.setText("");
                txtNomBodDes.setText("");
            } else {
                mostrarVenConBodDes(1);
            }
        } else
            txtCodBodDes.setText(strCodBodDes);
        }   
        
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
        txtNomBodDes.transferFocus();
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
        strNomBodDes = txtNomBodDes.getText();
        txtNomBodDes.selectAll();
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else{
            if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)) {
                if (txtNomBodDes.getText().equals("")) {
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } else {
                    mostrarVenConBodDes(2);
                }
            }
            else
                txtNomBodDes.setText(strNomBodDes);
        }             
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
        }
        else {
            mostrarVenConBodDes(0);
        }
    }//GEN-LAST:event_butBodDesActionPerformed

    private void cargarItems() 
    {
        bdePesTotTrs = new BigDecimal("0");
        if (hayDatosEnTablaDat()) 
            limpiarTablaDat();
        txtPesTot.setText("0");
        intNumRegSel = 0;
        try 
        {
            nd_pes = Double.parseDouble(txtPesDis.getText().trim());
        } 
        catch (NumberFormatException e)  
        {
            nd_pes = 0;
            txtPesDis.setText("0"); 
            mostrarMsgInf("El peso disponible debe ser un número válido.");
            return; 
        }
        if (butCarItm.getText().equals("Cargar items")) 
        {
////            blnIsMostrarItemsNoDisponibles = mostrarMsgCon( "¿Mostrar los items donde no hay cantidad disponible suficiente?" ) == 0;
            blnIsMostrarItemsNoDisponibles = true;
            blnCon = true;
            if (objThrGUI == null || !objThrGUI.isAlive()) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } 
        else 
            blnCon = false;
    }
    
    private boolean blnIsMostrarItemsNoDisponibles=false; 
 
    private boolean blnMostrar=true;
    
    private void butCarItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCarItmActionPerformed

        
        
                cargarItems();
 
    }//GEN-LAST:event_butCarItmActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void optPorVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPorVarActionPerformed
        if(optPorVar.isSelected())
        {
            optItmApl.setSelected(false);
            optItmSelUsr.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else
        {
            optPorVar.setSelected(true);
        }
    }//GEN-LAST:event_optPorVarActionPerformed

    private void optItmAplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmAplActionPerformed
        if(optItmApl.isSelected())
        {
            optPorVar.setSelected(false);
            optItmSelUsr.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else
        {
            optItmApl.setSelected(true);
        }
    }//GEN-LAST:event_optItmAplActionPerformed

    private void optItmSelUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optItmSelUsrActionPerformed
        if(optItmSelUsr.isSelected())
        {
            optPorVar.setSelected(false);
            optItmApl.setSelected(false);
            objTblModRep.removeAllRows();
            objTblModRepTrs.removeAllRows();
        }
        else 
        {
            optItmSelUsr.setSelected(true);
        }
    }//GEN-LAST:event_optItmSelUsrActionPerformed

    private void txtPesTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesTotFocusGained
       strPesTotKgrSolTra = txtPesTot.getText();
    }//GEN-LAST:event_txtPesTotFocusGained

    private boolean ejecucionWebService () 
    {
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLocEx;        
        boolean blnRes=false;
        blnRes = objWeb.ejecucionWebServReposFechaRepo(intNumDoc ,dtpFecDoc.getText());
////        if (blnRes) 
////            blnRes=true;
////        else
////             blnRes=false;
        return blnRes;
    }
    
    private void butLlamadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLlamadaActionPerformed
        boolean blnResWeb=true;  
		
										
        intNumDoc=Integer.parseInt(txtCodDoc.getText());
        blnResWeb=ejecucionWebService(); 


        if (!blnResWeb)
        {
            String strTit="Mensaje del sistema Zafiro";
            JOptionPane.showMessageDialog(this, "<html> La reposicion no se PROCESO.... <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                    
            updateCabecera(1);
            obtenerEstadoDoc();

        }
        else
        {
            String strTit="Mensaje del sistema Zafiro";
            JOptionPane.showMessageDialog(this, "<html> La reposición se realizo de forma EXITOSA <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                                                                    
            updateCabecera(0);
            obtenerEstadoDoc();
        }           
        
        
////        //consultarReservadosDesdeWeb();
////        intNumDoc=Integer.parseInt(txtCodDoc.getText());
////        
//// blnResWeb=ejecucionWebService(); 
////                if (blnResWeb)
////                    blnRes=true;
////                else
////                {
////                    blnRes=false;
////                    blnResWeb=false;                                                    
////                }
				 
////                if (!blnResWeb)
////                {
////                    String strTit="Mensaje del sistema Zafiro";
////                    JOptionPane.showMessageDialog(this, "<html> La reposicion no se PROCESO.... <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                    
////                    updateCabecera(1);
////                    obtenerEstadoDoc();
////                    
////                }
////                else
////                {
////                    String strTit="Mensaje del sistema Zafiro";
////                    JOptionPane.showMessageDialog(this, "<html> La reposición se realizo de forma EXITOSA <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                                                                    
////                    updateCabecera(0);
////                    obtenerEstadoDoc();
////                }   
////            }

    }//GEN-LAST:event_butLlamadaActionPerformed

    private void rbMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMinActionPerformed
        cargarItems();
    }//GEN-LAST:event_rbMinActionPerformed

    private void txtCanTotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCanTotFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCanTotFocusGained

    private void rbMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMaxActionPerformed
       cargarItems();
       
    }//GEN-LAST:event_rbMaxActionPerformed

    private void rbMedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMedActionPerformed
        cargarItems();
    }//GEN-LAST:event_rbMedActionPerformed

    private void btn_quitarAbcCapCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarAbcCapCarActionPerformed
        try
        {
            int intFilSel = tblDatCapCar.getSelectedRow();

            if (intFilSel < 0)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ningún registro.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            int intTip = 1;
            String strCatNiv = objTblModRepCapCar.getValueAt(intFilSel,INT_TBL_DAT_CAP_CAR_CAT_NIV).toString();
            String strNiv = objTblModRepCapCar.getValueAt(intFilSel,INT_TBL_DAT_CAP_CAR_NIV).toString();


            // Eliminar físicamente de SQL Server
            if (anularCfgCapCar(intTip, strNiv, strCatNiv))
            {
                // Recargar nuevamente la tabla
                if (cmb_abcCat.getSelectedItem() != null)
                {
                    String strItmAbcCat =cmb_abcCat.getSelectedItem().toString().trim();
                    cargarCfgCapCar(1, strItmAbcCat);
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_btn_quitarAbcCapCarActionPerformed

private boolean anularCfgCapCar(int intTip, String strNiv, String strCatNiv)
{
    boolean blnRes = true;

    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;

    try
    {
        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(), 
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";
            strSQL += " UPDATE cnf ";
            strSQL += " SET cnf.st_reg = 'I' ,  cnf.fe_ultmod =CURRENT_TIMESTAMP, cnf.tx_usrmod ='"+ objParSis.getNombreUsuario() + "' \n";            
            strSQL += " FROM tbm_cfgcapcar AS cnf \n";
            strSQL += "     INNER JOIN tbm_nivpar AS niv ON cnf.co_nivpar = niv.co_nivpar \n"; 
            strSQL += "     INNER JOIN tbm_catpar AS cat ON cnf.co_tipniv = cat.co_catpar \n"; 
            strSQL += " WHERE   cnf.st_reg = 'A'   AND niv.tx_nivpar ='"+ strNiv +"'  AND cat.tx_catpar ='"+strCatNiv+"'";            

            System.out.println("eliminarCfgCapCar: " + strSQL);

            int intFilAfe = stmInt.executeUpdate(strSQL);

            if (intFilAfe <= 0)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el registro a eliminar.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                blnRes = false;
            }

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;
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
    finally
    {
        try
        {
            if (stmInt != null)
                stmInt.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}    

private boolean anularCfgTipEmi(int intTip, String strNiv, String strCatNiv)
{
    boolean blnRes = true;

    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;

    try
    {
        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";
            strSQL += " UPDATE cnf  ";
            strSQL += " SET cnf.st_reg = 'I' ,  cnf.fe_ultmod =CURRENT_TIMESTAMP, cnf.tx_usrmod ='"+ objParSis.getNombreUsuario() + "' \n";            
            strSQL += " FROM tbm_cfgTipRep AS cnf \n";
            strSQL += "     INNER JOIN tbm_nivpar AS niv ON cnf.co_nivpar = niv.co_nivpar \n"; 
            strSQL += "     INNER JOIN tbm_catpar AS cat ON cnf.co_tipniv = cat.co_catpar \n"; 
            strSQL += " WHERE   cnf.st_reg = 'A'   AND niv.tx_nivpar ='"+ strNiv +"'  AND cat.tx_catpar ='"+strCatNiv+"' and cnf.co_tiprep="+intTip+"";            

            System.out.println("eliminarCfgCapCar: " + strSQL);

            int intFilAfe = stmInt.executeUpdate(strSQL);

            if (intFilAfe <= 0)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró el registro a eliminar.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                blnRes = false;
            }

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;
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
    finally
    {
        try
        {
            if (stmInt != null)
                stmInt.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}    

private boolean guardarCfgCapCar(int intTip, String strCat, String strAbc)
{
    boolean blnRes = true;

    java.sql.Connection conInt = null;
    java.sql.PreparedStatement pst = null;
    java.sql.ResultSet rstInt = null;

    int intCodCat = 0;
    int intCodNiv = 0;

    try
    {
        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            /*
             * 1. Obtener co_catpar y co_nivpar
             */
            strSQL = "";
            strSQL += " SELECT ";
            strSQL += "     cat.co_catpar, ";
            strSQL += "     niv.co_nivpar ";
            strSQL += " FROM Dynamics2021..tbm_catpar AS cat ";
            strSQL += " CROSS JOIN Dynamics2021..tbm_nivpar AS niv ";
            strSQL += " WHERE cat.st_reg='A' ";
            strSQL += " AND niv.st_reg='A' ";
            strSQL += " AND TRIM(cat.tx_catpar)=? ";
            strSQL += " AND TRIM(niv.tx_nivpar)=? ";

            pst = conInt.prepareStatement(strSQL);
            pst.setString(1, strCat);
            pst.setString(2, strAbc);

            rstInt = pst.executeQuery();

            if (rstInt.next())
            {
                intCodCat = rstInt.getInt("co_catpar");
                intCodNiv = rstInt.getInt("co_nivpar");
            }
            else
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró la categoría o el nivel seleccionado.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return false;
            }

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 2. Revisar si YA EXISTE ACTIVO
             */
            strSQL = "";
            strSQL += " SELECT 1 ";
            strSQL += " FROM Dynamics2021..tbm_cfgcapcar ";
            strSQL += " WHERE ne_tip=? ";
            strSQL += " AND co_tipniv=? ";
            strSQL += " AND co_nivpar=? ";
            strSQL += " AND st_reg='A' ";

            pst = conInt.prepareStatement(strSQL);

            pst.setInt(1, intTip);
            pst.setInt(2, intCodCat);
            pst.setInt(3, intCodNiv);

            rstInt = pst.executeQuery();

            if (rstInt.next())
            {
                rstInt.close();
                rstInt = null;

                pst.close();
                pst = null;

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "El item seleccionado ya se encuentra ingresado.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return false;
            }

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 3. Revisar si existe INACTIVO
             */
            strSQL = "";
            strSQL += " SELECT 1 ";
            strSQL += " FROM Dynamics2021..tbm_cfgcapcar ";
            strSQL += " WHERE ne_tip=? ";
            strSQL += " AND co_tipniv=? ";
            strSQL += " AND co_nivpar=? ";
            strSQL += " AND st_reg='I' ";

            pst = conInt.prepareStatement(strSQL);

            pst.setInt(1, intTip);
            pst.setInt(2, intCodCat);
            pst.setInt(3, intCodNiv);

            rstInt = pst.executeQuery();

            boolean blnExisteInactivo = rstInt.next();

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 4. Si existe INACTIVO -> REACTIVAR
             */
            if (blnExisteInactivo)
            {
                strSQL = "";
                strSQL += " UPDATE Dynamics2021..tbm_cfgcapcar ";
                strSQL += " SET st_reg='A', ";
                strSQL += "     fe_ultmod=GETDATE(), ";
                strSQL += "     tx_usrmod=? ";
                strSQL += " WHERE ne_tip=? ";
                strSQL += " AND co_tipniv=? ";
                strSQL += " AND co_nivpar=? ";
                strSQL += " AND st_reg='I' ";

                pst = conInt.prepareStatement(strSQL);

                pst.setString(1, objParSis.getNombreUsuario());
                pst.setInt(2, intTip);
                pst.setInt(3, intCodCat);
                pst.setInt(4, intCodNiv);

                pst.executeUpdate();

                pst.close();
                pst = null;
            }

            /*
             * 5. Si NO EXISTE -> INSERTAR
             */
            else
            {
                strSQL = "";
                strSQL += " INSERT INTO Dynamics2021..tbm_cfgcapcar ";
                strSQL += " ( ";
                strSQL += "     ne_tip, ";
                strSQL += "     co_tipniv, ";
                strSQL += "     co_nivpar, ";
                strSQL += "     st_reg, ";
                strSQL += "     fe_ing, ";
                strSQL += "     tx_usring ";
                strSQL += " ) ";
                strSQL += " VALUES ";
                strSQL += " (?,?,?, 'A', GETDATE(), ?) ";

                pst = conInt.prepareStatement(strSQL);

                pst.setInt(1, intTip);
                pst.setInt(2, intCodCat);
                pst.setInt(3, intCodNiv);
                pst.setString(4, objParSis.getNombreUsuario());

                pst.executeUpdate();

                pst.close();
                pst = null;
            }

            conInt.close();
            conInt = null;
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
    finally
    {
        try
        {
            if (rstInt != null)
                rstInt.close();

            if (pst != null)
                pst.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}

private boolean guardarCfgTipEnv(int intTip, String strCat, String strAbc)
{
    boolean blnRes = true;

    java.sql.Connection conInt = null;
    java.sql.PreparedStatement pst = null;
    java.sql.ResultSet rstInt = null;

    int intCodCat = 0;
    int intCodNiv = 0;

    try
    {
        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            /*
             * 1. Obtener co_catpar y co_nivpar
             */
            strSQL = "";
            strSQL += " SELECT ";
            strSQL += "     cat.co_catpar, ";
            strSQL += "     niv.co_nivpar ";
            strSQL += " FROM Dynamics2021..tbm_catpar AS cat ";
            strSQL += " CROSS JOIN Dynamics2021..tbm_nivpar AS niv ";
            strSQL += " WHERE cat.st_reg='A' ";
            strSQL += " AND niv.st_reg='A' ";
            strSQL += " AND TRIM(cat.tx_catpar)=? ";
            strSQL += " AND TRIM(niv.tx_nivpar)=? ";

            pst = conInt.prepareStatement(strSQL);
            pst.setString(1, strCat);
            pst.setString(2, strAbc);

            rstInt = pst.executeQuery();

            if (rstInt.next())
            {
                intCodCat = rstInt.getInt("co_catpar");
                intCodNiv = rstInt.getInt("co_nivpar");
            }
            else
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontró la categoría o el nivel seleccionado.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return false;
            }

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 2. Revisar si YA EXISTE ACTIVO
             */
            strSQL = "";
            strSQL += " SELECT 1 ";
            strSQL += " FROM Dynamics2021..tbm_cfgTipRep ";
            strSQL += " WHERE co_tipRep=? ";
            strSQL += " AND co_tipniv=? ";
            strSQL += " AND co_nivpar=? ";
            strSQL += " AND st_reg='A' ";

            pst = conInt.prepareStatement(strSQL);

            pst.setInt(1, intTip);
            pst.setInt(2, intCodCat);
            pst.setInt(3, intCodNiv);

            rstInt = pst.executeQuery();

            if (rstInt.next())
            {
                rstInt.close();
                rstInt = null;

                pst.close();
                pst = null;

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "El item seleccionado ya se encuentra ingresado.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );

                return false;
            }

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 3. Revisar si existe INACTIVO
             */
            strSQL = "";
            strSQL += " SELECT 1 ";
            strSQL += " FROM Dynamics2021..tbm_cfgTipRep ";
            strSQL += " WHERE co_tipRep=? ";
            strSQL += " AND co_tipniv=? ";
            strSQL += " AND co_nivpar=? ";
            strSQL += " AND st_reg='I' ";

            pst = conInt.prepareStatement(strSQL);

            pst.setInt(1, intTip);
            pst.setInt(2, intCodCat);
            pst.setInt(3, intCodNiv);

            rstInt = pst.executeQuery();

            boolean blnExisteInactivo = rstInt.next();

            rstInt.close();
            rstInt = null;

            pst.close();
            pst = null;


            /*
             * 4. Si existe INACTIVO -> REACTIVAR
             */
            if (blnExisteInactivo)
            {
                strSQL = "";
                strSQL += " UPDATE Dynamics2021..tbm_cfgTipRep ";
                strSQL += " SET st_reg='A', ";
                strSQL += "     fe_ultmod=GETDATE(), ";
                strSQL += "     tx_usrmod=? ";
                strSQL += " WHERE co_tipRep=? ";
                strSQL += " AND co_tipniv=? ";
                strSQL += " AND co_nivpar=? ";
                strSQL += " AND st_reg='I' ";

                pst = conInt.prepareStatement(strSQL);

                pst.setString(1, objParSis.getNombreUsuario());
                pst.setInt(2, intTip);
                pst.setInt(3, intCodCat);
                pst.setInt(4, intCodNiv);

                pst.executeUpdate();

                pst.close();
                pst = null;
            }

            /*
             * 5. Si NO EXISTE -> INSERTAR
             */
            else
            {
                strSQL = "";
                strSQL += " INSERT INTO Dynamics2021..tbm_cfgTipRep ";
                strSQL += " ( ";
                strSQL += "     co_tipRep, ";
                strSQL += "     co_tipniv, ";
                strSQL += "     co_nivpar, ";
                strSQL += "     st_reg, ";
                strSQL += "     fe_ing, ";
                strSQL += "     tx_usring ";
                strSQL += " ) ";
                strSQL += " VALUES ";
                strSQL += " (?,?,?, 'A', GETDATE(), ?) ";

                pst = conInt.prepareStatement(strSQL);

                pst.setInt(1, intTip);
                pst.setInt(2, intCodCat);
                pst.setInt(3, intCodNiv);
                pst.setString(4, objParSis.getNombreUsuario());

                pst.executeUpdate();

                pst.close();
                pst = null;
            }

            conInt.close();
            conInt = null;
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
    finally
    {
        try
        {
            if (rstInt != null)
                rstInt.close();

            if (pst != null)
                pst.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}


    private void btn_anadirAbcCapCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anadirAbcCapCarActionPerformed
        try
        {
            // Validar ABC seleccionado
            if (cmb_abcCapCar.getSelectedItem() == null)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ningún registro.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Validar categoría
            if (cmb_abcCat.getSelectedItem() == null)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ninguna categoría.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            String strAbcSel =
                    cmb_abcCapCar.getSelectedItem().toString().trim();

            String strCat =
                    cmb_abcCat.getSelectedItem().toString().trim();

            // Guardar/reactivar directamente en SQL Server
            if (guardarCfgCapCar(1, strCat, strAbcSel))
            {
                // Recargar la JTable desde SQL Server
                cargarCfgCapCar(1, strCat);
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        
    }//GEN-LAST:event_btn_anadirAbcCapCarActionPerformed

    private void cmb_abcCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_abcCatActionPerformed
        // Si el combo todavía se está llenando, no hacer nada
        if (blnCarCmbAbcCat)
            return;

        if (cmb_abcCat.getSelectedItem() != null)
        {
            String strItmAbcCat = cmb_abcCat.getSelectedItem().toString();

            System.out.println("Categoría seleccionada: " + strItmAbcCat);

            cargarCfgCapCar(1, strItmAbcCat);
        }  
    }//GEN-LAST:event_cmb_abcCatActionPerformed

    private void cmb_abcCapCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_abcCapCarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_abcCapCarActionPerformed
private void estadoChkCargaPar(boolean valid)
{
    cmb_abcCat.setEnabled(valid);
    cmb_abcCapCar.setEnabled(valid);
    btn_anadirAbcCapCar.setEnabled(valid);
    btn_quitarAbcCapCar.setEnabled(valid);
}
    private void chk_conCapCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_conCapCarActionPerformed
        if (chk_conCapCar.isSelected())
        {
            configurarTblDatABCCapCarga();
            cargarComboCatABC();  
            estadoChkCargaPar(true);
            
    //////        cargarComboCatABC();
    //////        configurarTblDatABCCapCarga();
    //////        String strItmAbcCat="";
    //////        if (cmb_abcCat.getSelectedItem() != null)
    //////        {
    //////            strItmAbcCat = String.valueOf(cmb_abcCat.getSelectedItem());
    //////            cargarCfgCapCar(1,strItmAbcCat);
    //////            System.out.println("Categoría seleccionada: " + strItmAbcCat);
    //////        }                             
        }
        else
        {
            estadoChkCargaPar(false);
            // Borrar todos los registros de la tabla
            if (objTblModRepCapCar != null)
            {
                objTblModRepCapCar.removeAllRows();
            }

            // Dejar sin selección el combo de categoría ABC
            if (cmb_abcCat.getItemCount() > 0)
            {
                cmb_abcCat.setSelectedIndex(-1);
            }

            // Dejar sin selección el combo ABC capacidad de carga
            if (cmb_abcCapCar.getItemCount() > 0)
            {
                cmb_abcCapCar.setSelectedIndex(-1);
            }
        }
    }//GEN-LAST:event_chk_conCapCarActionPerformed

    private void chk_capCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_capCarActionPerformed
        // TODO add your handling code here:
        if (chk_capCar.isSelected())
        {
            chk_conCapCar.setSelected(true);
            configurarTblDatABCCapCarga();
            cargarComboCatABC();
            cmb_abcCat.setSelectedItem("Tuval");
            cargarCfgCapCar(1, "Tuval");
            
        }
        else
        {
            chk_conCapCar.setSelected(false);
            // Borrar todos los registros de la tabla
            if (objTblModRepCapCar != null)
            {
                objTblModRepCapCar.removeAllRows();
            }

            // Dejar sin selección el combo de categoría ABC
            if (cmb_abcCat.getItemCount() > 0)
            {
                cmb_abcCat.setSelectedIndex(-1);
            }

            // Dejar sin selección el combo ABC capacidad de carga
            if (cmb_abcCapCar.getItemCount() > 0)
            {
                cmb_abcCapCar.setSelectedIndex(-1);
            }            
            
        }
            
            
    }//GEN-LAST:event_chk_capCarActionPerformed

    private void btn_quitarAbcCapCar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarAbcCapCar1ActionPerformed
        try
        {
            int intFilSel = tblDatTipEmi.getSelectedRow();

            if (intFilSel < 0)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ningún registro.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            String strCatNiv = objTblModRepTipEmi.getValueAt(intFilSel,INT_TBL_DAT_TIP_EMI_CAT_NIV).toString();
            String strNiv = objTblModRepTipEmi.getValueAt(intFilSel,INT_TBL_DAT_TIP_EMI_NIV).toString();

            int ne_tipEmi=0;
            if (cmb_tipEmi.getSelectedItem().equals("Maximo"))
                ne_tipEmi=1;
            if (cmb_tipEmi.getSelectedItem().equals("Promedio"))
                ne_tipEmi=2;            
            if (cmb_tipEmi.getSelectedItem().equals("Minimo"))
                ne_tipEmi=3;  
            
            // Eliminar físicamente de SQL Server
            if (anularCfgTipEmi(ne_tipEmi, strNiv, strCatNiv))
            {
                // Recargar nuevamente la tabla
                if (cmb_abcCatTipEmi.getSelectedItem() != null)
                {
                    String strItmAbcCatTipEmi =cmb_abcCatTipEmi.getSelectedItem().toString().trim();
                    cargarCfgTipEnv(ne_tipEmi, strItmAbcCatTipEmi);
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_btn_quitarAbcCapCar1ActionPerformed

    private void cmb_abcCatTipEmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_abcCatTipEmiActionPerformed
        if (blnCarCmbAbcCatTipEmi)
            return;

        if ((cmb_abcCatTipEmi.getSelectedItem() != null) && (cmb_tipEmi.getSelectedItem() != null))
        {
            String strItmAbcCatTipEmi = cmb_abcCatTipEmi.getSelectedItem().toString();
            int ne_tipEmi=0;

            System.out.println("Categoría seleccionada: " + strItmAbcCatTipEmi);
            if (cmb_tipEmi.getSelectedItem().equals("Maximo"))
                ne_tipEmi=1;
            if (cmb_tipEmi.getSelectedItem().equals("Promedio"))
                ne_tipEmi=2;            
            if (cmb_tipEmi.getSelectedItem().equals("Minimo"))
                ne_tipEmi=3;                        
                        
            
                
            cargarCfgTipEnv(ne_tipEmi, strItmAbcCatTipEmi);
        } 
    }//GEN-LAST:event_cmb_abcCatTipEmiActionPerformed

    private void cmb_abcTipEmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_abcTipEmiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_abcTipEmiActionPerformed

    private void cmb_tipEmiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_tipEmiActionPerformed
        if ((cmb_abcCatTipEmi.getSelectedItem() != null) && (cmb_tipEmi.getSelectedItem() != null))
        {
            String strItmAbcCatTipEmi = cmb_abcCatTipEmi.getSelectedItem().toString();
            int ne_tipEmi=0;

            System.out.println("Categoría seleccionada: " + strItmAbcCatTipEmi);
            if (cmb_tipEmi.getSelectedItem().equals("Maximo"))
                ne_tipEmi=1;
            if (cmb_tipEmi.getSelectedItem().equals("Promedio"))
                ne_tipEmi=2;            
            if (cmb_tipEmi.getSelectedItem().equals("Minimo"))
                ne_tipEmi=3;                         
                         
            
                
            cargarCfgTipEnv(ne_tipEmi, strItmAbcCatTipEmi);
        }
    }//GEN-LAST:event_cmb_tipEmiActionPerformed

    private void btn_agrAbcTipEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agrAbcTipEmActionPerformed
        try
        {
            // Validar ABC seleccionado
            if (cmb_abcTipEmi.getSelectedItem() == null)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ningún registro.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Validar categoría
            if (cmb_abcCatTipEmi.getSelectedItem() == null)
            {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se ha seleccionado ninguna categoría.",
                        "Mensaje",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            String strAbcSel =
                    cmb_abcTipEmi.getSelectedItem().toString().trim();

            String strCatTipEmi =
                    cmb_abcCatTipEmi.getSelectedItem().toString().trim();
            int ne_tipEmi=0;
            if (cmb_tipEmi.getSelectedItem().equals("Maximo"))
                ne_tipEmi=1;
            if (cmb_tipEmi.getSelectedItem().equals("Promedio"))
                ne_tipEmi=2;            
            if (cmb_tipEmi.getSelectedItem().equals("Minimo"))
                ne_tipEmi=3;              

            // Guardar/reactivar directamente en SQL Server
            if (guardarCfgTipEnv(ne_tipEmi, strCatTipEmi, strAbcSel))
            {
                // Recargar la JTable desde SQL Server
                cargarCfgTipEnv(ne_tipEmi, strCatTipEmi);
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        
        
    }//GEN-LAST:event_btn_agrAbcTipEmActionPerformed

private boolean updateCabecera(int tipo){
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
                    strSqlCab+=" UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET st_doc ='E',fe_ing = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                else//CUANDO ESTA TODO BIEN
                    strSqlCab+=" UPDATE "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab SET st_doc ='A',fe_ing = CAST(CURRENT_TIMESTAMP AS DATE) , tx_usring = '"+ objParSis.getNombreUsuario() +"' \n";
                strSqlCab+=" WHERE  trc_numtrx = "+intNumDoc +"\n";
                System.out.println("updateCabecera GP" + strSqlCab);
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
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrupo;
    private javax.swing.JButton btn_agrAbcTipEm;
    private javax.swing.JButton btn_anadirAbcCapCar;
    private javax.swing.JButton btn_quitarAbcCapCar;
    private javax.swing.JButton btn_quitarAbcCapCar1;
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butBodOri;
    private javax.swing.JButton butCarItm;
    private javax.swing.JButton butLlamada;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chk_capCar;
    private javax.swing.JCheckBox chk_conCapCar;
    private javax.swing.JComboBox<String> cmb_abcCapCar;
    private javax.swing.JComboBox<String> cmb_abcCat;
    private javax.swing.JComboBox<String> cmb_abcCatTipEmi;
    private javax.swing.JComboBox<String> cmb_abcTipEmi;
    private javax.swing.JComboBox<String> cmb_tipEmi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblBodDes1;
    private javax.swing.JLabel lblBodOri;
    private javax.swing.JLabel lblCantTot;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPesDis;
    private javax.swing.JLabel lblPesTot;
    private javax.swing.JLabel lblPorVar;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optItmApl;
    private javax.swing.JRadioButton optItmSelUsr;
    private javax.swing.JRadioButton optPorVar;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCapCar;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panConf;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panRep;
    private javax.swing.JPanel panTipEmi;
    private javax.swing.JPanel panTraInv;
    private javax.swing.JRadioButton rbMax;
    private javax.swing.JRadioButton rbMed;
    private javax.swing.JRadioButton rbMin;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnDat1;
    private javax.swing.JScrollPane spnDat2;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnTrs;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatCapCar;
    private javax.swing.JTable tblDatTipEmi;
    private javax.swing.JTable tblTrs;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCanTot;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodBodOri;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomBodOri;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtPesDis;
    private javax.swing.JTextField txtPesTot;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }



    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() 
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBodOri.getDocument().addDocumentListener(objDocLis);
        txtNomBodOri.getDocument().addDocumentListener(objDocLis);
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtPesDis.getDocument().addDocumentListener(objDocLis);
        txtPesTot.getDocument().addDocumentListener(objDocLis);
    }

    private class MiToolBar extends ZafToolBar 
    {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }
        
        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterConsultar() 
        {
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
            tblTrs.setEnabled(true);

            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() 
        {
            //System.out.println("afterInsertar");
            //Configurar JFrame de acuerdo al estado del registro.
            blnChnEstImp=true;
            objTooBar.setEstado('w');
//            imprimirOrdDes();
            consultarReg();
            blnHayCam=false;
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_TRS_BUT);
            objTblModRepTrs.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
            tblTrs.setEnabled(true);
            blnChnEstImp=false;
            return true;
        }

        public boolean afterModificar() 
        {
            blnHayCam=false;
            return true;
        }

        public boolean afterVistaPreliminar() 
        {
            return true;
        }

        public boolean anular() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() 
        {
            if(objParSis.getCodigoUsuario()!=1)
            {
                if( ! isBodOri())
                    return false;
            }

            return true;
        }

        public boolean beforeEliminar() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya esta ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeInsertar() 
        {
            boolean blnRes=false;
            if (isCamVal())
                if(isItmRep())
                    blnRes=true;
            return blnRes;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean cancelar() 
        {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }
                
        public void clickInicio() {
            try{
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }

        public void clickAnterior() {
            try{
                if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }            
        }      
        
        public void clickSiguiente() {
            try{
                 if(arlDatConCnf.size()>0){
                    if(intIndiceConCnf < arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }     
        }

        public void clickFin() {
            try{
                 if(arlDatConCnf.size()>0){ 
                    if(intIndiceConCnf<arlDatConCnf.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceConCnf=arlDatConCnf.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceConCnf=arlDatConCnf.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }                
        }        

        public void clickAceptar() {
            
        }

        public void clickAnular() {
            
        }

        public void clickCancelar() {
            
        }

        public void clickConsultar()
        {
            mostrarTipDocPre();
            configurarVenConBodOri();
            configurarVenConBodDes();
            if( ! objTooBar.isOpcionEnabled(2337))
            {
                butCarItm.setEnabled(true);
                optPorVar.setEnabled(false);
                optItmApl.setEnabled(false);
                optItmSelUsr.setEnabled(false);
            }
        }
        
        public void clickEliminar() {
            
        }
                
        public void clickImprimir() {
            
        }

        public void clickInsertar() 
        {
            try
            {
                //System.out.println("clickInsertar");
                if (blnHayCam)
                {
                    isRegPro();
                }
                limpiarFrm();
//        if(!isVersionActualizada()){
//                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
//                dispose();
//        } 
////             
                estadoChkCargaPar(false);
                        
                txtCodDoc.setEditable(false);
                txtPesTot.setEditable(false);
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                optPorVar.setEnabled(false);
                optItmApl.setEnabled(false);
                //datFecAux=null;
                mostrarTipDocPre();
                configurarVenConBodOri();
                configurarVenConBodDes();
                
                objTblModRep.setModoOperacion(objTblModRep.INT_TBL_INS);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                blnHayCam=false;
                butCarItm.setEnabled(true);
                ObtenerMax();
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {
            inactivarCampos();
        }

        public void clickVisPreliminar() {
            
        }

        public boolean consultar() {
            consultarReg();
            
            return true;
        }

        public boolean eliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean insertar() {
            boolean blnResWeb =false;
            String strTit="Mensaje del sistema Zafiro";
            int cod_loc =-1;
////            boolean blnRes =false;
            if (!insertarReg())
                return false;
            else
            {
                blnResWeb=ejecucionWebService();  
////                if (blnResWeb)
////                    blnRes=true;
////                else
////                {
////                    blnRes=false;
////                    blnResWeb=false;                                                    
////                }
                if (!blnResWeb)
                { 
                    JOptionPane.showMessageDialog(this, "<html> La reposicion no se PROCESO.... <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                    
                    updateCabecera(1);
                    obtenerEstadoDoc();
                }
                else
                {
                    //JOptionPane.showMessageDialog(this, "<html> La reposición se realizo de forma EXITOSA <BR><BR>", strTit, JOptionPane.INFORMATION_MESSAGE);                                                                                                    
                    updateCabecera(0);
                    obtenerEstadoDoc();

                    
                    if(!txtCodBodDes.getText().equals("IMPORT"))
                    {
                        obtenerListaFinal();
                        htmlGlo.setLength(0); // Esto deja el StringBuilder en blanco
                        obtenerListaDiferencias();                         
                        cod_loc=obtenerCodigoLocalBodDestino();
                        String mensajesSujeto ="ITEMS FALTANTES DE LA REPOSICION : " +intNumDoc  +"  REALIZADA DESDE LA BODEGA : " +txtCodBodOri.getText() + " HACIA LA BODEGA " + txtCodBodDes.getText() + " POR EL USUARIO:  " + objParSis.getNombreUsuario()+ ""  ;
                        if(objRepCorEleGP.enviarNotificacionCorreoElectronicoConAsunto(1, cod_loc, 36, htmlGlo, mensajesSujeto  ))
                        {
                            
                                
                        }                        
                    }

                    
                    
                }   
            }
            return true;
        }

        public boolean modificar() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }
        
    }//Fin MiToolBar
    
    private boolean historialRep(java.sql.Connection conExt)
    {
        boolean blnRes=false;
        java.sql.Statement stmLocInt;
        java.sql.ResultSet rstLocInt;
        java.sql.Connection conLocInt;
        String strSqlCab="";
        try
        {
            //conExt=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conExt!=null)
            {
                stmLocInt = conExt.createStatement();
                if(insertarDetalleCabRep(conExt))
                    blnRes=true;
                else
                    blnRes=false;
            }            
        }
        catch (Exception e)
        {
            blnRes=false;
            //objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
        
        
    }
    
    
private void ObtenerMax() {
    String sql =
        "SELECT ISNULL(MAX(trc_numtrx), 0) + 1 AS NumeroDocumento "
        + "FROM " + objParSis.getNombreBaseDatosGP()
        + ".dbo.tbmsw_transferencia_cab";

    try (
        java.sql.Connection conLocInt = DriverManager.getConnection(
            objParSis.getStringConexion_Dynamics2021(),
            objParSis.getUsuarioBaseDatos_Dynamics2021(),
            objParSis.getClaveBaseDatos_Dynamics2021()
        );
        java.sql.Statement stmLocInt = conLocInt.createStatement();
        java.sql.ResultSet rstLocInt = stmLocInt.executeQuery(sql)
    ) {
        if (rstLocInt.next()) {
            intNumDoc = rstLocInt.getInt(1);
            txtNumDoc.setText(String.valueOf(intNumDoc));
        }

    } catch (java.sql.SQLException e) {
        objUti.mostrarMsgErr_F1(this, e);
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}
            
            
            
    
private boolean obtenerListaDiferencias() 
{
    boolean blnRes = false;
    try 
    {
        listaDiferencias.clear();
        for (Map<String, Object> ori : listaDatosOri) 
        {
            Map<String, Object> nuevo = new HashMap<>(ori); // Copiar todos los campos del origen

            // Buscar ítem en lista final por "ITEM_GP"
            Map<String, Object> fin = buscarEnListaFin((String) ori.get("ITEM_GP"));

            if (fin != null) 
            {
                // Añadir el campo nuevo con el valor de REP_ENVIADA del destino
                nuevo.put("CANT_ENVIADA", fin.get("REP_ENVIADA"));
                nuevo.put("PESO_ENVIADO", fin.get("PES_TOT"));
            } 
            else 
            {
                // Si no se encuentra, dejar el valor en null o cero
                nuevo.put("CANT_ENVIADA", "0.00");
                nuevo.put("PESO_ENVIADO", "0.00");
                
            }

            listaDiferencias.add(nuevo);
            blnRes = true;
        }
        
        if (blnRes)
             htmlGlo =generarHtmlDesdeLista(listaDiferencias);        

    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }

    return blnRes;
}

private Map<String, Object> buscarEnListaFin(String codigoItem) {
    for (Map<String, Object> item : listaDatosFin) {
        if (codigoItem.equals(item.get("ITEM_GP"))) {
            return item;
        }
    }
    return null;
}

    

	
private int obtenerCodigoLocalBodDestino()
{
        java.sql.Connection conLocIn;
        java.sql.ResultSet rstLocIn;
        java.sql.Statement stmLocIn; 
        String strSQL="";
        String bodega="";
        int co_loc=-1;
        try
        {
            conLocIn=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLocIn!=null)
            {
                stmLocIn=conLocIn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sálo se muestre los documentos asignados al programa.
                if (txtCodBodDes.getText().equals("CUENCA_2"))
                    bodega="CUENCA";
                else
                    bodega= txtCodBodDes.getText();
                
                
                strSQL ="";
                strSQL+="SELECT ISNULL(loc1.co_loc,0) AS co_loc, loc1.co_emp ";
                strSQL+="FROM  Dynamics2021..tbm_loc1 as loc1  ";
                strSQL+="      INNER JOIN Dynamics2021..tbm_loc as loc on (loc1.co_emp = loc.co_emp and loc1.tx_ptoVta = loc.tx_codSer)";
                strSQL+="      INNER JOIN Dynamics2021..tbm_bod as bod on (loc1.co_emp = bod.co_emp and loc.tx_sucAlt =bod.tx_descor)";
                strSQL+="WHERE bod.tx_descor= '"+ bodega +"' AND  bod.st_bodDis ='S'";
                
                rstLocIn = stmLocIn.executeQuery(strSQL);
                if(rstLocIn.next())
                    co_loc =rstLocIn.getInt("co_loc");
                else
                    co_loc =-1;
                    

                stmLocIn.close();
                stmLocIn=null;
                rstLocIn.close();
                rstLocIn=null;
                conLocIn.close();
                conLocIn=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return co_loc;
    }    
private void obtenerEstadoDoc()
{
        java.sql.Connection conLocIn;
        java.sql.ResultSet rstLocIn;
        java.sql.Statement stmLocIn; 
        String strSQL="";
        boolean blnExi =false;
        String strEstado ="";
        try
        {
            conLocIn=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (conLocIn!=null)
            {
                stmLocIn=conLocIn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sálo se muestre los documentos asignados al programa.
                
                strSQL ="";
                strSQL+=" SELECT st_doc FROM  "+objParSis.getNombreBaseDatosGP()+"..tbmsw_transferencia_cab  where trc_numtrx ="+ intNumDoc +" ";
                rstLocIn = stmLocIn.executeQuery(strSQL);
                while(rstLocIn.next())
                {
                    blnExi=true;
                    strEstado =rstLocIn.getString("st_doc");
                    
                }
                if (blnExi)
                {
                    if (strEstado.equals("E"))
                    {
                        lbl_titulo = lblTit.getText() + " - PENDIENTE POR GRABAR EN GP";
                        lblTit.setText(lbl_titulo);
                    }
                }
                stmLocIn.close();
                stmLocIn=null;
                rstLocIn.close();
                rstLocIn=null;
                conLocIn.close();
                conLocIn=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
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
  
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="áDesea guardar los cambios efectuados a áste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la informacián que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
    
private void limpiarTablaDat() {
    try {
        if (objTblModRep == null) {
            return;
        }

        // Si no hay datos, no intentar eliminar.
        if (objTblModRep.getRowCountTrue() <= 0) {
            tblDat.clearSelection();
            ne_canTotSel = 0;
            intNumRegSel = 0;
            txtCanTot.setText("0");
            return;
        }

        // Detener edición si la tabla está editando una celda.
        if (tblDat.isEditing()) {
            tblDat.getCellEditor().cancelCellEditing();
        }

        // Si tienes ordenamiento personalizado, quitarlo temporalmente.
        if (tblDat.getRowSorter() != null) {
            tblDat.setRowSorter(null);
        }

        // Limpiar los datos del vector.
        vecDat.clear();

        // Recargar modelo vacío.
        objTblModRep.setData(vecDat);

        // Limpiar selección.
        tblDat.clearSelection();

        // Reiniciar contadores.
        ne_canTotSel = 0;
        intNumRegSel = 0;
//////        txtCanTot.setText("0");

        // Refrescar tabla.
        objTblModRep.fireTableDataChanged();

        // Volver a configurar el ordenamiento personalizado.
        configurarOrdenamientoPersonalizado();

    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
} 


    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodBodOri.setText("");
            txtNomBodOri.setText("");
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            txtPesDis.setText("");
            txtPesTot.setText("");
            txtCanTot.setText("");
            //objTblModRep.removeAllRows();
            if (hayDatosEnTablaDat()) 
                limpiarTablaDat();
                     
            
            lblPorVar.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la descripcián larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripcián larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacáa.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'E':
                    estado="Eliminado";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
            
        return estado;
    }
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        String strFecDocTmp="";
        int intTipCamFec;
        String strFecAuxTmp="";
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        //Validar ""Bodega Origen"
        if (txtCodBodOri.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega origen </FONT> es obligatorio.<BR>Escriba o seleccione una bodega origen y vuelva a intentarlo.</HTML>");
            txtNomBodOri.requestFocus();
            return false;
        }
        //Validar "Bodega Destino"
        if (txtCodBodDes.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega destino </FONT> es obligatorio.<BR>Escriba o seleccione una bodega destino y vuelva a intentarlo.</HTML>");
            txtNomBodDes.requestFocus();
            return false;
        }

        //Validar Otros Datos
        if(objTooBar.isOpcionEnabled(2337)) 
        {
            //"Numero de Documento."
            if (txtNumDoc.getText().equals("")) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNomBodDes.requestFocus();
                return false;
            }
            //"Peso Disponible"
            if (txtPesDis.getText().equals("")) 
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Peso disponible</FONT> es obligatorio.<BR>Escriba el valor de Peso disponible y vuelva a intentarlo.</HTML>");
                txtPesDis.requestFocus();
                return false;
            }
            //"Items"
            if(( ! optPorVar.isSelected())  &&  ( ! optItmApl.isSelected())  &&  ( ! optItmSelUsr.isSelected())  )
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar alguno de los filtros para generar items a reponer.<BR>Seleccione alguno y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            //<editor-fold defaultstate="collapsed" desc="/* Fecha del documento*/">.
            if (!dtpFecDoc.isFecha())
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
                dtpFecDoc.requestFocus();
                return false;
            }
            else
            {
                intTipCamFec=canChangeDate();
                switch(intTipCamFec)
                {
                    case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                        if(objParSis.getCodigoUsuario()!=1){
                            if(objTooBar.getEstado()=='n'){//insertar
                                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                String strMsj="";
                                strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                                strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                                strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                                switch (mostrarMsgCon(strMsj)){
                                    case 0: //YES_OPTION
                                        //System.out.println("POR YES");
                                        return true;
                                    case 1: //NO_OPTION
                                        //System.out.println("POR NO");
                                        return false;
                                    case 2: //CANCEL_OPTION
                                        //System.out.println("POR CANCEL");
                                        return false;
                                }
                                datFecAux=null;
                            }
                        }
                        break;
                    case 1://no puede cambiarla para nada
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        break;
                    case 2://la fecha puede ser menor o igual a la q se presenta
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        break;
                    case 3://la fecha puede ser mayor o igual a la q se presenta
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            strFecDocTmp="";strFecAuxTmp="";
                            strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                            strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                            if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                datFecAux=null;
                                return false;
                            }
                        }
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
            }
            //</editor-fold>
            
            
            //Tipo de Item
            boolean blnExiSel=false;
            if (rbMax.isSelected())
                blnExiSel=true;
            if (rbMin.isSelected())
                blnExiSel=true;
            if (rbMed.isSelected())                
                blnExiSel=true;
            
            if (!blnExiSel)
            {
                mostrarMsgInf("<HTML>Se debe seleccionar al menos uno de TIPO DE ITEM.<BR>Seleccion al menos uno.<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }
                
            
        }

        return true;
    }
       
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
   private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarRegRep())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                butCarItm.setText("Cargar items");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

   /**
    * Función que carga registros de ítems a Reponer
    * @return 
    */
   private boolean cargarRegRep()
   {
        boolean blnRes=true;
        Connection conLoc1;
        try
        {
            //System.out.println("cargarRegRep");
            conLoc1=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc1!=null)
            {
                objTblModRep.setModoOperacion(objTblModRep.INT_TBL_NO_EDI);
                //  objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI);
                if(isCamVal())
                {
                    butCarItm.setText("Obteniendo datos...");
                    if(consultarDatTbl(conLoc1)){
                        if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)) { 
                            intOpcRedondeoStock=0;
                            intOpcRedondeoMinimo=0;
                        }
                        else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                            intOpcRedondeoStock=2;
                            intOpcRedondeoMinimo=2;
                        }
                        if(optPorVar.isSelected()){
                            if(seleccionarItmRepVar()){
                            }
                        }
                        else if(optItmApl.isSelected()){
                            if(seleccionarItmRepItmSel(13)){
                            }
                        }
                        else if(optItmSelUsr.isSelected()){ //  José Marín M. 15/Jul/2014
                            vecAux=new Vector();
                            vecAux.add(""+INT_TBL_DAT_CAN_REP_SEL);
                            objTblModRep.setColumnasEditables(vecAux);
                            vecAux=null;
                            objTblModRep.setModoOperacion(objTblModRep.INT_TBL_EDI);
                        }
                    }
                }
                butCarItm.setText("Cargar items");
                objTblModRep.initRowsState();
                conLoc1.close();
                conLoc1=null;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    private boolean consultarDatTbl(Connection con)
    {
        boolean blnRes=true;
        int intCanRepEnt=-1;
        float fltCanRepFrc=-1;
        BigDecimal bdeCanRepEntFrc=new BigDecimal("0");
        String strTipUniMed="";
        String strAuxJ=""; 
        String strTipIteGlo ="";
        try
        {
            if(con!=null)
            {
                objTooBar.setMenSis("Obteniendo datos...");
                stm=con.createStatement();
                    strSQL="{call spInv49(?,?,?)}";
                    java.sql.CallableStatement cal;
                    cal = con.prepareCall(strSQL);
                    cal.setString(1,  txtCodBodOri.getText());//Envio de parametro
                    cal.setString(2, txtCodBodDes.getText());//Envio de parametro 
                    cal.registerOutParameter(3, java.sql.Types.NVARCHAR);
                    boolean hasResults = cal.execute();
                    if (hasResults) 
                    {
                        rst = cal.getResultSet();
                        vecDat.clear();
                        objTooBar.setMenSis("Cargando datos...");

                        BigDecimal bdeMin=new BigDecimal(BigInteger.ZERO);
                        BigDecimal bdeMax=new BigDecimal(BigInteger.ZERO);
                        BigDecimal bdeStk=new BigDecimal(BigInteger.ZERO);
                        BigDecimal bdeExc=new BigDecimal(BigInteger.ZERO);
                        BigDecimal bdeDispIn4=new BigDecimal(BigInteger.ZERO);
                        
                        if (rbMax.isSelected())
                            strTipIteGlo="M";
                        if (rbMed.isSelected())
                            strTipIteGlo="ME";
                        if (rbMin.isSelected())
                            strTipIteGlo="MI";                        
                            
                        

                        while(rst.next())
                        {
                            if (blnCon)
                            {
                                blnMostrar=true;
                                vecReg=new Vector();
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                vecReg.add(INT_TBL_DAT_COD_EMP,                 "");
                                vecReg.add(INT_TBL_DAT_CHK,                     null);
                                vecReg.add(INT_TBL_DAT_COD_ITM,                 "" + rst.getString("ITEMNMBR"));
                                vecReg.add(INT_TBL_DAT_COD_ITM_MAE,             "" + rst.getString("ITEMNMBR"));
                                vecReg.add(INT_TBL_DAT_COD_ALT_ITM,             "" + rst.getString("ITEMNMBR"));
                                vecReg.add(INT_TBL_DAT_COD_ALT_DOS,             "" + rst.getString("ITMGEDSC")); 
                                vecReg.add((INT_TBL_DAT_NOM_ITM),               "" + rst.getString("ITEMDESC"));
                                vecReg.add((INT_TBL_DAT_DES_COR_UNI_MED),       "" + rst.getString("UOMSCHDL"));
                                String catItm = rst.getString("catItm");
                                vecReg.add(INT_TBL_DAT_ITM_TIP,(catItm == null || catItm.trim().isEmpty())? "0" : catItm);
                                vecReg.add((INT_TBL_DAT_STK_ACT_BOD_CEN_DIS),   "" + rst.getString("StockOrigen"));
                                bdeDispIn4=new BigDecimal(rst.getObject("dispIn4")==null?"0":(rst.getString("dispIn4").equals("")?"0":rst.getString("dispIn4")));
                                if (bdeDispIn4.compareTo(BigDecimal.ZERO) == 0) 
                                    vecReg.add(INT_TBL_DAT_DIS_IN4, "");
                                 else 
                                    vecReg.add(INT_TBL_DAT_DIS_IN4, bdeDispIn4);
                                vecReg.add((INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP),     "" + rst.getObject("MaximoTransferibleOrg")==null?"0":rst.getString("MaximoTransferibleOrg"));// Lo maximo que se puede transferir
                                vecReg.add((INT_TBL_DAT_CAN_DIS),""+ rst.getObject("StockOrigen")==null?"0":rst.getString("StockOrigen"));  // DIPONIBLE BODEGA DE ORIGEN
                                vecReg.add(INT_TBL_DAT_CAN_MIN_ORI, ""+ rst.getObject("canMinOri")==null?"0":rst.getString("canMinOri")); 
                                vecReg.add(INT_TBL_DAT_CAN_MAX_ORI, ""+ rst.getObject("canMaxOri")==null?"0":rst.getString("canMaxOri"));                                
                                vecReg.add((INT_TBL_DAT_STK_ACT),         "" + rst.getObject("DisDestino")==null?"0":rst.getString("DisDestino")); 
                                vecReg.add(INT_TBL_DAT_CAN_MIN_DES, ""+ rst.getObject("canMinDes")==null?"0":rst.getString("canMinDes"));                                 
                                vecReg.add(INT_TBL_DAT_CAN_MAX_DES, ""+ rst.getObject("canMaxDes")==null?"0":rst.getString("canMaxDes"));    
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MIN, ""+ rst.getObject("repCalMin")==null?"0":rst.getString("repCalMin")); 
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MIN, ""+ rst.getObject("pesMin")==null?"0":rst.getString("pesMin")); 
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MED, ""+ rst.getObject("repCalMed")==null?"0":rst.getString("repCalMed")); 
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MED, ""+ rst.getObject("pesMed")==null?"0":rst.getString("pesMed")); 
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MAX, ""+ rst.getObject("repCalMax")==null?"0":rst.getString("repCalMax")); 
                                vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MAX, ""+ rst.getObject("pesMax")==null?"0":rst.getString("pesMax"));  
                                
                                String strTipItm = rst.getString("catItm");
                                strTipItm = (strTipItm == null) ? "" : strTipItm.trim();

                                String strTipIteGloSeguro = (strTipIteGlo == null) ? "" : strTipIteGlo.trim();

                                if ("M".equals(strTipIteGloSeguro))
                                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL, getStringSeguro(rst, "repCalMax"));
                                else if ("ME".equals(strTipIteGloSeguro))
                                {
                                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL, getStringSeguro(rst, "repCalMed"));
                                }
                                else if ("MI".equals(strTipIteGloSeguro))
                                {
                                    if (strTipItm != null && strTipItm.endsWith("A"))
                                        vecReg.add(INT_TBL_DAT_CAN_REP_CAL, getStringSeguro(rst, "repCalMed"));
                                    else
                                        vecReg.add(INT_TBL_DAT_CAN_REP_CAL, getStringSeguro(rst, "repCalMin"));
                                }
                                else
                                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL, "0");
                                
                                vecReg.add((INT_TBL_DAT_CAN_REP_SEL),     null);
                                vecReg.add((INT_TBL_DAT_CAN_REP_DIS),     null);
                                vecReg.add((INT_TBL_DAT_CAN_COB_PIS),     null);
                                vecReg.add((INT_TBL_DAT_CAN_COB_PRO),     null);
                                vecReg.add((INT_TBL_DAT_CAN_COB_SUC),     null);    
                                vecReg.add((INT_TBL_DAT_PES),             "" + rst.getObject("peso")==null?"0":rst.getString("peso"));
                                vecReg.add((INT_TBL_DAT_PES_TOT_KGR),     "");
                                vecReg.add((INT_TBL_DAT_STK_MIN),         "" + rst.getObject("CantMinimoOrigen")==null?"0":rst.getString("CantMinimoOrigen"));
                                bdeMin=new BigDecimal(rst.getObject("CantMinimoDestino")==null?"0":(rst.getString("CantMinimoDestino").equals("")?"0":rst.getString("CantMinimoDestino")));
                                bdeMax=new BigDecimal(rst.getObject("CantMaximoDestino")==null?"0":(rst.getString("CantMaximoDestino").equals("")?"0":rst.getString("CantMaximoDestino")));
                                bdeStk=new BigDecimal(rst.getObject("stockDestino")==null?"0":(rst.getString("stockDestino").equals("")?"0":rst.getString("stockDestino")));
                                bdeExc=new BigDecimal("0");
                                vecReg.add((INT_TBL_DAT_MIN),  "" + bdeMin);//
                                vecReg.add((INT_TBL_DAT_MAX),  "" + bdeMax);//
                                vecReg.add((INT_TBL_DAT_UNI_MED),""+ rst.getObject("UMDPQTYS")==null?"0":rst.getString("UMDPQTYS"));//  José Marín 
                                vecReg.add(INT_TBL_DAT_TRA, ""+ rst.getObject("TransDest")==null?"0":rst.getString("TransDest")); 
                                vecReg.add(INT_TBL_DAT_FEC_ULT_REP,  rst.getString("ultcosfearr").toString() );
                                if(!blnIsMostrarItemsNoDisponibles )
                                    blnMostrar=false;
                                vecReg.add((INT_TBL_DAT_UBI),       "");                                 
                                if(blnMostrar)
                                    vecDat.add(vecReg);
                            }
                            else
                                break;
                        }
                    }
                        
                //rst=stm.executeQuery(strSQL);

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                vecAux=new Vector();
                vecAux.add("" + INT_TBL_DAT_CAN_REP_SEL);

                if(optItmSelUsr.isSelected()){
                    vecAux.add("" + INT_TBL_DAT_CHK);
                    objTooBar.setMenSis("Listo");
                }

                objTblModRep.setColumnasEditables(vecAux);
                vecAux=null;

                //Asignar vectores al modelo.
                objTblModRep.setData(vecDat);

                tblDat.setModel(objTblModRep);
                vecDat.clear();
                
                if(!chk_capCar.isSelected())
                    seleccionarItemsEnviar(strTipIteGlo);
                else
                    seleccionarItemsEnviarABC();
                
                calcularPesTotRep(); // JM 
                calcularCobertura();
                if(!txtCodBodDes.getText().equals("IMPORT"))
                    obtenerListaOrigen();//CR
                
                //txtCanTot.setText(String.valueOf(ne_canTotSel));
                ne_canTotSel = getNumeroRegistrosChequeados();
                txtCanTot.setText(String.valueOf(ne_canTotSel));
                eliminarItemsNoChequeados();
                
                butCarItm.setText("Cargar items");
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);   blnRes=false; }
        return blnRes;
    }

    private String getStringSeguro(ResultSet rst, String campo) throws SQLException
    {
        Object obj = rst.getObject(campo);

        if (obj == null)
        {
            return "0";
        }

        String valor = rst.getString(campo);

        if (valor == null || valor.trim().equals(""))
        {
            return "0";
        }

        return valor.trim();
    }
    
private boolean eliminarItemsNoChequeados()
{
    boolean blnRes = true;

    try
    {
        Vector vecDatAux = new Vector();
        Vector vecRegAux;

        int intNumFil = objTblModRep.getRowCountTrue();

        System.out.println("Filas antes: " + intNumFil);

        for (int i = 0; i < intNumFil; i++)
        {
            Object objChk = objTblModRep.getValueAt(i, INT_TBL_DAT_CHK);

            // Conservar solamente registros chequeados
            if (Boolean.TRUE.equals(objChk))
            {
                vecRegAux = new Vector();

                for (int j = 0; j < objTblModRep.getColumnCount(); j++)
                {
                    vecRegAux.add(
                            objTblModRep.getValueAt(i, j)
                    );
                }

                vecDatAux.add(vecRegAux);
            }
        }

        System.out.println("Filas que se conservarán: " + vecDatAux.size());

        /*
         * NO USAR removeAllRows()
         * porque ZafTblMod genera ArrayIndexOutOfBoundsException.
         */

        // Reemplazar directamente los datos actuales
        objTblModRep.setData(vecDatAux);

        // Refrescar tabla
        tblDat.setModel(objTblModRep);
        tblDat.clearSelection();
        tblDat.revalidate();
        tblDat.repaint();

        System.out.println(
                "Filas después: " + objTblModRep.getRowCountTrue()
        );

        ne_canTotSel = getNumeroRegistrosChequeados();
        txtCanTot.setText(String.valueOf(ne_canTotSel));
    }
    catch (Exception e)
    {
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes;
}  

    /**
     * Seleccionar los items que se deben enviar 
     * @return 
     */
    private boolean seleccionarItemsEnviar(String strTipIteGlo)
    {
        boolean blnRes=true;
        String item="";
        double  dblDisponible=0.00, dblTransferible=0.00, dblNecesario=0.00, dblPesoPermitido=0.00, dblPeso=0.00, dblPesoItem=0.00;
        try{
            dblPesoPermitido = Double.valueOf( txtPesDis.getText().toString());
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                String strItm=objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP) == null? "": objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP).toString().trim();
                if (strTipIteGlo.equals("MI"))
                {
                    if(!esAbcNoPermitidoTipEmi(strItm))
                    {
                        if(dblPeso<dblPesoPermitido)
                        {
        //////////                    item =objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
        //////////                    if (item.equals("019-000035-I"))
        //////////                        System.out.println("seeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                            dblDisponible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString());
                            dblTransferible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                            dblNecesario = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString());
                            dblPesoItem = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                            if(dblDisponible>0 && dblTransferible>0 && dblNecesario>0){
                                if(dblNecesario<dblTransferible){
                                    dblPeso = dblPeso + (dblNecesario * dblPesoItem);
                                    if(dblPeso<dblPesoPermitido){
                                        objTblModRep.setValueAt(dblNecesario, i, INT_TBL_DAT_CAN_REP_SEL); // Se envia lo necesario por que es menor que lo disponible
                                        objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                        //ne_canTotSel=ne_canTotSel+1;
                                    }
                                }
                                else if(dblNecesario>=dblTransferible && dblTransferible>0){
                                    dblPeso = dblPeso + (dblTransferible * dblPesoItem);
                                    if(dblPeso<dblPesoPermitido){
                                        objTblModRep.setValueAt(dblTransferible, i, INT_TBL_DAT_CAN_REP_SEL);  // Se envia lo que se puede 
                                        objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                        //ne_canTotSel=ne_canTotSel+1;
                                    }
                                }
                            }

                        }
                        else
                            break;                        
                    }
                    
                }
                else
                {
                    if(dblPeso<dblPesoPermitido)
                    {
    //////////                    item =objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
    //////////                    if (item.equals("019-000035-I"))
    //////////                        System.out.println("seeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        dblDisponible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString());
                        dblTransferible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                        dblNecesario = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString());
                        dblPesoItem = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                        if(dblDisponible>0 && dblTransferible>0 && dblNecesario>0){
                            if(dblNecesario<dblTransferible){
                                dblPeso = dblPeso + (dblNecesario * dblPesoItem);
                                if(dblPeso<dblPesoPermitido){
                                    objTblModRep.setValueAt(dblNecesario, i, INT_TBL_DAT_CAN_REP_SEL); // Se envia lo necesario por que es menor que lo disponible
                                    objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                    //ne_canTotSel=ne_canTotSel+1;
                                }
                            }
                            else if(dblNecesario>=dblTransferible && dblTransferible>0){
                                dblPeso = dblPeso + (dblTransferible * dblPesoItem);
                                if(dblPeso<dblPesoPermitido){
                                    objTblModRep.setValueAt(dblTransferible, i, INT_TBL_DAT_CAN_REP_SEL);  // Se envia lo que se puede 
                                    objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                    //ne_canTotSel=ne_canTotSel+1;
                                }
                            }
                        }

                    }
                    else

                        break;                    
                }
                dblDisponible=0.00; dblTransferible=0.00; dblNecesario=0.00;  dblPesoItem=0.00;
            }
            ne_canTotSel = getNumeroRegistrosChequeados();
            txtCanTot.setText(String.valueOf(ne_canTotSel));
        }
        catch(Exception e){  
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);    
        }
        return blnRes;
    }
    
private boolean esAbcPermitidoCapCar(String strAbc)
{
    boolean blnExiste = false;

    try
    {
        if (strAbc == null)
            return false;

        strAbc = strAbc.trim();

        for (int i = 0; i < objTblModRepCapCar.getRowCountTrue(); i++)
        {
            Object objNivel = objTblModRepCapCar.getValueAt(i,INT_TBL_DAT_CAP_CAR_NIV);

            if (objNivel != null)
            {
                String strNivel = objNivel.toString().trim();

                if (strAbc.equals(strNivel))
                {
                    blnExiste = true;
                    break;
                }
            }
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnExiste;
}    

private boolean esAbcNoPermitidoTipEmi(String strAbc)
{
    boolean blnExiste = false;

    try
    {
        if (strAbc == null)
            return false;

        strAbc = strAbc.trim();

        for (int i = 0; i < objTblModRepTipEmi.getRowCountTrue(); i++)
        {
            Object objNivel = objTblModRepTipEmi.getValueAt(i,INT_TBL_DAT_TIP_EMI_NIV);

            if (objNivel != null)
            {
                String strNivel = objNivel.toString().trim();

                if (strAbc.equals(strNivel))
                {
                    blnExiste = true;
                    break;
                }
            }
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnExiste;
}    

    
    private boolean seleccionarItemsEnviarABC()
    {
        boolean blnRes=true;
        String strItm="";
        double  dblDisponible=0.00, dblTransferible=0.00, dblNecesario=0.00, dblPesoPermitido=0.00, dblPeso=0.00, dblPesoItem=0.00;
        try
        {
            dblPesoPermitido = Double.valueOf( txtPesDis.getText().toString());
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                strItm=objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP) == null? "": objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP).toString().trim();
                //if (strItm.equals("AA") || strItm.equals("AB") || strItm.equals("BA")|| strItm.equals("BB"))
                if (esAbcPermitidoCapCar(strItm))
                {
                    if(dblPeso<dblPesoPermitido)
                    {
    //////////                    item =objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
    //////////                    if (item.equals("019-000035-I"))
    //////////                        System.out.println("seeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        dblDisponible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS).toString());
                        dblTransferible = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());
                        dblNecesario = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString());
                        dblPesoItem = Double.valueOf(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                        if(dblDisponible>0 && dblTransferible>0 && dblNecesario>0){
                            if(dblNecesario<dblTransferible){
                                dblPeso = dblPeso + (dblNecesario * dblPesoItem);
                                if(dblPeso<dblPesoPermitido){
                                    objTblModRep.setValueAt(dblNecesario, i, INT_TBL_DAT_CAN_REP_SEL); // Se envia lo necesario por que es menor que lo disponible
                                    objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                    //ne_canTotSel=ne_canTotSel+1;
                                }
                            }
                            else if(dblNecesario>=dblTransferible && dblTransferible>0){
                                dblPeso = dblPeso + (dblTransferible * dblPesoItem);
                                if(dblPeso<dblPesoPermitido){
                                    objTblModRep.setValueAt(dblTransferible, i, INT_TBL_DAT_CAN_REP_SEL);  // Se envia lo que se puede 
                                    objTblModRep.setValueAt(true, i, INT_TBL_DAT_CHK);
                                    //ne_canTotSel=ne_canTotSel+1;
                                }
                            }
                        }

                    }
                    else
                        break;                    
                }
                dblDisponible=0.00; dblTransferible=0.00; dblNecesario=0.00;  dblPesoItem=0.00;
            }
            ne_canTotSel = getNumeroRegistrosChequeados();
            txtCanTot.setText(String.valueOf(ne_canTotSel));
        }
        catch(Exception e){  
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);    
        }
        return blnRes;
    }    
    
    
    
    
    

    private boolean seleccionarItmRepVar()
    {
        boolean blnRes=true;
        BigDecimal bdePesDisUsr=new BigDecimal(txtPesDis.getText());
        BigDecimal bdePesDisSel=new BigDecimal("0");
        BigDecimal bdePorVar=new BigDecimal("1");
        int intNumRegChk=0;
        BigDecimal bdeStkMin=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkAct=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkRep=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkMinCalPor=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePesRegSel=new BigDecimal("0");
        BigDecimal bdeStkDisGrp=new BigDecimal(BigInteger.ZERO);
        //captura los valores del ultimo porcentaje de variacion con el ultimo numero de items q se podian reponer
        BigDecimal bdePorVarConVal=new BigDecimal("0");
        int intNumItemPueRep=0;
        intOpcRedondeoStock++;
        String strCodAltItm="", strSubCodAltItm="", strCodAltDos="";//se lo almacena para luego compararlo y saber si esta entre iniciales 124,125,870,871,873,874 TUBERIAS y asi no redondearlos
        String strUniMed="", strUbiItm="";
        try
        {
            do
            {
                intNumRegChk=0;
                bdePesRegSel=new BigDecimal("0");
                bdePesDisSel=new BigDecimal("0");
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    objTblModRep.setValueAt(null, i, INT_TBL_DAT_CAN_REP_SEL);
                    txtPesTot.setText("");
                }
                objTblModRep.initRowsState();

                if(bdePorVar.compareTo(new BigDecimal("0"))<=0) //disminuye de 1 en 1
                {
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }

                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    bdeStkMin=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN).toString());
                    bdeStkAct=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT).toString());
                    bdeStkDisGrp=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());

                    strCodAltItm=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                    strSubCodAltItm=strCodAltItm.substring(0, 4);
                    
                    strCodAltDos=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString();

                    strUniMed=objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED).toString();

                    strUbiItm=objTblModRep.getValueAt(i, INT_TBL_DAT_UBI)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_UBI).toString();


                    if(bdeStkAct.compareTo(new BigDecimal("0"))<0){
                    }
                    else{
                        if( (strSubCodAltItm.trim().equals("124-")) || (strSubCodAltItm.trim().equals("125-")) || (strSubCodAltItm.trim().equals("870-")) || (strSubCodAltItm.trim().equals("871-")) || (strSubCodAltItm.trim().equals("873-")) || (strSubCodAltItm.trim().equals("874-"))        )
                        {
                            bdeStkMinCalPor=bdeStkMin.multiply(bdePorVar);
                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));

                            if(strUniMed.trim().equals("PZAS"))
                                bdeStkRep=bdeStkRep.divide(new BigDecimal("1"), 0, BigDecimal.ROUND_DOWN);

                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){  
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                              else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.compareTo(new BigDecimal("0"))<0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                        else{
                            if(intOpcRedondeoMinimo==0)
                                bdeStkMinCalPor=bdeStkMin.multiply(bdePorVar);
                            else if(intOpcRedondeoMinimo==1)
                                bdeStkMinCalPor=objUti.redondearBigDecimal(bdeStkMin.multiply(bdePorVar), 0);
                            else if(intOpcRedondeoMinimo==2)
                                bdeStkMinCalPor=new BigDecimal( "" + Math.floor(Double.parseDouble(""+ bdeStkMin.multiply(bdePorVar))));


                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));
                            if(intOpcRedondeoStock==1)
                                bdeStkRep=objUti.redondearBigDecimal(bdeStkRep, 0);//redondear
                            else if(intOpcRedondeoStock==2)
                                bdeStkRep=new BigDecimal("" + Math.floor(Double.parseDouble(""+bdeStkRep))); //redondeando a menos
                            else if(intOpcRedondeoStock==3)
                                bdeStkRep=new BigDecimal("" + Math.ceil(Double.parseDouble(""+bdeStkRep))); //redondeando a mas


                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.compareTo(new BigDecimal("0"))<0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }

                    }
                }
                //lblPorVar.setText("" + bdePorVar);
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;

            if(intNumRegChk>13){
                objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + ".Ninguno se procesó porque excede el número de items de una guía</HTML>");
                bdePorVarConVal=bdePorVar;
                intNumItemPueRep=intNumRegChk;
            }
            else if(intNumRegChk<=0){
                objTooBar.setMenSis("<HTML>El número de items último que se podian procesar es: " + intNumItemPueRep + " con un porcentaje de variación de: " + (bdePorVarConVal).multiply(new BigDecimal("100")) + " % </HTML>");
            }
            else{
                objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");
            }

                
            if(bdePorVar.compareTo(new BigDecimal("0.01"))>0){//disminuye de 1 en 1
                bdePorVar=bdePorVar.subtract(new BigDecimal("0.01"));
            }
            else if(bdePorVar.compareTo(new BigDecimal("0.001"))<0){
                bdePorVar=new BigDecimal("0");
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;
                break;
            }
            else if(bdePorVar.compareTo(new BigDecimal("0.01"))<=0){//cuando el % llega al 1%
                bdePorVar=bdePorVar.subtract(new BigDecimal("0.001"));
            }

            }while(  (intNumRegChk>13) || (bdePesDisSel.compareTo(bdePesDisUsr)>0) );
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesDisSel,objParSis.getDecimalesMostrar()));

            BigDecimal bdeCanRepNeg=new BigDecimal("0");


            for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                bdeCanRepNeg=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                if(bdeCanRepNeg.compareTo(new BigDecimal("0"))<0)
                    objTblModRep.setValueAt((bdeCanRepNeg.multiply(new BigDecimal("-1"))), i, INT_TBL_DAT_CAN_REP_SEL);
            }

            if(intNumRegChk==0){
                for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                    objTblModRep.setValueAt(new BigDecimal("0"), i, INT_TBL_DAT_CAN_REP_SEL);
                }
            }



            if( (intOpcRedondeoStock<3) &&((intNumRegChk>13) || (intNumRegChk<=0))  )
                seleccionarItmRepVar();
            else if( (intNumRegChk<=0) && (intOpcRedondeoMinimo<1) )
            {
                intOpcRedondeoStock=0;
                intOpcRedondeoMinimo++;
                seleccionarItmRepVar();
            }

            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean seleccionarItmRepItmSel(int intNumRegSeleccionados)
    {
        boolean blnRes=true;
        BigDecimal bdePesDisUsr=new BigDecimal(txtPesDis.getText());
        BigDecimal bdePesDisSel=new BigDecimal("0");
        BigDecimal bdePorVar=new BigDecimal("1");
        int intNumRegChk=0;
        BigDecimal bdeStkMin=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkAct=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkRep=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeStkMinCalPor=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePesRegSel=new BigDecimal("0");
        BigDecimal bdeStkDisGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdePorVarConVal=new BigDecimal("0");
        int intNumItemPueRep=0;
        String strCodAltItm="", strSubCodAltItm="", strCodAltDos="";//se lo almacena para luego compararlo y saber si esta entre iniciales 124,125,870,871,873,874 TUBERIAS y asi no redondearlos
        try
        {
            do
            {
                intNumRegChk=0;
                bdePesRegSel=new BigDecimal("0");
                bdePesDisSel=new BigDecimal("0");
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    objTblModRep.setValueAt(null, i, INT_TBL_DAT_CAN_REP_SEL);
                    txtPesTot.setText("");
                }
                objTblModRep.initRowsState();
                if(bdePorVar.compareTo(new BigDecimal("0"))<=0) //disminuye de 1 en 1
                {
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }
                for(int i=0;i<objTblModRep.getRowCountTrue();i++)
                {
                    bdeStkMin=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN).toString());
                    bdeStkAct=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT).toString());
                    bdeStkDisGrp=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString());

                    strCodAltItm=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString();
                    strSubCodAltItm=strCodAltItm.substring(0, 4);

                    strCodAltDos=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString(); 

                    if(bdeStkAct.compareTo(new BigDecimal("0"))<0){
                    }
                    else{
                        if( (strSubCodAltItm.trim().equals("124-")) || (strSubCodAltItm.trim().equals("125-")) || (strSubCodAltItm.trim().equals("870-")) || (strSubCodAltItm.trim().equals("871-")) || (strSubCodAltItm.trim().equals("873-")) || (strSubCodAltItm.trim().equals("874-"))        )
                        {
                            bdeStkMinCalPor=new BigDecimal( "" + bdeStkMin.multiply(bdePorVar));

                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));
                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);
                            // if(txtCodBodOri.getText().equals("1")){
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){  
                                if((bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                              else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.abs().compareTo(new BigDecimal("0"))>0) && (bdeStkRep.abs().compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                        else{
                            if(intOpcRedondeoMinimo==2){
                                bdeStkMinCalPor=new BigDecimal( "" + Math.floor(Double.parseDouble(""+ bdeStkMin.multiply(bdePorVar))));
                            }
                            else
                                bdeStkMinCalPor=new BigDecimal( "" + bdeStkMin.multiply(bdePorVar));

                            bdeStkRep=(bdeStkAct.subtract(bdeStkMinCalPor));
                            bdeStkRep=bdeStkRep.multiply(new BigDecimal("-1"));

                            objTblModRep.setValueAt(bdeStkRep, i, INT_TBL_DAT_CAN_REP_SEL);

                           // if(txtCodBodOri.getText().equals("1")){
                            if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBO)){    
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)<0) && (bdeStkRep.compareTo(new BigDecimal("0"))>0) && (bdeStkRep.compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                            else if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD)){
                                if(  (bdeStkAct.compareTo(bdeStkMinCalPor)>0) && (bdeStkRep.abs().compareTo(new BigDecimal("0"))>0) && (bdeStkRep.abs().compareTo(bdeStkDisGrp)<=0)   ){
                                    objTblModRep.setChecked(true, i, INT_TBL_DAT_CHK);
                                    bdePesRegSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                                    bdePesDisSel=bdePesDisSel.add(bdePesRegSel.multiply(bdeStkRep));
                                    intNumRegChk++;
                                }
                            }
                        }
                    }
                    if(intNumRegChk==intNumRegSeleccionados){
                        break;
                    }
                    bdeStkRep=new BigDecimal("0");
                    bdeStkMin=new BigDecimal("0");
                    bdeStkAct=new BigDecimal("0");
                    bdeStkDisGrp=new BigDecimal("0");
                    strCodAltItm="";
                    strSubCodAltItm="";
                }
                lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                bdePorVarGrl=bdePorVar;
                //System.out.println("% VARIACION: " + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                if(intNumRegChk>13){
                    objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + ".Ninguno se procesó porque excede el número de items de una guía</HTML>");
                    bdePorVarConVal=bdePorVar;
                    intNumItemPueRep=intNumRegChk;
                }
                else if(intNumRegChk<=0){
                    objTooBar.setMenSis("<HTML>El número de items último que se podian procesar es: " + intNumItemPueRep + " con un porcentaje de variación de: " + (bdePorVarConVal).multiply(new BigDecimal("100")) + " % </HTML>");
                }
                else{
                    objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");
                }


                if(bdePorVar.compareTo(new BigDecimal("0.01"))>0){//disminuye de 1 en 1
                    bdePorVar=bdePorVar.subtract(new BigDecimal("0.01"));
                }
                else if(bdePorVar.compareTo(new BigDecimal("0.001"))<0){
                    bdePorVar=new BigDecimal("0");
                    lblPorVar.setText("" + (bdePorVar).multiply(new BigDecimal("100")) + " %");
                    bdePorVarGrl=bdePorVar;
                    break;
                }
                else if(bdePorVar.compareTo(new BigDecimal("0.01"))<=0){//cuando el % llega al 1%
                    bdePorVar=bdePorVar.subtract(new BigDecimal("0.001"));
                }


            }while(  (bdePesDisSel.abs().compareTo(bdePesDisUsr)>0) );
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesDisSel.abs(),objParSis.getDecimalesMostrar()));

            BigDecimal bdeCanRepNeg=new BigDecimal("0");


            for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                bdeCanRepNeg=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                if(bdeCanRepNeg.compareTo(new BigDecimal("0"))<0)
                    objTblModRep.setValueAt((bdeCanRepNeg.multiply(new BigDecimal("-1"))), i, INT_TBL_DAT_CAN_REP_SEL);
            }

            if(intNumRegChk==0){
                for(int i=0;i<objTblModRep.getRowCountTrue();i++){
                    objTblModRep.setValueAt(new BigDecimal("0"), i, INT_TBL_DAT_CAN_REP_SEL);
                }
            }

            if( (  intNumRegChk==0 ) && (intNumRegSeleccionados>0) ){
                intOpcRedondeoMinimo++;
                intNumRegSeleccionados--;
                seleccionarItmRepItmSel(intNumRegSeleccionados);
            }
//            objTooBar.setMenSis("<HTML>El número de items a reponer encontrados es: " + intNumRegChk + " con un porcentaje de variación de: " + (bdePorVar).multiply(new BigDecimal("100")) + " % </HTML>");

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
     
    /**
     * Obtiene el tipo de permiso para modificar fecha del documento.
     * @return intTipModFec
     */
    private int canChangeDate()
    {
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try
        {
            //System.out.println("canChangeDate");
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null)
            {
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1)
                {
                    intTipModFec=4;
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + intCodEmpGrp + "";
                    strSQL+=" AND co_loc=" + intCodLocGrp + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next())
                    {
                        intTipModFec=rstChaDat.getInt("ne_tipresmodfecdoc");
                    }
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
    }


   /**
    * Valida si existen seleccionados items a reponer.
    * @return true: Si existen items a reponer.
    * <BR> false: Caso contrario.
    */
    private boolean isItmRep()
    {
       boolean blnRes=false;
       int intExiDocAut=0;
       
       if(quitarItmCanRepCer())
       {
            for(int i=0;i<objTblModRep.getRowCountTrue(); i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                   intExiDocAut++;
                }
            }
       }
       if(intExiDocAut>0)
           blnRes=true;
   
       return blnRes;
    }

    /**
     * Función que elimina items con cantidad a reponer 0.
     * @return 
     */
    private boolean quitarItmCanRepCer()
    {
        boolean blnRes=true;
        BigDecimal bdeValRepCer=new BigDecimal("0");
        try
        {
            //System.out.println("quitarItmCanRepCer");
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                    bdeValRepCer=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                    if(bdeValRepCer.compareTo(new BigDecimal("0"))==0)
                    {
                        objTblModRep.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                }
            }
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;

    }
    
private double TotalItems(int tipo){
        double  dblCanRep=0;
        double  dblTotCanRep=0; 
        double  dblTotRegis=0;
        int contador =0;        
        
        for(int i=0; i<objTblModRep.getRowCountTrue();i++){
            if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK)){
                dblCanRep= objUti.parseDouble(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL));
                dblTotCanRep =dblTotCanRep +dblCanRep;
                contador=contador+1;

                    
                if (tipo==1)
                {
                    dblTotCanRep=contador;
                }
                    
            }
        }
        if (tipo==1)
        {
            if (contador >100)
                blnTotItm =true;  
            else
                blnTotItm =false;  
        }
        return dblTotCanRep;
    }    
private boolean validacionesIngreso(java.sql.Connection conExt){
        boolean blnRes=true, blnItem=true;
        double totalCantidad=0;
        double totalRegistros=0;     
        blnTotItm =false;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc = null, rstLoc01;
        String strMsg="", strItem="";
        try{
            if(conExt!=null){
                stmLoc= conExt.createStatement();
                totalCantidad = TotalItems(0);
                totalRegistros = TotalItems(1);
                if(totalCantidad!=0)
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
                        strSQL+=" where cab.trc_estado ='I' and cab.trc_bodorigen = "+ objUti.codificar(txtCodBodOri.getText()) +"  \n";           
                        strSQL+="       and  cab.trc_boddestino = "+ objUti.codificar(txtCodBodDes.getText()) +"  \n";      
                        strSQL+="       and  cab.trc_numitems = "+ totalRegistros +"  \n";  
                        strSQL+="       and trc_codigomotivo='REPOSICION'  \n";    
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
                    strMsg="<html> La reposición ya se encuentra ingresada en espera de ser procesada. <BR><BR>" ;// CAMBIA
                    String strTit="Mensaje del sistema Zafiro";
                    JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                    strMsg="<html> La reposición no tiene items ingresados. <BR><BR>" ;// CAMBIA
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

private boolean validacionUbicaciones(java.sql.Connection conExt,String cadenaItems){
        boolean blnRes=true, blnItem=true;
        java.sql.Statement stmLoc,stmLoc01;
        java.sql.ResultSet rstLoc = null, rstLoc01;
////        cadenaItems="'958-000059-I','950-000024-S','025-000056-I','869-000039-I','950-000015-S','882-000003-S','525-000035-I','869-000036-I','950-000026-S','025-000170-I','025-000167-I','025-000209-I','025-000135-I','025-000150-I','026-000108-I','889-000035-S','023-000212-I','023-000199-I','025-000048-I','026-000008-I','025-000133-I','025-000129-I','025-000104-I','025-000091-I','025-000214-I','024-000032-I','027-000043-I','025-000050-I','873-000033-I','876-000119-I','952-000032-I','028-000318-I','040-000026-I','961-000117-I','023-000193-I','023-000203-I','020-000205-I','949-000014-I','945-000068-I','876-000218-I','897-000014-S','024-000054-I','029-000199-I','521-000053-I','949-000019-I','945-000090-I','521-000110-I','961-000118-I','038-000070-I','953-000143-I','953-000027-I','951-000001-I','880-000056-I','882-000014-S','945-000031-I','040-000073-I','020-000211-I','020-000183-I','020-000084-I','028-000140-I','945-000046-I','880-000053-I','020-000209-I','021-000073-I'";
        String  cadenaItemsLimpia ="";
        ArrayList<String> lstItemsDuplicados = new ArrayList<>();
        try{
            listaUbicacion.clear();
            if(conExt!=null){
                stmLoc= conExt.createStatement();
                cadenaItemsLimpia= cadenaItems.replace("''", "'");

                  strSQL="";
                        strSQL+=" SELECT iv102.ITEMNMBR, iv102.BIN ,trim (iv01.ITMGEDSC) as tresl, trim(iv01.ITMSHNAM) as itemzaf\n";
                        strSQL+=" FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112 AS iv102 \n";
                        strSQL+="           inner join "+objParSis.getNombreBaseDatosGP()+"..IV00101 as iv01 on (iv102.ITEMNMBR = iv01.ITEMNMBR)  \n";                        
                        strSQL+=" WHERE iv102.LOCNCODE = "+ objUti.codificar(txtCodBodOri.getText()) +"  \n";           
                        strSQL+="       AND iv102.ITEMNMBR IN ( \n";      
                        strSQL+="                               SELECT ITEMNMBR \n";  
                        strSQL+="                               FROM "+objParSis.getNombreBaseDatosGP()+"..IV00112  \n"; 
                        strSQL+="                               WHERE LOCNCODE = "+ objUti.codificar(txtCodBodOri.getText()) +"  \n";  
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


private boolean validacionTiempo(java.sql.Connection conExt){
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
//ooo
////private boolean consultaDisponible(java.sql.Connection conExt, String items,String cantidades,String bodega)//chiqui
////    {
////        
////        java.sql.Statement stmGP;
////        java.sql.ResultSet rstGP;  
////        java.sql.CallableStatement cal;
////        String strSql ="";
////        int intCodRes=-1;
////        
////        boolean blnRes=true;
////        try
////        {
////            //conGP = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
////             
////            if (conExt!=null)
////            {
////                stmGP = conExt.createStatement();
////                System.out.println("VAAAAAMMMOOOOSSSS ");
////////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
////////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
////                strSql="";
////                strSql+="{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
////                System.out.println("iniciaProcesoOT:" + strSql);
////            items = "'038-000106-I'"; // <<< si quieres que siempre sea este item, sino comenta esta línea
////            cantidades = "1000000";                
////                cal = conExt.prepareCall(strSql);
////                cal.setString("ITEMS",  items);//Envio de parametro
////                cal.setString("CANTIDADES",  cantidades);//Envio de parametro
////                cal.setString("BODEGA",  bodega   );//Envio de parametro
////                
////                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
////                cal.execute();
////                intCodRes = cal.getInt("intRes");
////                if(intCodRes==1){
////                    blnRes=false;
////                    System.out.println("falla");
////                }
////              
////                cal.close();
////                cal=null;
////                 
////                stmGP.close();
////                stmGP=null;
////                
////            }
////        }
////        catch(java.sql.SQLFeatureNotSupportedException e){
////            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch (java.sql.SQLException e){
////            System.out.println("entra como que se cae SQLException... " + e.toString());
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        catch(Exception e){
////            System.out.println("entra como que se cae Exception... " + e.toString());
////            blnRes=false;
////            objUti.mostrarMsgErr_F1(this, e);
////        }
////        return blnRes;
////    }



////modificado
private boolean consultaDisponible(java.sql.Connection conExt, String items, String cantidades, String bodega) {
    java.sql.Statement stmGP = null;
    java.sql.ResultSet rstGP = null;
    java.sql.Connection GPInt = null;
    java.sql.CallableStatement cal = null;
    String strSql = "";
    String respuesta = "";

    boolean blnRes = true;

    // Declarar ArrayList para guardar los datos del SELECT
    ArrayList<HashMap<String, Object>> listaReservados = new ArrayList<>();

    try {
        GPInt = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(),objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
        if (GPInt != null) {
            stmGP = GPInt.createStatement();
            System.out.println("VAAAAAMMMOOOOSSSS ");

            strSql = "{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
            System.out.println("iniciaProcesoOT:" + strSql);

            cal = GPInt.prepareCall(strSql);

            // Puedes ajustar si quieres usar los valores que llegan o forzar como antes
            items = "'038-000106-I'"; // <<< si quieres que siempre sea este item, sino comenta esta línea
            cantidades = "1";

            cal.setString(1, items);       // Envío de parámetro
            cal.setString(2, cantidades);  // Envío de parámetro
            cal.setString(3, bodega);      // Envío de parámetro
            cal.registerOutParameter(4, java.sql.Types.INTEGER); // Parametro de salida

            boolean hayResultados = cal.execute();

            if (hayResultados) {
                rstGP = cal.getResultSet();

                while (rstGP.next()) {
                    HashMap<String, Object> fila = new HashMap<>();
                    fila.put("ITEM", rstGP.getString("ITEM"));
                    fila.put("BODEGA", rstGP.getString("BODEGA"));
                    fila.put("DISPONIBLEGP", rstGP.getBigDecimal("DISPONIBLEGP"));
                    fila.put("DISPONIBLEGPWEB", rstGP.getBigDecimal("DISPONIBLEGPWEB"));
                    fila.put("reserva", rstGP.getBigDecimal("reserva"));
                    fila.put("ATYALLOC", rstGP.getBigDecimal("ATYALLOC"));
////////                    fila.put("ITEM", rstGP.getString("ITEM"));
////////                    fila.put("CANTIDAD", rstGP.getString("CANTIDAD"));
////                    fila.put("Codigo", rstGP.getString("Codigo"));
////                    fila.put("Bodega", rstGP.getString("Bodega"));
////                    fila.put("Reservado", rstGP.getString("Reservado"));

                    listaReservados.add(fila);
                }
            }

            // Leer el parámetro de salida
            respuesta = cal.getString(4);
            System.out.println("Respuesta procedimiento: " + respuesta);

            if (respuesta == null || respuesta.equalsIgnoreCase("ERROR") || respuesta.equalsIgnoreCase("ERROR_EXCEPTION")) {
                blnRes = false;
                System.out.println("falla en respuesta");
            } else {
                System.out.println("Procedimiento ejecutado correctamente");
            }

            // Mostrar datos guardados
            for (HashMap<String, Object> fila : listaReservados) {
////                System.out.println("ITEM: " + fila.get("ITEM"));
////                System.out.println("BODEGA: " + fila.get("BODEGA"));
////                System.out.println("Disponible GP: " + fila.get("DISPONIBLEGP"));
////                System.out.println("Disponible GP Web: " + fila.get("DISPONIBLEGPWEB"));
////                System.out.println("Reserva: " + fila.get("reserva"));
////                System.out.println("ATYALLOC: " + fila.get("ATYALLOC"));
////////                System.out.println("ITEM: " + fila.get("ITEM"));
////////                System.out.println("CANTIDAD: " + fila.get("CANTIDAD"));
                System.out.println("----------------------------");
            }

            // Cerrar recursos
            if (rstGP != null) rstGP.close();
            if (stmGP != null) stmGP.close();
            if (cal != null) cal.close();
            if (GPInt != null) GPInt.close();
        }
    } catch (java.sql.SQLFeatureNotSupportedException e) {
        System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    } catch (java.sql.SQLException e) {
        System.out.println("entra como que se cae SQLException... " + e.toString());
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    } catch (Exception e) {
        System.out.println("entra como que se cae Exception... " + e.toString());
        blnRes = false;
        objUti.mostrarMsgErr_F1(this, e);
    } finally {
        try { if (rstGP != null) rstGP.close(); } catch (Exception e) {}
        try { if (stmGP != null) stmGP.close(); } catch (Exception e) {}
        try { if (cal != null) cal.close(); } catch (Exception e) {}
        try { if (GPInt != null) GPInt.close(); } catch (Exception e) {}
    }

    return blnRes;
}

////
////private boolean consultarReservadosDesdeWeb() {
////    java.sql.Connection conn = null;
////    java.sql.CallableStatement cstmt = null;
////    java.sql.ResultSet rs = null;
////
////    boolean exito = true;
////    listaReservas.clear();
////
////    try {
////        conn = DriverManager.getConnection(
////            objParSis.getStringConexion_Dynamics2021(),
////            objParSis.getUsuarioBaseDatos_Dynamics2021(),
////            objParSis.getClaveBaseDatos_Dynamics2021()
////        );
////
////        String procedimiento = "{call dbo.TUVAL_ACTUALIZA_RESERVADO_WEB_n(?, ?, ?, ?)}";
////        cstmt = conn.prepareCall(procedimiento);
////
////        String items = "'025-000067-I','025-000190-I'";
////        String cantidades = "20,1";
////        String bodega = "VIA_DAULE";
////
////        cstmt.setString(1, items);
////        cstmt.setString(2, cantidades);
////        cstmt.setString(3, bodega);
////        cstmt.registerOutParameter(4, java.sql.Types.INTEGER); // Output param
////
////        boolean hayResultados = cstmt.execute();
////
////        if (hayResultados) {
////            rs = cstmt.getResultSet();
////            while (rs.next()) {
////                HashMap<String, Object> fila = new HashMap<>();
////                fila.put("CODIGO", rs.getString("CODIGO"));
////                fila.put("BODEGA", rs.getString("BODEGA"));
////                fila.put("CANTIDADPEDIDA", rs.getBigDecimal("CANTIDADPEDIDA"));
////                fila.put("DISPONIBLEGP", rs.getBigDecimal("DISPONIBLEGP"));
////                fila.put("RESERVADOGP", rs.getBigDecimal("RESERVADOGP"));
////                fila.put("RESERVADOWEB", rs.getBigDecimal("RESERVADOWEB"));
////                fila.put("DISPONIBLEGPVSWEB", rs.getBigDecimal("DISPONIBLEGPVSWEB"));
////                listaReservas.add(fila);
////            }
////        }
////
////        int resultadoSalida = cstmt.getInt(4);
////        System.out.println("Código de resultado SP: " + resultadoSalida);
////
////        for (HashMap<String, Object> fila : listaReservas) {
////            System.out.println("CODIGO: " + fila.get("CODIGO"));
////            System.out.println("BODEGA: " + fila.get("BODEGA"));
////            System.out.println("CANTIDADPEDIDA: " + fila.get("CANTIDADPEDIDA"));
////            System.out.println("DISPONIBLEGP: " + fila.get("DISPONIBLEGP"));
////            System.out.println("RESERVADOGP: " + fila.get("RESERVADOGP"));
////            System.out.println("RESERVADOWEB: " + fila.get("RESERVADOWEB"));
////            System.out.println("DISPONIBLEGPVSWEB: " + fila.get("DISPONIBLEGPVSWEB"));
////            System.out.println("-----------------------------");
////        }
////
////    } catch (Exception e) {
////        System.err.println("Error al ejecutar el procedimiento: " + e.getMessage());
////        exito = false;
////    } finally {
////        try { if (rs != null) rs.close(); } catch (Exception e) {}
////        try { if (cstmt != null) cstmt.close(); } catch (Exception e) {}
////        try { if (conn != null) conn.close(); } catch (Exception e) {}
////    }
////
////    return exito;
////}

private boolean consultarReservadosDesdeWeb(String items,String cantidades,String bodega) {
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




//////private boolean consultaDisponible(java.sql.Connection conExt, String items,String cantidades,String bodega)//chiqui
//////    {
//////        
//////        java.sql.Statement stmGP;
//////        java.sql.ResultSet rstGP;  
//////       java.sql.Connection GPInt;
//////        java.sql.CallableStatement cal;
//////        String strSql ="";
//////        String respuesta="";
//////        
//////        boolean blnRes=true;
//////        try
//////        {
//////            GPInt = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
//////             
//////            if (GPInt!=null)
//////            {
//////                stmGP = GPInt.createStatement();
//////                System.out.println("VAAAAAMMMOOOOSSSS ");
//////////                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
//////////                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
//////                strSql="";
//////                strSql+="{call TUVAL_ACTUALIZA_RESERVADO_WEB(?, ?, ?, ?)}";
//////                System.out.println("iniciaProcesoOT:" + strSql);
//////                cal = GPInt.prepareCall(strSql);
//////                items ="038-000106-I";
//////                cantidades ="1";
//////                cal.setString(1,  items);//Envio de parametro
//////                cal.setString(2,  cantidades);//Envio de parametro
//////                cal.setString(3,  bodega   );//Envio de parametro
//////                
//////                cal.registerOutParameter(4, java.sql.Types.NVARCHAR);
//////                cal.execute();
//////                respuesta = cal.getString(4);
//////                if(respuesta.equals("ERROR")){
//////                    blnRes=false;
//////                    System.out.println("falla");
//////                }
//////              
//////                cal.close();
//////                cal=null;
//////                 
//////                stmGP.close();
//////                stmGP=null;
//////                GPInt.close();
//////                GPInt=null;
//////                
//////            }
//////        }
//////        catch(java.sql.SQLFeatureNotSupportedException e){
//////            System.out.println("entra como que se cae SQLFeatureNotSupportedException... " + e.toString());
//////            blnRes=false;
//////            objUti.mostrarMsgErr_F1(this, e);
//////        }
//////        catch (java.sql.SQLException e){
//////            System.out.println("entra como que se cae SQLException... " + e.toString());
//////            blnRes=false;
//////            objUti.mostrarMsgErr_F1(this, e);
//////        }
//////        catch(Exception e){
//////            System.out.println("entra como que se cae Exception... " + e.toString());
//////            blnRes=false;
//////            objUti.mostrarMsgErr_F1(this, e);
//////        }
//////        return blnRes;
//////    }

private boolean validacionDisponible(java.sql.Connection conExt){
        boolean blnRes=true;
        double totalCantidad=0;
        double totalRegistros=0;      
        int contador =0;
        strCadeItems="";

        String strMsg="", strCadenaItem="",strBodega="",strCadenaCant ="";
        if(conExt!=null)
        {
            strBodega =txtCodBodOri.getText();
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                    contador++;
                    
//////                    strCadenaItem = strCadenaItem + ","+;
//////                    strCadenaCant = strCadenaCant + ","+ objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString();
                    
                    strCadenaItem = objUti.codificar(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM))+","+ strCadenaItem;
                    strCadenaCant = objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString()+","+ strCadenaCant;                    
                } 
//////////                if (contador ==100)
//////////                {
//////////                    break;
//////////                }
                    
            }
            
            if (strCadenaItem.endsWith(",")) {
                strCadenaItem = strCadenaItem.substring(0, strCadenaItem.length() - 1);
                }
            if (strCadenaCant.endsWith(",")) {
                strCadenaCant = strCadenaCant.substring(0, strCadenaCant.length() - 1);                
                }
            
            if((strCadenaItem!="") && (strCadenaCant !=""))
            {
                //strCadenaItem=strCadenaItem.replace("'", "''");
                strCadeItems=strCadenaItem;
                if (consultarReservadosDesdeWeb(strCadenaItem,strCadenaCant,strBodega))
                //if (consultaDisponible(conExt,strCadenaItem,strCadenaCant,strBodega))
                      blnRes =true;
                else
                      blnRes =false;
            }
            else
                blnRes=false;


        }
        else
        {
            blnRes=false;
        }
////        if(!blnRes)
////        {
////            
////                strMsg="<html> La reposición posee items que no tienen disponibilidad. <BR><BR>" ;// CAMBIA
////                String strTit="Mensaje del sistema Zafiro";
////                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.ERROR_MESSAGE);
////        }            
  
        return blnRes;
    }   
  
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        blnTotItm=false;
        boolean blnRes=false;
        boolean blnResWeb=false;
        Connection conLoc;
        // Formato del string que contiene la fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");       
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                
                
                conLoc.setAutoCommit(false);
                fe_ultmod ="";
                ne_tieespe=-1;
                if (validacionTiempo(conLoc))
                {
                   Date fecha1 = formato.parse(fe_ultmod);
                   Date fechaActual = new Date();
                   Calendar calendario = Calendar.getInstance();
                   calendario.setTime(fecha1);
                   calendario.add(Calendar.MINUTE, ne_tieespe);
                   Date nuevaFecha1 = calendario.getTime();
                   long diferenciaEnMilisegundos = fechaActual.getTime() - fecha1.getTime();
                   long diferenciaEnMinutos = TimeUnit.MILLISECONDS.toMinutes(diferenciaEnMilisegundos);                   

////                    if (nuevaFecha1.before(fechaActual))                    
////                        {
                            if (validacionesIngreso(conLoc))
                            {
                                if(validacionDisponible(conLoc))
                                {
                                    if(validacionUbicaciones(conLoc,strCadeItems))  
                                    {
    ////                                if(insertarCabecera(conLoc)){
                                        if(insertarDetalleCab(conLoc))
                                        {
                                            if (updateUltRepTra(conLoc))
                                            {
                                                if (historialRep(conLoc))
                                                {
                                                    blnRes=true;
                                                    conLoc.commit();
                                                    txtCanTot.setText("");                                                    
                                                }
                                                else
                                                    conLoc.rollback();                                                
////////////////                                                if (blnTotItm )
////////////////                                                {
////////////////                                                    strMsgTotItm="";
////////////////                                                    strMsgTotItm="<HTML>SE ENCUENTRAN MAS ITEMS POR REPONER , DEBE PROCEDER A REALIZAR OTRA REPOSICION DE LA BODEGA " + txtCodBodDes.getText() + " <BR><BR><html>";
////////////////                                                    mostrarMsgInf(strMsgTotItm);
////////////////                                                }
////                                                llamarWebTuval();
////                                                butLlamada.doClick();                                                
                                            }
                                            else
                                                conLoc.rollback();                                        
                                        }
                                         else
                                              conLoc.rollback(); 
                                    }
                                    else{
                                        conLoc.rollback();
                                        }
                                    }
                                    else{
                                        conLoc.rollback();                        
                                        }    
    ////                            }
                            }                         
////                        }
////                        else
////                            mostrarMsgInf("Por cada reposicion se debe esperar "+ne_tieespe +" minutos ,faltan "+ diferenciaEnMinutos +" minutos para poder realizar otra reposicion.");
                }
                else
                    mostrarMsgInf("No se tiene una configuraciòn de tiempo de espera.");
                conLoc.close();
                conLoc=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
     
    
    /**
     * Reposiciones llamado a la WebTuval para que actualice lo solicitado 
     * @return 
     */
     
////    private boolean llamarWebTuval()//jose mario
////    {
////        boolean blnRes=true;
////        String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaBaseDatos());
////        String strUrlWebTuval = "http://api.tuvalsa.com/bodegas/getreposicion/"+strFecSis; // Agregar fecha en este formato ======> 2023-08-02  
////        try{
////            System.out.println("LLAMADA>>>> "+strUrlWebTuval);
////            URL url = new URL(strUrlWebTuval);
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setRequestMethod("GE+++++++++T");
////            
////        }
////        catch (Exception e){
////            objUti.mostrarMsgErr_F1(this, e);
////            blnRes=false;
////        }
////        return blnRes;
////    }
    
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
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    
//    
//        try {
    
        
    /**
     * Función que envía a imprimir orden de despacho.
     * @return 
     */
  

    private boolean insertar_cabRepInvBod(Connection conLocal)
    {
        boolean blnRes=true; 
        int intUltReg;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabRepInvBod AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpGrp;
                strSQL+=" AND a1.co_loc=" + intCodLocGrp;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+="INSERT INTO tbm_cabRepInvBod(";
                strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bodorg,";
                strSQL+="             co_boddes, nd_pesdiskgr, nd_pestotkgr, st_imp, tx_obs1, tx_obs2, ";
                strSQL+="             st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_regrep)";
                strSQL+=" VALUES (";
                strSQL+=" " + intCodEmpGrp + "";//co_emp
                strSQL+=", " + intCodLocGrp + "";//co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";//co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";//co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + "";//ne_numdoc
                strSQL+=", " + txtCodBodOri.getText() + "";//co_bodorg
                strSQL+=", " + txtCodBodDes.getText() + "";//co_boddes
                strSQL+=", " + txtPesDis.getText() + "";//nd_pesdiskgr
                //strSQL+="," + txtPesTot.getText() + "";//nd_pestotkgr
                strSQL+=", " + objUti.redondearBigDecimal(txtPesTot.getText(),objParSis.getDecimalesBaseDatos()) + "";//nd_pestotkgr
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultmod
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=", 'I'";//st_regrep
                strSQL+=");";
            
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean insertar_detRepInvBod(Connection conLocal)
    {
        boolean blnRes=true;
        int intReg=0, intCodItmMaeRep=0;
        BigDecimal bdeCanRep=new BigDecimal("0");
        BigDecimal bdePesUniItm = new BigDecimal("0");
        BigDecimal bdePesTotItm=new BigDecimal("0");
        String strInsTmp="";
        try
        {
            //System.out.println("insertar_detRepInvBod");
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                for(int i=0; i<objTblModRep.getRowCountTrue();i++)
                {
                    if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                    {
                        bdeCanRep=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                        bdePesUniItm=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                        bdePesTotItm=bdeCanRep.multiply(bdePesUniItm);
                        
                        if(bdeCanRep.compareTo(new BigDecimal("0"))>0)
                        {
                            intReg++;
                            intCodItmMaeRep=objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE)==null?0:Integer.parseInt(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detRepInvBod(";
                            strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_canrep, ";
                            strSQL+="             nd_pestotkgr, st_regrep)";
                            strSQL+=" VALUES (";
                            strSQL+="" + intCodEmpGrp + "";//co_emp
                            strSQL+="," + intCodLocGrp + "";//co_loc
                            strSQL+="," + txtCodTipDoc.getText() + "";//co_tipdoc
                            strSQL+="," + txtCodDoc.getText() + "";//co_doc
                            strSQL+=","+ intReg + "";//co_reg
                            strSQL+="," + objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";//co_itm
                            //strSQL+="," + bdeCanRepDisIns + "";//nd_canrep
                            //strSQL+="," + bdeCanRepDisIns.multiply(bdePesIns) + "";//nd_pestotkgr
                            strSQL+="," + bdeCanRep +  "";//nd_canrep
                            strSQL+="," + bdePesTotItm +  "";//nd_pestotkgr
                            strSQL+=",'I'";//st_regrep
                            strSQL+=");";
                            strInsTmp+=strSQL;
                        }
                    }
                }
                System.out.println("insertar_detRepInvBod: "+strInsTmp);
                stm.executeUpdate(strInsTmp);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Insertar seguimiento de reposición
     * @param conLocal
     * @return 
     */
    private boolean insertar_segMovInv(Connection conLocal)
    {
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=1;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Obtener el código del último seguimiento.
                strSQL = "";
                strSQL+= " SELECT MAX(a1.co_seg)+1 as co_seg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                }
                rstLoc.close();
                rstLoc=null;
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabRepInvBod, co_locRelCabRepInvBod, co_tipDocRelCabRepInvBod, co_docRelCabRepInvBod) \n";
                strSQL+=" VALUES ("+intCodSeg+","+intCodRegSolTra+", "+intCodEmpGrp+", "+intCodLocGrp+" ,"+  txtCodTipDoc.getText() +","+ txtCodDoc.getText() +"); \n";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    private boolean generarArrSolTrs()
    {
        boolean blnRes=true;
        BigDecimal bdeValRepCer=new BigDecimal("0");
        arlDatConSolTraInv = new ArrayList();
        try
        {
            calcularPesTotRep(); 
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                    bdeValRepCer=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                    if(bdeValRepCer.toString().length()>0)
                    {
                        arlRegConSolTraInv = new ArrayList();
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_TRA_ITM_GRP, Integer.parseInt(tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_TRA_CAN, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_PES_UNI, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_PES).toString()));
                        arlRegConSolTraInv.add(INT_ARL_GEN_SOL_PES_TOT_ITM, Double.parseDouble(tblDat.getValueAt(i, INT_TBL_DAT_PES_TOT_KGR).toString()));
                        arlDatConSolTraInv.add(arlRegConSolTraInv);
                    }
                }
            }
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que agrega relación de las transferencias con las reposiciones.
     * Inserta relacion de REINBO con TRINRB 
     * @param conLocal
     * @return 
     */
    private boolean insertar_traEmiRepInvBod(Connection conLocal)
    {
        boolean blnRes=true;
        java.sql.ResultSet rstLoc;
        String strInsTraEmiRep="";
        int intReg=1;
        try
        {
            if(conLocal!=null)
            {
                stm=conLocal.createStatement();
               
                //QUERY PARA CONSULTAR LAS TRANSFERENCIAS EXISTENTES
                strSQL =" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a2.co_Seg ";
                strSQL+=" FROM tbm_CabMovInv as  a ";
                strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut as t ON (t.co_emp=a.co_emp AND t.co_loc=a.co_loc AND t.co_tipDoc=a.co_tipDoc) ";
                strSQL+=" INNER JOIN tbm_cabSolTraInv as a1 ON (a1.co_emp=a.co_empRelCabSolTraInv AND a1.co_loc=a.co_locRelCabSolTraInv AND a1.co_tipDoc=a.co_tipDocRelCabSolTraInv AND a1.co_Doc=a.co_docRelCabSolTraInv) ";
                strSQL+=" INNER JOIN tbm_cabSegMovInv as a2 ON (a2.co_empRelCabSolTraInv=a1.co_emp AND a2.co_locRelCabSolTraInv=a1.co_loc AND a2.co_tipDocRelCabSolTraInv=a1.co_tipDoc AND a2.co_docRelCabSolTraInv=a1.co_Doc) ";
                strSQL+=" AND t.co_cfg="+INT_COD_CFG_TIP_AUT_TRA_REP_INV;
                strSQL+=" AND a2.co_Seg in ( ";
                strSQL+="   SELECT a.co_Seg ";
                strSQL+="   FROM tbm_cabSegMovInv as a ";
                strSQL+="   INNER JOIN tbm_cabRepInvBod as a1  ";
                strSQL+="   ON (a1.co_Emp=a.co_EmpRelCabRepInvBod AND a1.co_loc=a.co_LocRelCabRepInvBod AND a1.co_tipDoc=a.co_TipDocRelCabRepInvBod AND a1.co_Doc=a.co_DocRelCabRepInvBod ) ";
                strSQL+="   WHERE a1.co_Emp="+intCodEmpGrp;
                strSQL+="   AND a1.co_loc="+intCodLocGrp;
                strSQL+="   AND a1.co_tipDoc="+txtCodTipDoc.getText();
                strSQL+="   AND a1.co_doc="+txtCodDoc.getText();
                strSQL+="   GROUP BY a.co_Seg ";
                strSQL+="   )";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a2.co_Seg ";

                //System.out.println("ConsultarTransferenciasREINBO: "+strSQL);
                rstLoc= stm.executeQuery(strSQL);
                while(rstLoc.next())
                {
                    strSQL="";
                    strSQL+="INSERT INTO tbm_traEmiRepInvBod(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_emprel, co_locrel, ";
                    strSQL+="             co_tipdocrel, co_docrel, st_regrep)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + intCodEmpGrp + "";//co_emp
                    strSQL+=" ," + intCodLocGrp + "";//co_loc
                    strSQL+=" ," + txtCodTipDoc.getText() + "";//co_tipdoc
                    strSQL+=" ," + txtCodDoc.getText() + "";//co_doc
                    strSQL+=" ," + intReg + "";//co_reg
                    strSQL+=" ," + rstLoc.getString("co_emp") + "";//co_emprel
                    strSQL+=" ," + rstLoc.getString("co_loc") + "";//co_locrel
                    strSQL+=" ," + rstLoc.getString("co_tipDoc") + "";//co_tipdocrel
                    strSQL+=" ," + rstLoc.getString("co_doc") + "";//co_docrel
                    strSQL+=" ,'I'";//st_regrep
                    strSQL+=" );";
                    strInsTraEmiRep+=strSQL;
                    intReg++;
                }
                rstLoc.close();
                rstLoc=null;
                
                System.out.println("insertar_traEmiRepInvBod: "+strInsTraEmiRep);
                stm.executeUpdate(strInsTraEmiRep);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
private boolean hayDatosEnTablaDat() {
    boolean blnRes = false;

    try {
        if (objTblModRep == null) {
            return false;
        }

        if (objTblModRep.getRowCountTrue() > 0) {
            blnRes = true;
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }

    return blnRes; 
}    

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
private boolean consultarReg()
    {
        boolean blnRes=true;
        boolean blnExi=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sálo se muestre los documentos asignados al programa.
                
                strSQL ="";
                strSQL+=" SELECT * FROM vwListadoReposiciones ";
                strSQL+=" WHERE 1=1   ";
                if (!txtCodBodOri.getText().equals(""))
                {
                    strAux=txtCodBodOri.getText();
                    strSQL+=" AND tx_bodOri='" + strAux + "'";
                    blnExi=true;
                }   
                
                if (!txtCodBodDes.getText().equals(""))
                {
                    strAux=txtCodBodDes.getText();
                    strSQL+=" AND tx_bodDes='" + strAux + "'";
                    blnExi=true;
                }                 
 
                if (dtpFecDoc.isFecha())
                {
                    strSQL+=" AND CAST(fe_feIng as DATE) = '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                    blnExi=true;
                }
                
                if (!txtNumDoc.getText().equals(""))
                {
                    strAux=txtNumDoc.getText();
                    strSQL+=" AND trc_numtrx=" + strAux + "";
                    blnExi=true;
                }                                       
                
                    if(!blnExi)
                    {
                        if (intNumDoc>0)
                            strSQL+=" AND trc_numtrx='" + intNumDoc + "'";                        
                    }
                
    
                

                

                //strAux=txtCodDoc.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND trc_numtrx=" + strAux + "";
//                strAux=txtNumDoc.getText();
//                if (!strAux.equals(""))
//                    strSQL+=" AND a1.trc_numdoc=" + objUti.codificar(strAux) + "";
////                strAux=txtCodBodOri.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND trc_bodorigen=" + objUti.codificar(strAux) + "";
////                strAux=txtCodBodDes.getText();
////                if (!strAux.equals(""))
////                    strSQL+=" AND trc_boddestino=" + objUti.codificar(strAux) + "";
              
                strSQL+=" ORDER BY trc_numtrx   ";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                arlDatConCnf = new ArrayList();
                while(rst.next()){
                    arlRegConCnf = new ArrayList();
 
                    arlRegConCnf.add(INT_ARL_CON_COD_DOC,rst.getInt("trc_numtrx"));
                    arlDatConCnf.add(arlRegConCnf);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConCnf.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConCnf.size()) + " registros");
                    intIndiceConCnf=arlDatConCnf.size()-1;
                    cargarReg();
                    obtenerEstadoDoc();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                    lblTit.setText("Reposición de inventario desde otras bodegas...");
                }
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
//                    if (cargarTrsReg()){
                        blnRes=true;
//                    }
                }
            }
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                 strSQL+=" select * from vwListadoReposiciones ";
                strSQL+=" WHERE trc_numtrx=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
////                    strAux=rst.getString("tx_tipdoc");
////                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipdoc");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
////                    strAux=rst.getString("tx_tipdoc");
////                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("trc_numtrx");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_bodOri");
                    txtCodBodOri.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("tx_bodOri");
                    txtNomBodOri.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_bodDes");
                    txtNomBodDes.setText((strAux==null)?"":strAux);

                    
                    strAux=rst.getString("tx_bodDes");
                    txtCodBodDes.setText((strAux==null)?"":strAux);

                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_feIng"),"dd/MM/yyyy"));

//                    strEstImpDoc=rst.getString("st_imp");

                    strAux=rst.getString("trc_numtrx");
                    txtNumDoc.setText((strAux==null)?"":strAux);

                    txtPesDis.setText(""+objUti.redondearBigDecimal(rst.getObject("nd_pesIng")==null?"0":rst.getString("nd_pesIng"),objParSis.getDecimalesMostrar()));
                    
                    txtPesTot.setText(""+objUti.redondearBigDecimal(rst.getObject("nd_pesTot")==null?"0":rst.getString("nd_pesTot"),objParSis.getDecimalesMostrar()));
                    txtCanTot.setText(""+objUti.redondearBigDecimal(rst.getObject("nd_canIng")==null?"0":rst.getString("nd_canIng"),objParSis.getDecimalesMostrar()));
                    
                    strAux=rst.getString("tx_tiprep");
                    if (strAux.equals("MAXIMO"))
                        rbMax.isSelected();
                    if (strAux.equals("MINIMO"))
                        rbMin.isSelected();
                    if (strAux.equals("PROMEDIO"))
                        rbMed.isSelected();                    
                    

//                    strAux=rst.getString("tx_obs1");
//                    txaObs1.setText((strAux==null)?"":strAux);
//                    strAux=rst.getString("tx_obs2");
//                    txaObs2.setText((strAux==null)?"":strAux);
//                    //Mostrar el estado del registro.
//                    strAux=rst.getString("st_reg");
//                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            con.close();
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceConCnf+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConCnf.size()) );
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            
            //objTblModRep.removeAllRows();
            
           if (hayDatosEnTablaDat()) 
                limpiarTablaDat();
                    
                    
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT * FROM vwListadoReposiciones_detalle ";
                strSQL+=" WHERE trd_numtrx=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                strSQL+=" ORDER BY trd_numtrx, co_itm  ";
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                //objTooBar.setMenSis("Cargando datos...");
                

                        
                        
                        
                while (rst.next())
                {
                    vecReg=new Vector(); 
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,         "" );
                    vecReg.add(INT_TBL_DAT_CHK,             null);
                    vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,     rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_DOS,     "" + rst.getString("co_itmAlt2")); 
                    vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_desItm"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED, "" + rst.getString("tx_uni"));
                    vecReg.add(INT_TBL_DAT_ITM_TIP, "" + rst.getString("tx_abc"));
                    //vecRegTrs.add(INT_TBL_DAT_UBI, "" );
                    vecReg.add(INT_TBL_DAT_STK_ACT_BOD_CEN_DIS,     rst.getString("nd_stkCenDis"));
                    vecReg.add(INT_TBL_DAT_DIS_IN4,     rst.getString("nd_disInm4"));
                    vecReg.add(INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP,     rst.getString("nd_perCenDis"));
                    vecReg.add(INT_TBL_DAT_CAN_DIS,     null); 
                    vecReg.add(INT_TBL_DAT_CAN_MIN_ORI,     null); 
                    vecReg.add(INT_TBL_DAT_CAN_MAX_ORI,     null); 
                    vecReg.add(INT_TBL_DAT_STK_ACT,         rst.getString("nd_dis"));
                    vecReg.add(INT_TBL_DAT_CAN_MIN_DES,     rst.getString("nd_minDes"));      
                    vecReg.add(INT_TBL_DAT_CAN_MAX_DES,     rst.getString("nd_maxDes"));                     
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MIN,     rst.getString("nd_repMin"));  
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MIN,     rst.getString("nd_repMinPes"));  
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MED,     rst.getString("nd_repMed"));  
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MED,     rst.getString("nd_repMedPes"));                      
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_MAX,     rst.getString("nd_repMax"));  
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL_PES_MAX,     rst.getString("nd_repMaxPes"));                                          
                    vecReg.add(INT_TBL_DAT_CAN_REP_CAL,     rst.getString("nd_canRepCal"));
                    vecReg.add(INT_TBL_DAT_CAN_REP_SEL,         "" + rst.getString("nd_canRepSel"));
                    vecReg.add(INT_TBL_DAT_CAN_REP_DIS,         null);
                    vecReg.add(INT_TBL_DAT_CAN_COB_PIS,         "" + rst.getString("nd_cobPis"));
                    vecReg.add(INT_TBL_DAT_CAN_COB_PRO,         "" + rst.getString("nd_cobPro"));
                    vecReg.add(INT_TBL_DAT_CAN_COB_SUC,         "" + rst.getString("nd_cobSuc"));
                    vecReg.add(INT_TBL_DAT_PES,            rst.getString("nd_pesKg"));
                    vecReg.add(INT_TBL_DAT_PES_TOT_KGR,      "" + rst.getString("nd_pesTotKg"));
                    vecReg.add(INT_TBL_DAT_STK_MIN,         null);
                    
                    vecReg.add(INT_TBL_DAT_MIN,     null);
                    vecReg.add(INT_TBL_DAT_MAX,     null);
                    vecReg.add(INT_TBL_DAT_UNI_MED,     null); 
                    vecReg.add(INT_TBL_DAT_TRA,     rst.getString("nd_tra"));
                    vecReg.add(INT_TBL_DAT_FEC_ULT_REP,     rst.getString("fe_ultRep"));      
     
                    vecReg.add(INT_TBL_DAT_UBI,     null);      
                    
                            
                    
                    

                    

                  
                if (strAux!=null)
                        vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK);
                    vecDat.add(vecReg);
                }
                
////                    ne_canTotSel = getNumeroRegistrosChequeados();
////                    txtCanTot.setText(String.valueOf(ne_canTotSel));                
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                
                //Asignar vectores al modelo.
                objTblModRep.setData(vecDat);
                tblDat.setModel(objTblModRep);
                vecDat.clear();
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
     * Esta función permite cargar el detalle de las transferencias que se realizaron en cada empresa a través del proceso reposición.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTrsReg()
    {
        boolean blnRes=true;
        try
        {
            objTblModRepTrs.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a3.ne_numDoc, a3.fe_doc, a4.tx_desCor";
            
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConCnf, intIndiceConCnf, INT_ARL_CON_COD_DOC) + "";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatTrs.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecRegTrs=new Vector();
                    vecRegTrs.add(INT_TBL_DAT_TRS_LIN,              "");
                    vecRegTrs.add(INT_TBL_DAT_TRS_CHK,              null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_EMP,          "" + rst.getString("co_emp"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_LOC,          "" + rst.getString("co_loc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_TIP_DOC,      "" + rst.getString("co_tipDoc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_DES_COR_TIP_DOC,  "" + rst.getString("tx_desCor"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_DOC,          "" + rst.getString("co_doc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_NUM_DOC,          "" + rst.getString("ne_numDoc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_FEC_DOC,          "" + rst.getString("fe_doc"));
                    vecRegTrs.add(INT_TBL_DAT_TRS_BUT,              null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_BOD_ORI,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ITM,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ITM_MAE,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_ALT_ITM,      null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_NOM_ITM,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_UNI_MED,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_CAN_REP,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_ACT,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_MIN,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_STK_DIS,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COS_UNI,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_PRE_UNI,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COS_TOT,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_PES_GRP,          null);
                    vecRegTrs.add(INT_TBL_DAT_TRS_COD_BOD_DES,      null);
                    vecDatTrs.add(vecRegTrs);

                    if(blnChnEstImp)
                    {
                        vecDocSinPnd.add(rst.getInt("co_emp")+"-"+ rst.getInt("co_loc")+"-"+rst.getInt("co_tipDoc")+"-"+rst.getInt("co_doc"));
                    }
                }

                rst.close();
                stm.close();
                con.close();
                con=null;
                rst=null;
                stm=null;
                //Asignar vectores al modelo.
                objTblModRepTrs.setData(vecDatTrs);
                tblTrs.setModel(objTblModRepTrs);
                vecDatTrs.clear();
                objTooBar.setMenSis("Listo");
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




    private boolean abreTransferencia(int fila)
    {
        boolean blnRes=true;
        try
        {
            invocarClase("Compras.ZafCom20.ZafCom20");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean invocarClase(String clase)
    {
        int intFilSel;
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objParCla[]=new Class[1];
            objParCla[0]=objParSis.getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objParCla);
            intFilSel=tblTrs.getSelectedRow();
            //Inicializar el constructor que se obtuvo.
            Object objValCla[]=new Object[1];
            objValCla[0]=objParSis;
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objValCla);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
            //Obtener el método que se desea invocar.
            Class objParMet[]=new Class[4];
            objParMet[0]=new Integer(0).getClass();
            objParMet[1]=new Integer(0).getClass();
            objParMet[2]=new Integer(0).getClass();
            objParMet[3]=new Integer(0).getClass();
            java.lang.reflect.Method objMet=ifrVen.getClass().getMethod("cargarDocumento", objParMet);
            //Ejecutar el método que se obtuvo.
            Object objValMet[]=new Object[4];
            objValMet[0]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_EMP).toString());
            objValMet[1]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_LOC).toString());
            objValMet[2]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_TIP_DOC).toString());
            objValMet[3]=new Integer(objTblModRepTrs.getValueAt(intFilSel,INT_TBL_DAT_TRS_COD_DOC).toString());
            objMet.invoke(ifrVen, objValMet);
        }
        catch (ClassNotFoundException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean inactivarCampos()
    {
        boolean blnRes=true;
        try{
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodBodOri.setEditable(false);
            txtNomBodOri.setEditable(false);
            butBodOri.setEnabled(false);
            txtCodBodDes.setEditable(false);
            txtNomBodDes.setEditable(false);
            butBodDes.setEnabled(false);
            txtCodDoc.setEditable(false);
            butCarItm.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtPesDis.setEditable(false);
            txtPesTot.setEditable(false);
            ///////////
            optPorVar.setEnabled(false);
            optItmApl.setEnabled(false);
            ///////// José Mario Marín M. 18/07/2014
            objTblModRepTrs.setModoOperacion(objTblModRepTrs.INT_TBL_EDI);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean isBodOri()
    {
        if (txtCodBodOri.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">bodega origen</FONT> es obligatorio.<BR>Escriba o seleccione la bodega origen y vuelva a intentarlo.</HTML>");
            txtNomBodOri.requestFocus();
            return false;
        }
        return true;
    }


    BigDecimal bdeCanRepSel = new BigDecimal("0");
    BigDecimal bdePesUniItm = new BigDecimal("0");
    BigDecimal bdePesTotItm = new BigDecimal("0");
    BigDecimal bdePesTotRep = new BigDecimal("0");
    private boolean calcularPesTotRep()
    {
        boolean blnRes=false;
        try
        {
            bdePesTotRep=new BigDecimal("0");
            for(int i=0; i<objTblModRep.getRowCountTrue();i++)
            {
                if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                {
                   bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString().equals("")?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                   bdePesUniItm=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
                   bdePesTotItm=bdeCanRepSel.multiply(bdePesUniItm);
                   objTblModRep.setValueAt(bdePesTotItm, i, INT_TBL_DAT_PES_TOT_KGR);
                   bdePesTotRep=bdePesTotRep.add(bdePesTotItm);
                   //System.out.println("bdePesTotRep: "+bdePesTotRep+ " i = "+i);
                   blnRes=true;
                }
            }
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdePesTotRep,objParSis.getDecimalesMostrar()));
            strPesTotKgrSolTra = txtPesTot.getText();
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
private boolean calcularCobertura()
{
    boolean blnRes = false;

    final BigDecimal BDE_CERO = BigDecimal.ZERO;
    final int INT_DECIMALES = 4;

    try
    {
        for (int i = 0; i < objTblModRep.getRowCountTrue(); i++)
        {
            if (objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
            {
                BigDecimal bdeDisponible = obtenerBigDecimal(
                    objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)
                );

                BigDecimal bdeMaximo = obtenerBigDecimal(
                    objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_MAX_DES)
                );

                BigDecimal bdeCanRepSel = obtenerBigDecimal(
                    objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)
                );

                BigDecimal bdeCobPis = BDE_CERO;
                BigDecimal bdeCobPro = BDE_CERO;
                BigDecimal bdeCobSuc = BDE_CERO;

                /*
                 * Solo se realizan los cálculos cuando el máximo
                 * es mayor que cero.
                 *
                 * Con esto se evita:
                 * - División para cero.
                 * - División para números negativos.
                 * - ArithmeticException por decimales infinitos.
                 */
                if (bdeMaximo.compareTo(BDE_CERO) > 0)
                {
                    bdeCobPis = bdeDisponible.divide(
                        bdeMaximo,
                        INT_DECIMALES,
                        RoundingMode.HALF_UP
                    );

                    bdeCobPro = bdeCanRepSel.divide(
                        bdeMaximo,
                        INT_DECIMALES,
                        RoundingMode.HALF_UP
                    );

                    bdeCobSuc = bdeDisponible
                        .add(bdeCanRepSel)
                        .divide(
                            bdeMaximo,
                            INT_DECIMALES,
                            RoundingMode.HALF_UP
                        );
                }

                objTblModRep.setValueAt(
                    bdeCobPis,
                    i,
                    INT_TBL_DAT_CAN_COB_PIS
                );

                objTblModRep.setValueAt(
                    bdeCobPro,
                    i,
                    INT_TBL_DAT_CAN_COB_PRO
                );

                objTblModRep.setValueAt(
                    bdeCobSuc,
                    i,
                    INT_TBL_DAT_CAN_COB_SUC
                );

                blnRes = true;
            }
        }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }

    return blnRes;
}

private void calcularCobertura(int indice)
{
    boolean blnRes = false;

    final BigDecimal BDE_CERO = BigDecimal.ZERO;
    final int INT_DECIMALES = 4;

    try
    {
            if (objTblModRep.isChecked(indice, INT_TBL_DAT_CHK))
            {
                BigDecimal bdeDisponible = obtenerBigDecimal(
                    objTblModRep.getValueAt(indice, INT_TBL_DAT_STK_ACT)
                );

                BigDecimal bdeMaximo = obtenerBigDecimal(
                    objTblModRep.getValueAt(indice, INT_TBL_DAT_CAN_MAX_DES)
                );

                BigDecimal bdeCanRepSel = obtenerBigDecimal(
                    objTblModRep.getValueAt(indice, INT_TBL_DAT_CAN_REP_SEL)
                );

                BigDecimal bdeCobPis = BDE_CERO;
                BigDecimal bdeCobPro = BDE_CERO;
                BigDecimal bdeCobSuc = BDE_CERO;

                /*
                 * Solo se realizan los cálculos cuando el máximo
                 * es mayor que cero.
                 *
                 * Con esto se evita:
                 * - División para cero.
                 * - División para números negativos.
                 * - ArithmeticException por decimales infinitos.
                 */
                if (bdeMaximo.compareTo(BDE_CERO) > 0)
                {
                    bdeCobPis = bdeDisponible.divide(
                        bdeMaximo,
                        INT_DECIMALES,
                        RoundingMode.HALF_UP
                    );

                    bdeCobPro = bdeCanRepSel.divide(
                        bdeMaximo,
                        INT_DECIMALES,
                        RoundingMode.HALF_UP
                    );

                    bdeCobSuc = bdeDisponible
                        .add(bdeCanRepSel)
                        .divide(
                            bdeMaximo,
                            INT_DECIMALES,
                            RoundingMode.HALF_UP
                        );
                }

                objTblModRep.setValueAt(
                    bdeCobPis,
                    indice,
                    INT_TBL_DAT_CAN_COB_PIS
                );

                objTblModRep.setValueAt(
                    bdeCobPro,
                    indice,
                    INT_TBL_DAT_CAN_COB_PRO
                );

                objTblModRep.setValueAt(
                    bdeCobSuc,
                    indice,
                    INT_TBL_DAT_CAN_COB_SUC
                );

                blnRes = true;
            }
    }
    catch (Exception e)
    {
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
}


private BigDecimal obtenerBigDecimal(Object objValor)
{
    if (objValor == null)
    {
        return BigDecimal.ZERO;
    }

    if (objValor instanceof BigDecimal)
    {
        return (BigDecimal) objValor;
    }

    if (objValor instanceof Number)
    {
        return new BigDecimal(objValor.toString());
    }

    String strValor = objValor.toString().trim();

    if (strValor.isEmpty())
    {
        return BigDecimal.ZERO;
    }

    /*
     * Permite valores con coma decimal, por ejemplo:
     * 10,50
     */
    if (strValor.contains(",") && !strValor.contains("."))
    {
        strValor = strValor.replace(",", ".");
    }

    try
    {
        return new BigDecimal(strValor);
    }
    catch (NumberFormatException e)
    {
        return BigDecimal.ZERO;
    }
}

    
   
   private boolean obtenerListaOrigen() {
	 
    boolean blnRes = false;
    try {
		 
        listaDatosOri.clear();
        for (int i = 0; i < objTblModRep.getRowCountTrue(); i++) {
            if (objTblModRep.isChecked(i, INT_TBL_DAT_CHK)) {
                Map<String, Object> fila = new HashMap<>();

                fila.put("ITEM_GP", objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                fila.put("ITEM_3L", objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS));
                fila.put("DESCRIPCION", objTblModRep.getValueAt(i, INT_TBL_DAT_NOM_ITM));
                fila.put("UNIDAD", objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED));
                fila.put("ABC_TUV", objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP));

                // Formatear los valores numéricos a dos decimales
                fila.put("DISPONIBLE_ORI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_DIS)));
                fila.put("MINIMO_ORI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)));
                fila.put("DISPONIBLE_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)));
                fila.put("MINIMO_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_MIN)));
                fila.put("MAXIMO_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_MAX)));
                fila.put("REP_CALCULADA", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL)));
                fila.put("REP_ENVIADA", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)));
                fila.put("REP_DIS", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_DIS)));
                fila.put("PES_UNI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)));
                fila.put("PES_TOT", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES_TOT_KGR)));
                double dblCanRepSel = Double.parseDouble(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                double dblCanRepCal = Double.parseDouble(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString());
                double dblPorCum = 0.00;
                if (dblCanRepCal != 0)
                    dblPorCum = (dblCanRepSel / dblCanRepCal) * 100;
                fila.put("POR_CUM", formatDecimal(dblCanRepSel)+"/"+ formatDecimal(dblCanRepCal) + "="+ formatDecimal(dblPorCum)+ " %");                              

                listaDatosOri.add(fila);
                blnRes = true;
            }
        }
						  
		 
											 
						 
		 
    } catch (Exception e) {
		 
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    return blnRes;
}
   
private boolean cargarComboABC()
{
    boolean blnRes = true;
    java.sql.Connection conInt;    
    java.sql.Statement stmInt;
    java.sql.ResultSet rstInt;    

    try
    {
        conInt = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            // Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT trim(tx_nivpar) as nivel \n";
            strSQL += " FROM Dynamics2021..tbm_nivpar \n";
            //strSQL += " WHERE LEN(TRIM(tx_nivpar)) = 2 and st_reg='A' \n";
            strSQL += " WHERE st_reg='A' \n";            
            strSQL += " ORDER BY tx_nivpar asc ";

            rstInt = stmInt.executeQuery(strSQL);

            // Limpiar combo antes de cargarlo.
            cmb_abcCapCar.removeAllItems();
            cmb_abcTipEmi.removeAllItems();

            // Obtener registros.
            while (rstInt.next())
            {
                cmb_abcCapCar.addItem(rstInt.getString("nivel"));
                cmb_abcTipEmi.addItem(rstInt.getString("nivel"));
            }

            rstInt.close();
            stmInt.close();
            conInt.close();

            conInt = null;
            rstInt = null;
            stmInt = null;
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

private boolean cargarCfgCapCar(int tipoCon,String tipABC)
{
    boolean blnRes = true;
    tipoCon=1;

    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;
    java.sql.ResultSet rstInt = null;

    try
    {
        // Limpiar tabla
        objTblModRepCapCar.removeAllRows();

        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";

            if (tipoCon == 1)
            {
                strSQL += " SELECT cnf.ne_tip,cnf.co_tipniv, cat.tx_catpar,cnf.co_nivpar,niv.tx_nivpar \n";
                strSQL += " FROM Dynamics2021..tbm_cfgcapcar  as cnf  ";
                strSQL += "     INNER JOIN Dynamics2021..tbm_nivpar as niv on (cnf.co_nivpar = niv.co_nivpar)  ";                
                strSQL += "     INNER JOIN Dynamics2021..tbm_catpar as cat on (cnf.co_tipniv = cat.co_catpar)  ";                                
                strSQL += " WHERE cnf.st_reg ='A' and cat.tx_catpar = '"+ tipABC +"' ";
                strSQL += " ORDER BY niv.tx_nivpar ASC ";
            }

            System.out.println("cargarCfgCapCar: " + strSQL);

            rstInt = stmInt.executeQuery(strSQL);

            // Limpiar vector
            vecDatCapCar.clear();

            while (rstInt.next())
            {
                vecRegCapCar = new Vector();
                

                vecRegCapCar.add(INT_TBL_DAT_CAP_CAR_LIN,"" );
                vecRegCapCar.add(INT_TBL_DAT_CAP_CAR_CAT_NIV,rstInt.getString("tx_catpar") );
                vecRegCapCar.add(INT_TBL_DAT_CAP_CAR_NIV,rstInt.getString("tx_nivpar") );


                vecDatCapCar.add(vecRegCapCar);
            }

            rstInt.close();
            rstInt = null;

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;

            // Asignar los datos al modelo
            objTblModRepCapCar.setData(vecDatCapCar);
            tblDatCapCar.setModel(objTblModRepCapCar);

            vecDatCapCar.clear();
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

private boolean cargarCfgTipEnv(int tipoCon,String tipABC)
{
    boolean blnRes = true;
    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;
    java.sql.ResultSet rstInt = null;

    try
    {
        // Limpiar tabla
        objTblModRepTipEmi.removeAllRows();

        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";


                strSQL += " SELECT cnf.co_tipniv, cat.tx_catpar,cnf.co_nivpar,niv.tx_nivpar \n";
                strSQL += " FROM Dynamics2021..tbm_cfgTipRep  as cnf  ";
                strSQL += "     INNER JOIN Dynamics2021..tbm_nivpar as niv on (cnf.co_nivpar = niv.co_nivpar)  ";                
                strSQL += "     INNER JOIN Dynamics2021..tbm_catpar as cat on (cnf.co_tipniv = cat.co_catpar)  ";                                
                strSQL += " WHERE cnf.st_reg ='A' and cat.tx_catpar = '"+ tipABC +"' and cnf.co_tiprep ="+ tipoCon +" ";
                strSQL += " ORDER BY niv.tx_nivpar ASC ";
    

            System.out.println("cargarCfgTipEnv: " + strSQL);

            rstInt = stmInt.executeQuery(strSQL);

            // Limpiar vector
            vecDatTipEmi.clear();

            while (rstInt.next())
            {
                vecRegTipEmi = new Vector();
                

                vecRegTipEmi.add(INT_TBL_DAT_TIP_EMI_LIN,"" );
                vecRegTipEmi.add(INT_TBL_DAT_TIP_EMI_CAT_NIV,rstInt.getString("tx_catpar") );
                vecRegTipEmi.add(INT_TBL_DAT_TIP_EMI_NIV,rstInt.getString("tx_nivpar") );


                vecDatTipEmi.add(vecRegTipEmi);
            }

            rstInt.close();
            rstInt = null;

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;

            // Asignar los datos al modelo
            objTblModRepTipEmi.setData(vecDatTipEmi);
            tblDatTipEmi.setModel(objTblModRepTipEmi);

            vecDatTipEmi.clear();
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

private boolean cargarComboCatABC()
{
    boolean blnRes = true;
    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;
    java.sql.ResultSet rstInt = null;

    try
    {
        // Evita que ActionPerformed haga consultas mientras se llena el combo
        blnCarCmbAbcCat = true;
        blnCarCmbAbcCatTipEmi = true;

        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";
            strSQL += " SELECT TRIM(tx_catpar) AS cat \n";
            strSQL += " FROM Dynamics2021..tbm_catpar \n";
            strSQL += " WHERE st_reg='A' \n";
            strSQL += " ORDER BY co_catpar ASC ";

            System.out.println("cargarComboCatABC: " + strSQL);

            rstInt = stmInt.executeQuery(strSQL);

            cmb_abcCat.removeAllItems();
            cmb_abcCatTipEmi.removeAllItems();

////////////////            while (rstInt.next())
////////////////            {
////////////////                cmb_abcCat.addItem(rstInt.getString("cat"));
////////////////                cmb_abcCatTipEmi.addItem(rstInt.getString("cat"));
////////////////            }
            cmb_abcCat.addItem("Tuval");
            cmb_abcCatTipEmi.addItem("Tuval");
            
            rstInt.close();
            rstInt = null;

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;
        }

        // Ya terminó completamente de cargar el combo
        blnCarCmbAbcCat = false;
        blnCarCmbAbcCatTipEmi = false;

        // Ahora sí cargamos la información del primer elemento
        if (cmb_abcCat.getSelectedItem() != null)
        {
            String strItmAbcCat = cmb_abcCat.getSelectedItem().toString();

            System.out.println("Categoría inicial: " + strItmAbcCat);

            cargarCfgCapCar(1, strItmAbcCat);
        }
        //25082026
        // Ahora sí cargamos la información del primer elemento
        if (cmb_abcCatTipEmi.getSelectedItem() != null)
        {
            String strItmAbcCatTipEmi = cmb_abcCatTipEmi.getSelectedItem().toString();

            System.out.println("Categoría inicial: " + strItmAbcCatTipEmi);

////            cargarCfgCapCar(1, strItmAbcCatTipEmi);
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
    finally
    {
        // Muy importante: dejar habilitados los eventos
        blnCarCmbAbcCat = false;

        try
        {
            if (rstInt != null)
                rstInt.close();

            if (stmInt != null)
                stmInt.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}

private boolean cargarComboCatTipEmiABC()
{
    boolean blnRes = true;
    java.sql.Connection conInt = null;
    java.sql.Statement stmInt = null;
    java.sql.ResultSet rstInt = null;

    try
    {
        // Evita que ActionPerformed haga consultas mientras se llena el combo
        blnCarCmbAbcCatTipEmi = true;

        conInt = DriverManager.getConnection(
                objParSis.getStringConexion_Dynamics2021(),
                objParSis.getUsuarioBaseDatos_Dynamics2021(),
                objParSis.getClaveBaseDatos_Dynamics2021()
        );

        if (conInt != null)
        {
            stmInt = conInt.createStatement();

            strSQL = "";
            strSQL += " SELECT TRIM(tx_catpar) AS cat \n";
            strSQL += " FROM Dynamics2021..tbm_catpar \n";
            strSQL += " WHERE st_reg='A' \n";
            strSQL += " ORDER BY co_catpar ASC ";

            System.out.println("cargarComboCatABC: " + strSQL);

            rstInt = stmInt.executeQuery(strSQL);

            cmb_abcCatTipEmi.removeAllItems();

////////            while (rstInt.next())
////////            {
////////                cmb_abcCatTipEmi.addItem(rstInt.getString("cat"));
////////            }
            cmb_abcCatTipEmi.addItem("Tuval");
            rstInt.close();
            rstInt = null;

            stmInt.close();
            stmInt = null;

            conInt.close();
            conInt = null;
        }

        // Ya terminó completamente de cargar el combo
        blnCarCmbAbcCatTipEmi = false;

    
        //25082026
        // Ahora sí cargamos la información del primer elemento
        if (cmb_abcCatTipEmi.getSelectedItem() != null)
        {
            String strItmAbcCatTipEmi = cmb_abcCatTipEmi.getSelectedItem().toString();

            System.out.println("Categoría inicial: " + strItmAbcCatTipEmi);

////            cargarCfgCapCar(1, strItmAbcCatTipEmi);
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
    finally
    {
        // Muy importante: dejar habilitados los eventos
        blnCarCmbAbcCatTipEmi = false;

        try
        {
            if (rstInt != null)
                rstInt.close();

            if (stmInt != null)
                stmInt.close();

            if (conInt != null)
                conInt.close();
        }
        catch (Exception e)
        {
        }
    }

    return blnRes;
}


   
    private String formatDecimal(Object value) {
    if (value == null) return "0.00";
    try {
        double number = Double.parseDouble(value.toString());
        return String.format("%.2f", number);
    } catch (NumberFormatException e) {
        return "0.00";
    }
}	
    
 
private boolean obtenerListaFinal() {
	 
    boolean blnRes = false;
    try {
		 
			
        listaDatosFin.clear();
        for (int i = 0; i < objTblModRep.getRowCountTrue(); i++) 
        {
            if (objTblModRep.isChecked(i, INT_TBL_DAT_CHK)) 
            {
                Map<String, Object> fila = new HashMap<>();
                fila.put("ITEM_GP", objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                fila.put("ITEM_3L", objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS));
                fila.put("DESCRIPCION", objTblModRep.getValueAt(i, INT_TBL_DAT_NOM_ITM));
                fila.put("UNIDAD", objTblModRep.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED));
                fila.put("ABC_TUV", objTblModRep.getValueAt(i, INT_TBL_DAT_ITM_TIP));
                fila.put("DISPONIBLE_ORI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_DIS)));
                fila.put("MINIMO_ORI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_MIN)));
                fila.put("DISPONIBLE_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_STK_ACT)));
                fila.put("MINIMO_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_MIN)));
                fila.put("MAXIMO_DES", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_MAX)));
                fila.put("REP_CALCULADA", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL)));
                fila.put("REP_ENVIADA", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)));
                fila.put("REP_DIS", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_DIS)));
                fila.put("PES_UNI", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)));
                fila.put("PES_TOT", formatDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES_TOT_KGR)));
                double dblCanRepSel = Double.parseDouble(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
                double dblCanRepCal = Double.parseDouble(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_CAL).toString());
                double dblPorCum = 0.00;
                if (dblCanRepCal != 0)
                    dblPorCum = (dblCanRepSel / dblCanRepCal) * 100;
                fila.put("POR_CUM", formatDecimal(dblCanRepSel)+"/"+ formatDecimal(dblCanRepCal) + "="+ formatDecimal(dblPorCum)+ " %");                
                listaDatosFin.add(fila);
                blnRes = true;
            }
        }
						  
		 
											 
						 
		 
    } catch (Exception e) {
		 
        objUti.mostrarMsgErr_F1(this, e);
        blnRes = false;
    }
    return blnRes;
}
      
    
    
////private List<Map<String, Object>> obtenerListaDiferenciasRepSel() 
////{
////    boolean blnExi=false;
////    
////    try {
////        listaDiferencias.clear();
////        for (Map<String, Object> filaOri : listaDatosOri) {
////            String itemNumOri = (filaOri.get("ITEM_GP") != null) ? filaOri.get("ITEM_GP").toString() : null;
////            if (itemNumOri == null) continue;
////
////            // Buscar el ítem correspondiente en listaDatosFin
////            Map<String, Object> filaFin = listaDatosFin.stream()
////                .filter(f -> itemNumOri.equals(f.get("ITEM_GP")))
////                .findFirst()
////                .orElse(null);
////
////            if (filaFin == null) {
////                // Caso 1: El ítem no existe en listaDatosFin
////                listaDiferencias.add(filaOri);
////                blnExi=true;
////            } else {
////                // Caso 2: El ítem existe en ambas listas, comparar REP_SEL
////                Object repSelOri = filaOri.get("REP_ENVIADA");
////                Object repSelFin = filaFin.get("REP_ENVIADA");
////
////                // Convertir a números para comparación segura (evita nulls)
////                double valOri = (repSelOri != null) ? Double.parseDouble(repSelOri.toString()) : 0;
////                double valFin = (repSelFin != null) ? Double.parseDouble(repSelFin.toString()) : 0;
////
////                if (Double.compare(valOri, valFin) != 0) {
////                    listaDiferencias.add(filaFin);
////                    blnExi=true;
////                    
////                }
////            }
////        }
////        if (blnExi)
////            htmlGlo =generarHtmlDesdeLista(listaDiferencias);
////    } catch (Exception e) {
////        objUti.mostrarMsgErr_F1(this, e);
////    }
////    return listaDiferencias;
////}
////////
////////private StringBuilder generarHtmlDesdeLista(List<Map<String, Object>> lista) {
////////    StringBuilder html = new StringBuilder();
////////    html.append("<html>");
////////    html.append("<head><style>");
////////    html.append("table { border-collapse: collapse; width: 100%; font-family: Arial; font-size: 12px; }");
////////    html.append("th, td { border: 1px solid black; padding: 4px; text-align: left; }");
////////    html.append("th { background-color: #f2f2f2; }");
////////    html.append(".centered { text-align: center; }");
////////    html.append("</style></head>");
////////    html.append("<body>");
////////    html.append("<h3>Diferencias encontradas</h3>");
////////    html.append("<table>");
////////
////////    // Primera fila de encabezados agrupados
////////    html.append("<tr>");
////////    html.append("<th rowspan='2'>ITEM GP</th>");
////////    html.append("<th rowspan='2'>ITEM 3L</th>");
////////    html.append("<th rowspan='2'>DESCRIPCION</th>");
////////    //html.append("<th colspan='2' class='centered'>ORIGEN</th>");
////////    html.append("<th colspan='2' class='centered'><b>BOD. ORIGEN: ")
////////        .append(txtCodBodOri.getText())
////////        .append("</b></th>");    
////////    html.append("<th colspan='2' class='centered'><b>BOD. DESTINO: ")
////////        .append(txtCodBodDes.getText())
////////        .append("</b></th>");      
////////    //html.append("<th colspan='2' class='centered'>DESTINO</th>");
////////    html.append("<th colspan='4' class='centered'>REPOSICION</th>");
////////    html.append("</tr>");
////////
////////    // Segunda fila de subencabezados
////////    html.append("<tr>");
////////    html.append("<th>DISPONIBLE</th>");
////////    html.append("<th>MINIMO</th>");
////////    html.append("<th>DISPONIBLE</th>");
////////    html.append("<th>MINIMO</th>");
////////    html.append("<th>CANT. SUGERIDA</th>");
////////    html.append("<th>PESO SUGERIDO</th>");
////////    html.append("<th>CANT. ENVIADA</th>");
////////    html.append("<th>PESO ENVIADO</th>");
////////    html.append("</tr>");
////////
////////    // Filas de datos
////////    for (Map<String, Object> fila : lista) {
////////        html.append("<tr>");
////////        html.append("<td>").append(fila.getOrDefault("ITEM_GP", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("ITEM_3L", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("DESCRIPCION", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("DISPONIBLE_ORI", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("MINIMO_ORI", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("DISPONIBLE_DES", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("MINIMO_DES", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("REP_CALCULADA", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("PES_TOT", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("CANT_ENVIADA", "")).append("</td>");
////////        html.append("<td>").append(fila.getOrDefault("PESO_ENVIADO", "")).append("</td>");
////////        html.append("</tr>");
////////    }
////////
////////    html.append("</table>");
////////    html.append("</body></html>");
////////
////////    return html;
////////}  

private StringBuilder generarHtmlDesdeLista(List<Map<String, Object>> lista) {
    StringBuilder html = new StringBuilder();
    int contador =0;
    html.append("<html>");
    html.append("<head><style>");
    html.append("table { border-collapse: collapse; width: 100%; font-family: Arial; font-size: 12px; }");
    html.append("th, td { border: 1px solid black; padding: 4px; text-align: left; }");
    html.append("th { background-color: #f2f2f2; }");
    html.append(".centered { text-align: center; }");
    html.append("</style></head>");
    html.append("<body>");
    html.append("<h3>Diferencias encontradas</h3>");
    html.append("<table>");

    // Cabeceras
    html.append("<tr>");
    html.append("<th rowspan='2'><b>REG.</b></th>");
    html.append("<th rowspan='2'><b>ITEM GP</b></th>");
    html.append("<th rowspan='2'><b>ITEM 3L</b></th>");
    html.append("<th rowspan='2'><b>DESCRIPCION</b></th>");
    html.append("<th rowspan='2'><b>UNIDAD</b></th>");
    html.append("<th rowspan='2'><b>ABC</b></th>");
    html.append("<th colspan='2' class='centered'><b>BOD. ORIGEN: ").append(txtCodBodOri.getText()).append("</b></th>");
    html.append("<th colspan='3' class='centered'><b>BOD. DESTINO: ").append(txtCodBodDes.getText()).append("</b></th>");
    html.append("<th colspan='5' class='centered'><b>REPOSICION</b></th>");
    html.append("</tr>");

    html.append("<tr>");
    html.append("<th><b>DISPONIBLE</b></th>");
    html.append("<th><b>MINIMO</b></th>");
    html.append("<th><b>DISPONIBLE</b></th>");
    html.append("<th><b>MINIMO</b></th>");
    html.append("<th><b>MAXIMO</b></th>");
    html.append("<th><b>CANT. SUGERIDA</b></th>");
    html.append("<th><b>PESO SUGERIDO</b></th>");
    html.append("<th><b>CANT. ENVIADA</b></th>");
    html.append("<th><b>PESO ENVIADO</b></th>");
    html.append("<th><b>POR.CUMP.</b></th>");
    html.append("</tr>");

    // Totales acumulados
    BigDecimal totalSug = BigDecimal.ZERO;
    BigDecimal totalPesoSug = BigDecimal.ZERO;
    BigDecimal totalEnv = BigDecimal.ZERO;
    BigDecimal totalPesoEnv = BigDecimal.ZERO;

    // Filas de datos
    for (Map<String, Object> fila : lista) {
        contador++;
        BigDecimal repEnviada = toBigDecimal(fila.get("REP_ENVIADA"));
        BigDecimal cantEnviada = toBigDecimal(fila.get("CANT_ENVIADA"));
        BigDecimal porCum = repEnviada.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO.setScale(2) : cantEnviada.divide(repEnviada, 2, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP);

////        boolean esDiferente = repEnviada.compareTo(cantEnviada) != 0;
////
////        if (esDiferente) {
////            html.append("<tr style='background-color: #ffff99;'>");
////        } else {
////            html.append("<tr>");
////        }      
        String colorFondo;
        //BigDecimal porcentajeFaltante;
       // BigDecimal diferencia = repEnviada.subtract(cantEnviada).abs();
       if (cantEnviada.equals(new BigDecimal("0.00")))
           colorFondo = "#ff4d4d"; 
       else
       {
            
          //  porcentajeFaltante = ((new BigDecimal("100")).multiply(cantEnviada)).divide(repEnviada); 
            BigDecimal porcentajeFaltante = (new BigDecimal("100"))
                                            .multiply(cantEnviada)
                                            .divide(repEnviada, 2, RoundingMode.HALF_UP); // 2 decimales
            if (porcentajeFaltante.compareTo(new BigDecimal("0")) >= 0 &&
                porcentajeFaltante.compareTo(new BigDecimal("30")) <= 0) {
                colorFondo = "#ff4d4d"; // rojo
            } else if (porcentajeFaltante.compareTo(new BigDecimal("30")) > 0 &&
                       porcentajeFaltante.compareTo(new BigDecimal("50")) <= 0) {
                colorFondo = "#ff9933"; // anaranjado
            } else if (porcentajeFaltante.compareTo(new BigDecimal("50")) > 0 &&
                       porcentajeFaltante.compareTo(new BigDecimal("99")) <= 0) {
                colorFondo = "#ffff99"; // amarillo sol
            } else {
                colorFondo = ""; // sin color para otros casos (ej. valores negativos inesperados)
            }            
       }           
           

																	   
										

////////        if (repEnviada.compareTo(BigDecimal.ZERO) == 0) {
////////            porcentajeFaltante = BigDecimal.ZERO; // evitar división por cero
////////        } else {
////////            porcentajeFaltante = diferencia
////////                .divide(repEnviada, 4, RoundingMode.HALF_UP)
////////                .multiply(new BigDecimal("100"));
////////        }

// Determinar el color según el porcentaje FALTANTE
       

																		
										   
																			  
																			   
												 
																			  
																			  
												   
				
														  
		 
        // Agregar la fila HTML
        if (!colorFondo.isEmpty()) {
            html.append("<tr style='background-color: ").append(colorFondo).append(";'>");
        } else {
            html.append("<tr>");
        }

        //html.append("<tr>");
        html.append("<td>").append(contador).append("</td>");
        html.append("<td>").append(fila.getOrDefault("ITEM_GP", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("ITEM_3L", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("DESCRIPCION", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("UNIDAD", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("ABC_TUV", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("DISPONIBLE_ORI", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("MINIMO_ORI", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("DISPONIBLE_DES", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("MINIMO_DES", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("MAXIMO_DES", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("REP_ENVIADA", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("PES_TOT", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("CANT_ENVIADA", "")).append("</td>");
        html.append("<td>").append(fila.getOrDefault("PESO_ENVIADO", "")).append("</td>");
        //html.append("<td>").append(porCum).append("</td>");
         //html.append("<td>").append(cantEnviada +"/"+ repEnviada +"=" + porCum).append("</td>");
         html.append("<td>").append(porCum +"%").append("</td>");
        //html.append("<td>").append(porCum + " %").append("</td>");
        html.append("</tr>");

        // Acumular totales (conversión segura a BigDecimal)
        totalSug = totalSug.add(toBigDecimal(fila.get("REP_ENVIADA")));
        totalPesoSug = totalPesoSug.add(toBigDecimal(fila.get("PES_TOT")));
        totalEnv = totalEnv.add(toBigDecimal(fila.get("CANT_ENVIADA")));
        totalPesoEnv = totalPesoEnv.add(toBigDecimal(fila.get("PESO_ENVIADO")));
    }

//////////    html.append("<tr>");
//////////    html.append("<td colspan='8'><b>TOTAL</b></td>");
//////////    html.append("<td colspan='9'></td>"); // Columnas vacías para origen y destino
//////////    html.append("<td><b>").append(totalSug).append("</b></td>");
//////////    html.append("<td><b>").append(totalPesoSug).append("</b></td>");
//////////    html.append("<td><b>").append(totalEnv).append("</b></td>");
//////////    html.append("<td><b>").append(totalPesoEnv).append("</b></td>");
//////////    html.append("</tr>"); 

        // Fila de totales
        html.append("<tr style='background-color:#d9edf7;'>"); // color celeste opcional
        html.append("<td colspan='11'><b>TOTAL</b></td>"); // primeras 11 columnas (REG a MINIMO_DESTINO)
        html.append("<td><b>").append(totalSug).append("</b></td>");       // CANT. SUGERIDA
        html.append("<td><b>").append(totalPesoSug).append("</b></td>");   // PESO SUGERIDO
        html.append("<td><b>").append(totalEnv).append("</b></td>");       // CANT. ENVIADA
        html.append("<td><b>").append(totalPesoEnv).append("</b></td>");   // PESO ENVIADO
        html.append("<td><b>").append("").append("</b></td>");  
        html.append("</tr>");
    html.append("</table>");
////////    if (blnTotItm) 
////////    {
////////        html.append("<p><b>SE TIENE ITEMS FALTANTESDE LA REPOSICION:</b> ").append(intNumDoc)
////////            .append(" REALIZADA DESDE LA BODEGA: ").append(txtCodBodOri.getText())
////////            .append(" HACIA LA BODEGA: ").append(txtCodBodDes.getText())
////////            .append(" POR EL USUARIO: ").append(objParSis.getNombreUsuario()).append("</p>");
////////    }

//////    if (blnTotItm) 
//////    {
    html.append("<p><b>SE INGRESO UN PESO MAXIMO DE :</b> ").append(nd_pes)
        .append(" DESDE LA BODEGA:").append(txtCodBodOri.getText())
        .append(" HACIA LA BODEGA: ").append(txtCodBodDes.getText())
        .append(" POR EL USUARIO: ").append(objParSis.getNombreUsuario()).append("</p>");
    String txtTipSel="";
    if (rbMax.isSelected())
        txtTipSel = "MAXIMO";    
    if (rbMin.isSelected())
        txtTipSel = "MINIMO";   
    if (rbMed.isSelected())
        txtTipSel = "MEDIO";       
    html.append("<p><b>REPOSICION REALIZADA :</b> ").append(txtTipSel).append("</p>"); 
////    }
    
    html.append("</body></html>");

    return html;
}

// Conversión segura a BigDecimal
private BigDecimal toBigDecimal(Object value) {
    try {
        return new BigDecimal(value != null ? value.toString() : "0");
    } catch (Exception e) {
        return BigDecimal.ZERO;
    }
}



    private boolean getSumaTotalPeso(int fila)
    {
        boolean blnRes=false;
        BigDecimal bdePesTotSel=new BigDecimal("0");
        BigDecimal bdeCanRepSel=new BigDecimal("0");
        BigDecimal bdePesRepSel=new BigDecimal("0");
        BigDecimal bdePesRepDis=new BigDecimal("0");
        BigDecimal bdeSumPesTotPesSel=new BigDecimal("0");
        try{
            bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            bdePesRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_PES).toString());
            bdePesRepDis=new BigDecimal(txtPesDis.getText()==null?"0":(txtPesDis.getText().equals("")?"0":txtPesDis.getText())   );//txt usuario
            bdePesTotSel=new BigDecimal(txtPesTot.getText()==null?"0":(txtPesTot.getText().equals("")?"0":txtPesTot.getText())   );//txt sistema
            bdeSumPesTotPesSel=bdePesTotSel.add((bdeCanRepSel.multiply(bdePesRepSel)));//peso total txt sumado al registro seleccionado
            if(bdeSumPesTotPesSel.compareTo(bdePesRepDis)<=0){//si el peso total sumado al nuevo reg seleccionado es <= al ingresado por usuario
                //txtPesTot.setText("" + bdeSumPesTotPesSel);
                txtPesTot.setText("" + objUti.redondearBigDecimal(bdeSumPesTotPesSel,objParSis.getDecimalesMostrar()));

                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean getRestaTotalPeso(int fila)
    {
        boolean blnRes=false;
        BigDecimal bdeCanRepSel=new BigDecimal("0");//cantidad a reponer seleccionada
        BigDecimal bdePesRepSel=new BigDecimal("0");//peso a reponer seleccionado
        BigDecimal bdePesTotSel=new BigDecimal("0");//peso total del campo txt
        BigDecimal bdeSumPesTotPesSel=new BigDecimal("0");//total de peso (cantidad * peso) seleccionado
        try
        {
            bdeCanRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            bdePesRepSel=new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(fila, INT_TBL_DAT_PES).toString());
            bdePesTotSel=new BigDecimal(txtPesTot.getText()==null?"0":(txtPesTot.getText().equals("")?"0":txtPesTot.getText())   );//txt sistema
            bdeSumPesTotPesSel=bdePesTotSel.subtract((bdeCanRepSel.multiply(bdePesRepSel)));//peso total txt sumado al registro seleccionado
                //txtPesTot.setText("" + bdeSumPesTotPesSel);
            txtPesTot.setText("" + objUti.redondearBigDecimal(bdeSumPesTotPesSel,objParSis.getDecimalesMostrar()));
                blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean validaCantidad(int fila, BigDecimal bdeStkDis, BigDecimal bdeValCanRepBefEdi, BigDecimal bdeValCanRepAftEdi)
    {
        boolean blnRes=true;
        BigDecimal bdeStkFilSel=new BigDecimal("0");
        
        BigDecimal bdeCanRepMin=new BigDecimal("0");//cantidad a reponer menos 6 mts me da el rango inicial
        String strUniMed="";
        BigDecimal bdeStkMin=new BigDecimal("0");
        BigDecimal bdeStkAct=new BigDecimal("0");
        BigDecimal bdePorVarVal=new BigDecimal("0");
        bdePorVarGrl=new BigDecimal("1");
                    
        BigDecimal bdeA=new BigDecimal("0");//minimo * porcentaje variacion
        BigDecimal bdeB=new BigDecimal("0");//stockActual - A
        Double disponibleCalculada;
        Double dblStk=bdeStkDis.doubleValue();
        disponibleCalculada=objUti.parseDouble(objTblModRep.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CAN_REP_CAL));///  José Marín M. 16/Jul/2014
        strUniMed=objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED)==null?"":objTblModRep.getValueAt(fila, INT_TBL_DAT_DES_COR_UNI_MED).toString();  //José Marín M.
        try
        {
            bdeStkFilSel=objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL)==null?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_CAN_REP_SEL).toString());
            if(bdeValCanRepAftEdi.compareTo(new BigDecimal("0"))==0){
                
            }
            else{
                //System.out.println("validaCantidad");
                bdeStkMin=objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN)==null?new BigDecimal("0"):(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN).toString().equals("")?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_MIN).toString()));
                bdeStkAct=objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT)==null?new BigDecimal("0"):(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT).toString().equals("")?new BigDecimal("0"):new BigDecimal(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_ACT).toString()));
                bdePorVarVal=bdePorVarGrl;
                bdeA=bdeStkMin.multiply(bdePorVarVal);
                bdeB=bdeStkAct.subtract(bdeA);
                bdeB=bdeB.abs();
                bdeCanRepMin=bdeB.subtract(new BigDecimal("6"));
                if(strUniMed.equals("MTS")){
                    //System.out.println("MTS....");
                    if((bdeValCanRepAftEdi.compareTo(bdeCanRepMin)>=0) && (bdeValCanRepAftEdi.compareTo(bdeB)<=0)){
                    }
                    else{
//                        mostrarMsgInf("<HTML>La cantidad ingresada es mayor a la permitida.<BR>La cantidad permitida debe estar en un rango entre <FONT COLOR=\"blue\">" + objUti.redondearBigDecimal(bdeCanRepMin.abs(), objParSis.getDecimalesMostrar()) +" y " + objUti.redondearBigDecimal(bdeB, objParSis.getDecimalesMostrar()) + "</FONT><BR>Ingrese una cantidad entre ese rango y vuelva a intentarlo.</HTML>");
//                        objTblModRep.setValueAt(bdeValCanRepBefEdi, fila, INT_TBL_DAT_CAN_REP_SEL);/*Ingrid Lino: Solicitado por werner campoverde 19/Agosto/2016*/
                    }
                }
                if(bdeValCanRepAftEdi.compareTo(bdeStkDis)>0){
                    if(txtCodTipDoc.getText().equals(STR_COD_TIP_DOC_REINBD))
                        mostrarMsgInf("<HTML>La cantidad a reponer ingresada es mayor al stock disponible de grupo.<BR>Recuerde que el máximo valor a colocar es el stock disponible de grupo: " + objUti.redondear(objTblModRep.getValueAt(fila, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP).toString(), objParSis.getDecimalesMostrar()) + "</HTML>");
                    else
                        mostrarMsgInf("<HTML>La cantidad a reponer ingresada es mayor al stock disponible de grupo</HTML>");
                    if(dblStk>=disponibleCalculada){
                        objTblModRep.setValueAt(disponibleCalculada, fila, INT_TBL_DAT_CAN_REP_SEL);//se le da lo maximo calculado si es posible tener en stock 
                        objTblModRep.setValueAt(false, fila, INT_TBL_DAT_CHK);
                    }
                    else{
                        objTblModRep.setValueAt(dblStk, fila, INT_TBL_DAT_CAN_REP_SEL);//Se le da lo maximo en stock
                        objTblModRep.setValueAt(false, fila, INT_TBL_DAT_CHK);    
                    }
                    
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_cabTipDoc(Connection conLocal)
    {
        boolean blnRes=true;
        try
        {
            if (conLocal!=null)
            {
                stm=conLocal.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + intCodEmpGrp;
                strSQL+=" AND co_loc=" + intCodLocGrp;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
    
    
    public ArrayList getRepInvBod()
    {
        ArrayList arlReg, arlDat;
        arlReg = new ArrayList();
        arlDat = new ArrayList();
        arlReg.add(intCodEmpGrp);
        arlReg.add(intCodLocGrp);
        arlReg.add(txtCodTipDoc.getText());
        arlReg.add(txtCodDoc.getText());
        arlDat.add(arlReg);
        return arlDat;
    }
    
    
    private String strSql;
    
    /**
     * 
     * @param conExt
     * @return 
     */
    
    private boolean insertarCabecera(java.sql.Connection conExt,int contadorItems)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSqlCab="";
        String strNumMod="";
        try{
            if(conExt!=null){
                strNumMod = String.format("%015d", intNumDoc);
                stmLoc = conExt.createStatement();
                strSqlCab="";
                strSqlCab+=" insert "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado,trc_numdoc) \n";
                strSqlCab+=" VALUES("+intNumDoc+", CAST(CURRENT_TIMESTAMP AS DATE), 'REPOSICION', "+objUti.codificar(txtCodBodOri.getText())+", "+objUti.codificar(txtCodBodDes.getText())+", \n";
                strSqlCab+=" " + contadorItems +",'P','"+strNumMod+"'); \n";
                System.out.println("insertarCabecera GP" + strSqlCab);
                stmLoc.executeUpdate(strSqlCab);
                stmLoc.close();
                stmLoc=null;
                 
                
            }            
//            insert tbmsw_transferencia_cab (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado)
//            values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
        }
        catch (Exception e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean insertarCabeceraRep(java.sql.Connection conExt)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        String strSqlCab="";
        String strNumMod="",strTip="";
        String strFecha ="";
        try{
            if(conExt!=null)
            {
                strFecha=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strNumMod = String.format("%015d", intNumDoc);
                stmLoc = conExt.createStatement();
                strSqlCab="";
                strSqlCab+=" insert Dynamics2021..tbm_cab_reposicion (trc_numtrx,fe_feIng,tx_tipdoc,nd_pesIng,nd_pesTot,nd_canIng,tx_bodOri,tx_bodDes,tx_tipRep,tx_usrIng,fe_Ing) \n";
                strSqlCab+=" VALUES("+intNumDoc+", CAST(CURRENT_TIMESTAMP AS DATE), 'REPOSICION', "+txtPesDis.getText()+","+txtPesTot.getText()+","+txtCanTot.getText()+", "+objUti.codificar(txtCodBodOri.getText())+","+objUti.codificar(txtCodBodDes.getText())+",\n";
                if (rbMax.isSelected())
                    strTip ="MAXIMO";
                if (rbMed.isSelected())
                    strTip ="PROMEDIO";
                if (rbMin.isSelected())
                    strTip ="MINIMO";  
                
                strSqlCab+=" '" + strTip +"','"+objParSis.getNombreUsuario() +"','"+strFecha+"' ); \n";
                
                
                System.out.println("insertarCabeceraRep GP" + strSqlCab);
                stmLoc.executeUpdate(strSqlCab);
                stmLoc.close();
                stmLoc=null;
                 
                
            }            
//            insert tbmsw_transferencia_cab (trc_numtrx,trc_fechatrx,trc_codigomotivo,trc_bodorigen,trc_boddestino,trc_numitems,trc_estado)
//            values (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA',2,'I')
        }
        catch (Exception e) 
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
        private boolean ObtenerMax(java.sql.Connection conExt)
        {
            boolean blnRes=true;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc;
            try{
                if(conExt!=null){
                    stmLoc = conExt.createStatement();
                    strSql="";
                    strSql+=" SELECT CASE WHEN MAX(trc_numtrx) IS NULL THEN 0 ELSE MAX(trc_numtrx) END + 1 as NumeroDocumento  \n";
                    strSql+=" FROM  "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_cab  \n";
                    ///strSql+=" WHERE trc_codigomotivo = 'REPOSICION' \n";
                    rstLoc = stmLoc.executeQuery(strSql);
                    if(rstLoc.next()){
                        intNumDoc = rstLoc.getInt("NumeroDocumento");
                    }
                    rstLoc.close();
                    rstLoc=null;
                }            
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }
            return blnRes;
    }
    
private boolean updateUltRepTra(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" update Dynamics2021..tbm_emp set fe_ultRepTran = CONVERT(VARCHAR(16), GETDATE(), 120) where co_emp=1 \n";
                System.out.println("updateUltRepTra GP" + strSql);
                stmLoc.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
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
     * 
     * @param conExt
     * @return 
     */
    
    private boolean insertarDetalleCab(java.sql.Connection conExt){
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        
        BigDecimal bdeCanRep=new BigDecimal("0");
        BigDecimal bdePesUniItm = new BigDecimal("0");
        BigDecimal bdePesTotItm=new BigDecimal("0");
        int contador =0,contadorItms=0;
        boolean validaIngre =false;
        String strSqlitms="", strSqlitmsGlob="";
        String strUbi ="";
        String strNumMod  ="";
        intNumDoc=-1;
        
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                 ObtenerMax(conExt);
                 
                for(int i=0; i<objTblModRep.getRowCountTrue();i++){
                    if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                    {

                        bdeCanRep=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_CAN_REP_SEL).toString());
//                        bdePesUniItm=new BigDecimal(objTblModRep.getValueAt(i, INT_TBL_DAT_PES)==null?"0":objTblModRep.getValueAt(i, INT_TBL_DAT_PES).toString());
//                        bdePesTotItm=bdeCanRep.multiply(bdePesUniItm);
                        
                        strUbi = obtenerUbicacion(conExt,objUti.codificar(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM)),objUti.codificar(txtCodBodOri.getText()));
                        strNumMod =String.format("%015d", intNumDoc);
                        strSqlitms="";
                        //strSqlitms+=" INSERT "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_det (trd_numtrx,trd_fechatrx,trd_codigomotivo,trd_bodorigen,trd_boddestino,trd_item,trd_cantidad_trf,trd_estado,trd_ubic) \n";
                        strSqlitms+=" INSERT "+objParSis.getNombreBaseDatosGP()+".dbo.tbmsw_transferencia_det (trd_numtrx,trd_fechatrx,trd_codigomotivo,trd_bodorigen,trd_boddestino,trd_item,trd_cantidad_trf,trd_estado,trd_ubic,trd_numdoc) \n";
                        strSqlitms+=" VALUES("+intNumDoc+", CAST(CURRENT_TIMESTAMP AS DATE), 'REPOSICION', "+objUti.codificar(txtCodBodOri.getText())+", "+objUti.codificar(txtCodBodDes.getText())+",";
                        strSqlitms+=" "+objUti.codificar(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM))+", "+bdeCanRep+",'P','"+strUbi +"','"+ strNumMod +"' ); \n";
                        
                        contador++;
                        contadorItms++;
                        strSqlitmsGlob += strSqlitms;  
                        validaIngre=true;
////                        if (contador == 100)
////                        {
////////                            insertarCabecera(conExt,contadorItms);
////////                            //ObtenerMax(conExt);
////////                            contadorItms=0;
////                            //validaIngre=true;
////                            break;
////                            
////                        }                        

                    }
                }
                if (validaIngre)
                {
                   insertarCabecera(conExt,contadorItms); 
                   contadorItms=0;
                }
                System.out.println("insertarDetalle GP" + strSqlitmsGlob);
                stmLoc.executeUpdate(strSqlitmsGlob);
                stmLoc.close();
                stmLoc=null;
// insert tbmsw_transferencia_det (trd_numtrx,trd_fechatrx,trd_codigomotivo,trd_bodorigen,trd_boddestino,trd_item,trd_cantidad_trf,trd_estado)
// VALUES (1,'2022-11-01','REPOSICION','INMACONSA','CALIFORNIA','002-000013-S',2,'I')
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
private String obtenerNumeroTabla(int fila, int columna)
{
    Object valor = objTblModRep.getValueAt(fila, columna);

    if (valor == null)
        return "0";

    String texto = valor.toString().trim();

    if (texto.isEmpty() || texto.equalsIgnoreCase("null"))
        return "0";

    try
    {
        return new BigDecimal(texto).toPlainString();
    }
    catch (NumberFormatException e)
    {
        return "0";
    }
}    
private String obtenerTextoTabla(int fila, int columna)
{
    Object valor = objTblModRep.getValueAt(fila, columna);

    if (valor == null)
        return objUti.codificar("");

    String texto = valor.toString().trim();

    return objUti.codificar(texto);
}
private String obtenerFechaTabla(int fila, int columna)
{
    Object valor = objTblModRep.getValueAt(fila, columna);

    if (valor == null)
        return "NULL";

    String texto = valor.toString().trim();

    if (texto.isEmpty() || texto.equalsIgnoreCase("null"))
        return "NULL";

    return objUti.codificar(valor);
}
    
    private boolean insertarDetalleCabRep(java.sql.Connection conExt)
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        
        BigDecimal bdeCanRep=new BigDecimal("0");
        BigDecimal bdePesUniItm = new BigDecimal("0");
        BigDecimal bdePesTotItm=new BigDecimal("0");
        int contador =0,contadorItmsRep=0;
        boolean validaIngre =false;
        String strSqlitms="", strSqlitmsGlob="";
        String strUbi ="";
        String strNumMod  ="";
//////        intNumDoc=-1;
        
        try
        {
            if(conExt!=null)
            {
                stmLoc = conExt.createStatement();
                strSql="";
                 //ObtenerMax(conExt);
                 
                for(int i=0; i<objTblModRep.getRowCountTrue();i++)
                {
                    if(objTblModRep.isChecked(i, INT_TBL_DAT_CHK))
                    {

                        //strUbi = obtenerUbicacion(conExt,objUti.codificar(objTblModRep.getValueAt(i, INT_TBL_DAT_COD_ITM)),objUti.codificar(txtCodBodOri.getText()));
                        strNumMod =String.format("%015d", intNumDoc);
                        strSqlitms = "";

                        strSqlitms +=
                            "INSERT INTO Dynamics2021.dbo.tbm_det_reposicion "
                            + "(trd_numtrx, co_itm, co_itmMae, co_itmAlt, co_itmAlt2, "
                            + "tx_desItm, tx_uni, tx_abc, nd_stkCenDis, nd_disInm4, "
                            + "nd_perCenDis, nd_dis, nd_minDes, nd_maxDes, nd_repMin, "
                            + "nd_repMinPes, nd_repMed, nd_repMedPes, nd_repMax, "
                            + "nd_repMaxPes, nd_canRepCal, nd_canRepSel, nd_cobPis, "
                            + "nd_cobPro, nd_cobSuc, nd_pesKg, nd_pesTotKg, nd_tra, "
                            + "fe_ultRep) \n";

                        strSqlitms += "VALUES (";

                        strSqlitms += intNumDoc + ", ";

                        // Campos de texto
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_COD_ITM) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_COD_ITM) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_COD_ITM) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_COD_ALT_DOS) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_NOM_ITM) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_DES_COR_UNI_MED) + ", ";
                        strSqlitms += obtenerTextoTabla(i, INT_TBL_DAT_ITM_TIP) + ", ";

                        // Campos numéricos
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_STK_ACT_BOD_CEN_DIS) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_DIS_IN4) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_STK_PER_BOD_CEN_DIS_GRP) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_STK_ACT) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_MIN_DES) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_MAX_DES) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_MIN) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_PES_MIN) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_MED) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_PES_MED) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_MAX) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL_PES_MAX) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_CAL) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_REP_SEL) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_COB_PIS) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_COB_PRO) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_CAN_COB_SUC) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_PES) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_PES_TOT_KGR) + ", ";
                        strSqlitms += obtenerNumeroTabla(i, INT_TBL_DAT_TRA) + ", ";

                        // Fecha
                        strSqlitms += obtenerFechaTabla(i, INT_TBL_DAT_FEC_ULT_REP);

                        strSqlitms += "); \n";
                        contador++;
                        contadorItmsRep++;
                        strSqlitmsGlob += strSqlitms;  
                        validaIngre=true;
////                        if (contador == 100)
////                        {
////////                            insertarCabecera(conExt,contadorItms);
////////                            //ObtenerMax(conExt);
////////                            contadorItms=0;
////                            //validaIngre=true;
////                            break;
////                            
////                        }                        

                    }
                }
                if (validaIngre)
                {
                   insertarCabeceraRep(conExt); 
                   contadorItmsRep=0;
                }
                System.out.println("insertarDetalle GP" + strSqlitmsGlob);
                stmLoc.executeUpdate(strSqlitmsGlob);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }

}