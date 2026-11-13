/*
 * ZafImp32.java
 *
 * Created on March 15, 2012
 */
package Importaciones.ZafImp32;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTextField;

/**
 *
 * @author  Gigi
 */
public class ZafImp32 extends javax.swing.JInternalFrame 
{
    //ArrayList para consultar Nota de Pedido
    private ArrayList arlDatConNotPed, arlRegConNotPed;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIP_DOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USRING=4;  
    private static final int INT_ARL_CON_TXT_USRMOD=5;
    private static final int INT_ARL_CON_COD_IMP=6;  
    private static final int INT_ARL_CON_COD_CTAACT=7;  
    private static final int INT_ARL_CON_COD_CTAPAS=8;  
    private int intIndiceNotPed=0;
    
    //Constantes: Columnas del JTable Forma de Pago
    final int INT_TBL_FOR_PAG_DAT_LIN=0;
    final int INT_TBL_FOR_PAG_DAT_DIA_CRE=1;
    final int INT_TBL_FOR_PAG_DAT_FEC_VEN=2;
    final int INT_TBL_FOR_PAG_DAT_VAL_PAG=3;
    
    //Constantes: Columnas del JTable Pedidos Embarcados
    private static final int INT_TBL_DAT_PED_EMB_LIN=0;
    private static final int INT_TBL_DAT_PED_EMB_COD_EMP=1;
    private static final int INT_TBL_DAT_PED_EMB_COD_LOC=2;
    private static final int INT_TBL_DAT_PED_EMB_COD_TIP_DOC=3;
    private static final int INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC=4;
    private static final int INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC=5;
    private static final int INT_TBL_DAT_PED_EMB_COD_DOC=6;
    private static final int INT_TBL_DAT_PED_EMB_NUM_DOC=7;
    private static final int INT_TBL_DAT_PED_EMB_FEC_DOC=8;
    private static final int INT_TBL_DAT_PED_EMB_COD_IMP=9;
    private static final int INT_TBL_DAT_PED_EMB_NOM_IMP=10;
    private static final int INT_TBL_DAT_PED_EMB_VAL_DOC=11;
    private static final int INT_TBL_DAT_PED_EMB_BUT=12;
    
    //Jtable: Columnas del JTable Documentos Asociados - Cabecera
    private static final int INT_TBL_DAT_CAB_DOC_ASO_LIN=0;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_NOM_EMP=1;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_EMP=2;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_LOC=3;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC=4;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_DES_COR_TIP_DOC=5;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_DES_LAR_TIP_DOC=6;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_DOC=7;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_NUM_DOC=8;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_FEC_DOC=9;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_CTA=10;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_NUM_CTA=11;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_NOM_CTA=12;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB=13;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB=14;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_BUT=15;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_COD_MNU=16;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_NOM_MNU_CLS=17;
    private static final int INT_TBL_DAT_CAB_DOC_ASO_FEC_ING=18;

    //Jtable: Columnas del JTable Documentos Asociados - Detalle
    private static final int INT_TBL_DAT_DET_DOC_ASO_LIN=0;
    private static final int INT_TBL_DAT_DET_DOC_ASO_COD_EMP=1;
    private static final int INT_TBL_DAT_DET_DOC_ASO_COD_LOC=2;
    private static final int INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC=3;
    private static final int INT_TBL_DAT_DET_DOC_ASO_DES_COR_TIP_DOC=4;
    private static final int INT_TBL_DAT_DET_DOC_ASO_DES_LAR_TIP_DOC=5;
    private static final int INT_TBL_DAT_DET_DOC_ASO_COD_DOC=6;
    private static final int INT_TBL_DAT_DET_DOC_ASO_NUM_DOC=7;
    private static final int INT_TBL_DAT_DET_DOC_ASO_FEC_DOC=8;
    private static final int INT_TBL_DAT_DET_DOC_ASO_BUT=9;    

    //ArrayList objImp32_04: Por los pedidos previos
    private ArrayList arlDatCab_objImp32_04; //Cabecera: contendra la cabecera, sea referenciada o cargada a traves del boton consultar
    private ArrayList arlDatDet_objImp32_04; //Detalle: contendra el detalle, sea referenciado o cargado a traves del boton consultar
    
    //ArrayList:
    private ArrayList arlRegDiaPag_ForPag, arlDatDiaPag_ForPag;
    private int INT_ARL_DIA_PAG_COD_EMP=0;
    private int INT_ARL_DIA_PAG_COD_FOR_PAG=1;
    private int INT_ARL_DIA_PAG_NUM_DIA_CRE=2;  
    
    //ArrayList para Agrupar datos de Exportadores
    private ArrayList arlDatExp, arlRegExp;
    private static final int INT_ARL_DET_COD_EXP=0;  
    
    //ArrayList
    private ArrayList arlRegCtaDeb, arlDatCtaDeb;
    private static final int INT_ARL_CTA_DEB_COD=0;
    private static final int INT_ARL_CTA_DEB_NUM=1;
    private static final int INT_ARL_CTA_DEB_NOM=2;
    private ArrayList arlRegCtaHab, arlDatCtaHab;
    private static final int INT_ARL_CTA_HAB_COD=0;
    private static final int INT_ARL_CTA_HAB_NUM=1;
    private static final int INT_ARL_CTA_HAB_NOM=2;    
    
    //Variables del sistema.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafTblMod objTblModImp32_01, objTblModImp32_02;
    private ZafDatePicker dtpFecDoc, dtpFecColPed, dtpFecEmb, dtpFecPue, dtpFecArr, dtpFecProPed;
    private ZafDocLis objDocLis;
    private java.util.Date datFecAux;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;
    private ZafTblMod objTblModPedEmb, objTblModCabDocAso, objTblModDetDocAso, objTblModForPag;
    private ZafTblPopMnu objTblPopMnuPedEmb, objTblPopMnuCabDocAso, objTblPopMnuDetDocAso, objTblPopMnuForPag;
    private ZafTblFilCab objTblFilCabPedEmb, objTblFilCabCabDocAso, objTblFilDetDocAso;
    private ZafMouMotAdaForPag objMouMotAdaForPag;
    private ZafMouMotAdaPedEmb objMouMotAdaPedEmb;
    private ZafMouMotAdaCabDocAso objMouMotAdaCabDocAso;
    private ZafMouMotAdaDetDocAso objMouMotAdaDetDocAso;
    private ZafTblBus objTblBusPedEmb, objTblBusCabDocAso, objTblBusDetDocAso;
    private ZafTblCelRenLbl objTblCelRenLblPedEmb, objTblCelRenLblCabDocAso, objTblCelRenLblDetDocAso, objTblCelRenLbl;
    private ZafTblCelRenLbl objTblCelRenLblRanPzaTon;
    private ZafTblOrd objTblOrdPedEmb, objTblOrdCabDocAso, objTblOrdDetDocAso;
    private ZafTblCelEdiButGen objTblCelEdiButGenPedEmb, objTblCelEdiButGenCabDocAso, objTblCelEdiButGenDetDocAso;
    private ZafTblCelRenBut    objTblCelRenButPedEmb, objTblCelRenButCabDocAso, objTblCelRenButDetDocAso;   
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelEdiTxt objTblCelEdiTxtValForPag;    
    private ZafSegMovInv objSegMovInv;
    private ZafNotCorEle objNotCorEle;
    private ZafImp objZafImp; 
    private ZafAsiDia objAsiDia, objAsiDiaRev, objAsiDiaNue;
    private ZafVenCon vcoTipDoc, vcoImp, vcoPrv, vcoPto, vcoSeg, vcoPag, vcoForPag, vcoMeses, vcoAniEmb;
    public ZafVenCon vcoExp;
    
    private ZafImp32_04 objImp32_04;
    private ZafImp32_01 objImp32_01;
    private ZafImp32_02 objImp32_02;    

    private Object objCodSegInsAnt;
    private Object objNivAct1, objNivAct2, objNivAct3, objNivAct4, objNivAct5, objNivAct6;
    private Object objNivPas1, objNivPas2, objNivPas3, objNivPas4, objNivPas5, objNivPas6;
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatPedEmb, vecCabPedEmb, vecRegPedEmb, vecAuxPedEmb, vecAux;//tabla del pedido embarcado
    private Vector vecDatCabDocAso, vecCabCabDocAso, vecRegCabDocAso, vecAuxCabDocAso;//tabla de cabecera de Documentos asociados
    private Vector vecDatDetDocAso, vecCabDetDocAso, vecRegDetDocAso, vecAuxDetDocAso;//tabla de detalle de Documentos asociados
    private boolean blnHayCam;
    private boolean blnVenConEme=false; //true: Se llama desde otra ventana. false: Consulta directamente desde el programa.
    
    //Rangos para calcular la cantidad permitida en Pzas cuando es pedido en Toneladas.
    ////////////////////////////////////////////////////
    private BigDecimal bgdMin= new BigDecimal ("0.95");
    private BigDecimal bgdMax= new BigDecimal ("1.05"); 
    ////////////////////////////////////////////////////
    
    private BigDecimal bgdSumValTotFobCfrDb; 
    
    private int intSig=1;
    private int intTipNotPed;
    private int intNivCtaPadAct, intNivCtaPadPas;
    private int intCodCtaPedAct, intCodCtaPedPas;//Crearlo en todas las empresas
    private int intCodCtaPedActIns, intCodCtaPedPasIns;//Para inserción por importación
    private int intNumNotPedDia;

    private String strCodImp, strNomImp;
    private String strCodPrv, strNomPrv;
    private String strCodExp, strNomExp;
    private String strCodPto, strNomPto;
    private String strCodSeg, strNomSeg;
    private String strCodPag, strNomPag;
    private String strNumDoc, strMonDoc;
    private String strCodMesEmb, strNomMesEmb;
    private String strCodMesArr, strNomMesArr;
    private String strCodMesPue, strNomMesPue;
    private String strCodAniEmb, strNomAniEmb;
    private String strCodCtaPadPedAct, strCodCtaPadPedPas;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    private String strFecEmbIni, strFecEmbFin, strFecArrIni, strFecArrFin;
    private String strCodForPag, strNomForPag;
    private String strEstNotPedLis;
    private String strCodImpAnt;
    private String strCodLocPre;
    private String strNumCtaActAsiDia, strNumCtaPasAsiDia;
    private String strNomCtaActAsiDia, strNomCtaPasAsiDia;    
    private String strEstDocOri;    //Indica si el documento fue inactivado, es de reversion o es el nuevo documento
    private String strAux;
    private String strSQL;
    private String strVer="v0.12.18"; 

    /** Creates new form ZafImp32 */
    public ZafImp32(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatDiaPag_ForPag=new ArrayList();
            arlDatCtaDeb=new ArrayList(); 
            arlDatCtaHab=new ArrayList(); 
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis(); 
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
    
    /** Creates new form ZafImp32 */
    public ZafImp32(ZafParSis obj, int codigoLocal, int codigoTipoDocumento, int codigoDocumento) {
        try{
            blnVenConEme=true;//Se esta llamando desde otro programa
            objParSis=(ZafParSis)obj.clone();
            arlDatCtaDeb=new ArrayList();
            arlDatCtaHab=new ArrayList();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                configurarFrm();
                agregarDocLis();
                
                objParSis.setCodigoLocal(codigoLocal);
                txtCodTipDoc.setText(""+codigoTipoDocumento);
                txtCodDoc.setText(""+codigoDocumento);
                
                objTooBar.beforeConsultar();
                objTooBar.consultar();
                objTooBar.afterConsultar();
                objTooBar.setVisible(false);
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
            objDocLis=new ZafDocLis();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objNotCorEle = new ZafNotCorEle(objParSis);  /*Libreria para enviar correos electronicos a usuario.*/
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objSegMovInv=new ZafSegMovInv(objParSis, this);            
            
            //ToolBar
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            objTooBar.agregarSeparador();
            objTooBar.agregarBoton(butPedPre);

            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(582, 4, 102, 20);
            
            //Configurar ZafDatePicker:
            dtpFecColPed=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecColPed.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecColPed.setText("");
            panCab.add(dtpFecColPed);
            dtpFecColPed.setBounds(582, 24, 102, 20);     
            dtpFecColPed.setEnabled(false);
            
            //Configurar ZafDatePicker:
            dtpFecProPed=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecProPed.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecProPed.setText("");
            panCab.add(dtpFecProPed);
            dtpFecProPed.setBounds(582, 44, 102, 20);     
            dtpFecProPed.setEnabled(false);            
            
            //Configurar ZafDatePicker:
            dtpFecEmb=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecEmb.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecEmb.setText("");
            panCab.add(dtpFecEmb);
            dtpFecEmb.setBounds(582, 64, 102, 20);     

            //Configurar ZafDatePicker:
            dtpFecPue=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecPue.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecPue.setText("");
            panCab.add(dtpFecPue);
            dtpFecPue.setBounds(582, 84, 102, 20);     
            
            //Configurar ZafDatePicker:
            dtpFecArr=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecArr.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecArr.setText("");
            panCab.add(dtpFecArr);
            dtpFecArr.setBounds(582, 104, 102, 20);     

            //////////////////////////////////////////////////////////////////////
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodImp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomImp.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtNomPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtCodExp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomExp2.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecColPed.setBackground(objParSis.getColorCamposSistema());
            dtpFecProPed.setBackground(objParSis.getColorCamposSistema());
            dtpFecEmb.setBackground(objParSis.getColorCamposSistema());
            dtpFecPue.setBackground(objParSis.getColorCamposSistema());
            dtpFecArr.setBackground(objParSis.getColorCamposSistema());
            txtAniEmb.setBackground(objParSis.getColorCamposObligatorios());
            txtCodMesEmb.setBackground(objParSis.getColorCamposObligatorios());
            txtNomMesEmb.setBackground(objParSis.getColorCamposObligatorios());
            txtCodMesPue.setBackground(objParSis.getColorCamposObligatorios());
            txtNomMesPue.setBackground(objParSis.getColorCamposObligatorios());            
            txtCodMesArr.setBackground(objParSis.getColorCamposObligatorios());
            txtNomMesArr.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumPedOri.setBackground(objParSis.getColorCamposObligatorios());
            txtVerNumPed.setBackground(objParSis.getColorCamposSistema());
            txtVerPed.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtPesTotKgr.setBackground(objParSis.getColorCamposSistema());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);

            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodExp.setVisible(false);
            txtCodExp.setEditable(false);
            
            //Ocultar campos de meses de embarque, puerto y arribo para usuarios distintos a admin.
            txtCodMesEmb.setVisible(false);
            txtCodMesEmb.setEditable(false);
            txtCodMesPue.setVisible(false);
            txtCodMesPue.setEditable(false);                 
            txtCodMesArr.setVisible(false);
            txtCodMesArr.setEditable(false);
       
            if(objParSis.getCodigoUsuario()!=1){
                txtAniEmb.setVisible(false);
                lblMesEmb.setVisible(false);
                txtCodMesEmb.setVisible(false);
                txtNomMesEmb.setVisible(false);
                butMesEmb.setVisible(false);
                lblMesPue.setVisible(false);
                txtCodMesPue.setVisible(false);
                txtNomMesPue.setVisible(false);
                butMesPue.setVisible(false);
                lblMesArr.setVisible(false);
                lblAniEmb.setVisible(false);
                txtCodMesArr.setVisible(false);
                txtNomMesArr.setVisible(false);   
                butMesArr.setVisible(false);
                lblValFac.setVisible(false);
                txtValFac.setVisible(false);
            }
            
            /////////////////////////////////////////////////////////////////////
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaNor.add(objAsiDia,java.awt.BorderLayout.CENTER);

            //Reversión
            objAsiDiaRev=new ZafAsiDia(objParSis);
            objAsiDiaRev.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaRev.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaRev.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaCen.add(objAsiDiaRev,java.awt.BorderLayout.CENTER);
            
            //Nuevo diario al modificar
            objAsiDiaNue=new ZafAsiDia(objParSis);
            objAsiDiaNue.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDiaNue.setCodigoTipoDocumento(-1);
                    else
                        objAsiDiaNue.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });
            panAsiDiaSur.add(objAsiDiaNue,java.awt.BorderLayout.CENTER);
            
            //Configuración de Ventana de Consulta.
            configurarVenConTipDoc();
            configurarVenConImp();
            configurarVenConPrv();
            configurarVenConExp();
            configurarVenConPto();
            configurarVenConSeg();
            configurarVenConPag();
            configurarVenConForPag();
            configurarVenConMesEmb();
            configurarTblForPag();

            //tabla de cargos a pagar
            objImp32_01=new ZafImp32_01(objParSis);
            panCarPagImp.add(objImp32_01,java.awt.BorderLayout.CENTER);

            //tabla de items y sus valores
            objImp32_02=new ZafImp32_02(objParSis, objImp32_01);
            panDetPagImp.add(objImp32_02,java.awt.BorderLayout.CENTER);
            objImp32_01.setObjectoImp32_02(objImp32_02);

            dtpFecDoc.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    //txtDesCorTipDocFocusLost(evt);
                    objImp32_02.setFechaDocumento(dtpFecDoc.getText());
                }
            });           
            
            vecDat=new Vector();    //Almacena los datos

            objImp32_02.addImp32_02Listener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeInsertar");
//                    objImp32_02.regenerarCalculos();
                }
                public void afterInsertar(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("afterInsertar");
                }
                public void beforeEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeEditarCelda");
                }
                public void afterEditarCelda(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    txtValDoc.setText("" + objImp32_02.getValorTotalCosto());
                    txtPesTotKgr.setText("" + objImp32_02.getCalcularPesoTotal()); 
           
                }
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("beforeConsultarCuentas");
                }
                public void afterConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    System.out.println("afterConsultarCuentas");
                }
            });

            configurarTblPedEmb();
            configurarTblDatDocAso();
            configurarTblDetDocAso();
            
            txtNumCorEle.setVisible(false);
            butNumCorEle.setVisible(false);
            if(objParSis.getCodigoUsuario()==1){
                txtNumCorEle.setVisible(true);
                butNumCorEle.setVisible(true);
            }
            bgdSumValTotFobCfrDb=BigDecimal.ZERO;
            
            //txtVerPed.setVisible(false);
            txtVerPed.setEditable(false);
            //txtNumPedOri.setVisible(false);
            txtNumPedOri.setEditable(false);
            
            objTooBar.setVisibleAnular(false);// se inactiva por el momento, hasta implementar el núevo proceso de Reversiones (Asiento de diario que antes no existia aqui)
            
            butCreCta.setVisible(false);
            butCreCta.setEnabled(false);
            butCalISDCarPag.setVisible(false);
            if(objParSis.getCodigoUsuario()==1){
                butCreCta.setVisible(true);
                butCreCta.setEnabled(true);
                butCalISDCarPag.setVisible(true);
            }

        }
        catch(Exception e){
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
    private boolean configurarTblForPag(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCab=new Vector(4);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FOR_PAG_DAT_LIN,"");
            vecCab.add(INT_TBL_FOR_PAG_DAT_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_FOR_PAG_DAT_VAL_PAG,"Val.Pag.");

            objTblModForPag=new ZafTblMod();
            objTblModForPag.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblForPag.setModel(objTblModForPag);
            //Configurar JTable: Establecer tipo de seleccián.
            tblForPag.setRowSelectionAllowed(true);
            tblForPag.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuForPag=new ZafTblPopMnu(tblForPag);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblForPag.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblForPag.getColumnModel();
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_DIA_CRE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_FEC_VEN).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_VAL_PAG).setPreferredWidth(120);

//            Configurar JTable: Ocultar columnas del sistema.
//            objTblModForPag.addSystemHiddenColumn(INT_TBL_FOR_PAG_DAT_COD_CTA_PAG, tblForPag);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_COD_TIP_RET).setResizable(false);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblForPag.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaForPag=new ZafMouMotAdaForPag();
            tblForPag.getTableHeader().addMouseMotionListener(objMouMotAdaForPag);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblForPag);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_FOR_PAG_DAT_VAL_PAG);
            objTblModForPag.setColumnasEditables(vecAux);
            vecAux=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_VAL_PAG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxtValForPag=new ZafTblCelEdiTxt(tblForPag);
            tcmAux.getColumn(INT_TBL_FOR_PAG_DAT_VAL_PAG).setCellEditor(objTblCelEdiTxtValForPag);
            objTblCelEdiTxtValForPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

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
    private class ZafMouMotAdaForPag extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblForPag.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_FOR_PAG_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_FOR_PAG_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_FOR_PAG_DAT_VAL_PAG:
                    strMsg="Valor a pagar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblForPag.getTableHeader().setToolTipText(strMsg);
        }
    } 
    
    private boolean configurarTblPedEmb(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatPedEmb=new Vector();    //Almacena los datos
            vecCabPedEmb=new Vector(13);  //Almacena las cabeceras
            vecCabPedEmb.clear();
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_LIN,"");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_COD_EMP,"Cód.Emp.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_COD_LOC,"Cód.Loc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_COD_DOC,"Cód.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_NUM_DOC,"Núm.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_FEC_DOC,"Fec.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_COD_IMP,"Cód.Imp.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_NOM_IMP,"Importador");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_VAL_DOC,"Val.Doc.");
            vecCabPedEmb.add(INT_TBL_DAT_PED_EMB_BUT,"");

            objTblModPedEmb=new ZafTblMod();
            objTblModPedEmb.setHeader(vecCabPedEmb);
            tblPedEmb.setModel(objTblModPedEmb);

            tblPedEmb.setRowSelectionAllowed(true);
            tblPedEmb.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuPedEmb=new ZafTblPopMnu(tblPedEmb);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabPedEmb=new ZafTblFilCab(tblPedEmb);
            tblPedEmb.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_LIN).setCellRenderer(objTblFilCabPedEmb);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblPedEmb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblPedEmb.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_COD_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_NOM_IMP).setPreferredWidth(130);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_BUT).setPreferredWidth(20);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblPedEmb.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaPedEmb=new ZafMouMotAdaPedEmb();
            tblPedEmb.getTableHeader().addMouseMotionListener(objMouMotAdaPedEmb);
            //Configurar JTable: Editor de búsqueda.
            objTblBusPedEmb=new ZafTblBus(tblPedEmb);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmb.setTipoFormato(objTblCelRenLblPedEmb.INT_FOR_NUM);
            objTblCelRenLblPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PED_EMB_VAL_DOC).setCellRenderer(objTblCelRenLblPedEmb);
            objTblCelRenLblPedEmb=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdPedEmb=new ZafTblOrd(tblPedEmb);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_EMP, tblPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_LOC, tblPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_TIP_DOC, tblPedEmb);
            objTblModPedEmb.addSystemHiddenColumn(INT_TBL_DAT_PED_EMB_COD_DOC, tblPedEmb);

            //Configurar JTable: Establecer columnas editables.
            vecAuxPedEmb=new Vector();
            vecAuxPedEmb.add("" + INT_TBL_DAT_PED_EMB_BUT);
            objTblModPedEmb.setColumnasEditables(vecAuxPedEmb);
            vecAuxPedEmb=null;

            objTblModPedEmb.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);

            objTblCelRenButPedEmb=new ZafTblCelRenBut();
            tblPedEmb.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_BUT).setCellRenderer(objTblCelRenButPedEmb);
            objTblCelEdiButGenPedEmb=new ZafTblCelEdiButGen();
            tblPedEmb.getColumnModel().getColumn(INT_TBL_DAT_PED_EMB_BUT).setCellEditor(objTblCelEdiButGenPedEmb);
            objTblCelEdiButGenPedEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblPedEmb.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFrmPedidoEmbarcado(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
  
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaPedEmb extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblPedEmb.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_PED_EMB_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_TIP_DOC:
                    strMsg="Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_PED_EMB_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_PED_EMB_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_PED_EMB_COD_IMP:
                    strMsg="Código del importador";
                    break;
                case INT_TBL_DAT_PED_EMB_NOM_IMP:
                    strMsg="Nombre del importador";
                    break;
                case INT_TBL_DAT_PED_EMB_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblPedEmb.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean configurarTblDatDocAso(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatCabDocAso=new Vector();    //Almacena los datos
            vecCabCabDocAso=new Vector(19);  //Almacena las cabeceras
            vecCabCabDocAso.clear();
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_LIN,"");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_EMP,"Empresa");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_EMP,"Cód.Emp.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_LOC,"Cód.Loc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_DOC,"Cód.Doc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NUM_DOC,"Núm.Doc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_FEC_DOC,"Fec.Doc.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_CTA,"Cód.Cta.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NUM_CTA,"Cuenta");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_CTA,"Nom.Cta.");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB,"Debe");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB,"Haber");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_BUT,"");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_MNU,"");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_MNU_CLS,"");
            vecCabCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_FEC_ING,"Fec.Ing.");

            objTblModCabDocAso=new ZafTblMod();
            objTblModCabDocAso.setHeader(vecCabCabDocAso);
            tblCabDocAso.setModel(objTblModCabDocAso);

            tblCabDocAso.setRowSelectionAllowed(true);
            tblCabDocAso.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuCabDocAso=new ZafTblPopMnu(tblCabDocAso);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabCabDocAso=new ZafTblFilCab(tblCabDocAso);
            tblCabDocAso.getColumnModel().getColumn(INT_TBL_DAT_CAB_DOC_ASO_LIN).setCellRenderer(objTblFilCabCabDocAso);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCabDocAso.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCabDocAso.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_NOM_EMP).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_NUM_CTA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_NOM_CTA).setPreferredWidth(176);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_BUT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_MNU).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_NOM_MNU_CLS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_FEC_ING).setPreferredWidth(40);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCabDocAso.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCabDocAso=new ZafMouMotAdaCabDocAso();
            tblCabDocAso.getTableHeader().addMouseMotionListener(objMouMotAdaCabDocAso);
            //Configurar JTable: Editor de búsqueda.
            objTblBusCabDocAso=new ZafTblBus(tblCabDocAso);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCabDocAso=new ZafTblCelRenLbl();
            objTblCelRenLblCabDocAso.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCabDocAso.setTipoFormato(objTblCelRenLblCabDocAso.INT_FOR_NUM);
            objTblCelRenLblCabDocAso.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB).setCellRenderer(objTblCelRenLblCabDocAso);
            tcmAux.getColumn(INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB).setCellRenderer(objTblCelRenLblCabDocAso);
            objTblCelRenLblCabDocAso=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdCabDocAso=new ZafTblOrd(tblCabDocAso);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_EMP,       tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_LOC,       tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC,   tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_DOC,       tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_CTA,       tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_COD_MNU,       tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_NOM_MNU_CLS,   tblCabDocAso);
            objTblModCabDocAso.addSystemHiddenColumn(INT_TBL_DAT_CAB_DOC_ASO_FEC_ING,       tblCabDocAso);

            //Configurar JTable: Establecer columnas editables.
            vecAuxCabDocAso=new Vector();
            vecAuxCabDocAso.add("" + INT_TBL_DAT_CAB_DOC_ASO_BUT);
            objTblModCabDocAso.setColumnasEditables(vecAuxCabDocAso);
            vecAuxCabDocAso=null;

            objTblModCabDocAso.setModoOperacion(objTblModCabDocAso.INT_TBL_EDI);

            objTblCelRenButCabDocAso=new ZafTblCelRenBut();
            tblCabDocAso.getColumnModel().getColumn(INT_TBL_DAT_CAB_DOC_ASO_BUT).setCellRenderer(objTblCelRenButCabDocAso);
            objTblCelEdiButGenCabDocAso=new ZafTblCelEdiButGen();
            tblCabDocAso.getColumnModel().getColumn(INT_TBL_DAT_CAB_DOC_ASO_BUT).setCellEditor(objTblCelEdiButGenCabDocAso);
            objTblCelEdiButGenCabDocAso.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblCabDocAso.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFrmDocumentoAsosiadoCabecera(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblCabDocAso.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaCabDocAso extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCabDocAso.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_CAB_DOC_ASO_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;                
                case INT_TBL_DAT_CAB_DOC_ASO_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC:
                    strMsg="Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_COD_CTA:
                    strMsg="Código de la cuenta contable";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_NUM_CTA:
                    strMsg="Número de la cuenta contable";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_NOM_CTA:
                    strMsg="Nombre de la cuenta contable";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB:
                    strMsg="Valor del debe";
                    break;
                case INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB:
                    strMsg="Valor del haber";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblCabDocAso.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean configurarTblDetDocAso(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatDetDocAso=new Vector();    //Almacena los datos
            vecCabDetDocAso=new Vector(10);  //Almacena las cabeceras
            vecCabDetDocAso.clear();
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_LIN,"");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_EMP,"Cód.Emp.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_LOC,"Cód.Loc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_DOC,"Cód.Doc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_NUM_DOC,"Núm.Doc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_FEC_DOC,"Fec.Doc.");
            vecCabDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_BUT,"");

            objTblModDetDocAso=new ZafTblMod();
            objTblModDetDocAso.setHeader(vecCabDetDocAso);
            tblDetDocAso.setModel(objTblModDetDocAso);

            tblDetDocAso.setRowSelectionAllowed(true);
            tblDetDocAso.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuDetDocAso=new ZafTblPopMnu(tblDetDocAso);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilDetDocAso=new ZafTblFilCab(tblDetDocAso);
            tblDetDocAso.getColumnModel().getColumn(INT_TBL_DAT_DET_DOC_ASO_LIN).setCellRenderer(objTblFilDetDocAso);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDetDocAso.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDetDocAso.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_COD_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_NUM_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_BUT).setPreferredWidth(20);


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDetDocAso.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDetDocAso=new ZafMouMotAdaDetDocAso();
            tblDetDocAso.getTableHeader().addMouseMotionListener(objMouMotAdaDetDocAso);
            //Configurar JTable: Editor de búsqueda.
            objTblBusDetDocAso=new ZafTblBus(tblDetDocAso);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblDetDocAso=new ZafTblCelRenLbl();
            objTblCelRenLblDetDocAso.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDetDocAso.setTipoFormato(objTblCelRenLblDetDocAso.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_DET_DOC_ASO_NUM_DOC).setCellRenderer(objTblCelRenLblDetDocAso);
            objTblCelRenLblDetDocAso=null;

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrdDetDocAso=new ZafTblOrd(tblDetDocAso);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDetDocAso.addSystemHiddenColumn(INT_TBL_DAT_DET_DOC_ASO_COD_EMP, tblDetDocAso);
            objTblModDetDocAso.addSystemHiddenColumn(INT_TBL_DAT_DET_DOC_ASO_COD_LOC, tblDetDocAso);
            objTblModDetDocAso.addSystemHiddenColumn(INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC, tblDetDocAso);
            objTblModDetDocAso.addSystemHiddenColumn(INT_TBL_DAT_DET_DOC_ASO_COD_DOC, tblDetDocAso);

            //Configurar JTable: Establecer columnas editables.
            vecAuxDetDocAso=new Vector();
            vecAuxDetDocAso.add("" + INT_TBL_DAT_DET_DOC_ASO_BUT);
            objTblModDetDocAso.setColumnasEditables(vecAuxDetDocAso);
            vecAuxDetDocAso=null;

            objTblModDetDocAso.setModoOperacion(objTblModDetDocAso.INT_TBL_EDI);

            objTblCelRenButDetDocAso=new ZafTblCelRenBut();
            tblDetDocAso.getColumnModel().getColumn(INT_TBL_DAT_DET_DOC_ASO_BUT).setCellRenderer(objTblCelRenButDetDocAso);
            objTblCelEdiButGenDetDocAso=new ZafTblCelEdiButGen();
            tblDetDocAso.getColumnModel().getColumn(INT_TBL_DAT_DET_DOC_ASO_BUT).setCellEditor(objTblCelEdiButGenDetDocAso);
            objTblCelEdiButGenDetDocAso.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDetDocAso.getSelectedRow();
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    mostrarFrmDocumentoAsosiadoDetalle(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaDetDocAso extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDetDocAso.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_DET_DOC_ASO_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC:
                    strMsg="Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_DET_DOC_ASO_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDetDocAso.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);


        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * mostrar las "Importadores".
     */
    private boolean configurarVenConImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_ruc");
            arlCam.add("a2.tx_dir");
            arlCam.add("a2.co_loc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód. Emp.");
            arlAli.add("Nombre");
            arlAli.add("Ruc");
            arlAli.add("Dirección");
            arlAli.add("Local");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("130");
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("80");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
            strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_loc AS a2 ON a1.co_emp=a2.co_emp";
            strSQL+=" WHERE a1.st_reg NOT IN('I','E') AND a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + " AND a2.st_reg='P'";
            strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a1.tx_ruc, a1.tx_dir, a2.co_loc";
            strSQL+=" ORDER BY a1.co_emp";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoImp.setCampoBusqueda(2);
                    vcoImp.show();
                    if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoImp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    else{
                        vcoImp.setCampoBusqueda(0);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPre=vcoImp.getValueAt(5);
                        }
                        else{
                            txtCodImp.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoImp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoImp.getValueAt(1));
                        txtNomImp.setText(vcoImp.getValueAt(2));
                        strCodLocPre=vcoImp.getValueAt(5);
                    }
                    else{
                        vcoImp.setCampoBusqueda(2);
                        vcoImp.setCriterio1(11);
                        vcoImp.cargarDatos();
                        vcoImp.show();
                        if (vcoImp.getSelectedButton()==vcoImp.INT_BUT_ACE){
                            txtCodImp.setText(vcoImp.getValueAt(1));
                            txtNomImp.setText(vcoImp.getValueAt(2));
                            strCodLocPre=vcoImp.getValueAt(5);
                        }
                        else{
                            txtNomImp.setText(strNomPag);
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
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_prv");
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
            strSQL="";
            strSQL+="SELECT a1.co_prv, a1.tx_nom";
            strSQL+=" FROM tbm_prvExtImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtNomPrv.setText(vcoPrv.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_prv", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtNomPrv.setText(vcoPrv.getValueAt(2));
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            txtNomPrv.setText(vcoPrv.getValueAt(2));
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtNomPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtNomPrv.setText(vcoPrv.getValueAt(2));
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            txtNomPrv.setText(vcoPrv.getValueAt(2));
                        }
                        else
                        {
                            txtNomPrv.setText(strNomPrv);
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
     * mostrar los "Proveedores de Exportadores".
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
            arlAli.add("Nombre 2");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("230");
            arlAncCol.add("120");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1"; 
            strSQL+=" WHERE a1.st_reg='A'";
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    public boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{
            
            if(txtCodImp.getText().length()>0){

                strSQLTmp="";
                strSQLTmp+=" ORDER BY a1.tx_nom";
                vcoExp.setCondicionesSQL(strSQLTmp);
                
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoExp.setCampoBusqueda(1);
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                        }
                        break;
                    case 1: //Búsqueda directa por "Código de Exportador".
                        if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
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
                                txtNomExp2.setText(vcoExp.getValueAt(3));
                                objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                            }
                            else
                            {
                                txtCodExp.setText(strCodExp);
                            }
                        }
                        break;
                    case 2: //Búsqueda directa por "Descripción larga".
                        if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                            txtNomExp2.setText(vcoExp.getValueAt(3));
                            objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
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
                                txtNomExp2.setText(vcoExp.getValueAt(3));
                                objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
                            }
                            else
                            {
                                txtNomExp.setText(strNomExp);
                            }
                        }
                        break;
                }
            }
             else{
                mostrarMsgInf("Se debe seleccionar el Importador previamente.");
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
     * mostrar los "Puerto".
     */
    private boolean configurarVenConPto()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_ciupueemb");
            arlCam.add("tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_ciu AS co_ciupueemb, a1.tx_desLar";
//            strSQL+=" FROM tbm_ciu AS a1 /*LEFT OUTER JOIN*/ INNER JOIN tbm_expImp AS a2";
//            strSQL+=" ON a1.co_ciu=a2.co_ciu AND a2.st_reg='A'";
//            strSQL+=" WHERE a1.st_reg='A' ";
//            strSQL+=" GROUP BY a1.co_ciu, a1.tx_desLar";
//            strSQL+=" ORDER BY a1.tx_desLar";


            strSQL="";
            strSQL+=" SELECT a1.co_ciu AS co_ciupueemb, a1.tx_desLar";
            strSQL+=" FROM tbm_ciu AS a1";
            strSQL+="  WHERE a1.st_reg='A' AND a1.co_pai NOT IN(6)";//No carga ninguna ciudad de Ecuador
            strSQL+=" GROUP BY a1.co_ciu, a1.tx_desLar, a1.co_pai";
            strSQL+=" ORDER BY a1.tx_desLar";

            vcoPto=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de puertos de embarque", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPto.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConPto(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPto.setCampoBusqueda(2);
                    vcoPto.show();
                    if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoPto.buscar("a1.co_ciupueemb", txtCodPue.getText())){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(0);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPue.setText(vcoPto.getValueAt(1));
                            txtNomPue.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtCodPue.setText(strCodPto);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPto.buscar("a1.tx_desLar", txtNomPue.getText())){
                        txtCodPue.setText(vcoPto.getValueAt(1));
                        txtNomPue.setText(vcoPto.getValueAt(2));
                    }
                    else{
                        vcoPto.setCampoBusqueda(2);
                        vcoPto.setCriterio1(11);
                        vcoPto.cargarDatos();
                        vcoPto.show();
                        if (vcoPto.getSelectedButton()==vcoPto.INT_BUT_ACE){
                            txtCodPue.setText(vcoPto.getValueAt(1));
                            txtNomPue.setText(vcoPto.getValueAt(2));
                        }
                        else{
                            txtNomPue.setText(strNomPto);
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
     * mostrar los "Seguro".
     */
    private boolean configurarVenConSeg()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_seg");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_seg, a1.tx_desLar";
            strSQL+=" FROM tbm_segImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoSeg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de seguros", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoSeg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConSeg(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoSeg.setCampoBusqueda(2);
                    vcoSeg.show();
                    if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoSeg.buscar("a1.co_seg", txtCodSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(0);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtCodSeg.setText(strCodSeg);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoSeg.buscar("a1.tx_desLar", txtNomSeg.getText())){
                        txtCodSeg.setText(vcoSeg.getValueAt(1));
                        txtNomSeg.setText(vcoSeg.getValueAt(2));
                    }
                    else{
                        vcoSeg.setCampoBusqueda(2);
                        vcoSeg.setCriterio1(11);
                        vcoSeg.cargarDatos();
                        vcoSeg.show();
                        if (vcoSeg.getSelectedButton()==vcoSeg.INT_BUT_ACE){
                            txtCodSeg.setText(vcoSeg.getValueAt(1));
                            txtNomSeg.setText(vcoSeg.getValueAt(2));
                        }
                        else{
                            txtNomSeg.setText(strNomSeg);
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
     * mostrar los "Pagos".
     */
    private boolean configurarVenConPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forPag, a1.tx_desLar";
            strSQL+=" FROM tbm_forPagImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_desLar";
            vcoPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConPag(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoPag.setCampoBusqueda(2);
                    vcoPag.show();
                    if (vcoPag.getSelectedButton()==vcoPag.INT_BUT_ACE){
                        txtCodPag.setText(vcoPag.getValueAt(1));
                        txtNomPag.setText(vcoPag.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoPag.buscar("a1.co_forPag", txtCodForPag.getText())){
                        txtCodPag.setText(vcoPag.getValueAt(1));
                        txtNomPag.setText(vcoPag.getValueAt(2));
                    }
                    else{
                        vcoPag.setCampoBusqueda(0);
                        vcoPag.setCriterio1(11);
                        vcoPag.cargarDatos();
                        vcoPag.show();
                        if (vcoPag.getSelectedButton()==vcoPag.INT_BUT_ACE){
                            txtCodPag.setText(vcoPag.getValueAt(1));
                            txtNomPag.setText(vcoPag.getValueAt(2));
                        }
                        else{
                            txtCodPag.setText(strCodPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoPag.buscar("a1.tx_desLar", txtNomPag.getText())){
                        txtCodPag.setText(vcoPag.getValueAt(1));
                        txtNomPag.setText(vcoPag.getValueAt(2));
                    }
                    else{
                        vcoPag.setCampoBusqueda(2);
                        vcoPag.setCriterio1(11);
                        vcoPag.cargarDatos();
                        vcoPag.show();
                        if (vcoPag.getSelectedButton()==vcoPag.INT_BUT_ACE){
                            txtCodPag.setText(vcoPag.getValueAt(1));
                            txtNomPag.setText(vcoPag.getValueAt(2));
                        }
                        else{
                            txtNomPag.setText(strNomPag);
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
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a7.co_forPag");
            arlCam.add("a8.tx_desForPag");
            arlCam.add("a8.ne_numPag");
            arlCam.add("a8.ne_tipForPag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            arlAli.add("Número de pagos");
            arlAli.add("Tipo de pago");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="select a8.co_forPag, a8.tx_des AS tx_desForPag";
            strSQL+=" , a8.ne_numPag, a8.ne_tipForPag";
            strSQL+=" FROM tbm_cabForPag AS a8";
            strSQL+=" WHERE a8.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a8.st_reg NOT IN('E','I') ";
            strSQL+=" GROUP BY a8.co_forPag, a8.tx_des, a8.ne_numPag, a8.ne_tipForPag";
            strSQL+=" ORDER BY a8.tx_des";

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;

            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de forma de pago", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.

                    vcoForPag.setCampoBusqueda(0);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        cargarFormaPago();
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        cargarFormaPago();                        
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            cargarFormaPago();
                        }
                        else
                            txtCodForPag.setText(strCodForPag);
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForPag.buscar("a1.tx_desForPag", txtNomForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                        txtNumForPag.setText(vcoForPag.getValueAt(3));
                        txtTipForPag.setText(vcoForPag.getValueAt(4));
                        objTblModForPag.removeAllRows();
                        cargarFormaPago();
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                            txtNumForPag.setText(vcoForPag.getValueAt(3));
                            txtTipForPag.setText(vcoForPag.getValueAt(4));
                            objTblModForPag.removeAllRows();
                            cargarFormaPago();
                        }
                        else
                        {
                            txtNomForPag.setText(strNomForPag);
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
     * mostrar los "Meses de Embarque".
     */
    private boolean configurarVenConMesEmb()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_mesemb");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_mesemb, a1.tx_desLar";
            strSQL+=" FROM tbm_mesembimp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_mesemb";
            vcoMeses=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de mes de embarque", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMeses.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
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
    private boolean mostrarVenConMesEmb(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.show();
                    if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                        txtCodMesEmb.setText(vcoMeses.getValueAt(1));
                        txtNomMesEmb.setText(vcoMeses.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código de mes de embarque".
                    if (vcoMeses.buscar("a1.co_mesemb", txtCodMesEmb.getText())){
                        txtCodMesEmb.setText(vcoMeses.getValueAt(1));
                        txtNomMesEmb.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(0);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesEmb.setText(vcoMeses.getValueAt(1));
                            txtNomMesEmb.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtCodMesEmb.setText(strCodMesEmb);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMeses.buscar("a1.tx_desLar", txtNomMesEmb.getText())){
                        txtCodMesEmb.setText(vcoMeses.getValueAt(1));
                        txtNomMesEmb.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(1);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesEmb.setText(vcoMeses.getValueAt(1));
                            txtNomMesEmb.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtNomMesEmb.setText(strNomMesEmb);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConMesAniEmb(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoAniEmb.setCampoBusqueda(1);
                    vcoAniEmb.show();
                    if (vcoAniEmb.getSelectedButton()==vcoAniEmb.INT_BUT_ACE){
                        txtAniEmb.setText(vcoAniEmb.getValueAt(1));
                        txtAniEmb.setText(vcoAniEmb.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código de año de embarque".
                    if (vcoAniEmb.buscar("a1.co_aniemb", txtAniEmb.getText())){
                        txtAniEmb.setText(vcoAniEmb.getValueAt(1));
                        txtAniEmb.setText(vcoAniEmb.getValueAt(2));
                    }
                    else{
                        vcoAniEmb.setCampoBusqueda(0);
                        vcoAniEmb.setCriterio1(11);
                        vcoAniEmb.cargarDatos();
                        vcoAniEmb.show();
                        if (vcoAniEmb.getSelectedButton()==vcoAniEmb.INT_BUT_ACE){
                            txtAniEmb.setText(vcoAniEmb.getValueAt(1));
                            txtAniEmb.setText(vcoAniEmb.getValueAt(2));
                        }
                        else{
                            txtAniEmb.setText(strCodAniEmb);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoAniEmb.buscar("a1.tx_desLar", txtAniEmb.getText())){
                        txtAniEmb.setText(vcoAniEmb.getValueAt(1));
                        txtAniEmb.setText(vcoAniEmb.getValueAt(2));
                    }
                    else{
                        vcoAniEmb.setCampoBusqueda(1);
                        vcoAniEmb.setCriterio1(11);
                        vcoAniEmb.cargarDatos();
                        vcoAniEmb.show();
                        if (vcoAniEmb.getSelectedButton()==vcoAniEmb.INT_BUT_ACE){
                            txtAniEmb.setText(vcoAniEmb.getValueAt(1));
                            txtAniEmb.setText(vcoAniEmb.getValueAt(2));
                        }
                        else{
                            txtAniEmb.setText(strNomAniEmb);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConMesArr(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.show();
                    if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                        txtCodMesArr.setText(vcoMeses.getValueAt(1));
                        txtNomMesArr.setText(vcoMeses.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código de Mes de Arribo".
                    if (vcoMeses.buscar("a1.co_mesemb", txtCodMesArr.getText())){
                        txtCodMesArr.setText(vcoMeses.getValueAt(1));
                        txtNomMesArr.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(0);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesArr.setText(vcoMeses.getValueAt(1));
                            txtNomMesArr.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtCodMesArr.setText(strCodMesArr);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMeses.buscar("a1.tx_desLar", txtNomMesArr.getText())){
                        txtCodMesArr.setText(vcoMeses.getValueAt(1));
                        txtNomMesArr.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(1);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesArr.setText(vcoMeses.getValueAt(1));
                            txtNomMesArr.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtNomMesArr.setText(strNomMesArr);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConMesPue(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoMeses.setCampoBusqueda(1);
                    vcoMeses.show();
                    if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                        txtCodMesPue.setText(vcoMeses.getValueAt(1));
                        txtNomMesPue.setText(vcoMeses.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Námero de cuenta".
                    if (vcoMeses.buscar("a1.co_mesemb", txtCodMesPue.getText())){
                        txtCodMesPue.setText(vcoMeses.getValueAt(1));
                        txtNomMesPue.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(0);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesPue.setText(vcoMeses.getValueAt(1));
                            txtNomMesPue.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtCodMesPue.setText(strCodMesEmb);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMeses.buscar("a1.tx_desLar", txtNomMesPue.getText())){
                        txtCodMesPue.setText(vcoMeses.getValueAt(1));
                        txtNomMesPue.setText(vcoMeses.getValueAt(2));
                    }
                    else{
                        vcoMeses.setCampoBusqueda(1);
                        vcoMeses.setCriterio1(11);
                        vcoMeses.cargarDatos();
                        vcoMeses.show();
                        if (vcoMeses.getSelectedButton()==vcoMeses.INT_BUT_ACE){
                            txtCodMesPue.setText(vcoMeses.getValueAt(1));
                            txtNomMesPue.setText(vcoMeses.getValueAt(2));
                        }
                        else{
                            txtNomMesPue.setText(strNomMesEmb);
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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtNomPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp2 = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblEmpExp = new javax.swing.JLabel();
        txtEmpExp = new javax.swing.JTextField();
        lblPue = new javax.swing.JLabel();
        txtCodPue = new javax.swing.JTextField();
        txtNomPue = new javax.swing.JTextField();
        butPue = new javax.swing.JButton();
        lblSeg = new javax.swing.JLabel();
        txtCodSeg = new javax.swing.JTextField();
        txtNomSeg = new javax.swing.JTextField();
        butSeg = new javax.swing.JButton();
        lblPag = new javax.swing.JLabel();
        txtCodPag = new javax.swing.JTextField();
        txtNomPag = new javax.swing.JTextField();
        butPag = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblMesEmb = new javax.swing.JLabel();
        txtCodMesEmb = new javax.swing.JTextField();
        txtNomMesEmb = new javax.swing.JTextField();
        butMesEmb = new javax.swing.JButton();
        lblAniEmb = new javax.swing.JLabel();
        txtAniEmb = new javax.swing.JTextField();
        lblMesArr = new javax.swing.JLabel();
        txtCodMesArr = new javax.swing.JTextField();
        txtNomMesArr = new javax.swing.JTextField();
        butMesArr = new javax.swing.JButton();
        lblMesPue = new javax.swing.JLabel();
        txtCodMesPue = new javax.swing.JTextField();
        txtNomMesPue = new javax.swing.JTextField();
        butMesPue = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblVerNumPed = new javax.swing.JLabel();
        txtVerNumPed = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        lblPesTotKgr = new javax.swing.JLabel();
        txtPesTotKgr = new javax.swing.JTextField();
        optTmFob = new javax.swing.JRadioButton();
        optTmCfr = new javax.swing.JRadioButton();
        optPzaFob = new javax.swing.JRadioButton();
        optPzaCfr = new javax.swing.JRadioButton();
        chkCreCta = new javax.swing.JCheckBox();
        chkNotPedLis = new javax.swing.JCheckBox();
        chkModSolFecEmbArr = new javax.swing.JCheckBox();
        butPedPre = new javax.swing.JButton();
        txtNumCorEle = new javax.swing.JTextField();
        butNumCorEle = new javax.swing.JButton();
        txtVerPed = new javax.swing.JTextField();
        txtNumPedOri = new javax.swing.JTextField();
        butCreCta = new javax.swing.JButton();
        lblFecColPed = new javax.swing.JLabel();
        lblNumPedOri = new javax.swing.JLabel();
        chkMosTodVerPed = new javax.swing.JCheckBox();
        lblFecEmb = new javax.swing.JLabel();
        lblFecPue = new javax.swing.JLabel();
        lblFecArr = new javax.swing.JLabel();
        butCalISDCarPag = new javax.swing.JButton();
        lblValFac = new javax.swing.JLabel();
        txtValFac = new javax.swing.JTextField();
        lblFecPro = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtModPed = new javax.swing.JTextField();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JSplitPane();
        panCarPagImp = new javax.swing.JPanel();
        panDetPagImp = new javax.swing.JPanel();
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
        panAsiDiaNor = new javax.swing.JPanel();
        panAsiDiaCen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        panAsiDiaSur = new javax.swing.JPanel();
        panForPag = new java.awt.Panel();
        panCabForPag = new javax.swing.JPanel();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        txtNumForPag = new javax.swing.JTextField();
        txtTipForPag = new javax.swing.JTextField();
        panDatForPag = new javax.swing.JPanel();
        spnForPag = new javax.swing.JScrollPane();
        tblForPag = new javax.swing.JTable();
        panPedEmb = new javax.swing.JPanel();
        spnPedEmb = new javax.swing.JScrollPane();
        tblPedEmb = new javax.swing.JTable();
        panDocAso = new javax.swing.JPanel();
        sppDocAso = new javax.swing.JSplitPane();
        panCabDocAso = new javax.swing.JPanel();
        spnCabDocAso = new javax.swing.JScrollPane();
        tblCabDocAso = new javax.swing.JTable();
        panDetDocAso = new javax.swing.JPanel();
        chkMosMovReg = new javax.swing.JCheckBox();
        spnDetDocAso = new javax.swing.JScrollPane();
        tblDetDocAso = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

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
                formInternalFrameOpened(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setAutoscrolls(true);

        panGen.setAutoscrolls(true);
        panGen.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 268));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 122, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(70, 4, 32, 20);

        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(102, 4, 78, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(180, 4, 258, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(438, 4, 20, 20);

        lblImp.setText("Importador:");
        lblImp.setToolTipText("Cuenta");
        panCab.add(lblImp);
        lblImp.setBounds(4, 24, 90, 20);

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
        panCab.add(txtCodImp);
        txtCodImp.setBounds(102, 24, 78, 20);

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
        panCab.add(txtNomImp);
        txtNomImp.setBounds(180, 24, 258, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panCab.add(butImp);
        butImp.setBounds(438, 24, 20, 20);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panCab.add(lblPrv);
        lblPrv.setBounds(4, 44, 100, 20);

        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        panCab.add(txtCodPrv);
        txtCodPrv.setBounds(102, 44, 78, 20);

        txtNomPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrvFocusLost(evt);
            }
        });
        txtNomPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrvActionPerformed(evt);
            }
        });
        panCab.add(txtNomPrv);
        txtNomPrv.setBounds(180, 44, 258, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panCab.add(butPrv);
        butPrv.setBounds(438, 44, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Proveedor");
        panCab.add(lblExp);
        lblExp.setBounds(4, 64, 70, 20);

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
        panCab.add(txtCodExp);
        txtCodExp.setBounds(80, 64, 20, 20);

        txtNomExp2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExp2FocusLost(evt);
            }
        });
        txtNomExp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExp2ActionPerformed(evt);
            }
        });
        panCab.add(txtNomExp2);
        txtNomExp2.setBounds(102, 64, 78, 20);

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
        panCab.add(txtNomExp);
        txtNomExp.setBounds(180, 64, 258, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panCab.add(butExp);
        butExp.setBounds(438, 64, 20, 20);

        lblEmpExp.setText("Empaque:");
        lblEmpExp.setToolTipText("Código del documento");
        panCab.add(lblEmpExp);
        lblEmpExp.setBounds(4, 84, 110, 20);
        panCab.add(txtEmpExp);
        txtEmpExp.setBounds(102, 84, 336, 20);

        lblPue.setText("Puerto:");
        lblPue.setToolTipText("Proveedor");
        panCab.add(lblPue);
        lblPue.setBounds(4, 104, 100, 20);

        txtCodPue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPueFocusLost(evt);
            }
        });
        txtCodPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPueActionPerformed(evt);
            }
        });
        panCab.add(txtCodPue);
        txtCodPue.setBounds(102, 104, 78, 20);

        txtNomPue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPueFocusLost(evt);
            }
        });
        txtNomPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPueActionPerformed(evt);
            }
        });
        panCab.add(txtNomPue);
        txtNomPue.setBounds(180, 104, 258, 20);

        butPue.setText("...");
        butPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPueActionPerformed(evt);
            }
        });
        panCab.add(butPue);
        butPue.setBounds(438, 104, 20, 20);

        lblSeg.setText("Seguro:");
        lblSeg.setToolTipText("Proveedor");
        panCab.add(lblSeg);
        lblSeg.setBounds(4, 124, 100, 20);

        txtCodSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSegFocusLost(evt);
            }
        });
        txtCodSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSegActionPerformed(evt);
            }
        });
        panCab.add(txtCodSeg);
        txtCodSeg.setBounds(102, 124, 78, 20);

        txtNomSeg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomSegFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomSegFocusLost(evt);
            }
        });
        txtNomSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomSegActionPerformed(evt);
            }
        });
        panCab.add(txtNomSeg);
        txtNomSeg.setBounds(180, 124, 258, 20);

        butSeg.setText("...");
        butSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSegActionPerformed(evt);
            }
        });
        panCab.add(butSeg);
        butSeg.setBounds(438, 124, 20, 20);

        lblPag.setText("Pago:");
        lblPag.setToolTipText("Proveedor");
        panCab.add(lblPag);
        lblPag.setBounds(4, 144, 100, 20);

        txtCodPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPagFocusLost(evt);
            }
        });
        txtCodPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPagActionPerformed(evt);
            }
        });
        panCab.add(txtCodPag);
        txtCodPag.setBounds(102, 144, 78, 20);

        txtNomPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPagFocusLost(evt);
            }
        });
        txtNomPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPagActionPerformed(evt);
            }
        });
        panCab.add(txtNomPag);
        txtNomPag.setBounds(180, 144, 258, 20);

        butPag.setText("...");
        butPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPagActionPerformed(evt);
            }
        });
        panCab.add(butPag);
        butPag.setBounds(438, 144, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(460, 4, 116, 20);

        lblMesEmb.setText("Mes de embarque:");
        lblMesEmb.setToolTipText("Número alterno 1");
        panCab.add(lblMesEmb);
        lblMesEmb.setBounds(10, 240, 100, 10);

        txtCodMesEmb.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMesEmb.setToolTipText("Número de egreso");
        panCab.add(txtCodMesEmb);
        txtCodMesEmb.setBounds(120, 240, 20, 10);

        txtNomMesEmb.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNomMesEmb.setToolTipText("Número de egreso");
        txtNomMesEmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMesEmbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMesEmbFocusLost(evt);
            }
        });
        txtNomMesEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMesEmbActionPerformed(evt);
            }
        });
        panCab.add(txtNomMesEmb);
        txtNomMesEmb.setBounds(140, 240, 82, 10);

        butMesEmb.setText("...");
        butMesEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMesEmbActionPerformed(evt);
            }
        });
        panCab.add(butMesEmb);
        butMesEmb.setBounds(220, 240, 20, 10);

        lblAniEmb.setText("Año de embarque:");
        lblAniEmb.setToolTipText("Número alterno 1");
        panCab.add(lblAniEmb);
        lblAniEmb.setBounds(250, 240, 110, 10);

        txtAniEmb.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAniEmb.setToolTipText("Número de egreso");
        panCab.add(txtAniEmb);
        txtAniEmb.setBounds(340, 240, 100, 10);

        lblMesArr.setText("Mes de arribo:");
        lblMesArr.setToolTipText("Fecha del documento");
        panCab.add(lblMesArr);
        lblMesArr.setBounds(250, 250, 90, 10);

        txtCodMesArr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMesArr.setToolTipText("Número de egreso");
        panCab.add(txtCodMesArr);
        txtCodMesArr.setBounds(330, 250, 20, 10);

        txtNomMesArr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNomMesArr.setToolTipText("Número de egreso");
        txtNomMesArr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMesArrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMesArrFocusLost(evt);
            }
        });
        txtNomMesArr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMesArrActionPerformed(evt);
            }
        });
        panCab.add(txtNomMesArr);
        txtNomMesArr.setBounds(350, 250, 82, 10);

        butMesArr.setText("...");
        butMesArr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMesArrActionPerformed(evt);
            }
        });
        panCab.add(butMesArr);
        butMesArr.setBounds(430, 250, 20, 10);

        lblMesPue.setText("Mes de puerto:");
        lblMesPue.setToolTipText("Fecha del documento");
        panCab.add(lblMesPue);
        lblMesPue.setBounds(10, 250, 90, 10);

        txtCodMesPue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMesPue.setToolTipText("Número de egreso");
        panCab.add(txtCodMesPue);
        txtCodMesPue.setBounds(120, 250, 20, 10);

        txtNomMesPue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNomMesPue.setToolTipText("Número de egreso");
        txtNomMesPue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomMesPueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomMesPueFocusLost(evt);
            }
        });
        txtNomMesPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomMesPueActionPerformed(evt);
            }
        });
        panCab.add(txtNomMesPue);
        txtNomMesPue.setBounds(140, 250, 82, 10);

        butMesPue.setText("...");
        butMesPue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMesPueActionPerformed(evt);
            }
        });
        panCab.add(butMesPue);
        butMesPue.setBounds(220, 250, 20, 10);

        lblNumDoc.setText("Número de documento:");
        lblNumDoc.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(460, 124, 120, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(582, 124, 102, 20);

        lblVerNumPed.setText("Versión número pedido:");
        lblVerNumPed.setToolTipText("Versión del número del pedido");
        panCab.add(lblVerNumPed);
        lblVerNumPed.setBounds(460, 164, 130, 20);

        txtVerNumPed.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtVerNumPed.setToolTipText("Version del número del pedido");
        txtVerNumPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtVerNumPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVerNumPedFocusLost(evt);
            }
        });
        panCab.add(txtVerNumPed);
        txtVerNumPed.setBounds(582, 164, 77, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(460, 184, 120, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(582, 184, 102, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panCab.add(lblMonDoc);
        lblMonDoc.setBounds(460, 204, 122, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValDocFocusLost(evt);
            }
        });
        txtValDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValDocActionPerformed(evt);
            }
        });
        panCab.add(txtValDoc);
        txtValDoc.setBounds(582, 204, 102, 20);

        lblPesTotKgr.setText("Peso(kg):");
        panCab.add(lblPesTotKgr);
        lblPesTotKgr.setBounds(460, 224, 100, 20);

        txtPesTotKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPesTotKgr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesTotKgrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesTotKgrFocusLost(evt);
            }
        });
        txtPesTotKgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesTotKgrActionPerformed(evt);
            }
        });
        panCab.add(txtPesTotKgr);
        txtPesTotKgr.setBounds(582, 224, 102, 20);

        optTmFob.setText("TM FOB");
        optTmFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmFobActionPerformed(evt);
            }
        });
        panCab.add(optTmFob);
        optTmFob.setBounds(180, 170, 70, 14);

        optTmCfr.setText("TM CFR");
        optTmCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTmCfrActionPerformed(evt);
            }
        });
        panCab.add(optTmCfr);
        optTmCfr.setBounds(250, 170, 70, 14);

        optPzaFob.setText("PZA FOB");
        optPzaFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaFobActionPerformed(evt);
            }
        });
        panCab.add(optPzaFob);
        optPzaFob.setBounds(320, 170, 70, 14);

        optPzaCfr.setText("PZA CFR");
        optPzaCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPzaCfrActionPerformed(evt);
            }
        });
        panCab.add(optPzaCfr);
        optPzaCfr.setBounds(390, 170, 70, 14);

        chkCreCta.setText("Crear cuentas");
        chkCreCta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        panCab.add(chkCreCta);
        chkCreCta.setBounds(250, 204, 120, 14);

        chkNotPedLis.setText("Nota Pedido lista");
        panCab.add(chkNotPedLis);
        chkNotPedLis.setBounds(0, 190, 120, 14);

        chkModSolFecEmbArr.setText("Modificar sólo fecha embarque y fecha de arribo");
        chkModSolFecEmbArr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkModSolFecEmbArrActionPerformed(evt);
            }
        });
        panCab.add(chkModSolFecEmbArr);
        chkModSolFecEmbArr.setBounds(120, 190, 340, 14);

        butPedPre.setText("Pedido previo");
        butPedPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedPreActionPerformed(evt);
            }
        });
        panCab.add(butPedPre);
        butPedPre.setBounds(560, 60, 120, 20);
        panCab.add(txtNumCorEle);
        txtNumCorEle.setBounds(410, 220, 40, 18);

        butNumCorEle.setText("CorEle");
        butNumCorEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNumCorEleActionPerformed(evt);
            }
        });
        panCab.add(butNumCorEle);
        butNumCorEle.setBounds(320, 220, 80, 20);
        panCab.add(txtVerPed);
        txtVerPed.setBounds(659, 164, 25, 20);

        txtNumPedOri.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtNumPedOri);
        txtNumPedOri.setBounds(582, 144, 102, 20);

        butCreCta.setText("CrearCuentas");
        butCreCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCreCtaActionPerformed(evt);
            }
        });
        panCab.add(butCreCta);
        butCreCta.setBounds(190, 220, 120, 20);

        lblFecColPed.setText("Fecha colocación pedido:");
        lblFecColPed.setToolTipText("Fecha del documento");
        panCab.add(lblFecColPed);
        lblFecColPed.setBounds(458, 24, 136, 20);

        lblNumPedOri.setText("Número de pedido:");
        lblNumPedOri.setToolTipText("Número del pedido original");
        panCab.add(lblNumPedOri);
        lblNumPedOri.setBounds(460, 144, 120, 20);

        chkMosTodVerPed.setText("Mostrar \"Todas las versiones del pedido\".");
        panCab.add(chkMosTodVerPed);
        chkMosTodVerPed.setBounds(0, 206, 240, 14);

        lblFecEmb.setText("Fecha de embarque:");
        lblFecEmb.setToolTipText("Número alterno 1");
        panCab.add(lblFecEmb);
        lblFecEmb.setBounds(460, 64, 130, 20);

        lblFecPue.setText("Fecha de puerto:");
        lblFecPue.setToolTipText("Número alterno 1");
        panCab.add(lblFecPue);
        lblFecPue.setBounds(460, 84, 130, 20);

        lblFecArr.setText("Fecha de arribo:");
        lblFecArr.setToolTipText("Número alterno 1");
        panCab.add(lblFecArr);
        lblFecArr.setBounds(460, 104, 130, 20);

        butCalISDCarPag.setText("Calcular ISD en Cargos");
        butCalISDCarPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCalISDCarPagActionPerformed(evt);
            }
        });
        panCab.add(butCalISDCarPag);
        butCalISDCarPag.setBounds(30, 220, 150, 20);

        lblValFac.setText("Valor de la factura:");
        panCab.add(lblValFac);
        lblValFac.setBounds(460, 244, 110, 20);
        panCab.add(txtValFac);
        txtValFac.setBounds(582, 244, 102, 20);

        lblFecPro.setText("Fecha de producción:");
        lblFecPro.setToolTipText("Número alterno 1");
        panCab.add(lblFecPro);
        lblFecPro.setBounds(460, 44, 130, 20);

        jLabel1.setText("Modalidad:");
        panCab.add(jLabel1);
        jLabel1.setBounds(4, 166, 90, 14);
        panCab.add(txtModPed);
        txtModPed.setBounds(102, 164, 78, 20);

        panGen.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setAutoscrolls(true);
        panDet.setLayout(new java.awt.BorderLayout());

        spnDet.setDividerLocation(258);
        spnDet.setOneTouchExpandable(true);

        panCarPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setLeftComponent(panCarPagImp);

        panDetPagImp.setLayout(new java.awt.BorderLayout());
        spnDet.setRightComponent(panDetPagImp);

        panDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(100, 62));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación 2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panDet.add(panObs, java.awt.BorderLayout.SOUTH);

        panGen.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());

        panAsiDiaNor.setPreferredSize(new java.awt.Dimension(100, 120));
        panAsiDiaNor.setLayout(new java.awt.BorderLayout());
        panAsiDia.add(panAsiDiaNor, java.awt.BorderLayout.NORTH);

        panAsiDiaCen.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        panAsiDiaCen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panAsiDia.add(panAsiDiaCen, java.awt.BorderLayout.CENTER);

        panAsiDiaSur.setPreferredSize(new java.awt.Dimension(100, 120));
        panAsiDiaSur.setLayout(new java.awt.BorderLayout());
        panAsiDia.add(panAsiDiaSur, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Asiento de diario", panAsiDia);

        panForPag.setBackground(new java.awt.Color(240, 240, 240));
        panForPag.setLayout(new java.awt.BorderLayout());

        panCabForPag.setPreferredSize(new java.awt.Dimension(1, 30));
        panCabForPag.setLayout(null);

        lblForPag.setText("Forma de pago:");
        panCabForPag.add(lblForPag);
        lblForPag.setBounds(10, 8, 90, 16);

        txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForPagActionPerformed(evt);
            }
        });
        txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusLost(evt);
            }
        });
        panCabForPag.add(txtCodForPag);
        txtCodForPag.setBounds(110, 6, 60, 20);

        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        panCabForPag.add(txtNomForPag);
        txtNomForPag.setBounds(170, 6, 300, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panCabForPag.add(butForPag);
        butForPag.setBounds(470, 6, 20, 20);
        panCabForPag.add(txtNumForPag);
        txtNumForPag.setBounds(500, 6, 30, 20);
        panCabForPag.add(txtTipForPag);
        txtTipForPag.setBounds(540, 6, 30, 20);

        panForPag.add(panCabForPag, java.awt.BorderLayout.NORTH);

        panDatForPag.setLayout(new java.awt.BorderLayout());

        tblForPag.setModel(new javax.swing.table.DefaultTableModel(
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
        spnForPag.setViewportView(tblForPag);

        panDatForPag.add(spnForPag, java.awt.BorderLayout.CENTER);

        panForPag.add(panDatForPag, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Forma de pago", panForPag);

        panPedEmb.setLayout(new java.awt.BorderLayout());

        tblPedEmb.setModel(new javax.swing.table.DefaultTableModel(
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
        spnPedEmb.setViewportView(tblPedEmb);

        panPedEmb.add(spnPedEmb, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Pedidos embarcados", panPedEmb);

        panDocAso.setLayout(new java.awt.BorderLayout());

        sppDocAso.setDividerLocation(250);
        sppDocAso.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppDocAso.setOneTouchExpandable(true);

        panCabDocAso.setLayout(new java.awt.BorderLayout());

        tblCabDocAso.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCabDocAso.setViewportView(tblCabDocAso);

        panCabDocAso.add(spnCabDocAso, java.awt.BorderLayout.CENTER);

        sppDocAso.setLeftComponent(panCabDocAso);

        panDetDocAso.setLayout(new java.awt.BorderLayout());

        chkMosMovReg.setText("Mostrar el movimiento del documento seleccionado");
        chkMosMovReg.setPreferredSize(new java.awt.Dimension(267, 18));
        chkMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMovRegActionPerformed(evt);
            }
        });
        panDetDocAso.add(chkMosMovReg, java.awt.BorderLayout.PAGE_START);

        tblDetDocAso.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDetDocAso.setViewportView(tblDetDocAso);

        panDetDocAso.add(spnDetDocAso, java.awt.BorderLayout.CENTER);

        sppDocAso.setRightComponent(panDetDocAso);

        panDocAso.add(sppDocAso, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Documentos asociados", panDocAso);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    //TODO add your handling code here:

}//GEN-LAST:event_formInternalFrameOpened

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
}//GEN-LAST:event_exitForm

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    strDesCorTipDoc=txtDesCorTipDoc.getText();
    txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
        if (txtDesCorTipDoc.getText().equals("")) {
            txtCodTipDoc.setText("");
            txtDesLarTipDoc.setText("");
        } else {
            mostrarVenConTipDoc(1);
        }
    } else
        txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
    txtCodImp.transferFocus();
}//GEN-LAST:event_txtCodImpActionPerformed

private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
    strCodImp=txtCodImp.getText();
    txtCodImp.selectAll();
}//GEN-LAST:event_txtCodImpFocusGained

private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodImp.getText().equalsIgnoreCase(strCodImp)) {
        if (txtCodImp.getText().equals("")) {
            txtNomImp.setText("");
        } else {
            mostrarVenConImp(1);

        }
    } else
        txtCodImp.setText(strCodImp);
}//GEN-LAST:event_txtCodImpFocusLost

private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
    txtNomImp.transferFocus();
}//GEN-LAST:event_txtNomImpActionPerformed

private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
    strNomImp=txtNomImp.getText();
    txtNomImp.selectAll();
}//GEN-LAST:event_txtNomImpFocusGained

private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomImp.getText().equalsIgnoreCase(strNomImp)) {
        if (txtNomImp.getText().equals("")) {
            txtCodImp.setText("");
        } else {
            mostrarVenConImp(2);
        }
    } else
        txtNomImp.setText(strNomImp);
}//GEN-LAST:event_txtNomImpFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
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

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
    mostrarVenConImp(0);
}//GEN-LAST:event_butImpActionPerformed

private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
    strNumDoc=txtNumDoc.getText();
    txtNumDoc.selectAll();
}//GEN-LAST:event_txtNumDocFocusGained

private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) {
        //actualizarGlo();
    }
}//GEN-LAST:event_txtNumDocFocusLost

private void txtValDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValDocActionPerformed
    txtValDoc.transferFocus();
}//GEN-LAST:event_txtValDocActionPerformed

private void txtValDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusGained
    strMonDoc=txtValDoc.getText();
    txtValDoc.selectAll();
}//GEN-LAST:event_txtValDocFocusGained

private void txtValDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValDocFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtValDoc.getText().equalsIgnoreCase(strMonDoc)){
    }
}//GEN-LAST:event_txtValDocFocusLost

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
    txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
    strCodPrv=txtCodPrv.getText();
    txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (txtCodPrv.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)){
            if (txtCodPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtNomPrv.setText("");
                txtValDoc.setText("");
            }
            else
            {
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
    }
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtNomPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvActionPerformed
    txtNomPrv.transferFocus();
}//GEN-LAST:event_txtNomPrvActionPerformed

private void txtNomPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusGained
    strNomPrv=txtNomPrv.getText();
    txtNomPrv.selectAll();
}//GEN-LAST:event_txtNomPrvFocusGained

private void txtNomPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomPrv.getText().equalsIgnoreCase(strNomPrv)) {
        if (txtNomPrv.getText().equals("")) {
            txtCodPrv.setText("");
            txtNomPrv.setText("");
        } else {
            mostrarVenConPrv(2);
        }
    } else
        txtNomPrv.setText(strNomPrv);
}//GEN-LAST:event_txtNomPrvFocusLost

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
    mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void txtCodPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPueActionPerformed
    txtCodPue.transferFocus();
}//GEN-LAST:event_txtCodPueActionPerformed

private void txtCodPueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPueFocusGained
    strCodPto=txtCodPue.getText();
    txtCodPue.selectAll();
}//GEN-LAST:event_txtCodPueFocusGained

private void txtCodPueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPueFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodPue.isEditable()){
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodPue.getText().equalsIgnoreCase(strCodPto)){
                if (txtCodPue.getText().equals("")){
                    txtNomPue.setText("");
                }
                else
                {
                    mostrarVenConPto(1);
                }
            }
            else
                txtCodPue.setText(strCodPto);
        }
}//GEN-LAST:event_txtCodPueFocusLost

private void txtNomPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPueActionPerformed
    txtNomPue.transferFocus();
}//GEN-LAST:event_txtNomPueActionPerformed

private void txtNomPueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPueFocusGained
    strNomPto=txtNomPue.getText();
    txtNomPue.selectAll();
}//GEN-LAST:event_txtNomPueFocusGained

private void txtNomPueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPueFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomPue.getText().equalsIgnoreCase(strNomPto)) {
        if (txtNomPue.getText().equals("")) {
            txtCodPue.setText("");
        } else {
            mostrarVenConPto(2);
        }
    } else
        txtNomPue.setText(strNomPto);
}//GEN-LAST:event_txtNomPueFocusLost

private void butPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPueActionPerformed
    mostrarVenConPto(0);
}//GEN-LAST:event_butPueActionPerformed

private void txtCodSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSegActionPerformed
    txtCodSeg.transferFocus();
}//GEN-LAST:event_txtCodSegActionPerformed

private void txtCodSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusGained
    strCodSeg=txtCodSeg.getText();
    txtCodSeg.selectAll();
}//GEN-LAST:event_txtCodSegFocusGained

private void txtCodSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSegFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (txtCodSeg.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodSeg.getText().equalsIgnoreCase(strCodSeg)){
            if (txtCodSeg.getText().equals("")){
                txtNomSeg.setText("");
            }
            else
            {
                mostrarVenConSeg(1);
            }
        }
        else
            txtCodSeg.setText(strCodSeg);
    }
}//GEN-LAST:event_txtCodSegFocusLost

private void txtNomSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomSegActionPerformed
    txtNomSeg.transferFocus();
}//GEN-LAST:event_txtNomSegActionPerformed

private void txtNomSegFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusGained
    strNomSeg=txtNomSeg.getText();
    txtNomSeg.selectAll();
}//GEN-LAST:event_txtNomSegFocusGained

private void txtNomSegFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomSegFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomSeg.getText().equalsIgnoreCase(strNomSeg)) {
        if (txtNomSeg.getText().equals("")) {
            txtCodSeg.setText("");
        } else {
            mostrarVenConSeg(2);
        }
    } else
        txtNomSeg.setText(strNomSeg);
}//GEN-LAST:event_txtNomSegFocusLost

private void butSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSegActionPerformed
    mostrarVenConSeg(0);
}//GEN-LAST:event_butSegActionPerformed

private void txtCodPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPagActionPerformed
    txtCodForPag.transferFocus();
}//GEN-LAST:event_txtCodPagActionPerformed

private void txtCodPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPagFocusGained
    strCodPag=txtCodForPag.getText();
    txtCodForPag.selectAll();
}//GEN-LAST:event_txtCodPagFocusGained

private void txtCodPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPagFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (txtCodForPag.isEditable()){
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodPag)){
            if (txtCodForPag.getText().equals("")){
                txtNomForPag.setText("");
            }
            else
            {
                mostrarVenConPag(1);
            }
        }
        else
            txtCodForPag.setText(strCodPag);
    }
}//GEN-LAST:event_txtCodPagFocusLost

private void txtNomPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPagActionPerformed
    txtNomForPag.transferFocus();
}//GEN-LAST:event_txtNomPagActionPerformed

private void txtNomPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPagFocusGained
    strNomPag=txtNomForPag.getText();
    txtNomForPag.selectAll();
}//GEN-LAST:event_txtNomPagFocusGained

private void txtNomPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPagFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomForPag.getText().equalsIgnoreCase(strNomPag)) {
        if (txtNomForPag.getText().equals("")) {
            txtCodForPag.setText("");
        } else {
            mostrarVenConPag(2);
        }
    } else
        txtNomForPag.setText(strNomPag);
}//GEN-LAST:event_txtNomPagFocusLost

private void butPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPagActionPerformed
    mostrarVenConPag(0);
}//GEN-LAST:event_butPagActionPerformed

private void txtNomMesEmbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesEmbFocusGained
    strNomMesEmb=txtNomMesEmb.getText();
    txtNomMesEmb.selectAll();
}//GEN-LAST:event_txtNomMesEmbFocusGained

private void txtNomMesEmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesEmbFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomMesEmb.getText().equalsIgnoreCase(strNomMesEmb)) {
        if (txtNomMesEmb.getText().equals("")) {
            txtCodMesEmb.setText("");
        } else {
            mostrarVenConMesEmb(2);
        }
    } else
        txtNomMesEmb.setText(strNomMesEmb);
}//GEN-LAST:event_txtNomMesEmbFocusLost

private void butMesEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMesEmbActionPerformed
    mostrarVenConMesEmb(0);
}//GEN-LAST:event_butMesEmbActionPerformed

private void txtNomMesEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMesEmbActionPerformed
    txtNomMesEmb.transferFocus();
}//GEN-LAST:event_txtNomMesEmbActionPerformed

private void txtPesTotKgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesTotKgrActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_txtPesTotKgrActionPerformed

private void txtPesTotKgrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesTotKgrFocusGained
    // TODO add your handling code here:
}//GEN-LAST:event_txtPesTotKgrFocusGained

private void txtPesTotKgrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesTotKgrFocusLost
    // TODO add your handling code here:
}//GEN-LAST:event_txtPesTotKgrFocusLost

private void optTmFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmFobActionPerformed
    if(optTmFob.isSelected()){
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=1;
    objImp32_01.setTipoNotaPedido(intTipNotPed);
    objImp32_02.setTipoNotaPedido(intTipNotPed); //se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    objImp32_01.calculaTotalFletes();
    objImp32_02.regenerarCalculos();
}//GEN-LAST:event_optTmFobActionPerformed

private void optTmCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTmCfrActionPerformed
    if(optTmCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(false);
    }
    else
        optTmCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=2;
    objImp32_01.setTipoNotaPedido(intTipNotPed);
    objImp32_01.setValorFleteInactivo();
    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    objImp32_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp32_01.calculaTotalFletes();
    objImp32_02.regenerarCalculos();
}//GEN-LAST:event_optTmCfrActionPerformed

private void optPzaFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaFobActionPerformed
    if(optPzaFob.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
        optPzaCfr.setSelected(false);
    }
    else
        optPzaFob.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=3;
    objImp32_01.setTipoNotaPedido(intTipNotPed);
    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    objImp32_01.calculaTotalFletes();
    //objImp32_02.seteaColumnaPiezas();
    objImp32_02.regenerarCalculos();
}//GEN-LAST:event_optPzaFobActionPerformed

private void optPzaCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPzaCfrActionPerformed
    if(optPzaCfr.isSelected()){
        optTmFob.setSelected(false);
        optTmCfr.setSelected(false);
        optPzaFob.setSelected(false);
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    }
    else
        optPzaCfr.setSelected(true);//siempre que se da click se selecciona.
    intTipNotPed=4;
    objImp32_01.setTipoNotaPedido(intTipNotPed);
    objImp32_01.setValorFleteInactivo();
    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
    objImp32_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
    objImp32_01.calculaTotalFletes();
    //objImp32_02.seteaColumnaPiezas();
    objImp32_02.regenerarCalculos();

}//GEN-LAST:event_optPzaCfrActionPerformed

private void chkMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovRegActionPerformed
    if (chkMosMovReg.isSelected()){
        cargarMovReg();
    }
    else
        objTblModDetDocAso.removeAllRows();
}//GEN-LAST:event_chkMosMovRegActionPerformed

    private void txtVerNumPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVerNumPedFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVerNumPedFocusGained

    private void txtVerNumPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVerNumPedFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVerNumPedFocusLost

    private void txtNomMesArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMesArrActionPerformed
        txtNomMesArr.transferFocus();
    }//GEN-LAST:event_txtNomMesArrActionPerformed

    private void txtNomMesArrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesArrFocusGained
        strNomMesArr=txtNomMesArr.getText();
        txtNomMesArr.selectAll();
    }//GEN-LAST:event_txtNomMesArrFocusGained

    private void txtNomMesArrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesArrFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomMesArr.getText().equalsIgnoreCase(strNomMesArr)) {
            if (txtNomMesArr.getText().equals("")) {
                txtCodMesArr.setText("");
            } else {
                mostrarVenConMesArr(2);
            }
        } else
            txtNomMesArr.setText(strNomMesArr);
    }//GEN-LAST:event_txtNomMesArrFocusLost

    private void butMesArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMesArrActionPerformed
        mostrarVenConMesArr(0);
    }//GEN-LAST:event_butMesArrActionPerformed

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        strCodExp = txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodExp.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)) {
                if (txtCodExp.getText().equals("")) {
                    txtNomExp.setText("");
                } else {
                    mostrarVenConExp(1);
                }
            } else {
                txtCodExp.setText(strCodExp);
            }
        }
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtNomExp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExp2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomExp2ActionPerformed

    private void txtNomExp2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomExp2FocusGained

    private void txtNomExp2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExp2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomExp2FocusLost

    private void txtNomExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExpActionPerformed
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        strNomExp = txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomExp.getText().equalsIgnoreCase(strNomExp)) {
            if (txtNomExp.getText().equals("")) {
                txtCodExp.setText("");
            } else {
                mostrarVenConExp(2);
            }
        } else {
            txtNomExp.setText(strNomExp);
        }
    }//GEN-LAST:event_txtNomExpFocusLost

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

    private void butPedPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedPreActionPerformed
        if(  (optTmFob.isSelected()) || (optTmCfr.isSelected()) || (optPzaFob.isSelected()) || (optPzaCfr.isSelected())  ){
            if(objImp32_04.getBotonPresionado()==0){//no se ha presionado ningun boton, en este caso NO se debe cambiar nada del modelo
            }
            objImp32_04.setVisible(true);
            if(objImp32_04.getBotonPresionado()==1){ //se presiono el boton ACEPTAR
                //Se coloca la informacion en los arreglos
                arlDatCab_objImp32_04=objImp32_04.getCabeceraSeleccionada();//devuelve los datos de la cabecera - POR EL MOMENTO NO LE VEO NINGUNA FINALIDAD, PERO LO REFERENCIO POR SIAK
                arlDatDet_objImp32_04=objImp32_04.getDetalleSeleccionado(); //devuelve los datos del detalle
                //la informacion de los arreglos se la coloca en los campos y en las tablas respectivas
                setCabeceraPedidoPrevio();
                setDetallePedidoPrevio();
                txtVerNumPed.setText(objImp32_04.getNotaPedidoSeleccionado());
                txtNumPedOri.setText(objImp32_04.getNotaPedidoSeleccionado());
                objImp32_02.setPesoArancelItem();   
                objImp32_02.regenerarCalculoValISD(); 
            }
        }
        else{
            mostrarMsgInf("Seleccione el tipo de Nota de Pedido y vuelva a intentarlo.");
        }
    }//GEN-LAST:event_butPedPreActionPerformed

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag))
        {
            if (txtCodForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                mostrarVenConForPag(1);
            }
        }
        else
        txtCodForPag.setText(strCodForPag);
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomForPag.getText().equalsIgnoreCase(strNomForPag))
        {
            if (txtNomForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtNomForPag.setText("");
                txtNumForPag.setText("");
                txtTipForPag.setText("");
                objTblModForPag.removeAllRows();
            }
            else
            {
                mostrarVenConForPag(2);
            }
        }
        else
        txtNomForPag.setText(strNomForPag);
    }//GEN-LAST:event_txtNomForPagFocusLost

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        strCodForPag=txtCodForPag.getText();
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void butNumCorEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNumCorEleActionPerformed
        enviarCorElePreVta(false);
        //validarRangoPzas();
    }//GEN-LAST:event_butNumCorEleActionPerformed

    private void chkModSolFecEmbArrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkModSolFecEmbArrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkModSolFecEmbArrActionPerformed

    private void txtNomMesPueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesPueFocusGained
        strNomMesPue=txtNomMesPue.getText();
        txtNomMesPue.selectAll();
    }//GEN-LAST:event_txtNomMesPueFocusGained

    private void txtNomMesPueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomMesPueFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtNomMesPue.getText().equalsIgnoreCase(strNomMesPue)) {
            if (txtNomMesPue.getText().equals("")) {
                txtCodMesPue.setText("");
            } else {
                mostrarVenConMesPue(2);
            }
        } else
            txtNomMesPue.setText(strNomMesPue);
    }//GEN-LAST:event_txtNomMesPueFocusLost

    private void txtNomMesPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomMesPueActionPerformed
        txtNomMesPue.transferFocus();
    }//GEN-LAST:event_txtNomMesPueActionPerformed

    private void butMesPueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMesPueActionPerformed
        mostrarVenConMesPue(0);
    }//GEN-LAST:event_butMesPueActionPerformed

    private void butCreCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCreCtaActionPerformed
        if(!chkCreCta.isSelected()){
            if(generaCtaPed()){
                mostrarMsgInf("Se generaron cuentas correctamente");
                chkCreCta.setSelected(true);
            }
            else{
                mostrarMsgInf("Se produjo un error");
                chkCreCta.setSelected(false);
            }
        }
        else
            mostrarMsgInf("No se puede crear cuentas contables porque ya existen");
    }//GEN-LAST:event_butCreCtaActionPerformed

    private void butCalISDCarPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCalISDCarPagActionPerformed
        // TODO add your handling code here:        
        if(calcula_ISD())
            mostrarMsgInf("El proceso se realizó correctamente.");
        else 
            mostrarMsgInf("El proceso no se realizó correctamente.");
        
    }//GEN-LAST:event_butCalISDCarPagActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCalISDCarPag;
    private javax.swing.JButton butCreCta;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butMesArr;
    private javax.swing.JButton butMesEmb;
    private javax.swing.JButton butMesPue;
    private javax.swing.JButton butNumCorEle;
    private javax.swing.JButton butPag;
    private javax.swing.JButton butPedPre;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butPue;
    private javax.swing.JButton butSeg;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkCreCta;
    private javax.swing.JCheckBox chkModSolFecEmbArr;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JCheckBox chkMosTodVerPed;
    private javax.swing.JCheckBox chkNotPedLis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblAniEmb;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblEmpExp;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblFecArr;
    private javax.swing.JLabel lblFecColPed;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecEmb;
    private javax.swing.JLabel lblFecPro;
    private javax.swing.JLabel lblFecPue;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblImp;
    private javax.swing.JLabel lblMesArr;
    private javax.swing.JLabel lblMesEmb;
    private javax.swing.JLabel lblMesPue;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblNumPedOri;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPag;
    private javax.swing.JLabel lblPesTotKgr;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblPue;
    private javax.swing.JLabel lblSeg;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValFac;
    private javax.swing.JLabel lblVerNumPed;
    private javax.swing.JRadioButton optPzaCfr;
    private javax.swing.JRadioButton optPzaFob;
    private javax.swing.JRadioButton optTmCfr;
    private javax.swing.JRadioButton optTmFob;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panAsiDiaCen;
    private javax.swing.JPanel panAsiDiaNor;
    private javax.swing.JPanel panAsiDiaSur;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabDocAso;
    private javax.swing.JPanel panCabForPag;
    private javax.swing.JPanel panCarPagImp;
    private javax.swing.JPanel panDatForPag;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panDetDocAso;
    private javax.swing.JPanel panDetPagImp;
    private javax.swing.JPanel panDocAso;
    private java.awt.Panel panForPag;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panPedEmb;
    private javax.swing.JScrollPane spnCabDocAso;
    private javax.swing.JSplitPane spnDet;
    private javax.swing.JScrollPane spnDetDocAso;
    private javax.swing.JScrollPane spnForPag;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnPedEmb;
    private javax.swing.JSplitPane sppDocAso;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCabDocAso;
    private javax.swing.JTable tblDetDocAso;
    private javax.swing.JTable tblForPag;
    private javax.swing.JTable tblPedEmb;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtAniEmb;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodMesArr;
    private javax.swing.JTextField txtCodMesEmb;
    private javax.swing.JTextField txtCodMesPue;
    private javax.swing.JTextField txtCodPag;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodPue;
    private javax.swing.JTextField txtCodSeg;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtEmpExp;
    private javax.swing.JTextField txtModPed;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomExp2;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomImp;
    private javax.swing.JTextField txtNomMesArr;
    private javax.swing.JTextField txtNomMesEmb;
    private javax.swing.JTextField txtNomMesPue;
    private javax.swing.JTextField txtNomPag;
    private javax.swing.JTextField txtNomPrv;
    private javax.swing.JTextField txtNomPue;
    private javax.swing.JTextField txtNomSeg;
    private javax.swing.JTextField txtNumCorEle;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumForPag;
    private javax.swing.JTextField txtNumPedOri;
    public javax.swing.JTextField txtPesTotKgr;
    private javax.swing.JTextField txtTipForPag;
    public javax.swing.JTextField txtValDoc;
    private javax.swing.JTextField txtValFac;
    private javax.swing.JTextField txtVerNumPed;
    private javax.swing.JTextField txtVerPed;
    // End of variables declaration//GEN-END:variables
    
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodImp.getDocument().addDocumentListener(objDocLis);
        txtNomImp.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtNomPrv.getDocument().addDocumentListener(objDocLis);
        txtCodExp.getDocument().addDocumentListener(objDocLis);
        txtNomExp.getDocument().addDocumentListener(objDocLis);
        txtNomExp2.getDocument().addDocumentListener(objDocLis);
        txtEmpExp.getDocument().addDocumentListener(objDocLis);
        txtCodPue.getDocument().addDocumentListener(objDocLis);
        txtNomPue.getDocument().addDocumentListener(objDocLis);
        txtCodSeg.getDocument().addDocumentListener(objDocLis);
        txtNomSeg.getDocument().addDocumentListener(objDocLis);
        txtCodForPag.getDocument().addDocumentListener(objDocLis);
        txtNomForPag.getDocument().addDocumentListener(objDocLis);
        txtCodMesEmb.getDocument().addDocumentListener(objDocLis);
        txtNomMesEmb.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtVerNumPed.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtPesTotKgr.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    }     

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
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgYesNo(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                            objTooBar.afterInsertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){
                            blnRes=objTooBar.modificar();
                            objTooBar.afterModificar();
                        }
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
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodPrv.setText("");
            txtNomPrv.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");
            txtNomExp2.setText("");
            txtEmpExp.setText("");
            txtCodPue.setText("");
            txtNomPue.setText("");
            txtCodSeg.setText("");
            txtNomSeg.setText("");
            txtCodForPag.setText("");
            txtNomForPag.setText("");
            dtpFecDoc.setText("");
            dtpFecColPed.setText("");
            dtpFecProPed.setText("");
            dtpFecEmb.setText("");
            dtpFecPue.setText("");
            dtpFecArr.setText("");
            txtAniEmb.setText("");
            txtCodMesEmb.setText(""); 
            txtNomMesEmb.setText("");
            txtCodMesPue.setText("");
            txtNomMesPue.setText("");     
            txtCodMesArr.setText("");
            txtNomMesArr.setText("");
            txtNumDoc.setText("");
            txtNumPedOri.setText("");
            txtVerNumPed.setText("");
            txtVerPed.setText("");
            chkMosTodVerPed.setSelected(false); 
            txtCodDoc.setText("");
            txtValDoc.setText("");
            txtPesTotKgr.setText("");
            txtCodPag.setText("");
            txtNomPag.setText("");
            chkNotPedLis.setSelected(false);

            optTmFob.setSelected(false);
            optTmCfr.setSelected(false);
            optPzaFob.setSelected(false);
            optPzaCfr.setSelected(false);

            txtCodDoc.setEditable(false);
            txtValDoc.setEditable(false);
            txtPesTotKgr.setEditable(false);

            txaObs1.setText("");
            txaObs2.setText("");

            objImp32_01.limpiarTablaImp32_01();
            objImp32_02.limpiarTablaImp32_02();
            objImp32_01.setBlnHayCamCamImp32_01(false);
            objImp32_02.setBlnHayCamCamImp32_02(false);
            objTblModPedEmb.removeAllRows();    //Se limpia el modelo
            objTblModCabDocAso.removeAllRows(); //Se limpia el modelo
            
            chkCreCta.setSelected(false);
            chkModSolFecEmbArr.setSelected(false);
            objAsiDia.inicializar();
            objAsiDiaRev.inicializar();
            objAsiDiaNue.inicializar();
            txtModPed.setText("");
            
            strEstDocOri="";
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }

                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
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

    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConNotPed.size()>0){
                    if(intIndiceNotPed>0){
                        if (blnHayCam || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02())  ) {
                            if (isRegPro()) {
                                intIndiceNotPed=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceNotPed=0;
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
                if(arlDatConNotPed.size()>0){
                    if(intIndiceNotPed>0){
                        if (blnHayCam || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02()) ){
                            if (isRegPro()) {
                                intIndiceNotPed--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceNotPed--;
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
                if(arlDatConNotPed.size()>0){
                    if(intIndiceNotPed < arlDatConNotPed.size()-1){
                        if (blnHayCam || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02())){
                            if (isRegPro()) {
                                intIndiceNotPed++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceNotPed++;
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
                if(arlDatConNotPed.size()>0){
                    if(intIndiceNotPed<arlDatConNotPed.size()-1){
                        if (blnHayCam || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02()) ) {
                            if (isRegPro()) {
                                intIndiceNotPed=arlDatConNotPed.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceNotPed=arlDatConNotPed.size()-1;
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
                if ((blnHayCam) || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02())   ){
                    isRegPro();
                }
                limpiarFrm();
                txtEmpExp.setText("El adecuado");
                mostrarTipDocPre();
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                objImp32_02.setFechaDocumento(dtpFecDoc.getText());
                
                dtpFecColPed.setEnabled(false);
                dtpFecProPed.setEnabled(true);
                dtpFecEmb.setEnabled(true);
                dtpFecPue.setEnabled(true);
                dtpFecArr.setEnabled(true);
                
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                objImp32_01.inicializar();
                objImp32_01.generarDatosCargoPagar();
                objImp32_01.setEditable(true);
                objImp32_02.inicializar();
                objImp32_02.setEditable(true);
                
                objImp32_04=new ZafImp32_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objTooBar.getEstado());
                butPedPre.setEnabled(true);
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
                chkCreCta.setSelected(true);
                chkCreCta.setEnabled(false);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar() {            
        }

        public void clickModificar() {
            chkCreCta.setEnabled(false);
            System.out.println("strEstDocOri: " + strEstDocOri);
            if(strEstDocOri.equals("A")) 
            {
                txtCodTipDoc.setEditable(false);
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                dtpFecDoc.setEnabled(false);
                objImp32_02.setFechaDocumento(dtpFecDoc.getText());
                dtpFecColPed.setEnabled(false);
                dtpFecProPed.setEnabled(true);
                objImp32_02.setEditable(true);
                chkCreCta.setEnabled(false);
                
                //Se habilita fechas a editar
//                dtpFecEmb.setEnabled(true);
//                dtpFecPue.setEnabled(true);
//                dtpFecArr.setEnabled(true);
                
                //Se bloquea edición de importador, porque ya tiene cuentas creadas.
                txtCodImp.setEditable(false);
                txtNomImp.setEditable(false);
                butImp.setEnabled(false);
                /////////////////////////////////////////////////////////////////////
                
                objImp32_01.setEditable(true);
                objImp32_02.setEditable(true);
                
                if(optTmFob.isSelected()){
                    intTipNotPed=1;
                    objImp32_01.setTipoNotaPedido(intTipNotPed);
                    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
                    objImp32_01.calculaTotalFletes();
                    objImp32_02.regenerarCalculos();
                } 
                else if(optTmCfr.isSelected()){
                    intTipNotPed=2;
                    objImp32_01.setTipoNotaPedido(intTipNotPed);
                    objImp32_01.setValorFleteInactivo();
                    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
                    objImp32_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
                    objImp32_01.calculaTotalFletes();
                    objImp32_02.regenerarCalculos();
                }
                else if(optPzaFob.isSelected()){
                    intTipNotPed=3;
                    objImp32_01.setTipoNotaPedido(intTipNotPed);
                    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
                    objImp32_01.calculaTotalFletes();
                    //objImp32_02.seteaColumnaPiezas();
                    objImp32_02.regenerarCalculos();
                } 
                else if(optPzaCfr.isSelected()) {
                    intTipNotPed=4;
                    objImp32_01.setTipoNotaPedido(intTipNotPed);
                    objImp32_01.setValorFleteInactivo();
                    objImp32_02.setTipoNotaPedido(intTipNotPed);//se lo hace para cancelar la edicion de la columna de PIEZA, si esta en 0 ó 3 no se edita la columna
                    objImp32_01.setFleteItem(new BigDecimal(BigInteger.ZERO));
                    objImp32_01.calculaTotalFletes();
                    //objImp32_02.seteaColumnaPiezas();
                    objImp32_02.regenerarCalculos();
                }
                butPedPre.setEnabled(true);
                txtNumPedOri.setEditable(false);
                txtVerNumPed.setEditable(false);
                txtVerPed.setEditable(false);
                txtModPed.setEditable(true);
            }
        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
            cargarCabReg();
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
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

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                
                //Desplazarse al siguiente registro si es posible.
                if(intIndiceNotPed<arlDatConNotPed.size()-1){
                    intIndiceNotPed++;
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }                  
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
                return true;
            }
            return true;
        }

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null){
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if ((blnHayCam) || (objImp32_01.isBlnHayCamImp32_01())  || (objImp32_02.isBlnHayCamImp32_02())   ){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            objImp32_02.getCalcularPesoTotal(); 
            objTblModImp32_01=objImp32_01.getTblModImp32_01();
            objTblModImp32_02=objImp32_02.getTblModImp32_02();
            
            if (!isCamVal())
                return false;
            
            return true;
        }

        public boolean beforeConsultar() {
            objTblModImp32_01=objImp32_01.getTblModImp32_01();
            objTblModImp32_02=objImp32_02.getTblModImp32_02();
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            
            objImp32_02.getCalcularPesoTotal(); 
            
            if (!isCamVal())
                return false;

            /* Se valida que no permita cambiar de importador, cuando ya han habido movimientos contables.*/
            if(!strCodImpAnt.equalsIgnoreCase(txtCodImp.getText())){
                if(isCuentaAsociadaSaldo()){
                    mostrarMsgInf("<HTML>No se puede cambiar de Importador por que la(s) cuenta(s) asociada(s) tienen movimientos contables.<BR>Verifique movimientos contables y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            return blnRes;
        }

        public boolean beforeEliminar() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            if(isPedidoEmbarcado()){
                mostrarMsgInf("<HTML>Existe Pedido Embarcado asociado.<BR>Anule el Pedido Embarcado e intente de nuevo.</HTML>");
                return false;
            }
            
            if(isCuentaAsociadaSaldo()){
                mostrarMsgInf("<HTML>Existe(n) cuenta(s) asociada(s) con saldo en la Nota de Pedido.<BR>Verifique saldos y vuelva a intentarlo.</HTML>");
                return false;
            }
            
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            enviarCorEle_insNotPed();
            //consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            //Configurar JTable: Establecer columnas editables.
            vecAuxPedEmb=new Vector();
            vecAuxPedEmb.add("" + INT_TBL_DAT_PED_EMB_BUT);
            objTblModPedEmb.setColumnasEditables(vecAuxPedEmb);
            vecAuxPedEmb=null;
            objTblModPedEmb.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);
            tblPedEmb.setEnabled(true);
            chkMosMovReg.setEnabled(true);
            objTblModCabDocAso.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);
            tblCabDocAso.setEnabled(true);
            objTblModDetDocAso.setModoOperacion(objTblModPedEmb.INT_TBL_EDI);
            tblDetDocAso.setEnabled(true);
            butNumCorEle.setEnabled(true);
            txtNumCorEle.setEnabled(true);
            txtNumCorEle.setEditable(true);
            
            bgdSumValTotFobCfrDb=BigDecimal.ZERO;
            BigDecimal bgdValTotFobCfrDb=BigDecimal.ZERO;
            if(objTblModImp32_02.getRowCountTrue()>0){
                for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                    bgdValTotFobCfrDb=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                    
                    bgdSumValTotFobCfrDb=bgdSumValTotFobCfrDb.add(bgdValTotFobCfrDb);
                }
            }
            //System.out.println("CONSULTA-bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);
            butCalISDCarPag.setEnabled(true);
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            objAsiDia.inicializar();
            objAsiDiaNue.inicializar();
            objAsiDiaRev.inicializar();
            //cargarReg();
            
            //Se setea la fecha por el día de hoy, para que presente la consulta y no retorne vacío. Cuando se reversaba reversaba un documento anterior, ya no aparecía en la consulta.
            dtpFecDoc.setText(""); 
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
            objImp32_02.setFechaDocumento(dtpFecDoc.getText());       
            ////////////////////
                    
            consultarReg();
            objTooBar.afterConsultar();
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        

        
    }

    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa(instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior a la Nota de Pedido(Instancia anterior: Nota de Pedido Previa)
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotPedPreImp(){
        boolean blnRes=false;
        Object objCodSegNotPedPre=null;
        Object objCodSegNotPed=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT *FROM(";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPedPre, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedPreImp AND a1.co_loc=a3.co_locRelCabNotPedPreImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedPreImp AND a1.co_doc=a3.co_docRelCabNotPedPreImp";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" 	AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" 	AND a1.co_doc=" + txtCodDoc.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPedPre=rst.getObject("co_segNotPedPre");
                    objCodSegNotPed=rst.getObject("co_segNotPed");
                }
                
                if(objCodSegNotPed==null){//no existe seguimiento por tanto se debe ingresar 
                    blnRes=true;
                    objCodSegInsAnt=objCodSegNotPedPre;
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
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        boolean blnOptCreCtaPas=false;//SI ES TRUE CREA CUENTA  DE ACTIVO Y PASIVO
        objCodSegInsAnt=null;
        java.util.Date datTmp;
        try{
            System.out.println("insertarReg...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(chkCreCta.isSelected())
                    blnOptCreCtaPas=true;
                
                if (insertar_tbmCabNotPedImp()){
                    if(insertar_tbmDetNotPedImp()){
                        if(insertar_tbmCarPagNotPedImp()){
                            if(actualizar_tbmCabTipDoc()){
                                if(generaCtaPedIns()){
//                                if(crearCuentaPedido(blnOptCreCtaPas, Integer.parseInt(txtCodImp.getText()))){
//                                    if(insertarCtaPed(blnOptCreCtaPas, Integer.parseInt(txtCodImp.getText()))){
//                                        if(insertaTbmSalCtaPed(blnOptCreCtaPas, Integer.parseInt(txtCodImp.getText()))){
//                                            if(setPermisosUsrCtaPed(txtCodImp.getText())){
                                                if(getCodSegNotPedPreImp()){
                                                    //Asignar fecha actual
                                                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                                    datTmp=objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy");
                                                        if(generaAsiDia()){
                                                            if(objAsiDia.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), datTmp)){
                                                                if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 1, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
                                                                , 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                                    
                                                                    if(calculaISDCarPag()){
                                                                        con.commit();
                                                                        blnRes=true;
                                                                        enviarCorElePreVta(true);
//                                                                        if(insertaDatosGP(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),
//                                                                                          Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumPedOri.getText())){
//                                                                            mostrarMsgInf("Orden de compra creada en GP");
//                                                                        }
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
//                                            }
//                                            else
//                                                con.rollback();
//                                        }
//                                        else
//                                            con.rollback();
//                                    }
//                                    else
//                                        con.rollback();
//                                }
//                                else
//                                    con.rollback();
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
            con.close();
            con=null;
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
     * Función que permite configurar el permiso a la cuenta recién creada de acuerdo a si se tiene ascceso a la cuenta de cabecera
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean setPermisosUsrCtaPed(String codImp){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" INSERT INTO tbr_ctaTipDocUsr(co_emp, co_loc, co_tipDoc, co_usr, co_cta)";
                strSQL+=" (	SELECT co_emp, co_loc, co_tipDoc, co_usr, " + intCodCtaPedActIns + " AS co_cta";
                strSQL+=" 	FROM tbr_ctaTipDocUsr";
                strSQL+="       WHERE co_emp=" + codImp + "";
                strSQL+="	AND co_cta=(SELECT ne_pad FROM tbm_plaCta WHERE co_emp=" + codImp + " AND co_cta=" + intCodCtaPedActIns + ")";
                strSQL+="	AND NOT EXISTS(SELECT *FROM tbr_ctaTipDocUsr WHERE co_emp=" + codImp + " AND co_cta=" + intCodCtaPedActIns + ")";
                strSQL+="	GROUP BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+="	ORDER BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+=" );";
                strSQL+=" INSERT INTO tbr_ctaTipDocUsr(co_emp, co_loc, co_tipDoc, co_usr, co_cta)";
                strSQL+=" (	SELECT co_emp, co_loc, co_tipDoc, co_usr, " + intCodCtaPedPasIns + " AS co_cta";
                strSQL+=" 	FROM tbr_ctaTipDocUsr";
                strSQL+="       WHERE co_emp=" + codImp + "";
                strSQL+="	AND co_cta=(SELECT ne_pad FROM tbm_plaCta WHERE co_emp=" + codImp + " AND co_cta=" + intCodCtaPedPasIns + ")";
                strSQL+="	AND NOT EXISTS(SELECT *FROM tbr_ctaTipDocUsr WHERE co_emp=" + codImp + " AND co_cta=" + intCodCtaPedPasIns + ")";
                strSQL+="	GROUP BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+="	ORDER BY co_emp, co_loc, co_tipDoc, co_usr";
                strSQL+=" );";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabNotPedImp(){
        int intUltReg=0;
        boolean blnRes=true;
        intNumNotPedDia=-1;
        int intVerNumPed=0;
        int intNumUltDoc=0;
        String strFecUltMod="";
        String strNumReq="";
        try{
            txtPesTotKgr.setText(""+objImp32_02.calcularV20PesoTotal());
            
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 
                
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){//modificar
                    strSQL="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()) {
                        intNumUltDoc=(rst.getInt("ne_ultDoc")+1);
                    }
                    txtNumDoc.setText("" + intNumUltDoc);

                    intVerNumPed=(txtVerPed.getText().length()==0?1:(Integer.parseInt(txtVerPed.getText())+1));
                    txtVerPed.setText(""+intVerNumPed);

                }
                else if(objTooBar.getEstado()=='n'){
                    txtVerPed.setText("");
                }
                
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_cabnotpedimp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intUltReg=rst.getInt("co_doc");
                }
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocNotPed";
                strSQL+=" FROM tbm_cabNotPedImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumNotPedDia=rst.getInt("ne_numDocNotPed");
                }
                intNumNotPedDia++;
                
                strNumReq="";
                if(arlDatCab_objImp32_04!=null){
                    if(arlDatCab_objImp32_04.size()>0){
                        for(int f=0; f<arlDatCab_objImp32_04.size();f++){
                            strSQL="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.tx_numReq";
                            strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_EMP) + "";
                            strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_LOC) + "";
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_TIP_DOC) + "";
                            strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_DOC) + "";
                            strSQL+=";";
                            System.out.println("error: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                strNumReq+=strNumReq.length()<=0?"":(";" + rst.getString("tx_numReq"));
                            }
                        }
                    }  
                }
                

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabnotpedimp(";
                strSQL+="         co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, tx_numdoc2, ne_tipnotped";
                strSQL+="       , co_imp, co_prv, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="       , tx_tipemp, nd_valdoc, nd_pestotkgr, st_notpedlis, st_imp, tx_obs1";
                strSQL+="       , tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ne_verNumPed, tx_numdoc3, st_doc, nd_porISD";
                strSQL+="       , ne_aniemb, co_mesemb, co_mespue, co_mesarr";
                strSQL+="       , fe_emb, fe_pue, fe_arr";
                //strSQL+="     , co_empDiaAju, co_locDiaAju, co_tipDocDiaAju, co_docDiaAju";
                if(dtpFecColPed.isFecha())  
                    strSQL+="   , fe_colPed";
                if(dtpFecProPed.isFecha())  
                    strSQL+="   , fe_pro";
                strSQL+="   , tx_modPed"; 
                strSQL+=" )";   
                
                strSQL+=" VALUES (";
                strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                strSQL+=", " + objParSis.getCodigoLocal();   //co_loc
                strSQL+=", " + txtCodTipDoc.getText();       //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText();          //co_doc
                
                if(objTooBar.getEstado()=='n')
                    strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                else
                    strSQL+=", '" + objUti.formatearFecha(datFecAux,"yyyy-MM-dd") + "'"; //fe_doc

                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + ""; //ne_numdoc
                
                if(objTooBar.getEstado()=='n'){
                    strSQL+=", " + objUti.codificar(txtVerNumPed.getText()) + "";//tx_numdoc2 
                }   
                else{ 
                    strSQL+=", " + objUti.codificar(txtNumPedOri.getText() + "_" + "" + txtVerPed.getText()); //tx_numdoc2
                    txtVerNumPed.setText(txtNumPedOri.getText() + "_" + "" + txtVerPed.getText());
                }   
                
                if(optTmFob.isSelected())
                    strSQL+=",'1'"; //ne_tipnotped
                else if(optTmCfr.isSelected())
                    strSQL+=",'2'"; //ne_tipnotped
                else if(optPzaFob.isSelected())
                    strSQL+=",'3'"; //ne_tipnotped
                else if(optPzaCfr.isSelected())
                    strSQL+=",'4'"; //ne_tipnotped      
                
                strSQL+=", " + objUti.codificar(txtCodImp.getText()) + "";    //co_imp
                strSQL+=", " + objUti.codificar(txtCodPrv.getText()) + "";    //co_prv
                strSQL+=", " + objUti.codificar(txtCodExp.getText()) + "";    //co_exp
                strSQL+=", " + objUti.codificar(txtCodPue.getText()) + "";    //co_ciupueemb
                strSQL+=", " + objUti.codificar(txtCodSeg.getText()) + "";    //co_seg
                strSQL+=", " + objUti.codificar(txtCodPag.getText()) + ""; //co_forpag
                strSQL+=", " + objUti.codificar(txtEmpExp.getText()) + "";    //tx_tipemp
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + ""; //nd_valdoc
                strSQL+=", " + objUti.codificar(txtPesTotKgr.getText()) + ""; //nd_pestotkgr
                strSQL+=", '"+((chkNotPedLis.isSelected())?"S":"N")+"'";      //st_notpedlis
                strSQL+=", 'N'"; //st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";      //tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";      //tx_obs2
                strSQL+=", 'A'";//st_reg
                strSQL+=", '" + strFecUltMod + "'"; //fe_ing
                strSQL+=", '" + strFecUltMod + "'"; //fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + ""; //co_usring
                strSQL+="," + objParSis.getCodigoUsuario() + ""; //co_usrmod
                
                if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') )//modificar
                    strSQL+=", " + txtVerPed.getText() + ""; //ne_verNumPed
                else
                    strSQL+=", Null";//ne_verNumPed
                
                strSQL+=", " + objUti.codificar(txtNumPedOri.getText()) + "";   //tx_numdoc3
                strSQL+=", 'A'";//st_doc
                
                BigDecimal bgdPorISD=objZafImp.getPorcentajeISD(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre)).multiply(BigDecimal.valueOf(100));
                strSQL+=", "+bgdPorISD+""; //nd_porISD     
                
                strSQL+=", " + txtAniEmb.getText() + "";                        
                strSQL+=", " + objUti.codificar(txtCodMesEmb.getText()) + ""; //co_mesemb
                strSQL+=", " + objUti.codificar(txtCodMesPue.getText()) + ""; //co_mespue     
                strSQL+=", " + objUti.codificar(txtCodMesArr.getText()) + ""; //co_mesarr    
                strSQL+=", '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_emb     
                strSQL+=", '" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_pue       
                strSQL+=", '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_arr      
                
                if(dtpFecColPed.isFecha())  
                    strSQL+=", '" + objUti.formatearFecha(dtpFecColPed.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_colPed
                
                if(dtpFecProPed.isFecha())  
                    strSQL+=", '" + objUti.formatearFecha(dtpFecProPed.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_pro
                       
                strSQL+=", " + objUti.codificar(txtModPed.getText()) + "";   //tx_modPed
                strSQL+=");";
                
                if(arlDatCab_objImp32_04!=null){
                    if(arlDatCab_objImp32_04.size()>0){
                        for(int f=0; f<arlDatCab_objImp32_04.size();f++){
                            strSQL+=" UPDATE tbm_cabNotPedPreImp";
                            strSQL+=" SET co_locRel=" + objParSis.getCodigoLocal() + "";
                            strSQL+="   , co_tipDocRel=" + txtCodTipDoc.getText() + "";
                            strSQL+="   , co_docRel=" + txtCodDoc.getText() + "";
                            strSQL+="   , st_cie='S'";
                            strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_EMP) + "";
                            strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_LOC) + "";
                            strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_TIP_DOC) + "";
                            strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_COD_DOC) + "";
                            strSQL+=";";
                        }
                    }  
                }
                
                //Se actualizan cuentas
                strSQL+=" UPDATE tbm_cabNotPedImp";
                strSQL+=" SET co_ctaAct=" + intCodCtaPedActIns + "";
                strSQL+="   , co_ctaPas=" + intCodCtaPedPasIns + "";
                //strSQL+="   , tx_numReq=" + strNumReq + "";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                strSQL+=";";
                System.out.println("insertar_tbmCabNotPedImp: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetNotPedImp(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                objImp32_02.regenerarISD();        
                for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detnotpedimp(";
                    strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_exp, nd_ara, nd_can";
                    strSQL+="   , nd_preuni, nd_valtotfobcfr, nd_canTonMet, nd_valfle, nd_valcfr, nd_valtotara";
                    strSQL+="   , nd_valtotgas, nd_valtotcos, nd_cosuni";
                    strSQL+="   , nd_valISD, st_impISDCreTri";
                    strSQL+=" )";
                    strSQL+=" VALUES (";
                    strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal();   //co_loc
                    strSQL+=", " + txtCodTipDoc.getText();       //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText();          //co_doc
                    strSQL+=", " + j; //co_reg

                    strSQL+=", " + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM); //co_itm
                    strSQL+=", " + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_EXP); //co_exp

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_POR_ARA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_POR_ARA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_POR_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_ara
                    
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_can

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PRE_UNI)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PRE_UNI).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PRE_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_preUni

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotfobcfr

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_canTonMet

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_FLE)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_FLE).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_FLE).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valfle

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_CFR).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valcfr

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_ARA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_ARA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_ARA).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotara

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_GAS)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_GAS).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_GAS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotgas

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valtotcos

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_cosuni
                    
                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_ISD)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_ISD).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_VAL_ISD).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + bgdVarValTmp; //nd_valISD    
                    
                    strSQL+=", '"+((objTblModImp32_02.isChecked(i, objImp32_02.INT_TBL_DAT_CHK_ISD_CRE_TRI))?"S":"N")+"'";  //st_impISDCreTri
                    
                    strSQL+=");";

                    strSQLIns+=strSQL;
                    j++;
                    
                }
                System.out.println("insertar_tbmDetNotPedImp: " + strSQLIns);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagNotPedImp(){
        boolean blnRes=true;
        String strSQLIns="";
        BigDecimal bgdVarValTmp=new BigDecimal("0");
        int j=1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp32_01.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_carpagnotpedimp(";
                    strSQL+="  co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_carpag, nd_valcarpag)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg

                    strSQL+=", " + objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag

                    bgdVarValTmp=objUti.redondearBigDecimal(objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP)==null?"0":(objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString().equals("")?"0":objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP).toString()), objParSis.getDecimalesBaseDatos());
                    strSQL+=", " + (bgdVarValTmp.compareTo(BigDecimal.ZERO)==0?null:bgdVarValTmp); //nd_valcarpag

                    strSQL+=");";
                    strSQLIns+=strSQL;
                    j++;

                }
                System.out.println("insertar_tbmCarPagNotPedImp: " + strSQLIns);
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
     * Esta función permite actualizar los cargos, cuando existen los actualiza y cuando no existe los inserta.
     * @return true: Si se pudo actualizar los cargos.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCarPagNotPedImp(){
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        String strSQLIns="";
        Object objVarValTmp=null;
        int intUltRegCar=-1;
        try{
            if (con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblModImp32_01.getRowCountTrue(); i++){
                    objVarValTmp=objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP);
                    //Cuando existe el cargo, Actualiza el valor del cargo.
                    strSQL ="";
                    strSQL+=" UPDATE tbm_carPagNotPedImp ";
                    strSQL+=" SET nd_valCarPag="+objVarValTmp;
                    strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa(); //co_emp
                    strSQL+=" AND co_loc="+ objParSis.getCodigoLocal();     //co_loc
                    strSQL+=" AND co_tipDoc="+ txtCodTipDoc.getText();      //co_tipDoc
                    strSQL+=" AND co_doc="+ txtCodDoc.getText();            //co_doc
                    strSQL+=" AND co_carpag=" + objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                    strSQL+=" ;"; 
                    strSQLIns+=strSQL;
                    
                    //Inserta solo los que no existen
                    Boolean blnExiCar=false;
                    stmLoc=con.createStatement();    
                    //Verifica si existe o no el cargo a pagar.
                    strSQL ="";
                    strSQL+=" SELECT * FROM tbm_carPagNotPedImp as x"; 
                    strSQL+=" WHERE x.co_emp="+objParSis.getCodigoEmpresa();
                    strSQL+=" AND x.co_loc="+ objParSis.getCodigoLocal();
                    strSQL+=" AND x.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND x.co_doc=" + txtCodDoc.getText();
                    strSQL+=" AND x.co_carPag="+objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_COD_CAR_PAG_IMP);                        
                    rstLoc=stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        blnExiCar=true;
                    }
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;

                    if(!blnExiCar){  //Sólo si cargo NO EXISTE
                        //System.out.println("No existe");
                        //Obtener el código del último registro.
                        strSQL="";
                        strSQL+=" SELECT MAX(a1.co_reg) AS co_reg";
                        strSQL+=" FROM tbm_carPagNotPedImp AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()) {
                            intUltRegCar=rst.getInt("co_reg");
                        }
                        intUltRegCar++;

                        //Inserta solo los que no existen
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_carPagNotPedImp(co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_carPag, nd_valCarPag)";
                        strSQL+=" VALUES (";
                        strSQL+="  " + objParSis.getCodigoEmpresa(); //co_emp
                        strSQL+=", " + objParSis.getCodigoLocal(); //co_loc
                        strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                        strSQL+=", " + txtCodDoc.getText(); //co_doc
                        strSQL+=", " + intUltRegCar; //co_reg
                        strSQL+=", " + objTblModImp32_01.getValueAt(i, objImp32_01.INT_TBL_DAT_COD_CAR_PAG_IMP); //co_carpag
                        strSQL+=", " + objVarValTmp; //nd_valcarpag
                        strSQL+=");";
                        strSQLIns+=strSQL;                            
                    }
                }
                System.out.println("actualizar_tbmCarPagNotPedImp: " + strSQLIns);
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
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        boolean blnOptCreCtaPas=false;//SI ES TRUE CREA CUENTA  DE ACTIVO Y PASIVO
        objCodSegInsAnt=null;
        java.util.Date datTmp;
        boolean blnResAct=false; 
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                /* Actualizar: Sólo Fechas*/
                if(strEstNotPedLis.equals("S")){
                    if(chkModSolFecEmbArr.isSelected()){
                        if(actualizar_tbmCabNotPedImp_FecEmbArr()){
                            blnResAct=true;
                            blnRes=true;
                        }
                        else
                            con.rollback();
                    }
                }
                else
                {
                    if(chkCreCta.isSelected())
                        blnOptCreCtaPas=true;

                    //<editor-fold defaultstate="collapsed" desc="/* Documento y Diario Reversión - NP y PE (Información que está en la base de datos y no en el formulario) */">
                    System.out.println("----------------------------------------------------------------------------------------");
                    if (insertar_tbmCabNotPedImp_RV()){
                        if(insertar_tbmDetNotPedImp_RV()){
                            if(insertar_tbmCarPagNotPedImp_RV()){
                                if(actualizar_tbmCabTipDoc_RV()){
                                    if(generaAsiDiaRevNP()){
                                        //Asignar fecha actual
                                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                        datTmp=objUti.parseDate((objUti.formatearFecha(datFecAux,"dd/MM/yyyy")),"dd/MM/yyyy");
                                        if(objAsiDiaRev.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                            if(getCodSegNotPedPreImp_DB()){
                                                if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 1, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
                                                                                   , 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                    blnResAct=true;
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
                    //</editor-fold>
                    
                    //<editor-fold defaultstate="collapsed" desc="/* Documento y Diario Nuevo NP (Con Cambios Realizados) */">
                    System.out.println("***************************************************************************************************");
                    if (insertar_tbmCabNotPedImp()){
                        if(insertar_tbmDetNotPedImp()){
                            if(insertar_tbmCarPagNotPedImp()){
                                if(actualizar_tbmCabTipDoc()){
                                    if(modifica_tbrDetConIntCarPagInsImp()){
                                        if(generaAsiDiaNue()){
                                            //Asignar fecha actual
                                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                                            //datTmp=objUti.parseDate((objUti.formatearFecha(datFecAux,"dd/MM/yyyy")),"dd/MM/yyyy");
                                            if(objAsiDiaNue.insertarDiario(con, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"),"dd/MM/yyyy"))){
                                                if(getCodSegNotPedPreImp_DB()){
                                                    if(objSegMovInv.setSegMovInvCompra(con, objCodSegInsAnt, 1, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null
                                                    , 8, Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), null)){
                                                        if(actualizar_tbmCabNotPedPreImp()){
                                                            if(calculaISDCarPag()){
                                                                
                                                                blnResAct=true;
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
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();   
                    //</editor-fold>
                }
                
                if(blnResAct==true){
                    con.commit();
                    blnRes=true;
                    enviarCorElePreVta(false);
                    if(isChangeFecha()){
                        enviarCorEleDptoVta();
                    }
//                    /* JM: 6/Sept/2022 Agregado para que trabaje con GP */
//                    if(actualizarDatosGP(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(),  Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumPedOri.getText())){
//                        //con.commit();
//                        mostrarMsgInf("Pedido plantado en GP");
//                    }
                     
                }                
            }
            con.close();
            con=null;
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
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabNotPedImp(){
        boolean blnRes=true;
        String strFecUltMod="";
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()); 

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabnotpedimp";
                strSQL+=" SET ne_numdoc=" + objUti.codificar(txtNumDoc.getText(),2) + "";
                strSQL+=" , tx_numdoc2=" + objUti.codificar(txtVerNumPed.getText()) + "";
                strSQL+=" , ne_tipnotped='"+( ( (optTmFob.isSelected())?"1":( (optTmCfr.isSelected())?"2":((optPzaFob.isSelected())?"3":((optPzaCfr.isSelected())?"4":"")))) )+"'";//ne_tipnotped
                 
//                if(optTmFob.isSelected())
//                    strSQL+=", ne_tipnotped='1'";//ne_tipnotped
//                else if(optTmCfr.isSelected())
//                    strSQL+=", ne_tipnotped='2'";//ne_tipnotped
//                else if(optPzaFob.isSelected())
//                    strSQL+=", ne_tipnotped='3'"; //ne_tipnotped
//                else if(optPzaCfr.isSelected())
//                    strSQL+=", ne_tipnotped='4'";//ne_tipnotped
                
                strSQL+=", co_imp=" + objUti.codificar(txtCodImp.getText()) + "";
                strSQL+=", co_prv=" + objUti.codificar(txtCodPrv.getText()) + "";
                strSQL+=", co_exp=" + objUti.codificar(txtCodExp.getText()) + "";
                strSQL+=", co_ciupueemb=" + objUti.codificar(txtCodPue.getText()) + "";
                strSQL+=", co_seg=" + objUti.codificar(txtCodSeg.getText()) + "";
                strSQL+=", co_forpag=" + objUti.codificar(txtCodForPag.getText()) + "";
                strSQL+=", ne_aniemb=" + objUti.codificar(txtAniEmb.getText()) + "";
                strSQL+=", co_mesemb=" + objUti.codificar(txtCodMesEmb.getText()) + "";
                strSQL+=", co_mespue=" + objUti.codificar(txtCodMesPue.getText()) + "";
                strSQL+=", co_mesarr=" + objUti.codificar(txtCodMesArr.getText()) + "";
                strSQL+=", fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", tx_tipemp=" + objUti.codificar(txtEmpExp.getText()) + "";
                strSQL+=", nd_valdoc=" + objUti.codificar((objUti.isNumero(txtValDoc.getText())?"" + (intSig*Double.parseDouble(txtValDoc.getText())):"0"),3) + "";
                strSQL+=", nd_pestotkgr=" + objUti.codificar(txtPesTotKgr.getText()) + "";
                strSQL+=", st_notpedlis='"+(chkNotPedLis.isSelected()?"S":"N")+"'"; //st_notpedlis
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText()) + "";
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText()) + "";
                strSQL+=", fe_ultmod='" + strFecUltMod + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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

    /**
     * Esta función permite actualizar las fechas de la nota de pedido
     * @return true: Si se pudo actualizar las fechas 
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabNotPedImp_FecEmbArr(){
        boolean blnRes=true;
        String strFecUltMod="";
        try{
            if (con!=null){
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());

                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabnotpedimp";
                strSQL+=" SET ";
                strSQL+="  ne_aniemb=" + objUti.codificar(txtAniEmb.getText()) + "";
                strSQL+=", co_mesemb=" + objUti.codificar(txtCodMesEmb.getText()) + "";
                strSQL+=", co_mesarr=" + objUti.codificar(txtCodMesArr.getText()) + "";
                strSQL+=", co_mespue=" + objUti.codificar(txtCodMesPue.getText()) + "";
                strSQL+=", fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_emb     
                strSQL+=", fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_pue       
                strSQL+=", fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_arr      
                strSQL+=", fe_ultmod='" + strFecUltMod + "'";
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmDetNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_detNotPedImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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

    /**
     * Esta función permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCarPagNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_carPagNotPedImp";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        int intCodEmp;
        boolean blnRes=true;
        try{
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="      , a1.co_usrIng, a2.tx_usr AS tx_nomUsrIng, a1.co_usrMod, a3.tx_usr AS tx_nomUsrMod, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";
                strSQL+=" FROM ( (tbm_cabNotPedImp AS a1 INNER JOIN tbm_usr AS a2 ON a1.co_usrIng=a2.co_usr) INNER JOIN tbm_usr AS a3 ON a1.co_usrMod=a3.co_usr )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_detNotPedImp AS a6 ON (a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc)";
                strSQL+=" INNER JOIN tbm_inv AS a7 ON (a1.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                if(!blnVenConEme){ 
                    strSQL+=" AND a7.tx_codAlt like '%I'"; //Mostrar Solo items con Terminal I
                } 
                
                if(!chkMosTodVerPed.isSelected()){ //Filtro: Oculta o muestra todas las versiones del pedido
                    strSQL+=" AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL) "; //Sólo mostrará la última versión del pedido. No mostrará ni la inactiva, ni la de reversión.
                }
                                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_imp = " + strAux + "";
                strAux=txtCodPrv.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_prv = " + strAux + "";
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_exp = " + strAux + "";
                strAux=txtCodPue.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_ciupueemb = " + strAux + "";
                strAux=txtCodSeg.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_seg = " + strAux + "";
                
                strAux=txtCodPag.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_forpag = " + strAux + "";
                
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                if (dtpFecEmb.isFecha())
                    strSQL+=" AND a1.fe_emb='" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                
                if (dtpFecPue.isFecha())
                    strSQL+=" AND a1.fe_pue='" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";        
                if (dtpFecArr.isFecha())
                    strSQL+=" AND a1.fe_arr='" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                     
                
                strAux=txtCodMesEmb.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_mesemb = " + strAux + "";
                strAux=txtCodMesArr.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_mesarr = " + strAux + "";        
                strAux=txtCodMesPue.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_mespue = " + strAux + "";     
                
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                
                strAux=txtNumPedOri.getText();
                strSQL+=" AND (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                
                strAux=txtVerNumPed.getText();
                if (!strAux.equals("")){
                    strSQL+=" AND (  UPPER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+="        OR LOWER(a1.tx_numdoc2) LIKE '%" + strAux.replaceAll("'", "''") + "%'";
                    strSQL+=" )";
                }
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="        , a1.co_usrIng, a2.tx_usr, a1.co_usrMod, a3.tx_usr, a1.co_imp, a1.co_ctaAct, a1.co_ctaPas";                
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatConNotPed = new ArrayList();
                while(rst.next())
                {
                    arlRegConNotPed = new ArrayList();
                    arlRegConNotPed.add(INT_ARL_CON_COD_EMP,   rst.getInt("co_emp"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_LOC,   rst.getInt("co_loc"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_TIP_DOC,rst.getInt("co_tipDoc"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_DOC,   rst.getInt("co_doc"));
                    arlRegConNotPed.add(INT_ARL_CON_TXT_USRING,rst.getString("tx_nomUsrIng"));
                    arlRegConNotPed.add(INT_ARL_CON_TXT_USRMOD,rst.getString("tx_nomUsrMod"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_IMP,   rst.getString("co_imp"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_CTAACT,rst.getString("co_ctaAct"));
                    arlRegConNotPed.add(INT_ARL_CON_COD_CTAPAS,rst.getString("co_ctaPas"));
                    arlDatConNotPed.add(arlRegConNotPed);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
                
                if(arlDatConNotPed.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConNotPed.size()) + " registros");
                    intIndiceNotPed=arlDatConNotPed.size()-1;
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
                if (cargarCarPagImp()){
                    if (cargarDetRegItmVal()){
                        objImp32_02.setBlnHayCamCamImp32_02(false);
                        objImp32_01.setBlnHayCamCamImp32_01(false);
                        //se debe llamar a la funcion de PEDIDOS EMBARCADOS
                        if(cargarDetRegPedEmb()){
                            if(cargarCabDocAso()){
                                if(getCodigoLocalPredeterminado(Integer.parseInt(txtCodImp.getText()))){
                                    if(objAsiDia.consultarDiario(Integer.parseInt(txtCodImp.getText()), Integer.parseInt(strCodLocPre), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()) )){
                                        blnRes=true;
                                    }
                                }
                            }
                        }
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
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_colPed, a1.fe_pro, a1.fe_emb, a1.fe_pue, a1.fe_arr, a1.ne_numdoc, a1.tx_numdoc2, a1.ne_tipnotped";
                strSQL+="      , a1.co_imp, a1.co_prv, a1.co_exp, a1.co_ciupueemb, a1.co_seg, a1.co_forpag, a1.co_mesemb, a1.co_mesarr";
                strSQL+="      , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_notpedlis, a1.st_imp, a1.tx_obs1";
                strSQL+="      , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                strSQL+="      , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                strSQL+="      , a4.co_prv, a4.tx_nom AS tx_nomPrvExt";
                strSQL+="      , a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp";
                strSQL+="      , a6.co_ciu, a6.tx_deslar AS tx_nomPueEmb";
                strSQL+="      , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                strSQL+="      , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                strSQL+="      , a9.co_mesemb, a9.tx_deslar AS tx_nomMesEmb"; //Embarque
                strSQL+="      , a10.co_mesemb AS co_mesArr, a10.tx_deslar AS tx_nomMesArr"; //Arribo
                strSQL+="      , a11.co_mesemb AS co_mesPue, a11.tx_deslar AS tx_nomMesPue"; //Puerto
                strSQL+="      , a1.ne_aniemb, a1.co_ctaAct, a1.co_ctaPas, ne_verNumPed";
                strSQL+="      , (CASE WHEN a1.tx_numdoc3 IS NOT NULL THEN a1.tx_numdoc3 ELSE a1.tx_numdoc2 END) AS tx_numPed, a1.st_doc, a1.tx_modPed";
                strSQL+=" FROM tbm_cabNotPedImp AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                strSQL+=" INNER JOIN tbm_prvExtImp AS a4 ON(a1.co_prv=a4.co_prv)";
                strSQL+=" INNER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                strSQL+=" LEFT OUTER JOIN tbm_ciu AS a6 ON(a1.co_ciupueemb=a6.co_ciu)";
                strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a9  ON(a1.co_mesemb=a9.co_mesemb)";  //mes embarque
                strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a10 ON(a1.co_mesArr=a10.co_mesemb)"; //mes arribo
                strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a11 ON(a1.co_mesPue=a11.co_mesemb)"; //mes puerto
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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
                    
                    strAux=rst.getString("co_imp");
                    txtCodImp.setText((strAux==null)?"":strAux);
                    strCodImpAnt = ((strAux==null)?"":strAux);  /* Se captura el código de importador, para obtener el anterior importador, previo a alguna modificación. */           
                    
                    strAux=rst.getString("tx_nomImp");
                    txtNomImp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_prv");
                    txtCodPrv.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomPrvExt");
                    txtNomPrv.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_exp");
                    txtCodExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomExp");
                    txtNomExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_aliExp");
                    txtNomExp2.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_tipemp");
                    txtEmpExp.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_ciupueemb");
                    txtCodPue.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomPueEmb");
                    txtNomPue.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_seg");
                    txtCodSeg.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomSeg");
                    txtNomSeg.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("co_forpag");
                    txtCodForPag.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomForPag");
                    txtNomForPag.setText((strAux==null)?"":strAux);

                    strAux=rst.getString("co_forpag");
                    txtCodPag.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomForPag");
                    txtNomPag.setText((strAux==null)?"":strAux);

                    dtpFecDoc.setText(""); 
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    dtpFecColPed.setText(""); 
                    dtpFecColPed.setText(objUti.formatearFecha(rst.getDate("fe_colPed"),"dd/MM/yyyy"));

                    dtpFecProPed.setText(""); 
                    dtpFecProPed.setText(objUti.formatearFecha(rst.getDate("fe_pro"),"dd/MM/yyyy"));                        
                    
                    dtpFecEmb.setText(""); 
                    dtpFecEmb.setText(objUti.formatearFecha(rst.getDate("fe_emb"),"dd/MM/yyyy"));    

                    dtpFecPue.setText(""); 
                    dtpFecPue.setText(objUti.formatearFecha(rst.getDate("fe_pue"),"dd/MM/yyyy"));    
                    
                    dtpFecArr.setText(""); 
                    dtpFecArr.setText(objUti.formatearFecha(rst.getDate("fe_arr"),"dd/MM/yyyy"));         
                    
                    strAux=rst.getString("ne_aniemb");
                    txtAniEmb.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_mesemb");
                    txtCodMesEmb.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomMesEmb");
                    txtNomMesEmb.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_mesPue");
                    txtCodMesPue.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomMesPue");
                    txtNomMesPue.setText((strAux==null)?"":strAux);                              
                    
                    strAux=rst.getString("co_mesArr");
                    txtCodMesArr.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomMesArr");
                    txtNomMesArr.setText((strAux==null)?"":strAux);
                    
                    strFecEmbIni=rst.getString("tx_nomMesEmb");
                    strFecArrIni=rst.getString("tx_nomMesArr");                    
                    
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);                    

                    strAux=rst.getString("tx_numdoc2");
                    txtVerNumPed.setText((strAux==null)?"":strAux);

                    txtVerPed.setText(rst.getObject("ne_verNumPed")==null?"":rst.getString("ne_verNumPed"));
                    txtNumPedOri.setText(rst.getObject("tx_numPed")==null?"":rst.getString("tx_numPed"));                     

                    strAux=rst.getObject("nd_valdoc")==null?"0":(rst.getString("nd_valdoc").equals("")?"0":rst.getString("nd_valdoc"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst.getObject("nd_pestotkgr")==null?"0":(rst.getString("nd_pestotkgr").equals("")?"0":rst.getString("nd_pestotkgr"));
                    txtPesTotKgr.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));

                    strAux=rst.getObject("st_notPedLis")==null?"":rst.getString("st_notPedLis");
                    strEstNotPedLis=strAux;
                    if(strAux.equals("S"))
                        chkNotPedLis.setSelected(true);
                    else
                        chkNotPedLis.setSelected(false);

                    strAux=rst.getString("ne_tipnotped");
                    if(strAux.equals("1")){
                        optTmFob.setSelected(true);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }                        
                    else if(strAux.equals("2")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(true);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("3")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(true);
                        optPzaCfr.setSelected(false);
                    }
                    else if(strAux.equals("4")){
                        optTmFob.setSelected(false);
                        optTmCfr.setSelected(false);
                        optPzaFob.setSelected(false);
                        optPzaCfr.setSelected(true);
                    }

                    txaObs1.setText(rst.getObject("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    txaObs2.setText(rst.getObject("tx_obs2")==null?"":rst.getString("tx_obs2"));
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                              
                    chkCreCta.setSelected(false);
                    strAux=(rst.getObject("co_ctaAct")==null?"":rst.getString("co_ctaAct"));
                    if(! strAux.equals("")){
                        chkCreCta.setSelected(true);
                    }
                    strAux=(rst.getObject("co_ctaPas")==null?"":rst.getString("co_ctaPas"));
                    if(!strAux.equals("")){
                        chkCreCta.setSelected(true);
                    }
                    
                    strEstDocOri="";
                    strEstDocOri=rst.getObject("st_doc")==null?"A":rst.getString("st_doc");
                    
                    strAux=rst.getString("tx_modPed");
                    txtModPed.setText((strAux==null)?"":strAux);
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
                
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL+=" , b1.co_ctaAct, b2.tx_codCtaAct, b2.tx_nomCtaAct";
                strSQL+=" , b1.co_ctaPas, b3.tx_codCtaPas, b3.tx_nomCtaPas";
                strSQL+=" FROM tbm_cabNotPedImp AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaAct, a1.tx_desLar AS tx_nomCtaAct";
                strSQL+=" 	FROM tbm_plaCta AS a1";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_imp=b2.co_emp AND b1.co_ctaAct=b2.co_cta";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta AS tx_codCtaPas, a1.tx_desLar AS tx_nomCtaPas";
                strSQL+=" 	FROM tbm_plaCta AS a1";
                strSQL+=" ) AS b3";
                strSQL+=" ON b1.co_imp=b3.co_emp AND b1.co_ctaPas=b3.co_cta";
                strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC) + "";
                strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC) + "";
                //System.out.println("CUENTAS ACTIVO/PASIVO: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodCtaPedAct=rst.getInt("co_ctaAct");
                    intCodCtaPedActIns=rst.getInt("co_ctaAct");
                    strNumCtaActAsiDia=rst.getString("tx_codCtaAct");
                    strNomCtaActAsiDia=rst.getString("tx_nomCtaAct");
                    intCodCtaPedPas=rst.getInt("co_ctaPas");
                    intCodCtaPedPasIns=rst.getInt("co_ctaPas");
                    strNumCtaPasAsiDia=rst.getString("tx_codCtaPas");
                    strNomCtaPasAsiDia=rst.getString("tx_nomCtaPas");
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceNotPed+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConNotPed.size()) );
            
            strAux=objParSis.getNombreMenu();
            if(strEstDocOri.equals("A")){
                this.setTitle(strAux + strVer + "(ACTIVO)");
                lblTit.setText(strAux + "(ACTIVO)");
            }
            if(strEstDocOri.equals("R")){
                this.setTitle(strAux + strVer + "(REVERSIÓN)");
                lblTit.setText(strAux + "(REVERSIÓN)");
            }            
            if(strEstDocOri.equals("I")){
                this.setTitle(strAux + strVer + "(INACTIVO)");
                lblTit.setText(strAux + "(INACTIVO)");
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetRegItmVal(){
        boolean blnRes=true;
        try{
            objTblModImp32_02.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="      , a8.co_exp, a8.tx_nom as tx_nomExp, a5.co_parara, a5.tx_par, a5.tx_des, a2.tx_desCor as tx_uniMed";
                    strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                    strSQL+="      , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                    strSQL+="      , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                    strSQL+="      , CASE WHEN a1.nd_pesitmkgr > 0 THEN ROUND((b1.nd_cosUni/a1.nd_pesitmkgr),2) ELSE 0 END as nd_cosKgr";
                    strSQL+="      , b1.nd_valISD, b1.st_impISDCreTri";
                    strSQL+=" FROM tbm_detnotpedimp AS b1 INNER JOIN";
                    strSQL+=" (";
                    strSQL+=" 	((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                    strSQL+=" 	  LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                    strSQL+=" 	)";
                    strSQL+=" 	LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                    strSQL+=" 		 INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra";
                    strSQL+=" 	) ON a1.co_grpImp=a4.co_grp";
                    strSQL+=" )";
                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+=" INNER JOIN tbm_cabnotpedimp AS a7 ON(b1.co_emp=a7.co_emp AND b1.co_loc=a7.co_loc AND b1.co_tipDoc=a7.co_tipDoc AND b1.co_doc=a7.co_doc)";
                    strSQL+=" INNER JOIN tbm_expImp AS a8 ON(b1.co_exp=a8.co_exp)";
                    strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                    strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="        , a8.co_exp, a8.tx_nom, a5.co_parara, a5.tx_par, a5.tx_des, a2.tx_desCor, b1.nd_ara";
                    strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                    strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                    strSQL+="        , b1.nd_valISD, b1.st_impISDCreTri";
                    strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                    System.out.println("cargarDetRegItmVal: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp32_02.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itmMae"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_ITM,        rst.getString("co_itm"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(objImp32_02.INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_UNI_MED,        rst.getString("tx_uniMed"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_PES,            rst.getString("nd_pesitmkgr"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_ARA,        rst.getString("co_parara"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_NOM_ARA,        rst.getString("tx_par"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_DES_ARA,        rst.getString("tx_des"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_POR_ARA,        rst.getString("nd_ara"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COD_EXP,        rst.getString("co_exp"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_NOM_EXP,        rst.getString("tx_nomExp"));      
                        vecReg.add(objImp32_02.INT_TBL_DAT_BUT_EXP,        null);                        
                        vecReg.add(objImp32_02.INT_TBL_DAT_CAN_TON_MET,    rst.getString("nd_canTonMet"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_CAN_PZA,        rst.getString("nd_can"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_PRE_UNI,        rst.getString("nd_preUni"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_TOT_FOB_CFR,    rst.getString("nd_valTotFobCfr"));                        
                        vecReg.add(objImp32_02.INT_TBL_DAT_VAL_FLE,        rst.getString("nd_valFle"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_VAL_CFR,        rst.getString("nd_valCfr"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_VAL_CFR_ARA,    "0");
                        vecReg.add(objImp32_02.INT_TBL_DAT_VAL_ISD,        rst.getString("nd_valISD"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_CHK_ISD_CRE_TRI,null);                              
                        vecReg.add(objImp32_02.INT_TBL_DAT_TOT_ARA,        rst.getString("nd_valTotAra"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_TOT_GAS,        rst.getString("nd_valTotGas"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_TOT_COS,        rst.getString("nd_valTotCos"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_COS_KGR,        rst.getString("nd_cosKgr"));
                        vecReg.add(objImp32_02.INT_TBL_DAT_REV_ITM,        "");
                        
                        String strChkISD=rst.getObject("st_impISDCreTri")==null?"N":(rst.getString("st_impISDCreTri").equals("")?"N":rst.getString("st_impISDCreTri"));
                        if(strChkISD.equals("S")){
                            vecReg.setElementAt( new Boolean(true), objImp32_02.INT_TBL_DAT_CHK_ISD_CRE_TRI);
                        }                            
                        
                        vecDat.add(vecReg);
                    }
                    
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp32_02.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
                    objImp32_02.regenerarCalculoCfrAra();
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCarPagImp(){
        boolean blnRes=true;
        BigDecimal bgdValFleSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValAraSav=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValGasSav=new BigDecimal(BigInteger.ZERO);
        String strTipCarPag="";
        try{
            objTblModImp32_01.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carpag, a1.nd_valcarpag";
                    strSQL+="      , a2.tx_nom AS tx_nomCarPag, a2.tx_tipcarpag, (CASE WHEN a2.st_pagExt IS NULL THEN 'N' ELSE a2.st_pagExt END ) AS st_pagExt";
                    strSQL+=" FROM tbm_carPagnotpedimp AS a1 INNER JOIN tbm_carPagImp AS a2";
                    strSQL+=" ON a1.co_carpag=a2.co_carpag";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                    strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                    strSQL+=" ORDER BY a2.ne_ubi";
                    //System.out.println("cargarCarPagImp: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(objImp32_01.INT_TBL_DAT_LIN,"");
                        vecReg.add(objImp32_01.INT_TBL_DAT_TIP_CAR_PAG_IMP,    rst.getString("tx_tipcarpag"));
                        vecReg.add(objImp32_01.INT_TBL_DAT_COD_CAR_PAG_IMP,    rst.getString("co_carpag"));
                        vecReg.add(objImp32_01.INT_TBL_DAT_NOM_CAR_PAG_IMP,    rst.getString("tx_nomCarPag"));
                        vecReg.add(objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP,    rst.getString("nd_valcarpag"));
                        vecReg.add(objImp32_01.INT_TBL_DAT_VAL_CAR_PAG_IMP_AUX,rst.getString("nd_valcarpag"));
                        vecReg.add(objImp32_01.INT_TBL_DAT_TIP_NOT_PED,        "");

                        if(optTmFob.isSelected())
                            vecReg.setElementAt("1", objImp32_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optTmCfr.isSelected())
                            vecReg.setElementAt("2", objImp32_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaFob.isSelected())
                            vecReg.setElementAt("3", objImp32_01.INT_TBL_DAT_TIP_NOT_PED);
                        else if(optPzaCfr.isSelected())
                            vecReg.setElementAt("4", objImp32_01.INT_TBL_DAT_TIP_NOT_PED);

                        strTipCarPag=rst.getObject("tx_tipcarpag")==null?"":rst.getString("tx_tipcarpag");
                        if(strTipCarPag.equals("F")){
                            bgdValFleSav=bgdValFleSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        else if(strTipCarPag.equals("A")){
                            bgdValAraSav=bgdValAraSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }

                        else if(strTipCarPag.equals("G")){
                            bgdValGasSav=bgdValGasSav.add(new BigDecimal(rst.getObject("nd_valcarpag")==null?"0":(rst.getString("nd_valcarpag").equals("")?"0":rst.getString("nd_valcarpag"))));
                        }
                        vecReg.add(objImp32_01.INT_TBL_DAT_TIP_PAG_EXT,rst.getString("st_pagExt"));
                        vecDat.add(vecReg);
                    }

                    objImp32_01.setFleteItem(bgdValFleSav);
                    objImp32_01.setDerechosArancelarios(bgdValAraSav);
                    objImp32_01.setTotalGastos(bgdValGasSav);

                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    if(objImp32_01.asignarVectorModeloDatos(vecDat)){
                        vecDat.clear();
                    }
                    else{
                        mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
                    }
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
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if (txtNomImp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Importador</FONT> es obligatorio.<BR>Seleccione un Importador y vuelva a intentarlo.</HTML>");
            txtNomImp.requestFocus();
            return false;
        }
        if (txtNomPrv.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Proveedor</FONT> es obligatorio.<BR>Seleccione un Proveedor y vuelva a intentarlo.</HTML>");
            txtNomPrv.requestFocus();
            return false;
        }
        if (txtNomExp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Exportador</FONT> es obligatorio.<BR>Seleccione un Exportador y vuelva a intentarlo.</HTML>");
            txtNomExp.requestFocus();
            return false;
        }
        
        /* Fecha de Embarque */
        if(!dtpFecEmb.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de embarque</FONT> es obligatorio.<BR>Ingrese la fecha de embarque y vuelva a intentarlo.</HTML>");
            dtpFecEmb.requestFocus();
            return false;
        }
        else{
            /* Se asigna la fecha al TextField*/
            int FecEmb[] =  dtpFecEmb.getFecha(dtpFecEmb.getText());  
            txtAniEmb.setText(""+(FecEmb[2]));
            txtCodMesEmb.setText(""+((FecEmb[1]*2)-1));
        }
        
        /* Fecha de Puerto */
        if(!dtpFecPue.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de puerto</FONT> es obligatorio.<BR>Ingrese la fecha de puerto y vuelva a intentarlo.</HTML>");
            dtpFecPue.requestFocus();
            return false;
        }     
        else{
            /* Se asigna la fecha al TextField*/
            int FecPue[] =  dtpFecPue.getFecha(dtpFecPue.getText()); 
            txtCodMesPue.setText(""+((FecPue[1]*2)-1));
        }        
        
        /* Fecha de Arribo */
        if(!dtpFecArr.isFecha()){ 
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de arribo</FONT> es obligatorio.<BR>Ingrese la fecha de arribo y vuelva a intentarlo.</HTML>");
            dtpFecArr.requestFocus();
            return false;
        }  
        else{
            /* Se asigna la fecha al TextField*/
            int FecArr[] =  dtpFecArr.getFecha(dtpFecArr.getText());  
            txtCodMesArr.setText(""+((FecArr[1]*2)-1));
        }       
        
        
        /* Fecha de Producción */
        if(!dtpFecProPed.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de producción</FONT> es obligatorio.<BR>Ingrese la fecha de producción y vuelva a intentarlo.</HTML>");
            dtpFecProPed.requestFocus();
            return false;
        }     

//        if (txtNomMesEmb.getText().equals("")){
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes de embarque</FONT> es obligatorio.<BR>Seleccione el Mes de embarque y vuelva a intentarlo.</HTML>");
//            txtNomMesEmb.requestFocus();
//            return false;
//        }
//        
//        if (txtAniEmb.getText().equals("")){
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Año de embarque</FONT> es obligatorio.<BR>Escriba el año de embarque y vuelva a intentarlo.</HTML>");
//            txtAniEmb.requestFocus();
//            return false;
//        }
//        
//        if (txtNomMesArr.getText().equals("")){
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes de arribo</FONT> es obligatorio.<BR>Seleccione el mes de arribo y vuelva a intentarlo.</HTML>");
//            txtNomMesArr.requestFocus();
//            return false;
//        }      
//        
//        if (txtNomMesPue.getText().equals("")){
//            tabFrm.setSelectedIndex(0);
//            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Mes de puerto</FONT> es obligatorio.<BR>Seleccione el mes de puerto y vuelva a intentarlo.</HTML>");
//            txtNomMesPue.requestFocus();
//            return false;
//        }  
        
        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Seleccione un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }

        if (txtVerNumPed.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> es obligatorio.<BR>Escriba un Número de pedido y vuelva a intentarlo.</HTML>");
            txtVerNumPed.requestFocus();
            return false;
        }
        
        if(objTooBar.getEstado()=='n'){
            if( isExisteNumeroPedido(txtNumPedOri.getText()) ) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de pedido</FONT> está repetido.<BR>Escriba un Número de pedido diferente y vuelva a intentarlo.</HTML>");
                txtNumPedOri.requestFocus();
                txtNumPedOri.selectAll();
                return false;
            } 
        }
   
        
        //Valida se ingrese exportador por detalle
        /*Se agrega esta linea para que cada vez que siempre el exportador de la cabecera sea el mismo del detalle.
          Cuando se desee elegir el exportador por detalle, se debe comentar esta linea */
        objImp32_02.seteaColumnaExportador(Integer.parseInt(txtCodExp.getText()), txtNomExp.getText());
        
        for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
            String strCodExp=objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_EXP)==null?"":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_EXP).toString().equals("")?"":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_EXP).toString());
            if(strCodExp.equals("")){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Existe uno o varios items sin <FONT COLOR=\"blue\">exportador</FONT> y es obligatorio.<BR>Seleccione un exportador y vuelva a intentarlo.</HTML>");
                return false;                    
            }
        }        
        
        //Validar que se ingresen solo pedidos con items Terminal 'I' de Importaciones
        if(isExisteItemTerminalDiferente()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existe uno o varios items cuyo terminal es diferente a 'I'<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }        

        if(objTooBar.getEstado()=='n'){
            if(!objTblModImp32_01.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Cargos a Pagar no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(!objTblModImp32_02.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblModImp32_02.getRowCountTrue()<=0){
                objTblModImp32_02.setModoOperacion(objTblModImp32_02.INT_TBL_EDI);
                objTblModImp32_02.removeEmptyRows();
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                objImp32_02.setEditable(true);
                return false;
            }
        }

        if( (objTooBar.getEstado()=='x')  || (objTooBar.getEstado()=='m') ){
            if(!chkModSolFecEmbArr.isSelected()){
                if(strEstNotPedLis.equals("S")){//si la nota de pedido esta lista
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>La Nota de Pedido ya fue marcada como lista.<BR>No se puede modificar una Nota de Pedido que ya fue marcada como <FONT COLOR=\"red\">Lista</FONT>.</HTML>");
                    return false;
                }
            }
        }
        
        //si es TM-FOB o TM-CFR deben tener datos cada fila de las piezas, en PZA-FOB o PZA-CFR no deben tener valores.
        BigDecimal bgdCanPie=new BigDecimal("0");
        if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
            for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                bgdCanPie=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString()));
                
                if(bgdCanPie.compareTo(new BigDecimal("0"))<=0){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El valor de la(s) <FONT COLOR=\"blue\">pieza(s)</FONT> es obligatorio.<BR>Escriba el(los) valor(es) y vuelva a intentarlo.</HTML>");
                    return false;                    
                }
            }
        }
        

        

//        //se coloca el valor de cantidad de nota de pedido en la pieza, antes ese valor no se ingresaba, ahora si porque para generar el 
//        //ingreso por importacion se usa el valor de la pieza y no valor de NotaPedido, PedidoEmbarcado, sino el valor de piezas.
//        //la diferencia entre los tipos es la edicion de la columna de piezas y la obligatoriedad de q se ingresen.
//        BigDecimal bgdCanNotPed=new BigDecimal("0");
//        if( (optPzaFob.isSelected())  || (optPzaCfr.isSelected())  ){
//            for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
//                bgdCanNotPed=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString()));
//                objTblModImp32_02.setValueAt(bgdCanNotPed, i, objImp32_02.INT_TBL_DAT_CAN_PZA);                
//            }
//        }
        
        
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        
        if ((objUti.redondearBigDecimal(objImp32_02.getValorTotalArancel(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp32_01.getValorTotalArancel(), objParSis.getDecimalesMostrar())))!=0){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El valor de Total de Aranceles del Cargo a Pagar es diferente al valor de Total de Aranceles del detalle.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        /* Valida que el valor de factura sea igual al valor de la Columna Tot.Fob/Cfr. */
        if(objImp32_01.getValorTotalPagoExterior().compareTo(BigDecimal.ZERO)<=0){ /* Valida que siempre se asigne un valor de la factura. */
            mostrarMsgInf("<HTML>Ingresar el valor de la factura en cargos a pagar.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;
        }   
        else{
            /* Se agrega rango permitido de 0.80 hasta 1.20 (20% indicado por Alfredo Paulson)*/
            BigDecimal bgdRanPer=objUti.redondearBigDecimal((objUti.redondearBigDecimal(objImp32_01.getValorTotalPagoExterior(), objParSis.getDecimalesMostrar())).divide((objUti.redondearBigDecimal(objImp32_02.getValorTotalFactura(), objParSis.getDecimalesMostrar())), BigDecimal.ROUND_HALF_UP) , objParSis.getDecimalesMostrar());
            //if ((objUti.redondearBigDecimal(objImp32_02.getValorTotalFactura(), objParSis.getDecimalesMostrar())).compareTo((objUti.redondearBigDecimal(objImp32_01.getValorTotalPagoExterior(), objParSis.getDecimalesMostrar())))!=0){
            if( !  ( (bgdRanPer.compareTo(new BigDecimal("0.80")) >= 0)  &&  (bgdRanPer.compareTo(new BigDecimal("1.20")) <= 0)  ) )  { 
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El valor de Total de Factura del Cargo a Pagar es diferente al Valor de Tot.FOB/CFR del detalle + Gastos Origen.<BR>Verifique y vuelva a intentarlo.</HTML>");
                return false;
            }                         
        }
        
        //Validar rando de piezas cuando es pedido por tonelada.
        if(!validarRangoPzas()) { 
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existen items fuera del rango "+bgdMin+" - "+bgdMax+" en la cantidad de piezas.<BR>Verifique y vuelva a intentarlo.</HTML>");
            objTblModImp32_02.fireTableDataChanged();
            return false; 
        }
        
        objImp32_02.regenerarCalculos();
        
        //enviarCorElePreVta(); //solo se la llama.
        
        return true;
    }
    
    /**
     * Función que valida en cada fila que la cantidad ingresada en la columna "Can.Pza." no sea distinta al rango permitido
     * El rango minimo es 0.95 y el rango máximo es 1.05 
     * Esta validación se deberá realizar solo cuando es un pedido por Toneladas.
     * @return 
     */
    private boolean validarRangoPzas(){
        boolean blnRes=true;
        try{
            //si es TM-FOB o TM-CFR debe validarse los datos de cada fila de las piezas, antes de guardar
            if( (optTmFob.isSelected())  || (optTmCfr.isSelected())  ){
                for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                    BigDecimal bgdCanPza=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString()));
                    BigDecimal bgdCanTon=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString()));
                    BigDecimal bgdPesKgr=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PES)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PES).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_PES).toString()));
                    //System.out.println("Fila#"+i+" <> bgdCanTon: "+bgdCanTon+" <> bgdCanPza: "+bgdCanPza+" <> bgdPesKgr:"+bgdPesKgr);
                    if(bgdPesKgr.compareTo(BigDecimal.ZERO)<=0){ /* Valida que siempre se asigne un peso al item, antes de ingresar */
                        mostrarMsgInf("<HTML>Existen items sin peso.<BR>Verifique, asigne peso y vuelva a intentarlo.</HTML>");
                        objTblModImp32_02.setValueAt("S", i, objImp32_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                        blnRes=false;
                    }
                    else{ /* Cuando existe un peso en el item, se valida el rango permitido*/
                        BigDecimal bgdCanCal= objUti.redondearBigDecimal( ( ( bgdCanTon.multiply(new BigDecimal("1000")) ).divide(bgdPesKgr, BigDecimal.ROUND_HALF_UP) ).divide(bgdCanPza, BigDecimal.ROUND_HALF_UP), objParSis.getDecimalesMostrar());
                        //System.out.println("cantidadCalculada: "+bgdCanCal);
                        if( (bgdCanCal.compareTo(bgdMin)<0) || (bgdCanCal.compareTo(bgdMax)>0) ) {
                            objTblModImp32_02.setValueAt("S", i, objImp32_02.INT_TBL_DAT_REV_ITM); //Marcar columnas para revisión
                            //System.out.println("Fuera del rango permitido. Fila#"+i+" => bgdCanCal: "+bgdCanCal);
                            blnRes=false;
                        }
                        else{
                            objTblModImp32_02.setValueAt("N", i, objImp32_02.INT_TBL_DAT_REV_ITM); 
                        }                    
                    }
                }
            }
        } 
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;    
    }
    
    /**
     * Función que permite determinar si algún item tiene un terminal que no debe estar asociado al tipo de documento(Ejm: 'I' sólo para INIMPO ; 'S' sólo para INCOLO
     * @return true: Si el terminal del item no corresponde al tipo de documento
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemTerminalDiferente(){
        boolean blnRes=false;
        String strCodAltItmTer="";
        int intCodAltItmTer=0;
        int intTipTerItm_regTblDat_i=0, intTipTerItm_regTblDat_s=0;
        try{
            for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                strCodAltItmTer=objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ALT_ITM)==null?"":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ALT_ITM).toString();
                intCodAltItmTer=(strCodAltItmTer.length()-1);
                
                if(strCodAltItmTer.charAt(intCodAltItmTer) == 'I'){
                    intTipTerItm_regTblDat_i++;
                }
                else if(strCodAltItmTer.charAt(intCodAltItmTer) == 'S'){
                    intTipTerItm_regTblDat_s++;
                }
                
                if( (intTipTerItm_regTblDat_i!=0)  && (intTipTerItm_regTblDat_s!=0) ){
                    break;
                }                
            }
            
            if(intTipTerItm_regTblDat_s>0){//debería ser "cero" porque el INIMPO sólo maneja terminales 'I' y esta variable refleja que hay items con terminales 'S' en la tabla
                blnRes=true;
            }
          
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(anular_tbmCabNotPedImp()){
                    if(anular_tbmPlaCta()){
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(eliminar_tbmCabNotPedImp()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabNotPedImp";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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
     * Esta función permite anular un registro.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmPlaCta(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_CTAACT);
                strSQL+=";";
                strSQL+=" UPDATE tbm_plaCta";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_IMP);
                strSQL+=" AND co_cta=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_CTAPAS);
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
        
    /**
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabNotPedImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL ="";
                strSQL+=" UPDATE tbm_cabNotPedImp";
                strSQL+=" SET st_reg='E'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
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

    
    public JTextField getTxtPesTotKgs() {
        return txtPesTotKgr;
    }

    public void setTxtPesTotKgs(String strPesTotKgs) {
        this.txtPesTotKgr.setText(strPesTotKgs);
    }


    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                System.out.println("actualizarCabTipDoc: " + strSQL);
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

    private boolean cargarDetRegPedEmb(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                objTblModPedEmb.removeAllRows(); //Se limpia el modelo
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb";
                strSQL+=", a1.ne_numdoc, a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp";
                strSQL+=", a1.nd_valdoc, a2.tx_desCor, a2.tx_desLar";
                strSQL+=" FROM (tbm_cabPedEmbImp AS a1";
                strSQL+="       INNER JOIN tbm_detPedEmbImp as a4";
                strSQL+="       ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="       INNER JOIN tbm_detNotPedImp AS a5";
                strSQL+="       ON a4.co_emp=a5.co_emp AND a4.co_locRel=a5.co_loc AND a4.co_tipDocRel=a5.co_tipDoc AND a4.co_docRel=a5.co_doc AND a4.co_regRel=a5.co_reg";
                strSQL+="       INNER JOIN tbm_cabNotPedImp AS a6";
                strSQL+="       ON a5.co_emp=a6.co_emp AND a5.co_loc=a6.co_loc AND a5.co_tipDoc=a6.co_tipDoc AND a5.co_doc=a6.co_doc";
                strSQL+="       )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_imp=a3.co_emp)";
                strSQL+=" WHERE a5.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a5.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a5.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a5.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a6.st_reg NOT IN('I','E') AND a1.st_reg NOT IN('I','E')";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.fe_emb";
                strSQL+=" , a1.ne_numdoc, a3.co_emp, a3.tx_nom";
                strSQL+=" , a1.nd_valdoc, a2.tx_desCor, a2.tx_desLar";
                //System.out.println("cargarDetRegPedEmb: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDatPedEmb.clear();

                while (rst.next()){
                    vecRegPedEmb=new Vector();
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_LIN, "");
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_COD_EMP,             "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_COD_LOC,             "" + rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_COD_TIP_DOC,         "" + rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_DES_COR_TIP_DOC,     "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_DES_LAR_TIP_DOC,     "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_COD_DOC,             "" + rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_NUM_DOC,             "" + rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_FEC_DOC,             "" + rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_COD_IMP,             "" + rst.getObject("co_imp")==null?"":rst.getString("co_imp"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_NOM_IMP,             "" + rst.getObject("tx_nomImp")==null?"":rst.getString("tx_nomImp"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_VAL_DOC,             "" + rst.getObject("nd_valDoc")==null?"":rst.getString("nd_valDoc"));
                    vecRegPedEmb.add(INT_TBL_DAT_PED_EMB_BUT,                 null);
                    vecDatPedEmb.add(vecRegPedEmb);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                con.close();
                con=null;

                //Asignar vectores al modelo.
                objTblModPedEmb.setData(vecDatPedEmb);
                tblPedEmb.setModel(objTblModPedEmb);
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

    private void mostrarFrmPedidoEmbarcado(int fila){
        int intCodTipDoc=Integer.parseInt(objTblModPedEmb.getValueAt(fila, INT_TBL_DAT_PED_EMB_COD_TIP_DOC).toString());
        int intCodDoc=Integer.parseInt(objTblModPedEmb.getValueAt(fila, INT_TBL_DAT_PED_EMB_COD_DOC).toString());
        Compras.ZafCom76.ZafCom76 objCom76=new Compras.ZafCom76.ZafCom76(objParSis, intCodTipDoc, intCodDoc);
        this.getParent().add(objCom76,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCom76.show();
    }

    private boolean mostrarFrmDocumentoAsosiadoCabecera(int fila){
        boolean blnRes=true;
        int intCodEmp=-1, intCodLoc=-1, intCodTipDoc=-1, intCodDoc=-1;
        try{
            intCodEmp=Integer.parseInt(objTblModCabDocAso.getValueAt(fila, INT_TBL_DAT_CAB_DOC_ASO_COD_EMP).toString());
            intCodLoc=Integer.parseInt(objTblModCabDocAso.getValueAt(fila, INT_TBL_DAT_CAB_DOC_ASO_COD_LOC).toString());
            intCodTipDoc=Integer.parseInt(objTblModCabDocAso.getValueAt(fila, INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC).toString());
            intCodDoc=Integer.parseInt(objTblModCabDocAso.getValueAt(fila, INT_TBL_DAT_CAB_DOC_ASO_COD_DOC).toString());
            Contabilidad.ZafCon04.ZafCon04 objCon04=new Contabilidad.ZafCon04.ZafCon04(objParSis, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, Integer.parseInt("523"));
            this.getParent().add(objCon04,javax.swing.JLayeredPane.DEFAULT_LAYER);
            objCon04.show();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean mostrarFrmDocumentoAsosiadoDetalle(int fila){
        boolean blnRes=true;
        int intCodEmp=-1, intCodLoc=-1, intCodTipDoc=-1, intCodDoc=-1;
        try{

            intCodEmp=Integer.parseInt(objTblModDetDocAso.getValueAt(fila, INT_TBL_DAT_DET_DOC_ASO_COD_EMP).toString());
            intCodLoc=Integer.parseInt(objTblModDetDocAso.getValueAt(fila, INT_TBL_DAT_DET_DOC_ASO_COD_LOC).toString());
            intCodTipDoc=Integer.parseInt(objTblModDetDocAso.getValueAt(fila, INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC).toString());
            intCodDoc=Integer.parseInt(objTblModDetDocAso.getValueAt(fila, INT_TBL_DAT_DET_DOC_ASO_COD_DOC).toString());
            CxC.ZafCxC01.ZafCxC01 objCxC01=new CxC.ZafCxC01.ZafCxC01(objParSis, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, Integer.parseInt("488"));
            this.getParent().add(objCxC01,javax.swing.JLayeredPane.DEFAULT_LAYER);
            objCxC01.show();


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean cargarCabDocAso(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar sentencia SQL
                objTblModCabDocAso.removeAllRows(); //Se limpia el modelo
                strSQL ="";    
                strSQL+=" SELECT y1.tx_nom AS tx_nomEmp, y.* FROM (";
                strSQL+="    SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia, x.tx_numDia, x.fe_dia";
                strSQL+="         , x.tx_desCor, x.tx_desLar, x.co_cta, x.tx_codCta, x.tx_nomCta";
                strSQL+="         , SUM(x.nd_monDeb) AS nd_monDeb, SUM(x.nd_monHab) AS nd_monHab, x.fe_ing ";    
                strSQL+="    FROM(";
                /* Muestra documentos asociados a NOTPED: Cuenta ACTIVO */
                strSQL+=" 	SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_dia, a.nd_monDeb, a.nd_monHab";
                strSQL+="              , a.tx_desCor, a.tx_desLar, a.co_cta, a.tx_codCta, a.tx_nomCta, a.fe_ing";
                strSQL+=" 	FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	        , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar AS tx_nomCta, a1.fe_ing";
                strSQL+=" 	        , (CASE WHEN  a6.co_tipDoc IS NULL  THEN  (CASE WHEN a7.co_empDiaAju IS NOT NULL THEN 'A' ELSE 'X' END) ELSE  (CASE WHEN a6.st_doc IS NOT NULL THEN a6.st_doc END) END ) AS st_Doc";
                strSQL+=" 	   FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                strSQL+=" 	   INNER JOIN(";
                strSQL+=" 		SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.co_imp, a.co_ctaAct AS co_Cta";
                strSQL+=" 		FROM tbm_cabNotPedImp AS a";
                strSQL+="               WHERE a.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="               AND a.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="               AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="               AND a.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);     
                strSQL+=" 		GROUP BY a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.co_imp, a.co_ctaAct, a.co_ctaPas";
                strSQL+=" 	    ) AS a3 ON a1.co_emp=a3.co_imp AND a2.co_cta=a3.co_cta";
                strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                strSQL+=" 	    INNER JOIN tbm_plaCta AS a5 ON a4.co_emp=a5.co_emp AND a2.co_cta=a5.co_Cta\n";
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabNotPedImp AS a6 ON a1.co_emp=a6.co_imp AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_dia=a6.co_doc ";
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabNotPedImp AS a7 ON a1.co_emp=a7.co_empDiaAju AND a1.co_loc=a7.co_locDiaAju AND a1.co_tipDoc=a7.co_tipDocDiaAju AND a1.co_dia=a7.co_diaDiaAju";	    
                strSQL+=" 	    WHERE a1.st_reg IN ('A') AND a4.st_Reg IN ('A')";
                strSQL+=" 	    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	           , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar, a1.fe_ing, a6.st_doc, a6.co_tipDoc, a7.co_empDiaAju";	
                strSQL+=" 	) AS a WHERE (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+="       UNION ALL";
                /* Muestra documentos asociados a NOTPED: Cuenta PASIVO */	
                strSQL+=" 	SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_dia, a.nd_monDeb, a.nd_monHab";
                strSQL+="              , a.tx_desCor, a.tx_desLar, a.co_cta, a.tx_codCta, a.tx_nomCta, a.fe_ing";
                strSQL+="         FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	        , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar AS tx_nomCta, a1.fe_ing";	    
                strSQL+=" 	        , (CASE WHEN  a6.co_tipDoc IS NULL  THEN  (CASE WHEN a7.co_empDiaAju IS NOT NULL THEN 'A' ELSE 'X' END) ELSE  (CASE WHEN a6.st_doc IS NOT NULL THEN a6.st_doc END) END ) AS st_Doc";
                strSQL+=" 	   FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                strSQL+=" 	   INNER JOIN(";
                strSQL+=" 		SELECT a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.co_imp, a.co_ctaPas AS co_Cta";
                strSQL+=" 		FROM tbm_cabNotPedImp AS a";
                strSQL+="               WHERE a.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="               AND a.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="               AND a.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="               AND a.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);  
                strSQL+=" 		GROUP BY a.co_emp, a.co_loc, a.co_TipDoc, a.co_doc, a.co_imp, a.co_ctaAct, a.co_ctaPas";
                strSQL+=" 	    ) AS a3 ON a1.co_emp=a3.co_imp AND a2.co_cta=a3.co_cta";
                strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                strSQL+=" 	    INNER JOIN tbm_plaCta AS a5 ON a4.co_emp=a5.co_emp AND a2.co_cta=a5.co_Cta\n"; 
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabNotPedImp AS a6 ON a1.co_emp=a6.co_imp AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_dia=a6.co_doc"; 
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabNotPedImp AS a7 ON a1.co_emp=a7.co_empDiaAju AND a1.co_loc=a7.co_locDiaAju AND a1.co_tipDoc=a7.co_tipDocDiaAju AND a1.co_dia=a7.co_diaDiaAju";
                strSQL+=" 	    WHERE a1.st_reg IN ('A') AND a4.st_Reg IN ('A')";
                strSQL+=" 	    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	           , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar, a1.fe_ing, a6.st_doc, a6.co_tipDoc, a7.co_empDiaAju";	
                strSQL+=" 	) AS a WHERE (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+=" 	UNION ALL";
                /* Muestra documentos asociados a PEDEMB: Cuenta ACTIVO */
                strSQL+=" 	SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_dia, a.nd_monDeb, a.nd_monHab";
                strSQL+="              , a.tx_desCor, a.tx_desLar, a.co_cta, a.tx_codCta, a.tx_nomCta, a.fe_ing";
                strSQL+="       FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	        , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar AS tx_nomCta, a1.fe_ing";    
                strSQL+=" 	        , (CASE WHEN  a6.co_tipDoc IS NULL  THEN 'A' ELSE (CASE WHEN a6.st_doc IS NOT NULL THEN a6.st_doc END) END ) AS st_Doc";
                strSQL+=" 	   FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                strSQL+=" 	   INNER JOIN(";
                strSQL+=" 		SELECT a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaAct AS co_cta";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a";
                strSQL+=" 		INNER JOIN tbm_detPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" 		INNER JOIN (  tbm_detNotPedImp AS a2 INNER JOIN tbm_cabNotPedImp AS a3";
                strSQL+=" 		              ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_TipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 		) ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_Doc AND a1.co_regRel=a2.co_reg";
                strSQL+="               WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="               AND a3.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="               AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="               AND a3.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);  
                strSQL+=" 		AND a.st_Reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+=" 	        GROUP BY a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaAct ";
                strSQL+=" 	    ) AS a3  ON a1.co_emp=a3.co_imp AND a2.co_cta=a3.co_cta";
                strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                strSQL+=" 	    INNER JOIN tbm_plaCta AS a5 ON a4.co_emp=a5.co_emp AND a2.co_cta=a5.co_Cta\n";
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabPedEmbImp AS a6 ON a1.co_emp=a6.co_imp AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_dia=a6.co_doc\n"; 
                strSQL+=" 	    WHERE a1.st_reg IN ('A') AND a4.st_Reg IN ('A')";
                strSQL+=" 	    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	           , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar, a1.fe_ing, a6.st_doc, a6.co_tipDoc";	
                strSQL+=" 	) AS a WHERE (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+="       UNION ALL";
                /* Muestra documentos asociados a PEDEMB: Cuenta PASIVO */
                strSQL+=" 	SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_dia, a.nd_monDeb, a.nd_monHab";
                strSQL+="              , a.tx_desCor, a.tx_desLar, a.co_cta, a.tx_codCta, a.tx_nomCta, a.fe_ing";
                strSQL+="       FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	        , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar AS tx_nomCta, a1.fe_ing";	
                strSQL+=" 	        , (CASE WHEN  a6.co_tipDoc IS NULL  THEN 'A' ELSE (CASE WHEN a6.st_doc IS NOT NULL THEN a6.st_doc END) END ) AS st_Doc";
                strSQL+=" 	   FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                strSQL+=" 	   INNER JOIN(";
                strSQL+=" 		SELECT a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaPas AS co_cta";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a";
                strSQL+=" 		INNER JOIN tbm_detPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" 		INNER JOIN (  tbm_detNotPedImp AS a2 INNER JOIN tbm_cabNotPedImp AS a3";
                strSQL+=" 	                       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_TipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";	
                strSQL+=" 		) ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_Doc AND a1.co_regRel=a2.co_reg";
                strSQL+="               WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="               AND a3.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="               AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="               AND a3.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);  
                strSQL+=" 		AND a.st_Reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+=" 	        GROUP BY a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaPas ";
                strSQL+=" 	    ) AS a3  ON a1.co_emp=a3.co_imp AND a2.co_cta=a3.co_cta";
                strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                strSQL+=" 	    INNER JOIN tbm_plaCta AS a5 ON a4.co_emp=a5.co_emp AND a2.co_cta=a5.co_Cta\n"; 
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabPedEmbImp AS a6 ON a1.co_emp=a6.co_imp AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_dia=a6.co_doc"; 
                strSQL+=" 	    WHERE a1.st_reg IN ('A') AND a4.st_Reg IN ('A')";
                strSQL+=" 	    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	           , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar, a1.fe_ing, a6.st_doc, a6.co_tipDoc";
                strSQL+=" 	) AS a WHERE (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+="       UNION ALL";
                /* Muestra documentos asociados a PEDEMB: Cuenta PROVISIÓN */
                strSQL+=" 	SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_dia, a.nd_monDeb, a.nd_monHab";
                strSQL+="              , a.tx_desCor, a.tx_desLar, a.co_cta, a.tx_codCta, a.tx_nomCta, a.fe_ing";
                strSQL+="         FROM(";
                strSQL+=" 	   SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	        , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar AS tx_nomCta, a1.fe_ing";
                strSQL+=" 	        , (CASE WHEN  a6.co_tipDoc IS NULL  THEN 'A' ELSE (CASE WHEN a6.st_doc IS NOT NULL THEN a6.st_doc END) END ) AS st_Doc";
                strSQL+=" 	   FROM tbm_cabDia AS a1 INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
                strSQL+=" 	   INNER JOIN(";
                strSQL+=" 		SELECT a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaPro AS co_cta";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS a";
                strSQL+=" 		INNER JOIN tbm_detPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc";
                strSQL+=" 		INNER JOIN ( tbm_detNotPedImp AS a2 INNER JOIN tbm_cabNotPedImp AS a3";
                strSQL+=" 		             ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_TipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" 		) ON a1.co_emp=a2.co_emp AND a1.co_locRel=a2.co_loc AND a1.co_tipDocRel=a2.co_tipDoc AND a1.co_docRel=a2.co_Doc AND a1.co_regRel=a2.co_reg";
                strSQL+="               WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="               AND a3.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="               AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="               AND a3.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);  
                strSQL+=" 		AND a.st_Reg IN ('A') AND (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+=" 	        GROUP BY a3.co_emp, a3.co_loc, a3.co_TipDoc, a3.co_doc, a.co_imp, a.co_ctaPro";
                strSQL+=" 	    ) AS a3 ON a1.co_emp=a3.co_imp AND a2.co_cta=a3.co_cta";
                strSQL+=" 	    INNER JOIN tbm_cabTipDoc AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc";
                strSQL+=" 	    INNER JOIN tbm_plaCta AS a5 ON a4.co_emp=a5.co_emp AND a2.co_cta=a5.co_Cta\n"; 
                strSQL+=" 	    LEFT OUTER JOIN tbm_cabPedEmbImp AS a6 ON a1.co_emp=a6.co_imp AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_dia=a6.co_doc ";
                strSQL+=" 	    WHERE a1.st_reg IN ('A') AND a4.st_Reg IN ('A')";
                strSQL+=" 	    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.tx_numDia, a1.fe_dia, a2.nd_monDeb, a2.nd_monHab";
                strSQL+=" 	           , a4.tx_desCor, a4.tx_desLar, a2.co_cta, a5.tx_codCta, a5.tx_desLar, a1.fe_ing, a6.st_doc, a6.co_tipDoc";	
                strSQL+=" 	) AS a WHERE (a.st_doc IN ('A') OR a.st_doc IS NULL)";
                strSQL+="    ) AS x";
                strSQL+="    GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia, x.tx_numDia, x.fe_dia";
                strSQL+="           , x.tx_desCor, x.tx_desLar, x.co_cta, x.tx_codCta, x.tx_nomCta, x.fe_ing";
                strSQL+=" ) AS y";
                strSQL+=" INNER JOIN tbm_emp AS y1 ON y.co_emp=y1.co_emp";
                strSQL+=" WHERE (CASE WHEN nd_monDeb=nd_monHab THEN FALSE ELSE TRUE END)";
                strSQL+=" ORDER BY  y.tx_codCta, y.fe_dia, y.fe_ing ";
                System.out.println("cargarCabDocAso: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDatCabDocAso.clear();
                while (rst.next()){
                    vecRegCabDocAso=new Vector();
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_LIN, "");
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_EMP,             "" + rst.getObject("tx_nomEmp")==null?"":rst.getString("tx_nomEmp"));                    
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_EMP,             "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_LOC,             "" + rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC,         "" + rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_DES_COR_TIP_DOC,     "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_DES_LAR_TIP_DOC,     "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_DOC,             "" + rst.getObject("co_dia")==null?"":rst.getString("co_dia"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NUM_DOC,             "" + rst.getObject("tx_numDia")==null?"":rst.getString("tx_numDia"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_FEC_DOC,             "" + rst.getObject("fe_dia")==null?"":rst.getString("fe_dia"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_CTA,             "" + rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NUM_CTA,             "" + rst.getObject("tx_codCta")==null?"":rst.getString("tx_codCta"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_CTA,             "" + rst.getObject("tx_nomCta")==null?"":rst.getString("tx_nomCta"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_VAL_DEB,             "" + rst.getObject("nd_monDeb")==null?"":rst.getString("nd_monDeb"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_VAL_HAB,             "" + rst.getObject("nd_monHab")==null?"":rst.getString("nd_monHab"));
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_BUT,                 null);
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_COD_MNU,             null);
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_NOM_MNU_CLS,         null);
                    vecRegCabDocAso.add(INT_TBL_DAT_CAB_DOC_ASO_FEC_ING,             "" + rst.getObject("fe_ing")==null?"":rst.getString("fe_ing"));
                    vecDatCabDocAso.add(vecRegCabDocAso);
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                con.close();
                con=null;

                //Asignar vectores al modelo.
                objTblModCabDocAso.setData(vecDatCabDocAso);
                tblCabDocAso.setModel(objTblModCabDocAso);
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

    private boolean cargarMovReg(){
        boolean blnRes=true;
        strAux="";
        try{
            if (tblCabDocAso.getSelectedRow()!=-1){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="(";
                    strSQL+="       SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a5.tx_desCor, a5.tx_desLar, a4.ne_numDoc1, a4.fe_doc";
                    strSQL+="       FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="       ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="       INNER JOIN tbm_detPag AS a3";
                    strSQL+="       ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag";
                    strSQL+="       INNER JOIN tbm_cabPag AS a4";
                    strSQL+="       ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                    strSQL+="       INNER JOIN tbm_cabTipDoc AS a5 ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                    strSQL+="       WHERE a1.co_emp=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_EMP) + "";
                    strSQL+="       AND a1.co_loc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_LOC) + "";
                    strSQL+="       AND a1.co_tipDoc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC) + "";
                    strSQL+="       AND a1.co_doc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_DOC) + "";
                    strSQL+=" 	    AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I')";
                    strSQL+=")";
                    strSQL+=" UNION";
                    strSQL+="(";
                    strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.ne_numDoc, a1.fe_doc";
                    strSQL+="   FROM tbm_cabPag AS a4 INNER JOIN tbm_detPag AS a3";
                    strSQL+="		ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc";
                    strSQL+="		INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="		ON a3.co_emp=a2.co_emp AND a3.co_locPag=a2.co_loc AND a3.co_tipDocPag=a2.co_tipDoc AND a3.co_docPag=a2.co_doc AND a3.co_regPag=a2.co_reg";
                    strSQL+="		INNER JOIN tbm_cabMovInv AS a1";
                    strSQL+="		ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                    strSQL+="		INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc";
                    strSQL+="		WHERE a4.co_emp=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_EMP) + "";
                    strSQL+="		AND a4.co_loc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_LOC) + "";
                    strSQL+="		AND a4.co_tipDoc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC) + "";
                    strSQL+="		AND a4.co_doc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_DOC) + "";
                    strSQL+="		AND a1.st_reg NOT IN('E','I') AND a4.st_reg NOT IN('E','I')";
                    strSQL+="	)";
                    strSQL+="	EXCEPT";
                    strSQL+="	(";
                    strSQL+="		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.ne_numDoc, a1.fe_doc";
                    strSQL+="		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                    strSQL+="		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="		INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc";
                    strSQL+="		WHERE a1.co_emp=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_EMP) + "";
                    strSQL+="		AND a1.co_loc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_LOC) + "";
                    strSQL+="		AND a1.co_tipDoc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC) + "";
                    strSQL+="		AND a1.co_doc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_DOC) + "";
                    strSQL+="		AND a1.st_reg NOT IN('E','I')";
                    strSQL+="		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.ne_numDoc, a1.fe_doc";
                    strSQL+="		UNION";
                    strSQL+="		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.ne_numDoc1, a1.fe_doc";
                    strSQL+="		FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a2";
                    strSQL+="		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="		INNER JOIN tbm_cabTipDoc AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc";
                    strSQL+="		WHERE a1.co_emp=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_EMP) + "";
                    strSQL+="		AND a1.co_loc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_LOC) + "";
                    strSQL+="		AND a1.co_tipDoc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_TIP_DOC) + "";
                    strSQL+="		AND a1.co_doc=" + objTblModCabDocAso.getValueAt(tblCabDocAso.getSelectedRow(), INT_TBL_DAT_CAB_DOC_ASO_COD_DOC) + "";
                    strSQL+="		AND a1.st_reg NOT IN('E','I')";
                    strSQL+="		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a5.tx_desCor, a5.tx_desLar, a1.ne_numDoc1, a1.fe_doc";
                    strSQL+="	)";
                    strSQL+="ORDER BY fe_doc";
                    //System.out.println("cargarMovReg:" +strSQL);
                    rst=stm.executeQuery(strSQL);
                    vecDatDetDocAso.clear();
                    while(rst.next()){
                        vecRegDetDocAso=new Vector();
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_LIN, "");
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_EMP,        "" + rst.getString("co_emp"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_LOC,        "" + rst.getString("co_loc"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_TIP_DOC,    "" + rst.getString("co_tipDoc"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_COD_DOC,        "" + rst.getString("co_doc"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_NUM_DOC,        "" + rst.getString("ne_numDoc1"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_FEC_DOC,        "" + rst.getString("fe_doc"));
                        vecRegDetDocAso.add(INT_TBL_DAT_DET_DOC_ASO_BUT, "");
                        vecDatDetDocAso.add(vecRegDetDocAso);
                    }
                    con.close();
                    con=null;
                    stm.close();
                    stm=null;
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModDetDocAso.setData(vecDatDetDocAso);
                    tblDetDocAso.setModel(objTblModDetDocAso);
                    vecDatDetDocAso.clear();
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

    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener{
        public void valueChanged(javax.swing.event.ListSelectionEvent e){
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty()){
                if (chkMosMovReg.isSelected()){
                    cargarMovReg();
                }
                else
                    objTblModDetDocAso.removeAllRows();
            }
        }
    }

    private boolean crearCuentaPedido(boolean crearCtaPas, int codImp){
        boolean blnRes=true;
        boolean blnCreCtaPas=crearCtaPas;
        intNivCtaPadAct=-1; intNivCtaPadPas=-1;
        strCodCtaPadPedAct="";
        strCodCtaPadPedPas="";
        objNivAct1=null;        objNivAct2=null;        objNivAct3=null;
        objNivAct4=null;        objNivAct5=null;        objNivAct6=null;
        objNivPas1=null;        objNivPas2=null;        objNivPas3=null;
        objNivPas4=null;        objNivPas5=null;        objNivPas6=null;
        try{
            if(con!=null){
                if(blnCreCtaPas){
                    stm=con.createStatement();
                    //para obtener el nivel de la cuenta padre
                    //ACTIVO
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp";
                    strSQL+=" WHERE a1.co_emp=" + codImp + "";
                    strSQL+=" AND a2.co_cta=a1.co_ctaCabActImp";
                    //System.out.println("intNivCtaPadAct: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNivCtaPadAct=rst.getInt("ne_niv");

                    //se obtiene la informacion para armar la cuenta de activo del pedido
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp,";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        if(j==1)
                            strSQL+=" b1.tx_niv" + j + "";
                        else
                            strSQL+=", b1.tx_niv" + j + "";
                    }
                    strSQL+=", b1.co_ctaPad";
                    strSQL+=", b2.tx_niv" + (intNivCtaPadAct+1) + "";
                    strSQL+=" FROM(";
                    strSQL+="        SELECT a1.co_emp,";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        if(j==1)
                            strAux="        a1.tx_niv" + j + "";
                        else
                            strAux+="       , a1.tx_niv" + j + "";
                    }
                    strSQL+=strAux;
                    strSQL+="       , a2.co_ctaPad";
                    strSQL+="       FROM tbm_plaCta AS a1 INNER JOIN(";
                    strSQL+="               SELECT a1.co_emp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                    strSQL+="               FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+="               WHERE a1.co_emp=" + codImp + "";
                    strSQL+="               AND a2.co_cta=a1.co_ctaCabActImp";
                    strSQL+="       ) AS a2";
                    strSQL+="       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                    strSQL+="       WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                    strSQL+="       GROUP BY a1.co_emp, ";
                    strSQL+="              " + strAux;
                    strSQL+="              , a2.co_ctaPad";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+="        SELECT a1.co_emp,";
                    strSQL+="              " + strAux;
                    strSQL+="             , (MAX(CAST(a1.tx_niv" + (intNivCtaPadAct+1) + " AS SMALLINT))+1) AS tx_niv" + (intNivCtaPadAct+1) + "";
                    strSQL+="             , a1.ne_pad AS co_ctaPad";
                    strSQL+=" 	     FROM tbm_plaCta AS a1";
                    strSQL+=" 	     WHERE a1.co_emp=" + codImp + "";
                    strSQL+="       GROUP BY a1.co_emp, ";
                    strSQL+="                " + strAux;
                    strSQL+="                , a1.ne_pad";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad";
                    for(int j=1; j<=intNivCtaPadAct; j++){
                        strSQL+=" AND b1.tx_niv" + j + "=b2.tx_niv" + j;
                    }
                    //System.out.println("ACTIVO: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        for(int j=1; j<=(intNivCtaPadAct+1); j++){
                            if(j==1)
                                objNivAct1=j==1?rst.getObject("tx_niv" + j):null;
                            else if(j==2)
                                objNivAct2=j==2?rst.getObject("tx_niv" + j):null;
                            else if(j==3)
                                objNivAct3=j==3?rst.getObject("tx_niv" + j):null;
                            else if(j==4)
                                objNivAct4=j==4?rst.getObject("tx_niv" + j):null;
                            else if(j==5)
                                objNivAct5=j==5?rst.getObject("tx_niv" + j):null;
                            else if(j==6)
                                objNivAct6=j==6?rst.getObject("tx_niv" + j):null;
                        }
                        strCodCtaPadPedAct=rst.getString("co_ctaPad");
                    }

                    //PASIVO
                    //nivel de la cuenta padre del pasivo
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+=" WHERE a1.co_emp=" + codImp + "";
                    strSQL+=" AND a2.co_cta=a1.co_ctaCabPasImp";
                    //System.out.println("intNivCtaPadPas: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intNivCtaPadPas=rst.getInt("ne_niv");

                    //se obtiene la informacion para armar la cuenta de pasivo del pedido
                    strSQL="";
                    strSQL+=" SELECT b1.co_emp,";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        if(j==1)
                            strSQL+=" b1.tx_niv" + j + "";
                        else
                            strSQL+=", b1.tx_niv" + j + "";
                    }
                    strSQL+=", b1.co_ctaPad";
                    strSQL+=", b2.tx_niv" + (intNivCtaPadPas+1) + "";
                    strSQL+=" FROM(";
                    strSQL+="        SELECT a1.co_emp,";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        if(j==1)
                            strAux="        a1.tx_niv" + j + "";
                        else
                            strAux+="       , a1.tx_niv" + j + "";
                    }
                    strSQL+=strAux;
                    strSQL+="       , a2.co_ctaPad";
                    strSQL+="       FROM tbm_plaCta AS a1 INNER JOIN(";
                    strSQL+="               SELECT a1.co_emp, a1.co_ctaCabPasImp AS co_ctaPad, a2.ne_niv";
                    strSQL+="               FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp";
                    strSQL+="               WHERE a1.co_emp=" + codImp + "";
                    strSQL+="               AND a2.co_cta=a1.co_ctaCabPasImp";
                    strSQL+="       ) AS a2";
                    strSQL+="       ON a1.co_emp=a2.co_emp AND a1.ne_pad=a2.co_ctaPad";
                    strSQL+="       WHERE a1.tx_tipCta='D' AND a1.ne_niv=(a2.ne_niv+1)";
                    strSQL+="       GROUP BY a1.co_emp, ";
                    strSQL+="                " + strAux;
                    strSQL+="              , a2.co_ctaPad";
                    strSQL+=" ) AS b1";
                    strSQL+=" INNER JOIN(";
                    strSQL+="        SELECT a1.co_emp,";
                    strSQL+="              " + strAux;
                    strSQL+="              , (MAX(CAST(a1.tx_niv" + (intNivCtaPadPas+1) + " AS SMALLINT))+1) AS tx_niv" + (intNivCtaPadPas+1) + "";
                    strSQL+="              , a1.ne_pad AS co_ctaPad";
                    strSQL+=" 	     FROM tbm_plaCta AS a1";
                    strSQL+=" 	     WHERE a1.co_emp=" + codImp + "";
                    strSQL+="        GROUP BY a1.co_emp, ";
                    strSQL+="                " + strAux;
                    strSQL+="               , a1.ne_pad";
                    strSQL+=" ) AS b2";
                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_ctaPad=b2.co_ctaPad";
                    for(int j=1; j<=intNivCtaPadPas; j++){
                        strSQL+=" AND b1.tx_niv" + j + "=b2.tx_niv" + j;
                    }
                    //System.out.println("PASIVO: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){
                        for(int j=1; j<=(intNivCtaPadPas+1); j++){
                            if(j==1)
                                objNivPas1=j==1?rst.getObject("tx_niv" + j):null;
                            else if(j==2)
                                objNivPas2=j==2?rst.getObject("tx_niv" + j):null;
                            else if(j==3)
                                objNivPas3=j==3?rst.getObject("tx_niv" + j):null;
                            else if(j==4)
                                objNivPas4=j==4?rst.getObject("tx_niv" + j):null;
                            else if(j==5)
                                objNivPas5=j==5?rst.getObject("tx_niv" + j):null;
                            else if(j==6)
                                objNivPas6=j==6?rst.getObject("tx_niv" + j):null;
                        }
                        strCodCtaPadPedPas=rst.getString("co_ctaPad");
                    }
                }
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCtaPed(boolean crearCtaPas, int codImp){
        int intCodUsr;
        boolean blnRes=true;
        String strSQLIns="";
        boolean blnCreCtaPas=crearCtaPas;
        String strNumCtaAct="", strNumCtaPas="";
        String strNivTmp="";
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //CUENTA DE ACTIVO
                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_cta) AS co_cta";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + codImp;
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intCodCtaPedAct=rst.getInt("co_cta");
                }
                intCodCtaPedAct++;

                if(txtCodImp.getText().equals(""+codImp)){
                    intCodCtaPedActIns=intCodCtaPedAct;
                }
                
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                if(blnCreCtaPas){
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="  " + codImp; //co_emp
                    strSQL+=", " + intCodCtaPedAct;     //co_cta
                    strSQL+=", Null" ; //tx_codAlt
                    
                    getDatExp(); //Obtiene los exportadores ingresados en el detalle.
                    
                    if(arlDatExp.size()==1){ //Si existe 1 solo exportador en el detalle.
                        //27 Nov 2014 ; Alfredo solicita a través de Michelle cambiar de txtNomExp2 al txtNomExp
                        strSQL+=", " + objUti.codificar(txtNomExp.getText() + " (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                        strNomCtaActAsiDia=objUti.codificar(txtNomExp.getText() + " (" +  txtNumPedOri.getText() + ")");//txtNumPed
                    }
                    else {  //Pedidos Consolidados: Si existe más de un exportador en el detalle.
                        //Se creará una cuenta por pedido y no por exportador
                        strSQL+=", " + objUti.codificar("Pedido Consolidado (" +  txtNumPedOri.getText() + ")"); //txtNumPed
                        strNomCtaActAsiDia=objUti.codificar("Pedido Consolidado (" +  txtNumPedOri.getText() + ")");//txtNumPed
                    }
                    strSQL+=", " + strCodCtaPadPedAct; //ne_pad  cuenta de tbm_emp
                    strSQL+="," + (intNivCtaPadAct+1) + ""; //ne_niv

                    strNivTmp=(objNivAct1==null?"":(objNivAct1.toString().length()==1?""+objNivAct1:""+objNivAct1));
                    strSQL+=", " + objUti.codificar((objNivAct1==null?null:(objNivAct1.toString().length()==1?""+objNivAct1:objNivAct1)), 0); //tx_niv1
                    strNumCtaAct+=strNivTmp;

                    strNivTmp=(objNivAct2==null?"":(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2));
                    strSQL+=", " + objUti.codificar((objNivAct2==null?null:(objNivAct2.toString().length()==1?"0"+objNivAct2:""+objNivAct2)), 0); //tx_niv2
                    strNumCtaAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct3==null?"":(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3));
                    strSQL+=", " + objUti.codificar((objNivAct3==null?null:(objNivAct3.toString().length()==1?"0"+objNivAct3:""+objNivAct3)), 0); //tx_niv3
                    strNumCtaAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct4==null?"":(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4));
                    strSQL+=", " + objUti.codificar((objNivAct4==null?null:(objNivAct4.toString().length()==1?"0"+objNivAct4:""+objNivAct4)), 0); //tx_niv4
                    strNumCtaAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct5==null?"":(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5));
                    strSQL+=", " + objUti.codificar((objNivAct5==null?null:(objNivAct5.toString().length()==1?"0"+objNivAct5:""+objNivAct5)), 0); //tx_niv5
                    strNumCtaAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivAct6==null?"":(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6));
                    strSQL+=", " + objUti.codificar((objNivAct6==null?null:(objNivAct6.toString().length()==1?"0"+objNivAct6:""+objNivAct6)), 0); //tx_niv6
                    strNumCtaAct+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNumCtaActAsiDia=strNumCtaAct;
                    
                    strSQL+=", '" + strNumCtaAct + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'D'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    if(txtCodImp.getText().equals(""+codImp)){
                        strSQL+="UPDATE tbm_cabNotPedImp";
                        strSQL+="        SET co_ctaAct=" + intCodCtaPedActIns + "";
                        strSQL+="        WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="        AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+="        AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+="        AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                    }
                    strSQLIns+=strSQL;

//                if(blnCreCtaPas){
                    //CUENTA DE PASIVO

                    intCodCtaPedPas=(intCodCtaPedAct+1);                    
                    if(txtCodImp.getText().equals(""+codImp)){
                        intCodCtaPedPasIns=intCodCtaPedPas;
                    }
                    
                    //Obtener la fecha del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;

                    strSQL="";
                    strSQL+=" INSERT INTO tbm_plaCta (";
                    strSQL+="       co_emp, co_cta, tx_codAlt, tx_desLar, ne_pad, ne_niv, tx_niv1, tx_niv2, tx_niv3";
                    strSQL+="     , tx_niv4, tx_niv5, tx_niv6, tx_codCta, tx_tipCta, tx_natCta, st_aut, st_ctaFluEfe, st_ctaFluFon, st_ctaPre";
                    strSQL+="     , tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                    strSQL+=" VALUES (";
                    strSQL+="" + codImp; //co_emp
                    strSQL+=", " + intCodCtaPedPas; //co_cta
                    strSQL+=", Null" ; //tx_codAlt
                    
                    if(arlDatExp.size()==1){ //Si existe 1 solo exportador en el detalle.
                        //27 Nov 2014 ; Alfredo solicita a través de Michelle cambiar de txtNomExp2 al txtNomExp
                        strSQL+=", " + objUti.codificar(txtNomExp.getText() + " (" +  txtVerNumPed.getText() + ")"); //tx_desLar
                        strNomCtaPasAsiDia=objUti.codificar(txtNomExp.getText() + " (" +  txtVerNumPed.getText() + ")");
                    }
                    else {  //Pedidos Consolidados: Si existe más de un exportador en el detalle.
                        //Se creará una cuenta por pedido y no por exportador
                        strSQL+=", " + objUti.codificar("Pedido Consolidado (" +  txtVerNumPed.getText() + ")"); //tx_desLar
                        strNomCtaPasAsiDia=objUti.codificar("Pedido Consolidado (" +  txtVerNumPed.getText() + ")");
                    }
                    
                    strSQL+=", " + strCodCtaPadPedPas; //ne_pad  cuenta de tbm_emp
                    strSQL+="," + (intNivCtaPadPas+1) + ""; //ne_niv

                    strNivTmp=(objNivPas1==null?"":(objNivPas1.toString().length()==1?""+objNivPas1:""+objNivPas1));
                    strSQL+=", " + objUti.codificar((objNivPas1==null?null:(objNivPas1.toString().length()==1?""+objNivPas1:objNivPas1)), 0); //tx_niv1
                    strNumCtaPas+=strNivTmp;

                    strNivTmp=(objNivPas2==null?"":(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2));
                    strSQL+=", " + objUti.codificar((objNivPas2==null?null:(objNivPas2.toString().length()==1?"0"+objNivPas2:""+objNivPas2)), 0); //tx_niv2
                    strNumCtaPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas3==null?"":(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3));
                    strSQL+=", " + objUti.codificar((objNivPas3==null?null:(objNivPas3.toString().length()==1?"0"+objNivPas3:""+objNivPas3)), 0); //tx_niv3
                    strNumCtaPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas4==null?"":(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4));
                    strSQL+=", " + objUti.codificar((objNivPas4==null?null:(objNivPas4.toString().length()==1?"0"+objNivPas4:""+objNivPas4)), 0); //tx_niv4
                    strNumCtaPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas5==null?"":(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5));
                    strSQL+=", " + objUti.codificar((objNivPas5==null?null:(objNivPas5.toString().length()==1?"0"+objNivPas5:""+objNivPas5)), 0); //tx_niv5
                    strNumCtaPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);

                    strNivTmp=(objNivPas6==null?"":(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6));
                    strSQL+=", " + objUti.codificar((objNivPas6==null?null:(objNivPas6.toString().length()==1?"0"+objNivPas6:""+objNivPas6)), 0); //tx_niv6
                    strNumCtaPas+="" + (strNivTmp.length()>0?"."+strNivTmp:strNivTmp);
                    strNumCtaPasAsiDia=strNumCtaPas;
                    
                    strSQL+=", '" + strNumCtaPas + "'"; //tx_codCta
                    strSQL+=", 'D'"; //tx_tipCta
                    strSQL+=", 'D'"; //tx_natCta
                    strSQL+=", 'N'"; //st_aut
                    strSQL+=", 'N'"; //st_ctaFluEfe
                    strSQL+=", 'N'"; //st_ctaFluFon
                    strSQL+=", 'N'"; //st_ctaPre
                    strSQL+=", " + objUti.codificar(txaObs1.getText()); //tx_obs1
                    strSQL+=", " + objUti.codificar(txaObs2.getText()); //tx_obs2
                    strSQL+=", 'A'"; //st_reg
                    strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                    strSQL+=", '" + strAux + "'"; //fe_ing
                    strSQL+=", '" + strAux + "'"; //fe_ultMod
                    strSQL+=", " + intCodUsr; //co_usrIng
                    strSQL+=", " + intCodUsr; //co_usrMod
                    strSQL+=");";
                    if(txtCodImp.getText().equals(""+codImp)){
                        strSQL+=" UPDATE tbm_cabNotPedImp";
                        strSQL+="        SET co_ctaPas=" + intCodCtaPedPasIns + "";
                        strSQL+="        WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="        AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+="        AND co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strSQL+="        AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+=";";
                    }

                    strSQLIns+=strSQL;
                    
                }
                System.out.println("insertarCtaPed: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
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

    private boolean insertaTbmSalCtaPed(boolean crearCta, int codImp){
        boolean blnRes=true;
        int intAni=0;
        String strMes="";
        BigDecimal bgdSalCtaPed=new BigDecimal(BigInteger.ZERO);
        boolean blnCreCta=crearCta;
        String strSQLIns="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL=" select extract('year' FROM CURRENT_TIMESTAMP) as anio";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                    intAni=rst.getInt("anio");

                if(blnCreCta){
                    //Activo
                    for(int i=1; i<=12; i++){
                        strMes=(i<=9?"0"+i:""+i);
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_salCta( co_emp, co_cta, co_per, tx_codCta, tx_desLar, nd_salCta, ne_pad, ne_niv, st_regrep)";
                        strSQL+=" VALUES(";
                        strSQL+="  " + codImp + "";   //co_emp
                        strSQL+=", " + intCodCtaPedAct + "";       //co_cta
                        strSQL+=", " + intAni + "" + strMes + "";  //co_per
                        strSQL+=", '" + strNumCtaActAsiDia + "'";     //tx_codCta
                        strSQL+=", " + strNomCtaActAsiDia + "";       //tx_desLar     
                        strSQL+=", " + bgdSalCtaPed + "";          //nd_salCta
                        strSQL+=", " + strCodCtaPadPedAct + "";    //ne_pad
                        strSQL+=", " + (intNivCtaPadAct+1)+ "";    //ne_niv
                        strSQL+=", 'I'";                           //st_regrep
                        strSQL+=");";
                        //System.out.println("cuenta activo: "+strSQL);
                        strSQLIns+=strSQL;
                    }
                    
                    //Pasivo
                    for(int i=1; i<=12; i++){
                        strMes=(i<=9?"0"+i:""+i);
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_salCta( co_emp, co_cta, co_per, tx_codCta, tx_desLar, nd_salCta, ne_pad, ne_niv, st_regrep)";
                        strSQL+=" VALUES(";
                        strSQL+="  " + codImp + "";   //co_emp
                        strSQL+=", " + intCodCtaPedPas + "";       //co_cta
                        strSQL+=", " + intAni + "" + strMes + "";  //co_per
                        strSQL+=", '" + strNumCtaPasAsiDia + "'";     //tx_codCta
                        strSQL+=", " + strNomCtaPasAsiDia + "";       //tx_desLar                          
                        strSQL+=", " + bgdSalCtaPed + "";          //nd_salCta
                        strSQL+=", " + strCodCtaPadPedPas + "";    //ne_pad
                        strSQL+=", " + (intNivCtaPadPas+1) + "";   //ne_niv                        
                        strSQL+=", 'I'";                           //st_regrep
                        strSQL+=");";
                        //System.out.println("cuenta Pasivo: "+strSQL);
                        strSQLIns+=strSQL;
                    }
                }
                System.out.println("insertaTbmSalCtaPed: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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

    private boolean isChangeFecha(){
        boolean blnRes=false;
        try{
            strFecEmbFin=txtNomMesEmb.getText();
            strFecArrFin=txtNomMesArr.getText();
            
            if( (strFecEmbIni.compareTo(strFecEmbFin)!=0)  ||  (strFecArrIni.compareTo(strFecArrFin)!=0)  ){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean enviarCorEleDptoVta(){
        boolean blnRes=false;
        int intCodCfgCorEle=15;
        try{
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
            String strSbjMsg="Sistema Zafiro:   ";
            
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Se ha modificado en " + objParSis.getNombreMenu() + "<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#ffffcc' width='120%' border='1'>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td rowspan='2'>Datos</td>";
            strMsg+="	<td colspan='2'>" + objParSis.getNombreMenu() + " # " + txtVerNumPed.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Anterior</td>";
            strMsg+="	<td>Actual</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de embarque:</td>";
            strMsg+="	<td>" + strFecEmbIni + "</td>";
            strMsg+="	<td>" + txtNomMesEmb.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#E3CEF6'>";
            strMsg+="	<td>Fecha de arribo:</td>";
            strMsg+="	<td>" + strFecArrIni + "</td>";
            strMsg+="	<td>" + txtNomMesArr.getText() + "</td>";
            strMsg+="</tr>";
            strMsg+="<tr bgcolor='#BCA9F5'>";
            strMsg+="	<td>Modificado:</td>";
            strMsg+="	<td>" + objParSis.getNombreUsuario() + "</td>";
            strMsg+="	<td>" + strAux + "</td>";
            strMsg+="</tr>";
            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            //Envía el correo.
            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                    //mostrarMsgInf("<HTML>Correo de notificación de Cambio de fecha.</HTML>");
                    blnRes=true;
                }
            }            
            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean setCabeceraPedidoPrevio(){
        boolean blnRes=true;
        String strFecColPed="";
        try{
            if(arlDatCab_objImp32_04!=null){
                if(arlDatCab_objImp32_04.size()>0){ 
                    for(int f=0; f<arlDatCab_objImp32_04.size();f++){
                        /* Obtiene fecha de colocación. */
                        dtpFecColPed.setText("");
                        strFecColPed=objUti.formatearFecha(objUti.getStringValueAt(arlDatCab_objImp32_04, f, objImp32_04.INT_TBL_ARL_FEC_COL_PED), objParSis.getFormatoFechaBaseDatos(), "dd/MM/yyyy");
                        dtpFecColPed.setText(""+strFecColPed);  
                        break;
                    }
                }             
            }
        }
        catch (Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        

    private boolean setDetallePedidoPrevio(){
        boolean blnRes=true;
        try{
            for(int k=0; k<arlDatDet_objImp32_04.size(); k++){
                vecReg=new Vector();
                vecReg.add(objImp32_02.INT_TBL_DAT_LIN,"");
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_ITM_MAE,    objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_COD_ITM_MAE));
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_ITM,        objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_COD_ITM));
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_ALT_ITM,    objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_COD_ALT_ITM));
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_LET_ITM,    objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_COD_LET_ITM));
                vecReg.add(objImp32_02.INT_TBL_DAT_BUT_ITM,        null);
                vecReg.add(objImp32_02.INT_TBL_DAT_NOM_ITM,        objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_NOM_ITM));
                vecReg.add(objImp32_02.INT_TBL_DAT_UNI_MED,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_PES,            "");
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_ARA,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_NOM_ARA,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_DES_ARA,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_POR_ARA,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_COD_EXP,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_NOM_EXP,        "");  
                vecReg.add(objImp32_02.INT_TBL_DAT_BUT_EXP,        null);                
                vecReg.add(objImp32_02.INT_TBL_DAT_CAN_TON_MET,    objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_CAN_PED_PRE_TON_MET));
                vecReg.add(objImp32_02.INT_TBL_DAT_CAN_PZA,        objUti.getStringValueAt(arlDatDet_objImp32_04, k, objImp32_04.INT_TBL_ARL_DET_CAN_PED_PRE));
                vecReg.add(objImp32_02.INT_TBL_DAT_PRE_UNI,        "");   
                vecReg.add(objImp32_02.INT_TBL_DAT_TOT_FOB_CFR,    "");                 
                vecReg.add(objImp32_02.INT_TBL_DAT_VAL_FLE,        "");   
                vecReg.add(objImp32_02.INT_TBL_DAT_VAL_CFR,        "");   
                vecReg.add(objImp32_02.INT_TBL_DAT_VAL_CFR_ARA,    "");   
                vecReg.add(objImp32_02.INT_TBL_DAT_VAL_ISD,        ""); 
                vecReg.add(objImp32_02.INT_TBL_DAT_CHK_ISD_CRE_TRI,"");                 
                vecReg.add(objImp32_02.INT_TBL_DAT_TOT_ARA,        ""); 
                vecReg.add(objImp32_02.INT_TBL_DAT_TOT_GAS,        ""); 
                vecReg.add(objImp32_02.INT_TBL_DAT_TOT_COS,        ""); 
                vecReg.add(objImp32_02.INT_TBL_DAT_COS_UNI,        "");
                vecReg.add(objImp32_02.INT_TBL_DAT_COS_KGR,        "");  
                vecReg.add(objImp32_02.INT_TBL_DAT_REV_ITM,        ""); 
                vecDat.add(vecReg);
            }

            if(objImp32_02.asignarVectorModeloDatos(vecDat)){
                vecDat.clear();
            }
            else{
                mostrarMsgInf("Los datos del detalle no se pudieron cargar correctamente");
            }
            //objImp32_02.regenerarCalculoCfrAra();
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si existe un Pedido Embarcado sociado a la Nota de Pedido
     * @return true: Si existe un Pedido Embarcado asociado.
     * <BR>false: En el caso contrario.
     */
    private boolean isPedidoEmbarcado(){
        boolean blnRes=false;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+=" FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locRel AND a2.co_tipDoc=a3.co_tipDocRel AND a2.co_doc=a3.co_docRel AND a2.co_reg=a3.co_regRel";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a4.st_reg='A'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                if(rstPedEmb.next())
                    blnRes=true;
                conPedEmb.close();
                conPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }

    /**
     * Función que permite conocer si la cuenta creada en la Nota de P
     * @return 
     */
    private boolean isCuentaAsociadaSaldo(){
        boolean blnRes=false;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaAct=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                strSQL+=" UNION";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_salcta";
                strSQL+=" FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_imp=a2.co_emp AND a1.co_ctaPas=a2.co_cta";
                strSQL+=" INNER JOIN tbm_salCta AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" AND a3.nd_salcta<>0";
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                if(rstPedEmb.next())
                    blnRes=true;
                conPedEmb.close();
                conPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    /**
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorElePreVta(boolean blnIsNew){
        boolean blnRes=false;
        int intNumFil=0;
        Connection conValPreVta;
        Statement stmValPreVta;
        ResultSet rstValPreVta;
        BigDecimal bgdPreVtaItm=new BigDecimal("0");
        //BigDecimal bgdPreVtaDscItm=new BigDecimal("0");//precio de venta menos el 25%
        BigDecimal bgdMarUtiItm=new BigDecimal("0");
        BigDecimal bgdCosUniItm=new BigDecimal("0");//costo unitario del item
        BigDecimal bgdCosUniInsPed=new BigDecimal("0");//Costo unitario de la instancia del pedido
        BigDecimal bgdRelCosUniMarPreVta=new BigDecimal("0");//para la formula    Costo Unitario /    (   1 - (Margen precio vta/100)    )
        
        String strSbjMsg="";  
        if(blnIsNew){
            strSbjMsg=""+intNumNotPedDia + ") " + txtVerNumPed.getText() + " (" + objParSis.getNombreMenu() + ")" + " ***CREACIÓN DE NOTA DE PEDIDO...***";
        }
        else{
            strSbjMsg=""+intNumNotPedDia + ") " + txtVerNumPed.getText() + " (" + objParSis.getNombreMenu() + ")" + " ***MODIFICACIÓN DE NOTA DE PEDIDO...***";
        }
        //String strSbjMsg="" + txtNumCorEle.getText() + ") " + txtNumDoc2.getText() + " (" + objParSis.getNombreMenu() + " - Modificado)"; 
        boolean blnEstExiPreFueMar=false;//Para saber si existen items que esten fuera del margen del precio de venta
        int intCodCfgCorEle=14;
        
        try{
            conValPreVta=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conValPreVta!=null){
                stmValPreVta=conValPreVta.createStatement();
                
                String strMsg="";
                strMsg+="<html>";
                strMsg+="<body>";
                if(blnIsNew){
                    strMsg+="<h3>Revisar precios - CREACIÓN DE NOTA DE PEDIDO...<hr></h3>";
                }
                else{
                    strMsg+="<h3>Revisar precios - MODIFICACIÓN DE NOTA DE PEDIDO... <hr></h3>";
                }
                strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th colspan='7'> Pedido # " + txtVerNumPed.getText() + "</th>";
                strMsg+="</tr>";
                
                strMsg+="<tr bgcolor='#F2E0F7'>";
                strMsg+="	<th>Nº</th>";
                strMsg+="	<th>Cod.Alt.Itm.</th>";
                strMsg+="	<th>Cod.Let.Itm.</th>";
                strMsg+="	<th>Nom.Itm.</th>";
                strMsg+="	<th>Cos.Uni.Ped.</th>";
                strMsg+="	<th>Pre.Vta.</th>";
                strMsg+="	<th>Cos.Uni.Itm.</th>";
                strMsg+="	<th>Pre.Sug.</th>";
                strMsg+="	<th>Mar.Pre.Vta.Min.</th>";                        
                strMsg+="</tr>";
                
                //Se ordena por código alterno, para enviar el correo ordenado. Solicitado por Wcampoverde.
                objImp32_02.ordenarDatPorCodAlt();
                objTblModImp32_02.setModoOperacion(objTblModImp32_02.INT_TBL_NO_EDI);
                objTblModImp32_02.removeEmptyRows();
                
                for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                    intNumFil++;
                    strSQL =""; 
                    strSQL+=" SELECT a1.nd_preVta1, a1.nd_marUti, CASE WHEN a1.nd_cosUni IS NULL THEN 0 ELSE a1.nd_cosUni END AS nd_cosUni FROM tbm_inv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_itm=" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM) + "";
                    strSQL+=";";
                    rstValPreVta=stmValPreVta.executeQuery(strSQL);
                    if(rstValPreVta.next()){
                        System.out.println("===>> " +objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM)+ " CosUni "+ rstValPreVta.getDouble("nd_cosUni"));
                        bgdPreVtaItm=new BigDecimal(rstValPreVta.getObject("nd_preVta1")==null?"0":rstValPreVta.getString("nd_preVta1").equals("")?"0":rstValPreVta.getString("nd_preVta1"));
                        
                        bgdPreVtaItm=objUti.redondearBigDecimal(bgdPreVtaItm, objParSis.getDecimalesMostrar());
                        //bgdPreVtaDscItm=objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("1.005"))), objParSis.getDecimalesMostrar());
                        bgdMarUtiItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_marUti"),objParSis.getDecimalesMostrar());
                        bgdCosUniItm=objUti.redondearBigDecimal(rstValPreVta.getBigDecimal("nd_cosUni"), objParSis.getDecimalesMostrar());
                    }
                    bgdCosUniInsPed=objUti.redondearBigDecimal((new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString()))), objParSis.getDecimalesMostrar());
                    //obtencion de la formula
                    /*ANTES JM 20/Sep/2022 *///bgdRelCosUniMarPreVta=objUti.redondearBigDecimal((bgdCosUniInsPed.divide((   (new BigDecimal("1")).subtract(   bgdMarUtiItm.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)        )        ), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar());
                    
                    /*JM 20/Sep/2022 Nueva forma*/
                    BigDecimal bgdValorDivAux;
                    bgdValorDivAux = (   (new BigDecimal("1")).subtract(   bgdMarUtiItm.divide(new BigDecimal("100"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)        )        );

                    if(bgdValorDivAux.compareTo(new BigDecimal("0")) == 0 )  //Si el valor es igual a 0, se asigna 1 para poder realizar la division sin error
                       bgdValorDivAux = new BigDecimal("1");
                    
                    bgdRelCosUniMarPreVta=objUti.redondearBigDecimal((bgdCosUniInsPed.divide( bgdValorDivAux, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesMostrar());
                    
                    /**/
                    
                    if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("1.005"))), objParSis.getDecimalesMostrar()))>0){// bgdPreVtaDscItm                       por debajo del margen  - rojo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FF4000'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else if(bgdRelCosUniMarPreVta.compareTo(objUti.redondearBigDecimal( (bgdPreVtaItm.subtract((bgdPreVtaItm.multiply(new BigDecimal("0.25")))).multiply(new BigDecimal("0.995"))), objParSis.getDecimalesMostrar()))<0){//bgdPreVtaDscItm                       por encima del margen - amarillo
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#FFFF00'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }
                    else{//son iguales  - verde
                        blnEstExiPreFueMar=true;
                        strMsg+="<tr bgcolor='#D0F5A9'>";
                        strMsg+="	<td>" + intNumFil + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ALT_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_LET_ITM) + "</td>";
                        strMsg+="	<td>" + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_NOM_ITM) + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniInsPed + "</td>";
                        strMsg+="	<td align=right>" + bgdPreVtaItm + "</td>";
                        strMsg+="	<td align=right>" + bgdCosUniItm + "</td>";
                        strMsg+="	<td align=right>" + (bgdRelCosUniMarPreVta.divide(new BigDecimal("0.75"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)) + "</td>";
                        strMsg+="	<td align=right>" + bgdMarUtiItm + "</td>";                        
                        strMsg+="</tr>";
                    }                    
                }
                
                strMsg+="</table>";
                strMsg+="</body>";
                strMsg+="</html> ";
         
                if(strMsg.length()>0){
                    if(blnEstExiPreFueMar){                    
                        if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                            mostrarMsgInf("<HTML>Estimado usuario, <FONT COLOR=\"blue\">REVISAR PRECIOS</FONT></HTML>");
                            blnRes=true;
                        }
                    }
                }
                
                conValPreVta.close();
                conValPreVta=null;
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
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblModImp32_02.getRowCountTrue(); j++){
                    if(objTblModImp32_02.compareStringCells(i, objImp32_02.INT_TBL_DAT_COD_ITM, j, objImp32_02.INT_TBL_DAT_COD_ITM)){
                        blnRes=true;
                        break;
                    }
                }
                if(blnRes)
                    break;
            }
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean getDiaPag_ForPag(){
        boolean blnRes=true;
        Connection conDiaPag;
        Statement stmDiaPag;
        ResultSet rstDiaPag;
        String strDiaPag="";
        arlDatDiaPag_ForPag.clear();
        try{
            if(txtCodForPag.getText().length()>0){
                conDiaPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conDiaPag!=null){
                    stmDiaPag=conDiaPag.createStatement();
                    strDiaPag="";
                    strDiaPag+=" SELECT a8.co_emp, a8.co_forPag, a8.tx_des AS tx_desForPag, a8.ne_numPag, a8.ne_tipForPag, a9.ne_diaCre";
                    strDiaPag+=" FROM tbm_cabForPag AS a8 INNER JOIN tbm_detForPag AS a9";
                    strDiaPag+=" ON a8.co_emp=a9.co_emp AND a8.co_forPag=a9.co_forPag";
                    strDiaPag+=" WHERE a8.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strDiaPag+=" AND a8.co_forPag=" + txtCodForPag.getText() + "";
                    strDiaPag+=" AND a8.st_reg NOT IN('E','I') ";
                    strDiaPag+=" GROUP BY a8.co_emp, a8.co_forPag, a8.tx_des, a8.ne_numPag, a8.ne_tipForPag, a9.ne_diaCre, a9.co_reg";
                    strDiaPag+=" ORDER BY a8.co_forPag, a9.co_reg";
                    rstDiaPag=stmDiaPag.executeQuery(strDiaPag);
                    while(rstDiaPag.next()){
                        arlRegDiaPag_ForPag=new ArrayList();
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_EMP, "" + rstDiaPag.getString("co_emp"));
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_COD_FOR_PAG, "" + rstDiaPag.getString("co_forPag"));
                        arlRegDiaPag_ForPag.add(INT_ARL_DIA_PAG_NUM_DIA_CRE, "" + rstDiaPag.getString("ne_diaCre"));
                        arlDatDiaPag_ForPag.add(arlRegDiaPag_ForPag);
                    }
                    conDiaPag.close();
                    conDiaPag=null;
                    stmDiaPag.close();
                    stmDiaPag=null;
                    rstDiaPag.close();
                    rstDiaPag=null;
                }
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private String fechaVencimientoPago(int intValDia, String fecha){
        String strFecVncPag="";
        Connection conVctPag;
        Statement stmVctPag;
        ResultSet rstVctPag;
        try{
            conVctPag=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conVctPag!=null){
                stmVctPag=conVctPag.createStatement();
                strSQL="";
                strSQL+=" SELECT (CAST('" + objUti.formatearFecha(fecha, "dd/MM/yyyy", "yyyy-MM-dd") + "' AS DATE) + " + intValDia + ") AS fe_ven";
                rstVctPag=stmVctPag.executeQuery(strSQL);
                if(rstVctPag.next()){
                    strFecVncPag=rstVctPag.getString("fe_ven");
                }
                conVctPag.close();
                conVctPag=null;
                stmVctPag.close();
                stmVctPag=null;
                rstVctPag.close();
                rstVctPag=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecVncPag;
    }
    
    private boolean cargarFormaPago(){
        boolean blnRes=true;
        BigDecimal bgdTot=new BigDecimal("0");
        int intValDia=0;
        BigDecimal bgdValCer=new BigDecimal("0");
        BigDecimal bgdValApl=new BigDecimal("0");
        BigDecimal bgdValAplAcu=new BigDecimal("0");
        int intNumDiaForPag=Integer.parseInt(txtNumForPag.getText()==null?"0":(txtNumForPag.getText().equals("")?"0":txtNumForPag.getText()));

        getDiaPag_ForPag();

        try{
            if(intNumDiaForPag>0){
                txtValFac.setText((objUti.redondearBigDecimal(objImp32_01.getValFacCarPag(), objParSis.getDecimalesMostrar())).toString());
                bgdTot=txtValFac.getText()==null?bgdValCer:(txtValFac.getText().equals("")?bgdValCer:new BigDecimal(txtValFac.getText()));
                objTblModForPag.setModoOperacion(objTblModForPag.INT_TBL_INS);
                bgdValApl=objUti.redondearBigDecimal((bgdTot), objParSis.getDecimalesMostrar());
                bgdValApl=(bgdValApl.divide(new BigDecimal("" + intNumDiaForPag), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));

                for(int i=0; i<intNumDiaForPag; i++ ){
                    intValDia=objUti.getIntValueAt(arlDatDiaPag_ForPag, i, INT_ARL_DIA_PAG_NUM_DIA_CRE);
                    objTblModForPag.insertRow();
                    objTblModForPag.setValueAt("" + intValDia, i, INT_TBL_FOR_PAG_DAT_DIA_CRE);
                    objTblModForPag.setValueAt(objUti.formatearFecha(fechaVencimientoPago(intValDia,dtpFecDoc.getText()), "yyyy-MM-dd", "dd/MM/yyyy"), i, INT_TBL_FOR_PAG_DAT_FEC_VEN);
                    objTblModForPag.setValueAt(bgdValApl, i, INT_TBL_FOR_PAG_DAT_VAL_PAG);
                    
                    if(i==(intNumDiaForPag-1))
                        objTblModForPag.setValueAt((bgdTot.subtract(bgdValAplAcu)), i, INT_TBL_FOR_PAG_DAT_VAL_PAG);
                    bgdValAplAcu=bgdValAplAcu.add(bgdValApl);
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }

    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRepAra="", strSQLSubRepItm="", strSQLSubRepCarPag="";;
        Statement stmIns;
        ResultSet rstIns;
        String strNomPaiEmbPto="";
        String strValDocPal="0";
        String strRutImg="";
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(conIns!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){

                            stmIns=conIns.createStatement();
                            strSQL="";
                            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.tx_numdoc2, a1.ne_tipnotped";
                            strSQL+=" 	   , a1.co_imp, a1.co_prv, a1.co_exp, a1.co_ciupueemb AS co_pueEmb, a1.co_seg, a1.co_forpag, a1.co_mesemb";
                            strSQL+=" 	   , a1.tx_tipemp, a1.nd_valdoc, a1.nd_pestotkgr, a1.st_notpedlis, a1.st_imp, a1.tx_obs1";
                            strSQL+=" 	   , a1.tx_obs2, a1.st_reg, a1.tx_tipemp, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc";
                            strSQL+=" 	   , a3.co_emp AS co_imp, a3.tx_nom AS tx_nomImp, a3.tx_ruc AS tx_rucEmp";
                            strSQL+=" 	   , a3.tx_dir AS tx_dirEmp, CAST('ECUADOR' AS CHARACTER VARYING) AS tx_paiEmp";
                            strSQL+=" 	   , a3.tx_tel AS tx_telEmp, a3.tx_fax AS tx_faxEmp, a4.co_prv, a4.tx_nom AS tx_nomPrvExt, a5.co_exp";
                            if(Integer.parseInt(objRptSis.getCodigoReporte(i))==419)//seguro
                                strSQL+=" 	,a5.tx_nom2 AS tx_nomExp";
                            else
                                strSQL+=" 	,a5.tx_nom AS tx_nomExp";
                                
                            strSQL+=" 	   , a5.tx_dir AS tx_dirExp, a5.tx_tel1 AS tx_telExp1, a5.tx_tel2 AS tx_telExp2, a5.tx_fax AS tx_faxExp, a5.tx_perCon AS tx_perConExp";
                            strSQL+=" 	   , a7.co_seg, a7.tx_deslar AS tx_nomSeg";
                            strSQL+=" 	   , a8.co_forpag, a8.tx_deslar AS tx_nomForPag";
                            strSQL+=" 	   , a9.co_mesemb, a9.tx_deslar AS tx_nomMesEmb";
                            strSQL+=" 	   , a11.co_pai, a11.tx_desCor AS tx_desCorPaiExp, a11.tx_desLar AS tx_desLarPaiExp";
                            strSQL+=" 	   , a12.co_mesemb, a12.tx_deslar AS tx_nomMesEmb";
                            strSQL+=" 	   , a13.co_ciu, a13.tx_desCor AS tx_desCorPueEmb, a13.tx_deslar AS tx_desLarPueEmb";
                            strSQL+=" 	   , a14.co_seg, a14.tx_deslar AS tx_nomSeg";
                            strSQL+=" 	   , a15.co_forpag, a15.tx_deslar AS tx_nomForPag";
                            strSQL+=" 	   , a16.co_pai, a16.tx_desCor AS tx_desCorPaiPto, a16.tx_desLar AS tx_desLarPaiPto";
                            strSQL+=" 	   , a3.nd_porImpIvaCom, ROUND(a3.nd_porImpIvaCom/100, 2) as PorIva  ";
                            strSQL+=" FROM tbm_cabNotPedImp AS a1";
                            strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a12 ON(a1.co_mesemb=a12.co_mesemb)";
                            strSQL+=" LEFT OUTER JOIN tbm_ciu AS a13 ON(a1.co_ciupueemb=a13.co_ciu)";
                            strSQL+=" LEFT OUTER JOIN tbm_pai AS a16 ON(a13.co_pai=a16.co_pai)";
                            strSQL+=" LEFT OUTER JOIN tbm_segImp AS a14 ON(a1.co_seg=a14.co_seg)";
                            strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a15 ON(a1.co_forpag=a15.co_forpag)";
                            strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                            strSQL+=" LEFT OUTER JOIN ";
                            strSQL+=" ( ";
                            strSQL+="    select emp.co_emp, loc.co_loc, emp.tx_nom, emp.tx_ruc, emp.tx_dir, emp.tx_tel, emp.tx_fax, cfg.fe_vigHas, cfg.fe_vigDes";        
                            strSQL+="         , emp.nd_ivaCom, loc.tx_nom as Loc, cfg.nd_porImp as nd_porImpIvaCom  ";         
                            strSQL+="    from tbm_emp as emp  ";
                            strSQL+="    inner join tbm_loc as loc on (loc.co_emp=emp.co_emp) ";
                            strSQL+="    inner join tbm_cfgImpLoc as cfg on (cfg.co_emp=emp.co_emp and cfg.co_loc=loc.co_loc) ";
                            strSQL+="    where cfg.co_imp=1 and emp.st_Reg='A' and loc.st_Reg='P' ";
                            strSQL+=" ) as a3  ";
                            strSQL+=" ON (a3.co_emp=a1.co_imp AND (CASE WHEN a3.fe_vigHas IS NULL THEN a1.fe_doc>=a3.fe_vigDes ELSE a1.fe_doc>=a3.fe_vigDes AND a1.fe_doc<=a3.fe_vigHas END) )  ";
                            strSQL+=" LEFT OUTER JOIN tbm_prvExtImp AS a4 ON(a1.co_prv=a4.co_prv)";
                            strSQL+=" LEFT OUTER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                            strSQL+=" LEFT OUTER JOIN tbm_ciu AS a10 ON (a5.co_ciu=a10.co_ciu)";
                            strSQL+=" LEFT OUTER JOIN tbm_pai AS a11 ON(a10.co_pai=a11.co_pai)";
                            strSQL+=" LEFT OUTER JOIN tbm_segimp AS a7 ON(a1.co_seg=a7.co_seg)";
                            strSQL+=" LEFT OUTER JOIN tbm_forpagimp AS a8 ON(a1.co_forpag=a8.co_forpag)";
                            strSQL+=" LEFT OUTER JOIN tbm_mesEmbImp AS a9 ON(a1.co_mesemb=a9.co_mesemb)";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                            strSQLRep=strSQL;
                            rstIns=stmIns.executeQuery(strSQLRep);
                            if(rstIns.next()){
                                strNomPaiEmbPto=rstIns.getObject("tx_desLarPaiPto")==null?"":rstIns.getString("tx_desLarPaiPto");
                            }
                            rstIns.close();
                            rstIns=null;
                            stmIns.close();
                            stmIns=null;
                            
                            strSQL="";
                            strSQL+=" SELECT b1.co_parara, b1.tx_par, CAST ((b1.tx_des || b1.tx_tna) AS CHARACTER VARYING) AS tx_des ";
                            strSQL+=" FROM( SELECT CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                            strSQL+="            , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                            strSQL+="            , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                            strSQL+="            , CASE WHEN a5.tx_destna IS NULL THEN '' ELSE '  TNAN:'||a6.nd_ara END AS tx_tna";                            
                            strSQL+="       FROM (tbm_detnotpedimp AS b1 INNER JOIN tbm_cabNotPedImp AS c1";
                            strSQL+="             ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc)";
                            strSQL+="       INNER JOIN";
                            strSQL+="       (";
                            strSQL+="           ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                            strSQL+="             LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg))";
                            strSQL+="            LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                            strSQL+="                           INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra)";
                            strSQL+="            ON a1.co_grpImp=a4.co_grp";
                            strSQL+="       ) ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                            strSQL+="       WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                            strSQL+="       AND b1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                            strSQL+="       AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                            strSQL+="       AND b1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                            strSQL+="       AND c1.st_reg='A'";
                            strSQL+="       AND c1.fe_doc BETWEEN a6.fe_vigDes";
                            strSQL+="       AND (CASE WHEN a6.fe_vigHas IS NULL THEN CURRENT_DATE ELSE a6.fe_vigHas END)";
                            strSQL+="       GROUP BY a5.co_parara, a5.tx_par, a5.tx_des,a5.tx_destna, a6.nd_ara";
                            strSQL+="       ORDER BY a5.co_parara";
                            strSQL+=" ) AS b1";                            
                            strSQLSubRepAra=strSQL;

                            strSQL="";
                            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQL+="      , CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END AS co_parara";
                            strSQL+="      , CASE WHEN a5.tx_par IS NULL THEN '' ELSE a5.tx_par END AS tx_par";
                            strSQL+="      , CASE WHEN a5.tx_des IS NULL THEN '' ELSE a5.tx_des END AS tx_des";
                            strSQL+="      , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";

                            if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                strSQL+=" , ROUND(b1.nd_can,4) AS nd_can";
                            else
                                strSQL+=" , ROUND(b1.nd_can,0) AS nd_can";

                            //strSQL+=" , b1.nd_can";
                            strSQL+="     , b1.nd_preUni, b1.nd_valTotFobCfr";
                            if( (optTmFob.isSelected())  ||  (optTmCfr.isSelected()))
                                strSQL+=" , b1.nd_canTonMet";
                            else
                                strSQL+=" , b1.nd_can AS nd_canTonMet";
                            strSQL+="     , b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                            strSQL+="     , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                            strSQL+=" FROM tbm_detnotpedimp AS b1 INNER JOIN";
                            strSQL+=" (";
                            strSQL+="    ((tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                            strSQL+="      LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg) )";
                            strSQL+="    LEFT OUTER JOIN(tbm_grpInvImp AS a4 INNER JOIN tbm_parAraImp AS a5 ON a4.co_parAra=a5.co_parAra";
                            strSQL+=" 	                 INNER JOIN tbm_vigParAraImp AS a6 ON a5.co_parAra=a6.co_parAra )";
                            strSQL+="    ON a1.co_grpImp=a4.co_grp";
                            strSQL+=" )";
                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                            strSQL+=" WHERE b1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND b1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND b1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                            strSQL+=" AND b1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);                                                  
                                                                                    
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 472:
                                case 474:
                                    break;
                                default:
                                    strSQL+=" AND (CASE WHEN a5.co_parara IS NULL THEN 0 ELSE a5.co_parara END)=$P!{co_parAraItm}";
                                    break;
                            }
                            
                            strSQL+=" GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                            strSQL+="        , a5.co_parara, a5.tx_par, a5.tx_des, b1.nd_ara";
                            strSQL+="        , b1.nd_can, b1.nd_preUni, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle, b1.nd_valCfr, b1.nd_valTotAra";
                            strSQL+="        , b1.nd_valTotGas, b1.nd_valTotCos, b1.nd_cosUni, b1.co_reg";
                            strSQL+=" ORDER BY b1.co_reg, a1.tx_codAlt";
                            strSQLSubRepItm=strSQL;

                            strSQL="";
                            strSQL+=" SELECT a2.co_carPag, a2.tx_nom AS tx_nomCarPag, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+=" FROM tbm_carPagNotPedImp AS a1 INNER JOIN tbm_carPagImp AS a2";
                            strSQL+=" ON a1.co_carPag=a2.co_carPag";
                            strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                            strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                            strSQL+=" AND a2.st_reg IN('A')";
                            strSQL+=" GROUP BY a2.co_carPag, a2.tx_nom, a2.tx_tipCarPag, a1.nd_valCarPag, a2.ne_ubi";
                            strSQL+=" ORDER BY a2.ne_ubi";
                            strSQLSubRepCarPag=strSQL;

                            //Cantidad en palabras.
                            try{
                                Librerias.ZafUtil.ZafNumLetra numero;
                                //double cantidad= objUti.redondear(txtValDoc.getText(), objParSis.getDecimalesMostrar());
                                double cantidad= objUti.redondear(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString()), objParSis.getDecimalesMostrar());
                                String decimales=String.valueOf(cantidad).toString();
                                decimales=decimales.substring(decimales.indexOf('.') + 1);
                                decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
                                int deci= Integer.parseInt(decimales);
                                int m_pesos=(int)cantidad;
                                numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
                                strValDocPal = numero.convertirLetras(m_pesos);
                                strValDocPal = strValDocPal + " "+decimales+"/100  DOLARES";
                                strValDocPal=strValDocPal.toUpperCase();
                                numero=null;
                            }
                            catch(Exception e){
                                objUti.mostrarMsgErr_F1(this, e);
                            }

                            strRutImg=objRptSis.getRutaReporte(i);

                            java.util.Map mapPar = new java.util.HashMap();
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 420://nota de pedido incompleta(se envia al seguro y a ....)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    
                                    //Inicializar los parametros que se van a pasar al reporte.                                    
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " "+strVer);
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP));
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC));
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC));
                                    mapPar.put("codDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC));
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                                                        
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fobApr", new BigDecimal("0"));
                                    mapPar.put("valTotPal", strValDocPal);
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    //para colocar en el pie de pagina
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("cfrFob", "fob");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("cfrFob", "cfr");
                                    else
                                        mapPar.put("cfrFob", "");

                                    mapPar.put("imgRut", strRutImg);
                                    
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    break;
                                case 419://nota de pedido incompleta(se envia al seguro y a ....)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRING) + " / " + objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP));
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC));
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC));
                                    mapPar.put("codDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC));
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepAra", strSQLSubRepAra);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("strSQLSubRepCarPag", strSQLSubRepCarPag);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    mapPar.put("totMts", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fobApr", new BigDecimal("0"));
                                    mapPar.put("valTotPal", strValDocPal);
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    //para colocar en el pie de pagina
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("cfrFob", "fob");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("cfrFob", "cfr");
                                    else
                                        mapPar.put("cfrFob", "");

                                    mapPar.put("imgRut", strRutImg);

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    break;

                                case 472://compras locales
                                case 474://compras locales(bodega)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    
                                    //Inicializar los parametros que se van a pasar al reporte.                                    
                                    mapPar.put("strCamAudRpt", "" + (objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRING) + " / " +  objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_TXT_USRMOD) + " / " + objParSis.getNombreUsuario()) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + " v0.1    ");
                                    mapPar.put("codEmp", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP));
                                    mapPar.put("codLoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC));
                                    mapPar.put("codTipDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC));
                                    mapPar.put("codDoc", objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC));
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRepItm", strSQLSubRepItm);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("imgCodImp", txtCodImp.getText());
                                    mapPar.put("NomPaiEmbPto", strNomPaiEmbPto);
                                    
                                    //el valor me sale mal en el reporte asi q mejor lo envio por parametro
                                    if( (optTmFob.isSelected()) || (optTmCfr.isSelected()) ){
                                        mapPar.put("totMts", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_TON_MET).toString())));//SUMA DE LA CANTIDAD
                                    }
                                    else{
                                        mapPar.put("totMts", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_CAN_PZA).toString())));//SUMA DE LA CANTIDAD
                                    }                                    
                                    
                                    mapPar.put("fleApr", new BigDecimal("0"));
                                    mapPar.put("cfrApr", new BigDecimal(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR)==null?"0":(objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString().equals("")?"0":objImp32_02.getTblTot().getValueAt(0, objImp32_02.INT_TBL_DAT_VAL_CFR).toString())));//SUMA DE LA CANTIDAD
                                    mapPar.put("fobApr", new BigDecimal("0"));
                                    mapPar.put("valTotPal", strValDocPal);
                                    //para colocar en la columna de cantidad
                                    if( (optTmFob.isSelected()) || optTmCfr.isSelected() )
                                        mapPar.put("medCan", "TM");
                                    else if( (optPzaFob.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("medCan", "PCS");
                                    else
                                        mapPar.put("medCan", "");
                                    //para colocar en el pie de pagina
                                    if( (optTmFob.isSelected()) || optPzaFob.isSelected() )
                                        mapPar.put("cfrFob", "fob");
                                    else if( (optTmCfr.isSelected()) || optPzaCfr.isSelected() )
                                        mapPar.put("cfrFob", "cfr");
                                    else
                                        mapPar.put("cfrFob", "");

                                    mapPar.put("imgRut", strRutImg);
                                    

                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }
                conIns.close();
                conIns=null;
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    /**
     * Esta función permite obtener los exportadores ingresados en el detalle.
     * @return los exportadores de manera agrupada, es decir,
     * si existen 10 registros con el mismo exportador devuelve 1 solo registro en el arraylist.
     */
    private ArrayList getDatExp(){
        String strExp="";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        int intCodExp=0;
        arlDatExp = new ArrayList();
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strExp ="";
                for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                    intCodExp=Integer.parseInt(objTblModImp32_02.getValueAt(i,objImp32_02.INT_TBL_DAT_COD_EXP)==null?"0":(objTblModImp32_02.getValueAt(i,objImp32_02.INT_TBL_DAT_COD_EXP).toString().equals("")?"0":objTblModImp32_02.getValueAt(i,objImp32_02.INT_TBL_DAT_COD_EXP).toString()));
                    //Armar sentencia SQL
                    if(i==0) {
                        strExp+=" SELECT * FROM (";
                    }
                    strExp+="     SELECT CAST("+intCodExp+" AS INTEGER) AS co_exp";

                    if(i<objTblModImp32_02.getRowCountTrue()-1){
                        strExp+=" UNION ALL";
                    }
                    else{
                        strExp+=" ) as a WHERE a.co_exp <>0 ";
                        strExp+=" GROUP BY a.co_exp ";
                        strExp+=" ORDER BY a.co_exp ";
                    }
                }
                //System.out.println("getDatExp:  " + strExp);         
                rstLoc=stmLoc.executeQuery(strExp);
                arlDatExp = new ArrayList();
                while(rstLoc.next())
                {
                    arlRegExp = new ArrayList();
                    arlRegExp.add(INT_ARL_DET_COD_EXP,   rstLoc.getInt("co_exp"));
                    arlDatExp.add(arlRegExp);
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
        return arlDatExp;
    }
    
    /**
     * Función que permite conocer si el numero de pedido ya existe
     * @return 
     */
    private boolean isExisteNumeroPedido(String strNumPed){
        boolean blnRes=false;
        Connection conExiNumPed;
        Statement stmExiNumPed;
        ResultSet rstExiNumPed;
        try{
            conExiNumPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiNumPed!=null){
                stmExiNumPed=conExiNumPed.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabNotPedImp AS a1";
                strSQL+=" WHERE a1.st_Reg IN ('A') AND (a1.st_doc IN ('A') OR a1.st_doc IS NULL)";
                strSQL+=" AND a1.tx_numDoc2 LIKE "+objUti.codificar(strNumPed)+"";
                strSQL+=" AND (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) LIKE "+objUti.codificar(strNumPed)+"";
                if(!txtCodDoc.getText().equals("")){
                    strSQL+=" AND a1.co_doc <> "+txtCodDoc.getText(); /* Para que valida entre los demas documentos. */
                }                
                rstExiNumPed=stmExiNumPed.executeQuery(strSQL);
                if(rstExiNumPed.next())
                    blnRes=true;
                conExiNumPed.close();
                conExiNumPed=null;
                stmExiNumPed.close();
                stmExiNumPed=null;
                rstExiNumPed.close();
                rstExiNumPed=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
   
    
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo(){
        strAux="";
        strAux+="" + txtDesCorTipDoc.getText();
        strAux+=" # " + txtNumDoc.getText();
        strAux+="; Importador: " + txtNomImp.getText();
        strAux+="; Exportador: " + txtNomExp2.getText();
        strAux+="; Pedido: " + txtVerNumPed.getText();
        objAsiDia.setGlosa(strAux);
    }
    
    
    
    private boolean generaAsiDia(){
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
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        
        try{
            if( (objTooBar.getEstado()=='n') ){
                ///para generar el asiento de diario
                objAsiDia.inicializar();            
                vecDatDia.clear();

                if(objTblModImp32_02.getRowCountTrue()>0){
                    for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                        bgdValTotFobCfr=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                        bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
                    }
                }
                //System.out.println("bgdSumValTotFobCfr: " + bgdSumValTotFobCfr);

                //total - debe              
                if( bgdSumValTotFobCfr.compareTo(BigDecimal.ZERO)>0  ){
                    if(chkCreCta.isSelected()){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedActIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfr);
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                        //total - haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedPasIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfr);
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//En cuentas generales tbm_cabTipDoc.co_ctaDeb y tbm_cabTipDoc.co_ctaHab -  "NO SE CREAN CUENTAS"
                        if(getCodigoCuentaDebeGral()){
                            if(getCodigoCuentaHaberGral()){
                                if(arlDatCtaDeb.size()>0){
                                    if(arlDatCtaHab.size()>0){
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfr);
                                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);

                                        //total - haber
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfr);
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("vecDatDia: " + vecDatDia.toString());
                objAsiDia.setDetalleDiario(vecDatDia);
                objAsiDia.setGeneracionDiario((byte)2);
                actualizarGlo();
            }            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    /**
     * Función que permite generar el asiento de diario de reversión
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean generaAsiDiaRevNP(){
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
        
        try{
            if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') || (objTooBar.getEstado()=='a') || (objTooBar.getEstado()=='e') ){//x,m: modificar, a:anular, e:eliminar
                ///para generar el asiento de diario
                objAsiDiaRev.inicializar();            
                vecDatDia.clear();            

                System.out.println("generaAsiDiaRevNP-bgdSumValTotFobCfrDb: " + bgdSumValTotFobCfrDb);

                //total - debe              
                if( bgdSumValTotFobCfrDb.compareTo(BigDecimal.ZERO)>0  ){
                    if(chkCreCta.isSelected()){
                        //Diario de reversión
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedPasIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfrDb);
                        vecRegDia.add(INT_VEC_DIA_HAB, "");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                        //total - haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedActIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfrDb);
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//En cuentas generales tbm_cabTipDoc.co_ctaDeb y tbm_cabTipDoc.co_ctaHab -  "NO SE CREAN CUENTAS"

                        if(getCodigoCuentaDebeGral()){
                            if(getCodigoCuentaHaberGral()){

                                System.out.println("arlDatCtaDeb: " + arlDatCtaDeb.toString());
                                System.out.println("arlDatCtaHab: " + arlDatCtaHab.toString());

                                if(arlDatCtaDeb.size()>0){
                                    if(arlDatCtaHab.size()>0){
                                        //Diario de reversión
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfrDb);
                                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);

                                        //total - haber
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "");
                                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfrDb);
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);

                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("vecDatDiaLocRev: " + vecDatDia.toString());
                objAsiDiaRev.setDetalleDiario(vecDatDia);
                objAsiDiaRev.setGeneracionDiario((byte)2);
                actualizarGlo();
            }


        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean generaAsiDiaNue(){
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
        
        BigDecimal bgdValTotFobCfr=new BigDecimal("0");
        BigDecimal bgdSumValTotFobCfr=new BigDecimal("0");
        
        try{
            if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                ///para generar el asiento de diario
                objAsiDiaNue.inicializar();            
                vecDatDia.clear();

                if(objTblModImp32_02.getRowCountTrue()>0){
                    for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                        bgdValTotFobCfr=new BigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_FOB_CFR).toString()));
                        bgdSumValTotFobCfr=bgdSumValTotFobCfr.add(bgdValTotFobCfr);
                    }
                }
                System.out.println("bgdSumValTotFobCfr: " + bgdSumValTotFobCfr);

                //total - debe              
                if( bgdSumValTotFobCfr.compareTo(BigDecimal.ZERO)>0  ){
                    if(chkCreCta.isSelected()){
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedActIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaActAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfr);
                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);

                        //total - haber
                        vecRegDia=new java.util.Vector();
                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaPedPasIns);//intCodCtaTot
                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaPasAsiDia);
                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfr);
                        vecRegDia.add(INT_VEC_DIA_REF, "");
                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                        vecDatDia.add(vecRegDia);
                    }
                    else{//En cuentas generales tbm_cabTipDoc.co_ctaDeb y tbm_cabTipDoc.co_ctaHab -  "NO SE CREAN CUENTAS"
                        if(getCodigoCuentaDebeGral()){
                            if(getCodigoCuentaHaberGral()){
                                if(arlDatCtaDeb.size()>0){
                                    if(arlDatCtaHab.size()>0){
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaDeb, 0, INT_ARL_CTA_DEB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "" + bgdSumValTotFobCfr);
                                        vecRegDia.add(INT_VEC_DIA_HAB, "0");
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);

                                        //total - haber
                                        vecRegDia=new java.util.Vector();
                                        vecRegDia.add(INT_VEC_DIA_LIN, "");
                                        vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_COD));//intCodCtaTot
                                        vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NUM));
                                        vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                                        vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + objUti.getStringValueAt(arlDatCtaHab, 0, INT_ARL_CTA_HAB_NOM));
                                        vecRegDia.add(INT_VEC_DIA_DEB, "0");
                                        vecRegDia.add(INT_VEC_DIA_HAB, "" + bgdSumValTotFobCfr);
                                        vecRegDia.add(INT_VEC_DIA_REF, "");
                                        vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                                        vecDatDia.add(vecRegDia);
                                    }
                                }
                            }
                        }
                    }
                }

                System.out.println("vecDatDiaNue: " + vecDatDia.toString());
                objAsiDiaNue.setDetalleDiario(vecDatDia);
                objAsiDiaNue.setGeneracionDiario((byte)2);
                actualizarGlo();
            }

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    /**
     * Función que permite obtener información de la cuenta del debe configurada según el tipo de documento
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean getCodigoCuentaDebeGral(){
        boolean blnRes=true;
        Connection conCtaDebGrl;
        Statement stmCtaDebGrl;
        ResultSet rstCtaDebGrl;
        try{
            conCtaDebGrl=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCtaDebGrl!=null){
                arlDatCtaDeb.clear();
                stmCtaDebGrl=conCtaDebGrl.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_nomCtaDeb";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar AS tx_nomCtaDeb";
                strSQL+="	FROM tbm_plaCta AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + txtCodImp.getText() + "";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_tipDoc, a1.co_ctaDeb";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + txtCodImp.getText() + " AND a1.co_loc=" + strCodLocPre + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_ctaDeb";
                strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_nomCtaDeb";
                System.out.println("getCodigoCuentaDebeGral: " + strSQL);
                rstCtaDebGrl=stmCtaDebGrl.executeQuery(strSQL);
                if(rstCtaDebGrl.next()){
                    arlRegCtaDeb=new ArrayList();
                    arlRegCtaDeb.add(INT_ARL_CTA_DEB_COD, rstCtaDebGrl.getString("co_cta"));
                    arlRegCtaDeb.add(INT_ARL_CTA_DEB_NUM, rstCtaDebGrl.getString("tx_codCta"));
                    arlRegCtaDeb.add(INT_ARL_CTA_DEB_NOM, rstCtaDebGrl.getString("tx_nomCtaDeb"));
                    arlDatCtaDeb.add(arlRegCtaDeb);
                }
                System.out.println("arlDatCtaDeb: " + arlDatCtaDeb.toString());
                conCtaDebGrl.close();
                conCtaDebGrl=null;
                stmCtaDebGrl.close();
                stmCtaDebGrl=null;
                rstCtaDebGrl.close();
                rstCtaDebGrl=null;

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
     * Función que permite obtener información de la cuenta del haber configurada según el tipo de documento
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean getCodigoCuentaHaberGral(){
        boolean blnRes=true;
        Connection conCtaHabGrl;
        Statement stmCtaHabGrl;
        ResultSet rstCtaHabGrl;
        try{
            conCtaHabGrl=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conCtaHabGrl!=null){
                arlDatCtaHab.clear();
                stmCtaHabGrl=conCtaHabGrl.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_nomCtaHab";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp, a1.co_cta, a1.tx_codCta, a1.tx_desLar AS tx_nomCtaHab";
                strSQL+="	FROM tbm_plaCta AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + txtCodImp.getText() + "";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_tipDoc, a1.co_ctaHab";
                strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                strSQL+=" 	WHERE a1.co_emp=" + txtCodImp.getText() + " AND a1.co_loc=" + strCodLocPre + " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_ctaHab";
                strSQL+=" GROUP BY b1.co_emp, b1.co_cta, b1.tx_codCta, b1.tx_nomCtaHab";
                System.out.println("getCodigoCuentaHaberGral: " + strSQL);
                rstCtaHabGrl=stmCtaHabGrl.executeQuery(strSQL);
                if(rstCtaHabGrl.next()){
                    arlRegCtaHab=new ArrayList();
                    arlRegCtaHab.add(INT_ARL_CTA_HAB_COD, rstCtaHabGrl.getString("co_cta"));
                    arlRegCtaHab.add(INT_ARL_CTA_HAB_NUM, rstCtaHabGrl.getString("tx_codCta"));
                    arlRegCtaHab.add(INT_ARL_CTA_HAB_NOM, rstCtaHabGrl.getString("tx_nomCtaHab"));
                    arlDatCtaHab.add(arlRegCtaHab);
                }
                System.out.println("arlDatCtaHab: " + arlDatCtaHab.toString());
                conCtaHabGrl.close();
                conCtaHabGrl=null;
                stmCtaHabGrl.close();
                stmCtaHabGrl=null;
                rstCtaHabGrl.close();
                rstCtaHabGrl=null;
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
     * Esta función devuelve el local predeterminado de la empresa seleccionada
     * @return entero: si devuelve el local predeterminado
     * <BR>false: En el caso contrario.
     */
    private boolean getCodigoLocalPredeterminado(int codigoEmpresa){
        boolean blnRes=true;
        Statement stmLocPre;
        ResultSet rstLocPre;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stmLocPre=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_loc FROM tbm_loc";
                strSQL+=" WHERE co_emp=" + codigoEmpresa + "";
                strSQL+=" AND st_reg='P'";
                //System.out.println("getCodigoLocalPredeterminado: " + strSQL);
                rstLocPre=stmLocPre.executeQuery(strSQL);
                if(rstLocPre.next()){
                    strCodLocPre=rstLocPre.getString("co_loc");
                }
                stmLocPre.close();
                stmLocPre=null;
                rstLocPre.close();
                rstLocPre=null;
                con.close();
                con=null;
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabNotPedImp_RV(){
        int intUltReg=-1;
        boolean blnRes=true;
        intNumNotPedDia=-1;
        int intVerNumPed=0;
        int intNumUltDoc=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+="      , CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=";
                strSQL+=" (";
                strSQL+=" SELECT co_tipDoc";
                strSQL+=" FROM tbr_tipDocPrg";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND st_reg='S'";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intNumUltDoc=(rst.getInt("ne_ultDoc")+1);
                }
                txtNumDoc.setText("" + intNumUltDoc);

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_cabnotpedimp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    intUltReg=rst.getInt("co_doc");
                }
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                strSQL="";
                strSQL+=" SELECT (COUNT(a1.co_tipDoc)) AS ne_numDocNotPed";
                strSQL+=" FROM tbm_cabNotPedImp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND CAST(fe_ing AS DATE)=CURRENT_DATE";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intNumNotPedDia=rst.getInt("ne_numDocNotPed");
                }
                intNumNotPedDia++;
                
                intVerNumPed=(txtVerPed.getText().length()==0?1:(Integer.parseInt(txtVerPed.getText())+1));
                txtVerPed.setText(""+intVerNumPed);
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabNotPedImp(";
                strSQL+="      co_emp, co_loc, co_tipdoc, co_doc";
                strSQL+="    , fe_doc";
                strSQL+="    , ne_numdoc, tx_numdoc2, ne_tipnotped";
                strSQL+="    , co_imp, co_prv, co_exp, co_ciupueemb, co_seg, co_forpag";
                strSQL+="    , co_mesemb, co_mespue, co_mesarr, fe_emb, fe_pue, fe_arr";
                strSQL+="    , tx_tipemp, nd_valdoc, nd_pestotkgr, st_notpedlis, st_imp, tx_obs1";
                strSQL+="    , tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, ne_aniEmb, ne_verNumPed, tx_numdoc3, st_doc, nd_porISD";
                if(dtpFecColPed.isFecha())  
                    strSQL+="   , fe_colPed"; 
                if(dtpFecProPed.isFecha())  
                    strSQL+="   , fe_pro"; 
                
                strSQL+="   , tx_modPed";
                strSQL+=" )";   
                strSQL+=" (";
                strSQL+="   SELECT co_emp, co_loc, co_tipdoc, " + txtCodDoc.getText() + " AS co_doc";
                strSQL+="       , '" + objUti.formatearFecha(datFecAux,"yyyy-MM-dd") + "' AS fe_doc";
                strSQL+="       , ne_numdoc, (tx_numdoc3||'_'||" + objUti.codificar(txtVerPed.getText()) + "), ne_tipnotped";
                strSQL+="       , co_imp, co_prv, co_exp, co_ciupueemb, co_seg, co_forpag";
                 strSQL+="      , co_mesemb, co_mespue, co_mesarr, fe_emb, fe_pue, fe_arr";
                strSQL+="       , tx_tipemp, (nd_valdoc*-1), nd_pestotkgr, st_notpedlis, st_imp, tx_obs1";
                strSQL+="       , tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, ne_aniEmb";
                strSQL+="       , " + objUti.codificar(txtVerPed.getText()) + "";
                strSQL+="       , tx_numdoc3";
                strSQL+="       , 'R'";
                strSQL+="       , nd_porISD";
                if(dtpFecColPed.isFecha())
                    strSQL+="   , fe_colPed";
                if(dtpFecProPed.isFecha())
                    strSQL+="   , fe_pro";
                strSQL+="   , tx_modPed";
                    
                strSQL+="   FROM tbm_cabNotPedImp";                
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" );";
                
                //Se actualiza el estado del documento origen
                strSQL+=" UPDATE tbm_cabNotPedImp";
                strSQL+=" SET st_doc='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=";";
                
                //Se agrega las cuentas contables de activo y pasivo en el nuevo documento
                strSQL+=" UPDATE tbm_cabNotPedImp";
                strSQL+=" SET co_ctaAct=" + intCodCtaPedActIns + "";
                strSQL+="   , co_ctaPas=" + intCodCtaPedPasIns + "";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                strSQL+=";";
                System.out.println("insertar_tbmCabNotPedImp_RV: " + strSQL);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetNotPedImp_RV(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" INSERT INTO tbm_detnotpedimp(";
                strSQL+="     co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, co_exp, nd_ara, nd_can";  
                strSQL+="   , nd_preuni, nd_valtotfobcfr, nd_canTonMet, nd_valfle, nd_valcfr, nd_valtotara";    
                strSQL+="   , nd_valtotgas, nd_valtotcos, nd_cosuni";     
                strSQL+="   , nd_valISD, st_impISDCreTri";
                strSQL+=" )";
                strSQL+=" (";
                strSQL+="   SELECT co_emp, co_loc, co_tipdoc, " + txtCodDoc.getText() + " AS co_doc, co_reg, co_itm, co_exp, nd_ara, (nd_can*-1)";
                strSQL+="        , nd_preuni";
                strSQL+="        , (nd_valtotfobcfr*-1), (nd_canTonMet*-1), (nd_valfle*-1), (nd_valcfr*-1), (nd_valtotara*-1)";
                strSQL+="        , (nd_valtotgas*-1), (nd_valtotcos*-1)";
                strSQL+="        , nd_cosuni";
                strSQL+="        , nd_valISD, st_impISDCreTri";
                strSQL+="   FROM tbm_detNotPedImp";
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=" )";
                System.out.println("insertar_tbmDetNotPedImp_RV: " + strSQL);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCarPagNotPedImp_RV(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_carpagnotpedimp(";
                strSQL+="  co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_carpag, nd_valcarpag )";
                strSQL+=" (";
                strSQL+="   SELECT co_emp, co_loc, co_tipdoc, " + txtCodDoc.getText() + " AS co_doc, co_reg, co_carpag, (nd_valcarpag*-1)";
                strSQL+="   FROM tbm_carPagNotPedImp";
                strSQL+="   WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+="   AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+="   AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="   AND co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                strSQL+=");";
                System.out.println("insertar_tbmCarPagNotPedImp_RV: " + strSQL);
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
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabTipDoc_RV(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                System.out.println("actualizar_tbmCabTipDoc_RV: " + strSQL);
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
    
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa(instancia anterior)
     * @return true Si existe seguimiento para la instancia anterior a la Nota de Pedido(Instancia anterior: Nota de Pedido Previa)
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotPedPreImp_DB(){
        boolean blnRes=false;
        Object objCodSegNotPedPre=null;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" 	SELECT a3.co_seg AS co_segNotPed, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 	FROM tbm_cabNotPedImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" 		ON a1.co_emp=a3.co_empRelCabNotPedImp AND a1.co_loc=a3.co_locRelCabNotPedImp";
                strSQL+=" 		AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedImp AND a1.co_doc=a3.co_docRelCabNotPedImp";
                strSQL+=" 	WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP) + "";
                strSQL+=" 	AND a1.co_loc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC) + "";
                strSQL+=" 	AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC) + "";
                strSQL+=" 	AND a1.co_doc=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC) + "";
                System.out.println("getCodSegNotPedPreImp_DB: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    objCodSegNotPedPre=rst.getObject("co_segNotPed");
                    blnRes=true;
                }
                objCodSegInsAnt=objCodSegNotPedPre;
                System.out.println("objCodSegInsAnt: " + objCodSegInsAnt);
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta función actualiza el campo "ne_ultDoc" de la tabla "tbm_cabTipDoc".
     * @param con El objeto que contiene la conexiï¿½n a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @return true: Si se pudo actualizar el campo.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabNotPedPreImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabNotPedPreImp";
                strSQL+=" SET co_locRel=" + objParSis.getCodigoLocal() + ""; 
                strSQL+="   , co_tipDocRel=" + txtCodTipDoc.getText() + "";  
                strSQL+="   , co_docRel=" + txtCodDoc.getText() + ""; 
                strSQL+=" WHERE co_locRel=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDocRel=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);
                strSQL+=" AND co_docRel=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC);
                System.out.println("actualizar_tbmCabNotPedPreImp: " + strSQL);
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
    
    /**
     * Función que permite crear cuentas contables de la Nota de Pedido cuando el Pedido ya existe
     * (modo modificar) 
     * @return true Si se pudo crear las cuentas
     * <BR> false Caso contrario
     */
    private boolean generaCtaPed(){
        boolean blnRes=true;
        boolean blnOptCreCtaPas=true;
        Statement stmGenCta;
        ResultSet rstGenCta;
        try{            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                stmGenCta=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp AS co_imp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" AND a2.co_cta=a1.co_ctaCabActImp";
                strSQL+=" ORDER BY a1.co_emp";
                rstGenCta=stmGenCta.executeQuery(strSQL);
                while(rstGenCta.next()){
                    
                    if(crearCuentaPedido(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                        if(insertarCtaPed(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                            if(insertaTbmSalCtaPed(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                                if(rstGenCta.getString("co_imp").equals(txtCodImp.getText())){
                                    if(setPermisosUsrCtaPed(rstGenCta.getString("co_imp"))){
                                        con.commit();
                                        blnRes=true;
                                        enviarCorEleCreCtaPed();
                                    }
                                    else
                                        con.rollback();
                                }
                                else{
                                    con.commit();
                                    blnRes=true;
                                    enviarCorEleCreCtaPed();
                                }
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
            
                
                con.close();
                con=null;
                stmGenCta.close();
                stmGenCta=null;
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
     * Función que permite crear cuentas contables de la Nota de Pedido cuando el Pedido ya existe
     * (modo modificar) 
     * @return true Si se pudo crear las cuentas
     * <BR> false Caso contrario
     */
    private boolean generaCtaPedIns(){
        boolean blnRes=true;
        boolean blnOptCreCtaPas=true;
        Statement stmGenCta;
        ResultSet rstGenCta;
        try{            
            if(con!=null){
                stmGenCta=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp AS co_imp, a1.co_ctaCabActImp AS co_ctaPad, a2.ne_niv";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
                strSQL+=" AND a2.co_cta=a1.co_ctaCabActImp";
                strSQL+=" ORDER BY a1.co_emp";
                rstGenCta=stmGenCta.executeQuery(strSQL);
                while(rstGenCta.next()){
                    
                    if(crearCuentaPedido(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                        if(insertarCtaPed(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                            if(insertaTbmSalCtaPed(blnOptCreCtaPas, rstGenCta.getInt("co_imp"))){
                                if(rstGenCta.getString("co_imp").equals(txtCodImp.getText())){
                                    if(setPermisosUsrCtaPed(rstGenCta.getString("co_imp"))){
                                        con.commit();
                                        blnRes=true;
                                        enviarCorEleCreCtaPed();
                                    }
                                    else
                                        con.rollback();
                                }
                                else{
                                    con.commit();
                                    blnRes=true;
                                    enviarCorEleCreCtaPed();
                                }
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
                stmGenCta.close();
                stmGenCta=null;
                rstGenCta.close();
                rstGenCta=null;
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
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorEleCreCtaPed(){
        boolean blnRes=false;        
        String strSbjMsg="" + intNumNotPedDia + ") " + txtVerNumPed.getText() + " (" + objParSis.getNombreMenu() + ")";
        int intCodCfgCorEle=17;
        try{
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Creación de cuentas pedido<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th colspan='7'> Pedido # " + txtVerNumPed.getText() + "</th>";
            strMsg+="</tr>";

            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th>Código Cuenta Activo</th>";
            strMsg+="	<th>Número Cuenta Activo</th>";
            strMsg+="	<th>Nombre Cuenta Activo</th>";
            strMsg+="</tr>";

            strMsg+="<tr bgcolor='#FF4000'>";
            strMsg+="	<td align=right>" + intCodCtaPedActIns + "</td>";
            strMsg+="	<td align=left>" + strNumCtaActAsiDia + "</td>";
            strMsg+="	<td align=left>" + strNomCtaActAsiDia + "</td>";
            strMsg+="</tr>";
            
            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th>Código Cuenta Pasivo</th>";
            strMsg+="	<th>Número Cuenta Pasivo</th>";
            strMsg+="	<th>Nombre Cuenta Pasivo</th>";
            strMsg+="</tr>";
            
            strMsg+="<tr bgcolor='#FF4000'>";
            strMsg+="	<td align=right>" + intCodCtaPedPasIns + "</td>";
            strMsg+="	<td align=left>" + strNumCtaPasAsiDia + "</td>";
            strMsg+="	<td align=left>" + strNomCtaPasAsiDia + "</td>";
            strMsg+="</tr>";
            
            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                mostrarMsgInf("<HTML>Estimado usuario, <FONT COLOR=\"blue\">Creación de cuentas</FONT></HTML>");
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;     
    }
    
    
    /**
     * Esta función permite modificar la relación de los pagos con los pedidos.
     * <BR> Cada vez que se realice una modificación del pedido, se generará un nuevo documento
     * Este nuevo pedido, quedará enlazado a todos los pagos anteriores.
     * @return true: Si se pudo modificar la relación de los pagos con los pedidos.
     * <BR>false: En el caso contrario.
     */
    private boolean modifica_tbrDetConIntCarPagInsImp(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbr_DetConIntCarPagInsImp";
                strSQL+=" SET co_empRelNotPed=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   , co_locRelNotPed=" + objParSis.getCodigoLocal() + "";
                strSQL+="   , co_tipDocRelNotPed=" + txtCodTipDoc.getText() + "";
                strSQL+="   , co_docRelNotPed=" + txtCodDoc.getText() + "";
                strSQL+=" WHERE co_empRelNotPed=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_locRelNotPed=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDocRelNotPed=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC);  
                strSQL+=" AND co_docRelNotPed=" + objUti.getIntValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC); 
                strSQL+=";";
                System.out.println("modifica_tbrDetConIntCarPagInsImp: " + strSQL);
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
     * Función que permite calcular ISD de Crédito Tributario e ISD de Gasto
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcula_ISD(){
        boolean blnRes=true;
        Connection conIsd;
        Statement stmIsd;
        ResultSet rstIsd;
        int intCodEmpIsd, intCodLocIsd, intCodTipDocIsd, intCodDocIsd;
        BigDecimal bdeValISdCreTri=BigDecimal.ZERO;
        BigDecimal bdeValISdGto=BigDecimal.ZERO;
        String strSQLUpd="";
        try{
            conIsd=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conIsd!=null){
                stmIsd=conIsd.createStatement();
                /*Nota de Pedido*/
                strSQL="";
                strSQL+=" SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.tx_numDoc3, d1.fe_doc, d1.nd_valTotFobCfr";
                strSQL+=" , ROUND((d1.nd_valTotFobCfr*0.05),2) AS nd_valISDTotFobCfr, d1.nd_valDetIsdCreTri, d1.nd_valDetIsdGto";
                strSQL+=" , ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2) AS nd_porISDCreTri";
                strSQL+=" , (100-ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2)) AS nd_porISDGto";
                strSQL+=" , d1.nd_valCarPagIntFac, d1.nd_valCarPagGtoOriFac";
                strSQL+=" , ((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05) AS nd_valISDIntFac_GtoOriFac";
                strSQL+=" /*, ROUND((((ROUND(((ROUND((d1.nd_valCarPagIntFac*0.05),2)) * (ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2) AS nd_valISDCreTriIntFac*/";
                strSQL+=" /*, ROUND((((ROUND(((ROUND((d1.nd_valCarPagIntFac*0.05),2)) * (ROUND((((d1.nd_valDetIsdGto*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2) AS nd_valISDGtoIntFac*/";
                strSQL+=" , ROUND(((d1.nd_valDetIsdCreTri + ROUND((((ROUND(((ROUND(((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05),2)) * (ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2))),2) AS valISDCreTriFin";
                strSQL+=" , ROUND(((d1.nd_valDetIsdGto + ROUND((((ROUND(((ROUND(((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05),2)) * (ROUND((((d1.nd_valDetIsdGto*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2))),2) AS valISDGtoFin";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc3, c1.fe_doc, c1.nd_valTotFobCfr, c1.nd_valDetIsdCreTri, c1.nd_valDetIsdGto";
                strSQL+=" 	, c1.co_carPagValFac, c1.nd_valCarPagValFac, c1.co_carPagGtoOriFac, c1.nd_valCarPagGtoOriFac, c1.co_carPagIntFac, c1.nd_valCarPagIntFac";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc, b1.st_notPedLis";
                strSQL+=" 		, b4.co_carPag AS co_carPagValFac, b4.tx_nom, b4.nd_valCarPagValFac";
                strSQL+=" 		, SUM(b1.nd_valTotFobCfr) AS nd_valTotFobCfr, SUM(b1.nd_valIsdCreTri) AS nd_valDetIsdCreTri, SUM(b1.nd_valIsdGto) AS nd_valDetIsdGto";
                strSQL+=" 		, b2.co_carPag AS co_carPagGtoOriFac, b2.tx_nom, CASE WHEN b2.nd_valCarPagGtoOriFac IS NULL THEN 0 ELSE b2.nd_valCarPagGtoOriFac END nd_valCarPagGtoOriFac";
                strSQL+=" 		, b3.co_carPag AS co_carPagIntFac, b3.tx_nom, CASE WHEN b3.nd_valCarPagIntFac IS NULL THEN 0 ELSE b3.nd_valCarPagIntFac END nd_valCarPagIntFac";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.tx_numDoc3, a1.fe_doc, a1.st_notPedLis";
                strSQL+=" 			, CASE WHEN a2.st_impIsdCreTri='S' THEN a2.nd_valIsd ELSE 0 END AS nd_valIsdCreTri";
                strSQL+=" 			, CASE WHEN a2.st_impIsdCreTri='N' THEN a2.nd_valIsd ELSE 0 END AS nd_valIsdGto";
                strSQL+=" 			, a2.nd_valTotFobCfr";
                strSQL+=" 			FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			WHERE a1.st_reg='A' AND a1.st_doc='A' AND a1.fe_doc>='2019-01-01' AND a1.st_notPedLis='N'";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.tx_numDoc3, a1.fe_doc, a1.st_notPedLis";
                strSQL+=" 			, a2.st_impIsdCreTri, a2.nd_valIsd, a2.nd_valTotFobCfr";
                strSQL+=" 			ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		LEFT OUTER JOIN(";
                strSQL+=" 			SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagGtoOriFac";
                strSQL+=" 			FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 			ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 			WHERE a2.co_carPag IN(24)";
                strSQL+=" 		) AS b2";
                strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 		LEFT OUTER JOIN(";
                strSQL+=" 			SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagIntFac";
                strSQL+=" 			FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 			ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 			WHERE a2.co_carPag IN(25)";
                strSQL+=" 		) AS b3";
                strSQL+=" 		ON b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc AND b1.co_doc=b3.co_doc";
                strSQL+=" 		LEFT OUTER JOIN(";
                strSQL+=" 			SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagValFac";
                strSQL+=" 			FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 			ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 			WHERE a2.co_carPag IN(22)";
                strSQL+=" 		) AS b4";
                strSQL+=" 		ON b1.co_emp=b4.co_emp AND b1.co_loc=b4.co_loc AND b1.co_tipDoc=b4.co_tipDoc AND b1.co_doc=b4.co_doc";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc, b1.st_notPedLis, b4.co_carPag";
                strSQL+=" 		, b4.tx_nom, b4.nd_valCarPagValFac, b2.co_carPag, b2.tx_nom, b2.nd_valCarPagGtoOriFac, b3.co_carPag, b3.tx_nom, b3.nd_valCarPagIntFac";
                strSQL+=" 		ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_valTotFobCfr<>0/*Quitar AND c1.co_doc=3065 3241*/";
                strSQL+=" 	ORDER BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc3";
                strSQL+=" ) AS d1";
                strSQL+=" ORDER BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.tx_numDoc3, d1.fe_doc";
                rstIsd=stmIsd.executeQuery(strSQL);
                strSQLUpd="";
                while(rstIsd.next()){
                    intCodEmpIsd=rstIsd.getInt("co_emp");
                    intCodLocIsd=rstIsd.getInt("co_loc");
                    intCodTipDocIsd=rstIsd.getInt("co_tipDoc");
                    intCodDocIsd=rstIsd.getInt("co_doc");
                    bdeValISdCreTri=rstIsd.getBigDecimal("valISDCreTriFin");
                    bdeValISdGto=rstIsd.getBigDecimal("valISDGtoFin");
                    
                    //Credito Tributario
                    strSQL="";
                    strSQL+=" UPDATE tbm_carPagNotPedImp";
                    strSQL+=" SET nd_valCarPag=" + bdeValISdCreTri + "";
                    strSQL+=" WHERE co_emp=" + intCodEmpIsd + "";
                    strSQL+=" AND co_loc=" + intCodLocIsd + "";
                    strSQL+=" AND co_tipDoc=" + intCodTipDocIsd + "";
                    strSQL+=" AND co_doc=" + intCodDocIsd + "";
                    strSQL+=" AND co_carPag=" + objZafImp.INT_COD_CAR_PAG_ISD_CRE_TRI + "";
                    strSQL+=";";
                    strSQL+=" UPDATE tbm_carPagNotPedImp";
                    strSQL+=" SET nd_valCarPag=" + bdeValISdGto + "";
                    strSQL+=" WHERE co_emp=" + intCodEmpIsd + "";
                    strSQL+=" AND co_loc=" + intCodLocIsd + "";
                    strSQL+=" AND co_tipDoc=" + intCodTipDocIsd + "";
                    strSQL+=" AND co_doc=" + intCodDocIsd + "";
                    strSQL+=" AND co_carPag=" + objZafImp.INT_COD_CAR_PAG_ISD_GTO + "";
                    strSQL+=";";
                    strSQLUpd+="" + strSQL;
                }
                stmIsd.executeUpdate(strSQLUpd);
                System.out.println("calcula_ISD:" + strSQLUpd);
                
                conIsd.close();
                conIsd=null;
                stmIsd.close();
                stmIsd=null;
                rstIsd.close();
                rstIsd=null;
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
     * Función que permite realizar el cálculo de ISD de Crédito Tributario e ISD de Gasto
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calculaISDCarPag(){
        boolean blnRes=true;
        Statement stmIsdCarPag;
        ResultSet rstIsdCarPag;
        try{
            if(con!=null){
                stmIsdCarPag=con.createStatement();
                strSQL="";
                /*PERMITE AGREGAR LOS VALORES DE ISD EN NOTA DE PEDIDO*/
                strSQL+=" SELECT e1.valISDCreTriFin, e1.valISDGtoFin/*, (e1.nd_valISDTotFobCfr+nd_valIsdIntFac_gtoOriFac) - (e1.valISDCreTriFin+e1.valISDGtoFin) AS nd_dif*/ FROM(";
                strSQL+=" 	SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.tx_numDoc3, d1.fe_doc, d1.nd_valTotFobCfr";
                strSQL+=" 	, ROUND((d1.nd_valTotFobCfr*0.05),2) AS nd_valISDTotFobCfr, d1.nd_valDetIsdCreTri, d1.nd_valDetIsdGto";
                strSQL+=" 	, ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2) AS nd_porISDCreTri";
                strSQL+=" 	, (100-ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2)) AS nd_porISDGto";
                strSQL+=" 	, d1.nd_valCarPagIntFac, d1.nd_valCarPagGtoOriFac";
                strSQL+=" 	, ((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05) AS nd_valISDIntFac_GtoOriFac";
                strSQL+=" 	/*, ROUND((((ROUND(((ROUND((d1.nd_valCarPagIntFac*0.05),2)) * (ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2) AS nd_valISDCreTriIntFac*/";
                strSQL+=" 	/*, ROUND((((ROUND(((ROUND((d1.nd_valCarPagIntFac*0.05),2)) * (ROUND((((d1.nd_valDetIsdGto*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2) AS nd_valISDGtoIntFac*/";
                strSQL+=" 	, ROUND(((d1.nd_valDetIsdCreTri + ROUND((((ROUND(((ROUND(((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05),2)) * (ROUND((((d1.nd_valDetIsdCreTri*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2))),2) AS valISDCreTriFin";
                strSQL+=" 	, ROUND(((d1.nd_valDetIsdGto + ROUND((((ROUND(((ROUND(((d1.nd_valCarPagIntFac+nd_valCarPagGtoOriFac)*0.05),2)) * (ROUND((((d1.nd_valDetIsdGto*100)/(d1.nd_valTotFobCfr*0.05))),2))),2)))/100),2))),2) AS valISDGtoFin";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc3, c1.fe_doc, c1.nd_valTotFobCfr, c1.nd_valDetIsdCreTri, c1.nd_valDetIsdGto";
                strSQL+=" 		, c1.co_carPagValFac, c1.nd_valCarPagValFac, c1.co_carPagGtoOriFac, c1.nd_valCarPagGtoOriFac, c1.co_carPagIntFac, c1.nd_valCarPagIntFac";
                strSQL+=" 		FROM(";
                strSQL+=" 			SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc, b1.st_notPedLis";
                strSQL+=" 			, b4.co_carPag AS co_carPagValFac, b4.tx_nom, b4.nd_valCarPagValFac";
                strSQL+=" 			, SUM(b1.nd_valTotFobCfr) AS nd_valTotFobCfr, SUM(b1.nd_valIsdCreTri) AS nd_valDetIsdCreTri, SUM(b1.nd_valIsdGto) AS nd_valDetIsdGto";
                strSQL+=" 			, b2.co_carPag AS co_carPagGtoOriFac, b2.tx_nom, CASE WHEN b2.nd_valCarPagGtoOriFac IS NULL THEN 0 ELSE b2.nd_valCarPagGtoOriFac END nd_valCarPagGtoOriFac";
                strSQL+=" 			, b3.co_carPag AS co_carPagIntFac, b3.tx_nom, CASE WHEN b3.nd_valCarPagIntFac IS NULL THEN 0 ELSE b3.nd_valCarPagIntFac END nd_valCarPagIntFac";
                strSQL+=" 			FROM(";
                strSQL+=" 				SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.tx_numDoc3, a1.fe_doc, a1.st_notPedLis";
                strSQL+=" 				, CASE WHEN a2.st_impIsdCreTri='S' THEN a2.nd_valIsd ELSE 0 END AS nd_valIsdCreTri";
                strSQL+=" 				, CASE WHEN a2.st_impIsdCreTri='N' THEN a2.nd_valIsd ELSE 0 END AS nd_valIsdGto";
                strSQL+=" 				, a2.nd_valTotFobCfr";
                strSQL+=" 				FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 				ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 				WHERE a1.st_reg='A' AND a1.st_doc='A' /*AND a1.fe_doc>='2019-01-01'*/ AND a1.st_notPedLis='N'";
                strSQL+=" 				GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.tx_numDoc3, a1.fe_doc, a1.st_notPedLis";
                strSQL+=" 				, a2.st_impIsdCreTri, a2.nd_valIsd, a2.nd_valTotFobCfr";
                strSQL+=" 				ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" 			) AS b1";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagGtoOriFac";
                strSQL+=" 				FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 				ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 				WHERE a2.co_carPag IN(24)";
                strSQL+=" 			) AS b2";
                strSQL+=" 			ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagIntFac";
                strSQL+=" 				FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 				ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 				WHERE a2.co_carPag IN(25)";
                strSQL+=" 			) AS b3";
                strSQL+=" 			ON b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc AND b1.co_doc=b3.co_doc";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a1.co_carPag, a1.tx_nom, a2.nd_valCarPag AS nd_valCarPagValFac";
                strSQL+=" 				FROM tbm_carPagImp AS a1 LEFT OUTER JOIN tbm_carPagNotPedImp AS a2";
                strSQL+=" 				ON a1.co_carPag=a2.co_carPag";
                strSQL+=" 				WHERE a2.co_carPag IN(22)";
                strSQL+=" 			) AS b4";
                strSQL+=" 			ON b1.co_emp=b4.co_emp AND b1.co_loc=b4.co_loc AND b1.co_tipDoc=b4.co_tipDoc AND b1.co_doc=b4.co_doc";
                strSQL+=" 			GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc, b1.st_notPedLis, b4.co_carPag";
                strSQL+=" 			, b4.tx_nom, b4.nd_valCarPagValFac, b2.co_carPag, b2.tx_nom, b2.nd_valCarPagGtoOriFac, b3.co_carPag, b3.tx_nom, b3.nd_valCarPagIntFac";
                strSQL+=" 			ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_numDoc3, b1.fe_doc";
                strSQL+=" 		) AS c1";
                strSQL+=" 		WHERE c1.nd_valTotFobCfr<>0/*Quitar AND c1.co_doc=3065 */";
                strSQL+=" 		ORDER BY c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc3";
                strSQL+=" 	) AS d1";
                strSQL+=" 	ORDER BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.tx_numDoc3, d1.fe_doc";
                strSQL+=" ) AS e1";
                //strSQL+=" WHERE (e1.nd_valISDTotFobCfr+nd_valIsdIntFac_gtoOriFac)<>(e1.valISDCreTriFin+e1.valISDGtoFin)";
                strSQL+=" WHERE e1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND e1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND e1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND e1.co_doc=" + txtCodDoc.getText() + "";
                rstIsdCarPag=stmIsdCarPag.executeQuery(strSQL);
                if(rstIsdCarPag.next()){
                    strSQL="";
                    strSQL+=" UPDATE tbm_carPagNotPedImp";
                    strSQL+=" SET nd_valCarPag=" + rstIsdCarPag.getString("valISDCreTriFin") + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_carPag=" + objZafImp.INT_COD_CAR_PAG_ISD_CRE_TRI + "";
                    strSQL+=";";
                    strSQL+=" UPDATE tbm_carPagNotPedImp";
                    strSQL+=" SET nd_valCarPag=" + rstIsdCarPag.getString("valISDGtoFin") + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+=" AND co_carPag=" + objZafImp.INT_COD_CAR_PAG_ISD_GTO + "";
                    strSQL+=";";
                    System.out.println("calculaISDCarPag: " + strSQL);
                    stmIsdCarPag.executeUpdate(strSQL);
                }

                
                stmIsdCarPag.close();
                stmIsdCarPag=null;
                rstIsdCarPag.close();
                rstIsdCarPag=null;
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
     * Función que permite validar si el precio de venta está acorde al margen de utilidad según 
     * costos de importaciones(Nota de Pedido, Pedido Embarcado, Ingreso por Importación).
     * @return 
     */
    private boolean enviarCorEle_insNotPed(){
        boolean blnRes=false;
        String strSbjMsg=" Creación de Nota de Pedido " + txtNumPedOri.getText() + "";
        int intCodCfgCorEle=31;        
        try{
            String strMsg="";
            strMsg+="<html>";
            strMsg+="<body>";
            strMsg+="<h3>Creación de Nota de Pedido<hr></h3>";
            strMsg+="<table id='objTable' bgcolor='#F2E0F7' width='100%' border='1'>";
            strMsg+="<tr bgcolor='#F2E0F7'>";
            strMsg+="	<th colspan='7'> Estimado usuario, se ha creado la Nota de Pedido # " + txtNumPedOri.getText() + "</th>";
            strMsg+="</tr>";
            strMsg+="</table>";
            strMsg+="</body>";
            strMsg+="</html> ";

            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSbjMsg )){
                    mostrarMsgInf("<HTML>Estimado usuario, se ha creado la Nota de Pedido # <FONT COLOR=\"blue\">" + txtNumPedOri.getText() + "</FONT></HTML>");
                    blnRes=true;
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
     * 
     * @param conLoc
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @param txtNumDoc
     * @return 
     */

    private boolean insertaDatosGP(java.sql.Connection conLoc,Integer intCodEmp, Integer intCodLoc,  Integer intCodTipDoc, Integer intCodDoc, String txtNumDoc){
        boolean blnRes = false;
        java.sql.Connection conDyn;
        try{
            System.out.println("INICIA LO NUEVO XAN XAN XAN!!!");
            if(conLoc!=null){
                conDyn=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if(conDyn!=null){
                    conDyn.setAutoCommit(false);
                    System.out.println("CONEXION " + objParSis.getStringConexion_Dynamics2021());
                    if(InsertarOrdenCompra(conDyn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, txtNumDoc)){
                        if(iniciaProcesoOC(conDyn)){
                            conDyn.commit();
                            blnRes=true;
                        }
                        else{
                            conDyn.rollback();
                        }
                    }
                    else{
                        conDyn.rollback();
                        mostrarMsgInf("Codigos con error no se pudo agregar a GP " + strCodigosError);
                    }
                    conDyn.close();
                    conDyn=null;
                }
            }
            
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param conExt
     * @return 
     */
    private boolean iniciaProcesoOC(java.sql.Connection conExt){
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        String strNumeroOrdenCompraGP="";
        int intCodRes=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement(); 
                
                strSql="";
                strSql+="{call SP_INSERCCION_TABLA_ORDEN_COMPRA_CAB_DET_IMPORT(?, ?)}";
                System.out.println("iniciaProcesoOC 1:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("DOCUMENTO_ZAFIRO",  strClaveImportaciones);//Envio de parametro
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
                cal.close();
                cal=null;
                
                
                strSql="";
                strSql+=" SELECT DISTINCT cciOrdenCompra, cciDocumento ";
                strSql+=" FROM TB_ORDEN_COMPRA_IMPORT_CAB   ";
                strSql+=" WHERE  cciDocumento = "+objUti.codificar(strClaveImportaciones)+" ";
                System.out.println("iniciaProcesoOC:2 "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strNumeroOrdenCompraGP = rstLoc.getString("cciOrdenCompra");
                }
                else{
                    blnRes=false;
                }
                rstLoc.close();
                rstLoc=null;
                
                System.out.println("strClaveImportaciones " + strClaveImportaciones);
                System.out.println("strNumeroOrdenCompraGP " + strNumeroOrdenCompraGP);
               
                strSql="";
                strSql+="{call SP_CREACION_OC_IMPORT(?, ?, ?)}";
                System.out.println("iniciaProcesoOC 3:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("DOCUMENTO_ZAFIRO",  strClaveImportaciones);//Envio de parametro
                cal.setString("ORDEN_COMPRA",  strNumeroOrdenCompraGP);//Envio de parametro
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
                cal.close();
                cal=null;
                
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLFeatureNotSupportedException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private String strSql="", strCodigosError="";
    
    private boolean InsertarOrdenCompra(java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc, String strPedido){
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        String strSqlIns="";      
        int j=1;
        try{
            strSqlIns="";      
            strCodigosError="";
            if(cargarDatosParaGP()){
                if(conExt!=null){
                    stmLoc=conExt.createStatement();
                    strClaveImportaciones=CodEmp+"-"+CodLoc+"-"+CodTipDoc+"-"+CodDoc+"/"+strPedido;// 0-1-189-151515/P-185 \m/  JM 23/Ago/2022
                    if(cargarDatosItem2()){
                        for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                            if(cargarDatosItemNew(objParSis.getCodigoEmpresa(), Integer.valueOf(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM).toString()) )  ){
                                strSqlIns+=" INSERT INTO TB_ORDEN_COMPRA_ZAFIRO (cciDocumento, cciLinea, dfeDocumento, dfeDocumentoEntrega, cciCliente, cciProveedor, cciDireccion, cobDocumento, \n";
                                strSqlIns+="                                     cciBodega, cciArticulo, cnoArticulo, cciUMedida, nqnCantidad, nqnCostoUnitario, nqnTotal, nqnIVA, cciUsuario, \n";
                                strSqlIns+="                                     dfeCreacion, cciEstado, dfeEstimadaEmbarque, dfeRealEmbarque, dfeLlegada, dfePago)  \n";
                                strSqlIns+=" VALUES ("+objUti.codificar(strClaveImportaciones)+","+j+", CURRENT_TIMESTAMP, ";
                                strSqlIns+=" '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "', \n"; // dfeEstimadaEmbarque
                                strSqlIns+=" "+objUti.codificar(strCodigoCliente)+",";
                                strSqlIns+=objUti.codificar(strCodigoProveedor)+", 'PRINCIPAL',"+objUti.codificar(txaObs1.getText(),1)+",'IMPORT',"+objUti.codificar(strCodItmDyn)+",";
                                strSqlIns+=objUti.codificar(strItmDes)+","+objUti.codificar(strUniMed)+",";
                                strSqlIns+=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_CAN_PZA).toString()), objParSis.getDecimalesMostrar())+", ";
                                strSqlIns+=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COS_UNI).toString()), objParSis.getDecimalesMostrar())+", ";
                                strSqlIns+=objUti.redondearBigDecimal(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS)==null?"0":(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS).toString().equals("")?"0":objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_TOT_COS).toString()), objParSis.getDecimalesMostrar())+", ";
                                strSqlIns+="0.00,"+objUti.codificar(objParSis.getNombreUsuario())+", CURRENT_TIMESTAMP, 'NUEVO', ";
                                strSqlIns+=" '" + objUti.formatearFecha(dtpFecEmb.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "', \n"; // dfeEstimadaEmbarque
                                strSqlIns+=" '" + objUti.formatearFecha(dtpFecPue.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "', \n"; // dfeRealEmbarque
                                strSqlIns+=" '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "', \n"; // dfeLlegada
                                strSqlIns+=" '" + objUti.formatearFecha(dtpFecArr.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' \n"; // dfePago
                                strSqlIns+=" );  \n";
                                j++;
                            }
                            else{
                                blnRes=false;
                                System.out.println("problemas con el item: " + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM).toString()  );
                                strCodigosError+=" " + objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM).toString();
                            }
                        }
                        System.out.println("InsertarOrdenCompra \n"+strSqlIns);
                        stmLoc.executeUpdate(strSqlIns);
                    }
                    stmLoc.close();
                    stmLoc=null;
                }
                else{
                    blnRes=false;
                }
            }
            else{
                blnRes=false;
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
     
    
    
    private boolean cargarDatosItemNew(int CodEmp, int CodItm){
        boolean blnRes=true;
        try{
            for(int i=0;i<arlDatItm.size();i++){
                if(objUti.getIntValueAt(arlDatItm, i, INT_COD_ZAF_ITM) == CodItm ){
                    strCodItmDyn =  objUti.getStringValueAt(arlDatItm, i, INT_COD_DYN_ITM);
                    strItmDes    =  objUti.getStringValueAt(arlDatItm, i, INT_DES_ITM_GP);
                    strUniMed    =  objUti.getStringValueAt(arlDatItm, i, INT_UNI_MED_GP);
                    break;
                }
            }
            if(strCodItmDyn=="" || strItmDes=="" ||  strUniMed=="")
                blnRes=false;
            
            System.out.println("DEVUELVE " + CodItm + " - " + strCodItmDyn + " - " + strItmDes + " - " + strUniMed);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean cargarDatosItem(int CodEmp, int CodItm){
        boolean blnRes=true;
         java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_codAltDyn, a1.tx_nomItm  "
                        + "FROM tbm_inv as a1 "
                        + "where a1.co_emp="+CodEmp+" AND a1.co_itm="+CodItm;
                System.out.println("cargarDatosItem: \n"+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strCodItmDyn = rstLoc.getString("tx_codAltDyn");
                    if(obtenerDatosGP(strCodItmDyn)){
                        System.out.println("item "+strCodItmDyn+" ok ");
                    }
                    else{
                        blnRes=false;
                    }
                     
                }
                else{
                    blnRes=false;
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
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    ArrayList arlRegItm, arlDatItm;
    private static final int INT_COD_EMP=0;
    private static final int INT_COD_ZAF_ITM=1;
    private static final int INT_COD_DYN_ITM=2;    
    private static final int INT_DES_ITM_GP=3;     
    private static final int INT_UNI_MED_GP=4;
    
    private boolean cargarDatosItem2(){
        boolean blnRes=true;
         java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        String strCodItm="", strCodItmDynBus="";
        try{
            arlDatItm = new ArrayList();
            for(int i=0; i<objTblModImp32_02.getRowCountTrue(); i++){
                arlRegItm = new ArrayList();
                arlRegItm.add(INT_COD_EMP,objParSis.getCodigoEmpresa());
                arlRegItm.add(INT_COD_ZAF_ITM,Integer.valueOf(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM).toString())  );
                strCodItm += "" + Integer.valueOf(objTblModImp32_02.getValueAt(i, objImp32_02.INT_TBL_DAT_COD_ITM).toString()) ;
                if(i+1<objTblModImp32_02.getRowCountTrue()) {
                    strCodItm+=",";
                }
                arlRegItm.add(INT_COD_DYN_ITM,"");
                arlRegItm.add(INT_DES_ITM_GP,"");
                arlRegItm.add(INT_UNI_MED_GP,"");
                arlDatItm.add(arlRegItm);
            }
            
            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_codAltDyn, a1.tx_nomItm  "
                        + "FROM tbm_inv as a1 "
                        + "where a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_itm IN ("+strCodItm+") ";
                System.out.println("cargarDatosItem: \n"+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    for(int i=0;i<arlDatItm.size();i++){
                        if(objUti.getIntValueAt(arlDatItm, i, INT_COD_ZAF_ITM) == rstLoc.getInt("co_itm") ){
                            objUti.setStringValueAt(arlDatItm, i, INT_COD_DYN_ITM, rstLoc.getString("tx_codAltDyn"));
                            break;
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
            
            for(int i=0;i<arlDatItm.size();i++){
                strCodItmDynBus+="'"+objUti.getStringValueAt(arlDatItm, i, INT_COD_DYN_ITM)+"'";
                if(i+1<arlDatItm.size()) {
                    strCodItmDynBus+=",";
                }
            }
            
            
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();

                strSql="";
                strSql+=" SELECT TRIM(ITEMNMBR) as ITEMNMBR, TRIM(ITEMDESC) as ITEMDESC, TRIM(ITMSHNAM) as ITMSHNAM, ITEMTYPE, "
                        + " CURRCOST, TRIM(ITMTSHID) as ITMTSHID, TRIM(UOMSCHDL) as UOMSCHDL, TRIM(PRCLEVEL) as PRCLEVEL "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 "
                        + " WHERE ITEMNMBR IN ( "+strCodItmDynBus+" )" ;
                System.out.println("obtenerDatosGP: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){ 
                    for(int i=0;i<arlDatItm.size();i++){
                        if(objUti.getStringValueAt(arlDatItm, i, INT_COD_DYN_ITM).equals(rstLoc.getString("ITEMNMBR")) ){
                            objUti.setStringValueAt(arlDatItm, i, INT_DES_ITM_GP, rstLoc.getString("ITEMDESC"));
                            objUti.setStringValueAt(arlDatItm, i, INT_UNI_MED_GP, rstLoc.getString("UOMSCHDL"));
                            break;
                        }
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
            else{
                blnRes=false;
            }
                
                
            
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    private boolean obtenerDatosGP(String CodAltDyn){
        boolean blnRes=true;
         java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT TRIM(ITEMNMBR) as ITEMNMBR, TRIM(ITEMDESC) as ITEMDESC, TRIM(ITMSHNAM) as ITMSHNAM, ITEMTYPE, "
                        + " CURRCOST, TRIM(ITMTSHID) as ITMTSHID, TRIM(UOMSCHDL) as UOMSCHDL, TRIM(PRCLEVEL) as PRCLEVEL "
                        + " FROM "+objParSis.getNombreBaseDatosGP()+".dbo.IV00101 "
                        + " WHERE ITEMNMBR = "+objUti.codificar(CodAltDyn)+" " ;
                System.out.println("obtenerDatosGP: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strItmDes = rstLoc.getString("ITEMDESC");
                    strUniMed = rstLoc.getString("UOMSCHDL");
                }
                else{
                    blnRes=false;
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
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private double dblCosUni=0.00;
    private String strCodigoCliente, strCodigoProveedor, strDireccionEntrega, strCodItmDyn, strItmDes, strUniMed, strClaveImportaciones = "";
    
    private boolean cargarDatosParaGP(){
        boolean blnRes=true;
         java.sql.Connection conLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Statement stmLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT tx_ruc, tx_dir FROM tbm_emp where co_emp="+txtCodImp.getText()+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strCodigoCliente = rstLoc.getString("tx_ruc");
                    strDireccionEntrega = rstLoc.getString("tx_dir");
                }
                rstLoc.close();
                rstLoc=null;
                
                strSql="";
                strSql+=" SELECT tx_codGP, tx_nom FROM tbm_expImp where co_exp="+txtCodExp.getText()+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strCodigoProveedor = rstLoc.getString("tx_codGP");  
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
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }   
    
    
    
    
    /**
     * 
     * @param conLoc
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodTipDoc
     * @param intCodDoc
     * @param txtNumDoc
     * @return 
     */

    private boolean actualizarDatosGP(java.sql.Connection conLoc,Integer intCodEmp, Integer intCodLoc,  Integer intCodTipDoc, Integer intCodDoc, String txtNumDoc){
        boolean blnRes = false;
        java.sql.Connection conDyn;
        try{
            System.out.println("INICIA UPDATE XAN XAN XAN!!!");
            if(conLoc!=null){
                conDyn=DriverManager.getConnection(objParSis.getStringConexion_Dynamics2021(), objParSis.getUsuarioBaseDatos_Dynamics2021(), objParSis.getClaveBaseDatos_Dynamics2021());
                if(conDyn!=null){
                    conDyn.setAutoCommit(false);
                    System.out.println("CONEXION " + objParSis.getStringConexion_Dynamics2021());
                    if(anularOrdenComprAnterior(conDyn,txtNumDoc)){
                        if(InsertarOrdenCompra(conDyn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, txtNumDoc)){
                            if(iniciaProcesoOC(conDyn)){
                                conDyn.commit();
                                blnRes=true;
                            }
                            else{
                                conDyn.rollback();
                            }
                        }
                        else{
                            conDyn.rollback();
                            mostrarMsgInf("Codigos con error no se pudo agregar a GP " + strCodigosError);
                        }
                    }
                    else{conDyn.rollback();}
                    
                    conDyn.close();
                    conDyn=null;
                }
            }
            
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    String strClaveImportacionesAnular="", strOrdenCompraAnular="";
    
    
    private boolean anularOrdenComprAnterior(java.sql.Connection conExt, String txtNumDoc){
        boolean blnRes = true;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.CallableStatement cal;
        int intCodRes=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strClaveImportacionesAnular=objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_EMP)+
                        "-"+objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_LOC)+
                        "-"+objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_TIP_DOC)+
                        "-"+objUti.getStringValueAt(arlDatConNotPed, intIndiceNotPed, INT_ARL_CON_COD_DOC)+
                        "/"+txtNumDoc;// 0-1-189-151515/P-185 \m/   // Documento para anular xD
                
                strSql="";
                strSql+=" SELECT a1.cciDocumento, cciEstado, a2.cciOrdenCompra  ";
                strSql+=" FROM TB_ORDEN_COMPRA_ZAFIRO as a1  \n";
                strSql+=" INNER JOIN TB_ORDEN_COMPRA_IMPORT_CAB as a2 ON (a1.cciDocumento=a2.cciDocumento) \n";
                strSql+=" WHERE a2.cciDocumento = "+objUti.codificar(strClaveImportacionesAnular)+" ";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    strOrdenCompraAnular = rstLoc.getString("cciOrdenCompra");
                }
                rstLoc.close();
                rstLoc=null;
                System.out.println("ANULAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ");
                System.out.println("strClaveImportaciones " + strClaveImportacionesAnular);
                System.out.println("strNumeroOrdenCompraGP " + strOrdenCompraAnular);
                strSql="";
                strSql+="{call SP_ANULACION_OC_IMPORT_2(?, ?, ?)}";
                System.out.println("iniciaAnulacionProcesoOC:" + strSql);
                cal = conExt.prepareCall(strSql);
                cal.setString("DOCUMENTO_ZAFIRO",  strClaveImportacionesAnular);//Envio de parametro
                cal.setString("ORDEN_COMPRA",  strOrdenCompraAnular);//Envio de parametro
                cal.registerOutParameter("intRes", java.sql.Types.INTEGER);
                cal.execute();
                intCodRes = cal.getInt("intRes");
                if(intCodRes==1){
                    blnRes=false;
                    System.out.println("falla");
                }
                cal.close();
                cal=null;
                 
                stmLoc.close();
                stmLoc=null;
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
    
    
     
    
}