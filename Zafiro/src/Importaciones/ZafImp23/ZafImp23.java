/*
 * ZafImp23.java
 *
 *  Created on 09 de agosto del 2017
 */
package Importaciones.ZafImp23;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.DriverManager;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import java.math.BigDecimal;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenCbo.ZafTblCelRenCbo;
import Librerias.ZafTblUti.ZafTblCelEdiCbo.ZafTblCelEdiCbo;
import java.awt.Color;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp23 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_CHK=1;
    final int INT_TBL_DAT_COD_EMP_ING_IMP=2;
    final int INT_TBL_DAT_COD_LOC_ING_IMP=3;
    final int INT_TBL_DAT_COD_TIP_DOC_ING_IMP=4;
    final int INT_TBL_DAT_COD_DOC_ING_IMP=5;
    final int INT_TBL_DAT_COD_REG_ING_IMP=6;
    final int INT_TBL_DAT_COD_ITM_GRP=7;
    final int INT_TBL_DAT_COD_ITM_EMP=8;
    final int INT_TBL_DAT_COD_ITM_MAE=9;
    final int INT_TBL_DAT_COD_ALT_ITM=10;
    final int INT_TBL_DAT_COD_ITM_LET=11;
    final int INT_TBL_DAT_BUT_ITM=12;
    final int INT_TBL_DAT_NOM_ITM=13;
    final int INT_TBL_DAT_DES_COR_UNI_MED=14;
    final int INT_TBL_DAT_TIP_MOV_DES=15;
    final int INT_TBL_DAT_TIP_MOV_LET=16;
    final int INT_TBL_DAT_CAN_AJU=17;
    final int INT_TBL_DAT_COS_UNI_ING_IMP=18;
    final int INT_TBL_DAT_COS_TOT=19;
    final int INT_TBL_DAT_PES_UNI=20;
    final int INT_TBL_DAT_PES_TOT=21;
    //campos utiles para calculos
    final int INT_TBL_DAT_CAN_ING_IMP=22;
    final int INT_TBL_DAT_CAN_CON=23;
    final int INT_TBL_DAT_CAN_TRS=24;
    final int INT_TBL_DAT_EST_EXI_ITM=25;
    final int INT_TBL_DAT_CAN_ING_EGR=26;

    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4; 
    private static final int INT_ARL_CON_TXT_USRMOD=5; 
    private int intIndiceCon=0;    
    
    //ArrayList: CONTEO
    private ArrayList arlRegAju, arlDatAju;
    private final int INT_ARL_AJU_COD_EMP_CON=0;
    private final int INT_ARL_AJU_COD_LOC_CON=1;
    private final int INT_ARL_AJU_COD_TIP_DOC_CON=2;
    private final int INT_ARL_AJU_COD_DOC_CON=3;
    private final int INT_ARL_AJU_COD_REG_CON=4;
    private final int INT_ARL_AJU_CAN_CON=5;
    private final int INT_ARL_AJU_COD_ITM_MAE=6;
    private final int INT_ARL_AJU_COD_ITM_GRP=7;
    //ArrayList: SOLICITUD DE TRANSFERENCIA
    private final int INT_ARL_AJU_COD_EMP_SOL_TRS=8;
    private final int INT_ARL_AJU_COD_LOC_SOL_TRS=9;
    private final int INT_ARL_AJU_COD_TIP_DOC_SOL_TRS=10;
    private final int INT_ARL_AJU_CAN_SOL_TRS=11;
    //ArrayList: TRANSFERENCIA
    private final int INT_ARL_AJU_COD_EMP_TRS=12;
    private final int INT_ARL_AJU_COD_LOC_TRS=13;
    private final int INT_ARL_AJU_COD_TIP_DOC_TRS=14;
    private final int INT_ARL_AJU_COD_DOC_TRS=15;
    private final int INT_ARL_AJU_CAN_TRS=16;    
    //ArrayList: INGRESO POR IMPORTACION - PEDIDO    
    private final int INT_ARL_AJU_COD_EMP_ING_IMP=17;
    private final int INT_ARL_AJU_COD_LOC_ING_IMP=18;
    private final int INT_ARL_AJU_COD_TIP_DOC_ING_IMP=19;
    private final int INT_ARL_AJU_COD_DOC_ING_IMP=20;
    private final int INT_ARL_AJU_COD_REG_ING_IMP=21;
    private final int INT_ARL_AJU_NUM_DOC_ING_IMP=22;
    private final int INT_ARL_AJU_FEC_DOC_ING_IMP=23;
    private final int INT_ARL_AJU_NUM_PED_ING_IMP=24;
    private final int INT_ARL_AJU_CAN_ING_IMP=25;
    private final int INT_ARL_AJU_COS_UNI_ING_IMP=26;
    private final int INT_ARL_AJU_PES_UNI_ING_IMP=27;
    private final int INT_ARL_AJU_NOM_ITM_ING_IMP=28;
    private final int INT_ARL_AJU_COD_ALT_ITM_ING_IMP=29;
    private final int INT_ARL_AJU_COD_ITM_LET_ING_IMP=30;
    private final int INT_ARL_AJU_UNI_MED_ING_IMP=31;
    private final int INT_ARL_AJU_COD_ITM_EMP_ING_IMP=32;
    //ArrayList: CANTIDAD QUE SE DEBE AJUSTAR
    private final int INT_ARL_AJU_CAN_AJU=33;
    private final int INT_ARL_AJU_EST_PRO=34;
    private final int INT_ARL_AJU_CAN_ING_IMP_ORI=35;
    
    //ArrayList: Stock de inventario
    private ArrayList arlRegStkInvItm, arlDatStkInvItm, arlRegDisInvItm, arlDatDisInvItm;
    private static final int INT_ARL_STK_INV_COD_ITM_GRP=0;
    private static final int INT_ARL_STK_INV_COD_ITM_EMP=1;
    private static final int INT_ARL_STK_INV_COD_ITM_MAE=2;    
    private static final int INT_ARL_STK_INV_COD_LET_ITM=3;     
    private static final int INT_ARL_STK_INV_CAN_ITM=4;
    private static final int INT_ARL_STK_INV_COD_BOD_EMP=5; 
    
    //ArrayList: Vistos Buenos
    private ArrayList arlRegVisBueUsrTipDoc, arlDatVisBueUsrTipDoc;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_EMP=0;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_LOC=1;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_TIP_DOC=2;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE=3;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_COD_USR=4;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE=5;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE=6;
    
    private ArrayList arlRegVisBueUsrTipDocObl, arlDatVisBueUsrTipDocObl;
    final int INT_ARL_VIS_BUE_USR_TIP_DOC_OBL_COD_EMP=0;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_LOC=1;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_TIP_DOC=2;
    final int INT_ARL_COD_VIS_BUE_OBL_OBL_COD_VIS_BUE=3;
    private int intNumVisBueObl;

    //ArrayList: Datos Items
    private final int INT_ARL_ITM_COD_EMP=0;
    private final int INT_ARL_ITM_COD_ITM_GRP=1;
    private final int INT_ARL_ITM_COD_ITM_EMP=2;
    private final int INT_ARL_ITM_COD_ITM_MAE=3;
    private final int INT_ARL_ITM_COD_ALT_ITM=4;
    private final int INT_ARL_ITM_COD_ITM_LET=5;
    private final int INT_ARL_ITM_NOM_ITM=6;
    private final int INT_ARL_ITM_UNI_MED=7;
    
    //Tipos de documentos de ajustes 
    private static final int INT_COD_TIP_DOC_AJU_ING_IMP=293;
    private static final int INT_COD_TIP_DOC_AJU_COM_LOC=294;
    private static final int INT_DAT_VAL_AUT_DOC_AJU=500;
    
    //Variables generales.
    private Connection con, conRef;
    private Statement stm;
    private ResultSet rst;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private Librerias.ZafUtil.UltDocPrint  objUltDocPrint2;
    private ZafStkInv objStkInv;    
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private java.util.Date datFecAux;                                           //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblTxt;                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxtCan, objTblCelEdiTxtCos;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafMouMotAda objMouMotAda;                                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                          //PopupMenu: Establecer PeopuMená en JTable.
    private ZafVenCon vcoTipDoc, vcoIngImp, vcoItm;                                                //Ventana de consulta "Tipo de documento".    
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private MiToolBar objTooBar;
    private ZafRptSis objRptSis;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                                    //Editor: JCheckBox en celda.
    private ZafAsiDia objAsiDia;
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItmAlt, objTblCelEdiTxtVcoItmLet;
    private ZafTblCelRenCbo objTblCelRenCmbBoxMot;
    private ZafTblCelEdiCbo objTblCelEdiCmbBoxMot;   
    private ZafImp objImp;
    private ZafSegMovInv objSegMovInv;    
    private Object objCodSegInsAnt;    
    
    private Vector vecDat,  vecCab,  vecReg, vecAux;

    private String strSQL,  strAux;
    private String strNumDocIngImp, strNumPedIngImp;
    private String strDesCorTipDoc, strDesLarTipDoc;                            //Contenido del campo al obtener el foco.   
    private String strSQLInsItm;
    private String strDocActivo;
    
    private boolean blnCon;                                                     //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                                                  //Determina si hay cambios en el formulario.
    private boolean isAjuPro;
    private boolean isModDoc;
  
    private int intSecGrp, intSecEmp;
    private int intCodEmpIngImp=-1;
    private int intCodLocIngImp=-1;
    private int intCodTipDocIngImp=-1;
    private int intCodDocIngImp=-1;
    private int intSig=1;
    
    private int intCodEmpAju;
    private int intCodLocAju;
    private int intCodTipDocAju;
    private int intCodDocAju;

    /** Crea una nueva instancia de la clase ZafImp23. */
    public ZafImp23(ZafParSis obj){
        try{
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            strSQLInsItm="";
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            arlDatAju=new ArrayList();
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatStkInvItm=new ArrayList();
            arlDatDisInvItm=new ArrayList();
            objSegMovInv=new ZafSegMovInv(objParSis, this);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    /** Crea una nueva instancia de la clase ZafImp23. */
    /**
     * 
     * @param obj
     * @param conexion   Si se desea trabajar con commit del Ajuste de Inventario desde afuera se deberá enviar la conexión, caso contrario enviar null
     * @param codEmpDocOri Código de empresa del Ingreso por Importación
     * @param codLocDocOri Código de local del Ingreso por Importación
     * @param codTipDocOri Código de Tipo de Documento del Ingreso por Importación
     * @param codDocOri    Código de documento del Ingreso por Importación
     * @param codTipDocAju Código de Tipo de Documento del Ajuste de Importación
     */
    public ZafImp23(ZafParSis obj, Connection conexion, int codEmpDocOri, int codLocDocOri, int codTipDocOri, int codDocOri, int codTipDocAju){
        try{
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objParSis.setCodigoMenu(3460);
            strSQLInsItm="";
            intCodEmpIngImp=codEmpDocOri;
            intCodLocIngImp=codLocDocOri;
            intCodTipDocIngImp=codTipDocOri;
            intCodDocIngImp=codDocOri;
            conRef=conexion;
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            
            if( (intCodEmpIngImp!=-1) || (intCodLocIngImp!=-1) || (intCodTipDocIngImp!=-1) || (intCodDocIngImp!=-1) ){
                if(isIngresoImportacion()){
                    
                    initComponents();
                    if (!configurarFrm())
                        exitForm();
                    agregarDocLis();
                    
                    objTooBar.setEstado('n');

                    objTooBar.clickInsertar();//limpia el formulario
                    txtCodTipDoc.setText(""+codTipDocAju);
                    
                    objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));

                    objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                    arlDatAju=new ArrayList();
                    objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
                    objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
                    arlDatStkInvItm=new ArrayList();
                    arlDatDisInvItm=new ArrayList();
                    
                    if(txtCodTipDoc.getText().length()>0)
                        mostrarVenConTipDoc(3);
                    txtCodPed.setText(""+codDocOri);
                    txtCodImp.setText(""+intCodEmpIngImp);
                    if(txtCodPed.getText().length()>0){
                        configurarIngImp();
                        mostrarVenConIngImp(5);
                    }
                    objTooBar.beforeInsertar();//Validaciones
                    
                    
                    if(isValAjuIngImp()){
                        objTooBar.insertar();
                    }
                    objTooBar.setVisible(false);
                }
                else
                    mostrarMsgInf("<HTML>El documento no se ha encontrado.<BR>Verifique y vuelva a intentarlo.");
            }
            else{
                mostrarMsgInf("<HTML>La información no se pudo cargar.<BR>Verifique y vuelva a intentarlo.");
            }
            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    /** Crea una nueva instancia de la clase ZafImp23. */
    public ZafImp23(ZafParSis obj, Connection conexion, int intCodEmpDoc, int intCodLocDoc, int intCodTipDoc, int intCodDoc, int intCodMnu, char chrModTooBar){
        try{
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            strSQLInsItm="";
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            arlDatAju=new ArrayList();
            objUltDocPrint2 = new Librerias.ZafUtil.UltDocPrint(objParSis);
            objStkInv = new Librerias.ZafStkInv.ZafStkInv(objParSis);
            arlDatStkInvItm=new ArrayList();
            arlDatDisInvItm=new ArrayList();
            objParSis.setCodigoMenu(intCodMnu);
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            conRef=conexion;

            intCodEmpIngImp=intCodEmpDoc;
            intCodLocIngImp=intCodLocDoc;
            txtCodTipDoc.setText(""+intCodTipDoc);
            txtCodDoc.setText(""+intCodDoc);
            
            //Consulta
            if(chrModTooBar=='c'){
                objTooBar.setEstado('c');
                objTooBar.consultar();
                objTooBar.afterConsultar();
                objTooBar.setEstado('w');
                objTooBar.setVisible(false);
            }//Modificación
            else if(chrModTooBar=='x'){
                //Consultar
                //objTooBar.setEstado('c');
                objTooBar.consultar();
                objTooBar.afterConsultar();
                //objTooBar.setEstado('w');
                objTooBar.setVisibleInsertar(false);
                objTooBar.setVisibleConsultar(false);
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleEliminar(false);
                objTooBar.setVisibleVistaPreliminar(false);
                objTooBar.setVisibleImprimir(false);
                objTooBar.setVisibleModificar(true);
                objTooBar.setVisibleAceptar(true);
                objTooBar.setVisibleCancelar(false);
                setClosable(false);

                //Modificar
                objTooBar.setEstado('x');
                //después de haber realizado los cambios en el modelo
                objTooBar.clickModificar();
                objTooBar.modificar();
                objTooBar.afterModificar();
                objTooBar.setEstado('m');
//                objTooBar.setVisible(false);
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    

    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try {
            //arlDatItmModTbl=new ArrayList();
            objUti = new ZafUtil();
            
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 6, 105, 20);
            
            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.6");
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());

//            txtCodTipDoc.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblDat();
            
            objTooBar=new MiToolBar(this);
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleVistaPreliminar(false); 
            objTooBar.setVisibleAceptar(true);
            objTooBar.setVisibleCancelar(true);
            panBarBot.add(objTooBar);
            
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //tabFrm.remove(panAsiDia);
                        
            txtCodImp.setEnabled(false);
            txtCodImp.setBackground(Color.WHITE);
            txtNomImp.setEnabled(false);
            txtNomImp.setBackground(Color.WHITE);
            txtCodExp.setEnabled(false);
            txtCodExp.setBackground(Color.WHITE);
            txtNomExp2.setEnabled(false);
            txtNomExp2.setBackground(Color.WHITE);
            txtNomExp.setEnabled(false);
            txtNomExp.setBackground(Color.WHITE);
            txtCodPed.setVisible(false);
            txtNumDocPed.setVisible(false);
            txtCodExp.setVisible(false);
            
            arlDatVisBueUsrTipDoc=new ArrayList();
            arlDatVisBueUsrTipDocObl=new ArrayList();
            
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
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(27);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK, "");
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP, "Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP, "Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, "Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP, "Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_REG_ING_IMP, "Cód.Reg.Ing.Imp.");            
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cód.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_LET, "Cód.Itm.Let.");
            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_TIP_MOV_DES, "Tip.Mov.");
            vecCab.add(INT_TBL_DAT_TIP_MOV_LET, "Tip.Mov.Let.");
            vecCab.add(INT_TBL_DAT_CAN_AJU, "Can.Ing.Egr.");
            vecCab.add(INT_TBL_DAT_COS_UNI_ING_IMP, "Cos.Uni.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COS_TOT, "Cos.Tot.");
            vecCab.add(INT_TBL_DAT_PES_UNI, "Pes.Uni.");
            vecCab.add(INT_TBL_DAT_PES_TOT, "Pes.Tot.");
            vecCab.add(INT_TBL_DAT_CAN_ING_IMP, "Can.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_CON, "Can.Cnf.");
            vecCab.add(INT_TBL_DAT_CAN_TRS, "Can.Trs.");
            vecCab.add(INT_TBL_DAT_EST_EXI_ITM, "Est.Exi.Itm.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_CAN_ING_EGR, "Can.Ing.Egr.");
            
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_AJU, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_UNI_ING_IMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COS_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_UNI, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_TOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ING_IMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_CON, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_TRS, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_LET).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setPreferredWidth(65);            
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_EST_EXI_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_EGR).setPreferredWidth(65);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TRS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_EXI_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_EGR, tblDat);
                        
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_ITM_LET);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_TIP_MOV_DES);
            vecAux.add("" + INT_TBL_DAT_CAN_AJU);
            vecAux.add("" + INT_TBL_DAT_COS_UNI_ING_IMP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenChk.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenChk.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });

                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[8];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            int intColTbl[]=new int[8];
            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
            intColTbl[1]=INT_TBL_DAT_COD_ITM_GRP;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[3]=INT_TBL_DAT_COD_ITM_LET;
            intColTbl[4]=INT_TBL_DAT_NOM_ITM;
            intColTbl[5]=INT_TBL_DAT_PES_UNI;
            intColTbl[6]=INT_TBL_DAT_COD_ITM_EMP;
            intColTbl[7]=INT_TBL_DAT_DES_COR_UNI_MED;


            objTblCelEdiTxtVcoItmAlt=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItmAlt);
            objTblCelEdiTxtVcoItmAlt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    if(strLin.equals("I"))//el item no existe
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    if(strLin.equals("I")){//el item no existe
                        if(isWinCreItm(intCodItmMae, intFil)){//crear nuevo item
                            objTblCelEdiTxtVcoItmAlt.setMosVenCon(false);
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiTxtVcoItmAlt.setMosVenCon(true);
                        }
                        
                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(2);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY a1.tx_codAlt";
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }
                    }
                    else{
                        objTblCelEdiTxtVcoItmAlt.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularPesTot();
                    objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                }
            });
            
            ////INICIO
            objTblCelEdiTxtVcoItmLet=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setCellEditor(objTblCelEdiTxtVcoItmLet);
            objTblCelEdiTxtVcoItmLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    
                    if(strLin.equals("I"))//el item no existe
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //////////

                    if(strLin.equals("I")){//el item no existe
                        if(isWinCreItm(intCodItmMae, intFil)){//crear nuevo item
                            objTblCelEdiTxtVcoItmLet.setMosVenCon(false);
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiTxtVcoItmLet.setMosVenCon(true);
                        }
                        
                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(3);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY a1.tx_codAlt";
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }
                        
                    }
                    else{
                        objTblCelEdiTxtVcoItmLet.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularPesTot();
                    objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                }
                
            });
            

            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                int intCodItmMae=-1;
                boolean blnResBefCon=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    intCodItmMae=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE)==null?"-1":(objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString().equals("")?"-1":objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_ITM_MAE).toString()));
                    
                    if(strLin.equals("I"))//el item no existe
                        objTblCelEdiButVcoItm.setCancelarEdicion(false);
                    else
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {


                    if(strLin.equals("I")){//el item no existe
                        if(isWinCreItm(intCodItmMae, intFil)){//crear nuevo item
                            objTblCelEdiButVcoItm.setMosVenCon(false);
                        }
                        else{
                            blnResBefCon=true;
                            objTblCelEdiButVcoItm.setMosVenCon(true);
                        }
                        
                        if(blnResBefCon){
                            vcoItm.setCampoBusqueda(2);
                            vcoItm.setCriterio1(11);
                            strSQLAdi="";
                            if(txtCodTipDoc.getText().equals(""+INT_COD_TIP_DOC_AJU_ING_IMP))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%I'";
                            else if(txtCodTipDoc.getText().equals("" + INT_COD_TIP_DOC_AJU_COM_LOC))
                                strSQLAdi+="        AND UPPER(a1.tx_codAlt) LIKE '%S'";

                            strSQLAdi+="        AND a1.st_reg NOT IN('T','U') AND a1.st_ser NOT IN('S','T')";
                            strSQLAdi+="        GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQLAdi+="        ORDER BY a1.tx_codAlt";
                            strSQLAdi+=" ) AS b1";
                            strSQLAdi+=" INNER JOIN(";
                            strSQLAdi+=" 	SELECT a1.co_itm AS co_itmEmp, a2.co_itmMae";
                            strSQLAdi+=" 	FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                            strSQLAdi+=" 	ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                            strSQLAdi+=" 		WHERE a1.co_emp=" + intCodEmpAju + "";
                            strSQLAdi+=" 	) AS b2";
                            strSQLAdi+=" 	ON b1.co_itmMae=b2.co_itmMae";
                            strSQLAdi+=" ORDER BY b1.tx_codAlt";
                            System.out.println("strSQLAdi: " + strSQLAdi);
                            vcoItm.setCondicionesSQL(strSQLAdi);
                        }
                    }
                    else{
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularPesTot();
                    objTblMod.setValueAt("I", intFil, INT_TBL_DAT_LIN);
                }

            });
            intColVen=null;
            intColTbl=null;
            
            //ComboBox
            objTblCelRenCmbBoxMot=new ZafTblCelRenCbo();
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setCellRenderer(objTblCelRenCmbBoxMot);
            objTblCelRenCmbBoxMot=null;

            objTblCelEdiCmbBoxMot=new ZafTblCelEdiCbo(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TIP_MOV_DES).setCellEditor(objTblCelEdiCmbBoxMot);
            objTblCelEdiCmbBoxMot.addItem("--Seleccione una opción--");
            objTblCelEdiCmbBoxMot.addItem("E: Empresa");
            objTblCelEdiCmbBoxMot.addItem("R: Reclamos a Terceros");
            objTblCelEdiCmbBoxMot.addItem("S: Sobrante");
            objTblCelEdiCmbBoxMot.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intComBoxSel=-1;
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intComBoxSel=objTblCelEdiCmbBoxMot.getSelectedIndex();
                    System.out.println("intComBoxSel: " + intComBoxSel);
                    if(intComBoxSel==1)
                        objTblMod.setValueAt("E", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                    else if(intComBoxSel==2)
                        objTblMod.setValueAt("R", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                    else if(intComBoxSel==3)
                        objTblMod.setValueAt("S", intFilSel, INT_TBL_DAT_TIP_MOV_LET);
                }
            });
            
            objTblCelRenLblTxt=new ZafTblCelRenLbl();
            objTblCelRenLblTxt.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblTxt.setTipoFormato(objTblCelRenLblTxt.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_ING_IMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_LET).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setCellRenderer(objTblCelRenLblTxt);
            tcmAux.getColumn(INT_TBL_DAT_EST_EXI_ITM).setCellRenderer(objTblCelRenLblTxt);            
            objTblCelRenLblTxt.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenLblTxt.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenLblTxt.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblTxt.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TRS).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstExiItm="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);
                    strEstExiItm=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_EXI_ITM)==null?"":objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_EST_EXI_ITM).toString();

                    if (strEstExiItm.equals("N")){
                        objTblCelRenLbl.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                }
            });            
            
            objTblCelEdiTxtCan=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_AJU).setCellEditor(objTblCelEdiTxtCan);
            objTblCelEdiTxtCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeValCanIngImp=new BigDecimal("0");
                BigDecimal bdeValCanUsr=new BigDecimal("0");

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    bdeValCanIngImp=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_ING_IMP).toString()));
                    if(bdeValCanIngImp.compareTo(BigDecimal.ZERO)<=0)
                        objTblCelEdiTxtCan.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtCan.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    actualizarGlo();
                }
            });
            
            
            objTblCelEdiTxtCos=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI_ING_IMP).setCellEditor(objTblCelEdiTxtCos);
            objTblCelEdiTxtCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intCodItmGrp=-1;
                int intFilSel=-1;
                String strLin="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFilSel, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("I"))
                        objTblCelEdiTxtCos.setCancelarEdicion(false);
                    else
                        objTblCelEdiTxtCos.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCosTot(intFilSel);
                    setValDoc();
                    generaDiarioAjuste();
                    actualizarGlo();
//                    setTipMov(intFilSel);
                }
            });
            
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            
            
            txtValDoc.setEnabled(false);
            txtPesTotKgs.setEnabled(false);
            txtCodDoc.setEnabled(false);
            txtCodBodEmp.setEnabled(false);
            txtNomBodEmp.setEnabled(false);

            //Libero los objetos auxiliares.
            tcmAux = null;
            

        }
        catch (Exception e)
        {
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg = "Código de empresa ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg = "Código de local ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ING_IMP:
                    strMsg = "Código de tipo de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg = "Código de documento ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_REG_ING_IMP:
                    strMsg = "Código de registro ingreso por importación";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código de item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código de item de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código maestro de item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno de item";
                    break;
                case INT_TBL_DAT_COD_ITM_LET:
                    strMsg = "Código de item en letras";
                    break;
                case INT_TBL_DAT_BUT_ITM:
                    strMsg = "Listado de Items";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre de Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de Medida";
                    break;
                case INT_TBL_DAT_TIP_MOV_DES:
                    strMsg = "Tipo de movimiento";
                    break;
                case INT_TBL_DAT_TIP_MOV_LET:
                    strMsg = "Tipo de movimiento letra";
                    break;
                case INT_TBL_DAT_CAN_AJU:
                    strMsg = "Cantidad de ingreso/egreso de ajuste";
                    break;
                case INT_TBL_DAT_COS_UNI_ING_IMP:
                    strMsg = "Costo unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg = "Costo total";
                    break;
                case INT_TBL_DAT_PES_UNI:
                    strMsg = "Peso unitario";
                    break;
                case INT_TBL_DAT_PES_TOT:
                    strMsg = "Pes total";
                    break;
                case INT_TBL_DAT_CAN_ING_IMP:
                    strMsg = "Cantidad del Ingreso por Importación";
                    break;                    
                case INT_TBL_DAT_CAN_CON:
                    strMsg = "Cantidad contada";
                    break;                    
                case INT_TBL_DAT_CAN_TRS:
                    strMsg = "Cantidad transferida";
                    break;
                case INT_TBL_DAT_EST_EXI_ITM:
                    strMsg = "Item existe en el Ingreso por Importación";
                    break;
                case INT_TBL_DAT_CAN_ING_EGR:
                    strMsg = "Cantidad Ingreso/Egreso del Ingreso por Importación";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }   
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a3.co_itmMae");
            arlCam.add("a3.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a1.nd_pesitmkgr");
            arlCam.add("co_itmEmp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Letra");
            arlAli.add("Nombre");
            arlAli.add("Peso(kg)");
            arlAli.add("Item Empresa");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("40");
            arlAncCol.add("250");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.            
//            strSQL="";
//            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
//            strSQL+=" FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
//            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";


            strSQL="";
            strSQL+=" SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.nd_pesitmkgr, b2.co_itmEmp, b1.tx_desCor";
            strSQL+=" FROM(";
            strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr, a2.tx_desCor";
            strSQL+=" 	FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" 	LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
            System.out.println("configurarVenConItm: " + strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=1;
            intColOcu[1]=2;

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setCampoBusqueda(3);
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
            arlAli.add("Cádigo");
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
            if(objParSis.getCodigoUsuario()==1){
                strSQL = "";
                strSQL += " SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                System.out.println("configurarVenConTipDoc: " + strSQL);
            } 
            else{
                strSQL = "";
                strSQL += " SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

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
        }
        catch (Exception e) 
        {
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
    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
            System.out.println("mostrarVenConTipDoc: " + txtCodTipDoc.getText());
//            if(txtCodTipDoc.getText().length()>0){
//                strAux="";
//                strAux+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
//                strAux+=" AND a1.st_reg NOT IN('I','E')";
//                strAux+=" ORDER BY a1.tx_desCor";
//                
//                System.out.println("strAux: " + strAux);
//                vcoTipDoc.setCondicionesSQL(strAux);
//            }
            
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(0);
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
                    } 
                    else{
                        vcoTipDoc.setCampoBusqueda(2);
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
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Código".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                    } 
                    else{
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                        } 
//                        else {
                            //txtDesLarTipDoc.setText(strDesLarTipDoc);
//                        }
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
     * Función que permite cargar información del Ingreso por Importación/Compras Locales
     * @param intTipBus
     * @return 
     */
    private boolean mostrarVenConIngImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoIngImp.setCampoBusqueda(6);
                    vcoIngImp.setVisible(true);
                    if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPed.setText(vcoIngImp.getValueAt(5));
                        txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                        txtNumPed.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));
                        
                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    break;
                case 1: //Búsqueda directa por "Número Documento".
                    if (vcoIngImp.buscar("a1.ne_numDoc", txtNumDocPed.getText()))
                    {
                        txtCodPed.setText(vcoIngImp.getValueAt(5));
                        txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                        txtNumPed.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));
                        
                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(5);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoIngImp.getValueAt(5));
                            txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                            txtNumPed.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));
                            
                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumDocPed.setText(strNumDocIngImp); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.tx_numDoc2", txtNumPed.getText()))
                    {
                        txtCodPed.setText(vcoIngImp.getValueAt(5));
                        txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                        txtNumPed.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));
                        
                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(6);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoIngImp.getValueAt(5));
                            txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                            txtNumPed.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));
                            
                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumPed.setText(strNumPedIngImp);
                        }
                    }
                    break;
                case 5: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoIngImp.getValueAt(5));
                        txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                        txtNumPed.setText(vcoIngImp.getValueAt(7));
                        txtCodImp.setText(vcoIngImp.getValueAt(1));
                        txtNomImp.setText(vcoIngImp.getValueAt(2));
                        txtCodExp.setText(vcoIngImp.getValueAt(8));
                        txtNomExp2.setText(vcoIngImp.getValueAt(9));
                        txtNomExp.setText(vcoIngImp.getValueAt(10));
                        
                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                        txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(4);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoIngImp.getValueAt(5));
                            txtNumDocPed.setText(vcoIngImp.getValueAt(6));
                            txtNumPed.setText(vcoIngImp.getValueAt(7));
                            txtCodImp.setText(vcoIngImp.getValueAt(1));
                            txtNomImp.setText(vcoIngImp.getValueAt(2));
                            txtCodExp.setText(vcoIngImp.getValueAt(8));
                            txtNomExp2.setText(vcoIngImp.getValueAt(9));
                            txtNomExp.setText(vcoIngImp.getValueAt(10));
                            
                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            txtCodBodEmp.setText(vcoIngImp.getValueAt(11).toString());
                            txtNomBodEmp.setText(vcoIngImp.getValueAt(12).toString());
                        }
                        else
                        {
                            txtNumPed.setText(strNumPedIngImp);
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
     * mostrar los "Pedidos".
     */
    private boolean configurarIngImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("tx_nomEmp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            arlCam.add("a1.co_exp");
            arlCam.add("tx_nomExp");
            arlCam.add("tx_nomExp2");
            arlCam.add("co_bodEmp");
            arlCam.add("tx_nomBodEmp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Imp.");
            arlAli.add("Nom.Imp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            arlAli.add("Cód.Exp.");
            arlAli.add("Nom.Exp.");
            arlAli.add("Nom.Exp2.");
            arlAli.add("Cod.Bod.Emp.");
            arlAli.add("Nom.Bod.Emp.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("40");
            arlAncCol.add("80");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("40");
            arlAncCol.add("70");
            arlAncCol.add("130");
            arlAncCol.add("40");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a2.tx_nom AS tx_nomEmp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+="      , a1.co_exp, a3.tx_nom AS tx_nomExp, a3.tx_nom2 AS tx_nomExp2, a4.co_bod AS co_bodEmp, a4.tx_nomBodOrgDes AS tx_nomBodEmp";            
            strSQL+=" FROM (  (tbm_cabMovInv AS a1 INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp)";
            strSQL+="       INNER JOIN tbm_detMovInv AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc)";
            strSQL+=" INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp";            
            strSQL+=" WHERE a1.st_reg<>'E' AND a1.tx_numDoc2 IS NOT NULL ";
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                strSQL+=" AND a1.co_emp="+objParSis.getCodigoEmpresa();
            }
            strSQL+=" AND a1.co_tipDoc IN (select co_tipDoc from tbr_tipDocDetPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+ objParSis.getCodigoMenu()+")";
            strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+="        , a1.co_exp, a3.tx_nom, a3.tx_nom2, a4.co_bod, a4.tx_nomBodOrgDes, a1.fe_doc";
            strSQL+=" ORDER BY a1.fe_doc DESC";
            System.out.println("configurarIngImp: "+strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[9];
            intColOcu[0]=1;
            intColOcu[1]=3;
            intColOcu[2]=4;
            intColOcu[3]=5;
            intColOcu[4]=6;
            intColOcu[5]=8;
            intColOcu[6]=9;
            intColOcu[7]=11;
            intColOcu[8]=12;
            
            vcoIngImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ingresos por Importación", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoIngImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
   
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="   SELECT co_tipDoc";
                    strSQL+="   FROM tbr_tipDocPrg";
                    strSQL+="   WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="   AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="   AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="   AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="   SELECT co_tipDoc";
                    strSQL+="   FROM tbr_tipDocUsr";
                    strSQL+="   WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="   AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="   AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="   AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+="   AND st_reg='S'";
                    strSQL+=" )";
                }
                System.out.println("mostrarTipDocPre: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n'){
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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

        jButton1 = new javax.swing.JButton();
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
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocPed = new javax.swing.JTextField();
        txtNumPed = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblBod = new javax.swing.JLabel();
        txtCodBodEmp = new javax.swing.JTextField();
        txtNomBodEmp = new javax.swing.JTextField();
        lblPesDoc = new javax.swing.JLabel();
        txtPesTotKgs = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDia = new javax.swing.JPanel();
        panBarBot = new javax.swing.JPanel();

        jButton1.setText("jButton1");

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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 108));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setPreferredSize(new java.awt.Dimension(0, 118));
        panCabFil.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 110, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(115, 6, 32, 20);

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
        txtDesCorTipDoc.setBounds(147, 6, 56, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(203, 6, 210, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(413, 6, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(438, 6, 130, 20);

        lblPed.setText("Pedido:");
        panCabFil.add(lblPed);
        lblPed.setBounds(4, 28, 140, 20);
        panCabFil.add(txtCodPed);
        txtCodPed.setBounds(127, 26, 10, 20);

        txtNumDocPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocPedFocusLost(evt);
            }
        });
        txtNumDocPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocPedActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumDocPed);
        txtNumDocPed.setBounds(137, 26, 10, 20);

        txtNumPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedFocusLost(evt);
            }
        });
        txtNumPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumPedActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumPed);
        txtNumPed.setBounds(147, 26, 266, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panCabFil.add(butPedImp);
        butPedImp.setBounds(413, 26, 20, 20);

        lblNumDoc.setText("Número documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc);
        lblNumDoc.setBounds(438, 27, 130, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(570, 26, 105, 20);

        lblImp.setText("Importador:");
        lblImp.setToolTipText("Cuenta");
        panCabFil.add(lblImp);
        lblImp.setBounds(4, 46, 110, 20);
        panCabFil.add(txtCodImp);
        txtCodImp.setBounds(147, 46, 56, 20);
        panCabFil.add(txtNomImp);
        txtNomImp.setBounds(203, 46, 210, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(438, 46, 130, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(570, 46, 105, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCabFil.add(lblExp);
        lblExp.setBounds(4, 66, 110, 20);
        panCabFil.add(txtCodExp);
        txtCodExp.setBounds(117, 66, 30, 20);
        panCabFil.add(txtNomExp2);
        txtNomExp2.setBounds(147, 66, 56, 20);
        panCabFil.add(txtNomExp);
        txtNomExp.setBounds(203, 66, 210, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Código del documento");
        panCabFil.add(lblValDoc);
        lblValDoc.setBounds(438, 66, 130, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabFil.add(txtValDoc);
        txtValDoc.setBounds(570, 66, 105, 20);

        lblBod.setText("Bodega Ingreso/Egreso:");
        lblBod.setToolTipText("Proveedor");
        panCabFil.add(lblBod);
        lblBod.setBounds(4, 86, 125, 20);
        panCabFil.add(txtCodBodEmp);
        txtCodBodEmp.setBounds(147, 86, 56, 20);
        panCabFil.add(txtNomBodEmp);
        txtNomBodEmp.setBounds(203, 86, 210, 20);

        lblPesDoc.setText("Peso del documento:");
        lblPesDoc.setToolTipText("Código del documento");
        panCabFil.add(lblPesDoc);
        lblPesDoc.setBounds(438, 86, 130, 20);

        txtPesTotKgs.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabFil.add(txtPesTotKgs);
        txtPesTotKgs.setBounds(570, 86, 105, 20);

        panGenCab.add(panCabFil, java.awt.BorderLayout.NORTH);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panCon.add(panGenDet, java.awt.BorderLayout.CENTER);

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

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setPreferredSize(new java.awt.Dimension(0, 50));
        panBarBot.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
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
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                dispose();
            }
        } 
        catch (Exception e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtNumDocPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocPedFocusGained
        strNumDocIngImp=txtNumDocPed.getText();
        txtNumDocPed.selectAll();
    }//GEN-LAST:event_txtNumDocPedFocusGained

    private void txtNumDocPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocPedFocusLost
        if (!txtNumDocPed.getText().equalsIgnoreCase(strNumDocIngImp))
        {
            if (txtNumDocPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocPed.setText("");
                txtNumPed.setText("");
                txtCodImp.setText("");
                txtNomImp.setText("");
                txtCodExp.setText("");
                txtNomExp2.setText("");
                txtNomExp.setText("");
            }
            else
            {
                configurarIngImp();
                mostrarVenConIngImp(2);
            }
        }
        else
            txtNumDocPed.setText(strNumDocIngImp);
    }//GEN-LAST:event_txtNumDocPedFocusLost

    private void txtNumDocPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocPedActionPerformed
        txtNumDocPed.transferFocus();
    }//GEN-LAST:event_txtNumDocPedActionPerformed

    private void txtNumPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedFocusGained
        strNumPedIngImp=txtNumPed.getText();
        txtNumPed.selectAll();
    }//GEN-LAST:event_txtNumPedFocusGained

    private void txtNumPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumPed.getText().equalsIgnoreCase(strNumPedIngImp))
        {
            if (txtNumPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumPed.setText("");
                txtNumDocPed.setText("");
                txtCodImp.setText("");
                txtNomImp.setText("");
                txtCodExp.setText("");
                txtNomExp2.setText("");
                txtNomExp.setText("");
            }
            else
            {
                configurarIngImp();
                mostrarVenConIngImp(3);
            }
        }
        else
            txtNumPed.setText(strNumPedIngImp);
    }//GEN-LAST:event_txtNumPedFocusLost

    private void txtNumPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumPedActionPerformed
        txtNumPed.transferFocus();
    }//GEN-LAST:event_txtNumPedActionPerformed

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarIngImp();
        mostrarVenConIngImp(0);
    }//GEN-LAST:event_butPedImpActionPerformed


    /** Cerrar la aplicacián. */
    private void exitForm() {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPedImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblBod;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblPesDoc;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodEmp;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBodEmp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumDocPed;
    private javax.swing.JTextField txtNumPed;
    private javax.swing.JTextField txtPesTotKgs;
    private javax.swing.JTextField txtValDoc;
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
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
                    
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if (txtNumDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        return true;
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
            txtCodPed.setText("");
            txtNumDocPed.setText("");
            txtNumPed.setText("");
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp2.setText("");
            txtNomExp.setText("");
            txtCodBodEmp.setText("");
            txtNomBodEmp.setText("");
            txtPesTotKgs.setText("");            
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
            txtNumDoc.setEnabled(false);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            txtValDoc.setEnabled(false);
            txtValDoc.setText("");
            intCodEmpIngImp=-1;
            intCodLocIngImp=-1;
            intCodTipDocIngImp=-1;
            intCodDocIngImp=-1;
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
    private String getEstReg(String estado){
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
                case 'O':
                    estado="Predocumento";
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

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
    private void agregarDocLis() {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
    }

    private class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }
        
        public void clickInicio() {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ( blnHayCam ){
                            if (isRegPro()) {
                                intIndiceCon=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=0;
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
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if ( blnHayCam ){
                            if (isRegPro()) {
                                intIndiceCon--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon--;
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
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if ( blnHayCam  ){
                            if (isRegPro()) {
                                intIndiceCon++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon++;
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
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if ( blnHayCam ){
                            if (isRegPro()) {
                                intIndiceCon=arlDatCon.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceCon=arlDatCon.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
    
 
        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                limpiarFrm();
                //txtCodDoc.setEditable(false);
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                                
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);                
                mostrarTipDocPre();
                objAsiDia.inicializar();
                
                blnHayCam=false;
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {
            mostrarTipDocPre();
            txtNumDoc.setEditable(true);
        }
        
        public void clickModificar() {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            txtCodTipDoc.setEditable(false);
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtPesTotKgs.setEditable(false);
            txtCodImp.setEditable(false);
            txtNomImp.setEditable(false);
            txtCodExp.setEditable(false);
            txtNomExp.setEditable(false);
            txtNomExp2.setEditable(false);
            
            isValUsrModDoc();
        }        

        public void clickAceptar() {
        }
        public void clickCancelar() {
        }
        public void clickEliminar() {
        }
        public void clickAnular() {
        }        
        public void clickImprimir() {
        }
        public void clickVisPreliminar() {
        }
                
        public boolean aceptar() {
            return true;
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }
        
        public boolean consultar() {
            consultarReg();
            return true;
        }        

        public boolean modificar(){
            boolean blnRes=true;
            if(isModDoc){
                if(actualizarReg()){
                    blnRes=true;
                }
                else
                    blnRes=false;
            }
            else{
                mostrarMsgInf("<HTML>Existen vistos buenos previos no concedidos<BR>o el usuario no cuenta con los permisos respectivos para modificar.</HTML>");
                blnRes=false;
            }
            
            return blnRes;
        }

        public boolean imprimir() {
            return true;
        }     
        public boolean vistaPreliminar() {
            return true;
        }  
        
        public boolean eliminar() {
            return true;
        }
        public boolean anular() {
            return true;
        }        

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)  {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
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

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeInsertar() {
            boolean blnRes=true;
                if (!isCamVal())
                    blnRes=false;
            return blnRes;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
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

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar()  {
            limpiarFrm();
            objTooBar.setEstado('w');
            blnHayCam=false;
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }        
        
    }
    
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                con.setAutoCommit(false);
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que sólo se muestre los documentos asignados al programa.
//                if (txtCodTipDoc.getText().equals("")){
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                    strSQL+="      , a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                    strSQL+="      , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a5.co_mnu";
                    strSQL+="      , a17.co_empRel, a17.co_locRel, a17.co_tipDocRel, a17.co_docRel";
                    strSQL+=" FROM ( (  (  (tbm_cabMovInv AS a1 INNER JOIN tbr_cabMovInv AS a17 ON a1.co_emp=a17.co_emp AND a1.co_loc=a17.co_loc AND a1.co_tipDoc=a17.co_tipDoc AND a1.co_doc=a17.co_doc)";
                    strSQL+="               INNER JOIN tbm_detMovInv AS a6";
                    strSQL+=" 		    ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                    strSQL+=" 		 )";
                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                    strSQL+=" )";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a5.co_mnu=" + objParSis.getCodigoMenu() + ")";
                    }
                    else{
                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc AND a5.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    }
                    strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";
                    }
                    
//                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";                

                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                
                if(!txtNumPed.getText().equals("")) { //Por Pedido
                    strSQL+=" AND a17.co_empRel = " +intCodEmpIngImp+" AND a17.co_locRel ="+intCodLocIngImp+" AND a17.co_tipDocRel ="+intCodTipDocIngImp+" AND a17.co_docRel ="+intCodDocIngImp;
                }
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";
                
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E' AND a1.co_tipDoc<>a17.co_tipDocRel";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="        , a1.co_usrIng, a15.tx_usr, a1.co_usrMod, a16.tx_usr, a5.co_mnu";
                strSQL+="        , a17.co_empRel, a17.co_locRel, a17.co_tipDocRel, a17.co_docRel";
                strSQL+=" ORDER BY a1.fe_doc, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg:  " + strSQL);
                
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,    rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,    rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC, rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,    rst.getInt("co_doc"));
                    arlRegCon.add(INT_ARL_CON_TXT_USRING, rst.getString("tx_nomUsrIng"));
                    arlRegCon.add(INT_ARL_CON_TXT_USRMOD, rst.getString("tx_nomUsrMod"));
                    arlDatCon.add(arlRegCon);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatCon.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatCon.size()) + " registros");
                    intIndiceCon=arlDatCon.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    if(objAsiDia.consultarDiario(intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju)){
                    }
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
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){                
                intCodEmpAju    = objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                intCodLocAju    = objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                intCodTipDocAju = objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                intCodDocAju    = objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                
                txtCodImp.setText(""+intCodEmpAju);
                
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+="      , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.tx_desCor";
                strSQL+="      , a1.tx_desLar, a1.tx_natDoc, a2.co_bodIng, a2.tx_nomBodIng, a3.co_bodEgr, a3.tx_nomBodEgr, a1.nd_tot";
                strSQL+="      , a1.co_docIngImp, a1.ne_numDocIngImp, a1.tx_numIngImp";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	     , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a2.tx_desCor";
                strSQL+=" 	     , a2.tx_desLar, a2.tx_natDoc, a10.co_doc AS co_docIngImp, a10.ne_numDoc AS ne_numDocIngImp, a10.tx_numDoc2 AS tx_numIngImp";
                strSQL+=" 	FROM ( (tbm_cabMovInv AS a1 ";
                strSQL+="                LEFT OUTER JOIN (tbr_cabMovInv AS a9 INNER JOIN tbm_cabMovInv AS a10 ";
                strSQL+="                                ON a9.co_empRel=a10.co_emp AND a9.co_locRel=a10.co_loc AND a9.co_tipDocRel=a10.co_tipDoc AND a9.co_docRel=a10.co_doc";
                strSQL+=" 	         ) ON a1.co_emp=a9.co_emp AND a1.co_loc=a9.co_loc AND a1.co_tipDoc=a9.co_tipDoc AND a1.co_doc=a9.co_doc";
                strSQL+=" 	        )";
                strSQL+=" 	        INNER JOIN (tbm_detMovInv AS a6";
                strSQL+=" 	                    INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                strSQL+=" 		            INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                strSQL+=" 	        )";
                strSQL+=" 	        ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+=" 	)";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" 	       , a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" 	       , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a10.co_doc, a10.ne_numDoc, a10.tx_numDoc2";
                strSQL+=" ) AS a1";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_bod AS co_bodIng, a2.tx_nom AS tx_nomBodIng";
                strSQL+=" 	FROM tbm_detMovInv AS a1 INNER JOIN tbm_bod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	AND a1.nd_can>0";
                strSQL+=") AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" LEFT OUTER JOIN (";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_bod AS co_bodEgr, a2.tx_nom AS tx_nomBodEgr";
                strSQL+=" 	FROM tbm_detMovInv AS a1 INNER JOIN tbm_bod AS a2 ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+="       WHERE a1.co_emp=" + intCodEmpAju;
                strSQL+="       AND a1.co_loc=" + intCodLocAju;
                strSQL+="       AND a1.co_tipDoc=" + intCodTipDocAju;
                strSQL+="       AND a1.co_doc=" + intCodDocAju;
                strSQL+=" 	AND a1.nd_can<0";
                strSQL+=" ) AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2 , a1.nd_tot, a1.nd_pestotkgr";
                strSQL+="        , a1.st_imp, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.tx_desCor , a1.tx_desLar, a1.tx_natDoc, a2.co_bodIng, a2.tx_nomBodIng";
                strSQL+="        , a3.co_bodEgr, a3.tx_nomBodEgr, a1.nd_tot , a1.co_docIngImp, a1.ne_numDocIngImp, a1.tx_numIngImp ";
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("tx_obs2");

                    strAux=rst.getString("co_docIngImp");
                    txtCodPed.setText(strAux);
                    
                    strAux=rst.getString("ne_numDocIngImp");
                    txtNumDocPed.setText(strAux);
                    
                    strAux=rst.getString("tx_numIngImp");
                    txtNumPed.setText(strAux);
                    
                    strAux=rst.getString("co_bodIng");
                    txtCodBodEmp.setText(strAux);
                    strAux=rst.getString("tx_nomBodIng");
                    txtNomBodEmp.setText(strAux);
//                    strAux=rst.getString("co_bodEgr");
//                    strAux=rst.getString("tx_nomBodEgr");                    
                    
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    txtPesTotKgs.setText(strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }

            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceCon+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatCon.size()) );
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
    private boolean cargarDetReg(){//por consulta
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt";
                strSQL+="      , b1.tx_codAlt2, b1.tx_nomItm, b1.tx_uniMed, b1.nd_can";
                strSQL+="      , b1.nd_cosUni, b1.nd_preUni, b1.nd_tot, b1.nd_cosUniGrp, b1.nd_cosTotGrp";
                strSQL+="      , b1.nd_pesitmkgr, b1.nd_pesItmKgrTot, b1.co_itmMae, b2.co_itmGrp, b1.st_tipAju";
                strSQL+=" FROM(";
                strSQL+=" 	 SELECT a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_reg, a2.co_itm, a2.tx_codAlt";
                strSQL+=" 	      , a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, ABS(a2.nd_can) AS nd_can";
                strSQL+=" 	      , ABS(a2.nd_cosUni) AS nd_cosUni, a2.nd_preUni, a2.nd_tot, a2.nd_cosUniGrp, ABS(a2.nd_cosTotGrp) AS nd_cosTotGrp";
                strSQL+=" 	      , a3.nd_pesitmkgr, (ABS(a2.nd_can)*a3.nd_pesitmkgr) AS nd_pesItmKgrTot";
                strSQL+=" 	      , a4.co_itmMae, a2.st_tipAju";
                strSQL+=" 	 FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 	 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	 LEFT OUTER JOIN (tbm_inv AS a3 INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                strSQL+=" 	 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 	 WHERE a1.co_emp=" + intCodEmpAju + "";
                strSQL+="        AND a1.co_loc=" + intCodLocAju + "";
                strSQL+="        AND a1.co_tipDoc=" + intCodTipDocAju + "";
                strSQL+="        AND a1.co_doc=" + intCodDocAju + "";
                strSQL+=" ) AS b1";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT a1.co_itm AS co_itmGrp, a1.co_itmMae";
                strSQL+=" 	FROM tbm_equInv AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_itmMae=b2.co_itmMae";
                strSQL+=" ORDER BY b1.co_reg";
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,                 "");
                    vecReg.add(INT_TBL_DAT_CHK,                 new Boolean(true));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,     "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,     "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, "" + rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,     "" + rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,     "" + rst.getString("co_reg"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         rst.getObject("co_itmGrp"));//
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,         "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         rst.getObject("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         "" + (rst.getObject("tx_codAlt")==null?"":rst.getString("tx_codAlt")));
                    vecReg.add(INT_TBL_DAT_COD_ITM_LET,         "" + (rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2")));
                    vecReg.add(INT_TBL_DAT_BUT_ITM,null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,             "" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     "" + (rst.getObject("tx_uniMed")==null?"":rst.getString("tx_uniMed")));
                    vecReg.add(INT_TBL_DAT_TIP_MOV_DES,         "");
                    vecReg.add(INT_TBL_DAT_TIP_MOV_LET,         "" + rst.getString("st_tipAju"));
                    vecReg.add(INT_TBL_DAT_CAN_AJU,             "" + rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COS_UNI_ING_IMP,     "" + rst.getString("nd_cosUni"));
                    vecReg.add(INT_TBL_DAT_COS_TOT,             "" + rst.getString("nd_tot"));
                    vecReg.add(INT_TBL_DAT_PES_UNI,             "" + (rst.getObject("nd_pesitmkgr")==null?"":rst.getString("nd_pesitmkgr")));
                    vecReg.add(INT_TBL_DAT_PES_TOT,             "" + (rst.getObject("nd_pesItmKgrTot")==null?"":rst.getString("nd_pesItmKgrTot")));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP,         "");
                    vecReg.add(INT_TBL_DAT_CAN_CON,             "");                    
                    vecReg.add(INT_TBL_DAT_CAN_TRS,             "");
                    vecReg.add(INT_TBL_DAT_EST_EXI_ITM,         "");
                    vecReg.add(INT_TBL_DAT_CAN_ING_EGR,         null);
                    vecDat.add(vecReg);
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
                setTipAjuLar();
                objTblMod.initRowsState();
                setEstFilMod();
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
     * Esta función inserta el registro en la base de datos.
     *
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            objCodSegInsAnt=null;
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            
            if(conRef==null)
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            else
                con=conRef;
            
            if (con!=null){
                con.setAutoCommit(false);
                //calcularCosTot();
                
                if(setAsiEstDocAju(con)){
                    if(objImp.setCampoTabla(con, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp
                                                            , intCodDocIngImp, "tbm_cabMovInv", "st_cieAjuInvIngImp", 'S')){//actualiza el estado del documento en el Ingreso por Importacion de si existe un ajuste de inventario
                        if(objImp.setCampoTabla(con, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp
                                                                                , intCodDocIngImp, "tbm_cabMovInv", "st_ingImp", 'C')){//actualiza el estado del documento en el Ingreso por Importacion
                            if(insertar_tbmCabMovInv()){
                                if(objImp.setCampoTabla(con, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText())
                                                                , Integer.parseInt(txtCodDoc.getText()), "tbm_cabMovInv", "st_reg", strDocActivo)){//actualiza el estado del documento en el Ajuste
                                    if(objImp.setCampoTabla(con, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())
                                                            , "tbm_cabMovInv", "co_usrIng", "" + objParSis.getCodigoUsuario())){

                                        if(objImp.setCampoTabla(con, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())
                                                , "tbm_cabMovInv", "fe_ing", strAux)){
                                            if(insertar_tbmDetMovInv()){
                                                if(generaDiarioAjuste()){
                                                    System.out.println("Dentro de generaDiarioAjuste");
                                                    
                                                    if (objAsiDia.insertarDiario(con, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "O")){
                                                        if(getCodSegIngImp()){
                                                            if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 5, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()))){
                                                                if(conRef!=null)
                                                                    con.commit();
                                                                blnRes=true;
                                                            }
                                                            else
                                                                con.rollback();
                                                        }
                                                        else
                                                            con.rollback();
                                                    }
                                                    else
                                                        con.rollback();
                                                }
                                                else
                                                    con.rollback();
                                            }
                                            else
                                                con.rollback();
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
                
                
                if(conRef==null){
                    con.close();
                    con=null;
                }
            }
            
            isAjuPro=blnRes;
            System.out.println("isAjuPro: " + isAjuPro);
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        strAux="";
        strAux+=" Ajuste: " + txtDesCorTipDoc.getText();
        strAux+=" # " + txtNumDoc.getText();
        strAux+="; Pedido: " + txtNumPed.getText();
        objAsiDia.setGlosa(strAux);
    }

    /**
     * Función que permite saber si existen solicitudes de transferencia pendientes de autorizar(Las solicitudes difieren de las transferencias),
     * están relacionadas con las transferencias que han sido previamente autorizadas
     * @return true: Si existen solicitudes pendientes 
     * <BR>   false: Caso contrario
     */
    private boolean isSolTrs(){
        boolean blnRes=false;
        Connection conSolTrs;
        Statement stmSolTrs;
        ResultSet rstSolTrs;
        try{
           conSolTrs=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conSolTrs!=null){
                stmSolTrs=conSolTrs.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numdoc, b1.fe_doc, b1.tx_obs1";
                strSQL+=" , b1.co_reg, b1.co_itm, b1.nd_can AS nd_canSolTrs, b1.co_itmMae";
                strSQL+=" , b1.co_empRelCabMovInv, b1.co_locRelCabMovInv, b1.co_tipDocRelCabMovInv, b1.co_docRelCabMovInv";
                strSQL+=" , b2.nd_can AS nd_canTrs, b2.tx_codAlt, b2.co_itmMae, b2.tx_codAlt2";
                strSQL+=" FROM(";
                //SOLICITUD
                strSQL+="       SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
                strSQL+="       , a4.co_reg, a4.co_itm, a4.nd_can, a6.co_itmMae";
                strSQL+="       , a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
                strSQL+="       FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                strSQL+="	    ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+="	    AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+="       INNER JOIN tbm_cabSolTraInv AS a2";
                strSQL+="       ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv";
                strSQL+="       AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                strSQL+="       INNER JOIN tbm_detSolTraInv AS a4";
                strSQL+="       ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                strSQL+="       INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                strSQL+="       INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                strSQL+="       WHERE a3.co_empRelCabMovInv=" + intCodEmpIngImp + " AND a3.co_locRelCabMovInv=" + intCodLocIngImp + "";
                strSQL+="       AND a3.co_tipDocRelCabMovInv=" + intCodTipDocIngImp + " AND a3.co_docRelCabMovInv=" + intCodDocIngImp + "";
                strSQL+="       AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+="       AND (a2.st_aut='A' OR a2.st_aut IS NULL )";//AUTORIZADO
                strSQL+=" ) AS b1";
                strSQL+=" LEFT OUTER JOIN(";
                //TRANSFERENCIA
                strSQL+=" 	SELECT a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, SUM(a1.nd_can) AS nd_can, a1.tx_codAlt";
                strSQL+=" 	, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 	, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.tx_codAlt, a4.co_itmMae, a2.nd_can";
                strSQL+=" 		, a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+=" 		, a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                strSQL+=" 		, a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                strSQL+=" 		FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                strSQL+=" 			ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                strSQL+=" 		WHERE a2.nd_can<0";
                strSQL+="		AND a5.co_empRel=" + intCodEmpIngImp + " AND a5.co_locRel=" + intCodLocIngImp + " AND a5.co_tipDocRel=" + intCodTipDocIngImp + " AND a5.co_docRel=" + intCodDocIngImp  + "";
                strSQL+=" 	) AS a1";
                strSQL+=" 	GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipdocRel, a1.co_docRel, a1.tx_codAlt, a1.co_itmMae, a1.tx_codAlt2";
                strSQL+=" 	, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_empRelCabMovInv=b2.co_empRelCabMovInv AND b1.co_locRelCabMovInv=b2.co_locRelCabMovInv";
                strSQL+=" AND b1.co_tipDocRelCabMovInv=b2.co_tipDocRelCabMovInv AND b1.co_docRelCabMovInv=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                strSQL+=" WHERE (b1.nd_can - b2.nd_can) <> 0";
                strSQL+=" ORDER BY b1.co_reg";
                rstSolTrs=stmSolTrs.executeQuery(strSQL);
                while(rstSolTrs.next()){
                    blnRes=true;
                }
                
               conSolTrs.close();
               conSolTrs=null;
               stmSolTrs.close();
               stmSolTrs=null;
               rstSolTrs.close();
               rstSolTrs=null;
              
           }
        }
        catch(java.sql.SQLException e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);            
        }
        catch(Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite obtener información del Ingreso por Importacion/Compra Local 
     * que cuentan con valores que deben ajustarse a valores negativos o valores positivos
     * @return 
     */
    private boolean isValAjuIngImp(){
        boolean blnRes=true;
        Connection conValAjuIngImp;
        Statement stmValAjuIngImp;
        ResultSet rstValAjuIngImp;
        BigDecimal bdeCanIngImp=BigDecimal.ZERO;
        BigDecimal bdeCanCon=BigDecimal.ZERO;
        BigDecimal bdeCanSolTrs=BigDecimal.ZERO;
        BigDecimal bdeCanTrs=BigDecimal.ZERO;
        BigDecimal bdeCanAju=BigDecimal.ZERO;
        try{
            conValAjuIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conValAjuIngImp!=null){
                arlDatAju.clear();
                System.out.println("intCodEmpIngImp: " + intCodEmpIngImp);
                System.out.println("intCodLocIngImp: " + intCodLocIngImp);
                System.out.println("intCodTipDocIngImp: " + intCodTipDocIngImp);
                System.out.println("intCodDocIngImp: " + intCodDocIngImp);
                
                if(!objImp.isPedidoCompleto(conValAjuIngImp, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp)){
                    if(objImp.getEstadoDocumento(conValAjuIngImp, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp, "tbm_cabMovInv", "st_ingImp")){
                        
                        if(objImp.getEstDoc().equals("B")){
                            
                            if(!objImp.isSolicitudPendiente(conValAjuIngImp, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp)){
                                System.out.println("error3...");
                                if(!objImp.isConteoPendienteTransferir(conValAjuIngImp, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp)){
                                    System.out.println("error4...");
                                    stmValAjuIngImp=conValAjuIngImp.createStatement();
                                    strSQL="";
                                    //CONTEO_TRANSFERENCIA_INGRESOIMPORTACION
                                    strSQL+=" SELECT b1.co_emp AS co_empCon, b1.co_loc AS co_locCon, b1.co_tipdoc AS co_tipdocCon, b1.co_doc AS co_docCon";
                                    strSQL+=" , b1.co_reg AS co_regCon, CASE WHEN b1.nd_stkCon IS NULL THEN 0 ELSE b1.nd_stkCon END AS nd_canCon, b1.co_itm AS co_itmGrp";
                                    strSQL+=" , b2.co_emp AS co_empSolTrs, b2.co_loc AS co_locSolTrs, b2.co_tipDoc AS co_tipDocSolTrs";
                                    strSQL+=" , CASE WHEN b2.nd_canSolTrs IS NULL THEN 0 ELSE b2.nd_canSolTrs END AS nd_canSolTrs";
                                    strSQL+=" , b3.co_empRel AS co_empTrs, b3.co_locRel AS co_locTrs, b3.co_tipdocRel AS co_tipDocTrs, b3.co_docRel AS co_docTrs";
                                    strSQL+=" , CASE WHEN b3.nd_canTrs IS NULL THEN 0 ELSE b3.nd_canTrs END AS nd_canTrs, b4.co_itmMae";
                                    strSQL+=" , b4.co_emp AS co_empIngImp, b4.co_loc AS co_locIngImp, b4.co_tipDoc AS co_tipDocIngImp, b4.co_doc AS co_docIngImp, b4.co_reg AS co_regIngImp";
                                    strSQL+=" , b4.ne_numDoc AS ne_numDocIngImp, b4.fe_doc AS fe_docIngImp, b4.tx_numDoc2 AS tx_numDoc2IngImp";
                                    strSQL+=" , CASE WHEN b4.nd_can IS NULL THEN 0 ELSE b4.nd_can END AS nd_canIngImp, b4.nd_cosUni";
                                    strSQL+=" , b4.nd_preUniImp";
                                    strSQL+=" , b1.co_itmEmp, b1.tx_nomItm, b1.tx_uniMed, b1.nd_pesItmKgr, b1.tx_codAlt, b1.tx_codAlt2";

                                    strSQL+=" FROM(";
                                    //CONTEO
                                    strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.nd_stkCon,a1.co_itmMae, a1.co_itm";
                                    strSQL+="	, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                                    strSQL+="	, a2.co_itmEmp, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_uniMed, a2.nd_pesItmKgr";
                                    strSQL+="	, CASE WHEN a2.co_emp IS NULL THEN a1.tx_obs1 ELSE a2.tx_nomItm END AS tx_nomItm";
                                    strSQL+="	FROM(";
                                    
                                    strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a8.nd_stkCon,a6.co_itmMae, a2.co_itm";
                                    strSQL+="           , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a8.tx_obs1";
                                    strSQL+="           FROM tbm_cabOrdConInv AS a1";
                                    strSQL+="           INNER JOIN (tbm_detOrdConInv AS a2 INNER JOIN tbm_conInv AS a8";
                                    strSQL+="                   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                                    strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="           LEFT OUTER JOIN (tbm_invBod AS a3 INNER JOIN";
                                    strSQL+="                         (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                                    strSQL+="                                             INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                                    strSQL+="                                        ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
                                    strSQL+="           ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                                    strSQL+="           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+="           AND a1.co_tipDocRel IN(14,245) AND a1.st_reg='A'";
                                    strSQL+="           AND a1.co_empRel=" + intCodEmpIngImp + " AND a1.co_locRel=" + intCodLocIngImp + "";
                                    strSQL+="           AND a1.co_tipDocRel=" + intCodTipDocIngImp + " AND a1.co_docRel=" + intCodDocIngImp + "";
                                    strSQL+="	) AS a1";
                                    strSQL+="	LEFT OUTER JOIN(";
                                    //Información por empresa
                                    strSQL+=" 	SELECT a1.co_emp, a1.tx_nomItm, a1.tx_codAlt, a1.tx_codAlt2, a2.tx_desCor AS tx_uniMed, a1.co_itm AS co_itmEmp";
                                    strSQL+="   , a3.co_itmMae, a1.nd_pesItmKgr";
                                    strSQL+=" 	FROM (tbm_inv AS a1 INNER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                                    strSQL+=" 		INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                                    strSQL+=" 	) AS a2";
                                    strSQL+=" 	ON a1.co_empRel=a2.co_emp AND a1.co_itmMae=a2.co_itmMae";
                                    strSQL+=" ) AS b1";
                                    strSQL+=" LEFT OUTER JOIN(";
                                    //SOLICITUD
                                    strSQL+="        SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_itm, SUM(c1.nd_canSolTrs) AS nd_canSolTrs";
                                    strSQL+="        , c1.co_itmMae, c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+="        FROM(";
                                    strSQL+="                SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.ne_numdoc, a2.fe_doc, a2.tx_obs1";
                                    strSQL+="                , a4.co_reg, a4.co_itm, CASE WHEN a4.nd_can IS NULL THEN 0 ELSE a4.nd_can END AS nd_canSolTrs, a6.co_itmMae";
                                    strSQL+="                , a3.co_empRelCabMovInv, a3.co_locRelCabMovInv, a3.co_tipDocRelCabMovInv, a3.co_docRelCabMovInv";
                                    strSQL+="                FROM tbm_cabMovInv AS a1 INNER JOIN tbr_cabSolTraInvCabMovInv AS a3";
                                    strSQL+="                                ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                                    strSQL+="                                AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                                    strSQL+="                INNER JOIN tbm_cabSolTraInv AS a2";
                                    strSQL+="                ON a2.co_emp=a3.co_empRelCabSolTraInv AND a2.co_loc=a3.co_locRelCabSolTraInv AND a2.co_tipDoc=a3.co_tipDocRelCabSolTraInv AND a2.co_doc=a3.co_docRelCabSolTraInv";
                                    strSQL+="                INNER JOIN tbm_detSolTraInv AS a4";
                                    strSQL+="                ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc AND a2.co_doc=a4.co_doc";
                                    strSQL+="                INNER JOIN tbm_inv AS a5 ON a4.co_emp=a5.co_emp AND a4.co_itm=a5.co_itm";
                                    strSQL+="                INNER JOIN tbm_equInv AS a6 ON a5.co_emp=a6.co_emp AND a5.co_itm=a6.co_itm";
                                    strSQL+="                WHERE a3.co_empRelCabMovInv=" + intCodEmpIngImp + " AND a3.co_locRelCabMovInv=" + intCodLocIngImp + "";
                                    strSQL+="                AND a3.co_tipDocRelCabMovInv=" + intCodTipDocIngImp + " AND a3.co_docRelCabMovInv=" + intCodDocIngImp + "";
                                    strSQL+="                AND a1.st_reg='A' AND a2.st_reg='A'";
                                    strSQL+="                AND a2.st_aut='A'";//AUTORIZADO
                                    strSQL+="        ) AS c1";
                                    strSQL+="        GROUP BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_itm";
                                    strSQL+="        , c1.co_itmMae, c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+=" ) AS b2";
                                    strSQL+=" ON b1.co_empRel=b2.co_empRelCabMovInv AND b1.co_locRel=b2.co_locRelCabMovInv AND b1.co_tipDocRel=b2.co_tipDocRelCabMovInv";
                                    strSQL+=" AND b1.co_docRel=b2.co_docRelCabMovInv AND b1.co_itmMae=b2.co_itmMae";
                                    strSQL+=" LEFT OUTER JOIN(";
                                    //TRANSFERENCIA
                                    strSQL+="        SELECT c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_itmMae, SUM(c1.nd_canTrs) AS nd_canTrs, c1.co_itm";
                                    strSQL+="        , c1.co_empRel, c1.co_locRel, c1.co_tipdocRel, c1.co_docRel";
                                    strSQL+="        , c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+="        FROM(";
                                    strSQL+="                SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_reg, a2.tx_codAlt, a4.co_itmMae";
                                    strSQL+="                , CASE WHEN a2.nd_can IS NULL THEN 0 ELSE ABS(a2.nd_can) END AS nd_canTrs, a2.co_itm";
                                    strSQL+="                , a3.tx_codAlt2, a3.tx_nomItm, a2.tx_unimed, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                                    strSQL+="                , a5.co_empRel, a5.co_locRel, a5.co_tipdocRel, a5.co_docRel";
                                    strSQL+="                , a5.co_empRel AS co_empRelCabMovInv, a5.co_locRel AS co_locRelCabMovInv, a5.co_tipDocRel AS co_tipDocRelCabMovInv, a5.co_docRel AS co_docRelCabMovInv";
                                    strSQL+="                FROM (tbr_cabMovInv AS a5 INNER JOIN tbm_cabMovInv AS a1";
                                    strSQL+="                        ON a5.co_emp=a1.co_emp AND a5.co_loc=a1.co_loc AND a5.co_tipDoc=a1.co_tipDoc AND a5.co_doc=a1.co_doc)";
                                    strSQL+="                INNER JOIN tbm_detMovInv AS a2";
                                    strSQL+="                ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="                INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                                    strSQL+="                INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                                    strSQL+="                WHERE a2.nd_can<0";
                                    strSQL+="                AND a5.co_empRel=" + intCodEmpIngImp + " AND a5.co_locRel=" + intCodLocIngImp + "";
                                    strSQL+="                AND a5.co_tipDocRel=" + intCodTipDocIngImp + " AND a5.co_docRel=" + intCodDocIngImp + "";
                                    strSQL+="        ) AS c1";
                                    strSQL+="        GROUP BY c1.co_emp, c1.co_loc, c1.co_tipdoc, c1.co_itmMae, c1.co_itm";
                                    strSQL+="        , c1.co_empRel, c1.co_locRel, c1.co_tipdocRel, c1.co_docRel";
                                    strSQL+="        , c1.co_empRelCabMovInv, c1.co_locRelCabMovInv, c1.co_tipDocRelCabMovInv, c1.co_docRelCabMovInv";
                                    strSQL+=" ) AS b3";
                                    strSQL+=" ON b2.co_empRelCabMovInv=b3.co_empRel AND b2.co_locRelCabMovInv=b3.co_locRel AND b2.co_tipDocRelCabMovInv=b3.co_tipDocRel AND b2.co_docRelCabMovInv=b3.co_docRel AND b2.co_itmMae=b3.co_itmMae";
                                    strSQL+="    LEFT OUTER JOIN(";
                                    //PEDIDO
                                    strSQL+="            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a1.tx_numDoc2";
                                    strSQL+="            , a2.nd_can, a4.co_itmMae, a2.nd_cosUni, a2.tx_codAlt, a2.tx_codAlt2";
                                    strSQL+="            , a2.tx_nomItm, a2.tx_uniMed, a2.nd_preUniImp, a3.nd_pesItmKgr, a2.co_itm";
                                    strSQL+="            FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a2";
                                    strSQL+="            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                                    strSQL+="            INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                                    strSQL+="            INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                                    strSQL+="            WHERE a1.co_emp=" + intCodEmpIngImp + " AND a1.co_loc=" + intCodLocIngImp + "";
                                    strSQL+="            AND a1.co_tipDoc=" + intCodTipDocIngImp + " AND a1.co_doc=" + intCodDocIngImp + "";
                                    strSQL+="            AND a1.st_reg='A'";
                                    strSQL+="     ) AS b4";
                                    strSQL+="     ON b1.co_empRel=b4.co_emp AND b1.co_locRel=b4.co_loc AND b1.co_tipDocRel=b4.co_tipDoc AND b1.co_docRel=b4.co_doc AND b1.co_itmMae=b4.co_itmMae";
                                    strSQL+=" ORDER BY b1.co_reg";
                                    System.out.println("isValAjuIngImp: " + strSQL);
                                    rstValAjuIngImp=stmValAjuIngImp.executeQuery(strSQL);
                                    while(rstValAjuIngImp.next()){
                                        arlRegAju=new ArrayList();
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_CON,           "" + rstValAjuIngImp.getString("co_empCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_CON,           "" + rstValAjuIngImp.getString("co_locCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_CON,       "" + rstValAjuIngImp.getString("co_tipdocCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_CON,           "" + rstValAjuIngImp.getString("co_docCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_REG_CON,           "" + rstValAjuIngImp.getString("co_regCon"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_CON,               "" + rstValAjuIngImp.getString("nd_canCon"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_MAE,           "" + rstValAjuIngImp.getString("co_itmMae"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_GRP,           "" + rstValAjuIngImp.getString("co_itmGrp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_SOL_TRS,       "" + rstValAjuIngImp.getString("co_empSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_SOL_TRS,       "" + rstValAjuIngImp.getString("co_locSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_SOL_TRS,   "" + rstValAjuIngImp.getString("co_tipDocSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_SOL_TRS,           "" + rstValAjuIngImp.getString("nd_canSolTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_TRS,           "" + rstValAjuIngImp.getString("co_empTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_TRS,           "" + rstValAjuIngImp.getString("co_locTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_TRS,       "" + rstValAjuIngImp.getString("co_tipDocTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_TRS,           "" + rstValAjuIngImp.getString("co_docTrs"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_TRS,               "" + rstValAjuIngImp.getString("nd_canTrs"));
                                        arlRegAju.add(INT_ARL_AJU_COD_EMP_ING_IMP,       rstValAjuIngImp.getObject("co_empIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_LOC_ING_IMP,       rstValAjuIngImp.getObject("co_locIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_TIP_DOC_ING_IMP,   rstValAjuIngImp.getObject("co_tipDocIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_DOC_ING_IMP,       rstValAjuIngImp.getObject("co_docIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COD_REG_ING_IMP,       rstValAjuIngImp.getObject("co_regIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_NUM_DOC_ING_IMP,       rstValAjuIngImp.getObject("ne_numDocIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_FEC_DOC_ING_IMP,       rstValAjuIngImp.getObject("fe_docIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_NUM_PED_ING_IMP,       rstValAjuIngImp.getObject("tx_numDoc2IngImp"));
                                        arlRegAju.add(INT_ARL_AJU_CAN_ING_IMP,           rstValAjuIngImp.getObject("nd_canIngImp"));
                                        arlRegAju.add(INT_ARL_AJU_COS_UNI_ING_IMP,       rstValAjuIngImp.getObject("nd_cosUni"));
                                        arlRegAju.add(INT_ARL_AJU_PES_UNI_ING_IMP,       rstValAjuIngImp.getObject("nd_pesItmKgr"));
                                        arlRegAju.add(INT_ARL_AJU_NOM_ITM_ING_IMP,       rstValAjuIngImp.getObject("tx_nomItm"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ALT_ITM_ING_IMP,   rstValAjuIngImp.getObject("tx_codAlt"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_LET_ING_IMP,   rstValAjuIngImp.getObject("tx_codAlt2"));
                                        arlRegAju.add(INT_ARL_AJU_UNI_MED_ING_IMP,       rstValAjuIngImp.getObject("tx_uniMed"));
                                        arlRegAju.add(INT_ARL_AJU_COD_ITM_EMP_ING_IMP,   rstValAjuIngImp.getObject("co_itmEmp"));
                                        
                                        arlRegAju.add(INT_ARL_AJU_CAN_AJU,               "");
                                        arlRegAju.add(INT_ARL_AJU_EST_PRO,               "");
                                        arlRegAju.add(INT_ARL_AJU_CAN_ING_IMP_ORI,       rstValAjuIngImp.getObject("nd_canIngImp"));
                                        arlDatAju.add(arlRegAju);

                                    }
                                    System.out.println("arlDatAju: " + arlDatAju.toString());

                                    for(int i=0; i<arlDatAju.size(); i++){
                                        bdeCanIngImp=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP);
                                        bdeCanCon=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_CON);
                                        bdeCanSolTrs=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_SOL_TRS);
                                        bdeCanTrs=objUti.getBigDecimalValueAt(arlDatAju, i, INT_ARL_AJU_CAN_TRS);

                                        System.out.println("----------------------------------------------");
                                        System.out.println("bdeCanIngImp: " + bdeCanIngImp);
                                        System.out.println("bdeCanCon: " + bdeCanCon);
                                        System.out.println("bdeCanSolTrs: " + bdeCanSolTrs);
                                        System.out.println("bdeCanTrs: " + bdeCanTrs);

                                        bdeCanAju=BigDecimal.ZERO;

                                        if(bdeCanIngImp.compareTo(BigDecimal.ZERO)==0){//No existe item que ha sido contado por eso la cantidad de Ing.Imp. es cero
                                            objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "I");//No existe el item en ese Ing.Imp.
                                            bdeCanAju=bdeCanCon;
                                            objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, (bdeCanAju.abs()).toString());
                                        }
                                        else if(  (bdeCanIngImp.compareTo(bdeCanCon)==0) && (bdeCanCon.compareTo(bdeCanSolTrs)==0) && (bdeCanSolTrs.compareTo(bdeCanTrs)==0)  ){
                                            objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "N");//No existe ajuste para ese item
                                        }
                                        else{
                                            if(bdeCanSolTrs.compareTo(bdeCanTrs)==0){
                                                //Todos iguales
                                                if(bdeCanCon.compareTo(bdeCanIngImp)==0){
                                                    if(bdeCanCon.compareTo(bdeCanTrs)==0){
                                                         bdeCanAju=bdeCanCon.subtract(bdeCanIngImp);
                                                         if(bdeCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bdeCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                }
                                                //Conteo > Ing.Imp.
                                                else if(bdeCanCon.compareTo(bdeCanIngImp)>0){
                                                    if(bdeCanTrs.compareTo(bdeCanIngImp)==0){
                                                         bdeCanAju=bdeCanCon.subtract(bdeCanIngImp);
                                                         if(bdeCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bdeCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                }
                                                //Conteo < Ing.Imp.
                                                else if(bdeCanCon.compareTo(bdeCanIngImp)<0){
                                                    if(bdeCanTrs.compareTo(bdeCanCon)==0){
                                                         bdeCanAju=bdeCanCon.subtract(bdeCanIngImp);
                                                         if(bdeCanAju.compareTo(BigDecimal.ZERO)!=0){
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU, bdeCanAju.toString());
                                                             objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "S");
                                                         }
                                                    }
                                                    else
                                                        objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                }
                                                else{
                                                    objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                                }
                                            }
                                            else{//la solicitud es diferente a la transferencia
                                                objUti.setStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO, "E");
                                            }
                                        }
                                    }
                                    System.out.println("*arlDatAju: " + arlDatAju.toString());
                                    if(isErrorAjuste()){
                                        mostrarMsgInf("Existen items que están pendientes de procesar");
                                        blnRes=false;
                                    }
                                    else{
                                         if(isAjuste()){
                                            if(!cargarValAju()){
                                                mostrarMsgInf("<HTML>Los datos de ajuste no se cargaron correctamente.<BR>Verifique e intente nuevamente.</HTML>");
                                                blnRes=false;
                                            }
                                        }
                                        else
                                             mostrarMsgInf("El documento no ha generado ningún tipo de Ajuste.");
                                    }

                                    conValAjuIngImp.close();
                                    conValAjuIngImp=null;
                                    stmValAjuIngImp.close();
                                    stmValAjuIngImp=null;
                                    rstValAjuIngImp.close();
                                    rstValAjuIngImp=null;
                                }
                                else{
                                    //mostrarMsgInf("Existen solicitudes pendientes de autorizar(Transferir)");  no se ponen porque en la clase ya estan
                                    blnRes=false;
                                }
                            }
                            else{
                                //mostrarMsgInf("Existen solicitudes pendientes de generarse");  no se ponen porque en la clase ya estan
                                blnRes=false;
                            }
                        }
                        else
                            blnRes=false;
                    }
                    else{
                        blnRes=false;
                    }
                }
                else{
                    mostrarMsgInf("El pedido no ha generado Ajuste de inventario");
                }
                
                setValDoc();
                setTipMovAjuLet();
                setTipAjuLar();
                


                generaDiarioAjuste();
                actualizarGlo();
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
    
    private boolean isErrorAjuste(){
        boolean blnRes=false;
        String strLin="";
        try{
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if(strLin.equals("E")){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite determinar si existe un ajuste de inventario
     * @return true: Si existe ajuste de inventario
     * <BR> false: Caso contrario
     */
    private boolean isAjuste(){
        boolean blnRes=false;
        String strLin="";
        try{
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if(  (strLin.equals("S")) ||  (strLin.equals("I")) ){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si existe algún item que debe ser ajustado y que no se encuentran en el documento origen
     * @return true: Si existe un item no registrado en el documento origen
     * <BR> false: Caso contrario
     */
    private boolean isAjusteItmNotExi(){
        boolean blnRes=false;
        String strLin="";
        try{
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);
                if(strLin.equals("I")){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    
    /**
     * Función que permite cargar los items que se deben ajustar de manera automática de acuerdo al Ingreso por Importación/Compra Local seleccionado
     * @return true: Si se pudo cargar la información
     * <BR>   false: Caso contrario
     */
    private boolean cargarValAju(){
        boolean blnRes=true;
        String strLin="";
        BigDecimal bdeCanItm=BigDecimal.ZERO;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        BigDecimal bdeCosTot=BigDecimal.ZERO;
        BigDecimal bdePesUni=BigDecimal.ZERO;
        BigDecimal bdePesTot=BigDecimal.ZERO;
        int intCodEmpIngImp=-1;//Para saber si el item existe en el Ingreso por Importacion
        try{
            vecDat.clear();
            for(int i=0; i<arlDatAju.size();i++){
                strLin=objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_EST_PRO);                
                if( (strLin.equals("S")) || (strLin.equals("I")) ){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"M");
                    vecReg.add(INT_TBL_DAT_CHK, true);
              
                    intCodEmpIngImp=Integer.parseInt(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP)==null?"-1":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP).equals("")?"-1":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_EMP_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,     intCodEmpIngImp);
                    vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_LOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_TIP_DOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_DOC_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_REG_ING_IMP,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_REG_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_GRP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_EMP_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_MAE));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ALT_ITM_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COD_ITM_LET,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COD_ITM_LET_ING_IMP));
                    vecReg.add(INT_TBL_DAT_BUT_ITM,             null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM,             objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_NOM_ITM_ING_IMP));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_UNI_MED_ING_IMP));
                    vecReg.add(INT_TBL_DAT_TIP_MOV_DES,             null);
                    vecReg.add(INT_TBL_DAT_TIP_MOV_LET,             null);
                    bdeCanItm=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_AJU));
                    vecReg.add(INT_TBL_DAT_CAN_AJU,         bdeCanItm);
                    bdeCosUni=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_COS_UNI_ING_IMP));
                    vecReg.add(INT_TBL_DAT_COS_UNI_ING_IMP,     bdeCosUni);
                    bdeCosTot=bdeCanItm.multiply(bdeCosUni);
                    vecReg.add(INT_TBL_DAT_COS_TOT,             bdeCosTot);
                    bdePesUni=new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_PES_UNI_ING_IMP));
                    vecReg.add(INT_TBL_DAT_PES_UNI,             bdePesUni);
                    vecReg.add(INT_TBL_DAT_PES_TOT,             bdeCanItm.multiply(bdePesUni));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP,         objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP));
                    vecReg.add(INT_TBL_DAT_CAN_CON,             objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_CON));
                    vecReg.add(INT_TBL_DAT_CAN_TRS,             objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_TRS));
                    vecReg.add(INT_TBL_DAT_EST_EXI_ITM,         "");
                    vecReg.add(INT_TBL_DAT_CAN_ING_EGR,         new BigDecimal(objUti.getObjectValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI)==null?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI).toString().equals("")?"0":objUti.getStringValueAt(arlDatAju, i, INT_ARL_AJU_CAN_ING_IMP_ORI)));
                    
                    if(intCodEmpIngImp==-1)//No existe en el Ingreso por Importación
                        vecReg.setElementAt("N", INT_TBL_DAT_EST_EXI_ITM);
                    else//Si existe en el Ingreso por Importación
                        vecReg.setElementAt("S", INT_TBL_DAT_EST_EXI_ITM);
                    vecDat.add(vecReg);
                    
                }
            }
            //if(!setVecModDat(vecDat))
                //mostrarMsgInf("<HTML>Los datos de ajuste no se cargaron correctamente.<BR>Verifique e intente nuevamente.</HTML>");

            setVecModDat(vecDat);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    public boolean setVecModDat(Vector vectorDatos){
        boolean blnRes=true;
        try{
            //Asignar vectores al modelo.
            objTblMod.setData(vectorDatos);
            tblDat.setModel(objTblMod);
            System.out.println("vecDat: " + vecDat.toString());            
            vecDat.clear();
            
            
            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                intSecGrp=objUltDocPrint2.getNumSecDoc(con, objParSis.getCodigoEmpresa() );//la empresa es el grupo
                intSecEmp=objUltDocPrint2.getNumSecDoc(con, Integer.parseInt(txtCodImp.getText()) );//la empresa-importador

           }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabMovInv(){
        int intUltReg;
        boolean blnRes=true;
        int intNumIngImpDia=-1;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                getSecuencias();

                //Obtener el código del último registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpIngImp + "";
                strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                System.out.println("error: " + strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocIngImp";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumIngImpDia=rst.getInt("ne_numDocIngImp");
                    System.out.println("* intNumIngImpDia: " + intNumIngImpDia);
                }
                
                intNumIngImpDia++;
                System.out.println("*** intNumIngImpDia: " + intNumIngImpDia);

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,tx_numDoc2,";
                strSQL+="        tx_numPed,ne_numOrdDes,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="        tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,nd_porIva,";
                strSQL+="        nd_valIva,nd_tot,nd_pesTotKgr,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,co_motDoc,";
                strSQL+="        co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,co_usrMod,tx_comIng,";
                strSQL+="        tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,ne_numVecRecDoc,fe_ultRecDoc,";
                strSQL+="        tx_obsSolAut,tx_obsAutSol,st_aut,st_tipDev,st_conInv,st_docGenDevMerMalEst,";
                strSQL+="        st_regRep,ne_numDocRee,st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="        co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,co_usrAutExcDocConVenCon,";
                strSQL+="        tx_comAutExcDocConVenCon,co_ben,tx_benChq,st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,";
                strSQL+="        co_usrAutAnu,tx_comAutAnu,co_ptoDes,st_emiChqAntRecFacPrv,st_docMarLis,st_itmSerPro,";
                strSQL+="        co_exp,st_cieDis)";
                strSQL+=" VALUES (";
                strSQL+=" " + txtCodImp.getText(); //co_emp
                strSQL+=", " + intCodLocIngImp; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", " + intSecGrp;//ne_secGrp
                strSQL+=", " + intSecEmp;//ne_secEmp
                strSQL+=", Null";//ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2);//ne_numDoc
                strSQL+=", " + objUti.codificar((txtNumPed.getText()+"_AJ"),1);//tx_numDoc2
                strSQL+=", Null";//tx_numPed
                strSQL+=", Null";//ne_numOrdDes
                strSQL+=", Null";//ne_numGui
                strSQL+=", " + txtCodDoc.getText();//co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                if(txtCodTipDoc.getText().equals("" + INT_COD_TIP_DOC_AJU_ING_IMP)){//INIMPO
                    strSQL+=", Null";//co_cli
                    strSQL+=", Null";//tx_ruc
                    strSQL+=", Null";//tx_nomCli
                }
                else{//COMPRAS LOCALES
                    strSQL+=", " + txtCodExp.getText() + "";//co_cli
                    strSQL+=", " + objUti.codificar(txtNomExp2.getText()) + "";//tx_ruc
                    strSQL+=", " + objUti.codificar(txtNomExp.getText()) + "";//tx_nomCli
                }
                
                strSQL+=", Null";//tx_dirCli
                strSQL+=", Null";//tx_telCli
                strSQL+=", Null";//tx_ciuCli
                strSQL+=", Null";//co_com
                strSQL+=", Null";//tx_nomVen
                strSQL+=", Null";//tx_ate
                strSQL+=", Null";//co_forPag
                strSQL+=", Null";//tx_desForPag
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3);//nd_sub
                strSQL+=", 0";//nd_porIva
                strSQL+=", 0";//nd_valIva
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3);//nd_tot
                strSQL+=", " + objUti.codificar(txtPesTotKgs.getText());//nd_pesTotKgr
                strSQL+=", Null";//tx_ptoPar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_motTra
                strSQL+=", Null";//tx_desMotTra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu();//co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'O'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultMod
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrIng
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forRet
                strSQL+=", Null";//tx_vehRet
                strSQL+=", Null";//tx_choRet
                strSQL+=", 0";//ne_numVecRecDoc
                strSQL+=", Null";//fe_ultRecDoc
                strSQL+=", Null";//tx_obsSolAut
                strSQL+=", Null";//tx_obsAutSol
                strSQL+=", Null";//st_aut
                //strSQL+=", " + INT_DAT_VAL_AUT_DOC_AJU + "";//nd_valAut
                strSQL+=", 'C'";//st_tipDev
                strSQL+=", 'C'";//st_conInv
                strSQL+=", Null";//st_docGenDevMerMalEst
                strSQL+=", 'I'";//st_regRep
                strSQL+=", Null";//ne_numDocRee
                strSQL+=", 'N'";//st_creGuiRem
                strSQL+=", 'N'";//st_conInvTraAut
                strSQL+=", Null";//co_locRelSolDevVen
                strSQL+=", Null";//co_tipDocRelSolDevVen
                strSQL+=", Null";//co_docRelSolDevVen
                strSQL+=", Null";//st_excDocConVenCon
                strSQL+=", Null";//fe_autExcDocConVenCon
                strSQL+=", Null";//co_usrAutExcDocConVenCon
                strSQL+=", Null";//tx_comAutExcDocConVenCon
                strSQL+=", Null";//co_ben
                strSQL+=", Null";//tx_benChq
                strSQL+=", 'N'";//st_docConMerSalDemDebFac
                strSQL+=", Null";//st_autAnu
                strSQL+=", Null";//fe_autAnu
                strSQL+=", Null";//co_usrAutAnu
                strSQL+=", Null";//tx_comAutAnu
                strSQL+=", Null";//co_ptoDes             objUti.codificar(txtCodBod.getText())
                strSQL+=", Null";//st_emiChqAntRecFacPrv
                strSQL+=", Null";//st_docMarLis
                strSQL+=", Null";//st_itmSerPro

                if(txtCodTipDoc.getText().equals("" + INT_COD_TIP_DOC_AJU_ING_IMP))//INIMPO
                    strSQL+=", " + objUti.codificar(txtCodExp.getText());//co_exp
                else//Compras Locales
                    strSQL+=", Null";//co_exp
                
                strSQL+=", 'N'";//st_cieDis               
                
                strSQL+=");";
                
                //Inserta la relación entre el Ingreso por Ajuste  y el Importación/Compras Locales
                strSQL+="INSERT INTO tbr_cabmovinv(";
                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, st_reg, co_locrel, co_tipdocrel, ";
                strSQL+="   co_docrel, st_regrep, co_emprel)";
                strSQL+="VALUES (";
                strSQL+="" + intCodEmpIngImp + ",";//co_emp
                strSQL+="" + intCodLocIngImp + ",";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL+="" + txtCodDoc.getText() + ",";//co_doc
                strSQL+="'A',";//st_reg
                strSQL+="" + intCodLocIngImp + ",";//co_locrel
                strSQL+="" + intCodTipDocIngImp + ",";//co_tipdocrel
                strSQL+="" + intCodDocIngImp + ",";//co_docrel
                strSQL+="'I',";//st_regrep
                strSQL+="" + intCodEmpIngImp + "";//co_emprel
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
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
    
    /**
     * Esta función permite insertar la detalle de un registro.
     * @return true: Si se pudo insertar la detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        String strLin="";
        BigDecimal bdeCanIngEgrImp=BigDecimal.ZERO;//Cantidad de ingreso/egreso del documento Ingreso por Importación
        BigDecimal bdeCanAjuIngEgr=BigDecimal.ZERO;//Cantidad que se debe ajustar
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(  (strLin.equals("I")) || (strLin.equals("M"))  ){
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            //Armar la sentencia SQL.
                            //Insertar
                            strSQL="";
                            strSQL+="INSERT INTO tbm_detMovInv(";
                            strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_numFil,co_itm,co_itmAct,tx_codAlt,tx_codAlt2,";
                            strSQL+="        tx_nomItm,tx_uniMed,co_bod,tx_nomBodOrgDes,nd_can,nd_canOrg,nd_canDev,nd_cosUni,nd_preUniVenLis,";
                            strSQL+="        nd_porDesVenMax,nd_preUni,nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                            strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                            strSQL+="        nd_canNunRec,nd_canTotNunRecPro,nd_canTotMalEst,nd_canTotMalEstPro,tx_obs1,co_usrCon,";
                            strSQL+="        co_locRelSolDevVen,co_tipDocRelSolDevVen,co_docRelSolDevVen,co_regRelSolDevVen,co_locRelSolSalTemMer,";
                            strSQL+="        co_tipDocRelSolSalTemMer,co_docRelSolSalTemMer,co_regRelSolSalTemMer,st_cliRetEmpRel,tx_obsCliRetEmpRel,";
                            strSQL+="        nd_ara,nd_preUniImp,nd_valTotFobCfr,nd_cantonmet,nd_valFle,nd_valCfr,nd_valTotAra,nd_valTotGas,st_regRep, st_tipAju)";
                            strSQL+=" (SELECT ";
                            if(objTooBar.getEstado()=='n'){
                                strSQL+=" " + intCodEmpIngImp; //co_emp
                                strSQL+=", " + intCodLocIngImp; //co_loc
                                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                                strSQL+=", " + txtCodDoc.getText(); //co_doc
                            }
                            else if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                                strSQL+=" " + intCodEmpAju; //co_emp
                                strSQL+=", " + intCodLocAju; //co_loc
                                strSQL+=", " + intCodTipDocAju; //co_tipdoc
                                strSQL+=", " + intCodDocAju; //co_doc
                            }

                            strSQL+=", " + j; //co_reg
                            strSQL+=", Null";//ne_numFil
                            strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itm
                            strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itmAct
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)) + "";//tx_codAlt
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET)) + "";//tx_codAlt2
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)) + "";//tx_nomItm
                            strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)) + "";//tx_uniMed
                            strSQL+=", " + txtCodBodEmp.getText() + "";//co_bod
                            strSQL+=", " + objUti.codificar(txtNomBodEmp.getText()) + "";//tx_nomBodOrgDes
                            
//                            bdeCanIngEgrImp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_EGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_EGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_EGR).toString()));
//                            bdeCanAjuIngEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
//                            
//                            
//                            if(bdeCanIngEgrImp.compareTo(BigDecimal.ZERO)>0)
//                                strSQL+=", " + (bdeCanAjuIngEgr.multiply()) + "";//nd_can
//                            else
//                                strSQL+=", " + (bdeCanAjuIngEgr) + "";//nd_can
                            
                            
                            strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU) + "";//nd_can
                            
                            strSQL+=", Null";//nd_canOrg
                            strSQL+=", 0";//nd_canDev
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), 4) + "";//nd_cosUni
                            strSQL+=", Null";//nd_preUniVenLis
                            strSQL+=", Null";//nd_porDesVenMax
                            strSQL+=", Null";//nd_preUni
                            strSQL+=", 0";//nd_porDes
                            strSQL+=", 'N'";//st_ivaCom
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_tot 'precio al publico'
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_cosTot 'precio de compra para vender'
                            strSQL+=", 0";//nd_exi
                            strSQL+=", 0";//nd_valExi
                            strSQL+=", 0";//nd_cosPro
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), 4) + "";//nd_cosUniGrp
                            strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), 4) + "";//nd_cosTotGrp
                            strSQL+=", 0";//nd_exiGrp
                            strSQL+=", 0";//nd_valExiGrp
                            strSQL+=", 0";//nd_cosProGrp
                            strSQL+=", 'S'";//st_merIngEgrFisBod
                            strSQL+=", 0";//nd_canCon
                            strSQL+=", 0";//nd_canNunRec
                            strSQL+=", Null";//nd_canTotNunRecPro
                            strSQL+=", Null";//nd_canTotMalEst
                            strSQL+=", Null";//nd_canTotMalEstPro
                            strSQL+=", Null";//tx_obs1
                            strSQL+=", Null";//co_usrCon
                            strSQL+=", Null";//co_locRelSolDevVen
                            strSQL+=", Null";//co_tipDocRelSolDevVen
                            strSQL+=", Null";//co_docRelSolDevVen
                            strSQL+=", Null";//co_regRelSolDevVen
                            strSQL+=", Null";//co_locRelSolSalTemMer
                            strSQL+=", Null";//co_tipDocRelSolSalTemMer
                            strSQL+=", Null";//co_docRelSolSalTemMer
                            strSQL+=", Null";//co_regRelSolSalTemMer
                            strSQL+=", Null";//st_cliRetEmpRel
                            strSQL+=", Null";//tx_obsCliRetEmpRel
                            strSQL+=", Null";//nd_ara
                            strSQL+=", Null";//nd_preUniImp
                            strSQL+=", Null";//nd_valTotFobCfr
                            strSQL+=", Null";//nd_cantonmet
                            strSQL+=", Null";//nd_valFle
                            strSQL+=", Null";//nd_valCfr
                            strSQL+=", Null";//nd_valTotAra
                            strSQL+=", Null";//nd_valTotGas
                            strSQL+=", 'I'";//st_regRep
                            strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET) + "'";//st_tipAju
                            strSQL+="	    WHERE NOT EXISTS(";
                            strSQL+="           SELECT *FROM tbm_detMovInv";
                            strSQL+="           WHERE co_emp=" + intCodEmpAju + "";
                            strSQL+="           AND co_loc=" + intCodLocAju + "";
                            strSQL+="           AND co_tipDoc=" + intCodTipDocAju + "";
                            strSQL+="           AND co_doc=" + intCodDocAju + "";
                            //strSQL+="           AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+="           AND co_reg=" + j + "";
                            strSQL+="	    )";
                            strSQL+=")";
                            strSQL+=";";
                            System.out.println("INSERT: " + strSQL);
                            strSQLIns+=strSQL;
                            
                            //Actualizar
                            strSQL="";
                            strSQL+="UPDATE tbm_detMovInv";
                            strSQL+=" SET nd_can=" + objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU) + "";
                            strSQL+=", nd_cosUni=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_tot=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), objParSis.getDecimalesBaseDatos()) + "";
                            strSQL+=", nd_cosTot=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_cosUniGrp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_cosTotGrp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";
                            strSQL+=", nd_preUniImp=" + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()), objParSis.getDecimalesBaseDatos()) + "";
                            strSQL+=", co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=", co_itmAct=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=", tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM)) + "";
                            strSQL+=", tx_codAlt2=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET)) + "";
                            strSQL+=", tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)) + "";
                            strSQL+=", tx_uniMed=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED)) + "";
                            strSQL+=", st_tipAju=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET)) + "";
                            strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                            strSQL+=" AND co_loc=" + intCodLocAju + "";
                            strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                            strSQL+=" AND co_doc=" + intCodDocAju + "";                    
                            //strSQL+=" AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+=" AND co_reg=" + j + "";
                            strSQL+=" AND EXISTS(";
                            strSQL+="           SELECT *FROM tbm_detMovInv";
                            strSQL+="           WHERE co_emp=" + intCodEmpAju + "";
                            strSQL+="           AND co_loc=" + intCodLocAju + "";
                            strSQL+="           AND co_tipDoc=" + intCodTipDocAju + "";
                            strSQL+="           AND co_doc=" + intCodDocAju + "";
                            //strSQL+="           AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP) + "";
                            strSQL+="           AND co_reg=" + j + "";
                            strSQL+="            )";
                            strSQL+=";";
                            System.out.println("UPDATE: " + strSQL);
                            strSQLIns+=strSQL;
                            
                            j++;
                            
//                            if(objTooBar.getEstado()=='n'){
                                if(strDocActivo=="A"){
                                    System.out.println("INT_TBL_DAT_COD_ITM_GRP: " + Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString()));
                                    System.out.println("INT_TBL_DAT_COD_ITM_MAE: " + Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString()));
                                    System.out.println("INT_TBL_DAT_COD_ITM_LET: " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET).toString());
                                    System.out.println("INT_TBL_DAT_COD_ITM_EMP: " + Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString()));
                                    System.out.println("INT_TBL_DAT_CAN_AJU: " + new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                                    System.out.println("txtCodBodEmp: " + Integer.parseInt(txtCodBodEmp.getText()));
                                    
                                    if(!_setArlStkInv(Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString())
                                            , Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString())
                                            , objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_LET).toString()
                                            , Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString())
                                            , new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString())
                                            , Integer.parseInt(txtCodBodEmp.getText()))){
                                        blnRes=false;
                                    }
                                }
//                            }
                            
                        }
                    }
                }//fin de for
                
                //No se hace esto en inserción porque no debe mover el inventario ya que está en estado 'O'
                if(strDocActivo=="A"){//cuando el estado es A se debe hacer cambios en el inventario
//                    if( (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='x') ){
                         if(objStkInv.actualizaInventario(con, intCodEmpIngImp, INT_TBL_DAT_CAN_AJU, "+", 1, arlDatStkInvItm)){   //parámetro: 1 - valida y actualiza en tbm_inv
                            if(objStkInv.actualizaInventario(con, intCodEmpIngImp, INT_TBL_DAT_CAN_AJU, "+", 0, arlDatStkInvItm)){//parámetro: 0 - actualiza en tbm_invBod
                                if(objStkInv.actualizaDisponible(con, intCodEmpIngImp, INT_TBL_DAT_CAN_AJU, "+", 0, arlDatDisInvItm)){
                                    arlDatStkInvItm.clear();
                                    arlDatDisInvItm.clear();
                                }
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                        }
                        else
                             blnRes=false;
//                    }
                }
                
                System.out.println("insertar_tbmDetMovInv: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
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
    

    /**
     * Función que permite cargar información que afectará a las tablas de inventario
     * @param intCodItmGrp : Código de item en el grupo
     * @param intCodItmMae : Código de item maestro
     * @param strCodItmLet : Código de item en letras
     * @param intCodItm    : Código de item en la empresa
     * @param bdeCanMov    : Cantidad 
     * @param intCodBodEmp : Código de bodega de empresa
     * @return 
     */
    private boolean _setArlStkInv(int intCodItmGrp, int intCodItmMae, String strCodItmLet
            , int intCodItm, BigDecimal bdeCanMov,int intCodBodEmp){
        boolean blnRes=true;
        try{
            arlRegStkInvItm = new ArrayList();
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItm);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodItmLet);
            arlRegStkInvItm.add(INT_ARL_STK_INV_CAN_ITM,     bdeCanMov);
            arlRegStkInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp);
            arlDatStkInvItm.add(arlRegStkInvItm);
            
            if(bdeCanMov.compareTo(BigDecimal.ZERO)>0){//Sobrante por tanto se adiciona al arreglo para actualizar en tbm_invBod.nd_canDis
                arlRegDisInvItm = new ArrayList();
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_GRP, intCodItmGrp);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_EMP, intCodItm);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_ITM_MAE, intCodItmMae);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_LET_ITM, strCodItmLet);
                arlRegDisInvItm.add(INT_ARL_STK_INV_CAN_ITM,     bdeCanMov);
                arlRegDisInvItm.add(INT_ARL_STK_INV_COD_BOD_EMP, intCodBodEmp);
                arlDatDisInvItm.add(arlRegDisInvItm);
            }
        }
        catch(Exception e){
                objUti.mostrarMsgErr_F1(null, e);
                blnRes=false;
        }
        return blnRes;
    }
 
    
    private boolean generaDiarioAjuste(){
        boolean blnRes=true;
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;
        
        BigDecimal bdeValDoc=new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().toString().equals("")?"0":txtValDoc.getText()));
        String strCodCtaAct="", strCodCtaPas="";

        try{            
            ///para generar el asiento de diario
            objAsiDia.inicializar();            
            vecDatDia.clear();
            
            //if(txtCodTipDoc.getText().equals("14")){//INIMPO
                if(txtCodImp.getText().equals("1")){//Tuval
                    strCodCtaAct="4682";
                    strCodCtaPas="4683";
                }
                else if(txtCodImp.getText().equals("2")){//Castek
                    strCodCtaAct="2299";
                    strCodCtaPas="2300";
                }
                else if(txtCodImp.getText().equals("4")){//Castek
                    strCodCtaAct="3206";
                    strCodCtaPas="3207";
                }
                
                if( bdeValDoc.compareTo(BigDecimal.ZERO)>0  ){
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaAct);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_DEB, "" + bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);                    
                    //total - haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + strCodCtaPas);//intCodCtaTot
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_DEB, "0");
                    vecRegDia.add(INT_VEC_DIA_HAB, "" + bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }

            //}
            System.out.println("vecDatDia: " + vecDatDia.toString());
            objAsiDia.setDetalleDiario(vecDatDia);
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Función que permite calcular el valor del documento y colocarlo en el campo de Valor del Documento
     * @return 
     */
    private boolean setValDoc(){
        boolean blnRes=true;
        BigDecimal bdeValDoc=BigDecimal.ZERO;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        BigDecimal bdeValTotDoc=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeValDoc=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bdeCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
                bdeValTotDoc=bdeValTotDoc.add(bdeValDoc.abs().multiply(bdeCosUni.abs()));
            }
            bdeValTotDoc=objUti.redondearBigDecimal(bdeValTotDoc, objParSis.getDecimalesMostrar());
            txtValDoc.setText(bdeValTotDoc.toString());
            bdeValTotDoc=BigDecimal.ZERO;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si existe el ingreso por importacion que se desea ajustar
     * @return 
     */
    private boolean isIngresoImportacion(){
        boolean blnRes=false;
        Connection conExiIngImp;
        Statement stmExiIngImp;
        ResultSet rstExiIngImp;
        try{
            conExiIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiIngImp!=null){
                stmExiIngImp=conExiIngImp.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a2.tx_nom AS tx_nomEmp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" , a1.co_exp, a3.tx_nom AS tx_nomExp, a3.tx_nom2 AS tx_nomExp2, a4.co_bod AS co_bodEmp, a4.tx_nomBodOrgDes AS tx_nomBodEmp";            
                strSQL+=" FROM (  (tbm_cabMovInv AS a1 INNER JOIN tbm_emp AS a2 ON a1.co_emp=a2.co_emp)";
                strSQL+="       INNER JOIN tbm_detMovInv AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc)";
                strSQL+=" INNER JOIN tbm_expImp AS a3 ON a1.co_exp=a3.co_exp";            
                strSQL+=" WHERE a1.co_tipDoc in(14,245) and a1.co_mnu=2889 AND a1.st_reg='A' AND a1.st_ingImp='B'";
                strSQL+=" AND a1.co_emp=" + intCodEmpIngImp + " AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocIngImp + " AND a1.co_doc=" + intCodDocIngImp + "";
                strSQL+=" GROUP BY a1.co_emp, a2.tx_nom, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" , a1.co_exp, a3.tx_nom, a3.tx_nom2, a4.co_bod, a4.tx_nomBodOrgDes, a1.fe_doc";
                strSQL+=" ORDER BY a1.fe_doc DESC";
                rstExiIngImp=stmExiIngImp.executeQuery(strSQL);
                if(rstExiIngImp.next()){
                    blnRes=true;
                }
                conExiIngImp.close();
                conExiIngImp=null;
                stmExiIngImp.close();
                stmExiIngImp=null;
                rstExiIngImp.close();
                rstExiIngImp=null;
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
     * Función permite calcular el costo total de un item
     * @return true Si se realizó completamente 
     * <BR> false Caso contrario
     */
    private boolean calcularCosTot(int fila){
        boolean blnRes=true;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        BigDecimal bdeCosTot=BigDecimal.ZERO;
        try{
            bdeCan=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString()));
            bdeCosUni=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
            bdeCosTot=bdeCosTot.add(bdeCan.multiply(bdeCosUni));
            objTblMod.setValueAt(bdeCosTot, fila, INT_TBL_DAT_COS_TOT);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función permite calcular el costo total en el modelo de datos
     * @return true Si se realizó completamente 
     * <BR> false Caso contrario
     */
    private boolean calcularCosTot(){
        boolean blnRes=true;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        BigDecimal bdeCosTot=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosTot=BigDecimal.ZERO;
                bdeCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bdeCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI_ING_IMP).toString()));
                bdeCosTot=bdeCosTot.add(bdeCan.multiply(bdeCosUni));
                objTblMod.setValueAt(bdeCosTot, i, INT_TBL_DAT_COS_TOT);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función permite calcular el peso total en el modelo de datos
     * @return true Si se realizó completamente 
     * <BR> false Caso contrario
     */
    private boolean calcularPesTot(){
        boolean blnRes=true;
        BigDecimal bdeCan=BigDecimal.ZERO;
        BigDecimal bdePesUni=BigDecimal.ZERO;
        BigDecimal bdePesTot=BigDecimal.ZERO;
        try{
            System.out.println("calcularPesTot");
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdePesTot=BigDecimal.ZERO;
                bdeCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                bdePesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_UNI).toString()));
                bdePesTot=bdePesTot.add(bdeCan.multiply(bdePesUni));
                System.out.println("bdeCan: " + bdeCan);
                System.out.println("bdePesUni: " + bdePesUni);
                System.out.println("bdePesTot: " + bdePesTot);
                objTblMod.setValueAt(bdePesTot, i, INT_TBL_DAT_PES_TOT);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite conocer si el usuario puede dar visto bueno a ese tipo de documento.
     * Se arma un ArrayList con datos del usuario y tipo de documento de ese visto bueno
     * @return 
     */
    private boolean isUsrPerModVisBueUsrTipDoc(char almacenaArreglo, int codigoUsuario){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                //--Código de Vistos Buenos por usuario y si necesitan autorización previa
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, a1.co_usr, a1.st_necVisBuePre, a1.nd_valAut";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.st_reg='A' AND a1.co_usr=" + codigoUsuario + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                System.out.println("isUsrPerModVisBueUsrTipDoc: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatVisBueUsrTipDoc.clear();
                if(rst.next()){
                    blnRes=true;
                    if(almacenaArreglo=='S'){
                        ///llenado del arreglo que contiene los datos del visto bueno por usuario
                        arlRegVisBueUsrTipDoc=new ArrayList();
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_EMP, rst.getInt("co_emp"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_LOC, rst.getInt("co_loc"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_TIP_DOC, rst.getInt("co_tipDoc"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE, rst.getInt("co_visBue"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_COD_USR, rst.getInt("co_usr"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE, rst.getString("st_necVisBuePre"));
                        arlRegVisBueUsrTipDoc.add(INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE, rst.getBigDecimal("nd_valAut"));
                        arlDatVisBueUsrTipDoc.add(arlRegVisBueUsrTipDoc);
                    }
                }
                System.out.println("*arlDatVisBueUsrTipDoc: " + arlDatVisBueUsrTipDoc.toString());
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * Función que permite conocer si el usuario ha concedido el visto bueno
     * @return 
     */
    private boolean isVisBueMovInv(int codigoVistoBueno){
        boolean blnRes=false;
        try{
            if(con!=null){
                stm=con.createStatement();
                //--Código de Vistos Buenos por usuario y si necesitan autorización previa
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_visbue, a1.co_usr, a1.st_reg";
                strSQL+=" FROM tbm_visbuemovinv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpAju + "";
                strSQL+=" AND a1.co_loc=" + intCodLocAju + "";
                strSQL+=" AND a1.co_tipDoc=" + intCodTipDocAju + "";
                strSQL+=" AND a1.co_doc=" + intCodDocAju + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a1.co_visBue=" + codigoVistoBueno + "";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                rst=stm.executeQuery(strSQL);
                arlDatVisBueUsrTipDoc.clear();
                if(rst.next()){
                    blnRes=true;
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * Función que permite conocer si el usuario puede modificar el documento
     * @return true: Si se puede modificar el documento
     * <BR> false: Caso contrario
     */
    private boolean isValUsrModDoc(){
        boolean blnRes=false;
        boolean isNecVisBuePre;
        BigDecimal bdeValAut=BigDecimal.ZERO;
        int intCodVisBue=-1;
        
        try{
            if(conRef==null)
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            else
                con=conRef;
            
            if(con!=null){
                
                
                
                if(isUsrPerModVisBueUsrTipDoc('S', objParSis.getCodigoUsuario())){//el usuario si puede dar visto bueno (S-> Indica que llena el arreglo)
                    
                    System.out.println("arlDatVisBueUsrTipDoc: " + arlDatVisBueUsrTipDoc.toString());
                    
                    for(int i=0; i<arlDatVisBueUsrTipDoc.size(); i++){//El arreglo contiene solo el dato asociado al usuario
                        isNecVisBuePre=(objUti.getObjectValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE)==null?"N":objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_NEC_AUT_PRE)).toString().equals("S")?true:false;
                        bdeValAut=new BigDecimal(objUti.getObjectValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE)==null?"0":(objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE).equals("")?"0":objUti.getStringValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_VAL_VIS_BUE)));
                        intCodVisBue=objUti.getIntValueAt(arlDatVisBueUsrTipDoc, i, INT_ARL_VIS_BUE_USR_TIP_DOC_COD_VIS_BUE);
                 
                        if(isNecVisBuePre){
                            if(isVisBueMovInv(intCodVisBue-1)){//ya fue concedido el visto bueno previo
                                //puede modificar ?  segun valor
                                if(isValPerVisBueUsrTipDoc())
                                    blnRes=true;
                                else
                                    blnRes=false;
                            }
                            else
                                blnRes=false;
                        }
                        else{//no necesita visto bueno previo                            
                            //puede modificar ?  segun valor
                            if(isValPerVisBueUsrTipDoc())
                                blnRes=true;
                            else
                                blnRes=false;
                        }
                        
                    }
                     
                }
                if(conRef==null){
                    con.close();
                    con=null;
                }
                    
            }
            
            isModDoc=blnRes;
            System.out.println("isValUsrModDoc");
            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
   /**
    * Función que permite saber si el valor del documento puede ser autorizado por el usuario
    * @return true Si se puede proceder con el visto bueno
    * <BR> false: Caso contrario
    */ 
   private boolean isValPerVisBueUsrTipDoc(){
        boolean blnRes=false;
        Statement stmValVisBue;
        ResultSet rstValVisBue;
        try{
            if(con!=null){
                stmValVisBue=con.createStatement();
                //Numero de Vistos Buenos
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue, (CASE WHEN a1.nd_valAut IS NULL THEN (300+1) ELSE a1.nd_valAut END)";
                strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" AND " + txtValDoc.getText() + "<=(CASE WHEN a1.nd_valAut IS NULL THEN (" + txtValDoc.getText() + " +1) ELSE a1.nd_valAut END)";/*Artificio porque el valor es nulo*/
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_visBue";
                System.out.println("isValPerVisBueUsrTipDoc: " + strSQL);
                rstValVisBue=stmValVisBue.executeQuery(strSQL);
                if(rstValVisBue.next()){
                    blnRes=true;//si retorna algo, el usuario si puede dar VB a ese valor
                }
                stmValVisBue.close();
                stmValVisBue=null;
                rstValVisBue.close();
                rstValVisBue=null;
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
     * Función que permite determinar si existe código de seguimiento asociado al Ingreso de Importación
     * @return true Si existe seguimiento para la instancia anterior del Ajuste
     * <BR> false Caso contrario
     */
    private boolean getCodSegIngImp(){
        boolean blnRes=false;
        Object objCodSegPedEmb=null;
        Object objCodSegIngImp=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a3.co_seg AS co_segIngImp, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" , a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                strSQL+=" FROM tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" ON a1.co_emp=a3.co_empRelCabMovInv AND a1.co_loc=a3.co_locRelCabMovInv";
                strSQL+=" AND a1.co_tipDoc=a3.co_tipDocRelCabMovInv AND a1.co_doc=a3.co_docRelCabMovInv";
                strSQL+=" WHERE a1.co_emp=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_loc=" + intCodLocIngImp + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegPedEmb=rst.getObject("co_segPedEmb");
                    objCodSegIngImp=rst.getObject("co_segIngImp");
                }
                
                if(objCodSegIngImp==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegPedEmb;
                }
                else{//ya existe en el seguimiento la nota de pedido por tanto no se debe hacer nada
                    //blnRes=false; no es necesario porque desde el inicio está en "false"
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            System.out.println("Error - getCodSegIngImp SQL: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            System.out.println("Error - getCodSegIngImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    public boolean isAjuPro() {
        return isAjuPro;
    }
    

    private boolean isCallVenItm(int intFilSel){
        boolean blnRes=true;
        int intCodEmp=-1;
        int intCodItm=-1;
        int intCodItmMae=-1;
        String strCodItmAlt="", strCodItmLet="", strNomItm="", strUniMed="";
        try{
            Importaciones.ZafImp23.ZafImp23_01 objImp23_01=new Importaciones.ZafImp23.ZafImp23_01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis);
            objImp23_01.setVisible(true);

            if(objImp23_01.getBotonPresionado()==0){//no se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
                System.out.println("Cero");
            }
            if(objImp23_01.getBotonPresionado()==1){//se presiono el boton Guardar
                System.out.println("Guardar");
                System.out.println("getDatItm: " + objImp23_01.getDatItm());
                strSQLInsItm=objImp23_01.getSQLInsItm();
                
                if(objImp23_01.getDatItm().size()>0){
                    for(int i=0; i<objImp23_01.getDatItm().size(); i++){
                        intCodEmp=objUti.getIntValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_EMP);
                        if(intCodEmp==objParSis.getCodigoEmpresaGrupo()){//el registro es el registro que contiene los datos del grupo
                            intCodItm=objUti.getIntValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_GRP);
                            objTblMod.setValueAt(intCodItm, intFilSel, INT_TBL_DAT_COD_ITM_GRP);
                        }
                        if(intCodEmp==intCodEmpAju){//el registro es el registro que contiene los datos de la empresa
                            intCodItm=objUti.getIntValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_EMP);
                            intCodItmMae=objUti.getIntValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_MAE);
                            strCodItmAlt=objUti.getStringValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_ALT_ITM);
                            strCodItmLet=objUti.getStringValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_COD_ITM_LET);
                            strNomItm=objUti.getStringValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_NOM_ITM);
                            strUniMed=objUti.getStringValueAt(objImp23_01.getDatItm(), i, INT_ARL_ITM_UNI_MED);
                            
                            objTblMod.setValueAt(intCodItm, intFilSel, INT_TBL_DAT_COD_ITM_EMP);
                            objTblMod.setValueAt(intCodItmMae, intFilSel, INT_TBL_DAT_COD_ITM_MAE);
                            objTblMod.setValueAt(strCodItmAlt, intFilSel, INT_TBL_DAT_COD_ALT_ITM);
                            objTblMod.setValueAt(strCodItmLet, intFilSel, INT_TBL_DAT_COD_ITM_LET);
                            objTblMod.setValueAt(strNomItm, intFilSel, INT_TBL_DAT_NOM_ITM);
                            objTblMod.setValueAt(strUniMed, intFilSel, INT_TBL_DAT_DES_COR_UNI_MED);
                        }
                    }   
                }
            }
            if(objImp23_01.getBotonPresionado()==2){//no se ha presionado ningun boton, este caso se va a dar cuando se consulte una nota de embarque, solo en ese caso no se ha presionado ese boton
                System.out.println("Dos");
            }            
        }
        catch(Exception e){
            System.out.println("Error - isCallVenItm: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private boolean isWinCreItm(int codigoItemMaestro, int fila){
        boolean blnRes=false;
        String strTit="";
        String strMsg="";
        try{
            if(codigoItemMaestro==-1){//el item no existe
                javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                strTit="Mensaje del sistema Zafiro";
                strMsg="Desea crear el item de la fila seleccionada?";
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                {
                    blnRes=true;
                    isCallVenItm(fila);
                }
            }
        }
        catch(Exception e){
            System.out.println("Error - getCodSegIngImp: " + e);
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }

    
    
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                //calcularCosTot();
                if(setAsiEstDocAju(con)){
                    if(insertarInventario()){
                        if(actualizaCabMovInv()){
                            if(objImp.setCampoTabla(con, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, "tbm_cabMovInv", "st_reg", "'" + strDocActivo + "'")){//actualiza el estado del documento en el Ajuste
                                if(insertar_tbmDetMovInv()){
                                    if(generaDiarioAjuste()){
                                        
                                        System.out.println("Fecha documento: " + objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
                                        
                                        //if (objAsiDia.insertarDiario(con, intCodEmpIngImp, intCodLocIngImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "O")){
                                        if(objAsiDia.actualizarDiario(con, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"), "O")){
                                            con.commit();
                                            blnRes=true;
                                        }
                                        else
                                            con.rollback();
                                    }
                                    else
                                        con.rollback();
                                }
                                else
                                    con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();

                con.close();
                con=null;                
            }

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarInventario(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                System.out.println("insertarInventario: " + strSQLInsItm);
                stm.executeUpdate(strSQLInsItm);
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
   
    
    
    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizaCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET ";
                strSQL+=" nd_sub=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=" , nd_tot=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=" , nd_pesTotKgr=" + objUti.codificar(txtPesTotKgs.getText()) + "";
                strSQL+=" , tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=" , tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=" , fe_ultMod='" + strAux + "'";
                strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" , tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + intCodEmpAju + "";
                strSQL+=" AND co_loc=" + intCodLocAju + "";
                strSQL+=" AND co_tipDoc=" + intCodTipDocAju + "";
                strSQL+=" AND co_doc=" + intCodDocAju + "";
                strSQL+=";";
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

    
    private boolean setAsiEstDocAju(Connection conEstDocAju){
        boolean blnRes=true;
        int intNumVisBueTipDoc=-1, intNumVisBueOblTipDoc=-1, intNumVisBueAutUsr=-1;
        try{
            conEstDocAju=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conEstDocAju!=null){
                if(objTooBar.getEstado()=='n'){
                    strDocActivo="O";
                }
                else if((objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                    intNumVisBueTipDoc=objImp.getNumVisBueTipDoc(conEstDocAju, intCodEmpAju, intCodLocAju, intCodTipDocAju);//Numero de vistos buenos
                    intNumVisBueOblTipDoc=objImp.getNumVisBueObl(conEstDocAju, intCodEmpAju, intCodLocAju, intCodTipDocAju);//Numero de vistos buenos obligatorios
                    intNumVisBueAutUsr=objImp.getNumVisBueAutUsr(conEstDocAju, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju);
                    
                    System.out.println("intNumVisBueTipDoc: " + intNumVisBueTipDoc);
                    System.out.println("intNumVisBueOblTipDoc: " + intNumVisBueOblTipDoc);
                    System.out.println("intNumVisBueAutUsr: " + intNumVisBueAutUsr);
                    
                    if(intNumVisBueTipDoc>intNumVisBueOblTipDoc){// 2 > 1
                        if(intNumVisBueOblTipDoc==intNumVisBueAutUsr){
                            strDocActivo="A";
                        }
                        else{
                            strDocActivo="O";
                        }
                    }
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
    
    
    private boolean setTipMov(int fila){
        boolean blnRes=true;
        BigDecimal bdeValDoc=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        try{
            if(txtValDoc.getText().length()>0)
                bdeValDoc=new BigDecimal(txtValDoc.getText().equals("")?"0":txtValDoc.getText());
            
            if(bdeValDoc.compareTo(new BigDecimal("500"))>0){
                bdeCan=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_CAN_AJU).toString()));
                if(bdeCan.compareTo(BigDecimal.ZERO)>0){
                    objTblMod.setSelectedItem("S", fila, INT_TBL_DAT_TIP_MOV_DES);
                }
                else if(bdeCan.compareTo(BigDecimal.ZERO)<0){
                    objTblMod.setSelectedItem("R", fila, INT_TBL_DAT_TIP_MOV_DES);
                }
            }
            else{
                objTblMod.setSelectedItem("E", fila, INT_TBL_DAT_TIP_MOV_DES);
            }
            generaDiarioAjuste();
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean setTipMovAjuLet(){
        boolean blnRes=true;
        BigDecimal bdeValDoc=BigDecimal.ZERO;
        BigDecimal bdeCan=BigDecimal.ZERO;
        try{
            if(txtValDoc.getText().length()>0)
                bdeValDoc=new BigDecimal(txtValDoc.getText().equals("")?"0":txtValDoc.getText());
            
            if(bdeValDoc.compareTo(new BigDecimal("500"))>0){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_AJU).toString()));
                    if(bdeCan.compareTo(BigDecimal.ZERO)>0){
                        objTblMod.setSelectedItem("S", i, INT_TBL_DAT_TIP_MOV_LET);
                    }
                    else if(bdeCan.compareTo(BigDecimal.ZERO)<0){
                        objTblMod.setSelectedItem("R", i, INT_TBL_DAT_TIP_MOV_LET);
                    }
                }
            }
            else{
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    objTblMod.setSelectedItem("E", i, INT_TBL_DAT_TIP_MOV_LET);
                }
            }
            generaDiarioAjuste();
            
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean setEstFilMod(){
        boolean blnRes=true;
        int intCodItmGrp=-1;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intCodItmGrp=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP)==null?"-1":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString().equals("")?"-1":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString()));
                if(intCodItmGrp==-1)
                    objTblMod.setValueAt("I", i, INT_TBL_DAT_LIN);
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean setTipAjuLar(){
        boolean blnRes=true;
        String strTipAjuLet="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipAjuLet=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_MOV_LET).toString();
                if(strTipAjuLet.equals("E"))
                    objTblMod.setSelectedItem("E: Empresa", i, INT_TBL_DAT_TIP_MOV_DES);
                else if(strTipAjuLet.equals("R"))
                    objTblMod.setSelectedItem("R: Reclamos a Terceros", i, INT_TBL_DAT_TIP_MOV_DES);
                else if(strTipAjuLet.equals("S"))
                    objTblMod.setSelectedItem("S: Sobrante", i, INT_TBL_DAT_TIP_MOV_DES);
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
}