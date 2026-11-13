/*
 * ZafCom68.java
 *
 * Created on Aug 24, 2009, 11:34:14 AM
 */
package Compras.ZafCom68;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafPerUsr.ZafPerUsr;
import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenConItm01;
//import com.lowagie.text.Cell;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

//import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 *
 * @author ilino
 */
public class ZafCom68 extends javax.swing.JInternalFrame{
    //Constantes
    //JTable TblHis: Históricos
    private final int INT_TBL_DAT_HIS_LIN=0;
    private final int INT_TBL_DAT_HIS_CHK=1;
    private final int INT_TBL_DAT_HIS_ANI=2;
    private final int INT_TBL_DAT_HIS_NUM_MES=3;
    private final int INT_TBL_DAT_HIS_MES=4;

    //JTable TblFix: Tabla Fija
    private final int INT_TBL_DAT_FIX_LIN=0;
    private final int INT_TBL_DAT_FIX_CHK_EST_MOV_ITM=1;//ITEMS QUE NO SE PUEDEN QUEDAR SIN STOCK
    private final int INT_TBL_DAT_FIX_COD_ITM=2;
    private final int INT_TBL_DAT_FIX_COD_ALT_ITM=3;
    private final int INT_TBL_DAT_FIX_COD_ALT_ITM2=4;
    private final int INT_TBL_DAT_FIX_NOM_ITM=5;
    private final int INT_TBL_DAT_FIX_UNI_MED=6;
    private final int INT_TBL_DAT_FIX_PES_KG=7;
    private final int INT_TBL_DAT_FIX_COS_UNI_GRP=8;
    private final int INT_TBL_DAT_FIX_EST_ELI_ITM=9;
    
    //JTable TblDat: Tabla Dinámica
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_CHK_EST_MOV_ITM=1;
    private final int INT_TBL_DAT_COD_ITM=2;
    private final int INT_TBL_DAT_COD_ALT_ITM=3;
    private final int INT_TBL_DAT_COD_ALT_ITM2=4;
    private final int INT_TBL_DAT_NOM_ITM=5;
    private final int INT_TBL_DAT_UNI_MED=6;
    private final int INT_TBL_DAT_PES_KG=7;
    private final int INT_TBL_DAT_PRE_VTA=8;    
    
    //Constantes
    private final int INT_NUM_COL_HIS_VTA_FEC_TIT=4; //Número de columnas dentro de cada selección del Histórico de Ventas
    private final int INT_NUM_COL_HIS_VTA_STK=0;     //Stock (nueva)
    private final int INT_NUM_COL_HIS_VTA_UNI=1;     //Unidades
    private final int INT_NUM_COL_HIS_VTA_VEC=2;     //Veces
    private final int INT_NUM_COL_HIS_VTA_MES=3;     //Meses
    
    //Reposicion segun el dato ingresado por usuario
    private final int INT_NUM_COL_REP=3;
    private final int INT_NUM_COL_REP_DIS=0;
    private final int INT_NUM_COL_REP_VTA=1;
    private final int INT_NUM_COL_REP_LLE=2;
    
    //Proyeción segun el dato ingresado por usuario
    private final int INT_NUM_COL_PRY=2;
    private final int INT_NUM_COL_PRY_DIS=0;
    private final int INT_NUM_COL_PRY_MES=1;    
    
    //Constantes: ArrayList
    private ArrayList arlRegAniAdd, arlDatAniAdd, arlRegAniAdd02, arlDatAniAdd02, arlRegAniAdd03, arlDatAniAdd03, arlRegAniAdd04, arlDatAniAdd04;
    private final int INT_ARL_ANI=0;
    private final int INT_ARL_NUM_MES=1;
    private final int INT_ARL_MES=2;
    private final int INT_ARL_COL=3;
    
    //ArrayList: Contiene la informacion de los nombres de los pedidos embarcados
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    private final int INT_ARL_PED_EMB_COD_EMP=0;
    private final int INT_ARL_PED_EMB_COD_LOC=1;
    private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_PED_EMB_COD_DOC=3;
    private final int INT_ARL_PED_EMB_NUM_PED=4;
    private final int INT_ARL_PED_EMB_NUM_COL=5;
    private final int INT_ARL_PED_EMB_EST_COL=6;
    private final int INT_ARL_PED_EMB_FEC_EST_PED_EMB=7;

    //ArrayList: Contiene la informacion de los valores ingresados para cada item en los pedidos embarcados
    private ArrayList arlRegDetPedEmb, arlDatDetPedEmb;
    private final int INT_ARL_DET_PED_EMB_COD_EMP=0;
    private final int INT_ARL_DET_PED_EMB_COD_LOC=1;
    private final int INT_ARL_DET_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_DET_PED_EMB_COD_DOC=3;
    private final int INT_ARL_DET_PED_EMB_COD_ITM=4;
    private final int INT_ARL_DET_PED_EMB_CAN_PED_EMB=5;
    private final int INT_ARL_DET_PED_EMB_COD_ALT_ITM=6;
    private final int INT_ARL_DET_PED_EMB_EST=7;
    private final int INT_ARL_DET_PED_EMB_NUM_COL=8;    
    
    private ArrayList arlRegNumMesRep;
    private ArrayList arlDatNumMesRep;
    private final int INT_ARL_NUM_MES_REP_NUM_ANI=0;//Número de anio
    private final int INT_ARL_NUM_MES_REP_NUM_MES=1;//Número de mes
    private final int INT_ARL_NUM_MES_REP_NOM_MES=2;//Nombre de mes
    private final int INT_ARL_NUM_MES_REP_FAC_REP=3;//Factor de reposición
    private final int INT_ARL_NUM_MES_REP_EST_PRO=4;//Estado de procesado
    private final int INT_ARL_NUM_MES_REP_NUM_COL=5;//Nùmero de columna
    
    private ArrayList arlRegNumMesPry;
    private ArrayList arlDatNumMesPry;
    private final int INT_ARL_NUM_MES_PRY_NUM_ANI=0;//Número de mes
    private final int INT_ARL_NUM_MES_PRY_NUM_MES=1;//Número de mes
    private final int INT_ARL_NUM_MES_PRY_NOM_MES=2;//Nombre de mes
    private final int INT_ARL_NUM_MES_PRY_EST_PRO=3;//Estado de procesado
    private final int INT_ARL_NUM_MES_PRY_NUM_COL=4;//Número de columna asociada
    
    private ArrayList arlRegMesRep, arlDatMesRep;
    private int INT_ARL_MES_REP_COD_ANI=0;
    private int INT_ARL_MES_REP_COD_MES=1;
    private int INT_ARL_MES_REP_COD_ITM=2;
    private int INT_ARL_MES_REP_COD_ALT_ITM=3;
    private int INT_ARL_MES_REP_CAN=4;
    private int INT_ARL_MES_REP_EST_PRO=5;
    private int INT_ARL_MES_REP_COL=6;    
    
    //ArrayList: Filas Eliminadas
    private ArrayList arlRegFilEli, arlDatFilEli;
    final int INT_ARL_FIL_ELI_COD_EMP=0;
    final int INT_ARL_FIL_ELI_COD_ITM=1;
    final int INT_ARL_FIL_ELI_EST_ITM=2;
    
    //ArrayList: Exportar Excel
    private ArrayList arlRegExpExc, arlDatExpExc;
    private final int INT_ARL_EXP_EXC_COD_ALT_ITM=0;
    private final int INT_ARL_EXP_EXC_COD_ALT_ITM2=1;
    private final int INT_ARL_EXP_EXC_NOM_ITM=2;
    private final int INT_ARL_EXP_EXC_PES_UNI=3;
    private final int INT_ARL_EXP_EXC_CAN_REP_LSC=4;
    private final int INT_ARL_EXP_EXC_PES_TOT_ITM=5;
      
    //contiene la informacion de los pedidos previos -CABECERA
    private ArrayList arlRegPedPre, arlDatPedPre;
    private final int INT_ARL_CAB_PED_PRE_COD_EMP=0;
    private final int INT_ARL_CAB_PED_PRE_COD_LOC=1;
    private final int INT_ARL_CAB_PED_PRE_COD_TIP_DOC=2;
    private final int INT_ARL_CAB_PED_PRE_COD_DOC=3;
    private final int INT_ARL_CAB_PED_PRE_FEC_DOC=4;
    private final int INT_ARL_CAB_PED_PRE_NUM_DOC=5;
    private final int INT_ARL_CAB_PED_PRE_NUM_PED_PRE=6;
    private final int INT_ARL_CAB_PED_PRE_NUM_COL=7;
    private final int INT_ARL_CAB_PED_PRE_COL_MOD=8;//si algun dato de esa columna ha sido modificado
    
    //contiene la informacion de los pedidos previos -DETALLE(items con cantidades)
    private ArrayList arlRegDetPedPre, arlDatDetPedPre;
    private final int INT_ARL_DET_PED_PRE_COD_EMP=0;
    private final int INT_ARL_DET_PED_PRE_COD_LOC=1;
    private final int INT_ARL_DET_PED_PRE_COD_TIP_DOC=2;
    private final int INT_ARL_DET_PED_PRE_COD_DOC=3;
    private final int INT_ARL_DET_PED_PRE_COD_ITM=4;
    private final int INT_ARL_DET_PED_PRE_CAN_PED_PRE=5;
    private final int INT_ARL_DET_PED_PRE_COD_ALT_ITM=6;
    private final int INT_ARL_DET_PED_PRE_EST=7;
    private final int INT_ARL_DET_PED_PRE_NUM_COL=8;
    private final int INT_ARL_DET_PED_PRE_EST_ANU=9;//contendra el estado si se debe Anular la Nota de Pedido Previa.    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafImp objZafImp;
    private ZafUtil objUti;
    private ZafTblMod objTblModHis, objTblModHis02, objTblModHis03, objTblModHis04;
    private ZafTblMod objTblMod, objTblModFix;
    private ZafTblMod objTblModTot, objTblModTotFix;
    private ZafTblFilCab objTblFilCabHis, objTblFilCabHis2, objTblFilCabHis3, objTblFilCabHis4;
    private ZafTblFilCab objTblFilCab, objTblFilCabFix;
    private ZafTblFilCab objTblFilCabTot, objTblFilCabTotFix;
    private ZafTblPopMnu objTblPopMnuHis, objTblPopMnuHis02, objTblPopMnuHis03, objTblPopMnuHis04;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;    
    private ZafTblCelRenChk objTblCelRenChkHis, objTblCelRenChkHis02, objTblCelRenChkHis03, objTblCelRenChkHis04;
    private ZafTblCelEdiChk objTblCelEdiChkHis, objTblCelEdiChkHis02, objTblCelEdiChkHis03, objTblCelEdiChkHis04;
    private ZafTblCelRenLbl objTblCelRenLblTot, objTblCelRenLblTotFix;
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtProAnu, objTblCelEdiTxtPed, objTblCelEdiTxtImp1, objTblCelEdiTxtImp2, objTblCelEdiTxtPesItmFix, objTblCelEdiTxtPedPre;
//------------------------------------------------------------------------------
    private ZafTblCelEdiTxt objTblCelEdiTxtValManCalRep;
//------------------------------------------------------------------------------
    //para poder dar formato y color al mismo tiempo se crearon varias variables renderizadoras, antes se la trabajaba solo con una variables
    private ZafTblCelRenLbl objTblCelRenLblGrl, objTblCelRenLblAni, objTblCelRenLblAniMes, objTblCelRenLblAni2, objTblCelRenLblAniMes2, objTblCelRenLblAni3, objTblCelRenLblAniMes3, objTblCelRenLblAni4, objTblCelRenLblAniMes4;
    private ZafTblCelRenLbl objTblCelRenLblSumHisVta, objTblCelRenLblProAnu, objTblCelRenLblStk;
//------------------------------------------------------------------------------
    private ZafTblCelRenLbl objTblCelRenLblValManCalRep;
//------------------------------------------------------------------------------
    private ZafTblCelRenLbl objTblCelRenLblPedEmb, objTblCelRenLblRep, objTblCelRenLblImpPed1, objTblCelRenLblImpPed2, objTblCelRenLblItmEli, objTblCelRenLblItmEliChk;
    private ZafTblCelRenLbl objTblCelRenLblPedPre, objTblCelRenLblPedPreTot;
    private ZafTblCelRenLbl objTblCelRenLblColFix, objTblCelRenLblFixStr;
    private ZafTblCelRenLbl objTblCelRenLblDis, objTblCelRenLblVtaPro;
    private ZafTblCelRenLbl objTblCelRenLblNumMesRep, objTblCelRenLblNumMesPry;
    private ZafTblCelRenChk objTblCelRenChkEstEliItm, objTblCelRenChkEstItmStkRep, objTblCelRenChkFixEstItmStkRep;
    private ZafTblCelEdiChk objTblCelEdiChkEstEliItm, objTblCelEdiChkEstItmStkRep, objTblCelEdiChkFixEstItmStkRep;
    private ZafMouMotAda objMouMotAda;
    private ZafMouMotAdaFix objMouMotAdaFix;    
    private ZafTblBus objTblBus, objTblBusFix; 
    private ZafPerUsr objPerUsr;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoItm;   
    private ZafThreadGUI objThrGUI;
    private ZafSegMovInv objSegMovInv;
    private java.util.Date datFecAux;
    private ZafHisTblBasDat objHisTblBasDat;                    //Histórico    
    
    private Vector vecCabHis, vecCabHis02, vecCabHis03, vecCabHis04;
    private Vector vecRegHis, vecRegHis02, vecRegHis03, vecRegHis04;
    private Vector vecDatHis, vecDatHis02, vecDatHis03, vecDatHis04;
    private Vector vecAux, vecAux02, vecAux03, vecAux04;
    private Vector vecCab, vecReg, vecDat;
    private Vector vecCabFix, vecRegFix, vecDatFix;
    
    private boolean blnHayCam;
    private boolean blnCon;
    private boolean BLN_EXI_NPP;
    private final int INT_EST_COD_TIP_DOC_PED_PRE=207;
    private JScrollBar barDat1, barFix1, barDatVer1, barDatTot1, barDat2, barFix2, barFixVer2, barDatTotFix2, barDat3, barFix3, barFixVer3, barDatTotFix3, barDatTot3;
    
    private int intFilColSel;  
    private int intNumColEst;//numero de columnas fijas
    private int intNumColAddFecCor, intNumColIniFecCor, intNumColFinFecCor;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor2, intNumColIniFecCor2, intNumColFinFecCor2;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor3, intNumColIniFecCor3, intNumColFinFecCor3;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddFecCor4, intNumColIniFecCor4, intNumColFinFecCor4;//numero de columnas que se adicionan si se selecciona historico
    private int intNumColAddTot, intNumColIniTot, intNumColFinTot;//numero de columnas que se adicionan si hay totales(depende de las columnas de historico)
    private int intNumColAddTot2, intNumColIniTot2, intNumColFinTot2, intNumColAddTot3, intNumColIniTot3, intNumColFinTot3, intNumColAddTot4, intNumColIniTot4, intNumColFinTot4;//numero de columnas que se adicionan si hay totales(depende de las columnas de historico)
    private int intNumColAddProAnu, intNumColIniProAnu, intNumColFinProAnu;//numero de columnas que se adicionan para promedio anual(calculado, manual)
//------------------------------------------------------------------------------
    private int intNumColAddValManCalRep, intNumColIniValManCalRep, intNumColFinValManCalRep;//numero de columnas que se adicionan para "ValoresManualesCalculoReposicion".
//------------------------------------------------------------------------------
    private int intNumColAddStk, intNumColIniStk, intNumColFinStk;//numero de columnas que se adicionan para Stock actual y de importaciones
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb;//numero de columnas que se adicionan pedidos de importaciones
    private int intNumColAddNumMesRep, intNumColIniNumMesRep, intNumColFinNumMesRep;//Reposición
    private int intNumColAddNumMesPry, intNumColIniNumMesPry, intNumColFinNumMesPry;//Proyección    
    private int intNumColAddPedPre, intNumColIniPedPre, intNumColFinPedPre;//numero de columnas que se adicionan para datos de notas de pedido previas
    private int intNumColAddDis, intNumColIniDis, intNumColFinDis;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddVtaPro, intNumColIniVtaPro, intNumColFinVtaPro;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddRep, intNumColIniRep, intNumColFinRep;//numero de columnas que se adicionan para datos de reposicion
    private int intNumColAddImp, intNumColIniImp_Ped1, intNumColFinImp_Ped1;//numero de columnas que se adicionan para datos que se van a importar por pedido 1 (comprende Pedido1 y Peso)
    private int intNumColIniImp_Ped2, intNumColFinImp_Ped2;//numero de columnas que se adicionan para datos que se van a importar por pedido 2 (comprende Pedido2 y Peso)
    private int intNumColAddEstItmEli, intNumColIniEstItmEli, intNumColFinEstItmEli;//estado del item a mostrarse
    private int intNumColAddChkItmEli, intNumColIniChkItmEli, intNumColFinChkItmEli;//checkbox que permite cambiar el estado del item a mostrarse
    private int intCodDocPedPre_canRepLSC, intNumDocPedPre_canRepLSC;
    private int intCodDocPedPre_seg;
    private int intCodEmpExiNomPedPre,intCodLocExiNomPedPre, intCodTipDocExiNomPedPre, intCodDocExiNomPedPre;
    private int intFilSelCop[];
    
    private Object objCodSegNotPedPre;   // Objeto de Codigo de Seguimiento.
    
    private String strSQL, strAux;
    private String strNomPedPreIngLSC; 
    private String strCodAlt, strNomItm;   
    
    private String strBodEmpGrp=" 1,2,3,5,11,15,28,31,32,36,38 ";  //Otras Bodegas de Empresas del Grupo LSC, excepto Importaciones y Compras Locales
    private String strCodBodImp=" 6,4 ";                     //Bodega Importaciones y Compras Locales.
    
    private String strVer=" v0.74";
    private ZafVenConItm01 objVenConItm01;
//------------------------------------------------------------------------------
    private boolean blnCalTotAftEdi=false; //Calcular Total "AfterEdit". Se utiliza para que el calculo se ejecute 1 sóla vez. 
//------------------------------------------------------------------------------
    
    /* JM: Agrupacion con columnas de datos adicionales del item  11/Jul/2023 */
    private int INT_DAT_ADI_ITM_STK_BAS=0;
    private int INT_DAT_ADI_ITM_TRA=1;
    private int INT_DAT_ADI_ITM_FRE=2;
    private int INT_DAT_ADI_ITM_EXC=3;
    private int INT_DAT_ADI_ITM_PUN_PAR_MAX=4;
    private int INT_DAT_ADI_ITM_PUN_PAR_MIN=5;
    private int INT_DAT_ADI_ITM_MOQ=6;


    
    /** Crea una nueva instancia. */
    public ZafCom68(ZafParSis obj)
    { 
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if ( (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) )
            { 
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través del GRUPO.</HTML>");
                dispose();
            }
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }    
    
    /** Configurar el formulario. */
    private boolean configurarFrm()  
    {
        boolean blnRes = true;
        try
        {
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItm01(objParSis, "a1.", optFil);
            panFilItm.add(objVenConItm01);
            objVenConItm01.setBounds(24, 44, 656, 20);
            //Título de la ventana
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            //Inicializar objetos.
            objUti = new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPerUsr=new ZafPerUsr(objParSis);
            
            vecDatHis=new Vector();
            vecDatHis02=new Vector();
            vecDatHis03=new Vector();
            vecDatHis04=new Vector();
            arlDatAniAdd=new ArrayList();
            arlDatAniAdd02=new ArrayList();
            arlDatAniAdd03=new ArrayList();
            arlDatAniAdd04=new ArrayList();
            arlDatPedEmb=new ArrayList();
            arlDatDetPedEmb=new ArrayList();
            arlDatFilEli=new ArrayList();
            arlDatExpExc=new ArrayList();
            arlDatPedPre=new ArrayList();    //contendra las notas de pedido previas, solo datos generales, o sea los de cabecera
            arlDatDetPedPre=new ArrayList(); //contendra los datos de las notas de pedido previas, datos del item
            objSegMovInv=new ZafSegMovInv(objParSis, this);
            objHisTblBasDat=new ZafHisTblBasDat();
            arlDatNumMesRep=new ArrayList();
            arlDatNumMesPry=new ArrayList();
            arlDatMesRep=new ArrayList();
            BLN_EXI_NPP=false;

            jspNumMesRep.setModel(new SpinnerNumberModel(1, 1, 100, 0.1));
            jspNumMesIniRep.setModel(new SpinnerNumberModel(1, 1, 100, 0.1));
            
            //Configurar los JTables.
            configurarTblDatHis(); 
            configurarTblDatHis02();
            configurarTblDatHis03();
            configurarTblDatHis04();
            configurarTblDatFix();
            configurarTblDatFixTot();
            
            objTblModTotFix.insertRows(2,0);
            objTblModTotFix.setValueAt("Totales - peso: ", 0, INT_TBL_DAT_FIX_NOM_ITM);
            objTblModTotFix.setValueAt("Totales - precio venta neta: ", 1, INT_TBL_DAT_FIX_NOM_ITM);
            configurarTblDat();
            configurarTblDatTot();
            //Configurar las ZafVenCon.
            configurarVenConItm();
            chkItmNotEli.setVisible(true);
            chkItmNotEli.setEnabled(true);

            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_ING_IMP)
            {
                if(!objPerUsr.isOpcionEnabled(2734)){ //Consultar
                    butCon.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(2735)){ //Guardar
                    butGua.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(2736)){ //Cerrar
                    butCer.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(3838)){ //Eliminar nota de pedido previa
                    butEliPedPrv.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(3493)){ //Exportar
                    butExpExc.setVisible(false);
                }        
            }
            else
            {  //Compras Locales
                if(!objPerUsr.isOpcionEnabled(4321)){ //Consultar
                    butCon.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(4322)){ //Guardar
                    butGua.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(4326)){ //Cerrar
                    butCer.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(4323)){ //Eliminar nota de pedido previa
                    butEliPedPrv.setVisible(false);
                }
                if(!objPerUsr.isOpcionEnabled(4324)){ //Exportar
                    butExpExc.setVisible(false);
                }                 
            }

            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);
            butRecNumMesChg.setVisible(false);
            
            chkItmNotEli.setSelected(true);
            chkHisVta1.setSelected(true);
            chkHisVtaStk.setSelected(true);
            chkHisVtaUni.setSelected(true);
            
            jspNumMesIniRep.setEnabled(false);
            lblNumMesPry.setVisible(false);
            jspNumMesIniRep.setVisible(false);
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
    private boolean configurarTblDatHis() 
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatHis = new Vector();    //Almacena los datos
            vecCabHis = new Vector(5);   //Almacena las cabeceras
            vecCabHis.clear();
            vecCabHis.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis = new ZafTblMod();
            objTblModHis.setHeader(vecCabHis);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis.setModel(objTblModHis);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis.setRowSelectionAllowed(true);
            tblHis.getTableHeader().setReorderingAllowed(false);
            tblHis.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuHis=new ZafTblPopMnu(tblHis);
            objTblPopMnuHis.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnuHis.setDesmarcarCasillasVerificacionEnabled(true);
            
            objTblPopMnuHis.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilSel[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilSel=tblHis.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    for (int i=0; i<intFilSel.length; i++){
                        if(objTblPopMnuHis.isClickMarcarCasillasVerificacion()){
                            objTblModHis.setChecked(true, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                        else if(objTblPopMnuHis.isClickDesmarcarCasillasVerificacion()){
                            objTblModHis.setChecked(false, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                    }
                }
            });

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis = new ZafTblFilCab(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis);

            vecAux=new Vector();
            objTblModHis.setModoOperacion(objTblModHis.INT_TBL_EDI);
            vecAux.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis);
            objTblCelRenChkHis=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis=new ZafTblCelEdiChk(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis); 

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDat" para el histórico 2
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis02() 
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatHis02 = new Vector();    //Almacena los datos
            vecCabHis02 = new Vector(5);  //Almacena las cabeceras
            vecCabHis02.clear();
            vecCabHis02.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis02.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis02.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis02.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis02.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis02 = new ZafTblMod();
            objTblModHis02.setHeader(vecCabHis02);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis02.setModel(objTblModHis02);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis02.setRowSelectionAllowed(true);
            tblHis02.getTableHeader().setReorderingAllowed(false);
            tblHis02.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuHis02=new ZafTblPopMnu(tblHis02);
            objTblPopMnuHis02.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnuHis02.setDesmarcarCasillasVerificacionEnabled(true);
            
            objTblPopMnuHis02.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilSel[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilSel=tblHis02.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    for (int i=0; i<intFilSel.length; i++){
                        if(objTblPopMnuHis02.isClickMarcarCasillasVerificacion()){
                            objTblModHis02.setChecked(true, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                        else if(objTblPopMnuHis02.isClickDesmarcarCasillasVerificacion()){
                            objTblModHis02.setChecked(false, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis02.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis02.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis2 = new ZafTblFilCab(tblHis02);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis2);

            vecAux02=new Vector();
            objTblModHis02.setModoOperacion(objTblModHis02.INT_TBL_EDI);
            vecAux02.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis02.setColumnasEditables(vecAux02);
            vecAux02=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis02=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis02);
            objTblCelRenChkHis02=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis02=new ZafTblCelEdiChk(tblHis02);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis02);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis02.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis02);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e)
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
    private boolean configurarTblDatHis03()
    {
        boolean blnRes = true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatHis03 = new Vector();    //Almacena los datos
            vecCabHis03 = new Vector(5);  //Almacena las cabeceras
            vecCabHis03.clear();
            vecCabHis03.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis03.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis03.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis03.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis03.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis03 = new ZafTblMod();
            objTblModHis03.setHeader(vecCabHis03);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis03.setModel(objTblModHis03);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis03.setRowSelectionAllowed(true);
            tblHis03.getTableHeader().setReorderingAllowed(false);
            tblHis03.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuHis03=new ZafTblPopMnu(tblHis03);
            objTblPopMnuHis03.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnuHis03.setDesmarcarCasillasVerificacionEnabled(true);
            
            objTblPopMnuHis03.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilSel[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilSel=tblHis03.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    for (int i=0; i<intFilSel.length; i++){
                        if(objTblPopMnuHis03.isClickMarcarCasillasVerificacion()){
                            objTblModHis03.setChecked(true, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                        else if(objTblPopMnuHis03.isClickDesmarcarCasillasVerificacion()){
                            objTblModHis03.setChecked(false, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                    }
                }
            });

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis03.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis03.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis3 = new ZafTblFilCab(tblHis03);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis3);

            vecAux03=new Vector();
            objTblModHis03.setModoOperacion(objTblModHis03.INT_TBL_EDI);
            vecAux03.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis03.setColumnasEditables(vecAux03);
            vecAux03=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis03=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis03);
            objTblCelRenChkHis03=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis03=new ZafTblCelEdiChk(tblHis03);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis03);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis03.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis03);

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
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis04() 
    {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis04 = new Vector();    //Almacena los datos
            vecCabHis04 = new Vector(5);  //Almacena las cabeceras
            vecCabHis04.clear();
            vecCabHis04.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis04.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis04.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis04.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis04.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis04 = new ZafTblMod();
            objTblModHis04.setHeader(vecCabHis04);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis04.setModel(objTblModHis04);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis04.setRowSelectionAllowed(true);
            tblHis04.getTableHeader().setReorderingAllowed(false);
            tblHis04.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuHis04=new ZafTblPopMnu(tblHis04);
            objTblPopMnuHis04.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnuHis04.setDesmarcarCasillasVerificacionEnabled(true);
            
            objTblPopMnuHis04.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                 int intFilSel[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilSel=tblHis04.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    for (int i=0; i<intFilSel.length; i++){
                        if(objTblPopMnuHis04.isClickMarcarCasillasVerificacion()){
                            objTblModHis04.setChecked(true, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                        else if(objTblPopMnuHis04.isClickDesmarcarCasillasVerificacion()){
                            objTblModHis04.setChecked(false, intFilSel[i], INT_TBL_DAT_HIS_CHK);
                        }
                    }
                }
            });

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis04.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis04.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(true);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(true);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis4 = new ZafTblFilCab(tblHis04);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis4);

            vecAux04=new Vector();
            objTblModHis04.setModoOperacion(objTblModHis04.INT_TBL_EDI);
            vecAux04.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis04.setColumnasEditables(vecAux04);
            vecAux04=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis04=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis04);
            objTblCelRenChkHis04=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis04=new ZafTblCelEdiChk(tblHis04);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis04);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis04.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis04);

            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
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
            vecCab = new Vector(9);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK_EST_MOV_ITM, "Est.Mov.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM2, "Cod.Alt2.");            
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            vecCab.add(INT_TBL_DAT_PES_KG, "Peso(kg)");
            vecCab.add(INT_TBL_DAT_PRE_VTA, "Pre.Vta.Net.");
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intArlColSel=-1;                 
                int intCodEmpDatPedPre, intCodLocDatPedPre, intCodTipDocDatPedPre, intCodDocDatPedPre;
                int intCodEmpDatDetPedPre, intCodLocDatDetPedPre, intCodTipDocDatDetPedPre, intCodDocDatDetPedPre, intCodItmDatDetPedPre;
                int intCodItmFilSel;
                int intFilEli[];
                boolean blnExiCodItm;
                
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt)
                {
                    intFilEli=tblDat.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt)
                {
                    if (objTblPopMnu.isClickTipoSeleccionSeleccionColumna())
                    {
                        seleccionarCel();
                    }
                    
                    if (objTblPopMnu.isClickCopiar())
                    {
                        intFilSelCop=intFilEli;
                    }
                    
                    intFilSelCop=intFilEli;
                    
                    //no cambiar a la variable global porque esta solo se carga si se da click en "copiar"
                    if (objTblPopMnu.isClickEliminarFila()){
                        for(int p=(intFilEli.length-1);p>=0; p--){
                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + objTblModFix.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM));
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                            //ahora si se remueve la fila seleccionada
                            objTblModFix.removeRow(intFilEli[p]);
                        }
                    }

                    //no cambiar a la variable global porque esta solo se carga si se da click en "copiar"
                    if (objTblPopMnu.isClickBorrarContenido()){
                        //Escriba aquí el código que se debe realizar luego de insertar la fila.
                        objTblModTot.setValueAt("0", 0, (intNumColIniImp_Ped1+1));//
                        objTblModTot.setValueAt("0", 0, (intNumColIniImp_Ped2+1));
                    }

                    if (objTblPopMnu.isClickPegar())
                    {
                        if(tblDat.getSelectedColumn()==(intNumColFinPedPre-2)){
                            if(strNomPedPreIngLSC.equals("")){
                                strNomPedPreIngLSC="";
                                /* Valida si se debe seleccionar o digitar el Número del Pedido.*/
                                if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_COM_LOC) {  //Compras Locales
                                    //Se digita el número del pedido, debido a que no se crearán NPP de Compras Locales en el Presupuesto.
                                    strNomPedPreIngLSC= (javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre del Pedido Previo:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();
                                    if(strNomPedPreIngLSC != null) { //Aceptar
                                        BLN_EXI_NPP=true;    
                                    }
                                    else{ //Cancelar
                                        strNomPedPreIngLSC="";
                                    }
                                }
                                else{ //Importaciones 
                                    //Se selecciona el número del pedido, debido a que las NPP de Importaciones, se crearán en el Presupuesto.
                                    Compras.ZafCom68.ZafCom68_03 objPed=new Compras.ZafCom68.ZafCom68_03(javax.swing.JOptionPane.getFrameForComponent(tblDat), true, objParSis); 
                                    objPed.setVisible(true); 
                                    if(objPed.isPedSel()) { //Aceptar
                                        strNomPedPreIngLSC=objPed.getNumPedSel(); 
                                        BLN_EXI_NPP=true;                                
                                    }
                                    else{ //Cancelar
                                        strNomPedPreIngLSC=""; 
                                    }
                                    objPed=null;                            
                                }
                            }      
                            
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                            
///////////////////////////////Antess
////                            if(strNomPedPreIngLSC.equals("")){   
////                                //se presenta un mensaje donde el usuario debe ingresar el numero del pedido
////                                strNomPedPreIngLSC="";
////                                strNomPedPreIngLSC=(javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre del Pedido Previo:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();
////                            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                            
                            //se añade ese nombre al arreglo q contine la informacion de los Pedidos Previos
                            //tblDat.getColumnModel().getColumn(tblDat.getSelectedColumn()).setHeaderValue(strNomPedPreIngLSC);
                            for(int m=0; m<intFilSelCop.length; m++){
                                if((intFilSelCop[m])< objTblMod.getRowCountTrue()){
                                    intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ITM).toString());
                                    for(int j=0; j<arlDatPedPre.size();j++){
                                        blnExiCodItm=false;
                                        intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                                        if(intArlColSel==tblDat.getSelectedColumn()){
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_PED_PRE, strNomPedPreIngLSC);
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "I");
                                            for(int k=0; k<arlDatDetPedPre.size();k++){
                                                intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);
                                                    if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                                        //se modifica la cantidad en el arreglo
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                                        blnExiCodItm=true;
                                                        break;
                                                    }
                                                    else{
                                                        blnExiCodItm=false;
                                                    }
                                            }
                                            if(!blnExiCodItm){
                                                arlRegDetPedPre=new ArrayList();
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ALT_ITM).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                                arlDatDetPedPre.add(arlRegDetPedPre);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            for(int m=0; m<intFilSelCop.length; m++){
                                if((intFilSelCop[m])< objTblMod.getRowCountTrue()){
                                    for(int j=0; j<arlDatPedPre.size();j++){
                                        blnExiCodItm=false;
                                        intCodEmpDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_EMP);
                                        intCodLocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_LOC);
                                        intCodTipDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                                        intCodDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_DOC);
                                        intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                                        if(intArlColSel==tblDat.getSelectedColumn()){
                                            objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "M");
                                            //detalle
                                            for(int k=0; k<arlDatDetPedPre.size();k++){
                                                intCodEmpDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_EMP);
                                                intCodLocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_LOC);
                                                intCodTipDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                                                intCodDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_DOC);
                                                intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);

                                                intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ITM).toString());

                                                if(  (intCodEmpDatPedPre==intCodEmpDatDetPedPre) && (intCodLocDatPedPre==intCodLocDatDetPedPre) && (intCodTipDocDatPedPre==intCodTipDocDatDetPedPre) && (intCodDocDatPedPre==intCodDocDatDetPedPre)   ){
                                                    if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                                        //se modifica la cantidad en el arreglo
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                                        blnExiCodItm=true;
                                                        break;
                                                    }
                                                    else{
                                                        blnExiCodItm=false;
                                                    }

                                                }
                                            }
                                            if (!blnExiCodItm)
                                            {
                                                arlRegDetPedPre=new ArrayList();
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt((intFilSelCop[m]), intArlColSel).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt((intFilSelCop[m]), INT_TBL_DAT_COD_ALT_ITM).toString());
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                                arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                                arlDatDetPedPre.add(arlRegDetPedPre);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                            
                        for(int k=0; k<intFilSelCop.length; k++)
                        {
                            recalcularValoresTabla((intFilSelCop[k]));
                        }
                        
//                        tblDat.setRowSelectionInterval(0, 0);
//                        tblDat.requestFocus();
//                        //Ubicar el foco en la fila seleccionada.
//                        tblDat.changeSelection(0, 0, true, true);
                        
                        //para seleccionar una fila
                        tblDat.setRowSelectionAllowed(true);
                        tblDat.setColumnSelectionAllowed(false);
                    }
                }
            });
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA).setPreferredWidth(60);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setCellRenderer(objTblCelRenLblGrl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA).setCellRenderer(objTblCelRenLblGrl);
            objTblCelRenLblGrl=null;

            //Libero los objetos auxiliares.
            tcmAux = null;

            intNumColEst=objTblMod.getColumnCount();
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setResizable(false);
                    
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setResizable(false);

            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setResizable(false);
            
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setMaxWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setMinWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setResizable(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkEstItmStkRep=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setCellRenderer(objTblCelRenChkEstItmStkRep);
            objTblCelRenChkEstItmStkRep=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkEstItmStkRep=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setCellEditor(objTblCelEdiChkEstItmStkRep);

            objTblCelEdiChkEstItmStkRep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strNomCliFilSel="";
                String strNomBenFilSel="";
                boolean blnIsChkBef;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_EST_MOV_ITM)){
                        //objTblCelEdiChkEstItmStkRep.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            objTblModHis.initRowsState();
            getTipoConfiguracion();
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean configurarTblDatTot()
    {
        boolean blnRes=true;
        try
        {
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);
            tblTotDat.setModel(objTblModTot);

            //Configurar JTable: Establecer tipo de selección.
            tblTotDat.setRowSelectionAllowed(true);
            tblTotDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTot=new ZafTblFilCab(tblTotDat);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCabTot);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_CHK_EST_MOV_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA).setPreferredWidth(60);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_COD_ALT_ITM2).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_ITM).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_UNI_MED).setResizable(false);

            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setResizable(false);
            
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setMaxWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setMinWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setPreferredWidth(0);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setResizable(false);
                                    
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++)
            {
                tblTotDat.getColumnModel().getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tblTotDat.getColumnModel().getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tblTotDat.getColumnModel().getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tblTotDat.getColumnModel().getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotDat.getColumnModel().getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PES_KG).setCellRenderer(objTblCelRenLblTot);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_PRE_VTA).setCellRenderer(objTblCelRenLblTot);
            objTblCelRenLblTot=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            //spnFix.setHorizontalScrollBarPolicy(spnDat.getHorizontalScrollBarPolicy());
            spnTotDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotDat.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTot objTblColModLis=new ZafTblColModLisTot();
            tblDat.getColumnModel().addColumnModelListener(objTblColModLis);

            ZafTblColModLisTot objTblColModLisTot=new ZafTblColModLisTot();
            tblTotDat.getColumnModel().addColumnModelListener(objTblColModLisTot);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDat1=spnDat.getHorizontalScrollBar();
            barDatTot1=spnTotDat.getHorizontalScrollBar();
            
            barDatVer1=spnDat.getVerticalScrollBar();
            barFix1=spnFix.getVerticalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barDat1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTot1.setValue(evt.getValue());
                }
            });
            //VERTICAL            
            barDatVer1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barFix1.setValue(evt.getValue());
                }
            });
            spnTotDat.setColumnHeader(null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura el JTable "tblDatFix".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatFix() 
    {
        boolean blnRes = true;
        try
        {
            
            //Configurar JTable: Establecer el modelo.
            vecDatFix = new Vector();    //Almacena los datos
            vecCabFix = new Vector(10);   //Almacena las cabeceras
            vecCabFix.clear();
            vecCabFix.add(INT_TBL_DAT_FIX_LIN, "");
            vecCabFix.add(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM, "Est.Mov.Itm.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ITM, "Cod.Itm");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "Cod.Alt.");
            vecCabFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2, "Cod.Alt2.");
            vecCabFix.add(INT_TBL_DAT_FIX_NOM_ITM, "Nombre");
            vecCabFix.add(INT_TBL_DAT_FIX_UNI_MED, "Uni");
            vecCabFix.add(INT_TBL_DAT_FIX_PES_KG, "Peso(kg)");
            vecCabFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "Pre.Vta.Net.");
            vecCabFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "Est.Eli.Itm.");
            
            objTblModFix = new ZafTblMod();
            objTblModFix.setHeader(vecCabFix);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblFix.setModel(objTblModFix);
            //Configurar JTable: Establecer tipo de seleccián.
            tblFix.setRowSelectionAllowed(true);
            tblFix.getTableHeader().setReorderingAllowed(false);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            objTblPopMnuFix.setBorrarContenidoEnabled(true);
            objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilEli[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    intFilEli=tblFix.getSelectedRows();
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuFix.isClickEliminarFila()){
                        for(int p=(intFilEli.length-1);p>=0; p--){
                            //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM));
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                            //ahora si se remueve la fila seleccionada
                            objTblMod.removeRow(intFilEli[p]);
                        }
                    }
                }
            });

            tblFix.setAutoResizeMode(tblDat.getAutoResizeMode());
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblFix.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(74);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setPreferredWidth(12);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(125);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(34);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(58);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(58);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblFix);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix = new ZafMouMotAdaFix();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix = new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabFix);

            //Configurar JTable: Editor de básqueda.
            objTblBusFix = new ZafTblBus(tblFix);

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
            barFix3=spnFix.getHorizontalScrollBar();
            barDatTotFix3=spnTotFix.getHorizontalScrollBar();
            
            barFixVer3=spnFix.getVerticalScrollBar();
            barDat3=spnDat.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barFix3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTotFix3.setValue(evt.getValue());
                }
            });
            //VERTICAL
            barFixVer3.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat3.setValue(evt.getValue());
                }
            });

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_FIX_PES_KG);
            vecAux.add("" + INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
            objTblModFix.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModFix.setModoOperacion(objTblModFix.INT_TBL_INS);


            //Configurar JTable: Agrupamiento de columnas
            ZafTblHeaGrp objTblHeaGrpDatChq=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpDatChq.setHeight(16*4);
            ZafTblHeaColGrp objTblHeaColGrpDatChq=null;
            objTblHeaColGrpDatChq=new ZafTblHeaColGrp("Datos del item");
            objTblHeaColGrpDatChq.setHeight(16);
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_LIN));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM));
            objTblHeaGrpDatChq.addColumnGroup(objTblHeaColGrpDatChq);
            //FIN FIXED

            objTblCelRenLblColFix=new ZafTblCelRenLbl();
            objTblCelRenLblColFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColFix.setTipoFormato(objTblCelRenLblColFix.INT_FOR_NUM);
            objTblCelRenLblColFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblColFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblColFix);
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setCellRenderer(objTblCelRenLblColFix);
            objTblCelRenLblColFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblColFix.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblColFix.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblColFix.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelRenLblFixStr=new ZafTblCelRenLbl();
            objTblCelRenLblFixStr.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblFixStr.setTipoFormato(objTblCelRenLblFixStr.INT_FOR_GEN);
            //tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFixStr);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setCellRenderer(objTblCelRenLblFixStr);
            
            objTblCelRenLblFixStr.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM)==null?"":objTblModFix.getValueAt(objTblCelRenLblFixStr.getRowRender(), INT_TBL_DAT_FIX_EST_ELI_ITM).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblFixStr.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblFixStr.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtPesItmFix=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_DAT_PES_KG).setCellEditor(objTblCelEdiTxtPesItmFix);
            objTblCelEdiTxtPesItmFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bgdPesItmFix;
                int intFil=-1;
                int intCol=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bgdPesItmFix=new BigDecimal(BigInteger.ZERO);
                    intFil=tblFix.getSelectedRow();
                    intCol=tblFix.getSelectedColumn();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    bgdPesItmFix=new BigDecimal(objTblModFix.getValueAt(intFil, intCol)==null?"0":(objTblModFix.getValueAt(intFil, intCol).toString().equals("")?"0":objTblModFix.getValueAt(intFil, intCol).toString()));
                    objTblMod.setValueAt(bgdPesItmFix, intFil, INT_TBL_DAT_PES_KG);
                    recalcularValoresTabla(tblFix.getSelectedRow());
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkFixEstItmStkRep=new ZafTblCelRenChk();
            tblFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellRenderer(objTblCelRenChkFixEstItmStkRep);
            objTblCelRenChkFixEstItmStkRep=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkFixEstItmStkRep=new ZafTblCelEdiChk(tblFix);
            tblFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setCellEditor(objTblCelEdiChkFixEstItmStkRep);
            objTblCelEdiChkFixEstItmStkRep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strNomCliFilSel="";
                String strNomBenFilSel="";
                boolean blnIsChkBef;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblFix.getSelectedRow();
                    intCol=tblFix.getSelectedColumn();
                    
//                    if(objTblModFix.isChecked(intFil, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM)){
//                        objTblCelEdiChkFixEstItmStkRep.setCancelarEdicion(false);
//                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblModFix.isChecked(intFil, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM))
                        objTblMod.setChecked(true, intFil, INT_TBL_DAT_CHK_EST_MOV_ITM);
                    else
                        objTblMod.setChecked(false, intFil, INT_TBL_DAT_CHK_EST_MOV_ITM);
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

    private boolean configurarTblDatFixTot()
    {
        boolean blnRes=true;
        try
        {
            objTblModTotFix=new ZafTblMod();
            objTblModTotFix.setHeader(vecCabFix);
            tblTotFix.setModel(objTblModTotFix);

            //Configurar JTable: Establecer tipo de selección.
            tblTotFix.setRowSelectionAllowed(true);
            tblTotFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabTotFix=new ZafTblFilCab(tblTotFix);
            tblTotFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_LIN).setCellRenderer(objTblFilCabTotFix);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblTotFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_FIX_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FIX_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_FIX_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_PES_KG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setPreferredWidth(60);            
            tcmAux.getColumn(INT_TBL_DAT_FIX_EST_ELI_ITM).setPreferredWidth(60);        
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_LIN, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ALT_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COD_ALT_ITM2, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_NOM_ITM, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_UNI_MED, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_PES_KG, tblTotFix);
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_COS_UNI_GRP, tblTotFix);            
            objTblModTotFix.addSystemHiddenColumn(INT_TBL_DAT_FIX_EST_ELI_ITM, tblTotFix);            
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblFix.getColumnCount(); j++){
                tblTotFix.getColumnModel().getColumn(j).setWidth(tblFix.getColumnModel().getColumn(j).getWidth());
                tblTotFix.getColumnModel().getColumn(j).setMaxWidth(tblFix.getColumnModel().getColumn(j).getMaxWidth());
                tblTotFix.getColumnModel().getColumn(j).setMinWidth(tblFix.getColumnModel().getColumn(j).getMinWidth());
                tblTotFix.getColumnModel().getColumn(j).setPreferredWidth(tblFix.getColumnModel().getColumn(j).getPreferredWidth());
                tblTotFix.getColumnModel().getColumn(j).setResizable(tblFix.getColumnModel().getColumn(j).getResizable());
            }

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblTotFix.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTotFix=new ZafTblCelRenLbl();
            objTblCelRenLblTotFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTotFix.setTipoFormato(objTblCelRenLblTotFix.INT_FOR_NUM);
            objTblCelRenLblTotFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTotFix.getColumnModel().getColumn(INT_TBL_DAT_FIX_PES_KG).setCellRenderer(objTblCelRenLblTotFix);
            tblTotDat.getColumnModel().getColumn(INT_TBL_DAT_FIX_COS_UNI_GRP).setCellRenderer(objTblCelRenLblTotFix);
            objTblCelRenLblTotFix=null;
            
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            spnTotFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            tblTotFix.setTableHeader(null);            
            
            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLisTotFix objTblColModLisFix=new ZafTblColModLisTotFix();
            tblFix.getColumnModel().addColumnModelListener(objTblColModLisFix);

            ZafTblColModLisTotFix objTblColModLisTotFix=new ZafTblColModLisTotFix();
            tblTotFix.getColumnModel().addColumnModelListener(objTblColModLisTotFix);                        
            
            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barFix2=spnFix.getHorizontalScrollBar();
            barDatTotFix2=spnTotFix.getHorizontalScrollBar();
            
            barFixVer2=spnFix.getVerticalScrollBar();
            barDat2=spnDat.getVerticalScrollBar();
            
            //PARA DESPLAZAMIENTOS DE CELDAS
            //HORIZONTAL
            barFix2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDatTotFix2.setValue(evt.getValue());
                }
            });
            //VERTICAL
            barFixVer2.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat2.setValue(evt.getValue());
                }
            });
            spnTotFix.setColumnHeader(null);

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
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_CHK_EST_MOV_ITM:
                    strMsg = "Estado de movimientos del item (Items que no se pueden quedar sin stock)";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM2:
                    strMsg = "Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KG:
                    strMsg = "Peso";
                    break;
                case INT_TBL_DAT_PRE_VTA:
                    strMsg = "Precio de Venta (Costo unitario del grupo)";
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
    private class ZafMouMotAdaFix extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblFix.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol)
            {
                case INT_TBL_DAT_FIX_CHK_EST_MOV_ITM:
                    strMsg = "Estado de movimientos del item(Items que no se pueden quedar sin stock(reposición)";
                    break;
                case INT_TBL_DAT_FIX_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_FIX_COD_ALT_ITM2:
                    strMsg = "Código alterno del item 2";
                    break;
                case INT_TBL_DAT_FIX_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_FIX_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                case INT_TBL_DAT_FIX_PES_KG:
                    strMsg = "Peso";
                    break;
                case INT_TBL_DAT_FIX_COS_UNI_GRP:
                    strMsg = "Costo unitario del grupo";
                    break;
                case INT_TBL_DAT_FIX_EST_ELI_ITM:
                    strMsg = "Estado del item";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblFix.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean cargarAnios(int opcion)
    {
        boolean blnRes=true;
        String strAniSel="";
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.0 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    //Query mejorado (0.4 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while (rst.next())
                    {
                        vecRegHis=new Vector();
                        vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals(""))
                        {
                            //vecRegHis.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis.add(vecRegHis);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis.setData(vecDatHis);
                    tblHis.setModel(objTblModHis);
                    vecDatHis.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    private boolean cargarAniosMeses(int opcion)
    {
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.5 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    //Query mejorado (0.5 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani, ne_mes";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani, ne_mes";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis=new Vector();
                        vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Enero");       break;
                            case 2:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Febrero");     break;
                            case 3:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Marzo");       break;
                            case 4:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Abril");       break;
                            case 5:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Mayo");        break;
                            case 6:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Junio");       break;
                            case 7:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Julio");       break;
                            case 8:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Agosto");      break;
                            case 9:  vecRegHis.add(INT_TBL_DAT_HIS_MES,"Septiembre");  break;
                            case 10: vecRegHis.add(INT_TBL_DAT_HIS_MES,"Octubre");     break;
                            case 11: vecRegHis.add(INT_TBL_DAT_HIS_MES,"Noviembre");   break;
                            case 12: vecRegHis.add(INT_TBL_DAT_HIS_MES,"Diciembre");   break;
                            default: vecRegHis.add(INT_TBL_DAT_HIS_MES,"");            break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals("")))
                        {
                            //vecRegHis.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis.add(vecRegHis);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis.setData(vecDatHis);
                    tblHis.setModel(objTblModHis);
                    vecDatHis.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarAniosMeses2(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.5 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    //Query mejorado (0.5 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani, ne_mes";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani, ne_mes";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis02=new Vector();
                        vecRegHis02.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis02.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis02.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis02.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Enero");       break;
                            case 2:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Febrero");     break;
                            case 3:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Marzo");       break;
                            case 4:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Abril");       break;
                            case 5:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Mayo");        break;
                            case 6:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Junio");       break;
                            case 7:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Julio");       break;
                            case 8:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Agosto");      break;
                            case 9:  vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Septiembre");  break;
                            case 10: vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Octubre");     break;
                            case 11: vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Noviembre");   break;
                            case 12: vecRegHis02.add(INT_TBL_DAT_HIS_MES,"Diciembre");   break;
                            default: vecRegHis02.add(INT_TBL_DAT_HIS_MES,"");            break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals("")))
                        {
                            //vecRegHis02.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis02.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis02.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis02.add(vecRegHis02);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis02.setData(vecDatHis02);
                    tblHis02.setModel(objTblModHis02);
                    vecDatHis02.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarAniosMeses3(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.5 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    //Query mejorado (0.5 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani, ne_mes";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani, ne_mes";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis03=new Vector();
                        vecRegHis03.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis03.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis03.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis03.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Enero");           break;
                            case 2:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Febrero");         break;
                            case 3:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Marzo");           break;
                            case 4:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Abril");           break;
                            case 5:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Mayo");            break;
                            case 6:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Junio");           break;
                            case 7:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Julio");           break;
                            case 8:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Agosto");          break;
                            case 9:  vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Septiembre");      break;
                            case 10: vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Octubre");         break;
                            case 11: vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Noviembre");       break;
                            case 12: vecRegHis03.add(INT_TBL_DAT_HIS_MES,"Diciembre");       break;
                            default: vecRegHis03.add(INT_TBL_DAT_HIS_MES,"");                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals("")))
                        {
                            //vecRegHis03.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis03.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis03.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis03.add(vecRegHis03);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis03.setData(vecDatHis03);
                    tblHis03.setModel(objTblModHis03);
                    vecDatHis03.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarAniosMeses4(int opcion){
        boolean blnRes=true;
        String strAniSel="", strMesSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.5 seg):
//                    strSQL="";
//                    strSQL+="SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    //Query mejorado (0.5 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a1.ne_mes, a2.ne_aniSel, a2.ne_mesSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani, ne_mes";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani, ne_mes";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filMesSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel AND a1.ne_mes=a2.ne_mesSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC, a1.ne_mes DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis04=new Vector();
                        vecRegHis04.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis04.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis04.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis04.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                        switch(rst.getInt("ne_mes")){
                            case 1:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Enero");           break;
                            case 2:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Febrero");         break;
                            case 3:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Marzo");           break;
                            case 4:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Abril");           break;
                            case 5:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Mayo");            break;
                            case 6:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Junio");           break;
                            case 7:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Julio");           break;
                            case 8:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Agosto");          break;
                            case 9:  vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Septiembre");      break;
                            case 10: vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Octubre");         break;
                            case 11: vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Noviembre");       break;
                            case 12: vecRegHis04.add(INT_TBL_DAT_HIS_MES,"Diciembre");       break;
                            default: vecRegHis04.add(INT_TBL_DAT_HIS_MES,"");                break;
                        }
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        strMesSel=rst.getObject("ne_mesSel")==null?"":rst.getString("ne_mesSel");
                        if((!strAniSel.equals("")) && (!strMesSel.equals("")))
                        {
                            //vecRegHis04.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis04.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis04.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis04.add(vecRegHis04);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis04.setData(vecDatHis04);
                    tblHis04.setModel(objTblModHis04);
                    vecDatHis04.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite cargar los datos de años de la tabla 2
     * @param opcion
     * @return true si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean cargarAnios2(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.0 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    //Query mejorado (0.4 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis02=new Vector();
                        vecRegHis02.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis02.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis02.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis02.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis02.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals(""))
                        {
                            //vecRegHis02.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis02.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis02.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis02.add(vecRegHis02);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis02.setData(vecDatHis02);
                    tblHis02.setModel(objTblModHis02);
                    vecDatHis02.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    private boolean cargarAnios3(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.0 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    //Query mejorado (0.4 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis03=new Vector();
                        vecRegHis03.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis03.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis03.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis03.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis03.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals(""))
                        {
                            //vecRegHis03.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis03.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis03.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis03.add(vecRegHis03);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis03.setData(vecDatHis03);
                    tblHis03.setModel(objTblModHis03);
                    vecDatHis03.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    private boolean cargarAnios4(int opcion){
        boolean blnRes=true;
        String strAniSel="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(opcion==0)
                {
                    
                }
                else
                {
                    //Query original (1.0 seg):
//                    strSQL ="";
//                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" FROM tbm_venMenInvBod AS a1 LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2";
//                    strSQL+=" ON a1.ne_ani=a2.ne_aniSel";
//                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+=" GROUP BY a1.co_emp, a1.ne_ani, a2.ne_aniSel";
//                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    //Query mejorado (0.4 seg):
                    strSQL ="";
                    strSQL+=" SELECT a1.co_emp, a1.ne_ani, a2.ne_aniSel";
                    strSQL+=" FROM (";
                    strSQL+=" SELECT co_emp, ne_ani";
                    strSQL+=" FROM tbm_venMenInvBod";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" GROUP BY co_emp, ne_ani";
                    strSQL+=" ) AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_filAniSelCfgImp AS a2 ON (a1.ne_ani=a2.ne_aniSel)";
                    strSQL+=" ORDER BY a1.ne_ani DESC";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        vecRegHis04=new Vector();
                        vecRegHis04.add(INT_TBL_DAT_HIS_LIN,"");
                        vecRegHis04.add(INT_TBL_DAT_HIS_CHK,"");
                        vecRegHis04.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                        vecRegHis04.add(INT_TBL_DAT_HIS_NUM_MES,"");
                        vecRegHis04.add(INT_TBL_DAT_HIS_MES,"");
                        strAniSel=rst.getObject("ne_aniSel")==null?"":rst.getString("ne_aniSel");
                        if(!strAniSel.equals(""))
                        {
                            //vecRegHis04.setElementAt(new Boolean(true), INT_TBL_DAT_HIS_CHK);
                            vecRegHis04.setElementAt(Boolean.TRUE, INT_TBL_DAT_HIS_CHK);
                            vecRegHis04.setElementAt("M", INT_TBL_DAT_HIS_LIN);
                        }
                        vecDatHis04.add(vecRegHis04);
                    }
                    rst.close();
                    rst=null;
                    //Asignar vectores al modelo.
                    objTblModHis04.setData(vecDatHis04);
                    tblHis04.setModel(objTblModHis04);
                    vecDatHis04.clear();
                }
                stm.close();
                stm=null;
                con.close();
                con=null;
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
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a2.tx_desCor");
            arlCam.add("a2.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" GROUP BY a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)   {
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
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panCon = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panFilCab = new javax.swing.JPanel();
        panFilHis = new javax.swing.JPanel();
        panOptHis = new javax.swing.JPanel();
        optHisMen = new javax.swing.JRadioButton();
        optHisAnu = new javax.swing.JRadioButton();
        chkHisVta1 = new javax.swing.JCheckBox();
        panTblHis = new javax.swing.JPanel();
        spnFilHis = new javax.swing.JScrollPane();
        tblHis = new javax.swing.JTable();
        panFilHis02 = new javax.swing.JPanel();
        panOptHis02 = new javax.swing.JPanel();
        optHisMen2 = new javax.swing.JRadioButton();
        optHisAnu2 = new javax.swing.JRadioButton();
        chkHisVta2 = new javax.swing.JCheckBox();
        panTblHis02 = new javax.swing.JPanel();
        spnFilHis02 = new javax.swing.JScrollPane();
        tblHis02 = new javax.swing.JTable();
        panFilHis03 = new javax.swing.JPanel();
        panOptHis03 = new javax.swing.JPanel();
        optHisMen3 = new javax.swing.JRadioButton();
        optHisAnu3 = new javax.swing.JRadioButton();
        chkHisVta3 = new javax.swing.JCheckBox();
        panTblHis03 = new javax.swing.JPanel();
        spnFilHis03 = new javax.swing.JScrollPane();
        tblHis03 = new javax.swing.JTable();
        panFilHis04 = new javax.swing.JPanel();
        panOptHis04 = new javax.swing.JPanel();
        optHisMen4 = new javax.swing.JRadioButton();
        optHisAnu4 = new javax.swing.JRadioButton();
        chkHisVta4 = new javax.swing.JCheckBox();
        panTbllHis04 = new javax.swing.JPanel();
        spnFilHis04 = new javax.swing.JScrollPane();
        tblHis04 = new javax.swing.JTable();
        panFilDet = new javax.swing.JPanel();
        panFilMes = new javax.swing.JPanel();
        lblNumMesRep = new javax.swing.JLabel();
        butRecNumMesChg = new javax.swing.JButton();
        jspNumMesRep = new javax.swing.JSpinner();
        lblNumMesPry = new javax.swing.JLabel();
        jspNumMesIniRep = new javax.swing.JSpinner();
        panFilItm = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panFilChk = new javax.swing.JPanel();
        panChkCal = new javax.swing.JPanel();
        chkItmNotEli = new javax.swing.JCheckBox();
        chkCalRepPes = new javax.swing.JCheckBox();
        chkMosDatKil = new javax.swing.JCheckBox();
        panChkHisVta = new javax.swing.JPanel();
        chkHisVtaStk = new javax.swing.JCheckBox();
        chkHisVtaUni = new javax.swing.JCheckBox();
        chkHisVtaVec = new javax.swing.JCheckBox();
        chkHisVtaMes = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnRpt = new javax.swing.JSplitPane();
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
        panBotAux = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butEliPedPrv = new javax.swing.JButton();
        butExpExc = new javax.swing.JButton();
        btnSubirDatos = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panCon.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(710, 330));
        panFil.setLayout(new java.awt.BorderLayout());

        panFilCab.setPreferredSize(new java.awt.Dimension(710, 206));
        panFilCab.setLayout(new java.awt.GridLayout(1, 0));

        panFilHis.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panFilHis.setPreferredSize(new java.awt.Dimension(380, 100));
        panFilHis.setLayout(new java.awt.BorderLayout());

        panOptHis.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis.setLayout(null);

        optHisMen.setText("Histórico mensual");
        optHisMen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMenActionPerformed(evt);
            }
        });
        panOptHis.add(optHisMen);
        optHisMen.setBounds(20, 31, 140, 14);

        optHisAnu.setSelected(true);
        optHisAnu.setText("Histórico anual");
        optHisAnu.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnuActionPerformed(evt);
            }
        });
        panOptHis.add(optHisAnu);
        optHisAnu.setBounds(20, 17, 140, 14);

        chkHisVta1.setText("Histórico de ventas");
        chkHisVta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVta1ActionPerformed(evt);
            }
        });
        panOptHis.add(chkHisVta1);
        chkHisVta1.setBounds(0, 0, 140, 20);

        panFilHis.add(panOptHis, java.awt.BorderLayout.NORTH);

        panTblHis.setPreferredSize(new java.awt.Dimension(452, 360));
        panTblHis.setLayout(new java.awt.BorderLayout());

        spnFilHis.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFilHis.setViewportView(tblHis);

        panTblHis.add(spnFilHis, java.awt.BorderLayout.CENTER);

        panFilHis.add(panTblHis, java.awt.BorderLayout.CENTER);

        panFilCab.add(panFilHis);

        panFilHis02.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panFilHis02.setPreferredSize(new java.awt.Dimension(380, 100));
        panFilHis02.setLayout(new java.awt.BorderLayout());

        panOptHis02.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis02.setLayout(null);

        optHisMen2.setText("Histórico mensual");
        optHisMen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen2ActionPerformed(evt);
            }
        });
        panOptHis02.add(optHisMen2);
        optHisMen2.setBounds(20, 31, 140, 14);

        optHisAnu2.setSelected(true);
        optHisAnu2.setText("Histórico anual");
        optHisAnu2.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu2ActionPerformed(evt);
            }
        });
        panOptHis02.add(optHisAnu2);
        optHisAnu2.setBounds(20, 17, 140, 14);

        chkHisVta2.setText("Histórico de ventas");
        chkHisVta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVta2ActionPerformed(evt);
            }
        });
        panOptHis02.add(chkHisVta2);
        chkHisVta2.setBounds(0, 0, 140, 20);

        panFilHis02.add(panOptHis02, java.awt.BorderLayout.NORTH);

        panTblHis02.setPreferredSize(new java.awt.Dimension(452, 360));
        panTblHis02.setLayout(new java.awt.BorderLayout());

        spnFilHis02.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis02.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFilHis02.setViewportView(tblHis02);

        panTblHis02.add(spnFilHis02, java.awt.BorderLayout.CENTER);

        panFilHis02.add(panTblHis02, java.awt.BorderLayout.CENTER);

        panFilCab.add(panFilHis02);

        panFilHis03.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panFilHis03.setPreferredSize(new java.awt.Dimension(380, 100));
        panFilHis03.setLayout(new java.awt.BorderLayout());

        panOptHis03.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis03.setLayout(null);

        optHisMen3.setText("Histórico mensual");
        optHisMen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen3ActionPerformed(evt);
            }
        });
        panOptHis03.add(optHisMen3);
        optHisMen3.setBounds(20, 31, 130, 14);

        optHisAnu3.setSelected(true);
        optHisAnu3.setText("Histórico anual");
        optHisAnu3.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu3ActionPerformed(evt);
            }
        });
        panOptHis03.add(optHisAnu3);
        optHisAnu3.setBounds(20, 17, 130, 14);

        chkHisVta3.setText("Histórico de ventas");
        chkHisVta3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVta3ActionPerformed(evt);
            }
        });
        panOptHis03.add(chkHisVta3);
        chkHisVta3.setBounds(0, 0, 140, 20);

        panFilHis03.add(panOptHis03, java.awt.BorderLayout.NORTH);

        panTblHis03.setPreferredSize(new java.awt.Dimension(452, 360));
        panTblHis03.setLayout(new java.awt.BorderLayout());

        spnFilHis03.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis03.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFilHis03.setViewportView(tblHis03);

        panTblHis03.add(spnFilHis03, java.awt.BorderLayout.CENTER);

        panFilHis03.add(panTblHis03, java.awt.BorderLayout.CENTER);

        panFilCab.add(panFilHis03);

        panFilHis04.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panFilHis04.setPreferredSize(new java.awt.Dimension(380, 100));
        panFilHis04.setLayout(new java.awt.BorderLayout());

        panOptHis04.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis04.setLayout(null);

        optHisMen4.setText("Histórico mensual");
        optHisMen4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMen4ActionPerformed(evt);
            }
        });
        panOptHis04.add(optHisMen4);
        optHisMen4.setBounds(20, 31, 130, 14);

        optHisAnu4.setSelected(true);
        optHisAnu4.setText("Histórico anual");
        optHisAnu4.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnu4ActionPerformed(evt);
            }
        });
        panOptHis04.add(optHisAnu4);
        optHisAnu4.setBounds(20, 17, 130, 14);

        chkHisVta4.setText("Histórico de ventas");
        chkHisVta4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVta4ActionPerformed(evt);
            }
        });
        panOptHis04.add(chkHisVta4);
        chkHisVta4.setBounds(0, 0, 140, 20);

        panFilHis04.add(panOptHis04, java.awt.BorderLayout.NORTH);

        panTbllHis04.setPreferredSize(new java.awt.Dimension(452, 360));
        panTbllHis04.setLayout(new java.awt.BorderLayout());

        spnFilHis04.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis04.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFilHis04.setViewportView(tblHis04);

        panTbllHis04.add(spnFilHis04, java.awt.BorderLayout.CENTER);

        panFilHis04.add(panTbllHis04, java.awt.BorderLayout.CENTER);

        panFilCab.add(panFilHis04);

        panFil.add(panFilCab, java.awt.BorderLayout.CENTER);

        panFilDet.setPreferredSize(new java.awt.Dimension(100, 180));
        panFilDet.setLayout(new java.awt.BorderLayout());

        panFilMes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panFilMes.setPreferredSize(new java.awt.Dimension(100, 38));
        panFilMes.setLayout(null);

        lblNumMesRep.setText("Número de meses a reponer:");
        panFilMes.add(lblNumMesRep);
        lblNumMesRep.setBounds(10, 8, 170, 20);

        butRecNumMesChg.setText("C");
        butRecNumMesChg.setToolTipText("Cambia el número de meses a reponer en la tabla");
        butRecNumMesChg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRecNumMesChgActionPerformed(evt);
            }
        });
        panFilMes.add(butRecNumMesChg);
        butRecNumMesChg.setBounds(260, 4, 20, 20);

        jspNumMesRep.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspNumMesRepStateChanged(evt);
            }
        });
        panFilMes.add(jspNumMesRep);
        jspNumMesRep.setBounds(180, 4, 80, 20);

        lblNumMesPry.setText("Mes inicial de saldo:");
        panFilMes.add(lblNumMesPry);
        lblNumMesPry.setBounds(340, 8, 130, 20);

        jspNumMesIniRep.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jspNumMesIniRepStateChanged(evt);
            }
        });
        panFilMes.add(jspNumMesIniRep);
        jspNumMesIniRep.setBounds(470, 4, 50, 20);

        panFilDet.add(panFilMes, java.awt.BorderLayout.PAGE_START);

        panFilItm.setPreferredSize(new java.awt.Dimension(420, 100));
        panFilItm.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFilItm.add(optTod);
        optTod.setBounds(8, 2, 410, 14);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFilItm.add(optFil);
        optFil.setBounds(8, 18, 410, 14);

        panFilDet.add(panFilItm, java.awt.BorderLayout.CENTER);

        panFilChk.setPreferredSize(new java.awt.Dimension(100, 70));
        panFilChk.setLayout(new java.awt.GridLayout(1, 0));

        panChkCal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panChkCal.setLayout(null);

        chkItmNotEli.setText("Mostrar sólo los items que no han sido eliminados");
        panChkCal.add(chkItmNotEli);
        chkItmNotEli.setBounds(3, 10, 330, 14);

        chkCalRepPes.setText("Calcular reposición por peso");
        panChkCal.add(chkCalRepPes);
        chkCalRepPes.setBounds(3, 26, 260, 14);

        chkMosDatKil.setText("Mostrar datos en kilos");
        panChkCal.add(chkMosDatKil);
        chkMosDatKil.setBounds(3, 42, 260, 14);

        panFilChk.add(panChkCal);

        panChkHisVta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panChkHisVta.setLayout(null);

        chkHisVtaStk.setText("Mostrar Histórico de Ventas: Stock Inicial");
        chkHisVtaStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaStkActionPerformed(evt);
            }
        });
        panChkHisVta.add(chkHisVtaStk);
        chkHisVtaStk.setBounds(16, 3, 310, 14);

        chkHisVtaUni.setText("Mostrar Histórico de Ventas: Unidades");
        chkHisVtaUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaUniActionPerformed(evt);
            }
        });
        panChkHisVta.add(chkHisVtaUni);
        chkHisVtaUni.setBounds(16, 18, 310, 14);

        chkHisVtaVec.setText("Mostrar Histórico de Ventas: Veces");
        chkHisVtaVec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaVecActionPerformed(evt);
            }
        });
        panChkHisVta.add(chkHisVtaVec);
        chkHisVtaVec.setBounds(16, 33, 310, 14);

        chkHisVtaMes.setText("Mostrar Histórico de Ventas: Meses");
        chkHisVtaMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaMesActionPerformed(evt);
            }
        });
        panChkHisVta.add(chkHisVtaMes);
        chkHisVtaMes.setBounds(16, 48, 310, 14);

        panFilChk.add(panChkHisVta);

        panFilDet.add(panFilChk, java.awt.BorderLayout.SOUTH);

        panFil.add(panFilDet, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnRpt.setDividerLocation(380);
        spnRpt.setDividerSize(2);

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

        spnRpt.setLeftComponent(panRptFix);

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

        spnRpt.setRightComponent(panRptDat);

        panRpt.add(spnRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panCon.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(685, 46));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(764, 27));
        panBot.setLayout(new java.awt.BorderLayout());

        panBotAux.setPreferredSize(new java.awt.Dimension(279, 26));
        panBotAux.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 1));

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(104, 28));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBotAux.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(104, 28));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBotAux.add(butGua);

        butEliPedPrv.setText("Eliminar N.P.P.");
        butEliPedPrv.setToolTipText("Eliminar Nota de Pedido Previo");
        butEliPedPrv.setPreferredSize(new java.awt.Dimension(116, 28));
        butEliPedPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEliPedPrvActionPerformed(evt);
            }
        });
        panBotAux.add(butEliPedPrv);

        butExpExc.setText("Exportar Excel");
        butExpExc.setPreferredSize(new java.awt.Dimension(118, 28));
        butExpExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpExcActionPerformed(evt);
            }
        });
        panBotAux.add(butExpExc);

        btnSubirDatos.setText("Subir datos");
        btnSubirDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirDatosActionPerformed(evt);
            }
        });
        panBotAux.add(btnSubirDatos);

        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(104, 28));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBotAux.add(butCer);

        panBot.add(panBotAux, java.awt.BorderLayout.CENTER);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 16));
        panBarEst.setRequestFocusEnabled(false);
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

        panCon.add(panBar, java.awt.BorderLayout.SOUTH);

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        panCon.add(lblTit, java.awt.BorderLayout.NORTH);

        getContentPane().add(panCon);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optHisAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnuActionPerformed
        if(optHisAnu.isSelected()){
            optHisMen.setSelected(false);
            cargarAnios(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
        else{
            optHisMen.setSelected(true);
            cargarAniosMeses(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
    }//GEN-LAST:event_optHisAnuActionPerformed

    private void optHisMenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMenActionPerformed
        if(optHisMen.isSelected()){
            optHisAnu.setSelected(false);
            cargarAniosMeses(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
        else{
            optHisAnu.setSelected(true);
            cargarAnios(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
    }//GEN-LAST:event_optHisMenActionPerformed

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
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();                
            }
        }
        else{
            blnCon=false;
        }
        strNomPedPreIngLSC="";
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void jspNumMesRepStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspNumMesRepStateChanged
        objTblModHis.setDataModelChanged(true);
    }//GEN-LAST:event_jspNumMesRepStateChanged

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(isCamVal()){
            if (guardarDatos()) {
                mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
                arlDatFilEli.clear();
            }
            else {
                mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
            }
            strNomPedPreIngLSC="";
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butRecNumMesChgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRecNumMesChgActionPerformed
        cambiarNumeroMesesReponerTabla();
        recalcularValoresTabla();
    }//GEN-LAST:event_butRecNumMesChgActionPerformed

    private void tblDatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblDatFocusGained

    }//GEN-LAST:event_tblDatFocusGained

    private void tblFixFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblFixFocusGained

    }//GEN-LAST:event_tblFixFocusGained

    private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
        java.io.File filExc;
        Process pro;
        BigDecimal bgdCanRepValExpExc=new BigDecimal("0");
        try{
            mostrarMsgInf("<HTML>Recuerde guardar el archivo con otro nombre y en otro directorio.<BR>Si no realiza este paso, al volver a exportar el archivo se sobreescribirá y Ud. perderá todos los cambios efectuados.</HTML>");
            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            if(System.getProperty("os.name").equals("Linux")){
                filExc=new java.io.File("/tmp/ZafCom68.xls");
            } 
            else { 
                filExc=new java.io.File("C:\\Zafiro\\Reportes\\Compras\\ZafCom68\\ZafCom68.xls");
            }
            String strNomHoj="Hoja1";
            
            arlDatExpExc.clear();
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    bgdCanRepValExpExc=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                    if(bgdCanRepValExpExc.compareTo(new BigDecimal("0"))!=0){
                        arlRegExpExc=new ArrayList();
                        arlRegExpExc.add(INT_ARL_EXP_EXC_COD_ALT_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_COD_ALT_ITM2, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM2).toString());                        
                        arlRegExpExc.add(INT_ARL_EXP_EXC_NOM_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_PES_UNI, objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString());
                        arlRegExpExc.add(INT_ARL_EXP_EXC_CAN_REP_LSC, bgdCanRepValExpExc);
                        arlRegExpExc.add(INT_ARL_EXP_EXC_PES_TOT_ITM, objTblMod.getValueAt(i, (k+1)).toString());
                        arlDatExpExc.add(arlRegExpExc);
                    }
                }
            }
            ZafCom68_02 objCom68_02= new ZafCom68_02(arlDatExpExc, filExc, strNomHoj);
            if(objCom68_02.export()){
                mostrarMsgInf("El archivo se cargó correctamente.");
                //Process pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon24/ZafCon24.xls");
                if(System.getProperty("os.name").equals("Linux"))
                {
                    pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCom68.xls");
                }
                else
                {
                    pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Compras/ZafCom68/ZafCom68.xls");
                }
            }
            else
                mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpExcActionPerformed

    private void butEliPedPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEliPedPrvActionPerformed
        int intColSelEliPedPrv=tblDat.getSelectedColumn();
        int intColDetPedPreAux;
        
        if(intColSelEliPedPrv!=-1){
            objTblMod.addSystemHiddenColumn(intColSelEliPedPrv, tblDat);
            for(int e=0; e<(arlDatDetPedPre.size()); e++){
                intColDetPedPreAux=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_NUM_COL);
                
                if(intColDetPedPreAux==intColSelEliPedPrv){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST_ANU, "E");
                    break;
                }
            }
        }
    }//GEN-LAST:event_butEliPedPrvActionPerformed

    private void optHisMen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen2ActionPerformed
        if(optHisMen2.isSelected()){
            optHisAnu2.setSelected(false);
            cargarAniosMeses2(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
        else{
            optHisAnu2.setSelected(true);
            cargarAnios2(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
    }//GEN-LAST:event_optHisMen2ActionPerformed

    private void optHisAnu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu2ActionPerformed
        if(optHisAnu2.isSelected()){
            optHisMen2.setSelected(false);
            cargarAnios2(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
        else{
            optHisMen2.setSelected(true);
            cargarAniosMeses2(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
    }//GEN-LAST:event_optHisAnu2ActionPerformed

    private void optHisMen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen3ActionPerformed
        if(optHisMen3.isSelected()){
            optHisAnu3.setSelected(false);
            cargarAniosMeses3(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
        else{
            optHisAnu3.setSelected(true);
            cargarAnios3(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
    }//GEN-LAST:event_optHisMen3ActionPerformed

    private void optHisAnu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu3ActionPerformed
        if(optHisAnu3.isSelected()){
            optHisMen3.setSelected(false);
            cargarAnios3(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
        else{
            optHisMen3.setSelected(true);
            cargarAniosMeses3(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }        
    }//GEN-LAST:event_optHisAnu3ActionPerformed

    private void optHisMen4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMen4ActionPerformed
        if(optHisMen4.isSelected()){
            optHisAnu4.setSelected(false);
            cargarAniosMeses4(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
        else{
            optHisAnu4.setSelected(true);
            cargarAnios4(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
    }//GEN-LAST:event_optHisMen4ActionPerformed

    private void optHisAnu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnu4ActionPerformed
        if(optHisAnu4.isSelected()){
            optHisMen4.setSelected(false);
            cargarAnios4(1);
            chkHisVtaStk.setSelected(false);
            chkHisVtaStk.setEnabled(false);
        }
        else{
            optHisMen4.setSelected(true);
            cargarAniosMeses4(1);
            chkHisVtaStk.setSelected(true);
            chkHisVtaStk.setEnabled(true);
        }
    }//GEN-LAST:event_optHisAnu4ActionPerformed

    private void chkHisVtaUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaUniActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVtaUniActionPerformed

    private void chkHisVtaVecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaVecActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVtaVecActionPerformed

    private void jspNumMesIniRepStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jspNumMesIniRepStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jspNumMesIniRepStateChanged

    private void chkHisVtaStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaStkActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVtaStkActionPerformed

    private void chkHisVtaMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaMesActionPerformed
        // TODO add your handling code here:
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVtaMesActionPerformed

    private void chkHisVta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVta1ActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVta1ActionPerformed

    private void chkHisVta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVta2ActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVta2ActionPerformed

    private void chkHisVta3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVta3ActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVta3ActionPerformed

    private void chkHisVta4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVta4ActionPerformed
        //ocultarColumnas();
    }//GEN-LAST:event_chkHisVta4ActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        // TODO add your handling code here:
        if (optTod.isSelected())
        {
            objVenConItm01.limpiar();
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void btnSubirDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirDatosActionPerformed
        // TODO add your handling code here:
        try{
            if(cargarArchivoXLS()){
        
            }
            
        }
        catch(Exception e)
        {  
           objUti.mostrarMsgErr_F1(this, e);
        }
        
    }//GEN-LAST:event_btnSubirDatosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnSubirDatos;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEliPedPrv;
    private javax.swing.JButton butExpExc;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butRecNumMesChg;
    private javax.swing.JCheckBox chkCalRepPes;
    private javax.swing.JCheckBox chkHisVta1;
    private javax.swing.JCheckBox chkHisVta2;
    private javax.swing.JCheckBox chkHisVta3;
    private javax.swing.JCheckBox chkHisVta4;
    private javax.swing.JCheckBox chkHisVtaMes;
    private javax.swing.JCheckBox chkHisVtaStk;
    private javax.swing.JCheckBox chkHisVtaUni;
    private javax.swing.JCheckBox chkHisVtaVec;
    private javax.swing.JCheckBox chkItmNotEli;
    private javax.swing.JCheckBox chkMosDatKil;
    private javax.swing.JSpinner jspNumMesIniRep;
    private javax.swing.JSpinner jspNumMesRep;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumMesPry;
    private javax.swing.JLabel lblNumMesRep;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optHisAnu;
    private javax.swing.JRadioButton optHisAnu2;
    private javax.swing.JRadioButton optHisAnu3;
    private javax.swing.JRadioButton optHisAnu4;
    private javax.swing.JRadioButton optHisMen;
    private javax.swing.JRadioButton optHisMen2;
    private javax.swing.JRadioButton optHisMen3;
    private javax.swing.JRadioButton optHisMen4;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panAuxSpnTot;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBotAux;
    private javax.swing.JPanel panChkCal;
    private javax.swing.JPanel panChkHisVta;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panFilChk;
    private javax.swing.JPanel panFilDet;
    private javax.swing.JPanel panFilHis;
    private javax.swing.JPanel panFilHis02;
    private javax.swing.JPanel panFilHis03;
    private javax.swing.JPanel panFilHis04;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panFilMes;
    private javax.swing.JPanel panOptHis;
    private javax.swing.JPanel panOptHis02;
    private javax.swing.JPanel panOptHis03;
    private javax.swing.JPanel panOptHis04;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptDat;
    private javax.swing.JPanel panRptFix;
    private javax.swing.JPanel panTblHis;
    private javax.swing.JPanel panTblHis02;
    private javax.swing.JPanel panTblHis03;
    private javax.swing.JPanel panTbllHis04;
    private javax.swing.JPanel panTotDat;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFilHis;
    private javax.swing.JScrollPane spnFilHis02;
    private javax.swing.JScrollPane spnFilHis03;
    private javax.swing.JScrollPane spnFilHis04;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JSplitPane spnRpt;
    private javax.swing.JScrollPane spnTotDat;
    private javax.swing.JScrollPane spnTotFix;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTable tblHis;
    private javax.swing.JTable tblHis02;
    private javax.swing.JTable tblHis03;
    private javax.swing.JTable tblHis04;
    private javax.swing.JTable tblTotDat;
    private javax.swing.JTable tblTotFix;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa utilizar
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

    private void exitForm(){
        String strTit, strMsg;
        try{
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
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
                //Ubicar el foco en la fila seleccionada.
                tblDat.changeSelection(0, 0, true, true);
            }
            objThrGUI=null;
        }
    }

    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            lblMsgSis.setText("Obteniendo datos...");
            
            setModelChkHis(); 
            getTipoConfiguracion(); 
            
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarCabeceraPedidosConfigurados()){
                    if(cargarNotasPedidoPrevias()){    
                        arlDatFilEli.clear();
                        if(configurarColumnasAniosMeses()){
                            if(cargarDetallePedidosConfigurados()){
                                if(consultaItemsPedidoPrevio()){
                                    if(consultarReg()){
                                        if(recalcularValoresTabla()){
                                            if(ocultarColumnas())
                                            {
                                                objTblMod.initRowsState();
                                                objTblModFix.initRowsState();
                                                lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
                                            }
                                        }
                                    }
                                }
                            }                            
                        }
                    }
                }                        
                objTblMod.initRowsState();
                con.close();
                con=null;
            }
            lblMsgSis.setText("Listo.");
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }  

    private void setModelChkHis()
    {
        //Historico de ventas 1
        if(chkHisVta1.isSelected()){
            if(objTblModHis.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis.setDataModelChanged(true);
            }
        }
        else{
            objTblModHis.setDataModelChanged(false);
        }

        //Historico de ventas 2
        if(chkHisVta2.isSelected()){
            if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis02.setDataModelChanged(true); 
            }
        }
        else{
            objTblModHis02.setDataModelChanged(false);
        }

        //Historico de ventas 3
        if(chkHisVta3.isSelected()){
            if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis03.setDataModelChanged(true); 
            }
        }
        else{
            objTblModHis03.setDataModelChanged(false); 
        }

        //Historico de ventas 4
        if(chkHisVta4.isSelected()){
            if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                objTblModHis04.setDataModelChanged(true);
            }
        }
        else{
            objTblModHis04.setDataModelChanged(false);
        }    
    }    
    
    private boolean getTipoConfiguracion(){
        boolean blnRes=true;
        String strTipHis="";
        Object objNumMesRep=null;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        try{
            if(  (objTblModHis.isDataModelChanged()) || (objTblModHis02.isDataModelChanged()) || (objTblModHis03.isDataModelChanged()) || (objTblModHis04.isDataModelChanged()) ){
                if(optHisAnu.isSelected()){
                    cargarAnios(0);
                    cargarAnios2(0);
                    cargarAnios3(0);
                    cargarAnios4(0);
                }
                else{
                    cargarAniosMeses(0);
                    cargarAniosMeses2(0);
                    cargarAniosMeses3(0);
                    cargarAniosMeses4(0);
                }
            }
            else{
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    stmLoc=conLoc.createStatement();
                    strSQL ="";
                    strSQL+=" SELECT a1.tx_tipHis, a1.ne_numMesRep";
                    strSQL+=" FROM tbm_filCfgImp AS a1";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        strTipHis=rstLoc.getString("tx_tipHis");
                        objNumMesRep=rstLoc.getObject("ne_numMesRep");
                        if(strTipHis.equals("A")){
                            optHisAnu.setSelected(true);
                            optHisMen.setSelected(false);
                            //2
                            optHisAnu2.setSelected(true);
                            optHisMen2.setSelected(false);
                            //3
                            optHisAnu3.setSelected(true);
                            optHisMen3.setSelected(false);
                            //4
                            optHisAnu4.setSelected(true);
                            optHisMen4.setSelected(false);
                        }
                        else if(strTipHis.equals("M")){
                            optHisAnu.setSelected(false);
                            optHisMen.setSelected(true);
                            //2
                            optHisAnu2.setSelected(false);
                            optHisMen2.setSelected(true);
                            //3
                            optHisAnu3.setSelected(false);
                            optHisMen3.setSelected(true);
                            //4
                            optHisAnu4.setSelected(false);
                            optHisMen4.setSelected(true);
                        }
                        jspNumMesRep.setValue(objNumMesRep);
                    }

                    conLoc.close();
                    conLoc=null;
                    stmLoc.close();
                    stmLoc=null;
                    rstLoc.close();
                    rstLoc=null;
                    if(optHisAnu.isSelected()){
                        cargarAnios(1);
                        cargarAnios2(1);
                        cargarAnios3(1);
                        cargarAnios4(1);
                    }
                    else {
                        cargarAniosMeses(1);
                        cargarAniosMeses2(1);
                        cargarAniosMeses3(1);
                        cargarAniosMeses4(1);
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
    
    private boolean cargarCabeceraPedidosConfigurados(){  
        boolean blnRes=true;
        arlDatPedEmb.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_arr, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+="  , (CASE WHEN a1.tx_numDoc3 IS NOT NULL THEN a1.tx_numDoc3 ELSE a1.tx_numDoc2 END) AS tx_numPed ";
                strSQL+=" FROM(";
                strSQL+="    SELECT d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.fe_arr, d1.tx_numDoc2, d1.tx_numDoc3";
                strSQL+="    FROM(";
                strSQL+="         SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.fe_doc, c1.fe_emb, c1.fe_pue, c1.fe_arr, c1.tx_numDoc2, c1.tx_numDoc3";
                strSQL+="	       , c1.nd_canNotPed, c1.nd_canPedEmb, c1.nd_canNotPedFal";
                strSQL+="         FROM(";
                strSQL+="              SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.fe_emb, b1.fe_pue, b1.fe_arr, b1.tx_numDoc2, b1.tx_numDoc3";
                strSQL+="                   , b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="                   , (b1.nd_canNotPed - CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END ) AS nd_canNotPedFal";
                strSQL+="              FROM(";
                strSQL+="                    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.fe_pue, a1.fe_arr, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+="                         , a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="                         , a4.nd_can AS nd_canNotPed, a2.nd_can AS nd_canPedEmb";
                //strSQL+="	             FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="                    FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesemb)";//la fecha estiamada se presenta en base a ARRIBO
                strSQL+="                    INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="                    ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="                    LEFT OUTER JOIN ";
                strSQL+="                    (tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="                     ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a3.st_reg NOT IN('I','E')";
                strSQL+="                    ) ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+="                    AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+="                    WHERE a1.st_reg NOT IN('E','I') AND a1.st_cie='N' AND (a1.st_doc='A' OR a1.st_doc IS NULL)";
                strSQL+="                    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.fe_emb, a1.fe_pue, a1.fe_arr, a1.tx_numDoc2, a1.tx_numDoc3";
                strSQL+="                           , a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can";
                strSQL+="                    ORDER BY a1.co_doc";
                strSQL+="               ) AS b1";
                strSQL+="               GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.fe_emb, b1.fe_pue, b1.fe_arr, b1.tx_numDoc2, b1.tx_numDoc3, b1.nd_canNotPed";
                strSQL+="         ) AS c1";
                strSQL+="         WHERE c1.nd_canNotPedFal > 0";
                strSQL+="    ) AS d1";
                strSQL+="    WHERE d1.nd_canNotPedFal > 0";
                strSQL+="    GROUP BY d1.co_emp, d1.co_loc, d1.co_tipDoc, d1.co_doc, d1.fe_doc, d1.fe_arr, d1.tx_numDoc2, d1.tx_numDoc3";
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                strSQL+="    UNION"; 
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+="         , a1.fe_arr, a1.tx_numDoc2, a1.tx_numDoc4"; //la fecha estimada se presenta en base a ARRIBO
                strSQL+="    FROM tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="    ON a1.co_emp=a2.co_emprelpedembimp AND a1.co_loc=a2.co_locrelpedembimp";
                strSQL+="    AND a1.co_tipDoc=a2.co_tipdocrelpedembimp AND a1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="    WHERE a1.st_reg NOT IN('E','I') AND a1.st_doc='A'";
                strSQL+="    AND a2.co_emp IS NULL";
                strSQL+="    ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                strSQL+=" ) AS a1";
                strSQL+=" ORDER BY a1.fe_arr, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.tx_numDoc2, a1.tx_numDoc3";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"" + rst.getString("co_emp"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"" + rst.getString("co_loc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"" + rst.getString("co_tipDoc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"" + rst.getString("co_doc"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"" + rst.getString("tx_numPed"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"" + rst.getString("fe_arr"));
                    arlDatPedEmb.add(arlRegPedEmb);
                }

                //para agregar la columna de totales
                arlRegPedEmb=new ArrayList();
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_PED,"Total");
                arlRegPedEmb.add(INT_ARL_PED_EMB_NUM_COL,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,"");
                arlRegPedEmb.add(INT_ARL_PED_EMB_FEC_EST_PED_EMB,"");                
                arlDatPedEmb.add(arlRegPedEmb);
                rst.close();
                stm.close();
                rst=null;
                stm=null;
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
    
    private boolean cargarNotasPedidoPrevias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.tx_numdoc2 ";
                strSQL+="      , a1.st_reg, a1.fe_ing, a1.fe_ultmod, a1.co_usring, a1.co_usrmod, a1.st_cie";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cie='N'";
                strSQL+=" ORDER BY a1.co_doc";
                rst=stm.executeQuery(strSQL);
                arlDatPedPre.clear();
                while(rst.next()){
                    arlRegPedPre=new ArrayList();
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "" + rst.getString("co_emp"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "" + rst.getString("co_loc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "" + rst.getString("co_tipdoc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "" + rst.getString("co_doc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "" + rst.getString("fe_doc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "" + rst.getString("ne_numdoc"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "" + rst.getString("tx_numdoc2"));
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                    arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                    arlDatPedPre.add(arlRegPedPre);
                }
                
                //para almacenar cantidades del nuevo pedido, los datos se copian de la columna que ha ingresado LSC, FRUIZ o AP
                arlRegPedPre=new ArrayList();
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                arlDatPedPre.add(arlRegPedPre);
                
                //para almacenar cantidades del nuevo pedido, los datos se copian de la columna que ha ingresado LSC, FRUIZ o AP
                arlRegPedPre=new ArrayList();
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_EMP,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_LOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_TIP_DOC,    "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COD_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_FEC_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_DOC,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_PED_PRE,    "Totales");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_NUM_COL,        "");
                arlRegPedPre.add(INT_ARL_CAB_PED_PRE_COL_MOD,        "");
                arlDatPedPre.add(arlRegPedPre);
                
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
    
    /**
    * Función que permite ocultar columnas
    */ 
    private boolean ocultarColumnas(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllSystemHiddenColumns(tblDat);
            objTblModTot.removeAllSystemHiddenColumns(tblTotDat);
            
            if(ocultaColumnasPedidoSinValor()){
                if(ocultaColumnasPedidoPrevioSinValor()){
                    if( (chkHisVta1.isSelected()) || (chkHisVta2.isSelected()) || (chkHisVta3.isSelected()) || (chkHisVta4.isSelected()) ){
                        if(ocultaColumnaStkIni()){//columna del stock inicial
                            if(ocultaColumnaUnidades()){
                                if(ocultaColumnaVeces()){
                                    if(ocultaColumnaMeses()){
                                        if(ocultaColumnasSumaTotales()){

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    private boolean configurarColumnasAniosMeses()
    {
        boolean blnRes=true;
        try{
            if(con!=null){
                if(eliminaColumnasAdicionadasAnios()){
                    intNumColAddFecCor=numeroColumnasAdicionar();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del PRIMER PANEL
                    intNumColAddFecCor2=numeroColumnasAdicionar2();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del SEGUNDO PANEL
                    intNumColAddFecCor3=numeroColumnasAdicionar3();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del TERCER PANEL
                    intNumColAddFecCor4=numeroColumnasAdicionar4();//el numero de columnas a adicionar dependera de los meses/anios seleccionados en el historico del CUARTO PANEL
                   
                    if(optHisAnu.isSelected()){
                        if(agregarColumnasAdicionadasAnios()){
                            if(agregarColumnasAdicionadasAniosTotales()){//totales
                                if(agregarColumnasAdicionadasAnios2()){
                                    if(agregarColumnasAdicionadasAniosTotales2()){
                                        if(agregarColumnasAdicionadasAnios3()){
                                            if(agregarColumnasAdicionadasAniosTotales3()){
                                                if(agregarColumnasAdicionadasAnios4()){
                                                    if(agregarColumnasAdicionadasAniosTotales4()){
                                                        if(agregarColumnasAdicionadasSumHisVta()){
                                                            if(agregarColumnasAdicionadasSumHisVtaTotales()){
                                                                if(agregarColumnasAdicionadasSumHisVta2()){
                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales2()){ 
                                                                        if(agregarColumnasAdicionadasSumHisVta3()){
                                                                            if(agregarColumnasAdicionadasSumHisVtaTotales3()){
                                                                                if(agregarColumnasAdicionadasSumHisVta4()){
                                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales4()){
                                                                                        if(agregarColumnasPromedioAnual()){
                                                                                            if(agregarColumnasPromedioAnualTotales()){
//---------------------------------------------------------------------------------------------------------------------------------
                                                                                                if(agregarColumnasValoresManualesCalculoReposicion()){
                                                                                                    if(agregarColumnasValoresManualesCalculoReposicionTotales()){
//---------------------------------------------------------------------------------------------------------------------------------
                                                                                                if(agregarColumnasStock()){
                                                                                                    if(agregarColumnasStockTotales()){
                                                                                                        if(agregarColumnasPedidosEmbarcados()){
                                                                                                            if(agregarColumnasPedidosEmbarcadosTotales()){                                                                
                                                                                                                if(agregarColumnasPedidosPrevios()){
                                                                                                                    if(agregarColumnasPedidosPreviosTotales()){
                                                                                                                        if(agregarColumnaDisponible()){
                                                                                                                            if(agregarColumnaDisponibleTotales()){
                                                                                                                                if(agregarColumnaVentaPromedio()){
                                                                                                                                    if(agregarColumnaVentaPromedioTotales()){
                                                                                                                                        if(agregarColumnasReposicion()){
                                                                                                                                            if(agregarColumnasReposicionTotales()){
                                                                                                                                                if(agregarColumnasImportacion()){
                                                                                                                                                    if(agregarColumnasImportacionTotales()){
                                                                                                                                                        if(agregarColumnasItemEliminado()){
                                                                                                                                                            if(agregarColumnasItemEliminadoTotales()){
                                                                                                                                                                if(agregarColumnasCheckBoxItemEliminado()){
                                                                                                                                                                    if(agregarColumnasCheckBoxItemEliminadoTotales()){
                                                                                                                                                                        if(getNumMesRep()){
                                                                                                                                                                            if(agregarColumnasNumMesRep()){
                                                                                                                                                                                if(agregarColumnasNumMesRepTotales()){
                                                                                                                                                                                    if(getNumMesPry()){
                                                                                                                                                                                        if(agregarColumnasNumMesPry()){
                                                                                                                                                                                            if(agregarColumnasNumMesPryTotales()){
                                                                                                                                                                                                objTblModTot.insertRows(2,0);
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
//-------------------------------------------------------------------------------------------
                                                                                        }
                                                                                    }
//-------------------------------------------------------------------------------------------
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
                   }
                    else if(optHisMen.isSelected()){
                        if(agregarColumnasAdicionadasAniosMeses()){//tabla normal
                            if(agregarColumnasAdicionadasAniosMesesTotales()){//tabla de totales
                                if(agregarColumnasAdicionadasAniosMeses2()){
                                    if(agregarColumnasAdicionadasAniosMesesTotales2()){
                                        if(agregarColumnasAdicionadasAniosMeses3()){
                                            if(agregarColumnasAdicionadasAniosMesesTotales3()){
                                                if(agregarColumnasAdicionadasAniosMeses4()){
                                                    if(agregarColumnasAdicionadasAniosMesesTotales4()){
                                                        if(agregarColumnasAdicionadasSumHisVta()){
                                                            if(agregarColumnasAdicionadasSumHisVtaTotales()){
                                                                if(agregarColumnasAdicionadasSumHisVta2()){
                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales2()){
                                                                        if(agregarColumnasAdicionadasSumHisVta3()){
                                                                            if(agregarColumnasAdicionadasSumHisVtaTotales3()){
                                                                                if(agregarColumnasAdicionadasSumHisVta4()){
                                                                                    if(agregarColumnasAdicionadasSumHisVtaTotales4()){
                                                                                        if(agregarColumnasPromedioAnual()){
                                                                                            if(agregarColumnasPromedioAnualTotales()){
//---------------------------------------------------------------------------------------------------------------------------------
                                                                                                if(agregarColumnasValoresManualesCalculoReposicion()){
                                                                                                    if(agregarColumnasValoresManualesCalculoReposicionTotales()){
//---------------------------------------------------------------------------------------------------------------------------------
                                                                                                if(agregarColumnasStock()){
                                                                                                    if(agregarColumnasStockTotales()){
                                                                                                        if(agregarColumnasPedidosEmbarcados()){
                                                                                                            if(agregarColumnasPedidosEmbarcadosTotales()){
                                                                                                                if(agregarColumnasPedidosPrevios()){
                                                                                                                    if(agregarColumnasPedidosPreviosTotales()){
                                                                                                                        if(agregarColumnaDisponible()){
                                                                                                                            if(agregarColumnaDisponibleTotales()){
                                                                                                                                if(agregarColumnaVentaPromedio()){
                                                                                                                                    if(agregarColumnaVentaPromedioTotales()){
                                                                                                                                        if(agregarColumnasReposicion()){
                                                                                                                                            if(agregarColumnasReposicionTotales()){
                                                                                                                                                if(agregarColumnasImportacion()){
                                                                                                                                                    if(agregarColumnasImportacionTotales()){
                                                                                                                                                        if(agregarColumnasItemEliminado()){
                                                                                                                                                            if(agregarColumnasItemEliminadoTotales()){
                                                                                                                                                                if(agregarColumnasCheckBoxItemEliminado()){
                                                                                                                                                                    if(agregarColumnasCheckBoxItemEliminadoTotales()){
                                                                                                                                                                        if(getNumMesRep()){
                                                                                                                                                                            if(agregarColumnasNumMesRep()){
                                                                                                                                                                                if(agregarColumnasNumMesRepTotales()){
                                                                                                                                                                                    if(getNumMesPry()){
                                                                                                                                                                                        if(agregarColumnasNumMesPry()){
                                                                                                                                                                                            if(agregarColumnasNumMesPryTotales()){
                                                                                                                                                                                                objTblModTot.insertRows(2,0);
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
//-------------------------------------------------------------------------------------------
                                                                                        }
                                                                                    }
//-------------------------------------------------------------------------------------------
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
               }
           }

       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }

    private boolean eliminaColumnasAdicionadasAnios(){
        boolean blnRes=true;
        try{
            //arlDatPedPre.clear();
            //arlDatDetPedPre.clear();
            objTblMod.removeAllRows();
            objTblModTot.removeAllRows();
            
            for (int i=(objTblMod.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblMod.removeColumn(tblDat, i);
            }
 
            //tengo columnas de mas en la de totales
            for (int i=(objTblModTot.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblModTot.removeColumn(tblTotDat, i);               
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private int numeroColumnasAdicionar(){
        String strLin;
        int intNumColAdd=0;
        arlDatAniAdd.clear();
        try{
            if(objTblModHis.isDataModelChanged()){
                for(int i=(objTblModHis.getRowCountTrue()-1); i>=0; i--){
                    strLin=objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblModHis.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                            intNumColAdd++;
                            arlRegAniAdd=new ArrayList();
                            arlRegAniAdd.add(INT_ARL_ANI, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                            arlRegAniAdd.add(INT_ARL_NUM_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                            arlRegAniAdd.add(INT_ARL_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                            arlRegAniAdd.add(INT_ARL_COL, "" );
                            arlDatAniAdd.add(arlRegAniAdd);
                        }
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }

   private int numeroColumnasAdicionar2(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd02.clear();
       try{
            if(objTblModHis02.isDataModelChanged()){
                for(int i=(objTblModHis02.getRowCountTrue()-1); i>=0; i--){
                    strLin=objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblModHis02.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                            intNumColAdd++;
                            arlRegAniAdd02=new ArrayList();
                            arlRegAniAdd02.add(INT_ARL_ANI, "" + objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                            arlRegAniAdd02.add(INT_ARL_NUM_MES, "" + objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                            arlRegAniAdd02.add(INT_ARL_MES, "" + objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis02.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                            arlRegAniAdd02.add(INT_ARL_COL, "" );
                            arlDatAniAdd02.add(arlRegAniAdd02);
                        }
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }
   
   private int numeroColumnasAdicionar3(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd03.clear();
       try{
            if(objTblModHis03.isDataModelChanged()){
                for(int i=(objTblModHis03.getRowCountTrue()-1); i>=0; i--){
                    strLin=objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblModHis03.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                            intNumColAdd++;
                            arlRegAniAdd03=new ArrayList();
                            arlRegAniAdd03.add(INT_ARL_ANI, "" + objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                            arlRegAniAdd03.add(INT_ARL_NUM_MES, "" + objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                            arlRegAniAdd03.add(INT_ARL_MES, "" + objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis03.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                            arlRegAniAdd03.add(INT_ARL_COL, "" );
                            arlDatAniAdd03.add(arlRegAniAdd03);
                        }
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }
   
   private int numeroColumnasAdicionar4(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd04.clear();
       try{
            if(objTblModHis04.isDataModelChanged()){
                for(int i=(objTblModHis04.getRowCountTrue()-1); i>=0; i--){
                    strLin=objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblModHis04.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                            intNumColAdd++;
                            arlRegAniAdd04=new ArrayList();
                            arlRegAniAdd04.add(INT_ARL_ANI, "" + objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                            arlRegAniAdd04.add(INT_ARL_NUM_MES, "" + objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                            arlRegAniAdd04.add(INT_ARL_MES, "" + objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis04.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                            arlRegAniAdd04.add(INT_ARL_COL, "" );
                            arlDatAniAdd04.add(arlRegAniAdd04);
                        }
                    }
                }
            }
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
    }
   
    private boolean agregarColumnasAdicionadasAnios(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor=intNumColEst;
        try{
            objTblCelRenLblAni=new ZafTblCelRenLbl();
            objTblCelRenLblAni.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni.setTipoFormato(objTblCelRenLblAni.INT_FOR_NUM);
            objTblCelRenLblAni.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAnios2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor2=intNumColFinFecCor;
        try{
            objTblCelRenLblAni2=new ZafTblCelRenLbl();
            objTblCelRenLblAni2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni2.setTipoFormato(objTblCelRenLblAni2.INT_FOR_NUM);
            objTblCelRenLblAni2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni2.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni2.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni2);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAnios3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor3=intNumColFinFecCor2;
        try{
            objTblCelRenLblAni3=new ZafTblCelRenLbl();
            objTblCelRenLblAni3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni3.setTipoFormato(objTblCelRenLblAni3.INT_FOR_NUM);
            objTblCelRenLblAni3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni3.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni3.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni3.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni3.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni3);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor3=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAnios4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        String strAni="";
        int p=0, f=-1;
        intNumColIniFecCor4=intNumColFinFecCor3;
        try{            
            objTblCelRenLblAni4=new ZafTblCelRenLbl();
            objTblCelRenLblAni4.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAni4.setTipoFormato(objTblCelRenLblAni4.INT_FOR_NUM);
            objTblCelRenLblAni4.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAni4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAni4.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAni4.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAni4.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAni4.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAni4);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor4=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMeses(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor=intNumColEst;
        try{
            objTblCelRenLblAniMes=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes.setTipoFormato(objTblCelRenLblAniMes.INT_FOR_NUM);
            objTblCelRenLblAniMes.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAniMes.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasAdicionadasAniosMeses2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor2=intNumColFinFecCor;
        try{
            objTblCelRenLblAniMes2=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes2.setTipoFormato(objTblCelRenLblAniMes2.INT_FOR_NUM);
            objTblCelRenLblAniMes2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAniMes2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes2.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes2.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes2);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd02, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor2=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMeses3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor3=intNumColFinFecCor2;
        try{
            objTblCelRenLblAniMes3=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes3.setTipoFormato(objTblCelRenLblAniMes3.INT_FOR_NUM);
            objTblCelRenLblAniMes3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAniMes3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes3.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes3.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes3.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes3.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes3);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd03, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;

                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor3=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMeses4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFecCor4=intNumColFinFecCor3;
        try{
            objTblCelRenLblAniMes4=new ZafTblCelRenLbl();
            objTblCelRenLblAniMes4.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblAniMes4.setTipoFormato(objTblCelRenLblAniMes4.INT_FOR_NUM);
            objTblCelRenLblAniMes4.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblAniMes4.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblAniMes4.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblAniMes4.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblAniMes4.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblAniMes4.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Stk.His.":(p==1?"Unidades":(p==2?"Veces":(p==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblAniMes4);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMaxWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMinWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setPreferredWidth(0);
                        tblDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setResizable(false);
                    }
                }
                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd04, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinFecCor4=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
            objTblHeaColSubGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMaxWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setMinWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setPreferredWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor2*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor2+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMaxWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setMinWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setPreferredWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor2+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor3*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor3+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMaxWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setMinWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setPreferredWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor3+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasAdicionadasAniosMesesTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        int p=0, f=-1, d=9999;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<(intNumColAddFecCor4*INT_NUM_COL_HIS_VTA_FEC_TIT); i++){
                if(p==0)
                    f++;
                tbc=new javax.swing.table.TableColumn(intNumColIniFecCor4+i);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                if(optHisMen.isSelected()){
                    if(p==INT_NUM_COL_HIS_VTA_MES){
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMaxWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setMinWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setPreferredWidth(0);
                        tblTotDat.getColumnModel().getColumn(((intNumColIniFecCor4+i))).setResizable(false);
                    }
                }

                if(p==INT_NUM_COL_HIS_VTA_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasAdicionadasSumHisVta(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot=3;
        intNumColIniTot=intNumColFinFecCor4;
        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrp.setHeight(32);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasAdicionadasSumHisVta2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot2=3;
        intNumColIniTot2=intNumColFinTot;
        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot2; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales2");
                    objTblHeaColGrp.setHeight(32);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
   
    private boolean agregarColumnasAdicionadasSumHisVta3(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot3=3;
        intNumColIniTot3=intNumColFinTot2;
        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot3; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales3");
                    objTblHeaColGrp.setHeight(32);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot3=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

    private boolean agregarColumnasAdicionadasSumHisVta4(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddTot4=3;
        intNumColIniTot4=intNumColFinTot3;
        try{
            objTblCelRenLblSumHisVta=new ZafTblCelRenLbl();
            objTblCelRenLblSumHisVta.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSumHisVta.setTipoFormato(objTblCelRenLblSumHisVta.INT_FOR_NUM);
            objTblCelRenLblSumHisVta.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblSumHisVta.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblSumHisVta.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblSumHisVta.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblSumHisVta.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddTot4; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblSumHisVta);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales4");
                    objTblHeaColGrp.setHeight(32);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinTot4=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasAdicionadasSumHisVtaTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<intNumColAddTot; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

    private boolean agregarColumnasAdicionadasSumHisVtaTotales2(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot2; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    private boolean agregarColumnasAdicionadasSumHisVtaTotales3(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot3; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot3+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasAdicionadasSumHisVtaTotales4(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddTot4; i++){
                //String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                String strSubTit=(i==0?"Stk.His.":(i==1?"Unidades":(i==2?"Veces":(i==3?"Meses":""))));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot4+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasPromedioAnual()
    {
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddProAnu=6;//numero de columnas que se adicionan al modelo
        intNumColIniProAnu=intNumColFinTot4;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try
        {
            objTblCelRenLblProAnu=new ZafTblCelRenLbl();
            objTblCelRenLblProAnu.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblProAnu.setTipoFormato(objTblCelRenLblProAnu.INT_FOR_NUM);
            objTblCelRenLblProAnu.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            objTblCelRenLblProAnu.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblProAnu.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblProAnu.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblProAnu.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblProAnu.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtProAnu=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddProAnu; i++)
            {
//------------------------------------------------------------------------------
                //15/Mar/2023: Don Luigi solicitó que se le cambie el nombre a la columna "Manual".
                //String strSubTit=(i==0?"Cal1":(i==1?"Cal2":(i==2?"Cal3":(i==3?"Cal4":(i==4?"Máx.Cal.":"Manual")))));
                String strSubTit=(i==0?"Cal1":(i==1?"Cal2":(i==2?"Cal3":(i==3?"Cal4":(i==4?"Máx.Cal.":"Promedio de venta")))));
//------------------------------------------------------------------------------
                tbc=new javax.swing.table.TableColumn(intNumColIniProAnu+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblProAnu);
                tbc.setCellEditor(objTblCelEdiTxtProAnu);
                objTblCelEdiTxtProAnu.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bgdPedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        blnCalTotAftEdi=true;
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//------------------------------------------------------------------------------
//                        //15_Mar_2023: Don Luigi solicitó que se permitiera manejar decimales.
//                        if (objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()))
//                        {
//                            //El valor ingresado es cambiado al +- valor entero(se quitan los decimales)
//                            bgdPedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
//                            objTblMod.setValueAt("" + bgdPedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
//                        }
//                        else
//                        {
//                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
//                        }
                        String strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()));
                        //Se valida que sólo se calcule 1 vez. Si no se pone ésta validación se calcula 4 veces (1 vez por cada columna que se adicionó al editor).
                        if (blnCalTotAftEdi)
                        {
                            //Si no es un número o es negativo asignar vacío.
                            if (!objUti.isNumero(strAux) || objUti.parseDouble(strAux)<0)
                            {
                                objTblMod.setValueAt("", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            }
                            calcularTotalProAnuVenMan();
                            blnCalTotAftEdi=false;
                        }
//------------------------------------------------------------------------------
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0)
                {
                    objTblHeaColGrp=new ZafTblHeaColGrp("Promedio anual");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);         
            }
            recalcularValoresTabla(tblDat.getSelectedRow());
            intNumColFinProAnu=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasPromedioAnualTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddProAnu; i++){
                String strSubTit=(i==0?"Calculado":"Manual");
                tbc=new javax.swing.table.TableColumn(intNumColIniProAnu+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);     
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

//------------------------------------------------------------------------------
    private boolean agregarColumnasValoresManualesCalculoReposicion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddValManCalRep=7; //numero de columnas que se adicionan al modelo
        intNumColIniValManCalRep=intNumColFinProAnu; //numero de columnas que tiene el modelo antes de adicionar las columnas
        try
        {
            objTblCelRenLblValManCalRep=new ZafTblCelRenLbl();
            objTblCelRenLblValManCalRep.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblValManCalRep.setTipoFormato(objTblCelRenLblValManCalRep.INT_FOR_NUM);
            objTblCelRenLblValManCalRep.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblValManCalRep.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblValManCalRep.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblValManCalRep.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblValManCalRep.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblValManCalRep.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtValManCalRep=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAddValManCalRep; i++)
            {



                String strSubTit=(i==INT_DAT_ADI_ITM_STK_BAS?"Stock base":(i==INT_DAT_ADI_ITM_TRA?"Tránsito":(i==INT_DAT_ADI_ITM_FRE?"Frecuencia":(i==INT_DAT_ADI_ITM_EXC?"Exc":(i==INT_DAT_ADI_ITM_PUN_PAR_MAX?"PPMax":(i==INT_DAT_ADI_ITM_PUN_PAR_MIN?"PPMin":"MOQ"))))));
                tbc=new javax.swing.table.TableColumn(intNumColIniValManCalRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblValManCalRep);
                tbc.setCellEditor(objTblCelEdiTxtValManCalRep);
                objTblCelEdiTxtValManCalRep.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bgdPedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        blnCalTotAftEdi=true;
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//------------------------------------------------------------------------------
                        String strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()));
                        //Se valida que sólo se calcule 1 vez. Si no se pone ésta validación se calcula 4 veces (1 vez por cada columna que se adicionó al editor).
                        if (blnCalTotAftEdi)
                        {
                            //Si no es un número o es negativo asignar vacío.
                            if (!objUti.isNumero(strAux) || objUti.parseDouble(strAux)<0)
                            {
                                objTblMod.setValueAt("", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            }
                            calcularTotalValManCalRep();
                            blnCalTotAftEdi=false;
                        }
//------------------------------------------------------------------------------
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Valores manuales calculo de reposición");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);         
            }
//            recalcularValoresTabla(tblDat.getSelectedRow());
            intNumColFinValManCalRep=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasValoresManualesCalculoReposicionTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddValManCalRep; i++){
                String strSubTit=(i==0?"Calculado":"Manual");
                tbc=new javax.swing.table.TableColumn(intNumColIniValManCalRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);     
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
//------------------------------------------------------------------------------
    
    private boolean agregarColumnasStock(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddStk=3;//numero de columnas que se adicionan al modelo para stock
//------------------------------------------------------------------------------
        //intNumColIniStk=intNumColFinProAnu;//numero de columnas que tiene el modelo antes de adicionar las columnas
        intNumColIniStk=intNumColFinValManCalRep;//numero de columnas que tiene el modelo antes de adicionar las columnas
//------------------------------------------------------------------------------
        try{
            objTblCelRenLblStk=new ZafTblCelRenLbl();
            objTblCelRenLblStk.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblStk.setTipoFormato(objTblCelRenLblStk.INT_FOR_NUM);
            objTblCelRenLblStk.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblStk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblStk.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblStk.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblStk.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblStk.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddStk; i++){
                String strSubTit="";  
                strSubTit=(i==0?"Actual":(i==1?"Importaciones/Compras Locales":"Tot.Dis."));
                    
                tbc=new javax.swing.table.TableColumn(intNumColIniStk+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblStk);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Stock");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinStk=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasStockTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddStk; i++){
                String strSubTit=(i==0?"Actual":(i==1?"Importaciones/Compras Locales":"Tot.Dis."));
                tbc=new javax.swing.table.TableColumn(intNumColIniStk+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasPedidosEmbarcados(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=intNumColFinStk;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        String strFecEstPedEmb="";//fecha estimada de pedidoEmbarcado
        try{
            objTblCelRenLblPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedEmb.setTipoFormato(objTblCelRenLblPedEmb.INT_FOR_NUM);
            objTblCelRenLblPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPed=new ZafTblCelEdiTxt();
            objTblCelRenLblPedEmb.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblPedEmb.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblPedEmb.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblPedEmb.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblPedEmb.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                strNomPed="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_PED);
                strFecEstPedEmb="" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC_EST_PED_EMB);
                    
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblPedEmb);
                
                tbc.setCellEditor(objTblCelEdiTxtPed);
                objTblCelEdiTxtPed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bgdPedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bgdPedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bgdPedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
//                            recalcularValoresTabla(tblDat.getSelectedRow());
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Pedidos en curso");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                
                if(i<(intNumColAddPedEmb)-1){
                    objTblHeaColGrpFecEstEmb=new ZafTblHeaColGrp(strFecEstPedEmb);
                    objTblHeaColGrpFecEstEmb.setHeight(16*2);                
                    objTblHeaColGrpTit.add(objTblHeaColGrpFecEstEmb);
                    objTblHeaColGrpFecEstEmb.add(tbc);
                }
                else{
                    objTblHeaColGrpTit.add(tbc);
                }
            }
            
            recalcularValoresTabla(tblDat.getSelectedRow());
            intNumColFinPedEmb=objTblMod.getColumnCount();

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

    private boolean agregarColumnasPedidosEmbarcadosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            objTblCelEdiTxtPed=new ZafTblCelEdiTxt();                   
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue("");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasPedidosPrevios(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddPedPre=arlDatPedPre.size();//
        intNumColIniPedPre=intNumColFinPedEmb;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPedPre="";
        try{
            objTblCelRenLblPedPre=new ZafTblCelRenLbl();
            objTblCelRenLblPedPre.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPedPre.setTipoFormato(objTblCelRenLblPedPre.INT_FOR_NUM);
            objTblCelRenLblPedPre.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPedPre=new ZafTblCelEdiTxt();
            objTblCelRenLblPedPre.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblPedPre.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblPedPre.getRowRender(), intNumColIniEstItmEli).toString();

                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblPedPre.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblPedPre.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtPedPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bgdPedPre=new BigDecimal(BigInteger.ZERO);
                int intArlColSel=-1;
                int intCodEmpDatPedPre, intCodLocDatPedPre, intCodTipDocDatPedPre, intCodDocDatPedPre;
                int intCodEmpDatDetPedPre, intCodLocDatDetPedPre, intCodTipDocDatDetPedPre, intCodDocDatDetPedPre, intCodItmDatDetPedPre;
                int intCodItmFilSel;
                boolean blnExiCodItm;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                        //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                        bgdPedPre=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                        objTblMod.setValueAt(""+bgdPedPre, tblDat.getSelectedRow(), tblDat.getSelectedColumn());                           
                    }
                    else{
                        objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                    }

                    if(tblDat.getSelectedColumn()==(intNumColFinPedPre-2)){
                        intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString());

                        //se añade ese nombre al arreglo q contine la informacion de los Pedidos Previos
                        //tblDat.getColumnModel().getColumn(tblDat.getSelectedColumn()).setHeaderValue(strNomPedPreIngLSC);
                        for(int j=0; j<arlDatPedPre.size();j++){
                            blnExiCodItm=false;
                            intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);

                            if(intArlColSel==tblDat.getSelectedColumn()){
                                objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "I");

                                for(int k=0; k<arlDatDetPedPre.size();k++){
                                    intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);
                                    if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                        //se modifica la cantidad en el arreglo
                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                        objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                        blnExiCodItm=true;
                                        break;
                                    }
                                    else{
                                        blnExiCodItm=false;
                                    }
                                }
                                if(!blnExiCodItm){
                                    arlRegDetPedPre=new ArrayList();
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                    arlDatDetPedPre.add(arlRegDetPedPre);
                                }
                            }
                        }
                    }
                    else{
                        for(int j=0; j<arlDatPedPre.size();j++){
                            blnExiCodItm=false;
                            intCodEmpDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_EMP);
                            intCodLocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_LOC);
                            intCodTipDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                            intCodDocDatPedPre=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COD_DOC);

                            intArlColSel=objUti.getIntValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_NUM_COL);
                            if(intArlColSel==tblDat.getSelectedColumn()){
                                objUti.setStringValueAt(arlDatPedPre, j, INT_ARL_CAB_PED_PRE_COL_MOD, "M");

                                //detalle
                                for(int k=0; k<arlDatDetPedPre.size();k++){
                                    intCodEmpDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_EMP);
                                    intCodLocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_LOC);
                                    intCodTipDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                                    intCodDocDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_DOC);
                                    intCodItmDatDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_COD_ITM);
                                    intCodItmFilSel=Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM).toString());

                                    if(  (intCodEmpDatPedPre==intCodEmpDatDetPedPre) && (intCodLocDatPedPre==intCodLocDatDetPedPre) && (intCodTipDocDatPedPre==intCodTipDocDatDetPedPre) && (intCodDocDatPedPre==intCodDocDatDetPedPre)   ){
                                        if((intCodItmFilSel==intCodItmDatDetPedPre)){
                                            //se modifica la cantidad en el arreglo
                                            objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                            objUti.setStringValueAt(arlDatDetPedPre, k, INT_ARL_DET_PED_PRE_EST, "S");
                                            blnExiCodItm=true;
                                            break;
                                        }
                                        else{

                                            blnExiCodItm=false;
                                        }
                                    }                                                
                                }
                                if(!blnExiCodItm){
                                    arlRegDetPedPre=new ArrayList();
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     intCodEmpDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     intCodLocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, intCodTipDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     intCodDocDatDetPedPre);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     intCodItmFilSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, objTblMod.getValueAt(tblDat.getSelectedRow(), intArlColSel).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM).toString());
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "S");
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     intArlColSel);
                                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");                                                        
                                    arlDatDetPedPre.add(arlRegDetPedPre);
                                }
                            }
                        }
                    }
                    recalcularValoresTabla(tblDat.getSelectedRow());

                }
                //}
            });
            
            for (int i=0; i<intNumColAddPedPre; i++){
                intFilColSel=i;
                strNomPedPre="" + objUti.getStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                objUti.setStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_COL, "" + (intNumColIniPedPre+i));
                tbc=new javax.swing.table.TableColumn(intNumColIniPedPre+i);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
                tbc.setHeaderValue(strNomPedPre);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblPedPre);
                //editor de la celda
                tbc.setCellEditor(objTblCelEdiTxtPedPre);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Notas de Pedido Previas");
                    objTblHeaColGrp.setHeight(32);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);                
            }
          
            intNumColFinPedPre=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrp=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasPedidosPreviosTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            objTblCelEdiTxtPedPre=new ZafTblCelEdiTxt();                   
            for (int i=0; i<intNumColAddPedPre; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniPedPre+i);
                tbc.setHeaderValue("");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnaDisponible(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddDis=1;//numero de columnas q se deben adicionar al modelo para datos de disponible y de venta
        intNumColIniDis=intNumColFinPedPre;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblDis=new ZafTblCelRenLbl();
            objTblCelRenLblDis.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblDis.setTipoFormato(objTblCelRenLblDis.INT_FOR_NUM);
            objTblCelRenLblDis.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblDis.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblDis.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblDis.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblDis.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblDis.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            for (int i=0; i<intNumColAddDis; i++){
                String strSubTit="Dis.Stk.Ped.Imp.";
                tbc=new javax.swing.table.TableColumn(intNumColIniDis+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblDis);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Disponible");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinDis=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnaVentaPromedio(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddVtaPro=3;//numero de columnas q se deben adicionar al modelo para datos de disponible y de venta
        intNumColIniVtaPro=intNumColFinDis;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblVtaPro=new ZafTblCelRenLbl();
            objTblCelRenLblVtaPro.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblVtaPro.setTipoFormato(objTblCelRenLblVtaPro.INT_FOR_NUM);
            objTblCelRenLblVtaPro.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblVtaPro.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblVtaPro.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblVtaPro.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblVtaPro.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblVtaPro.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            for (int i=0; i<intNumColAddVtaPro; i++){
                String strSubTit=(i==0?"Cal.":(i==1?"Man.":"Cal.Men."));
                //String strSubTit=(i==0?"Cal1.":(i==1?"Cal2.":(i==2?"Cal3.":(i==3?"Cal4.":"Man."))));
                tbc=new javax.swing.table.TableColumn(intNumColIniVtaPro+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblVtaPro);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Venta Promedio");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            intNumColFinVtaPro=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasReposicion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddRep=3;//numero de columnas q se deben adicionar al modelo para datos de reposicion
        intNumColIniRep=intNumColFinVtaPro;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblRep=new ZafTblCelRenLbl();
            objTblCelRenLblRep.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblRep.setTipoFormato(objTblCelRenLblRep.INT_FOR_NUM);
            objTblCelRenLblRep.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblRep.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblRep.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblRep.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblRep.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblRep.setBackground(Color.WHITE);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    recalcularValoresTabla(tblDat.getSelectedRow());                    
                }
            });
            
            for (int i=0; i<intNumColAddRep; i++){
                String strSubTit=(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"));
                tbc=new javax.swing.table.TableColumn(intNumColIniRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblRep);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Reposición");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinRep=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnaDisponibleTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddDis; i++){
                String strSubTit="";//(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"))
                tbc=new javax.swing.table.TableColumn(intNumColIniDis+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnaVentaPromedioTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddVtaPro; i++){
                String strSubTit="";//(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"))
                tbc=new javax.swing.table.TableColumn(intNumColIniVtaPro+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasReposicionTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddRep; i++){
                String strSubTit=(i==0?"Núm.Mes.Rep":(i==1?"Calculada":"Manual"));
                tbc=new javax.swing.table.TableColumn(intNumColIniRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasImportacion(){
        boolean blnRes=true;
        try{
            if(agregarColumnasImportacionPedido1()){
                if(agregarColumnasImportacionPedido2()){
                    
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasImportacionTotales(){
        boolean blnRes=true;
        try{
            if(agregarColumnasImportacionPedido1Totales()){
                if(agregarColumnasImportacionPedido2Totales()){
                    
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean agregarColumnasImportacionPedido1(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddImp=2;//son 5 columnas para cantidad a pedir para importar y 1 mas para el peso.
        intNumColIniImp_Ped1=intNumColFinRep;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblImpPed1=new ZafTblCelRenLbl();
            objTblCelRenLblImpPed1.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblImpPed1.setTipoFormato(objTblCelRenLblImpPed1.INT_FOR_NUM);
            objTblCelRenLblImpPed1.setFormatoNumerico("###,###,##0",false,true);
            objTblCelRenLblImpPed1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblImpPed1.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblImpPed1.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblImpPed1.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblImpPed1.setBackground(Color.WHITE);
                    }
                }
            });
            
            objTblCelEdiTxtImp1=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+1)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped1+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblImpPed1);
                tbc.setCellEditor(objTblCelEdiTxtImp1);
                objTblCelEdiTxtImp1.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bgdPedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt)
                    {
                        
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bgdPedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bgdPedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            //recalcularValoresTabla(tblDat.getSelectedRow());
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pedido");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            recalcularValoresTabla(tblDat.getSelectedRow());
            intNumColFinImp_Ped1=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasImportacionPedido1Totales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);
            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+1)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped1+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }       
    
    private boolean agregarColumnasImportacionPedido2(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddImp=2;//son 2 columnas para cantidad a pedir para importar y 1 mas para el peso.
        intNumColIniImp_Ped2=intNumColFinImp_Ped1;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblImpPed2=new ZafTblCelRenLbl();
            objTblCelRenLblImpPed2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblImpPed2.setTipoFormato(objTblCelRenLblImpPed2.INT_FOR_NUM);
            objTblCelRenLblImpPed2.setFormatoNumerico("###,###,##0",false,true);
            objTblCelRenLblImpPed2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblImpPed2.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblImpPed2.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblImpPed2.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblImpPed2.setBackground(Color.WHITE);
                    }
                }
            });

            objTblCelEdiTxtImp2=new ZafTblCelEdiTxt();

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+2)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblImpPed2);
                tbc.setCellEditor(objTblCelEdiTxtImp2);
                objTblCelEdiTxtImp2.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bgdPedCur=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bgdPedCur=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bgdPedCur, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            //recalcularValoresTabla(tblDat.getSelectedRow());
                        }
                        else{
                            objTblMod.setValueAt("0", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pedido");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            recalcularValoresTabla(tblDat.getSelectedRow());
            intNumColFinImp_Ped2=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasImportacionPedido2Totales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddImp; i++){
                String strSubTit=(i==1?"Peso(kg)":("Can.Ped." + (i+2)));
                tbc=new javax.swing.table.TableColumn(intNumColIniImp_Ped2+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
    
    private boolean agregarColumnasItemEliminado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddEstItmEli=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniEstItmEli=intNumColFinImp_Ped2;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblItmEli=new ZafTblCelRenLbl();
            objTblCelRenLblItmEli.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblItmEli.setTipoFormato(objTblCelRenLblItmEli.INT_FOR_NUM);
            objTblCelRenLblItmEli.setFormatoNumerico("###,###,##0",false,true);
            objTblCelRenLblItmEli.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblItmEli.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblItmEli.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblItmEli.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblItmEli.setBackground(Color.WHITE);
                    }
                }
            });

            for (int i=0; i<intNumColAddEstItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblItmEli);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMaxWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMinWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setPreferredWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setResizable(false);
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Estado");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinEstItmEli=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasItemEliminadoTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddEstItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniEstItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                
                tblTotDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setWidth(0);
                tblTotDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMaxWidth(0);
                tblTotDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setMinWidth(0);
                tblTotDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setPreferredWidth(0);
                tblTotDat.getColumnModel().getColumn(((intNumColIniEstItmEli+i))).setResizable(false);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    private boolean agregarColumnasCheckBoxItemEliminado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAddChkItmEli=1;//es una columna solo para cargar el estado del item, si esta eliminado o no (solo es visual la eliminacion)
        intNumColIniChkItmEli=intNumColFinEstItmEli;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenChkEstEliItm=new ZafTblCelRenChk();
            objTblCelRenChkEstEliItm.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenChkEstEliItm.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenChkEstEliItm.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenChkEstEliItm.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenChkEstEliItm.setBackground(Color.WHITE);
//                        //Mostrar ToolTipText con el contenido de la celda.
                      objTblCelRenChkEstEliItm.setToolTipText("Celda seleccionada: " + objTblCelRenChkEstEliItm.isSelected());
                    }
                }
            });
            
            objTblCelEdiChkEstEliItm=new ZafTblCelEdiChk(tblDat);
            objTblCelEdiChkEstEliItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int fila=-1;
                int columna=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            for (int i=0; i<intNumColAddChkItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniChkItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenChkEstEliItm);
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiChkEstEliItm);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
//                //para ocultar la columna
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMaxWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMinWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setPreferredWidth(0);
//                tblDat.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setResizable(false);
                
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Estado");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }

            intNumColFinChkItmEli=objTblMod.getColumnCount();
            objTblHeaGrp=null;
            objTblHeaColGrp=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean agregarColumnasCheckBoxItemEliminadoTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<intNumColAddChkItmEli; i++){
                String strSubTit=("Itm.Eli");
                tbc=new javax.swing.table.TableColumn(intNumColIniChkItmEli+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(62);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
                //para ocultar la columna
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMaxWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setMinWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setPreferredWidth(0);
//                tblTot.getColumnModel().getColumn(((intNumColIniChkItmEli+i))).setResizable(false);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo:
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
    }

    private boolean consultarReg(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(optHisAnu.isSelected()){
                    if(consultarRegAniGrp()){                
                        blnRes=true;
                    }
                }
                else if(optHisMen.isSelected()){
                    if(consultarRegAniMesGrp()){
                        blnRes=true;
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return blnRes; 
    } 

    
    
    
    private boolean consultarRegAniGrp()
    {
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intTerTmp=0;
        BigDecimal bgdValPedCur=new BigDecimal(BigInteger.ZERO);//pedido en curso
        BigDecimal bgdValPedImp=new BigDecimal(BigInteger.ZERO);//pedido a importar
        BigDecimal bgdValPedPre=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdVarGenKil=BigDecimal.ZERO;
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdVarGenKil_2=new BigDecimal(BigInteger.ZERO);//por si existe otra variable con valor que se debe tomar en cuenta
        try{
            if(con!=null){
                lblMsgSis.setText("Obteniendo datos...");
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                    
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_COM_LOC){
                    strFilItm+="  AND NOT (a1.tx_codAlt LIKE '%I')";  //Solicitado por AP, que en el modulo de compras Locales, no se presenten los items en I.
                }                    
                
                // <editor-fold defaultstate="collapsed" desc="Panel 1: Histórico de Ventas">     
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                          , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_salUni) AS " + "b" + (j+2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 2: Histórico de Ventas">    
                int intSizPan2=arlDatAniAdd.size();
                for(int j=0; j<arlDatAniAdd02.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd02, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd02, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                          , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan2) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan2) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".nd_salUni) AS " + "b" + (j+2+intSizPan2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".ne_numVec) AS " + "b" + (j+2+intSizPan2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".ne_numMes) AS " + "b" + (j+2+intSizPan2) + "_nenumMes" ;
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 3: Histórico de Ventas">  
                int intSizPan3=(arlDatAniAdd.size()+arlDatAniAdd02.size());
                for(int j=0; j<arlDatAniAdd03.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd03, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd03, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                          , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan3) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan3) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan3) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan3) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_salUni) AS " + "b" + (j+2+intSizPan3) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan3) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numVec) AS " + "b" + (j+2+intSizPan3) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numMes) AS " + "b" + (j+2+intSizPan3) + "_nenumMes" ;
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 4: Histórico de Ventas">  
                int intSizPan4=(arlDatAniAdd.size()+arlDatAniAdd02.size()+arlDatAniAdd03.size()+arlDatAniAdd04.size());
                for(int j=0; j<arlDatAniAdd04.size(); j++){                   
                    intAni=objUti.getObjectValueAt(arlDatAniAdd04, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd04, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                     SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                          , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, 0 AS nd_salUni";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2+intSizPan4) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan4) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan4) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan4) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_salUni) AS " + "b" + (j+2+intSizPan4) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan4) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numVec) AS " + "b" + (j+2+intSizPan4) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numMes) AS " + "b" + (j+2+intSizPan4) + "_nenumMes" ;
                }
                //</editor-fold>
                
                strAux+=" WHERE b1.st_reg NOT IN('U','T','E','I') AND b1.st_ser NOT IN('S','T') ";
                strAux+=" GROUP BY b1.co_emp, b1.co_itm, b1.st_itmStk, b1.tx_codAlt, b1.tx_codAlt2, b1.st_itmEli, b1.tx_nomItm, b1.tx_desCor";
                strAux+=" 	 , b1.nd_pesitmkgr, b1.nd_preVta1, b1.nd_stkAct, b1.nd_stkImp, b1.nd_proAnuVenMan";
//------------------------------------------------------------------------------
                strAux+=" , b1.nd_stkBas, b1.nd_tra, b1.nd_fre, b1.nd_punParMin, b1.nd_punParMax, b1.nd_MOQ, b1.nd_exc";
//------------------------------------------------------------------------------

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                strAux+=" ORDER BY b1.tx_codAlt";

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_itm, b1.st_itmStk, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1";
                strSQL+="        " + strCamSel + " ";
                strSQL+="      , b1.nd_stkAct, b1.nd_stkImp";
                strSQL+="      , b1.nd_proAnuVenMan";
                

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, b1.st_itmeli";//aqui
                }
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++)
                {
                    //strSQL+=" 	, " + new Boolean(false);//aqui
                    strSQL+=" 	, " + Boolean.FALSE;//aqui
                }
//------------------------------------------------------------------------------
                strSQL+=" , b1.nd_stkBas";
                strSQL+=" , b1.nd_tra";
                strSQL+=" , b1.nd_fre";
                strSQL+=" , b1.nd_punParMin";
                strSQL+=" , b1.nd_punParMax";
                strSQL+=" , b1.nd_MOQ";
                strSQL+=" , b1.nd_exc";
//------------------------------------------------------------------------------
                strSQL+=" FROM(";
                strSQL+="   SELECT g1.*, h1.nd_stkAct, h1.nd_stkImp ";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.st_itmStk, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg";
                strSQL+="            , a1.st_ser, a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, c1.co_itmMae, a1.nd_proAnuVenMan";
//------------------------------------------------------------------------------
                strSQL+=" , a1.nd_stkBas, a1.nd_tra, a1.nd_fre, a1.nd_punParMin, a1.nd_punParMax, a1.nd_MOQ, a1.nd_exc";
//------------------------------------------------------------------------------
                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	    , a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	    , a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	    , a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	    , a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	    , a1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++)
                {
                    //strSQL+=" 	    , " + new Boolean(false); //aqui
                    strSQL+=" 	    , " + Boolean.FALSE; //aqui
                }
                
                strSQL+=" 	FROM ( (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                strSQL+="               INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm";
                strSQL+="       )";
                strSQL+=" 	INNER JOIN tbm_invBod as a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                if(chkItmNotEli.isSelected()){//mostrar solo los items que no han sido eliminados
                    strSQL+=" 	AND a1.st_itmEli IS NULL";
                }
                strSQL+="       " + strFilItm + "";
                strSQL+="   ) AS g1";
                strSQL+="   INNER JOIN(";
                strSQL+=" 	SELECT j1.*, k1.nd_stkImp ";
                strSQL+="       FROM(  SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct";
                strSQL+="              FROM(";
                strSQL+="                    SELECT x.*, y.co_empGrp ";
                strSQL+="                    FROM( SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                strSQL+="                          FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                          ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                    ) AS x";
                strSQL+="                    INNER JOIN(";
                strSQL+="                          SELECT co_emp, co_bod, co_empGrp"; 
                strSQL+="                          FROM tbr_bodEmpBodGrp";
                strSQL+="                          WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+="                          AND co_bodGrp IN("+strBodEmpGrp+")";  //Presenta stock de todas las demas bodegas, excepto la de Importaciones, Compras Locales.
                strSQL+="                          ORDER BY co_emp"; 
                strSQL+="                    ) AS y";
                strSQL+="                    ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="              ) AS z";
                strSQL+="              GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+="       ) AS j1";
                strSQL+=" 	INNER JOIN(";
                strSQL+="              SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkImp) AS nd_stkImp";
                strSQL+="              FROM(";
                strSQL+="                   SELECT x.*, y.co_empGrp ";
                strSQL+="                   FROM(  SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct AS nd_stkImp, a2.co_itmMae";
                strSQL+="                          FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                          ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                   ) AS x";
                strSQL+="                   INNER JOIN(";
                strSQL+="                          SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                          FROM tbr_bodEmpBodGrp";
                strSQL+="                          WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+="                          AND co_bodGrp IN("+strCodBodImp+")";   //6:Importaciones ; 4:ComprasLocales                
                strSQL+="                          ORDER BY co_emp";
                strSQL+="                   ) AS y";
                strSQL+="                   ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="              ) AS z";
                strSQL+=" 	       GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 	) AS k1 ";
                strSQL+=" 	ON j1.co_itmMae=k1.co_itmMae";
                strSQL+="   ) AS h1 ";
                strSQL+="   ON g1.co_itmMae=h1.co_itmMae";
                strSQL+=" ) AS b1";
                strSQL+="" + strAux;
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                BigDecimal bgdTotPed=new BigDecimal(BigInteger.ZERO);
                while(rst.next()){
                    if (blnCon){
                        //para cargar los datos en la tabla de datos
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK_EST_MOV_ITM, null);
                        vecReg.add(INT_TBL_DAT_COD_ITM,     "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,     "" + rst.getString("tx_desCor"));
                        
                        bgdValPesItmKil=new BigDecimal(rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr").equals("")?"0":rst.getString("nd_pesitmkgr"));
                        bgdVarGenKil=new BigDecimal(rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1").equals("")?"0":rst.getString("nd_preVta1"));
                        
                        vecReg.add(INT_TBL_DAT_PES_KG,      "" + bgdValPesItmKil);
                        vecReg.add(INT_TBL_DAT_PRE_VTA,     "" + bgdVarGenKil);  //rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni")
                                       
                        if(chkMosDatKil.isSelected())
                            vecReg.add(INT_TBL_DAT_PRE_VTA, "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                        else
                            vecReg.add(INT_TBL_DAT_PRE_VTA, "" + bgdVarGenKil);
                        
                        //adicionar las columnas de fecha de corte panel 1
                        bgdVarGenKil=BigDecimal.ZERO;
                        for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":rst.getString(j+1).equals("")?"0":rst.getString(j+1));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //adicionar las columnas de fecha de corte panel 2
                        bgdVarGenKil=BigDecimal.ZERO;
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //adicionar las columnas de fecha de corte panel 3
                        bgdVarGenKil=BigDecimal.ZERO;
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        
                        //adicionar las columnas de fecha de corte panel 4
                        bgdVarGenKil=BigDecimal.ZERO;
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //adicionar las columnas de totales 1
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 2
                        for(int j=intNumColIniTot2; j<intNumColFinTot2;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 3
                        for(int j=intNumColIniTot3; j<intNumColFinTot3;j++){
                            vecReg.add(j,     "0");
                        }
                        //adicionar las columnas de totales 4
                        for(int j=intNumColIniTot4; j<intNumColFinTot4;j++){
                            vecReg.add(j,     "0");
                        }
                        
                        //adicionar las columnas de promedio anual
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdVarGenKil=new BigDecimal(rst.getObject("nd_proAnuVenMan")==null?"0":rst.getString("nd_proAnuVenMan").equals("")?"0":rst.getString("nd_proAnuVenMan"));
                        for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                            if(j==(intNumColFinProAnu-1)){
                                if(chkMosDatKil.isSelected())
                                    vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));
                                else
                                    vecReg.add(j,     "" + bgdVarGenKil);
                            }
                            else
                                vecReg.add(j,     "0");
                        }
                        
//------------------------------------------------------------------------------
                        //Valores manuales calculo de reposición:
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_STK_BAS, objUti.parseString(rst.getString("nd_stkBas")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_TRA, objUti.parseString(rst.getString("nd_tra")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_FRE, objUti.parseString(rst.getString("nd_fre")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_EXC, objUti.parseString(rst.getString("nd_exc")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MAX, objUti.parseString(rst.getString("nd_punParMax")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MIN, objUti.parseString(rst.getString("nd_punParMin")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_MOQ, objUti.parseString(rst.getString("nd_MOQ"))); 
//------------------------------------------------------------------------------

                        //adicionar las columnas de stock
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdVarGenKil_2=BigDecimal.ZERO;
                        for(int j=intNumColIniStk; j<intNumColFinStk;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                            bgdVarGenKil_2=new BigDecimal(rst.getObject("nd_stkImp")==null?"0":(rst.getString("nd_stkImp").equals("")?"0":rst.getString("nd_stkImp")));
                            if(j==intNumColIniStk)
                                if(chkMosDatKil.isSelected())
                                    vecReg.add(j,     bgdVarGenKil.multiply(bgdValPesItmKil));
                                else
                                    vecReg.add(j,     bgdVarGenKil);
                            else if(j==(intNumColIniStk+1)){
                                if(chkMosDatKil.isSelected())
                                    vecReg.add(j,     bgdVarGenKil_2.multiply(bgdValPesItmKil));
                                else
                                    vecReg.add(j,     bgdVarGenKil_2);
                            }
                            else
                            vecReg.add(j,     "0");

                        }
                        //adicionar las columnas de pedidos en curso
                        for(int d=0; d<arlDatPedEmb.size(); d++){
                            vecReg.add((intNumColIniPedEmb+d),     "");
                        }
                        
                        for(int d=0; d<arlDatPedPre.size(); d++){
                            vecReg.add((intNumColIniPedPre+d),     "");
                        }

                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniDis; j<intNumColFinDis;j++){
                                vecReg.add(j,     "0");
                        }

                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniVtaPro; j<intNumColFinVtaPro;j++){
                                vecReg.add(j,     "0");
                        }
                    
                        //adicionar las columnas de reposicion calculado en base a los datos ingresados o presentados
                        for(int j=intNumColIniRep; j<intNumColFinRep;j++){
                            if(j==intNumColIniRep)
                                vecReg.add(j,     jspNumMesRep.getValue());//coloca el valor ingresado por el usuario en "numero de meses a reponer"
                            else
                                vecReg.add(j,     "0");
                        }
                        //adicionar 
                        intTerTmp=1;
                        
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": (rst.getString("nd_canPed" + intTerTmp + "").equals("")?"0":rst.getString("nd_canPed" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil=bgdValPedImp;
                        //2
                        bgdVarGenKil_2=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": (rst.getString("nd_canpedpes" + intTerTmp + "").equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil_2=bgdValPedImp;
                        
                        for(int j=intNumColIniImp_Ped1; j<(intNumColFinImp_Ped1-1);j++){
                                if(chkMosDatKil.isSelected())
                                    vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil) );
                                else
                                    vecReg.add(j,     "" + bgdValPedImp );
                                
                                if(chkMosDatKil.isSelected())
                                    vecReg.add((j+1),     "" + bgdValPedImp );
                                else
                                    vecReg.add((j+1),     "" + bgdValPedImp );
                                intTerTmp++;

                        }
                        for(int j=intNumColIniImp_Ped2; j<(intNumColFinImp_Ped2-1);j++){
                            bgdValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": (rst.getString("nd_canPed" + intTerTmp + "").equals("")?"0":rst.getString("nd_canPed" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                            vecReg.add(j,     "" + bgdValPedImp );
                            bgdValPedImp=objUti.redondearBigDecimal(  (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": (rst.getString("nd_canpedpes" + intTerTmp + "").equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + ""))  ), objParSis.getDecimalesMostrar());
                            vecReg.add((j+1),     "" + bgdValPedImp );
                            intTerTmp++;
                        }
                        
                        
                        for(int j=intNumColIniEstItmEli; j<intNumColFinEstItmEli;j++){
                            vecReg.add(j,     "" + (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ) );//aqui
                        }
                              
                        for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++)
                        {
                            //vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?new Boolean(true):new Boolean(false)  ) );//aqui
                            vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?Boolean.TRUE:Boolean.FALSE  ) );//aqui
                        }
                        
                        for(int j=intNumColIniNumMesRep; j<intNumColFinNumMesRep;j++){
                            vecReg.add(j, "");
                        }
                        
                        for(int j=intNumColIniNumMesPry; j<intNumColFinNumMesPry;j++){
                            vecReg.add(j, "");
                        }
                     
                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
                        if(strAux.equals("S"))
                        {
                            //vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_EST_MOV_ITM);
                            vecReg.setElementAt(Boolean.TRUE, INT_TBL_DAT_CHK_EST_MOV_ITM);
                        }
                        
                        vecDat.add(vecReg);

                        //para cargar los datos en la tabla fija
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM, null);
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM,     "" + rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_DAT_FIX_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));
                        
                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
                        if(strAux.equals("S"))
                        {
                            //vecRegFix.setElementAt(new Boolean(true),INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                            vecRegFix.setElementAt(Boolean.TRUE, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                        }
                        
                        vecDatFix.add(vecRegFix);

                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                vecAux=new Vector();
                //adicionar las columnas de promedio anual
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){

                    if(objParSis.getCodigoUsuario()==1){
                        if(j==(intNumColFinProAnu-1))
                            vecAux.add(""+j);
                    }
                    else{
                        if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_ING_IMP){
                            if(objPerUsr.isOpcionEnabled(4072)){  //Ficha "Reporte": Tabla->Modificar "Promedio anual (Manual)"
                                if(j==(intNumColFinProAnu-1))
                                    vecAux.add(""+j);
                            }
                        }
                        else{
                            if(objPerUsr.isOpcionEnabled(4325)){  //Ficha "Reporte": Tabla->Modificar "Promedio anual (Manual)"
                                if(j==(intNumColFinProAnu-1))
                                    vecAux.add(""+j);
                            }                        
                        }
                    }
                }

                //adicionar las columnas de importacion
                for(int j=intNumColIniImp_Ped1; j<intNumColFinImp_Ped1;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }

                }
                for(int j=intNumColIniImp_Ped2; j<intNumColFinImp_Ped2;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                }

                for(int j=intNumColIniPedPre; j<intNumColFinPedPre;j++){
                    if(j!=(intNumColFinPedPre-1))
                        vecAux.add(""+j);
                }
                
                for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                        vecAux.add(""+j);
                }
//------------------------------------------------------------------------------
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Valores manuales calculo de reposición:
                    vecAux.add("" + (intNumColIniValManCalRep + 0));
                    vecAux.add("" + (intNumColIniValManCalRep + 1));
                    vecAux.add("" + (intNumColIniValManCalRep + 2));
                    vecAux.add("" + (intNumColIniValManCalRep + 3));
                    vecAux.add("" + (intNumColIniValManCalRep + 4));
                    vecAux.add("" + (intNumColIniValManCalRep + 5));
                }
                else
                {
                    //4631: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Stock base)"
                    if (objPerUsr.isOpcionEnabled(4631))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_STK_BAS));
                    //4632: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Tránsito)"
                    if (objPerUsr.isOpcionEnabled(4632))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_TRA));
                    //4633: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Frecuencia)"
                    if (objPerUsr.isOpcionEnabled(4633))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_FRE));
                    //4634: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (PPMax)"
                    if (objPerUsr.isOpcionEnabled(4634))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_PUN_PAR_MAX));
                    //4635: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (PPMin)"
                    if (objPerUsr.isOpcionEnabled(4635))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_PUN_PAR_MIN));
                    //4636: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (MOQ)"
                    if (objPerUsr.isOpcionEnabled(4636))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_MOQ));
                }
//------------------------------------------------------------------------------
                
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                //objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();


                //tabla fija
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();

                //se cargan los datos de valores del pedido embarcado
                int intCodItmTbl=0;
                int intCodItmArl=0;
                
                String strEstPedEmb="", strEstPedPre="";
                int intColPedEmb=0, intColPedPre=0;

                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    bgdValPesItmKil=new BigDecimal(objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG).toString()));
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_COD_ITM);
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        intColPedEmb=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_NUM_COL);
                        
                        if(strEstPedEmb.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                bgdValPedCur=objUti.getBigDecimalValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_CAN_PED_EMB);
                                
                                if(chkMosDatKil.isSelected())
                                    objTblMod.setValueAt((bgdValPedCur.multiply(bgdValPesItmKil)), d, intColPedEmb);
                                else
                                    objTblMod.setValueAt(bgdValPedCur, d, intColPedEmb);

                                bgdTotPed=bgdTotPed.add(bgdValPedCur);

                            }
                        }

                    }

                }

                intCodItmTbl=0;
                intCodItmArl=0;
                //se carga la información de los pedidos previos en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    bgdValPesItmKil=new BigDecimal(objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(d, INT_TBL_DAT_FIX_PES_KG).toString()));
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_COD_ITM);
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        intColPedPre=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_NUM_COL);                        
                        if(strEstPedPre.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                bgdValPedPre=objUti.getBigDecimalValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_CAN_PED_PRE);

                                if(chkMosDatKil.isSelected())
                                    objTblMod.setValueAt((bgdValPedPre.multiply(bgdValPesItmKil)), d, intColPedPre);
                                else
                                    objTblMod.setValueAt(bgdValPedPre, d, intColPedPre);

                                //bgdTotPed=bgdTotPed.add(bgdValPedPre);
                                bgdValPedPre=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }
                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
                }
                
                
                //lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    private boolean consultarRegAniMesGrp()
    {
        boolean blnRes=true;
        int intAni=0, intAniAnt=0;
        int intMes=0, intMesAnt=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intTerTmp=1;
        BigDecimal bgdValPedCur=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValPedImp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdValPedPre=new BigDecimal(BigInteger.ZERO);
        //Nuevo - KILOS
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdVarGenKil=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdVarGenKil_2=new BigDecimal(BigInteger.ZERO);//por si existe otra variable con valor que se debe tomar en cuenta
        try{
            if(con!=null){
                lblMsgSis.setText("Obteniendo datos...");
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strFilItm+=objVenConItm01.getCondicionesSQL();
                }
                
                if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_COM_LOC){
                    strFilItm+="  AND NOT (a1.tx_codAlt LIKE '%I')";  //Solicitado por AP, que en el modulo de compras Locales, no se presenten los items en I.
                }
                
                // <editor-fold defaultstate="collapsed" desc="Panel 1: Histórico de Ventas">  
                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                        , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3 ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + ""; 
                    strAux+="                           AND a2.co_bod IN("+strBodEmpGrp+")"; //Presenta stock de todas las demas bodegas, excepto la de Importaciones, Compras Locales.
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                                , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                                    , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                                    , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                                    , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                                    , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                                    , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod";
                    strAux+="                                      , a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                          , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_salUni) AS " + "b" + (j+2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;                    
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 2: Histórico de Ventas">  
                int intSizPan2=arlDatAniAdd.size();
                for(int j=0; j<arlDatAniAdd02.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd02, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd02, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd02, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd02, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd02, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd02, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd02, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd02, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                        , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3 ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
//                    if(objParSis.getCodigoUsuario()!=1){
//                        strAux+="                       INNER JOIN tbr_bodLocPrgUsr AS a4 ON a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod";
//                    }
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    if(objParSis.getCodigoUsuario()!=1){
//                        strAux+="                       AND a4.co_loc=" + objParSis.getCodigoLocal() + "";
//                        strAux+="                       AND a1.st_reg='A'";
//                        strAux+="                       AND a4.co_mnu=" + objParSis.getCodigoMenu() + "";
//                        strAux+="                       AND a4.co_usr=" + objParSis.getCodigoUsuario() + "";
//                        strAux+="                       AND a4.tx_natBod IN('I','A')";
//                        strAux+="                       AND a4.st_reg IN('A','P')";
//                    }
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN("+strBodEmpGrp+")";  //Presenta stock de todas las demas bodegas, excepto la de Importaciones, Compras Locales.
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                                , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                                    , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                                    , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                                    , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                                    , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                                    , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                          , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";
                    strAux+=" 		) AS b" + (j+2+intSizPan2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan2) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan2) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".nd_salUni) AS " + "b" + (j+2+intSizPan2) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".ne_numVec) AS " + "b" + (j+2+intSizPan2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan2) + ".ne_numMes) AS " + "b" + (j+2+intSizPan2) + "_nenumMes" ;                    
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 3: Histórico de Ventas">  
                int intSizPan3=(arlDatAniAdd.size()+arlDatAniAdd02.size());
                for(int j=0; j<arlDatAniAdd03.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd03, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd03, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd03, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd03, j, INT_ARL_NUM_MES);
                    
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd03, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd03, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd03, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd03, j, INT_ARL_NUM_MES);
                    
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                        , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3 ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN("+strBodEmpGrp+")";  //Presenta stock de todas las demas bodegas, excepto la de Importaciones, Compras Locales.
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                                , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                                    , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                                    , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                                    , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                                    , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                                    , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                          , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";
                    strAux+=" 		) AS b" + (j+2+intSizPan3) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan3) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan3) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan3) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_salUni) AS " + "b" + (j+2+intSizPan3) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan3) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numVec) AS " + "b" + (j+2+intSizPan3) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan3) + ".ne_numMes) AS " + "b" + (j+2+intSizPan3) + "_nenumMes" ;                    
                }
                //</editor-fold>
                
                // <editor-fold defaultstate="collapsed" desc="Panel 3: Histórico de Ventas"> 
                int intSizPan4=(arlDatAniAdd.size()+arlDatAniAdd02.size()+arlDatAniAdd03.size()+arlDatAniAdd04.size());
                for(int j=0; j<arlDatAniAdd04.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd04, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd04, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd04, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd04, j, INT_ARL_NUM_MES);
                    intAniAnt=objUti.getObjectValueAt(arlDatAniAdd04, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd04, j, INT_ARL_ANI);
                    intMesAnt=objUti.getObjectValueAt(arlDatAniAdd04, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd04, j, INT_ARL_NUM_MES);
                    intMesAnt=intMesAnt==1?12:(intMesAnt-1);
                    intAniAnt=intMesAnt==12?(intAniAnt-1):intAniAnt;
                    
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="                   SELECT c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                        , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   FROM(";
                    strAux+="                   	SELECT  a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom AS tx_nomBod";
                    strAux+="                           FROM tbm_inv AS a1 INNER JOIN tbm_invBod AS a2";
                    strAux+="                           ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strAux+="                           INNER JOIN tbm_bod AS a3 ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
                    strAux+="                           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                           " + strFilItm + "";
                    strAux+="                           AND a2.co_bod IN("+strBodEmpGrp+")";  //Presenta stock de todas las demas bodegas, excepto la de Importaciones, Compras Locales.
                    strAux+="                           GROUP BY a1.co_emp, a1.co_itm, a3.co_bod, a3.tx_nom";
                    strAux+="                           ORDER BY a1.co_emp, a3.co_bod";
                    strAux+="                   ) AS c1";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                           SELECT b1.co_emp, b1.co_itm, b1.co_bod, b1.nd_uniVen, b1.ne_numVec, b1.ne_numMes, b1.ne_ani";
                    strAux+="                                , b1.ne_mes, b1.nd_pesitmkgr, b1.nd_preVta1, b1.co_perAnt";
                    strAux+="                           FROM(";
                    strAux+="                               SELECT a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                                    , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                                    , CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS ne_mes";
                    strAux+="                                    , '' || a4.ne_ani || CASE WHEN a4.ne_mes<10 THEN '0'||a4.ne_mes ELSE ''||a4.ne_mes END AS co_per";
                    strAux+="                                    , '' ||" + intAniAnt + "" + (intMesAnt<10?("0"+intMesAnt):intMesAnt) + " AS co_perAnt";
                    strAux+="                                    , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1";
                    strAux+="                               FROM tbm_venMenInvBod AS a4";
                    strAux+="                               INNER JOIN tbm_inv AS a1 ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                               WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                               AND a4.ne_ani=" + intAni + "";
                    strAux+="                               AND a4.ne_mes=" + intMes + "";
                    strAux+="                               " + strFilItm + "";
                    strAux+="                               GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes, a1.nd_pesitmkgr, a1.nd_preVta1";
                    strAux+="                               ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="                           ) AS b1";
                    strAux+="                   ) AS c2";
                    strAux+="                   ON c1.co_emp=c2.co_emp AND c1.co_itm=c2.co_itm AND c1.co_bod=c2.co_bod";
                    strAux+="                   LEFT OUTER JOIN(";
                    strAux+="                       SELECT a1.co_emp, a1.co_bod, a1.co_itm, ''|| a1.co_per AS co_per, a1.nd_salUni";
                    strAux+="                       FROM tbm_salMenInvBod AS a1";
                    strAux+="                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strAux+="                       AND a1.co_per=" + intAniAnt + "" + (intMesAnt<10?"0" + intMesAnt:intMesAnt) + "";
                    strAux+="                   ) AS c3";
                    strAux+="                   ON c1.co_emp=c3.co_emp AND c1.co_itm=c3.co_itm AND c1.co_bod=c3.co_bod";
                    strAux+="                   GROUP BY c1.co_emp, c1.co_itm, c1.co_bod, c2.nd_uniVen, c2.ne_numVec, c2.ne_numMes, c2.ne_ani";
                    strAux+="                          , c2.ne_mes, c2.nd_pesitmkgr, c2.nd_preVta1, c3.nd_salUni";
                    strAux+="                   ORDER BY c1.co_itm, c2.ne_mes";
                    strAux+=" 		) AS b" + (j+2+intSizPan4) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2+intSizPan4) + ".co_emp AND b1.co_bod=b" + (j+2+intSizPan4) + ".co_bod AND b1.co_itm=b" + (j+2+intSizPan4) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_salUni) AS " + "b" + (j+2+intSizPan4) + "_ndsalUni" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".nd_uniVen) AS " + "b" + (j+2+intSizPan4) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numVec) AS " + "b" + (j+2+intSizPan4) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2+intSizPan4) + ".ne_numMes) AS " + "b" + (j+2+intSizPan4) + "_nenumMes" ;                    
                }
                //</editor-fold>
                
                strAux+="   WHERE b1.st_reg NOT IN('U','T','E','I') AND b1.st_ser NOT IN('S','T') ";
                strAux+="   GROUP BY b1.co_emp, b1.co_itm, b1.st_itmStk, b1.tx_codAlt, b1.tx_codAlt2, b1.st_itmEli, b1.tx_nomItm, b1.tx_desCor";
                strAux+="          , b1.nd_pesitmkgr, b1.nd_preVta1, b1.nd_stkAct, b1.nd_stkImp, b1.nd_proAnuVenMan";
//------------------------------------------------------------------------------
                strAux+=" , b1.nd_stkBas, b1.nd_tra, b1.nd_fre, b1.nd_punParMin, b1.nd_punParMax, b1.nd_MOQ, b1.nd_exc";
//------------------------------------------------------------------------------

                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }

                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strAux+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strAux+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                                
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL ="";
                strSQL+=" SELECT b1.co_emp, b1.co_itm, b1.st_itmStk, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_pesitmkgr, b1.nd_preVta1";
                strSQL+="        " + strCamSel + " ";
                strSQL+="      , b1.nd_stkAct, b1.nd_stkImp";
                strSQL+="      , b1.nd_proAnuVenMan";               
                
                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }                
                
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, b1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, b1.nd_canpedpes" + intTerTmp + "";                    
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, b1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++)
                {
                    //strSQL+=" 	," + new Boolean(false);//aqui
                    strSQL+=" 	," + Boolean.FALSE;//aqui
                }                
//------------------------------------------------------------------------------
                strSQL+=" , b1.nd_stkBas";
                strSQL+=" , b1.nd_tra";
                strSQL+=" , b1.nd_fre";
                strSQL+=" , b1.nd_punParMin";
                strSQL+=" , b1.nd_punParMax";
                strSQL+=" , b1.nd_MOQ";
                strSQL+=" , b1.nd_exc";
//------------------------------------------------------------------------------
                strSQL+=" FROM(";
                strSQL+="   SELECT g1.*, h1.nd_stkAct, h1.nd_stkImp ";
                strSQL+="   FROM(  SELECT a1.co_emp, a1.co_itm, a1.st_itmStk, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+="               , a1.nd_pesitmkgr, (a1.nd_preVta1*0.75) AS nd_preVta1, c1.co_itmMae, a1.nd_proAnuVenMan";
//------------------------------------------------------------------------------
                strSQL+=" , a1.nd_stkBas, a1.nd_tra, a1.nd_fre, a1.nd_punParMin, a1.nd_punParMax, a1.nd_MOQ, a1.nd_exc";
//------------------------------------------------------------------------------
                intTerTmp=1;
                for(int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }     
                
                for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++){
                    strSQL+=" 	, a1.nd_canPed" + intTerTmp + "";
                    strSQL+=" 	, a1.nd_canpedpes" + intTerTmp + "";
                    intTerTmp++;
                }
                
                for(int k=intNumColIniEstItmEli; k<(intNumColFinEstItmEli);k++){
                    strSQL+=" 	, a1.st_itmeli";//aqui
                }
                
                for(int k=intNumColIniChkItmEli; k<(intNumColFinChkItmEli);k++)
                {
                    //strSQL+=" 	, " + new Boolean(false);//aqui
                    strSQL+=" 	, " + Boolean.FALSE;//aqui
                }
                
                strSQL+=" 		FROM ( (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                strSQL+="                       INNER JOIN tbm_equInv AS c1 ON a1.co_emp=c1.co_emp AND a1.co_itm=c1.co_itm";
                strSQL+="               )";
                strSQL+=" 		INNER JOIN tbm_invBod as a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if(chkItmNotEli.isSelected()){//mostrar solo los items que no han sido eliminados
                    strSQL+=" 		AND a1.st_itmEli IS NULL";
                }
                strSQL+="               " + strFilItm + "";
                strSQL+=" ) AS g1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 		SELECT j1.*, k1.nd_stkImp ";
                strSQL+="               FROM(   SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct";
                strSQL+="                       FROM(  SELECT x.*, y.co_empGrp";
                strSQL+="                              FROM(  SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae";
                strSQL+="                                     FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                     ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                              ) AS x";
                strSQL+="                              INNER JOIN(";
                strSQL+="                                     SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                     FROM tbr_bodEmpBodGrp";
                strSQL+="                                     WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+="                                 AND co_bodGrp IN("+strBodEmpGrp+")"; //Presenta stock de todas las bodegas, excepto de Compras Locales e Importaciones.
                strSQL+="                                     ORDER BY co_emp";
                strSQL+="                               ) AS y";
                strSQL+="                               ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                           ) AS z";
                strSQL+=" 		            GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+="               ) AS j1";
                strSQL+=" 		INNER JOIN(";
                strSQL+="                          SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_stkImp) AS nd_stkImp";
                strSQL+="                          FROM(  SELECT x.*, y.co_empGrp ";
                strSQL+="                                 FROM(  SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct AS nd_stkImp, a2.co_itmMae";
                strSQL+="                                        FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                      	 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                 ) AS x";
                strSQL+="                                 INNER JOIN(";
                strSQL+="                                      	 SELECT co_emp, co_bod, co_empGrp";
                strSQL+="                                      	 FROM tbr_bodEmpBodGrp";
                strSQL+="                                        WHERE co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+="                                        AND co_bodGrp IN("+strCodBodImp+")";   //6:Importaciones ; 4:ComprasLocales
                strSQL+="                                      	 ORDER BY co_emp";
                strSQL+="                                ) AS y";
                strSQL+="                                ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+="                            ) AS z";
                strSQL+=" 			     GROUP BY z.co_empGrp, z.co_itmMae";
                strSQL+=" 		) AS k1";
                strSQL+=" 		ON j1.co_itmMae=k1.co_itmMae";
                strSQL+="   ) AS h1";
                strSQL+="   ON g1.co_itmMae=h1.co_itmMae";
                strSQL+=" ) AS b1";
                strSQL+="" + strAux;
                //verificar los datos que sean en kilos
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                BigDecimal bgdTotPed=new BigDecimal(BigInteger.ZERO);
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK_EST_MOV_ITM, null);
                        vecReg.add(INT_TBL_DAT_COD_ITM,     "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,     "" + rst.getString("tx_desCor"));
                        bgdValPesItmKil=new BigDecimal(rst.getObject("nd_pesitmkgr")==null?"0":(rst.getString("nd_pesitmkgr").equals("")?"0":rst.getString("nd_pesitmkgr")));
                        vecReg.add(INT_TBL_DAT_PES_KG,      "" + bgdValPesItmKil);
                        
                        bgdVarGenKil=new BigDecimal(rst.getObject("nd_preVta1")==null?"0":(rst.getString("nd_preVta1").equals("")?"0":rst.getString("nd_preVta1")));
                        //vecReg.add(INT_TBL_DAT_PRE_VTA, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));//*PESO
                        if(chkMosDatKil.isSelected())
                            vecReg.add(INT_TBL_DAT_PRE_VTA, "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                        else
                            vecReg.add(INT_TBL_DAT_PRE_VTA, "" + bgdVarGenKil);
                        
                        //--2--
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 1: Adicionar las columnas de fecha de corte*/"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        //vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));//*PESO
                        for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //</editor-fold>
                        
                        //--3--
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 2: Adicionar las columnas de fecha de corte*/"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        //vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));//*PESO
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //</editor-fold>
                        
                        //--4--
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 3: Adicionar las columnas de fecha de corte*/"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        //vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));//*PESO
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //</editor-fold>
                        
                        //--5--                        
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 4: Adicionar las columnas de fecha de corte*/"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        //vecReg.add(j,     "" + rst.getObject(j+1)==null?"0":rst.getString(j+1));//*PESO
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                            bgdVarGenKil=new BigDecimal(rst.getObject(j+1)==null?"0":(rst.getString(j+1).equals("")?"0":rst.getString(j+1)));
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 1: Adicionar las columnas de Totales*/"> 
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "0");
                        }     
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 2: Adicionar las columnas de Totales*/"> 
                        for(int j=intNumColIniTot2; j<intNumColFinTot2;j++){
                            vecReg.add(j,     "0");
                        }      
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 3: Adicionar las columnas de Totales*/"> 
                        for(int j=intNumColIniTot3; j<intNumColFinTot3;j++){
                            vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Panel 4: Adicionar las columnas de Totales*/"> 
                        for(int j=intNumColIniTot4; j<intNumColFinTot4;j++){
                            vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
                        //--6--
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Promedio Anual */"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdVarGenKil=new BigDecimal(rst.getObject("nd_proAnuVenMan")==null?"0":(rst.getString("nd_proAnuVenMan").equals("")?"0":rst.getString("nd_proAnuVenMan")));
                        //vecReg.add(j,     "" + rst.getObject("nd_proAnuVenMan")==null?"0":rst.getString("nd_proAnuVenMan"));//*PESO//*PESO
                        for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                            if(j==(intNumColFinProAnu-1)){
                                if(chkMosDatKil.isSelected())
                                    vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil));//*PESO
                                else
                                    vecReg.add(j,     "" + bgdVarGenKil);
                            }                                
                            else
                                vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
//------------------------------------------------------------------------------
                        //Valores manuales calculo de reposición:
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_STK_BAS, objUti.parseString(rst.getString("nd_stkBas")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_TRA, objUti.parseString(rst.getString("nd_tra")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_FRE, objUti.parseString(rst.getString("nd_fre")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_EXC, objUti.parseString(rst.getString("nd_exc")));                      
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MAX, objUti.parseString(rst.getString("nd_punParMax")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MIN, objUti.parseString(rst.getString("nd_punParMin")));
                        vecReg.add(intNumColIniValManCalRep+INT_DAT_ADI_ITM_MOQ, objUti.parseString(rst.getString("nd_MOQ")));
 //------------------------------------------------------------------------------
                        
                        //--7--
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Stock */"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdVarGenKil_2=BigDecimal.ZERO;
                        //vecReg.add(j, rst.getObject("nd_stkAct")==null?"0":rst.getString("nd_stkAct"));
                        //vecReg.add(j, rst.getObject("nd_stkImp")==null?"0":(rst.getString("nd_stkImp")));
                        bgdVarGenKil=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                        bgdVarGenKil_2=new BigDecimal(rst.getObject("nd_stkImp")==null?"0":(rst.getString("nd_stkImp").equals("")?"0":rst.getString("nd_stkImp")));
                        for(int j=intNumColIniStk; j<intNumColFinStk;j++){
                            if(j==intNumColIniStk){
                                if(chkMosDatKil.isSelected()){
                                    vecReg.add(j,     bgdVarGenKil.multiply(bgdValPesItmKil));
                                } //*PESO
                                else{
                                    vecReg.add(j,     bgdVarGenKil);
                                }
                            }//*PESO
                            else if(j==(intNumColIniStk+1)){
                                if(chkMosDatKil.isSelected()){
                                    vecReg.add(j,     bgdVarGenKil_2.multiply(bgdValPesItmKil));
                                }//*PESO
                                else{
                                    vecReg.add(j,     bgdVarGenKil_2);
                                }
                            }//*PESO
                            else
                                vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Pedidos en Curso */"> 
                        for(int d=0; d<arlDatPedEmb.size(); d++){
                            vecReg.add((intNumColIniPedEmb+d),     "");
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Pedidos Previos */"> 
                        for(int d=0; d<arlDatPedPre.size(); d++){
                            vecReg.add((intNumColIniPedPre+d),     "");
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar la columna de Disponible en base a los datos ingresados o presentados. */"> 
                        for(int j=intNumColIniDis; j<intNumColFinDis;j++){
                                vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar la columna de Venta en base a los datos ingresados o presentados. */"> 
                        for(int j=intNumColIniVtaPro; j<intNumColFinVtaPro;j++){
                                vecReg.add(j,     "0");
                        }  
                        //</editor-fold>
                        
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de reposición calculado en base a los datos ingresados o presentados. */"> 
                        for(int j=intNumColIniRep; j<intNumColFinRep;j++){
                            if(j==intNumColIniRep)
                                vecReg.add(j,     jspNumMesRep.getValue());//*PESO
                            else
                                vecReg.add(j,     "0");
                        }
                        //</editor-fold>
                        
                        //--8--
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Importación. */"> 
                        intTerTmp=1;
                        //bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPed" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPed" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        //bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": ((rst.getString("nd_canpedpes" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPed" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPed" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil=bgdValPedImp;
                        //2
                        bgdVarGenKil_2=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPedPes" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPedPes" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPedPes" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil_2=bgdValPedImp;
                        
                        for(int j=intNumColIniImp_Ped1; j<(intNumColFinImp_Ped1-1);j++){
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil) );//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil );

                            if(chkMosDatKil.isSelected())
                                vecReg.add((j+1),     "" + bgdVarGenKil_2.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add((j+1),     "" + bgdVarGenKil_2);
                            
                            intTerTmp++;
                        }
                        //</editor-fold>
                        
                        //--9--
                        //<editor-fold defaultstate="collapsed" desc="/* Adicionar las columnas de Importación. */"> 
                        bgdVarGenKil=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canPed" + intTerTmp + "")==null?"0": ((rst.getString("nd_canPed" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canPed" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil=bgdValPedImp;
                        //2
                        bgdVarGenKil_2=BigDecimal.ZERO;
                        bgdValPedImp=objUti.redondearBigDecimal(   (rst.getObject("nd_canpedpes" + intTerTmp + "")==null?"0": ((rst.getString("nd_canpedpes" + intTerTmp + "")).equals("")?"0":rst.getString("nd_canpedpes" + intTerTmp + "")) ), objParSis.getDecimalesMostrar());
                        bgdVarGenKil_2=bgdValPedImp;
                        for(int j=intNumColIniImp_Ped2; j<(intNumColFinImp_Ped2-1);j++){
                            if(chkMosDatKil.isSelected())
                                vecReg.add(j,     "" + bgdVarGenKil.multiply(bgdValPesItmKil) );//*PESO
                            else
                                vecReg.add(j,     "" + bgdVarGenKil);                            

                            if(chkMosDatKil.isSelected())
                                vecReg.add((j+1),     "" + bgdVarGenKil_2.multiply(bgdValPesItmKil));//*PESO
                            else
                                vecReg.add((j+1),     "" + bgdVarGenKil_2);
                            intTerTmp++;
                        }
                        //</editor-fold>
                        
                        //--10--
                        for(int j=intNumColIniEstItmEli; j<intNumColFinEstItmEli;j++){
                            vecReg.add(j,     "" + (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ) );
                        }
                                
                        for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++)
                        {
                            //vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?new Boolean(true):new Boolean(false)  ) );
                            vecReg.add(j,     (  (rst.getObject("st_itmEli")==null?"":rst.getString("st_itmEli")  ).equals("S")?Boolean.TRUE:Boolean.FALSE  ) );
                        }                        
                        
                        for(int j=intNumColIniNumMesRep; j<intNumColFinNumMesRep;j++){
                            vecReg.add(j, "");
                        }
                        
                        for(int j=intNumColIniNumMesPry; j<intNumColFinNumMesPry;j++){
                            vecReg.add(j, "");
                        }        
                                                
                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
                        if(strAux.equals("S"))
                        {
                            //vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_EST_MOV_ITM);
                            vecReg.setElementAt(Boolean.TRUE, INT_TBL_DAT_CHK_EST_MOV_ITM);
                        }
                        
                        vecDat.add(vecReg);

                        //TABLA FIXED
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_DAT_FIX_LIN,"");
                        vecRegFix.add(INT_TBL_DAT_FIX_CHK_EST_MOV_ITM, null);
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ITM,     "" + rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COD_ALT_ITM2,"" + rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_DAT_FIX_NOM_ITM,     "" + rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_DAT_FIX_UNI_MED,     "" + rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_DAT_FIX_PES_KG,      "" + rst.getObject("nd_pesitmkgr")==null?"0":rst.getString("nd_pesitmkgr"));
                        //vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_cosUni")==null?"0":rst.getString("nd_cosUni"));
                        vecRegFix.add(INT_TBL_DAT_FIX_COS_UNI_GRP, "" + rst.getObject("nd_preVta1")==null?"0":rst.getString("nd_preVta1"));
                        vecRegFix.add(INT_TBL_DAT_FIX_EST_ELI_ITM, "" + rst.getObject("st_itmeli")==null?"0":rst.getString("st_itmeli"));

                        strAux=rst.getObject("st_itmStk")==null?"":rst.getString("st_itmStk");
                        if(strAux.equals("S"))
                        {
                            //vecRegFix.setElementAt(new Boolean(true),INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                            vecRegFix.setElementAt(Boolean.TRUE, INT_TBL_DAT_FIX_CHK_EST_MOV_ITM);
                        }

                        vecDatFix.add(vecRegFix);
                        //FIN DE TABLA FIXED
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
                        break;
                    }
                }
                
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                vecAux=new Vector();
                
                //adicionar las columnas de promedio anual
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                    if(objParSis.getCodigoUsuario()==1){
                        if(j==(intNumColFinProAnu-1))
                            vecAux.add(""+j);
                    }
                    else{
                        if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_ING_IMP){
                            if(objPerUsr.isOpcionEnabled(4072)){  //Ficha "Reporte": Tabla->Modificar "Promedio anual (Manual)"
                                if(j==(intNumColFinProAnu-1)){
                                    vecAux.add(""+j);
                                }
                            }
                        }
                        else{  //Compras Locales
                            if(objPerUsr.isOpcionEnabled(4325)){   //Ficha "Reporte": Tabla->Modificar "Promedio anual (Manual)"
                                if(j==(intNumColFinProAnu-1)){
                                    vecAux.add(""+j);
                                }
                            }                        
                        }
                    }
                }

                //adicionar las columnas de importacion
                for(int j=intNumColIniImp_Ped1; j<intNumColFinImp_Ped1;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped1-1))
                            vecAux.add(""+j);
                    }
                    

                }
                for(int j=intNumColIniImp_Ped2; j<intNumColFinImp_Ped2;j++){
                    if(chkCalRepPes.isSelected()){//por peso
                        if(j==(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                    else{//por pieza
                        if(j!=(intNumColFinImp_Ped2-1))
                            vecAux.add(""+j);
                    }
                }
                              
                for(int j=intNumColIniPedPre; j<intNumColFinPedPre;j++){
                    if(j!=(intNumColFinPedPre-1))
                        vecAux.add(""+j);
                }
                
                for(int j=intNumColIniChkItmEli; j<intNumColFinChkItmEli;j++){
                        vecAux.add(""+j);
                }
//------------------------------------------------------------------------------
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Valores manuales calculo de reposición:
                    vecAux.add("" + (intNumColIniValManCalRep + 0));
                    vecAux.add("" + (intNumColIniValManCalRep + 1));
                    vecAux.add("" + (intNumColIniValManCalRep + 2));
                    vecAux.add("" + (intNumColIniValManCalRep + 3));
                    vecAux.add("" + (intNumColIniValManCalRep + 4));
                    vecAux.add("" + (intNumColIniValManCalRep + 5));
                }
                else
                {
                    //4631: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Stock base)"
                    if (objPerUsr.isOpcionEnabled(4631))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_STK_BAS));
                    //4632: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Tránsito)"
                    if (objPerUsr.isOpcionEnabled(4632))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_TRA));
                    //4633: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (Frecuencia)"
                    if (objPerUsr.isOpcionEnabled(4633))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_FRE));
                    //4634: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (PPMax)"
                    if (objPerUsr.isOpcionEnabled(4634))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_PUN_PAR_MAX));
                    //4635: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (PPMin)"
                    if (objPerUsr.isOpcionEnabled(4635))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_PUN_PAR_MIN));
                    //4636: Ficha "Reporte": Tabla->Modificar "Valores manuales calculo de reposición (MOQ)"
                    if (objPerUsr.isOpcionEnabled(4636))
                        vecAux.add("" + (intNumColIniValManCalRep + INT_DAT_ADI_ITM_MOQ));
                }
//------------------------------------------------------------------------------
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();

                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();

                //se cargan los datos de valores del pedido embarcado
                int intCodItmTbl=0;
                int intCodItmArl=0;
                
                String strEstPedEmb="", strEstPedPre="";
                int intColPedEmb=0, intColPedPre=0;
                bgdValPesItmKil=BigDecimal.ZERO;
                //se carga la informacion de los pedidos en curso en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    //nuevo esquema por kilos
                    bgdValPesItmKil=new BigDecimal(objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG).toString()));
                    
                    //para capturar el peso del item
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_COD_ITM);
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        intColPedEmb=objUti.getIntValueAt(arlDatDetPedEmb,    e, INT_ARL_DET_PED_EMB_NUM_COL);                        
                        if(strEstPedEmb.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                bgdValPedCur=objUti.getBigDecimalValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_CAN_PED_EMB);
                                if(chkMosDatKil.isSelected())
                                    objTblMod.setValueAt((bgdValPedCur.multiply(bgdValPesItmKil)), d, intColPedEmb);
                                else
                                    objTblMod.setValueAt(bgdValPedCur, d, intColPedEmb);
                                bgdTotPed=bgdTotPed.add(bgdValPedCur);
                                bgdValPedCur=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }
                
                intCodItmTbl=0;
                intCodItmArl=0;
                bgdValPesItmKil=BigDecimal.ZERO;
                //se carga la información de los pedidos previos en las columnas necesarias
                for(int d=0; d<objTblMod.getRowCountTrue(); d++){
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_COD_ITM).toString()));
                    bgdValPesItmKil=new BigDecimal(objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(d, INT_TBL_DAT_PES_KG).toString()));
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        intCodItmArl=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_COD_ITM);
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        intColPedPre=objUti.getIntValueAt(arlDatDetPedPre,    e, INT_ARL_DET_PED_PRE_NUM_COL);                        
                        if(strEstPedPre.equals("")){
                            if(intCodItmTbl==intCodItmArl){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                bgdValPedPre=objUti.getBigDecimalValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_CAN_PED_PRE);
                                if(chkMosDatKil.isSelected())
                                    objTblMod.setValueAt((bgdValPedPre.multiply(bgdValPesItmKil)), d, intColPedPre);
                                else
                                    objTblMod.setValueAt(bgdValPedPre, d, intColPedPre);
                                bgdValPedPre=new BigDecimal(BigInteger.ZERO);
                            }
                        }
                    }
                }
                 
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
                }
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    

    private boolean calcularColumnaTotalesAniMes_1(){
        boolean blnRes=true;
        BigDecimal bgdSumStk1=new BigDecimal(0);
        BigDecimal bgdSumUni1=new BigDecimal(0);
        BigDecimal bgdSumVec1=new BigDecimal(0);
        BigDecimal bgdSumMes1=new BigDecimal(0);
        BigDecimal bgdSumStk2=new BigDecimal(0);
        BigDecimal bgdSumUni2=new BigDecimal(0);
        BigDecimal bgdSumVec2=new BigDecimal(0);
        BigDecimal bgdSumMes2=new BigDecimal(0);
        BigDecimal bgdSumStk3=new BigDecimal(0);
        BigDecimal bgdSumUni3=new BigDecimal(0);
        BigDecimal bgdSumVec3=new BigDecimal(0);
        BigDecimal bgdSumMes3=new BigDecimal(0);
        BigDecimal bgdSumStk4=new BigDecimal(0);
        BigDecimal bgdSumUni4=new BigDecimal(0);
        BigDecimal bgdSumVec4=new BigDecimal(0);
        BigDecimal bgdSumMes4=new BigDecimal(0);
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                    //stock
                    bgdSumStk1=bgdSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //unidades
                    bgdSumUni1=bgdSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //veces
                    bgdSumVec1=bgdSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    j++;
                    //meses
                    bgdSumMes1=bgdSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                bgdSumStk1=new BigDecimal(0);
                bgdSumUni1=new BigDecimal(0);
                bgdSumVec1=new BigDecimal(0);
                bgdSumMes1=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis02.isDataModelChanged()){
                    if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                            //stock
                            bgdSumStk2=bgdSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bgdSumUni2=bgdSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bgdSumVec2=bgdSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bgdSumMes2=bgdSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot2+3));//Meses

                        bgdSumStk2=new BigDecimal(0);
                        bgdSumUni2=new BigDecimal(0);
                        bgdSumVec2=new BigDecimal(0);
                        bgdSumMes2=new BigDecimal(0);
                    }
                }
                
                
                //Totales 3
                if(objTblModHis03.isDataModelChanged()){
                    if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                            //stock
                            bgdSumStk3=bgdSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bgdSumUni3=bgdSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bgdSumVec3=bgdSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bgdSumMes3=bgdSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot3+3));//Meses

                        bgdSumStk3=new BigDecimal(0);
                        bgdSumUni3=new BigDecimal(0);
                        bgdSumVec3=new BigDecimal(0);
                        bgdSumMes3=new BigDecimal(0);
                    }
                }
                
                
                //Totales 4
                if(objTblModHis04.isDataModelChanged()){
                    if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                            //stock
                            bgdSumStk4=bgdSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //unidades
                            bgdSumUni4=bgdSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //veces
                            bgdSumVec4=bgdSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                            j++;
                            //meses
                            bgdSumMes4=bgdSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        }
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4));//Stock
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+1));//Unidades
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+2));//Veces
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot4+3));//Meses

                        bgdSumStk4=new BigDecimal(0);
                        bgdSumUni4=new BigDecimal(0);
                        bgdSumVec4=new BigDecimal(0);
                        bgdSumMes4=new BigDecimal(0);
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularColumnaTotalesAniMes_1(int fila){
        boolean blnRes=true;
        int i=fila;
        BigDecimal bgdSumStk1=new BigDecimal(0);
        BigDecimal bgdSumUni1=new BigDecimal(0);
        BigDecimal bgdSumVec1=new BigDecimal(0);
        BigDecimal bgdSumMes1=new BigDecimal(0);
        BigDecimal bgdSumStk2=new BigDecimal(0);
        BigDecimal bgdSumUni2=new BigDecimal(0);
        BigDecimal bgdSumVec2=new BigDecimal(0);
        BigDecimal bgdSumMes2=new BigDecimal(0);
        BigDecimal bgdSumStk3=new BigDecimal(0);
        BigDecimal bgdSumUni3=new BigDecimal(0);
        BigDecimal bgdSumVec3=new BigDecimal(0);
        BigDecimal bgdSumMes3=new BigDecimal(0);
        BigDecimal bgdSumStk4=new BigDecimal(0);
        BigDecimal bgdSumUni4=new BigDecimal(0);
        BigDecimal bgdSumVec4=new BigDecimal(0);
        BigDecimal bgdSumMes4=new BigDecimal(0);
        
        try{
            //Totales 1
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor; j++){
                //stock
                bgdSumStk1=bgdSumStk1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //unidades
                bgdSumUni1=bgdSumUni1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //veces
                bgdSumVec1=bgdSumVec1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                j++;
                //meses
                bgdSumMes1=bgdSumMes1.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes1, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

            bgdSumStk1=new BigDecimal(0);
            bgdSumUni1=new BigDecimal(0);
            bgdSumVec1=new BigDecimal(0);
            bgdSumMes1=new BigDecimal(0);

            //Totales 2
            if(objTblModHis02.isDataModelChanged()){
                if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2; j++){
                        //stock
                        bgdSumStk2=bgdSumStk2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bgdSumUni2=bgdSumUni2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bgdSumVec2=bgdSumVec2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bgdSumMes2=bgdSumMes2.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes2, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bgdSumStk2=new BigDecimal(0);
                    bgdSumUni2=new BigDecimal(0);
                    bgdSumVec2=new BigDecimal(0);
                    bgdSumMes2=new BigDecimal(0);
                }
            }


            //Totales 3
            if(objTblModHis03.isDataModelChanged()){
                if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3; j++){
                        //stock
                        bgdSumStk3=bgdSumStk3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bgdSumUni3=bgdSumUni3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bgdSumVec3=bgdSumVec3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bgdSumMes3=bgdSumMes3.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes3, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bgdSumStk3=new BigDecimal(0);
                    bgdSumUni3=new BigDecimal(0);
                    bgdSumVec3=new BigDecimal(0);
                    bgdSumMes3=new BigDecimal(0);
                }
            }


            //Totales 4
            if(objTblModHis04.isDataModelChanged()){
                if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4; j++){
                        //stock
                        bgdSumStk4=bgdSumStk4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //unidades
                        bgdSumUni4=bgdSumUni4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //veces
                        bgdSumVec4=bgdSumVec4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        j++;
                        //meses
                        bgdSumMes4=bgdSumMes4.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                    }
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(BigDecimal.ZERO, objParSis.getDecimalesMostrar()), i, (intNumColIniTot));//Stock
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumUni4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+1));//Unidades
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumVec4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+2));//Veces
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdSumMes4, objParSis.getDecimalesMostrar()), i, (intNumColIniTot+3));//Meses

                    bgdSumStk4=new BigDecimal(0);
                    bgdSumUni4=new BigDecimal(0);
                    bgdSumVec4=new BigDecimal(0);
                    bgdSumMes4=new BigDecimal(0);
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularCantidadReponerCalculada(){
        boolean blnRes=true;

        BigDecimal bgdProAnuCal=new BigDecimal(0);
        BigDecimal bgdNumMesRep=new BigDecimal(0);
        BigDecimal bgdStkAct=new BigDecimal(0);
        BigDecimal bgdStkImp=new BigDecimal(0);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPedPreCur=new BigDecimal(0);
        BigDecimal bgdTotPedPreCur=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdProAnuCal=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                bgdNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniRep).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniRep).toString()));
                bgdStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bgdStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()));
                
                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bgdPedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
                }
                
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bgdPedPreCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdTotPedPreCur=bgdTotPedPreCur.add(bgdPedPreCur);
                }               
                
                bgdCanRepCal=(  ((bgdProAnuCal.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdNumMesRep)).subtract(bgdStkAct).subtract(bgdStkImp).subtract(bgdTotPedCur).subtract(bgdTotPedPreCur));
                
                if(bgdCanRepCal.compareTo(new BigDecimal(0))>0)
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCanRepCal, Integer.parseInt("0")), i, (intNumColIniRep+1));//columna de cantidad a reponer calculada

                bgdTotPedCur=new BigDecimal(0);
                bgdTotPedPreCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularCantidadReponerCalculada(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdProAnuCal=new BigDecimal(0);
        BigDecimal bgdNumMesRep=new BigDecimal(0);
        BigDecimal bgdStkAct=new BigDecimal(0);
        BigDecimal bgdStkImp=new BigDecimal(0);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPedPreCur=new BigDecimal(0);
        BigDecimal bgdTotPedPreCur=new BigDecimal(0);
        try{
            bgdProAnuCal=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(fila, intNumColIniProAnu).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniProAnu).toString()));
            bgdNumMesRep=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniRep)==null?"0":(objTblMod.getValueAt(fila, intNumColIniRep).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniRep).toString()));
            bgdStkAct=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()));
            bgdStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()));

            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bgdPedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
            }
            
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bgdPedPreCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).toString().equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bgdTotPedPreCur=bgdTotPedPreCur.add(bgdPedPreCur);
            }
            
            bgdCanRepCal=(  ((bgdProAnuCal.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdNumMesRep)).subtract(bgdStkAct).subtract(bgdStkImp).subtract(bgdTotPedCur).subtract(bgdTotPedPreCur) );
            if(bgdCanRepCal.compareTo(new BigDecimal(0))>0)
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCanRepCal, Integer.parseInt("0")), fila, (intNumColIniRep+1));//columna de cantidad a reponer calculada
            else
                objTblMod.setValueAt("0", fila, (intNumColIniRep+1));//columna de cantidad a reponer calculada

            bgdTotPedCur=new BigDecimal(0);
            bgdTotPedPreCur=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularColumnaTotalPedido(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        BigDecimal bgdPesItm=BigDecimal.ZERO;
        try{
            bgdPesItm=new BigDecimal(objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG).toString()));
            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bgdPedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
                else
                    bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdTotPedCur, objParSis.getDecimalesMostrar()), fila, (intNumColFinPedEmb-1));//columna de cantidad a reponer calculada

            bgdTotPedCur=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean calcularColumnaTotalPedido(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        try{
            for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bgdPedCur=new BigDecimal(objTblMod.getValueAt(k, j)==null?"0":(objTblMod.getValueAt(k, j).equals("")?"0":objTblMod.getValueAt(k, j).toString()));
                    bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdTotPedCur, objParSis.getDecimalesMostrar()), k, (intNumColFinPedEmb-1));//columna de cantidad a reponer calculada
                bgdTotPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularColumnaTotalPedidoPrevio(){
        boolean blnRes=true;
        BigDecimal bgdPedPre=new BigDecimal(0);
        BigDecimal bgdTotPedPre=new BigDecimal(0);
        try{
            for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bgdPedPre=new BigDecimal(objTblMod.getValueAt(k, j)==null?"0":(objTblMod.getValueAt(k, j).equals("")?"0":objTblMod.getValueAt(k, j).toString()));
                    bgdTotPedPre=bgdTotPedPre.add(bgdPedPre);
                }
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdTotPedPre, objParSis.getDecimalesMostrar()), k, (intNumColFinPedPre-1));//columna de cantidad a reponer calculada
                bgdTotPedPre=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean calcularColumnaTotalPedidoPrevio(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdPedPre=new BigDecimal(0);
        BigDecimal bgdTotPedPre=new BigDecimal(0);
        try{
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bgdPedPre=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bgdTotPedPre=bgdTotPedPre.add(bgdPedPre);
            }
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdTotPedPre, objParSis.getDecimalesMostrar()), fila, (intNumColFinPedPre-1));//columna de cantidad a reponer calculada
            bgdTotPedPre=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularCantidadReponerManual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdProAnuMan=new BigDecimal(0);
        BigDecimal bgdNumMesRep=new BigDecimal(0);
        BigDecimal bgdStkAct=new BigDecimal(0);
        BigDecimal bgdStkImp=new BigDecimal(0);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPedPreCur=new BigDecimal(0);
        BigDecimal bgdTotPedPreCur=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                bgdNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniRep).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniRep).toString()));
                bgdStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bgdStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()));

                for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                    bgdPedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
                }
                
                for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                    bgdPedPreCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdTotPedPreCur=bgdTotPedPreCur.add(bgdPedPreCur);
                }
                
                
                bgdCanRepCal=(  ((bgdProAnuMan.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdNumMesRep)).subtract(bgdStkAct).subtract(bgdStkImp).subtract(bgdTotPedCur).subtract(bgdTotPedPreCur) );

                if(bgdCanRepCal.compareTo(new BigDecimal(0))>0)
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCanRepCal, Integer.parseInt("0")), i, (intNumColIniRep+2));//columna de cantidad a reponer calculada

                bgdTotPedCur=new BigDecimal(0);
                bgdTotPedPreCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPesoCantidadImportar(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdCanImp=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesCanImp=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                
//                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
//                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
//                bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), i));
//                
//                objTblMod.setValueAt("" + bgdPesCanImp, i, (intNumColIniImp_Ped1+1));
//                bgdPesCanImp=new BigDecimal(0);
//                bgdCanImp=new BigDecimal(0);
//                bgdPesItm=new BigDecimal(0);                
//                
//                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
//                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
//                bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), i));         
//                
//                objTblMod.setValueAt("" + bgdPesCanImp, i, (intNumColIniImp_Ped2+1));
//                bgdPesCanImp=new BigDecimal(0);
//                bgdCanImp=new BigDecimal(0);
//                bgdPesItm=new BigDecimal(0);
                
                if(chkCalRepPes.isSelected()){
                    //para calcular columa Can.Ped.1
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bgdPesCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniImp_Ped1+1)).toString()));
                    if(bgdPesCanImp.compareTo(new BigDecimal("0"))>0)
                        bgdCanImp=bgdCanImp.add(objUti.redondearBigDecimal((bgdPesCanImp.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                    
                    objTblMod.setValueAt("" + bgdCanImp, i, (intNumColIniImp_Ped1));

                    bgdCanImp=new BigDecimal(0);            
                    bgdPesCanImp=new BigDecimal(0);

                    //para calcular columa Can.Ped.2
                    bgdPesCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniImp_Ped2+1)).toString()));
                    if(bgdPesCanImp.compareTo(new BigDecimal("0"))>0)
                        bgdCanImp=bgdCanImp.add(objUti.redondearBigDecimal((bgdPesCanImp.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));

                    objTblMod.setValueAt("" + bgdCanImp, i, (intNumColIniImp_Ped2));

                    bgdCanImp=new BigDecimal(0);
                    bgdPesCanImp=new BigDecimal(0);

                    bgdPesItm=new BigDecimal(0);
                }
                else{
                    //para calcular columa peso(kg)1
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                    bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), new Integer("0")));

                    objTblMod.setValueAt("" + bgdPesCanImp, i, (intNumColIniImp_Ped1+1));

                    bgdCanImp=new BigDecimal(0);            
                    bgdPesCanImp=new BigDecimal(0);

                    //para calcular columa peso(kg)2
                    bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                    bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), new Integer("0")));
                    objTblMod.setValueAt("" + bgdPesCanImp, i, (intNumColIniImp_Ped2+1));

                    bgdCanImp=new BigDecimal(0);
                    bgdPesCanImp=new BigDecimal(0);

                    bgdPesItm=new BigDecimal(0);
                }
                
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPesoCantidadImportar(int fila){
        boolean blnRes=true;
        BigDecimal bgdCanImp=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesCanImp=new BigDecimal(0);
        try{
            if(chkCalRepPes.isSelected()){
                //para calcular columa Can.Ped.1
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));
                bgdPesCanImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniImp_Ped1+1)).toString()));

                if(bgdPesCanImp.compareTo(new BigDecimal("0"))>0)
                    bgdCanImp=bgdCanImp.add(objUti.redondearBigDecimal((bgdPesCanImp.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                
                objTblMod.setValueAt("" + bgdCanImp, fila, (intNumColIniImp_Ped1));
                
                bgdCanImp=new BigDecimal(0);            
                bgdPesCanImp=new BigDecimal(0);
                
                //para calcular columa Can.Ped.2
                bgdPesCanImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniImp_Ped2+1)).toString()));
                if(bgdPesCanImp.compareTo(new BigDecimal("0"))>0)
                    bgdCanImp=bgdCanImp.add(objUti.redondearBigDecimal((bgdPesCanImp.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)), new Integer("0")));
                
                objTblMod.setValueAt("" + bgdCanImp, fila, (intNumColIniImp_Ped2));

                bgdCanImp=new BigDecimal(0);
                bgdPesCanImp=new BigDecimal(0);

                bgdPesItm=new BigDecimal(0);
            }
            else{
                //para calcular columa peso(kg)1
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(fila, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniImp_Ped1).toString()));
                bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), new Integer("0")));

                objTblMod.setValueAt("" + bgdPesCanImp, fila, (intNumColIniImp_Ped1+1));

                bgdCanImp=new BigDecimal(0);            
                bgdPesCanImp=new BigDecimal(0);

                //para calcular columa peso(kg)2
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(fila, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniImp_Ped2).toString()));
                bgdPesCanImp=bgdPesCanImp.add(objUti.redondearBigDecimal((bgdCanImp.multiply(bgdPesItm)), new Integer("0")));
                objTblMod.setValueAt("" + bgdPesCanImp, fila, (intNumColIniImp_Ped2+1));

                bgdCanImp=new BigDecimal(0);
                bgdPesCanImp=new BigDecimal(0);

                bgdPesItm=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularCantidadReponerManual(int fila){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdProAnuMan=new BigDecimal(0);
        BigDecimal bgdNumMesRep=new BigDecimal(0);
        BigDecimal bgdStkAct=new BigDecimal(0);
        BigDecimal bgdStkImp=new BigDecimal(0);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdTotPedCur=new BigDecimal(0);
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPedPreCur=new BigDecimal(0);
        BigDecimal bgdTotPedPreCur=new BigDecimal(0);
        try{
            bgdProAnuMan=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinProAnu-1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinProAnu-1)).toString()));
            bgdNumMesRep=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniRep)==null?"0":(objTblMod.getValueAt(fila, intNumColIniRep).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniRep).toString()));
            bgdStkAct=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()));
            bgdStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()));

            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb-1); j++){
                bgdPedCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bgdTotPedCur=bgdTotPedCur.add(bgdPedCur);
            }
            
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre-1); j++){
                bgdPedPreCur=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).toString().equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                bgdTotPedPreCur=bgdTotPedPreCur.add(bgdPedPreCur);
            }
                        
            bgdCanRepCal=(  ((bgdProAnuMan.divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdNumMesRep)).subtract(bgdStkAct).subtract(bgdStkImp).subtract(bgdTotPedCur).subtract(bgdTotPedPreCur) );

            if(bgdCanRepCal.compareTo(new BigDecimal(0))>0)
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCanRepCal, Integer.parseInt("0")), fila, (intNumColIniRep+2));//columna de cantidad a reponer calculada
            else
                objTblMod.setValueAt("0", fila, (intNumColIniRep+2));//columna de cantidad a reponer calculada

            bgdTotPedCur=new BigDecimal(0);
            bgdTotPedPreCur=new BigDecimal(0);

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPromedioAnual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdTotUni=new BigDecimal(0);
        BigDecimal bgdNumAniSel=new BigDecimal(0);
        BigDecimal bgdProAnuCal=new BigDecimal(0);
        try{
            bgdNumAniSel=new BigDecimal("" + intNumColAddFecCor);
            
            if(bgdNumAniSel.compareTo(BigDecimal.ZERO)<=0)
                bgdNumAniSel=new BigDecimal("1"); //Cuando no existen anios seleccionados, se determina uno para que no envie error de división para zero            

            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                //Totales 1
                bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP); //promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu); 
                bgdProAnuCal=new BigDecimal(0);
                bgdTotUni=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis02.isDataModelChanged()){
                    if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                        bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 3
                if(objTblModHis03.isDataModelChanged()){
                    if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                        bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 4
                if(objTblModHis04.isDataModelChanged()){
                    if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                        bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                        
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0);                        
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPromedioAnual(int fila){
        boolean blnRes=true;
        int i=fila;
        int p=0;
        BigDecimal bgdTotUni=new BigDecimal(0);
        BigDecimal bgdNumAniSel=new BigDecimal(0);
        BigDecimal bgdProAnuCal=new BigDecimal(0);
        try{
            bgdNumAniSel=new BigDecimal("" + intNumColAddFecCor);
            
            if(bgdNumAniSel.compareTo(BigDecimal.ZERO)<=0) 
                bgdNumAniSel=new BigDecimal("1"); //Cuando no existen anios seleccionados, se determina uno para que no envie error de división para zero            

            //Totales 1
            //contiene el valor de la columna de UNIDADES en TOTALES
            bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
            bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
            bgdProAnuCal=new BigDecimal(0);
            bgdTotUni=new BigDecimal(0);
            
            //Totales 2
            if(objTblModHis02.isDataModelChanged()){
                if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                    bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 3
            if(objTblModHis03.isDataModelChanged()){
                if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                    bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 4
            if(objTblModHis04.isDataModelChanged()){
                if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                    bgdProAnuCal=bgdTotUni.divide(bgdNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    //Estos metodos para promedio mensual y anual crearlo para cada columna agregada, es decir para los 3 historicos de ventas que se han agregado 
    private boolean calcularPromedioMensual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bgdTotUni=new BigDecimal(0);
        BigDecimal bgdNumAniSel1=new BigDecimal(0);
        BigDecimal bgdNumAniSel2=new BigDecimal(0);
        BigDecimal bgdNumAniSel3=new BigDecimal(0);
        BigDecimal bgdNumAniSel4=new BigDecimal(0);
        BigDecimal bgdProAnuCal=new BigDecimal(0);
        try{
            bgdNumAniSel1=new BigDecimal("" + intNumColAddFecCor);
            bgdNumAniSel2=new BigDecimal("" + intNumColAddFecCor2);
            bgdNumAniSel3=new BigDecimal("" + intNumColAddFecCor3);
            bgdNumAniSel4=new BigDecimal("" + intNumColAddFecCor4);
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //Totales 1
                //contiene el valor de la columna de UNIDADES en TOTALES
                bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot+1)).toString()));
                bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel1, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
                bgdProAnuCal=new BigDecimal(0);
                bgdTotUni=new BigDecimal(0);
                
                //Totales 2
                if(objTblModHis02.isDataModelChanged()){
                    if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot2+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot2+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot2+1)).toString()));
                        bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel2, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+1));
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0);
                    }
                }
                
                //Totales 3
                if(objTblModHis03.isDataModelChanged()){
                    if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot3+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot3+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot3+1)).toString()));
                        bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel3, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0); 
                    }
                }
                
                //Totales 4
                if(objTblModHis04.isDataModelChanged()){
                    if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                        //contiene el valor de la columna de UNIDADES en TOTALES
                        bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot4+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot4+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniTot4+1)).toString()));
                        bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel4, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                        
                        bgdProAnuCal=new BigDecimal(0);
                        bgdTotUni=new BigDecimal(0);
                    }
                }
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean calcularPromedioMensual(int fila){
        boolean blnRes=true;
        int i=fila;
        int p=0;
        BigDecimal bgdTotUni=new BigDecimal(0);
        BigDecimal bgdNumAniSel1=new BigDecimal(0);
        BigDecimal bgdNumAniSel2=new BigDecimal(0);
        BigDecimal bgdNumAniSel3=new BigDecimal(0);
        BigDecimal bgdNumAniSel4=new BigDecimal(0);
        BigDecimal bgdProAnuCal=new BigDecimal(0);
        try{
            //Totales 1
            bgdNumAniSel1=new BigDecimal("" + intNumColAddFecCor);
            bgdNumAniSel2=new BigDecimal("" + intNumColAddFecCor2);
            bgdNumAniSel3=new BigDecimal("" + intNumColAddFecCor3);
            bgdNumAniSel4=new BigDecimal("" + intNumColAddFecCor4);
            //contiene el valor de la columna de UNIDADES en TOTALES
            bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot).toString()));
            bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel1, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, intNumColIniProAnu);
            bgdProAnuCal=new BigDecimal(0);
            bgdTotUni=new BigDecimal(0);
            
            //Totales 2
            if(objTblModHis02.isDataModelChanged()){
                if(objTblModHis02.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot2)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot2).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot2).toString()));
                    bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel2, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+2));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 3
            if(objTblModHis03.isDataModelChanged()){
                if(objTblModHis03.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot3)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot3).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot3).toString()));
                    bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel3, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+3));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
                }
            }
            
            //Totales 4
            if(objTblModHis04.isDataModelChanged()){
                if(objTblModHis04.isCheckedAnyRow(INT_TBL_DAT_HIS_CHK)){
                    //contiene el valor de la columna de UNIDADES en TOTALES
                    bgdTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot4)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot4).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot4).toString()));
                    bgdProAnuCal=(bgdTotUni.divide(bgdNumAniSel4, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("12"));//promedio anual calculado de ventas calculado por item
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdProAnuCal, Integer.parseInt("0")), i, (intNumColIniProAnu+4));
                    bgdProAnuCal=new BigDecimal(0);
                    bgdTotUni=new BigDecimal(0);
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
     * Función que permite calcular totales
    */
    private boolean calcularTotalCantidadReponerCalculada_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniRep + 1)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCal=bgdSum_PesItm_CanRepCal.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
                
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColIniRep+1));//columna de cantidad a reponer calculada
            bgdSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalCantidadReponerManual_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanRepMan=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+2)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepMan;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepMan.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCal=bgdSum_PesItm_CanRepCal.add(bgdPesItm_CanRepCal);
                
                bgdCanRepMan=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColIniRep+2));//columna de cantidad a reponer calculada
            bgdSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPromedioAnualCalculada_peso_1(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal1=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal1=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);//peso
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);//peso        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal1=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal1;
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal1.multiply(bgdPesItm);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                
                bgdSum_PesItm_CanRepCal1=bgdSum_PesItm_CanRepCal1.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal1=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal1, objParSis.getDecimalesMostrar()), 0, intNumColIniProAnu);//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCal1=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */    
    private boolean calcularTotalPromedioAnualCalculada_peso_2(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal2=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal2=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);//peso
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal2=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal2;
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal2.multiply(bgdPesItm);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    
                bgdSum_PesItm_CanRepCal2=bgdSum_PesItm_CanRepCal2.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal2=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal2, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+1));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCal2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPromedioAnualCalculada_peso_3(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal3=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal3=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);//peso
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal3=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal3;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal3.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCal3=bgdSum_PesItm_CanRepCal3.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal3=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal3, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+2));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCal3=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPromedioAnualCalculada_peso_4(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal4=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal4=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);//peso
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal4=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+3))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal4;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal4.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCal4=bgdSum_PesItm_CanRepCal4.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal4=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal4, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+3));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCal4=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPromedioAnualManual_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCal=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCal=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCal=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCal;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepCal.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCal=bgdSum_PesItm_CanRepCal.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCal=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
                
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCal, objParSis.getDecimalesMostrar()), 0, (intNumColFinProAnu-1));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCal=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalStockActual_Importacion_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCalStkAct=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanRepCalStkAct=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCalStkAct=new BigDecimal(0);
        BigDecimal bgdCanRepCalImp=new BigDecimal(0);
        BigDecimal bgdPesItm_CanRepCalImp=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCalImp=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCalStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCalStkAct=bgdCanRepCalStkAct;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCalStkAct=bgdCanRepCalStkAct.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCalStkAct=bgdSum_PesItm_CanRepCalStkAct.add(bgdPesItm_CanRepCalStkAct);
                                
                bgdCanRepCalImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk+1)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk+1).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniStk + 1).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCalImp=bgdCanRepCalImp;
                else
                    bgdPesItm_CanRepCalImp=bgdCanRepCalImp.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCalImp=bgdSum_PesItm_CanRepCalImp.add(bgdPesItm_CanRepCalImp);

                bgdCanRepCalStkAct=new BigDecimal(0);
                bgdPesItm_CanRepCalStkAct=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdCanRepCalImp=new BigDecimal(0);
                bgdPesItm_CanRepCalImp=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCalStkAct, objParSis.getDecimalesMostrar()), 0, intNumColIniStk);//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCalStkAct=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCalImp, objParSis.getDecimalesMostrar()), 0, (intNumColIniStk+1));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCalImp=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPedido_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanPed=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanPed=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed=new BigDecimal(0);
        try{           
            for(int j=intNumColIniPedEmb; j<(intNumColFinPedEmb); j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                    
                    bgdSum_PesItm_CanPed=bgdSum_PesItm_CanPed.add(bgdPesItm_CanPed);
                    
                    bgdCanPed=new BigDecimal(0);
                    bgdPesItm=new BigDecimal(0);
                    bgdPesItm_CanPed=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bgdSum_PesItm_CanPed=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPedidoPrevio_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanPed=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanPed=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed=new BigDecimal(0);
        try{           
            for(int j=intNumColIniPedPre; j<(intNumColFinPedPre); j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                    
                    bgdSum_PesItm_CanPed=bgdSum_PesItm_CanPed.add(bgdPesItm_CanPed);
                    
                    bgdCanPed=new BigDecimal(0);
                    bgdPesItm=new BigDecimal(0);
                    bgdPesItm_CanPed=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bgdSum_PesItm_CanPed=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalCantidadImportar_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanPed=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanPed=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed1=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                //Pedido 1
                bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanPed1=bgdSum_PesItm_CanPed1.add(bgdPesItm_CanPed);
                bgdCanPed=new BigDecimal(0);
                bgdPesItm_CanPed=new BigDecimal(0);
                //Pedido 2
                bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanPed2=bgdSum_PesItm_CanPed2.add(bgdPesItm_CanPed);
                //se setea los campos de calculo
                bgdCanPed=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanPed=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed1, objParSis.getDecimalesMostrar()), 0, (intNumColIniImp_Ped1+1));//columna de promedio anual calculado del Pedido 1
            bgdSum_PesItm_CanPed1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed2, objParSis.getDecimalesMostrar()), 0, (intNumColIniImp_Ped2+1));//columna de promedio anual calculado del Pedido 2
            bgdSum_PesItm_CanPed2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
     
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesCantidadReposicion_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(0);
        BigDecimal bgdCanImp=new BigDecimal(0);
        BigDecimal bgdSum_CanCos1=new BigDecimal(0);
        BigDecimal bgdSum_CanCos2=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                
                //Calculada
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+1)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSum_CanCos1=(bgdSum_CanCos1.add(   ((bgdCanImp.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp))     ));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else{
                    bgdSum_CanCos1=bgdSum_CanCos1.add(bgdCanImp.multiply(bgdCosUniGrp));
                }
                
                bgdCanImp=new BigDecimal(0);
                //Manual
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniRep+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniRep+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniRep+2)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSum_CanCos2=(bgdSum_CanCos2.add(     ((bgdCanImp.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)     )));

                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSum_CanCos2=bgdSum_CanCos2.add(bgdCanImp.multiply(bgdCosUniGrp));
                
                //se setea los campos de calculo
                bgdCanImp=new BigDecimal(0);
                bgdCosUniGrp=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_CanCos1, objParSis.getDecimalesMostrar()), 1, (intNumColIniRep+1));//columna de promedio anual calculado del Pedido 1
            bgdSum_CanCos1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_CanCos2, objParSis.getDecimalesMostrar()), 1, (intNumColIniRep+2));//columna de promedio anual calculado del Pedido 2
            bgdSum_CanCos2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesCantidadImportar_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(0);
        BigDecimal bgdCanImp=new BigDecimal(0);
        BigDecimal bgdSum_CanCos1=new BigDecimal(0);
        BigDecimal bgdSum_CanCos2=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                //Pedido 1
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped1)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped1).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSum_CanCos1=bgdSum_CanCos1.add(((bgdCanImp.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSum_CanCos1=bgdSum_CanCos1.add(bgdCanImp.multiply(bgdCosUniGrp));
                
                bgdCanImp=new BigDecimal(0);
                //Pedido 2
                bgdCanImp=new BigDecimal(objTblMod.getValueAt(i, intNumColIniImp_Ped2)==null?"0":(objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniImp_Ped2).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSum_CanCos2=bgdSum_CanCos2.add(((bgdCanImp.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSum_CanCos2=bgdSum_CanCos2.add(bgdCanImp.multiply(bgdCosUniGrp));
                
                //se setea los campos de calculo
                bgdCanImp=new BigDecimal(0);
                bgdCosUniGrp=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_CanCos1, objParSis.getDecimalesMostrar()), 1, (intNumColIniImp_Ped1+1));//columna de promedio anual calculado del Pedido 1
            bgdSum_CanCos1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_CanCos2, objParSis.getDecimalesMostrar()), 1, (intNumColIniImp_Ped2+1));//columna de promedio anual calculado del Pedido 2
            bgdSum_CanCos2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean ocultaColumnasPedidoSinValor(){
        boolean blnRes=true;
        BigDecimal bgdValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try{
            for(int j=(intNumColFinPedEmb-2); j>=intNumColIniPedEmb; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    bgdValPed=bgdValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }                    
                if( (bgdValPed.compareTo(new BigDecimal("0"))>0) ||  (bgdValPed.compareTo(new BigDecimal("0"))<0) ){
                }
                else{//se elimina la columna y se coloca como estado 'E' en el arraylist
                    for(int k=0;k<arlDatPedEmb.size(); k++){

                        intNumCol=objUti.getIntValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL);
                        
                        if(! strEstArl.equals("E")){
                            if(j==intNumCol){
                                objUti.setStringValueAt(arlDatPedEmb, k, INT_ARL_PED_EMB_EST_COL, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat);
                                objTblModTot.addSystemHiddenColumn(intNumCol, tblTotDat);//Totales
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bgdValPed=new BigDecimal(BigInteger.ZERO);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }        
    
    private boolean ocultaColumnasPedidoPrevioSinValor(){
        boolean blnRes=true;
        BigDecimal bgdValPed=new BigDecimal(BigInteger.ZERO);
        int intNumCol=-1;
        String strEstArl="";
        int intNumColEli=0;
        try{
            for(int j=(intNumColFinPedPre-3); j>=intNumColIniPedPre; j--){//es menos 1 porque la ultima columna es en realidad de TOTAL y otra mas para q se vea la columna libre
                for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                    bgdValPed=bgdValPed.add(new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString())));
                }
                
                if( (bgdValPed.compareTo(new BigDecimal("0"))>0) ||  (bgdValPed.compareTo(new BigDecimal("0"))<0) ){
                }
                else{//se elimina la columna y se coloca como estado 'E' en el arraylist
                    for(int k=0;k<arlDatPedPre.size(); k++){
                        intNumCol=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_COL);
                        strEstArl=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);//se marca con 'E' si es q se oculta en el modelo
                        
                        if(! strEstArl.equals("E")){
                            if(j==intNumCol){
                                objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD, "E");
                                objTblMod.addSystemHiddenColumn(intNumCol, tblDat);
                                objTblModTot.addSystemHiddenColumn(intNumCol, tblTotDat);
                                intNumColEli++;
                                break;
                            }
                        }
                    }
                }
                bgdValPed=new BigDecimal(BigInteger.ZERO);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    private boolean cargarDetallePedidosConfigurados(){
        boolean blnRes=true;
        Statement stmCanPedEmb;
        ResultSet rstCanPedEmb;
        try{
            if(con!=null){
                stmCanPedEmb=con.createStatement();
                //crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+="SELECT g1.co_emp, g1.co_loc, g1.co_tipDoc, g1.co_doc, g1.co_itm, g1.nd_can, a1.tx_codAlt";
                strSQL+="  FROM tbm_inv AS a1 ";
                strSQL+="  INNER JOIN(";
                strSQL+="  SELECT f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm, SUM(f2.nd_can) AS nd_can";
                strSQL+="     FROM (tbm_cabPedEmbImp AS f1 ";
                strSQL+="  		LEFT OUTER JOIN tbm_cabMovInv AS a2";
                strSQL+="  		ON f1.co_emp=a2.co_emprelpedembimp AND f1.co_loc=a2.co_locrelpedembimp ";
                strSQL+="  		AND f1.co_tipDoc=a2.co_tipdocrelpedembimp AND f1.co_doc=a2.co_docrelpedembimp AND a2.st_reg NOT IN('E','I')";
                strSQL+="  	)";
                strSQL+="         INNER JOIN tbm_detPedEmbImp AS f2";
                strSQL+="         ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc ";
                strSQL+="         WHERE f1.st_reg NOT IN('E','I') AND a2.co_emp IS NULL";
                strSQL+="         GROUP BY f2.co_emp, f2.co_loc, f2.co_tipDoc, f2.co_doc, f2.co_itm";
                strSQL+="       ) AS g1";
                strSQL+="       ON a1.co_emp=g1.co_emp AND a1.co_itm=g1.co_itm";
                strSQL+=" UNION ALL";
                strSQL+=" SELECT g2.co_emp, g2.co_loc, g2.co_tipDoc, g2.co_doc, g2.co_itm, g2.nd_canNotPedFal, a1.tx_codAlt";
                strSQL+=" FROM tbm_inv AS a1 ";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_itm";
                strSQL+=" 		/*, c1.nd_canNotPed, c1.nd_canPedEmb*/, c1.nd_canNotPedFal";
                strSQL+=" 	FROM(";
                strSQL+=" 		SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_itm";
                strSQL+="		, b1.nd_canNotPed, CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END AS nd_canPedEmb";
                strSQL+="		, (b1.nd_canNotPed - ";
                strSQL+="			CASE WHEN SUM(b1.nd_canPedEmb) IS NULL THEN 0 ELSE SUM(b1.nd_canPedEmb) END";
                strSQL+="		  ) AS nd_canNotPedFal";
                strSQL+="		FROM(	";
                strSQL+="			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar AS tx_mesNotPed, a1.tx_numDoc2";
                strSQL+="			, a2.co_emp AS co_empPedEmb, a2.co_loc AS co_locPedEmb, a2.co_tipDoc AS co_tipDocPedEmb, a2.co_doc AS co_docPedEmb, a2.co_reg AS co_regPedEmb";
                strSQL+="			, CAST(a4.nd_can AS NUMERIC(18,6) ) AS nd_canNotPed, CAST(a2.nd_can AS NUMERIC(18,6) ) AS nd_canPedEmb";
                strSQL+=" 			, a4.co_itm";
                strSQL+=" 			FROM (tbm_cabNotPedImp AS a1 INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesemb=a5.co_mesemb)";
                strSQL+="			INNER JOIN tbm_detNotPedImp AS a4";
                strSQL+="			ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="			LEFT OUTER JOIN ";
                strSQL+="				(tbm_detPedEmbImp AS a2 INNER JOIN tbm_cabPedEmbImp AS a3";
                strSQL+="				 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+="				 AND a3.st_reg NOT IN('I','E')";
                strSQL+="				)";
                strSQL+=" 			ON a4.co_emp=a2.co_emp AND a4.co_loc=a2.co_locRel AND a4.co_tipDoc=a2.co_tipdocRel ";
                strSQL+=" 			AND a4.co_doc=a2.co_docRel AND a4.co_reg=a2.co_regRel";
                strSQL+=" 			WHERE a1.st_reg NOT IN('E','I')	AND a1.st_cie='N'	";
                strSQL+="			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a5.tx_deslar, a1.tx_numDoc2";
                strSQL+=" 			, a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a4.nd_can, a2.nd_can, a4.co_itm";
                strSQL+=" 			ORDER BY a1.co_doc";
                strSQL+=" 		) AS b1";
                strSQL+=" 		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc, b1.tx_mesNotPed, b1.tx_numDoc2, b1.nd_canNotPed, b1.co_itm";
                strSQL+=" 	) AS c1";
                strSQL+=" 	WHERE c1.nd_canNotPedFal > 0";
                strSQL+=") AS g2";
                strSQL+=" ON a1.co_emp=g2.co_emp AND a1.co_itm=g2.co_itm";
                strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                rstCanPedEmb=stmCanPedEmb.executeQuery(strSQL);
                arlDatDetPedEmb.clear();
                while(rstCanPedEmb.next()){
                    arlRegDetPedEmb=new ArrayList();
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_EMP, rstCanPedEmb.getString("co_emp"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_LOC, rstCanPedEmb.getString("co_loc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_TIP_DOC, rstCanPedEmb.getString("co_tipDoc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_DOC, rstCanPedEmb.getString("co_doc"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_ITM, rstCanPedEmb.getString("co_itm"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_CAN_PED_EMB, rstCanPedEmb.getString("nd_can"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_COD_ALT_ITM, rstCanPedEmb.getString("tx_codAlt"));
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_EST, "");
                    arlRegDetPedEmb.add(INT_ARL_DET_PED_EMB_NUM_COL, "0");                    
                    arlDatDetPedEmb.add(arlRegDetPedEmb);                    
                }
                stmCanPedEmb.close();
                stmCanPedEmb=null;
                rstCanPedEmb.close();
                rstCanPedEmb=null;
                
                //se cargan los datos de valores del pedido embarcado
                int intCodEmpNomPedEmb=-1;
                int intCodLocNomPedEmb=-1;
                int intCodTipDocNomPedEmb=-1;
                int intCodDocNomPedEmb=-1;
                int intNumColNomPedEmb=-1;                
                int intCodEmpCanPedEmb=-1;
                int intCodLocCanPedEmb=-1;
                int intCodTipDocCanPedEmb=-1;
                int intCodDocCanPedEmb=-1;                
                String strEstPedEmb="";
                
                for(int d=0; d<(arlDatPedEmb.size()); d++){
                    intCodEmpNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_EMP);
                    intCodLocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_LOC);
                    intCodTipDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_TIP_DOC);
                    intCodDocNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_COD_DOC);
                    intNumColNomPedEmb=objUti.getIntValueAt(arlDatPedEmb, d, INT_ARL_PED_EMB_NUM_COL);
                    
                    for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                        intCodEmpCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_EMP);
                        intCodLocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_LOC);
                        intCodTipDocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_TIP_DOC);
                        intCodDocCanPedEmb=objUti.getIntValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_COD_DOC);
                        
                        strEstPedEmb=objUti.getStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST);
                        if(strEstPedEmb.equals("")){
                            if( (intCodEmpNomPedEmb==intCodEmpCanPedEmb) && (intCodLocNomPedEmb==intCodLocCanPedEmb) && (intCodTipDocNomPedEmb==intCodTipDocCanPedEmb) && (intCodDocNomPedEmb==intCodDocCanPedEmb) ){
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "S");
                                objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_NUM_COL, ""+intNumColNomPedEmb);
                            }
                        }
                    }                    
                }                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedEmb.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedEmb, e, INT_ARL_DET_PED_EMB_EST, "");
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
     * Función que valida que todos los datos sean correctos, antes de realizar alguna acción.
     * @return 
     */
    private boolean isCamVal(){
        int intArlColSel;
        String strColModLib="";

        //Valida que existan NPP antes de guardar
        if(arlDatPedPre.size()>0){ /* Se añade ese nombre al arreglo q contiene la informacion de los Pedidos Previos*/
            for(int i=0; i<arlDatPedPre.size();i++){
                intArlColSel=objUti.getIntValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_COL);
                strColModLib=objUti.getStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_COL_MOD);
                if(intArlColSel==(intNumColFinPedPre-2)){
                    if(strColModLib.equals("I")){
                        if(strNomPedPreIngLSC.equals("")){  
                            strNomPedPreIngLSC="";
                            /* Valida si se debe seleccionar o digitar el Número del Pedido.*/
                            if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_COM_LOC) {  //Compras Locales
                                //Se digita el número del pedido, debido a que no se crearán NPP de Compras Locales en el Presupuesto.
                                strNomPedPreIngLSC= (javax.swing.JOptionPane.showInputDialog(javax.swing.JOptionPane.getFrameForComponent(tblDat), "Ingrese el nombre del Pedido Previo:", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.QUESTION_MESSAGE)).toString();
                                if(strNomPedPreIngLSC != null) { //Aceptar
                                    objUti.setStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_PED_PRE, strNomPedPreIngLSC);
                                    BLN_EXI_NPP=true;    
                                }
                                else{ //Cancelar
                                    strNomPedPreIngLSC="";
                                    return false;
                                }
                            }
                            else{ //Importaciones
                                //Se selecciona el número del pedido, debido a que las NPP de Importaciones, se crearán en el Presupuesto.
                                Compras.ZafCom68.ZafCom68_03 objPed=new Compras.ZafCom68.ZafCom68_03(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
                                objPed.setVisible(true);
                                if(objPed.isPedSel()) { //Aceptar
                                    strNomPedPreIngLSC=objPed.getNumPedSel(); 
                                    objUti.setStringValueAt(arlDatPedPre, i, INT_ARL_CAB_PED_PRE_NUM_PED_PRE, strNomPedPreIngLSC);
                                    BLN_EXI_NPP=true;                                
                                }
                                else{ //Cancelar
                                    strNomPedPreIngLSC=""; 
                                    return false;
                                }
                                objPed=null;                            
                            }
                        }
                        break;
                    }
                }
            }        
        }  
        else{
            //Cuando no existe NPP
            return false;
        }
        return true;
    } 
    
    private boolean guardarDatos(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)   
            {
                con.setAutoCommit(false);
                if(elimina_tbm_filCfgImp()){
                    if(inserta_tbm_filCfgImp()){
                        if(elimina_tbm_filAniSelCfgImp()){
                            if(inserta_tbm_filAniSelCfgImp()){
                                if(elimina_tbm_filMesSelCfgImp()){
                                    if(inserta_tbm_filMesSelCfgImp()){
                                        if(update_tbmInv()){
                                            if(update_tbmCabNotPedPreImp()){
                                                if(update_tbmDetNotPedPreImp()){
                                                    if(insertar_tbmCabSegMovInv_NotPedPreImp())
                                                    { 
                                                        con.commit();
//------------------------------------------------------------------------------
                                                        //Eddye: Agregué código porque no estaba inicializando luego de guardar.
                                                        objTblMod.initRowsState();
                                                        objTblMod.setDataModelChanged(false);
                                                        objTblModFix.initRowsState();
                                                        objTblModFix.setDataModelChanged(false);
//------------------------------------------------------------------------------
                                                        blnRes=true;
                                                    }
                                                    else
                                                    {
                                                        con.rollback();
                                                    }
                                                }
                                                else
                                                {
                                                    con.rollback();
                                                }
                                            }
                                            else
                                            {
                                                con.rollback();
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
    
   private boolean elimina_tbm_filCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+="DELETE FROM tbm_filCfgImp";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

   private boolean inserta_tbm_filCfgImp(){
        boolean blnRes=true;
        String strTipHis="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strTipHis=(optHisAnu.isSelected())?"'A'":"'M'";
                strSQL ="";
                strSQL+=" INSERT INTO tbm_filCfgImp(tx_tipHis, ne_numMesRep)";
                strSQL+=" VALUES(" + strTipHis + ", " + jspNumMesRep.getValue() + ");";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
   
   private boolean elimina_tbm_filAniSelCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+="DELETE FROM tbm_filAniSelCfgImp";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
   
   private boolean inserta_tbm_filAniSelCfgImp(){
        boolean blnRes=true;
        int intAniSel=-1;
        int intAniSelAnt=-1;
        String strUpdSQL="";
        try{
            if(con!=null){
                if(optHisAnu.isSelected()){
                    stm=con.createStatement();
                    for(int i=0; i<arlDatAniAdd.size(); i++){
                        intAniSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_ANI);
                        if(intAniSelAnt!=intAniSel){
                            strSQL="";
                            strSQL+=" INSERT INTO tbm_filAniSelCfgImp(ne_aniSel)";
                            strSQL+=" VALUES( " + intAniSel + ");";
                            strUpdSQL+=strSQL;
                            intAniSelAnt=intAniSel;
                        }
                    }
                    stm.executeUpdate(strUpdSQL);
                    stm.close();
                    stm=null;
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

   private boolean elimina_tbm_filMesSelCfgImp(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+="DELETE FROM tbm_filMesSelCfgImp";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
   
   private boolean inserta_tbm_filMesSelCfgImp(){
        boolean blnRes=true;
        int intAniSel=-1;
        int intMesSel=-1;
        String strUpdSQL="";
        try{
            if(con!=null){
                if(optHisMen.isSelected()){
                   stm=con.createStatement();
                   for(int i=0; i<arlDatAniAdd.size(); i++){
                        intAniSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_ANI);
                        intMesSel=objUti.getIntValueAt(arlDatAniAdd, i, INT_ARL_NUM_MES);
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_filMesSelCfgImp(ne_aniSel, ne_mesSel)";
                        strSQL+=" VALUES( " + intAniSel + ", " + intMesSel + ");";
                        strUpdSQL+=strSQL;
                   }
                   stm.executeUpdate(strUpdSQL);
                   stm.close();
                   stm=null;
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
   
   private boolean update_tbmInv(){
        boolean blnRes=true;
        String strCodItm="";
        String strUpdSQL="";
        BigDecimal bgdProAnuVenMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanImpPza=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanImpPes=new BigDecimal(BigInteger.ZERO);
        int intTerTmp=0;
        String strLin="";
        try{
            if(con!=null){
               stm=con.createStatement();
               for(int i=0; i<objTblMod.getRowCountTrue(); i++)
               {
                   strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                   if(strLin.equals("M"))
                   {
                        strCodItm=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString();
                        strSQL ="";
                        strSQL+=" UPDATE tbm_inv";
                        strSQL+=" SET";
                        strSQL+=" nd_pesitmkgr=" + new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0": objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString())) + "";
                        
                        if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK_EST_MOV_ITM)) )
                            strSQL+=" , st_itmStk='S'";
                        else
                            strSQL+=" , st_itmStk='N'";
                        
                        bgdProAnuVenMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                        if (bgdProAnuVenMan.compareTo(new BigDecimal("0"))==0)
                            strSQL+=" , nd_proAnuVenMan=Null";
                        else
                            strSQL+=" , nd_proAnuVenMan=" + bgdProAnuVenMan + "";

                        //Aqui va para las columnas de importacion que se agregaron
                        intTerTmp=1;
                        for (int k=intNumColIniImp_Ped1; k<(intNumColFinImp_Ped1-1);k++)
                        {
                            bgdCanImpPza=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                            if(bgdCanImpPza.compareTo(new BigDecimal("0"))==0)
                                strSQL+=", nd_canPed" + intTerTmp + "=Null";
                            else
                                strSQL+=", nd_canPed" + intTerTmp + "=" + bgdCanImpPza + "";//intNumColIniPed

                            bgdCanImpPes=new BigDecimal(objTblMod.getValueAt(i, (k+1))==null?"0":(objTblMod.getValueAt(i, (k+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (k+1)).toString()));
                            if(bgdCanImpPes.compareTo(new BigDecimal("0"))==0)
                                strSQL+=", nd_canPedPes" + intTerTmp + "=Null";
                            else
                                strSQL+=", nd_canPedPes" + intTerTmp + "=" + bgdCanImpPes + "";//intNumColIniPed
                                       
                            intTerTmp++;
                        }
                        for(int k=intNumColIniImp_Ped2; k<(intNumColFinImp_Ped2-1);k++)
                        {
                            bgdCanImpPza=new BigDecimal(objTblMod.getValueAt(i, k)==null?"0":(objTblMod.getValueAt(i, k).toString().equals("")?"0":objTblMod.getValueAt(i, k).toString()));
                            if(bgdCanImpPza.compareTo(new BigDecimal("0"))==0)
                                strSQL+=", nd_canPed" + intTerTmp + "=Null";
                            else
                                strSQL+=", nd_canPed" + intTerTmp + "=" + bgdCanImpPza + "";//intNumColIniPed

                            bgdCanImpPes=new BigDecimal(objTblMod.getValueAt(i, (k+1))==null?"0":(objTblMod.getValueAt(i, (k+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (k+1)).toString()));
                            if(bgdCanImpPes.compareTo(new BigDecimal("0"))==0)
                                strSQL+=", nd_canPedPes" + intTerTmp + "=Null";
                            else
                                strSQL+=", nd_canPedPes" + intTerTmp + "=" + bgdCanImpPes + "";//intNumColIniPed
                        
                            intTerTmp++;
                        }
//------------------------------------------------------------------------------
                        //Valores manuales calculo de reposición:
                        strSQL+=" , nd_stkBas=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_STK_BAS), 0);
                        strSQL+=" , nd_tra=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_TRA), 0);
                        strSQL+=" , nd_fre=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_FRE), 0);
                        strSQL+=" , nd_exc=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_EXC), 0);
                        strSQL+=" , nd_punParMax=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MAX), 0);
                        strSQL+=" , nd_punParMin=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MIN), 0);
                        strSQL+=" , nd_MOQ=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_MOQ), 0);
//------------------------------------------------------------------------------
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_itm=" + strCodItm + ";";
                        //actualiza el peso en todas las empresas
                        strSQL+=" UPDATE tbm_inv";
                        strSQL+=" SET";
                        strSQL+=" nd_pesitmkgr=" + new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString())) + "";
                        if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK_EST_MOV_ITM)) )
                            strSQL+=" , st_itmStk='S'";
                        else
                            strSQL+=" , st_itmStk='N'";
//------------------------------------------------------------------------------
                        //Valores manuales calculo de reposición:
                        strSQL+=" , nd_stkBas=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_STK_BAS), 0);
                        strSQL+=" , nd_tra=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_TRA), 0);
                        strSQL+=" , nd_fre=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_FRE), 0);
                        strSQL+=" , nd_exc=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_EXC), 0);
                        strSQL+=" , nd_punParMax=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MAX), 0);
                        strSQL+=" , nd_punParMin=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_PUN_PAR_MIN), 0);
                        strSQL+=" , nd_MOQ=" + objUti.codificar(objTblMod.getValueAt(i, intNumColIniValManCalRep+INT_DAT_ADI_ITM_MOQ), 0);
//------------------------------------------------------------------------------
                        strSQL+=" FROM (";
                        strSQL+=" 	SELECT a1.co_emp, a2.co_itm";
                        strSQL+=" 	FROM tbm_inv AS a1";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+=" 	WHERE a2.co_itmMae=(";
                        strSQL+=" 	    SELECT a2.co_itmMae FROM tbm_inv AS a1 INNER JOIN tbm_equInv AS a2";
                        strSQL+=" 	    ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                        strSQL+=" 	    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="           AND a1.co_itm=" + strCodItm + "";
                        strSQL+=" 	)";
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                        
                        if(objTblMod.isChecked(i, intNumColIniChkItmEli)){
                            //agrego los items eliminados al arraylist, pero fueron eliminados por checkbox
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + strCodItm);
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "S");
                            arlDatFilEli.add(arlRegFilEli);
                        }
                        else{
                            //agrego los items eliminados al arraylist, pero fueron eliminados por checkbox
                            arlRegFilEli=new ArrayList();
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_EMP, "" + objParSis.getCodigoEmpresa());
                            arlRegFilEli.add(INT_ARL_FIL_ELI_COD_ITM, "" + strCodItm);
                            arlRegFilEli.add(INT_ARL_FIL_ELI_EST_ITM, "N");
                            arlDatFilEli.add(arlRegFilEli);
                        }
                   }
               }
               int intCodEmpFilEstEli=-1;
               int intCodItmFilEstEli=-1;
               String strEstEliItmChk="";

               for(int i=0; i<arlDatFilEli.size(); i++){
                   intCodEmpFilEstEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_EMP);
                   intCodItmFilEstEli=objUti.getIntValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_COD_ITM);
                   strEstEliItmChk=objUti.getStringValueAt(arlDatFilEli, i, INT_ARL_FIL_ELI_EST_ITM);
                   
                   strSQL="";
                   strSQL+=" UPDATE tbm_inv";
                   strSQL+=" SET";
                   if(strEstEliItmChk.equals("S"))
                        strSQL+=" st_itmEli='S'";
                   else
                       strSQL+=" st_itmEli=Null";
                   strSQL+=" WHERE co_emp=" + intCodEmpFilEstEli + "";
                   strSQL+=" AND co_itm=" + intCodItmFilEstEli + "";
                   strSQL+=" ;";
                   strUpdSQL+=strSQL;
               }
               stm.executeUpdate(strUpdSQL);
               stm.close();
               stm=null;
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
   
   private boolean update_tbmCabNotPedPreImp(){
        boolean blnRes=true;
        String strUpdSQL="";
        String strLin="";
        String strFecUltMod="";
        int intColArl=-1;
        String strNomPedIngNow="";
        try{
            if(con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
               
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
               
                strSQL ="";
                strSQL+=" SELECT (CASE WHEN (MAX(a1.co_doc)+1) IS NULL THEN 1 ELSE (MAX(a1.co_doc)+1) END) AS co_doc";                    
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1"; 
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + ""; 
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intCodDocPedPre_canRepLSC=rst.getInt("co_doc");
                if (intCodDocPedPre_canRepLSC==-1)
                    return false;
                
                strSQL ="";
                strSQL+=" SELECT (CASE WHEN (MAX(a1.ne_numDoc)+1) IS NULL THEN 1 ELSE (MAX(a1.ne_numDoc)+1) END) AS ne_numDoc";                    
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1"; 
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + ""; 
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + INT_EST_COD_TIP_DOC_PED_PRE + ""; 
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intNumDocPedPre_canRepLSC=rst.getInt("ne_numDoc");
                if (intNumDocPedPre_canRepLSC==-1)
                    return false;
                
                //Validar si existe el nombre Pedido Previo en la DB
                for(int k=0; k<arlDatPedPre.size();k++) {
                    strLin=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);
                    if( strLin.equals("I")) {
                        strNomPedIngNow=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                        if(existeNombrePedidoPrevioDB(strNomPedIngNow)) { 
                            //Coloco los campos del primary key de la tabla en el arreglo que tenia estado 'I', los saco de la DB si me retorna algo el query q valida el nombrePedPrevio
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_EMP, "" + Integer.parseInt(""+ intCodEmpExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_LOC, "" + Integer.parseInt(""+ intCodLocExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_TIP_DOC, "" + Integer.parseInt(""+ intCodTipDocExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_DOC, "" + Integer.parseInt(""+ intCodDocExiNomPedPre));
                            objUti.setStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD, "M"); //cambio el estado de 'I' a 'M'
                        }
                    }
                }
                strLin="";
                strNomPedIngNow="";
                
                //Obtengo la columna del pedido previo y se arma el SQL de acuerdo al pedido previo para guardar por pedido previo y no item por item
                for(int k=0; k<arlDatPedPre.size();k++){
                    strLin=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COL_MOD);
                    intColArl=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_COL);
                    if(strLin.equals("I")){
                        if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_ING_IMP) {
                            //Se valida para que no inserte NPP de Importaciones, las NPP se crearán desde el Presupuesto.
                            mostrarMsgInf("<HTML>No se pueden crear Notas de Pedido Previas desde este programa.<BR>Cree la Nota de Pedido Previa en el presupuesto y vuelva a intentarlo.</HTML>");
                            return false;                        
                        }
                        else{
                            //Se valida para que inserte NPP de Compras Locales. Debido a que no se crearán desde el Presupuesto.
                            strNomPedIngNow=objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_NUM_PED_PRE);
                            if(intColArl==(intNumColFinPedPre-2)){
                                strSQL="";
                                strSQL+=" INSERT INTO tbm_cabNotPedPreImp(";
                                strSQL+="   co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, tx_numdoc2";
                                strSQL+=" , st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, st_cie, co_locRel, co_tipDocRel, co_docRel)";
                                strSQL+=" VALUES(";
                                strSQL+="         " + objParSis.getCodigoEmpresa() + "";  //co_emp
                                strSQL+="       , " + objParSis.getCodigoLocal() + "";    //co_loc
                                strSQL+="       , " + INT_EST_COD_TIP_DOC_PED_PRE + "";   //co_tipdoc
                                strSQL+="       , " + intCodDocPedPre_canRepLSC + "";     //co_doc
                                strSQL+="       ,'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                                strSQL+="       , " + intNumDocPedPre_canRepLSC + "";         //ne_numdoc
                                strSQL+="       , " + objUti.codificar(strNomPedIngNow) + ""; //tx_numdoc2
                                strSQL+="       ,'A'";//st_reg
                                strSQL+="       ,'" + strFecUltMod + "'";//fe_ing
                                strSQL+="       ,'" + strFecUltMod + "'";//fe_ultmod
                                strSQL+="       , " + objParSis.getCodigoUsuario() + "";  //co_usring
                                strSQL+="       , " + objParSis.getCodigoUsuario() + "";  //co_usrmod
                                strSQL+="       ,'N'";   //st_cie
                                strSQL+="       ,Null";  //co_locRel
                                strSQL+="       ,Null";  //co_tipDocRel
                                strSQL+="       ,Null";  //co_docRel
                                strSQL+=" );";
                                strUpdSQL+=strSQL;
                                intCodDocPedPre_seg=intCodDocPedPre_canRepLSC;
                            }
                        }
                    }
                    else if(strLin.equals("M")) {
                        strSQL="";
                        strSQL+=" UPDATE tbm_cabNotPedPreImp";
                        strSQL+=" SET co_usrMod=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" , fe_ultMod='" + strFecUltMod + "'";
                        strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_EMP) + "";
                        strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_LOC) + "";
                        strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_TIP_DOC) + "";
                        strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_DOC) + "";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                        intCodDocPedPre_seg=objUti.getIntValueAt(arlDatPedPre, k, INT_ARL_CAB_PED_PRE_COD_DOC);
                    }                   
                }
               
                //para anular las Notas de Pedido Previas que se eliminaron cuando el usuario selecciono la columna
                int intCodEmpEstNotPedPreAnu, intCodLocEstNotPedPreAnu, intCodTipDocEstNotPedPreAnu, intCodDocEstNotPedPreAnu;
                String strEstNotPedPreAnu;
               
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    strEstNotPedPreAnu=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST_ANU);
                    intCodEmpEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_EMP);
                    intCodLocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_LOC);
                    intCodTipDocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                    intCodDocEstNotPedPreAnu=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_DOC);
                    if(strEstNotPedPreAnu.equals("E")){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabNotPedPreImp";
                        strSQL+=" SET st_reg='I'";
                        strSQL+=" , co_usrMod=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" , fe_ultMod='" + strFecUltMod + "'";
                        strSQL+=" WHERE co_emp=" + intCodEmpEstNotPedPreAnu + "";
                        strSQL+=" AND co_loc=" + intCodLocEstNotPedPreAnu + "";
                        strSQL+=" AND co_tipDoc=" + intCodTipDocEstNotPedPreAnu + "";
                        strSQL+=" AND co_doc=" + intCodDocEstNotPedPreAnu + "";
                        strSQL+=" ;";
                        strUpdSQL+=strSQL;
                    }
                }
                stm.executeUpdate(strUpdSQL);
                stm.close();
                stm=null;
                
                //Insertar Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_cabNotPedPreImp", "tbh_cabNotPedPreImp", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());                                                                    
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
   
    private boolean update_tbmDetNotPedPreImp(){
        boolean blnRes=true;
        String strUpdSQL="";
        String strColArlCab="";
        int intCodEmpCab=-1, intCodLocCab=-1, intCodTipDocCab=-1, intCodDocCab=-1;
        int intCodEmpDet=-1, intCodLocDet=-1, intCodTipDocDet=-1, intCodDocDet=-1, intCodItmDet=-1;
        int intColModCab=-1;
        int intUltRegPedPre=0;
        BigDecimal bgdCanPedPrePza=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanPedPrePes=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanPesItmTmp=new BigDecimal(BigInteger.ZERO);
        //String strEstPedPre="";
        String strLinMod="";
        try{
            if(con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
               
                //obtengo la columna del pedido previo que se ha modificado, ya con esto obtengo que columnas debo modificar y no senso en vano las otras columnas y filas
                for(int c=0; c<arlDatPedPre.size();c++){
                   strColArlCab=objUti.getStringValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COL_MOD);
                   intCodEmpCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_EMP);
                   intCodLocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_LOC);
                   intCodTipDocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                   intCodDocCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_DOC);
                   intColModCab=objUti.getIntValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_NUM_COL);
                   
                    //obtengo el ultimo registro insertado
                    strSQL="";
                    strSQL+=" SELECT MAX(a1.co_reg) AS ne_ultCodReg";
                    strSQL+=" FROM tbm_detNotPedPreImp AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmpCab + "";
                    strSQL+=" AND a1.co_loc=" + intCodLocCab + "";
                    strSQL+=" AND a1.co_tipdoc=" + intCodTipDocCab + "";
                    strSQL+=" AND a1.co_doc=" + intCodDocCab + "";
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                        intUltRegPedPre=rst.getInt("ne_ultCodReg");
                    if (intUltRegPedPre==-1)
                        intUltRegPedPre=0;
                   
                    if(strColArlCab.equals("I")){
                        if(intColModCab==(intNumColFinPedPre-2)){
                            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                                intCodItmDet=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString());
                               
                                if(chkCalRepPes.isSelected()){
                                    bgdCanPedPrePes=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                    bgdCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                    bgdCanPedPrePza=calcularPesoCantidadImportar(bgdCanPedPrePes, bgdCanPesItmTmp);
                                }
                                else{//pieza
                                    bgdCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                    bgdCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                    bgdCanPedPrePes=calcularPesoCantidadImportar(bgdCanPedPrePza, bgdCanPesItmTmp);
                                    bgdCanPedPrePes=new BigDecimal("0");//se manda a cero porque para cuando es por pieza, no se guarda la TM
                                }                               
                                strLinMod=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString(); 
                               
                               if(strLinMod.equals("M")){
                                    if(bgdCanPedPrePza.compareTo(new BigDecimal("0"))>0){
                                        intUltRegPedPre++;

                                        //Si el item no existe se inserta en el detalle(en este caso si tengo los datos del primary key
                                        strSQL="";
                                        strSQL+=" INSERT INTO tbm_detNotPedPreImp";
                                        strSQL+="(co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_can, nd_pestotkgr)";
                                        strSQL+="(";
                                        strSQL+="   SELECT ";
                                        strSQL+="      " + objParSis.getCodigoEmpresa() + " AS co_emp";   //co_emp
                                        strSQL+="     ," + objParSis.getCodigoLocal() + " AS co_loc";     //co_loc
                                        strSQL+="     ," + INT_EST_COD_TIP_DOC_PED_PRE + " AS co_tipdoc"; //co_tipdoc
                                        strSQL+="     ," + intCodDocPedPre_canRepLSC + " AS co_doc";      //co_doc
                                        strSQL+="     ," + intUltRegPedPre + " AS co_reg";                //co_reg
                                        strSQL+="     ," + intCodItmDet + " AS co_itm";                   //co_itm
                                        strSQL+="     ," + bgdCanPedPrePza + " AS nd_can";                //nd_can
                                        strSQL+="     ," + bgdCanPedPrePes + " AS nd_pestotkgr";          //nd_pestotkgr
                                        strSQL+="   WHERE NOT EXISTS(";
                                        strSQL+="           SELECT *FROM tbm_detNotPedPreImp";
                                        strSQL+="           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                        strSQL+="           AND co_loc=" + objParSis.getCodigoLocal() + "";
                                        strSQL+="           AND co_tipDoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                                        strSQL+="           AND co_doc=" + intCodDocPedPre_canRepLSC + "";
                                        strSQL+="           AND co_itm=" + intCodItmDet + "";
                                        strSQL+="   )";
                                        strSQL+=");";
                                        strUpdSQL+=strSQL;
                                        bgdCanPedPrePza=new BigDecimal(BigInteger.ZERO);
                                        bgdCanPedPrePes=new BigDecimal(BigInteger.ZERO);
                                    }
                                }
                            }
                       }
                   }
                   else if(strColArlCab.equals("M")){
                        for(int b=intNumColIniPedPre; b<intNumColFinPedPre;b++){
                            if(intColModCab==b){
                                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                                    intCodItmDet=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()); 
                                    //bgdCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                    
                                    if(chkCalRepPes.isSelected()){
                                        bgdCanPedPrePes=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString())   );
                                        bgdCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                        bgdCanPedPrePza=calcularPesoCantidadImportar(bgdCanPedPrePes, bgdCanPesItmTmp);
                                    }
                                    else{//pieza
                                        bgdCanPedPrePza=new BigDecimal(objTblMod.getValueAt(i, intColModCab)==null?"0":(objTblMod.getValueAt(i, intColModCab).toString().equals("")?"0":objTblMod.getValueAt(i, intColModCab).toString()));
                                        bgdCanPesItmTmp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                        bgdCanPedPrePes=calcularPesoCantidadImportar(bgdCanPedPrePza, bgdCanPesItmTmp);
                                        bgdCanPedPrePes=new BigDecimal("0");//se manda a cero porque para cuando es por pieza, no se guarda la TM
                                    }
                                    
                                    strLinMod=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                                    if(strLinMod.equals("M")){
                                        if(bgdCanPedPrePza.compareTo(new BigDecimal("0"))>=0){
                                            intUltRegPedPre++;
                                            //objUti.getStringValueAt(arlDatPedPre, c, INT_ARL_CAB_PED_PRE_COD_EMP)
                                            //Armar la sentencia SQL.
                                            strSQL ="";
                                            strSQL+=" UPDATE tbm_detNotPedPreImp";
                                            strSQL+=" SET nd_can=" + bgdCanPedPrePza + "";
                                            strSQL+="   , nd_pestotkgr=" + bgdCanPedPrePes + "";
                                            strSQL+=" WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+=" AND co_loc=" + intCodLocCab + "";
                                            strSQL+=" AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+=" AND co_doc=" + intCodDocCab + "";
                                            strSQL+=" AND co_itm=" + intCodItmDet + "";
                                            strSQL+=" AND EXISTS(";
                                            strSQL+="       SELECT *FROM tbm_detNotPedPreImp";
                                            strSQL+="       WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+="       AND co_loc=" + intCodLocCab + "";
                                            strSQL+="       AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+="       AND co_doc=" + intCodDocCab + "";
                                            strSQL+="       AND co_itm=" + intCodItmDet + "";
                                            strSQL+=" )";
                                            strSQL+=";";
                                            strUpdSQL+=strSQL;
                                            strSQL="";
                                            //si el item no existe se inserta en el detalle(en este caso si tengo los datos del primary key
                                            strSQL+=" INSERT INTO tbm_detNotPedPreImp";
                                            strSQL+=" (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_itm, nd_can, nd_pestotkgr )";
                                            strSQL+=" (";
                                            strSQL+="   SELECT ";
                                            strSQL+="      " + intCodEmpCab + " AS co_emp";          //co_emp
                                            strSQL+="     ," + intCodLocCab + " AS co_loc";          //co_loc
                                            strSQL+="     ," + intCodTipDocCab + " AS co_tipdoc";    //co_tipdoc
                                            strSQL+="     ," + intCodDocCab + " AS co_doc";          //co_doc
                                            strSQL+="     ," + intUltRegPedPre + " AS co_reg";       //co_reg
                                            strSQL+="     ," + intCodItmDet + " AS co_itm";          //co_itm
                                            strSQL+="     ," + bgdCanPedPrePza + " AS nd_can";       //nd_can
                                            strSQL+="     ," + bgdCanPedPrePes + " AS nd_pestotkgr"; //nd_pestotkgr
                                            strSQL+="	WHERE NOT EXISTS(";
                                            strSQL+="           SELECT *FROM tbm_detNotPedPreImp";
                                            strSQL+="           WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+="           AND co_loc=" + intCodLocCab + "";
                                            strSQL+="           AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+="           AND co_doc=" + intCodDocCab + "";
                                            strSQL+="           AND co_itm=" + intCodItmDet + "";
                                            strSQL+="	)";
                                            strSQL+=");";
                                            strUpdSQL+=strSQL;
                                            bgdCanPedPrePza=new BigDecimal(BigInteger.ZERO);
                                            bgdCanPedPrePes=new BigDecimal(BigInteger.ZERO);
                                            
                                            strSQL="";
                                            strSQL+=" DELETE FROM tbm_detNotPedPreImp";
                                            strSQL+=" WHERE co_emp=" + intCodEmpCab + "";
                                            strSQL+=" AND co_loc=" + intCodLocCab + "";
                                            strSQL+=" AND co_tipDoc=" + intCodTipDocCab + "";
                                            strSQL+=" AND co_doc=" + intCodDocCab + "";
                                            strSQL+=" AND nd_can=0";
                                            strSQL+=";";
                                            strUpdSQL+=strSQL;
                                        }
                                    }
                                }
                            }
                        }
                   }
               }//fin del for
               stm.executeUpdate(strUpdSQL);
               stm.close();
               stm=null;
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
     * Función que permite insertar la Nota de Pedido Previa en la tabla de seguimiento.
     * @return 
     */
    private boolean insertar_tbmCabSegMovInv_NotPedPreImp(){
        boolean blnRes=false;
        objCodSegNotPedPre=null;
        try{  
            if(con!=null){
                if(BLN_EXI_NPP){
                    if(getCodSegNotExiNotPedPre()){ 
                        /*Cuando no existe seguimiento, insertar seguimiento. */
                        if(objSegMovInv.setSegMovInvCompra(con, objCodSegNotPedPre, 0, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), INT_EST_COD_TIP_DOC_PED_PRE, intCodDocPedPre_seg)){
                            blnRes=true;  /*Inserción en seguimiento de inventario.*/
                        }
                    }
                    else{ 
                        blnRes=true; /* Cuando existe seguimiento, no insertar seguimiento. Solo se continúa con el proceso de guardado. */
                    }
                }
                else {
                    blnRes=true; /* Cuando NO existe NPP, solo se continúa con el proceso de guardado. */
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }   
    
    /**
     * Función que permite determinar si existe código de seguimiento asociado a la nota de pedido previa
     * @return true Si no existe seguimiento asociado para la Nota de Pedido Previa
     * <BR> false Caso contrario
     */
    private boolean getCodSegNotExiNotPedPre(){
        boolean blnRes=false;
        objCodSegNotPedPre=null;
        try{
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT a3.co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                strSQL+=" ON a1.co_emp=a3.co_empRelCabNotPedPreImp AND a1.co_loc=a3.co_locRelCabNotPedPreImp";
                strSQL+=" AND a1.co_tipDoc=a3.co_tipDocRelCabNotPedPreImp AND a1.co_doc=a3.co_docRelCabNotPedPreImp";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipDoc=" + INT_EST_COD_TIP_DOC_PED_PRE + "";
                strSQL+=" AND a1.co_doc=" + intCodDocPedPre_seg + "";                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    objCodSegNotPedPre=rst.getObject("co_seg"); 
                }
                if (objCodSegNotPedPre==null)
                { //No existe seguimiento por tanto se debe ingresar
                    blnRes=true;
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }    

   private boolean cambiarNumeroMesesReponerTabla(){
       boolean blnRes=true;
       try{
           for(int i=0; i<objTblMod.getRowCountTrue(); i++){
               objTblMod.setValueAt(jspNumMesRep.getValue(), i, intNumColIniRep);
           }        
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }   
      
   private boolean recalcularValoresTabla()
   {
        boolean blnRes=true;
        try
        {
            calcularColumnaTotalPedido();
            calcularColumnaTotalPedidoPrevio();
            calcularColumnaTotalesAniMes_1();
            calcularColumnaSumaTotales_peso();
            calcularColumnaSumaTotales_costo();
            if(optHisMen.isSelected())
                calcularPromedioMensual();
            else
                calcularPromedioAnual();
            calcularCantidadReponerCalculada();
            calcularCantidadReponerManual();
            calcularPesoCantidadImportar();
            calcularTotalPromedioAnualCalculada_peso_1();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_2();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_3();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_4();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualCalculada_peso_calMax();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
            calcularTotalPromedioAnualManual_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion manual)
            calcularTotalStockActual_Importacion_peso();//calcula el valor de la columna en la tabla de totales(columna de stock e importacion)
            calcularTotalPedido_peso();//calcula el valor de la columna en la tabla de todades(cada columna del pedido)
            calcularTotalPedidoPrevio_peso();
            calcularTotalCantidadReponerCalculada_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion calculada)
            calcularTotalCantidadReponerManual_peso();//calcula el valor de la columna en la tabla de totales(olumna de reposicion manual)
            calcularTotalCantidadImportar_peso();//calcula el valor de la columna en la tabla de totales(columna de importacion)
            calcularTotalesCostoProAnuCalMan_costo();
            calcularTotalesHistorico_costo_1();
            calcularTotalesHistorico_costo_2();
            calcularTotalesHistorico_costo_3();
            calcularTotalesHistorico_costo_4();
            calcularTotalesStock_costo();
            calcularTotalesPedidosCurso_costo();
            calcularTotalesPedidosPrevios_costo();
            calcularTotalesCantidadReposicion_costo();
            calcularTotalesCantidadImportar_costo();
            calcularTotalesHistorico_peso_1();
            calcularTotalesHistorico_peso_2();
            calcularTotalesHistorico_peso_3();
            calcularTotalesHistorico_peso_4();

            calcularDisponiblePedido();//calcula disponible(Stock inicial+importaciones+llegadas)
            calcularVentaPromedioManualCalculado_1();//ventas promedio a 12 meses
            calcularTotalVentaPromedio_peso();
            calcularTotalVentaPromedio_costo();
            setValMaxCal();
            calcularDisponibleStock();
            calcularCalMenMax();
            //Para calcular las cantidades de reposicion segun los meses que el usuario ha escogido
            getDatNotPed_PedEmb();
            calcularColumnaRep_Dis();
            calculaSaldoIniRep();
            calcularTotalesReponer_peso();
            calcularTotalesReponer_costo();
            calcularTotalesCostoProAnuCalMan_costo_MaxCal();
            calcularTotalPromedioAnualCalculada_peso_calMax();
            calcularTotalesSaldoInicial_costo();
            calcularTotalesSaldoInicial_peso();
            calcularTotalesHistorico_costoStockInicial_1();
            calcularTotalesHistorico_costoStockInicial_2();
            calcularTotalesHistorico_costoStockInicial_3();
            calcularTotalesHistorico_costoStockInicial_4();
            calcularTotalesHistorico_precioStockInicial_1();
            calcularTotalesHistorico_precioStockInicial_2();
            calcularTotalesHistorico_precioStockInicial_3();
            calcularTotalesHistorico_precioStockInicial_4();
//------------------------------------------------------------------------------
            calcularTotalValManCalRep();
//------------------------------------------------------------------------------
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
   }
   
   
   private boolean recalcularValoresTabla(int fila){
       boolean blnRes=true;
       int i=fila;
       try{
            if(i!=-1){
                calcularColumnaTotalPedido(i);
                calcularColumnaTotalPedidoPrevio(i);
                calcularColumnaTotalesAniMes_1(i);

                calcularColumnaSumaTotales_peso(i);
                calcularColumnaSumaTotales_costo(i);

                if(optHisMen.isSelected())
                    calcularPromedioMensual(i);
                else
                    calcularPromedioAnual(i);

                calcularCantidadReponerCalculada(i);
                calcularCantidadReponerManual(i);
                calcularPesoCantidadImportar(i);

                //Completos
                calcularTotalPromedioAnualCalculada_peso_1();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
                calcularTotalPromedioAnualCalculada_peso_2();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
                calcularTotalPromedioAnualCalculada_peso_3();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
                calcularTotalPromedioAnualCalculada_peso_4();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
                calcularTotalPromedioAnualCalculada_peso_calMax();//calcula el valor de la columna en la tabla de totales(columna promedio anual calculada)
                calcularTotalPromedioAnualManual_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion manual)
                calcularTotalStockActual_Importacion_peso();//calcula el valor de la columna en la tabla de totales(columna de stock e importacion)
                calcularTotalPedido_peso();//calcula el valor de la columna en la tabla de todades(cada columna del pedido)
                calcularTotalPedidoPrevio_peso();//columna de totales 
                calcularTotalCantidadReponerCalculada_peso();//calcula el valor de la columna en la tabla de totales(columna de reposicion calculada)
                calcularTotalCantidadReponerManual_peso();//calcula el valor de la columna en la tabla de totales(olumna de reposicion manual)
                calcularTotalCantidadImportar_peso();//calcula el valor de la columna en la tabla de totales(columna de importacion)
                calcularTotalesCostoProAnuCalMan_costo();//fila de costo
                calcularTotalesHistorico_costo_1();
                calcularTotalesHistorico_costo_2();
                calcularTotalesHistorico_costo_3();
                calcularTotalesHistorico_costo_4();
                calcularTotalesStock_costo();
                calcularTotalesPedidosCurso_costo();
                calcularTotalesPedidosPrevios_costo();
                calcularTotalesCantidadReposicion_costo();
                calcularTotalesCantidadImportar_costo();
                calcularTotalesHistorico_peso_1();
                calcularTotalesHistorico_peso_2();
                calcularTotalesHistorico_peso_3();
                calcularTotalesHistorico_peso_4();

                calcularDisponiblePedido(i);//calcula disponible(Stock inicial+importaciones+llegadas)
                calcularVentaPromedioManualCalculado_1(i);//ventas promedio a 12 meses
                calcularTotalVentaPromedio_peso();
                calcularTotalVentaPromedio_costo();

                setValMaxCal(i);
                calcularDisponibleStock(i);
                calcularCalMenMax(i);

                //Para calcular las cantidades de reposicion segun los meses que el usuario ha escogido
                //getDatNotPed_PedEmb(i);
                calcularColumnaRep_Dis(i);
                calculaSaldoIniRep(i);
                calcularTotalesReponer_peso();
                calcularTotalesReponer_costo();
                calcularTotalesCostoProAnuCalMan_costo_MaxCal();
                calcularTotalPromedioAnualCalculada_peso_calMax();
                calcularTotalesSaldoInicial_costo();
                calcularTotalesSaldoInicial_peso();
                calcularTotalesHistorico_costoStockInicial_1();
                calcularTotalesHistorico_costoStockInicial_2();
                calcularTotalesHistorico_costoStockInicial_3();
                calcularTotalesHistorico_costoStockInicial_4();
                calcularTotalesHistorico_precioStockInicial_1();
                calcularTotalesHistorico_precioStockInicial_2();
                calcularTotalesHistorico_precioStockInicial_3();
                calcularTotalesHistorico_precioStockInicial_4();
            }

           
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
       }
       return blnRes;
   }
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistorico_costo_1(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_costoStockInicial_1(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));

                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));

                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);                    
                    
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_costoStockInicial_2(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_costoStockInicial_3(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_costoStockInicial_4(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_precioStockInicial_1(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));

                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);                    
                    
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_precioStockInicial_2(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));

                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);                    
                    
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_precioStockInicial_3(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));

                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);                    
                    
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesHistorico_precioStockInicial_4(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_STK;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));

                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);                    
                    
                }
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                j=j+(INT_NUM_COL_HIS_VTA_MES);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistorico_costo_2(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistorico_costo_3(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   /**
    * Función que permite calcular los totales de precio de la tabla
    * @return 
    */
   private boolean calcularTotalesHistorico_costo_4(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(((bgdTotHisVta1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdCosUniGrp.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 1, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesCostoProAnuCalMan_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuCal1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuCal1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuCal2=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuCal2=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuCal3=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuCal3=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuCal4=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuCal4=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                //calculado 1
                bgdProAnuCal1=new BigDecimal(objTblMod.getValueAt(i, intNumColIniProAnu)==null?"0":(objTblMod.getValueAt(i, intNumColIniProAnu).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniProAnu).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuCal1=bgdSumProAnuCal1.add(((bgdProAnuCal1.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuCal1=bgdSumProAnuCal1.add(bgdCosUniGrp.multiply(bgdProAnuCal1));
                
                //calculado 2
                bgdProAnuCal2=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+1)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuCal2=bgdSumProAnuCal2.add(((bgdProAnuCal2.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuCal2=bgdSumProAnuCal2.add(bgdCosUniGrp.multiply(bgdProAnuCal2));
                
                
                //calculado 3
                bgdProAnuCal3=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+2)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuCal3=bgdSumProAnuCal3.add(((bgdProAnuCal3.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuCal3=bgdSumProAnuCal3.add(bgdCosUniGrp.multiply(bgdProAnuCal3));
                
                //calculado 4
                bgdProAnuCal4=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+3))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+3)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuCal4=bgdSumProAnuCal4.add(((bgdProAnuCal4.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuCal4=bgdSumProAnuCal4.add(bgdCosUniGrp.multiply(bgdProAnuCal4));
                
                //manual
                bgdProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinProAnu-1)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuMan=bgdSumProAnuMan.add(((bgdProAnuMan.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuMan=bgdSumProAnuMan.add(bgdCosUniGrp.multiply(bgdProAnuMan));
                
                bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                bgdProAnuCal1=new BigDecimal(BigInteger.ZERO);
                bgdProAnuCal2=new BigDecimal(BigInteger.ZERO);
                bgdProAnuCal3=new BigDecimal(BigInteger.ZERO);
                bgdProAnuCal4=new BigDecimal(BigInteger.ZERO);
                bgdProAnuMan=new BigDecimal(BigInteger.ZERO);
            }
            objTblModTot.setValueAt(bgdSumProAnuCal1, 1, intNumColIniProAnu);//calculado 1
            objTblModTot.setValueAt(bgdSumProAnuCal2, 1, (intNumColIniProAnu+1));//calculado 2
            objTblModTot.setValueAt(bgdSumProAnuCal3, 1, (intNumColIniProAnu+2));//calculado 3
            objTblModTot.setValueAt(bgdSumProAnuCal4, 1, (intNumColIniProAnu+3));//calculado 4
            objTblModTot.setValueAt(bgdSumProAnuMan, 1, (intNumColFinProAnu-1));
            bgdSumProAnuCal1=new BigDecimal(BigInteger.ZERO);
            bgdSumProAnuCal2=new BigDecimal(BigInteger.ZERO);
            bgdSumProAnuCal3=new BigDecimal(BigInteger.ZERO);
            bgdSumProAnuCal4=new BigDecimal(BigInteger.ZERO);
            bgdSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Función que permite calcular totales
    */
   private boolean calcularTotalesCostoProAnuCalMan_costo_MaxCal(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdMaxCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumMaxCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                //maximo calculado
                bgdMaxCal=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+4))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumMaxCal=bgdSumMaxCal.add(((bgdMaxCal.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumMaxCal=bgdSumMaxCal.add(bgdCosUniGrp.multiply(bgdMaxCal));
                
                bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                bgdMaxCal=new BigDecimal(BigInteger.ZERO);
            }
            objTblModTot.setValueAt(bgdSumMaxCal, 1, (intNumColIniProAnu+4));//maximo calculado
            bgdSumMaxCal=new BigDecimal(BigInteger.ZERO);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

      
   

   

   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistorico_peso_1(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor; j<intNumColFinFecCor;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));
                    
                    
                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistorico_peso_2(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor2; j<intNumColFinFecCor2;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistorico_peso_3(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor3; j<intNumColFinFecCor3;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   /**
    * Función que permite realizar la asuma de totales en la columna de unidades -- historico de ventas
    * @return true Si se pudo realizar la operación
    * <BR> false en caso contrario
    */
    private boolean calcularTotalesHistorico_peso_4(){
        boolean blnRes=true;
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniFecCor4; j<intNumColFinFecCor4;j++){
                j=j+INT_NUM_COL_HIS_VTA_UNI;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    //Histórico de ventas 1
                    bgdTotHisVta1=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdTotHisVta1);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumTotHisVta1=bgdSumTotHisVta1.add(bgdPesItm.multiply(bgdTotHisVta1));
                    
                    //Limpia las variables
                    bgdPesItm=new BigDecimal(BigInteger.ZERO);
                    bgdTotHisVta1=new BigDecimal(BigInteger.ZERO);
                }
                //de totales en la fila cero, es decir calcula la primra fila
                //Histórico de ventas 1
                objTblModTot.setValueAt(bgdSumTotHisVta1, 0, j);
                bgdSumTotHisVta1=new BigDecimal(BigInteger.ZERO);
                
                j=j+(INT_NUM_COL_HIS_VTA_MES-1);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesStock_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdStk=new BigDecimal(0);
        BigDecimal bgdSumStkCos=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniStk; j<intNumColFinStk; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    bgdStk=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumStkCos=bgdSumStkCos.add(((bgdStk.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumStkCos=bgdSumStkCos.add(bgdStk.multiply(bgdCosUniGrp));

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdStk=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumStkCos, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bgdSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesPedidosCurso_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdSumPedCur=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    bgdPedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumPedCur=bgdSumPedCur.add(((bgdPedCur.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumPedCur=bgdSumPedCur.add(bgdPedCur.multiply(bgdCosUniGrp));
                    

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdPedCur=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumPedCur, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bgdSumPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesPedidosPrevios_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPedCur=new BigDecimal(0);
        BigDecimal bgdSumPedCur=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniPedPre; j<intNumColFinPedPre; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    bgdPedCur=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumPedCur=bgdSumPedCur.add(((bgdPedCur.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumPedCur=bgdSumPedCur.add(bgdPedCur.multiply(bgdCosUniGrp));

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdPedCur=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumPedCur, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bgdSumPedCur=new BigDecimal(0);
            }
        }
        catch(Exception e){
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

    private boolean consultaItemsPedidoPrevio(){
        boolean blnRes=true;
        Statement stmCanPedPre;
        ResultSet rstCanPedPre;
        try{
            if(con!=null){
                stmCanPedPre=con.createStatement();
                //crear un arreglo y colocar ahi la informacion para luego ser leida antes de terminar de hacer la consulta.
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc";
                strSQL+="      , a1.tx_numdoc2, a2.co_itm, a2.nd_can, a3.tx_codAlt";
                strSQL+=" FROM tbm_cabNotPedPreImp AS a1 INNER JOIN tbm_detNotPedPreImp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cie='N'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc, a1.ne_numdoc, a1.tx_numdoc2";
                strSQL+="        , a2.co_itm, a2.nd_can, a3.tx_codAlt";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                rstCanPedPre=stmCanPedPre.executeQuery(strSQL);
                arlDatDetPedPre.clear();
                while(rstCanPedPre.next()){
                    arlRegDetPedPre=new ArrayList();
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_EMP,     rstCanPedPre.getString("co_emp"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_LOC,     rstCanPedPre.getString("co_loc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_TIP_DOC, rstCanPedPre.getString("co_tipDoc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_DOC,     rstCanPedPre.getString("co_doc"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ITM,     rstCanPedPre.getString("co_itm"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_CAN_PED_PRE, rstCanPedPre.getString("nd_can"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_COD_ALT_ITM, rstCanPedPre.getString("tx_codAlt"));
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST,         "");
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_NUM_COL,     "0");
                    arlRegDetPedPre.add(INT_ARL_DET_PED_PRE_EST_ANU,     "");
                    arlDatDetPedPre.add(arlRegDetPedPre);
                }
                stmCanPedPre.close();
                stmCanPedPre=null;
                rstCanPedPre.close();
                rstCanPedPre=null;
                               
                //se cargan los datos de valores del pedido previos
                int intCodEmpNomPedPre=-1;
                int intCodLocNomPedPre=-1;
                int intCodTipDocNomPedPre=-1;
                int intCodDocNomPedPre=-1;
                int intNumColNomPedPre=-1;                
                int intCodEmpDetPedPre=-1;
                int intCodLocDetPedPre=-1;
                int intCodTipDocDetPedPre=-1;
                int intCodDocDetPedPre=-1;                
                String strEstPedPre="";
                
                for(int d=0; d<(arlDatPedPre.size()); d++){
                    intCodEmpNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_EMP);
                    intCodLocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_LOC);
                    intCodTipDocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_TIP_DOC);
                    intCodDocNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_COD_DOC);
                    intNumColNomPedPre=objUti.getIntValueAt(arlDatPedPre, d, INT_ARL_CAB_PED_PRE_NUM_COL);
           
                    for(int e=0; e<(arlDatDetPedPre.size()); e++){
                        intCodEmpDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_EMP);
                        intCodLocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_LOC);
                        intCodTipDocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_TIP_DOC);
                        intCodDocDetPedPre=objUti.getIntValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_COD_DOC);
                        strEstPedPre=objUti.getStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST);
                        
                        if(strEstPedPre.equals("")){
                            if( (intCodEmpNomPedPre==intCodEmpDetPedPre) && (intCodLocNomPedPre==intCodLocDetPedPre) && (intCodTipDocNomPedPre==intCodTipDocDetPedPre) && (intCodDocNomPedPre==intCodDocDetPedPre) ){
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "S");
                                objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_NUM_COL, ""+intNumColNomPedPre);
                            }
                        }
                    }
                }                
                
                //SE BLANQUEAN LOS ESTADOS DE LOS REGISTROS PARA PODER PROCESARLOS AL MOMENTO DE CARGAR LOS DATOS
                for(int e=0; e<(arlDatDetPedPre.size()); e++){
                    objUti.setStringValueAt(arlDatDetPedPre, e, INT_ARL_DET_PED_PRE_EST, "");
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
    
    private boolean existeNombrePedidoPrevioDB(String nombrePedido){
        boolean blnRes=false;
        String strSQLAux="";
        Statement stmExiNomPedPre;
        ResultSet rstExiNomPedPre;
        Connection conExiNomPedPre;
        try{
            conExiNomPedPre=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conExiNomPedPre!=null){
                stmExiNomPedPre=conExiNomPedPre.createStatement(); 
                strSQLAux ="";
                strSQLAux+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.tx_numdoc2";
                strSQLAux+=" FROM tbm_cabnotpedpreimp AS a1";
                strSQLAux+=" WHERE a1.tx_numdoc2='" + nombrePedido + "'";
                strSQLAux+=" AND a1.st_reg='A' AND a1.st_cie='N'";
                strSQLAux+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc";
                rstExiNomPedPre=stmExiNomPedPre.executeQuery(strSQLAux);
                if(rstExiNomPedPre.next()){
                    intCodEmpExiNomPedPre=rstExiNomPedPre.getInt("co_emp");
                    intCodLocExiNomPedPre=rstExiNomPedPre.getInt("co_loc");
                    intCodTipDocExiNomPedPre=rstExiNomPedPre.getInt("co_tipdoc");
                    intCodDocExiNomPedPre=rstExiNomPedPre.getInt("co_doc");
                    blnRes=true;
                }
                conExiNomPedPre.close();
                conExiNomPedPre=null;
                stmExiNomPedPre.close();
                stmExiNomPedPre=null;
                rstExiNomPedPre.close();
                rstExiNomPedPre=null;
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
     * Esta función selecciona las celdas de un JTable de acuerdo al tipo de selección.
     * El tipo de selección permite que se selecionen todas las celdas de una fila, de una columna
     * o ciertas celdas en particular. Esta selección es aparente ya que cuando se utilizan las
     * funciones "getSelectedRows()" y "getSelectedColumns()" para determinar las celdas seleccionadas
     * sólo se devuelve como celda seleccionada la celda actual cuando en realidad están selecionadas
     * todas las celdas de una columna(s) en particular o todas las celdas de una fila(s) en particular
     * Los métodos que permiten cortar, copiar, etc. utilizan dichos métodos para determinar las celdas
     * seleccionadas. Es por eso que antes de cortar, copiar, etc. se utiliza éste método para seleccionar
     * las celdas que deben estar seleccionadas de acuerdo al tipo de selección de celdas que tiene
     * establecido el JTable.
     * @return true: Si se pudieron seleccionar las celdas.
     * <BR>false: En el caso contrario.
     */
    private boolean seleccionarCel()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getRowSelectionAllowed())
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por celda.
                }
                else
                {
                    //Selección por fila.
                    tblDat.setColumnSelectionInterval(0, tblDat.getColumnCount()-1);
                }
            }
            else
            {
                if (tblDat.getColumnSelectionAllowed())
                {
                    //Selección por columna.
                    tblDat.setRowSelectionInterval(0, tblDat.getRowCount()-1);
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
/**
 * Función que permite obtener la cantidad en peso y en piezas
 * @param cantidad
 * @return 
 */
    
    private BigDecimal calcularPesoCantidadImportar(BigDecimal cantidad_Peso, BigDecimal pesoItem){
        BigDecimal bgdCanImp=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesTotKgr=new BigDecimal(0);
        BigDecimal bgdValRet_segTipImp=new BigDecimal(0);//pieza o tm
        try{
            bgdPesItm=pesoItem;
            if(chkCalRepPes.isSelected()){//tengo peso obtengo la pieza
                //para calcular cantidad en PIEZAS
                
                bgdPesTotKgr=cantidad_Peso;//es peso
                if(bgdPesTotKgr.compareTo(new BigDecimal("0"))>0)
                    bgdCanImp=bgdCanImp.add((bgdPesTotKgr.divide(bgdPesItm, 0, BigDecimal.ROUND_HALF_UP)));//cantidad que se calcula
                
                //retorna pieza
                bgdValRet_segTipImp=bgdCanImp;
            }
            else{
                //para calcular cantidad en TM
                bgdCanImp=cantidad_Peso;
                bgdPesTotKgr=bgdPesTotKgr.add((bgdCanImp.multiply(bgdPesItm)));//cantidad que se calcula
                
                //retorna bgdPesTotKgr
                bgdValRet_segTipImp=bgdPesTotKgr;
            }
            
            bgdValRet_segTipImp=new BigDecimal("" + Math.ceil(Double.parseDouble(""+bgdValRet_segTipImp))); //redondeando a mas
            
        }
        catch(Exception e){
            bgdValRet_segTipImp=BigDecimal.ZERO;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return bgdValRet_segTipImp;
    }
//    
//    private boolean getCostoMovimientoImportacion(){
//        boolean blnRes=true;
//        try{
//            
//        }
//        catch(Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    


    

    private boolean calcularDisponiblePedido(){
        boolean blnRes=true;
        BigDecimal bgdStkActIni=BigDecimal.ZERO;
        BigDecimal bgdStkImp=BigDecimal.ZERO;
        
        BigDecimal bgdStkPedEmb=BigDecimal.ZERO;//Pedido Embarcado
        BigDecimal bgdStkPedPre=BigDecimal.ZERO;//Pedido Previo        
        BigDecimal bgdStkPedLle=BigDecimal.ZERO;//Pedido Embarcado+Pedido Previo
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdStkActIni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()))    ;//Stock inicial
                bgdStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()))    ;//Stock importaciones
                bgdStkPedEmb=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinPedEmb-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinPedEmb-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinPedEmb-1)).toString()))    ;//Pedido Embarcado
                bgdStkPedPre=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinPedPre-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinPedPre-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinPedPre-1)).toString()))    ;//Pedido Previo
                bgdStkPedLle=bgdStkPedEmb.add(bgdStkPedPre);
                
                objTblMod.setValueAt(bgdStkPedLle, i, intNumColIniDis);//disponible
            }            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean calcularDisponiblePedido(int fila){
        boolean blnRes=true;
        BigDecimal bgdStkActIni=BigDecimal.ZERO;
        BigDecimal bgdStkImp=BigDecimal.ZERO;
        
        BigDecimal bgdStkPedEmb=BigDecimal.ZERO;//Pedido Embarcado
        BigDecimal bgdStkPedPre=BigDecimal.ZERO;//Pedido Previo        
        BigDecimal bgdStkPedLle=BigDecimal.ZERO;//Pedido Embarcado+Pedido Previo
        try{
            bgdStkActIni=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()))    ;//Stock inicial
            bgdStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()))    ;//Stock importaciones
            bgdStkPedEmb=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinPedEmb-1))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinPedEmb-1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinPedEmb-1)).toString()))    ;//Pedido Embarcado
            bgdStkPedPre=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinPedPre-1))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinPedPre-1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinPedPre-1)).toString()))    ;//Pedido Previo
            bgdStkPedLle=bgdStkPedEmb.add(bgdStkPedPre);

            objTblMod.setValueAt(bgdStkPedLle, fila, intNumColIniDis);//disponible
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    private boolean calcularVentaPromedioManualCalculado_1(){
        boolean blnRes=true;
        Object objProCalVta;
        Object objProManVta;
        BigDecimal bgdProCalVtaMen;
        BigDecimal bgdProManVtaMen;

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objProCalVta=objTblMod.getValueAt(i, intNumColIniProAnu);//Calculado
                objProManVta=objTblMod.getValueAt(i, (intNumColFinProAnu-1));//Manual
                
                bgdProCalVtaMen=new BigDecimal(objProCalVta==null?"0":(objProCalVta.toString().equals("")?"0":objProCalVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                bgdProManVtaMen=new BigDecimal(objProManVta==null?"0":(objProManVta.toString().equals("")?"0":objProManVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                
                objTblMod.setValueAt(bgdProCalVtaMen, i, intNumColIniVtaPro);//calculado1
                objTblMod.setValueAt(bgdProManVtaMen, i, (intNumColIniVtaPro+1));//manual
            }            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean calcularVentaPromedioManualCalculado_1(int fila){
        boolean blnRes=true;
        Object objProCalVta;
        Object objProManVta;
        BigDecimal bgdProCalVtaMen;
        BigDecimal bgdProManVtaMen;

        try{
            objProCalVta=objTblMod.getValueAt(fila, intNumColIniProAnu);//Calculado
            objProManVta=objTblMod.getValueAt(fila, (intNumColFinProAnu-1));//Manual

            bgdProCalVtaMen=new BigDecimal(objProCalVta==null?"0":(objProCalVta.toString().equals("")?"0":objProCalVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
            bgdProManVtaMen=new BigDecimal(objProManVta==null?"0":(objProManVta.toString().equals("")?"0":objProManVta.toString())).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);

            objTblMod.setValueAt(bgdProCalVtaMen, fila, intNumColIniVtaPro);//calculado1
            objTblMod.setValueAt(bgdProManVtaMen, fila, (intNumColIniVtaPro+1));//manual
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite calcular el valor de total manual y calculado de las Ventas Promedio
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularTotalVentaPromedio_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuCal=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                //calculado
                bgdProAnuCal=new BigDecimal(objTblMod.getValueAt(i, intNumColIniVtaPro)==null?"0":(objTblMod.getValueAt(i, intNumColIniVtaPro).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniVtaPro).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuCal=bgdSumProAnuCal.add(((bgdProAnuCal.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuCal=bgdSumProAnuCal.add(bgdCosUniGrp.multiply(bgdProAnuCal));
                
                //manual
                bgdProAnuMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdSumProAnuMan=bgdSumProAnuMan.add(((bgdProAnuMan.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdSumProAnuMan=bgdSumProAnuMan.add(bgdCosUniGrp.multiply(bgdProAnuMan));
                
                bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
                bgdProAnuCal=new BigDecimal(BigInteger.ZERO);
                bgdProAnuMan=new BigDecimal(BigInteger.ZERO);
            }
            objTblModTot.setValueAt(bgdSumProAnuCal, 1, intNumColIniVtaPro);
            objTblModTot.setValueAt(bgdSumProAnuMan, 1, (intNumColIniVtaPro+1));
            bgdSumProAnuCal=new BigDecimal(BigInteger.ZERO);
            bgdSumProAnuMan=new BigDecimal(BigInteger.ZERO);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalVentaPromedio_peso(){
        boolean blnRes=true;
        BigDecimal bgdCanPed=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);
        BigDecimal bgdPesItm_CanPed=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed1=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanPed2=new BigDecimal(0);
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0": objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                //Pedido 1
                bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, intNumColIniVtaPro)==null?"0":(objTblMod.getValueAt(i, intNumColIniVtaPro).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniVtaPro).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanPed1=bgdSum_PesItm_CanPed1.add(bgdPesItm_CanPed);
                bgdCanPed=new BigDecimal(0);
                bgdPesItm_CanPed=new BigDecimal(0);
                //Pedido 2
                bgdCanPed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+1)).toString()));
                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanPed=bgdCanPed;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanPed=bgdCanPed.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanPed2=bgdSum_PesItm_CanPed2.add(bgdPesItm_CanPed);
                //se setea los campos de calculo
                bgdCanPed=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanPed=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed1, objParSis.getDecimalesMostrar()), 0, intNumColIniVtaPro);//columna de promedio anual calculado del Pedido 1
            bgdSum_PesItm_CanPed1=new BigDecimal(0);
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanPed2, objParSis.getDecimalesMostrar()), 0, (intNumColIniVtaPro+1));//columna de promedio anual calculado del Pedido 2
            bgdSum_PesItm_CanPed2=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    private boolean setValMaxCal(){
        boolean blnRes=true;
        BigDecimal bgdMaxCal=BigDecimal.ZERO;
        BigDecimal bgdValCal=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                    if((j>=intNumColIniProAnu) && (j<(intNumColFinProAnu-2)) ){//Calculado 1 hasta calculado 4
                        bgdValCal=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if(bgdValCal.compareTo(bgdMaxCal)>0)
                            bgdMaxCal=bgdValCal;
                    }
                }                
                objTblMod.setValueAt((bgdMaxCal.compareTo(BigDecimal.ZERO)==0?null:bgdMaxCal), i, (intNumColFinProAnu-2));
                bgdMaxCal=BigDecimal.ZERO;
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    private boolean setValMaxCal(int fila){
        boolean blnRes=true;
        BigDecimal bgdMaxCal=BigDecimal.ZERO;
        BigDecimal bgdValCal=BigDecimal.ZERO;
        try{
            for(int j=intNumColIniProAnu; j<intNumColFinProAnu;j++){
                if((j>=intNumColIniProAnu) && (j<(intNumColFinProAnu-2)) ){//Calculado 1 hasta calculado 4
                    bgdValCal=new BigDecimal(objTblMod.getValueAt(fila, j)==null?"0":(objTblMod.getValueAt(fila, j).toString().equals("")?"0":objTblMod.getValueAt(fila, j).toString()));
                    if(bgdValCal.compareTo(bgdMaxCal)>0)
                        bgdMaxCal=bgdValCal;
                }
            }                
            objTblMod.setValueAt((bgdMaxCal.compareTo(BigDecimal.ZERO)==0?null:bgdMaxCal), fila, (intNumColFinProAnu-2));
            bgdMaxCal=BigDecimal.ZERO;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite ocultar la columna de stock histórico(inicial)
     */
    private boolean ocultaColumnaStkIni(){
        boolean blnRes=true;
        try{
            if(optHisAnu.isSelected()){
                for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_STK); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_STK); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_STK); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_STK); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
            }
            else{
                if(chkHisVtaStk.isSelected()){//mostrar
                    if(chkHisVta1.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta2.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_STK); i++)
                        {
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_STK); i++)
                        {
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta3.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta4.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_STK); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_STK); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_STK); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_STK); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_STK); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_STK); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
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
     * Función que permite ocultar columna de unidades según el filtro seleccionado
     */
    private boolean ocultaColumnaUnidades(){
        boolean blnRes=true;
        try{
            if(chkHisVtaUni.isSelected()){//mostrar
                if(chkHisVta1.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta2.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta3.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta4.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_UNI); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
            }
            else{//ocultar
                for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_UNI); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_UNI); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_UNI); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_UNI); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_UNI); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
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
     * Función que permite ocultar columna de veces según el filtro seleccionado
     */
    private boolean ocultaColumnaVeces(){
        boolean blnRes=true;
        try{
            if(chkHisVtaVec.isSelected())
            {//mostrar
                if(chkHisVta1.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta2.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta3.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                if(chkHisVta4.isSelected()){//mostrar
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_VEC); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                }
            }
            else
            {//ocultar
                for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_VEC); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_VEC); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_VEC); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_VEC); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                repaint();
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite ocultar la columna de stock histórico(inicial)
     */
    private boolean ocultaColumnaMeses(){
        boolean blnRes=true;
        try{
            if(optHisAnu.isSelected()){
                for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_MES); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_MES); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_MES); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
                for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_MES); i++){
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                    objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                    i=(i+INT_NUM_COL_HIS_VTA_MES);
                }
            }
            else{
                if(chkHisVtaMes.isSelected()){//mostrar
                    if(chkHisVta1.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta2.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta3.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    if(chkHisVta4.isSelected()){//mostrar
                        for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.removeSystemHiddenColumn(i, tblDat);
                            objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                    else{//ocultar
                        for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_MES); i++){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            i=(i+INT_NUM_COL_HIS_VTA_MES);
                        }
                    }
                }
                else{//ocultar
                    for(int i=(intNumColIniFecCor+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor+INT_NUM_COL_HIS_VTA_MES); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor2+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor2+INT_NUM_COL_HIS_VTA_MES); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor3+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor3+INT_NUM_COL_HIS_VTA_MES); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
                    }
                    for(int i=(intNumColIniFecCor4+INT_NUM_COL_HIS_VTA_MES); i<(intNumColFinFecCor4+INT_NUM_COL_HIS_VTA_MES); i++){
                        objTblMod.addSystemHiddenColumn(i, tblDat);
                        objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                        i=(i+INT_NUM_COL_HIS_VTA_MES);
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
     * Función que permite ocultar columnas de totales según el filtro seleccionado
     */
    private boolean ocultaColumnasSumaTotales(){
        boolean blnRes=true;
        try{
            
            if(optHisAnu.isSelected()){
                if(chkHisVta1.isSelected()){
                    for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        break;
                    }
                }
                if(chkHisVta2.isSelected()){
                    for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        break;
                    }
                }
                if(chkHisVta3.isSelected()){
                    for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        break;
                    }
                }
                if(chkHisVta4.isSelected()){
                    for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                        objTblMod.removeSystemHiddenColumn(i, tblDat);
                        objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                        break;
                    }
                }
            }
            else{//si es mensual
                if(chkHisVta1.isSelected()){//inicio de historial 1
                    //stock
                    if(chkHisVtaStk.isSelected()){
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_STK))
                            {
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_STK))
                            {
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }                    
                    //unidades
                    if(chkHisVtaUni.isSelected()){
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }                    
                    //veces
                    if(chkHisVtaVec.isSelected()){//
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }                    
                    //meses
                    if(chkHisVtaMes.isSelected()){
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                            if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                }
                else{//no está seleccionado
                    //stock    
                    for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                        if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_STK)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //unidades
                    for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                        if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //veces
                    for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                        if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_VEC)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //meses
                    for(int i=intNumColIniTot; i<intNumColFinTot; i++){
                        if(i==(intNumColIniTot+INT_NUM_COL_HIS_VTA_MES)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                }//fin de historial 1

                
                
                if(chkHisVta2.isSelected()){//inicio de historial 2
                    //stock
                    if(chkHisVtaStk.isSelected()){
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //unidades
                    if(chkHisVtaUni.isSelected()){
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //veces
                    if(chkHisVtaVec.isSelected()){//
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //meses
                    if(chkHisVtaMes.isSelected()){
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                            if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                }
                else{//no está seleccionado
                    //stock    
                    for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                        if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_STK)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //unidades
                    for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                        if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //veces
                    for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                        if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_VEC)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //meses
                    for(int i=intNumColIniTot2; i<intNumColFinTot2; i++){
                        if(i==(intNumColIniTot2+INT_NUM_COL_HIS_VTA_MES)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                }//fin de historial 2

                if(chkHisVta3.isSelected()){//inicio de historial 3
                    //stock
                    if(chkHisVtaStk.isSelected()){
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //unidades
                    if(chkHisVtaUni.isSelected()){
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //veces
                    if(chkHisVtaVec.isSelected()){//
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //meses
                    if(chkHisVtaMes.isSelected()){
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                            if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                }
                else{//no está seleccionado
                    //stock    
                    for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                        if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_STK)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //unidades
                    for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                        if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //veces
                    for(int i=(intNumColIniTot3+INT_NUM_COL_HIS_VTA_VEC); i<(intNumColFinTot3+INT_NUM_COL_HIS_VTA_VEC); i++){
                        if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_VEC)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //meses
                    for(int i=intNumColIniTot3; i<intNumColFinTot3; i++){
                        if(i==(intNumColIniTot3+INT_NUM_COL_HIS_VTA_MES)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                }//fin de historial 3


                if(chkHisVta4.isSelected()){//inicio de historial 4
                    //stock
                    if(chkHisVtaStk.isSelected()){
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_STK)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //unidades
                    if(chkHisVtaUni.isSelected()){
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //veces
                    if(chkHisVtaVec.isSelected()){//
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_VEC)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    
                    //meses
                    if(chkHisVtaMes.isSelected()){
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.removeSystemHiddenColumn(i, tblDat);
                                objTblModTot.removeSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                            if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_MES)){
                                objTblMod.addSystemHiddenColumn(i, tblDat);
                                objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                                break;
                            }
                        }
                    }
                }
                else{//no está seleccionado
                    //stock
                    for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                        if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_STK)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //unidades
                    for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                        if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //veces
                    for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                        if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_VEC)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                    //meses
                    for(int i=intNumColIniTot4; i<intNumColFinTot4; i++){
                        if(i==(intNumColIniTot4+INT_NUM_COL_HIS_VTA_MES)){
                            objTblMod.addSystemHiddenColumn(i, tblDat);
                            objTblModTot.addSystemHiddenColumn(i, tblTotDat);
                            break;
                        }
                    }
                }//fin de historial 4
                
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
   /**
    * Función que permite obtener el índice del mes que se desea reponer
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */ 
    /*
   private boolean getNumMesRep(){
       boolean blnRes=true;
       Statement stmNumMesRep;
       ResultSet rstNumMesRep;
       intNumColAddNumMesRep=0;
       int intNumMesRep=0, intNumMesAct=0;
       arlDatNumMesRep.clear();
       
       try{
           if(con!=null){
               intNumMesRep=Integer.parseInt(""+objUti.redondearBigDecimal(jspNumMesRep.getValue().toString(), 0));
               
               stmNumMesRep=con.createStatement();
               strSQL="";
               strSQL+=" SELECT (EXTRACT('month' FROM CAST(CURRENT_DATE AS DATE))+1) AS ne_numMesAct";
               rstNumMesRep=stmNumMesRep.executeQuery(strSQL);
               if(rstNumMesRep.next()){
                   intNumMesAct=rstNumMesRep.getInt("ne_numMesAct");
               }
               for(int i=0; i<intNumMesRep; i++){
                   arlRegNumMesRep=new ArrayList();
                   arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NUM_MES, intNumMesAct);
                   arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NOM_MES, getNomMes(intNumMesAct));
                   arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_EST_PRO, "");
                   arlDatNumMesRep.add(arlRegNumMesRep);
                   intNumMesAct++;
               }
               intNumColAddNumMesRep=arlDatNumMesRep.size();//numero de meses que se va a reponer
               
               rstNumMesRep.close();
               rstNumMesRep=null;
               stmNumMesRep.close();
               stmNumMesRep=null;
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
   */
    
   
   /**
    * Función que permite obtener el índice del mes que se desea reponer
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */ 
   private boolean getNumMesRep(){
       boolean blnRes=true;
       Statement stmNumMesRep;
       ResultSet rstNumMesRep;
       intNumColAddNumMesRep=0;
       int intNumMesRep=0; //, intNumMesAct=0;
       arlDatNumMesRep.clear();
       String strFecAct="CURRENT_DATE";
       //String strFecAct="CAST('2018-06-08' AS DATE)";  //para pruebas que me presente un factor diferente a cero
       
       try{
           if(con!=null){
                intNumMesRep=Integer.parseInt(""+objUti.redondearBigDecimal(jspNumMesRep.getValue().toString(), 0));

                stmNumMesRep=con.createStatement();
                for(int i=0; i<intNumMesRep; i++){
                    strSQL="";
                    strSQL+=" SELECT d1.fe_hoy, d1.ne_ani";
                    strSQL+=" , CAST((CASE WHEN d1.ne_numMesAct<10 THEN '0'||d1.ne_numMesAct ELSE ''||d1.ne_numMesAct END) AS CHARACTER VARYING) AS ne_numMesAct";
                    strSQL+=" , CAST((CASE WHEN d1.ne_dia<10 THEN '0'||d1.ne_dia ELSE ''||d1.ne_dia END) AS CHARACTER VARYING) AS ne_dia";
                    strSQL+=" , d1.nd_facRep";
                    strSQL+=" FROM(";
                    strSQL+="       SELECT c1.fe_hoy";
                    strSQL+="       , CASE WHEN c1.ne_numMesAct=13 THEN (c1.ne_ani+1) ELSE c1.ne_ani END AS ne_ani";
                    strSQL+="       , CASE WHEN c1.ne_numMesAct=13 THEN 1 ELSE c1.ne_numMesAct END AS ne_numMesAct";
                    strSQL+="       , c1.ne_dia";
                    strSQL+="       , CASE WHEN (c1.ne_dia>=1  AND c1.ne_dia<=3)  THEN 1";
                    strSQL+="       	   WHEN (c1.ne_dia>=4  AND c1.ne_dia<=10) THEN 0.75";
                    strSQL+="              WHEN (c1.ne_dia>=11 AND c1.ne_dia<=18) THEN 0.5";
                    strSQL+="              WHEN (c1.ne_dia>=19 AND c1.ne_dia<=25) THEN 0.25";
                    strSQL+="              WHEN (c1.ne_dia>=26 AND c1.ne_dia<=31) THEN 0      END AS nd_facRep";
                    strSQL+="       FROM(";
                    strSQL+="              SELECT b1.fe_hoy";
                    strSQL+="              , (EXTRACT('year' FROM CAST(b1.fe_hoy AS DATE))) AS ne_ani";
                    if(i==0){//para el mes actual es el dia actual
                        strSQL+=" 		   , (EXTRACT('month' FROM CAST(b1.fe_hoy AS DATE))+" + i + ") AS ne_numMesAct";
                        strSQL+="		   , (EXTRACT('day' FROM CAST(b1.fe_hoy AS DATE))) AS ne_dia";
                        strSQL+="		   FROM(";
                        strSQL+=" 			SELECT " + strFecAct + " AS fe_hoy";//CAST('2018-06-29' AS DATE)
                    }
                    else{//para el mes siguiente el dia seria 01
                        strSQL+=" 		   , (EXTRACT('month' FROM CAST(b1.fe_hoy AS DATE))+1) AS ne_numMesAct";
                        strSQL+="		   , 1 AS ne_dia";
                        strSQL+="		   FROM(";
                        strSQL+=" 			SELECT CAST('" + strFecAct + "' AS DATE) AS fe_hoy";
                    }
                    strSQL+=" 		   ) AS b1";
                    strSQL+="       ) AS c1";
                    strSQL+=" ) AS d1";
                    rstNumMesRep=stmNumMesRep.executeQuery(strSQL);
                    if(rstNumMesRep.next()){
                        //intNumMesAct=rstNumMesRep.getInt("ne_numMesAct");

                        arlRegNumMesRep=new ArrayList();
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NUM_ANI, rstNumMesRep.getString("ne_ani"));
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NUM_MES, rstNumMesRep.getString("ne_numMesAct"));
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NOM_MES, getNomMes(rstNumMesRep.getString("ne_numMesAct")));
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_FAC_REP, rstNumMesRep.getString("nd_facRep"));
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_EST_PRO, "");
                        arlRegNumMesRep.add(INT_ARL_NUM_MES_REP_NUM_COL, "");
                        arlDatNumMesRep.add(arlRegNumMesRep);
                        //intNumMesAct++;
                        
                        strFecAct="" + rstNumMesRep.getString("ne_ani") + "-" + rstNumMesRep.getString("ne_numMesAct") + "-" + rstNumMesRep.getString("ne_dia");
                    }
                    rstNumMesRep.close();
                    rstNumMesRep=null;
               }
               intNumColAddNumMesRep=arlDatNumMesRep.size();//numero de meses que se va a reponer
               stmNumMesRep.close();
               stmNumMesRep=null;
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
     * Función que permite obtener el nombre del mes según el número de índice del mes seleccionado
     * @param intNumMesAct Índice del mes que se desea obtener
     * @return Nombre del mes que se desea obtener
     */
    private String getNomMes(String strNumMesAct){
        String strNomMes="";
        try{            
            switch(strNumMesAct){
                case "01":
                    strNomMes="Enero";
                    break;
                case "02":
                    strNomMes="Febrero";
                    break;
                case "03":
                    strNomMes="Marzo";
                    break;
                case "04":
                    strNomMes="Abril";
                    break;
                case "05":
                    strNomMes="Mayo";
                    break;
                case "06":
                    strNomMes="Junio";
                    break;
                case "07":
                    strNomMes="Julio";
                    break;
                case "08":
                    strNomMes="Agosto";
                    break;
                case "09":
                    strNomMes="Septiembre";
                    break;
                case "10":
                    strNomMes="Octubre";
                    break;
                case "11":
                    strNomMes="Noviembre";
                    break;
                case "12":
                    strNomMes="Diciembre";
                    break;
                default:
                    strNomMes="";
                    break;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strNomMes;
    }
       
   
    
    /**
     * Función que permite adicionar el número de columnas según el número de meses a reponer escogido por el usuario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean agregarColumnasNumMesRep(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp1=null;
        ZafTblHeaColGrp objTblHeaColGrp2=null;
        String strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1;
        intNumColIniNumMesRep=intNumColFinChkItmEli;
        try{
            objTblCelRenLblNumMesRep=new ZafTblCelRenLbl();
            objTblCelRenLblNumMesRep.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumMesRep.setTipoFormato(objTblCelRenLblNumMesRep.INT_FOR_NUM);
            objTblCelRenLblNumMesRep.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblNumMesRep.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                int intFilSel=-1;
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    intFilSel=objTblCelRenLblNumMesRep.getRowRender();
                    if(intFilSel>0){
                        colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                        strEstItmEli=objTblMod.getValueAt(intFilSel, intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(intFilSel, intNumColIniEstItmEli).toString();
                        if (strEstItmEli.equals("S")){
                            objTblCelRenLblNumMesRep.setBackground(colFonColCru);
                        }
                        else{
                            objTblCelRenLblNumMesRep.setBackground(Color.WHITE);
                        }
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddNumMesRep*INT_NUM_COL_REP); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Disp.":(p==1?"Vtas.":"Lleg."));
                //INT_ARL_NUM_MES_REP_NUM_COL
                objUti.setStringValueAt(arlDatNumMesRep, f, INT_ARL_NUM_MES_REP_NUM_COL, "" + (intNumColIniNumMesRep+i));//Nuevo               
                
                tbc=new javax.swing.table.TableColumn(intNumColIniNumMesRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblNumMesRep);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if(i==0){
                    objTblHeaColGrp1=new ZafTblHeaColGrp("Reposición por Número de Meses");
                    objTblHeaColGrp1.setHeight(16);
                }

                if (!strMes.equals(objUti.getStringValueAt(arlDatNumMesRep, f, INT_ARL_NUM_MES_REP_NUM_MES).toString())){
                    objTblHeaColGrp2=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatNumMesRep, f, INT_ARL_NUM_MES_REP_NOM_MES));
                    objTblHeaColGrp2.setHeight(16*2);
                    strMes=objUti.getStringValueAt(arlDatNumMesRep, f, INT_ARL_NUM_MES_REP_NUM_MES).toString();
                    objTblHeaColGrp1.add(objTblHeaColGrp2);
                }
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp1);
                objTblHeaColGrp2.add(tbc);
                
                if(p==INT_NUM_COL_REP_LLE){
                    p=0;
                }
                else{
                    p++;
                }
            }
            intNumColFinNumMesRep=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp1=null;
            objTblHeaColGrp2=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite adicionar el número de columnas en la tabla de totales según el número de meses a reponer escogido por el usuario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean agregarColumnasNumMesRepTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddNumMesRep*INT_NUM_COL_REP); i++){
                String strSubTit=("Rep.");
                tbc=new javax.swing.table.TableColumn(intNumColIniNumMesRep+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
   /**
    * Función que permite obtener el índice del mes que se desea proyectar
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */ 
   private boolean getNumMesPry(){
       boolean blnRes=true;
       Statement stmNumMesPry;
       ResultSet rstNumMesPry;
       intNumColAddNumMesPry=0;
       int intNumMesPry=0; //, intNumMesAct=0;
       arlDatNumMesPry.clear();
       String strFecAct="CURRENT_DATE";
       int intUltNumMesRep=-1;
       int intUltNumAniRep=-1;
       try{
           if(con!=null){
                intNumMesPry=Integer.parseInt(""+objUti.redondearBigDecimal(jspNumMesIniRep.getValue().toString(), 0));
                
                for(int i=(arlDatNumMesRep.size()-1); i<arlDatNumMesRep.size(); i++){
                    intUltNumMesRep=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_MES);
                    intUltNumAniRep=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_ANI);
                }

                stmNumMesPry=con.createStatement();
                for(int i=0; i<intNumMesPry; i++){
                    strSQL="";
                    strSQL+=" SELECT CAST( (a1.ne_ani || '-' || a1.ne_numMesAct || '-' || a1.ne_dia) AS DATE)";
                    strSQL+=" , a1.ne_ani, a1.ne_numMesAct, a1.ne_dia";
                    strSQL+=" FROM(";
                    strSQL+=" 	 SELECT d1.fe_hoy, d1.ne_ani";
                    strSQL+=" 	 , CAST((CASE WHEN d1.ne_numMesAct<10 THEN '0'||d1.ne_numMesAct ELSE ''||d1.ne_numMesAct END) AS CHARACTER VARYING) AS ne_numMesAct";
                    strSQL+=" 	 , CAST((CASE WHEN d1.ne_dia<10 THEN '0'||d1.ne_dia ELSE ''||d1.ne_dia END) AS CHARACTER VARYING) AS ne_dia";
                    strSQL+=" 	 FROM(";
                    strSQL+=" 	       SELECT c1.fe_hoy";
                    strSQL+=" 	       , CASE WHEN c1.ne_numMesAct=13 THEN (c1.ne_ani+1) ELSE c1.ne_ani END AS ne_ani";
                    strSQL+=" 	       , CASE WHEN c1.ne_numMesAct=13 THEN 1 ELSE c1.ne_numMesAct END AS ne_numMesAct";
                    strSQL+=" 	       , c1.ne_dia";
                    strSQL+=" 	       FROM(";
                    strSQL+=" 		      SELECT b1.fe_hoy";
                    strSQL+=" 		      , (EXTRACT('year' FROM CAST(b1.fe_hoy AS DATE))) AS ne_ani";
                    strSQL+=" 				   , (EXTRACT('month' FROM CAST(b1.fe_hoy AS DATE))+" + (i+1) + ") AS ne_numMesAct";
                    strSQL+=" 				   , 1 AS ne_dia";
                    strSQL+=" 				   FROM(";
                    strSQL+=" 					SELECT CAST('" + intUltNumAniRep + "-" + intUltNumMesRep + "-01' AS DATE) AS fe_hoy";
                    strSQL+=" 			   ) AS b1";
                    strSQL+=" 	       ) AS c1";
                    strSQL+=" 	 ) AS d1";
                    strSQL+=" ) AS a1";
                    rstNumMesPry=stmNumMesPry.executeQuery(strSQL);
                    if(rstNumMesPry.next()){
                        //intNumMesAct=rstNumMesPry.getInt("ne_numMesAct");
                        arlRegNumMesPry=new ArrayList();
                        arlRegNumMesPry.add(INT_ARL_NUM_MES_PRY_NUM_ANI, rstNumMesPry.getString("ne_ani"));
                        arlRegNumMesPry.add(INT_ARL_NUM_MES_PRY_NUM_MES, rstNumMesPry.getString("ne_numMesAct"));
                        arlRegNumMesPry.add(INT_ARL_NUM_MES_PRY_NOM_MES, getNomMes(rstNumMesPry.getString("ne_numMesAct")));
                        arlRegNumMesPry.add(INT_ARL_NUM_MES_PRY_EST_PRO, "");
                        arlRegNumMesPry.add(INT_ARL_NUM_MES_PRY_NUM_COL, "");
                        arlDatNumMesPry.add(arlRegNumMesPry);
                        //intNumMesAct++;
                        strFecAct="" + rstNumMesPry.getString("ne_ani") + "-" + rstNumMesPry.getString("ne_numMesAct") + "-" + rstNumMesPry.getString("ne_dia");
                    }
                    rstNumMesPry.close();
                    rstNumMesPry=null;
               }
               intNumColAddNumMesPry=arlDatNumMesPry.size();//numero de meses que se va a proyectar
               stmNumMesPry.close();
               stmNumMesPry=null;
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
     * Función que permite adicionar el número de columnas según el número de meses a proyectar escogido por el usuario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean agregarColumnasNumMesPry(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrp1=null;
        ZafTblHeaColGrp objTblHeaColGrp2=null;
        String strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1;
        intNumColIniNumMesPry=intNumColFinNumMesRep;
        try{
            objTblCelRenLblNumMesPry=new ZafTblCelRenLbl();
            objTblCelRenLblNumMesPry.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumMesPry.setTipoFormato(objTblCelRenLblNumMesPry.INT_FOR_NUM);
            objTblCelRenLblNumMesPry.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblNumMesPry.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                    strEstItmEli=objTblMod.getValueAt(objTblCelRenLblNumMesPry.getRowRender(), intNumColIniEstItmEli)==null?"":objTblMod.getValueAt(objTblCelRenLblNumMesPry.getRowRender(), intNumColIniEstItmEli).toString();
                    if (strEstItmEli.equals("S")){
                        objTblCelRenLblNumMesPry.setBackground(colFonColCru);
                    }
                    else{
                        objTblCelRenLblNumMesPry.setBackground(Color.WHITE);
                    }
                }
            });
            
            for (int i=0; i<(intNumColAddNumMesPry*INT_NUM_COL_PRY); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Dis.Ini.":"Mes");
                //objUti.setStringValueAt(arlDatNumMesPry, i, INT_ARL_NUM_MES_PRY_NUM_COL, "" + (intNumColIniNumMesPry+i));//Nuevo
                tbc=new javax.swing.table.TableColumn(intNumColIniNumMesPry+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblNumMesPry);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrp1=new ZafTblHeaColGrp("Saldo Inicial");
                    objTblHeaColGrp1.setHeight(16);
                }

                if (!strMes.equals(objUti.getStringValueAt(arlDatNumMesPry, f, INT_ARL_NUM_MES_PRY_NUM_MES).toString())){
                    objTblHeaColGrp2=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatNumMesPry, f, INT_ARL_NUM_MES_PRY_NOM_MES));
                    objTblHeaColGrp2.setHeight(16*2);
                    strMes=objUti.getStringValueAt(arlDatNumMesPry, f, INT_ARL_NUM_MES_PRY_NUM_MES).toString();
                    objTblHeaColGrp1.add(objTblHeaColGrp2);
                }
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp1);
                objTblHeaColGrp2.add(tbc);
                
                if(p==INT_NUM_COL_PRY_MES){
                    p=0;
                }
                else{
                    p++;
                }
            }            
            
            intNumColFinNumMesPry=objTblMod.getColumnCount();
            tcmAux=null;
            objTblHeaGrp=null;
            objTblHeaColGrp1=null;
            objTblHeaColGrp2=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite adicionar el número de columnas en la tabla de totales según el número de meses a proyectar escogido por el usuario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean agregarColumnasNumMesPryTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        try{
            objTblCelRenLblGrl=new ZafTblCelRenLbl();
            objTblCelRenLblGrl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblGrl.setTipoFormato(objTblCelRenLblGrl.INT_FOR_NUM);
            objTblCelRenLblGrl.setFormatoNumerico("###,###,##0",false,true);

            for (int i=0; i<(intNumColAddNumMesPry*INT_NUM_COL_PRY); i++){
                String strSubTit=("Pry.");
                tbc=new javax.swing.table.TableColumn(intNumColIniNumMesPry+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblGrl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTotDat, tbc);                
            }
            objTblCelRenLblGrl=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    /**
     * Función que permite obtener el total del stock actual y de importación
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularDisponibleStock(){
        boolean blnRes=true;
        BigDecimal bgdStkActIni=BigDecimal.ZERO;
        BigDecimal bgdStkImp=BigDecimal.ZERO;
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdStkActIni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStk)==null?"0":(objTblMod.getValueAt(i, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStk).toString()))    ;//Stock inicial
                bgdStkImp=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+1)).toString()))    ;//Stock importaciones
                objTblMod.setValueAt((bgdStkActIni.add(bgdStkImp)), i, (intNumColIniStk+2));//disponible
            }            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite obtener el total del stock actual y de importación
     * @param fila la fila a calcular
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */     
    private boolean calcularDisponibleStock(int fila){
        boolean blnRes=true;
        BigDecimal bgdStkActIni=BigDecimal.ZERO;
        BigDecimal bgdStkImp=BigDecimal.ZERO;
        
        try{
            bgdStkActIni=new BigDecimal(objTblMod.getValueAt(fila, intNumColIniStk)==null?"0":(objTblMod.getValueAt(fila, intNumColIniStk).toString().equals("")?"0":objTblMod.getValueAt(fila, intNumColIniStk).toString()))    ;//Stock inicial
            bgdStkImp=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+1))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+1)).toString()))    ;//Stock importaciones
            objTblMod.setValueAt((bgdStkActIni.add(bgdStkImp)), fila, (intNumColIniStk+2));//disponible
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    //intNumColIniVtaPro
    /**
     * Función que permite obtener el valor calculado mensual de los valores máximos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularCalMenMax(){
        boolean blnRes=true;
        BigDecimal bgdValMax=BigDecimal.ZERO;
        BigDecimal bgdCalMenMax=BigDecimal.ZERO;
        
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdValMax=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinProAnu-2))==null?"0":(objTblMod.getValueAt(i, (intNumColFinProAnu-2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinProAnu-2)).toString()));
                //bgdCalMenMax=bgdValMax.divide(BigDecimal.ONE, 0, BigDecimal.ROUND_HALF_UP);
                
                bgdCalMenMax=bgdValMax.divide(new BigDecimal("12"), 0, BigDecimal.ROUND_UP);
                
                objTblMod.setValueAt(bgdCalMenMax, i, (intNumColIniVtaPro+2));//disponible
            }            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   
    
    /**
     * Función que permite obtener el valor calculado mensual de los valores máximos
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean calcularCalMenMax(int fila){
        boolean blnRes=true;
        BigDecimal bgdValMax=BigDecimal.ZERO;
        BigDecimal bgdCalMenMax=BigDecimal.ZERO;
        
        try{
            bgdValMax=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinProAnu-2))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinProAnu-2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinProAnu-2)).toString()));
            //bgdCalMenMax=bgdValMax.divide(BigDecimal.ONE, 0, BigDecimal.ROUND_HALF_UP);

            bgdCalMenMax=bgdValMax.divide(new BigDecimal("12"), 0, BigDecimal.ROUND_UP);

            objTblMod.setValueAt(bgdCalMenMax, fila, (intNumColIniVtaPro+2));//disponible
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    /**
     * Función que permite calcular los totales de peso de las columna de totales de los históricos de ventas
     * @return true Si se pudo realizar la operaciòn
     * <BR> false Caso contrario
     */
    private boolean calcularColumnaSumaTotales_peso(){
        boolean blnRes=true;
        BigDecimal bgdPes=BigDecimal.ZERO;
        BigDecimal bgdUniMed=BigDecimal.ZERO;
        BigDecimal bgdColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdColTotPes4=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes4=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdPes=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                //Totales 1
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                if(chkMosDatKil.isSelected())
                    bgdColTotPes1=bgdColTotPes1.add(bgdUniMed);
                else
                    bgdColTotPes1=bgdPes.multiply(bgdUniMed);
                bgdSumColTotPes1=bgdSumColTotPes1.add(bgdColTotPes1);

                //Totales 2
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                if(chkMosDatKil.isSelected())
                    bgdColTotPes2=bgdUniMed;
                else
                    bgdColTotPes2=bgdPes.multiply(bgdUniMed);
                bgdSumColTotPes2=bgdSumColTotPes2.add(bgdColTotPes2);
                
                //Totales 3
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                if(chkMosDatKil.isSelected())
                    bgdColTotPes3=bgdUniMed;
                else
                    bgdColTotPes3=bgdPes.multiply(bgdUniMed);
                bgdSumColTotPes3=bgdSumColTotPes3.add(bgdColTotPes3);
                
                //Totales 4
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                if(chkMosDatKil.isSelected())
                    bgdColTotPes4=bgdUniMed;
                else
                    bgdColTotPes4=bgdPes.multiply(bgdUniMed);
                bgdSumColTotPes4=bgdSumColTotPes4.add(bgdColTotPes4);
                
                bgdColTotPes1=BigDecimal.ZERO;
                bgdColTotPes2=BigDecimal.ZERO;
                bgdColTotPes3=BigDecimal.ZERO;
                bgdColTotPes4=BigDecimal.ZERO;
                
                
                
            }
            objTblModTot.setValueAt(bgdSumColTotPes1, 0, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes2, 0, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes3, 0, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes4, 0, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI));
            
            bgdSumColTotPes1=BigDecimal.ZERO;
            bgdSumColTotPes2=BigDecimal.ZERO;
            bgdSumColTotPes3=BigDecimal.ZERO;
            bgdSumColTotPes4=BigDecimal.ZERO;
            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite calcular los totales de costo de las columna de totales de los históricos de ventas
     * @return true Si se pudo realizar la operaciòn
     * <BR> false Caso contrario
     */
    private boolean calcularColumnaSumaTotales_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=BigDecimal.ZERO;
        BigDecimal bgdPesItm=BigDecimal.ZERO;
        BigDecimal bgdUniMed=BigDecimal.ZERO;
        BigDecimal bgdColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdColTotPes4=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes4=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                //Totales 1
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdColTotPes1=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdColTotPes1=bgdCosUniGrp.multiply(bgdUniMed);
                
                bgdSumColTotPes1=bgdSumColTotPes1.add(bgdColTotPes1);
                bgdColTotPes1=BigDecimal.ZERO;
                //Totales 2
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdColTotPes2=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdColTotPes2=bgdCosUniGrp.multiply(bgdUniMed);
                
                bgdSumColTotPes2=bgdSumColTotPes2.add(bgdColTotPes2);
                bgdColTotPes2=BigDecimal.ZERO;
                //Totales 3
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString()));
                
                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdColTotPes3=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdColTotPes3=bgdCosUniGrp.multiply(bgdUniMed);
                
                bgdSumColTotPes3=bgdSumColTotPes3.add(bgdColTotPes3);
                bgdColTotPes3=BigDecimal.ZERO;
                //Totales 4
                //unidades
                bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString()));

                if(chkMosDatKil.isSelected()){
                    if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                        bgdColTotPes4=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));
                }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdColTotPes4=bgdCosUniGrp.multiply(bgdUniMed);
                
                bgdSumColTotPes4=bgdSumColTotPes4.add(bgdColTotPes4);
                bgdColTotPes4=BigDecimal.ZERO;
                
            }
            objTblModTot.setValueAt(bgdSumColTotPes1, 1, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes2, 1, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes3, 1, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes4, 1, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI));
            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Función que permite calcular los totales de costo de las columna de totales de los históricos de ventas
     * @return true Si se pudo realizar la operaciòn
     * <BR> false Caso contrario
     */
    private boolean calcularColumnaSumaTotales_costo(int fila){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=BigDecimal.ZERO;
        BigDecimal bgdPesItm=BigDecimal.ZERO;
        BigDecimal bgdUniMed=BigDecimal.ZERO;
        BigDecimal bgdColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdColTotPes4=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes4=BigDecimal.ZERO;
        int i=fila;
        try{
            
            bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
            bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
            //Totales 1
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString()));

            if(chkMosDatKil.isSelected())
                bgdColTotPes1=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
            else
                bgdColTotPes1=bgdCosUniGrp.multiply(bgdUniMed);

            bgdSumColTotPes1=bgdSumColTotPes1.add(bgdColTotPes1);

            //Totales 2
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString()));

            if(chkMosDatKil.isSelected())
                bgdColTotPes2=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
            else
                bgdColTotPes2=bgdCosUniGrp.multiply(bgdUniMed);

            bgdSumColTotPes2=bgdSumColTotPes2.add(bgdColTotPes2);

            //Totales 3
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString()));

            if(chkMosDatKil.isSelected())
                bgdColTotPes3=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
            else
                bgdColTotPes3=bgdCosUniGrp.multiply(bgdUniMed);

            bgdSumColTotPes3=bgdSumColTotPes3.add(bgdColTotPes3);

            //Totales 4
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString()));

            if(chkMosDatKil.isSelected())
                bgdColTotPes4=((bgdUniMed.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp));//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
            else
                bgdColTotPes4=bgdCosUniGrp.multiply(bgdUniMed);

            bgdSumColTotPes4=bgdSumColTotPes4.add(bgdColTotPes4);
            
            objTblModTot.setValueAt(bgdSumColTotPes1, 1, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes2, 1, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes3, 1, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes4, 1, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI));
            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    /**
     * Función que permite calcular los totales de peso de las columna de totales de los históricos de ventas por fila seleccionada
     * @return true Si se pudo realizar la operaciòn
     * <BR> false Caso contrario
     */
    private boolean calcularColumnaSumaTotales_peso(int fila){
        boolean blnRes=true;
        BigDecimal bgdPes=BigDecimal.ZERO;
        BigDecimal bgdUniMed=BigDecimal.ZERO;
        BigDecimal bgdColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes1=BigDecimal.ZERO;
        BigDecimal bgdColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes2=BigDecimal.ZERO;
        BigDecimal bgdColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes3=BigDecimal.ZERO;
        BigDecimal bgdColTotPes4=BigDecimal.ZERO;
        BigDecimal bgdSumColTotPes4=BigDecimal.ZERO;
        try{
            bgdPes=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));
            //Totales 1
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI)).toString()));
            bgdColTotPes1=bgdPes.multiply(bgdUniMed);
            bgdSumColTotPes1=bgdSumColTotPes1.add(bgdColTotPes1);

            //Totales 2
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI)).toString()));
            bgdColTotPes2=bgdPes.multiply(bgdUniMed);
            bgdSumColTotPes2=bgdSumColTotPes2.add(bgdColTotPes2);

            //Totales 3
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI)).toString()));
            bgdColTotPes3=bgdPes.multiply(bgdUniMed);
            bgdSumColTotPes3=bgdSumColTotPes3.add(bgdColTotPes3);

            //Totales 4
            //unidades
            bgdUniMed=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI)).toString()));
            bgdColTotPes4=bgdPes.multiply(bgdUniMed);
            bgdSumColTotPes4=bgdSumColTotPes4.add(bgdColTotPes4);
                
            objTblModTot.setValueAt(bgdSumColTotPes1, 0, (intNumColIniTot+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes2, 0, (intNumColIniTot2+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes3, 0, (intNumColIniTot3+INT_NUM_COL_HIS_VTA_UNI));
            objTblModTot.setValueAt(bgdSumColTotPes4, 0, (intNumColIniTot4+INT_NUM_COL_HIS_VTA_UNI));
            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private boolean calcularColumnaRep_Dis(){
        boolean blnRes=true;
        BigDecimal bgdTotStkDis=BigDecimal.ZERO;
        
        BigDecimal bgdStkDisAnt=BigDecimal.ZERO;
        BigDecimal bgdVtaAnt=BigDecimal.ZERO;
        BigDecimal bgdLleAnt=BigDecimal.ZERO;
        
        BigDecimal bgdTotStkDisRep=BigDecimal.ZERO;
        int intNumMesRepNumColAct=-1;
        int intNumMesRepNumColAnt=-1;
        
        BigDecimal bgdFacRep=BigDecimal.ONE;// se comento la asignacion de los valores porque en disponible no se debe aplicar factor. , sino cambiar este valor a 1
        
        //STOCK
        BigDecimal bgdStkDis=BigDecimal.ZERO;
        BigDecimal bgdVta=BigDecimal.ZERO;
        BigDecimal bgdCalMen=BigDecimal.ZERO;
        
        //Llegada
        String strEstPro="";
        int intCodItmTbl=-1, intCodItmArl=-1;
        BigDecimal bgdCanRep=BigDecimal.ZERO;
        int intColMesRep=-1;
        
        int intArlNumMesRep1=-1;
        int intArlNumMesRep2=-1;
        
        //en Kilos
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdValPesItmKil=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                
                for(int k=0; k<arlDatNumMesRep.size(); k++){
                    //bgdFacRep=objUti.getBigDecimalValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_FAC_REP);
                    intNumMesRepNumColAct=objUti.getIntValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_NUM_COL);
                    intArlNumMesRep1=objUti.getIntValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_NUM_MES);
                    
                    if(k==0){//primera interaccion
                        bgdTotStkDis=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+2)).toString()));
                        bgdTotStkDisRep=bgdTotStkDis.multiply(bgdFacRep);
                    }
                    else{//mas de una interaccion
                        //capturar los datos del mes anterior
                        intNumMesRepNumColAnt=objUti.getIntValueAt(arlDatNumMesRep, (k-1), INT_ARL_NUM_MES_REP_NUM_COL);
                        
                        for(int m=intNumColIniNumMesRep; m<intNumColFinNumMesRep; m++){
                            if(m==intNumMesRepNumColAnt){
                                //bgdStkDisAnt=new BigDecimal(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS))==null?"0":(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS)).toString().equals("")?"0":objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS)).toString()));
                                bgdStkDisAnt=new BigDecimal(objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_LLE))==null?"0":(objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_LLE)).toString().equals("")?"0":objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_LLE)).toString()));
                                //bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA))==null?"0":(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA)).toString().equals("")?"0":objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA)).toString()));
                                bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_VTA))==null?"0":(objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_VTA)).toString().equals("")?"0":objTblMod.getValueAt(i, (m-INT_NUM_COL_REP_VTA)).toString()));
                                bgdLleAnt=new BigDecimal(objTblMod.getValueAt(i, m)==null?"0":(objTblMod.getValueAt(i, m).toString().equals("")?"0":objTblMod.getValueAt(i, m).toString()));
                                bgdTotStkDis=bgdStkDisAnt.subtract(bgdVtaAnt).add(bgdLleAnt);
                                bgdTotStkDisRep=bgdTotStkDis;
                                break;
                            }
                        }
                    }

                    objTblMod.setValueAt(bgdTotStkDisRep, i, (intNumMesRepNumColAct-INT_NUM_COL_REP_LLE));//disponible

                    //VENTA*****
                    bgdCalMen=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+2)).toString()));
                    //Disponible
                    bgdStkDis=bgdTotStkDisRep;
                    //Vtas
                    if(bgdStkDis.compareTo(bgdCalMen)>0){
                        bgdVta=bgdCalMen;
                    }
                    else{
                        bgdVta=bgdStkDis;
                    }                    
                    
//                    if(chkMosDatKil.isSelected())
//                        objTblMod.setValueAt((bgdVta.multiply(bgdValPesItmKil)), i, (intNumMesRepNumColAct-INT_NUM_COL_REP_VTA));//ventas
//                    else
                        objTblMod.setValueAt(bgdVta, i, (intNumMesRepNumColAct-INT_NUM_COL_REP_VTA));//ventas

                    //LLEGADAS
                    //aqui
                    intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM)==null?"-1":(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals("")?"-1":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM).toString()));
                    
                    for(int j=0; j<arlDatMesRep.size(); j++){
                        strEstPro=objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO)==null?"":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO);
                        intColMesRep=Integer.parseInt(objUti.getObjectValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL)==null?"0":(objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL).equals("")?"0":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL)  ));
                        intArlNumMesRep2=Integer.parseInt(objUti.getObjectValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES)==null?"0":(objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES).equals("")?"0":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES)  ));
                        
                        intCodItmArl=objUti.getIntValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_ITM);
                        
                        bgdCanRep=objUti.getBigDecimalValueAt(arlDatMesRep, j, INT_ARL_MES_REP_CAN);
                        
                        if(intCodItmTbl==intCodItmArl){
                            if(intArlNumMesRep1==intArlNumMesRep2){
                                if(chkMosDatKil.isSelected())
                                    objTblMod.setValueAt((bgdCanRep.multiply(bgdValPesItmKil)), i, intColMesRep);
                                else
                                    objTblMod.setValueAt(bgdCanRep, i, intColMesRep);
                                
                                objUti.setStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO, "P");
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
        
    private boolean calcularColumnaRep_Dis(int fila){
        boolean blnRes=true;
        BigDecimal bgdTotStkDis=BigDecimal.ZERO;
        
        BigDecimal bgdStkDisAnt=BigDecimal.ZERO;
        BigDecimal bgdVtaAnt=BigDecimal.ZERO;
        BigDecimal bgdLleAnt=BigDecimal.ZERO;
        
        BigDecimal bgdTotStkDisRep=BigDecimal.ZERO;
        int intNumMesRepNumColAct=-1;
        int intNumMesRepNumColAnt=-1;
        
        BigDecimal bgdFacRep=BigDecimal.ONE;// se comento la asignacion de los valores porque en disponible no se debe aplicar factor, sino cambiar este valor a 1
        
        //STOCK
        BigDecimal bgdStkDis=BigDecimal.ZERO;
        BigDecimal bgdVta=BigDecimal.ZERO;
        BigDecimal bgdCalMen=BigDecimal.ZERO;
        
        //Llegada
        String strEstPro="";
        int intCodItmTbl=-1, intCodItmArl=-1;
        BigDecimal bgdCanRep=BigDecimal.ZERO;
        int intColMesRep=-1;
        
        int intArlNumMesRep1=-1;
        int intArlNumMesRep2=-1;
        
        //en Kilos
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);

        try{
            bgdValPesItmKil=new BigDecimal(objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_PES_KG).toString()));
            for(int k=0; k<arlDatNumMesRep.size(); k++){
                bgdFacRep=objUti.getBigDecimalValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_FAC_REP);
                intNumMesRepNumColAct=objUti.getIntValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_NUM_COL);
                intArlNumMesRep1=objUti.getIntValueAt(arlDatNumMesRep, k, INT_ARL_NUM_MES_REP_NUM_MES);

                if(k==0){//primera interaccion
                    bgdTotStkDis=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+2))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+2)).toString()));
                    bgdTotStkDisRep=bgdTotStkDis.multiply(bgdFacRep);                    
                }
                else{//mas de una interaccion
                    //capturar los datos del mes anterior
                    intNumMesRepNumColAnt=objUti.getIntValueAt(arlDatNumMesRep, (k-1), INT_ARL_NUM_MES_REP_NUM_COL);

                    for(int m=intNumColIniNumMesRep; m<intNumColFinNumMesRep; m++){
                        if(m==intNumMesRepNumColAnt){
                            //bgdStkDisAnt=new BigDecimal(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS))==null?"0":(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS)).toString().equals("")?"0":objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_DIS)).toString()));
                            bgdStkDisAnt=new BigDecimal(objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_LLE))==null?"0":(objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_LLE)).toString().equals("")?"0":objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_LLE)).toString()));
                            //bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA))==null?"0":(objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA)).toString().equals("")?"0":objTblMod.getValueAt(i, (m+INT_NUM_COL_REP_VTA)).toString()));
                            bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_VTA))==null?"0":(objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_VTA)).toString().equals("")?"0":objTblMod.getValueAt(fila, (m-INT_NUM_COL_REP_VTA)).toString()));
                            bgdLleAnt=new BigDecimal(objTblMod.getValueAt(fila, m)==null?"0":(objTblMod.getValueAt(fila, m).toString().equals("")?"0":objTblMod.getValueAt(fila, m).toString()));
                            bgdTotStkDis=bgdStkDisAnt.subtract(bgdVtaAnt).add(bgdLleAnt);
                            bgdTotStkDisRep=bgdTotStkDis;
                            break;
                        }
                    }
                }

                //VENTA*****
                bgdCalMen=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniVtaPro+2))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniVtaPro+2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniVtaPro+2)).toString()));
                //Disponible
                bgdStkDis=bgdTotStkDisRep;
                //Vtas
                if(bgdStkDis.compareTo(bgdCalMen)>0){
                    bgdVta=bgdCalMen;
                }
                else{
                    bgdVta=bgdStkDis;
                }

                objTblMod.setValueAt(bgdVta, fila, (intNumMesRepNumColAct-INT_NUM_COL_REP_VTA));//ventas

                //LLEGADAS
                //aqui
                intCodItmTbl=Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM)==null?"-1":(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM).toString().equals("")?"-1":objTblMod.getValueAt(fila, INT_TBL_DAT_COD_ITM).toString()));

                for(int j=0; j<arlDatMesRep.size(); j++){
                    strEstPro=objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO)==null?"":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO);
                    intColMesRep=Integer.parseInt(objUti.getObjectValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL)==null?"0":(objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL).equals("")?"0":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COL)  ));
                    intArlNumMesRep2=Integer.parseInt(objUti.getObjectValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES)==null?"0":(objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES).equals("")?"0":objUti.getStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_MES)  ));

                    intCodItmArl=objUti.getIntValueAt(arlDatMesRep, j, INT_ARL_MES_REP_COD_ITM);

                    bgdCanRep=objUti.getBigDecimalValueAt(arlDatMesRep, j, INT_ARL_MES_REP_CAN);

                    if(intCodItmTbl==intCodItmArl){
                        if(intArlNumMesRep1==intArlNumMesRep2){
                            objTblMod.setValueAt(bgdCanRep, fila, intColMesRep);
                            objUti.setStringValueAt(arlDatMesRep, j, INT_ARL_MES_REP_EST_PRO, "P");
                            break;
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
            blnRes=false;
        }
        return blnRes;
    }
    
  
    /**
     * Función que permite obtener cantidades de nota de pedido+pedido embarcado
     * @param intNumAni
     * @param intNumMes
     * @param intColMesRep
     * @return 
     */
    private boolean getDatNotPed_PedEmb(){
        boolean blnRes=true;
        int intNumAni=-1;
        int intNumMes=-1;
        int intNumDia=-1;
        int intColMesRep=-1;
        BigDecimal bgdCanRep=BigDecimal.ZERO;
        Connection conRep;
        try{
            conRep=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conRep!=null){
                stm=conRep.createStatement();
                arlDatMesRep.clear();
                
                for(int i=0; i<arlDatNumMesRep.size(); i++){
                    intNumAni=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_ANI);
                    intNumMes=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_MES);
                    intColMesRep=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_COL);
                    
                    if( (intNumMes==1) || (intNumMes==3) || (intNumMes==5) || (intNumMes==7) || (intNumMes==8) || (intNumMes==10) || (intNumMes==12) )
                        intNumDia=31;
                    else if(intNumMes==2)
                        intNumDia=28;
                    else
                        intNumDia=30;

                    strSQL="";
                    strSQL+=" SELECT a1.co_itmMae, a1.co_itm, a1.tx_codAlt, SUM(a1.nd_canNotPedEmb) AS nd_can, a1.co_uni";
                    strSQL+=" FROM(";
                    //--Para obtener el Nota de Pedido
                    strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.fe_arr, b1.st_reg, b1.tx_numDoc2";
                    strSQL+=" 	     , b1.co_imp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.co_itmMae, b1.nd_canNotPedEmb, b1.co_uni";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 		    , ('' || a1.ne_aniemb || '-' || (CASE WHEN a5.ne_numMes<10 THEN '0' || a5.ne_numMes ELSE '' || a5.ne_numMes END) || '-' || '01') AS fe_arr";
                    strSQL+=" 		    , a1.st_reg, a1.tx_numDoc2, a1.co_imp, a2.co_reg, a2.co_itm, a2.nd_can AS nd_canNotPedEmp, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae, a1.st_cie";
                    strSQL+=" 		    , (a2.nd_can-(CASE WHEN a2.nd_canUtiPedEmb IS NULL THEN 0 ELSE a2.nd_canUtiPedEmb END)) AS nd_canNotPedEmb, k1.co_reg AS co_uni";
                    strSQL+=" 		FROM tbm_cabNotPedImp AS a1";
                    strSQL+=" 		INNER JOIN tbm_detNotPedImp AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN (tbm_inv AS a3 LEFT OUTER JOIN tbm_var AS k1 ON a3.co_uni=k1.co_reg) ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesEmb";
                    strSQL+=" 		WHERE a1.st_reg='A' AND a1.st_cie='N'";
                    strSQL+=" 		AND a1.ne_aniemb=" + intNumAni + "";
                    if(i==0)
                        strSQL+=" 		AND a5.ne_numMes<=" + intNumMes + "";
                    else
                        strSQL+=" 		AND a5.ne_numMes=" + intNumMes + "";
                    strSQL+=" 		ORDER BY co_itmMae, co_emp, co_loc, co_tipDoc, co_doc, co_reg";
                    strSQL+=" 	) AS b1";
                    strSQL+=" 	WHERE b1.nd_canNotPedEmb>0";
                    strSQL+=" 	UNION";
                    //--Para obtener el pedido Embarcado
                    strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.fe_arr, b1.st_reg, b1.tx_numDoc2";
                    strSQL+=" 	     , b1.co_imp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.co_itmMae, b1.nd_canPedEmb, b1.co_uni";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 		     , '' || a1.fe_arr AS fe_arr, a1.st_reg, a1.tx_numDoc2";
                    strSQL+=" 		     , a1.co_imp, a2.co_reg, a2.co_itm, a2.nd_can AS nd_canPedEmb, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae";
                    strSQL+=" 		     , a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel, k1.co_reg AS co_uni";
                    strSQL+=" 		FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN (tbm_inv AS a3 LEFT OUTER JOIN tbm_var AS k1 ON a3.co_uni=k1.co_reg) ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		WHERE a1.st_reg='A'";
                    if(i==0)
                        strSQL+="               AND a1.fe_arr<='" + intNumAni + "-" + intNumMes + "-" + intNumDia + "'";
                    else
                        strSQL+="               AND a1.fe_arr BETWEEN '" + intNumAni + "-" + intNumMes + "-01' AND '" + intNumAni + "-" + intNumMes + "-" + intNumDia + "'";
                    strSQL+=" 	) AS b1";
                    strSQL+=" 	LEFT OUTER JOIN(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.fe_doc AS fe_arr";
                    strSQL+=" 		     , a1.st_reg, a1.tx_numDoc2";
                    strSQL+=" 		     , a2.co_reg, a2.co_itm, a2.nd_can AS nd_canIngImp, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae";
                    strSQL+=" 		     , a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp, k1.co_reg AS co_uni";
                    strSQL+=" 		FROM tbm_cabMovInv AS a1";
                    strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN (tbm_inv AS a3 LEFT OUTER JOIN tbm_var AS k1 ON a3.co_uni=k1.co_reg) ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		WHERE a1.st_reg='A' AND a1.co_tipDoc IN(14, 245)";
                    strSQL+=" 		ORDER BY co_itmMae, co_emp, co_loc, co_tipDoc, co_doc, co_reg";
                    strSQL+=" 	) AS b2";
                    strSQL+=" 	ON b1.co_emp=b2.co_empRelPedEmbImp AND b1.co_loc=b2.co_locRelPedEmbImp AND b1.co_tipDoc=b2.co_tipDocRelPedEmbImp AND b1.co_doc=b2.co_docRelPedEmbImp";
                    strSQL+=" 	WHERE b2.co_emp IS NULL";                    
                    strSQL+=" 	ORDER BY tx_codAlt, tx_numDoc2";
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE a1.co_emp IS NOT NULL";
                    
                    //Filtrar por "Item".
                    if (objVenConItm01.isCondicionAplicada())
                    {
                        strSQL+=objVenConItm01.getCondicionesSQL();
                    }
                    
                    if(objParSis.getCodigoMenu()==objZafImp.INT_COD_MNU_PRG_CFG_COM_LOC){
                        strSQL+="  AND NOT (a1.tx_codAlt LIKE '%I')";  //Solicitado por AP, que en el modulo de compras Locales, no se presenten los items en I.
                    }                           
                    
                    strSQL+=" GROUP BY a1.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.co_uni";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegMesRep=new ArrayList();
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ANI, intNumAni);
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_MES, intNumMes);
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ITM, rst.getString("co_itm"));
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ALT_ITM, rst.getString("tx_codAlt"));
                        arlRegMesRep.add(INT_ARL_MES_REP_CAN, rst.getString("nd_can"));
                        arlRegMesRep.add(INT_ARL_MES_REP_EST_PRO, "");
                        arlRegMesRep.add(INT_ARL_MES_REP_COL, intColMesRep);
                        arlDatMesRep.add(arlRegMesRep);
                    }
                }//fin del for
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                conRep.close();
                conRep=null;
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
     * Función que permite obtener cantidades de nota de pedido+pedido embarcado
     * @param intNumAni
     * @param intNumMes
     * @param intColMesRep
     * @return 
     */
    private boolean getDatNotPed_PedEmb(int fila){
        boolean blnRes=true;
        int intNumAni=-1;
        int intNumMes=-1;
        int intNumDia=-1;
        int intColMesRep=-1;
        int intCodItmArl=-1, intCodItmTbl=-1;
        BigDecimal bgdCanRep=BigDecimal.ZERO;
        Connection conRep;
        try{
            conRep=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conRep!=null){
                stm=conRep.createStatement();
                arlDatMesRep.clear();
                
                for(int i=0; i<arlDatNumMesRep.size(); i++){
                    intNumAni=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_ANI);
                    intNumMes=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_MES);
                    intColMesRep=objUti.getIntValueAt(arlDatNumMesRep, i, INT_ARL_NUM_MES_REP_NUM_COL);
                    
                    if( (intNumMes==1) || (intNumMes==3) || (intNumMes==5) || (intNumMes==7) || (intNumMes==8) || (intNumMes==10) || (intNumMes==12) )
                        intNumDia=31;
                    else if(intNumMes==2)
                        intNumDia=28;
                    else
                        intNumDia=30;

                    strSQL="";
                    strSQL+=" SELECT c1.co_itmMae, c1.co_itm, c1.tx_codAlt, SUM(c1.nd_canNotPedEmb) AS nd_can";
                    strSQL+=" FROM(";
                    //--Para obtener el Nota de Pedido
                    strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.fe_arr, b1.st_reg, b1.tx_numDoc2";
                    strSQL+=" 	     , b1.co_imp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.co_itmMae, b1.nd_canNotPedEmb";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 		     , ('' || a1.ne_aniemb || '-' || (CASE WHEN a5.ne_numMes<10 THEN '0' || a5.ne_numMes ELSE '' || a5.ne_numMes END) || '-' || '01') AS fe_arr";
                    strSQL+=" 		     , a1.st_reg, a1.tx_numDoc2, a1.co_imp, a2.co_reg, a2.co_itm, a2.nd_can AS nd_canNotPedEmp, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae, a1.st_cie";
                    strSQL+=" 		     , (a2.nd_can-(CASE WHEN a2.nd_canUtiPedEmb IS NULL THEN 0 ELSE a2.nd_canUtiPedEmb END)) AS nd_canNotPedEmb";
                    strSQL+=" 		FROM tbm_cabNotPedImp AS a1";
                    strSQL+=" 		INNER JOIN tbm_detNotPedImp AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		INNER JOIN tbm_mesEmbImp AS a5 ON a1.co_mesArr=a5.co_mesEmb";
                    strSQL+=" 		WHERE a1.st_reg='A' AND a1.st_cie='N'";
                    strSQL+=" 		AND a1.ne_aniemb=" + intNumAni + "";
                    if(i==0)
                        strSQL+=" 		AND a5.ne_numMes<=" + intNumMes + "";
                    else
                        strSQL+=" 		AND a5.ne_numMes=" + intNumMes + "";
                    strSQL+=" 		ORDER BY co_itmMae, co_emp, co_loc, co_tipDoc, co_doc, co_reg";
                    strSQL+=" 	) AS b1";
                    strSQL+=" 	WHERE b1.nd_canNotPedEmb>0";
                    strSQL+=" 	UNION";
                    //--Para obtener el pedido Embarcado
                    strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.fe_arr, b1.st_reg, b1.tx_numDoc2";
                    strSQL+=" 	     , b1.co_imp, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b1.co_itmMae, b1.nd_canPedEmb";
                    strSQL+=" 	FROM(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc";
                    strSQL+=" 		     , '' || a1.fe_arr AS fe_arr, a1.st_reg, a1.tx_numDoc2";
                    strSQL+=" 		     , a1.co_imp, a2.co_reg, a2.co_itm, a2.nd_can AS nd_canPedEmb, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae";
                    strSQL+=" 		     , a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                    strSQL+=" 		FROM tbm_cabPedEmbImp AS a1 INNER JOIN tbm_detPedEmbImp AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		WHERE a1.st_reg='A'";
                    if(i==0)
                        strSQL+="               AND a1.fe_arr<='" + intNumAni + "-" + intNumMes + "-" + intNumDia + "'";
                    else
                        strSQL+="               AND a1.fe_arr BETWEEN '" + intNumAni + "-" + intNumMes + "-01' AND '" + intNumAni + "-" + intNumMes + "-" + intNumDia + "'";
                    strSQL+=" 	) AS b1";
                    strSQL+=" 	LEFT OUTER JOIN(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.fe_doc AS fe_arr";
                    strSQL+=" 		     , a1.st_reg, a1.tx_numDoc2";
                    strSQL+=" 		     , a2.co_reg, a2.co_itm, a2.nd_can AS nd_canIngImp, a3.tx_codAlt, a3.tx_nomItm, a4.co_itmMae";
                    strSQL+=" 		     , a1.co_empRelPedEmbImp, a1.co_locRelPedEmbImp, a1.co_tipDocRelPedEmbImp, a1.co_docRelPedEmbImp";
                    strSQL+=" 		FROM tbm_cabMovInv AS a1";
                    strSQL+=" 		INNER JOIN tbm_detMovInv AS a2";
                    strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" 		INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+=" 		WHERE a1.st_reg='A' AND a1.co_tipDoc IN(14, 245)";
                    strSQL+=" 		ORDER BY co_itmMae, co_emp, co_loc, co_tipDoc, co_doc, co_reg";
                    strSQL+=" 	) AS b2";
                    strSQL+=" 	ON b1.co_emp=b2.co_empRelPedEmbImp AND b1.co_loc=b2.co_locRelPedEmbImp AND b1.co_tipDoc=b2.co_tipDocRelPedEmbImp AND b1.co_doc=b2.co_docRelPedEmbImp";
                    strSQL+=" 	WHERE b2.co_emp IS NULL";                    
                    strSQL+=" 	ORDER BY tx_codAlt, tx_numDoc2";
                    strSQL+=" ) AS c1";
                    strSQL+=" WHERE c1.co_emp IS NOT NULL";
                    strSQL+=" AND c1.co_itm=" + objTblModFix.getValueAt(fila, INT_TBL_DAT_FIX_COD_ITM)  + "";//
                    strSQL+=" GROUP BY c1.co_itmMae, c1.co_itm, c1.tx_codAlt";
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegMesRep=new ArrayList();
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ANI, intNumAni);
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_MES, intNumMes);
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ITM, rst.getString("co_itm"));
                        arlRegMesRep.add(INT_ARL_MES_REP_COD_ALT_ITM, rst.getString("tx_codAlt"));
                        arlRegMesRep.add(INT_ARL_MES_REP_CAN, rst.getString("nd_can"));
                        arlRegMesRep.add(INT_ARL_MES_REP_EST_PRO, "");
                        arlRegMesRep.add(INT_ARL_MES_REP_COL, intColMesRep);
                        arlDatMesRep.add(arlRegMesRep);
                    }
                }//fin del for
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                conRep.close();
                conRep=null;
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
    
    
    private boolean calculaSaldoIniRep(){
        boolean blnRes=true;
        BigDecimal bgdDisAnt=BigDecimal.ZERO;
        BigDecimal bgdVtaAnt=BigDecimal.ZERO;
        BigDecimal bgdLleAnt=BigDecimal.ZERO;
        BigDecimal bgdDisAct=BigDecimal.ZERO;
        BigDecimal bgdCanRepCalMax=BigDecimal.ZERO;
        BigDecimal bgdMes=BigDecimal.ZERO;
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO); //en Kilos
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){//intNumColIniProAnu
                //bgdCalMen=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniStk+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniStk+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniStk+2)).toString()));
                //bgdCanRepCalMax=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+4))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString()));
                bgdCanRepCalMax=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniVtaPro+2))==null?"0":(objTblMod.getValueAt(i, (intNumColIniVtaPro+2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColIniVtaPro+2)).toString()));
                
                
                bgdDisAnt=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinNumMesRep-3))==null?"0":(objTblMod.getValueAt(i, (intNumColFinNumMesRep-3)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinNumMesRep-3)).toString()));
                bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinNumMesRep-2))==null?"0":(objTblMod.getValueAt(i, (intNumColFinNumMesRep-2)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinNumMesRep-2)).toString()));
                bgdLleAnt=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinNumMesRep-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinNumMesRep-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinNumMesRep-1)).toString()));
                       
                bgdValPesItmKil=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                
                bgdDisAct=bgdDisAnt.subtract(bgdVtaAnt).add(bgdLleAnt);
                if(bgdCanRepCalMax.compareTo(BigDecimal.ZERO)>0)
                    bgdMes=bgdDisAct.divide(bgdCanRepCalMax, 2, BigDecimal.ROUND_UP);
//                if(chkMosDatKil.isSelected()){
//                    objTblMod.setValueAt((bgdDisAct.multiply(bgdValPesItmKil)), i, intNumColIniNumMesPry);
//                    objTblMod.setValueAt((bgdMes.multiply(bgdValPesItmKil)), i, (intNumColIniNumMesPry+1));
//                }
//                else{
                    objTblMod.setValueAt(bgdDisAct, i, intNumColIniNumMesPry);
                    objTblMod.setValueAt(bgdMes, i, (intNumColIniNumMesPry+1));
//                }

                bgdDisAct=BigDecimal.ZERO;
                bgdMes=BigDecimal.ZERO;
                bgdValPesItmKil=BigDecimal.ZERO;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }        
             
    private boolean calculaSaldoIniRep(int fila){
        boolean blnRes=true;
        BigDecimal bgdDisAnt=BigDecimal.ZERO;
        BigDecimal bgdVtaAnt=BigDecimal.ZERO;
        BigDecimal bgdLleAnt=BigDecimal.ZERO;
        BigDecimal bgdDisAct=BigDecimal.ZERO;
        BigDecimal bgdCalMen=BigDecimal.ZERO;
        BigDecimal bgdMes=BigDecimal.ZERO;
        
        //en Kilos
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);
        try{
            //bgdCalMen=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniStk+2))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniStk+2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniStk+2)).toString()));
            bgdCalMen=new BigDecimal(objTblMod.getValueAt(fila, (intNumColIniVtaPro+2))==null?"0":(objTblMod.getValueAt(fila, (intNumColIniVtaPro+2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColIniVtaPro+2)).toString()));
            bgdDisAnt=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-3))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-3)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinNumMesRep-3)).toString()));
            bgdVtaAnt=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-2))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-2)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinNumMesRep-2)).toString()));
            bgdLleAnt=new BigDecimal(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-1))==null?"0":(objTblMod.getValueAt(fila, (intNumColFinNumMesRep-1)).toString().equals("")?"0":objTblMod.getValueAt(fila, (intNumColFinNumMesRep-1)).toString()));
            bgdValPesItmKil=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_PES_KG).toString()));

            bgdDisAct=bgdDisAnt.subtract(bgdVtaAnt).add(bgdLleAnt);
            if(bgdCalMen.compareTo(BigDecimal.ZERO)>0)
                bgdMes=bgdDisAct.divide(bgdCalMen, 2, BigDecimal.ROUND_UP);

//            if(chkMosDatKil.isSelected()){
//                objTblMod.setValueAt((bgdDisAct.multiply(bgdValPesItmKil)), fila, intNumColIniNumMesPry);
//                objTblMod.setValueAt((bgdMes.multiply(bgdValPesItmKil)), fila, (intNumColIniNumMesPry+1));
//            }
//            else{
                objTblMod.setValueAt(bgdDisAct, fila, intNumColIniNumMesPry);
                objTblMod.setValueAt(bgdMes, fila, (intNumColIniNumMesPry+1));
//            }

            bgdDisAct=BigDecimal.ZERO;
            bgdMes=BigDecimal.ZERO;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesReponer_peso(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanRep=new BigDecimal(0);
        BigDecimal bgdSumStkCos=new BigDecimal(0);

        //en Kilos
        BigDecimal bgdValPesItmKil=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniNumMesRep; j<intNumColFinNumMesRep; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    
                    bgdCanRep=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanRep);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanRep.multiply(bgdCosUniGrp));

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdCanRep=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumStkCos, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bgdSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesReponer_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanRep=new BigDecimal(0);
        BigDecimal bgdSumStkCos=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniNumMesRep; j<intNumColFinNumMesRep; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    bgdCanRep=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumStkCos=bgdSumStkCos.add(((bgdCanRep.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanRep.multiply(bgdCosUniGrp));
                    

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdCanRep=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumStkCos, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bgdSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesSaldoInicial_peso(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanSalIni=new BigDecimal(0);
        BigDecimal bgdSumStkCos=new BigDecimal(0);

        try{
            for(int j=intNumColIniNumMesPry; j<intNumColFinNumMesPry; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                    
                    bgdCanSalIni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected())
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanSalIni);//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                    else
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanSalIni.multiply(bgdCosUniGrp));

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdCanSalIni=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumStkCos, objParSis.getDecimalesMostrar()), 0, j);//columna de promedio anual calculado
                bgdSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalesSaldoInicial_costo(){
        boolean blnRes=true;
        BigDecimal bgdCosUniGrp=new BigDecimal(BigInteger.ZERO);
        BigDecimal bgdCanSalIni=new BigDecimal(0);
        BigDecimal bgdSumStkCos=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(BigInteger.ZERO);
        try{
            for(int j=intNumColIniNumMesPry; j<intNumColFinNumMesPry; j++){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdCosUniGrp=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP).toString()));
                    bgdPesItm=new BigDecimal(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)==null?"0":(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString().equals("")?"0":objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG).toString()));
                    bgdCanSalIni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0": objTblMod.getValueAt(i, j).toString()));
                    
                    if(chkMosDatKil.isSelected()){
                        if(bgdPesItm.compareTo(BigDecimal.ZERO)!=0)
                            bgdSumStkCos=bgdSumStkCos.add(((bgdCanSalIni.divide(bgdPesItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).multiply(bgdCosUniGrp)));
                    }
                    else
                        bgdSumStkCos=bgdSumStkCos.add(bgdCanSalIni.multiply(bgdCosUniGrp));

                    bgdCosUniGrp=new BigDecimal(0);
                    bgdCanSalIni=new BigDecimal(0);
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSumStkCos, objParSis.getDecimalesMostrar()), 1, j);//columna de promedio anual calculado
                bgdSumStkCos=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite calcular totales
    */
    private boolean calcularTotalPromedioAnualCalculada_peso_calMax(){
        boolean blnRes=true;
        BigDecimal bgdCanRepCalMax=new BigDecimal(0);
        BigDecimal bgdSum_PesItm_CanRepCalMax=new BigDecimal(0);
        BigDecimal bgdPesItm=new BigDecimal(0);//peso
        BigDecimal bgdPesItm_CanRepCal=new BigDecimal(0);//peso
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bgdCanRepCalMax=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProAnu+4))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProAnu+4)).toString()));
                bgdPesItm=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KG).toString()));
                                
                if(chkMosDatKil.isSelected())
                    bgdPesItm_CanRepCal=bgdCanRepCalMax;//No se multiplica por el peso, porque la información de las columnas de la tabla de detalle ya esta en kilos, por tanto es sólo de sumar
                else
                    bgdPesItm_CanRepCal=bgdCanRepCalMax.multiply(bgdPesItm);
                
                bgdSum_PesItm_CanRepCalMax=bgdSum_PesItm_CanRepCalMax.add(bgdPesItm_CanRepCal);
                
                bgdCanRepCalMax=new BigDecimal(0);
                bgdPesItm=new BigDecimal(0);
                bgdPesItm_CanRepCal=new BigDecimal(0);
            }
            objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdSum_PesItm_CanRepCalMax, objParSis.getDecimalesMostrar()), 0, (intNumColIniProAnu+4));//columna de promedio anual calculado
            bgdSum_PesItm_CanRepCalMax=new BigDecimal(0);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
//------------------------------------------------------------------------------
    private boolean calcularTotalProAnuVenMan()
    {
        boolean blnRes=true;
        BigDecimal bgdPes, bgdPreUni, bgdVal, bgdTotPes, bgdTotPreUni;
        try
        {
            for (int j=intNumColIniProAnu; j<intNumColFinProAnu; j++)
            {
                bgdTotPes=BigDecimal.ZERO;
                bgdTotPreUni=BigDecimal.ZERO;
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    bgdPes=BigDecimal.valueOf(objUti.parseDouble(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)));
                    bgdPreUni=BigDecimal.valueOf(objUti.parseDouble(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)));
                    bgdVal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));
                    bgdTotPes=bgdTotPes.add(bgdVal.multiply(bgdPes));
                    bgdTotPreUni=bgdTotPreUni.add(bgdVal.multiply(bgdPreUni));
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdTotPes, objParSis.getDecimalesMostrar()), 0, j);
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdTotPreUni, objParSis.getDecimalesMostrar()), 1, j);
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     private boolean calcularTotalValManCalRep()
    {
        boolean blnRes=true;
        BigDecimal bgdPes, bgdPreUni, bgdVal, bgdTotPes, bgdTotPreUni;
        try
        {
            for (int j=intNumColIniValManCalRep; j<intNumColFinValManCalRep; j++)
            {
                bgdTotPes=BigDecimal.ZERO;
                bgdTotPreUni=BigDecimal.ZERO;
                for (int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    bgdPes=BigDecimal.valueOf(objUti.parseDouble(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_PES_KG)));
                    bgdPreUni=BigDecimal.valueOf(objUti.parseDouble(objTblModFix.getValueAt(i, INT_TBL_DAT_FIX_COS_UNI_GRP)));
                    bgdVal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, j)));
                    bgdTotPes=bgdTotPes.add(bgdVal.multiply(bgdPes));
                    bgdTotPreUni=bgdTotPreUni.add(bgdVal.multiply(bgdPreUni));
                }
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdTotPes, objParSis.getDecimalesMostrar()), 0, j);
                objTblModTot.setValueAt("" + objUti.redondearBigDecimal(bgdTotPreUni, objParSis.getDecimalesMostrar()), 1, j);
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
//------------------------------------------------------------------------------
     
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
     
     
     /**
      * 
      * @param conExt
      * @return 
      */
     
     private boolean ingresarDatosCargados(){
        boolean blnRes=true;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        String strUpdateItems="", strFecUltMod;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                conLoc.setAutoCommit(false);
                stmLoc = conLoc.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                  return false;
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                for(int i=0;i<arlDatExcel.size();i++){
                    strUpdateItems+="UPDATE tbm_inv SET "
                        + "nd_proAnuVenMan="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PROMEDIO)+", "
                        + "nd_stkBas="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_STOCK)+", "
                        + "nd_tra="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_TRANSITO)+", "
                        + "nd_fre="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_FRECUENCIA)+", "
                        + "nd_punParMax="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PPMAX)+", "
                        + "nd_punParMin="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_PPMIN)+", "
                        + "nd_moq="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_MOQ)+", "
                        + "fe_ultmod="+objUti.codificar(strFecUltMod)+", "
                        + "co_usrMod="+objParSis.getCodigoUsuario()+", "
                        + "nd_exc="+objUti.getDoubleValueAt(arlDatExcel, i, INT_ARL_EXC_EXC)+"  "    
                        + "WHERE tx_codAlt2="+objUti.codificar(objUti.getStringValueAt(arlDatExcel, i, INT_ARL_EXC_ITEM)) +"; ";
                }
                //System.out.println("ITEMS: " + strUpdateItems);
                stmLoc.executeUpdate(strUpdateItems);
                //Historico de inventario
                if(!objHisTblBasDat.insertarHistoricoMasivo(conLoc, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())){
                    blnRes=false;
                }
                
                if(blnRes){
                    System.out.println("OK! ");
                    conLoc.commit();
                }
                else{
                    conLoc.rollback();
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
    
    
    /**
     * 
     * @param conExt
     * @return 
     */
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
   
    private static int INT_ARL_EXC_ITEM=0; 
    private static int INT_ARL_EXC_PROMEDIO=1;
    private static int INT_ARL_EXC_PPMIN=2;
    private static int INT_ARL_EXC_PPMAX=3;
    private static int INT_ARL_EXC_EXC=4;
    private static int INT_ARL_EXC_STOCK=5;
    private static int INT_ARL_EXC_FRECUENCIA=6;
    private static int INT_ARL_EXC_TRANSITO=7;
    private static int INT_ARL_EXC_MOQ=8;
    private static int INT_ARL_VERIFICACION=9;
     
    
    
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
                        + " WHERE a1.ITMSHNAM IN ( "+strCodItmDynBus+" ) OR a1.ITMGEDSC IN ( "+strCodItmDynBus+" )"  ;
                System.out.println("obtenerDatosGP: "+strSql);
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
    
    
}