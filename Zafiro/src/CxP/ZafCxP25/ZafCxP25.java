
package CxP.ZafCxP25;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafPagImp;
import Librerias.ZafNotCorEle.ZafNotCorEle;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafSegMovInv.ZafSegMovInv;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  
import java.util.ArrayList;  
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;  
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.UltDocPrint;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * @author sistemas9
 */
public class ZafCxP25 extends javax.swing.JInternalFrame
{
    //Constantes: JTable TblDat
    private static final int INT_TBL_DAT_LIN  = 0 ; 
    private static final int INT_TBL_DAT_COD_EMP_PED = 1;              
    private static final int INT_TBL_DAT_COD_LOC_PED = 2;              
    private static final int INT_TBL_DAT_COD_TIPDOC_PED = 3;               
    private static final int INT_TBL_DAT_COD_DOC_PED = 4;     
    private static final int INT_TBL_DAT_NUM_PED_IMP = 5;    
    private static final int INT_TBL_DAT_COD_EMP_IMP = 6;    
    private static final int INT_TBL_DAT_COD_REG_CAR = 7;               //Código de registro del cargo a pagar
    private static final int INT_TBL_DAT_COD_CAR_PAG = 8;               //Código de cargo a pagar
    private static final int INT_TBL_DAT_NOM_CAR_PAG = 9;               //Nombre del cargo a pagar
    private static final int INT_TBL_DAT_BUT_CAR_PAG = 10; 
    private static final int INT_TBL_DAT_VAL_PAG_IMP  =11;              //Valor a pagar  del cargo del pedido embarcado.
    
    //Constantes: Asiento de Diario.
    private Vector vecRegDia, vecDatDia;
    final int INT_VEC_DIA_LIN=0;
    final int INT_VEC_DIA_COD_CTA=1;
    final int INT_VEC_DIA_NUM_CTA=2;
    final int INT_VEC_DIA_BUT_CTA=3;
    final int INT_VEC_DIA_NOM_CTA=4;
    final int INT_VEC_DIA_DEB=5;
    final int INT_VEC_DIA_HAB=6;
    final int INT_VEC_DIA_REF=7;
    final int INT_VEC_DIA_EST_CON=8;
    
    //ArrayList para consultar
    private ArrayList arlDatCon, arlRegCon;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;   
    private static final int INT_ARL_CON_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_COD_DOC=3;  
    private static final int INT_ARL_CON_TXT_USR_ING=4;  
    private static final int INT_ARL_CON_TXT_USR_MOD=5;  
    private int intIndiceCon=0;    
    
    //Variables
    private Connection con;                           //Coneccion a la base donde se encuentra la Documento
    private Statement stm;                            //Statement para la Documento 
    private ResultSet rst;                            //Resultset que tendra los datos de la cabecera de la Documento
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafRptSis objRptSis;                       //Reportes del Sistema.
    private ZafAsiDia objAsiDia, objAsiDiaTraBan;
    private ZafSegMovInv objSegMovInv;
    private UltDocPrint objUltDocPrint;                //Para trabajar con la informacion de tipo de documento
    private ZafVenCon vcoTipDoc; 
    private ZafVenCon vcoPedImp;                       //Ventana de consulta "Pedidos".
    private ZafVenCon vcoCarPag;                       //Ventana de consulta "Impuestos a pagar".
    private ZafImp objZafImp;
    private ZafPagImp objPagImp;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblCelRenBut objTblCelRenBut;        //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk;        //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;        //Editor: JCheckBox en celda.    
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCar;
    private ZafTblCelEdiButVco objTblCelEdiButVcoCar;
    private ZafTblModLis objTblModLis;
    private ZafMouMotAda objMouMotAda;
    private ZafTblFilCab objTblFilCab;
    private ZafTblEdi objTblEdi;                    //Editor: Editor del JTable.
    private ZafDocLis objDocLis;                    //Instancia de clase que detecta cambios
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafDatePicker dtpFecCar;
    private java.util.Date datFecAux;
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    private ZafNotCorEle objNotCorEle;
    private MiToolBar objTooBar;
    private Vector vecDat,  vecCab,  vecReg;
    private boolean blnHayCam = false;   //Detecta ke se hizo cambios en el documento
    
    private BigDecimal bgdSig=new BigDecimal("0");
    private int intCodEmpImp, intCodLocImp, intCodCliImp;
    private int intCodEmpTraBan, intCodLocTraBan, intCodTipDocTraBan, intCodDocTraBan;
    private int intCodEmpMovBan, intCodLocMovBan, intCodTipDocMovBan, intCodDocMovBan;
    
    private int intCodCtaDocPag=0;
    
    private Object objCodSeg;
    
    private String strNumCtaDocPag="", strNomCtaDocPag=""; 
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strNumPedEmb;                                //Contenido del campo al obtener el foco.
    
    private String strSQL, strAux;    //EL filtro de la Consulta actual
    
    private String strVer = " v0.2.10";
  
        
    /** Crea una nueva instancia de la clase ZafCxP25. */
    public ZafCxP25(ZafParSis obj)
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();   
                if (!configurarFrm())
                    exitForm();
                agregarDocLis();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
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
            //Título de la ventana.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti = new ZafUtil();
            objZafImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            objPagImp = new ZafPagImp(objParSis, this);  //Pagos de Impuestos
            objUltDocPrint = new UltDocPrint(objParSis);  
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDiaTraBan = new ZafAsiDia(objParSis);
            objNotCorEle = new ZafNotCorEle(objParSis);       //Libreria para enviar correos electronicos a usuario.
            objSegMovInv=new ZafSegMovInv(objParSis, this);
                        
            //Fecha del documento.
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecDoc.setBounds(582, 4, 100, 20);
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            
            //Fecha de carta.
            dtpFecCar = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecCar.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecCar.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecCar.setBounds(125, 15, 100, 20);
            dtpFecCar.setText("");
            panDatCar.add(dtpFecCar);                   
  
            //Campos 
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtNomEmpPedEmb.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false); 
            
            //Botón Correo Electrónico          
            butCorEle.setVisible(false);            
            if(objParSis.getCodigoUsuario()==1) { 
                butCorEle.setVisible(true);
            }            
            
            //ToolBar
            objTooBar=new MiToolBar(this);
            panBar.add(objTooBar);
            
            //objTooBar.setVisibleModificar(false);  /* Se comenta para poder modificar la fecha y valor de la carta.*/
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            if(objParSis.getCodigoUsuario()==1){  //Sólo admin podrá anular documento.
                objTooBar.setVisibleAnular(true);
            }
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            if (!objPerUsr.isOpcionEnabled(4249)) 
            {
                objTooBar.setVisibleInsertar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4250)) 
            {
                objTooBar.setVisibleConsultar(false);
            }
            if (!objPerUsr.isOpcionEnabled(4254)) 
            {
                objTooBar.setVisibleImprimir(false);
            }
            if (!objPerUsr.isOpcionEnabled(4255)) 
            {
                objTooBar.setVisibleVistaPreliminar(false);
            }            

            //***********************************************************************
            //Asiento de diario: Pago de Impuesto
            vecDatDia = new Vector();
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
             });
            
            objAsiDia.setEditable(true);
            panAsiDiaPagImp.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //tabFrm.remove(panAsiDiaPagImp);  //No mostrar Tab Asiento de Diario: Pago de Impuestos. No se muestra porque es un asiento nulo.
            
            //***********************************************************************
            pack();
            
            //Asiento de diario: Transferencia Bancaria
            panAsiDiaTraBan.add(objAsiDiaTraBan,java.awt.BorderLayout.CENTER);

            //Metodo para agregar o eliminar lineas con enter y con escape
            agregarDocLis();
            objUti.desactivarCom(this);
            
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCarPag();
            
            //Configurar los JTables.
            configurarTblDat();   
            
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
            vecCab = new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");  
            vecCab.add(INT_TBL_DAT_COD_EMP_PED,"Cód.Emp."); 
            vecCab.add(INT_TBL_DAT_COD_LOC_PED,"Cód.Loc."); 
            vecCab.add(INT_TBL_DAT_COD_TIPDOC_PED,"Cód.Tip.Doc."); 
            vecCab.add(INT_TBL_DAT_COD_DOC_PED,"Cód.Doc."); 
            vecCab.add(INT_TBL_DAT_NUM_PED_IMP,"Pedido"); 
            vecCab.add(INT_TBL_DAT_COD_EMP_IMP,"Cód.Emp.Imp."); 
            vecCab.add(INT_TBL_DAT_COD_REG_CAR,"Cód.Reg.Car."); 
            vecCab.add(INT_TBL_DAT_COD_CAR_PAG,"Cód.Car.Pag."); 
            vecCab.add(INT_TBL_DAT_NOM_CAR_PAG,"Cargo a Pagar");
            vecCab.add(INT_TBL_DAT_BUT_CAR_PAG,"");
            vecCab.add(INT_TBL_DAT_VAL_PAG_IMP, "Valor a Pagar");
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Editor de bósqueda.
            objTblBus = new ZafTblBus(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);  
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_IMP).setPreferredWidth(30);              
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_CAR).setPreferredWidth(60);     
            tcmAux.getColumn(INT_TBL_DAT_COD_CAR_PAG).setPreferredWidth(60);     
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CAR_PAG).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_IMP).setPreferredWidth(120); 
                      
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_PED, tblDat);            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIPDOC_PED, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_PED, tblDat);   
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_IMP, tblDat);
            if(objParSis.getCodigoUsuario()!=1) {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_CAR, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CAR_PAG, tblDat);
            }        
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                        
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            tblDat.getTableHeader().setReorderingAllowed(false);            
            objMouMotAda=new ZafMouMotAda();            
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);            
            
            //Configurar JTable: Renderizar celdas. 
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_IMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            //vecAux.add("" + INT_TBL_DAT_NOM_CAR_PAG);
            //vecAux.add("" + INT_TBL_DAT_BUT_CAR_PAG);
            vecAux.add("" + INT_TBL_DAT_VAL_PAG_IMP);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_VAL_PAG_IMP);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;            
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_PAG_IMP, objTblMod.INT_COL_DBL, new Integer(0), null);            

            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_CAR_PAG).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PAG_IMP).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil = tblDat.getSelectedRow();
                    if (txtCodTipDoc.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Tipo de documento no ha sido ingresado.<BR>Ingrese el campo tipo de documento y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtDesCorTipDoc.requestFocus();
                    }                    
                    else if (txtNumPedEmb.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Pedido no ha sido ingresado.<BR>Ingrese el campo Pedido y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxt.setCancelarEdicion(true);
                        txtNumPedEmb.requestFocus();
                    }   
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularTot();
                }
            });
            objTblCelEdiTxt=null;
            
            
            //Configurar JTable: Editor de celdas 
            //<editor-fold defaultstate="collapsed" desc="/* Ventana de consulta: Impuesto a pagar.*/">
            int intColVen[]=new int[10];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            intColVen[8]=9;
            intColVen[9]=10;
            int intColTbl[]=new int[10];
            intColTbl[0]=INT_TBL_DAT_COD_EMP_PED;
            intColTbl[1]=INT_TBL_DAT_COD_LOC_PED;
            intColTbl[2]=INT_TBL_DAT_COD_TIPDOC_PED;
            intColTbl[3]=INT_TBL_DAT_COD_DOC_PED;
            intColTbl[4]=INT_TBL_DAT_NUM_PED_IMP;
            intColTbl[5]=INT_TBL_DAT_COD_EMP_IMP;
            intColTbl[6]=INT_TBL_DAT_COD_REG_CAR;
            intColTbl[7]=INT_TBL_DAT_COD_CAR_PAG;
            intColTbl[8]=INT_TBL_DAT_NOM_CAR_PAG;
            intColTbl[9]=INT_TBL_DAT_VAL_PAG_IMP;     

            //Búsqueda por Decripción.
            objTblCelEdiTxtVcoCar=new ZafTblCelEdiTxtVco(tblDat, vcoCarPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CAR_PAG).setCellEditor(objTblCelEdiTxtVcoCar);
            objTblCelEdiTxtVcoCar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    if (txtNumPedEmb.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Pedido Embarcado no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un pedido embarcado y vuelva a intentarlo.</HTML>");
                        objTblCelEdiTxtVcoCar.setCancelarEdicion(true);
                        txtNumPedEmb.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCarPag.limpiar();
                    vcoCarPag.setCampoBusqueda(1);
                    vcoCarPag.setCriterio1(11);
                    strSQL+=" AND x.tx_numPed LIKE '%" + txtNumPedEmb.getText().replaceAll("'", "''") + "%'";
                    strSQL+=" GROUP BY x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPed, x.co_regCar, x.co_carPag, x.tx_nomCarPag, x.nd_ValCarPag, x.nd_ValPagImp, x.co_EmpImp, x.ne_ubi";
                    strSQL+=" ORDER BY x.ne_ubi";
                    vcoCarPag.setCondicionesSQL(strSQL);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoCar.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoCarPag.getValueAt(1),  intFil, INT_TBL_DAT_COD_EMP_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(2),  intFil, INT_TBL_DAT_COD_LOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(3),  intFil, INT_TBL_DAT_COD_TIPDOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(4),  intFil, INT_TBL_DAT_COD_DOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(5),  intFil, INT_TBL_DAT_NUM_PED_IMP);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(6),  intFil, INT_TBL_DAT_COD_EMP_IMP);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(7),  intFil, INT_TBL_DAT_COD_REG_CAR);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(8),  intFil, INT_TBL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(9),  intFil, INT_TBL_DAT_NOM_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(10), intFil, INT_TBL_DAT_VAL_PAG_IMP);      
                    }
                }
            });
            //Búsqueda por botón.
            objTblCelEdiButVcoCar=new ZafTblCelEdiButVco(tblDat, vcoCarPag, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CAR_PAG).setCellEditor(objTblCelEdiButVcoCar);
            objTblCelEdiButVcoCar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    if (txtNumPedEmb.getText().equals("")){
                        mostrarMsgInf("<HTML>El campo Pedido Embarcado no ha sido ingresado.<BR>Para agregar un cargo a pagar ingrese un pedido embarcado y vuelva a intentarlo.</HTML>");
                        objTblCelEdiButVcoCar.setCancelarEdicion(true);
                        txtNumPedEmb.requestFocus();
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCarPag.limpiar();
                    vcoCarPag.setCampoBusqueda(1);
                    vcoCarPag.setCriterio1(11);
                    strSQL+=" AND x.tx_numPed LIKE '%" + txtNumPedEmb.getText().replaceAll("'", "''") + "%'";
                    strSQL+=" GROUP BY x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPed, x.co_regCar, x.co_carPag, x.tx_nomCarPag, x.nd_ValCarPag, x.nd_ValPagImp, x.co_EmpImp, x.ne_ubi";
                    strSQL+=" ORDER BY x.ne_ubi";
                    vcoCarPag.setCondicionesSQL(strSQL);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoCar.isConsultaAceptada()) {
                        objTblMod.setValueAt(vcoCarPag.getValueAt(1),  intFil, INT_TBL_DAT_COD_EMP_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(2),  intFil, INT_TBL_DAT_COD_LOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(3),  intFil, INT_TBL_DAT_COD_TIPDOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(4),  intFil, INT_TBL_DAT_COD_DOC_PED);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(5),  intFil, INT_TBL_DAT_NUM_PED_IMP);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(6),  intFil, INT_TBL_DAT_COD_EMP_IMP);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(7),  intFil, INT_TBL_DAT_COD_REG_CAR);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(8),  intFil, INT_TBL_DAT_COD_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(9),  intFil, INT_TBL_DAT_NOM_CAR_PAG);
                        objTblMod.setValueAt(vcoCarPag.getValueAt(10), intFil, INT_TBL_DAT_VAL_PAG_IMP); 
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            //</editor-fold>

            //Libero los objetos auxiliares.
            tcmAux = null;
            
            setEditable(false);
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
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_EMP_PED:
                    strMsg="Código de empresa";                  
                    break;   
                case INT_TBL_DAT_COD_LOC_PED:
                    strMsg="Código de local";                  
                    break;       
                case INT_TBL_DAT_COD_TIPDOC_PED:
                    strMsg="Código de tipo de documento";                  
                    break;      
                case INT_TBL_DAT_COD_DOC_PED:
                    strMsg="Código de documento";                  
                    break;        
                case INT_TBL_DAT_NUM_PED_IMP:
                    strMsg="Número de pedido";                  
                    break;   
                case INT_TBL_DAT_COD_EMP_IMP:
                    strMsg="Código de Empresa Importadora";                  
                    break;                       
                case INT_TBL_DAT_COD_REG_CAR:
                    strMsg="Código de registro del cargo";                  
                    break;
                case INT_TBL_DAT_COD_CAR_PAG:
                    strMsg="Código de cargo";
                    break;                       
                case INT_TBL_DAT_NOM_CAR_PAG:
                    strMsg="Nombre del cargo";
                    break;
                case INT_TBL_DAT_VAL_PAG_IMP:
                    strMsg="Valor a pagado del cargo";
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
                    // tblDat.setValueAt("V", tblDat.getSelectedRow(), INT_TBL_ESTADO);
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    calcularTot();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:                         
                    break;
            }
        }
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            blnHayCam = true;
        }
    }       
        
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txtValCar.getDocument().addDocumentListener(objDocLis);
    }
    
    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }    
        
    public void noEditable(boolean blnEditable)
    {
        txtCodDoc.setEditable(blnEditable);  
        txaObs2.setEditable(blnEditable);  
        txtNomEmpPedEmb.setEditable(blnEditable); 
        txtValDoc.setEditable(blnEditable);
        txtValDoc.setBackground(Color.WHITE);
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
            arlAncCol.add("325");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if(objParSis.getCodigoUsuario()==1){
                strSQL ="";
                strSQL+=" SELECT distinct a.co_tipdoc, a.tx_descor, a.tx_deslar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocPrg AS a1 LEFT OUTER JOIN tbm_cabTipDoc as a";
                strSQL+=" ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipdoc=a.co_tipdoc)" ;
                strSQL+=" WHERE a1.co_emp = " + objParSis.getCodigoEmpresa() ;
                strSQL+=" AND a1.co_loc = " + objParSis.getCodigoLocal() ;
                strSQL+=" AND a1.co_mnu = " + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a.tx_desCor";
            }
            else 
            {     
                strSQL ="";
                strSQL+=" SELECT a.co_tipdoc, a.tx_desCor, a.tx_desLar, a.ne_ultDoc, a.tx_natDoc ";
                strSQL+=" FROM tbr_tipDocUsr AS a1 INNER JOIN  tbm_cabTipDoc AS a";
                strSQL+=" ON (a.co_emp=a1.co_Emp AND a.co_loc=a1.co_loc AND a.co_tipdoc=a1.co_tipdoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a.tx_desCor";
            }
           
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=4;
            intColOcu[1]=5;
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Pedido Embarcado".
     */
    private boolean configurarVenConPedEmb()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.tx_numPed");
            arlCam.add("a1.co_empImp");
            arlCam.add("a1.co_locImp");
            arlCam.add("a1.co_cliImp");
            arlCam.add("a1.tx_nomEmp");
            arlCam.add("a1.ne_ultDoc");

            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Pedido");
            arlAli.add("Cód.Emp.Imp.");
            arlAli.add("Cód.Loc.Imp.");            
            arlAli.add("Cód.Cli.Imp.");  
            arlAli.add("Nom.Emp."); 
            arlAli.add("Ult.Doc."); 

            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("100");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("50");
            
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT b1.co_empImp, b1.co_locImp, b2.co_cliEmpOrg AS co_cliImp, b3.tx_nom AS tx_nomEmp,( b4.ne_ultDoc+1) AS ne_ultDoc, b1.tx_numPed\n";
            strSQL+=" FROM(\n";
            strSQL+="        SELECT a3.co_empImp, a3.co_locImp, (CASE WHEN a1.tx_numDocCon IS NOT NULL THEN a1.tx_numDocCon ELSE (CASE WHEN a1.tx_numDoc4 IS NOT NULL THEN a1.tx_numDoc4 ELSE a1.tx_numDoc2 END) END) AS tx_numPed ";
            strSQL+="        FROM tbm_cabPedEmbImp AS a1\n";
            strSQL+="        INNER JOIN (SELECT co_emp as co_empImp, co_loc as co_locImp FROM tbm_loc as a WHERE a.st_Reg IN ('P') ) AS a3 ON a1.co_imp=a3.co_empImp\n";  
            strSQL+="        WHERE a1.st_reg NOT IN('E','I') \n";           //Solo pedidos activos.
            strSQL+="        AND (a1.st_doc='A' OR a1.st_doc IS NULL) \n";  //Solo pedidos activos, no reversión, ni inactivos
            strSQL+="        AND a1.co_exp NOT IN (32)";                    //Compras Locales

            if(objTooBar.getEstado()!='c'){
                strSQL+="    AND NOT EXISTS ( SELECT * FROM tbm_cabMovInv as x WHERE a1.co_emp=x.co_empRelPedEmbImp AND a1.co_loc=x.co_locRelPedEmbImp";
                strSQL+="                     AND a1.co_tipDoc=x.co_tipDocRelPedEmbImp AND a1.co_doc=x.co_DocRelPedEmbImp  )"; /* No mostrar los pedidos que tienen ingresos por importación */                
                strSQL+="    AND (CASE WHEN a1.st_ciePagImp IS NULL THEN 'P' ELSE a1.st_ciePagImp END) NOT IN ('S')\n";  //Mostrar pedidos abiertos, es decir, que no hayan sido cerrados para generar pagos de impuestos.
                strSQL+="    AND a1.st_cie IN ('S')";  //Mostrar pedidos con cierre de pedido embarcado, es decir, que haya generado o no la provisión.
            }
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                strSQL+="    AND a1.co_imp="+objParSis.getCodigoEmpresa();
            
            strSQL+="        GROUP BY a1.tx_numDocCon, a1.tx_numDoc2, a1.tx_numDoc4, a3.co_empImp, a3.co_locImp";
            strSQL+=" ) AS b1\n";     
            strSQL+=" INNER JOIN tbm_cfgEmpRel as b2 ON b1.co_empImp=b2.co_empOrg AND b1.co_empImp=b2.co_empDes\n";   
            strSQL+=" INNER JOIN tbm_emp as b3 ON b1.co_empImp=b3.co_emp\n";   
            strSQL+=" INNER JOIN tbm_cabTipDoc AS b4 ON b1.co_empImp=b4.co_emp AND b1.co_locImp=b4.co_loc AND "+txtCodTipDoc.getText()+"=b4.co_tiPDoc\n";
            strSQL+=" GROUP BY b1.co_empImp, b1.co_locImp, b2.co_cliEmpOrg, b3.tx_nom, b4.ne_ultDoc, b1.tx_numPed\n";
            //System.out.println("configurarVenConPedEmb: "+strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[5];
            intColOcu[0]=2;
            intColOcu[1]=3;
            intColOcu[2]=4;
            intColOcu[3]=5;
            intColOcu[4]=6;
            
//            if(objParSis.getCodigoUsuario()==1){
//                vcoPedImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedido Embarcado", strSQL, arlCam, arlAli, arlAncCol);
//            }
//            else {
                vcoPedImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedido Embarcado", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
//            }
            
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPedImp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoPedImp.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoPedImp.setCampoBusqueda(0);
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
     * mostrar los "Cargos a Pagar".
     */
    private boolean configurarVenConCarPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_empPedEmb");
            arlCam.add("a1.co_locpPedEmb");
            arlCam.add("a1.co_tipDocPedEmb");
            arlCam.add("a1.co_docPedEmb");
            arlCam.add("a1.tx_numPed");
            arlCam.add("a1.co_empImp");
            arlCam.add("a1.co_regCar");
            arlCam.add("a1.co_carPag");
            arlCam.add("a1.tx_nomCarPag");
            arlCam.add("a1.nd_valCarPag");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Cód.Imp.");
            arlAli.add("Pedido");
            arlAli.add("Cód.Reg.");
            arlAli.add("Cód.Car.");
            arlAli.add("Cargo");
            arlAli.add("Val.Ped.Emb.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("55");
            arlAncCol.add("55");
            arlAncCol.add("55");
            arlAncCol.add("55");
            arlAncCol.add("55");
            arlAncCol.add("100");
            arlAncCol.add("55");
            arlAncCol.add("55");
            arlAncCol.add("220");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL ="";
            strSQL+=" SELECT x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPed, x.co_regCar, x.co_carPag, x.tx_nomCarPag, x.nd_ValCarPag, x.nd_ValPagImp, x.co_EmpImp, x.ne_ubi";
            strSQL+=" FROM (";
            strSQL+="    SELECT a3.co_Emp AS co_EmpPedEmb, a3.co_loc AS co_locPedEmb, a3.co_tipDoc AS co_tipDocPedEmb, a3.co_doc AS co_docPedEmb, a3.co_imp AS co_EmpImp, a3.st_reg, a1.ne_ubi";
            strSQL+="         , a2.co_Reg as co_regCar, a1.co_carPag, a1.tx_nom as tx_nomCarPag, a2.nd_valCarPag, a4.nd_valPagImp";
            strSQL+="         , ( CASE WHEN a3.tx_numDocCon IS NOT NULL THEN a3.tx_numDocCon ELSE (CASE WHEN a3.tx_numDoc4 IS NOT NULL THEN a3.tx_numDoc4 ELSE a3.tx_numDoc2 END) END) AS tx_numPed";
            strSQL+="    FROM tbm_carPagImp AS a1 ";
            strSQL+="    INNER JOIN tbm_carPagPedEmbImp AS a2 ON a1.co_carPag=a2.co_CarPag ";
            strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc ";
            strSQL+="    LEFT OUTER JOIN (";
            strSQL+=" 	      SELECT a3.co_empRelPedEmb AS co_empRel, a3.co_locRelPedEmb AS co_locRel, a3.co_tipDocRelPedEmb AS co_tipDocRel, a3.co_docRelPedEmb AS co_docRel, a3.co_regRelPedEmb AS co_regRel, a2.nd_tot as nd_valPagImp";
            strSQL+="         FROM tbm_docGenOtrMovBan AS a1 ";
            strSQL+="         INNER JOIN tbm_detConIntMovInv AS a2 ON a1.co_empRelCabMovInv=a2.co_emp AND a1.co_locRelCabMovInv=a2.co_loc AND a1.co_tipDocRelCabMovInv=a2.co_tipDoc AND a1.co_docRelCabMovInv=a2.co_doc";
            strSQL+="         INNER JOIN tbr_detConIntCarPagInsImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_reg";
            strSQL+="         GROUP BY a3.co_empRelPedEmb, a3.co_locRelPedEmb, a3.co_tipDocRelPedEmb, a3.co_docRelPedEmb, a3.co_regRelPedEmb, a2.nd_tot";
            strSQL+="    ) AS a4"; 
            strSQL+="    ON a3.co_emp=a4.co_empRel AND a3.co_loc=a4.co_locRel AND a3.co_tipDoc=a4.co_tipDocRel AND a3.co_doc=a4.co_docRel AND a2.co_reg=a4.co_regRel";
            strSQL+="    WHERE a3.st_reg IN ('A') ";
            strSQL+="    AND a1.st_imp IN ('S') AND a1.st_pagExt='N'"; //Mostrar sólo cargos que son Impuestos.   
            strSQL+=" ) AS x";
            strSQL+=" WHERE x.st_reg IN ('A')";
            
            vcoCarPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Cargos a Pagar", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCarPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
            
            vcoCarPag.setConfiguracionColumna(6, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(8, javax.swing.JLabel.RIGHT);
            vcoCarPag.setConfiguracionColumna(9, javax.swing.JLabel.LEFT);
            vcoCarPag.setConfiguracionColumna(10, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoCarPag.setCampoBusqueda(1);
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
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //Se eliminan los datos del pedido.
                        txtNomEmpPedEmb.setText("");
                        txtNumPedEmb.setText("");
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");
                        txtValCar.setText("");
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
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
                        //Se eliminan los datos del pedido.
                        txtNomEmpPedEmb.setText("");
                        txtNumPedEmb.setText(""); 
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");          
                        txtValCar.setText("");
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //Se eliminan los datos del pedido.
                            txtNomEmpPedEmb.setText("");
                            txtNumPedEmb.setText("");    
                            chkMosParRes.setSelected(false);
                            txtValDoc.setText("");   
                            txtValCar.setText("");

                            //Número de documento
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
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
                        //Se eliminan los datos del pedido.
                        txtNomEmpPedEmb.setText("");
                        txtNumPedEmb.setText("");       
                        chkMosParRes.setSelected(false);
                        txtValDoc.setText("");  
                        txtValCar.setText("");
                        
                        //Número de documento
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                        {
                            objTblMod.removeAllRows();          
                            objAsiDia.inicializar();
                            objAsiDia.setEditable(true);
                        }
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //Se eliminan los datos del pedido.
                            txtNumPedEmb.setText("");    
                            chkMosParRes.setSelected(false);
                            txtValDoc.setText("");  
                            txtValCar.setText("");
                            
                            //Número de documento
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            bgdSig=new BigDecimal(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            //Limpiar "Detalle" y "Diario" sólo si se cambiado el "Tipo de documento".
                            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
                            {
                                objTblMod.removeAllRows();          
                                objAsiDia.inicializar();
                                objAsiDia.setEditable(true);
                            }
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
    
    private boolean mostrarPedEmb(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPedImp.setCampoBusqueda(0);
                    vcoPedImp.setVisible(true);
                    if (vcoPedImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtNumPedEmb.setText(vcoPedImp.getValueAt(1));
                        intCodEmpImp = Integer.parseInt(vcoPedImp.getValueAt(2));
                        intCodLocImp = Integer.parseInt(vcoPedImp.getValueAt(3));
                        intCodCliImp = Integer.parseInt(vcoPedImp.getValueAt(4));    
                        txtNomEmpPedEmb.setText(vcoPedImp.getValueAt(5));
                        if(objTooBar.getEstado()=='n'){
                            txtNumDoc.setText(vcoPedImp.getValueAt(6));
                            limpiarTabla();
                            cargarTabla();
                            actualizarGlo();
                        }
                    }
                    break;
               
                case 1: //Búsqueda directa por "Pedido".
                    if (vcoPedImp.buscar("a1.tx_numPed", txtNumPedEmb.getText()))
                    {
                        txtNumPedEmb.setText(vcoPedImp.getValueAt(1));
                        intCodEmpImp = Integer.parseInt(vcoPedImp.getValueAt(2));
                        intCodLocImp = Integer.parseInt(vcoPedImp.getValueAt(3));
                        intCodCliImp = Integer.parseInt(vcoPedImp.getValueAt(4));    
                        txtNomEmpPedEmb.setText(vcoPedImp.getValueAt(5));
                        if(objTooBar.getEstado()=='n'){
                            txtNumDoc.setText(vcoPedImp.getValueAt(6));
                            limpiarTabla();
                            cargarTabla();
                            actualizarGlo();
                        }
                    }
                    else
                    {
                        vcoPedImp.setCampoBusqueda(0);
                        vcoPedImp.setCriterio1(11);
                        vcoPedImp.cargarDatos();
                        vcoPedImp.setVisible(true);
                        if (vcoPedImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtNumPedEmb.setText(vcoPedImp.getValueAt(1));
                            intCodEmpImp = Integer.parseInt(vcoPedImp.getValueAt(2));
                            intCodLocImp = Integer.parseInt(vcoPedImp.getValueAt(3));
                            intCodCliImp = Integer.parseInt(vcoPedImp.getValueAt(4));    
                            txtNomEmpPedEmb.setText(vcoPedImp.getValueAt(5));
                            if(objTooBar.getEstado()=='n'){
                                txtNumDoc.setText(vcoPedImp.getValueAt(6));
                                limpiarTabla();
                                cargarTabla();
                                actualizarGlo();
                            }
                        }
                        else   {
                            txtNumPedEmb.setText(strNumPedEmb);
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocPrg";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" +  objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL ="";
                    strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+="    SELECT co_tipDoc";
                    strSQL+="    FROM tbr_tipDocUsr";
                    strSQL+="    WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+="    AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+="    AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="    AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+="    AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    if(objTooBar.getEstado()=='n') {
                        txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    }
                    bgdSig=new BigDecimal(rst.getString("tx_natDoc").equals("I")?1:-1);
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblPed = new javax.swing.JLabel();
        txtNomEmpPedEmb = new javax.swing.JTextField();
        txtNumPedEmb = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panDatCar = new javax.swing.JPanel();
        lblFecCar = new javax.swing.JLabel();
        lblValCar = new javax.swing.JLabel();
        txtValCar = new javax.swing.JTextField();
        chkMosParRes = new javax.swing.JCheckBox();
        lblNomEmpImp = new javax.swing.JLabel();
        butCorEle = new javax.swing.JButton();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenObs = new javax.swing.JPanel();
        panGenLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTxtObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panAsiDiaPagImp = new javax.swing.JPanel();
        panAsiDiaTraBan = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(700, 450));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                CerrarVentana(evt);
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

        tabFrm.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabFrm.setName("General"); // NOI18N

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(800, 110));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 125, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(115, 4, 10, 22);

        txtDesCorTipDoc.setMinimumSize(new java.awt.Dimension(0, 0));
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(25, 20));
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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(125, 4, 70, 20);

        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(195, 4, 240, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(435, 4, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(457, 4, 125, 20);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("");
        panGenCab.add(lblPed);
        lblPed.setBounds(0, 24, 80, 20);

        txtNomEmpPedEmb.setMinimumSize(new java.awt.Dimension(0, 0));
        txtNomEmpPedEmb.setPreferredSize(new java.awt.Dimension(25, 20));
        txtNomEmpPedEmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpPedEmbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpPedEmbFocusLost(evt);
            }
        });
        txtNomEmpPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpPedEmbActionPerformed(evt);
            }
        });
        panGenCab.add(txtNomEmpPedEmb);
        txtNomEmpPedEmb.setBounds(125, 24, 70, 20);

        txtNumPedEmb.setBackground(objParSis.getColorCamposObligatorios());
        txtNumPedEmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedEmbFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedEmbFocusLost(evt);
            }
        });
        txtNumPedEmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumPedEmbActionPerformed(evt);
            }
        });
        panGenCab.add(txtNumPedEmb);
        txtNumPedEmb.setBounds(195, 24, 240, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panGenCab.add(butPedImp);
        butPedImp.setBounds(435, 24, 20, 20);

        lblNumDoc.setText("Número de documento:");
        panGenCab.add(lblNumDoc);
        lblNumDoc.setBounds(457, 24, 125, 20);

        txtNumDoc.setMaximumSize(null);
        txtNumDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocActionPerformed(evt);
            }
        });
        txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocFocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc);
        txtNumDoc.setBounds(582, 24, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setInheritsPopupMenu(false);
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(457, 44, 127, 20);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(582, 44, 100, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setPreferredSize(new java.awt.Dimension(60, 14));
        panGenCab.add(lblValDoc);
        lblValDoc.setBounds(457, 64, 125, 20);
        panGenCab.add(txtValDoc);
        txtValDoc.setBounds(582, 64, 100, 20);

        panDatCar.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Carta"));
        panDatCar.setPreferredSize(new java.awt.Dimension(100, 38));
        panDatCar.setLayout(null);

        lblFecCar.setText("Fecha de la carta:");
        lblFecCar.setPreferredSize(new java.awt.Dimension(110, 15));
        panDatCar.add(lblFecCar);
        lblFecCar.setBounds(15, 15, 125, 20);

        lblValCar.setText("Valor de la carta:");
        lblValCar.setPreferredSize(new java.awt.Dimension(60, 14));
        panDatCar.add(lblValCar);
        lblValCar.setBounds(240, 15, 110, 20);

        txtValCar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panDatCar.add(txtValCar);
        txtValCar.setBounds(340, 15, 100, 20);

        chkMosParRes.setText("Mostrar párrafo de responsabilidad");
        chkMosParRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosParResActionPerformed(evt);
            }
        });
        panDatCar.add(chkMosParRes);
        chkMosParRes.setBounds(15, 35, 230, 20);

        lblNomEmpImp.setText("***");
        panDatCar.add(lblNomEmpImp);
        lblNomEmpImp.setBounds(260, 35, 180, 20);

        panGenCab.add(panDatCar);
        panDatCar.setBounds(0, 45, 450, 60);

        butCorEle.setText("CorEle");
        butCorEle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCorEleActionPerformed(evt);
            }
        });
        panGenCab.add(butCorEle);
        butCorEle.setBounds(457, 85, 73, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setPreferredSize(new java.awt.Dimension(452, 350));
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

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenObs.setLayout(new java.awt.BorderLayout());

        panGenLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenLblObs.add(lblObs2);

        panGenObs.add(panGenLblObs, java.awt.BorderLayout.WEST);

        panGenTxtObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTxtObs.add(spnObs2);

        panGenObs.add(panGenTxtObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDiaPagImp.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento Diario", panAsiDiaPagImp);

        panAsiDiaTraBan.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab(" Transferencia Bancaria", panAsiDiaTraBan);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
     
    
    private void CerrarVentana(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_CerrarVentana
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 )
           dispose();
    }//GEN-LAST:event_CerrarVentana

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus(); 
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if (txtDesCorTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
            {
                if (txtDesCorTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesLarTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    objTblMod.removeAllRows();          
                    objAsiDia.inicializar();
                    objAsiDia.setEditable(true);
                }
                else
                {
                    mostrarVenConTipDoc(1);
                }
            }
            else
                txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
    }//GEN-LAST:event_txtNumDocFocusLost

    private void txtCliDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliDirActionPerformed
    }//GEN-LAST:event_txtCliDirActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        if (txtDesLarTipDoc.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
            {
                if (txtDesLarTipDoc.getText().equals(""))
                {
                    txtCodTipDoc.setText("");
                    txtDesCorTipDoc.setText("");
                    //Limpiar la "Bodega" sólo si se cambiado el "Tipo de documento".
                    objTblMod.removeAllRows();          
                    objAsiDia.inicializar();
                    objAsiDia.setEditable(true);
                }
                else
                {
                    mostrarVenConTipDoc(2);
                }
            }
            else
                txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtNumPedEmbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedEmbFocusGained
        strNumPedEmb=txtNumPedEmb.getText();
        txtNumPedEmb.selectAll();
    }//GEN-LAST:event_txtNumPedEmbFocusGained

    private void txtNumPedEmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedEmbFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumPedEmb.getText().equalsIgnoreCase(strNumPedEmb))
        {
            if (txtNumPedEmb.getText().equals(""))
            {
                txtNumPedEmb.setText("");
            }
            else
            {
                configurarVenConPedEmb();
                mostrarPedEmb(1);
            }
        }
        else
            txtNumPedEmb.setText(strNumPedEmb);
    }//GEN-LAST:event_txtNumPedEmbFocusLost

    private void txtNumPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumPedEmbActionPerformed
        txtNumPedEmb.transferFocus();
    }//GEN-LAST:event_txtNumPedEmbActionPerformed

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        configurarVenConPedEmb();
        mostrarPedEmb(0);
    }//GEN-LAST:event_butPedImpActionPerformed

    private void txtNomEmpPedEmbFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpPedEmbFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomEmpPedEmbFocusGained

    private void txtNomEmpPedEmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpPedEmbFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomEmpPedEmbFocusLost

    private void txtNomEmpPedEmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpPedEmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomEmpPedEmbActionPerformed

    private void chkMosParResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosParResActionPerformed
    }//GEN-LAST:event_chkMosParResActionPerformed

    private void butCorEleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCorEleActionPerformed
        if(enviarCorEle()) {
            mostrarMsgInf("<HTML>Correo enviado con exito.</HTML>");
        }
    }//GEN-LAST:event_butCorEleActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCorEle;
    private javax.swing.JButton butPedImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkMosParRes;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecCar;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNomEmpImp;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValCar;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDiaPagImp;
    private javax.swing.JPanel panAsiDiaTraBan;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panDatCar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenLblObs;
    private javax.swing.JPanel panGenObs;
    private javax.swing.JPanel panGenTxtObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomEmpPedEmb;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtNumPedEmb;
    private javax.swing.JTextField txtValCar;
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /* Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". 
     * Presenta las opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }    
    
    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        String strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        }
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    } 
  
    /**
     * Esta función muestra un mensaje de validación de campos.
     * @param strNomCampo 
     */
    private void mostrarMsgValCam(String strNomCampo){
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
         
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=false;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux, true))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if(objTooBar.beforeInsertar()){
                            blnRes=objTooBar.insertar();
                        }
                        break;
                    case 'm': //Modificar
                        if(objTooBar.beforeModificar()){                        
                            blnRes=objTooBar.modificar();
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
    
    public void limpiarFrm()
    {
        //Cabecera
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodDoc.setText("");
        txtNumDoc.setText("");
        txtNomEmpPedEmb.setText("");
        txtNumPedEmb.setText("");
        txtValDoc.setText("0");
        dtpFecDoc.setText("");
        dtpFecCar.setText("");
        txtValCar.setText("0");
        chkMosParRes.setSelected(false);
        lblNomEmpImp.setText("***");
        //Detalle
        objTblMod.removeAllRows();          
        //Diario: DxP
        objAsiDia.inicializar();
        objAsiDia.setEditable(true);
        //Diario: Tranferencia Bancaria
        objAsiDiaTraBan.inicializar();
        objAsiDiaTraBan.setEditable(true);        
        //Pie de pagina
        txaObs1.setText("");
        txaObs2.setText("");        
    }       

    private void calcularTot()
    {
        BigDecimal bdeValPagImp = new BigDecimal("0");
        BigDecimal bgdTotValDoc = new BigDecimal("0");
        BigDecimal bgdValCar = new BigDecimal("0");
        try{
            for (int i=0;i<objTblMod.getRowCountTrue();i++){ 
                bdeValPagImp=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()));
                bgdTotValDoc=bgdTotValDoc.add(bdeValPagImp);
            }
            txtValDoc.setText( "" + (objUti.redondearBigDecimal(bgdTotValDoc, objParSis.getDecimalesMostrar())) );
            
            generarAsiDia();
        }
        catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e );
        }
    }
    
    private boolean isCamVal()
    {
        if(txtCodTipDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Tipo de documento");
            txtCodTipDoc.requestFocus();
            return false;
        }
               
        if(txtNumDoc.getText().equals("") ){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Número de documento");
            txtNumDoc.requestFocus();
            return false;
        }
        
        if( objUltDocPrint.getExisteDoc(Integer.parseInt( txtNumDoc.getText()) ,  Integer.parseInt(txtCodTipDoc.getText()) ) ){
            mostrarMsgInf("El número de documento ya existe.");
            txtNumDoc.requestFocus();
            txtNumDoc.selectAll();
            return false;
        }        

        if(!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha del Documento");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(!dtpFecCar.isFecha()){
            tabFrm.setSelectedIndex(0);                  
            mostrarMsgValCam("Fecha de la carta");
            dtpFecCar.requestFocus();
            return false;
        }            

        if (!objAsiDia.isDiarioCuadrado()){
            mostrarMsgInf("Asiento descuadrado.");
            tabFrm.setSelectedIndex(2);
            return false;                                    
        }
        
        if (!objAsiDia.isDocumentoCuadrado(txtValDoc.getText())){
            mostrarMsgInf("Asiento y Total del documento esta descuadrado.");
            tabFrm.setSelectedIndex(1);
            return false;                                                       
        }
               
        eliminaItmValImpCero();
        for(int intRowVal=0; intRowVal<objTblMod.getRowCountTrue(); intRowVal++)
        {
            if(objTblMod.getValueAt(intRowVal,INT_TBL_DAT_COD_CAR_PAG) != null )
            {
                BigDecimal bgdCan= new BigDecimal(Double.parseDouble(objTblMod.getValueAt(intRowVal, INT_TBL_DAT_VAL_PAG_IMP).toString()));
                if(bgdCan.compareTo(BigDecimal.ZERO)<=0) {
                    mostrarMsgInf("<HTML>Es obligatorio ingresar un valor en el detalle.<BR>Escriba en el campo y vuelva a intentarlo.</BR></HTML>");
                    tblDat.repaint();    
                    tblDat.requestFocus();
                    tblDat.editCellAt(intRowVal, INT_TBL_DAT_VAL_PAG_IMP);
                    return false; 
                }
            }
        }
        
        /* Se valida que NO exista ingreso de importación generado. Los pagos solo deben realizarse antes del Ingreso por importación.*/
        for (int i=0;i<objTblMod.getRowCountTrue();i++){   
            if(objZafImp.existeIngresoImportacion( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                                 , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                                 , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                                 , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
            )){           
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido "+txtNumPedEmb.getText()+" tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>Seleccione un Pedido Embarcado diferente y vuelva a intentarlo.</HTML>");
                return false;
            }
        }
        
        if(!validarRangoValor()) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El pago de impuesto tiene valores fuera de rango<BR>Revise los valores y vuelva a intentarlo.</HTML>");
            return false;
        }
        
        /* Se valida que NO exista pago de arancel ingresado. La validación aplica para pagos autorizados o no.
           AP:  Solo se puede ingresar un pago de impuesto por pedido. */
        for (int i=0;i<objTblMod.getRowCountTrue();i++){   
            if(objPagImp.existePagoArancel( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                          , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                          , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                          , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
            )){             
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El Pedido "+txtNumPedEmb.getText()+" tiene un <FONT COLOR=\"blue\">Pago de Impuesto</FONT> asociado.<BR>Solo se puede ingresar un pago de impuesto por pedido.</HTML>");
                return false;
            }
        }        
 
        return true; 
    }  
    
    private boolean eliminaItmValImpCero()
    {
        boolean blnRes=true;
        BigDecimal bgdCanPen=new BigDecimal("0");
        try
        {
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bgdCanPen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()));
                if(bgdCanPen.compareTo(new BigDecimal("0"))<=0)
                    objTblMod.removeRow(i);
            }
        }
        catch (Exception e)  {
            blnRes=false;
            System.err.println("ERROR eliminaItmValImpCero: " + e);
        }
        return blnRes;
    } 
    
    private boolean eliminaItmValCarPagCero()
    {
        boolean blnRes=true;
        BigDecimal bgdCanPen=new BigDecimal("0");
        try
        {
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bgdCanPen=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()));
                if(bgdCanPen.compareTo(new BigDecimal("0"))<=0)
                    objTblMod.removeRow(i);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            System.err.println("ERROR eliminaItmValCarPagCero: " + e);
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
        
        public void clickInicio()
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
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
        
        public void clickAnterior() 
        {
            try{
                if(arlDatCon.size()>0){
                    if(intIndiceCon>0){
                        if (blnHayCam) {
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
        
        public void clickSiguiente()
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon < arlDatCon.size()-1){
                        if (blnHayCam) {
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

        public void clickFin() 
        {
            try{
                 if(arlDatCon.size()>0){
                    if(intIndiceCon<arlDatCon.size()-1){
                        if (blnHayCam) {
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

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam)
                {
                    isRegPro();
                }
                
                limpiarFrm();
                noEditable(false);   
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecCar.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                setEditable(true);
                objAsiDia.setEditable(true);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;                
                dtpFecDoc.setEnabled(true);
                dtpFecCar.setEnabled(true);
                txtNumDoc.setEnabled(false);
         
                blnHayCam=false;
                txaObs1.setText("USD");
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }
        
        public void clickConsultar(){
            mostrarTipDocPre();
            setEditable(false);
            objAsiDia.setEditable(false);
            objAsiDiaTraBan.setEditable(false);
        }  
        
        public void clickModificar()
        {
            //General
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            dtpFecDoc.setEnabled(false);
            txtNomEmpPedEmb.setEditable(false);
            txtNumPedEmb.setEditable(false);
            butPedImp.setEnabled(false);
            txtNumDoc.setEditable(false);
            txtCodDoc.setEditable(false);
            txaObs1.setEditable(true);
            txaObs2.setEditable(true);   
            
            noEditable(false); 

            //Asiento de Diario
            objAsiDia.setEditable(false);
            objAsiDiaTraBan.setEditable(false);
            //Inicializar las variables que indican cambios.
            objAsiDia.setDiarioModificado(false);
            objAsiDiaTraBan.setDiarioModificado(false);
            blnHayCam=false;
            //cargarReg();            
        }   
                    
        public void clickEliminar(){
        }
                    
        public void clickAnular(){
        }

        public void clickImprimir(){
        }
        
        public void clickVisPreliminar(){
        }
        
        public void clickAceptar(){
        }
        
        public void clickCancelar(){
        }
        
        public boolean insertar()
        {
            if(!insertarReg())
              return false;

            blnHayCam=false;
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
            return true;
        }        
                
        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;            
        }

        public boolean imprimir() 
        {
            return true;
        }
        
        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null)
            {
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }

        public boolean cancelar()
        {
            boolean blnRes=true;
            try{
                if ((blnHayCam) ){
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
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean beforeInsertar()
        {
            
            if(!isCamVal())
                return false;            

            return true;
        }   
        public boolean beforeConsultar() {
            return true;
        } 
        
        public boolean beforeModificar()
        {
            /* Valida que no exista Transferencias Bancarias Asociadas al pago de impuesto. */
            for (int i=0;i<objTblMod.getRowCountTrue();i++){   
                if(objPagImp.existeTransferenciaBancariaArancel( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
                )){      
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido "+txtNumPedEmb.getText()+" tiene <FONT COLOR=\"blue\">Transferencias Bancarias por Arancel </FONT> asociadas.<BR>No se puede modificar el documento, verifique y vuelva a intentarlo.</HTML>");
                    return false;
                } 
            }                
            return true;
        }    
        
        public boolean beforeEliminar() 
        {
            return true;
        }        
        
        public boolean beforeAnular() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")){
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            
            /* Valida que NO exista ingreso de importación generado. Los pagos solo deben realizarse antes del Ingreso por importación.*/
            for (int i=0;i<objTblMod.getRowCountTrue();i++){  
                if(objZafImp.existeIngresoImportacion( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                                     , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                                     , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                                     , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
                )){
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido "+txtNumPedEmb.getText()+" tiene un <FONT COLOR=\"blue\">Ingreso por Importación </FONT>asociado.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                    return false;
                }             
            }    
            
            /* Valida que no exista Cierre de Pago de Impuestos del pedido embarcado. */
            for (int i=0;i<objTblMod.getRowCountTrue();i++){  
                if(objPagImp.existeCierrePagoImpuestos( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                                      , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                                      , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                                      , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
                )){            
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido "+txtNumPedEmb.getText()+" ha sido cerrado para pago de impuestos.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                    return false;
                }      
            }         
            
            /* Valida que no exista Transferencias Bancarias Asociadas al pago de impuesto. */
            for (int i=0;i<objTblMod.getRowCountTrue();i++){   
                if(objPagImp.existeTransferenciaBancariaArancel( Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED),3))
                                                               , Integer.parseInt(objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED),3))
                )){              
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El Pedido Embarcado tiene <FONT COLOR=\"blue\">Transferencias Bancarias por Arancel </FONT> asociadas.<BR>No se puede anular el documento, verifique y vuelva a intentarlo.</HTML>");
                    return false;
                }     
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
            consultarReg();            
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
        
        public boolean afterModificar() {
            return true;
        }
                
        public boolean afterEliminar()
        {
            return true;
        }
        
        public boolean afterAnular() 
        {
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
    //Fin ToolBar 
      
    
    private boolean consultarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );

                //Armar sentencia SQL
                strSQL ="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="      , a1.co_usrIng, a5.tx_usr as tx_nomUsrIng, a1.co_usrMod, a6.tx_usr as tx_nomUsrMod";
                strSQL+=" FROM ( (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS a5 ON a1.co_usrIng=a5.co_usr)";
                strSQL+="         LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_usrMod=a6.co_usr";
                strSQL+=" )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_DOC=a3.co_doc)";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc AND a3.co_reg=a4.co_reg)";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a7 ON (a4.co_empRelPedEmb=a7.co_emp AND a4.co_locRelPedEmb=a7.co_loc AND a4.co_tipDocRelPedEmb=a7.co_tipDoc AND a4.co_docRelPedEmb=a7.co_doc)";
                strSQL+=" WHERE a1.st_reg<>'E'";
                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL+=" AND a1.co_emp="+ objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc="+ objParSis.getCodigoLocal();
                }
                //Validar que sólo se muestre los documentos asignados al programa.
                if(txtCodTipDoc.getText().equals("")) {
                    if(objParSis.getCodigoUsuario()==1)
                        strSQL+=" AND a1.co_TipDoc in ( select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                    else
                        strSQL+=" AND a1.co_TipDoc in ( select co_tipDoc from tbr_tiPDocUsr where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+objParSis.getCodigoMenu()+")";
                }
                else 
                    strSQL+=" AND a1.co_tipDoc =" + txtCodTipDoc.getText() + "";
                    
                if (!txtCodDoc.getText().equals(""))
                    strSQL+=" AND a1.co_doc =" + txtCodDoc.getText() + "";
                if (!txtNumDoc.getText().equals(""))
                    strSQL+=" AND a1.ne_numDoc =" + txtNumDoc.getText() + "";
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                if(!txtNumPedEmb.getText().equals("")) { //Por Pedido
                    strSQL+=" AND ( CASE WHEN a7.tx_numDocCon IS NOT NULL THEN a7.tx_numDocCon ELSE (CASE WHEN a7.tx_numDoc4 IS NOT NULL THEN a7.tx_numDoc4 ELSE a7.tx_numDoc2 END) END) LIKE '%" + txtNumPedEmb.getText().replaceAll("'", "''") + "%'";
                }  
                
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+="       , a1.co_usrIng, a5.tx_usr , a1.co_usrMod, a6.tx_usr";
                strSQL+=" ORDER BY a1.fe_doc, a1.fe_ing, a1.ne_numDoc, a1.co_tipDoc, a1.co_doc"; 
                //System.out.println("consultarReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                arlDatCon = new ArrayList();
                while(rst.next())
                {
                    arlRegCon = new ArrayList();
                    arlRegCon.add(INT_ARL_CON_COD_EMP,     rst.getInt("co_emp"));
                    arlRegCon.add(INT_ARL_CON_COD_LOC,     rst.getInt("co_loc"));
                    arlRegCon.add(INT_ARL_CON_COD_TIPDOC,  rst.getInt("co_tipDoc"));
                    arlRegCon.add(INT_ARL_CON_COD_DOC,     rst.getInt("co_doc"));
                    arlRegCon.add(INT_ARL_CON_TXT_USR_ING, rst.getString("tx_nomUsrIng"));
                    arlRegCon.add(INT_ARL_CON_TXT_USR_MOD, rst.getString("tx_nomUsrMod"));
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
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg())
            {
                if(cargarDetReg())
                {
                    blnRes=true;               
                }                
            }
            else
            {
                mostrarMsgInf("Error al cargar registro");
                blnHayCam=false;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    } 
    
    private boolean cargarCabReg()
    {
        boolean blnRes=true;
        int intPosRel;
        try
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a5.co_emp as co_empPedEmb, a5.co_loc as co_locPedEmb, a5.co_tipDoc as co_tipDocPedEmb, a5.co_doc as co_docPedEmb";
                strSQL+="      , (CASE WHEN a5.tx_numDoc4 IS NOT NULL THEN a5.tx_numDoc4 ELSE a5.tx_numDoc2 END) AS tx_numPed";
                strSQL+="      , a1.co_tipDoc, a2.tx_desCor as tx_desCorTipDoc, a2.tx_desLar as tx_desLarTipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc";
                strSQL+="      , a8.fe_venChqAutPag as fe_Car, a1.nd_ValCar, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp, a7.tx_nom as tx_nomEmpPed, a1.nd_tot as nd_valDoc";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_DOC=a3.co_doc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc AND a1.co_tipDoc=a8.co_tipDoc AND a1.co_Doc=a8.co_doc)";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc AND a3.co_reg=a4.co_reg)";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a5 ON (a4.co_empRelPedEmb=a5.co_emp AND a4.co_locRelPedEmb=a5.co_loc AND a4.co_tipDocRelPedEmb=a5.co_tipDoc AND a4.co_docRelPedEmb=a5.co_doc)";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON a1.co_com=a6.co_usr";
                strSQL+=" INNER JOIN tbm_emp AS a7 ON a1.co_emp=a7.co_emp";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);  
                strSQL+=" GROUP BY a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc, a5.tx_numDoc2";
                strSQL+="        , a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.fe_doc, a1.ne_numDoc";
                strSQL+="        , a8.fe_venChqAutPag, a1.nd_ValCar, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp, a7.tx_nom, a1.nd_tot";
                //System.out.println("cargarCabReg: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {    
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desCorTipDoc");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                                      
                    strAux=rst.getString("tx_desLarTipDoc");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);   
                    
                    strAux=rst.getString("tx_nomEmpPed");
                    txtNomEmpPedEmb.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_numPed");
                    txtNumPedEmb.setText((strAux==null)?"":strAux);
                    
                    dtpFecDoc.setText("");
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    dtpFecCar.setText("");
                    dtpFecCar.setText(objUti.formatearFecha(rst.getDate("fe_car"),"dd/MM/yyyy"));
                    
                    strAux=rst.getString("nd_valDoc");
                    txtValDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("nd_valCar");
                    txtValCar.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("tx_nomEmpPed");
                    lblNomEmpImp.setText((strAux==null)?"***":"*** "+strAux);
                    
                    //Pie de pagina
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("I")){
                        strAux="Anulado";
                        objUti.desactivarCom(this);
                    }
                    else if (strAux.equals("E")){
                        strAux="Eliminado";
                        objUti.desactivarCom(this);
                    }
                    else {
                        strAux="Otro";
                        if (objTooBar.getEstado() == 'm'){
                            objUti.activarCom(this);
                            noEditable(false);
                        }
                    }
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
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
                blnHayCam=false;
            }
        }
        catch (java.sql.SQLException e)  {
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=false;
        try{
            if (cargarTabGen()){ 
                if (cargarTabAsiDiaPagImp()){ 
                    if (cargarTabAsiDiaTraBan()){ 
                        blnRes=true;
                    }
                }
            }                    
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabGen()
    {
        boolean blnRes=true;
        BigDecimal bgdAux=new BigDecimal(BigInteger.ZERO);
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a4.co_empPedEmb, a4.co_locPedEmb, a4.co_tipDocPedEmb, a4.co_docPedEmb, a2.co_reg AS co_regDxP, a3.co_regRelPedEmb AS co_regCar, a4.co_carPag";
                strSQL+="      , a4.tx_numPed, a2.tx_des AS tx_nomCarPag, a2.nd_tot AS nd_valPag, a4.co_empImp";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detConIntMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbr_detConIntCarPagInsImp AS a3";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc AND a2.co_reg=a3.co_Reg";
                strSQL+=" INNER JOIN ( ";
                strSQL+="     SELECT a.co_emp AS co_empPedEmb, a.co_loc AS co_locPedEmb, a.co_tipDoc AS co_TipDocPedEmb";
                strSQL+="          , a.co_doc AS co_docPedEmb, a1.co_Reg AS co_regPedEmb, a1.co_carPag, a1.nd_valCarPag, a.co_imp AS co_empImp ";
                strSQL+="          , (CASE WHEN a.tx_numDoc4 IS NOT NULL THEN a.tx_numDoc4 ELSE a.tx_numDoc2 END) AS tx_numPed";
                strSQL+="     FROM tbm_cabPedEmbImp AS a ";
                strSQL+="     INNER JOIN tbm_carPagPedEmbImp AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_Doc";
                strSQL+=" )AS a4 ON a3.co_empRelPedEmb=a4.co_empPedEmb AND a3.co_locRelPedEmb=a4.co_locPedEmb AND a3.co_tipDocRelPedEmb=a4.co_TipDocPedEmb AND a3.co_docRelPedEmb=a4.co_docPedEmb AND a3.co_regRelPedEmb=a4.co_regPedEmb";
                strSQL+=" WHERE a1.co_emp="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a1.co_loc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a1.co_tipDoc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a1.co_doc="+objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a1.st_reg NOT IN('E')"; 
                strSQL+=" GROUP BY a4.co_empPedEmb, a4.co_locPedEmb, a4.co_tipDocPedEmb, a4.co_docPedEmb, a2.co_reg, a3.co_regRelPedEmb, a4.co_carPag";
                strSQL+="      , a4.tx_numPed, a2.tx_des, a2.nd_tot, a4.co_empImp";            
                strSQL+=" ORDER BY a2.co_reg";
                System.out.println("cargarTabGen: "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP_PED,     rst.getString("co_empPedEmb"));    
                    vecReg.add(INT_TBL_DAT_COD_LOC_PED,     rst.getString("co_locPedEmb"));    
                    vecReg.add(INT_TBL_DAT_COD_TIPDOC_PED,  rst.getString("co_tipDocPedEmb"));    
                    vecReg.add(INT_TBL_DAT_COD_DOC_PED,     rst.getString("co_docPedEmb"));    
                    vecReg.add(INT_TBL_DAT_NUM_PED_IMP,     rst.getString("tx_numPed"));  
                    vecReg.add(INT_TBL_DAT_COD_EMP_IMP,     rst.getString("co_empImp"));  
                    vecReg.add(INT_TBL_DAT_COD_REG_CAR,     rst.getString("co_regCar"));               
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG,     rst.getString("co_carPag"));
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,     rst.getString("tx_nomCarPag"));
                    vecReg.add(INT_TBL_DAT_BUT_CAR_PAG,     "");
                    bgdAux=objUti.redondearBigDecimal( (rst.getBigDecimal("nd_valPag")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_valPag"))  , objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_DAT_VAL_PAG_IMP,     ""+bgdAux);
                    vecDat.add(vecReg);      
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                calcularTot();
            }
        }
        catch (java.sql.SQLException e)    {
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
     * Esta función permite cargar el asiento de diario del registro seleccionado.
     * @return true: Si se pudo cargar el asiento de diario del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarTabAsiDiaPagImp()
    {
        boolean blnRes=false;
        try{
            objAsiDia.inicializar();
            if(objAsiDia.consultarDiario(objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)  
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                       , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)))
            {
                blnRes=true;    
            }
        }
        catch (Exception e)   {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }        
    
    private boolean cargarTabAsiDiaTraBan()
    {
        boolean blnRes=false;
        intCodEmpTraBan=0;
        intCodLocTraBan=0;
        intCodTipDocTraBan=0;
        intCodDocTraBan=0;
        intCodEmpMovBan=0;
        intCodLocMovBan=0;
        intCodTipDocMovBan=0;
        intCodDocMovBan=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                objAsiDiaTraBan.inicializar(); //Limpiar el panel de Asiento de Diario de transferencias bancarias.
                strSQL ="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";
                strSQL+="      , a5.co_emp as co_empMovBan, a5.co_loc as co_locMovBan, a5.co_tipDoc as co_tipDocMovBan, a5.co_doc as co_docMovBan";
                strSQL+=" FROM tbm_cabDia AS a";
                strSQL+=" INNER JOIN tbm_detDia AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                //strSQL+=" INNER JOIN tbm_docgenotrmovban AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                strSQL+=" INNER JOIN tbm_CabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                strSQL+=" INNER JOIN tbm_docGenOtrMovBan AS a5 ON a.co_emp=a5.co_empRelCabDia AND a.co_loc=a5.co_locRelCabDia AND a.co_tipDoc=a5.co_tipDocRelCabDia AND a.co_dia=a5.co_docRelCabDia";
                strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" AND a.st_reg NOT IN ('E')";
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia";   
                strSQL+="        , a5.co_emp, a5.co_loc, a5.co_tipDoc, a5.co_doc "; 
                //System.out.println("cargarTabAsiDiaTraBan: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodEmpTraBan=rst.getInt("co_emp");
                    intCodLocTraBan=rst.getInt("co_loc");
                    intCodTipDocTraBan=rst.getInt("co_tipDoc");
                    intCodDocTraBan=rst.getInt("co_dia");
                    intCodEmpMovBan=rst.getInt("co_empMovBan");
                    intCodLocMovBan=rst.getInt("co_locMovBan");
                    intCodTipDocMovBan=rst.getInt("co_tipDocMovBan");
                    intCodDocMovBan=rst.getInt("co_docMovBan");
                    //if(objAsiDiaTraBan.consultarDiario(rst.getInt("co_emp"), rst.getInt("co_loc"), rst.getInt("co_tipDoc"), rst.getInt("co_dia") ) )
                    if(objAsiDiaTraBan.consultarDiario(intCodEmpTraBan, intCodLocTraBan, intCodTipDocTraBan, intCodDocTraBan) )
                    {
                        blnRes=true;
                    }                      
                }                
                rst.close();
                rst=null;
                stm.close();
                stm=null;
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
    
    private boolean insertarReg()
    {   
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                con.setAutoCommit(false);
                if(asignarValorCarta()){
                    if(insertarCabMovInv()){
                        if (insertarDetConIntMovInv()){
                            if (insertarRelDetConIntCarPag()){
                                if(insertarPagMovInv()){
                                    if(insertarDiario()){
                                        if(insertarCabSegMovInv()) {
                                            if(enviarCorEle()) {
                                                con.commit();
                                                blnRes=true;
                                            }else con.rollback(); 
                                        }else con.rollback();  
                                    }else con.rollback();
                                }else con.rollback(); 
                            }else con.rollback();
                        }else con.rollback();   
                    }else con.rollback(); 
                }else con.rollback(); 
                
                con.close();                                            
                con=null;
                objTblMod.clearDataSavedBeforeRemoveRow();
                objTblMod.initRowsState();
            }
        }
        catch(SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        catch(Exception Evt)  {  blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
        return blnRes;                  
    }  
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCabMovInv(){
        boolean blnRes=true;
        int intUltDoc=-1;
        BigDecimal bgdValTot=new BigDecimal ("0");
        BigDecimal bgdValCar=new BigDecimal ("0");
        String strFecUltMod="";
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener el código del último registro. 
                //Se verifica en la tabla de diarios, porque ya existen registros con el tipo de documento TRBADA.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc) as co_doc";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmpImp;
                strSQL+=" AND a1.co_loc=" + intCodLocImp;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltDoc=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltDoc==-1)
                    return false;
                intUltDoc++;
                txtCodDoc.setText(""+intUltDoc);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                
                bgdValTot=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());
                bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabmovinv(";
                strSQL+="       co_emp, co_loc, co_tipDoc, co_doc, ne_secGrp, ne_secEmp, ne_numCot, ne_numDoc";
                strSQL+="     , tx_numPed, ne_numGui, co_dia, fe_doc, fe_ven, co_Exp, co_cli, co_com, tx_nomVen, tx_ate";
                strSQL+="     , co_forPag, tx_desForPag, nd_sub";
                strSQL+="     , nd_valIva, nd_tot, tx_ptoPar, tx_tra, co_motTra, tx_desMotTra, co_cta";
                strSQL+="     , co_motDoc, co_mnu, st_imp, tx_obs1, tx_obs2, st_reg";
                strSQL+="     , fe_ing, co_usrIng, tx_comIng, fe_ultMod, co_usrMod , tx_comMod";
                strSQL+="     , fe_con, tx_obs3, co_forRet, tx_vehRet, tx_choRet";
                strSQL+="     , ne_numVecRecDoc, fe_ultRecDoc, tx_obsSolAut, tx_obsAutSol, st_aut, ne_valAut";
                strSQL+="     , st_tipDev, st_conInv, st_regRep, ne_numDocRee";
                strSQL+="     , st_creGuiRem, st_conInvTraAut, co_locRelSolDevVen, co_tipDocRelSolDevVen";
                strSQL+="     , co_docRelSolDevVen, st_excDocConVenCon, fe_autExcDocConVenCon";
                strSQL+="     , co_usrAutExcDocConVenCon, tx_comAutExcDocConVenCon";
                strSQL+="     , st_docConMerSalDemDebFac, st_autAnu, fe_autAnu, co_usrAutAnu, tx_comAutAnu, st_emiChqAntRecFacPrv, st_docMarLis, nd_valCar)";
                strSQL+=" VALUES (";
                strSQL+="  " + intCodEmpImp + "";  //co_emp
                strSQL+=", " + intCodLocImp + "";  //co_loc
                strSQL+=", " + txtCodTipDoc.getText() + "";        //co_tipdoc
                strSQL+=", " + txtCodDoc.getText() + "";           //co_doc
                strSQL+=", Null"; //ne_secgrp
                strSQL+=", Null"; //ne_secemp
                strSQL+=", Null"; //ne_numcot
                strSQL+=", " + txtNumDoc.getText() + "";      //ne_numdoc
                strSQL+=", Null"; //tx_numped
                strSQL+=", Null"; //ne_numgui
                strSQL+=", Null"; //co_dia
                strSQL+=",' " + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_doc
                strSQL+=", Null"; //fe_ven
                strSQL+=", Null"; //co_Exp
                strSQL+=", " + intCodCliImp + "";      //co_cli
                strSQL+=", " + objParSis.getCodigoUsuario() + ""; //co_com
                strSQL+=", '" + objParSis.getNombreUsuario().toLowerCase() + "'"; //tx_nomven
   
                if(chkMosParRes.isSelected()) //tx_ate: Se usará para indicar si muestra el párrafo de responsabilidad.
                    strSQL+=", 'S'";   //tx_ate
                else
                    strSQL+=", Null "; //tx_ate
                
                strSQL+=", 1";    //co_forpag
                strSQL+=", 'Contado'"; //tx_desforpag
                strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_sub
                strSQL+=", 0";//nd_valiva
                strSQL+=", " + bgdValTot.multiply(bgdSig); //nd_tot
                strSQL+=", Null";//tx_ptopar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_mottra
                strSQL+=", Null";//tx_desmottra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motdoc
                strSQL+=", " + objParSis.getCodigoMenu(); //co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=", 'A'";//st_reg
                strSQL+=", '" + strFecUltMod + "'";//fe_ing
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usring
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_coming
                strSQL+=", '" + strFecUltMod + "'";//fe_ultMod
                strSQL+=", " + objParSis.getCodigoUsuario() + "";//co_usrmod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";//tx_commod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forret
                strSQL+=", Null";//tx_vehret
                strSQL+=", Null";//tx_choret
                strSQL+=", 0";   //ne_numvecrecdoc
                strSQL+=", Null";//fe_ultrecdoc
                strSQL+=", Null";//tx_obssolaut
                strSQL+=", Null";//tx_obsautsol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valaut
                strSQL+=", 'C'";//st_tipdev
                strSQL+=", 'P'";//st_coninv
                strSQL+=", 'I'";//st_regrep
                strSQL+=", Null";//ne_numdocree
                strSQL+=", 'N'";//st_creguirem
                strSQL+=", 'N'";//st_coninvtraaut
                strSQL+=", Null";//co_locrelsoldevven
                strSQL+=", Null";//co_tipdocrelsoldevven
                strSQL+=", Null";//co_docrelsoldevven
                strSQL+=", Null";//st_excdocconvencon
                strSQL+=", Null";//fe_autexcdocconvencon
                strSQL+=", Null";//co_usrautexcdocconvencon
                strSQL+=", Null";//tx_comautexcdocconvencon
                strSQL+=", 'N'";//st_docconmersaldemdebfac
                strSQL+=", Null";//st_autanu
                strSQL+=", Null";//fe_autanu
                strSQL+=", Null";//co_usrautanu
                strSQL+=", Null";//tx_comautanu
                strSQL+=", 'S'"; //st_emiChqAntRecFacPrv
                strSQL+=", 'S'"; //st_docMarLis
                strSQL+=", " + bgdValCar; //nd_valCar
                strSQL+=");";

                System.out.println("insertarCabMovInv: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)   {
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDetConIntMovInv(){
        boolean blnRes=true;
        BigDecimal bgdValImp=BigDecimal.ZERO;
        int j=1;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);//
                objTblMod.removeEmptyRows();
                
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    bgdValImp=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_PAG_IMP)==null)?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()),objParSis.getDecimalesBaseDatos());                    
                    if(bgdValImp.compareTo(BigDecimal.ZERO)>0){
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_detConIntMovInv";
                        strSQL+=" (co_emp,co_loc,co_tipDoc,co_doc,co_reg,tx_des,nd_can,nd_preUni,st_iva,nd_tot,st_regRep)";
                        strSQL+="  SELECT ";
                        strSQL+="  " + intCodEmpImp + " AS co_emp";//co_emp
                        strSQL+=", " + intCodLocImp + " AS co_loc";  //co_loc
                        strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";   //co_tipDoc
                        strSQL+=", " + txtCodDoc.getText() + " AS co_doc";         //co_doc
                        strSQL+=", " + j + " AS co_reg"; //co_reg
                        strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CAR_PAG)) + " AS tx_des";//tx_des
                        strSQL+=", 1 AS nd_can";   //nd_can
                        strSQL+=", " + bgdValImp.multiply(bgdSig) + " AS nd_preUni";//nd_preUni     
                        strSQL+=", 'N' AS st_iva";//st_iva
                        strSQL+=", " + bgdValImp.multiply(bgdSig)+" AS nd_tot"; //nd_tot
                        strSQL+=", 'I' AS st_regRep";//st_regRep
                        strSQL+=" WHERE NOT EXISTS(";
                        strSQL+="    SELECT *FROM tbm_detConIntMovInv ";
                        strSQL+="    WHERE co_emp=" + intCodEmpImp + " AND co_loc=" + intCodLocImp + "";
                        strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                        strSQL+="    AND co_reg=" + j + "";
                        strSQL+=" );";
                        strSQLUpd+=strSQL;
                        j++;
                    }
                }
                System.out.println("insertarDetConIntMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar la relación del detalle de un registro y cargos a pagar.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarRelDetConIntCarPag(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        try{
            if (con!=null){
                stm=con.createStatement();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                objTblMod.removeEmptyRows();
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    strSQL="";
                    strSQL+=" INSERT INTO tbr_detConIntCarPagInsImp";
                    strSQL+=" (co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_empRelPedEmb,co_locRelPedEmb,co_tipDocRelPedEmb,co_docRelPedEmb,co_regRelPedEmb)";
                    strSQL+="  SELECT ";
                    strSQL+="  " + intCodEmpImp + " AS co_emp";   //co_emp
                    strSQL+=", " + intCodLocImp + " AS co_loc";   //co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + " AS co_tipDoc";    //co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + " AS co_doc";          //co_doc
                    strSQL+=", " + j + " AS co_reg";                            //co_reg
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED)) + " AS co_empRelPedEmb";           //co_empRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED)) + " AS co_locRelPedEmb";           //co_locRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED)) + " AS co_tipDocRelPedEmb";     //co_tipDocRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED)) + " AS co_docRelPedEmb";           //co_docRel
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_REG_CAR)) + " AS co_regRelPedEmb";           //co_regRel                    
                    strSQL+=" WHERE NOT EXISTS( ";
                    strSQL+="    SELECT * FROM tbr_detConIntCarPagInsImp ";
                    strSQL+="    WHERE co_emp=" + intCodEmpImp + " AND co_loc=" + intCodLocImp + "";
                    strSQL+="    AND co_tipDoc=" + txtCodTipDoc.getText() + " AND co_doc=" + txtCodDoc.getText() + "";
                    strSQL+="    AND co_reg=" + j + "";
                    strSQL+=" );";
                    j++;
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertarRelDetConIntCarPag: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarPagMovInv(){
        boolean blnRes=true;
        int j=1;
        String strSQLUpd="";
        BigDecimal bgdValTot=new BigDecimal ("0");
        try{
            if (con!=null){
                stm=con.createStatement();
                bgdValTot=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());
                strSQL ="";
                strSQL+=" INSERT INTO tbm_pagMovInv(";
                strSQL+="            co_emp, co_loc, co_tipDoc, co_doc, co_reg, ne_diaCre, fe_ven,";
                strSQL+="            co_tipRet, nd_porRet, tx_aplRet, nd_basImp, mo_pag, ne_diaGra,";
                strSQL+="            nd_abo, st_sop, st_entSop, st_pos, co_banChq, tx_numCtaChq,";
                strSQL+="            fe_recChq, nd_monChq, co_proChq, st_reg,";
                strSQL+="            fe_ree, co_usrRee, tx_comRee, st_autPag, co_ctaAutPag, tx_obs1,";
                strSQL+="            tx_numChq, tx_numSer, tx_numAutSRI, tx_fecCad, fe_venChq, nd_valIva, tx_codSRI, st_regRep";
                if (dtpFecCar.isFecha()){
                    strSQL+="          , fe_venChqAutPag";
                }
                strSQL+=" )";      
                strSQL+=" VALUES (";
                strSQL+="  " + intCodEmpImp;    //co_emp
                strSQL+=", " + intCodLocImp;    //co_loc
                strSQL+=", " + txtCodTipDoc.getText();          //co_tipDoc
                strSQL+=", " + txtCodDoc.getText();             //co_doc
                strSQL+=", " + j;               //co_reg
                strSQL+=", 0";                  //ne_diaCre
                strSQL+=",' " + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_ven
                strSQL+=", Null";                            //co_tipRet
                strSQL+=", Null";                            //nd_porRet
                strSQL+=", Null";                            //tx_aplRet
                strSQL+=", " + BigDecimal.ZERO + "";         //nd_basImp
                strSQL+=", " + bgdValTot.multiply(bgdSig)+"";//mo_pag
                strSQL+=", 0";                               //ne_diaGra
                strSQL+=", " + BigDecimal.ZERO + "";         //nd_abo
                strSQL+=", 'N'"; //st_sop
                strSQL+=", 'N'"; //st_entSop
                strSQL+=", 'N'"; //st_pos
                strSQL+=", Null";//co_banChq
                strSQL+=", Null";//tx_numCtaChq
                strSQL+=", Null";//fe_recChq
                strSQL+=", Null";//nd_monChq
                strSQL+=", Null";//co_proChq
                strSQL+=", 'A'"; //st_reg
                strSQL+=", Null";//fe_ree
                strSQL+=", Null";//co_usrRee
                strSQL+=", Null";//tx_comRee
                strSQL+=", 'N'"; //st_autPag
                strSQL+=", Null";//co_ctaAutPag
                strSQL+=", Null";//tx_obs1
                strSQL+=", Null";//tx_numChq
                strSQL+=", Null";//tx_numSer
                strSQL+=", Null";//tx_numAutSRI
                strSQL+=", Null";//tx_fecCad
                strSQL+=", Null";//fe_venChq
                strSQL+=", " + BigDecimal.ZERO + "";//nd_valIva
                strSQL+=", Null";//tx_codSRI
                strSQL+=", 'I'"; //st_regRep
                if (dtpFecCar.isFecha()){
                    strSQL+=",' " + objUti.formatearFecha(dtpFecCar.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"; //fe_venChqAutPag
                }
                strSQL+=");";
                strSQLUpd+=strSQL;
                System.out.println("insertarPagMovInv: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
    
    private boolean insertarDiario(){
        boolean blnRes=false;
        try{
            if(con!=null){
                if (objAsiDia.insertarDiario(con, intCodEmpImp, intCodLocImp, Integer.parseInt(txtCodTipDoc.getText()), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy")))  
                {
                    System.out.println("insertarDiario");
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
     * Función que permite relación entre Pago de Impuesto y Pedido Embarcado
     * @return 
     */
    private boolean insertarCabSegMovInv(){
        boolean blnRes=true;
        String strNumPedBef="", strNumPedAft="";
        objCodSeg=null;
        try{
            if(con!=null){
                for (int i=0;i<objTblMod.getRowCountTrue();i++){   
                    objCodSeg=null;
                    strNumPedBef=objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED_IMP)) ;
                    if(!strNumPedBef.equals(strNumPedAft)) //Valida que solo se inserte seguimiento una sola vez por pedido.
                    {
                        strNumPedAft=objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NUM_PED_IMP)) ;
                        //Armar sentencia SQL que determina si existe código de seguimiento asociado al pedido embarcado.
                        stm=con.createStatement();
                        strSQL="";
                        strSQL+=" SELECT *FROM(";
                        strSQL+=" 	SELECT a3.co_seg AS co_segPedEmb, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+=" 	FROM (tbm_cabPedEmbImp AS a1 LEFT OUTER JOIN tbm_cabSegMovInv AS a3";
                        strSQL+="         ON a1.co_emp=a3.co_empRelCabPedEmbImp AND a1.co_loc=a3.co_locRelCabPedEmbImp";
                        strSQL+=" 	      AND a1.co_tipDoc=a3.co_tipDocRelCabPedEmbImp AND a1.co_doc=a3.co_docRelCabPedEmbImp)";
                        strSQL+=" 	WHERE a1.co_emp=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED))  + "";
                        strSQL+=" 	AND a1.co_loc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED))  + "";
                        strSQL+=" 	AND a1.co_tipDoc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED)) + "";
                        strSQL+=" 	AND a1.co_doc=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED)) + "";
                        strSQL+=" ) AS b1";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            objCodSeg=rst.getObject("co_segPedEmb");  //Ya existe seguimiento del pedido embarcado.
                        }
                        rst.close();
                        rst=null;
                        stm.close();
                        stm=null;                

                        //Insertar en Seguimiento: Pagos de impuestos.
                        if( !objSegMovInv.setSegMovInvCompra(con,  objCodSeg 
                                                           ,  5, intCodEmpImp, intCodLocImp, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), "Null"
                                                           ,  2
                                                           ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED).toString() )
                                                           ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED).toString() )
                                                           ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED).toString() )
                                                           ,  Integer.parseInt(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED).toString() ) , "Null"                                

                           ))
                        {
                            blnRes=false;
                        }    
                        
                    }
                }
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
    
    
    private boolean enviarCorEle()
    {
        boolean blnRes=false;
        int intCodCfgCorEle=12;
        String strBan="BANCO ";//String strBan="BANCO PRODUBANCO";
        try{
            String strSubject="Liquidación de Aduana - Pedido: "+txtNumPedEmb.getText()+" *** "+txtNomEmpPedEmb.getText()+" - "+strBan;
            
            //Arma Cabecera Tabla
            String strMsg ="";
            strMsg+="<HTML> ";
            strMsg+="<STRONG> **** "+txtNomEmpPedEmb.getText()+" **** <BR><BR>";
            strMsg+="<STRONG> REALIZAR PAGO POR:  "+strBan+"  <BR><BR>";
            strMsg+="<STRONG>"+txtDesCorTipDoc.getText()+" # "+txtNumDoc.getText()+" - VALOR TOTAL: $ "+txtValDoc.getText()+" <STRONG> <BR><BR>";
            strMsg+="<TABLE BORDER=1> ";   
            for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    BigDecimal bgdVal=objUti.redondearBigDecimal(new BigDecimal( (objTblMod.getValueAt(i,INT_TBL_DAT_VAL_PAG_IMP)==null)?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()),objParSis.getDecimalesMostrar());
                strMsg+="  <tr> <td>"+objTblMod.getValueAt(i,INT_TBL_DAT_NOM_CAR_PAG).toString()+"</td> <td>$ "+bgdVal+"</td> </tr>"; 
            }                
            strMsg+=" </TABLE> ";
            strMsg+=" </HTML>";
            //Envía el correo.
            if(strMsg.length()>0){
                if(objNotCorEle.enviarNotificacionCorreoElectronicoConAsunto(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), intCodCfgCorEle, strMsg, strSubject )){
                    mostrarMsgInf("<HTML>Correo de notificación de Liquidación de Aduana.</HTML>");
                    blnRes=true;
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
     * Genera Asiento de Diario del documento
     * @return 
     */
    private boolean generarAsiDia()
    {  
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;  
        intCodCtaDocPag=-1;
        strNumCtaDocPag="";
        strNomCtaDocPag="";        
        try
        { 
            if(objTooBar.getEstado()=='n') //Se valida que solo genere asiento de diario cuando se inserte y NO modo Consulta.
            { 
                conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conLoc!=null){
                    stmLoc=conLoc.createStatement();               

                    //Asiento de DxP: Obtiene Cuenta por Pagar
                    //Se realiza el asiento de diario neteado con las CxP, para que existan movimientos duplicados con la cuenta de pedido, analizado con Ingrid Lino 06/Ago/2019.
                    strSQL ="";
                    strSQL+=" SELECT x.co_ctaDocPag, x.tx_codCtaDocPag, x.tx_nomCtaDocPag";
                    strSQL+=" FROM(";
                    strSQL+=" 	SELECT a1.co_emp, a1.co_ctaHab as co_ctaDocPag, a1.co_tipDoc";
                    strSQL+=" 	     , a2.tx_codCta AS tx_codCtaDocPag, a2.tx_desLar AS tx_nomCtaDocPag";
                    strSQL+=" 	FROM tbm_cabTipDoc AS a1";
                    strSQL+=" 	INNER JOIN tbm_plaCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_ctaHab=a2.co_cta";
                    strSQL+="   WHERE a1.co_emp=" + intCodEmpImp + "";
                    strSQL+="   AND a1.co_loc=" + intCodLocImp + "";
                    strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" ) AS x";
                    rstLoc=stmLoc.executeQuery(strSQL);
                    if(rstLoc.next()){
                        intCodCtaDocPag=rstLoc.getInt("co_ctaDocPag");
                        strNumCtaDocPag=rstLoc.getString("tx_codCtaDocPag");
                        strNomCtaDocPag=rstLoc.getString("tx_nomCtaDocPag");
                    }
                    else{
                        mostrarMsgInf("<HTML>Configurar cuentas contables en el Tipo de Documento.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        limpiarTabla();
                        blnRes=false;
                    }
                    rstLoc.close();
                    rstLoc=null;                    
                    stmLoc.close();
                    stmLoc=null;
                    conLoc.close();
                    conLoc=null;
                }                
                
                if( new BigDecimal(txtValDoc.getText()).compareTo(BigDecimal.ZERO)>0  )
                {
                    //Se inicializa el asiento de diario
                    objAsiDia.inicializar();
                    vecDatDia.clear();

                    //Debe
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_DEB, ""+ new BigDecimal(txtValDoc.getText()) );
                    vecRegDia.add(INT_VEC_DIA_HAB, "0");
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);

                    //Haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA, "" + intCodCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA, "" + strNumCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA, "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA, "" + strNomCtaDocPag);
                    vecRegDia.add(INT_VEC_DIA_DEB, "0" );
                    vecRegDia.add(INT_VEC_DIA_HAB, ""+ new BigDecimal(txtValDoc.getText()));
                    vecRegDia.add(INT_VEC_DIA_REF, "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON, "");
                    vecDatDia.add(vecRegDia);
                }
                objAsiDia.setDetalleDiario(vecDatDia);
                objAsiDia.setGeneracionDiario((byte)2);
                actualizarGlo();
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo(){
        strAux="";
        strAux+="" + txtNumPedEmb.getText()+ ": Liquidación de Aduana";
        objAsiDia.setGlosa(strAux);
        txaObs2.setText(strAux);
    }
    
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes = false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizarDatCar()){  
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        } 
        catch (java.sql.SQLException e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) { 
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite actualizar los datos de la carta
     * Sólo se actualizará cuando no exista transferencia bancaria.
     * @return true: Si se pudo actualizar
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDatCar(){
        boolean blnRes=false;
        BigDecimal bgdValCar;
        String strFecUltMod="";
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;                
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
                
                //Armar la sentencia SQL.
                strSQL="";
                /* Actualiza Valor de la Carta */
                strSQL+=" UPDATE tbm_cabMovInv ";
                strSQL+=" SET nd_ValCar="+bgdValCar;
                if(chkMosParRes.isSelected()) //tx_ate: Se usará para indicar si muestra el párrafo de responsabilidad.
                    strSQL+=", tx_ate='S'";   //tx_ate
                else
                    strSQL+=", tx_ate=Null "; //tx_ate   
                
                strSQL+=", fe_ultMod='" + strFecUltMod + "'";  
                strSQL+=", co_usrmod=" + objParSis.getCodigoUsuario() + ""; 
                strSQL+=", tx_commod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + ""; 
                
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                strSQL+=" ;";
                
                /* Actualiza Fecha de la Carta */
                if (dtpFecCar.isFecha()){
                    strSQL+=" UPDATE tbm_pagMovInv ";
                    strSQL+=" SET fe_venChqAutPag='" + objUti.formatearFecha(dtpFecCar.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_Car
                    strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                    strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                    strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                    strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                    strSQL+=" ;";
                }
                
                System.out.println("actualizarDatCar: "+strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                blnRes=true;
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
        boolean blnRes=true;
        boolean blnRpt=false;
        int intNumTotRpt=0;
        String strRutImg="";
        String strSQLRep="", strSQLSubRep="";
        String strRutRpt="", strNomRpt="", strFecHorSer="";        
        java.util.Map mapPar = new java.util.HashMap();
        String strNumDia="", strDesCor="", strDesLar="", strUsrIng="", strUsrMod="";
        try
        {
            con =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){            
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
                    for (int i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){
                            blnRpt=false;
                            strRutRpt=objRptSis.getRutaReporte(i);
                            strNomRpt=objRptSis.getNombreReporte(i);
                     
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                            {   
                                case 276: 
                                    //<editor-fold defaultstate="collapsed" desc="/* Otros movimientos bancarios (Asientos Diario)... */"> 
                                    stm=con.createStatement();
                                    //Cabecera de Reporte 
                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.tx_numDia, a.fe_Dia, a.tx_glo, a5.tx_descor, a5.tx_deslar";
                                    strSQL+="      , a.co_usring, a6.tx_usr AS tx_nomUsrIng, a.co_usrMod, a7.tx_usr AS tx_nomUsrMod, a.st_Reg, a.st_imp";
                                    strSQL+=" FROM tbm_cabDia AS a";
                                    strSQL+=" INNER JOIN tbm_detDia AS a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                                    //strSQL+=" INNER JOIN tbm_docgenotrmovban AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a5 ON a.co_emp=a5.co_emp AND a.co_loc=a5.co_loc AND a.co_tipDoc=a5.co_tipDoc ";
                                    strSQL+=" INNER JOIN tbm_usr AS a6 ON a3.co_usring=a6.co_usr "; /* El usuario de ingreso, siempre se presentará el usuario que ingreso el OPIMIM (DXP)*/
                                    strSQL+=" INNER JOIN tbm_usr AS a7 ON a.co_usrMod=a7.co_usr ";  /* El usuario de modificación siempre se presentará el usuario que autorizó el OPIMP, o el usuario que modificó el Asiento de diario*/
                                    strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                    strSQL+=" AND a.st_reg NOT IN ('E', 'I')";
                                    strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a.fe_Dia, a.tx_glo, a5.tx_Descor, a5.tx_Deslar";
                                    strSQL+="      , a.co_usring, a6.tx_usr, a.co_usrMod, a7.tx_usr, a.st_Reg, a.st_imp";
                                    System.out.println("generarRpt:"+strSQL);
                                    strSQLRep=strSQL;
                                    rst = stm.executeQuery(strSQLRep);
                                    if (rst.next()) {
                                        blnRpt = true;
                                        strNumDia = rst.getString("tx_numdia");
                                        strDesCor = rst.getString("tx_descor");
                                        strDesLar = rst.getString("tx_deslar");
                                        strUsrIng = rst.getString("tx_nomUsrIng");
                                        strUsrMod = rst.getString("tx_nomUsrMod");
                                    }
                                    rst.close();
                                    rst=null;
                                    stm.close();
                                    stm=null;

                                    //Detalle de Reporte 
                                    strSQL ="";
                                    strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a1.co_reg";
                                    strSQL+="      , a4.co_cta, a4.tx_CodCta, a4.tx_Deslar AS tx_nomCta, a1.nd_monDeb, a1.nd_monHab";
                                    strSQL+=" FROM tbm_cabDia as a";
                                    strSQL+=" INNER JOIN tbm_detDia as a1 ON a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_dia=a1.co_dia";
                                    //strSQL+=" INNER JOIN tbm_docgenotrmovban AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_docRelCabDia";
                                    strSQL+=" INNER JOIN tbm_CabSegMovInv AS a2 ON a.co_emp=a2.co_empRelCabDia AND a.co_loc=a2.co_locRelCabDia AND a.co_tipDoc=a2.co_tipDocRelCabDia AND a.co_dia=a2.co_diaRelCabDia";
                                    strSQL+=" INNER JOIN tbm_cabMovInv as a3 ON a2.co_empRelCabMovInv=a3.co_emp AND a2.co_locRelCabMovInv=a3.co_loc AND a2.co_tipDocRelCabMovInv=a3.co_tipDoc AND a2.co_docRelCabMovInv=a3.co_doc";
                                    strSQL+=" INNER JOIN tbm_placta as a4 ON a.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta ";
                                    strSQL+=" WHERE a3.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND a3.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND a3.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND a3.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                    strSQL+=" AND a.st_reg NOT IN ('E', 'I')";
                                    strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_dia, a1.co_reg";
                                    strSQL+="        , a4.co_cta, a4.tx_CodCta, a4.tx_Deslar, a1.nd_monDeb, a1.nd_monHab";                   
                                    strSQLSubRep=strSQL;

                                    //Inicializar los parametros que se van a pasar al reporte.        
                                    mapPar.put("strCamAudRpt", "" + ( strUsrIng  + " / " + strUsrMod + " / " + objParSis.getNombreUsuario() ) + "      " + strFecHorSer + "      " + this.getClass().getName() + "      " + strNomRpt + "   "+strVer);
                                    mapPar.put("codUsrImp", "" + objParSis.getCodigoUsuario());
                                    mapPar.put("codEmp", intCodEmpTraBan);
                                    mapPar.put("codLoc", intCodLocTraBan);
                                    mapPar.put("codTipDoc", intCodTipDocTraBan);
                                    mapPar.put("codDia", intCodDocTraBan);
                                    mapPar.put("nomEmp", getNomEmp(intCodEmpTraBan));
                                    mapPar.put("rucEmp", getRucEmp(intCodEmpTraBan));
                                    mapPar.put("nomCorTipDoc", strDesCor);
                                    mapPar.put("nomLarTipDoc", strDesLar);
                                    mapPar.put("fecAct", "" + strFecHorSer);
                                    mapPar.put("fecDoc", objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", "dd/MM/yyyy"));
                                    mapPar.put("numDia", strNumDia);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);

                                    //</editor-fold>
                                    break;
                                case 388: 
                                    //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Banco del Pacífico)... */"> 
                                    //</editor-fold>
                                   break;

                                case 389: 
                                    //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Produbanco)... */"> 
                                    //</editor-fold>
                                   break;

                                case 424: 
                                    //<editor-fold defaultstate="collapsed" desc="/* Carta transferencias por derechos arancelarios (Internacional)... */"> 
                                    stm=con.createStatement();
                                    strSQL ="";
                                    strSQL+=" SELECT a1.fe_car, a2.tx_nom AS tx_nomEmp, a2.tx_dir AS tx_dirEmp, a2.tx_tel AS tx_telEmp, a2.tx_ruc, 'X' as marca_ruc";
                                    strSQL+="      , ROUND(a1.nd_valTra, 2) as nd_valTra,  a1.tx_valTraPal";
                                    strSQL+="      , ROUND((a1.nd_valTra - ((CASE WHEN f2.nd_adv IS NULL THEN 0 ELSE f2.nd_adv END) + (CASE WHEN f2.nd_fod IS NULL THEN 0 ELSE f2.nd_fod END))  ), 2) as nd_valIva";                                            
                                    strSQL+="      , a3.tx_numCtaBan AS tx_numCtaBanEgr, CASE WHEN a3.tx_tipCtaBan='A' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrAho";
                                    strSQL+="      , CASE WHEN a3.tx_tipCtaBan='C' THEN 'X' ELSE ' ' END AS tx_tipCtaBanEgrCor, a5.tx_desLar AS tx_nomBanCtaBanEgr";
                                    strSQL+="      , a6.tx_desLar AS tx_nomBanCtaBanIng, a7.tx_desLar AS tx_nomCiuCtaBanIng, a8.tx_desLar AS tx_nomPaiCtaBanIng";
                                    strSQL+="      , a4.tx_codIba, a4.tx_codAba, a4.tx_codSwi, a9.tx_nom2 AS tx_nomBenCtaBanIng, a4.tx_numCtaBan AS tx_numCtaBanIng";
                                    strSQL+="      , a9.tx_dir AS tx_dirBenCtaBanIng, a10.tx_desLar AS tx_nomCiuBenCtaBanIng";
                                    strSQL+="      , a11.tx_desLar AS tx_nomPaiBenCtaBanIng, a9.tx_tel1 as tx_telefBenef, a1.tx_motTra AS nd_valdau, 'X' as gastos_exterior";
                                    strSQL+="      , a4.tx_dirBan, f2.tx_motTra, CASE WHEN f2.nd_adv IS NULL THEN 0 ELSE f2.nd_adv END AS nd_adv, CASE WHEN f2.nd_fod IS NULL THEN 0 ELSE f2.nd_fod END AS nd_fod";
                                    strSQL+="      , a1.tx_firAut1, a1.tx_carFirAut1, a1.tx_firAut2, a1.tx_carFirAut2, a1.st_fircon, f1.tx_obs1";
                                    strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2  INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                    strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                    strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                    strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                    strSQL+=" INNER JOIN (  tbm_carTraBanDia AS a1";
                                    strSQL+=" 		    INNER JOIN tbm_emp AS a2 ON (a1.co_emp=a2.co_emp)";
                                    strSQL+=" 	            INNER JOIN tbm_ctaBan AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_ctaBanEgr=a3.co_ctaBan)";
                                    strSQL+=" 		    INNER JOIN tbm_ctaBan AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_ctaBanIng=a4.co_ctaBan)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_var AS a5 ON (a3.co_ban=a5.co_reg)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_var AS a6 ON (a4.co_ban=a6.co_reg)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_ciu AS a7 ON (a4.co_ciuCtaBan=a7.co_ciu)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_pai AS a8 ON (a7.co_pai=a8.co_pai)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_expImp AS a9 ON (a4.co_ctaExp=a9.co_exp)";
                                    strSQL+=" 		    LEFT OUTER JOIN tbm_ciu AS a10 ON (a9.co_ciu=a10.co_ciu)";
                                    strSQL+="		    LEFT OUTER JOIN tbm_pai AS a11 ON (a10.co_pai=a11.co_pai)";
                                    strSQL+=" ) ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                    strSQL+=" WHERE f2.tx_tipTra='A'";
                                    strSQL+=" AND f1.co_emp=" + intCodEmpMovBan;
                                    strSQL+=" AND f1.co_loc=" + intCodLocMovBan;
                                    strSQL+=" AND f1.co_tipDoc=" + intCodTipDocMovBan;
                                    strSQL+=" AND f1.co_doc=" + intCodDocMovBan;

                                    if(Integer.parseInt(objRptSis.getCodigoReporte(i))==388)//para banco del pacifico
                                        strSQL+=" AND  (a5.tx_desLar LIKE lower('%fi%') OR a5.tx_desLar LIKE upper('%FI%')  )";
                                    else if(Integer.parseInt(objRptSis.getCodigoReporte(i))==389)//para banco produbanco
                                        strSQL+=" AND  (a5.tx_desLar LIKE lower('%DU%') OR a5.tx_desLar LIKE upper('%DU%')  )";
                                    else if(Integer.parseInt(objRptSis.getCodigoReporte(i))==424)//para banco internacional
                                        strSQL+=" AND  (a5.tx_desLar LIKE lower('%ternacio%') OR a5.tx_desLar LIKE upper('%TERNACIO%')  )";

                                    strSQL+=" ORDER BY f2.co_emp, f2.co_loc, f2.co_tipdoc, f2.co_doc, f2.co_reg";
                                    strSQLRep=strSQL;
                                    rst=stm.executeQuery(strSQLRep);
                                    if(rst.next())
                                        blnRpt=true;
                                    rst.close();
                                    rst=null;
                                    stm.close();
                                    stm=null;                                            
                                    

                                    //Fecha de Carta
                                    stm=con.createStatement();
                                    strSQL ="";
                                    strSQL+=" SELECT a1.fe_car, f3.co_emp";
                                    strSQL+=" FROM tbm_cabOtrMovBan AS f1 INNER JOIN (  tbm_detOtrMovBan AS f2     INNER JOIN tbm_emp AS f3  ON f2.co_empTra=f3.co_emp)";
                                    strSQL+=" ON f1.co_emp=f2.co_emp AND f1.co_loc=f2.co_loc AND f1.co_tipDoc=f2.co_tipDoc AND f1.co_doc=f2.co_doc";
                                    strSQL+=" INNER JOIN tbm_docgenotrmovban AS f4";
                                    strSQL+=" ON f2.co_emp=f4.co_emp AND f2.co_loc=f4.co_loc AND f2.co_tipDoc=f4.co_tipDoc AND f2.co_doc=f4.co_doc AND f2.co_reg=f4.co_reg";
                                    strSQL+=" INNER JOIN tbm_carTraBanDia AS a1";
                                    strSQL+=" ON f4.co_empRelCabDia=a1.co_emp AND f4.co_locRelCabDia=a1.co_loc AND f4.co_tipDocRelCabDia=a1.co_tipDoc AND f4.co_docRelCabDia=a1.co_dia";
                                    strSQL+=" WHERE f2.tx_tipTra='A'";
                                    strSQL+=" AND f1.co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                                    strSQL+=" AND f1.co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                                    strSQL+=" AND f1.co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                                    strSQL+=" AND f1.co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
                                    strSQL+=" GROUP BY a1.fe_car, f3.co_emp";
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        if( Integer.parseInt(objRptSis.getCodigoReporte(i))==389 ){ //debito por aranceles - copia exacta
                                            mapPar.put("fe_car", objUti.formatearFecha(rst.getString("fe_car"), "yyyy-MM-dd", "dd/MM/yyyy") );
                                        }
                                        else{
                                            mapPar.put("fe_car", objUti.formatearFecha(rst.getString("fe_car"), "yyyy-MM-dd", "dd 'de' MMMMM 'de' yyyy") );
                                        }
                                        //Ruta de Imagen del Logo
                                        switch(rst.getInt("co_emp")){
                                            case 1: strRutImg=strRutRpt + "\\logCabTuv.png";  break;
                                            case 2: strRutImg=strRutRpt + "\\logCabCas.png";  break;
                                            case 4: strRutImg=strRutRpt + "\\logCabDim.png";  break;                                                        
                                        }                                                 
                                    }
                                    else{
                                        mapPar.put("fe_car", "" );
                                        strRutImg="";
                                    }
                                    //otros parametros
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strRutRpt", strRutRpt);
                                    mapPar.put("strRutImg", strRutImg);

                                    rst.close();
                                    rst=null;
                                    stm.close();
                                    stm=null;
                                    //</editor-fold>
                                    break;                                    
                            }
                            if(blnRpt)
                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);

                        }
                    }
                }
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  
    
    private String getNomEmp(int intCodEmp) {
        String strNomEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSqlAux="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSqlAux = " SELECT tx_nom FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSqlAux);
                if (rstLoc.next()) {
                    strNomEmp = rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strNomEmp;
    }

    private String getRucEmp(int intCodEmp) {
        String strRucEmp= "";
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSqlAux="";
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc = conLoc.createStatement();
                strSqlAux=" SELECT tx_ruc FROM tbm_emp WHERE co_emp=" + intCodEmp;
                rstLoc = stmLoc.executeQuery(strSqlAux);
                if (rstLoc.next()) {
                    strRucEmp = rstLoc.getString("tx_ruc");
                }
                rstLoc.close();
                stmLoc.close();
                conLoc.close();
                rstLoc = null;
                stmLoc = null;
                conLoc = null;
            }
        } 
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e);} 
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);  }
        return strRucEmp;
    }       
    
    /**
     * Función que valida en cada fila que la cantidad ingresada en la columna "Val.Pag." no este fuera del rango permitido
     * El rango minimo es 0.995 y el rango máximo es 1.005 
     * @return 
     */
    private boolean validarRangoValor(){
        boolean blnRes=true;
        Connection conLoc;
        ResultSet rstLoc;
        Statement stmLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc=conLoc.createStatement();  
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    BigDecimal bgdCan=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_VAL_PAG_IMP).toString()));
                    //Verificar
                    strSQL ="";
                    strSQL+=" SELECT * ";
                    strSQL+=" FROM ("; 
                    strSQL+="    SELECT ("+bgdCan+" /(CASE WHEN a.nd_valCarPag IS NULL THEN 0 ELSE a.nd_valCarPag END) )AS valorCalculadoRango";
                    strSQL+="          , CAST (0.995 AS NUMERIC) AS min, CAST (1.005 AS NUMERIC) AS max";
                    strSQL+="    FROM tbm_carPagPedEmbImp as a";
                    strSQL+="    WHERE a.co_emp="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_EMP_PED));
                    strSQL+="    AND a.co_loc="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_LOC_PED));
                    strSQL+="    AND a.co_tipDoc="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_TIPDOC_PED));
                    strSQL+="    AND a.co_doc="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_DOC_PED));
                    strSQL+="    AND a.co_carPag="+ objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_CAR_PAG));
                    strSQL+=" ) AS x";
                    strSQL+=" WHERE (CASE WHEN (x.valorCalculadoRango < x.min) or (x.valorCalculadoRango > x.max) THEN TRUE ELSE FALSE END )  ";
                    rstLoc=stmLoc.executeQuery(strSQL); 
                    if(rstLoc.next()){
                        blnRes=false;
                    }
                    rstLoc.close();
                    rstLoc=null;
                }                
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
            }
        } 
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        } 
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }        
        return blnRes;    
    }    

    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
        objAsiDia.inicializar();
        objAsiDiaTraBan.inicializar();
    }
    
    /**
     * Esta función permite cargar el detalle del pedido seleccionado
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private void cargarTabla()
    {
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        BigDecimal bgdAux=new BigDecimal(BigInteger.ZERO);
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null){
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT * FROM (";
                strSQL+="    SELECT a3.co_emp AS co_empPedEmb, a3.co_loc AS co_locPedEmb, a3.co_tipDoc AS co_tipDocPedEmb, a3.co_doc AS co_docPedEmb, a3.co_imp AS co_EmpImp, a3.st_doc, a1.ne_ubi";
                strSQL+="        ,  a2.co_reg as co_regCar, a1.co_carPag, a1.tx_nom as tx_nomCar, 'A' as tx_tipTra, a2.nd_valCarPag, a4.tx_nom as tx_nomEmpImp, a3.co_exp";
                strSQL+="        , (CASE WHEN a3.tx_numDocCon IS NOT NULL THEN a3.tx_numDocCon ELSE (CASE WHEN a3.tx_numDoc4 IS NOT NULL THEN a3.tx_numDoc4 ELSE a3.tx_numDoc2 END) END) AS tx_numPedCon";
                strSQL+="        , (CASE WHEN a3.tx_numDoc4 IS NOT NULL THEN a3.tx_numDoc4 ELSE a3.tx_numDoc2 END) AS tx_numPed";
                strSQL+="    FROM tbm_carPagImp AS a1 ";
                strSQL+="    INNER JOIN tbm_carPagPedEmbImp AS a2 ON a1.co_carPag=a2.co_CarPag ";
                strSQL+="    INNER JOIN tbm_cabPedEmbImp AS a3 ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc ";
                strSQL+="    INNER JOIN tbm_emp AS a4 ON a3.co_imp=a4.co_emp";
                strSQL+="    WHERE a3.st_reg='A' AND (a3.st_doc IN ('A') OR a3.st_doc IS NULL) ";
                strSQL+="    AND a1.st_imp IN ('S') AND a1.st_pagExt='N'"; //Mostrar sólo cargos que son Impuestos.  
                strSQL+=" ) AS x";
                strSQL+=" WHERE x.tx_numPedCon LIKE '%" + txtNumPedEmb.getText().replaceAll("'", "''") + "%'";
                //strSQL+=" ORDER BY x.co_carPag";
                strSQL+=" ORDER BY x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.ne_ubi";
                //System.out.println("cargarTabla: "+strSQL);
                rstLoc=stmLoc.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rstLoc.next()){
                    intCodEmpImp=rstLoc.getInt("co_empImp");
                    lblNomEmpImp.setText("*** "+rstLoc.getString("tx_nomEmpImp"));
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP_PED,     rstLoc.getString("co_empPedEmb"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_PED,     rstLoc.getString("co_locPedEmb"));
                    vecReg.add(INT_TBL_DAT_COD_TIPDOC_PED,  rstLoc.getString("co_tipDocPedEmb"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_PED,     rstLoc.getString("co_docPedEmb"));
                    vecReg.add(INT_TBL_DAT_NUM_PED_IMP,     rstLoc.getString("tx_numPed"));
                    vecReg.add(INT_TBL_DAT_COD_EMP_IMP,     rstLoc.getString("co_empImp"));  
                    
                    vecReg.add(INT_TBL_DAT_COD_REG_CAR,     rstLoc.getString("co_regCar"));
                    vecReg.add(INT_TBL_DAT_COD_CAR_PAG,     rstLoc.getString("co_carPag"));
                    vecReg.add(INT_TBL_DAT_NOM_CAR_PAG,     rstLoc.getString("tx_nomCar"));
                    vecReg.add(INT_TBL_DAT_BUT_CAR_PAG,     "");
                    bgdAux=objUti.redondearBigDecimal( (rstLoc.getBigDecimal("nd_valCarPag")==null?BigDecimal.ZERO:rstLoc.getBigDecimal("nd_valCarPag")) , objParSis.getDecimalesBaseDatos());
                    vecReg.add(INT_TBL_DAT_VAL_PAG_IMP, ""+bgdAux);
                    vecDat.add(vecReg);      
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                conLoc.close();
                conLoc=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                eliminaItmValCarPagCero();
                calcularTot();
            }
        }
        catch (java.sql.SQLException e)    {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)   {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
                if(anular_tbmCabMovInv()){
                    if(anularDiario()){
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
        catch (java.sql.SQLException e)   {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e)    {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }    
    
    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabMovInv(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_CabMovInv";
                strSQL+=" SET st_reg='I'";
                strSQL+=" WHERE co_emp=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP);
                strSQL+=" AND co_loc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC);
                strSQL+=" AND co_tipDoc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC);
                strSQL+=" AND co_doc=" + objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC);
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
    
    
    private boolean anularDiario(){
        boolean blnRes=false;
        try{ 
            if(con!=null){
                if(objAsiDia.anularDiario(con, objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_EMP)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_LOC)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_TIPDOC)
                                             , objUti.getIntValueAt(arlDatCon, intIndiceCon, INT_ARL_CON_COD_DOC)
                                        ))
                {
                    //System.out.println("anularDiario");
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
     * Se asigna valor del documento a al valor de la carta
     * @return 
     */
    private boolean asignarValorCarta(){
        boolean blnRes=true;

        /* Validar: Valor de la carta */
        BigDecimal bgdValCar=objUti.redondearBigDecimal(new BigDecimal(txtValCar.getText()==null?"0":(txtValCar.getText().equals("")?"0":txtValCar.getText())) , objParSis.getDecimalesMostrar());
        BigDecimal bgdValDoc=objUti.redondearBigDecimal(new BigDecimal(txtValDoc.getText()==null?"0":(txtValDoc.getText().equals("")?"0":txtValDoc.getText())) , objParSis.getDecimalesMostrar());

        /* Si el valor de la carta es menor o igual a 0, entonces se le asigna el valor total del documento. */
        if(bgdValCar.compareTo(BigDecimal.ZERO)<=0) {
            txtValCar.setText( "" + (objUti.redondearBigDecimal(bgdValDoc, objParSis.getDecimalesMostrar())) );
        }                 

        return blnRes;
    }
    
    
        
}



