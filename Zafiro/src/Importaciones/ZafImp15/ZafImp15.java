 /*
 * ZafImp15.java
 *
 * Created on 15 de Octubre del 2018
 */
package Importaciones.ZafImp15;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis; 
import Librerias.ZafPerUsr.ZafPerUsr; 
import Librerias.ZafUtil.ZafUtil; 
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;  
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;  
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;  
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;   
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus; 
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd; 
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen; 
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp15 extends javax.swing.JInternalFrame
{
    /*  ================================================================================================================  */    
    /*  =================================================  Constantes  =================================================  */    
    /*  ================================================================================================================  */
    
//    //Constantes: Columnas del JTable: tblFix
    static final int INT_TBL_FIX_LIN=0;
    static final int INT_TBL_FIX_INS_PED=1;                  //Para saber que instancia del pedido es: NOTPED, PEDEMB, INIMPO.
    static final int INT_TBL_FIX_COD_EMP=2;
    static final int INT_TBL_FIX_COD_LOC=3;
    static final int INT_TBL_FIX_COD_TIP_DOC=4;
    static final int INT_TBL_FIX_DES_COR_TIP_DOC=5;
    static final int INT_TBL_FIX_DES_LAR_TIP_DOC=6;
    static final int INT_TBL_FIX_COD_DOC=7; 
    static final int INT_TBL_FIX_NUM_DOC=8;
    static final int INT_TBL_FIX_NUM_PED=9;
    static final int INT_TBL_FIX_NUM_CON=10;    
    static final int INT_TBL_FIX_COD_IMP=11;
    static final int INT_TBL_FIX_NOM_IMP=12;
    static final int INT_TBL_FIX_COD_EXP=13;
    static final int INT_TBL_FIX_NOM_EXP=14;
    static final int INT_TBL_FIX_REF_PED=15;
    static final int INT_TBL_FIX_TIP_NOT_PED=16;
    static final int INT_TBL_FIX_VAL_FAC=17;                  //Orden de la fila
    static final int INT_TBL_FIX_TXT_FOR_PAG=18;                  //Orden de la fila
    static final int INT_TBL_FIX_EST_COL_FIL=19;              //Estado que establece color de las filas. N=Ninguno; A=Amarillo; R=Rojo.
    static final int INT_TBL_FIX_ORD_FIL=20;                  //Orden de la fila
        
    //Constantes: Columnas del JTable: TblDat - Pedidos en Tránsito 
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_INS_PED=1;                  //Para saber que instancia del pedido es: NOTPED, PEDEMB, INIMPO.
    static final int INT_TBL_DAT_COD_EMP=2;
    static final int INT_TBL_DAT_COD_LOC=3;
    static final int INT_TBL_DAT_COD_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=5;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=6;
    static final int INT_TBL_DAT_COD_DOC=7; 
    static final int INT_TBL_DAT_NUM_DOC=8;
    static final int INT_TBL_DAT_NUM_PED=9;
    static final int INT_TBL_DAT_NUM_CON=10;
    static final int INT_TBL_DAT_COD_IMP=11;
    static final int INT_TBL_DAT_NOM_IMP=12;
    static final int INT_TBL_DAT_COD_EXP=13; 
    static final int INT_TBL_DAT_NOM_EXP=14;
    static final int INT_TBL_DAT_REF_PED=15;
    static final int INT_TBL_DAT_TIP_NOT_PED=16;
    static final int INT_TBL_DAT_VAL_FAC=17;
    static final int INT_TBL_DAT_TXT_FOR_PAG=18;
    static final int INT_TBL_DAT_NUM_REQ=19;
    static final int INT_TBL_DAT_MOD_PED=20;     
    static final int INT_TBL_DAT_PTO_EMB=21;
    static final int INT_TBL_DAT_FEC_PRE_IMP=22;
    static final int INT_TBL_DAT_FEC_COL_PED=23;
    static final int INT_TBL_DAT_FEC_PRO_PED=24;//Nuevo
    static final int INT_TBL_DAT_FEC_EMB_EST=25;             //Fecha de Embarque Estimada
    static final int INT_TBL_DAT_FEC_EMB_REA=26;             //Fecha de Embarque Real.
    static final int INT_TBL_DAT_FEC_PUE=27;
    static final int INT_TBL_DAT_FEC_ARR_BOD=28;
    static final int INT_TBL_DAT_VAL_TOT_FAC=29;
    static final int INT_TBL_DAT_VAL_TOT_ABO=30;
    static final int INT_TBL_DAT_VAL_TOT_PEN=31;
    static final int INT_TBL_DAT_ANT_POR=32;
    static final int INT_TBL_DAT_ANT_VAL=33;
    static final int INT_TBL_DAT_ANT_COD_USR=34;
    static final int INT_TBL_DAT_ANT_FEC_ING=35;    
    static final int INT_TBL_DAT_AUT_CHK=36;
    static final int INT_TBL_DAT_AUT_CHK_AUX=37;
    static final int INT_TBL_DAT_AUT_COD_USR=38;
    static final int INT_TBL_DAT_AUT_FEC_ING=39;    
    static final int INT_TBL_DAT_BUT=40;
    static final int INT_TBL_DAT_EST_PAG_ARA=41;
    static final int INT_TBL_DAT_EST_COL_FIL=42;              //Estado que establece color de las filas. N=Ninguno; A=Amarillo; R=Rojo.
    static final int INT_TBL_DAT_ORD_FIL=43;                  //Orden de la fila    
    static final int INT_TBL_DAT_COL_VAL_PAG=44;              //Color rojo si es que el valor de (anticipo + estimados por fecha) estimado es menor al Valos a pagar

    //Constantes: ArrayList
    private ArrayList arlRegCarPag, arlDatCarPag; 
    final int INT_ARL_CAR_PAG_COD_CAR_PAG=0;
    final int INT_ARL_CAR_PAG_NOM_CAR_PAG=1;
    final int INT_ARL_CAR_PAG_TIP_CAR_PAG=2;
    final int INT_ARL_CAR_PAG_COL_CAR_PAG=3;
    
    private ArrayList arlRegTotCarPag, arlDatTotCarPag;
    final int INT_ARL_TOT_CAR_PAG_TIP=0;
    final int INT_ARL_TOT_CAR_PAG_NOM=1;
    final int INT_ARL_TOT_CAR_PAG_COL=2;
    final int INT_ARL_TOT_CAR_PAG_SUM=3;  
    
    final int INT_COD_TIP_DOC_PED_IMP=310;   //Tipo de Documento de ORDEN DEL PEDIDO DE IMPORTACIÓN.

    /*  ================================================================================================================  */    
    /*  =================================================  Variables  ==================================================  */    
    /*  ================================================================================================================  */        
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab, objTblFilCabFix ;
    private ZafTblMod objTblModDat, objTblModFix;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblFix, objTblCelRenLblNumEst, objTblCelRenLblEst, objTblCelRenLblTot, objTblCelRenLblTotFix, objTblCelRenLblColPag;
    private ZafTblCelRenLbl objTblCelRenLblAntGrl, objTblCelRenLblAntPor, objTblCelRenLblAntVal;
    private ZafTblCelRenLbl objTblCelRenLblFixValFac, objTblCelRenLblDinValFac, objTblCelRenLblFijValFac, objTblCelRenLblModPed;//Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblFec, objTblCelRenLblNumDin;          //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblCarPag, objTblCelRenLblTotCarPag;    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtDiaCre, objTblCelEdiTxtPorCre, objTblCelEdiTxtValPag, objTblCelEdiTxtModPed;  //Render: Presentar JTextField en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtPorAnt, objTblCelEdiTxtValAnt;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafMouMotAda objMouMotAda;                         //ToolTipText en TableHeader.
    private ZafMouMotAdaFix objMouMotAdaFix;                   //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;                         //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus, objTblBusFix;                                  //Editor de búsqueda.
    private ZafTblOrd objTblOrdDat, objTblOrdFix;                      //JTable de ordenamiento.
    private ZafDtePckEdi objDtePckEdiFecEmb, objDtePckEdiFecPue, objDtePckEdiFecVenEst, objDtePckEdiFecPro;
    private ZafHisTblBasDat objHisTblBasDat;                                    //Histórico    
    private ZafDocLis objDocLis;
    private ZafVenCon vcoEmp, vcoExp;
    private ZafImp objZafImp;
    private ZafSelFec objSelFecDoc;
    private ZafVenCon vcoPedAso, vcoEmpImp, vcoTipPed, vcoPrvExt;      
    
    private ZafPerUsr objPerUsr;    
    private Vector vecDat, vecCab, vecReg, vecAux, vecDatFix, vecCabFix, vecRegFix;
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnHayCam;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.

    private int intNumColEstDat, intNumColEstFix, intNumColEstDatTot, intNumColEstFixTot;
    private int intNumColAddCarPag, intNumColIniCarPag, intNumColFinCarPag;
    private int intNumColAddTotCarPag, intNumColIniTotCarPag, intNumColFinTotCarPag;
    private int intNumGrpAddFecVenEst, intNumColFecVenEst, intNumColIniFecVenEst, intNumColFinFecVenEst;
    private int intNumColAddEstReg, intNumColIniEstReg, intNumColFinEstReg;     //Estado del item a mostrarse

    private static int intCalEditAfter=0, intCalEditBefore=1;
    
    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    private String strFormatoDatePicker = "y-m-d";    
    private String strTipCarPag="'A','F','G','I'";
    private String strCodExpHavelock="15";
    private String strSQL, strAux;
    private String strVer=" v0.4.16 BETA 0.01 DynamicsGP";    
    private ZafTblMod objTblModTot, objTblModTotFix;
    private String VERSION="v1.0"; 
    
    //Tabla fija de datos
    private JScrollBar jscTblFix_horScr, jscTblTotFix_horScr, jscTblFix_verScr, jscTblDat_verScr;
    private JScrollBar jscTblTotDat_horScr_dat, jscTblTotDat_horScr_tot, jscTblTotDat_verScr_dat, jscTblTotDat_verScr_fix;
    
    //Configurar JTable: Establecer relación entre el JTable de datos y JTable fijo.
    private ArrayList<Integer> arlColFij=new ArrayList();
    private ZafTblCelEdiButDlg objTblCelEdiButMod;
    private ZafImp15_01 objImp15_01;
    
    private ArrayList arlRegFecVen, arlDatFecVen;
    final int INT_ARL_FEC_VEN_DES=0;
    final int INT_ARL_FEC_VEN_COL=1;
    
    private ZafTblCelRenChk objTblCelRenChkAut,objTblCelRenChkAutAux;
    private ZafTblCelEdiChk objTblCelEdiChkAut, objTblCelEdiChkAutAux;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp15(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            
            objTblModDat=new ZafTblMod();
            objTblModFix=new ZafTblMod();
            objTblModTotFix=new ZafTblMod();
            objTblModTot=new ZafTblMod();
            arlDatFecVen=new ArrayList();
                    
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }   
            if(!isVersionActualizada() )
            {
                mostrarMsgInf("Su version del sistema no es la adecuada para poder continuar, actualice el sistema e intente nuevamente..");
                dispose();
            }            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }  
      
        private boolean isVersionActualizada()//chiqui
    {
        java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strSQL="";
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
                    System.out.println("Version " + VERSION);
                    System.out.println("tx_ver " + rstLoc.getString("tx_ver"));
                    
                    if(VERSION.equals(rstLoc.getString("tx_ver"))){
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
    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Título de la ventana
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objHisTblBasDat=new ZafHisTblBasDat();
            objDocLis=new ZafDocLis();
            
            //Inicializar ArrayList
            arlDatCarPag=new ArrayList();
            arlDatTotCarPag=new ArrayList();
            
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(true);
            objSelFecDoc.setCheckBoxChecked(false);
            objSelFecDoc.setTitulo("Fecha del documento");       

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();
                txtCodImp.setEditable(true);
                txtNomImp.setEditable(true);
                butImp.setEnabled(true);
            }
            else{
                lblEmpImp.setVisible(false);
                txtCodImp.setVisible(false);
                txtNomImp.setVisible(false);
                butImp.setVisible(false);
            }
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr=new ZafPerUsr(objParSis);  
            if (!objPerUsr.isOpcionEnabled(3242)){//Consultar
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4495)){//Guardar
                butGua.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3243)){//Cerrar
                butCer.setVisible(false);
            }
            
            //Configurar Ventanas de consulta
            configurarVenConExp();
            configurarVenConPedAso();
            configurarVenConEmpImp();
            configurarVenConTipPed();
            configurarVenConPrvExt();
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable fijo.
            arlColFij.add(INT_TBL_FIX_LIN);
            arlColFij.add(INT_TBL_FIX_INS_PED);
            arlColFij.add(INT_TBL_FIX_COD_EMP);
            arlColFij.add(INT_TBL_FIX_COD_LOC);
            arlColFij.add(INT_TBL_FIX_COD_TIP_DOC);
            arlColFij.add(INT_TBL_FIX_DES_COR_TIP_DOC);
            arlColFij.add(INT_TBL_FIX_DES_LAR_TIP_DOC);
            arlColFij.add(INT_TBL_FIX_COD_DOC);
            arlColFij.add(INT_TBL_FIX_NUM_DOC);
            arlColFij.add(INT_TBL_FIX_NUM_PED);
            arlColFij.add(INT_TBL_FIX_NUM_CON);
            arlColFij.add(INT_TBL_FIX_COD_IMP);
            arlColFij.add(INT_TBL_FIX_NOM_IMP);
            arlColFij.add(INT_TBL_FIX_COD_EXP);
            arlColFij.add(INT_TBL_FIX_NOM_EXP);
            arlColFij.add(INT_TBL_FIX_REF_PED);
            arlColFij.add(INT_TBL_FIX_TIP_NOT_PED);
            arlColFij.add(INT_TBL_FIX_VAL_FAC);
            arlColFij.add(INT_TBL_FIX_TXT_FOR_PAG);
            arlColFij.add(INT_TBL_FIX_EST_COL_FIL);
            arlColFij.add(INT_TBL_FIX_ORD_FIL);
            
            chkMosNotPed.setSelected(true);
            chkMosPedEmb.setSelected(true);
            chkMosPedLleDir.setSelected(true);
            //chkMosCarPag.setSelected(true);
            //chkMosTotCarPag.setSelected(true);
            chkMosFecVenRea.setSelected(true);
            //chkMosPagExt.setSelected(true);
            
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_INS_PED,"Ins.Ped.");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_NUM_CON,"Núm.Con.");
            vecCab.add(INT_TBL_DAT_COD_IMP,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Importador");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_REF_PED,"Ref.Ped.");
            vecCab.add(INT_TBL_DAT_TIP_NOT_PED,"Tip.Not.Ped.");
            vecCab.add(INT_TBL_DAT_VAL_FAC,"Val.Fac.");            
            vecCab.add(INT_TBL_DAT_TXT_FOR_PAG,"Des.For.Pag.");
            vecCab.add(INT_TBL_DAT_NUM_REQ,"RFQ.");
            vecCab.add(INT_TBL_DAT_MOD_PED,"Mod.Ped.");
            vecCab.add(INT_TBL_DAT_PTO_EMB,"Pue.Emb.");
            vecCab.add(INT_TBL_DAT_FEC_PRE_IMP,"Fec.Pre.");
            vecCab.add(INT_TBL_DAT_FEC_COL_PED,"Fec.Col.");
            vecCab.add(INT_TBL_DAT_FEC_PRO_PED,"Fec.Pro.");
            vecCab.add(INT_TBL_DAT_FEC_EMB_EST,"Fec.Emb.Est."); 
            vecCab.add(INT_TBL_DAT_FEC_EMB_REA,"Fec.BL");
            vecCab.add(INT_TBL_DAT_FEC_PUE,"Fec.Pue.");
            vecCab.add(INT_TBL_DAT_FEC_ARR_BOD,"Fec.Bod.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_FAC,"Val.Tot.Fac.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_ABO,"Val.Abo.");
            vecCab.add(INT_TBL_DAT_VAL_TOT_PEN,"Val.Pen.");
                                    
            vecCab.add(INT_TBL_DAT_ANT_POR,"%.Ant.");
            vecCab.add(INT_TBL_DAT_ANT_VAL,"Val.Ant.");
            vecCab.add(INT_TBL_DAT_ANT_COD_USR,"Ant.Cod.Usr.");
            vecCab.add(INT_TBL_DAT_ANT_FEC_ING,"Ant.Fec.Ing.");
            
            vecCab.add(INT_TBL_DAT_AUT_CHK,"Aut.");
            vecCab.add(INT_TBL_DAT_AUT_CHK_AUX,"Aut.Aux.");
            vecCab.add(INT_TBL_DAT_AUT_COD_USR,"Cód.Usr.Aut.");
            vecCab.add(INT_TBL_DAT_AUT_FEC_ING,"Fec.Fec.Ing.");
            
            vecCab.add(INT_TBL_DAT_BUT,"");
            vecCab.add(INT_TBL_DAT_EST_PAG_ARA,"Est.Pag.Ara."); 
            vecCab.add(INT_TBL_DAT_EST_COL_FIL,"Est.Col.Fil."); 
            vecCab.add(INT_TBL_DAT_ORD_FIL,"Orden");
            vecCab.add(INT_TBL_DAT_COL_VAL_PAG,"");
            
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
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PTO_EMB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRO_PED).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setPreferredWidth(76);
            
            tcmAux.getColumn(INT_TBL_DAT_ANT_POR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_VAL).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setPreferredWidth(76);
                        
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setPreferredWidth(76);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setPreferredWidth(25);   
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setPreferredWidth(25);  
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setPreferredWidth(25);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Agrupamiento de columnas            
            //Datos del Pedido
            ZafTblHeaGrp objTblHeaGrpDatPedDat=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatPedDat.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatPedDat=new ZafTblHeaColGrp("Datos del Pedido");
            objTblHeaColGrpDatPedDat.setHeight(16); 
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_INS_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_CON));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NOM_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_COD_EXP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EXP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_REF_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_VAL_FAC));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_NUM_REQ));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_MOD_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_PTO_EMB));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_PRO_PED));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_PUE));
            objTblHeaColGrpDatPedDat.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD));            
            objTblHeaGrpDatPedDat.addColumnGroup(objTblHeaColGrpDatPedDat);            
            
            //Valor Factura
            ZafTblHeaGrp objTblHeaGrpValFac=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpValFac.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColValFac=new ZafTblHeaColGrp("Pagos al Exterior");
            objTblHeaColValFac.setHeight(16);
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC));
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO));
            objTblHeaColValFac.add(tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN));
            objTblHeaGrpValFac.addColumnGroup(objTblHeaColValFac);
            
            //Valor de Anticipo
            ZafTblHeaGrp objTblHeaGrpAnt=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpAnt.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColAnt=new ZafTblHeaColGrp("Anticipo");
            objTblHeaColAnt.setHeight(16);
            objTblHeaColAnt.add(tcmAux.getColumn(INT_TBL_DAT_ANT_POR));
            objTblHeaColAnt.add(tcmAux.getColumn(INT_TBL_DAT_ANT_VAL));
            objTblHeaColAnt.add(tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR));
            objTblHeaColAnt.add(tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING));
            objTblHeaGrpAnt.addColumnGroup(objTblHeaColAnt);
                        
            //Autorización
            ZafTblHeaGrp objTblHeaGrpAut=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpAut.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColAut=new ZafTblHeaColGrp("Autorización");
            objTblHeaColAut.setHeight(16);
            objTblHeaColAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_CHK));
            objTblHeaColAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX));
            objTblHeaColAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR));
            objTblHeaColAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING));
            objTblHeaGrpAut.addColumnGroup(objTblHeaColAut);

            tcmAux.getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);            
            
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setResizable(false);

            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setResizable(false);
                                    
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setResizable(false);
            
//            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setResizable(false);
//            
//            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setResizable(false);
                        
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdDat=new ZafTblOrd(tblDat);
            
            objTblOrdDat.addTblOrdListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdAdapter() {
                @Override
                public void beforeOrder(Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdEvent evt)
                {

                }
                @Override
                public void afterOrder(Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdEvent evt)
                {
                    int intColFijOrd;
                    intColFijOrd=arlColFij.indexOf(objTblOrdDat.getColumnaClick());
                    copiarDatTblDatTblFij();
                    objTblOrdFix.cambiarIconoOrdenamiento(intColFijOrd, objTblOrdDat.getTipoOrdenamiento());
                }
            });
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            //objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl); 
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRO_PED).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //System.out.println("**intFil: " + intFil);
                    intFil = objTblCelRenLbl.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLbl.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLbl.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblModPed=new ZafTblCelRenLbl();
            objTblCelRenLblModPed.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblModPed.setTipoFormato(objTblCelRenLblModPed.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setCellRenderer(objTblCelRenLblModPed);
            tcmAux.getColumn(INT_TBL_DAT_PTO_EMB).setCellRenderer(objTblCelRenLblModPed);
            objTblCelRenLblModPed.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblModPed.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblModPed.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblModPed.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblModPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblDinValFac=new ZafTblCelRenLbl();
            objTblCelRenLblDinValFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDinValFac.setTipoFormato(objTblCelRenLblDinValFac.INT_FOR_NUM);
            objTblCelRenLblDinValFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setCellRenderer(objTblCelRenLblDinValFac);            
            objTblCelRenLblDinValFac=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblNumEst=new ZafTblCelRenLbl();
            objTblCelRenLblNumEst.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumEst.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNumEst.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setCellRenderer(objTblCelRenLblNumEst);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setCellRenderer(objTblCelRenLblNumEst);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setCellRenderer(objTblCelRenLblNumEst);
            objTblCelRenLblNumEst.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblNumEst.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblNumEst.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblNumEst.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblNumEst.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
                //Fin nuevo
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblAntGrl=new ZafTblCelRenLbl();
            objTblCelRenLblAntGrl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblAntGrl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setCellRenderer(objTblCelRenLblAntGrl);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setCellRenderer(objTblCelRenLblAntGrl);
            objTblCelRenLblAntGrl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblAntGrl.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblAntGrl.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblAntGrl.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblAntGrl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
                //Fin nuevo
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblAntPor=new ZafTblCelRenLbl();
            objTblCelRenLblAntPor.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAntPor.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblAntPor.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_ANT_POR).setCellRenderer(objTblCelRenLblAntPor);
            objTblCelRenLblAntPor.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblAntPor.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblAntPor.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblAntPor.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblAntPor.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });            

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblAntVal=new ZafTblCelRenLbl();
            objTblCelRenLblAntVal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAntVal.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblAntVal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_ANT_VAL).setCellRenderer(objTblCelRenLblAntVal);
            objTblCelRenLblAntVal.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblAntVal.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblAntVal.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblAntVal.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblAntVal.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
                //Fin nuevo
            });            
            
            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecPue = new ZafDtePckEdi(strFormatoDatePicker);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setCellEditor(objDtePckEdiFecPue); 
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setCellEditor(objDtePckEdiFecPue); 
            objDtePckEdiFecPue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {   
                int intFil=-1;
                String strEstCiePedEmb=""; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    /**
                     * Validar actualización de fecha de embarque
                     * <BR>Pedido Embarcado: Se podrá actualizar la fecha de embarque, mientras no este cerrado el pedido embarcado.
                     * <BR>LSC - 15/Abr/2019: El pedido embarcado consta como cerrado, cuando se paga el arancel del pedido (tbm_cabPedEmbImp.st_ciePagAra)
                     * <BR>Nota de Pedido: Siempre se podrá actualizar el mes de embarque, debido a que no ha sido realizado el embarque.
                     */
                    strEstCiePedEmb=objUti.parseString(objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_PAG_ARA));
                    if(strEstCiePedEmb.equals("S")){
                        objDtePckEdiFecPue.setCancelarEdicion(true); 
                        mostrarMsgInf("<HTML>No puede editarse las fechas de un pedido cerrado.</HTML>");
                    }
                    else{
                        objDtePckEdiFecPue.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    /* Se vuelve a calcular las fechas de vencimiento, de acuerdo a la nueva fecha de embarque.*/
                    calcularGrupoColumnsFecVenEstPorFila(intFil);
                }
            });                

            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecEmb = new ZafDtePckEdi(strFormatoDatePicker);  
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setCellEditor(objDtePckEdiFecEmb);    
            objDtePckEdiFecEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strEstCiePedEmb=""; 
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    /**
                     * Validar actualización de fecha de embarque
                     * <BR>Pedido Embarcado: Se podrá actualizar la fecha de embarque, mientras no este cerrado el pedido embarcado.
                     * <BR>LSC - 15/Abr/2019: El pedido embarcado consta como cerrado, cuando se paga el arancel del pedido (tbm_cabPedEmbImp.st_ciePagAra)
                     * <BR>Nota de Pedido: Siempre se podrá actualizar el mes de embarque, debido a que no ha sido realizado el embarque.
                     */
                    strEstCiePedEmb=objUti.parseString(objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_PAG_ARA));
                    if(strEstCiePedEmb.equals("S")){
                        objDtePckEdiFecEmb.setCancelarEdicion(true); 
                        mostrarMsgInf("<HTML>No puede cambiarse la fecha de embarque de un pedido cerrado.</HTML>");
                    }
                    else{
                        objDtePckEdiFecEmb.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    /* Se vuelve a calcular las fechas de vencimiento, de acuerdo a la nueva fecha de embarque.*/
                    calcularGrupoColumnsFecVenEstPorFila(intFil);
                }
            });
            
            
            //Establecer JTable: Establecer columnas DataPicker
            objDtePckEdiFecPro = new ZafDtePckEdi(strFormatoDatePicker);  
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRO_PED).setCellEditor(objDtePckEdiFecPro);    
            objDtePckEdiFecPro.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCodNotPed=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    intCodNotPed=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                    /**
                     * Validar actualización de fecha de producción
                     * <BR>Nota de Pedido: Se podrá cambiar la fecha de producción sólo en la instancia de la Nota de Pedido.
                     */
                    if(intCodNotPed==objZafImp.INT_COD_TIP_DOC_NOT_PED_IMP){
                        objDtePckEdiFecPro.setCancelarEdicion(false);
                    }
                    else{
                        objDtePckEdiFecPro.setCancelarEdicion(true);
                        mostrarMsgInf("<HTML>Sólo se puede modificar la fecha de producción en una Nota de Pedido.</HTML>");
                    }
                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                }
            });
            
            //PARA EL BOTON DE ANEXO UNO, QUE LLAMA A LA VENTANA DE NOTA DE PEDIDO
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFormularioPedido(intFilSel); 
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            //Configurar JTable: Editor celdas.
            objTblCelEdiTxtPorAnt=new ZafTblCelEdiTxt();
            tcmAux.getColumn(INT_TBL_DAT_ANT_POR).setCellEditor(objTblCelEdiTxtPorAnt);
            objTblCelEdiTxtPorAnt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strDat="";
                int intCodTipDoc=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                    objTblCelEdiTxtPorAnt.setCancelarEdicion(true);
                    
                    if(objZafImp.INT_COD_TIP_DOC_NOT_PED_IMP==intCodTipDoc)//Se puede editar   z
                        objTblCelEdiTxtPorAnt.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtPorAnt.setCancelarEdicion(true);
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString());
                    if(objUti.isNumero(strDat)){
                        calculaAntPor(intFil);
                        
                        if(intCodTipDoc!=objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP){
                            if(isExcValPagFecVen_valPenPag(intFil)){//Si excede
                                mostrarMsgInf("<HTML>La suma de los valores a pagar exceden el valor pendiente de pago del pedido.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                objTblModDat.setValueAt("", intFil, intCol);
                                objTblModDat.setValueAt("", intFil, (intCol+1));
                            }
                        }
                        else
                            objTblModDat.setValueAt("", intFil, intCol);
                    }
                    else
                        objTblModDat.setValueAt("", intFil, intCol);

                } 
            });
            
            //Configurar JTable: Editor celdas.
            objTblCelEdiTxtValAnt=new ZafTblCelEdiTxt();
            tcmAux.getColumn(INT_TBL_DAT_ANT_VAL).setCellEditor(objTblCelEdiTxtValAnt);
            objTblCelEdiTxtValAnt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strDat="";
                int intCodTipDoc=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    
                    intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                    objTblCelEdiTxtValAnt.setCancelarEdicion(true);
                    
                    if(objZafImp.INT_COD_TIP_DOC_NOT_PED_IMP==intCodTipDoc)//Se puede editar   z
                        objTblCelEdiTxtValAnt.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtValAnt.setCancelarEdicion(true);
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString());
                    if(objUti.isNumero(strDat)){
                        calculaAntVal(intFil);
                        if(intCodTipDoc!=objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP){
                            if(isExcValPagFecVen_valPenPag(intFil)){//Si excede
                                mostrarMsgInf("<HTML>La suma de los valores a pagar exceden el valor pendiente de pago del pedido.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                objTblModDat.setValueAt("", intFil, intCol);
                                objTblModDat.setValueAt("", intFil, (intCol-1));
                            }
                        }
                        else
                            objTblModDat.setValueAt("", intFil, intCol);
                    }
                    else
                        objTblModDat.setValueAt("", intFil, intCol);

                } 
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAut=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setCellRenderer(objTblCelRenChkAut);
            objTblCelRenChkAut=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAut=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setCellEditor(objTblCelEdiChkAut);
            objTblCelEdiChkAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                int intCodTipDoc=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString());
                    objTblCelEdiChkAut.setCancelarEdicion(true);
                    
                    if(objZafImp.INT_COD_TIP_DOC_NOT_PED_IMP==intCodTipDoc){//Se puede editar   z
                        if(objTblModDat.isChecked(intFil, INT_TBL_DAT_AUT_CHK)){
                            if(objTblModDat.isChecked(intFil, INT_TBL_DAT_AUT_CHK_AUX)){
                                objTblCelEdiChkAut.setCancelarEdicion(true);
                            }
                            else
                                objTblCelEdiChkAut.setCancelarEdicion(false);
                        }
                        else{
                            objTblCelEdiChkAut.setCancelarEdicion(false);
                        }
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAutAux=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setCellRenderer(objTblCelRenChkAutAux);
            objTblCelRenChkAutAux=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAutAux=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setCellEditor(objTblCelEdiChkAutAux);
            
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiTxtModPed=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setCellEditor(objTblCelEdiTxtModPed);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblColPag=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setCellRenderer(objTblCelRenLblColPag);
            objTblCelRenLblColPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstColPag="";
                int intFil=-1;
                int intCodTipDoc=-1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                    if(intCodTipDoc!=objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP){
                        if(isExcValPagFecVen_valPenPag(intFil)){//Si excede
                            strEstColPag=objTblModDat.getValueAt(objTblCelRenLblColPag.getRowRender(), INT_TBL_DAT_COL_VAL_PAG)==null?"":objTblModDat.getValueAt(objTblCelRenLblColPag.getRowRender(), INT_TBL_DAT_COL_VAL_PAG).toString();
                            if (strEstColPag.equals("S")){
                                objTblCelRenLblColPag.setBackground(colFonColCru);
                            }
                            else{
                                objTblCelRenLblColPag.setBackground(Color.WHITE);
                            }

                        }
                        else{
                            objTblCelRenLblColPag.setBackground(Color.WHITE);
                        }
                    }
                    else
                        objTblCelRenLblColPag.setBackground(Color.WHITE);
                }
            });
            
            objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEstDat=objTblModDat.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblFix(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCabFix=new Vector();    //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_FIX_LIN,"");
            vecCabFix.add(INT_TBL_FIX_INS_PED,"Ins.Ped.");
            vecCabFix.add(INT_TBL_FIX_COD_EMP,"Cód.Emp.");
            vecCabFix.add(INT_TBL_FIX_COD_LOC,"Cód.Loc.");
            vecCabFix.add(INT_TBL_FIX_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabFix.add(INT_TBL_FIX_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabFix.add(INT_TBL_FIX_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCabFix.add(INT_TBL_FIX_COD_DOC,"Cód.Doc.");
            vecCabFix.add(INT_TBL_FIX_NUM_DOC,"Núm.Doc.");
            vecCabFix.add(INT_TBL_FIX_NUM_PED,"Núm.Ped.");
            vecCabFix.add(INT_TBL_FIX_NUM_CON,"Núm.Con.");
            vecCabFix.add(INT_TBL_FIX_COD_IMP,"Cód.Imp.");
            vecCabFix.add(INT_TBL_FIX_NOM_IMP,"Importador");
            vecCabFix.add(INT_TBL_FIX_COD_EXP,"Cód.Exp.");
            vecCabFix.add(INT_TBL_FIX_NOM_EXP,"Exportador");
            vecCabFix.add(INT_TBL_FIX_REF_PED,"Ref.Ped.");
            vecCabFix.add(INT_TBL_FIX_TIP_NOT_PED,"Tip.Not.Ped.");
            vecCabFix.add(INT_TBL_FIX_VAL_FAC,"Val.Fac.");
            vecCabFix.add(INT_TBL_FIX_TXT_FOR_PAG,"Des.For.Pag.");
            vecCabFix.add(INT_TBL_FIX_EST_COL_FIL,"Estado de columna para color");
            vecCabFix.add(INT_TBL_FIX_ORD_FIL,"Ordenamiento");
            
                                    
            //objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblFix.setModel(objTblModFix);

            //Configurar JTable: Establecer tipo de selección.
            tblFix.setRowSelectionAllowed(true);
            tblFix.getTableHeader().setReorderingAllowed(false);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            //tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblFix.setAutoResizeMode(tblDat.getAutoResizeMode());
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_FIX_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_FIX_NUM_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_NOM_IMP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_REF_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_TIP_NOT_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_FIX_VAL_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_TXT_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setPreferredWidth(30);
            
            
//            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//            tblFix.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix = new ZafMouMotAdaFix();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);            

            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setResizable(false);
   
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setResizable(false);            
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setResizable(false);            
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setResizable(false);  
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setResizable(false);
                        
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema. 
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setResizable(false);
                  
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdFix=new ZafTblOrd(tblFix);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix=new ZafMouMotAdaFix();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblFilCabFix);
            
            
            //INICIO FIXED
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
            tblDat.setSelectionModel(modDat);//se descomento

            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);
                      
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            jscTblFix_horScr=spnFix.getHorizontalScrollBar();
            jscTblTotFix_horScr=spnTotFix.getHorizontalScrollBar();
            
            jscTblFix_verScr=spnFix.getVerticalScrollBar();
            jscTblDat_verScr=spnDat.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            jscTblFix_horScr.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    jscTblTotFix_horScr.setValue(evt.getValue());
                }
            });
            //VERTICAL
            jscTblFix_verScr.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    jscTblDat_verScr.setValue(evt.getValue());
                }
            });
            
            //Configurar JTable: Agrupamiento de columnas            
            //Datos del Pedido
            ZafTblHeaGrp objTblHeaGrpPedFix=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpPedFix.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpPedFix=new ZafTblHeaColGrp("Datos del Pedido");
            objTblHeaColGrpPedFix.setHeight(16); 
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_LIN));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_INS_PED));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_EMP));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_LOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_DES_COR_TIP_DOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_DOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_NUM_DOC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_NUM_PED));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_NUM_CON));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_IMP));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_NOM_IMP));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_COD_EXP));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_NOM_EXP));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_REF_PED));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_TIP_NOT_PED));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_VAL_FAC));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_TXT_FOR_PAG));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL));
            objTblHeaColGrpPedFix.add(tcmAux.getColumn(INT_TBL_FIX_ORD_FIL));
            
            objTblHeaGrpPedFix.addColumnGroup(objTblHeaColGrpPedFix);

            //FIN FIXED
            
            
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblFix=new ZafTblCelRenLbl();
            objTblCelRenLblFix.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            //tcmAux.getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setCellRenderer(objTblCelRenLblFix); 
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NUM_PED).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NUM_CON).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NOM_IMP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NOM_EXP).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_REF_PED).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_TIP_NOT_PED).setCellRenderer(objTblCelRenLblFix);            
            tcmAux.getColumn(INT_TBL_FIX_TXT_FOR_PAG).setCellRenderer(objTblCelRenLblFix);
            
            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblFix.getRowRender();
                    strColFil=objTblModFix.getValueAt(intFil, INT_TBL_FIX_EST_COL_FIL)== null ? "N": objTblModFix.getValueAt(intFil, INT_TBL_FIX_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblFix.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblFix.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLblFixValFac=new ZafTblCelRenLbl();
            objTblCelRenLblFixValFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblFixValFac.setTipoFormato(objTblCelRenLblFixValFac.INT_FOR_NUM);
            objTblCelRenLblFixValFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FIX_VAL_FAC).setCellRenderer(objTblCelRenLblFixValFac);            
            objTblCelRenLblFixValFac.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblFixValFac.getRowRender();
                    strColFil=objTblModFix.getValueAt(intFil, INT_TBL_FIX_EST_COL_FIL)== null ? "N": objTblModFix.getValueAt(intFil, INT_TBL_FIX_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblFixValFac.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblFixValFac.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblFixValFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });

            
            
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdFix=new ZafTblOrd(tblFix);
            objTblOrdFix.addTblOrdListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdAdapter() {
                @Override
                public void beforeOrder(Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdEvent evt)
                {
                    objTblOrdFix.cancelarOrdenamiento();
                    objTblOrdDat.ordenar(arlColFij.get(objTblOrdFix.getColumnaClick()));
                    copiarDatTblDatTblFij();
                    objTblOrdFix.cambiarIconoOrdenamiento(objTblOrdFix.getColumnaClick(), objTblOrdDat.getTipoOrdenamiento());
                }
                @Override
                public void afterOrder(Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdEvent evt)
                {
                    
                }
            });
            
            
            
            intNumColEstFix=objTblModFix.getColumnCount();
            
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
                case INT_TBL_DAT_LIN:
                    //strMsg="";
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que \"Mes de Embarque es menor a la Fecha Actual\".</td></tr>"; //Color Rojo
                    strMsg+="<tr><td style='background-color: #FFFF00'>&nbsp;</td><td>&nbsp;</td><td>Indica \"Mes de Embarque estimado dentro de los 30 días\".</td></tr>"; //Color Amarillo
                    //strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que son \"Items de ajustes tipo egreso\".</td></tr>"; //Color Rojo
                    strMsg+="</table><br></html>";
                    break;                
                case INT_TBL_DAT_INS_PED:
                    strMsg="Instancia del pedido";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del pedido";
                    break;
                case INT_TBL_DAT_NUM_CON:
                    strMsg="Número del pedido consolidado";
                    break;
                case INT_TBL_DAT_COD_IMP:
                    strMsg="Código del Importador";
                    break;
                case INT_TBL_DAT_NOM_IMP:
                    strMsg="Nombre del Importador";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del Exportador";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del Exportador";
                    break;
                case INT_TBL_DAT_REF_PED:
                    strMsg="Referencia de Pedido(Presupuesto/Observación)";
                    break;
                case INT_TBL_DAT_TIP_NOT_PED:
                    strMsg="Tipo de Nota de Pedido";
                    break;
                case INT_TBL_DAT_VAL_FAC:
                    strMsg="Valor de la factura";
                    break;
                case INT_TBL_DAT_TXT_FOR_PAG:
                    strMsg="Descripción de la forma de pago del pedido";
                    break;
                case INT_TBL_DAT_NUM_REQ:
                    strMsg="Descripción de forma de pago";
                    break;
                case INT_TBL_DAT_MOD_PED:
                    strMsg="Modalidad del pedido";
                    break;
                case INT_TBL_DAT_PTO_EMB:
                    strMsg="Puerto de Embarque del pedido";
                    break;
                case INT_TBL_DAT_FEC_PRE_IMP:
                    strMsg="Fecha de presupuesto";
                    break;                     
                case INT_TBL_DAT_FEC_COL_PED:
                    strMsg="Fecha de colocación del pedido";
                    break;
                case INT_TBL_DAT_FEC_PRO_PED:
                    strMsg="Fecha de producción del pedido";
                    break;
                case INT_TBL_DAT_FEC_EMB_EST:
                    strMsg="Fecha de Embarque Estimada";
                    break;                    
                case INT_TBL_DAT_FEC_EMB_REA:
                    strMsg="Fecha de Embarque BL";
                    break;
                case INT_TBL_DAT_FEC_PUE:
                    strMsg="Fecha del puerto";
                    break;                    
                case INT_TBL_DAT_FEC_ARR_BOD:
                    strMsg="Fecha de arribo de bodega";
                    break;
                case INT_TBL_DAT_VAL_TOT_FAC:
                    //strMsg="Total FOB / CFR";
                    strMsg="Valor Total Factura";
                    break;
                case INT_TBL_DAT_VAL_TOT_ABO:
                    strMsg="Valor Abonado";
                    break;
                case INT_TBL_DAT_VAL_TOT_PEN:
                    strMsg="Valor Pendiente"; 
                    break;
                case INT_TBL_DAT_ANT_POR:
                    strMsg="Porcentaje de Anticipo"; 
                    break;
                case INT_TBL_DAT_ANT_VAL:
                    strMsg="Valor de Anticipo"; 
                    break;
                case INT_TBL_DAT_ANT_COD_USR:
                    strMsg="Código de usuario de ingreso de anticipo"; 
                    break;
                case INT_TBL_DAT_ANT_FEC_ING:
                    strMsg="Fecha de ingreso de anticipo"; 
                    break;
                case INT_TBL_DAT_AUT_CHK:
                    strMsg="Autorización de anticipo"; 
                    break;
                case INT_TBL_DAT_AUT_CHK_AUX:
                    strMsg="Autorización de anticipo Auxiliar"; 
                    break;
                case INT_TBL_DAT_AUT_COD_USR:
                    strMsg="Código de usuario de autorización de anticipo"; 
                    break;
                case INT_TBL_DAT_AUT_FEC_ING:
                    strMsg="Fecha de autorización de anticipo"; 
                    break;
                    
                case INT_TBL_DAT_BUT:
                    strMsg="Cargar formulario de Nota de Pedido/Pedido Embarcado";
                    break;                    
                case INT_TBL_DAT_EST_PAG_ARA:
                    strMsg="Estado del cierre del pago de arancel";
                    break;
                case INT_TBL_DAT_EST_COL_FIL:
                    strMsg="Estado del color para la fila";
                    break;  
                default:
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
    private class ZafMouMotAdaFix extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblFix.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FIX_LIN:
                    //strMsg="";
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que \"Mes de Embarque es menor a la Fecha Actual\".</td></tr>"; //Color Rojo
                    strMsg+="<tr><td style='background-color: #FFFF00'>&nbsp;</td><td>&nbsp;</td><td>Indica \"Mes de Embarque estimado dentro de los 30 días\".</td></tr>"; //Color Amarillo
                    //strMsg+="<tr><td>&nbsp;</td><td style='background-color: #FF0000'>&nbsp;</td><td>Indica que son \"Items de ajustes tipo egreso\".</td></tr>"; //Color Rojo
                    strMsg+="</table><br></html>";
                    break;                
                case INT_TBL_FIX_INS_PED:
                    strMsg="Instancia del pedido";
                    break;
                case INT_TBL_FIX_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_FIX_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_FIX_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_FIX_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_FIX_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_FIX_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_FIX_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_FIX_NUM_PED:
                    strMsg="Número del pedido";
                    break;
                case INT_TBL_FIX_NUM_CON:
                    strMsg="Número del Pedido consolidado";
                    break;
                case INT_TBL_FIX_COD_IMP:
                    strMsg="Código del Importador";
                    break;
                case INT_TBL_FIX_NOM_IMP:
                    strMsg="Nombre del Importador";
                    break;
                case INT_TBL_FIX_COD_EXP:
                    strMsg="Código del Exportador";
                    break;
                case INT_TBL_FIX_NOM_EXP:
                    strMsg="Nombre del Exportador";
                    break;
                case INT_TBL_FIX_REF_PED:
                    strMsg="Referencia de Pedido(Presupuesto/Observación)";
                    break;
                case INT_TBL_FIX_TIP_NOT_PED:
                    strMsg="Tipo de Nota de Pedido";
                    break;
                case INT_TBL_FIX_VAL_FAC:
                    strMsg="Valor de la factura";
                    break;
                case INT_TBL_FIX_TXT_FOR_PAG:
                    strMsg="Descripción de forma de pago del pedido";
                    break;
                case INT_TBL_FIX_EST_COL_FIL:
                    strMsg="Estado para identificar el color de la fila";
                    break;
                case INT_TBL_FIX_ORD_FIL:
                    strMsg="Ordenamiento de la fila";
                    break;
                default:
                    break;
            }
            tblFix.getTableHeader().setToolTipText(strMsg);
        }
    }    
      
    
    private void mostrarFormularioPedido(int fila){
        try{
            String strCodEmpPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_EMP).toString();
            String strCodLocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
            String strCodTipDocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
            String strCodDocPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
            String strInsPed=objTblModDat.getValueAt(fila, INT_TBL_DAT_INS_PED).toString();
            
            if(strInsPed.equals("NOTPED")){   //Nota de Pedido
                Importaciones.ZafImp32.ZafImp32 objVcoPed=new Importaciones.ZafImp32.ZafImp32(objParSis, Integer.parseInt(strCodLocPed), Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }
            else if(strInsPed.equals("PEDEMB")){  //Pedido Embarcado
                Importaciones.ZafImp33.ZafImp33 objVcoPed=new Importaciones.ZafImp33.ZafImp33(objParSis, Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }
            else if(strInsPed.equals("INIMPO")){  //Ingreso por Importación
                Importaciones.ZafImp34.ZafImp34 objVcoPed=new Importaciones.ZafImp34.ZafImp34(objParSis, Integer.parseInt(strCodEmpPed), Integer.parseInt(strCodLocPed), Integer.parseInt(strCodTipDocPed), Integer.parseInt(strCodDocPed));
                this.getParent().add(objVcoPed,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objVcoPed.setVisible(true);
                objVcoPed=null;
            }            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }    
    
    private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtNomImp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
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
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{               
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoExp.setCampoBusqueda(1);
                    vcoExp.show();
                    if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(0);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtCodExp.setText(strCodExp);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(2);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtNomExp.setText(strNomExp);
                        }
                    }
                    break;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Exportadores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("225");
            arlAncCol.add("225");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT); 
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Pedidos Asociados".
     */
    private boolean configurarVenConPedAso()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.tx_numPed");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_Doc, a1.tx_numDoc2 AS tx_numPed";
            strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'"; 
            strSQL+=" AND NOT ( (a1.tx_numDoc2 LIKE '%PS%') OR (a1.tx_numDoc2 LIKE '%PL%') )"; /* No mostrar compras locales. */
            strSQL+=" AND a1.fe_doc>'2018-01-01'";
            strSQL+=" ORDER BY a1.tx_numDoc2";            
            vcoPedAso=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado pedidos asociados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPedAso.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoPedAso.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empresas Importadoras".
     */
    private boolean configurarVenConEmpImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nomEmpImp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Imp.");
            arlAli.add("Importador");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom AS tx_nomEmpImp";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A' "; 
            strSQL+=" ORDER BY a1.co_emp";            
            vcoEmpImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmpImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de Pedidos".
     */
    private boolean configurarVenConTipPed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipPed");
            arlCam.add("a1.tx_DesTipPed");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Tip.Ped.");
            arlAli.add("Tipo de Pedido");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT * FROM (";
            strSQL+=" 	SELECT 0 AS co_tipPed, 'Directo' AS tx_DesTipPed";
            strSQL+=" 	UNION ALL";
            strSQL+=" 	SELECT 1 AS co_tipPed, 'Indirecto' AS tx_DesTipPed";
            strSQL+=" ) AS x";      
            strSQL+=" ORDER BY x.co_tipPed";   
            vcoTipPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tipos de Pedidos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }      
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores del Exterior".
     */
    private boolean configurarVenConPrvExt()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_prvExt");
            arlCam.add("a1.tx_nomPrvExt");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Prv.Ext.");
            arlAli.add("Proveedor Exportador");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("300"); 
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_exp AS co_prvExt, a1.tx_nom AS tx_nomPrvExt";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrvExt=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Proveedores del Exterior", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrvExt.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
    

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        panPedTra = new javax.swing.JPanel();
        chkMosNotPed = new javax.swing.JCheckBox();
        chkMosPedEmb = new javax.swing.JCheckBox();
        chkMosPedLleDir = new javax.swing.JCheckBox();
        chkMosPedLleInd = new javax.swing.JCheckBox();
        chkMosFecVenRea = new javax.swing.JCheckBox();
        chkMosCarPag = new javax.swing.JCheckBox();
        chkMosTotCarPag = new javax.swing.JCheckBox();
        chkMosPagExt = new javax.swing.JCheckBox();
        chkMosNotPedPre = new javax.swing.JCheckBox();
        chkOrder = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptFix = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotFix = new javax.swing.JScrollPane();
        tblTotFix = new javax.swing.JTable();
        panRptDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panTotDat = new javax.swing.JPanel();
        spnTotDat = new javax.swing.JScrollPane();
        tblTotDat = new javax.swing.JTable();
        panAuxSpnTot = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panCon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 70));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panCon.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 0, 400, 16);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 16, 400, 16);

        lblEmpImp.setText("Empresa(Importador):");
        lblEmpImp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(40, 32, 136, 20);

        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(180, 32, 70, 20);

        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(250, 32, 360, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(610, 32, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblExp);
        lblExp.setBounds(40, 50, 140, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panFil.add(txtCodExp);
        txtCodExp.setBounds(180, 52, 70, 20);

        txtNomExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExpFocusLost(evt);
            }
        });
        txtNomExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExpActionPerformed(evt);
            }
        });
        panFil.add(txtNomExp);
        txtNomExp.setBounds(250, 52, 360, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panFil.add(butExp);
        butExp.setBounds(610, 52, 20, 20);

        panPedTra.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panPedTra.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        panPedTra.setLayout(null);

        chkMosNotPed.setText("Mostrar Notas de Pedido");
        chkMosNotPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosNotPedActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosNotPed);
        chkMosNotPed.setBounds(10, 20, 200, 16);

        chkMosPedEmb.setText("Mostrar Pedidos Embarcados");
        chkMosPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedEmbActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedEmb);
        chkMosPedEmb.setBounds(10, 36, 200, 16);

        chkMosPedLleDir.setText("Mostrar Pedidos Llegados (Directos)");
        chkMosPedLleDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedLleDirActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedLleDir);
        chkMosPedLleDir.setBounds(10, 52, 300, 16);

        chkMosPedLleInd.setText("Mostrar Pedidos Llegados (Indirectos - Havelock)");
        chkMosPedLleInd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedLleIndActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPedLleInd);
        chkMosPedLleInd.setBounds(10, 68, 380, 16);

        chkMosFecVenRea.setText("Mostrar Fechas de Vencimiento");
        chkMosFecVenRea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosFecVenReaActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosFecVenRea);
        chkMosFecVenRea.setBounds(10, 84, 290, 16);

        chkMosCarPag.setText("Mostrar Cargos a Pagar");
        chkMosCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCarPagActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosCarPag);
        chkMosCarPag.setBounds(10, 100, 230, 16);

        chkMosTotCarPag.setText("Mostrar Total Cargos a Pagar");
        chkMosTotCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosTotCarPagActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosTotCarPag);
        chkMosTotCarPag.setBounds(10, 120, 230, 16);

        chkMosPagExt.setText("Mostrar valores de pagos y valores pendientes a Proveedores del Exterior");
        chkMosPagExt.setToolTipText("Esta opción debe marcarse si se ingresan datos de pagos a proveedores");
        chkMosPagExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPagExtActionPerformed(evt);
            }
        });
        panPedTra.add(chkMosPagExt);
        chkMosPagExt.setBounds(10, 144, 450, 16);

        chkMosNotPedPre.setText("Mostrar Notas de Pedido Previas");
        panPedTra.add(chkMosNotPedPre);
        chkMosNotPedPre.setBounds(10, 4, 240, 16);

        chkOrder.setSelected(true);
        chkOrder.setText("Ordenar Fecha_Bodega");
        chkOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOrderActionPerformed(evt);
            }
        });
        panPedTra.add(chkOrder);
        chkOrder.setBounds(250, 10, 230, 16);

        panFil.add(panPedTra);
        panPedTra.setBounds(22, 80, 490, 170);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setDividerLocation(690);
        sppRpt.setDividerSize(2);

        panRptFix.setLayout(new java.awt.BorderLayout());

        tblFix.setModel(new javax.swing.table.DefaultTableModel(
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
        tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblFix.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblFixFocusGained(evt);
            }
        });
        spnFix.setViewportView(tblFix);

        panRptFix.add(spnFix, java.awt.BorderLayout.CENTER);

        spnTotFix.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTotFix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotFix.setViewportView(tblTotFix);

        panRptFix.add(spnTotFix, java.awt.BorderLayout.SOUTH);

        sppRpt.setLeftComponent(panRptFix);

        panRptDat.setLayout(new java.awt.BorderLayout());

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
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblDatFocusGained(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRptDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panTotDat.setLayout(new java.awt.BorderLayout());

        spnTotDat.setPreferredSize(new java.awt.Dimension(320, 42));

        tblTotDat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotDat.setViewportView(tblTotDat);

        panTotDat.add(spnTotDat, java.awt.BorderLayout.CENTER);

        panAuxSpnTot.setPreferredSize(new java.awt.Dimension(12, 10));
        panTotDat.add(panAuxSpnTot, java.awt.BorderLayout.EAST);

        panRptDat.add(panTotDat, java.awt.BorderLayout.SOUTH);

        sppRpt.setRightComponent(panRptDat);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(350, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 40));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");
            //cboEstDoc.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
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

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodImp.getText().equals("")){
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblModDat.removeAllRows();
                txtCodExp.setText("");
                txtNomExp.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodImp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblModDat.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        strCodEmp=txtCodImp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblModDat.removeAllRows();
            }
            else
                mostrarVenConExp(1);
        }
        else
            txtCodExp.setText(strCodExp);
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtNomExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExpActionPerformed
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        strNomExp=txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomExp.getText().equalsIgnoreCase(strNomExp))
        {
            if (txtNomExp.getText().equals(""))
            {
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblModDat.removeAllRows();
            }
            else
            {
                mostrarVenConExp(2);
            }
        }
        else
            txtNomExp.setText(strNomExp);
    }//GEN-LAST:event_txtNomExpFocusLost

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

    private void chkMosNotPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosNotPedActionPerformed
//        if(chkMosNotPedAbi.isSelected()){
//            objTblModDat.removeSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
//        }
//        else{
//            objTblModDat.addSystemHiddenColumn(INT_TBL_DAT_BUT_NOT_PED, tblDat);
//        }
    }//GEN-LAST:event_chkMosNotPedActionPerformed

    private void chkMosPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedEmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedEmbActionPerformed

    private void chkMosCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCarPagActionPerformed
        //ocultarMostrarColDin();
//        if(chkMosCarPag.isSelected()){
//            for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
//                objTblModDat.removeSystemHiddenColumn(j, tblDat);
//                objTblModTot.removeSystemHiddenColumn(j, tblTotDat);
//            }
//        }
//        else{
//            for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
//                objTblModDat.addSystemHiddenColumn(j, tblDat);
//                objTblModTot.addSystemHiddenColumn(j, tblTotDat);
//            }
//        }
        
    }//GEN-LAST:event_chkMosCarPagActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblModDat.isDataModelChanged()){      
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                if(isCamVal()){
                    if (guardar()){
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        //cargarReg();
                    }
                    else
                        mostrarMsgErr("<HTML>Ocurrió un error al realizar la operación GUARDAR.<BR>Intente realizar la operación nuevamente.<BR>Si el problema persiste notifiquelo a su administrador del sistema.</HTML>");
                }
                else
                    mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        else
            mostrarMsgInf("No se han realizado cambios que guardar.");


    }//GEN-LAST:event_butGuaActionPerformed

    private void chkMosFecVenReaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosFecVenReaActionPerformed
        //ocultarMostrarColDin();
    }//GEN-LAST:event_chkMosFecVenReaActionPerformed

    private void chkMosTotCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosTotCarPagActionPerformed
//        if(chkMosTotCarPag.isSelected()){
//            for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
//                objTblModDat.removeSystemHiddenColumn(j, tblDat);
//                objTblModTot.removeSystemHiddenColumn(j, tblTotDat);
//            }
//        }
//        else{
//            for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
//                objTblModDat.addSystemHiddenColumn(j, tblDat);
//                objTblModTot.addSystemHiddenColumn(j, tblTotDat);
//            }
//        }

    }//GEN-LAST:event_chkMosTotCarPagActionPerformed

    private void chkMosPedLleDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedLleDirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedLleDirActionPerformed

    private void chkMosPedLleIndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedLleIndActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMosPedLleIndActionPerformed

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained

    }//GEN-LAST:event_tblFixFocusGained

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void chkMosPagExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPagExtActionPerformed
        // TODO add your handling code here:
        if(chkMosPagExt.isSelected()){
            cargarValFacAboPen();
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
        }
    }//GEN-LAST:event_chkMosPagExtActionPerformed

    private void chkOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOrderActionPerformed
    if(chkMosPagExt.isSelected())
        butCon.doClick();       
    }//GEN-LAST:event_chkOrderActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butImp;
    private javax.swing.JCheckBox chkMosCarPag;
    private javax.swing.JCheckBox chkMosFecVenRea;
    private javax.swing.JCheckBox chkMosNotPed;
    private javax.swing.JCheckBox chkMosNotPedPre;
    private javax.swing.JCheckBox chkMosPagExt;
    private javax.swing.JCheckBox chkMosPedEmb;
    private javax.swing.JCheckBox chkMosPedLleDir;
    private javax.swing.JCheckBox chkMosPedLleInd;
    private javax.swing.JCheckBox chkMosTotCarPag;
    private javax.swing.JCheckBox chkOrder;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panAuxSpnTot;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPedTra;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptDat;
    private javax.swing.JPanel panRptFix;
    private javax.swing.JPanel panTotDat;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JScrollPane spnTotDat;
    private javax.swing.JScrollPane spnTotFix;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTable tblTotDat;
    private javax.swing.JTable tblTotFix;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomImp;
    // End of variables declaration//GEN-END:variables

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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run() 
        {
            if (!cargarReg())
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
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    private boolean cargarReg(){
        boolean blnRes=false;
        try{           
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());  
            if(con!=null){
                //Listado de Pedidos en tránsito
                if(eliminarColTblFix()){
                    if(eliminarColTblDat()){
                        if(eliminarColTblDatTot()){
                            if(eliminarColTblFixTot()){                                    
                                objTblFilCab=null;
                                objTblFilCabFix=null;
                                if(configurarTblFix()){
                                    if(configurarTblDat()){
                                        if(configurarTblDatTot()){
                                            if(configurarTblDatFixTot()){
                                                if(addColumnDat()){
                                                    if(cargarDetReg()){
                                                        if(cargarValFacAboPen()){
                                                            if(cargarValCarPagPed()){ 
                                                                if(cargarValTotCarPagPed()){
                                                                    if(cargarFecVenPagEst()){
                                                                        if(setColumnasEditables()){
                                                                            if(delRowValPenCero()){
                                                                                sppRpt.setDividerLocation(690);
                                                                                calculaTotalesTblFij();
                                                                                calculaTotalesTblMov();
                                                                                blnRes=true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }                        
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                con.close();
                con=null;                
                
                if(blnRes){
                    objTblModDat.initRowsState(); 
                    if (blnCon)
                        lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    else
                        lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                    butCon.setText("Consultar");
                    pgrSis.setIndeterminate(false);
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite configurar el número de columnas que se deben agregar al modelo de datos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */ 
    private boolean addColumnDat(){
        boolean blnRes=false;
        try{
            if(con!=null){

                if(addColumnAddFecVenEst()){
                    if(addColumnAddFecVenEstTot()){
                        if(addColumnAddEstReg()){
                            if(addColumnAddEstRegTot()){
                                if(addColumnsAddCarPag()){
                                    if(addColumnsAddCarPagTot()){
                                        if(addColumnsAddTotCarPag()){
                                            if(addColumnsAddTotCarPagTot()){
                                                objTblModTot.insertRows(1,0);
                                                blnRes=true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
    

    private boolean addColumnsAddCarPag(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
        intNumColIniCarPag=intNumColFinEstReg;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomCarPag="";
        try{
            intNumColAddCarPag=numeroColumnasAddCarPag();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del PRIMER PANEL
            
            objTblCelRenLblCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCarPag.setTipoFormato(objTblCelRenLblCarPag.INT_FOR_NUM);
            objTblCelRenLblCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblCarPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                java.awt.Color colFonColCru;
//                String strEstItmEli="";
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
//                }
                
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblCarPag.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblCarPag.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblCarPag.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblCarPag.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
                //Fin nuevo

            });

            for (int i=0; i<intNumColAddCarPag; i++){
                strNomCarPag="" + objUti.getStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_NOM_CAR_PAG);

                objUti.setStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_COL_CAR_PAG, "" + (intNumColIniCarPag+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniCarPag+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomCarPag);

                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                
//                if(!chkMosCarPag.isSelected()) {
//                    /* Ocultar columnas de cargo, solicitado por LSC. */
//                    tbc.setWidth(0);
//                    tbc.setResizable(false);
//                    tbc.setMaxWidth(0);
//                    tbc.setMinWidth(0);
//                    tbc.setPreferredWidth(0);
//                }
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblCarPag);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Cargos a Pagar");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                objTblHeaColGrpTit.add(tbc);
            }

            intNumColFinCarPag=objTblModDat.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
            objTblHeaColGrpFecEstEmb=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean addColumnsAddCarPagTot(){
        //System.out.println("agregarColumnasAdicionadasAniosTotales");
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            for (int i=0; i<intNumColAddCarPag; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniCarPag+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblTot);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblTot=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    
   /**
    * Función que permite obtener los cargos a pagar de
    * "O"  "FLETE APROXIMADO"
    * "V"  "VALOR DE LA FACTURA"
    * @return 
    */
    private int numeroColumnasAddCarPag(){
        int intNumColAdd=0;
        arlDatCarPag.clear();
        try{
            if(con!=null){
                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg";
                strSQL+=" FROM tbm_carpagimp AS a1";
                strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND tx_tipcarpag NOT IN('O', 'V')"; /* No mostrar cargo de valor factura, porque este valor se refleja en el total del pedido.*/
                //strSQL+=" WHERE a1.st_reg NOT IN('E','I') AND tx_tipcarpag NOT IN('O')";
                strSQL+=" ORDER BY a1.ne_ubi";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegCarPag=new ArrayList();
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COD_CAR_PAG, rst.getInt("co_carpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_NOM_CAR_PAG, rst.getString("tx_nom"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_TIP_CAR_PAG, rst.getString("tx_tipcarpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COL_CAR_PAG, "");
                    arlDatCarPag.add(arlRegCarPag);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                    
                intNumColAdd=arlDatCarPag.size();
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumColAdd;
   }

    private boolean addColumnsAddTotCarPag(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        intNumColIniTotCarPag=intNumColFinCarPag;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomCarPag="";
        try{
            intNumColAddTotCarPag=numeroColumnasAddTotCarPag();
            
            objTblCelRenLblTotCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblTotCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotCarPag.setTipoFormato(objTblCelRenLblTotCarPag.INT_FOR_NUM);
            objTblCelRenLblTotCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblTotCarPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {                
                //Inicio nuevo
                java.awt.Color colFonColRoj, colFonColYel;
                int intFil = -1;
                String strColFil="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFil = objTblCelRenLblTotCarPag.getRowRender();
                    strColFil=objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL)== null ? "N": objTblModDat.getValueAt(intFil, INT_TBL_DAT_EST_COL_FIL).toString();
                    colFonColRoj=new java.awt.Color(255, 0, 0);
                    colFonColYel=new java.awt.Color(255, 255, 0);
                    if(strColFil.equals("A")){ 
                        objTblCelRenLblTotCarPag.setBackground(colFonColYel);
                    }
                    else if(strColFil.equals("R")){
                        objTblCelRenLblTotCarPag.setBackground(colFonColRoj);
                    }
                    else {
                        objTblCelRenLblTotCarPag.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
                //Fin nuevo
                
                
                
            });
            
            for (int i=0; i<intNumColAddTotCarPag; i++){
                strNomCarPag="" + objUti.getStringValueAt(arlDatTotCarPag, i, INT_ARL_TOT_CAR_PAG_NOM);
                /* Se asigna el número de la columna de los totales de los cargos a pagar. */
                objUti.setStringValueAt(arlDatTotCarPag, i, INT_ARL_TOT_CAR_PAG_COL, "" + (intNumColIniTotCarPag+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniTotCarPag+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomCarPag);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(66);
                
//                if(!chkMosTotCarPag.isSelected()) {
//                    tbc.setWidth(0);
//                    tbc.setResizable(false);
//                    tbc.setMaxWidth(0);
//                    tbc.setMinWidth(0);
//                    tbc.setPreferredWidth(0);                
//                }                
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblTotCarPag);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                objTblHeaColGrpTit.add(tbc);
            }
            intNumColFinTotCarPag=objTblModDat.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    private boolean addColumnsAddTotCarPagTot(){
        //System.out.println("agregarColumnasAdicionadasAniosTotales");
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<intNumColAddTotCarPag; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniTotCarPag+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(66);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblTot);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblTot=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
            
            
            
            
            
    
   /**
    * Función que permite conocer los tipos de cargos agrupados, excepto
    * "O"  "FLETE APROXIMADO"
    * "V"  "VALOR DE LA FACTURA"
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */
    private int numeroColumnasAddTotCarPag(){
        int intNumColAdd=0;
        arlDatTotCarPag.clear();
        try{
            if(con!=null){
                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.tx_tipCarPag";
                strSQL+="       , CASE WHEN b1.tx_tipCarPag='A' THEN 'Ara.'";
                strSQL+="              WHEN b1.tx_tipCarPag='F' THEN 'Flet.'";
                strSQL+=" 	       WHEN b1.tx_tipCarPag='G' THEN 'Gas.'";
                strSQL+=" 	       WHEN b1.tx_tipCarPag='I' THEN 'Iva.'";
                strSQL+=" 	       ELSE '' END AS tx_nomTipCarPag";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.tx_tipCarPag";
                strSQL+=" 	FROM tbm_carPagImp AS a1";
                strSQL+=" 	WHERE a1.st_reg='A' AND a1.tx_tipCarPag IN("+strTipCarPag+")";
                strSQL+=" 	GROUP BY a1.tx_tipCarPag";
                strSQL+=" ) AS b1";
                strSQL+=" ORDER BY b1.tx_tipCarPag";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegTotCarPag=new ArrayList();
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_TIP, rst.getString("tx_tipCarPag"));
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_NOM, rst.getString("tx_nomTipCarPag"));
                    arlRegTotCarPag.add(INT_ARL_TOT_CAR_PAG_COL, "");
                    arlDatTotCarPag.add(arlRegTotCarPag);
                } 
                stm.close();
                stm=null;
                rst.close(); 
                rst=null;

                intNumColAdd=arlDatTotCarPag.size();
            }
        }
        catch(Exception e){ 
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumColAdd;
    }    

    /**
     * Función que permite adicionar la columna de Fechas de vencimientos Estimadas de los pedidos
     * @return 
     */
    private boolean addColumnAddFecVenEst(){
        boolean blnRes=true;
        String strSubTit="";
        ZafTblHeaGrp objTblHeaGrpTblFecPry = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrpTblFecPry.setHeight(16*3);    
        ZafTblHeaColGrp objTblHeaColGrpFec = null;
        ZafTblHeaColGrp objTblHeaColSubGrp= null;
        javax.swing.table.TableColumn tbc;
        intNumGrpAddFecVenEst=4;          //AP: Se estableció 4 columnas para grupo de las fechas de vencimiento Proyectadas.
        intNumColFecVenEst=4;             //Son las columnas que estan dentro de cada grupo de fecha de vencimiento.
        intNumColIniFecVenEst=intNumColEstDat; //numero de columnas que tiene el modelo antes de adicionar las columnas
        java.awt.Color colFonColPar, colFonColImp;
        colFonColPar = new java.awt.Color(255, 221, 187); //Color Naranja.
        colFonColImp = new java.awt.Color(228, 228, 203); //Color Verde Agua.        
        try{
            for (int i=0; i<intNumGrpAddFecVenEst; i++)
            {
                /*Creando Grupo "Fechas de Vencimiento Estimadas" */
                if(i==0){ 
                    objTblHeaColGrpFec=new ZafTblHeaColGrp("Fechas de Vencimiento");
                    objTblHeaColGrpFec.setHeight(16);
                }                    
                for(int j=0; j<intNumColFecVenEst; j++)
                {
                    int intIndColGrp =(intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna.
                    
                    arlRegFecVen=new ArrayList();
//                    arlRegFecVen.add(INT_ARL_FEC_VEN_DIA, );
//                    arlRegFecVen.add(INT_ARL_FEC_VEN_FEC, );
//                    arlRegFecVen.add(INT_ARL_FEC_VEN_POR, );
//                    arlRegFecVen.add(INT_ARL_FEC_VEN_VAL);
//                    arlRegFecVen.add(INT_ARL_FEC_VEN_COL, );
                    
                    //Dias
                    if(j==0){
                        arlRegFecVen.add(INT_ARL_FEC_VEN_DES, "0");
                    }
                    if(j==1){//FecVen.
                        arlRegFecVen.add(INT_ARL_FEC_VEN_DES, "1");//(intNumColIniFecVenEst+j)
                    }
                    if(j==2){//% Pa,
                        arlRegFecVen.add(INT_ARL_FEC_VEN_DES, "2");
                    }
                    if(j==3){//Val.Pag.
                        arlRegFecVen.add(INT_ARL_FEC_VEN_DES, "3");
                    }
                    arlRegFecVen.add(INT_ARL_FEC_VEN_COL, intIndColGrp);
                    
                    arlDatFecVen.add(arlRegFecVen);
                    
                    strSubTit = (j==0? "Días":(j==1?"Fec.Ven.":(j==2?"% Pag.":(j==3?"Val.Pag":""))));
                    tbc=new javax.swing.table.TableColumn( (intIndColGrp) );
                    tbc.setHeaderValue("" + strSubTit);
                    
                    if( (j==0) ) { //Día.Cré.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_INT, new Integer(0), 100);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        //objTblCelRenLblNumDin.setBackground(objParSis.getColorCamposObligatorios());
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(ZafTblCelRenLbl.INT_FOR_GEN);            
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), true, true); //Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin); 
                        
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);   
                        objTblCelRenLblNumDin=null;
                        
                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtDiaCre=new ZafTblCelEdiTxt();
                        //tbc.setCellRenderer(new RenderDecimales(objParSis.getDecimalesMostrar()) );
                        tbc.setCellEditor(objTblCelEdiTxtDiaCre);
                        objTblCelEdiTxtDiaCre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                            int intFil=-1;
                            int intCol=-1;
                            String strDat="";
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString());
                                if(objUti.isNumero(strDat)){
                                    calcularFecPagFecVenEstSel(intFil, intCol, intCalEditAfter);
                                }
                                else
                                    objTblModDat.setValueAt("", intFil, intCol);
                                
                            } 
                        });
                    }
                    else if (j==1) { //Fec.Ven.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);
                        
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblFec=new ZafTblCelRenLbl();
                        objTblCelRenLblFec.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                        objTblCelRenLblFec.setTipoFormato(objTblCelRenLblFec.INT_FOR_FEC);    
                        tbc.setCellRenderer(objTblCelRenLblFec);
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblFec.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblFec.setBackground(colFonColImp);  
                        objTblCelRenLblFec=null;
                        
                        ///////////////////////////////////////////////////////////////////////////////////////////
                        //Establecer JTable: Establecer columnas DataPicker
                        objDtePckEdiFecVenEst = new ZafDtePckEdi(strFormatoDatePicker);
                        tbc.setCellEditor(objDtePckEdiFecVenEst);
                        objDtePckEdiFecVenEst.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                            int intFil=-1;
                            int intCol=-1;
                            String strFecVenPag="";
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                                                
                                /* Fecha Embarque del pedido: Solo podrá modificarse la fecha de embarque estimada, la real no puede ser editada. */
                                strFecVenPag=(objTblModDat.getValueAt(intFil,intCol)==null)?"":objTblModDat.getValueAt(intFil, intCol).toString();
                                objDtePckEdiFecEmb.setText(strFecVenPag); //Se asigna la fecha de embarque al datePicker
                                if(objDtePckEdiFecEmb.isFecha()){ //Si existe fecha de Embarque real, se asigna la fecha de embarque real.
                                    objTblModDat.setValueAt(calcularNumDiaFecVen(intFil, intCol, strFecVenPag), intFil, (intCol-1));
                                }
                                else{ //Si no existe fecha de Embarque real, se asigna la fecha de embarque estimada.
                                    mostrarMsgInf("<HTML>El dato ingresado no corresponde a una fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                }
                            } 
                        });
                                                
                        ///////////////////////////////////////////////////////////////////////////////////////////
                    }
                    else if( (j==2) ) { // % Pag
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_INT, new Integer(0), 100);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(objTblCelRenLblNumDin.INT_FOR_NUM);                           
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), false, false); //No Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin);      
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);  
                        objTblCelRenLblNumDin=null;

                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtPorCre=new ZafTblCelEdiTxt();
                        tbc.setCellEditor(objTblCelEdiTxtPorCre);
                        objTblCelEdiTxtPorCre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                            int intFil=-1;
                            int intCol=-1;
                            String strDat="";
                            int intCodTipDoc=-1;
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                                intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                                strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()); 
                                if(objUti.isNumero(strDat)) {
                                    if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)<0){/* Valida que solo se pueda ingresar valores positivos. */
                                        objTblModDat.setValueAt("", intFil, intCol);
                                    }
                                    else{
                                        strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()); 
                                        if(objUti.isNumero(strDat)) {
                                            if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)>=0){/* Valida que solo se pueda ingresar valores positivos. */
                                                calcularValPagFecVenEstSel(intFil, intCol, intCalEditAfter);
                                                
                                                if(intCodTipDoc!=objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP){
                                                    if(isExcValPagFecVen_valPenPag(intFil)){//Si excede
                                                        mostrarMsgInf("<HTML>La suma de los valores a pagar exceden el valor pendiente de pago del pedido.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                                        objTblModDat.setValueAt("", intFil, intCol);
                                                        objTblModDat.setValueAt("", intFil, (intCol+1));
                                                    }
                                                }
                                                else
                                                    objTblModDat.setValueAt("", intFil, intCol);
                                            }
                                            else 
                                                objTblModDat.setValueAt("", intFil, intCol);  
                                        } 
                                        else
                                            objTblModDat.setValueAt("", intFil, intCol);  
                                    }
                                }
                                
                            }
                        });
                    }
                    else if( (j==3) ) { //Val.Pag.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);         
                        //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                        objTblModDat.setColumnDataType(intIndColGrp, objTblModDat.INT_COL_DBL, new Integer(0), null);    
                        //Configurar JTable: Renderizar celdas.
                        objTblCelRenLblNumDin = new ZafTblCelRenLbl();
                        objTblCelRenLblNumDin.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                        objTblCelRenLblNumDin.setTipoFormato(objTblCelRenLblNumDin.INT_FOR_NUM);                           
                        objTblCelRenLblNumDin.setFormatoNumerico(objParSis.getFormatoNumero(), true, false); //Muestra 0
                        tbc.setCellRenderer(objTblCelRenLblNumDin); 
                        //Configurar JTable: Establecer color en celdas
                        if((i % 2)==0) 
                            objTblCelRenLblNumDin.setBackground(colFonColPar);                       
                        else
                            objTblCelRenLblNumDin.setBackground(colFonColImp);  
                        objTblCelRenLblNumDin=null;
                                                
                        //Configurar JTable: Editor celdas.
                        objTblCelEdiTxtValPag=new ZafTblCelEdiTxt();
                        tbc.setCellEditor(objTblCelEdiTxtValPag);
                        objTblCelEdiTxtValPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() { 
                            int intFil=-1;
                            int intCol=-1;
                            int intCodTipDoc=-1;
                            String strDat="";
                            BigDecimal bdeValPag=BigDecimal.ZERO;
                            BigDecimal bdeValPen=BigDecimal.ZERO;
                            BigDecimal bdeValPor=BigDecimal.ZERO;
                            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                intFil=tblDat.getSelectedRow();
                                intCol=tblDat.getSelectedColumn();
                                intCodTipDoc=Integer.parseInt(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()));
                            }
                            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                                if(intCodTipDoc!=objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP){
                                    if(isExcValPagFecVen_valPenPag(intFil)){//Si excede
                                        mostrarMsgInf("<HTML>La suma de los valores a pagar exceden el valor pendiente de pago del pedido.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                        objTblModDat.setValueAt("", intFil, intCol);
                                        objTblModDat.setValueAt("", intFil, (intCol-1));
                                    }
                                    else{
                                        strDat=objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()); 
                                        if(objUti.isNumero(strDat)) {
                                            if( new BigDecimal (strDat).compareTo(BigDecimal.ZERO)<0){/* Valida que solo se pueda ingresar valores positivos. */
                                                objTblModDat.setValueAt("", intFil, intCol);
                                            }
                                            else{
                                                bdeValPag=new BigDecimal(objTblModDat.getValueAt(intFil, intCol)==null?"0":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModDat.getValueAt(intFil, intCol).toString()));
                                                bdeValPag=objUti.redondearBigDecimal(bdeValPag, objParSis.getDecimalesMostrar());
                                                bdeValPen=new BigDecimal(objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                                                bdeValPen=objUti.redondearBigDecimal(bdeValPen, objParSis.getDecimalesMostrar());
                                                bdeValPor=(bdeValPag.multiply(BigDecimal.valueOf(100))).divide(bdeValPen, objParSis.getDecimalesMostrar());

                                                objTblModDat.setValueAt(bdeValPor, intFil, (intCol-1));
                                            }
                                        }
                                    }
                                }
                                else{
                                    objTblModDat.setValueAt("", intFil, intCol);
                                    objTblModDat.setValueAt("", intFil, (intCol-1));
                                }

                            }
                        });
                        
                    }
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(!chkMosFecVenRea.isSelected()) {
                        /* Ocultar columnas de fechas de vencimiento, de acuerdo al filtro. */
                        tbc.setWidth(0); 
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);                
                    }                    

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblModDat.addColumn(tblDat, tbc);
                    
                    //Configurar JTable: Agrupamiento de Columnas
                    if(j==0) //Primera Columna del Grupo.
                    {
                        objTblHeaColSubGrp=new ZafTblHeaColGrp("Fecha Vencimiento #"+ (i+1) );
                        objTblHeaColSubGrp.setHeight(16);
                        objTblHeaColGrpFec.add(objTblHeaColSubGrp); 
                    } 
                    objTblHeaGrpTblFecPry.addColumnGroup(objTblHeaColGrpFec);  
                    objTblHeaColSubGrp.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                }
            }
            
            System.out.println("arlDatFecVen: " + arlDatFecVen.toString());
            System.out.println("intNumColIniFecVenEst: " + intNumColIniFecVenEst);
            
            intNumColFinFecVenEst=objTblModDat.getColumnCount();
            objTblHeaGrpTblFecPry=null;
            objTblHeaColGrpFec=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){ 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e); 
        } 
        return blnRes;
    }
    
    
    
    
    private boolean addColumnAddFecVenEstTot(){
        //System.out.println("agregarColumnasAdicionadasAniosTotales");
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico("###,###,##0",false,true);
            
            for (int i=0; i<intNumGrpAddFecVenEst; i++){
                for(int j=0; j<intNumColFecVenEst; j++){
                    tbc=new javax.swing.table.TableColumn(intNumColFecVenEst+i);
                    tbc.setHeaderValue("");
                    
                    if( (j==0) ) { //Día.Cré.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblTot);
                    }
                    else if (j==1) { //Fec.Ven.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblTot);
                    }
                    else if( (j==2) ) { // % Pag
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(42);
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblTot);                        
                    }
                    else if( (j==3) ) { //Val.Pag.
                        //Configurar JTable: Establecer el ancho de la columna.
                        tbc.setPreferredWidth(70);
                        //Configurar JTable: Renderizar celdas.
                        tbc.setCellRenderer(objTblCelRenLblTot);
                    }
                    
                    //Configurar JTable: Establecer el ancho de la columna.
                    if(!chkMosFecVenRea.isSelected()) {
                        /* Ocultar columnas de fechas de vencimiento, de acuerdo al filtro. */
                        tbc.setWidth(0); 
                        tbc.setResizable(false);
                        tbc.setMaxWidth(0);
                        tbc.setMinWidth(0);
                        tbc.setPreferredWidth(0);                
                    }                    

                    //Configurar JTable: Agregar la columna al JTable.
                    objTblModTot.addColumn(tblTotDat, tbc);
                }
            }
            objTblCelRenLblTot=null;
            ///////////////////////////////////////////////////////////////////////////////////
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    /**
     * Función que permite adicionar la columna de Estado de los pedidos
     * @return 
     */
    private boolean addColumnAddEstReg(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAddEstReg=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniEstReg=intNumColFinFecVenEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblEst=new ZafTblCelRenLbl();
            objTblCelRenLblEst.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            objTblCelRenLblEst.setTipoFormato(objTblCelRenLblEst.INT_FOR_GEN);
            objTblCelRenLblEst.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblModDat.getValueAt(objTblCelRenLblEst.getRowRender(), intNumColIniEstReg)==null?"":objTblModDat.getValueAt(objTblCelRenLblEst.getRowRender(), intNumColIniEstReg).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblEst.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblEst.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddEstReg; i++){
                String strSubTit=("Est.");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstReg+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblEst);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModDat.addColumn(tblDat, tbc);
            }
            intNumColFinEstReg=objTblModDat.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean addColumnAddEstRegTot(){
        //System.out.println("agregarColumnasAdicionadasAniosTotales");
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddEstReg; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniEstReg+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblTot);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblTot=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado. Pedidos embarcados
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        int intOrdFil=0;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            String strAuxFil="";
            if (con!=null){
                stm=con.createStatement();
                
                //objZafImp.getUltimaInstanciaPedidos(con);    //Cargar listado de pedidos: Última Instancia.
                objZafImp.getOpenNotaPedido(con);              //Cargar listado de Notas de Pedido Abiertas.
                objZafImp.getOpenPedidoEmbarcado(con);         //Cargar listado de Pedido Embarcado Abiertos.
                objZafImp.getOpenIngresoImportacion(con);      //Cargar listado de Ingreso por Importación Abiertos.
                
                //Filtro
                //Fecha de documento
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAuxFil+=" AND y1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAuxFil+=" AND y1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAuxFil+=" AND y1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                /* Otros Filtros */
                strAuxFil+=txtCodImp.getText().equals("")?"":" AND y1.co_imp=" + txtCodImp.getText() + "";
                strAuxFil+=txtCodExp.getText().equals("")?"":" AND y1.co_exp=" + txtCodExp.getText() + "";
                
                //Armar sentencia SQL
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCorTipDoc, a1.tx_desLarTipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numPedCon";
                strSQL+="      , CASE WHEN a1.ne_tipNotPed=1 THEN 'TM FOB' WHEN a1.ne_tipNotPed=2 THEN 'TM CFR'";
                strSQL+="             WHEN a1.ne_tipNotPed=3 THEN 'PZA FOB' WHEN a1.ne_tipNotPed=4 THEN 'PZA CFR' ELSE '' END AS tx_tipNotPed";
                strSQL+="      , a1.ne_tipNotPed, a1.tx_numPed, a1.fe_doc, a1.fe_preImp"; 
                strSQL+="      , a1.fe_embEst, a1.fe_embRea, a1.fe_emb, a1.fe_arr, a1.fe_pue";
                strSQL+="      , a1.co_imp, a1.tx_nomEmpImp, a1.co_exp, a1.tx_nomExp, a1.st_reg, a1.st_regPed";
                strSQL+="      , a1.fe_colPed, a1.tx_insPed, a1.st_ciePagAra, a1.st_Color, a1.nd_valCarPag, a1.tx_desForPag, a1.tx_modPed, a1.tx_numReq, a1.tx_refPed, a1.fe_pro";
                strSQL+="      , a1.co_ciu, a1.tx_desCorCiuPueEmb, a1.tx_desLarCiuPueEmb";
                strSQL+="      , a1.nd_valPagAnt, a1.nd_porCrePagAnt, a1.co_usrIngAnt, a1.fe_ingAnt, a1.st_autAnt, a1.co_usrIngAutAnt, a1.fe_ingAutAnt";
                strSQL+=" FROM(\n";
                                
                //<editor-fold defaultstate="collapsed" desc="/*NOTA DE PEDIDO*/">
                if(chkMosNotPedPre.isSelected()){
                    strSQL+="     SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_preImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="          , CAST(0 AS SMALLINT ) AS co_imp, CAST('' AS CHARACTER VARYING ) AS tx_nomEmpImp";
                    strSQL+="          , CAST(0 AS SMALLINT ) AS co_exp, CAST('' AS CHARACTER VARYING ) AS tx_nomExp, CAST(0 AS SMALLINT ) AS ne_tipNotPed";
                    strSQL+="          , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS fe_embEst, CAST('' AS CHARACTER VARYING) AS fe_embRea, CAST('' AS CHARACTER VARYING) AS fe_emb";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS fe_pue, CAST('' AS CHARACTER VARYING) AS fe_arr";
                    strSQL+="          , z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS tx_numPedCon, 0 AS nd_valCarPag, CAST('' AS CHARACTER VARYING) AS tx_desForPag";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS tx_modPed, z.tx_numReq";
                    strSQL+="          , z.tx_refPed, NULL AS fe_pro, CAST(0 AS SMALLINT ) AS co_ciu";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS tx_desCorCiuPueEmb, CAST('' AS CHARACTER VARYING) AS tx_desLarCiuPueEmb";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS nd_valPagAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS nd_porCrePagAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS co_usrIngAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS fe_ingAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS st_autAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS co_usrIngAutAnt";
                    strSQL+="          , CAST('' AS CHARACTER VARYING) AS fe_ingAutAnt";
                    strSQL+="     FROM (";
                    strSQL+="               SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                    strSQL+="                , a1.ne_numDoc, a1.tx_numDoc2 AS tx_numPed";
                    strSQL+="                , CAST( 'NOPEPR' AS CHARACTER VARYING) AS tx_insPed";
                    strSQL+="                , '3' AS st_ins, CAST('Nota de Pedido Previa' AS CHARACTER VARYING) AS tx_insPedImp";
                    strSQL+="                , ( (''||a1.ne_ani) ||'-'|| (  (CASE WHEN a1.ne_mes<=9 THEN '0' ELSE ''END)||'' ||a1.ne_mes) ||'-'|| '01')  AS fe_preImp";
                    strSQL+="                , CAST('N' AS CHARACTER VARYING ) AS st_ciePagAra";
                    strSQL+="                , CAST('N' AS CHARACTER VARYING) AS st_regPed";
                    strSQL+="                , CAST('N' AS CHARACTER VARYING) AS st_color";
                    strSQL+="                , b1.tx_desCor AS tx_desCorTipDoc, b1.tx_desLar AS tx_desLarTipDoc";
                    strSQL+="                , a1.st_reg, a1.fe_colPed, a1.tx_numReq";
                    strSQL+="                , CASE WHEN a1.tx_obs2 IS NULL THEN e2.tx_des ELSE a1.tx_obs2 END AS tx_refPed";
                    strSQL+="                FROM (tbm_cabNotPedPreImp AS a1 INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc)";
                    strSQL+="                LEFT OUTER JOIN (tbr_detPreImp AS e1 INNER JOIN tbm_detPreImp AS e2 ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc";
                    strSQL+="                        AND e1.co_tipDoc=e2.co_tipDoc AND e1.co_doc=e2.co_doc AND e1.co_reg=e2.co_reg)";
                    strSQL+="                       ON a1.co_emp=e1.co_empRelCabNotPedPreImp AND a1.co_loc=e1.co_locRelCabNotPedPreImp";
                    strSQL+="                       AND a1.co_tipDoc=e1.co_tipDocRelCabNotPedPreImp AND a1.co_doc=e1.co_docRelCabNotPedPreImp";
                    strSQL+="                WHERE a1.st_reg='A'";
                    strSQL+="                AND a1.st_cie='N' AND a1.fe_colPed IS NOT NULL";
                    strSQL+="                ORDER BY a1.fe_doc, a1.tx_numDoc2";
                    strSQL+="    ) AS z";
                }
                //</editor-fold>
                
                if( (chkMosNotPedPre.isSelected()) && (chkMosNotPed.isSelected()) ) {
                    strSQL+="        UNION\n";
                }
                
                //<editor-fold defaultstate="collapsed" desc="/*NOTA DE PEDIDO*/">
                if(chkMosNotPed.isSelected()){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_preImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , (''||z.fe_emb) AS fe_embEst, CAST('' AS CHARACTER VARYING) AS fe_embRea, (''||z.fe_emb) AS fe_emb\n";
                    strSQL+="      , (''||z.fe_pue) AS fe_pue, (''||z.fe_arr) AS fe_arr";
                    strSQL+=" 	   , z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color\n";
                    strSQL+=" 	   , z.tx_numPedCon, z.nd_valCarPag, z.tx_desForPag, z.tx_modPed, z.tx_numReq, z.tx_refPed, z.fe_pro, z.co_ciu, z.tx_desCorCiuPueEmb, z.tx_desLarCiuPueEmb";

                    strSQL+="      , CAST(z.nd_valPagAnt AS CHARACTER VARYING) AS nd_valPagAnt";
                    strSQL+="      , CAST(z.nd_porCrePagAnt AS CHARACTER VARYING) AS nd_porCrePagAnt";
                    strSQL+="      , CAST(z.co_usrIngAnt AS CHARACTER VARYING) AS co_usrIngAnt";
                    strSQL+="      , CAST(z.fe_ingAnt AS CHARACTER VARYING) AS fe_ingAnt";
                    
                    strSQL+="      , CAST(z.st_autAnt AS CHARACTER VARYING) AS st_autAnt";
                    strSQL+="      , CAST(z.co_usrIngAutAnt AS CHARACTER VARYING) AS co_usrIngAutAnt";
                    strSQL+="      , CAST(z.fe_ingAutAnt AS CHARACTER VARYING) AS fe_ingAutAnt";
                    
                    strSQL+=" FROM (\n";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="		 , y1.fe_emb, y1.fe_pue, y1.fe_arr";  
                    strSQL+="            , CAST( 'NOTPED' AS CHARACTER VARYING) AS tx_insPed, CAST('N' AS CHARACTER VARYING ) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_regPed";
                    strSQL+="            , (CASE WHEN ( CAST (y1.fe_emb AS DATE) < CURRENT_DATE)  THEN 'R' ELSE  (CASE WHEN ( (CAST (y1.fe_emb AS DATE) - CURRENT_DATE) BETWEEN 0 AND 30 )  THEN 'A' ELSE 'N' END) END) AS st_color";
                    strSQL+="            , b1.nd_valCarPag, c1.tx_desLar AS tx_desForPag, y1.tx_modPed, y3.tx_numReq, y3.tx_refPed";
                    strSQL+="            , CAST('' AS CHARACTER VARYING) AS tx_numPedCon, y1.fe_pro";
                    strSQL+="            , d1.co_ciu, d1.tx_desCor AS tx_desCorCiuPueEmb, d1.tx_desLar AS tx_desLarCiuPueEmb";//Puerto de embarque
                    strSQL+="            , y1.nd_valPagAnt, y1.nd_porCrePagAnt, y1.co_usrIngAnt, y1.fe_ingAnt, y1.st_autAnt, y1.co_usrIngAutAnt, y1.fe_ingAutAnt";
                    strSQL+="       FROM( "+objZafImp.getSQLNotaPedidoInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN (((tbm_cabNotPedImp AS y1 LEFT OUTER JOIN tbm_ciu AS d1 ON y1.co_ciuPueEmb=d1.co_ciu)";
                    strSQL+="                               LEFT OUTER JOIN tbm_forPagImp AS c1 ON y1.co_forPag=c1.co_forPag)";
                    strSQL+="                   INNER JOIN tbm_carPagNotPedImp AS b1 ON y1.co_emp=b1.co_emp AND y1.co_loc=b1.co_loc AND y1.co_tipDoc=b1.co_tipDoc AND y1.co_doc=b1.co_doc";
                    strSQL+="                   AND b1.co_carPag=" + objZafImp.INT_COD_CAR_PAG_VAL_FAC + ")";
                    
                    strSQL+="       ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       INNER JOIN (";
                    strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numReq";
                    strSQL+="            , array_to_string(array_agg(a.tx_refPed), '; ') AS tx_refPed";
                    strSQL+="            FROM(";
                    strSQL+="                   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";
                    strSQL+="                       , ( (''||a1.ne_ani) ||'-'|| (  (CASE WHEN a1.ne_mes<=9 THEN '0' ELSE ''END)||'' ||a1.ne_mes) ||'-'|| '01')  AS fe_preImp\n";
                    strSQL+="                       , a1.tx_numReq";
                    strSQL+="                       , CASE WHEN a1.tx_obs2 IS NULL THEN e2.tx_des ELSE a1.tx_obs2 END AS tx_refPed";
                    strSQL+="                   FROM tbm_cabNotPedImp AS a "; 
                    strSQL+="                   INNER JOIN (tbm_cabNotPedPreImp AS a1 LEFT OUTER JOIN (tbr_detPreImp AS e1 INNER JOIN tbm_detPreImp AS e2 ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc";
                    strSQL+="                                                                   AND e1.co_tipDoc=e2.co_tipDoc AND e1.co_doc=e2.co_doc AND e1.co_reg=e2.co_reg)";
                    strSQL+="                           ON a1.co_emp=e1.co_empRelCabNotPedPreImp AND a1.co_loc=e1.co_locRelCabNotPedPreImp";
                    strSQL+="                           AND a1.co_tipDoc=e1.co_tipDocRelCabNotPedPreImp AND a1.co_doc=e1.co_docRelCabNotPedPreImp)";
                    strSQL+="                   ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_locRel AND a.co_tipDoc=a1.co_tipDocRel AND a.co_doc=a1.co_docRel";
                    strSQL+="                   WHERE a1.st_reg IN ('A') AND a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL) ";
                    strSQL+="            ) AS a";
                    strSQL+="            GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numReq";
                    
                    strSQL+="       ) AS y3 ON y.co_emp=y3.co_emp AND y.co_loc=y3.co_loc AND y.co_tipDoc=y3.co_tipDoc AND y.co_doc=y3.co_doc ";
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    //strSQL+="       AND y.st_notPedLis='N'";  //Se comenta validación porque AP necesita que se muestren las notas de pedido listas, aun cuando no tengan pedidos embarcados
                    strSQL+="       " + strAuxFil; 
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+="       	   , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+="		   , y1.fe_emb, y1.fe_pue, y1.fe_arr, b1.nd_valCarPag, c1.tx_desLar, y1.tx_modPed, y3.tx_numReq, y3.tx_refPed, y1.fe_pro, d1.co_ciu, d1.tx_desCor, d1.tx_desLar";
                    strSQL+="              , y1.nd_valPagAnt, y1.nd_porCrePagAnt, y1.co_usrIngAnt, y1.fe_ingAnt, y1.st_autAnt, y1.co_usrIngAutAnt, y1.fe_ingAutAnt";
                    strSQL+=" ) AS z\n";   
                }
                //</editor-fold>
                
                if( (chkMosNotPed.isSelected()) && (chkMosPedEmb.isSelected()) ) {
                    strSQL+="        UNION\n";
                }

                //<editor-fold defaultstate="collapsed" desc="/*PEDIDO EMBARCADO*/">
                if((chkMosPedEmb.isSelected())){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_PreImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , z.fe_embEst, z.fe_embRea, z.fe_emb, z.fe_pue, z.fe_arr, z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color";
                    strSQL+=" 	   , z.tx_numPedCon, z.nd_valCarPag, z.tx_desForPag, z.tx_modPed, z.tx_numReq, z.tx_refPed, z.fe_pro, z.co_ciu, z.tx_desCorCiuPueEmb, z.tx_desLarCiuPueEmb";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS nd_valPagAnt, CAST( '' AS CHARACTER VARYING) AS nd_porCrePagAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS co_usrIngAnt, CAST( '' AS CHARACTER VARYING) AS fe_ingAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS st_autAnt, CAST( '' AS CHARACTER VARYING) AS co_usrIngAutAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS fe_ingAutAnt";
                    strSQL+=" FROM (";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="            , (CASE WHEN y1.st_emb IN ('N') THEN  (''||y1.fe_emb) ELSE CAST('' AS CHARACTER VARYING) END) AS fe_embEst";
                    strSQL+="            , (CASE WHEN y1.st_emb IN ('N') THEN  CAST('' AS CHARACTER VARYING) ELSE (''||y1.fe_emb) END) AS fe_embRea, (''||y1.fe_emb) AS fe_emb";
                    strSQL+=" 	         , ''||y1.fe_pue AS fe_pue, ''||y1.fe_arr AS fe_arr";
                    strSQL+="            , CAST( 'PEDEMB' AS CHARACTER VARYING) AS tx_insPed, ( CASE WHEN y1.st_ciePagImp IS NULL THEN 'S' ELSE y1.st_ciePagImp END ) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_color";
                    strSQL+="            , CAST('P' AS CHARACTER VARYING) AS st_regPed";
                    strSQL+="            , y1.tx_numDocCon AS tx_numPedCon, b1.nd_valCarPag, c1.tx_desLar AS tx_desForPag, y1.tx_modPed, y3.tx_numReq, y3.tx_refPed, y3.fe_pro";
                    strSQL+="            , d1.co_ciu, d1.tx_desCor AS tx_desCorCiuPueEmb, d1.tx_desLar AS tx_desLarCiuPueEmb";//Puerto de embarque
                    strSQL+="       FROM( "+objZafImp.getSQLPedidoEmbarcadoInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN (((tbm_cabPedEmbImp AS y1 LEFT OUTER JOIN tbm_ciu AS d1 ON y1.co_ciuPueEmb=d1.co_ciu)";
                    strSQL+="       LEFT OUTER JOIN tbm_forPagImp AS c1 ON y1.co_forPag=c1.co_forPag)";
                    strSQL+="                   INNER JOIN tbm_carPagPedEmbImp AS b1 ON y1.co_emp=b1.co_emp AND y1.co_loc=b1.co_loc AND y1.co_tipDoc=b1.co_tipDoc AND y1.co_doc=b1.co_doc";
                    strSQL+="                           AND b1.co_carPag=" + objZafImp.INT_COD_CAR_PAG_VAL_FAC + ")";                    
                    strSQL+="       ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       LEFT OUTER JOIN (";
                    strSQL+="               SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numReq, a.fe_pro";
                    strSQL+="               , array_to_string(array_agg(a.tx_refPed), '; ') AS tx_refPed";
                    strSQL+="               FROM(";                    
                    strSQL+="                   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";
                    strSQL+="                       , ( (''||a4.ne_ani) ||'-'|| (  (CASE WHEN a4.ne_mes<=9 THEN '0' ELSE ''END)||'' ||a4.ne_mes) ||'-'|| '01')  AS fe_preImp, a4.tx_numReq, a3.fe_pro";
                    strSQL+="                       , CASE WHEN a4.tx_obs2 IS NULL THEN e2.tx_des ELSE a4.tx_obs2 END AS tx_refPed";
                    strSQL+="                   FROM tbm_cabPedEmbImp AS a";
                    strSQL+="                   INNER JOIN tbm_detPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                    strSQL+="                   INNER JOIN tbm_detNotPedImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_doc AND a1.co_regRel=a2.co_reg";
                    strSQL+="                   INNER JOIN tbm_cabNotPedImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                    strSQL+="                   INNER JOIN (tbm_cabNotPedPreImp AS a4 LEFT OUTER JOIN (tbr_detPreImp AS e1 INNER JOIN tbm_detPreImp AS e2 ON e1.co_emp=e2.co_emp AND e1.co_loc=e2.co_loc";
                    strSQL+="                                                                   AND e1.co_tipDoc=e2.co_tipDoc AND e1.co_doc=e2.co_doc AND e1.co_reg=e2.co_reg)";
                    strSQL+="                           ON a4.co_emp=e1.co_empRelCabNotPedPreImp AND a4.co_loc=e1.co_locRelCabNotPedPreImp";
                    strSQL+="                           AND a4.co_tipDoc=e1.co_tipDocRelCabNotPedPreImp AND a4.co_doc=e1.co_docRelCabNotPedPreImp)";
                    strSQL+="                   ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel";
                    strSQL+="                   WHERE a.st_reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL) ";
                    strSQL+="                   AND a3.st_reg IN ('A') AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL) AND a4.st_reg IN ('A')"; 
                    strSQL+="                   GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a4.ne_ani, a4.ne_mes, a4.tx_numReq, a4.tx_obs2, e2.tx_des, a3.fe_pro";
                    strSQL+="           ) AS a";
                    strSQL+="           GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numReq, a.fe_pro";
                    strSQL+="       ) AS y3 ON y.co_emp=y3.co_emp AND y.co_loc=y3.co_loc AND y.co_tipDoc=y3.co_tipDoc AND y.co_doc=y3.co_doc ";                    
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    strSQL+="       " + strAuxFil;
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y3.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	           , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+="              , y1.st_emb, y1.fe_emb, y1.fe_pue, y1.fe_arr, y1.st_ciePagImp, y1.tx_numDocCon, b1.nd_valCarPag, c1.tx_desLar";
                    strSQL+="              , y1.tx_modPed, y3.tx_numReq, y3.tx_refPed, y3.fe_pro, d1.co_ciu, d1.tx_desCor, d1.tx_desLar";
                    strSQL+=" ) AS z\n";
                }
                //</editor-fold>
                
                if( ( (chkMosNotPed.isSelected()) || (chkMosPedEmb.isSelected()) )  &&  ( (chkMosPedLleDir.isSelected()) || (chkMosPedLleInd.isSelected()) )  )
                {
                    strSQL+="        UNION\n";
                }
                
                //<editor-fold defaultstate="collapsed" desc="/*INGRESOS POR IMPORTACION */">
                if( (chkMosPedLleDir.isSelected())  || (chkMosPedLleInd.isSelected()) ){
                    strSQL+=" SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.fe_doc, z.fe_PreImp, z.ne_numDoc, z.tx_numPed";
                    strSQL+="      , z.co_imp, z.tx_nomEmpImp, z.co_exp, z.tx_nomExp, z.ne_tipNotPed";
                    strSQL+=" 	   , z.tx_desCorTipDoc, z.tx_desLarTipDoc, z.st_reg, z.fe_colPed";     
                    strSQL+="      , z.fe_embEst, z.fe_embRea, z.fe_emb, z.fe_arr, z.fe_pue, z.tx_insPed, z.st_ciePagAra, z.st_regPed, z.st_color";
                    strSQL+=" 	   , z.tx_numPedCon, z.nd_valCarPag, CAST( '' AS CHARACTER VARYING) AS tx_desForPag, z.tx_modPed, z.tx_numReq, z.tx_refPed, z.fe_pro";
                    strSQL+="      , z.co_ciu, z.tx_desCorCiuPueEmb, z.tx_desLarCiuPueEmb";
                    //strSQL+="      , z.nd_valPagAnt, z.nd_porCrePagAnt, z.co_usrIngAnt, z.fe_ingAnt, z.st_autAnt, z.co_usrIngAutAnt, z.fe_ingAutAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS nd_valPagAnt, CAST( '' AS CHARACTER VARYING) AS nd_porCrePagAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS co_usrIngAnt, CAST( '' AS CHARACTER VARYING) AS fe_ingAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS st_autAnt, CAST( '' AS CHARACTER VARYING) AS co_usrIngAutAnt";
                    strSQL+=" 	   , CAST( '' AS CHARACTER VARYING) AS fe_ingAutAnt";
                    strSQL+=" FROM (";
                    strSQL+="       SELECT y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y4.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="            , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	         , y2.tx_desCor AS tx_desCorTipDoc, y2.tx_desLar AS tx_desLarTipDoc, y1.st_reg, y1.fe_colPed";
                    strSQL+="            , CAST('' AS CHARACTER VARYING) AS fe_embEst, ''||y3.fe_Emb AS fe_embRea, ''||y3.fe_Emb AS fe_emb";
                    strSQL+=" 	         , ''||y.fe_doc AS fe_arr, ''||y3.fe_pue AS fe_pue";
                    strSQL+="            , CAST( 'INIMPO' AS CHARACTER VARYING) AS tx_insPed, CAST( 'S' AS CHARACTER VARYING) AS st_ciePagAra";
                    strSQL+="            , CAST('N' AS CHARACTER VARYING) AS st_color";
                    strSQL+="            , CAST('P' AS CHARACTER VARYING) AS st_regPed";
                    strSQL+="            , y4.tx_numDocCon AS tx_numPedCon, b1.nd_valCarPag, y1.tx_modPed, y4.tx_numReq, y4.tx_refPed, y4.fe_pro";
                    strSQL+="            , y4.co_ciu, y4.tx_desCorCiuPueEmb, y4.tx_desLarCiuPueEmb";
                    strSQL+="       FROM( "+objZafImp.getSQLIngresoImportacionInstanciaOpen()+"  ) AS y"; 
                    strSQL+="       INNER JOIN (tbm_cabMovInv AS y1 INNER JOIN tbm_carPagMovInv AS b1 ON y1.co_emp=b1.co_emp AND y1.co_loc=b1.co_loc AND y1.co_tipDoc=b1.co_tipDoc AND y1.co_doc=b1.co_doc";
                    strSQL+="                   AND b1.co_carPag=" + objZafImp.INT_COD_CAR_PAG_VAL_FAC + ")";
                    
                    strSQL+="       ON y.co_emp=y1.co_emp AND y.co_loc=y1.co_loc AND y.co_tipDoc=y1.co_tipDoc AND y.co_doc=y1.co_doc"; 
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS y2 ON y1.co_emp=y2.co_emp AND y1.co_loc=y2.co_loc AND y1.co_tipDoc=y2.co_tipDoc"; 
                    strSQL+="       INNER JOIN tbm_cabPedEmbImp AS y3 ON y1.co_empRelPedEmbImp=y3.co_emp AND y1.co_locRelPedEmbImp=y3.co_loc AND y1.co_tipDocRelPedEmbImp=y3.co_tipDoc AND y1.co_docRelPedEmbImp=y3.co_doc"; 
                    strSQL+="       LEFT OUTER JOIN (";
                    strSQL+="            SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numDocCon, a.tx_numReq, a.fe_pro";
                    strSQL+="            , array_to_string(array_agg(a.tx_refPed), '; ') AS tx_refPed";
                    strSQL+="            , a.co_ciu, a.tx_desCorCiuPueEmb, a.tx_desLarCiuPueEmb";
                    strSQL+="            FROM(";
                    /*Nuevo-inicio*/
                    
                    strSQL+="                   SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b2.fe_preImp, b2.tx_numDocCon, b2.tx_numReq";
                    strSQL+="                   , b2.tx_refPed, b2.fe_pro, b2.co_ciu, b2.tx_desCorCiuPueEmb, b2.tx_desLarCiuPueEmb";
                    strSQL+="                   FROM(";
                    strSQL+="                   	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                    strSQL+="                   	FROM tbm_cabMovInv AS a1";
                    strSQL+="                   	WHERE a1.co_tipdoc=" + objZafImp.INT_COD_TIP_DOC_ING_IMP + " and a1.st_reg='A'";
                    strSQL+="                   	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                    strSQL+="                   ) AS b1";
                    strSQL+="                   INNER JOIN(";
                    strSQL+="                   	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+="                   	, ( (''||a5.ne_ani) ||'-'|| (  (CASE WHEN a5.ne_mes<=9 THEN '0' ELSE ''END)||'' ||a5.ne_mes) ||'-'|| '01')  AS fe_preImp";
                    strSQL+="                   	, a1.tx_numDocCon,  a5.tx_numReq, a5.tx_obs2 AS tx_refPed, a4.fe_pro";
                    strSQL+="                   	, d1.co_ciu, d1.tx_desCor AS tx_desCorCiuPueEmb, d1.tx_desLar AS tx_desLarCiuPueEmb";//Puerto de embarque
                    strSQL+="                   	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_ciu AS d1 ON a1.co_ciuPueEmb=d1.co_ciu)";
                    strSQL+="                   	INNER JOIN tbm_detPedEmbImp AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="                   	INNER JOIN tbm_detNotPedImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_tipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc AND a2.co_regRel=a3.co_reg";
                    strSQL+="                   	INNER JOIN tbm_cabNotPedImp AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="                   	INNER JOIN tbm_cabNotPedPreImp AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_locRel AND a4.co_tipDoc=a5.co_tipDocRel AND a4.co_doc=a5.co_docRel";
                    strSQL+="                   	WHERE a1.st_reg IN ('A')  AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL)";
                    strSQL+="                   	AND a4.st_reg IN ('A')  AND (a4.st_doc IN ('A') OR a4.st_doc IS NULL) AND a5.st_reg IN ('A')";
                    strSQL+="                   	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.ne_ani, a5.ne_mes, a1.tx_numDocCon, a5.tx_numReq, a5.tx_obs2,a4.fe_pro";
                    strSQL+="                   	, d1.co_ciu, d1.tx_desCor, d1.tx_desLar";
                    strSQL+="                   ) AS b2";
                    strSQL+="                   ON b1.co_empRelPedEmbImp=b2.co_emp AND b1.co_locRelPedEmbImp=b2.co_loc AND b1.co_tipDocRelPedEmbImp=b2.co_tipDoc AND b1.co_docRelPedEmbImp=b2.co_doc";
                    strSQL+="                   GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b2.fe_preImp, b2.tx_numDocCon, b2.tx_numReq";
                    strSQL+="                   , b2.tx_refPed, b2.fe_pro, b2.co_ciu, b2.tx_desCorCiuPueEmb, b2.tx_desLarCiuPueEmb";
                    /*Nuevo-fin*/
                    strSQL+="            ) AS a";
                    strSQL+="            GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc, a.fe_preImp, a.tx_numDocCon, a.tx_numReq, a.fe_pro, a.co_ciu, a.tx_desCorCiuPueEmb, a.tx_desLarCiuPueEmb";
                    strSQL+="       ) AS y4 ON y.co_emp=y4.co_emp AND y.co_loc=y4.co_loc AND y.co_tipDoc=y4.co_tipDoc AND y.co_doc=y4.co_doc ";                              
                    strSQL+="       WHERE y1.st_Reg IN ('A') ";  
                    strSQL+="       " + strAuxFil;
                    
                    /* Pedidos Llegados */
                    if( (chkMosPedLleDir.isSelected()) && (!chkMosPedLleInd.isSelected()) ) { /*Mostrar sólo pedidos directos.*/
                        strSQL+=" AND y.co_exp NOT IN ("+strCodExpHavelock+")";    
                    }
                    else if( (chkMosPedLleInd.isSelected()) && (!chkMosPedLleDir.isSelected()) ) { /*Mostrar sólo pedidos indirectos.*/
                        strSQL+=" AND y.co_exp IN ("+strCodExpHavelock+")"; 
                    }
                    
                    strSQL+="       GROUP BY y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.fe_doc, y4.fe_preImp, y.ne_numDoc, y.tx_numPed";
                    strSQL+="              , y.co_imp, y.tx_nomEmpImp, y.co_exp, y.tx_nomExp, y1.ne_tipNotPed";
                    strSQL+=" 	           , y2.tx_desCor, y2.tx_desLar, y1.st_reg, y1.fe_colPed";
                    strSQL+=" 	           , y3.fe_Emb, y3.fe_pue, y4.tx_numDocCon, b1.nd_valCarPag, y1.tx_modPed, y4.tx_numReq, y4.tx_refPed, y4.fe_pro/*, y3.fe_arr*/";
                    strSQL+=" 	           , y4.co_ciu, y4.tx_desCorCiuPueEmb, y4.tx_desLarCiuPueEmb";
                    strSQL+=" ) AS z\n";
                }
                //</editor-fold>                
                
                strSQL+=" ) AS a1";
                if(chkOrder.isSelected())
                    strSQL+=" ORDER BY a1.fe_arr desc ";
                else
                    strSQL+=" ORDER BY a1.fe_emb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_preImp, a1.ne_numDoc, a1.ne_tipNotPed, a1.tx_numPed";
                
                System.out.println("cargarDetReg: "+strSQL); 
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){     
                        intOrdFil++;
                        //Cargar los datos en la Tabla Datos
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_INS_PED,         rst.getString("tx_insPed"));       //tx_insPed
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));          //co_emp
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));          //co_loc
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));       //co_tipDoc
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCorTipDoc")); //tx_desCorTipDoc
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLarTipDoc")); //tx_desLarTipDoc
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));          //co_doc
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));       //ne_numDoc
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numPed"));       //tx_numPed
                        vecReg.add(INT_TBL_DAT_NUM_CON,         rst.getString("tx_numPedCon"));       //tx_numPed
                        vecReg.add(INT_TBL_DAT_COD_IMP,         rst.getString("co_imp"));          //co_imp
                        vecReg.add(INT_TBL_DAT_NOM_IMP,         rst.getString("tx_nomEmpImp"));       //tx_nomEmpImp
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));          //co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));       //tx_nomExp
                        vecReg.add(INT_TBL_DAT_REF_PED,         rst.getString("tx_refPed"));    //tx_refPed
                        vecReg.add(INT_TBL_DAT_TIP_NOT_PED,     rst.getString("tx_tipNotPed"));    //tx_tipNotPed
                        vecReg.add(INT_TBL_DAT_VAL_FAC,         rst.getString("nd_valCarPag"));       //nd_valCarPag - En las notas de Pedido, este valor se va a recalcular
                        vecReg.add(INT_TBL_DAT_TXT_FOR_PAG,     rst.getString("tx_desForPag"));       //tx_desForPag
                        vecReg.add(INT_TBL_DAT_NUM_REQ,         rst.getString("tx_numReq"));       //tx_numReq
                        vecReg.add(INT_TBL_DAT_MOD_PED,         rst.getString("tx_modPed"));       //tx_modPed
                        vecReg.add(INT_TBL_DAT_PTO_EMB,         rst.getString("tx_desLarCiuPueEmb"));       //tx_desLarCiuPueEmb
                        vecReg.add(INT_TBL_DAT_FEC_PRE_IMP,     (rst.getString("fe_preImp")==null?"":rst.getString("fe_preImp").equals("")?"":rst.getString("fe_preImp")));       //fe_preImp
                        vecReg.add(INT_TBL_DAT_FEC_COL_PED,     rst.getString("fe_colPed"));       //fe_colPed
                        vecReg.add(INT_TBL_DAT_FEC_PRO_PED,     rst.getString("fe_pro"));       //fe_pro
                        vecReg.add(INT_TBL_DAT_FEC_EMB_EST,     (rst.getString("fe_embEst")==null?"":rst.getString("fe_embEst").equals("")?"":rst.getString("fe_embEst"))); //fe_embEst: Fecha Embarque Estimada.
                        vecReg.add(INT_TBL_DAT_FEC_EMB_REA,     (rst.getString("fe_embRea").equals("")?"":rst.getString("fe_embRea"))); //fe_embRea: Fecha Embarque Real.
                        vecReg.add(INT_TBL_DAT_FEC_PUE,         rst.getString("fe_pue"));          //fe_pue
                        vecReg.add(INT_TBL_DAT_FEC_ARR_BOD,     rst.getString("fe_arr"));          //fe_arr
                        vecReg.add(INT_TBL_DAT_VAL_TOT_FAC,     null); 
                        vecReg.add(INT_TBL_DAT_VAL_TOT_ABO,     null);
                        vecReg.add(INT_TBL_DAT_VAL_TOT_PEN,     null);
                        
                        vecReg.add(INT_TBL_DAT_ANT_POR,         rst.getObject("nd_porCrePagAnt"));
                        vecReg.add(INT_TBL_DAT_ANT_VAL,         rst.getObject("nd_valPagAnt"));
                        vecReg.add(INT_TBL_DAT_ANT_COD_USR,     rst.getObject("co_usrIngAnt"));
                        vecReg.add(INT_TBL_DAT_ANT_FEC_ING,     rst.getObject("fe_ingAnt"));
                                                
                        vecReg.add(INT_TBL_DAT_AUT_CHK,         null);
                        vecReg.add(INT_TBL_DAT_AUT_CHK_AUX,     null);
                        vecReg.add(INT_TBL_DAT_AUT_COD_USR,     null);
                        vecReg.add(INT_TBL_DAT_AUT_FEC_ING,     null);
                        
                        vecReg.add(INT_TBL_DAT_BUT,             null);
                        vecReg.add(INT_TBL_DAT_EST_PAG_ARA,     rst.getString("st_ciePagAra"));   //st_ciePagAra  
                        vecReg.add(INT_TBL_DAT_EST_COL_FIL,     null);
                        vecReg.add(INT_TBL_DAT_ORD_FIL,         ""+intOrdFil);
                        vecReg.add(INT_TBL_DAT_COL_VAL_PAG,     "");
                        
                        //Columnas dinámicas de Fechas de vencimiento Estimadas
                        for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j++){
                            vecReg.add(j,     "");
                        }       
                        //Columnas dinámica de Estado de registro
                        for(int j=intNumColIniEstReg; j<intNumColFinEstReg;j++){
                            vecReg.add(j,     rst.getString("st_reg"));
                        }

                        //Columnas dinámicas de Cargos a Pagar
                        for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                            vecReg.add(j,     "0");
                        }
                        //Columnas dinámicas de Totales de Cargos a Pagar
                        for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
                            vecReg.add(j,     "0");
                        }
                        
                        //Se establece el color de la fila.
                        strAux= rst.getString("st_color")==null?"N":rst.getString("st_color");
                        if (! strAux.equals("") )  {
                            vecReg.setElementAt(strAux, INT_TBL_DAT_EST_COL_FIL);
                        }
                        
                        strAux=rst.getObject("st_autAnt")==null?"":rst.getString("st_autAnt");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_AUT_CHK);
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_AUT_CHK_AUX);
                        }
                        
                        strAux=rst.getObject("co_usrIngAutAnt")==null?"":rst.getString("co_usrIngAutAnt");
                        vecReg.setElementAt(strAux,INT_TBL_DAT_AUT_COD_USR);
                        strAux=rst.getObject("fe_ingAutAnt")==null?"":rst.getString("fe_ingAutAnt");
                        vecReg.setElementAt(strAux,INT_TBL_DAT_AUT_FEC_ING);
              
                        vecDat.add(vecReg);    
                        
                        //Cargar los datos en la Tabla Fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_FIX_INS_PED,         rst.getString("tx_insPed"));       //tx_insPed
                        vecRegFix.add(INT_TBL_FIX_COD_EMP,         rst.getString("co_emp"));          //co_emp
                        vecRegFix.add(INT_TBL_FIX_COD_LOC,         rst.getString("co_loc"));          //co_loc
                        vecRegFix.add(INT_TBL_FIX_COD_TIP_DOC,     rst.getString("co_tipDoc"));       //co_tipDoc
                        vecRegFix.add(INT_TBL_FIX_DES_COR_TIP_DOC, rst.getString("tx_desCorTipDoc")); //tx_desCorTipDoc
                        vecRegFix.add(INT_TBL_FIX_DES_LAR_TIP_DOC, rst.getString("tx_desLarTipDoc")); //tx_desLarTipDoc
                        vecRegFix.add(INT_TBL_FIX_COD_DOC,         rst.getString("co_doc"));          //co_doc
                        vecRegFix.add(INT_TBL_FIX_NUM_DOC,         rst.getString("ne_numDoc"));       //ne_numDoc
                        vecRegFix.add(INT_TBL_FIX_NUM_PED,         rst.getString("tx_numPed"));       //tx_numPed                        
                        vecRegFix.add(INT_TBL_FIX_NUM_CON,         rst.getString("tx_numPedCon"));    //tx_numPedCon
                        vecRegFix.add(INT_TBL_FIX_COD_IMP,         rst.getString("co_imp"));          //co_imp
                        vecRegFix.add(INT_TBL_FIX_NOM_IMP,         rst.getString("tx_nomEmpImp"));    //tx_nomEmpImp
                        vecRegFix.add(INT_TBL_FIX_COD_EXP,         rst.getString("co_exp"));          //co_exp
                        vecRegFix.add(INT_TBL_FIX_NOM_EXP,         rst.getString("tx_nomExp"));       //tx_nomExp
                        vecRegFix.add(INT_TBL_FIX_REF_PED,         rst.getString("tx_refPed"));    //tx_refPed
                        vecRegFix.add(INT_TBL_FIX_TIP_NOT_PED,     rst.getString("tx_tipNotPed"));    //tx_tipNotPed
                        vecRegFix.add(INT_TBL_FIX_VAL_FAC,         rst.getString("nd_valCarPag"));    //nd_valCarPag
                        vecRegFix.add(INT_TBL_FIX_TXT_FOR_PAG,     rst.getString("tx_desForPag"));    //tx_desForPag
                        vecRegFix.add(INT_TBL_FIX_EST_COL_FIL,     null);
                        vecRegFix.add(INT_TBL_FIX_ORD_FIL,         ""+intOrdFil);                     //tx_numPed

                        //Se establece el color de la fila.
                        strAux= rst.getString("st_color")==null?"N":rst.getString("st_color");
                        if (! strAux.equals("") )  {
                            vecRegFix.setElementAt(strAux, INT_TBL_FIX_EST_COL_FIL);
                        }                            
                        vecDatFix.add(vecRegFix);

                    }
                    else
                    {
                        break;
                    }
                }
                rst.close(); 
                stm.close();
                rst=null;
                stm=null;
                        
                //Asignar vectores al modelo.
                //Tabla de Datos
                objTblModDat.setData(vecDat);
                tblDat.setModel(objTblModDat);
                vecDat.clear();
                objTblModDat.initRowsState();        
                
                //Tabla Fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
            }
        }
        catch (java.sql.SQLException e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Función que permite cargar los valores abonados y pendientes de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValFacAboPen(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        BigDecimal bgdValAux=BigDecimal.ZERO;
        int intCodTipDocTraBan=0;
        Connection conTmp=null;
        try{
            if(con==null){
                conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTmp!=null)
                    con=conTmp;
            }
            
            if(con!=null){
                if(chkMosPagExt.isSelected()){
                
                    intCodTipDocTraBan=objZafImp.INT_COD_TIP_DOC_TRA_BAN_EXT;
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                        
                        if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                            stm=con.createStatement();
                            String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                            String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 

                            strSQL="";
                            strSQL+=" SELECT x.*, (x.nd_ValFac - x.nd_valAbo) AS nd_ValPen";
                            strSQL+=" FROM (";

                            strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cta, b1.tx_numDoc2";
                            strSQL+=" , SUM(b1.nd_ValAbo) AS nd_ValAbo, b1.nd_ValFac, b1.co_cta";
                            strSQL+=" FROM(";                    

                            strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, co_CtaPas AS co_cta, a2.tx_numDoc2";
                            strSQL+="             , (CASE WHEN a3.nd_valAbo IS NULL THEN 0 ELSE a3.nd_valAbo END) AS nd_ValAbo";    
                            if(strInsPed.equals("NOTPED")){
                                strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE (a1.nd_valCarPag - ( CASE WHEN a4.nd_ValPedEmb IS NULL THEN 0 ELSE a4.nd_ValPedEmb END ) ) END ) AS nd_ValFac"; //Se presenta valor parcial
                            }
                            else{
                                strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END ) AS nd_ValFac";
                            }
                            strSQL+="        FROM tbm_carPagImp AS a";  
                            strSQL+="        INNER JOIN tbm_carPag"+strNomTbl+" AS a1 ON a.co_CarPag=a1.co_CarPag AND a.tx_tipCarPag IN ('V')"; 
                            strSQL+="        INNER JOIN tbm_cab"+strNomTbl+" AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc"; 
                            if(strInsPed.equals("INIMPO")){
                                strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a4 ON a2.co_empRelPedEmbImp=a4.co_emp AND a2.co_locRelPedEmbImp=a4.co_loc AND a2.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a2.co_docRelPedEmbImp=a4.co_doc"; 
                            }

                            if(strInsPed.equals("NOTPED")){
                                strSQL+="    LEFT OUTER JOIN("; 
                                strSQL+="        SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed, SUM(x.nd_ValCarPag) AS nd_ValPedEmb";
                                strSQL+="        FROM (";
                                strSQL+="            SELECT a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag";
                                strSQL+="                , (CASE WHEN a4.tx_numDoc3 IS NOT NULL THEN a4.tx_numDoc3 ELSE a4.tx_numDoc2 END) AS tx_numPed";
                                strSQL+="            FROM tbm_CarPagImp AS a";
                                strSQL+="            INNER JOIN tbm_carPagPedEmbImp AS a1 ON a.co_carPag=a1.co_carPag";
                                strSQL+="            INNER JOIN tbm_cabPedEmbImp AS a5 ON a1.co_Emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_TipDOc=a5.co_tipDoc AND a1.co_Doc=a5.co_doc";
                                strSQL+="            INNER JOIN tbm_detPedEmbImp AS a2 ON a5.co_Emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_TipDOc=a2.co_tipDoc AND a5.co_Doc=a2.co_doc";
                                strSQL+="            INNER JOIN tbm_detNotPedImp AS a3 ON a2.co_Emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_TipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                                strSQL+="            INNER JOIN tbm_cabNotPedImp AS a4 ON a3.co_Emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_TipDOc=a4.co_tipDoc AND a3.co_Doc=a4.co_doc";
                                strSQL+="            WHERE a.tx_tipCarPag IN ('V') AND a4.st_cie IN ('N') ";
                                strSQL+="            AND a4.st_reg IN ('A') AND (a4.st_doc IN ('A') OR a4.st_doc IS NULL)";
                                strSQL+="            AND a5.st_reg IN ('A') AND (a5.st_doc IN ('A') OR a5.st_doc IS NULL)";
                                strSQL+="            GROUP BY a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag, a4.tx_numDoc2, a4.tx_numDoc3";
                                strSQL+="        ) AS x";
                                strSQL+="        WHERE NOT ( (x.tx_numPed LIKE '%PS%') OR (x.tx_numPed LIKE '%PL%') ) /* No mostrar compras locales. */";
                                strSQL+="        GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed";
                                strSQL+="    ) AS a4";    
                                strSQL+="    ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc"; 
                            }
                            strSQL+="        LEFT OUTER JOIN( "; 
                            strSQL+="             SELECT x.co_cta, x.co_emp, SUM(CASE WHEN x.nd_monDeb IS NULL THEN 0 ELSE x.nd_monDeb END ) AS nd_valAbo";  
                            strSQL+="             FROM tbm_detDia AS x"; 
                            strSQL+=" 	          INNER JOIN tbm_cabDia AS x1 ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_dia=x1.co_dia"; 
                            strSQL+=" 	          AND x1.st_reg IN ('A') AND x1.co_tipDoc="+intCodTipDocTraBan;  //TRABAEX
                            strSQL+="             GROUP BY x.co_cta, x.co_emp";
                            strSQL+="        ) AS a3";
                            if(strInsPed.equals("INIMPO"))
                                strSQL+="   ON a2.co_emp=a3.co_emp AND a4.co_CtaPas=a3.co_Cta";
                            else /* NotPed.; Ped.Emb.*/
                                strSQL+="   ON a2.co_imp=a3.co_emp AND a2.co_CtaPas=a3.co_Cta";
                            strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                            strSQL+="   AND a1.co_loc=" + strCodLoc;
                            strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                            strSQL+="   AND a1.co_doc=" + strCodDoc;
                            strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_cta, a2.tx_numDoc2, a3.nd_valAbo, co_CtaPas";

                            strSQL+=" UNION";

                            strSQL+="        SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, co_CtaPas AS co_cta, a2.tx_numDoc2";
                            strSQL+="             , (CASE WHEN a3.nd_valAbo IS NULL THEN 0 ELSE a3.nd_valAbo END) AS nd_ValAbo";    
                            if(strInsPed.equals("NOTPED")){
                                strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE (a1.nd_valCarPag - ( CASE WHEN a4.nd_ValPedEmb IS NULL THEN 0 ELSE a4.nd_ValPedEmb END ) ) END ) AS nd_ValFac"; //Se presenta valor parcial
                            }
                            else{
                                strSQL+="         , SUM(CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END ) AS nd_ValFac";
                            }
                            strSQL+="        FROM tbm_carPagImp AS a";  
                            strSQL+="        INNER JOIN tbm_carPag"+strNomTbl+" AS a1 ON a.co_CarPag=a1.co_CarPag AND a.tx_tipCarPag IN ('V')"; 
                            strSQL+="        INNER JOIN tbm_cab"+strNomTbl+" AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc"; 
                            if(strInsPed.equals("INIMPO")){
                                strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a4 ON a2.co_empRelPedEmbImp=a4.co_emp AND a2.co_locRelPedEmbImp=a4.co_loc AND a2.co_tipDocRelPedEmbImp=a4.co_tipDoc AND a2.co_docRelPedEmbImp=a4.co_doc"; 
                            }

                            if(strInsPed.equals("NOTPED")){
                                strSQL+="    LEFT OUTER JOIN("; 
                                strSQL+="        SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed, SUM(x.nd_ValCarPag) AS nd_ValPedEmb";
                                strSQL+="        FROM (";
                                strSQL+="            SELECT a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag";
                                strSQL+="                , (CASE WHEN a4.tx_numDoc3 IS NOT NULL THEN a4.tx_numDoc3 ELSE a4.tx_numDoc2 END) AS tx_numPed";
                                strSQL+="            FROM tbm_CarPagImp AS a";
                                strSQL+="            INNER JOIN tbm_carPagPedEmbImp AS a1 ON a.co_carPag=a1.co_carPag";
                                strSQL+="            INNER JOIN tbm_cabPedEmbImp AS a5 ON a1.co_Emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_TipDOc=a5.co_tipDoc AND a1.co_Doc=a5.co_doc";
                                strSQL+="            INNER JOIN tbm_detPedEmbImp AS a2 ON a5.co_Emp=a2.co_emp AND a5.co_loc=a2.co_loc AND a5.co_TipDOc=a2.co_tipDoc AND a5.co_Doc=a2.co_doc";
                                strSQL+="            INNER JOIN tbm_detNotPedImp AS a3 ON a2.co_Emp=a3.co_emp AND a2.co_locRel=a3.co_loc AND a2.co_TipDocRel=a3.co_tipDoc AND a2.co_docRel=a3.co_doc";
                                strSQL+="            INNER JOIN tbm_cabNotPedImp AS a4 ON a3.co_Emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_TipDOc=a4.co_tipDoc AND a3.co_Doc=a4.co_doc";
                                strSQL+="            WHERE a.tx_tipCarPag IN ('V') AND a4.st_cie IN ('N') ";
                                strSQL+="            AND a4.st_reg IN ('A') AND (a4.st_doc IN ('A') OR a4.st_doc IS NULL)";
                                strSQL+="            AND a5.st_reg IN ('A') AND (a5.st_doc IN ('A') OR a5.st_doc IS NULL)";
                                strSQL+="            GROUP BY a.tx_tipCarPag, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a1.nd_ValCarPag, a4.tx_numDoc2, a4.tx_numDoc3";
                                strSQL+="        ) AS x";
                                strSQL+="        WHERE NOT ( (x.tx_numPed LIKE '%PS%') OR (x.tx_numPed LIKE '%PL%') ) /* No mostrar compras locales. */";
                                strSQL+="        GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x.tx_numPed";
                                strSQL+="    ) AS a4";    
                                strSQL+="    ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc"; 
                            }
                            strSQL+="        LEFT OUTER JOIN( "; 
                            strSQL+="             SELECT x.co_cta, x.co_emp, SUM(CASE WHEN x.nd_monDeb IS NULL THEN 0 ELSE x.nd_monDeb END ) AS nd_valAbo";  
                            strSQL+="             FROM tbm_detDia AS x"; 
                            strSQL+=" 	          INNER JOIN tbm_cabDia AS x1 ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_dia=x1.co_dia"; 
                            strSQL+=" 	          AND x1.st_reg IN ('A') AND x1.co_tipDoc="+intCodTipDocTraBan;  //TRABAEX
                            strSQL+="             GROUP BY x.co_cta, x.co_emp";
                            strSQL+="        ) AS a3";
                            if(strInsPed.equals("INIMPO"))
                                strSQL+="   ON a2.co_emp=a3.co_emp AND a4.co_CtaAct=a3.co_Cta";
                            else /* NotPed.; Ped.Emb.*/
                                strSQL+="   ON a2.co_imp=a3.co_emp AND a2.co_CtaAct=a3.co_Cta";
                            strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                            strSQL+="   AND a1.co_loc=" + strCodLoc;
                            strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                            strSQL+="   AND a1.co_doc=" + strCodDoc;
                            strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_cta, a2.tx_numDoc2, a3.nd_valAbo, co_CtaPas";
                            strSQL+="	) AS b1";
                            strSQL+="	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cta, b1.tx_numDoc2";
                            strSQL+="	, b1.nd_ValFac";
                            strSQL+=" ) AS x";

                            System.out.println("cargarValFacAboPen: "+strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                //<editor-fold defaultstate="collapsed" desc="/* LSC: Presenta el valor de la factura (Pago a proveedores del exterior). Actualizar en la columna Tot.Fob.Cfr, con el valor del cargo a pagar "Valor Factura".*/">
                                bgdValAux=new BigDecimal(rst.getObject("nd_valFac")==null?"0":(rst.getString("nd_valFac").equals("")?"0":rst.getString("nd_valFac")));
                                objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_FAC);
                                objTblModFix.setValueAt(bgdValAux, i, INT_TBL_FIX_VAL_FAC);//nueva columna del valor de la factura, tabla fija
                                objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_FAC);//nueva columna del valor de la factura, tabla dinamica
                                //</editor-fold>

                                bgdValAux=new BigDecimal(rst.getObject("nd_valAbo")==null?"0":(rst.getString("nd_valAbo").equals("")?"0":rst.getString("nd_valAbo")));
                                objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_ABO);

                                bgdValAux=new BigDecimal(rst.getObject("nd_valPen")==null?"0":(rst.getString("nd_valPen").equals("")?"0":rst.getString("nd_valPen")));
                                objTblModDat.setValueAt(bgdValAux, i, INT_TBL_DAT_VAL_TOT_PEN);
                            }
                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;
                        }
                        else{
                            objTblModDat.setValueAt("0", i, INT_TBL_DAT_VAL_TOT_ABO);
                            objTblModDat.setValueAt("0", i, INT_TBL_DAT_VAL_TOT_PEN);
                        }


                    }
                }
                
                if(conTmp!=null){
                    conTmp.close();
                    conTmp=null;
                    con=conTmp;
                }
                

            }
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
    
    /**
     * Función que permite cargar los valores de Cargos a Pagar de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValCarPagPed(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intColCarPag=-1;
        BigDecimal bgdValCarPag=BigDecimal.ZERO;
        Connection conTmp=null;
        try{
            if(con==null){
                conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTmp!=null)
                    con=conTmp;
            }
            
            
            if(con!=null){
                if(chkMosCarPag.isSelected()) {
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 
                        
                        if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                            stm=con.createStatement();
                            
                            for(int j=0; j<arlDatCarPag.size(); j++){
                                intColCarPag=objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COL_CAR_PAG);
                                strSQL ="";
                                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carPag, a2.tx_tipCarPag";
                                strSQL+="     , (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) AS nd_valCarPag";
                                strSQL+=" FROM tbm_carPag"+strNomTbl+" AS a1 INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag";
                                strSQL+=" WHERE a1.co_emp=" + strCodEmp + "";
                                strSQL+=" AND a1.co_loc=" + strCodLoc + "";
                                strSQL+=" AND a1.co_tipDoc=" + strCodTipDoc + "";
                                strSQL+=" AND a1.co_doc=" + strCodDoc + "";
                                strSQL+=" AND a1.co_carPag=" + objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COD_CAR_PAG) + "";
                                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carPag, a2.tx_tipCarPag, a1.nd_valCarPag";
                                rst=stm.executeQuery(strSQL);
                                if(rst.next()){
                                    bgdValCarPag=new BigDecimal(rst.getObject("nd_valCarPag")==null?"0":(rst.getString("nd_valCarPag").equals("")?"0":rst.getString("nd_valCarPag")));
                                    objTblModDat.setValueAt(bgdValCarPag, i, intColCarPag);
                                    //<editor-fold defaultstate="collapsed" desc="/* LSC: Presenta el valor de la factura (Pago a proveedores del exterior). Actualizar en la columna Tot.Fob.Cfr, con el valor del cargo a pagar "Valor Factura".*/">
                                    // if(rst.getString("tx_tipCarPag").equals("V")){
                                    //     objTblModDat.setValueAt(bgdValCarPag, i, INT_TBL_DAT_VAL_TOT_FAC);
                                    // }
                                    //</editor-fold>
                                }
                                rst.close();
                                rst=null;
                            }
                            stm.close();
                            stm=null;

                        }
                        else
                            objTblModDat.setValueAt("", i, intColCarPag);
                        
                        
                        


                        



                    }
                }
                
                if(conTmp!=null){
                    conTmp.close();
                    conTmp=null;
                    con=conTmp;
                }
                
            }
            
            if(chkMosCarPag.isSelected()){
                for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                    objTblModDat.removeSystemHiddenColumn(j, tblDat);
                    objTblModTot.removeSystemHiddenColumn(j, tblTotDat);
                    //cargarValCarPagPed();
                }
            }
            else{
                for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                    objTblModDat.addSystemHiddenColumn(j, tblDat);
                    objTblModTot.addSystemHiddenColumn(j, tblTotDat);
                }
            }
            
            
            
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
    
    /**
     * Función que permite cargar los totales de los Cargos a Pagar de los Pedidos seleccionados
     * <BR> Segmentando por tipo de cargo, tales como: Gastos, Aranceles, etc.
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarValTotCarPagPed(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        String strRstTotTipCarPag="";
        String strArlTotTipCarPag="";
        int intNumColTotCarPag=-1;
        BigDecimal bgdTotValCarPag=BigDecimal.ZERO;
        Connection conTmp=null;
        try{
            if(con==null){
                conTmp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTmp!=null)
                    con=conTmp;
            }
            
            if(con!=null){
                if(chkMosTotCarPag.isSelected()) {
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());

                        String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                        String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 
                        
                        if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                            stm=con.createStatement();
                            strSQL ="";
                            strSQL+=" SELECT * FROM (";
                            strSQL+="   SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_tipCarPag";
                            strSQL+="        , SUM (CASE WHEN a1.nd_valCarPag IS NULL THEN 0 ELSE a1.nd_valCarPag END) AS nd_valTotCarPag";
                            strSQL+="   FROM tbm_carPag"+strNomTbl+" AS a1 ";
                            strSQL+="   INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag ";
                            strSQL+="   WHERE a1.co_emp=" + strCodEmp + "";
                            strSQL+="   AND a1.co_loc=" + strCodLoc + "";
                            strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc + "";
                            strSQL+="   AND a1.co_doc=" + strCodDoc + "";
                            strSQL+="   AND a2.tx_tipCarPag IN("+strTipCarPag+")";
                            strSQL+="   GROUP BY  a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_tipCarPag";
                            strSQL+=" ) AS x";
                            strSQL+=" ORDER BY x.tx_tipCarPag";
                            rst=stm.executeQuery(strSQL);
                            while(rst.next()){
                                strRstTotTipCarPag=rst.getString("tx_tipCarPag");   
                                /* Calcular los valores de las columnas de Totales. */
                                for(int k=0; k<arlDatTotCarPag.size(); k++){
                                    strArlTotTipCarPag=objUti.getStringValueAt(arlDatTotCarPag, k, INT_ARL_TOT_CAR_PAG_TIP);
                                    intNumColTotCarPag=objUti.getIntValueAt(arlDatTotCarPag, k, INT_ARL_TOT_CAR_PAG_COL);
                                    if(strRstTotTipCarPag.equals(strArlTotTipCarPag)){
                                        bgdTotValCarPag=new BigDecimal(rst.getObject("nd_valTotCarPag")==null?"0":(rst.getString("nd_valTotCarPag").equals("")?"0":rst.getString("nd_valTotCarPag")));                                
                                        objTblModDat.setValueAt(bgdTotValCarPag, i, intNumColTotCarPag);
                                        break;
                                    }
                                }
                            }
                            rst.close();
                            rst=null;
                            stm.close();
                            stm=null;
                        }
                        else
                            objTblModDat.setValueAt("0", i, intNumColTotCarPag);
                    }
                }
                
                if(conTmp!=null){
                    conTmp.close();
                    conTmp=null;
                    con=conTmp;
                }
            }
            
            if(chkMosTotCarPag.isSelected()){
                for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
                    objTblModDat.removeSystemHiddenColumn(j, tblDat);
                    objTblModTot.removeSystemHiddenColumn(j, tblTotDat);
                }
            }
            else{
                for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
                    objTblModDat.addSystemHiddenColumn(j, tblDat);
                    objTblModTot.addSystemHiddenColumn(j, tblTotDat);
                }
            }
            
            
            
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
    
    /**
     * Función que permite cargar los valores y fechas de vencimiento de pagos de los Pedidos seleccionados
     * @return true Si se realizó la operación
     * <BR> false Caso contrario
     */
    private boolean cargarFecVenPagEst(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intAux=0;
        try{
            if(con!=null){
                if(chkMosFecVenRea.isSelected()){ 
                    for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                        strCodEmp=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                        strCodLoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                        strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                        strCodDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                        
                        if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                            stm=con.createStatement();
                            
                            String strInsPed=objUti.parseString(objTblModDat.getValueAt(i,INT_TBL_DAT_INS_PED)); 
                            String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":(strInsPed.equals("INIMPO")?"MovInv":""))); 
                            //String strNomTbl=(strInsPed.equals("NOTPED")?"NotPedImp": (strInsPed.equals("PEDEMB")?"PedEmbImp":"")); 

    //                        if(!strInsPed.equals("INIMPO")){
                                strSQL ="";
                                strSQL+="   SELECT a1.ne_numDiaCre1, a1.ne_numDiaCre2, a1.ne_numDiaCre3, a1.ne_numDiaCre4";     
                                strSQL+="        , a1.nd_porCrePag1, a1.nd_porCrePag2, a1.nd_porCrePag3, a1.nd_porCrePag4";     
                                strSQL+="        , a1.fe_venRea1,  a1.fe_venRea2, a1.fe_venRea3, a1.fe_venRea4" ;  
                                strSQL+="        , a1.nd_valPagVen1,  a1.nd_valPagVen2, a1.nd_valPagVen3, a1.nd_valPagVen4" ;  
                                strSQL+="   FROM tbm_cab"+strNomTbl+" AS a1"; 
                                strSQL+="   WHERE a1.co_emp=" + strCodEmp;
                                strSQL+="   AND a1.co_loc=" + strCodLoc;
                                strSQL+="   AND a1.co_tipDoc=" + strCodTipDoc;
                                strSQL+="   AND a1.co_doc=" + strCodDoc;
                                rst=stm.executeQuery(strSQL);
                                if(rst.next()){
                                    intAux=0;
                                    //Actualizar datos de Fechas Vencimiento: Estimadas
                                    for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j=(j+intNumColFecVenEst)) {
                                        objTblModDat.setValueAt(rst.getString("ne_numDiaCre"+(intAux+1)+""), i, (j+0)); 
                                        objTblModDat.setValueAt(rst.getString("fe_venRea"+(intAux+1)+""), i, (j+1));          //calcularFecPagFecVenEstSel(i, (j+0), intCalEditBefore);
                                        objTblModDat.setValueAt(rst.getString("nd_porCrePag"+(intAux+1)+""), i, (j+2));
                                        objTblModDat.setValueAt(rst.getString("nd_valPagVen"+(intAux+1)+""), i, (j+3));    //calcularValPagFecVenEstSel(i, (j+2), intCalEditBefore);
                                        intAux++;
                                    }                                
                                } 
                                rst.close();
                                rst=null;
                                stm.close();
                                stm=null;
                            
                        }
                        else{
                            for(int j=intNumColIniFecVenEst; j<intNumColFinFecVenEst;j=(j+intNumColFecVenEst)) {
                                objTblModDat.setValueAt("", i, (j+0)); 
                                objTblModDat.setValueAt("", i, (j+1));          //calcularFecPagFecVenEstSel(i, (j+0), intCalEditBefore);
                                objTblModDat.setValueAt("", i, (j+2));
                                objTblModDat.setValueAt("", i, (j+3));    //calcularValPagFecVenEstSel(i, (j+2), intCalEditBefore);
                            }
                        }
                            
                        
                         


                    }
                }
            }
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

    private boolean setColumnasEditables(){
        Boolean blnRes=true;
        try{
            //Establecer columnas editables
            vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_FEC_COL_PED);
            vecAux.add("" + INT_TBL_DAT_FEC_EMB_EST);
            vecAux.add("" + INT_TBL_DAT_FEC_PRO_PED);
            vecAux.add("" + INT_TBL_DAT_FEC_PUE);
            vecAux.add("" + INT_TBL_DAT_FEC_ARR_BOD);
            vecAux.add("" + INT_TBL_DAT_MOD_PED);
            vecAux.add("" + INT_TBL_DAT_BUT);
            
            vecAux.add("" + INT_TBL_DAT_ANT_POR);
            vecAux.add("" + INT_TBL_DAT_ANT_VAL);
            vecAux.add("" + INT_TBL_DAT_AUT_CHK);
            
            /* Fechas de vencimiento: Estimadas */
            for(int i=0; i<(intNumGrpAddFecVenEst); i++) {
                for(int j=0; j<(intNumColFecVenEst); j++) {
                    int intIndColGrp =(intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
//                    if(j%2==0) { //Permitir edición de Columnas: Días y % Pago
                        vecAux.add(""+ intIndColGrp);
//                    }
                }
            }
            /* Fechas de vencimiento: Reales */
            for(int i=0; i<(intNumGrpAddFecVenEst); i++) {
                for(int j=0; j<(intNumColFecVenEst); j++)  {
                    int intIndColGrp =(intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                    vecAux.add(""+ intIndColGrp);
                }
            }                
            objTblModDat.setColumnasEditables(vecAux);
            vecAux=null;                
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    
    }
    
    private boolean delRowValPenCero()
    {
        boolean blnRes=true;
        String strInsPed="";
        BigDecimal bgdValPen=new BigDecimal("0");
        try 
        {
            if(chkMosPagExt.isSelected()){
                //Tabla de datos
                if(chkMosPedLleDir.isSelected() || (chkMosPedLleInd.isSelected()) ) {
                    objTblModDat.setModoOperacion(objTblModDat.INT_TBL_INS);//Tabla de datos
                    objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);//Tabla fija
                    for(int i=(objTblModDat.getRowCountTrue()-1); i>=0; i--)
                    {
                        strInsPed=objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED)==null?"":(objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED).toString().equals("")?"":objTblModDat.getValueAt(i, INT_TBL_DAT_INS_PED).toString());
                        if(strInsPed.equals("INIMPO")) { //Se eliminan las filas de los INIMPO que no tienen valores pendientes.
                            bgdValPen=new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                            if(bgdValPen.compareTo(new BigDecimal("0"))==0)  {
                                objTblModDat.removeRow(i);//Tabla de datos
                                objTblModFix.removeRow(i);//Tabla fija
                            }
                        }
                    }
                    objTblModDat.setModoOperacion(objTblModDat.INT_TBL_EDI);//Tabla de datos
                    objTblModDat.removeEmptyRows();//Tabla de datos
                    objTblModFix.setModoOperacion(objTblModFix.INT_TBL_EDI);//Tabla fija
                    objTblModFix.removeEmptyRows();//Tabla fija
                } 
            }
           
        }
        catch (Exception e) 
        {
            blnRes=false;
        }
        return blnRes;
    }    
    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        return true;
    }
    
    /**
     * Esta función permite actualizar los registros del detalle.
     * Indica que el pedido ha sido arribado.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                //Listado de Pedidos en Tránsito
                if(actualizarDatosPedTra()) {   
                    blnRes=true; 
                    con.commit();
                }
                else
                    con.rollback(); 
                
                con.close();
                con=null; 
            }
            //Inicializo el estado de las filas.
            objTblModDat.initRowsState();
            objTblModDat.setDataModelChanged(false);
        }
        catch (java.sql.SQLException e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
      
    /**
     * Esta función permite actualizar las fechas de la nota de pedido
     * @return true: Si se pudo actualizar las fechas 
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatosPedTra() {
        boolean blnRes=true;
        String strInsPed="", strNomTbl="";
        String strSQLUpdate="", strFecUltMod="";
        String strUpdModPed="", strModPed;
        try{
            if (con!=null)
            {
                stm=con.createStatement(); 
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                //Armar la sentencia SQL.
                strSQLUpdate="";
                strUpdModPed="";
                for (int intFil=0; intFil<objTblModDat.getRowCountTrue(); intFil++){
                    if (objUti.parseString(objTblModDat.getValueAt(intFil,INT_TBL_DAT_LIN)).equals("M")) {
                        /* Obtener instancia del pedido a actualizar */
                        strInsPed=objUti.parseString(objTblModDat.getValueAt(intFil,INT_TBL_DAT_INS_PED));
                        strNomTbl=(strInsPed.equals("NOTPED")?"tbm_cabNotPedImp": (strInsPed.equals("PEDEMB")?"tbm_cabPedEmbImp":(strInsPed.equals("INIMPO")?"tbm_CabMovInv":"")));
                                                
                        strModPed=(objTblModDat.getValueAt(intFil,INT_TBL_DAT_MOD_PED)==null?"":objTblModDat.getValueAt(intFil,INT_TBL_DAT_MOD_PED).toString());
                        
                        strUpdModPed+=" UPDATE " + strNomTbl + "";
                        strUpdModPed+=" SET tx_modPed=" + objUti.codificar(strModPed, 1) + "";
                        strUpdModPed+=" WHERE co_emp=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_EMP) + "";
                        strUpdModPed+=" AND co_loc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_LOC) + "";
                        strUpdModPed+=" AND co_tipDoc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_TIP_DOC) + "";
                        strUpdModPed+=" AND co_doc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_DOC) + "";
                        strUpdModPed+=";";
                        
                        if(strInsPed.equals("NOTPED")){
                            String strAuxFec=objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PRO_PED)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PRO_PED).toString();
                            strUpdModPed+="; UPDATE " + strNomTbl + "";
                            strUpdModPed+=" SET fe_pro=" + objUti.codificar(strAuxFec, 0) + "";
                            /*Se agrega campos de anticipo*/
                            strUpdModPed+=" , nd_valPagAnt=" + objUti.redondearBigDecimal((objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL).toString())), objParSis.getDecimalesMostrar()) + "";
                            strUpdModPed+=" , nd_porCrePagAnt=" + objUti.redondearBigDecimal((objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR).toString())), objParSis.getDecimalesMostrar()) + "";
                            strUpdModPed+=" , co_usrIngAnt=" + objParSis.getCodigoUsuario() + "";
                            strUpdModPed+=" , fe_ingAnt=CURRENT_TIMESTAMP";
                                                                
                            /*Se agrega campos de autorización*/
                            if(objTblModDat.isChecked(intFil, INT_TBL_DAT_AUT_CHK)){
                                strUpdModPed+=" , st_autAnt='S'";
                                strUpdModPed+=" , co_usrIngAutAnt=" + objParSis.getCodigoUsuario() + "";
                                strUpdModPed+=" , fe_ingAutAnt=CURRENT_TIMESTAMP";
                            }
                            else{
                                if(objTblModDat.isChecked(intFil, INT_TBL_DAT_AUT_CHK_AUX)){
                                    strUpdModPed+=" , st_autAnt=Null";
                                    strUpdModPed+=" , co_usrIngAutAnt=Null";
                                    strUpdModPed+=" , fe_ingAutAnt=Null";
                                }
                            }

                            strUpdModPed+=" WHERE co_emp=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_EMP) + "";
                            strUpdModPed+=" AND co_loc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_LOC) + "";
                            strUpdModPed+=" AND co_tipDoc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_TIP_DOC) + "";
                            strUpdModPed+=" AND co_doc=" + objTblModDat.getValueAt(intFil,INT_TBL_DAT_COD_DOC) + "";
                            strUpdModPed+=";";
                        }
                        
                        //Armar sentencia SQL
                        strSQL="";
                        strSQL+=" UPDATE "+strNomTbl+"";
                        strSQL+=" SET fe_ultmod='"+strFecUltMod+"'";
                        strSQL+="   , co_usrmod=" + objParSis.getCodigoUsuario() + "";
                        //strSQL+="   , fe_colPed=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_COL_PED)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_COL_PED).toString(), 0);
                        
                        if(!strInsPed.equals("INIMPO")){
                            String strAuxFec=objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST).toString();
                            if(!strAuxFec.equals("")){ /* Fecha Embarque del pedido: Solo podrá modificarse la fecha de embarque estimada, la real no puede ser editada. */
                               strSQL+="   , fe_emb=" + objUti.codificar(strAuxFec, 0);
                            }
                            strSQL+="   , fe_pue=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE).toString(), 0);
                            strSQL+="   , fe_arr=" + objUti.codificar( objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD).toString(), 0);
                        }
                        //Actualizar datos Grupo: Fechas Vencimiento Estimadas
                        for(int i=0; i<(intNumGrpAddFecVenEst); i++) { //Cantidad de grupos de fechas de vencimiento
                            for(int j=0; j<(intNumColFecVenEst); j++) { //Número de columnas dinámicas por grupo de fecha.        
                                int intIndColGrp = (intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                                //Valores
                                Object objDat =(objTblModDat.getValueAt(intFil,intIndColGrp)==null)?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString().equals("")?null:objTblModDat.getValueAt(intFil, intIndColGrp).toString();   
                                if(j==0) {
                                    strSQL+=" , ne_numDiaCre"+(i+1)+"=" + objDat;
                                }
                                else if(j==1) { //Fec.Ven.Est.
                                    strSQL+=" , fe_venRea"+(i+1)+"="+ objUti.codificar( objTblModDat.getValueAt(intFil, intIndColGrp)==null?"":objTblModDat.getValueAt(intFil, intIndColGrp).toString(), 0);  /* Fecha Vencimiento Estimada */
                                }                             
                                else if(j==2) {
                                    strSQL+=" , nd_porCrePag"+(i+1)+"=" + objDat;
                                }
                                else if(j==3) { //Val.Ven.Est.
                                    strSQL+=" , nd_valPagVen"+(i+1)+"=" + objDat;
                                }                                       
                            }
                        }    
//                        }
                        
                        strSQL+=" WHERE co_emp=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_EMP);
                        strSQL+=" AND co_loc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_DOC);
                        strSQL+=";";
                        strSQLUpdate+=strSQL + strUpdModPed;
                        
                        /* JM: 26/Oct/2022 DynamicsGP */
                        if(strInsPed.equals("PEDEMB")){
                            if(modificarFechas(Integer.valueOf(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_EMP).toString()),
                                               Integer.valueOf(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_LOC).toString()),
                                               Integer.valueOf(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_TIP_DOC).toString()),
                                               Integer.valueOf(objTblModDat.getValueAt(intFil, INT_TBL_DAT_COD_DOC).toString()), 
                                               objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA).toString(),
                                               objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_PUE).toString(),
                                               objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_ARR_BOD).toString()
                                               )){
                                
                            }
                        }
                        /* JM: 26/Oct/2022 DynamicsGP */
                        
                    }
                }
                System.out.println("strSQLUpdate: "+strSQLUpdate);
                stm.executeUpdate(strSQLUpdate);
                stm.close();
                stm=null;   
                
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedImp", "tbh_cabNotPedImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabPedEmbImp", "tbh_cabPedEmbImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                
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
    
    
    private int getNumFecha(String strFecha, int intTipDat){
        int intNumFec=-1;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        String strSqlAux;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSqlAux ="";
                strSqlAux+=" SELECT (CASE WHEN (EXTRACT(YEAR from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(YEAR from CAST('"+strFecha+"'AS DATE))) END)  AS ne_numAnio";
                strSqlAux+="      , (CASE WHEN (EXTRACT(MONTH from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(MONTH from CAST('"+strFecha+"'AS DATE))) END) AS ne_numMes";  
                strSqlAux+="      , (CASE WHEN (EXTRACT(DAY from CAST('"+strFecha+"'AS DATE))) IS NULL THEN -1 ELSE ( EXTRACT(DAY from CAST('"+strFecha+"'AS DATE))) END) AS ne_numDia";
                rstLoc=stmLoc.executeQuery(strSqlAux);
                if(rstLoc.next()){
                    switch (intTipDat){
                        case 1: intNumFec=rstLoc.getInt("ne_numAnio"); break;
                        case 2: intNumFec=rstLoc.getInt("ne_numMes");  break;
                        case 3: intNumFec=rstLoc.getInt("ne_numDia");  break;
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
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumFec;
    }
  

    
    /***
     * Calcula fechas y valores dentro del grupo "Fechas de Vencimiento Proyectadas"
     * Este calculo solo se realiza por fila, en todas las columnas.
     * <BR>Ejemplo: Se utiliza para recalcular fechas, despues de editar la fecha de embarque en el JTable.
     */
    private void calcularGrupoColumnsFecVenEstPorFila(int intFilSel){ 
        try {
            for(int i=0; i<(intNumGrpAddFecVenEst); i++) { //Cantidad de grupos de fechas de vencimiento
                for(int j=0; j<(intNumColFecVenEst); j++) { //Número de columnas dinámicas por grupo de fecha.    
                    int intIndColGrp = (intNumColIniFecVenEst+j+(i*intNumColFecVenEst)); //Obtengo Indice de la Columna. 
                    if(j==0) /* Calcula Fecha Vencimiento*/
                        calcularFecPagFecVenEstSel(intFilSel, intIndColGrp, intCalEditAfter);
                    else if(j==2) /* Calcula Valor Vencimiento*/
                        calcularValPagFecVenEstSel(intFilSel, intIndColGrp, intCalEditAfter);
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }      
    
    /**
     * Función que calcula las fechas de vencimiento Estimadas: Por Fila y Columna Seleccionada
     * @param intFil
     * @param intCol
     * @param intTipEdi 0=Después de editar; 1=Antes de Editar
     */
    private void calcularFecPagFecVenEstSel(int intFil, int intCol, int intTipEdi) { 
        int intNumDiaCre=-999999;
        String strFecEmb="", strFecEmbEst="", strFecEmbRea="";
        LocalDate ldtFecVenEst;        
        try {
            intNumDiaCre=Integer.parseInt(objTblModDat.getValueAt(intFil, intCol)==null?"-999999":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"-999999":objTblModDat.getValueAt(intFil, intCol).toString()));
            
            /* Fecha Embarque del pedido: Solo podrá modificarse la fecha de embarque estimada, la real no puede ser editada. */
            strFecEmb="";
            strFecEmbRea=(objTblModDat.getValueAt(intFil,INT_TBL_DAT_FEC_EMB_REA)==null)?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA).toString();
            objDtePckEdiFecEmb.setText(strFecEmbRea); //Se asigna la fecha de embarque al datePicker
            if(objDtePckEdiFecEmb.isFecha()) { //Si existe fecha de Embarque real, se asigna la fecha de embarque real.
                strFecEmb=objUti.formatearFecha(objDtePckEdiFecEmb.getText(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos());
            }
            else{ //Si no existe fecha de Embarque real, se asigna la fecha de embarque estimada.
                strFecEmbEst=(objTblModDat.getValueAt(intFil,INT_TBL_DAT_FEC_EMB_EST)==null)?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_EST).toString();
                objDtePckEdiFecEmb.setText(strFecEmbEst); //Se asigna la fecha de embarque al datePicker
                if(objDtePckEdiFecEmb.isFecha()) { 
                    strFecEmb=objUti.formatearFecha(objDtePckEdiFecEmb.getText(),"yyyy-MM-dd",objParSis.getFormatoFechaBaseDatos());
                }
            }
            
            if(intNumDiaCre==-999999){
                objTblModDat.setValueAt("", intFil, (intCol+1)); 
            }
            else{
                LocalDate datFecEmb = LocalDate.of(getNumFecha(strFecEmb, 1), getNumFecha(strFecEmb, 2), getNumFecha(strFecEmb, 3)); 
                ldtFecVenEst = datFecEmb.plus(Period.ofDays(intNumDiaCre));
                objTblModDat.setValueAt(""+ldtFecVenEst, intFil, (intCol+1)); 
                if(intTipEdi==intCalEditBefore){
                    if(intNumDiaCre==0){
                        objTblModDat.setValueAt("", intFil, (intCol+1)); 
                    }
                }            
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * Función que calcula los valores de vencimiento Estimadas: Por Fila y Columna Seleccionada
     * @param intFil
     * @param intCol
     * @param intTipEdi 0=Después de editar; 1=Antes de Editar
     */    
    private void calcularValPagFecVenEstSel(int intFil, int intCol, int intTipEdi){ 
        BigDecimal bgdAuxPor = new BigDecimal ("-1");
        BigDecimal bgdPorCre = BigDecimal.ZERO;
        BigDecimal bgdValFac = BigDecimal.ZERO;
        BigDecimal bgdValPag = BigDecimal.ZERO;
        try {
            bgdAuxPor = new BigDecimal ( objTblModDat.getValueAt(intFil, intCol)==null?"-1":(objTblModDat.getValueAt(intFil, intCol).toString().equals("")?"-1":objTblModDat.getValueAt(intFil, intCol).toString()));
            bgdPorCre=objUti.redondearBigDecimal( bgdAuxPor.divide(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar() ); 
            bgdValFac=objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString()),objParSis.getDecimalesBaseDatos());                     
            
            if( bgdAuxPor.compareTo(new BigDecimal("-1"))==0 ){
                objTblModDat.setValueAt("", intFil, (intCol+1)); 
            }
            else{
                bgdValPag = bgdValFac.multiply(bgdPorCre);  
                objTblModDat.setValueAt(""+bgdValPag, intFil, (intCol+1)); 
                if(intTipEdi==intCalEditBefore){ 
                    if(bgdPorCre.compareTo(BigDecimal.ZERO)==0){
                        objTblModDat.setValueAt("", intFil, (intCol+1)); 
                    }
                }            
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }   
                  
    
    private boolean configurarTblDatFixTot()
    {
        boolean blnRes=true;
        try
        {
            //objTblModTotFix=new ZafTblMod();
            objTblModTotFix.setHeader(vecCabFix);
            tblTotFix.setModel(objTblModTotFix);

            //Configurar JTable: Establecer tipo de selección.
            tblTotFix.setRowSelectionAllowed(true);
            tblTotFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_FIX_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_FIX_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_FIX_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_FIX_NUM_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_NOM_IMP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_FIX_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_REF_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_TIP_NOT_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_FIX_VAL_FAC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_TXT_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_EST_COL_FIL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_ORD_FIL).setPreferredWidth(30);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_EMP, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_LOC, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_TIP_DOC, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_DOC, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_IMP, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_COD_EXP, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_EST_COL_FIL, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_ORD_FIL, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_NUM_DOC, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_INS_PED, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_FIX_DES_LAR_TIP_DOC, tblTotFix);
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblFix.getColumnCount(); j++){
                tblTotFix.getColumnModel().getColumn(j).setWidth(tblFix.getColumnModel().getColumn(j).getWidth());
                tblTotFix.getColumnModel().getColumn(j).setMaxWidth(tblFix.getColumnModel().getColumn(j).getMaxWidth());
                tblTotFix.getColumnModel().getColumn(j).setMinWidth(tblFix.getColumnModel().getColumn(j).getMinWidth());
                tblTotFix.getColumnModel().getColumn(j).setPreferredWidth(tblFix.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotFix.getColumnModel().getColumn(j).setResizable(tblFix.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            //tblTotFix.getTableHeader().setReorderingAllowed(false);

            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            //spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnTotFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotFix.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);

            ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix();
            tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);                        
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            jscTblFix_horScr=spnFix.getHorizontalScrollBar();
            jscTblTotFix_horScr=spnTotFix.getHorizontalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            jscTblFix_horScr.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    jscTblTotFix_horScr.setValue(evt.getValue());
                }
            });
            spnTotFix.setColumnHeader(null);

            objTblModTotFix.insertRows(1,0);
            objTblModTotFix.setValueAt("Totales - peso: ", 0, INT_TBL_FIX_NOM_EXP);
            
            objTblCelRenLblFijValFac=new ZafTblCelRenLbl();
            objTblCelRenLblFijValFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblFijValFac.setTipoFormato(objTblCelRenLblFijValFac.INT_FOR_NUM);
            objTblCelRenLblFijValFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FIX_VAL_FAC).setCellRenderer(objTblCelRenLblFijValFac);            
            objTblCelRenLblFijValFac=null;
            
            
            
            intNumColEstFixTot=objTblModTotFix.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private class ZafTblColModLisTotFix implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblFix.getTableHeader().getResizingColumn()!=null){
                intColSel=tblFix.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblFix.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotFix.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }
    
    
    
    private boolean configurarTblDatTot()
    {
        boolean blnRes=true;
        try
        {
            //objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);
            tblTotDat.setModel(objTblModTot);

            //Configurar JTable: Establecer tipo de selección.
            tblTotDat.setRowSelectionAllowed(true);
            tblTotDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_INS_PED).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CON).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_REF_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_DAT_TIP_NOT_PED).setPreferredWidth(53);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TXT_FOR_PAG).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_NUM_REQ).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_MOD_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PTO_EMB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRE_IMP).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_COL_PED).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PRO_PED).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_EST).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_REA).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_PUE).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_BOD).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_POR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_VAL).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_COD_USR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_ANT_FEC_ING).setPreferredWidth(76);
                        
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK_AUX).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USR).setPreferredWidth(76);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC_ING).setPreferredWidth(76);
            
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_EST_PAG_ARA).setPreferredWidth(25);   
            tcmAux.getColumn(INT_TBL_DAT_EST_COL_FIL).setPreferredWidth(25);  
            tcmAux.getColumn(INT_TBL_DAT_ORD_FIL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COL_VAL_PAG).setPreferredWidth(25);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_INS_PED).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_INS_PED).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_INS_PED).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_INS_PED).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_INS_PED).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_IMP).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_IMP).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_IMP).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_IMP).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_IMP).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EXP).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EXP).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EXP).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EXP).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_PAG_ARA).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_PAG_ARA).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_PAG_ARA).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_PAG_ARA).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_PAG_ARA).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_COL_FIL).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_COL_FIL).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_COL_FIL).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_COL_FIL).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_EST_COL_FIL).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ORD_FIL).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ORD_FIL).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ORD_FIL).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ORD_FIL).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ORD_FIL).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COL_VAL_PAG).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COL_VAL_PAG).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COL_VAL_PAG).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COL_VAL_PAG).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COL_VAL_PAG).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_REF_PED).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_REF_PED).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_REF_PED).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_REF_PED).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_REF_PED).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_FAC).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_FAC).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_FAC).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_FAC).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_FAC).setResizable(false);
                                    
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_TXT_FOR_PAG).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_TXT_FOR_PAG).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_TXT_FOR_PAG).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_TXT_FOR_PAG).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_TXT_FOR_PAG).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_COD_USR).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_COD_USR).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_COD_USR).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_COD_USR).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_COD_USR).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_FEC_ING).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_FEC_ING).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_FEC_ING).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_FEC_ING).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_ANT_FEC_ING).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_CHK_AUX).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_COD_USR).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_COD_USR).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_COD_USR).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_COD_USR).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_COD_USR).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_FEC_ING).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_FEC_ING).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_FEC_ING).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_FEC_ING).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_AUT_FEC_ING).setResizable(false);
                                    
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++){
                tblTotDat.getColumnModel().getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tblTotDat.getColumnModel().getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tblTotDat.getColumnModel().getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tblTotDat.getColumnModel().getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotDat.getColumnModel().getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }

//            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//            tblTotDat.getTableHeader().setReorderingAllowed(false);
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnTotDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotDat.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTot objTblColModLis=new ZafTblColModLisTot();
            tblDat.getColumnModel().addColumnModelListener(objTblColModLis);

            ZafTblColModLisTot objTblColModLisTot=new ZafTblColModLisTot();
            tblTotDat.getColumnModel().addColumnModelListener(objTblColModLisTot);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            jscTblTotDat_horScr_dat=spnDat.getHorizontalScrollBar();
            jscTblTotDat_horScr_tot=spnTotDat.getHorizontalScrollBar();
            
            jscTblTotDat_verScr_dat=spnDat.getVerticalScrollBar();
            jscTblTotDat_verScr_fix=spnFix.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            jscTblTotDat_horScr_dat.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    jscTblTotDat_horScr_tot.setValue(evt.getValue());
                }
            });
            //VERTICAL            
            jscTblTotDat_verScr_dat.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    jscTblTotDat_verScr_fix.setValue(evt.getValue());
                }
            });
            spnTotDat.setColumnHeader(null);
            
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_FAC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_FAC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_ABO).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT_PEN).setCellRenderer(objTblCelRenLblTot);
                        
            tcmAux.getColumn(INT_TBL_DAT_ANT_POR).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_ANT_VAL).setCellRenderer(objTblCelRenLblTot);
            
            objTblCelRenLblTot=null;
            
            intNumColEstDatTot=objTblModTot.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private class ZafTblColModLisTot implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotDat.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }
    
    
    private boolean eliminarColTblFix(){
        boolean blnRes=true;
        try{
            objTblModFix.removeAllRows();
            for (int i=(objTblModFix.getColumnCount()-1); i>=0; i--){               
               objTblModFix.removeColumn(tblFix, i);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean eliminarColTblDat(){
        boolean blnRes=true;
        try{
            objTblModDat.removeAllRows();
            for (int i=(objTblModDat.getColumnCount()-1); i>=0; i--){               
               objTblModDat.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean eliminarColTblDatTot(){
        boolean blnRes=true;
        try{
            objTblModTot.removeAllRows();
            for (int i=(objTblModTot.getColumnCount()-1); i>=0; i--){               
               objTblModTot.removeColumn(tblTotDat, i);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean eliminarColTblFixTot(){
        boolean blnRes=true;
        try{
            objTblModTotFix.removeAllRows();
            for (int i=(objTblModTotFix.getColumnCount()-1); i>=0; i--){               
               objTblModTotFix.removeColumn(tblTotFix, i);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función copia los datos de la tabla de datos a las columnas fijas en la tabla fija.
     * @return true: Si la función se ejecutó con éxito.
     * <BR>false: En el caso contrario.
     */
    private boolean copiarDatTblDatTblFij()
    {
        int i, j;
        boolean blnRes=true;
        try
        {
            for (i=0; i<objTblModDat.getRowCountTrue(); i++)
            {
                j=0;
                for (int intAux : arlColFij)
                {
                    objTblModFix.setValueAt(objTblModDat.getValueAt(i, intAux), i, j);
                    j++;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite calcular los valores de la tabla de totales de la tabla dinámica
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calculaTotalesTblFij(){
        boolean blnRes=true;
        BigDecimal bdeValSumValFac=BigDecimal.ZERO;
        try{
            /*Valor de la factura*/
            for(int i=0; i<objTblModFix.getRowCountTrue(); i++){
                bdeValSumValFac=bdeValSumValFac.add(new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_FIX_VAL_FAC)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_FIX_VAL_FAC).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_FIX_VAL_FAC).toString())));
            }
            objTblModTotFix.setValueAt(bdeValSumValFac, 0, INT_TBL_FIX_VAL_FAC);
            bdeValSumValFac=BigDecimal.ZERO;
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite calcular los valores de la tabla de totales de la tabla dinámica
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calculaTotalesTblMov(){
        boolean blnRes=true;
        BigDecimal bdeValSumValFac=BigDecimal.ZERO;
        BigDecimal bdeValSumTotFac=BigDecimal.ZERO;
        BigDecimal bdeValSumTotAbo=BigDecimal.ZERO;
        BigDecimal bdeValSumTotPen=BigDecimal.ZERO;
        BigDecimal bdeValSumGrl=BigDecimal.ZERO;
        try{
            /*Pago al Exterior*/
            for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                bdeValSumValFac=bdeValSumValFac.add(new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_FAC)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_FAC).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_FAC).toString())));
                bdeValSumTotFac=bdeValSumTotFac.add(new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_FAC)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_FAC).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_FAC).toString())));
                bdeValSumTotAbo=bdeValSumTotAbo.add(new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_ABO)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_ABO).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_ABO).toString())));
                bdeValSumTotPen=bdeValSumTotPen.add(new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString())));
            }
            objTblModTot.setValueAt(bdeValSumTotFac, 0, INT_TBL_DAT_VAL_FAC);
            objTblModTot.setValueAt(bdeValSumTotFac, 0, INT_TBL_DAT_VAL_TOT_FAC);
            objTblModTot.setValueAt(bdeValSumTotAbo, 0, INT_TBL_DAT_VAL_TOT_ABO);
            objTblModTot.setValueAt(bdeValSumTotPen, 0, INT_TBL_DAT_VAL_TOT_PEN);
            bdeValSumTotFac=BigDecimal.ZERO;
            bdeValSumTotAbo=BigDecimal.ZERO;
            bdeValSumTotPen=BigDecimal.ZERO;
                
            /*Cargos a Pagar*/
            //Columnas dinámicas de Cargos a Pagar
            for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                bdeValSumGrl=BigDecimal.ZERO;
                for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                    bdeValSumGrl=bdeValSumGrl.add(new BigDecimal(objTblModDat.getValueAt(i, j)==null?"0":(objTblModDat.getValueAt(i, j).toString().equals("")?"0":objTblModDat.getValueAt(i, j).toString())));
                }
                objTblModTot.setValueAt(bdeValSumGrl, 0, j);
            }
            //Columnas dinámicas de Totales de Cargos a Pagar
            for(int j=intNumColIniTotCarPag; j<intNumColFinTotCarPag;j++){
                bdeValSumGrl=BigDecimal.ZERO;
                for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                    bdeValSumGrl=bdeValSumGrl.add(new BigDecimal(objTblModDat.getValueAt(i, j)==null?"0":(objTblModDat.getValueAt(i, j).toString().equals("")?"0":objTblModDat.getValueAt(i, j).toString())));
                }
                objTblModTot.setValueAt(bdeValSumGrl, 0, j);
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    

    private int calcularNumDiaFecVen(int intFil, int intCol, String strFecVenPag){
        Connection conNumDiaFecVen;
        ResultSet rstNumDiaFecVen;
        Statement stmNumDiaFecVen;
        String strNumDiaFecVen="";
        String strFecEmbRea="";
        int intNumDicFecVen=0;
        try{
            strFecEmbRea=(objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA)==null?"":objTblModDat.getValueAt(intFil, INT_TBL_DAT_FEC_EMB_REA).toString());
            if(strFecEmbRea.length()>0){
                conNumDiaFecVen=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conNumDiaFecVen!=null){
                    stmNumDiaFecVen=conNumDiaFecVen.createStatement();
                    strNumDiaFecVen="";
                    strNumDiaFecVen+=" SELECT (CAST('" + strFecVenPag + "' AS DATE) - CAST('" + strFecEmbRea + "' AS DATE)) AS ne_numDia";
                    rstNumDiaFecVen=stmNumDiaFecVen.executeQuery(strNumDiaFecVen);
                    System.out.println("calcularNumDiaFecVen: " + strNumDiaFecVen);
                    if(rstNumDiaFecVen.next())
                        intNumDicFecVen=rstNumDiaFecVen.getInt("ne_numDia");

                    conNumDiaFecVen.close();
                    conNumDiaFecVen=null;
                    stmNumDiaFecVen.close();
                    stmNumDiaFecVen=null;
                    rstNumDiaFecVen.close();
                    rstNumDiaFecVen=null;
                }            
            }
            else
                mostrarMsgInf("Fecha de BL no tiene dato asociado.");

        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return intNumDicFecVen;
    }
    
    /**
     * Función que permite validar si el valor a pagar de fechas de vencimiento es mayor al valor pendiente de pago del pedido
     * @param fila Registro donde se encuentra el cursor
     * @return true Si el valor pendiente es inferior a la suma de los valores estimados a pagar
     * <BR> false Caso contrario
     */
    private boolean isExcValPagFecVen_valPenPag(int fila){
        boolean blnRes=false;
        BigDecimal bdeValPenPag=BigDecimal.ZERO;
        BigDecimal bdeValPagFecVen=BigDecimal.ZERO;
        BigDecimal bdeSumValPagFecVen=BigDecimal.ZERO;
        
        BigDecimal bdeValAnt=BigDecimal.ZERO;//Valor de anticipo
        
        String strDesFecVen="";//3
        int intColFecVen=-1;
        String strCodTipDoc="";
        try{
            strCodTipDoc=(objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC)==null?"":(objTblModDat.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()));
            System.out.println("strCodTipDoc:" + strCodTipDoc);
            if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                //0: Dias
                //1: Fecha Vcto
                //2: % Pag
                //3 Valor pago
                bdeValPenPag=new BigDecimal(objTblModDat.getValueAt(fila, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(fila, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(fila, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                bdeValAnt=new BigDecimal(objTblModDat.getValueAt(fila, INT_TBL_DAT_ANT_VAL)==null?"0":(objTblModDat.getValueAt(fila, INT_TBL_DAT_ANT_VAL).toString().equals("")?"0":objTblModDat.getValueAt(fila, INT_TBL_DAT_ANT_VAL).toString()));
                for(int j=0; j<arlDatFecVen.size();j++){
                    strDesFecVen=objUti.getStringValueAt(arlDatFecVen, j, INT_ARL_FEC_VEN_DES);
                    if(strDesFecVen.equals("3")){//Valores a pagar
                        intColFecVen=objUti.getIntValueAt(arlDatFecVen, j, INT_ARL_FEC_VEN_COL);
                        for(int k=intNumColIniFecVenEst; k<intNumColFinFecVenEst; k++){
                            if(k==intColFecVen){
                                bdeValPagFecVen=new BigDecimal(objTblModDat.getValueAt(fila, k)==null?"0":(objTblModDat.getValueAt(fila, k).toString().equals("")?"0":objTblModDat.getValueAt(fila, k).toString()));
                                bdeSumValPagFecVen=bdeSumValPagFecVen.add(bdeValPagFecVen);
                            }
                        }
                    }
                }
                //Se suman valores de (anticipo + valores de vencimiento)
                if(bdeValPenPag.compareTo((bdeSumValPagFecVen.add(bdeValAnt)))<0){
                    blnRes=true;

                }
                else if(bdeValPenPag.compareTo((bdeSumValPagFecVen.add(bdeValAnt)))>0){
                    if((bdeSumValPagFecVen.add(bdeValAnt)).compareTo(BigDecimal.ZERO)>0)
                        objTblModDat.setValueAt("S", fila, INT_TBL_DAT_COL_VAL_PAG);
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite validar si el valor a pagar de fechas de vencimiento es mayor al valor pendiente de pago del pedido
     * @return true Si el valor pendiente es inferior a la suma de los valores estimados a pagar
     * <BR> false Caso contrario
     */
    private boolean isExcValPagFecVen_valPenPag(){
        boolean blnRes=false;
        BigDecimal bdeValPenPag=BigDecimal.ZERO;
        BigDecimal bdeValPagFecVen=BigDecimal.ZERO;
        BigDecimal bdeSumValPagFecVen=BigDecimal.ZERO;
        
        BigDecimal bdeValAnt=BigDecimal.ZERO;//Valor de anticipo
        
        String strDesFecVen="";//3
        int intColFecVen=-1;
        String strCodTipDoc="";
        try{
            //0: Dias
            //1: Fecha Vcto
            //2: % Pag
            //3 Valor pago
            for(int i=0; i<objTblModDat.getRowCountTrue(); i++){
                bdeValPenPag=new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                bdeValAnt=new BigDecimal(objTblModDat.getValueAt(i, INT_TBL_DAT_ANT_VAL)==null?"0":(objTblModDat.getValueAt(i, INT_TBL_DAT_ANT_VAL).toString().equals("")?"0":objTblModDat.getValueAt(i, INT_TBL_DAT_ANT_VAL).toString()));


                strCodTipDoc=(objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblModDat.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                if(!strCodTipDoc.equals(""+objZafImp.INT_COD_TIP_DOC_NOT_PED_PRE_IMP)){
                    for(int j=0; j<arlDatFecVen.size();j++){
                        strDesFecVen=objUti.getStringValueAt(arlDatFecVen, j, INT_ARL_FEC_VEN_DES);
                        if(strDesFecVen.equals("3")){//Valores a pagar
                            intColFecVen=objUti.getIntValueAt(arlDatFecVen, j, INT_ARL_FEC_VEN_COL);
                            for(int k=intNumColIniFecVenEst; k<intNumColFinFecVenEst; k++){
                                if(k==intColFecVen){
                                    bdeValPagFecVen=new BigDecimal(objTblModDat.getValueAt(i, k)==null?"0":(objTblModDat.getValueAt(i, k).toString().equals("")?"0":objTblModDat.getValueAt(i, k).toString()));
                                    bdeSumValPagFecVen=bdeSumValPagFecVen.add(bdeValPagFecVen);
                                }
                            }
                        }
                    }

                    //Se suman valores de (anticipo + valores de vencimiento)
                    if(bdeValPenPag.compareTo((bdeSumValPagFecVen.add(bdeValAnt)))<0){
                        blnRes=true;
                    }
                    else if(bdeValPenPag.compareTo((bdeSumValPagFecVen.add(bdeValAnt)))>0){
                        if((bdeSumValPagFecVen.add(bdeValAnt)).compareTo(BigDecimal.ZERO)>0){
                            objTblModDat.setValueAt("S", i, INT_TBL_DAT_COL_VAL_PAG);
                            System.out.println("S");
                            objTblCelRenLblColPag.setBackground(Color.yellow);
//                            objTblCelRenLblColPag.updateUI(Color.PINK);
                        }

                    }

                    bdeValPenPag=BigDecimal.ZERO;
                    bdeSumValPagFecVen=BigDecimal.ZERO;
                    bdeValAnt=BigDecimal.ZERO;
                }



            }
                
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite realizar el cálculo de anticipo por porcentaje
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calculaAntPor(int intFil){
        boolean blnRes=true;
        BigDecimal bgdAuxPor=BigDecimal.ZERO;
        BigDecimal bgdPorCre=BigDecimal.ZERO;
        BigDecimal bgdValPen=BigDecimal.ZERO;
        BigDecimal bgdValPag=BigDecimal.ZERO;
        try{
            bgdAuxPor = new BigDecimal ( objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR)==null?"-1":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR).toString().equals("")?"-1":objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_POR).toString()));
            bgdPorCre=objUti.redondearBigDecimal( bgdAuxPor.divide(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar() );
            bgdValPen=objUti.redondearBigDecimal(new BigDecimal( (objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN)==null)?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString()),objParSis.getDecimalesBaseDatos());                     
            
            if( bgdAuxPor.compareTo(new BigDecimal("-1"))==0 ){
                objTblModDat.setValueAt("", intFil, INT_TBL_DAT_ANT_VAL); 
            }
            else{
                bgdValPag = bgdValPen.multiply(bgdPorCre);  
                objTblModDat.setValueAt(""+bgdValPag, intFil, INT_TBL_DAT_ANT_VAL); 
                if(bgdPorCre.compareTo(BigDecimal.ZERO)==0){
                    objTblModDat.setValueAt("", intFil, INT_TBL_DAT_ANT_VAL); 
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite realizar el cálculo de anticipo por porcentaje
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calculaAntVal(int intFil){
        boolean blnRes=true;
        //BigDecimal bgdAuxPor=BigDecimal.ZERO;
        BigDecimal bdeValPor=BigDecimal.ZERO;
        BigDecimal bdeValPen=BigDecimal.ZERO;
        BigDecimal bdeValPag=BigDecimal.ZERO;
        try{
            if( bdeValPag.compareTo(new BigDecimal("-1"))==0 ){
                objTblModDat.setValueAt("", intFil, INT_TBL_DAT_ANT_POR); 
            }
            else{
                bdeValPag=new BigDecimal(objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_ANT_VAL).toString()));
                bdeValPag=objUti.redondearBigDecimal(bdeValPag, objParSis.getDecimalesMostrar());
                bdeValPen=new BigDecimal(objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN)==null?"0":(objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString().equals("")?"0":objTblModDat.getValueAt(intFil, INT_TBL_DAT_VAL_TOT_PEN).toString()));
                bdeValPen=objUti.redondearBigDecimal(bdeValPen, objParSis.getDecimalesMostrar());
                bdeValPor=(bdeValPag.multiply(BigDecimal.valueOf(100))).divide(bdeValPen, objParSis.getDecimalesMostrar());

                objTblModDat.setValueAt(bdeValPor, intFil, INT_TBL_DAT_ANT_POR);                
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    private String strUpdateGP="";
    
    
    
    
    private boolean modificarFechas(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, String FecEmp, String FecPue, String FecArrBod){
        boolean blnRes=true;
        try{
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    

}

