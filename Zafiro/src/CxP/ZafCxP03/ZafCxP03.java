/*
 * ZafCxP03.java
 *
 * Created on 29 de Julio del 2008
 */
package CxP.ZafCxP03;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafPagExt;
import Librerias.ZafImp.ZafPagImp;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec; 
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import java.util.ArrayList;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.swing.JScrollBar;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCxP03 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_PER=3;
    static final int INT_TBL_DAT_NOM_PER=4;
    static final int INT_TBL_DAT_COD_TIP_DOC=5;
    static final int INT_TBL_DAT_DES_COR=6;
    static final int INT_TBL_DAT_DES_LAR=7;
    static final int INT_TBL_DAT_COD_DOC=8;
    static final int INT_TBL_DAT_COD_REG=9;
    static final int INT_TBL_DAT_NUM_DOC=10;
    static final int INT_TBL_DAT_NUM_FAC_PRV=11;
    static final int INT_TBL_DAT_MOT_DOC=12;
    static final int INT_TBL_DAT_FEC_DOC=13;
    static final int INT_TBL_DAT_FEC_VEN=14;
    static final int INT_TBL_DAT_VAL_PEN=15;
    static final int INT_TBL_DAT_CHK_AUT_PAG=16;
    static final int INT_TBL_DAT_CHK_USR_AUT_AUX=17;
    static final int INT_TBL_DAT_FEC_CHQ=18;
    static final int INT_TBL_DAT_COD_BEN=19;
    static final int INT_TBL_DAT_NOM_BEN=20;
    static final int INT_TBL_DAT_CHK_IMP=21;      //Para documentos de importaciones
    static final int INT_TBL_DAT_CHK_PAG_ARA=22;  //Para pago de aranceles
    static final int INT_TBL_DAT_CHK_PAG_EXT=23;  //Para pagos al exterior
    
    //Constantes: Columnas del arreglo arlDatAutPag
    static final int INT_TBL_DAT_AUT_PAG_COD_EMP=0;
    static final int INT_TBL_DAT_AUT_PAG_COD_LOC=1;
    static final int INT_TBL_DAT_AUT_PAG_COD_TIP_DOC=2;
    static final int INT_TBL_DAT_AUT_PAG_COD_DOC=3;
    static final int INT_TBL_DAT_AUT_PAG_NUM_DOC=4;
    static final int INT_TBL_DAT_AUT_PAG_COD_CLI=5;
    static final int INT_TBL_DAT_AUT_PAG_NOM_CLI=6;
    static final int INT_TBL_DAT_AUT_PAG_COD_BEN=7;
    static final int INT_TBL_DAT_AUT_PAG_NOM_BEN=8;
    static final int INT_TBL_DAT_AUT_PAG_FEC_ULT_MOD=9;
    
    //ArrayList: Vistos Buenos - Títulos
    private ArrayList arlRegTitVisBue, arlDatTitVisBue;
    final int INT_ARL_TIT_COD_VIS_BUE=0;
    final int INT_ARL_TIT_NOM_VIS_BUE=1;

    //ArrayList: Vistos Buenos - Columnas
    private ArrayList arlRegVisBue, arlDatVisBue;
    final int INT_ARL_COD_VIS_BUE=0;
    final int INT_ARL_NOM_VIS_BUE=1;
    final int INT_ARL_COL_VIS_BUE=2;
    
    //ArrayList: Vistos Buenos - Datos
    private ArrayList arlRegVisBueDat, arlDatVisBueDat;
    final int INT_ARL_VIS_BUE_DB_COD_EMP_DB=0;
    final int INT_ARL_VIS_BUE_DB_COD_LOC_DB=1;
    final int INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB=2;
    final int INT_ARL_VIS_BUE_DB_COD_DOC_DB=3;
    final int INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB=4;
    final int INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB=5;
    final int INT_ARL_VIS_BUE_DB_COD_USR_DB=6;    

    //ArrayList: Bancos
    private ArrayList arlRegBco, arlDatBco;
    final int INT_ARL_COD_BCO=0;
    final int INT_ARL_NOM_BCO=1;
    final int INT_ARL_COL_BCO=2;

    //ArrayList: Beneficiarios
    private ArrayList arlRegBen, arlDatBen;
    final int INT_ARL_COD_EMP=0;
    final int INT_ARL_COD_CLI=1;
    final int INT_ARL_NOM_CLI=2;
    final int INT_ARL_COD_BEN=3;
    final int INT_ARL_NOM_BEN=4;
    
    //ArrayList: 
    private ArrayList arlRegModTieEje, arlDatModTieEje;
    final int INT_TBL_ARL_COD_EMP=0;
    final int INT_TBL_ARL_COD_LOC=1;
    final int INT_TBL_ARL_COD_CLI=2;
    final int INT_TBL_ARL_NOM_CLI=3;
    final int INT_TBL_ARL_COD_TIP_DOC=4;
    final int INT_TBL_ARL_DES_COR=5;
    final int INT_TBL_ARL_DES_LAR=6;
    final int INT_TBL_ARL_COD_DOC=7;
    final int INT_TBL_ARL_COD_REG=8;
    final int INT_TBL_ARL_NUM_DOC=9;    
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private ZafParSis objParSis;
    private ZafPagImp objPagImp;
    private ZafPagExt objPagExt;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod, objTblModTot;
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenó en JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblTot;
    private ZafTblBus objTblBus;
    private ZafSelFec objSelFec;
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAux;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkAux;
    private ZafTblCelRenChk objTblCelRenChkVisBue;
    private ZafTblCelEdiChk objTblCelEdiChkVisBue;
    private ZafTblCelRenChk objTblCelRenChkBco;
    private ZafTblCelEdiChk objTblCelEdiChkBco;
    private ZafTblCelRenChk objTblCelRenChkBcoAux;
    private ZafTblCelEdiChk objTblCelEdiChkBcoAux;  
    private ZafTblCelRenLbl objTblCelRenLblSal;  
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiBut;    
    private ZafTblCelEdiTxt objTblCelEdiTxtFecChq;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    
    private ZafPerUsr objPerUsr;
    private ZafDatePicker dtpFecVenChq, dtpFecSis;
    
    private java.util.Date datFecAuxFic;
    private java.util.Date datFecConRea;        //fecha q sirve para saber si ha habido algun cambio en el documento entre mientras estaba consultado en este programa(cargado en memoria->pantalla de Alfredo, Fernando, Luigui)
    private JScrollBar barDat, barTot;
    
    private ZafVenCon vcoTipDoc;
    private ZafVenCon vcoPrv;    
    private ZafCxP03_01 objCxP03_01;
    private ZafCxP03_02 objCxP03_02;
    
    private ArrayList arlReg, arlDat;
    private Vector vecDat, vecCab, vecReg, vecAux;
    
    private boolean blnCon;                     //true: Continua la ejecución del hilo.
    private boolean blnCamFecChq;               // fecha        -> modificar
    private boolean blnAutPag, blnDesAutPag;    // pago         -> autorizar/desautorizar
    private boolean blnAutBco, blnDesAutBco;    // banco        -> autorizar/desautorizar
    private boolean blnCamMot;                  // motivo       -> ingresar/cambiar    

    private int intNumColCnfEstIni;             //numero de columnas creadar inicialmente, con las que se crea la tabla en configurarTblDat()
    private int intNumColAdiVisBue;             //numero de columnas que se deben adicionar al modelo por vistos buenos
    private int intNumColIniVisBue;             //numero de columna desde donde empieza la columna adicionada por visto bueno
    private int intNumColFinVisBue;             //numero de columna hasta donde termina la columna adicionada por visto bueno
    private int intNumColAdiBco;                //numero de columnas que se deben adicionar al modelo por vistos buenos
    private int intNumColIniBco;                //numero de columna desde donde empieza la columna adicionada por visto bueno
    private int intNumColFinBco;                //numero de columna hasta donde termina la columna adicionada por visto bueno
    private int intNumColAdiVar;                //numero de columnas a adicionar son 3
    private int intNumColIniVar;                //numero de columna despues de las columnas de chks de bancos
    private int intNumColFinVar;                //numero de columna despues de haber adicionado las 3 despues de las columnas de chks de bancos

    private int intNumColAdiBcoAux;//
    private int intNumColIniBcoAux;//
    private int intNumColFinBcoAux;//

    private int intNumColIniMotBut;//
    private int intNumColFinMotBut;//

    private String strSQL, strAux;
    private String strFecSis;
    private String strCliPrv;
    private String strCodPrv, strDesLarPrv;
    private String strIdePrv, strDirPrv;
    private String strDesCorTipDoc, strDesLarTipDoc;    

    /** Crea una nueva instancia de la clase ZafParSis. */
    public ZafCxP03(ZafParSis obj) 
    {
        try{
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();   
                if (!configurarFrm())
                    exitForm();
                actualizarBeneficiario();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }            
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }

    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Título de la ventana.
            if(objParSis.getCodigoMenu()==2012 || objParSis.getCodigoMenu()==2016 || objParSis.getCodigoMenu()==2020){
                strCliPrv="Clientes";
            }
            else if(objParSis.getCodigoMenu()==1769 || objParSis.getCodigoMenu()==1773 || objParSis.getCodigoMenu()==1777){
                strCliPrv="Proveedores";
            }
            
            strAux = objParSis.getNombreMenu() + " "+strCliPrv;
            this.setTitle(strAux+" v1.27");
            lblTit.setText(strAux);
            
            //Inicializar objetos
            objUti=new ZafUtil();
            arlDat=new ArrayList();
            arlDatTitVisBue=new ArrayList();
            arlDatVisBue=new ArrayList();
            arlDatBco=new ArrayList();
            arlDatVisBueDat=new ArrayList();
            arlDatBen=new ArrayList();
            arlDatModTieEje=new ArrayList();     
            objPagImp = new ZafPagImp(objParSis, this);  //Pagos de Impuestos Importaciones
            objPagExt = new ZafPagExt(objParSis, this);  //Pagos al Exterior Importaciones
            
            //Configurar ZafDatePicker:
            strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            dtpFecSis=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecSis.setText(strFecSis);

            dtpFecVenChq=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecVenChq.setText("");            
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(20, 32, 550, 70);
            
            blnCamFecChq=false;
            blnAutPag=false;
            blnDesAutPag=false;
            blnAutBco=false;
            blnDesAutBco=false;
            blnCamMot=true;
            butGuaMot.setVisible(false);
            butGuaMot.setEnabled(false);
            butCon.setVisible(false);
            butCon.setEnabled(false);
            butGua.setVisible(false);
            butGua.setEnabled(false);
            butCer.setVisible(false);
            butCer.setEnabled(false);

            //Se ocultan ciertos filtros
            chkMosDocAllVisBue.setVisible(false);
            chkMosDocAllVisBue.setEnabled(false);
            chkMosValExc.setVisible(false);
            chkMosValExc.setEnabled(false);
            txtValExc.setVisible(false);
            txtValExc.setEditable(false);
            txtValExc.setEnabled(false);
            
            //Actualizar etiquetas
            lblPrv.setText(""+strCliPrv+":");
            panPrv.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre "+strCliPrv));
            
            if(objParSis.getCodigoUsuario()==1){
                blnAutPag=true;
                blnDesAutPag=true;
                blnAutBco=true;
                blnDesAutBco=true;
                blnCamFecChq=true;
                blnCamMot=true;
                butGuaMot.setVisible(true);
                butGuaMot.setEnabled(true);
                butCon.setEnabled(true);
                butGua.setVisible(true);
                butCer.setVisible(true);
            }
            
            txtEstExp.setVisible(false); /* No mostrar si es exportador o no. */
            txtCodTipDoc.setVisible(false); 
            txtCodTipDoc.setEditable(false); 
            txtCodTipDoc.setEnabled(false); 
            
            if( (objParSis.getCodigoMenu()==2012) || (objParSis.getCodigoMenu()==2016)  ||  (objParSis.getCodigoMenu()==2020)  ){
                chkMosDocSinFacPrv.setVisible(false);
                chkMosDocSinFacPrv.setEnabled(false);            
            }            

            //Permisos de usuarios
            objPerUsr=new ZafPerUsr(objParSis);
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos: Clientes */" >
            if(objParSis.getCodigoMenu()==2012){
                if(objPerUsr.isOpcionEnabled(2013)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2014)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2015)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2427)){//autorizar pagos
                    blnAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2428)){//desautorizar pagos
                    blnDesAutPag=true;
                }
                chkMosValExc.setVisible(true);
                chkMosValExc.setEnabled(true);
                txtValExc.setVisible(true);
                txtValExc.setEditable(true);
                txtValExc.setEnabled(true);
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1769){
                if(objPerUsr.isOpcionEnabled(1770)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1771)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1772)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2435)){//autorizar pagos
                    blnAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2436)){//desautorizar pagos
                    blnDesAutPag=true;
                }
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Bancos: Clientes */" >
            if(objParSis.getCodigoMenu()==2016){
                if(objPerUsr.isOpcionEnabled(2017)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2018)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2019)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2429)){//autorizar banco
                    blnAutBco=true;
                }
                if(objPerUsr.isOpcionEnabled(2430)){//desautorizar banco
                    blnDesAutBco=true;
                }

                chkMosValExc.setVisible(true);
                chkMosValExc.setEnabled(true);
                txtValExc.setVisible(true);
                txtValExc.setEditable(true);
                txtValExc.setEnabled(true);
            }
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Bancos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1773){
                if(objPerUsr.isOpcionEnabled(1774)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1775)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1776)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2437)){//autorizar banco
                    blnAutBco=true;
                }
                if(objPerUsr.isOpcionEnabled(2438)){//desautorizar banco
                    blnDesAutBco=true;
                }
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos y Bancos: Clientes */" >
            if(objParSis.getCodigoMenu()==2020){
                if(objPerUsr.isOpcionEnabled(2021)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2022)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2023)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2431)){//autorizar pagos
                    blnAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2432)){//desautorizar pagos
                    blnDesAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2433)){//autorizar banco
                    blnAutBco=true;
                }
                if(objPerUsr.isOpcionEnabled(2434)){//desautorizar banco
                    blnDesAutBco=true;
                }

                chkMosValExc.setVisible(true);
                chkMosValExc.setEnabled(true);
                txtValExc.setVisible(true);
                txtValExc.setEditable(true);
                txtValExc.setEnabled(true);
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos y Bancos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1777){
                if(objPerUsr.isOpcionEnabled(1778)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1779)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(1780)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }

                if(objPerUsr.isOpcionEnabled(2439)){//autorizar pagos
                    blnAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2440)){//desautorizar pagos
                    blnDesAutPag=true;
                }
                if(objPerUsr.isOpcionEnabled(2441)){//autorizar banco
                    blnAutBco=true;
                }
                if(objPerUsr.isOpcionEnabled(2442)){//desautorizar banco
                    blnDesAutBco=true;
                }
                if(objPerUsr.isOpcionEnabled(2443)){//editar fecha  de vcto del chq a girar(solo para proveedores)
                    blnCamFecChq=true;
                }
                if(objPerUsr.isOpcionEnabled(2444)){//editar motivo por el cual no se emite el chq(solo para proveedores)
                    blnCamMot=true;
                    butGuaMot.setVisible(true);
                    butGuaMot.setEnabled(true);
                }
            }
            //</editor-fold>
            
            configurarTblDat();
            configurarTblTot();
            configurarVenConTipDoc();
            configurarVenConPrv();            
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
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();      //Almacena los datos
            vecCab=new Vector(21);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cod.Loc.");
            vecCab.add(INT_TBL_DAT_COD_PER,"Cod."+strCliPrv.substring(0, 3)+".");
            vecCab.add(INT_TBL_DAT_NOM_PER,"Nombre "+strCliPrv+"");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR,"Tip.Doc");
            vecCab.add(INT_TBL_DAT_DES_LAR,"Tipo de Documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_FAC_PRV,"Num.Fac.Prv.");
            vecCab.add(INT_TBL_DAT_MOT_DOC,"Motivo");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_CHK_AUT_PAG,"Aut.");
            vecCab.add(INT_TBL_DAT_CHK_USR_AUT_AUX,"Autorizar Auxiliar");
            vecCab.add(INT_TBL_DAT_FEC_CHQ,"Fec.Chq.");
            vecCab.add(INT_TBL_DAT_COD_BEN,"Cód.Ben.");
            vecCab.add(INT_TBL_DAT_NOM_BEN,"Nom.Ben.");
            vecCab.add(INT_TBL_DAT_CHK_IMP,"Imp.");
            vecCab.add(INT_TBL_DAT_CHK_PAG_ARA,"Pag.Ara.");
            vecCab.add(INT_TBL_DAT_CHK_PAG_EXT,"Pag.Ext.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);      
            
            //Permisos de Usuarios     
            if(objParSis.getCodigoUsuario()==1){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            }
            else{
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            }            

            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos: Clientes */" >
            if(objParSis.getCodigoMenu()==2012){
                if(objPerUsr.isOpcionEnabled(2427)){//autorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2428)){//desautorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1769){
                if(objPerUsr.isOpcionEnabled(2435)){//autorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2436)){//desautorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }  
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Bancos: Clientes */" >
            if(objParSis.getCodigoMenu()==2016){
                if(objPerUsr.isOpcionEnabled(2429)){//autorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2430)){//desautorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }  
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Bancos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1773){
                if(objPerUsr.isOpcionEnabled(2437)){//autorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2438)){//desautorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }     
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos y Bancos: Clientes */" >
            if(objParSis.getCodigoMenu()==2020){
                if(objPerUsr.isOpcionEnabled(2431)){//autorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2432)){//desautorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2433)){//autorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2434)){//desautorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }   
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="/* Autorización de Pagos y Bancos: Proveedores */" >
            if(objParSis.getCodigoMenu()==1777){
                if(objPerUsr.isOpcionEnabled(2439)){//autorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2440)){//desautorizar pagos
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2441)){//autorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2442)){//desautorizar banco
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2443)){//editar fecha  de vcto del chq a girar(solo para proveedores)
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
                if(objPerUsr.isOpcionEnabled(2444)){//editar motivo por el cual no se emite el chq(solo para proveedores)
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                }
            }     
            //</editor-fold>
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            objTblPopMnu.setMarcarCasillasVerificacionEnabled(true);
            objTblPopMnu.setDesmarcarCasillasVerificacionEnabled(true);            
            
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilEli[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    //if( (objTblPopMnu.isMarcarCasillasVerificacionEnabled()) || (objTblPopMnu.isDesmarcarCasillasVerificacionEnabled()) ){
                        intFilEli=tblDat.getSelectedRows();
                    //}                    
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if( (objTblPopMnu.isClickMarcarCasillasVerificacion()) || (objTblPopMnu.isClickDesmarcarCasillasVerificacion())  ){
                        for(int p=0;p<(intFilEli.length); p++){
                            //Se guardan los datos necesarios en el log.
                            generaArchivoTextoCheckBox(intFilEli[p]);
                        }                        
                    }
                }
            });               
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat);
            
            //Configurar JTable: Editor de ordenamiento.
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_PER).setPreferredWidth(50);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_PER).setPreferredWidth(150);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(10);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(60);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(10);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(10);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(10);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_FAC_PRV).setPreferredWidth(80);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_MOT_DOC).setPreferredWidth(110);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUT_PAG).setPreferredWidth(50);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setPreferredWidth(50);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_CHQ).setPreferredWidth(70);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BEN).setPreferredWidth(80);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_BEN).setPreferredWidth(80);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_IMP).setPreferredWidth(30);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_ARA).setPreferredWidth(30);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_EXT).setPreferredWidth(30);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_USR_AUT_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BEN, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_BEN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_PAG_ARA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_PAG_EXT, tblDat);

            if( (objParSis.getCodigoMenu()==2012) || (objParSis.getCodigoMenu()==2016)  ||  (objParSis.getCodigoMenu()==2020)  ){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_CHQ, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_FAC_PRV, tblDat);
            }
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setResizable(false);
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);            
            
            //Configurar JTable: Renderizar celdas.
            java.awt.Color colFonCol;
            colFonCol=new java.awt.Color(250,250,250);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(colFonCol);
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(colFonCol);
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            //objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_FAC_PRV).setCellRenderer(objTblCelRenLbl);
            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblCelRenLbl=null;

            //Configurar JTable: Color
            objTblCelRenLbl=new ZafTblCelRenLbl();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_PER).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_PER).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_DES_LAR).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUM_FAC_PRV).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_MOT_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUT_PAG).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_CHQ).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_BEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_BEN).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_IMP).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_ARA).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_EXT).setCellRenderer(objTblCelRenLbl);
            
            tblDat.getTableHeader().setReorderingAllowed(false); 

            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strNomCli="";
                String strNomBen="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(0,153,153);
                    strNomCli=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_NOM_PER).toString();
                    strNomBen=objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_DAT_NOM_BEN).toString();

                    if (strNomCli.equals(strNomBen)){ //Cambia de color si el beneficiario es distinto al nombre del cliente.
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    }
                    else{
                        objTblCelRenLbl.setForeground(colFonColCru);
                    }
                }
            });

             //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAux=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setCellRenderer(objTblCelRenChkAux);
            objTblCelRenChkAux=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAux=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setCellEditor(objTblCelEdiChkAux);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUT_PAG).setCellRenderer(objTblCelRenChk);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_IMP).setCellRenderer(objTblCelRenChk);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_ARA).setCellRenderer(objTblCelRenChk);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_PAG_EXT).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUT_PAG).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strNomCliFilSel="";
                String strNomBenFilSel="";
                boolean blnIsChkBef;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    strNomCliFilSel=objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_PER).toString();
                    strNomBenFilSel=objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_BEN).toString();
                    
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)){
                        if((blnAutPag) && (blnDesAutPag) ){
                            blnIsChkBef=true;
                            objTblCelEdiChk.setCancelarEdicion(false);
                        }
                        else
                            objTblCelEdiChk.setCancelarEdicion(true);
                    }
                    else{
                        blnIsChkBef=false;
                        if(blnAutPag ){
                            if(strNomCliFilSel.equals(strNomBenFilSel)){
                                if((objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && (objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                    if(( ! blnAutPag) && ( ! blnDesAutPag) ){
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                    if(blnDesAutPag){
                                        objTblCelEdiChk.setCancelarEdicion(false);
                                    }
                                    else{
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }
                                else if((objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && ( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                    if(blnAutPag){
                                        objTblCelEdiChk.setCancelarEdicion(false);
                                    }
                                    else{
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }
                                else if(( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && ( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                    if( ( ! blnAutPag) && ( ! blnDesAutPag)){
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }

                                    if(blnAutPag){
                                        objTblCelEdiChk.setCancelarEdicion(false);
                                    }
                                    else{
                                        objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }
                            }
                            else{
                                if(isBeneficiarioAutorizado(intFil)){
                                    objTblCelEdiChk.setCancelarEdicion(false);
                                }
                                else{//se presenta el mensaje porq aun no se autoriza ninguno similar.
                                    String strMsg="";
                                    strMsg+="<HTML>";
                                    strMsg+="El nombre de la empresa y beneficiario son diferentes. <BR>";
                                    strMsg+="Empresa:       " + strNomCliFilSel + "<BR>";
                                    strMsg+="Beneficiario:  " + strNomBenFilSel + "<BR>";
                                    strMsg+="¿Está seguro que desea autorizar el pago?";
                                    strMsg+="</HTML>";
                                    switch (mostrarMsgCon(strMsg)){
                                        case 0: //YES_OPTION
                                           arlRegBen=new ArrayList();
                                           arlRegBen.add(INT_ARL_COD_EMP, "" + objParSis.getCodigoEmpresa());
                                           arlRegBen.add(INT_ARL_COD_CLI, "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_PER).toString());
                                           arlRegBen.add(INT_ARL_NOM_CLI, "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_PER).toString());
                                           arlRegBen.add(INT_ARL_COD_BEN, "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_COD_BEN).toString());
                                           arlRegBen.add(INT_ARL_NOM_BEN, "" + objTblMod.getValueAt(intFil, INT_TBL_DAT_NOM_BEN).toString());
                                           arlDatBen.add(arlRegBen);
                                            if((objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && (objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                                if(( ! blnAutPag) && ( ! blnDesAutPag) ){
                                                    objTblCelEdiChk.setCancelarEdicion(true);
                                                }
                                                if(blnDesAutPag){
                                                    objTblCelEdiChk.setCancelarEdicion(false);
                                                }
                                                else{
                                                    objTblCelEdiChk.setCancelarEdicion(true);
                                                }
                                            }
                                            else if((objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && ( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                                if(blnAutPag){
                                                    objTblCelEdiChk.setCancelarEdicion(false);
                                                }
                                                else{
                                                    objTblCelEdiChk.setCancelarEdicion(true);
                                                }
                                            }
                                            else if(( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUT_PAG)) && ( ! objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_USR_AUT_AUX)) ){
                                                if( ( ! blnAutPag) && ( ! blnDesAutPag)){
                                                    objTblCelEdiChk.setCancelarEdicion(true);
                                                }

                                                if(blnAutPag){
                                                    objTblCelEdiChk.setCancelarEdicion(false);
                                                }
                                                else{
                                                    objTblCelEdiChk.setCancelarEdicion(true);
                                                }
                                            }
                                            break;
                                        case 1: //NO_OPTION
                                            objTblCelEdiChk.setCancelarEdicion(true);
                                            break;
                                        case 2: //CANCEL_OPTION
                                            objTblCelEdiChk.setCancelarEdicion(true);
                                    }
                                }
                            }
                        }
                        else
                            objTblCelEdiChk.setCancelarEdicion(true);

                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    generaArchivoTextoCheckBox(intFil);
                    if(   ( ! blnIsChkBef) &&  (objTblMod.isChecked(intFil, intCol)) ){//si se lo esta seleccionando
                        cargarSaldoAutorizadoActual(intFil, intCol);
                        calcularActualTotalSinBco(intFil, "S");
                    }
                    else if(  (blnIsChkBef) &&  ( ! objTblMod.isChecked(intFil, intCol))  ){//si se esta quitando la seleccion
                        cargarSaldoAutorizadoActual(intFil, intCol);
                        if(existeCuentaBancariaSeleccionada(intFil, intCol)){
                            calcularActualTotalConBco(intFil, "R");
                        }
                        else{
                            calcularActualTotalSinBco(intFil, "R");
                        }
                        quitarSeleccionBanco(intFil);
                    }
                    else{
                        
                    }
                    cargarSaldoFinal();
                }
            });

            objTblCelEdiTxtFecChq=new ZafTblCelEdiTxt(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_FEC_CHQ).setCellEditor(objTblCelEdiTxtFecChq);
            objTblCelEdiTxtFecChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1, intCol=-1;
                String strFecChq="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    strFecChq=objTblMod.getValueAt(intFil, intCol)==null?"":(objTblMod.getValueAt(intFil, intCol).equals("")?"":objTblMod.getValueAt(intFil, intCol).toString());
                    
                    objTblCelEdiTxtFecChq.setCancelarEdicion(true);
                    if(blnCamFecChq){
                        objTblCelEdiTxtFecChq.setCancelarEdicion(false);
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    generaArchivoTextoCheckBox(intFil);
                    if( ! validaFechaVencimiento(intFil)){
                    //mostrarMsgInf("<HTML>Fecha inválida.</HTML>");
                    }
                }
            });
            
            //Libero los objetos auxiliares.
            tcmAux = null;
            
            intNumColCnfEstIni=objTblMod.getColumnCount();
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";       
            switch (intCol)
            {       
                case INT_TBL_DAT_LIN:
                    strMsg ="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'>";
                    strMsg+="<tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr>";
                    strMsg+="<tr> <td>&nbsp;</td> <td style='background-color:#009999'>&nbsp;</td> <td>Indica que el nombre del \"Beneficiario\" es distinto a  \""+strCliPrv+"\".</td></tr>"; //Color Turquesa
                    strMsg+="</table><br></html>";
                    break;                
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;   
                case INT_TBL_DAT_COD_LOC:
                    //strMsg="Código del local";
                    strMsg="<html><h3 style='text-align:center;'>Listado de locales</h3>"
                            + "<table border='1'>"
                            + "<tr><td><b>Cód.Loc.</b></td><td><b>Local</b></td></tr>"
                            + "<tr><td>4</td><td>TUVAL - California (005-001)</td></tr>"
                            + "<tr><td>12</td><td>TUVAL - Inmaconsa (005-003)</td></tr>"
                            + "<tr><td>13</td><td>TUVAL - Cuenca (008-001)</td></tr>"
                            + "<tr><td>14</td><td>TUVAL - Cuenca (008-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>15</td><td>TUVAL - Manta (009-001)</td></tr>"
                            + "<tr><td>16</td><td>TUVAL - Manta (009-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>17</td><td>TUVAL - Santo Domingo (010-001)</td></tr>"
                            + "<tr><td>18</td><td>TUVAL - Santo Domingo (010-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>19</td><td>TUVAL - Vía Daule (011-001)</td></tr>"
                            + "<tr><td>20</td><td>TUVAL - Vía Daule (011-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>21</td><td>TUVAL - Durán (012-001)</td></tr>"
                            + "<tr><td>22</td><td>TUVAL - Durán (012-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>23</td><td>TUVAL - Quito Norte (013-001)</td></tr>"
                            + "<tr><td>24</td><td>TUVAL - Quito Norte (013-002)(Inmaconsa)</td></tr>"
                            + "<tr><td>25</td><td>TUVAL - Quito Sur (014-001)</td></tr>"
                            + "<tr><td>26</td><td>TUVAL - Quito Sur (014-002)(Inmaconsa)</td></tr>"
                            + "</table><br></html>";
                    break;                       
                case INT_TBL_DAT_COD_PER:
                    strMsg="Código de "+strCliPrv+"";
                    break;
                case INT_TBL_DAT_NOM_PER:
                    strMsg="Nombre de "+strCliPrv+"";
                    break;                    
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;                           
                case INT_TBL_DAT_DES_COR:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR:
                    strMsg="Descripción larga del tipo de documento";
                    break;       
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;   
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código de registro";
                    break;   
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;   
                case INT_TBL_DAT_NUM_FAC_PRV:
                    strMsg="Número de factura de "+strCliPrv+"";
                    break;   
                case INT_TBL_DAT_MOT_DOC:
                    strMsg="Motivo de documento";
                    break;    
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;   
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;    
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor Pendiente";
                    break;    
                case INT_TBL_DAT_CHK_AUT_PAG:
                    strMsg="";
                    break;    
                case INT_TBL_DAT_CHK_USR_AUT_AUX:
                    strMsg="";
                    break;   
                case INT_TBL_DAT_FEC_CHQ:
                    strMsg="Fecha del cheque";
                    break;    
                case INT_TBL_DAT_COD_BEN:
                    strMsg="Código del beneficiario";
                    break;    
                case INT_TBL_DAT_NOM_BEN:
                    strMsg="Nombre del beneficiario";
                    break;  
                case INT_TBL_DAT_CHK_IMP:
                    strMsg="Indica si es un documento de importación";
                    break;   
                case INT_TBL_DAT_CHK_PAG_ARA:
                    strMsg="Indica si es un Pago de arancel";
                    break;        
                case INT_TBL_DAT_CHK_PAG_EXT:
                    strMsg="Indica si es un Pago al exterior";
                    break;                          
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
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
                    //tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    //calcularTot();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    } 
        
    /**
     * Configurar el formulario de tabla totales
     * @return
     */
    private boolean configurarTblTot(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);
            tblTot.setModel(objTblModTot);
            
            //Configurar JTable: Establecer tipo de selección.
            tblTot.setRowSelectionAllowed(true);
            tblTot.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblTot,INT_TBL_DAT_LIN);
            tblTot.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer formato.
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLblTot);
            tblTot.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++){
                tblTot.getColumnModel().getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tblTot.getColumnModel().getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tblTot.getColumnModel().getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tblTot.getColumnModel().getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tblTot.getColumnModel().getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblTot);
            //objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_CHK_USR_AUT_AUX, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_BEN, tblTot);
//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_NOM_BEN, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_CHK_IMP, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_CHK_PAG_ARA, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_CHK_PAG_EXT, tblTot);

            if( (objParSis.getCodigoMenu()==2012) || (objParSis.getCodigoMenu()==2016)  ||  (objParSis.getCodigoMenu()==2020)  ){
                objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_FEC_CHQ, tblTot);
                objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_NUM_FAC_PRV, tblTot);
            }            

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);
            tblTot.getColumnModel().getColumn(INT_TBL_DAT_CHK_USR_AUT_AUX).setResizable(false);            

            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnDat.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            tblTot.setTableHeader(null);

            //Adicionar el listener que controla el redimensionamiento de las columnas.
            ZafTblColModLis objTblColModLisCta=new ZafTblColModLis();
            tblDat.getColumnModel().addColumnModelListener(objTblColModLisCta);

            //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
            barDat=spnDat.getHorizontalScrollBar();
            barTot=spnTot.getHorizontalScrollBar();

            //PARA DESPLAZAMIENTOS DE CELDAS
            barDat.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barTot.setValue(evt.getValue());
                }
            });
            barTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                    barDat.setValue(evt.getValue());
                }
            });

            spnTot.setColumnHeader(null);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTot.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
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
    
    /**
     * Esta función configura la "Ventana de consulta" que seró utilizada para
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");

            //Armar sentencia SQL
            strSQL ="";
            strSQL+=" SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
            strSQL+=" FROM tbm_cabTipDoc AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
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
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
     * Esta función configura la "Ventana de consulta" que seró utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            arlCam.add("a1.st_Exp");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            arlAli.add("Exportador");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            arlAncCol.add("75");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT a1.* FROM (";
            strSQL+="     SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, 'N' AS st_Exp";
            strSQL+="     FROM tbm_cli AS a1";
            strSQL+="     WHERE a1.st_reg NOT IN('I','E') ";
            strSQL+="     AND a1.co_emp=" + objParSis.getCodigoEmpresa();
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   )
                strSQL+=" AND a1.st_prv='S'";
            else
                strSQL+=" AND a1.st_cli='S'";
            
            strSQL+="     UNION ALL ";
            strSQL+="     SELECT a1.co_Exp AS co_cli, CAST('0' AS CHARACTER VARYING) AS tx_ide, a1.tx_nom2 AS tx_nom, a1.tx_dir, 'S' AS st_Exp";
            strSQL+="     FROM tbm_expImp AS a1";
            strSQL+="     WHERE a1.st_reg NOT IN('I','E')";
            strSQL+=" ) AS a1";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=4;
            intColOcu[1]=5;
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPrv.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
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
     * registro de la base de datos. El tipo de bósqueda determina si se debe hacer
     * una bósqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se estó buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtNomPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        txtEstExp.setText(vcoPrv.getValueAt(5));
                        if(   (txtNomPrv.getText().length()>0) || (txtCodPrv.getText().length()>0)  ){
                            txtNomPrvDes.setText("");
                        }
                    }
                    break;
                case 1: //Búsqueda directa por "Nómero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtNomPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        txtEstExp.setText(vcoPrv.getValueAt(5));
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
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtNomPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            txtEstExp.setText(vcoPrv.getValueAt(5));
                            if(   (txtNomPrv.getText().length()>0) || (txtCodPrv.getText().length()>0)  ){
                                txtNomPrvDes.setText("");
                            }
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
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtNomPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                        txtEstExp.setText(vcoPrv.getValueAt(5));
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
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtNomPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                            txtEstExp.setText(vcoPrv.getValueAt(5));
                            if(   (txtNomPrv.getText().length()>0) || (txtCodPrv.getText().length()>0)  ){
                                txtNomPrvDes.setText("");
                            }
                        }
                        else
                        {
                            txtNomPrv.setText(strDesLarPrv);
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
     * Esta función permite sumar/restar valores al seleccionar un pago
     * @param fila La fila donde se encuentra el pago autorizado
     * @param columna La columna donde se encuentra el valor pendiente
     * @return true: Si se pudo realizar el cálculo
     * <BR>false: En el caso contrario.
     */
    private boolean cargarSaldoAutorizadoActual(int fila, int columna){ 
        boolean blnRes=true;
        BigDecimal bgdSalAutAct=new BigDecimal("0");
        BigDecimal bgdSumSalAutAct=new BigDecimal("0");
        BigDecimal bgdValAutSel=new BigDecimal("0");
        BigDecimal bgdValTotSinBco=new BigDecimal("0");
        BigDecimal bgdSumTotSinBco=new BigDecimal("0");
        try{
            //contiene el valor que se esta autorizando
            bgdValAutSel=new BigDecimal("" + objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN)==null?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN).toString());
            //contiene el valor de "fila:saldo autorizado actual-columna:valor pendiente"
            bgdSalAutAct=objTblModTot.getValueAt(2, INT_TBL_DAT_VAL_PEN)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, INT_TBL_DAT_VAL_PEN).toString());
            //contiene el valor de "fila:saldo autorizado actual - columna total sin bco"
            bgdValTotSinBco=objTblModTot.getValueAt(2, intNumColIniVar)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, intNumColIniVar).toString());
            if(objTblMod.isChecked(fila, columna)){//se suma
                bgdSumSalAutAct=bgdSalAutAct.add(bgdValAutSel);
                bgdSumTotSinBco=bgdValTotSinBco.add(bgdValAutSel);
            }
            else{//se resta
                 bgdSumSalAutAct=bgdSalAutAct.subtract(bgdValAutSel);
                 bgdSumTotSinBco=bgdValTotSinBco.subtract(bgdValAutSel);
            }
            objTblModTot.setValueAt(bgdSumSalAutAct, 2, INT_TBL_DAT_VAL_PEN );
            //objTblModTot.setValueAt(bgdSumTotSinBco, 2, intNumColIniVar );
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
        }
        return blnRes;
    }

    /**
     * Está función va sumando/restando al momento de seleccionar el banco
     * @param fila La fila que contiene el saldo autorizado.
     * @param columna La columna del banco seleccionado
     * @return true: Si se pudo realizar el cálculo
     * <BR>false: En el caso contrario.
     */
    private boolean cargarSaldoBancarioActual(int fila, int columna){ 
        boolean blnRes=true;
        BigDecimal bgdSalAutAct=new BigDecimal("0");
        BigDecimal bgdSumSalAutAct=new BigDecimal("0");
        BigDecimal bgdValAutSel=new BigDecimal("0");
        BigDecimal bgdValConBco=new BigDecimal("0");
        BigDecimal bgdSumConBco=new BigDecimal("0");
        try{
            //contiene el valor seleccionado a autorizar
            bgdValAutSel=new BigDecimal("" + objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN)==null?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN).toString());
            //contiene el valor "fila:saldo autorizado actual columna:banco seleccionado"
            bgdSalAutAct=objTblModTot.getValueAt(2, columna)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, columna).toString());
            //contiene el valor "fila:saldo autorizado actual-columna:total con bco"
            bgdValConBco=objTblModTot.getValueAt(2, (intNumColIniVar+1))==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, (intNumColIniVar+1)).toString());
            
            //System.out.println("bgdSalAutAct: "+bgdSalAutAct+ " - bgdValAutSel:"+bgdValAutSel );
            if(objTblMod.isChecked(fila, columna)){//se suma
                bgdSumSalAutAct=bgdSalAutAct.add(bgdValAutSel);
                bgdSumConBco=bgdValConBco.add(bgdValAutSel);
                //System.out.println("sumando: "+bgdSalAutAct+" + "+bgdValAutSel+" = "+bgdSumSalAutAct);
            }
            else{//se resta
                bgdSumSalAutAct=bgdSalAutAct.subtract(bgdValAutSel);
                bgdSumConBco=bgdValConBco.subtract(bgdValAutSel);
                //System.out.println("restando: "+bgdSalAutAct+" - "+bgdValAutSel+" = "+bgdSumSalAutAct);
            }
            objTblModTot.setValueAt(bgdSumSalAutAct, 2, columna );
            //objTblModTot.setValueAt(bgdSumConBco, 2, (intNumColIniVar+1) ); 
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función calcula valores totales que no tienen asignado un banco
     * @param fila La fila donde se encuentra el valor que se debe sumar.
     * @param sumaResta El valor a sumar/restar.
     * @return true: Si se pudo realizar el cálculo
     * <BR>false: En el caso contrario.
     */
    private boolean calcularActualTotalSinBco(int fila, String sumaResta){
        boolean blnRes=true;
        BigDecimal bgdVal=new BigDecimal("0");
        BigDecimal bgdValIniSinBco=new BigDecimal("0");
        try{
            bgdValIniSinBco=new BigDecimal(objTblModTot.getValueAt(2, intNumColIniVar)==null?"0":objTblModTot.getValueAt(2, intNumColIniVar).toString());
            bgdVal=new BigDecimal("0");
            //si el chk esta seleccionado y el chk aux no ->barrerse la tabla y recalcular
            bgdVal=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN)==null?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN).toString());
            if(sumaResta.equals("S")){
                objTblModTot.setValueAt((bgdValIniSinBco.add(bgdVal)), 2, intNumColIniVar);
            }
            else if(sumaResta.equals("R")){
                objTblModTot.setValueAt((bgdValIniSinBco.subtract(bgdVal)), 2, intNumColIniVar);
            }
            bgdValIniSinBco=new BigDecimal("0");
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
        }
        return blnRes;
    }

    /**
      * Esta función calcula valores totales de bancos
      * @param fila La fila donde se encuentra el valor que se debe sumar al valor del total bancario actual.
      * @param sumaResta El valor a sumar/restar.
      * @return true: Si se pudo realizar el cálculo
      * <BR>false: En el caso contrario.
      */
    private boolean calcularActualTotalConBco(int fila, String sumaResta){//suma esta seleccionada cta bancaria
        boolean blnRes=true;
        BigDecimal bgdValConBco=new BigDecimal("0");
        BigDecimal bgdVal=new BigDecimal("0");
        int intExiCtaRelValAut=0;// 0 no existe cuenta asociada; 1 existe cuenta asociada
        String strLin="";
        BigDecimal bgdValIniConBco=new BigDecimal("0");
        BigDecimal bgdValIniSinBco=new BigDecimal("0");
        try{
            bgdValIniConBco=new BigDecimal(objTblModTot.getValueAt(2, (intNumColIniVar+1))==null?"0":objTblModTot.getValueAt(2, (intNumColIniVar+1)).toString());
            bgdValIniSinBco=new BigDecimal(objTblModTot.getValueAt(2, intNumColIniVar)==null?"0":objTblModTot.getValueAt(2, intNumColIniVar).toString());

            bgdVal=new BigDecimal("0");
            //si el chk esta seleccionado y el chk aux no ->barrerse la tabla y recalcular
            bgdVal=new BigDecimal(objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN)==null?"0":objTblMod.getValueAt(fila, INT_TBL_DAT_VAL_PEN).toString());
            bgdValConBco=bgdValConBco.add(bgdVal);
            if(sumaResta.equals("S")){
                 objTblModTot.setValueAt((bgdValIniConBco.add(bgdValConBco)), 2, (intNumColIniVar+1));
                 if(objTblMod.isChecked(fila, INT_TBL_DAT_CHK_AUT_PAG))
                     objTblModTot.setValueAt((bgdValIniSinBco.subtract(bgdValConBco)), 2, intNumColIniVar);
            }
            else if(sumaResta.equals("R")){
                 objTblModTot.setValueAt((bgdValIniConBco.subtract(bgdValConBco)), 2, (intNumColIniVar+1));
                 if(objTblMod.isChecked(fila, INT_TBL_DAT_CHK_AUT_PAG))
                     objTblModTot.setValueAt((bgdValIniSinBco.add(bgdValConBco)), 2, intNumColIniVar);
            }
            bgdValConBco=new BigDecimal("0");
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
           blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función quita la selección en las columnas de bancos
     * @param fila La fila donde se debe quitar la selección.
     * @return true: Si se pudo quitar la selección del banco
     * <BR>false: En el caso contrario.
     */
    private boolean quitarSeleccionBanco(int fila){
       boolean blnRes=true;
       try{
            for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                if(objTblMod.isChecked(fila, j)) {
                    objTblMod.setChecked(false, fila, j);
                    /*Rose: Para que recalcule el saldo bancario despues de desmarcar la autorización de pago de un banco.*/
                    cargarSaldoBancarioActual(fila, j);                  
                }
            }
       }
       catch(Exception e){
          objUti.mostrarMsgErr_F1(this, e);
          blnRes=false;
       }
       return blnRes;
       
   }
    
    private boolean isBeneficiarioAutorizado(int filaSeleccionada){
        boolean blnRes=false;
        int intFilSelBen=filaSeleccionada;
        String strTblCodCli="", strTblNomCli="";
        String strTblCodBen="", strTblNomBen="";
        String strArlCodCli="", strArlNomCli="";
        String strArlCodBen="", strArlNomBen="";
        int intEnc=0;
        try{
            strTblCodCli=objTblMod.getValueAt(intFilSelBen, INT_TBL_DAT_COD_PER).toString();
            strTblNomCli=objTblMod.getValueAt(intFilSelBen, INT_TBL_DAT_NOM_PER).toString();
            strTblCodBen=objTblMod.getValueAt(intFilSelBen, INT_TBL_DAT_COD_BEN).toString();
            strTblNomBen=objTblMod.getValueAt(intFilSelBen, INT_TBL_DAT_NOM_BEN).toString();
            for(int i=0; i<arlDatBen.size(); i++){
                strArlCodCli=objUti.getStringValueAt(arlDatBen, i, INT_ARL_COD_CLI);
                strArlNomCli=objUti.getStringValueAt(arlDatBen, i, INT_ARL_NOM_CLI);
                strArlCodBen=objUti.getStringValueAt(arlDatBen, i, INT_ARL_COD_BEN);
                strArlNomBen=objUti.getStringValueAt(arlDatBen, i, INT_ARL_NOM_BEN);
                if(  (strTblCodCli.equals(strArlCodCli)) && (strTblNomCli.equals(strArlNomCli)) && (strTblCodBen.equals(strArlCodBen)) && (strTblNomBen.equals(strArlNomBen)) ){
                    blnRes=true;
                    intEnc++;
                    break;
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite guardar un log de datos que han sido modificados al momento
     * dar click en el botón Guardar
     * @return true: Si se pudo guardar datos en el archivo ZafCxP03.txt
     * <BR>false: En el caso contrario.
     */
    private boolean generaArchivoTextoGuardar(){
        boolean blnRes=true;
        FileWriter filWrt = null;
        PrintWriter prnWrt = null;
        String strLinSet="";
        String strLin="";
        boolean blnIsBcoSel=false;
        int intColBcoSel=-1;
        String strVecCodCta="", strVecNumCol="";
        int intVecNumCol=0;
        try{
            filWrt = new FileWriter("C:/Zafiro/logs/CxP/ZafCxP03/ZafCxP03.txt",true);//el parametro true permite que se vaya agregando y no sobreescribiendo
            prnWrt = new PrintWriter(filWrt);
            
            //String strRutFilTxt=objParSis.getDirectorioSistema();
            //filWrt = new FileWriter(strRutFilTxt + "/logs/CxP/ZafCxP03/ZafCxP03.txt", true); //Rose 1
            //prnWrt = new PrintWriter(filWrt);

            //Obtener la fecha del servidor.
            datFecAuxFic=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAuxFic==null)
                return false;
            strAux=objUti.formatearFecha(datFecAuxFic, objParSis.getFormatoFechaHoraBaseDatos());//fe_ing
            
            prnWrt.println("---------- " + strAux + " ----------");

            prnWrt.println("Cód.Emp;Cód.Loc;Cód.Tip.Doc;Cód.Doc;Cód.Reg;Est.Aut.Pag;Est.Aut.Pag.Aux;Val.Pen;Fec.Chq;Cód.Cta.Ban;Cód.Usr;");
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                if(strLin.equals("M")){
                    //prnWrt.println("Linea " + i);
                    
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + ";";
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + ";";
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + ";";
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + ";";
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + ";";
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT_PAG))
                        strLinSet+="S" + ";";
                    else
                        strLinSet+="N" + ";";
                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_USR_AUT_AUX))
                        strLinSet+="S" + ";";
                    else
                        strLinSet+="N" + ";";

                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PEN) + ";";
                    strLinSet+="" + objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CHQ) + ";";
                    
                    //adicionar las columnas de bancos
                    blnIsBcoSel=false;
                    for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                        if(objTblMod.isChecked(i, j)){
                            blnIsBcoSel=true;
                            intColBcoSel=j;
                        }
                    }
                    if(blnIsBcoSel){
                        if(intColBcoSel>=0){
                            for(int y=0; y<arlDatBco.size(); y++){
                                strVecCodCta=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COD_BCO);
                                strVecNumCol=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COL_BCO);
                                intVecNumCol=Integer.parseInt(strVecNumCol);
                                if(intVecNumCol==intColBcoSel){
                                    strLinSet+="" + strVecCodCta + ";";
                                    break;
                                }
                            }                            
                        }
                    }
                    else
                        strLinSet+=" " + ";";
                    
                    strLinSet+="" + objParSis.getCodigoUsuario() + ";";
                    prnWrt.println("" + strLinSet);
                    strLinSet="";
                }
            }            
            prnWrt.println(" ");
            filWrt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
       return blnRes;
    }

    /**
     * Esta función permite guardar un log de datos que han sido modificados al momento
     * dar click sobre el componente checkbox de Autorizacion de Pago, Autorizacion de Bco, Fecha de cheque.
     * La cabecera de cada bloque de datos especificará que tipo de autorización se efectuó
     * @return true: Si se pudo guardar datos en el archivo ZafCxP03_01.txt
     * <BR>false: En el caso contrario.
     */
    private boolean generaArchivoTextoCheckBox(int fila){
        boolean blnRes=true;
        FileWriter filWrt = null;
        PrintWriter prnWrt = null;
        String strLinSet="";
        String strLin="";
        boolean blnIsBcoSel=false;
        int intColBcoSel=-1;
        String strVecCodCta="", strVecNumCol="";
        int intVecNumCol=0;
        int intLisFilSel=fila;
        try{
            filWrt = new FileWriter("C:/Zafiro/logs/CxP/ZafCxP03/ZafCxP03_01.txt",true);//el parametro true permite que se vaya agregando y no sobreescribiendo
            prnWrt = new PrintWriter(filWrt);
            
            //String strRutFilTxt=objParSis.getDirectorioSistema();
            //filWrt = new FileWriter(strRutFilTxt + "/logs/CxP/ZafCxP03/ZafCxP03_01.txt", true); //Rose 2
            //prnWrt = new PrintWriter(filWrt);

            //Obtener la fecha del servidor.
            datFecAuxFic=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecAuxFic==null)
                return false;
            strAux=objUti.formatearFecha(datFecAuxFic, objParSis.getFormatoFechaHoraBaseDatos());//fe_ing
            
            prnWrt.println("---------- " + strAux + " ----------");

            prnWrt.println("Cód.Emp;Cód.Loc;Cód.Tip.Doc;Cód.Doc;Cód.Reg;Est.Aut.Pag;Est.Aut.Pag.Aux;Val.Pen;Fec.Chq;Cód.Cta.Ban;Cód.Usr;");
            strLin=objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_LIN).toString();
            if(strLin.equals("M")){
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_COD_EMP) + ";";
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_COD_LOC) + ";";
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_COD_TIP_DOC) + ";";
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_COD_DOC) + ";";
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_COD_REG) + ";";
                if(objTblMod.isChecked(intLisFilSel, INT_TBL_DAT_CHK_AUT_PAG))
                    strLinSet+="S" + ";";
                else
                    strLinSet+="N" + ";";
                if(objTblMod.isChecked(intLisFilSel, INT_TBL_DAT_CHK_USR_AUT_AUX))
                    strLinSet+="S" + ";";
                else
                    strLinSet+="N" + ";";

                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_VAL_PEN) + ";";
                strLinSet+="" + objTblMod.getValueAt(intLisFilSel, INT_TBL_DAT_FEC_CHQ) + ";";
                
                //dato de columnas de bancos
                blnIsBcoSel=false;
                for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                    if(objTblMod.isChecked(intLisFilSel, j)){
                        blnIsBcoSel=true;
                        intColBcoSel=j;
                    }
                }
                if(blnIsBcoSel){
                    if(intColBcoSel>=0){
                        for(int y=0; y<arlDatBco.size(); y++){
                            strVecCodCta=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COD_BCO);
                            strVecNumCol=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COL_BCO);
                            intVecNumCol=Integer.parseInt(strVecNumCol);
                            if(intVecNumCol==intColBcoSel){
                                strLinSet+="" + strVecCodCta + ";";
                                break;
                            }
                        }                            
                    }
                }
                else
                    strLinSet+=" " + ";";
                strLinSet+="" + objParSis.getCodigoUsuario() + ";";
            }
            
            prnWrt.println("" + strLinSet);
            prnWrt.println(" ");
            filWrt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        panPrv = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtNomPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblNomPrvDes = new javax.swing.JLabel();
        txtNomPrvDes = new javax.swing.JTextField();
        txtEstExp = new javax.swing.JTextField();
        panValPen = new javax.swing.JPanel();
        txtValPndDes = new javax.swing.JTextField();
        lblValPndDes = new javax.swing.JLabel();
        txtValPndHas = new javax.swing.JTextField();
        lblValPndHas = new javax.swing.JLabel();
        panChk = new javax.swing.JPanel();
        chkMosDocNotAut = new javax.swing.JCheckBox();
        chkMosNotCre = new javax.swing.JCheckBox();
        chkExcVenEmpGrp = new javax.swing.JCheckBox();
        chkMosDocSinFacPrv = new javax.swing.JCheckBox();
        chkMosValExc = new javax.swing.JCheckBox();
        txtValExc = new javax.swing.JTextField();
        chkMosDocAllVisBue = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        panDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panTot = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butGuaMot = new javax.swing.JButton();
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
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panGen.setLayout(new java.awt.BorderLayout());

        panFil.setPreferredSize(new java.awt.Dimension(100, 142));
        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los documentos por pagar");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 0, 420, 16);

        optFil.setText("Sólo los documentos por pagar que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 16, 550, 16);

        lblTipDoc.setText("Tipo de documento:");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(20, 106, 125, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(132, 106, 15, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(147, 106, 110, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(257, 106, 264, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(521, 106, 20, 20);

        panPrv.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre del Proveedor"));
        panPrv.setPreferredSize(new java.awt.Dimension(100, 38));
        panPrv.setVerifyInputWhenFocusTarget(false);
        panPrv.setLayout(null);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panPrv.add(lblPrv);
        lblPrv.setBounds(20, 15, 80, 20);

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
        panPrv.add(txtCodPrv);
        txtCodPrv.setBounds(130, 15, 70, 20);

        txtNomPrv.setVerifyInputWhenFocusTarget(false);
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
        panPrv.add(txtNomPrv);
        txtNomPrv.setBounds(200, 15, 300, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panPrv.add(butPrv);
        butPrv.setBounds(500, 15, 20, 20);

        lblNomPrvDes.setText("Desde:");
        panPrv.add(lblNomPrvDes);
        lblNomPrvDes.setBounds(140, 40, 60, 20);

        txtNomPrvDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrvDesFocusLost(evt);
            }
        });
        panPrv.add(txtNomPrvDes);
        txtNomPrvDes.setBounds(200, 40, 300, 20);
        panPrv.add(txtEstExp);
        txtEstExp.setBounds(510, 40, 20, 20);

        panFil.add(panPrv);
        panPrv.setBounds(20, 125, 550, 65);

        panValPen.setBorder(javax.swing.BorderFactory.createTitledBorder("Valor Pendiente"));
        panValPen.setPreferredSize(new java.awt.Dimension(100, 38));
        panValPen.setLayout(null);

        txtValPndDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValPndDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValPndDesFocusLost(evt);
            }
        });
        panValPen.add(txtValPndDes);
        txtValPndDes.setBounds(200, 10, 100, 20);

        lblValPndDes.setText("Desde:");
        panValPen.add(lblValPndDes);
        lblValPndDes.setBounds(140, 10, 60, 20);

        txtValPndHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValPndHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValPndHasFocusLost(evt);
            }
        });
        panValPen.add(txtValPndHas);
        txtValPndHas.setBounds(400, 10, 100, 20);

        lblValPndHas.setText("Hasta:");
        panValPen.add(lblValPndHas);
        lblValPndHas.setBounds(340, 10, 60, 20);

        panFil.add(panValPen);
        panValPen.setBounds(20, 188, 550, 40);

        panChk.setPreferredSize(new java.awt.Dimension(100, 95));
        panChk.setRequestFocusEnabled(false);
        panChk.setLayout(null);

        chkMosDocNotAut.setText("Mostrar sólo los documentos por pagar que todavía no han sido autorizados");
        chkMosDocNotAut.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMosDocNotAutStateChanged(evt);
            }
        });
        panChk.add(chkMosDocNotAut);
        chkMosDocNotAut.setBounds(5, 3, 540, 16);

        chkMosNotCre.setText("Mostrar Notas de Crédito");
        chkMosNotCre.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMosNotCreStateChanged(evt);
            }
        });
        panChk.add(chkMosNotCre);
        chkMosNotCre.setBounds(5, 21, 540, 16);

        chkExcVenEmpGrp.setSelected(true);
        chkExcVenEmpGrp.setText("Excluir las ventas entre las empresas del grupo");
        chkExcVenEmpGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkExcVenEmpGrpActionPerformed(evt);
            }
        });
        panChk.add(chkExcVenEmpGrp);
        chkExcVenEmpGrp.setBounds(5, 37, 540, 16);

        chkMosDocSinFacPrv.setText("Mostrar los documentos que no tienen asignada la factura del proveedor");
        chkMosDocSinFacPrv.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkMosDocSinFacPrvStateChanged(evt);
            }
        });
        panChk.add(chkMosDocSinFacPrv);
        chkMosDocSinFacPrv.setBounds(5, 53, 540, 16);

        chkMosValExc.setText("Mostrar valores en exceso mayores a:");
        panChk.add(chkMosValExc);
        chkMosValExc.setBounds(5, 69, 270, 16);

        txtValExc.setText("10");
        panChk.add(txtValExc);
        txtValExc.setBounds(280, 69, 50, 17);

        chkMosDocAllVisBue.setText("Mostrar sólo los documentos que cumplan con todos los vistos buenos");
        panChk.add(chkMosDocAllVisBue);
        chkMosDocAllVisBue.setBounds(5, 85, 540, 16);

        panFil.add(panChk);
        panChk.setBounds(20, 227, 550, 100);

        panGen.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panGen);

        panRpt.setLayout(new java.awt.BorderLayout());

        panDat.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(452, 452));

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

        panDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panRpt.add(panDat, java.awt.BorderLayout.CENTER);

        panTot.setPreferredSize(new java.awt.Dimension(100, 90));
        panTot.setLayout(new java.awt.BorderLayout());

        spnTot.setPreferredSize(new java.awt.Dimension(452, 452));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTot.setViewportView(tblTot);

        panTot.add(spnTot, java.awt.BorderLayout.CENTER);

        panRpt.add(panTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(296, 26));
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
        butGua.setToolTipText("Guardar datos");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butGuaMot.setText("Guardar Motivo");
        butGuaMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaMotActionPerformed(evt);
            }
        });
        panBot.add(butGuaMot);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 16));
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

        getContentPane().add(panFrm);

        setBounds(430, 180, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
    
     /**
      * Función que realiza la consulta a través de un objeto Thread
      * @param evt
      */
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if(objTblMod.isDataModelChanged()){      
            isRegPro();
        }
        else{
            objTblMod.removeAllRows();
            lblMsgSis.setText("");
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
        }
    }//GEN-LAST:event_butConActionPerformed
    
    /**
     * Función que permite cerrar el formulario
     * @param evt
     */
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

    /**
     * Función que se ejecuta al momento de ejecutar una acción sobre el la Descripción corta del Tipo de Documento
     * @param evt
     */
    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    /**
     * Función que se ejecuta al momento de posicionarse sobre el la Descripción corta del Tipo de Documento
     * @param evt
     */
    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    /**
     * Función que se ejecuta al momento de perder el  foco sobre el la Descripción corta del Tipo de Documento
     * @param evt
     */
    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesCorTipDoc.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    /**
     * Función que se ejecuta al momento de ejecutar una acción sobre el la Descripción larga del Tipo de Documento
     * @param evt
     */
    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    /**
     * Función que se ejecuta al momento de posicionarse sobre el la Descripción larga del Tipo de Documento
     * @param evt
     */
    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    /**
     * Función que se ejecuta al momento de perder el  foco sobre el la Descripción larga del Tipo de Documento
     * @param evt
     */
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
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesLarTipDoc.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }        
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    /**
     * Función que cargar la ventana donde se muestra la información del Tipo de Documento
     * @param evt
     */
    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

    /**
     * Función que ejecuta la acción del evento guardar
     * @param evt
     */
    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if(isCamVal()){
            if(guardar()){
                mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

    /**
     * Función que ejecuta la acción al momento de colocar el cursor en checkbox de "Excluir Ventas entre Compañias"
     * @param evt
     */
    private void chkExcVenEmpGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkExcVenEmpGrpActionPerformed

}//GEN-LAST:event_chkExcVenEmpGrpActionPerformed

    /**
     * Función que se ejecuta al momento de posicionarse sobre el campo valor pendiente
     * @param evt
     */
    private void txtValPndHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValPndHasFocusGained
        txtValPndHas.selectAll();
}//GEN-LAST:event_txtValPndHasFocusGained

    /**
     * Función que se ejecuta al momento de perder el  foco sobre el campo valor pendiente
     * @param evt
     */
    private void txtValPndHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValPndHasFocusLost
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtValPndHas.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }  
    }//GEN-LAST:event_txtValPndHasFocusLost

    /**
     * Función que se ejecuta al momento de posicionarse sobre el campo valor pendiente
     * @param evt
     */
    private void txtValPndDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValPndDesFocusGained
        txtValPndDes.selectAll();
}//GEN-LAST:event_txtValPndDesFocusGained

    /**
     * Función que se ejecuta al momento de perder el  foco sobre el campo valor pendiente
     * @param evt
     */
    private void txtValPndDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValPndDesFocusLost
        if (txtValPndDes.getText().length()>0){
            if (txtValPndHas.getText().length()==0)
                txtValPndHas.setText(txtValPndDes.getText());
        }

        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtValPndDes.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }        
}//GEN-LAST:event_txtValPndDesFocusLost

    /**
     * Función que ejecuta la acción de la selección de "todos los registros"
     * @param evt
     */
    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if(optTod.isSelected()){
            optFil.setSelected(false);
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodPrv.setText("");
            txtNomPrv.setText("");
            txtEstExp.setText("");
            txtNomPrvDes.setText("");
            txtValPndDes.setText("");
            txtValPndHas.setText("");
            chkMosDocNotAut.setSelected(false);
            chkMosDocAllVisBue.setSelected(false);
            chkMosNotCre.setSelected(false);
            chkMosDocSinFacPrv.setSelected(false);
            chkMosValExc.setSelected(false);
        }
        else{
            if( ! optFil.isSelected()){
                optTod.setSelected(true);
            }
        }
}//GEN-LAST:event_optTodActionPerformed

    /**
     * Función que ejecuta la acción de la selección de "filtro de los registros"
     * @param evt
     */
    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if(optFil.isSelected()){
            optTod.setSelected(false);
        } 
        else{
            if( ! optTod.isSelected())
                optFil.setSelected(true);
        }
}//GEN-LAST:event_optFilActionPerformed

    /**
     * Función que ejecuta la acción en el campo Codigo de Proveedor
     * @param evt
     */
    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

    /**
     * Función que ejecuta la acción al momento de colocar el cursor en campo Codigo de Proveedor
     * @param evt
     */
    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

    /**
     * Función que ejecuta la acción al momento de perder el cursor en campo Codigo de Proveedor
     * @param evt
     */
    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)){
            if (txtCodPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtNomPrv.setText("");
                objTblMod.removeAllRows();
            } else{
                mostrarVenConPrv(1);
            }
        } else
            txtCodPrv.setText(strCodPrv);

        if(txtCodPrv.getText().length()>0){
            txtNomPrvDes.setText("");
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPrv.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
}//GEN-LAST:event_txtCodPrvFocusLost

    /**
     * Función que ejecuta la acción en el campo Descripcion larga de Proveedor
     * @param evt
     */
    private void txtNomPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrvActionPerformed
        txtNomPrv.transferFocus();
}//GEN-LAST:event_txtNomPrvActionPerformed

    /**
     * Función que posiciona en el campo Descripcion larga de Proveedor
     * @param evt
     */
    private void txtNomPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusGained
        strDesLarPrv=txtNomPrv.getText();
        txtNomPrv.selectAll();
}//GEN-LAST:event_txtNomPrvFocusGained

    /**
     * Función que se quita el cursor en el campo Descripcion larga de Proveedor
     * @param evt
     */
    private void txtNomPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomPrv.getText().equalsIgnoreCase(strDesLarPrv)){
            if (txtNomPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtNomPrv.setText("");
                objTblMod.removeAllRows();
            } else{
                mostrarVenConPrv(2);
                //Cargar los documentos pendientes sólo si ha cambiado el beneficiario.
            }
        } else
            txtNomPrv.setText(strDesLarPrv);

        if(txtNomPrv.getText().length()>0){
            txtNomPrvDes.setText("");
        }
        
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomPrv.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }        
}//GEN-LAST:event_txtNomPrvFocusLost

    /**
     * Función que ejecuta el llamado a la ventana que muestra la información del Proveedor
     * @param evt
     */
    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
        if (!txtCodPrv.getText().equals("")){
            //Cargar los documentos pendientes sólo si ha cambiado el beneficiario.
        }
}//GEN-LAST:event_butPrvActionPerformed

    /**
     * Función que ejecuta la acción del botón guardar
     * @param evt
     */
    private void butGuaMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaMotActionPerformed
        if(guardarMotivo()){
            mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
            objTblMod.removeAllRows();
            consultarReg();
        } else{
            mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
        }
}//GEN-LAST:event_butGuaMotActionPerformed

    private void txtNomPrvDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrvDesFocusLost
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomPrvDes.getText().length()>0)    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomPrvDesFocusLost

    private void chkMosDocNotAutStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMosDocNotAutStateChanged
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (chkMosDocNotAut.isSelected())    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkMosDocNotAutStateChanged

    private void chkMosDocSinFacPrvStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMosDocSinFacPrvStateChanged
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (chkMosDocSinFacPrv.isSelected())    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkMosDocSinFacPrvStateChanged

    private void chkMosNotCreStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkMosNotCreStateChanged
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (chkMosNotCre.isSelected())    {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_chkMosNotCreStateChanged


    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butGuaMot;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkExcVenEmpGrp;
    private javax.swing.JCheckBox chkMosDocAllVisBue;
    private javax.swing.JCheckBox chkMosDocNotAut;
    private javax.swing.JCheckBox chkMosDocSinFacPrv;
    private javax.swing.JCheckBox chkMosNotCre;
    private javax.swing.JCheckBox chkMosValExc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomPrvDes;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValPndDes;
    private javax.swing.JLabel lblValPndHas;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panChk;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panPrv;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTot;
    private javax.swing.JPanel panValPen;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtEstExp;
    private javax.swing.JTextField txtNomPrv;
    private javax.swing.JTextField txtNomPrvDes;
    private javax.swing.JTextField txtValExc;
    private javax.swing.JTextField txtValPndDes;
    private javax.swing.JTextField txtValPndHas;
    // End of variables declaration//GEN-END:variables


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
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     * @param strMsg
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son vólidos.
     * @return true: Si los campos son vólidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
       boolean blnRes=true;
        //Validar la "Cuenta".
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            if( ! validaFechaVencimiento(i)){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(i, i);
                tblDat.changeSelection(i, INT_TBL_DAT_FEC_CHQ, true, true);
                tblDat.requestFocus();
                blnRes=false;
                break;
            }
        }
        return blnRes;
    } 
    
   /**
    * Función que valida que la fecha de vencimiento asignada al pago debe ser una fecha y debe ser <= a 2 años del presente año
    * @param fila
    * @return true: Si se pudo efectuar la operación
    * <BR>false: En el caso contrario.
    */
    private boolean validaFechaVencimiento(int fila){
        boolean blnRes=true;
        int intFecSis[];
        int intFecVncChq[];
        String strFecRecChq="";
        String strFecVenChq="";
        try{
            dtpFecVenChq.setText("");strFecVenChq="";
            //fecha del sistema para comparar q no sea mayor a 2 años la fecha q se ingresa con la fecha del sistema
            intFecSis=dtpFecSis.getFecha(dtpFecSis.getText());
            strFecVenChq=objTblMod.getValueAt(fila, INT_TBL_DAT_FEC_CHQ)==null?"":objTblMod.getValueAt(fila, INT_TBL_DAT_FEC_CHQ).toString();

            if( ! strFecVenChq.toString().equals("")){
                dtpFecVenChq.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
                if(dtpFecVenChq.isFecha()){
                    intFecVncChq=dtpFecVenChq.getFecha(dtpFecVenChq.getText());
                    if(   (intFecVncChq[2]) > ((intFecSis[2])+2)  ){
                        mostrarMsgInf("El año solo puede ser igual o superior en dos al presente año.");
                        blnRes=false;
                    }
                }
                else{
                    strFecVenChq=strFecVenChq+ "/" + intFecSis[2];
                    dtpFecVenChq.setText(objUti.formatearFecha(strFecVenChq.trim(),"dd/MM/yyyy", "dd/MM/yyyy"));
                    if(dtpFecVenChq.isFecha()){
                        intFecVncChq=dtpFecVenChq.getFecha(dtpFecVenChq.getText());
                        strFecVenChq="" + intFecVncChq[0] + "/" + (intFecVncChq[1]<=9?"0" + intFecVncChq[1]:"" + intFecVncChq[1]) + "/" + (intFecSis[2]);
                        dtpFecVenChq.setText(strFecVenChq);
                        tblDat.setValueAt(strFecVenChq, fila, INT_TBL_DAT_FEC_CHQ);
                    }
                    else{
                        mostrarMsgInf("El dato ingresado no es una fecha.");
                        blnRes=false;
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
     * Función que permite establecer si existen cuentas bancarias seleccionadas
     * @param fila: Fila en la que se debe verificar si existe banco seleccionado
     * @param columna: Columna en la que se debe verificar si existe banco seleccionado
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean existeCuentaBancariaSeleccionada(int fila, int columna){
       boolean blnRes=false;
       int intNumBcoSel=0;
       try{
           System.out.println("---------------------------------------");
           System.out.println("tblDat.getColumn(): " + tblDat.getColumnCount());
           System.out.println("objTblMod.getColumn(): " + objTblMod.getColumnCount());
           System.out.println("intNumColIniBco: " + intNumColIniBco);
           System.out.println("intNumColIniBco: " + intNumColIniBco);
           System.out.println("intNumColFinBco: " + intNumColFinBco);
           System.out.println("fila: " + fila);
           System.out.println("columna: " + columna);
           for(int j=intNumColIniBco; j<intNumColFinBco; j++){
               if(j==columna){
               }
               else{
                   if(objTblMod.isChecked(fila, j)){
                       intNumBcoSel++;
                       break;
                   }
               }
           }
           if(intNumBcoSel>0){
               blnRes=true;
           }
       }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Función para indicar que ha sido seleccionada el metodo de Pago de Transferencia Bancaria.
     * @param fila
     * @param columna
     * @return 
     */
    private boolean bloqueaSeleccionTransferenciasBancarias(int fila, int columna){
        boolean blnRes=false;
        int intCodCtaUpd=-1;
        try{
            System.out.println("---------------------------------------isTransferenciaBancariaSeleccionada---------------------------------------");
            System.out.println("tblDat.getColumn(): " + tblDat.getColumnCount());
            System.out.println("objTblMod.getColumn(): " + objTblMod.getColumnCount());
            System.out.println("intNumColIniBco: " + intNumColIniBco);
            System.out.println("intNumColIniBco: " + intNumColIniBco);
            System.out.println("intNumColFinBco: " + intNumColFinBco);
            System.out.println("fila: " + fila);
            System.out.println("columna: " + columna);
         
            //Obtener el código de cuenta.
            for(int y=0; y<arlDatBco.size(); y++){
                String strArlCodCta=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COD_BCO);
                String strArlNumCol=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COL_BCO);
                int intVecNumCol=Integer.parseInt(strArlNumCol);
                if(intVecNumCol==columna) {
                    intCodCtaUpd=Integer.parseInt(strArlCodCta);
                    String strEstPedImp=objTblMod.isChecked(fila, INT_TBL_DAT_CHK_IMP)?"S":"N";
                    if(strEstPedImp.equals("S")){ /* Para documentos de Importaciones. */
                        if( (intCodCtaUpd==4052) || (intCodCtaUpd==1879) || (intCodCtaUpd==2911) )   { /* Cuando seleccione Transferencias Bancarias. */
                            blnRes=true;
                        }                    
                    }
                    break;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }        
    
    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a este registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                if(isCamVal()){
                    if(guardar()){
                        mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
                    }
                }
                break;
            case 1: //NO_OPTION
                objTblMod.removeAllRows();
                lblMsgSis.setText("");
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
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }       
    
    /**
     * Esta clase crea un hilo que permite manipular la interface grófica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estó ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podróa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaróa informado en todo
     * momento de lo que ocurre. Si se desea hacer ósto es necesario utilizar ósta clase
     * ya que si no sólo se apreciaróa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!consultarReg())
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

    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){ 
        boolean blnRes=false;
        arlDatBen.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(eliminaTodasColumnasAdicionadas()){
                    if(eliminaTodasColumnasAdicionadasTotales()){
                        if(adicionaColumnasVistoBueno()){
                            if(adicionaColumnasVistoBuenoTotales()){
                                if(adicionaColumnasBanco()){
                                    if(adicionaColumnasBancoTotales()){
                                        if(generaAdicionaColumnasMotivo()){
                                            if(generaAdicionaColumnasMotivoTotales()){
                                                if(adicionaColumnaAuxiliarBco()){
                                                    if(adicionaColumnaAuxiliarBcoTotales()){
                                                        if(agregarColumnaAdicionadaBotonMotivoEmisionCheque()){
                                                            if(agregarColumnaAdicionadaBotonMotivoEmisionChequeTotales()){
                                                                if(cargarArregloVistosBuenosDB()){
                                                                    if(cargarDetReg()){
                                                                        objTblMod.initRowsState();
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
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e) {
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
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaTodasColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColCnfEstIni); i--){
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaTodasColumnasAdicionadasTotales(){
        boolean blnRes=true;
        try{
            for (int i=(objTblModTot.getColumnCount()-1); i>=(intNumColCnfEstIni); i--){
                objTblModTot.removeColumn(tblTot, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  

    /**
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnasVistoBueno(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(obtenerTituloVistoBueno()){
                    if(numeroColumnasAdicionarVistoBueno()){
                        if(generaColumnasVistoBueno()){
                            blnRes=true;
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
    
    /**
     * Función que permite obtener información que será usada como títulos de las columnas adicionadas al modelo
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean obtenerTituloVistoBueno(){
        boolean blnRes=true;
        arlDatTitVisBue.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT co_visBue, CAST('Visto Bueno '||co_visBue AS CHARACTER VARYING) AS tx_heaTbl";
                strSQL+=" FROM tbr_visBueUsrTipDoc ";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                //strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                 if(optFil.isSelected()){
                     if(!  txtCodTipDoc.getText().toString().equals(""))
                         strSQL+="   AND co_tipDoc=" + txtCodTipDoc.getText()  + "";
                 }
                 else{
                    if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                         strSQL+=" AND (co_tipDoc IN(" + getTipoDocumentoAsociadoPrograma() + ")";
                         if( ! chkMosNotCre.isSelected())
                             strSQL+=" AND co_tipDoc NOT IN(4)";
                         strSQL+=" )";
                    }
                    else{
                         strSQL+="   AND co_tipdoc IN(1";
                         if(chkMosNotCre.isSelected())
                             strSQL+=",3,28";
                         strSQL+="   )";
                    }
                 }
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue ";
                strSQL+=" ORDER BY co_visBue";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegTitVisBue=new ArrayList();
                    arlRegTitVisBue.add(INT_ARL_TIT_COD_VIS_BUE, "" + rst.getString("co_visBue"));
                    arlRegTitVisBue.add(INT_ARL_TIT_NOM_VIS_BUE, "" + rst.getString("tx_heaTbl"));
                    arlDatTitVisBue.add(arlRegTitVisBue);
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
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean numeroColumnasAdicionarVistoBueno(){
        boolean blnRes=true;
        intNumColAdiVisBue=0;
        arlDatVisBue.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                //Armar filtro
                strAux="";
                if(optFil.isSelected()){
                    if(!  txtCodTipDoc.getText().toString().equals(""))
                        strAux+="   AND co_tipdoc=" + txtCodTipDoc.getText()  + "";
                }
                else{
                    if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                        strAux+=" AND (co_tipDoc IN(" + getTipoDocumentoAsociadoPrograma() + ")";
                        if( ! chkMosNotCre.isSelected())
                            strAux+=" AND co_tipDoc NOT IN(4)";
                        strAux+=" )";
                    }
                    else{
                        strAux+="   AND co_tipdoc IN(1";
                        if(chkMosNotCre.isSelected())
                            strAux+=",3,28";
                        strAux+="   )";
                    }
                }                
                
                //Armar la sentencia SQL
                strSQL="";
                strSQL+=" SELECT COUNT(*) FROM(";
                strSQL+=" SELECT co_visBue, CAST('Aprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                //strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" "+strAux;
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('Reprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                //strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" "+strAux;
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" ORDER BY co_visBue";
                strSQL+=" ) AS x";
                intNumColAdiVisBue=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumColAdiVisBue==-1)
                    return false;

                strSQL="";
                strSQL+=" SELECT co_visBue, CAST('Aprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                //strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" "+strAux;                
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('Reprobar' AS CHARACTER VARYING) AS tx_desLar";
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                //strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" "+strAux;
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                strSQL+=" ORDER BY co_visBue";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegVisBue=new ArrayList();
                    arlRegVisBue.add(INT_ARL_COD_VIS_BUE, "" + rst.getInt("co_visBue"));
                    arlRegVisBue.add(INT_ARL_NOM_VIS_BUE, "" + rst.getString("tx_desLar"));
                    arlRegVisBue.add(INT_ARL_COL_VIS_BUE, "");
                    arlDatVisBue.add(arlRegVisBue);
                }
                stm.close();
                rst.close();
                stm=null;
                rst=null;
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
     * Función que adiciona las columnas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean generaColumnasVistoBueno(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        try{
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);

            int intConTmp=0;
            int intLeeDatArlTitCab=0;
            intNumColIniVisBue=intNumColCnfEstIni;
            //System.out.println("intNumColIniVisBue: " + intNumColIniVisBue);

            for (int i=0; i<intNumColAdiVisBue; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniVisBue+i);
                tbc.setHeaderValue(" " + objUti.getStringValueAt(arlDatVisBue, i, INT_ARL_NOM_VIS_BUE) + " ");
                objUti.setStringValueAt(arlDatVisBue, i, INT_ARL_COL_VIS_BUE, String.valueOf((intNumColIniVisBue+i)));
                //Configurar JTable: Establecer el ancho de la columna.
                //tbc.setPreferredWidth(40);
                
                //Configurar JTable: Ocultar columnas. //Se ocultan columnas de visto bueno 
                tbc.setWidth(0);  
                tbc.setMaxWidth(0);  
                tbc.setMinWidth(0); 
                tbc.setPreferredWidth(0); 
                tbc.setResizable(false); 

                objTblCelRenChkVisBue=new ZafTblCelRenChk();
                tbc.setCellRenderer(objTblCelRenChkVisBue);
                objTblCelRenChkVisBue=null;
                objTblCelEdiChkVisBue=new ZafTblCelEdiChk(tblDat);
                tbc.setCellEditor(objTblCelEdiChkVisBue);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(intConTmp==1){
                    ZafTblHeaColGrp objTblHeaColGrp=null;
                    objTblHeaColGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatTitVisBue, intLeeDatArlTitCab, INT_ARL_TIT_NOM_VIS_BUE));
                    objTblHeaColGrp.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                    objTblHeaColGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-2)));
                    objTblHeaColGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));
                    intConTmp=0;
                    intLeeDatArlTitCab++;

                }
                else{
                    intConTmp++;
                }
                tbc=null;
            }
            intNumColFinVisBue=objTblMod.getColumnCount();
            //System.out.println("intNumColFinVisBue: " + intNumColFinVisBue);
            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769) || (objParSis.getCodigoMenu()==2016) || (objParSis.getCodigoMenu()==1773) ){
                for (int i=0; i<intNumColAdiVisBue; i++){
                    tblDat.getColumnModel().getColumn(((intNumColIniVisBue+i))).setWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVisBue+i))).setMaxWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVisBue+i))).setMinWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVisBue+i))).setPreferredWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVisBue+i))).setResizable(false);
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
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnasVistoBuenoTotales(){
        boolean blnRes=false;
        try{
            if(generaColumnasVistoBuenoTotales()){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
   private boolean generaColumnasVistoBuenoTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbcTot;
        try{
            objTblCelRenLblSal=new ZafTblCelRenLbl();
            objTblCelRenLblSal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSal.setTipoFormato(objTblCelRenLblSal.INT_FOR_NUM);
            objTblCelRenLblSal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (int i=0; i<intNumColAdiVisBue; i++){
                tbcTot=new javax.swing.table.TableColumn(intNumColIniVisBue+i);
                tbcTot.setHeaderValue(" ");
                
                //Configurar JTable: Establecer el ancho de la columna.
                //tbcTot.setPreferredWidth(40);
                
                //Configurar JTable: Ocultar columnas. //Se ocultan columnas de visto bueno 
                tbcTot.setWidth(0);  
                tbcTot.setMaxWidth(0);  
                tbcTot.setMinWidth(0); 
                tbcTot.setPreferredWidth(0); 
                tbcTot.setResizable(false); 
                
                //Configurar JTable: Renderizar celdas.
                tbcTot.setCellRenderer(objTblCelRenLblSal);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbcTot);
            }
            objTblCelRenLblSal=null;
            tbcTot=null;

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769) || (objParSis.getCodigoMenu()==2016) || (objParSis.getCodigoMenu()==1773) ){
                for (int i=0; i<intNumColAdiVisBue; i++){
                    tblTot.getColumnModel().getColumn(((intNumColIniVisBue+i))).setWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVisBue+i))).setMaxWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVisBue+i))).setMinWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVisBue+i))).setPreferredWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVisBue+i))).setResizable(false);
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
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnasBanco(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if(numeroColumnasAdicionarBanco()){
                    if(generaColumnasBanco()){
                        blnRes=true;
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean numeroColumnasAdicionarBanco(){
        boolean blnRes=true;
        intNumColAdiBco=0;
        strAux="";
        arlDatBco.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL ="";
                strSQL+=" SELECT COUNT(*) FROM(";
                strSQL+="       SELECT co_cta, tx_desLar";
                strSQL+="       FROM tbm_plaCta";
                strSQL+="       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND st_ctaBan='S'";
                strSQL+="       AND st_reg NOT IN('I','E')";
                strSQL+="       ORDER BY co_cta";
                strSQL+=" ) AS x";
                intNumColAdiBco=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumColAdiBco==-1)
                    return false;

                strSQL ="";
                strSQL+="       SELECT co_cta, tx_desLar";
                strSQL+="            , (substring( tx_desLar ,0, position(' ' in tx_desLar)  ) ) as tx_grpBan, ne_ordCtaBan";
                strSQL+="       FROM tbm_plaCta";
                strSQL+="       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND st_ctaBan='S'";
                strSQL+="       AND st_reg NOT IN('I','E')";
                strSQL+="       ORDER BY ne_ordCtaBan, tx_grpBan, co_cta";
                rst=stm.executeQuery(strSQL);
                for(int i=0; rst.next(); i++){
                    arlRegBco=new ArrayList();
                    arlRegBco.add(INT_ARL_COD_BCO, "" + rst.getInt("co_cta"));
                    arlRegBco.add(INT_ARL_NOM_BCO, "" + rst.getString("tx_desLar"));
                    arlRegBco.add(INT_ARL_COL_BCO, "");
                    arlDatBco.add(arlRegBco);
                }
                stm.close();
                rst.close();
                stm=null;
                rst=null;
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
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean generaColumnasBanco(){
        boolean blnRes=true;
        strAux="";
        String strTitGrpBanAft="";
        ZafTblHeaColGrp objTblHeaColGrpBco=null;
        javax.swing.table.TableColumn tbc;
        try{
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            ZafTblHeaGrp objTblHeaGrpBco=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpBco.setHeight(16*3);
            intNumColIniBco=intNumColFinVisBue;
            
            objTblCelEdiChkBco=new ZafTblCelEdiChk(tblDat);
            objTblCelEdiChkBco.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int fila=-1;
                int columna=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                fila=tblDat.getSelectedRow();
                columna=tblDat.getSelectedColumn();
                
                if(objTblMod.isChecked(fila, INT_TBL_DAT_CHK_AUT_PAG)){
                    if(existeCuentaBancariaSeleccionada(fila, columna)){
                        mostrarMsgInf("<HTML>No puede seleccionar este banco porque existe ya uno seleccionado.<BR>Quite la selección del otro banco y vuelva a intentarlo</HTML>");
                        //objTblMod.setChecked(false, fila, columna);
                        objTblCelEdiChkBco.setCancelarEdicion(true);
                    }
                    else{
                        if(bloqueaSeleccionTransferenciasBancarias(fila, columna)){ /* Se bloquea la seleccion de Transferencias Bancarias cuando son DxP Importaciones. */
                            mostrarMsgInf("<HTML>No puede seleccionar como banco Transferencias Bancarias.<BR>Debe seleccionar un banco y vuelva a intentarlo.</HTML>");
                            objTblCelEdiChkBco.setCancelarEdicion(true);
                        }
                        else{
                            objTblCelEdiChkBco.setCancelarEdicion(false);
                            if((objTblMod.isChecked(fila, columna)) && (objTblMod.isChecked(fila, intNumColIniBcoAux)) ){
                                if(( ! blnAutBco) && ( ! blnDesAutBco) ){
                                    objTblCelEdiChkBco.setCancelarEdicion(true);
                                }
                                if(blnDesAutBco){
                                    objTblCelEdiChkBco.setCancelarEdicion(false);
                                }
                                else{
                                    objTblCelEdiChkBco.setCancelarEdicion(true);
                                }
                            }
                            else if((objTblMod.isChecked(fila, columna)) && ( ! objTblMod.isChecked(fila, intNumColIniBcoAux)) ){
                                if(blnAutBco){
                                    objTblCelEdiChkBco.setCancelarEdicion(false);
                                }
                                else
                                    objTblCelEdiChkBco.setCancelarEdicion(true);
                            }
                            else if(( ! objTblMod.isChecked(fila, columna)) && ( ! objTblMod.isChecked(fila, intNumColIniBcoAux)) ){
                                if( ( ! blnAutBco) && ( ! blnDesAutBco)){
                                    objTblCelEdiChkBco.setCancelarEdicion(true);
                                }
                                if(blnAutBco)
                                    objTblCelEdiChkBco.setCancelarEdicion(false);
                                else
                                    objTblCelEdiChkBco.setCancelarEdicion(true);
                            }                        
                        }
                    }
                }
                else
                    objTblCelEdiChkBco.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    generaArchivoTextoCheckBox(fila); 
                    cargarSaldoBancarioActual(fila, columna);
                    if(objTblMod.isChecked(fila, INT_TBL_DAT_CHK_AUT_PAG)){
                        if(objTblMod.isChecked(fila, columna)){                                
                            calcularActualTotalConBco(fila, "S");
                            cargarSaldoFinal();
                        }
                        else{
                            //cargarSaldoBancarioActual(fila, columna);  //OJO: Probar si debe comentarse o no.
                            calcularActualTotalConBco(fila, "R");
                            cargarSaldoFinal();
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>No puede seleccionar el banco si no tiene autorización de pago.<BR>Solicite autorización de pago y vuelva a intentarlo</HTML>");
                        objTblMod.setChecked(false, fila, columna);
                    }
                }
            });


            for (int i=0; i<intNumColAdiBco; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniBco+i);
                tbc.setHeaderValue(" " + objUti.getStringValueAt(arlDatBco, i, INT_ARL_NOM_BCO).substring( objUti.getStringValueAt(arlDatBco, i, INT_ARL_NOM_BCO).indexOf(" ", 0) ) + " ");

                objUti.setStringValueAt(arlDatBco, i, INT_ARL_COL_BCO, String.valueOf((intNumColIniBco+i)));
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenChkBco=new ZafTblCelRenChk();
                tbc.setCellRenderer(objTblCelRenChkBco);
                objTblCelRenChkBco=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiChkBco);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                //ZafTblHeaColGrp objTblHeaColGrpBco=null;
                String strTitGrpBan=""+ objUti.getStringValueAt(arlDatBco, i, INT_ARL_NOM_BCO).substring(0, objUti.getStringValueAt(arlDatBco, i, INT_ARL_NOM_BCO).indexOf(" ", 0) );
                if(!strTitGrpBan.equals(strTitGrpBanAft)) //Primera Columna del Grupo.
                {
                    objTblHeaColGrpBco=new ZafTblHeaColGrp("" + strTitGrpBan);
                    objTblHeaColGrpBco.setHeight(16);
                    strTitGrpBanAft=strTitGrpBan;
                }
                objTblHeaGrpBco.addColumnGroup(objTblHeaColGrpBco);
                objTblHeaColGrpBco.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));
            }
            tbc=null;
            intNumColFinBco=objTblMod.getColumnCount(); 

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769) ){
                for (int i=0; i<intNumColAdiBco; i++){
                    tblDat.getColumnModel().getColumn(((intNumColIniBco+i))).setWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniBco+i))).setMaxWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniBco+i))).setMinWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniBco+i))).setPreferredWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniBco+i))).setResizable(false);
                }
            }
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

   /**
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
   private boolean adicionaColumnasBancoTotales(){
        boolean blnRes=false;
        try{
            if(generaColumnasBancoTotales()){
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
   private boolean generaColumnasBancoTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbcTot;
        try{
            objTblCelRenLblSal=new ZafTblCelRenLbl();
            objTblCelRenLblSal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSal.setTipoFormato(objTblCelRenLblSal.INT_FOR_NUM);
            objTblCelRenLblSal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (int i=0; i<intNumColAdiBco; i++){
                tbcTot=new javax.swing.table.TableColumn(intNumColIniBco+i);
                tbcTot.setHeaderValue(" ");
                //Configurar JTable: Establecer el ancho de la columna.
                tbcTot.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                tbcTot.setCellRenderer(objTblCelRenLblSal);

                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbcTot);
            }
            objTblCelRenLblSal=null;
            tbcTot=null;

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769) ){
                for (int i=0; i<intNumColAdiBco; i++){
                    tblTot.getColumnModel().getColumn(((intNumColIniBco+i))).setWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniBco+i))).setMaxWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniBco+i))).setMinWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniBco+i))).setPreferredWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniBco+i))).setResizable(false);
                }
            }
        }
        catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean generaAdicionaColumnasMotivo(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiVar=3;
        try{
            intNumColIniVar=objTblMod.getColumnCount();
            for (int i=0; i<intNumColAdiVar; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniVar+i);
                switch(i){
                    case 0:
                        tbc.setHeaderValue("Tot.Sin.Bco.");
                        break;
                    case 1:
                        tbc.setHeaderValue("Tot.Con.Bco.");
                        break;
                    case 2:
                        tbc.setHeaderValue("Mot.Pen.Chq.");
                }
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(70);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                tbc=null;
            }
            intNumColFinVar=objTblMod.getColumnCount();

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769)  ){
                for (int i=0; i<intNumColAdiVar; i++){
                    tblDat.getColumnModel().getColumn(((intNumColIniVar+i))).setWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVar+i))).setMaxWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVar+i))).setMinWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVar+i))).setPreferredWidth(0);
                    tblDat.getColumnModel().getColumn(((intNumColIniVar+i))).setResizable(false);
                }
            }
        }
        catch(Exception e)       {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
   private boolean generaAdicionaColumnasMotivoTotales(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        javax.swing.table.TableColumn tbcTot;
        try{
            objTblCelRenLblSal=new ZafTblCelRenLbl();
            objTblCelRenLblSal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblSal.setTipoFormato(objTblCelRenLblSal.INT_FOR_NUM);
            objTblCelRenLblSal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<intNumColAdiVar; i++){
                tbcTot=new javax.swing.table.TableColumn(intNumColIniVar+i);
                tbcTot.setHeaderValue(" ");
                //Configurar JTable: Establecer el ancho de la columna.
                tbcTot.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                tbcTot.setCellRenderer(objTblCelRenLblSal);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbcTot);
            }
            tbcTot=null;

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769) ){
                for (int i=0; i<intNumColAdiVar; i++){
                    tblTot.getColumnModel().getColumn(((intNumColIniVar+i))).setWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVar+i))).setMaxWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVar+i))).setMinWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVar+i))).setPreferredWidth(0);
                    tblTot.getColumnModel().getColumn(((intNumColIniVar+i))).setResizable(false);
                }
            }
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    

    /**
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnaAuxiliarBco(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiBcoAux=1;
        try{
            intNumColIniBcoAux=objTblMod.getColumnCount();
            for (int i=0; i<intNumColAdiBcoAux; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniBcoAux+i);
                tbc.setHeaderValue("Chk.Aux.Bco.");

                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(70);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenChkBcoAux=new ZafTblCelRenChk();
                tbc.setCellRenderer(objTblCelRenChkBcoAux);
                objTblCelRenChkBcoAux=null;

                objTblCelEdiChkBcoAux=new ZafTblCelEdiChk(tblDat);
                tbc.setCellEditor(objTblCelEdiChkBcoAux);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            tbc=null;
            intNumColFinBcoAux=objTblMod.getColumnCount();
            //objTblMod.addSystemHiddenColumn((intNumColIniBcoAux), tblDat);
            tblDat.getColumnModel().getColumn((intNumColIniBcoAux)).setWidth(0);
            tblDat.getColumnModel().getColumn((intNumColIniBcoAux)).setMaxWidth(0);
            tblDat.getColumnModel().getColumn((intNumColIniBcoAux)).setMinWidth(0);
            tblDat.getColumnModel().getColumn((intNumColIniBcoAux)).setPreferredWidth(0);
            tblDat.getColumnModel().getColumn((intNumColIniBcoAux)).setResizable(false);
        }
        catch(Exception e)  {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite ejecutar funciones que generan la adición de columnas en tiempo de ejecución
     * @return
     */
    private boolean adicionaColumnaAuxiliarBcoTotales(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        javax.swing.table.TableColumn tbcTot;
        try{
            for (int i=0; i<intNumColAdiBcoAux; i++){
                tbcTot=new javax.swing.table.TableColumn(intNumColIniBcoAux+i);
                tbcTot.setHeaderValue(" ");
                //Configurar JTable: Establecer el ancho de la columna.
                tbcTot.setPreferredWidth(70);
                //Configurar JTable: Agregar la columna al JTable.
                objTblModTot.addColumn(tblTot, tbcTot);
            }
            tbcTot=null;
            //objTblModTot.addSystemHiddenColumn((intNumColIniBcoAux), tblTot);
            tblTot.getColumnModel().getColumn((intNumColIniBcoAux)).setWidth(0);
            tblTot.getColumnModel().getColumn((intNumColIniBcoAux)).setMaxWidth(0);
            tblTot.getColumnModel().getColumn((intNumColIniBcoAux)).setMinWidth(0);
            tblTot.getColumnModel().getColumn((intNumColIniBcoAux)).setPreferredWidth(0);
            tblTot.getColumnModel().getColumn((intNumColIniBcoAux)).setResizable(false);
        }
        catch(Exception e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
      * Esta función agrega columnas al modelo
      * @return true: Si se pudo agregar columnas
      * <BR>false: En el caso contrario.
      */
    private boolean agregarColumnaAdicionadaBotonMotivoEmisionCheque(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColIniMotBut=objTblMod.getColumnCount();
        try{
            String strSubTit="Obs.Mot.Emi.Chq.";
            tbc=new javax.swing.table.TableColumn(intNumColIniMotBut);
            tbc.setHeaderValue("" + strSubTit);
            //Configurar JTable: Establecer el ancho de la columna.
            tbc.setPreferredWidth(30);

            objTblCelRenBut=new ZafTblCelRenBut();
            tbc.setCellRenderer(objTblCelRenBut);

            objCxP03_01=new ZafCxP03_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiBut= new ZafTblCelEdiButDlg(objCxP03_01);
            tbc.setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(blnCamMot){
                        objTblCelEdiBut.setCancelarEdicion(false);
                        objCxP03_01.setContenido(""+ (objTblMod.getValueAt(tblDat.getSelectedRow(), (intNumColFinVar-1))==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), (intNumColFinVar-1)).toString())   );
                    }
                    else
                        objTblCelEdiBut.setCancelarEdicion(true);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objCxP03_01.getContenido(), tblDat.getSelectedRow(), (intNumColFinVar-1));
                }
            });

            objTblMod.addColumn(tblDat, tbc);
            objTblCelRenBut=null;

            intNumColFinMotBut=objTblMod.getColumnCount();

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769)  ){
                tblDat.getColumnModel().getColumn(((intNumColIniMotBut))).setWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniMotBut))).setMaxWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniMotBut))).setMinWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniMotBut))).setPreferredWidth(0);
                tblDat.getColumnModel().getColumn(((intNumColIniMotBut))).setResizable(false);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función agrega columnas al modelo
     * @return true: Si se pudo agregar columnas
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColumnaAdicionadaBotonMotivoEmisionChequeTotales(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbcTot;
        int intNumColAdiTot=objTblModTot.getColumnCount();
        try{
            tbcTot=new javax.swing.table.TableColumn(intNumColAdiTot);
            tbcTot.setHeaderValue(" ");
            //Configurar JTable: Establecer el ancho de la columna.
            tbcTot.setPreferredWidth(30);

            //Configurar JTable: Renderizar celdas.
            tbcTot.setCellRenderer(objTblCelRenLblSal);
            //Configurar JTable: Agregar la columna al JTable.
            objTblModTot.addColumn(tblTot, tbcTot);
            tbcTot=null;

            if(  (objParSis.getCodigoMenu()==2012) ||  (objParSis.getCodigoMenu()==1769)  ){
                tblTot.getColumnModel().getColumn(((intNumColIniMotBut))).setWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniMotBut))).setMaxWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniMotBut))).setMinWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniMotBut))).setPreferredWidth(0);
                tblTot.getColumnModel().getColumn(((intNumColIniMotBut))).setResizable(false);
            }
        }
        catch(Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 

    /**
     * Función que permite cargar en un ArrayList la información de vistos buenos
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean cargarArregloVistosBuenosDB(){
        boolean blnRes=true;
        arlDatVisBueDat.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue, b1.st_reg, b1.co_usr";
                strSQL+=" FROM tbm_visBueMovInv AS b1";
                strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if(optFil.isSelected()){
                    if(!  txtCodTipDoc.getText().toString().equals(""))
                        strSQL+="   AND b1.co_tipDoc=" + txtCodTipDoc.getText()  + "";
                }
                else{
                   if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                        strSQL+=" AND (b1.co_tipDoc IN(" + getTipoDocumentoAsociadoPrograma() + ")";
                        if( ! chkMosNotCre.isSelected())
                            strSQL+=" AND b1.co_tipDoc NOT IN(4)";
                        strSQL+=" )";
                   }
                   else{
                        strSQL+="   AND b1.co_tipdoc IN(1";
                        if(chkMosNotCre.isSelected())
                            strSQL+=",3,28";
                        strSQL+="   )";
                   }
                }
                strSQL+=" ORDER BY b1.co_doc, b1.co_visBue";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegVisBueDat=new ArrayList();
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_EMP_DB,     "" + rst.getString("co_emp"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_LOC_DB,     "" + rst.getString("co_loc"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB, "" + rst.getString("co_tipDoc"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_DOC_DB,     "" + rst.getString("co_doc"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB, "" + rst.getString("co_visBue"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB, "" + rst.getString("st_reg"));
                    arlRegVisBueDat.add(INT_ARL_VIS_BUE_DB_COD_USR_DB,     "" + rst.getString("co_usr"));
                    arlDatVisBueDat.add(arlRegVisBueDat);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }   
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes=true;
        strAux="";
        String strArlVisBueDbCodEmp="", strArlVisBueDbCodLoc="", strArlVisBueDbCodTipDoc="", strArlVisBueDbCodDoc="";
        String strArlVisBueDbCodVisBue="", strArlVisBueDbEstVisBue="", strArlVisBueDbCodUsr="";
        String strRstVisBueDbCodEmp="", strRstVisBueDbCodLoc="", strRstVisBueDbCodTipDoc="", strRstVisBueDbCodDoc="";
        String strCodVisBueVal=""; //aqui se almacena el codigo del visto bueno del arreglo que guarda el co_emp, co_loc, co_tipdoc, ....etc
        int intNumColSet=-1;
        try{
            pgrSis.setIndeterminate(true);
            lblMsgSis.setText("Cargando datos...");
            butCon.setText("Detener");
            if (con!=null){
                stm=con.createStatement();
                strSQL=getConsulta();
                //System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        
                        strRstVisBueDbCodEmp=rst.getString("co_emp");
                        strRstVisBueDbCodLoc=rst.getString("co_loc");
                        strRstVisBueDbCodTipDoc=rst.getString("co_tipdoc");
                        strRstVisBueDbCodDoc=rst.getString("co_doc");
                        
                        vecReg.add(INT_TBL_DAT_COD_EMP,     "" + strRstVisBueDbCodEmp);
                        vecReg.add(INT_TBL_DAT_COD_LOC,     "" + strRstVisBueDbCodLoc);
                        vecReg.add(INT_TBL_DAT_COD_PER,     "" + rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_PER,     "" + rst.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + strRstVisBueDbCodTipDoc);
                        vecReg.add(INT_TBL_DAT_DES_COR,     "" + rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR,     "" + rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,     "" + strRstVisBueDbCodDoc);
                        vecReg.add(INT_TBL_DAT_COD_REG,     "" + rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,     "" + rst.getString("ne_numdoc"));
                        
                        strAux = rst.getObject("tx_numChq")==null? "" :rst.getString("tx_numChq");
                        vecReg.add(INT_TBL_DAT_NUM_FAC_PRV, "" + strAux);
                        
                        vecReg.add(INT_TBL_DAT_MOT_DOC,     "" + rst.getString("tx_obs2"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,     "" + rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,     "" + rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,     "" + rst.getString("nd_valPen"));
                        vecReg.add(INT_TBL_DAT_CHK_AUT_PAG,     null);
                        vecReg.add(INT_TBL_DAT_CHK_USR_AUT_AUX,     null);
                        strAux=rst.getObject("fe_venChqAutPag")==null?"":(rst.getString("fe_venChqAutPag").toString().equals("Null")?"":objUti.formatearFecha(rst.getString("fe_venChqAutPag"), "yyyy-MM-dd", "dd/MM/yyyy") );
                        vecReg.add(INT_TBL_DAT_FEC_CHQ,     "" + strAux);

                        strAux = rst.getObject("co_ben") == null? "" :rst.getString("co_ben");
                        vecReg.add(INT_TBL_DAT_COD_BEN,     "" + strAux);
                        
                        strAux = rst.getObject("tx_benchq") == null? "" :rst.getString("tx_benchq");
                        vecReg.add(INT_TBL_DAT_NOM_BEN,     "" + strAux);
                        
                        vecReg.add(INT_TBL_DAT_CHK_IMP,   null);
                        vecReg.add(INT_TBL_DAT_CHK_PAG_ARA,   null);
                        vecReg.add(INT_TBL_DAT_CHK_PAG_EXT,   null);
                        
                        strAux=rst.getObject("st_autpag")==null?"":rst.getString("st_autpag");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_AUT_PAG);
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_USR_AUT_AUX);
                        }
                        
                        strAux=rst.getObject("st_PagImp")==null?"":rst.getString("st_PagImp");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_IMP);
                        }
                        
                        strAux=rst.getObject("st_PagAra")==null?"":rst.getString("st_PagAra");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_PAG_ARA);
                        }

                        strAux=rst.getObject("st_PagExt")==null?"":rst.getString("st_PagExt");
                        if(strAux.equals("S")){
                            vecReg.setElementAt(new Boolean(true),INT_TBL_DAT_CHK_PAG_EXT);
                        }

                        //adicionar las columnas de vistos buenos
                        for(int j=intNumColIniVisBue; j<intNumColFinVisBue;j++){
                            vecReg.add(j,     null);
                        }
                        //adicionar las columnas de bancos
                        for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                            vecReg.add(j,     null);
                        }
                        //adicionar las columnas varias - total sin bancos, total con bancos, motivo xq no se emite chq.
                        for(int j=intNumColIniVar; j<intNumColFinVar;j++){
                            vecReg.add(j,     "");
                        }
                        //adicionar las columnas auxiliar bco
                        for(int j=intNumColIniBcoAux; j<intNumColFinBcoAux;j++){
                            vecReg.add(j,     "");
                        }
                        //adicionar la columna de motivo de porque no se ha emitido el cheque
                        for(int j=intNumColIniVar; j<intNumColFinVar;j++){
                            vecReg.add(j,     "");
                        }
                        //adicionar la columna de boton de motivo de porque no se ha emitido el cheque
                        for(int j=intNumColIniMotBut; j<intNumColFinMotBut;j++){
                            vecReg.add(j,     "");
                        }

                        //cargar los datos en las columnas de visto bueno
                        for(int k=0; k<arlDatVisBueDat.size(); k++){
                            strArlVisBueDbCodEmp=objUti.getStringValueAt(arlDatVisBueDat,     k, INT_ARL_VIS_BUE_DB_COD_EMP_DB);
                            strArlVisBueDbCodLoc=objUti.getStringValueAt(arlDatVisBueDat,     k, INT_ARL_VIS_BUE_DB_COD_LOC_DB);
                            strArlVisBueDbCodTipDoc=objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB);
                            strArlVisBueDbCodDoc=objUti.getStringValueAt(arlDatVisBueDat,     k, INT_ARL_VIS_BUE_DB_COD_DOC_DB);
                            strArlVisBueDbCodVisBue=objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB);
                            strArlVisBueDbEstVisBue=objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB)==null?"":objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB);
                            strArlVisBueDbCodUsr=objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB)==null?"":objUti.getStringValueAt(arlDatVisBueDat,  k, INT_ARL_VIS_BUE_DB_COD_USR_DB);
                            if(strRstVisBueDbCodEmp.equals(strArlVisBueDbCodEmp)){
                                if(strRstVisBueDbCodLoc.equals(strArlVisBueDbCodLoc)){
                                    if(strRstVisBueDbCodTipDoc.equals(strArlVisBueDbCodTipDoc)){
                                        if(strRstVisBueDbCodDoc.equals(strArlVisBueDbCodDoc)){
                                            if(! strArlVisBueDbCodVisBue.equals("")){
                                                for(int x=0; x<arlDatVisBue.size(); x++){
                                                    strCodVisBueVal=objUti.getStringValueAt(arlDatVisBue, x, INT_ARL_COD_VIS_BUE);
                                                    if(strArlVisBueDbCodVisBue.equals(strCodVisBueVal)){
                                                        intNumColSet=objUti.getIntValueAt(arlDatVisBue, x, INT_ARL_COL_VIS_BUE);
                                                        if(strArlVisBueDbEstVisBue.equals("A")){
                                                            vecReg.setElementAt(new Boolean(true), intNumColSet);
                                                        }
                                                        else if(strArlVisBueDbEstVisBue.equals("D")){
                                                            vecReg.setElementAt(new Boolean(true), (intNumColSet+1));
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //cargar los datos en las columnas de bancos
                        String strRstCodCtaBco=rst.getString("co_ctaautpag")==null?"":rst.getString("co_ctaautpag");
                        String strVecCodCta="", strVecNumCol="";
                        int intVecNumCol=0;

                        if( ! strRstCodCtaBco.equals("")){
                            for(int y=0; y<arlDatBco.size(); y++){
                                strVecCodCta=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COD_BCO);
                                strVecNumCol=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COL_BCO);
                                intVecNumCol=Integer.parseInt(strVecNumCol);
                                if(strRstCodCtaBco.equals(strVecCodCta)){
                                    vecReg.setElementAt(new Boolean(true), intVecNumCol);
                                    vecReg.setElementAt(new Boolean(true), intNumColIniBcoAux);
                                }
                                else{
                                    vecReg.setElementAt(null, intVecNumCol);
                                }
                            }
                        }
                        vecReg.setElementAt(rst.getString("tx_obsMot"), (intNumColIniVar+2));
                        vecDat.add(vecReg);                                                                                                
                    }
                    else{
                        break;
                    }                    
                }

                //Configurar JTable: Establecer columnas editables.
                vecAux=new Vector();
                vecAux.add("" + INT_TBL_DAT_CHK_AUT_PAG);
                vecAux.add("" + INT_TBL_DAT_FEC_CHQ);                
                //for(int j=intNumColIniVisBue; j<=intNumColFinVisBue;j++){
                //      vecAux.add("" + j);
                //}                
                for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                    vecAux.add("" + j);
                }  
                for(int j=intNumColIniMotBut; j<intNumColFinMotBut;j++){
                    vecAux.add("" + j);
                }
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;

                if((blnAutPag) || (blnDesAutPag) || (blnAutBco) || (blnDesAutBco) || (blnCamFecChq) || (blnCamMot))
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                else
                    objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
                cargarFilasSaldosBancarios();
                
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                

                //Obtener la fecha del servidor.
                datFecConRea=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecConRea==null)
                    return false;

            }
        }
        catch (java.sql.SQLException e)     {
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
    * Función que carga información de totales
    * @return true: Si se pudo efectuar la operación
    * <BR>false: En el caso contrario.
    */
   private boolean cargarFilasSaldosBancarios(){
        boolean blnRes=true;
        try{
            if(con!=null){
                objTblModTot.removeAllRows();
                for(int i=0; i<4; i++)
                    objTblModTot.insertRow();
                if(cargarSaldoActual()){
                    if(cargarSaldoAnterior()){
                        if(cargarTotalSinBanco()){
                            if(cargarTotalConBanco()){
                                if(cargarSaldoFinal()){
                                }
                            }
                        }
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

    /**
     * Función que carga la información de totales de saldos actuales autorizados
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean cargarSaldoActual(){
        boolean blnRes=true;
        int intFlg=0;
        String strCodCtaRst="", strCodCtaArl="";
        int intNumCol=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_cta, SUM(a2.nd_salCta) AS nd_salCta";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN tbm_salCta AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                if(arlDatBco.size()>0){
                    strSQL+=" AND a1.co_cta IN(";
                    if(arlDatBco.size()==1){
                        strSQL+="" + objUti.getStringValueAt(arlDatBco, 0, INT_ARL_COD_BCO) + "";
                    }
                    else{
                        for(int g=0;g<arlDatBco.size();g++){
                            if(intFlg==0){
                                strSQL+="" + objUti.getStringValueAt(arlDatBco, g, INT_ARL_COD_BCO) + "";
                                intFlg++;
                            }
                            else{
                                strSQL+=",";
                                strSQL+="" + objUti.getStringValueAt(arlDatBco, g, INT_ARL_COD_BCO) + "";
                            }
                        }
                    }
                    strSQL+=")";
                }
                strSQL+=" GROUP BY a2.co_emp, a2.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" ORDER BY a2.co_cta";
                rst=stm.executeQuery(strSQL);


                objTblModTot.setValueAt("Saldo Bancario Actual: ", 0, INT_TBL_DAT_NOM_PER);

                while(rst.next()){
                    strCodCtaRst=rst.getString("co_cta");
                    for(int p=0; p<arlDatBco.size(); p++){
                        strCodCtaArl=objUti.getStringValueAt(arlDatBco, p, INT_ARL_COD_BCO);
                        if(strCodCtaRst.equals(strCodCtaArl)){
                            intNumCol=objUti.getIntValueAt(arlDatBco, p, INT_ARL_COL_BCO);
                            objTblModTot.setValueAt(rst.getString("nd_salCta"), 0, intNumCol);
                        }
                    }
                }
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
     * Función que carga la información de totales de saldos anteriores autorizados
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean cargarSaldoAnterior(){
        boolean blnRes=true;
        String strCodCtaRst="", strCodCtaArl="";
        int intNumCol=0;
        BigDecimal bgdSalAntBco=new BigDecimal("0");
        BigDecimal bgdSumSalAntBco=new BigDecimal("0");
        try{
            if(con!=null){
                stm=con.createStatement();
                int intFlg=0;
                strSQL="";
                strSQL+="SELECT a1.co_ctaAutPag, ABS(SUM(abs(mo_pag) - abs(nd_abo))) AS nd_salCta  ";
                strSQL+=" FROM tbm_cabMovInv AS a2 INNER JOIN tbm_pagMovInv AS a1";
                strSQL+=" ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_autPag='S' AND a2.st_reg NOT IN('I','E') AND a1.st_reg IN('A','C')";
                //strSQL+=" and (abs(mo_pag) > abs(nd_abo))";
                strSQL+=" AND (a1.mo_pag+a1.nd_abo)>0";
                if(arlDatBco.size()>0){
                    strSQL+=" AND a1.co_ctaAutPag IN(";
                    if(arlDatBco.size()==1){
                        strSQL+="" + objUti.getStringValueAt(arlDatBco, 0, INT_ARL_COD_BCO) + "";
                    }
                    else{
                        for(int g=0;g<arlDatBco.size();g++){
                            if(intFlg==0){
                                strSQL+="" + objUti.getStringValueAt(arlDatBco, g, INT_ARL_COD_BCO) + "";
                                intFlg++;
                            }
                            else{
                                strSQL+=",";
                                strSQL+="" + objUti.getStringValueAt(arlDatBco, g, INT_ARL_COD_BCO) + "";
                            }
                        }
                    }
                    strSQL+=")";
                }
                strSQL+=" GROUP BY a1.co_ctaAutPag";
                strSQL+=" ORDER BY a1.co_ctaAutPag";
                //System.out.println("cargarSaldoAnterior: " + strSQL);
                rst=stm.executeQuery(strSQL);
                objTblModTot.setValueAt("Saldo Autorizado Anterior: ", 1, INT_TBL_DAT_NOM_PER);
                objTblModTot.setValueAt("Saldo Autorizado Actual: ", 2, INT_TBL_DAT_NOM_PER);
                while(rst.next()){
                    strCodCtaRst=rst.getString("co_ctaAutPag");
                    for(int p=0; p<arlDatBco.size(); p++){
                        strCodCtaArl=objUti.getStringValueAt(arlDatBco, p, INT_ARL_COD_BCO);
                        if(strCodCtaRst.equals(strCodCtaArl)){
                            intNumCol=objUti.getIntValueAt(arlDatBco, p, INT_ARL_COL_BCO);
                            bgdSalAntBco=rst.getBigDecimal("nd_salCta")==null?new BigDecimal("0"):rst.getBigDecimal("nd_salCta");
                            objTblModTot.setValueAt(bgdSalAntBco, 1, intNumCol);
                            bgdSumSalAntBco=bgdSumSalAntBco.add(bgdSalAntBco);
                        }
                    }
                }

                strSQL="";
                strSQL+="SELECT SUM(abs(mo_pag) - abs(nd_abo)) AS nd_salCta  ";
                strSQL+=" FROM tbm_cabMovInv AS a2 INNER JOIN tbm_pagMovInv AS a1";
                strSQL+=" ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_autPag='S' AND a2.st_reg NOT IN('I','E') AND a1.st_reg IN('A','C')";
                strSQL+=" and (abs(mo_pag) > abs(nd_abo))";
                rst=stm.executeQuery(strSQL);
                bgdSumSalAntBco=new BigDecimal("0");
                if(rst.next()){
                    bgdSumSalAntBco=rst.getBigDecimal("nd_salCta");
                }
                objTblModTot.setValueAt(bgdSumSalAntBco, 1, INT_TBL_DAT_VAL_PEN);
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
    * Esta función muestra los valores autorizados que están pendientes de autorizar bancos
    * @return true: Si se pudo cargar la información
    * <BR>false: En el caso contrario.
    */
    private boolean cargarTotalSinBanco(){
       boolean blnRes=true;
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+="SELECT ABS(SUM(a2.mo_pag + a2.nd_abo)) AS nd_valPenAutSinBco";
               strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagmovinv AS a2";
               strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
               strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+="         AND a2.st_autPag='S' AND a2.co_ctaAutPag IS NULL AND a2.st_reg IN('A','C') AND a1.st_reg NOT IN('I','E')";
               strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
               rst=stm.executeQuery(strSQL);
               if(rst.next()){
                   objTblModTot.setValueAt(rst.getBigDecimal("nd_valPenAutSinBco"), 1, intNumColIniVar );
               }

               stm.close();
               stm=null;
               rst.close();
               rst=null;
           }
       }
       catch(Exception e){
          objUti.mostrarMsgErr_F1(this, e);
          blnRes=false;
       }
       return blnRes;
   }
   
    /**
    * Esta función muestra los valores autorizados que tienen bancos asignados
    * @return true: Si se pudo cargar la información
    * <BR>false: En el caso contrario.
    */
    private boolean cargarTotalConBanco(){
       boolean blnRes=true;
       try{
           if(con!=null){
               stm=con.createStatement();
               strSQL="";
               strSQL+="SELECT ABS(SUM(a2.mo_pag + a2.nd_abo)) AS nd_valPenAutSinBco";
               strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagmovinv AS a2";
               strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
               strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+="         AND a2.st_autPag='S' AND a2.co_ctaAutPag IS NOT NULL AND a2.st_reg IN('A','C') AND a1.st_reg NOT IN('I','E')";
               strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";

               rst=stm.executeQuery(strSQL);
               if(rst.next()){
                   objTblModTot.setValueAt(rst.getBigDecimal("nd_valPenAutSinBco"), 1, (intNumColIniVar+1) );
               }
               stm.close();
               stm=null;
               rst.close();
               rst=null;
           }
       }
       catch(Exception e){
          objUti.mostrarMsgErr_F1(this, e);
          blnRes=false;
       }
       return blnRes;
   }
    
    /**
     * Función que carga presenta saldos totales finales
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean cargarSaldoFinal(){
        boolean blnRes=true;
        int intNumCol=-1;
        BigDecimal bgdSalActBco=new BigDecimal("0");
        BigDecimal bgdSalAutAnt=new BigDecimal("0");
        BigDecimal bgdSalAutAct=new BigDecimal("0");
        BigDecimal bgdSalFin=new BigDecimal("0");
        try{
            objTblModTot.setValueAt("Saldo Final: ", 3, INT_TBL_DAT_NOM_PER);
            for(int i=0;i<arlDatBco.size(); i++){
                intNumCol=objUti.getIntValueAt(arlDatBco, i, INT_ARL_COL_BCO);
                bgdSalActBco=objTblModTot.getValueAt(0, intNumCol)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(0, intNumCol).toString());
                bgdSalAutAnt=objTblModTot.getValueAt(1, intNumCol)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(1, intNumCol).toString());
                bgdSalAutAct=objTblModTot.getValueAt(2, intNumCol)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, intNumCol).toString());
                bgdSalFin=bgdSalActBco.subtract((bgdSalAutAnt.add(bgdSalAutAct)));
                objTblModTot.setValueAt(bgdSalFin, 3, intNumCol);
                bgdSalActBco=new BigDecimal("0");
                bgdSalAutAnt=new BigDecimal("0");
                bgdSalAutAct=new BigDecimal("0");
                bgdSalFin=new BigDecimal("0");
            }

            bgdSalAutAnt=objTblModTot.getValueAt(1, INT_TBL_DAT_VAL_PEN)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(1, INT_TBL_DAT_VAL_PEN).toString());
            bgdSalAutAct=objTblModTot.getValueAt(2, INT_TBL_DAT_VAL_PEN)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, INT_TBL_DAT_VAL_PEN).toString());
            bgdSalFin=(bgdSalAutAnt.add(bgdSalAutAct));
            objTblModTot.setValueAt(bgdSalFin, 3, INT_TBL_DAT_VAL_PEN);
            bgdSalAutAnt=new BigDecimal("0");
            bgdSalAutAct=new BigDecimal("0");
            bgdSalFin=new BigDecimal("0");

            bgdSalAutAnt=objTblModTot.getValueAt(1, intNumColIniVar)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(1, intNumColIniVar).toString());
            bgdSalAutAct=objTblModTot.getValueAt(2, intNumColIniVar)==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, intNumColIniVar).toString());
            bgdSalFin=(bgdSalAutAnt.add(bgdSalAutAct));
            objTblModTot.setValueAt(bgdSalFin, 3, intNumColIniVar);
            bgdSalAutAnt=new BigDecimal("0");
            bgdSalAutAct=new BigDecimal("0");
            bgdSalFin=new BigDecimal("0");
            
            bgdSalAutAnt=objTblModTot.getValueAt(1, (intNumColIniVar+1))==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(1, (intNumColIniVar+1)).toString());
            bgdSalAutAct=objTblModTot.getValueAt(2, (intNumColIniVar+1))==null?new BigDecimal("0"):new BigDecimal(objTblModTot.getValueAt(2, (intNumColIniVar+1)).toString());
            bgdSalFin=(bgdSalAutAnt.add(bgdSalAutAct));
            objTblModTot.setValueAt(bgdSalFin, 3, (intNumColIniVar+1));
            bgdSalAutAnt=new BigDecimal("0");
            bgdSalAutAct=new BigDecimal("0");
            bgdSalFin=new BigDecimal("0");
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
        
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                generaArchivoTextoGuardar();
                if(guardarDatos()){
                    con.commit();
                    blnRes=true;
//                    con.rollback();
//                    blnRes=false;
                    if(arlDatModTieEje.size()>0){
                        mostrarMsgInf("<HTML>La información no se guardó completamente<BR>Hubieron registros que no se pudieron guardar porque fueron modificados <BR>por otro usuario mientras estaban cargados en su pantalla.</HTML>");
                        objCxP03_02=new ZafCxP03_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, arlDatModTieEje);
                        objCxP03_02.setVisible(true);
                    }
                    cargarDetReg();
                }
                else{
                    mostrarMsgInf("<HTML>La información no se guardó correctamente.</HTML>");
                    con.rollback();
                }
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDatos() {
        boolean blnRes=true;
        boolean blnUsrAut=false; //Usuario permitido para autorizar
        boolean blnPenAut=false; //Pendiente de autorización
        boolean blnAutPag=false;
        boolean blnAutBan=false;
        String strLin="";
        String strEstAutPag="";
        String strVecCodCta="", strVecNumCol="";
        String strFecEmiChq="";
        String strCodCtaUpd="";
        int intVecNumCol=0;
        int intColBanSel=-1;
        int intUltRegAutPagMovInv, intUltRegUpdAutPagMovInv;
        try{
            if(con!=null){
                stm=con.createStatement();
                arlDatModTieEje.clear();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    strCodCtaUpd="Null";
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M"))
                    {
                        //Obtiene estado de autorización.
                        strEstAutPag=objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT_PAG)?"S":"N";
                        
                        //Obtiene Fecha de emisión del Cheque.
                        strFecEmiChq=objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CHQ)==null?"":(objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CHQ).equals("")?"":"" + objTblMod.getValueAt(i, INT_TBL_DAT_FEC_CHQ).toString() + "");                        
                        
                        //Obtiene Banco seleccionado.
                        for(int j=intNumColIniBco; j<intNumColFinBco;j++){
                            if(objTblMod.isChecked(i, j)){
                                intColBanSel=j;
                                break;
                            }
                        }
                        
                        //Obtiene código de cuenta del Banco seleccionado.
                        if(intColBanSel>=0){
                            for(int y=0; y<arlDatBco.size(); y++){
                                strVecCodCta=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COD_BCO);
                                strVecNumCol=objUti.getStringValueAt(arlDatBco, y, INT_ARL_COL_BCO);
                                intVecNumCol=Integer.parseInt(strVecNumCol);
                                if(intVecNumCol==intColBanSel) {
                                    strCodCtaUpd=strVecCodCta;
                                    break;
                                }
                            }
                        }
                        
                        //Obtiene que tipo de pago se va a realizar, de acuerdo al menú por el que se ingrese.
                        blnAutPag=false;   blnAutBan=false;
                        switch(objParSis.getCodigoMenu())
                        {
                            case 2012:  blnAutPag=true;                   break; //Clientes: Autorización de pagos...
                            case 2016:  blnAutBan=true;                   break; //Clientes: Autorización de bancos...
                            case 2020:  blnAutPag=true;  blnAutBan=true;  break; //Clientes: Autorización de pagos y bancos...
                            case 1769:  blnAutPag=true;                   break; //Proveedores: Autorización de pagos...
                            case 1773:  blnAutBan=true;                   break; //Proveedores: Autorización de bancos...
                            case 1777:  blnAutPag=true;  blnAutBan=true;  break; //Proveedores: Autorización de pagos y bancos...
                        }

                        //Verifica que el Pago seleccionado siga pendiente de pago.
                        blnPenAut=false;
                        strSQL ="";
                        strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                        strSQL+=" FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                        strSQL+=" WHERE a2.co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                        strSQL+=" AND a2.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                        strSQL+=" AND a2.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                        strSQL+=" AND a2.co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=" AND a2.co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                        strSQL+=" AND a2.st_reg IN('A','C')";
                        strSQL+=" AND '" + objUti.formatearFecha(datFecConRea, objParSis.getFormatoFechaHoraBaseDatos()) + "' >=a1.fe_ultMod";
                        //System.out.println("Verifica si pago está pendiente: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()) {
                            blnPenAut=true;
                        }   
                        rst.close();
                        rst=null;      
                        
                        //Verifica si el usuario tiene permiso para autorizar pagos o pagos y bancos
                        blnUsrAut=false;
                        if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT_PAG)) &&  ( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK_USR_AUT_AUX)) ) {
                            blnUsrAut=true;
                        }
                        else if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_USR_AUT_AUX)){
                            blnUsrAut=false;
                        }
                        
                        //Obtiene los últimos registros de AutPagMovInv
                        strSQL ="";
                        strSQL+=" SELECT MAX(a1.co_aut) FROM tbm_autPagMovInv AS a1";
                        strSQL+=" WHERE a1.co_emp=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP), 3) + "";
                        strSQL+=" AND a1.co_loc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC), 3) + "";
                        strSQL+=" AND a1.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC), 3) + "";
                        strSQL+=" AND a1.co_doc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC), 3) + "";
                        strSQL+=" AND a1.co_reg=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";
                        intUltRegAutPagMovInv=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                        if (intUltRegAutPagMovInv==-1){
                            return false;
                        }
                        intUltRegUpdAutPagMovInv=intUltRegAutPagMovInv;
                        intUltRegAutPagMovInv++;                        
                        
                        //Ejecuta sentencias
                        if(blnPenAut) //Cuando documento sigue pendiente de pago.
                        {
                            if(blnAutPag){ //Autorización - Pagos
                                //Actualización de PagMovInv
                                strSQL ="";
                                strSQL+=" UPDATE tbm_pagMovInv";
                                strSQL+=" SET st_autPag='" + strEstAutPag + "'";
                                if(strFecEmiChq.trim().equals(""))
                                    strSQL+=" , fe_venChqAutPag=Null";
                                else
                                    strSQL+=" , fe_venChqAutPag='" + objUti.formatearFecha(strFecEmiChq,"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                                strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                                strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                                strSQL+=" AND st_reg IN('A','C')";
                                strSQL+=" ;";
                                //System.out.println("UPDATE tbm_pagMovInv.Autorización - Pagos : " + strSQL);
                                stm.executeUpdate(strSQL);                                
                            }
                            if(blnAutBan){ //Autorización - Bancos
                                //Actualización de PagMovInv
                                strSQL="";
                                strSQL+=" UPDATE tbm_pagMovInv";
                                strSQL+=" SET co_ctaAutPag=" + strCodCtaUpd + "";
                                strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                                strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + "";
                                strSQL+=" AND st_reg IN('A','C')";
                                strSQL+=" ;";
                                //System.out.println("UPDATE tbm_pagMovInv.Autorización - Bancos : " + strSQL);
                                stm.executeUpdate(strSQL); 
                                
                                //<editor-fold defaultstate="collapsed" desc="/*Genera transferencias bancarias: Documentos importaciones*/">
                                if(intColBanSel>=0) { //Si se ha seleccionado un banco, se podrá generar Transferencias Bancarias.
                                    if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_IMP)){  //Si es documento de pago de importaciones, genera transferencias bancarias.
                                        objPagImp.arlDatDatPag=new ArrayList();
                                        objPagImp.arlRegDatPag=new ArrayList();
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_COD_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP));
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_COD_LOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC));
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_COD_TIPDOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_COD_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC));
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_VAL_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PEN));
                                        objPagImp.arlRegDatPag.add(objPagImp.INT_TBL_ARL_PAG_COD_BAN, ""+strCodCtaUpd);
                                        objPagImp.arlDatDatPag.add(objPagImp.arlRegDatPag);
                                        //System.out.println("objPagImp.arlDatDatPag: "+objPagImp.arlDatDatPag);
                                        
                                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PAG_ARA)){   /* Pagos de Arancel  */
                                            /* Generación de transferencia bancaria automática, cuando son pago de aranceles (Importaciones). */
                                            if(!objPagImp.insertar(con, objPagImp.arlDatDatPag)){
                                                blnRes=false; 
                                                objPagImp.mostrarMsgErr();
                                            }  
                                        }
                                        else if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PAG_EXT)){  /* Pagos al exterior  */
                                            /* Generación de transferencia bancaria automática, cuando son pagos al exterior (Importaciones). */
                                            if(!objPagExt.insertar(con, objPagImp.arlDatDatPag)){
                                                blnRes=false;
                                                objPagExt.mostrarMsgErr();
                                            }  
                                        }
                                    }
                                }
                                //</editor-fold>                                
                            }
                            
                            if(blnUsrAut){ //Si usuario tiene permiso para autorizar pagos.
                                //Inserción AutPagMovInv
                                strSQL="";
                                strSQL+=" INSERT INTO tbm_autpagmovinv(";
                                strSQL+="      co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_aut, fe_autpag";
                                strSQL+="    , co_usrautpag, tx_comautpag, co_ctaautpag, st_reg)";
                                strSQL+=" VALUES (";
                                strSQL+=" " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP), 3) + "";//co_emp
                                strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC), 3) + "";//co_loc
                                strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC), 3) + "";//co_tipDoc
                                strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC), 3) + "";//co_doc
                                strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";//co_reg
                                strSQL+=", " + intUltRegAutPagMovInv + "";//co_aut
                                strSQL+=", " + objParSis.getFuncionFechaHoraBaseDatos() + ""; //fe_autPag
                                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrautpag
                                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP(), 0) + "";//tx_comautpag
                                strSQL+=", " + strCodCtaUpd + "";//co_ctaautpag
                                strSQL+=", 'A'";//st_reg
                                strSQL+=");";
                                //System.out.println("INSERT tbm_autpagmovinv : " + strSQL);   
                                stm.executeUpdate(strSQL);                              
                            }
                            else{
                                //Actualización AutPagMovInv
                                strSQL ="";
                                strSQL+=" UPDATE tbm_autpagmovinv";
                                strSQL+=" SET co_ctaautpag=" + strCodCtaUpd + "";//co_ctaautpag
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT_PAG))
                                    strSQL+=", st_reg='A'";
                                else if( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUT_PAG))
                                    strSQL+=", st_reg='C'";
                                strSQL+=" WHERE co_emp=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP), 3) + "";
                                strSQL+=" AND co_loc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC), 3) + "";
                                strSQL+=" AND co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC), 3) + "";
                                strSQL+=" AND co_doc=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC), 3) + "";
                                strSQL+=" AND co_reg=" + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG), 3) + "";
                                strSQL+=" AND co_aut=" + intUltRegUpdAutPagMovInv + "";
                                strSQL+="";
                                //System.out.println("UPDATE tbm_autpagmovinv : " + strSQL); 
                                stm.executeUpdate(strSQL);  
                            }                            
                        }
                        else{  //Cuando ya el documento no está pendiente de pago.
                            //Arreglo
                            arlRegModTieEje=new ArrayList();
                            arlRegModTieEje.add(INT_TBL_ARL_COD_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP));
                            arlRegModTieEje.add(INT_TBL_ARL_COD_LOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC));
                            arlRegModTieEje.add(INT_TBL_ARL_COD_CLI, objTblMod.getValueAt(i, INT_TBL_DAT_COD_PER));
                            arlRegModTieEje.add(INT_TBL_ARL_NOM_CLI, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_PER));
                            arlRegModTieEje.add(INT_TBL_ARL_COD_TIP_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                            arlRegModTieEje.add(INT_TBL_ARL_DES_COR, objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR));
                            arlRegModTieEje.add(INT_TBL_ARL_DES_LAR, objTblMod.getValueAt(i, INT_TBL_DAT_DES_LAR));
                            arlRegModTieEje.add(INT_TBL_ARL_COD_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC));
                            arlRegModTieEje.add(INT_TBL_ARL_COD_REG, objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG));
                            arlRegModTieEje.add(INT_TBL_ARL_NUM_DOC, objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                            arlDatModTieEje.add(arlRegModTieEje);                        
                        }

                        strLin="";              strEstAutPag="";
                        strVecCodCta="";        strVecNumCol="";                intVecNumCol=0;
                        strCodCtaUpd="";        intColBanSel=-1;                strFecEmiChq="";                    
                    }
                }
                stm.close();
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
  
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarMotivo(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(guardarMotivo_TbmPagMovInv()){
                    con.commit();
                    blnRes=true;
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
      * Esta función actualiza el registro en la base de datos.
      * @return true: Si se pudo actualizar el registro.
      * <BR>false: En el caso contrario.
      */
    private boolean guardarMotivo_TbmPagMovInv(){
        boolean blnRes=true;
        String strLin="";
        String strSQLTmp="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0;i<objTblMod.getRowCountTrue();i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        strSQL="";
                        strSQL+="UPDATE tbm_pagMovInv";
                        strSQL+=" SET tx_obs1=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniVar+2))) + "";
                        strSQL+=" WHERE co_emp=" +objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP);
                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG);
                        strSQL+=";";
                        strSQLTmp+=strSQL;
                    }
                }
                stm.executeUpdate(strSQLTmp);
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
     * Función que obtiene los tipos de documentos asociados al programa
     * @param conTipDocAsoPrg Connexión para realizar la sentencia sql
     * @return strTipDoc La cadena con todos los tipos de documentos asociados separados por ","
     * este será usado en la sentencia sql
     */
    private String getTipoDocumentoAsociadoPrograma(){
        String strSQLTipDocAsoPrg="";
        try{
            if(objParSis.getCodigoUsuario()==1){
                strSQLTipDocAsoPrg="";
                strSQLTipDocAsoPrg+=" SELECT co_tipdoc";
                strSQLTipDocAsoPrg+=" FROM tbr_tipdocprg";
                strSQLTipDocAsoPrg+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQLTipDocAsoPrg+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQLTipDocAsoPrg+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
            }
            else{
                strSQLTipDocAsoPrg="";
                strSQLTipDocAsoPrg+=" SELECT co_tipdoc";
                strSQLTipDocAsoPrg+=" FROM tbr_tipdocusr";
                strSQLTipDocAsoPrg+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQLTipDocAsoPrg+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQLTipDocAsoPrg+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQLTipDocAsoPrg+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strSQLTipDocAsoPrg;
    }    

    /**
     * Esta función permite actualizar el beneficiario.
     * @return true: Si se pudo actualizar el beneficiario.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarBeneficiario(){
        boolean blnRes = true;
        int intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodCli, intCodBen_BenChq;
        String strNumDoc, strNomCli, strCodBen, strNomBen, strFecUltMod;
        try{
            strAux = "";
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp = objParSis.getCodigoEmpresa();
            
            if (con!=null){
                stm = con.createStatement();
                strSQL=getConsulta();
                //System.out.println("actualizarBeneficiario: "+strSQL);
                rst = stm.executeQuery(strSQL);
                arlDat = new ArrayList();
                while (rst.next()){
                    arlReg = new ArrayList();
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_EMP,     "" + rst.getString("co_emp"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_LOC,     "" + rst.getString("co_loc"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_TIP_DOC, "" + rst.getString("co_tipdoc"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_DOC,     "" + rst.getString("co_doc"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_NUM_DOC,     "" + rst.getString("ne_numdoc"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_CLI,     "" + rst.getString("co_cli"));
                    arlReg.add(INT_TBL_DAT_AUT_PAG_NOM_CLI,     "" + rst.getString("tx_nomcli"));

                    strAux = rst.getObject("co_ben") == null? "" :rst.getString("co_ben");
                    arlReg.add(INT_TBL_DAT_AUT_PAG_COD_BEN,     "" + strAux);

                    strAux = rst.getObject("tx_benchq") == null? "" :rst.getString("tx_benchq");
                    arlReg.add(INT_TBL_DAT_AUT_PAG_NOM_BEN,     "" + strAux);

                    strAux = rst.getObject("fe_ultmod") == null? "" :rst.getString("fe_ultmod");
                    arlReg.add(INT_TBL_DAT_AUT_PAG_FEC_ULT_MOD, "" + strAux);

                    arlDat.add(arlReg);            
                }

                for (int i = 0; i < arlDat.size(); i++)
                {  
                    intCodEmp = objUti.getIntValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_EMP);
                    intCodLoc = objUti.getIntValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_LOC);
                    intCodTipDoc = objUti.getIntValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_TIP_DOC);
                    intCodDoc = objUti.getIntValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_DOC);
                    strNumDoc = objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_NUM_DOC);
                    intCodCli = objUti.getIntValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_CLI);
                    strNomCli = objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_NOM_CLI);
                    strCodBen = objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_COD_BEN);
                    strNomBen = objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_NOM_BEN);
                    strFecUltMod = objUti.getStringValueAt(arlDat, i, INT_TBL_DAT_AUT_PAG_FEC_ULT_MOD);

                    if (!strNomCli.equals(strNomBen) && (!strCodBen.equals("")))
                    {  //Se va a verificar si intCodCli (obtenido del campo tbm_cabmovinv.co_cli) existe en tbm_benchq
                        strSQL =" select count(*) as cont_reg from tbm_benchq where st_reg <> 'I' and co_emp = " + intCodEmp + " and co_cli = " + intCodCli;
                        rst = stm.executeQuery(strSQL);

                        if (rst.next())
                        {   if (rst.getInt("cont_reg") > 0)
                            {  //Como si existe, se va a actualizar el campo tbm_benchq.tx_benchq con el valor del campo tbm_cabmovinv.tx_nomcli
                                strSQL =" update tbm_benchq set tx_benchq = '" + strNomCli + "' ";
                                strSQL+=" where co_emp = " + intCodEmp + " and co_cli = " + intCodCli + " and co_reg = " + strCodBen;
                                stm.executeUpdate(strSQL);
                            }
                            //En la tabla tbm_cabmovinv se va a actualizar el campo tx_benchq.
                            strSQL =" update tbm_cabmovinv ";
                            strSQL+=" set tx_benchq = '" + strNomCli + "' ";
                            strSQL+=" where co_emp = " + intCodEmp;
                            strSQL+=" and co_loc = " + intCodLoc;
                            strSQL+=" and co_tipdoc = " + intCodTipDoc;
                            strSQL+=" and co_doc = " + intCodDoc;
                            stm.executeUpdate(strSQL);
                        }
                    }
                    else if (strCodBen.equals(""))
                    {   //Si strCodBen = "" significa que se encontro NULL en el campo tbm_cabmovinv.co_ben.
                        //Se va a verificar si intCodCli (obtenido del campo tbm_cabmovinv.co_cli) existe en tbm_benchq
                        strSQL = "select co_reg from tbm_benchq where st_reg <> 'I' and co_emp = " + intCodEmp + " and co_cli = " + intCodCli + " order by co_reg";
                        rst = stm.executeQuery(strSQL);
                        intCodBen_BenChq = 0;
                        if (rst.next()) {  intCodBen_BenChq = rst.getInt("co_reg");      }

                        if (intCodBen_BenChq > 0)
                        {   //Como si existe, se va a actualizar el campo tbm_benchq.tx_benchq con el valor del campo tbm_cabmovinv.tx_nomcli
                            strSQL =" update tbm_benchq set tx_benchq = '" + strNomCli + "' ";
                            strSQL+=" where co_emp = " + intCodEmp + " and co_cli = " + intCodCli + " and co_reg = " + intCodBen_BenChq;
                            stm.executeUpdate(strSQL);
                            //En la tabla tbm_cabmovinv se va a actualizar los campos co_ben y tx_benchq.
                            strSQL =" update tbm_cabmovinv ";
                            strSQL+=" set co_ben = " + intCodBen_BenChq + ", ";
                            strSQL+=" tx_benchq = '" + strNomCli + "' ";
                            strSQL+=" where co_emp = " + intCodEmp;
                            strSQL+=" and co_loc = " + intCodLoc;
                            strSQL+=" and co_tipdoc = " + intCodTipDoc;
                            strSQL+=" and co_doc = " + intCodDoc;
                            stm.executeUpdate(strSQL);
                        }
                    } 

                    if (strFecUltMod.equals(""))
                    {   //En la tabla tbm_cabmovinv se va a actualizar el campo fe_ultmod con el valor del campo fe_ing.
                        strSQL =" update tbm_cabmovinv ";
                        strSQL+=" set fe_ultmod = fe_ing ";
                        strSQL+=" where co_emp = " + intCodEmp;
                        strSQL+=" and co_loc = " + intCodLoc;
                        strSQL+=" and co_tipdoc = " + intCodTipDoc;
                        strSQL+=" and co_doc = " + intCodDoc;
                        stm.executeUpdate(strSQL);
                    }
                } 
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        }
        catch (java.sql.SQLException e)    {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)       {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } 
  
    /**
     * Función que retorna el query de consulta.
     * @return 
     */
    private String getConsulta(){    
        String strSqlCon="";
        
        //Filtros
        strAux="";
        if( ! chkMosNotCre.isSelected()){
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773) ) { //Proveedores
                strAux+=" AND  (x.tx_natDoc NOT IN('E') AND x.tx_desCor NOT IN('NCCO', 'NDPS', 'NDPR')  )\n ";
            }
            else {  //Clientes
                strAux+=" AND  (x.tx_natDoc NOT IN('I') AND x.tx_desCor NOT IN('NCCO', 'NDPS', 'NDPR')  ) \n";
            }
        }
        if( ! (   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   )  ){
            if(chkMosValExc.isSelected())
            strAux+=" AND abs(x.nd_valPen) > " + txtValExc.getText() + "\n";
        }        

        //Filtro: Fecha
        if(objSelFec.isCheckBoxChecked()){
            switch (objSelFec.getTipoSeleccion()){
                case 0: //Búsqueda por rangos
                    strAux+=" AND x.fe_ven BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' \n";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAux+=" AND x.fe_ven<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' \n";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAux+=" AND x.fe_ven>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' \n";
                    break;
                case 3: //Todo.
                    break;
            }
        }

        if(!  txtCodPrv.getText().toString().equals("")) {
            if(txtEstExp.getText().toString().equals("N")) /* Cuando son proveedores normales */
                strAux+=" AND x.co_cli=" + txtCodPrv.getText()  + "\n";
            else /* Cuando son proveedores del exterior */
                strAux+=" AND x.co_exp=" + txtCodPrv.getText()  + "\n";
        }

        if(!  txtCodTipDoc.getText().toString().equals("")) 
            strAux+=" AND x.co_tipDoc=" + txtCodTipDoc.getText()  + "\n";
        else
            strAux+=" AND x.co_tipDoc IN (" + getTipoDocumentoAsociadoPrograma()  + ")\n"; 

        if (txtNomPrvDes.getText().length()>0 )
            strAux+=" AND ( LOWER(x.tx_nomcli) LIKE '%" + txtNomPrvDes.getText().replaceAll("'", "''").toLowerCase() + "%')\n";

        if (txtValPndDes.getText().length()>0 || txtValPndHas.getText().length()>0)
            strAux+=" AND x.nd_valPen BETWEEN " + txtValPndDes.getText() + " AND " + txtValPndHas.getText() + "\n";

        if(chkMosDocNotAut.isSelected())
            strAux+=" AND x.st_autpag='N'\n";        

        //PARA FACTURAS Y NOTAS DE CREDITO
        if (chkExcVenEmpGrp.isSelected())   {
            //Temporal hasta que se busque una mejor manera de hacerlo.
            strAux+=" AND NOT (x.co_emp=1 AND x.co_cli IN( 3516, 603, 2600, 1039 ) )\n";                   
            strAux+=" AND NOT (x.co_emp=2 AND x.co_cli IN( 2854, 446, 2105, 789 ) )\n";                  
            strAux+=" AND NOT (x.co_emp=3 AND x.co_cli IN( 2858, 453, 2107, 832 ) )\n";                  
            strAux+=" AND NOT (x.co_emp=4 AND x.co_cli IN( 3117, 498, 2294, 886 ) )\n";            
        }

        //<editor-fold defaultstate="collapsed" desc="/* Vistos Buenos */">
        if(chkMosDocAllVisBue.isSelected()){
            strSqlCon="";
            strSqlCon+=" SELECT y.* FROM( ";
            strSqlCon+=" 	SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue ";
            strSqlCon+="   FROM(  SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
            strSqlCon+="          FROM tbm_cabTipDoc AS b1";
            strSqlCon+="          WHERE b1.st_reg='A' AND b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSqlCon+="   ) AS a";
            strSqlCon+="   INNER JOIN(";
            strSqlCon+="          SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc ";
            strSqlCon+="          FROM(  SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
            strSqlCon+="                 FROM tbm_visBueMovInv AS b1";
            strSqlCon+="                 WHERE b1.st_reg='A' AND b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSqlCon+="                 ORDER BY b1.co_doc, b1.co_visBue";
            strSqlCon+="          ) AS x";
            strSqlCon+="          GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc";
            strSqlCon+="    ) AS b";
            strSqlCon+="    ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
            strSqlCon+=" ) AS x";
            strSqlCon+=" INNER JOIN(";
            strSqlCon+="       SELECT *FROM(";
            strSqlCon+=" 	            SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc";
            strSqlCon+=" 	                 , CASE WHEN a1.co_tipdoc IN(7,8) THEN a1.tx_obs1 ELSE a3.tx_descor  END AS tx_descor";
            strSqlCon+=" 	                 , a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numdoc, a2.tx_numChq, a1.tx_obs2, a1.co_cli ";
            strSqlCon+=" 	                 , a1.tx_nomcli, a1.co_ben, a1.tx_benchq, a1.fe_doc, a2.fe_ven AS fe_ven, a3.tx_natDoc ";
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                strSqlCon+=" 	         , CASE WHEN a3.tx_natDoc='I' THEN (abs(a2.mo_pag) - abs(a2.nd_abo))";
            }
            else{
                strSqlCon+=" 	         , CASE WHEN a3.tx_natDoc='I' THEN (abs(a2.mo_pag) - abs(a2.nd_abo))  *(-1)";
            }
            strSqlCon+=" 		        WHEN a3.tx_natDoc='E' THEN (abs(a2.mo_pag) - abs(a2.nd_abo)) *(-1)";
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                strSqlCon+=" 		WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('FAPR', 'FAPS', 'NCPS', 'NCPR') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))";
                strSqlCon+=" 		WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('NDPS', 'NDPR') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo)) * (-1)";
            }
            else{
                strSqlCon+="               WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('FACC') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))*(-1)";
                strSqlCon+="               WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('NCCO') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))*(-1)";
            }
            strSqlCon+=" 	                 END AS nd_valPen";
            strSqlCon+=" 	               , a2.st_autpag, a2.co_ctaautpag, a4.tx_codCta, a4.tx_desLar, a2.fe_venChqAutPag, a2.tx_obs1";
            strSqlCon+=" 	               , (CASE WHEN a3.co_cat IN (27, 28) THEN 'S' ELSE 'N' END ) AS st_PagImp  \n ";  //Para indicar que son documentos de pagos de importaciones
            strSqlCon+=" 	            FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagmovinv AS a2";
            strSqlCon+=" 	            ON a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipdoc = a2.co_tipdoc AND a1.co_doc = a2.co_doc";
            strSqlCon+=" 	            INNER JOIN tbm_cabtipdoc AS a3";
            strSqlCon+=" 	            ON a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_loc AND a1.co_tipdoc = a3.co_tipdoc";
            strSqlCon+=" 	            LEFT OUTER JOIN tbm_plaCta AS a4";
            strSqlCon+=" 	            ON a2.co_emp=a4.co_emp AND a2.co_ctaAutPag=a4.co_cta";
            strSqlCon+=" 	            WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                strSqlCon+=" 	    AND a3.ne_mod IN(2,4,5)";
            }
            else{
                strSqlCon+=" 	    AND a3.ne_mod IN(1,3)";
            }
            strSqlCon+=" 	            AND CASE WHEN a3.tx_natDoc='I' THEN (a2.mo_pag + a2.nd_abo) >0";
            strSqlCon+=" 	                     WHEN a3.tx_natDoc='E' THEN (a2.mo_pag + a2.nd_abo) <0 ";
            strSqlCon+=" 	                     ELSE (a2.mo_pag + a2.nd_abo) >0  END";

            //solo para pagos a proveedores, clientes no necesitan este proceso adicional
            if(  (objParSis.getCodigoMenu()==1769)  ||  (objParSis.getCodigoMenu()==1773)  || (objParSis.getCodigoMenu()==1777) ) {
                if(!chkMosDocSinFacPrv.isSelected()){//solo se presentan los que tienen asociada una factura de proveedor
                    strSqlCon+=" AND (    (a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL";
                    strSqlCon+="           AND a2.tx_fecCad IS NOT NULL AND a2.tx_numChq IS NOT NULL)";
                    strSqlCon+="     OR a1.st_emiChqAntRecFacPrv='S')     ";
                }
            }

            strSqlCon+=" 	AND a1.st_reg NOT IN('I','E')";
            strSqlCon+=" 	AND a2.st_reg IN('A','C')  AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL) ";
            strSqlCon+=" 	GROUP BY a1.tx_obs1, a1.co_emp, a1.co_loc, a1.co_tipdoc";
            strSqlCon+=" 	       , a1.co_doc, a2.co_reg, a1.ne_numdoc, a2.tx_numChq, a1.tx_obs2, a1.co_cli";
            strSqlCon+=" 	       , a1.tx_nomcli, a1.co_ben, a1.tx_benchq, a1.fe_doc, a2.fe_ven, a3.tx_descor, a3.tx_desLar";
            strSqlCon+=" 	       , a2.st_autpag, a2.co_ctaautpag, a4.tx_codCta, a4.tx_desLar, a2.fe_venChqAutPag, a2.tx_obs1, a3.tx_natDoc, a2.mo_pag, a2.nd_abo, a1.fe_ultmod, a3.co_cat ";
            strSqlCon+=" 	ORDER BY a1.tx_nomCli, a1.co_tipDoc,  a1.co_loc";
            strSqlCon+=" ) AS x";
            strSqlCon+=" WHERE x.nd_valPen IS NOT NULL";
            strSqlCon+=" "+strAux+"\n";
            strSqlCon+="   ORDER BY x.tx_nomCli, x.tx_desCor, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc";
            strSqlCon+=" ) AS y";
            strSqlCon+=" ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_doc=y.co_doc";
        }
        //</editor-fold>
        else{ //Para cuando tienen o no tienen VB
            strSqlCon ="";
            strSqlCon+=" SELECT *FROM( \n";
            strSqlCon+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc\n";
            //Eddye: Al hacer las pruebas se detectó que no se estaba presentando "tx_descor". Se puso como comentario dicha línea y se presentó "tx_descor".
            //strSqlCon+=" 	     , CASE WHEN a1.co_tipdoc IN(7,8) THEN a1.tx_obs1 ELSE a3.tx_descor  END AS tx_descor\n";
            strSqlCon+=" 	     , a3.tx_descor";
            strSqlCon+=" 	     , a3.tx_desLar,a1.co_doc, a2.co_reg, a1.ne_numdoc, a2.tx_numChq, a1.tx_obs2\n";
            //strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (27, 28) THEN 0 ELSE a1.co_cli END as co_cli\n";
	    //strSqlCon+=" 	     , CASE WHEN a5.tx_nom IS NOT NULL THEN a5.tx_nom ELSE a1.tx_nomcli END as tx_nomcli\n";
            strSqlCon+="             , CASE WHEN a3.co_cat IN (27) THEN 0 ELSE (CASE WHEN a3.co_cat IN (28) THEN a1.co_exp ELSE a1.co_cli END) END AS co_cli \n";
            strSqlCon+="             , CASE WHEN a3.co_cat IN (27) THEN 0 ELSE (CASE WHEN a3.co_cat IN (28) THEN a1.co_exp ELSE 0 END) END AS co_exp \n";
	    strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (27) THEN CAST ('LIQ. ADUANA' AS CHARACTER VARYING) ";
            strSqlCon+=" 	       ELSE (CASE WHEN a3.co_cat IN (28) THEN a5.tx_nom ELSE a1.tx_nomcli END) END AS tx_nomcli\n";
  	    strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (27, 28) THEN 0 ELSE a1.co_ben END AS co_ben\n";
            strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (27) THEN CAST ('LIQ. ADUANA' AS CHARACTER VARYING) ";
            strSqlCon+=" 	       ELSE (CASE WHEN a3.co_cat IN (28) THEN a5.tx_nom ELSE a1.tx_benchq END) END AS tx_benchq\n";
            strSqlCon+=" 	     , a1.fe_doc, a2.fe_ven AS fe_ven, a3.tx_natDoc\n";
            if(  (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)  )
                strSqlCon+=" 	     , CASE WHEN a3.tx_natDoc='I' THEN (abs(a2.mo_pag) - abs(a2.nd_abo))";
            else
            {
                //strSqlCon+=" 	     , CASE  WHEN a3.tx_natDoc='I' THEN (abs(a2.mo_pag) - abs(a2.nd_abo))  *(-1)";
                strSqlCon+=" 	     , CASE  WHEN a3.tx_natDoc='I' THEN (a2.mo_pag+a2.nd_abo)";
            }
            strSqlCon+=" 	     WHEN a3.tx_natDoc='E' THEN (abs(a2.mo_pag) - abs(a2.nd_abo)) *(-1)\n";
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   ){
                strSqlCon+=" 	     WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('FAPR', 'FAPS', 'NCPS', 'NCPR') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))\n";
                strSqlCon+=" 	     WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('NDPS', 'NDPR') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo)) * (-1)\n";
            }
            else{
                strSqlCon+=" 	     WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('FACC') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))*(-1)\n";
                strSqlCon+=" 	     WHEN (a3.tx_natDoc='A'  AND a1.tx_obs1 IN('NCCO') ) THEN (abs(a2.mo_pag) - abs(a2.nd_abo))*(-1)\n";
                //Eddye: Se agregó para poder procesar "7=CXCINI" y "320=CXCINID".
                strSqlCon+=" 	     WHEN (a3.tx_natDoc='A' AND a1.co_tipDoc IN (7, 320)) THEN a2.mo_pag+a2.nd_abo";
            }
            strSqlCon+=" 	     END AS nd_valPen\n";
            strSqlCon+=" 	     , a2.st_autpag, a2.co_ctaautpag, a4.tx_codCta, a4.tx_desLar, a2.fe_venChqAutPag, a2.tx_obs1 AS tx_obsMot, a1.fe_ultmod\n ";
            strSqlCon+=" 	     , (CASE WHEN a3.co_cat IN (27, 28) THEN 'S' ELSE 'N' END) AS st_PagImp  \n ";  //Para indicar que son documentos de pagos de importaciones
            strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (27) THEN 'S' ELSE 'N' END AS st_pagAra\n";
            strSqlCon+=" 	     , CASE WHEN a3.co_cat IN (28) THEN 'S' ELSE 'N' END AS st_pagExt\n";
            strSqlCon+=" 	FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagmovinv AS a2\n";
            strSqlCon+=" 	ON a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipdoc = a2.co_tipdoc AND a1.co_doc = a2.co_doc\n";
            strSqlCon+=" 	INNER JOIN tbm_cabtipdoc AS a3 ON a1.co_emp = a3.co_emp AND a1.co_loc = a3.co_loc AND a1.co_tipdoc = a3.co_tipdoc\n";
            strSqlCon+=" 	LEFT OUTER JOIN tbm_plaCta AS a4 ON a2.co_emp=a4.co_emp AND a2.co_ctaAutPag=a4.co_cta\n";
            strSqlCon+=" 	LEFT OUTER JOIN tbm_expImp AS a5 ON a1.co_exp=a5.co_exp\n";
            strSqlCon+=" 	WHERE a1.co_emp <>"+objParSis.getCodigoEmpresaGrupo()+" \n";
            if(objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                strSqlCon+=" 	AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "\n";
            }
            if(   (objParSis.getCodigoMenu()==1777) ||  (objParSis.getCodigoMenu()==1769)  || (objParSis.getCodigoMenu()==1773)   )  {
                strSqlCon+=" 	AND a3.ne_mod IN(2,4,5)\n";
                strSqlCon+=" 	AND CASE WHEN a3.tx_natDoc='I' THEN (a2.mo_pag + a2.nd_abo) >0 WHEN a3.tx_natDoc='E' THEN (a2.mo_pag + a2.nd_abo) <0 ELSE (a2.mo_pag + a2.nd_abo) >0  END \n";
            }
            else{
                strSqlCon+=" 	AND a3.ne_mod IN(1,3)\n";
                //Eddye: Se agregó para poder procesar "7=CXCINI" y "320=CXCINID". Se cambió en el ELSE el "<0" por ">0" porque hacía que no aparecieran los "7=CXCINI" y "320=CXCINID".
                //strSqlCon+=" 	AND CASE WHEN a3.tx_natDoc='I' THEN (a2.mo_pag + a2.nd_abo) >0 WHEN a3.tx_natDoc='E' THEN (a2.mo_pag + a2.nd_abo) >0 ELSE (a2.mo_pag + a2.nd_abo) <0  END\n";
                strSqlCon+=" 	AND CASE WHEN a3.tx_natDoc='I' THEN (a2.mo_pag + a2.nd_abo) >0 WHEN a3.tx_natDoc='E' THEN (a2.mo_pag + a2.nd_abo) >0 ELSE (a2.mo_pag + a2.nd_abo) >0  END\n";
            }

            strSqlCon+=" 	AND a1.st_reg NOT IN('I','E')\n";
            strSqlCon+=" 	AND a2.st_reg IN('A','C') AND (a2.nd_porRet=0 OR a2.nd_porRet IS NULL) \n";

            //solo para pagos a proveedores, clientes no necesitan este proceso adicional
            if(  (objParSis.getCodigoMenu()==1769)  ||  (objParSis.getCodigoMenu()==1773)  || (objParSis.getCodigoMenu()==1777)   )  {
                if(chkMosDocSinFacPrv.isSelected()){//Se presentan todos los documentos, incluyendo los que no tienen Factura de Proveedor asociado
                    strSqlCon+=" AND CASE WHEN (a3.st_docNecMarLis='S') THEN a1.st_docMarLis='S'";
                    strSqlCon+="     WHEN (a3.st_docNecMarLis='N' OR a3.st_docNecMarLis IS NULL) THEN (a1.st_docMarLis='S' OR a1.st_docMarLis='N' OR a1.st_docMarLis IS NULL) END\n";
                }
                else{//solo se presentan los que tienen asociada una factura de proveedor
                    strSqlCon+=" AND CASE WHEN (a3.st_docNecMarLis='S') THEN a1.st_docMarLis='S'";
                    strSqlCon+="     WHEN (a3.st_docNecMarLis='N' OR a3.st_docNecMarLis IS NULL)";
                    strSqlCon+="     THEN (a2.tx_numSer IS NOT NULL AND a2.tx_numAutSri IS NOT NULL AND a2.tx_fecCad IS NOT NULL AND a2.tx_numChq IS NOT NULL OR a1.st_emiChqAntRecFacPrv='S') END\n";
                }                       
            }

            strSqlCon+=" 	GROUP BY a1.tx_obs1, a1.co_emp, a1.co_loc, a1.co_tipdoc\n";
            strSqlCon+=" 	       , a1.co_doc, a2.co_reg, a1.ne_numdoc, a2.tx_numChq, a1.tx_obs2\n";
            strSqlCon+=" 	       , a5.co_exp, a1.co_ben, a5.tx_nom, a1.tx_benchq, a1.fe_doc, a2.fe_ven, a3.tx_descor, a3.tx_desLar\n ";
            strSqlCon+=" 	       , a2.st_autpag, a2.co_ctaautpag, a4.tx_codCta, a4.tx_desLar, a2.fe_venChqAutPag, a2.tx_obs1, a3.tx_natDoc, a2.mo_pag, a2.nd_abo, a1.fe_ultmod, a3.co_cat \n";
            strSqlCon+=" 	ORDER BY a1.tx_nomCli, a1.co_tipDoc,  a1.co_loc\n"; 
            strSqlCon+=" ) AS x\n";
            strSqlCon+=" WHERE x.nd_valPen IS NOT NULL\n";
            strSqlCon+=" "+strAux+"\n";
            strSqlCon+=" ORDER BY x.tx_nomCli, x.tx_desCor, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc\n";
        }   

        return strSqlCon;
    }
   
   
   
   
}